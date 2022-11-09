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
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;

public class SingleShiftEditTest extends TestBase {
    private DashboardPage dashboardPage;
    private CreateSchedulePage createSchedulePage;
    private ScheduleMainPage scheduleMainPage;
    private ScheduleShiftTablePage scheduleShiftTablePage;
    private ScheduleCommonPage scheduleCommonPage;
    private EditShiftPage editShiftPage;
    private NewShiftPage newShiftPage;
    private ShiftOperatePage shiftOperatePage;
    private ControlsPage controlsPage;
    private ControlsNewUIPage controlsNewUIPage;
    private MySchedulePage mySchedulePage;
    private BasePage basePage;
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
            controlsPage = pageFactory.createConsoleControlsPage();
            controlsNewUIPage = pageFactory.createControlsNewUIPage();
            mySchedulePage = pageFactory.createMySchedulePage();
            basePage = new BasePage();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
//    @Enterprise(name = "Vailqacn_Enterprise")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the content on Edit Single Shifts window for regular location")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOnSingleEditShiftsWindowForRegularLocationAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            goToSchedulePageScheduleTab();

            // Create schedule if it is not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Open");
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Unassigned");
            scheduleMainPage.saveSchedule();
            String workRole = shiftOperatePage.getRandomWorkRole();

            BasePage basePage = new BasePage();
            String activeWeek = basePage.getActiveWeekText();
            String startOfWeek = activeWeek.split(" ")[3] + " " + activeWeek.split(" ")[4];
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            // Verify can select single shifts by pressing Ctrl/Cmd(Mac)
            int selectedShiftCount = 1;
            int index = 1;
            HashSet<Integer> set = new HashSet<>();
            set.add(index);
            List<String> shiftInfo= scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            List<String> selectedDays = scheduleShiftTablePage.getSelectedWorkDays(set);
            // Verify action menu will pop up when right clicking on anywhere of the selected shifts
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            scheduleShiftTablePage.verifyTheContentOnBulkActionMenu(selectedShiftCount);
            // Verify Edit action is visible when right clicking the selected shift in week view
            // Verify the functionality of Edit button in week view
            String action = "Edit";
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            SimpleUtils.assertOnFail("Edit Shifts window failed to load!", editShiftPage.isEditShiftWindowLoaded(), false);
            // Verify the title of Edit Shifts window
            editShiftPage.verifyTheTitleOfEditShiftsWindow(selectedShiftCount, startOfWeek);
            // Verify the selected days show correctly
            editShiftPage.verifyShiftInfoCard(shiftInfo);
            // Verify the Location Name shows correctly
            editShiftPage.verifyLocationNameShowsCorrectly(location);
            // Verify the visibility of buttons
            editShiftPage.verifyTheVisibilityOfButtons();
            // Verify the content of options section
            editShiftPage.verifyTheContentOfOptionsSectionIsNotLoaded();
            // Verify the visibility of Clear Edited Fields button
            SimpleUtils.assertOnFail("Clear Edited Fields button failed to load!", editShiftPage.isClearEditedFieldsBtnLoaded(), false);
            // Verify the three columns show on Shift Details section
            editShiftPage.verifyThreeColumns();
            // Verify the editable types show on Shift Detail section
            editShiftPage.verifyEditableTypesShowOnSingleEditShiftDetail();
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
            shiftInfo = scheduleShiftTablePage.getTheShiftInfoInDayViewByIndex(index);
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            SimpleUtils.assertOnFail("Edit Shifts window failed to load!", editShiftPage.isEditShiftWindowLoaded(), false);
            // Verify the title of Edit Shifts window in day view
            editShiftPage.verifyTheTitleOfEditShiftsWindow(selectedShiftCount, startOfWeek);
            // Verify the selected days show correctly in day view
            editShiftPage.verifyShiftInfoCard(shiftInfo);
            // Verify the Location Name shows correctly in day view
            editShiftPage.verifyLocationNameShowsCorrectly(location);
            // Verify the visibility of buttons in day view
            editShiftPage.verifyTheVisibilityOfButtons();
            // Verify the content of options section in day view
            editShiftPage.verifyTheContentOfOptionsSectionIsNotLoaded();
            // Verify the visibility of Clear Edited Fields button in day view
            SimpleUtils.assertOnFail("Clear Edited Fields button failed to load!", editShiftPage.isClearEditedFieldsBtnLoaded(), false);
            // Verify the three columns show on Shift Details section in day view
            editShiftPage.verifyThreeColumns();
            // Verify the editable types show on Shift Detail section in day view
            editShiftPage.verifyEditableTypesShowOnSingleEditShiftDetail();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
//    @Enterprise(name = "Vailqacn_Enterprise")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the functionality of Current and Edit column when selecting single shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheCurrentColumnOnSingleEditShiftsWindowAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            goToSchedulePageScheduleTab();

