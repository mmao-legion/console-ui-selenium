package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
import com.legion.pages.core.schedule.ConsoleEditShiftPage;
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
import java.util.*;

public class BulkDeleteNEditTest extends TestBase {
    private DashboardPage dashboardPage;
    private CreateSchedulePage createSchedulePage;
    private ScheduleMainPage scheduleMainPage;
    private ScheduleShiftTablePage scheduleShiftTablePage;
    private ScheduleCommonPage scheduleCommonPage;
    private EditShiftPage editShiftPage;
    private NewShiftPage newShiftPage;
    private ShiftOperatePage shiftOperatePage;

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{
        try {
            this.createDriver((String) params[0], "83", "Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
            dashboardPage = pageFactory.createConsoleDashboardPage();
            createSchedulePage = pageFactory.createCreateSchedulePage();
            scheduleMainPage = pageFactory.createScheduleMainPage();
            scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            scheduleCommonPage = pageFactory.createScheduleCommonPage();
            editShiftPage = pageFactory.createEditShiftPage();
            newShiftPage = pageFactory.createNewShiftPage();
            shiftOperatePage = pageFactory.createShiftOperatePage();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify can delete multiple shifts at a time in week view")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDeleteMultipleShiftsInWeekViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            goToSchedulePageScheduleTab();

            // Create schedule if it is not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            int previousShiftCount = scheduleShiftTablePage.getShiftsCount();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            // Verify can select multiple shifts by pressing Ctrl/Cmd(Mac)
            int selectedShiftCount = 2;
            HashSet<Integer> set = scheduleShiftTablePage.verifyCanSelectMultipleShifts(selectedShiftCount);
            // Verify action menu will pop up when right clicking on anywhere of the selected shifts
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            // Verify the content on pop up menu
            scheduleShiftTablePage.verifyTheContentOnBulkActionMenu(selectedShiftCount);
            // Verify the Delete button on Bulk Action Menu is clickable
            String action = "Delete";
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            // Verify the shifts are marked as X after clicking on Delete button
            scheduleShiftTablePage.verifySelectedShiftsAreMarkedWithX(set);
            // Verify the shift which are marked "x" will not be deleted if canceling editing
            scheduleMainPage.clickOnCancelButtonOnEditMode();
            if (scheduleShiftTablePage.getShiftsCount() == previousShiftCount) {
                SimpleUtils.pass("Shifts are not deleted when clicking on Cancel button!");
            } else {
                SimpleUtils.fail("Shifts are deleted when clicking on Cancel button!", false);
            }
            // Verify multiple shifts are deleted
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            set = scheduleShiftTablePage.verifyCanSelectMultipleShifts(selectedShiftCount);
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            scheduleShiftTablePage.verifyTheContentOnBulkActionMenu(selectedShiftCount);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            scheduleShiftTablePage.verifySelectedShiftsAreMarkedWithX(set);
            scheduleMainPage.saveSchedule();
            Thread.sleep(10000);
            int currentShiftCount = scheduleShiftTablePage.getShiftsCount();
            if (currentShiftCount == previousShiftCount - selectedShiftCount) {
                SimpleUtils.pass("Selected shifts are deleted successfully!");
            } else {
                SimpleUtils.fail("Selected shifts are not deleted successfully!", false);
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify can delete multiple shifts at a time in day view")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDeleteMultipleShiftsInDayViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            goToSchedulePageScheduleTab();

            // Create schedule if it is not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            scheduleCommonPage.clickOnDayView();

            int previousShiftCount = scheduleShiftTablePage.getShiftsCount();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            // Verify can select multiple shifts by pressing Ctrl/Cmd(Mac)
            int selectedShiftCount = 2;
            HashSet<Integer> set = scheduleShiftTablePage.verifyCanSelectMultipleShifts(selectedShiftCount);
            // Verify action menu will pop up when right clicking on anywhere of the selected shifts
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            // Verify the content on pop up menu
            scheduleShiftTablePage.verifyTheContentOnBulkActionMenu(selectedShiftCount);
            // Verify the Delete button on Bulk Action Menu is clickable
            String action = "Delete";
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            // Verify the shifts are marked as X after clicking on Delete button
            scheduleShiftTablePage.verifySelectedShiftsAreMarkedWithX(set);
            // Verify the shift which are marked "x" will not be deleted if canceling editing
            scheduleMainPage.clickOnCancelButtonOnEditMode();
            if (scheduleShiftTablePage.getShiftsCount() == previousShiftCount) {
                SimpleUtils.pass("Shifts are not deleted when clicking on Cancel button!");
            } else {
                SimpleUtils.fail("Shifts are deleted when clicking on Cancel button!", false);
            }
            // Verify multiple shifts are deleted
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            set = scheduleShiftTablePage.verifyCanSelectMultipleShifts(selectedShiftCount);
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            scheduleShiftTablePage.verifyTheContentOnBulkActionMenu(selectedShiftCount);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            scheduleShiftTablePage.verifySelectedShiftsAreMarkedWithX(set);
            scheduleMainPage.saveSchedule();
            int currentShiftCount = scheduleShiftTablePage.getShiftsCount();
            if (currentShiftCount == previousShiftCount - selectedShiftCount) {
                SimpleUtils.pass("Selected shifts are deleted successfully!");
            } else {
                SimpleUtils.fail("Selected shifts are not deleted successfully!", false);
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the content on Multiple Edit Shifts window for regular location")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOnMultipleEditShiftsWindowAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            goToSchedulePageScheduleTab();

            // Create schedule if it is not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            String workRole = shiftOperatePage.getRandomWorkRole();

            BasePage basePage = new BasePage();
            String activeWeek = basePage.getActiveWeekText();
            String startOfWeek = activeWeek.split(" ")[3] + " " + activeWeek.split(" ")[4];
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            // Verify can select multiple shifts by pressing Ctrl/Cmd(Mac)
            int selectedShiftCount = 2;
            HashSet<Integer> set = scheduleShiftTablePage.verifyCanSelectMultipleShifts(selectedShiftCount);
            List<String> selectedDays = scheduleShiftTablePage.getSelectedWorkDays(set);
            // Verify action menu will pop up when right clicking on anywhere of the selected shifts
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            scheduleShiftTablePage.verifyTheContentOnBulkActionMenu(selectedShiftCount);
            // Verify Edit action is visible when right clicking the selected shifts in week view
            // Verify the functionality of Edit button in week view
            String action = "Edit";
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            SimpleUtils.assertOnFail("Edit Shifts window failed to load!", editShiftPage.isEditShiftWindowLoaded(), false);
            // Verify the title of Edit Shifts window
            editShiftPage.verifyTheTitleOfEditShiftsWindow(selectedShiftCount, startOfWeek);
            // Verify the selected days show correctly
            editShiftPage.verifySelectedWorkDays(selectedShiftCount, selectedDays);
            // Verify the Location Name shows correctly
            editShiftPage.verifyLocationNameShowsCorrectly(location);
            // Verify the visibility of buttons
            editShiftPage.verifyTheVisibilityOfButtons();
            // Verify the content of options section
            editShiftPage.verifyTheContentOfOptionsSection();
            // Verify the visibility of Clear Edited Fields button
            SimpleUtils.assertOnFail("Clear Edited Fields button failed to load!", editShiftPage.isClearEditedFieldsBtnLoaded(), false);
            // Verify the three columns show on Shift Details section
            editShiftPage.verifyThreeColumns();
            // Verify the editable types show on Shift Detail section
            editShiftPage.verifyEditableTypesShowOnShiftDetail();
            // Verify the functionality of x button
            editShiftPage.clickOnXButton();
            SimpleUtils.assertOnFail("Click on X button failed!", !editShiftPage.isEditShiftWindowLoaded(), false);
            // Verify the functionality of Cancel button
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            SimpleUtils.assertOnFail("Edit Shifts window failed to load!", editShiftPage.isEditShiftWindowLoaded(), false);
            editShiftPage.clickOnCancelButton();

            scheduleMainPage.clickOnCancelButtonOnEditMode();
            scheduleCommonPage.clickOnDayView();
            String weekDay = basePage.getActiveWeekText();
            String fullWeekDay = SimpleUtils.getFullWeekDayName(weekDay.split(" ")[0].trim());
            selectedDays = new ArrayList<>();
            selectedDays.add(fullWeekDay);
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            scheduleMainPage.saveSchedule();

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            set = scheduleShiftTablePage.verifyCanSelectMultipleShifts(selectedShiftCount);
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            SimpleUtils.assertOnFail("Edit Shifts window failed to load!", editShiftPage.isEditShiftWindowLoaded(), false);
            // Verify the title of Edit Shifts window in day view
            editShiftPage.verifyTheTitleOfEditShiftsWindow(selectedShiftCount, startOfWeek);
            // Verify the selected days show correctly in day view
            editShiftPage.verifySelectedWorkDays(selectedShiftCount, selectedDays);
            // Verify the Location Name shows correctly in day view
            editShiftPage.verifyLocationNameShowsCorrectly(location);
            // Verify the visibility of buttons in day view
            editShiftPage.verifyTheVisibilityOfButtons();
            // Verify the content of options section in day view
            editShiftPage.verifyTheContentOfOptionsSection();
            // Verify the visibility of Clear Edited Fields button in day view
            SimpleUtils.assertOnFail("Clear Edited Fields button failed to load!", editShiftPage.isClearEditedFieldsBtnLoaded(), false);
            // Verify the three columns show on Shift Details section in day view
            editShiftPage.verifyThreeColumns();
            // Verify the editable types show on Shift Detail section in day view
            editShiftPage.verifyEditableTypesShowOnShiftDetail();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the content on Multiple Edit Shifts window for regular location")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheCurrentColumnOnMultipleEditShiftsWindowAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String various = "Various";
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            goToSchedulePageScheduleTab();

            // Create schedule if it is not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            ArrayList<HashMap<String,String>> workRoles = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            String workRole1 = workRoles.get(0).get("optionName");
            String workRole2 = workRoles.get(1).get("optionName");
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyAll.getValue());

            String shiftName1 = "Shift Name 1";
            String shiftName2 = "Shift Name 2";
            String shiftNotes1 = "Shift Notes 1";
            String shiftNotes2 = "Shift Notes 2";
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            // Create 2 shifts with all different
            createShiftsWithSpecificValues(workRole1, shiftName1, "", "9:00am", "12:00pm",
                    1, 1, ScheduleTestKendraScott2.staffingOption.OpenShift.getValue(), shiftNotes1);
            createShiftsWithSpecificValues(workRole2, shiftName2, "", "1:00pm", "3:00pm",
                    1, 2, ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), shiftNotes2);

            HashSet<Integer> shiftIndexes = scheduleShiftTablePage.getAddedShiftsIndexesByPlusIcon();

            scheduleShiftTablePage.selectSpecificShifts(shiftIndexes);
            scheduleShiftTablePage.rightClickOnSelectedShifts(shiftIndexes);
            String action = "Edit";
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            SimpleUtils.assertOnFail("Edit Shifts window failed to load!", editShiftPage.isEditShiftWindowLoaded(), false);
            // Verify "Various" will show when selecting different work roles
            editShiftPage.verifyTheTextInCurrentColumn(ConsoleEditShiftPage.sectionType.WorkRole.getType(), various);
            // Verify "Various" will show when selecting different shift names
            editShiftPage.verifyTheTextInCurrentColumn(ConsoleEditShiftPage.sectionType.ShiftName.getType(), various);
            // Verify "Various" will show when selecting the shifts with different start times
            editShiftPage.verifyTheTextInCurrentColumn(ConsoleEditShiftPage.sectionType.StartTime.getType(), various);
            // Verify "Various" will show when selecting the shifts with different end times
            editShiftPage.verifyTheTextInCurrentColumn(ConsoleEditShiftPage.sectionType.EndTime.getType(), various);
            // Verify "Various" will show when selecting the shifts on different days
            editShiftPage.verifyTheTextInCurrentColumn(ConsoleEditShiftPage.sectionType.Date.getType(), various);
            // Verify "Various" will show when selecting the shifts with different shift notes
            editShiftPage.verifyTheTextInCurrentColumn(ConsoleEditShiftPage.sectionType.ShiftNotes.getType(), various);

            editShiftPage.clickOnDateSelect();
            List<String> dates = editShiftPage.getOptionsFromSpecificSelect();

            editShiftPage.clickOnCancelButton();
            scheduleMainPage.clickOnCancelButtonOnEditMode();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            createShiftsWithSpecificValues(workRole1, shiftName1, null, "9:00am", "12:00pm",
                    2, 1, ScheduleTestKendraScott2.staffingOption.OpenShift.getValue(), shiftNotes1);
            String selectedDate = dates.get(1);

            shiftIndexes = scheduleShiftTablePage.getAddedShiftsIndexesByPlusIcon();

            scheduleShiftTablePage.selectSpecificShifts(shiftIndexes);
            scheduleShiftTablePage.rightClickOnSelectedShifts(shiftIndexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            SimpleUtils.assertOnFail("Edit Shifts window failed to load!", editShiftPage.isEditShiftWindowLoaded(), false);

            // Verify work role name will show when selecting same work role
            editShiftPage.verifyTheTextInCurrentColumn(ConsoleEditShiftPage.sectionType.WorkRole.getType(), workRole1);
            // Verify shift name will show when selecting same shift name
            editShiftPage.verifyTheTextInCurrentColumn(ConsoleEditShiftPage.sectionType.ShiftName.getType(), shiftName1);
            // Verify shift start time will show when selecting the shifts with same start time
            editShiftPage.verifyTheTextInCurrentColumn(ConsoleEditShiftPage.sectionType.StartTime.getType(), "9:00am");
            // Verify shift end time will show when selecting the shifts with same start time
            editShiftPage.verifyTheTextInCurrentColumn(ConsoleEditShiftPage.sectionType.EndTime.getType(), "12:00pm");
            // Verify date will show when selecting the shifts on the same day
            editShiftPage.verifyTheTextInCurrentColumn(ConsoleEditShiftPage.sectionType.Date.getType(), selectedDate);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the functionality of Edited column")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheEditedColumnOnMultipleEditShiftsWindowAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            goToSchedulePageScheduleTab();

            // Create schedule if it is not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            ArrayList<HashMap<String,String>> workRoles = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            String workRole1 = workRoles.get(0).get("optionName");
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyAll.getValue());

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            int selectedShiftCount = 2;
            HashSet<Integer> set = scheduleShiftTablePage.verifyCanSelectMultipleShifts(selectedShiftCount);
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            String action = "Edit";
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            SimpleUtils.assertOnFail("Edit Shifts window failed to load!", editShiftPage.isEditShiftWindowLoaded(), false);

            // Verify the functionality of Work Role Select
            editShiftPage.clickOnWorkRoleSelect();
            editShiftPage.selectSpecificOptionByText(workRole1);
            String selectedWorkRole = editShiftPage.getSelectedWorkRole();
            SimpleUtils.assertOnFail("Failed to select the work role!", !selectedWorkRole.trim().equals(workRole1), false);
            // Verify the functionality of Clear Edited Fields button
            editShiftPage.clickOnClearEditedFieldsButton();
            selectedWorkRole = editShiftPage.getSelectedWorkRole();
            SimpleUtils.assertOnFail("Failed to clear the edited field!", !workRole1.equalsIgnoreCase(selectedWorkRole), false);
            // Verify the functionality of Shift Name input
            String shiftName = "This is the shift name";
            editShiftPage.inputShiftName(shiftName);
            // Verify the functionality of Update button
            editShiftPage.clickOnUpdateButton();

            scheduleShiftTablePage.selectSpecificShifts(set);
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            SimpleUtils.assertOnFail("Edit Shifts window failed to load!", editShiftPage.isEditShiftWindowLoaded(), false);

            // Verify the functionality of Start Time input
            editShiftPage.inputStartOrEndTime("09:00 AM", true);
            // Verify selecting Use Offset checkbox on Start Time section
            editShiftPage.checkUseOffset(true, true);
            // Verify the functionality of Mins input in Start Time section
            editShiftPage.verifyTheFunctionalityOfOffsetTime(null, "15", null, true);
            editShiftPage.verifyTheFunctionalityOfOffsetTime(null, "1", null, true);
            // Verify the functionality of Hours input in Start Time section
            editShiftPage.verifyTheFunctionalityOfOffsetTime("11", null, null, true);
            editShiftPage.verifyTheFunctionalityOfOffsetTime("12", null, null, true);
            // Verify the functionality of Early/Late select in Start Time section
            editShiftPage.verifyTheFunctionalityOfOffsetTime(null, null, "Early", true);
            editShiftPage.verifyTheFunctionalityOfOffsetTime(null, null, "Late", true);
            // Verify un-selecting Use Offset checkbox on Start Time section
            editShiftPage.checkUseOffset(true, false);
            // Verify the functionality of End Time input
            editShiftPage.inputStartOrEndTime("12:00 PM", false);
            // Verify selecting Use Offset checkbox on End Time
            editShiftPage.checkUseOffset(false, true);
            // Verify the functionality of Mins input in End Time section
            editShiftPage.verifyTheFunctionalityOfOffsetTime(null, "15", null, false);
            editShiftPage.verifyTheFunctionalityOfOffsetTime(null, "1", null, false);
            // Verify the functionality of Hours input in End Time section
            editShiftPage.verifyTheFunctionalityOfOffsetTime("11", null, null, false);
            editShiftPage.verifyTheFunctionalityOfOffsetTime("12", null, null, false);
            // Verify the functionality of Early/Late select in End Time section
            editShiftPage.verifyTheFunctionalityOfOffsetTime(null, null, "Early", false);
            editShiftPage.verifyTheFunctionalityOfOffsetTime(null, null, "Late", false);
            // Verify un-selecting Use Offset checkbox on End Time section
            editShiftPage.checkUseOffset(false, false);
            // Verify the functionality of Next day check box
            editShiftPage.checkOrUnCheckNextDayOnBulkEditPage(true);

            // Verify the content in Date select
            editShiftPage.clickOnDateSelect();
            List<String> dates = editShiftPage.getOptionsFromSpecificSelect();
            for (String date : dates) {
                if (!date.contains("Sunday") && !date.contains("Monday") && !date.contains("Tuesday") && !date.contains("Wednesday")
                        && !date.contains("Thursday") && !date.contains("Friday") && !date.contains("Saturday")) {
                    SimpleUtils.fail("The content of dates are incorrect!", false);
                    break;
                }
            }
            // Verify can select the date
            editShiftPage.selectSpecificOptionByText(dates.get(0));
            SimpleUtils.assertOnFail("Failed to select the date: " + dates.get(0), editShiftPage.getSelectedDate()
                    .trim().contains(dates.get(0)), false);
            // Verify the content in Assignment Select
            editShiftPage.clickOnAssignmentSelect();
            List<String> assignments = editShiftPage.getOptionsFromSpecificSelect();
            List<String> expectedAssignments = new ArrayList<>();
            expectedAssignments.add("Do not change assignments");
            expectedAssignments.add("Open Shift: Auto Offer to TMs");
            expectedAssignments.add("Assign or Offer to Specific TM's");
            String info = "Only the shifts with same start time, end time, location and worker role can be bulk-assigned";
            if (assignments.get(0).equalsIgnoreCase(expectedAssignments.get(0)) && assignments.get(1).equalsIgnoreCase(
                    expectedAssignments.get(1)) && assignments.get(2).contains(expectedAssignments.get(2))) {
                SimpleUtils.pass("The content in Assignment list is correct!");
            } else {
                SimpleUtils.fail("The content in Assignment list is incorrect!", false);
            }
            // Verify can select the Assignment
            editShiftPage.selectSpecificOptionByText(assignments.get(0));
            editShiftPage.getSelectedAssignment();
            SimpleUtils.assertOnFail("Failed to select the assignment: " + assignments.get(0), editShiftPage
                    .getSelectedAssignment().trim().contains(assignments.get(0)), false);
            // Verify can input the shift notes
            editShiftPage.inputShiftNotes("notes");
            // Verify can select the two options
            editShiftPage.checkOrUncheckOptionsByName(ConsoleEditShiftPage.twoOptions.AllowConflicts.getOption(), true);
            editShiftPage.checkOrUncheckOptionsByName(ConsoleEditShiftPage.twoOptions.AllowComplianceErrors.getOption(), false);
            editShiftPage.checkOrUncheckOptionsByName(ConsoleEditShiftPage.twoOptions.AllowConflicts.getOption(), true);
            editShiftPage.checkOrUncheckOptionsByName(ConsoleEditShiftPage.twoOptions.AllowComplianceErrors.getOption(), false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
