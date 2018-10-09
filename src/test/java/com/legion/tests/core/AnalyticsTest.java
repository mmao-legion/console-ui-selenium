package com.legion.tests.core;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.legion.pages.AnalyticsPage;
import com.legion.pages.LocationSelectorPage;
import com.legion.pages.LoginPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.utils.JsonUtil;

public class AnalyticsTest extends TestBase{
    
//	 @BeforeClass()
//	 public void beforeEachClass() throws Exception {
//	    initialize();
//	    LoginPage loginPage = pageFactory.createConsoleLoginPage();
//		AnalyticsPage analyticsPage = pageFactory.createConsoleAnalyticsPage();
//		LocationSelectorPage consoleLocationSelectorPage = pageFactory.createLocationSelectorPage();
//		loginPage.loginToLegionWithCredential(propertyMap, propertyMap.get("DEFAULT_USERNAME"), propertyMap.get("DEFAULT_PASSWORD"));
//	    boolean isLoginDone = loginPage.isLoginDone();
//	    loginPage.verifyLoginDone(isLoginDone);
//	    consoleLocationSelectorPage.changeLocation(propertyMap.get("location"));
//				
//	 }
	
	
   @Automated(automated ="Automated")
	@Owner(owner = "Naval")
	@TestName(description = "Verify Analytics flow")
   @Test(dataProvider = "usersDataCredential")
   public void gotoAnalyticsPageTest(String username, String password, String location) throws Exception { 
   		LoginPage loginPage = pageFactory.createConsoleLoginPage();
		AnalyticsPage analyticsPage = pageFactory.createConsoleAnalyticsPage();
		LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
	    loginPage.goToDashboardHomePage(username,password);
	    boolean isLoginDone = loginPage.isLoginDone();
	    loginPage.verifyLoginDone(isLoginDone);
	    locationSelectorPage.changeLocation(location);
		analyticsPage.gotoAnalyticsPage();	
		
   }

}
