package com.legion.tests.core;

import com.google.inject.internal.cglib.core.$WeakCacheKey;
import com.legion.pages.*;
import com.legion.pages.core.ConsoleScheduleCommonPage;
import com.legion.pages.core.ConsoleShiftOperatePage;
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
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Select one team member to view profile
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            String userName = teamPage.selectATeamMemberToViewProfile();
            String firstName = userName.contains(" ") ? userName.split(" ")[0] : userName;

            // Go to Schedule page, Schedule tab
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

            // Create schedule if it is not created
            scheduleCommonPage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            // Edit the Schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            // Delete all the shifts that are assigned to the team member on Step #1
            shiftOperatePage.deleteTMShiftInWeekView(firstName);
            String workRole = shiftOperatePage.getRandomWorkRole();

            // Create new shift for this TM on Monday and Tuesday
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            List<Integer> dayIndexes = newShiftPage.selectDaysByCountAndCannotSelectedDate(2, "");
            //newShiftPage.selectWorkRole("MOD");
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstName);
            newShiftPage.clickOnOfferOrAssignBtn();

            // Save the Schedule
            scheduleMainPage.saveSchedule();
            List<Integer> shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(firstName);
            SimpleUtils.assertOnFail("Failed to add two shifts!", shiftIndexes != null && shiftIndexes.size() == 2, false);
            List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(shiftIndexes.get(1));

            // Edit the Schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            // Drag the TM's avatar on Monday to another TM's shift on Tuesday
            scheduleShiftTablePage.dragOneAvatarToAnother(dayIndexes.get(0), firstName, dayIndexes.get(1));

            String weekday = scheduleShiftTablePage.getWeekDayTextByIndex(Integer.parseInt(shiftInfo.get(1)));
            String fullWeekDay = SimpleUtils.getFullWeekDayName(weekday);
            String expectedMessage = shiftInfo.get(0) + " is scheduled " + shiftInfo.get(6).toUpperCase() + " on " + fullWeekDay
                    + ". This shift will be converted to an open shift";
            scheduleShiftTablePage.verifySwapAndAssignWarningMessageInConfirmPage(expectedMessage, "swap");
            scheduleShiftTablePage.verifySwapAndAssignWarningMessageInConfirmPage(expectedMessage, "assign");
            List<String> swapData = scheduleShiftTablePage.getShiftSwapDataFromConfirmPage("swap");
            scheduleShiftTablePage.selectSwapOrAssignOption("swap");
            scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();
            mySchedulePage.verifyShiftsAreSwapped(swapData);

            // Delete the shifts for this TM
            shiftOperatePage.deleteTMShiftInWeekView(firstName);

            // Prepare the shift for this TM again
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            dayIndexes = newShiftPage.selectDaysByCountAndCannotSelectedDate(2, "");
            //newShiftPage.selectWorkRole("MOD");
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstName);
            newShiftPage.clickOnOfferOrAssignBtn();

            // Save the Schedule
            scheduleMainPage.saveSchedule();
            shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(firstName);
            SimpleUtils.assertOnFail("Failed to add the shifts!", shiftIndexes != null && shiftIndexes.size() > 0, false);
            shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(shiftIndexes.get(1));

            // Edit the Schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            // Drag the TM's shift on Monday to another TM's shift on Tuesday
            scheduleShiftTablePage.dragOneShiftToAnotherDay(dayIndexes.get(0), firstName, dayIndexes.get(1));

            // Verify the warning model pops up
            expectedMessage = firstName + " is scheduled " + shiftInfo.get(6) + " on " + fullWeekDay
                    + ". This shift will be converted to an open shift";

            scheduleShiftTablePage.verifyMessageOnCopyMoveConfirmPage(expectedMessage,expectedMessage);
            scheduleShiftTablePage.selectCopyOrMoveByOptionName("Move");
            scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();

            // Verify if Confirm Store opening closing hour window pops up
            scheduleShiftTablePage.verifyConfirmStoreOpenCloseHours();

            if (scheduleShiftTablePage.ifMoveAnywayDialogDisplay()) {
                scheduleShiftTablePage.moveAnywayWhenChangeShift();
            } else {
                SimpleUtils.fail("MOVE ANYWAY dialog failed to load!", false);
            }
            scheduleMainPage.saveSchedule();
            scheduleShiftTablePage.verifyShiftIsMovedToAnotherDay(dayIndexes.get(0), firstName, dayIndexes.get(1));
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
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
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
            controlsNewUIPage.clickOnGlobalLocationButton();
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.setWFS("Yes");

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
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

            // Create schedule if it is not created
            scheduleCommonPage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            // Edit the Schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            // Delete all the shifts that are assigned to the team member on Step #1
            shiftOperatePage.deleteTMShiftInWeekView(firstName);
            String workRole = shiftOperatePage.getRandomWorkRole();

            // Create new shift for this TM on Monday and Tuesday
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            List<Integer> dayIndexes = newShiftPage.selectDaysByCountAndCannotSelectedDate(2, "");
            //newShiftPage.selectWorkRole("MOD");
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstName);
            newShiftPage.clickOnOfferOrAssignBtn();
            shiftOperatePage.deleteTMShiftInWeekView("Unassigned");

            // Save the Schedule
            scheduleMainPage.saveSchedule();
            List<Integer> shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(firstName);
            SimpleUtils.assertOnFail("Failed to add two shifts!", shiftIndexes != null && shiftIndexes.size() > 0, false);
            List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(shiftIndexes.get(1));

            // Edit the Schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            // Drag the TM's avatar on Monday to another TM's shift on Tuesday
            scheduleShiftTablePage.dragOneAvatarToAnother(dayIndexes.get(0), firstName, dayIndexes.get(1));

            String weekday = scheduleShiftTablePage.getWeekDayTextByIndex(Integer.parseInt(shiftInfo.get(1)));
            String fullWeekDay = SimpleUtils.getFullWeekDayName(weekday);
            String expectedMessage = shiftInfo.get(0) + " is scheduled " + shiftInfo.get(6).toUpperCase() + " on " + fullWeekDay
                    + ". This shift will be converted to an open shift";
            scheduleShiftTablePage.verifySwapAndAssignWarningMessageInConfirmPage(expectedMessage, "swap");
            scheduleShiftTablePage.verifySwapAndAssignWarningMessageInConfirmPage(expectedMessage, "assign");
            List<String> swapData = scheduleShiftTablePage.getShiftSwapDataFromConfirmPage("swap");
            scheduleShiftTablePage.selectSwapOrAssignOption("swap");
            scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();
            mySchedulePage.verifyShiftsAreSwapped(swapData);
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
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
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
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

            // Navigate to the week that contains the date that provided
            scheduleCommonPage.goToSpecificWeekByDate(timeOffDate);

            // Ungenerate and create the schedule
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            // Edit schedule to create the new shift for new TM
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.deleteTMShiftInWeekView(firstName);
            String workRole = shiftOperatePage.getRandomWorkRole();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            List<Integer> indexes = newShiftPage.selectDaysByCountAndCannotSelectedDate(1, timeOffDate);
            //newShiftPage.selectWorkRole("MOD");
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstName + " " + lastName.substring(0, 1));
            newShiftPage.clickOnOfferOrAssignBtn();
            scheduleMainPage.saveSchedule();

            // Drag the shift to the time off day
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            int endIndex = scheduleShiftTablePage.getTheIndexOfTheDayInWeekView(timeOffDate.substring(timeOffDate.length() - 2));
            scheduleShiftTablePage.dragOneAvatarToAnother(indexes.get(0), firstName, endIndex);

            // Verify the warning model pops up and Click on OK button
            shiftOperatePage.verifyWarningModelForAssignTMOnTimeOff(firstName);

            // Drag the TM's shift to the day that he/she has time off
            scheduleShiftTablePage.dragOneShiftToAnotherDay(indexes.get(0), firstName, endIndex);

            // Verify the Warning model pops up with the message
            String expectedMsg = firstName + " is approved for Time Off";
            scheduleShiftTablePage.verifyMessageOnCopyMoveConfirmPage(expectedMsg, expectedMsg);
            String copyOption = "Copy";
            String moveOption = "Move";
            scheduleShiftTablePage.verifyConfirmBtnIsDisabledForSpecificOption(copyOption);
            scheduleShiftTablePage.verifyConfirmBtnIsDisabledForSpecificOption(moveOption);
            shiftOperatePage.clickOnCancelEditShiftTimeButton();

            // Verify nothing happens after clicking CANCEL button
            if (scheduleShiftTablePage.verifyDayHasShiftByName(indexes.get(0), firstName) == 1 && scheduleShiftTablePage.verifyDayHasShiftByName(endIndex, firstName) == 0)
                SimpleUtils.pass("Nothing happens as expected after clicking OK button");
            else
                SimpleUtils.fail("The TM's shift may be assigned unexpected", false);
            scheduleMainPage.saveSchedule();

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
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
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
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()) , false);

            // Navigate to next week
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            // recreate the schedule
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            // Edit schedule to create the new shifts for new TM1 and TM2
            //String TM1 = "John";
            //String TM2 = "Pat";
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.deleteTMShiftInWeekView(TM1);
            shiftOperatePage.deleteTMShiftInWeekView(TM2);
            String workRole = shiftOperatePage.getRandomWorkRole();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            //newShiftPage.selectWorkRole("EVENT MANAGER");
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectSpecificWorkDay(2);
            newShiftPage.moveSliderAtSomePoint("8", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(TM1);
            newShiftPage.clickOnOfferOrAssignBtn();

            newShiftPage.clickOnDayViewAddNewShiftButton();
            //newShiftPage.selectWorkRole("EVENT MANAGER");
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectSpecificWorkDay(1);
            newShiftPage.moveSliderAtSomePoint("8", 10, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("8", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(TM2);
            newShiftPage.clickOnOfferOrAssignBtn();
            scheduleMainPage.saveSchedule();

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.dragOneAvatarToAnotherSpecificAvatar(1,TM1,0,TM2);
            List<String> swapData = scheduleShiftTablePage.getShiftSwapDataFromConfirmPage("swap");
            String expectedMessage = TM1+" will incur 1 hours of overtime";
            SimpleUtils.report(expectedMessage);
            scheduleShiftTablePage.verifyMessageInConfirmPage(expectedMessage, expectedMessage);
            scheduleShiftTablePage.selectSwapOrAssignOption("swap");
            scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();
            mySchedulePage.verifyShiftsAreSwapped(swapData);

            //verify cancel button
            scheduleShiftTablePage.dragOneAvatarToAnotherSpecificAvatar(1,TM2,0,TM1);
            scheduleShiftTablePage.selectSwapOrAssignOption("swap");
            scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();
            scheduleShiftTablePage.dragOneAvatarToAnotherSpecificAvatar(1,TM1,0,TM2);
            scheduleShiftTablePage.clickCancelBtnOnDragAndDropConfirmPage();
            if (scheduleShiftTablePage.verifyDayHasShiftByName(0,TM1)==1 && scheduleShiftTablePage.verifyDayHasShiftByName(1,TM1)==1){
                SimpleUtils.pass("cancel successfully!");
            }

            scheduleShiftTablePage.dragOneAvatarToAnotherSpecificAvatar(1,TM1,0,TM2);
            scheduleShiftTablePage.selectSwapOrAssignOption("assign");
            scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();
            if (scheduleShiftTablePage.verifyDayHasShiftByName(0,TM1)==2 && scheduleShiftTablePage.verifyDayHasShiftByName(1,TM1)==1){
                SimpleUtils.pass("assign successfully!");
            }
            shiftOperatePage.deleteTMShiftInWeekView(TM1);
            shiftOperatePage.deleteTMShiftInWeekView(TM2);
            scheduleMainPage.saveSchedule();

            //verify change shift
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            //newShiftPage.selectWorkRole("EVENT MANAGER");
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.clearAllSelectedDays();
            //newShiftPage.selectDaysByIndex(0, 0, 2);
            newShiftPage.selectSpecificWorkDay(1);
            newShiftPage.moveSliderAtSomePoint("8", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(TM1);
            newShiftPage.clickOnOfferOrAssignBtn();

            newShiftPage.clickOnDayViewAddNewShiftButton();
            //newShiftPage.selectWorkRole("EVENT MANAGER");
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectDaysByIndex(1, 1, 1);
            //newShiftPage.selectSpecificWorkDay(1);
            newShiftPage.moveSliderAtSomePoint("8", 10, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("8", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(TM1);
            newShiftPage.clickOnOfferOrAssignBtn();
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.dragOneShiftToAnotherDay(1,TM1,0);
            if (scheduleShiftTablePage.ifMoveAnywayDialogDisplay()){
                scheduleShiftTablePage.moveAnywayWhenChangeShift();
            }
            scheduleMainPage.saveSchedule();
            expectedMessage = "1 hrs daily overtime";
            List<WebElement> shiftsOfFirstDay = scheduleShiftTablePage.getOneDayShiftByName(0, TM1);
            SimpleUtils.assertOnFail("Get "+TM1+"'s shift failed",shiftsOfFirstDay.size()>0, false);
            String actualMessage=null;
            for (String s:scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup(shiftsOfFirstDay.get(shiftsOfFirstDay.size()-1))){
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
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Select one team member to view profile
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            String userName = teamPage.selectATeamMemberToViewProfile();
            String firstName = userName.contains(" ") ? userName.split(" ")[0] : userName;

            // Go to Schedule page, Schedule tab, navigate to next week
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
            scheduleCommonPage.navigateToNextWeek();

            // Create schedule if it is not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated){
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            // Edit the Schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            // Delete all the shifts that are assigned to the team member on Step #1
            shiftOperatePage.deleteTMShiftInWeekView(firstName);
            shiftOperatePage.deleteTMShiftInWeekView("Open");
            String workRole = shiftOperatePage.getRandomWorkRole();

            // Create 2 new shifts for this TM on Monday and Tuesday
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            List<Integer> dayIndexes = newShiftPage.selectDaysByCountAndCannotSelectedDate(2, "");
            //newShiftPage.selectWorkRole("MOD");
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstName);
            newShiftPage.clickOnOfferOrAssignBtn();

            // Create an open shift on Tuesday
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            if (dayIndexes.size() == 2)
                newShiftPage.selectWorkingDaysOnNewShiftPageByIndex(dayIndexes.get(1));
            else
                newShiftPage.selectWorkingDaysOnNewShiftPageByIndex(1);
            //newShiftPage.selectWorkRole("MOD");
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();

            // Save the Schedule
            scheduleMainPage.saveSchedule();
            List<Integer> shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(firstName);
            SimpleUtils.assertOnFail("Failed to add two shifts for the TM!", shiftIndexes != null && shiftIndexes.size() == 2, false);
            List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(shiftIndexes.get(1));
            List<Integer> openShiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes("Open");
            SimpleUtils.assertOnFail("Failed to add an open shift!", openShiftIndexes != null && openShiftIndexes.size() == 1, false);
            List<String> openShiftInfo = scheduleShiftTablePage.getOpenShiftInfoByIndex(openShiftIndexes.get(0));

            // Edit the Schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            // Drag the TM's avatar on Monday to the open shift on Tuesday
            scheduleShiftTablePage.dragOneAvatarToAnotherSpecificAvatar(0,firstName,1,"Open");
            String weekday = scheduleShiftTablePage.getWeekDayTextByIndex(Integer.parseInt(shiftInfo.get(1)));
            String fullWeekDay = SimpleUtils.getFullWeekDayName(weekday);
            String expectedMessage = shiftInfo.get(0) + " is scheduled " + shiftInfo.get(6).toUpperCase() + " on " + fullWeekDay
                    + ". This shift will be converted to an open shift";
            scheduleShiftTablePage.verifySwapAndAssignWarningMessageInConfirmPage(expectedMessage,"assign");
            scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();

            // Verify the shift is assigned
            List<String> shiftInfoAfterDrag = scheduleShiftTablePage.getTheShiftInfoByIndex(shiftIndexes.get(1));
            List<String> openShiftInfoAfterDrag = scheduleShiftTablePage.getOpenShiftInfoByIndex(openShiftIndexes.get(0));

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
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
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
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

            // Create schedule if it is not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }

            scheduleCommonPage.navigateToNextWeek();
            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( "09:00AM", "11:00PM");
            List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getRandomIndexOfShift());
            String firstNameOfTM1 = shiftInfo.get(0);
            while (firstNameOfTM1.equalsIgnoreCase("open")) {
                shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getRandomIndexOfShift());
                firstNameOfTM1  = shiftInfo.get(0);
            }
            String workRoleOfTM1 = shiftInfo.get(4);
            while (shiftInfo.get(0).equalsIgnoreCase(firstNameOfTM1) || shiftInfo.get(0).equalsIgnoreCase("open")) {
                shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getRandomIndexOfShift());
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
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
            scheduleCommonPage.navigateToNextWeek();
            // Edit the Schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            // Delete all the shifts that are assigned to the team member
            shiftOperatePage.deleteTMShiftInWeekView(firstNameOfTM1);
            shiftOperatePage.deleteTMShiftInWeekView(firstNameOfTM2);

            // Create new shift for TM1 on Monday and Wednesday
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectDaysByIndex(0, 0, 2);
            newShiftPage.selectWorkRole(workRoleOfTM1);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstNameOfTM1);
            newShiftPage.clickOnOfferOrAssignBtn();

            //Create new shift for TM2 on Tuesday
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME_2"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount3.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME_2"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectDaysByIndex(1, 1, 1);
            newShiftPage.selectWorkRole(workRoleOfTM2);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstNameOfTM2);
            newShiftPage.clickOnOfferOrAssignBtn();

            // Save the Schedule
            scheduleMainPage.saveSchedule();

            // Edit the Schedule and try to drag TM1 on Monday to TM2 on Tuesday
            String clopeningWarningMessage = " will incur clopening";
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            int i=0;
            while (!scheduleShiftTablePage.isDragAndDropConfirmPageLoaded() && i<5){
                scheduleShiftTablePage.dragOneAvatarToAnotherSpecificAvatar(0,firstNameOfTM1,1,firstNameOfTM2);
                i++;
                Thread.sleep(2000);
            }
            SimpleUtils.assertOnFail("Clopening message display incorrectly on swap section!",
                    scheduleShiftTablePage.verifySwapAndAssignWarningMessageInConfirmPage(firstNameOfTM1 + clopeningWarningMessage, "swap"), false);
            SimpleUtils.assertOnFail("Clopening message display incorrectly on assign section!",
                    scheduleShiftTablePage.verifySwapAndAssignWarningMessageInConfirmPage(firstNameOfTM1 + clopeningWarningMessage, "assign"), false);

            // Swap TM1 and TM2, check the TMs been swapped successfully
            scheduleShiftTablePage.selectSwapOrAssignOption("swap");
            scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();
            scheduleShiftTablePage.verifyDayHasShiftByName(1, firstNameOfTM1);
            scheduleShiftTablePage.verifyDayHasShiftByName(0, firstNameOfTM2);
            scheduleMainPage.saveSchedule();
            //check compliance smart card display
            SimpleUtils.assertOnFail("Compliance smart card display successfully!",
                    smartCardPage.verifyComplianceShiftsSmartCardShowing(), false);
            smartCardPage.clickViewShift();

            //check the violation on the info popup
            List<WebElement> shiftsOfTuesday = scheduleShiftTablePage.getOneDayShiftByName(1, firstNameOfTM1);
            SimpleUtils.assertOnFail("Get compliance shift failed",shiftsOfTuesday.size()>0, false);

            List<WebElement> shiftsOfWednesday = scheduleShiftTablePage.getOneDayShiftByName(2, firstNameOfTM1);
            SimpleUtils.assertOnFail("Get compliance shift failed",shiftsOfWednesday.size()>0, false);

            SimpleUtils.assertOnFail("Clopening compliance message display failed",
                    scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup(shiftsOfTuesday.get(shiftsOfTuesday.size()-1)).contains("Clopening"), false);

            SimpleUtils.assertOnFail("Clopening compliance message display failed",
                    scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup(shiftsOfWednesday.get(0)).contains("Clopening"), false);

            // Swap TM1 and TM2 back, check the TMs been swapped successfully
            smartCardPage.clickViewShift();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            i=0;
            while (!scheduleShiftTablePage.isDragAndDropConfirmPageLoaded() && i<5){
                scheduleShiftTablePage.dragOneAvatarToAnotherSpecificAvatar(0,firstNameOfTM2,1,firstNameOfTM1);
                i++;
                Thread.sleep(2000);
            }

            SimpleUtils.assertOnFail("Clopening message is not display because there should no clopening !",
                    !scheduleShiftTablePage.verifySwapAndAssignWarningMessageInConfirmPage(firstNameOfTM1 + clopeningWarningMessage, "swap"), false);
            SimpleUtils.assertOnFail("Clopening message is not display because there should no clopening !",
                    !scheduleShiftTablePage.verifySwapAndAssignWarningMessageInConfirmPage(firstNameOfTM1 + clopeningWarningMessage, "assign"), false);

            scheduleShiftTablePage.selectSwapOrAssignOption("swap");
            scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();
            scheduleShiftTablePage.verifyDayHasShiftByName(0, firstNameOfTM1);
            scheduleShiftTablePage.verifyDayHasShiftByName(1, firstNameOfTM2);
            scheduleMainPage.saveSchedule();
            // Edit the Schedule and try to drag TM1 on Monday to TM2 on Tuesday again
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            i=0;
            while (!scheduleShiftTablePage.isDragAndDropConfirmPageLoaded() && i<5){
                scheduleShiftTablePage.dragOneAvatarToAnotherSpecificAvatar(0,firstNameOfTM1,1,firstNameOfTM2);
                i++;
                Thread.sleep(2000);
            }
            SimpleUtils.assertOnFail("Clopening message display successfully on swap section!",
                    scheduleShiftTablePage.verifySwapAndAssignWarningMessageInConfirmPage(firstNameOfTM1 + clopeningWarningMessage, "swap"), false);
            SimpleUtils.assertOnFail("Clopening message display successfully on assign section!",
                    scheduleShiftTablePage.verifySwapAndAssignWarningMessageInConfirmPage(firstNameOfTM1 + clopeningWarningMessage, "assign"), false);

            // Swap TM1 and TM2, check the TMs been swapped successfully
            scheduleShiftTablePage.selectSwapOrAssignOption("swap");
            scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();
            scheduleShiftTablePage.verifyDayHasShiftByName(1, firstNameOfTM1);
            scheduleShiftTablePage.verifyDayHasShiftByName(0, firstNameOfTM2);
            scheduleMainPage.saveSchedule();

            //check compliance smart card display
            SimpleUtils.assertOnFail("Compliance smart card display successfully!",
                    smartCardPage.verifyComplianceShiftsSmartCardShowing(), false);
            smartCardPage.clickViewShift();

            //check the violation on the info popup
            shiftsOfTuesday = scheduleShiftTablePage.getOneDayShiftByName(1, firstNameOfTM1);
            SimpleUtils.assertOnFail("Get compliance shift failed",shiftsOfTuesday.size()>0, false);

            shiftsOfWednesday = scheduleShiftTablePage.getOneDayShiftByName(2, firstNameOfTM1);
            SimpleUtils.assertOnFail("Get compliance shift failed",shiftsOfWednesday.size()>0, false);

            SimpleUtils.assertOnFail("Clopening compliance message display failed",
                    scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup(shiftsOfTuesday.get(shiftsOfTuesday.size()-1)).contains("Clopening"), false);

            SimpleUtils.assertOnFail("Clopening compliance message display failed",
                    scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup(shiftsOfWednesday.get(0)).contains("Clopening"), false);
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
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String anotherLocation = "New York Central Park";
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeLocation(anotherLocation);
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated){
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            // Edit the Schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            List<String> shiftInfo = new ArrayList<>();
            while (shiftInfo.size() ==0) {
                shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getRandomIndexOfShift());
            }
//        List<String> shiftInfo1 = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getRandomIndexOfShift());
            String firstNameOfTM1 = shiftInfo.get(0);
            String workRoleOfTM1 = shiftInfo.get(4);
            // Delete all the shifts that are assigned to the team member
            shiftOperatePage.deleteTMShiftInWeekView(firstNameOfTM1);

            // Create new shift for TM on first day
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectSpecificWorkDay(1);
            newShiftPage.selectWorkRole(workRoleOfTM1);
            String timeInfo = newShiftPage.getTimeDurationWhenCreateNewShift();
            String dayInfo = newShiftPage.getSelectedDayInfoFromCreateShiftPage().get(0);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstNameOfTM1);
            newShiftPage.clickOnOfferOrAssignBtn();
            scheduleMainPage.saveSchedule();
            createSchedulePage.publishActiveSchedule();

