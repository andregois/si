package models;

import javax.persistence.*;

import com.google.common.collect.Lists;
import play.db.ebean.Model;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Arquivo extends Model {

    @Id @GeneratedValue
    private String id;

    @ManyToMany
    private List<Usuario> sharedWith;

    private String name;
    private String content;

    public Arquivo() {
        this.sharedWith = Lists.newArrayList();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Usuario> getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(List<Usuario> sharedWith) {
        this.sharedWith = sharedWith;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}