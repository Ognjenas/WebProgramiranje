package rest;

import controllers.*;
import dto.sportfacility.AllFacilitiesDto;

import java.io.File;

import static spark.Spark.*;

public class SparkMainApp {


    public static void main(String[] args) throws Exception {
        port(8080);

        staticFiles.externalLocation(new File("./static").getCanonicalPath());



        path("/", () -> {
            get("/get-facilities",SportFacilityController::loadFacilities);
            get("/search-facility",SportFacilityController::searchFacilities);
            get("/show-facility",SportFacilityController::showFacility);
            post("/register-customer", UserController::registerCustomer);
            post("/login", UserController::login);
        });


        path("/administrator", () -> {
            before("/*", AuthController::authFilter);
            before("/*", AuthController::authAdministrator);
            get("/get-users", AdministratorController::getAllUsers);
            post("/register-trainer", AdministratorController::registerTrainer);
            post("/register-manager", AdministratorController::registerManager);
            get("/get-free-managers",AdministratorController::getFreeManagers);
            post("/create-facility",AdministratorController::createFacility);
            post("/create-facility-with-manager",AdministratorController::createFacilityWithManager);
        });

        path("/manager", () -> {
            before("/*", AuthController::authFilter);
            before("/*", AuthController::authManager);
            get("/get-facility", ManagerController::getManagersFacility);
            get("/get-facility-offers", ManagerController::getFacilityOffers);
            get("/create-offer/get-all-trainers", ManagerController::getAllTrainers);
            post("/create-offer", ManagerController::makeOffer);
        });


        path("/users", () -> {
            before("/*", AuthController::authFilter);
            post("/get-info", UserController::getInfo);
            get("/get-user", UserController::getUser);
            post("/edit-user", UserController::editUser);
        });
    }
}
