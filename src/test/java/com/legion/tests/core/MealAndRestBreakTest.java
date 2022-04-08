package com.legion.tests.core;

import com.legion.api.toggle.ToggleAPI;
import com.legion.api.toggle.Toggles;
import com.legion.pages.*;
import com.legion.pages.OpsPortaPageFactories.ConfigurationPage;
import com.legion.pages.OpsPortaPageFactories.LocationsPage;
import com.legion.pages.core.ConsoleControlsNewUIPage;
import com.legion.pages.core.OpsPortal.OpsPortalConfigurationPage;
import com.legion.pages.core.opConfiguration.MealAndRestPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.core.OpsPortal.LocationsTest;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.Constants;
import com.legion.utils.SimpleUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;

import static com.legion.utils.MyThreadLocal.getDriver;

public class MealAndRestBreakTest extends TestBase {

    private String breakOption = "Breaks";
    private String editBreakOption = "Edit Breaks";
    private String meal = "Meal";
    private String rest = "Rest";

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

    private enum MealBreakDuration {
        EventManager(20),
        GeneralManager(25),
        TeamMember(30);
        private final int value;
        private MealBreakDuration(final int duration) {
            value = duration;
        }
    }

    private enum RestBreakDuration {
        EventManager(15),
        GeneralManager(20),
        TeamMember(25);
        private final int value;
        private RestBreakDuration(final int duration) {
            value = duration;
        }
    }

    private enum WorkRoles {
        EventManager("Event Manager"),
        GeneralManager("General Manager"),
        TeamMember("Team Member Corporate-Theatre");
        private final String value;
        private WorkRoles(final String role) {
            value = role;
        }
    }

    private enum MealRestBreakViolations {
        MissedMealBreak("Missed Meal Break"),
        MealDuration("Meal Duration"),
        MealPlacement("Meal Placement"),
        NoMealCoverage("No Meal Coverage"),
        MissedRestBreak("Missed Rest Break"),
        ShortRestBreak("Short Rest Break"),
        RestTimeNotIdeal("Rest Time Not Ideal");
        private final String value;
        private MealRestBreakViolations(final String violation) {
            value = violation;
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "Verify Edit Breaks option is available")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyEditBreaksOptionIsAvailableAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab

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

            // Verify Breaks option is enabled in non-Edit mode week view
            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.verifySpecificOptionEnabledOnShiftMenu(breakOption);

            // Verify Breaks option is enabled in non-Edit mode day view
            scheduleCommonPage.clickOnDayView();
            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.verifySpecificOptionEnabledOnShiftMenu(breakOption);

            // Verify Edit Breaks is enabled in edit mode day view
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.verifySpecificOptionEnabledOnShiftMenu(editBreakOption);

            // Verify Edit Breaks is enabled in edit mode week view
            scheduleCommonPage.clickOnWeekView();
            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.verifySpecificOptionEnabledOnShiftMenu(editBreakOption);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "Verify the functionality of Edit Break option in week view")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheFunctionalityOfEditBreaksWeekViewAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab

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
            boolean isPublished = createSchedulePage.isWeekPublished();
            if (!isPublished) {
                createSchedulePage.publishActiveSchedule();
            }

            // Verify Edit Breaks is clickable in week view
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            List<String> shiftInfo = new ArrayList<>();
            String firstNameOfTM = "";
            int index = 0;
            while (firstNameOfTM.equals("") || firstNameOfTM.equals("Open") || firstNameOfTM.equals("Unassigned")) {
                index = scheduleShiftTablePage.getRandomIndexOfShift();
                shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(index);
                //Search shift by TM names: first name and last name
                firstNameOfTM = shiftInfo.get(0);
            }
            mySchedulePage.clickOnShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();

            // Verify Edit breaks dialog should pop up
            SimpleUtils.assertOnFail("Edit Breaks dialog doesn't pop up!", shiftOperatePage.isMealBreakTimeWindowDisplayWell(true), false);

