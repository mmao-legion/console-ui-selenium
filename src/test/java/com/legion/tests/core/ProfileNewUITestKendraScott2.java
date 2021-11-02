package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.core.OpsPortalLocationsPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.junit.experimental.theories.Theories;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;

import static com.legion.utils.MyThreadLocal.*;
import static com.legion.utils.MyThreadLocal.getDriver;

public class ProfileNewUITestKendraScott2 extends TestBase {

    private static Map<String, String> propertyMap = SimpleUtils.getParameterMap();

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception {
        this.createDriver((String) params[0], "69", "Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify My Profile details by updating the information")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyMyProfileDetailsByUpdatingTheInformationAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            dashboardPage.clickOnSubMenuOnProfile("My Profile");
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();

            //T1838595 Validate the edit functionality on My Profile page.
            profileNewUIPage.validateTheEditFunctionalityOnMyProfile("Sammie", "Garden Rd 2-8", "Los_Angeles", "California", "214121");

            //T1838596 Validate the feature of Change password.
            profileNewUIPage.validateTheFeatureOfChangePassword(password);

            //T1838597 Validate the login with new password.
            String newPassword = profileNewUIPage.getNewPassword(password);
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();
            loginToLegionAndVerifyIsLoginDone(username, newPassword, location);
            dashboardPage.clickOnSubMenuOnProfile("My Profile");
            profileNewUIPage.validateTheFeatureOfChangePassword(newPassword);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Work Preference details by updating the information")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyWorkPreferenceDetailsByUpdatingTheInformationAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            dashboardPage.clickOnSubMenuOnProfile("My Work Preferences");
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            //SimpleUtils.assertOnFail("Profile Page not loaded Successfully!", profileNewUIPage.isProfilePageLoaded(), false);

            //T1838598 Validate the update of shift preferences.
            profileNewUIPage.validateTheUpdateOfShiftPreferences(true, false);

            //T1838599 Validate the update of Availability.
            profileNewUIPage.validateTheUpdateOfAvailability("Preferred", 1, "Right",
                    120, "This week only");
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Create New Time Off functionality")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateNewTimeOffFunctionalityAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            dashboardPage.clickOnSubMenuOnProfile("My Time Off");
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            SimpleUtils.assertOnFail("Profile Page not loaded Successfully!", profileNewUIPage.isProfilePageLoaded(), false);
            profileNewUIPage.cancelAllTimeOff();

            //T1838600 Validate the New time off functionality.
            profileNewUIPage.clickOnCreateTimeOffBtn();
            profileNewUIPage.reasonsOfLeaveOnNewTimOffRequest();
            String timeOffReasonLabelApprove = profileNewUIPage.selectRandomReasonOfLeaveOnNewTimeOffRequest();
            String timeOffExplanationApprove = (new Random()).nextInt(100) + "approve" + (new Random()).nextInt(100) + "approve" + (new Random()).nextInt(100);
            profileNewUIPage.createNewTimeOffRequestAndVerify(timeOffReasonLabelApprove, timeOffExplanationApprove);
            String timeOffStartDateApprove = getTimeOffStartTime();
            String timeOffEndDateApprove = getTimeOffEndTime();
            profileNewUIPage.clickOnCreateTimeOffBtn();
            String timeOffReasonLabelReject = profileNewUIPage.selectRandomReasonOfLeaveOnNewTimeOffRequest();
            String timeOffExplanationReject = (new Random()).nextInt(100) + "reject" + (new Random()).nextInt(100) + "reject" + (new Random()).nextInt(100);
            profileNewUIPage.createNewTimeOffRequestAndVerify(timeOffReasonLabelReject, timeOffExplanationReject);
            String timeOffStartDateReject = getTimeOffStartTime();
            String timeOffEndDateReject = getTimeOffEndTime();

            //T1838601 Validate the status of time off requests.
            String nickName = profileNewUIPage.getNickNameFromProfile();
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();
            /// Login as Store Manager
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            teamPage.openToDoPopupWindow();
            teamPage.approveOrRejectTimeOffRequestFromToDoList(nickName, timeOffStartDateApprove, timeOffEndDateApprove, TeamTestKendraScott2.timeOffRequestAction.Approve.getValue());
            teamPage.closeToDoPopupWindow();
            teamPage.openToDoPopupWindow();
            teamPage.approveOrRejectTimeOffRequestFromToDoList(nickName, timeOffStartDateReject, timeOffEndDateReject, TeamTestKendraScott2.timeOffRequestAction.Reject.getValue());
            teamPage.closeToDoPopupWindow();
            teamPage.searchAndSelectTeamMemberByName(nickName);
            String TeamMemberProfileSubSectionLabel = "Time Off";
            profileNewUIPage.selectProfilePageSubSectionByLabel(TeamMemberProfileSubSectionLabel);
            String requestStatusApprove = profileNewUIPage.getTimeOffRequestStatusByExplanationText(timeOffExplanationApprove);
            String requestStatusReject = profileNewUIPage.getTimeOffRequestStatusByExplanationText(timeOffExplanationReject);
            if (requestStatusApprove.toLowerCase().contains(TeamTestKendraScott2.timeOffRequestStatus.Approved.getValue().toLowerCase()))
                SimpleUtils.pass("Team Page: Time Off request Approved By Store Manager reflected on Team Page.");
            else
                SimpleUtils.fail("Team Page: Time Off request Approved By Store Manager not reflected on Team Page.", false);
            if (requestStatusReject.toLowerCase().contains(TeamTestKendraScott2.timeOffRequestStatus.Rejected.getValue().toLowerCase()))
                SimpleUtils.pass("Team Page: Time Off request Rejected By Store Manager reflected on Team Page.");
            else
                SimpleUtils.fail("Team Page: Time Off request Rejected By Store Manager not reflected on Team Page.", false);
            loginPage.logOut();
            /// Login as Team Member Again
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            dashboardPage.clickOnSubMenuOnProfile("My Time Off");
            requestStatusApprove = profileNewUIPage.getTimeOffRequestStatusByExplanationText(timeOffExplanationApprove);
            if (requestStatusApprove.toLowerCase().contains(TeamTestKendraScott2.timeOffRequestStatus.Approved.getValue().toLowerCase()))
                SimpleUtils.pass("Profile Page: New Time Off Request status is '" + requestStatusApprove
                        + "' after Store Manager Approved the request.");
            else
                SimpleUtils.fail("Profile Page: New Time Off Request status is '" + requestStatusApprove
                        + "' after Store Manager Approved the request.", false);
            requestStatusReject = profileNewUIPage.getTimeOffRequestStatusByExplanationText(timeOffExplanationReject);
            if (requestStatusReject.toLowerCase().contains(TeamTestKendraScott2.timeOffRequestStatus.Rejected.getValue().toLowerCase()))
                SimpleUtils.pass("Profile Page: New Time Off Request status is '" + requestStatusReject
                        + "' after Store Manager Rejected the request.");
            else
                SimpleUtils.fail("Profile Page: New Time Off Request status is '" + requestStatusReject
                        + "' after Store Manager Rejected the request.", false);

            //T1838602 Validate the functionality of time off cancellation.
            profileNewUIPage.validateTheFunctionalityOfTimeOffCancellation();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the content of new profile page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheContentOfNewProfilePageAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Select one team member to view profile
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            teamPage.selectATeamMemberToViewProfile();

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            //Verify User Profile Section is loaded
            profileNewUIPage.verifyUserProfileSectionIsLoaded();
            //Verify HR Profile Information Section is loaded
            profileNewUIPage.verifyHRProfileInformationSectionIsLoaded();
            //Verify Legion Information Section is loaded
            profileNewUIPage.verifyLegionInformationSectionIsLoaded();
            //Verify Actions Section is loaded
            profileNewUIPage.verifyActionSectionIsLoaded();
            //Verify the fields in User Profile Section are display correctly
            profileNewUIPage.verifyFieldsInUserProfileSection();
            //Verify the fields in HR Profile Information Section are display correctly
            profileNewUIPage.verifyFieldsInHRProfileInformationSection();
            //Verify the fields in Legion Information Section are display correctly
            profileNewUIPage.verifyFieldsInLegionInformationSection();
            //Verify the contents in Actions Section are display correctly
            profileNewUIPage.verifyContentsInActionsSection();
            //Verify Edit and Sync TM Info buttons are display correctly
            profileNewUIPage.verifyEditUserProfileButtonIsLoaded();
            profileNewUIPage.verifySyncTMInfoButtonIsLoaded();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the content of new profile page in TM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheContentOfNewProfilePageInTMViewAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Select one team member to view profile
            dashboardPage.clickOnSubMenuOnProfile("My Profile");

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            //Verify User Profile Section is loaded
            profileNewUIPage.verifyUserProfileSectionIsLoaded();
            //Verify HR Profile Information Section is loaded
            profileNewUIPage.verifyHRProfileInformationSectionIsLoaded();
            //Verify Legion Information Section is loaded
            profileNewUIPage.verifyLegionInformationSectionIsLoaded();
            //Verify Actions Section is loaded
            profileNewUIPage.verifyActionSectionIsLoaded();
            //Verify the fields in User Profile Section are display correctly
            profileNewUIPage.verifyFieldsInUserProfileSection();
            //Verify the fields in HR Profile Information Section are display correctly
            profileNewUIPage.verifyFieldsInHRProfileInformationSection();
            //Verify the fields in Legion Information Section are display correctly
            profileNewUIPage.verifyFieldsInLegionInformationSection();
//            //Verify the contents in Actions Section are display correctly
//            profileNewUIPage.verifyContentsInActionsSectionInTMView();
            //Verify Edit button is display correctly
            profileNewUIPage.verifyEditUserProfileButtonIsLoaded();

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the edit mode in New User Profile page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyEditModeInNewUserProfilePageAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            teamPage.selectATeamMemberToViewProfile();

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            SimpleUtils.assertOnFail("Profile Page not loaded Successfully!", profileNewUIPage.isProfilePageLoaded(), false);
            profileNewUIPage.clickOnEditUserProfilePencilIcon();
            HashMap<String,String> values = profileNewUIPage.getValuesOfFields();
            while (!profileNewUIPage.ifMatchEmailRegex(values.get("E-mail"))){
                profileNewUIPage.clickOnCancelUserProfileBtn();
                teamPage.goToTeam();
                teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
                teamPage.selectATeamMemberToViewProfile();
                SimpleUtils.assertOnFail("Profile Page not loaded Successfully!", profileNewUIPage.isProfilePageLoaded(), false);
                profileNewUIPage.clickOnEditUserProfilePencilIcon();
                values = profileNewUIPage.getValuesOfFields();
            }
            profileNewUIPage.verifyHRProfileSectionIsNotEditable();
            profileNewUIPage.verifyLegionInfoSectionIsNotEditable();
            List<String> testEmails = new ArrayList<>(Arrays.asList("123456", "@#$%%", "nora@legion.co"));
            profileNewUIPage.verifyTheEmailFormatInProfilePage(testEmails);
            profileNewUIPage.clickOnCancelUserProfileBtn();
            profileNewUIPage.clickOnEditUserProfilePencilIcon();
            HashMap<String,String> newValues = new HashMap<String, String>();
            newValues.put("address1","12_-*&(ag");
            newValues.put("address2","12_-*&(ag");
            newValues.put("City","12_-*&(ag");
            newValues.put("State","Texas");
            newValues.put("Zip Code","12_-*&(ag");
            newValues.put("Country","United States");
            newValues.put("First Name","12_-*&(ag");
            newValues.put("Last Name","12_-*&(ag");
            newValues.put("Nickname","12_-*&(ag");
            newValues.put("Phone","4567890097");
            newValues.put("E-mail",values.get("E-mail"));
            profileNewUIPage.updateAllFields(newValues);
            profileNewUIPage.clickOnSaveUserProfileBtn();
            profileNewUIPage.clickOnEditUserProfilePencilIcon();
            HashMap<String,String> valuesUpdated = profileNewUIPage.getValuesOfFields();
            SimpleUtils.assertOnFail("profile page fail to update!",newValues.equals(valuesUpdated),false);
            profileNewUIPage.updateAllFields(values);
            profileNewUIPage.verifyManageBadgeBtn();
            profileNewUIPage.verifySelectBadge();
            profileNewUIPage.cancelBadgeBtn();
            profileNewUIPage.verifyManageBadgeBtn();
            profileNewUIPage.verifySelectBadge();
            profileNewUIPage.saveBadgeBtn();
            profileNewUIPage.clickOnSaveUserProfileBtn();
            profileNewUIPage.clickOnEditUserProfilePencilIcon();
            profileNewUIPage.clickOnCancelUserProfileBtn();
        } catch (Exception e){
            SimpleUtils.fail(e.toString(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the edit mode in New User Profile page in TM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyEditModeInNewUserProfilePageAsTeamMember(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            profileNewUIPage.getNickNameFromProfile();
            String myProfileLabel = "My Profile";
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myProfileLabel);

            SimpleUtils.assertOnFail("Profile Page not loaded Successfully!", profileNewUIPage.isProfilePageLoaded(), false);
            profileNewUIPage.clickOnEditUserProfilePencilIcon();
            HashMap<String,String> values = profileNewUIPage.getValuesOfFields();
            profileNewUIPage.verifyHRProfileSectionIsNotEditable();
            profileNewUIPage.verifyLegionInfoSectionIsNotEditable();
            List<String> testEmails = new ArrayList<>(Arrays.asList("123456", "@#$%%", "nora@legion.co"));
            profileNewUIPage.verifyTheEmailFormatInProfilePage(testEmails);
            profileNewUIPage.clickOnCancelUserProfileBtn();
            profileNewUIPage.clickOnEditUserProfilePencilIcon();
            HashMap<String,String> newValues = new HashMap<String, String>();
            newValues.put("address1","12_-*&(ag");
            newValues.put("address2","12_-*&(ag");
            newValues.put("City","12_-*&(ag");
            newValues.put("State","Texas");
            newValues.put("Zip Code","12_-*&(ag");
            newValues.put("Country","United States");
            newValues.put("First Name","12_-*&(ag");
            newValues.put("Last Name","12_-*&(ag");
            newValues.put("Nickname","12_-*&(ag");
            newValues.put("Phone","4567890097");
            newValues.put("E-mail",values.get("E-mail"));
            profileNewUIPage.updateAllFields(newValues);
            profileNewUIPage.clickOnSaveUserProfileBtn();
            profileNewUIPage.clickOnEditUserProfilePencilIcon();
            HashMap<String,String> valuesUpdated = profileNewUIPage.getValuesOfFields();
            SimpleUtils.assertOnFail("profile page fail to update!",newValues.equals(valuesUpdated),false);
            profileNewUIPage.updateAllFields(values);
            SimpleUtils.assertOnFail("Manage badge button should not display!",!profileNewUIPage.verifyManageBadgeBtn(),false);
            profileNewUIPage.clickOnSaveUserProfileBtn();
            profileNewUIPage.clickOnEditUserProfilePencilIcon();
            profileNewUIPage.clickOnCancelUserProfileBtn();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "Validate TM and SM can see the dotted lines to check the availability changes for This Week Only")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
        public void verifyTMAndSMCanSeeTheDottedLinesToCheckTheAvailabilityChangesForThisWeekOnlyAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            // Set availability policy
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            controlsPage.gotoControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            SimpleUtils.assertOnFail("Scheduling policy page not loaded successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
            controlsNewUIPage.clickOnGlobalLocationButton();
            String isApprovalRequired = "Required for all changes";
            controlsNewUIPage.updateAvailabilityManagementIsApprovalRequired(isApprovalRequired);
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            //Login as TM
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String tmName = profileNewUIPage.getNickNameFromProfile();
            String myProfileLabel = "My Work Preferences";
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myProfileLabel);

            while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
                profileNewUIPage.clickNextWeek();
            }
            //Delete all availabilities of the first two editable weeks
            Thread.sleep(3000);
            profileNewUIPage.clickAvailabilityEditButton();
            profileNewUIPage.deleteAllAvailabilitiesForCurrentWeek();
            profileNewUIPage.saveMyAvailabilityEditMode("This week only");
            profileNewUIPage.clickNextWeek();
            profileNewUIPage.clickAvailabilityEditButton();
            profileNewUIPage.deleteAllAvailabilitiesForCurrentWeek();
            profileNewUIPage.saveMyAvailabilityEditMode("This week only");


            loginPage.logOut();

            //Login as SM and approve all the pending request of the TM
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.searchAndSelectTeamMemberByName(tmName);
            profileNewUIPage.selectProfilePageSubSectionByLabel("Work Preferences");
            Thread.sleep(5000);
            profileNewUIPage.approveAllPendingAvailabilityRequest();
            loginPage.logOut();

            //Login back to TM and add availabilities
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            profileNewUIPage.getNickNameFromProfile();
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myProfileLabel);
            while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
                profileNewUIPage.clickNextWeek();
            }
            profileNewUIPage.clickAvailabilityEditButton();
            profileNewUIPage.updatePreferredOrBusyHoursToAllDay(0, "Preferred");
            profileNewUIPage.updatePreferredOrBusyHoursToAllDay(1, "Busy");
            profileNewUIPage.saveMyAvailabilityEditMode("This week only");
            List<WebElement> changedPreferredAvailabilities = profileNewUIPage.getChangedPreferredAvailabilities();
            List<WebElement> changedBusyAvailabilities = profileNewUIPage.getChangedBusyAvailabilities();
            SimpleUtils.assertOnFail("The changed preferred availabilities fail to load! ",
                    changedPreferredAvailabilities.size()==1, false);
            SimpleUtils.assertOnFail("The changed busy availabilities fail to load! ",
                    changedBusyAvailabilities.size()==1, false);
            String availabilityWeek1 = profileNewUIPage.getAvailabilityWeek();

            profileNewUIPage.clickNextWeek();
            profileNewUIPage.clickAvailabilityEditButton();
            profileNewUIPage.updatePreferredOrBusyHoursToAllDay(0, "Preferred");
            profileNewUIPage.updatePreferredOrBusyHoursToAllDay(1, "Busy");
            profileNewUIPage.saveMyAvailabilityEditMode("This week only");

            //Check the dotted line
            changedPreferredAvailabilities = profileNewUIPage.getChangedPreferredAvailabilities();
            changedBusyAvailabilities = profileNewUIPage.getChangedBusyAvailabilities();
            SimpleUtils.assertOnFail("The changed preferred availabilities fail to load! ",
                    changedPreferredAvailabilities.size()==1, false);
            SimpleUtils.assertOnFail("The changed busy availabilities fail to load! ",
                    changedBusyAvailabilities.size()==1, false);
            String availabilityWeek2 = profileNewUIPage.getAvailabilityWeek();
            loginPage.logOut();

            //Login as SM and check the dotted line
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            teamPage.goToTeam();
            teamPage.searchAndSelectTeamMemberByName(tmName);
            profileNewUIPage.selectProfilePageSubSectionByLabel("Work Preferences");
            while (!profileNewUIPage.getAvailabilityWeek().equalsIgnoreCase(availabilityWeek1)){
                profileNewUIPage.clickNextWeek();
            }
            changedPreferredAvailabilities = profileNewUIPage.getChangedPreferredAvailabilities();
            changedBusyAvailabilities = profileNewUIPage.getChangedBusyAvailabilities();
            SimpleUtils.assertOnFail("The changed preferred availabilities fail to load! ",
                    changedPreferredAvailabilities.size()==1, false);
            SimpleUtils.assertOnFail("The changed busy availabilities fail to load! ",
                    changedBusyAvailabilities.size()==1, false);

            //Approve the availabilities, the dotted line will disappear
            profileNewUIPage.approveOrRejectSpecificPendingAvailabilityRequest(availabilityWeek1, "Approve");
            Thread.sleep(3000);
            changedPreferredAvailabilities = profileNewUIPage.getChangedPreferredAvailabilities();
            changedBusyAvailabilities = profileNewUIPage.getChangedBusyAvailabilities();
            SimpleUtils.assertOnFail("The changed preferred availabilities should not load! ",
                    changedPreferredAvailabilities.size()==0, false);
            SimpleUtils.assertOnFail("The changed busy availabilities should not load! ",
                    changedBusyAvailabilities.size()==0, false);

            profileNewUIPage.clickNextWeek();
            Thread.sleep(3000);
            changedPreferredAvailabilities = profileNewUIPage.getChangedPreferredAvailabilities();
            changedBusyAvailabilities = profileNewUIPage.getChangedBusyAvailabilities();
            SimpleUtils.assertOnFail("The changed preferred availabilities fail to load! ",
                    changedPreferredAvailabilities.size()==1, false);
            SimpleUtils.assertOnFail("The changed busy availabilities fail to load! ",
                    changedBusyAvailabilities.size()==1, false);

            //Reject the availabilities, the dotted line will disappear
            profileNewUIPage.approveOrRejectSpecificPendingAvailabilityRequest(availabilityWeek2, "Reject");
            Thread.sleep(3000);
            changedPreferredAvailabilities = profileNewUIPage.getChangedPreferredAvailabilities();
            changedBusyAvailabilities = profileNewUIPage.getChangedBusyAvailabilities();
            SimpleUtils.assertOnFail("The changed preferred availabilities should not load! ",
                    changedPreferredAvailabilities.size()==0, false);
            SimpleUtils.assertOnFail("The changed busy availabilities should not load! ",
                    changedBusyAvailabilities.size()==0, false);
            loginPage.logOut();

            //Login back to TM and check the dotted lines are disappear
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());

            profileNewUIPage.getNickNameFromProfile();
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myProfileLabel);
            while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
                profileNewUIPage.clickNextWeek();
            }
            changedPreferredAvailabilities = profileNewUIPage.getChangedPreferredAvailabilities();
            changedBusyAvailabilities = profileNewUIPage.getChangedBusyAvailabilities();
            SimpleUtils.assertOnFail("The changed preferred availabilities should not load! ",
                    changedPreferredAvailabilities.size()==0, false);
            SimpleUtils.assertOnFail("The changed busy availabilities should not load! ",
                    changedBusyAvailabilities.size()==0, false);

            profileNewUIPage.clickNextWeek();
            changedPreferredAvailabilities = profileNewUIPage.getChangedPreferredAvailabilities();
            changedBusyAvailabilities = profileNewUIPage.getChangedBusyAvailabilities();
            SimpleUtils.assertOnFail("The changed preferred availabilities should not load! ",
                    changedPreferredAvailabilities.size()==0, false);
            SimpleUtils.assertOnFail("The changed busy availabilities should not load! ",
                    changedBusyAvailabilities.size()==0, false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "Validate TM and SM can see the dotted lines to check the availability changes for Repeat Forward and dotted will disappear after SM approve request")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheDottedLinesOnAvailabilityForRepeatForwardAndApproveAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            String action = "Approve";
            verifyTMAndSMCanSeeTheDottedLinesToCheckTheAvailabilityChangesForRepeatForward(action);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "Validate TM and SM can see the dotted lines to check the availability changes for Repeat Forward and dotted will disappear after SM reject request")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheDottedLinesOnAvailabilityForRepeatForwardAndRejectAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            String action = "Reject";
            verifyTMAndSMCanSeeTheDottedLinesToCheckTheAvailabilityChangesForRepeatForward(action);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    private void verifyTMAndSMCanSeeTheDottedLinesToCheckTheAvailabilityChangesForRepeatForward(String action) throws Exception {
        //Login as TM
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String tmName = profileNewUIPage.getNickNameFromProfile();
        String myProfileLabel = "My Work Preferences";
        profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myProfileLabel);

        while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
            profileNewUIPage.clickNextWeek();
        }
        //Delete all availabilities of the first editable week
        Thread.sleep(3000);
        profileNewUIPage.clickAvailabilityEditButton();
        profileNewUIPage.deleteAllAvailabilitiesForCurrentWeek();
        profileNewUIPage.saveMyAvailabilityEditMode("Repeat Forward");

        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();

        //Login as SM and approve all the pending request of the TM
        loginAsDifferentRole(AccessRoles.StoreManager.getValue());
        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        teamPage.goToTeam();
        teamPage.searchAndSelectTeamMemberByName(tmName);
        profileNewUIPage.selectProfilePageSubSectionByLabel("Work Preferences");
        Thread.sleep(5000);
        profileNewUIPage.approveAllPendingAvailabilityRequest();
        loginPage.logOut();

        //Login back to TM and add availabilities
        loginAsDifferentRole(AccessRoles.TeamMember.getValue());
        profileNewUIPage.getNickNameFromProfile();
        profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myProfileLabel);
        while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
            profileNewUIPage.clickNextWeek();
        }
        profileNewUIPage.clickAvailabilityEditButton();
        profileNewUIPage.updatePreferredOrBusyHoursToAllDay(0, "Preferred");
        profileNewUIPage.updatePreferredOrBusyHoursToAllDay(1, "Busy");
        profileNewUIPage.saveMyAvailabilityEditMode("Repeat Forward");
        List<WebElement> changedPreferredAvailabilities = profileNewUIPage.getChangedPreferredAvailabilities();
        List<WebElement> changedBusyAvailabilities = profileNewUIPage.getChangedBusyAvailabilities();
        SimpleUtils.assertOnFail("The changed preferred availabilities fail to load! ",
                changedPreferredAvailabilities.size()==1, false);
        SimpleUtils.assertOnFail("The changed busy availabilities fail to load! ",
                changedBusyAvailabilities.size()==1, false);
        String availabilityWeek1 = profileNewUIPage.getAvailabilityWeek();
        //Check the dotted line on next week
        profileNewUIPage.clickNextWeek();
        changedPreferredAvailabilities = profileNewUIPage.getChangedPreferredAvailabilities();
        changedBusyAvailabilities = profileNewUIPage.getChangedBusyAvailabilities();
        SimpleUtils.assertOnFail("The changed preferred availabilities fail to load! ",
                changedPreferredAvailabilities.size()==1, false);
        SimpleUtils.assertOnFail("The changed busy availabilities fail to load! ",
                changedBusyAvailabilities.size()==1, false);
        loginPage.logOut();

        //Login as SM and check the dotted line
        loginAsDifferentRole(AccessRoles.StoreManager.getValue());
        teamPage.goToTeam();
        teamPage.searchAndSelectTeamMemberByName(tmName);
        profileNewUIPage.selectProfilePageSubSectionByLabel("Work Preferences");
        while (!profileNewUIPage.getAvailabilityWeek().equalsIgnoreCase(availabilityWeek1)){
            profileNewUIPage.clickNextWeek();
        }
        changedPreferredAvailabilities = profileNewUIPage.getChangedPreferredAvailabilities();
        changedBusyAvailabilities = profileNewUIPage.getChangedBusyAvailabilities();
        SimpleUtils.assertOnFail("The changed preferred availabilities fail to load! ",
                changedPreferredAvailabilities.size()==1, false);
        SimpleUtils.assertOnFail("The changed busy availabilities fail to load! ",
                changedBusyAvailabilities.size()==1, false);
        profileNewUIPage.clickNextWeek();
        Thread.sleep(3000);
        changedPreferredAvailabilities = profileNewUIPage.getChangedPreferredAvailabilities();
        changedBusyAvailabilities = profileNewUIPage.getChangedBusyAvailabilities();
        SimpleUtils.assertOnFail("The changed preferred availabilities fail to load! ",
                changedPreferredAvailabilities.size()==1, false);
        SimpleUtils.assertOnFail("The changed busy availabilities fail to load! ",
                changedBusyAvailabilities.size()==1, false);

        //Approve the availabilities, the dotted line will disappear
        String onwardWeekInfo = availabilityWeek1.split("-")[0]+ "-" + "ONWARD";
        profileNewUIPage.approveOrRejectSpecificPendingAvailabilityRequest(onwardWeekInfo, action);
        Thread.sleep(3000);
        changedPreferredAvailabilities = profileNewUIPage.getChangedPreferredAvailabilities();
        changedBusyAvailabilities = profileNewUIPage.getChangedBusyAvailabilities();
        SimpleUtils.assertOnFail("The changed preferred availabilities should not load! ",
                changedPreferredAvailabilities.size()==0, false);
        SimpleUtils.assertOnFail("The changed busy availabilities should not load! ",
                changedBusyAvailabilities.size()==0, false);

        profileNewUIPage.clickPreviousWeek();
        changedPreferredAvailabilities = profileNewUIPage.getChangedPreferredAvailabilities();
        changedBusyAvailabilities = profileNewUIPage.getChangedBusyAvailabilities();
        SimpleUtils.assertOnFail("The changed preferred availabilities should not load! ",
                changedPreferredAvailabilities.size()==0, false);
        SimpleUtils.assertOnFail("The changed busy availabilities should not load! ",
                changedBusyAvailabilities.size()==0, false);
        loginPage.logOut();
        //Login back to TM and check the dotted lines are disappear
        loginAsDifferentRole(AccessRoles.TeamMember.getValue());

        profileNewUIPage.getNickNameFromProfile();
        profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myProfileLabel);
        while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
            profileNewUIPage.clickNextWeek();
        }
        changedPreferredAvailabilities = profileNewUIPage.getChangedPreferredAvailabilities();
        changedBusyAvailabilities = profileNewUIPage.getChangedBusyAvailabilities();
        SimpleUtils.assertOnFail("The changed preferred availabilities should not load! ",
                changedPreferredAvailabilities.size()==0, false);
        SimpleUtils.assertOnFail("The changed busy availabilities should not load! ",
                changedBusyAvailabilities.size()==0, false);

        profileNewUIPage.clickNextWeek();
        changedPreferredAvailabilities = profileNewUIPage.getChangedPreferredAvailabilities();
        changedBusyAvailabilities = profileNewUIPage.getChangedBusyAvailabilities();
        SimpleUtils.assertOnFail("The changed preferred availabilities should not load! ",
                changedPreferredAvailabilities.size()==0, false);
        SimpleUtils.assertOnFail("The changed busy availabilities should not load! ",
                changedBusyAvailabilities.size()==0, false);
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "Validate the dotted lins will disappear for TM and SM after TM cancel the availability")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheDottedLinesWillDisappearForTMAndSMAfterTMCancelAvailabilityAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            //Login as TM
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String tmName = profileNewUIPage.getNickNameFromProfile();
            String myProfileLabel = "My Work Preferences";
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myProfileLabel);
            while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
                profileNewUIPage.clickNextWeek();
            }
            //Get the editable week info
            String availabilityWeek = profileNewUIPage.getAvailabilityWeek();
            profileNewUIPage.cancelAllPendingAvailabilityRequest();
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            //Login as SM and approve all the pending request of the TM
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.searchAndSelectTeamMemberByName(tmName);
            profileNewUIPage.selectProfilePageSubSectionByLabel("Work Preferences");
            profileNewUIPage.approveAllPendingAvailabilityRequest();
            while (!profileNewUIPage.getAvailabilityWeek().equalsIgnoreCase(availabilityWeek)){
                profileNewUIPage.clickNextWeek();
            }
            Thread.sleep(5000);
            profileNewUIPage.clickAvailabilityEditButton();
            profileNewUIPage.deleteAllAvailabilitiesForCurrentWeek();
            profileNewUIPage.saveMyAvailabilityEditMode("Repeat Forward");
            loginPage.logOut();

            //Login back to TM and add availabilities
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            profileNewUIPage.getNickNameFromProfile();
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myProfileLabel);
            while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
                profileNewUIPage.clickNextWeek();
            }
            profileNewUIPage.clickAvailabilityEditButton();
            profileNewUIPage.updatePreferredOrBusyHoursToAllDay(0, "Preferred");
            profileNewUIPage.updatePreferredOrBusyHoursToAllDay(1, "Busy");
            profileNewUIPage.saveMyAvailabilityEditMode("Repeat Forward");
            List<WebElement> changedPreferredAvailabilities = profileNewUIPage.getChangedPreferredAvailabilities();
            List<WebElement> changedBusyAvailabilities = profileNewUIPage.getChangedBusyAvailabilities();
            SimpleUtils.assertOnFail("The changed preferred availabilities fail to load! ",
                    changedPreferredAvailabilities.size()==1, false);
            SimpleUtils.assertOnFail("The changed busy availabilities fail to load! ",
                    changedBusyAvailabilities.size()==1, false);
            String availabilityWeek1 = profileNewUIPage.getAvailabilityWeek();
            loginPage.logOut();

            //Login as SM and check the dotted line
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            teamPage.goToTeam();
            teamPage.searchAndSelectTeamMemberByName(tmName);
            profileNewUIPage.selectProfilePageSubSectionByLabel("Work Preferences");
            while (!profileNewUIPage.getAvailabilityWeek().equalsIgnoreCase(availabilityWeek1)){
                profileNewUIPage.clickNextWeek();
            }
            changedPreferredAvailabilities = profileNewUIPage.getChangedPreferredAvailabilities();
            changedBusyAvailabilities = profileNewUIPage.getChangedBusyAvailabilities();
            SimpleUtils.assertOnFail("The changed preferred availabilities fail to load! ",
                    changedPreferredAvailabilities.size()==1, false);
            SimpleUtils.assertOnFail("The changed busy availabilities fail to load! ",
                    changedBusyAvailabilities.size()==1, false);

            loginPage.logOut();
            //Login back to TM and check the dotted lines are disappear
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());

            profileNewUIPage.getNickNameFromProfile();
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myProfileLabel);
            while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
                profileNewUIPage.clickNextWeek();
            }
            String onwardWeekInfo = availabilityWeek1.split("-")[0]+ "-" + "ONWARD";
            profileNewUIPage.cancelSpecificPendingAvailabilityRequest(onwardWeekInfo);
            changedPreferredAvailabilities = profileNewUIPage.getChangedPreferredAvailabilities();
            changedBusyAvailabilities = profileNewUIPage.getChangedBusyAvailabilities();
            Thread.sleep(3000);
            SimpleUtils.assertOnFail("The changed preferred availabilities should not load! ",
                    changedPreferredAvailabilities.size()==0, false);
            SimpleUtils.assertOnFail("The changed busy availabilities should not load! ",
                    changedBusyAvailabilities.size()==0, false);

            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            teamPage.goToTeam();
            teamPage.searchAndSelectTeamMemberByName(tmName);
            profileNewUIPage.selectProfilePageSubSectionByLabel("Work Preferences");
            while (!profileNewUIPage.getAvailabilityWeek().equalsIgnoreCase(availabilityWeek1)){
                profileNewUIPage.clickNextWeek();
            }
            changedPreferredAvailabilities = profileNewUIPage.getChangedPreferredAvailabilities();
            changedBusyAvailabilities = profileNewUIPage.getChangedBusyAvailabilities();
            SimpleUtils.assertOnFail("The changed preferred availabilities should not load! ",
                    changedPreferredAvailabilities.size()==0, false);
            SimpleUtils.assertOnFail("The changed busy availabilities should not load! ",
                    changedBusyAvailabilities.size()==0, false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the manager cannot edit the availability of the TM that has pending request")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyManagerCannotEditTheAvailabilityWhenTMHasPendingRequestAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            //Login as TM
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String tmName = profileNewUIPage.getNickNameFromProfile();
            String myProfileLabel = "My Work Preferences";
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myProfileLabel);
            int i = 0;
            while (!profileNewUIPage.getCountForStatus("pending").trim().equalsIgnoreCase("0") && i <20) {
                profileNewUIPage.cancelAllPendingAvailabilityRequest();
                Thread.sleep(2000);
                i++;
            }
            //Create multiple availability requests
            while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
                profileNewUIPage.clickNextWeek();
            }
            String availabilityWeek1 = profileNewUIPage.getAvailabilityWeek();
            profileNewUIPage.clickAvailabilityEditButton();
            profileNewUIPage.updatePreferredOrBusyHoursToAllDay(0, "Preferred");
            profileNewUIPage.saveMyAvailabilityEditMode("This week only");
            profileNewUIPage.clickNextWeek();
            String availabilityWeek2 = profileNewUIPage.getAvailabilityWeek();
            profileNewUIPage.clickAvailabilityEditButton();
            profileNewUIPage.updatePreferredOrBusyHoursToAllDay(0, "Preferred");
            profileNewUIPage.saveMyAvailabilityEditMode("This week only");
            //Get the editable week info

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            //Login as SM
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.searchAndSelectTeamMemberByName(tmName);
            profileNewUIPage.selectProfilePageSubSectionByLabel("Work Preferences");
            profileNewUIPage.clickAvailabilityEditButton();
            SimpleUtils.assertOnFail("The availability cannot be edited alert fail to load! ",
                    profileNewUIPage.isAlertDialogLoaded()
                            && profileNewUIPage.getMessageFromAlertDialog().equalsIgnoreCase("This Team Member current has open Availability Requests. Please approve or deny the requests before making any changes"), false);
            profileNewUIPage.clickOnOKBtnOnAlert();

            //Approve or reject one request, then try to edit the TM's availability
            profileNewUIPage.approveOrRejectSpecificPendingAvailabilityRequest( availabilityWeek1, "Approve");
            Thread.sleep(3000);
            profileNewUIPage.clickAvailabilityEditButton();
            SimpleUtils.assertOnFail("The availability cannot be edited alert fail to load! ",
                    profileNewUIPage.isAlertDialogLoaded()
                            && profileNewUIPage.getMessageFromAlertDialog().equalsIgnoreCase("This Team Member current has an open Availability Request. Please approve or deny the request before making any changes"), false);
            profileNewUIPage.clickOnOKBtnOnAlert();

            //Approve or reject all requests, then try to edit the TM's availability
            profileNewUIPage.approveOrRejectSpecificPendingAvailabilityRequest(availabilityWeek2, "Reject");
            Thread.sleep(3000);
            profileNewUIPage.clickAvailabilityEditButton();
            SimpleUtils.assertOnFail("The availability cannot be edited alert fail to load! ",
                    !profileNewUIPage.isAlertDialogLoaded(), false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
