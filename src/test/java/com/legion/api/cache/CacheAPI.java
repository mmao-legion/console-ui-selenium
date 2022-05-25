package com.legion.api.cache;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.legion.api.common.EnterpriseId;
import com.legion.utils.SimpleUtils;

import java.util.HashMap;

import static com.jayway.restassured.RestAssured.given;

public class CacheAPI {

    public static void refreshTemplateCache(String username, String password) {
        try {
            Response response = given().params("enterpriseName",System.getProperty("enterprise"),"sourceSystem","legion","passwordPlainText",password,"userName",username)
                    .when().get("https://rc-enterprise.dev.legion.work/legion/authentication/login").then().statusCode(200).extract().response();
            String sessionId = response.header("sessionid");
            Response cacheResponse = given().log().all().header("sessionId", sessionId).param("cacheType", "Template").when().get("https://rc-enterprise.dev.legion.work/legion/cache/refreshCache").then().log().all().extract().response();
            cacheResponse.then().statusCode(204);
        } catch (Exception e) {
            SimpleUtils.report("Failed to refresh the cache!");
        }
    }
}
