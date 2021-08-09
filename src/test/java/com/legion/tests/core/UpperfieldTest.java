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

    private static String Location = "Location";
    private static String District = "District";
    private static String Region = "Region";
    private static String BusinessUnit = "Business Unit";

    String[] upperFields2 = districtsMap.get("Coffee_Enterprise2").split(">");
    String[] upperFields3 = districtsMap.get("Coffee_Enterprise3").split(">");

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
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get(BusinessUnit));

            // Validate the title
            dashboardPage.verifyHeaderOnDashboard();
            locationSelectorPage.verifyTheDisplayBUWithSelectedBUConsistent(selectedUpperFields.get(BusinessUnit));
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
            String buName = selectedUpperFields.get(BusinessUnit);
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
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get(BusinessUnit));

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
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get(BusinessUnit));

            SimpleUtils.assertOnFail("Project Compliance widget not loaded successfully", dashboardPage.isProjectedComplianceWidgetDisplay(), false);

            // Validate the content in Projected Compliance widget without TA
            dashboardPage.verifyTheContentInProjectedComplianceWidget();

            // Validate the data in Projected Compliance widget without TA
            String totalViolationHrsFromProjectedComplianceWidget =
                    dashboardPage.getTheTotalViolationHrsFromProjectedComplianceWidget();
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            dashboardPage.clickOnViewComplianceLink();
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
            dashboardPage.clickOnViewComplianceLink();
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
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get(BusinessUnit));

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
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get(BusinessUnit));

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
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get(BusinessUnit));

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
    public void verifyOpenShiftsWidgetOnDashboardInBUViewAsInternalAdmin(String browser, String username, String password, String location) {
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
                openRateExpected = Math.round((float)(openShiftsNumForLoc1+openShiftsNumForLoc2)*100/(shiftsNumForLoc1+shiftsNumForLoc2));
            }
            //int assignedRateExpected = 100-openRateExpected;
            int assignedRateExpected = Math.round((float)(shiftsNumForLoc1+shiftsNumForLoc2-openShiftsNumForLoc1-openShiftsNumForLoc2)*100/(shiftsNumForLoc1+shiftsNumForLoc2));

            // refresh shifts offer KPI.
            dashboardPage.navigateToDashboard();
            locationSelectorPage.changeLocation(location);
            AnalyticsPage analyticsPage = pageFactory.createConsoleAnalyticsPage();
            analyticsPage.gotoAnalyticsPage();
            analyticsPage.switchAllLocationsOrSingleLocation(false);
            analyticsPage.mouseHoverAndRefreshByName("Shift Offer KPI");

            dashboardPage.navigateToDashboard();
            locationSelectorPage.reSelectDistrict(districtName);

            SimpleUtils.assertOnFail("Open Shifts widget not loaded successfully", dashboardPage.isOpenShiftsWidgetDisplay(), false);
            String currentWeek = dashboardPage.getWeekInfoFromDMView();

            //Get values on open shifts widget and verify the info on Open_Shifts Widget
            HashMap<String, Integer> valuesOnOpenShiftsWidget = dashboardPage.verifyContentOfOpenShiftsWidgetForDMView();

            // Verify navigation to schedule page by "View Schedules" button on Open_Shifts Widget
            dashboardPage.clickViewSchedulesLinkOnOpenShiftsWidget();
            SimpleUtils.assertOnFail("Schedule page not loaded Successfully!", schedulePage.isScheduleDMView(), false);
            String[] weekInfoInDMView = MyThreadLocal.getDriver().findElement(By.cssSelector(".day-week-picker-period-active")).getText().toLowerCase().split("\n");
            String weekInfoExpected = schedulePage.convertDateStringFormat(weekInfoInDMView[weekInfoInDMView.length-1]);
            if (currentWeek.toLowerCase().contains(weekInfoExpected.toLowerCase())) {
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


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Region View Navigation")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyRegionViewNavigationAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            String regionName = selectedUpperFields.get(Region);
            String districtName = selectedUpperFields.get(District);
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            locationSelectorPage.isRegionView();

            //Validate user has see multiple regions in upperfield dropdown list
            List<String> upperFieldNames = locationSelectorPage.getAllUpperFieldNamesInUpperFieldDropdownList(Region);
            SimpleUtils.assertOnFail("The selected region should display in the search region dropdown list!", upperFieldNames.contains(selectedUpperFields.get(Region)), false);

            //Validate drilling into a district
            locationSelectorPage.changeUpperFieldDirect(District, districtName);
            locationSelectorPage.isDMView();

            //Validate navigating back to region view
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            locationSelectorPage.isRegionView();

            //Validate navigating back to region view
            SimpleUtils.assertOnFail("Schedule Region view page not loaded Successfully!",
                    schedulePage.isScheduleDMView(), false);
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(districtName);

            //Validate changing regions
            String regionName2 = upperFields2[upperFields2.length-2].trim();
            String districtName2 = upperFields2[upperFields2.length-1].trim();
            locationSelectorPage.changeUpperFieldDirect(Region, regionName2);
            SimpleUtils.assertOnFail("Schedule Region view page not loaded Successfully!",
                    schedulePage.isScheduleDMView(), false);
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(districtName2);
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            timeSheetPage.clickOnTimeSheetConsoleMenu();
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            SimpleUtils.assertOnFail("The selected region display incorrectly! The expected region is: " + regionName2 +
                            " . The actual region name is " + selectedUpperFields.get(Region),
                    selectedUpperFields.get(Region).equalsIgnoreCase(regionName2), false);
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);

            //Validate changing dates and district
            schedulePage.clickOnScheduleConsoleMenuItem();
            locationSelectorPage.changeUpperFieldDirect(District, districtName);
            locationSelectorPage.isDMView();
            schedulePage.navigateToNextWeek();
            String selectedWeekInfo = schedulePage.getSelectedWeek();
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            SimpleUtils.assertOnFail("The selected week should not been changed after change to region view from DM view! ",
                    schedulePage.getSelectedWeek().equalsIgnoreCase(selectedWeekInfo), false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "BU View Navigation")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyBUViewNavigationAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            String regionName = selectedUpperFields.get(Region);
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            String buName = selectedUpperFields.get(BusinessUnit);
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);

            //Validate user has access to multiple BUs in upperfield dropdown list
            List<String> upperFieldNames = locationSelectorPage.getAllUpperFieldNamesInUpperFieldDropdownList(BusinessUnit);
            SimpleUtils.assertOnFail("The selected region should display in the search region dropdown list!",
                    upperFieldNames.contains(buName), false);

            //Validate drilling into a region
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            locationSelectorPage.isRegionView();

            //Validate navigating back to BU view
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);
            locationSelectorPage.isBUView();

            SimpleUtils.assertOnFail("Schedule BU view page not loaded Successfully!",
                    schedulePage.isScheduleDMView(), false);
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(regionName);

            //Validate changing BUs
            String buName2 = upperFields3[upperFields3.length-3].trim();
            String regionName2 = upperFields3[upperFields3.length-2].trim();
            String districtName2 = upperFields3[upperFields3.length-1].trim();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName2);
            SimpleUtils.assertOnFail("Schedule BU view page not loaded Successfully!",
                    schedulePage.isScheduleDMView(), false);
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(regionName2);
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            timeSheetPage.clickOnTimeSheetConsoleMenu();
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            SimpleUtils.assertOnFail("The selected BU display incorrectly! The expected BU is: " + buName2 +
                            " . The actual BU name is " + selectedUpperFields.get(BusinessUnit),
                    selectedUpperFields.get(BusinessUnit).equalsIgnoreCase(buName2), false);
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);

            //Validate changing dates and district
            schedulePage.clickOnScheduleConsoleMenuItem();
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            schedulePage.navigateToNextWeek();
            String selectedWeekInfo = schedulePage.getSelectedWeek();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);
            SimpleUtils.assertOnFail("The selected week should not been changed after change to BU view from region view! ",
                    schedulePage.getSelectedWeek().equalsIgnoreCase(selectedWeekInfo), false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }



    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify Controls in BU View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyControlsInBUViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            String regionName = selectedUpperFields.get(Region);
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            String buName = selectedUpperFields.get(BusinessUnit);
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);

            //Validate Controls existing from BU view
            SimpleUtils.assertOnFail("Controls menu tab should be available on BU view",
                    !dashboardPage.isConsoleNavigationBarIsGray("Controls"), false);

            //Validate navigate to Controls from BU view
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            SimpleUtils.assertOnFail("Controls page fail to load! ",
                    controlsNewUIPage.isControlsPageLoaded(), false);
            //Title: Controls Global > All Locations
            controlsNewUIPage.getCurrentLocationInControls().equalsIgnoreCase("All Locations");

            //Validate week navigation in BU View getting updated based on schedule planning window settings
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.updateSchedulePlanningWindow("5 weeks", false, true);

            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            String weekInfo = "";
            String selectedWeekInfo = schedulePage.getSelectedWeek();
            int i = 0;
            while (!weekInfo.equalsIgnoreCase(selectedWeekInfo) && schedulePage.hasNextWeek()){
                weekInfo = schedulePage.getSelectedWeek();
                schedulePage.navigateToNextWeek();
                selectedWeekInfo = schedulePage.getSelectedWeek();
                i++;
            }
            SimpleUtils.assertOnFail("The week navigation in BU View should get updated to " +
                    "5 weeks based on schedule planning window settings for Schedule, but the actual weeks are: " + i,
                    i==5, false);

            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            compliancePage.clickOnComplianceConsoleMenu();

            weekInfo = "";
            selectedWeekInfo = schedulePage.getSelectedWeek();
            i = 0;
            while (!weekInfo.equalsIgnoreCase(selectedWeekInfo)&& schedulePage.hasNextWeek()){
                weekInfo = schedulePage.getSelectedWeek();
                schedulePage.navigateToNextWeek();
                selectedWeekInfo = schedulePage.getSelectedWeek();
                i++;
            }
            SimpleUtils.assertOnFail("The week navigation in BU View should get updated to be " +
                            "5 weeks based on schedule planning window settings for Compliance, but the actual weeks are: " + i,
                    i==5, false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }



    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify Controls in Region View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyControlsInRegionViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            String regionName = selectedUpperFields.get(Region);
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);

            //Validate Controls existing from Region view
            SimpleUtils.assertOnFail("Controls menu tab should be available on Region view",
                    !dashboardPage.isConsoleNavigationBarIsGray("Controls"), false);

            //Validate navigate to Controls from Region view
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            SimpleUtils.assertOnFail("Controls page fail to load! ",
                    controlsNewUIPage.isControlsPageLoaded(), false);
            //Title: Controls Global > All Locations
            controlsNewUIPage.getCurrentLocationInControls().equalsIgnoreCase("All Locations");

            //Validate week navigation in Region View getting updated based on schedule planning window settings
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.updateSchedulePlanningWindow("5 weeks", false, true);

            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            String weekInfo = "";
            String selectedWeekInfo = schedulePage.getSelectedWeek();
            int i = 0;
            while (!weekInfo.equalsIgnoreCase(selectedWeekInfo) && schedulePage.hasNextWeek()){
                weekInfo = schedulePage.getSelectedWeek();
                schedulePage.navigateToNextWeek();
                selectedWeekInfo = schedulePage.getSelectedWeek();
                i++;
            }
            SimpleUtils.assertOnFail("The week navigation in Region View should get updated to " +
                            "5 weeks based on schedule planning window settings for Schedule, but the actual weeks are: " + i,
                    i==5, false);

            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            compliancePage.clickOnComplianceConsoleMenu();

            weekInfo = "";
            selectedWeekInfo = schedulePage.getSelectedWeek();
            i = 0;
            while (!weekInfo.equalsIgnoreCase(selectedWeekInfo)&& schedulePage.hasNextWeek()){
                weekInfo = schedulePage.getSelectedWeek();
                schedulePage.navigateToNextWeek();
                selectedWeekInfo = schedulePage.getSelectedWeek();
                i++;
            }
            SimpleUtils.assertOnFail("The week navigation in Region View should get updated to be " +
                            "5 weeks based on schedule planning window settings for Compliance, but the actual weeks are: " + i,
                    i==5, false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }



    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify analytics table on Compliance in BU View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAnalyticsTableOnComplianceInBUViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            String regionName = selectedUpperFields.get(Region);
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            String buName = selectedUpperFields.get(BusinessUnit);
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);

            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            compliancePage.clickOnComplianceConsoleMenu();
            SimpleUtils.assertOnFail("Compliance page not loaded successfully", compliancePage.isCompliancePageLoaded(), false);

            // Validate the field names in analytics table
            compliancePage.verifyFieldNamesInAnalyticsTable(Region);

            // Validate the field columns can be ordered
            compliancePage.verifySortByColForLocationsInDMView(1);
            compliancePage.verifySortByColForLocationsInDMView(2);
            compliancePage.verifySortByColForLocationsInDMView(3);
            compliancePage.verifySortByColForLocationsInDMView(4);
            compliancePage.verifySortByColForLocationsInDMView(5);
            compliancePage.verifySortByColForLocationsInDMView(6);
            compliancePage.verifySortByColForLocationsInDMView(7);
            compliancePage.verifySortByColForLocationsInDMView(8);

            // Validate the data of analytics table for past week.
            compliancePage.navigateToPreviousWeek();
            SimpleUtils.assertOnFail("Compliance page analytics table not loaded for past week successfully",compliancePage.isComplianceUpperFieldView(), false);
            List<String> dataInBUViewForPast = compliancePage.getDataFromComplianceTableForGivenLocationInDMView(regionName);
            String totalExtraHoursInBUView = dataInBUViewForPast.get(0);
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            String totalHrsInRegionViewForPast = compliancePage.getTheTotalViolationHrsFromSmartCard().split(" ")[0];
            SimpleUtils.report("Total Extra Hours In BU View for past week is "+totalExtraHoursInBUView);
            SimpleUtils.report("Total Extra Hours In Region View for past week is "+totalHrsInRegionViewForPast);
            SimpleUtils.assertOnFail("Compliance Page: Analytics table doesn't match the past week's data",
                    totalHrsInRegionViewForPast.equals(String.valueOf(Math.round(Float.parseFloat(totalExtraHoursInBUView)))), false);

            // Validate the data of analytics table for current week.
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);
            compliancePage.navigateToNextWeek();
            SimpleUtils.assertOnFail("Compliance page analytics table not loaded for current week successfully",
                    compliancePage.isComplianceUpperFieldView(), false);
            List<String> dataInDMForCurrent = compliancePage.getDataFromComplianceTableForGivenLocationInDMView(regionName);
            String totalExtraHoursInBUViewForCurrent = dataInDMForCurrent.get(0);

            dashboardPage.navigateToDashboard();
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            List<String> dataInRegionForCurrent  = liquidDashboardPage.getDataOnComplianceViolationWidget();
            String totalHrsInRegionForCurrent = dataInRegionForCurrent.get(0);

            SimpleUtils.report("Total Extra Hours In BU View for current week is " + totalExtraHoursInBUViewForCurrent);
            SimpleUtils.report("Total Extra Hours In Region View for current week is " + totalHrsInRegionForCurrent);
            SimpleUtils.assertOnFail("Compliance Page: Analytics table doesn't match the current week's data",
                    totalHrsInRegionForCurrent.equals(String.valueOf(Math.round(Float.parseFloat((totalExtraHoursInBUViewForCurrent))))), false);

            // Validate the data of analytics table for future week
            compliancePage.clickOnComplianceConsoleMenu();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);
            compliancePage.navigateToNextWeek();
            SimpleUtils.assertOnFail("Compliance page analytics table not loaded for future week successfully",compliancePage.isComplianceUpperFieldView(), false);
            List<String> dataInBUForFuture = compliancePage.getDataFromComplianceTableForGivenLocationInDMView(regionName);
            String totalExtraHoursInBUViewForFuture = dataInBUForFuture.get(0);
            SimpleUtils.report("Total Extra Hours In DM View for future week is " + totalExtraHoursInBUViewForFuture);
            SimpleUtils.assertOnFail("Compliance Page: Analytics table doesn't match the future week's data",
                    totalExtraHoursInBUViewForFuture.equals("0"), false);


            // Validate Late Schedule is Yes or No
            compliancePage.navigateToPreviousWeek();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            SimpleUtils.assertOnFail("Scheduling policy page not loaded successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
            Thread.sleep(2000);
            controlsNewUIPage.updateDaysInAdvancePublishSchedulesInSchedulingPolicies("7");

            compliancePage.clickOnComplianceConsoleMenu();
            List<String>  dataCurrent = compliancePage.getDataFromComplianceTableForGivenLocationInDMView(regionName);
            String lateScheduleYes = dataCurrent.get(dataCurrent.size()-1);

            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            List<String> upperFieldNames = compliancePage.getAllUpperFieldNamesOnAnalyticsTable();
            List<String> schedulePublishedOnTime = new ArrayList<>();
            for (String upperFieldName: upperFieldNames){
                dataCurrent = compliancePage.getDataFromComplianceTableForGivenLocationInDMView(upperFieldName);
                schedulePublishedOnTime.add(dataCurrent.get(dataCurrent.size()-1));
            }

            if (lateScheduleYes.equals("Yes"))
                SimpleUtils.assertOnFail("Compliance Page: Late Schedule is not Yes", !schedulePublishedOnTime.contains("No"), false);
            else
                SimpleUtils.assertOnFail("Compliance Page: Late Schedule is not contain No", schedulePublishedOnTime.contains("No"),false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify analytics table on Compliance in Region View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAnalyticsTableOnComplianceInRegionViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String regionName = upperFields2[upperFields2.length-2].trim();
            String districtName = upperFields2[upperFields2.length-1].trim();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);

            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            compliancePage.clickOnComplianceConsoleMenu();
            SimpleUtils.assertOnFail("Compliance page not loaded successfully",
                    compliancePage.isCompliancePageLoaded(), false);

            // Validate the field names in analytics table
            compliancePage.verifyFieldNamesInAnalyticsTable(District);

            // Validate the field columns can be ordered
            compliancePage.verifySortByColForLocationsInDMView(1);
            compliancePage.verifySortByColForLocationsInDMView(2);
            compliancePage.verifySortByColForLocationsInDMView(3);
            compliancePage.verifySortByColForLocationsInDMView(4);
            compliancePage.verifySortByColForLocationsInDMView(5);
            compliancePage.verifySortByColForLocationsInDMView(6);
            compliancePage.verifySortByColForLocationsInDMView(7);
            compliancePage.verifySortByColForLocationsInDMView(8);

            // Validate the data of analytics table for past week.
            compliancePage.navigateToPreviousWeek();
            SimpleUtils.assertOnFail("Compliance page analytics table not loaded for past week successfully",compliancePage.isComplianceUpperFieldView(), false);
            List<String> dataInRegionViewForPast = compliancePage.getDataFromComplianceTableForGivenLocationInDMView(districtName);
            String totalExtraHoursInRegionView = dataInRegionViewForPast.get(0);
            locationSelectorPage.changeUpperFieldDirect(District, districtName);
            String totalHrsInDistrictViewForPast = compliancePage.getTheTotalViolationHrsFromSmartCard().split(" ")[0];
            SimpleUtils.report("Total Extra Hours In Region View for past week is "+totalExtraHoursInRegionView);
            SimpleUtils.report("Total Extra Hours In District View for past week is "+totalHrsInDistrictViewForPast);
//            SimpleUtils.assertOnFail("Compliance Page: Analytics table doesn't match the past week's data",                           //Blocked by https://legiontech.atlassian.net/browse/SCH-4937
//                    totalHrsInDistrictViewForPast.equals
//                            (String.valueOf(Math.round(Float.parseFloat(totalExtraHoursInRegionView.replace(",",""))))), false);

            // Validate the data of analytics table for current week.
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            compliancePage.navigateToNextWeek();
            SimpleUtils.assertOnFail("Compliance page analytics table not loaded for current week successfully",
                    compliancePage.isComplianceUpperFieldView(), false);
            List<String> dataInRegionForCurrent = compliancePage.getDataFromComplianceTableForGivenLocationInDMView(districtName);
            String totalExtraHoursInRegionViewForCurrent = dataInRegionForCurrent.get(0);

            dashboardPage.navigateToDashboard();
            locationSelectorPage.changeUpperFieldDirect(District, districtName);
            List<String> dataInDistrictForCurrent  = liquidDashboardPage.getDataOnComplianceViolationWidget();
            String totalHrsInDistrictForCurrent = dataInDistrictForCurrent.get(0);

            SimpleUtils.report("Total Extra Hours In BU View for current week is " + totalExtraHoursInRegionViewForCurrent);
            SimpleUtils.report("Total Extra Hours In Region View for current week is " + totalHrsInDistrictForCurrent);
            SimpleUtils.assertOnFail("Compliance Page: Analytics table doesn't match the current week's data",
                    totalHrsInDistrictForCurrent.equals(String.valueOf(Math.round(Float.parseFloat((totalExtraHoursInRegionViewForCurrent))))), false);

            // Validate the data of analytics table for future week
            compliancePage.clickOnComplianceConsoleMenu();
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            compliancePage.navigateToNextWeek();
            SimpleUtils.assertOnFail("Compliance page analytics table not loaded for future week successfully",
                    compliancePage.isComplianceUpperFieldView(), false);
            List<String> dataInRegionForFuture = compliancePage.getDataFromComplianceTableForGivenLocationInDMView(districtName);
            String totalExtraHoursInRegionViewForFuture = dataInRegionForFuture.get(0);
            SimpleUtils.report("Total Extra Hours In Region View for future week is " + totalExtraHoursInRegionViewForFuture);
            SimpleUtils.assertOnFail("Compliance Page: Analytics table doesn't match the future week's data",
                    totalExtraHoursInRegionViewForFuture.equals("0"), false);


            // Validate Late Schedule is Yes or No
            compliancePage.navigateToPreviousWeek();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            SimpleUtils.assertOnFail("Scheduling policy page not loaded successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
            Thread.sleep(2000);
            controlsNewUIPage.updateDaysInAdvancePublishSchedulesInSchedulingPolicies("7");

            compliancePage.clickOnComplianceConsoleMenu();
            List<String>  dataCurrent = compliancePage.getDataFromComplianceTableForGivenLocationInDMView(districtName);
            String lateScheduleYes = dataCurrent.get(dataCurrent.size()-1);

            locationSelectorPage.changeUpperFieldDirect(District, districtName);
            List<String> upperFieldNames = compliancePage.getAllUpperFieldNamesOnAnalyticsTable();
            List<String> schedulePublishedOnTime = new ArrayList<>();
            for (String upperFieldName: upperFieldNames){
                dataCurrent = compliancePage.getDataFromComplianceTableForGivenLocationInDMView(upperFieldName);
                schedulePublishedOnTime.add(dataCurrent.get(dataCurrent.size()-1));
            }

            if (lateScheduleYes.equals("Yes"))
                SimpleUtils.assertOnFail("Compliance Page: Late Schedule is not Yes", !schedulePublishedOnTime.contains("No"), false);
            else
                SimpleUtils.assertOnFail("Compliance Page: Late Schedule is not contain No", schedulePublishedOnTime.contains("No"),false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
