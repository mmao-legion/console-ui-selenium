package com.legion.tests.core;

import com.legion.pages.DashboardPage;
import com.legion.pages.ProfileNewUIPage;
import com.legion.pages.TeamPage;
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

public class CinemarkMinorTest extends TestBase {

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception {
        this.createDriver((String) params[0], "69", "Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "OP_Enterprise")
    @TestName(description = "Verify Minor filed is displayed on TM Profile")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyMinorFieldIsDisplayedOnTMProfileAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        teamPage.goToTeam();
        teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
        teamPage.selectATeamMemberToViewProfile();
        teamPage.isProfilePageLoaded();

        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        if (profileNewUIPage.isMINORYesOrNo())
            profileNewUIPage.verifyMINORField(true);
        else
            profileNewUIPage.verifyMINORField(false);
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "OP_Enterprise")
    @TestName(description = "Verify SM will have ability to select a calendar for the minor from a dropdown menu within the profile")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifySMCanSelectACalendarForMinorAsStoreManager(String browser, String username, String password, String location) throws Exception {
        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();

        // Search out a TM who is a minor
        do {
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            teamPage.selectATeamMemberToViewProfile();
            teamPage.isProfilePageLoaded();
        }
        while (!profileNewUIPage.isMINORYesOrNo());

        // Verify SM can select a calendar from a dropdown menu within the profile
        profileNewUIPage.verifySMCanSelectACalendarForMinor();
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "OP_Enterprise")
    @TestName(description = "Verify the default value of a minor without a calendar")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDefaultValueOfAMinorWithoutACalendarAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();

        // Search out a TM who is a minor
        do {
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            teamPage.selectATeamMemberToViewProfile();
            teamPage.isProfilePageLoaded();
        }
        while (!profileNewUIPage.isMINORYesOrNo());

        // Get minor name from user profile page
        String minorName = profileNewUIPage.getUserProfileName();
        String firstName = minorName.contains(" ")? minorName.split(" ")[0] : minorName;
        String lastName = minorName.contains(" ")? minorName.split(" ")[1] : minorName;

        // Edit, select "None" from the calendar dropdown menu, and save the profile
        profileNewUIPage.selectAGivenCalendarForMinor("None");


    }

    //Haya
    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "OP_Enterprise")
    @TestName(description = "Verify turn off minor rule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTurnOffMinorRuleAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);


    }
}