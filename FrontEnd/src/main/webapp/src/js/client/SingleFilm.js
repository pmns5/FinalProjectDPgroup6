class SingleFilm {
    constructor(endPoint, id) {
        this.addFeedbackEndPoint = endPoint + "/add-feedback";
        this.editFeedbackEndpoint = endPoint + "/edit-feedback"
        this.deleteFeedbackEndpoint = endPoint + "/delete-feedback"
        this.getFilmReviewPageEndPoint = endPoint + "/get-film-review-page";
        this.id = id
        this.countFeedback = 0;
    }

    showFilm() {
        let controller = this
        $.getJSON(this.getFilmReviewPageEndPoint, {id: this.id}, function (obj) {
            $('#title').text(obj.title);
            $('#genre').text(obj.genre);
            $('#plot').text(obj.plot);
            $('#filmPoster').attr("src", "data:image/jpeg;base64," + obj.poster);
            $('#trailerFilm').attr("src", "https://www.youtube.com/embed/" + obj.trailer);
            $('#cast').text(controller.getCast(obj.actors));
            $.each(obj.feedbacks, function (index, obj) {
                controller.countFeedback += 1;
                if (obj.username === getCookieUsername()) {
                    controller.insertViewFeedback(obj);
                } else {
                    let feedback = controller.constructFeedbackView(obj);
                    $('#feedbackList').append(feedback)
                }
            });
            $(".rating-upper").css({
                width: obj.avgScore * 20 + "%"
            });
            $('#number_feedback').text("(" + controller.countFeedback + ")");
        });
    }

    constructFeedbackView(obj) {
        return '<div class="card p-3 mt-2">' +
            '       <div class="d-flex justify-content-between align-items-center">' +
            '           <div class="user d-flex flex-row align-items-center">' +
            '               <img src="../../images/user.jpeg" width="30" class="user-img rounded-circle mr-2" alt="">' +
            '               <span>' +
            '                   <small class="font-weight-bold text-primary">' + obj.username + '</small>' +
            '                   <small class="font-weight-bold">' + String(obj.comment) + '</small>' +
            '               </span>' +
            this.addStars(obj.score) +
            '           </div>' +
            '       <small>' + obj.date + '</small>' +
            '       </div>' +
            '   </div>'
    }

    getCast(actors) {
        var allActors = [];
        $.each(actors, function (index, actor) {
            allActors.push(actor.name + " " + actor.surname)
        })
        return allActors.join(", ")
    }

    addStars(score) {
        return '<fieldset class="rating">' +
            '       <input id="id_1_"' + this.countFeedback + ' type="checkbox" disabled ' + (score >= 5 ? 'checked' : '') + ' value="5"/><label for="id_1_"' + this.countFeedback + '>5 stars</label>' +
            '       <input id="id_2_"' + this.countFeedback + ' type="checkbox" disabled ' + (score >= 4 ? 'checked' : '') + ' value="4"/><label for="id_2_"' + this.countFeedback + '>4 stars</label>' +
            '       <input id="id_3_"' + this.countFeedback + ' type="checkbox" disabled ' + (score >= 3 ? 'checked' : '') + ' value="3"/><label for="id_3_"' + this.countFeedback + '>3 stars</label>' +
            '       <input id="id_4_"' + this.countFeedback + ' type="checkbox" disabled ' + (score >= 2 ? 'checked' : '') + ' value="2"/><label for="id_4_"' + this.countFeedback + '>2 stars</label>' +
            '       <input id="id_5_"' + this.countFeedback + ' type="checkbox" disabled ' + (score >= 1 ? 'checked' : '') + ' value="1"/><label for="id_5_"' + this.countFeedback + '>1 stars</label>' +
            '   </fieldset>'
    }

    insertViewFeedback(obj) {
        $('#insert-button').prop('hidden', true)
        let row = $("#my-feedback-area-template").html();
        row = row.replace(/{USERNAME}/gi, obj.username);
        row = row.replace(/{COMMENT}/gi, obj.comment);
        $('#edit-feedback').val(obj.comment)
        row = row.replace(/{DATE}/gi, obj.date);
        row = row.replace(/{SCORE}/gi, obj.score)
        $("#my-feedback-area").append(row);
        $("#my-feedback-area :checkbox[value=" + obj.score + "]").prop("checked", "true");
    }

    viewEditFeedback(score) {
        $('#edit-idUser').val(getCookieID());
        $('#edit-idFilm').val(controller.id)
        $('#edit-feedback').val()
        $('#edit-star' + score).prop("checked", "true")
    }

    insertFeedback() {
        let controller = this;
        $('#add-idFilm').val(this.id)
        $('#add-idUser').val(getCookieID())
        let data = $('#insert-form').serialize();
        $.post(this.addFeedbackEndPoint, data, function () {
        }).done(function () {
            controller.renderAlert('Feedback successfully inserted.', true);
            $('#add-feedback').val('');
            window.location.reload();
        }).fail(function () {
            controller.renderAlert('Error while inserting!! Try again.', false);
        });
    }

    editFeedback() {
        let controller = this;
        //
        let data = $('#edit-form').serialize();
        $.post(this.editFeedbackEndpoint, data, function () {
        }).done(function () {
            controller.renderAlert('Feedback successfully edited.', true)
            window.location.reload(true);
        }).fail(function () {
            controller.renderAlert('Error while editing!! Try again.', false);
        });
    }

    deleteFeedback() {
        let controller = this;
        $.get(this.deleteFeedbackEndpoint, {id_film: controller.id, id_user: getCookieID()}, function () {
        });
        window.location.reload(true);
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
}