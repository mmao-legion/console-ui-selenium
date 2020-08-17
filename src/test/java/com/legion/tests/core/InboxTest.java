package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;

public class InboxTest extends TestBase {

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{
        this.createDriver((String)params[0],"83","Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
    }

    //Added by Nora
    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "verify reports will be available for export")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyGFEReportsAreAbleToExportAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        // Go to Controls -> Compliance, turn on the GFE
        ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        controlsPage.gotoControlsPage();
        SimpleUtils.assertOnFail("Controls page not loaded Successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
        controlsNewUIPage.clickOnControlsComplianceSection();
        SimpleUtils.assertOnFail("Controls: Compliance page not loaded Successfully!", controlsNewUIPage.isControlsComplianceLoaded(), false);
        controlsNewUIPage.turnGFEToggleOnOrOff(true);

        // Go to Analytics page
        AnalyticsPage analyticsPage = pageFactory.createConsoleAnalyticsPage();
        analyticsPage.clickOnAnalyticsConsoleMenu();
        SimpleUtils.assertOnFail("Analytics Page not loaded Successfully!", analyticsPage.isReportsPageLoaded(), false);

        analyticsPage.switchAllLocationsOrSingleLocation(false);
        String gfe = "Good Faith Estimate";
        if (analyticsPage.isSpecificReportLoaded(gfe)) {
            analyticsPage.mouseHoverAndExportReportByName(gfe);
        } else {
            SimpleUtils.fail("Analytics: " + gfe + " not loaded Successfully!", false);
        }
    }

    //Added by Julie
    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the content of operating hours and the first day of week are correct")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheContentOfOperationHrsAndTheFirstDayOfWeekAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        InboxPage inboxPage = pageFactory.createConsoleInboxPage();

        // Make sure that GFE is turned on
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
        controlsPage.gotoControlsPage();
        SimpleUtils.assertOnFail("Controls Page failed to load", controlsNewUIPage.isControlsPageLoaded(), false);
        controlsNewUIPage.clickOnControlsComplianceSection();
        SimpleUtils.assertOnFail("Compliance Card failed to load", controlsNewUIPage.isCompliancePageLoaded(), false);
        controlsNewUIPage.turnGFEToggleOnOrOff(true);

        // Get Regular hours from Controls-> Working hours -> Regular
        String workingHoursType = "Regular";
        controlsPage.gotoControlsPage();
        SimpleUtils.assertOnFail("Controls page failed to load", controlsNewUIPage.isControlsPageLoaded(), false);
        controlsNewUIPage.clickOnControlsWorkingHoursCard();
        SimpleUtils.assertOnFail("Working Hours Card failed to load", controlsNewUIPage.isControlsWorkingHoursLoaded(), false);
        controlsNewUIPage.clickOnWorkHoursTypeByText(workingHoursType);
        LinkedHashMap<String, List<String>> regularHoursFromControls = controlsNewUIPage.getRegularWorkingHours();

