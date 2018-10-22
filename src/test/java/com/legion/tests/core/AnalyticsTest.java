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
    	
   @Automated(automated ="Automated")
   @Owner(owner = "Naval")
   @TestName(description = "Verify Analytics flow")
   @Test(dataProvider = "usersDataCredential")
   public void gotoAnalyticsPageTest(String username, String password, String location) throws Exception {
	   loginToLegionAndVerifyIsLoginDone(username,password);
	    LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
	    locationSelectorPage.changeLocation(location);
	    AnalyticsPage analyticsPage = pageFactory.createConsoleAnalyticsPage();
		analyticsPage.gotoAnalyticsPage();	
		
   }

}
