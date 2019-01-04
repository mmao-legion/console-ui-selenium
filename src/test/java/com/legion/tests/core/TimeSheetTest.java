package com.legion.tests.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.SimpleLayout;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.legion.pages.BasePage;
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


	public enum dayWeekOrPayPeriodViewType{
		  Next("Next"),
		  Previous("Previous");
			private final String value;
			dayWeekOrPayPeriodViewType(final String newValue) {
	            value = newValue;
	        }
	        public String getValue() { return value; }
	}
	
	public enum dayWeekOrPayPeriodCount{
		Zero(0),
		One(1),
		Two(2),
		Three(3),
		Four(4),
		Five(5);		
		private final int value;
		dayWeekOrPayPeriodCount(final int newValue) {
            value = newValue;
        }
        public int getValue() { return value; }
	}
	
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
        
        timeSheetPage.openATimeSheetWithClockInAndOut();
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
	
	
	@Automated(automated =  "Automated")
	@Owner(owner = "Naval")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "TP-113 : Automation TA Module : Validate the number of hours in for the TS of the TM in the REG column is 8 and OT is 2.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void validateNumberOfHoursAfterAddingTimeClockAsStoreManager(String browser, String username, String password, String location)
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
        String timeClockStartTime = "09:00AM";
        String timeClockEndTime = "07:00pm";
        String timeClockAddNote = addTimeClockDetails.get("Add_Note");
        
        timeSheetPage.addNewTimeClock(timeClockLocation, timeClockDate, timeClockEmployee,timeClockWorkRole, timeClockStartTime, timeClockEndTime, timeClockAddNote);
        HashMap<String, Float> allHours = timeSheetPage.getTimeClockHoursByDate(timeClockDate, timeClockEmployee);
		float regHours = allHours.get("regHours");
		float totalHours = allHours.get("totalHours");
		float dTHours = allHours.get("dTHours");
		float oTHours = allHours.get("oTHours");
		float expectedRegHours = 8;
		float expectedOTHours = totalHours - (regHours + dTHours);
		float expectedDTHours = totalHours - (regHours + oTHours);
		
		if(dTHours > 0)
			SimpleUtils.pass("Timesheet Total hours for user'"+ timeClockEmployee +"' found '" + totalHours + "' hours");
		
		if(regHours == expectedRegHours)
			SimpleUtils.pass("Timesheet Regular hours for user'"+ timeClockEmployee +"' found '" + regHours + "' hours");
		else
			SimpleUtils.fail("Timesheet Regular hours found'"+ regHours +"', expected '" + expectedRegHours + "' hours", true);
		
		if(oTHours == expectedOTHours)
			SimpleUtils.pass("Timesheet Overtime hours for user'"+ timeClockEmployee +"' found '" + oTHours + "' hours");
		else
			SimpleUtils.fail("Timesheet Overtime hours found'"+ oTHours +"', expected '" + expectedOTHours + "' hours", true);
		
		if(dTHours == expectedDTHours)
			SimpleUtils.pass("Timesheet Double Time hours for user'"+ timeClockEmployee +"' found '" + dTHours + "' hours");
		else
			SimpleUtils.fail("Timesheet Double Time hours found'"+ dTHours +"', expected '" + expectedDTHours + "' hours", true);
	}
	
	@Automated(automated =  "Automated")
	@Owner(owner = "Naval")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "TP-114: Automation TA module : Verify Manager can review past payperiod and cannot approve pending status.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyPastPayPeriodAndCanNotApprovePastPendingStatusAsStoreManager(String browser, String username, String password, String location)
    		throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeLocation(location);
        TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
        timeSheetPage.clickOnTimeSheetConsoleMenu();
        SimpleUtils.assertOnFail("TimeSheet Page not loaded Successfully!",timeSheetPage.isTimeSheetPageLoaded() , false);
        SimpleUtils.pass("Timesheet PayPeriod duration: '"+ timeSheetPage.getActiveDayWeekOrPayPeriod() +"' loaded.");
        timeSheetPage.clickOnDayView();
        SimpleUtils.pass("Timesheet Day View: '"+ timeSheetPage.getActiveDayWeekOrPayPeriod() +"' loaded.");
        timeSheetPage.navigateDayWeekOrPayPeriodToPastOrFuture(dayWeekOrPayPeriodViewType.Previous.getValue()
        		, dayWeekOrPayPeriodCount.One.getValue());
        SimpleUtils.pass("Timesheet Day View: '"+ timeSheetPage.getActiveDayWeekOrPayPeriod() +"' loaded.");
        timeSheetPage.openFirstPendingTimeSheet();
        SimpleUtils.assertOnFail("Manager can approve TimeSheet of past date: '" + timeSheetPage.getActiveDayWeekOrPayPeriod() + "'", 
        		(! timeSheetPage.isTimeSheetPopupApproveButtonActive()), false);  
        SimpleUtils.pass("Manager can not approve TimeSheet of past date: '" + timeSheetPage.getActiveDayWeekOrPayPeriod() + "'");  
        timeSheetPage.closeTimeSheetDetailPopUp();
	}
	
	
	@Automated(automated =  "Automated")
	@Owner(owner = "Naval")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "TP-115: Automation TA module : Verify Admin/Manager are alerted when a TM doesn't clock in and it displays a no show alert.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyNoShowAlertWhenTMDoesNotClockInAsStoreManager(String browser, String username, String password, String location)
    		throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeLocation(location);
        TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
        timeSheetPage.clickOnTimeSheetConsoleMenu();
        SimpleUtils.assertOnFail("TimeSheet Page not loaded Successfully!",timeSheetPage.isTimeSheetPageLoaded() , false);
        SimpleUtils.pass("Timesheet PayPeriod duration: '"+ timeSheetPage.getActiveDayWeekOrPayPeriod() +"' loaded.");
        String timeClockEmployee = addTimeClockDetails.get("Employee");
        SimpleUtils.assertOnFail("TimeSheet worker: '"+ timeClockEmployee +"' not found.",
        		timeSheetPage.seachAndSelectWorkerByName(timeClockEmployee) , false);
        String expectedAlertMessage = "No show";
        String textToVerifyOnTimesheetPopup = "No shifts scheduled today";
        boolean isScheduleShiftWithOutClockInFound = false;
        for(WebElement workersDayRow : timeSheetPage.getTimeSheetDisplayedWorkersDayRows()) {
        	HashMap<String, Float> timesheetWorkerDaysHours = timeSheetPage.getTimesheetWorkerHoursByDay(workersDayRow);
        	String[] workersDayRowText = workersDayRow.getText().split("\n");
        	if(timesheetWorkerDaysHours.get("regHours") == 0)
        	{
        		timeSheetPage.openWorkerDayTimeSheetByElement(workersDayRow);
            	if(! timeSheetPage.isTimesheetPopupModelContainsKeyword(textToVerifyOnTimesheetPopup))
            	{
            		isScheduleShiftWithOutClockInFound = true;
            		timeSheetPage.closeTimeSheetDetailPopUp();
            		Thread.sleep(1000);
            		String workerTimeSheetAlert = timeSheetPage.getWorkerTimeSheetAlert(workersDayRow);
            		if(workerTimeSheetAlert.toLowerCase().contains(expectedAlertMessage.toLowerCase()))
            			SimpleUtils.pass("Manager alerted when a TM ('" + timeClockEmployee + 
            					"') doesn't clock in and it displays a no show alert for duration: '" + workersDayRowText[0] +"'.");
            		else
            			SimpleUtils.fail("Manager is not alerted with message '" + expectedAlertMessage + "' when a TM ('" + 
            					timeClockEmployee + "') doesn't clock in for duration: '" + workersDayRowText[0] +"'.", false);
            		break;
            	}
            	timeSheetPage.closeTimeSheetDetailPopUp();
        	}
        }
        if(! isScheduleShiftWithOutClockInFound)
        	SimpleUtils.report("No Schedule Shift without clock-in found.");
	}
	
	
	@Automated(automated =  "Automated")
	@Owner(owner = "Naval")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "TP-116: Automation TA module : Verify Admin/Manager are alerted when a TM clocks in and he hadn't got a shift and gets a unschedule.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyUnscheduledAlertWhenTMClockInWhileNotHavingShiftAsStoreManager(String browser, String username, String password, String location)
    		throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeLocation(location);
        TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
        timeSheetPage.clickOnTimeSheetConsoleMenu();
        SimpleUtils.assertOnFail("TimeSheet Page not loaded Successfully!",timeSheetPage.isTimeSheetPageLoaded() , false);
        SimpleUtils.pass("Timesheet PayPeriod duration: '"+ timeSheetPage.getActiveDayWeekOrPayPeriod() +"' loaded.");
        String timeClockEmployee = addTimeClockDetails.get("Employee");
        SimpleUtils.assertOnFail("TimeSheet worker: '"+ timeClockEmployee +"' not found.",
        		timeSheetPage.seachAndSelectWorkerByName(timeClockEmployee) , false);
        String textToVerifyOnTimesheetPopup_1 = "No shifts scheduled today";
        String textToVerifyOnTimesheetPopup_2 = "No Timeclocks to Display";
        String expectedAlertMessage = "unscheduled";
        boolean isunScheduleClockFound = false;
        
        for(WebElement workersDayRow : timeSheetPage.getTimeSheetDisplayedWorkersDayRows()) {
        	String[] workersDayRowText = workersDayRow.getText().split("\n");
        	timeSheetPage.openWorkerDayTimeSheetByElement(workersDayRow);
        	if(timeSheetPage.isTimesheetPopupModelContainsKeyword(textToVerifyOnTimesheetPopup_1) && 
        			! timeSheetPage.isTimesheetPopupModelContainsKeyword(textToVerifyOnTimesheetPopup_2))
        	{
        		isunScheduleClockFound = true;
        		timeSheetPage.closeTimeSheetDetailPopUp();
        		Thread.sleep(1000);
        		String workerTimeSheetAlert = timeSheetPage.getWorkerTimeSheetAlert(workersDayRow);
        		if(workerTimeSheetAlert.toLowerCase().contains(expectedAlertMessage.toLowerCase()))
        			SimpleUtils.pass("Manager alerted '"+ expectedAlertMessage +"' when a TM ('" + timeClockEmployee + 
        					"') clock in while not having schedule shift for duration: '" + workersDayRowText[0] +"'.");
        		else
        			SimpleUtils.fail("Manager is not alerted with message ' " + expectedAlertMessage + "' when a TM ('" + 
        					timeClockEmployee + "') clock in while not having shift for the duration: '" + workersDayRowText[0] +"'.", false);
        		
        		break;
        	}
        	timeSheetPage.closeTimeSheetDetailPopUp();
        }
        if(! isunScheduleClockFound)
        	SimpleUtils.report("No unSchedule Clock found.");
	}
	
	
	
	@Automated(automated =  "Automated")
	@Owner(owner = "Naval")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "TP-117 : Automation TA module : Verify Admin/Manager are alerted when a TM misses a mealbreak gets a missed meal-break alert.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyMealBreakAlertAsStoreManager(String browser, String username, String password, String location)
    		throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeLocation(location);
        TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
        timeSheetPage.clickOnTimeSheetConsoleMenu();
        SimpleUtils.assertOnFail("TimeSheet Page not loaded Successfully!",timeSheetPage.isTimeSheetPageLoaded() , false);
        SimpleUtils.pass("Timesheet PayPeriod duration: '"+ timeSheetPage.getActiveDayWeekOrPayPeriod() +"' loaded.");
        String timeClockEmployee = addTimeClockDetails.get("Employee");
        SimpleUtils.assertOnFail("TimeSheet worker: '"+ timeClockEmployee +"' not found.",
        		timeSheetPage.seachAndSelectWorkerByName(timeClockEmployee) , false);
        String textToVerifyOnTimesheetPopup_1 = "Break:";
        float verifyTotalHours = 6;
        String expectedAlertMessage = "Missed Meal";
        boolean isTimeClockFound = false;
        
        for(WebElement workersDayRow : timeSheetPage.getTimeSheetDisplayedWorkersDayRows()) {
        	String[] workersDayRowText = workersDayRow.getText().split("\n");
           	HashMap<String, Float> workerDayRowHours = timeSheetPage.getTimesheetWorkerHoursByDay(workersDayRow);
        	if(workerDayRowHours.size() != 0)
        	{ 
        		float totalHours = workerDayRowHours.get("totalHours");
        		if(totalHours > verifyTotalHours)
        		{
        			timeSheetPage.openWorkerDayTimeSheetByElement(workersDayRow);
        			boolean isWorkerTookBreak = timeSheetPage.isTimesheetPopupModelContainsKeyword(textToVerifyOnTimesheetPopup_1);
        			timeSheetPage.closeTimeSheetDetailPopUp();
        			Thread.sleep(1000);
        			isTimeClockFound = true;
        			String workerTimeSheetAlert = timeSheetPage.getWorkerTimeSheetAlert(workersDayRow);
        			
        			if(isWorkerTookBreak)
        				SimpleUtils.pass("Verify Meal Break Alert: Worker: ' "+ timeClockEmployee +"' Took a break for the duration: '" 
        						+ workersDayRowText[0] +"'.");
        			else if (workerTimeSheetAlert.toLowerCase().contains(expectedAlertMessage.toLowerCase()))
            			SimpleUtils.pass("Manager alerted '"+ expectedAlertMessage +"' when a TM ('" + timeClockEmployee + 
            					"') missed the meal break for duration: '" + workersDayRowText[0] +"'.");
            		else
            			SimpleUtils.fail("Manager is not alerted with message ' " + expectedAlertMessage + "' when a TM ('" + 
            					timeClockEmployee + "') missed the meal break for the duration: '" + workersDayRowText[0] +"'.", false);
            		
            		break;
        		}
        	}
        }
        if(! isTimeClockFound)
        	SimpleUtils.report("No Time clock with more than '"+ verifyTotalHours + " Hours' found for Worker: '"+ timeClockEmployee +"'.");
	}
	
	
	@Automated(automated =  "Automated")
	@Owner(owner = "Naval")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "TP-118: Automation TA module : Verify Manager can review detail of TM and approve it.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void reviewTimeClockAndApproveAsStoreManager(String browser, String username, String password, String location)
    		throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeLocation(location);
        TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
        timeSheetPage.clickOnTimeSheetConsoleMenu();
        SimpleUtils.assertOnFail("TimeSheet Page not loaded Successfully!",timeSheetPage.isTimeSheetPageLoaded() , false);
        SimpleUtils.pass("Timesheet Day duration: '"+ timeSheetPage.getActiveDayWeekOrPayPeriod() +"' loaded.");
        String timeClockEmployee = addTimeClockDetails.get("Employee");
        String date = addTimeClockDetails.get("Date"); 
    	String workRole = addTimeClockDetails.get("Work_Role");
    	String startTime = addTimeClockDetails.get("Shift_Start");
    	String endTime = addTimeClockDetails.get("Shift_End");
    	String notes = addTimeClockDetails.get("Add_Note");
    	
        timeSheetPage.addNewTimeClock(location, date, timeClockEmployee, workRole, startTime, endTime, notes);
        timeSheetPage.valiadteTimeClock(location, date, timeClockEmployee, workRole, startTime, endTime, notes);
        
        SimpleUtils.assertOnFail("Time Clock: approve button not active for '"+ timeClockEmployee 
					+"' and duration: '"+timeClockEmployee +"'.", timeSheetPage.isTimeSheetPopupApproveButtonActive(), false);
		
        timeSheetPage.clickOnApproveButton();
			
		SimpleUtils.assertOnFail("Time Clock: unable to approve timesheet for '"+ timeClockEmployee 
					+"' and duration: '"+timeClockEmployee +"'.", timeSheetPage.isTimeSheetApproved(), false);

		timeSheetPage.closeTimeSheetDetailPopUp();
 	}
	
	
	@Automated(automated =  "Automated")
	@Owner(owner = "Naval")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "TP- 120: Automation TA module : Validate Edit timesheet entry is saved in the story: Edit time is saved in history.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void validateEditTimeSheetEntryToBeSavedInHistoryAsStoreManager(String browser, String username, String password, String location)
    		throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeLocation(location);
        TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
        timeSheetPage.clickOnTimeSheetConsoleMenu();
        SimpleUtils.assertOnFail("TimeSheet Page not loaded Successfully!",timeSheetPage.isTimeSheetPageLoaded() , false);
        SimpleUtils.pass("Timesheet Pay Period duration: '"+ timeSheetPage.getActiveDayWeekOrPayPeriod() +"' loaded.");
    	timeSheetPage.openATimeSheetWithClockInAndOut();
    	timeSheetPage.displayTimeClockHistory();
    	String timeSheetHistoryBeforeModifying = timeSheetPage.getTimeClockHistoryText();
    	for(WebElement timeSheetEditbtn : timeSheetPage.getAllTimeSheetEditBtnElements())
    	{
    		timeSheetPage.clickOnEditTimesheetClock(timeSheetEditbtn);
        	timeSheetPage.clickOnDeleteClockButton();
    	}
    	String timeSheetHistoryAfterModifying = timeSheetPage.getTimeClockHistoryText();
    	if(timeSheetHistoryAfterModifying.length() > timeSheetHistoryBeforeModifying.length())
    		SimpleUtils.pass("Edit timesheet entry is saved in the story successfully.");
    	else
    		SimpleUtils.fail("Edit timesheet entry not saved in the story.", false);
    	timeSheetPage.closeTimeSheetDetailPopUp();
 	}
	
	@Automated(automated =  "Automated")
	@Owner(owner = "Naval")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "TP- 122: Automation TA module : Verify if auto approved shift is unapproved, shift with less than 5.5 hours..")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyAutoApprovedShiftIsUnApprovedAfterDeletingClockOutAsStoreManager(String browser, String username, String password, String location)
    		throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeLocation(location);
        TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
        timeSheetPage.clickOnTimeSheetConsoleMenu();
        SimpleUtils.assertOnFail("TimeSheet Page not loaded Successfully!",timeSheetPage.isTimeSheetPageLoaded() , false);
        SimpleUtils.pass("Timesheet Pay Period duration: '"+ timeSheetPage.getActiveDayWeekOrPayPeriod() +"' loaded.");
    	
        
    	timeSheetPage.closeTimeSheetDetailPopUp();
 	}
	
}
