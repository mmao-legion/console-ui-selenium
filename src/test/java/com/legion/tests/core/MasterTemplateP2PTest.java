package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.OpsPortaPageFactories.ConfigurationPage;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.jsoup.Connection;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.legion.api.login.LoginAPI;

import javax.security.auth.login.Configuration;
import java.lang.reflect.Method;
import java.util.*;

public class MasterTemplateP2PTest extends TestBase {
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
    private SmartTemplatePage smartTemplatePage;
    private SmartCardPage smartCardPage;
    private ProfileNewUIPage profileNewUIPage;
    private LoginPage loginPage;
    private ConfigurationPage configurationPage;
    private LoginAPI loginAPI;
    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) {
        try {

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
            smartTemplatePage = pageFactory.createSmartTemplatePage();
            smartCardPage = pageFactory.createSmartCardPage();
            profileNewUIPage = pageFactory.createProfileNewUIPage();
            loginPage = pageFactory.createConsoleLoginPage();
            configurationPage = pageFactory.createOpsPortalConfigurationPage();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Eric")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "validate new added shifts can show in master template and schedule for P2P schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyNewAddedShiftsCanShowInMasterTemplateAndScheduleAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
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
            //Go to master template
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();
            //Check the shifts in master template can load
            SimpleUtils.assertOnFail("Shifts failed to load in master template! ",
                    scheduleShiftTablePage.getShiftsCount()>=0, false);

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            ArrayList<HashMap<String,String>> workRoles = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            String workRole = workRoles.get(0).get("optionName");
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(firstName);
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //Create new shift for one employee
            String shiftStartTime= "10:00am";
            String shiftEndTime = "5:00pm";
            List<String> childLocationNames = scheduleMainPage.getSpecificFilterNames("location");
            createShiftsWithSpecificValues(workRole, "", childLocationNames.get(0), shiftStartTime, shiftEndTime,
                    1, Arrays.asList(0,1), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
            createShiftsWithSpecificValues(workRole, "", childLocationNames.get(1), shiftStartTime, shiftEndTime,
                    1, Arrays.asList(2,3), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
            scheduleMainPage.saveSchedule();

            //Verify the new created shifts saved in master template successfully
            SimpleUtils.assertOnFail("The new created shift failed display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(firstName)==4, false );

            //Verify the new created shift can show in previous week
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            scheduleCommonPage.navigateToPreviousWeek();
            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();
            SimpleUtils.assertOnFail("The new created shift failed display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(firstName)==4, false );
            //Verify the new created shift can show in future week
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();
            SimpleUtils.assertOnFail("The new created shift failed display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(firstName)==4, false );


            //Verify the new created shift can show in schedule
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            String sheduleStartTime = "06:00AM";
            String scheduleEndTime = "11:00PM";
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( sheduleStartTime, scheduleEndTime);
            SimpleUtils.assertOnFail("The new created shift failed display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(firstName)==4, false );

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Eric")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "validate updated shifts can show in master template and schedule for P2P schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyUpdatedShiftsCanShowInMasterTemplateAndScheduleAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            //using API to get name of the TM
            List<String> usernameAndPwd = getUsernameAndPwd(AccessRoles.TeamMember.getValue());
            List<String> names = LoginAPI.getFirstNameAndLastNameFromLoginAPI(usernameAndPwd.get(0), usernameAndPwd.get(1));
            String tmFullName = names.get(0) + " "+ names.get(1);
            goToSchedulePageScheduleTab();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            //Go to master template
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            ArrayList<HashMap<String,String>> workRoles = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            String workRole = workRoles.get(0).get("optionName");
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(tmFullName);
            scheduleMainPage.saveSchedule();

            int employeeShiftCount = scheduleShiftTablePage.getShiftsNumberByName(tmFullName);
            if (employeeShiftCount==0){
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyLocation.getValue());
                //Create new shift for one employee
                String shiftStartTime= "10:00am";
                String shiftEndTime = "5:00pm";
                List<String> childLocationNames = scheduleMainPage.getSpecificFilterNames("location");
                createShiftsWithSpecificValues(workRole, "", childLocationNames.get(0), shiftStartTime, shiftEndTime,
                        1, Arrays.asList(0,1), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
                createShiftsWithSpecificValues(workRole, "", childLocationNames.get(1), shiftStartTime, shiftEndTime,
                        1, Arrays.asList(2,3), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
                scheduleMainPage.saveSchedule();
                employeeShiftCount = scheduleShiftTablePage.getShiftsNumberByName(tmFullName);
            }

            //Update shift in master template
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            List<Integer> shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(tmFullName);
            scheduleShiftTablePage.rightClickOnSelectedShiftInDayView(shiftIndexes.get(0));
            String action = "Edit";
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            //Change shift time
            String inputStartTime = "8:30 AM";
            String inputEndTime = "4:30 PM";
            editShiftPage.inputStartOrEndTime(inputStartTime, true);
            editShiftPage.inputStartOrEndTime(inputEndTime, false);

            //Change shift name and notes
            String shiftName = "Update shift name in master template";
            String shiftNotes = "Update shift notes in master template";
            editShiftPage.inputShiftName(shiftName);
            editShiftPage.inputShiftNotes(shiftNotes);

            editShiftPage.clickOnUpdateButton();
            editShiftPage.clickOnUpdateAnywayButton();

            scheduleMainPage.saveSchedule();
            shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(tmFullName);
            List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(shiftIndexes.get(0));
            String shiftStartTime = shiftInfo.get(6).split("-")[0].trim();
            String shiftEndTime = shiftInfo.get(6).split("-")[1].trim();
            SimpleUtils.assertOnFail("Start time or End time is not updated!", inputStartTime.equalsIgnoreCase(shiftStartTime) &&
                    inputEndTime.equalsIgnoreCase(shiftEndTime), false);

            SimpleUtils.assertOnFail("Shift Name is not updated!", shiftName.equalsIgnoreCase(shiftInfo.get(9)), false);
            SimpleUtils.assertOnFail("Failed to update the shift notes!", shiftNotes.equalsIgnoreCase(shiftInfo.get(10)), false);


            //Verify the new created shift can show in previous week
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            scheduleCommonPage.navigateToPreviousWeek();
            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();
            SimpleUtils.assertOnFail("The updated shift failed display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(tmFullName)==employeeShiftCount, false );
            String smartcardName = "Action Required";
            SimpleUtils.assertOnFail("The Action Required smart card should not show in master template! ",
                    !smartCardPage.isSpecificSmartCardLoaded(smartcardName), false);
            shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(tmFullName);
            shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(shiftIndexes.get(0));
            shiftStartTime = shiftInfo.get(6).split("-")[0].trim();
            shiftEndTime = shiftInfo.get(6).split("-")[1].trim();
            SimpleUtils.assertOnFail("Start time or End time is not updated!", inputStartTime.equalsIgnoreCase(shiftStartTime) &&
                    inputEndTime.equalsIgnoreCase(shiftEndTime), false);

            SimpleUtils.assertOnFail("Shift Name is not updated!", shiftName.equalsIgnoreCase(shiftInfo.get(9)), false);
            SimpleUtils.assertOnFail("Failed to update the shift notes!", shiftNotes.equalsIgnoreCase(shiftInfo.get(10)), false);
            //Verify the new created shift can show in future week
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();
            SimpleUtils.assertOnFail("The updated shift failed display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(tmFullName)==employeeShiftCount, false );
            SimpleUtils.assertOnFail("The Action Required smart card should not show in master template! ",
                    !smartCardPage.isSpecificSmartCardLoaded(smartcardName), false);
            shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(tmFullName);
            shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(shiftIndexes.get(0));
            shiftStartTime = shiftInfo.get(6).split("-")[0].trim();
            shiftEndTime = shiftInfo.get(6).split("-")[1].trim();
            SimpleUtils.assertOnFail("Start time or End time is not updated!", inputStartTime.equalsIgnoreCase(shiftStartTime) &&
                    inputEndTime.equalsIgnoreCase(shiftEndTime), false);

            SimpleUtils.assertOnFail("Shift Name is not updated!", shiftName.equalsIgnoreCase(shiftInfo.get(9)), false);
            SimpleUtils.assertOnFail("Failed to update the shift notes!", shiftNotes.equalsIgnoreCase(shiftInfo.get(10)), false);

            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            String sheduleStartTime = "06:00AM";
            String scheduleEndTime = "11:00PM";
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( sheduleStartTime, scheduleEndTime);
            SimpleUtils.assertOnFail("The updated shift failed display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(tmFullName)==employeeShiftCount, false );
            shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(tmFullName);
            shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(shiftIndexes.get(0));
            shiftStartTime = shiftInfo.get(6).split("-")[0].trim();
            shiftEndTime = shiftInfo.get(6).split("-")[1].trim();
            SimpleUtils.assertOnFail("Start time or End time is not updated!", inputStartTime.equalsIgnoreCase(shiftStartTime) &&
                    inputEndTime.equalsIgnoreCase(shiftEndTime), false);

            SimpleUtils.assertOnFail("Shift Name is not updated!", shiftName.equalsIgnoreCase(shiftInfo.get(9)), false);
            SimpleUtils.assertOnFail("Failed to update the shift notes!", shiftNotes.equalsIgnoreCase(shiftInfo.get(10)), false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
