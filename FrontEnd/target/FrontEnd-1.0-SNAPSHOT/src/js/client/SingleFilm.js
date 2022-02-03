class SingleFilm {
    constructor(endPoint, id) {
        this.viewEndPoint = endPoint + "/getFilmDetails";
        this.feedbackAdd = endPoint + "/add-feedback";
        this.id = id
    }

    showFilm() {
        let controller = this
        $.getJSON(this.viewEndPoint, {id: this.id}, function (obj) {
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

    /**
     * Render an alert banner with the message status.
     *
     * @param message: the message to show.
     * @param success: true if is a success banner, false if is a fail banner.
     */
    renderAlert(message, success) {
        let alert;
        if (success) {
            alert = $('#success-alert-template');
        } else {
            alert = $('#fail-alert-template');
        }
        const html = alert.html().replace(/{message}/ig, message);
        // Add banner and remove it after 5 seconds.
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
            '                   <small class="font-weight-bold">' +String(obj.feedback.comment) + '</small>' +
            '               </span>' +

                    this.addStars(obj.user.id, obj.feedback.score) +


        '           </div>' +
        '       <small>' + obj.feedback.date + '</small>' +
        '       </div>' +
        '   </div>'
    }

    addStars(id, score){

        return '    <fieldset class="rating">' +
            '        <input id="id_1_"' + id + ' type="checkbox" disabled ' + (score >= 5 ? 'checked' : '') + ' value="5"/><label for="id_1_"' + id + '>5 stars</label>' +
            '        <input id="id_2_"' + id + ' type="checkbox" disabled ' + (score >= 4 ? 'checked' : '') + ' value="4"/><label for="id_2_"' + id + '>4 stars</label>' +
            '        <input id="id_3_"' + id + ' type="checkbox" disabled ' + (score >= 3 ? 'checked' : '') + ' value="3"/><label for="id_3_"' + id + '>3 stars</label>' +
            '        <input id="id_4_"' + id + ' type="checkbox" disabled ' + (score >= 2 ? 'checked' : '') + ' value="2"/><label for="id_4_"' + id + '>2 stars</label>' +
            '        <input id="id_5_"' + id + ' type="checkbox" disabled ' + (score >= 1 ? 'checked' : '') + ' value="1"/><label for="id_5_"' + id + '>1 stars</label>' +
            '    </fieldset>'
    }


    insertFeedback() {
        let controller = this;
        $('#add-idFilm').val(this.id)
        $('#add-idUser').val(getCookieID())
        let data = $('#insert-form').serialize();
        console.log(data)
        $.post(this.feedbackAdd, data, function () { // waiting for response-
        }).done(function () { // success response-
            // Set success alert.
            controller.renderAlert('Feedback successfully entered.', true);
            // Reset modal form.
            $('#add-feedback').val('');
            // charge new data.
           // window.location.reload();
        }).fail(function (err) { // fail response

            console.log(err)
            alert("ERRORE!!!!")
            controller.renderAlert('Error while inserting. Try again.', false);
        });

    }
}