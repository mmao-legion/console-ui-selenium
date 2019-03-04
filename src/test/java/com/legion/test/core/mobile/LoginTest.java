package com.legion.test.core.mobile;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import com.legion.pages.*;
import com.legion.tests.annotations.*;
import com.legion.tests.core.ScheduleNewUITest;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.legion.pages.mobile.LoginPageAndroid;
import com.legion.pages.pagefactories.mobile.MobilePageFactory;
import com.legion.tests.TestBase;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;

import io.appium.java_client.android.AndroidDriver;

import static com.legion.utils.SimpleUtils.verifyTeamCount;

public class LoginTest extends TestBase{
	
	SchedulePage schedulePage = null;
	private static HashMap<String, String> propertyCustomizeMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ScheduleCustomizeNewShift.json");
	private static HashMap<String, String> scheduleWorkRoles = JsonUtil.getPropertiesFromJsonFile("src/test/resources/WorkRoleOptions.json");
	private static HashMap<String, String> propertySearchTeamMember = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SearchTeamMember.json");
	@Override
	@BeforeMethod
	public void firstTest(Method method, Object[] params) throws Exception {
		// TODO Auto-generated method stub
		this.createDriver((String) params[0], "68", "Linux");
	    visitPage(method);
	    loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
	}

	public enum overviewWeeksStatus{
		NotAvailable("Not Available"),
		Draft("Draft"),
		Guidance("Guidance"),
		Published("Published"),
		Finalized("Finalized");

		private final String value;
		overviewWeeksStatus(final String newValue) {
			value = newValue;
		}
		public String getValue() { return value; }
	}

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
	
	@MobilePlatform(platform = "Android")
	@UseAsTestRailSectionId(testRailSectionId = 65)
	@Automated(automated ="Automated")
	@Owner(owner = "Nishant")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate integration of Console UI with Mobile [Check only Open Schedule Offer is sent to the TM from Console UI and validate it is visible in Legion Mobile app for corresponding TM]")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void gotoLoginPageTest(String username, String password, String browser, String location) throws Exception {
	   DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
       SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
       schedulePage = pageFactory.createConsoleScheduleNewUIPage();
       schedulePage.clickOnScheduleConsoleMenuItem();
       ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
       List<String> overviewPageScheduledWeekStatus = scheduleOverviewPage.getScheduleWeeksStatus();
       schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
       SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
       List<WebElement> overviewPageScheduledWeeks = scheduleOverviewPage.getOverviewScheduleWeeks();
       for(int i=0; i <overviewPageScheduledWeeks.size();i++)
		{
			if(overviewPageScheduledWeeks.get(i).getText().toLowerCase().contains(overviewWeeksStatus.Guidance.getValue().toLowerCase()))
			{
				scheduleOverviewPage.clickOnGuidanceBtnOnOverview(i);
				if(schedulePage.isGenerateButtonLoaded())
				{
					SimpleUtils.pass("Guidance week found : '"+ schedulePage.getActiveWeekText() +"'");
					schedulePage.generateOrUpdateAndGenerateSchedule();
					schedulePage.clickOnSchedulePublishButton();
					break;
				}
			}
		}

