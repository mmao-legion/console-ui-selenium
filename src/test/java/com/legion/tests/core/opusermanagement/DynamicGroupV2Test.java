package com.legion.tests.core.opusermanagement;

import com.legion.api.toggle.ToggleAPI;
import com.legion.api.toggle.Toggles;
import com.legion.pages.OpsPortaPageFactories.*;
import com.legion.pages.core.OpCommons.RightHeaderBarPage;
import com.legion.pages.core.OpsPortal.OpsPortalConfigurationPage;
import com.legion.pages.core.OpsPortal.OpsPortalSettingsAndAssociationPage;
import com.legion.tests.TestBase;
import com.legion.tests.core.CinemarkMinorTest;
import com.legion.utils.SimpleUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DynamicGroupV2Test extends TestBase {

    @BeforeClass
    public void enableDynamicGroupV2Toggle() throws Exception{
        //ToggleAPI.enableToggle(Toggles.DynamicGroupV2.getValue(), "stoneman@legion.co", "admin11.a");
        ToggleAPI.enableToggle(Toggles.MinorRulesTemplate.getValue(), "stoneman@legion.co", "admin11.a");
    }


    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception {
        this.createDriver((String) params[0], "69", "Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
        RightHeaderBarPage modelSwitchPage = new RightHeaderBarPage();
        modelSwitchPage.switchToOpsPortal();
    }
/*
    @AfterClass
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
        configurationPage.clickOnConfigurationCrad(OpsPortalConfigurationPage.configurationLandingPageTemplateCards.Compliance.getValue());
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

        //delete all template.
        configurationPage.archiveOrDeleteAllTemplates();

        settingsAndAssociationPage.goToTemplateListOrSettings("setting");
        List<String> ExpectedFieldsFromSettingsTab = new ArrayList<>();
        ExpectedFieldsFromSettingsTab.add("Minor");
        settingsAndAssociationPage.setupRequiredFields(ExpectedFieldsFromSettingsTab);
        settingsAndAssociationPage.goToTemplateListOrSettings("template list");

        //Create new template.
        String templateName = "AutoTest"+String.valueOf(System.currentTimeMillis());
        configurationPage.createNewTemplate(templateName);
        configurationPage.clickOnTemplateName(templateName);
        configurationPage.clickOnEditButtonOnTemplateDetailsPage();
        //Go to the Association page to create a dynamic employee group and save it.
        settingsAndAssociationPage.goToAssociationTabOnTemplateDetailsPage();
        settingsAndAssociationPage.clickOnAddBtnForDynamicGroupOnAssociationPage();
        settingsAndAssociationPage.inputGroupNameForDynamicGroupOnAssociationPage("group-test4");
        settingsAndAssociationPage.deleteAllCriteriaOnTheAssociationPageIfExist();
        settingsAndAssociationPage.selectAnOptionForCriteria(OpsPortalSettingsAndAssociationPage.requiredFieldsForEmployeeGroup.Minor.getValue(), "IN", "15");
        settingsAndAssociationPage.clickOnDoneBtnForDynamicGroupOnAssociationPage();
        configurationPage.clickOnTemplateDetailTab();
        configurationPage.publishNowTemplate();

        //Go into that template again to update the group.
        configurationPage.clickOnTemplateName(templateName);
        configurationPage.clickOnEditButtonOnTemplateDetailsPage();
        settingsAndAssociationPage.goToAssociationTabOnTemplateDetailsPage();
        settingsAndAssociationPage.clickOnEditBtnForDynamicGroupOnAssociationPage();
        settingsAndAssociationPage.selectAnOptionForCriteria(OpsPortalSettingsAndAssociationPage.requiredFieldsForEmployeeGroup.Minor.getValue(), "IN", "14");
        settingsAndAssociationPage.clickOnDoneBtnForDynamicGroupOnAssociationPage();
        configurationPage.clickOnTemplateDetailTab();
        configurationPage.publishNowTemplate();

        //delete the template.
        configurationPage.clickOnSpecifyTemplateName(templateName, "view");
        configurationPage.archiveOrDeleteTemplate(templateName);
    }



//--------------Tests for Settings tab------------ Begin-------------
    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate settings tab fields")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheSettingsFieldsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
        SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
        configurationPage.goToConfigurationPage();
        //======verify Setting tab for dynamic employee group========
        configurationPage.clickOnConfigurationCrad(OpsPortalConfigurationPage.configurationLandingPageTemplateCards.MinorsRules.getValue());
        //Go to the Settings tab.
        settingsAndAssociationPage.goToTemplateListOrSettings("setting");
        List<String> ExpectedFieldsFromSettingsTab = new ArrayList<>();
        ExpectedFieldsFromSettingsTab.add("Work Role");
        ExpectedFieldsFromSettingsTab.add("Country");
        ExpectedFieldsFromSettingsTab.add("State");
        ExpectedFieldsFromSettingsTab.add("City");
        ExpectedFieldsFromSettingsTab.add("Location Name");
        ExpectedFieldsFromSettingsTab.add("Location Id");
        ExpectedFieldsFromSettingsTab.add("Employment Type");
        ExpectedFieldsFromSettingsTab.add("Employment Status");
        ExpectedFieldsFromSettingsTab.add("Minor");
        ExpectedFieldsFromSettingsTab.add("Exempt");
        ExpectedFieldsFromSettingsTab.add("Badge");
        ExpectedFieldsFromSettingsTab.add("Job Title");
        List<String> ActualFieldsFromSettingsTab = settingsAndAssociationPage.getFieldListFromSettingsTab();
        SimpleUtils.assertOnFail("Fields are not all expected!", ExpectedFieldsFromSettingsTab.containsAll(ActualFieldsFromSettingsTab), false);
        settingsAndAssociationPage.setupRequiredFields(ExpectedFieldsFromSettingsTab);

        //======verify Setting tab for dynamic location group========
        configurationPage.goToConfigurationPage();
        //======verify Setting tab for dynamic employee group========
        configurationPage.clickOnConfigurationCrad(OpsPortalConfigurationPage.configurationLandingPageTemplateCards.Compliance.getValue());
        //Go to the Settings tab.
        settingsAndAssociationPage.goToTemplateListOrSettings("setting");
        ExpectedFieldsFromSettingsTab.clear();
        ExpectedFieldsFromSettingsTab.add("Config Type");
        ExpectedFieldsFromSettingsTab.add("District");
        ExpectedFieldsFromSettingsTab.add("Country");
        ExpectedFieldsFromSettingsTab.add("State");
        ExpectedFieldsFromSettingsTab.add("City");
        ExpectedFieldsFromSettingsTab.add("Location Name");
        ExpectedFieldsFromSettingsTab.add("Location Id");
        ExpectedFieldsFromSettingsTab.add("Location Type");
        ExpectedFieldsFromSettingsTab.add("UpperField");

        ActualFieldsFromSettingsTab = settingsAndAssociationPage.getFieldListFromSettingsTab();
        SimpleUtils.assertOnFail("Fields are not all expected!", ExpectedFieldsFromSettingsTab.containsAll(ActualFieldsFromSettingsTab), false);
        settingsAndAssociationPage.setupRequiredFields(ExpectedFieldsFromSettingsTab);
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate criteria selection and usage for dynamic employee group criteria")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCriteriaSelectionAndUsageForDynamicEmployeeGroupAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
        SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
        configurationPage.goToConfigurationPage();
        //======verify Setting tab for dynamic employee group========
        configurationPage.clickOnConfigurationCrad(OpsPortalConfigurationPage.configurationLandingPageTemplateCards.MinorsRules.getValue());
        //Go to the Settings tab.
        settingsAndAssociationPage.goToTemplateListOrSettings("setting");
        List<String> ExpectedFieldsFromSettingsTab = new ArrayList<>();
        ExpectedFieldsFromSettingsTab.add("Country");
        ExpectedFieldsFromSettingsTab.add("State");
        ExpectedFieldsFromSettingsTab.add("City");
        settingsAndAssociationPage.setupRequiredFields(ExpectedFieldsFromSettingsTab);
        settingsAndAssociationPage.goToTemplateListOrSettings("template list");
        //delete all template.
        configurationPage.archiveOrDeleteAllTemplates();

        //Create new template.
        String templateName = "AutoTest"+String.valueOf(System.currentTimeMillis());
        configurationPage.createNewTemplate(templateName);
        configurationPage.clickOnTemplateName(templateName);
        configurationPage.clickOnEditButtonOnTemplateDetailsPage();
        //Go to the Association page to create a dynamic employee group and save it.
        settingsAndAssociationPage.goToAssociationTabOnTemplateDetailsPage();
        settingsAndAssociationPage.clickOnAddBtnForDynamicGroupOnAssociationPage();
        settingsAndAssociationPage.inputGroupNameForDynamicGroupOnAssociationPage(templateName);
        settingsAndAssociationPage.deleteAllCriteriaOnTheAssociationPageIfExist();
        List<String> ActualFieldsFromTheAssociationPage = settingsAndAssociationPage.getCriteriaListFromTheAssociationPage();
        SimpleUtils.assertOnFail("Criteria on the Association page are not all expected!", ExpectedFieldsFromSettingsTab.containsAll(ActualFieldsFromTheAssociationPage), false);
        settingsAndAssociationPage.selectAnOptionForCriteria(OpsPortalSettingsAndAssociationPage.requiredFieldsForEmployeeGroup.Country.getValue(), "IN", "United States");
        settingsAndAssociationPage.selectAnOptionForCriteria(OpsPortalSettingsAndAssociationPage.requiredFieldsForEmployeeGroup.State.getValue(), "IN", "Texas");
        settingsAndAssociationPage.selectAnOptionForCriteria(OpsPortalSettingsAndAssociationPage.requiredFieldsForEmployeeGroup.City.getValue(), "IN", "ANY");
        String testResult = settingsAndAssociationPage.clickOnTestBtnAndGetResultString();
        SimpleUtils.assertOnFail("Test result is not coming up!", testResult != null, false);
        settingsAndAssociationPage.clickOnDoneBtnForDynamicGroupOnAssociationPage();
        configurationPage.clickOnBackBtnOnTheTemplateDetailPage();

        //delete the template.
        configurationPage.archiveOrDeleteTemplate(templateName);

        //Go to another type of template.
        configurationPage.goToConfigurationPage();
        configurationPage.clickOnConfigurationCrad(OpsPortalConfigurationPage.configurationLandingPageTemplateCards.MealAndRest.getValue());

        //Go to the Settings tab.
        settingsAndAssociationPage.goToTemplateListOrSettings("setting");
        List<String> ExpectedFieldsFromSettingsTabForAnotherTemplate = new ArrayList<>();
        ExpectedFieldsFromSettingsTabForAnotherTemplate.add("Minor");
        settingsAndAssociationPage.setupRequiredFields(ExpectedFieldsFromSettingsTabForAnotherTemplate);
        settingsAndAssociationPage.goToTemplateListOrSettings("template list");
        //delete all template.
        configurationPage.archiveOrDeleteAllTemplates();

        //Create new template.
        String templateName2 = "AutoTest"+String.valueOf(System.currentTimeMillis());
        configurationPage.createNewTemplate(templateName2);
        configurationPage.clickOnTemplateName(templateName2);
        configurationPage.clickOnEditButtonOnTemplateDetailsPage();
        //Go to the Association page to create a dynamic employee group and save it.
        settingsAndAssociationPage.goToAssociationTabOnTemplateDetailsPage();
        settingsAndAssociationPage.clickOnAddBtnForDynamicGroupOnAssociationPage();
        settingsAndAssociationPage.inputGroupNameForDynamicGroupOnAssociationPage(templateName2);
        settingsAndAssociationPage.deleteAllCriteriaOnTheAssociationPageIfExist();
        List<String> ActualFieldsFromTheAssociationPageForAnotherTemplate = settingsAndAssociationPage.getCriteriaListFromTheAssociationPage();
        SimpleUtils.assertOnFail("Criteria on the Association page are not all expected!", ExpectedFieldsFromSettingsTabForAnotherTemplate.containsAll(ActualFieldsFromTheAssociationPageForAnotherTemplate), false);
        configurationPage.clickOnBackBtnOnTheTemplateDetailPage();
        //delete the template.
        configurationPage.archiveOrDeleteTemplate(templateName2);
    }
//--------------Tests for Settings tab------------ End-------------

}
