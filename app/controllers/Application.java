package controllers;

import com.avaje.ebean.Ebean;
import models.Arquivo;
import models.Pasta;
import models.Usuario;
import org.h2.engine.User;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;
import play.data.*;

import java.util.ArrayList;
import java.util.List;

import static play.data.Form.form;

public class Application extends Controller {

    public static Result index() {
//      List<Usuario> usuarios = Ebean.createQuery(Usuario.class).findList();
        Form<Usuario> form = form(Usuario.class);
        return ok(index.render(form));
    }

    public static Result diretorio() {
        List<Arquivo> arquivos = Ebean.createQuery(Arquivo.class).findList();
        List<Pasta> pastas = Ebean.createQuery(Pasta.class).findList();
        return ok(diretorio.render(arquivos, pastas));
    }

    public static Result formularioNovoArquivo(String id) {
        Form<Arquivo> form = form(Arquivo.class);
        return ok(criarArquivo.render(form, id));
    }

    public static Result formularioNovaPasta() {
        Form<Pasta> form = form(Pasta.class);
        return ok(criarPasta.render(form));
    }

    public static Result formularioNovoUsuario() {
        Form<Usuario> form = form(Usuario.class);
        return ok(cadastro.render(form));
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

    public static Result arquivo(String id) {
        return ok(arquivo.render(getArquivoPorId(id)));
    }


    private static Arquivo getArquivoPorId(String id) {
        List<Arquivo> listaArquivos = Ebean.createQuery(Arquivo.class).findList();
        for (Arquivo a: listaArquivos) {
            if (a.getId().equals(id)) {
                return a;
            }
        }
        return null;
    }



    public static Result pasta(String id) {
        return ok(pasta.render(getArquivosPasta(id), id));
    }


//    private static Pasta getPastaPorId(String id) {
//        List<Pasta> listaPasta = Ebean.createQuery(Pasta.class).findList();
//        for (Pasta a: listaPasta) {
//            if (a.getId().equals(id)) {
//                return a;
//            }
//        }
//        return null;
//    }

    private static List<Arquivo> getArquivosPasta(String id) {
        List<Arquivo> arquivos = Ebean.createQuery(Arquivo.class).findList();
        List<Arquivo> arq = new ArrayList<>();
        for (Arquivo a: arquivos) {
            if (a.getPastaId() != null && a.getPastaId().equals(id)) {
                arq.add(a);
            }
        }
        return arq;
    }


    public static Result novoArquivo() {
        Form<Arquivo> form = form(Arquivo.class).bindFromRequest();
//        if (form.hasErrors()) {
//            return badRequest(criarArquivo.render(form);
//        }
        Arquivo arquivo = form.get();
        arquivo.save();
        return redirect(routes.Application.diretorio());
    }

    public static Result novaPasta() {
        Form<Pasta> form = form(Pasta.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(criarPasta.render(form));
        }
        Pasta pasta = form.get();
        pasta.save();
        return redirect(routes.Application.diretorio());
    }



    public static Result login() {
        //List<Arquivo> arquivos = Ebean.createQuery(Arquivo.class).findList();
        //return ok(diretorio.render(arquivos));
        Form<Usuario> form = Form.form(Usuario.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(cadastro.render(form));
        }
        Usuario a = form.get();
        Usuario b = Ebean.createQuery(Usuario.class).where().eq("email", a.getEmail()).findUnique();

        if (b != null){
            return redirect(routes.Application.diretorio());
        } else {
            return redirect(routes.Application.index());
        }

//        Form<Arquivo> form = Form.form(Arquivo.class);
//        return ok(diretorio.render(form));
    }



}
