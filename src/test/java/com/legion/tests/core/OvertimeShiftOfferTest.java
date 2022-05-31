package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.Constants;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;

public class OvertimeShiftOfferTest extends TestBase {

    private static HashMap<String, String> scheduleWorkRoles = JsonUtil.getPropertiesFromJsonFile("src/test/resources/WorkRoleOptions.json");
    private static HashMap<String, String> propertyCustomizeMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ScheduleCustomizeNewShift.json");
    private static HashMap<String, String> propertySearchTeamMember = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SearchTeamMember.json");
    private static Map<String, String> newTMDetails = JsonUtil.getPropertiesFromJsonFile("src/test/resources/AddANewTeamMember.json");
    private static HashMap<String, String> imageFilePath = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ProfileImageFilePath.json");

    private HashMap<String, Object[][]> swapCoverCredentials = null;
    private List<String> swapCoverNames = null;
    private String workRoleName = "";

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{
        try {
            this.createDriver((String) params[0], "83", "Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    public enum indexOfActivityType{
        TimeOff(0),
        ShiftOffer(1),
        ShiftSwap(2),
        ProfileUpdate(3),
        Schedule(4);
        private final int value;
        indexOfActivityType(final int newValue){
            value = newValue;
        }
        public int getValue(){
            return value;
        }
    }

    public enum approveRejectAction{
        Approve("APPROVE"),
        Reject("REJECT");
        private final String value;
        approveRejectAction(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    public enum timeOffReasonType{
        Vacation("VACATION"),
        JuryDuty("JURY DUTY"),
        Bereavement("BEREAVEMENT"),
        UnpaidTimeOff("UNPAID TIME OFF"),
        PersonalEmergency("PERSONAL EMERGENCY"),
        FamilyEmergency("FAMILY EMERGENCY"),
        FloatingHoliday("FLOATING HOLIDAY"),
        Sick("SICK");
        private final String value;
        timeOffReasonType(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Ting")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Should be able to claim the over time shift offer by TM")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAllowClaimOvertimeShiftOfferAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            // TODO: 1.Internal Admin and set up Claim Open Shift At Overtime Rate in Schedule Collaboration
            // TODO: 2.Internal Admin and set up shift length in Scheduling Policy Groups
            // TODO: 3.Internal Admin create OT shift and offer to TM(no available recommendation)
            // TODO: 4.TM claim OT shift(can't claim by TM with red warning)
            // TODO: 5.SM approve OT shift
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

            scheduleCommonPage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Action Required");
            shiftOperatePage.deleteTMShiftInWeekView("");
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Open");
            shiftOperatePage.deleteTMShiftInWeekView("");
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
            scheduleMainPage.clickOnFilterBtn();

            // Create and assign shift to consume the available shift hours for the TM
            String workRoleOfTM = "Retail Associate";
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectDaysByIndex(0, 1, 2);
            newShiftPage.moveSliderAtCertainPoint("9am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint("8pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.selectWorkRole(workRoleOfTM);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstNameOfTM);
            newShiftPage.clickOnCreateOrNextBtn();
            scheduleMainPage.saveSchedule();
            scheduleMainPage.publishOrRepublishSchedule();

            //create an overtime shift
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectSpecificWorkDay(4);
            newShiftPage.moveSliderAtCertainPoint("9am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint("8pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.selectWorkRole(workRoleOfTM);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.verifySelectTeamMembersOption();
            newShiftPage.clickOnOfferOrAssignBtn();
            scheduleMainPage.saveSchedule();
            scheduleMainPage.publishOrRepublishSchedule();

            // Offer overtime shift in non-edit mode
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", shiftOperatePage.isOfferTMOptionEnabled(), false);
            shiftOperatePage.clickOnOfferTMOption();
            Thread.sleep(3000);
            shiftOperatePage.switchSearchTMAndRecommendedTMsTab();
            shiftOperatePage.verifyRecommendedTableHasTM();
            shiftOperatePage.switchSearchTMAndRecommendedTMsTab();
            newShiftPage.searchTeamMemberByNameNLocation(firstNameOfTM, location);
            newShiftPage.clickOnOfferOrAssignBtn();
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            scheduleShiftTablePage.clickViewStatusBtn();
            shiftOperatePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
            shiftOperatePage.closeViewStatusContainer();
            loginPage.logOut();

            //login with TM.
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.navigateToNextWeek();
            smartCardPage.clickLinkOnSmartCardByName("View Shifts");
            SimpleUtils.assertOnFail("Didn't get open shift offer!", scheduleShiftTablePage.getShiftsCount()==1, false);

            // Claim overtime shift
            // TODO:
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}