            // Create schedule if it is not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            createSchedulePage.publishActiveSchedule();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            ArrayList<HashMap<String,String>> workRoles = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            String workRole = workRoles.get(0).get("optionName");
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyAll.getValue());
            String activeWeek = basePage.getActiveWeekText();
            String startOfWeek =  SimpleUtils.getFullWeekDayName(activeWeek.split(" ")[0]);
            String startOfWeekDate = activeWeek.split(" ")[3] + " " + activeWeek.split(" ")[4];
            String shiftName = "Shift Name 1";
            String shiftNotes = "Shift Notes 1";
            String shiftStartTime = "9:00am";
            String shiftEndTime = "12:00pm";
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            // Create 2 shifts with all different
            createShiftsWithSpecificValues(workRole, shiftName, "", shiftStartTime, shiftEndTime,
                    1, Arrays.asList(0), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), shiftNotes, "a");

            int index = scheduleShiftTablePage.getAddedShiftsIndexesByPlusIcon().iterator().next();
            mySchedulePage.clickOnShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();
            List<String> breakTimes = shiftOperatePage.verifyEditBreaks();
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            String mealBreakTime = breakTimes.get(0).replace(" am", "am").replace(" pm", "pm");
            String restBreakTime = breakTimes.get(1).replace(" am", "am").replace(" pm", "pm");
            HashSet<Integer> set = new HashSet<>();
            set.add(index);
            List<String> shiftInfo= scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            String firstName = shiftInfo.get(0);
            String lastName = shiftInfo.get(5);
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            String action = "Edit";
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            SimpleUtils.assertOnFail("Edit Shifts window failed to load!", editShiftPage.isEditShiftWindowLoaded(), false);
            // Verify the original work role will show when selecting the single shift
            editShiftPage.verifyTheTextInCurrentColumnOnSingleEditShiftPage(ConsoleEditShiftPage.sectionType.WorkRole.getType(), workRole);
            // Verify the original shift name will show when selecting single shift
            editShiftPage.verifyTheTextInCurrentColumnOnSingleEditShiftPage(ConsoleEditShiftPage.sectionType.ShiftName.getType(), shiftName);
            // Verify original shift start time will show when selecting single shift
            editShiftPage.verifyTheTextInCurrentColumnOnSingleEditShiftPage(ConsoleEditShiftPage.sectionType.StartTime.getType(), shiftStartTime);
            // Verify original shift end time will show when selecting single shift
            editShiftPage.verifyTheTextInCurrentColumnOnSingleEditShiftPage(ConsoleEditShiftPage.sectionType.EndTime.getType(), shiftEndTime);
            // Verify original Breaks will show when selecting single shift
            editShiftPage.verifyTheTextInCurrentColumnOnSingleEditShiftPage(ConsoleEditShiftPage.sectionType.Breaks.getType(), mealBreakTime+" and "+restBreakTime);
            // Verify original Date will show when selecting single shift
            editShiftPage.verifyTheTextInCurrentColumnOnSingleEditShiftPage(ConsoleEditShiftPage.sectionType.Date.getType(), startOfWeek+ " - "+startOfWeekDate);
            // Verify avatars will show consistent with the selected shift
            editShiftPage.verifyTheTextInCurrentColumnOnSingleEditShiftPage(ConsoleEditShiftPage.sectionType.Assignment.getType(), firstName+ " "+lastName);
            // Verify the original shift note will show when selecting single shift
            editShiftPage.verifyTheTextInCurrentColumnOnSingleEditShiftPage(ConsoleEditShiftPage.sectionType.ShiftNotes.getType(), shiftNotes);

            //update work role
            editShiftPage.clickOnWorkRoleSelect();
            List<String> actualWorkRoleList2 = editShiftPage.getOptionsFromSpecificSelect();
            editShiftPage.selectSpecificOptionByText(actualWorkRoleList2.get(0));
            //update shift name
            editShiftPage.inputShiftName(shiftName+ " updated");
            //update shift start and end time
            editShiftPage.inputStartOrEndTime("10:00 AM", true);
            editShiftPage.inputStartOrEndTime("2:00 PM", false);
            //remove breaks
            editShiftPage.removeAllMealBreaks();
            editShiftPage.removeAllRestBreaks();
            //update date
            editShiftPage.clickOnDateSelect();
            List<String> dates = editShiftPage.getOptionsFromSpecificSelect();
            // Verify can select the date
            editShiftPage.selectSpecificOptionByText(dates.get(2));
            //update shift notes
            editShiftPage.inputShiftNotes(shiftNotes+ " updated");

            //Verify the original work role name still show when selecting new work role
            editShiftPage.verifyTheTextInCurrentColumnOnSingleEditShiftPage(ConsoleEditShiftPage.sectionType.WorkRole.getType(), workRole);
            // Verify the original shift name will show when input new shift name
            editShiftPage.verifyTheTextInCurrentColumnOnSingleEditShiftPage(ConsoleEditShiftPage.sectionType.ShiftName.getType(), shiftName);
            // Verify original shift start time still show when input new shift start time
            editShiftPage.verifyTheTextInCurrentColumnOnSingleEditShiftPage(ConsoleEditShiftPage.sectionType.StartTime.getType(), shiftStartTime);
            // Verify original shift end time still show when input new shift end time
            editShiftPage.verifyTheTextInCurrentColumnOnSingleEditShiftPage(ConsoleEditShiftPage.sectionType.EndTime.getType(), shiftEndTime);
            // Verify original Breaks still show when remove all breaks
            editShiftPage.verifyTheTextInCurrentColumnOnSingleEditShiftPage(ConsoleEditShiftPage.sectionType.Breaks.getType(), mealBreakTime+" and "+restBreakTime);
            // Verify original Date still show when selecting new Date
            editShiftPage.verifyTheTextInCurrentColumnOnSingleEditShiftPage(ConsoleEditShiftPage.sectionType.Date.getType(), startOfWeek+ " - "+startOfWeekDate);
            // Verify avatars will show consistent with the selected shift
            editShiftPage.verifyTheTextInCurrentColumnOnSingleEditShiftPage(ConsoleEditShiftPage.sectionType.Assignment.getType(), firstName+ " "+lastName);
            // Verify the original shift note will show when input new shift note
            editShiftPage.verifyTheTextInCurrentColumnOnSingleEditShiftPage(ConsoleEditShiftPage.sectionType.ShiftNotes.getType(), shiftNotes);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
