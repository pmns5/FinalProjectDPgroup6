package userAPI.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="User")
@XmlAccessorType(XmlAccessType.NONE)
public class User {
    @XmlAttribute
    private int id_user;
    @XmlAttribute
    private String username;
    @XmlAttribute
    private String email;
    @XmlAttribute
    private String password;
    @XmlAttribute
    private String role;
    @XmlAttribute
    private int ban;


    public User(){

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
