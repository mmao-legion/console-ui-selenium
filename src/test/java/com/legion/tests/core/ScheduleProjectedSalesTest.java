package com.legion.tests.core;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.legion.pages.DashboardPage;
import com.legion.pages.LoginPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.ScheduleProjectedSalesPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.utils.JsonUtil;

public class ScheduleProjectedSalesTest extends TestBase{

	private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
	ScheduleProjectedSalesPage schedulePage = null;
	@Automated(automated =  "/"+"Automated]")
	@Owner(owner = "[Naval")
	@TestName(description = "LEG-2422: As a store manager, can view Projected Sales Forecast data for past and current week")
    @Test(dataProvider = "browsers")
    public void projectedSalesForecastDataAsStoreManagerTest(String browser, String version, String os, String pageobject)
            throws Exception
    {
    	//To Do Should be separate Test from Schedule test
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.loginToLegionWithCredential(propertyMap, propertyMap.get("DEFAULT_USERNAME"), propertyMap.get("DEFAULT_PASSWORD"));
        assertOnFail( "Login Failed!", loginPage.isLoginDone(),false);
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        dashboardPage.goToToday();
        SchedulePage schedulePage = pageFactory.createConsoleSchedulePage();
        assertOnFail( "Schedule Page not loaded Successfully!", schedulePage.isSchedule(),false);
        ScheduleProjectedSalesPage scheduleProjectedSalesPage = pageFactory.createScheduleProjectedSalesPage();  
        scheduleProjectedSalesPage.navigateToProjectedSalesForecastTab();
        assertOnFail( "Schedule Projected Sales Tab not Active!",scheduleProjectedSalesPage.isScheduleProjectedSalesTabActive() ,false);
        
        /*
         * Schedule Projected Sales as Week View
         */
        scheduleProjectedSalesPage.navigateToProjectedSalesForecastTabWeekView();
        assertOnFail( "Schedule page Projected Sales Forecast Tab Week View not loaded successfully!",scheduleProjectedSalesPage.isProjectedSalesForecastTabWeekViewActive() ,false);
        assertOnFail( "Schedule page Projected Sales Item Options/Categories With User Job Title not matched!",scheduleProjectedSalesPage.validateProjectedSalesItemOptionWithUserJobTitle("Manager") ,true);
        //pass("Shedule page Projected Sales Item Option/Categories With User Job Title matched!");
        Map<String, String> dayMonthDateFormatForCurrentPastAndFutureWeek = getDayMonthDateFormatForCurrentPastAndFutureWeek(getCurrentDateDayOfYear(), getCurrentISOYear());
        String currentWeekDate = (String)dayMonthDateFormatForCurrentPastAndFutureWeek.get("currentWeekDate");
        String pastWeekDate = (String)dayMonthDateFormatForCurrentPastAndFutureWeek.get("pastWeekDate");
        String futureWeekDate = (String)dayMonthDateFormatForCurrentPastAndFutureWeek.get("futureWeekDate");
        assertOnFail( "Map return currentWeekDate as 'null'!",(currentWeekDate != null) ,false);
        assertOnFail( "Map return pastWeekDate as 'null'!",(pastWeekDate != null) ,true);
        assertOnFail( "Map return futureWeekDate as 'null'!",(futureWeekDate != null) ,true);
        
        /*
         * Projected Sales forecast for current week
         */
        assertOnFail( "Shedule page Projected Sales Current Week View not Loaded Successfully!",scheduleProjectedSalesPage.validateWeekViewWithDateFormat(currentWeekDate) ,true);
		Map<String, String> currentWeekProjectedSalesCardsData =  scheduleProjectedSalesPage.getScheduleProjectedSalesForeCastData();
        projectedSalesWeeksViewForeCastData(currentWeekProjectedSalesCardsData, "Current Week");
       
        
        /*
         * Projected Sales forecast for Past week
         */
        scheduleProjectedSalesPage.navigateProjectedSalesWeekViewTpPastOrFuture("Previous Week", 1);
        assertOnFail( "Shedule page Projected Sales Previous Week View not Loaded Successfully!",scheduleProjectedSalesPage.validateWeekViewWithDateFormat(pastWeekDate) ,true);
        Map<String, String> previousWeekProjectedSalesCardsData =  scheduleProjectedSalesPage.getScheduleProjectedSalesForeCastData();
        projectedSalesWeeksViewForeCastData(previousWeekProjectedSalesCardsData, "Previous Week");
        
        
        /*
         * Projected Sales forecast for Future week
         */
        scheduleProjectedSalesPage.navigateProjectedSalesWeekViewTpPastOrFuture("Future Week", 2);
        assertOnFail( "Shedule page Projected Sales Future Week View not Loaded Successfully!",scheduleProjectedSalesPage.validateWeekViewWithDateFormat(futureWeekDate) ,true);
        Map<String, String> futureWeekProjectedSalesCardsData =  scheduleProjectedSalesPage.getScheduleProjectedSalesForeCastData();
        projectedSalesWeeksViewForeCastData(futureWeekProjectedSalesCardsData, "Future Week");
        
    }
	
	
	
