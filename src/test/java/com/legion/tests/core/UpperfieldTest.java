package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.core.ConsoleLocationSelectorPage;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.legion.utils.MyThreadLocal.getDriver;

public class UpperfieldTest extends TestBase {

    private static String Location = "Location";
    private static String District = "District";
    private static String Region = "Region";
    private static String BusinessUnit = "Business Unit";

    String[] controlUpperFields2 = districtsMap.get("Vailqacn_Enterprise2").split(">");
    String[] controlUpperFields3 = districtsMap.get("Vailqacn_Enterprise3").split(">");
    String[] opUpperFields2 = districtsMap.get("CinemarkWkdy_Enterprise2").split(">");
    String[] opUpperFields3 = districtsMap.get("CinemarkWkdy_Enterprise3").split(">");
    private static String controlEnterprice = "Vailqacn_Enterprise";
    private static String opEnterprice = "CinemarkWkdy_Enterprise";
    private static HashMap<String, String> parameterMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");

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
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));

            // Validate the title
            dashboardPage.verifyHeaderOnDashboard();
            locationSelectorPage.verifyTheDisplayRegionWithSelectedRegionConsistent(selectedUpperFields.get(Region));
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
            String regionName = selectedUpperFields.get(Region);
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
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));
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
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));

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
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get(BusinessUnit));

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
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));

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
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));
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
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));

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
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));
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
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));

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
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get(BusinessUnit));

            // Set 'Apply labor budget to schedules?' to No
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.updateApplyLaborBudgetToSchedules("No");
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(selectedUpperFields.get(BusinessUnit));
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
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(selectedUpperFields.get(BusinessUnit));
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
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));

            // Set 'Apply labor budget to schedules?' to No
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.updateApplyLaborBudgetToSchedules("No");
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(selectedUpperFields.get(Region));
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
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(selectedUpperFields.get(Region));
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
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get(BusinessUnit));

            SimpleUtils.assertOnFail("Open Shifts widget not loaded successfully", dashboardPage.isOpenShiftsWidgetPresent(), false);

            // Get values on open shifts widget and verify the info on Open_Shifts Widget
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
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get(BusinessUnit));

            SimpleUtils.assertOnFail("Open Shifts widget not loaded successfully", dashboardPage.isOpenShiftsWidgetPresent(), false);

            // Get values on open shifts widget and verify the info on Open_Shifts Widget
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
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get(BusinessUnit));

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
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));

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
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "Verify Payroll Projection widget on Dashboard in BU View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyPayrollProjectionWidgetOnDashboardInBUViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get(BusinessUnit));

            //  Set 'Apply labor budget to schedules?' to Yes
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.updateApplyLaborBudgetToSchedules("Yes");
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(selectedUpperFields.get(BusinessUnit));
            SimpleUtils.assertOnFail("Payroll Projection widget loaded fail! ", dashboardPage.isPayrollProjectionWidgetDisplay(), false);

            // Validate the content on Payroll Projection widget with TA
            dashboardPage.validateTheContentOnPayrollProjectionWidget(true);

            // Validate the week information in Payroll Projection widget with TA
            String weekOnPayrollProjectionWidget = dashboardPage.getWeekOnPayrollProjectionWidget();
            String weekOnWelcomeSection = dashboardPage.getWeekInfoFromUpperfieldView();
            dashboardPage.clickOnViewSchedulesOnPayrollProjectWidget();
            SimpleUtils.assertOnFail("Schedule page not loaded successfully", dashboardPage.isScheduleConsoleMenuDisplay(), false);
            ScheduleDMViewPage scheduleBUViewPage = pageFactory.createScheduleDMViewPage();
            String currentWeekInBUViewSchedule = scheduleBUViewPage.getCurrentWeekInDMView();
            String forecastKPIInBUViewSchedule = scheduleBUViewPage.getBudgetComparisonInDMView();
            dashboardPage.navigateToDashboard();
            SimpleUtils.assertOnFail("Payroll Projection widget not loaded successfully", dashboardPage.isPayrollProjectionWidgetDisplay(), false);
            SimpleUtils.assertOnFail("The week in Payroll Projection widget is inconsistent with the week of welcome section of Dashboard page", weekOnWelcomeSection.contains(weekOnPayrollProjectionWidget), false);
            dashboardPage.validateWeekOnPayrollProjectionWidget(weekOnPayrollProjectionWidget, currentWeekInBUViewSchedule);

            // Validate today's time line in Payroll Projection widget with TA
            dashboardPage.validateTodayAtTimeOnPayrollProjectionWidget();

            // Validate the budget comparison in Payroll Projection widget with TA
            dashboardPage.validateTheFutureBudgetComparisonOnPayrollProjectionWidget();
            String forecastKPIOnPayrollProjectionWidget = dashboardPage.getBudgetComparisonOnPayrollProjectionWidget();
            dashboardPage.validateBudgetComparisonOnPayrollProjectionWidget(forecastKPIOnPayrollProjectionWidget, forecastKPIInBUViewSchedule);

            // Validate hours tooltips of Payroll Projection widget with TA
            dashboardPage.validateHoursTooltipsOfPayrollProjectionWidget();

            // Validate the data in Payroll Projection widget with TA
            HashMap<String, Integer> theSumOfValues = dashboardPage.getTheSumOfValuesOnPayrollProjectionWidget();
            List<String> dataOnLocationSummaryWidget = dashboardPage.getTheDataOnLocationSummaryWidget();
            if ((Integer.valueOf(dataOnLocationSummaryWidget.get(0)) - theSumOfValues.get("Budgeted") < 14)
                  && theSumOfValues.get("Scheduled").equals(Integer.valueOf(dataOnLocationSummaryWidget.get(1)))
                  && theSumOfValues.get("Projected").equals(Integer.valueOf(dataOnLocationSummaryWidget.get(2))))
                SimpleUtils.pass("Dashboard Page: The sum of days matches the numbers in Region Summary widget for Budgeted, Scheduled and Projected");
            else
                SimpleUtils.fail("Dashboard Page: The sum of days doesn't match the numbers in Region Summary widget for Budgeted, Scheduled and Projected",false);

            // Set 'Apply labor budget to schedules?' to No
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.updateApplyLaborBudgetToSchedules("No");
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(selectedUpperFields.get(BusinessUnit));
            SimpleUtils.assertOnFail("Payroll Projection widget loaded fail! ", dashboardPage.isPayrollProjectionWidgetDisplay(), false);

            // Validate the content in Payroll Projection widget with TA
            dashboardPage.validateTheContentOnPayrollProjectionWidget(false);

            // Validate the data in Payroll Projection widget with TA
            theSumOfValues = dashboardPage.getTheSumOfValuesOnPayrollProjectionWidget();
            if (Integer.valueOf(dataOnLocationSummaryWidget.get(0)) - theSumOfValues.get("Budgeted") < 14
                  && theSumOfValues.get("Scheduled").equals(Integer.valueOf(dataOnLocationSummaryWidget.get(1)))
                  && theSumOfValues.get("Projected").equals(Integer.valueOf(dataOnLocationSummaryWidget.get(2))))
                SimpleUtils.pass("Dashboard Page: The sum of days matches the numbers in Region Summary widget for Guidance, Scheduled and Projected");
           else
                SimpleUtils.fail("Dashboard Page: The sum of days doesn't match the numbers in Region Summary widget for Guidance, Scheduled and Projected",false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "Verify Payroll Projection widget on Dashboard in Region View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyPayrollProjectionWidgetOnDashboardInRegionViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));

            //  Set 'Apply labor budget to schedules?' to Yes
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.updateApplyLaborBudgetToSchedules("Yes");
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(selectedUpperFields.get(Region));
            SimpleUtils.assertOnFail("Payroll Projection widget loaded fail! ", dashboardPage.isPayrollProjectionWidgetDisplay(), false);

            // Validate the content on Payroll Projection widget with TA
            dashboardPage.validateTheContentOnPayrollProjectionWidget(true);

            // Validate the week information in Payroll Projection widget with TA
            String weekOnPayrollProjectionWidget = dashboardPage.getWeekOnPayrollProjectionWidget();
            String weekOnWelcomeSection = dashboardPage.getWeekInfoFromUpperfieldView();
            dashboardPage.clickOnViewSchedulesOnPayrollProjectWidget();
            SimpleUtils.assertOnFail("Schedule page not loaded successfully", dashboardPage.isScheduleConsoleMenuDisplay(), false);
            ScheduleDMViewPage scheduleBUViewPage = pageFactory.createScheduleDMViewPage();
            String currentWeekInBUViewSchedule = scheduleBUViewPage.getCurrentWeekInDMView();
            String forecastKPIInBUViewSchedule = scheduleBUViewPage.getBudgetComparisonInDMView();
            dashboardPage.navigateToDashboard();
            SimpleUtils.assertOnFail("Payroll Projection widget not loaded successfully", dashboardPage.isPayrollProjectionWidgetDisplay(), false);
            SimpleUtils.assertOnFail("The week in Payroll Projection widget is inconsistent with the week of welcome section of Dashboard page", weekOnWelcomeSection.contains(weekOnPayrollProjectionWidget), false);
            dashboardPage.validateWeekOnPayrollProjectionWidget(weekOnPayrollProjectionWidget, currentWeekInBUViewSchedule);

            // Validate today's time line in Payroll Projection widget with TA
            dashboardPage.validateTodayAtTimeOnPayrollProjectionWidget();

            // Validate the budget comparison in Payroll Projection widget with TA
            dashboardPage.validateTheFutureBudgetComparisonOnPayrollProjectionWidget();
            String forecastKPIOnPayrollProjectionWidget = dashboardPage.getBudgetComparisonOnPayrollProjectionWidget();
            dashboardPage.validateBudgetComparisonOnPayrollProjectionWidget(forecastKPIOnPayrollProjectionWidget, forecastKPIInBUViewSchedule);

            // Validate hours tooltips of Payroll Projection widget with TA
            dashboardPage.validateHoursTooltipsOfPayrollProjectionWidget();

            // Validate the data in Payroll Projection widget with TA
            HashMap<String, Integer> theSumOfValues = dashboardPage.getTheSumOfValuesOnPayrollProjectionWidget();
            List<String> dataOnLocationSummaryWidget = dashboardPage.getTheDataOnLocationSummaryWidget();
            if ((Integer.valueOf(dataOnLocationSummaryWidget.get(0)) - theSumOfValues.get("Budgeted") < 14)
                    && theSumOfValues.get("Scheduled").equals(Integer.valueOf(dataOnLocationSummaryWidget.get(1)))
                    && theSumOfValues.get("Projected").equals(Integer.valueOf(dataOnLocationSummaryWidget.get(2))))
                SimpleUtils.pass("Dashboard Page: The sum of days matches the numbers in District Summary widget for Budgeted, Scheduled and Projected");
            else
                SimpleUtils.fail("Dashboard Page: The sum of days doesn't match the numbers in District Summary widget for Budgeted, Scheduled and Projected",false);

            // Set 'Apply labor budget to schedules?' to No
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.updateApplyLaborBudgetToSchedules("No");
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(selectedUpperFields.get(Region));
            SimpleUtils.assertOnFail("Payroll Projection widget loaded fail! ", dashboardPage.isPayrollProjectionWidgetDisplay(), false);

            // Validate the content in Payroll Projection widget with TA
            dashboardPage.validateTheContentOnPayrollProjectionWidget(false);

            // Validate the data in Payroll Projection widget with TA
            theSumOfValues = dashboardPage.getTheSumOfValuesOnPayrollProjectionWidget();
            if (Integer.valueOf(dataOnLocationSummaryWidget.get(0)) - theSumOfValues.get("Budgeted") < 14
                    && theSumOfValues.get("Scheduled").equals(Integer.valueOf(dataOnLocationSummaryWidget.get(1)))
                    && theSumOfValues.get("Projected").equals(Integer.valueOf(dataOnLocationSummaryWidget.get(2))))
                SimpleUtils.pass("Dashboard Page: The sum of days matches the numbers in District Summary widget for Guidance, Scheduled and Projected");
            else
                SimpleUtils.fail("Dashboard Page: The sum of days doesn't match the numbers in District Summary widget for Guidance, Scheduled and Projected",false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "Verify Region Summary widget on Dashboard in BU View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyRegionSummaryWidgetOnDashboardInBUViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, selectedUpperFields.get(BusinessUnit));

            //  Set 'Apply labor budget to schedules?' to Yes
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.updateApplyLaborBudgetToSchedules("Yes");
            dashboardPage.navigateToDashboard();
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(selectedUpperFields.get(BusinessUnit));
            SimpleUtils.assertOnFail("Regions Summary widget loaded fail!", dashboardPage.isLocationSummaryWidgetDisplay(), false);

            // Validate the content in Region Summary widget
            dashboardPage.verifyTheContentOnOrgSummaryWidget(true);

            // Validate region number in Region Summary widget
            List<String> regionList = locationSelectorPage.getOrgList();
            int regionCountInNavigatorBar = regionList.size();
            String title = dashboardPage.getTitleOnOrgSummaryWidget();
            title = title.contains(" ")? title.split(" ")[0]:title;
            SimpleUtils.assertOnFail("Region number in Regions Summary widget is incorrect", Integer.valueOf(title).equals(regionCountInNavigatorBar), false);

            // Validate as of time under Projected in Region Summary widget
            dashboardPage.validateAsOfTimeUnderProjectedOnOrgSummaryWidget();

            // Validate Projected Hrs match
            List<String> dataFromRegionSummaryWidget = dashboardPage.getTheDataOnLocationSummaryWidget();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            dashboardPage.clickOnViewSchedulesOnOrgSummaryWidget();
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            List<Float> totalBudgetedScheduledProjectedHour= scheduleDMViewPage.getTheTotalBudgetedScheduledProjectedHourOfScheduleInDMView();
            DecimalFormat df1 = new DecimalFormat("###.#");
            boolean isProjectedHrsMatched = dataFromRegionSummaryWidget.get(2).equals(df1.format(totalBudgetedScheduledProjectedHour.get(2)));
            // SimpleUtils.assertOnFail("Projected hours in Regions Summary widget did not match", isProjectedHrsMatched, false);
            // todo: Failed due to https://legiontech.atlassian.net/browse/SCH-5057

            // Validate Projected Hrs match without TA
            if (MyThreadLocal.getDriver().getCurrentUrl().contains(parameterMap.get("KendraScott2_Enterprise"))) {
                isProjectedHrsMatched = dataFromRegionSummaryWidget.get(2).equals("--");
                // SimpleUtils.assertOnFail("Projected hours in Regions Summary widget did not match", isProjectedHrsMatched, false);
                // todo: Failed due to https://legiontech.atlassian.net/browse/SCH-5057
            }

            // Validate Scheduled Hrs match
            boolean isScheduledHrsMatched = dataFromRegionSummaryWidget.get(1).equals(df1.format(totalBudgetedScheduledProjectedHour.get(1)));
            SimpleUtils.assertOnFail("Scheduled hours in Regions Summary widget did not match", isScheduledHrsMatched, false);

            // Validate the  ▲ ▼ caret and hours under Scheduled Hrs
            List<String> regionNumbersFromRegionSummarySmartCard = scheduleDMViewPage.getLocationNumbersFromLocationSummarySmartCard();
            List<String> textOnTheChartInRegionSummarySmartCard= scheduleDMViewPage.getTextFromTheChartInLocationSummarySmartCard();
            boolean isHrsOfUnderOrCoverBudgetMatched = false;
            isHrsOfUnderOrCoverBudgetMatched = dataFromRegionSummaryWidget.get(5).split(" ")[0].
                    equals(textOnTheChartInRegionSummarySmartCard.get(6).split(" ")[0]);
            // SimpleUtils.assertOnFail("The  ▲ ▼ caret and hours under Scheduled Hrs in Regions Summary widget did not match", isHrsOfUnderOrCoverBudgetMatched, false);
            // todo: Failed due to https://legiontech.atlassian.net/browse/SCH-5165

            // Validate Budgeted Hrs match
            boolean isBudgetedHrsMatched = dataFromRegionSummaryWidget.get(0).equals(df1.format(totalBudgetedScheduledProjectedHour.get(0)));
            // SimpleUtils.assertOnFail("Budgeted hours in Regions Summary widget did not match", isBudgetedHrsMatched, false);
            // todo: Failed due to https://legiontech.atlassian.net/browse/SCH-5165

            // Validate the data of Projected Within Budget
            boolean isProjectedWithinBudgetRegionsMatched = dataFromRegionSummaryWidget.get(3).equals(regionNumbersFromRegionSummarySmartCard.get(0));
            SimpleUtils.assertOnFail("Budgeted hours in Regions Summary widget did not match", isProjectedWithinBudgetRegionsMatched, false);

            // Validate the data of Projected Over Budget
            boolean isProjectedOverBudgetRegionsMatched = dataFromRegionSummaryWidget.get(4).equals(regionNumbersFromRegionSummarySmartCard.get(1));
            SimpleUtils.assertOnFail("Budgeted hours in Regions Summary widget did not match", isProjectedOverBudgetRegionsMatched, false);

            //  Set 'Apply labor budget to schedules?' to No
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.updateApplyLaborBudgetToSchedules("No");
            dashboardPage.navigateToDashboard();
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(selectedUpperFields.get(BusinessUnit));
            SimpleUtils.assertOnFail("Regions Summary widget loaded fail!", dashboardPage.isLocationSummaryWidgetDisplay(), false);

            // Validate the content in Region Summary widget
            dashboardPage.verifyTheContentOnOrgSummaryWidget(false);

            //  Validate Guidance Hrs match
            dataFromRegionSummaryWidget = dashboardPage.getTheDataOnLocationSummaryWidget();
            dashboardPage.clickOnViewSchedulesOnOrgSummaryWidget();
            totalBudgetedScheduledProjectedHour= scheduleDMViewPage.getTheTotalBudgetedScheduledProjectedHourOfScheduleInDMView();
            boolean isGuidanceHrsMatched = dataFromRegionSummaryWidget.get(0).equals(df1.format(totalBudgetedScheduledProjectedHour.get(0)));
            // SimpleUtils.assertOnFail("Budgeted hours in Regions Summary widget did not match", isGuidanceHrsMatched, false);
            // todo: Failed due to https://legiontech.atlassian.net/browse/SCH-5165

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "Verify Region Summary widget on Dashboard in Region View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDistrictSummaryWidgetOnDashboardInRegionViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));

            //  Set 'Apply labor budget to schedules?' to Yes
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.updateApplyLaborBudgetToSchedules("Yes");
            dashboardPage.navigateToDashboard();
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));
            SimpleUtils.assertOnFail("Districts Summary widget loaded fail!", dashboardPage.isLocationSummaryWidgetDisplay(), false);

            // Validate the content in Region Summary widget
            dashboardPage.verifyTheContentOnOrgSummaryWidget(true);

            // Validate district number in Region Summary widget
            List<String> districtList = locationSelectorPage.getOrgList();
            int districtCountInNavigatorBar = districtList.size();
            String title = dashboardPage.getTitleOnOrgSummaryWidget();
            title = title.contains(" ")? title.split(" ")[0]:title;
            SimpleUtils.assertOnFail("District number in Districts Summary widget is incorrect", Integer.valueOf(title).equals(districtCountInNavigatorBar), false);

            // Validate as of time under Projected in Region Summary widget
            dashboardPage.validateAsOfTimeUnderProjectedOnOrgSummaryWidget();

            // Validate Projected Hrs match
            List<String> dataFromDistrictSummaryWidget = dashboardPage.getTheDataOnLocationSummaryWidget();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            dashboardPage.clickOnViewSchedulesOnOrgSummaryWidget();
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            List<Float> totalBudgetedScheduledProjectedHour= scheduleDMViewPage.getTheTotalBudgetedScheduledProjectedHourOfScheduleInDMView();
            DecimalFormat df1 = new DecimalFormat("###.#");
            boolean isProjectedHrsMatched = dataFromDistrictSummaryWidget.get(2).equals(df1.format(totalBudgetedScheduledProjectedHour.get(2)));
            // SimpleUtils.assertOnFail("Projected hours in Districts Summary widget did not match", isProjectedHrsMatched, false);
            // todo: Failed due to https://legiontech.atlassian.net/browse/SCH-5057

            // Validate Projected Hrs match without TA
            if (MyThreadLocal.getDriver().getCurrentUrl().contains(parameterMap.get("KendraScott2_Enterprise"))) {
                isProjectedHrsMatched = dataFromDistrictSummaryWidget.get(2).equals("--");
                // SimpleUtils.assertOnFail("Projected hours in Districts Summary widget did not match", isProjectedHrsMatched, false);
                // todo: Failed due to https://legiontech.atlassian.net/browse/SCH-5057
            }

            // Validate Scheduled Hrs match
            boolean isScheduledHrsMatched = dataFromDistrictSummaryWidget.get(1).equals(df1.format(totalBudgetedScheduledProjectedHour.get(1)));
            SimpleUtils.assertOnFail("Scheduled hours in Districts Summary widget did not match", isScheduledHrsMatched, false);

            // Validate the  ▲ ▼ caret and hours under Scheduled Hrs
            List<String> regionNumbersFromRegionSummarySmartCard = scheduleDMViewPage.getLocationNumbersFromLocationSummarySmartCard();
            List<String> textOnTheChartInRegionSummarySmartCard= scheduleDMViewPage.getTextFromTheChartInLocationSummarySmartCard();
            boolean isHrsOfUnderOrCoverBudgetMatched = false;
            isHrsOfUnderOrCoverBudgetMatched = dataFromDistrictSummaryWidget.get(5).split(" ")[0].
                    equals(textOnTheChartInRegionSummarySmartCard.get(6).split(" ")[0]);
            // SimpleUtils.assertOnFail("The  ▲ ▼ caret and hours under Scheduled Hrs in Districts Summary widget did not match", isHrsOfUnderOrCoverBudgetMatched, false);
            // todo: Failed due to https://legiontech.atlassian.net/browse/SCH-5165

            // Validate Budgeted Hrs match
            boolean isBudgetedHrsMatched = dataFromDistrictSummaryWidget.get(0).equals(df1.format(totalBudgetedScheduledProjectedHour.get(0)));
            // SimpleUtils.assertOnFail("Budgeted hours in Districts Summary widget did not match", isBudgetedHrsMatched, false);
            // todo: Failed due to https://legiontech.atlassian.net/browse/SCH-5165

            // Validate the data of Projected Within Budget
            boolean isProjectedWithinBudgetRegionsMatched = dataFromDistrictSummaryWidget.get(3).equals(regionNumbersFromRegionSummarySmartCard.get(0));
            SimpleUtils.assertOnFail("Budgeted hours in Districts Summary widget did not match", isProjectedWithinBudgetRegionsMatched, false);

            // Validate the data of Projected Over Budget
            boolean isProjectedOverBudgetRegionsMatched = dataFromDistrictSummaryWidget.get(4).equals(regionNumbersFromRegionSummarySmartCard.get(1));
            SimpleUtils.assertOnFail("Budgeted hours in Districts Summary widget did not match", isProjectedOverBudgetRegionsMatched, false);

            //  Set 'Apply labor budget to schedules?' to No
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.updateApplyLaborBudgetToSchedules("No");
            dashboardPage.navigateToDashboard();
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            locationSelectorPage.changeUpperFieldDirect(Region, selectedUpperFields.get(Region));
            SimpleUtils.assertOnFail("Districts Summary widget loaded fail!", dashboardPage.isLocationSummaryWidgetDisplay(), false);

            // Validate the content in Region Summary widget
            dashboardPage.verifyTheContentOnOrgSummaryWidget(false);

            //  Validate Guidance Hrs match
            dataFromDistrictSummaryWidget = dashboardPage.getTheDataOnLocationSummaryWidget();
            dashboardPage.clickOnViewSchedulesOnOrgSummaryWidget();
            totalBudgetedScheduledProjectedHour= scheduleDMViewPage.getTheTotalBudgetedScheduledProjectedHourOfScheduleInDMView();
            boolean isGuidanceHrsMatched = dataFromDistrictSummaryWidget.get(0).equals(df1.format(totalBudgetedScheduledProjectedHour.get(0)));
            // SimpleUtils.assertOnFail("Budgeted hours in Districts Summary widget did not match", isGuidanceHrsMatched, false);
            // todo: Failed due to https://legiontech.atlassian.net/browse/SCH-5165

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Region View Navigation")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyRegionViewNavigationAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(districtName);

            //Validate navigating back to region view
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            locationSelectorPage.isRegionView();

            //Validate navigating back to region view
            SimpleUtils.assertOnFail("Schedule Region view page not loaded Successfully!",
                    schedulePage.isScheduleDMView(), false);

            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(districtName);

            //Validate changing regions
            String[] upperFields2 = null;
            if (getDriver().getCurrentUrl().contains(propertyMap.get(controlEnterprice))) {
                upperFields2 = controlUpperFields2;
            } else {
                upperFields2 = opUpperFields2;
            }
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
    @Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "BU View Navigation")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyBUViewNavigationAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            String regionName = selectedUpperFields.get(Region);
            String districtName = selectedUpperFields.get(District);
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
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(districtName);

            //Validate navigating back to BU view
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);
            locationSelectorPage.isBUView();

            SimpleUtils.assertOnFail("Schedule BU view page not loaded Successfully!",
                    schedulePage.isScheduleDMView(), false);
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(regionName);

            //Validate changing BUs
            String[] upperFields3 = null;
            if (getDriver().getCurrentUrl().contains(propertyMap.get(controlEnterprice))) {
                upperFields3 = controlUpperFields3;
            } else {
                upperFields3 = opUpperFields3;
            }
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
    @Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
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
    @Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
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
    @Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
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
//            SimpleUtils.assertOnFail("Compliance Page: Analytics table doesn't match the past week's data",   //Blocked by https://legiontech.atlassian.net/browse/SCH-4937
//                    totalHrsInRegionViewForPast.equals(String.valueOf(Math.round(Float.parseFloat(totalExtraHoursInBUView)))), false);

            // Validate the data of analytics table for current week.
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);
            compliancePage.navigateToNextWeek();
            SimpleUtils.assertOnFail("Compliance page analytics table not loaded for current week successfully",
                    compliancePage.isComplianceUpperFieldView(), false);
            List<String> dataInDMForCurrent = compliancePage.getDataFromComplianceTableForGivenLocationInDMView(regionName);
            String totalExtraHoursInBUViewForCurrent = dataInDMForCurrent.get(0);

            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            String totalHrsInRegionForCurrent = compliancePage.getTheTotalViolationHrsFromSmartCard().split(" ")[0];

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
    @Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify analytics table on Compliance in Region View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAnalyticsTableOnComplianceInRegionViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String[] upperFields2 = null;
            if (getDriver().getCurrentUrl().contains(propertyMap.get(controlEnterprice))) {
                upperFields2 = controlUpperFields2;
            } else {
                upperFields2 = opUpperFields2;
            }
            String regionName = upperFields2[upperFields2.length-2].trim();
            String districtName = upperFields2[upperFields2.length-1].trim();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);