            // Verify the shift info is correct
            shiftOperatePage.verifyShiftInfoIsCorrectOnMealBreakPopUp(shiftInfo);

            // Verify meal break and rest break are placed in the correct time
            shiftOperatePage.verifyMealBreakAndRestBreakArePlacedCorrectly();

            // Verify can change the length of meal break and rest break
            // Verify can move the place of meal break and rest break
            shiftOperatePage.verifyEditBreaks();
            // Verify the functionality of CANCEL button
            shiftOperatePage.clickOnCancelEditShiftTimeButton();
            // Verify the functionality of UPDATE button
            mySchedulePage.clickOnShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();
            List<String> breakTimes = shiftOperatePage.verifyEditBreaks();
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            // Verify the shift should have edit icon
            shiftOperatePage.verifySpecificShiftHaveEditIcon(index);
            // Verify the changed meal break and rest break should be updated
            mySchedulePage.clickOnShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();
            shiftOperatePage.verifyBreakTimesAreUpdated(breakTimes);
            shiftOperatePage.clickOnCancelEditShiftTimeButton();
            Thread.sleep(1000);
            // Verify the changed meal break and rest break are saved successfully
            scheduleMainPage.saveSchedule();
            mySchedulePage.clickOnShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();
            shiftOperatePage.verifyBreakTimesAreUpdated(breakTimes);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "Verify the functionality of Edit Break option in day view")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheFunctionalityOfEditBreaksDayViewAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab

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
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            scheduleCommonPage.clickOnDayView();

            // Verify Edit Breaks is clickable in day view
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            List<String> shiftInfo = new ArrayList<>();
            String firstNameOfTM = "";
            int index = 0;
            while (firstNameOfTM.equals("") || firstNameOfTM.equals("Open") || firstNameOfTM.equals("Unassigned")) {
                index = scheduleShiftTablePage.getRandomIndexOfShift();
                shiftInfo = scheduleShiftTablePage.getTheShiftInfoInDayViewByIndex(index);
                firstNameOfTM = shiftInfo.get(0);
            }
            mySchedulePage.clickOnShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();

            // Verify Edit breaks dialog should pop up
            SimpleUtils.assertOnFail("Edit Breaks dialog doesn't pop up!", shiftOperatePage.isMealBreakTimeWindowDisplayWell(true), false);

            // Verify the shift info is correct
            shiftOperatePage.verifyShiftInfoIsCorrectOnMealBreakPopUp(shiftInfo);

            // Verify meal break and rest break are placed in the correct time
            shiftOperatePage.verifyMealBreakAndRestBreakArePlacedCorrectly();

            // Verify can change the length of meal break and rest break
            // Verify can move the place of meal break and rest break
            shiftOperatePage.verifyEditBreaks();
            // Verify the functionality of CANCEL button
            shiftOperatePage.clickOnCancelEditShiftTimeButton();
            // Verify the functionality of UPDATE button
            mySchedulePage.clickOnShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();
            List<String> breakTimes = shiftOperatePage.verifyEditBreaks();
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            // Verify the shift should have edit icon
            shiftOperatePage.verifySpecificShiftHaveEditIcon(index);
            // Verify the changed meal break and rest break should be updated
            mySchedulePage.clickOnShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();
            shiftOperatePage.verifyBreakTimesAreUpdated(breakTimes);
            shiftOperatePage.clickOnCancelEditShiftTimeButton();
            // Verify the changed meal break and rest break are saved successfully
            scheduleMainPage.saveSchedule();
            mySchedulePage.clickOnShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();
            shiftOperatePage.verifyBreakTimesAreUpdated(breakTimes);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "Verify turn on meal break setting")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTurnOnMealBreakSettingAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();

            if (getDriver().getCurrentUrl().contains(propertyMap.get(Constants.ControlEnterprice))){
                controlsPage.gotoControlsPage();
                SimpleUtils.assertOnFail("Controls Page failed to load", controlsNewUIPage.isControlsPageLoaded(), false);
                controlsNewUIPage.clickOnControlsComplianceSection();
                SimpleUtils.assertOnFail("Compliance Card failed to load", controlsNewUIPage.isCompliancePageLoaded(), false);
            } else if (getDriver().getCurrentUrl().contains(propertyMap.get(Constants.OpEnterprice))) {
                //Go to OP page
                LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
                locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
                SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
                locationsPage.clickOnLocationsTab();
                locationsPage.goToSubLocationsInLocationsPage();
                locationsPage.searchLocation(location);               ;
                SimpleUtils.assertOnFail("Locations not searched out Successfully!",  locationsPage.verifyUpdateLocationResult(location), false);
                locationsPage.clickOnLocationInLocationResult(location);
                locationsPage.clickOnConfigurationTabOfLocation();
                HashMap<String, String> templateTypeAndName = locationsPage.getTemplateTypeAndNameFromLocation();
                ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
                configurationPage.goToConfigurationPage();
                configurationPage.clickOnConfigurationCrad("Compliance");
                configurationPage.clickOnSpecifyTemplateName(templateTypeAndName.get("Compliance"), "edit");
                configurationPage.clickOnEditButtonOnTemplateDetailsPage();
                Thread.sleep(3000);
            }

            //Turn off meal break setting
            controlsNewUIPage.turnOnOrTurnOffMealBreakToggle(false);

            //turn on meal break setting
            controlsNewUIPage.turnOnOrTurnOffMealBreakToggle(true);

            //Edit meal break setting and click Cancel
            controlsNewUIPage.editMealBreak(ConsoleControlsNewUIPage.MealBreakDuration.Minute5.getValue(),
                    ConsoleControlsNewUIPage.MealBreakPaidType.Paid.getValue(), "10",false );

            //Edit meal break setting and click Save
            controlsNewUIPage.editMealBreak(ConsoleControlsNewUIPage.MealBreakDuration.Minute30.getValue(),
                    ConsoleControlsNewUIPage.MealBreakPaidType.Unpaid.getValue(), "6",true );

            if (getDriver().getCurrentUrl().contains(propertyMap.get(Constants.OpEnterprice))){
                ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
                configurationPage.publishNowTheTemplate();
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "Verify the meal placement violation show/not show when the start time in the five different points")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheMealPlacementViolationAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
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
            }
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange("08:00AM", "08:00PM");
            // Verify Edit Breaks is clickable in day view
            List<String> shiftInfo = new ArrayList<>();
            String firstNameOfTM = "";
            int i = 0;
            while (i< 40 &&firstNameOfTM.equals("") || firstNameOfTM.equals("Open") || firstNameOfTM.equals("Unassigned")) {
                shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getRandomIndexOfShift());
                firstNameOfTM = shiftInfo.get(0);
                i++;
            }
            String workRole = shiftInfo.get(4);
            //Delete the shift of the TM
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.deleteTMShiftInWeekView(firstNameOfTM);
            scheduleMainPage.saveSchedule();
            shiftOperatePage.convertAllUnAssignedShiftToOpenShift();

            //Create new shift for the TM
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectSpecificWorkDay(5);
            newShiftPage.moveSliderAtCertainPoint("3pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint("8am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstNameOfTM);
            newShiftPage.clickOnOfferOrAssignBtn();
            scheduleMainPage.saveSchedule();

            /*
             * - middle time = (start minutes + end minutes)/2 - meal duration/2 = (8* 60+ 15*60)/2 - 30/2 = 675 (11:15PM)
             *
             * - meal break range should be [middle time - 45 min, middle time + 45 min] = [675 - 45, 675 + 45] = [10:30am, 12pm]
             *
             * */
            //Get the index new added shift
            List<Integer> shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(firstNameOfTM);
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //Set the meal break time for the first shift to earlier than expected range
            int index = shiftIndexes.get(0);
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();
            SimpleUtils.assertOnFail("Edit Breaks dialog doesn't pop up!", shiftOperatePage.isMealBreakTimeWindowDisplayWell(true), false);
            shiftOperatePage.moveMealAndRestBreaksOnEditBreaksPage("10:00am",0,true);
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            //Set the meal break time for the second shift in expected range
            index = shiftIndexes.get(1);
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();
            SimpleUtils.assertOnFail("Edit Breaks dialog doesn't pop up!", shiftOperatePage.isMealBreakTimeWindowDisplayWell(true), false);
            shiftOperatePage.moveMealAndRestBreaksOnEditBreaksPage("11:00am",0,true);
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            //Set the meal break time for the third shift to later than expected range
            index = shiftIndexes.get(2);
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();
            SimpleUtils.assertOnFail("Edit Breaks dialog doesn't pop up!", shiftOperatePage.isMealBreakTimeWindowDisplayWell(true), false);
            shiftOperatePage.moveMealAndRestBreaksOnEditBreaksPage("1:00pm",0,true);
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            //Set the meal break time for the fourth shift to equal to expected range
            index = shiftIndexes.get(3);
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();
            SimpleUtils.assertOnFail("Edit Breaks dialog doesn't pop up!", shiftOperatePage.isMealBreakTimeWindowDisplayWell(true), false);
            shiftOperatePage.moveMealAndRestBreaksOnEditBreaksPage("12:00pm",0,true);
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            //Set the meal break time for the fifth shift to equal to expected range
            index = shiftIndexes.get(4);
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();
            SimpleUtils.assertOnFail("Edit Breaks dialog doesn't pop up!", shiftOperatePage.isMealBreakTimeWindowDisplayWell(true), false);
            shiftOperatePage.moveMealAndRestBreaksOnEditBreaksPage("10:30am",0,true);
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            scheduleMainPage.saveSchedule();
            //check compliance smart card display
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            SimpleUtils.assertOnFail("Compliance smart card display successfully!",
                    smartCardPage.verifyComplianceShiftsSmartCardShowing(), false);

            //Check the compliance violation of the shift on the first day
            SimpleUtils.assertOnFail("Meal break compliance message display failed",
                    scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup(scheduleShiftTablePage.getTheShiftByIndex(shiftIndexes.get(0)))
                            .contains("Meal Placement"), false);

            //There is no shift for the TM on the second day
            SimpleUtils.assertOnFail("Meal break compliance message should not display! ",
                    !scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup(scheduleShiftTablePage.getTheShiftByIndex(shiftIndexes.get(1)))
                            .contains("Meal Placement"), false);

            //Check the compliance violation of the shift on the third day
            SimpleUtils.assertOnFail("Meal break compliance message display failed",
                    scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup(scheduleShiftTablePage.getTheShiftByIndex(shiftIndexes.get(2)))
                            .contains("Meal Placement"), false);

            //There is no shift for the TM on the fourth day
            SimpleUtils.assertOnFail("Meal break compliance message display failed",
                    !scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup(scheduleShiftTablePage.getTheShiftByIndex(shiftIndexes.get(3)))
                            .contains("Meal Placement"), false);

            //There is no shift for the TM on the fifth day
            SimpleUtils.assertOnFail("Meal break compliance message display failed",
                    !scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup(scheduleShiftTablePage.getTheShiftByIndex(shiftIndexes.get(4)))
                            .contains("Meal Placement"), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the settings are applied according to different dynamic employee groups")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySettingsAreAppliedAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            goToSchedulePageScheduleTab();

            // Create schedule if it is not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange("08:00AM", "08:00PM");
            // Verify the duration of meal/rest for work role: Event Manager
            verifyDurationForDifferentWorkRoles(WorkRoles.EventManager.value, MealBreakDuration.EventManager.value, RestBreakDuration.EventManager.value);
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
            // Verify the duration of meal/rest for work role: General Manager
            verifyDurationForDifferentWorkRoles(WorkRoles.GeneralManager.value, MealBreakDuration.GeneralManager.value, RestBreakDuration.GeneralManager.value);
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
            // Verify the duration of meal/rest for work role: Team Member Corporate-Theatre
            verifyDurationForDifferentWorkRoles(WorkRoles.TeamMember.value, MealBreakDuration.TeamMember.value, RestBreakDuration.TeamMember.value);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    private void verifyDurationForDifferentWorkRoles(String workRole, int mealDuration, int restDuration) throws Exception {
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        scheduleMainPage.selectWorkRoleFilterByText(workRole, true);
        scheduleShiftTablePage.clickProfileIconOfShiftByIndex(0);
        shiftOperatePage.clickOnEditMeaLBreakTime();
        SimpleUtils.assertOnFail("Edit Breaks dialog doesn't pop up!", shiftOperatePage.isMealBreakTimeWindowDisplayWell(false), false);
        if ((mealDuration == shiftOperatePage.getTheDurationOfBreaks(true)) &&
                (restDuration == shiftOperatePage.getTheDurationOfBreaks(false))) {
            SimpleUtils.pass("Meal and Rest break durations for " + workRole + " is correct!");
        } else {
            SimpleUtils.fail("Meal and Rest break durations for " + workRole + " is incorrect!", false);
        }
        shiftOperatePage.clickOnOKBtnOnMealBreakDialog();
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify \"Missed Meal Break\" violation when turning on MealAndRestTemplate toggle")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyMissedMealBreakWhenUsingTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            goToSchedulePageScheduleTab();

            // Create schedule if it is not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange("08:00AM", "08:00PM");
            // Verify warning dialog will show up when deleting the meal break
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            int count = scheduleShiftTablePage.getShiftsCount();
            int index = (new Random()).nextInt(count);
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();
            SimpleUtils.assertOnFail("Edit Breaks dialog doesn't pop up!", shiftOperatePage.isMealBreakTimeWindowDisplayWell(true), false);
            shiftOperatePage.deleteMealOrRestBreaks(true);
            scheduleMainPage.saveSchedule();
            // Verify "Missed Meal Break" violation
            scheduleShiftTablePage.verifyComplianceForShiftByIndex(MealRestBreakViolations.MissedMealBreak.value, index);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify \"Meal Duration\" violation when turning on MealAndRestTemplate toggle")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyMealDurationWhenUsingTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            goToSchedulePageScheduleTab();

            // Create schedule if it is not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange("08:00AM", "08:00PM");
            // Verify warning dialog will show up when deleting the meal break
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            int count = scheduleShiftTablePage.getShiftsCount();
            int index = (new Random()).nextInt(count);
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();
            SimpleUtils.assertOnFail("Edit Breaks dialog doesn't pop up!", shiftOperatePage.isMealBreakTimeWindowDisplayWell(true), false);
            // Verify warning dialog will show up when shortening the meal break
            shiftOperatePage.shortenMealOrRestBreak(true);
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            // Verify the pencil icon will show
            shiftOperatePage.verifySpecificShiftHaveEditIcon(index);
            scheduleMainPage.saveSchedule();
            // Verify "Meal Duration" violation
            scheduleShiftTablePage.verifyComplianceForShiftByIndex(MealRestBreakViolations.MealDuration.value, index);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify \"Meal Placement\" violation when turning on MealAndRestTemplate toggle")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyMealPlacementWhenUsingTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            goToSchedulePageScheduleTab();

            // Create schedule if it is not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange("08:00AM", "08:00PM");
            // Verify warning dialog will show up when deleting the meal break
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            int count = scheduleShiftTablePage.getShiftsCount();
            int index = (new Random()).nextInt(count);
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();
            SimpleUtils.assertOnFail("Edit Breaks dialog doesn't pop up!", shiftOperatePage.isMealBreakTimeWindowDisplayWell(true), false);
            // Verify warning dialog will show up when shortening the meal break
            shiftOperatePage.moveMealOrRestBreak(true, 100);
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            // Verify the pencil icon will show
            shiftOperatePage.verifySpecificShiftHaveEditIcon(index);
            scheduleMainPage.saveSchedule();
            // Verify "Meal Placement" violation
            scheduleShiftTablePage.verifyComplianceForShiftByIndex(MealRestBreakViolations.MealPlacement.value, index);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify \"Missed Rest Break\" violation when turning on MealAndRestTemplate toggle")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyMissedRestBreakWhenUsingTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            goToSchedulePageScheduleTab();

            // Create schedule if it is not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange("08:00AM", "08:00PM");
            // Verify warning dialog will show up when deleting the meal break
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleMainPage.selectWorkRoleFilterByText(WorkRoles.GeneralManager.value, true);
            int count = scheduleShiftTablePage.getShiftsCount();
            int index = (new Random()).nextInt(count);
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();
            SimpleUtils.assertOnFail("Edit Breaks dialog doesn't pop up!", shiftOperatePage.isMealBreakTimeWindowDisplayWell(true), false);
            shiftOperatePage.deleteMealOrRestBreaks(false);
            // Verify the pencil icon will show
            shiftOperatePage.verifySpecificShiftHaveEditIcon(index);
            scheduleMainPage.saveSchedule();
            // Verify "Missed Rest Break" violation
            scheduleShiftTablePage.verifyComplianceForShiftByIndex(MealRestBreakViolations.MissedRestBreak.value, index);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify \"Short Rest Break\" violation when turning on MealAndRestTemplate toggle")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyShortRestBreakWhenUsingTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            goToSchedulePageScheduleTab();

            // Create schedule if it is not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange("08:00AM", "08:00PM");
            // Verify warning dialog will show up when deleting the meal break
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleMainPage.selectWorkRoleFilterByText(WorkRoles.GeneralManager.value, true);
            int count = scheduleShiftTablePage.getShiftsCount();
            int index = (new Random()).nextInt(count);
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();
            SimpleUtils.assertOnFail("Edit Breaks dialog doesn't pop up!", shiftOperatePage.isMealBreakTimeWindowDisplayWell(true), false);
            shiftOperatePage.shortenMealOrRestBreak(false);
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            // Verify the pencil icon will show
            shiftOperatePage.verifySpecificShiftHaveEditIcon(index);
            scheduleMainPage.saveSchedule();
            // Verify "Short Rest Break" violation
            scheduleShiftTablePage.verifyComplianceForShiftByIndex(MealRestBreakViolations.ShortRestBreak.value, index);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify \"Rest Time Not Ideal\" violation when turning on MealAndRestTemplate toggle")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyRestTimeNotIdealWhenUsingTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            goToSchedulePageScheduleTab();

            // Create schedule if it is not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange("08:00AM", "11:00PM");
            // Verify warning dialog will show up when deleting the meal break
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleMainPage.selectWorkRoleFilterByText(WorkRoles.TeamMember.value, true);
            int count = scheduleShiftTablePage.getShiftsCount();
            int index = (new Random()).nextInt(count);
            List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            String shiftStartTime = shiftInfo.get(2).split("-")[0].trim();
            String restBreakStartTime = getRestBreakStartTime(shiftStartTime, 225 + 5);
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();
            SimpleUtils.assertOnFail("Edit Breaks dialog doesn't pop up!", shiftOperatePage.isMealBreakTimeWindowDisplayWell(true), false);
            shiftOperatePage.deleteMealOrRestBreaks(true);
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();
            shiftOperatePage.moveMealAndRestBreaksOnEditBreaksPage(restBreakStartTime, 0, false);
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            scheduleMainPage.saveSchedule();
            // Verify "Rest Time Not Ideal" violation when the rest break starts longer than 225 minutes from the shift start time
            scheduleShiftTablePage.verifyComplianceForShiftByIndex(MealRestBreakViolations.RestTimeNotIdeal.value, index);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    private static String getRestBreakStartTime(String shiftStartTime, int minutes) {
        int tempMinutes = SimpleUtils.getMinutesFromTime(shiftStartTime) + minutes;
        return SimpleUtils.convertMinutesToTime(tempMinutes);
    }
}
