package com.legion.tests.core;

import com.legion.pages.*;
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
import java.net.SocketImpl;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class BulkDeleteTest extends TestBase {
    private DashboardPage dashboardPage;
    private CreateSchedulePage createSchedulePage;
    private ScheduleMainPage scheduleMainPage;
    private ScheduleShiftTablePage scheduleShiftTablePage;
    private ScheduleCommonPage scheduleCommonPage;

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
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify can delete multiple shifts at a time in week view")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDeleteMultipleShiftsInWeekViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            goToSchedulePageScheduleTab();

            // Create schedule if it is not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            int previousShiftCount = scheduleShiftTablePage.getShiftsCount();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            // Verify can select multiple shifts by pressing Ctrl/Cmd(Mac)
            int selectedShiftCount = 2;
            HashSet<Integer> set = scheduleShiftTablePage.verifyCanSelectMultipleShifts(selectedShiftCount);
            // Verify action menu will pop up when right clicking on anywhere of the selected shifts
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            // Verify the content on pop up menu
            scheduleShiftTablePage.verifyTheContentOnBulkActionMenu(selectedShiftCount);
            // Verify the Delete button on Bulk Action Menu is clickable
            String action = "Delete";
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            // Verify the shifts are marked as X after clicking on Delete button
            scheduleShiftTablePage.verifySelectedShiftsAreMarkedWithX(set);
            // Verify the shift which are marked "x" will not be deleted if canceling editing
            scheduleMainPage.clickOnCancelButtonOnEditMode();
            if (scheduleShiftTablePage.getShiftsCount() == previousShiftCount) {
                SimpleUtils.pass("Shifts are not deleted when clicking on Cancel button!");
            } else {
                SimpleUtils.fail("Shifts are deleted when clicking on Cancel button!", false);
            }
            // Verify multiple shifts are deleted
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            set = scheduleShiftTablePage.verifyCanSelectMultipleShifts(selectedShiftCount);
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            scheduleShiftTablePage.verifyTheContentOnBulkActionMenu(selectedShiftCount);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            scheduleShiftTablePage.verifySelectedShiftsAreMarkedWithX(set);
            scheduleMainPage.saveSchedule();
            int currentShiftCount = scheduleShiftTablePage.getShiftsCount();
            if (currentShiftCount == previousShiftCount - selectedShiftCount) {
                SimpleUtils.pass("Selected shifts are deleted successfully!");
            } else {
                SimpleUtils.fail("Selected shifts are not deleted successfully!", false);
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify can delete multiple shifts at a time in day view")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDeleteMultipleShiftsInDayViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            goToSchedulePageScheduleTab();

            // Create schedule if it is not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            scheduleCommonPage.clickOnDayView();

            int previousShiftCount = scheduleShiftTablePage.getShiftsCount();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            // Verify can select multiple shifts by pressing Ctrl/Cmd(Mac)
            int selectedShiftCount = 2;
            HashSet<Integer> set = scheduleShiftTablePage.verifyCanSelectMultipleShifts(selectedShiftCount);
            // Verify action menu will pop up when right clicking on anywhere of the selected shifts
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            // Verify the content on pop up menu
            scheduleShiftTablePage.verifyTheContentOnBulkActionMenu(selectedShiftCount);
            // Verify the Delete button on Bulk Action Menu is clickable
            String action = "Delete";
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            // Verify the shifts are marked as X after clicking on Delete button
            scheduleShiftTablePage.verifySelectedShiftsAreMarkedWithX(set);
            // Verify the shift which are marked "x" will not be deleted if canceling editing
            scheduleMainPage.clickOnCancelButtonOnEditMode();
            if (scheduleShiftTablePage.getShiftsCount() == previousShiftCount) {
                SimpleUtils.pass("Shifts are not deleted when clicking on Cancel button!");
            } else {
                SimpleUtils.fail("Shifts are deleted when clicking on Cancel button!", false);
            }
            // Verify multiple shifts are deleted
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            set = scheduleShiftTablePage.verifyCanSelectMultipleShifts(selectedShiftCount);
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            scheduleShiftTablePage.verifyTheContentOnBulkActionMenu(selectedShiftCount);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            scheduleShiftTablePage.verifySelectedShiftsAreMarkedWithX(set);
            scheduleMainPage.saveSchedule();
            int currentShiftCount = scheduleShiftTablePage.getShiftsCount();
            if (currentShiftCount == previousShiftCount - selectedShiftCount) {
                SimpleUtils.pass("Selected shifts are deleted successfully!");
            } else {
                SimpleUtils.fail("Selected shifts are not deleted successfully!", false);
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

}
