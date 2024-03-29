package controllers;

import com.google.gson.Gson;
import dto.offer.MakeOfferDto;
import dto.offer.OfferDto;
import io.jsonwebtoken.Jwts;
import services.ManagerService;
import services.SecretKeyGetter;
import spark.Request;
import spark.Response;

public class ManagerController {

    private static final ManagerService managerService = ManagerService.getInstance();
    private static final Gson gson = new Gson();

    public static String getManagersFacility(Request req, Response res) {
        res.type("application/json");
        String token = gson.fromJson(req.headers("token"), String.class);
        String username = Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build().parseClaimsJws(token).getBody().getSubject();
        return gson.toJson(managerService.getManagersFacility(username));
    }

    public static String getFacilityOffers(Request req, Response res) {
        res.type("application/json");
        String token = gson.fromJson(req.headers("token"), String.class);
        String username = Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build().parseClaimsJws(token).getBody().getSubject();
        return gson.toJson(managerService.getOffers(username));
    }

    public static String getAllTrainers(Request req, Response res) {
        res.type("application/json");
        return gson.toJson(managerService.getAllTrainers());
    }

    public static String makeOffer(Request req, Response res) {
        res.type("application/json");
        String token = gson.fromJson(req.headers("token"), String.class);
        String username = Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build().parseClaimsJws(token).getBody().getSubject();
        String payload = req.body();
        MakeOfferDto makeOfferDto = gson.fromJson(payload, MakeOfferDto.class);
        boolean returnValue = managerService.makeOffer(makeOfferDto, username);
        if(!returnValue) {
            res.status(403);
        }
        return gson.toJson(returnValue);
    }

    public static String getTrainersFromFacility(Request req, Response res) {
        res.type("application/json");
        String token = gson.fromJson(req.headers("token"), String.class);
        String username = Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build().parseClaimsJws(token).getBody().getSubject();
        return gson.toJson(managerService.getTrainersFromFacility(username));
    }

    public static String getOffer(Request req, Response res) {
        res.type("application/json");
        String token = gson.fromJson(req.headers("token"), String.class);
        String username = Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build().parseClaimsJws(token).getBody().getSubject();
        int id = Integer.parseInt(req.queryParams("id"));
        return gson.toJson(managerService.getOffer(id, username));
    }

    public static String editOffer(Request req, Response res) {
        res.type("application/json");
        String token = gson.fromJson(req.headers("token"), String.class);
        String username = Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build().parseClaimsJws(token).getBody().getSubject();
        String payload = req.body();
        OfferDto offerDto = gson.fromJson(payload, OfferDto.class);
        boolean returnValue = managerService.editOffer(offerDto, username);
        if(!returnValue) {
            res.status(403);
        }
        return gson.toJson(returnValue);
    }

    public static String getTrainingsFromFacility(Request req, Response res) {
        res.type("application/json");
        String token = gson.fromJson(req.headers("token"), String.class);
        String username = Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build().parseClaimsJws(token).getBody().getSubject();
        return gson.toJson(managerService.getTrainingsFromFacility(username));
    }

    public static String searchTrainings(Request req, Response res) {
        res.type("application/json");
        String price = req.queryParams("price");
        String trainingType = req.queryParams("trainingType");
        String sortType = req.queryParams("sortType");
        String sortDir = req.queryParams("sortDir");
        String fromDate = req.queryParams("fromDate");
        String toDate = req.queryParams("toDate");
        String username = req.queryParams("username");
        return gson.toJson(managerService.searchTrainings(price, trainingType, sortType, sortDir, fromDate, toDate, username));

    }
    public static String getCustomersFromFacility(Request req, Response res) {
        res.type("application/json");
        String token = gson.fromJson(req.headers("token"), String.class);
        String username = Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build().parseClaimsJws(token).getBody().getSubject();
        return gson.toJson(managerService.getCustomersFromFacility(username));
    }

    public static String getFacilityComments(Request req, Response res) {
        res.type("application/json");
        String token = gson.fromJson(req.headers("token"), String.class);
        String username = Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build().parseClaimsJws(token).getBody().getSubject();
        System.out.println(username);
        return gson.toJson(managerService.getComments(username));
    }
}

