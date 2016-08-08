package models;

import javax.persistence.*;
import play.db.ebean.Model;

@Entity
public class Arquivo extends Model {

    @Id @GeneratedValue
    private String id;
    private String UserId;
    private String PastaId;
    private String nomeDoArquivo;
    private String conteudo;

    public String getPastaId() {
        return PastaId;
    }

    public void setPastaId(String pastaId) {
        PastaId = pastaId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeDoArquivo() {
        return nomeDoArquivo;
    }

    public void setNomeDoArquivo(String nomeDoArquivo) {
        this.nomeDoArquivo = nomeDoArquivo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}