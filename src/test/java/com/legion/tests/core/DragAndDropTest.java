package com.legion.tests.core;

import com.google.inject.internal.cglib.core.$WeakCacheKey;
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

import static com.legion.utils.MyThreadLocal.getDriver;
import static com.legion.utils.MyThreadLocal.getEnterprise;

public class DragAndDropTest extends TestBase {

    private static HashMap<String, String> propertyCustomizeMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ScheduleCustomizeNewShift.json");
    private static HashMap<String, Object[][]> kendraScott2TeamMembers = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson("KendraScott2TeamMembers.json");
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
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the box interaction color and message for TM status: Scheduled at home location")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyWarningMessageForAlreadyScheduledAtHomeLocationAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
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
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
                schedulePage.createScheduleForNonDGFlowNewUI();
            }

            // Edit the Schedule
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            // Delete all the shifts that are assigned to the team member on Step #1
            schedulePage.deleteTMShiftInWeekView(firstName);
            String workRole = schedulePage.getRandomWorkRole();

            // Create new shift for this TM on Monday and Tuesday
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            List<Integer> dayIndexes = schedulePage.selectDaysByCountAndCannotSelectedDate(2, "");
            //schedulePage.selectWorkRole("MOD");
            schedulePage.selectWorkRole(workRole);
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

            String weekday = schedulePage.getWeekDayTextByIndex(Integer.parseInt(shiftInfo.get(1)));
            String fullWeekDay = SimpleUtils.getFullWeekDayName(weekday);
            String expectedMessage = shiftInfo.get(0) + " is scheduled " + shiftInfo.get(6).toUpperCase() + " on " + fullWeekDay
                    + ". This shift will be converted to an open shift";
            schedulePage.verifySwapAndAssignWarningMessageInConfirmPage(expectedMessage, "swap");
            schedulePage.verifySwapAndAssignWarningMessageInConfirmPage(expectedMessage, "assign");
            List<String> swapData = schedulePage.getShiftSwapDataFromConfirmPage("swap");
            schedulePage.selectSwapOrAssignOption("swap");
            schedulePage.clickConfirmBtnOnDragAndDropConfirmPage();
            schedulePage.verifyShiftsAreSwapped(swapData);

            // Delete the shifts for this TM
            schedulePage.deleteTMShiftInWeekView(firstName);

            // Prepare the shift for this TM again
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            dayIndexes = schedulePage.selectDaysByCountAndCannotSelectedDate(2, "");
            //schedulePage.selectWorkRole("MOD");
            schedulePage.selectWorkRole(workRole);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstName);
            schedulePage.clickOnOfferOrAssignBtn();

            // Save the Schedule
            schedulePage.saveSchedule();
            shiftIndexes = schedulePage.getAddedShiftIndexes(firstName);
            SimpleUtils.assertOnFail("Failed to add the shifts!", shiftIndexes != null && shiftIndexes.size() > 0, false);
            shiftInfo = schedulePage.getTheShiftInfoByIndex(shiftIndexes.get(1));

            // Edit the Schedule
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            // Drag the TM's shift on Monday to another TM's shift on Tuesday
            schedulePage.dragOneShiftToAnotherDay(dayIndexes.get(0), firstName, dayIndexes.get(1));

            // Verify the warning model pops up
            expectedMessage = firstName + " is scheduled " + shiftInfo.get(6) + " on " + fullWeekDay
                    + ". This shift will be converted to an open shift";

            schedulePage.verifyMessageOnCopyMoveConfirmPage(expectedMessage,expectedMessage);
            schedulePage.selectCopyOrMoveByOptionName("Move");
            schedulePage.clickConfirmBtnOnDragAndDropConfirmPage();

            // Verify if Confirm Store opening closing hour window pops up
            schedulePage.verifyConfirmStoreOpenCloseHours();

            if (schedulePage.ifMoveAnywayDialogDisplay()) {
                schedulePage.moveAnywayWhenChangeShift();
            } else {
                SimpleUtils.fail("MOVE ANYWAY dialog failed to load!", false);
            }
            schedulePage.saveSchedule();
            schedulePage.verifyShiftIsMovedToAnotherDay(dayIndexes.get(0), firstName, dayIndexes.get(1));
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the box interaction color and message when TM is from another store and is already scheduled at this store")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyWarningMsgForTMFromAnotherStoreScheduledAtCurrentLocationAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsPage.gotoControlsPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            SimpleUtils.assertOnFail("Controls Scheduling Policies page not loaded Successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
            controlsNewUIPage.clickOnGlobalLocationButton();
            controlsNewUIPage.clickOnSchedulingPoliciesShiftAdvanceBtn();
            controlsNewUIPage.enableOverRideAssignmentRuleAsYes();

            // Set "Can a manager add another locations' employee in schedule before the employee's home location has published the schedule?" to "Yes, anytime"
            controlsPage.gotoControlsPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
            SimpleUtils.assertOnFail("Scheduling collaboration page not loaded successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
            controlsNewUIPage.updateCanManagerAddAnotherLocationsEmployeeInScheduleBeforeTheEmployeeHomeLocationHasPublishedTheSchedule("Yes, anytime");

            dashboardPage.navigateToDashboard();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String anotherLocation = "";
            if (getEnterprise().equalsIgnoreCase("KendraScott2")) {
                 anotherLocation = "AUSTIN DOWNTOWN";
            } else {
                anotherLocation = "7500216 - Can-Ski Village";
            }
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(anotherLocation);
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Select one team member to view profile
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            String userName = teamPage.selectATeamMemberToViewProfile();
            String firstName = userName.contains(" ") ? userName.split(" ")[0] : userName;

            // Go to Dashboard page
            dashboardPage.navigateToDashboard();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Change the location to the original location
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(location);
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            // Create schedule if it is not created
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                schedulePage.createScheduleForNonDGFlowNewUI();
            }

            // Edit the Schedule
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            // Delete all the shifts that are assigned to the team member on Step #1
            schedulePage.deleteTMShiftInWeekView(firstName);
            String workRole = schedulePage.getRandomWorkRole();

            // Create new shift for this TM on Monday and Tuesday
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            List<Integer> dayIndexes = schedulePage.selectDaysByCountAndCannotSelectedDate(2, "");
            //schedulePage.selectWorkRole("MOD");
            schedulePage.selectWorkRole(workRole);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstName);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.deleteTMShiftInWeekView("Unassigned");

            // Save the Schedule
            schedulePage.saveSchedule();
            List<Integer> shiftIndexes = schedulePage.getAddedShiftIndexes(firstName);
            SimpleUtils.assertOnFail("Failed to add two shifts!", shiftIndexes != null && shiftIndexes.size() > 0, false);
            List<String> shiftInfo = schedulePage.getTheShiftInfoByIndex(shiftIndexes.get(1));

            // Edit the Schedule
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            // Drag the TM's avatar on Monday to another TM's shift on Tuesday
            schedulePage.dragOneAvatarToAnother(dayIndexes.get(0), firstName, dayIndexes.get(1));

            String weekday = schedulePage.getWeekDayTextByIndex(Integer.parseInt(shiftInfo.get(1)));
            String fullWeekDay = SimpleUtils.getFullWeekDayName(weekday);
            String expectedMessage = shiftInfo.get(0) + " is scheduled " + shiftInfo.get(6).toUpperCase() + " on " + fullWeekDay
                    + ". This shift will be converted to an open shift";
            schedulePage.verifySwapAndAssignWarningMessageInConfirmPage(expectedMessage, "swap");
            schedulePage.verifySwapAndAssignWarningMessageInConfirmPage(expectedMessage, "assign");
            List<String> swapData = schedulePage.getShiftSwapDataFromConfirmPage("swap");
            schedulePage.selectSwapOrAssignOption("swap");
            schedulePage.clickConfirmBtnOnDragAndDropConfirmPage();
            schedulePage.verifyShiftsAreSwapped(swapData);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the box interaction color and message for TM status: Time Off")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyWarningModelForTimeOffAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            String userName = teamPage.selectATeamMemberToViewProfile();
            String firstName = userName.contains(" ") ? userName.split(" ")[0] : userName;
            String lastName = userName.contains(" ") ? userName.split(" ")[1] : userName;
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
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            // Navigate to the week that contains the date that provided
            schedulePage.goToSpecificWeekByDate(timeOffDate);

            // Ungenerate and create the schedule
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();

            // Edit schedule to create the new shift for new TM
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView(firstName);
            String workRole = schedulePage.getRandomWorkRole();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            List<Integer> indexes = schedulePage.selectDaysByCountAndCannotSelectedDate(1, timeOffDate);
            //schedulePage.selectWorkRole("MOD");
            schedulePage.selectWorkRole(workRole);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstName + " " + lastName.substring(0, 1));
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();

            // Drag the shift to the time off day
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            int endIndex = schedulePage.getTheIndexOfTheDayInWeekView(timeOffDate.substring(timeOffDate.length() - 2));
            schedulePage.dragOneAvatarToAnother(indexes.get(0), firstName, endIndex);

            // Verify the warning model pops up and Click on OK button
            schedulePage.verifyWarningModelForAssignTMOnTimeOff(firstName);

            // Drag the TM's shift to the day that he/she has time off
            schedulePage.dragOneShiftToAnotherDay(indexes.get(0), firstName, endIndex);

            // Verify the Warning model pops up with the message
            String expectedMsg = firstName + " is approved for Time Off";
            schedulePage.verifyMessageOnCopyMoveConfirmPage(expectedMsg, expectedMsg);
            String copyOption = "Copy";
            String moveOption = "Move";
            schedulePage.verifyConfirmBtnIsDisabledForSpecificOption(copyOption);
            schedulePage.verifyConfirmBtnIsDisabledForSpecificOption(moveOption);
            schedulePage.clickOnCancelEditShiftTimeButton();

            // Verify nothing happens after clicking CANCEL button
            if (schedulePage.verifyDayHasShiftByName(indexes.get(0), firstName) == 1 && schedulePage.verifyDayHasShiftByName(endIndex, firstName) == 0)
                SimpleUtils.pass("Nothing happens as expected after clicking OK button");
            else
                SimpleUtils.fail("The TM's shift may be assigned unexpected", false);
            schedulePage.saveSchedule();

            // Clean up data
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            teamPage.searchAndSelectTeamMemberByName(firstName);
            profileNewUIPage.selectProfilePageSubSectionByLabel(myTimeOffLabel);
            teamPage.rejectAllTheTimeOffRequests();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the box interaction color and message for TM status: overtime")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySwapWarningModelForOvertimeAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            String userName1 = teamPage.selectATeamMemberToViewProfile();
            String TM1 = userName1.contains(" ") ? userName1.split(" ")[0] : userName1;
            teamPage.goToTeam();
            String userName2 = teamPage.selectATeamMemberToViewProfile();
            String TM2 = userName2.contains(" ") ? userName2.split(" ")[0] : userName2;

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
            // recreate the schedule
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();
            // Edit schedule to create the new shifts for new TM1 and TM2
            //String TM1 = "John";
            //String TM2 = "Pat";
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView(TM1);
            schedulePage.deleteTMShiftInWeekView(TM2);
            String workRole = schedulePage.getRandomWorkRole();
            schedulePage.clickOnDayViewAddNewShiftButton();
            //schedulePage.selectWorkRole("EVENT MANAGER");
            schedulePage.selectWorkRole(workRole);
            schedulePage.clearAllSelectedDays();
            schedulePage.selectSpecificWorkDay(2);
            schedulePage.moveSliderAtSomePoint("8", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(TM1);
            schedulePage.clickOnOfferOrAssignBtn();

            schedulePage.clickOnDayViewAddNewShiftButton();
            //schedulePage.selectWorkRole("EVENT MANAGER");
            schedulePage.selectWorkRole(workRole);
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
            String expectedMessage = TM1+" will incur 1 hours of overtime";
            SimpleUtils.report(expectedMessage);
            schedulePage.verifyMessageInConfirmPage(expectedMessage, expectedMessage);
            schedulePage.selectSwapOrAssignOption("swap");
            schedulePage.clickConfirmBtnOnDragAndDropConfirmPage();
            schedulePage.verifyShiftsAreSwapped(swapData);

            //verify cancel button
            schedulePage.dragOneAvatarToAnotherSpecificAvatar(1,TM2,0,TM1);
            schedulePage.selectSwapOrAssignOption("swap");
            schedulePage.clickConfirmBtnOnDragAndDropConfirmPage();
            schedulePage.dragOneAvatarToAnotherSpecificAvatar(1,TM1,0,TM2);
            schedulePage.clickCancelBtnOnDragAndDropConfirmPage();
            if (schedulePage.verifyDayHasShiftByName(0,TM1)==1 && schedulePage.verifyDayHasShiftByName(1,TM1)==1){
                SimpleUtils.pass("cancel successfully!");
            }

            schedulePage.dragOneAvatarToAnotherSpecificAvatar(1,TM1,0,TM2);
            schedulePage.selectSwapOrAssignOption("assign");
            schedulePage.clickConfirmBtnOnDragAndDropConfirmPage();
            if (schedulePage.verifyDayHasShiftByName(0,TM1)==2 && schedulePage.verifyDayHasShiftByName(1,TM1)==1){
                SimpleUtils.pass("assign successfully!");
            }
            schedulePage.deleteTMShiftInWeekView(TM1);
            schedulePage.deleteTMShiftInWeekView(TM2);
            schedulePage.saveSchedule();

            //verify change shift
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            //schedulePage.selectWorkRole("EVENT MANAGER");
            schedulePage.selectWorkRole(workRole);
            schedulePage.clearAllSelectedDays();
            //schedulePage.selectDaysByIndex(0, 0, 2);
            schedulePage.selectSpecificWorkDay(1);
            schedulePage.moveSliderAtSomePoint("8", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(TM1);
            schedulePage.clickOnOfferOrAssignBtn();

            schedulePage.clickOnDayViewAddNewShiftButton();
            //schedulePage.selectWorkRole("EVENT MANAGER");
            schedulePage.selectWorkRole(workRole);
            schedulePage.clearAllSelectedDays();
            schedulePage.selectDaysByIndex(1, 1, 1);
            //schedulePage.selectSpecificWorkDay(1);
            schedulePage.moveSliderAtSomePoint("8", 10, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtSomePoint("8", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(TM1);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.dragOneShiftToAnotherDay(1,TM1,0);
            if (schedulePage.ifMoveAnywayDialogDisplay()){
                schedulePage.moveAnywayWhenChangeShift();
            }
            schedulePage.saveSchedule();
            expectedMessage = "1 hrs daily overtime";
            List<WebElement> shiftsOfFirstDay = schedulePage.getOneDayShiftByName(0, TM1);
            SimpleUtils.assertOnFail("Get "+TM1+"'s shift failed",shiftsOfFirstDay.size()>0, false);
            String actualMessage=null;
            for (String s:schedulePage.getComplianceMessageFromInfoIconPopup(shiftsOfFirstDay.get(shiftsOfFirstDay.size()-1))){
                actualMessage = actualMessage+s;
            }
            SimpleUtils.assertOnFail("overtime comliance message display failed",
                    actualMessage.toString().contains(expectedMessage), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the box interaction color and message when SM tries to assign TM to an open shift that overlaps a time TM is already assigned to")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyWarningMessageWhenAssignTMToOpenShiftThatTMIsAlreadyAssignedToAsStoreManager(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Select one team member to view profile
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            String userName = teamPage.selectATeamMemberToViewProfile();
            String firstName = userName.contains(" ") ? userName.split(" ")[0] : userName;

            // Go to Schedule page, Schedule tab, navigate to next week
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            schedulePage.navigateToNextWeek();

            // Create schedule if it is not created
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }

            // Edit the Schedule
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            // Delete all the shifts that are assigned to the team member on Step #1
            schedulePage.deleteTMShiftInWeekView(firstName);
            schedulePage.deleteTMShiftInWeekView("Open");
            String workRole = schedulePage.getRandomWorkRole();

            // Create 2 new shifts for this TM on Monday and Tuesday
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            List<Integer> dayIndexes = schedulePage.selectDaysByCountAndCannotSelectedDate(2, "");
            //schedulePage.selectWorkRole("MOD");
            schedulePage.selectWorkRole(workRole);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstName);
            schedulePage.clickOnOfferOrAssignBtn();

            // Create an open shift on Tuesday
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            if (dayIndexes.size() == 2)
                schedulePage.selectWorkingDaysOnNewShiftPageByIndex(dayIndexes.get(1));
            else
                schedulePage.selectWorkingDaysOnNewShiftPageByIndex(1);
            //schedulePage.selectWorkRole("MOD");
            schedulePage.selectWorkRole(workRole);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();

            // Save the Schedule
            schedulePage.saveSchedule();
            List<Integer> shiftIndexes = schedulePage.getAddedShiftIndexes(firstName);
            SimpleUtils.assertOnFail("Failed to add two shifts for the TM!", shiftIndexes != null && shiftIndexes.size() == 2, false);
            List<String> shiftInfo = schedulePage.getTheShiftInfoByIndex(shiftIndexes.get(1));
            List<Integer> openShiftIndexes = schedulePage.getAddedShiftIndexes("Open");
            SimpleUtils.assertOnFail("Failed to add an open shift!", openShiftIndexes != null && openShiftIndexes.size() == 1, false);
            List<String> openShiftInfo = schedulePage.getOpenShiftInfoByIndex(openShiftIndexes.get(0));

            // Edit the Schedule
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            // Drag the TM's avatar on Monday to the open shift on Tuesday
            schedulePage.dragOneAvatarToAnotherSpecificAvatar(0,firstName,1,"Open");
            String weekday = schedulePage.getWeekDayTextByIndex(Integer.parseInt(shiftInfo.get(1)));
            String fullWeekDay = SimpleUtils.getFullWeekDayName(weekday);
            String expectedMessage = shiftInfo.get(0) + " is scheduled " + shiftInfo.get(6).toUpperCase() + " on " + fullWeekDay
                    + ". This shift will be converted to an open shift";
            schedulePage.verifySwapAndAssignWarningMessageInConfirmPage(expectedMessage,"assign");
            schedulePage.clickConfirmBtnOnDragAndDropConfirmPage();

            // Verify the shift is assigned
            List<String> shiftInfoAfterDrag = schedulePage.getTheShiftInfoByIndex(shiftIndexes.get(1));
            List<String> openShiftInfoAfterDrag = schedulePage.getOpenShiftInfoByIndex(openShiftIndexes.get(0));

            if (shiftInfoAfterDrag.get(2).equals(openShiftInfo.get(0)) && shiftInfoAfterDrag.get(4).equals(openShiftInfo.get(1)) && openShiftInfoAfterDrag.get(0).equals(shiftInfo.get(2)))
                SimpleUtils.pass("Assign a TM to an open shift that overlaps a time TM is already assigned to successfully!");
            else
                SimpleUtils.fail("Failed to assign a TM to an open shift that overlaps a time TM is already assigned to",false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the box interaction color and message when TM will incur clopening")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyWarningModelForClopeningAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            SimpleUtils.assertOnFail("Compliance page not loaded successfully!", controlsNewUIPage.isCompliancePageLoaded(), false);
            //turn on clopening toggle and set hours
            controlsNewUIPage.selectClopeningHours(12);

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

            schedulePage.navigateToNextWeek();
            isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( "09:00AM", "11:00PM");
            List<String> shiftInfo = schedulePage.getTheShiftInfoByIndex(schedulePage.getRandomIndexOfShift());
            String firstNameOfTM1 = shiftInfo.get(0);
            while (firstNameOfTM1.equalsIgnoreCase("open")) {
                shiftInfo = schedulePage.getTheShiftInfoByIndex(schedulePage.getRandomIndexOfShift());
                firstNameOfTM1  = shiftInfo.get(0);
            }
            String workRoleOfTM1 = shiftInfo.get(4);
            while (shiftInfo.get(0).equalsIgnoreCase(firstNameOfTM1) || shiftInfo.get(0).equalsIgnoreCase("open")) {
                shiftInfo = schedulePage.getTheShiftInfoByIndex(schedulePage.getRandomIndexOfShift());
            }
            String firstNameOfTM2 = shiftInfo.get(0);
            String workRoleOfTM2 = shiftInfo.get(4);

            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.searchAndSelectTeamMemberByName(firstNameOfTM1);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String myTimeOffLabel = "Time Off";
            profileNewUIPage.selectProfilePageSubSectionByLabel(myTimeOffLabel);
            teamPage.rejectAllTheTimeOffRequests();
            teamPage.goToTeam();
            teamPage.searchAndSelectTeamMemberByName(firstNameOfTM2);
            profileNewUIPage.selectProfilePageSubSectionByLabel(myTimeOffLabel);
            teamPage.rejectAllTheTimeOffRequests();
            // Edit the Schedule
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            schedulePage.navigateToNextWeek();
            // Edit the Schedule
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            // Delete all the shifts that are assigned to the team member
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM2);

            // Create new shift for TM1 on Monday and Wednesday
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            schedulePage.selectDaysByIndex(0, 0, 2);
            schedulePage.selectWorkRole(workRoleOfTM1);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM1);
            schedulePage.clickOnOfferOrAssignBtn();

            //Create new shift for TM2 on Tuesday
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME_2"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount3.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME_2"), ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clearAllSelectedDays();
            schedulePage.selectDaysByIndex(1, 1, 1);
            schedulePage.selectWorkRole(workRoleOfTM2);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM2);
            schedulePage.clickOnOfferOrAssignBtn();

            // Save the Schedule
            schedulePage.saveSchedule();

            // Edit the Schedule and try to drag TM1 on Monday to TM2 on Tuesday
            String clopeningWarningMessage = " will incur clopening";
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            int i=0;
            while (!schedulePage.isDragAndDropConfirmPageLoaded() && i<5){
                schedulePage.dragOneAvatarToAnotherSpecificAvatar(0,firstNameOfTM1,1,firstNameOfTM2);
                i++;
                Thread.sleep(2000);
            }
            SimpleUtils.assertOnFail("Clopening message display incorrectly on swap section!",
                    schedulePage.verifySwapAndAssignWarningMessageInConfirmPage(firstNameOfTM1 + clopeningWarningMessage, "swap"), false);
            SimpleUtils.assertOnFail("Clopening message display incorrectly on assign section!",
                    schedulePage.verifySwapAndAssignWarningMessageInConfirmPage(firstNameOfTM1 + clopeningWarningMessage, "assign"), false);

            // Swap TM1 and TM2, check the TMs been swapped successfully
            schedulePage.selectSwapOrAssignOption("swap");
            schedulePage.clickConfirmBtnOnDragAndDropConfirmPage();
            schedulePage.verifyDayHasShiftByName(1, firstNameOfTM1);
            schedulePage.verifyDayHasShiftByName(0, firstNameOfTM2);
            schedulePage.saveSchedule();
            //check compliance smart card display
            SimpleUtils.assertOnFail("Compliance smart card display successfully!",
                    schedulePage.verifyComplianceShiftsSmartCardShowing(), false);
            schedulePage.clickViewShift();

            //check the violation on the info popup
            List<WebElement> shiftsOfTuesday = schedulePage.getOneDayShiftByName(1, firstNameOfTM1);
            SimpleUtils.assertOnFail("Get compliance shift failed",shiftsOfTuesday.size()>0, false);

            List<WebElement> shiftsOfWednesday = schedulePage.getOneDayShiftByName(2, firstNameOfTM1);
            SimpleUtils.assertOnFail("Get compliance shift failed",shiftsOfWednesday.size()>0, false);

            SimpleUtils.assertOnFail("Clopening compliance message display failed",
                    schedulePage.getComplianceMessageFromInfoIconPopup(shiftsOfTuesday.get(shiftsOfTuesday.size()-1)).contains("Clopening"), false);

            SimpleUtils.assertOnFail("Clopening compliance message display failed",
                    schedulePage.getComplianceMessageFromInfoIconPopup(shiftsOfWednesday.get(0)).contains("Clopening"), false);

            // Swap TM1 and TM2 back, check the TMs been swapped successfully
            schedulePage.clickViewShift();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            i=0;
            while (!schedulePage.isDragAndDropConfirmPageLoaded() && i<5){
                schedulePage.dragOneAvatarToAnotherSpecificAvatar(0,firstNameOfTM2,1,firstNameOfTM1);
                i++;
                Thread.sleep(2000);
            }

            SimpleUtils.assertOnFail("Clopening message is not display because there should no clopening !",
                    !schedulePage.verifySwapAndAssignWarningMessageInConfirmPage(firstNameOfTM1 + clopeningWarningMessage, "swap"), false);
            SimpleUtils.assertOnFail("Clopening message is not display because there should no clopening !",
                    !schedulePage.verifySwapAndAssignWarningMessageInConfirmPage(firstNameOfTM1 + clopeningWarningMessage, "assign"), false);

            schedulePage.selectSwapOrAssignOption("swap");
            schedulePage.clickConfirmBtnOnDragAndDropConfirmPage();
            schedulePage.verifyDayHasShiftByName(0, firstNameOfTM1);
            schedulePage.verifyDayHasShiftByName(1, firstNameOfTM2);
            schedulePage.saveSchedule();
            // Edit the Schedule and try to drag TM1 on Monday to TM2 on Tuesday again
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            i=0;
            while (!schedulePage.isDragAndDropConfirmPageLoaded() && i<5){
                schedulePage.dragOneAvatarToAnotherSpecificAvatar(0,firstNameOfTM1,1,firstNameOfTM2);
                i++;
                Thread.sleep(2000);
            }
            SimpleUtils.assertOnFail("Clopening message display successfully on swap section!",
                    schedulePage.verifySwapAndAssignWarningMessageInConfirmPage(firstNameOfTM1 + clopeningWarningMessage, "swap"), false);
            SimpleUtils.assertOnFail("Clopening message display successfully on assign section!",
                    schedulePage.verifySwapAndAssignWarningMessageInConfirmPage(firstNameOfTM1 + clopeningWarningMessage, "assign"), false);

            // Swap TM1 and TM2, check the TMs been swapped successfully
            schedulePage.selectSwapOrAssignOption("swap");
            schedulePage.clickConfirmBtnOnDragAndDropConfirmPage();
            schedulePage.verifyDayHasShiftByName(1, firstNameOfTM1);
            schedulePage.verifyDayHasShiftByName(0, firstNameOfTM2);
            schedulePage.saveSchedule();

            //check compliance smart card display
            SimpleUtils.assertOnFail("Compliance smart card display successfully!",
                    schedulePage.verifyComplianceShiftsSmartCardShowing(), false);
            schedulePage.clickViewShift();

            //check the violation on the info popup
            shiftsOfTuesday = schedulePage.getOneDayShiftByName(1, firstNameOfTM1);
            SimpleUtils.assertOnFail("Get compliance shift failed",shiftsOfTuesday.size()>0, false);

            shiftsOfWednesday = schedulePage.getOneDayShiftByName(2, firstNameOfTM1);
            SimpleUtils.assertOnFail("Get compliance shift failed",shiftsOfWednesday.size()>0, false);

            SimpleUtils.assertOnFail("Clopening compliance message display failed",
                    schedulePage.getComplianceMessageFromInfoIconPopup(shiftsOfTuesday.get(shiftsOfTuesday.size()-1)).contains("Clopening"), false);

            SimpleUtils.assertOnFail("Clopening compliance message display failed",
                    schedulePage.getComplianceMessageFromInfoIconPopup(shiftsOfWednesday.get(0)).contains("Clopening"), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the box interaction color and message when TM is already scheduled during this time at another location")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyWarningModeWhenTMIsScheduledAtAnotherLocationAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String anotherLocation = "New York Central Park";
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeLocation(anotherLocation);
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }

            // Edit the Schedule
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            List<String> shiftInfo = new ArrayList<>();
            while (shiftInfo.size() ==0) {
                shiftInfo = schedulePage.getTheShiftInfoByIndex(schedulePage.getRandomIndexOfShift());
            }
