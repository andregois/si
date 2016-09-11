package models;

import javax.persistence.*;

import com.google.common.collect.Lists;
import play.db.ebean.Model;

import java.util.List;

@Entity
public class Arquivo extends Model {

    @Id @GeneratedValue
    private String id;

    @ManyToMany
    private List<Usuario> sharedWith;

    @ManyToMany
    private List<Usuario> sharedReadOnly;


    private String name;
    private String content;
    private boolean compartilhado;
    private String extension;

    public Arquivo() {
        this.sharedWith = Lists.newArrayList();
        this.sharedReadOnly = Lists.newArrayList();
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

    public List<Usuario> getSharedReadOnly() {
        return sharedReadOnly;
    }

    public void setSharedReadOnly(List<Usuario> sharedReadOnly) {
        this.sharedReadOnly = sharedReadOnly;
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

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public boolean getCompartilhado() {return compartilhado;}

    public void setCompartilhado(boolean compartilhado){ this.compartilhado = compartilhado; }
    @Override
    public String toString() {
        return "Arquivo{" +
                "id='" + id + '\'' +
                ", sharedWith = " + sharedWith +
                ", name = '" + name + '\'' +
                ", content = '" + content + '\'' +
                ", extension = '" + extension + '\'' +
                '}';
    }
}