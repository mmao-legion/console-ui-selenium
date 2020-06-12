package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;

public class ActivityTest extends TestBase {

    private static HashMap<String, String> scheduleWorkRoles = JsonUtil.getPropertiesFromJsonFile("src/test/resources/WorkRoleOptions.json");
    private static HashMap<String, String> propertyCustomizeMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ScheduleCustomizeNewShift.json");

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{
        this.createDriver((String)params[0],"83","Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
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

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Validate the content of Shift Swap activity when TM request to swap the shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOfShiftSwapActivityAsTeamMember(String browser, String username, String password, String location) throws Exception {
        String swapName = "";
        Object[][] credential = null;
        String fileName = "UserCredentialsForComparableSwapShifts.json";
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        for (Map.Entry<String, Object[][]> entry : userCredentials.entrySet()) {
            swapName = entry.getKey();
            credential = userCredentials.get(swapName);
        }
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String requestUserName = profileNewUIPage.getNickNameFromProfile();
        SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
        schedulePage.isSchedule();

        // For Swap Feature
        List<String> swapCoverRequsts = new ArrayList<>(Arrays.asList("Request to Swap Shift", "Request to Cover Shift"));
        int index = schedulePage.verifyClickOnAnyShift();
        String request = "Request to Swap Shift";
        String title = "Find Shifts to Swap";
        schedulePage.clickTheShiftRequestByName(request);
        SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), true);
        schedulePage.verifyComparableShiftsAreLoaded();
        schedulePage.verifySelectMultipleSwapShifts();
        // Validate the Submit button feature
        schedulePage.verifyClickOnNextButtonOnSwap();
        title = "Submit Swap Request";
        SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), false);
        schedulePage.verifyClickOnSubmitButton();
        // Validate the disappearence of Request to Swap and Request to Cover option
        schedulePage.clickOnShiftByIndex(index);
        if (!schedulePage.verifyShiftRequestButtonOnPopup(swapCoverRequsts)) {
            SimpleUtils.pass("Request to Swap and Request to Cover options are disappear");
        }else {
            SimpleUtils.fail("Request to Swap and Request to Cover options are still shown!", false);
        }


        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();
        loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                , String.valueOf(credential[0][2]));
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        dashboardPage.goToTodayForNewUI();
        schedulePage.isSchedule();
        // Validate that swap request smartcard is available to recipient team member
        String smartCard = "SWAP REQUESTS";
        schedulePage.isSmartCardAvailableByLabel(smartCard);
        // Validate the availability of all swap request shifts in schedule table
        String linkName = "View All";
        schedulePage.clickLinkOnSmartCardByName(linkName);
        schedulePage.verifySwapRequestShiftsLoaded();
        // Validate that recipient can claim the swap request shift.
        schedulePage.verifyClickAcceptSwapButton();

        loginPage.logOut();

        // Login as Store Manager
        fileName = "UsersCredentials.json";
        fileName = SimpleUtils.getEnterprise("Coffee_Enterprise")+fileName;
        userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] teamMemberCredentials = userCredentials.get("StoreManager");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                , String.valueOf(teamMemberCredentials[0][2]));
        dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

        // Verify Activity Icon is loaded
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftSwap.getValue(), indexOfActivityType.ShiftSwap.name());
        activityPage.approveOrRejectShiftSwapRequestOnActivity(requestUserName, swapName, approveRejectAction.Approve.getValue());
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Validate the activity of publish or update schedule")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyActivityOfPublishUpdateSchedule(String browser, String username, String password, String location) throws Exception {

        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());

        //make publish schedule activity
        boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
        if(!isActiveWeekGenerated){
            schedulePage.generateOrUpdateAndGenerateSchedule();
            schedulePage.publishActiveSchedule();
        }else {
            schedulePage.unGenerateActiveScheduleScheduleWeek();
            schedulePage.generateOrUpdateAndGenerateSchedule();
            schedulePage.publishActiveSchedule();
        }
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String requestUserName = profileNewUIPage.getNickNameFromProfile();
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();


        // Login as Store Manager
        String fileName = "UserCredentialsForComparableSwapShifts.json";
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        fileName = "UsersCredentials.json";
        fileName = SimpleUtils.getEnterprise("Coffee_Enterprise")+fileName;
        userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] teamMemberCredentials = userCredentials.get("StoreManager");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                , String.valueOf(teamMemberCredentials[0][2]));
        dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());


        // Verify Schedule publish activity are loaded

        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(indexOfActivityType.Schedule.getValue(), indexOfActivityType.Schedule.name());
        activityPage.verifyActivityOfPublishSchedule(requestUserName);
        activityPage.verifyClickOnActivityCloseButton();

        //make update schedule activity to add one open shift
        schedulePage.clickOnDayView();
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.clickNewDayViewShiftButtonLoaded();
        schedulePage.customizeNewShiftPage();
        schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole"));
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.saveSchedule();
        schedulePage.publishActiveSchedule();


        // Verify Schedule update activity are

        String requestUserNameSM = profileNewUIPage.getNickNameFromProfile();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(indexOfActivityType.Schedule.getValue(), indexOfActivityType.Schedule.name());
        activityPage.verifyActivityOfUpdateSchedule(requestUserNameSM);




    }


}
