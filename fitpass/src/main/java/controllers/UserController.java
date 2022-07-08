package controllers;

import beans.users.User;
import dto.users.*;
import services.SecretKeyGetter;
import services.UserService;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.put;
import static spark.Spark.post;

import io.jsonwebtoken.Jwts;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class UserController {

    private static Gson gson = new Gson();
    private static UserService userService = UserService.getInstance();

    public static String registerCustomer(Request req, Response res) {
        String payload = req.body();
        RegisterCustomerDto customerDto = gson.fromJson(payload, RegisterCustomerDto.class);
        return gson.toJson(userService.registerCustomer(customerDto));

    }

    public static void getAllUsers() {
        get(
                "users/", (req, res) -> {
                    AllUsersDto dto = userService.getAllUsers();
                    return gson.toJson(dto);
                }
        );
    }

    public static String login(Request req, Response res) {
        String payload = req.body();
        LoginDto loginDto = gson.fromJson(payload, LoginDto.class);
        User loggedUser = userService.checkLogin(loginDto);

        if (loggedUser != null) {
            String jws = Jwts.builder()
                    .setSubject(loggedUser.getUsername())
                    .signWith(SecretKeyGetter.get()).compact();

            return gson.toJson(jws);
        }
        return gson.toJson("");

    }

    public static String getInfo(Request req, Response res) {
        res.type("application/json");
        String payload = req.body();
        String token = gson.fromJson(payload, String.class);
        String username = Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build().parseClaimsJws(token).getBody().getSubject();
        return gson.toJson(userService.getUserInfo(username));

    }
    public static String getUser(Request req, Response res) {
        res.type("application/json");
        String token = gson.fromJson(req.headers("token"), String.class);
        String username = Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build().parseClaimsJws(token).getBody().getSubject();
        return gson.toJson(userService.getUser(username));
    }

    public static String editUser(Request req, Response res) {
        res.type("application/json");
        String payload = req.body();
        EditUserDto editUserDto = gson.fromJson(payload, EditUserDto.class);
        User user = userService.editUser(editUserDto);
        if(user != null) {
                String jws = Jwts.builder()
                        .setSubject(user.getUsername())
                        .signWith(SecretKeyGetter.get()).compact();

                return gson.toJson(jws);

        } else {
            return gson.toJson("");
        }

    }

    public static String createSubscription(Request req, Response res) {
        res.type("application/json");
        String payload = req.body();
        MakeSubscriptionDto dto = gson.fromJson(payload, MakeSubscriptionDto.class);
        return gson.toJson(userService.createSubscription(dto));
    }

    public static String getCurrentSubscription(Request req, Response res) {
        res.type("application/json");
        String username =req.queryParams("username");
        return gson.toJson(userService.getCurrentSubscription(username));
    }

    public static Object checkPromoCode(Request req, Response res) {
        res.type("application/json");
        String searchedCode =req.queryParams("src");
        return gson.toJson(userService.checkPromoCode(searchedCode));
    }

    public static Object checkSubscriptionValid(Request req, Response res) {
        res.type("application/json");
        String payload = req.body();
        UserInfoDto dto = gson.fromJson(payload, UserInfoDto.class);
        userService.checkSubscriptionValid(dto.getUsername());
        return gson.toJson("");
    }
}
