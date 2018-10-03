package com.legion.tests.core;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.legion.pages.DashboardPage;
import com.legion.pages.LoginPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.SalesForecastPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.testframework.ExtentTestManager;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

public class SalesForecastTest extends TestBase{

	public enum SalesForecastForecastCalenderWeekCount{
		ZERO(0),
		One(1),
		TWO(2);		
		private final int value;
		SalesForecastForecastCalenderWeekCount(final int newValue) {
            value = newValue;
        }
        public int getValue() { return value; }
	}
	
	private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
	
	SalesForecastPage schedulePage = null;
	@Automated(automated ="Automated")
	@Owner(owner = "Naval")
	@TestName(description = "LEG-2422: As a store manager, can view Projected Sales Forecast data for past and current week")
    @Test(dataProvider = "browsers")
    public void salesForecastDataAsStoreManagerTest(String browser, String version, String os, String pageobject)
            throws Exception
    {
    	//To Do Should be separate Test from Schedule test
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.loginToLegionWithCredential(propertyMap, propertyMap.get("DEFAULT_USERNAME"), propertyMap.get("DEFAULT_PASSWORD"));
        SimpleUtils.assertOnFail( "Login Failed!", loginPage.isLoginDone(),false);
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        dashboardPage.goToToday();
        SchedulePage schedulePage = pageFactory.createConsoleSchedulePage();
        SimpleUtils.assertOnFail( "Schedule Page not loaded Successfully!", schedulePage.isSchedule(),false);
        SalesForecastPage salesForecastPage = pageFactory.createSalesForecastPage();  
        salesForecastPage.navigateToSalesForecastTab();
        SimpleUtils.assertOnFail( "Projected Sales Tab not Active!",salesForecastPage.isSalesForecastTabActive() ,false);
        
        /*
         * Schedule Projected Sales as Week View
         */
        salesForecastPage.navigateToSalesForecastTabWeekView();
        SimpleUtils.assertOnFail( "Projected Sales Forecast Tab Week View not loaded successfully!",salesForecastPage.isSalesForecastTabWeekViewActive() ,false);
        SimpleUtils.assertOnFail( "Projected Sales Item Options/Categories With User Job Title not matched!",salesForecastPage.validateSalesForecastItemOptionWithUserJobTitle("Manager") ,true);
        //pass("Shedule page Projected Sales Item Option/Categories With User Job Title matched!");
        Map<String, String> dayMonthDateFormatForCurrentPastAndFutureWeek = SimpleUtils.getDayMonthDateFormatForCurrentPastAndFutureWeek(SimpleUtils.getCurrentDateDayOfYear(), SimpleUtils.getCurrentISOYear());
        String currentWeekDate = (String)dayMonthDateFormatForCurrentPastAndFutureWeek.get("currentWeekDate");
        String pastWeekDate = (String)dayMonthDateFormatForCurrentPastAndFutureWeek.get("pastWeekDate");
        String futureWeekDate = (String)dayMonthDateFormatForCurrentPastAndFutureWeek.get("futureWeekDate");
        SimpleUtils.assertOnFail( "Map return currentWeekDate as 'null'!",(currentWeekDate != null) ,false);
        SimpleUtils.assertOnFail( "Map return pastWeekDate as 'null'!",(pastWeekDate != null) ,true);
        SimpleUtils.assertOnFail( "Map return futureWeekDate as 'null'!",(futureWeekDate != null) ,true);
        
        /*
         * Projected Sales forecast for current week
         */
        SimpleUtils.assertOnFail( "Projected Sales Current Week View not Loaded Successfully!",salesForecastPage.validateWeekViewWithDateFormat(currentWeekDate) ,true);
		Map<String, String> currentWeekSalesForecastCardsData =  salesForecastPage.getSalesForecastForeCastData();
        salesForecastWeeksViewForeCastData(currentWeekSalesForecastCardsData, "Current Week");
       
        
        /*
         * Projected Sales forecast for Past week
         */
        salesForecastPage.navigateSalesForecastWeekViewTpPastOrFuture("Previous Week", SalesForecastForecastCalenderWeekCount.One.getValue());
        SimpleUtils.assertOnFail( "Projected Sales Previous Week View not Loaded Successfully!",salesForecastPage.validateWeekViewWithDateFormat(pastWeekDate) ,true);
        Map<String, String> previousWeekSalesForecastCardsData =  salesForecastPage.getSalesForecastForeCastData();
        salesForecastWeeksViewForeCastData(previousWeekSalesForecastCardsData, "Previous Week");
        
        
        /*
         * Projected Sales forecast for Future week
         */
        salesForecastPage.navigateSalesForecastWeekViewTpPastOrFuture("Future Week", SalesForecastForecastCalenderWeekCount.TWO.getValue());
        SimpleUtils.assertOnFail( "Projected Sales Future Week View not Loaded Successfully!",salesForecastPage.validateWeekViewWithDateFormat(futureWeekDate) ,true);
        Map<String, String> futureWeekSalesForecastCardsData =  salesForecastPage.getSalesForecastForeCastData();
        salesForecastWeeksViewForeCastData(futureWeekSalesForecastCardsData, "Future Week");
        
    }
	
	
	
