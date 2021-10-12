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

import static com.legion.utils.MyThreadLocal.*;

public class ActivityTest extends TestBase {

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

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Prepare the data for swap")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void prepareTheSwapShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            swapCoverNames = new ArrayList<>();
            swapCoverCredentials = getSwapCoverUserCredentials(location);
            for (Map.Entry<String, Object[][]> entry : swapCoverCredentials.entrySet()) {
                swapCoverNames.add(entry.getKey());
            }
            workRoleName = String.valueOf(swapCoverCredentials.get(swapCoverNames.get(0))[0][3]);

            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            // Deleting the existing shifts for swap team members
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.deleteTMShiftInWeekView(swapCoverNames.get(0));
            shiftOperatePage.deleteTMShiftInWeekView(swapCoverNames.get(1));
            scheduleMainPage.saveSchedule();
            shiftOperatePage.convertAllUnAssignedShiftToOpenShift();
            // Add the new shifts for swap team members
            Thread.sleep(5000);
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.addNewShiftsByNames(swapCoverNames, workRoleName);
            scheduleMainPage.saveSchedule();
            createSchedulePage.publishActiveSchedule();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the content of Shift Swap activity when TM request to swap the shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOfShiftSwapActivityAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        prepareTheSwapShiftsAsInternalAdmin(browser, username, password, location);
        SimpleUtils.report("Need to set 'Is approval by Manager required when an employee claims a shift swap or cover request?' to 'Always' First!");
        ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
        MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
        SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
        controlsPage.gotoControlsPage();
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        SimpleUtils.assertOnFail("Controls Page not loaded Successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
        controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
        SimpleUtils.assertOnFail("Schedule Collaboration Page not loaded Successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
        String option = "Always";
        controlsNewUIPage.updateSwapAndCoverRequestIsApprovalRequired(option);

        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();

        Object[][] credential = swapCoverCredentials.get(swapCoverNames.get(0));
        loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                , String.valueOf(credential[0][2]));
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String requestUserName = profileNewUIPage.getNickNameFromProfile();
        if (dashboardPage.isSwitchToEmployeeViewPresent()) {
            dashboardPage.clickOnSwitchToEmployeeView();
        }
        SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
        scheduleCommonPage.navigateToNextWeek();
        scheduleCommonPage.navigateToNextWeek();

        // For Swap Feature
        List<String> swapCoverRequsts = new ArrayList<>(Arrays.asList("Request to Swap Shift", "Request to Cover Shift"));
        int index = mySchedulePage.verifyClickOnAnyShift();
        String request = "Request to Swap Shift";
        String title = "Find Shifts to Swap";
        mySchedulePage.clickTheShiftRequestByName(request);
        SimpleUtils.assertOnFail(title + " page not loaded Successfully!", mySchedulePage.isPopupWindowLoaded(title), true);
        mySchedulePage.verifyComparableShiftsAreLoaded();
        mySchedulePage.verifySelectMultipleSwapShifts();
        // Validate the Submit button feature
        mySchedulePage.verifyClickOnNextButtonOnSwap();
        title = "Submit Swap Request";
        SimpleUtils.assertOnFail(title + " page not loaded Successfully!", mySchedulePage.isPopupWindowLoaded(title), false);
        mySchedulePage.verifyClickOnSubmitButton();
        // Validate the disappearence of Request to Swap and Request to Cover option
        mySchedulePage.clickOnShiftByIndex(index);
        if (!mySchedulePage.verifyShiftRequestButtonOnPopup(swapCoverRequsts)) {
            SimpleUtils.pass("Request to Swap and Request to Cover options are disappear");
        }else {
            SimpleUtils.fail("Request to Swap and Request to Cover options are still shown!", false);
        }

        loginPage.logOut();
        credential = swapCoverCredentials.get(swapCoverNames.get(1));
        loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                , String.valueOf(credential[0][2]));
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        String respondUserName = profileNewUIPage.getNickNameFromProfile();
        if (dashboardPage.isSwitchToEmployeeViewPresent()) {
            dashboardPage.clickOnSwitchToEmployeeView();
        }
        dashboardPage.goToTodayForNewUI();
        scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
        scheduleCommonPage.navigateToNextWeek();
        scheduleCommonPage.navigateToNextWeek();

        // Validate that swap request smartcard is available to recipient team member
        String smartCard = "SWAP REQUESTS";
        smartCardPage.isSmartCardAvailableByLabel(smartCard);
        // Validate the availability of all swap request shifts in schedule table
        String linkName = "View All";
        smartCardPage.clickLinkOnSmartCardByName(linkName);
        mySchedulePage.verifySwapRequestShiftsLoaded();
        // Validate that recipient can claim the swap request shift.
        mySchedulePage.verifyClickAcceptSwapButton();

        loginPage.logOut();

        // Login as Store Manager
        loginAsDifferentRole(AccessRoles.StoreManager.getValue());
        dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

