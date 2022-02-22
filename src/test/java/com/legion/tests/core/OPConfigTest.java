package com.legion.tests.core;

import com.legion.pages.OpsPortaPageFactories.ConfigurationPage;
import com.legion.pages.OpsPortaPageFactories.LocationsPage;
import com.legion.pages.core.OpsPortal.OpsPortalConfigurationPage;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OPConfigTest extends TestBase {

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

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate Minors Rules template")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyMinorRulesTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            //check description and the items in the Minors Rules tile
            configurationPage.verifyMinorRulesTileIsLoaded();

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate new tile for Minors Rules")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyNewTileForMinorRulesAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.verifyMinorRulesTileIsLoaded();
            configurationPage.clickOnConfigurationCrad("Minors Rules");
            configurationPage.isTemplateListPageShow();
            String templateName = "TemplateName-ForAuto";
            configurationPage.deleteTemplate(templateName);

            //create new template with the 'New Template' button, there are 2 tabs: Detail and Association
            configurationPage.createNewTemplate(templateName);
            configurationPage.clickOnSpecifyTemplateName(templateName, "edit");

            // Check 2 sections: Minor Schedule by Week and Minor Schedule by Day in detail page
            SimpleUtils.assertOnFail("The Minors Sections should load on minor template page! ",
                    configurationPage.checkIfMinorSectionsLoaded(), false);
            configurationPage.clickOnBackButton();
            configurationPage.deleteTemplate(templateName);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate no minors rules in Compliance template")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyNoMinorRulesInComplianceTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad("Compliance");
            configurationPage.isTemplateListPageShow();
            String templateName = "TemplateName-ForAuto";
            configurationPage.deleteTemplate(templateName);
            configurationPage.createNewTemplate(templateName);
            configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
            //There are no setting for minors rules on Compliance template page
            SimpleUtils.assertOnFail("The Minors Sections should not on Compliance template page! ",
                    !configurationPage.checkIfMinorSectionsLoaded(), false);
            configurationPage.clickOnBackButton();
            configurationPage.deleteTemplate(templateName);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify new tile for Meal and Rest")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyMealAndRestTileAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            // Verify the title of the tile
            // Verify the description of the tile
            // Verify the sub description list of the tile
            List<String> expectedContent = new ArrayList<>();
            expectedContent.add("Meal and Rest Breaks");
            expectedContent.add("Number and length of meal breaks per shift");
            expectedContent.add("Number and length of rest breaks per shift");
            expectedContent.add("Paid / non paid meal break definitions");
            configurationPage.verifyTheContentOnSpecificCard(
                    OpsPortalConfigurationPage.configurationLandingPageTemplateCards.MealAndRest.getValue(), expectedContent);
            // Verify the "Meal and Rest" tile is clickable
            configurationPage.clickOnConfigurationCrad(OpsPortalConfigurationPage.configurationLandingPageTemplateCards.MealAndRest.getValue());
            // Verify the content on Meal and Rest page
            configurationPage.isTemplateListPageShow();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the functionality on Meal and Rest Details page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheMealAndRestTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String meal = "Meal";
            String rest = "Rest";
            String templateName = TestBase.getCurrentTime().substring(0, 8);
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();

            configurationPage.clickOnConfigurationCrad(OpsPortalConfigurationPage.configurationLandingPageTemplateCards.MealAndRest.getValue());
            // Verify the content on Meal and Rest page
            configurationPage.isTemplateListPageShow();
            configurationPage.createNewTemplate(templateName);
            configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            // Verify the content on Meal Breaks section
            configurationPage.verifyTheContentOnMealBreaksSection();
            // Verify Yes/No button is clickable on Meal Breaks section
            configurationPage.selectYesOrNoOnMealOrRest(meal, "Yes");
            configurationPage.selectYesOrNoOnMealOrRest(meal, "No");
            // Verify + Add button is clickable on Meal Breaks section
            configurationPage.clickOnAddButtonOnMealOrRestSection(meal);
            // Verify the functionality of 7 input boxes on Meal Breaks section
            configurationPage.verifyTheFunctionalityOfInputsInMealOrRest(meal);
            // Verify X button is clickable on Meal Breaks section
            configurationPage.verifyXbuttonOnMealOrRest(meal);
            // Verify the content on Rest Breaks section
            configurationPage.verifyTheContentOnRestBreaksSection();
            // Verify Yes/No button is clickable on Rest Breaks section
            configurationPage.selectYesOrNoOnMealOrRest(rest, "Yes");
            configurationPage.selectYesOrNoOnMealOrRest(rest, "No");
            // Verify + Add button is clickable on Rest Breaks section
            configurationPage.clickOnAddButtonOnMealOrRestSection(rest);
            // Verify the functionality of 3 input boxes on Rest Breaks section
            configurationPage.verifyTheFunctionalityOfInputsInMealOrRest(rest);
            // Verify X button is clickable on Rest Breaks section
            configurationPage.verifyXbuttonOnMealOrRest(rest);
            // Clear data
            configurationPage.saveADraftTemplate();
            configurationPage.archiveOrDeleteTemplate(templateName);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }
}
