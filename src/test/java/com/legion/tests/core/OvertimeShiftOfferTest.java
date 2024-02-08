package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.OpsPortaPageFactories.ConfigurationPage;
import com.legion.pages.OpsPortaPageFactories.LocationsPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.core.OpsPortal.LocationsTest;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.Constants;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;

public class OvertimeShiftOfferTest extends TestBase {
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

    @Automated(automated = "Automated")
    @Owner(owner = "Ting")
    @Enterprise(name = "")
    @TestName(description = "Should be able to claim the over time shift offer by TM")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAllowClaimOvertimeShiftOfferAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();

            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();

            //Enable employee claim overtime open shift config
//            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
//            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
//            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
//            locationsPage.clickOnLocationsTab();
//            locationsPage.goToSubLocationsInLocationsPage();
//            locationsPage.searchLocation(location);
//            ;
//            SimpleUtils.assertOnFail("Locations not searched out Successfully!", locationsPage.verifyUpdateLocationResult(location), false);
//            locationsPage.clickOnLocationInLocationResult(location);
//            locationsPage.clickOnConfigurationTabOfLocation();
//            HashMap<String, String> templateTypeAndName = locationsPage.getTemplateTypeAndNameFromLocation();
//            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
//            configurationPage.goToConfigurationPage();
//            configurationPage.clickOnConfigurationCrad("Schedule Collaboration");
//            configurationPage.clickOnSpecifyTemplateName(templateTypeAndName.get("Schedule Collaboration"), "edit");
//            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
//            Thread.sleep(3000);
//            controlsNewUIPage.allowEmployeesClaimOvertimeShiftOffer();
//            configurationPage.publishNowTheTemplate();
//            switchToConsoleWindow();
//            refreshCachesAfterChangeTemplate();

            // Start to check and generate target schedule
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            // Modify corresponding work role by enterprise
            String workRoleOfTM = "Retail Associate";
            String enterprise = System.getProperty("enterprise").toLowerCase();

            // Delete open shifts.
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
            scheduleMainPage.selectShiftTypeFilterByText("Open");
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("");
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(firstNameOfTM);

            // To handle the assignment failures when delete TM's shifts and reassign
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            int shiftsCountBefore = 0;