	private void projectedSalesWeeksViewForeCastData(Map<String, String> WeekProjectedSalesCardsData, String weekType) {
		
    	String peakDemandProjected = (String)WeekProjectedSalesCardsData.get("peakDemandProjected");
    	String peakDemandActual = (String)WeekProjectedSalesCardsData.get("peakDemandActual");
    	String totalDemandProjected = (String)WeekProjectedSalesCardsData.get("totalDemandProjected");
    	String totalDemandActual = (String)WeekProjectedSalesCardsData.get("totalDemandActual");
    	String peakTimeProjected = (String)WeekProjectedSalesCardsData.get("peakTimeProjected");
    	String peakTimeActual = (String)WeekProjectedSalesCardsData.get("peakTimeActual");
    	
    	/*
         * Fail on Projected & Actual values are null
         */
    	assertOnFail( weekType+" Projected Sales Cards Data Peak Demand Projected is'null'!",(peakDemandProjected != null) ,true);
        assertOnFail( weekType+" Projected Sales Cards Data Peak Demand Actual is'null'!",(peakDemandActual != null) ,true);
        assertOnFail( weekType+" Projected Sales Cards Data Total Demand Projected is'null'!",(totalDemandProjected != null) ,true);
        assertOnFail( weekType+" Projected Sales Cards Data Total Demand Actual is'null'!",(totalDemandActual != null) ,true);
        assertOnFail( weekType+" Projected Sales Cards Data Peak Time Projected is'null'!",(peakTimeProjected != null) ,true);
        assertOnFail( weekType+" Projected Sales Cards Data Peak Time Actual is'null'!",(peakTimeActual != null) ,true);
        /*
         * fail on "N/A" value of Actuals
         */
        if(!weekType.toLowerCase().contains("future"))
        {
        	assertOnFail( weekType+" Projected Sales Cards Data Peak Demand Actual is 'N/A'!",(! peakDemandActual.contains("N/A")),true);
            assertOnFail( weekType+" Projected Sales Cards Data Total Demand Actual is 'N/A'!",(! totalDemandActual.contains("N/A")) ,true);
            assertOnFail( weekType+" Projected Sales Cards Data Peak Time Actual is 'N/A'!",(! peakTimeActual.contains("N/A")) ,true);
        }
        
        
        /*
         *  Logging Projected Sales forecast Data card values
         */
        
        extentTest.log(Status.INFO, weekType+" Projected Sales Cards Data Peak Demand Projected - "+peakDemandProjected );
        extentTest.log(Status.INFO, weekType+" Projected Sales Cards Data Peak Demand Actual - "+peakDemandActual );
        extentTest.log(Status.INFO, weekType+" Projected Sales Cards Data Total Demand Projected - "+totalDemandProjected);
        extentTest.log(Status.INFO, weekType+" Projected Sales Cards Data Total Demand Actual - "+totalDemandActual );
        extentTest.log(Status.INFO, weekType+" Projected Sales Cards Data Peak Time Projected - "+peakTimeProjected );
        extentTest.log(Status.INFO, weekType+" Projected Sales Cards Data Peak Time Actual - "+peakTimeActual );
		
	}
}
