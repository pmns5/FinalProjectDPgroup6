package models;

import javax.servlet.http.HttpServletRequest;

public class User {
    private int id_user;
    private String username;
    private String email;
    private String password;
    private String role;
    private int ban;


    public User(){

    }

    public User(HttpServletRequest req, boolean add){
        if(!add){
            id_user = Integer.parseInt(req.getParameter("id_user"));
            role = req.getParameter("role");
        }else{
            role = "client";
        }
        username = req.getParameter("username");
        email = req.getParameter("email");
        password = req.getParameter("password");
    }

    public User(int id_user, String username, String email, String password, String role, int ban) {
        this.id_user = id_user;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.ban = ban;
    }

    public User(int id_user, String username, String email, String role, int ban) {
        this.id_user = id_user;
        this.username = username;
        this.email = email;
        this.role = role;
        this.ban = ban;
    }

    public User(int id_user, String username, String email, String password) {
        this.id_user = id_user;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getId_user() {
        return id_user;
    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public int getBan() {
        return ban;
    }
}
