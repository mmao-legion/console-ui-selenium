package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.OpsPortaPageFactories.ConfigurationPage;
import com.legion.pages.OpsPortaPageFactories.JobsPage;
import com.legion.pages.OpsPortaPageFactories.LocationsPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.core.OpsPortal.ConfigurationTest;
import com.legion.tests.core.OpsPortal.LocationsTest;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;
import org.junit.Ignore;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.legion.utils.MyThreadLocal.getTimeOffEndTime;
import static com.legion.utils.MyThreadLocal.getTimeOffStartTime;

public class PlanTest extends TestBase {


    private static HashMap<String, String> scheduleWorkRoles = JsonUtil.getPropertiesFromJsonFile("src/test/resources/WorkRoleOptions.json");
    private static HashMap<String, String> propertyCustomizeMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ScheduleCustomizeNewShift.json");
    private static HashMap<String, String> propertySearchTeamMember = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SearchTeamMember.json");
    private static Map<String, String> newTMDetails = JsonUtil.getPropertiesFromJsonFile("src/test/resources/AddANewTeamMember.json");
    private static HashMap<String, String> imageFilePath = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ProfileImageFilePath.json");

    public enum modelSwitchOperation {

        Console("Console"),
        OperationPortal("Operation Portal");

        private final String value;

        modelSwitchOperation(final String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    public enum planLevelSelection{
        NoSubPlans(""),
        levelBu("BU"),
        levelRegion("Region"),
        levelDistrict("District");
        private final String value;
        planLevelSelection(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    public enum statusCheck {

        NotStarted("Not Started"),
        Inprogress("In Progress"),
        Completed("Completed"),
        ForReview("Ready For Review"),
        Approved("Reviewed-Approved"),
        Rejected("Reviewed-Rejected");
        private final String value;
        statusCheck(final String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }



    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception {
        try {
            this.createDriver((String) params[0], "83", "Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    public enum indexOfActivityType {
        TimeOff(0),
        ShiftOffer(1),
        ShiftSwap(2),
        ProfileUpdate(3),
        Schedule(4);
        private final int value;

        indexOfActivityType(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }
    }

    public enum approveRejectAction {
        Approve("APPROVE"),
        Reject("REJECT");
        private final String value;

        approveRejectAction(final String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Lizzy")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "verify HQ level create plan without sub-plan")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateHQCentralPlanAsAInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
            String currentTime =  dfs.format(new Date()).trim();
            String planName = "AutomationCreatedPlanName" +currentTime;
//            String regionName="Region-ForAutomation";
            String regionName="Region1";

            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            //navigate to some region
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeUpperFieldsByMagnifyGlassIcon(regionName);
            //Go to OP page
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
            //go to locations tab
            locationsPage.clickOnLocationsTab();
            //go to Global Configuration tab
            locationsPage.goToGlobalConfigurationInLocations();
            //set the plan level as centrailized
            locationsPage.setLaborBudgetLevel(true,planLevelSelection.NoSubPlans.getValue());
            //switch back to controls
            locationsPage.clickModelSwitchIconInDashboardPage(ConfigurationTest.modelSwitchOperation.Console.getValue());
//            locationSelectorPage.changeUpperFieldsByMagnifyGlassIcon(regionName);
            //nacigate to plan page
            PlanPage planPage = pageFactory.createConsolePlanPage();
            planPage.clickOnPlanConsoleMenuItem();
            //create a centralized plan
            planPage.createANewPlan(planName);
            String scePlanName=planName+" scenario 1";
            //Assert the scenario plan created and scenario plan name
            planPage.verifyScenarioPlanAutoCreated(scePlanName);
            //search a plan and do operation for some scenario plan
            planPage.takeOperationToPlan(planName,scePlanName,PlanTest.statusCheck.NotStarted.getValue());
            planPage.takeOperationToPlan(planName,scePlanName, PlanTest.statusCheck.Inprogress.getValue());

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Lizzy")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "verify HQ level create plan with sub-plan")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreatePlanWithSubPlansAsAInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
            String currentTime =  dfs.format(new Date()).trim();
            String planName = "AutomationCreatedPlanName" +currentTime;
            String regionName="Region-ForAutomation";

            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            //navigate to some region
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeUpperFieldsByMagnifyGlassIcon(regionName);
             //Go to OP page
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
            //go to locations tab
            locationsPage.clickOnLocationsTab();
            //go to Global Configuration tab
            locationsPage.goToGlobalConfigurationInLocations();
            //set the plan level as centrailized
            locationsPage.setLaborBudgetLevel(true,planLevelSelection.NoSubPlans.getValue());
            //switch back to controls
            locationsPage.clickModelSwitchIconInDashboardPage(ConfigurationTest.modelSwitchOperation.Console.getValue());
            locationSelectorPage.changeUpperFieldsByMagnifyGlassIcon(regionName);
            //nacigate to plan page
            PlanPage planPage = pageFactory.createConsolePlanPage();
            planPage.clickOnPlanConsoleMenuItem();
            //create a centralized plan
            planPage.createANewPlan(planName);
            //Assert the scenario plan created and scenario plan name
            planPage.verifyScenarioPlanAutoCreated(planName);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
    @Automated(automated = "Automated")
    @Owner(owner = "Lizzy")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "verify HQ level create plan with sub-plan")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyHQCentralPlanListPageAsAInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String keywords = "AutomationCreatedPlanName";
            String regionName="Region-ForAutomation";

            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail(pa l"DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            //navigate to some region
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(regionName);
            //navigate to plan page
            PlanPage planPage = pageFactory.createConsolePlanPage();
            planPage.clickOnPlanConsoleMenuItem();
            //check a HQ parent can be expanded
            planPage.searchAPlan(keywords);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

}