package com.legion.tests.core;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.legion.pages.LocationSelectorPage;
import com.legion.pages.LoginPage;
import com.legion.pages.ScheduleOverviewPage;
import com.legion.pages.SchedulePage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.core.ScheduleTest.SchedulePageSubTabText;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

public class ScheduleOverviewTest extends TestBase{

	private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
    @Automated(automated ="Automated")
	@Owner(owner = "Naval")
	@TestName(description = "change the location from Dashboard!")
    @Test(dataProvider = "browsers")
    public void changeLocationTest(String browser, String version, String os, String pageobject) throws Exception { 
    	LoginPage loginPage = pageFactory.createConsoleLoginPage();
	    loginPage.loginToLegionWithCredential(propertyMap, propertyMap.get("DEFAULT_USERNAME"), propertyMap.get("DEFAULT_PASSWORD"));
	    boolean isLoginDone = loginPage.isLoginDone();
	    loginPage.verifyLoginDone(isLoginDone);		
	    LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
	    locationSelectorPage.changeLocation("Legion Coffee Mock Store");
    }
    
    @Automated(automated ="Automated")
	@Owner(owner = "Naval")
	@TestName(description = "Check the 1st week is highlighted and DateAndDay are correct")
    @Test(dataProvider = "browsers")
    public void verifyCurrentWeekDateAndDay(String browser, String version, String os, String pageobject) throws Exception { 
    	LoginPage loginPage = pageFactory.createConsoleLoginPage();
	    loginPage.loginToLegionWithCredential(propertyMap, propertyMap.get("DEFAULT_USERNAME"), propertyMap.get("DEFAULT_PASSWORD"));
	    boolean isLoginDone = loginPage.isLoginDone();
	    loginPage.verifyLoginDone(isLoginDone);
	    SchedulePage schedulePage = pageFactory.createConsoleSchedulePage();
	    schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
        Map<String, String> currentWeekCalendarWeekDaysAndStartDay = scheduleOverviewPage.getWeekStartDayAndCurrentWeekDates();
        System.out.println("currentWeekCalendarWeekDaysAndStartDay.get(\"weekStartDay\")" + currentWeekCalendarWeekDaysAndStartDay.get("weekStartDay"));
    }
    
    
}
