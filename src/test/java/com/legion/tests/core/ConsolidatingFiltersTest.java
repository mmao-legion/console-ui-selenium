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
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsolidatingFiltersTest extends TestBase {

    private static HashMap<String, Object[][]> kendraScott2TeamMembers = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson("KendraScott2TeamMembers.json");
    private static HashMap<String, Object[][]> cinemarkWkdyTeamMembers = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson("CinemarkWkdyTeamMembers.json");

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception {
        try {
            this.createDriver((String) params[0], "69", "Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
        } catch (Exception e){
            SimpleUtils.fail(e.toString(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate Shift Type content in Filter without LG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyShiftTypeContentInFilterWithoutLGAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            // Create schedule if it is not created
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            schedulePage.clickOnFilterBtn();
            schedulePage.verifyShiftTypeInLeft(false);
            schedulePage.verifyShiftTypeFilters();

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate Compliance Review in week view and day view")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyComplianceReviewInWeekViewAndDayViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            HashMap<String, Object[][]> teamMembers = new HashMap<>();
            if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
                teamMembers = kendraScott2TeamMembers;
            } else {
                teamMembers = cinemarkWkdyTeamMembers;
            }

            String firstNameOfTM1 = teamMembers.get("TeamMember2")[0][0].toString();
            String workRoleOfTM1 = teamMembers.get("TeamMember2")[0][2].toString();
            teamPage.activeTMAndRejectOrApproveAllAvailabilityAndTimeOff(firstNameOfTM1);

            // Go to Schedule page, Schedule tab
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            // Create schedule if it is not created
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            Thread.sleep(3000);
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( "09:00AM", "11:00PM");

            // Edit the Schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            // Delete all the shifts that are assigned to the team member
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);

            // Create new shift for TM1 on first and second day for Clopening violation
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            schedulePage.selectSpecificWorkDay(1);
            newShiftPage.selectWorkRole(workRoleOfTM1);
            newShiftPage.moveSliderAtCertainPoint("11pm", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint("6pm", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstNameOfTM1);
            newShiftPage.clickOnOfferOrAssignBtn();

            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectDaysByIndex(1, 1, 1);
            newShiftPage.selectWorkRole(workRoleOfTM1);
            newShiftPage.moveSliderAtCertainPoint("11am", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint("8am", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstNameOfTM1);
            newShiftPage.clickOnOfferOrAssignBtn();

            //Create new shift for TM on third day for meal break violation
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectDaysByIndex(2, 2, 2);
            newShiftPage.selectWorkRole(workRoleOfTM1);
            newShiftPage.moveSliderAtCertainPoint("4pm", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint("8am", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstNameOfTM1);
            newShiftPage.clickOnOfferOrAssignBtn();

            //Create new shift for TM on forth day for OT violation
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectDaysByIndex(3, 3, 3);
            newShiftPage.selectWorkRole(workRoleOfTM1);
            newShiftPage.moveSliderAtCertainPoint("8pm", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint("8am", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstNameOfTM1);
            newShiftPage.clickOnOfferOrAssignBtn();

            scheduleMainPage.saveSchedule();

            // Edit the Schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            List<WebElement> shiftsOfThirdDay = schedulePage.getOneDayShiftByName(2, firstNameOfTM1);
            shiftOperatePage.deleteMealBreakForOneShift(shiftsOfThirdDay.get(0));
            scheduleMainPage.saveSchedule();

            schedulePage.clickOnFilterBtn();
            int allShiftsCount = schedulePage.getShiftsCount();
            int complianceReviewCount = schedulePage.getSpecificFiltersCount("Compliance Review");
            scheduleMainPage.selectShiftTypeFilterByText("Compliance Review");
            int complianceShiftsCount = schedulePage.getShiftsCount();
            SimpleUtils.assertOnFail("The compliance shift count display incorrectly in schedule filter dropdown list! ",
                    complianceReviewCount == complianceShiftsCount, false);

            List<WebElement> shiftsOfFirstDay = schedulePage.getOneDayShiftByName(0, firstNameOfTM1);
            List<WebElement> shiftsOfSecondDay = schedulePage.getOneDayShiftByName(1, firstNameOfTM1);
            shiftsOfThirdDay = schedulePage.getOneDayShiftByName(2, firstNameOfTM1);
//            List<WebElement> shiftsOfForthDay = schedulePage.getOneDayShiftByName(3, firstNameOfTM1);

            //Check the clopening violation shifts on the first and second day
            SimpleUtils.assertOnFail("Clopening compliance message display failed",
                    scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup(shiftsOfFirstDay.get(0)).contains("Clopening")
                            && scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup(shiftsOfSecondDay.get(0)).contains("Clopening") , false);

            //Check the meal break violation shifts on the third day
            SimpleUtils.assertOnFail("Meal break compliance message display failed",
                    scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup(shiftsOfThirdDay.get(0)).contains("Missed Meal"), false);

    //            //Check the OT violation shifts on the forth day. Blocked by SCH-4250
    //            SimpleUtils.assertOnFail("OT compliance message display failed",
    //                    scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup(shiftsOfForthDay.get(0)).contains("overtime"), false);

            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            SimpleUtils.assertOnFail("Uncheck Compliance Review filter fail! ",
                    allShiftsCount == schedulePage.getShiftsCount(), false);

            //Validate Compliance Review in day view
            scheduleCommonPage.clickOnDayView();
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Compliance Review");
            for (int i=0; i< 4; i++) {
                scheduleCommonPage.navigateDayViewWithIndex(i);
                schedulePage.clickOnFilterBtn();
                complianceReviewCount = schedulePage.getSpecificFiltersCount("Compliance Review");
                complianceShiftsCount = schedulePage.getShiftsCount();
                SimpleUtils.assertOnFail("The compliance shift count display incorrectly in schedule filter dropdown list! ",
                        complianceReviewCount == complianceShiftsCount, false);

                //Check the clopening violation shifts on the first and second day
                if (i ==0 || i==1) {
                    shiftsOfFirstDay = schedulePage.getShiftsByNameOnDayView(firstNameOfTM1);
                    SimpleUtils.assertOnFail("Clopening compliance message display failed",
                            scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup(shiftsOfFirstDay.get(0)).contains("Clopening"), false);
                }

                //Check the meal break violation shifts on the third day
                if (i==2) {
                    shiftsOfThirdDay = schedulePage.getShiftsByNameOnDayView(firstNameOfTM1);
                    SimpleUtils.assertOnFail("Meal break compliance message display failed",
                            scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup(shiftsOfThirdDay.get(0)).contains("Missed Meal"), false);
                }

                //Check the OT violation shifts on the forth day. Blocked by SCH-4250
//                if (i==3) {
//                      shiftsOfForthDay = schedulePage.getShiftsByNameOnDayView(firstNameOfTM1);
//                    SimpleUtils.assertOnFail("OT compliance message display failed",
//                            scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup(shiftsOfForthDay.get(0)).contains("overtime"), false);
//                }
            }

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
//    @Enterprise(name = "KendraScott2_Enterprise")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate Action Required in week view and day view")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyActionRequiredInWeekViewAndDayViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            String option = "No, keep as unassigned";
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
                configurationPage.clickOnConfigurationCrad("Schedule Collaboration");
                configurationPage.clickOnSpecifyTemplateName("Cinemark Base Template", "edit");
                configurationPage.clickOnEditButtonOnTemplateDetailsPage();
                configurationPage.updateConvertUnassignedShiftsToOpenWhenCreatingScheduleSettingOption(option);
                configurationPage.updateConvertUnassignedShiftsToOpenWhenCopyingScheduleSettingOption(option);
                configurationPage.publishNowTheTemplate();
                switchToConsoleWindow();
            }

            if(getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))){
                Thread.sleep(180000);
            }

            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            // Create schedule if it is not created
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            Thread.sleep(3000);
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( "08:00AM", "08:00PM");

            schedulePage.clickOnFilterBtn();
            int allShiftsCount = schedulePage.getShiftsCount();
            int unassignedAndOOOHShiftCount = schedulePage.getSpecificFiltersCount("Action Required");
            scheduleMainPage.selectShiftTypeFilterByText("Action Required");
            int unassignedAndOOOHShiftCountInFilter = schedulePage.getShiftsCount();
            SimpleUtils.assertOnFail("The Action Required shift count display incorrectly in schedule filter dropdown list! ",
                    unassignedAndOOOHShiftCount == unassignedAndOOOHShiftCountInFilter, false);

            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            SimpleUtils.assertOnFail("Uncheck Action Required filter fail! ",
                    allShiftsCount == schedulePage.getShiftsCount(), false);

            //Validate Action Required shifts in day view
            scheduleCommonPage.clickOnDayView();
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Action Required");
            for (int i=0; i< 7; i++) {
                scheduleCommonPage.navigateDayViewWithIndex(i);
                schedulePage.clickOnFilterBtn();
                unassignedAndOOOHShiftCount = schedulePage.getSpecificFiltersCount("Action Required");
                unassignedAndOOOHShiftCountInFilter = schedulePage.getShiftsCount();
                SimpleUtils.assertOnFail("The Action Required shift count display incorrectly in schedule filter dropdown list! ",
                        unassignedAndOOOHShiftCount == unassignedAndOOOHShiftCountInFilter, false);
            }

            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            shiftOperatePage.convertAllUnAssignedShiftToOpenShift();
            createSchedulePage.publishActiveSchedule();


            //Get the info of this week for copy schedule
            String firstWeekInfo = scheduleCommonPage.getActiveWeekText();
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

            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.clickCreateScheduleBtn();
            createSchedulePage.editOperatingHoursWithGivingPrameters("Sunday", "11:00AM", "08:00PM");
            createSchedulePage.editOperatingHoursWithGivingPrameters("Monday", "11:00AM", "08:00PM");
            createSchedulePage.editOperatingHoursWithGivingPrameters("Tuesday", "11:00AM", "08:00PM");
            createSchedulePage.editOperatingHoursWithGivingPrameters("Wednesday", "11:00AM", "08:00PM");
            createSchedulePage.editOperatingHoursWithGivingPrameters("Thursday", "11:00AM", "08:00PM");
            createSchedulePage.editOperatingHoursWithGivingPrameters("Friday", "11:00AM", "08:00PM");
            createSchedulePage.editOperatingHoursWithGivingPrameters("Saturday", "11:00AM", "08:00PM");
            createSchedulePage.clickNextBtnOnCreateScheduleWindow();
            createSchedulePage.selectWhichWeekToCopyFrom(firstWeekInfo);
            createSchedulePage.clickOnFinishButtonOnCreateSchedulePage();

            schedulePage.clickOnFilterBtn();
            allShiftsCount = schedulePage.getShiftsCount();
            unassignedAndOOOHShiftCount = schedulePage.getSpecificFiltersCount("Action Required");
            scheduleMainPage.selectShiftTypeFilterByText("Action Required");
            unassignedAndOOOHShiftCountInFilter = schedulePage.getShiftsCount();
            SimpleUtils.assertOnFail("The Action Required shift count display incorrectly in schedule filter dropdown list! ",
                    unassignedAndOOOHShiftCount == unassignedAndOOOHShiftCountInFilter, false);

            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            SimpleUtils.assertOnFail("Uncheck Action Required filter fail! ",
                    allShiftsCount == schedulePage.getShiftsCount(), false);

            //Validate Action Required shifts in day view
            scheduleCommonPage.clickOnDayView();
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Action Required");
            for (int i=0; i< 7; i++) {
                scheduleCommonPage.navigateDayViewWithIndex(i);
                schedulePage.clickOnFilterBtn();
                unassignedAndOOOHShiftCount = schedulePage.getSpecificFiltersCount("Action Required");
                unassignedAndOOOHShiftCountInFilter = schedulePage.getShiftsCount();
                SimpleUtils.assertOnFail("The Action Required shift count display incorrectly in schedule filter dropdown list! ",
                        unassignedAndOOOHShiftCount == unassignedAndOOOHShiftCountInFilter, false);
            }

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate Shift Type content in Filter with LG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyShiftTypeContentInFilterWithLGAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("District Whistler");
            locationSelectorPage.changeLocation("Lift Ops_Parent");
            // Go to Schedule page, Schedule tab
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            // Create schedule if it is not created
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            schedulePage.clickOnFilterBtn();
            schedulePage.verifyShiftTypeInLeft(true);
            schedulePage.verifyShiftTypeFilters();

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate Compliance Review with LG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyComplianceReviewWithLGAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("District Whistler");
            locationSelectorPage.changeLocation("Lift Ops_Parent");

            // Go to Schedule page, Schedule tab
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            // Create schedule if it is not created
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            // Edit the Schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            schedulePage.clickOnFilterBtn();
            int allShiftsCount = schedulePage.getShiftsCount();
            int complianceReviewCount = schedulePage.getSpecificFiltersCount("Compliance Review");
            scheduleMainPage.selectShiftTypeFilterByText("Compliance Review");
            int complianceShiftsCount = schedulePage.getShiftsCount();
            SimpleUtils.assertOnFail("The compliance shift count display incorrectly in schedule filter dropdown list! ",
                    complianceReviewCount == complianceShiftsCount, false);

            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            SimpleUtils.assertOnFail("Uncheck Compliance Review filter fail! ",
                    allShiftsCount == schedulePage.getShiftsCount(), false);

            //Validate Compliance Review in day view
            scheduleCommonPage.clickOnDayView();
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Action Required");
            for (int i=0; i< 7; i++) {
                scheduleCommonPage.navigateDayViewWithIndex(i);
                schedulePage.clickOnFilterBtn();
                complianceReviewCount = schedulePage.getSpecificFiltersCount("Compliance Review");
                complianceShiftsCount = schedulePage.getShiftsCount();
                SimpleUtils.assertOnFail("The compliance shift count display incorrectly in schedule filter dropdown list! ",
                        complianceReviewCount == complianceShiftsCount, false);
            }

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
