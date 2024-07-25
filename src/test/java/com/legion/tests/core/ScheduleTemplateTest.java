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

import javax.security.auth.login.Configuration;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ScheduleTemplateTest extends TestBase {

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


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
//    @Enterprise(name = "Vailqacn_Enterprise")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the new create shift feature in edit schedule template")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheAssignShiftsWorkFlowInEditScheduleTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try {

            goToSchedulePageScheduleTab();
            scheduleCommonPage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            String workRole = scheduleMainPage.getStaffWorkRoles().get(scheduleMainPage.getStaffWorkRoles().size()-1);
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("");
            scheduleMainPage.saveSchedule();
            //Verify the auto offer workflow with one shift for one days
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            SimpleUtils.assertOnFail("New create shift page is not display! ",
                    newShiftPage.checkIfNewCreateShiftPageDisplay(), false);
            //Fill the required option

            newShiftPage.selectWorkRole(workRole);
            String shiftStartTime = "8:00am";
            String shiftEndTime = "11:00am";
            String totalHrs = "3 Hrs";
            newShiftPage.moveSliderAtCertainPoint(shiftEndTime, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint(shiftStartTime, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            int count = 1;
            newShiftPage.setShiftPerDayOnNewCreateShiftPage(count);
            newShiftPage.clearAllSelectedDays();
            int dayCount = 1;
            newShiftPage.selectSpecificWorkDay(dayCount);
            String shiftName = "ShiftNameForBulkCreateShiftUIAuto";
            String shiftNotes = "Shift Notes For Bulk Create Shift UI Auto";
            newShiftPage.setShiftNameOnNewCreateShiftPage(shiftName);
            newShiftPage.setShiftNotesOnNewCreateShiftPage(shiftNotes);
            newShiftPage.clickOnCreateOrNextBtn();
            //Select 3 TMs to offer and click Create button
            shiftOperatePage.switchSearchTMAndRecommendedTMsTab();
            MyThreadLocal.setAssignTMStatus(true);
            String selectedTM1 = newShiftPage.selectTeamMembers();
            newShiftPage.clickOnCreateOrNextBtn();
            List<WebElement> shiftsOfOneDay = scheduleShiftTablePage.getOneDayShiftByName(0, selectedTM1.split(" ")[0]);
            SimpleUtils.assertOnFail("The "+selectedTM1+ "shift is not exist on the first day! ",
                    shiftsOfOneDay.size()==1, false);
            scheduleMainPage.saveSchedule();
            Thread.sleep(5000);
            shiftsOfOneDay = scheduleShiftTablePage.getOneDayShiftByName(0, selectedTM1.split(" ")[0]);
            SimpleUtils.assertOnFail("The open shift is not exist on the first day! ",
                    shiftsOfOneDay.size()==1, false);
            createSchedulePage.publishActiveSchedule();
            shiftsOfOneDay = scheduleShiftTablePage.getOneDayShiftByName(0, selectedTM1.split(" ")[0]);
            SimpleUtils.assertOnFail("The open shift is not exist on the first day! ",
                    shiftsOfOneDay.size()==1, false);

            String shiftId = shiftsOfOneDay.get(0).getAttribute("id").toString();
            int index = scheduleShiftTablePage.getShiftIndexById(shiftId);
            List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            String shiftTime = shiftInfo.get(2);
            String workRoleOfNewShift = shiftInfo.get(4);
            String shiftHrs = shiftInfo.get(8);
            String shiftNameOfNewShift = shiftInfo.get(9);
            String shiftNotesOfNewShift = shiftInfo.get(10);
            SimpleUtils.assertOnFail("The new shift's shift time display incorrectly, the expected is:"+shiftStartTime+"-"+shiftEndTime
                            + " the actual is: "+ shiftTime,
                    shiftTime.equalsIgnoreCase(shiftStartTime+"-"+shiftEndTime), false);
            SimpleUtils.assertOnFail("The new shift's work role display incorrectly, the expected is:"+ workRole
                            + " the actual is: "+ workRoleOfNewShift,
                    workRoleOfNewShift.equalsIgnoreCase(workRole), false);
            SimpleUtils.assertOnFail("The new shift's hrs display incorrectly, the expected is:"+ totalHrs
                            + " the actual is: "+ shiftHrs,
                    totalHrs.equalsIgnoreCase(shiftHrs), false);
            SimpleUtils.assertOnFail("The new shift's name display incorrectly, the expected is:"+ shiftName
                            + " the actual is: "+ shiftNameOfNewShift,
                    shiftName.equals(shiftNameOfNewShift), false);
            SimpleUtils.assertOnFail("The new shift's notes display incorrectly, the expected is:"+ shiftNotes
                            + " the actual is: "+ shiftNotesOfNewShift,
                    shiftNotes.equalsIgnoreCase(shiftNotesOfNewShift), false);

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(selectedTM1.split(" ")[0]);
            scheduleMainPage.saveSchedule();


            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            SimpleUtils.assertOnFail("New create shift page is not display! ",
                    newShiftPage.checkIfNewCreateShiftPageDisplay(), false);
            //Fill the required option
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.moveSliderAtCertainPoint(shiftEndTime, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint(shiftStartTime, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            count =5;
            newShiftPage.setShiftPerDayOnNewCreateShiftPage(count);
            newShiftPage.clearAllSelectedDays();
            dayCount = 7;
            newShiftPage.selectSpecificWorkDay(dayCount);
            newShiftPage.setShiftNameOnNewCreateShiftPage(shiftName);
            newShiftPage.setShiftNotesOnNewCreateShiftPage(shiftNotes);
            newShiftPage.clickOnCreateOrNextBtn();
            shiftOperatePage.switchSearchTMAndRecommendedTMsTab();
            selectedTM1 = newShiftPage.selectTeamMembers();
            String selectedTM2 = newShiftPage.selectTeamMembers();
            String selectedTM3 = newShiftPage.selectTeamMembers();
            String selectedTM4 = newShiftPage.selectTeamMembers();
            String selectedTM5 = newShiftPage.selectTeamMembers();
            List<String> selectedTMs = new ArrayList<>();
            selectedTMs.add(selectedTM1);
            selectedTMs.add(selectedTM2);
            selectedTMs.add(selectedTM3);
            selectedTMs.add(selectedTM4);
            selectedTMs.add(selectedTM5);
            newShiftPage.clickOnCreateOrNextBtn();
            scheduleMainPage.saveSchedule();
            Thread.sleep(5000);
            for (int i =0; i<dayCount; i++) {
                for (int j=0;j<selectedTMs.size();j++) {
                    shiftsOfOneDay = scheduleShiftTablePage.getOneDayShiftByName(i, selectedTMs.get(j).split(" ")[0]);
                    SimpleUtils.assertOnFail("The "+selectedTMs.get(j)+" shift is not exist on the "+i+" day! ",
                            shiftsOfOneDay.size()==1, false);

                    shiftId = shiftsOfOneDay.get(0).getAttribute("id").toString();
                    index = scheduleShiftTablePage.getShiftIndexById(shiftId);
                    shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(index);
                    shiftTime = shiftInfo.get(2);
                    workRoleOfNewShift = shiftInfo.get(4);
                    shiftHrs = shiftInfo.get(8);
                    shiftNameOfNewShift = shiftInfo.get(9);
                    shiftNotesOfNewShift = shiftInfo.get(10);
                    SimpleUtils.assertOnFail("The new shift's shift time display incorrectly, the expected is:"+shiftStartTime+"-"+shiftEndTime
                                    + " the actual is: "+ shiftTime,
                            shiftTime.equalsIgnoreCase(shiftStartTime+"-"+shiftEndTime), false);
                    SimpleUtils.assertOnFail("The new shift's work role display incorrectly, the expected is:"+ workRole
                                    + " the actual is: "+ workRoleOfNewShift,
                            workRoleOfNewShift.equalsIgnoreCase(workRole), false);
                    SimpleUtils.assertOnFail("The new shift's hrs display incorrectly, the expected is:"+ totalHrs
                                    + " the actual is: "+ shiftHrs,
                            totalHrs.equalsIgnoreCase(shiftHrs), false);
                    SimpleUtils.assertOnFail("The new shift's name display incorrectly, the expected is:"+ shiftName
                                    + " the actual is: "+ shiftNameOfNewShift,
                            shiftName.equals(shiftNameOfNewShift), false);
                    SimpleUtils.assertOnFail("The new shift's notes display incorrectly, the expected is:"+ shiftNotes
                                    + " the actual is: "+ shiftNotesOfNewShift,
                            shiftNotes.equalsIgnoreCase(shiftNotesOfNewShift), false);
                }
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }



    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate employee can acknowledge the notification for schedule template location")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTMCanAcknowledgeTheNotificationAfterScheduleTemplateLocationAsTeamMember(String browser, String username, String password, String location) throws Exception {
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
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            String smartCardName = "SCHEDULE ACKNOWLEDGEMENT";
            //Verify the SCHEDULE ACKNOWLEDGEMENT smart card will not display after generate but not publish schedule
            SimpleUtils.assertOnFail("The SCHEDULE ACKNOWLEDGEMENT smart card should not display before publish schedule! ",
                    !smartCardPage.isSpecificSmartCardLoaded(smartCardName), false);
            String workRole = shiftOperatePage.getRandomWorkRole();
            //Verify the SCHEDULE ACKNOWLEDGEMENT smart card will not display after edit but not publish schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(firstName);
            scheduleMainPage.saveSchedule();
            Thread.sleep(3000);
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            createShiftsWithSpecificValues(workRole, "", "", "9:00am", "12:00pm",
                    1, Arrays.asList(1), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", firstName);
            scheduleMainPage.saveSchedule();
            //https://legiontech.atlassian.net/browse/SCH-8043
            SimpleUtils.assertOnFail("The SCHEDULE ACKNOWLEDGEMENT smart card should not display before publish schedule! ",
                    !smartCardPage.isSpecificSmartCardLoaded(smartCardName), false);
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
    @TestName(description = "validate new added shifts can show in master template and schedule for regular schedule")
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
            String workRole = scheduleMainPage.getStaffWorkRoles().get(scheduleMainPage.getStaffWorkRoles().size()-1);
            //Go to master template
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();
            //Check the shifts in master template can load
            SimpleUtils.assertOnFail("Shifts failed to load in master template! ",
                    scheduleShiftTablePage.getShiftsCount()>0, false);

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(firstName);
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //Create new shift for one employee
            String shiftStartTime= "10:00am";
            String shiftEndTime = "5:00pm";
            createShiftsWithSpecificValues(workRole, "", "", shiftStartTime, shiftEndTime,
                    1, Arrays.asList(0,1), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
            scheduleMainPage.saveSchedule();

            //Verify the new created shifts saved in master template successfully
            SimpleUtils.assertOnFail("The new created shift failed display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(firstName)==2, false );

            //Verify the new created shift can show in previous week
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            scheduleCommonPage.navigateToPreviousWeek();
            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();
            SimpleUtils.assertOnFail("The new created shift failed display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(firstName)==2, false );
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
                    scheduleShiftTablePage.getShiftsNumberByName(firstName)==2, false );


            //Verify the new created shift can show in schedule
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( "06:00AM", "11:00PM");
            SimpleUtils.assertOnFail("The new created shift failed display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(firstName)==2, false );

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "validate updated shifts can show in master template and schedule for regular schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyUpdatedShiftsCanShowInMasterTemplateAndScheduleAsTeamMember(String browser, String username, String password, String location) throws Exception {
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
            String workRole = scheduleMainPage.getStaffWorkRoles().get(scheduleMainPage.getStaffWorkRoles().size()-1);
            //Go to master template
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();

            int employeeShiftCount = scheduleShiftTablePage.getShiftsNumberByName(firstName);
            if (employeeShiftCount==0){
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                //Create new shift for one employee
                String shiftStartTime= "10:00am";
                String shiftEndTime = "5:00pm";
                createShiftsWithSpecificValues(workRole, "", "", shiftStartTime, shiftEndTime,
                        1, Arrays.asList(0), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
                scheduleMainPage.saveSchedule();
                employeeShiftCount = scheduleShiftTablePage.getShiftsNumberByName(firstName);
            }

            //Update shift in master template
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            List<Integer> shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(firstName);
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
            shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(firstName);
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
                    scheduleShiftTablePage.getShiftsNumberByName(firstName)==employeeShiftCount, false );

            shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(firstName);
            Thread.sleep(5000);
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
            SimpleUtils.assertOnFail("The new created shift failed display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(firstName)==employeeShiftCount, false );
            shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(firstName);
            Thread.sleep(5000);
            shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(shiftIndexes.get(0));
            shiftStartTime = shiftInfo.get(6).split("-")[0].trim();
            shiftEndTime = shiftInfo.get(6).split("-")[1].trim();
            SimpleUtils.assertOnFail("Start time or End time is not updated!", inputStartTime.equalsIgnoreCase(shiftStartTime) &&
                    inputEndTime.equalsIgnoreCase(shiftEndTime), false);

            SimpleUtils.assertOnFail("Shift Name is not updated!", shiftName.equalsIgnoreCase(shiftInfo.get(9)), false);
            SimpleUtils.assertOnFail("Failed to update the shift notes!", shiftNotes.equalsIgnoreCase(shiftInfo.get(10)), false);

            //Verify the new created shift can show in schedule
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( "06:00AM", "11:00PM");
            SimpleUtils.assertOnFail("The new created shift failed display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(firstName)==employeeShiftCount, false );
            Thread.sleep(5000);
            shiftIndexes = scheduleShiftTablePage.getAddedShiftIndexes(firstName);
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
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "validate deleted shifts will not show in master template and schedule for regular schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyDeletedShiftsWillNotShowInMasterTemplateAndScheduleAsTeamMember(String browser, String username, String password, String location) throws Exception {
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
            String workRole = scheduleMainPage.getStaffWorkRoles().get(scheduleMainPage.getStaffWorkRoles().size()-1);
            //Go to master template
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();

            int employeeShiftCount = scheduleShiftTablePage.getShiftsNumberByName(firstName);
            if (employeeShiftCount==0){
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                //Create new shift for one employee
                String shiftStartTime= "10:00am";
                String shiftEndTime = "5:00pm";
                createShiftsWithSpecificValues(workRole, "", "", shiftStartTime, shiftEndTime,
                        1, Arrays.asList(0), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
                scheduleMainPage.saveSchedule();
            }

            //Delete shift in master template
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(firstName);
            scheduleMainPage.saveSchedule();
            //Verify the deleted shifts not show in master template anymore
            SimpleUtils.assertOnFail("The new created shift failed display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(firstName)==0, false );

            //Verify the deleted shifts not show in master template in previous week
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            scheduleCommonPage.navigateToPreviousWeek();
            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();
            SimpleUtils.assertOnFail("The new created shift failed display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(firstName)==0, false );
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
                    scheduleShiftTablePage.getShiftsNumberByName(firstName)==0, false );


            //Verify the new created shift can show in schedule
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( "06:00AM", "11:00PM");
            SimpleUtils.assertOnFail("The new created shift failed display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(firstName)==0, false );

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