            if (enterprise.equalsIgnoreCase("vailqacn")) {
                // Create and assign shift to consume the available shift hours for the TM
                newShiftPage.clickOnDayViewAddNewShiftButton();
                newShiftPage.customizeNewShiftPage();
                newShiftPage.clearAllSelectedDays();
                newShiftPage.selectDaysByIndex(0, 1, 2);
                newShiftPage.moveSliderAtCertainPoint("9am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
                newShiftPage.moveSliderAtCertainPoint("8pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
                newShiftPage.selectWorkRole(workRoleOfTM);
                newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
                newShiftPage.clickOnCreateOrNextBtn();
                newShiftPage.searchTeamMemberByName(firstNameOfTM);
                newShiftPage.clickOnCreateOrNextBtn();
                scheduleMainPage.saveSchedule();
                shiftsCountBefore = shiftOperatePage.countShiftsByUserName(firstNameOfTM);

                //create an overtime shift
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                newShiftPage.clickOnDayViewAddNewShiftButton();
                newShiftPage.customizeNewShiftPage();
                newShiftPage.clearAllSelectedDays();
                newShiftPage.selectMultipleOrSpecificWorkDay(4, true);
                newShiftPage.moveSliderAtCertainPoint("9am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
                newShiftPage.moveSliderAtCertainPoint("8pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
                newShiftPage.selectWorkRole(workRoleOfTM);
                newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
                newShiftPage.clickOnCreateOrNextBtn();
                scheduleMainPage.saveSchedule();
            }

            if (enterprise.equalsIgnoreCase("cinemark-wkdy")) {
                workRoleOfTM = "Team Member Corporate-Theatre";

                // Create and assign shift to consume the available shift hours for the TM
                newShiftPage.clickOnDayViewAddNewShiftButton();
                newShiftPage.customizeNewShiftPage();
                newShiftPage.clearAllSelectedDays();
                newShiftPage.selectMultipleOrSpecificWorkDay(5, false);
                newShiftPage.moveSliderAtCertainPoint("9am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
                newShiftPage.moveSliderAtCertainPoint("5pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
                newShiftPage.selectWorkRole(workRoleOfTM);
                newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
                newShiftPage.clickOnCreateOrNextBtn();
                newShiftPage.searchTeamMemberByName(firstNameOfTM);
                newShiftPage.clickOnCreateOrNextBtn();
                scheduleMainPage.saveSchedule();
                shiftsCountBefore = shiftOperatePage.countShiftsByUserName(firstNameOfTM);

                //create an overtime shift
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                newShiftPage.clickOnDayViewAddNewShiftButton();
                newShiftPage.customizeNewShiftPage();
                newShiftPage.clearAllSelectedDays();
                newShiftPage.selectMultipleOrSpecificWorkDay(6, true);
                newShiftPage.moveSliderAtCertainPoint("10am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
                newShiftPage.moveSliderAtCertainPoint("5pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
                newShiftPage.selectWorkRole(workRoleOfTM);
                newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
                newShiftPage.clickOnCreateOrNextBtn();
                newShiftPage.searchTeamMemberByNameAndAssignOrOfferShift(firstNameOfTM, true);
                newShiftPage.clickOnCreateOrNextBtn();
                scheduleMainPage.saveSchedule();
            }

            if (enterprise.equalsIgnoreCase("circlek")) {
                workRoleOfTM = "CSR";

                // Create and assign shift to consume the available shift hours for the TM
                newShiftPage.clickOnDayViewAddNewShiftButton();
                newShiftPage.customizeNewShiftPage();
                newShiftPage.clearAllSelectedDays();
                newShiftPage.selectMultipleOrSpecificWorkDay(5, false);
                newShiftPage.moveSliderAtCertainPoint("9am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
                newShiftPage.moveSliderAtCertainPoint("5pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
                newShiftPage.selectWorkRole(workRoleOfTM);
                newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
                newShiftPage.clickOnCreateOrNextBtn();
                newShiftPage.searchTeamMemberByName(firstNameOfTM);
                newShiftPage.clickOnCreateOrNextBtn();
                scheduleMainPage.saveSchedule();
                shiftsCountBefore = shiftOperatePage.countShiftsByUserName(firstNameOfTM);

                //create an overtime shift
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                newShiftPage.clickOnDayViewAddNewShiftButton();
                newShiftPage.customizeNewShiftPage();
                newShiftPage.clearAllSelectedDays();
                newShiftPage.selectMultipleOrSpecificWorkDay(6, true);
                newShiftPage.moveSliderAtCertainPoint("9am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
                newShiftPage.moveSliderAtCertainPoint("5pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
                newShiftPage.selectWorkRole(workRoleOfTM);
                newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
                newShiftPage.clickOnCreateOrNextBtn();
                newShiftPage.searchTeamMemberByNameAndAssignOrOfferShift(firstNameOfTM, true);
                newShiftPage.clickOnCreateOrNextBtn();
                scheduleMainPage.saveSchedule();
            }

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Unassigned");
            scheduleMainPage.saveSchedule();
            createSchedulePage.publishActiveSchedule();

            // Offer overtime shift in non-edit mode
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            shiftOperatePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
            shiftOperatePage.closeViewStatusContainer();
            loginPage.logOut();
            Thread.sleep(3000);
            //login with TM.
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            smartCardPage.clickLinkOnSmartCardByName("View Shifts");
            SimpleUtils.assertOnFail("Didn't get open shift offer!", scheduleShiftTablePage.getShiftsCount()>=1, false);

            // Claim overtime shift
            mySchedulePage.clickOnShiftByIndex(0);
            mySchedulePage.claimTheOfferedOpenShift("View Offer");
            loginPage.logOut();
            Thread.sleep(10000);
            // Login as SM and approve claim request from TM
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftOffer.getValue(), ActivityTest.indexOfActivityType.ShiftOffer.name());
            activityPage.verifyActivityOfShiftOffer(firstNameOfTM, location);
            activityPage.approveOrRejectShiftOfferRequestOnActivity(firstNameOfTM, ActivityTest.approveRejectAction.Approve.getValue());
            activityPage.closeActivityWindow();
            // Double check if the approved shift offer has been assigned to the TM
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            int shiftsCountAfter = shiftOperatePage.countShiftsByUserName(firstNameOfTM);
            SimpleUtils.assertOnFail("Failed for approving overtime shift offer! shiftsCountBefore: " + shiftsCountBefore +
                    ", shiftsCountAfter: " + shiftsCountAfter, (shiftsCountAfter - shiftsCountBefore) == 1, false);
            List<WebElement> newShifts = scheduleShiftTablePage.getOneDayShiftByName(6, firstNameOfTM);
            String warningMessage = scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup(newShifts.get(0)).toString();
            SimpleUtils.assertOnFail("The weekly OT violation message display incorrectly in i icon popup! ",
                    warningMessage.contains("5.0 hrs weekly overtime"), false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "")
    @TestName(description = "Should be able to claim the clopening shift offer by TM")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAllowClaimClopeningShiftOfferAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();

            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();


            // Start to check and generate target schedule
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            // Delete open shifts.
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
            scheduleMainPage.selectShiftTypeFilterByText("Open");
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("");
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(firstNameOfTM);

            // To handle the assignment failures when delete TM's shifts and reassign
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            int shiftsCountBefore = 0;

            String workRoleOfTM = "Team Member Corporate-Theatre";

            // Create and assign shift to consume the available shift hours for the TM
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectMultipleOrSpecificWorkDay(2, true);
            newShiftPage.moveSliderAtCertainPoint("9pm", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint("11pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.selectWorkRole(workRoleOfTM);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstNameOfTM);
            newShiftPage.clickOnCreateOrNextBtn();
            scheduleMainPage.saveSchedule();
            shiftsCountBefore = shiftOperatePage.countShiftsByUserName(firstNameOfTM);

            //create an clopening shift
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectMultipleOrSpecificWorkDay(3, true);
            newShiftPage.moveSliderAtCertainPoint("6am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint("11am", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.selectWorkRole(workRoleOfTM);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByNameAndAssignOrOfferShift(firstNameOfTM, true);
            newShiftPage.clickOnCreateOrNextBtn();
            scheduleMainPage.saveSchedule();
            createSchedulePage.publishActiveSchedule();

            // Offer overtime shift in non-edit mode
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            shiftOperatePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
            shiftOperatePage.closeViewStatusContainer();
            loginPage.logOut();
            Thread.sleep(3000);
            //login with TM.
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            smartCardPage.clickLinkOnSmartCardByName("View Shifts");
            SimpleUtils.assertOnFail("Didn't get open shift offer!", scheduleShiftTablePage.getShiftsCount()>=1, false);

            // Claim clopening shift
            mySchedulePage.clickOnShiftByIndex(0);
            mySchedulePage.claimTheOfferedOpenShift("View Offer");
            loginPage.logOut();

            // Login as SM and approve claim request from TM
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftOffer.getValue(), ActivityTest.indexOfActivityType.ShiftOffer.name());
            activityPage.verifyActivityOfShiftOffer(firstNameOfTM, location);
            activityPage.approveOrRejectShiftOfferRequestOnActivity(firstNameOfTM, ActivityTest.approveRejectAction.Approve.getValue());
            activityPage.closeActivityWindow();
            // Double check if the approved shift offer has been assigned to the TM
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            List<WebElement> newShifts = scheduleShiftTablePage.getOneDayShiftByName(2, firstNameOfTM);
            String warningMessage = scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup(newShifts.get(0)).toString();
            SimpleUtils.assertOnFail("The weekly OT violation message display incorrectly in i icon popup! But the actaul is "+warningMessage,
                    warningMessage.contains("Clopening (<12.0)"), false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}