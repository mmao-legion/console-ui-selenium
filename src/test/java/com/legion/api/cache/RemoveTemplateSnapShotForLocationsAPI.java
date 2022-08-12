package com.legion.api.cache;

import com.jayway.restassured.response.Response;
import com.legion.tests.TestBase;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

import static com.jayway.restassured.RestAssured.given;

public class RemoveTemplateSnapShotForLocationsAPI {

    public static void removeTemplateSnapShotForLocationsAPI(String username, String password) {
        try {
            HashMap<String, String> locationBusinessID = JsonUtil.getPropertiesFromJsonFile("src/test/resources/LocationBusinessID.json");
            int dayOfYearCurrentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
            int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
            int lastSundayOfYear = dayOfYearCurrentDay - (dayOfWeek-1)-7;
            String currentYear = TestBase.getCurrentTime().substring(0, 4);
            Response response = given().params("enterpriseName",System.getProperty("enterprise"),"sourceSystem","legion","passwordPlainText",password,"userName",username)
                    .when().get("https://rc-enterprise.dev.legion.work/legion/authentication/login").then().statusCode(200).extract().response();
            String sessionId = response.header("sessionid");
            Set<String> keySet = locationBusinessID.keySet();
            for (String key: keySet) {
                String businessId = locationBusinessID.get(key);
                for (int i=0; i<4; i++) {
                    Response cacheResponse = given().log().all()
                            .header("sessionId", sessionId)
                            .param("year", currentYear)
                            .param("weekStartDayOfTheYear",lastSundayOfYear+ i*7)
                            .param("businessId",businessId)
                            .when().get("https://rc-enterprise.dev.legion.work/legion/configTemplate/removeTemplateSnapShotForLocations")
                            .then().log().all().extract().response();
                    cacheResponse.then().statusCode(200);
                }
            }

        } catch (Exception e) {
            SimpleUtils.report("Failed to refresh the cache!");
        }
    }
}
