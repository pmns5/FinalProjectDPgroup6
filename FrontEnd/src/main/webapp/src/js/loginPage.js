class LoginPage{

    constructor(endpoint) {
        this.registerEndpoint = endpoint + "/add-user";
        this.accessEndpoint = endpoint + "/login-user";
        this.getUser = endpoint + "/view-user";
        this.editUser = endpoint + "/edit-user";
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

    registration() {
        let controller = this;
        let data = $('#registration-form').serialize();
        $.ajax({
            type:'post',
            url : controller.registerEndpoint,
            data:data,
        }).done(function (){
            controller.renderAlert('Sign Up Successful', true);
        }).fail(function (){
            controller.renderAlert('Error: Sign Up Failed', false);
        })

    }

    access() {
        let controller = this;
        let data = $('#insert-form').serialize();
        $.ajax({
            type:'post',
            url : controller.accessEndpoint,
            data: data,
            traditional: true,
        }).done(function (data){
            console.log(data)
            data = JSON.parse(data)
            setCookie(data, 1);
            controller.redirect();

        }).fail(function (){
            controller.renderAlert("Error during Login. Incorrect Fields", false);
        })
    }

    redirect(){
        let role = getCookieRole();
        if (role === "manager"){
            $(location).attr("href", "manager/mainpageManager.html");
        }else {
            $(location).attr("href", "client/mainpageClient.html");
        }
    }
}