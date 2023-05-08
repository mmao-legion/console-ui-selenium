package com.legion.tests.core;

import com.legion.api.toggle.ToggleAPI;
import com.legion.api.toggle.Toggles;
import com.legion.pages.*;
import com.legion.pages.OpsPortaPageFactories.ConfigurationPage;
import com.legion.pages.OpsPortaPageFactories.LocationsPage;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.core.OpsPortal.LocationsTest;
import com.legion.tests.core.ScheduleTestKendraScott2;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class ReliefPoolTest extends TestBase {

	private static HashMap<String, String> scheduleWorkRoles = JsonUtil.getPropertiesFromJsonFile("src/test/resources/WorkRoleOptions.json");
	private static String opWorkRole = scheduleWorkRoles.get("RETAIL_ASSOCIATE");
	private static String controlWorkRole = scheduleWorkRoles.get("RETAIL_RENTAL_MGMT");
	private static String controlEnterprice = "Vailqacn_Enterprise";
	private static String opEnterprice = "CinemarkWkdy_Enterprise";

	@Override
	@BeforeMethod()
	public void firstTest(Method testMethod, Object[] params) {
		try {
			this.createDriver((String) params[0], "69", "Window");
			visitPage(testMethod);
			ToggleAPI.updateToggle(Toggles.ReliefPool.getValue(), getUserNameNPwdForCallingAPI().get(0), getUserNameNPwdForCallingAPI().get(1), true);
			loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Cosimo")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the configurable of auto group toggle")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyAutoGroupConfigsAsInternalAdmin(String username, String password, String browser, String location)
			throws Exception {
		try {
			//Go to the Scheduling Collaboration page
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
			String templateName = templateTypeAndName.get("Schedule Collaboration");
			ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
			configurationPage.goToConfigurationPage();
			configurationPage.clickOnConfigurationCrad("Schedule Collaboration");
			configurationPage.clickOnSpecifyTemplateName(templateName, "edit");

			//Open the auto group toggle, save the change
			configurationPage.clickOnEditButtonOnTemplateDetailsPage();
			Thread.sleep(3000);
			controlsNewUIPage.isAutoGroupSectionLoaded();
			controlsNewUIPage.updateAutoGroupToggle("Yes");
			configurationPage.publishNowTheTemplate();
			configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
			Thread.sleep(10000);
			String activeBtnLabel1 = controlsNewUIPage.getAutoGroupToggleActiveBtnLabel();
			SimpleUtils.assertOnFail("The selected button is not expected!", activeBtnLabel1.equalsIgnoreCase("Yes"),false);

			//Close the auto group toggle, save the change
			configurationPage.clickOnEditButtonOnTemplateDetailsPage();
			Thread.sleep(3000);
			controlsNewUIPage.isAutoGroupSectionLoaded();
			controlsNewUIPage.updateAutoGroupToggle("No");
			configurationPage.publishNowTheTemplate();
			configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
			Thread.sleep(10000);
			String activeBtnLabel2 = controlsNewUIPage.getAutoGroupToggleActiveBtnLabel();
			SimpleUtils.assertOnFail("The selected button is not expected!", activeBtnLabel2.equalsIgnoreCase("No"),false);

		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Cosimo")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the configurable rules of open shift group")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyOpenShiftGroupRulesConfigsAsInternalAdmin(String username, String password, String browser, String location)
			throws Exception {
		try {
			//Go to the Scheduling Collaboration page
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
			String templateType = "Schedule Collaboration";
			String templateName = templateTypeAndName.get(templateType);
			ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
			configurationPage.goToConfigurationPage();
			configurationPage.clickOnConfigurationCrad(templateType);
			configurationPage.clickOnSpecifyTemplateName(templateName, "edit");

			//Select different rules for open shift group, save the change
			configurationPage.clickOnEditButtonOnTemplateDetailsPage();
			Thread.sleep(3000);
			controlsNewUIPage.isAutoGroupSectionLoaded();
			controlsNewUIPage.updateAutoGroupToggle("Yes");
			ArrayList<String> selectOptions = new ArrayList<>();
			selectOptions.add("ShiftPatternAssignment()");
			controlsNewUIPage.selectOpenShiftGroupRule(selectOptions);
			configurationPage.publishNowTheTemplate();

			configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
			configurationPage.clickOnEditButtonOnTemplateDetailsPage();
			Thread.sleep(3000);
			selectOptions.add("ScheduleConflictCheck()");
			controlsNewUIPage.selectOpenShiftGroupRule(selectOptions);
			configurationPage.publishNowTheTemplate();

			configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
			configurationPage.clickOnEditButtonOnTemplateDetailsPage();
			Thread.sleep(3000);
			selectOptions.add("AccrualBalanceCheck()");
			controlsNewUIPage.selectOpenShiftGroupRule(selectOptions);
			configurationPage.publishNowTheTemplate();

			configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
			configurationPage.clickOnEditButtonOnTemplateDetailsPage();
			Thread.sleep(3000);
			selectOptions.add("AssignmentRuleCheck()");
			controlsNewUIPage.selectOpenShiftGroupRule(selectOptions);
			configurationPage.publishNowTheTemplate();

			configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
			configurationPage.clickOnEditButtonOnTemplateDetailsPage();
			Thread.sleep(3000);
			selectOptions.add("TimeOffCheck()");
			controlsNewUIPage.selectOpenShiftGroupRule(selectOptions);
			configurationPage.publishNowTheTemplate();

			//Select multiple rules, check the text in the input box
			configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
			String text = "5 What criteria are used when evaluating employees for open shift offers? Selected";
			controlsNewUIPage.verifyTextInOpenShiftGroupRuleInputBox(text);

			//Group rules section disappears after close auto group toggle
			configurationPage.clickOnEditButtonOnTemplateDetailsPage();
			Thread.sleep(3000);
			controlsNewUIPage.isAutoGroupSectionLoaded();
			controlsNewUIPage.updateAutoGroupToggle("No");
			SimpleUtils.assertOnFail("Group rules section still displays!", controlsNewUIPage.isOpenShiftGroupRuleSectionLoaded() == false,false);
			configurationPage.publishNowTheTemplate();

			configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
			configurationPage.clickOnEditButtonOnTemplateDetailsPage();
			Thread.sleep(3000);
			SimpleUtils.assertOnFail("Group rules section still displays!", controlsNewUIPage.isOpenShiftGroupRuleSectionLoaded() == false,false);

			controlsNewUIPage.isAutoGroupSectionLoaded();
			controlsNewUIPage.updateAutoGroupToggle("Yes");
			SimpleUtils.assertOnFail("Group rules section not displays!", controlsNewUIPage.isOpenShiftGroupRuleSectionLoaded() == true,false);
			configurationPage.publishNowTheTemplate();

		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Cosimo")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the configurable advanced hours of auto approval")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyConfigOfAdvancedHoursAsInternalAdmin(String username, String password, String browser, String location)
			throws Exception {
		try {
			//Go to the Scheduling Collaboration page
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
			LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
			locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
			SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
			locationsPage.clickOnLocationsTab();
			locationsPage.goToSubLocationsInLocationsPage();
			locationsPage.searchLocation(location);
			SimpleUtils.assertOnFail("Locations not searched out Successfully!", locationsPage.verifyUpdateLocationResult(location), false);
			locationsPage.clickOnLocationInLocationResult(location);
			locationsPage.clickOnConfigurationTabOfLocation();
			HashMap<String, String> templateTypeAndName = locationsPage.getTemplateTypeAndNameFromLocation();
			String templateType = "Schedule Collaboration";
			String templateName = templateTypeAndName.get(templateType);
			configurationPage.goToConfigurationPage();
			configurationPage.clickOnConfigurationCrad(templateType);
			configurationPage.clickOnSpecifyTemplateName(templateName, "edit");

			//Input advanced hours, save the change
			configurationPage.clickOnEditButtonOnTemplateDetailsPage();
			Thread.sleep(3000);
			controlsNewUIPage.isAutoApprovalSectionLoaded();
			String inputAdvancedHours = "48";
			controlsNewUIPage.setAutoApprovalAdvancedHours(inputAdvancedHours);
			configurationPage.publishNowTheTemplate();
			configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
			Thread.sleep(10000);
			String advancedHours = controlsNewUIPage.getAutoApprovalAdvancedHours();
			SimpleUtils.assertOnFail("The advanced hours value is not expected!", advancedHours.equalsIgnoreCase(inputAdvancedHours),false);

			//Advance hours section disappears after close auto group toggle
			configurationPage.clickOnEditButtonOnTemplateDetailsPage();
			Thread.sleep(3000);
			controlsNewUIPage.isAutoGroupSectionLoaded();
			controlsNewUIPage.updateAutoGroupToggle("No");
			SimpleUtils.assertOnFail("Advance hours section still displays!", controlsNewUIPage.isAutoApprovalSectionLoaded() == false,false);
			configurationPage.publishNowTheTemplate();

			configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
			configurationPage.clickOnEditButtonOnTemplateDetailsPage();
			Thread.sleep(3000);
			controlsNewUIPage.isAutoGroupSectionLoaded();
			controlsNewUIPage.updateAutoGroupToggle("Yes");
			SimpleUtils.assertOnFail("Advance hours section not displays!", controlsNewUIPage.isAutoApprovalSectionLoaded() == true,false);
			configurationPage.publishNowTheTemplate();

		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Cosimo")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the Group by Pattern filter in the schedule")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyGroupByPatternAsInternalAdmin(String username, String password, String browser, String location)
			throws Exception {
		try {
			//Go to the Scheduling page
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			// Go to Schedule page, create a schedule
			goToSchedulePageScheduleTab();
			scheduleCommonPage.navigateToNextWeek();
			boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
			if(isActiveWeekGenerated){
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			Thread.sleep(5000);
			createSchedulePage.createScheduleForNonDGFlowNewUI();

			//Check Group by Pattern filter in the group by list
			ArrayList<String> shiftTitles = new ArrayList<>();
			shiftTitles.add("MASTER");
			scheduleCommonPage.clickOnWeekView();
			scheduleMainPage.clickOnSuggestedButton();
			scheduleMainPage.validateGroupBySelectorSchedulePage(false);
			scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
			scheduleShiftTablePage.verifyGroupCanbeCollapsedNExpanded();
			scheduleMainPage.isShiftTitleExist(shiftTitles);

			scheduleMainPage.clickOnManagerButton();
			scheduleMainPage.validateGroupBySelectorSchedulePage(false);
			scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
			scheduleShiftTablePage.verifyGroupCanbeCollapsedNExpanded();
			scheduleMainPage.isShiftTitleExist(shiftTitles);

			scheduleCommonPage.clickOnDayView();
			scheduleMainPage.clickOnSuggestedButton();
			scheduleMainPage.validateGroupBySelectorSchedulePage(false);
			scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
			scheduleMainPage.isShiftTitleExist(shiftTitles);

			scheduleMainPage.clickOnManagerButton();
			scheduleMainPage.validateGroupBySelectorSchedulePage(false);
			scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
			scheduleMainPage.isShiftTitleExist(shiftTitles);

			scheduleCommonPage.clickOnMultiWeekView();
			scheduleMainPage.validateGroupBySelectorSchedulePage(false);
			scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());

		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Cosimo")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the function of open shift group")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyFunctionOfOpenShiftGroupAsInternalAdmin(String username, String password, String browser, String location)
			throws Exception {
		try {
			//Go to the Scheduling page
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			// Go to Schedule page, create a schedule
			goToSchedulePageScheduleTab();
			scheduleCommonPage.navigateToNextWeek();
//			boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
//			if(isActiveWeekGenerated){
//				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
//			}
//			Thread.sleep(5000);
//			createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange("06:00AM", "06:00AM");
//			createSchedulePage.publishActiveSchedule();

			//TM can receive open shift group
//			scheduleCommonPage.clickOnWeekView();
//			scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
//			scheduleMainPage.clickOnOpenSearchBoxButton();
//			scheduleMainPage.searchShiftOnSchedulePage("Master");
//			scheduleShiftTablePage.clickProfileIconOfShiftByIndex(0);
//			shiftOperatePage.clickOnOfferTMOption();
//			newShiftPage.searchTeamMemberByName("Sebastian");
//			newShiftPage.clickOnOfferOrAssignBtn();

			//Login as TM, check the open shift group
			MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
			scheduleCommonPage.clickOnDayView();
			ArrayList<String> expectedWorkDays = mySchedulePage.getAllWeekDays();
//			System.out.println("=========" + workDays + "============");
//			ArrayList<String> expectedWorkDays = new ArrayList<>();
			LoginPage loginPage = pageFactory.createConsoleLoginPage();
			loginPage.logOut();
			loginAsDifferentRole(AccessRoles.TeamMember.getValue());
			SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.MySchedule.getValue());
			Thread.sleep(3000);
			scheduleCommonPage.navigateToNextWeek();
			smartCardPage.clickLinkOnSmartCardByName("View Shifts");
//			SimpleUtils.assertOnFail("Didn't get open shift group offer!", scheduleShiftTablePage.getShiftsCount() == 14, false);
//			SimpleUtils.assertOnFail("The shift hours were incorrect!", scheduleShiftTablePage.getShiftTextByIndex(0).replaceAll(" ", "").contains("6:00am-4:00pm"), false );

			int shiftCount = 7;
			String shiftWorkRole1 = "Master";
			String shiftWorkRole2 = "Deckhand";
			String locationName = "SeaspanRegularLocation2";
			String shiftTimePeriod1 = "06:00-16:00";
			String shiftTimePeriod2 = "07:00-19:00";
			List<String> expectedRequest = Arrays.asList("View Group Offer(" + shiftCount + ")");
//			ArrayList<String> weekDays = new ArrayList<String>(Arrays.asList("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"));
//			mySchedulePage.checkOpenShiftGroup(shiftCount, shiftTimePeriod1, shiftWorkRole1, weekDays, expectedWorkDays,locationName);
//			mySchedulePage.verifyClaimShiftOfferNBtnsLoaded();
//			mySchedulePage.verifyClickAgreeBtnOnClaimShiftOffer();

			//Decline the open shift group offer
//			mySchedulePage.checkOpenShiftGroup(shiftCount, shiftTimePeriod2, shiftWorkRole2, weekDays, expectedWorkDays,locationName);
//			mySchedulePage.verifyClaimShiftOfferNBtnsLoaded();
//			mySchedulePage.verifyClickAgreeBtnOnClaimShiftOffer();
//			mySchedulePage.clickOnDeclineButton();

			//Cancel claim the open shift group offer
			mySchedulePage.cancelClaimOpenShiftGroupRequest(expectedRequest,shiftWorkRole1);


		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

}