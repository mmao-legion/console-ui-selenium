package com.legion.tests.core;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.legion.pages.LoginPage;
import com.legion.pages.SchedulePage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.TestName;
import com.legion.utils.JsonUtil;

import org.openqa.selenium.WebDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * Manideep
 */
public class ScheduleTest extends TestBase{
    private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
    SchedulePage schedulePage = null;
    //todo refactor test method so first 7 lines to do driver init & login can be shared by all Console tests
    @TestName(description = "shouldEditPublishedSchedule (Verify Schedule edit Flow functionality")
    @Test(dataProvider = "browsers")
    public void shouldEditPublishedSchedule (String browser, String version, String os, String pageobject)
            throws Exception
    {
        //todo fix failure due to waiting on Edit button on a week without any schedule and add assertion
        
        
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.loginToLegionWithCredential(propertyMap, propertyMap.get("DEFAULT_USERNAME"), propertyMap.get("DEFAULT_PASSWORD"));
//        AssertJUnit.assertTrue("Login Done Successfully", loginPage.isLoginDone());
        SchedulePage schedulePage = pageFactory.createConsoleSchedulePage(); 
        schedulePage.gotoToSchedulePage();
        schedulePage.goToSchedule();
        extentTest.assignCategory("Schedule");
        extentTest.assignAuthor("Legion team");
//        schedulePage.editWeeklySchedule(propertyMap);
    }
    
    
    @Test(priority=2)
    public void createDayViewShiftOnSchedulePageTest()
           throws Exception { 
    	 /*Staffing Option possible values: Auto, Manual or Assign Team Member */
    	String staffingOption1 = "Auto";
    	String staffingOption2 = "Manual";
    	String staffingOption3 = "Assign Team Member";
   	    schedulePage.createShiftOnDayViewSchedulePage(propertyMap, staffingOption1);
   	    //schedulePage.createShiftOnDayViewSchedulePage(propertyMap, staffingOption2);
   	    //schedulePage.createShiftOnDayViewSchedulePage(propertyMap, staffingOption3);
    }
    
   
    
    @Test(priority=3)
    public void getDayViewScheduleProjectedSalesTest()
           throws Exception { 
   	    schedulePage.dayViewScheduleProjectedSales();
    }
}