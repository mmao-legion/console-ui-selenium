package com.legion.utils;

import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Headers;
import com.legion.entity.TestStep;
import com.legion.enums.HttpType;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.legion.GlobalVar.COOKIES;
import static com.legion.GlobalVar.HEADERS;
import static com.legion.GlobalVar.listenerUtils;
import static com.jayway.restassured.RestAssured.config;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.config.JsonConfig.jsonConfig;
import static com.jayway.restassured.path.json.config.JsonPathConfig.NumberReturnType.DOUBLE;


public class HttpUtils {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private RestAssuredConfig restAssuredConfig;
    private Response response;
    private String baseURL;



    HttpUtils(String url) {
        baseURL = url;
        restAssuredConfig = config()
                .jsonConfig(jsonConfig().numberReturnType(DOUBLE));
    }



    private String getRequestInfo(String path, Map<String, Object> params) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String key : params.keySet()) {
            stringBuilder.append(key).append("=").append(params.get(key)).append("&");
        }

        if (stringBuilder.length() >= 1 && stringBuilder.toString().endsWith("&")) {
            stringBuilder = new StringBuilder(stringBuilder.substring(0, stringBuilder.length() - 1));
        }

        return getRequestInfo(path) + "?" + stringBuilder;
    }

    private String getRequestInfoWithHeader(String path, Map<String, Object> header) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String key : header.keySet()) {
            stringBuilder.append(key).append("=").append(header.get(key)).append("&");
        }

        if (stringBuilder.length() >= 1 && stringBuilder.toString().endsWith("&")) {
            stringBuilder = new StringBuilder(stringBuilder.substring(0, stringBuilder.length() - 1));
        }

        return getRequestInfo(path) + "?" + stringBuilder;
    }
    private String getRequestInfo(String path, Map<String, Object> params, Map<String, Object> header) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String key : params.keySet()) {
            stringBuilder.append(key).append("=").append(params.get(key)).append("&");
        }

        if (stringBuilder.length() >= 1 && stringBuilder.toString().endsWith("&")) {
            stringBuilder = new StringBuilder(stringBuilder.substring(0, stringBuilder.length() - 1));
        }
        StringBuilder stringBuilderHeader = new StringBuilder();

        for (String key : header.keySet()) {
            stringBuilderHeader.append(key).append("=").append(header.get(key)).append("&");
        }

        if (stringBuilderHeader.length() >= 1 && stringBuilderHeader.toString().endsWith("&")) {
            stringBuilderHeader = new StringBuilder(stringBuilderHeader.substring(0, stringBuilderHeader.length() - 1));
        }

        return getRequestInfo(path) + "?" + stringBuilder + stringBuilderHeader;
    }


    private String getRequestInfo(String path) {
        return RestAssured.baseURI + path;
    }


    private String getResponseInfo(Response response) {


        if (response.contentType().contains("json")) {
            return "[" + response.statusCode() + "]" + response.jsonPath().get();
        } else {
            return "[" + response.statusCode() + "]" + response.htmlPath().get();
        }
    }


    private RequestSpecification getRequestSpecification(String path) {
        return given().headers(HEADERS).cookies(COOKIES).config(restAssuredConfig).basePath(path);
    }


    private Response request(HttpType httpType, String path, Map<String, Object> params,Map<String, Object> header) {
        logger.info("[" + httpType.getValue() + "]" + getRequestInfo(path, params,header));

        switch (httpType) {
            case GET:
                response = getRequestSpecification(path).headers(header).params(params).get();
                break;
            case POST:
                response = getRequestSpecification(path).headers(header).params(params).post();
                break;
            default:
                throw new RuntimeException(String.format("not support %s request type", httpType));
        }

        return response;
    }
    private Response request(HttpType httpType, String path, Map<String, Object> params) {
        logger.info("[" + httpType.getValue() + "]" + getRequestInfo(path, params));

        switch (httpType) {
            case GET:
                response = getRequestSpecification(path).params(params).get();
                break;
            case POST:
                response = getRequestSpecification(path).params(params).post();
                break;
            default:
                throw new RuntimeException(String.format("not support %s request type", httpType));
        }

        return response;
    }

    private Response requestWithHeader(HttpType httpType, String path, Map<String, Object> header) {
        logger.info("[" + httpType.getValue() + "]" + getRequestInfoWithHeader(path, header));

        switch (httpType) {
            case GET:
                response = getRequestSpecification(path).headers(header).get();
                break;
            case POST:
                response = getRequestSpecification(path).headers(header).post();
                break;
            default:
                throw new RuntimeException(String.format("not support %s request type", httpType));
        }

        return response;
    }

    private Response request(HttpType httpType, String path) {
        logger.info("[" + httpType.getValue() + "]" + getRequestInfo(path));

        switch (httpType) {
            case GET:
                response = getRequestSpecification(path).get();
                break;
            case POST:
                response = getRequestSpecification(path).post();
                break;
            default:
                throw new RuntimeException(String.format("not support this request type", httpType));
        }

        return response;
    }



    public Response request(TestStep testStep) {
        RestAssured.baseURI = baseURL;

        if (!testStep.getParams().isEmpty()) {
            response = request(testStep.getType(), testStep.getPath(), testStep.getParams());
        }else if(!testStep.getHeader().isEmpty()){
            response = requestWithHeader(testStep.getType(), testStep.getPath(), testStep.getHeader());
        }else{
            response = request(testStep.getType(), testStep.getPath());
        }


        logger.info(getResponseInfo(response));


        if (response.contentType().contains("json")) {
            listenerUtils.testCaseResult.setDescription(response.jsonPath().get().toString());
        } else {
            listenerUtils.testCaseResult.setDescription(response.htmlPath().get().toString());
        }

        return response;
    }

}

