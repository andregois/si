package models;

import com.google.common.collect.Lists;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Pasta extends Model {

    @Id @GeneratedValue
    private String id;
    private String name;

//    @OneToMany(cascade = CascadeType.ALL)
    private List<Pasta> folders;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Arquivo> files;

    public Pasta() {
        this.folders = Lists.newArrayList();
        this.files = Lists.newArrayList();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Pasta> getFolders() {
        return folders;
    }

    public void setFolders(List<Pasta> folders) {
        this.folders = folders;
    }

    public List<Arquivo> getFiles() {
        return files;
    }

    public void setFiles(List<Arquivo> files) {
        this.files = files;
    }
}