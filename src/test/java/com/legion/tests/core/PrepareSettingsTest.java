package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.core.ConsoleControlsNewUIPage;
import com.legion.pages.core.OpsPortalLocationsPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrepareSettingsTest extends TestBase {

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

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Prepare the control settings First")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void prepareSettingsInControlsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Team page, reject all the time off request
//        TeamPage teamPage = pageFactory.createConsoleTeamPage();
//        teamPage.goToTeam();
//        teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
//        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
//        teamPage.rejectAllTeamMembersTimeOffRequest(profileNewUIPage, 0);

            // Set time off policy
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsPage.gotoControlsPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);

            dashboardPage.navigateToDashboard();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            controlsPage.gotoControlsPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);

            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            SimpleUtils.assertOnFail("Scheduling policy page not loaded successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
            controlsNewUIPage.updateCanWorkerRequestTimeOff("Yes");
            controlsNewUIPage.clickOnSchedulingPoliciesTimeOffAdvanceBtn();
            controlsNewUIPage.updateShowTimeOffReasons("Yes");

            controlsNewUIPage.clickOnGlobalLocationButton();
            controlsNewUIPage.clickOnSchedulingPoliciesShiftAdvanceBtn();
            controlsNewUIPage.enableOverRideAssignmentRuleAsYes();

            controlsPage.gotoControlsPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
            SimpleUtils.assertOnFail("Scheduling collaboration page not loaded successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
            controlsNewUIPage.updateCanManagerAddAnotherLocationsEmployeeInScheduleBeforeTheEmployeeHomeLocationHasPublishedTheSchedule("Yes, anytime");

            //Set 'Automatically convert unassigned shifts to open shifts when generating the schedule?' set as Yes, all unassigned shifts
            controlsNewUIPage.clickOnScheduleCollaborationOpenShiftAdvanceBtn();
            controlsNewUIPage.updateConvertUnassignedShiftsToOpenSettingOption("Yes, all unassigned shifts");

            controlsPage.gotoControlsPage();
            controlsPage.clickGlobalSettings();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.enableOrDisableScheduleCopyRestriction("no");

            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            MyThreadLocal.setIsNeedEditingOperatingHours(true);
            createScheduleForThreeWeeks(schedulePage);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Prepare the op template settings First")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void prepareSettingsInOPTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            CinemarkMinorPage cinemarkMinorPage = pageFactory.createConsoleCinemarkMinorPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();

            String option = "Yes, all unassigned shifts";
            OpsPortalLocationsPage opsPortalLocationsPage = (OpsPortalLocationsPage) pageFactory.createOpsPortalLocationsPage();
            opsPortalLocationsPage.clickModelSwitchIconInDashboardPage("Operation Portal");
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
            cinemarkMinorPage.findDefaulTemplate("Cinemark Base Template");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.updateConvertUnassignedShiftsToOpenWhenCreatingScheduleSettingOption(option);
            configurationPage.updateConvertUnassignedShiftsToOpenWhenCopyingScheduleSettingOption(option);
            configurationPage.publishNowTheTemplate();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    private void createScheduleForThreeWeeks(SchedulePage schedulePage) throws Exception {
        boolean isWeekGenerated = schedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            schedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        schedulePage.createScheduleForNonDGFlowNewUI();
        schedulePage.navigateToNextWeek();
        isWeekGenerated = schedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            schedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        schedulePage.createScheduleForNonDGFlowNewUI();
        schedulePage.navigateToNextWeek();
        isWeekGenerated = schedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            schedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        schedulePage.createScheduleForNonDGFlowNewUI();
    }
}
