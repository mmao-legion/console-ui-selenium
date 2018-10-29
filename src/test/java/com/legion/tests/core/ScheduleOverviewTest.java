package com.legion.tests.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.legion.pages.LocationSelectorPage;
import com.legion.pages.ScheduleOverviewPage;
import com.legion.pages.SchedulePage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.core.ScheduleTest.SchedulePageSubTabText;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

public class ScheduleOverviewTest extends TestBase{

	private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
    
	@Override
	  @BeforeMethod()
	  public void firstTest(Method testMethod, Object[] params) throws Exception{
		  this.createDriver((String)params[0],"69","Window");
	      visitPage(testMethod);
	      loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
	  }
//    @Automated(automated ="Automated")
//	@Owner(owner = "Naval")
//	@TestName(description = "change the location from Dashboard!")
//    @Test
//    public void changeLocationTest() throws Exception { 
////    	loginToLegionAndVerifyIsLoginDone(propertyMap.get("DEFAULT_USERNAME"), propertyMap.get("DEFAULT_PASSWORD"));
//    	LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
//	    locationSelectorPage.changeLocation(newLocationName);
//	    SimpleUtils.assertOnFail("Dashboard Page: Location not changed!",locationSelectorPage.isLocationSelected(newLocationName), false);
//    }
    
    @Automated(automated ="Automated")
	@Owner(owner = "Naval")
    @Enterprise(name = "Coffee2_Enterprise")
	@TestName(description = "Check the 1st/current week is highlighted and DateAndDay are correct")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyCurrentWeekDateAndDayTest(String username, String password, String browser, String location) throws Exception { 
//    	loginToLegionAndVerifyIsLoginDone(propertyMap.get("DEFAULT_USERNAME"), propertyMap.get("DEFAULT_PASSWORD"));
    	SchedulePage schedulePage = pageFactory.createConsoleSchedulePage();
	    schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
        //Map<String, String> currentWeekCalendarWeekDaysAndStartDay = scheduleOverviewPage.getWeekStartDayAndCurrentWeekDates();
	    SimpleUtils.assertOnFail("Current Week not Highlighted!",scheduleOverviewPage.isCurrentWeekHighLighted(), false);

    }
    
    @Automated(automated ="Automated")
	@Owner(owner = "Naval")
    @Enterprise(name = "Coffee2_Enterprise")
	@TestName(description = "Check each week until weeks are not available DateAndDay are correct on overview page")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyDateAndDayForEachWeeksUntilNotAvailableTest(String username, String password, String browser, String location) throws Exception { 
//    	loginToLegionAndVerifyIsLoginDone(propertyMap.get("DEFAULT_USERNAME"), propertyMap.get("DEFAULT_PASSWORD"));
    	SchedulePage schedulePage = pageFactory.createConsoleSchedulePage();
	    schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
	    SimpleUtils.assertOnFail("DateAndDay verification failed for each week",
	    		scheduleOverviewPage.verifyDateAndDayForEachWeekUntilNotAvailable(), false);
    }
    
    @Automated(automated ="Automated")
	@Owner(owner = "Naval")
    @Enterprise(name = "Coffee2_Enterprise")
	@TestName(description = "Click on each week to open schedule page and ensure the DayAndDate on schedule page matches the DayAndDate on overview page")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyDayAndDateOnSchedulePageMatchesDayAndDateOnOverviewPageTest(String username, String password, String browser, String location) throws Exception { 
    	String scheduleWeekStatusToVerify = "Not Available";
    	Boolean isCurrentWeekSelected = false;
    	int index = 0;
//    	loginToLegionAndVerifyIsLoginDone(propertyMap.get("DEFAULT_USERNAME"), propertyMap.get("DEFAULT_PASSWORD"));
    	SchedulePage schedulePage = pageFactory.createConsoleSchedulePage();
	    schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
        List<String> currentAndUpcomingActiveWeeksDatesOnCalendar = scheduleOverviewPage.getCurrentAndUpcomingActiveWeeksDaysOnCalendar();
		List<String> overviewPageScheduledWeekStatus = scheduleOverviewPage.getScheduleWeeksStatus();
		String overviewCalendarDays = scheduleOverviewPage.getOverviewCalenderWeekDays();
		for(String scheduleWeekStatus : overviewPageScheduledWeekStatus)
		{ 
			if(!scheduleWeekStatus.contains(scheduleWeekStatusToVerify)) {
				if(isCurrentWeekSelected) {
					schedulePage.navigateWeekViewToPastOrFuture("next", 1);
				}
				else {
					scheduleOverviewPage.clickOnCurrentWeekToOpenSchedule();
					isCurrentWeekSelected = true;
				}
				
		        String activeWeekTimeDurationForEachday = schedulePage.getActiveWeekDayMonthAndDateForEachDay();
				Boolean isScheduleActiveWeekMatchedWithOverviewCalendarWeek = schedulePage.validateScheduleActiveWeekWithOverviewCalendarWeek(currentAndUpcomingActiveWeeksDatesOnCalendar.get(index), 
						overviewCalendarDays, activeWeekTimeDurationForEachday);
		        SimpleUtils.assertOnFail("Verification for DayAndDate On SchedulePage does not Matche with DayAndDate On OverviewPage!",isScheduleActiveWeekMatchedWithOverviewCalendarWeek, false);
			}
			index = index + 1;
		}
		
		
		
		
    }
	
   
}
