class ActorsAdding {

    constructor(endPoint) {
        this.addActorEndPoint = endPoint + "/add-actor";
        this.editActorEndPoint = endPoint + "/edit-actor";
        this.deleteActorEndPoint = endPoint + "/delete-actor";
        this.getActorEndPoint = endPoint + "/get-actor";
        this.getActorsEndPoint = endPoint + "/get-actors";
    }

    fillTable() {
        let controller = this;
        $.getJSON(this.getActorsEndPoint, function (data) {
            controller.renderGUI(data);
        }).done(function () {
            $('#insert-button').prop('disabled', false);
        }).fail(function () {
            controller.renderAlert('Error while charging data. Retry in a few second.', false);
        });
    }

    renderGUI(data) {
        $('#table td').remove();
        let staticHtml = $("#table-template").html();
        $.each(data, function (index, obj) {
            let row = staticHtml;
            row = row.replace(/{ID}/ig, obj.id);
            row = row.replace(/{Name}/ig, obj.name);
            row = row.replace(/{Surname}/ig, obj.surname);
            $('#table-rows').append(row);
        });
        if (data.length === 0) {
            $("tfoot").html('<tr><th colspan="3">No records</th></tr>');
        } else {
            $("tfoot tr:first").fadeOut(100, function () {
                $("tfoot tr:first").remove();
            })
        }
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

    viewEdit(id) {
        $.get(this.getActorEndPoint, {id: id}, function (data) {
            $('#edit-id').val(data.id);
            $('#edit-name').val(data.name);
            $('#edit-surname').val(data.surname);
        }).done(function () {
            $('#edit-modal').modal('show');
        });
    }

    edit() {
        let controller = this;
        // 
        let data = $('#edit-form').serialize();
        $.post(this.editActorEndPoint, data, function () {
        }).done(function () {
            controller.renderAlert('Actor edited entered.', true);
            $('#edit-id').val('');
            $('#edit-name').val('');
            $('#edit-surname').val('');
            controller.fillTable();
        }).fail(function () {
            controller.renderAlert('Error while editing. Try again.', false);
        });
    }

    deleteView(id) {
        $.get(this.getActorEndPoint, {id: id}, function (data) {
            $('#delete-id').val(data.id);
            $('#delete-name').html(data.name);
        }).done(function () {
            $('#delete-modal').modal('show');
        });
    }

    delete() {
        let controller = this;
        let data = $('#delete-form').serialize();
        $.get(this.deleteActorEndPoint, data, function () {
        }).done(function () {
            controller.renderAlert('Actor successfully deleted.', true);
        }).always(function () {
            controller.fillTable();
        });
    }

    insert() {
        let controller = this;
        if (validate('#insert-form') === false) {
            controller.renderAlert('Error: Not all fields have been entered correctly. Please try again', false);
            return;
        }
        let data = $('#insert-form').serialize();
        console.log(data)
        $.post(this.addActorEndPoint, data, function () {
        }).done(function () {
            controller.renderAlert('Actor successfully entered.', true);
            $('#add-name').val('');
            $('#add-surname').val('');
            controller.fillTable();
        }).fail(function () {
            controller.renderAlert('Error while inserting. Try again.', false);
        });
    }
}
