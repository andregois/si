package controllers;

import com.avaje.ebean.Ebean;
import models.Arquivo;
import models.Pasta;
import models.Usuario;
import play.Logger;
import play.data.Form;
import play.mvc.*;
import views.html.*;

import javax.validation.constraints.Past;
import java.util.List;

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
        Logger.info("Novo Usuario Cadastrado: " + usuario.toString());
        usuario.save();
        return redirect(routes.Application.index());
    }

    public static Result formularioNovoUsuario() {
        Form<Usuario> form = form(Usuario.class);
        return ok(cadastro.render(form));
    }


    public static Result login() {
        Form<Usuario> form = Form.form(Usuario.class).bindFromRequest();

        Usuario usuario = form.get();
        Logger.info("Logando usuario: " + usuario.toString());
        Usuario user = Ebean.createQuery(Usuario.class).
                where().
                and(eq("username", usuario.getUsername()), eq("password", usuario.getPassword())).findUnique();

        if (user == null) {
            return redirect(routes.Application.index());
        } else {
            Logger.info("Usuario Logado : " + usuario.toString());
            session("username", user.getUsername());
            session("id", user.getId());
            session("root", user.getRoot().getId());
            Logger.info("Usuario Logado 2 : " + user.toString());
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
        Logger.info("Pasta: " + pasta.toString());
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
        Logger.info("Arquivo concluir: " + arq.toString());
        String action = postAction[0];
        Logger.info("Arquivo concluir: " + action);
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
            Logger.info("Pasta: " + pastas.toString());
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
        Logger.info("PastaPai : " + pastaPai.toString());
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
        Logger.info("Pasta: " + file2.toString());

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
                Logger.info("Tipo de compartilhamento: " + usuario.getId());
            } else if (usuario.getId().equals("r")) {
                arq.getSharedReadOnly().add(user);
                Logger.info("Tipo de compartilhamento: " + usuario.getId());
            }
            arq.update();
        }
        Logger.info("Compartilhando: " + usuario.toString());
        return redirect(routes.Application.arquivo(id, MODO_W));
    }

}