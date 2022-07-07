package controllers;

import beans.users.Role;
import beans.users.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import rest.SparkMainApp;
import services.SecretKeyGetter;
import services.UserService;
import spark.Request;
import spark.Response;

import static spark.Spark.halt;

public class AuthController {

    public static void authFilter(Request request, Response response) {
        if(request.headers("token") != null) {
            try{
                Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build().parseClaimsJws(request.headers("token"));
            }catch (JwtException e) {
                System.out.println("Nije dobar token");
                response.removeCookie("token");
                halt(401);
                response.status(401);
            }
        } else {
            System.out.println("Nije dobar token");
            halt(401);
        }

    }

    public static void authAdministrator(Request request, Response response) {
        if(request.headers("token") != null) {
            try{
                String username = Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build()
                        .parseClaimsJws(request.headers("token")).getBody().getSubject();
                User user = UserService.getInstance().getByUsername(username);
                if(user == null || !checkUser(user, Role.ADMINISTRATOR)) {
                    halt(401);
                }
            }catch (JwtException e) {
                System.out.println("Nije dobar token");
                response.removeCookie("token");
                halt(401);
                response.status(401);
            }
        } else {
            System.out.println("Nije dobar token");
            halt(401);
        }
    }

    public static void authManager(Request request, Response response) {
        if(request.headers("token") != null) {
            try{
                String username = Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build()
                        .parseClaimsJws(request.headers("token")).getBody().getSubject();
                User user = UserService.getInstance().getByUsername(username);
                if(user == null || !checkUser(user, Role.MANAGER)) {
                    halt(401);
                }
            }catch (JwtException e) {
                System.out.println("Nije dobar token");
                response.removeCookie("token");
                halt(401);
                response.status(401);
            }
        } else {
            System.out.println("Nije dobar token");
            halt(401);
        }
    }

    public static void authCustomer(Request request, Response response) {
        if(request.headers("token") != null) {
            try{
                String username = Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build()
                        .parseClaimsJws(request.headers("token")).getBody().getSubject();
                User user = UserService.getInstance().getByUsername(username);
                if(user == null || !checkUser(user, Role.CUSTOMER)) {
                    halt(401);
                }
            }catch (JwtException e) {
                System.out.println("Nije dobar token");
                response.removeCookie("token");
                halt(401);
                response.status(401);
            }
        } else {
            System.out.println("Nije dobar token");
            halt(401);
        }
    }

    private static boolean checkUser(User user, Role role) {
        return !user.isDeleted() && user.getRole().equals(role);
    }

}
