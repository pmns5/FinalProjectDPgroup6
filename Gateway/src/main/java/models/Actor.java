package models;

import APIGateway.Util;

import javax.servlet.http.HttpServletRequest;

public class Actor {
    private int id;
    private String name;
    private String surname;

    public Actor(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public Actor (){

    }

    public Actor(HttpServletRequest req, boolean add) throws Exception{
        if(!add) this.id = Integer.parseInt(req.getParameter("id"));
        this.name = Util.validate(req.getParameter("name"));
        this.surname = Util.validate(req.getParameter("surname"));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}