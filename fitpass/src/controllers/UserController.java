package controllers;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.put;
import static spark.Spark.post;

public class UserController {

    public static void test() {
        get(
                "rest/users/", (req, res) -> {
                    return "hello world";
                }
        );
    }
}
