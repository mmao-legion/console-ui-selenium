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
import com.legion.utils.SimpleUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.legion.api.login.LoginAPI;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.*;

public class MasterTemplatePCLGTest extends TestBase {
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
    private TeamPage teamPage;
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
            teamPage = pageFactory.createConsoleTeamPage();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Ashutosh")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "validate new added shifts can show in master template and schedule for PCLG schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyNewAddedShiftsForPCLGCanShowInMasterTemplateAndScheduleAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
            String workRole = scheduleMainPage.getStaffWorkRoles().get(scheduleMainPage.getStaffWorkRoles().size()-1);
            //Go to master template
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();
            //Check the shifts in master template can load
            SimpleUtils.assertOnFail("Shifts failed to load in master template! ",
                    scheduleShiftTablePage.getShiftsCount()>=0, false);

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            //ArrayList<HashMap<String,String>> workRoles = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            //String workRole = workRoles.get(0).get("optionName");

            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(tmFullName);
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
                    scheduleShiftTablePage.getShiftsNumberByName(tmFullName)==4, false );

            //Verify the new created shift can show in previous week
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            scheduleCommonPage.navigateToPreviousWeek();
            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();
            SimpleUtils.assertOnFail("The new created shift failed to display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(tmFullName)==4, false );
            //Verify the new created shift can show in future week
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();
            SimpleUtils.assertOnFail("The new created shift failed to display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(tmFullName)==4, false );


            //Verify the new created shift can show in schedule
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            String sheduleStartTime = "06:00AM";
            String scheduleEndTime = "11:00PM";
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( sheduleStartTime, scheduleEndTime);
            SimpleUtils.assertOnFail("The new created shift failed to display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(tmFullName)==4, false );

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Ashutosh")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "validate updated shifts can show in master template and schedule for PCLG schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyUpdatedShiftsForPCLGCanShowInMasterTemplateAndScheduleAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
            String workRole = scheduleMainPage.getStaffWorkRoles().get(scheduleMainPage.getStaffWorkRoles().size()-1);
            //Go to master template
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();

            int employeeShiftCount = scheduleShiftTablePage.getShiftsNumberByName(tmFullName);
            if (employeeShiftCount==0){
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

            //Verify the new created shift can show in schedule
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

    @Automated(automated ="Automated")
    @Owner(owner = "Ashutosh")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "validate deleted shifts will not show in master template and schedule for PCLG schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDeletedShiftsForPCLGWillNotShowInMasterTemplateAndScheduleAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
            String workRole = scheduleMainPage.getStaffWorkRoles().get(scheduleMainPage.getStaffWorkRoles().size()-1);
            //Go to master template
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();

            int employeeShiftCount = scheduleShiftTablePage.getShiftsNumberByName(tmFullName);
            if (employeeShiftCount==0) {
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                //Create new shift for one employee
                String shiftStartTime = "10:00am";
                String shiftEndTime = "5:00pm";
                List<String> childLocationNames = scheduleMainPage.getSpecificFilterNames("location");
                createShiftsWithSpecificValues(workRole, "", childLocationNames.get(0), shiftStartTime, shiftEndTime,
                        1, Arrays.asList(0, 1), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
                createShiftsWithSpecificValues(workRole, "", childLocationNames.get(1), shiftStartTime, shiftEndTime,
                        1, Arrays.asList(2, 3), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
                scheduleMainPage.saveSchedule();
                employeeShiftCount = scheduleShiftTablePage.getShiftsNumberByName(tmFullName);
            }

            //Delete shift in master template
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(tmFullName);
            scheduleMainPage.saveSchedule();
            //Verify the deleted shifts not show in master template anymore
            SimpleUtils.assertOnFail("The deleted shift should not display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(tmFullName)==0, false );

            //Verify the deleted shifts not show in master template in previous week
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            scheduleCommonPage.navigateToPreviousWeek();
            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();
            SimpleUtils.assertOnFail("The deleted shift should not display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(tmFullName)==0, false );
            //Verify the new created shift can show in future week
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();
            SimpleUtils.assertOnFail("The deleted shift should not display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(tmFullName)==0, false );


            //Verify the new created shift can show in schedule
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            String sheduleStartTime = "06:00AM";
            String scheduleEndTime = "11:00PM";
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange(sheduleStartTime, scheduleEndTime);
            SimpleUtils.assertOnFail("The deleted shift should not display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(tmFullName)==0, false );

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Ashutosh")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify master template shift adheres to the meal and rest break template based on the association for PCLG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyMasterTemplateShiftForPCLGAdheresToTheMealAndRestBreakTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
            String workRole = scheduleMainPage.getStaffWorkRoles().get(scheduleMainPage.getStaffWorkRoles().size()-1);
            //Go to master template
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();
            //Check the shifts in master template can load
            SimpleUtils.assertOnFail("Shifts failed to load in master template! ",
                    scheduleShiftTablePage.getShiftsCount()>0, false);

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(tmFullName);
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //Create new shift for one employee
            String shiftStartTime= "10:00am";
            String shiftEndTime = "5:00pm";
            String mealBreakStartTime = "1:15 pm";
            String mealBreakEndTime = "1:45 pm";
            String restBreakStartTime = "12:15 pm";
            String restBreakEndTime = "12:30 pm";
            List<String> childLocationNames = scheduleMainPage.getSpecificFilterNames("location");
            createShiftsWithSpecificValues(workRole, "", childLocationNames.get(0), shiftStartTime, shiftEndTime,
                    1, Arrays.asList(0, 1), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
            createShiftsWithSpecificValues(workRole, "", childLocationNames.get(1), shiftStartTime, shiftEndTime,
                    1, Arrays.asList(2, 3), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
            scheduleMainPage.saveSchedule();
            //employeeShiftCount = scheduleShiftTablePage.getShiftsNumberByName(firstName);
            String smartcardName = "Action Required";
            SimpleUtils.assertOnFail("The Action Required smart card should not show in master template! ",
                    !smartCardPage.isSpecificSmartCardLoaded(smartcardName), false);
            List<Integer> shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(tmFullName);
            List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(shiftIndexes.get(0));
            String shiftMealTime = shiftInfo.get(11);
            String shiftRestTime = shiftInfo.get(12);
            SimpleUtils.assertOnFail("The meal break time display incorrectly, the actual meal break time is:"+shiftMealTime,
                    shiftMealTime.contains(mealBreakStartTime)
                            && shiftMealTime.contains(mealBreakEndTime), false);
            SimpleUtils.assertOnFail("The rest break time display incorrectly, the actual rest break time is:"+shiftRestTime,
                    shiftRestTime.contains(restBreakStartTime)
                            && shiftRestTime.contains(restBreakEndTime), false);

            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(tmFullName);
            shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(shiftIndexes.get(0));
            shiftMealTime = shiftInfo.get(11);
            shiftRestTime = shiftInfo.get(12);
            SimpleUtils.assertOnFail("The meal break time display incorrectly, the actual meal break time is:"+shiftMealTime,
                    shiftMealTime.contains(mealBreakStartTime)
                            && shiftMealTime.contains(mealBreakEndTime), false);
            SimpleUtils.assertOnFail("The rest break time display incorrectly, the actual rest break time is:"+shiftRestTime,
                    shiftRestTime.contains(restBreakStartTime)
                            && shiftRestTime.contains(restBreakEndTime), false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Ashutosh")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify changed meal and rest break timing consistent in master template and schedule for PCLG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyChangedMealAndRestBreakConsistentInMasterTemplateForPCLGAndScheduleAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
            String workRole = scheduleMainPage.getStaffWorkRoles().get(scheduleMainPage.getStaffWorkRoles().size()-1);
            //Go to master template
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();
            //Check the shifts in master template can load
            SimpleUtils.assertOnFail("Shifts failed to load in master template! ",
                    scheduleShiftTablePage.getShiftsCount()>0||scheduleShiftTablePage.isScheduleTableDisplay(), false);

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(tmFullName);
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //Create new shift for one employee
            String shiftStartTime= "8:00am";
            String shiftEndTime = "4:00pm";
            String mealBreakStartTime = "11:45 am";
            String mealBreakEndTime = "12:15 pm";
            String restBreakStartTime = "10:15 am";
            String restBreakEndTime = "10:30 am";
            List<String> childLocationNames = scheduleMainPage.getSpecificFilterNames("location");
            createShiftsWithSpecificValues(workRole, "", childLocationNames.get(0), shiftStartTime, shiftEndTime,
                    1, Arrays.asList(0, 1), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
            createShiftsWithSpecificValues(workRole, "", childLocationNames.get(1), shiftStartTime, shiftEndTime,
                    1, Arrays.asList(2, 3), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
            scheduleMainPage.saveSchedule();
            List<Integer> shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(tmFullName);
            String smartcardName = "Action Required";
            SimpleUtils.assertOnFail("The Action Required smart card should not show in master template! ",
                    !smartCardPage.isSpecificSmartCardLoaded(smartcardName), false);

            List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(shiftIndexes.get(0));
            String shiftMealTime = shiftInfo.get(11);
            String shiftRestTime = shiftInfo.get(12);
            SimpleUtils.assertOnFail("The meal break time display incorrectly, the actual meal break time is:"+shiftMealTime,
                    shiftMealTime.contains(mealBreakStartTime)
                            && shiftMealTime.contains(mealBreakEndTime), false);
            SimpleUtils.assertOnFail("The rest break time display incorrectly, the actual rest break time is:"+shiftRestTime,
                    shiftRestTime.contains(restBreakStartTime)
                            && shiftRestTime.contains(restBreakEndTime), false);

            //Update breaks in master template
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.rightClickOnSelectedShiftInDayView(shiftIndexes.get(0));
            String action = "Edit";
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            String updatedMealBreakStartTime = "8:15 am";
            String updatedMealBreakEndTime = "8:45 am";
            String updateRestBreakStartTime = "3:15 pm";
            String updateRestBreakEndTime = "3:30 pm";
            editShiftPage.inputMealBreakTimes(updatedMealBreakStartTime, updatedMealBreakEndTime, 0);
            editShiftPage.inputRestBreakTimes(updateRestBreakStartTime, updateRestBreakEndTime, 0);
            editShiftPage.clickOnUpdateButton();
            editShiftPage.clickOnUpdateAnywayButton();

            scheduleMainPage.saveSchedule();

            shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(shiftIndexes.get(0));
            shiftMealTime = shiftInfo.get(11);
            shiftRestTime = shiftInfo.get(12);
            SimpleUtils.assertOnFail("The meal break time display incorrectly, the actual meal break time is:"+shiftMealTime,
                    shiftMealTime.contains(updatedMealBreakStartTime)
                            && shiftMealTime.contains(updatedMealBreakEndTime), false);
            SimpleUtils.assertOnFail("The rest break time display incorrectly, the actual rest break time is:"+shiftRestTime,
                    shiftRestTime.contains(updateRestBreakStartTime)
                            && shiftRestTime.contains(updateRestBreakEndTime), false);

            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(tmFullName);
            shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(shiftIndexes.get(0));
            shiftMealTime = shiftInfo.get(11);
            shiftRestTime = shiftInfo.get(12);
            SimpleUtils.assertOnFail("The meal break time display incorrectly, the actual meal break time is:"+shiftMealTime,
                    shiftMealTime.contains(updatedMealBreakStartTime)
                            && shiftMealTime.contains(updatedMealBreakEndTime), false);
            SimpleUtils.assertOnFail("The rest break time display incorrectly, the actual rest break time is:"+shiftRestTime,
                    shiftRestTime.contains(updateRestBreakStartTime)
                            && shiftRestTime.contains(updateRestBreakEndTime), false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Ashutosh")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the Master Template is loading when employee has multiple shifts in same day triggering Daily OT in the Template for PCLG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyMasterTemplateForPCLGIsLoadingWhenShiftsTriggerDailyOTAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
            String workRole = scheduleMainPage.getStaffWorkRoles().get(scheduleMainPage.getStaffWorkRoles().size()-1);
            //Go to master template
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(tmFullName);
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //Create two shifts for one employee in same day and make sure they will trigger Daily OT
            String shiftStartTime1= "8:00am";
            String shiftEndTime1 = "4:00pm";
            List<String> childLocationNames = scheduleMainPage.getSpecificFilterNames("location");
            createShiftsWithSpecificValues(workRole, "", childLocationNames.get(0), shiftStartTime1, shiftEndTime1,
                    1, Arrays.asList(0, 1), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
            String shiftStartTime2= "6:00pm";
            String shiftEndTime2 = "9:00pm";
            createShiftsWithSpecificValues(workRole, "", childLocationNames.get(1), shiftStartTime2, shiftEndTime2,
                    1, Arrays.asList(0, 1), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
            scheduleMainPage.saveSchedule();

            //Check the daily OT display correctly in master template
            List<Integer> shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(tmFullName);
            List<String> violations = scheduleShiftTablePage.
                    getComplianceMessageFromInfoIconPopup(scheduleShiftTablePage.getTheShiftByIndex(shiftIndexes.get(1)));
            String otViolation = "2.5 hrs daily overtime";
            SimpleUtils.assertOnFail("The OT violation display incorrect, the actual is:"+violations.toString(),
                    violations.contains(otViolation), false);

            //Check the daily OT display correctly in schedule
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            SimpleUtils.assertOnFail("The OT shifts of"+tmFullName+" not display in schedule! ",
                    scheduleShiftTablePage.getShiftsNumberByName(tmFullName)==2, false );
            violations = scheduleShiftTablePage.
                    getComplianceMessageFromInfoIconPopup(scheduleShiftTablePage.getTheShiftByIndex(shiftIndexes.get(1)));
            SimpleUtils.assertOnFail("The OT violation display incorrect, the actual is:"+violations.toString(),
                    violations.contains(otViolation), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Ashutosh")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the Master Template is loading when employee has multiple shifts in same day triggering Weekly OT in the Template for PCLG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyMasterTemplateForPCLGIsLoadingWhenShiftsTriggerWeeklyOTAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
            String workRole = scheduleMainPage.getStaffWorkRoles().get(scheduleMainPage.getStaffWorkRoles().size()-1);
            //Go to master template
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(tmFullName);
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //Create multiple shifts for one employee on multiple days, make sure they will trigger weekly OT
            String shiftStartTime1= "8:00am";
            String shiftEndTime1 = "4:00pm";
            List<String> childLocationNames = scheduleMainPage.getSpecificFilterNames("location");
            createShiftsWithSpecificValues(workRole, "", childLocationNames.get(0), shiftStartTime1, shiftEndTime1,
                    1, Arrays.asList(0, 1,2), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
            createShiftsWithSpecificValues(workRole, "", childLocationNames.get(1), shiftStartTime1, shiftEndTime1,
                    1, Arrays.asList(3,4,5), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
            scheduleMainPage.saveSchedule();
            //Check the weekly OT display correctly in master template
            List<Integer> shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(tmFullName);
            List<String> violations = scheduleShiftTablePage.
                    getComplianceMessageFromInfoIconPopup(scheduleShiftTablePage.getTheShiftByIndex(shiftIndexes.get(shiftIndexes.size()-1)));
            String otViolation = "5 hrs weekly overtime";
            SimpleUtils.assertOnFail("The OT violation display incorrect, the actual is:"+violations.toString(),
                    violations.contains(otViolation), false);

            ////Check the weekly OT display correctly in schedule
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            SimpleUtils.assertOnFail("The OT shifts of"+tmFullName+" not display in schedule! ",
                    scheduleShiftTablePage.getShiftsNumberByName(tmFullName)==6, false );
            violations = scheduleShiftTablePage.
                    getComplianceMessageFromInfoIconPopup(scheduleShiftTablePage.getTheShiftByIndex(shiftIndexes.get(shiftIndexes.size()-1)));
            SimpleUtils.assertOnFail("The OT violation display incorrect, the actual is:"+violations.toString(),
                    violations.contains(otViolation), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Ashutosh")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify meal break timing persists on bulk edit shift for PCLG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyMealBreakTimingForPCLGPersistsOnBulkEditShiftAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
            String workRole = scheduleMainPage.getStaffWorkRoles().get(scheduleMainPage.getStaffWorkRoles().size()-1);
            //Go to master template
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(tmFullName);
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //Create new shift for one employee
            String shiftStartTime1= "8:00am";
            String shiftEndTime1 = "4:00pm";
            List<String> childLocationNames = scheduleMainPage.getSpecificFilterNames("location");
            createShiftsWithSpecificValues(workRole, "", childLocationNames.get(0), shiftStartTime1, shiftEndTime1,
                    1, Arrays.asList(0, 1), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
            createShiftsWithSpecificValues(workRole, "", childLocationNames.get(1), shiftStartTime1, shiftEndTime1,
                    1, Arrays.asList(2, 3), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkEditTMShiftsInWeekView(tmFullName);

            String shiftStartTime2= "3:00pm";
            String shiftEndTime2 = "11:00pm";
            String shiftNotes = "Shift notes in master template";

            editShiftPage.inputStartOrEndTime(shiftStartTime2, true);
            editShiftPage.inputStartOrEndTime(shiftEndTime2, false);
            editShiftPage.inputShiftNotes(shiftNotes);
            editShiftPage.clickOnUpdateButton();
            editShiftPage.clickOnUpdateAnywayButton();

            scheduleMainPage.saveSchedule();
            List<Integer> shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(tmFullName);
            String smartcardName = "Action Required";
            SimpleUtils.assertOnFail("The Action Required smart card should not show in master template! ",
                    !smartCardPage.isSpecificSmartCardLoaded(smartcardName), false);

            List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(shiftIndexes.get(0));
            String shiftMealTime = shiftInfo.get(11);
            String shiftRestTime = shiftInfo.get(12);
            SimpleUtils.assertOnFail("The break time display incorrectly, the actual meal break time is:"+shiftMealTime+"" +
                            "The rest break time is"+shiftRestTime,
                    !shiftMealTime.equals("") && !shiftRestTime.equals(""), false);

            String shiftTime = shiftInfo.get(2);
            String shiftNotesOfNewShift = shiftInfo.get(10);
            SimpleUtils.assertOnFail("The new shift's shift time display incorrectly, the expected is:"+shiftStartTime2+"-"+shiftEndTime2
                            + " the actual is: "+ shiftTime,
                    shiftTime.equalsIgnoreCase(shiftStartTime2+"-"+shiftEndTime2), false);
            SimpleUtils.assertOnFail("The new shift's notes display incorrectly, the expected is:"+ shiftNotes
                            + " the actual is: "+ shiftNotesOfNewShift,
                    shiftNotes.equalsIgnoreCase(shiftNotesOfNewShift), false);

            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkEditTMShiftsInWeekView(tmFullName);

            shiftNotes = "Updated--Shift notes in master template";

            editShiftPage.inputStartOrEndTime(shiftStartTime1, true);
            editShiftPage.inputStartOrEndTime(shiftEndTime1, false);
            editShiftPage.inputShiftNotes(shiftNotes);
            editShiftPage.clickOnUpdateButton();
            editShiftPage.clickOnUpdateAnywayButton();
            scheduleMainPage.saveSchedule();
            shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(tmFullName);
            shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(shiftIndexes.get(2));
            shiftMealTime = shiftInfo.get(11);
            shiftRestTime = shiftInfo.get(12);
            SimpleUtils.assertOnFail("The break time display incorrectly, the actual meal break time is:"+shiftMealTime+"" +
                            "The rest break time is"+shiftRestTime,
                    !shiftMealTime.equals("") && !shiftRestTime.equals(""), false);

            shiftTime = shiftInfo.get(2);
            shiftNotesOfNewShift = shiftInfo.get(10);
            SimpleUtils.assertOnFail("The new shift's shift time display incorrectly, the expected is:"+shiftStartTime2+"-"+shiftEndTime2
                            + " the actual is: "+ shiftTime,
                    shiftTime.equalsIgnoreCase(shiftStartTime1+"-"+shiftEndTime1), false);
            SimpleUtils.assertOnFail("The new shift's notes display incorrectly, the expected is:"+ shiftNotes
                            + " the actual is: "+ shiftNotesOfNewShift,
                    shiftNotes.equalsIgnoreCase(shiftNotesOfNewShift), false);


        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Ashutosh")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the preassigned shift of TM gets converted to Open when TM is on PTO for PCLG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyPreassignedShiftOfTMForPCLGGetsConvertedToOpenWhenTMIsOnPTOAsTeamMember2(String browser, String username, String password, String location) throws Exception {
        try {
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
            String workRole = scheduleMainPage.getStaffWorkRoles().get(scheduleMainPage.getStaffWorkRoles().size()-1);

            String activeWeek = scheduleCommonPage.getActiveWeekText();
            List<String> year = scheduleCommonPage.getYearsFromCalendarMonthYearText();
            String[] items = activeWeek.split(" ");
            String ptomDate = year.get(0)+ " " + items[3] + " " + items[4];
            String ptoDateWithOutYear = items[3] + " " + items[4];
            teamPage.goToTeam();
            teamPage.searchAndSelectTeamMember(tmFullName);
            String timeOffLabel = "Time Off";
            profileNewUIPage.selectProfilePageSubSectionByLabel(timeOffLabel);
            String timeOffExplanationText = "Sample Explanation Text For master template testing";
            String timeOffStatus = profileNewUIPage.getTimeOffRequestStatus(ActivityTest.timeOffReasonType.JuryDuty.getValue(), timeOffExplanationText, ptoDateWithOutYear, ptoDateWithOutYear);
            if (!timeOffStatus.equalsIgnoreCase("approved")) {
                profileNewUIPage.rejectAllTimeOff();
                //Go to team page and create time off for tm
                profileNewUIPage.createTimeOffOnSpecificDays(ActivityTest.timeOffReasonType.JuryDuty.getValue(), timeOffExplanationText, ptomDate, 0);
                teamPage.approvePendingTimeOffRequest();
            }

            goToSchedulePageScheduleTab();
            scheduleCommonPage.navigateToNextWeek();
            //Go to master template
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();
            String openShift = "Open";
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(tmFullName);
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(openShift);
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //Create new shift for one employee
            String shiftStartTime1= "8:00am";
            String shiftEndTime1 = "12:00pm";
            List<String> childLocationNames = scheduleMainPage.getSpecificFilterNames("location");
            createShiftsWithSpecificValues(workRole, "", childLocationNames.get(0), shiftStartTime1, shiftEndTime1,
                    1, Arrays.asList(0, 1,2,3,4,5,6), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
            scheduleMainPage.saveSchedule();
            int employeeShiftCount = scheduleShiftTablePage.getShiftsNumberByName(tmFullName);
            int openShiftCount = scheduleShiftTablePage.getShiftsNumberByName(openShift);
            SimpleUtils.assertOnFail("Employee shift count should be 7, the actual count is"+employeeShiftCount,
                    employeeShiftCount==7, false);
            SimpleUtils.assertOnFail("Open shift count should be 0, the actual count is"+openShiftCount,
                    openShiftCount==0, false);

            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            employeeShiftCount = scheduleShiftTablePage.getShiftsNumberByName(tmFullName);
            openShiftCount = scheduleShiftTablePage.getShiftsNumberByName(openShift);
            SimpleUtils.assertOnFail("Employee shift count should be 7, the actual count is"+employeeShiftCount,
                    employeeShiftCount==6, false);
            SimpleUtils.assertOnFail("Open shift count should be 0, the actual count is"+openShiftCount,
                    openShiftCount==1, false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
