package com.legion.tests.core;

import com.legion.pages.DashboardPage;
import com.legion.pages.LiquidDashboardPage;
import com.legion.pages.SchedulePage;
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
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            // Create schedule if it is not created
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                schedulePage.createScheduleForNonDGFlowNewUI();
            }

            // Verify Breaks option is enabled in non-Edit mode week view
            schedulePage.clickOnProfileIcon();
            schedulePage.verifySpecificOptionEnabledOnShiftMenu(breakOption);

            // Verify Breaks option is enabled in non-Edit mode day view
            schedulePage.clickOnDayView();
            schedulePage.clickOnProfileIcon();
            schedulePage.verifySpecificOptionEnabledOnShiftMenu(breakOption);

            // Verify Edit Breaks is enabled in edit mode day view
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnProfileIcon();
            schedulePage.verifySpecificOptionEnabledOnShiftMenu(editBreakOption);

            // Verify Edit Breaks is enabled in edit mode week view
            schedulePage.clickOnWeekView();
            schedulePage.clickOnProfileIcon();
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
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            // Create schedule if it is not created
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                schedulePage.createScheduleForNonDGFlowNewUI();
            }

            // Verify Edit Breaks is clickable in week view
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            List<String> shiftInfo = new ArrayList<>();
            String firstNameOfTM = "";
            int index = 0;
            while (firstNameOfTM.equals("") || firstNameOfTM.equals("Open") || firstNameOfTM.equals("Unassigned")) {
                index = schedulePage.getRandomIndexOfShift();
                shiftInfo = schedulePage.getTheShiftInfoByIndex(index);
                //Search shift by TM names: first name and last name
                firstNameOfTM = shiftInfo.get(0);
            }
            schedulePage.clickOnShiftByIndex(index);
            schedulePage.clickOnEditMeaLBreakTime();

            // Verify Edit breaks dialog should pop up
            SimpleUtils.assertOnFail("Edit Breaks dialog doesn't pop up!", schedulePage.isMealBreakTimeWindowDisplayWell(true), false);

            // Verify the shift info is correct
            schedulePage.verifyShiftInfoIsCorrectOnMealBreakPopUp(shiftInfo);

            //
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
