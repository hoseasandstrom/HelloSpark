package com.studenttheironyard;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    static HashMap<String, User> users = new HashMap<>();

    static ArrayList<User> userList = new ArrayList<>();


    public static void main(String[] args) {
        Spark.init();
        Spark.get(
                "/",
                (request, response) -> {
                    Session session = request.session();
                    String username = session.attribute("username");
                    HashMap content = new HashMap();
                    if (username == null){
                        return new ModelAndView(content, "login.html");
                    }
                    else {
                        content.put("name", username);
                        content.put("users", userList);
                        return new ModelAndView(content, "home.html");
                    }

                },
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/login",
                (request, response) -> {
                    String username = request.queryParams("username");
                    User user = users.get(username);
                    if(user == null) {
                        user = new User(username);
                        users.put(username, user);
                        userList.add(user);
                    }

                    Session session = request.session();
                    session.attribute("username", username);

                    response.redirect("/");
                    return "";
                }
        );
        Spark.post(
                "/logout",
                (request, response) -> {
                   Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                }
        );

    }
}
