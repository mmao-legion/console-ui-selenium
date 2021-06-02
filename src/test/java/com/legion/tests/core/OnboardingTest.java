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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.legion.utils.MyThreadLocal.*;

public class OnboardingTest extends TestBase {

    private static HashMap<String, String> testDataMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/OnboardingTestData.json");
    private String nonSSOEnterprise = propertyMap.get("KendraScott2_Enterprise");
    private String ssoEnterprise = propertyMap.get("Dgch_Enterprise");
    private String currentLocation = "";
    private String invitationCode = "";
    private String newPassword = testDataMap.get("Password");
    private String continueLabel = "Continue";
    private String nextLabel = "Next";
    boolean hasCompanyMobilePolicyURL = false;

    private static Map<String, String> newTMDetails = JsonUtil.getPropertiesFromJsonFile("src/test/resources/AddANewTeamMember.json");
    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{
        try {
            this.createDriver((String) params[0], "83", "Window");
            visitPage(testMethod);
            verifyLoginToTheSpecificLocation((String) params[1], (String) params[2], (String) params[3]);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the onboarding flow for New hire and status changed to Active (Non-SSO)")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheOnboardingFlowForNewHireAndStatusChangeToActiveAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            verifyOnboardingFlow("Yes", username, password);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the onboarding flow for New hire and status changed to Onboarded (Non-SSO)")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheOnboardingFlowForNewHireAndStatusChangeToOnboardedAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            verifyOnboardingFlow("No", username, password);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the onboarding flow when the rehired user has an account and status change to Onboarded (Non-SSO)")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheOnboardingFlowForRehireAndStatusChangeToOnboardedAsInternalAdmin(String browser, String username, String password, String location){
        try {
            verifyOnboardingFlow("No", username, password);
            verifyOnboardingFlowForRehire("No", username, password);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the onboarding flow when the rehired user has an account and status change to Active (Non-SSO)")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheOnboardingFlowForRehireAndStatusChangeToActiveAsInternalAdmin(String browser, String username, String password, String location){
        try {
            verifyOnboardingFlow("Yes", username, password);
            verifyOnboardingFlowForRehire("Yes", username, password);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the onboarding flow for New hire (Non-SSO & OP based)")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheOnboardingFlowForNewHireOnOPEnabledEnvAsInternalAdmin(String browser, String username, String password, String location){
        try {
            verifyOnboardingFlow("Yes", username, password);
            verifyOnboardingFlowForRehire("Yes", username, password);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    private void verifyOnboardingFlow (String yesOrNo, String username, String password) throws Exception {
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        OnboardingPage onboardingPage = pageFactory.createOnboardingPage();
        // Set "Automatically set onboarded employees to active?"
        if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
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

        //Check if there is the url set under Controls -> Compliance -> Company Mobile Policy
        controlsPage.gotoControlsPage();
        SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
        controlsNewUIPage.clickOnControlsComplianceSection();
        SimpleUtils.assertOnFail("Controls: Compliance page not loaded Successfully!", controlsNewUIPage.isControlsComplianceLoaded(), false);
        controlsNewUIPage.clickOnGlobalLocationButton();
        hasCompanyMobilePolicyURL = controlsNewUIPage.hasCompanyMobilePolicyURLOrNot();

        //Create new TM
        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        teamPage.goToTeam();
        teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
        teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
        teamPage.isProfilePageLoaded();
        String firstName = teamPage.addANewTeamMemberToInvite(newTMDetails);
        teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();

        //If testing on rc, set "Preview User" for this user
        if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
            controlsPage.gotoControlsPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsUsersAndRolesSection();
            controlsNewUIPage.searchAndSelectTeamMemberByName(firstName);
            List<String> selectAccessRoles = new ArrayList<>();
            selectAccessRoles.add("Preview User");
            controlsNewUIPage.selectAccessRoles(selectAccessRoles);
        }

        teamPage.goToTeam();
        teamPage.searchAndSelectTeamMemberByName(firstName);
        //click Invite to Legion button
        profileNewUIPage.userProfileInviteTeamMember();

        //Get invitation code
        profileNewUIPage.clickOnShowOrHideInvitationCodeButton(true);
        invitationCode = profileNewUIPage.getInvitationCode();

        String lastName = MyThreadLocal.getLastNameForNewHire();
        onboardingPage.openOnboardingPage(invitationCode, firstName, false, "KendraScott2");
        onboardingPage.verifyTheContentOfCreateAccountPage(firstName, invitationCode);
        onboardingPage.verifyLastName(lastName);
        SimpleUtils.assertOnFail("Create Account page failed to load after verifying last name!", onboardingPage.isCreateAccountPageLoadedAfterVerifyingLastName(), false);
        onboardingPage.createAccountForNewHire(newPassword);
        // Verify "Is this email correct?" dialog pops up
        onboardingPage.verifyIsEmailCorrectDialogPopup();
        // Verify the functionality of YES button
        onboardingPage.clickYesBtnOnIsEmailCorrectDialog();
        // Verify Important Notice from your Employer page will load
        // Verify the content on Important Notice from your Employer page
        if (hasCompanyMobilePolicyURL) {
            onboardingPage.verifyImportantNoticeFromYourEmployerPageLoaded();
            onboardingPage.clickOnButtonByLabel(continueLabel);
        }
        // Verify the content on Verify Profile page
        onboardingPage.validateVerifyProfilePageLoaded();
        onboardingPage.clickOnButtonByLabel(nextLabel);

        //Verify the content on Set Availability page
        onboardingPage.verifySetAvailabilityPageLoaded();
        onboardingPage.clickOnNextButtonOnSetAvailabilityPage();
        onboardingPage.verifyThatsItPageLoaded();
        onboardingPage.clickOnDoneOnThatsItPage();
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        //Check user status from profile page
        profileNewUIPage.clickOnUserProfileImage();
        profileNewUIPage.selectProfileSubPageByLabelOnProfileImage("My Profile");
        String status = profileNewUIPage.getStatusOnProfilePage();

        if(yesOrNo.equalsIgnoreCase("Yes")){
        SimpleUtils.assertOnFail("The user status display incorrectly! It should display as: Active, but actual display as "+ status,
                status.equalsIgnoreCase("Active"), false);
        } else
            SimpleUtils.assertOnFail("The user status display incorrectly! It should display as: Onboarded, but actual display as "+ status,
                    status.equalsIgnoreCase("Onboarded"), false);

        // Logout and login as internal admin to terminate the user
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();
        verifyLoginToTheSpecificLocation(username, password, currentLocation);
        teamPage.goToTeam();
        teamPage.searchAndSelectTeamMemberByName(firstName);
        teamPage.terminateTheTeamMember(true);
    }

    private void verifyOnboardingFlowForRehire(String yesOrNo, String username, String password) throws Exception {
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        OnboardingPage onboardingPage = pageFactory.createOnboardingPage();
        teamPage.goToTeam();
        teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
        teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
        teamPage.isProfilePageLoaded();
        String firstName = teamPage.addANewTeamMemberToInvite(newTMDetails);
        teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();

        //If testing on rc, set "Preview User" for this user
        if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
            controlsPage.gotoControlsPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsUsersAndRolesSection();
            controlsNewUIPage.searchAndSelectTeamMemberByName(firstName);
            List<String> selectAccessRoles = new ArrayList<>();
            selectAccessRoles.add("Preview User");
            controlsNewUIPage.selectAccessRoles(selectAccessRoles);
        }

        teamPage.goToTeam();
        teamPage.searchAndSelectTeamMemberByName(firstName);
        // Verify manager can invite the user to use Legion, click Re invite to Legion button
        profileNewUIPage.userProfileInviteTeamMember();
        //Get invitation code
        profileNewUIPage.clickOnShowOrHideInvitationCodeButton(true);
        invitationCode = profileNewUIPage.getInvitationCode();

        String lastName = MyThreadLocal.getLastNameForNewHire();
        onboardingPage.openOnboardingPage(invitationCode, firstName, true, getEnterprise());

        // Verify the content of "Log in to your account" page
        onboardingPage.verifyTheContentOfLoginToYourAccountPage();
        // Verify the rehire can login to the previous legion credential successfully
        onboardingPage.verifyRehireLoginToPreviousCredential(getEmailAccount(), newPassword);

        // Verify the content on Important Notice from your Employer page
        if (hasCompanyMobilePolicyURL) {
            onboardingPage.verifyImportantNoticeFromYourEmployerPageLoaded();
            onboardingPage.clickOnButtonByLabel(continueLabel);
        }
        // Verify the content on Verify Profile page
        onboardingPage.validateVerifyProfilePageLoaded();
        onboardingPage.clickOnButtonByLabel(nextLabel);

        //Verify the content on Set Availability page
        onboardingPage.verifySetAvailabilityPageLoaded();
        onboardingPage.clickOnNextButtonOnSetAvailabilityPage();
        onboardingPage.verifyThatsItPageLoaded();
        onboardingPage.clickOnDoneOnThatsItPage();
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.verifyNewTermsOfServicePopUp();

        //Check user status from profile page
        profileNewUIPage.clickOnUserProfileImage();
        profileNewUIPage.selectProfileSubPageByLabelOnProfileImage("My Profile");
        String status = profileNewUIPage.getStatusOnProfilePage();

        if(yesOrNo.equalsIgnoreCase("Yes")){
            SimpleUtils.assertOnFail("The user status display incorrectly! It should display as: Active, but actual display as "+ status,
                    status.equalsIgnoreCase("Active"), false);
        } else
            SimpleUtils.assertOnFail("The user status display incorrectly! It should display as: Onboarded, but actual display as "+ status,
                    status.equalsIgnoreCase("Onboarded"), false);

        // Logout and login as internal admin to terminate the user
        loginPage.logOut();
        verifyLoginToTheSpecificLocation(username, password, currentLocation);
        teamPage.goToTeam();
        teamPage.searchAndSelectTeamMemberByName(firstName);
        teamPage.terminateTheTeamMember(true);
    }

    private void verifyLoginToTheSpecificLocation(String username, String password, String location) throws Exception {
        loginToLegionAndVerifyIsLoginDoneWithoutUpdateUpperfield(username, password, location);
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
    }
}