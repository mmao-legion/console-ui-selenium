package com.legion.tests.core;

import com.legion.api.abSwitch.ABSwitchAPI;
import com.legion.api.abSwitch.AbSwitches;
import com.legion.api.toggle.ToggleAPI;
import com.legion.api.toggle.Toggles;
import com.legion.pages.*;
import com.legion.pages.OpsPortaPageFactories.ConfigurationPage;
import com.legion.pages.OpsPortaPageFactories.LocationsPage;
import com.legion.pages.OpsPortaPageFactories.UserManagementPage;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
import com.legion.pages.core.OpsPortal.OpsPortalConfigurationPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.core.OpsPortal.LocationsTest;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

import static com.legion.utils.MyThreadLocal.*;


public class ScheduleSeniorityTest extends TestBase {

	private static HashMap<String, String> scheduleWorkRoles = JsonUtil.getPropertiesFromJsonFile("src/test/resources/WorkRoleOptions.json");
	private static HashMap<String, String> propertyCustomizeMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ScheduleCustomizeNewShift.json");
	private static HashMap<String, String> schedulePolicyData = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SchedulingPoliciesData.json");
	private static HashMap<String, String> propertySearchTeamMember = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SearchTeamMember.json");
	private static HashMap<String, Object[][]> kendraScott2TeamMembers = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson("KendraScott2TeamMembers.json");
	private static HashMap<String, Object[][]> cinemarkWkdyTeamMembers = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson("CinemarkWkdyTeamMembers.json");
	private static String opWorkRole = scheduleWorkRoles.get("RETAIL_ASSOCIATE");
	private static String controlWorkRole = scheduleWorkRoles.get("RETAIL_RENTAL_MGMT");
	private static String controlEnterprice = "Vailqacn_Enterprise";
	private static String opEnterprice = "CinemarkWkdy_Enterprise";

	public enum weekCount {
		Zero(0),
		One(1),
		Two(2),
		Three(3),
		Four(4),
		Five(5);
		private final int value;

		weekCount(final int newValue) {
			value = newValue;
		}

		public int getValue() {
			return value;
		}
	}

	public enum filtersIndex {
		Zero(0),
		One(1),
		Two(2),
		Three(3),
		Four(4),
		Five(5);
		private final int value;

		filtersIndex(final int newValue) {
			value = newValue;
		}

		public int getValue() {
			return value;
		}
	}


	public enum dayCount{
		Seven(7);
		private final int value;
		dayCount(final int newValue) {
            value = newValue;
        }
        public int getValue() { return value; }
	}

	public enum schedulePlanningWindow{
		Eight(8);
		private final int value;
		schedulePlanningWindow(final int newValue) {
            value = newValue;
        }
        public int getValue() { return value; }
	}

	public enum sliderShiftCount {
		SliderShiftStartCount(2),
		SliderShiftEndTimeCount(10),
		SliderShiftEndTimeCount2(14),
		SliderShiftEndTimeCount3(40);
		private final int value;
		sliderShiftCount(final int newValue) {
            value = newValue;
        }
        public int getValue() { return value; }
	}

