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

    public static String serachUsers(Request req, Response res) {
        res.type("application/json");
        String searchInput = req.queryParams("searchInput");
        String userRole = req.queryParams("userRole");
        String userType = req.queryParams("userType");
        AllUsersDto dto = UserService.getInstance().getSearchedUsers(searchInput,userRole,userType);
        return new Gson().toJson(dto);
    }

    public static Object sortAndSearchUsers(Request req, Response res) {
        res.type("application/json");
        String searchInput = req.queryParams("searchInput");
        String userRole = req.queryParams("userRole");
        String userType = req.queryParams("userType");
        String columnIndex = req.queryParams("columnIndex");
        String sortDir = req.queryParams("sortDir");
        AllUsersDto dto = UserService.getInstance().getSortedAndSearchedUsers(searchInput,userRole,userType,columnIndex,sortDir);
        return new Gson().toJson(dto);
    }
}
