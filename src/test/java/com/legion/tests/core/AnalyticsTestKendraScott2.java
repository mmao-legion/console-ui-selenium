package com.legion.tests.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.legion.pages.AnalyticsPage;
import com.legion.pages.DashboardPage;
import com.legion.pages.ScheduleOverviewPage;
import com.legion.pages.SchedulePage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
//import com.legion.tests.core.ScheduleNewUITest.SchedulePageSubTabText;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;

public class AnalyticsTestKendraScott2 extends TestBase{
    	
	public enum SchedulePageSubTabText{
		  Overview("OVERVIEW"),
		  ProjectedSales("PROJECTED SALES"),
		  StaffingGuidance("STAFFING GUIDANCE"),
		  Schedule("SCHEDULE"),
		  ProjectedTraffic("PROJECTED TRAFFIC");
			private final String value;
			SchedulePageSubTabText(final String newValue) {
	            value = newValue;
	        }
	        public String getValue() { return value; }
		}
	
	 @Override
	 @BeforeMethod()
	 public void firstTest(Method testMethod, Object[] params) throws Exception{
	   this.createDriver((String)params[0],"69","Window");
       visitPage(testMethod);
       loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
     }
	 
	@Automated(automated ="Automated")
	@Owner(owner = "Naval")
	@Enterprise(name = "Kendrascott2_Enterprise")
	@TestName(description = "Verify Analytics flow")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void gotoAnalyticsPageTest(String username, String password, String browser, String location) throws Exception {
	   AnalyticsPage analyticsPage = pageFactory.createConsoleAnalyticsPage();
	   analyticsPage.gotoAnalyticsPage();			
   }
	
	
	@Automated(automated ="Automated")
	@Owner(owner = "Naval")
	@Enterprise(name = "Kendrascott2_Enterprise")
	@TestName(description = "TP-133 : Analytics :- Value of Forecast and Schedule graph needs to be verified.")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void validateForecastAndScheduleHoursWithScheduleOverviewPageAsStoreManager(String username, String password, String browser, String location) throws Exception {
		ArrayList<LinkedHashMap<String, Float>> scheduleOverviewAllWeekHours = new ArrayList<LinkedHashMap<String, Float>>();
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        
        SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , false);
        
        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , false);
        
        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
        List<WebElement> scheduleOverViewWeeks = scheduleOverviewPage.getOverviewScheduleWeeks();
        for(WebElement overViewWeek : scheduleOverViewWeeks)
        {
        	scheduleOverviewAllWeekHours.add(scheduleOverviewPage.getWeekHoursByWeekElement(overViewWeek));
        }
        String forecastedHoursText = "forecastedHours";
		String publishedScheduleHoursText = "publishedScheduleHours";
		
		String overviewGuidanceHoursText = "guidanceHours";
		String overviewScheduleHoursText = "scheduledHours";
		
		AnalyticsPage analyticsPage = pageFactory.createConsoleAnalyticsPage();
		analyticsPage.gotoAnalyticsPage();	
		boolean isFirst = true;
		for(LinkedHashMap<String, Float> weekHours : scheduleOverviewAllWeekHours)
		{
			if(! isFirst)
			{
				analyticsPage.navigateToNextWeek();
			}
				
			
			HashMap<String, Float> analyticaForecastedHours = analyticsPage.getForecastedHours();
			HashMap<String, Float> analyticaScheduleHours = analyticsPage.getScheduleHours();
			if(analyticaForecastedHours.get(forecastedHoursText).equals(weekHours.get(overviewGuidanceHoursText)))
				SimpleUtils.pass("Analytics Forecated Hours and Schedule Guidance Hours found same ("+ 
						analyticaForecastedHours.get(forecastedHoursText) +"/" + weekHours.get(overviewGuidanceHoursText) +") for Duration: '"+
							analyticsPage.getAnalyticsActiveDuration()+"'.");
			else
				SimpleUtils.fail("Analytics Forecated Hours and Schedule Guidance Hours not same ("+ 
						analyticaForecastedHours.get(forecastedHoursText) +"/" + weekHours.get(overviewGuidanceHoursText) +") for Duration: '"+
							analyticsPage.getAnalyticsActiveDuration()+"'.", true);
			
			
			if(analyticaScheduleHours.get(publishedScheduleHoursText).equals(weekHours.get(overviewScheduleHoursText)))
				SimpleUtils.pass("Analytics Scheduled Hours and Overview Schedule Hours found same ("+ 
						analyticaScheduleHours.get(publishedScheduleHoursText) +"/" + weekHours.get(overviewScheduleHoursText) +") for Duration: '"+
							analyticsPage.getAnalyticsActiveDuration()+"'.");
			else
				SimpleUtils.fail("Analytics Scheduled Hours and Overview Schedule Hours not same ("+ 
						analyticaScheduleHours.get(publishedScheduleHoursText) +"/" + weekHours.get(overviewScheduleHoursText) +") for Duration: '"+
							analyticsPage.getAnalyticsActiveDuration()+"'.", true);
			
			isFirst = false;
		}
   }
	
}
