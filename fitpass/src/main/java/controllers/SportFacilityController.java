package controllers;

import beans.users.User;
import com.google.gson.Gson;
import dto.sportfacility.AllFacilitiesDto;
import dto.sportfacility.CreateSportFacilityDto;
import dto.users.LoginDto;
import services.SportFacilityService;
import services.UserService;

import static spark.Spark.get;
import static spark.Spark.post;


public class SportFacilityController {
    private static SportFacilityService facilityService = new SportFacilityService();
    private static Gson g = new Gson();

    public static void test() {

        get("facilities/", (req, res) -> {
                    res.type("application/json");
                    AllFacilitiesDto s = facilityService.getAllFacilities();
                    return g.toJson(s);
                }
        );

        get("facilities/search",(req,res)->{
                    res.type("application/json");
                    String name= req.queryParams("name");
                    String type= req.queryParams("type");
                    String city= req.queryParams("city");
                    String grade= req.queryParams("grade");
                    AllFacilitiesDto s = facilityService.getSearchedFacilities(name,type,city,grade);
                    return g.toJson(s);
                }
        );

        post("facilities/create",(req,res)->{
                    String payload = req.body();
                    CreateSportFacilityDto facilityDto = g.fromJson(payload, CreateSportFacilityDto.class);
                    boolean created = facilityService.createFacility(facilityDto);
                    return true;
                }
        );
    }
}
