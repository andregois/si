package controllers;

import com.avaje.ebean.Ebean;
import models.Arquivo;
import models.Usuario;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;

import java.util.ArrayList;
import java.util.List;

public class Application extends Controller {


    public static Result index() {
//        List<Usuario> usuarios = Ebean.createQuery(Usuario.class).findList();
        Form<Usuario> form = Form.form(Usuario.class);
        return ok(index.render(form));
    }

    public static Result diretorio() {
        List<Arquivo> arquivos = Ebean.createQuery(Arquivo.class).findList();
        return ok(diretorio.render(arquivos));
    }

    public static Result formularioNovoArquivo() {
        Form<Arquivo> form = Form.form(Arquivo.class);
        return ok(criarArquivo.render(form));
    }

    public static Result formularioNovoUsuario() {
        Form<Usuario> form = Form.form(Usuario.class);
        return ok(cadastro.render(form));
    }

    public static Result novoUsuario() {
        Form<Usuario> form = Form.form(Usuario.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(cadastro.render(form));
        }
        Usuario usuario = form.get();
        usuario.save();
        return redirect(routes.Application.index());
    }


    public static Result novoArquivo() {
        Form<Arquivo> form = Form.form(Arquivo.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(criarArquivo.render(form));
        }
        Arquivo arquivo = form.get();
        arquivo.save();
        return redirect(routes.Application.diretorio());
    }

    public static Result login() {
        List<Arquivo> arquivos = Ebean.createQuery(Arquivo.class).findList();
        return ok(diretorio.render(arquivos));
//        Form<Usuario> form = Form.form(Usuario.class).bindFromRequest();
//        if (form.hasErrors()) {
//            return badRequest(cadastro.render(form));
//        }
//        return redirect(routes.Application.index());
//        Form<Arquivo> form = Form.form(Arquivo.class);
//        return ok(diretorio.render(form));
    }

}
