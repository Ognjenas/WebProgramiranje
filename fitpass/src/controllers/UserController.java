package controllers;

import dto.users.LoginDto;
import dto.users.RegisterCustomerDto;
import services.UserService;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.put;
import static spark.Spark.post;

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
                    return gson.toJson(UserService.getInstance().checkLogin(loginDto));
                }
        );
    }

    public static void test() {
        get(
                "users/test", (req, res) -> gson.toJson(LocalDate.now())
        );
    }
}
