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
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            schedulePage.saveSchedule();
            List<String> shiftInfo = new ArrayList<>();
            while (shiftInfo.size() == 0) {
                shiftInfo = schedulePage.getTheShiftInfoByIndex(0);
            }
            String firstNameOfTM = shiftInfo.get(0);
            String workRoleOfTM = shiftInfo.get(4);


            //verify assigned shift in non-edit mode for week view and day view
            schedulePage.clickOnProfileIcon();
            SimpleUtils.assertOnFail("Offer TMs option should be visible!", !schedulePage.isOfferTMOptionVisible(), false);
            schedulePage.clickOnDayView();
            schedulePage.clickOnProfileIconOfShiftInDayView("no");
            SimpleUtils.assertOnFail("Offer TMs option should be visible!", !schedulePage.isOfferTMOptionVisible(), false);
            schedulePage.clickOnWeekView();

            //verify assigned shift in edit mode for week view and day view
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnProfileIcon();
            SimpleUtils.assertOnFail("Offer TMs option should be visible!", !schedulePage.isOfferTMOptionVisible(), false);
            schedulePage.clickOnDayView();
            schedulePage.clickOnProfileIconOfShiftInDayView("no");
            SimpleUtils.assertOnFail("Offer TMs option should be visible!", !schedulePage.isOfferTMOptionVisible(), false);
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            //create auto open shifts.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.selectWorkRole(workRoleOfTM);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();

            //verify auto open shift in edit mode.
            //--verify in day view
            schedulePage.clickOnProfileIconOfShiftInDayView("open");
            SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            //--verify in week view
            schedulePage.clickOnWeekView();
            schedulePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.saveSchedule();
            //verify auto open shift in non-edit mode.
            //--verify in day view
            schedulePage.clickOnDayView();
            schedulePage.clickOnProfileIconOfShiftInDayView("open");
            SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            //--verify in week view
            schedulePage.clickOnWeekView();
            schedulePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);

            //create manual open shifts.
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView("open");
            schedulePage.clickOnDayView();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.selectWorkRole(workRoleOfTM);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.verifySelectTeamMembersOption();
            schedulePage.clickOnOfferOrAssignBtn();
            //verify manual open shift in edit mode.
            //--verify in day view
            schedulePage.clickOnProfileIconOfShiftInDayView("open");
            SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            //--verify in week view
            schedulePage.clickOnWeekView();
            schedulePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.saveSchedule();
            //verify manual open shifts in non-edit mode.
            //--verify in day view
            schedulePage.clickOnDayView();
            schedulePage.clickOnProfileIconOfShiftInDayView("open");
            SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            //--verify in week view
            schedulePage.clickOnWeekView();
            schedulePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the functionality of \"Offer Team Members\" for Open Shift: Auto in non-Edit mode")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyFunctionalityOfOfferTMForAutoOpenShiftsInNonEditModeAsTeamMember(String browser, String username, String password, String location) throws Exception{
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            String fileName = "UsersCredentials.json";
            String workRoleOfTM = "";
            fileName = this.enterpriseName+fileName;
            if (this.enterpriseName.contains("cinemark-wkdy")){
                workRoleOfTM = "Team Member Corporate-Theatre";
            } else {//KendraScott2_Enterprise by default
                workRoleOfTM = scheduleWorkRoles.get("MOD");
            }

            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);

            Object[][] adminCredentials = userCredentials.get("InternalAdmin");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(adminCredentials[0][0]), String.valueOf(adminCredentials[0][1])
                    , String.valueOf(adminCredentials[0][2]));
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();



            //create auto open shifts.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.selectWorkRole(workRoleOfTM);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.saveSchedule();

            //verify auto open shift in non-edit mode.
            schedulePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.clickOnOfferTMOption();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            schedulePage.verifyRecommendedTableHasTM();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            schedulePage.searchTeamMemberByName(firstNameOfTM);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickViewStatusBtn();
            schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
            schedulePage.closeViewStatusContainer();
            loginPage.logOut();

            //login with TM.
            Object[][] TMCredentials = userCredentials.get("TeamMember");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(TMCredentials[0][0]), String.valueOf(TMCredentials[0][1])
                    , String.valueOf(TMCredentials[0][2]));
            schedulePage.clickOnScheduleConsoleMenuItem();
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
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            String fileName = "UsersCredentials.json";
            String workRoleOfTM = "";
            fileName = this.enterpriseName+fileName;
            if (this.enterpriseName.contains("cinemark-wkdy")){
                workRoleOfTM = "Team Member Corporate-Theatre";
            } else {//KendraScott2_Enterprise by default
                workRoleOfTM = scheduleWorkRoles.get("MOD");
            }

            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);

            Object[][] adminCredentials = userCredentials.get("InternalAdmin");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(adminCredentials[0][0]), String.valueOf(adminCredentials[0][1])
                    , String.valueOf(adminCredentials[0][2]));
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();

            //create auto open shifts.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            //schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("8","8");
            schedulePage.selectWorkRole(workRoleOfTM);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            WebElement selectedShift = schedulePage.clickOnProfileIconOfOpenShift();
            String selectedShiftId= selectedShift.getAttribute("id");
            int index = schedulePage.getShiftIndexById(selectedShiftId);

            //verify auto open shift in edit mode.
            List<String> shiftInfo = schedulePage.getTheShiftInfoByIndex(index);
            schedulePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.clickOnOfferTMOption();
            String shiftInfoInWindows = schedulePage.getViewStatusShiftsInfo();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            schedulePage.verifyRecommendedTableHasTM();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            schedulePage.searchTeamMemberByName(firstNameOfTM);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickViewStatusBtn();
            schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
            schedulePage.closeViewStatusContainer();
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
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            String fileName = "UsersCredentials.json";
            String workRoleOfTM = "";
            fileName = this.enterpriseName+fileName;
            if (this.enterpriseName.contains("cinemark-wkdy")){
                workRoleOfTM = "Team Member Corporate-Theatre";
            } else {//KendraScott2_Enterprise by default
                workRoleOfTM = scheduleWorkRoles.get("MOD");
            }

            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);

            Object[][] adminCredentials = userCredentials.get("InternalAdmin");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(adminCredentials[0][0]), String.valueOf(adminCredentials[0][1])
                    , String.valueOf(adminCredentials[0][2]));
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();



            //create manual open shifts.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.selectWorkRole(workRoleOfTM);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.verifySelectTeamMembersOption();
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();

            //verify manual open shift in non-edit mode.
            schedulePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.clickOnOfferTMOption();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            schedulePage.verifyRecommendedTableHasTM();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            schedulePage.searchTeamMemberByNameNLocation(firstNameOfTM, location);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickViewStatusBtn();
            schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
            schedulePage.closeViewStatusContainer();
            loginPage.logOut();

            //login with TM.
            Object[][] TMCredentials = userCredentials.get("TeamMember");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(TMCredentials[0][0]), String.valueOf(TMCredentials[0][1])
                    , String.valueOf(TMCredentials[0][2]));
            schedulePage.clickOnScheduleConsoleMenuItem();
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
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            String fileName = "UsersCredentials.json";
            String workRoleOfTM = "";
            fileName = this.enterpriseName+fileName;
            if (this.enterpriseName.contains("cinemark-wkdy")){
                workRoleOfTM = "Team Member Corporate-Theatre";
            } else {//KendraScott2_Enterprise by default
                workRoleOfTM = scheduleWorkRoles.get("MOD");
            }

            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);

            Object[][] adminCredentials = userCredentials.get("InternalAdmin");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(adminCredentials[0][0]), String.valueOf(adminCredentials[0][1])
                    , String.valueOf(adminCredentials[0][2]));
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();

            //create manual open shifts.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            //schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("8","8");
            schedulePage.selectWorkRole(workRoleOfTM);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.verifySelectTeamMembersOption();
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            WebElement selectedShift = schedulePage.clickOnProfileIconOfOpenShift();
            String selectedShiftId= selectedShift.getAttribute("id");
            int index = schedulePage.getShiftIndexById(selectedShiftId);

            //verify manual open shift in edit mode.
            List<String> shiftInfo = schedulePage.getTheShiftInfoByIndex(index);
            schedulePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.clickOnOfferTMOption();
            String shiftInfoInWindows = schedulePage.getViewStatusShiftsInfo();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            schedulePage.verifyRecommendedTableHasTM();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            schedulePage.searchTeamMemberByNameNLocation(firstNameOfTM, location);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickViewStatusBtn();
            schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
            schedulePage.closeViewStatusContainer();
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
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            String fileName = "UsersCredentials.json";
            String workRoleOfTM = "";
            fileName = this.enterpriseName+fileName;
            if (this.enterpriseName.contains("cinemark-wkdy")){
                workRoleOfTM = "Team Member Corporate-Theatre";
            } else {//KendraScott2_Enterprise by default
                workRoleOfTM = scheduleWorkRoles.get("MOD");
            }

            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);

            Object[][] adminCredentials = userCredentials.get("InternalAdmin");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(adminCredentials[0][0]), String.valueOf(adminCredentials[0][1])
                    , String.valueOf(adminCredentials[0][2]));
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM);
            schedulePage.saveSchedule();

            //convert a shift to open shift
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            schedulePage.selectWorkRoleFilterByText(workRoleOfTM, true);
            schedulePage.clickOnProfileIcon();
            schedulePage.clickOnConvertToOpenShift();
            schedulePage.convertToOpenShiftDirectly();
            schedulePage.saveSchedule();

            //verify the open shift in non-edit mode.
            schedulePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.clickOnOfferTMOption();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            schedulePage.verifyRecommendedTableHasTM();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            schedulePage.searchTeamMemberByNameNLocation(firstNameOfTM, location);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickViewStatusBtn();
            schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
            schedulePage.closeViewStatusContainer();
            loginPage.logOut();

            //login with TM.
            Object[][] TMCredentials = userCredentials.get("TeamMember");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(TMCredentials[0][0]), String.valueOf(TMCredentials[0][1])
                    , String.valueOf(TMCredentials[0][2]));
            schedulePage.clickOnScheduleConsoleMenuItem();
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
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            String fileName = "UsersCredentials.json";
            String workRoleOfTM = "";
            fileName = this.enterpriseName+fileName;
            if (this.enterpriseName.contains("cinemark-wkdy")){
                workRoleOfTM = "Team Member Corporate-Theatre";
            } else {//KendraScott2_Enterprise by default
                workRoleOfTM = scheduleWorkRoles.get("MOD");
            }

            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);

            Object[][] adminCredentials = userCredentials.get("InternalAdmin");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(adminCredentials[0][0]), String.valueOf(adminCredentials[0][1])
                    , String.valueOf(adminCredentials[0][2]));
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM);
            schedulePage.saveSchedule();

            //convert a shift to open shift
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            schedulePage.selectWorkRoleFilterByText(workRoleOfTM, true);
            schedulePage.clickOnProfileIcon();
            schedulePage.clickOnConvertToOpenShift();
            schedulePage.convertToOpenShiftDirectly();
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            WebElement selectedShift = schedulePage.clickOnProfileIconOfOpenShift();
            String selectedShiftId= selectedShift.getAttribute("id");
            int index = schedulePage.getShiftIndexById(selectedShiftId);

            //verify manual open shift in edit mode.
            List<String> shiftInfo = schedulePage.getTheShiftInfoByIndex(index);
            schedulePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.clickOnOfferTMOption();
            String shiftInfoInWindows = schedulePage.getViewStatusShiftsInfo();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            schedulePage.verifyRecommendedTableHasTM();
            schedulePage.switchSearchTMAndRecommendedTMsTab();
            schedulePage.searchTeamMemberByNameNLocation(firstNameOfTM, location);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickViewStatusBtn();
            schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
            schedulePage.closeViewStatusContainer();
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
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            String fileName = "UsersCredentials.json";
            String workRoleOfTM = "";
            fileName = this.enterpriseName+fileName;
            if (this.enterpriseName.contains("cinemark-wkdy")){
                workRoleOfTM = "Team Member Corporate-Theatre";
            } else {//KendraScott2_Enterprise by default
                workRoleOfTM = scheduleWorkRoles.get("MOD");
            }

            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);

            Object[][] adminCredentials = userCredentials.get("InternalAdmin");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(adminCredentials[0][0]), String.valueOf(adminCredentials[0][1])
                    , String.valueOf(adminCredentials[0][2]));

            // 1.Checking configuration in controls
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
            boolean isScheduleCollaboration = controlsNewUIPage.isControlsScheduleCollaborationLoaded();
            SimpleUtils.assertOnFail("Controls Page: Schedule Collaboration Section not Loaded.", isScheduleCollaboration, true);
            String option = "Always";
            controlsNewUIPage.updateOpenShiftApprovedByManagerOption(option);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM);

            //create manual open shifts.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.moveSliderAtSomePoint("8", 20, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.selectWorkRole(workRoleOfTM);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.saveSchedule();
            WebElement selectedShift = schedulePage.clickOnProfileIconOfOpenShift();
            String selectedShiftId= selectedShift.getAttribute("id");
            int index = schedulePage.getShiftIndexById(selectedShiftId);


            List<String> shiftInfo = schedulePage.getTheShiftInfoByIndex(index);
            schedulePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.clickOnOfferTMOption();
            schedulePage.searchTeamMemberByNameNLocation(firstNameOfTM, location);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickViewStatusBtn();
            schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
            schedulePage.closeViewStatusContainer();
            loginPage.logOut();

            //login with TM.
            Object[][] TMCredentials = userCredentials.get("TeamMember");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(TMCredentials[0][0]), String.valueOf(TMCredentials[0][1])
                    , String.valueOf(TMCredentials[0][2]));
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.navigateToNextWeek();
            schedulePage.clickLinkOnSmartCardByName("View Shifts");
            SimpleUtils.assertOnFail("Didn't get open shift offer!", schedulePage.getShiftsCount()==1, false);
            List<String> shiftInfoFromTMView = schedulePage.getTheShiftInfoInDayViewByIndex(0);
            SimpleUtils.assertOnFail("shift info is not consistent！Shift info: "+shiftInfo.get(2)+" "+shiftInfo.get(4)+"TM Shift info: "+shiftInfoFromTMView.get(2)+" "+shiftInfoFromTMView.get(4), shiftInfo.get(2).contains(shiftInfoFromTMView.get(2)) && shiftInfoFromTMView.get(4).contains(shiftInfo.get(4)), false);

            List<String> claimShift = new ArrayList<>(Arrays.asList("View Offer"));
            schedulePage.selectOneShiftIsClaimShift(claimShift);
            schedulePage.clickTheShiftRequestByName(claimShift.get(0));
            schedulePage.verifyClickAgreeBtnOnClaimShiftOffer();
            loginPage.logOut();

            Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
                    , String.valueOf(storeManagerCredentials[0][2]));
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftOffer.getValue(), ActivityTest.indexOfActivityType.ShiftOffer.name());
            activityPage.verifyActivityOfShiftOffer(firstNameOfTM, location);
            activityPage.approveOrRejectShiftOfferRequestOnActivity(firstNameOfTM, ActivityTest.approveRejectAction.Approve.getValue());
            activityPage.closeActivityWindow();

            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            schedulePage.navigateToNextWeek();
            shiftInfo = schedulePage.getTheShiftInfoByIndex(index);
            SimpleUtils.assertOnFail("Open shift is not assigned successfully!", shiftInfo.get(0).equalsIgnoreCase(firstNameOfTM), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the open shift is not assigned if SM rejected")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheOpenShiftIsNotAssignedIfSMRejectedAsTeamMember(String browser, String username, String password, String location) throws Exception{
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            String fileName = "UsersCredentials.json";
            String workRoleOfTM = "";
            fileName = this.enterpriseName+fileName;
            if (this.enterpriseName.contains("cinemark-wkdy")){
                workRoleOfTM = "Team Member Corporate-Theatre";
            } else {//KendraScott2_Enterprise by default
                workRoleOfTM = scheduleWorkRoles.get("MOD");
            }

            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);

            Object[][] adminCredentials = userCredentials.get("InternalAdmin");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(adminCredentials[0][0]), String.valueOf(adminCredentials[0][1])
                    , String.valueOf(adminCredentials[0][2]));
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
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM);

            //create auto open shifts.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.moveSliderAtSomePoint("8", 20, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.selectWorkRole(workRoleOfTM);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.saveSchedule();
            WebElement selectedShift = schedulePage.clickOnProfileIconOfOpenShift();
            String selectedShiftId= selectedShift.getAttribute("id");
            int index = schedulePage.getShiftIndexById(selectedShiftId);


            List<String> shiftInfo = schedulePage.getTheShiftInfoByIndex(index);
            schedulePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.clickOnOfferTMOption();
            schedulePage.searchTeamMemberByNameNLocation(firstNameOfTM, location);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickViewStatusBtn();
            schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
            schedulePage.closeViewStatusContainer();
            loginPage.logOut();

            //login with TM.
            Object[][] TMCredentials = userCredentials.get("TeamMember");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(TMCredentials[0][0]), String.valueOf(TMCredentials[0][1])
                    , String.valueOf(TMCredentials[0][2]));
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.navigateToNextWeek();
            schedulePage.clickLinkOnSmartCardByName("View Shifts");
            SimpleUtils.assertOnFail("Didn't get open shift offer!", schedulePage.getShiftsCount()==1, false);
            List<String> shiftInfoFromTMView = schedulePage.getTheShiftInfoInDayViewByIndex(0);
            SimpleUtils.assertOnFail("shift info is not consistent", shiftInfo.get(2).contains(shiftInfoFromTMView.get(2)) && shiftInfoFromTMView.get(4).contains(shiftInfo.get(4)), false);

            List<String> claimShift = new ArrayList<>(Arrays.asList("View Offer"));
            schedulePage.selectOneShiftIsClaimShift(claimShift);
            schedulePage.clickTheShiftRequestByName(claimShift.get(0));
            schedulePage.verifyClickAgreeBtnOnClaimShiftOffer();
            loginPage.logOut();

            Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
                    , String.valueOf(storeManagerCredentials[0][2]));
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
            loginToLegionAndVerifyIsLoginDone(String.valueOf(TMCredentials[0][0]), String.valueOf(TMCredentials[0][1])
                    , String.valueOf(TMCredentials[0][2]));
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.navigateToNextWeek();
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Open");
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
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String firstNameOfTM1 = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            //log in as TM2 to get nickname.
            String fileName = "UsersCredentials.json";
            String workRoleOfTM = "";
            fileName = this.enterpriseName+fileName;
            if (this.enterpriseName.contains("cinemark-wkdy")){
                workRoleOfTM = "Team Member Corporate-Theatre";
            } else {//KendraScott2_Enterprise by default
                workRoleOfTM = scheduleWorkRoles.get("MOD");
            }
            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);

            Object[][] TM2Credentials = userCredentials.get("TeamMember2");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(TM2Credentials[0][0]), String.valueOf(TM2Credentials[0][1])
                    , String.valueOf(TM2Credentials[0][2]));
            String firstNameOfTM2 = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();


            Object[][] adminCredentials = userCredentials.get("InternalAdmin");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(adminCredentials[0][0]), String.valueOf(adminCredentials[0][1])
                    , String.valueOf(adminCredentials[0][2]));
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
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM2);

            //create manual open shifts.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.moveSliderAtSomePoint("8", 20, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.selectWorkRole(workRoleOfTM);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.saveSchedule();


            schedulePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickOnOfferTMOption();
            schedulePage.searchTeamMemberByNameNLocation(firstNameOfTM1, location);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickOnOfferTMOption();
            schedulePage.searchTeamMemberByNameNLocation(firstNameOfTM2, location);
            schedulePage.clickOnOfferOrAssignBtn();
            loginPage.logOut();

            //login with TM1 to claim.
            Object[][] TMCredentials = userCredentials.get("TeamMember");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(TMCredentials[0][0]), String.valueOf(TMCredentials[0][1])
                    , String.valueOf(TMCredentials[0][2]));
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.navigateToNextWeek();
            schedulePage.clickLinkOnSmartCardByName("View Shifts");
            List<String> claimShift = new ArrayList<>(Arrays.asList("Claim Shift"));
            schedulePage.selectOneShiftIsClaimShift(claimShift);
            schedulePage.clickTheShiftRequestByName(claimShift.get(0));
            schedulePage.verifyClickAgreeBtnOnClaimShiftOffer();
            loginPage.logOut();

            //login with TM2 to claim.
            loginToLegionAndVerifyIsLoginDone(String.valueOf(TM2Credentials[0][0]), String.valueOf(TM2Credentials[0][1])
                    , String.valueOf(TM2Credentials[0][2]));
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.navigateToNextWeek();
            schedulePage.clickLinkOnSmartCardByName("View Shifts");
            claimShift = new ArrayList<>(Arrays.asList("Claim Shift"));
            schedulePage.selectOneShiftIsClaimShift(claimShift);
            schedulePage.clickTheShiftRequestByName(claimShift.get(0));
            schedulePage.verifyClickAgreeBtnOnClaimShiftOffer();
            loginPage.logOut();

            //log in as SM to check.
            Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
                    , String.valueOf(storeManagerCredentials[0][2]));
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftOffer.getValue(), ActivityTest.indexOfActivityType.ShiftOffer.name());
            activityPage.verifyActivityOfShiftOffer(firstNameOfTM2, location);
            activityPage.approveOrRejectShiftOfferRequestOnActivity(firstNameOfTM1, ActivityTest.approveRejectAction.Approve.getValue());
            activityPage.verifyApproveShiftOfferRequestAndGetErrorOnActivity(firstNameOfTM1);
            String expectedTopMessage = "Error! Failed to Approve";
            schedulePage.verifyThePopupMessageOnTop(expectedTopMessage);
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
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String firstNameOfTM1 = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            String workRoleOfTM = "";
            String fileName = "UsersCredentials.json";
            fileName = this.enterpriseName+fileName;
            if (this.enterpriseName.contains("cinemark-wkdy")){
                workRoleOfTM = "Team Member Corporate-Theatre";
            } else {//KendraScott2_Enterprise by default
                workRoleOfTM = scheduleWorkRoles.get("MOD");
            }

            //log in as TM2 to get nickname.
            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
            Object[][] TM2Credentials = userCredentials.get("TeamMember2");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(TM2Credentials[0][0]), String.valueOf(TM2Credentials[0][1])
                    , String.valueOf(TM2Credentials[0][2]));
            String firstNameOfTM2 = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            Object[][] adminCredentials = userCredentials.get("InternalAdmin");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(adminCredentials[0][0]), String.valueOf(adminCredentials[0][1])
                    , String.valueOf(adminCredentials[0][2]));
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
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM2);

            //create manual open shifts.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.moveSliderAtSomePoint("8", 20, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.selectWorkRole(workRoleOfTM);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.saveSchedule();


            schedulePage.clickOnProfileIconOfOpenShift();
            SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
            schedulePage.clickOnOfferTMOption();
            schedulePage.searchTeamMemberByNameNLocation(firstNameOfTM1, location);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickOnOfferTMOption();
            schedulePage.searchTeamMemberByNameNLocation(firstNameOfTM2, location);
            schedulePage.clickOnOfferOrAssignBtn();
            loginPage.logOut();

            //login with TM1 to claim.
            Object[][] TMCredentials = userCredentials.get("TeamMember");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(TMCredentials[0][0]), String.valueOf(TMCredentials[0][1])
                    , String.valueOf(TMCredentials[0][2]));
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.navigateToNextWeek();
            schedulePage.clickLinkOnSmartCardByName("View Shifts");
            List<String> claimShift = new ArrayList<>(Arrays.asList("View Offer"));
            schedulePage.selectOneShiftIsClaimShift(claimShift);
            schedulePage.clickTheShiftRequestByName(claimShift.get(0));
            schedulePage.verifyClickAgreeBtnOnClaimShiftOfferWhenDontNeedApproval();
            loginPage.logOut();

            //login with TM2 to claim.
            loginToLegionAndVerifyIsLoginDone(String.valueOf(TM2Credentials[0][0]), String.valueOf(TM2Credentials[0][1])
                    , String.valueOf(TM2Credentials[0][2]));
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.navigateToNextWeek();
            schedulePage.clickLinkOnSmartCardByName("View Shifts");
            SimpleUtils.assertOnFail("shouldn't get any open shift offer!", schedulePage.getShiftsCount()==0, false);
            loginPage.logOut();

            //log in as AM to set back the config.
            loginToLegionAndVerifyIsLoginDone(String.valueOf(adminCredentials[0][0]), String.valueOf(adminCredentials[0][1])
                    , String.valueOf(adminCredentials[0][2]));
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
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            LoginPage loginPage = pageFactory.createConsoleLoginPage();

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String firstNameOfTM1 = profileNewUIPage.getNickNameFromProfile();
            loginPage.logOut();

            String workRoleOfTM = "";
            String fileName = "UsersCredentials.json";
            fileName = this.enterpriseName+fileName;
            if (this.enterpriseName.contains("cinemark-wkdy")){
                workRoleOfTM = "Team Member Corporate-Theatre";
            } else {//KendraScott2_Enterprise by default
                workRoleOfTM = scheduleWorkRoles.get("MOD");
            }

            //log in as admin.
            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
            Object[][] adminCredentials = userCredentials.get("InternalAdmin");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(adminCredentials[0][0]), String.valueOf(adminCredentials[0][1])
                    , String.valueOf(adminCredentials[0][2]));
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
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);

            //create manual open shifts.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.moveSliderAtSomePoint("8", 20, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.selectWorkRole(workRoleOfTM);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM1);
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();

            WebElement openShift = schedulePage.clickOnProfileIconOfOpenShift();
            String selectedShiftId= openShift.getAttribute("id");
            int index = schedulePage.getShiftIndexById(selectedShiftId);
            schedulePage.clickViewStatusBtn();
            schedulePage.verifyTMInTheOfferList(firstNameOfTM1, "draft");
            schedulePage.closeViewStatusContainer();
            schedulePage.publishActiveSchedule();
            schedulePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickViewStatusBtn();
            schedulePage.verifyTMInTheOfferList(firstNameOfTM1, "offered");
            schedulePage.closeViewStatusContainer();
            loginPage.logOut();

            //login with TM to claim.
            Object[][] TMCredentials = userCredentials.get("TeamMember");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(TMCredentials[0][0]), String.valueOf(TMCredentials[0][1])
                    , String.valueOf(TMCredentials[0][2]));
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.navigateToNextWeek();
            schedulePage.clickLinkOnSmartCardByName("View Shifts");
            List<String> claimShift = new ArrayList<>(Arrays.asList("View Offer"));
            schedulePage.selectOneShiftIsClaimShift(claimShift);
            schedulePage.clickTheShiftRequestByName(claimShift.get(0));
            schedulePage.verifyClickAgreeBtnOnClaimShiftOfferWhenDontNeedApproval();
            loginPage.logOut();

            //log in as SM to check the shift
            Object[][] SMCredentials = userCredentials.get("StoreManager");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(SMCredentials[0][0]), String.valueOf(SMCredentials[0][1])
                    , String.valueOf(SMCredentials[0][2]));
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            List<String> shiftInfo = schedulePage.getTheShiftInfoByIndex(index);
            SimpleUtils.assertOnFail("Open shift is not assigned successfully!", shiftInfo.get(0).equalsIgnoreCase(firstNameOfTM1), false);

            //convert to open shift.
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickProfileIconOfShiftByIndex(index);
            schedulePage.clickOnConvertToOpenShift();
            schedulePage.convertToOpenShiftDirectly();
            schedulePage.saveSchedule();
            schedulePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickViewStatusBtn();
