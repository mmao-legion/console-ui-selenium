package com.legion.api;


import com.legion.GlobalVar;
import com.legion.tests.annotations.*;
import com.jayway.restassured.response.Response;


import java.util.Map;

@SERVER(GlobalVar.Base_SERVER)
public interface ApiList {

    @GET(path = "/authentication/login", description = "login with params")
    Response login(@Param("enterpriseName") String enterpriseName, @Param("sourceSystem") String sourceSystem,
                   @Param("userName") String userName, @Param("passwordPlainText") String passwordPlainText);

    @POST(path = "/authentication/login", description = "login")
    Response login(@Header("Content-Type") String contentType,@Param("enterpriseName") String enterpriseName, @Param("sourceSystem") String sourceSystem,
                   @Param("userName") String userName, @Param("passwordPlainText") String passwordPlainText);

    @GET(path = "/business/getStatusAndDueLocationCounts", description = "getlocation")
    Response getLocation(@Header("sessionId") String sessionId);

    @GET(path = "/schedule/rebuildSearchIndex", description = "rebuild Search Index in admin tab")
    Response rebuildSearchIndex(@Header("sessionId") String sessionId, @Param("enterpriseId") String enterpriseId);

    @GET(path = "/business/queryABSwitch", description = "query ABSwitch by switch name")
    Response queryABSwitch(@Param("switchName") String switchName, @Header("sessionId") String sessionId,
                           @Header("Host") String host,@Header("Accept-Encoding") String AcceptEncoding,
                           @Header("Connection") String Connection
    );

    @GET(path = "/business/queryABSwitch", description = "query ABSwitch by switch name")
    Response queryABSwitch(@Param("switchName") String switchName, @Header("sessionId") String sessionId);

    @GET(path = "/business/queryCategory", description = "query category")
    Response queryCategory(@Header("sessionId") String sessionId);


    @GET(path = "/business/switches", description = "query ABSwitch by switch name")
    Response switches(@Header("sessionId") String sessionId);

    @GET(path = "/configTemplate/getSingletonVersion", description = "query singleton version")
    Response getSingletonVersion(@Header("sessionId") String sessionId,@Param("type ") String type);

}