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
        console.log(data)
        alert("DATI SERIALIZZATI")
        $.post(controller.editEndPoint, data, function () {
        }).done(function (msg) {
            alert("DONE")
            console.log(msg)
            // $(".modal-header").addClass("bg-success");
            // $(".modal-title").html("Success");
            // $("#redirect-to-home").val(1);
            // $("#modal-ajax-response").modal();
            //updateCookieUsername(data.username)
        }).fail(function (msg) {
            alert("FAILED")
            console.log(msg)
            // $(".modal-header").addClass("bg-danger");
            // $(".modal-title").html("Error");
            // $("#redirect-to-home").val(0);
            // $("#ajax-response").html("Forse si deve togliere");
        });

    }

    deleteUser() {
        $.get(this.deleteEndPoint, {id_user: getCookieID()}, function () {
        }).done(function () {
            logout();
        })
    }

}


//
//
//
//
// function openRecord(id) {
//     // For demo purpose we define many service endpoints providing data
//     let microServiceEndpoints = ENDPOINT + "/get-user";
//     $.get(microServiceEndpoints, {id_user: id}, function (data) {
//         console.log(data);
//         let html = $("#form-html").html();
//         $.each(data, function () {
//             html = html.replace(/{Username}/ig, data.username);
//             html = html.replace(/{Email}/ig, data.email);
//             html = html.replace(/{Password}/ig, data.password);
//             html = html.replace(/{id}/ig, getCookieID());
//             html = html.replace(/{role}/ig, data.role);
//         });
//         $("#form-html").html(html);
//         $("#form-html").removeClass("invisible");
//         if (id == 0)
//             $(".btn-action-delete").hide();
//     });
//
// }
//
// function remove_user() {
//     $.get(ENDPOINT + "/delete-user", {id_user: getCookieID()}, function () {
//     }).done(function () {
//         $(".modal-header").addClass("bg-success");
//         $(".modal-title").html("Success");
//         $("#redirect-to-home").val(1);
//         $("#modal-ajax-response").modal();
//         logout()
//
//     }).fail(function (){
//         $(".modal-header").addClass("bg-danger");
//         $(".modal-title").html("Error");
//         $("#ajax-response").html("Error during execute ajax operation");
//     })
//
// }
//
//
//
//
// /**
//  * Internal function used for resetting the modal
//  * #modal-ajax-response
//  */
// function resetModal() {
//     $(".modal-header").removeClass("bg-danger bg-success");
//     $(".modal-title").html("")
//     $("#ajax-response").html("");
//     $("#redirect-to-home").val(1);
// }
//
// /**
//  *  Internal function used for redirecting
//  *  to browse after any record operation
//  */
// function redirectToHome() {
//     let role = getCookieRole();
//     if (role === "manager") {
//         $(location).attr("href", "manager/mainpageManager.html");
//     } else {
//         $(location).attr("href", "client/mainpageClient.html");
//     }
// }
//
// /**
//  * Manage form submission
//  *
//  * @param formData form serialized data
//  * @use External microservice providing record write operation (add, update or delete)
//  */
// function editUser() {
//
//     let formData = $('#edit-form').serialize()
//     // For demo purpose we define many service endpoints providing crud operation
//     let selectedMicroServiceEndpoint = ENDPOINT + "/edit-user";
//     $.post(selectedMicroServiceEndpoint, formData, function() {
//     }).done(function () {
//         $(".modal-header").addClass("bg-success");
//         $(".modal-title").html("Success");
//         $("#redirect-to-home").val(1);
//         $("#modal-ajax-response").modal();
//         updateCookieUsername(formData.username)
//
//     }).fail(function (){
//         $(".modal-header").addClass("bg-danger");
//         $(".modal-title").html("Error");
//         $("#redirect-to-home").val(0);
//         $("#ajax-response").html("Forse si deve togliere");
//     })
// }
//
//
