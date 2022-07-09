package controllers;

import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import services.SecretKeyGetter;
import services.TrainerService;
import spark.Request;
import spark.Response;

public class TrainerController {

    private static final TrainerService trainerService = TrainerService.getInstance();

    private static Gson gson = new Gson();

    public static String getTrainings(Request req, Response res) {
        res.type("application/json");
        String token = gson.fromJson(req.headers("token"), String.class);
        String username = Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build().parseClaimsJws(token).getBody().getSubject();
        return gson.toJson(trainerService.getTrainingsForTrainer(username));
    }

    public static String cancelTraining(Request req, Response res) {
        res.type("application/json");
        String token = gson.fromJson(req.headers("token"), String.class);
        String username = Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build().parseClaimsJws(token).getBody().getSubject();
        int id = Integer.parseInt(req.queryParams("id"));
        return gson.toJson(trainerService.cancelTraining(id, username));
    }

    public static String searchTrainings(Request req, Response res) {
        res.type("application/json");
        String facName = req.queryParams("facName");
        String price = req.queryParams("price");
        String facType = req.queryParams("facType");
        String trainingType = req.queryParams("trainingType");
        String sortType = req.queryParams("sortType");
        String sortDir = req.queryParams("sortDir");
        String fromDate = req.queryParams("fromDate");
        String toDate = req.queryParams("toDate");
        String username = req.queryParams("username");
        return gson.toJson(trainerService.searchTrainings(facName,price,facType,trainingType,sortType,sortDir,fromDate,toDate,username));
    }
}
