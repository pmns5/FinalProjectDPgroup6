class FilmAdding {
    /**
     * Constructor
     *
     * @param endPoint
     */
    constructor(endPoint) {
        this.viewOneEndPoint = endPoint + "/view-film";
        this.viewAllEndPoint = endPoint + "/getAll"
        this.createEndPoint = endPoint + "/add-film";
        this.editEndPoint = endPoint + "/edit-film";
        this.deleteEndPoint = endPoint + "/delete-film";
        this.actorsEndPoint = endPoint + "/actors";
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
            $('#insert-button').prop('disabled', false);
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

    /**
     * Open Model, download Json data and render
     * @param id the id of the row to edit.
     */
    viewEdit(id) {
        let genreSelect = document.getElementById("edit-genre")
        $('#edit-genre option:not(:first)').remove();
        $.each(Genres, function(index, obj){
            genreSelect.add(new Option(obj, obj));
        })

        $.getJSON(this.viewOneEndPoint, {id: id}, function (obj) {
            $('#edit-id').val(obj.film.id);
            $('#edit-title').val(obj.film.title);
            $('#edit-plot').val(obj.film.plot);
            $('#edit-genre').val(obj.film.genre);
            controller.getActors($('#edit-table-actors'));
            controller.markActors(obj.actors);

        }).done(function () {
            $('#edit-modal').modal('show');
        });
        // Charging
    }

    /**
     * Send edit request and show result alert.
     */
    edit() {
        let controller = this;
        if (validate('#edit-form') === false) {
            controller.renderAlert('Error: The input fields cannot be left empty. Edit rejected', false);
            return;
        }
        $.ajax({
            type: "POST",
            url: controller.editEndPoint,
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

    /**
     * Call delete service and get requested data with get method. Show an alert showing the response.
     */
    delete() {
        let controller = this;
        let id = $('#edit-id').val();

        $.get(this.deleteEndPoint, {id: id}, function () {
            // waiting
        }).done(function () {
            // show alert
            controller.renderAlert('Film successfully deleted.', true);
            // charge new data.
            controller.fillTable();
        }).fail(function () {
            controller.renderAlert('Error while deleting. Try again.', false);
        });
    }

    /**
     * Call insert service and get requested data with post method. Show an alert showing the response.
     */
    insert() {
        let controller = this;
        // if (validate('#insert-form') === false) {
        //     controller.renderAlert('Error: Not all fields have been entered correctly. Please try again', false);
        //     return;
        // }
        $.ajax({
            type: "POST",
            url: controller.createEndPoint,
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

    getActors(modal) {
        modal.find("tr").remove();
        let controller = this;
        $.getJSON(this.actorsEndPoint, function (data) {
            controller.addCheckBoxes(data, modal);
        }).done(function () {
        }).fail(function () {
        });
    }

    insertView() {
        let genreSelect = document.getElementById("add-genre")
        $('#add-genre option:not(:first)').remove();
        $.each(Genres, function(index, obj){
            genreSelect.add(new Option(obj, obj));
        })

        this.getActors($("#insert-table-actors"))
    }

    addCheckBoxes(data, modal) {
        $.each(data, function (index, obj) {
            modal.append('<tr class="list-group-item py-1">' +
                '<td>' +
                '<input type="checkbox" class="form-check-input" name="actors" value="' + obj.id + '">  ' + obj.name + '  ' + obj.surname +
                '</td>' +
                '</tr>');
        });
    }

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

    markActors(actorList){
        console.log("DA AGGIUNGERE: ")
        console.log(actorList)
        $.each(actorList, function(index, obj){
            $(":checkbox").filter(function(x) {
                return this.value === '11';
            }).prop("checked","true");
        });
    }

    constructFilmView(obj) {
        return '<div class="col-sm-4">' +
            '   <div class="our_2">' +
            '       <div class="ih-item square effect5 left_to_right"><a data-toggle="modal" data-target="#edit-modal" onclick="controller.viewEdit(' + obj.film.id + ')">' +
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
