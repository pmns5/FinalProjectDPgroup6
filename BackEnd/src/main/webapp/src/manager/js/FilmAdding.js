class FilmAdding {
    /**
     * Constructor
     *
     * @param endPoint
     */
    constructor(endPoint) {
        this.viewEndPoint = endPoint + "/films";
        this.createEndPoint = endPoint + "/add-film";
        this.editEndPoint = endPoint + "/edit-film";
        this.deleteEndPoint = endPoint + "/delete-film";
    }

    /**
     * Fetch JSON data from the service, then call a function for rendering the View
     *
     * @use renderGUI()
     */
    fillTable() {
        let controller = this;
        /* Call the microservice and evaluate data and result status */
        $.getJSON(this.viewEndPoint, function (data) {

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
        // If table not empty
        $('#table td').remove();

        // Get the html template for table rows
        let staticHtml = $("#table-template").html();

        /* Bind obj data to the template, then append to table body */
        $.each(data, function (index, obj) {
            let row = staticHtml;
            row = row.replace(/{ID}/ig, obj.id);
            row = row.replace(/{Title}/ig, obj.title);
            row = row.replace(/{Plot}/ig, obj.plot);
            row = row.replace(/{Genre}/ig, obj.genre);

            $('#table-rows').append(row);
        });

        /* When empty address-book */
        if (data.length === 0) {
            $("tfoot").html('<tr><th colspan="3">No records</th></tr>');
        } else {
            $("tfoot tr:first").fadeOut(100, function () {
                $("tfoot tr:first").remove();
            })
        }
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
        $.get(this.viewEndPoint, {id: id}, function (data) {
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
     * Open Model, download Json data and render.
     * @param id the id of the row to delete
     */
    deleteView(id) {
        $.get(this.viewEndPoint, {id: id}, function (data) {
            $('#delete-id').val(data.id);
            $('#delete-name').html(data.name);
        }).done(function () {
            $('#delete-modal').modal('show');
        });
    }

    /**
     * Call delete service and get requested data with get method. Show an alert showing the response.
     */
    delete() {
        let controller = this;
        let data = $('#delete-form').serialize();
        $.get(this.deleteEndPoint, data, function () {
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

        let formData = new FormData($("#insert-form")[0]);

        console.log(formData)
        $.ajax({
            url : this.createEndPoint,
            type : 'POST',
            data : formData,
            contentType : false,
            processData : false,
            success: function(resp) {
                // Set success alert.
                controller.renderAlert('Film successfully entered.', true);
                // Reset modal form.
                $('#add-title').val('');
                $('#add-plot').val('');
                // charge new data.
                controller.fillTable();
            }
        });

    }
}

