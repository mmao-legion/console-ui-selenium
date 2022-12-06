package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.OpsPortaPageFactories.LaborModelPage;
import com.legion.pages.OpsPortaPageFactories.LocationsPage;
import com.legion.pages.OpsPortaPageFactories.UserManagementPage;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
import com.legion.pages.core.opusermanagement.OpsPortalUserManagementPanelPage;
import com.legion.pages.core.opusermanagement.OpsPortalWorkRolesPage;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class WorkRoleTest extends TestBase {

    private DashboardPage dashboardPage;
    private CreateSchedulePage createSchedulePage;
    private ScheduleMainPage scheduleMainPage;
    private ScheduleShiftTablePage scheduleShiftTablePage;
    private ScheduleCommonPage scheduleCommonPage;
    private LocationsPage locationsPage;
    private NewShiftPage newShiftPage;
    private LaborModelPage laborModelPage;
    private ForecastPage forecastPage;
    private ScheduleOverviewPage scheduleOverviewPage;

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
            laborModelPage = pageFactory.createOpsPortalLaborModelPage();
            forecastPage  = pageFactory.createForecastPage();
            scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the functionality of Display Order")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyFunctionalityOfDisplayOrderAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String workRole = "AutoTest";
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            // Go to User Management page
            UserManagementPage userManagementPage = pageFactory.createOpsPortalUserManagementPage();
            userManagementPage.clickOnUserManagementTab();
            userManagementPage.goToWorkRolesTile();

            // Verify the visibility of Display Order column in Work Roles page
            OpsPortalWorkRolesPage workRolesPage = new OpsPortalWorkRolesPage();
            SimpleUtils.assertOnFail("Column `Display Order` failed to show on Work Roles page!", workRolesPage.
                    isColumnVisibleOnWorkRoles("Display Order"), false);
            workRolesPage.sortByDisplayOrder(false);
            List<Integer> displayOrders = workRolesPage.getDisplayOrders();
            List<String> workRoleNames = workRolesPage.getWorkRoleNames();
            int maximumOrder = displayOrders.get(0);
            // Verify the visibility of Display Order input in specific work role configure page
            workRolesPage.clickEditButton();
            workRolesPage.goToWorkRoleDetails();
            SimpleUtils.assertOnFail("Input `Display Order` failed to show on Work Role Detail page!", workRolesPage.
                    isDisplayOrderInputShowInDetailPage(), false);
            // Verify the minimum value of Display Order
            SimpleUtils.assertOnFail("0 should not be allowed!", !workRolesPage.isSpecificValueOfDisplayOrderAllowed("0"), false);
            SimpleUtils.assertOnFail("1 should be allowed!", workRolesPage.isSpecificValueOfDisplayOrderAllowed("1"), false);
            // Verify the maximum value of Display Order
            SimpleUtils.assertOnFail("Maximum value should be allowed!", workRolesPage.
                    isSpecificValueOfDisplayOrderAllowed(String.valueOf(maximumOrder)), false);
            SimpleUtils.assertOnFail("Maximum value plus one should not be allowed!", !workRolesPage.
                    isSpecificValueOfDisplayOrderAllowed(String.valueOf(maximumOrder + 1)), false);
            // Verify decimal is not allowed
            SimpleUtils.assertOnFail("Maximum value plus one should not be allowed!", !workRolesPage.
                    isSpecificValueOfDisplayOrderAllowed("1.1"), false);
            // Verify characters are not allowed
            SimpleUtils.assertOnFail("Maximum value plus one should not be allowed!", !workRolesPage.
                    isSpecificValueOfDisplayOrderAllowed("a"), false);
            // Verify changing the Display Order of one work role
            String firstWorkName = workRoleNames.get(0);
            workRolesPage.updateDisplayOrder(maximumOrder - 1);
            workRolesPage.sortByDisplayOrder(false);
            List<Integer> currentOrder = workRolesPage.getDisplayOrders();
            List<String> currentWorkRole = workRolesPage.getWorkRoleNames();
            SimpleUtils.assertOnFail("The second work role name is incorrect!", currentWorkRole.get(1).equalsIgnoreCase(firstWorkName), false);
            SimpleUtils.assertOnFail("The display order of second work role is incorrect!", maximumOrder - 1 == currentOrder.get(1), false);
            workRolesPage.save();
            // Verify adding a new work role with the display order
            workRolesPage.addNewWorkRole();
            workRolesPage.inputWorkRoleDetail(workRole, maximumOrder + 1);
            workRolesPage.searchByWorkRole(workRole);
            currentOrder = workRolesPage.getDisplayOrders();
            SimpleUtils.assertOnFail("Display order is incorrect for new work role!", currentOrder.get(0) == maximumOrder + 1, false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the Dislay Order of the work roles in forecast and schedule are consistent with the settings in Control Center")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyWorkRoleDisplayOrderInConsoleAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            // Go to User Management page
            UserManagementPage userManagementPage = pageFactory.createOpsPortalUserManagementPage();
            userManagementPage.clickOnUserManagementTab();
            userManagementPage.goToWorkRolesTile();

            // Verify the visibility of Display Order column in Work Roles page
            OpsPortalWorkRolesPage workRolesPage = new OpsPortalWorkRolesPage();
            SimpleUtils.assertOnFail("Column `Display Order` failed to show on Work Roles page!", workRolesPage.
                    isColumnVisibleOnWorkRoles("Display Order"), false);
            workRolesPage.sortByDisplayOrder(true);
            HashMap<String, Integer> workRoleNOrders = new HashMap<>();
            workRolesPage.getWholeWorkRoleNDisplayOrderMap(workRoleNOrders);
            // Switch to console
            switchToConsoleWindow();

            goToSchedulePageScheduleTab();
            // Create schedule if it is not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            // Verify the display order when grouping by work role in week view
            SimpleUtils.assertOnFail("Work Role order is incorrect when grouping by Work Role!",
                    scheduleMainPage.verifyDisplayOrderWhenGroupingByWorkRole(workRoleNOrders), false);
            // Verify the display order on filter in week view
            scheduleMainPage.clickOnFilterBtn();
            SimpleUtils.assertOnFail("Work Role order is incorrect on filter page!",
                    scheduleMainPage.areDisplayOrderCorrectOnFilterPopup(workRoleNOrders), false);
            // Verify the display order on create new shift window in week view
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            SimpleUtils.assertOnFail("Work Role order is incorrect on Create New Shift page", newShiftPage.areWorkRoleDisplayOrderCorrect(workRoleNOrders), false);
            newShiftPage.clickOnBackButton();
            scheduleMainPage.clickOnCancelButtonOnEditMode();

            scheduleCommonPage.clickOnDayView();
            // Verify the display order when grouping by work role in day view
            SimpleUtils.assertOnFail("Work Role order is incorrect when grouping by Work Role!",
                    scheduleMainPage.verifyDisplayOrderWhenGroupingByWorkRole(workRoleNOrders), false);
            // Verify the display order on filter in day view
            scheduleMainPage.clickOnFilterBtn();
            SimpleUtils.assertOnFail("Work Role order is incorrect on filter page!",
                    scheduleMainPage.areDisplayOrderCorrectOnFilterPopup(workRoleNOrders), false);
            // Verify the display order on create new shift window in day view
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            SimpleUtils.assertOnFail("Work Role order is incorrect on Create New Shift page", newShiftPage.areWorkRoleDisplayOrderCorrect(workRoleNOrders), false);
            newShiftPage.clickOnBackButton();
            scheduleMainPage.clickOnCancelButtonOnEditMode();
            scheduleCommonPage.clickOnWeekView();

            // Go to Forecast page
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Forecast.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Forecast' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Forecast.getValue()), false);
            // Verify the display order on labor forecast work role dropdown list in week view
            ForecastPage forecastPage = pageFactory.createForecastPage();
            forecastPage.clickOnLabor();
            SimpleUtils.assertOnFail("Work role display order is incorrect on Forecast week view page!",
                    forecastPage.areWorkRoleDisplayOrderCorrectOnLaborForecast(workRoleNOrders), false);
            forecastPage.clickOnDayView();
            // Verify the display order on labor forecast work role dropdown list in day view
            SimpleUtils.assertOnFail("Work role display order is incorrect on Forecast week view page!",
                    forecastPage.areWorkRoleDisplayOrderCorrectOnLaborForecast(workRoleNOrders), false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the work roles removed from labor model should not be copied")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheRemovedWorkRoleShouldNotBeCopiedAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            goToSchedulePageScheduleTab();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            String firstWeekInfo = scheduleCommonPage.getActiveWeekText();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Unassgined");
            scheduleMainPage.saveSchedule();
            createSchedulePage.publishActiveSchedule();

            // Verify creating the schedule with 3 work roles
            scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            ArrayList<HashMap<String,String>> workRoles = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            List<String> currentWorkRoleNames = new ArrayList<>();
            for (HashMap<String, String> role : workRoles) {
                currentWorkRoleNames.add(role.get("optionName"));
            }

            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.searchLocation(location);               ;
            SimpleUtils.assertOnFail("Locations not searched out Successfully!",  locationsPage.verifyUpdateLocationResult(location), false);
            locationsPage.clickOnLocationInLocationResult(location);
            locationsPage.clickOnConfigurationTabOfLocation();
            String workRole = currentWorkRoleNames.get(0);

            // Verify can disable the work role from location level labor model template
            locationsPage.clickActionsForTemplate("Labor Model", "Edit");
            laborModelPage.disableOrEnableWorkRoleInLocationLevel(workRole, false);
            locationsPage.clickOnSaveButton();

            switchToConsoleWindow();
            scheduleCommonPage.navigateToNextWeek();
            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }

            // Refresh cache
            refreshCachesAfterChangeTemplate();

            scheduleOverviewPage.loadScheduleOverview();
            forecastPage.clickForecast();
            forecastPage.clickOnLabor();
            scheduleCommonPage.navigateToNextWeek();
            forecastPage.clickOnRefreshButton();

            // Verify the disabled work role doesn't show in labor forecast
            List<String> laborWorkRoles = forecastPage.getLaborWorkRoles();
            if (laborWorkRoles.contains(workRole)) {
                SimpleUtils.fail(workRole + " is disabled, should not show in labor forecast!", false);
            }

            // Verify the disabled work role will not be created when creating the schedule
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            workRoles = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            List<String> newWorkRoleNames = new ArrayList<>();
            for (HashMap<String, String> role : workRoles) {
                newWorkRoleNames.add(role.get("optionName"));
            }
            if (newWorkRoleNames.contains(workRole)) {
                SimpleUtils.fail(workRole + " is disabled, should not be created!", false);
            }
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();

            // Verify the disabled work role should not be copied
            createSchedulePage.clickCreateScheduleBtn();
            createSchedulePage.clickNextBtnOnCreateScheduleWindow();
            createSchedulePage.selectWhichWeekToCopyFrom(firstWeekInfo.split("\n")[1].trim());
            createSchedulePage.clickOnFinishButtonOnCreateSchedulePage();
            scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            workRoles = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            List<String> copyWorkRoleNames = new ArrayList<>();
            for (HashMap<String, String> role : workRoles) {
                copyWorkRoleNames.add(role.get("optionName"));
            }
            if (copyWorkRoleNames.contains(workRole)) {
                SimpleUtils.fail(workRole + " is disabled, should be disabled!", false);
            }

            // Verify the disabled work role will not show in the work role list on create New shift window
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            SimpleUtils.assertOnFail("New create shift page is not display! ",
                    newShiftPage.checkIfNewCreateShiftPageDisplay(), false);
            List<String> workRolesOnNewShift = newShiftPage.getWorkRoleList();
            if (workRolesOnNewShift.contains(workRole)) {
                SimpleUtils.fail(workRole + " is disabled, should not be created!", false);
            }
            newShiftPage.clickCloseBtnForCreateShift();
            scheduleMainPage.clickOnCancelButtonOnEditMode();

            // Enable the work role after testing
            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.searchLocation(location);
            SimpleUtils.assertOnFail("Locations not searched out Successfully!",  locationsPage.verifyUpdateLocationResult(location), false);
            locationsPage.clickOnLocationInLocationResult(location);
            locationsPage.clickOnConfigurationTabOfLocation();

            // Verify can disable the work role from location level labor model template
            locationsPage.clickActionsForTemplate("Labor Model", "Edit");
            laborModelPage.disableOrEnableWorkRoleInLocationLevel(workRole, true);
            locationsPage.clickOnSaveButton();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
