class LoginPage {

    constructor(endpoint) {
        this.addUserEndpoint = endpoint + "/add-user";
        this.editUserEndPoint = endpoint + "/edit-user";
        this.loginUserEndpoint = endpoint + "/login-user";
        this.getUserEndPoint = endpoint + "/get-user";

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

    registration() {
        let controller = this;
        let data = $('#register-form').serialize();
        $.ajax({
            type: 'post',
            url: controller.addUserEndpoint,
            data: data,
        }).done(function () {
            controller.renderAlert('Sign Up Successful', true);
        }).fail(function () {
            controller.renderAlert('Error: Sign Up Failed', false);
        })

    }

    access() {
        let controller = this;
        let data = $('#login-form').serialize();
        $.ajax({
            type: 'post',
            url: controller.loginUserEndpoint,
            data: data,
            traditional: true,
        }).done(function (data) {
            data = JSON.parse(data)
            setCookie(data, 1);
            controller.redirect();

        }).fail(function () {
            controller.renderAlert("Error during Login. Incorrect Fields", false);
        })
    }

    redirect() {
        let role = getCookieRole();
        if (role === "manager") {
            $(location).attr("href", "manager/mainpageManager.html");
        } else {
            $(location).attr("href", "client/mainpageClient.html");
        }
    }
}