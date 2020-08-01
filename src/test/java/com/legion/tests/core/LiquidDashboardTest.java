package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LiquidDashboardTest extends TestBase {
    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{
        this.createDriver((String)params[0],"83","Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
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
        Compliance_Violation("compilance violation");
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
    public void verifyCommonUIOfwidgetsAsStoreManager(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
        // Verifiy Edit mode Dashboard loaded
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


        //verify switch off Timesheet_Approval_Rate widget
        liquidDashboardPage.switchOffWidget(widgetType.Timesheet_Approval_Rate.getValue());
        //verify switch on Timesheet_Approval_Rate widget
        liquidDashboardPage.switchOnWidget(widgetType.Timesheet_Approval_Rate.getValue());
        //verify close Timesheet_Approval_Rate widget
        liquidDashboardPage.closeWidget(widgetType.Timesheet_Approval_Rate.getValue());
        liquidDashboardPage.switchOnWidget(widgetType.Timesheet_Approval_Rate.getValue());


        //verify switch off Alerts widget
        liquidDashboardPage.switchOffWidget(widgetType.Alerts.getValue());
        //verify switch on Alerts widget
        liquidDashboardPage.switchOnWidget(widgetType.Alerts.getValue());
        //verify close Alerts widget
        liquidDashboardPage.closeWidget(widgetType.Alerts.getValue());
        liquidDashboardPage.switchOnWidget(widgetType.Alerts.getValue());


        //verify switch off Swaps_Covers widget
        liquidDashboardPage.switchOffWidget(widgetType.Swaps_Covers.getValue());
        //verify switch on Swaps_Covers widget
        liquidDashboardPage.switchOnWidget(widgetType.Swaps_Covers.getValue());
        //verify close Swaps_Covers widget
        liquidDashboardPage.closeWidget(widgetType.Swaps_Covers.getValue());
        liquidDashboardPage.switchOnWidget(widgetType.Swaps_Covers.getValue());


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


        //verify switch off Open_Shifts widget
        liquidDashboardPage.switchOffWidget(widgetType.Open_Shifts.getValue());
        //verify switch on Open_Shifts widget
        liquidDashboardPage.switchOnWidget(widgetType.Open_Shifts.getValue());
        //verify close Open_Shifts widget
        liquidDashboardPage.closeWidget(widgetType.Open_Shifts.getValue());
        liquidDashboardPage.switchOnWidget(widgetType.Open_Shifts.getValue());


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
        liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Open_Shifts.getValue());
        liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Schedules.getValue());
        liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Starting_Soon.getValue());
        liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Swaps_Covers.getValue());
        liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Alerts.getValue());
        liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Timesheet_Approval_Rate.getValue());
        liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Timesheet_Approval_Status.getValue());
        liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Helpful_Links.getValue());
        liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Todays_Forecast.getValue());
        //verify search input
        liquidDashboardPage.enterEditMode();
        liquidDashboardPage.verifySearchInput(widgetType.Helpful_Links.getValue());
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Validate the content of Timesheet Approval Status widget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOfTimesheetApprovalStatusWidgetAsStoreManager(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
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
            }else {
                SimpleUtils.warn("Timesheet Approval rate is inconsistent, dashboard: " + approvalRate + ", and Timesheet page: " + approvalRateOnTimesheet
                + ". Failed Since this bug: https://legiontech.atlassian.net/browse/SF-287");
            }
        } else {
            SimpleUtils.fail("\"Timesheet Approval Status\" widget not loaded Successfully!", false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the content Starting Soon section")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOfStartingSoonWidgetAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
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
                schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()) , false);

        boolean isWeekGenerated = schedulePage.isWeekGenerated();
        if (!isWeekGenerated){
            schedulePage.createScheduleForNonDGFlowNewUI();
        }
        boolean isWeekPublished = schedulePage.isWeekPublished();
        if (!isWeekPublished) {
            schedulePage.publishActiveSchedule();
        }

        dashboardPage.navigateToDashboard();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

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
        }else {
            SimpleUtils.fail("No upcoming shifts loaded!", false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate no content Starting Soon section")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyNoContentInStartingSoonWidgetAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
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
                schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()) , false);

        // Ungenerate the schedule from current week
        schedulePage.unGenerateActiveScheduleFromCurrentWeekOnward(0);

        // Navigate to dashboard page to check there should no shifts in Starting soon, there should show "No published shifts for today"
        dashboardPage.navigateToDashboard();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

        boolean areShiftsLoaded = dashboardPage.isStartingSoonLoaded();
        if (!areShiftsLoaded) {
            SimpleUtils.pass("There are no shifts after ungenerating the schedule from current week onward!");
        }else {
            SimpleUtils.fail("There still shows the upcoming shifts after ungenerating the schedule from current week onward!", false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the content of Alerts widget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOfAlertWidgetAsStoreManager(String browser, String username, String password, String location) throws Exception {
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
    }
    
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Helpful Links widget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyHelpfulLinksWidgetsAsStoreManager(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
        // Verifiy Edit mode Dashboard loaded
        liquidDashboardPage.enterEditMode();
        liquidDashboardPage.switchOnWidget(widgetType.Helpful_Links.getValue());
        //verify there are 5 link at most
        liquidDashboardPage.verifyEditLinkOfHelpgulLinks();
        liquidDashboardPage.deleteAllLinks();
        liquidDashboardPage.saveLinks();
        liquidDashboardPage.verifyNoLinksOnHelpfulLinks();
        liquidDashboardPage.verifyEditLinkOfHelpgulLinks();
        liquidDashboardPage.deleteAllLinks();
        for (int i=0;i<6;i++){ //the 6th is to verify no add link button
            liquidDashboardPage.addLinkOfHelpfulLinks();
        }
        liquidDashboardPage.saveLinks();
        liquidDashboardPage.verifyEditLinkOfHelpgulLinks();
        liquidDashboardPage.cancelLinks();
        liquidDashboardPage.saveAndExitEditMode();
        //verify links
        liquidDashboardPage.verifyLinks();
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify today's forecast widget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTodayForecastWidgetsAsStoreManager(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
        // Verifiy Edit mode Dashboard loaded
        liquidDashboardPage.enterEditMode();
        liquidDashboardPage.switchOnWidget(widgetType.Todays_Forecast.getValue());
        liquidDashboardPage.saveAndExitEditMode();
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
        if (dataOnWidget.get("demand forecast") <= insightDataFromForecastPage.get("totalShoppers") && dataOnWidget.get("demand forecast") >= insightDataFromForecastPage.get("totalShoppers")){
            SimpleUtils.pass("Demand Forecast number is correct!");
        } else {
            SimpleUtils.fail("Demand Forecast number is not correct!",true);
        }
        if (dataOnWidget.get("budget") >= dataFromSchedule.get("budgetedHours")&&dataOnWidget.get("budget") <= dataFromSchedule.get("budgetedHours")){
            SimpleUtils.pass("budget number is correct!");
        } else {
            SimpleUtils.fail("budget number is not correct!",true);
        }
        if (dataOnWidget.get("scheduled") <= dataFromSchedule.get("scheduledHours")&&dataOnWidget.get("scheduled") >= dataFromSchedule.get("scheduledHours")){
            SimpleUtils.pass("scheduledHours number is correct!");
        } else {
            SimpleUtils.fail("scheduledHours number is not correct!",true);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Schedules widget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySchedulesWidgetsAsStoreManager(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
        // Verifiy Edit mode Dashboard loaded
        liquidDashboardPage.enterEditMode();
        liquidDashboardPage.switchOnWidget(widgetType.Schedules.getValue());
        liquidDashboardPage.saveAndExitEditMode();
        //verify view schedules link
        List<String> resultListOnWidget = liquidDashboardPage.getDataOnSchedulesWidget();
        liquidDashboardPage.clickOnLinkByWidgetNameAndLinkName(widgetType.Schedules.getValue(),linkNames.View_Schedules.getValue());
        //verify value on widget
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        List<String> resultListInOverview = schedulePage.getOverviewData();
        if (resultListOnWidget.size()==resultListInOverview.size()){
            boolean falg = false;
            for (int i=0;i<resultListInOverview.size();i++){
                falg = resultListInOverview.get(i).equals(resultListOnWidget.get(i));
            }
            if (falg){
                SimpleUtils.pass("Values on widget are consistent with the one in overview");
            } else {
                SimpleUtils.fail("Values on widget are not consistent with the one in overview!",true);
            }

        } else {
            SimpleUtils.fail("something wrong with the number of week displayed!",true);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the content of Open Shifts widget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheContentOfOpenShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate to switch to specific week by clicking arrow or point of Open Shifts widget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifySwitchWeekOfOpenShiftsAsStoreManager(String browser, String username, String password, String location) throws Exception {
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
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Data validation of Open Shifts-unclaimed")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void dataValidationOfOpenShiftsUnclaimedAsStoreManager(String browser, String username, String password, String location) throws Exception {
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
        schedulePage.deleteOpenShiftWithLastDay();
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
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Data validation of Open Shifts-claimed")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void dataValidationOfOpenShiftsClaimedAsTeamMember(String browser, String username, String password, String location) throws Exception {
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

        String fileName = "UsersCredentials.json";
        fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise") + fileName;
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] credentials = userCredentials.get("StoreManager");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(credentials[0][0]), String.valueOf(credentials[0][1]), String.valueOf(credentials[0][2]));
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
        loginToLegionAndVerifyIsLoginDone(String.valueOf(credentials[0][0]), String.valueOf(credentials[0][1]), String.valueOf(credentials[0][2]));
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
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify compliance violation widget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyComplianceViolationWidgetsAsStoreManager(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
        // Verifiy Edit mode Dashboard loaded
        liquidDashboardPage.enterEditMode();
        liquidDashboardPage.switchOnWidget(widgetType.Schedules.getValue());
        liquidDashboardPage.saveAndExitEditMode();
        //get the values on widget: violations, total hrs, locations.
        List<String> resultListOnWidget = liquidDashboardPage.getDataOnComplianceViolationWidget();
        //verify week info on widget
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        //gp to schedule, get week info of current week, last week and next week.
        liquidDashboardPage.clickFirstWeekOnSchedulesGoToSchedule();
        String startDayOfLastWeek = schedulePage.getActiveWeekText().split(" - ")[1];
        schedulePage.navigateToNextWeek();
        String startDayOfCurrentWeek = schedulePage.getActiveWeekText().split(" - ")[1];
        schedulePage.navigateToNextWeek();
        String startDayOfNextWeek = schedulePage.getActiveWeekText().split(" - ")[1];

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
        String fileName = "UsersCredentials.json";
        fileName = SimpleUtils.getEnterprise("Coffee_Enterprise")+fileName;
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] teamMemberCredentials = userCredentials.get("InternalAdmin");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                , String.valueOf(teamMemberCredentials[0][2]));
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
                SimpleUtils.pass("Values on widget are consistent with the ones in compliance page");
            } else {
                SimpleUtils.fail("Values on widget are not consistent with the ones in compliance page!",true);
            }

        } else {
            SimpleUtils.fail("something wrong with the number of compliance violation displayed!",true);
        }
    }
}
