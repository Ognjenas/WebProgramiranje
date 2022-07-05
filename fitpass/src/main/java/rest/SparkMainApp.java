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



        path("/", () -> {
            get("/get-facilities",SportFacilityController::loadFacilities);
            get("/search-facility",SportFacilityController::searchFacilities);
            get("/sort-search-facilites",SportFacilityController::sortAndSearchFacilites);
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
            get("/search-users",AdministratorController::serachUsers);
            get("/sort-search-users",AdministratorController::sortAndSearchUsers);
        });

        path("/users", () -> {
            before("/*", AuthController::authFilter);
            post("/get-info", UserController::getInfo);
            get("/get-user", UserController::getUser);
            post("/edit-user", UserController::editUser);
        });
    }
}
