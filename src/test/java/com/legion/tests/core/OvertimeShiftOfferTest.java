package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.Constants;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;
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

            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

            scheduleCommonPage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Action Required");
            shiftOperatePage.deleteTMShiftInWeekView("");
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Open");
            shiftOperatePage.deleteTMShiftInWeekView("");
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
            scheduleMainPage.clickOnFilterBtn();
            shiftOperatePage.deleteTMShiftInWeekView(firstNameOfTM);
            scheduleMainPage.saveSchedule();
            scheduleMainPage.publishOrRepublishSchedule();

            // Create and assign shift to consume the available shift hours for the TM
            String workRoleOfTM = "Retail Associate";
            String enterprise = System.getProperty("enterprise").toLowerCase();
            if (enterprise.equalsIgnoreCase("cinemark-wkdy")) {
                workRoleOfTM = "Team Member Corporate-Theatre";
            }
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
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
            scheduleMainPage.publishOrRepublishSchedule();

            //create an overtime shift
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectMultipleOrSpecificWorkDay(3, true);
            newShiftPage.moveSliderAtCertainPoint("9am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint("8pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.selectWorkRole(workRoleOfTM);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.clickOnCreateOrNextBtn();
            scheduleMainPage.saveSchedule();
            scheduleMainPage.publishOrRepublishSchedule();

            // Offer overtime shift in non-edit mode
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", shiftOperatePage.isOfferTMOptionEnabled(), false);
            shiftOperatePage.clickOnOfferTMOption();
            newShiftPage.searchTeamMemberByNameNLocation(firstNameOfTM, location);
            newShiftPage.clickOnOfferOrAssignBtn();
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            scheduleShiftTablePage.clickViewStatusBtn();
            shiftOperatePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
            shiftOperatePage.closeViewStatusContainer();
            loginPage.logOut();

            //login with TM.
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.navigateToNextWeek();
            smartCardPage.clickLinkOnSmartCardByName("View Shifts");
            SimpleUtils.assertOnFail("Didn't get open shift offer!", scheduleShiftTablePage.getShiftsCount()==1, false);

            // Claim overtime shift
            mySchedulePage.clickOnShiftByIndex(0);
            mySchedulePage.claimTheOfferedOpenShift("View Offer");
            loginPage.logOut();

            // Login as SM and approve claim request from TM
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftOffer.getValue(), ActivityTest.indexOfActivityType.ShiftOffer.name());
            activityPage.verifyActivityOfShiftOffer(firstNameOfTM, location);
            activityPage.approveOrRejectShiftOfferRequestOnActivity(firstNameOfTM, ActivityTest.approveRejectAction.Approve.getValue());
            activityPage.closeActivityWindow();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}