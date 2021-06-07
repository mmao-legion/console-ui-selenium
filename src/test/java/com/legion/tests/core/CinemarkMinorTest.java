package com.legion.tests.core;


import com.legion.pages.DashboardPage;
import com.legion.pages.ProfileNewUIPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.TeamPage;
import com.legion.pages.*;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.*;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.legion.utils.MyThreadLocal.location;
import static com.legion.utils.MyThreadLocal.setTestSuiteID;

public class CinemarkMinorTest extends TestBase {

    private static HashMap<String, String> cinemarkSetting14N15 = JsonUtil.getPropertiesFromJsonFile("src/test/resources/CinemarkMinorSettings.json");
    private static HashMap<String, String> cinemarkSetting16N17 = JsonUtil.getPropertiesFromJsonFile("src/test/resources/CinemarkMinorSettings16N17.json");
    private static HashMap<String, String> cinemarkMinors = JsonUtil.getPropertiesFromJsonFile("src/test/resources/CinemarkMinorsData.json");


    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception {
        try {
            this.createDriver((String) params[0], "69", "Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
            if (MyThreadLocal.getCurrentComplianceTemplate()==null || MyThreadLocal.getCurrentComplianceTemplate().equals("")){
                getAndSetDefaultTemplate((String) params[3]);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    //The template the location is using.
    public enum templateInUse{
        TEMPLATE_NAME("New Hampshire Compliance"),
        TEMPLATE_OP("clement-997");
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
        Save_As_Draft("saveAsDraft"),
        Publish_Now("publishNow"),
        Publish_Later("publishLater");
        private final String value;
        templateAction(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    public enum buttonGroup{
        Cancel("Cancel"),
        Close("Close"),
        OKWhenEdit("OK"),
        OKWhenPublish("OK"),
        Delete("Delete"),
        Save("Save"),
        EditTemplate("Edit template"),
        Edit("Edit"),
        Yes("Yes"),
        No("No");
        private final String value;
        buttonGroup(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }



    @Automated(automated = "Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Prepare the calendar for all the minors")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void prepareTheCalendarForAllMinorsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()) , false);
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            schedulePage.navigateDayViewWithDayName("Sat");
            Map<String, String> dayInfo = schedulePage.getActiveDayInfo();

            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();

            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();

            teamPage.clickOnTeamSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue());
            SimpleUtils.assertOnFail("Team page 'School Calendars' sub tab not loaded",
                    teamPage.verifyActivatedSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue()), false);
            String calendarName = "Start Next Saturday1";

            teamPage.deleteCalendarByName(calendarName);
            teamPage.clickOnCreateNewCalendarButton();
            teamPage.selectSchoolYear();
            teamPage.clickOnSchoolSessionStart();
            teamPage.selectSchoolSessionStartAndEndDate(dayInfo.get("year") +" Jan 1",
                    dayInfo.get("year") +" "+ dayInfo.get("month") + " "+ dayInfo.get("day"));
            teamPage.clickOnSaveSchoolSessionCalendarBtn();
            teamPage.inputCalendarName(calendarName);
            teamPage.clickOnSaveSchoolCalendarBtn();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();

            List<String> minorNames = new ArrayList<>();
            minorNames.add(cinemarkMinors.get("Minor13"));
            minorNames.add(cinemarkMinors.get("Minor14"));
            minorNames.add(cinemarkMinors.get("Minor15"));
            minorNames.add(cinemarkMinors.get("Minor16"));
            minorNames.add(cinemarkMinors.get("Minor17"));

            teamPage.setTheCalendarForMinors(minorNames, calendarName, profileNewUIPage);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify add dates for breaks")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAddDatesForBreaksAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();

            teamPage.clickOnTeamSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue());
            SimpleUtils.assertOnFail("Team page 'School Calendars' sub tab not loaded",
                    teamPage.verifyActivatedSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue()), false);

            teamPage.clickTheCalendarByRandom();
            teamPage.verifySchoolSessionPageLoaded();
            teamPage.clickOnEditCalendarButton();
            teamPage.verifyEditCalendarAlertModelPopsUp();
            teamPage.clickOnEditAnywayButton();
            SimpleUtils.assertOnFail("Edit Calendar page not loaded Successfully!", teamPage.isEditCalendarModeLoaded(), false);

            // Verify the clicked days are highlighted as "Non School Day" color
            teamPage.verifyClickedDayIsHighlighted();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify view details of  calendars and edit")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyViewDetailsAndEditAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();

            teamPage.clickOnTeamSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue());
            SimpleUtils.assertOnFail("Team page 'School Calendars' sub tab not loaded",
                    teamPage.verifyActivatedSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue()), false);

            // Verify the visibility of calendars on School Calendars page
            teamPage.verifyTheCalendarListLoaded();
            // Verify the content of each calendar
            teamPage.verifyTheContentOnEachCalendarList();
            // Verify the visibility of the detailed calendar page
            teamPage.clickTheCalendarByRandom();
            teamPage.verifySchoolSessionPageLoaded();
            // Verify the content on detailed calendar page
            teamPage.verifyTheContentOnDetailedCalendarPage();
            // Verify the functionality of Edit button
            teamPage.clickOnEditCalendarButton();
            teamPage.verifyEditCalendarAlertModelPopsUp();
            teamPage.clickOnEditAnywayButton();
            SimpleUtils.assertOnFail("Edit Calendar page not loaded Successfully!", teamPage.isEditCalendarModeLoaded(), false);

            // Verify the functionality of Save button
            teamPage.clickOnSaveSchoolCalendarBtn();
            // Verify the functionality of "School Schedules" button
            teamPage.clickOnSchoolSchedulesButton();
            teamPage.verifyTheCalendarListLoaded();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify delete calendar")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDeleteCalendarAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();

            teamPage.clickOnTeamSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue());
            SimpleUtils.assertOnFail("Team page 'School Calendars' sub tab not loaded",
                    teamPage.verifyActivatedSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue()), false);

            // Verify the visibility of calendars on School Calendars page
            teamPage.verifyTheCalendarListLoaded();

            teamPage.clickTheCalendarByRandom();
            teamPage.verifySchoolSessionPageLoaded();

            // Verify the presence of DELETE button
            // Verify the functionality of DELETE button
            teamPage.clickOnDeleteCalendarButton();
            // Verify the functionality of CANCEL button
            teamPage.clickOnCancelButtonOnPopup();
            // Verify the functionality of DELETE ANYWAY button
            teamPage.clickOnDELETEANYWAYButton();
            teamPage.verifyTheCalendarListLoaded();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify SM will have ability to select a calendar for the minor from a dropdown menu within the profile")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifySMCanSelectACalendarForMinorAsStoreManager(String browser, String username, String password, String location) throws Exception {
        try {
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
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            String minorName = teamPage.searchAndSelectTeamMemberByName(cinemarkMinors.get("Minor14"));
            teamPage.isProfilePageLoaded();
            if (minorName != "")
                SimpleUtils.pass("Team Page: search out one minor to View Profile successfully");
            else
                SimpleUtils.fail("Team Page: Failed to search out one minor to View Profile",false);

            // Verify SM can select a calendar from a dropdown menu within the profile
            profileNewUIPage.verifySMCanSelectACalendarForMinor();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the default value of a minor without a calendar")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDefaultValueOfAMinorWithoutACalendarAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();

            // Get Cinemark minor settings from Jason file
            String schoolWeekMaxScheduleHrs = cinemarkSetting14N15.get(minorRuleWeekType.School_Week.getValue()).split(",")[1];
            String nonSchoolWeekMaxScheduleHrs = cinemarkSetting14N15.get(minorRuleWeekType.Non_School_Week.getValue()).split(",")[1];

            // Search out a TM who is a minor and get minor name to enter profile page
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            teamPage.searchAndSelectTeamMemberByName(cinemarkMinors.get("Minor14"));
            teamPage.isProfilePageLoaded();

            // Edit, select "None" from the calendar dropdown menu, and save the profile
            profileNewUIPage.selectAGivenCalendarForMinor("None");

            // Go to Schedule page and navigate to a week
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()) , false);
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();

            // Get the current holiday information if have
            String holidaySmartCard = "HOLIDAYS";
            List<String> holidays = null;
            if (schedulePage.isSpecificSmartCardLoaded(holidaySmartCard)) {
                schedulePage.navigateToTheRightestSmartCard();
                schedulePage.clickLinkOnSmartCardByName("View All");
                holidays = schedulePage.getHolidaysOfCurrentWeek();
                // Close popup window
                schedulePage.closeAnalyzeWindow();
            }

            // Ungenerate the schedule if it is created or published
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }

            // Create new shift for the minor at weekday, weekend and holiday if have
            schedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( "05:00AM", "11:00PM");
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView(cinemarkMinors.get("Minor14"));
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            schedulePage.selectSpecificWorkDay(7);
            schedulePage.moveSliderAtCertainPoint("10pm", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("6am", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.selectWorkRole("Team Member Corporate-Theatre");
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(cinemarkMinors.get("Minor14"));
            schedulePage.clickOnRadioButtonOfSearchedTeamMemberByName(cinemarkMinors.get("Minor14"));
            schedulePage.clickOnAssignAnywayButton();
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();

            // Get holidays index if have
            ArrayList<Integer> holidayIndexes = new ArrayList<>();
            ArrayList<Integer> weekdayIndexes = new ArrayList<>();
            if (holidays!= null) {
                for (int index = 0; index < 5; index ++) {
                    for (String s: holidays) {
                        if (s.contains(schedulePage.getWeekDayTextByIndex(index)))
                            holidayIndexes.add(index);
                        else
                            weekdayIndexes.add(index);
                    }
                }
            } else
                weekdayIndexes.add(0);

            // Validate weekday should apply the settings of school day
            WebElement newAddedShift = schedulePage.getTheShiftByIndex(schedulePage.getAddedShiftIndexes(cinemarkMinors.get("Minor14")).get(weekdayIndexes.get(0)));
            if (newAddedShift != null && schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Minor weekly max " + schoolWeekMaxScheduleHrs + " hrs"))
                SimpleUtils.pass("Schedule Page: Weekday applies the settings of non school day");
            else
                SimpleUtils.fail("Get new added shift failed", false);

            // Validate weekend should apply the settings of non school day
            newAddedShift = schedulePage.getTheShiftByIndex(schedulePage.getAddedShiftIndexes(cinemarkMinors.get("Minor14")).get(5));
            if (newAddedShift != null && schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Minor weekly max " + schoolWeekMaxScheduleHrs + " hrs"))
                SimpleUtils.pass("Schedule Page: Weekday applies the settings of non school day");
            else
                SimpleUtils.fail("Get new added shift failed", false);


            // Validate holiday should apply the settings of non school day
            if (holidays != null) {
                newAddedShift = schedulePage.getTheShiftByIndex(schedulePage.getAddedShiftIndexes(cinemarkMinors.get("Minor14")).get(holidayIndexes.get(0)));
                if (newAddedShift != null) {
                    if (holidayIndexes.size() == 5 && schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Minor weekly max " + nonSchoolWeekMaxScheduleHrs + " hrs"))
                        SimpleUtils.pass("Schedule Page: Holiday applies the settings of non school day");
                    else if (holidayIndexes.size() < 5 && schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Minor weekly max " + schoolWeekMaxScheduleHrs + " hrs"))
                        SimpleUtils.pass("Schedule Page: Holiday applies the settings of non school day");
                    else
                        SimpleUtils.fail("Schedule Page: Holiday does not apply the settings of non school day",false);
                } else
                    SimpleUtils.fail("Get new added shift failed", false);
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify create calendar")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateCalendarAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            int randomDigits = (new Random()).nextInt(100);

            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();

            // Go to School Calendars sub tab
            teamPage.clickOnTeamSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue());
            SimpleUtils.assertOnFail("Team page 'School Calendars' sub tab not loaded",
                    teamPage.verifyActivatedSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue()), false);

            // Click on Create New Calendar button, verify the Cancel and Save button display correctly
            teamPage.clickOnCreateNewCalendarButton();

            // Verify the Session Start and Session End fields are mandatory fields
            teamPage.verifyCreateCalendarLoaded();
            teamPage.verifySessionStartNEndIsMandatory();

            // Click on School Session Start
            teamPage.clickOnSchoolSessionStart();

            // Select random start and end day and verify they display correctly
            String startDate = teamPage.selectRandomDayInSessionStart(); //08-25-2020
            String endDate = teamPage.selectRandomDayInSessionEnd(); //05-31-2021

            // Save after setting session start and end time
            teamPage.clickOnSaveSchoolSessionCalendarBtn();

            // Verify dates will be color coded by start and end time
            teamPage.verifyDatesInCalendar(startDate,endDate);

            // Input calendar name, and verify the calendar name can be edited and changed
            String calendarName = "Calendar" + randomDigits;
            teamPage.inputCalendarName(calendarName);

            // Verify calendar for the next year will show the same calendar name until enter the start and end date, the calendar is editable
            teamPage.checkNextYearInEditMode();

            // Verify the year display when going back to current calendar
            teamPage.clickOnPriorYearInEditMode();

            // Verify that cannot go to prior year
            teamPage.checkPriorYearInEditMode();

            // Verify the calendar can be saved
            teamPage.clickOnSaveCalendar();

            // Verify the new created calendar will list in the calendar list
            teamPage.clickOnTeamSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue());
            SimpleUtils.assertOnFail("Team page 'School Calendars' sub tab not loaded",
                    teamPage.verifyActivatedSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue()), false);
            if (teamPage.isCalendarDisplayedByName("Calendar" + randomDigits))
                SimpleUtils.pass("School Calendar: Calendar just created is in the list");
            else
                SimpleUtils.fail("School Calendar: ACalendar just created is not in the list",false);

            // Verify the calendar can be deleted
            teamPage.clickOnTeamSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue());
            SimpleUtils.assertOnFail("Team page 'School Calendars' sub tab not loaded",
                    teamPage.verifyActivatedSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue()), false);
            teamPage.deleteCalendarByName(calendarName);

            // Verify the the change for calendar will not been saved after click Cancel button
            teamPage.clickOnCreateNewCalendarButton();
            teamPage.clickOnSchoolSessionStart();
            teamPage.selectRandomDayInSessionStart();
            teamPage.selectRandomDayInSessionEnd();
            teamPage.clickOnSaveSchoolSessionCalendarBtn();
            teamPage.inputCalendarName("CancelledCalendar");
            teamPage.clickOnCancelEditCalendarBtn();
            if (!teamPage.isCalendarDisplayedByName("CancelledCalendar"))
                SimpleUtils.pass("School Calendar: Create action is cancelled, there will not be this calendar in the list");
            else
                SimpleUtils.fail("School Calendar: Create action failed to cancel",false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify school calendar list")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifySchoolCalendarListAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            int random1 = (new Random()).nextInt(1000);
            int random2 = (new Random()).nextInt(1000);

            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            teamPage.clickOnTeamSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue());
            SimpleUtils.assertOnFail("Team page 'School Calendars' sub tab not loaded",
                    teamPage.verifyActivatedSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue()), false);

            // Create a new calendar via Admin
            teamPage.createNewCalendarByName("Calendar" + random1);

            // Create another new calendar via Admin
            teamPage.createNewCalendarByName("Calendar" + random2);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            // Login as Store Manager
            String fileName = "UsersCredentials.json";
            fileName = SimpleUtils.getEnterprise("CinemarkWkdy_Enterprise") + fileName;
            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
            Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
                    , String.valueOf(storeManagerCredentials[0][2]));
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            int random3 = (new Random()).nextInt(1000);
            int random4 = (new Random()).nextInt(1000);

            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            teamPage.clickOnTeamSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue());
            SimpleUtils.assertOnFail("Team page 'School Calendars' sub tab not loaded",
                    teamPage.verifyActivatedSubTab(TeamTest.TeamPageSubTabText.SchoolCalendars.getValue()), false);

            // Create a new calendar via Store Manager
            teamPage.createNewCalendarByName("Calendar" + random3);

            // Create another new calendar via Store Manager
            teamPage.createNewCalendarByName("Calendar" + random4);

            // Check the School Calendars list
            if (teamPage.isCalendarDisplayedByName("Calendar" + random1) && teamPage.isCalendarDisplayedByName("Calendar" + random2)
                    && teamPage.isCalendarDisplayedByName("Calendar" + random3) && teamPage.isCalendarDisplayedByName("Calendar" + random4))
                SimpleUtils.pass("School Calendar: All the calendars have been created display in the list");
            else
                SimpleUtils.fail("School Calendar: All the calendars have been created don't display in the list",false);

            // Clean up data
            teamPage.deleteCalendarByName("Calendar" + random1);
            teamPage.deleteCalendarByName("Calendar" + random2);
            teamPage.deleteCalendarByName("Calendar" + random3);
            teamPage.deleteCalendarByName("Calendar" + random4);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    //Haya
    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify turn off minor rule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTurnOffMinorRuleAsInternalAdmin(String browser, String username, String password, String location) {
        try {
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
            cinemarkMinorPage.findDefaultTemplate(templateName);
            cinemarkMinorPage.clickOnBtn(buttonGroup.Delete.getValue());
            cinemarkMinorPage.clickOnBtn(buttonGroup.OKWhenPublish.getValue());

            //cinemarkMinorPage.findDefaultTemplate(templateInUse.TEMPLATE_NAME.getValue());
            cinemarkMinorPage.findDefaultTemplate(MyThreadLocal.getCurrentComplianceTemplate());
            cinemarkMinorPage.clickOnBtn(buttonGroup.Edit.getValue());
            cinemarkMinorPage.clickOnBtn(buttonGroup.OKWhenEdit.getValue());
            cinemarkMinorPage.minorRuleToggle("no","14N15");
            cinemarkMinorPage.minorRuleToggle("no","16N17");
            cinemarkMinorPage.saveOrPublishTemplate(templateAction.Publish_Now.getValue());
            cinemarkMinorPage.clickOnBtn(buttonGroup.OKWhenPublish.getValue());
            Thread.sleep(3000);

            //Back to Console
            switchToConsoleWindow();

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            String fileName = "UsersCredentials.json";
            fileName = SimpleUtils.getEnterprise("CinemarkWkdy_Enterprise")+fileName;
            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
            Object[][] teamMemberCredentials = userCredentials.get("StoreManager");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                    , String.valueOf(teamMemberCredentials[0][2]));

            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            SimpleUtils.assertOnFail("School Calendar tab should not be loaded when minor rule turned off", !teamPage.isCalendarTabLoad(), false);

            loginPage.logOut();

            // Login as Internal Admin
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
            cinemarkMinorPage.clickConfigurationTabInOP();
            controlsNewUIPage.clickOnControlsComplianceSection();
            cinemarkMinorPage.findDefaultTemplate(templateInUse.TEMPLATE_NAME.getValue());
            cinemarkMinorPage.clickOnBtn(buttonGroup.Edit.getValue());
            cinemarkMinorPage.clickOnBtn(buttonGroup.OKWhenEdit.getValue());
            cinemarkMinorPage.minorRuleToggle("yes","14N15");
            cinemarkMinorPage.minorRuleToggle("yes","16N17");
            cinemarkMinorPage.saveOrPublishTemplate(templateAction.Publish_Now.getValue());
            cinemarkMinorPage.clickOnBtn(buttonGroup.OKWhenPublish.getValue());
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "verify turn on minor rule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTurnOnAndSetMinorRuleEmptyAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            CinemarkMinorPage cinemarkMinorPage = pageFactory.createConsoleCinemarkMinorPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            String districtName = dashboardPage.getCurrentDistrict();

            //Go to OP page
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
            //go to Configuration
            cinemarkMinorPage.clickConfigurationTabInOP();
            controlsNewUIPage.clickOnControlsComplianceSection();

            //Find the template
            //cinemarkMinorPage.findDefaultTemplate(templateInUse.TEMPLATE_NAME.getValue());
            cinemarkMinorPage.findDefaultTemplate(MyThreadLocal.getCurrentComplianceTemplate());
            cinemarkMinorPage.clickOnBtn(buttonGroup.Edit.getValue());
            cinemarkMinorPage.clickOnBtn(buttonGroup.OKWhenEdit.getValue());
            cinemarkMinorPage.minorRuleToggle("yes","14N15");

            cinemarkMinorPage.setMinorRuleByWeek(minorType.Minor16N17.getValue(), minorRuleWeekType.School_Week.getValue(),"","");
            cinemarkMinorPage.setMinorRuleByWeek(minorType.Minor16N17.getValue(), minorRuleWeekType.Non_School_Week.getValue(),"","");
            cinemarkMinorPage.setMinorRuleByWeek(minorType.Minor16N17.getValue(), minorRuleWeekType.Summer_Week.getValue(),"","");
            cinemarkMinorPage.setMinorRuleByDay(minorType.Minor16N17.getValue(), minorRuleDayType.SchoolToday_SchoolTomorrow.getValue(), "","","");
            cinemarkMinorPage.setMinorRuleByDay(minorType.Minor16N17.getValue(), minorRuleDayType.SchoolToday_NoSchoolTomorrow.getValue(), "","","");
            cinemarkMinorPage.setMinorRuleByDay(minorType.Minor16N17.getValue(), minorRuleDayType.NoSchoolToday_NoSchoolTomorrow.getValue(), "","","");
            cinemarkMinorPage.setMinorRuleByDay(minorType.Minor16N17.getValue(), minorRuleDayType.NoSchoolToday_SchoolTomorrow.getValue(), "","","");
            cinemarkMinorPage.setMinorRuleByDay(minorType.Minor16N17.getValue(), minorRuleDayType.Summer_Day.getValue(), "","","");

            cinemarkMinorPage.saveOrPublishTemplate(templateAction.Publish_Now.getValue());
            cinemarkMinorPage.clickOnBtn(buttonGroup.OKWhenPublish.getValue());

            //Back to Console
            switchToConsoleWindow();
//            locationSelectorPage.changeUpperFieldsByName("Business Unit", "BU1");
//            locationSelectorPage.changeUpperFieldsByName("Region", "Region1");
//            locationSelectorPage.changeDistrict(districtName);
//            String minorLocation = "Test For Minors";
//            locationSelectorPage.changeLocation(minorLocation);
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()) , false);
            // Navigate to a week
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            // create the schedule if not created
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView(cinemarkMinors.get("Minor17"));
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.clearAllSelectedDays();
            schedulePage.selectDaysByIndex(0,0,0);
            schedulePage.selectWorkRole("MOD");
            schedulePage.moveSliderAtSomePoint("8", 20, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchText(cinemarkMinors.get("Minor17"));
            SimpleUtils.assertOnFail("Minor warning should not work when setting is empty", !schedulePage.getAllTheWarningMessageOfTMWhenAssign().contains("Minor"), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Admin can configure the access to edit calendars")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAccessToEditCalendarsAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            CinemarkMinorPage cinemarkMinorPage = pageFactory.createConsoleCinemarkMinorPage();
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            controlsPage.gotoControlsPage();
            controlsPage.clickGlobalSettings();

            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsNewUIPage.clickOnControlsUsersAndRolesSection();
            String accessRoleTab = "Access Roles";
            controlsNewUIPage.selectUsersAndRolesSubTabByLabel(accessRoleTab);
            String permissionSection = "Team";
            String permission1 = "Manage School Calendars";
            String permission2 = "View School Calendars";
            String actionOff = "off";
            String actionOn = "on";
            cinemarkMinorPage.clickOnBtn(buttonGroup.Edit.getValue());
            controlsNewUIPage.turnOnOrOffSpecificPermissionForSM(permissionSection, permission1, actionOff);
            controlsNewUIPage.turnOnOrOffSpecificPermissionForSM(permissionSection, permission2, actionOff);
            cinemarkMinorPage.clickOnBtn(buttonGroup.Save.getValue());
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            //Log in as SM to check
            String fileName = "UsersCredentials.json";
            fileName = SimpleUtils.getEnterprise("CinemarkWkdy_Enterprise")+fileName;
            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
            Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
                    , String.valueOf(storeManagerCredentials[0][2]));
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            SimpleUtils.assertOnFail("School Calendar tab should not be loaded when SM doesn't have the permission!", !teamPage.isCalendarTabLoad(), false);
            loginPage.logOut();

            //Log in as admin, grant the view calendar permission to SM.
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            controlsPage.gotoControlsPage();
            controlsPage.clickGlobalSettings();

            controlsNewUIPage.clickOnControlsUsersAndRolesSection();
            controlsNewUIPage.selectUsersAndRolesSubTabByLabel(accessRoleTab);
            cinemarkMinorPage.clickOnBtn(buttonGroup.Edit.getValue());
            controlsNewUIPage.turnOnOrOffSpecificPermissionForSM(permissionSection, permission2, actionOn);
            cinemarkMinorPage.clickOnBtn(buttonGroup.Save.getValue());
            loginPage.logOut();

            //Log in as SM to check
            loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
                    , String.valueOf(storeManagerCredentials[0][2]));
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            teamPage.goToTeam();
            String calendarTab = "School Calendars";
            teamPage.clickOnTeamSubTab(calendarTab);
            SimpleUtils.assertOnFail("School Calendar tab should show up!", teamPage.isCalendarTabLoad(), false);
            //SimpleUtils.assertOnFail("School Calendar Create New Calendar button should not load!", !teamPage.isCreateCalendarBtnLoaded(), true);
            if (teamPage.isCreateCalendarBtnLoaded()){
                SimpleUtils.warn("School Calendar Create New Calendar button should not load!");
            }

            loginPage.logOut();
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            controlsPage.gotoControlsPage();
            controlsPage.clickGlobalSettings();

            controlsNewUIPage.clickOnControlsUsersAndRolesSection();
            controlsNewUIPage.selectUsersAndRolesSubTabByLabel(accessRoleTab);
            cinemarkMinorPage.clickOnBtn(buttonGroup.Edit.getValue());
            controlsNewUIPage.turnOnOrOffSpecificPermissionForSM(permissionSection, permission1, actionOn);
            cinemarkMinorPage.clickOnBtn(buttonGroup.Save.getValue());
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify turn on minor rule and set rule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTurnOnAndSetMinorRuleAsInternalAdmin(String browser, String username, String password, String location) {
        try {
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
            //cinemarkMinorPage.findDefaultTemplate(templateInUse.TEMPLATE_NAME.getValue());
            cinemarkMinorPage.findDefaultTemplate(MyThreadLocal.getCurrentComplianceTemplate());
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
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    //added by Haya.
    public void getAndSetDefaultTemplate(String currentLocation) throws Exception{
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        CinemarkMinorPage cinemarkMinorPage = pageFactory.createConsoleCinemarkMinorPage();
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();

        //Go to OP page
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
        locationsPage.clickOnLocationsTab();
        locationsPage.goToSubLocationsInLocationsPage();
        locationsPage.searchLocation(currentLocation);               ;
        SimpleUtils.assertOnFail("Locations not searched out Successfully!",  locationsPage.verifyUpdateLocationResult(currentLocation), false);
        locationsPage.clickOnLocationInLocationResult(currentLocation);
        locationsPage.clickOnConfigurationTabOfLocation();
        HashMap<String, String> templateTypeAndName = locationsPage.getTemplateTypeAndNameFromLocation();
        MyThreadLocal.setCurrentComplianceTemplate(templateTypeAndName.get("Compliance"));
        //back to console.
        switchToConsoleWindow();
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the School today and school tomorrow settings for the Minors of Age 14 or 15")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheSchoolTodayAndSchoolTomorrowSettingsForTheMinorsOfAge14Or15AsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String minorName = "Minor14";
            String shiftTime1 = "7am,1pm";
            String shiftTime2 = "9am,4pm";
            String shiftTime3 = "8am,2pm";
            String workRole = "Team Member Corporate-Theatre";
            String scheduleFromToTime = "8am - 4pm";
            String scheduleMaxHours = "6";
            String selectWeekDayName = "Mon";
            verifyDayOvertimeViolationsForMinors(minorName, shiftTime1, shiftTime2, shiftTime3,
                    workRole, scheduleFromToTime, scheduleMaxHours, false, selectWeekDayName);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the School today and school tomorrow  settings for the Minors of Age 16 or 17")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheSchoolTodayAndSchoolTomorrowSettingsForTheMinorsOfAge16Or17AsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String minorName = "Minor17";
            String shiftTime1 = "8am,1pm";
            String shiftTime2 = "9am,4pm";
            String shiftTime3 = "9am,2pm";
            String workRole = "Team Member Corporate-Theatre";
            String scheduleFromToTime = "8:30am - 4pm";
            String scheduleMaxHours = "5";
            String selectWeekDayName = "Mon";
            verifyDayOvertimeViolationsForMinors(minorName, shiftTime1, shiftTime2, shiftTime3, workRole,
                    scheduleFromToTime, scheduleMaxHours, false, selectWeekDayName);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the School today and no school tomorrow  settings for the Minors of Age 14 or 15")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheSchoolTodayAndNoSchoolTomorrowSettingsForTheMinorsOfAge14Or15AsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String minorName = "Minor14";
            String shiftTime1 = "8am,3pm";
            String shiftTime2 = "9am,5pm";
            String shiftTime3 = "11am,4pm";
            String workRole = "Team Member Corporate-Theatre";
            String scheduleFromToTime = "8:30am - 5pm";
            String scheduleMaxHours = "5";
            String selectWeekDayName = "Fri";
            verifyDayOvertimeViolationsForMinors(minorName, shiftTime1, shiftTime2, shiftTime3,
                    workRole, scheduleFromToTime, scheduleMaxHours, false, selectWeekDayName);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the School today and no school tomorrow  settings for the Minors of Age 16 or 17")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheSchoolTodayAndNoSchoolTomorrowSettingsForTheMinorsOfAge16Or17AsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String minorName = "Minor17";
            String shiftTime1 = "8am,1pm";
            String shiftTime2 = "9am,5pm";
            String shiftTime3 = "9am,2pm";
            String workRole = "Team Member Corporate-Theatre";
            String scheduleFromToTime = "9am - 5pm";
            String scheduleMaxHours = "6";
            String selectWeekDayName = "Fri";
            verifyDayOvertimeViolationsForMinors(minorName, shiftTime1, shiftTime2, shiftTime3, workRole,
                    scheduleFromToTime, scheduleMaxHours, false, selectWeekDayName);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the no School today and no school tomorrow  settings for the Minors of Age 14 or 15")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheNoSchoolTodayAndNoSchoolTomorrowSettingsForTheMinorsOfAge14Or15AsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String minorName = "Minor14";
            String shiftTime1 = "8am,3pm";
            String shiftTime2 = "9am,6pm";
            String shiftTime3 = "9am,3pm";
            String workRole = "Team Member Corporate-Theatre";
            String scheduleFromToTime = "9am - 6pm";
            String scheduleMaxHours = "7";
            String selectWeekDayName = "Sat";
            verifyDayOvertimeViolationsForMinors(minorName, shiftTime1, shiftTime2, shiftTime3,
                    workRole, scheduleFromToTime, scheduleMaxHours, false, selectWeekDayName);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the no School today and no school tomorrow  settings for the Minors of Age 16 or 17")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheNoSchoolTodayAndNoSchoolTomorrowSettingsForTheMinorsOfAge16Or17AsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String minorName = "Minor17";
            String shiftTime1 = "8am,1pm";
            String shiftTime2 = "10am,7pm";
            String shiftTime3 = "10am,2pm";
            String workRole = "Team Member Corporate-Theatre";
            String scheduleFromToTime = "9:30am - 7pm";
            String scheduleMaxHours = "7";
            String selectWeekDayName = "Sat";
            verifyDayOvertimeViolationsForMinors(minorName, shiftTime1, shiftTime2, shiftTime3, workRole,
                    scheduleFromToTime, scheduleMaxHours, false, selectWeekDayName);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the no School today and school tomorrow  settings for the Minors of Age 14 or 15")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheNoSchoolTodayAndSchoolTomorrowSettingsForTheMinorsOfAge14Or15AsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String minorName = "Minor14";
            String shiftTime1 = "8am,3pm";
            String shiftTime2 = "10am,9pm";
            String shiftTime3 = "10am,4pm";
            String workRole = "Team Member Corporate-Theatre";
            String scheduleFromToTime = "9:30am - 9pm";
            String scheduleMaxHours = "9";
            String selectWeekDayName = "Sun";
            verifyDayOvertimeViolationsForMinors(minorName, shiftTime1, shiftTime2, shiftTime3, workRole, scheduleFromToTime, scheduleMaxHours, false, selectWeekDayName);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the no School today and school tomorrow  settings for the Minors of Age 16 or 17")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheNoSchoolTodayAndSchoolTomorrowSettingsForTheMinorsOfAge16Or17AsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String minorName = "Minor17";
            String shiftTime1 = "8am,1pm";
            String shiftTime2 = "10am,8pm";
            String shiftTime3 = "10am,2pm";
            String workRole = "Team Member Corporate-Theatre";
            String scheduleFromToTime = "10am - 8pm";
            String scheduleMaxHours = "8";
            String selectWeekDayName = "Sun";
            verifyDayOvertimeViolationsForMinors(minorName, shiftTime1, shiftTime2, shiftTime3, workRole, scheduleFromToTime, scheduleMaxHours, false, selectWeekDayName);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
      }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the summer day settings for the Minors of Age 14 or 15")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheSummerDaySettingsForTheMinorsOfAge14Or15AsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String minorName = "Minor14";
            String shiftTime1 = "8am,3pm";
            String shiftTime2 = "10am,10pm";
            String shiftTime3 = "10am,4pm";
            String workRole = "Team Member Corporate-Theatre";
            String scheduleFromToTime = "10am - 10pm";
            String scheduleMaxHours = "10";
            verifyDayOvertimeViolationsForMinors(minorName, shiftTime1, shiftTime2, shiftTime3, workRole, scheduleFromToTime, scheduleMaxHours, true, null);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the summer day  settings for the Minors of Age 16 or 17")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheSummerDaySettingsForTheMinorsOfAge16Or17AsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String minorName = "Minor17";
            String shiftTime1 = "8am,1pm";
            String shiftTime2 = "11am,10pm";
            String shiftTime3 = "11am,7pm";
            String workRole = "Team Member Corporate-Theatre";
            String scheduleFromToTime = "10:30am - 10pm";
            String scheduleMaxHours = "9";
            verifyDayOvertimeViolationsForMinors(minorName, shiftTime1, shiftTime2, shiftTime3, workRole, scheduleFromToTime, scheduleMaxHours, true, null);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    public void verifyDayOvertimeViolationsForMinors(String minorName, String shiftTime1, String shiftTime2, String shiftTime3, String workRole,
                                                     String scheduleFromToTime, String scheduleMaxHours, boolean isSummerWeek, String selectWeekDayName) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
        schedulePage.navigateToNextWeek();
        if (isSummerWeek){
            schedulePage.navigateToNextWeek();
        }
        boolean isWeekGenerated = schedulePage.isWeekGenerated();
        if (isWeekGenerated){
            schedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        List<String> toCloseDays = new ArrayList<>();
        //schedulePage.editOperatingHoursOnScheduleOldUIPage("6am", "11pm", toCloseDays);
        schedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( "06:00AM", "11:00PM");
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        String firstNameOfTM1 = cinemarkMinors.get(minorName);
        schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);
        schedulePage.saveSchedule();
        if(schedulePage.isRequiredActionSmartCardLoaded()){
            schedulePage.convertAllUnAssignedShiftToOpenShift();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteAllOOOHShiftInWeekView();
            schedulePage.saveSchedule();
        }
        schedulePage.publishActiveSchedule();

        //Create new shift with shift time is not during the minor setting for TM
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.customizeNewShiftPage();

        //set shift time
        schedulePage.moveSliderAtCertainPoint(shiftTime1.split(",")[1], ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.moveSliderAtCertainPoint(shiftTime1.split(",")[0], ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        if(!isSummerWeek){
            schedulePage.clearAllSelectedDays();
            schedulePage.selectWeekDaysByDayName(selectWeekDayName);
        }
        schedulePage.selectWorkRole(workRole);
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.searchText(firstNameOfTM1);

        //check the message in warning mode
        if(schedulePage.ifWarningModeDisplay()){
            String warningMessage1 = "As a minor, "+firstNameOfTM1+" should be scheduled from "+ scheduleFromToTime;
            String warningMessage2 = "Please confirm that you want to make this change.";
            if (schedulePage.getWarningMessageInDragShiftWarningMode().contains(warningMessage1)
                    && schedulePage.getWarningMessageInDragShiftWarningMode().contains(warningMessage2)){
                SimpleUtils.pass("The message in warning mode display correctly! ");
            } else
                SimpleUtils.fail("The message in warning mode display incorrectly! ", false);
            schedulePage.clickOnAssignAnywayButton();
        } else
            SimpleUtils.fail("There should have warning mode display with minor warning message! ",false);


        //check the violation message in Status column
        SimpleUtils.assertOnFail("There should have minor warning message display as: Minor hrs "+scheduleFromToTime+"! ",
                schedulePage.getTheMessageOfTMScheduledStatus().contains("Minor hrs "+ scheduleFromToTime), false);

        schedulePage.clickOnOfferOrAssignBtn();
        schedulePage.saveSchedule();

        //check the compliance smart card
        SimpleUtils.assertOnFail("The compliance smart card display correctly! ",
                schedulePage.verifyComplianceShiftsSmartCardShowing(), false);
        schedulePage.clickViewShift();
        //check the violation in i icon popup of new create shift
        WebElement newAddedShift = schedulePage.getTheShiftByIndex(schedulePage.getAddedShiftIndexes(firstNameOfTM1).get(0));
        if (newAddedShift != null) {
            SimpleUtils.assertOnFail("The minor violation message display incorrectly in i icon popup! ",
                    schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Minor hrs "+ scheduleFromToTime), false);
        } else
            SimpleUtils.fail("Get new added shift failed! ", false);

        schedulePage.verifyClearFilterFunction();
        //Create new shift with shift hours is more than minor setting for TM1
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);
        schedulePage.saveSchedule();
        schedulePage.publishActiveSchedule();
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.customizeNewShiftPage();

        //set shift time
        schedulePage.moveSliderAtCertainPoint(shiftTime2.split(",")[1], ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.moveSliderAtCertainPoint(shiftTime2.split(",")[0], ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        if(!isSummerWeek){
            schedulePage.clearAllSelectedDays();
            schedulePage.selectWeekDaysByDayName(selectWeekDayName);
        }
        schedulePage.selectWorkRole(workRole);
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.searchText(firstNameOfTM1);

        //check the message in warning mode
        if(schedulePage.ifWarningModeDisplay()){
            String warningMessage1 = "As a minor, "+firstNameOfTM1+"'s daily schedule should not exceed "+ scheduleMaxHours +" hours";
            String warningMessage2 = "Please confirm that you want to make this change.";
            if (schedulePage.getWarningMessageInDragShiftWarningMode().contains(warningMessage1)
                    && schedulePage.getWarningMessageInDragShiftWarningMode().contains(warningMessage2)){
                SimpleUtils.pass("The message in warning mode display correctly! ");
            } else
                SimpleUtils.fail("The message in warning mode display incorrectly! ", false);
            schedulePage.clickOnAssignAnywayButton();
        } else
            SimpleUtils.fail("There should have warning mode display with minor warning message! ",false);

        //check the violation message in Status column
        SimpleUtils.assertOnFail("There should have minor warning message display as: Minor daily max "+scheduleMaxHours+" hrs! ",
                schedulePage.getTheMessageOfTMScheduledStatus().contains("Minor daily max "+scheduleMaxHours+" hrs"), false);

        schedulePage.clickOnOfferOrAssignBtn();
        schedulePage.saveSchedule();
        //check the compliance smart card
        SimpleUtils.assertOnFail("The compliance smart card display correctly! ",
                schedulePage.verifyComplianceShiftsSmartCardShowing(), false);
        schedulePage.clickViewShift();
        //check the violation in i icon popup of new create shift
        newAddedShift = schedulePage.getTheShiftByIndex(schedulePage.getAddedShiftIndexes(firstNameOfTM1).get(0));
        if (newAddedShift != null) {
            SimpleUtils.assertOnFail("The minor violation message display incorrectly in i icon popup! ",
                    schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Minor daily max "+scheduleMaxHours+" hrs"), false);
        } else
            SimpleUtils.fail("Get new added shift failed", false);

        schedulePage.verifyClearFilterFunction();
        //Create new shift that not avoid the minor settings for TM1
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);
        schedulePage.saveSchedule();
        schedulePage.publishActiveSchedule();
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.customizeNewShiftPage();

        //set shift time
        schedulePage.moveSliderAtCertainPoint(shiftTime3.split(",")[1], ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.moveSliderAtCertainPoint(shiftTime3.split(",")[0], ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        if(!isSummerWeek){
            schedulePage.clearAllSelectedDays();
            schedulePage.selectWeekDaysByDayName(selectWeekDayName);
        }
        schedulePage.selectWorkRole(workRole);
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.searchText(firstNameOfTM1);
        SimpleUtils.assertOnFail("There should no minor warning message display when shift is not avoid the minor setting! ",
                !schedulePage.getTheMessageOfTMScheduledStatus().contains("Minor"), false);
        schedulePage.clickOnRadioButtonOfSearchedTeamMemberByName(firstNameOfTM1);
        if(schedulePage.ifWarningModeDisplay()){
            String warningMessage = schedulePage.getWarningMessageInDragShiftWarningMode();
            if (!warningMessage.contains("Minor ")){
                SimpleUtils.pass("There is no minor warning message display on the warning mode when shift is not avoid the minor setting! ");
            } else
                SimpleUtils.fail("There should no minor warning message display warning mode when shift is not avoid the minor setting! ", false);
            schedulePage.clickOnAssignAnywayButton();
        } else
            SimpleUtils.pass("There is no minor warning message display on search TM page shift is not avoid the minor setting! ");

        schedulePage.clickOnOfferOrAssignBtn();
        schedulePage.saveSchedule();

        //check the violation in i icon popup of new create shift
        newAddedShift = schedulePage.getTheShiftByIndex(schedulePage.getAddedShiftIndexes(firstNameOfTM1).get(0));
        if (newAddedShift != null) {
            SimpleUtils.assertOnFail("There should no minor warning message display on the i icon when shift is not avoid the minor setting! ",
                    !schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Minor"), false);
        } else
            SimpleUtils.fail("Get new added shift failed! ", false);
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the school week settings for the Minors of Age 14 or 15")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheSchoolWeekSettingsForTheMinorsOfAge14Or15AsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String minorName = "Minor14";
            String shiftTime1 = "10am,1pm";
            String shiftTime2 = "10am,4pm";
            int needCreateShiftsNumber1 = 4;
            int needCreateShiftsNumber2 = 2;
            String workRole = "Team Member Corporate-Theatre";
            String maxOfDays = "4";
            String maxOfScheduleHours = "15";
            verifyWeekOvertimeViolationsForMinors(minorName, shiftTime1, shiftTime2, workRole, needCreateShiftsNumber1,
                    needCreateShiftsNumber2, maxOfDays, maxOfScheduleHours, true, false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
     }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the non school week settings for the Minors of Age 14 or 15")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheNonSchoolWeekSettingsForTheMinorsOfAge14Or15AsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String minorName = "Minor14";
            String shiftTime1 = "11am,1pm";
            String shiftTime2 = "10am,4pm";
            int needCreateShiftsNumber1 = 5;
            int needCreateShiftsNumber2 = 2;
            String workRole = "Team Member Corporate-Theatre";
            String maxOfDays = "5";
            String maxOfScheduleHours = "16";
            verifyWeekOvertimeViolationsForMinors(minorName, shiftTime1, shiftTime2, workRole, needCreateShiftsNumber1,
                    needCreateShiftsNumber2, maxOfDays, maxOfScheduleHours, false, true);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the summer week settings for the Minors of Age 14 or 15")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheSummerWeekSettingsForTheMinorsOfAge14Or15AsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String minorName = "Minor14";
            String shiftTime1 = "11am,1pm";
            String shiftTime2 = "10am,5pm";
            int needCreateShiftsNumber1 = 6;
            int needCreateShiftsNumber2 = 2;
            String workRole = "Team Member Corporate-Theatre";
            String maxOfDays = "6";
            String maxOfScheduleHours = "17";
            verifyWeekOvertimeViolationsForMinors(minorName, shiftTime1, shiftTime2, workRole, needCreateShiftsNumber1,
                    needCreateShiftsNumber2, maxOfDays, maxOfScheduleHours, false, false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the school week  settings for the Minors of Age 16 or 17")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheSchoolWeekSettingsForTheMinorsOfAge16Or17AsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String minorName = "Minor17";
            String shiftTime1 = "11am,1pm";
            String shiftTime2 = "11am,4pm";
            int needCreateShiftsNumber1 = 6;
            int needCreateShiftsNumber2 = 4;
            String workRole = "Team Member Corporate-Theatre";
            String maxOfDays = "6";
            String maxOfScheduleHours = "18";
            verifyWeekOvertimeViolationsForMinors(minorName, shiftTime1, shiftTime2, workRole, needCreateShiftsNumber1,
                    needCreateShiftsNumber2, maxOfDays, maxOfScheduleHours, true, false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the non school week  settings for the Minors of Age 16 or 17")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheNonSchoolWeekSettingsForTheMinorsOfAge16Or17AsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String minorName = "Minor17";
            String shiftTime1 = "11am,1pm";
            String shiftTime2 = "11am,4pm";
            int needCreateShiftsNumber1 = 4;
            int needCreateShiftsNumber2 = 3;
            String workRole = "Team Member Corporate-Theatre";
            String maxOfDays = "4";
            String maxOfScheduleHours = "16";
            verifyWeekOvertimeViolationsForMinors(minorName, shiftTime1, shiftTime2, workRole, needCreateShiftsNumber1,
                    needCreateShiftsNumber2, maxOfDays, maxOfScheduleHours, false, true);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the summer week settings for the Minors of Age 16 or 17")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheSummerWeekSettingsForTheMinorsOfAge16Or17AsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String minorName = "Minor17";
            String shiftTime1 = "11am,1pm";
            String shiftTime2 = "11am,5pm";
            int needCreateShiftsNumber1 = 5;
            int needCreateShiftsNumber2 = 3;
            String workRole = "Team Member Corporate-Theatre";
            String maxOfDays = "5";
            String maxOfScheduleHours = "17";
            verifyWeekOvertimeViolationsForMinors(minorName, shiftTime1, shiftTime2, workRole, needCreateShiftsNumber1,
                    needCreateShiftsNumber2, maxOfDays, maxOfScheduleHours, false, false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    public void verifyWeekOvertimeViolationsForMinors(String minorName, String shiftTime1, String shiftTime2, String workRole,
                                                      int needCreateShiftsNumber1, int needCreateShiftsNumber2,
                                                      String maxOfDays, String maxOfScheduleHours,
                                                      boolean isSchoolWeek, boolean isNonSchoolWeek) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

        if (isSchoolWeek){
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
        } else if (isNonSchoolWeek){
            schedulePage.navigateToNextWeek();
        }
        boolean isWeekGenerated = schedulePage.isWeekGenerated();
        if (isWeekGenerated){
            schedulePage.unGenerateActiveScheduleScheduleWeek();
        }
//        List<String> toCloseDays = new ArrayList<>();
        //schedulePage.editOperatingHoursOnScheduleOldUIPage("6", "23", toCloseDays);
        schedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( "06:00AM", "11:00PM");
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        String firstNameOfTM1 = cinemarkMinors.get(minorName);
        schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);
        schedulePage.saveSchedule();

        //Create new shift with shift time is not during the minor setting for TM
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.customizeNewShiftPage();

        //set shift time
        schedulePage.moveSliderAtCertainPoint(shiftTime1.split(",")[1], ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.moveSliderAtCertainPoint(shiftTime1.split(",")[0], ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        schedulePage.clearAllSelectedDays();
        schedulePage.selectSpecificWorkDay(needCreateShiftsNumber1);
        schedulePage.selectWorkRole(workRole);
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.searchTeamMemberByName(firstNameOfTM1);
        schedulePage.clickOnOfferOrAssignBtn();
        schedulePage.saveSchedule();

        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.customizeNewShiftPage();
        schedulePage.moveSliderAtCertainPoint(shiftTime1.split(",")[1], ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.moveSliderAtCertainPoint(shiftTime1.split(",")[0], ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        schedulePage.clearAllSelectedDays();
        schedulePage.selectDaysByIndex(needCreateShiftsNumber1, needCreateShiftsNumber1, needCreateShiftsNumber1);
        schedulePage.selectWorkRole(workRole);
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.searchText(firstNameOfTM1);
        //check the message in warning mode
        if(schedulePage.ifWarningModeDisplay()){
            String warningMessage1 = "As a minor, "+firstNameOfTM1+"'s weekly schedule should not exceed "+ maxOfDays +" days";
            String warningMessage2 = "Please confirm that you want to make this change.";
            if (schedulePage.getWarningMessageInDragShiftWarningMode().contains(warningMessage1)
                    && schedulePage.getWarningMessageInDragShiftWarningMode().contains(warningMessage2)){
                SimpleUtils.pass("The message in warning mode display correctly! ");
            } else
                SimpleUtils.fail("The message in warning mode display incorrectly! ", false);
            schedulePage.clickOnAssignAnywayButton();
        } else
            SimpleUtils.fail("There should have warning mode display with minor warning message! ",false);


        //check the violation message in Status column
        SimpleUtils.assertOnFail("There should have minor warning message display as: Minor weekly max "+maxOfDays+"days! ",
                schedulePage.getTheMessageOfTMScheduledStatus().contains("Minor weekly max "+ maxOfDays+ " days"), false);
        schedulePage.clickOnOfferOrAssignBtn();
        schedulePage.saveSchedule();

        //check the compliance smart card
        SimpleUtils.assertOnFail("The compliance smart card display correctly! ",
                schedulePage.verifyComplianceShiftsSmartCardShowing(), false);
        schedulePage.clickViewShift();
        //check the violation in i icon popup of new create shift
        WebElement newAddedShift = schedulePage.getOneDayShiftByName(needCreateShiftsNumber1, firstNameOfTM1).get(0);
        if (newAddedShift != null) {
            SimpleUtils.assertOnFail("The minor violation message display incorrectly in i icon popup! ",
                    schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Minor weekly max "+ maxOfDays+ " days"), false);
        } else
            SimpleUtils.fail("Get new added shift failed! ", false);

        //to close the i icon popup
        schedulePage.publishActiveSchedule();
        schedulePage.verifyClearFilterFunction();
        //Create new shift with shift hours is more than minor setting for TM1
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);
        schedulePage.saveSchedule();
        schedulePage.publishActiveSchedule();
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.customizeNewShiftPage();
        schedulePage.moveSliderAtCertainPoint(shiftTime2.split(",")[1], ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.moveSliderAtCertainPoint(shiftTime2.split(",")[0], ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        schedulePage.clearAllSelectedDays();
        schedulePage.selectSpecificWorkDay(needCreateShiftsNumber2);
        schedulePage.selectWorkRole(workRole);
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.searchTeamMemberByName(firstNameOfTM1);
        schedulePage.clickOnOfferOrAssignBtn();
        schedulePage.saveSchedule();


        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.customizeNewShiftPage();
        schedulePage.moveSliderAtCertainPoint(shiftTime2.split(",")[1], ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.moveSliderAtCertainPoint(shiftTime2.split(",")[0], ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        schedulePage.clearAllSelectedDays();
        schedulePage.selectDaysByIndex(needCreateShiftsNumber2, needCreateShiftsNumber2, needCreateShiftsNumber2);
        schedulePage.selectWorkRole(workRole);
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.searchText(firstNameOfTM1);

        //check the message in warning mode
        if(schedulePage.ifWarningModeDisplay()){
            String warningMessage1 = "As a minor, "+firstNameOfTM1+"'s weekly schedule should not exceed "+ maxOfScheduleHours +" hours";
            String warningMessage2 = "Please confirm that you want to make this change.";
            if (schedulePage.getWarningMessageInDragShiftWarningMode().contains(warningMessage1)
                    && schedulePage.getWarningMessageInDragShiftWarningMode().contains(warningMessage2)){
                SimpleUtils.pass("The message in warning mode display correctly! ");
            } else
                SimpleUtils.fail("The message in warning mode display incorrectly! ", false);
            schedulePage.clickOnAssignAnywayButton();
        } else
            SimpleUtils.fail("There should have warning mode display with minor warning message! ",false);

        //check the violation message in Status column
        SimpleUtils.assertOnFail("There should have minor warning message display as: Minor weekly max "+maxOfScheduleHours+" hrs! ",
                schedulePage.getTheMessageOfTMScheduledStatus().contains("Minor weekly max "+maxOfScheduleHours+" hrs"), false);

        schedulePage.clickOnOfferOrAssignBtn();
        schedulePage.saveSchedule();

        //check the violation in i icon popup of new create shift
        SimpleUtils.assertOnFail("The compliance smart card display correctly! ",
                schedulePage.verifyComplianceShiftsSmartCardShowing(), false);
        schedulePage.clickViewShift();
        newAddedShift = schedulePage.getOneDayShiftByName(needCreateShiftsNumber2, firstNameOfTM1).get(0);
        if (newAddedShift != null) {
            SimpleUtils.assertOnFail("The minor violation message display incorrectly in i icon popup! ",
                    schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Minor weekly max "+maxOfScheduleHours+" hrs"), false);
        } else
            SimpleUtils.fail("Get new added shift failed", false);
    }

}