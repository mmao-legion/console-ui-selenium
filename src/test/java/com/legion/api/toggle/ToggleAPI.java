package com.legion.api.toggle;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.legion.api.login.LoginAPI;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;
import com.legion.tests.TestBase;

import java.util.*;


import static com.jayway.restassured.RestAssured.given;
import static com.legion.utils.MyThreadLocal.getEnterprise;

public class ToggleAPI {

    public static void updateToggle(String toggleName, String username, String password, boolean isTurnOn) {
        try {
            String enterpriseName = getEnterprise();
            String sessionId = LoginAPI.getSessionIdFromLoginAPI(username, password);

            List<HashMap> rules = new ArrayList<>();
            // Get the current enterprise names which have specific toggle turned on
            List<String> enterpriseNames = getCurrentEnabledEnterprises(toggleName);
            if (isTurnOn) {
                if (!enterpriseNames.contains(enterpriseName.toLowerCase())) {
                    enterpriseNames.add(enterpriseName);
                }
            } else {
                if (enterpriseNames.contains(enterpriseName.toLowerCase())) {
                    for (int i = 0; i < enterpriseNames.size(); i++) {
                        if (enterpriseNames.get(i).equalsIgnoreCase(enterpriseName)) {
                            enterpriseNames.remove(i);
                        }
                    }
                }
            }
            if (enterpriseNames != null && enterpriseNames.size() > 0) {
                for (String name : enterpriseNames) {
                    HashMap<String, Object> rulesValue = new HashMap<>();
                    rulesValue.put("enterpriseName", name);
                    rules.add(rulesValue);
                }
            }

            HashMap<String, Object> recordContext = new HashMap<>();
            recordContext.put("name", toggleName);
            recordContext.put("defaultValue", false);
            recordContext.put("rules", rules);
            HashMap<String, Object> jsonAsMap = new HashMap<>();
            jsonAsMap.put("level", "Enterprise");
            jsonAsMap.put("valid", true);
            jsonAsMap.put("record", recordContext);

            Response responseAfterDisable = given().log().all().headers("sessionId", sessionId).contentType(ContentType.JSON).body(jsonAsMap)
                    .when().post(System.getProperty("env") + "legion/toggles").then().log().all().extract().response();
            responseAfterDisable.then().statusCode(200);
        } catch (Exception e) {
            SimpleUtils.report("Failed to enable toggle: " + toggleName);
        }
    }

    private static List<String> getCurrentEnabledEnterprises(String toggleName) {
        List<String> enterpriseNames = new ArrayList<>();
        String fileName = "Toggles.json";
        try {
            String env = System.getProperty("env");
            if (env != null && !env.isEmpty()) {
                if (env.toLowerCase().contains("rc") || env.toLowerCase().contains("ephemeral")) {
                    fileName = "RC" + fileName;
                } else if (env.toLowerCase().contains("rel")) {
                    fileName = "Release" + fileName;
                }
                HashMap<String, String> toggleNEnterprises = JsonUtil.getPropertiesFromJsonFile("src/test/java/com/legion/api/" + fileName);
                String enterprises = toggleNEnterprises.get(toggleName);
                String[] nameList = enterprises.split(",");
                Collections.addAll(enterpriseNames, nameList);
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
        return enterpriseNames;
    }

    public static void enableToggleNew(String toggleName) {
        try {

            String sessionId = TestBase.getSession();
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

    public static void disableToggleNew(String toggleName) {
        try {

            String sessionId = TestBase.getSession();
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
