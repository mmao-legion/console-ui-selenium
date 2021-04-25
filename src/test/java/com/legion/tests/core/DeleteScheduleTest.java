package com.legion.tests.core;

import com.legion.pages.DashboardPage;
import com.legion.pages.SchedulePage;
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
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
                schedulePage.createScheduleForNonDGFlowNewUI();
            } else {
                schedulePage.createScheduleForNonDGFlowNewUI();
            }

            String deleteForWeekText = schedulePage.getDeleteScheduleForWhichWeekText();
            String unPublishedMessage = "This action can’t be undone.";
            // Verify ungenerate button is removed
            schedulePage.verifyUngenerateButtonIsRemoved();
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
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView("Unassigned");
            schedulePage.saveSchedule();

            schedulePage.publishActiveSchedule();
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
            schedulePage.unGenerateActiveScheduleScheduleWeek();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
