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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteScheduleTest extends TestBase {

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
    @TestName(description = "Verify internal admin can delete a published schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDeletePublishedScheduleAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            } else {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            String deleteForWeekText = schedulePage.getDeleteScheduleForWhichWeekText();
            String unPublishedMessage = "This action can’t be undone.";
            // Verify the visibility of Delete button
            SimpleUtils.assertOnFail("Schedule page: Delete button is not visible!", schedulePage.isDeleteScheduleButtonLoaded(), false);
            // Verify the functionality of Delete button
            schedulePage.verifyClickOnDeleteScheduleButton();
            // Verify the content on Delete Schedule confirm window
            schedulePage.verifyTheContentOnDeleteScheduleDialog(unPublishedMessage, deleteForWeekText);
            // Verify the Delete button is disabled by default
            schedulePage.verifyDeleteBtnDisabledOnDeleteScheduleDialog();
            // Verify the Delete button is enabled when clicking the check box
            schedulePage.verifyDeleteButtonEnabledWhenClickingCheckbox();
            // Verify the functionality of Cancel button
            schedulePage.verifyClickOnCancelBtnOnDeleteScheduleDialog();

            // Delete the Unassigned shifts to unblock publishing
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView("Unassigned");
            scheduleMainPage.saveSchedule();

            createSchedulePage.publishActiveSchedule();
            String publishedDeleteMessage = "This action can’t be undone. The schedule has been published, it will be withdrawn from team members";
            schedulePage.verifyClickOnDeleteScheduleButton();
            // Verify the content of Delete Schedule window when schedule is published
            schedulePage.verifyTheContentOnDeleteScheduleDialog(publishedDeleteMessage, deleteForWeekText);
            // Verify the Delete button is disabled by default when schedule is published
            schedulePage.verifyDeleteBtnDisabledOnDeleteScheduleDialog();
            // Verify the Delete button is enabled when clicking the check box when schedule is published
            schedulePage.verifyDeleteButtonEnabledWhenClickingCheckbox();
            // Verify the functionality of Cancel button when schedule is published
            schedulePage.verifyClickOnCancelBtnOnDeleteScheduleDialog();
            // Verify the functionality of Delete button when schedule is published
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Store Manger can delete an unpublished schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDeleteUnPublishedScheduleAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            } else {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            // Login as SM
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());

            String deleteForWeekText = schedulePage.getDeleteScheduleForWhichWeekText();
            String unPublishedMessage = "This action can’t be undone.";

            // Verify the visibility of Delete button
            SimpleUtils.assertOnFail("Schedule page: Delete button is not visible!", schedulePage.isDeleteScheduleButtonLoaded(), false);
            // Verify the functionality of Delete button
            schedulePage.verifyClickOnDeleteScheduleButton();
            // Verify the content on Delete Schedule confirm window
            schedulePage.verifyTheContentOnDeleteScheduleDialog(unPublishedMessage, deleteForWeekText);
            // Verify the Delete button is disabled by default
            schedulePage.verifyDeleteBtnDisabledOnDeleteScheduleDialog();
            // Verify the Delete button is enabled when clicking the check box
            schedulePage.verifyDeleteButtonEnabledWhenClickingCheckbox();
            // Verify the functionality of Cancel button
            schedulePage.verifyClickOnCancelBtnOnDeleteScheduleDialog();
            // Verify the functionality of Delete button when schedule is unpublished
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Store Manger cannot see the Delete button if schedule is published")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySMCannotSeeDeleteButtonIfScheduleIsPublishedAsStoreManager(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            boolean isSchedulePublished = createSchedulePage.isCurrentScheduleWeekPublished();
            if (!isSchedulePublished) {
                shiftOperatePage.convertAllUnAssignedShiftToOpenShift();
                createSchedulePage.publishActiveSchedule();
            }

            // Verify Store Manger cannot see the Delete button if schedule is published
            SimpleUtils.assertOnFail("Schedule page: Delete button should not show when the schedule is published!", !schedulePage.isDeleteScheduleButtonLoaded(), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Store Manager cannot see the Delete button when schedule is not pulished")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySMCannotSeeDeleteButtonIfScheduleIsNotPublishedAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Verify SM doesn't have "Schedule: Manage Schedule" permission
            CinemarkMinorPage cinemarkMinorPage = pageFactory.createConsoleCinemarkMinorPage();
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsPage.gotoControlsPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);

            SimpleUtils.assertOnFail("Users and Roles card not loaded Successfully!", controlsNewUIPage.isControlsUsersAndRolesCard(), false);
            controlsNewUIPage.clickOnControlsUsersAndRolesSection();
            controlsNewUIPage.verifyUsersAreLoaded();
            controlsPage.clickGlobalSettings();
            String accessRoleTab = "Access Roles";
            controlsNewUIPage.selectUsersAndRolesSubTabByLabel(accessRoleTab);
            String permissionSection = "Schedule";
            String permission = "Manage Schedule";
            String actionOff = "off";
            String actionOn = "on";
            cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Edit.getValue());
            controlsNewUIPage.turnOnOrOffSpecificPermissionForSM(permissionSection, permission, actionOff);
            cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Save.getValue());

            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            boolean isScheduleCreated = createSchedulePage.isWeekGenerated();
            if (isScheduleCreated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            // Login as SM
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            // Verify Store Manager cannot see the Delete button when schedule is not published
            SimpleUtils.assertOnFail("Schedule page: Delete button should not show when the schedule is not published!", !schedulePage.isDeleteScheduleButtonLoaded(), false);
            loginPage.logOut();

            // Login as Internal admin, add the permission back
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            controlsPage.gotoControlsPage();
            controlsNewUIPage.clickOnControlsUsersAndRolesSection();
            controlsNewUIPage.verifyUsersAreLoaded();
            controlsPage.clickGlobalSettings();
            controlsNewUIPage.selectUsersAndRolesSubTabByLabel(accessRoleTab);
            cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Edit.getValue());
            controlsNewUIPage.turnOnOrOffSpecificPermissionForSM(permissionSection, permission, actionOn);
            cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Save.getValue());
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Store Manger can publish schedule if schedule is not published")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySMCanPublishScheduleIfScheduleIsNotPublishedAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Verify SM doesn't have "Schedule: Manage Schedule" permission
            CinemarkMinorPage cinemarkMinorPage = pageFactory.createConsoleCinemarkMinorPage();
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            controlsPage.gotoControlsPage();

            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsNewUIPage.clickOnControlsUsersAndRolesSection();
            controlsNewUIPage.verifyUsersAreLoaded();
            controlsPage.clickGlobalSettings();
            String accessRoleTab = "Access Roles";
            controlsNewUIPage.selectUsersAndRolesSubTabByLabel(accessRoleTab);
            String permissionSection = "Schedule";
            String permission1 = "Manage Schedule";
            String permission2 = "Publish Schedule";
            String actionOff = "off";
            String actionOn = "on";
            cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Edit.getValue());
            controlsNewUIPage.turnOnOrOffSpecificPermissionForSM(permissionSection, permission1, actionOff);
            // Verify SM have "Schedule: Publish Schedule" permission
            controlsNewUIPage.turnOnOrOffSpecificPermissionForSM(permissionSection, permission2, actionOn);
            cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Save.getValue());

            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            boolean isScheduleCreated = createSchedulePage.isWeekGenerated();
            if (!isScheduleCreated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            boolean isSchedulePublished = createSchedulePage.isCurrentScheduleWeekPublished();
            if (isSchedulePublished) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            // Login as SM
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            // Verify Store Manger can publish schedule if schedule is not published
            createSchedulePage.publishActiveSchedule();
            loginPage.logOut();

            // Login as Internal admin, add the permission back
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            controlsPage.gotoControlsPage();
            controlsNewUIPage.clickOnControlsUsersAndRolesSection();
            controlsNewUIPage.verifyUsersAreLoaded();
            controlsPage.clickGlobalSettings();
            controlsNewUIPage.selectUsersAndRolesSubTabByLabel(accessRoleTab);
            cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Edit.getValue());
            controlsNewUIPage.turnOnOrOffSpecificPermissionForSM(permissionSection, permission1, actionOn);
            controlsNewUIPage.turnOnOrOffSpecificPermissionForSM(permissionSection, permission2, actionOn);
            cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Save.getValue());
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

}
