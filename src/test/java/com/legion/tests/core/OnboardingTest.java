package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.core.OpsPortalLocationsPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;

import static com.legion.utils.MyThreadLocal.getDriver;

public class OnboardingTest  extends TestBase {

    private static HashMap<String, String> testDataMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/OnboardingTestData.json");
    private String nonSSOEnterprise = propertyMap.get("KendraScott2_Enterprise");
    private String ssoEnterprise = propertyMap.get("Dgch_Enterprise");
    private String currentLocation = "";

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{
        try {
            this.createDriver((String) params[0], "83", "Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDoneWithoutUpdateUpperfield((String) params[1], (String) params[2], (String) params[3]);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            if (MyThreadLocal.getEnterprise().equalsIgnoreCase(nonSSOEnterprise)) {
                currentLocation = testDataMap.get("Non-SSO_Location");
                locationSelectorPage.changeUpperFields(testDataMap.get("Non-SSO_Upperfields"));
                locationSelectorPage.changeLocation(currentLocation);
            } else if (MyThreadLocal.getEnterprise().equalsIgnoreCase(ssoEnterprise)) {
                currentLocation = testDataMap.get("SSO_Location");
                locationSelectorPage.changeUpperFields(testDataMap.get("SSO_Upperfields"));
                locationSelectorPage.changeLocation(currentLocation);
            }
            boolean isLoginDone = loginPage.isLoginDone();
            loginPage.verifyLoginDone(isLoginDone, currentLocation);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the onboarding flow for New hire and status changed to Active (Non-SSO)")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheOnboardingFlowForNewHireAndStatusChangeToActiveAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            verifyOnboardingFlow("Yes");

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    private void verifyOnboardingFlow (String yesOrNo) throws Exception {
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();

            controlsPage.gotoControlsPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
            SimpleUtils.assertOnFail("Scheduling collaboration page not loaded successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
            controlsNewUIPage.clickOnGlobalLocationButton();

            controlsNewUIPage.setAutomaticallySetOnboardedEmployeesToActive(yesOrNo);

        } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
            OpsPortalLocationsPage opsPortalLocationsPage = (OpsPortalLocationsPage) pageFactory.createOpsPortalLocationsPage();
            opsPortalLocationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.goToTemplateDetailsPage("Schedule Collaboration");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            controlsNewUIPage.setAutomaticallySetOnboardedEmployeesToActive(yesOrNo);
            configurationPage.publishNowTheTemplate();
            opsPortalLocationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.Console.getValue());
        }
    }
}