        // Get the first day of week that schedule begins from Controls -> Scheduling Policies -> Schedules
        controlsPage.gotoControlsPage();
        SimpleUtils.assertOnFail("Controls page failed to load", controlsNewUIPage.isControlsPageLoaded(), false);
        controlsNewUIPage.clickOnControlsSchedulingPolicies();
        SimpleUtils.assertOnFail("Schedule Policy Card failed to load", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
        String firstDayOfWeekFromControls = controlsNewUIPage.getSchedulingPoliciesFirstDayOfWeek();

        // Create a GFE announcement to verify its content of operation hours and the first day of week
        inboxPage.clickOnInboxConsoleMenuItem();
        inboxPage.createGFEAnnouncement();
        String theFirstDayOfWeekFromGFE = inboxPage.getGFEFirstDayOfWeek();
        LinkedHashMap<String, List<String>> GFEWorkingHours = inboxPage.getGFEWorkingHours();

        // Compare the first day of week
        SimpleUtils.report("The first day of week from controls is: " + firstDayOfWeekFromControls);
        SimpleUtils.report("The first day of week from GFE is: " + theFirstDayOfWeekFromGFE);
        if (firstDayOfWeekFromControls.toUpperCase().contains(theFirstDayOfWeekFromGFE))
            SimpleUtils.pass("Inbox page: The first day of the week in GFE is consistent with the setting in Control -> Scheduling Policies -> What day of the week does your schedule begin?");
        else
            SimpleUtils.fail("Inbox page: The first day of the week in GFE is inconsistent with the setting in Control -> Scheduling Policies -> What day of the week does your schedule begin?",false);

        // Compare the operation days and hours
        if (inboxPage.compareGFEWorkingHrsWithRegularWorkingHrs(GFEWorkingHours, regularHoursFromControls))
            SimpleUtils.pass("Inbox page: Operation days and hours in GFE is consistent with the setting in Controls -> Working Hours -> Regular");
        else
            SimpleUtils.fail("Inbox page: Operation days and hours in GFE is inconsistent with the setting in Controls -> Working Hours -> Regular",false);
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify VSL info shows or not")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyVSLInfoShowsOrNotAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        InboxPage inboxPage = pageFactory.createConsoleInboxPage();

        // Make sure that GFE is turned on
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
        controlsPage.gotoControlsPage();
        SimpleUtils.assertOnFail("Controls Page failed to load", controlsNewUIPage.isControlsPageLoaded(), false);
        controlsNewUIPage.clickOnControlsComplianceSection();
        SimpleUtils.assertOnFail("Compliance Card failed to load", controlsNewUIPage.isCompliancePageLoaded(), false);
        controlsNewUIPage.turnGFEToggleOnOrOff(true);

        // Turn on VSL to verify VSL info
        controlsNewUIPage.turnVSLToggleOnOrOff(true);
        inboxPage.clickOnInboxConsoleMenuItem();
        inboxPage.createGFEAnnouncement();
        inboxPage.verifyVSLInfo(true);

        // Turn off VSL to verify VSL info
        controlsPage.gotoControlsPage();
        SimpleUtils.assertOnFail("Controls Page failed to load", controlsNewUIPage.isControlsPageLoaded(), false);
        controlsNewUIPage.clickOnControlsComplianceSection();
        SimpleUtils.assertOnFail("Compliance Card failed to load", controlsNewUIPage.isCompliancePageLoaded(), false);
        controlsNewUIPage.turnVSLToggleOnOrOff(false);
        inboxPage.clickOnInboxConsoleMenuItem();
        inboxPage.createGFEAnnouncement();
        inboxPage.verifyVSLInfo(false);
    }

    //Added by Marym
    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify turn off GFE ")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTurnOffGFEAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        controlsNewUIPage.clickOnControlsConsoleMenu();
        SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
        controlsNewUIPage.clickOnControlsComplianceSection();
        SimpleUtils.assertOnFail("Compliance page not loaded successfully!", controlsNewUIPage.isCompliancePageLoaded(), false);

        controlsNewUIPage.turnGFEToggleOnOrOff(false);

        InboxPage inboxPage = pageFactory.createConsoleInboxPage();
        inboxPage.clickOnInboxConsoleMenuItem();
        inboxPage.checkCreateAnnouncementPageWithGFETurnOnOrTurnOff(false);

        // Go to Analytics page
        AnalyticsPage analyticsPage = pageFactory.createConsoleAnalyticsPage();
        analyticsPage.clickOnAnalyticsConsoleMenu();
        SimpleUtils.assertOnFail("Analytics Page not loaded Successfully!", analyticsPage.isReportsPageLoaded(), false);