		schedulePage.clickOnDayView();
		int previousGutterCount = schedulePage.getgutterSize();
		scheduleNavigationTest(previousGutterCount);
		HashMap<String, Float> ScheduledHours = schedulePage.getScheduleLabelHours();
		Float scheduledHoursBeforeEditing = ScheduledHours.get("scheduledHours");
		HashMap<List<String>,List<String>> teamCount = schedulePage.calculateTeamCount();
		SimpleUtils.assertOnFail("User can add new shift for past week", (schedulePage.isAddNewDayViewShiftButtonLoaded()) , true);
		String textStartDay = schedulePage.clickNewDayViewShiftButtonLoaded();
		schedulePage.customizeNewShiftPage();
		schedulePage.compareCustomizeStartDay(textStartDay);
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
		HashMap<String, String> shiftTimeSchedule = schedulePage.calculateHourDifference();
		schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole"));
		schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
		schedulePage.clickOnCreateOrNextBtn();
		schedulePage.customizeNewShiftPage();
		schedulePage.verifySelectTeamMembersOption();
		schedulePage.clickOnOfferOrAssignBtn();
		int updatedGutterCount = schedulePage.getgutterSize();
		List<String> previousTeamCount = schedulePage.calculatePreviousTeamCount(shiftTimeSchedule,teamCount);
		List<String> currentTeamCount = schedulePage.calculateCurrentTeamCount(shiftTimeSchedule);
		verifyTeamCount(previousTeamCount,currentTeamCount);
		schedulePage.clickSaveBtn();
		HashMap<String, Float> editScheduledHours = schedulePage.getScheduleLabelHours();
		Float scheduledHoursAfterEditing = editScheduledHours.get("scheduledHours");
		verifyScheduleLabelHours(shiftTimeSchedule.get("ScheduleHrDifference"), scheduledHoursBeforeEditing, scheduledHoursAfterEditing);
		schedulePage.clickOnSchedulePublishButton();
		//Schedule overview should show 5 week's schedule

//	   launchMobileApp();
//	   LoginPageAndroid loginPageAndroid = mobilePageFactory.createMobileLoginPage();
//	   loginPageAndroid.clickFirstLoginBtn();
//	   loginPageAndroid.verifyLoginTitle("LOGIN");
//	   loginPageAndroid.selectEnterpriseName();
//	   loginPageAndroid.loginToLegionWithCredentialOnMobile("Gordon.M", "Gordon.M");
//	   loginPageAndroid.clickShiftOffers("Gordon.M");
   }



	public void verifyScheduleLabelHours(String shiftTimeSchedule,
										 Float scheduledHoursBeforeEditing, Float scheduledHoursAfterEditing) throws Exception{
		Float scheduledHoursExpectedValueEditing = 0.0f;
		// If meal break is applicable
//	  		if(Float.parseFloat(shiftTimeSchedule) >= 6){
//	  			scheduledHoursExpectedValueEditing = (float) (scheduledHoursBeforeEditing + (Float.parseFloat(shiftTimeSchedule) - 0.5));
//	  		}else{
//	  			scheduledHoursExpectedValueEditing = (float)(scheduledHoursBeforeEditing + Float.parseFloat(shiftTimeSchedule));
//	  		}
		// If meal break is not applicable
		scheduledHoursExpectedValueEditing = (float)(scheduledHoursBeforeEditing + Float.parseFloat(shiftTimeSchedule));
		if(scheduledHoursExpectedValueEditing.equals(scheduledHoursAfterEditing)){
			SimpleUtils.pass("Scheduled Hours Expected value "+scheduledHoursExpectedValueEditing+" matches with Scheduled Hours Actual value "+scheduledHoursAfterEditing);
		}else{
			SimpleUtils.fail("Scheduled Hours Expected value "+scheduledHoursExpectedValueEditing+" does not match with Scheduled Hours Actual value "+scheduledHoursAfterEditing,false);
		}
	}


	public void verifyTeamCount(List<String> previousTeamCount, List<String> currentTeamCount) throws Exception {
		if(previousTeamCount.size() == currentTeamCount.size()){
			for(int i =0; i<currentTeamCount.size();i++){
				String currentCount = currentTeamCount.get(i);
				String previousCount = previousTeamCount.get(i);
				if(Integer.parseInt(currentCount) == Integer.parseInt(previousCount)+1){
					SimpleUtils.pass("Current Team Count is greater than Previous Team Count");
				}else{
					SimpleUtils.fail("Current Team Count is not greater than Previous Team Count",true);
				}
			}
		}else{
			SimpleUtils.fail("Size of Current Team Count should be equal to Previous Team Count",false);
		}
	}

	public void verifyGutterCount(int previousGutterCount, int updatedGutterCount){
		if(updatedGutterCount == previousGutterCount + 1){
			SimpleUtils.pass("Size of gutter is "+updatedGutterCount+" greater than previous value "+previousGutterCount);
		}else{
			SimpleUtils.fail("Size of gutter is "+updatedGutterCount+" greater than previous value "+previousGutterCount, false);
		}
	}


	public void scheduleNavigationTest(int previousGutterCount) throws Exception{
		schedulePage.clickOnEditButton();
		boolean bolDeleteShift = checkAddedShift(previousGutterCount);
		if(bolDeleteShift){
			schedulePage.clickSaveBtn();
			schedulePage.clickOnEditButton();
		}
	}

	public boolean checkAddedShift(int guttercount)throws Exception {
		boolean bolDeleteShift = false;
		if (guttercount > 0) {
			schedulePage.clickOnShiftContainer(guttercount);
			bolDeleteShift = true;
		}
		return bolDeleteShift;
	}





}
