package com.legion.tests.core;

import com.legion.pages.ControlsNewUIPage;
import com.legion.pages.ControlsPage;
import com.legion.pages.DashboardPage;
import com.legion.pages.OpsPortaPageFactories.LocationsPage;
import com.legion.pages.TeamPage;
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

public class BadgeTest extends TestBase {
    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{
        try {
            this.createDriver((String) params[0], "83", "Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Ting")
    @Enterprise(name = "")
    @TestName(description = "Should be able to see the badge change while switch between the shift smart card view")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheBadgeChangeInShiftSmartCardViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Prepare for the 2 TMs one is with badge the other is not
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            String teamMemberA = "Holly Trainer";
            String teamMemberB = "Chloe Keates";
            teamPage.goToTeam();
            String tmA = teamPage.searchAndSelectTeamMemberByName(teamMemberA);
            teamPage.clickTheTMByNameAndEdit(tmA);
            teamPage.deleteBadges();
            teamPage.verifyTheFunctionOfEditBadges();
            if (teamPage.isWithBadges()) {
                SimpleUtils.pass("Bage for TM " + tmA + " has been added!");
            }

            teamPage.goToTeam();
            String tmB = teamPage.searchAndSelectTeamMemberByName(teamMemberB);
            teamPage.clickTheTMByNameAndEdit(tmB);
            teamPage.deleteBadges();
            if (!teamPage.isWithBadges()) {
                SimpleUtils.pass("Bage for TM " + tmB + " has been removed!");
            }

            // TODO: Generate target schedule
            // TODO: Create 2 open shifts and assigned to each of the 2 TMs
            // TODO: Check the badge in the shift smart card while switching between the to shifts
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}