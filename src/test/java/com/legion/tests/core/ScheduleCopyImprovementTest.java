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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

            //Go to schedule page and create new schedule
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();

            //Check the Action required smart card is not display
            SimpleUtils.assertOnFail("Action Required smart card should not be loaded! ",
                    !schedulePage.isRequiredActionSmartCardLoaded(), false);
            //Check there is no unassigned shifts
            SimpleUtils.assertOnFail("The unassigned shifts should not display in this schedule! ",
                    schedulePage.getAllUnassignedShifts().size()==0, false);
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

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the unassiged shifts in new schedule if some shifts will covert to unassigned shifts in new schedule when 'Automatically convert unassigned shifts to open shifts when coping the schedule?' set as Yes, all unassigned shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateShiftsWithConvertToOpenShiftsWhenCopyingScheduleSettingAsAllUnassignedShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String option = "Yes, all unassigned shifts";
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

//            //Go to schedule page and create new schedule
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();

            //Get one random TM name from shifts
            List<String> shiftInfo = schedulePage.getTheShiftInfoByIndex(schedulePage.getRandomIndexOfShift());
            String firstNameOfTM = shiftInfo.get(0);
            String workRoleOfTM = shiftInfo.get(4);

            // Delete all the shifts that are assigned to the team member
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM);
            schedulePage.saveSchedule();

            // Create new shift for TM on seven days
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            schedulePage.selectSpecificWorkDay(7);
            schedulePage.selectWorkRole(workRoleOfTM);
            schedulePage.moveSliderAtCertainPoint("4", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("10", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM);
            schedulePage.clickOnOfferOrAssignBtn();

            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

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

            //Go to schedule page and create new schedule by copy last week schedule
            schedulePage.navigateToNextWeek();
            isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.clickCreateScheduleBtn();
            schedulePage.editOperatingHoursWithGivingPrameters("Sunday", "10:00AM", "9:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Monday", "11:00AM", "9:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Tuesday", "7:00AM", "4:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Wednesday", "7:00AM", "3:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Thursday", "10:00AM", "9:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Friday", "8:00AM", "8:00PM");
            schedulePage.editOperatingHoursWithGivingPrameters("Saturday", "8:00AM", "8:00PM");
            schedulePage.clickNextBtnOnCreateScheduleWindow();
            schedulePage.selectWhichWeekToCopyFrom(firstWeekInfo);
            schedulePage.clickOnFinishButtonOnCreateSchedulePage();

            //Check the Action required smart card is not display
            if (schedulePage.isRequiredActionSmartCardLoaded()) {
                SimpleUtils.pass("Action Required smart card should not be loaded! ");
            } else
                SimpleUtils.assertOnFail("Action Required smart card should not be loaded! ",
                    !schedulePage.getMessageFromActionRequiredSmartCard().contains("Unassigned"), false);
            //Check there is no unassigned shifts
            SimpleUtils.assertOnFail("The unassigned shifts should not display in this schedule! ",
                    schedulePage.getAllUnassignedShifts().size()==0, false);
            //Check the schedule can be saved and published
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.selectWorkRole(scheduleWorkRoles.get(workRoleOfTM));
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();

            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }


//    @Automated(automated = "Automated")
//    @Owner(owner = "Mary")
//    @Enterprise(name = "KendraScott2_Enterprise")
//    @TestName(description = "Validate the unassiged shifts when 'Automatically convert unassigned shifts to open shifts when generating the schedule?' set as No, keep as unassigned")
//    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
//    public void validateShiftsWithConvertToOpenShiftsWhenGeneratingScheduleSettingAsKeepAsUnassignedAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
//        try {
//            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
//
//            String option = "No, keep as unassigned";
//            if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
//                ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
//                ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
//                controlsPage.gotoControlsPage();
//                SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
//                controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
//                SimpleUtils.assertOnFail("Scheduling collaboration page not loaded successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
//                controlsNewUIPage.clickOnScheduleCollaborationOpenShiftAdvanceBtn();
//
//                //Set 'Automatically convert unassigned shifts to open shifts when generating the schedule?' set as Yes, all unassigned shifts
//                controlsNewUIPage.updateConvertUnassignedShiftsToOpenSettingOption(option);
//
//            } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
//                OpsPortalLocationsPage opsPortalLocationsPage = (OpsPortalLocationsPage) pageFactory.createOpsPortalLocationsPage();
//                opsPortalLocationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
//                ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
//                configurationPage.goToConfigurationPage();
//                configurationPage.goToTemplateDetailsPage("Schedule Collaboration");
//                configurationPage.clickOnEditButtonOnTemplateDetailsPage();
//                configurationPage.updateConvertUnassignedShiftsToOpenWhenCreatingScheduleSettingOption(option);
//                configurationPage.updateConvertUnassignedShiftsToOpenWhenCopyingScheduleSettingOption(option);
//                configurationPage.publishNowTheTemplate();
//                opsPortalLocationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.Console.getValue());
//            }
//
//            //Go to schedule page and create new schedule
//            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
//            schedulePage.clickOnScheduleConsoleMenuItem();
//            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
//                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
//            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
//            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
//                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
//
//            boolean isWeekGenerated = schedulePage.isWeekGenerated();
//            if (isWeekGenerated) {
//                schedulePage.unGenerateActiveScheduleScheduleWeek();
//            }
//            schedulePage.createScheduleForNonDGFlowNewUI();
//
//            //Check the Action required smart card is not display
//            SimpleUtils.assertOnFail("Action Required smart card should not be loaded! ",
//                    schedulePage.isRequiredActionSmartCardLoaded(), false);
//            //Check there is no unassigned shifts
//            SimpleUtils.assertOnFail("The unassigned shifts should not display in this schedule! ",
//                    schedulePage.getAllUnassignedShifts().size()==0, false);
//            //Check the schedule can be saved and published
//            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
//            schedulePage.clickOnDayViewAddNewShiftButton();
//            schedulePage.customizeNewShiftPage();
//            if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
//                schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
//            } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
//                schedulePage.selectWorkRole(scheduleWorkRoles.get("GENERAL MANAGER"));
//            }
//            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
//            schedulePage.clickOnCreateOrNextBtn();
//
//            schedulePage.saveSchedule();
//            schedulePage.publishActiveSchedule();
//            String firstWeekInfo = schedulePage.getActiveWeekText();
//            if (firstWeekInfo.length() > 11) {
//                firstWeekInfo = firstWeekInfo.trim().substring(10);
//                if (firstWeekInfo.contains("-")) {
//                    String[] temp = firstWeekInfo.split("-");
//                    if (temp.length == 2 && temp[0].contains(" ") && temp[1].contains(" ")) {
//                        firstWeekInfo = temp[0].trim().split(" ")[0] + " " + (temp[0].trim().split(" ")[1].length() == 1 ? "0" + temp[0].trim().split(" ")[1] : temp[0].trim().split(" ")[1])
//                                + " - " + temp[1].trim().split(" ")[0] + " " + (temp[1].trim().split(" ")[1].length() == 1 ? "0" + temp[1].trim().split(" ")[1] : temp[1].trim().split(" ")[1]);
//                    }
//                }
//            }
//
//            //Go to schedule page and create new schedule by copy last week schedule
//            schedulePage.navigateToNextWeek();
//            isWeekGenerated = schedulePage.isWeekGenerated();
//            if (isWeekGenerated) {
//                schedulePage.unGenerateActiveScheduleScheduleWeek();
//            }
//            List<String> weekDaysToClose = new ArrayList<>();
//            schedulePage.createScheduleForNonDGByWeekInfo(firstWeekInfo, weekDaysToClose, null);
//
//            //Check the Action required smart card is not display
//            SimpleUtils.assertOnFail("Action Required smart card should not be loaded! ",
//                    !schedulePage.isRequiredActionSmartCardLoaded(), false);
//            //Check there is no unassigned shifts
//            SimpleUtils.assertOnFail("The unassigned shifts should not display in this schedule! ",
//                    schedulePage.getAllUnassignedShifts().size()==0, false);
//            //Check the schedule can be saved and published
//            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
//            schedulePage.clickOnDayViewAddNewShiftButton();
//            schedulePage.customizeNewShiftPage();
//            if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
//                schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
//            } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
//                schedulePage.selectWorkRole(scheduleWorkRoles.get("GENERAL MANAGER"));
//            }
//            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
//            schedulePage.clickOnCreateOrNextBtn();
//
//            schedulePage.saveSchedule();
//            schedulePage.publishActiveSchedule();
//
//        } catch (Exception e) {
//            SimpleUtils.fail(e.getMessage(),false);
//        }
//    }
}
