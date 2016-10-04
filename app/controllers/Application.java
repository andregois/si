package controllers;

import com.avaje.ebean.Ebean;
import models.Arquivo;
import models.Comprimir;
import models.Pasta;
import models.Usuario;
import play.Logger;
import play.data.Form;
import play.mvc.*;
import views.html.*;

import javax.validation.constraints.Past;
import java.util.List;
import java.util.logging.LoggingMXBean;
import java.util.zip.ZipOutputStream;

import static com.avaje.ebean.Expr.*;


import static play.data.Form.form;

public class Application extends Controller {

    private static final String MODO_R = "r";
    private static final String MODO_W = "w";

    public static Result index() {
        Form<Usuario> form = form(Usuario.class);
        return ok(index.render(form));
    }

    public static Result novoUsuario() {

        Form<Usuario> form = form(Usuario.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(cadastro.render(form));
        }
        Usuario usuario = form.get();
        usuario.save();
        return redirect(routes.Application.index());
    }

    public static Result formularioNovoUsuario() {
        Form<Usuario> form = form(Usuario.class);
        return ok(cadastro.render(form));
    }

    //@Security.Authenticated(ActionAuthenticator.class)
    public static Result login() {
        Form<Usuario> form = Form.form(Usuario.class).bindFromRequest();

        Usuario usuario = form.get();
        Usuario user = Ebean.createQuery(Usuario.class).
                where().
                and(eq("username", usuario.getUsername()), eq("password", usuario.getPassword())).findUnique();

        if (user == null) {
            return redirect(routes.Application.index());
        } else {
            session("username", user.getUsername());
            session("id", user.getId());
            session("root", user.getRoot().getId());
            session("trash", user.getTrash().getId());
            return redirect(routes.Application.diretorio());
        }

    }


    public static Result deslogar() {
        session().clear();
        return redirect(routes.Application.index());
    }

    @Security.Authenticated(Secured.class)
    public static Result diretorio() {
        Usuario user = Ebean.createQuery(Usuario.class).where().idEq(session("id")).findUnique();
//        user.getUsername();
        Pasta raiz = Ebean.createQuery(Pasta.class).where().idEq(user.getRoot().getId()).findUnique();

        return ok(diretorio.render(raiz.getFiles(), raiz.getFolders(), user.getSharedWithMe(), user.getSharedReadOnlyWhithMe()));
    }

    @Security.Authenticated(Secured.class)
    public static Result formularioNovoArquivo(String id) {
        Form<Arquivo> form = form(Arquivo.class);
        return ok(criarArquivo.render(form, id));
    }

    @Security.Authenticated(Secured.class)
    public static Result novoArquivo(String id) {//id da pasta
        Form<Arquivo> form = form(Arquivo.class).bindFromRequest();
        Arquivo arquivo = form.get();

        Pasta pasta = Ebean.createQuery(Pasta.class).fetch("files").where().idEq(id).findUnique();
        pasta.getFiles().add(arquivo);
        pasta.update();
        return redirect(routes.Application.pasta(id));

    }

    @Security.Authenticated(Secured.class)
    public static Result formularioEditarArquivo(String id) {
        Form<Arquivo> form = form(Arquivo.class);
        Arquivo arq = Ebean.createQuery(Arquivo.class).where().idEq(id).findUnique();
        return ok(editarArquivo.render(form, id, arq));
    }

    @Security.Authenticated(Secured.class)
    public static Result editarArquivo(String id) {//id da pasta
        String[] postAction = request().body().asFormUrlEncoded().get("action");
        Form<Arquivo> form = form(Arquivo.class).bindFromRequest();
        Arquivo arquivo = form.get();
        Arquivo arq = Ebean.createQuery(Arquivo.class).where().idEq(id).findUnique();
        arq.setName(arquivo.getName());
        arq.setContent(arquivo.getContent());
        arq.setExtension(arquivo.getExtension());
        arq.update();
        String action = postAction[0];
        if ("salvar".equals(action)) {
            return redirect(routes.Application.editarArquivo(id));
        } else {
            return redirect(routes.Application.arquivo(id, MODO_W));
        }
    }



