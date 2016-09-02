package controllers;

import play.Logger;
import play.mvc.*;
import play.mvc.Http.*;

import static play.mvc.Controller.flash;


public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("username");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        flash("erro", "Você não está logado no sistema.");
        Logger.info("Acessando Negado");
        return redirect(routes.Application.index());
    }
}