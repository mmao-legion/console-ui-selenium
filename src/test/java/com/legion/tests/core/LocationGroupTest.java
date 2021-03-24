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
import java.util.*;

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
    public void validateTheGenerationOfLGScheduleForMSAsInternalAdmin(String username, String password, String browser, String location)
            throws Exception {
        try {
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
            schedulePage.editOperatingHoursOnScheduleOldUIPage("9", "17", toCloseDays);
            // Edit random location's operating hours during generate schedule
            schedulePage.createScheduleForNonDGFlowNewUI();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Validate the generation of LG schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheGenerationOfLGScheduleForP2PAsInternalAdmin(String username, String password, String browser, String location)
            throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if (isActiveWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.navigateToNextWeek();
            isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if (isActiveWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            // Edit random location's operating hours before generate schedule
            schedulePage.selectRandomOrSpecificLocationOnUngenerateScheduleEditOperatingHoursPage(null);
            List<String> toCloseDays = new ArrayList<>();
            schedulePage.editOperatingHoursOnScheduleOldUIPage("9am", "5pm", toCloseDays);
            // Edit random location's operating hours during generate schedule
            schedulePage.createScheduleForNonDGFlowNewUI();
            schedulePage.publishActiveSchedule();

            dashboardPage.clickOnDashboardConsoleMenu();
            locationSelectorPage.changeLocation("Mountain View");
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    !schedulePage.isWeekGenerated(), false);
            schedulePage.createScheduleForNonDGFlowNewUI();
            schedulePage.publishActiveSchedule();

            schedulePage.navigateToNextWeek();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.isWeekGenerated(), false);

            dashboardPage.clickOnDashboardConsoleMenu();
            locationSelectorPage.changeLocation("Carmel Club DG Oregon");
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    !schedulePage.isWeekGenerated(), false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate manager cannot edit operating hours when disable it's Manage Working Hours Setting permission")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateManagerCannotEditOperatingHoursWhenDisableItsManageWorkingHoursSettingPermissionForMSAsInternalAdmin(String username, String password, String browser, String location)
            throws Exception {
        try {
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
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Validate manager cannot edit operating hours when disable it's Manage Working Hours Setting permission")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateManagerCannotEditOperatingHoursWhenDisableItsManageWorkingHoursSettingPermissionForP2PAsInternalAdmin(String username, String password, String browser, String location)
            throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();

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

            //Log in as admin, uncheck the Working Hours Setting Permission to SM.

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
            fileName = SimpleUtils.getEnterprise("Coffee_Enterprise")+fileName;
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
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");

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
            //Check the edit buttons on ungenerate schedule page
            SimpleUtils.assertOnFail("Edit operating hours buttons are shown on ungenerate schedule page! ",
                    schedulePage.checkIfEditOperatingHoursButtonsAreShown() , false);

            //Check the edit button on create schedule page
            schedulePage.clickCreateScheduleBtn();
            SimpleUtils.assertOnFail("Edit operating hours buttons are shown on ungenerate schedule page! ",
                    schedulePage.checkIfEditOperatingHoursButtonsAreShown() , false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate all smart cards display correctly after generate schedule ")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateAllSmartCardsDisplayCorrectlyAfterGenerateScheduleForMSAsInternalAdmin (String username, String password, String browser, String location)
            throws Exception {
        try {
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
            schedulePage.editOperatingHoursOnScheduleOldUIPage("5am", "11pm", toCloseDays);
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
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Validate all smart cards display correctly after generate schedule ")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateAllSmartCardsDisplayCorrectlyAfterGenerateScheduleForP2PAsInternalAdmin (String username, String password, String browser, String location)
            throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");
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

            schedulePage.selectRandomOrSpecificLocationOnUngenerateScheduleEditOperatingHoursPage("Mountain View");
            List<String> toCloseDays = new ArrayList<>();
            schedulePage.editOperatingHoursOnScheduleOldUIPage("5am", "11pm", toCloseDays);
            // Edit random location's operating hours during generate schedule
            schedulePage.createScheduleForNonDGFlowNewUI();

            schedulePage.clickOnFilterBtn();
            schedulePage.selectShiftTypeFilterByText("Mountain View");
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

            //verify Schedule Functionality Schedule SmartCard
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
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the buttons on schedule page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheButtonsOnSchedulePageForMSAsInternalAdmin(String username, String password, String browser, String location)
            throws Exception {
        try {
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

            // Edit random location's operating hours during generate schedule
            schedulePage.createScheduleForNonDGFlowNewUI();

            // Check Edit button
            schedulePage.checkEditButton();
            schedulePage.verifyEditButtonFuntionality();

            // Check Publish button
            schedulePage.isPublishButtonLoadedOnSchedulePage();
            schedulePage.publishActiveSchedule();
            SimpleUtils.assertOnFail("Schedule should be published! ", schedulePage.isCurrentScheduleWeekPublished(), false);

            // Check Republish button
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnProfileIcon();
            schedulePage.clickOnEditShiftTime();
            schedulePage.verifyEditShiftTimePopUpDisplay();
            schedulePage.editShiftTime();
            schedulePage.clickOnUpdateEditShiftTimeButton();
            schedulePage.saveSchedule();
            SimpleUtils.assertOnFail("The Republish button should display! ", schedulePage.isRepublishButtonLoadedOnSchedulePage(), false);
            schedulePage.clickOnSchedulePublishButton();
            SimpleUtils.assertOnFail("Schedule should be published! ", schedulePage.isCurrentScheduleWeekPublished(), false);

            // Check Analyze button
            schedulePage.verifyAnalyzeBtnFunctionAndScheduleHistoryScroll();


            // Check Toggle Summary View
            schedulePage.goToToggleSummaryView();
            //verify the operating hours in Toggle Summary View
            schedulePage.checkIfEditOperatingHoursButtonsAreShown();
            schedulePage.clickToggleSummaryViewButton();

            // Ungenerate button
            schedulePage.unGenerateActiveScheduleScheduleWeek();
            SimpleUtils.assertOnFail("Schedule should been ungenerated", !schedulePage.isWeekGenerated(), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Validate the buttons on schedule page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheButtonsOnSchedulePageForP2PAsInternalAdmin(String username, String password, String browser, String location)
            throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");
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

            // Edit random location's operating hours during generate schedule
            schedulePage.createScheduleForNonDGFlowNewUI();

            // Check Edit button
            schedulePage.checkEditButton();
            schedulePage.verifyEditButtonFuntionality();

            // Check Publish button
            schedulePage.isPublishButtonLoadedOnSchedulePage();
            schedulePage.publishActiveSchedule();
            SimpleUtils.assertOnFail("Schedule should be published! ", schedulePage.isCurrentScheduleWeekPublished(), false);

            // Check Republish button
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnProfileIcon();
            schedulePage.clickOnEditShiftTime();
            schedulePage.verifyEditShiftTimePopUpDisplay();
            schedulePage.editShiftTime();
            schedulePage.clickOnUpdateEditShiftTimeButton();
            schedulePage.saveSchedule();
            SimpleUtils.assertOnFail("The Republish button should display! ", schedulePage.isRepublishButtonLoadedOnSchedulePage(), false);
            schedulePage.clickOnSchedulePublishButton();
            SimpleUtils.assertOnFail("Schedule should be published! ", schedulePage.isCurrentScheduleWeekPublished(), false);

            // Check Analyze button
            schedulePage.verifyAnalyzeBtnFunctionAndScheduleHistoryScroll();


            // Check Toggle Summary View
            schedulePage.goToToggleSummaryView();
            //verify the operating hours in Toggle Summary View
            schedulePage.checkIfEditOperatingHoursButtonsAreShown();
            schedulePage.clickToggleSummaryViewButton();

            // Ungenerate button
            schedulePage.unGenerateActiveScheduleScheduleWeek();
            SimpleUtils.assertOnFail("Schedule should been ungenerated", !schedulePage.isWeekGenerated(), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the group by dropdown list")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheGroupByDropdownListForMSAsInternalAdmin(String username, String password, String browser, String location)
            throws Exception {
        try {
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
            schedulePage.createScheduleForNonDGFlowNewUI();

            //Check schedule defaulted with group by location
//            SimpleUtils.assertOnFail("Schedule table should defaulted with group by location! ",schedulePage.getActiveGroupByFilter().equals("Group by Location"), false);
            //In week view, Group by All filter have 4 filters:1.Group by all  2. Group by work role  3. Group by TM 4.Group by job title
            schedulePage.validateGroupBySelectorSchedulePage(true);
            //Selecting any of them, check the schedule table
            schedulePage.validateScheduleTableWhenSelectAnyOfGroupByOptions(true);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Validate the group by dropdown list")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheGroupByDropdownListForP2PAsInternalAdmin(String username, String password, String browser, String location)
            throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");
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
            schedulePage.createScheduleForNonDGFlowNewUI();

            //Check schedule defaulted with group by location
//            SimpleUtils.assertOnFail("Schedule table should defaulted with group by location! ",schedulePage.getActiveGroupByFilter().equals("Group by Location"), false);
            //In week view, Group by All filter have 4 filters:1.Group by all  2. Group by work role  3. Group by TM 4.Group by job title
            schedulePage.validateGroupBySelectorSchedulePage(true);
            //Selecting any of them, check the schedule table
            schedulePage.validateScheduleTableWhenSelectAnyOfGroupByOptions(true);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Validate the function of auto open shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheFunctionOfAutoOpenShiftForP2PAsInternalAdmin (String username, String password, String browser, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            //schedulePage.navigateToNextWeek();
            //schedulePage.navigateToNextWeek();
            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if (!isActiveWeekGenerated) {
                schedulePage.createScheduleForNonDGFlowNewUI();
            }

            schedulePage.clickOnFilterBtn();
            schedulePage.selectChildLocationFilterByText("Mountain View");
            if (schedulePage.getShiftsCount()>0){
                schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                schedulePage.deleteTMShiftInWeekView("Open");
                schedulePage.saveSchedule();
            }
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            //Create auto open shift.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole("BARTENDER");
            schedulePage.selectChildLocInCreateShiftWindow("Mountain View");
            schedulePage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtSomePoint("28", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();


            //edit shift time
            WebElement selectedShift = schedulePage.clickOnProfileIconOfOpenShift();
            String selectedShiftId= selectedShift.getAttribute("id");
            int index = schedulePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoBefore = schedulePage.getTheShiftInfoByIndex(index);
            schedulePage.clickProfileIconOfShiftByIndex(index);
            schedulePage.clickOnEditShiftTime();
            schedulePage.verifyEditShiftTimePopUpDisplay();
            schedulePage.editShiftTimeToTheLargest();
            schedulePage.clickOnUpdateEditShiftTimeButton();
            index = schedulePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoAfter= schedulePage.getTheShiftInfoByIndex(index);
            SimpleUtils.assertOnFail("Auto shift time is not updated!", !shiftInfoBefore.containsAll(shiftInfoAfter), false);


            //change work role
            schedulePage.clickProfileIconOfShiftByIndex(index);
            schedulePage.clickOnChangeRole();
            schedulePage.verifyChangeRoleFunctionality();
            //check the work role by click Apply button
            schedulePage.changeWorkRoleInPromptOfAShift(true, selectedShift);
            index = schedulePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoAfterChangeRole= schedulePage.getTheShiftInfoByIndex(index);
            SimpleUtils.assertOnFail("Work role is not updated!", !shiftInfoBefore.containsAll(shiftInfoAfterChangeRole), false);


            //Edit meal break
            //schedulePage.clickProfileIconOfShiftByIndex(index);
            //schedulePage.verifyEditMealBreakTimeFunctionalityForAShift(true, selectedShift);

            //View status
            schedulePage.clickProfileIconOfShiftByIndex(index);
            schedulePage.clickViewStatusBtn();
            //schedulePage.verifyListOfOfferNotNull();
            schedulePage.closeViewStatusContainer();

            //Assign TM
            schedulePage.clickProfileIconOfShiftByIndex(index);
            schedulePage.clickonAssignTM();
            schedulePage.verifySelectTeamMembersOption();
            schedulePage.clickOnOfferOrAssignBtn();
            index = schedulePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoAfterAssignTM= schedulePage.getTheShiftInfoByIndex(index);
            SimpleUtils.assertOnFail("TM is not assigned!", !shiftInfoBefore.containsAll(shiftInfoAfterAssignTM), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the function of auto open shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheFunctionOfAutoOpenShiftForMSAsInternalAdmin (String username, String password, String browser, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("District Whistler");
            locationSelectorPage.changeLocation("Lift Ops_Parent");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            //schedulePage.navigateToNextWeek();
            //schedulePage.navigateToNextWeek();
            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if (!isActiveWeekGenerated) {
                schedulePage.createScheduleForNonDGFlowNewUI();
            }

            schedulePage.clickOnFilterBtn();
            schedulePage.selectChildLocationFilterByText("Child1");
            schedulePage.clickOnFilterBtn();
            if (schedulePage.getShiftsCount()>0){
                schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                schedulePage.deleteTMShiftInWeekView("Open");
                schedulePage.saveSchedule();
            }
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            //Create auto open shift.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole("LIFT OPERATOR");
            schedulePage.selectChildLocInCreateShiftWindow("Child1");
            schedulePage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtSomePoint("28", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();

            //Edit meal break
            WebElement selectedShift = null;
            selectedShift = schedulePage.clickOnProfileIconOfOpenShift();
            schedulePage.verifyEditMealBreakTimeFunctionalityForAShift(true, selectedShift);

            //edit shift time

            selectedShift = schedulePage.clickOnProfileIconOfOpenShift();
            String selectedShiftId= selectedShift.getAttribute("id");
            int index = schedulePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoBefore = schedulePage.getTheShiftInfoByIndex(index);
            schedulePage.clickProfileIconOfShiftByIndex(index);
            schedulePage.clickOnEditShiftTime();
            schedulePage.verifyEditShiftTimePopUpDisplay();
            schedulePage.editShiftTimeToTheLargest();
            schedulePage.clickOnUpdateEditShiftTimeButton();
            index = schedulePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoAfter= schedulePage.getTheShiftInfoByIndex(index);
            SimpleUtils.assertOnFail("Auto shift time is not updated!", !shiftInfoBefore.containsAll(shiftInfoAfter), false);


            //change work role
            selectedShift = schedulePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickOnChangeRole();
            schedulePage.verifyChangeRoleFunctionality();
            //check the work role by click Apply button
            schedulePage.changeWorkRoleInPromptOfAShift(true, selectedShift);
            index = schedulePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoAfterChangeRole= schedulePage.getTheShiftInfoByIndex(index);
            SimpleUtils.assertOnFail("Work role is not updated!", !shiftInfoBefore.containsAll(shiftInfoAfterChangeRole), false);


            //View status
            schedulePage.clickProfileIconOfShiftByIndex(index);
            schedulePage.clickViewStatusBtn();
            //schedulePage.verifyListOfOfferNotNull();
            schedulePage.closeViewStatusContainer();

            //Assign TM
            schedulePage.clickProfileIconOfShiftByIndex(index);
            schedulePage.clickonAssignTM();
            schedulePage.verifySelectTeamMembersOption();
            schedulePage.clickOnOfferOrAssignBtn();
            index = schedulePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoAfterAssignTM= schedulePage.getTheShiftInfoByIndex(index);
            SimpleUtils.assertOnFail("TM is not assigned!", !shiftInfoBefore.containsAll(shiftInfoAfterAssignTM), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the function of auto open shift in day view")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheFunctionOfAutoOpenShiftForMSInDayViewAsInternalAdmin (String username, String password, String browser, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict("District Whistler");
        locationSelectorPage.changeLocation("Lift Ops_Parent");
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
        if (!isActiveWeekGenerated) {
            schedulePage.createScheduleForNonDGFlowNewUI();
        }

        schedulePage.clickOnFilterBtn();
        schedulePage.selectChildLocationFilterByText("Child1");
        schedulePage.clickOnFilterBtn();
        schedulePage.clickOnDayView();

        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.deleteAllShiftsInDayView();
        //schedulePage.deleteTMShiftInWeekView("Open");
        schedulePage.saveSchedule();


        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

        //Create auto open shift.
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.selectWorkRole("LIFT OPERATOR");
        schedulePage.selectChildLocInCreateShiftWindow("Child1");
        schedulePage.moveSliderAtSomePoint("36", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.moveSliderAtSomePoint("20", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();

        //Edit meal break
        WebElement selectedShift = null;
        selectedShift = schedulePage.clickOnProfileIconOfShiftInDayView("open");
        String selectedShiftId= selectedShift.getAttribute("id");
        schedulePage.verifyEditMealBreakTimeFunctionalityForAShiftInDayView(true, selectedShiftId);

        //edit shift time
        int index = schedulePage.getShiftIndexById(selectedShiftId);
        String shiftInfoBefore = schedulePage.getTheShiftInfoByIndexInDayview(index);
        schedulePage.clickOnProfileIconOfShiftInDayView("open");
        schedulePage.clickOnEditShiftTime();
        schedulePage.verifyEditShiftTimePopUpDisplay();
        schedulePage.editShiftTimeToTheLargest();
        schedulePage.clickOnUpdateEditShiftTimeButton();
        index = schedulePage.getShiftIndexById(selectedShiftId);
        String shiftInfoAfter= schedulePage.getTheShiftInfoByIndexInDayview(index);
        SimpleUtils.assertOnFail("Auto shift time is not updated!", !shiftInfoBefore.contains(shiftInfoAfter), false);


        //change work role
        schedulePage.clickOnProfileIconOfShiftInDayView("open");
        schedulePage.changeWorkRoleInPromptOfAShiftInDayView(true, selectedShiftId);
        index = schedulePage.getShiftIndexById(selectedShiftId);
        String shiftInfoAfterChangeRole= schedulePage.getTheShiftInfoByIndexInDayview(index);
        SimpleUtils.assertOnFail("Work role is not updated!", !shiftInfoBefore.contains(shiftInfoAfterChangeRole), false);


        //View status
        schedulePage.clickOnProfileIconOfShiftInDayView("open");
        schedulePage.clickViewStatusBtn();
        //schedulePage.verifyListOfOfferNotNull();
        schedulePage.closeViewStatusContainer();

        //Assign TM
        schedulePage.clickOnProfileIconOfShiftInDayView("open");
        schedulePage.clickonAssignTM();
        schedulePage.verifySelectTeamMembersOption();
        schedulePage.clickOnOfferOrAssignBtn();
        index = schedulePage.getShiftIndexById(selectedShiftId);
        String shiftInfoAfterAssignTM= schedulePage.getTheShiftInfoByIndexInDayview(index);
        SimpleUtils.assertOnFail("TM is not assigned!", !shiftInfoBefore.contains(shiftInfoAfterAssignTM), false);
        try{

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Validate the function of auto open shift in day view")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheFunctionOfAutoOpenShiftForP2PInDayViewAsInternalAdmin (String username, String password, String browser, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if (!isActiveWeekGenerated) {
                schedulePage.createScheduleForNonDGFlowNewUI();
            }

            schedulePage.clickOnFilterBtn();
            schedulePage.selectChildLocationFilterByText("Mountain View");
            schedulePage.clickOnFilterBtn();
            schedulePage.clickOnDayView();

            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteAllShiftsInDayView();
            //schedulePage.deleteTMShiftInWeekView("Open");
            schedulePage.saveSchedule();


            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            //Create auto open shift.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole("BARTENDER");
            schedulePage.selectChildLocInCreateShiftWindow("Mountain View");
            schedulePage.moveSliderAtSomePoint("36", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtSomePoint("20", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();

            WebElement selectedShift = schedulePage.clickOnProfileIconOfShiftInDayView("open");
            String selectedShiftId= selectedShift.getAttribute("id");

            //Edit meal break
            //schedulePage.verifyEditMealBreakTimeFunctionalityForAShiftInDayView(true, selectedShiftId);

            //edit shift time
            int index = schedulePage.getShiftIndexById(selectedShiftId);
            String shiftInfoBefore = schedulePage.getTheShiftInfoByIndexInDayview(index);
            schedulePage.clickOnProfileIconOfShiftInDayView("open");
            schedulePage.clickOnEditShiftTime();
            schedulePage.verifyEditShiftTimePopUpDisplay();
            schedulePage.editShiftTimeToTheLargest();
            schedulePage.clickOnUpdateEditShiftTimeButton();
            index = schedulePage.getShiftIndexById(selectedShiftId);
            String shiftInfoAfter= schedulePage.getTheShiftInfoByIndexInDayview(index);
            SimpleUtils.assertOnFail("Auto shift time is not updated!", !shiftInfoBefore.contains(shiftInfoAfter), false);


            //change work role
            schedulePage.clickOnProfileIconOfShiftInDayView("open");
            schedulePage.changeWorkRoleInPromptOfAShiftInDayView(true, selectedShiftId);
            index = schedulePage.getShiftIndexById(selectedShiftId);
            String shiftInfoAfterChangeRole= schedulePage.getTheShiftInfoByIndexInDayview(index);
            SimpleUtils.assertOnFail("Work role is not updated!", !shiftInfoBefore.contains(shiftInfoAfterChangeRole), false);


            //View status
            schedulePage.clickOnProfileIconOfShiftInDayView("open");
            schedulePage.clickViewStatusBtn();
            //schedulePage.verifyListOfOfferNotNull();
            schedulePage.closeViewStatusContainer();

            //Assign TM
            schedulePage.clickOnProfileIconOfShiftInDayView("open");
            schedulePage.clickonAssignTM();
            schedulePage.verifySelectTeamMembersOption();
            schedulePage.clickOnOfferOrAssignBtn();
            index = schedulePage.getShiftIndexById(selectedShiftId);
            String shiftInfoAfterAssignTM= schedulePage.getTheShiftInfoByIndexInDayview(index);
            SimpleUtils.assertOnFail("TM is not assigned!", !shiftInfoBefore.contains(shiftInfoAfterAssignTM), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Validate the function of manual open shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheFunctionOfManualOpenShiftForP2PAsInternalAdmin (String username, String password, String browser, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            schedulePage.navigateToNextWeek();
            //schedulePage.navigateToNextWeek();
            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            List<String> toCloseDays = new ArrayList<>();
            schedulePage.clickCreateScheduleBtn();
            schedulePage.chooseLocationInCreateSchedulePopupWindow("Mountain View");
            schedulePage.editTheOperatingHoursForLGInPopupWinodw(toCloseDays);
            schedulePage.chooseLocationInCreateSchedulePopupWindow("Carmel Club DG Oregon");
            schedulePage.editTheOperatingHoursForLGInPopupWinodw(toCloseDays);
            schedulePage.clickNextBtnOnCreateScheduleWindow();
            schedulePage.selectWhichWeekToCopyFrom("SUGGESTED");
            schedulePage.clickNextBtnOnCreateScheduleWindow();


            schedulePage.clickOnFilterBtn();
            schedulePage.selectChildLocationFilterByText("Carmel Club DG Oregon");
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView("Open");
            schedulePage.deleteTMShiftInWeekView("Abigayle");
            schedulePage.clickOnFilterBtn();
            schedulePage.selectChildLocationFilterByText("Mountain View");
            schedulePage.deleteTMShiftInWeekView("Open");
            schedulePage.deleteTMShiftInWeekView("Marlon");
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            //Create shift and assign to TM.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole("KEY MANAGER");
            schedulePage.selectChildLocInCreateShiftWindow("Mountain View");
            schedulePage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtSomePoint("28", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.customizeNewShiftPage();
            schedulePage.searchTeamMemberByName("Marlon");
            schedulePage.clickOnOfferOrAssignBtn();

            //Create shift and assign to TM.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole("KEY MANAGER");
            schedulePage.selectChildLocInCreateShiftWindow("Mountain View");
            schedulePage.moveSliderAtSomePoint("40", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.customizeNewShiftPage();
            schedulePage.searchTeamMemberByName("Marlon");
            schedulePage.clickOnOfferOrAssignBtn();

            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView("Marlon");
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            //Create auto open shift.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole("KEY MANAGER");
            schedulePage.selectChildLocInCreateShiftWindow("Carmel Club DG Oregon");
            schedulePage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtSomePoint("28", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.customizeNewShiftPage();
            schedulePage.searchTeamMemberByName("Marlon");
            //schedulePage.verifySelectTeamMembersOption();
            schedulePage.clickOnOfferOrAssignBtn();


            //Edit meal break
            WebElement selectedShift = null;
            selectedShift = schedulePage.clickOnProfileIconOfOpenShift();
            schedulePage.verifyEditMealBreakTimeFunctionalityForAShift(true, selectedShift);

            //edit shift time

            selectedShift = schedulePage.clickOnProfileIconOfOpenShift();
            String selectedShiftId= selectedShift.getAttribute("id");
            int index = schedulePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoBefore = schedulePage.getTheShiftInfoByIndex(index);
            schedulePage.clickProfileIconOfShiftByIndex(index);
            schedulePage.clickOnEditShiftTime();
            schedulePage.verifyEditShiftTimePopUpDisplay();
            schedulePage.editShiftTimeToTheLargest();
            schedulePage.clickOnUpdateEditShiftTimeButton();
            index = schedulePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoAfter= schedulePage.getTheShiftInfoByIndex(index);
            SimpleUtils.assertOnFail("Auto shift time is not updated!", !shiftInfoBefore.containsAll(shiftInfoAfter), false);


            //change work role
            selectedShift = schedulePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickOnChangeRole();
            schedulePage.verifyChangeRoleFunctionality();
            //check the work role by click Apply button
            schedulePage.changeWorkRoleInPromptOfAShift(true, selectedShift);
            index = schedulePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoAfterChangeRole= schedulePage.getTheShiftInfoByIndex(index);
            SimpleUtils.assertOnFail("Work role is not updated!", !shiftInfoBefore.containsAll(shiftInfoAfterChangeRole), false);


            //View status
            schedulePage.clickProfileIconOfShiftByIndex(index);
            schedulePage.clickViewStatusBtn();
            //schedulePage.verifyListOfOfferNotNull();
            schedulePage.closeViewStatusContainer();

            schedulePage.clickOnCancelButtonOnEditMode();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            //Create shift and assign to TM.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole("KEY MANAGER");
            schedulePage.selectChildLocInCreateShiftWindow("Mountain View");
            schedulePage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtSomePoint("28", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.customizeNewShiftPage();
            schedulePage.searchTeamMemberByName("Marlon");
            schedulePage.clickOnOfferOrAssignBtn();

            //verify travel violation message when assign TM.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole("KEY MANAGER");
            schedulePage.selectChildLocInCreateShiftWindow("Carmel Club DG Oregon");
            schedulePage.moveSliderAtSomePoint("44", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.customizeNewShiftPage();
            schedulePage.searchTeamMemberByName("Marlon");
            String actualMessage = schedulePage.getAllTheWarningMessageOfTMWhenAssign();
            SimpleUtils.assertOnFail("No travel time needed violation!", actualMessage.toLowerCase().contains("travel time needed"), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the function of manual open shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheFunctionOfManualOpenShiftForMSAsInternalAdmin (String username, String password, String browser, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("District Whistler");
            locationSelectorPage.changeLocation("Lift Ops_Parent");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            schedulePage.navigateToNextWeek();
            //schedulePage.navigateToNextWeek();
            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            List<String> toCloseDays = new ArrayList<>();
            schedulePage.clickCreateScheduleBtn();
            schedulePage.chooseLocationInCreateSchedulePopupWindow("Child1");
            schedulePage.editTheOperatingHoursForLGInPopupWinodw(toCloseDays);
            schedulePage.chooseLocationInCreateSchedulePopupWindow("Child2");
            schedulePage.editTheOperatingHoursForLGInPopupWinodw(toCloseDays);
            schedulePage.clickNextBtnOnCreateScheduleWindow();
            schedulePage.selectWhichWeekToCopyFrom("SUGGESTED");
            schedulePage.clickNextBtnOnCreateScheduleWindow();

            schedulePage.clickOnFilterBtn();
            schedulePage.selectChildLocationFilterByText("Child2");
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView("Open");
            schedulePage.deleteTMShiftInWeekView("Abigayle");
            schedulePage.clickOnFilterBtn();
            schedulePage.selectChildLocationFilterByText("Child1");
            schedulePage.deleteTMShiftInWeekView("Open");
            schedulePage.deleteTMShiftInWeekView("Abigayle");
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            //Create shift and assign to TM.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole("LIFT OPERATOR");
            schedulePage.selectChildLocInCreateShiftWindow("Child1");
            schedulePage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtSomePoint("28", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.customizeNewShiftPage();
            schedulePage.searchTeamMemberByName("Abigayle");
            schedulePage.clickOnOfferOrAssignBtn();

            //Create shift and assign to TM.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole("LIFT OPERATOR");
            schedulePage.selectChildLocInCreateShiftWindow("Child2");
            schedulePage.moveSliderAtSomePoint("40", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.customizeNewShiftPage();
            schedulePage.searchTeamMemberByName("Abigayle");
            schedulePage.clickOnOfferOrAssignBtn();

            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView("Abigayle");
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            //Create auto open shift.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole("LIFT OPERATOR");
            schedulePage.selectChildLocInCreateShiftWindow("Child1");
            schedulePage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtSomePoint("28", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.customizeNewShiftPage();
            schedulePage.searchTeamMemberByName("Abigayle");
            //schedulePage.verifySelectTeamMembersOption();
            schedulePage.clickOnOfferOrAssignBtn();


            //Edit meal break
            WebElement selectedShift = null;
            selectedShift = schedulePage.clickOnProfileIconOfOpenShift();
            schedulePage.verifyEditMealBreakTimeFunctionalityForAShift(true, selectedShift);

            //edit shift time

            selectedShift = schedulePage.clickOnProfileIconOfOpenShift();
            String selectedShiftId= selectedShift.getAttribute("id");
            int index = schedulePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoBefore = schedulePage.getTheShiftInfoByIndex(index);
            schedulePage.clickProfileIconOfShiftByIndex(index);
            schedulePage.clickOnEditShiftTime();
            schedulePage.verifyEditShiftTimePopUpDisplay();
            schedulePage.editShiftTimeToTheLargest();
            schedulePage.clickOnUpdateEditShiftTimeButton();
            index = schedulePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoAfter= schedulePage.getTheShiftInfoByIndex(index);
            SimpleUtils.assertOnFail("Auto shift time is not updated!", !shiftInfoBefore.containsAll(shiftInfoAfter), false);


            //change work role
            selectedShift = schedulePage.clickOnProfileIconOfOpenShift();
            schedulePage.clickOnChangeRole();
            schedulePage.verifyChangeRoleFunctionality();
            //check the work role by click Apply button
            schedulePage.changeWorkRoleInPromptOfAShift(true, selectedShift);
            index = schedulePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoAfterChangeRole= schedulePage.getTheShiftInfoByIndex(index);
            SimpleUtils.assertOnFail("Work role is not updated!", !shiftInfoBefore.containsAll(shiftInfoAfterChangeRole), false);


            //View status
            schedulePage.clickProfileIconOfShiftByIndex(index);
            schedulePage.clickViewStatusBtn();
            //schedulePage.verifyListOfOfferNotNull();
            schedulePage.closeViewStatusContainer();

            schedulePage.clickOnCancelButtonOnEditMode();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            //Create shift and assign to TM.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole("LIFT OPERATOR");
            schedulePage.selectChildLocInCreateShiftWindow("Child1");
            schedulePage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtSomePoint("28", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.customizeNewShiftPage();
            schedulePage.searchTeamMemberByName("Abigayle");
            schedulePage.clickOnOfferOrAssignBtn();

            //verify travel violation message when assign TM.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole("LIFT OPERATOR");
            schedulePage.selectChildLocInCreateShiftWindow("Child2");
            schedulePage.moveSliderAtSomePoint("44", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.customizeNewShiftPage();
            schedulePage.searchTeamMemberByName("Abigayle");
            String actualMessage = schedulePage.getAllTheWarningMessageOfTMWhenAssign();
            SimpleUtils.assertOnFail("No travel time needed violation!", actualMessage.toLowerCase().contains("travel time needed"), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the function of manual open shift in day view")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheFunctionOfManualOpenShiftInDayViewForMSAsInternalAdmin (String username, String password, String browser, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("District Whistler");
            locationSelectorPage.changeLocation("Lift Ops_Parent");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();

            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            List<String> toCloseDays = new ArrayList<>();
            schedulePage.clickCreateScheduleBtn();
            schedulePage.chooseLocationInCreateSchedulePopupWindow("Child1");
            schedulePage.editTheOperatingHoursForLGInPopupWinodw(toCloseDays);
            schedulePage.chooseLocationInCreateSchedulePopupWindow("Child2");
            schedulePage.editTheOperatingHoursForLGInPopupWinodw(toCloseDays);
            schedulePage.clickNextBtnOnCreateScheduleWindow();
            schedulePage.selectWhichWeekToCopyFrom("SUGGESTED");
            schedulePage.clickNextBtnOnCreateScheduleWindow();

            schedulePage.clickOnDayView();
            schedulePage.clickOnFilterBtn();
            schedulePage.selectChildLocationFilterByText("Child1");
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteAllShiftsInDayView();
            schedulePage.clickOnFilterBtn();
            schedulePage.selectChildLocationFilterByText("Child2");
            schedulePage.deleteAllShiftsInDayView();
            schedulePage.saveSchedule();

            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            //Create shift and assign to TM.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole("LIFT OPERATOR");
            schedulePage.selectChildLocInCreateShiftWindow("Child1");
            schedulePage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtSomePoint("20", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.customizeNewShiftPage();
            schedulePage.searchTeamMemberByName("Abigayle");
            schedulePage.clickOnOfferOrAssignBtn();

            //Create shift and assign to TM.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole("LIFT OPERATOR");
            schedulePage.selectChildLocInCreateShiftWindow("Child2");
            schedulePage.moveSliderAtSomePoint("44", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.customizeNewShiftPage();
            schedulePage.searchTeamMemberByName("Abigayle");
            schedulePage.clickOnOfferOrAssignBtn();

            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteAllShiftsInDayView();
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            //Create manual open shift.
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole("LIFT OPERATOR");
            schedulePage.selectChildLocInCreateShiftWindow("Child2");
            schedulePage.moveSliderAtSomePoint("44", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.customizeNewShiftPage();
            schedulePage.searchTeamMemberByName("Abigayle");
            String actualMessage = schedulePage.getAllTheWarningMessageOfTMWhenAssign();
            SimpleUtils.assertOnFail("No travel time needed violation!", actualMessage.toLowerCase().contains("travel time needed"), false);
            schedulePage.clickOnOfferOrAssignBtn();


            //Edit meal break
            WebElement selectedShift = null;
            selectedShift = schedulePage.clickOnProfileIconOfShiftInDayView("open");
            String selectedShiftId= selectedShift.getAttribute("id");
            schedulePage.verifyEditMealBreakTimeFunctionalityForAShiftInDayView(true, selectedShiftId);

            //edit shift time
            int index = schedulePage.getShiftIndexById(selectedShiftId);
            String shiftInfoBefore = schedulePage.getTheShiftInfoByIndexInDayview(index);
            schedulePage.clickOnProfileIconOfShiftInDayView("open");
            schedulePage.clickOnEditShiftTime();
            schedulePage.verifyEditShiftTimePopUpDisplay();
            schedulePage.editShiftTimeToTheLargest();
            schedulePage.clickOnUpdateEditShiftTimeButton();
            index = schedulePage.getShiftIndexById(selectedShiftId);
            String shiftInfoAfter= schedulePage.getTheShiftInfoByIndexInDayview(index);
            SimpleUtils.assertOnFail("Auto shift time is not updated!", !shiftInfoBefore.contains(shiftInfoAfter), false);


            //change work role
            schedulePage.clickOnProfileIconOfShiftInDayView("open");
            //check the work role by click Apply button
            schedulePage.changeWorkRoleInPromptOfAShiftInDayView(true, selectedShiftId);
            index = schedulePage.getShiftIndexById(selectedShiftId);
            String shiftInfoAfterChangeRole= schedulePage.getTheShiftInfoByIndexInDayview(index);
            SimpleUtils.assertOnFail("Work role is not updated!", !shiftInfoBefore.contains(shiftInfoAfterChangeRole), false);

            //View status
            schedulePage.clickOnProfileIconOfShiftInDayView("open");
            schedulePage.clickViewStatusBtn();
            //schedulePage.verifyListOfOfferNotNull();
            schedulePage.closeViewStatusContainer();

            //assign TM.
            schedulePage.clickOnProfileIconOfShiftInDayView("open");
            schedulePage.clickonAssignTM();
            schedulePage.verifySelectTeamMembersOption();
            schedulePage.clickOnOfferOrAssignBtn();
            index = schedulePage.getShiftIndexById(selectedShiftId);
            String shiftInfoAfterAssignTM= schedulePage.getTheShiftInfoByIndexInDayview(index);
            SimpleUtils.assertOnFail("TM is not assigned!", !shiftInfoBefore.contains(shiftInfoAfterAssignTM), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
