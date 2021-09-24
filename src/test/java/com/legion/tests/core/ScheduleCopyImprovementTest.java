package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.core.ConsoleScheduleCommonPage;
import com.legion.pages.core.OpsPortalLocationsPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.legion.utils.MyThreadLocal.getDriver;
import static com.legion.utils.MyThreadLocal.teamMemberName;

public class ScheduleCopyImprovementTest extends TestBase {

    private static HashMap<String, Object[][]> controlTeamMembers = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson("VailqacnTeamMembers.json");
    private static HashMap<String, Object[][]> opTeamMembers = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson("CinemarkWkdyTeamMembers.json");
    private static String controlEnterprice = "Vailqacn_Enterprise";
    private static String opEnterprice = "CinemarkWkdy_Enterprise";

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) {
        try {
            this.createDriver((String) params[0], "69", "Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the unassigned shifts convert to open shifts setting in Control")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheUnassignedShiftsConvertToOpenShiftsSettingInControlAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsPage.gotoControlsPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
            SimpleUtils.assertOnFail("Scheduling collaboration page not loaded successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
            controlsNewUIPage.clickOnScheduleCollaborationOpenShiftAdvanceBtn();

            controlsNewUIPage.verifyConvertUnassignedShiftsToOpenSetting();

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the unassigned shifts convert to open shifts setting in OP")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheUnassignedShiftsConvertToOpenShiftsSettingInOPAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            OpsPortalLocationsPage opsPortalLocationsPage = (OpsPortalLocationsPage) pageFactory.createOpsPortalLocationsPage();
            opsPortalLocationsPage.clickModelSwitchIconInDashboardPage("Operation Portal");
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.goToTemplateDetailsPage("Schedule Collaboration");
            configurationPage.verifyConvertUnassignedShiftsToOpenSetting();

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the unassigned shifts convert to open shifts when generating schedule setting set as Yes, all unassigned shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateShiftsWithConvertToOpenShiftsWhenGeneratingScheduleSettingAsAllUnassignedShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String option = "Yes, all unassigned shifts";
            changeConvertToOpenShiftsSettings(option, location);
            if(getDriver().getCurrentUrl().contains(propertyMap.get(opEnterprice))){
                Thread.sleep(300000);
            }
            validateShiftsWithConvertToOpenShiftsWhenCopyingScheduleSetting(false, option, false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the unassigned shifts convert to open shifts when copying schedule setting set as Yes, all unassigned shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateShiftsWithConvertToOpenShiftsWhenCopyingScheduleSettingAsAllUnassignedShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
//            if (getDriver().getCurrentUrl().contains(propertyMap.get(controlEnterprice))){
//                disableCopyRestriction();
//            }
            String option = "Yes, all unassigned shifts";
            changeConvertToOpenShiftsSettings(option, location);
            validateShiftsWithConvertToOpenShiftsWhenCopyingScheduleSetting(true, option, false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the unassigned shifts convert to open shifts when generating schedule setting set as No, keep as unassigned")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateShiftsWithConvertToOpenShiftsWhenGeneratingScheduleSettingAsKeepAsUnassignedAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            String option = "No, keep as unassigned";
            changeConvertToOpenShiftsSettings(option, location);
            if(getDriver().getCurrentUrl().contains(propertyMap.get(opEnterprice))){
                Thread.sleep(180000);
            }
            validateShiftsWithConvertToOpenShiftsWhenCopyingScheduleSetting(false, option, false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }



    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the unassigned shifts convert to open shifts when copying schedule setting set as No, keep as unassigned")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateShiftsWithConvertToOpenShiftsWhenCopyingScheduleSettingAsKeepAsUnassignedAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            disableCopyRestriction(location);
            String option = "No, keep as unassigned";
            changeConvertToOpenShiftsSettings(option, location);
            validateShiftsWithConvertToOpenShiftsWhenCopyingScheduleSetting(true, option, false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the unassigned opening or closing shifts will not convert to open shifts when generating schedule setting set as Yes, except opening/closing shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateShiftsWithConvertToOpenShiftsWhenGeneratingScheduleSettingAsExceptOpeningClosingShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String option = "Yes, except opening/closing shifts";
            changeConvertToOpenShiftsSettings(option, location);
            if(getDriver().getCurrentUrl().contains(propertyMap.get(opEnterprice))){
                Thread.sleep(300000);
            }
            validateShiftsWithConvertToOpenShiftsWhenCopyingScheduleSetting(false, option, false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the unassigned opening or closing shifts will not convert to open shifts when copying schedule setting set as Yes, except opening/closing shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateShiftsWithConvertToOpenShiftsWhenCopyingScheduleSettingAsExceptOpeningClosingShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
//            if (getDriver().getCurrentUrl().contains(propertyMap.get(controlEnterprice))){
//                disableCopyRestriction();
//            }
            String option = "Yes, except opening/closing shifts";
            changeConvertToOpenShiftsSettings(option, location);
            validateShiftsWithConvertToOpenShiftsWhenCopyingScheduleSetting(true, option, false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }



    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the schedule with both Uassigned and OOH shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheScheduleWithBothUnassignedAndOOOHShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
//            if (getDriver().getCurrentUrl().contains(propertyMap.get(controlEnterprice))){
//                disableCopyRestriction();
//            }
            String option = "No, keep as unassigned";
            changeConvertToOpenShiftsSettings(option, location);
            validateShiftsWithConvertToOpenShiftsWhenCopyingScheduleSetting(true, option, true);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    private void validateShiftsWithConvertToOpenShiftsWhenCopyingScheduleSetting(boolean isCopySchedule, String option, boolean ifVerifyOOOHShifts) throws Exception {


        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String firstNameOfTM = "";
        String lastNameOfTM = "";
        String workRoleOfTM = "";
        if (isCopySchedule){
            HashMap<String, Object[][]> teamMembers = null;
            if (getDriver().getCurrentUrl().contains(propertyMap.get(controlEnterprice))){
                teamMembers = controlTeamMembers;
            } else {
                teamMembers = opTeamMembers;
            }

            firstNameOfTM = teamMembers.get("TeamMember1")[0][0].toString();
            lastNameOfTM = teamMembers.get("TeamMember1")[0][1].toString();
            workRoleOfTM = teamMembers.get("TeamMember1")[0][2].toString();

            teamPage.goToTeam();

            if (teamPage.checkIfTMExists(firstNameOfTM)) {
                teamPage.searchAndSelectTeamMemberByName(firstNameOfTM);
                if(teamPage.isManualOnBoardButtonLoaded()) {
                    teamPage.manualOnBoardTeamMember();
                }
                if (teamPage.isActivateButtonLoaded()) {
                    teamPage.clickOnActivateButton();
                    teamPage.isActivateWindowLoaded();
                    teamPage.selectADateOnCalendarAndActivate();
                }
                if (teamPage.isCancelTerminateButtonLoaded()) {
                    teamPage.cancelTMTerminate();
                }
                if (teamPage.isCancelDeactivateButtonLoaded()) {
                    teamPage.cancelTMDeactivate();
                }

                profileNewUIPage.selectProfilePageSubSectionByLabel("Time Off");
                profileNewUIPage.rejectAllTimeOff();
                profileNewUIPage.cancelAllTimeOff();

            } else
                SimpleUtils.fail("The team member '"+ firstNameOfTM +"' is not exists! ", false);
        }



        //Go to schedule page and create new schedule

        String userName = "";
        schedulePage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
        schedulePage.navigateToNextWeek();
        schedulePage.navigateToNextWeek();
        schedulePage.navigateToNextWeek();
        boolean isWeekGenerated = schedulePage.isWeekGenerated();
        if (isWeekGenerated) {
            schedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        Thread.sleep(5000);
        if(!isCopySchedule && option.equalsIgnoreCase("Yes, except opening/closing shifts")) {
            schedulePage.clickCreateScheduleBtn();
            schedulePage.editOperatingHoursWithGivingPrameters("Sunday", "10:00AM", "09:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Monday", "11:00AM", "09:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Tuesday", "07:00AM", "01:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Wednesday", "07:00AM", "03:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Thursday", "08:00AM", "08:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Friday", "08:00AM", "08:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Saturday", "08:00AM", "08:00PM");
            schedulePage.clickNextBtnOnCreateScheduleWindow();
            schedulePage.selectWhichWeekToCopyFrom("SUGGESTED");
            schedulePage.clickOnFinishButtonOnCreateSchedulePage();
        } else {
            schedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange("08:00AM", "08:00PM");
        }

        // For copy schedule, select one TM -> create time off for TM -> create schedule by copy last week schedule
        if (isCopySchedule){
            // Delete all the shifts that are assigned to the team member
            schedulePage.convertAllUnAssignedShiftToOpenShift();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM);
            schedulePage.deleteTMShiftInWeekView("open");
            schedulePage.deleteTMShiftInWeekView("unassigned");
            schedulePage.saveSchedule();

            // Create new shift for TM on seven days
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            schedulePage.selectSpecificWorkDay(7);
            schedulePage.selectWorkRole(workRoleOfTM);
            schedulePage.moveSliderAtCertainPoint("3pm", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("10am", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM);
            schedulePage.clickOnOfferOrAssignBtn();

            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            //Get the info of this week for copy schedule
            String firstWeekInfo = schedulePage.getActiveWeekText();
            if (firstWeekInfo.length() > 11) {
                firstWeekInfo = firstWeekInfo.trim().substring(10);
                if (firstWeekInfo.contains("-")) {
                    String[] temp = firstWeekInfo.split("-");
                    if (temp.length == 2 && temp[0].contains(" ") && temp[1].contains(" ")) {
                        firstWeekInfo = temp[0].trim().split(" ")[0] + " " + (temp[0].trim().split(" ")[1].length() == 1 ? "0" + temp[0].trim().split(" ")[1] : temp[0].trim().split(" ")[1])
                                + " - " + temp[1].trim().split(" ")[0] + " " + (temp[1].trim().split(" ")[1].length() == 1 ? "0" + temp[1].trim().split(" ")[1] : temp[1].trim().split(" ")[1]);
                    }
                }
            }

            schedulePage.navigateToNextWeek();
            //Get the date info of the week for create time off
            String activeWeek = schedulePage.getActiveWeekText();
            List<String> year = schedulePage.getYearsFromCalendarMonthYearText();
            String[] items = activeWeek.split(" ");
            String fromDate = year.get(0)+ " " + items[3] + " " + items[4];

            //To avoid one issue -- the schedule cannot be generated when directly go to Team tab
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

           //Go to team page and create time off for tm
            teamPage.goToTeam();
            teamPage.searchAndSelectTeamMemberByName(firstNameOfTM);
            profileNewUIPage.selectProfilePageSubSectionByLabel("Time Off");
            userName = firstNameOfTM.trim() + " " + lastNameOfTM.trim();
            String timeOffReasonLabel = "VACATION";
            String timeOffExplanationText = "Sample Explanation Text";
            profileNewUIPage.createTimeOffOnSpecificDays(timeOffReasonLabel, timeOffExplanationText, fromDate, 6);

            //Go to schedule page and create new schedule by copy last week schedule
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();

            isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            Thread.sleep(5000);
            schedulePage.clickCreateScheduleBtn();
            if (option.equalsIgnoreCase("Yes, except opening/closing shifts")) {
                schedulePage.editOperatingHoursWithGivingPrameters("Sunday", "10:00AM", "09:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Monday", "11:00AM", "09:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Tuesday", "07:00AM", "01:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Wednesday", "07:00AM", "03:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Thursday", "08:00AM", "08:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Friday", "08:00AM", "08:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Saturday", "08:00AM", "08:00PM");
            } else if (ifVerifyOOOHShifts) {
                schedulePage.editOperatingHoursWithGivingPrameters("Sunday", "08:00AM", "04:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Monday", "08:00AM", "04:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Tuesday", "08:00AM", "04:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Wednesday", "08:00AM", "04:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Thursday", "08:00AM", "04:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Friday", "08:00AM", "04:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Saturday", "08:00AM", "04:00PM");
            } else {
                schedulePage.editOperatingHoursWithGivingPrameters("Sunday", "08:00AM", "08:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Monday", "08:00AM", "08:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Tuesday", "08:00AM", "08:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Wednesday", "08:00AM", "08:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Thursday", "08:00AM", "08:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Friday", "08:00AM", "08:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Saturday", "08:00AM", "08:00PM");
            }
            schedulePage.clickNextBtnOnCreateScheduleWindow();
            schedulePage.selectWhichWeekToCopyFrom(firstWeekInfo);
            schedulePage.clickOnFinishButtonOnCreateSchedulePage();
        }

        //Check the Action required smart card is not display
        if (option.equalsIgnoreCase("Yes, all unassigned shifts")) {
            if (!schedulePage.isRequiredActionSmartCardLoaded()) {
                SimpleUtils.pass("Action Required smart card should not be loaded! ");
            } else
                SimpleUtils.assertOnFail("Action Required smart card should not be loaded! ",
                        !schedulePage.getWholeMessageFromActionRequiredSmartCard().contains("Unassigned"), false);
            //Check there is no unassigned shifts
            SimpleUtils.assertOnFail("The unassigned shifts should not display in this schedule! ",
                    schedulePage.getAllShiftsOfOneTM("unassigned").size()==0, false);
            //Check there are open shifts
            for (int i = 0; i<7 ;i++){
                SimpleUtils.assertOnFail("The open shifts display incorrectly! ",
                        schedulePage.getOneDayShiftByName(0, "open").size()>0, false);
            }
        } else if (option.equalsIgnoreCase("Yes, except opening/closing shifts")) {

            //Check the tooltip of publish button
            String tooltip = schedulePage.getTooltipOfPublishButton();
            SimpleUtils.assertOnFail("The tooltip of publish button should display as: Please address required action(s)! But the actual tooltip is: "+ tooltip,
                    tooltip.equalsIgnoreCase("Please address required action(s)"), false);
            //Check the Action required smart card is display
            SimpleUtils.assertOnFail("Action Required smart card should be loaded! ",
                    schedulePage.isRequiredActionSmartCardLoaded(), false);
            //Check the Unassigned shifts will display
            int unassignedShiftsCount = schedulePage.getAllShiftsOfOneTM("unassigned").size();
            SimpleUtils.assertOnFail("Thers are 4 unassigned shifts should display! but actually there are "+unassignedShiftsCount+" display",
                    unassignedShiftsCount >= 4, false);
            //Check the message on the Action required smart card
            HashMap<String, String> message = schedulePage.getUnassignedAndOOOHMessageFromActionRequiredSmartCard();
            SimpleUtils.assertOnFail("Unassigned shifts message display incorrectly! ",
                    message.get("unassigned").equalsIgnoreCase(unassignedShiftsCount+" shifts\n" +"Unassigned") ||
                            message.get("unassigned").equalsIgnoreCase(unassignedShiftsCount+ " unassigned shifts\n" +
                                    "Manually assign or convert to Open"), false);

            List<String> unassignedShiftTimes = new ArrayList<>();
            List<String> openShiftTimes = new ArrayList<>();
            List<WebElement> unassignedShifts = new ArrayList<>();
            List<WebElement> openShifts = new ArrayList<>();
            String unassignedShiftTime = "";
            List<String> complianceMessage = new ArrayList<>();
            for(int i = 0; i< 7; i++){
                unassignedShiftTimes.clear();
                openShiftTimes.clear();
                unassignedShifts.clear();
                openShifts.clear();
                String weekDay = schedulePage.getWeekDayTextByIndex(i);
                if(weekDay.equalsIgnoreCase("Sun")
                        || weekDay.equalsIgnoreCase("Mon")
                        || weekDay.equalsIgnoreCase("Tue")
                        || weekDay.equalsIgnoreCase("Wed")) {

                    unassignedShifts = schedulePage.getOneDayShiftByName(i, "unassigned");
                    openShifts = schedulePage.getOneDayShiftByName(i, "open");
                    SimpleUtils.assertOnFail("There should has at least 1 unassigned shifts in this day! ", unassignedShifts.size()>0, false);

                    for (WebElement unassignedShift: unassignedShifts) {
                        complianceMessage = schedulePage.getComplianceMessageFromInfoIconPopup(unassignedShift);
                        SimpleUtils.assertOnFail("The unassigned violation message display incorrectly in i icon popup! ",
                                complianceMessage.contains("Unassigned Shift"), false);
                        unassignedShiftTimes.add(unassignedShift.findElement(By.className("week-schedule-shift-time")).getText());
                    }
                    for(WebElement openShift: openShifts){
                        openShiftTimes.add(openShift.findElement(By.className("week-schedule-shift-time")).getText()) ;
                    }

                    if(unassignedShiftTimes.contains("10am - 3pm") && !openShiftTimes.contains("10am - 3pm")) {
                        SimpleUtils.pass("The opening/closing shifts display as unassigned! ");
                    } else
                        SimpleUtils.fail("The opening/closing shifts are not display as unassigned! ", false);
                } else {
                    unassignedShifts = schedulePage.getOneDayShiftByName(i, "unassigned");
                    openShifts = schedulePage.getOneDayShiftByName(i, "open");

                    SimpleUtils.assertOnFail("There should has at least 1 open shifts in this day! ", openShifts.size()>0, false);

                    for (WebElement unassignedShift: unassignedShifts) {
                        complianceMessage = schedulePage.getComplianceMessageFromInfoIconPopup(unassignedShift);
                        SimpleUtils.assertOnFail("The unassigned violation message display incorrectly in i icon popup! ",
                                complianceMessage.contains("Unassigned Shift"), false);
                        unassignedShiftTimes.add(unassignedShift.findElement(By.className("week-schedule-shift-time")).getText());
                    }
                    for(WebElement openShift: openShifts){
                        openShiftTimes.add(openShift.findElement(By.className("week-schedule-shift-time")).getText()) ;
                    }
                    if(!unassignedShiftTimes.contains("10am - 3pm") && openShiftTimes.contains("10am - 3pm")) {
                        SimpleUtils.pass("The opening/closing shifts display as open! ");
                    } else
                        SimpleUtils.fail("The opening/closing shifts are not display as open! ", false);
                }
            }

            //Convert unassigned shifts to open
            schedulePage.convertAllUnAssignedShiftToOpenShift();
            Thread.sleep(10000);
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteAllOOOHShiftInWeekView();
            schedulePage.saveSchedule();

            //Check there is no Unassigned shifts display
            unassignedShiftsCount = schedulePage.getAllShiftsOfOneTM("unassigned").size();
            SimpleUtils.assertOnFail("Unassigned shifts should display! ",
                    unassignedShiftsCount == 0, false);
            //Check the Action required smart card is display
            SimpleUtils.assertOnFail("Action Required smart card should not be loaded! ",
                    !schedulePage.isRequiredActionSmartCardLoaded(), false);

            //Check the publish button is enabled
            SimpleUtils.assertOnFail("The tooltip of publish button should display as blank! But the actual tooltip is: "+ tooltip,
                    schedulePage.getTooltipOfPublishButton().equalsIgnoreCase(""), false);
            schedulePage.publishActiveSchedule();
            SimpleUtils.assertOnFail("The schedule fail to publish! ", schedulePage.isCurrentScheduleWeekPublished(), false);

        } else {
            //Check the tooltip of publish button
            String tooltip = schedulePage.getTooltipOfPublishButton();
            SimpleUtils.assertOnFail("The tooltip of publish button should display as: Please address required action(s)! But the actual tooltip is: "+ tooltip,
                    tooltip.equalsIgnoreCase("Please address required action(s)"), false);
            //Check there is no open shifts
            SimpleUtils.assertOnFail("The open shifts should not display in this schedule! ",
                    schedulePage.getAllShiftsOfOneTM("open").size()==0, false);
            //Check the Unassigned shifts will display
            int unassignedShiftsCount = schedulePage.getAllShiftsOfOneTM("unassigned").size();
            SimpleUtils.assertOnFail("Unassigned shifts should display! ",unassignedShiftsCount >= 7, false);
            //Check the Action required smart card is display
            SimpleUtils.assertOnFail("Action Required smart card should be loaded! ",
                    schedulePage.isRequiredActionSmartCardLoaded(), false);
            //Check there are unassigned shifts on every day
            for (int i = 0; i<7 ;i++){
                SimpleUtils.assertOnFail("The unassigned shifts in the "+ i+ " day display incorrectly! ",
                        schedulePage.getOneDayShiftByName(i, "unassigned").size()>0, false);
            }


            //Check the message on the Action required smart card
            int oOOHShiftsCount = 0;
            if(ifVerifyOOOHShifts){
                //Check the message on the Action required smart card
                List<WebElement> allOOOHShifts = schedulePage.getAllOOOHShifts();
                oOOHShiftsCount = allOOOHShifts.size();
                HashMap<String, String> message = schedulePage.getUnassignedAndOOOHMessageFromActionRequiredSmartCard();
                SimpleUtils.assertOnFail("The unassiged and OOOH shifts message display incorrectly! The actual message is: " + message,
                        message.get("OOOH").equalsIgnoreCase(oOOHShiftsCount+ " shifts\n" +
                                "Outside Operating Hours") &&
                                message.get("unassigned").equalsIgnoreCase(unassignedShiftsCount+" shifts\n" +"Unassigned"),
                        false);
            } else {
                HashMap<String, String> message = schedulePage.getUnassignedAndOOOHMessageFromActionRequiredSmartCard();
                SimpleUtils.assertOnFail("Unassigned shifts message display incorrectly! ",
                        message.get("unassigned").equalsIgnoreCase(unassignedShiftsCount+" shifts\n" +"Unassigned") ||
                                message.get("unassigned").equalsIgnoreCase(unassignedShiftsCount+ " unassigned shifts\n" +
                                        "Manually assign or convert to Open"), false);
            }

            //Verify view shifts button
            schedulePage.clickOnViewShiftsBtnOnRequiredActionSmartCard();
            SimpleUtils.assertOnFail("The unassigned shifts count is not correct after click View Shifts! ",
                    schedulePage.getAllShiftsOfOneTM("unassigned").size() == unassignedShiftsCount,
                    false);
            if(ifVerifyOOOHShifts){
                SimpleUtils.assertOnFail("The OOOH shifts count is not correct after click View Shifts! ",
                        schedulePage.getAllOOOHShifts().size() == oOOHShiftsCount, false);
            }
            ArrayList<WebElement> allShiftsInWeekView = schedulePage.getAllAvailableShiftsInWeekView();
            List<String> complianceMessage = new ArrayList<>();
            for (int i = 0; i< allShiftsInWeekView.size(); i ++) {
                complianceMessage = schedulePage.getComplianceMessageFromInfoIconPopup(allShiftsInWeekView.get(i));
                SimpleUtils.assertOnFail("The unassigned violation message display incorrectly in i icon popup! ",
                        complianceMessage.contains("Unassigned Shift") || complianceMessage.contains("Outside Operating hours"), false);
            }



            //Check unassigned shifts on day view
            List<WebElement> allShiftsInDayView = new ArrayList<>();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnDayView();
            if(isCopySchedule && option.equalsIgnoreCase("No, keep as unassigned")){
                for (int i = 0; i< 7; i++) {
                    scheduleCommonPage.navigateDayViewWithIndex(i);
                    if (schedulePage.isRequiredActionSmartCardLoaded()){
                        SimpleUtils.pass("The " +i+ " day has unassigned shifts! ");
                        Thread.sleep(2000);
                        allShiftsInDayView = schedulePage.getAvailableShiftsInDayView();
                        if (ifVerifyOOOHShifts){
                            HashMap<String, String> message = schedulePage.getUnassignedAndOOOHMessageFromActionRequiredSmartCard();
                            oOOHShiftsCount = schedulePage.getAllOOOHShifts().size();
                            unassignedShiftsCount = schedulePage.getAllShiftsOfOneTM("unassigned").size();
                            SimpleUtils.assertOnFail("The unassiged and OOOH shifts message display incorrectly! The actual message is: " + message,(
                                    message.get("OOOH").equalsIgnoreCase(oOOHShiftsCount+ " shifts\n" +
                                            "Outside Operating Hours")
                                            ||message.get("OOOH").equalsIgnoreCase(oOOHShiftsCount+ " shift\n" +
                                            "Outside Operating Hours") &&
                                            (message.get("unassigned").equalsIgnoreCase(unassignedShiftsCount+" shifts\n" +"Unassigned")
                                                    ||message.get("unassigned").equalsIgnoreCase(unassignedShiftsCount+" shift\n" +"Unassigned")))
                                            , false);
                        }
                        if (allShiftsInDayView.size()>0){
                            for (int j = 0; j< allShiftsInDayView.size(); j ++) {
                                complianceMessage = schedulePage.getComplianceMessageFromInfoIconPopup(allShiftsInDayView.get(j));
                                SimpleUtils.assertOnFail("The unassigned violation message display incorrectly in i icon popup! ",
                                        complianceMessage.contains("Unassigned Shift") || complianceMessage.contains("Outside Operating hours"), false);
                            }
                        } else
                            SimpleUtils.fail("There is no shifts in that day! ", false);

                    } else
                        SimpleUtils.fail("The " +i+ " day should has unassigned shifts! ", false);
                }
            } else if (!isCopySchedule && option.equalsIgnoreCase("No, keep as unassigned")) {
                boolean hasActionRequiredSmartCardInDayView = false;
                for (int i = 0; i< 7; i++) {
                    scheduleCommonPage.navigateDayViewWithIndex(i);
                    if (schedulePage.isRequiredActionSmartCardLoaded()){
                        hasActionRequiredSmartCardInDayView = true;
                        allShiftsInDayView = schedulePage.getAvailableShiftsInDayView();
                        for (int j = 0; j< allShiftsInDayView.size(); j ++) {
                            complianceMessage = schedulePage.getComplianceMessageFromInfoIconPopup(allShiftsInDayView.get(j));
                            SimpleUtils.assertOnFail("The unassigned violation message display incorrectly in i icon popup! ",
                                    complianceMessage.contains("Unassigned Shift") || complianceMessage.contains("Outside Operating hours"), false);
                        }


                    }
                }
                SimpleUtils.assertOnFail("The Action Required smart card is not display in day view! ", hasActionRequiredSmartCardInDayView, false);
            }


            //Convert unassigned shifts to open
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            schedulePage.clickOnFilterBtn();
            schedulePage.convertAllUnAssignedShiftToOpenShift();
            Thread.sleep(10000);
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView("Unassigned");
            schedulePage.saveSchedule();
            if (ifVerifyOOOHShifts){
                HashMap<String, String> message = schedulePage.getUnassignedAndOOOHMessageFromActionRequiredSmartCard();
                oOOHShiftsCount = schedulePage.getAllOOOHShifts().size();
                SimpleUtils.assertOnFail("The unassiged and OOOH shifts message display incorrectly! The actual message is: " + message,
                        message.get("OOOH").equalsIgnoreCase(oOOHShiftsCount+ " shifts\n" +
                                "Outside Operating Hours") &&
                                message.get("unassigned").equals(""),
                        false);
            }
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteAllOOOHShiftInWeekView();
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteAllOOOHShiftInWeekView();
            schedulePage.saveSchedule();

            //Check there is no Unassigned shifts display
            unassignedShiftsCount = schedulePage.getAllShiftsOfOneTM("unassigned").size();
            SimpleUtils.assertOnFail("Unassigned shifts should not display! but there are "+unassignedShiftsCount+" unassigned shifts display!",
                    unassignedShiftsCount == 0, false);
            if (ifVerifyOOOHShifts) {
                oOOHShiftsCount = schedulePage.getAllOOOHShifts().size();
                SimpleUtils.assertOnFail("OOOH shifts should display! ",
                        oOOHShiftsCount == 0, false);
            }
            //Check the Action required smart card is not display
            SimpleUtils.assertOnFail("Action Required smart card should not be loaded! ",
                    !schedulePage.isRequiredActionSmartCardLoaded(), false);

            //Check the publish button is enabled
            SimpleUtils.assertOnFail("The tooltip of publish button should display as blank! But the actual tooltip is: "+ tooltip,
                    schedulePage.getTooltipOfPublishButton().equalsIgnoreCase(""), false);
            schedulePage.publishActiveSchedule();
            SimpleUtils.assertOnFail("The schedule fail to publish! ", schedulePage.isCurrentScheduleWeekPublished(), false);
        }

        //Check the schedule can be saved and published
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        String workRole = schedulePage.getRandomWorkRole();
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.customizeNewShiftPage();
        schedulePage.selectWorkRole(workRole);

        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();

        schedulePage.saveSchedule();
        schedulePage.publishActiveSchedule();


        if (isCopySchedule) {
            //Reject the approved time offs
            teamPage.goToTeam();
            teamPage.searchAndSelectTeamMemberByName(userName);
            profileNewUIPage.selectProfilePageSubSectionByLabel("Time Off");
            profileNewUIPage.rejectAllTimeOff();
        }

    }


    private void changeConvertToOpenShiftsSettings(String option, String location) throws Exception {
        if (getDriver().getCurrentUrl().contains(propertyMap.get(controlEnterprice))){
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsPage.gotoControlsPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
            SimpleUtils.assertOnFail("Scheduling collaboration page not loaded successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
            controlsNewUIPage.clickOnScheduleCollaborationOpenShiftAdvanceBtn();

            //Set 'Automatically convert unassigned shifts to open shifts when generating the schedule?' set as Yes, all unassigned shifts
            controlsNewUIPage.updateConvertUnassignedShiftsToOpenSettingOption(option);

        } else if (getDriver().getCurrentUrl().contains(propertyMap.get(opEnterprice))) {

            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.searchLocation(location);               ;
            SimpleUtils.assertOnFail("Locations not searched out Successfully!",  locationsPage.verifyUpdateLocationResult(location), false);
            locationsPage.clickOnLocationInLocationResult(location);
            locationsPage.clickOnConfigurationTabOfLocation();
            HashMap<String, String> templateTypeAndName = locationsPage.getTemplateTypeAndNameFromLocation();
            OpsPortalLocationsPage opsPortalLocationsPage = (OpsPortalLocationsPage) pageFactory.createOpsPortalLocationsPage();
//            opsPortalLocationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad("Schedule Collaboration");
            configurationPage.clickOnSpecifyTemplateName(templateTypeAndName.get("Schedule Collaboration"), "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.updateConvertUnassignedShiftsToOpenWhenCreatingScheduleSettingOption(option);
            configurationPage.updateConvertUnassignedShiftsToOpenWhenCopyingScheduleSettingOption(option);
            configurationPage.publishNowTheTemplate();
            Thread.sleep(3000);
            switchToConsoleWindow();
        }
    }

    private void disableCopyRestriction(String location) throws Exception {
        if (getDriver().getCurrentUrl().contains(propertyMap.get(controlEnterprice))){
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            controlsPage.gotoControlsPage();
            controlsPage.clickGlobalSettings();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            Thread.sleep(20000);
            controlsNewUIPage.enableOrDisableScheduleCopyRestriction("no");

        } else if (getDriver().getCurrentUrl().contains(propertyMap.get(opEnterprice))) {
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.searchLocation(location);               ;
            SimpleUtils.assertOnFail("Locations not searched out Successfully!",  locationsPage.verifyUpdateLocationResult(location), false);
            locationsPage.clickOnLocationInLocationResult(location);
            locationsPage.clickOnConfigurationTabOfLocation();
            HashMap<String, String> templateTypeAndName = locationsPage.getTemplateTypeAndNameFromLocation();
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad("Scheduling Policies");
            configurationPage.clickOnSpecifyTemplateName(templateTypeAndName.get("Scheduling Policies"), "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.setScheduleCopyRestrictions("no");
            configurationPage.publishNowTheTemplate();
            switchToConsoleWindow();
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the Outside of Operating Hours shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheOOOHShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
//            if (getDriver().getCurrentUrl().contains(propertyMap.get(controlEnterprice))){
//                disableCopyRestriction();
//            }
            //Go to schedule page and create new schedule
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            Thread.sleep(3000);
            schedulePage.createScheduleForNonDGFlowNewUI();
            schedulePage.convertAllUnAssignedShiftToOpenShift();
            schedulePage.publishActiveSchedule();
            //Get the info of this week for copy schedule
            String firstWeekInfo = schedulePage.getActiveWeekText();
            if (firstWeekInfo.length() > 11) {
                firstWeekInfo = firstWeekInfo.trim().substring(10);
                if (firstWeekInfo.contains("-")) {
                    String[] temp = firstWeekInfo.split("-");
                    if (temp.length == 2 && temp[0].contains(" ") && temp[1].contains(" ")) {
                        firstWeekInfo = temp[0].trim().split(" ")[0] + " " + (temp[0].trim().split(" ")[1].length() == 1 ? "0" + temp[0].trim().split(" ")[1] : temp[0].trim().split(" ")[1])
                                + " - " + temp[1].trim().split(" ")[0] + " " + (temp[1].trim().split(" ")[1].length() == 1 ? "0" + temp[1].trim().split(" ")[1] : temp[1].trim().split(" ")[1]);
                    }
                }
            }
            schedulePage.navigateToNextWeek();
            isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            Thread.sleep(5000);
            schedulePage.clickCreateScheduleBtn();
            schedulePage.editOperatingHoursWithGivingPrameters("Sunday", "08:00AM", "04:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Monday", "08:00AM", "04:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Tuesday", "08:00AM", "04:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Wednesday", "08:00AM", "04:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Thursday", "08:00AM", "04:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Friday", "08:00AM", "04:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Saturday", "08:00AM", "04:00PM");
            schedulePage.clickNextBtnOnCreateScheduleWindow();
            schedulePage.selectWhichWeekToCopyFrom(firstWeekInfo);
            schedulePage.clickOnFinishButtonOnCreateSchedulePage();

            //Check the tooltip of publish button
            String tooltip = schedulePage.getTooltipOfPublishButton();
            SimpleUtils.assertOnFail("The tooltip of publish button should display as: Please address required action(s)! But the actual tooltip is: "+ tooltip,
                    tooltip.equalsIgnoreCase("Please address required action(s)"), false);

            //Check the Action required smart card is display
            SimpleUtils.assertOnFail("Action Required smart card should be loaded! ",
                    schedulePage.isRequiredActionSmartCardLoaded(), false);

            //Check the message on the Action required smart card
            List<WebElement> allOOOHShifts = schedulePage.getAllOOOHShifts();
            int oOOHShiftsCount = allOOOHShifts.size();
            HashMap<String, String> message = schedulePage.getUnassignedAndOOOHMessageFromActionRequiredSmartCard();
            SimpleUtils.assertOnFail("OOOH shifts message display incorrectly! The actual message is: " + message.get("OOOH"),
                            message.get("OOOH").equalsIgnoreCase(oOOHShiftsCount+ " shifts\n" +
                                    "Outside Operating Hours"), false);

            //Verify view shifts button
            schedulePage.clickOnViewShiftsBtnOnRequiredActionSmartCard();
            SimpleUtils.assertOnFail("The OOOH shifts count is not correct after click View Shifts! ",
                    schedulePage.getAllOOOHShifts().size() == oOOHShiftsCount, false);

            ArrayList<WebElement> allShiftsInWeekView = schedulePage.getAllAvailableShiftsInWeekView();
            List<String> complianceMessage = new ArrayList<>();
            for (int i = 0; i< allShiftsInWeekView.size(); i ++) {
                complianceMessage = schedulePage.getComplianceMessageFromInfoIconPopup(allShiftsInWeekView.get(i));
                SimpleUtils.assertOnFail("The OOOH violation message display incorrectly in i icon popup! ",
                        complianceMessage.contains("Unassigned Shift") || complianceMessage.contains("Outside Operating hours"), false);
            }

            //Check OOOH shifts on day view
            List<WebElement> allShiftsInDayView = new ArrayList<>();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnDayView();

            for (int i = 0; i< 7; i++) {
                scheduleCommonPage.navigateDayViewWithIndex(i);
                if (schedulePage.isRequiredActionSmartCardLoaded()){
                    SimpleUtils.pass("The " +i+ " day has OOOH shifts! ");
                    Thread.sleep(2000);
                    allShiftsInDayView = schedulePage.getAvailableShiftsInDayView();
                    if (allShiftsInDayView.size()>0){
                        for (int j = 0; j< allShiftsInDayView.size(); j ++) {
                            complianceMessage = schedulePage.getComplianceMessageFromInfoIconPopup(allShiftsInDayView.get(j));
                            SimpleUtils.assertOnFail("The unassigned violation message display incorrectly in i icon popup! ",
                                    complianceMessage.contains("Unassigned Shift") || complianceMessage.contains("Outside Operating hours"), false);
                        }
                    } else
                        SimpleUtils.fail("There is no shifts in that day! ", false);

                } else
                    SimpleUtils.fail("The " +i+ " day should has OOOH shifts! ", false);
            }

            //Convert unassigned shifts to open
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            schedulePage.clickOnFilterBtn();
            schedulePage.convertAllUnAssignedShiftToOpenShift();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteAllOOOHShiftInWeekView();
            schedulePage.saveSchedule();

            //Check there is no OOOH shifts display
            allOOOHShifts = schedulePage.getAllOOOHShifts();
            oOOHShiftsCount = allOOOHShifts.size();
            SimpleUtils.assertOnFail("OOOH shifts should not display! ",
                    oOOHShiftsCount == 0, false);
            //Check the Action required smart card is not display
            SimpleUtils.assertOnFail("Action Required smart card should not be loaded! ",
                    !schedulePage.isRequiredActionSmartCardLoaded(), false);

            //Check the publish button is enabled
            SimpleUtils.assertOnFail("The tooltip of publish button should display as blank! But the actual tooltip is: "+ tooltip,
                    schedulePage.getTooltipOfPublishButton().equalsIgnoreCase(""), false);
            schedulePage.publishActiveSchedule();
            SimpleUtils.assertOnFail("The schedule fail to publish! ", schedulePage.isCurrentScheduleWeekPublished(), false);


        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the hierarchy/priority order for all smart cards")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheHierarchyPriorityOrderForAllSmartCardsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String option = "No, keep as unassigned";
            changeConvertToOpenShiftsSettings(option, location);

            //Go to schedule page and create new schedule
            Thread.sleep(2000);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            schedulePage.navigateToNextWeek();

            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();

            //Check the tooltip of publish button
            String tooltip = schedulePage.getTooltipOfPublishButton();
            SimpleUtils.assertOnFail("The tooltip of publish button should display as: Please address required action(s)! But the actual tooltip is: "+ tooltip,
                    tooltip.equalsIgnoreCase("Please address required action(s)"), false);

            //Check the Action required smart card is display
            SimpleUtils.assertOnFail("Action Required smart card should be loaded! ",
                    schedulePage.isRequiredActionSmartCardLoaded(), false);

            //Check the message on the Action required smart card
            int unassignedShiftsCount = schedulePage.getAllShiftsOfOneTM("unassigned").size();
            HashMap<String, String> message = schedulePage.getUnassignedAndOOOHMessageFromActionRequiredSmartCard();
            SimpleUtils.assertOnFail("Unassigned shifts message display incorrectly! ",
                    message.get("unassigned").equalsIgnoreCase(unassignedShiftsCount+" shifts\n" +"Unassigned") ||
                            message.get("unassigned").equalsIgnoreCase(unassignedShiftsCount+ " unassigned shifts\n" +
                                    "Manually assign or convert to Open"), false);

            //Convert unassigned shifts to open
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            schedulePage.clickOnFilterBtn();
            schedulePage.convertAllUnAssignedShiftToOpenShift();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteAllOOOHShiftInWeekView();
            schedulePage.saveSchedule();

            //Check the Action required smart card is not display
            SimpleUtils.assertOnFail("Action Required smart card should not be loaded! ",
                    !schedulePage.isRequiredActionSmartCardLoaded(), false);
            //Check the Schedule Not Publish smart card will display
            SimpleUtils.assertOnFail("Schedule not published smart card should display for new generate schedule! ",
                    schedulePage.isScheduleNotPublishedSmartCardLoaded(),false);

            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //Check the Schedule Not Publish smart card will not display on edit mode
            SimpleUtils.assertOnFail("Schedule not published smart card should not display on edit mode! ",
                    !schedulePage.isScheduleNotPublishedSmartCardLoaded(),false);
            schedulePage.saveSchedule();
            //Check the Schedule Not Publish smart card will display
            SimpleUtils.assertOnFail("Schedule not published smart card should display for new generate schedule! ",
                    schedulePage.isScheduleNotPublishedSmartCardLoaded(),false);

            schedulePage.publishActiveSchedule();
            SimpleUtils.assertOnFail("The schedule fail to publish! ", schedulePage.isCurrentScheduleWeekPublished(), false);
            //Check the Schedule Not Publish smart card will not display after publish
            SimpleUtils.assertOnFail("Schedule not published smart card should not display after publish! ",
                    !schedulePage.isScheduleNotPublishedSmartCardLoaded(),false);
            //Check the Action required smart card is not display
            SimpleUtils.assertOnFail("Action Required smart card should not be loaded! ",
                    !schedulePage.isRequiredActionSmartCardLoaded(), false);

            //Edit the schedule
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            String workRole = schedulePage.getRandomWorkRole();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.selectWorkRole(workRole);

            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.saveSchedule();

            schedulePage.verifyChangesNotPublishSmartCard(1);

            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //Check the Change Not Publish smart card will not display after publish
            SimpleUtils.assertOnFail("Change not published smart card should not display after publish! ",
                    !schedulePage.isScheduleNotPublishedSmartCardLoaded(),false);

            schedulePage.saveSchedule();
            //Check the Schedule Not Publish smart card will display
            SimpleUtils.assertOnFail("Schedule not published smart card should display for new generate schedule! ",
                    schedulePage.isScheduleNotPublishedSmartCardLoaded(),false);
            schedulePage.publishActiveSchedule();
            //Check the Change Not Publish smart card will not display after publish
            SimpleUtils.assertOnFail("Change not published smart card should not display after publish! ",
                    !schedulePage.isScheduleNotPublishedSmartCardLoaded(),false);


        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }



    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate one shift which has both Unassigned and OOH violation")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateOneShiftWhichHasBothUnassignedAndOOHViolationAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
//            if (getDriver().getCurrentUrl().contains(propertyMap.get(controlEnterprice))){
//                disableCopyRestriction();
//            }
            String option = "No, keep as unassigned";
            changeConvertToOpenShiftsSettings(option, location);

            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            HashMap<String, Object[][]> teamMembers = null;
            if (getDriver().getCurrentUrl().contains(propertyMap.get(controlEnterprice))){
                teamMembers = controlTeamMembers;
            } else {
                teamMembers = opTeamMembers;
            }

            String firstNameOfTM = teamMembers.get("TeamMember1")[0][0].toString();
            String lastNameOfTM = teamMembers.get("TeamMember1")[0][1].toString();
            String workRoleOfTM = teamMembers.get("TeamMember1")[0][2].toString();

            teamPage.goToTeam();

            if (teamPage.checkIfTMExists(firstNameOfTM)) {
                teamPage.searchAndSelectTeamMemberByName(firstNameOfTM);
                if(teamPage.isManualOnBoardButtonLoaded()) {
                    teamPage.manualOnBoardTeamMember();
                }
                if (teamPage.isActivateButtonLoaded()) {
                    teamPage.clickOnActivateButton();
                    teamPage.isActivateWindowLoaded();
                    teamPage.selectADateOnCalendarAndActivate();
                }
                if (teamPage.isCancelTerminateButtonLoaded()) {
                    teamPage.cancelTMTerminate();
                }
                if (teamPage.isCancelDeactivateButtonLoaded()) {
                    teamPage.cancelTMDeactivate();
                }

                profileNewUIPage.selectProfilePageSubSectionByLabel("Time Off");
                profileNewUIPage.rejectAllTimeOff();
                profileNewUIPage.cancelAllTimeOff();

            } else
                SimpleUtils.fail("The team member '"+ firstNameOfTM +"' is not exists! ", false);

            //Go to schedule page and create new schedule
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            String userName = "";
            Thread.sleep(2000);
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            Thread.sleep(5000);
            schedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange("08:00AM", "08:00PM");

            // For copy schedule, select one TM -> create time off for TM -> create schedule by copy last week schedule

            // Delete all the shifts that are assigned to the team member
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM);
            schedulePage.deleteTMShiftInWeekView("open");
            schedulePage.deleteTMShiftInWeekView("unassigned");
            schedulePage.saveSchedule();

            // Create new shift for TM on seven days
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            schedulePage.selectSpecificWorkDay(7);
            schedulePage.selectWorkRole(workRoleOfTM);
            schedulePage.moveSliderAtCertainPoint("1pm", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("8am", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM);
            schedulePage.clickOnOfferOrAssignBtn();

            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            //Get the info of this week for copy schedule
            String firstWeekInfo = schedulePage.getActiveWeekText();
            if (firstWeekInfo.length() > 11) {
                firstWeekInfo = firstWeekInfo.trim().substring(10);
                if (firstWeekInfo.contains("-")) {
                    String[] temp = firstWeekInfo.split("-");
                    if (temp.length == 2 && temp[0].contains(" ") && temp[1].contains(" ")) {
                        firstWeekInfo = temp[0].trim().split(" ")[0] + " " + (temp[0].trim().split(" ")[1].length() == 1 ? "0" + temp[0].trim().split(" ")[1] : temp[0].trim().split(" ")[1])
                                + " - " + temp[1].trim().split(" ")[0] + " " + (temp[1].trim().split(" ")[1].length() == 1 ? "0" + temp[1].trim().split(" ")[1] : temp[1].trim().split(" ")[1]);
                    }
                }
            }

            schedulePage.navigateToNextWeek();
            //Get the date info of the week for create time off
            String activeWeek = schedulePage.getActiveWeekText();
            List<String> year = schedulePage.getYearsFromCalendarMonthYearText();
            String[] items = activeWeek.split(" ");
            String fromDate = year.get(0)+ " " + items[3] + " " + items[4];

            //To avoid one issue -- the schedule cannot be generated when directly go to Team tab
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            //Go to team page and create time off for tm
            teamPage.goToTeam();
            userName = firstNameOfTM.trim() + " " + lastNameOfTM.trim();
            teamPage.searchAndSelectTeamMemberByName(userName);

            profileNewUIPage.selectProfilePageSubSectionByLabel("Time Off");
            String timeOffReasonLabel = "VACATION";
            String timeOffExplanationText = "Sample Explanation Text";
            profileNewUIPage.createTimeOffOnSpecificDays(timeOffReasonLabel, timeOffExplanationText, fromDate, 6);

            //Go to schedule page and create new schedule by copy last week schedule
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.clickCreateScheduleBtn();
            schedulePage.editOperatingHoursWithGivingPrameters("Sunday", "11:00AM", "05:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Monday", "08:00AM", "08:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Tuesday", "08:00AM", "08:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Wednesday", "08:00AM", "08:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Thursday", "08:00AM", "08:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Friday", "08:00AM", "08:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Saturday", "08:00AM", "08:00PM");
            schedulePage.clickNextBtnOnCreateScheduleWindow();
            schedulePage.selectWhichWeekToCopyFrom(firstWeekInfo);
            schedulePage.clickOnFinishButtonOnCreateSchedulePage();


            //Check the tooltip of publish button
            String tooltip = schedulePage.getTooltipOfPublishButton();
            SimpleUtils.assertOnFail("The tooltip of publish button should display as: Please address required action(s)! But the actual tooltip is: "+ tooltip,
                    tooltip.equalsIgnoreCase("Please address required action(s)"), false);
            //Check the Unassigned shifts will display
            int unassignedShiftsCount = schedulePage.getAllShiftsOfOneTM("unassigned").size();
            SimpleUtils.assertOnFail("Unassigned shifts should display! ",unassignedShiftsCount >= 0, false);
            //Check the Action required smart card is display
            SimpleUtils.assertOnFail("Action Required smart card should be loaded! ",
                    schedulePage.isRequiredActionSmartCardLoaded(), false);

            int oOOHShiftsCount = 0;
            List<WebElement> allOOOHShifts = schedulePage.getAllOOOHShifts();
            oOOHShiftsCount = allOOOHShifts.size();
            HashMap<String, String> message = schedulePage.getUnassignedAndOOOHMessageFromActionRequiredSmartCard();
            SimpleUtils.assertOnFail("The unassiged and OOOH shifts message display incorrectly! The actual message is: " + message,
                    (message.get("OOOH").equalsIgnoreCase(oOOHShiftsCount+ " shifts\n" +
                            "Outside Operating Hours") || message.get("OOOH").equalsIgnoreCase(oOOHShiftsCount+ " shift\n" +
                            "Outside Operating Hours"))&&
                            (message.get("unassigned").equalsIgnoreCase(unassignedShiftsCount+" shifts\n" +"Unassigned")
                            || message.get("unassigned").equalsIgnoreCase(unassignedShiftsCount+" shift\n" +"Unassigned")),
                    false);

            List<String> unassignedShiftTimes = new ArrayList<>();
            List<String> openShiftTimes = new ArrayList<>();
            List<WebElement> unassignedShifts = new ArrayList<>();
            List<WebElement> openShifts = new ArrayList<>();
            List<String> complianceMessage = new ArrayList<>();
            for(int i = 0; i< 7; i++){
                unassignedShiftTimes.clear();
                openShiftTimes.clear();
                unassignedShifts.clear();
                openShifts.clear();
                String weekDay = schedulePage.getWeekDayTextByIndex(i);
                if(weekDay.equalsIgnoreCase("Sun")) {
                    unassignedShifts = schedulePage.getOneDayShiftByName(i, "unassigned");
                    SimpleUtils.assertOnFail("There should has at least 1 unassigned shifts in this day! ", unassignedShifts.size()>0, false);
                    boolean isTheShiftWithBothViolationsExist = false;
                    for (WebElement unassignedShift: unassignedShifts) {
                        unassignedShiftsCount = schedulePage.getAllShiftsOfOneTM("unassigned").size();
                        oOOHShiftsCount = schedulePage.getAllOOOHShifts().size();
                        unassignedShiftTimes.add(unassignedShift.findElement(By.className("week-schedule-shift-time")).getText());
                        if(unassignedShiftTimes.contains("8am - 1pm")) {
                            isTheShiftWithBothViolationsExist = true;
                            //To close the i icon popup
                            schedulePage.clickOnOpenSearchBoxButton();
                            complianceMessage = schedulePage.getComplianceMessageFromInfoIconPopup(unassignedShift);
                            SimpleUtils.assertOnFail("The unassigned violation message display incorrectly in i icon popup! ",
                                    complianceMessage.contains("Unassigned Shift") && complianceMessage.contains("Outside Operating hours"), false);
                            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                            schedulePage.convertUnAssignedShiftToOpenShift(unassignedShift);
                            schedulePage.saveSchedule();
                            complianceMessage = schedulePage.getComplianceMessageFromInfoIconPopup(unassignedShift);
                            SimpleUtils.assertOnFail("The unassigned violation message display incorrectly in i icon popup! ",
                                    !complianceMessage.contains("Unassigned Shift") && complianceMessage.contains("Outside Operating hours"), false);
                            message = schedulePage.getUnassignedAndOOOHMessageFromActionRequiredSmartCard();
                            SimpleUtils.assertOnFail("The unassiged and OOOH shifts message display incorrectly! The actual message is: " + message,
                                    message.get("OOOH").equalsIgnoreCase(oOOHShiftsCount+ " shifts\n" +
                                            "Outside Operating Hours") &&
                                            (message.get("unassigned").equalsIgnoreCase(unassignedShiftsCount-1+" shifts\n" +"Unassigned")
                                                    || message.get("unassigned").equalsIgnoreCase("")), false);
                            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                            schedulePage.editTheShiftTimeForSpecificShift(unassignedShift, "11am", "3pm");
                            schedulePage.saveSchedule();
                            complianceMessage = schedulePage.getComplianceMessageFromInfoIconPopup(unassignedShift);
                            SimpleUtils.assertOnFail("The unassigned violation message display incorrectly in i icon popup! ",
                                    !complianceMessage.contains("Unassigned Shift") && !complianceMessage.contains("Outside Operating hours"), false);
                            message = schedulePage.getUnassignedAndOOOHMessageFromActionRequiredSmartCard();
                            SimpleUtils.assertOnFail("The unassiged and OOOH shifts message display incorrectly! The actual message is: " + message,
                                    schedulePage.isRequiredActionSmartCardLoaded() ||
                                            ((message.get("OOOH").equalsIgnoreCase(oOOHShiftsCount-1+ " shifts\n" +
                                            "Outside Operating Hours") || message.get("OOOH").equalsIgnoreCase("")) &&
                                            (message.get("unassigned").equalsIgnoreCase(unassignedShiftsCount-1+" shifts\n" +"Unassigned")
                                                    || message.get("unassigned").equalsIgnoreCase(""))), false);
                            break;
                        }

                    }
                    if(!isTheShiftWithBothViolationsExist) {
                        SimpleUtils.fail("Get the shift with both OOOH and Unassigned violations failed! ", false);
                    }
                }
            }

            //Check the schedule can be saved and published
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            String workRole = schedulePage.getRandomWorkRole();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.selectWorkRole(workRole);

            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.saveSchedule();

            schedulePage.convertAllUnAssignedShiftToOpenShift();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteAllOOOHShiftInWeekView();
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            teamPage.goToTeam();
            teamPage.searchAndSelectTeamMemberByName(userName);
            profileNewUIPage.selectProfilePageSubSectionByLabel("Time Off");
            profileNewUIPage.rejectAllTimeOff();

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }



    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate that if an employee is on PTO, leave or terminated as of the new week, the shift day/time should be copied but will be unassigned")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateThatIfAnEmployeeIsOnPTOLeaveOrTerminatedAsOfTheNewWeekTheShiftDayTimeShouldBeCopiedButWillBeUnassignedAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
//            if (getDriver().getCurrentUrl().contains(propertyMap.get(controlEnterprice))){
//                disableCopyRestriction();
//            }
            String option = "No, keep as unassigned";
            changeConvertToOpenShiftsSettings(option, location);
            HashMap<String, Object[][]> teamMembers = null;
            if (getDriver().getCurrentUrl().contains(propertyMap.get(controlEnterprice))){
                teamMembers = controlTeamMembers;
            } else {
                teamMembers = opTeamMembers;
            }

            String tm1 = teamMembers.get("TeamMember1")[0][0].toString();
            String tm2 = teamMembers.get("TeamMember2")[0][0].toString();

            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.activeTMAndRejectOrApproveAllAvailabilityAndTimeOff(tm1);
            teamPage.activeTMAndRejectOrApproveAllAvailabilityAndTimeOff(tm2);

            Thread.sleep(2000);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            //Get the date info of the week for create time off, leave and terminate
            String activeWeek = schedulePage.getActiveWeekText();
            List<String> year = schedulePage.getYearsFromCalendarMonthYearText();
            String[] items = activeWeek.split(" ");
            String fromDate = year.get(0)+ " " + items[3] + " " + items[4];

            //To avoid one issue -- the schedule cannot be generated when directly go to Team tab
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            teamPage.goToTeam();
            teamPage.searchAndSelectTeamMemberByName(tm1);
            teamPage.terminateOrDeactivateTheTeamMemberFromSpecificDate(true, fromDate);

            teamPage.goToTeam();
            teamPage.searchAndSelectTeamMemberByName(tm2);
            teamPage.terminateOrDeactivateTheTeamMemberFromSpecificDate(false, fromDate);

            Thread.sleep(3000);
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            Thread.sleep(5000);
            schedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange("08:00AM", "08:00PM");

            // Create new shift for TM1 on seven days
            schedulePage.convertAllUnAssignedShiftToOpenShift();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView(tm1);
            schedulePage.deleteTMShiftInWeekView(tm2);
            schedulePage.deleteTMShiftInWeekView("Unassigned");
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            String workRole = schedulePage.getRandomWorkRole();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            schedulePage.selectSpecificWorkDay(7);
            schedulePage.selectWorkRole(workRole);

            schedulePage.moveSliderAtCertainPoint("3pm", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("10am", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(tm1);
            schedulePage.clickOnOfferOrAssignBtn();

            // Create new shift for TM2 on seven days
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            schedulePage.selectSpecificWorkDay(7);
            schedulePage.selectWorkRole(workRole);
            schedulePage.moveSliderAtCertainPoint("4pm", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("11am", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(tm2);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();

            schedulePage.publishActiveSchedule();
            SimpleUtils.assertOnFail("The schedule fail to publish! ", schedulePage.isCurrentScheduleWeekPublished(), false);

            //Get the info of this week for copy schedule
            String firstWeekInfo = schedulePage.getActiveWeekText();
            if (firstWeekInfo.length() > 11) {
                firstWeekInfo = firstWeekInfo.trim().substring(10);
                if (firstWeekInfo.contains("-")) {
                    String[] temp = firstWeekInfo.split("-");
                    if (temp.length == 2 && temp[0].contains(" ") && temp[1].contains(" ")) {
                        firstWeekInfo = temp[0].trim().split(" ")[0] + " " + (temp[0].trim().split(" ")[1].length() == 1 ? "0" + temp[0].trim().split(" ")[1] : temp[0].trim().split(" ")[1])
                                + " - " + temp[1].trim().split(" ")[0] + " " + (temp[1].trim().split(" ")[1].length() == 1 ? "0" + temp[1].trim().split(" ")[1] : temp[1].trim().split(" ")[1]);
                    }
                }
            }

            schedulePage.navigateToNextWeek();
            isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.clickCreateScheduleBtn();
            schedulePage.editOperatingHoursWithGivingPrameters("Sunday", "08:00AM", "08:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Monday", "08:00AM", "08:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Tuesday", "08:00AM", "08:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Wednesday", "08:00AM", "08:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Thursday", "08:00AM", "08:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Friday", "08:00AM", "08:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Saturday", "08:00AM", "08:00PM");
            schedulePage.clickNextBtnOnCreateScheduleWindow();
            schedulePage.selectWhichWeekToCopyFrom(firstWeekInfo);
            schedulePage.clickOnFinishButtonOnCreateSchedulePage();


            //Check the tooltip of publish button
            String tooltip = schedulePage.getTooltipOfPublishButton();
            SimpleUtils.assertOnFail("The tooltip of publish button should display as: Please address required action(s)! But the actual tooltip is: "+ tooltip,
                    tooltip.equalsIgnoreCase("Please address required action(s)"), false);
            //Check the Unassigned shifts will display
            int unassignedShiftsCount = schedulePage.getAllShiftsOfOneTM("unassigned").size();
            SimpleUtils.assertOnFail("Unassigned shifts should display! ",unassignedShiftsCount >= 14, false);
            //Check the Action required smart card is display
            SimpleUtils.assertOnFail("Action Required smart card should be loaded! ",
                    schedulePage.isRequiredActionSmartCardLoaded(), false);

            List<String> unassignedShiftTimes = new ArrayList<>();
            List<String> openShiftTimes = new ArrayList<>();
            List<WebElement> unassignedShifts = new ArrayList<>();
            List<WebElement> openShifts = new ArrayList<>();
            List<String> complianceMessage = new ArrayList<>();
            for(int i = 0; i< 7; i++){
                unassignedShiftTimes.clear();
                openShiftTimes.clear();
                unassignedShifts.clear();
                openShifts.clear();

                unassignedShifts = schedulePage.getOneDayShiftByName(i, "unassigned");
                SimpleUtils.assertOnFail("There should has at least 2 unassigned shifts in this day! ", unassignedShifts.size()>1, false);

                for (WebElement unassignedShift: unassignedShifts) {
                    complianceMessage = schedulePage.getComplianceMessageFromInfoIconPopup(unassignedShift);
                    SimpleUtils.assertOnFail("The unassigned violation message display incorrectly in i icon popup! ",
                            complianceMessage.contains("Unassigned Shift"), false);
                    unassignedShiftTimes.add(unassignedShift.findElement(By.className("week-schedule-shift-time")).getText());
                }

                if(unassignedShiftTimes.contains("10am - 3pm") && unassignedShiftTimes.contains("11am - 4pm")) {
                    SimpleUtils.pass("The opening/closing shifts display as unassigned! ");
                } else
                    SimpleUtils.fail("The opening/closing shifts are not display as unassigned! ", false);
            }

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    private String createAndOnboardNewTM(Map<String, String> tmDetails) throws Exception {
        String onBoarded = "Onboarded";
        String active = "Active";
        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        teamPage.goToTeam();
        teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
        teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
        String firstName = teamPage.addANewTeamMemberToInvite(tmDetails);
        teamPage.goToTeam();
        teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
        teamPage.searchAndSelectTeamMemberByName(firstName);
        if(teamPage.isManualOnBoardButtonLoaded()) {
            teamPage.manualOnBoardTeamMember();
        }
        if (teamPage.isActivateButtonLoaded()) {
            teamPage.clickOnActivateButton();
            teamPage.isActivateWindowLoaded();
            teamPage.selectADateOnCalendarAndActivate();
        }
        // Verify While activating team Member, On boarded date is updating to new one and Deactivate & terminate button is enabled
        teamPage.verifyDeactivateAndTerminateEnabled();
        return firstName;
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate that if employee availability preference is set to unavailable, or the schedule has compliance violations such as overtime, the schedule should be copied as-is")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateThatIfEmployeeAvailabilityPreferenceIsSetToUnavailableOrHasViolationsTheScheduleShouldBeCopiedAsIsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
//            if (getDriver().getCurrentUrl().contains(propertyMap.get(controlEnterprice))){
//                disableCopyRestriction();
//            }
            HashMap<String, Object[][]> teamMembers = null;
            if (getDriver().getCurrentUrl().contains(propertyMap.get(controlEnterprice))){
                teamMembers = controlTeamMembers;
            } else {
                teamMembers = opTeamMembers;
            }


            String teamMember1 = teamMembers.get("TeamMember1")[0][0].toString();
            String teamMember2 = teamMembers.get("TeamMember2")[0][0].toString();
            String teamMember3 = teamMembers.get("TeamMember3")[0][0].toString();
            String teamMember1WorkRole = teamMembers.get("TeamMember1")[0][2].toString();
            String teamMember2WorkRole = teamMembers.get("TeamMember2")[0][2].toString();
            String teamMember3WorkRole = teamMembers.get("TeamMember3")[0][2].toString();

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            String workPreferencesLabel = "Work Preferences";

            for (int i=1; i< 4; i++){
                teamPage.goToTeam();
                String teamMember = "";
                switch (i){
                    case 1:
                        teamMember = teamMember1;
                        break;
                    case 2:
                        teamMember = teamMember2;
                        break;
                    case 3:
                        teamMember = teamMember3;
                        break;
                }
                if (teamPage.checkIfTMExists(teamMember)) {
                    teamPage.searchAndSelectTeamMemberByName(teamMember);
                    if(teamPage.isManualOnBoardButtonLoaded()) {
                        teamPage.manualOnBoardTeamMember();
                    }
                    if (teamPage.isActivateButtonLoaded()) {
                        teamPage.clickOnActivateButton();
                        teamPage.isActivateWindowLoaded();
                        teamPage.selectADateOnCalendarAndActivate();
                    }
                    if (teamPage.isCancelTerminateButtonLoaded()) {
                        teamPage.cancelTMTerminate();
                    }
                    if (teamPage.isCancelDeactivateButtonLoaded()) {
                        teamPage.cancelTMDeactivate();
                    }

                    profileNewUIPage.selectProfilePageSubSectionByLabel(workPreferencesLabel);
                    profileNewUIPage.approveAllPendingAvailabilityRequest();

                    profileNewUIPage.selectProfilePageSubSectionByLabel("Time Off");
                    profileNewUIPage.rejectAllTimeOff();
                    profileNewUIPage.cancelAllTimeOff();

                } else {
                    SimpleUtils.fail("The team member '"+ teamMember +"' is not exists! ", false);
                }
            }

            profileNewUIPage.selectProfilePageSubSectionByLabel(workPreferencesLabel);
            profileNewUIPage.clickNextWeek();
            profileNewUIPage.clickNextWeek();
            profileNewUIPage.clickNextWeek();
            profileNewUIPage.clickNextWeek();
            profileNewUIPage.clickAvailabilityEditButton();
            profileNewUIPage.updatePreferredOrBusyHoursToAllDay(0, "Busy");
            profileNewUIPage.saveMyAvailabilityEditMode("This week only");

            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange("05:00AM", "11:00PM");
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView(teamMember1);

            // Delete all the shifts that are assigned to the team member
            schedulePage.deleteTMShiftInWeekView(teamMember2);
            schedulePage.deleteTMShiftInWeekView(teamMember3);
            schedulePage.deleteTMShiftInWeekView("unassigned");
            schedulePage.saveSchedule();

            //Create shift for tm1 on the first day
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            schedulePage.selectSpecificWorkDay(1);
            schedulePage.selectWorkRole(teamMember1WorkRole);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(teamMember1);
            schedulePage.clickOnOfferOrAssignBtn();

            // Create new shift for TM2 and make it has overtime violation
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            schedulePage.selectSpecificWorkDay(1);
            schedulePage.selectWorkRole(teamMember2WorkRole);
            schedulePage.moveSliderAtCertainPoint("11pm", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("8am", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(teamMember2);
            schedulePage.clickOnOfferOrAssignBtn();

            //Create new shifts for TM3 and make it has clopening violation
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            schedulePage.selectSpecificWorkDay(1);
            schedulePage.selectWorkRole(teamMember3WorkRole);
            schedulePage.moveSliderAtCertainPoint("11pm", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("6pm", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(teamMember3);
            schedulePage.clickOnOfferOrAssignBtn();


            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            schedulePage.selectDaysByIndex(1,1,1);
            schedulePage.selectWorkRole(teamMember3WorkRole);
            schedulePage.moveSliderAtCertainPoint("1pm", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("7am", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(teamMember3);
            schedulePage.clickOnOfferOrAssignBtn();

            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();


            //Get the info of this week for copy schedule
            String firstWeekInfo = schedulePage.getActiveWeekText();
            if (firstWeekInfo.length() > 11) {
                firstWeekInfo = firstWeekInfo.trim().substring(10);
                if (firstWeekInfo.contains("-")) {
                    String[] temp = firstWeekInfo.split("-");
                    if (temp.length == 2 && temp[0].contains(" ") && temp[1].contains(" ")) {
                        firstWeekInfo = temp[0].trim().split(" ")[0] + " " + (temp[0].trim().split(" ")[1].length() == 1 ? "0" + temp[0].trim().split(" ")[1] : temp[0].trim().split(" ")[1])
                                + " - " + temp[1].trim().split(" ")[0] + " " + (temp[1].trim().split(" ")[1].length() == 1 ? "0" + temp[1].trim().split(" ")[1] : temp[1].trim().split(" ")[1]);
                    }
                }
            }

            schedulePage.navigateToNextWeek();
            isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.clickCreateScheduleBtn();
            schedulePage.editOperatingHoursWithGivingPrameters("Sunday", "05:00AM", "11:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Monday", "05:00AM", "11:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Tuesday", "05:00AM", "11:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Wednesday", "05:00AM", "11:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Thursday", "05:00AM", "11:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Friday", "05:00AM", "11:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Saturday", "05:00AM", "11:00PM");
            schedulePage.clickNextBtnOnCreateScheduleWindow();
            schedulePage.selectWhichWeekToCopyFrom(firstWeekInfo);
            schedulePage.clickOnFinishButtonOnCreateSchedulePage();

            List<WebElement> shifts = schedulePage.getOneDayShiftByName(0, teamMember1);
            SimpleUtils.assertOnFail("Get compliance shift failed",shifts.size()==1, false);

            shifts = schedulePage.getOneDayShiftByName(0, teamMember2);
            SimpleUtils.assertOnFail("Get compliance shift failed",shifts.size()==1, false);
            List<String> violations = schedulePage.getComplianceMessageFromInfoIconPopup(shifts.get(0));
            boolean hasOTViolation = false;
            for (String violation: violations) {
                if (violation.contains("overtime")){
                    hasOTViolation = true;
                    break;
                }
            }
            SimpleUtils.assertOnFail("Overtime compliance message display failed", hasOTViolation, false);

            shifts = schedulePage.getOneDayShiftByName(0, teamMember3);
            SimpleUtils.assertOnFail("Get compliance shift failed",shifts.size()==1, false);
            SimpleUtils.assertOnFail("Clopening compliance message display failed",
                    schedulePage.getComplianceMessageFromInfoIconPopup(shifts.get(0)).contains("Clopening"), false);

            shifts = schedulePage.getOneDayShiftByName(1, teamMember3);
            SimpleUtils.assertOnFail("Get compliance shift failed",shifts.size()==1, false);
            SimpleUtils.assertOnFail("Clopening compliance message display failed",
                    schedulePage.getComplianceMessageFromInfoIconPopup(shifts.get(0)).contains("Clopening"), false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the operating hours grid will automatically adjust to fit the full range if the copied schedule extend outside the operating hour")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheOperatingHoursGridWillAutomaticallyAdjustToFitFullRangeIfCopiedScheduleExtendOutsideTheOperatingHourAsInternalAdmin (String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
//            if (getDriver().getCurrentUrl().contains(propertyMap.get(controlEnterprice))){
//                disableCopyRestriction();
//            }
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
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            Thread.sleep(5000);
            schedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange("08:00AM", "08:00PM");
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView("unassigned");
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            //Get the info of this week for copy schedule
            String firstWeekInfo = schedulePage.getActiveWeekText();
            if (firstWeekInfo.length() > 11) {
                firstWeekInfo = firstWeekInfo.trim().substring(10);
                if (firstWeekInfo.contains("-")) {
                    String[] temp = firstWeekInfo.split("-");
                    if (temp.length == 2 && temp[0].contains(" ") && temp[1].contains(" ")) {
                        firstWeekInfo = temp[0].trim().split(" ")[0] + " " + (temp[0].trim().split(" ")[1].length() == 1 ? "0" + temp[0].trim().split(" ")[1] : temp[0].trim().split(" ")[1])
                                + " - " + temp[1].trim().split(" ")[0] + " " + (temp[1].trim().split(" ")[1].length() == 1 ? "0" + temp[1].trim().split(" ")[1] : temp[1].trim().split(" ")[1]);
                    }
                }
            }

            schedulePage.navigateToNextWeek();
            isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            Thread.sleep(5000);
            schedulePage.clickCreateScheduleBtn();
            schedulePage.editOperatingHoursWithGivingPrameters("Sunday", "11:00AM", "08:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Monday", "08:00AM", "04:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Tuesday", "08:00AM", "08:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Wednesday", "08:00AM", "08:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Thursday", "08:00AM", "08:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Friday", "08:00AM", "08:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Saturday", "08:00AM", "08:00PM");
            schedulePage.clickNextBtnOnCreateScheduleWindow();
            schedulePage.selectWhichWeekToCopyFrom(firstWeekInfo);
            schedulePage.clickOnFinishButtonOnCreateSchedulePage();

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnDayView();
            List<String> gridHeaderTimes = new ArrayList();
            for (int i = 0; i< 7; i++) {
                scheduleCommonPage.navigateDayViewWithIndex(i);
                String weekDay = schedulePage.getScheduleWeekStartDayMonthDate();
                gridHeaderTimes = schedulePage.getScheduleDayViewGridTimeDuration();
                if (weekDay.contains("Sun")) {
                    SimpleUtils.assertOnFail("The grid header time should start as 8 AM, the actual time is: " +
                            gridHeaderTimes.get(0), gridHeaderTimes.get(0).contains("8 AM"), false);

                } else if (weekDay.contains("Mon")) {
                    SimpleUtils.assertOnFail("The grid header time should end with 7 PM, the actual time is: " +
                            gridHeaderTimes.get(gridHeaderTimes.size() - 1), gridHeaderTimes.get(gridHeaderTimes.size() - 1).contains("7 PM"), false);
                } else {
                    SimpleUtils.assertOnFail("The grid header time should start as 6 AM, the actual time is: " +
                            gridHeaderTimes.get(0), gridHeaderTimes.get(0).contains("6 AM"), false);
                    SimpleUtils.assertOnFail("The grid header time should end with 10 PM, the actual time is: " +
                            gridHeaderTimes.get(gridHeaderTimes.size() - 1), gridHeaderTimes.get(gridHeaderTimes.size() - 1).contains("10 PM"), false);
                }
            }

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }
}