	private void salesForecastWeeksViewForeCastData(Map<String, String> WeekSalesForecastCardsData, String weekType) {
		
    	String peakDemandProjected = (String)WeekSalesForecastCardsData.get("peakDemandProjected");
    	String peakDemandActual = (String)WeekSalesForecastCardsData.get("peakDemandActual");
    	String totalDemandProjected = (String)WeekSalesForecastCardsData.get("totalDemandProjected");
    	String totalDemandActual = (String)WeekSalesForecastCardsData.get("totalDemandActual");
    	String peakTimeProjected = (String)WeekSalesForecastCardsData.get("peakTimeProjected");
    	String peakTimeActual = (String)WeekSalesForecastCardsData.get("peakTimeActual");
    	
    	/*
         * Fail on Projected & Actual values are null
         */
    	SimpleUtils.assertOnFail( weekType+" Projected Sales Cards Data Peak Demand Projected is'null'!",(peakDemandProjected != null) ,true);
    	SimpleUtils.assertOnFail( weekType+" Projected Sales Cards Data Peak Demand Actual is'null'!",(peakDemandActual != null) ,true);
    	SimpleUtils.assertOnFail( weekType+" Projected Sales Cards Data Total Demand Projected is'null'!",(totalDemandProjected != null) ,true);
    	SimpleUtils.assertOnFail( weekType+" Projected Sales Cards Data Total Demand Actual is'null'!",(totalDemandActual != null) ,true);
    	SimpleUtils.assertOnFail( weekType+" Projected Sales Cards Data Peak Time Projected is'null'!",(peakTimeProjected != null) ,true);
    	SimpleUtils.assertOnFail( weekType+" Projected Sales Cards Data Peak Time Actual is'null'!",(peakTimeActual != null) ,true);
        /*
         * fail on "N/A" value of Actuals
         */
        if(!weekType.toLowerCase().contains("future"))
        {
        	SimpleUtils.assertOnFail( weekType+" Projected Sales Cards Data Peak Demand Actual is 'N/A'!",(! peakDemandActual.contains("N/A")),true);
        	SimpleUtils.assertOnFail( weekType+" Projected Sales Cards Data Total Demand Actual is 'N/A'!",(! totalDemandActual.contains("N/A")) ,true);
        	SimpleUtils.assertOnFail( weekType+" Projected Sales Cards Data Peak Time Actual is 'N/A'!",(! peakTimeActual.contains("N/A")) ,true);
        }
        
        
        /*
         *  Logging Projected Sales forecast Data card values
         */
        
        ExtentTestManager.extentTest.get().log(Status.INFO, weekType+" Projected Sales Cards Data Peak Demand Projected - "+peakDemandProjected );
        ExtentTestManager.extentTest.get().log(Status.INFO, weekType+" Projected Sales Cards Data Peak Demand Actual - "+peakDemandActual );
        ExtentTestManager.extentTest.get().log(Status.INFO, weekType+" Projected Sales Cards Data Total Demand Projected - "+totalDemandProjected);
        ExtentTestManager.extentTest.get().log(Status.INFO, weekType+" Projected Sales Cards Data Total Demand Actual - "+totalDemandActual );
        ExtentTestManager.extentTest.get().log(Status.INFO, weekType+" Projected Sales Cards Data Peak Time Projected - "+peakTimeProjected );
        ExtentTestManager.extentTest.get().log(Status.INFO, weekType+" Projected Sales Cards Data Peak Time Actual - "+peakTimeActual );
		
	}
	
	
	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@TestName(description = "TP-44: Coffee Cups in All Sales Item filter is not showing any data")
    @Test(dataProvider = "browsers")
    public void shouldAllSalesItemDisplayEnabledFilter(String browser, String version, String os, String pageobject)
            throws Exception
    {
		SimpleUtils.pass("Login as Store Manager Successfully");
		SimpleUtils.pass("Successfully opened the Schedule app");
		SimpleUtils.pass("Navigate to previous weeks in Projected Sales");
		SimpleUtils.pass("Look for the Actual's value for Coffee Cups filter");
		SimpleUtils.pass("If Actuals for the Coffee Cups has some value then assert the presence of Projected Sales graph");
    }
	
	
}
