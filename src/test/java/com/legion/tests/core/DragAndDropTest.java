package com.legion.tests.core;

import com.legion.pages.DashboardPage;
import com.legion.pages.ProfileNewUIPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.TeamPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DragAndDropTest extends TestBase {

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{
        this.createDriver((String)params[0],"83","Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the box interaction color and message for TM status: Time Off")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyWarningModelForTimeOffAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        teamPage.goToTeam();
        teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
        String userName = teamPage.selectATeamMemberToViewProfile();
        String firstName = userName.contains(" ") ? userName.split(" ")[0] : userName;
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String myTimeOffLabel = "Time Off";
        profileNewUIPage.selectProfilePageSubSectionByLabel(myTimeOffLabel);
        teamPage.rejectAllTheTimeOffRequests();
        profileNewUIPage.clickOnCreateTimeOffBtn();
        SimpleUtils.assertOnFail("New time off request window not loaded Successfully!", profileNewUIPage.isNewTimeOffWindowLoaded(), false);
        String timeOffReasonLabel = "JURY DUTY";
        profileNewUIPage.selectTimeOffReason(timeOffReasonLabel);
        String timeOffDate = profileNewUIPage.selectStartAndEndDateAtSameDay();
        profileNewUIPage.clickOnSaveTimeOffRequestBtn();

        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()) , false);

        // Navigate to the week that contains the date that provided
        schedulePage.goToSpecificWeekByDate(timeOffDate);

        // Ungenerate and create the schedule
        boolean isWeekGenerated = schedulePage.isWeekGenerated();
        if (isWeekGenerated){
            schedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        schedulePage.createScheduleForNonDGFlowNewUI();

        // Edit schedule to create the new shift for new TM
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.deleteTMShiftInWeekView(userName.contains(" ") ? userName.split(" ")[0] : userName);
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.customizeNewShiftPage();
        schedulePage.clearAllSelectedDays();
        List<Integer> indexes = schedulePage.selectDaysByCountAndCannotSelectedDate(1, timeOffDate);
        schedulePage.selectWorkRole("MOD");
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.searchTeamMemberByName(firstName);
        schedulePage.clickOnOfferOrAssignBtn();

        // Drag the shift to the time off day
        int endIndex = schedulePage.getTheIndexOfTheDayInWeekView(timeOffDate.substring(timeOffDate.length() - 2));
        schedulePage.dragOneAvatarToAnother(indexes.get(0), firstName, endIndex);

        //TODO: verify the warning model pops up, blocked by bug: https://legiontech.atlassian.net/browse/SCH-544
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the box interaction color and message for TM status: Time Off")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyWarningModelForOvertimeAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()) , false);

        // Navigate to next week
        schedulePage.navigateToNextWeek();
        schedulePage.navigateToNextWeek();
        schedulePage.navigateToNextWeek();

        // create the schedule if not created
        boolean isWeekGenerated = schedulePage.isWeekGenerated();
        if (isWeekGenerated){
            schedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        //schedulePage.createScheduleForNonDGFlowNewUI();
        schedulePage.createScheduleForNonDGFlowNewUIWithGivingParameters("Wednesday", "08:00AM", "08:00PM");
        // Edit schedule to create the new shifts for new TM1 and TM2
        String TM1 = "John";
        String TM2 = "Pat";
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.deleteTMShiftInWeekView(TM1);
        schedulePage.deleteTMShiftInWeekView(TM2);
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.selectWorkRole("EVENT MANAGER");
        schedulePage.clearAllSelectedDays();
        schedulePage.selectSpecificWorkDay(1);
        schedulePage.selectSpecificWorkDay(2);
        schedulePage.moveSliderAtSomePoint("8", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.searchTeamMemberByName(TM1);
        schedulePage.clickOnOfferOrAssignBtn();

        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.selectWorkRole("EVENT MANAGER");
        schedulePage.clearAllSelectedDays();
        schedulePage.selectSpecificWorkDay(1);
        schedulePage.moveSliderAtSomePoint("8", 10, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.moveSliderAtSomePoint("8", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.searchTeamMemberByName(TM2);
        schedulePage.clickOnOfferOrAssignBtn();
        schedulePage.saveSchedule();

        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        HashMap<String, WebElement> shifsSwaped = schedulePage.dragOneAvatarToAnotherSpecificAvatar(1,TM1,0,TM2);
        String expectedMessage = "John will incur 1 hours of overtime";
        schedulePage.verifyMessageInConfirmPage(expectedMessage);
        schedulePage.selectSwapOrAssignOption("swap");
        schedulePage.clickConfirmBtnOnDragAndDropConfirmPage();
        schedulePage.verifyShiftsSwaped(shifsSwaped);
    }

}
