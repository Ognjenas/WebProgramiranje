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
}