//        List<String> shiftInfo1 = schedulePage.getTheShiftInfoByIndex(schedulePage.getRandomIndexOfShift());
            String firstNameOfTM1 = shiftInfo.get(0);
            String workRoleOfTM1 = shiftInfo.get(4);
            // Delete all the shifts that are assigned to the team member
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);

            // Create new shift for TM on first day
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            schedulePage.selectSpecificWorkDay(1);
            schedulePage.selectWorkRole(workRoleOfTM1);
            String timeInfo = schedulePage.getTimeDurationWhenCreateNewShift();
            String dayInfo = schedulePage.getSelectedDayInfoFromCreateShiftPage().get(0);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM1);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

//        DashboardPage dashboardPage2 = pageFactory.createConsoleDashboardPage();
            dashboardPage.navigateToDashboard();
            locationSelectorPage.changeLocation("AUSTIN DOWNTOWN");
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
//        SchedulePage schedulePage2 = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            // Create schedule if it is not created
            boolean isWeekGenerated2 = schedulePage.isWeekGenerated();
            if (isWeekGenerated2){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();
            // Edit the Schedule
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);

            // Create new shift for TM on first day
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            schedulePage.selectDaysByIndex(1, 1, 1);
            schedulePage.selectWorkRole(workRoleOfTM1);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM1);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.dragOneShiftToAnotherDay(1, firstNameOfTM1, 0);
            String expectedWarningMesage = firstNameOfTM1+" is already scheduled to work at" +
                    anotherLocation + " at " + timeInfo + " " + dayInfo.replace("\n",", ") +
                    "\n" + "Please contact " +anotherLocation+" to change assignment.";
            String actualwarningMessage = schedulePage.getWarningMessageInDragShiftWarningMode();
            expectedWarningMesage.equalsIgnoreCase(actualwarningMessage);
            schedulePage.clickOnOkButtonInWarningMode();
            List<WebElement> shiftsOfFirstDay= schedulePage.getOneDayShiftByName(0, firstNameOfTM1);
            SimpleUtils.assertOnFail("Get compliance shift failed",shiftsOfFirstDay.size()==0, false);

            List<WebElement> shiftsOfSecondDay = schedulePage.getOneDayShiftByName(1, firstNameOfTM1);
            SimpleUtils.assertOnFail("Get compliance shift failed",shiftsOfSecondDay.size()>0, false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate Assignment rule violation with Yes")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySwapWarningModelForRoleViolationConfigYesAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsPage.gotoControlsPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            SimpleUtils.assertOnFail("Scheduling policy page not loaded successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
            //controlsNewUIPage.clickOnGlobalLocationButton();
            controlsNewUIPage.clickOnSchedulingPoliciesShiftAdvanceBtn();
            controlsNewUIPage.enableOverRideAssignmentRuleAsYes();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            // Navigate to next week
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            // create the schedule if not created
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();
            //edit schedule
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //schedulePage.deleteTMShiftInWeekView("Open");
            schedulePage.deleteTMShiftInWeekView("Unassigned");
            schedulePage.deleteTMShiftInWeekView("open");
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            List<String> shiftInfo = new ArrayList<>();
            while (shiftInfo.size() == 0) {
                shiftInfo = schedulePage.getTheShiftInfoByIndex(0);
            }
            String firstNameOfTM1 = shiftInfo.get(0);
            String workRoleOfTM1 = shiftInfo.get(4);
            List<String> shiftInfo2 = new ArrayList<>();
            while (shiftInfo2.size() == 0 || workRoleOfTM1.equals(shiftInfo2.get(4))) {
                shiftInfo2 = schedulePage.getTheShiftInfoByIndex(schedulePage.getRandomIndexOfShift());
            }
            String firstNameOfTM2 = shiftInfo2.get(0);
            String workRoleOfTM2 = shiftInfo2.get(4);
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM2);
            schedulePage.saveSchedule();

            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole(workRoleOfTM1);
            schedulePage.clearAllSelectedDays();
            schedulePage.selectDaysByIndex(0, 0, 0);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM1);
            SimpleUtils.report("teammember1: "+ firstNameOfTM1);
            schedulePage.clickOnOfferOrAssignBtn();

            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole(workRoleOfTM2);
            schedulePage.clearAllSelectedDays();
            schedulePage.selectDaysByIndex(1, 1, 1);
            schedulePage.moveSliderAtSomePoint("8", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM2);
            SimpleUtils.report("teammember2: "+ firstNameOfTM2);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.dragOneAvatarToAnotherSpecificAvatar(0, firstNameOfTM1, 1, firstNameOfTM2);
            schedulePage.verifyConfirmStoreOpenCloseHours();
            String expectedViolationMessage = firstNameOfTM1+" should not take a "+workRoleOfTM2+" shift";
            schedulePage.verifyMessageInConfirmPage(expectedViolationMessage,expectedViolationMessage);
            List<String> swapData = schedulePage.getShiftSwapDataFromConfirmPage("swap");
            schedulePage.selectSwapOrAssignOption("swap");
            schedulePage.clickConfirmBtnOnDragAndDropConfirmPage();
            schedulePage.verifyShiftsAreSwapped(swapData);

            //assign option
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM2);
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole(workRoleOfTM1);
            schedulePage.clearAllSelectedDays();
            schedulePage.selectDaysByIndex(0, 0, 0);
            schedulePage.moveSliderAtSomePoint("8", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM1);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole(workRoleOfTM2);
            schedulePage.clearAllSelectedDays();
            schedulePage.selectDaysByIndex(1, 1, 1);
            schedulePage.moveSliderAtSomePoint("8", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM2);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.dragOneAvatarToAnotherSpecificAvatar(0, firstNameOfTM1, 1, firstNameOfTM2);
            schedulePage.verifyConfirmStoreOpenCloseHours();
            schedulePage.selectSwapOrAssignOption("assign");
            schedulePage.clickConfirmBtnOnDragAndDropConfirmPage();
            if (schedulePage.verifyDayHasShiftByName(0,firstNameOfTM1)==1 && schedulePage.verifyDayHasShiftByName(1,firstNameOfTM1)==1){
                SimpleUtils.pass("assign successfully!");
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate Assignment rule violation with No")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySwapWarningModelForRoleViolationConfigNoAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsPage.gotoControlsPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            //controlsNewUIPage.clickOnGlobalLocationButton();
            controlsNewUIPage.clickOnSchedulingPoliciesShiftAdvanceBtn();
            controlsNewUIPage.enableOverRideAssignmentRuleAsNo();

            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), true);

            // Navigate to next week
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            // create the schedule if not created
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();
            //edit schedule
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Action Required");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            List<String> shiftInfo = new ArrayList<>();
            while (shiftInfo.size() == 0) {
                shiftInfo = schedulePage.getTheShiftInfoByIndex(0);
            }
            String firstNameOfTM1 = shiftInfo.get(0);
            String workRoleOfTM1 = shiftInfo.get(4);
            List<String> shiftInfo2 = new ArrayList<>();
            while (shiftInfo2.size() == 0 || workRoleOfTM1.equals(shiftInfo2.get(4))) {
                shiftInfo2 = schedulePage.getTheShiftInfoByIndex(schedulePage.getRandomIndexOfShift());
            }
            String firstNameOfTM2 = shiftInfo2.get(0);
            String workRoleOfTM2 = shiftInfo2.get(4);
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM2);
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole(workRoleOfTM2);
            schedulePage.clearAllSelectedDays();
            schedulePage.selectDaysByIndex(1, 1, 1);
            schedulePage.moveSliderAtSomePoint("8", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM2);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.dragOneAvatarToAnotherSpecificAvatar(0, firstNameOfTM1, 1, firstNameOfTM2);
            String expectedViolationMessage ="This assignment will trigger a role violation.\n" +
                    firstNameOfTM1+" "+shiftInfo.get(5)+" can not take a "+workRoleOfTM2+" shift\n";
            String actualwarningMessage = schedulePage.getWarningMessageInDragShiftWarningMode();
            if (expectedViolationMessage.equalsIgnoreCase(actualwarningMessage)){
                SimpleUtils.pass("violation warning message is expected!");
            } else {
                SimpleUtils.fail("violation warning message is not expected! the actual is: " + actualwarningMessage+" expected: "+ expectedViolationMessage,true);
            }
            schedulePage.clickOnOkButtonInWarningMode();
            if (schedulePage.verifyDayHasShiftByName(0,firstNameOfTM1)==1 && schedulePage.verifyDayHasShiftByName(1,firstNameOfTM2)==1){
                SimpleUtils.pass("assign successfully!");
            }
            schedulePage.saveSchedule();

            controlsPage.gotoControlsPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), true);
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            SimpleUtils.assertOnFail("Scheduling policy page not loaded successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), true);
            //controlsNewUIPage.clickOnGlobalLocationButton();
            controlsNewUIPage.clickOnSchedulingPoliciesShiftAdvanceBtn();
            controlsNewUIPage.enableOverRideAssignmentRuleAsYes();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "validate drag and drop to the middle days of the week to copy shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDragDropToAMiddleDayToCopyShiftAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), true);

        // Navigate to next week
        schedulePage.navigateToNextWeek();
        // create the schedule.
        boolean isWeekGenerated = schedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            schedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        schedulePage.createScheduleForNonDGFlowNewUI();
        //edit schedule
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.clickOnFilterBtn();
        schedulePage.selectShiftTypeFilterByText("Action Required");
        schedulePage.deleteTMShiftInWeekView("");
        schedulePage.clickOnFilterBtn();
        schedulePage.clickOnClearFilterOnFilterDropdownPopup();
        schedulePage.saveSchedule();
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

        List<String> shiftInfo = new ArrayList<>();
        while (shiftInfo.size() == 0) {
            shiftInfo = schedulePage.getTheShiftInfoByIndex(0);
        }
        String firstNameOfTM1 = shiftInfo.get(0);
        //String workRoleOfTM1 = shiftInfo.get(4);
        schedulePage.dragOneShiftToAnotherDay(0, firstNameOfTM1, 3 );
        schedulePage.selectCopyOrMoveByOptionName("copy");
        schedulePage.clickConfirmBtnOnDragAndDropConfirmPage();
        schedulePage.verifyShiftIsCopiedToAnotherDay(0,firstNameOfTM1,3);
        schedulePage.saveSchedule();
        schedulePage.verifyShiftIsCopiedToAnotherDay(0,firstNameOfTM1,3);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "validate drag and drop to the middle days of the week to copy shift(blank day)")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDragDropToABlankMiddleDayToCopyShiftAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), true);

        // Navigate to next week
        schedulePage.navigateToNextWeek();
        // create the schedule.
        boolean isWeekGenerated = schedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            schedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        schedulePage.createScheduleForNonDGFlowNewUI();
        //edit schedule
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.clickOnFilterBtn();
        schedulePage.selectShiftTypeFilterByText("Action Required");
        schedulePage.deleteTMShiftInWeekView("");
        schedulePage.clickOnFilterBtn();
        schedulePage.clickOnClearFilterOnFilterDropdownPopup();
        schedulePage.navigateDayViewWithIndex(3);
        schedulePage.deleteAllShiftsInDayView();
        schedulePage.clickOnWeekView();
        schedulePage.saveSchedule();
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

        List<String> shiftInfo = new ArrayList<>();
        while (shiftInfo.size() == 0) {
            shiftInfo = schedulePage.getTheShiftInfoByIndex(0);
        }
        String firstNameOfTM1 = shiftInfo.get(0);
        //String workRoleOfTM1 = shiftInfo.get(4);
        schedulePage.dragOneShiftToAnotherDay(0, firstNameOfTM1, 3 );
        schedulePage.selectCopyOrMoveByOptionName("copy");
        schedulePage.clickConfirmBtnOnDragAndDropConfirmPage();

        schedulePage.verifyShiftIsCopiedToAnotherDay(0,firstNameOfTM1,3);
        schedulePage.saveSchedule();
        schedulePage.verifyShiftIsCopiedToAnotherDay(0,firstNameOfTM1,3);
    }

}
