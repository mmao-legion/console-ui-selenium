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

import static com.legion.utils.MyThreadLocal.getTimeOffEndTime;
import static com.legion.utils.MyThreadLocal.getTimeOffStartTime;

public class ActivityTest extends TestBase {

    private static HashMap<String, String> scheduleWorkRoles = JsonUtil.getPropertiesFromJsonFile("src/test/resources/WorkRoleOptions.json");
    private static HashMap<String, String> propertyCustomizeMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ScheduleCustomizeNewShift.json");
    private static HashMap<String, String> propertySearchTeamMember = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SearchTeamMember.json");
    private static Map<String, String> newTMDetails = JsonUtil.getPropertiesFromJsonFile("src/test/resources/AddANewTeamMember.json");
    private static HashMap<String, String> imageFilePath = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ProfileImageFilePath.json");

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
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the content of Shift Swap activity when TM request to swap the shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOfShiftSwapActivityAsTeamMember(String browser, String username, String password, String location) throws Exception {
        SimpleUtils.report("Need to set 'Is approval by Manager required when an employee claims a shift swap or cover request?' to 'Always' First!");
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
        schedulePage.navigateToNextWeek();

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
        schedulePage.navigateToNextWeek();

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
        String actionLabel = "requested";
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, swapName, actionLabel, true);
        activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftSwap.getValue(), indexOfActivityType.ShiftSwap.name());
        activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, swapName, actionLabel, false);
        activityPage.approveOrRejectShiftSwapRequestOnActivity(requestUserName, swapName, approveRejectAction.Approve.getValue());
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the content of Shift Swap activity when TM automatic to request to swap the shif")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOfShiftSwapActivityWithoutApprovalAsTeamMember(String browser, String username, String password, String location) throws Exception {
        SimpleUtils.report("Need to set 'Is approval by Manager required when an employee claims a shift swap or cover request?' to 'Never' First!");
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
        schedulePage.navigateToNextWeek();

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
        schedulePage.navigateToNextWeek();

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
        String actionLabel = "agreed";
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, swapName, actionLabel, true);
        activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftSwap.getValue(), indexOfActivityType.ShiftSwap.name());
        activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, swapName, actionLabel, false);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the functioning of Approve button on pending Approval for swap the shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheFunctionOfShiftSwapActivityAsTeamMember(String browser, String username, String password, String location) throws Exception {
        SimpleUtils.report("Need to set 'Is approval by Manager required when an employee claims a shift swap or cover request?' to 'Always' First!");
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
        schedulePage.navigateToNextWeek();

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
        schedulePage.navigateToNextWeek();

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
        String actionLabel = "requested";
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, swapName, actionLabel, true);
        activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftSwap.getValue(), indexOfActivityType.ShiftSwap.name());
        activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, swapName, actionLabel, false);
        List<String> swapData = activityPage.getShiftSwapDataFromActivity(requestUserName, swapName);
        activityPage.approveOrRejectShiftSwapRequestOnActivity(requestUserName, swapName, approveRejectAction.Approve.getValue());
        activityPage.closeActivityWindow();

        // Go to Schedule page to check whether the shifts are swapped
        schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        schedulePage.isSchedule();
        schedulePage.navigateToNextWeek();
        schedulePage.verifyShiftsAreSwapped(swapData);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the functioning of Reject button on pending Reject for swap the shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOfRejectShiftSwapActivityAsTeamMember(String browser, String username, String password, String location) throws Exception {
        SimpleUtils.report("Need to set 'Is approval by Manager required when an employee claims a shift swap or cover request?' to 'Always' First!");
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
        schedulePage.navigateToNextWeek();

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
        schedulePage.navigateToNextWeek();

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
        String actionLabel = "requested";
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, swapName, actionLabel, true);
        activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftSwap.getValue(), indexOfActivityType.ShiftSwap.name());
        activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, swapName, actionLabel, false);
        activityPage.approveOrRejectShiftSwapRequestOnActivity(requestUserName, swapName, approveRejectAction.Reject.getValue());
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the content of Activities page after click on the Activities button")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOfActivityPageAsStoreManager(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.verifyFiveActivityButtonsLoaded();
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate to close Activity Feed")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyToCloseActivityFeedAsStoreManager(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.verifyClickOnActivityCloseButton();
        if (activityPage.isActivityContainerPoppedUp()) {
            SimpleUtils.fail("Activity pop up container is not closed!", false);
        }else {
            SimpleUtils.pass("Activity pop up container is closed Successfully!");
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the navigation in each tab is normal on Activities page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyNavigationOfEachTabOnActivityAsStoreManager(String browser, String username, String password, String location) throws Exception {
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
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the notification when TM updates work preferences")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheNotificationOfWorkPreferencesAsTeamMember(String browser, String username, String password, String location) throws Exception {
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
        String fileName = "UsersCredentials.json";
        fileName = SimpleUtils.getEnterprise("Coffee_Enterprise")+fileName;
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] teamMemberCredentials = userCredentials.get("StoreManager");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                , String.valueOf(teamMemberCredentials[0][2]));
        dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

        // Verify Activity Icon is loaded
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(indexOfActivityType.ProfileUpdate.getValue(), indexOfActivityType.ProfileUpdate.name());
        activityPage.verifyNewWorkPreferencesCardShowsOnActivity(tmName);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the notification when TM updates business profile")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheNotificationOfBusinessProfileAsTeamMember(String browser, String username, String password, String location) throws Exception {
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
        String fileName = "UsersCredentials.json";
        fileName = SimpleUtils.getEnterprise("Coffee_Enterprise")+fileName;
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] teamMemberCredentials = userCredentials.get("StoreManager");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                , String.valueOf(teamMemberCredentials[0][2]));
        dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

        // Verify Activity Icon is loaded
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.verifyNewBusinessProfileCardShowsOnActivity(tmName, true);
        activityPage.clickActivityFilterByIndex(indexOfActivityType.ProfileUpdate.getValue(), indexOfActivityType.ProfileUpdate.name());
        activityPage.verifyNewBusinessProfileCardShowsOnActivity(tmName, false);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "KendraScott2_Enterprise")
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
            // generateOrUpdateAndGenerateSchedule() is used for the old UI
            //schedulePage.generateOrUpdateAndGenerateSchedule();
            schedulePage.createScheduleForNonDGFlowNewUI();
            schedulePage.publishActiveSchedule();
        }else {
            schedulePage.unGenerateActiveScheduleScheduleWeek();
            // generateOrUpdateAndGenerateSchedule() is used for the old UI
            //schedulePage.generateOrUpdateAndGenerateSchedule();
            schedulePage.createScheduleForNonDGFlowNewUI();
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
        fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
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
        // This method is used for the old UI
        //schedulePage.clickNewDayViewShiftButtonLoaded();
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.customizeNewShiftPage();
        schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        schedulePage.selectWorkRole("MOD");
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

    @Automated(automated ="Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate access controls on Activities page when logon with Admin/TM or SM switch to employer view")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyAccessControlsOnActivitiesPage(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();

        //Verify Activity Feed as admin
        if (!activityPage.isActivityBellIconLoaded())
            SimpleUtils.pass("Admin view have no access to see Activity Feed as expected");
        else SimpleUtils.fail("Admin view can see Activity Feed unexpectedly",true);
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();

        // Verify Activity Feed as Store Manager
        String fileName = "UsersCredentials.json";
        fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] teamMemberCredentials = userCredentials.get("StoreManager");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                , String.valueOf(teamMemberCredentials[0][2]));
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
        if (activityPage.isActivityBellIconLoaded()) {
            SimpleUtils.pass("SM Employee view have access to see Activity Feed successfully");
        } else {
            SimpleUtils.fail("SM Employee view failed to see Activity Feed",true);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the content when there is no notification in every activity tab")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOnActivityAsStoreManager(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyClickOnActivityIcon();
        activityPage.verifyTheContentOnActivity();
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the content of shift swap activity when there is no TM request to cover/swap shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOfShiftSwapActivityAsStoreManager(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftSwap.getValue(), indexOfActivityType.ShiftSwap.name());
        activityPage.verifyTheContentOfShiftSwapActivity();
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the content of Shift Swap activity when TM request to cover the shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyApproveCoverRequestOfShiftSwapActivityAsTeamMember(String browser, String username, String password, String location) throws Exception {
        // Configuration in controls :Is approval by Manager required when an employee claims a shift swap or cover request?-Always
        // TM's next week's schedule must be published before running this test case
        // Cover TM should be in the list of Cover Request Status window
        // Cover TM should be not on the schedule at the same day with requested TM and is defined in "UserCredentialsForComparableSwapShifts.json"
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String requestUserName = profileNewUIPage.getNickNameFromProfile();
        SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
        schedulePage.isSchedule();
        if (schedulePage.isNextWeekAvaibale())
            schedulePage.selectNextWeekSchedule();
        Thread.sleep(10000);

        // For Cover Feature
        List<String> swapCoverRequests = new ArrayList<>(Arrays.asList("Request to Swap Shift", "Request to Cover Shift"));
        int index = schedulePage.verifyClickOnAnyShift();
        String request = "Request to Cover Shift";
        schedulePage.clickTheShiftRequestByName(request);
        // Validate the Submit button feature
        String title = "Submit Cover Request";
        SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), false);
        schedulePage.verifyClickOnSubmitButton();

        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();

        String fileName = "UserCredentialsForComparableSwapShifts.json";
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] credential = userCredentials.get("Cover TM");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                , String.valueOf(credential[0][2]));
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        if (dashboardPage.isSwitchToEmployeeViewPresent())
            dashboardPage.clickOnSwitchToEmployeeView();
        String coverName = profileNewUIPage.getNickNameFromProfile();
        dashboardPage.goToTodayForNewUI();
        schedulePage.isSchedule();
        if (schedulePage.isNextWeekAvaibale())
        schedulePage.selectNextWeekSchedule();
        Thread.sleep(10000);

        // Validate that smartcard is available to recipient team member
        String smartCard = "WANT MORE HOURS?";
        SimpleUtils.assertOnFail("Smart Card: " + smartCard + " not loaded Successfully!", schedulePage.isSpecificSmartCardLoaded(smartCard), false);
        // Validate the availability of all cover request shifts in schedule table
        String linkName = "View Shifts";;
        schedulePage.clickLinkOnSmartCardByName(linkName);
        SimpleUtils.assertOnFail("Open shifts not loaded Successfully!", schedulePage.areShiftsPresent(), false);
        // Validate the availability of Claim Shift Request popup
        String requestName = "Claim Shift";
        schedulePage.clickTheShiftRequestToClaimShift(requestName, requestUserName);
        // Validate the clickability of I Agree button
        schedulePage.verifyClickAgreeBtnOnClaimShiftOffer();

        loginPage.logOut();

        // Login as Store Manager
        fileName = "UsersCredentials.json";
        fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
        userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] teamMemberCredentials = userCredentials.get("StoreManager");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                , String.valueOf(teamMemberCredentials[0][2]));
        dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

        // Verify Activity Icon is loaded and approve the cover shift request
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftSwap.getValue(), indexOfActivityType.ShiftSwap.name());
        activityPage.approveOrRejectShiftCoverRequestOnActivity(requestUserName, coverName, approveRejectAction.Approve.getValue());
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the content of Shift Swap activity when TM request to cover the shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyRejectCoverRequestOfShiftSwapActivityAsTeamMember(String browser, String username, String password, String location) throws Exception {
        // Configuration in controls :Is approval by Manager required when an employee claims a shift swap or cover request?-Always
        // TM's next week's schedule must be published before running this test case
        // Cover TM should be in the list of Cover Request Status window
        // Cover TM should be not on the schedule at the same day with requested TM and is defined in "UserCredentialsForComparableSwapShifts.json"
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String requestUserName = profileNewUIPage.getNickNameFromProfile();
        SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
        schedulePage.isSchedule();
        if (schedulePage.isNextWeekAvaibale())
            schedulePage.selectNextWeekSchedule();
        Thread.sleep(10000);

        // For Cover Feature
        List<String> swapCoverRequests = new ArrayList<>(Arrays.asList("Request to Swap Shift", "Request to Cover Shift"));
        int index = schedulePage.verifyClickOnAnyShift();
        String request = "Request to Cover Shift";
        schedulePage.clickTheShiftRequestByName(request);
        // Validate the Submit button feature
        String title = "Submit Cover Request";
        SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), false);
        schedulePage.verifyClickOnSubmitButton();

        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();

        String fileName = "UserCredentialsForComparableSwapShifts.json";
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] credential = userCredentials.get("Cover TM");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                , String.valueOf(credential[0][2]));
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        if (dashboardPage.isSwitchToEmployeeViewPresent())
            dashboardPage.clickOnSwitchToEmployeeView();
        String coverName = profileNewUIPage.getNickNameFromProfile();
        dashboardPage.goToTodayForNewUI();
        schedulePage.isSchedule();
        if (schedulePage.isNextWeekAvaibale())
            schedulePage.selectNextWeekSchedule();
        Thread.sleep(10000);

        // Validate that smartcard is available to recipient team member
        String smartCard = "WANT MORE HOURS?";
        SimpleUtils.assertOnFail("Smart Card: " + smartCard + " not loaded Successfully!", schedulePage.isSpecificSmartCardLoaded(smartCard), false);
        // Validate the availability of all cover request shifts in schedule table
        String linkName = "View Shifts";;
        schedulePage.clickLinkOnSmartCardByName(linkName);
        SimpleUtils.assertOnFail("Open shifts not loaded Successfully!", schedulePage.areShiftsPresent(), false);
        // Validate the availability of Claim Shift Request popup
        String requestName = "Claim Shift";
        schedulePage.clickTheShiftRequestToClaimShift(requestName, requestUserName);
        // Validate the clickability of I Agree button
        schedulePage.verifyClickAgreeBtnOnClaimShiftOffer();

        loginPage.logOut();

        // Login as Store Manager
        fileName = "UsersCredentials.json";
        fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
        userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] teamMemberCredentials = userCredentials.get("StoreManager");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                , String.valueOf(teamMemberCredentials[0][2]));
        dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

        // Verify Activity Icon is loaded and approve the cover shift request
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftSwap.getValue(), indexOfActivityType.ShiftSwap.name());
        activityPage.approveOrRejectShiftCoverRequestOnActivity(requestUserName, coverName, approveRejectAction.Reject.getValue());
    }
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the notification when TM is requesting time off")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyTheNotificationForRequestTimeOffAsStoreManager(String browser, String username, String password, String location) throws Exception {
        // Login with Store Manager Credentials
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        // Set time off policy
        ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
        controlsPage.gotoControlsPage();
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
        controlsNewUIPage.clickOnControlsSchedulingPolicies();
        SimpleUtils.assertOnFail("Scheduling policy page not loaded successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
        controlsNewUIPage.updateCanWorkerRequestTimeOff("Yes");
        controlsNewUIPage.clickOnSchedulingPoliciesTimeOffAdvanceBtn();
        controlsNewUIPage.updateShowTimeOffReasons("Yes");
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();

        // Login as Team Member to create time off
        String fileName = "UsersCredentials.json";
        fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] teamMemberCredentials = userCredentials.get("TeamMember");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                , String.valueOf(teamMemberCredentials[0][2]));
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String requestUserName = profileNewUIPage.getNickNameFromProfile();
        profileNewUIPage.clickOnUserProfileImage();
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
        String timeOffReasonLabel = "JURY DUTY";
        // select time off reason
        profileNewUIPage.selectTimeOffReason(timeOffReasonLabel);
        profileNewUIPage.selectStartAndEndDate();
        profileNewUIPage.clickOnSaveTimeOffRequestBtn();
        loginPage.logOut();

        // Login as Store Manager again to check message and reject
        String RequstTimeOff = "requested";
        loginToLegionAndVerifyIsLoginDone(username, password, location);
        String respondUserName = profileNewUIPage.getNickNameFromProfile();
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(indexOfActivityType.TimeOff.getValue(),indexOfActivityType.TimeOff.name());
        activityPage.verifyTheNotificationForReqestTimeOff(requestUserName, getTimeOffStartTime(),getTimeOffEndTime(), RequstTimeOff);
        activityPage.approveOrRejectTTimeOffRequestOnActivity(requestUserName,respondUserName,approveRejectAction.Reject.getValue());
        activityPage.closeActivityWindow();
        loginPage.logOut();

        // Login as Team Member to create time off
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                , String.valueOf(teamMemberCredentials[0][2]));
        profileNewUIPage.clickOnUserProfileImage();
        profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myProfileLabel);
        SimpleUtils.assertOnFail("Profile page not loaded Successfully!", profileNewUIPage.isProfilePageLoaded(), false);
        profileNewUIPage.selectProfilePageSubSectionByLabel(aboutMeLabel);
        profileNewUIPage.selectProfilePageSubSectionByLabel(myTimeOffLabel);
        profileNewUIPage.cancelAllTimeOff();
        profileNewUIPage.clickOnCreateTimeOffBtn();
        SimpleUtils.assertOnFail("New time off request window not loaded Successfully!", profileNewUIPage.isNewTimeOffWindowLoaded(), false);
        //select time off reason
        profileNewUIPage.selectTimeOffReason(timeOffReasonLabel);
        profileNewUIPage.selectStartAndEndDate();
        profileNewUIPage.clickOnSaveTimeOffRequestBtn();
        loginPage.logOut();

        // Login as Store Manager again to check message and approve
        loginToLegionAndVerifyIsLoginDone(username, password, location);
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(indexOfActivityType.TimeOff.getValue(),indexOfActivityType.TimeOff.name());
        activityPage.verifyTheNotificationForReqestTimeOff(requestUserName, getTimeOffStartTime(),getTimeOffEndTime(), RequstTimeOff);
        activityPage.approveOrRejectTTimeOffRequestOnActivity(requestUserName,respondUserName,approveRejectAction.Approve.getValue());
        activityPage.closeActivityWindow();
        loginPage.logOut();

        // Login as Team Member to cancel all time off
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                , String.valueOf(teamMemberCredentials[0][2]));
        pageFactory.createProfileNewUIPage();
        profileNewUIPage.clickOnUserProfileImage();
        profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myProfileLabel);
        SimpleUtils.assertOnFail("Profile page not loaded Successfully!", profileNewUIPage.isProfilePageLoaded(), false);
        profileNewUIPage.selectProfilePageSubSectionByLabel(aboutMeLabel);
        profileNewUIPage.selectProfilePageSubSectionByLabel(myTimeOffLabel);
        profileNewUIPage.cancelAllTimeOff();
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the notification when TM cancels time off request")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyTheNotificationForCancelTimeOffAsStoreManager(String browser, String username, String password, String location) throws Exception {
        // Login with Store Manager Credentials
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        // Set time off policy
        ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
        controlsPage.gotoControlsPage();
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
        controlsNewUIPage.clickOnControlsSchedulingPolicies();
        SimpleUtils.assertOnFail("Scheduling policy page not loaded successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
        controlsNewUIPage.updateCanWorkerRequestTimeOff("Yes");
        controlsNewUIPage.clickOnSchedulingPoliciesTimeOffAdvanceBtn();
        controlsNewUIPage.updateShowTimeOffReasons("Yes");
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();

        // Login as Team Member to create time off
        String fileName = "UsersCredentials.json";
        fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] teamMemberCredentials = userCredentials.get("TeamMember");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                , String.valueOf(teamMemberCredentials[0][2]));
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String requestUserName = profileNewUIPage.getNickNameFromProfile();
        profileNewUIPage.clickOnUserProfileImage();
        String myProfileLabel = "My Profile";
        profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myProfileLabel);
        SimpleUtils.assertOnFail("Profile page not loaded Successfully!", profileNewUIPage.isProfilePageLoaded(), false);
        String aboutMeLabel = "About Me";
        profileNewUIPage.selectProfilePageSubSectionByLabel(aboutMeLabel);
        String myTimeOffLabel = "My Time Off";
        profileNewUIPage.selectProfilePageSubSectionByLabel(myTimeOffLabel);
        String timeOffReasonLabel = "JURY DUTY";
        profileNewUIPage.cancelAllTimeOff();
        profileNewUIPage.clickOnCreateTimeOffBtn();
        SimpleUtils.assertOnFail("New time off request window not loaded Successfully!", profileNewUIPage.isNewTimeOffWindowLoaded(), false);
        // select time off reason
        profileNewUIPage.selectTimeOffReason(timeOffReasonLabel);
        List<String> startNEndDates = profileNewUIPage.selectStartAndEndDate();
        profileNewUIPage.clickOnSaveTimeOffRequestBtn();
        profileNewUIPage.cancelAllTimeOff();
        loginPage.logOut();

        // Login as Store Manager again to check message
        String RequstTimeOff = "cancelled";
        loginToLegionAndVerifyIsLoginDone(username, password, location);
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(indexOfActivityType.TimeOff.getValue(),indexOfActivityType.TimeOff.name());
        activityPage.verifyTheNotificationForReqestTimeOff(requestUserName,getTimeOffStartTime(),getTimeOffEndTime(),RequstTimeOff);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify there is no notification when TM has been activated")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyNoNotificationForActivateTMAsStoreManager(String browser, String username, String password, String location) throws Exception {
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
        teamPage.isManualOnBoardButtonLoaded();
        teamPage.manualOnBoardTeamMember();
        teamPage.verifyTheStatusOfTeamMember(onBoarded);
        teamPage.isActivateButtonLoaded();
        teamPage.clickOnActivateButton();
        teamPage.isActivateWindowLoaded();
        teamPage.selectADateOnCalendarAndActivate();

        //to check there is no message for activating TM
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(indexOfActivityType.ProfileUpdate.getValue(),indexOfActivityType.ProfileUpdate.name());
        activityPage.verifyNoNotificationForActivateTM();
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the notification when TM updates availability from a week onwards.Set \"Is manager approval required when an employee changes availability?\" to \"Not required\" ")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyNotificationForUpdateAvailabilityRepeatForwardWithConfNOAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
        String fileName = "UsersCredentials.json";
        fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] teamMemberCredentials = userCredentials.get("TeamMember");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                , String.valueOf(teamMemberCredentials[0][2]));
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String requestUserName = profileNewUIPage.getNickNameFromProfile();
        profileNewUIPage.clickOnUserProfileImage();
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
        Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
                , String.valueOf(storeManagerCredentials[0][2]));
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(indexOfActivityType.ProfileUpdate.getValue(),indexOfActivityType.ProfileUpdate.name());
        String requestAwailabilityChangeLabel = "request";
        activityPage.verifyNotificationForUpdateAvailability(requestUserName,isApprovalRequired,requestAwailabilityChangeLabel,weekInfo,repeatChanges);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the notification when TM updates availability from a week onwards.Set \"Is manager approval required when an employee changes availability?\" to \"Not required\" ")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyNotificationForUpdateAvailability4SpecificWeekWithConfNOAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
        String fileName = "UsersCredentials.json";
        fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] teamMemberCredentials = userCredentials.get("TeamMember");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                , String.valueOf(teamMemberCredentials[0][2]));
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String requestUserName = profileNewUIPage.getNickNameFromProfile();
        profileNewUIPage.clickOnUserProfileImage();
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
        Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
                , String.valueOf(storeManagerCredentials[0][2]));
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(indexOfActivityType.ProfileUpdate.getValue(),indexOfActivityType.ProfileUpdate.name());
        String requestAwailabilityChangeLabel = "request";
        activityPage.verifyNotificationForUpdateAvailability(requestUserName,isApprovalRequired,requestAwailabilityChangeLabel,weekInfo,repeatChanges);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the notification when TM updates availability from a week onwards.Set \"Is manager approval required when an employee changes availability?\" to \"Required for all changes\" ")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyNotificationForUpdateAvailability4SpecificWeekWithConfYesAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
        String isApprovalRequired = "Required for all changes";
        controlsNewUIPage.updateAvailabilityManagementIsApprovalRequired(isApprovalRequired);
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();

        //Login as Team Member to change availability
        String fileName = "UsersCredentials.json";
        fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] teamMemberCredentials = userCredentials.get("TeamMember");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                , String.valueOf(teamMemberCredentials[0][2]));
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String requestUserName = profileNewUIPage.getNickNameFromProfile();
        profileNewUIPage.clickOnUserProfileImage();
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
        Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
                , String.valueOf(storeManagerCredentials[0][2]));
        String respondUserName = profileNewUIPage.getNickNameFromProfile();
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(indexOfActivityType.ProfileUpdate.getValue(),indexOfActivityType.ProfileUpdate.name());
        String requestAwailabilityChangeLabel = "requested";
        activityPage.verifyNotificationForUpdateAvailability(requestUserName,isApprovalRequired,requestAwailabilityChangeLabel,weekInfo,repeatChanges);
        activityPage.approveOrRejectTTimeOffRequestOnActivity(requestUserName,respondUserName,approveRejectAction.Approve.getValue());
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the notification when TM updates availability from a week onwards.Set \"Is manager approval required when an employee changes availability?\" to \"Required for all changes\" ")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyNotificationForUpdateAvailabilityRepeatForwardWithConfYesAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
        String isApprovalRequired = "Required for all changes";
        controlsNewUIPage.updateAvailabilityManagementIsApprovalRequired(isApprovalRequired);
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();

        //Login as Team Member to change availability
        String fileName = "UsersCredentials.json";
        fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] teamMemberCredentials = userCredentials.get("TeamMember");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                , String.valueOf(teamMemberCredentials[0][2]));
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String requestUserName = profileNewUIPage.getNickNameFromProfile();
        profileNewUIPage.clickOnUserProfileImage();
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
        Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
                , String.valueOf(storeManagerCredentials[0][2]));
        String respondUserName = profileNewUIPage.getNickNameFromProfile();
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(indexOfActivityType.ProfileUpdate.getValue(),indexOfActivityType.ProfileUpdate.name());
        String requestAwailabilityChangeLabel = "requested";
        activityPage.verifyNotificationForUpdateAvailability(requestUserName,isApprovalRequired,requestAwailabilityChangeLabel,weekInfo,repeatChanges);
        activityPage.approveOrRejectTTimeOffRequestOnActivity(requestUserName,respondUserName,approveRejectAction.Reject.getValue());
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the activity of claim open shift")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyActivityOfClaimOpenShift(String browser, String username, String password, String location) throws Exception {

        // 1.Checking configuration in controls
        String option = "Always";
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        controlsNewUIPage.clickOnControlsConsoleMenu();
        controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
        boolean isScheduleCollaboration = controlsNewUIPage.isControlsScheduleCollaborationLoaded();
        SimpleUtils.assertOnFail("Controls Page: Schedule Collaboration Section not Loaded.", isScheduleCollaboration, true);
        String selectedOption = controlsNewUIPage.getIsApprovalByManagerRequiredWhenEmployeeClaimsOpenShiftSelectedOption();
        if (!selectedOption.equalsIgnoreCase("Always")) {
            controlsNewUIPage.updateOpenShiftApprovedByManagerOption(option);
        } else {

            // 2.admin create one manual open shift and assign to specific TM
            String teamMemberName = propertySearchTeamMember.get("TeamLCMember");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());

            //to generate schedule  if current week is not generated
            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                schedulePage.generateOrUpdateAndGenerateSchedule();
            }

            float shiftHoursInWeekForTM = schedulePage.getShiftHoursByTMInWeekView(teamMemberName);
            if (shiftHoursInWeekForTM == 0) {
                schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                schedulePage.clickOnDayView();
                schedulePage.clickNewDayViewShiftButtonLoaded();
                schedulePage.customizeNewShiftPage();
                schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
                schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
                schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole_BARISTA"));
                schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
                schedulePage.clickOnCreateOrNextBtn();
                schedulePage.selectSpecificTMWhileCreateNewShift(teamMemberName);
                schedulePage.clickOnOfferOrAssignBtn();
                schedulePage.saveSchedule();
                schedulePage.publishActiveSchedule();
            } else {
                schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                schedulePage.deleteTMShiftInWeekView(teamMemberName);
                schedulePage.clickOnDayView();
                schedulePage.clickNewDayViewShiftButtonLoaded();
                schedulePage.customizeNewShiftPage();
                schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
                schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
                schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole_BARISTA"));
                schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
                schedulePage.clickOnCreateOrNextBtn();
                schedulePage.selectSpecificTMWhileCreateNewShift(teamMemberName);
                schedulePage.clickOnOfferOrAssignBtn();
                schedulePage.saveSchedule();
                schedulePage.publishActiveSchedule();
            }

        }

        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();

        // 3.Login with the TM to claim the shift

        String fileName = "UsersCredentials.json";
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        fileName = SimpleUtils.getEnterprise("Coffee_Enterprise") + fileName;
        userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] teamMemberCredentials = userCredentials.get("TeamMember");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                , String.valueOf(teamMemberCredentials[0][2]));
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
        schedulePage.isSchedule();
        String cardName = "WANT MORE HOURS?";
        SimpleUtils.assertOnFail("Smart Card: " + cardName + " not loaded Successfully!", schedulePage.isSpecificSmartCardLoaded(cardName), false);
        String linkName = "View Shifts";
        schedulePage.clickLinkOnSmartCardByName(linkName);
        SimpleUtils.assertOnFail("Open shifts not loaed Successfully!", schedulePage.areShiftsPresent(), false);
        List<String> shiftHours = schedulePage.getShiftHoursFromInfoLayout();
        List<String> claimShift = new ArrayList<>(Arrays.asList("Claim Shift"));
        int index = schedulePage.selectOneShiftIsClaimShift(claimShift);
        schedulePage.clickOnShiftByIndex(index);
        schedulePage.clickTheShiftRequestByName(claimShift.get(0));
        schedulePage.verifyClickAgreeBtnOnClaimShiftOffer();

        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String requestUserName = profileNewUIPage.getNickNameFromProfile();
        loginPage.logOut();

        // 4.Login with SM to check activity
        Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
                , String.valueOf(storeManagerCredentials[0][2]));
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(indexOfActivityType.ShiftOffer.getValue(), indexOfActivityType.ShiftOffer.name());
        activityPage.verifyActivityOfShiftOffer(requestUserName);
        activityPage.approveOrRejectShiftOfferRequestOnActivity(requestUserName,approveRejectAction.Approve.getValue());

    }
}
