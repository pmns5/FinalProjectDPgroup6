class SingleFilm {
    constructor(endPoint, id) {
        this.addFeedbackEndPoint = endPoint + "/add-feedback";
        this.getFilmDetailsEndPoint = endPoint + "/get-film-details";
        this.id = id
    }

    showFilm() {
        let controller = this
        $.getJSON(this.getFilmDetailsEndPoint, {id: this.id}, function (obj) {
            $('#title').text(obj.film.title);
            $('#genre').text(obj.film.genre);
            $('#plot').text(obj.film.plot);
            $('#filmPoster').attr("src", "data:image/jpeg;base64," + obj.film.poster);
            $('#trailerFilm').attr("src", "https://www.youtube.com/embed/" + obj.film.trailer);
            $('#cast').text(controller.getCast(obj.actors));
            $.each(obj.feedbackList, function (index, obj) {
                let feedback = controller.constructFeedbackView(obj);
                $('#feedbackList').append(feedback)
            });
            $(".rating-upper").css({
                width: obj.avgScore * 20 + "%"
            });
        });
    }

    renderAlert(message, success) {
        let alert;
        if (success) {
            alert = $('#success-alert-template');
        } else {
            alert = $('#fail-alert-template');
        }
        const html = alert.html().replace(/{message}/ig, message);
        $(html).prependTo('#response-alert-section')
            .delay(5000)
            .queue(function () {
                $(this).remove();
            });
    };

    getCast(actors) {
        var allActors = [];
        $.each(actors, function (index, actor) {
            allActors.push(actor.name + " " + actor.surname)
        })
        return allActors.join(", ")
    }

    constructFeedbackView(obj) {
        console.log(obj);
        return '<div class="card p-3 mt-2">' +
            '       <div class="d-flex justify-content-between align-items-center">' +
            '           <div class="user d-flex flex-row align-items-center">' +
            '               <img src="../../images/user.jpeg" width="30" class="user-img rounded-circle mr-2" alt="">' +
            '               <span>' +
            '                   <small class="font-weight-bold text-primary">' + obj.user.username + '</small>' +
            '                   <small class="font-weight-bold">' + String(obj.feedback.comment) + '</small>' +
            '               </span>' +
            this.addStars(obj.user.id, obj.feedback.score) +
            '           </div>' +
            '       <small>' + obj.feedback.date + '</small>' +
            '       </div>' +
            '   </div>'
    }

    addStars(id, score) {

        return '<fieldset class="rating">' +
            '       <input id="id_1_"' + id + ' type="checkbox" disabled ' + (score >= 5 ? 'checked' : '') + ' value="5"/><label for="id_1_"' + id + '>5 stars</label>' +
            '       <input id="id_2_"' + id + ' type="checkbox" disabled ' + (score >= 4 ? 'checked' : '') + ' value="4"/><label for="id_2_"' + id + '>4 stars</label>' +
            '       <input id="id_3_"' + id + ' type="checkbox" disabled ' + (score >= 3 ? 'checked' : '') + ' value="3"/><label for="id_3_"' + id + '>3 stars</label>' +
            '       <input id="id_4_"' + id + ' type="checkbox" disabled ' + (score >= 2 ? 'checked' : '') + ' value="2"/><label for="id_4_"' + id + '>2 stars</label>' +
            '       <input id="id_5_"' + id + ' type="checkbox" disabled ' + (score >= 1 ? 'checked' : '') + ' value="1"/><label for="id_5_"' + id + '>1 stars</label>' +
            '   </fieldset>'
    }

    insertFeedback() {
        let controller = this;
        $('#add-idFilm').val(this.id)
        $('#add-idUser').val(getCookieID())
        let data = $('#insert-form').serialize();
        console.log(data)
        $.post(this.addFeedbackEndPoint, data, function () {
        }).done(function () {
            controller.renderAlert('Feedback successfully entered.', true);
            $('#add-feedback').val('');
            window.location.reload();
        }).fail(function () {
            controller.renderAlert('Error while inserting. Try again.', false);
        });

    }
}