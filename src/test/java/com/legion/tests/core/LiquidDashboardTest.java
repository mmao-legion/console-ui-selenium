package com.legion.tests.core;

import com.legion.pages.*;
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
import java.util.ArrayList;
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
        if (isWeekGenerated){
            schedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        schedulePage.createScheduleForNonDGFlowNewUI();
        schedulePage.publishActiveSchedule();

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
            HashMap<String, String> fourShifts = schedulePage.getFourUpComingShifts(false, timeFromDashboard);
            schedulePage.verifyUpComingShiftsConsistentWithSchedule(upComingShifts, fourShifts);
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
                SimpleUtils.pass("Values on widged are consistent with the one in overview");
            } else {
                SimpleUtils.fail("Values on widged are not consistent with the one in overview!",true);
            }

        } else {
            SimpleUtils.fail("something wrong with the number of week displayed!",true);
        }
    }
}
