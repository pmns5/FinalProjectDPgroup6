class UserEdit {
    constructor(endPoint) {
        this.editEndPoint = endPoint + "/edit-user";
        this.deleteEndPoint = endPoint + "/delete-user";
        this.getEndPoint = endPoint + "/get-user";
    }

    fillTable() {
        let controller = this;
        $.getJSON(this.getEndPoint, {id_user: getCookieID()}, function (data) {
            $('#id_user').val(getCookieID());
            $('#role').val(getCookieRole())
            $('#password').val(data.password)
            $('#email').val(data.email)
            $('#username').val(data.username)
        });
    }

    editUser() {
        let controller = this;
        let data = $('#entry-form').serialize();
        $.post(controller.editEndPoint, data, function () {
        }).done(function (msg) {
            updateCookieUsername(data.username)
            controller.renderAlert("Account successfully edit")
            $(location).attr("href", "./index.html");
            document.cookie = "data=" + null + ";expires=" + 0 + ";path=/";
        }).fail(function (msg) {
            controller.renderAlert("Error while editing this account!! Try again.")
        });
    }

    deleteUser() {
        $.get(this.deleteEndPoint, {id_user: getCookieID()}, function () {
        }).done(function () {
            $(location).attr("href", "./index.html");
            document.cookie = "data=" + null + ";expires=" + 0 + ";path=/";
        }).fail(function (msg) {
            controller.renderAlert("Error while deleting this account!! Try again.")
        });
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