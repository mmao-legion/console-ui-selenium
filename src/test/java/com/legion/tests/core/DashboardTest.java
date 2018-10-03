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
import com.legion.utils.SimpleUtils;

public class DashboardTest extends TestBase{
	
	private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
    @Automated(automated ="Manual")
	@Owner(owner = "Gunjan")
	@TestName(description = "LEG-4961: Should be able to set Location at Global Level")
    @Test(dataProvider = "browsers")
    public void navigateToDashboardFromGlobalSetting(String browser, String version, String os, String pageobject) throws Exception { 
    	SimpleUtils.pass("Navigate to Dashboard Page Successfully!");
    	SimpleUtils.pass("Click on Settings menu");
    	SimpleUtils.pass("Go back to Dashboard assert Dashboard loaded Successfully");
    	SimpleUtils.pass("Click on Settings menu again");
    	SimpleUtils.pass("Click on Global icon present next to Settings at top left section");
    	SimpleUtils.pass("Navigate back to Dashboard Page");
    	SimpleUtils.pass("assert Dashboard page is Loaded Successfully!");	
		
    }


}
