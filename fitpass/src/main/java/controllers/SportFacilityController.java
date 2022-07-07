package controllers;

import com.google.gson.Gson;
import dto.sportfacility.AllFacilitiesDto;
import dto.sportfacility.SportFacilityDto;
import services.SportFacilityService;
import spark.Request;
import spark.Response;


public class SportFacilityController {
    private static SportFacilityService facilityService = new SportFacilityService();
    private static Gson g = new Gson();


    public static String loadFacilities(Request req, Response res) {
        res.type("application/json");
        AllFacilitiesDto s = facilityService.getAllFacilitiesDto();
        return g.toJson(s);
    }

    public static String searchFacilities(Request req, Response res) {
        res.type("application/json");
        String name = req.queryParams("name");
        String type = req.queryParams("type");
        String city = req.queryParams("city");
        String grade = req.queryParams("grade");
        AllFacilitiesDto s = facilityService.getSearchedFacilities(name, type, city, grade);
        return g.toJson(s);
    }

    public static String getOffersByFacilityId(Request req, Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.queryParams("id"));
        return g.toJson(facilityService.getOffersByFacilityId(id));
    }

    public static String showFacility(Request req, Response res) {
        res.type("application/json");
        String id = req.queryParams("id");
        SportFacilityDto s = facilityService.getFacilityToShow(Integer.parseInt(id));
        return g.toJson(s);
    }

    public static String sortAndSearchFacilites(Request req, Response res) {
        res.type("application/json");
        String name = req.queryParams("name");
        String type = req.queryParams("type");
        String city = req.queryParams("city");
        String grade = req.queryParams("grade");
        String columnIndex = req.queryParams("columnIndex");
        String sortDir = req.queryParams("sortDir");

        AllFacilitiesDto s = facilityService.sortAndSearchFacilities(name, type, city, grade,columnIndex,sortDir);
        return g.toJson(s);
    }

    public static String getOfferById(Request req, Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.queryParams("id"));
        return g.toJson(facilityService.getOffer(id));
    }
}
