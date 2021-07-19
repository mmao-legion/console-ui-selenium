package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.*;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import java.util.*;


public class LiquidDashboardTest extends TestBase {

    private HashMap<String, Object[][]> swapCoverCredentials = null;
    private List<String> swapCoverNames = null;
    private String workRoleName = "";

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

    public enum widgetType{
        Helpful_Links("helpful links"),
        Todays_Forecast("today’s forecast"),
        Schedules("schedules"),
        Timesheet_Approval_Status("timesheet approval status"),
        Timesheet_Approval_Rate("timesheet approval rate"),
        Alerts("alerts"),
        Swaps_Covers("swaps & covers"),
        Starting_Soon("starting soon"),
        Open_Shifts("open shifts"),
        Compliance_Violation("compliance violation");
        private final String value;
        widgetType(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    public enum linkNames{
        View_Schedules("view schedules"),
        View_TimeSheets("view timesheets"),
        View_Schedule("view schedule"),
        View_Forecast("view forecast");
        private final String value;
        linkNames(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify UI for common widget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyCommonUIOfWidgetsAsStoreManager(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
            // Verify Edit mode Dashboard loaded
            liquidDashboardPage.enterEditMode();

            //verify switch off Todays_Forcast widget
            liquidDashboardPage.switchOffWidget(widgetType.Todays_Forecast.getValue());
            //verify switch on Todays_Forcast widget
            liquidDashboardPage.switchOnWidget(widgetType.Todays_Forecast.getValue());
            //verify close Todays_Forcast widget
            liquidDashboardPage.closeWidget(widgetType.Todays_Forecast.getValue());
            liquidDashboardPage.switchOnWidget(widgetType.Todays_Forecast.getValue());


            //verify switch off Timesheet_Approval_Status widget
            liquidDashboardPage.switchOffWidget(widgetType.Timesheet_Approval_Status.getValue());
            //verify switch on Timesheet_Approval_Status widget
            liquidDashboardPage.switchOnWidget(widgetType.Timesheet_Approval_Status.getValue());
            //verify close Timesheet_Approval_Status widget
            liquidDashboardPage.closeWidget(widgetType.Timesheet_Approval_Status.getValue());
            liquidDashboardPage.switchOnWidget(widgetType.Timesheet_Approval_Status.getValue());

/*
        //verify switch off Timesheet_Approval_Rate widget
        liquidDashboardPage.switchOffWidget(widgetType.Timesheet_Approval_Rate.getValue());
        //verify switch on Timesheet_Approval_Rate widget
        liquidDashboardPage.switchOnWidget(widgetType.Timesheet_Approval_Rate.getValue());
        //verify close Timesheet_Approval_Rate widget
        liquidDashboardPage.closeWidget(widgetType.Timesheet_Approval_Rate.getValue());
        liquidDashboardPage.switchOnWidget(widgetType.Timesheet_Approval_Rate.getValue());
*/

            //verify switch off Alerts widget
            liquidDashboardPage.switchOffWidget(widgetType.Alerts.getValue());
            //verify switch on Alerts widget
            liquidDashboardPage.switchOnWidget(widgetType.Alerts.getValue());
            //verify close Alerts widget
            liquidDashboardPage.closeWidget(widgetType.Alerts.getValue());
            liquidDashboardPage.switchOnWidget(widgetType.Alerts.getValue());

/*
        //verify switch off Swaps_Covers widget
        liquidDashboardPage.switchOffWidget(widgetType.Swaps_Covers.getValue());
        //verify switch on Swaps_Covers widget
        liquidDashboardPage.switchOnWidget(widgetType.Swaps_Covers.getValue());
        //verify close Swaps_Covers widget
        liquidDashboardPage.closeWidget(widgetType.Swaps_Covers.getValue());
        liquidDashboardPage.switchOnWidget(widgetType.Swaps_Covers.getValue());
*/

            //verify switch off Starting_Soon widget
            liquidDashboardPage.switchOffWidget(widgetType.Starting_Soon.getValue());
            //verify switch on Starting_Soon widget
            liquidDashboardPage.switchOnWidget(widgetType.Starting_Soon.getValue());
            //verify close Starting_Soon widget
            liquidDashboardPage.closeWidget(widgetType.Starting_Soon.getValue());
            liquidDashboardPage.switchOnWidget(widgetType.Starting_Soon.getValue());


            //verify switch off Schedules widget
            liquidDashboardPage.switchOffWidget(widgetType.Schedules.getValue());
            //verify switch on Schedules widget
            liquidDashboardPage.switchOnWidget(widgetType.Schedules.getValue());
            //verify close Schedules widget
            liquidDashboardPage.closeWidget(widgetType.Schedules.getValue());
            liquidDashboardPage.switchOnWidget(widgetType.Schedules.getValue());

/*
        //verify switch off Open_Shifts widget
        liquidDashboardPage.switchOffWidget(widgetType.Open_Shifts.getValue());
        //verify switch on Open_Shifts widget
        liquidDashboardPage.switchOnWidget(widgetType.Open_Shifts.getValue());
        //verify close Open_Shifts widget
        liquidDashboardPage.closeWidget(widgetType.Open_Shifts.getValue());
        liquidDashboardPage.switchOnWidget(widgetType.Open_Shifts.getValue());
*/

            //verify switch off compliance violation widget
            liquidDashboardPage.switchOffWidget(widgetType.Compliance_Violation.getValue());
            //verify switch on compliance violation widget
            liquidDashboardPage.switchOnWidget(widgetType.Compliance_Violation.getValue());
            //verify close compliance violation widget
            liquidDashboardPage.closeWidget(widgetType.Compliance_Violation.getValue());
            liquidDashboardPage.switchOnWidget(widgetType.Compliance_Violation.getValue());

            //verify switch off helpful links widget
            liquidDashboardPage.switchOffWidget(widgetType.Helpful_Links.getValue());
            //verify switch on helpful links widget
            liquidDashboardPage.switchOnWidget(widgetType.Helpful_Links.getValue());
            //verify close helpful links widget
            liquidDashboardPage.closeWidget(widgetType.Helpful_Links.getValue());
            liquidDashboardPage.switchOnWidget(widgetType.Helpful_Links.getValue());
            //verify back button to get out of manage page
            liquidDashboardPage.verifyBackBtn();
            //verify if there is update time info icon
            liquidDashboardPage.saveAndExitEditMode();
            liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Compliance_Violation.getValue());
            //liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Open_Shifts.getValue());
            liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Schedules.getValue());
            liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Starting_Soon.getValue());
            //liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Swaps_Covers.getValue());
            liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Alerts.getValue());
            //liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Timesheet_Approval_Rate.getValue());
            liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Timesheet_Approval_Status.getValue());
            liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Helpful_Links.getValue());
            liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Todays_Forecast.getValue());
            //verify search input
            liquidDashboardPage.enterEditMode();
            liquidDashboardPage.verifySearchInput(widgetType.Helpful_Links.getValue());
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Prepare the data for swap")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void prepareTheSwapShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            swapCoverNames = new ArrayList<>();
            swapCoverCredentials = getSwapCoverUserCredentials(location);
            for (Map.Entry<String, Object[][]> entry : swapCoverCredentials.entrySet()) {
                swapCoverNames.add(entry.getKey());
            }
            workRoleName = String.valueOf(swapCoverCredentials.get(swapCoverNames.get(0))[0][3]);
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();
            // Deleting the existing shifts for swap team members
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView(swapCoverNames.get(0));
            schedulePage.deleteTMShiftInWeekView(swapCoverNames.get(1));
            schedulePage.deleteTMShiftInWeekView("Unassigned");
            schedulePage.saveSchedule();
            // Add the new shifts for swap team members
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.addNewShiftsByNames(swapCoverNames, workRoleName);
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the content of Swaps & Covers section")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOfSwapNCoverWidgetAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            prepareTheSwapShiftsAsInternalAdmin(browser, username, password, location);
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            Object[][] credential = swapCoverCredentials.get(swapCoverNames.get(0));
            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                    , String.valueOf(credential[0][2]));
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String requestUserName = profileNewUIPage.getNickNameFromProfile();
            if (dashboardPage.isSwitchToEmployeeViewPresent()) {
                dashboardPage.clickOnSwitchToEmployeeView();
            }
            SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
            schedulePage.isSchedule();
            schedulePage.navigateToNextWeek();

            // For Swap Feature
            List<String> swapCoverRequsts = new ArrayList<>(Arrays.asList("Request to Swap Shift", "Request to Cover Shift"));
            int index = schedulePage.verifyClickOnAnyShift();
            String request = "Request to Swap Shift";
            String title = "Find Shifts to Swap";
            schedulePage.clickTheShiftRequestByName(request);
            SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), true);
            schedulePage.verifyComparableShiftsAreLoaded();
            schedulePage.verifySelectMultipleSwapShifts();
            // Validate the Submit button feature
            schedulePage.verifyClickOnNextButtonOnSwap();
            title = "Submit Swap Request";
            SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), false);
            schedulePage.verifyClickOnSubmitButton();
            // Validate the disappearence of Request to Swap and Request to Cover option
            schedulePage.clickOnShiftByIndex(index);
            if (!schedulePage.verifyShiftRequestButtonOnPopup(swapCoverRequsts)) {
                SimpleUtils.pass("Request to Swap and Request to Cover options are disappear");
            } else {
                SimpleUtils.fail("Request to Swap and Request to Cover options are still shown!", false);
            }

            loginPage.logOut();
            credential = swapCoverCredentials.get(swapCoverNames.get(1));
            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                    , String.valueOf(credential[0][2]));
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            String respondUserName = profileNewUIPage.getNickNameFromProfile();
            if (dashboardPage.isSwitchToEmployeeViewPresent()) {
                dashboardPage.clickOnSwitchToEmployeeView();
            }
            dashboardPage.goToTodayForNewUI();
            schedulePage.isSchedule();
            schedulePage.navigateToNextWeek();

            // Validate that swap request smartcard is available to recipient team member
            String smartCard = "SWAP REQUESTS";
            schedulePage.isSmartCardAvailableByLabel(smartCard);
            // Validate the availability of all swap request shifts in schedule table
            String linkName = "View All";
            schedulePage.clickLinkOnSmartCardByName(linkName);
            schedulePage.verifySwapRequestShiftsLoaded();
            // Validate that recipient can claim the swap request shift.
            schedulePage.verifyClickAcceptSwapButton();

            loginPage.logOut();

            loginToLegionAndVerifyIsLoginDone(username, password, location);
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
            if (!liquidDashboardPage.isSpecificWidgetLoaded(widgetType.Swaps_Covers.getValue())) {
                // Verify Edit mode Dashboard loaded
                liquidDashboardPage.enterEditMode();
                //verify switch on Swaps&Covers widget
                liquidDashboardPage.switchOnWidget(widgetType.Swaps_Covers.getValue());
                // Exit Edit mode
                liquidDashboardPage.saveAndExitEditMode();
            }
            if (liquidDashboardPage.isSpecificWidgetLoaded(widgetType.Swaps_Covers.getValue())) {
                liquidDashboardPage.clickOnCarouselOnWidget(widgetType.Swaps_Covers.getValue(), "right");
                String swapOrCover = "Swap";
                liquidDashboardPage.verifyTheContentOfSwapNCoverWidget(swapOrCover);
            } else {
                SimpleUtils.fail("\"Swaps & Covers\" widget not loaded Successfully!", false);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate no content of Swaps & Covers section")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyNoContentOfSwapNCoverWidgetAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            dashboardPage.navigateToDashboard();

            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
            if (!liquidDashboardPage.isSpecificWidgetLoaded(widgetType.Swaps_Covers.getValue())) {
                // Verify Edit mode Dashboard loaded
                liquidDashboardPage.enterEditMode();
                //verify switch on Swaps&Covers widget
                liquidDashboardPage.switchOnWidget(widgetType.Swaps_Covers.getValue());
                // Exit Edit mode
                liquidDashboardPage.saveAndExitEditMode();
            }
            // Make sure that Schedules widget is loaded, we can compare the current week from Schedules widget
            if (!liquidDashboardPage.isSpecificWidgetLoaded(widgetType.Schedules.getValue())) {
                // Verify Edit mode Dashboard loaded
                liquidDashboardPage.enterEditMode();
                //verify switch on Schedules widget
                liquidDashboardPage.switchOnWidget(widgetType.Schedules.getValue());
                // Exit Edit mode
                liquidDashboardPage.saveAndExitEditMode();
            }
            if (liquidDashboardPage.isSpecificWidgetLoaded(widgetType.Swaps_Covers.getValue())) {
                //verify week info on widget
                //gp to schedule, get week info of current week, last week and next week.
                liquidDashboardPage.clickFirstWeekOnSchedulesGoToSchedule();
                String startDayOfLastWeek = schedulePage.getActiveWeekText().split(" - ")[1];
                schedulePage.navigateToNextWeek();
                String startDayOfCurrentWeek = schedulePage.getActiveWeekText().split(" - ")[1];
                schedulePage.navigateToNextWeek();
                String startDayOfNextWeek = schedulePage.getActiveWeekText().split(" - ")[1];

                //go back to dashboard to verify week info on widget is consistent with the ones in schedule.
                dashboardPage.navigateToDashboard();
                SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
                liquidDashboardPage.verifyNoContentOfSwapsNCoversWidget();

                liquidDashboardPage.verifyWeekInfoOnWidget(widgetType.Swaps_Covers.getValue(), startDayOfCurrentWeek);
                //click on carousel to navigate to last week and next week to verify
                liquidDashboardPage.clickOnCarouselOnWidget(widgetType.Swaps_Covers.getValue(), "left");
                liquidDashboardPage.verifyWeekInfoOnWidget(widgetType.Swaps_Covers.getValue(), startDayOfLastWeek);
                liquidDashboardPage.clickOnCarouselOnWidget(widgetType.Swaps_Covers.getValue(), "left");
                liquidDashboardPage.verifyWeekInfoOnWidget(widgetType.Swaps_Covers.getValue(), startDayOfNextWeek);
            } else {
                SimpleUtils.fail("\"Swaps & Covers\" widget not loaded Successfully!", false);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Validate the content of Timesheet Approval Status widget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOfTimesheetApprovalStatusWidgetAsStoreManager(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();

            if (!liquidDashboardPage.isSpecificWidgetLoaded(widgetType.Timesheet_Approval_Status.getValue())) {
                // Verify Edit mode Dashboard loaded
                liquidDashboardPage.enterEditMode();

                //verify switch on Starting_Soon widget
                liquidDashboardPage.switchOnWidget(widgetType.Timesheet_Approval_Status.getValue());
                // Exit Edit mode
                liquidDashboardPage.saveAndExitEditMode();
            }
            // Make sure that Schedules widget is loaded, we can compare the current week from Schedules widget
            if (!liquidDashboardPage.isSpecificWidgetLoaded(widgetType.Schedules.getValue())) {
                // Verify Edit mode Dashboard loaded
                liquidDashboardPage.enterEditMode();
                //verify switch on Starting_Soon widget
                liquidDashboardPage.switchOnWidget(widgetType.Schedules.getValue());
                // Exit Edit mode
                liquidDashboardPage.saveAndExitEditMode();
            }

            String currentWeek = liquidDashboardPage.getTheStartOfCurrentWeekFromSchedulesWidget();
            // Verify the content on Timesheet Approval Status Widget
            if (liquidDashboardPage.isSpecificWidgetLoaded(widgetType.Timesheet_Approval_Status.getValue())) {
                liquidDashboardPage.verifyTheContentOnTimesheetApprovalStatusWidgetLoaded(currentWeek);
                int approvalRate = liquidDashboardPage.getTimeSheetApprovalStatusFromPieChart();
                liquidDashboardPage.clickOnLinkByWidgetNameAndLinkName(widgetType.Timesheet_Approval_Status.getValue(), linkNames.View_TimeSheets.getValue());
                SimpleUtils.assertOnFail("Timesheet page not loaded Successfully!", timeSheetPage.isTimeSheetPageLoaded(), false);
                timeSheetPage.verifyCurrentWeekIsSelectedByDefault(currentWeek);
                int approvalRateOnTimesheet = timeSheetPage.getApprovalRateFromTimesheetByLocation(location);
                if (approvalRate == approvalRateOnTimesheet) {
                    SimpleUtils.pass("Verified the TimeSheet Approval Rate on dashboard is consistent with Timesheet Page!");
                } else {
                    SimpleUtils.warn("Timesheet Approval rate is inconsistent, dashboard: " + approvalRate + ", and Timesheet page: " + approvalRateOnTimesheet
                            + ". Failed Since this bug: https://legiontech.atlassian.net/browse/SF-287");
                }
            } else {
                SimpleUtils.fail("\"Timesheet Approval Status\" widget not loaded Successfully!", false);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the content Starting Soon section")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOfStartingSoonWidgetAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();

            if (!liquidDashboardPage.isSpecificWidgetLoaded(widgetType.Starting_Soon.getValue())) {
                // Verify Edit mode Dashboard loaded
                liquidDashboardPage.enterEditMode();

                //verify switch on Starting_Soon widget
                liquidDashboardPage.switchOnWidget(widgetType.Starting_Soon.getValue());
                // Exit Edit mode
                liquidDashboardPage.saveAndExitEditMode();
            }

            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView("Unassigned");
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            dashboardPage.navigateToDashboard();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Get the current upcoming shifts
            HashMap<String, String> upComingShifts = new HashMap<>();
            boolean areShiftsLoaded = dashboardPage.isStartingSoonLoaded();
            if (areShiftsLoaded) {
                upComingShifts = dashboardPage.getUpComingShifts();
                // Verify click on "View Schedule" link
                liquidDashboardPage.clickOnLinkByWidgetNameAndLinkName(widgetType.Starting_Soon.getValue(), linkNames.View_Schedule.getValue());
                schedulePage.isSchedule();
                String timeFromDashboard = dashboardPage.getDateFromTimeZoneOfLocation("hh:mm aa");
                HashMap<String, String> shiftsFromDayView = schedulePage.getFourUpComingShifts(false, timeFromDashboard);
                schedulePage.verifyUpComingShiftsConsistentWithSchedule(upComingShifts, shiftsFromDayView);
            } else {
                SimpleUtils.fail("No upcoming shifts loaded!", false);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate no content Starting Soon section")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyNoContentInStartingSoonWidgetAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();

            if (!liquidDashboardPage.isSpecificWidgetLoaded(widgetType.Starting_Soon.getValue())) {
                // Verify Edit mode Dashboard loaded
                liquidDashboardPage.enterEditMode();

                //verify switch on Starting_Soon widget
                liquidDashboardPage.switchOnWidget(widgetType.Starting_Soon.getValue());
                // Exit Edit mode
                liquidDashboardPage.saveAndExitEditMode();
            }

            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            // Ungenerate the schedule from current week
            schedulePage.unGenerateActiveScheduleFromCurrentWeekOnward(0);

            // Navigate to dashboard page to check there should no shifts in Starting soon, there should show "No published shifts for today"
            dashboardPage.navigateToDashboard();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            dashboardPage.clickOnRefreshButton();

            boolean areShiftsLoaded = dashboardPage.isStartingSoonLoaded();
            if (!areShiftsLoaded) {
                SimpleUtils.pass("There are no shifts after ungenerating the schedule from current week onward!");
            } else {
                SimpleUtils.fail("There still shows the upcoming shifts after ungenerating the schedule from current week onward!", false);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Validate the content of Alerts widget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOfAlertWidgetAsStoreManager(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();

            if (!liquidDashboardPage.isSpecificWidgetLoaded(widgetType.Alerts.getValue())) {
                // Verify Edit mode Dashboard loaded
                liquidDashboardPage.enterEditMode();
                //verify switch on Starting_Soon widget
                liquidDashboardPage.switchOnWidget(widgetType.Alerts.getValue());
                // Exit Edit mode
                liquidDashboardPage.saveAndExitEditMode();
            }

            // Make sure that Schedules widget is loaded, we can compare the current week from Schedules widget
            if (!liquidDashboardPage.isSpecificWidgetLoaded(widgetType.Schedules.getValue())) {
                // Verify Edit mode Dashboard loaded
                liquidDashboardPage.enterEditMode();
                //verify switch on Starting_Soon widget
                liquidDashboardPage.switchOnWidget(widgetType.Schedules.getValue());
                // Exit Edit mode
                liquidDashboardPage.saveAndExitEditMode();
            }

            dashboardPage.clickOnRefreshButton();

            String currentWeek = liquidDashboardPage.getTheStartOfCurrentWeekFromSchedulesWidget();
            List<String> alertsFromDashboard = new ArrayList<>();
            // Verify the content on Alerts Widget
            if (liquidDashboardPage.isSpecificWidgetLoaded(widgetType.Alerts.getValue())) {
                alertsFromDashboard = liquidDashboardPage.verifyTheContentOnAlertsWidgetLoaded(currentWeek);
            } else {
                SimpleUtils.fail("\"Alerts\" widget not loaded Successfully!", false);
            }
            liquidDashboardPage.clickOnLinkByWidgetNameAndLinkName(widgetType.Alerts.getValue(), linkNames.View_TimeSheets.getValue());
            SimpleUtils.assertOnFail("Timesheet page not loaded Successfully!", timeSheetPage.isTimeSheetPageLoaded(), false);
            List<String> alertsFromTimesheet = timeSheetPage.getAlertsDataFromSmartCard();

            if (alertsFromDashboard.containsAll(alertsFromTimesheet) && alertsFromTimesheet.containsAll(alertsFromDashboard)) {
                SimpleUtils.pass("Alerts data on Dashboard is consistent with Timesheet page!");
            } else {
                SimpleUtils.fail("Alerts data on Dashboard is inconsistent with Timesheet page!", false);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "verify Helpful Links")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyHelpfulLinksWidgetsAsStoreManager(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
            // Verifiy Edit mode Dashboard loaded
            liquidDashboardPage.enterEditMode();
            liquidDashboardPage.switchOnWidget(widgetType.Helpful_Links.getValue());
            //verify there are 5 link at most
            liquidDashboardPage.verifyEditLinkOfHelpfulLinks();
            liquidDashboardPage.deleteAllLinks();
            liquidDashboardPage.saveLinks();
            liquidDashboardPage.verifyNoLinksOnHelpfulLinks();
            liquidDashboardPage.verifyEditLinkOfHelpfulLinks();
            liquidDashboardPage.deleteAllLinks();
            for (int i=0;i<6;i++){ //the 6th is to verify no add link button
                liquidDashboardPage.addLinkOfHelpfulLinks();
            }
            liquidDashboardPage.saveLinks();
            liquidDashboardPage.verifyEditLinkOfHelpfulLinks();
            liquidDashboardPage.cancelLinks();
            liquidDashboardPage.saveAndExitEditMode();
            //verify links
            liquidDashboardPage.verifyLinks();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify Today's Forecast widget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTodayForecastWidgetsAsStoreManager(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
            if (!liquidDashboardPage.isSpecificWidgetLoaded(widgetType.Todays_Forecast.getValue())) {
                // Verify Edit mode Dashboard loaded
                liquidDashboardPage.enterEditMode();
                liquidDashboardPage.switchOnWidget(widgetType.Todays_Forecast.getValue());
                liquidDashboardPage.saveAndExitEditMode();
            }
            //verify there is a graph
            liquidDashboardPage.verifyIsGraphExistedOnWidget();
            HashMap <String,Float> dataOnWidget = liquidDashboardPage.getDataOnTodayForecast();
            //verify view forecast link
            liquidDashboardPage.clickOnLinkByWidgetNameAndLinkName(widgetType.Todays_Forecast.getValue(),linkNames.View_Forecast.getValue());
            //verify value on widget
            ForecastPage forecastPage = pageFactory.createForecastPage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            HashMap <String,Float> insightDataFromForecastPage = forecastPage.getInsightDataInShopperWeekView();
            schedulePage.clickOnScheduleSubTab("Schedule");
            HashMap <String,Float> dataFromSchedule = schedulePage.getScheduleLabelHoursAndWages();
            String enterprise = SimpleUtils.getEnterprise(this.enterpriseName);
            if (enterprise.contains("KendraScott2_Enterprise")){
                if (dataOnWidget.get("demand forecast") <= insightDataFromForecastPage.get("totalShoppers") && dataOnWidget.get("demand forecast") >= insightDataFromForecastPage.get("totalShoppers")){
                    SimpleUtils.pass("Demand Forecast number is correct!");
                } else {
                    SimpleUtils.fail("today's forecast widget: Demand Forecast number is not correct!",true);
                }
            } else {
                if (dataOnWidget.get("demand forecast") <= insightDataFromForecastPage.get("totalItems") && dataOnWidget.get("demand forecast") >= insightDataFromForecastPage.get("totalItems")){
                    SimpleUtils.pass("Demand Forecast number is correct!");
                } else {
                    SimpleUtils.fail("today's forecast widget: Demand Forecast number is not correct!",true);
                }
            }

            if (dataOnWidget.get("budget") >= dataFromSchedule.get("budgetedHours")&&dataOnWidget.get("budget") <= dataFromSchedule.get("budgetedHours")){
                SimpleUtils.pass("budget number is correct!");
            } else {
                SimpleUtils.fail("today's forecast widget: budget number is not correct!",true);
            }
            if (dataOnWidget.get("scheduled") <= dataFromSchedule.get("scheduledHours")&&dataOnWidget.get("scheduled") >= dataFromSchedule.get("scheduledHours")){
                SimpleUtils.pass("scheduledHours number is correct!");
            } else {
                SimpleUtils.fail("today's forecast widget: scheduledHours number is not correct!",true);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Julie/Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Schedules widget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySchedulesWidgetAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();

            // Verify Edit mode Dashboard loaded
            liquidDashboardPage.enterEditMode();
            liquidDashboardPage.switchOnWidget(widgetType.Schedules.getValue());
            liquidDashboardPage.saveAndExitEditMode();

            // Refresh the dashboard to get the value updated
            dashboardPage.clickOnRefreshButton();

            //verify view schedules link
            List<String> resultListOnWidget = liquidDashboardPage.getDataOnSchedulesWidget();
            liquidDashboardPage.clickOnLinkByWidgetNameAndLinkName(widgetType.Schedules.getValue(),linkNames.View_Schedules.getValue());

            //verify value on widget
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            List<String> resultListInOverview = schedulePage.getOverviewData();
            if (resultListOnWidget.size() == 4){
                for (int i=0;i<resultListInOverview.size();i++){
                    boolean flag = resultListInOverview.get(i).equals(resultListOnWidget.get(i));
                    if (flag){
                        SimpleUtils.pass("Schedules widget: Values on widget are consistent with the one in overview");
                    } else {
                        SimpleUtils.fail("Schedules widget: Values on widget are not consistent with the one in overview!",false);
                    }
                }
            } else {
                SimpleUtils.fail("Schedules widget: something wrong with the number of week displayed!",true);
            }

            // Verify the schedule status is Guidance in Overview
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated && schedulePage.isDeleteScheduleButtonLoaded()) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            resultListInOverview = schedulePage.getOverviewData();
            if (resultListInOverview.size() > 2 && resultListInOverview.get(1).contains(",")) {
                if (resultListInOverview.get(1).split(",").length > 4) {
                    String currentStatus = resultListInOverview.get(1).split(",")[3].replace(" ", "");
                    if (currentStatus.equals("Guidance"))
                        SimpleUtils.pass("Schedule Page: The current week's status in 'Overview' is Guidance as expected");
                    else
                        SimpleUtils.fail("Schedule Page: The current week's status in 'Overview' is " + currentStatus + ", not Guidance",false);
                } else
                    SimpleUtils.fail("Schedule Page: The result item's length in 'Overview' is incorrect",false);
            } else
                SimpleUtils.fail("Schedule Page: The results list's size in 'Overview' is incorrect",false);

            // Verify the Refresh button functionality on SM dashboard
            dashboardPage.navigateToDashboard();
            dashboardPage.clickOnRefreshButton();
            dashboardPage.validateRefreshPerformance();

            // Verify the latest timestamp should be Just Updated
            dashboardPage.validateRefreshTimestamp();

            // Verify the status of the schedule is Guidance in Schedules widget
            resultListOnWidget = liquidDashboardPage.getDataOnSchedulesWidget();
            if (resultListOnWidget.size() > 3 && resultListOnWidget.get(1).contains(",")) {
                if (resultListOnWidget.get(1).split(",").length > 4) {
                    String currentStatus = resultListOnWidget.get(1).split(",")[3].replace(" ", "");
                    if (currentStatus.equals("Guidance"))
                        SimpleUtils.pass("Liquid Dashboard: The current week's status in 'Schedules' widget is Guidance as expected");
                    else
                        SimpleUtils.fail("Liquid Dashboard: The current week's status in 'Schedules' widget is " + currentStatus + ", not Guidance",true);
                } else
                    SimpleUtils.fail("Liquid Dashboard: The result item's length in 'Schedules' widget  is incorrect",false);
            } else
                SimpleUtils.fail("Liquid Dashboard: The results list's size in 'Schedules' widget is incorrect",false);

            // Verify the status of the schedule is Draft in Overview
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            resultListInOverview = schedulePage.getOverviewData();
            if (resultListInOverview.size() > 2 && resultListInOverview.get(1).contains(",")) {
                if (resultListInOverview.get(1).split(",").length > 4) {
                    String currentStatus = resultListInOverview.get(1).split(",")[3].replace(" ", "");
                    if (currentStatus.equals("Draft"))
                        SimpleUtils.pass("Schedule Page: The current week's status in 'Overview' is Draft as expected");
                    else
                        SimpleUtils.fail("Schedule Page: The current week's status in 'Overview' is " + currentStatus + ", not Draft",false);
                } else
                    SimpleUtils.fail("Schedule Page: The result item's length in 'Overview' is incorrect",false);
            } else
                SimpleUtils.fail("Schedule Page: The results list's size in 'Overview' is incorrect",false);

            // Verify the status of the schedule is Draft in Schedules widget
            dashboardPage.navigateToDashboard();
            dashboardPage.clickOnRefreshButton();
            resultListOnWidget = liquidDashboardPage.getDataOnSchedulesWidget();
            if (resultListOnWidget.size() > 3 && resultListOnWidget.get(1).contains(",")) {
                if (resultListOnWidget.get(1).split(",").length > 4) {
                    String currentStatus = resultListOnWidget.get(1).split(",")[3].replace(" ", "");
                    if (currentStatus.equals("Draft"))
                        SimpleUtils.pass("Liquid Dashboard: The current week's status in 'Schedules' widget is Draft as expected");
                    else
                        SimpleUtils.fail("Liquid Dashboard: The current week's status in 'Schedules' widget is " + currentStatus + ", not Daft",true);
                } else
                    SimpleUtils.fail("Liquid Dashboard: The result item's length in 'Schedules' widget  is incorrect",false);
            } else
                SimpleUtils.fail("Liquid Dashboard: The results list's size in 'Schedules' widget is incorrect",false);

            // Verify the status of the schedule is Finalized in Overview
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteAllOOOHShiftInWeekView();
            schedulePage.deleteTMShiftInWeekView("Unassigned");
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            resultListInOverview = schedulePage.getOverviewData();
            if (resultListInOverview.size() > 2 && resultListInOverview.get(1).contains(",")) {
                if (resultListInOverview.get(1).split(",").length > 4) {
                    String currentStatus = resultListInOverview.get(1).split(",")[3].replace(" ", "");
                    if (currentStatus.equals("Finalized"))
                        SimpleUtils.pass("Schedule Page: The current week's status in 'Overview' is Finalized as expected");
                    else
                        SimpleUtils.fail("Schedule Page: The current week's status in 'Overview' is " + currentStatus + ", not Finalized",false);
                } else
                    SimpleUtils.fail("Schedule Page: The result item's length in 'Overview' is incorrect",false);
            } else
                SimpleUtils.fail("Schedule Page: The results list's size in 'Overview' is incorrect",false);

            // Verify the status of the schedule is Finalized in Schedules widget
            dashboardPage.navigateToDashboard();
            dashboardPage.clickOnRefreshButton();
            dashboardPage.validateRefreshTimestamp();
            resultListOnWidget = liquidDashboardPage.getDataOnSchedulesWidget();
            if (resultListOnWidget.size() > 3 && resultListOnWidget.get(1).contains(",")) {
                if (resultListOnWidget.get(1).split(",").length > 4) {
                    String currentStatus = resultListOnWidget.get(1).split(",")[3].replace(" ", "");
                    if (currentStatus.equals("Finalized"))
                        SimpleUtils.pass("Liquid Dashboard: The current week's status in 'Schedules' widget is Finalized as expected");
                    else
                        SimpleUtils.fail("Liquid Dashboard: The current week's status in 'Schedules' widget is " + currentStatus + ", not Finalized",true);
                } else
                    SimpleUtils.fail("Liquid Dashboard: The result item's length in 'Schedules' widget  is incorrect",false);
            } else
                SimpleUtils.fail("Liquid Dashboard: The results list's size in 'Schedules' widget is incorrect",false);

            // Verify the status of the schedule is Published in Overview
            liquidDashboardPage.clickOnLinkByWidgetNameAndLinkName(widgetType.Schedules.getValue(),linkNames.View_Schedules.getValue());
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated)
                schedulePage.createScheduleForNonDGFlowNewUI();
            if (!schedulePage.isWeekPublished()) {
                schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                schedulePage.deleteAllOOOHShiftInWeekView();
                schedulePage.deleteTMShiftInWeekView("Unassigned");
                schedulePage.saveSchedule();
                schedulePage.publishActiveSchedule();
            }
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            resultListInOverview = schedulePage.getOverviewData();
            String finalizeByDate = "";
            String scheduleStartDate = "";
            if (resultListInOverview.size() > 5 && resultListInOverview.get(4).contains(",")) {
                if (resultListInOverview.get(4).split(",").length > 5) {
                    String nextStatus = resultListInOverview.get(4).split(",")[3].replace(" ", "");
                    finalizeByDate = resultListInOverview.get(4).split(",")[4].trim();
                    scheduleStartDate = resultListInOverview.get(4).split(",")[0].replace("[","");
                    if (nextStatus.equals("Published"))
                        SimpleUtils.pass("Schedule Page: The next next next week's status in 'Overview' is Published as expected");
                    else
                        SimpleUtils.fail("Schedule Page: The next next next week's status in 'Overview' is " + nextStatus + ", not Published",false);
                } else
                    SimpleUtils.fail("Schedule Page: The result item's length in 'Overview' is incorrect",false);
            } else
                SimpleUtils.fail("Schedule Page: The results list's size in 'Overview' is incorrect",false);

            // Verify the Finalized Date is correct
            SimpleUtils.report("The finalized date is " + finalizeByDate);
            int days = schedulePage.getDaysBetweenFinalizeDateAndScheduleStartDate(finalizeByDate,scheduleStartDate);

            /// Get 'How many days in advance would you finalize schedule' from controls
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            SimpleUtils.assertOnFail("Controls Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            Thread.sleep(2000);
            controlsNewUIPage.clickOnSchedulingPoliciesSchedulesAdvanceBtn();
            int advanceFinalizeScheduleDaysCount = controlsNewUIPage.getAdvanceScheduleDaysCountToBeFinalize();
            SimpleUtils.report("Scheduling Policies : 'How many days in advance would you finalize schedule' is set to be '"+advanceFinalizeScheduleDaysCount+"' Days.");

            if (days - advanceFinalizeScheduleDaysCount <= 3)
                SimpleUtils.pass("Schedule page: The finalized date for the week '" + scheduleStartDate + "' is equal to the Scheduling Policies setting in Controls");
            else
                SimpleUtils.fail("Schedule page: The finalized date for the week '" + scheduleStartDate + "' isn't equal to the Scheduling Policies setting in Controls",false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the content of Open Shifts Widget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheContentOfOpenShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();

            if (!liquidDashboardPage.isSpecificWidgetLoaded(widgetType.Open_Shifts.getValue())) {
                // Verify Edit mode Dashboard loaded
                liquidDashboardPage.enterEditMode();
                //verify switch on Open_Shifts widget
                liquidDashboardPage.switchOnWidget(widgetType.Open_Shifts.getValue());
                // Exit Edit mode
                liquidDashboardPage.saveAndExitEditMode();
            }

            // Make sure that Schedules widget is loaded, we can get the current week from Schedules widget
            if (!liquidDashboardPage.isSpecificWidgetLoaded(widgetType.Schedules.getValue())) {
                // Verify Edit mode Dashboard loaded
                liquidDashboardPage.enterEditMode();
                //verify switch on Starting_Soon widget
                liquidDashboardPage.switchOnWidget(widgetType.Schedules.getValue());
                // Exit Edit mode
                liquidDashboardPage.saveAndExitEditMode();
            }
            String currentWeek = liquidDashboardPage.getTheStartOfCurrentWeekFromSchedulesWidget();

            // Verify navigation to schedule page by "View Schedules" button on Open_Shifts Widget
            liquidDashboardPage.clickOnLinkByWidgetNameAndLinkName(widgetType.Open_Shifts.getValue(), linkNames.View_Schedules.getValue());
            SimpleUtils.assertOnFail("Schedule page not loaded Successfully!", schedulePage.isSchedule(), true);
            if (MyThreadLocal.getDriver().findElement(By.cssSelector(".day-week-picker-period-active")).getText().toUpperCase().contains(currentWeek)) {
                SimpleUtils.pass("Open Shifts: \"View Schedules\" button is to navigate to current week schedule page");
            } else {
                SimpleUtils.fail("Open Shifts: \"View Schedules\" button failed to navigate to current week schedule page", true);
            }

            // Create open shift in schedule so that we can verify the content on Open_Shifts Widget
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.addOpenShiftWithLastDay("MOD");
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            // Verify the content on Open_Shifts Widget
            dashboardPage.navigateToDashboard();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            if (liquidDashboardPage.isOpenShiftsPresent()) {
                liquidDashboardPage.verifyTheContentOfOpenShiftsWidgetLoaded(currentWeek);
            } else {
                SimpleUtils.fail("\"Open Shifts\" widget content not loaded", true);
            }

            // Ungenerate the schedule to make sure there are no open shifts on Open_Shifts Widget
            liquidDashboardPage.clickOnLinkByWidgetNameAndLinkName(widgetType.Open_Shifts.getValue(), linkNames.View_Schedules.getValue());
            SimpleUtils.assertOnFail("Schedule page not loaded Successfully!", schedulePage.isSchedule(), true);
            if (schedulePage.isWeekGenerated())
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            dashboardPage.navigateToDashboard();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Verify no content on Open_Shifts Widget
            if (liquidDashboardPage.isOpenShiftsNoContent()) {
                liquidDashboardPage.verifyTheContentOfOpenShiftsWidgetLoaded(currentWeek);
            } else {
                SimpleUtils.fail("\"Open Shifts\" widget not loaded", true);
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate to switch to specific week by clicking arrow or point of Open Shifts widget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifySwitchWeekOfOpenShiftsAsStoreManager(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();

            if (!liquidDashboardPage.isSpecificWidgetLoaded(widgetType.Open_Shifts.getValue())) {
                // Verify Edit mode Dashboard loaded
                liquidDashboardPage.enterEditMode();
                //verify switch on Open_Shifts widget
                liquidDashboardPage.switchOnWidget(widgetType.Open_Shifts.getValue());
                // Exit Edit mode
                liquidDashboardPage.saveAndExitEditMode();
            }

            // Make sure that Schedules widget is loaded, we can get the last, current and next week from Schedules widget
            if (!liquidDashboardPage.isSpecificWidgetLoaded(widgetType.Schedules.getValue())) {
                // Verify Edit mode Dashboard loaded
                liquidDashboardPage.enterEditMode();
                //verify switch on Starting_Soon widget
                liquidDashboardPage.switchOnWidget(widgetType.Schedules.getValue());
                // Exit Edit mode
                liquidDashboardPage.saveAndExitEditMode();
            }
            String currentWeek = liquidDashboardPage.getTheStartOfCurrentWeekFromSchedulesWidget();
            String lastWeek = liquidDashboardPage.getTheStartOfLastWeekFromSchedulesWidget();
            String nextWeek = liquidDashboardPage.getTheStartOfNextWeekFromSchedulesWidget();

            // Verify that switch to specific week by clicking arrow or point
            liquidDashboardPage.switchWeeksOnOpenShiftsWidget(lastWeek, currentWeek, nextWeek);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Data validation of Open Shifts-unclaimed")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void dataValidationOfOpenShiftsUnclaimedAsStoreManager(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            int[] unclaimed1 = new int[2];
            int[] claimed1 = new int[2];
            int[] unclaimed2 = new int[2];
            int[] claimed2 = new int[2];
            int[] unclaimed3;

            if (!liquidDashboardPage.isSpecificWidgetLoaded(widgetType.Open_Shifts.getValue())) {
                // Verify Edit mode Dashboard loaded
                liquidDashboardPage.enterEditMode();
                //verify switch on Open_Shifts widget
                liquidDashboardPage.switchOnWidget(widgetType.Open_Shifts.getValue());
                // Exit Edit mode
                liquidDashboardPage.saveAndExitEditMode();
            }

            // Create open shift in schedule so that we can verify the content on Open_Shifts Widget
            liquidDashboardPage.clickOnLinkByWidgetNameAndLinkName(widgetType.Open_Shifts.getValue(), linkNames.View_Schedules.getValue());
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.addOpenShiftWithLastDay("MOD");
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            // Verify the content on Open_Shifts Widget
            dashboardPage.navigateToDashboard();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            if (liquidDashboardPage.isOpenShiftsPresent()) {
                HashMap<String, int[]> dataOnWidget1 = liquidDashboardPage.getDataFromOpenShiftsWidget();
                unclaimed1 = dataOnWidget1.get("Unclaimed");
                claimed1 = dataOnWidget1.get("Claimed");
            } else
                SimpleUtils.fail("\"Open Shifts\" widget content not loaded", true);


            // Create open shift in schedule again to verify the data on Open_Shifts Widget
            liquidDashboardPage.clickOnLinkByWidgetNameAndLinkName(widgetType.Open_Shifts.getValue(), linkNames.View_Schedules.getValue());
            if (!isWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView("Unassigned");
            schedulePage.addOpenShiftWithLastDay("MOD");
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            // Verify if unclaimed number can increase successfully and % show correctly
            dashboardPage.navigateToDashboard();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            if (liquidDashboardPage.isOpenShiftsPresent()) {
                HashMap<String, int[]> dataOnWidget2 = liquidDashboardPage.getDataFromOpenShiftsWidget();
                unclaimed2 = dataOnWidget2.get("Unclaimed");
                claimed2 = dataOnWidget2.get("Claimed");
                if ((unclaimed1[0] + 1) == unclaimed2[0] && Math.round(unclaimed2[0] * 100.0/(unclaimed2[0] + claimed1[0])) == unclaimed2[1]){
                    SimpleUtils.pass("Open Shifts: Unclaimed number can increase successfully and % show correctly");
                } else
                    SimpleUtils.fail("Open Shifts: Unclaimed number failed to increase and % show incorrectly",true);
            } else
                SimpleUtils.fail("\"Open Shifts\" widget content not loaded", true);

            // Delete one open shift in schedule again to verify the data on Open_Shifts Widget
            liquidDashboardPage.clickOnLinkByWidgetNameAndLinkName(widgetType.Open_Shifts.getValue(), linkNames.View_Schedules.getValue());
            if (!isWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView("Unassigned");
            schedulePage.deleteLatestOpenShift();
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            // Verify if unclaimed number can decrease successfully and % show correctly
            dashboardPage.navigateToDashboard();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            if (liquidDashboardPage.isOpenShiftsPresent()) {
                HashMap<String, int[]> dataOnWidget3 = liquidDashboardPage.getDataFromOpenShiftsWidget();
                unclaimed3 = dataOnWidget3.get("Unclaimed");
                if (unclaimed2[0] - 1 == unclaimed3[0] && Math.round(unclaimed3[0] * 100.0/(unclaimed3[0] + claimed2[0])) == unclaimed3[1]){
                    SimpleUtils.pass("Open Shifts: Unclaimed number can decrease successfully and % show correctly");
                } else
                    SimpleUtils.fail("Open Shifts: Unclaimed number failed to decrease and % show incorrectly",true);
            } else
                SimpleUtils.fail("\"Open Shifts\" widget content not loaded", true);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Data validation of Open Shifts-claimed")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void dataValidationOfOpenShiftsClaimedAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            int[] unclaimed1 = new int[2];
            int[] claimed1 = new int[2];
            int[] claimed2;
            int[] unclaimed2;
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded() , false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String tmName = profileNewUIPage.getNickNameFromProfile();
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded() , false);
            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();

            if (!liquidDashboardPage.isSpecificWidgetLoaded(widgetType.Open_Shifts.getValue())) {
                // Verify Edit mode Dashboard loaded
                liquidDashboardPage.enterEditMode();
                //verify switch on Open_Shifts widget
                liquidDashboardPage.switchOnWidget(widgetType.Open_Shifts.getValue());
                // Exit Edit mode
                liquidDashboardPage.saveAndExitEditMode();
            }

            // Create Open Shift - Manual in schedule so that we can verify the content on Open_Shifts Widget
            liquidDashboardPage.clickOnLinkByWidgetNameAndLinkName(widgetType.Open_Shifts.getValue(), linkNames.View_Schedules.getValue());
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView(tmName);
            schedulePage.deleteTMShiftInWeekView("Unassigned");
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.addManualShiftWithLastDay("MOD", tmName);
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            // Get the data on Open_Shifts Widget before claiming shift
            dashboardPage.navigateToDashboard();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            if (liquidDashboardPage.isOpenShiftsPresent()) {
                HashMap<String, int[]> dataOnWidget1 = liquidDashboardPage.getDataFromOpenShiftsWidget();
                unclaimed1 = dataOnWidget1.get("Unclaimed");
                claimed1 = dataOnWidget1.get("Claimed");
                SimpleUtils.pass("Open Shifts: Get data before claiming shift successfully");
            } else
                SimpleUtils.fail("\"Open Shifts\" widget content not loaded", true);

            loginPage.logOut();

            // Claim shift as team member
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded() , false);
            schedulePage = dashboardPage.goToTodayForNewUI();
            schedulePage.isSchedule();
            String cardName = "WANT MORE HOURS?";
            SimpleUtils.assertOnFail("Smart Card: " + cardName + " not loaded Successfully!", schedulePage.isSpecificSmartCardLoaded(cardName), false);
            String linkName = "View Shifts";
            schedulePage.clickLinkOnSmartCardByName(linkName);
            List<String> claimShift = new ArrayList<>(Arrays.asList("Claim Shift"));
            int index = schedulePage.selectOneShiftIsClaimShift(claimShift);
            schedulePage.clickTheShiftRequestByName(claimShift.get(0));
            schedulePage.clickOnShiftByIndex(index);
            schedulePage.verifyClickAgreeBtnOnClaimShiftOffer();
            loginPage.logOut();

            // Log in as store manager to approve the request in activity->shift offer
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded() , false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftOffer.getValue(), ActivityTest.indexOfActivityType.ShiftOffer.name());
            activityPage.approveOrRejectShiftOfferRequestOnActivity(tmName, ActivityTest.approveRejectAction.Approve.getValue());
            activityPage.closeActivityWindow();

            // Verify if claim number can calculate successfully and % show correctly
            liquidDashboardPage.clickOnLinkByWidgetNameAndLinkName(widgetType.Open_Shifts.getValue(), linkNames.View_Schedules.getValue());
            dashboardPage.navigateToDashboard();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded() , false);
            if (liquidDashboardPage.isOpenShiftsPresent()) {
                HashMap<String, int[]> dataOnWidget2 = liquidDashboardPage.getDataFromOpenShiftsWidget();
                claimed2 = dataOnWidget2.get("Claimed");
                unclaimed2 = dataOnWidget2.get("Unclaimed");
                if (claimed1[0] + 1 == claimed2[0] && unclaimed2[0] == unclaimed1[0] -1 && Math.round(claimed2[0] * 100.0/(unclaimed2[0] + claimed2[0])) == claimed2[1]){
                    SimpleUtils.pass("Open Shifts: Claim number can calculate successfully and % show correctly");
                } else
                    SimpleUtils.fail("Open Shifts: Claimed number failed to calculate and % show incorrectly",true);
            } else
                SimpleUtils.fail("\"Open Shifts\" widget content not loaded", true);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Validate Compliance Widget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyComplianceViolationWidgetsAsStoreManager(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
            // Verify Edit mode Dashboard loaded
            liquidDashboardPage.enterEditMode();
            liquidDashboardPage.switchOnWidget(widgetType.Schedules.getValue());
            liquidDashboardPage.switchOnWidget(widgetType.Compliance_Violation.getValue());
            liquidDashboardPage.saveAndExitEditMode();
            //get the values on widget: violations, total hrs, locations.
            List<String> resultListOnWidget = liquidDashboardPage.getDataOnComplianceViolationWidget();
            //verify week info on widget
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            //gp to schedule, get week info of current week, last week and next week.
            liquidDashboardPage.clickFirstWeekOnSchedulesGoToSchedule();
            String startDayOfLastWeek = "";
            if (schedulePage.getActiveWeekText().split("-").length>1){
                startDayOfLastWeek = schedulePage.getActiveWeekText().split(" - ")[1];
            }
            schedulePage.navigateToNextWeek();
            String startDayOfCurrentWeek = "";
            if (schedulePage.getActiveWeekText().split("-").length>1){
                startDayOfCurrentWeek = schedulePage.getActiveWeekText().split(" - ")[1];
            }
            schedulePage.navigateToNextWeek();
            String startDayOfNextWeek = "";
            if (schedulePage.getActiveWeekText().split("-").length>1){
                startDayOfNextWeek = schedulePage.getActiveWeekText().split(" - ")[1];
            }

            //go back to dashboard to verify week info on widget is consistent with the ones in schedule.
            dashboardPage.navigateToDashboard();
            liquidDashboardPage.verifyWeekInfoOnWidget(widgetType.Compliance_Violation.getValue(),startDayOfCurrentWeek);
            //click on carousel to navigate to last week and next week to verify
            liquidDashboardPage.clickOnCarouselOnWidget(widgetType.Compliance_Violation.getValue(),"left");
            liquidDashboardPage.verifyWeekInfoOnWidget(widgetType.Compliance_Violation.getValue(),startDayOfLastWeek);
            liquidDashboardPage.clickOnCarouselOnWidget(widgetType.Compliance_Violation.getValue(),"left");
            liquidDashboardPage.verifyWeekInfoOnWidget(widgetType.Compliance_Violation.getValue(),startDayOfNextWeek);
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            //login as admin, go to DM view, go to compliance page to get the actual value.
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("PANDA district");
            liquidDashboardPage.goToCompliancePage();
            List<String> resultListInCompliancePage = liquidDashboardPage.getDataInCompliancePage(location);
            //verify if values are right
            if (resultListOnWidget.size()==resultListInCompliancePage.size()){
                boolean falg = false;
                for (int i=0;i<resultListInCompliancePage.size();i++){
                    falg = resultListInCompliancePage.get(i).equals(resultListOnWidget.get(i));
                }
                if (falg){
                    SimpleUtils.pass("compliance violation widget: Values on widget are consistent with the ones in compliance page");
                } else {
                    SimpleUtils.fail("compliance violation widget: Values on widget are not consistent with the ones in compliance page!",true);
                }

            } else {
                SimpleUtils.fail("compliance violation widget: something wrong with the number of compliance violation displayed!",true);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate Timesheet Approval Rate widget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTimesheetApprovalRateWidgetAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
            // turn on timesheet approval rate widget.
            liquidDashboardPage.enterEditMode();
            liquidDashboardPage.switchOnWidget(widgetType.Timesheet_Approval_Rate.getValue());
            liquidDashboardPage.saveAndExitEditMode();
            //verify view timesheets link
            //approvalRateOnWidget is a summary number of the 3 values on this widget.
            int approvalRateOnWidget = liquidDashboardPage.getApprovalRateOnTARWidget();
            liquidDashboardPage.clickOnLinkByWidgetNameAndLinkName(widgetType.Timesheet_Approval_Rate.getValue(),linkNames.View_TimeSheets.getValue());
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            SimpleUtils.assertOnFail("Timesheet Approval Rate widget: timesheet page fail to load!", timeSheetPage.isTimeSheetPageLoaded(),false);
            //approvalRateOnTimesheet is a total approval rate number on smart card in timesheet page.
            int approvalRateOnTimesheet = timeSheetPage.getApprovalRateFromTimesheetByLocation(location);
            SimpleUtils.assertOnFail("Timesheet Approval Rate widget: values on timesheet approval rate widget and those in timesheet page are not consistent!",approvalRateOnTimesheet==approvalRateOnWidget,true);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "verify there is no open shift on starting soon widget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyNoOpenShiftOnStartingSoonWidgetAsStoreManager(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
            liquidDashboardPage.switchOnWidget(widgetType.Starting_Soon.getValue());

            HashMap<String, String> upComingShifts = new HashMap<>();
            boolean areShiftsLoaded = dashboardPage.isStartingSoonLoaded();
            if (areShiftsLoaded) {
                upComingShifts = dashboardPage.getUpComingShifts();
                if (upComingShifts.containsKey("Open shift") || upComingShifts.containsValue("Open shift")){
                    SimpleUtils.fail("There should not be open shift displayed!",false);
                } else {
                    SimpleUtils.pass("No open shift diaplayed on the widget!");
                }
            }else {
                SimpleUtils.report("No upcoming shifts loaded!");
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
