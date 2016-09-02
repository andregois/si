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

//        if (form.hasErrors()) {
//            return badRequest(cadastro.render(form));
//        }
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
        Logger.info("Acessando Diretorio");
        Usuario user = Ebean.createQuery(Usuario.class).where().idEq(session("id")).findUnique();
        Logger.info("User" + user.toString());
        Pasta raiz = Ebean.createQuery(Pasta.class).where().idEq(user.getRoot().getId()).findUnique();
        return ok(diretorio.render(raiz.getFiles(), raiz.getFolders()));
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
    public static Result pasta(String id) {

        if (id == null || id.equals(session("root"))) {
            return redirect(routes.Application.diretorio());
        } else {
            Pasta pastas = Ebean.createQuery(Pasta.class).fetch("files").where().idEq(id).findUnique();
            Logger.info("Pasta: " + pastas.toString());
            List<Arquivo> arquivos = pastas.getFiles();
            return ok(pasta.render(arquivos, id));
        }

    }


/*
    @Security.Authenticated(Secured.class)
    public static Result novoArquivo() {
        Form<Arquivo> form = form(Arquivo.class).bindFromRequest();
        Arquivo arquivo = form.get();

//        Compartilhado comp = new Compartilhado();
//        comp.setId(arquivo.getId());
//        comp.setUser(arquivo.getDono());
//        arquivo.compartilhar(comp);
//        Logger.info("Criando donos" + arquivo.getCompartilhado().toString());
//        comp.save();
//        arquivo.save();
//        Logger.info("Salvou" + arquivo.toString());
//        if (arquivo.getPastaId().equals("main")) {
//            return redirect(routes.Application.diretorio());
//        } else {
        return redirect(routes.Application.pasta(arquivo.getPastaId()));
//        }
    }


    @Security.Authenticated(Secured.class)
    public static Result formularioNovaPasta() {
        Form<Pasta> form = form(Pasta.class);
        return ok(criarPasta.render(form));
    }


    @Security.Authenticated(Secured.class)
    public static Result arquivo(String id) {
        return ok(arquivo.render(getArquivoPorId(id)));
    }


    private static Arquivo getArquivoPorId(String id) {
//        List<Arquivo> listaArquivos = Ebean.createQuery(Arquivo.class).findList();
//        for (Arquivo a : listaArquivos) {
//            if (a.getId().equals(id)) {
//                return a;
//            }
//        }
        return null;
    }





    @Security.Authenticated(Secured.class)
    public static Result novoArquivo() {
//        Form<Arquivo> form = form(Arquivo.class).bindFromRequest();
//
//        Arquivo arquivo = form.get();
//        Compartilhado comp = new Compartilhado();
//        comp.setId(arquivo.getId());
//        comp.setUser(arquivo.getDono());
//        arquivo.compartilhar(comp);
//        Logger.info("Criando donos" + arquivo.getCompartilhado().toString());
//        comp.save();
//        arquivo.save();
//        Logger.info("Salvou" + arquivo.toString());
//        if (arquivo.getPastaId().equals("main")) {
//            return redirect(routes.Application.diretorio());
//        } else {
        return redirect(routes.Application.pasta(arquivo.getPastaId()));
//        }
    }

    private static Usuario getUsuarioPorUsername(String username) {
//        List<Usuario> listaUsuario = Ebean.createQuery(Usuario.class).findList();
//        for (Usuario u : listaUsuario) {
//            if (u.getUsername().equals(username)) {
//                return u;
//            }
//        }
        return null;
    }

    @Security.Authenticated(Secured.class)
    public static Result novaPasta() {
//        Form<Pasta> form = form(Pasta.class).bindFromRequest();
//        if (form.hasErrors()) {
//            return badRequest(criarPasta.render(form));
//        }
//        Pasta pasta = form.get();
//        pasta.save();
        return redirect(routes.Application.diretorio());
    }


    @Security.Authenticated(Secured.class)
    public static Result formularioCompartilhar(String id) {
//        Form<Compartilhado> form = form(Compartilhado.class);
        return ok(compartilharArquivo.render(form, id));
    }

    @Security.Authenticated(Secured.class)
    public static Result compartilhar() {
//        Form<Compartilhado> form = form(Compartilhado.class).bindFromRequest();
//        Compartilhado comp = form.get();
//        Arquivo arq = getArquivoPorId(comp.getId());
//        Usuario usr = getUsuarioPorUsername(comp.getUser());
//        if(usr != null) {
//            arq.compartilhar(comp);
//        }
//        comp.save();
//        arq.save();
        return redirect(routes.Application.diretorio());
    }


//
//        if (form.hasErrors()) {
//            return badRequest(cadastro.render(form));
//        }
//        Usuario a = form.get();
//        Usuario b = Ebean.createQuery(Usuario.class).where().eq("username", a.getUsername()).findUnique();
//
//        if (b != null){
//            return redirect(routes.Application.diretorio());
//        } else {
//            return redirect(routes.Application.index());
//        }

//        Form<Arquivo> form = Form.form(Arquivo.class);
//        return ok(diretorio.render(form))


*/

}