/*        //=============SCH-3418=============
        schedulePage.verifyTMInTheOfferList(firstNameOfTM1, "accepted");
        schedulePage.closeViewStatusContainer();
		schedulePage.clickOnProfileIconOfOpenShift();
		schedulePage.clickOnOfferTMOption();
		schedulePage.searchTeamMemberByNameNLocation(firstNameOfTM1, location);
		schedulePage.clickOnOfferOrAssignBtn();
		schedulePage.clickOnProfileIconOfOpenShift();
		schedulePage.clickViewStatusBtn();
		schedulePage.verifyTMInTheOfferList(firstNameOfTM1, "offered");
*/		//===================================
            schedulePage.closeViewStatusContainer();

            //Go to set back the config.
            option = "Always";
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
    @TestName(description = "Verify Offer Team Members is disabled in past days")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyOfferTMDisabledInPastDaysForOpenShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToPreviousWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();

            //delete unassigned shifts and open shifts.
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Action Required");
            //schedulePage.deleteTMShiftInWeekView("Unassigned");
            //Delete all shifts are action required.
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Open");
            schedulePage.deleteTMShiftInWeekView("");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnClearFilterOnFilterDropdownPopup();
            schedulePage.saveSchedule();
            List<String> shiftInfo = new ArrayList<>();
            while (shiftInfo.size() == 0) {
                shiftInfo = schedulePage.getTheShiftInfoByIndex(0);
            }
            String workRoleOfTM = shiftInfo.get(4);

            //create auto open shifts.
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.selectWorkRole(workRoleOfTM);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            //create manual open shift.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.selectWorkRole(workRoleOfTM);
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.verifySelectTeamMembersOption();
            schedulePage.clickOnOfferOrAssignBtn();
            //convert a shift to open shift.
            schedulePage.clickOnProfileIcon();
            schedulePage.clickOnConvertToOpenShift();
            schedulePage.convertToOpenShiftDirectly();
            schedulePage.saveSchedule();
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();



            String fileName = "UsersCredentials.json";
            fileName = this.enterpriseName+fileName;
            //log in as admin.
            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);

            Object[][] SMCredentials = userCredentials.get("StoreManager");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(SMCredentials[0][0]), String.valueOf(SMCredentials[0][1])
                    , String.valueOf(SMCredentials[0][2]));
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            schedulePage.navigateToPreviousWeek();

            //verify Offer TM option is disabled.
            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Open");
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
