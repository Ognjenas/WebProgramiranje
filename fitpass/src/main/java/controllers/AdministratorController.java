package controllers;

import com.google.gson.Gson;
import dto.users.AllUsersDto;
import dto.users.MakeUserDto;
import services.AdministratorService;
import services.UserService;
import spark.Request;
import spark.Response;


public class AdministratorController {
    public static String getAllUsers(Request req, Response res) {
        AllUsersDto dto = UserService.getInstance().getAllUsers();
        return new Gson().toJson(dto);

    }

    public static String registerTrainer(Request req, Response res) {
        String payload = req.body();
        MakeUserDto makeUserDto = new Gson().fromJson(payload, MakeUserDto.class);
        return new Gson().toJson(AdministratorService.getInstance().makeTrainer(makeUserDto));
    }

    public static String registerManager(Request req, Response res) {
        String payload = req.body();
        MakeUserDto makeUserDto = new Gson().fromJson(payload, MakeUserDto.class);
        return new Gson().toJson(AdministratorService.getInstance().makeManager(makeUserDto));
    }
}