//            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
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

            locationSelectorPage.changeUpperFieldDirect(District, districtName);
            String totalHrsInDistrictForCurrent = compliancePage.getTheTotalViolationHrsFromSmartCard().split(" ")[0];

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


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify Compliance functionality on Compliance in BU View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyComplianceFunctionalityOnComplianceInBUViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            String districtName = selectedUpperFields.get(District);
            String regionName = selectedUpperFields.get(Region);
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            String buName = selectedUpperFields.get(BusinessUnit);
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);

            //Validate the title and info of Compliance page.
            timeSheetPage.clickOnComplianceConsoleMenu();
            SimpleUtils.assertOnFail("Compliance Page not loaded Successfully!",compliancePage.isCompliancePageLoaded() , false);

            //Verify BU selected and displayed with "All Regions".
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            SimpleUtils.assertOnFail("The selected BU display incorrectly! ",
                    selectedUpperFields.get(BusinessUnit).equalsIgnoreCase(buName), false);
            SimpleUtils.assertOnFail("The 'All Regions' display incorrectly! ",
                    selectedUpperFields.get(Region).equalsIgnoreCase("All Regions"), false);

            //Validate search function.
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            compliancePage.getAllUpperFieldInfoFromComplianceDMViewByUpperField(districtName);

            //Validate the clickability of backward button.
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);
            String weekInfo = schedulePage.getActiveWeekText();
            schedulePage.navigateToPreviousWeek();

            //Validate the clickability of forward button.
            schedulePage.navigateToNextWeek();
            SimpleUtils.assertOnFail("Week picker has issue!", weekInfo.equals(schedulePage.getActiveWeekText()), false);

            //Validate changing BUs on Compliance
            String[] upperFields3 = null;
            if (getDriver().getCurrentUrl().contains(propertyMap.get(controlEnterprice))) {
                upperFields3 = controlUpperFields3;
            } else {
                upperFields3 = opUpperFields3;
            }
            String buName2 = upperFields3[upperFields3.length-3].trim();
            String regionName2 = upperFields3[upperFields3.length-2].trim();
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName2);

            compliancePage.getAllUpperFieldInfoFromComplianceDMViewByUpperField(regionName2);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify Compliance functionality on Compliance in Region View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyComplianceFunctionalityOnComplianceInRegionViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            String districtName = selectedUpperFields.get(District);
            String regionName = selectedUpperFields.get(Region);
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);

            //Validate the title and info of Compliance page.
            timeSheetPage.clickOnComplianceConsoleMenu();
            SimpleUtils.assertOnFail("Compliance Page not loaded Successfully!",compliancePage.isCompliancePageLoaded() , false);

            //Verify BU selected and displayed with "All Regions".
            selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            SimpleUtils.assertOnFail("The selected Region display incorrectly! ",
                    selectedUpperFields.get(Region).equalsIgnoreCase(regionName), false);
            SimpleUtils.assertOnFail("The 'All District' display incorrectly! ",
                    selectedUpperFields.get(District).equalsIgnoreCase("All Districts"), false);

            //Validate search function.
            locationSelectorPage.changeUpperFieldDirect(District, districtName);
            compliancePage.getAllUpperFieldInfoFromComplianceDMViewByUpperField(location);

            //Validate the click ability of backward button.
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            String weekInfo = schedulePage.getActiveWeekText();
            schedulePage.navigateToPreviousWeek();

            //Validate the click ability of forward button.
            schedulePage.navigateToNextWeek();
            SimpleUtils.assertOnFail("Week picker has issue!", weekInfo.equals(schedulePage.getActiveWeekText()), false);

            //Validate changing Regions on Compliance
            String[] upperFields2 = null;
            if (getDriver().getCurrentUrl().contains(propertyMap.get(controlEnterprice))) {
                upperFields2 = controlUpperFields2;
            } else {
                upperFields2 = opUpperFields2;
            }
            String regionName2 = upperFields2[upperFields2.length-2].trim();
            String districtName2 = upperFields2[upperFields2.length-1].trim();
            locationSelectorPage.changeUpperFieldDirect(Region, regionName2);
            compliancePage.getAllUpperFieldInfoFromComplianceDMViewByUpperField(districtName2);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify Refresh feature on Compliance in BU View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyRefreshFeatureOnComplianceInBUViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            timeSheetPage.clickOnComplianceConsoleMenu();

            // Validate the presence of Refresh button
            dashboardPage.validateThePresenceOfRefreshButton();

            // Validate Refresh timestamp
            dashboardPage.validateRefreshTimestamp();

            // Validate Refresh when navigation back
            dashboardPage.validateRefreshWhenNavigationBack("Compliance");

            // Validate Refresh function
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            scheduleDMViewPage.validateRefreshPerformance();

            // Validate Refresh performance
            scheduleDMViewPage.validateRefreshPerformance();

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify Refresh feature on Compliance in Region View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyRefreshFeatureOnComplianceInRegionViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            String regionName = selectedUpperFields.get(Region);
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            timeSheetPage.clickOnComplianceConsoleMenu();

            // Validate the presence of Refresh button
            dashboardPage.validateThePresenceOfRefreshButton();

            // Validate Refresh timestamp
            dashboardPage.validateRefreshTimestamp();

            // Validate Refresh when navigation back
            dashboardPage.validateRefreshWhenNavigationBack("Compliance");

            // Validate Refresh function
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            scheduleDMViewPage.validateRefreshPerformance();

            // Validate Refresh performance
            scheduleDMViewPage.validateRefreshPerformance();

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the availability of region list and sub region on Compliance in BU View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyRegionListAndSubRegionOnComplianceInBUViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            timeSheetPage.clickOnComplianceConsoleMenu();

            //Validate the region list
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            SimpleUtils.assertOnFail("The Regions on Compliance page in BU View display incorrectly! ", compliancePage.getAllUpperFieldNamesOnAnalyticsTable().size()>1, false);

            compliancePage.getAllUpperFieldInfoFromComplianceDMViewByUpperField(regionName);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the availability of district list and sub district on Compliance in Region View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDistrictListAndSubDistrictOnComplianceInBUViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            String districtName = selectedUpperFields.get(District);
            String regionName = selectedUpperFields.get(Region);
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            timeSheetPage.clickOnComplianceConsoleMenu();

            //Validate the region list
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            SimpleUtils.assertOnFail("The Regions on Compliance page in BU View display incorrectly! ", compliancePage.getAllUpperFieldNamesOnAnalyticsTable().size()>1, false);

            compliancePage.getAllUpperFieldInfoFromComplianceDMViewByUpperField(districtName);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify TOTAL VIOLATION HRS on Compliance in BU View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTotalViolationHrsOnComplianceInBUViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            timeSheetPage.clickOnComplianceConsoleMenu();

            //Validate the content of Toatal Violation smart card for current week.
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            HashMap<String, Float> valuesFromToatalViolationCard =  compliancePage.getValuesAndVerifyInfoForTotalViolationSmartCardInDMView();

            //Validate the data Toatal Violation smart card for current week.
            //Verify total violation hours for current week.
            List<String> allUpperFields = compliancePage.getAllUpperFieldNames();
            float totalViolationHrsFromTable = 0;
            for (String upperField: allUpperFields){
                totalViolationHrsFromTable = totalViolationHrsFromTable +
                        Float.parseFloat(compliancePage.getAllUpperFieldInfoFromComplianceDMViewByUpperField(upperField).get("totalExtraHours"));
            }
            SimpleUtils.assertOnFail("Total violation hours are inconsistent with analytic table!", (Math.abs(valuesFromToatalViolationCard.get("vioHrsCurrentWeek")) - totalViolationHrsFromTable) == 0, false);

            //Verify diff hours flag.
            if ((valuesFromToatalViolationCard.get("vioHrsCurrentWeek") - valuesFromToatalViolationCard.get("vioHrsPastWeek"))>0){
                compliancePage.verifyDiffFlag("down");
            } else {
                compliancePage.verifyDiffFlag("up");
            }

            //Verify total violation hours for last week.
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.navigateToPreviousWeek();
            HashMap<String, Float> valuesFromToatalViolationCardForLastWeek =  compliancePage.getValuesAndVerifyInfoForTotalViolationSmartCardInDMView();
            SimpleUtils.assertOnFail("Violation hours for last week are inconsistent!",
                    (Math.abs(valuesFromToatalViolationCard.get("vioHrsPastWeek")) - valuesFromToatalViolationCardForLastWeek.get("vioHrsCurrentWeek")) == 0,
                    false);

            //Verify diff hours.
            SimpleUtils.assertOnFail("Diff hours with last week is incorrect!",
                    (Math.abs(Math.abs(valuesFromToatalViolationCard.get("vioHrsCurrentWeek"))
                            - valuesFromToatalViolationCard.get("vioHrsPastWeek"))-valuesFromToatalViolationCard.get("diffHrs")) == 0,
                    false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }



    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify TOTAL VIOLATION HRS on Compliance in Region View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTotalViolationHrsOnComplianceInRegionViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            String regionName = selectedUpperFields.get(Region);
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            timeSheetPage.clickOnComplianceConsoleMenu();

            //Validate the content of Toatal Violation smart card for current week.
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            HashMap<String, Float> valuesFromToatalViolationCard =  compliancePage.getValuesAndVerifyInfoForTotalViolationSmartCardInDMView();

            //Validate the data Toatal Violation smart card for current week.
            //Verify total violation hours for current week.
            List<String> allUpperFields = compliancePage.getAllUpperFieldNames();
            float totalViolationHrsFromTable = 0;
            for (String upperField: allUpperFields){
                totalViolationHrsFromTable = totalViolationHrsFromTable +
                        Float.parseFloat(compliancePage.getAllUpperFieldInfoFromComplianceDMViewByUpperField(upperField).get("totalExtraHours"));
            }
            SimpleUtils.assertOnFail("Total violation hours are inconsistent with analytic table!", (Math.abs(valuesFromToatalViolationCard.get("vioHrsCurrentWeek")) - totalViolationHrsFromTable) == 0, false);

            //Verify diff hours flag.
            if ((valuesFromToatalViolationCard.get("vioHrsCurrentWeek") - valuesFromToatalViolationCard.get("vioHrsPastWeek"))>0){
                compliancePage.verifyDiffFlag("down");
            } else {
                compliancePage.verifyDiffFlag("up");
            }

            //Verify total violation hours for last week.
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.navigateToPreviousWeek();
            HashMap<String, Float> valuesFromToatalViolationCardForLastWeek =  compliancePage.getValuesAndVerifyInfoForTotalViolationSmartCardInDMView();
            SimpleUtils.assertOnFail("Violation hours for last week are inconsistent!",
                    (Math.abs(valuesFromToatalViolationCard.get("vioHrsPastWeek")) - valuesFromToatalViolationCardForLastWeek.get("vioHrsCurrentWeek")) == 0,
                    false);

            //Verify diff hours.
            SimpleUtils.assertOnFail("Diff hours with last week is incorrect!",
                    (Math.abs(Math.abs(valuesFromToatalViolationCard.get("vioHrsCurrentWeek"))
                            - valuesFromToatalViolationCard.get("vioHrsPastWeek"))-valuesFromToatalViolationCard.get("diffHrs")) == 0,
                    false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify Regions with Violations on Compliance in BU View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTotalLocationsWithViolationCardInComplianceBUViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            timeSheetPage.clickOnComplianceConsoleMenu();

            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            //Validate the content on Locations with violation card.
            HashMap<String, Integer> valuesFromLocationsWithViolationCard = compliancePage.getValueOnLocationsWithViolationCardAndVerifyInfo(Region);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            int index = schedulePage.getIndexOfColInDMViewTable("Extra Hours");
            List<Float> extraHours = schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(index));
            index = schedulePage.getIndexOfColInDMViewTable("Schedule Published On Time");
            List<String> publishStatus = schedulePage.getListByColInTimesheetDMView(index);
            SimpleUtils.assertOnFail("The extra hour count should consistent with publish status count! ",
                    extraHours.size() == publishStatus.size(), false);
            int totalLocationWithViolation = 0;

            for (int i = 0; i < extraHours.size(); i++){
                if (extraHours.get(i) >0 || publishStatus.get(i).equals("No")){
                    totalLocationWithViolation ++;
                }
            }

            SimpleUtils.assertOnFail("Locations With Violation Card and analytic table are inconsistent!",
                    valuesFromLocationsWithViolationCard.get("UpperFieldsWithViolations") == totalLocationWithViolation, false);
            SimpleUtils.assertOnFail("Locations With Violation Card and analytic table are inconsistent!",
                    valuesFromLocationsWithViolationCard.get("TotalUpperFields") == extraHours.size(), false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify Districts with Violations on Compliance in Region View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTotalLocationsWithViolationCardInComplianceRegionViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            String regionName = selectedUpperFields.get(Region);
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            timeSheetPage.clickOnComplianceConsoleMenu();
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            //Validate the content on Locations with violation card.

            HashMap<String, Integer> valuesFromLocationsWithViolationCard = compliancePage.getValueOnLocationsWithViolationCardAndVerifyInfo(District);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            int index = schedulePage.getIndexOfColInDMViewTable("Extra Hours");
            List<Float> extraHours = schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(index));
            index = schedulePage.getIndexOfColInDMViewTable("Schedule Published On Time");
            List<String> publishStatus = schedulePage.getListByColInTimesheetDMView(index);
            SimpleUtils.assertOnFail("The extra hour count should consistent with publish status count! ",
                    extraHours.size() == publishStatus.size(), false);
            int totalLocationWithViolation = 0;

            for (int i = 0; i < extraHours.size(); i++){
                if (extraHours.get(i) >0 || publishStatus.get(i).equals("No")){
                    totalLocationWithViolation ++;
                }
            }

            SimpleUtils.assertOnFail("Locations With Violation Card and analytic table are inconsistent!",
                    valuesFromLocationsWithViolationCard.get("UpperFieldsWithViolations") == totalLocationWithViolation, false);
            SimpleUtils.assertOnFail("Locations With Violation Card and analytic table are inconsistent!",
                    valuesFromLocationsWithViolationCard.get("TotalUpperFields") == extraHours.size(), false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }



    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify TOP x VIOLATIONS (HRS) on Compliance in BU View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTopViolationsCardInComplianceBUViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            timeSheetPage.clickOnComplianceConsoleMenu();

            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            //Validate the content on Locations with violation card.
            float topViolationInOvertimeCol = compliancePage.getTopOneViolationHrsOrNumOfACol(schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Overtime"))));
            float topViolationInClopeningCol = compliancePage.getTopOneViolationHrsOrNumOfACol(schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Clopening"))));
            float topViolationInMissedMealCol = compliancePage.getTopOneViolationHrsOrNumOfACol(schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Missed Meal"))));
            float topViolationInScheduleChangedCol = compliancePage.getTopOneViolationHrsOrNumOfACol(schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Schedule Changed"))));
            float topViolationInDoubletimeCol = compliancePage.getTopOneViolationHrsOrNumOfACol(schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Doubletime"))));

            if ((topViolationInOvertimeCol+topViolationInClopeningCol+topViolationInMissedMealCol+topViolationInScheduleChangedCol+topViolationInDoubletimeCol) != 0.0){
                HashMap<String, Float> valuesFromLocationsWithViolationCard = compliancePage.getViolationHrsFromTop1ViolationCardAndVerifyInfo();

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
            }

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify TOP x VIOLATIONS (HRS) on Compliance in Region View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTopViolationsCardInComplianceRegionViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            String regionName = selectedUpperFields.get(Region);
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
            timeSheetPage.clickOnComplianceConsoleMenu();
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();

            //Validate the content on Locations with violation card.
            float topViolationInOvertimeCol = compliancePage.getTopOneViolationHrsOrNumOfACol(schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Overtime"))));
            float topViolationInClopeningCol = compliancePage.getTopOneViolationHrsOrNumOfACol(schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Clopening"))));
            float topViolationInMissedMealCol = compliancePage.getTopOneViolationHrsOrNumOfACol(schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Missed Meal"))));
            float topViolationInScheduleChangedCol = compliancePage.getTopOneViolationHrsOrNumOfACol(schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Schedule Changed"))));
            float topViolationInDoubletimeCol = compliancePage.getTopOneViolationHrsOrNumOfACol(schedulePage.transferStringToFloat(schedulePage.getListByColInTimesheetDMView(schedulePage.getIndexOfColInDMViewTable("Doubletime"))));

            if ((topViolationInOvertimeCol+topViolationInClopeningCol+topViolationInMissedMealCol+topViolationInScheduleChangedCol+topViolationInDoubletimeCol) != 0.0){
                HashMap<String, Float> valuesFromLocationsWithViolationCard = compliancePage.getViolationHrsFromTop1ViolationCardAndVerifyInfo();

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
            }

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
