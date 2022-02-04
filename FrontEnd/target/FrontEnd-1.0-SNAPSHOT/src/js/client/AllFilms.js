class AllFilms {
    constructor(endPoint) {
        this.getFilmsHomePageEndPoint = endPoint + "/get-films-home-page"
        this.getFilmsPerGenreEndPoint = endPoint + "/get-films-per-genre"
        this.count = 0;
    }

    fillTable() {
        let controller = this;
        $.getJSON(this.getFilmsHomePageEndPoint, function (data) {
            controller.renderGUI(data);
        }).done(function () {
        }).fail(function () {
            controller.renderAlert('Error while charging data. Retry in a few second.', false);
        });
    }

    renderGUI(data) {
        $('#view .our_2').remove();
        var array = [];
        $.each(data, function (index, obj) {
            if (controller.count === 0) array.push('<div class="col-sm-12 row">');
            array.push(constructFilmView(obj));
            if (controller.count === 2) array.push('</div>')
            controller.count = (controller.count + 1) % 3;
        });
        $('#view').append(array.join(''));
        controller.count = 0;
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
