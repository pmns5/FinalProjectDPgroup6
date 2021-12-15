class AllFilms {
    /**
     * Constructor
     *
     * @param endPoint
     */
    constructor(endPoint) {
        this.viewAllEndPoint = endPoint + "/getAll"
        this.viewByGenre = endPoint + "/getPerGenre"
        this.count = 0;
    }

    /**
     * Fetch JSON data from the service, then call a function for rendering the View
     *
     * @use renderGUI()
     */
    fillTable() {
        let controller = this;
        /* Call the microservice and evaluate data and result status */
        $.getJSON(this.viewAllEndPoint, function (data) {
            controller.renderGUI(data);
        }).done(function () {
            //controller.renderAlert('Data charged successfully.', true);
        }).fail(function () {
            controller.renderAlert('Error while charging data. Retry in a few second.', false);
        });
    }

    /**
     * Render the given JSON data into GUI static design
     *
     * @param data a JSON representation of data
     */
    renderGUI(data) {
        $('#view .our_2').remove();
        var array = [];
        $.each(data, function (index, obj) {
            if (controller.count === 0) array.push('<div class="col-sm-12 row">');
            array.push(controller.constructFilmView(obj));
            if (controller.count === 2) array.push('</div>')
            controller.count = (controller.count + 1) % 3;
        });
        $('#view').append(array.join(''));
        controller.count = 0;
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

    addActors(actorList) {
        let str = '';
        str += '<h6 class="filmText" style="color: aquamarine">';
        $.each(actorList, function (index, obj) {
            str += ' ' + obj.name + ' ' + obj.surname + ',';
        });
        str = str.substring(0, str.length - 1);
        str += '</h6>';
        return str;
    }

    constructFilmView(obj) {
        return '<div class="col-sm-4">' +
            '   <div class="our_2">' +
            '       <div class="ih-item square effect5 left_to_right"><a href="../../html/client/SingleFilm.html?id='+obj.film.id+ '">' +
            '           <div class="img"><img src=data:image/jpeg;base64,' + obj.film.poster + ' alt="img" >' + ' </div>' +
            '            <div class="info">' +
            '               <h3>' + obj.film.title + '</h3>' +
            '               <p>' + obj.film.plot + '</p>' +
            controller.addActors(obj.actors) +
            '            </div>' +
            '       </a></div>' +
            '   </div>' +
            '</div>';
    }
}
