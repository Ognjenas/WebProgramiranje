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
            get("/sort-search-facilites",SportFacilityController::sortAndSearchFacilites);
            get("/show-facility",SportFacilityController::showFacility);
            post("/register-customer", UserController::registerCustomer);
            post("/login", UserController::login);
            get("/show-facility/offers", SportFacilityController::getOffersByFacilityId);
            get("/show-facility/get-offer", SportFacilityController::getOfferById);
            post("/show-facility/get-offer/get-available-appointments", CustomerController::getAvailableTimesForOfferByDate);
            get("/show-facility/get-confirmed-comments",SportFacilityController::loadConfirmedComments);
            get("/show-facility/can-comment",SportFacilityController::canComment);
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
            get("/get-promo-codes",AdministratorController::getValidPromoCodes);
            post("/create-promo-code",AdministratorController::createPromoCode);
            get("/get-unapproved-comments",AdministratorController::getUnapprovedComments);
            get("/get-finished-comments",AdministratorController::getFinishedComments);
            post("/confirm-comment",AdministratorController::confirmComment);
            post("/reject-comment",AdministratorController::rejectComment);
        });

        path("/manager", () -> {
            before("/*", AuthController::authFilter);
            before("/*", AuthController::authManager);
            get("/get-facility", ManagerController::getManagersFacility);
            get("/get-facility/get-trainings", ManagerController::getTrainingsFromFacility);
            get("/get-facility/offer", ManagerController::getOffer);
            post("/get-facility/offer/edit", ManagerController::editOffer);
            get("/get-facility/trainers", ManagerController::getTrainersFromFacility);
            get("/get-facility/customers", ManagerController::getCustomersFromFacility);
            get("/get-facility-offers", ManagerController::getFacilityOffers);
            get("/create-offer/get-all-trainers", ManagerController::getAllTrainers);
            post("/create-offer", ManagerController::makeOffer);
            get("/get-facility/search-trainings",ManagerController::searchTrainings);
            get("/get-facility-comments",ManagerController::getFacilityComments);
        });

        path("/customer", () -> {
            before("/*", AuthController::authFilter);
            before("/*", AuthController::authCustomer);
            post("/make-appointment", CustomerController::reserveOffer);
            get("/get-trainings", CustomerController::getTrainings);
            get("/search-trainings",CustomerController::searchTrainings);
            post("/create-comment",CustomerController::createComment);
        });

        path("/trainer", () -> {
            before("/*", AuthController::authFilter);
            before("/*", AuthController::authTrainer);
            get("/get-trainings", TrainerController::getTrainings);
            get("/get-trainings/cancel", TrainerController::cancelTraining);
            get("/search-trainings",TrainerController::searchTrainings);
        });


        path("/users", () -> {
            before("/*", AuthController::authFilter);
            post("/get-info", UserController::getInfo);
            get("/get-user", UserController::getUser);
            post("/edit-user", UserController::editUser);
            post("/create-subscription",UserController::createSubscription);
            get("/get-subscription",UserController::getCurrentSubscription);
            get("/check-promo-code",UserController::checkPromoCode);
            post("/check-subscription-valid",UserController::checkSubscriptionValid);
        });
    }
}
