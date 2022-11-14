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
import com.legion.utils.SimpleUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShiftPatternTest extends TestBase {

    private DashboardPage dashboardPage;
    private CreateSchedulePage createSchedulePage;
    private ScheduleMainPage scheduleMainPage;
    private ScheduleShiftTablePage scheduleShiftTablePage;
    private ScheduleCommonPage scheduleCommonPage;
    private LocationsPage locationsPage;
    private NewShiftPage newShiftPage;
    private ConfigurationPage configurationPage;
    private ShiftPatternPage shiftPatternPage;

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
            locationsPage = pageFactory.createOpsPortalLocationsPage();
            newShiftPage = pageFactory.createNewShiftPage();
            configurationPage = pageFactory.createOpsPortalConfigurationPage();
            shiftPatternPage = pageFactory.createConsoleShiftPatternPage();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify creating shift pattern rules from scheduling rules template")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyCreatingShiftPatternFromSchedulingRulesTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            // Go to Configurations page
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad("Scheduling Rules");
            configurationPage.isTemplateListPageShow();
            String templateName = "TemplateName-ForAuto";
            configurationPage.deleteTemplate(templateName);
            String workRole = "General Manager";

            // Create new template with the 'New Template' button, there are 2 tabs: Detail and Association
            configurationPage.createNewTemplate(templateName);
            configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            // Verify Shift Pattern option is available when click on + button
            //Verify Shift Pattern is clickable
            configurationPage.checkTheEntryOfAddShiftPatternRule();
            // Verify the content on Shift Pattern Details
            // Verify the default value of Role
            shiftPatternPage.verifyTheContentOnShiftPatternDetails(workRole);
            // Verify the mandatory fields
            shiftPatternPage.verifyTheMandatoryFields();
            // Verify can input the value in Name, Description and No. of instances* inputs
            shiftPatternPage.inputNameDescriptionNInstances("test", "description", "2");
            // Verify Select Start Date button is clickable
            // Verify can select one week successfully
            String selectedFirstDayOfWeek = shiftPatternPage.selectTheCurrentWeek();
            // Verify the functionality of + Add Week section
            // Verify the functionality of clicking on Off Week
            shiftPatternPage.selectAddOnOrOffWeek(false);
            // Verify the content on Off Week section
            shiftPatternPage.verifyTheContentOnOffWeekSection();
            // Verify the functionality of option "Auto schedule team members during off weeks"
            shiftPatternPage.checkOrUnCheckAutoSchedule(true);
            // Verify the functionality of each day checkbox
            shiftPatternPage.checkOrUnCheckSpecificDay(true, "Everyday");
            shiftPatternPage.checkOrUnCheckSpecificDay(false, "Everyday");
            shiftPatternPage.checkOrUnCheckSpecificDay(true, "Sunday");
            shiftPatternPage.checkOrUnCheckSpecificDay(false, "Sunday");
            // Verify the functionality of clicking on On Week
            shiftPatternPage.selectAddOnOrOffWeek(true);
            // Verify the content on On Week section
            shiftPatternPage.verifyTheContentOnOnWeekSection();
            // Verify the functionality of expand week icon
            shiftPatternPage.verifyTheFunctionalityOfExpandWeekIcon(2, false);
            shiftPatternPage.verifyTheFunctionalityOfExpandWeekIcon(2, true);
            // Verify the functionality of arrow icon
            shiftPatternPage.verifyTheFunctionalityOfArrowIcon(2, false);
            shiftPatternPage.verifyTheFunctionalityOfArrowIcon(2, true);
            // Verify the functionality of Delete week button
            int previousWeekCount = shiftPatternPage.getWeekCount();
            shiftPatternPage.deleteTheWeek(2);
            int currentWeekCount = shiftPatternPage.getWeekCount();
            if (previousWeekCount - currentWeekCount == 1) {
                SimpleUtils.pass("Delete the week successfully!");
            } else {
                SimpleUtils.fail("Failed to delete the week!", false);
            }
            // Verify the functionality of + Add Shift button
            shiftPatternPage.selectAddOnOrOffWeek(true);
            shiftPatternPage.clickOnAddShiftButton();
            // Verify the content on Create New Shift Window
            shiftPatternPage.verifyTheContentOnCreateNewShiftWindow();
            // Verify the functionality of Cancel button
            shiftPatternPage.clickOnCancelButton();
            // Verify the functionality of Create button without inputting anything
            shiftPatternPage.clickOnAddShiftButton();
            shiftPatternPage.clickOnCreateButton();
            List<String> warnings = shiftPatternPage.getWarningMessages();
            if (warnings.contains("At least one day should be selected") && warnings.contains("Field should not be empty")) {
                SimpleUtils.pass("Mandatory fields should be entered when clicking Create button!");
            } else {
                SimpleUtils.fail("There is no warning messages when nothing inputted!", false);
            }
            // Verify the work role shows on the Create New Shift window
            shiftPatternPage.verifyWorkRoleNameShows(workRole);
            // Verify the functionality of Shift Name/Description/Shift Notes inputs
            String shiftName = "Shift Name";
            String description = "Description";
            String shiftNotes = "Shift Notes";
            shiftPatternPage.inputShiftNameDescriptionNShiftNotes(shiftName, description, shiftNotes);
            // Verify the functionality of selecting work days
            List<String> workDays = new ArrayList<>(Arrays.asList("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"));
            shiftPatternPage.selectWorkDays(workDays);
            // Verify the functionality of Start Time and End Time inputs
            String startTime = "09:00 AM";
            String endTime = "05:00 PM";
            shiftPatternPage.inputStartOrEndTime(startTime, true);
            shiftPatternPage.inputStartOrEndTime(endTime, false);
            // Verify Start Time should be before End Time
            shiftPatternPage.inputStartOrEndTime("08:00 AM", false);
            warnings = shiftPatternPage.getWarningMessages();
            if (warnings.contains("Start time should be before End time")) {
                SimpleUtils.pass("Start time should be before End time message shows!");
            } else {
                SimpleUtils.fail("Start time should be before End time failed to show!", false);
            }
            shiftPatternPage.inputStartOrEndTime(endTime, false);
            // Verify the functionality of Add meal break button
            shiftPatternPage.clickOnAddMealOrRestBreakBtn(true);
            // Verify the shift offset and duration cannot be empty
            shiftPatternPage.clickOnCreateButton();
            warnings = shiftPatternPage.getBreakWarnings();
            if (warnings.contains("Start Offset should not be empty") && warnings.contains("Break Duration should not be empty")) {
                SimpleUtils.pass("Shift offset and break duration warning show correctly!");
            } else {
                SimpleUtils.fail("Shift offset and break duration warnings failed to show!", false);
            }
            // Verify the functionality of x button in meal break section
            shiftPatternPage.deleteTheBreakByNumber(true, 1);
            // Verify the functionality of Add rest break button
            shiftPatternPage.clickOnAddMealOrRestBreakBtn(false);
            // Verify the shift offset and duration cannot be empty
            shiftPatternPage.clickOnCreateButton();
            warnings = shiftPatternPage.getBreakWarnings();
            if (warnings.contains("Start Offset should not be empty") && warnings.contains("Break Duration should not be empty")) {
                SimpleUtils.pass("Shift offset and break duration warning show correctly!");
            } else {
                SimpleUtils.fail("Shift offset and break duration warnings failed to show!", false);
            }
            // Verify the functionality of x button in rest break section
            shiftPatternPage.deleteTheBreakByNumber(false, 1);
            // Verify can input the shift offset and break duration
            shiftPatternPage.inputShiftOffsetAndBreakDuration(5, 5, 1, true);
            // Verify the warning message "Start Offset must be in 5 minute increments" in meal break section
            shiftPatternPage.inputShiftOffsetAndBreakDuration(1, 1, 1, true);
            warnings = shiftPatternPage.getBreakWarnings();
            if (warnings.contains("Break Start must be in 5 minute increments") && warnings.contains("Break End must be in 5 minute increments")) {
                SimpleUtils.pass("Start Offset and Break Duration warning shows!");
            } else {
                SimpleUtils.fail("Start Offset and break duration warnings failed to show!", false);
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
