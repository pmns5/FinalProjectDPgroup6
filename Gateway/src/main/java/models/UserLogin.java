package models;

import javax.servlet.http.HttpServletRequest;

public class UserLogin{
    private String user;
    private String password;

    public UserLogin(){

    }

    public UserLogin(HttpServletRequest req){

        user = req.getParameter("user");
        password = req.getParameter("password");
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}