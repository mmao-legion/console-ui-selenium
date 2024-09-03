package com.legion.tests.core;

import com.legion.pages.*;
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
import java.util.HashMap;
import java.util.List;

public class BudgetWeeklyForRegularLocationTest extends TestBase {
    private DashboardPage dashboardPage;
    private CreateSchedulePage createSchedulePage;
    private ScheduleMainPage scheduleMainPage;
    private ScheduleShiftTablePage scheduleShiftTablePage;
    private ScheduleCommonPage scheduleCommonPage;
    private EditShiftPage editShiftPage;
    private NewShiftPage newShiftPage;
    private ShiftOperatePage shiftOperatePage;
    private ControlsPage controlsPage;
    private ControlsNewUIPage controlsNewUIPage;
    private MySchedulePage mySchedulePage;
    private ForecastPage forecastPage;
    private SmartCardPage smartCardPage;
    private ScheduleOverviewPage scheduleOverviewPage;
    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) {
        try {
            this.createDriver((String) params[0], "69", "Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
            dashboardPage = pageFactory.createConsoleDashboardPage();
            createSchedulePage = pageFactory.createCreateSchedulePage();
            scheduleMainPage = pageFactory.createScheduleMainPage();
            scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            scheduleCommonPage = pageFactory.createScheduleCommonPage();
            editShiftPage = pageFactory.createEditShiftPage();
            newShiftPage = pageFactory.createNewShiftPage();
            shiftOperatePage = pageFactory.createShiftOperatePage();
            controlsPage = pageFactory.createConsoleControlsPage();
            controlsNewUIPage = pageFactory.createControlsNewUIPage();
            mySchedulePage = pageFactory.createMySchedulePage();
            forecastPage = pageFactory.createForecastPage();
            smartCardPage = pageFactory.createSmartCardPage();
            scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Ashutosh")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "Verify the budget values display correct on all pages for regular location when enable display budget config")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyBudgetValuesOnAllPagesForRegularLocationWhenWeeklyEnableBudgetConfigAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Forecast.getValue());
            // Go to Forecast page, Schedule tab
            forecastPage.clickOnLabor();

            //Check edit budget button is present or not when the Budget Config to show is disabled
            SimpleUtils.assertOnFail("Edit button should not be present but is present",
                    !(forecastPage.isLaborBudgetEditBtnLoaded()),false);

            String budgetValueOnForecastSmartCard = forecastPage.getLaborGuidanceOnSummarySmartCard();

            //Go to schedule page
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());

            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isActiveWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            //Check the budget smart card should not show on Schedule Page
            SimpleUtils.assertOnFail("The Weekly budget Smart card should not show:",
                !(smartCardPage.isBudgetHoursSmartCardIsLoad()),false);

            //Check the guidance hrs on schedule smart card same as on forecast smart card
            String guidanceValueOnScheduleSmartCard = smartCardPage.getGuidanceValueFromScheduleBudgetSmartCard();
            SimpleUtils.assertOnFail("The budget value on forecast smart card is: "+budgetValueOnForecastSmartCard
                            + ". The budget value on schedule smart card is: "+guidanceValueOnScheduleSmartCard,
                    budgetValueOnForecastSmartCard.equals(guidanceValueOnScheduleSmartCard), false);

            //Go to overview page
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            //Check the Guidance display instead of Budget
            SimpleUtils.assertOnFail("The Guidance label fail to load! ",
                    !(scheduleOverviewPage.isBudgetLabelShow()), false);

            //Check the Guidance value same with the value on Forecast page
            String guidanceValueOnOverviewPage = scheduleOverviewPage.getCurrentWeekGuidanceHours().split(" ")[0];
            SimpleUtils.assertOnFail("The budget value on overview page is:"+guidanceValueOnOverviewPage
                            + ". The budget value on schedule smart card is: "+budgetValueOnForecastSmartCard,
                    guidanceValueOnOverviewPage.equals(budgetValueOnForecastSmartCard), false);

            //Go to dashboard page
            dashboardPage.clickOnDashboardConsoleMenu();
            dashboardPage.clickOnRefreshButtonOnSMDashboard();
            //Check the budget display instead of Guidance
            SimpleUtils.assertOnFail("The budget label fail to load! ",
                    !(scheduleOverviewPage.isBudgetLabelShow()), false);
            //Check the budget value same with the value on Overview page
            List<WebElement> scheduleOverViewWeeks = scheduleOverviewPage.getOverviewScheduleWeeks();
            HashMap<String, Float> overviewData = scheduleOverviewPage.getWeekHoursByWeekElement(scheduleOverViewWeeks.get(1));

            String guidanceValueOnDashboardPage = String.valueOf((int)Float.parseFloat(overviewData.get("guidanceHours").toString()));

            SimpleUtils.assertOnFail("The budget value on overview page is:"+guidanceValueOnDashboardPage
                            + ". The budget value on schedule smart card is: "+budgetValueOnForecastSmartCard,
                    guidanceValueOnDashboardPage.equals(budgetValueOnForecastSmartCard), false);
        } catch (Exception e) {
           SimpleUtils.fail(e.getMessage(), false);
       }
    }
}
