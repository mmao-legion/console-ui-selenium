package com.legion.tests.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.legion.pages.DashboardPage;
import com.legion.pages.SalesForecastPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.StaffingGuidancePage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.testframework.ExtentTestManager;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

public class StaffingGuidanceTest extends TestBase{
	
	private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
	SalesForecastPage schedulePage = null;
	
	@Automated(automated = "Automated")
	@Owner(owner = "Naval")
	@TestName(description = "LEG-2423: As a store manager, can view Staffing Guidance data for current week")
    @Test(dataProvider = "browsers")
    public void staffingGuidanceDataAsStoreManagerTest(String browser, String version, String os, String pageobject)
            throws Exception
    {
    	//To Do Should be separate Test from Schedule test
    	loginToLegionAndVerifyIsLoginDone(propertyMap.get("DEFAULT_USERNAME"), propertyMap.get("DEFAULT_PASSWORD"));
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        dashboardPage.goToToday();
        SchedulePage schedulePage = pageFactory.createConsoleSchedulePage();
        SimpleUtils.assertOnFail( "Schedule Page not loaded Successfully!", schedulePage.isSchedule(),false);
        StaffingGuidancePage staffingGuidancePage = pageFactory.createStaffingGuidancePage();
        staffingGuidancePage.navigateToStaffingGuidanceTab();
        SimpleUtils.assertOnFail( "Staffing Guidance tab not loaded successfully!", staffingGuidancePage.isStaffingGuidanceTabActive(),false);
        
        /*
         * Staffing Guidance for Day View
         */
        staffingGuidancePage.navigateToStaffingGuidanceTabDayView();
        SimpleUtils.assertOnFail( "Staffing Guidance Day View not loaded successfully!", staffingGuidancePage.isStaffingGuidanceForecastTabDayViewActive(),false);
        List<String> staffingGuidanceDayViewTimeDurationLabelsdata = staffingGuidancePage.getStaffingGuidanceForecastDayViewTimeDuration();
        List<Integer> staffingGuidanceDayViewItemsLabelsdata = staffingGuidancePage.getStaffingGuidanceForecastDayViewItemsCount();
        List<Integer> staffingGuidanceDayViewTeamMembersLabelsdata = staffingGuidancePage.getStaffingGuidanceForecastDayViewTeamMembersCount();
        String dayViewTimeDurationAsString = "";
        for(String timeDuration : staffingGuidanceDayViewTimeDurationLabelsdata)
        {
        	dayViewTimeDurationAsString = dayViewTimeDurationAsString + "|" + timeDuration;
        }
        String staffingGuidanceDayViewItemsCountAsString = "";
        int staffingGuidanceDayViewItemsTotalCount = 0;
        for(int itemsCount : staffingGuidanceDayViewItemsLabelsdata)
        {
        	staffingGuidanceDayViewItemsTotalCount = staffingGuidanceDayViewItemsTotalCount + itemsCount;
        	staffingGuidanceDayViewItemsCountAsString = staffingGuidanceDayViewItemsCountAsString + "|" + itemsCount;
        }
        String staffingGuidanceDayViewTeamMembersCountAsString = "";
        int staffingGuidanceDayViewTeamMembersTotalCount = 0;
        for(int itemsCount : staffingGuidanceDayViewTeamMembersLabelsdata)
        {
        	staffingGuidanceDayViewTeamMembersTotalCount = staffingGuidanceDayViewTeamMembersTotalCount + itemsCount;
        	staffingGuidanceDayViewTeamMembersCountAsString = staffingGuidanceDayViewTeamMembersCountAsString + "|" + itemsCount;
        }
        ExtentTestManager.extentTest.get().log(Status.INFO,"Staffing Guidance for Day View");
        ExtentTestManager.extentTest.get().log(Status.INFO,dayViewTimeDurationAsString);
        ExtentTestManager.extentTest.get().log(Status.INFO,staffingGuidanceDayViewItemsCountAsString);
        ExtentTestManager.extentTest.get().log(Status.INFO,staffingGuidanceDayViewTeamMembersCountAsString);
        SimpleUtils.assertOnFail( "Staffing Guidance Day View items Count is Zero!", (staffingGuidanceDayViewItemsTotalCount != 0),true);
        SimpleUtils.assertOnFail( "Staffing Guidance Day View Team Memners Count is Zero!", (staffingGuidanceDayViewTeamMembersTotalCount != 0),true);
        
        /*
         * Staffing Guidance for Week View
         */
        staffingGuidancePage.navigateToStaffingGuidanceTabWeekView();
        SimpleUtils.assertOnFail( "Staffing Guidance Week View not loaded successfully!", staffingGuidancePage.isStaffingGuidanceForecastTabWeekViewActive(),false);
        List<String> staffingGuidanceWeekViewDurationLabels = staffingGuidancePage.getStaffingGuidanceDayDateMonthLabelsForWeekView();
        List<Float> staffingGuidanceWeekViewDaysHours = staffingGuidancePage.getStaffingGuidanceHoursCountForWeekView();
        String weekViewDurationDataAsString = "";
        for(String weekViewDuration : staffingGuidanceWeekViewDurationLabels)
        {
        	weekViewDurationDataAsString = weekViewDurationDataAsString + "|" + weekViewDuration;
        }
        String staffingGuidanceWeekViewDaysHoursAsString = "";
        Float staffingGuidanceWeekViewHoursTotalCount = (float) 0;
        for(Float staffingGuidanceWeekViewDayHours : staffingGuidanceWeekViewDaysHours)
        {
        	staffingGuidanceWeekViewHoursTotalCount = staffingGuidanceWeekViewHoursTotalCount + staffingGuidanceWeekViewDayHours;
        	staffingGuidanceWeekViewDaysHoursAsString = staffingGuidanceWeekViewDaysHoursAsString + "|" + staffingGuidanceWeekViewDayHours;
        }
        ExtentTestManager.extentTest.get().log(Status.INFO,"Staffing Guidance for Week View");
        ExtentTestManager.extentTest.get().log(Status.INFO,weekViewDurationDataAsString);
        ExtentTestManager.extentTest.get().log(Status.INFO,staffingGuidanceWeekViewDaysHoursAsString);
        SimpleUtils.assertOnFail( "Staffing Guidance Week View Hours Count is Zero!", (staffingGuidanceWeekViewHoursTotalCount != 0),true);
        staffingGuidancePage.clickOnStaffingGuidanceAnalyzeButton();
        List<HashMap<String, String>> analyzePopupStaffingGuidanceData = staffingGuidancePage.getAnalyzePopupStaffingGuidanceAndLatestVersionData();
        for(HashMap<String, String> analyzePopupData : analyzePopupStaffingGuidanceData)
        {
        	for(Map.Entry<String, String> entry : analyzePopupData.entrySet())
            {
            	System.out.println(entry.getKey() +" : "+entry.getValue());
            }
        }
        
    }
	
	
	@Automated(automated = "Manual")
	@Owner(owner = "Manideep")
	@TestName(description = "LEG-2423: Weekly Guidance Hours Should match the sum of individual day working hours (Failed with Jira Ticket#4923)")
    @Test(dataProvider = "browsers")
    public void weeklyGuidanceHoursShouldMatchTheSumOfEachDay(String browser, String version, String os, String pageobject)
            throws Exception
    {
		SimpleUtils.pass("Login as Store Manager Successfully");
		SimpleUtils.pass("Successfully opened the Schedule app");
		SimpleUtils.pass("Open a Staffing Guidance of any Week (Not necessarily the current week) in Week view ");
        SimpleUtils.pass("Staffing Guidance hours not matching with the sum of individual day working hours"); 
    }
	
	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@TestName(description = "LEG-2423: Weekly Guidance Hours Should match the sum of Work Roles Enabled")
    @Test(dataProvider = "browsers")
    public void weeklyGuidanceHoursShouldMatchTheSumOfWorkRolesEnabled(String browser, String version, String os, String pageobject)
            throws Exception
    {

		SimpleUtils.pass("Login as Store Manager Successfully");
		SimpleUtils.pass("Successfully opened the Schedule app");
		SimpleUtils.pass("Open a Staffing Guidance of any Week (Not necessarily the current week) in Week view ");
		SimpleUtils.pass("Select Work Roles from dropdown and it should match with the Weekly Guidance hours"); 
		SimpleUtils.pass("Select Work Roles from dropdown and assert value of Work roles which are not enabled should be zero"); 
    }
	
	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@TestName(description = "LEG-5005: Refresh Guidance in LegionTech shows different guidance hours")
    @Test(dataProvider = "browsers")
    public void staffingGuidanceShowsDiffGuidanceHour(String browser, String version, String os, String pageobject)
            throws Exception
    {

		SimpleUtils.pass("Login to Legiontech Successfully");
		SimpleUtils.pass("Successfully opened the Schedule app");
		SimpleUtils.pass("Open a Staffing Guidance of 09/23 Week view ");
		SimpleUtils.pass("Data in Staffing Guidance table is same as yesterday"); 
    }
	
	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@TestName(description = "LEG-5037: Staffing guidance page gets blank on doing a refresh")
    @Test(dataProvider = "browsers")
    public void staffingGuidanceShouldNotBeBlankOnRefresh(String browser, String version, String os, String pageobject)
            throws Exception
    {

		SimpleUtils.pass("Login to reverted environement Successfully");
		SimpleUtils.pass("Successfully opened the Schedule app");
		SimpleUtils.pass("Open a Staffing Guidance of any Week (Not necessarily the current week) in Week view ");
		SimpleUtils.pass("Click Refresh button"); 
		SimpleUtils.pass("Data in Staffing Guidance table is not getting disappear"); 
    }
	

	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@TestName(description = "LEG-5062 : Items section of Day View on Staffing Guidance tab has no data on LegionCoffee env")
    @Test(dataProvider = "browsers")
    public void itemsOnstaffingGuidanceIsBlank(String browser, String version, String os, String pageobject)
            throws Exception
    {

		SimpleUtils.pass("Login to LegionCoffee environment Successfully");
		SimpleUtils.pass("Successfully opened the Schedule app");
		SimpleUtils.pass("Open a day view in Staffing Guidance of any Week");
		SimpleUtils.pass("assert Items section should not be empty.");
    }

	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@TestName(description = "LEG-5063 : For Bay Area location, Analyze section is showing Polo Alto by default even for Bay Area under Schedule History of Staffing Guidance")
    @Test(dataProvider = "browsers")
    public void analyzeShowsDifferentLocationInScheduleHistoryOfBayArea(String browser, String version, String os, String pageobject)
            throws Exception
    {

		SimpleUtils.pass("Login to LegionCoffee environment Successfully");
		SimpleUtils.pass("Successfully opened the Schedule app");
		SimpleUtils.pass("Open a day view in Staffing Guidance of any Week");
		SimpleUtils.pass("Click Analyze button");
		SimpleUtils.pass("location is configured to show data from three different locations");
    }

	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@TestName(description = "LEG-5108:Wages showing as zero for certain work roles having non-0 staffing guidance hour in LegionCoffee")
	@Test(dataProvider = "browsers")
	public void wagesAreZeroForGuidanceHourValue(String browser, String version, String os, String pageobject)
	          throws Exception
	{
	       SimpleUtils.pass("Login to LegionCoffee Successfully");
	       SimpleUtils.pass("Navigate to Staffing Guidance tab under Schedule tab");
	       SimpleUtils.pass("Open Guidance for Oct1-Oct7");
	       SimpleUtils.pass("Select Key Manager in all work role filter");
	       SimpleUtils.pass("assert for Non-0 Guidance hour schedule wages should be Non-0 ");
	}

	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@TestName(description = "LEG-4923:After adding individual Guidance Hrs of each Day present in the week is not equals to Total Guidance Hrs of the week")
	@Test(dataProvider = "browsers")
	public void sumOfGuidanceHourNotEqualToTotalGuidanceHour(String browser, String version, String os, String pageobject)
	          throws Exception
	{
	       SimpleUtils.pass("Login to environment Successfully");
	       SimpleUtils.pass("Navigate to Staffing Guidance tab open any week");
	       SimpleUtils.pass("assert sum of individual guidance hour should be equal to total guidance hour ");

	}



}
