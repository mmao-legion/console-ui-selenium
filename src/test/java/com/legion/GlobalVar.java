package com.legion;

import com.aventstack.extentreports.Status;
import com.legion.tests.testframework.ExtentTestManager;
import com.legion.utils.ListenerUtils;
import com.legion.utils.SimpleUtils;

import java.util.HashMap;
import java.util.Map;

import static com.legion.tests.TestBase.loadURL;
import static com.legion.utils.MyThreadLocal.setURL;

public class GlobalVar {

    public static Map<String, String> propertyMap = SimpleUtils.getParameterMap();
    public static final String Base_SERVER = "BASE_SERVER";
    public static String Base_SERVER_URL =propertyMap.get("QAURL")+"legion";
//    public static String Base_SERVER_URL ="https://dashboard.juhe.cn/server";

    public static Map<String, String> COOKIES = new HashMap<>();
    public static Map<String, Object> HEADERS = new HashMap<>();

//retry times and report path
    public final static Integer RETRY_COUNTS = 1;

    public static String REPORT_PATH = "target/";

    public final static ListenerUtils listenerUtils = new ListenerUtils();
}
