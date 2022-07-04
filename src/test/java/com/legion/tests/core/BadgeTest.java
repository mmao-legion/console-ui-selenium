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
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();

            // Prepare badge for the 2 TMs
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            String teamMemberA = "Holly Trainer";
            String teamMemberB = "Chloe Keates";

            teamPage.goToTeam();
            String tmA = teamPage.searchAndSelectTeamMemberByName(teamMemberA);
            teamPage.clickTheTMByName(tmA);
            teamPage.clickEditProfileBtn();
            teamPage.deleteBadges();
            teamPage.selectBadgeByName("Drop In Shift");
            teamPage.saveEditProfileBtn();
            if (teamPage.isWithBadges()) {
                SimpleUtils.pass("Bage for TM " + tmA + " has been added!");
            }

            teamPage.goToTeam();
            String tmB = teamPage.searchAndSelectTeamMemberByName(teamMemberB);
            teamPage.clickTheTMByName(tmB);
            teamPage.clickEditProfileBtn();
            teamPage.deleteBadges();
            teamPage.saveEditProfileBtn();
            if (!teamPage.isWithBadges()) {
                SimpleUtils.pass("Bage for TM " + tmB + " has been removed!");
            }

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
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByNameNLocation(teamMemberA, location);
            newShiftPage.clickOnCreateOrNextBtn();

            // Create and assign open shift to tmB
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectMultipleOrSpecificWorkDay(5, true);
            newShiftPage.moveSliderAtCertainPoint("9am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint("3pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.selectWorkRole(workRoleOfTM);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByNameNLocation(teamMemberB, location);
            newShiftPage.clickOnCreateOrNextBtn();
            scheduleMainPage.saveSchedule();

            // Check If badge status has been refreshed
            shiftOperatePage.checkBadgeOnProfilePopup(teamMemberA, teamMemberB);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}