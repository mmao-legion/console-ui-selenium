package com.legion.tests.core;


import com.legion.pages.DashboardPage;
import com.legion.pages.ProfileNewUIPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.TeamPage;
import com.legion.pages.*;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class CinemarkMinorTest extends TestBase {

    private static HashMap<String, String> cinemarkSetting14N15 = JsonUtil.getPropertiesFromJsonFile("src/test/resources/CinemarkMinorSettings.json");
    private static HashMap<String, String> cinemarkSetting16N17 = JsonUtil.getPropertiesFromJsonFile("src/test/resources/CinemarkMinorSettings16N17.json");
    private static HashMap<String, String> cinemarkMinors = JsonUtil.getPropertiesFromJsonFile("src/test/resources/CinemarkMinorsData.json");


    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception {
        this.createDriver((String) params[0], "69", "Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
    }

    //The template the location is using.
    public enum templateInUse{
        TEMPLATE_NAME("test2");
        private final String value;
        templateInUse(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    public enum minorType{
        Minor14N15("14N15"),
        Minor16N17("16N17");
        private final String value;
        minorType(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    public enum minorRuleWeekType{
        School_Week("School Week"),
        Non_School_Week("Non-School Week"),
        Summer_Week("Summer Week");
        private final String value;
        minorRuleWeekType(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    public enum minorRuleDayType{
        SchoolToday_SchoolTomorrow("School today, school tomorrow"),
        SchoolToday_NoSchoolTomorrow("School today, no school tomorrow"),
        NoSchoolToday_NoSchoolTomorrow("No school today, no school tomorrow"),
        NoSchoolToday_SchoolTomorrow("No school today, school tomorrow"),
        Summer_Day("Summer day");
        private final String value;
        minorRuleDayType(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    public enum templateAction{
        Save_As_Draft("Save as draft"),
        Publish_Now("Publish now"),
        Publish_Later("Publish later");
        private final String value;
        templateAction(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    public enum buttonGroup{
        Cancel("Cancel"),
        Close("Close"),
        OKWhenEdit("Ok"),
        OKWhenPublish("OK"),
        Delete("Delete"),
        Edit("Edit");
        private final String value;
        buttonGroup(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }



    @Automated(automated = "Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "OP_Enterprise")
    @TestName(description = "Prepare the calendar for all the minors")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void prepareTheCalendarForAllMinors(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()) , false);

        schedulePage.clickOnDayView();
        int currentDayIndex = schedulePage.getTheIndexOfCurrentDayInDayView();
        int nextSatIndex = 6 - currentDayIndex + 6;

        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();

        teamPage.goToTeam();
        teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();

        teamPage.clickOnTeamSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue());
        SimpleUtils.assertOnFail("Team page 'School Calendars' sub tab not loaded",
                teamPage.verifyActivatedSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue()), false);

        teamPage.clickOnCreateNewCalendarButton();
        teamPage.selectSchoolSessionStartNEndDate(nextSatIndex);
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "OP_Enterprise")
    @TestName(description = "Verify SM will have ability to select a calendar for the minor from a dropdown menu within the profile")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifySMCanSelectACalendarForMinorAsStoreManager(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

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
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

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
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        teamPage.goToTeam();
        teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();

        // Go to School Calendars sub tab
        teamPage.clickOnTeamSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue());
        SimpleUtils.assertOnFail("Team page 'School Calendars' sub tab not loaded",
                teamPage.verifyActivatedSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue()), false);

        //
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
        CinemarkMinorPage cinemarkMinorPage = pageFactory.createConsoleCinemarkMinorPage();
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();

        //Go to OP page
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
        //go to Configuration
        cinemarkMinorPage.clickConfigurationTabInOP();
        controlsNewUIPage.clickOnControlsComplianceSection();
        String templateName = "test"+String.valueOf(System.currentTimeMillis());
        cinemarkMinorPage.newTemplate(templateName);
        cinemarkMinorPage.verifyDefaultMinorRuleIsOff("14N15");
        cinemarkMinorPage.verifyDefaultMinorRuleIsOff("16N17");
        cinemarkMinorPage.saveOrPublishTemplate(templateAction.Save_As_Draft.getValue());
        cinemarkMinorPage.findDefaulTemplate(templateName);
        cinemarkMinorPage.clickOnBtn(buttonGroup.Delete.getValue());
        cinemarkMinorPage.clickOnBtn(buttonGroup.OKWhenPublish.getValue());

        cinemarkMinorPage.findDefaulTemplate(templateInUse.TEMPLATE_NAME.getValue());
        cinemarkMinorPage.clickOnBtn(buttonGroup.Edit.getValue());
        cinemarkMinorPage.clickOnBtn(buttonGroup.OKWhenEdit.getValue());
        cinemarkMinorPage.minorRuleToggle("no","14N15");
        cinemarkMinorPage.minorRuleToggle("no","16N17");
        cinemarkMinorPage.saveOrPublishTemplate(templateAction.Publish_Now.getValue());
        cinemarkMinorPage.clickOnBtn(buttonGroup.OKWhenPublish.getValue());

        //Back to Console
        locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.Console.getValue());
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        String minorLocation = "Test For Minors";
        locationSelectorPage.changeLocation(minorLocation);
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        teamPage.goToTeam();
        SimpleUtils.assertOnFail("School Calendar tab should not be loaded when minot rule turned off", !teamPage.isCalendarTabLoad(), false);
        locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
        cinemarkMinorPage.clickConfigurationTabInOP();
        controlsNewUIPage.clickOnControlsComplianceSection();
        cinemarkMinorPage.findDefaulTemplate(templateInUse.TEMPLATE_NAME.getValue());
        cinemarkMinorPage.clickOnBtn(buttonGroup.Edit.getValue());
        cinemarkMinorPage.clickOnBtn(buttonGroup.OKWhenEdit.getValue());
        cinemarkMinorPage.minorRuleToggle("yes","14N15");
        cinemarkMinorPage.minorRuleToggle("yes","16N17");
        cinemarkMinorPage.saveOrPublishTemplate(templateAction.Publish_Now.getValue());
        cinemarkMinorPage.clickOnBtn(buttonGroup.OKWhenPublish.getValue());
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "OP_Enterprise")
    @TestName(description = "Verify turn on minor rule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTurnOnAndSetMinorRuleAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        CinemarkMinorPage cinemarkMinorPage = pageFactory.createConsoleCinemarkMinorPage();
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();

        //Go to OP page
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
        //go to Configuration
        cinemarkMinorPage.clickConfigurationTabInOP();
        controlsNewUIPage.clickOnControlsComplianceSection();

        //Find the template
        cinemarkMinorPage.findDefaulTemplate(templateInUse.TEMPLATE_NAME.getValue());
        cinemarkMinorPage.clickOnBtn(buttonGroup.Edit.getValue());
        cinemarkMinorPage.clickOnBtn(buttonGroup.OKWhenEdit.getValue());
        cinemarkMinorPage.minorRuleToggle("yes","14N15");

        cinemarkMinorPage.setMinorRuleByWeek(minorType.Minor14N15.getValue(), minorRuleWeekType.School_Week.getValue(),cinemarkSetting14N15.get(minorRuleWeekType.School_Week.getValue()).split(",")[0],cinemarkSetting14N15.get(minorRuleWeekType.School_Week.getValue()).split(",")[1]);
        cinemarkMinorPage.setMinorRuleByWeek(minorType.Minor14N15.getValue(), minorRuleWeekType.Non_School_Week.getValue(),cinemarkSetting14N15.get(minorRuleWeekType.Non_School_Week.getValue()).split(",")[0],cinemarkSetting14N15.get(minorRuleWeekType.Non_School_Week.getValue()).split(",")[1]);
        cinemarkMinorPage.setMinorRuleByWeek(minorType.Minor14N15.getValue(), minorRuleWeekType.Summer_Week.getValue(),cinemarkSetting14N15.get(minorRuleWeekType.Summer_Week.getValue()).split(",")[0],cinemarkSetting14N15.get(minorRuleWeekType.Summer_Week.getValue()).split(",")[1]);
        cinemarkMinorPage.setMinorRuleByDay(minorType.Minor14N15.getValue(), minorRuleDayType.SchoolToday_SchoolTomorrow.getValue(), cinemarkSetting14N15.get(minorRuleDayType.SchoolToday_SchoolTomorrow.getValue()).split(",")[0], cinemarkSetting14N15.get(minorRuleDayType.SchoolToday_SchoolTomorrow.getValue()).split(",")[1], cinemarkSetting14N15.get(minorRuleDayType.SchoolToday_SchoolTomorrow.getValue()).split(",")[2]);
        cinemarkMinorPage.setMinorRuleByDay(minorType.Minor14N15.getValue(), minorRuleDayType.SchoolToday_NoSchoolTomorrow.getValue(), cinemarkSetting14N15.get(minorRuleDayType.SchoolToday_NoSchoolTomorrow.getValue()).split(",")[0], cinemarkSetting14N15.get(minorRuleDayType.SchoolToday_NoSchoolTomorrow.getValue()).split(",")[1], cinemarkSetting14N15.get(minorRuleDayType.SchoolToday_NoSchoolTomorrow.getValue()).split(",")[2]);
        cinemarkMinorPage.setMinorRuleByDay(minorType.Minor14N15.getValue(), minorRuleDayType.NoSchoolToday_NoSchoolTomorrow.getValue(), cinemarkSetting14N15.get(minorRuleDayType.NoSchoolToday_NoSchoolTomorrow.getValue()).split(",")[0], cinemarkSetting14N15.get(minorRuleDayType.NoSchoolToday_NoSchoolTomorrow.getValue()).split(",")[1], cinemarkSetting14N15.get(minorRuleDayType.NoSchoolToday_NoSchoolTomorrow.getValue()).split(",")[2]);
        cinemarkMinorPage.setMinorRuleByDay(minorType.Minor14N15.getValue(), minorRuleDayType.NoSchoolToday_SchoolTomorrow.getValue(), cinemarkSetting14N15.get(minorRuleDayType.NoSchoolToday_SchoolTomorrow.getValue()).split(",")[0], cinemarkSetting14N15.get(minorRuleDayType.NoSchoolToday_SchoolTomorrow.getValue()).split(",")[1], cinemarkSetting14N15.get(minorRuleDayType.NoSchoolToday_SchoolTomorrow.getValue()).split(",")[2]);
        cinemarkMinorPage.setMinorRuleByDay(minorType.Minor14N15.getValue(), minorRuleDayType.Summer_Day.getValue(), cinemarkSetting14N15.get(minorRuleDayType.Summer_Day.getValue()).split(",")[0], cinemarkSetting14N15.get(minorRuleDayType.Summer_Day.getValue()).split(",")[1], cinemarkSetting14N15.get(minorRuleDayType.Summer_Day.getValue()).split(",")[2]);
        cinemarkMinorPage.minorRuleToggle("yes","16N17");
        cinemarkMinorPage.setMinorRuleByWeek(minorType.Minor16N17.getValue(), minorRuleWeekType.School_Week.getValue(),cinemarkSetting16N17.get(minorRuleWeekType.School_Week.getValue()).split(",")[0],cinemarkSetting16N17.get(minorRuleWeekType.School_Week.getValue()).split(",")[1]);
        cinemarkMinorPage.setMinorRuleByWeek(minorType.Minor16N17.getValue(), minorRuleWeekType.Non_School_Week.getValue(),cinemarkSetting16N17.get(minorRuleWeekType.Non_School_Week.getValue()).split(",")[0],cinemarkSetting16N17.get(minorRuleWeekType.Non_School_Week.getValue()).split(",")[1]);
        cinemarkMinorPage.setMinorRuleByWeek(minorType.Minor16N17.getValue(), minorRuleWeekType.Summer_Week.getValue(),cinemarkSetting16N17.get(minorRuleWeekType.Summer_Week.getValue()).split(",")[0],cinemarkSetting16N17.get(minorRuleWeekType.Summer_Week.getValue()).split(",")[1]);
        cinemarkMinorPage.setMinorRuleByDay(minorType.Minor16N17.getValue(), minorRuleDayType.SchoolToday_SchoolTomorrow.getValue(), cinemarkSetting16N17.get(minorRuleDayType.SchoolToday_SchoolTomorrow.getValue()).split(",")[0], cinemarkSetting16N17.get(minorRuleDayType.SchoolToday_SchoolTomorrow.getValue()).split(",")[1], cinemarkSetting16N17.get(minorRuleDayType.SchoolToday_SchoolTomorrow.getValue()).split(",")[2]);
        cinemarkMinorPage.setMinorRuleByDay(minorType.Minor16N17.getValue(), minorRuleDayType.SchoolToday_NoSchoolTomorrow.getValue(), cinemarkSetting16N17.get(minorRuleDayType.SchoolToday_NoSchoolTomorrow.getValue()).split(",")[0], cinemarkSetting16N17.get(minorRuleDayType.SchoolToday_NoSchoolTomorrow.getValue()).split(",")[1], cinemarkSetting16N17.get(minorRuleDayType.SchoolToday_NoSchoolTomorrow.getValue()).split(",")[2]);
        cinemarkMinorPage.setMinorRuleByDay(minorType.Minor16N17.getValue(), minorRuleDayType.NoSchoolToday_NoSchoolTomorrow.getValue(), cinemarkSetting16N17.get(minorRuleDayType.NoSchoolToday_NoSchoolTomorrow.getValue()).split(",")[0], cinemarkSetting16N17.get(minorRuleDayType.NoSchoolToday_NoSchoolTomorrow.getValue()).split(",")[1], cinemarkSetting16N17.get(minorRuleDayType.NoSchoolToday_NoSchoolTomorrow.getValue()).split(",")[2]);
        cinemarkMinorPage.setMinorRuleByDay(minorType.Minor16N17.getValue(), minorRuleDayType.NoSchoolToday_SchoolTomorrow.getValue(), cinemarkSetting16N17.get(minorRuleDayType.NoSchoolToday_SchoolTomorrow.getValue()).split(",")[0], cinemarkSetting16N17.get(minorRuleDayType.NoSchoolToday_SchoolTomorrow.getValue()).split(",")[1], cinemarkSetting16N17.get(minorRuleDayType.NoSchoolToday_SchoolTomorrow.getValue()).split(",")[2]);
        cinemarkMinorPage.setMinorRuleByDay(minorType.Minor16N17.getValue(), minorRuleDayType.Summer_Day.getValue(), cinemarkSetting16N17.get(minorRuleDayType.Summer_Day.getValue()).split(",")[0], cinemarkSetting16N17.get(minorRuleDayType.Summer_Day.getValue()).split(",")[1], cinemarkSetting16N17.get(minorRuleDayType.Summer_Day.getValue()).split(",")[2]);

        cinemarkMinorPage.saveOrPublishTemplate(templateAction.Publish_Now.getValue());
        cinemarkMinorPage.clickOnBtn(buttonGroup.OKWhenPublish.getValue());

    }
}