    @Security.Authenticated(Secured.class)
    public static Result arquivo(String id, String modo) {
        Arquivo arq = Ebean.createQuery(Arquivo.class).where().idEq(id).findUnique();
        return ok(arquivo.render(arq, modo));
    }

    @Security.Authenticated(Secured.class)
    public static Result pasta(String id) {

        if (id == null || id.equals(session("root"))) {
            return redirect(routes.Application.diretorio());
        } else {
            Pasta pastas = Ebean.createQuery(Pasta.class).fetch("files").where().idEq(id).findUnique();
            List<Arquivo> arquivos = pastas.getFiles();
            List<Pasta> pastasFilha = pastas.getFolders();
            return ok(pasta.render(arquivos, pastasFilha, id, pastas.getName()));
        }

    }

    @Security.Authenticated(Secured.class)
    public static Result formularioNovaPasta(String id) {

        Form<Pasta> form = form(Pasta.class);
        return ok(criarPasta.render(form, id));
    }


    @Security.Authenticated(Secured.class)
    public static Result novaPasta(String id) {
        Form<Pasta> form = form(Pasta.class).bindFromRequest();

        Pasta pasta = form.get();

        Pasta pastaPai = Ebean.createQuery(Pasta.class).fetch("files").where().idEq(id).findUnique();
        pastaPai.getFolders().add(pasta);
        pastaPai.update();

        return redirect(routes.Application.pasta(id));

    }


    @Security.Authenticated(Secured.class)
    public static Result formularioEditarPasta(String id) {
        Form<Pasta> form = form(Pasta.class);
        Pasta file = Ebean.createQuery(Pasta.class).where().idEq(id).findUnique();
        return ok(RenomearPasta.render(form, id, file));
    }

    @Security.Authenticated(Secured.class)
    public static Result editarPasta(String id) {//id da pasta
        Form<Pasta> form = form(Pasta.class).bindFromRequest();
        Pasta file = form.get();
        Pasta file2 = Ebean.createQuery(Pasta.class).where().idEq(id).findUnique();
        file2.setName(file.getName());
        file2.update();

        return redirect(routes.Application.pasta(id));

    }


    @Security.Authenticated(Secured.class)
    public static Result formularioCompartilhar(String id) {
        Form<Usuario> form = form(Usuario.class);
        return ok(compartilharArquivo.render(form, id));
    }

    @Security.Authenticated(Secured.class)
    public static Result compartilhar(String id) {
        Form<Usuario> form = form(Usuario.class).bindFromRequest();
        Usuario usuario = form.get();
        Usuario user = Ebean.createQuery(Usuario.class).where().eq("username", usuario.getUsername()).findUnique();
        if (user != null) {
            Arquivo arq = Ebean.createQuery(Arquivo.class).where().idEq(id).findUnique();
            arq.setCompartilhado(true);
            if (usuario.getId().equals("w")) {
                arq.getSharedWith().add(user);
            } else if (usuario.getId().equals("r")) {
                arq.getSharedReadOnly().add(user);
            }
            arq.update();
        }
        return redirect(routes.Application.arquivo(id, MODO_W));
    }

    public static Result formularioComprimirZIP(String id) {
        Form<Usuario> form = form(Usuario.class);
        return ok(compartilharArquivo.render(form, id));
    }

    public static Result comprimirArquivosZIP(String id) {
        Arquivo arq = Ebean.createQuery(Arquivo.class).where().idEq(id).findUnique();
        Comprimir comprimir = new Comprimir();

        Arquivo arqTemp = new Arquivo();
        arqTemp.setName(arq.getName());
        arqTemp.setSharedReadOnly(arq.getSharedReadOnly());
        arqTemp.setSharedWith(arq.getSharedWith());
        arqTemp.setExtension("ZIP");
        arqTemp.setPaiID(arq.getPaiID());
        comprimir.setZip(arq.getId());
        arqTemp.setZIP(comprimir.getZip());

        Pasta pasta = Ebean.createQuery(Pasta.class).fetch("files").where().idEq(id).findUnique();
        pasta.getFiles().add(arqTemp);
        pasta.update();
        return redirect(routes.Application.diretorio());
    }

