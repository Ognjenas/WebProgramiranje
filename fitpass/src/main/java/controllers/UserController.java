package controllers;

import beans.users.User;
import dto.users.LoginDto;
import dto.users.RegisterCustomerDto;
import rest.SparkMainApp;
import services.UserService;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.put;
import static spark.Spark.post;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

import com.google.gson.Gson;

import java.time.LocalDate;

public class UserController {

    private static Gson gson = new Gson();

    public static void registerCustomer() {
        post(
                "users/register-customer", (req, res) -> {
                    String payload = req.body();
                    RegisterCustomerDto customerDto = gson.fromJson(payload, RegisterCustomerDto.class);
                    return gson.toJson(UserService.getInstance().registerCustomer(customerDto));
                }
        );
    }

    public static void login() {
        post(
                "users/login", (req, res) -> {
                    String payload = req.body();
                    LoginDto loginDto = gson.fromJson(payload, LoginDto.class);
                    User loggedUser = UserService.getInstance().checkLogin(loginDto);

                    if (loggedUser != null) {
                        String jws = Jwts.builder()
                                .setSubject(loggedUser.getUsername())
                                .signWith(SparkMainApp.key).compact();

                        return gson.toJson(jws);
                    }
                    return gson.toJson("");
                }
        );
    }

    public static void getInfo() {
        post(
                "users/get-info", (req, res) -> {
                    res.type("application/json");
                    String payload = req.body();
                    String token = gson.fromJson(payload, String.class);
                    String username = Jwts.parserBuilder().setSigningKey(SparkMainApp.key).build().parseClaimsJws(token).getBody().getSubject();
                    return gson.toJson(UserService.getInstance().getUserInfo(username));
                }
        );
    }
}
