package controllers;

import com.google.gson.Gson;
import dto.AllFacilitiesDto;
import services.SportFacilityService;

import java.util.List;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.put;
import static spark.Spark.post;


public class SportFacilityController {
    private static SportFacilityService facilityService = new SportFacilityService();
    private static Gson g = new Gson();

    public static void test() {
        get("facilities/", (req, res) -> {
                    res.type("application/json");
                    String payload = req.body();
                    AllFacilitiesDto s = SportFacilityService.getAllFacilities();
                    return g.toJson(s);
                }
        );
    }
}
