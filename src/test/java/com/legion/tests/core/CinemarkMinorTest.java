package com.legion.tests.core;

import com.legion.pages.DashboardPage;
import com.legion.pages.ProfileNewUIPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.TeamPage;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
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
import java.util.List;

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
    @TestName(description = "Verify SM will have ability to select a calendar for the minor from a dropdown menu within the profile")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifySMCanSelectACalendarForMinorAsStoreManager(String browser, String username, String password, String location) throws Exception {
        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();

        teamPage.goToTeam();
        teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
        teamPage.selectATeamMemberToViewProfile();
        teamPage.isProfilePageLoaded();

        // Verify Minor filed is displayed on TM Profile
        if (profileNewUIPage.isMINORYesOrNo())
            profileNewUIPage.verifyMINORField(true);
        else
            profileNewUIPage.verifyMINORField(false);

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

        // Go to Schedule page, get the current holiday information if have
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

        schedulePage.navigateToNextWeek();
        schedulePage.navigateToNextWeek();
        boolean isWeekGenerated = schedulePage.isWeekGenerated();
        if (isWeekGenerated){
            schedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        schedulePage.goToScheduleNewUI();
        String holidaySmartCard = "HOLIDAYS";
        List<String> holidays = null;
        if (schedulePage.isSpecificSmartCardLoaded(holidaySmartCard)) {
            schedulePage.clickLinkOnSmartCardByName("View All");
            holidays = schedulePage.getHolidaysOfCurrentWeek();
            // Close popup window
            schedulePage.closeAnalyzeWindow();
        }

        schedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( "08:00AM", "9:00PM");
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.deleteTMShiftInWeekView(firstName);

        // Create new shift for the minor at weekday, weekend and holiday if have
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.customizeNewShiftPage();
        schedulePage.clearAllSelectedDays();
        schedulePage.selectDaysByIndex(0,0,0);

        // Set shift time as 10:00 AM - 6:00 PM
        schedulePage.moveSliderAtCertainPoint("6", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.moveSliderAtCertainPoint("10", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        schedulePage.selectWorkRole("Lift Maintenance");
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.searchTeamMemberByName(firstName + " " + lastName.substring(0,1));
        schedulePage.verifyMessageIsExpected("minor daily max 6 hrs");
        schedulePage.clickOnRadioButtonOfSearchedTeamMemberByName(firstName);
        if(schedulePage.ifWarningModeDisplay()){
            String warningMessage = schedulePage.getWarningMessageInDragShiftWarningMode();
            if (warningMessage.contains("daily schedule should not exceed 6 hours")){
                SimpleUtils.pass("Minor warning message for exceed the weekend or holiday hours displays");
            } else {
                SimpleUtils.fail("There is no minor warning message display when shift exceed the weekend or holiday hours displays", false);
            }
            schedulePage.clickOnAssignAnywayButton();
        } else {
            SimpleUtils.fail("There is no minor warning message display when shift exceed the weekend or holiday hours displays",false);
        }

        // Weekday should apply the settings of school day

        // Weekend should apply the settings of non school day

        // Holiday should apply the settings of non school day

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "OP_Enterprise")
    @TestName(description = "Verify the default value of a minor without a calendar")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateCalendarAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        teamPage.goToTeam();
        teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
        teamPage.clickOnTeamSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue());
        SimpleUtils.assertOnFail("Team page 'School Calendars' sub tab not loaded",
                teamPage.verifyActivatedSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue()), false);

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