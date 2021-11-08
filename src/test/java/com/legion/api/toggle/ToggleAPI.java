package com.legion.api.toggle;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.legion.api.common.EnterpriseId;
import com.legion.utils.SimpleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

public class ToggleAPI {

    public static void enableToggle(String toggleName, String username, String password) {
        try {
            Response response = given().params("enterpriseName", System.getProperty("enterprise"), "sourceSystem", "legion", "passwordPlainText", password, "userName", username)
                    .when().get("https://rc-enterprise.dev.legion.work/legion/authentication/login").then().statusCode(200).extract().response();
            String sessionId = response.header("sessionid");
            //get toggle to confirm the switch is on or off
            Response response2 = given().log().all().header("sessionId", sessionId).param("toggle", toggleName).when().get("https://rc-enterprise.dev.legion.work/legion/toggles").then().log().all().extract().response();
            String enabled = response2.jsonPath().get("record.enabled").toString();

            List<HashMap> rules = new ArrayList<>();
            HashMap<String, Object> rulesValue = new HashMap<>();
            rulesValue.put("enterpriseName", EnterpriseId.valueOf(System.getProperty("enterprise").toLowerCase().replace("-", "")).getValue());
            List<String> businesses = new ArrayList<>();
            businesses.add(EnterpriseId.valueOf(System.getProperty("enterprise").toLowerCase().replace("-", "")).getValue());
            rulesValue.put("businessName", businesses);
            rules.add(rulesValue);

            if (enabled.equals("false")) {
                HashMap<String, Object> recordContext = new HashMap<>();
                recordContext.put("name", toggleName);
                recordContext.put("controlValue", "");
                recordContext.put("defaultValue", true);
                recordContext.put("rules", rules);
                HashMap<String, Object> jsonAsMap = new HashMap<>();
                jsonAsMap.put("level", "Enterprise");
                jsonAsMap.put("valid", true);
                jsonAsMap.put("record", recordContext);

                Response responseAfterDisable = given().log().all().headers("sessionId", sessionId).contentType(ContentType.JSON).body(jsonAsMap)
                        .when().post("https://rc-enterprise.dev.legion.work/legion/toggles").then().log().all().extract().response();
                responseAfterDisable.then().statusCode(200);
            }
        } catch (Exception e) {
            SimpleUtils.report("Failed to enable toggle: " + toggleName);
        }
    }

    public static void disableToggle(String toggleName, String username, String password) {

        Response response = given().params("enterpriseName",System.getProperty("enterprise"),"sourceSystem","legion","passwordPlainText",password,"userName",username)
                .when().get("https://rc-enterprise.dev.legion.work/legion/authentication/login").then().statusCode(200).extract().response();
        String sessionId = response.header("sessionid");
        //get toggle to confirm the switch is on or off
        Response response2= given().log().all().header("sessionId",sessionId).param("toggle", toggleName).when().get("https://rc-enterprise.dev.legion.work/legion/toggles").then().log().all().extract().response();
        String enabled = response2.jsonPath().get("record.enabled").toString();

        List<HashMap> rules = new ArrayList<>();
        HashMap<String, Object> rulesValue = new HashMap<>();
        rulesValue.put("enterpriseName", EnterpriseId.valueOf(System.getProperty("enterprise").toLowerCase().replace("-", "")).getValue());
        List<String> businesses = new ArrayList<>();
        businesses.add(EnterpriseId.valueOf(System.getProperty("enterprise").toLowerCase().replace("-", "")).getValue());
        rulesValue.put("businessName", businesses);
        rules.add(rulesValue);

        if (enabled.equals("true")) {
            HashMap<String, Object> recordContext = new HashMap<>();
            recordContext.put( "name", toggleName);
            recordContext.put("controlValue", "");
            recordContext.put("defaultValue", false);
            recordContext.put("rules", rules);
            HashMap<String, Object>  jsonAsMap = new HashMap<>();
            jsonAsMap.put("level", "Enterprise");
            jsonAsMap.put("valid", true);
            jsonAsMap.put("record",recordContext);

            Response responseAfterDisable= given().log().all().headers("sessionId",sessionId).contentType(ContentType.JSON).body(jsonAsMap)
                    .when().post("https://rc-enterprise.dev.legion.work/legion/toggles").then().log().all().extract().response();
            responseAfterDisable.then().statusCode(200);
        try {

            Response response = given().params("enterpriseName", System.getProperty("enterprise"), "sourceSystem", "legion", "passwordPlainText", password, "userName", username)
                    .when().get("https://rc-enterprise.dev.legion.work/legion/authentication/login").then().statusCode(200).extract().response();
            String sessionId = response.header("sessionid");
            //get toggle to confirm the switch is on or off
            Response response2 = given().log().all().header("sessionId", sessionId).param("toggle", toggleName).when().get("https://rc-enterprise.dev.legion.work/legion/toggles").then().log().all().extract().response();
            String enabled = response2.jsonPath().get("record.enabled").toString();

            List<HashMap> rules = new ArrayList<>();
            HashMap<String, Object> rulesValue = new HashMap<>();
            rulesValue.put("enterpriseName", EnterpriseId.valueOf(System.getProperty("enterprise").toLowerCase()).getValue());
            List<String> businesses = new ArrayList<>();
            businesses.add(EnterpriseId.valueOf(System.getProperty("enterprise").toLowerCase()).getValue());
            rulesValue.put("businessName", businesses);
            rules.add(rulesValue);

            if (enabled.equals("true")) {
                HashMap<String, Object> recordContext = new HashMap<>();
                recordContext.put("name", toggleName);
                recordContext.put("controlValue", "");
                recordContext.put("defaultValue", false);
                recordContext.put("rules", rules);
                HashMap<String, Object> jsonAsMap = new HashMap<>();
                jsonAsMap.put("level", "Enterprise");
                jsonAsMap.put("valid", true);
                jsonAsMap.put("record", recordContext);

                Response responseAfterDisable = given().log().all().headers("sessionId", sessionId).contentType(ContentType.JSON).body(jsonAsMap)
                        .when().post("https://rc-enterprise.dev.legion.work/legion/toggles").then().log().all().extract().response();
                responseAfterDisable.then().statusCode(200);
            }
        } catch (Exception e) {
            SimpleUtils.report("Failed to disable toggle: " + toggleName);
        }
    }
}
