package com.legion.tests.core;

import com.legion.api.login.LoginAPI;
import com.legion.pages.*;
import com.legion.pages.OpsPortaPageFactories.ConfigurationPage;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class VailBudgetPCLGTest extends TestBase {
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
    private SmartTemplatePage smartTemplatePage;
    private SmartCardPage smartCardPage;
    private ProfileNewUIPage profileNewUIPage;
    private LoginPage loginPage;
    private ConfigurationPage configurationPage;
    private LoginAPI loginAPI;
    private TeamPage teamPage;
    private ForecastPage forecastPage;
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
            smartTemplatePage = pageFactory.createSmartTemplatePage();
            smartCardPage = pageFactory.createSmartCardPage();
            profileNewUIPage = pageFactory.createProfileNewUIPage();
            loginPage = pageFactory.createConsoleLoginPage();
            configurationPage = pageFactory.createOpsPortalConfigurationPage();
            teamPage = pageFactory.createConsoleTeamPage();
            forecastPage = pageFactory.createForecastPage();
            smartCardPage = pageFactory.createSmartCardPage();
            scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Eric")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "validate budget when set as child in PC LG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyBudgetInPCLGAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Forecast.getValue());
            // Go to Forecast page, Schedule tab
            forecastPage.clickOnLabor();

            //Check edit budget button can load
            SimpleUtils.assertOnFail("Child location disabled the labor budget edit button on week view!",
                    forecastPage.isLaborBudgetEditBtnLoaded(),false);

            //Click edit budget button and check the child budget display
            smartCardPage.clickOnEnterBudgetLink();
            SimpleUtils.assertOnFail("Child Input Budget table are not visible on the page!",
                    smartCardPage.isChildBudgetInputDisplay(),false);

            //Input budget for every child location
            smartCardPage.inputRandomBudgetValueWithChildLocation();
            //Get the budget value on forecast smart card
            String budgetValueOnForecastSmartCard = forecastPage.getLaborBudgetOnSummarySmartCard();

            //Go to schedule page
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());

            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isActiveWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

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

            //Click edit budget button and check the budget display
            smartCardPage.isSmartCardScrolledToRightActive();
            smartCardPage.clickOnEnterBudgetLink();
            SimpleUtils.assertOnFail("Child Input Budget table are not visible on the page!",
                    smartCardPage.isChildBudgetInputDisplay(),false);
            //Input budget for child location
            int sum = smartCardPage.inputRandomBudgetValueWithChildLocation();
            //Get the budget value on schedule page
            budgetValueOnScheduleSmartCard = smartCardPage.getBudgetValueFromScheduleBudgetSmartCard();
            budgetValueOnWeeklyBudgetSmartCard = smartCardPage.getBudgetValueFromWeeklyBudgetSmartCard(weeklyBudgetSmartCard).split(" ")[0];
            SimpleUtils.assertOnFail("The budget value on forecast smart card is: "+budgetValueOnForecastSmartCard
                            + ". The budget value on weekly budget smart card is: "+budgetValueOnWeeklyBudgetSmartCard,
                    sum==Integer.parseInt(budgetValueOnWeeklyBudgetSmartCard.trim()), false);

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
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

}