    public static Result comprimirArquivosGZIP(String id) {
        Arquivo arq = Ebean.createQuery(Arquivo.class).where().idEq(id).findUnique();
        Comprimir comprimir = new Comprimir();

        Arquivo arqTemp = new Arquivo();
        arqTemp.setName(arq.getName());
        arqTemp.setSharedReadOnly(arq.getSharedReadOnly());
        arqTemp.setSharedWith(arq.getSharedWith());
        arqTemp.setExtension("GZIP");
        arqTemp.setPaiID(arq.getPaiID());
        comprimir.setGzip(id);
        arqTemp.setGZIP(comprimir.getGzip());

        Pasta pasta = Ebean.createQuery(Pasta.class).fetch("files").where().idEq(id).findUnique();
        pasta.getFiles().add(arqTemp);
        pasta.update();
        return redirect(routes.Application.diretorio());
    }

    public static Result deletarArquivo(String id) {

        Usuario user = Ebean.createQuery(Usuario.class).where().idEq(session("id")).findUnique();
        Arquivo arq = Ebean.createQuery(Arquivo.class).where().idEq(id).findUnique();

        Arquivo arqTemp = new Arquivo();
        arqTemp.setName(arq.getName());
        arqTemp.setContent(arq.getContent());
        arqTemp.setSharedReadOnly(arq.getSharedReadOnly());
        arqTemp.setSharedWith(arq.getSharedWith());
        arqTemp.setExtension(arq.getExtension());
        arqTemp.setPaiID(arq.getPaiID());

        arq.getSharedWith().clear();
        arq.getSharedReadOnly().clear();
        arq.update();

        if (arqTemp.getSharedReadOnly().contains(user)) {
            arqTemp.getSharedReadOnly().remove(user);
        } else if (arqTemp.getSharedWith().contains(user)) {
            arqTemp.getSharedWith().remove(user);
        }

        Pasta lixeira = Ebean.createQuery(Pasta.class).fetch("files").where().idEq(session("trash")).findUnique();
        Logger.info("pasta size - " + lixeira.getFiles().size() + "");
        lixeira.getFiles().add(arqTemp);
        lixeira.update();

        arq.delete();

        return redirect(routes.Application.diretorio());
    }

    public static Result deletarPasta(String id){

        Usuario user = Ebean.createQuery(Usuario.class).where().idEq(session("id")).findUnique();
        Pasta pasta = Ebean.createQuery(Pasta.class).where().idEq(id).findUnique();
        Pasta lixeira = Ebean.createQuery(Pasta.class).fetch("files").where().idEq(session("trash")).findUnique();

        Pasta pastaTemp = new Pasta();
        pastaTemp.setName(pasta.getName());
        lixeira.getFolders().add(pastaTemp);
        lixeira.update();

        limpaPastas(pasta);
        pasta.update();
        pasta.delete();

        return redirect(routes.Application.diretorio());
    }

    public static void limpaArquivo(Arquivo arq1) {
        Arquivo arq = Ebean.createQuery(Arquivo.class).where().idEq(arq1.getId()).findUnique();
        arq.getSharedWith().clear();
        arq.getSharedReadOnly().clear();
        arq.update();
        arq.delete();
    }

    public static void limpaPastas(Pasta pasta1) {
        Pasta pasta = Ebean.createQuery(Pasta.class).fetch("files").where().idEq(pasta1.getId()).findUnique();
        if (pasta.getFolders().size() > 0) {
            for (Pasta p : pasta.getFolders()) {
                Pasta p2 = Ebean.createQuery(Pasta.class).fetch("files").where().idEq(p.getId()).findUnique();
                limpaPastas(p2);
                p2.update();
                p2.delete();
            }
        }
        if (pasta.getFiles().size() > 0) {
            for (Arquivo a: pasta.getFiles()) {
                limpaArquivo(a);
            }

        }
    }

    public static Result limparLixeira(){

        Usuario user = Ebean.createQuery(Usuario.class).where().idEq(session("id")).findUnique();
        Pasta lixeira = Ebean.createQuery(Pasta.class).fetch("files").where().idEq(session("trash")).findUnique();


        limpaPastas(lixeira);

        lixeira.getFiles().clear();
        lixeira.getFolders().clear();

        lixeira.update();

        return redirect(routes.Application.diretorio());
    }
}
