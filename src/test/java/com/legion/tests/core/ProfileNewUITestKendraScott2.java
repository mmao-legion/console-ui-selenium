package com.legion.tests.core;

import com.legion.pages.*;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.legion.utils.MyThreadLocal.getTimeOffEndTime;
import static com.legion.utils.MyThreadLocal.getTimeOffStartTime;

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
    @TestName(description = "Verify My Profile details by updating the information when login through TM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyMyProfileDetailsByUpdatingTheInformationAsTeamMember2(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        dashboardPage.clickOnSubMenuOnProfile("My Profile");
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();

        //T1838595 Validate the edit functionality on My Profile page.
        boolean isEditingProfileSection = profileNewUIPage.isEditingProfileSectionLoaded();
        SimpleUtils.assertOnFail("Profile Page:  Editing Profile Section not loaded.", isEditingProfileSection, true);
        profileNewUIPage.validateTheEditFunctionalityOnMyProfile("Sammie", "Garden Rd 2-8", "Los_Angeles", "CA", "214121");

        //T1838596 Validate the feature of Change password.
        profileNewUIPage.validateTheFeatureOfChangePassword(password);

        //T1838597 Validate the login with new password.
        String newPassword = profileNewUIPage.getNewPassword(password);
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();
        loginToLegionAndVerifyIsLoginDone(username, newPassword, location);
        dashboardPage.clickOnSubMenuOnProfile("My Profile");
        profileNewUIPage.validateTheFeatureOfChangePassword(newPassword);
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify My Profile details by updating the information when login through TM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyWorkPreferenceDetailsByUpdatingTheInformationAsTeamMember(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), true);
        dashboardPage.clickOnSubMenuOnProfile("My Work Preferences");
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        SimpleUtils.assertOnFail("Profile Page not loaded Successfully!", profileNewUIPage.isProfilePageLoaded(), true);

        //T1838598 Validate the update of shift preferences.
        profileNewUIPage.validateTheUpdateOfShiftPreferences(true, false);

        //T1838599 Validate the update of Availability.
        profileNewUIPage.validateTheUpdateOfAvailability("Preferred", 1, "Right",
                120, "This week only");
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify My Profile details by updating the information when login through TM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateNewTimeOffFunctionalityAsTeamMember(String browser, String username, String password, String location) throws Exception {
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
        String fileName = "UsersCredentials.json";
        fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise") + fileName;
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
                , String.valueOf(storeManagerCredentials[0][2]));
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
            SimpleUtils.fail("Team Page: Time Off request Approved By Store Manager not reflected on Team Page.", true);
        if (requestStatusReject.toLowerCase().contains(TeamTestKendraScott2.timeOffRequestStatus.Rejected.getValue().toLowerCase()))
            SimpleUtils.pass("Team Page: Time Off request Rejected By Store Manager reflected on Team Page.");
        else
            SimpleUtils.fail("Team Page: Time Off request Rejected By Store Manager not reflected on Team Page.", true);
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
                    + "' after Store Manager Approved the request.", true);
        requestStatusReject = profileNewUIPage.getTimeOffRequestStatusByExplanationText(timeOffExplanationReject);
        if (requestStatusReject.toLowerCase().contains(TeamTestKendraScott2.timeOffRequestStatus.Rejected.getValue().toLowerCase()))
            SimpleUtils.pass("Profile Page: New Time Off Request status is '" + requestStatusReject
                    + "' after Store Manager Rejected the request.");
        else
            SimpleUtils.fail("Profile Page: New Time Off Request status is '" + requestStatusReject
                    + "' after Store Manager Rejected the request.", true);

        //T1838602 Validate the functionality of time off cancellation.
        profileNewUIPage.validateTheFunctionalityOfTimeOffCancellation();
    }
}
