package com.legion.tests.core;

import com.legion.pages.CompliancePage;
import com.legion.pages.DashboardPage;
import com.legion.pages.LocationSelectorPage;
import com.legion.pages.ProfileNewUIPage;
import com.legion.pages.TimeSheetPage;
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
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DMViewTest extends TestBase {

    private static HashMap<String, String> propertyCustomizeMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ScheduleCustomizeNewShift.json");

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception {
        try {
            this.createDriver((String) params[0], "83", "Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Dashboard functionality in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDashboardFunctionalityInDMViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.reSelectDistrict(districtName);

            // Validate the title
            dashboardPage.verifyHeaderOnDashboard();
            locationSelectorPage.verifyTheDisplayDistrictWithSelectedDistrictConsistent(districtName);
            locationSelectorPage.isDMView();

            // Validate the presence of district
            dashboardPage.validateThePresenceOfDistrict();

            // Validate the visibility of week
            dashboardPage.validateTheVisibilityOfWeek();

            // Validate welcome content
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String nickName = profileNewUIPage.getNickNameFromProfile();
            dashboardPage.verifyTheWelcomeMessageOfDM(nickName);

            // Validate changing districts on Dashboard
            locationSelectorPage.changeAnotherDistrict();
            districtName = dashboardPage.getCurrentDistrict();
            districtName = districtName.contains("\n")? districtName.split("\n")[0]:districtName;
            String districtOnDashboard = dashboardPage.getDistrictNameOnDashboard();
            if (districtName.equals(districtOnDashboard))
                SimpleUtils.pass("Dashboard Page: When the user selects a different district from the DM view, the data updates to reflect the selected district");
            else
                SimpleUtils.fail("When the user selects a different district from the DM view, the data doesn't update to reflect the selected district",false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Refresh feature on Dashboard in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyRefreshFeatureOnDashboardInDMViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.reSelectDistrict(districtName);

            // Validate the presence of Refresh button
            dashboardPage.validateThePresenceOfRefreshButton();

            // Validate Refresh timestamp
            dashboardPage.validateRefreshTimestamp();

            // Validate Refresh when navigation back
            dashboardPage.validateRefreshWhenNavigationBack();

            // Validate Refresh function
            dashboardPage.validateRefreshFunction();

            // Validate Refresh performance
            dashboardPage.validateRefreshPerformance();

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }
    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Projected Compliance widget on Dashboard in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyProjectedComplianceWidgetOnDashboardInDMViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.reSelectDistrict(districtName);

            SimpleUtils.assertOnFail("Project Compliance widget not loaded successfully", dashboardPage.isProjectedComplianceWidgetDisplay(), false);
            //Validate the content of Projected Compliance widget
            dashboardPage.verifyTheContentInProjectedComplianceWidget();

            //Validate the data of Projected Compliance widget
            String totalViolationHrsFromProjectedComplianceWidget =
                    dashboardPage.getTheTotalViolationHrsFromProjectedComplianceWidget();
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            dashboardPage.clickOnViewComplianceLink();
            String totalViolationHrsFromCompliancePage =
                    compliancePage.getTheTotalViolationHrsFromSmartCard().split(" ")[0];
            SimpleUtils.assertOnFail("Project Compliance widget not loaded successfully",
                    totalViolationHrsFromProjectedComplianceWidget.equals(totalViolationHrsFromCompliancePage), false);

        } catch (Exception e) {
            SimpleUtils.fail(e.toString(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "DGStage_Enterprise")
    @TestName(description = "Verify Timesheet Approval Rate widget on Dashboard in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTimesheetApprovalRateWidgetOnDashboardInDMViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.reSelectDistrict(districtName);

            // Validate the content on Timesheet Approval Rate widget on TA env
            dashboardPage.validateTheContentOnTimesheetApprovalRateWidgetInDMView();

            // Validate status value of Timesheet Approval Rate widget on TA env
            dashboardPage.validateStatusValueOfTimesheetApprovalRateWidget();
            // todo due to SCH-2636

            // Validate data on Timesheet Approval Rate widget on TA env
            List<String> timesheetApprovalRateOnDMViewDashboard = dashboardPage.getTimesheetApprovalRateOnDMViewWidget();
            dashboardPage.clickOnViewTimesheets();
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            List<String> timesheetApprovalRateFromSmartCardOnDMViewTimesheet = timeSheetPage.getTimesheetApprovalRateOnDMViewSmartCard();
            dashboardPage.validateDataOnTimesheetApprovalRateWidget(timesheetApprovalRateOnDMViewDashboard, timesheetApprovalRateFromSmartCardOnDMViewTimesheet);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }
    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Schedule Publish Status widget on Dashboard in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifySchedulePublishStatusWidgetOnDashboardInDMViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.reSelectDistrict(districtName);

            //Validate the Schedule Publish Status widget display on DM dashboard
            SimpleUtils.assertOnFail("Schedule Publish Status widget not loaded successfully",
                    dashboardPage.isSchedulePublishStatusWidgetDisplay(), false);
            //Validate the content of Schedule Publish Status widget
            dashboardPage.verifyTheContentInSchedulePublishStatusWidget();

            //Validate the data of Schedule Publish Status widget
            Map<String, Integer> scheduleStatusFromSchedulePublishStatusWidget = dashboardPage.getAllScheduleStatusFromSchedulePublishStatusWidget();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            Map<String, Integer> scheduleStatusFromScheduleDMViewPage = scheduleDMViewPage.getThreeWeeksScheduleStatusFromScheduleDMViewPage();
            SimpleUtils.assertOnFail("Schedule status on Schedule Publish Status widget and Schedule DM view page are different! ",
                    scheduleStatusFromSchedulePublishStatusWidget.equals(scheduleStatusFromScheduleDMViewPage), false);

        } catch (Exception e) {
            SimpleUtils.fail(e.toString(),false);
        }
    }

    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Open Shifts widget on Dashboard in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheContentOfOpenShiftsForDMViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();

            // Create open shift in schedule so that we can verify the content on Open_Shifts Widget
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()) , false);

            if (schedulePage.isWeekGenerated() && !schedulePage.isWeekPublished()){
                schedulePage.publishActiveSchedule();
            }
            int openShiftsNumForLoc1 = schedulePage.getShiftsNumberByName("open");
            int shiftsNumForLoc1 = schedulePage.getShiftsNumberByName("");
            dashboardPage.navigateToDashboard();
            locationSelectorPage.changeLocation("NY CENTRAL");
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()) , false);
            if (schedulePage.isWeekGenerated() && !schedulePage.isWeekPublished()){
                schedulePage.publishActiveSchedule();
            }
            int openShiftsNumForLoc2 = schedulePage.getShiftsNumberByName("open");
            int shiftsNumForLoc2 = schedulePage.getShiftsNumberByName("");
            int openRateExpected = 0;
            if ((shiftsNumForLoc1+shiftsNumForLoc2)!=0){
                openRateExpected = (openShiftsNumForLoc1+openShiftsNumForLoc2)*100/(shiftsNumForLoc1+shiftsNumForLoc2);
            }
            int assignedRateExpected = 100-openRateExpected;

            dashboardPage.navigateToDashboard();
            locationSelectorPage.reSelectDistrict(districtName);

            SimpleUtils.assertOnFail("Open Shifts widget not loaded successfully", dashboardPage.isOpenShiftsWidgetDisplay(), false);
            String currentWeek = dashboardPage.getWeekInfoFromDMView();

            //Get values on open shifts widget and verify the info on Open_Shifts Widget
            HashMap<String, Integer> valuesOnOpenShiftsWidget = dashboardPage.verifyContentOfOpenShiftsWidgetForDMView();

            // Verify navigation to schedule page by "View Schedules" button on Open_Shifts Widget
            dashboardPage.clickViewSchedulesLinkOnOpenShiftsWidget();
            SimpleUtils.assertOnFail("Schedule page not loaded Successfully!", schedulePage.isScheduleDMView(), false);
            if (currentWeek.toLowerCase().contains(MyThreadLocal.getDriver().findElement(By.cssSelector(".day-week-picker-period-active")).getText().toLowerCase().split("\n")[MyThreadLocal.getDriver().findElement(By.cssSelector(".day-week-picker-period-active")).getText().toLowerCase().split("\n").length-1])) {
                SimpleUtils.pass("Open Shifts: \"View Schedules\" butt591918on is to navigate to current week schedule page");
            } else {
                SimpleUtils.fail("Open Shifts: \"View Schedules\" button failed to navigate to current week schedule page", false);
            }
            // Verify the data on Open_Shifts Widget
            if (openRateExpected == valuesOnOpenShiftsWidget.get("open") && assignedRateExpected == valuesOnOpenShiftsWidget.get("assigned")){
                SimpleUtils.pass("Data is correct!");
            } else {
                SimpleUtils.fail("Data is incorrect!",false);
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Schedule vs. Guidance by Day widget on Dashboard in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifySchedulesGuidanceByDayWidgetOnDashboardInDMViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.reSelectDistrict(districtName);

            //Set 'Apply labor budget to schedules?' to No
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.updateApplyLaborBudgetToSchedules("No");
            dashboardPage.clickOnDashboardConsoleMenu();

            //Validate the Schedule Vs Guidance By Day widget is loaded on dashboard
            SimpleUtils.assertOnFail("Schedule Vs Guidance By Day widget loaded fail! ",
                    dashboardPage.isScheduleVsGuidanceByDayWidgetDisplay(), false);

            //Validate the content on Schedule Vs Guidance By Day widget display correctly
            dashboardPage.verifyTheContentOnScheduleVsGuidanceByDayWidget();

            //Validate the hours Under or Cover budget is consistent with the hours on schedule page
            dashboardPage.verifyTheHrsUnderOrCoverBudgetOnScheduleVsGuidanceByDayWidget();

        } catch (Exception e) {
            SimpleUtils.fail(e.toString(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Location Summary widget on Dashboard in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyLocationSummaryWidgetOnDashboardInDMViewOnNonTAEnvAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            verifyLocationSummaryWidgetOnDashboardInDMView(false);
        } catch (Exception e) {
            SimpleUtils.fail(e.toString(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify Location Summary widget on Dashboard in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyLocationSummaryWidgetOnDashboardInDMViewOnTAEnvAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            verifyLocationSummaryWidgetOnDashboardInDMView(true);
        } catch (Exception e) {
            SimpleUtils.fail(e.toString(),false);
        }
    }

    private void verifyLocationSummaryWidgetOnDashboardInDMView(boolean isTAEnv) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        String districtName = dashboardPage.getCurrentDistrict();
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.reSelectDistrict(districtName);

        //Validate the Location Summary widget is loaded on dashboard
        SimpleUtils.assertOnFail("Location Summary widget loaded fail! ",
                dashboardPage.isLocationSummaryWidgetDisplay(), false);

        //Validate the content on Location Summary widget display correctly
        dashboardPage.verifyTheContentOnLocationSummaryWidget();

        //Validate the hours on Location Summary widget is consistent with the hours on schedule page
        List<String> dataFromLocationSummaryWidget = dashboardPage.getTheDataOnLocationSummaryWidget();
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
        List<Float> totalBudgetedScheduledProjectedHour= scheduleDMViewPage.getTheTotalBudgetedScheduledProjectedHourOfScheduleInDMView();
        List<String> locationNumbersFromLocationSummarySmartCard= scheduleDMViewPage.getLocationNumbersFromLocationSummarySmartCard();
        List<String> textOnTheChartInLocationSummarySmartCard= scheduleDMViewPage.getTextFromTheChartInLocationSummarySmartCard();
        DecimalFormat df1 = new DecimalFormat("###.#");
        boolean isBudgetedHrsCorrect = dataFromLocationSummaryWidget.get(0).equals(df1.format(totalBudgetedScheduledProjectedHour.get(0)));
        boolean isScheduledHrsCorrect = dataFromLocationSummaryWidget.get(1).equals(df1.format(totalBudgetedScheduledProjectedHour.get(1)));
        boolean isProjectedHrsCorrect = dataFromLocationSummaryWidget.get(2).equals(df1.format(totalBudgetedScheduledProjectedHour.get(2)));
        boolean isProjectedWithinBudgetLocationsCorrect = dataFromLocationSummaryWidget.get(3).equals(locationNumbersFromLocationSummarySmartCard.get(0));
        boolean isProjectedOverBudgetLocationsCorrect = dataFromLocationSummaryWidget.get(4).equals(locationNumbersFromLocationSummarySmartCard.get(1));
        boolean isHrsOfUnderOrCoverBudgetCorrect = false;
        if(isTAEnv){
            isHrsOfUnderOrCoverBudgetCorrect = dataFromLocationSummaryWidget.get(5).equals(textOnTheChartInLocationSummarySmartCard.get(6));
        } else
            isHrsOfUnderOrCoverBudgetCorrect = dataFromLocationSummaryWidget.get(5).equals(textOnTheChartInLocationSummarySmartCard.get(4));

        SimpleUtils.assertOnFail("The hours on Location Summary widget is inconsistent with the hours on schedule page! ", isBudgetedHrsCorrect && isScheduledHrsCorrect
                && isProjectedHrsCorrect && isProjectedWithinBudgetLocationsCorrect
                && isProjectedOverBudgetLocationsCorrect&&isHrsOfUnderOrCoverBudgetCorrect, false);

        //Validate the Hrs Over Or Under Budget On Location Summary Widget
        dashboardPage.verifyTheHrsOverOrUnderBudgetOnLocationSummaryWidget();
    }
}
