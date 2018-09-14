package com.legion.tests.core;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.legion.pages.AnalyticsPage;
import com.legion.pages.LoginPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.utils.JsonUtil;

public class AnalyticsTest extends TestBase{
	
	private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
    @Automated(automated =  "/"+"Automated]")
	@Owner(owner = "[Naval")
	@TestName(description = ":Verify Analytics flow")
    @Test(dataProvider = "browsers")
    public void gotoAnalyticsPageTest(String browser, String version, String os, String pageobject) throws Exception { 
    	LoginPage loginPage = pageFactory.createConsoleLoginPage();
		AnalyticsPage analyticsPage = pageFactory.createConsoleAnalyticsPage(); 
	    loginPage.goToDashboardHome(propertyMap);
	//         loginPage.loginToLegionWithCredential(propertyMap, propertyMap.get("DEFAULT_USERNAME"), propertyMap.get("DEFAULT_PASSWORD"));
	    boolean isLoginDone = loginPage.isLoginDone();
	    loginPage.verifyLoginDone(isLoginDone);
		analyticsPage.gotoAnalyticsPage();
		
    }

}
