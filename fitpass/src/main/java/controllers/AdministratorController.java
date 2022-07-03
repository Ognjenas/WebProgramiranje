package controllers;

import com.google.gson.Gson;
import dto.sportfacility.CreateFacilityWithManagerDto;
import dto.sportfacility.CreateSportFacilityDto;
import dto.users.AllUsersDto;
import dto.users.MakeUserDto;
import services.AdministratorService;
import services.SportFacilityService;
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

    public static String getFreeManagers(Request req, Response res){
        return new Gson().toJson(SportFacilityService.getInstance().getFreeManagers());
    }

    public static String createFacility(Request req, Response res){
        String payload = req.body();
        CreateSportFacilityDto facilityDto = new Gson().fromJson(payload, CreateSportFacilityDto.class);
        boolean created = SportFacilityService.getInstance().createFacility(facilityDto);
        return new Gson().toJson(created);
    }

    public static String createFacilityWithManager(Request req, Response res) {
        String payload = req.body();
        CreateFacilityWithManagerDto dto= new Gson().fromJson(payload, CreateFacilityWithManagerDto.class);
        return new Gson().toJson(SportFacilityService.getInstance().createFacilityWithManager(dto));
    }
}
