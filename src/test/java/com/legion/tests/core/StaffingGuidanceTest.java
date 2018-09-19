package com.legion.tests.core;

import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.legion.pages.DashboardPage;
import com.legion.pages.LoginPage;
import com.legion.pages.SalesForecastPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.StaffingGuidancePage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

public class StaffingGuidanceTest extends TestBase{
	
	private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
	SalesForecastPage schedulePage = null;
	@Automated(automated =  "/"+"Automated]")
	@Owner(owner = "[Naval")
	@TestName(description = "LEG-2423: As a store manager, can view Staffing Guidance data for current week")
    @Test(dataProvider = "browsers")
	
	
    public void staffingGuidanceDataAsStoreManagerTest(String browser, String version, String os, String pageobject)
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
        extentTest.log(Status.INFO,"Staffing Guidance for Day View");
        extentTest.log(Status.INFO,dayViewTimeDurationAsString);
        extentTest.log(Status.INFO,staffingGuidanceDayViewItemsCountAsString);
        extentTest.log(Status.INFO,staffingGuidanceDayViewTeamMembersCountAsString);
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
        extentTest.log(Status.INFO,"Staffing Guidance for Week View");
        extentTest.log(Status.INFO,weekViewDurationDataAsString);
        extentTest.log(Status.INFO,staffingGuidanceWeekViewDaysHoursAsString);
        SimpleUtils.assertOnFail( "Staffing Guidance Week View Hours Count is Zero!", (staffingGuidanceWeekViewHoursTotalCount != 0),true);
    }

}
