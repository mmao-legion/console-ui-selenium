package com.legion.tests.core;

import com.legion.tests.TestBase;
import com.legion.utils.SimpleUtils;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class LocationNavigationTest extends TestBase {

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{
        try {
            this.createDriver((String) params[0], "83", "Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
