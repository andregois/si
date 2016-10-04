package models;

import javax.persistence.*;

import com.google.common.collect.Lists;
import play.db.ebean.Model;

import java.util.List;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipOutputStream;

@Entity
public class Arquivo extends Model {

    @Id @GeneratedValue
    private String id;

    private String paiID;

    @ManyToMany(targetEntity = Usuario.class,
                cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "SharedRW", joinColumns =@JoinColumn(name = "sharedrw_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "userRW_id"))
    private List<Usuario> sharedWith;

    @ManyToMany(targetEntity = Usuario.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "SharedR", joinColumns =@JoinColumn(name = "sharedr_id",referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "userR_id"))
    private List<Usuario> sharedReadOnly;


    private String name;
    private String content;
    private boolean compartilhado;
    private String extension;
    private ZipOutputStream ZIP;
    private GZIPOutputStream GZIP;

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

    public String getPaiID() {
        return paiID;
    }

    public void setPaiID(String paiID) {
        this.paiID = paiID;
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

    public void setZIP(ZipOutputStream zip){ this.ZIP = zip; }

    public ZipOutputStream getZIP(){return ZIP;}

    public void setGZIP(GZIPOutputStream gzip){ this.GZIP = gzip; }

    public GZIPOutputStream getGZIP(){return GZIP;}

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