package com.legion.tests.core;

import com.legion.pages.*;
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

public class ScheduleCopyImprovementTest extends TestBase {

    private static HashMap<String, String> scheduleWorkRoles = JsonUtil.getPropertiesFromJsonFile("src/test/resources/WorkRoleOptions.json");
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
    @Enterprise(name = "CinemarkWkdy_Enterprise")
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
    @TestName(description = "Validate the unassigned shifts when 'Automatically convert unassigned shifts to open shifts when generating the schedule?' set as Yes, all unassigned shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateShiftsWithConvertToOpenShiftsWhenGeneratingScheduleSettingAsAllUnassignedShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String option = "Yes, all unassigned shifts";
            changeConvertToOpenShiftsSettings(option);
            if(getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))){
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
    @TestName(description = "Validate the unassigned shifts convert to open shifts when copying schedule setting set as Yes, all unassigned shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateShiftsWithConvertToOpenShiftsWhenCopyingScheduleSettingAsAllUnassignedShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String option = "Yes, all unassigned shifts";
            changeConvertToOpenShiftsSettings(option);
            validateShiftsWithConvertToOpenShiftsWhenCopyingScheduleSetting(true, option, false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the unassigned shifts convert to open shifts when generating schedule setting set as No, keep as unassigned")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateShiftsWithConvertToOpenShiftsWhenGeneratingScheduleSettingAsKeepAsUnassignedAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String option = "No, keep as unassigned";
            changeConvertToOpenShiftsSettings(option);
            if(getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))){
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
    @TestName(description = "Validate the unassigned shifts convert to open shifts when copying schedule setting set as No, keep as unassigned")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateShiftsWithConvertToOpenShiftsWhenCopyingScheduleSettingAsKeepAsUnassignedAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String option = "No, keep as unassigned";
            changeConvertToOpenShiftsSettings(option);
            validateShiftsWithConvertToOpenShiftsWhenCopyingScheduleSetting(true, option, false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the unassigned opening or closing shifts will not convert to open shifts when generating schedule setting set as Yes, except opening/closing shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateShiftsWithConvertToOpenShiftsWhenGeneratingScheduleSettingAsExceptOpeningClosingShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String option = "Yes, except opening/closing shifts";
            changeConvertToOpenShiftsSettings(option);
            if(getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))){
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
    @TestName(description = "Validate the unassigned opening or closing shifts will not convert to open shifts when copying schedule setting set as Yes, except opening/closing shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateShiftsWithConvertToOpenShiftsWhenCopyingScheduleSettingAsExceptOpeningClosingShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String option = "Yes, except opening/closing shifts";
            changeConvertToOpenShiftsSettings(option);
            validateShiftsWithConvertToOpenShiftsWhenCopyingScheduleSetting(true, option, false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }



    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the schedule with both Uassigned and OOH shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheScheduleWithBothUnassignedAndOOOHShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String option = "No, keep as unassigned";
            changeConvertToOpenShiftsSettings(option);
            validateShiftsWithConvertToOpenShiftsWhenCopyingScheduleSetting(true, option, true);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    private void validateShiftsWithConvertToOpenShiftsWhenCopyingScheduleSetting(boolean isCopySchedule, String option, boolean ifVerifyOOOHShifts) throws Exception {

        //Go to schedule page and create new schedule
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
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

        if(!isCopySchedule && option.equalsIgnoreCase("Yes, except opening/closing shifts")) {
            schedulePage.clickCreateScheduleBtn();
            schedulePage.editOperatingHoursWithGivingPrameters("Sunday", "10:00AM", "9:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Monday", "11:00AM", "9:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Tuesday", "7:00AM", "1:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Wednesday", "7:00AM", "3:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Thursday", "8:00AM", "8:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Friday", "8:00AM", "8:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Saturday", "8:00AM", "8:00PM");
            schedulePage.clickNextBtnOnCreateScheduleWindow();
            schedulePage.selectWhichWeekToCopyFrom("SUGGESTED");
            schedulePage.clickOnFinishButtonOnCreateSchedulePage();
        } else {
            schedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange("8:00AM", "8:00PM");
        }

        // For copy schedule, select one TM -> create time off for TM -> create schedule by copy last week schedule
        if (isCopySchedule){
            //Get one random TM name from shifts
            List<String> shiftInfo = new ArrayList<>();
            while(shiftInfo.size() == 0 || shiftInfo.get(0).equalsIgnoreCase("open")
                    || shiftInfo.get(0).equalsIgnoreCase("unassigned")){
                shiftInfo = schedulePage.getTheShiftInfoByIndex(schedulePage.getRandomIndexOfShift());
            }
            String firstNameOfTM = shiftInfo.get(0);
            String workRoleOfTM = shiftInfo.get(4);
            String lastNameOfTM = shiftInfo.get(5);

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
            schedulePage.moveSliderAtCertainPoint("3", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("10", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
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
            profileNewUIPage.rejectAllTimeOff();
            profileNewUIPage.cancelAllTimeOff();
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
            schedulePage.clickCreateScheduleBtn();
            if (option.equalsIgnoreCase("Yes, except opening/closing shifts")) {
                schedulePage.editOperatingHoursWithGivingPrameters("Sunday", "10:00AM", "9:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Monday", "11:00AM", "9:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Tuesday", "7:00AM", "1:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Wednesday", "7:00AM", "3:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Thursday", "8:00AM", "8:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Friday", "8:00AM", "8:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Saturday", "8:00AM", "8:00PM");
            }
            if (ifVerifyOOOHShifts) {
                schedulePage.editOperatingHoursWithGivingPrameters("Sunday", "8:00AM", "5:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Monday", "8:00AM", "5:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Tuesday", "8:00AM", "5:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Wednesday", "8:00AM", "500PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Thursday", "8:00AM", "5:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Friday", "8:00AM", "5:00PM");
                schedulePage.editOperatingHoursWithGivingPrameters("Saturday", "8:00AM", "5:00PM");
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
            SimpleUtils.assertOnFail("Unassigned shifts should display! ",unassignedShiftsCount >= 4, false);
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
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteAllOOOHShiftInWeekView();
            schedulePage.saveSchedule();

            //Check all unassigned shifts convert to open
//            SimpleUtils.assertOnFail("The open shifts should not display in this schedule! ",
//                    schedulePage.getAllShiftsOfOneTM("open").size()==unassignedShiftsCount, false);
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
            schedulePage.clickOnDayView();
            if(isCopySchedule && option.equalsIgnoreCase("No, keep as unassigned")){
                for (int i = 0; i< 7; i++) {
                    schedulePage.navigateDayViewWithIndex(i);
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
                    schedulePage.navigateDayViewWithIndex(i);
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

            //Check there is no Unassigned shifts display
            unassignedShiftsCount = schedulePage.getAllShiftsOfOneTM("unassigned").size();
            SimpleUtils.assertOnFail("Unassigned shifts should display! ",
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
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.customizeNewShiftPage();
        if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
            schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
        } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
            schedulePage.selectWorkRole(scheduleWorkRoles.get("GENERAL MANAGER"));
        }
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


    private void changeConvertToOpenShiftsSettings(String option) throws Exception {
        if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsPage.gotoControlsPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
            SimpleUtils.assertOnFail("Scheduling collaboration page not loaded successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
            controlsNewUIPage.clickOnScheduleCollaborationOpenShiftAdvanceBtn();

            //Set 'Automatically convert unassigned shifts to open shifts when generating the schedule?' set as Yes, all unassigned shifts
            controlsNewUIPage.updateConvertUnassignedShiftsToOpenSettingOption(option);

        } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
            OpsPortalLocationsPage opsPortalLocationsPage = (OpsPortalLocationsPage) pageFactory.createOpsPortalLocationsPage();
            opsPortalLocationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.goToTemplateDetailsPage("Schedule Collaboration");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.updateConvertUnassignedShiftsToOpenWhenCreatingScheduleSettingOption(option);
            configurationPage.updateConvertUnassignedShiftsToOpenWhenCopyingScheduleSettingOption(option);
            configurationPage.publishNowTheTemplate();
            opsPortalLocationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.Console.getValue());
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the Outside of Operating Hours shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheOOOHShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            //Go to schedule page and create new schedule
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
            schedulePage.editOperatingHoursWithGivingPrameters("Sunday", "8:00AM", "4:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Monday", "8:00AM", "4:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Tuesday", "8:00AM", "4:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Wednesday", "8:00AM", "4:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Thursday", "8:00AM", "4:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Friday", "8:00AM", "4:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Saturday", "8:00AM", "4:00PM");
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
            schedulePage.clickOnDayView();

            for (int i = 0; i< 7; i++) {
                schedulePage.navigateDayViewWithIndex(i);
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
    @TestName(description = "Validate the hierarchy/priority order for all smart cards")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheHierarchyPriorityOrderForAllSmartCardsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String option = "No, keep as unassigned";
            changeConvertToOpenShiftsSettings(option);

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
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
                schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
            } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
                schedulePage.selectWorkRole(scheduleWorkRoles.get("GENERAL MANAGER"));
            }
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
    @TestName(description = "Validate one shift which has both Unassigned and OOH violation")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateOneShiftWhichHasBothUnassignedAndOOHViolationAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String option = "No, keep as unassigned";
            changeConvertToOpenShiftsSettings(option);

            //Go to schedule page and create new schedule
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
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
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }

            schedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange("8:00AM", "8:00PM");

            // For copy schedule, select one TM -> create time off for TM -> create schedule by copy last week schedule
            //Get one random TM name from shifts
            List<String> shiftInfo = new ArrayList<>();
            while(shiftInfo.size() == 0 || shiftInfo.get(0).equalsIgnoreCase("open")
                    || shiftInfo.get(0).equalsIgnoreCase("unassigned")){
                shiftInfo = schedulePage.getTheShiftInfoByIndex(schedulePage.getRandomIndexOfShift());
            }
            String firstNameOfTM = shiftInfo.get(0);
            String workRoleOfTM = shiftInfo.get(4);
            String lastNameOfTM = shiftInfo.get(5);

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
            schedulePage.moveSliderAtCertainPoint("1", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("8", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
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
            profileNewUIPage.rejectAllTimeOff();
            profileNewUIPage.cancelAllTimeOff();
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
            schedulePage.clickCreateScheduleBtn();
            schedulePage.editOperatingHoursWithGivingPrameters("Sunday", "11:00AM", "5:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Monday", "8:00AM", "8:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Tuesday", "8:00AM", "8:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Wednesday", "8:00AM", "8:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Thursday", "8:00AM", "8:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Friday", "8:00AM", "8:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Saturday", "8:00AM", "8:00PM");
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
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
                schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
            } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
                schedulePage.selectWorkRole(scheduleWorkRoles.get("GENERAL MANAGER"));
            }
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

}