	public enum staffingOption{
		OpenShift("Auto"),
		ManualShift("Manual"),
		AssignTeamMemberShift("Assign Team Member");
		private final String value;
		staffingOption(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
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


	public enum SchedulePageSubTabText {
		Overview("OVERVIEW"),
		Forecast("FORECAST"),
		ProjectedSales("PROJECTED SALES"),
		StaffingGuidance("STAFFING GUIDANCE"),
		Schedule("SCHEDULE"),
		MySchedule("MY SCHEDULE"),
		TeamSchedule("TEAM SCHEDULE"),
		ProjectedTraffic("PROJECTED TRAFFIC");
		private final String value;

		SchedulePageSubTabText(final String newValue) {
			value = newValue;
		}

		public String getValue() {
			return value;
		}
	}

	public enum weekViewType {
		Next("Next"),
		Previous("Previous");
		private final String value;

		weekViewType(final String newValue) {
			value = newValue;
		}

		public String getValue() {
			return value;
		}
	}

	  public enum shiftSliderDroppable{
		  StartPoint("Start"),
		  EndPoint("End");
			private final String value;
			shiftSliderDroppable(final String newValue) {
	            value = newValue;
	        }
	        public String getValue() { return value; }
		}

	  public enum scheduleHoursAndWagesData{
		  scheduledHours("scheduledHours"),
		  budgetedHours("budgetedHours"),
		  otherHours("otherHours"),
		  wagesBudgetedCount("wagesBudgetedCount"),
		  wagesScheduledCount("wagesScheduledCount");
			private final String value;
			scheduleHoursAndWagesData(final String newValue) {
	            value = newValue;
	        }
	        public String getValue() { return value; }
		}

	  public enum scheduleGroupByFilterOptions{
		  groupbyAll("Group by All"),
		  groupbyWorkRole("Group by Work Role"),
		  groupbyTM("Group by TM"),
		  groupbyJobTitle("Group by Job Title"),
		  groupbyLocation("Group by Location"),
		  groupbyDayParts("Group by Day Parts");
			private final String value;
			scheduleGroupByFilterOptions(final String newValue) {
	            value = newValue;
	        }
	        public String getValue() { return value; }
		}

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

    public enum DayOfWeek {
        Mon,
        Tue,
        Wed,
        Thu,
        Fri,
        Sat,
        Sun;
    }

	@Override
	@BeforeMethod()
	public void firstTest(Method testMethod, Object[] params) {
		try {
			this.createDriver((String) params[0], "69", "Window");
			visitPage(testMethod);
			loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Cosimo")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the configurable of seniority toggle")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifySeniorityConfigsOnSchedulingPolicyPageAsInternalAdmin(String username, String password, String browser, String location)
			throws Exception {
		try {
			//Go to the Scheduling Policy page
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
			locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
			SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
			locationsPage.clickOnLocationsTab();
			locationsPage.goToSubLocationsInLocationsPage();
			locationsPage.searchLocation(location);
			SimpleUtils.assertOnFail("Locations not searched out Successfully!", locationsPage.verifyUpdateLocationResult(location), false);
			locationsPage.clickOnLocationInLocationResult(location);
			locationsPage.clickOnConfigurationTabOfLocation();
			HashMap<String, String> templateTypeAndName = locationsPage.getTemplateTypeAndNameFromLocation();
			ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
			configurationPage.goToConfigurationPage();
			configurationPage.clickOnConfigurationCrad("Scheduling Policies");
			configurationPage.clickOnSpecifyTemplateName(templateTypeAndName.get("Scheduling Policies"), "edit");

			//Edit the seniority toggle as Yes, save the change
			configurationPage.clickOnEditButtonOnTemplateDetailsPage();
			Thread.sleep(3000);
			controlsNewUIPage.isSenioritySectionLoaded();
			controlsNewUIPage.updateSeniorityToggle("Yes");
			configurationPage.publishNowTheTemplate();
			configurationPage.clickOnSpecifyTemplateName(templateTypeAndName.get("Scheduling Policies"), "edit");
			Thread.sleep(10000);
			String activeBtnLabel = controlsNewUIPage.getSeniorityToggleActiveBtnLabel();
			SimpleUtils.assertOnFail("The selected button is not expected!", activeBtnLabel.equalsIgnoreCase("Yes"),false);

			//Edit the seniority toggle as No, save the change
			configurationPage.clickOnEditButtonOnTemplateDetailsPage();
			Thread.sleep(3000);
			controlsNewUIPage.isSenioritySectionLoaded();
			controlsNewUIPage.updateSeniorityToggle("No");
			configurationPage.publishNowTheTemplate();
			configurationPage.clickOnSpecifyTemplateName(templateTypeAndName.get("Scheduling Policies"), "edit");
			Thread.sleep(10000);
			activeBtnLabel = controlsNewUIPage.getSeniorityToggleActiveBtnLabel();
			SimpleUtils.assertOnFail("The selected button is not expected!", activeBtnLabel.equalsIgnoreCase("No"),false);

		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Cosimo")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the configurable of seniority sort")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifySeniorityOrderOnSchedulingPolicyPageAsInternalAdmin(String username, String password, String browser, String location)
			throws Exception {
		try {
			//Go to the Scheduling Policy page
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
			locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
			SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
			locationsPage.clickOnLocationsTab();
			locationsPage.goToSubLocationsInLocationsPage();
			locationsPage.searchLocation(location);
			SimpleUtils.assertOnFail("Locations not searched out Successfully!", locationsPage.verifyUpdateLocationResult(location), false);
			locationsPage.clickOnLocationInLocationResult(location);
			locationsPage.clickOnConfigurationTabOfLocation();
			HashMap<String, String> templateTypeAndName = locationsPage.getTemplateTypeAndNameFromLocation();
			ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
			configurationPage.goToConfigurationPage();
			configurationPage.clickOnConfigurationCrad("Scheduling Policies");
			configurationPage.clickOnSpecifyTemplateName(templateTypeAndName.get("Scheduling Policies"), "edit");

			//Edit the seniority sort as Ascending, save the change
			configurationPage.clickOnEditButtonOnTemplateDetailsPage();
			Thread.sleep(3000);
			controlsNewUIPage.selectSortOfSeniority("Ascending");
			configurationPage.publishNowTheTemplate();
			configurationPage.clickOnSpecifyTemplateName(templateTypeAndName.get("Scheduling Policies"), "edit");
			Thread.sleep(10000);
			String senioritySort = controlsNewUIPage.getSenioritySort();
			SimpleUtils.assertOnFail("The seniority sort is not expected!",senioritySort.equalsIgnoreCase("Ascending"),false);

			//Edit the seniority sort as Descending, save the change
			configurationPage.clickOnEditButtonOnTemplateDetailsPage();
			Thread.sleep(3000);
			controlsNewUIPage.selectSortOfSeniority("Descending");
			configurationPage.publishNowTheTemplate();
			configurationPage.clickOnSpecifyTemplateName(templateTypeAndName.get("Scheduling Policies"), "edit");
			Thread.sleep(10000);
			senioritySort = controlsNewUIPage.getSenioritySort();
			SimpleUtils.assertOnFail("The seniority sort is not expected!",senioritySort.equalsIgnoreCase("Descending"),false);

		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(), false);
		}
	}
}