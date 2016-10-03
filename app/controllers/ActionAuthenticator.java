package controllers;

import models.Usuario;
import org.h2.engine.User;
import org.jboss.logging.Logger;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.util.Arrays;

public class ActionAuthenticator extends Security.Authenticator {
    @Override
    public String getUsername(Http.Context ctx) {
        String token = getTokenFromHeader(ctx);
        play.Logger.info(token);
        if (token != null) {
            Usuario user = Usuario.find.where().eq("authToken", token).findUnique();
            if (user != null) {
                return user.username;
            }
       }

        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        return super.onUnauthorized(context);
    }

    private String getTokenFromHeader(Http.Context ctx) {
        return ctx.request().cookie("X-AUTH-TOKEN").value();
    }
}