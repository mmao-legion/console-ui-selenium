package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.OpsPortaPageFactories.ConfigurationPage;
import com.legion.pages.core.OpsPortal.OpsPortalLocationsPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
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
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            teamPage.rejectAllTeamMembersTimeOffRequest(profileNewUIPage, 0);

            dashboardPage.clickOnIntegrationConsoleMenu();
            dashboardPage.verifyIntegrationPageIsLoaded();
            IntegrationPage integrationPage = pageFactory.createIntegrationPage();
            if(!integrationPage.checkIsConfigExists("custom", "HR")){
                Map<String, String> configInfo = new HashMap<>();
                configInfo.put("channel", "CUSTOM");
                configInfo.put("applicationType", "HR");
                configInfo.put("status", "ENABLED");
                configInfo.put("timeZoneOption", "UTC");

                integrationPage.createConfig(configInfo);
            }

            // Set time off policy
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsPage.gotoControlsPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);

            dashboardPage.navigateToDashboard();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            controlsPage.gotoControlsPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
            SimpleUtils.assertOnFail("Scheduling collaboration page not loaded successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);

            //Set 'Automatically convert unassigned shifts to open shifts when generating the schedule?' set as Yes, all unassigned shifts
            controlsNewUIPage.clickOnScheduleCollaborationOpenShiftAdvanceBtn();
            controlsNewUIPage.updateConvertUnassignedShiftsToOpenSettingOption("Yes, all unassigned shifts");

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

            controlsPage.gotoControlsPage();
            controlsPage.clickGlobalSettings();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.enableOrDisableScheduleCopyRestriction("no");

            //Set buffer hours: before--2, after--3
            controlsPage.gotoControlsPage();
            controlsPage.clickGlobalSettings();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.clickOnSchedulingPoliciesSchedulesAdvanceBtn();
            HashMap<String, String> schedulingPoliciesData = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SchedulingPoliciesData.json");
            String beforeBufferCount = schedulingPoliciesData.get("Additional_Schedule_Hours_Before");
            String afterBufferCount = schedulingPoliciesData.get("Additional_Schedule_Hours_After");
            controlsNewUIPage.updateScheduleBufferHoursBefore(beforeBufferCount);
            controlsNewUIPage.updateScheduleBufferHoursAfter(afterBufferCount);

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

//            MyThreadLocal.setIsNeedEditingOperatingHours(true);
//            createScheduleForThreeWeeks(schedulePage);
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

            dashboardPage.clickOnIntegrationConsoleMenu();
            dashboardPage.verifyIntegrationPageIsLoaded();
            IntegrationPage integrationPage = pageFactory.createIntegrationPage();
            if(!integrationPage.checkIsConfigExists("custom", "HR")){
                Map<String, String> configInfo = new HashMap<>();
                configInfo.put("channel", "CUSTOM");
                configInfo.put("applicationType", "HR");
                configInfo.put("status", "ENABLED");
                configInfo.put("timeZoneOption", "UTC");

                integrationPage.createConfig(configInfo);
            }

            CinemarkMinorPage cinemarkMinorPage = pageFactory.createConsoleCinemarkMinorPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();

            String option = "Yes, all unassigned shifts";
            OpsPortalLocationsPage opsPortalLocationsPage = (OpsPortalLocationsPage) pageFactory.createOpsPortalLocationsPage();
            opsPortalLocationsPage.clickModelSwitchIconInDashboardPage("Operation Portal");
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
            cinemarkMinorPage.findDefaultTemplate("Cinemark Base Template");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.updateConvertUnassignedShiftsToOpenWhenCreatingScheduleSettingOption(option);
            configurationPage.updateConvertUnassignedShiftsToOpenWhenCopyingScheduleSettingOption(option);
            configurationPage.publishNowTheTemplate();

            String wfsName = "Lone Star Region";
            cinemarkMinorPage.findDefaultTemplate("Cinemark Base Template");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.setWFS("Yes");
            configurationPage.selectWFSGroup(wfsName);
            configurationPage.updateConvertUnassignedShiftsToOpenWhenCreatingScheduleSettingOption(option);
            configurationPage.updateConvertUnassignedShiftsToOpenWhenCopyingScheduleSettingOption(option);
            configurationPage.publishNowTheTemplate();

            //Set buffer hours on OP: before--2, after--3
            configurationPage.goToConfigurationPage();
            controlsNewUIPage.clickOnControlsOperatingHoursSection();
            cinemarkMinorPage.findDefaultTemplate("Cinemark Base Template Updated");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectOperatingBufferHours("BufferHour");
            configurationPage.setOpeningAndClosingBufferHours(2, 3);
            configurationPage.publishNowTheTemplate();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    private void createScheduleForThreeWeeks(SchedulePage schedulePage) throws Exception {
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();
        scheduleCommonPage.navigateToNextWeek();
        isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();
        scheduleCommonPage.navigateToNextWeek();
        isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();
    }
}
