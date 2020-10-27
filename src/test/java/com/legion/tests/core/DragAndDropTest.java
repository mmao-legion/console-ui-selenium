package com.legion.tests.core;

import com.legion.pages.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DragAndDropTest extends TestBase {

    private static HashMap<String, String> propertyCustomizeMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ScheduleCustomizeNewShift.json");

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
    @TestName(description = "Validate the box interaction color and message for TM status: Scheduled at home location")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyWarningMessageForAlreadyScheduledAtHomeLocationAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        // Select one team member to view profile
        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        teamPage.goToTeam();
        teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
        String userName = teamPage.selectATeamMemberToViewProfile();
        String firstName = userName.contains(" ") ? userName.split(" ")[0] : userName;

        // Go to Schedule page, Schedule tab
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

        // Create schedule if it is not created
        boolean isWeekGenerated = schedulePage.isWeekGenerated();
        if (!isWeekGenerated){
            schedulePage.createScheduleForNonDGFlowNewUI();
        }

        // Edit the Schedule
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

        // Delete all the shifts that are assigned to the team member on Step #1
        schedulePage.deleteTMShiftInWeekView(firstName);

        // Create new shift for this TM on Monday and Tuesday
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.customizeNewShiftPage();
        schedulePage.clearAllSelectedDays();
        List<Integer> dayIndexes = schedulePage.selectDaysByCountAndCannotSelectedDate(2, "");
        schedulePage.selectWorkRole("MOD");
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.searchTeamMemberByName(firstName);
        schedulePage.clickOnOfferOrAssignBtn();

        // Save the Schedule
        schedulePage.saveSchedule();
        List<Integer> shiftIndexes = schedulePage.getAddedShiftIndexes(firstName);
        SimpleUtils.assertOnFail("Failed to add two shifts!", shiftIndexes != null && shiftIndexes.size() == 2, false);
        List<String> shiftInfo = schedulePage.getTheShiftInfoByIndex(shiftIndexes.get(1));

        // Edit the Schedule
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

        // Drag the TM's avatar on Monday to another TM's shift on Tuesday
        schedulePage.dragOneAvatarToAnother(dayIndexes.get(0), firstName, dayIndexes.get(1));
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
        schedulePage.deleteTMShiftInWeekView(firstName);
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
    @TestName(description = "Validate the box interaction color and message for TM status: overtime")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySwapWarningModelForOvertimeAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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

        // create the schedule if not created
        boolean isWeekGenerated = schedulePage.isWeekGenerated();
        if (isWeekGenerated){
            schedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        schedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( "08:00AM", "08:00PM");
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
        schedulePage.dragOneAvatarToAnotherSpecificAvatar(1,TM1,0,TM2);
        List<String> swapData = schedulePage.getShiftSwapDataFromConfirmPage("swap");
        String expectedMessage = "John will incur 1 hours of overtime";
        schedulePage.verifyMessageInConfirmPage(expectedMessage);
        schedulePage.selectSwapOrAssignOption("swap");
        schedulePage.clickConfirmBtnOnDragAndDropConfirmPage();
        schedulePage.verifyShiftsAreSwapped(swapData);

        schedulePage.dragOneAvatarToAnotherSpecificAvatar(1,TM2,0,TM1);
        schedulePage.dragOneAvatarToAnotherSpecificAvatar(1,TM1,0,TM2);
        swapData = schedulePage.getShiftSwapDataFromConfirmPage("assign");
        schedulePage.selectSwapOrAssignOption("assign");
        schedulePage.clickConfirmBtnOnDragAndDropConfirmPage();
        if (schedulePage.verifyDayHasShiftByName(0,TM1)==2 && schedulePage.verifyDayHasShiftByName(1,TM1)==1){
            SimpleUtils.pass("assign successfully!");
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the box interaction color and message when TM will incur clopening")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyWarningModelForClopeningAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        controlsNewUIPage.clickOnControlsConsoleMenu();
        SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
        controlsNewUIPage.clickOnControlsComplianceSection();
        SimpleUtils.assertOnFail("Compliance page not loaded successfully!", controlsNewUIPage.isCompliancePageLoaded(), false);
        //turn on clopening toggle and set hours
        controlsNewUIPage.turnONClopeningToggleAndSetHours(12);

        // Go to Schedule page, Schedule tab
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

        // Create schedule if it is not created
        boolean isWeekGenerated = schedulePage.isWeekGenerated();
        if (isWeekGenerated){
            schedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        schedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( "09:00AM", "11:00PM");
        // Edit the Schedule
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

        //Get two random TM name from shifts
        List<String> shiftInfo1 = schedulePage.getTheShiftInfoByIndex(schedulePage.getRandomIndexOfShift());
        String firstNameOfTM1 = shiftInfo1.get(0);
        String workRoleOfTM1 = shiftInfo1.get(4);
        List<String> shiftInfo2 = schedulePage.getTheShiftInfoByIndex(schedulePage.getRandomIndexOfShift());
        String firstNameOfTM2 = shiftInfo2.get(0);
        String workRoleOfTM2 = shiftInfo2.get(4);
        // Delete all the shifts that are assigned to the team member
        schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);
        schedulePage.deleteTMShiftInWeekView(firstNameOfTM2);

        // Create new shift for TM1 on Monday and Wednesday
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.customizeNewShiftPage();
        schedulePage.clearAllSelectedDays();
//        schedulePage.selectSpecificWorkDay(1);
        schedulePage.selectDaysByIndex(0, 0, 2);
        schedulePage.selectWorkRole(workRoleOfTM1);
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.searchTeamMemberByName(firstNameOfTM1);
        schedulePage.clickOnOfferOrAssignBtn();

        //Create new shift for TM2 on Tuesday
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.customizeNewShiftPage();
        schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME_2"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount2.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME_2"), ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        schedulePage.clearAllSelectedDays();
//        schedulePage.selectSpecificWorkDay(1);
        schedulePage.selectDaysByIndex(1, 1, 1);
        schedulePage.selectWorkRole(workRoleOfTM2);
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.searchTeamMemberByName(firstNameOfTM2);
        schedulePage.clickOnOfferOrAssignBtn();

        // Save the Schedule
        schedulePage.saveSchedule();
//        List<Integer> shiftIndexes = schedulePage.getAddedShiftIndexes(firstName);
//        SimpleUtils.assertOnFail("Failed to add two shifts!", shiftIndexes != null && shiftIndexes.size() == 2, false);
//        List<String> shiftInfo = schedulePage.getTheShiftInfoByIndex(shiftIndexes.get(1));

        // Edit the Schedule
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
    }
}
