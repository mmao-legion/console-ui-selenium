package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.core.ConsoleScheduleCommonPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MealAndRestBreakTest extends TestBase {

    private String breakOption = "Breaks";
    private String editBreakOption = "Edit Breaks";

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
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "Verify Edit Breaks option is available")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyEditBreaksOptionIsAvailableAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            // Create schedule if it is not created
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            // Verify Breaks option is enabled in non-Edit mode week view
            shiftOperatePage.clickOnProfileIcon();
            schedulePage.verifySpecificOptionEnabledOnShiftMenu(breakOption);

            // Verify Breaks option is enabled in non-Edit mode day view
            scheduleCommonPage.clickOnDayView();
            shiftOperatePage.clickOnProfileIcon();
            schedulePage.verifySpecificOptionEnabledOnShiftMenu(breakOption);

            // Verify Edit Breaks is enabled in edit mode day view
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.clickOnProfileIcon();
            schedulePage.verifySpecificOptionEnabledOnShiftMenu(editBreakOption);

            // Verify Edit Breaks is enabled in edit mode week view
            scheduleCommonPage.clickOnWeekView();
            shiftOperatePage.clickOnProfileIcon();
            schedulePage.verifySpecificOptionEnabledOnShiftMenu(editBreakOption);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "Verify the functionality of Edit Break option in week view")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheFunctionalityOfEditBreaksWeekViewAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            // Create schedule if it is not created
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            // Verify Edit Breaks is clickable in week view
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            List<String> shiftInfo = new ArrayList<>();
            String firstNameOfTM = "";
            int index = 0;
            while (firstNameOfTM.equals("") || firstNameOfTM.equals("Open") || firstNameOfTM.equals("Unassigned")) {
                index = schedulePage.getRandomIndexOfShift();
                shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(index);
                //Search shift by TM names: first name and last name
                firstNameOfTM = shiftInfo.get(0);
            }
            schedulePage.clickOnShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();

            // Verify Edit breaks dialog should pop up
            SimpleUtils.assertOnFail("Edit Breaks dialog doesn't pop up!", shiftOperatePage.isMealBreakTimeWindowDisplayWell(true), false);

            // Verify the shift info is correct
            schedulePage.verifyShiftInfoIsCorrectOnMealBreakPopUp(shiftInfo);

            // Verify meal break and rest break are placed in the correct time
            schedulePage.verifyMealBreakAndRestBreakArePlacedCorrectly();

            // Verify can change the length of meal break and rest break
            // Verify can move the place of meal break and rest break
            shiftOperatePage.verifyEditBreaks();
            // Verify the functionality of CANCEL button
            shiftOperatePage.clickOnCancelEditShiftTimeButton();
            // Verify the functionality of UPDATE button
            List<String> breakTimes = shiftOperatePage.verifyEditBreaks();
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            // Verify the shift should have edit icon
            schedulePage.verifySpecificShiftHaveEditIcon(index);
            // Verify the changed meal break and rest break should be updated
            schedulePage.clickOnShiftByIndex(index);
            shiftOperatePage.clickOnEditMeaLBreakTime();
            schedulePage.verifyBreakTimesAreUpdated(breakTimes);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
