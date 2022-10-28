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
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ScheduleAcknowledgementTest extends TestBase {
    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{
        try {
            this.createDriver((String) params[0], "69", "Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate TM can acknowledge the notification after publish the schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTMCanAcknowledgeTheNotificationAfterPublishTheScheduleAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            profileNewUIPage.clickOnUserProfileImage();
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage("My Profile");
            String tmFullName = profileNewUIPage.getUserProfileName().get("fullName");
            String firstName = tmFullName.split(" ")[0];
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            goToSchedulePageScheduleTab();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            String smartCardName = "SCHEDULE ACKNOWLEDGEMENT";
            //Verify the SCHEDULE ACKNOWLEDGEMENT smart card will not display after generate but not publish schedule
            SimpleUtils.assertOnFail("The SCHEDULE ACKNOWLEDGEMENT smart card should not display before publish schedule! ",
                    !smartCardPage.isSpecificSmartCardLoaded(smartCardName), false);
            String workRole = shiftOperatePage.getRandomWorkRole();
            //Verify the SCHEDULE ACKNOWLEDGEMENT smart card will not display after edit but not publish schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            createShiftsWithSpecificValues(workRole, "", "", "9:00am", "12:00pm",
                    1, Arrays.asList(1), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", firstName);
            scheduleMainPage.saveSchedule();
            //https://legiontech.atlassian.net/browse/SCH-8043
            SimpleUtils.assertOnFail("The SCHEDULE ACKNOWLEDGEMENT smart card should not display before publish schedule! ",
                    smartCardPage.isSpecificSmartCardLoaded(smartCardName), false);
            //Verify the SCHEDULE ACKNOWLEDGEMENT smart card will display after publish schedule
            createSchedulePage.publishActiveSchedule();
            SimpleUtils.assertOnFail("The SCHEDULE ACKNOWLEDGEMENT smart card should display before publish schedule! ",
                    smartCardPage.isSpecificSmartCardLoaded(smartCardName), false);
            //Get count before acknowledge
            int pendingEmployeeCountBeforeAcknowledge = smartCardPage.getCountFromSmartCardByName(smartCardName);

            //Login as employee
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();

            //check ACTION REQUIRED smart card display
            String acknowledgeNotificationMessage = "Please review and acknowledge receiving your schedule below.";
            SimpleUtils.assertOnFail("The SCHEDULE ACKNOWLEDGEMENT smart card should display before publish schedule! ",
                    smartCardPage.isSpecificSmartCardLoaded("ACTION REQUIRED")
                            && smartCardPage.isSmartCardAvailableByLabel(acknowledgeNotificationMessage), false);
            smartCardPage.clickOnAcknowledgeButtonOnAcknowledgeNotificationSmartCard();
            refreshPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("The SCHEDULE ACKNOWLEDGEMENT smart card should display before publish schedule! ",
                    !smartCardPage.isSpecificSmartCardLoaded("ACTION REQUIRED"), false);

            //Login as admin
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            goToSchedulePageScheduleTab();
            int pendingEmployeeCountAfterAcknowledge = smartCardPage.getCountFromSmartCardByName(smartCardName);
            SimpleUtils.assertOnFail("The pending employee count display incorrectly, the expected is: "
                            + (pendingEmployeeCountBeforeAcknowledge-1) + ". The actual is: "+pendingEmployeeCountAfterAcknowledge,
                    pendingEmployeeCountAfterAcknowledge == (pendingEmployeeCountBeforeAcknowledge-1) , false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate SM can acknowledge the notification after publish the schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySMCanAcknowledgeTheNotificationAfterPublishTheScheduleAsStoreManager(String browser, String username, String password, String location) throws Exception {
        try {
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            profileNewUIPage.clickOnUserProfileImage();
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage("My Profile");
            String tmFullName = profileNewUIPage.getUserProfileName().get("fullName");
            String firstName = tmFullName.split(" ")[0];
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            goToSchedulePageScheduleTab();
            scheduleCommonPage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            String smartCardName = "SCHEDULE ACKNOWLEDGEMENT";
            //Verify the SCHEDULE ACKNOWLEDGEMENT smart card will not display after generate but not publish schedule
            SimpleUtils.assertOnFail("The SCHEDULE ACKNOWLEDGEMENT smart card should not display before publish schedule! ",
                    !smartCardPage.isSpecificSmartCardLoaded(smartCardName), false);
            String workRole = shiftOperatePage.getRandomWorkRole();
            //Verify the SCHEDULE ACKNOWLEDGEMENT smart card will not display after edit but not publish schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            createShiftsWithSpecificValues(workRole, "", "", "9:00am", "12:00pm",
                    1, Arrays.asList(1), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", firstName);
            scheduleMainPage.saveSchedule();
            //https://legiontech.atlassian.net/browse/SCH-8043
            SimpleUtils.assertOnFail("The SCHEDULE ACKNOWLEDGEMENT smart card should not display before publish schedule! ",
                    smartCardPage.isSpecificSmartCardLoaded(smartCardName), false);
            //Verify the SCHEDULE ACKNOWLEDGEMENT smart card will display after publish schedule
            createSchedulePage.publishActiveSchedule();
            SimpleUtils.assertOnFail("The SCHEDULE ACKNOWLEDGEMENT smart card should display before publish schedule! ",
                    smartCardPage.isSpecificSmartCardLoaded(smartCardName), false);
            //Get count before acknowledge
            int pendingEmployeeCountBeforeAcknowledge = smartCardPage.getCountFromSmartCardByName(smartCardName);

            //Login as employee
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            dashboardPage.clickOnProfileIconOnDashboard();
            dashboardPage.clickOnSwitchToEmployeeView();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.navigateToNextWeek();

            //check ACTION REQUIRED smart card display
            String acknowledgeNotificationMessage = "Please review and acknowledge receiving your schedule below.";
            SimpleUtils.assertOnFail("The SCHEDULE ACKNOWLEDGEMENT smart card should display before publish schedule! ",
                    smartCardPage.isSpecificSmartCardLoaded("ACTION REQUIRED")
                            && smartCardPage.isSmartCardAvailableByLabel(acknowledgeNotificationMessage), false);
            smartCardPage.clickOnAcknowledgeButtonOnAcknowledgeNotificationSmartCard();
            refreshPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.navigateToNextWeek();
            SimpleUtils.assertOnFail("The SCHEDULE ACKNOWLEDGEMENT smart card should display before publish schedule! ",
                    !smartCardPage.isSpecificSmartCardLoaded("ACTION REQUIRED"), false);

            //Login as admin
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            goToSchedulePageScheduleTab();
            scheduleCommonPage.navigateToNextWeek();
            int pendingEmployeeCountAfterAcknowledge = smartCardPage.getCountFromSmartCardByName(smartCardName);
            SimpleUtils.assertOnFail("The pending employee count display incorrectly, the expected is: "
                            + (pendingEmployeeCountBeforeAcknowledge-1) + ". The actual is: "+pendingEmployeeCountAfterAcknowledge,
                    pendingEmployeeCountAfterAcknowledge == (pendingEmployeeCountBeforeAcknowledge-1) , false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