        // Verify Activity Icon is loaded
        String actionLabel = "requested";
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, respondUserName, actionLabel, true, location);
        activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftSwap.getValue(), indexOfActivityType.ShiftSwap.name());
        activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, respondUserName, actionLabel, false, location);
        activityPage.approveOrRejectShiftSwapRequestOnActivity(requestUserName, respondUserName, approveRejectAction.Approve.getValue());
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the content of Shift Swap activity when TM automatic to request to swap the shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOfShiftSwapActivityWithoutApprovalAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            prepareTheSwapShiftsAsInternalAdmin(browser, username, password, location);
            SimpleUtils.report("Need to set 'Is approval by Manager required when an employee claims a shift swap or cover request?' to 'Never' First!");
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            controlsPage.gotoControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            SimpleUtils.assertOnFail("Controls Page not loaded Successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
            SimpleUtils.assertOnFail("Schedule Collaboration Page not loaded Successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
            String option = "Never";
            controlsNewUIPage.updateSwapAndCoverRequestIsApprovalRequired(option);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            Object[][] credential = swapCoverCredentials.get(swapCoverNames.get(0));
            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                    , String.valueOf(credential[0][2]));
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String requestUserName = profileNewUIPage.getNickNameFromProfile();
            if (dashboardPage.isSwitchToEmployeeViewPresent()) {
                dashboardPage.clickOnSwitchToEmployeeView();
            }
            SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            // For Swap Feature
            List<String> swapCoverRequsts = new ArrayList<>(Arrays.asList("Request to Swap Shift", "Request to Cover Shift"));
            int index = mySchedulePage.verifyClickOnAnyShift();
            String request = "Request to Swap Shift";
            String title = "Find Shifts to Swap";
            mySchedulePage.clickTheShiftRequestByName(request);
            SimpleUtils.assertOnFail(title + " page not loaded Successfully!", mySchedulePage.isPopupWindowLoaded(title), true);
            mySchedulePage.verifyComparableShiftsAreLoaded();
            mySchedulePage.verifySelectMultipleSwapShifts();
            // Validate the Submit button feature
            mySchedulePage.verifyClickOnNextButtonOnSwap();
            title = "Submit Swap Request";
            SimpleUtils.assertOnFail(title + " page not loaded Successfully!", mySchedulePage.isPopupWindowLoaded(title), false);
            mySchedulePage.verifyClickOnSubmitButton();
            // Validate the disappearence of Request to Swap and Request to Cover option
            mySchedulePage.clickOnShiftByIndex(index);
            if (!mySchedulePage.verifyShiftRequestButtonOnPopup(swapCoverRequsts)) {
                SimpleUtils.pass("Request to Swap and Request to Cover options are disappear");
            } else {
                SimpleUtils.fail("Request to Swap and Request to Cover options are still shown!", false);
            }
            loginPage.logOut();


            credential = swapCoverCredentials.get(swapCoverNames.get(1));
            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                    , String.valueOf(credential[0][2]));
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            String respondUserName = profileNewUIPage.getNickNameFromProfile();
            if (dashboardPage.isSwitchToEmployeeViewPresent()) {
                dashboardPage.clickOnSwitchToEmployeeView();
            }
            dashboardPage.goToTodayForNewUI();
            scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            // Validate that swap request smartcard is available to recipient team member
            String smartCard = "SWAP REQUESTS";
            smartCardPage.isSmartCardAvailableByLabel(smartCard);
            // Validate the availability of all swap request shifts in schedule table
            String linkName = "View All";
            smartCardPage.clickLinkOnSmartCardByName(linkName);
            mySchedulePage.verifySwapRequestShiftsLoaded();
            // Validate that recipient can claim the swap request shift.
            mySchedulePage.verifyClickAcceptSwapButton();

            loginPage.logOut();

            // Login as Store Manager
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Verify Activity Icon is loaded
            String actionLabel = "agreed";
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, respondUserName, actionLabel, true, location);
            activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftSwap.getValue(), indexOfActivityType.ShiftSwap.name());
            activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, respondUserName, actionLabel, false, location);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the functioning of Approve button on pending Approval for swap the shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheFunctionOfShiftSwapActivityAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            prepareTheSwapShiftsAsInternalAdmin(browser, username, password, location);
            SimpleUtils.report("Need to set 'Is approval by Manager required when an employee claims a shift swap or cover request?' to 'Always' First!");
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            controlsPage.gotoControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            SimpleUtils.assertOnFail("Controls Page not loaded Successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
            SimpleUtils.assertOnFail("Schedule Collaboration Page not loaded Successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
            String option = "Always";
            controlsNewUIPage.updateSwapAndCoverRequestIsApprovalRequired(option);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            Object[][] credential = swapCoverCredentials.get(swapCoverNames.get(0));
            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                    , String.valueOf(credential[0][2]));
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String requestUserName = profileNewUIPage.getNickNameFromProfile();
            if (dashboardPage.isSwitchToEmployeeViewPresent()) {
                dashboardPage.clickOnSwitchToEmployeeView();
            }
            SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            // For Swap Feature
            List<String> swapCoverRequsts = new ArrayList<>(Arrays.asList("Request to Swap Shift", "Request to Cover Shift"));
            int index = mySchedulePage.verifyClickOnAnyShift();
            String request = "Request to Swap Shift";
            String title = "Find Shifts to Swap";
            mySchedulePage.clickTheShiftRequestByName(request);
            SimpleUtils.assertOnFail(title + " page not loaded Successfully!", mySchedulePage.isPopupWindowLoaded(title), true);
            mySchedulePage.verifyComparableShiftsAreLoaded();
            mySchedulePage.verifySelectMultipleSwapShifts();
            // Validate the Submit button feature
            mySchedulePage.verifyClickOnNextButtonOnSwap();
            title = "Submit Swap Request";
            SimpleUtils.assertOnFail(title + " page not loaded Successfully!", mySchedulePage.isPopupWindowLoaded(title), false);
            mySchedulePage.verifyClickOnSubmitButton();
            // Validate the disappearence of Request to Swap and Request to Cover option
            mySchedulePage.clickOnShiftByIndex(index);
            if (!mySchedulePage.verifyShiftRequestButtonOnPopup(swapCoverRequsts)) {
                SimpleUtils.pass("Request to Swap and Request to Cover options are disappear");
            } else {
                SimpleUtils.fail("Request to Swap and Request to Cover options are still shown!", false);
            }

            loginPage.logOut();

            credential = swapCoverCredentials.get(swapCoverNames.get(1));
            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                    , String.valueOf(credential[0][2]));
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            String respondUserName = profileNewUIPage.getNickNameFromProfile();
            if (dashboardPage.isSwitchToEmployeeViewPresent()) {
                dashboardPage.clickOnSwitchToEmployeeView();
            }
            dashboardPage.goToTodayForNewUI();
            scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            // Validate that swap request smartcard is available to recipient team member
            String smartCard = "SWAP REQUESTS";
            smartCardPage.isSmartCardAvailableByLabel(smartCard);
            // Validate the availability of all swap request shifts in schedule table
            String linkName = "View All";
            smartCardPage.clickLinkOnSmartCardByName(linkName);
            mySchedulePage.verifySwapRequestShiftsLoaded();
            // Validate that recipient can claim the swap request shift.
            mySchedulePage.verifyClickAcceptSwapButton();

            loginPage.logOut();

            // Login as Store Manager
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Verify Activity Icon is loaded
            String actionLabel = "requested";
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, respondUserName, actionLabel, true, location);
            activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftSwap.getValue(), indexOfActivityType.ShiftSwap.name());
            activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, respondUserName, actionLabel, false, location);
            List<String> swapData = activityPage.getShiftSwapDataFromActivity(requestUserName, respondUserName);
            activityPage.approveOrRejectShiftSwapRequestOnActivity(requestUserName, respondUserName, approveRejectAction.Approve.getValue());
            activityPage.closeActivityWindow();

            // Go to Schedule page to check whether the shifts are swapped
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            mySchedulePage.verifyShiftsAreSwapped(swapData);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the functioning of Reject button on pending Reject for swap the shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOfRejectShiftSwapActivityAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            prepareTheSwapShiftsAsInternalAdmin(browser, username, password, location);
            SimpleUtils.report("Need to set 'Is approval by Manager required when an employee claims a shift swap or cover request?' to 'Always' First!");
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            controlsPage.gotoControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            SimpleUtils.assertOnFail("Controls Page not loaded Successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
            SimpleUtils.assertOnFail("Schedule Collaboration Page not loaded Successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
            String option = "Always";
            controlsNewUIPage.updateSwapAndCoverRequestIsApprovalRequired(option);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            Object[][] credential = swapCoverCredentials.get(swapCoverNames.get(0));
            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                    , String.valueOf(credential[0][2]));
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String requestUserName = profileNewUIPage.getNickNameFromProfile();
            if (dashboardPage.isSwitchToEmployeeViewPresent()) {
                dashboardPage.clickOnSwitchToEmployeeView();
            }
            SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            // For Swap Feature
            List<String> swapCoverRequsts = new ArrayList<>(Arrays.asList("Request to Swap Shift", "Request to Cover Shift"));
            int index = mySchedulePage.verifyClickOnAnyShift();
            String request = "Request to Swap Shift";
            String title = "Find Shifts to Swap";
            mySchedulePage.clickTheShiftRequestByName(request);
            SimpleUtils.assertOnFail(title + " page not loaded Successfully!", mySchedulePage.isPopupWindowLoaded(title), true);
            mySchedulePage.verifyComparableShiftsAreLoaded();
            mySchedulePage.verifySelectMultipleSwapShifts();
            // Validate the Submit button feature
            mySchedulePage.verifyClickOnNextButtonOnSwap();
            title = "Submit Swap Request";
            SimpleUtils.assertOnFail(title + " page not loaded Successfully!", mySchedulePage.isPopupWindowLoaded(title), false);
            mySchedulePage.verifyClickOnSubmitButton();
            // Validate the disappearence of Request to Swap and Request to Cover option
            mySchedulePage.clickOnShiftByIndex(index);
            if (!mySchedulePage.verifyShiftRequestButtonOnPopup(swapCoverRequsts)) {
                SimpleUtils.pass("Request to Swap and Request to Cover options are disappear");
            } else {
                SimpleUtils.fail("Request to Swap and Request to Cover options are still shown!", false);
            }

            loginPage.logOut();

            credential = swapCoverCredentials.get(swapCoverNames.get(1));
            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                    , String.valueOf(credential[0][2]));
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            String respondUserName = profileNewUIPage.getNickNameFromProfile();
            if (dashboardPage.isSwitchToEmployeeViewPresent()) {
                dashboardPage.clickOnSwitchToEmployeeView();
            }
            dashboardPage.goToTodayForNewUI();
            scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            // Validate that swap request smartcard is available to recipient team member
            String smartCard = "SWAP REQUESTS";
            smartCardPage.isSmartCardAvailableByLabel(smartCard);
            // Validate the availability of all swap request shifts in schedule table
            String linkName = "View All";
            smartCardPage.clickLinkOnSmartCardByName(linkName);
            mySchedulePage.verifySwapRequestShiftsLoaded();
            // Validate that recipient can claim the swap request shift.
            mySchedulePage.verifyClickAcceptSwapButton();

            loginPage.logOut();

            // Login as Store Manager
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Verify Activity Icon is loaded
            String actionLabel = "requested";
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, respondUserName, actionLabel, true, location);
            activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftSwap.getValue(), indexOfActivityType.ShiftSwap.name());
            activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, respondUserName, actionLabel, false, location);
            activityPage.approveOrRejectShiftSwapRequestOnActivity(requestUserName, respondUserName, approveRejectAction.Reject.getValue());
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate activity for cancel swap shift request")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyCancelToSwapActivityAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            prepareTheSwapShiftsAsInternalAdmin(browser, username, password, location);
            SimpleUtils.report("Need to set 'Is approval by Manager required when an employee claims a shift swap or cover request?' to 'Always' First!");
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            controlsPage.gotoControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            SimpleUtils.assertOnFail("Controls Page not loaded Successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
            SimpleUtils.assertOnFail("Schedule Collaboration Page not loaded Successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
            String option = "Always";
            controlsNewUIPage.updateSwapAndCoverRequestIsApprovalRequired(option);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            Object[][] credential = null;
            credential = swapCoverCredentials.get(swapCoverNames.get(0));
            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                    , String.valueOf(credential[0][2]));
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String requestUserName = profileNewUIPage.getNickNameFromProfile();
            if (dashboardPage.isSwitchToEmployeeViewPresent()) {
                dashboardPage.clickOnSwitchToEmployeeView();
            }
            SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            // For Swap Feature
            List<String> swapCoverRequsts = new ArrayList<>(Arrays.asList("Request to Swap Shift", "Request to Cover Shift"));
            int index = mySchedulePage.verifyClickOnAnyShift();
            String request = "Request to Swap Shift";
            String title = "Find Shifts to Swap";
            mySchedulePage.clickTheShiftRequestByName(request);
            SimpleUtils.assertOnFail(title + " page not loaded Successfully!", mySchedulePage.isPopupWindowLoaded(title), true);
            mySchedulePage.verifyComparableShiftsAreLoaded();
            mySchedulePage.verifySelectMultipleSwapShifts();
            // Validate the Submit button feature
            mySchedulePage.verifyClickOnNextButtonOnSwap();
            title = "Submit Swap Request";
            SimpleUtils.assertOnFail(title + " page not loaded Successfully!", mySchedulePage.isPopupWindowLoaded(title), false);
            mySchedulePage.verifyClickOnSubmitButton();
            // Validate the disappearence of Request to Swap and Request to Cover option
            mySchedulePage.clickOnShiftByIndex(index);
            if (!mySchedulePage.verifyShiftRequestButtonOnPopup(swapCoverRequsts)) {
                SimpleUtils.pass("Request to Swap and Request to Cover options are disappear");
            } else {
                SimpleUtils.fail("Request to Swap and Request to Cover options are still shown!", false);
            }

            loginPage.logOut();

            credential = swapCoverCredentials.get(swapCoverNames.get(1));
            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                    , String.valueOf(credential[0][2]));
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            String respondUserName = profileNewUIPage.getNickNameFromProfile();
            if (dashboardPage.isSwitchToEmployeeViewPresent()) {
                dashboardPage.clickOnSwitchToEmployeeView();
            }
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab("Schedule");
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            // Validate that swap request smartcard is available to recipient team member
            String smartCard = "SWAP REQUESTS";
            smartCardPage.isSmartCardAvailableByLabel(smartCard);
            // Validate the availability of all swap request shifts in schedule table
            String linkName = "View All";
            smartCardPage.clickLinkOnSmartCardByName(linkName);
            mySchedulePage.verifySwapRequestShiftsLoaded();
            // Validate that recipient can claim the swap request shift.
            mySchedulePage.verifyClickAcceptSwapButton();
            loginPage.logOut();

            //log in as the first TM to cancel the request.
            credential = swapCoverCredentials.get(swapCoverNames.get(0));
            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                    , String.valueOf(credential[0][2]));
            if (dashboardPage.isSwitchToEmployeeViewPresent()) {
                dashboardPage.clickOnSwitchToEmployeeView();
            }
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab("Schedule");
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            String requestName = "View Swap Request Status";
            mySchedulePage.clickTheShiftRequestToClaimShift(requestName, requestUserName);
            mySchedulePage.verifyClickCancelSwapOrCoverRequest();
            loginPage.logOut();

            // Login as Store Manager
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Verify Activity Icon is loaded
            String actionLabel = "requested to swap";
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, respondUserName, actionLabel, true, location);
            activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftSwap.getValue(), indexOfActivityType.ShiftSwap.name());
            activityPage.verifyCancelledMessageOnTheBottomOfTheNotification();
            SimpleUtils.assertOnFail("Shouldn't load Approval and Reject buttons!", !activityPage.isApproveRejectBtnsLoaded(0), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the content of Activities page after click on the Activities button")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOfActivityPageAsStoreManager(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.verifyFiveActivityButtonsLoaded();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate to close Activity Feed")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyToCloseActivityFeedAsStoreManager(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.verifyClickOnActivityCloseButton();
            if (activityPage.isActivityContainerPoppedUp()) {
                SimpleUtils.fail("Activity pop up container is not closed!", false);
            } else {
                SimpleUtils.pass("Activity pop up container is closed Successfully!");
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the navigation in each tab is normal on Activities page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyNavigationOfEachTabOnActivityAsStoreManager(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(indexOfActivityType.TimeOff.getValue(), indexOfActivityType.TimeOff.name());
            activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftOffer.getValue(), indexOfActivityType.ShiftOffer.name());
            activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftSwap.getValue(), indexOfActivityType.ShiftSwap.name());
            activityPage.clickActivityFilterByIndex(indexOfActivityType.ProfileUpdate.getValue(), indexOfActivityType.ProfileUpdate.name());
            activityPage.clickActivityFilterByIndex(indexOfActivityType.Schedule.getValue(), indexOfActivityType.Schedule.name());
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the notification when TM updates work preferences")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheNotificationOfWorkPreferencesAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String tmName = profileNewUIPage.getNickNameFromProfile();
            String myProfileLabel = "My Profile";
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myProfileLabel);
            SimpleUtils.assertOnFail("Profile page not loaded Successfully!", profileNewUIPage.isProfilePageLoaded(), false);
            String workPreferencesLabel = "My Work Preferences";
            profileNewUIPage.selectProfilePageSubSectionByLabel(workPreferencesLabel);
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.isWorkPreferencesPageLoaded();

            // Team Member updates the work Preferences
            teamPage.clickOnEditShiftPreference();
            SimpleUtils.assertOnFail("Edit Shift Preferences layout failed to load!", teamPage.isEditShiftPreferLayoutLoaded(), true);
            teamPage.setSliderForShiftPreferences();
            teamPage.changeShiftPreferencesStatus();
            teamPage.clickSaveShiftPrefBtn();

            // Team Member logout
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            // Login as Store Manager to check the activity
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Verify Activity Icon is loaded
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(indexOfActivityType.ProfileUpdate.getValue(), indexOfActivityType.ProfileUpdate.name());
            activityPage.verifyNewWorkPreferencesCardShowsOnActivity(tmName);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the notification when TM updates business profile")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheNotificationOfBusinessProfileAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String tmName = profileNewUIPage.getNickNameFromProfile();
            String myProfileLabel = "My Profile";
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myProfileLabel);
            SimpleUtils.assertOnFail("Profile page not loaded Successfully!", profileNewUIPage.isProfilePageLoaded(), false);
            TeamPage teamPage = pageFactory.createConsoleTeamPage();

            // Team Member updates the business profile
            String filePath = imageFilePath.get("FilePath");
            teamPage.updateBusinessProfilePicture(filePath);

            // Team Member logout
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();
            // Login as Store Manager to check the activity
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Verify Activity Icon is loaded
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.verifyNewBusinessProfileCardShowsOnActivity(tmName, true);
            activityPage.clickActivityFilterByIndex(indexOfActivityType.ProfileUpdate.getValue(), indexOfActivityType.ProfileUpdate.name());
            activityPage.verifyNewBusinessProfileCardShowsOnActivity(tmName, false);
        } catch (Exception e){
            SimpleUtils.fail(e.toString(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the activity of publish or update schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyActivityOfPublishUpdateScheduleAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());

        scheduleCommonPage.navigateToNextWeek();
        //make publish schedule activity
        boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
        if (isActiveWeekGenerated){
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        shiftOperatePage.deleteTMShiftInWeekView("Unassigned");
        scheduleMainPage.saveSchedule();
        createSchedulePage.publishActiveSchedule();
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String requestUserName = profileNewUIPage.getNickNameFromProfile();
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();


        // Login as Store Manager
        loginAsDifferentRole(AccessRoles.StoreManager.getValue());
        dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
        scheduleCommonPage.navigateToNextWeek();

        // Verify Schedule publish activity are loaded

        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(indexOfActivityType.Schedule.getValue(), indexOfActivityType.Schedule.name());
        activityPage.verifyActivityOfPublishSchedule(requestUserName);
        activityPage.verifyClickOnActivityCloseButton();

        //make update schedule activity to add one open shift
        //schedulePage.clickOnDayView();
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        shiftOperatePage.deleteTMShiftInWeekView("Unassigned");
        String workRole = shiftOperatePage.getRandomWorkRole();
        // This method is used for the old UI
        //schedulePage.clickNewDayViewShiftButtonLoaded();
        newShiftPage.clickOnDayViewAddNewShiftButton();
        newShiftPage.customizeNewShiftPage();
        newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
        newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleTestKendraScott2.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
        newShiftPage.selectWorkRole(workRole);
        newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
        newShiftPage.clickOnCreateOrNextBtn();
        scheduleMainPage.saveSchedule();
        createSchedulePage.publishActiveSchedule();


        // Verify Schedule update activity are

        String requestUserNameSM = profileNewUIPage.getNickNameFromProfile();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(indexOfActivityType.Schedule.getValue(), indexOfActivityType.Schedule.name());
        activityPage.verifyActivityOfUpdateSchedule(requestUserNameSM);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate access controls on Activities page when logon with Admin/TM or SM switch to employer view")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyAccessControlsOnActivitiesPageAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();

            //Verify Activity Feed as admin
            if (!activityPage.isActivityBellIconLoaded())
                SimpleUtils.pass("Admin view have no access to see Activity Feed as expected");
            else SimpleUtils.fail("Admin view can see Activity Feed unexpectedly",false);
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            // Verify Activity Feed as Store Manager
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            if (activityPage.isActivityBellIconLoaded()) {
                SimpleUtils.pass("SM view have access to see Activity Feed successfully");
            } else {
                SimpleUtils.fail("SM view failed to see Activity Feed",true);
            }

            // Verify Activity Feed as Store Manager Employee View
            dashboardPage.clickOnProfileIconOnDashboard();
            dashboardPage.clickOnSwitchToEmployeeView();
            if (!activityPage.isActivityBellIconLoaded()) {
                SimpleUtils.pass("SM Employee view have no access to see Activity Feed successfully");
            } else {
                SimpleUtils.warn("SM Employee view still have access to see Activity Feed unexpectedly since this bug: https://legiontech.atlassian.net/browse/SF-323");
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the content when there is no notification in every activity tab")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOnActivityAsStoreManager(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyClickOnActivityIcon();
            activityPage.verifyTheContentOnActivity();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the content of shift swap activity when there is no TM request to cover/swap shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOfShiftSwapActivityAsStoreManager(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftSwap.getValue(), indexOfActivityType.ShiftSwap.name());
            activityPage.verifyTheContentOfShiftSwapActivity(location);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the content of Shift Swap activity when TM request to cover the shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyApproveCoverRequestOfShiftSwapActivityAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        // Configuration in controls :Is approval by Manager required when an employee claims a shift swap or cover request?-Always
        // TM's next week's schedule must be published before running this test case
        // Cover TM should be in the list of Cover Request Status window
        // Cover TM should be not on the schedule at the same day with requested TM and is defined in "UserCredentialsForComparableSwapShifts.json"
        try {
            prepareTheSwapShiftsAsInternalAdmin(browser, username, password, location);
            SimpleUtils.report("Need to set 'Is approval by Manager required when an employee claims a shift swap or cover request?' to 'Always' First!");
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            controlsPage.gotoControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            SimpleUtils.assertOnFail("Controls Page not loaded Successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
            SimpleUtils.assertOnFail("Schedule Collaboration Page not loaded Successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
            String option = "Always";
            controlsNewUIPage.updateSwapAndCoverRequestIsApprovalRequired(option);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            Object[][] credential = swapCoverCredentials.get(swapCoverNames.get(0));
            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                    , String.valueOf(credential[0][2]));
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String requestUserName = profileNewUIPage.getNickNameFromProfile();
            if (dashboardPage.isSwitchToEmployeeViewPresent()) {
                dashboardPage.clickOnSwitchToEmployeeView();
            }
            SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            // For Cover Feature
            List<String> swapCoverRequests = new ArrayList<>(Arrays.asList("Request to Swap Shift", "Request to Cover Shift"));
            mySchedulePage.verifyClickOnAnyShift();
            //String request = "Request to Cover Shift";
            mySchedulePage.clickTheShiftRequestByName(swapCoverRequests.get(1));
            // Validate the Submit button feature
            String title = "Submit Cover Request";
            SimpleUtils.assertOnFail(title + " page not loaded Successfully!", mySchedulePage.isPopupWindowLoaded(title), false);
            mySchedulePage.verifyClickOnSubmitButton();

            loginPage.logOut();

            credential = swapCoverCredentials.get(swapCoverNames.get(1));
            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                    , String.valueOf(credential[0][2]));
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            String coverName = profileNewUIPage.getNickNameFromProfile();
            if (dashboardPage.isSwitchToEmployeeViewPresent()) {
                dashboardPage.clickOnSwitchToEmployeeView();
            }
            dashboardPage.goToTodayForNewUI();
            scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            // Validate that smartcard is available to recipient team member
            String smartCard = "WANT MORE HOURS?";
            SimpleUtils.assertOnFail("Smart Card: " + smartCard + " not loaded Successfully!", smartCardPage.isSpecificSmartCardLoaded(smartCard), false);
            // Validate the availability of all cover request shifts in schedule table
            String linkName = "View Shifts";;
            smartCardPage.clickLinkOnSmartCardByName(linkName);
            SimpleUtils.assertOnFail("Open shifts not loaded Successfully!", scheduleShiftTablePage.areShiftsPresent(), false);
            // Validate the availability of Claim Shift Request popup
            String requestName = "View Offer";
            mySchedulePage.clickTheShiftRequestToClaimShift(requestName, requestUserName);
            // Validate the clickability of I Agree button
            mySchedulePage.verifyClickAgreeBtnOnClaimShiftOffer();

            loginPage.logOut();

            // Login as Store Manager to see
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

            // Verify Activity Icon is loaded and approve the cover shift request
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftSwap.getValue(), indexOfActivityType.ShiftSwap.name());
            activityPage.approveOrRejectShiftCoverRequestOnActivity(requestUserName, coverName, approveRejectAction.Approve.getValue(), location);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
     }

    @Automated(automated ="Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the functioning of Reject button on pending Reject for Cover the shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyRejectCoverRequestOfShiftSwapActivityAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        // Configuration in controls :Is approval by Manager required when an employee claims a shift swap or cover request?-Always
        // TM's next week's schedule must be published before running this test case
        // Cover TM should be in the list of Cover Request Status window
        // Cover TM should be not on the schedule at the same day with requested TM and is defined in "UserCredentialsForComparableSwapShifts.json"
        try {
            prepareTheSwapShiftsAsInternalAdmin(browser, username, password, location);
            SimpleUtils.report("Need to set 'Is approval by Manager required when an employee claims a shift swap or cover request?' to 'Always' First!");
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            controlsPage.gotoControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            SimpleUtils.assertOnFail("Controls Page not loaded Successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
            SimpleUtils.assertOnFail("Schedule Collaboration Page not loaded Successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
            String option = "Always";
            controlsNewUIPage.updateSwapAndCoverRequestIsApprovalRequired(option);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            Object[][] credential = swapCoverCredentials.get(swapCoverNames.get(0));
            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                    , String.valueOf(credential[0][2]));
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String requestUserName = profileNewUIPage.getNickNameFromProfile();
            if (dashboardPage.isSwitchToEmployeeViewPresent()) {
                dashboardPage.clickOnSwitchToEmployeeView();
            }
            SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            // For Cover Feature
            List<String> swapCoverRequests = new ArrayList<>(Arrays.asList("Request to Swap Shift", "Request to Cover Shift"));
            mySchedulePage.verifyClickOnAnyShift();
            String request = "Request to Cover Shift";
            mySchedulePage.clickTheShiftRequestByName(request);
            // Validate the Submit button feature
            String title = "Submit Cover Request";
            SimpleUtils.assertOnFail(title + " page not loaded Successfully!", mySchedulePage.isPopupWindowLoaded(title), false);
            mySchedulePage.verifyClickOnSubmitButton();

            loginPage.logOut();

            credential = swapCoverCredentials.get(swapCoverNames.get(1));
            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                    , String.valueOf(credential[0][2]));
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            String coverName = profileNewUIPage.getNickNameFromProfile();
            if (dashboardPage.isSwitchToEmployeeViewPresent())
                dashboardPage.clickOnSwitchToEmployeeView();
            dashboardPage.goToTodayForNewUI();
            scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            // Validate that smartcard is available to recipient team member
            String smartCard = "WANT MORE HOURS?";
            SimpleUtils.assertOnFail("Smart Card: " + smartCard + " not loaded Successfully!", smartCardPage.isSpecificSmartCardLoaded(smartCard), false);
            // Validate the availability of all cover request shifts in schedule table
            String linkName = "View Shifts";
            smartCardPage.clickLinkOnSmartCardByName(linkName);
            SimpleUtils.assertOnFail("Open shifts not loaded Successfully!", scheduleShiftTablePage.areShiftsPresent(), false);
            // Validate the availability of Claim Shift Request popup
            String requestName = "View Offer";
            mySchedulePage.clickTheShiftRequestToClaimShift(requestName, requestUserName);
            // Validate the clickability of I Agree button
            mySchedulePage.verifyClickAgreeBtnOnClaimShiftOffer();

            loginPage.logOut();

            // Login as Store Manager
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

            // Verify Activity Icon is loaded and approve the cover shift request
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftSwap.getValue(), indexOfActivityType.ShiftSwap.name());
            activityPage.approveOrRejectShiftCoverRequestOnActivity(requestUserName, coverName, approveRejectAction.Reject.getValue(), location);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
     }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the content of Shift cover activity when TM automatic to request to cover the shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyCoverRequestOfShiftSwapActivityWithNoApprovalAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        // Configuration in controls :Is approval by Manager required when an employee claims a shift swap or cover request?-Always
        // TM's next week's schedule must be published before running this test case
        // Cover TM should be in the list of Cover Request Status window
        // Cover TM should be not on the schedule at the same day with requested TM and is defined in "UserCredentialsForComparableSwapShifts.json"
        try {
            prepareTheSwapShiftsAsInternalAdmin(browser, username, password, location);
            SimpleUtils.report("Need to set 'Is approval by Manager required when an employee claims a shift swap or cover request?' to 'Always' First!");
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            controlsPage.gotoControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            SimpleUtils.assertOnFail("Controls Page not loaded Successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
            SimpleUtils.assertOnFail("Schedule Collaboration Page not loaded Successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
            String option = "Never";
            controlsNewUIPage.updateSwapAndCoverRequestIsApprovalRequired(option);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            Object[][] credential = swapCoverCredentials.get(swapCoverNames.get(0));
            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                    , String.valueOf(credential[0][2]));
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String requestUserName = profileNewUIPage.getNickNameFromProfile();
            if (dashboardPage.isSwitchToEmployeeViewPresent()) {
                dashboardPage.clickOnSwitchToEmployeeView();
            }
            SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            // For Cover Feature
            List<String> swapCoverRequests = new ArrayList<>(Arrays.asList("Request to Swap Shift", "Request to Cover Shift"));
            int index = mySchedulePage.verifyClickOnAnyShift();
            String request = "Request to Cover Shift";
            mySchedulePage.clickTheShiftRequestByName(request);
            // Validate the Submit button feature
            String title = "Submit Cover Request";
            SimpleUtils.assertOnFail(title + " page not loaded Successfully!", mySchedulePage.isPopupWindowLoaded(title), false);
            mySchedulePage.verifyClickOnSubmitButton();

            loginPage.logOut();

            credential = swapCoverCredentials.get(swapCoverNames.get(1));
            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                    , String.valueOf(credential[0][2]));
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            String coverName = profileNewUIPage.getNickNameFromProfile();
            if (dashboardPage.isSwitchToEmployeeViewPresent())
                dashboardPage.clickOnSwitchToEmployeeView();
            dashboardPage.goToTodayForNewUI();
            scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            // Validate that smartcard is available to recipient team member
            String smartCard = "WANT MORE HOURS?";
            SimpleUtils.assertOnFail("Smart Card: " + smartCard + " not loaded Successfully!", smartCardPage.isSpecificSmartCardLoaded(smartCard), false);
            // Validate the availability of all cover request shifts in schedule table
            String linkName = "View Shifts";;
            smartCardPage.clickLinkOnSmartCardByName(linkName);
            SimpleUtils.assertOnFail("Open shifts not loaded Successfully!", scheduleShiftTablePage.areShiftsPresent(), false);
            // Validate the availability of Claim Shift Request popup
            String requestName = "Claim Shift";
            mySchedulePage.clickTheShiftRequestToClaimShift(requestName, requestUserName);
            // Validate the clickability of I Agree button
            mySchedulePage.verifyClickAgreeBtnOnClaimShiftOfferWhenDontNeedApproval();
            loginPage.logOut();

            // Login as Store Manager
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

            // Verify Activity Icon is loaded and approve the cover shift request
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftSwap.getValue(), indexOfActivityType.ShiftSwap.name());
            activityPage.verifyNewShiftCoverCardShowsOnActivity(requestUserName, coverName,  location);
            SimpleUtils.assertOnFail("Shouldn't load Approval and Reject buttons!", !activityPage.isApproveRejectBtnsLoaded(0), false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate activity for TM1 cancel cover shift request")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTM1CancelCoverRequestOfShiftSwapActivityAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        // Configuration in controls :Is approval by Manager required when an employee claims a shift swap or cover request?-Always
        // TM's next week's schedule must be published before running this test case
        // Cover TM should be in the list of Cover Request Status window
        // Cover TM should be not on the schedule at the same day with requested TM and is defined in "UserCredentialsForComparableSwapShifts.json"
        try {
            prepareTheSwapShiftsAsInternalAdmin(browser, username, password, location);
            SimpleUtils.report("Need to set 'Is approval by Manager required when an employee claims a shift swap or cover request?' to 'Always' First!");
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            controlsPage.gotoControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("Controls Page not loaded Successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
            SimpleUtils.assertOnFail("Schedule Collaboration Page not loaded Successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
            String option = "Always";
            controlsNewUIPage.updateSwapAndCoverRequestIsApprovalRequired(option);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            Object[][] credential = swapCoverCredentials.get(swapCoverNames.get(0));
            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                    , String.valueOf(credential[0][2]));
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String requestUserName = profileNewUIPage.getNickNameFromProfile();
            if (dashboardPage.isSwitchToEmployeeViewPresent()) {
                dashboardPage.clickOnSwitchToEmployeeView();
            }
            SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            // For Cover Feature
            List<String> swapCoverRequests = new ArrayList<>(Arrays.asList("Request to Swap Shift", "Request to Cover Shift"));
            int index = mySchedulePage.verifyClickOnAnyShift();
            String requestName = "Request to Cover Shift";
            mySchedulePage.clickTheShiftRequestByName(requestName);
            // Validate the Submit button feature
            String title = "Submit Cover Request";
            SimpleUtils.assertOnFail(title + " page not loaded Successfully!", mySchedulePage.isPopupWindowLoaded(title), false);
            mySchedulePage.verifyClickOnSubmitButton();
            loginPage.logOut();

            credential = swapCoverCredentials.get(swapCoverNames.get(1));
            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                    , String.valueOf(credential[0][2]));
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            String coverName = profileNewUIPage.getNickNameFromProfile();
            if (dashboardPage.isSwitchToEmployeeViewPresent())
                dashboardPage.clickOnSwitchToEmployeeView();
            dashboardPage.goToTodayForNewUI();
            scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            // Validate that smartcard is available to recipient team member
            String smartCard = "WANT MORE HOURS?";
            SimpleUtils.assertOnFail("Smart Card: " + smartCard + " not loaded Successfully!", smartCardPage.isSpecificSmartCardLoaded(smartCard), false);
            // Validate the availability of all cover request shifts in schedule table
            String linkName = "View Shifts";;
            smartCardPage.clickLinkOnSmartCardByName(linkName);
            SimpleUtils.assertOnFail("Open shifts not loaded Successfully!", scheduleShiftTablePage.areShiftsPresent(), false);
            // Validate the availability of Claim Shift Request popup
            requestName = "Claim Shift";
            mySchedulePage.clickTheShiftRequestToClaimShift(requestName, requestUserName);
            // Validate the clickability of I Agree button
            mySchedulePage.verifyClickAgreeBtnOnClaimShiftOffer();
            loginPage.logOut();

            //log in as the first TM to cancel the request.
            credential = swapCoverCredentials.get(swapCoverNames.get(0));
            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                    , String.valueOf(credential[0][2]));
            if (dashboardPage.isSwitchToEmployeeViewPresent()) {
                dashboardPage.clickOnSwitchToEmployeeView();
            }
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab("Schedule");
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            requestName = "View Cover Request Status";
            mySchedulePage.clickTheShiftRequestToClaimShift(requestName, requestUserName);
            mySchedulePage.verifyClickCancelSwapOrCoverRequest();
            loginPage.logOut();

            // Login as Store Manager
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

            // Verify Activity Icon is loaded and approve the cover shift request
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftSwap.getValue(), indexOfActivityType.ShiftSwap.name());
            activityPage.verifyNewShiftCoverCardShowsOnActivity(requestUserName, coverName,  location);
            activityPage.verifyCancelledMessageOnTheBottomOfTheNotification();
            SimpleUtils.assertOnFail("Shouldn't load Approval and Reject buttons!", !activityPage.isApproveRejectBtnsLoaded(0), false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Owner(owner = "Haya")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify the notification when TM is requesting time off")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyTheNotificationForRequestTimeOffAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try {
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            controlsPage.gotoControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            SimpleUtils.assertOnFail("Controls Page not loaded Successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.clickOnSchedulingPoliciesTimeOffAdvanceBtn();
            int advancedDays = controlsNewUIPage.getDaysInAdvanceCreateTimeOff();
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            // Login as Team Member to create time off
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String requestUserName = profileNewUIPage.getNickNameFromProfile();
            String myTimeOffLabel = "My Time Off";
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myTimeOffLabel);
            profileNewUIPage.cancelAllTimeOff();
            profileNewUIPage.clickOnCreateTimeOffBtn();
            SimpleUtils.assertOnFail("New time off request window not loaded Successfully!", profileNewUIPage.isNewTimeOffWindowLoaded(), false);
            // select time off reason
            if (profileNewUIPage.isReasonLoad(timeOffReasonType.FamilyEmergency.getValue())){
                profileNewUIPage.selectTimeOffReason(timeOffReasonType.FamilyEmergency.getValue());
            } else if (profileNewUIPage.isReasonLoad(timeOffReasonType.PersonalEmergency.getValue())){
                profileNewUIPage.selectTimeOffReason(timeOffReasonType.PersonalEmergency.getValue());
            } else if (profileNewUIPage.isReasonLoad(timeOffReasonType.JuryDuty.getValue())){
                profileNewUIPage.selectTimeOffReason(timeOffReasonType.JuryDuty.getValue());
            } else if (profileNewUIPage.isReasonLoad(timeOffReasonType.Sick.getValue())){
                profileNewUIPage.selectTimeOffReason(timeOffReasonType.Sick.getValue());
            } else if (profileNewUIPage.isReasonLoad(timeOffReasonType.Vacation.getValue())){
                profileNewUIPage.selectTimeOffReason(timeOffReasonType.Vacation.getValue());
            }
            profileNewUIPage.selectStartAndEndDate(advancedDays);
            profileNewUIPage.clickOnSaveTimeOffRequestBtn();
            loginPage.logOut();

            // Login as Store Manager again to check message and reject
            String RequstTimeOff = "requested";
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            String respondUserName = profileNewUIPage.getNickNameFromProfile();
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(indexOfActivityType.TimeOff.getValue(),indexOfActivityType.TimeOff.name());
            activityPage.verifyTheNotificationForReqestTimeOff(requestUserName, getTimeOffStartTime(),getTimeOffEndTime(), RequstTimeOff);
            activityPage.approveOrRejectTTimeOffRequestOnActivity(requestUserName,respondUserName,approveRejectAction.Reject.getValue());
            activityPage.closeActivityWindow();
            loginPage.logOut();

            // Login as Team Member to create time off
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            profileNewUIPage.clickOnUserProfileImage();
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myTimeOffLabel);
            profileNewUIPage.clickOnCreateTimeOffBtn();
            SimpleUtils.assertOnFail("New time off request window not loaded Successfully!", profileNewUIPage.isNewTimeOffWindowLoaded(), false);
            //select time off reason
            if (profileNewUIPage.isReasonLoad(timeOffReasonType.FamilyEmergency.getValue())){
                profileNewUIPage.selectTimeOffReason(timeOffReasonType.FamilyEmergency.getValue());
            } else if (profileNewUIPage.isReasonLoad(timeOffReasonType.PersonalEmergency.getValue())){
                profileNewUIPage.selectTimeOffReason(timeOffReasonType.PersonalEmergency.getValue());
            } else if (profileNewUIPage.isReasonLoad(timeOffReasonType.JuryDuty.getValue())){
                profileNewUIPage.selectTimeOffReason(timeOffReasonType.JuryDuty.getValue());
            } else if (profileNewUIPage.isReasonLoad(timeOffReasonType.Sick.getValue())){
                profileNewUIPage.selectTimeOffReason(timeOffReasonType.Sick.getValue());
            } else if (profileNewUIPage.isReasonLoad(timeOffReasonType.Vacation.getValue())){
                profileNewUIPage.selectTimeOffReason(timeOffReasonType.Vacation.getValue());
            }
            profileNewUIPage.selectStartAndEndDate(advancedDays);
            profileNewUIPage.clickOnSaveTimeOffRequestBtn();
            loginPage.logOut();

            // Login as Store Manager again to check message and approve
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(indexOfActivityType.TimeOff.getValue(),indexOfActivityType.TimeOff.name());
            activityPage.verifyTheNotificationForReqestTimeOff(requestUserName, getTimeOffStartTime(),getTimeOffEndTime(), RequstTimeOff);
            activityPage.approveOrRejectTTimeOffRequestOnActivity(requestUserName,respondUserName,approveRejectAction.Approve.getValue());
            activityPage.closeActivityWindow();
            loginPage.logOut();

            // Login as Team Member to cancel time off
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            profileNewUIPage.clickOnUserProfileImage();
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myTimeOffLabel);
            profileNewUIPage.cancelAllTimeOff();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify the notification when TM cancels time off request")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyTheNotificationForCancelTimeOffAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try {
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            controlsPage.gotoControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            SimpleUtils.assertOnFail("Controls Page not loaded Successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            controlsNewUIPage.clickOnSchedulingPoliciesTimeOffAdvanceBtn();
            int advancedDays = controlsNewUIPage.getDaysInAdvanceCreateTimeOff();
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();


            // Login as Team member to create the time off request
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String requestUserName = profileNewUIPage.getNickNameFromProfile();
            String myProfileLabel = "My Profile";
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myProfileLabel);
            SimpleUtils.assertOnFail("Profile page not loaded Successfully!", profileNewUIPage.isProfilePageLoaded(), false);
            String aboutMeLabel = "About Me";
            profileNewUIPage.selectProfilePageSubSectionByLabel(aboutMeLabel);
            String myTimeOffLabel = "My Time Off";
            profileNewUIPage.selectProfilePageSubSectionByLabel(myTimeOffLabel);
            profileNewUIPage.cancelAllTimeOff();
            profileNewUIPage.clickOnCreateTimeOffBtn();
            SimpleUtils.assertOnFail("New time off request window not loaded Successfully!", profileNewUIPage.isNewTimeOffWindowLoaded(), false);
            // select time off reason
            if (profileNewUIPage.isReasonLoad(timeOffReasonType.FamilyEmergency.getValue())){
                profileNewUIPage.selectTimeOffReason(timeOffReasonType.FamilyEmergency.getValue());
            } else if (profileNewUIPage.isReasonLoad(timeOffReasonType.PersonalEmergency.getValue())){
                profileNewUIPage.selectTimeOffReason(timeOffReasonType.PersonalEmergency.getValue());
            } else if (profileNewUIPage.isReasonLoad(timeOffReasonType.JuryDuty.getValue())){
                profileNewUIPage.selectTimeOffReason(timeOffReasonType.JuryDuty.getValue());
            } else if (profileNewUIPage.isReasonLoad(timeOffReasonType.Sick.getValue())){
                profileNewUIPage.selectTimeOffReason(timeOffReasonType.Sick.getValue());
            } else if (profileNewUIPage.isReasonLoad(timeOffReasonType.Vacation.getValue())){
                profileNewUIPage.selectTimeOffReason(timeOffReasonType.Vacation.getValue());
            }
            List<String> startNEndDates = profileNewUIPage.selectStartAndEndDate(advancedDays);
            profileNewUIPage.clickOnSaveTimeOffRequestBtn();
            profileNewUIPage.cancelAllTimeOff();
            loginPage.logOut();

            // Login as Store Manager again to check message
            String RequstTimeOff = "cancelled";
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(indexOfActivityType.TimeOff.getValue(),indexOfActivityType.TimeOff.name());
            activityPage.verifyTheNotificationForReqestTimeOff(requestUserName,getTimeOffStartTime(),getTimeOffEndTime(),RequstTimeOff);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify there is no notification when TM has been activated for auto scheduling")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyNoNotificationForActivateTMAsStoreManager(String browser, String username, String password, String location) {
        try {
            // Login with Store Manager Credentials, Add a team member and activate it.
            String onBoarded = "Onboarded";
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            dashboardPage.isDashboardPageLoaded();
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
            teamPage.isProfilePageLoaded();
            String firstName = teamPage.addANewTeamMemberToInvite(newTMDetails);
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            teamPage.searchAndSelectTeamMemberByName(firstName);
            teamPage.isProfilePageLoaded();
            teamPage.manualOnBoardTeamMember();
            teamPage.verifyTheStatusOfTeamMember(onBoarded);
            teamPage.clickOnActivateButton();
            teamPage.isActivateWindowLoaded();
            teamPage.selectADateOnCalendarAndActivate();

            //to check there is no message for activating TM
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(indexOfActivityType.ProfileUpdate.getValue(),indexOfActivityType.ProfileUpdate.name());
            activityPage.verifyNoNotificationForActivateTM();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the notification when TM updates availability from a week onwards with config Not required")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyNotificationForUpdateAvailabilityRepeatForwardWithConfNOAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            // Login with Store Manager Credentials
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            // Set availability policy
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            controlsPage.gotoControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            SimpleUtils.assertOnFail("Scheduling policy page not loaded successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
            controlsNewUIPage.clickOnGlobalLocationButton();
            String isApprovalRequired = "Not required";
            controlsNewUIPage.updateAvailabilityManagementIsApprovalRequired(isApprovalRequired);
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            //Login as Team Member to change availability
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String requestUserName = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            // Login as Internal Admin again
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

            // Go to Team Roster, search the team member
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            teamPage.searchAndSelectTeamMemberByName(requestUserName);
            String workPreferencesLabel = "Work Preferences";
            profileNewUIPage.selectProfilePageSubSectionByLabel(workPreferencesLabel);
            profileNewUIPage.approveAllPendingAvailabilityRequest();
            loginPage.logOut();

            // Login as Team Member again
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            profileNewUIPage.getNickNameFromProfile();
            String myWorkPreferencesLabel = "My Work Preferences";
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myWorkPreferencesLabel);
            //Update Preferred And Busy Hours
            while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
                profileNewUIPage.clickNextWeek();
            }
            String weekInfo = profileNewUIPage.getAvailabilityWeek();
            int sliderIndex = 1;
            double hours = -0.5;//move 1 metric 0.5h left
            String leftOrRightDuration = "Right";
            String hoursType = "Preferred";
            String repeatChanges = "repeat forward";
            profileNewUIPage.updateMyAvailability(hoursType, sliderIndex, leftOrRightDuration,
                    hours, repeatChanges);
            loginPage.logOut();

            // Login as Store Manager again to check message
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(indexOfActivityType.ProfileUpdate.getValue(),indexOfActivityType.ProfileUpdate.name());
            String requestAwailabilityChangeLabel = "request";
            activityPage.verifyNotificationForUpdateAvailability(requestUserName,isApprovalRequired,requestAwailabilityChangeLabel,weekInfo,repeatChanges);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the notification when TM updates availability for a specific week with config Not required")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyNotificationForUpdateAvailability4SpecificWeekWithConfNOAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            // Login with Store Manager Credentials
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            // Set availability policy
            Thread.sleep(5000);
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            controlsPage.gotoControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            SimpleUtils.assertOnFail("Scheduling policy page not loaded successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
            controlsNewUIPage.clickOnGlobalLocationButton();
            String isApprovalRequired = "Not required";
            controlsNewUIPage.updateAvailabilityManagementIsApprovalRequired(isApprovalRequired);
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            //Login as Team Member to change availability
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String requestUserName = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            // Login as Internal Admin again
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

            // Go to Team Roster, search the team member
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            teamPage.searchAndSelectTeamMemberByName(requestUserName);
            String workPreferencesLabel = "Work Preferences";
            profileNewUIPage.selectProfilePageSubSectionByLabel(workPreferencesLabel);
            profileNewUIPage.approveAllPendingAvailabilityRequest();
            loginPage.logOut();

            // Login as Team Member again
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            profileNewUIPage.getNickNameFromProfile();
            String myWorkPreferencesLabel = "My Work Preferences";
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myWorkPreferencesLabel);
            //Update Preferred And Busy Hours
            while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
                profileNewUIPage.clickNextWeek();
            }
            String weekInfo = profileNewUIPage.getAvailabilityWeek();
            int sliderIndex = 1;
            double hours = -0.5;//move 1 metric 0.5h left
            String leftOrRightDuration = "Right"; //move the right bar
            String hoursType = "Preferred";
            String repeatChanges = "This week only";
            profileNewUIPage.updateMyAvailability(hoursType, sliderIndex, leftOrRightDuration,
                    hours, repeatChanges);
            loginPage.logOut();

            // Login as Store Manager again to check message
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(indexOfActivityType.ProfileUpdate.getValue(),indexOfActivityType.ProfileUpdate.name());
            String requestAwailabilityChangeLabel = "request";
            activityPage.verifyNotificationForUpdateAvailability(requestUserName,isApprovalRequired,requestAwailabilityChangeLabel,weekInfo,repeatChanges);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya&Lizzy")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the notification when TM requests availability for a specific week")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyNotificationForUpdateAvailability4SpecificWeekWithConfYesAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            // Login with Store Manager Credentials
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            // Set availability policy
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            controlsPage.gotoControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);

            dashboardPage.navigateToDashboard();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            controlsPage.gotoControlsPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);

            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            SimpleUtils.assertOnFail("Scheduling policy page not loaded successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
            controlsNewUIPage.clickOnGlobalLocationButton();
            String isApprovalRequired = "Required for all changes";
            controlsNewUIPage.updateAvailabilityManagementIsApprovalRequired(isApprovalRequired);
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            //Login as Team Member to change availability
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String requestUserName = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            // Login as Internal Admin again
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

            // Go to Team Roster, search the team member
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            teamPage.searchAndSelectTeamMemberByName(requestUserName);
            String workPreferencesLabel = "Work Preferences";
            profileNewUIPage.selectProfilePageSubSectionByLabel(workPreferencesLabel);
            profileNewUIPage.approveAllPendingAvailabilityRequest();
            loginPage.logOut();

            // Login as Team Member again
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            profileNewUIPage.getNickNameFromProfile();
            String myWorkPreferencesLabel = "My Work Preferences";
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myWorkPreferencesLabel);
            //Update Preferred And Busy Hours
            while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
                profileNewUIPage.clickNextWeek();
            }
            String weekInfo = profileNewUIPage.getAvailabilityWeek();
            int sliderIndex = 1;
            double hours = -0.5;//move 1 metric 0.5h left
            String leftOrRightDuration = "Right";
            String hoursType = "Preferred";
            String repeatChanges = "This week only";
            profileNewUIPage.updateMyAvailability(hoursType, sliderIndex, leftOrRightDuration,
                    hours, repeatChanges);
            loginPage.logOut();

            // Login as Store Manager again to check message
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            String respondUserName = profileNewUIPage.getNickNameFromProfile();
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyClickOnActivityIcon();
            //check and click the go to profile link
            activityPage.goToProfileLinkOnActivity();
            //check the week data
            profileNewUIPage.verifyAvailabilityWeek(weekInfo);
            //click the activity bell to view the profile update again
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(indexOfActivityType.ProfileUpdate.getValue(),indexOfActivityType.ProfileUpdate.name());
            String requestAwailabilityChangeLabel = "requested";
            activityPage.verifyNotificationForUpdateAvailability(requestUserName,isApprovalRequired,requestAwailabilityChangeLabel,weekInfo,repeatChanges);
            activityPage.approveOrRejectTTimeOffRequestOnActivity(requestUserName,respondUserName,approveRejectAction.Approve.getValue());
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya&Lizzy")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the notification when TM requests availability from a week onwards")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyNotificationForUpdateAvailabilityRepeatForwardWithConfYesAsTeamMember(String browser, String username, String password, String location) throws Exception{
        try {
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String requestUserName = profileNewUIPage.getNickNameFromProfile();
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();
            // Set availability policy

            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            String respondUserName = profileNewUIPage.getNickNameFromProfile();
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            controlsPage.gotoControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);

            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            dashboardPage.navigateToDashboard();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            controlsPage.gotoControlsPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);

            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            SimpleUtils.assertOnFail("Scheduling policy page not loaded successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);

            controlsNewUIPage.clickOnGlobalLocationButton();
            String isApprovalRequired = "Required for all changes";
            controlsNewUIPage.updateAvailabilityManagementIsApprovalRequired(isApprovalRequired);

            // Go to Team Roster, search the team member
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            teamPage.searchAndSelectTeamMemberByName(requestUserName);
            String workPreferencesLabel = "Work Preferences";
            profileNewUIPage.selectProfilePageSubSectionByLabel(workPreferencesLabel);
            profileNewUIPage.approveAllPendingAvailabilityRequest();
            loginPage.logOut();

            // Login as Team Member again
            //Login as Team Member to change availability
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            profileNewUIPage.getNickNameFromProfile();
            String myWorkPreferencesLabel = "My Work Preferences";
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myWorkPreferencesLabel);
            //Update Preferred And Busy Hours
            while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
                profileNewUIPage.clickNextWeek();
            }
            String weekInfo = profileNewUIPage.getAvailabilityWeek();
            int sliderIndex = 1;
            double hours = -0.5;//move 1 metric 0.5h left
            String leftOrRightDuration = "Right";
            String hoursType = "Preferred";
            String repeatChanges = "repeat forward";
            profileNewUIPage.updateMyAvailability(hoursType, sliderIndex, leftOrRightDuration,
                    hours, repeatChanges);
            loginPage.logOut();
            // Login as Store Manager again to check message
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyClickOnActivityIcon();
            //check and click the go to profile link
            activityPage.goToProfileLinkOnActivity();
            //check the week data
            profileNewUIPage.verifyAvailabilityWeek(weekInfo);
            //click the activity bell to view the profile update again
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(indexOfActivityType.ProfileUpdate.getValue(),indexOfActivityType.ProfileUpdate.name());
            String requestAwailabilityChangeLabel = "requested";
            activityPage.verifyNotificationForUpdateAvailability(requestUserName,isApprovalRequired,requestAwailabilityChangeLabel,weekInfo,repeatChanges);
            activityPage.approveOrRejectTTimeOffRequestOnActivity(requestUserName,respondUserName,approveRejectAction.Reject.getValue());
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate activity for claim the open shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyActivityOfClaimOpenShiftAsTeamLead(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String teamMemberName = profileNewUIPage.getNickNameFromProfile();
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());

            // 1.Checking configuration in controls
            String option = "Always";
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
            boolean isScheduleCollaboration = controlsNewUIPage.isControlsScheduleCollaborationLoaded();
            SimpleUtils.assertOnFail("Controls Page: Schedule Collaboration Section not Loaded.", isScheduleCollaboration, true);
            //String selectedOption = controlsNewUIPage.getIsApprovalByManagerRequiredWhenEmployeeClaimsOpenShiftSelectedOption();
            controlsNewUIPage.updateOpenShiftApprovedByManagerOption(option);
            // 2.admin create one manual open shift and assign to specific TM
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            //to generate schedule  if current week is not generated
            scheduleCommonPage.navigateToNextWeek();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.deleteTMShiftInWeekView("Unassigned");
            shiftOperatePage.deleteTMShiftInWeekView(teamMemberName);
            scheduleMainPage.saveSchedule();
            String workRole = shiftOperatePage.getRandomWorkRole();

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(teamMemberName);
            newShiftPage.clickOnOfferOrAssignBtn();
            scheduleMainPage.saveSchedule();
            createSchedulePage.publishActiveSchedule();
            loginPage.logOut();

            // 3.Login with the TM to claim the shift
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            dashboardPage.goToTodayForNewUI();
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            String cardName = "WANT MORE HOURS?";
            SimpleUtils.assertOnFail("Smart Card: " + cardName + " not loaded Successfully!", smartCardPage.isSpecificSmartCardLoaded(cardName), false);
            String linkName = "View Shifts";
            smartCardPage.clickLinkOnSmartCardByName(linkName);
            SimpleUtils.assertOnFail("Open shifts not loaed Successfully!", scheduleShiftTablePage.areShiftsPresent(), false);
            List<String> claimShift = new ArrayList<>(Arrays.asList("View Offer"));
            mySchedulePage.selectOneShiftIsClaimShift(claimShift);
            mySchedulePage.clickTheShiftRequestByName(claimShift.get(0));
            mySchedulePage.verifyClickAgreeBtnOnClaimShiftOffer();

            loginPage.logOut();

            // 4.Login with SM to check activity
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftOffer.getValue(), indexOfActivityType.ShiftOffer.name());
            activityPage.verifyActivityOfShiftOffer(teamMemberName,location);
            activityPage.approveOrRejectShiftOfferRequestOnActivity(teamMemberName,approveRejectAction.Approve.getValue());

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the notification message when TM claim open shift automatically")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyActivityOfClaimOpenShiftNoApprovalAsTeamLead(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String teamMemberName = profileNewUIPage.getNickNameFromProfile();
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());

            // 1.Checking configuration in controls
            String option = "Never";
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
            boolean isScheduleCollaboration = controlsNewUIPage.isControlsScheduleCollaborationLoaded();
            SimpleUtils.assertOnFail("Controls Page: Schedule Collaboration Section not Loaded.", isScheduleCollaboration, true);
            //String selectedOption = controlsNewUIPage.getIsApprovalByManagerRequiredWhenEmployeeClaimsOpenShiftSelectedOption();
            controlsNewUIPage.updateOpenShiftApprovedByManagerOption(option);
            // 2.admin create one manual open shift and assign to specific TM
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            //to generate schedule  if current week is not generated
            scheduleCommonPage.navigateToNextWeek();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.deleteTMShiftInWeekView("Unassigned");
            shiftOperatePage.deleteTMShiftInWeekView(teamMemberName);
            scheduleMainPage.saveSchedule();
            String workRole = shiftOperatePage.getRandomWorkRole();

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.selectWorkRole(scheduleWorkRoles.get(workRole));
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(teamMemberName);
            newShiftPage.clickOnOfferOrAssignBtn();
            scheduleMainPage.saveSchedule();
            createSchedulePage.publishActiveSchedule();
            loginPage.logOut();

            // 3.Login with the TM to claim the shift
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            dashboardPage.goToTodayForNewUI();
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            String cardName = "WANT MORE HOURS?";
            SimpleUtils.assertOnFail("Smart Card: " + cardName + " not loaded Successfully!", smartCardPage.isSpecificSmartCardLoaded(cardName), false);
            String linkName = "View Shifts";
            smartCardPage.clickLinkOnSmartCardByName(linkName);
            SimpleUtils.assertOnFail("Open shifts not loaed Successfully!", scheduleShiftTablePage.areShiftsPresent(), false);
            List<String> claimShift = new ArrayList<>(Arrays.asList("Claim Shift"));
            mySchedulePage.selectOneShiftIsClaimShift(claimShift);
            mySchedulePage.clickTheShiftRequestByName(claimShift.get(0));
            mySchedulePage.verifyClickAgreeBtnOnClaimShiftOfferWhenDontNeedApproval();
            loginPage.logOut();

            // 4.Login with SM to check activity
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftOffer.getValue(), indexOfActivityType.ShiftOffer.name());
            activityPage.verifyActivityOfShiftOffer(teamMemberName,location);
            SimpleUtils.assertOnFail("Shouldn't load Approval and Reject buttons!", !activityPage.isApproveRejectBtnsLoaded(0), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}