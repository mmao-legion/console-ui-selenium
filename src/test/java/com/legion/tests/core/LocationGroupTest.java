package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class LocationGroupTest extends TestBase {

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
    @TestName(description = "Validate the generation of LG schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheGenerationOfLGScheduleAsInternalAdmin(String username, String password, String browser, String location)
            throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict("District Whistler");
        locationSelectorPage.changeLocation("Lift Ops_Parent");
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        schedulePage.navigateToNextWeek();
        boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
        if(isActiveWeekGenerated){
            schedulePage.unGenerateActiveScheduleScheduleWeek();
        }

        // Edit random location's operating hours before generate schedule
        schedulePage.selectRandomOrSpecificLocationOnUngenerateScheduleEditOperatingHoursPage(null);
        List<String> toCloseDays = new ArrayList<>();
        schedulePage.editOperatingHoursOnScheduleOldUIPage("8", "20", toCloseDays);
        // Edit random location's operating hours during generate schedule
        schedulePage.createScheduleForNonDGFlowNewUI();
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate manager cannot edit operating hours when disable it's Manage Working Hours Setting permission")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateManagerCannotEditOperatingHoursWhenDisableItsManageWorkingHoursSettingPermissionAsInternalAdmin(String username, String password, String browser, String location)
            throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict("District Whistler");
        locationSelectorPage.changeLocation("Lift Ops_Parent");

        //Log in as admin, uncheck the Working Hours Setting Permission to SM.
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        CinemarkMinorPage cinemarkMinorPage = pageFactory.createConsoleCinemarkMinorPage();
        controlsNewUIPage.clickOnControlsConsoleMenu();
        controlsNewUIPage.clickOnGlobalLocationButton();
        controlsNewUIPage.clickOnControlsUsersAndRolesSection();
        controlsNewUIPage.selectUsersAndRolesSubTabByLabel(ControlsNewUITest.usersAndRolesSubTabs.AccessByJobTitles.getValue());

        String permissionSection = "Controls";
        String permission = "Controls: Manage Working Hours Settings";
        String actionOff = "off";
        String actionOn = "on";
        cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Edit.getValue());
        controlsNewUIPage.turnOnOrOffSpecificPermissionForSM(permissionSection, permission, actionOff);
        cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Save.getValue());
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();

        //Log in as SM
        String fileName = "UsersCredentials.json";
        fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] storeManagerCredentials = userCredentials.get("StoreManagerLG");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
                , String.valueOf(storeManagerCredentials[0][2]));
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

        // Check SM cannot edit operating hours now
        schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        schedulePage.navigateToNextWeek();
        boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
        if(isActiveWeekGenerated){
            schedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        //Check the edit buttons on ungenerate schedule page
        SimpleUtils.assertOnFail("Edit operating hours buttons are shown on ungenerate schedule page! ",
                !schedulePage.checkIfEditOperatingHoursButtonsAreShown() , false);

        //Check the edit button on create schedule page
        schedulePage.clickCreateScheduleBtn();
        SimpleUtils.assertOnFail("Edit operating hours buttons are shown on ungenerate schedule page! ",
                !schedulePage.checkIfEditOperatingHoursButtonsAreShown() , false);
        schedulePage.clickExitBtnToExitCreateScheduleWindow();
        loginPage.logOut();

        //Log in as admin, grant the Working Hours Setting Permission to SM.
        storeManagerCredentials = userCredentials.get("InternalAdmin");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
                , String.valueOf(storeManagerCredentials[0][2]));
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        locationSelectorPage.changeDistrict("District Whistler");
        locationSelectorPage.changeLocation("Lift Ops_Parent");

        controlsNewUIPage.clickOnControlsConsoleMenu();
        controlsNewUIPage.clickOnGlobalLocationButton();
        controlsNewUIPage.clickOnControlsUsersAndRolesSection();
        controlsNewUIPage.selectUsersAndRolesSubTabByLabel(ControlsNewUITest.usersAndRolesSubTabs.AccessByJobTitles.getValue());
        cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Edit.getValue());
        controlsNewUIPage.turnOnOrOffSpecificPermissionForSM(permissionSection, permission, actionOn);
        cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Save.getValue());
        loginPage.logOut();

        // Check SM cannot edit operating hours now
        schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        schedulePage.navigateToNextWeek();
        isActiveWeekGenerated = schedulePage.isWeekGenerated();
        if(isActiveWeekGenerated){
            schedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        //Check the edit buttons on ungenerate schedule page
        SimpleUtils.assertOnFail("Edit operating hours buttons are shown on ungenerate schedule page! ",
                schedulePage.checkIfEditOperatingHoursButtonsAreShown() , false);

        //Check the edit button on create schedule page
        schedulePage.clickCreateScheduleBtn();
        SimpleUtils.assertOnFail("Edit operating hours buttons are shown on ungenerate schedule page! ",
                schedulePage.checkIfEditOperatingHoursButtonsAreShown() , false);

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate all smart cards display correctly after generate schedule ")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateAllSmartCardsDisplayCorrectlyAfterGenerateScheduleAsInternalAdmin (String username, String password, String browser, String location)
            throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict("District Whistler");
        locationSelectorPage.changeLocation("Lift Ops_Parent");
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        schedulePage.navigateToNextWeek();
        schedulePage.navigateToNextWeek();
        boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
        if(isActiveWeekGenerated){
            schedulePage.unGenerateActiveScheduleScheduleWeek();
        }

        schedulePage.selectRandomOrSpecificLocationOnUngenerateScheduleEditOperatingHoursPage("Child1");
        List<String> toCloseDays = new ArrayList<>();
        schedulePage.editOperatingHoursOnScheduleOldUIPage("5", "23", toCloseDays);
        // Edit random location's operating hours during generate schedule
        schedulePage.createScheduleForNonDGFlowNewUI();

        schedulePage.clickOnFilterBtn();
        schedulePage.selectShiftTypeFilterByText("Child1");
        schedulePage.clickOnFilterBtn();

        //Check Schedule not published smart card is display
        SimpleUtils.assertOnFail("Schedule not published smart card should display for new generate schedule! ",
                schedulePage.verifyScheduleNotPublishedSmartCardShowing(),false);

        schedulePage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyLocation.getValue());
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.clickOnProfileIcon();
        schedulePage.clickOnEditShiftTime();
        schedulePage.verifyEditShiftTimePopUpDisplay();
        schedulePage.editShiftTimeToTheLargest();
        schedulePage.clickOnUpdateEditShiftTimeButton();
        schedulePage.saveSchedule();
        //verify Compliance SmartCard Functionality
        if(schedulePage.verifyComplianceShiftsSmartCardShowing() && schedulePage.verifyRedFlagIsVisible()){
            schedulePage.verifyComplianceFilterIsSelectedAftClickingViewShift();
            schedulePage.verifyComplianceShiftsShowingInGrid();
            schedulePage.verifyClearFilterFunction();
        }else
            SimpleUtils.fail("There is no compliance and no red flag", false);

        //verifyScheduleFunctionalityScheduleSmartCard
        ArrayList<LinkedHashMap<String, Float>> scheduleOverviewAllWeekHours = new ArrayList<LinkedHashMap<String, Float>>();
        HashMap<String, Float> scheduleSmartCardHoursWages = new HashMap<>();
        HashMap<String, Float> overviewData = new HashMap<>();

        scheduleSmartCardHoursWages = schedulePage.getScheduleBudgetedHoursInScheduleSmartCard();
        SimpleUtils.report("scheduleSmartCardHoursWages :"+scheduleSmartCardHoursWages);
        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
        scheduleOverviewPage.clickOverviewTab();
        List<WebElement> scheduleOverViewWeeks =  scheduleOverviewPage.getOverviewScheduleWeeks();
        overviewData = scheduleOverviewPage.getWeekHoursByWeekElement(scheduleOverViewWeeks.get(2));
        SimpleUtils.report("overview data :"+scheduleOverviewPage.getWeekHoursByWeekElement(scheduleOverViewWeeks.get(2)));
        if (
//                Math.abs(overviewData.get("guidanceHours") - (scheduleSmartCardHoursWages.get("budgetedHours"))) <= 0.05 &&
                Math.abs(overviewData.get("scheduledHours") - (scheduleSmartCardHoursWages.get("scheduledHours"))) <= 0.05
                && Math.abs(overviewData.get("otherHours") - (scheduleSmartCardHoursWages.get("otherHours"))) <= 0.05) {
            SimpleUtils.pass("Schedule/Budgeted smartcard-is showing the values in Hours and wages, it is displaying the same data as overview page have for the current week .");
        }else {
            SimpleUtils.fail("Scheduled Hours and Overview Schedule Hours not same, hours on smart card for budget, scheduled and other are: " +
                    scheduleSmartCardHoursWages.get("budgetedHours") + ", " + scheduleSmartCardHoursWages.get("scheduledHours") + ", " + scheduleSmartCardHoursWages.get("otherHours")
                    + ". But hours on Overview page are: " + overviewData.get("guidanceHours") + ", " + overviewData.get("scheduledHours") + ", " + overviewData.get("otherHours"),false);
        }

    }
}
