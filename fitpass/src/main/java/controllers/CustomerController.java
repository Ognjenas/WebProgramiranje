package controllers;

import com.google.gson.Gson;
import dto.offer.MakeOfferDto;
import dto.offer.ReserveOfferDto;
import io.jsonwebtoken.Jwts;
import services.CustomerService;
import services.SecretKeyGetter;
import services.SportFacilityService;
import spark.Request;
import spark.Response;

import java.time.LocalDate;

public class CustomerController {

    private static CustomerService customerService = CustomerService.getInstance();

    private static Gson gson = new Gson();

    public static String getAvailableTimesForOfferByDate(Request req, Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.queryParams("id"));
        String payload = req.body();
        LocalDate date = gson.fromJson(payload, LocalDate.class);
        String token = gson.fromJson(req.headers("token"), String.class);
        String username = Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build().parseClaimsJws(token).getBody().getSubject();
        return gson.toJson(customerService.getAvailableTimesForOfferAndDate(id, date, username));
    }

    public static String reserveOffer(Request req, Response res) {
        res.type("application/json");
        String payload = req.body();
        ReserveOfferDto reserveOfferDto = gson.fromJson(payload, ReserveOfferDto.class);
        String token = gson.fromJson(req.headers("token"), String.class);
        String username = Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build().parseClaimsJws(token).getBody().getSubject();
        boolean response = customerService.reserveOffer(reserveOfferDto, username);
        if(!response) {
            res.status(400);
        }
        return gson.toJson(response);
    }

    public static String getTrainings(Request req, Response res) {
        res.type("application/json");
        String token = gson.fromJson(req.headers("token"), String.class);
        String username = Jwts.parserBuilder().setSigningKey(SecretKeyGetter.get()).build().parseClaimsJws(token).getBody().getSubject();
        return gson.toJson(customerService.getTrainings(username));
    }
}
