package ru.home;

import org.apache.http.client.fluent.Request;

import java.io.IOException;

import static ru.home.properties.Props.getProperty;

public class Authorization {

    public static void main(String[] args) {
        System.out.println(getCode());
    }

    public static String getCode() {
        try {
            return Request.Get(String.format(
                    "https://api.instagram.com/oauth/authorize/?client_id=%s&redirect_uri=%s&response_type=code",
                    getProperty("client_id"),
                    getProperty("redirect_URI"))).execute().returnContent().toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
