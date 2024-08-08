package com.legion.api.login;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.legion.utils.SimpleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static com.legion.tests.TestBase.propertyMap;
import static com.legion.utils.MyThreadLocal.*;
import static com.legion.utils.SimpleUtils.getParameterMap;

public class LoginAPI {

    public static String getSessionIdFromLoginAPI(String username, String password) {
        String sessionId = "";
        try {
            HashMap<String, Object> jsonAsMap = getJsonMap(username, password);
            Response response = given().log().all().contentType(ContentType.JSON).body(jsonAsMap)
                    .when().post(System.getProperty("env")+ "legion/authentication/user/login").then().statusCode(200).extract().response();
            sessionId = response.header("sessionid");
        } catch (Exception e) {
            SimpleUtils.report("Failed to launch login API!");
        }
        return sessionId;
    }
    public static List<String> getFirstNameAndLastNameFromLoginAPI(String username, String password) {
        List<String> names = new ArrayList<>();
        try {
            HashMap<String, Object> jsonAsMap = getJsonMap(username, password);
            Response response = given().log().all().contentType(ContentType.JSON).body(jsonAsMap)
                    .when().post(System.getProperty("env")+ "legion/authentication/user/login").then().statusCode(200).extract().response();
            String firstName = response.jsonPath().get("user.legionProfileApi.firstName");
            String lastName = response.jsonPath().get("user.legionProfileApi.lastName");
            names.add(firstName);
            names.add(lastName);
        } catch (Exception e) {
            SimpleUtils.report("Failed to launch login API!");
        }
        return names;
    }

    private static HashMap<String, Object> getJsonMap(String username, String password){
        HashMap<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("enterpriseName", System.getProperty("enterprise"));
        jsonAsMap.put("sourceSystem", "legion");
        jsonAsMap.put("userName", username);
        jsonAsMap.put("passwordPlainText", password);
        return jsonAsMap;
    }
}
