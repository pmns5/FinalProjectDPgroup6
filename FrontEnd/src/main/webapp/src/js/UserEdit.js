function openRecord(id) {

    // For demo purpose we define many service endpoints providing data
    let microServiceEndpoints = ENDPOINT + "/get-user";

    $.get(microServiceEndpoints, {id_user: id}, function (data) {
        console.log(data);
        let html = $("#form-html").html();
        $.each(data, function () {
            html = html.replace(/{Username}/ig, data.username);
            html = html.replace(/{Email}/ig, data.email);
            html = html.replace(/{Password}/ig, data.password);
            html = html.replace(/{id}/ig, data.id_user);
            html = html.replace(/{role}/ig, data.role);
        });
        $("#form-html").html(html);
        $("#form-html").removeClass("invisible");
        if (id == 0)
            $(".btn-action-delete").hide();
    });

}

/**
 * Action when submitting form for update
 * the record or adding a new one
 */
$("body").on("click", ".btn-action-submit", function () {
    var currentValidity = $("form")[0].checkValidity();
    if (currentValidity) {
        let formData = $("form").serialize();
        resetModal();
        postData(formData);
    } else {
        $("form")[0].reportValidity();
    }
    return false;
});

/**
 * Action when submitting form for deleting a record
 *
 */
$("body").on("click", ".btn-action-delete", function () {
    let okDelete = confirm("Confirm deletion?");
    if (okDelete) {
        $("#operation").val("delete");
        let formData = $("form").serialize();
        resetModal();
        postData(formData);
    }
    return false
});

/**
 * Internal function used for resetting the modal
 * #modal-ajax-response
 */
function resetModal() {
    $(".modal-header").removeClass("bg-danger bg-success");
    $(".modal-title").html("")
    $("#ajax-response").html("");
    $("#redirect-to-home").val(1);
}

/**
 * Actions when closing the modal #modal-ajax-response
 *
 */
$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
    if ($("#redirect-to-home").val() == 1)
        redirectToHome();
    $(window).scrollTop(0);
});

/**
 *  Internal function used for redirecting
 *  to browse after any record operation
 */
function redirectToHome() {
    let role = getCookieRole();
    if (role === "manager") {
        $(location).attr("href", "manager/mainpageManager.html");
    } else {
        $(location).attr("href", "client/mainpageClient.html");
    }
}

/**
 * Manage form submission
 *
 * @param formData form serialized data
 * @use External microservice providing record write operation (add, update or delete)
 */
function postData(formData) {

    // For demo purpose we define many service endpoints providing crud operation
    let selectedMicroServiceEndpoint = ENDPOINT + "/edit-user";
    $.ajax({
        type: "POST",
        url: selectedMicroServiceEndpoint,
        data: formData,
        dataType: "html",
        success: function () {
            $(".modal-header").addClass("bg-success");
            $(".modal-title").html("Success");
            $("#redirect-to-home").val(1);
            $("#modal-ajax-response").modal();
        },
        error: function () {
            $(".modal-header").addClass("bg-danger");
            $(".modal-title").html("Error");
            $("#redirect-to-home").val(0);
            $("#ajax-response").html("Error during execute ajax operation");
        }
    });
}


/**
 *  When document ready init address book record
 *
 *  @uses openRecord(), getCurrentRecord()
 */

let debug = false;
$(document).ready(function () {

    if (!debug)
        openRecord(getCookieID());
});