class FilmAdding {

    constructor(endPoint) {
        this.addFilmEndPoint = endPoint + "/add-film";
        this.editFilmEndPoint = endPoint + "/edit-film";
        this.deleteFilmEndPoint = endPoint + "/delete-film";
        this.getFilmEndPoint = endPoint + "/get-film";
        this.getFilmsHomePageEndPoint = endPoint + "/get-films-home-page"
        this.getActorsEndPoint = endPoint + "/get-actors";
        this.count = 0;
    }

    fillTable() {
        let controller = this;
        $.getJSON(this.getFilmsHomePageEndPoint, function (data) {
            controller.renderGUI(data);
        }).done(function () {
            $('#insert-button').prop('disabled', false);
        }).fail(function () {
            controller.renderAlert('Error while charging data. Retry in a few second.', false);
        });
    }

    renderGUI(data) {
        $('#view .our_2').remove();
        var array = [];
        let controller = this;
        $.each(data, function (index, obj) {
            if (controller.count === 0) array.push('<div class="col-sm-12 row">');
            array.push(controller.construct(obj));
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

    viewEditFilm(id) {
        let genreSelect = document.getElementById("edit-genre")
        $('#edit-genre option:not(:first)').remove();
        $.each(Genres, function (index, obj) {
            genreSelect.add(new Option(obj, obj));
        })
        $.getJSON(this.getFilmEndPoint, {id: id}, function (obj) {
            $('#edit-id').val(obj.id);
            $('#edit-title').val(obj.title);
            $('#edit-plot').val(obj.plot);
            $('#edit-genre').val(obj.genre);
            controller.getActors($('#edit-table-actors'), true, obj.actorList);
            $('#edit-trailer').val("www.youtube.com/" + obj.trailer);
        }).done(function (obj) {
            $('#edit-modal').modal('show');
        });
    }

    edit() {
        let controller = this;
        if (validate('#edit-form') === false) {
            controller.renderAlert('Error: The input fields cannot be left empty. Edit rejected', false);
            return;
        }
        $.ajax({
            type: "POST",
            url: controller.editFilmEndPoint,
            data: new FormData($('#edit-form')[0]),
            processData: false,
            contentType: false,
            success: function () {
                controller.renderAlert('Film edited successfully.', true);
                controller.fillTable();
            },
            error: function (e) {
                controller.renderAlert('Error while editing. Try again.', false);
            }
        });

    }

    delete() {
        let controller = this;
        let id = $('#edit-id').val();

        $.get(this.deleteFilmEndPoint, {id: id}, function () {
        }).done(function () {
            controller.renderAlert('Film successfully deleted.', true);
            controller.fillTable();
        }).fail(function () {
            controller.renderAlert('Error while deleting. Try again.', false);
        });
    }

    insert() {
        let controller = this;
        // 
        $.ajax({
            type: "POST",
            url: controller.addFilmEndPoint,
            data: new FormData($('#insert-form')[0]),
            processData: false,
            contentType: false,
            success: function () {
                controller.renderAlert('Film added successfully.', true);
                controller.fillTable();
            },
            error: function (e) {
                controller.renderAlert('Error while uploading. Try again.', false);
            }
        });
    }

    insertView() {
        let genreSelect = document.getElementById("add-genre")
        $('#add-genre option:not(:first)').remove();
        $.each(Genres, function (index, obj) {
            genreSelect.add(new Option(obj, obj));
        })

        this.getActors($("#insert-table-actors"), false, null)
    }

    getActors(modal, editing, actorList) {
        modal.find("tr").remove();
        let controller = this;
        $.getJSON(this.getActorsEndPoint, function (data) {
            controller.addCheckBoxes(data, modal, editing, actorList);
        }).done(function () {
        }).fail(function () {
        });
    }

    addCheckBoxes(data, modal, editing, actorList) {
        let listID;
        if (editing) listID = actorList.map(a => a.id);

        $.each(data, function (index, obj) {
            modal.append('<tr class="list-group-item py-1">' +
                '<td>' +
                '<input type="checkbox" class="form-check-input checkbox" name="actors" value="' + obj.id + '" id="Cb' + obj.id + '"' +
                (editing && listID.includes(obj.id) ? 'checked' : '') + '>  ' + obj.name + '  ' + obj.surname +
                '</td>' +
                '</tr>');
        });
    }

    construct(obj) {
        let cutPlot;
        if (obj.film.plot.length > PLOT_LENGTH) {
            cutPlot = obj.film.plot.substr(0, PLOT_LENGTH) + "..."
        } else {
            cutPlot = obj.film.plot
        }
        return '<div class="col-sm-4">' +
            '   <div class="our_2">' +
            '<div class="ih-item square effect5 left_to_right" > ' +
            '<a data-target="#edit-modal" data-toggle="modal" onclick="controller.viewEditFilm(' + obj.film.id + ')">' +
            '           <img src=data:image/jpeg;base64,' + obj.film.poster + ' alt="img" >' +
            '            <div class="info">' +
            '               <h3>' + obj.film.title + '</h3>' +
            '               <p style="font-size: small">' + cutPlot + '</p>' +
            addActors(obj.actors) +
            addStars(obj.avgScore) +
            '            </div>' +
            '       </a></div>' +
            '   </div>' +
            '</div>';
    }
}
