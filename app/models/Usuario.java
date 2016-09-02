package models;

import com.google.common.collect.Lists;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario extends Model {
    @Id @GeneratedValue
    private String id;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    private String password;

    @ManyToMany(mappedBy = "sharedWith")
    private List<Arquivo> sharedWithMe;

    @OneToOne(cascade = CascadeType.ALL)
    private Pasta root;

    public Usuario() {
        this.sharedWithMe = Lists.newArrayList();
        this.root = new Pasta();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Arquivo> getSharedWithMe() {
        return sharedWithMe;
    }

    public void setSharedWithMe(List<Arquivo> sharedWithMe) {
        this.sharedWithMe = sharedWithMe;
    }

    public Pasta getRoot() {
        return root;
    }

    public void setRoot(Pasta root) {
        this.root = root;
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sharedWithMe=" + sharedWithMe +
                ", root=" + root +
                '}';
    }
}