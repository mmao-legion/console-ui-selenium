package com.legion.tests.core.opusermanagement;

import com.legion.api.toggle.ToggleAPI;
import com.legion.api.toggle.Toggles;
import com.legion.pages.OpsPortaPageFactories.*;
import com.legion.pages.core.OpCommons.RightHeaderBarPage;
import com.legion.pages.core.OpsPortal.OpsPortalConfigurationPage;
import com.legion.pages.core.OpsPortal.OpsPortalSettingsAndAssociationPage;
import com.legion.tests.TestBase;
import com.legion.utils.SimpleUtils;
import org.testng.annotations.Test;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


import java.lang.reflect.Method;

public class DynamicGroupV2Test extends TestBase {
    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception {
        //ToggleAPI.enableToggle(Toggles.DynamicGroupV2.getValue(), "stoneman@legion.co", "admin11.a");
        //ToggleAPI.enableToggle(Toggles.MinorRulesTemplate.getValue(), "stoneman@legion.co", "admin11.a");
        this.createDriver((String) params[0], "69", "Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
        RightHeaderBarPage modelSwitchPage = new RightHeaderBarPage();
        modelSwitchPage.switchToOpsPortal();
    }
/*
    @AfterMethod
    public void tearDown() throws Exception {
        //ToggleAPI.disableToggle(Toggles.DynamicGroupV2.getValue(), "stoneman@legion.co", "admin11.a");
        //ToggleAPI.disableToggle(Toggles.MinorRulesTemplate.getValue(), "stoneman@legion.co", "admin11.a");
    }
*/
    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate the name for dynamic user group")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheNameForDynamicEmployeeGroupAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
        SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
        configurationPage.goToConfigurationPage();
        configurationPage.clickOnConfigurationCrad(OpsPortalConfigurationPage.configurationLandingPageTemplateCards.MinorsRules.getValue());
        settingsAndAssociationPage.goToTemplateListOrSettings("setting");
        settingsAndAssociationPage.verifyTitleOnTheSettingsPage("Employee");
        settingsAndAssociationPage.goToTemplateListOrSettings("template list");
        configurationPage.clickOnTemplateName("");
        settingsAndAssociationPage.goToAssociationTabOnTemplateDetailsPage();
        settingsAndAssociationPage.verifyTitleOnTheSAssociationPage("Employee");
        configurationPage.clickOnTemplateDetailTab();
        configurationPage.clickOnEditButtonOnTemplateDetailsPage();
        settingsAndAssociationPage.goToAssociationTabOnTemplateDetailsPage();
        settingsAndAssociationPage.clickOnEditBtnForDynamicGroupOnAssociationPage();
        settingsAndAssociationPage.verifyTitleOnTheSAssociationPage("Employee");
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate the name for dynamic location group")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheNameForDynamicLocationGroupAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
        SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
        configurationPage.goToConfigurationPage();
        configurationPage.clickOnConfigurationCrad(OpsPortalConfigurationPage.configurationLandingPageTemplateCards.OperatingHours.getValue());
        //Verify Dynamic group name on the setting page.
        settingsAndAssociationPage.goToTemplateListOrSettings("setting");
        settingsAndAssociationPage.verifyTitleOnTheSettingsPage("Location");
        settingsAndAssociationPage.goToTemplateListOrSettings("template list");
        //Verify Dynamic group name on the association page.
        configurationPage.clickOnTemplateName("");
        settingsAndAssociationPage.goToAssociationTabOnTemplateDetailsPage();
        settingsAndAssociationPage.verifyTitleOnTheSAssociationPage("Location");
        configurationPage.clickOnTemplateDetailTab();
        configurationPage.clickOnEditButtonOnTemplateDetailsPage();
        //Verify Dynamic group name on the association page---edit mode.
        settingsAndAssociationPage.goToAssociationTabOnTemplateDetailsPage();
        settingsAndAssociationPage.clickOnEditBtnForDynamicGroupOnAssociationPage();
        settingsAndAssociationPage.verifyTitleOnTheSAssociationPage("Location");

        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        //go to locations tab
        locationsPage.clickOnLocationsTab();
        //check dynamic group item
        locationsPage.iCanSeeDynamicGroupItemInLocationsTab();
        //go to dynamic group
        locationsPage.goToDynamicGroup();
        locationsPage.clickOnAddBtnForSharingDynamicLocationGroup();
        locationsPage.verifyTitleForWorkforceSharingLocationGroup();
        locationsPage.clickOnCancelBtnOnSharingDynamicLocationGroupWindow();

        //Verify Dynamic group name on the Job page.
        JobsPage jobsPage = pageFactory.createOpsPortalJobsPage();
        jobsPage.iCanEnterJobsTab();
        jobsPage.iCanEnterCreateNewJobPage();
        jobsPage.selectJobType("Release Schedule");
        jobsPage.selectWeekForJobToTakePlace();
        jobsPage.clickOkBtnInCreateNewJobPage();
        jobsPage.addLocationBtnIsClickable();
        jobsPage.verifyDynamicGroupName();
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate the Dynamic Group tile is removed from User Management")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheDynamicEmployeeGroupTileIsRemovedAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        UserManagementPage userManagementPage = pageFactory.createOpsPortalUserManagementPage();
        userManagementPage.clickOnUserManagementTab();
        SimpleUtils.assertOnFail("Dynamic User Group tile should not be showing up!", !userManagementPage.iCanSeeDynamicGroupItemTileInUserManagementTab(), false);
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate creation for \"Dynamic Employee Group\"")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheCreationForDynamicEmployeeGroupAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
        SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
        configurationPage.goToConfigurationPage();
        configurationPage.clickOnConfigurationCrad(OpsPortalConfigurationPage.configurationLandingPageTemplateCards.MinorsRules.getValue());

        //Create new template.
        String templateName = "test"+String.valueOf(System.currentTimeMillis());
        configurationPage.createNewTemplate(templateName);
        configurationPage.clickOnTemplateName(templateName);
        configurationPage.clickOnEditButtonOnTemplateDetailsPage();
        //Go to the Association page to create a dynamic employee group and save it.
        settingsAndAssociationPage.goToAssociationTabOnTemplateDetailsPage();
        settingsAndAssociationPage.clickOnAddBtnForDynamicGroupOnAssociationPage();
        settingsAndAssociationPage.inputGroupNameForDynamicGroupOnAssociationPage("group-test");
        settingsAndAssociationPage.deleteAllCriteriaOnTheAssociationPageIfExist();
        settingsAndAssociationPage.selectAnOptionForCriteria(OpsPortalSettingsAndAssociationPage.requiredFieldsForEmployeeGroup.Minor.getValue(), "IN", "15");
        settingsAndAssociationPage.clickOnDoneBtnForDynamicGroupOnAssociationPage();
        configurationPage.clickOnTemplateDetailTab();
        configurationPage.publishNowTemplate();

        //Go into that template again to update the group.
        //configurationPage.searchTemplate(templateName);
        configurationPage.clickOnTemplateName(templateName);
        configurationPage.clickOnEditButtonOnTemplateDetailsPage();
        settingsAndAssociationPage.goToAssociationTabOnTemplateDetailsPage();
        settingsAndAssociationPage.clickOnEditBtnForDynamicGroupOnAssociationPage();
        settingsAndAssociationPage.selectAnOptionForCriteria(OpsPortalSettingsAndAssociationPage.requiredFieldsForEmployeeGroup.Minor.getValue(), "IN", "14");
        settingsAndAssociationPage.clickOnDoneBtnForDynamicGroupOnAssociationPage();
        configurationPage.clickOnTemplateDetailTab();
    }
}
