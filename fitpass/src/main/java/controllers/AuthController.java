package controllers;

import com.google.gson.Gson;
import dto.AllFacilitiesDto;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import rest.SparkMainApp;
import services.SportFacilityService;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Calendar;

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
