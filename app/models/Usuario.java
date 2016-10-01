package models;

import com.google.common.collect.Lists;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Usuario extends Model {
    @Id
    @GeneratedValue
    private String id;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    public String username;
    private String password;
//    private String authToken;

//    public static Finder<Integer,Usuario> find = new Finder(Integer.class, Usuario.class);

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "sharedWith",
            targetEntity = Arquivo.class)
    private List<Arquivo> sharedWithMe;

    @ManyToMany(mappedBy = "sharedReadOnly")
    private List<Arquivo> sharedReadOnlyWhithMe;

    @OneToOne(cascade = CascadeType.ALL)
    private Pasta root;

    @OneToOne(cascade = CascadeType.ALL)
    private Pasta trash;

    public Usuario() {
        this.sharedWithMe = Lists.newArrayList();
        this.sharedReadOnlyWhithMe = Lists.newArrayList();
        this.root = new Pasta();
        this.trash = new Pasta();
        this.trash.setName("Lixeira");
        this.root.getFolders().add(this.trash);
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

    public List<Arquivo> getSharedReadOnlyWhithMe() {
        return sharedReadOnlyWhithMe;
    }

    public void setSharedReadOnlyWithMe(List<Arquivo> sharedReadOnlyWithMe) {
        this.sharedReadOnlyWhithMe = sharedReadOnlyWithMe;
    }

    public Pasta getRoot() {
        return root;
    }

    public Pasta getTrash() {
        return trash;
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
                ", sharedReadOnlyWhithMe=" + sharedReadOnlyWhithMe +
                ", root=" + root +
                '}';
    }
}