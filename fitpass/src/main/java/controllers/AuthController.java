package controllers;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import rest.SparkMainApp;
import spark.Request;
import spark.Response;

import static spark.Spark.halt;

public class AuthController {



    public static void authFilter(Request request, Response response) {
        if(request.headers("token") != null) {
            try{
                Jwts.parserBuilder().setSigningKey(SparkMainApp.key).build().parseClaimsJws(request.headers("token"));
            }catch (JwtException e) {
                System.out.println("Nije dobar token");
                response.removeCookie("token");
                response.status(401);
            }
        } else {
            System.out.println("Nije dobar token");
            response.status(401);
        }

    }

}
