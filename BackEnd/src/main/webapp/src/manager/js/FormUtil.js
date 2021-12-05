function validate(idForm) {
    $(idForm).addClass("needs-validation")
    let flag = true
    let forms = document.getElementsByClassName("needs-validation");
    Array.prototype.filter.call(forms, function (form) {
        if (form.checkValidity() === false) {
            flag = false
        }
    }, false)
    $(idForm).removeClass("needs-validation")
    return flag
}

function addFilmView(modal) {
    modal.append('<div class="col-sm-4">' +
        '<div class="our_2">' +
        ' <div class="ih-item square effect5 left_to_right"><a href="#">' +
        ' <div class="img"><img src="../images/image.jpeg" alt="img"></div>' +
        '  <div class="info">' +
        '<h3>MIO</h3>' +
        '   <p>mauris.</p>' +
        ' </div>' +
        ' </a></div>' +
        ' </div>' +
        ' </div>');
}


