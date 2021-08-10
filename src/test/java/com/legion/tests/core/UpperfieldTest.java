package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.core.ConsoleLocationSelectorPage;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpperfieldTest extends TestBase {

    private static String District = "District";
    private static String Region = "Region";
    private static String BusinessUnit = "Business Unit";

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception {
        try {
            this.createDriver((String) params[0], "83", "Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify Dashboard functionality in BU View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDashboardFunctionalityInBUViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get("Region"));
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get("BU"));

            // Validate the title
            dashboardPage.verifyHeaderOnDashboard();
            locationSelectorPage.verifyTheDisplayBUWithSelectedBUConsistent(selectedUpperFields.get("BU"));
            locationSelectorPage.isBUView();

            // Validate the presence of BU
            dashboardPage.validateThePresenceOfUpperfield();

            // Validate the visibility of week
            dashboardPage.validateTheVisibilityOfWeek();

            // Validate welcome content
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String nickName = profileNewUIPage.getNickNameFromProfile();
            dashboardPage.verifyTheWelcomeMessageForUpperfield(nickName);

            // Validate changing BUs on Dashboard
            locationSelectorPage.changeAnotherBUInBUView();
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            String buName = selectedUpperFields.get("BU");
            String buOnDashboard = dashboardPage.getUpperfieldNameOnDashboard();
            if (buName.equals(buOnDashboard))
                SimpleUtils.pass("Dashboard Page: When the user selects a different BU from the BU view, the data updates to reflect the selected BU");
            else
                SimpleUtils.fail("When the user selects a different BU from the BU view, the data doesn't update to reflect the selected BU",false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify Dashboard functionality in Region View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDashboardFunctionalityInRegionViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get("Region"));

            // Validate the title
            dashboardPage.verifyHeaderOnDashboard();
            locationSelectorPage.verifyTheDisplayRegionWithSelectedRegionConsistent(selectedUpperFields.get("Region"));
            locationSelectorPage.isRegionView();

            // Validate the presence of region
            dashboardPage.validateThePresenceOfUpperfield();

            // Validate the visibility of week
            dashboardPage.validateTheVisibilityOfWeek();

            // Validate welcome content
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String nickName = profileNewUIPage.getNickNameFromProfile();
            dashboardPage.verifyTheWelcomeMessageForUpperfield(nickName);

            // Validate changing regions on Dashboard
            locationSelectorPage.changeAnotherRegionInRegionView();
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            String regionName = selectedUpperFields.get("Region");
            String regionOnDashboard = dashboardPage.getUpperfieldNameOnDashboard();
            if (regionName.equals(regionOnDashboard))
                SimpleUtils.pass("Dashboard Page: When the user selects a different region from the region view, the data updates to reflect the selected region");
            else
                SimpleUtils.fail("When the user selects a different region from the region view, the data doesn't update to reflect the selected region",false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify Refresh feature on Dashboard in BU View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyRefreshFeatureOnDashboardInBUViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get("Region"));
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get("BU"));

            // Validate the presence of Refresh button
            dashboardPage.validateThePresenceOfRefreshButtonUpperfield();

            // Validate Refresh timestamp
            dashboardPage.validateRefreshTimestampUpperfield();

            // Validate Refresh when navigation back
            dashboardPage.validateRefreshWhenNavigationBackUpperfied();

            // Validate Refresh function
            dashboardPage.validateRefreshFunctionUpperfield();

            // Validate Refresh performance
            dashboardPage.validateRefreshPerformanceUpperfield();

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify Refresh feature on Dashboard in Region View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyRefreshFeatureOnDashboardInRegionViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get("Region"));

            // Validate the presence of Refresh button
            dashboardPage.validateThePresenceOfRefreshButtonUpperfield();

            // Validate Refresh timestamp
            dashboardPage.validateRefreshTimestampUpperfield();

            // Validate Refresh when navigation back
            dashboardPage.validateRefreshWhenNavigationBackUpperfied();

            // Validate Refresh function
            dashboardPage.validateRefreshFunctionUpperfield();

            // Validate Refresh performance
            dashboardPage.validateRefreshPerformanceUpperfield();

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Projected Compliance widget on Dashboard in BU View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyProjectedComplianceWidgetOnDashboardInBUViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get("Region"));
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get("BU"));

            SimpleUtils.assertOnFail("Project Compliance widget not loaded successfully", dashboardPage.isProjectedComplianceWidgetDisplay(), false);

            // Validate the content in Projected Compliance widget without TA
            dashboardPage.verifyTheContentInProjectedComplianceWidget();

            // Validate the data in Projected Compliance widget without TA
            String totalViolationHrsFromProjectedComplianceWidget =
            dashboardPage.getTheTotalViolationHrsFromProjectedComplianceWidget();
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            dashboardPage.clickOnViewViolationsLink();
            String totalViolationHrsFromCompliancePage =
                    compliancePage.getTheTotalViolationHrsFromSmartCard().split(" ")[0];
            SimpleUtils.assertOnFail("Project Compliance widget not loaded successfully",
                    totalViolationHrsFromProjectedComplianceWidget.equals(totalViolationHrsFromCompliancePage), false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Projected Compliance widget on Dashboard in Region View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyProjectedComplianceWidgetOnDashboardInRegionViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get("Region"));

            SimpleUtils.assertOnFail("Project Compliance widget not loaded successfully", dashboardPage.isProjectedComplianceWidgetDisplay(), false);

            // Validate the content in Projected Compliance widget without TA
            dashboardPage.verifyTheContentInProjectedComplianceWidget();

            // Validate the data in Projected Compliance widget without TA
            String totalViolationHrsFromProjectedComplianceWidget =
                    dashboardPage.getTheTotalViolationHrsFromProjectedComplianceWidget();
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            dashboardPage.clickOnViewViolationsLink();
            String totalViolationHrsFromCompliancePage =
                    compliancePage.getTheTotalViolationHrsFromSmartCard().split(" ")[0];
            SimpleUtils.assertOnFail("Project Compliance widget not loaded successfully",
                    totalViolationHrsFromProjectedComplianceWidget.equals(totalViolationHrsFromCompliancePage), false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify Timesheet Approval Rate widget on Dashboard in BU View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTimesheetApprovalRateWidgetOnDashboardInBUViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get("Region"));
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get("BU"));

            SimpleUtils.assertOnFail("Timesheet Approval Rate widget not loaded successfully", dashboardPage.isTimesheetApprovalRateWidgetDisplay(), false);

            // Validate the content on Timesheet Approval Rate widget on TA env
            dashboardPage.validateTheContentOnTimesheetApprovalRateWidgetInUpperfieldView();

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
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify Timesheet Approval Rate widget on Dashboard in Region View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTimesheetApprovalRateWidgetOnDashboardInRegionViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get("Region"));

            SimpleUtils.assertOnFail("Timesheet Approval Rate widget not loaded successfully", dashboardPage.isTimesheetApprovalRateWidgetDisplay(), false);

            // Validate the content on Timesheet Approval Rate widget on TA env
            dashboardPage.validateTheContentOnTimesheetApprovalRateWidgetInUpperfieldView();

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
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Schedule Publish Status widget on Dashboard in BU View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifySchedulePublishStatusWidgetOnDashboardInBUViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get("Region"));
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get("BU"));

            SimpleUtils.assertOnFail("Schedule Publish Status widget not loaded successfully", dashboardPage.isSchedulePublishStatusWidgetDisplay(), false);

            // Validate the content in Schedule Publish Status widget without TA
            dashboardPage.verifyTheContentInSchedulePublishStatusWidget();

            // Validate data in Schedule Publish Status widget without TA
            Map<String, Integer> scheduleStatusFromSchedulePublishStatusWidget = dashboardPage.getAllScheduleStatusFromSchedulePublishStatusWidget();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            dashboardPage.clickOnViewSchedulesLinkInSchedulePublishStatusWidget();
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            Map<String, Integer> scheduleStatusFromScheduleDMViewPage = scheduleDMViewPage.getThreeWeeksScheduleStatusFromScheduleDMViewPage();
            SimpleUtils.assertOnFail("Schedule status on Schedule Publish Status widget and Schedule region view page are different! ",
                    scheduleStatusFromSchedulePublishStatusWidget.equals(scheduleStatusFromScheduleDMViewPage), false);


            // Validate status value in Schedule Publish Status widget without TA
            dashboardPage.navigateToDashboard();
            dashboardPage.validateTooltipsOfSchedulePublishStatusWidget();

        } catch (Exception e) {
            SimpleUtils.fail(e.toString(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Schedule Publish Status widget on Dashboard in Region View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifySchedulePublishStatusWidgetOnDashboardInRegionViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get("Region"));

            SimpleUtils.assertOnFail("Schedule Publish Status widget not loaded successfully", dashboardPage.isSchedulePublishStatusWidgetDisplay(), false);

            // Validate the content in Schedule Publish Status widget without TA
            dashboardPage.verifyTheContentInSchedulePublishStatusWidget();

            // Validate data in Schedule Publish Status widget without TA
            Map<String, Integer> scheduleStatusFromSchedulePublishStatusWidget = dashboardPage.getAllScheduleStatusFromSchedulePublishStatusWidget();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            dashboardPage.clickOnViewSchedulesLinkInSchedulePublishStatusWidget();
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            Map<String, Integer> scheduleStatusFromScheduleDMViewPage = scheduleDMViewPage.getThreeWeeksScheduleStatusFromScheduleDMViewPage();
            SimpleUtils.assertOnFail("Schedule status on Schedule Publish Status widget and Schedule region view page are different! ",
                    scheduleStatusFromSchedulePublishStatusWidget.equals(scheduleStatusFromScheduleDMViewPage), false);


            // Validate status value in Schedule Publish Status widget without TA
            dashboardPage.navigateToDashboard();
            dashboardPage.validateTooltipsOfSchedulePublishStatusWidget();

        } catch (Exception e) {
            SimpleUtils.fail(e.toString(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Schedule vs. Guidance by Day widget on Dashboard in BU View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifySchedulesGuidanceByDayWidgetOnDashboardInBUViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get("Region"));
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get("BU"));

            // Set 'Apply labor budget to schedules?' to No
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.updateApplyLaborBudgetToSchedules("No");
            dashboardPage.clickOnDashboardConsoleMenu();
            SimpleUtils.assertOnFail("Schedule Vs Guidance By Day widget loaded fail! ",
                    dashboardPage.isScheduleVsGuidanceByDayWidgetDisplay(), false);

            //Validate the content on Schedule Vs Guidance By Day widget display correctly
            dashboardPage.verifyTheContentOnScheduleVsGuidanceByDayWidget();

            // Validate the Guidance comparison in Schedule vs. Guidance by Day widget without TA
            dashboardPage.verifyTheHrsUnderOrCoverBudgetOnScheduleVsGuidanceByDayWidget();

            // Validate the week information in Schedule vs. Guidance by Day widget without TA
            String weekOnScheduleVsGuidanceByDayWidget = dashboardPage.getWeekOnScheduleVsGuidanceByDayWidget();
            String weekInfoFromUpperfieldView = dashboardPage.getWeekInfoFromUpperfieldView();
            SimpleUtils.assertOnFail("The week in Schedule vs. Guidance by Day widget is inconsistent with the week of welcome section of Dashboard page",
                    weekOnScheduleVsGuidanceByDayWidget.equalsIgnoreCase(weekInfoFromUpperfieldView.substring(8)), false);
            dashboardPage.clickOnViewSchedulesOnScheduleVsGuidanceByDayWidget();
            SimpleUtils.assertOnFail("Schedule page not loaded successfully", dashboardPage.isScheduleConsoleMenuDisplay(), false);
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            String currentWeekInDMViewSchedule = scheduleDMViewPage.getCurrentWeekInDMView();
            dashboardPage.validateWeekOnScheduleVsGuidanceByDayWidget(weekOnScheduleVsGuidanceByDayWidget, currentWeekInDMViewSchedule);

            dashboardPage.navigateToDashboard();
            SimpleUtils.assertOnFail("Schedule Vs Guidance By Day widget loaded fail! ",
                    dashboardPage.isScheduleVsGuidanceByDayWidgetDisplay(), false);

            // Validate the data in Schedule vs. Guidance by Day without TA
            HashMap<String, Integer> theSumOfValues = dashboardPage.getTheSumOfValuesOnScheduleVsGuidanceByDayWidget();
            List<String> dataOnLocationSummaryWidget = dashboardPage.getTheDataOnLocationSummaryWidget();
            if (theSumOfValues.get("Guidance").equals(Integer.valueOf(dataOnLocationSummaryWidget.get(0)))
                    && theSumOfValues.get("Scheduled").equals(Integer.valueOf(dataOnLocationSummaryWidget.get(1))))
                SimpleUtils.pass("Dashboard Page: The sum of days matches the numbers in Region Summary widget for Guidance and Scheduled");
            else
                SimpleUtils.fail("Dashboard Page: The sum of days doesn't match the numbers in Region Summary widget for Guidance and Scheduled",false);

            // Validate value on in Schedule vs. Guidance by Day widget without TA
            dashboardPage.validateValueInScheduleVsGuidanceByDayWidget();

            //  Set 'Apply labor budget to schedules?' to Yes
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.updateApplyLaborBudgetToSchedules("Yes");
            dashboardPage.clickOnDashboardConsoleMenu();
            SimpleUtils.assertOnFail("Schedule Vs Guidance By Day widget loaded fail! ",
                    dashboardPage.isScheduleVsGuidanceByDayWidgetDisplay(), false);

            // Validate the content in Schedule vs. Budget by Day widget without TA
            dashboardPage.verifyTheContentOnScheduleVsBudgetByDayWidget();

            // Validate the data in Schedule vs. Budget by Day widget without TA
            theSumOfValues = dashboardPage.getTheSumOfValuesOnScheduleVsGuidanceByDayWidget();
            dataOnLocationSummaryWidget = dashboardPage.getTheDataOnLocationSummaryWidget();
            if (theSumOfValues.get("Guidance").equals(Integer.valueOf(dataOnLocationSummaryWidget.get(0)))
                    && theSumOfValues.get("Scheduled").equals(Integer.valueOf(dataOnLocationSummaryWidget.get(1))))
                SimpleUtils.pass("Dashboard Page: The sum of days matches the numbers in Region Summary widget for Budgeted and Scheduled");
            else
                SimpleUtils.fail("Dashboard Page: The sum of days doesn't match the numbers in Region Summary widget for Budgeted and Scheduled",false);

        } catch (Exception e) {
            SimpleUtils.fail(e.toString(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Schedule vs. Guidance by Day widget on Dashboard in Region View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifySchedulesGuidanceByDayWidgetOnDashboardInRegionViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get("Region"));

            // Set 'Apply labor budget to schedules?' to No
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.updateApplyLaborBudgetToSchedules("No");
            dashboardPage.clickOnDashboardConsoleMenu();
            SimpleUtils.assertOnFail("Schedule Vs Guidance By Day widget loaded fail! ",
                    dashboardPage.isScheduleVsGuidanceByDayWidgetDisplay(), false);

            //Validate the content on Schedule Vs Guidance By Day widget display correctly
            dashboardPage.verifyTheContentOnScheduleVsGuidanceByDayWidget();

            // Validate the Guidance comparison in Schedule vs. Guidance by Day widget without TA
            dashboardPage.verifyTheHrsUnderOrCoverBudgetOnScheduleVsGuidanceByDayWidget();

            // Validate the week information in Schedule vs. Guidance by Day widget without TA
            String weekOnScheduleVsGuidanceByDayWidget = dashboardPage.getWeekOnScheduleVsGuidanceByDayWidget();
            String weekInfoFromUpperfieldView = dashboardPage.getWeekInfoFromUpperfieldView();
            SimpleUtils.assertOnFail("The week in Schedule vs. Guidance by Day widget is inconsistent with the week of welcome section of Dashboard page",
                    weekOnScheduleVsGuidanceByDayWidget.equalsIgnoreCase(weekInfoFromUpperfieldView.substring(8)), false);
            dashboardPage.clickOnViewSchedulesOnScheduleVsGuidanceByDayWidget();
            SimpleUtils.assertOnFail("Schedule page not loaded successfully", dashboardPage.isScheduleConsoleMenuDisplay(), false);
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            String currentWeekInDMViewSchedule = scheduleDMViewPage.getCurrentWeekInDMView();
            dashboardPage.validateWeekOnScheduleVsGuidanceByDayWidget(weekOnScheduleVsGuidanceByDayWidget, currentWeekInDMViewSchedule);

            dashboardPage.navigateToDashboard();
            SimpleUtils.assertOnFail("Schedule Vs Guidance By Day widget loaded fail! ",
                    dashboardPage.isScheduleVsGuidanceByDayWidgetDisplay(), false);

            // Validate the data in Schedule vs. Guidance by Day without TA
            HashMap<String, Integer> theSumOfValues = dashboardPage.getTheSumOfValuesOnScheduleVsGuidanceByDayWidget();
            List<String> dataOnLocationSummaryWidget = dashboardPage.getTheDataOnLocationSummaryWidget();
            if (theSumOfValues.get("Guidance").equals(Integer.valueOf(dataOnLocationSummaryWidget.get(0)))
                    && theSumOfValues.get("Scheduled").equals(Integer.valueOf(dataOnLocationSummaryWidget.get(1))))
                SimpleUtils.pass("Dashboard Page: The sum of days matches the numbers in District Summary widget for Guidance and Scheduled");
            else
                SimpleUtils.fail("Dashboard Page: The sum of days doesn't match the numbers in District Summary widget for Guidance and Scheduled",false);

            // Validate value on in Schedule vs. Guidance by Day widget without TA
            dashboardPage.validateValueInScheduleVsGuidanceByDayWidget();

            //  Set 'Apply labor budget to schedules?' to Yes
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.updateApplyLaborBudgetToSchedules("Yes");
            dashboardPage.clickOnDashboardConsoleMenu();
            SimpleUtils.assertOnFail("Schedule Vs Guidance By Day widget loaded fail! ",
                    dashboardPage.isScheduleVsGuidanceByDayWidgetDisplay(), false);

            // Validate the content in Schedule vs. Budget by Day widget without TA
            dashboardPage.verifyTheContentOnScheduleVsBudgetByDayWidget();

            // Validate the data in Schedule vs. Budget by Day widget without TA
            theSumOfValues = dashboardPage.getTheSumOfValuesOnScheduleVsGuidanceByDayWidget();
            dataOnLocationSummaryWidget = dashboardPage.getTheDataOnLocationSummaryWidget();
            if (theSumOfValues.get("Guidance").equals(Integer.valueOf(dataOnLocationSummaryWidget.get(0)))
                    && theSumOfValues.get("Scheduled").equals(Integer.valueOf(dataOnLocationSummaryWidget.get(1))))
                SimpleUtils.pass("Dashboard Page: The sum of days matches the numbers in District Summary widget for Budgeted and Scheduled");
            else
                SimpleUtils.fail("Dashboard Page: The sum of days doesn't match the numbers in District Summary widget for Budgeted and Scheduled",false);

        } catch (Exception e) {
            SimpleUtils.fail(e.toString(),false);
        }
    }

    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Open Shifts widget on Dashboard in BU View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyOpenShiftsWidgetOnDashboardInBUViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get("Region"));
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get("BU"));

            SimpleUtils.assertOnFail("Open Shifts widget not loaded successfully", dashboardPage.isOpenShiftsWidgetPresent(), false);

            //Get values on open shifts widget and verify the info on Open_Shifts Widget
            if (dashboardPage.isRefreshButtonDisplay())
                dashboardPage.clickOnRefreshButton();
            HashMap<String, Integer> valuesOnOpenShiftsWidget = dashboardPage.verifyContentOfOpenShiftsWidgetForUpperfield();

            // Validate View Schedules link is clickable
            dashboardPage.clickViewSchedulesLinkOnOpenShiftsWidget();

            // Validate the data in Open Shifts widget without TA
           // todo: blocked by the bug https://legiontech.atlassian.net/browse/SCH-3224

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Open Shifts widget on Dashboard in Region View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyOpenShiftsWidgetOnDashboardInRegionViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get("Region"));
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get("BU"));

            SimpleUtils.assertOnFail("Open Shifts widget not loaded successfully", dashboardPage.isOpenShiftsWidgetPresent(), false);

            //Get values on open shifts widget and verify the info on Open_Shifts Widget
            if (dashboardPage.isRefreshButtonDisplay())
                dashboardPage.clickOnRefreshButton();
            HashMap<String, Integer> valuesOnOpenShiftsWidget = dashboardPage.verifyContentOfOpenShiftsWidgetForUpperfield();

            // Validate View Schedules link is clickable
            dashboardPage.clickOnViewSchedulesOnOpenShiftsWidget();

            // Validate the data in Open Shifts widget without TA
            // todo: blocked by the bug https://legiontech.atlassian.net/browse/SCH-3224

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify Compliance Violations widget on Dashboard in BU View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyComplianceViolationsWidgetOnDashboardInBUViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get("Region"));
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get("BU"));

            SimpleUtils.assertOnFail("Compliance Violations widget not loaded successfully", dashboardPage.isComplianceViolationsWidgetDisplay(), false);

            // Validate the content on Compliance Violations widget on TA env
            dashboardPage.validateTheContentOnComplianceViolationsWidgetInUpperfield();

            // Validate the data in Compliance Violations widget with TA between dashboard and compliance
            if (dashboardPage.isRefreshButtonDisplay())
                dashboardPage.clickOnRefreshButton();
            List<String> complianceViolationsOnBUViewDashboard = dashboardPage.getComplianceViolationsOnDashboard();
            dashboardPage.clickOnViewViolationsLink();
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            Thread.sleep(5000);
            if (compliancePage.isRefreshButtonDisplay())
                compliancePage.clickOnRefreshButton();
            List<String> complianceViolationsFromOnBUViewCompliance = compliancePage.getComplianceViolationsOnSmartCard();
            dashboardPage.validateDataOnComplianceViolationsWidget(complianceViolationsOnBUViewDashboard, complianceViolationsFromOnBUViewCompliance);

            // Validate the data in Compliance Violations widget with TA between BU view and region view
            dashboardPage.navigateToDashboard();
            List<String> regionList = locationSelectorPage.getOrgList();
            Float totalViolationHours = 0.00f;
            int totalViolations = 0;
            for (String region: regionList) {
                locationSelectorPage.changeUpperFieldDirect(Region, region);
                if (dashboardPage.isRefreshButtonDisplay())
                    dashboardPage.clickOnRefreshButton();
                List<String> complianceViolations = dashboardPage.getComplianceViolationsOnDashboard();
                totalViolationHours += Float.valueOf(complianceViolations.get(0).split(" ")[0]);
                totalViolations += Integer.valueOf(complianceViolations.get(1).split(" ")[0]);
            }
            if (totalViolationHours== Float.valueOf(complianceViolationsOnBUViewDashboard.get(0).split(" ")[0])
                    && totalViolations == Integer.valueOf(complianceViolationsOnBUViewDashboard.get(1).split(" ")[0]))
                SimpleUtils.pass("The data of Compliance Violations widget on dashboard is consistent with the summary of each region");
            else
                // SimpleUtils.fail("The data of Compliance Violations widget on dashboard is inconsistent with the summary of each region",false);
                SimpleUtils.warn("SCH-4937: The past week's Extra hours on Compliance BU view is inconsistent with the sum of the extra hours on Region view");
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify Compliance Violations widget on Dashboard in Region View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyComplianceViolationsWidgetOnDashboardInRegionViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get("Region"));

            SimpleUtils.assertOnFail("Compliance Violations widget not loaded successfully", dashboardPage.isComplianceViolationsWidgetDisplay(), false);

            // Validate the content on Compliance Violations widget on TA env
            dashboardPage.validateTheContentOnComplianceViolationsWidgetInUpperfield();

            // Validate the data in Compliance Violations widget with TA between dashboard and compliance
            if (dashboardPage.isRefreshButtonDisplay())
                dashboardPage.clickOnRefreshButton();
            List<String> complianceViolationsOnRegionViewDashboard = dashboardPage.getComplianceViolationsOnDashboard();
            dashboardPage.clickOnViewViolationsLink();
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            Thread.sleep(5000);
            if (compliancePage.isRefreshButtonDisplay())
                compliancePage.clickOnRefreshButton();
            List<String> complianceViolationsFromOnRegionViewCompliance = compliancePage.getComplianceViolationsOnSmartCard();
            dashboardPage.validateDataOnComplianceViolationsWidget(complianceViolationsOnRegionViewDashboard, complianceViolationsFromOnRegionViewCompliance);

            // Validate the data in Compliance Violations widget with TA between BU view and region view
            dashboardPage.navigateToDashboard();
            List<String> districtList = locationSelectorPage.getOrgList();
            Float totalViolationHours = 0.00f;
            int totalViolations = 0;
            for (String district: districtList) {
                locationSelectorPage.changeDistrict(district);
                if (dashboardPage.isRefreshButtonDisplay())
                    dashboardPage.clickOnRefreshButton();
                List<String> complianceViolations = dashboardPage.getComplianceViolationsOnDashboard();
                totalViolationHours += Float.valueOf(complianceViolations.get(0).split(" ")[0]);
                totalViolations += Integer.valueOf(complianceViolations.get(1).split(" ")[0]);
            }
            if (totalViolationHours== Float.valueOf(complianceViolationsOnRegionViewDashboard.get(0).split(" ")[0])
                    && totalViolations == Integer.valueOf(complianceViolationsOnRegionViewDashboard.get(1).split(" ")[0]))
                SimpleUtils.pass("The data of Compliance Violations widget on dashboard is consistent with the summary of each region");
            else
                // SimpleUtils.fail("The data of Compliance Violations widget on dashboard is inconsistent with the summary of each region",false);
                SimpleUtils.warn("SCH-4937: The past week's Extra hours on Compliance BU view is inconsistent with the sum of the extra hours on Region view");
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify Compliance Violations widget on Dashboard in BU View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyPayrollProjectionWidgetOnDashboardInBUViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get("Region"));

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
}
