package com.legion.tests.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.legion.pages.*;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.core.ScheduleNewUITest.SchedulePageSubTabText;
import com.legion.tests.core.ScheduleNewUITest.overviewWeeksStatus;
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
	
	public enum schedulingPoliciesShiftIntervalTime{
		  FifteenMinutes("15 minutes"),
		  ThirtyMinutes("30 minutes");
			private final String value;
			schedulingPoliciesShiftIntervalTime(final String newValue) {
	            value = newValue;
	        }
	        public String getValue() { return value; }
	}
	
	public enum usersAndRolesSubTabs{
		AllUsers("Users"),
		AccessByJobTitles("Access Roles"),
		Badges("Badges");
		private final String value;
		usersAndRolesSubTabs(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
	}
	
	public enum tasksAndWorkRolesSubTab{
		WorkRoles("Work Roles"),
		LaborCalculator("Labor Calculator");
		private final String value;
		tasksAndWorkRolesSubTab(final String newValue) {
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
	
	public enum schedulingPolicyGroupsTabs{
		  FullTimeSalariedExempt("Full Time Salaried Exempt"),
		  FullTimeSalariedNonExempt("Full Time Salaried Non Exempt"),
		  FullTimeHourlyNonExempt("Full Time Hourly Non Exempt"),
          PartTimeHourlyNonExempt("Part Time Hourly Non Exempt");
			private final String value;
			schedulingPolicyGroupsTabs(final String newValue) {
	            value = newValue;
	        }
	        public String getValue() { return value; }
	}
	
	@Override
	@BeforeMethod
	public void firstTest(Method method, Object[] params) throws Exception {
		this.createDriver((String) params[0], "68", "Linux");
	      visitPage(method);
	      loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
	}
	
	private static HashMap<String, String> controlsLocationDetail = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ControlsPageLocationDetail.json");
	private static HashMap<String, String> schedulingPoliciesData = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SchedulingPoliciesData.json");
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
      SimpleUtils.assertOnFail("Controls Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
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

	@Automated(automated =  "Automated")
	@Owner(owner = "Nishant")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Controls - Scheduling Policies > Enable Assignment Rule on Scheduling Policies")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void enableAssignmentRuleFromSchedulingPoliciesAsInternalAdmin(String browser, String username, String password, String location)
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
  
  
  
  
  @Automated(automated =  "Automated")
  @Owner(owner = "Naval")
  @Enterprise(name = "KendraScott2_Enterprise")
  @TestName(description = "TP-147: Onboarding - Check navigation to different section in controls tab[On click it should not logout].")
  @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
  public void updateControlsSectionLoadingAsStoreManager(String browser, String username, String password, String location)
  		throws Exception {
			
      DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
      SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);      
      ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
      controlsNewUIPage.clickOnControlsConsoleMenu();
      SimpleUtils.assertOnFail("Controls Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
      
      // Validate Controls Location Profile Section
      controlsNewUIPage.clickOnControlsLocationProfileSection();
      boolean isLocationProfile = controlsNewUIPage.isControlsLocationProfileLoaded();
      SimpleUtils.assertOnFail("Controls Page: Location Profile Section not Loaded.", isLocationProfile, true);
      
      // Validate Controls Scheduling Policies Section
      controlsNewUIPage.clickOnControlsConsoleMenu();
      controlsNewUIPage.clickOnControlsSchedulingPolicies();
	  boolean isSchedulingPolicies = controlsNewUIPage.isControlsSchedulingPoliciesLoaded();
	  SimpleUtils.assertOnFail("Controls Page: Scheduling Policies Section not Loaded.", isSchedulingPolicies, true);
      
      // Validate Controls Schedule Collaboration Section
	  controlsNewUIPage.clickOnControlsConsoleMenu();
      controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
	  boolean isScheduleCollaboration = controlsNewUIPage.isControlsScheduleCollaborationLoaded();
	  SimpleUtils.assertOnFail("Controls Page: Schedule Collaboration Section not Loaded.", isScheduleCollaboration, true);
      
      // Validate Controls Compliance Section
	  controlsNewUIPage.clickOnControlsConsoleMenu();
      controlsNewUIPage.clickOnControlsComplianceSection();
	  boolean isCompliance = controlsNewUIPage.isControlsComplianceLoaded();
	  SimpleUtils.assertOnFail("Controls Page: Compliance Section not Loaded.", isCompliance, true);
      
      // Validate Controls Users and Roles Section
	  controlsNewUIPage.clickOnControlsConsoleMenu();
      controlsNewUIPage.clickOnControlsUsersAndRolesSection();
	  boolean isUsersAndRoles = controlsNewUIPage.isControlsUsersAndRolesLoaded();
	  SimpleUtils.assertOnFail("Controls Page: Users and Roles Section not Loaded.", isUsersAndRoles, true);
      
      // Validate Controls Tasks and Work Roles Section
	  controlsNewUIPage.clickOnControlsConsoleMenu();
      controlsNewUIPage.clickOnControlsTasksAndWorkRolesSection();
	  boolean isTasksAndWorkRoles = controlsNewUIPage.isControlsTasksAndWorkRolesLoaded();
	  SimpleUtils.assertOnFail("Controls Page: Tasks and Work Roles Section not Loaded.", isTasksAndWorkRoles, true);
      
      // Validate Working Hours Profile Section
	  controlsNewUIPage.clickOnControlsConsoleMenu();
      controlsNewUIPage.clickOnControlsWorkingHoursCard();
	  boolean isWorkingHours = controlsNewUIPage.isControlsWorkingHoursLoaded();
	  SimpleUtils.assertOnFail("Controls Page: Working Hours Section not Loaded.", isWorkingHours, true);
  }
  
  
  @Automated(automated =  "Automated")
  @Owner(owner = "Naval")
  @Enterprise(name = "KendraScott2_Enterprise")
  @TestName(description = "TP-154: Controls :- Shift Interval minutes for the enterprise should get updated successfully.")
  @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
  public void updateAndValidateShiftIntervalTimeAsInternalAdmin(String browser, String username, String password, String location)
  		throws Exception {
			
      DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
      SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);      
      ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
      controlsNewUIPage.clickOnControlsConsoleMenu();
      SimpleUtils.assertOnFail("Controls Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
      controlsNewUIPage.clickOnControlsSchedulingPolicies();
      controlsNewUIPage.clickOnSchedulingPoliciesShiftAdvanceBtn();
      controlsNewUIPage.selectSchedulingPoliciesShiftIntervalByLabel(schedulingPoliciesShiftIntervalTime.ThirtyMinutes.getValue());
      Thread.sleep(1000);
      SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
      schedulePage.clickOnScheduleConsoleMenuItem();
  	  schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
  	  SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!"
  			,schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);
  	  ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
  	  BasePage basePase = new BasePage();
  	  Thread.sleep(1000);
      List<WebElement> overviewWeeks = scheduleOverviewPage.getOverviewScheduleWeeks();
      boolean isWeekFoundToGenerate = false;
      int minutesInAnHours = 60;
      for(WebElement overviewWeek : overviewWeeks)
      {
      	if(overviewWeek.getText().contains(overviewWeeksStatus.Guidance.getValue()))
      	{
      		isWeekFoundToGenerate = true;
      		basePase.click(overviewWeek);
      		schedulePage.generateOrUpdateAndGenerateSchedule();
      		boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
      		if(isActiveWeekGenerated)
      			SimpleUtils.pass("Schedule Page: Schedule week for duration:'"+ schedulePage.getActiveWeekText() +"' Generated Successfully.");
      		else
      			SimpleUtils.fail("Schedule Page: Schedule week for duration:'"+ schedulePage.getActiveWeekText() +"' not Generated.", false);
      		schedulePage.clickOnDayView();
      		int shiftIntervalCountInAnHour = schedulePage.getScheduleShiftIntervalCountInAnHour();
      		if((minutesInAnHours /shiftIntervalCountInAnHour) == Integer.valueOf(schedulingPoliciesShiftIntervalTime.ThirtyMinutes.getValue().split(" ")[0]))
      			SimpleUtils.pass("Schedule Page: Schedule week for duration:'"+ schedulePage.getActiveWeekText() 
      				+"' Shift Interval Time matched as '"+ schedulingPoliciesShiftIntervalTime.ThirtyMinutes.getValue() +"'.");
      		else
      			SimpleUtils.fail("Schedule Page: Schedule week for duration:'"+ schedulePage.getActiveWeekText() 
  					+"' Shift Interval Time not matched as '"+ schedulingPoliciesShiftIntervalTime.ThirtyMinutes.getValue() +"'.", false);
      		break;
      	}
      }
      if(! isWeekFoundToGenerate)
      	SimpleUtils.report("No 'Guidance' week found to Ungenerate Schedule.");
  }
  
  @Automated(automated =  "Automated")
  @Owner(owner = "Naval")
  @Enterprise(name = "KendraScott2_Enterprise")
  @TestName(description = "TP-155: Validate the schedule finalize functionality.")
  @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
  public void validateScheduleFinalizeFunctionalityAsInternalAdmin(String browser, String username, String password, String location)
  		throws Exception {
			
      DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
      SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);  
      
      ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
      controlsNewUIPage.clickOnControlsConsoleMenu();
      SimpleUtils.assertOnFail("Controls Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
      
      controlsNewUIPage.clickOnControlsSchedulingPolicies();
      Thread.sleep(2000);
      String schedulePublishWindowWeeks = controlsNewUIPage.getSchedulePublishWindowWeeks();
      int schedulePublishWindowWeeksCount = Integer.valueOf(schedulePublishWindowWeeks.split(" ")[0]);
      SimpleUtils.report("Scheduling Policies : Advance Schedule can be 'Published' upto upcoming '"+schedulePublishWindowWeeks+"'.");
      
      controlsNewUIPage.clickOnSchedulingPoliciesSchedulesAdvanceBtn();
      int advanceFinalizeScheduleDaysCount = controlsNewUIPage.getAdvanceScheduleDaysCountToBeFinalize();
      int advanceWeekToFinalizeCount = (advanceFinalizeScheduleDaysCount / 7);
      
      // How many days in advance would you finalize schedule
      SimpleUtils.report("Scheduling Policies : Advance Schedule can be 'Finalize' upto upcoming '"+advanceFinalizeScheduleDaysCount+"' Days.");
      Thread.sleep(1000);
      
      SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
      schedulePage.clickOnScheduleConsoleMenuItem();
  	  schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
  	  SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!"
  			,schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);
  	  
  	  ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
  	  Thread.sleep(2000);
  	  
      List<WebElement> overviewWeeks = scheduleOverviewPage.getOverviewScheduleWeeks();
      SimpleUtils.assertOnFail("Schedule Overview Page: Unable to fetch Schedule weeks detail.", (overviewWeeks.size() > 0), false);
      //div.left-banner
      String pastDueText = "PAST DUE";
      for(int index = 0; index < dayWeekOrPayPeriodCount.Five.getValue(); index ++)
      {
    	  String overviewWeekText = overviewWeeks.get(index).getText();
    	  if(overviewWeekText.contains(overviewWeeksStatus.Finalized.getValue())) {
    		  if(index <= advanceWeekToFinalizeCount)
    			  SimpleUtils.pass("Overview Page: Week ('"+ scheduleOverviewPage.getOverviewWeekDuration(overviewWeeks.get(index))+"') is finalized.");
    		  else
    			  SimpleUtils.fail("Overview Page: Week ('"+ scheduleOverviewPage.getOverviewWeekDuration(overviewWeeks.get(index)) 
    			  	+"') is finalized while week is out of finalize window ('"+ advanceWeekToFinalizeCount +" Weeks').", true);
    	  }
    	  else if(overviewWeekText.contains(overviewWeeksStatus.Published.getValue())){
    		  SimpleUtils.pass("Overview Page: Week ('"+ scheduleOverviewPage.getOverviewWeekDuration(overviewWeeks.get(index))+"') is Published.");
    		  if(index <= advanceWeekToFinalizeCount)
    			  SimpleUtils.fail("Overview Page: Week ('"+ scheduleOverviewPage.getOverviewWeekDuration(overviewWeeks.get(index)) 
    			  	+"') not 'Finalized' while Schedule can be finalized '"+ advanceFinalizeScheduleDaysCount +"' Days in Advance." , true);
    		  
    		  // Validate 'Past Due' Text
    		  if(index <= schedulePublishWindowWeeksCount)
    		  {
    			  if(overviewWeekText.toLowerCase().contains(pastDueText.toLowerCase()))
    				  SimpleUtils.pass("Overview Page: Non finalize Week ('"+ scheduleOverviewPage.getOverviewWeekDuration(overviewWeeks.get(index)) 
    				  +"') displaying '" + pastDueText + "' Text while schedule is not 'Finalize' and it reaches the Publish window.");  
    			  else
    				  SimpleUtils.fail("Overview Page: Non finalize week ('"+ scheduleOverviewPage.getOverviewWeekDuration(overviewWeeks.get(index))
    				  +"') not displaying '" + pastDueText + "' Text while schedule is not Finalised and it reaches the publish window.", true);
    		  }
    	  }
    	  else {
    		  // Validate 'Past Due' Text
    		  if(index <= schedulePublishWindowWeeksCount)
    		  {
    			  if(overviewWeekText.toLowerCase().contains(pastDueText.toLowerCase()))
    				  SimpleUtils.pass("Overview Page: Non finalize Week ('"+ scheduleOverviewPage.getOverviewWeekDuration(overviewWeeks.get(index))
    				  +"') displaying '" + pastDueText + "' Text while schedule is not 'Finalize' and it reaches the Publish window.");  
    			  else
    				  SimpleUtils.fail("Overview Page: Non finalize week ('"+ scheduleOverviewPage.getOverviewWeekDuration(overviewWeeks.get(index))
    				  +"') not displaying '" + pastDueText + "' Text while schedule is not Finalised and it reaches the publish window.", true);
    		  }
    	  }
    	  
      }
  }
  
  
  @Automated(automated =  "Automated")
  @Owner(owner = "Naval")
  @Enterprise(name = "KendraScott2_Enterprise")
  @TestName(description = "TP-158: Controls:- data for Scheduling Policies should be saved and display the success message.")
  @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
  public void updateAndValidateSchedulingPoliciesAllFieldsAsInternalAdmin(String browser, String username, String password, String location)
  		throws Exception {
			
      DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
      SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);  
      
      ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
      controlsNewUIPage.clickOnControlsConsoleMenu();
      SimpleUtils.assertOnFail("Controls Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
      
      controlsNewUIPage.clickOnControlsSchedulingPolicies();
      Thread.sleep(2000);
      String activeLocation = controlsNewUIPage.getSchedulingPoliciesActiveLocation();
      SimpleUtils.report("Controls Page: Scheduling Policies Active Location '"+ activeLocation +"'.");
  	  HashMap<String, String> schedulingPoliciesData = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SchedulingPoliciesData.json");

  	  String publishWindowAdvanceWeeks = schedulingPoliciesData.get("Schedule_Publish_Window");
  	  String planningWindowAdvanceWeeks = schedulingPoliciesData.get("Schedule_Planning_Window");
  	  String firstDayOfWeek = schedulingPoliciesData.get("Schedule_Week_Begin_Day");
  	  String openingTime = schedulingPoliciesData.get("Earliest_Opening_Time");
  	  String closingTime = schedulingPoliciesData.get("Latest_Closing_Time");
  	  String advanceDaysToFinalize = schedulingPoliciesData.get("Advance_Finalize_Schedule_Days");
  	  String beforeBufferCount = schedulingPoliciesData.get("Additional_Schedule_Hours_Before");
  	  String afterBufferCount = schedulingPoliciesData.get("Additional_Schedule_Hours_After");
  	  String minShiftLengthHour = schedulingPoliciesData.get("Minimum_Shift_Length");
  	  String maxShiftLengthHour = schedulingPoliciesData.get("Maximum_Shift_Length");
  	  String maxWeekDaysToAutoScheduleLabel = schedulingPoliciesData.get("Maximum_Number_Of_Days_To_AutoSchedule");
  	  String shiftBreakIcons = schedulingPoliciesData.get("Display_Shift_Break_Icons");
  	  String intervalTimeLabel = schedulingPoliciesData.get("Shift_Interval_Minutes");
  	  String isLaborBudgetToApply = schedulingPoliciesData.get("Labor_Budget_To_Apply");
  	  String budgetType = schedulingPoliciesData.get("Budget_Type");
  	  String availabilityLockModeLabel = schedulingPoliciesData.get("Availability_Lock_Mode");
  	  String unavailableWorkersHour = schedulingPoliciesData.get("Unavailable_Workers_Hour");
  	  String availabilityToleranceMinutes = schedulingPoliciesData.get("Availability_Tolerance_Minutes");
  	  String canWorkerRequestTimeOffValue = schedulingPoliciesData.get("Can_Employees_Request_Time_Off");
  	  String maxWorkersTimeOfPerDayCount = schedulingPoliciesData.get("Max_Workers_TimeOf_Per_Day_Count");
  	  String noticePeriodToRequestTimeOff = schedulingPoliciesData.get("Notice_Period_To_Request_TimeOff");
  	  String isShowTimeOffReasons = schedulingPoliciesData.get("Is_Show_TimeOff_Reasons");

      //Updating Scheduling Policies 'Schedule' Section
      controlsNewUIPage.updateSchedulePublishWindow(publishWindowAdvanceWeeks,true,true);
      controlsNewUIPage.updateSchedulePlanningWindow(planningWindowAdvanceWeeks, false, false);
      controlsNewUIPage.updateSchedulingPoliciesFirstDayOfWeek(firstDayOfWeek);
      controlsNewUIPage.updateEarliestOpeningTime(openingTime);
      controlsNewUIPage.updateLatestClosingTime(closingTime);
      controlsNewUIPage.clickOnSchedulingPoliciesSchedulesAdvanceBtn();
      controlsNewUIPage.updateAdvanceScheduleDaysToBeFinalize(advanceDaysToFinalize);
      controlsNewUIPage.updateScheduleBufferHoursBefore(beforeBufferCount);
      controlsNewUIPage.updateScheduleBufferHoursAfter(afterBufferCount);
      controlsNewUIPage.updateMinimumShiftLengthHour(minShiftLengthHour);
      controlsNewUIPage.updateMaximumShiftLengthHour(maxShiftLengthHour);
      controlsNewUIPage.updateMaximumNumWeekDaysToAutoSchedule(maxWeekDaysToAutoScheduleLabel);
      controlsNewUIPage.updateDisplayShiftBreakIcons(shiftBreakIcons);
      controlsNewUIPage.clickOnSchedulingPoliciesShiftAdvanceBtn();
      controlsNewUIPage.selectSchedulingPoliciesShiftIntervalByLabel(intervalTimeLabel);
      controlsNewUIPage.updateApplyLaborBudgetToSchedules(isLaborBudgetToApply);
      controlsNewUIPage.updateScheduleBudgetType(budgetType);
      controlsNewUIPage.updateTeamAvailabilityLockMode(availabilityLockModeLabel);
      controlsNewUIPage.updateScheduleUnavailableHourOfWorkers(unavailableWorkersHour);
      controlsNewUIPage.updateAvailabilityToleranceField(availabilityToleranceMinutes);
      controlsNewUIPage.updateCanWorkerRequestTimeOff(canWorkerRequestTimeOffValue);
      controlsNewUIPage.updateMaxEmployeeCanRequestForTimeOffOnSameDay(maxWorkersTimeOfPerDayCount);
      controlsNewUIPage.clickOnSchedulingPoliciesTimeOffAdvanceBtn();
      controlsNewUIPage.updateNoticePeriodToRequestTimeOff(noticePeriodToRequestTimeOff);
      controlsNewUIPage.updateShowTimeOffReasons(isShowTimeOffReasons);
      
      // Selecting Global Location
      controlsNewUIPage.clickOnGlobalLocationButton();
      activeLocation = controlsNewUIPage.getSchedulingPoliciesActiveLocation();
      SimpleUtils.report("Controls Page: Scheduling Policies Active Location '"+ activeLocation +"'.");
      controlsNewUIPage.updateSchedulePublishWindow(publishWindowAdvanceWeeks,true,true);
      controlsNewUIPage.updateSchedulePlanningWindow(planningWindowAdvanceWeeks,true, false);
      controlsNewUIPage.updateSchedulingPoliciesFirstDayOfWeek(firstDayOfWeek);
      controlsNewUIPage.updateEarliestOpeningTime(openingTime);
      controlsNewUIPage.updateLatestClosingTime(closingTime);
      controlsNewUIPage.clickOnSchedulingPoliciesSchedulesAdvanceBtn();
      controlsNewUIPage.updateAdvanceScheduleDaysToBeFinalize(advanceDaysToFinalize);
      controlsNewUIPage.updateScheduleBufferHoursBefore(beforeBufferCount);
      controlsNewUIPage.updateScheduleBufferHoursAfter(afterBufferCount);
      controlsNewUIPage.updateMinimumShiftLengthHour(minShiftLengthHour);
      controlsNewUIPage.updateMaximumShiftLengthHour(maxShiftLengthHour);
      controlsNewUIPage.updateMaximumNumWeekDaysToAutoSchedule(maxWeekDaysToAutoScheduleLabel);
      controlsNewUIPage.updateDisplayShiftBreakIcons(shiftBreakIcons);
      controlsNewUIPage.clickOnSchedulingPoliciesShiftAdvanceBtn();
      controlsNewUIPage.selectSchedulingPoliciesShiftIntervalByLabel(intervalTimeLabel);
      controlsNewUIPage.updateApplyLaborBudgetToSchedules(isLaborBudgetToApply);
      controlsNewUIPage.updateScheduleBudgetType(budgetType);
      controlsNewUIPage.updateTeamAvailabilityLockMode(availabilityLockModeLabel);
      controlsNewUIPage.updateScheduleUnavailableHourOfWorkers(unavailableWorkersHour);
      controlsNewUIPage.updateAvailabilityToleranceField(availabilityToleranceMinutes);
      controlsNewUIPage.updateCanWorkerRequestTimeOff(canWorkerRequestTimeOffValue);
      controlsNewUIPage.updateMaxEmployeeCanRequestForTimeOffOnSameDay(maxWorkersTimeOfPerDayCount);
      controlsNewUIPage.clickOnSchedulingPoliciesTimeOffAdvanceBtn();
      controlsNewUIPage.updateNoticePeriodToRequestTimeOff(noticePeriodToRequestTimeOff);
      controlsNewUIPage.updateShowTimeOffReasons(isShowTimeOffReasons);
  	  controlsNewUIPage.clickOnGlobalLocationButton();

  	  controlsNewUIPage.selectSchdulingPolicyGroupsTabByLabel(schedulingPolicyGroupsTabs.FullTimeSalariedExempt.getValue());
  	int fullTimeSalariedExemptHoursPerWeekMin  = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Exempt_Hours_Per_Week_Min" ));
  	int fullTimeSalariedExemptHoursPerWeekMax = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Exempt_Hours_Per_Week_Max"));
  	int fullTimeSalariedExemptHoursPerWeekIdeal = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Exempt_Hours_Per_Week_Ideal"));
  	int fullTimeSalariedExemptShiftsPerWeekMin = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Exempt_Shifts_Per_Week_Min"));
  	int fullTimeSalariedExemptShiftsPerWeekMax = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Exempt_Shifts_Per_Week_Max"));
  	int fullTimeSalariedExemptShiftsPerWeekIdeal = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Exempt_Shifts_Per_Week_Ideal"));
  	int fullTimeSalariedExemptHoursPerShiftMin = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Exempt_Hours_Per_Shift_Min"));
  	int fullTimeSalariedExemptHoursPerShiftMax = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Exempt_Hours_Per_Shift_Max"));
  	int fullTimeSalariedExemptHoursPerShiftIdeal = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Exempt_Hours_Per_Shift_Ideal"));
  	boolean fullTimeSalariedExemptIsEmployeeCommittedAvailability = Boolean.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Exempt_Is_Employee_Committed_Availability"));
  	int fullTimeSalariedExemptCommittedHoursWeeks = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Exempt_Committed_Hours_Weeks"));
  	
  	  controlsNewUIPage.updateSchedulingPolicyGroupsHoursPerWeek(fullTimeSalariedExemptHoursPerWeekMin, 
  			  fullTimeSalariedExemptHoursPerWeekMax, fullTimeSalariedExemptHoursPerWeekIdeal);
  	  controlsNewUIPage.updateSchedulingPolicyGroupsShiftsPerWeek(fullTimeSalariedExemptShiftsPerWeekMin,
  			  fullTimeSalariedExemptShiftsPerWeekMax,fullTimeSalariedExemptShiftsPerWeekIdeal);
  	  controlsNewUIPage.updateSchedulingPolicyGroupsHoursPerShift(fullTimeSalariedExemptHoursPerShiftMin,
  			  fullTimeSalariedExemptHoursPerShiftMax,fullTimeSalariedExemptHoursPerShiftIdeal);
  	  controlsNewUIPage.updateEnforceNewEmployeeCommittedAvailabilityWeeks(fullTimeSalariedExemptIsEmployeeCommittedAvailability,
  			  fullTimeSalariedExemptCommittedHoursWeeks);
  	  
  	  controlsNewUIPage.selectSchdulingPolicyGroupsTabByLabel(schedulingPolicyGroupsTabs.FullTimeSalariedNonExempt.getValue());
  	  
  	  int fullTimeSalariedNonExemptHoursPerWeekMin  = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Non_Exempt_Hours_Per_Week_Min" ));
  	  int fullTimeSalariedNonExemptHoursPerWeekMax = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Non_Exempt_Hours_Per_Week_Max"));
  	  int fullTimeSalariedNonExemptHoursPerWeekIdeal = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Non_Exempt_Hours_Per_Week_Ideal"));
  	  int fullTimeSalariedNonExemptShiftsPerWeekMin = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Non_Exempt_Shifts_Per_Week_Min"));
  	  int fullTimeSalariedNonExemptShiftsPerWeekMax = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Non_Exempt_Shifts_Per_Week_Max"));
  	  int fullTimeSalariedNonExemptShiftsPerWeekIdeal = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Non_Exempt_Shifts_Per_Week_Ideal"));
  	  int fullTimeSalariedNonExemptHoursPerShiftMin = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Non_Exempt_Hours_Per_Shift_Min"));
  	  int fullTimeSalariedNonExemptHoursPerShiftMax = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Non_Exempt_Hours_Per_Shift_Max"));
  	  int fullTimeSalariedNonExemptHoursPerShiftIdeal = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Non_Exempt_Hours_Per_Shift_Ideal"));
  	  boolean fullTimeSalariedNonExemptIsEmployeeCommittedAvailability = Boolean.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Non_Exempt_Is_Employee_Committed_Availability"));
  	  int fullTimeSalariedNonExemptCommittedHoursWeeks = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Salaried_Non_Exempt_Committed_Hours_Weeks"));
  	  
  	  controlsNewUIPage.updateSchedulingPolicyGroupsHoursPerWeek(fullTimeSalariedNonExemptHoursPerWeekMin, 
  		  fullTimeSalariedNonExemptHoursPerWeekMax, fullTimeSalariedNonExemptHoursPerWeekIdeal);
  	  controlsNewUIPage.updateSchedulingPolicyGroupsShiftsPerWeek(fullTimeSalariedNonExemptShiftsPerWeekMin,
  		  fullTimeSalariedNonExemptShiftsPerWeekMax,fullTimeSalariedNonExemptShiftsPerWeekIdeal);
  	  controlsNewUIPage.updateSchedulingPolicyGroupsHoursPerShift(fullTimeSalariedNonExemptHoursPerShiftMin,
  		  fullTimeSalariedNonExemptHoursPerShiftMax,fullTimeSalariedNonExemptHoursPerShiftIdeal);
  	  controlsNewUIPage.updateEnforceNewEmployeeCommittedAvailabilityWeeks(fullTimeSalariedNonExemptIsEmployeeCommittedAvailability,
  		  fullTimeSalariedNonExemptCommittedHoursWeeks);
  	  
  	  
	  controlsNewUIPage.selectSchdulingPolicyGroupsTabByLabel(schedulingPolicyGroupsTabs.FullTimeHourlyNonExempt.getValue());
	  int fullTimeHourlyNonExemptHoursPerWeekMin  = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Hourly_Non_Exempt_Hours_Per_Week_Min" ));
	  int fullTimeHourlyNonExemptHoursPerWeekMax = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Hourly_Non_Exempt_Hours_Per_Week_Max"));
	  int fullTimeHourlyNonExemptHoursPerWeekIdeal = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Hourly_Non_Exempt_Hours_Per_Week_Ideal"));
	  int fullTimeHourlyNonExemptShiftsPerWeekMin = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Hourly_Non_Exempt_Shifts_Per_Week_Min"));
	  int fullTimeHourlyNonExemptShiftsPerWeekMax = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Hourly_Non_Exempt_Shifts_Per_Week_Max"));
	  int fullTimeHourlyNonExemptShiftsPerWeekIdeal = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Hourly_Non_Exempt_Shifts_Per_Week_Ideal"));
	  int fullTimeHourlyNonExemptHoursPerShiftMin = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Hourly_Non_Exempt_Hours_Per_Shift_Min"));
	  int fullTimeHourlyNonExemptHoursPerShiftMax = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Hourly_Non_Exempt_Hours_Per_Shift_Max"));
	  int fullTimeHourlyNonExemptHoursPerShiftIdeal = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Hourly_Non_Exempt_Hours_Per_Shift_Ideal"));
	  boolean fullTimeHourlyNonExemptIsEmployeeCommittedAvailability = Boolean.valueOf(schedulingPoliciesData.get("Full_Time_Hourly_Non_Exempt_Is_Employee_Committed_Availability"));
	  int fullTimeHourlyNonExemptCommittedHoursWeeks = Integer.valueOf(schedulingPoliciesData.get("Full_Time_Hourly_Non_Exempt_Committed_Hours_Weeks"));
  	  
	  controlsNewUIPage.updateSchedulingPolicyGroupsHoursPerWeek(fullTimeHourlyNonExemptHoursPerWeekMin, 
	  		  fullTimeHourlyNonExemptHoursPerWeekMax, fullTimeHourlyNonExemptHoursPerWeekIdeal);
	  controlsNewUIPage.updateSchedulingPolicyGroupsShiftsPerWeek(fullTimeHourlyNonExemptShiftsPerWeekMin,
	  		  fullTimeHourlyNonExemptShiftsPerWeekMax,fullTimeHourlyNonExemptShiftsPerWeekIdeal);
	  controlsNewUIPage.updateSchedulingPolicyGroupsHoursPerShift(fullTimeHourlyNonExemptHoursPerShiftMin,
	  		  fullTimeHourlyNonExemptHoursPerShiftMax,fullTimeHourlyNonExemptHoursPerShiftIdeal);
	  controlsNewUIPage.updateEnforceNewEmployeeCommittedAvailabilityWeeks(fullTimeHourlyNonExemptIsEmployeeCommittedAvailability,
	  		  fullTimeHourlyNonExemptCommittedHoursWeeks);
	  
	  controlsNewUIPage.selectSchdulingPolicyGroupsTabByLabel(schedulingPolicyGroupsTabs.PartTimeHourlyNonExempt.getValue());
	  
	  int partTimeHourlyNonExemptHoursPerWeekMin  = Integer.valueOf(schedulingPoliciesData.get("Part_Time_Hourly_Non_Exempt_Hours_Per_Week_Min" ));
	  int partTimeHourlyNonExemptHoursPerWeekMax = Integer.valueOf(schedulingPoliciesData.get("Part_Time_Hourly_Non_Exempt_Hours_Per_Week_Max"));
	  int partTimeHourlyNonExemptHoursPerWeekIdeal = Integer.valueOf(schedulingPoliciesData.get("Part_Time_Hourly_Non_Exempt_Hours_Per_Week_Ideal"));
	  int partTimeHourlyNonExemptShiftsPerWeekMin = Integer.valueOf(schedulingPoliciesData.get("Part_Time_Hourly_Non_Exempt_Shifts_Per_Week_Min"));
	  int partTimeHourlyNonExemptShiftsPerWeekMax = Integer.valueOf(schedulingPoliciesData.get("Part_Time_Hourly_Non_Exempt_Shifts_Per_Week_Max"));
	  int partTimeHourlyNonExemptShiftsPerWeekIdeal = Integer.valueOf(schedulingPoliciesData.get("Part_Time_Hourly_Non_Exempt_Shifts_Per_Week_Ideal"));
	  int partTimeHourlyNonExemptHoursPerShiftMin = Integer.valueOf(schedulingPoliciesData.get("Part_Time_Hourly_Non_Exempt_Hours_Per_Shift_Min"));
	  int partTimeHourlyNonExemptHoursPerShiftMax = Integer.valueOf(schedulingPoliciesData.get("Part_Time_Hourly_Non_Exempt_Hours_Per_Shift_Max"));
	  int partTimeHourlyNonExemptHoursPerShiftIdeal = Integer.valueOf(schedulingPoliciesData.get("Part_Time_Hourly_Non_Exempt_Hours_Per_Shift_Ideal"));
	  boolean partTimeHourlyNonExemptIsEmployeeCommittedAvailability = Boolean.valueOf(schedulingPoliciesData.get("Part_Time_Hourly_Non_Exempt_Is_Employee_Committed_Availability"));
	  int partTimeHourlyNonExemptCommittedHoursWeeks = Integer.valueOf(schedulingPoliciesData.get("Part_Time_Hourly_Non_Exempt_Committed_Hours_Weeks"));
  	  
	  controlsNewUIPage.updateSchedulingPolicyGroupsHoursPerWeek(partTimeHourlyNonExemptHoursPerWeekMin, 
	  		  partTimeHourlyNonExemptHoursPerWeekMax, partTimeHourlyNonExemptHoursPerWeekIdeal);
	  controlsNewUIPage.updateSchedulingPolicyGroupsShiftsPerWeek(partTimeHourlyNonExemptShiftsPerWeekMin,
	  		  partTimeHourlyNonExemptShiftsPerWeekMax,partTimeHourlyNonExemptShiftsPerWeekIdeal);
	  controlsNewUIPage.updateSchedulingPolicyGroupsHoursPerShift(partTimeHourlyNonExemptHoursPerShiftMin,
	  		  partTimeHourlyNonExemptHoursPerShiftMax,partTimeHourlyNonExemptHoursPerShiftIdeal);
	  controlsNewUIPage.updateEnforceNewEmployeeCommittedAvailabilityWeeks(partTimeHourlyNonExemptIsEmployeeCommittedAvailability,
	  		  partTimeHourlyNonExemptCommittedHoursWeeks);
  }
  
  
  
	@Automated(automated =  "Automated")
	@Owner(owner = "Naval")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "TP-161: Automate all the areas of controls  with Admin, Customer Admin and SM to validate"
			+ " the fields enabled or disabled with corresponding work role access.")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void validateControlsAllFieldsEnabledOrDisabledAsInternalAdmin(String browser, String username, String password, String location)
			throws Exception {
		String fileName = "UsersCredentials.json";
        fileName=SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] teamLeadCredentials = userCredentials.get("TeamLead");
        Object[][] teamMemberCredentials = userCredentials.get("TeamMember");
        Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
		LoginPage loginPage = pageFactory.createConsoleLoginPage();
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		SimpleUtils.pass("<b>Legion Application User logged in as role 'Internal Admin'</b>.");
		if(controlsNewUIPage.isControlsConsoleMenuAvailable()) {
			verifyControlsPageAllFieldsEditableOrNot();
		}
		else
			SimpleUtils.report("Controls Console Menu not loaded Successfully!.");
	    
		loginPage.logOut();


        /*
         * Login as Store Manager
         */
        loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1]), 
        		String.valueOf(storeManagerCredentials[0][2]));
        SimpleUtils.pass("<b>Legion Application User logged in as role 'Store Manager'</b>.");
        if(controlsNewUIPage.isControlsConsoleMenuAvailable()) {
			verifyControlsPageAllFieldsEditableOrNot();
		}
		else
			SimpleUtils.report("Controls Console Menu not loaded Successfully!.");
		loginPage.logOut();
		
		 /*
         * Login as Team Lead
         */
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamLeadCredentials[0][0]), String.valueOf(teamLeadCredentials[0][1]), 
        		String.valueOf(teamLeadCredentials[0][2]));
        SimpleUtils.pass("<b>Legion Application User logged in as role 'Team Lead'</b>.");
        if(controlsNewUIPage.isControlsConsoleMenuAvailable()) {
			verifyControlsPageAllFieldsEditableOrNot();
		}
		else
			SimpleUtils.report("Controls Console Menu not loaded Successfully!.");
		loginPage.logOut();
		
		 /*
         * Login as Team Member
         */
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1]), 
        		String.valueOf(teamMemberCredentials[0][2]));
        SimpleUtils.pass("<b>Legion Application User logged in as role 'Team Member'</b>.");
        if(controlsNewUIPage.isControlsConsoleMenuAvailable()) {
			verifyControlsPageAllFieldsEditableOrNot();
		}
		else
			SimpleUtils.report("Controls Console Menu not loaded Successfully!.");
	}



	private void verifyControlsPageAllFieldsEditableOrNot() throws Exception {
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		Thread.sleep(1000);
		verifyLocationInformationEditModeFieldsEditableOrNot();
		
		// verifying Scheduling Policies 'Schedules' section Fields
    	controlsNewUIPage.clickOnControlsConsoleMenu();
	    SimpleUtils.assertOnFail("Controls Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , true);
	    controlsNewUIPage.clickOnControlsSchedulingPolicies();
	    
		verifySchedulingPoliciesAllSectionsFieldsEditableOrNot();
		controlsNewUIPage.clickOnGlobalLocationButton();
		verifySchedulingPoliciesAllSectionsFieldsEditableOrNot();
		
		controlsNewUIPage.clickOnControlsConsoleMenu();
		controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
		verifyingScheduleCollaborationFieldsEditableOrNot();
		controlsNewUIPage.clickOnGlobalLocationButton();
		verifyingScheduleCollaborationFieldsEditableOrNot();
		
		controlsNewUIPage.clickOnControlsConsoleMenu();
		controlsNewUIPage.clickOnControlsComplianceSection();
		if(controlsNewUIPage.isControlsComplianceLoaded()) {
			verifyingComplianceFieldsEditableOrNot();
			controlsNewUIPage.clickOnGlobalLocationButton();
			verifyingComplianceFieldsEditableOrNot();
		}
		
		controlsNewUIPage.clickOnControlsConsoleMenu();
		controlsNewUIPage.clickOnControlsUsersAndRolesSection();
		if(controlsNewUIPage.isControlsUsersAndRolesLoaded()) {
			controlsNewUIPage.selectUsersAndRolesSubTabByLabel(usersAndRolesSubTabs.AllUsers.getValue());
			verifyingUserAndRolesAddNewUserPageFieldsEditableOrNot();
			String userFirstName = "David";
			verifyingUserAndRolesEditUserPageFieldsEditableOrNot(userFirstName);
			controlsNewUIPage.selectUsersAndRolesSubTabByLabel(usersAndRolesSubTabs.AccessByJobTitles.getValue());
			String employeeJobTitle = "Retail Manager";
			verifyingUserAndRolesUpdateEmployeeJobTitleEditableOrNonEditableFields(employeeJobTitle);
			String newEmployeeJobTitle = "Sample Employee Job Title";
			String newEmployeeJobTitleRole = "Store Manager";
			verifyingUserAndRolesCreatNewEmployeeJobTitleEditableOrNonEditableFields(newEmployeeJobTitle, newEmployeeJobTitleRole);
			String badgeLabel = "Employee From Mars";
			controlsNewUIPage.selectUsersAndRolesSubTabByLabel(usersAndRolesSubTabs.Badges.getValue());
			verifyingUserAndRolesUpdateBadgesEditableOrNonEditableFields(badgeLabel);
			verifyingUserAndRolesCreateNewBadgesEditableOrNonEditableFields();
		}
		
		// Tasks and Work Roles Section
		controlsNewUIPage.clickOnControlsConsoleMenu();
		controlsNewUIPage.clickOnControlsTasksAndWorkRolesSection();
		if(controlsNewUIPage.isControlsTasksAndWorkRolesLoaded()) {
			verifyingTasksAndWorkRolesSectionWorkRolesTabEditableOrNonEditableFields();
		}
		
		controlsNewUIPage.clickOnControlsConsoleMenu();
		controlsNewUIPage.clickOnControlsTasksAndWorkRolesSection();
		if(controlsNewUIPage.isControlsTasksAndWorkRolesLoaded()) {
			verifyingTasksAndWorkRolesSectionLaborCalculatorTabEditableOrNonEditableFields();
		}
		// Global Location
	
		controlsNewUIPage.clickOnControlsConsoleMenu();
		controlsNewUIPage.clickOnControlsTasksAndWorkRolesSection();
		controlsNewUIPage.clickOnGlobalLocationButton();
		if(controlsNewUIPage.isControlsTasksAndWorkRolesLoaded()) {
			verifyingTasksAndWorkRolesAddWorkRolePageEditableOrNonEditableFields();
			verifyingTasksAndWorkRolesSectionWorkRolesTabEditableOrNonEditableFields();
		}
		
		controlsNewUIPage.clickOnControlsConsoleMenu();
		controlsNewUIPage.clickOnControlsTasksAndWorkRolesSection();
		controlsNewUIPage.clickOnGlobalLocationButton();
		if(controlsNewUIPage.isControlsTasksAndWorkRolesLoaded()) {
			verifyingTasksAndWorkRolesSectionLaborCalculatorTabEditableOrNonEditableFields();
		}
		
		controlsNewUIPage.clickOnControlsConsoleMenu();
		controlsNewUIPage.clickOnControlsWorkingHoursCard();
		verifyUpdateControlsRegularHoursPopupEditableOrNonEditableFields();
		verifyUpdateControlsHolidayHoursPopupEditableOrNonEditableFields();
		controlsNewUIPage.clickOnGlobalLocationButton();
		verifyUpdateControlsRegularHoursPopupEditableOrNonEditableFields();
		verifyUpdateControlsHolidayHoursPopupEditableOrNonEditableFields();
	}


	private void verifyLocationInformationEditModeFieldsEditableOrNot() throws Exception {
		 DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	     SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);  
	     
	     ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
	     if(controlsNewUIPage.isControlsConsoleMenuAvailable()) {
	    	 // Validating Location Profile Section
	    	 controlsNewUIPage.clickOnControlsConsoleMenu();
		     SimpleUtils.assertOnFail("Controls Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , true);
		     controlsNewUIPage.clickOnControlsLocationProfileSection();
		     HashMap<String,ArrayList<String>> locationInfoEditableOrNonEditableFields = controlsNewUIPage
		    		 .getLocationInformationEditableOrNonEditableFields();
		     
		     int valuesMaxCount = locationInfoEditableOrNonEditableFields.get("editableFields").size();
		     if(locationInfoEditableOrNonEditableFields.get("nonEditableFields").size() > valuesMaxCount)
		    	 valuesMaxCount = locationInfoEditableOrNonEditableFields.get("nonEditableFields").size();
		     if(valuesMaxCount > 0) {
		    	 
		    	 String editableOrNonEditableFieldsValueTable = getEditableNonEditableFieldsAsHTMLTable(locationInfoEditableOrNonEditableFields);
		    	 SimpleUtils.pass("Location Profile: Edit Location Information Input fields Editable or Non Editable details.<br>"
		    			 +editableOrNonEditableFieldsValueTable);
		     }
	     }
	     else
	    	 SimpleUtils.report("Controls menu not available for active user.");
	}
	
	private void verifySchedulingPoliciesAllSectionsFieldsEditableOrNot() throws Exception {
		verifySchedulingPoliciesSchedulesSectionFieldsEditableOrNot();
		verifySchedulingPoliciesShiftsSectionFieldsEditableOrNot();
		verifySchedulingPoliciesBudgetSectionFieldsEditableOrNot();
		verifySchedulingPoliciesTeamAvailabilityManagementSectionFieldsEditableOrNot();
		verifySchedulingPoliciesTimeOffSectionFieldsEditableOrNot();
		// Verifying Scheduling Policy Groups Fields
		verifySchedulingPoliciesSchedulingPolicyGroupsFieldsEditableorNot(schedulingPolicyGroupsTabs.FullTimeSalariedExempt.getValue());
		verifySchedulingPoliciesSchedulingPolicyGroupsFieldsEditableorNot(schedulingPolicyGroupsTabs.FullTimeSalariedNonExempt.getValue());
		verifySchedulingPoliciesSchedulingPolicyGroupsFieldsEditableorNot(schedulingPolicyGroupsTabs.FullTimeHourlyNonExempt.getValue());
		verifySchedulingPoliciesSchedulingPolicyGroupsFieldsEditableorNot(schedulingPolicyGroupsTabs.PartTimeHourlyNonExempt.getValue());
	}
	
	private void verifySchedulingPoliciesSchedulesSectionFieldsEditableOrNot() throws Exception {
		 ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
	     controlsNewUIPage.clickOnSchedulingPoliciesSchedulesAdvanceBtn();
	     Thread.sleep(2000);
	     HashMap<String,ArrayList<String>> schedulingPoliciesSchedulesEditableNonEditableFields = controlsNewUIPage
	    		 .getSchedulingPoliciesSchedulesSectionEditableOrNonEditableFields();
	     
	     int valuesMaxCount = schedulingPoliciesSchedulesEditableNonEditableFields.get("editableFields").size();
	     if(schedulingPoliciesSchedulesEditableNonEditableFields.get("nonEditableFields").size() > valuesMaxCount)
	    	 valuesMaxCount = schedulingPoliciesSchedulesEditableNonEditableFields.get("nonEditableFields").size();
	     if(valuesMaxCount > 0) {
	    	 
	    	 String editableOrNonEditableFieldsValueTable = getEditableNonEditableFieldsAsHTMLTable(schedulingPoliciesSchedulesEditableNonEditableFields);
	    	 SimpleUtils.pass("Scheduling Policies: 'Schedules' Section Input fields Editable or Non Editable details.<br>"
	    			 +editableOrNonEditableFieldsValueTable);
	     }
	    
	}
	
	private void verifySchedulingPoliciesShiftsSectionFieldsEditableOrNot() throws Exception {
		 ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
	     controlsNewUIPage.clickOnSchedulingPoliciesShiftAdvanceBtn();
	     Thread.sleep(2000);
	     HashMap<String,ArrayList<String>> schedulingPoliciesShiftsEditableNonEditableFields = controlsNewUIPage
	    		 .getSchedulingPoliciesShiftsSectionEditableOrNonEditableFields();
	     
	     int valuesMaxCount = schedulingPoliciesShiftsEditableNonEditableFields.get("editableFields").size();
	     if(schedulingPoliciesShiftsEditableNonEditableFields.get("nonEditableFields").size() > valuesMaxCount)
	    	 valuesMaxCount = schedulingPoliciesShiftsEditableNonEditableFields.get("nonEditableFields").size();
	     if(valuesMaxCount > 0) {
	    	 String editableOrNonEditableFieldsValueTable = getEditableNonEditableFieldsAsHTMLTable(schedulingPoliciesShiftsEditableNonEditableFields);
	    	 SimpleUtils.pass("Scheduling Policies: 'Shifts' Section Input fields Editable or Non Editable details.<br>"
	    			 +editableOrNonEditableFieldsValueTable);
	     }
	}
	
	private void verifySchedulingPoliciesBudgetSectionFieldsEditableOrNot() throws Exception {
		 ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
	     Thread.sleep(2000);
	     HashMap<String,ArrayList<String>> schedulingPoliciesBudgetEditableNonEditableFields = controlsNewUIPage
	    		 .getSchedulingPoliciesBudgetSectionEditableOrNonEditableFields();
	     
	     int valuesMaxCount = schedulingPoliciesBudgetEditableNonEditableFields.get("editableFields").size();
	     if(schedulingPoliciesBudgetEditableNonEditableFields.get("nonEditableFields").size() > valuesMaxCount)
	    	 valuesMaxCount = schedulingPoliciesBudgetEditableNonEditableFields.get("nonEditableFields").size();
	     if(valuesMaxCount > 0) {
	    	 String editableOrNonEditableFieldsValueTable = getEditableNonEditableFieldsAsHTMLTable(schedulingPoliciesBudgetEditableNonEditableFields);
	    	 SimpleUtils.pass("Scheduling Policies: 'Budget' Section Input fields Editable or Non Editable details.<br>"
	    			 +editableOrNonEditableFieldsValueTable);
	     }
	}
	
	private void verifySchedulingPoliciesTeamAvailabilityManagementSectionFieldsEditableOrNot() throws Exception {
		 ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
	     Thread.sleep(2000);
	     HashMap<String,ArrayList<String>> schedulingPoliciesTeamAvailabilityManagementEditableNonEditableFields = controlsNewUIPage
	    		 .getSchedulingPoliciesTeamAvailabilityManagementSectionEditableOrNonEditableFields();
	     
	     int valuesMaxCount = schedulingPoliciesTeamAvailabilityManagementEditableNonEditableFields.get("editableFields").size();
	     if(schedulingPoliciesTeamAvailabilityManagementEditableNonEditableFields.get("nonEditableFields").size() > valuesMaxCount)
	    	 valuesMaxCount = schedulingPoliciesTeamAvailabilityManagementEditableNonEditableFields.get("nonEditableFields").size();
	     if(valuesMaxCount > 0) {
	    	 String editableOrNonEditableFieldsValueTable = getEditableNonEditableFieldsAsHTMLTable(schedulingPoliciesTeamAvailabilityManagementEditableNonEditableFields);
	    	 SimpleUtils.pass("Scheduling Policies: 'Team Availability Management' Section Input fields Editable or Non Editable details.<br>"
	    			 +editableOrNonEditableFieldsValueTable);
	     }
	}
	
	private void verifySchedulingPoliciesTimeOffSectionFieldsEditableOrNot() throws Exception {
		 ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
	     Thread.sleep(2000);
	     controlsNewUIPage.clickOnSchedulingPoliciesTimeOffAdvanceBtn();
	     HashMap<String,ArrayList<String>> schedulingPoliciesTimeOffEditableNonEditableFields = controlsNewUIPage
	    		 .getSchedulingPoliciesTimeOffSectionEditableOrNonEditableFields();
	     
	     
	     
	     int valuesMaxCount = schedulingPoliciesTimeOffEditableNonEditableFields.get("editableFields").size();
	     if(schedulingPoliciesTimeOffEditableNonEditableFields.get("nonEditableFields").size() > valuesMaxCount)
	    	 valuesMaxCount = schedulingPoliciesTimeOffEditableNonEditableFields.get("nonEditableFields").size();
	     if(valuesMaxCount > 0) {
	    	 String editableOrNonEditableFieldsValueTable = getEditableNonEditableFieldsAsHTMLTable(schedulingPoliciesTimeOffEditableNonEditableFields);
	    	 SimpleUtils.pass("Scheduling Policies: 'Time Off' Section Input fields Editable or Non Editable details.<br>"
	    			 +editableOrNonEditableFieldsValueTable);
	     }
	}
	
	private void verifySchedulingPoliciesSchedulingPolicyGroupsFieldsEditableorNot(String schedulingPolicyGroupsTabsLabel) throws Exception {
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		controlsNewUIPage.selectSchdulingPolicyGroupsTabByLabel(schedulingPolicyGroupsTabsLabel);
	    HashMap<String,ArrayList<String>> schedulingPoliciesSchedulingPolicyGroupsEditableNonEditableFields = controlsNewUIPage
	    		 .getSchedulingPoliciesSchedulingPolicyGroupsSectionEditableOrNonEditableFields();
	     
	     int valuesMaxCount = schedulingPoliciesSchedulingPolicyGroupsEditableNonEditableFields.get("editableFields").size();
	     if(schedulingPoliciesSchedulingPolicyGroupsEditableNonEditableFields.get("nonEditableFields").size() > valuesMaxCount)
	    	 valuesMaxCount = schedulingPoliciesSchedulingPolicyGroupsEditableNonEditableFields.get("nonEditableFields").size();
	     if(valuesMaxCount > 0) {
	    	 String editableOrNonEditableFieldsValueTable = getEditableNonEditableFieldsAsHTMLTable(schedulingPoliciesSchedulingPolicyGroupsEditableNonEditableFields);
	    	 SimpleUtils.pass("Scheduling Policies: 'Scheduling Policy Groups - "+schedulingPolicyGroupsTabsLabel
	    			 +"' Section Input fields Editable or Non Editable details.<br>"
	    			 +editableOrNonEditableFieldsValueTable);
	     }

	}
	
	private void verifyingScheduleCollaborationFieldsEditableOrNot() throws Exception {
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		controlsNewUIPage.clickOnScheduleCollaborationOpenShiftAdvanceBtn();
	    HashMap<String,ArrayList<String>> scheduleCollaborationEditableNonEditableFields = controlsNewUIPage
	    		 .getScheduleCollaborationEditableOrNonEditableFields();
	     
	     int valuesMaxCount = scheduleCollaborationEditableNonEditableFields.get("editableFields").size();
	     if(scheduleCollaborationEditableNonEditableFields.get("nonEditableFields").size() > valuesMaxCount)
	    	 valuesMaxCount = scheduleCollaborationEditableNonEditableFields.get("nonEditableFields").size();
	     if(valuesMaxCount > 0) {
	    	 String editableOrNonEditableFieldsValueTable = getEditableNonEditableFieldsAsHTMLTable(scheduleCollaborationEditableNonEditableFields);
	    	 SimpleUtils.pass("Controls Page: 'Schedule Collaboration' Section Input fields Editable or Non Editable details.<br>"
	    			 +editableOrNonEditableFieldsValueTable);
	     }
	}
	
	private void verifyingComplianceFieldsEditableOrNot() throws Exception {
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
	    HashMap<String,ArrayList<String>> complianceEditableNonEditableFields = controlsNewUIPage
	    		 .getComplianceEditableOrNonEditableFields();
	     
	     int valuesMaxCount = complianceEditableNonEditableFields.get("editableFields").size();
	     if(complianceEditableNonEditableFields.get("nonEditableFields").size() > valuesMaxCount)
	    	 valuesMaxCount = complianceEditableNonEditableFields.get("nonEditableFields").size();
	     if(valuesMaxCount > 0) {
	    	 String editableOrNonEditableFieldsValueTable = getEditableNonEditableFieldsAsHTMLTable(complianceEditableNonEditableFields);
	    	 SimpleUtils.pass("Controls Page: 'Compliance' Section Input fields Editable or Non Editable details.<br>"
	    			 +editableOrNonEditableFieldsValueTable);
	     }
	}
	
	private void verifyingUserAndRolesAddNewUserPageFieldsEditableOrNot() throws Exception {
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
	    HashMap<String,ArrayList<String>> complianceEditableNonEditableFields = controlsNewUIPage
	    		 .getUsersAndRolesAddNewUserPageEditableOrNonEditableFields();
	     String editableOrNonEditableFieldsValueTable = getEditableNonEditableFieldsAsHTMLTable(complianceEditableNonEditableFields);
	    	 SimpleUtils.pass("Controls Page: 'User and Roles' Section 'Add New User page' Input fields Editable or Non Editable details.<br>"
	    			 +editableOrNonEditableFieldsValueTable);
	}
	
	public String getEditableNonEditableFieldsAsHTMLTable(HashMap<String,ArrayList<String>> editableNonEditableFields)
	{
		 String result = "";
		 int valuesMaxCount = editableNonEditableFields.get("editableFields").size();
	     if(editableNonEditableFields.get("nonEditableFields").size() > valuesMaxCount)
	    	 valuesMaxCount = editableNonEditableFields.get("nonEditableFields").size();
	     if(valuesMaxCount > 0) {
	    	 result = "<table><tr><th> Editable Fields Title </th><th>Non Editable Fields Title</th></tr>";
	    	 for(int index = 0; index < valuesMaxCount; index++) {
	    		 result = result + "<tr><td>";
		    	 if(editableNonEditableFields.get("editableFields").size() > index) {
		    		 result = result + editableNonEditableFields.get("editableFields").get(index);
		    	 }
		    	 result = result + "</td><td>";
		    	 if(editableNonEditableFields.get("nonEditableFields").size() > index) {
		    		 result = result + editableNonEditableFields.get("nonEditableFields").get(index);
		    	 }
		    	 result = result + "</td></tr>";
		     }
	    	 result = result + "</table>";
	     }
		return result;
	}
	
	private void verifyingUserAndRolesEditUserPageFieldsEditableOrNot(String userFirstName) throws Exception {
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
	    HashMap<String,ArrayList<String>> complianceEditableNonEditableFields = controlsNewUIPage
	    		 .getUsersAndRolesEditUserPageEditableOrNonEditableFields(userFirstName);
	     String editableOrNonEditableFieldsValueTable = getEditableNonEditableFieldsAsHTMLTable(complianceEditableNonEditableFields);
	    	 SimpleUtils.pass("Controls Page: 'User and Roles' Section 'Add New User page' Input fields Editable or Non Editable details.<br>"
	    			 +editableOrNonEditableFieldsValueTable);
	}
	
	private void verifyingUserAndRolesUpdateEmployeeJobTitleEditableOrNonEditableFields(String employeeJobTitle) throws Exception {
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		HashMap<String, ArrayList<String>> employeeJobTitleEditableOrNonEditableFields = controlsNewUIPage
				.getUsersAndRolesUpdateEmployeeJobTitleEditableOrNonEditableFields(employeeJobTitle);

	     String editableOrNonEditableFieldsValueTable = getEditableNonEditableFieldsAsHTMLTable(employeeJobTitleEditableOrNonEditableFields);
	    	 SimpleUtils.pass("Controls Page: 'User and Roles' Section 'Update Employee Job Title' Input fields Editable or Non Editable details.<br>"
	    			 +editableOrNonEditableFieldsValueTable);
	}
	
	private void verifyingUserAndRolesCreatNewEmployeeJobTitleEditableOrNonEditableFields(String employeeJobTitle, String newEmployeeJobTitleRole) throws Exception {
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		HashMap<String, ArrayList<String>> employeeJobTitleEditableOrNonEditableFields = controlsNewUIPage
				.getUsersAndRolesCreateNewEmployeeJobTitleEditableOrNonEditableFields(employeeJobTitle, newEmployeeJobTitleRole);

	     String editableOrNonEditableFieldsValueTable = getEditableNonEditableFieldsAsHTMLTable(employeeJobTitleEditableOrNonEditableFields);
	    	 SimpleUtils.pass("Controls Page: 'User and Roles' Section 'New Employee Job Title' Input fields Editable or Non Editable details.<br>"
	    			 +editableOrNonEditableFieldsValueTable);
		
	}
	
	private void verifyingUserAndRolesUpdateBadgesEditableOrNonEditableFields(String badgeLabel) throws Exception {
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		HashMap<String, ArrayList<String>> updateBadgeEditableOrNonEditableFields = controlsNewUIPage
				.getUsersAndRolesUpdateBadgesEditableOrNonEditableFields(badgeLabel);

	     String editableOrNonEditableFieldsValueTable = getEditableNonEditableFieldsAsHTMLTable(updateBadgeEditableOrNonEditableFields);
	    	 SimpleUtils.pass("Controls Page: 'User and Roles' Section 'Update Badges Popup' Input fields Editable or Non Editable details.<br>"
	    			 +editableOrNonEditableFieldsValueTable);
	}
	
	private void verifyingUserAndRolesCreateNewBadgesEditableOrNonEditableFields() throws Exception {
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		HashMap<String, ArrayList<String>> newBadgeEditableOrNonEditableFields = controlsNewUIPage
				.getUsersAndRolesNewBadgeEditableOrNonEditableFields();

	    String editableOrNonEditableFieldsValueTable = getEditableNonEditableFieldsAsHTMLTable(newBadgeEditableOrNonEditableFields);
	    	 SimpleUtils.pass("Controls Page: 'User and Roles' Section 'New Badge Popup' Input fields Editable or Non Editable details.<br>"
	    			 +editableOrNonEditableFieldsValueTable);
	}
	
	private void verifyingTasksAndWorkRolesSectionWorkRolesTabEditableOrNonEditableFields()throws Exception {
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		Thread.sleep(1000);
		controlsNewUIPage.selectTasksAndWorkRolesSubTabByLabel(tasksAndWorkRolesSubTab.WorkRoles.getValue());
		List<WebElement> workRolesList = controlsNewUIPage.getTasksAndWorkRolesSectionAllWorkRolesList();
		SimpleUtils.pass("Tasks and Work Roles Section: '"+workRolesList.size()+"' workroles loaded.");
		if(workRolesList.size() > 0) {
			HashMap<String, ArrayList<String>> editWorkRolePropertiesEditableOrNonEditableFields = controlsNewUIPage
					.getTasksAndWorkRolesEditWorkRolePropertiesEditableOrNonEditableFields();
			
			String editableOrNonEditableFieldsValueTable = getEditableNonEditableFieldsAsHTMLTable(editWorkRolePropertiesEditableOrNonEditableFields);
	    	SimpleUtils.pass("Controls Page: 'Tasks and Work Roles' Section 'Edit Work Role Properties' Input fields Editable or Non Editable details.<br>"
	    			 +editableOrNonEditableFieldsValueTable);
		}
		boolean isWorkRoleDetailsSectionExapandebale = controlsNewUIPage.isWorkRoleDetailPageSubSectionsExpandFunctionalityWorking();
		if(isWorkRoleDetailsSectionExapandebale)
			SimpleUtils.pass("Tasks and Work Roles Section: 'Work Roles Details' Page Details Sections are Expending successfully");
		
	}
	
	private void verifyingTasksAndWorkRolesSectionLaborCalculatorTabEditableOrNonEditableFields() throws Exception {
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		Thread.sleep(2000);
		controlsNewUIPage.selectTasksAndWorkRolesSubTabByLabel(tasksAndWorkRolesSubTab.LaborCalculator.getValue());
		Thread.sleep(1000);
		if(controlsNewUIPage.isLaborCalculationTabLoaded()) {
				HashMap<String, ArrayList<String>> editWorkRolePropertiesEditableOrNonEditableFields = controlsNewUIPage
						.getTasksAndWorkRolesLaborCalculatorTabEditableOrNonEditableFields();
				
				String editableOrNonEditableFieldsValueTable = getEditableNonEditableFieldsAsHTMLTable(editWorkRolePropertiesEditableOrNonEditableFields);
		    	SimpleUtils.pass("Controls Page: 'Tasks and Work Roles' Section 'Edit Work Role Properties' Input fields Editable or Non Editable details.<br>"
		    			 +editableOrNonEditableFieldsValueTable);
		}
		else
			SimpleUtils.fail("Tasks and Work Roles Section: 'Labor Calculation' Tab not loaded.", true);
	}

	private void verifyingTasksAndWorkRolesAddWorkRolePageEditableOrNonEditableFields() throws Exception {
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		Thread.sleep(2000);
		HashMap<String, ArrayList<String>> tasksAndWorkRolesAddWorkRolePageEditableOrNonEditableFields = controlsNewUIPage
				.getTasksAndWorkRolesAddWorkRolePageEditableOrNonEditableFields();
		
		String editableOrNonEditableFieldsValueTable = getEditableNonEditableFieldsAsHTMLTable(tasksAndWorkRolesAddWorkRolePageEditableOrNonEditableFields);
    	SimpleUtils.pass("Controls Page: 'Tasks and Work Roles' Section 'Add Work Role' page Input fields Editable or Non Editable details.<br>"
    			 +editableOrNonEditableFieldsValueTable);
	}
	
	private void verifyUpdateControlsRegularHoursPopupEditableOrNonEditableFields() throws Exception {
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		HashMap<String, ArrayList<String>> regularHoursPopupEditableOrNonEditableFields = controlsNewUIPage.verifyUpdateControlsRegularHoursPopupEditableOrNonEditableFields();
		String editableOrNonEditableFieldsValueTable = getEditableNonEditableFieldsAsHTMLTable(regularHoursPopupEditableOrNonEditableFields);
    	SimpleUtils.pass("Controls Working Hours Section: Regular Hours 'Edit' popup Editable or Not Editable fields.<br>"
    			 +editableOrNonEditableFieldsValueTable);
	}
	
	private void verifyUpdateControlsHolidayHoursPopupEditableOrNonEditableFields() throws Exception {
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		HashMap<String, ArrayList<String>> holidayHoursPopupEditableOrNonEditableFields = controlsNewUIPage.verifyUpdateControlsHolidayHoursPopupEditableOrNonEditableFields();
		String editableOrNonEditableFieldsValueTable = getEditableNonEditableFieldsAsHTMLTable(holidayHoursPopupEditableOrNonEditableFields);
    	SimpleUtils.pass("Controls Working Hours Section: Holiday Hours 'Edit' popup Editable or Not Editable fields.<br>"
    			 +editableOrNonEditableFieldsValueTable);
	}


	//added by Nishant

	@Automated(automated =  "Automated")
	@Owner(owner = "Nishant")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the breadcrumb on controls tab")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyControlsBreadcrumbAsInternalAdmin(String browser, String username, String password, String location)
			throws Exception {

		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		//navigateToControlsSchedulingPolicies(controlsNewUIPage);
		controlsNewUIPage.clickOnControlsConsoleMenu();
		SimpleUtils.assertOnFail("Control Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
		controlsNewUIPage.clickOnGlobalLocationButton();
		controlsNewUIPage.verifyAllLocations(schedulingPoliciesData.get("ALL_LOCATIONS"));
		controlsNewUIPage.verifySearchLocations(schedulingPoliciesData.get("SEARCH_LOCATION_TEXT"));
	}


	@Automated(automated =  "Automated")
	@Owner(owner = "Nishant")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the options available when Global selected")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyOptionWhenGlobalSelectedAsInternalAdmin(String browser, String username, String password, String location)
			throws Exception {

		String fileName = "UsersCredentials.json";
		fileName=SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
		HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
		Object[][] teamLeadCredentials = userCredentials.get("TeamLead");
		Object[][] teamMemberCredentials = userCredentials.get("TeamMember");
		Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
		LoginPage loginPage = pageFactory.createConsoleLoginPage();
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		controlsNewUIPage.clickOnControlsConsoleMenu();
		SimpleUtils.pass("<b>Legion Application User logged in as role 'Internal Admin'</b>.");
		SimpleUtils.assertOnFail("Control Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
		controlsNewUIPage.clickOnGlobalLocationButton();
		// Validate Controls Company Profile Section
		boolean isCompanyProfileSection = controlsNewUIPage.isControlsCompanyProfileCard();
		SimpleUtils.assertOnFail("Controls Page: Company Profile Section not Loaded.", isCompanyProfileSection, true);
		verifyControlsAllSection();
		loginPage.logOut();

		/*
		 * Login as Store Manager
		 */
		loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1]),
				String.valueOf(storeManagerCredentials[0][2]));
		SimpleUtils.pass("<b>Legion Application User logged in as role 'Store Manager'</b>.");
		controlsNewUIPage.clickOnControlsConsoleMenu();
		SimpleUtils.assertOnFail("Control Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
		controlsNewUIPage.clickOnGlobalLocationButton();
		verifyControlsAllSection();
	}

	@Automated(automated =  "Automated")
	@Owner(owner = "Nishant")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate functioning of Controls option available")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void validateSchedulingPoliciesControlOptionAsInternalAdmin(String browser, String username, String password, String location)
			throws Exception {

		String fileName = "UsersCredentials.json";
		fileName=SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
		HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
		Object[][] teamLeadCredentials = userCredentials.get("TeamLead");
		Object[][] teamMemberCredentials = userCredentials.get("TeamMember");
		Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
		LoginPage loginPage = pageFactory.createConsoleLoginPage();
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		verifyControlBreadcrumbForSchedulingPolicies();
		loginPage.logOut();

		/*
		 * Login as Store Manager
		 */
		loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1]),
				String.valueOf(storeManagerCredentials[0][2]));
		SimpleUtils.pass("<b>Legion Application User logged in as role 'Store Manager'</b>.");
		verifyControlBreadcrumbForSchedulingPolicies();
	}


	@Automated(automated =  "Automated")
	@Owner(owner = "Nishant")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the Schedule Publish Window")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void validateSchedulePublishWindowAsInternalAdmin(String browser, String username, String password, String location)
			throws Exception {

		String fileName = "UsersCredentials.json";
		fileName=SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
		HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
		Object[][] teamLeadCredentials = userCredentials.get("TeamLead");
		Object[][] teamMemberCredentials = userCredentials.get("TeamMember");
		Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
		LoginPage loginPage = pageFactory.createConsoleLoginPage();
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		String publishWindowAdvanceWeeks = schedulingPoliciesData.get("Schedule_Publish_Window");
		String publishWindowQuestion = schedulingPoliciesData.get("Schedule_Publish_Window_Question");
		controlsNewUIPage.clickOnControlsConsoleMenu();
		SimpleUtils.assertOnFail("Control Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
		controlsNewUIPage.clickOnControlsSchedulingPolicies();
		boolean isSchedulingPolicies = controlsNewUIPage.isControlsSchedulingPoliciesLoaded();
		SimpleUtils.assertOnFail("Controls Page: Scheduling Policies Section not Loaded.", isSchedulingPolicies, true);
		controlsNewUIPage.verifySchedulePublishWindow(publishWindowAdvanceWeeks, publishWindowQuestion,"InternalAdmin");
		String selectedOptionLabel = controlsNewUIPage.getSchedulePublishWindowWeeks();
		controlsNewUIPage.getSchedulePublishWindowWeeksDropDownValues();
		controlsNewUIPage.updateSchedulePublishWindow(publishWindowAdvanceWeeks,false,false);
		controlsNewUIPage.clickOnGlobalLocationButton();
		controlsNewUIPage.verifySchedulePublishWindow(publishWindowAdvanceWeeks, publishWindowQuestion,"InternalAdmin");
		controlsNewUIPage.updateSchedulePublishWindow(publishWindowAdvanceWeeks,true,false);
		List<String> selectionOptionLabelAfterUpdation = controlsNewUIPage.getSchedulePublishWindowValueAtDifferentLocations(true);
		controlsNewUIPage.verifySchedulePublishWindowUpdationValues(publishWindowAdvanceWeeks, selectionOptionLabelAfterUpdation);
		loginPage.logOut();

		/*
		 * Login as Store Manager
		 */
		loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1]),
				String.valueOf(storeManagerCredentials[0][2]));
		SimpleUtils.pass("<b>Legion Application User logged in as role 'Store Manager'</b>.");
		controlsNewUIPage.clickOnControlsConsoleMenu();
		SimpleUtils.assertOnFail("Control Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
		controlsNewUIPage.clickOnControlsSchedulingPolicies();
		boolean isSchedulingPolicies1 = controlsNewUIPage.isControlsSchedulingPoliciesLoaded();
		SimpleUtils.assertOnFail("Controls Page: Scheduling Policies Section not Loaded.", isSchedulingPolicies1, true);
		controlsNewUIPage.verifySchedulePublishWindow(publishWindowAdvanceWeeks, publishWindowQuestion,"StoreManager");
	}


	@Automated(automated =  "Automated")
	@Owner(owner = "Nishant")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the Schedule Publish Window")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void validateSchedulePlanningWindowAsInternalAdmin(String browser, String username, String password, String location)
			throws Exception {

		String fileName = "UsersCredentials.json";
		fileName=SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
		HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
		Object[][] teamLeadCredentials = userCredentials.get("TeamLead");
		Object[][] teamMemberCredentials = userCredentials.get("TeamMember");
		Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
		LoginPage loginPage = pageFactory.createConsoleLoginPage();
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		String planningWindowAdvanceWeeks = schedulingPoliciesData.get("Schedule_Planning_Window");
		String planningWindowQuestion = schedulingPoliciesData.get("Schedule_Planning_Window_Question");
		controlsNewUIPage.clickOnControlsConsoleMenu();
		SimpleUtils.assertOnFail("Control Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
		controlsNewUIPage.clickOnControlsSchedulingPolicies();
		boolean isSchedulingPolicies = controlsNewUIPage.isControlsSchedulingPoliciesLoaded();
		SimpleUtils.assertOnFail("Controls Page: Scheduling Policies Section not Loaded.", isSchedulingPolicies, true);
		controlsNewUIPage.verifySchedulePlanningWindow(planningWindowAdvanceWeeks, planningWindowQuestion,"InternalAdmin");
		String selectedOptionLabel = controlsNewUIPage.getSchedulePlanningWindowWeeks();
		controlsNewUIPage.getSchedulePlanningWindowWeeksDropDownValues();
		controlsNewUIPage.updateSchedulePlanningWindow(planningWindowAdvanceWeeks,false,false);
		controlsNewUIPage.clickOnGlobalLocationButton();
		controlsNewUIPage.verifySchedulePlanningWindow(planningWindowAdvanceWeeks, planningWindowQuestion,"InternalAdmin");
		controlsNewUIPage.updateSchedulePlanningWindow(planningWindowAdvanceWeeks,false,true);
		List<String> selectionOptionLabelAfterUpdation = controlsNewUIPage.getSchedulePublishWindowValueAtDifferentLocations(false);
		controlsNewUIPage.verifySchedulePlanningWindowUpdationValues(planningWindowAdvanceWeeks, selectionOptionLabelAfterUpdation);
//		loginPage.logOut();
//
//		/*
//		 * Login as Store Manager
//		 */
//		loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1]),
//				String.valueOf(storeManagerCredentials[0][2]));
//		SimpleUtils.pass("<b>Legion Application User logged in as role 'Store Manager'</b>.");
//		controlsNewUIPage.clickOnControlsConsoleMenu();
//		SimpleUtils.assertOnFail("Control Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
//		controlsNewUIPage.clickOnControlsSchedulingPolicies();
//		boolean isSchedulingPolicies1 = controlsNewUIPage.isControlsSchedulingPoliciesLoaded();
//		SimpleUtils.assertOnFail("Controls Page: Scheduling Policies Section not Loaded.", isSchedulingPolicies1, true);
//		controlsNewUIPage.verifySchedulePlanningWindow(planningWindowAdvanceWeeks, planningWindowAdvanceWeeks,"StoreManager");
	}



	public void verifyControlBreadcrumbForSchedulingPolicies() throws Exception {
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		controlsNewUIPage.clickOnControlsConsoleMenu();
		controlsNewUIPage.clickOnControlsSchedulingPolicies();
		boolean isSchedulingPolicies = controlsNewUIPage.isControlsSchedulingPoliciesLoaded();
		SimpleUtils.assertOnFail("Controls Page: Scheduling Policies Section not Loaded.", isSchedulingPolicies, true);
		controlsNewUIPage.clickOnGlobalLocationButton();
		controlsNewUIPage.verifyAllLocations(schedulingPoliciesData.get("ALL_LOCATIONS"));
		controlsNewUIPage.verifySearchLocations(schedulingPoliciesData.get("SEARCH_LOCATION_TEXT"));
	}



	public void verifyControlsAllSection() throws Exception{
		// Validate Controls Scheduling Profile Section
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		boolean isSchedulingPoliciesSection = controlsNewUIPage.isControlsSchedulingPoliciesCard();
		SimpleUtils.assertOnFail("Controls Page: Scheduling Profile Section not Loaded.", isSchedulingPoliciesSection, true);

		// Validate Controls Scheduling Collaboration Section
		boolean isSchedulingCollaborationSection = controlsNewUIPage.isControlsSchedulingCollaborationCard();
		SimpleUtils.assertOnFail("Controls Page: Scheduling Collaboration Section not Loaded.", isSchedulingCollaborationSection, true);

		// Validate Controls Compliance Section
		boolean isControlsComplianceCardSection = controlsNewUIPage.isControlsComplianceCard();
		SimpleUtils.assertOnFail("Controls Page: Compliance Section not Loaded.", isControlsComplianceCardSection, true);

		// Validate Controls User And Role Section
		boolean isUsersAndRolesSection = controlsNewUIPage.isControlsUsersAndRolesCard();
		SimpleUtils.assertOnFail("Controls Page: User And Role Section not Loaded.", isUsersAndRolesSection, true);

		// Validate Controls Task And Work Roles Section
		boolean isTaskAndWorkRolesSection = controlsNewUIPage.isControlsTaskAndWorkRolesCard();
		SimpleUtils.assertOnFail("Controls Page: Task And Work Roles Section not Loaded.", isTaskAndWorkRolesSection, true);

		// Validate Controls Company Profile Section
		boolean isWorkingHoursSection = controlsNewUIPage.isControlsWorkingHoursCard();
		SimpleUtils.assertOnFail("Controls Page: Working Hours Section not Loaded.", isWorkingHoursSection, true);

		// Validate Controls Location Section
		boolean isLocationSection = controlsNewUIPage.isControlsLocationsCard();
		SimpleUtils.assertOnFail("Controls Page: Location Section not Loaded.", isLocationSection, true);
  }

		@Automated(automated = "Automated")
		@Owner(owner = "Estelle")
		@Enterprise(name = "Kendrascott2_Enterprise")
		@TestName(description = "Controls Scheduling policies")
		@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
		public void verifyScheduleScoreIsUpdated(String username, String password, String browser, String location)
				throws Exception {
			String Budget_Score = schedulingPoliciesData.get("Budget_Score");
			String Coverage_Scores_Regular_Hours = schedulingPoliciesData.get("Coverage_Scores_Regular_Hours");
			String Coverage_Scores_Peak_Hours = schedulingPoliciesData.get("Coverage_Scores_Peak_Hours");
			String Employee_Match_Score = schedulingPoliciesData.get("Employee_Match_Score");
			String Compliance_Score = schedulingPoliciesData.get("Compliance_Score");
			String How_to_measure_coverage_relative_to_guidance_budget = schedulingPoliciesData.get("How_to_measure_coverage_relative_to_guidance_budget");
			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			controlsNewUIPage.clickOnControlsConsoleMenu();
			controlsNewUIPage.clickOnControlsSchedulingPolicies();
			boolean isSchedulingPolicies = controlsNewUIPage.isControlsSchedulingPoliciesLoaded();
			SimpleUtils.assertOnFail("Controls Page: Scheduling Policies Section not Loaded.", isSchedulingPolicies, true);
			controlsNewUIPage.clickOnGlobalLocationButton();
			controlsNewUIPage.updateScheduleScore(Budget_Score,Coverage_Scores_Regular_Hours,Coverage_Scores_Peak_Hours,Employee_Match_Score,Compliance_Score,How_to_measure_coverage_relative_to_guidance_budget);
			boolean isScheduleScoreUpdated = controlsNewUIPage.isScheduleScoreUpdated(Budget_Score,Coverage_Scores_Regular_Hours,Coverage_Scores_Peak_Hours,Employee_Match_Score,Compliance_Score,How_to_measure_coverage_relative_to_guidance_budget);
			SimpleUtils.assertOnFail("Schedule score updated  failed", isScheduleScoreUpdated, false);

	}

	
}