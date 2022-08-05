package com.legion.tests.core;

import com.legion.api.toggle.ToggleAPI;
import com.legion.api.toggle.Toggles;
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
import java.util.ArrayList;

public class ScheduleCopyTest extends TestBase {
    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) {
        try {
            this.createDriver((String) params[0], "69", "Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the content on create schedule page in week view")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheContentOnCreateSchedulePageInWeekViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ToggleSummaryPage toggleSummaryPage = pageFactory.createToggleSummaryPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }

            //Verify day/week button should be disabled
            SimpleUtils.assertOnFail("The day and week view button should not enabled! ",
                    scheduleCommonPage.checkIfDayAndWeekViewButtonEnabled(), false);

            //Verify weeks are loaded in week view
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToPreviousWeek();

            //Verify Create Schedule button is loaded in week view
            SimpleUtils.assertOnFail("The Create shcedule button should loaded! ",
                    createSchedulePage.isGenerateButtonLoaded(), false);

            //Verify OPERATING HOURS section is loaded in week view
            toggleSummaryPage.verifyTheContentInOperatingHoursSection();
            //Verify STAFF section is loaded in week view
            toggleSummaryPage.verifyTheContentInStaffSection();
            //Verify Roster Updates section is loaded in week view
            toggleSummaryPage.verifyTheContentInRosterUpdatesSection();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
//    @Enterprise(name = "Vailqacn_Enterprise")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the enhanced copy modal for non-dg flow")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheEnhancedCopyModalForNonDgFlowAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ToggleSummaryPage toggleSummaryPage = pageFactory.createToggleSummaryPage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            if (smartCardPage.isRequiredActionSmartCardLoaded()){
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("unassigned");
                scheduleMainPage.saveSchedule();
            }
            createSchedulePage.publishActiveSchedule();
            scheduleCommonPage.navigateToNextWeek();
            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }

            //Click on "Create Schedule"
            String weekInfo = scheduleCommonPage.getActiveWeekText().substring(10);
            createSchedulePage.clickCreateScheduleBtn();
            //Verify the content on Confirm Operating Hours window
            createSchedulePage.verifyTheContentOnConfirmOperatingHoursWindow(weekInfo, location);
            //Verify the functioning of Edit button on Confirm Operating Hours window
            //Verify Start and End time can be updated
            createSchedulePage.editTheOperatingHours(new ArrayList<>());
            //Verify the functionality of Cancel button on Comfirm Operating Hours window
            createSchedulePage.clickExitBtnToExitCreateScheduleWindow();
            Thread.sleep(3000);
            SimpleUtils.assertOnFail("The create schedule window should not loaded! ",
                    !createSchedulePage.checkIfCreateScheduleWindowLoad(), false);
            //Verify the functionality of Next button on Confirm Operating Hours window
            createSchedulePage.clickCreateScheduleBtn();
            createSchedulePage.clickNextBtnOnCreateScheduleWindow();
            //Verify the content on Enter Budget window
            createSchedulePage.verifyTheContentOnEnterBudgetWindow(weekInfo, location);
            //Verify the functioning of Edit button on Enter Budget window
            //Verify budget hours can be updated
            float budgetHrs = createSchedulePage.checkEnterBudgetWindowLoadedForNonDG();

            //Verify the content on Copy Schedule window
            createSchedulePage.verifyTheContentOnCopyScheduleWindow(weekInfo, location, budgetHrs, 0);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
//    @Enterprise(name = "Vailqacn_Enterprise")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the functionality of copying a Suggested Schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheFunctionalityOfCopyingASuggestedScheduleAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ToggleSummaryPage toggleSummaryPage = pageFactory.createToggleSummaryPage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            if (smartCardPage.isRequiredActionSmartCardLoaded()){
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("unassigned");
                scheduleMainPage.saveSchedule();
            }
            createSchedulePage.publishActiveSchedule();
            scheduleCommonPage.navigateToNextWeek();
            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            String weekInfo = scheduleCommonPage.getActiveWeekText().substring(10);
            createSchedulePage.clickCreateScheduleBtn();
            createSchedulePage.clickNextButtonOnCreateScheduleWindow();
            //Verify the content on Enter Budget window
            createSchedulePage.clickNextButtonOnCreateScheduleWindow();
            createSchedulePage.selectWhichWeekToCopyFrom("");
            createSchedulePage.selectWhichWeekToCopyFrom("SUGGESTED");
            // Click on Next button successfully, schedule will be created
            createSchedulePage.clickOnFinishButtonOnCreateSchedulePage();
            createSchedulePage.verifyTheScheduleSuccessMessage(weekInfo);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }
}