        String gfe = "Good Faith Estimate";
        if (!analyticsPage.isSpecificReportLoaded(gfe)){
            SimpleUtils.pass("Analytics: GFE report is not displayed in all location tab , because GFE is turned off");
        }
        else{
            SimpleUtils.fail("Analytics: Analytics: GFE report should not display in all location tab, because GFE is turned off", true);
        }
        analyticsPage.switchAllLocationsOrSingleLocation(false);
        if (!analyticsPage.isSpecificReportLoaded(gfe)){
            SimpleUtils.pass("Analytics: GFE report is not displayed in location: " + location +" tab, because GFE is turned off");
        }
        else{
            SimpleUtils.fail("Analytics: Analytics: GFE report should not display in location : " + location +" tab, because GFE is turned off", true);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify turn on GFE ")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTurnOnGFEAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        controlsNewUIPage.clickOnControlsConsoleMenu();
        SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
        controlsNewUIPage.clickOnControlsComplianceSection();
        SimpleUtils.assertOnFail("Compliance page not loaded successfully!", controlsNewUIPage.isCompliancePageLoaded(), false);

        controlsNewUIPage.turnGFEToggleOnOrOff(true);

        InboxPage inboxPage = pageFactory.createConsoleInboxPage();
        inboxPage.clickOnInboxConsoleMenuItem();

        inboxPage.checkCreateAnnouncementPageWithGFETurnOnOrTurnOff(true);

        // Go to Analytics page
        AnalyticsPage analyticsPage = pageFactory.createConsoleAnalyticsPage();
        analyticsPage.clickOnAnalyticsConsoleMenu();
        SimpleUtils.assertOnFail("Analytics Page not loaded Successfully!", analyticsPage.isReportsPageLoaded(), false);

        String gfe = "Good Faith Estimate";
        if (analyticsPage.isSpecificReportLoaded(gfe)){
            SimpleUtils.pass("Analytics: GFE report loaded successfully in all location tab");
        }
        else{
            SimpleUtils.fail("Analytics: Analytics: GFE report failed to load in all location tab", true);
        }
        analyticsPage.switchAllLocationsOrSingleLocation(false);
        if (analyticsPage.isSpecificReportLoaded(gfe)){
            SimpleUtils.pass("Analytics: GFE report loaded successfully in location: " +location+ " tab");
        }
        else{
            SimpleUtils.fail("Analytics: Analytics: GFE report failed to load in location: " + location+ "tab", true);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the template loaded when selecting GFE ")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheTemplateLoadedWhenSelectingGFEAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        controlsNewUIPage.clickOnControlsConsoleMenu();
        SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
        controlsNewUIPage.clickOnControlsComplianceSection();
        SimpleUtils.assertOnFail("Compliance page not loaded successfully!", controlsNewUIPage.isCompliancePageLoaded(), false);

        controlsNewUIPage.turnGFEToggleOnOrOff(true);

        InboxPage inboxPage = pageFactory.createConsoleInboxPage();
        inboxPage.clickOnInboxConsoleMenuItem();

        inboxPage.createGFEAnnouncement();
        inboxPage.checkCreateGFEPage();
    }

    //Added by Haya
    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the content of GFE is consistent between SM and TM")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyContentOfGFEAsTeamMember(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
/*        ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
        controlsPage.gotoControlsPage();
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
        controlsNewUIPage.clickOnControlsComplianceSection();

        //turn on GFE toggle
        controlsNewUIPage.turnGFEToggleOnOrOff(true);
        controlsNewUIPage.turnVSLToggleOnOrOff(false);
*/
        //login as TM to get nickName

        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String nickName = profileNewUIPage.getNickNameFromProfile();
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();

        //go to Inbox page create GFE announcement.
        String fileName = "UsersCredentials.json";
        fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] teamMemberCredentials = userCredentials.get("StoreManager");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                , String.valueOf(teamMemberCredentials[0][2]));
        InboxPage inboxPage = pageFactory.createConsoleInboxPage();
        inboxPage.clickOnInboxConsoleMenuItem();
        inboxPage.createGFEAnnouncement();
        inboxPage.sendToTM(nickName);
    }
}
