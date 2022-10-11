package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.OpsPortaPageFactories.ConfigurationPage;
import com.legion.pages.OpsPortaPageFactories.LocationsPage;
import com.legion.pages.OpsPortaPageFactories.UserManagementPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.core.OpsPortal.LocationsTest;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashSet;

public class ShiftPatternTest extends TestBase {

    private DashboardPage dashboardPage;
    private CreateSchedulePage createSchedulePage;
    private ScheduleMainPage scheduleMainPage;
    private ScheduleShiftTablePage scheduleShiftTablePage;
    private ScheduleCommonPage scheduleCommonPage;
    private LocationsPage locationsPage;
    private NewShiftPage newShiftPage;
    private ConfigurationPage configurationPage;

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
            locationsPage = pageFactory.createOpsPortalLocationsPage();
            newShiftPage = pageFactory.createNewShiftPage();
            configurationPage = pageFactory.createOpsPortalConfigurationPage();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify creating shift pattern rules from scheduling rules template")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyCreatingShiftPatternFromSchedulingRulesTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            // Go to Configurations page
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad("Scheduling Rules");
            configurationPage.isTemplateListPageShow();
            String templateName = "TemplateName-ForAuto";
            configurationPage.deleteTemplate(templateName);
            String workRole = "General Manager";

            //create new template with the 'New Template' button, there are 2 tabs: Detail and Association
            configurationPage.createNewTemplate(templateName);
            configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