//        DashboardPage dashboardPage2 = pageFactory.createConsoleDashboardPage();
            dashboardPage.navigateToDashboard();
            locationSelectorPage.changeLocation("AUSTIN DOWNTOWN");
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
//        SchedulePage schedulePage2 = pageFactory.createConsoleScheduleNewUIPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

            // Create schedule if it is not created
            boolean isWeekGenerated2 = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated2){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            // Edit the Schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.deleteTMShiftInWeekView(firstNameOfTM1);

            // Create new shift for TM on first day
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectDaysByIndex(1, 1, 1);
            newShiftPage.selectWorkRole(workRoleOfTM1);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstNameOfTM1);
            newShiftPage.clickOnOfferOrAssignBtn();
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.dragOneShiftToAnotherDay(1, firstNameOfTM1, 0);
            String expectedWarningMesage = firstNameOfTM1+" is already scheduled to work at" +
                    anotherLocation + " at " + timeInfo + " " + dayInfo.replace("\n",", ") +
                    "\n" + "Please contact " +anotherLocation+" to change assignment.";
            String actualwarningMessage = scheduleShiftTablePage.getWarningMessageInDragShiftWarningMode();
            expectedWarningMesage.equalsIgnoreCase(actualwarningMessage);
            scheduleShiftTablePage.clickOnOkButtonInWarningMode();
            List<WebElement> shiftsOfFirstDay= scheduleShiftTablePage.getOneDayShiftByName(0, firstNameOfTM1);
            SimpleUtils.assertOnFail("Get compliance shift failed",shiftsOfFirstDay.size()==0, false);

            List<WebElement> shiftsOfSecondDay = scheduleShiftTablePage.getOneDayShiftByName(1, firstNameOfTM1);
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
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
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
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

            // Navigate to next week
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            // create the schedule if not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            //edit schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //shiftOperatePage.deleteTMShiftInWeekView("Open");
            shiftOperatePage.deleteTMShiftInWeekView("Unassigned");
            shiftOperatePage.deleteTMShiftInWeekView("open");
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            List<String> shiftInfo = new ArrayList<>();
            while (shiftInfo.size() == 0) {
                shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
            }
            String firstNameOfTM1 = shiftInfo.get(0);
            String workRoleOfTM1 = shiftInfo.get(4);
            List<String> shiftInfo2 = new ArrayList<>();
            while (shiftInfo2.size() == 0 || workRoleOfTM1.equals(shiftInfo2.get(4))) {
                shiftInfo2 = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getRandomIndexOfShift());
            }
            String firstNameOfTM2 = shiftInfo2.get(0);
            String workRoleOfTM2 = shiftInfo2.get(4);
            shiftOperatePage.deleteTMShiftInWeekView(firstNameOfTM1);
            shiftOperatePage.deleteTMShiftInWeekView(firstNameOfTM2);
            scheduleMainPage.saveSchedule();

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRoleOfTM1);
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectDaysByIndex(0, 0, 0);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstNameOfTM1);
            SimpleUtils.report("teammember1: "+ firstNameOfTM1);
            newShiftPage.clickOnOfferOrAssignBtn();

            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRoleOfTM2);
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectDaysByIndex(1, 1, 1);
            newShiftPage.moveSliderAtSomePoint("8", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstNameOfTM2);
            SimpleUtils.report("teammember2: "+ firstNameOfTM2);
            newShiftPage.clickOnOfferOrAssignBtn();
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.dragOneAvatarToAnotherSpecificAvatar(0, firstNameOfTM1, 1, firstNameOfTM2);
            scheduleShiftTablePage.verifyConfirmStoreOpenCloseHours();
            String expectedViolationMessage = firstNameOfTM1+" should not take a "+workRoleOfTM2+" shift";
            scheduleShiftTablePage.verifyMessageInConfirmPage(expectedViolationMessage,expectedViolationMessage);
            List<String> swapData = scheduleShiftTablePage.getShiftSwapDataFromConfirmPage("swap");
            scheduleShiftTablePage.selectSwapOrAssignOption("swap");
            scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();
            mySchedulePage.verifyShiftsAreSwapped(swapData);

            //assign option
            shiftOperatePage.deleteTMShiftInWeekView(firstNameOfTM1);
            shiftOperatePage.deleteTMShiftInWeekView(firstNameOfTM2);
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRoleOfTM1);
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectDaysByIndex(0, 0, 0);
            newShiftPage.moveSliderAtSomePoint("8", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstNameOfTM1);
            newShiftPage.clickOnOfferOrAssignBtn();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRoleOfTM2);
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectDaysByIndex(1, 1, 1);
            newShiftPage.moveSliderAtSomePoint("8", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstNameOfTM2);
            newShiftPage.clickOnOfferOrAssignBtn();
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.dragOneAvatarToAnotherSpecificAvatar(0, firstNameOfTM1, 1, firstNameOfTM2);
            scheduleShiftTablePage.verifyConfirmStoreOpenCloseHours();
            scheduleShiftTablePage.selectSwapOrAssignOption("assign");
            scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();
            if (scheduleShiftTablePage.verifyDayHasShiftByName(0,firstNameOfTM1)==1 && scheduleShiftTablePage.verifyDayHasShiftByName(1,firstNameOfTM1)==1){
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
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
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
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

            // Navigate to next week
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            // create the schedule if not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            //edit schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Action Required");
            shiftOperatePage.deleteTMShiftInWeekView("");
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Open");
            shiftOperatePage.deleteTMShiftInWeekView("");
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            List<String> shiftInfo = new ArrayList<>();
            while (shiftInfo.size() == 0) {
                shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
            }
            String firstNameOfTM1 = shiftInfo.get(0);
            String workRoleOfTM1 = shiftInfo.get(4);
            List<String> shiftInfo2 = new ArrayList<>();
            while (shiftInfo2.size() == 0 || workRoleOfTM1.equals(shiftInfo2.get(4))) {
                shiftInfo2 = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getRandomIndexOfShift());
            }
            String firstNameOfTM2 = shiftInfo2.get(0);
            String workRoleOfTM2 = shiftInfo2.get(4);
            shiftOperatePage.deleteTMShiftInWeekView(firstNameOfTM2);
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRoleOfTM2);
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectDaysByIndex(1, 1, 1);
            newShiftPage.moveSliderAtSomePoint("8", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstNameOfTM2);
            newShiftPage.clickOnOfferOrAssignBtn();
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.dragOneAvatarToAnotherSpecificAvatar(0, firstNameOfTM1, 1, firstNameOfTM2);
            String expectedViolationMessage ="This assignment will trigger a role violation.\n" +
                    firstNameOfTM1+" "+shiftInfo.get(5)+" can not take a "+workRoleOfTM2+" shift\n";
            String actualwarningMessage = scheduleShiftTablePage.getWarningMessageInDragShiftWarningMode();
            if (expectedViolationMessage.equalsIgnoreCase(actualwarningMessage)){
                SimpleUtils.pass("violation warning message is expected!");
            } else {
                SimpleUtils.fail("violation warning message is not expected! the actual is: " + actualwarningMessage+" expected: "+ expectedViolationMessage,true);
            }
            scheduleShiftTablePage.clickOnOkButtonInWarningMode();
            if (scheduleShiftTablePage.verifyDayHasShiftByName(0,firstNameOfTM1)==1 && scheduleShiftTablePage.verifyDayHasShiftByName(1,firstNameOfTM2)==1){
                SimpleUtils.pass("assign successfully!");
            }
            scheduleMainPage.saveSchedule();

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
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

        // Navigate to next week
        scheduleCommonPage.navigateToNextWeek();
        // create the schedule.
        boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();
        //edit schedule
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.selectShiftTypeFilterByText("Action Required");
        shiftOperatePage.deleteTMShiftInWeekView("");
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
        scheduleMainPage.saveSchedule();
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

        List<String> shiftInfo = new ArrayList<>();
        while (shiftInfo.size() == 0) {
            shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
        }
        String firstNameOfTM1 = shiftInfo.get(0);
        //String workRoleOfTM1 = shiftInfo.get(4);
        scheduleShiftTablePage.dragOneShiftToAnotherDay(0, firstNameOfTM1, 3 );
        scheduleShiftTablePage.selectCopyOrMoveByOptionName("copy");
        scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();
        scheduleShiftTablePage.verifyShiftIsCopiedToAnotherDay(0,firstNameOfTM1,3);
        scheduleMainPage.saveSchedule();
        scheduleShiftTablePage.verifyShiftIsCopiedToAnotherDay(0,firstNameOfTM1,3);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "validate drag and drop to the middle days of the week to copy shift(blank day)")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDragDropToABlankMiddleDayToCopyShiftAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

        // Navigate to next week
        scheduleCommonPage.navigateToNextWeek();
        // create the schedule.
        boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();
        //edit schedule
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.selectShiftTypeFilterByText("Action Required");
        shiftOperatePage.deleteTMShiftInWeekView("");
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
        scheduleCommonPage.navigateDayViewWithIndex(3);
        shiftOperatePage.deleteAllShiftsInDayView();
        scheduleCommonPage.clickOnWeekView();
        scheduleMainPage.saveSchedule();
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

        List<String> shiftInfo = new ArrayList<>();
        while (shiftInfo.size() == 0) {
            shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
        }
        String firstNameOfTM1 = shiftInfo.get(0);
        //String workRoleOfTM1 = shiftInfo.get(4);
        scheduleShiftTablePage.dragOneShiftToAnotherDay(0, firstNameOfTM1, 3 );
        scheduleShiftTablePage.selectCopyOrMoveByOptionName("copy");
        scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();

        scheduleShiftTablePage.verifyShiftIsCopiedToAnotherDay(0,firstNameOfTM1,3);
        scheduleMainPage.saveSchedule();
        scheduleShiftTablePage.verifyShiftIsCopiedToAnotherDay(0,firstNameOfTM1,3);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "validate drag and drop to the middle days of the week to move shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDragDropToAMiddleDayToMoveShiftAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

        // Navigate to next week
        scheduleCommonPage.navigateToNextWeek();
        // create the schedule.
        boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();
        //edit schedule
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.selectShiftTypeFilterByText("Action Required");
        shiftOperatePage.deleteTMShiftInWeekView("");
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
        scheduleMainPage.saveSchedule();
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

        List<String> shiftInfo = new ArrayList<>();
        while (shiftInfo.size() == 0) {
            shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
        }
        String firstNameOfTM1 = shiftInfo.get(0);
        //String workRoleOfTM1 = shiftInfo.get(4);
        scheduleShiftTablePage.dragOneShiftToAnotherDay(0, firstNameOfTM1, 3 );
        scheduleShiftTablePage.selectCopyOrMoveByOptionName("move");
        scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();
        scheduleShiftTablePage.verifyShiftIsMovedToAnotherDay(0,firstNameOfTM1,3);
        scheduleMainPage.saveSchedule();
        scheduleShiftTablePage.verifyShiftIsMovedToAnotherDay(0,firstNameOfTM1,3);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "validate drag and drop to the middle days of the week to move shift(blank day)")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDragDropToABlankMiddleDayToMoveShiftAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

        // Navigate to next week
        scheduleCommonPage.navigateToNextWeek();
        // create the schedule.
        boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();
        //edit schedule
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.selectShiftTypeFilterByText("Action Required");
        shiftOperatePage.deleteTMShiftInWeekView("");
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
        scheduleCommonPage.navigateDayViewWithIndex(3);
        shiftOperatePage.deleteAllShiftsInDayView();
        scheduleCommonPage.clickOnWeekView();
        scheduleMainPage.saveSchedule();
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

        List<String> shiftInfo = new ArrayList<>();
        while (shiftInfo.size() == 0) {
            shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
        }
        String firstNameOfTM1 = shiftInfo.get(0);
        //String workRoleOfTM1 = shiftInfo.get(4);
        scheduleShiftTablePage.dragOneShiftToAnotherDay(0, firstNameOfTM1, 3 );
        scheduleShiftTablePage.selectCopyOrMoveByOptionName("move");
        scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();

        scheduleShiftTablePage.verifyShiftIsMovedToAnotherDay(0,firstNameOfTM1,3);
        scheduleMainPage.saveSchedule();
        scheduleShiftTablePage.verifyShiftIsMovedToAnotherDay(0,firstNameOfTM1,3);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "validate drag and drop to the first day of the week to copy shift(not blank day)")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDragDropToTheFirstDayToCopyShiftAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

        // Navigate to next week
        scheduleCommonPage.navigateToNextWeek();
        // create the schedule.
        boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();
        //edit schedule
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.selectShiftTypeFilterByText("Action Required");
        shiftOperatePage.deleteTMShiftInWeekView("");
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
        scheduleMainPage.saveSchedule();
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

        String firstNameOfTM1 = scheduleShiftTablePage.getNameOfTheFirstShiftInADay(6);;
        scheduleShiftTablePage.dragOneShiftToAnotherDay(6, firstNameOfTM1, 0 );
        scheduleShiftTablePage.selectCopyOrMoveByOptionName("copy");
        scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();
        scheduleShiftTablePage.verifyShiftIsCopiedToAnotherDay(6,firstNameOfTM1,0);
        scheduleMainPage.saveSchedule();
        scheduleShiftTablePage.verifyShiftIsCopiedToAnotherDay(6,firstNameOfTM1,0);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "validate drag and drop to the first day of the week to copy shift(blank day)")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDragDropToTheBlankFirstDayToCopyShiftAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

        // Navigate to next week
        scheduleCommonPage.navigateToNextWeek();
        // create the schedule.
        boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();
        //edit schedule
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.selectShiftTypeFilterByText("Action Required");
        shiftOperatePage.deleteTMShiftInWeekView("");
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
        scheduleCommonPage.navigateDayViewWithIndex(0);
        shiftOperatePage.deleteAllShiftsInDayView();
        scheduleCommonPage.clickOnWeekView();
        scheduleMainPage.saveSchedule();

        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        String firstNameOfTM1 = scheduleShiftTablePage.getNameOfTheFirstShiftInADay(6);
        scheduleShiftTablePage.dragOneShiftToAnotherDay(6, firstNameOfTM1, 0 );
        scheduleShiftTablePage.selectCopyOrMoveByOptionName("copy");
        scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();

        scheduleShiftTablePage.verifyShiftIsCopiedToAnotherDay(6,firstNameOfTM1,0);
        scheduleMainPage.saveSchedule();
        scheduleShiftTablePage.verifyShiftIsCopiedToAnotherDay(6,firstNameOfTM1,0);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "validate drag and drop to the first day of the week to move shift(not blank day)")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDragDropToTheFirstDayToMoveShiftAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

        // Navigate to next week
        scheduleCommonPage.navigateToNextWeek();
        // create the schedule.
        boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();
        //edit schedule
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.selectShiftTypeFilterByText("Action Required");
        shiftOperatePage.deleteTMShiftInWeekView("");
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
        scheduleMainPage.saveSchedule();
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

        String firstNameOfTM1 = scheduleShiftTablePage.getNameOfTheFirstShiftInADay(6);;
        scheduleShiftTablePage.dragOneShiftToAnotherDay(6, firstNameOfTM1, 0 );
        scheduleShiftTablePage.selectCopyOrMoveByOptionName("move");
        scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();
        scheduleShiftTablePage.verifyShiftIsMovedToAnotherDay(6,firstNameOfTM1,0);
        scheduleMainPage.saveSchedule();
        scheduleShiftTablePage.verifyShiftIsMovedToAnotherDay(6,firstNameOfTM1,0);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "validate drag and drop to the first day of the week to move shift(blank day)")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDragDropToTheBlankFirstDayToMoveShiftAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

        // Navigate to next week
        scheduleCommonPage.navigateToNextWeek();
        // create the schedule.
        boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();
        //edit schedule
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.selectShiftTypeFilterByText("Action Required");
        shiftOperatePage.deleteTMShiftInWeekView("");
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
        scheduleCommonPage.navigateDayViewWithIndex(0);
        shiftOperatePage.deleteAllShiftsInDayView();
        scheduleCommonPage.clickOnWeekView();
        scheduleMainPage.saveSchedule();

        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        String firstNameOfTM1 = scheduleShiftTablePage.getNameOfTheFirstShiftInADay(6);
        scheduleShiftTablePage.dragOneShiftToAnotherDay(6, firstNameOfTM1, 0 );
        scheduleShiftTablePage.selectCopyOrMoveByOptionName("move");
        scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();

        scheduleShiftTablePage.verifyShiftIsMovedToAnotherDay(6,firstNameOfTM1,0);
        scheduleMainPage.saveSchedule();
        scheduleShiftTablePage.verifyShiftIsMovedToAnotherDay(6,firstNameOfTM1,0);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "validate drag and drop to the last day of the week to copy shift(not blank day)")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDragDropToTheLastDayToCopyShiftAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

        // Navigate to next week
        scheduleCommonPage.navigateToNextWeek();
        // create the schedule.
        boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();
        //edit schedule
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.selectShiftTypeFilterByText("Action Required");
        shiftOperatePage.deleteTMShiftInWeekView("");
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
        scheduleMainPage.saveSchedule();
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

        String firstNameOfTM1 = scheduleShiftTablePage.getNameOfTheFirstShiftInADay(0);;
        scheduleShiftTablePage.dragOneShiftToAnotherDay(0, firstNameOfTM1, 6 );
        scheduleShiftTablePage.selectCopyOrMoveByOptionName("copy");
        scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();
        scheduleShiftTablePage.verifyShiftIsCopiedToAnotherDay(0,firstNameOfTM1,6);
        scheduleMainPage.saveSchedule();
        scheduleShiftTablePage.verifyShiftIsCopiedToAnotherDay(0,firstNameOfTM1,6);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "validate drag and drop to the last day of the week to copy shift(blank day)")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDragDropToTheBlankLastDayToCopyShiftAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

        // Navigate to next week
        scheduleCommonPage.navigateToNextWeek();
        // create the schedule.
        boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();
        //edit schedule
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.selectShiftTypeFilterByText("Action Required");
        shiftOperatePage.deleteTMShiftInWeekView("");
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
        scheduleCommonPage.navigateDayViewWithIndex(6);
        shiftOperatePage.deleteAllShiftsInDayView();
        scheduleCommonPage.clickOnWeekView();
        scheduleMainPage.saveSchedule();

        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        String firstNameOfTM1 = scheduleShiftTablePage.getNameOfTheFirstShiftInADay(0);
        scheduleShiftTablePage.dragOneShiftToAnotherDay(0, firstNameOfTM1, 6 );
        scheduleShiftTablePage.selectCopyOrMoveByOptionName("copy");
        scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();

        scheduleShiftTablePage.verifyShiftIsCopiedToAnotherDay(0,firstNameOfTM1,6);
        scheduleMainPage.saveSchedule();
        scheduleShiftTablePage.verifyShiftIsCopiedToAnotherDay(0,firstNameOfTM1,6);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "validate drag and drop to the last day of the week to move shift(not blank day)")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDragDropToTheLastDayToMoveShiftAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

        // Navigate to next week
        scheduleCommonPage.navigateToNextWeek();
        // create the schedule.
        boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();
        //edit schedule
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.selectShiftTypeFilterByText("Action Required");
        shiftOperatePage.deleteTMShiftInWeekView("");
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
        scheduleMainPage.saveSchedule();
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

        String firstNameOfTM1 = scheduleShiftTablePage.getNameOfTheFirstShiftInADay(0);;
        scheduleShiftTablePage.dragOneShiftToAnotherDay(0, firstNameOfTM1, 6 );
        scheduleShiftTablePage.selectCopyOrMoveByOptionName("move");
        scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();
        scheduleShiftTablePage.verifyShiftIsMovedToAnotherDay(0,firstNameOfTM1,6);
        scheduleMainPage.saveSchedule();
        scheduleShiftTablePage.verifyShiftIsMovedToAnotherDay(0,firstNameOfTM1,6);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "validate drag and drop to the last day of the week to move shift(blank day)")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDragDropToTheBlankLastDayToMoveShiftAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

        // Navigate to next week
        scheduleCommonPage.navigateToNextWeek();
        // create the schedule.
        boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();
        //edit schedule
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.selectShiftTypeFilterByText("Action Required");
        shiftOperatePage.deleteTMShiftInWeekView("");
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
        scheduleCommonPage.navigateDayViewWithIndex(6);
        shiftOperatePage.deleteAllShiftsInDayView();
        scheduleCommonPage.clickOnWeekView();
        scheduleMainPage.saveSchedule();

        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        String firstNameOfTM1 = scheduleShiftTablePage.getNameOfTheFirstShiftInADay(0);
        scheduleShiftTablePage.dragOneShiftToAnotherDay(0, firstNameOfTM1, 6 );
        scheduleShiftTablePage.selectCopyOrMoveByOptionName("move");
        scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();

        scheduleShiftTablePage.verifyShiftIsMovedToAnotherDay(0,firstNameOfTM1,6);
        scheduleMainPage.saveSchedule();
        scheduleShiftTablePage.verifyShiftIsMovedToAnotherDay(0,firstNameOfTM1,6);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "validate drag and drop to the another day of the week to copy shift will occur overlapping shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDragDropToCopyGetOverlappingShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

        // Navigate to next week
        scheduleCommonPage.navigateToNextWeek();
        // create the schedule.
        boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();
        List<String> shiftInfo = new ArrayList<>();
        while (shiftInfo.size() == 0) {
            shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
        }
        String firstNameOfTM1 = shiftInfo.get(0);
        String weekday = scheduleShiftTablePage.getWeekDayTextByIndex(1);
        String fullWeekDay = SimpleUtils.getFullWeekDayName(weekday);
        //edit schedule
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.selectShiftTypeFilterByText("Action Required");
        shiftOperatePage.deleteTMShiftInWeekView("");
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
        scheduleCommonPage.navigateDayViewWithIndex(1);
        shiftOperatePage.deleteAllShiftsInDayView();
        scheduleCommonPage.clickOnWeekView();
        scheduleMainPage.saveSchedule();

        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        scheduleShiftTablePage.dragOneShiftToAnotherDay(0, firstNameOfTM1, 1 );
        scheduleShiftTablePage.selectCopyOrMoveByOptionName("copy");
        scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();
        scheduleShiftTablePage.dragOneShiftToAnotherDay(0, firstNameOfTM1, 1 );
        scheduleShiftTablePage.selectCopyOrMoveByOptionName("copy");
        // Verify the warning model pops up
        String expectedMessage = firstNameOfTM1 + " is scheduled " + shiftInfo.get(6) + " on " + fullWeekDay
                + ". This shift will be converted to an open shift";
        scheduleShiftTablePage.verifyMessageOnCopyMoveConfirmPage(expectedMessage,expectedMessage);
        scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();
        //Check copy anyway button and result.
        if (scheduleShiftTablePage.ifMoveAnywayDialogDisplay()) {
            scheduleShiftTablePage.moveAnywayWhenChangeShift();
        } else {
            SimpleUtils.fail("Copy ANYWAY dialog failed to load!", false);
        }

        scheduleShiftTablePage.verifyShiftIsCopiedToAnotherDay(0,firstNameOfTM1,1);
        scheduleShiftTablePage.verifyShiftIsCopiedToAnotherDay(0,"open",1);
        scheduleMainPage.saveSchedule();
        scheduleShiftTablePage.verifyShiftIsCopiedToAnotherDay(0,firstNameOfTM1,1);
        scheduleShiftTablePage.verifyShiftIsCopiedToAnotherDay(0,"open",1);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "validate drag and drop to the same day of the week to copy shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDragDropToToTheSameDayAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

        // Navigate to next week
        scheduleCommonPage.navigateToNextWeek();
        // create the schedule.
        boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();
        List<String> shiftInfo = new ArrayList<>();
        while (shiftInfo.size() == 0) {
            shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
        }
        String firstNameOfTM1 = shiftInfo.get(0);
        //edit schedule
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.selectShiftTypeFilterByText("Action Required");
        shiftOperatePage.deleteTMShiftInWeekView("");
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.selectShiftTypeFilterByText("open");
        shiftOperatePage.deleteTMShiftInWeekView("");
        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
        scheduleMainPage.saveSchedule();

        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        scheduleShiftTablePage.dragOneShiftToAnotherDay(0, firstNameOfTM1, 0 );

        scheduleShiftTablePage.verifyShiftIsCopiedToAnotherDay(0,"open",0);
        scheduleMainPage.saveSchedule();
        scheduleShiftTablePage.verifyShiftIsCopiedToAnotherDay(0,"open",0);
    }
}
