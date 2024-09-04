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
import java.util.Random;

public class BudgetWeeklyForP2PLGTest extends TestBase {
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
    private LocationSelectorPage locationSelectorPage;
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
            locationSelectorPage = pageFactory.createLocationSelectorPage();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "Verify the budget values display correct on all pages for p2p child location when enable display budget config with weekly budget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyBudgetValuesOnAllPagesForP2PChildLocationWhenEnableBudgetConfigAndWeeklyAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
//        try {
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Forecast.getValue());

            //Get child location
            List<String> locations = forecastPage.getAllLocationsFromFilter();
            int index = (new Random()).nextInt(locations.size());
            String childLocation = locations.get(index);

            //Navigate to the child location
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(childLocation);
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Forecast.getValue());
            // Go to Forecast page, Schedule tab
            forecastPage.clickOnLabor();

            //Check edit budget button can load
            SimpleUtils.assertOnFail("Edit button fail to load on forecast smart card!",
                    forecastPage.isLaborBudgetEditBtnLoaded(),false);

            //Click edit budget button and check there is one budget row display for regular location
            smartCardPage.clickOnEnterBudgetLink();
            SimpleUtils.assertOnFail("Weekly Input Budget table are not visible on the page!",
                    smartCardPage.isWeeklyBudgetInputDisplayForRegularLocation(),false);

            //Input budget for every day and save
            smartCardPage.inputRandomBudgetValue();
            //Get the budget value on forecast smart card
            String budgetValueOnForecastSmartCard = forecastPage.getLaborBudgetOnSummarySmartCard();

            //Go to schedule page
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());

//            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
//            if (isActiveWeekGenerated) {
//                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
//            }
//            createSchedulePage.createScheduleForNonDGFlowNewUI();

            //Check the budget hrs on budget smart card same as on forecast smart card
            smartCardPage.isBudgetHoursSmartCardIsLoad();
            String weeklyBudgetSmartCard = "Weekly Budget";
            String budgetValueOnWeeklyBudgetSmartCard = smartCardPage.getBudgetValueFromWeeklyBudgetSmartCard(weeklyBudgetSmartCard).split(" ")[0];
            SimpleUtils.assertOnFail("The budget value on forecast smart card is: "+budgetValueOnForecastSmartCard
                            + ". The budget value on weekly budget smart card is: "+budgetValueOnWeeklyBudgetSmartCard,
                    budgetValueOnForecastSmartCard.equals(budgetValueOnWeeklyBudgetSmartCard), false);

            //Check the budget hrs on schedule smart card same as on forecast smart card
            String budgetValueOnScheduleSmartCard = smartCardPage.getBudgetValueFromScheduleBudgetSmartCard();
            SimpleUtils.assertOnFail("The budget value on forecast smart card is: "+budgetValueOnForecastSmartCard
                            + ". The budget value on schedule smart card is: "+budgetValueOnScheduleSmartCard,
                    budgetValueOnForecastSmartCard.equals(budgetValueOnScheduleSmartCard), false);

            //Click edit budget button and check the daily budget display
            smartCardPage.clickOnEnterBudgetLink();
            SimpleUtils.assertOnFail("Daily Input Budget table are not visible on the page!",
                    smartCardPage.isWeeklyBudgetInputDisplayForRegularLocation(),false);
            //Input budget for every day and save
            List<String> budgetValuesOnEditBudgetPage = smartCardPage.inputRandomBudgetValue();
            //Get the budget value on schedule page
            budgetValueOnScheduleSmartCard = smartCardPage.getBudgetValueFromScheduleBudgetSmartCard();
            budgetValueOnWeeklyBudgetSmartCard = smartCardPage.getBudgetValueFromWeeklyBudgetSmartCard(weeklyBudgetSmartCard).split(" ")[0];
            String budgetTotalValueOnEditBudgetPage = String.valueOf((int)Float.parseFloat(budgetValuesOnEditBudgetPage.get(budgetValuesOnEditBudgetPage.size()-1).split(" ")[0]));
            SimpleUtils.assertOnFail("The budget value on edit budget page is:"+budgetTotalValueOnEditBudgetPage+
                    "The budget value on weekly budget smart card is: "+budgetValueOnWeeklyBudgetSmartCard
                            + ". The budget value on schedule smart card is: "+budgetValueOnScheduleSmartCard,
                    budgetTotalValueOnEditBudgetPage.equals(budgetValueOnScheduleSmartCard)
                    && budgetTotalValueOnEditBudgetPage.equals(budgetValueOnWeeklyBudgetSmartCard), false);

            //Go to overview page
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            //Check the budget display instead of Guidance
            SimpleUtils.assertOnFail("The budget label fail to load! ",
                    scheduleOverviewPage.isBudgetLabelShow(), false);

            //Check the budget value same with the value on schedule page
            String budgetValueOnOverviewPage = scheduleOverviewPage.getCurrentWeekBudgetHours().split(" ")[0];
            SimpleUtils.assertOnFail("The budget value on overview page is:"+budgetValueOnOverviewPage
                            + ". The budget value on schedule smart card is: "+budgetValueOnScheduleSmartCard,
                    budgetValueOnOverviewPage.equals(budgetValueOnScheduleSmartCard), false);
            //Go to dashboard page
            dashboardPage.clickOnDashboardConsoleMenu();
            dashboardPage.clickOnRefreshButtonOnSMDashboard();
            //Check the budget display instead of Guidance
            SimpleUtils.assertOnFail("The budget label fail to load! ",
                    scheduleOverviewPage.isBudgetLabelShow(), false);
            //Check the budget value same with the value on schedule page
            List<WebElement> scheduleOverViewWeeks = scheduleOverviewPage.getOverviewScheduleWeeks();
            HashMap<String, Float> overviewData = scheduleOverviewPage.getWeekHoursByWeekElement(scheduleOverViewWeeks.get(1));

            String budgetValueOnDashboardPage = String.valueOf((int)Float.parseFloat(overviewData.get("guidanceHours").toString()));

            SimpleUtils.assertOnFail("The budget value on overview page is:"+budgetValueOnDashboardPage
                            + ". The budget value on schedule smart card is: "+budgetValueOnScheduleSmartCard,
                    budgetValueOnDashboardPage.equals(budgetValueOnScheduleSmartCard), false);
//        } catch (Exception e) {
//            SimpleUtils.fail(e.getMessage(), false);
//        }
    }
}
