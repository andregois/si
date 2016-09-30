package controllers;

import models.Usuario;
import org.h2.engine.User;
import org.jboss.logging.Logger;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

public class ActionAuthenticator extends Security.Authenticator {
    @Override
    public String getUsername(Http.Context ctx) {
        String token = getTokenFromHeader(ctx);
//        if (token != null) {
//            Usuario user = Usuario.find.where().eq("authToken", token).findUnique();
//            if (user != null) {
//                return user.username;
//            }
//        }
        //TODO
        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        return super.onUnauthorized(context);
    }

    private String getTokenFromHeader(Http.Context ctx) {
        String[] authTokenHeaderValues = ctx.request().headers().get("X-AUTH-TOKEN");
        if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1) && (authTokenHeaderValues[0] != null)) {
            return authTokenHeaderValues[0];
        }
        return null;
    }
}