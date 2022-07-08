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
        return gson.toJson(managerService.makeOffer(makeOfferDto, username));
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
        return gson.toJson(managerService.editOffer(offerDto, username));
    }

    public static String getTrainingsFromFacility(Request req, Response res) {
        res.type("application/json");
        String token = gson.fromJson(req.headers("token"), String.class);
        String username = Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build().parseClaimsJws(token).getBody().getSubject();
        return gson.toJson(managerService.getTrainingsFromFacility(username));
    }

    public static String getCustomersFromFacility(Request req, Response res) {
        res.type("application/json");
        String token = gson.fromJson(req.headers("token"), String.class);
        String username = Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build().parseClaimsJws(token).getBody().getSubject();
        return gson.toJson(managerService.getCustomersFromFacility(username));
    }
}
