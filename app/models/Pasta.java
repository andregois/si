package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Pasta extends Model {

    @Id @GeneratedValue
    private String id;
    private String UserId;
    private String nomeDaPasta;


    public String getPastaId() {
        return UserId;
    }

    public void setPastaId(String userId) {
        UserId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeDaPasta() {
        return nomeDaPasta;
    }

    public void setNomeDaPasta(String nomeDaPasta) {
        this.nomeDaPasta = nomeDaPasta;
    }

}