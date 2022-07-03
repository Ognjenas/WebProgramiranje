package rest;

import controllers.AdministratorController;
import controllers.AuthController;
import controllers.UserController;
import controllers.SportFacilityController;
import dto.sportfacility.AllFacilitiesDto;

import java.io.File;

import static spark.Spark.*;

public class SparkMainApp {


    public static void main(String[] args) throws Exception {
        port(8080);

        staticFiles.externalLocation(new File("./static").getCanonicalPath());

        path("/facilities", () -> {
            before("/*", AuthController::authFilter);
            get("/",SportFacilityController::loadFacilities);
            get("/search",SportFacilityController::searchFacilities);

        });


        path("/administrator", () -> {
            before("/*", AuthController::authFilter);
            before("/*", AuthController::authAdministrator);
            get("/users", AdministratorController::getAllUsers);
            post("/register-trainer", AdministratorController::registerTrainer);
            post("/register-manager", AdministratorController::registerManager);
            get("/get-free-managers",AdministratorController::getFreeManagers);
            post("/create-facility",AdministratorController::createFacility);
            post("/create-facility-with-manager",AdministratorController::createFacilityWithManager);
        });
        UserController.registerCustomer();
        UserController.getInfo();
        UserController.login();
    }
}
