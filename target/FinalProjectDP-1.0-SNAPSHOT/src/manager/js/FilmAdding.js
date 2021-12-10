class FilmAdding {
    /**
     * Constructor
     *
     * @param endPoint
     */
    constructor(endPoint) {
        this.viewOneEndPoint = endPoint + "/getFilm";
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
        $.get(this.viewOneEndPoint, {id: id}, function (data) {
            $('#edit-id').val(data.id);
            $('#edit-title').val(data.title);
            $('#edit-plot').val(data.plot);
            $('#edit-genre').val(data.genre);
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
        let data = $('#edit-form').serialize();
        $.post(this.editEndPoint, data, function () {
            // waiting
        }).done(function () {
            // show alert
            controller.renderAlert('Film edited entered.', true);
            // success
            $('#edit-id').val('');
            $('#edit-title').val('');
            $('#edit-plot').val('');
            $('#edit-genre').val('');
            controller.fillTable();
        }).fail(function () {
            controller.renderAlert('Error while editing. Try again.', false);
        });
    }

    /**
     * Call delete service and get requested data with get method. Show an alert showing the response.
     */
    delete() {
        let controller = this;
        let id = $('#edit-id').val();
        $.get(this.deleteEndPoint, {id:id}, function () {
            // waiting
        }).done(function () {
            // show alert
            controller.renderAlert('Film successfully deleted.', true);
            // charge new data.
            controller.fillTable();
            controller.unselect();
        }).fail(function () {
            controller.renderAlert('Error while deleting. Try again.', false);
        });
    }

    /**
     * Call insert service and get requested data with post method. Show an alert showing the response.
     */
    insert() {
        let controller = this;

        if (validate('#insert-form') === false) {
            controller.renderAlert('Error: Not all fields have been entered correctly. Please try again', false);
            return;
        }

        const form = $('#insert-form')[0];
        let data = new FormData(form);
        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: controller.createEndPoint,
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 5000,
            success: function (data) {
                controller.renderAlert('Film added successfully.', true);
                controller.fillTable();
                // create an image
                var outputImg = document.createElement('img');
                outputImg.src = 'data:image/jpeg;base64,' + data;
                document.body.appendChild(outputImg);
                //controller.addFilmView($('#view'), data)

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
        this.getActors($("#insert-table-actors"))
    }

    addCheckBoxes(data, modal) {
        $.each(data, function (index, obj) {
            modal.append('<tr class="list-group-item py-1">' +
                '<td>' +
                '<input type="checkbox" class="checkbox" name="actors" value=' + obj.id + '>  ' + obj.name + '  ' + obj.surname +
                '</td>' +
                '</tr>');
        });
    }

    addActors(actorList) {
        let str = '';
        str += '<h4 class="filmText">';
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
            '       <div class="ih-item square effect5 left_to_right"><a data-toggle="modal" data-target="#edit-modal" onclick="controller.viewEdit(obj.id)">' +
            '           <div class="img"><img src=data:image/jpeg;base64,' + obj.poster + ' alt="img" >' + ' </div>' +
            '            <div class="info">' +
            '               <h3>' + obj.title + '</h3>' +
            '               <p>' + obj.plot + '</p>' +
            controller.addActors(obj.actors) +
            '            </div>' +
            '       </a></div>' +
            '   </div>' +
            '</div>';
    }
}


