package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class BadgeTest extends TestBase {
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
    @TestName(description = "Should be able to see the badge change while switch between the shift smart card view")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheBadgeChangeInShiftSmartCardViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Prepare badge for the 2 TMs
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            String teamMemberA = "Holly Trainer";
            String teamMemberB = "Chloe Keates";
            teamPage.goToTeam();
            String tmA = teamPage.searchAndSelectTeamMemberByName(teamMemberA);
            teamPage.clickTheTMByNameAndEdit(tmA);
            teamPage.deleteBadges();
            teamPage.verifyTheFunctionOfEditBadges();
            if (teamPage.isWithBadges()) {
                SimpleUtils.pass("Bage for TM " + tmA + " has been added!");
            }

            teamPage.goToTeam();
            String tmB = teamPage.searchAndSelectTeamMemberByName(teamMemberB);
            teamPage.clickTheTMByNameAndEdit(tmB);
            teamPage.deleteBadges();
            if (!teamPage.isWithBadges()) {
                SimpleUtils.pass("Bage for TM " + tmB + " has been removed!");
            }

            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();

            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();

            // Start to check and generate target schedule
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }

            scheduleCommonPage.navigateToNextWeek();
            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            // Delete all shifts
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
            shiftOperatePage.deleteAllShiftsInWeekView();

            // Modify corresponding work role by enterprise
            String workRoleOfTM = "Retail Associate";
            String enterprise = System.getProperty("enterprise").toLowerCase();
            if (enterprise.equalsIgnoreCase("cinemark-wkdy")) {
                workRoleOfTM = "Team Member Corporate-Theatre";
            }

            // Create and assign open shift to tmA
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectMultipleOrSpecificWorkDay(1, true);
            newShiftPage.moveSliderAtCertainPoint("8am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint("5pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.selectWorkRole(workRoleOfTM);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByNameNLocation(teamMemberA, location);
            newShiftPage.clickOnOfferOrAssignBtn();
            newShiftPage.clickOnCreateOrNextBtn();

            // Create and assign open shift to tmB
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectMultipleOrSpecificWorkDay(3, true);
            newShiftPage.moveSliderAtCertainPoint("8am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint("5pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.selectWorkRole(workRoleOfTM);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByNameNLocation(teamMemberB, location);
            newShiftPage.clickOnOfferOrAssignBtn();
            newShiftPage.clickOnCreateOrNextBtn();
            scheduleMainPage.saveSchedule();

            // TODO: Check the badge in the shift smart card while switching between the to shifts
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}