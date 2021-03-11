package com.legion.tests.core;

import com.legion.api.ApiList;
import com.legion.runner.BaseRunner;
import com.legion.utils.ProxyUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class DataPreparationTest extends BaseRunner {

    private ApiList apiList = ProxyUtils.create(ApiList.class);


    String sessionId = null;
    String enterpriseId = null;
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test()
    public void Login() throws Exception {

        response = apiList.login("op","legion","Estelle.SM1","Estelle.SM1");
        response.then().statusCode(200);
        sessionId = response.header("sessionId");
        enterpriseId = response.jsonPath().get("enterprise.enterpriseId").toString();

//        //queryABSwitch
//        response = apiList.queryABSwitch("OPView",sessionId,"rc-enterprise.dev.legion.work","gzip,deflate,br","keep-alive");
//        response.then().statusCode(200);

//        response = apiList.switches(sessionId);
//        response.then().statusCode(200);

    }












}
