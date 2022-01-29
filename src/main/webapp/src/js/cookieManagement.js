function setCookie(id, role, exdays) {
    const d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    let expires = d.toUTCString();
    console.log("ID : ", id, "    ROLE: ", role);
    document.cookie = "role=" + role + ";id_user=" + id + ";expires=" + expires + ";path=/";
}

function getCookieID() {
    let ca = document.cookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let split = ca[i].split("=");
        if (split[0] === "id_user") {
            return split[1];
        }
    }
    return null;
}

function getCookieRole() {
    let ca = document.cookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let split = ca[i].split("=");
        if (split[0].trim() === "role") {
            return split[1];
        }
    }
    return null;
}

function checkCookie(role) {
    if (getCookieRole()!== role){
        alert("NOT ALLOWED");
        logout();
    }
}

function logout(){
    $(location).attr("href", "../loginPage.html");
    document.cookie = "role=" + null + ";id_user=" + null + ";expires=" + 0 + ";path=/";
}