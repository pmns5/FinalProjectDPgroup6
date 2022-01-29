class SingleFilm {
    constructor(endPoint, id) {
        this.viewEndPoint = endPoint + "/getFilmDetails";
        this.id = id
        this.showFilm()
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

    getCast(actors) {
        var allActors = [];
        $.each(actors, function (index, actor) {
            allActors.push(actor.name + " " + actor.surname)
        })
        return allActors.join(", ")
    }

    constructFeedbackView(obj) {
        return '<div class="card p-3 mt-2">' +
            '       <div class="d-flex justify-content-between align-items-center">' +
            '           <div class="user d-flex flex-row align-items-center">' +
            '               <img src="../../images/user.jpeg" width="30" class="user-img rounded-circle mr-2" alt="">' +
            '               <span>' +
            '                   <small class="font-weight-bold text-primary">' + obj.id_user + '</small>' +
            '                   <small class="font-weight-bold">' + obj.comment + '</small>' +
            '               </span>' +
            '           </div>' +
            '       <small>3 days ago</small>' +
            '       </div>' +
            '   </div>'
    }

    insertFeedback(){
        executeRating(ratingStars);
        console.log(ratingStars)
    }
}