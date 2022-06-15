package rest;

import controllers.UserController;
import controllers.SportFacilityController;
import java.io.File;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class SparkMainApp {

    public static void main(String[] args) throws Exception {
        port(8080);

        staticFiles.externalLocation(new File("./static").getCanonicalPath());

        UserController.test();
        SportFacilityController.test();
    }
}
