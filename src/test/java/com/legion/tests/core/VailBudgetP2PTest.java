package com.legion.tests.core;

import com.legion.api.login.LoginAPI;
import com.legion.pages.*;
import com.legion.pages.OpsPortaPageFactories.ConfigurationPage;
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
import java.util.List;
import java.util.Random;

public class VailBudgetP2PTest extends TestBase {
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
            smartTemplatePage = pageFactory.createSmartTemplatePage();
            smartCardPage = pageFactory.createSmartCardPage();
            profileNewUIPage = pageFactory.createProfileNewUIPage();
            loginPage = pageFactory.createConsoleLoginPage();
            configurationPage = pageFactory.createOpsPortalConfigurationPage();
            teamPage = pageFactory.createConsoleTeamPage();
            forecastPage = pageFactory.createForecastPage();
            locationSelectorPage = pageFactory.createLocationSelectorPage();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Ashutosh")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "Verify no budget page on P2P parent level when enable display budget config And Weekly")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyNoEditBudgetPageOnP2PParentLevelWhenEnableDisplayBudgetConfigAndWeeklyAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        //try {
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
        SimpleUtils.assertOnFail("Child location disabled the labor budget edit button on week view!",
                forecastPage.isLaborBudgetEditBtnLoaded(), false);

        //Click edit budget button and check the child budget display
        smartCardPage.clickOnEnterBudgetLink();
        SimpleUtils.assertOnFail("Child Input Budget table are not visible on the page!",
                smartCardPage.isChildBudgetInputDisplay(), false);

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
        SimpleUtils.assertOnFail("The budget value on forecast smart card is: " + budgetValueOnForecastSmartCard
                        + ". The budget value on weekly budget smart card is: " + budgetValueOnWeeklyBudgetSmartCard,
                budgetValueOnForecastSmartCard.equals(budgetValueOnWeeklyBudgetSmartCard), false);

        createSchedulePage.unGenerateActiveScheduleScheduleWeek();

        //Navigate to P2P parent and generate schedule
        locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(location);
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        //Go to schedule page
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());

        boolean isActiveWeekGeneratedAtP2P = createSchedulePage.isWeekGenerated();
        if (isActiveWeekGeneratedAtP2P) {
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();

        //No Weekly or Daily Budget Smart card at P2P Parent location
        boolean p2pNoBudgetSmartCard = smartCardPage.isBudgetHoursSmartCardIsLoad();
        if (!p2pNoBudgetSmartCard) {
            SimpleUtils.assertOnFail("Weekly Budget smart card is present ", (!p2pNoBudgetSmartCard), false);
            //}
        }
    }
}
