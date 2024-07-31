package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.core.ConsoleAnalyticsPage;
import com.legion.pages.core.schedule.ConsoleMySchedulePage;
import com.legion.pages.core.schedule.ConsoleSmartCardPage;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ScheduleAttestationTest extends TestBase {



    private DashboardPage dashboardPage;
    private CreateSchedulePage createSchedulePage;
    private ScheduleMainPage scheduleMainPage;
    private ScheduleShiftTablePage scheduleShiftTablePage;
    private ScheduleCommonPage scheduleCommonPage;
    private EditShiftPage editShiftPage;
    private NewShiftPage newShiftPage;
    private ShiftOperatePage shiftOperatePage;
    private ControlsPage controlsPage;
    private ControlsNewUIPage controlsNewUIPage;
    private MySchedulePage mySchedulePage;
    private SmartCardPage smartCardPage;
    private ProfileNewUIPage profileNewUIPage;
    private LoginPage loginPage;
    private ScheduleAttestationPage scheduleAttestationPage;
    private AnalyticsPage analyticsPage;
    private ActivityPage activityPage;
    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception {
        this.createDriver((String) params[0], "69", "Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
        dashboardPage = pageFactory.createConsoleDashboardPage();
        createSchedulePage = pageFactory.createCreateSchedulePage();
        scheduleMainPage = pageFactory.createScheduleMainPage();
        scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        scheduleCommonPage = pageFactory.createScheduleCommonPage();
        editShiftPage = pageFactory.createEditShiftPage();
        newShiftPage = pageFactory.createNewShiftPage();
        shiftOperatePage = pageFactory.createShiftOperatePage();
        controlsPage = pageFactory.createConsoleControlsPage();
        controlsNewUIPage = pageFactory.createControlsNewUIPage();
        mySchedulePage = pageFactory.createMySchedulePage();
        smartCardPage = pageFactory.createSmartCardPage();
        profileNewUIPage = pageFactory.createProfileNewUIPage();
        loginPage = pageFactory.createConsoleLoginPage();
        scheduleAttestationPage = pageFactory.createScheduleAttestationPage();
        analyticsPage = pageFactory.createConsoleAnalyticsPage();
        activityPage = pageFactory.createConsoleActivityPage();
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify employee can accept and decline the add new shift consent")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyEmployeeCanAcceptAndDeclineTheAddNewShiftConsentAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            profileNewUIPage.clickOnUserProfileImage();
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage("My Profile");
            String tmFullName = profileNewUIPage.getUserProfileName().get("fullName");
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            String firstName = tmFullName.split(" ")[0];
            goToSchedulePageScheduleTab();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( "06:00AM", "11:00PM");
            if (shiftOperatePage.countShiftsByUserName(firstName)>0){
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(firstName);
                scheduleMainPage.saveSchedule();
            }

            //Before publish, there is no Consent Required smart card
            String smartCardName = ConsoleSmartCardPage.smartCardNames.ConsentRequired.getValue();
            SimpleUtils.assertOnFail("CONSENT REQUIRED smart card should not show before publish schedule! ",
                    !smartCardPage.isSpecificSmartCardLoaded(ConsoleSmartCardPage.smartCardNames.ConsentRequired.getValue()), false);

            createSchedulePage.publishActiveSchedule();
            //After publish, the Consent Required smart card will show
            SimpleUtils.assertOnFail("CONSENT REQUIRED smart card will show after publish schedule! ",
                    smartCardPage.isSpecificSmartCardLoaded(smartCardName), false);
            int consentCount = smartCardPage.getCountFromSmartCardByName(smartCardName);
            scheduleCommonPage.clickOnDayView();
            scheduleCommonPage.navigateDayViewWithIndex(0);
            Map<String, String> dayInfo = scheduleCommonPage.getActiveDayInfo();
            String year = dayInfo.get("year");
            String fullWeekDay1 = dayInfo.get("weekDay");
            String fullMonth1 = dayInfo.get("month");
            String day1 = dayInfo.get("day");
            scheduleCommonPage.navigateDayViewWithIndex(1);
            Map<String, String> dayInfo2 = scheduleCommonPage.getActiveDayInfo();
            String fullWeekDay2 = dayInfo2.get("weekDay");
            String fullMonth2 = dayInfo2.get("month");
            String day2 = dayInfo2.get("day");
            String firstDayDateInfo = fullWeekDay1+ ", "+fullMonth1+ " "+day1;
            String secondDayDateInfo = fullWeekDay2+ ", "+fullMonth2+ " "+day2;
            scheduleCommonPage.clickOnWeekView();
            //Create two new shifts for one employee
            String workRole = shiftOperatePage.getRandomWorkRole();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            createShiftsWithSpecificValues(workRole, "", "", "1:00pm", "5:00pm",
                    1, Arrays.asList(0,1), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
            scheduleMainPage.clickSaveScheduleButtonOnly();
            //The Shift Date and Team Member name should correct, change should be Added (x hrs)
            SimpleUtils.assertOnFail("The schedule change reason modal should load! ",
                    scheduleAttestationPage.isScheduleChangeReasonModalLoaded(), false);
            Map<String, String> firstShiftInfo = scheduleAttestationPage.getScheduleChangesByIndex(0);
            Map<String, String> secondShiftInfo = scheduleAttestationPage.getScheduleChangesByIndex(1);

            //Verify shift date is correct
            SimpleUtils.assertOnFail("The expected date info is:"+firstDayDateInfo+", but the actual is: "+firstShiftInfo.get("shiftDate"),
                    firstDayDateInfo.equalsIgnoreCase(firstShiftInfo.get("shiftDate")), false);

            //Verify team member name is correct
            SimpleUtils.assertOnFail("The expected tm name is:"+firstDayDateInfo+", but the actual is: "+firstShiftInfo.get("shiftDate"),
                    tmFullName.equalsIgnoreCase(firstShiftInfo.get("teamMemberName")), false);

            //Verify change info is correct
            String changeInfo = "Added (4.0 hrs)";
            SimpleUtils.assertOnFail("The expected change info is:"+changeInfo+", but the actual is: "+firstShiftInfo.get("changeInfo"),
                    changeInfo.equalsIgnoreCase(firstShiftInfo.get("changeInfo")), false);

            //Verify shift date is correct
            SimpleUtils.assertOnFail("The expected date info is:"+secondDayDateInfo+", but the actual is: "+secondShiftInfo.get("shiftDate"),
                    secondDayDateInfo.equalsIgnoreCase(secondShiftInfo.get("shiftDate")), false);

            //Verify team member name is correct
            SimpleUtils.assertOnFail("The expected tm name is:"+secondDayDateInfo+", but the actual is: "+secondShiftInfo.get("shiftDate"),
                    tmFullName.equalsIgnoreCase(secondShiftInfo.get("teamMemberName")), false);

            //Verify change info is correct
            SimpleUtils.assertOnFail("The expected change info is:"+changeInfo+", but the actual is: "+secondShiftInfo.get("changeInfo"),
                    changeInfo.equalsIgnoreCase(secondShiftInfo.get("changeInfo")), false);
            scheduleAttestationPage.clickSaveButtonOnScheduleChangeReasonModal();
            createSchedulePage.publishActiveSchedule();

            //The Consent count will add two more
            SimpleUtils.assertOnFail("CONSENT REQUIRED smart card will show after publish schedule! ",
                    smartCardPage.getCountFromSmartCardByName(smartCardName) == consentCount +2, false);

            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            String smartCardNameOfTMConsent = ConsoleSmartCardPage.smartCardNames.ScheduleChange.getValue();
            SimpleUtils.assertOnFail("Schedule Change smart card should show on employee view! ",
                    smartCardPage.isSpecificSmartCardLoaded(smartCardNameOfTMConsent), false);
            consentCount = smartCardPage.getCountFromSmartCardByName(smartCardNameOfTMConsent);

            mySchedulePage.clickOnShiftByIndex(0);
            List<String> acceptAndDeclineConsent = new ArrayList<>(Arrays.asList("Accept schedule change", "Decline schedule change"));
            if (mySchedulePage.verifyShiftRequestButtonOnPopup(acceptAndDeclineConsent)) {
                SimpleUtils.pass("Accept schedule change and Decline schedule change options are shown");
            }else {
                SimpleUtils.fail("Accept schedule change and Decline schedule change options are not shown!", false);
            }

            //Accept successfully
            String request = ConsoleMySchedulePage.shiftConsentType.AcceptScheduleChange.getValue();
            mySchedulePage.clickTheShiftRequestByName(request);
            mySchedulePage.verifyThePopupMessageOnTop("Success");
            //Consent count reduce 1
            SimpleUtils.assertOnFail("The expected consent count is: "+(consentCount-1)+ ", the actual is: "
                            +smartCardPage.getCountFromSmartCardByName(smartCardNameOfTMConsent),
                    consentCount-1 == smartCardPage.getCountFromSmartCardByName(smartCardNameOfTMConsent), false);

            mySchedulePage.clickOnShiftByIndex(1);
            if (mySchedulePage.verifyShiftRequestButtonOnPopup(acceptAndDeclineConsent)) {
                SimpleUtils.pass("Accept schedule change and Decline schedule change options are shown");
            }else {
                SimpleUtils.fail("Accept schedule change and Decline schedule change options are not shown!", false);
            }
            //Decline successfully
            request = ConsoleMySchedulePage.shiftConsentType.DeclineScheduleChange.getValue();
            String declineNotes ="";
            mySchedulePage.clickTheShiftRequestByName(request);
            mySchedulePage.verifyClickOnSubmitButton();
            //Consent count reduce 1
            if (smartCardPage.isSpecificSmartCardLoaded(smartCardNameOfTMConsent)){
                SimpleUtils.assertOnFail("The expected consent count is: "+(consentCount-1)+ ", the actual is: "
                                +smartCardPage.getCountFromSmartCardByName(smartCardNameOfTMConsent),
                        consentCount-2 == smartCardPage.getCountFromSmartCardByName(smartCardNameOfTMConsent), false);
            }

            //Login as SM and check the consent activities
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.Schedule.getValue(),
                    ActivityTest.indexOfActivityType.Schedule.name());
            List<String> scheduleChangeActivityMessages = activityPage.getScheduleChangeActivity(tmFullName);
            String expectedMessage1 = tmFullName+" accepted an added hours schedule change for "+workRole+" shift on "
                    +fullMonth1+ " "+day1+", "+year+" .";
            String expectedMessage2 = tmFullName+" declined an added hours schedule change for "+workRole+" shift on "
                    +fullMonth2+ " "+day2+", "+year+" with the note:"+declineNotes+" .";
            SimpleUtils.assertOnFail("The expected message is "+expectedMessage1+" "+expectedMessage2
                            +". The actual message is:"+scheduleChangeActivityMessages,
                    scheduleChangeActivityMessages.contains(expectedMessage1)
                            &&scheduleChangeActivityMessages.contains(expectedMessage2), false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify employee can accept and decline the deleted shift consent")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyEmployeeCanAcceptAndDeclineTheDeletedShiftConsentAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            profileNewUIPage.clickOnUserProfileImage();
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage("My Profile");
            String tmFullName = profileNewUIPage.getUserProfileName().get("fullName");
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            String firstName = tmFullName.split(" ")[0];
            goToSchedulePageScheduleTab();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( "06:00AM", "11:00PM");
            if (shiftOperatePage.countShiftsByUserName(firstName)>0){
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(firstName);
                scheduleMainPage.saveSchedule();
            }

            //Before publish, there is no Consent Required smart card
            String smartCardName = ConsoleSmartCardPage.smartCardNames.ConsentRequired.getValue();
            SimpleUtils.assertOnFail("CONSENT REQUIRED smart card should not show before publish schedule! ",
                    !smartCardPage.isSpecificSmartCardLoaded(ConsoleSmartCardPage.smartCardNames.ConsentRequired.getValue()), false);

            createSchedulePage.publishActiveSchedule();
            //After publish, the Consent Required smart card will show
            SimpleUtils.assertOnFail("CONSENT REQUIRED smart card will show after publish schedule! ",
                    smartCardPage.isSpecificSmartCardLoaded(smartCardName), false);
            int consentCount = smartCardPage.getCountFromSmartCardByName(smartCardName);
            scheduleCommonPage.clickOnDayView();
            scheduleCommonPage.navigateDayViewWithIndex(0);
            Map<String, String> dayInfo = scheduleCommonPage.getActiveDayInfo();
            String year = dayInfo.get("year");
            String fullWeekDay1 = dayInfo.get("weekDay");
            String fullMonth1 = dayInfo.get("month");
            String day1 = dayInfo.get("day");
            scheduleCommonPage.navigateDayViewWithIndex(1);
            Map<String, String> dayInfo2 = scheduleCommonPage.getActiveDayInfo();
            String fullWeekDay2 = dayInfo2.get("weekDay");
            String fullMonth2 = dayInfo2.get("month");
            String day2 = dayInfo2.get("day");
            String firstDayDateInfo = fullWeekDay1+ ", "+fullMonth1+ " "+day1;
            String secondDayDateInfo = fullWeekDay2+ ", "+fullMonth2+ " "+day2;
            scheduleCommonPage.clickOnWeekView();
            //Create two new shifts for one employee
            String workRole = shiftOperatePage.getRandomWorkRole();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            createShiftsWithSpecificValues(workRole, "", "", "1:00pm", "5:00pm",
                    1, Arrays.asList(0,1), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
            scheduleMainPage.clickSaveScheduleButtonOnly();
            //The Shift Date and Team Member name should correct, change should be Added (x hrs)
            SimpleUtils.assertOnFail("The schedule change reason modal should load! ",
                    scheduleAttestationPage.isScheduleChangeReasonModalLoaded(), false);
            Map<String, String> firstShiftInfo = scheduleAttestationPage.getScheduleChangesByIndex(0);
            Map<String, String> secondShiftInfo = scheduleAttestationPage.getScheduleChangesByIndex(1);

            //Verify shift date is correct
            SimpleUtils.assertOnFail("The expected date info is:"+firstDayDateInfo+", but the actual is: "+firstShiftInfo.get("shiftDate"),
                    firstDayDateInfo.equalsIgnoreCase(firstShiftInfo.get("shiftDate")), false);

            //Verify team member name is correct
            SimpleUtils.assertOnFail("The expected tm name is:"+firstDayDateInfo+", but the actual is: "+firstShiftInfo.get("shiftDate"),
                    tmFullName.equalsIgnoreCase(firstShiftInfo.get("teamMemberName")), false);

            //Verify change info is correct
            String changeInfo = "Added (4.0 hrs)";
            SimpleUtils.assertOnFail("The expected change info is:"+changeInfo+", but the actual is: "+firstShiftInfo.get("changeInfo"),
                    changeInfo.equalsIgnoreCase(firstShiftInfo.get("changeInfo")), false);

            //Verify shift date is correct
            SimpleUtils.assertOnFail("The expected date info is:"+secondDayDateInfo+", but the actual is: "+secondShiftInfo.get("shiftDate"),
                    secondDayDateInfo.equalsIgnoreCase(secondShiftInfo.get("shiftDate")), false);

            //Verify team member name is correct
            SimpleUtils.assertOnFail("The expected tm name is:"+secondDayDateInfo+", but the actual is: "+secondShiftInfo.get("shiftDate"),
                    tmFullName.equalsIgnoreCase(secondShiftInfo.get("teamMemberName")), false);

            //Verify change info is correct
            SimpleUtils.assertOnFail("The expected change info is:"+changeInfo+", but the actual is: "+secondShiftInfo.get("changeInfo"),
                    changeInfo.equalsIgnoreCase(secondShiftInfo.get("changeInfo")), false);
            scheduleAttestationPage.clickSaveButtonOnScheduleChangeReasonModal();
            createSchedulePage.publishActiveSchedule();

            //The Consent count will add two more
            SimpleUtils.assertOnFail("CONSENT REQUIRED smart card will show after publish schedule! ",
                    smartCardPage.getCountFromSmartCardByName(smartCardName) == consentCount +2, false);

            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            String smartCardNameOfTMConsent = ConsoleSmartCardPage.smartCardNames.ScheduleChange.getValue();
            SimpleUtils.assertOnFail("Schedule Change smart card should show on employee view! ",
                    smartCardPage.isSpecificSmartCardLoaded(smartCardNameOfTMConsent), false);
            consentCount = smartCardPage.getCountFromSmartCardByName(smartCardNameOfTMConsent);

            mySchedulePage.clickOnShiftByIndex(0);
            List<String> acceptAndDeclineConsent = new ArrayList<>(Arrays.asList("Accept schedule change", "Decline schedule change"));
            if (mySchedulePage.verifyShiftRequestButtonOnPopup(acceptAndDeclineConsent)) {
                SimpleUtils.pass("Accept schedule change and Decline schedule change options are shown");
            }else {
                SimpleUtils.fail("Accept schedule change and Decline schedule change options are not shown!", false);
            }

            //Accept successfully
            String request = ConsoleMySchedulePage.shiftConsentType.AcceptScheduleChange.getValue();
            mySchedulePage.clickTheShiftRequestByName(request);
            mySchedulePage.verifyThePopupMessageOnTop("Success");
            //Consent count reduce 1
            SimpleUtils.assertOnFail("The expected consent count is: "+(consentCount-1)+ ", the actual is: "
                            +smartCardPage.getCountFromSmartCardByName(smartCardNameOfTMConsent),
                    consentCount-1 == smartCardPage.getCountFromSmartCardByName(smartCardNameOfTMConsent), false);

            mySchedulePage.clickOnShiftByIndex(1);
            if (mySchedulePage.verifyShiftRequestButtonOnPopup(acceptAndDeclineConsent)) {
                SimpleUtils.pass("Accept schedule change and Decline schedule change options are shown");
            }else {
                SimpleUtils.fail("Accept schedule change and Decline schedule change options are not shown!", false);
            }
            //Decline successfully
            request = ConsoleMySchedulePage.shiftConsentType.DeclineScheduleChange.getValue();
            String declineNotes ="";
            mySchedulePage.clickTheShiftRequestByName(request);
            mySchedulePage.verifyClickOnSubmitButton();
            //Consent count reduce 1
            if (smartCardPage.isSpecificSmartCardLoaded(smartCardNameOfTMConsent)){
                SimpleUtils.assertOnFail("The expected consent count is: "+(consentCount-1)+ ", the actual is: "
                                +smartCardPage.getCountFromSmartCardByName(smartCardNameOfTMConsent),
                        consentCount-2 == smartCardPage.getCountFromSmartCardByName(smartCardNameOfTMConsent), false);
            }

            //Login as admin and delete the new added shifts
            loginPage.logOut();
            Thread.sleep(5000);
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            goToSchedulePageScheduleTab();
            consentCount = smartCardPage.getCountFromSmartCardByName(smartCardName);
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(firstName);
            scheduleMainPage.clickSaveScheduleButtonOnly();

            //The Shift Date and Team Member name should correct, change should be Added (x hrs)
            SimpleUtils.assertOnFail("The schedule change reason modal should load! ",
                    scheduleAttestationPage.isScheduleChangeReasonModalLoaded(), false);
            firstShiftInfo = scheduleAttestationPage.getScheduleChangesByIndex(0);
            secondShiftInfo = scheduleAttestationPage.getScheduleChangesByIndex(1);

            //Verify shift date is correct
            SimpleUtils.assertOnFail("The expected date info is:"+firstDayDateInfo+", but the actual is: "+firstShiftInfo.get("shiftDate"),
                    firstDayDateInfo.equalsIgnoreCase(firstShiftInfo.get("shiftDate")), false);

            //Verify team member name is correct
            SimpleUtils.assertOnFail("The expected tm name is:"+firstDayDateInfo+", but the actual is: "+firstShiftInfo.get("shiftDate"),
                    tmFullName.equalsIgnoreCase(firstShiftInfo.get("teamMemberName")), false);

            //Verify change info is correct
            changeInfo = "Shift deleted (4.0 hrs)";
            SimpleUtils.assertOnFail("The expected change info is:"+changeInfo+", but the actual is: "+firstShiftInfo.get("changeInfo"),
                    changeInfo.equalsIgnoreCase(firstShiftInfo.get("changeInfo")), false);

            //Verify shift date is correct
            SimpleUtils.assertOnFail("The expected date info is:"+secondDayDateInfo+", but the actual is: "+secondShiftInfo.get("shiftDate"),
                    secondDayDateInfo.equalsIgnoreCase(secondShiftInfo.get("shiftDate")), false);

            //Verify team member name is correct
            SimpleUtils.assertOnFail("The expected tm name is:"+secondDayDateInfo+", but the actual is: "+secondShiftInfo.get("shiftDate"),
                    tmFullName.equalsIgnoreCase(secondShiftInfo.get("teamMemberName")), false);

            //Verify change info is correct
            SimpleUtils.assertOnFail("The expected change info is:"+changeInfo+", but the actual is: "+secondShiftInfo.get("changeInfo"),
                    changeInfo.equalsIgnoreCase(secondShiftInfo.get("changeInfo")), false);
            scheduleAttestationPage.clickSaveButtonOnScheduleChangeReasonModal();
            createSchedulePage.publishActiveSchedule();

            //The Consent count will add two more
            SimpleUtils.assertOnFail("CONSENT REQUIRED count incorrect, the expected is "+(consentCount +2)
                            +", actual is:"+smartCardPage.getCountFromSmartCardByName(smartCardName),
                    smartCardPage.getCountFromSmartCardByName(smartCardName) == consentCount, false);

            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            smartCardNameOfTMConsent = ConsoleSmartCardPage.smartCardNames.ScheduleChange.getValue();
            SimpleUtils.assertOnFail("Schedule Change smart card should show on employee view! ",
                    smartCardPage.isSpecificSmartCardLoaded(smartCardNameOfTMConsent), false);
            consentCount = smartCardPage.getCountFromSmartCardByName(smartCardNameOfTMConsent);
            String linkName = "View Shifts";
            smartCardPage.clickLinkOnSmartCardByName(linkName);

            mySchedulePage.clickOnShiftByIndex(0);
            if (mySchedulePage.verifyShiftRequestButtonOnPopup(acceptAndDeclineConsent)) {
                SimpleUtils.pass("Accept schedule change and Decline schedule change options are shown");
            }else {
                SimpleUtils.fail("Accept schedule change and Decline schedule change options are not shown!", false);
            }

            //Accept successfully
            request = ConsoleMySchedulePage.shiftConsentType.AcceptScheduleChange.getValue();
            mySchedulePage.clickTheShiftRequestByName(request);
            mySchedulePage.verifyThePopupMessageOnTop("Success");
            //Consent count reduce 1
            SimpleUtils.assertOnFail("The expected consent count is: "+(consentCount-1)+ ", the actual is: "
                            +smartCardPage.getCountFromSmartCardByName(smartCardNameOfTMConsent),
                    consentCount-1 == smartCardPage.getCountFromSmartCardByName(smartCardNameOfTMConsent), false);

            mySchedulePage.clickOnShiftByIndex(0);
            if (mySchedulePage.verifyShiftRequestButtonOnPopup(acceptAndDeclineConsent)) {
                SimpleUtils.pass("Accept schedule change and Decline schedule change options are shown");
            }else {
                SimpleUtils.fail("Accept schedule change and Decline schedule change options are not shown!", false);
            }
            //Decline successfully
            request = ConsoleMySchedulePage.shiftConsentType.DeclineScheduleChange.getValue();
            mySchedulePage.clickTheShiftRequestByName(request);
            mySchedulePage.verifyClickOnSubmitButton();
            //Consent count reduce 1
            if (smartCardPage.isSpecificSmartCardLoaded(smartCardNameOfTMConsent)){
                SimpleUtils.assertOnFail("The expected consent count is: "+(consentCount-1)+ ", the actual is: "
                                +smartCardPage.getCountFromSmartCardByName(smartCardNameOfTMConsent),
                        consentCount-2 == smartCardPage.getCountFromSmartCardByName(smartCardNameOfTMConsent), false);
            }

            //Login as SM and check the consent activities
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.Schedule.getValue(),
                    ActivityTest.indexOfActivityType.Schedule.name());
//            String tmFullName = "Aaron Bierwirth";
//            String fullMonth1 = "Jun";
//            String day1 = "14";
//            String fullMonth2 = "Jun";
//            String day2 = "15";
//            String year = "2024";
//            String workRole = "General Manager";
            List<String> scheduleChangeActivityMessages = activityPage.getScheduleChangeActivity(tmFullName);
            String expectedMessage1 = tmFullName+" accepted a deleted shift schedule change for "+workRole+" shift on "
                    +fullMonth1+ " "+day1+", "+year+" .";
            String expectedMessage2 = tmFullName+" declined a deleted shift schedule change for "+workRole+" shift on "
                    +fullMonth2+ " "+day2+", "+year+" with the note:"+declineNotes+" .";
            SimpleUtils.assertOnFail("The expected message is "+expectedMessage1+" "+expectedMessage2
                            +" The actual message is:"+scheduleChangeActivityMessages,
                    scheduleChangeActivityMessages.contains(expectedMessage1)
                            &&scheduleChangeActivityMessages.contains(expectedMessage2), false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


//    @Automated(automated ="Automated")
//    @Owner(owner = "Mary")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
//    @TestName(description = "Test extract")
//    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
//    public void verifyTextAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
//        try {
//            analyticsPage.clickOnAnalyticsConsoleMenu();
//            analyticsPage.clickOnAnalyticsSubTab(ConsoleAnalyticsPage.reportTabs.Extracts.getValue());
//            analyticsPage.mouseHoverAndExportExtractByName(ConsoleAnalyticsPage.extractNames.ScheduleChangePremiumExtract.getValue());
//
//        } catch (Exception e) {
//            SimpleUtils.fail(e.getMessage(), false);
//        }
//    }
}
