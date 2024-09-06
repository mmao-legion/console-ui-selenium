package com.legion.utils;

import com.legion.api.login.LoginAPI;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;


public class LegionRestAPI {

	public static JSONObject getLegionAPIResponse(String requestURL, Map<String, Object> requestParams)
	{
		JSONObject legionResponse = new JSONObject();
		try {
			RestAssured.baseURI = requestURL;
			RequestSpecification request = RestAssured.given();
			request.queryParams(requestParams);
			Response response = request.get();
			int statusCode = response.getStatusCode();
			SimpleUtils.assertOnFail( response.body().asString() , (statusCode == 200), true);
			JSONParser parser = new JSONParser();
			legionResponse = (JSONObject) parser.parse(response.body().asString());
		}
		catch (ParseException parseException) {
			System.err.println(parseException.getMessage());
			SimpleUtils.fail(parseException.getMessage(), true);
		}
		return legionResponse;
	}

	public static JSONObject getLegionAPIResponseWithBothHeaderAndParams(String requestURL, Map<String, Object> requestParams, Map<String, Object> requestHeaders){
		JSONObject legionAPIResponse = new JSONObject();
		try {
			RestAssured.baseURI = requestURL;
			RequestSpecification request = RestAssured.given();
			request.queryParams(requestParams);
			request.headers(requestHeaders);
			Response response = request.get();
			int statusCode = response.getStatusCode();
			SimpleUtils.assertOnFail( response.body().asString() , (statusCode == 200), true);
			JSONParser parser = new JSONParser();
			legionAPIResponse = (JSONObject) parser.parse(response.body().asString());
		}
		catch (ParseException parseException) {
			System.err.println(parseException.getMessage());
			SimpleUtils.fail(parseException.getMessage(), true);
		}
		return legionAPIResponse;
	}
	public static void getAllLocationsFromEnterprise(String accesstoken){
		String requestURL = System.getProperty("env")+ "legion/v2/locations";
		Map<String, Object> requestParams = new HashMap<>();
		Map<String, Object> requestHeaders = new HashMap<>();
		JSONObject legionAPIResponse = new JSONObject();
		requestParams.put("page", "0");
		requestParams.put("size", "5000");
		requestHeaders.put("Content-Type", "application/json");
		requestHeaders.put("accessToken", accesstoken);
		legionAPIResponse = getLegionAPIResponseWithBothHeaderAndParams(requestURL, requestParams, requestHeaders);
		JSONArray record = (JSONArray) legionAPIResponse.get("records");
		System.out.println(record);
	}
	public static Map<String, Object> getConfigTemplateNameForBusiness(List<String> businessIds, String templateType, String username,
																	   String password){
		Map<String, Object> requestParams = new HashMap<>();
		Map<String, Object> requestHeaders = new HashMap<>();
		JSONObject legionAPIResponse = new JSONObject();
		ArrayList<HashMap<String, Object>> templateInfo = new ArrayList<>();
		String versionNumber;
		String templateName;
		String instanceIds;
		Map<String, Object> parametersList = new HashMap<>();
		try{
			String requestURL = System.getProperty("env")+ "legion/configTemplate/getBusinessLevelTemplate";
			String sessionId = LoginAPI.getSessionIdFromLoginAPI(username, password);
			for (String businessId : businessIds) {
				requestParams.put("businessId", businessId);
				requestParams.put("type", templateType);
				requestHeaders.put("Content-Type", "application/json");
				requestHeaders.put("sessionId", sessionId);
				legionAPIResponse = getLegionAPIResponseWithBothHeaderAndParams(requestURL, requestParams, requestHeaders);
				JSONObject record = (JSONObject) legionAPIResponse.get("record");
				if (record != null) {
					versionNumber = JsonUtil.getJsonObjectValue(legionAPIResponse.toString(), "record", "version");
					templateName = JsonUtil.getJsonObjectValue(legionAPIResponse.toString(), "record", "name");
					instanceIds = JsonUtil.getJsonObjectValue(legionAPIResponse.toString(), "record", "instanceId");
					parametersList.put("version", versionNumber);
					parametersList.put("name", templateName);
					parametersList.put("instanceId", instanceIds);
				} else {
					System.out.println("Record is null");
				}
			}
		} catch (ClassCastException e) {
			System.err.println("Type casting issue: " + e.getMessage());
			SimpleUtils.fail(e.getMessage(), true);
		}catch (Exception e) {
			System.err.println(e.getMessage());
			SimpleUtils.fail(e.getMessage(), true);
		}
		return parametersList;
	}
	public static int postLegionAPIWithFileUpload(String requestURL, Map<String, Object> requestParams, Map<String, Object> requestHeaders, String path){
		JSONObject legionAPIResponse = new JSONObject();
		int statusCode = 0;
		try {
			RestAssured.baseURI = requestURL;
			RequestSpecification request = RestAssured.given();
			request.queryParams(requestParams);
			request.headers(requestHeaders);
			File file = new File(path);
			if (!file.exists()) {
				throw new FileNotFoundException("File not found: " + path);
			}
			request.multiPart("file", file);
			Response response = request.post();
			statusCode = response.getStatusCode();
			SimpleUtils.assertOnFail( response.body().asString() , (statusCode == 200), true);
		}catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}catch (Exception e){
			System.err.println(e.getMessage());
			SimpleUtils.fail(e.getMessage(), true);
		}
		return statusCode;
	}
	public static int postBudgetUpload(String username, String password, String filename, String path){
		int statusCode = 0;
		try{
			String requestURL = System.getProperty("env")+ "legion/integration/test/testUploadBudgetData";
			String sessionId = LoginAPI.getSessionIdFromLoginAPI(username, password);
			Map<String, Object> requestParams = new HashMap<>();
			Map<String, Object> requestHeaders = new HashMap<>();
			requestParams.put("isTest", false);
			requestParams.put("encrypted", false);
			requestParams.put("fileName", filename);
			requestHeaders.put("sessionId", sessionId);
			statusCode = postLegionAPIWithFileUpload(requestURL, requestParams, requestHeaders, path);
		}catch (Exception e) {
			System.err.println(e.getMessage());
			SimpleUtils.fail(e.getMessage(), true);
		}
		return statusCode;
	}
}
