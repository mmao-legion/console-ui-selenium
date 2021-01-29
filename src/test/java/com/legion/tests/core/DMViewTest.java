package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.CompliancePage;
import com.legion.pages.DashboardPage;
import com.legion.pages.LocationSelectorPage;
import com.legion.pages.ProfileNewUIPage;
import com.legion.pages.TimeSheetPage;
import com.legion.pages.*;
import com.legion.pages.core.ConsoleScheduleDMViewPage;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
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
import java.util.*;

import static com.legion.utils.MyThreadLocal.getDriver;

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

            SimpleUtils.assertOnFail("Timesheet Approval Rate widget not loaded successfully", dashboardPage.isTimesheetApprovalRateWidgetDisplay(), false);

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
    @Owner(owner = "Julie")
    @Enterprise(name = "DGStage_Enterprise")
    @TestName(description = "Verify Compliance Violations widget on Dashboard in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyComplianceViolationsWidgetOnDashboardInDMViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.reSelectDistrict(districtName);

            SimpleUtils.assertOnFail("Compliance Violations widget not loaded successfully", dashboardPage.isComplianceViolationsWidgetDisplay(), false);

            // Validate the content on Compliance Violations widget on TA env
            dashboardPage.validateTheContentOnComplianceViolationsWidgetInDMView();

            // Validate the data on Compliance Violations widget on TA env
            List<String> complianceViolationsOnDMViewDashboard = dashboardPage.getComplianceViolationsOnDMViewWidget();
            dashboardPage.clickOnViewViolations();
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            List<String> complianceViolationsFromOnDMViewCompliance = compliancePage.getComplianceViolationsOnDMViewSmartCard();
            dashboardPage.validateDataOnComplianceViolationsWidget(complianceViolationsOnDMViewDashboard, complianceViolationsFromOnDMViewCompliance);
            //todo SCH-1906： [Dashboard] Compliance violation widget -> the numbers of violation is incorrect

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "DGStage_Enterprise")
    @TestName(description = "Verify Compliance Violations widget on Dashboard in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyPayrollProjectionWidgetOnDashboardInDMViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.reSelectDistrict(districtName);

            SimpleUtils.assertOnFail("Payroll Projection widget not loaded successfully", dashboardPage.isPayrollProjectionWidgetDisplay(), false);

            // Validate the content on Payroll Projection widget on TA env
            dashboardPage.validateTheContentOnPayrollProjectionWidget();

            // Validate the date on Payroll Projection widget on TA env
            String weekOnPayrollProjectionWidget = dashboardPage.getWeekOnPayrollProjectionWidget();
            String forecastKPIOnPayrollProjectionWidget = dashboardPage.getBudgetSurplusOnPayrollProjectionWidget();
            dashboardPage.clickOnViewSchedulesOnPayrollProjectWidget();
            SimpleUtils.assertOnFail("Schedule page not loaded successfully", dashboardPage.isScheduleConsoleMenuDisplay(), false);
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            String currentWeekInDMViewSchedule = scheduleDMViewPage.getCurrentWeekInDMView();
            String forecastKPIInDMViewSchedule = scheduleDMViewPage.getBudgetSurplusInDMView();
            dashboardPage.navigateToDashboard();
            SimpleUtils.assertOnFail("Payroll Projection widget not loaded successfully", dashboardPage.isPayrollProjectionWidgetDisplay(), false);
            dashboardPage.validateWeekOnPayrollProjectionWidget(weekOnPayrollProjectionWidget, currentWeekInDMViewSchedule);
            dashboardPage.validateBudgetSurplusOnPayrollProjectionWidget(forecastKPIOnPayrollProjectionWidget, forecastKPIInDMViewSchedule);

            // Validate as of time on Payroll Projection widget on TA env
            dashboardPage.validateAsOfTimeOnPayrollProjectionWidget();

            // Validate the future Budget Surplus on Payroll Projection widget on TA env
            dashboardPage.validateTheFutureBudgetSurplusOnPayrollProjectionWidget();

            // Validate hours tooltips of Payroll Projection widget on TA env
            dashboardPage.validateHoursTooltipsOfPayrollProjectionWidget();
           // todo due to SCH-2634

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "DGStage_Enterprise")
    @TestName(description = "Verify Refresh feature on Dashboard in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyRefreshFeatureOnScheduleInDMViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.reSelectDistrict(districtName);

            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            SimpleUtils.assertOnFail("Schedule page not loaded successfully", dashboardPage.isScheduleConsoleMenuDisplay(), false);

            // Validate the presence of Refresh button
            scheduleDMViewPage.validateThePresenceOfRefreshButton();

            // Validate Refresh timestamp
            scheduleDMViewPage.validateRefreshTimestamp();

            // Validate Refresh when navigation back
            scheduleDMViewPage.validateRefreshWhenNavigationBack();

            // Validate Refresh function
            scheduleDMViewPage.validateRefreshFunction();

            // Validate Refresh performance
            scheduleDMViewPage.validateRefreshPerformance();

            // Validate Refresh function for past weeks
            schedulePage.navigateToPreviousWeek();
            scheduleDMViewPage.validateRefreshTimestamp();
            scheduleDMViewPage.clickOnRefreshButton();
            scheduleDMViewPage.validateRefreshFunction();

            // Validate Refresh function for current/future weeks
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            scheduleDMViewPage.validateRefreshTimestamp();
            scheduleDMViewPage.clickOnRefreshButton();
            scheduleDMViewPage.validateRefreshFunction();

            // Validate Refresh reflects schedule change
            while (!scheduleDMViewPage.isNotStartedScheduleDisplay()) {
                schedulePage.navigateToNextWeek();
            }
            if (scheduleDMViewPage.isNotStartedScheduleDisplay()) {
                String notStartedLocation = scheduleDMViewPage.getLocationsWithNotStartedSchedules().get(0);
                schedulePage.clickOnLocationNameInDMView(notStartedLocation);
                SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                        schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
                boolean isWeekGenerated = schedulePage.isWeekGenerated();
                if (!isWeekGenerated) {
                    schedulePage.createScheduleForNonDGFlowNewUI();
                }
                locationSelectorPage.reSelectDistrict(districtName);
                scheduleDMViewPage.clickOnRefreshButton();
                String scheduleStatus = scheduleDMViewPage.getScheduleStatusForGivenLocation(notStartedLocation);
                if (scheduleStatus.equals("In Progress"))
                    SimpleUtils.pass("Schedule Page: After the first refreshing, it is \"In Progress\" status");
                else
                    SimpleUtils.fail("Schedule Page: After the first refreshing, it isn't \"In Progress\" status", false);
                schedulePage.clickOnLocationNameInDMView(notStartedLocation);
                SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                        schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
                schedulePage.publishActiveSchedule();
                locationSelectorPage.reSelectDistrict(districtName);
                scheduleDMViewPage.clickOnRefreshButton();
                scheduleStatus = scheduleDMViewPage.getScheduleStatusForGivenLocation(notStartedLocation);
                if (scheduleStatus.equals("Published"))
                    SimpleUtils.pass("Schedule Page: After the second refreshing, it is \"Published\" status");
                else
                    SimpleUtils.fail("Schedule Page: After the second refreshing, it isn't \"Published\" status", false);
            } else
                SimpleUtils.report("Schedule Page: There are no Not Started schedules in the current and upcoming weeks");

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "DGStage_Enterprise")
    @TestName(description = "Verify Refresh feature on Dashboard in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyRefreshFeatureOnTimesheetInDMViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.reSelectDistrict(districtName);

            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            timeSheetPage.clickOnTimeSheetConsoleMenu();
            SimpleUtils.assertOnFail("Timesheet page not loaded successfully", timeSheetPage.isTimeSheetPageLoaded(), false);

            // Validate the presence of Refresh button
            timeSheetPage.validateThePresenceOfRefreshButton();

            // Validate Refresh timestamp
            timeSheetPage.validateRefreshTimestamp();

            // Validate Refresh when navigation back
            timeSheetPage.validateRefreshWhenNavigationBack();

            // Validate Refresh function
            timeSheetPage.validateRefreshFunction();

            // Validate Refresh performance
            timeSheetPage.validateRefreshPerformance();

            // Validate Refresh function for past weeks
            timeSheetPage.navigateToPreviousWeek();
            timeSheetPage.validateRefreshTimestamp();
            timeSheetPage.clickOnRefreshButton();
            timeSheetPage.validateRefreshFunction();

            // Validate Refresh reflects timesheet change
            String rateWithin24OnSmartCardBeforeChange = timeSheetPage.getTimesheetApprovalRateOnDMViewSmartCard().get(0);
            String rateInAnalyticsTableBeforeChange = timeSheetPage.getTimesheetApprovalForGivenLocationInDMView(location);
            timeSheetPage.clickOnGivenLocation(location);
            if (!timeSheetPage.isWorkerDisplayInTimesheetTable()) {
                timeSheetPage.navigateToSchedule();
                SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                        schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
                schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
                boolean isWeekGenerated = schedulePage.isWeekGenerated();
                if (!isWeekGenerated) {
                    schedulePage.createScheduleForNonDGFlowNewUI();
                }
                schedulePage.publishActiveSchedule();
                timeSheetPage.clickOnTimeSheetConsoleMenu();
            }
            timeSheetPage.approveAnyTimesheet();
            String rateInTimesheetDueAfterApprove = timeSheetPage.getApprovalRateFromTIMESHEETDUESmartCard();
            dashboardPage.navigateToDashboard();
            locationSelectorPage.reSelectDistrict(districtName);
            timeSheetPage.clickOnTimeSheetConsoleMenu();
            timeSheetPage.clickOnRefreshButton();
            String rateWithin24OnSmartCardAfterChange = timeSheetPage.getTimesheetApprovalRateOnDMViewSmartCard().get(0);
            String rateInAnalyticsTableAfterChange = timeSheetPage.getTimesheetApprovalForGivenLocationInDMView(location);
            if (rateInTimesheetDueAfterApprove.equals(rateInAnalyticsTableAfterChange)
                    && !rateWithin24OnSmartCardBeforeChange.equals(rateWithin24OnSmartCardAfterChange)
            && !rateInAnalyticsTableBeforeChange.equals(rateInAnalyticsTableAfterChange)) {
                SimpleUtils.report("The rate <24 Hrs on smart card before change is " + rateWithin24OnSmartCardBeforeChange);
                SimpleUtils.report("The rate <24 Hrs on smart card after change is " + rateWithin24OnSmartCardAfterChange);
                SimpleUtils.report("The rate in analytics table for given location before change is " + rateInAnalyticsTableBeforeChange);
                SimpleUtils.report("The rate in analytics table for given location after change is " + rateInAnalyticsTableAfterChange);
                SimpleUtils.report("The rate in SM view for given location after updating is " + rateInTimesheetDueAfterApprove);
                SimpleUtils.pass("Timesheet Page: The timesheet approval rate on smart card and in analytics table gets updating according to the new change");
                        } else
                // SimpleUtils.fail("Timesheet Page: The timesheet approval rate on smart card and in analytics table doesn't get updating according to the new change",false);
                SimpleUtils.warn("TA-5015: Approved percentage cannot get updating after approving on TIMESHEET DUE smart card");

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "DGStage_Enterprise")
    @TestName(description = "Verify Refresh feature on Dashboard in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyRefreshFeatureOnComplianceInDMViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        String districtName = dashboardPage.getCurrentDistrict();
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.reSelectDistrict(districtName);

        CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
        TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        compliancePage.clickOnComplianceConsoleMenu();
        SimpleUtils.assertOnFail("Compliance page not loaded successfully", compliancePage.isCompliancePageLoaded(), false);

        // Validate the presence of Refresh button
        compliancePage.validateThePresenceOfRefreshButton();

        // Validate Refresh timestamp
        compliancePage.validateRefreshTimestamp();

        // Validate Refresh when navigation back
        compliancePage.validateRefreshWhenNavigationBack();

        // Validate Refresh function
        compliancePage.validateRefreshFunction();

        // Validate Refresh performance
        compliancePage.validateRefreshPerformance();

        // Validate Refresh function for past weeks
        compliancePage.navigateToPreviousWeek();
        compliancePage.validateRefreshTimestamp();
        compliancePage.clickOnRefreshButton();
        compliancePage.validateRefreshFunction();

        // Validate Refresh function for future weeks
        compliancePage.navigateToNextWeek();
        compliancePage.navigateToNextWeek();
        compliancePage.validateRefreshTimestamp();
        compliancePage.clickOnRefreshButton();
        compliancePage.validateRefreshFunction();

        // Validate Refresh reflects timesheet change
        compliancePage.navigateToPreviousWeek();
        String totalViolationHrsBeforeChange = compliancePage.getTheTotalViolationHrsFromSmartCard();
        List<String> dataFromComplianceTableBeforeChange = compliancePage.getDataFromComplianceTableForGivenLocationInDMView(location);
        timeSheetPage.clickOnTimeSheetConsoleMenu();
        timeSheetPage.clickOnGivenLocation(location);
        if (!timeSheetPage.isWorkerDisplayInTimesheetTable()) {
            timeSheetPage.navigateToSchedule();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            schedulePage.publishActiveSchedule();
            timeSheetPage.clickOnTimeSheetConsoleMenu();
        }
//        timeSheetPage.clickOnDayView();
//        SimpleUtils.pass("Timesheet Day View: '"+ timeSheetPage.getActiveDayWeekOrPayPeriod() +"' loaded.");
//        timeSheetPage.navigateDayWeekOrPayPeriodToPastOrFuture(TimeSheetTest.dayWeekOrPayPeriodViewType.Previous.getValue()
//                , TimeSheetTest.dayWeekOrPayPeriodCount.One.getValue());
//        SimpleUtils.pass("Timesheet Day View: '"+ timeSheetPage.getActiveDayWeekOrPayPeriod() +"' loaded.");
        timeSheetPage.openFirstPendingTimeSheet();
        timeSheetPage.addTimeClockCheckInOutOnDetailWithDefaultValue(location);
        timeSheetPage.saveTimeSheetDetail();
        timeSheetPage.clickOnTimeSheetDetailBackBtn();
//        timeSheetPage.reaggregateTimesheet();

        dashboardPage.navigateToDashboard();
        locationSelectorPage.reSelectDistrict(districtName);
        dashboardPage.clickOnRefreshButton();
        SimpleUtils.assertOnFail("Compliance Violations widget not loaded successfully", dashboardPage.isComplianceViolationsWidgetDisplay(), false);
        compliancePage.clickOnComplianceConsoleMenu();
        compliancePage.clickOnRefreshButton();
        String totalViolationHrsAfterChange = compliancePage.getTheTotalViolationHrsFromSmartCard();
        List<String> dataFromComplianceTableAfterChange = compliancePage.getDataFromComplianceTableForGivenLocationInDMView(location);

        if (Integer.valueOf(totalViolationHrsAfterChange.split(" ")[0]) != Integer.valueOf(totalViolationHrsBeforeChange.split(" ")[0])
                && !dataFromComplianceTableBeforeChange.containsAll(dataFromComplianceTableAfterChange)
        && !dataFromComplianceTableAfterChange.containsAll(dataFromComplianceTableBeforeChange)) {
            SimpleUtils.report("The total violation hours before change is " + totalViolationHrsBeforeChange);
            SimpleUtils.report("The total violation hours after change is " + totalViolationHrsAfterChange);
            SimpleUtils.report("The total violation hours for the given location before change is " + dataFromComplianceTableBeforeChange.get(0));
            SimpleUtils.report("The total violation hours for the given location after change is " + dataFromComplianceTableAfterChange.get(0));
            SimpleUtils.pass("Compliance Page: The violation number or hours on smart card and in analytics table should get updating according to the new change");
        } else
            SimpleUtils.fail("Compliance Page: The timesheet approval rate on smart card and in analytics table doesn't get updating according to the new change",false);

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
    public void verifyTheContentOfOpenShiftsForDMViewAsInternalAdmin(String browser, String username, String password, String location) {
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
                SimpleUtils.pass("Open Shifts: \"View Schedules\" button is to navigate to current week schedule page");
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

    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Schedule functionality in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyScheduleFunctionalityForDMViewAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            locationSelectorPage.reSelectDistrict(districtName);

            //Go to the Schedule page in DM view. And to verify the title.
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule DM view page not loaded Successfully!", schedulePage.isScheduleDMView(), false);
            locationSelectorPage.verifyTheDisplayDistrictWithSelectedDistrictConsistent(districtName);
            locationSelectorPage.isLocationSelected("All Locations");
            List<String> locationInDistrict1 =  schedulePage.getLocationsInScheduleDMViewLocationsTable();
            schedulePage.verifySortByColForLocationsInDMView(1);
            schedulePage.verifySortByColForLocationsInDMView(1);
            schedulePage.verifySortByColForLocationsInDMView(4);
            schedulePage.verifySortByColForLocationsInDMView(4);
            String weekInfo = schedulePage.getActiveWeekText();
            System.out.println(weekInfo);
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToPreviousWeek();
            schedulePage.navigateToPreviousWeek();
            System.out.println(schedulePage.getActiveWeekText());
            SimpleUtils.assertOnFail("Week picker has issue!", weekInfo.equals(schedulePage.getActiveWeekText()), false);
            schedulePage.verifySearchLocationInScheduleDMView(location);
            //Change to another district.
            dashboardPage.navigateToDashboard();
            locationSelectorPage.changeAnotherDistrict();
            schedulePage.clickOnScheduleConsoleMenuItem();
            String anotherDistrictName = dashboardPage.getCurrentDistrict();
            locationSelectorPage.verifyTheDisplayDistrictWithSelectedDistrictConsistent(anotherDistrictName);
            List<String> locationInDistrict2 =  schedulePage.getLocationsInScheduleDMViewLocationsTable();
            SimpleUtils.assertOnFail("Schedule DM view page fail to update!", !locationInDistrict1.containsAll(locationInDistrict2), false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the availablity of location list and sub location on Schedule in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyLocationListAndSublocationInDMViewAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            locationSelectorPage.reSelectDistrict(districtName);

            //Go to the Schedule page in DM view.
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule DM view page not loaded Successfully!", schedulePage.isScheduleDMView(), false);

            //Click on a location name and go to the schedule page.
            schedulePage.clickOnLocationNameInDMView(location);
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            //Validate go back from one location
            locationSelectorPage.reSelectDistrictInDMView(districtName);
            SimpleUtils.assertOnFail("Schedule DM view page not loaded Successfully!", schedulePage.isScheduleDMView(), false);

            //Validate click given location and given week
            schedulePage.navigateToNextWeek();
            String weekInfo = schedulePage.getActiveWeekText();
            schedulePage.clickOnLocationNameInDMView(location);
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            SimpleUtils.assertOnFail("Didn't go to the right week!", weekInfo.equals(schedulePage.getActiveWeekText()), false);

            //Validate go back from one location and one week
            locationSelectorPage.reSelectDistrictInDMView(districtName);
            SimpleUtils.assertOnFail("Schedule DM view page not loaded Successfully!", schedulePage.isScheduleDMView(), false);
            SimpleUtils.assertOnFail("Didn't go back to the right week!", weekInfo.equals(schedulePage.getActiveWeekText()), false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Owner(owner = "Haya")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify Timesheet functionality on Timesheet in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTimesheetFunctionalityInDMViewAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            locationSelectorPage.reSelectDistrict(districtName);

            //Go to the Timesheet page in DM view and verify the title of the page.
            timeSheetPage.clickOnTimeSheetConsoleMenu();
            SimpleUtils.assertOnFail("TimeSheet Page not loaded Successfully!",timeSheetPage.isTimeSheetPageLoaded() , false);

            //Verify district selected and displayed with "All locations".
            locationSelectorPage.verifyTheDisplayDistrictWithSelectedDistrictConsistent(districtName);
            locationSelectorPage.isLocationSelected("All Locations");
            List<String> locationInDistrict1 =  schedulePage.getLocationsInScheduleDMViewLocationsTable();

            //Validate search function.
            schedulePage.verifySearchLocationInScheduleDMView(location);

            //Validate changing district.
            locationSelectorPage.changeAnotherDistrictInDMView();
            String anotherDistrictName = dashboardPage.getCurrentDistrict();
            locationSelectorPage.verifyTheDisplayDistrictWithSelectedDistrictConsistent(anotherDistrictName);
            List<String> locationInDistrict2 =  schedulePage.getLocationsInScheduleDMViewLocationsTable();
            SimpleUtils.assertOnFail("TimeSheet DM view page fail to update!", !locationInDistrict1.containsAll(locationInDistrict2), false);

            //Validate the clickability of backward button.
            String weekInfo = schedulePage.getActiveWeekText();
            schedulePage.navigateToPreviousWeek();

            //Validate the clickability of forward button.
            schedulePage.navigateToNextWeek();
            SimpleUtils.assertOnFail("Week picker has issue!", weekInfo.equals(schedulePage.getActiveWeekText()), false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify LOCATION SUMMARY on Schedule in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyLocationSummaryInScheduleDMViewAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            locationSelectorPage.reSelectDistrict(districtName);

            //Go to the Schedule page in DM view.
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule DM view page not loaded Successfully!", schedulePage.isScheduleDMView(), false);

            //Validate the content of LOCATION SUMMARY smart card for current/future weeks.
            HashMap<String, Float> valuesFromLocationSummaryCard =  schedulePage.getValuesAndVerifyInfoForLocationSummaryInDMView("current");

            //Validate the data LOCATION SUMMARY smart card for current/future weeks.
            SimpleUtils.assertOnFail("Location counts in title are inconsistent!", Math.round(valuesFromLocationSummaryCard.get("NumOfLocations")) == schedulePage.getLocationsInScheduleDMViewLocationsTable().size(), false);
            SimpleUtils.assertOnFail("Location counts from projected info are inconsistent!", (Math.round(valuesFromLocationSummaryCard.get("NumOfProjectedWithin")) + Math.round(valuesFromLocationSummaryCard.get("NumOfProjectedOver"))) == schedulePage.getLocationsInScheduleDMViewLocationsTable().size(), false);
            //verify budgeted hours.
            List<Float> data = schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Budgeted Hours")));
            float budgetedHrsFromTable = 0;
            for (Float f: data){
                budgetedHrsFromTable = budgetedHrsFromTable + f;
            }
            SimpleUtils.assertOnFail("Budgeted hours are inconsistent!", (Math.abs(valuesFromLocationSummaryCard.get("Budgeted Hrs")) - budgetedHrsFromTable) == 0, false);
            //verify scheduled hours
            data = schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Scheduled Hours")));
            float scheduledHrsFromTable = 0;
            for (Float f: data){
                scheduledHrsFromTable = scheduledHrsFromTable + f;
            }
            SimpleUtils.assertOnFail("Published hours are inconsistent!", (Math.abs(valuesFromLocationSummaryCard.get("Published Hrs")) - scheduledHrsFromTable) == 0, false);

            //Verify difference value between budgeted and projected.
            data = schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Projected Hours")));
            float projectedHours = 0;
            for (Float f: data){
                projectedHours = projectedHours + f;
            }
            if ((valuesFromLocationSummaryCard.get("Budgeted Hrs") - projectedHours)>0){
                SimpleUtils.assertOnFail("Difference hours is inconsistent!", (Math.abs(valuesFromLocationSummaryCard.get("▼")) - (valuesFromLocationSummaryCard.get("Budgeted Hrs") - projectedHours)) == 0, false);
            }
            if ((valuesFromLocationSummaryCard.get("Budgeted Hrs") - projectedHours)<0){
                SimpleUtils.assertOnFail("Difference hours is inconsistent!", (Math.abs(valuesFromLocationSummaryCard.get("▲")) - (valuesFromLocationSummaryCard.get("Budgeted Hrs") - projectedHours)) == 0, false);
            }

            //Verify currect week Projected Hours displays.
            schedulePage.verifyClockedOrProjectedInDMViewTable("Projected Hours");

            //Navigate to the past week to verify the info and data.
            schedulePage.navigateToPreviousWeek();
            valuesFromLocationSummaryCard =  schedulePage.getValuesAndVerifyInfoForLocationSummaryInDMView("past");

            //Validate the data LOCATION SUMMARY smart card for the past weeks.
            SimpleUtils.assertOnFail("Location counts in title are inconsistent!", Math.round(valuesFromLocationSummaryCard.get("NumOfLocations")) == schedulePage.getLocationsInScheduleDMViewLocationsTable().size(), false);
            SimpleUtils.assertOnFail("Location counts from projected info are inconsistent!", (Math.round(valuesFromLocationSummaryCard.get("NumOfProjectedWithin")) + Math.round(valuesFromLocationSummaryCard.get("NumOfProjectedOver"))) == schedulePage.getLocationsInScheduleDMViewLocationsTable().size(), false);
            //verify budgeted hours.
            data = schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Budgeted Hours")));
            budgetedHrsFromTable = 0;
            for (Float f: data){
                budgetedHrsFromTable = budgetedHrsFromTable + f;
            }
            SimpleUtils.assertOnFail("Budgeted hours are inconsistent!", (Math.abs(valuesFromLocationSummaryCard.get("Budgeted Hrs")) - budgetedHrsFromTable) == 0, false);
            //verify scheduled hours.
            data = schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Scheduled Hours")));
            scheduledHrsFromTable = 0;
            for (Float f: data){
                scheduledHrsFromTable = scheduledHrsFromTable + f;
            }
            SimpleUtils.assertOnFail("Published hours are inconsistent!", (Math.abs(valuesFromLocationSummaryCard.get("Published Hrs")) - scheduledHrsFromTable) == 0, false);
            //Verify difference value between budgeted and projected.
            data = schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Projected Hours")));
            projectedHours = 0;
            for (Float f: data){
                projectedHours = projectedHours + f;
            }
            if ((valuesFromLocationSummaryCard.get("Budgeted Hrs") - projectedHours)>=0){
                SimpleUtils.assertOnFail("Difference hours is inconsistent!", (Math.abs(valuesFromLocationSummaryCard.get("▼")) - (valuesFromLocationSummaryCard.get("Budgeted Hrs") - projectedHours)) == 0, false);
            } else {
                SimpleUtils.assertOnFail("Difference hours is inconsistent!", (Math.abs(valuesFromLocationSummaryCard.get("▲")) - (valuesFromLocationSummaryCard.get("Budgeted Hrs") - projectedHours)) == 0, false);

            }
            //Verify past week Clocked Hours displays.
            schedulePage.verifyClockedOrProjectedInDMViewTable("Clocked Hours");

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Owner(owner = "Haya")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify Unplanned Clocks on Timesheet in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyUnplannedClocksForTimesheetInDMViewAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            locationSelectorPage.reSelectDistrict(districtName);

            //Validate the content on Unplanned Clocks summary card.
            timeSheetPage.clickOnTimeSheetConsoleMenu();
            HashMap<String, Integer> valuesFromUnplannedClocksSummaryCard = schedulePage.getValueOnUnplannedClocksSummaryCardAndVerifyInfo();
            int index = schedulePage.getIndexOfColInDMViewTable("Unplanned Clocks");
            List<Float> data = schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(index));
            int unplannedClocks = 0;
            for (Float f: data){
                unplannedClocks = unplannedClocks + Math.round(f);
            }
            SimpleUtils.assertOnFail("Unplanned clocks from summary card and analytic table are inconsistent!", valuesFromUnplannedClocksSummaryCard.get("unplanned clocks")==unplannedClocks, false);

            index = schedulePage.getIndexOfColInDMViewTable("Total Timesheets");
            data = schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(index));
            int totalTimesheets = 0;
            for (Float f: data){
                totalTimesheets = totalTimesheets + Math.round(f);
            }
            SimpleUtils.assertOnFail("Total Timesheets from summary card and analytic table are inconsistent!", valuesFromUnplannedClocksSummaryCard.get("total timesheets")==totalTimesheets, false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Owner(owner = "Haya")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify UNPLANNED CLOCKS smart card on Timesheet in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyUnplannedClocksSmartCardForTimesheetInDMViewAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            locationSelectorPage.reSelectDistrict(districtName);

            //Validate the content on Unplanned Clocks summary smart card.
            timeSheetPage.clickOnTimeSheetConsoleMenu();
            HashMap<String, Integer> valuesFromUnplannedClocksSummaryCard = schedulePage.getValueOnUnplannedClocksSmartCardAndVerifyInfo();
            int index = schedulePage.getIndexOfColInDMViewTable("Unplanned Clocks");
            List<Float> data = schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(index));
            int unplannedClocks = 0;
            for (Float f: data){
                unplannedClocks = unplannedClocks + Math.round(f);
            }
            SimpleUtils.assertOnFail("Unplanned clocks from summary card and analytic table are inconsistent!", valuesFromUnplannedClocksSummaryCard.get("No Show")==unplannedClocks, false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Compliance functionality on Compliance in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyComplianceFunctionalityForComplianceInDMViewAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            locationSelectorPage.reSelectDistrict(districtName);

            //Validate the title and info of Compliance page.
            timeSheetPage.clickOnComplianceConsoleMenu();
            SimpleUtils.assertOnFail("Compliance Page not loaded Successfully!",compliancePage.isCompliancePageLoaded() , false);

            //Verify district selected and displayed with "All locations".
            locationSelectorPage.verifyTheDisplayDistrictWithSelectedDistrictConsistent(districtName);
            locationSelectorPage.isLocationSelected("All Locations");
            List<String> locationInDistrict1 =  schedulePage.getLocationsInScheduleDMViewLocationsTable();

            //Validate changing district.
            locationSelectorPage.changeAnotherDistrictInDMView();
            String anotherDistrictName = dashboardPage.getCurrentDistrict();
            locationSelectorPage.verifyTheDisplayDistrictWithSelectedDistrictConsistent(anotherDistrictName);
            List<String> locationInDistrict2 =  schedulePage.getLocationsInScheduleDMViewLocationsTable();
            SimpleUtils.assertOnFail("Compliance DM view page fail to update!", !locationInDistrict1.containsAll(locationInDistrict2), false);

            //Validate search function.
            locationSelectorPage.reSelectDistrictInDMView(districtName);
            schedulePage.verifySearchLocationInScheduleDMView(location);

            //Validate the clickability of backward button.
            String weekInfo = schedulePage.getActiveWeekText();
            schedulePage.navigateToPreviousWeek();

            //Validate the clickability of forward button.
            schedulePage.navigateToNextWeek();
            SimpleUtils.assertOnFail("Week picker has issue!", weekInfo.equals(schedulePage.getActiveWeekText()), false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Owner(owner = "Haya")
    @Enterprise(name = "DGStage_Enterprise")
    @TestName(description = "Verify TOTAL VIOLATION HRS on Compliance in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTotalViolationHrsInComplianceDMViewAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            locationSelectorPage.reSelectDistrict(districtName);

            //Navigate to Compliance page.
            timeSheetPage.clickOnComplianceConsoleMenu();
            SimpleUtils.assertOnFail("Compliance Page not loaded Successfully!",compliancePage.isCompliancePageLoaded() , false);

            //Validate the content of Toatal Violation smart card for current week.
            HashMap<String, Float> valuesFromToatalViolationCard =  compliancePage.getValuesAndVerifyInfoForTotalViolationSmartCardInDMView();

            //Validate the data Toatal Violation smart card for current week.
            //Verify total violation hours for current week.
            List<Float> data = schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Total Extra Hours")));
            float totalViolationHrsFromTable = 0;
            for (Float f: data){
                totalViolationHrsFromTable = totalViolationHrsFromTable + f;
            }
            SimpleUtils.assertOnFail("Total violation hours are inconsistent with analytic table!", (Math.abs(valuesFromToatalViolationCard.get("vioHrsCurrentWeek")) - totalViolationHrsFromTable) == 0, false);

            //Verify diff hours flag.
            if ((valuesFromToatalViolationCard.get("vioHrsCurrentWeek") - valuesFromToatalViolationCard.get("vioHrsPastWeek"))>0){
                compliancePage.verifyDiffFlag("down");
            } else {
                compliancePage.verifyDiffFlag("up");
            }

            //Verify total violation hours for last week.
            schedulePage.navigateToPreviousWeek();
            HashMap<String, Float> valuesFromToatalViolationCardForLastWeek =  compliancePage.getValuesAndVerifyInfoForTotalViolationSmartCardInDMView();
            SimpleUtils.assertOnFail("Violation hours for last week are inconsistent!", (Math.abs(valuesFromToatalViolationCard.get("vioHrsPastWeek")) - valuesFromToatalViolationCardForLastWeek.get("vioHrsCurrentWeek")) == 0, false);

            //Verify diff hours.
            SimpleUtils.assertOnFail("Diff hours with last week is incorrect!", (Math.abs(Math.abs(valuesFromToatalViolationCard.get("vioHrsCurrentWeek")) - valuesFromToatalViolationCard.get("vioHrsPastWeek"))-valuesFromToatalViolationCard.get("diffHrs")) == 0, false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Owner(owner = "Haya")
    @Enterprise(name = "DGStage_Enterprise")
    @TestName(description = "Verify Locations with Violations on Compliance in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTotalLocationsWithViolationCardInComplianceDMViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            locationSelectorPage.reSelectDistrict(districtName);

            //Navigate to Compliance page.
            timeSheetPage.clickOnComplianceConsoleMenu();
            SimpleUtils.assertOnFail("Compliance Page not loaded Successfully!",compliancePage.isCompliancePageLoaded() , false);

            //Validate the content on Locations with violation card.
            HashMap<String, Integer> valuesFromLocationsWithViolationCard = compliancePage.getValueOnLocationsWithViolationCardAndVerifyInfo();
            int index = schedulePage.getIndexOfColInDMViewTable("Total Extra Hours");
            List<Float> data = schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(index));
            index = schedulePage.getIndexOfColInDMViewTable("Late Schedule?");
            List<String> dataFromLateScheduleCol = schedulePage.getListByColInTimesheetDMView(index);
            List flag = new ArrayList();
            int totalLocationWithViolation = 0;
            int locationWithViolationCountFromTotalExtraHrs = 0;
            int locationWithViolationCountFromLateSchedule = 0;
            for (int i = 0; i < data.size(); i++){
                if (!(data.get(i) >0)){
                    flag.add(i);
                    locationWithViolationCountFromTotalExtraHrs++;
                }
            }
            locationWithViolationCountFromTotalExtraHrs = data.size() - locationWithViolationCountFromTotalExtraHrs;
            if (flag.size()>0){
                for (int i = 0; i < flag.size(); i++){
                    if (dataFromLateScheduleCol.get(i).equals("yes")){
                        locationWithViolationCountFromLateSchedule++;
                    }
                }
            }
            totalLocationWithViolation = locationWithViolationCountFromTotalExtraHrs + locationWithViolationCountFromLateSchedule;
            SimpleUtils.assertOnFail("Locations With Violation Card and analytic table are inconsistent!", valuesFromLocationsWithViolationCard.get("LocationsWithViolation") == totalLocationWithViolation, false);
            SimpleUtils.assertOnFail("Locations With Violation Card and analytic table are inconsistent!", valuesFromLocationsWithViolationCard.get("total locations") == totalLocationWithViolation, false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Owner(owner = "Haya")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify TOP x VIOLATIONS (HRS) on Compliance in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTopViolationsCardInComplianceDMViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            locationSelectorPage.reSelectDistrict(districtName);

            //Navigate to Compliance page.
            timeSheetPage.clickOnComplianceConsoleMenu();
            SimpleUtils.assertOnFail("Compliance Page not loaded Successfully!",compliancePage.isCompliancePageLoaded() , false);

            //Validate the content on top violations card.
            HashMap<String, Float> valuesFromLocationsWithViolationCard = compliancePage.getViolationHrsFromTop1ViolationCardAndVerifyInfo();
            System.out.println(valuesFromLocationsWithViolationCard);

            float topViolationInOvertimeCol = compliancePage.getTopOneViolationHrsOrNumOfACol(schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Overtime"))));
            float topViolationInClopeningCol = compliancePage.getTopOneViolationHrsOrNumOfACol(schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Clopening"))));
            float topViolationInMissedMealCol = compliancePage.getTopOneViolationHrsOrNumOfACol(schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Missed Meal"))));
            float topViolationInScheduleChangedCol = compliancePage.getTopOneViolationHrsOrNumOfACol(schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Schedule Changed"))));
            float topViolationInDoubletimeCol = compliancePage.getTopOneViolationHrsOrNumOfACol(schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Doubletime"))));

            if (valuesFromLocationsWithViolationCard.containsKey("Overtime (Hrs)")){
                SimpleUtils.assertOnFail("Overtime (Hrs) on smart cart is not correct!", Math.abs(valuesFromLocationsWithViolationCard.get("Overtime (Hrs)")-topViolationInOvertimeCol)==0, false);
            }
            if (valuesFromLocationsWithViolationCard.containsKey("Clopening (Hrs)")){
                SimpleUtils.assertOnFail("Clopening (Hrs) on smart cart is not correct!", Math.abs(valuesFromLocationsWithViolationCard.get("Clopening (Hrs)")-topViolationInClopeningCol)==0, false);
            }
            if (valuesFromLocationsWithViolationCard.containsKey("Overtime (Hrs)")){
                SimpleUtils.assertOnFail("Missed Meal on smart cart is not correct!", Math.abs(valuesFromLocationsWithViolationCard.get("Missed Meal")-topViolationInMissedMealCol)==0, false);
            }
            if (valuesFromLocationsWithViolationCard.containsKey("Schedule Changed")){
                SimpleUtils.assertOnFail("Schedule Changed on smart cart is not correct!", Math.abs(valuesFromLocationsWithViolationCard.get("Schedule Changed")-topViolationInScheduleChangedCol)==0, false);
            }
            if (valuesFromLocationsWithViolationCard.containsKey("Doubletime (Hrs)")){
                SimpleUtils.assertOnFail("Doubletime (Hrs) on smart cart is not correct!", Math.abs(valuesFromLocationsWithViolationCard.get("Doubletime (Hrs)")-topViolationInDoubletimeCol)==0, false);
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "DM View Navigation")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDMViewNavigationAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            //Validate user has access to 1 district.
            locationSelectorPage.reSelectDistrict(districtName);
            locationSelectorPage.isDMView();
            String currentWeek = dashboardPage.getWeekInfoFromDMView();

            //Validate drilling into a store location.
            locationSelectorPage.changeLocation(location);

            //Validate navigating back to district view.
            //Validate default date.
            schedulePage.clickOnScheduleConsoleMenuItem();
            locationSelectorPage.reSelectDistrict(districtName);
            SimpleUtils.assertOnFail("Schedule DM view page not loaded Successfully!", schedulePage.isScheduleDMView(), false);
            if (currentWeek.toLowerCase().contains(MyThreadLocal.getDriver().findElement(By.cssSelector(".day-week-picker-period-active")).getText().toLowerCase().split("\n")[MyThreadLocal.getDriver().findElement(By.cssSelector(".day-week-picker-period-active")).getText().toLowerCase().split("\n").length-1])) {
                SimpleUtils.pass("Open Shifts: \"View Schedules\" button is to navigate to current week schedule page");
            } else {
                SimpleUtils.fail("Open Shifts: \"View Schedules\" button failed to navigate to current week schedule page", false);
            }

            //Validate changing date and location.
            dashboardPage.navigateToDashboard();
            locationSelectorPage.changeLocation(location);
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab("Schedule");
            schedulePage.navigateToNextWeek();
            String weekInfo = schedulePage.getActiveWeekText();
            locationSelectorPage.reSelectDistrict(districtName);
            SimpleUtils.assertOnFail("Dates are inconsistent after changing date and location!", schedulePage.getActiveWeekText().equalsIgnoreCase(weekInfo), false);

            //Changing district and navigate to Timesheet page(Checked in other script).
            //Verify no timesheet tab in non-TA env.
            SimpleUtils.assertOnFail("Timesheet tab should not be loaded!", !timeSheetPage.isTimeSheetConsoleMenuTabLoaded(), false);
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

        //Validate the Hrs Over Or Under Budget On Location Summary Widget
//        dashboardPage.verifyTheHrsOverOrUnderBudgetOnLocationSummaryWidget();  //having a bug: https://legiontech.atlassian.net/browse/SCH-2767

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

    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the content between weeks on Schedule in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheContentBetweenWeeksOnScheduleInDMViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.reSelectDistrict(districtName);

            //Set 'Apply labor budget to schedules?' to Yes
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.updateApplyLaborBudgetToSchedules("Yes");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();

            //Validate the smart card and schedule table header for previous week
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            scheduleDMViewPage.verifySchedulesTableHeaderNames(true, false);
            scheduleDMViewPage.verifySmartCardsAreLoadedForPastOrFutureWeek(false);
            schedulePage.navigateToPreviousWeek();
            scheduleDMViewPage.verifySchedulesTableHeaderNames(true, true);
            scheduleDMViewPage.verifySmartCardsAreLoadedForPastOrFutureWeek(true);

            /*
            *  comment it because bug: https://legiontech.atlassian.net/browse/LEG-7198
            *
            * //Set 'Apply labor budget to schedules?' to No
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.updateApplyLaborBudgetToSchedules("No");
            schedulePage.clickOnScheduleConsoleMenuItem();

            //Validate the smart card and schedule table header for current week
            scheduleDMViewPage.verifySchedulesTableHeaderNames(false, false);
            scheduleDMViewPage.verifySmartCardsAreLoadedForPastOrFutureWeek(false);
            schedulePage.navigateToPreviousWeek();
            scheduleDMViewPage.verifySchedulesTableHeaderNames(false, true);
            scheduleDMViewPage.verifySmartCardsAreLoadedForPastOrFutureWeek(true);
            *
            *
            *
            * */

        } catch (Exception e) {
            SimpleUtils.fail(e.toString(),false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Schedule Status on Schedule in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyScheduleStatusAndHoursOnScheduleDMViewOnNonTAEnvAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.reSelectDistrict(districtName);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();

            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();

            //Validate the schedule status and hours on schedule list
//            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(false, false, "Published", "Current Week");   //comment it because bug: https://legiontech.atlassian.net/browse/SCH-2654
//            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(false, false, "Not Started", "Current Week");       //comment it because bug: https://legiontech.atlassian.net/browse/SCH-1874
            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(false, false, "In Progress", "Current Week");

            //Validate the schedule status and hours on schedule list
            schedulePage.navigateToNextWeek();
//            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(false, false, "Published", "Next Week");        //comment it because bug: https://legiontech.atlassian.net/browse/SCH-2654
//            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(false, false, "Not Started", "Next Week");      //comment it because bug: https://legiontech.atlassian.net/browse/SCH-1874
            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(false, false, "In Progress", "Next Week");

            //Validate the schedule status and hours on schedule list
            schedulePage.navigateToPreviousWeek();
            schedulePage.navigateToPreviousWeek();
//            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(false, false, "Published", "Previous Week");        //comment it because bug: https://legiontech.atlassian.net/browse/SCH-2654
//            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(false, false, "Not Started", "Previous Week");      //comment it because bug: https://legiontech.atlassian.net/browse/SCH-1874
//            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(false, false, "In Progress", "Previous Week");      //comment it because bug: https://legiontech.atlassian.net/browse/SCH-2654

            //Validate the numbers on Schedule Status Cards
            scheduleDMViewPage.verifyTheScheduleStatusAccountOnScheduleStatusCards();
        } catch (Exception e) {
            SimpleUtils.fail(e.toString(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify Schedule Status on Schedule in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyScheduleStatusAndHoursOnScheduleDMViewOnTANonDGEnvAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.reSelectDistrict(districtName);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();

            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();

            //Validate the schedule status and hours on schedule list
            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(true, false, "Published", "Current Week");
//            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(true, false, "Not Started", "Current Week");            //comment it because bug: https://legiontech.atlassian.net/browse/SCH-1874
            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(true, false, "In Progress", "Current Week");

            //Validate the schedule status and hours on schedule list
            schedulePage.navigateToNextWeek();
            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(true, false, "Published", "Next Week");
//            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(true, false, "Not Started", "Next Week");//comment it because bug: https://legiontech.atlassian.net/browse/SCH-1874
            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(true, false, "In Progress", "Next Week");

            //Validate the schedule status and hours on schedule list
            schedulePage.navigateToPreviousWeek();
            schedulePage.navigateToPreviousWeek();
//            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(true, false, "Published", "Previous Week");   //comment it because bug: https://legiontech.atlassian.net/browse/SCH-1482
//            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(true, false, "Not Started", "Previous Week");//comment it because bug: https://legiontech.atlassian.net/browse/SCH-1874
//            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(true, false, "In Progress", "Previous Week"); //comment it because bug: https://legiontech.atlassian.net/browse/SCH-1482

            //Validate the numbers on Schedule Status Cards
            scheduleDMViewPage.verifyTheScheduleStatusAccountOnScheduleStatusCards();
        } catch (Exception e) {
            SimpleUtils.fail(e.toString(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "DGStage_Enterprise")
    @TestName(description = "Verify Schedule Status on Schedule in DM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyScheduleStatusAndHoursOnScheduleDMViewOnTADGEnvAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.reSelectDistrict(districtName);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();

            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();

            //Validate the schedule status and hours on schedule list
            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(true, true, "Published", "Current Week");
            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(true, true, "Not Started", "Current Week");            //comment it because bug: https://legiontech.atlassian.net/browse/SCH-1874
            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(true, true, "In Progress", "Current Week");       // comment it because bug: https://legiontech.atlassian.net/browse/SCH-1653

            //Validate the schedule status and hours on schedule list
            schedulePage.navigateToNextWeek();
            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(true, true, "Published", "Next Week");
            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(true, true, "Not Started", "Next Week");//comment it because bug: https://legiontech.atlassian.net/browse/SCH-1874
            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(true, true, "In Progress", "Next Week");      //comment it because bug: https://legiontech.atlassian.net/browse/SCH-1653

            //Validate the schedule status and hours on schedule list
            schedulePage.navigateToPreviousWeek();
            schedulePage.navigateToPreviousWeek();
            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(true, true, "Published", "Previous Week");   //comment it because bug: https://legiontech.atlassian.net/browse/SCH-1482
            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(true, true, "Not Started", "Previous Week");//comment it because bug: https://legiontech.atlassian.net/browse/SCH-1874
            scheduleDMViewPage.verifyScheduleStatusAndHoursInScheduleList(true, true, "In Progress", "Previous Week"); //comment it because bug: https://legiontech.atlassian.net/browse/SCH-1482

            //Validate the numbers on Schedule Status Cards
            scheduleDMViewPage.verifyTheScheduleStatusAccountOnScheduleStatusCards();
        } catch (Exception e) {
            SimpleUtils.fail(e.toString(),false);
        }
    }
}
