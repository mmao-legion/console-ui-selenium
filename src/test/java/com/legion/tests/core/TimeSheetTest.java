package com.legion.tests.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.legion.pages.DashboardPage;
import com.legion.pages.LocationSelectorPage;
import com.legion.pages.TimeSheetPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

public class TimeSheetTest extends TestBase{
	
	private static HashMap<String, String> addTimeClockDetails = JsonUtil.getPropertiesFromJsonFile("src/test/resources/AddTimeClock.json");


	@Override
	@BeforeMethod
	public void firstTest(Method method, Object[] params) throws Exception {
		this.createDriver((String) params[0], "68", "Linux");
	      visitPage(method);
	      loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
	}
	
	
	
	@Automated(automated =  "Automated")
	@Owner(owner = "Naval")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "TP-119: Automation TA module : validate if it is possible to delete a clock in entry.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void validateAndDeleteATimeSheetClockAsStoreManager(String browser, String username, String password, String location)
    		throws Exception {
			
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
        
        // Click on "Timesheet" option menu.
        timeSheetPage.clickOnTimeSheetConsoleMenu();
        SimpleUtils.assertOnFail("TimeSheet Page not loaded Successfully!",timeSheetPage.isTimeSheetPageLoaded() , false);
        
        // Spot a timesheet with a clock in and out entered
        // Click on that time sheet to open the edit modal
        
        timeSheetPage.OpenATimeSheetWithClockInAndOut();
        int clickIndex = 0;
        
        // click in the edit button of a clock in
        timeSheetPage.clickOnEditTimesheetClock(clickIndex);
        
        //click in the delete button
        // Validate you are requested to confirm
        timeSheetPage.clickOnDeleteClockButton();
        
        //click on the clock mark to confirm
        ArrayList<String> clockmarksInfo = timeSheetPage.hoverOnClockIconAndGetInfo();
        for(String clockInfo : clockmarksInfo)
        {
        	SimpleUtils.report("Time Sheet Page: Clock Icon Information After Edit: '"+ clockInfo +"'");
        }
        
        timeSheetPage.closeTimeSheetDetailPopUp();
        
    }
	
	
	@Automated(automated =  "Automated")
	@Owner(owner = "Naval")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "TP-112 : Automation TA module : As a Manager or Payroll admin I can add a new Timesheet entry for a TM.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyNewTimesheetEntryAddedAsStoreManager(String browser, String username, String password, String location)
    		throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeLocation(location);
        TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
        
        // Click on "Timesheet" option menu.
        timeSheetPage.clickOnTimeSheetConsoleMenu();
        SimpleUtils.assertOnFail("TimeSheet Page not loaded Successfully!",timeSheetPage.isTimeSheetPageLoaded() , false);
        String timeClockLocation = addTimeClockDetails.get("Location");
        String timeClockDate = addTimeClockDetails.get("Date");
        String timeClockEmployee = addTimeClockDetails.get("Employee");
        String timeClockWorkRole = addTimeClockDetails.get("Work_Role");
        String timeClockStartTime = addTimeClockDetails.get("Shift_Start");
        String timeClockEndTime = addTimeClockDetails.get("Shift_End");
        String timeClockAddNote = addTimeClockDetails.get("Add_Note");
        
        timeSheetPage.addNewTimeClock(timeClockLocation, timeClockDate, timeClockEmployee,timeClockWorkRole, timeClockStartTime, timeClockEndTime, timeClockAddNote);
        timeSheetPage.valiadteTimeClock(timeClockLocation, timeClockDate, timeClockEmployee, timeClockWorkRole, timeClockStartTime, timeClockEndTime, timeClockAddNote);
        timeSheetPage.closeTimeSheetDetailPopUp();
	}
}
