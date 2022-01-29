LOGIN_SERVER = "http://79.46.58.44:8080"

class LoginPage{

    constructor() {
        this.registerEndpoint = LOGIN_SERVER + "/add-user";
        this.accessEndpoint = LOGIN_SERVER + "/login-user";
        this.getUser = LOGIN_SERVER + "/view-user";
        this.editUser = LOGIN_SERVER + "/edit-user";
    }

    registration() {
        let controller = this;
        let data = $('#insert-form').serialize();
        console.log(data)
        $.ajax({
            type:'post',
            url : controller.registerEndpoint,
            data:data,
            // responseType:'application/json',
            // crossDomain: true,
            // headers: [
            //     { "Access-Control-Allow-Origin": '*' },
            //     { "Access-Control-Allow-Headers": 'Origin, X-Requested-With, Content-Type, Accept '},
            //     { "Access-Control-Allow-Methods": "POST, GET, PUT, OPTIONS, DELETE" }
            // ]
        }).done(function (){
            alert("FATTO");
           controller.redirect();
        }).fail(function (){
            alert("FAILED");
        })

    }

    access() {
        let controller = this;
        let data = $('#insert-form').serialize();
        console.log(data)
        $.ajax({
            type:'post',
            url : controller.accessEndpoint,
            data: data,
            traditional: true,
        }).done(function (data){
            alert("LOGIN DONE");
            data = JSON.parse(data)
            console.log(data)
            setCookie(data.id_user, data.role, 365);
            controller.redirect();

        }).fail(function (){
            alert("LOGIN FAILED");
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