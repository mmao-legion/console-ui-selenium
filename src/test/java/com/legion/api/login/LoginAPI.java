package com.legion.api.login;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.legion.utils.SimpleUtils;

import java.util.HashMap;

import static com.jayway.restassured.RestAssured.given;

public class LoginAPI {

    public static String getSessionIdFromLoginAPI(String username, String password) {
        String sessionId = "";
        try {
            HashMap<String, Object> jsonAsMap = new HashMap<>();
            jsonAsMap.put("enterpriseName", System.getProperty("enterprise"));
            jsonAsMap.put("sourceSystem", "legion");
            jsonAsMap.put("userName", username);
            jsonAsMap.put("passwordPlainText", password);
            Response response = given().log().all().contentType(ContentType.JSON).body(jsonAsMap)
                    .when().post(System.getProperty("env") + "legion/authentication/user/login").then().statusCode(200).extract().response();
            sessionId = response.header("sessionid");
        } catch (Exception e) {
            SimpleUtils.report("Failed to refresh the cache!");
        }
        return sessionId;
    }
}
