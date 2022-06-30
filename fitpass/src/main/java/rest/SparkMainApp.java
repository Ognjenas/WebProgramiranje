package rest;

import controllers.AdministratorController;
import controllers.AuthController;
import controllers.UserController;
import controllers.SportFacilityController;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import services.SecretKeyGetter;
import spark.Filter;

import java.io.File;
import java.security.Key;

import static spark.Spark.*;

public class SparkMainApp {


    public static void main(String[] args) throws Exception {
        port(8080);

        staticFiles.externalLocation(new File("./static").getCanonicalPath());

        path("/facilities", () -> {
            before("/*", AuthController::authFilter);
        });
        path("/administrator", () -> {
            before("/*", AuthController::authFilter);
            before("/*", AuthController::authAdministrator);
            get("/users", AdministratorController::getAllUsers);
            post("/register-trainer", AdministratorController::registerTrainer);
            post("/register-manager", AdministratorController::registerManager);
        });
        UserController.registerCustomer();
        UserController.getInfo();
        UserController.login();
        SportFacilityController.test();
    }
}
