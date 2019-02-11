package com.legion.tests.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.legion.pages.DashboardPage;
import com.legion.pages.ScheduleOverviewPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.BasePage;
import com.legion.pages.ControlsNewUIPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.core.ScheduleNewUITest.SchedulePageSubTabText;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;


public class ControlsNewUITest extends TestBase{
	
	
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
	
	private static HashMap<String, String> controlsLocationDetail = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ControlsPageLocationDetail.json");
	
	//
	
	
  @Automated(automated =  "Automated")
  @Owner(owner = "Naval")
  @Enterprise(name = "KendraScott2_Enterprise")
  @TestName(description = "TP-139: Controls :- User should be able to save data for Company Profile.")
  @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
  public void updateUserLocationAsInternalAdmin(String browser, String username, String password, String location)
  		throws Exception {
			
      DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
      SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);      
      ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
      controlsNewUIPage.clickOnControlsConsoleMenu();
      SimpleUtils.assertOnFail("TimeSheet Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
      controlsNewUIPage.clickOnGlobalLocationButton();
      controlsNewUIPage.clickOnControlsCompanyProfileCard();
      
      String companyName = controlsLocationDetail.get("Company_Name");
	  String businessAddress = controlsLocationDetail.get("Business_Address");
	  String city = controlsLocationDetail.get("City");
	  String state = controlsLocationDetail.get("State");
	  String country = controlsLocationDetail.get("Country");
	  String zipCode = controlsLocationDetail.get("Zip_Code");
	  String timeZone = controlsLocationDetail.get("Time_Zone");
	  String website = controlsLocationDetail.get("Website");
	  String firstName = controlsLocationDetail.get("First_Name");
	  String lastName = controlsLocationDetail.get("Last_Name");
	  String email = controlsLocationDetail.get("E_mail");
	  String phone =controlsLocationDetail.get("Phone");
	  
	  controlsNewUIPage.updateUserLocationProfile(companyName, businessAddress, city, state, country, zipCode, timeZone, website,
			  firstName, lastName, email, phone);
	  boolean isUserLocationProfileUpdated = controlsNewUIPage.isUserLocationProfileUpdated(companyName, businessAddress, city, state, country, zipCode,
    		  timeZone, website, firstName, lastName, email, phone);
	  if(isUserLocationProfileUpdated)
		  SimpleUtils.pass("User Location Profile Updated successfully.");
  }
  
  
  @Automated(automated =  "Automated")
  @Owner(owner = "Naval")
  @Enterprise(name = "KendraScott2_Enterprise")
  @TestName(description = "TP-140 : Controls - User should be able to edit Controls > Working Hours successfully.")
  @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
  public void updateWorkingHoursAsInternalAdmin(String browser, String username, String password, String location)
  		throws Exception {
			
	  ArrayList<HashMap< String,String>> regularWorkingHours = JsonUtil.getArrayOfMapFromJsonFile("src/test/resources/ControlsRegularWorkingHours.json");
      DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
      SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);      
      ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
      controlsNewUIPage.clickOnControlsConsoleMenu();
      SimpleUtils.assertOnFail("TimeSheet Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
      controlsNewUIPage.clickOnGlobalLocationButton();
      controlsNewUIPage.clickOnControlsWorkingHoursCard();
      for(HashMap<String, String> eachRegularHours : regularWorkingHours)
      {
    	  String isStoreClosed = eachRegularHours.get("isStoreClosed");
	      String openingHours = eachRegularHours.get("Opening_Hours");
	      String closingHours = eachRegularHours.get("Closing_Hours");
	      String day = eachRegularHours.get("Day");
    	  controlsNewUIPage.updateControlsRegularHours(isStoreClosed, openingHours, closingHours, day);
      }
      controlsNewUIPage.clickOnSaveRegularHoursBtn();

  }
  
  
  @Automated(automated =  "Automated")
  @Owner(owner = "Naval")
  @Enterprise(name = "KendraScott2_Enterprise")
  @TestName(description = "TP-141 : Controls - Scheduling Policies > Budget: Enable and disable the budget and check its impact on schedule.")
  @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
  public void verifyBudgetSmartcardEnableOrDisableFromSchedulingPoliciesAsInternalAdmin(String browser, String username, String password, String location)
  		throws Exception {
			
      DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
      SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);      
      ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
      navigateToControlsSchedulingPolicies(controlsNewUIPage);
      
      // Enable Budget Smartcard
      boolean enableBudgetSmartcard = true;
      controlsNewUIPage.enableDisableBudgetSmartcard(enableBudgetSmartcard);
      
      SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
  	  schedulePage.clickOnScheduleConsoleMenuItem();
  	  schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.getValue());
  	  SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);
  	  
  	  String budgetSmartcardText = "WEEKLY BUDGET";
  	  boolean isBudgetSmartcardAppeared = schedulePage.isSmartCardAvailableByLabel(budgetSmartcardText);
  	  SimpleUtils.assertOnFail("Budget Smartcard not loaded on 'Schedule' tab even Scheduling policies Enabled Budget Smartcard.",
  			isBudgetSmartcardAppeared , false);
  	  if(isBudgetSmartcardAppeared)
  		SimpleUtils.pass("Budget Smartcard loaded on 'Schedule' tab when Scheduling policies Enabled Budget Smartcard.");
  	  
  	  
  	  // Disable Budget Smartcard
  	  navigateToControlsSchedulingPolicies(controlsNewUIPage);
  	  enableBudgetSmartcard = false;
      controlsNewUIPage.enableDisableBudgetSmartcard(enableBudgetSmartcard);
  	  schedulePage.clickOnScheduleConsoleMenuItem();
 	  schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.getValue());
 	  SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);
  	  isBudgetSmartcardAppeared = schedulePage.isSmartCardAvailableByLabel(budgetSmartcardText);
	  SimpleUtils.assertOnFail("Budget Smartcard loaded on 'Schedule' tab even Scheduling policies Disabled Budget Smartcard.",
			! isBudgetSmartcardAppeared , false);
	  if(! isBudgetSmartcardAppeared)
		SimpleUtils.pass("Budget Smartcard not loaded on 'Schedule' tab when Scheduling policies Disabled Budget Smartcard.");
  }
  
  public void navigateToControlsSchedulingPolicies(ControlsNewUIPage controlsNewUIPage)
  {
	  try {
		controlsNewUIPage.clickOnControlsConsoleMenu();
		SimpleUtils.assertOnFail("TimeSheet Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
	    controlsNewUIPage.clickOnGlobalLocationButton();
	    controlsNewUIPage.clickOnControlsSchedulingPolicies();
	} catch (Exception e) {
  		SimpleUtils.fail(e.getMessage(), false);
	}
  }
  
  @Automated(automated =  "Automated")
  @Owner(owner = "Naval")
  @Enterprise(name = "KendraScott2_Enterprise")
  @TestName(description = "TP-142 : Onboarding :- Verify Schedule planning window and additional schedule hours BEFORE and AFTER.")
  @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
  public void verifySchedulePlanningWindowAndAdditionalScheduleHoursAsInternalAdmin(String browser, String username, String password, String location)
  		throws Exception {
			
      DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
      SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);      
      ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
      //navigateToControlsSchedulingPolicies(controlsNewUIPage);
      controlsNewUIPage.clickOnControlsConsoleMenu();
	  SimpleUtils.assertOnFail("TimeSheet Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
	  controlsNewUIPage.clickOnControlsSchedulingPolicies();
      
	  // How many weeks in advance can a schedule be created?
      String scheduleWeekCoundToCreate = "6 weeks";  //8
      controlsNewUIPage.updateAdvanceScheduleWeekCountToCreate(scheduleWeekCoundToCreate);
      HashMap<String, Integer> schedulePoliciesBufferHours = controlsNewUIPage.getScheduleBufferHours();
      // Verify Schedule week can be created in advance
      SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
  	  schedulePage.clickOnScheduleConsoleMenuItem();
  	  schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
  	  SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);
  	  ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
  	  int weeksCountToBeCreated = scheduleOverviewPage.getScheduleOverviewWeeksCountCanBeCreatInAdvance();
  	  
  	  if(weeksCountToBeCreated == Integer.valueOf(scheduleWeekCoundToCreate.split(" ")[0]))
  		  SimpleUtils.pass("Schedule can be created upto '"+scheduleWeekCoundToCreate+"' in advance as defined in Controls Scheduling Policies.");
  	  else
  		SimpleUtils.fail("Schedule can not be created upto '"+scheduleWeekCoundToCreate+"' in advance as defined in Controls Scheduling Policies.", true);
      
  	  for(WebElement week : scheduleOverviewPage.getOverviewScheduleWeeks())
  	  {
  		  if(! week.getText().toLowerCase().contains("guidance"))
  		  {
  			BasePage basePage = new BasePage();
  			basePage.click(week);
  			schedulePage.clickOnDayView();
  			HashMap<String, Integer> schedulePageBufferHours = schedulePage.getScheduleBufferHours();
  			
  			// verifying opening buffer Hours
  			if(schedulePoliciesBufferHours.get("openingBufferHours") == schedulePageBufferHours.get("openingBufferHours"))
  				SimpleUtils.pass("Schedule page Opening Buffer Hours matched with Scheduling Policies Opening Buffer Hours ('"
    	  				  + schedulePoliciesBufferHours.get("openingBufferHours") +"/"+ schedulePageBufferHours.get("openingBufferHours")+"').");
  	  	    else
  	  	    	SimpleUtils.fail("Schedule page Opening Buffer Hours not matched with Scheduling Policies Opening Buffer Hours ('"
	  				  + schedulePoliciesBufferHours.get("openingBufferHours") +"/"+ schedulePageBufferHours.get("openingBufferHours")+"').", true);
  			// verifying closing buffer Hours
  			if(schedulePoliciesBufferHours.get("closingBufferHours") == schedulePageBufferHours.get("closingBufferHours"))
  	  		  SimpleUtils.pass("Schedule page Closing Buffer Hours matched with Scheduling Policies Closing Buffer Hours ('"
  	  				  + schedulePoliciesBufferHours.get("closingBufferHours") +"/"+ schedulePageBufferHours.get("closingBufferHours")+"').");
  	  	    else
  	  	    	SimpleUtils.fail("Schedule page Closing Buffer Hours not matched with Scheduling Policies Closing Buffer Hours ('"
	  				  + schedulePoliciesBufferHours.get("closingBufferHours") +"/"+ schedulePageBufferHours.get("closingBufferHours")+"').", true);
  			break;
  		  }
  	  }
  }
  
  
}