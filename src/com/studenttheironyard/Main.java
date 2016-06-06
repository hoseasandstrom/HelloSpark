package com.studenttheironyard;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {

    static User user;

    public static void main(String[] args) {
        Spark.init();
        Spark.get(
                "/",
                (request, response) -> {
                    HashMap content = new HashMap();
                    if (user == null){
                        return new ModelAndView(content, "login.html");
                    }
                    else {
                        content.put("name", user.name);
                        return new ModelAndView(content, "home.html");
                    }

                },
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/login",
                (request, response) -> {
                    String username = request.queryParams("username");
                    user = new User(username);
                    response.redirect("/");
                    return "";
                }
        );
    }
}
