package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.core.ConsoleScheduleCommonPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class OfferTMTest extends TestBase {
    private static HashMap<String, String> scheduleWorkRoles = JsonUtil.getPropertiesFromJsonFile("src/test/resources/WorkRoleOptions.json");
    private static HashMap<String, String> propertyCustomizeMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ScheduleCustomizeNewShift.json");
    private static HashMap<String, String> schedulePolicyData = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SchedulingPoliciesData.json");
    private static HashMap<String, String> propertySearchTeamMember = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SearchTeamMember.json");

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
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify \"Offer Team Members\" option is available for Open shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyOfferTMOptionAvailableForOpenShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

        schedulePage.navigateToNextWeek();
        boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (isWeekGenerated){
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();

        //delete unassigned shifts and open shifts.
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.clickOnFilterBtn();
        scheduleMainPage.selectShiftTypeFilterByText("Action Required");
        //schedulePage.deleteTMShiftInWeekView("Unassigned");
        //Delete all shifts are action required.
        schedulePage.deleteTMShiftInWeekView("");
        schedulePage.clickOnFilterBtn();
        scheduleMainPage.selectShiftTypeFilterByText("Open");
        schedulePage.deleteTMShiftInWeekView("");
        schedulePage.clickOnFilterBtn();
        schedulePage.clickOnClearFilterOnFilterDropdownPopup();
        scheduleMainPage.saveSchedule();
        String workRoleOfTM = shiftOperatePage.getRandomWorkRole();

        //verify assigned shift in non-edit mode for week view and day view
        shiftOperatePage.clickOnProfileIcon();
        SimpleUtils.assertOnFail("Offer TMs option should be visible!", !schedulePage.isOfferTMOptionVisible(), false);
        scheduleCommonPage.clickOnDayView();
        scheduleShiftTablePage.clickOnProfileIconOfShiftInDayView("no");
        SimpleUtils.assertOnFail("Offer TMs option should be visible!", !schedulePage.isOfferTMOptionVisible(), false);
        scheduleCommonPage.clickOnWeekView();

        //verify assigned shift in edit mode for week view and day view
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        shiftOperatePage.clickOnProfileIcon();
        SimpleUtils.assertOnFail("Offer TMs option should be visible!", !schedulePage.isOfferTMOptionVisible(), false);
        scheduleCommonPage.clickOnDayView();
        scheduleShiftTablePage.clickOnProfileIconOfShiftInDayView("no");
        SimpleUtils.assertOnFail("Offer TMs option should be visible!", !schedulePage.isOfferTMOptionVisible(), false);
        scheduleMainPage.saveSchedule();
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

        //create auto open shifts.
        newShiftPage.clickOnDayViewAddNewShiftButton();
        newShiftPage.customizeNewShiftPage();
        newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        newShiftPage.selectWorkRole(workRoleOfTM);
        newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
        newShiftPage.clickOnCreateOrNextBtn();

        //verify auto open shift in edit mode.
        //--verify in day view
        scheduleShiftTablePage.clickOnProfileIconOfShiftInDayView("open");
        SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
        SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
        //--verify in week view
        scheduleCommonPage.clickOnWeekView();
        shiftOperatePage.clickOnProfileIconOfOpenShift();
        SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
        SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
        scheduleMainPage.saveSchedule();
        //verify auto open shift in non-edit mode.
        //--verify in day view
        scheduleCommonPage.clickOnDayView();
        scheduleShiftTablePage.clickOnProfileIconOfShiftInDayView("open");
        SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
        SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
        //--verify in week view
        scheduleCommonPage.clickOnWeekView();
        shiftOperatePage.clickOnProfileIconOfOpenShift();
        SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
        SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);

        //create manual open shifts.
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.deleteTMShiftInWeekView("open");
        scheduleCommonPage.clickOnDayView();
        newShiftPage.clickOnDayViewAddNewShiftButton();
        newShiftPage.customizeNewShiftPage();
        newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        newShiftPage.selectWorkRole(workRoleOfTM);
        newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
        newShiftPage.clickOnCreateOrNextBtn();
        newShiftPage.verifySelectTeamMembersOption();
        newShiftPage.clickOnOfferOrAssignBtn();
        //verify manual open shift in edit mode.
        //--verify in day view
        scheduleShiftTablePage.clickOnProfileIconOfShiftInDayView("open");
        SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
        SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
        //--verify in week view
        scheduleCommonPage.clickOnWeekView();
        shiftOperatePage.clickOnProfileIconOfOpenShift();
        SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
        SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
        scheduleMainPage.saveSchedule();
        //verify manual open shifts in non-edit mode.
        //--verify in day view
        scheduleCommonPage.clickOnDayView();
        scheduleShiftTablePage.clickOnProfileIconOfShiftInDayView("open");
        SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
        SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
        //--verify in week view
        scheduleCommonPage.clickOnWeekView();
        shiftOperatePage.clickOnProfileIconOfOpenShift();
        SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
        SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the functionality of \"Offer Team Members\" for Open Shift: Auto in non-Edit mode")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyFunctionalityOfOfferTMForAutoOpenShiftsInNonEditModeAsTeamMember(String browser, String username, String password, String location) throws Exception{
        try {
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
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            schedulePage.clickOnFilterBtn();
            //click search button, to pick a work role won't get role violation.
            schedulePage.clickOnOpenSearchBoxButton();
            schedulePage.searchShiftOnSchedulePage(firstNameOfTM);
            String workRoleOfTM = shiftOperatePage.getRandomWorkRole();
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnCloseSearchBoxButton();

            //create auto open shifts.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.selectWorkRole(workRoleOfTM);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            scheduleMainPage.saveSchedule();

            //verify auto open shift in non-edit mode.
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.clickOnOfferTMOption();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            schedulePage.verifyRecommendedTableHasTM();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            newShiftPage.searchTeamMemberByName(firstNameOfTM);
            newShiftPage.clickOnOfferOrAssignBtn();
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickViewStatusBtn();
            schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
            shiftOperatePage.closeViewStatusContainer();
            loginPage.logOut();

            //login with TM.
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            schedulePage.navigateToNextWeek();
            schedulePage.clickLinkOnSmartCardByName("View Shifts");
            SimpleUtils.assertOnFail("Didn't get open shift offer!", schedulePage.getShiftsCount()==1, false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the functionality of \"Offer Team Members\" for Open Shift: Auto in Edit Mode")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyFunctionalityOfOfferTMForAutoOpenShiftsInEditModeAsTeamMember(String browser, String username, String password, String location) throws Exception{
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            String workRoleOfTM = shiftOperatePage.getRandomWorkRole();
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText(workRoleOfTM);
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();

            //create auto open shifts.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            //newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint("8","8");
            newShiftPage.selectWorkRole(workRoleOfTM);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            WebElement selectedShift = shiftOperatePage.clickOnProfileIconOfOpenShift();
            String selectedShiftId= selectedShift.getAttribute("id");
            int index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);

            //verify auto open shift in edit mode.
            List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.clickOnOfferTMOption();
            String shiftInfoInWindows = schedulePage.getViewStatusShiftsInfo();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            schedulePage.verifyRecommendedTableHasTM();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            newShiftPage.searchTeamMemberByName(firstNameOfTM);
            newShiftPage.clickOnOfferOrAssignBtn();
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickViewStatusBtn();
            schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
            shiftOperatePage.closeViewStatusContainer();
            SimpleUtils.assertOnFail("shift time is not consistent!", shiftInfoInWindows.toLowerCase().contains(shiftInfo.get(2).toLowerCase()), false);
            SimpleUtils.assertOnFail("shift work role is not consistent!", shiftInfoInWindows.toLowerCase().contains(shiftInfo.get(4).toLowerCase()), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the functionality of \"Offer Team Members\" for Open Shift: Manual in non-Edit mode")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyFunctionalityOfOfferTMForManualOpenShiftsInNonEditModeAsTeamMember(String browser, String username, String password, String location) throws Exception{
        try {
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
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            schedulePage.clickOnFilterBtn();
            //click search button, to pick a work role won't get role violation.
            schedulePage.clickOnOpenSearchBoxButton();
            schedulePage.searchShiftOnSchedulePage(firstNameOfTM);
            String workRoleOfTM = shiftOperatePage.getRandomWorkRole();
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnCloseSearchBoxButton();


            //create manual open shifts.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.selectWorkRole(workRoleOfTM);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.verifySelectTeamMembersOption();
            newShiftPage.clickOnOfferOrAssignBtn();
            scheduleMainPage.saveSchedule();

            //verify manual open shift in non-edit mode.
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.clickOnOfferTMOption();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            schedulePage.verifyRecommendedTableHasTM();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            newShiftPage.searchTeamMemberByNameNLocation(firstNameOfTM, location);
            newShiftPage.clickOnOfferOrAssignBtn();
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickViewStatusBtn();
            schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
            shiftOperatePage.closeViewStatusContainer();
            loginPage.logOut();

            //login with TM.
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            schedulePage.navigateToNextWeek();
            schedulePage.clickLinkOnSmartCardByName("View Shifts");
            SimpleUtils.assertOnFail("Didn't get open shift offer!", schedulePage.getShiftsCount()==1, false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the functionality of \"Offer Team Members\" for Open Shift: Manual in Edit Mode")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyFunctionalityOfOfferTMForManualOpenShiftsInEditModeAsTeamMember(String browser, String username, String password, String location) throws Exception{
        try {
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
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            String workRoleOfTM = shiftOperatePage.getRandomWorkRole();
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText(workRoleOfTM);
            schedulePage.deleteAllShiftsInWeekView();
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();

            //create manual open shifts.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            //newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint("8","8");
            newShiftPage.selectWorkRole(workRoleOfTM);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.verifySelectTeamMembersOption();
            newShiftPage.clickOnOfferOrAssignBtn();
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            WebElement selectedShift = shiftOperatePage.clickOnProfileIconOfOpenShift();
            String selectedShiftId= selectedShift.getAttribute("id");
            int index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);

            //verify manual open shift in edit mode.
            List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.clickOnOfferTMOption();
            String shiftInfoInWindows = schedulePage.getViewStatusShiftsInfo();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            schedulePage.verifyRecommendedTableHasTM();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            newShiftPage.searchTeamMemberByNameNLocation(firstNameOfTM, location);
            newShiftPage.clickOnOfferOrAssignBtn();
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickViewStatusBtn();
            schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
            shiftOperatePage.closeViewStatusContainer();
            SimpleUtils.assertOnFail("shift time is not consistent!", shiftInfoInWindows.toLowerCase().contains(shiftInfo.get(2).toLowerCase()), false);
            SimpleUtils.assertOnFail("shift work role is not consistent!", shiftInfoInWindows.toLowerCase().contains(shiftInfo.get(4).toLowerCase()), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the functionality of \"Offer Team Members\" for converting assigned shift to open in non-Edit mode")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyFunctionalityOfOfferTMForConvertToOpenShiftsInNonEditModeAsTeamMember(String browser, String username, String password, String location) throws Exception{
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM);
            scheduleMainPage.saveSchedule();
            String workRoleOfTM = shiftOperatePage.getRandomWorkRole();

            //convert a shift to open shift
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectWorkRoleFilterByText(workRoleOfTM, true);
            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.clickOnConvertToOpenShift();
            schedulePage.convertToOpenShiftDirectly();
            scheduleMainPage.saveSchedule();

            //verify the open shift in non-edit mode.
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.clickOnOfferTMOption();
            newShiftPage.searchTeamMemberByNameNLocation(firstNameOfTM, location);
            newShiftPage.clickOnOfferOrAssignBtn();
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickViewStatusBtn();
            schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
            shiftOperatePage.closeViewStatusContainer();
            loginPage.logOut();

            //login with TM.
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            schedulePage.navigateToNextWeek();
            schedulePage.clickLinkOnSmartCardByName("View Shifts");
            SimpleUtils.assertOnFail("Didn't get open shift offer!", schedulePage.getShiftsCount()==1, false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the functionality of \"Offer Team Members\" for converting assigned shift to open in Edit mode")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyFunctionalityOfOfferTMForConvertToOpenShiftsInEditModeAsTeamMember(String browser, String username, String password, String location) throws Exception{
        try {
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
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM);
            scheduleMainPage.saveSchedule();
            String workRoleOfTM = shiftOperatePage.getRandomWorkRole();

            //convert a shift to open shift
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectWorkRoleFilterByText(workRoleOfTM, true);
            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.clickOnConvertToOpenShift();
            schedulePage.convertToOpenShiftDirectly();
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            WebElement selectedShift = shiftOperatePage.clickOnProfileIconOfOpenShift();
            String selectedShiftId= selectedShift.getAttribute("id");
            int index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);

            //verify assign TM in edit mode.
            List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.clickOnOfferTMOption();
            String shiftInfoInWindows = schedulePage.getViewStatusShiftsInfo();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            schedulePage.verifyRecommendedTableHasTM();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            newShiftPage.searchTeamMemberByNameNLocation(firstNameOfTM, location);
            newShiftPage.clickOnOfferOrAssignBtn();
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickViewStatusBtn();
            schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
            shiftOperatePage.closeViewStatusContainer();
            SimpleUtils.assertOnFail("shift time is not consistent!", shiftInfoInWindows.toLowerCase().contains(shiftInfo.get(2).toLowerCase()), false);
            SimpleUtils.assertOnFail("shift work role is not consistent!", shiftInfoInWindows.toLowerCase().contains(shiftInfo.get(4).toLowerCase()), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the open shift is assigned successfully")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheOpenShiftIsAssignedSuccessfullyAsTeamMember(String browser, String username, String password, String location) throws Exception{
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
        MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
        loginPage.logOut();

        loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

        schedulePage.navigateToNextWeek();
        boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (isWeekGenerated){
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();

        //delete unassigned shifts and open shifts.
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.clickOnFilterBtn();
        scheduleMainPage.selectShiftTypeFilterByText("Action Required");
        //schedulePage.deleteTMShiftInWeekView("Unassigned");
        //Delete all shifts are action required.
        schedulePage.deleteTMShiftInWeekView("");
        schedulePage.clickOnFilterBtn();
        scheduleMainPage.selectShiftTypeFilterByText("Open");
        schedulePage.deleteTMShiftInWeekView("");
        schedulePage.clickOnFilterBtn();
        schedulePage.clickOnClearFilterOnFilterDropdownPopup();
        schedulePage.clickOnFilterBtn();
        //click search button, to pick a work role won't get role violation.
        schedulePage.clickOnOpenSearchBoxButton();
        schedulePage.searchShiftOnSchedulePage(firstNameOfTM);
        String workRoleOfTM = shiftOperatePage.getRandomWorkRole();
        schedulePage.deleteTMShiftInWeekView("");
        schedulePage.clickOnCloseSearchBoxButton();

        //create manual open shifts.
        newShiftPage.clickOnDayViewAddNewShiftButton();
        newShiftPage.customizeNewShiftPage();
        newShiftPage.moveSliderAtSomePoint("8", 20, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        newShiftPage.selectWorkRole(workRoleOfTM);
        newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
        newShiftPage.clickOnCreateOrNextBtn();
        scheduleMainPage.saveSchedule();
        WebElement selectedShift = shiftOperatePage.clickOnProfileIconOfOpenShift();
        String selectedShiftId= selectedShift.getAttribute("id");
        int index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);


        List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(index);
        shiftOperatePage.clickOnProfileIconOfOpenShift();
        SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
        schedulePage.clickOnOfferTMOption();
        newShiftPage.searchTeamMemberByNameNLocation(firstNameOfTM, location);
        newShiftPage.clickOnOfferOrAssignBtn();
        shiftOperatePage.clickOnProfileIconOfOpenShift();
        schedulePage.clickViewStatusBtn();
        schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
        shiftOperatePage.closeViewStatusContainer();
        loginPage.logOut();

        //login with TM.
        loginToLegionAndVerifyIsLoginDone(username, password, location);
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        schedulePage.navigateToNextWeek();
        schedulePage.clickLinkOnSmartCardByName("View Shifts");
        SimpleUtils.assertOnFail("Didn't get open shift offer!", schedulePage.getShiftsCount()==1, false);
        List<String> shiftInfoFromTMView = scheduleShiftTablePage.getTheShiftInfoInDayViewByIndex(0);
        SimpleUtils.assertOnFail("shift info is not consistent", shiftInfo.get(2).contains(shiftInfoFromTMView.get(2)) && shiftInfoFromTMView.get(4).contains(shiftInfo.get(4)), false);

        List<String> claimShift = new ArrayList<>(Arrays.asList("View Offer"));
        mySchedulePage.selectOneShiftIsClaimShift(claimShift);
        mySchedulePage.clickTheShiftRequestByName(claimShift.get(0));
        mySchedulePage.verifyClickAgreeBtnOnClaimShiftOffer();
        loginPage.logOut();

        loginAsDifferentRole(AccessRoles.StoreManager.getValue());
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftOffer.getValue(), ActivityTest.indexOfActivityType.ShiftOffer.name());
        activityPage.verifyActivityOfShiftOffer(firstNameOfTM, location);
        activityPage.approveOrRejectShiftOfferRequestOnActivity(firstNameOfTM, ActivityTest.approveRejectAction.Approve.getValue());
        activityPage.closeActivityWindow();

        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
        schedulePage.navigateToNextWeek();
        shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(index);
        SimpleUtils.assertOnFail("Open shift is not assigned successfully!", shiftInfo.get(0).equalsIgnoreCase(firstNameOfTM), false);
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the open shift is not assigned if SM rejected")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheOpenShiftIsNotAssignedIfSMRejectedAsTeamMember(String browser, String username, String password, String location) throws Exception{
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
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
            //===================================
            controlsNewUIPage.updateOpenShiftApprovedByManagerOption(option);
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            schedulePage.clickOnFilterBtn();
            //click search button, to pick a work role won't get role violation.
            schedulePage.clickOnOpenSearchBoxButton();
            schedulePage.searchShiftOnSchedulePage(firstNameOfTM);
            String workRoleOfTM = shiftOperatePage.getRandomWorkRole();
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnCloseSearchBoxButton();

            //create auto open shifts.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.moveSliderAtSomePoint("8", 20, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.selectWorkRole(workRoleOfTM);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            scheduleMainPage.saveSchedule();
            WebElement selectedShift = shiftOperatePage.clickOnProfileIconOfOpenShift();
            String selectedShiftId= selectedShift.getAttribute("id");
            int index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);


            List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.clickOnOfferTMOption();
            newShiftPage.searchTeamMemberByNameNLocation(firstNameOfTM, location);
            newShiftPage.clickOnOfferOrAssignBtn();
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickViewStatusBtn();
            schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
            shiftOperatePage.closeViewStatusContainer();
            loginPage.logOut();

            //login with TM.
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            schedulePage.navigateToNextWeek();
            schedulePage.clickLinkOnSmartCardByName("View Shifts");
            SimpleUtils.assertOnFail("Didn't get open shift offer!", schedulePage.getShiftsCount()==1, false);
            List<String> shiftInfoFromTMView = scheduleShiftTablePage.getTheShiftInfoInDayViewByIndex(0);
            SimpleUtils.assertOnFail("shift info is not consistent", shiftInfo.get(2).contains(shiftInfoFromTMView.get(2)) && shiftInfoFromTMView.get(4).contains(shiftInfo.get(4)), false);

            List<String> claimShift = new ArrayList<>(Arrays.asList("View Offer"));
            mySchedulePage.selectOneShiftIsClaimShift(claimShift);
            mySchedulePage.clickTheShiftRequestByName(claimShift.get(0));
            mySchedulePage.verifyClickAgreeBtnOnClaimShiftOffer();
            loginPage.logOut();

            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftOffer.getValue(), ActivityTest.indexOfActivityType.ShiftOffer.name());
            activityPage.verifyActivityOfShiftOffer(firstNameOfTM, location);
            activityPage.approveOrRejectShiftOfferRequestOnActivity(firstNameOfTM, ActivityTest.approveRejectAction.Reject.getValue());
            activityPage.closeActivityWindow();
            loginPage.logOut();

            //log in as TM.
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            schedulePage.navigateToNextWeek();
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Open");
            SimpleUtils.assertOnFail("shouldn't get any open shift offer!", schedulePage.getShiftsCount()==0, false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify 2 TMs claim the open shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verify2TMsClaimTheOpenShiftAsTeamMember(String browser, String username, String password, String location) throws Exception{
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String firstNameOfTM1 = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            //log in as TM2 to get nickname.
            loginAsDifferentRole(AccessRoles.TeamMember2.getValue());
            String firstNameOfTM2 = profileNewUIPage.getNickNameFromProfile();
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
            //===================================
            controlsNewUIPage.updateOpenShiftApprovedByManagerOption(option);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM2);
            String workRoleOfTM = shiftOperatePage.getRandomWorkRole();

            //create manual open shifts.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.moveSliderAtSomePoint("8", 20, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.selectWorkRole(workRoleOfTM);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            scheduleMainPage.saveSchedule();


            shiftOperatePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickOnOfferTMOption();
            newShiftPage.searchTeamMemberByNameNLocation(firstNameOfTM1, location);
            newShiftPage.clickOnOfferOrAssignBtn();
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickOnOfferTMOption();
            newShiftPage.searchTeamMemberByNameNLocation(firstNameOfTM2, location);
            newShiftPage.clickOnOfferOrAssignBtn();
            loginPage.logOut();

            //login with TM1 to claim.
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            schedulePage.navigateToNextWeek();
            schedulePage.clickLinkOnSmartCardByName("View Shifts");
            List<String> claimShift = new ArrayList<>(Arrays.asList("Claim Shift"));
            mySchedulePage.selectOneShiftIsClaimShift(claimShift);
            mySchedulePage.clickTheShiftRequestByName(claimShift.get(0));
            mySchedulePage.verifyClickAgreeBtnOnClaimShiftOffer();
            loginPage.logOut();

            //login with TM2 to claim.
            loginAsDifferentRole(AccessRoles.TeamMember2.getValue());
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            schedulePage.navigateToNextWeek();
            schedulePage.clickLinkOnSmartCardByName("View Shifts");
            claimShift = new ArrayList<>(Arrays.asList("Claim Shift"));
            mySchedulePage.selectOneShiftIsClaimShift(claimShift);
            mySchedulePage.clickTheShiftRequestByName(claimShift.get(0));
            mySchedulePage.verifyClickAgreeBtnOnClaimShiftOffer();
            loginPage.logOut();

            //log in as SM to check.
            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftOffer.getValue(), ActivityTest.indexOfActivityType.ShiftOffer.name());
            activityPage.verifyActivityOfShiftOffer(firstNameOfTM2, location);
            activityPage.approveOrRejectShiftOfferRequestOnActivity(firstNameOfTM1, ActivityTest.approveRejectAction.Approve.getValue());
            activityPage.verifyApproveShiftOfferRequestAndGetErrorOnActivity(firstNameOfTM1);
            String expectedTopMessage = "Error! Failed to Approve";
            mySchedulePage.verifyThePopupMessageOnTop(expectedTopMessage);
            activityPage.closeActivityWindow();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the second TM cannot claim the open shift when never need SM approval")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifySecondTMCannotClaimTheOpenShiftAsTeamMember(String browser, String username, String password, String location) throws Exception{
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String firstNameOfTM1 = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            loginAsDifferentRole(AccessRoles.TeamMember2.getValue());
            String firstNameOfTM2 = profileNewUIPage.getNickNameFromProfile();
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
            controlsNewUIPage.updateOpenShiftApprovedByManagerOption(option);
            //===================================
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            //delete unassigned shifts and open shifts.
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            schedulePage.clickOnFilterBtn();
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM2);
            //click search button, to pick a work role won't get role violation.
            schedulePage.clickOnOpenSearchBoxButton();
            schedulePage.searchShiftOnSchedulePage(firstNameOfTM1);
            String workRoleOfTM = shiftOperatePage.getRandomWorkRole();
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnCloseSearchBoxButton();;

            //create manual open shifts.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.moveSliderAtSomePoint("8", 20, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.selectWorkRole(workRoleOfTM);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            scheduleMainPage.saveSchedule();


            shiftOperatePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.clickOnOfferTMOption();
            newShiftPage.searchTeamMemberByNameNLocation(firstNameOfTM1, location);
            newShiftPage.clickOnOfferOrAssignBtn();
            shiftOperatePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickOnOfferTMOption();
            newShiftPage.searchTeamMemberByNameNLocation(firstNameOfTM2, location);
            newShiftPage.clickOnOfferOrAssignBtn();
            loginPage.logOut();

            //login with TM1 to claim.
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            schedulePage.navigateToNextWeek();
            schedulePage.clickLinkOnSmartCardByName("View Shifts");
            List<String> claimShift = new ArrayList<>(Arrays.asList("View Offer"));
            mySchedulePage.selectOneShiftIsClaimShift(claimShift);
            mySchedulePage.clickTheShiftRequestByName(claimShift.get(0));
            mySchedulePage.verifyClickAgreeBtnOnClaimShiftOfferWhenDontNeedApproval();
            loginPage.logOut();

            //login with TM2 to claim.
            loginAsDifferentRole(AccessRoles.TeamMember2.getValue());
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            schedulePage.navigateToNextWeek();
            schedulePage.clickLinkOnSmartCardByName("View Shifts");
            SimpleUtils.assertOnFail("shouldn't get any open shift offer!", schedulePage.getShiftsCount()==0, false);
            loginPage.logOut();

            //log in as AM to set back the config.
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            option = "Always";
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
            SimpleUtils.assertOnFail("Controls Page: Schedule Collaboration Section not Loaded.", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), true);
            controlsNewUIPage.updateOpenShiftApprovedByManagerOption(option);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify TM can be offered if claiming successfully and converting the assigned shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTMCanBeOfferedAsTeamMember(String browser, String username, String password, String location) throws Exception{
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
        MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

        LoginPage loginPage = pageFactory.createConsoleLoginPage();

        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String firstNameOfTM1 = profileNewUIPage.getNickNameFromProfile();
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
        controlsNewUIPage.updateOpenShiftApprovedByManagerOption(option);
        //===================================
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

        schedulePage.navigateToNextWeek();
        boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (isWeekGenerated){
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        createSchedulePage.createScheduleForNonDGFlowNewUI();

        //delete unassigned shifts and open shifts.
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.clickOnFilterBtn();
        scheduleMainPage.selectShiftTypeFilterByText("Action Required");
        //schedulePage.deleteTMShiftInWeekView("Unassigned");
        //Delete all shifts are action required.
        schedulePage.deleteTMShiftInWeekView("");
        schedulePage.clickOnFilterBtn();
        scheduleMainPage.selectShiftTypeFilterByText("Open");
        schedulePage.deleteTMShiftInWeekView("");
        schedulePage.clickOnFilterBtn();
        schedulePage.clickOnClearFilterOnFilterDropdownPopup();
        schedulePage.clickOnFilterBtn();
        //click search button, to pick a work role won't get role violation.
        schedulePage.clickOnOpenSearchBoxButton();
        schedulePage.searchShiftOnSchedulePage(firstNameOfTM1);
        String workRoleOfTM = shiftOperatePage.getRandomWorkRole();
        schedulePage.deleteTMShiftInWeekView("");
        schedulePage.clickOnCloseSearchBoxButton();

        //create manual open shifts.
        newShiftPage.clickOnDayViewAddNewShiftButton();
        newShiftPage.customizeNewShiftPage();
        newShiftPage.moveSliderAtSomePoint("8", 20, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        newShiftPage.selectWorkRole(workRoleOfTM);
        newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
        newShiftPage.clickOnCreateOrNextBtn();
        newShiftPage.searchTeamMemberByName(firstNameOfTM1);
        newShiftPage.clickOnOfferOrAssignBtn();
        scheduleMainPage.saveSchedule();

        WebElement openShift = shiftOperatePage.clickOnProfileIconOfOpenShift();
        String selectedShiftId= openShift.getAttribute("id");
        int index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
        schedulePage.clickViewStatusBtn();
        schedulePage.verifyTMInTheOfferList(firstNameOfTM1, "draft");
        shiftOperatePage.closeViewStatusContainer();
        createSchedulePage.publishActiveSchedule();
        shiftOperatePage.clickOnProfileIconOfOpenShift();
        schedulePage.clickViewStatusBtn();
        schedulePage.verifyTMInTheOfferList(firstNameOfTM1, "offered");
        shiftOperatePage.closeViewStatusContainer();
        loginPage.logOut();

        //login with TM to claim.
        loginToLegionAndVerifyIsLoginDone(username, password, location);
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        schedulePage.navigateToNextWeek();
        schedulePage.clickLinkOnSmartCardByName("View Shifts");
        List<String> claimShift = new ArrayList<>(Arrays.asList("View Offer"));
        mySchedulePage.selectOneShiftIsClaimShift(claimShift);
        mySchedulePage.clickTheShiftRequestByName(claimShift.get(0));
        mySchedulePage.verifyClickAgreeBtnOnClaimShiftOfferWhenDontNeedApproval();
        loginPage.logOut();

        //log in as SM to check the shift
        loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

        schedulePage.navigateToNextWeek();
        List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(index);
        SimpleUtils.assertOnFail("Open shift is not assigned successfully!", shiftInfo.get(0).equalsIgnoreCase(firstNameOfTM1), false);

        //convert to open shift.
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.clickProfileIconOfShiftByIndex(index);
        shiftOperatePage.clickOnConvertToOpenShift();
        schedulePage.convertToOpenShiftDirectly();
        scheduleMainPage.saveSchedule();
        shiftOperatePage.clickOnProfileIconOfOpenShift();
        schedulePage.clickViewStatusBtn();
/*        //=============SCH-3418=============
        schedulePage.verifyTMInTheOfferList(firstNameOfTM1, "accepted");
        shiftOperatePage.closeViewStatusContainer();
		shiftOperatePage.clickOnProfileIconOfOpenShift();
		schedulePage.clickOnOfferTMOption();
		newShiftPage.searchTeamMemberByNameNLocation(firstNameOfTM1, location);
		newShiftPage.clickOnOfferOrAssignBtn();
		shiftOperatePage.clickOnProfileIconOfOpenShift();
		schedulePage.clickViewStatusBtn();
		schedulePage.verifyTMInTheOfferList(firstNameOfTM1, "offered");
*/		//===================================
        shiftOperatePage.closeViewStatusContainer();

        //Go to set back the config.
        option = "Always";
        controlsNewUIPage.clickOnControlsConsoleMenu();
        controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
        SimpleUtils.assertOnFail("Controls Page: Schedule Collaboration Section not Loaded.", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), true);
        controlsNewUIPage.updateOpenShiftApprovedByManagerOption(option);
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify Offer Team Members is disabled in past days")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyOfferTMDisabledInPastDaysForOpenShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try {
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
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToPreviousWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            scheduleMainPage.saveSchedule();
            String workRoleOfTM = shiftOperatePage.getRandomWorkRole();

            //create auto open shifts.
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.selectWorkRole(workRoleOfTM);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            //create manual open shift.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.selectWorkRole(workRoleOfTM);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.verifySelectTeamMembersOption();
            newShiftPage.clickOnOfferOrAssignBtn();
            //convert a shift to open shift.
            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.clickOnConvertToOpenShift();
            schedulePage.convertToOpenShiftDirectly();
            scheduleMainPage.saveSchedule();
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();



            loginAsDifferentRole(AccessRoles.StoreManager.getValue());
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            schedulePage.navigateToPreviousWeek();

            //verify Offer TM option is disabled.
            schedulePage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Open");
            schedulePage.clickProfileIconOfShiftByIndex(0);
            SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
            SimpleUtils.assertOnFail("Offer TMs option should be disabled!", !schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.clickProfileIconOfShiftByIndex(0);

            schedulePage.clickProfileIconOfShiftByIndex(1);
            SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
            SimpleUtils.assertOnFail("Offer TMs option should be disabled!", !schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.clickProfileIconOfShiftByIndex(1);

            schedulePage.clickProfileIconOfShiftByIndex(2);
            SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
            SimpleUtils.assertOnFail("Offer TMs option should be disabled!", !schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.clickProfileIconOfShiftByIndex(2);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
