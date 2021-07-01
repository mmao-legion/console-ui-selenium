package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.core.OpsPortalLocationsPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;

public class ApproveActivitiesByHomeOrNonHomeManager extends TestBase {


    private static HashMap<String, String> propertyCustomizeMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ScheduleCustomizeNewShift.json");
    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{
        try {
            this.createDriver((String) params[0], "83", "Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
//    @Enterprise(name = "Vailqacn_Enterprise")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the manager need to approve the claimed open shift when enable the claim open shift in home location setting on OP env")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyManagerNeedToApproveShiftWhenEnableShiftInHomeLocationSettingOnOPAsTeamLead (String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String teamMemberName = profileNewUIPage.getNickNameFromProfile();
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());

//            // 1. Go to OP-> Schedule Collaboration -> Open Shifts -> enable -- Is approval required by Manager when an employee claims an Open Shift in a home location?
//            OpsPortalLocationsPage opsPortalLocationsPage = (OpsPortalLocationsPage) pageFactory.createOpsPortalLocationsPage();
//            opsPortalLocationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
//            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
//            configurationPage.goToConfigurationPage();
//            configurationPage.goToTemplateDetailsPage("Schedule Collaboration");
//            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
//            configurationPage.enableOrDisableApproveShiftInHomeLocationSetting("Yes");
//            configurationPage.publishNowTheTemplate();
//            switchToConsoleWindow();

            // 2.admin create one manual open shift and assign to specific TM
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            //to generate schedule  if current week is not generated
            schedulePage.navigateToNextWeek();
            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView("Unassigned");
            schedulePage.deleteTMShiftInWeekView(teamMemberName);
            schedulePage.saveSchedule();
            String workRole = schedulePage.getRandomWorkRole();

            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.moveSliderAtCertainPoint("2pm", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("11am", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.selectWorkRole(workRole);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(teamMemberName);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();
            loginPage.logOut();

            // 3.Login with the TM to claim the shift
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            dashboardPage.goToTodayForNewUI();
            schedulePage.navigateToNextWeek();
            schedulePage.isSchedule();
            String cardName = "WANT MORE HOURS?";
            SimpleUtils.assertOnFail("Smart Card: " + cardName + " not loaded Successfully!", schedulePage.isSpecificSmartCardLoaded(cardName), false);
            String linkName = "View Shifts";
            schedulePage.clickLinkOnSmartCardByName(linkName);
            SimpleUtils.assertOnFail("Open shifts not loaed Successfully!", schedulePage.areShiftsPresent(), false);
            List<String> claimShift = new ArrayList<>(Arrays.asList("Claim Shift"));
            schedulePage.selectOneShiftIsClaimShift(claimShift);
            schedulePage.clickTheShiftRequestByName(claimShift.get(0));
            schedulePage.verifyClickAgreeBtnOnClaimShiftOffer();

            loginPage.logOut();

            // 4.Login with SM to check activity
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftOffer.getValue(), ActivityTest.indexOfActivityType.ShiftOffer.name());
            activityPage.verifyActivityOfShiftOffer(teamMemberName,location);
            activityPage.approveOrRejectShiftOfferRequestOnActivity(teamMemberName, ActivityTest.approveRejectAction.Approve.getValue());
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
//    @Enterprise(name = "Vailqacn_Enterprise")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the manager doesn't need to approve the claimed open shift when disable the claim open shift in home location setting on OP env")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyManagerDoesNotNeedToApproveShiftWhenDisableShiftInHomeLocationSettingOnOPAsTeamLead (String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String teamMemberName = profileNewUIPage.getNickNameFromProfile();
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());

            // 1. Go to OP-> Schedule Collaboration -> Open Shifts -> enable -- Is approval required by Manager when an employee claims an Open Shift in a home location?
            OpsPortalLocationsPage opsPortalLocationsPage = (OpsPortalLocationsPage) pageFactory.createOpsPortalLocationsPage();
            opsPortalLocationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.goToTemplateDetailsPage("Schedule Collaboration");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.enableOrDisableApproveShiftInHomeLocationSetting("No");
            configurationPage.publishNowTheTemplate();
            switchToConsoleWindow();

            // 2.admin create one manual open shift and assign to specific TM
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            //to generate schedule  if current week is not generated
            schedulePage.navigateToNextWeek();
            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView("Unassigned");
            schedulePage.deleteTMShiftInWeekView(teamMemberName);
            schedulePage.saveSchedule();
            String workRole = schedulePage.getRandomWorkRole();

            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.moveSliderAtCertainPoint("2pm", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("11am", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.selectWorkRole(workRole);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(teamMemberName);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();
            loginPage.logOut();

            // 3.Login with the TM to claim the shift
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            dashboardPage.goToTodayForNewUI();
            schedulePage.navigateToNextWeek();
            schedulePage.isSchedule();
            String cardName = "WANT MORE HOURS?";
            SimpleUtils.assertOnFail("Smart Card: " + cardName + " not loaded Successfully!", schedulePage.isSpecificSmartCardLoaded(cardName), false);
            String linkName = "View Shifts";
            schedulePage.clickLinkOnSmartCardByName(linkName);
            SimpleUtils.assertOnFail("Open shifts not loaed Successfully!", schedulePage.areShiftsPresent(), false);
            List<String> claimShift = new ArrayList<>(Arrays.asList("Claim Shift"));
            schedulePage.selectOneShiftIsClaimShift(claimShift);
            schedulePage.clickTheShiftRequestByName(claimShift.get(0));
            schedulePage.verifyClickAgreeBtnOnClaimShiftOfferWhenDontNeedApproval();
            loginPage.logOut();

            // 4.Login with SM to check activity
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftOffer.getValue(), ActivityTest.indexOfActivityType.ShiftOffer.name());
            activityPage.verifyActivityOfShiftOffer(teamMemberName,location);
            activityPage.approveOrRejectShiftOfferRequestOnActivity(teamMemberName, ActivityTest.approveRejectAction.Approve.getValue());
            SimpleUtils.assertOnFail("Shouldn't load Approval and Reject buttons!", !activityPage.isApproveRejectBtnsLoaded(0), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
//    @Enterprise(name = "Vailqacn_Enterprise")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the manager and non-home manager need to approve the claimed open shift when enable the claim open shift in non-home location setting on OP env")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyNonHomeManagerNeedToApproveShiftWhenEnableShiftInNonHomeLocationSettingOnOPAsTeamLead (String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String teamMemberName = profileNewUIPage.getNickNameFromProfile();
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            String fileName = "UsersCredentials.json";
            fileName = MyThreadLocal.getEnterprise() + fileName;
            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
            Object[][] credentials = userCredentials.get(AccessRoles.StoreManagerOtherLocation1.getValue());

            // 1. Go to OP-> Schedule Collaboration -> Open Shifts -> enable -- Is approval required by Manager when an employee claims an Open Shift in a home location?
            OpsPortalLocationsPage opsPortalLocationsPage = (OpsPortalLocationsPage) pageFactory.createOpsPortalLocationsPage();
            opsPortalLocationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.goToTemplateDetailsPage("Schedule Collaboration");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.enableOrDisableApproveShiftInHomeLocationSetting("Yes");
            configurationPage.publishNowTheTemplate();
            switchToConsoleWindow();

            // 2.admin create one manual open shift and assign to specific TM
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            //Go to TM's home location, to generate and publish schedule if current week is not generated
            schedulePage.navigateToNextWeek();
            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            schedulePage.deleteTMShiftInWeekView("Unassigned");
            schedulePage.deleteTMShiftInWeekView(teamMemberName);
            schedulePage.publishActiveSchedule();

            //Go to TM's non-home location, to generate and publish schedule if current week is not generated
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(String.valueOf(credentials[0][2]));
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            //Go to TM's home location, to generate and publish schedule if current week is not generated
            schedulePage.navigateToNextWeek();
            isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView("Unassigned");
            schedulePage.deleteTMShiftInWeekView(teamMemberName);
            schedulePage.saveSchedule();
            String workRole = schedulePage.getRandomWorkRole();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            schedulePage.selectSpecificWorkDay(1);
            schedulePage.moveSliderAtCertainPoint("2pm", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("11am", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.selectWorkRole(workRole);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(teamMemberName);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();
            loginPage.logOut();

            // 3.Login with the TM to claim the shift
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            dashboardPage.goToTodayForNewUI();
            schedulePage.navigateToNextWeek();
            schedulePage.isSchedule();
            String cardName = "WANT MORE HOURS?";
            SimpleUtils.assertOnFail("Smart Card: " + cardName + " not loaded Successfully!", schedulePage.isSpecificSmartCardLoaded(cardName), false);
            String linkName = "View Shifts";
            schedulePage.clickLinkOnSmartCardByName(linkName);
            SimpleUtils.assertOnFail("Open shifts not loaed Successfully!", schedulePage.areShiftsPresent(), false);
            List<String> claimShift = new ArrayList<>(Arrays.asList("Claim Shift"));
            schedulePage.selectOneShiftIsClaimShift(claimShift);
            schedulePage.clickTheShiftRequestByName(claimShift.get(0));
            schedulePage.verifyClickAgreeBtnOnClaimShiftOffer();
            ;
            SimpleUtils.assertOnFail("", schedulePage.getAvailableShiftsInDayView().size()==0, false);
            loginPage.logOut();

            // 4.Login with SM to check activity
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftOffer.getValue(), ActivityTest.indexOfActivityType.ShiftOffer.name());
            activityPage.verifyActivityOfShiftOffer(teamMemberName,location);
            activityPage.approveOrRejectShiftOfferRequestOnActivity(teamMemberName, ActivityTest.approveRejectAction.Approve.getValue());

            //Go to schedule and check the shift
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            schedulePage.navigateToNextWeek();

            //Check the shift is not assigned TM
            List<WebElement> shiftsOfTuesday = schedulePage.getOneDayShiftByName(0, teamMemberName);
            SimpleUtils.assertOnFail("The shift should not been assigned! ",shiftsOfTuesday.size()==0, false);

            loginPage.logOut();

            // 4.Login with SM to check activity
            loginAsDifferentRole(AccessRoles.StoreManagerOtherLocation1.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftOffer.getValue(), ActivityTest.indexOfActivityType.ShiftOffer.name());
            activityPage.verifyActivityOfShiftOffer(teamMemberName,location);
            activityPage.approveOrRejectShiftOfferRequestOnActivity(teamMemberName, ActivityTest.approveRejectAction.Approve.getValue());

            //Go to schedule and check the shift
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            schedulePage.navigateToNextWeek();

            //Check the shift is assigned TM
            shiftsOfTuesday = schedulePage.getOneDayShiftByName(0, teamMemberName);
            SimpleUtils.assertOnFail("The shift should not been assigned! ",shiftsOfTuesday.size()> 0, false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
//    @Enterprise(name = "Vailqacn_Enterprise")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the managers doesn't need to approve the claimed open shift when disable the claim open shift in non-home location setting on OP env")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyNonHomeManagerNotNeedToApproveShiftWhenDisableShiftInNonHomeLocationSettingOnOPAsTeamLead (String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String teamMemberName = profileNewUIPage.getNickNameFromProfile();
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            String fileName = "UsersCredentials.json";
            fileName = MyThreadLocal.getEnterprise() + fileName;
            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
            Object[][] credentials = userCredentials.get(AccessRoles.StoreManagerOtherLocation1.getValue());

            // 1. Go to OP-> Schedule Collaboration -> Open Shifts -> enable -- Is approval required by Manager when an employee claims an Open Shift in a home location?
            OpsPortalLocationsPage opsPortalLocationsPage = (OpsPortalLocationsPage) pageFactory.createOpsPortalLocationsPage();
            opsPortalLocationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.goToTemplateDetailsPage("Schedule Collaboration");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.enableOrDisableApproveShiftInHomeLocationSetting("No");
            configurationPage.publishNowTheTemplate();
            switchToConsoleWindow();

            // 2.admin create one manual open shift and assign to specific TM
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            //Go to TM's home location, to generate and publish schedule if current week is not generated
            schedulePage.navigateToNextWeek();
            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            schedulePage.deleteTMShiftInWeekView("Unassigned");
            schedulePage.deleteTMShiftInWeekView(teamMemberName);
            schedulePage.publishActiveSchedule();

            //Go to TM's non-home location, to generate and publish schedule if current week is not generated
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(String.valueOf(credentials[0][2]));
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            //Go to TM's home location, to generate and publish schedule if current week is not generated
            schedulePage.navigateToNextWeek();
            isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView("Unassigned");
            schedulePage.deleteTMShiftInWeekView(teamMemberName);
            schedulePage.saveSchedule();
            String workRole = schedulePage.getRandomWorkRole();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            schedulePage.selectSpecificWorkDay(1);
            schedulePage.moveSliderAtCertainPoint("2pm", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("11am", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.selectWorkRole(workRole);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(teamMemberName);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();
            loginPage.logOut();

            // 3.Login with the TM to claim the shift
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            dashboardPage.goToTodayForNewUI();
            schedulePage.navigateToNextWeek();
            schedulePage.isSchedule();
            String cardName = "WANT MORE HOURS?";
            SimpleUtils.assertOnFail("Smart Card: " + cardName + " not loaded Successfully!", schedulePage.isSpecificSmartCardLoaded(cardName), false);
            String linkName = "View Shifts";
            schedulePage.clickLinkOnSmartCardByName(linkName);
            SimpleUtils.assertOnFail("Open shifts not loaed Successfully!", schedulePage.areShiftsPresent(), false);
            List<String> claimShift = new ArrayList<>(Arrays.asList("Claim Shift"));
            schedulePage.selectOneShiftIsClaimShift(claimShift);
            schedulePage.clickTheShiftRequestByName(claimShift.get(0));
            schedulePage.verifyClickAgreeBtnOnClaimShiftOffer();
            ;
            SimpleUtils.assertOnFail("", schedulePage.getAvailableShiftsInDayView().size()==0, false);
            loginPage.logOut();

            // 4.Login with SM to check activity
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftOffer.getValue(), ActivityTest.indexOfActivityType.ShiftOffer.name());
            activityPage.verifyActivityOfShiftOffer(teamMemberName,location);
            activityPage.approveOrRejectShiftOfferRequestOnActivity(teamMemberName, ActivityTest.approveRejectAction.Approve.getValue());

            //Go to schedule and check the shift
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            schedulePage.navigateToNextWeek();

            //Check the shift is not assigned TM
            List<WebElement> shiftsOfTuesday = schedulePage.getOneDayShiftByName(0, teamMemberName);
            SimpleUtils.assertOnFail("The shift should not been assigned! ",shiftsOfTuesday.size()==0, false);

            loginPage.logOut();

            // 4.Login with SM to check activity
            loginAsDifferentRole(AccessRoles.StoreManagerOtherLocation1.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftOffer.getValue(), ActivityTest.indexOfActivityType.ShiftOffer.name());
            activityPage.verifyActivityOfShiftOffer(teamMemberName,location);
            activityPage.approveOrRejectShiftOfferRequestOnActivity(teamMemberName, ActivityTest.approveRejectAction.Approve.getValue());

            //Go to schedule and check the shift
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            schedulePage.navigateToNextWeek();

            //Check the shift is assigned TM
            shiftsOfTuesday = schedulePage.getOneDayShiftByName(0, teamMemberName);
            SimpleUtils.assertOnFail("The shift should not been assigned! ",shiftsOfTuesday.size()> 0, false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
