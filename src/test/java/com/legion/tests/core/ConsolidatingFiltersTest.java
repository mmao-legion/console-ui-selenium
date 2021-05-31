package com.legion.tests.core;

import com.legion.pages.DashboardPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.TeamPage;
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
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            // Create schedule if it is not created
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                schedulePage.createScheduleForNonDGFlowNewUI();
            }

            schedulePage.clickOnFilterBtn();
            schedulePage.verifyShiftTypeInLeft();
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
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            // Create schedule if it is not created
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( "09:00AM", "11:00PM");

            // Edit the Schedule
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            // Delete all the shifts that are assigned to the team member
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);

            // Create new shift for TM1 on first and second day for Clopening violation
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            schedulePage.selectSpecificWorkDay(1);
            schedulePage.selectWorkRole(workRoleOfTM1);
            schedulePage.moveSliderAtCertainPoint("11pm", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("6pm", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM1);
            schedulePage.clickOnOfferOrAssignBtn();

            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            schedulePage.selectDaysByIndex(1, 1, 1);
            schedulePage.selectWorkRole(workRoleOfTM1);
            schedulePage.moveSliderAtCertainPoint("11am", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("8am", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM1);
            schedulePage.clickOnOfferOrAssignBtn();

            //Create new shift for TM on third day for meal break violation
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            schedulePage.selectDaysByIndex(2, 2, 2);
            schedulePage.selectWorkRole(workRoleOfTM1);
            schedulePage.moveSliderAtCertainPoint("4pm", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("8am", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM1);
            schedulePage.clickOnOfferOrAssignBtn();

            //Create new shift for TM on forth day for OT violation
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            schedulePage.selectDaysByIndex(3, 3, 3);
            schedulePage.selectWorkRole(workRoleOfTM1);
            schedulePage.moveSliderAtCertainPoint("8pm", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("8am", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM1);
            schedulePage.clickOnOfferOrAssignBtn();

            schedulePage.saveSchedule();

            // Edit the Schedule
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            List<WebElement> shiftsOfThirdDay = schedulePage.getOneDayShiftByName(2, firstNameOfTM1);
            schedulePage.deleteMealBreakForOneShift(shiftsOfThirdDay.get(0));
            schedulePage.saveSchedule();

            schedulePage.clickOnFilterBtn();
            int allShiftsCount = schedulePage.getShiftsCount();
            int complianceReviewCount = schedulePage.getSpecificFiltersCount("Compliance Review");
            schedulePage.selectShiftTypeFilterByText("Compliance Review");
            int complianceShiftsCount = schedulePage.getShiftsCount();
            SimpleUtils.assertOnFail("The compliance shift count display incorrectly in schedule filter dropdown list! ",
                    complianceReviewCount == complianceShiftsCount, false);

            List<WebElement> shiftsOfFirstDay = schedulePage.getOneDayShiftByName(0, firstNameOfTM1);
            List<WebElement> shiftsOfSecondDay = schedulePage.getOneDayShiftByName(1, firstNameOfTM1);
            shiftsOfThirdDay = schedulePage.getOneDayShiftByName(2, firstNameOfTM1);
            List<WebElement> shiftsOfForthDay = schedulePage.getOneDayShiftByName(3, firstNameOfTM1);

            //Check the clopening violation shifts on the first and second day
            SimpleUtils.assertOnFail("Clopening compliance message display failed",
                    schedulePage.getComplianceMessageFromInfoIconPopup(shiftsOfFirstDay.get(0)).contains("Clopening")
                            && schedulePage.getComplianceMessageFromInfoIconPopup(shiftsOfSecondDay.get(0)).contains("Clopening") , false);

            //Check the meal break violation shifts on the third day
            SimpleUtils.assertOnFail("Meal break compliance message display failed",
                    schedulePage.getComplianceMessageFromInfoIconPopup(shiftsOfThirdDay.get(0)).contains("Missed Meal"), false);

    //            //Check the OT violation shifts on the forth day. Blocked by SCH-4250
    //            SimpleUtils.assertOnFail("OT compliance message display failed",
    //                    schedulePage.getComplianceMessageFromInfoIconPopup(shiftsOfForthDay.get(0)).contains("overtime"), false);

            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            SimpleUtils.assertOnFail("Uncheck Compliance Review filter fail! ",
                    allShiftsCount == schedulePage.getShiftsCount(), false);

            //Validate Compliance Review in day view
            schedulePage.clickOnDayView();
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Compliance Review");
            for (int i=0; i< 4; i++) {
                schedulePage.navigateDayViewWithIndex(i);
                schedulePage.clickOnFilterBtn();
                complianceReviewCount = schedulePage.getSpecificFiltersCount("Compliance Review");
                complianceShiftsCount = schedulePage.getShiftsCount();
                SimpleUtils.assertOnFail("The compliance shift count display incorrectly in schedule filter dropdown list! ",
                        complianceReviewCount == complianceShiftsCount, false);

                //Check the clopening violation shifts on the first and second day
                if (i ==0 || i==1) {
                    shiftsOfFirstDay = schedulePage.getShiftsByNameOnDayView(firstNameOfTM1);
                    SimpleUtils.assertOnFail("Clopening compliance message display failed",
                            schedulePage.getComplianceMessageFromInfoIconPopup(shiftsOfFirstDay.get(0)).contains("Clopening"), false);
                }

                //Check the meal break violation shifts on the third day
                if (i==2) {
                    shiftsOfThirdDay = schedulePage.getShiftsByNameOnDayView(firstNameOfTM1);
                    SimpleUtils.assertOnFail("Meal break compliance message display failed",
                            schedulePage.getComplianceMessageFromInfoIconPopup(shiftsOfThirdDay.get(0)).contains("Missed Meal"), false);
                }

                //Check the OT violation shifts on the forth day. Blocked by SCH-4250
//                if (i==3) {
//                      shiftsOfForthDay = schedulePage.getShiftsByNameOnDayView(firstNameOfTM1);
//                    SimpleUtils.assertOnFail("OT compliance message display failed",
//                            schedulePage.getComplianceMessageFromInfoIconPopup(shiftsOfForthDay.get(0)).contains("overtime"), false);
//                }
            }

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
