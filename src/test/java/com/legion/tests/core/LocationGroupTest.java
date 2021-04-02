package com.legion.tests.core;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.legion.pages.*;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
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

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

import static com.legion.utils.MyThreadLocal.getDriver;

public class LocationGroupTest extends TestBase {

    private static HashMap<String, String> parameterMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");

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
    @TestName(description = "P2P:Validate the generation of LG schedule")
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
    @TestName(description = "Validate mananger cannot edit operating hours when disable it's Manage Working Hours Setting permission")
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
    @TestName(description = "P2P:Validate mananger cannot edit operating hours when disable it's Manage Working Hours Setting permission")
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
    @TestName(description = "P2P:Validate all smart cards display correctly after generate schedule ")
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
    @TestName(description = "P2P:Validate the buttons on schedule page")
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
    @TestName(description = "P2P:Validate the group by dropdown list")
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

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate Assign TM when TM has max no. of shifts scheduled")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateAssignTMWhenTMHasMaxNoOfShiftsScheduledForMSAsInternalAdmin (String username, String password, String browser, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict("District Whistler");
        locationSelectorPage.changeLocation("Lift Ops_Parent");
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();

        //Go to controls set max no. of shifts per week
        ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
        controlsPage.gotoControlsPage();
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        controlsNewUIPage.clickOnControlsSchedulingPolicies();
        controlsNewUIPage.clickOnGlobalLocationButton();
        controlsNewUIPage.updateSchedulingPolicyGroupsShiftsPerWeek(3, 5, 5);


        //Go to schedule page to check assign TM.
        schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        schedulePage.navigateToNextWeek();
        schedulePage.navigateToNextWeek();
/*        boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
        if(isActiveWeekGenerated){
            schedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        List<String> toCloseDays = new ArrayList<>();
        schedulePage.clickCreateScheduleBtn();
        schedulePage.chooseLocationInCreateSchedulePopupWindow("Child1");
        schedulePage.editTheOperatingHoursForLGInPopupWinodw(toCloseDays);
        schedulePage.clickNextBtnOnCreateScheduleWindow();
        schedulePage.selectWhichWeekToCopyFrom("SUGGESTED");
        schedulePage.clickNextBtnOnCreateScheduleWindow();
*/        //schedulePage.clickOnFilterBtn();
        //schedulePage.selectChildLocationFilterByText("Child1");
        List<String> shiftInfo = new ArrayList<>();
        while (shiftInfo.size() == 0) {
            shiftInfo = schedulePage.getTheShiftInfoByIndex(0);
        }
        String firstNameOfTM1 = shiftInfo.get(0);
        String workRoleOfTM1 = shiftInfo.get(4);
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.searchShiftOnSchedulePage(firstNameOfTM1);
        schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);
        schedulePage.clickOnCloseSearchBoxButton();
        schedulePage.saveSchedule();

        schedulePage.clickOnFilterBtn();
        schedulePage.selectChildLocationFilterByText("Child1");
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

        //Create shift and assign to TM.
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.selectWorkRole(workRoleOfTM1);
        schedulePage.selectChildLocInCreateShiftWindow("Child1");
        schedulePage.selectSpecificWorkDay(1);
        schedulePage.selectSpecificWorkDay(2);
        schedulePage.selectSpecificWorkDay(3);
        schedulePage.selectSpecificWorkDay(4);
        schedulePage.selectSpecificWorkDay(5);
        schedulePage.moveSliderAtCertainPoint("9", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        schedulePage.moveSliderAtCertainPoint("11", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        //schedulePage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        //schedulePage.moveSliderAtSomePoint("20", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.customizeNewShiftPage();
        schedulePage.searchTeamMemberByName(firstNameOfTM1);
        schedulePage.clickOnOfferOrAssignBtn();

        //Create manual open shift and assign TM.
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.selectWorkRole(workRoleOfTM1);
        schedulePage.selectChildLocInCreateShiftWindow("Child1");
        schedulePage.selectSpecificWorkDay(6);
        schedulePage.moveSliderAtCertainPoint("9", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        schedulePage.moveSliderAtCertainPoint("11", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.customizeNewShiftPage();
        schedulePage.searchTeamMemberByName(firstNameOfTM1);
        String actualMessage = schedulePage.getAllTheWarningMessageOfTMWhenAssign();
        SimpleUtils.assertOnFail("No max no. of shifts violation!", actualMessage.toLowerCase().contains("too many shifts"), false);

        try{
        }catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate TMs Can Receive & Accept Offers for Multiple Shifts and Multiple Locations")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyActivityOfClaimOpenShiftForMSAsTeamMemberLG(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String teamMemberName = profileNewUIPage.getNickNameFromProfile();
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();

        String fileName = "UsersCredentials.json";
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise") + fileName;
        userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] credential = userCredentials.get("InternalAdmin");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1]), String.valueOf(credential[0][2]));
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict("District Whistler");
        locationSelectorPage.changeLocation("Lift Ops_Parent");

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
        schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());

        //to generate schedule  if current week is not generated
        schedulePage.navigateToNextWeek();
        schedulePage.navigateToNextWeek();
        boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
        if(!isActiveWeekGenerated){
            schedulePage.createScheduleForNonDGFlowNewUI();
        }
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.clickOnOpenSearchBoxButton();
        schedulePage.searchShiftOnSchedulePage(teamMemberName);
        schedulePage.deleteTMShiftInWeekView("Unassigned");
        schedulePage.deleteTMShiftInWeekView(teamMemberName);
        schedulePage.saveSchedule();

        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.customizeNewShiftPage();
        schedulePage.selectWorkRole("LIFT OPERATOR");
        schedulePage.clearAllSelectedDays();
        schedulePage.selectSpecificWorkDay(1);
        List<String> locations = schedulePage.getAllLocationGroupLocationsFromCreateShiftWindow();
        schedulePage.selectChildLocInCreateShiftWindow(locations.get((new Random()).nextInt(locations.size()-1)+1));
        schedulePage.moveSliderAtSomePoint("40", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.moveSliderAtSomePoint("20", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        if(schedulePage.ifWarningModeDisplay()){
            schedulePage.clickOnOkButtonInWarningMode();
        }
        schedulePage.searchTeamMemberByName(teamMemberName);
        schedulePage.clickOnOfferOrAssignBtn();
        schedulePage.saveSchedule();
        schedulePage.publishActiveSchedule();
        loginPage.logOut();

        // 3.Login with the TM to claim the shift
        loginToLegionAndVerifyIsLoginDone(username, password, location);
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
//        dashboardPage.goToTodayForNewUI();
        schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.navigateToNextWeek();
        schedulePage.navigateToNextWeek();
        schedulePage.isSchedule();
        String cardName = "WANT MORE HOURS?";
        SimpleUtils.assertOnFail("Smart Card: " + cardName + " not loaded Successfully!", schedulePage.isSpecificSmartCardLoaded(cardName), false);
        String linkName = "View Shifts";
        schedulePage.clickLinkOnSmartCardByName(linkName);
        SimpleUtils.assertOnFail("Open shifts not loaed Successfully!", schedulePage.areShiftsPresent(), false);
        List<String> claimShift = new ArrayList<>(Arrays.asList("Claim Shift"));
        schedulePage.selectOneShiftIsClaimShift(claimShift);
        schedulePage.clickTheShiftRequestByName(claimShift.get(0));
        schedulePage.verifyClickAgreeBtnOnClaimShiftOffer();

        loginPage.logOut();

        // 4.Login with SM to check activity
        Object[][] storeManagerCredentials = userCredentials.get("StoreManagerLG");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
                , String.valueOf(storeManagerCredentials[0][2]));
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftOffer.getValue(), ActivityTest.indexOfActivityType.ShiftOffer.name());
        activityPage.verifyActivityOfShiftOffer(teamMemberName);
        activityPage.approveOrRejectShiftOfferRequestOnActivity(teamMemberName, ActivityTest.approveRejectAction.Approve.getValue());

        //Check the shift been scheduled
        schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        //to generate schedule  if current week is not generated
        schedulePage.navigateToNextWeek();
        schedulePage.navigateToNextWeek();
        schedulePage.clickOnOpenSearchBoxButton();
        schedulePage.searchShiftOnSchedulePage(teamMemberName);
        SimpleUtils.assertOnFail("", schedulePage.getOneDayShiftByName(0, teamMemberName).size()>0, false);
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "P2P:Validate TMs Can Receive & Accept Offers for Multiple Shifts and Multiple Locations")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyActivityOfClaimOpenShiftForP2PAsTeamMemberLG(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String teamMemberName = profileNewUIPage.getNickNameFromProfile();
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();

        String fileName = "UsersCredentials.json";
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise") + fileName;
        userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] credential = userCredentials.get("InternalAdmin");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1]), String.valueOf(credential[0][2]));
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict("Bay Area District");
        locationSelectorPage.changeLocation("LocGroup2");

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
        schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        //to generate schedule  if current week is not generated
        schedulePage.navigateToNextWeek();
        boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
        if(!isActiveWeekGenerated){
            schedulePage.createScheduleForNonDGFlowNewUI();
        }
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.clickOnOpenSearchBoxButton();
        schedulePage.searchShiftOnSchedulePage(teamMemberName);
        schedulePage.deleteTMShiftInWeekView("Unassigned");
        schedulePage.deleteTMShiftInWeekView(teamMemberName);
        schedulePage.saveSchedule();

        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.customizeNewShiftPage();
        schedulePage.selectWorkRole("KEY MANAGER");
        schedulePage.clearAllSelectedDays();
        schedulePage.selectSpecificWorkDay(1);
//        List<String> locations = schedulePage.getAllLocationGroupLocationsFromCreateShiftWindow();
        schedulePage.selectChildLocInCreateShiftWindow("Carmel Club DG Oregon");
        schedulePage.moveSliderAtSomePoint("44", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.moveSliderAtSomePoint("22", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        if(schedulePage.ifWarningModeDisplay()){
            schedulePage.clickOnOkButtonInWarningMode();
        }
        schedulePage.searchTeamMemberByName(teamMemberName);
        schedulePage.clickOnOfferOrAssignBtn();
        schedulePage.saveSchedule();
        schedulePage.publishActiveSchedule();
        loginPage.logOut();

        // 3.Login with the TM to claim the shift
        loginToLegionAndVerifyIsLoginDone(username, password, location);
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
//        dashboardPage.goToTodayForNewUI();
        schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.navigateToNextWeek();
        schedulePage.isSchedule();
        String cardName = "WANT MORE HOURS?";
        SimpleUtils.assertOnFail("Smart Card: " + cardName + " not loaded Successfully!", schedulePage.isSpecificSmartCardLoaded(cardName), false);
        String linkName = "View Shifts";
        schedulePage.clickLinkOnSmartCardByName(linkName);
        SimpleUtils.assertOnFail("Open shifts not loaed Successfully!", schedulePage.areShiftsPresent(), false);
        List<String> claimShift = new ArrayList<>(Arrays.asList("Claim Shift"));
        schedulePage.selectOneShiftIsClaimShift(claimShift);
        schedulePage.clickTheShiftRequestByName(claimShift.get(0));
        schedulePage.verifyClickAgreeBtnOnClaimShiftOffer();

        loginPage.logOut();

        // 4.Login with SM to check activity
        Object[][] storeManagerCredentials = userCredentials.get("StoreManagerLG");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
                , String.valueOf(storeManagerCredentials[0][2]));
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
//        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftOffer.getValue(), ActivityTest.indexOfActivityType.ShiftOffer.name());
        activityPage.verifyActivityOfShiftOffer(teamMemberName);
        activityPage.approveOrRejectShiftOfferRequestOnActivity(teamMemberName, ActivityTest.approveRejectAction.Approve.getValue());

        //Check the shift been scheduled
        schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        //to generate schedule  if current week is not generated
        schedulePage.navigateToNextWeek();
        schedulePage.clickOnOpenSearchBoxButton();
        schedulePage.searchShiftOnSchedulePage(teamMemberName);
        SimpleUtils.assertOnFail("", schedulePage.getOneDayShiftByName(0, teamMemberName).size()>0, false);
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate that operate LG schedule by different user")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateOperateLGScheduleByDifferentUserForMSAsInternalAdmin (String username, String password, String browser, String location) throws Exception {
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

            // Verify operate schedule by admin user
            /// Generate schedule
            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }

            /// Publish schedule
            if(schedulePage.isPublishButtonLoadedOnSchedulePage() || schedulePage.isRepublishButtonLoadedOnSchedulePage())
                schedulePage.publishActiveSchedule();

            /// Add shifts in schedule
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.addOpenShiftWithDefaultTime("MOD","Child2");

            /// Edit shifts(include edit shift time, assign TM, delete...)
            schedulePage.clickOnProfileIcon();
            schedulePage.clickOnEditShiftTime();
            schedulePage.editShiftTime();
            schedulePage.clickOnUpdateEditShiftTimeButton();

            schedulePage.clickOnProfileIcon();
            schedulePage.clickonAssignTM();
            schedulePage.selectTeamMembers();
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();

            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.selectShiftTypeFilterByText("Compliance Review");
            schedulePage.clickOnProfileIcon();
            schedulePage.verifyDeleteShift();

            /// Republish schedule
            schedulePage.publishActiveSchedule();

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            // Verify operate schedule by SM user
            /// Login as Store Manager
            String fileName = "UsersCredentials.json";
            fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
            Object[][] teamMemberCredentials = userCredentials.get("StoreManagerLG");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                    , String.valueOf(teamMemberCredentials[0][2]));
            dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            schedulePage.navigateToNextWeek();

            /// Generate schedule
            isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }

            /// Publish schedule
            if(schedulePage.isPublishButtonLoadedOnSchedulePage() || schedulePage.isRepublishButtonLoadedOnSchedulePage())
                schedulePage.publishActiveSchedule();

            /// Add shifts in schedule
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.addOpenShiftWithDefaultTime("MOD","Child2");

            /// Edit shifts(include edit shift time, assign TM, delete...)
            schedulePage.clickOnProfileIcon();
            schedulePage.clickOnEditShiftTime();
            schedulePage.editShiftTime();
            schedulePage.clickOnUpdateEditShiftTimeButton();

            schedulePage.clickOnProfileIcon();
            schedulePage.clickonAssignTM();
            schedulePage.selectTeamMembers();
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();

            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.selectShiftTypeFilterByText("Compliance Review");
            schedulePage.clickOnProfileIcon();
            schedulePage.verifyDeleteShift();

            /// Republish schedule
            schedulePage.publishActiveSchedule();

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Validate that operate LG schedule by different user")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateOperateLGScheduleByDifferentUserForP2PAsInternalAdmin (String username, String password, String browser, String location) throws Exception {
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

            // Verify operate schedule by admin user

            /// Generate schedule
            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }

            /// Publish schedule
            if(schedulePage.isPublishButtonLoadedOnSchedulePage() || schedulePage.isRepublishButtonLoadedOnSchedulePage())
                schedulePage.publishActiveSchedule();

            /// Add shifts in schedule
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.addOpenShiftWithDefaultTime("MOD","Mountain View");

            /// Edit shifts(include edit shift time, assign TM, delete...)
            schedulePage.clickOnProfileIcon();
            schedulePage.clickOnEditShiftTime();
            schedulePage.editShiftTime();
            schedulePage.clickOnUpdateEditShiftTimeButton();

            schedulePage.clickOnProfileIcon();
            schedulePage.clickonAssignTM();
            schedulePage.selectTeamMembers();
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();

            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.selectShiftTypeFilterByText("Compliance Review");
            schedulePage.clickOnProfileIcon();
            schedulePage.verifyDeleteShift();

            /// Republish schedule
            schedulePage.publishActiveSchedule();

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            // Verify operate schedule by SM user
            /// Login as Store Manager
            String fileName = "UsersCredentials.json";
            fileName = SimpleUtils.getEnterprise("Coffee_Enterprise")+fileName;
            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
            Object[][] teamMemberCredentials = userCredentials.get("StoreManagerLG");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                    , String.valueOf(teamMemberCredentials[0][2]));
            dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            schedulePage.navigateToNextWeek();

            /// Generate schedule
            isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }

            /// Publish schedule
            if(schedulePage.isPublishButtonLoadedOnSchedulePage() || schedulePage.isRepublishButtonLoadedOnSchedulePage())
                schedulePage.publishActiveSchedule();

            /// Add shifts in schedule
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.addOpenShiftWithDefaultTime("MOD");

            /// Edit shifts(include edit shift time, assign TM, delete...)
            schedulePage.clickOnProfileIcon();
            schedulePage.clickOnEditShiftTime();
            schedulePage.editShiftTime();
            schedulePage.clickOnUpdateEditShiftTimeButton();

            schedulePage.clickOnProfileIcon();
            schedulePage.clickonAssignTM();
            schedulePage.selectTeamMembers();
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();

            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.selectShiftTypeFilterByText("Compliance Review");
            schedulePage.clickOnProfileIcon();
            schedulePage.verifyDeleteShift();

            /// Republish schedule
            schedulePage.publishActiveSchedule();

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate UI performance for large roster (500 employees) with one location as well as multiple location groups")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateUIPerformanceForLargeRosterForMSAsInternalAdmin (String username, String password, String browser, String location) throws Exception {
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
            schedulePage.navigateToNextWeek();

            // Verify LG schedule can be generated with large TMs in 2 mins
            /// Generate one schedule with more than 500 TMs
            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();

            // Verify LG schedule can be edited with large TMs in 2 mins
            /// Edit this schedule, save and publish it
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Validate UI performance for large roster (500 employees) with one location as well as multiple location groups")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateUIPerformanceForLargeRosterForP2PAsInternalAdmin (String username, String password, String browser, String location) throws Exception {
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
            schedulePage.navigateToNextWeek();

            // Verify LG schedule can be generated with large TMs in 2 mins
            /// Generate one schedule with more than 500 TMs
            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();

            // Verify LG schedule can be edited with large TMs in 2 mins
            /// Edit this schedule, save and publish it
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }



    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate Print Schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validatePrintScheduleForMSAsInternalAdmin (String username, String password, String browser, String location) throws Exception {
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

            // Verify the LG schedule can be printed and the shift display correctly in print file in week view
            /// Go to one generated schedule
            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }

            /// In week view, print the schedule by click Print button
            SimpleUtils.assertOnFail("Print Icon not loaded Successfully!", schedulePage.isPrintIconLoaded(), false);
            String handle = getDriver().getWindowHandle();
            schedulePage.verifyThePrintFunction();

            /// Get the content in print file
            String downloadPath = parameterMap.get("Download_File_Default_Dir");
            Thread.sleep(5000);
            File latestFile = SimpleUtils.getLatestFileFromDirectory(downloadPath);
            String fileName = latestFile.getName();
            PdfReader reader = new PdfReader(downloadPath+"\\"+fileName);
            String content = PdfTextExtractor.getTextFromPage(reader, 1);

            /// Get scheduled hours and shifts count for one sub location on schedule page
            String subLocation = content.split(" ")[0];
            getDriver().switchTo().window(handle);
            schedulePage.selectLocationFilterByText(subLocation);
            HashMap<String, String> hoursOnSchedule = schedulePage.getHoursFromSchedulePage();
            int shiftsCount = schedulePage.getShiftsCount();

            /// Compare the data for one sub location in printed file and schedule page
            if (content.contains(hoursOnSchedule.get("Scheduled")) && content.contains(""+shiftsCount)) {
                SimpleUtils.report("The scheduled hours of " + subLocation+ " is " + hoursOnSchedule.get("Scheduled") + " Hrs");
                SimpleUtils.report("The shifts count  of " + subLocation + " is " + shiftsCount + " Shifts");
                SimpleUtils.pass("Schedule page: The content in printed file in week view displays correctly");
            } else
                SimpleUtils.fail("Schedule page: The content in printed file in week view displays incorrectly",false);

            // Verify the LG schedule can be printed and the shift display correctly in print file in day view
            /// In day view, print the schedule by click Print button
            schedulePage.clickOnDayView();
            SimpleUtils.assertOnFail("Print Icon not loaded Successfully!", schedulePage.isPrintIconLoaded(), false);
            schedulePage.verifyThePrintFunction();

            /// Get the content in print file
            downloadPath = parameterMap.get("Download_File_Default_Dir");
            Thread.sleep(5000);
            latestFile = SimpleUtils.getLatestFileFromDirectory(downloadPath);
            fileName = latestFile.getName();
            reader = new PdfReader(downloadPath+"\\"+fileName);
            content = PdfTextExtractor.getTextFromPage(reader, 1);

            /// Get scheduled hours and shifts count for one sub location on schedule page
            subLocation = content.split(" ")[0];
            getDriver().switchTo().window(handle);
            schedulePage.selectLocationFilterByText(subLocation);
            hoursOnSchedule = schedulePage.getHoursFromSchedulePage();
            shiftsCount = schedulePage.getAvailableShiftsInDayView().size();

            /// Compare the data for one sub location in printed file and schedule page
            if (content.contains(hoursOnSchedule.get("Scheduled")) && content.contains(""+shiftsCount)) {
                SimpleUtils.report("The scheduled hours of " + subLocation+ " is " + hoursOnSchedule.get("Scheduled") + " Hrs");
                SimpleUtils.report("The shifts count  of " + subLocation + " is " + shiftsCount + " Shifts");
                SimpleUtils.pass("Schedule page: The content in printed file in day view displays correctly");
            } else
                SimpleUtils.fail("Schedule page: The content in printed file in day view displays incorrectly",false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Validate Print Schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validatePrintScheduleForP2PAsInternalAdmin (String username, String password, String browser, String location) throws Exception {
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

            // Verify the LG schedule can be printed and the shift display correctly in print file in week view
            /// Go to one generated schedule
            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if (!isActiveWeekGenerated) {
                schedulePage.createScheduleForNonDGFlowNewUI();
            }

            /// In week view, print the schedule by click Print button
            SimpleUtils.assertOnFail("Print Icon not loaded Successfully!", schedulePage.isPrintIconLoaded(), false);
            String handle = getDriver().getWindowHandle();
            schedulePage.verifyThePrintFunction();

            /// Get the content in print file
            String downloadPath = parameterMap.get("Download_File_Default_Dir");
            Thread.sleep(5000);
            File latestFile = SimpleUtils.getLatestFileFromDirectory(downloadPath);
            String fileName = latestFile.getName();
            PdfReader reader = new PdfReader(downloadPath + "\\" + fileName);
            String content = PdfTextExtractor.getTextFromPage(reader, 1);

            /// Get scheduled hours and shifts count for one sub location on schedule page
            String subLocation = content.split(" ")[0];
            getDriver().switchTo().window(handle);
            schedulePage.selectLocationFilterByText(subLocation);
            HashMap<String, String> hoursOnSchedule = schedulePage.getHoursFromSchedulePage();
            int shiftsCount = schedulePage.getShiftsCount();

            /// Compare the data for one sub location in printed file and schedule page
            if (content.contains(hoursOnSchedule.get("Scheduled")) && content.contains("" + shiftsCount)) {
                SimpleUtils.report("The scheduled hours of " + subLocation + " is " + hoursOnSchedule.get("Scheduled") + " Hrs");
                SimpleUtils.report("The shifts count  of " + subLocation + " is " + shiftsCount + " Shifts");
                SimpleUtils.pass("Schedule page: The content in printed file in week view displays correctly");
            } else
                SimpleUtils.fail("Schedule page: The content in printed file in week view displays incorrectly", false);

            // Verify the LG schedule can be printed and the shift display correctly in print file in day view
            /// In day view, print the schedule by click Print button
            schedulePage.clickOnDayView();
            SimpleUtils.assertOnFail("Print Icon not loaded Successfully!", schedulePage.isPrintIconLoaded(), false);
            schedulePage.verifyThePrintFunction();

            /// Get the content in print file
            downloadPath = parameterMap.get("Download_File_Default_Dir");
            Thread.sleep(5000);
            latestFile = SimpleUtils.getLatestFileFromDirectory(downloadPath);
            fileName = latestFile.getName();
            reader = new PdfReader(downloadPath + "\\" + fileName);
            content = PdfTextExtractor.getTextFromPage(reader, 1);

            /// Get scheduled hours and shifts count for one sub location on schedule page
            subLocation = content.split(" ")[0];
            getDriver().switchTo().window(handle);
            schedulePage.selectLocationFilterByText(subLocation);
            hoursOnSchedule = schedulePage.getHoursFromSchedulePage();
            shiftsCount = schedulePage.getAvailableShiftsInDayView().size();

            /// Compare the data for one sub location in printed file and schedule page
            if (content.contains(hoursOnSchedule.get("Scheduled")) && content.contains("" + shiftsCount)) {
                SimpleUtils.report("The scheduled hours of " + subLocation + " is " + hoursOnSchedule.get("Scheduled") + " Hrs");
                SimpleUtils.report("The shifts count  of " + subLocation + " is " + shiftsCount + " Shifts");
                SimpleUtils.pass("Schedule page: The content in printed file in day view displays correctly");
            } else
                SimpleUtils.fail("Schedule page: The content in printed file in day view displays incorrectly",false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }



    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Prepare the data for swap")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void prepareTheSwapShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            List<String> swapNames = new ArrayList<>();
            String fileName = "UserCredentialsForComparableSwapShiftsLG.json";
            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
            for (Map.Entry<String, Object[][]> entry : userCredentials.entrySet()) {
                if (!entry.getKey().equals("Cover TM")) {
                    swapNames.add(entry.getKey());
                    SimpleUtils.pass("Get Swap User name:" + entry.getKey());
                }
            }
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("District Whistler");
            locationSelectorPage.changeLocation("Lift Ops_Parent");
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();
            // Deleting the existing shifts for swap team members
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.selectGroupByFilter("Group by All");
            schedulePage.clickOnOpenSearchBoxButton();
            schedulePage.searchShiftOnSchedulePage(swapNames.get(0));
            schedulePage.deleteTMShiftInWeekView(swapNames.get(0));
            schedulePage.searchShiftOnSchedulePage(swapNames.get(1));
            schedulePage.deleteTMShiftInWeekView(swapNames.get(1));
            schedulePage.clickOnCloseSearchBoxButton();
            if(schedulePage.isRequiredActionSmartCardLoaded()){
                schedulePage.clickOnViewShiftsBtnOnRequiredActionSmartCard();
                schedulePage.deleteTMShiftInWeekView("Unassigned");
                schedulePage.clickOnFilterBtn();
                schedulePage.clickOnClearFilterOnFilterDropdownPopup();
                schedulePage.clickOnFilterBtn();
            }
            schedulePage.saveSchedule();
            // Add the new shifts for swap team members
            Thread.sleep(5000);
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.addNewShiftsByNames(swapNames);
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the content of Shift Swap activity when TM request to swap the shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOfShiftSwapActivityForMSAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        prepareTheSwapShiftsAsInternalAdmin(browser, username, password, location);
        SimpleUtils.report("Need to set 'Is approval by Manager required when an employee claims a shift swap or cover request?' to 'Always' First!");
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict("District Whistler");
        locationSelectorPage.changeLocation("Lift Ops_Parent");
        ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
        controlsPage.gotoControlsPage();
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        SimpleUtils.assertOnFail("Controls Page not loaded Successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
        controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
        SimpleUtils.assertOnFail("Schedule Collaboration Page not loaded Successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
        String option = "Always";
        controlsNewUIPage.updateSwapAndCoverRequestIsApprovalRequired(option);

        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();

        List<String> swapNames = new ArrayList<>();
        String fileName = "UserCredentialsForComparableSwapShiftsLG.json";
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        for (Map.Entry<String, Object[][]> entry : userCredentials.entrySet()) {
            if (!entry.getKey().equals("Cover TM")) {
                swapNames.add(entry.getKey());
                SimpleUtils.pass("Get Swap User name: " + entry.getKey());
            }
        }
        Object[][] credential = null;
        credential = userCredentials.get(swapNames.get(0));
        loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                , String.valueOf(credential[0][2]));
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String requestUserName = profileNewUIPage.getNickNameFromProfile();
        if (dashboardPage.isSwitchToEmployeeViewPresent()) {
            dashboardPage.clickOnSwitchToEmployeeView();
        }
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.isSchedule();
        schedulePage.navigateToNextWeek();
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

        loginPage.logOut();
        credential = userCredentials.get(swapNames.get(1));
        loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
                , String.valueOf(credential[0][2]));
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        String respondUserName = profileNewUIPage.getNickNameFromProfile();
        if (dashboardPage.isSwitchToEmployeeViewPresent()) {
            dashboardPage.clickOnSwitchToEmployeeView();
        }
        dashboardPage.goToTodayForNewUI();
        schedulePage.isSchedule();
        schedulePage.navigateToNextWeek();
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
        fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
        userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] teamMemberCredentials = userCredentials.get("StoreManagerLG");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                , String.valueOf(teamMemberCredentials[0][2]));
        locationSelectorPage.changeDistrict("District Whistler");
        locationSelectorPage.changeLocation("Lift Ops_Parent");
        dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

        // Verify Activity Icon is loaded
        String actionLabel = "requested";
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, respondUserName, actionLabel, true);
        activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftSwap.getValue(), ActivityTest.indexOfActivityType.ShiftSwap.name());
        activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, respondUserName, actionLabel, false);
        activityPage.approveOrRejectShiftSwapRequestOnActivity(requestUserName, respondUserName, ActivityTest.approveRejectAction.Approve.getValue());
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the content of copy schedule for non dg flow")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheContentOfCopyScheduleForMSAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("District Whistler");
            locationSelectorPage.changeLocation("Lift Ops_Parent");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            List<String> weekDaysToClose = new ArrayList<>();
            weekDaysToClose.add("Sunday");
            schedulePage.createScheduleForNonDGByWeekInfo("SUGGESTED", weekDaysToClose, null);
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView("Unassigned");
            schedulePage.saveSchedule();
            //Create auto open shift.

            //Create auto open shift.
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole("LIFT OPERATOR");
            schedulePage.selectChildLocInCreateShiftWindow("Child1");
            schedulePage.moveSliderAtSomePoint("40", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtSomePoint("22", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            // Get the hours and the count of the tms for each day, ex: "37.5 Hrs 5TMs"
            HashMap<String, String> hoursNTMsCountFirstWeek = schedulePage.getTheHoursNTheCountOfTMsForEachWeekDays();
            HashMap<String, List<String>> shiftsForEachDayFirstWeek = schedulePage.getTheContentOfShiftsForEachWeekDay();
            HashMap<String, String> budgetNScheduledHoursFirstWeek = schedulePage.getBudgetNScheduledHoursFromSmartCard();
            String cardName = "COMPLIANCE";
            boolean isComplianceCardLoadedFirstWeek = schedulePage.isSpecificSmartCardLoaded(cardName);
            int complianceShiftCountFirstWeek = 0;
            if (isComplianceCardLoadedFirstWeek) {
                complianceShiftCountFirstWeek = schedulePage.getComplianceShiftCountFromSmartCard(cardName);
            }
            String firstWeekInfo = schedulePage.getActiveWeekText();
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

            //Full copy
            schedulePage.isSchedule();
            isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }


            schedulePage.createScheduleForNonDGByWeekInfo(firstWeekInfo, weekDaysToClose, null);

            HashMap<String, String> hoursNTMsCountSecondWeek = schedulePage.getTheHoursNTheCountOfTMsForEachWeekDays();
            HashMap<String, List<String>> shiftsForEachDaySecondWeek = schedulePage.getTheContentOfShiftsForEachWeekDay();
            HashMap<String, String> budgetNScheduledHoursSecondWeek = schedulePage.getBudgetNScheduledHoursFromSmartCard();
            boolean isComplianceCardLoadedSecondWeek = schedulePage.isSpecificSmartCardLoaded(cardName);
            int complianceShiftCountSecondWeek = 0;
            if (isComplianceCardLoadedFirstWeek) {
                complianceShiftCountSecondWeek = schedulePage.getComplianceShiftCountFromSmartCard(cardName);
            }

            if (hoursNTMsCountFirstWeek.equals(hoursNTMsCountSecondWeek)) {
                SimpleUtils.pass("Verified the scheduled hour and TMs of each week day are consistent with the copied schedule!");
            } else {
                SimpleUtils.fail("Verified the scheduled hour and TMs of each week day are inconsistent with the copied schedule", true);
            }
            if (SimpleUtils.compareHashMapByEntrySet(shiftsForEachDayFirstWeek, shiftsForEachDaySecondWeek)) {
                SimpleUtils.pass("Verified the shifts of each week day are consistent with the copied schedule!");
            } else {
                SimpleUtils.fail("Verified the shifts of each week day are inconsistent with the copied schedule!", true);
            }
            if (budgetNScheduledHoursFirstWeek.get("Scheduled").equals(budgetNScheduledHoursSecondWeek.get("Scheduled"))) {
                SimpleUtils.pass("The Scheduled hour is consistent with the copied scheudle: " + budgetNScheduledHoursFirstWeek.get("Scheduled"));
            } else {
                SimpleUtils.fail("The Scheduled hour is inconsistent, the first week is: " + budgetNScheduledHoursFirstWeek.get("Scheduled")
                        + ", but second week is: " + budgetNScheduledHoursSecondWeek.get("Scheduled"), true);
            }
            if ((isComplianceCardLoadedFirstWeek == isComplianceCardLoadedSecondWeek) && (complianceShiftCountFirstWeek == complianceShiftCountSecondWeek)) {
                SimpleUtils.pass("Verified Compliance is consistent with the copied schedule");
            } else {
                SimpleUtils.fail("Verified Compliance is inconsistent with the copied schedule!", true);
            }



            //Partial copy and select all work roles
            isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            List<String> specificShiftAssigments = new ArrayList<>();
            specificShiftAssigments.add("Lift Manager");
            specificShiftAssigments.add("Lift Maintenance");
            specificShiftAssigments.add("Lift Operator");
            schedulePage.createScheduleForNonDGByWeekInfo(firstWeekInfo, weekDaysToClose, specificShiftAssigments);

            hoursNTMsCountSecondWeek = schedulePage.getTheHoursNTheCountOfTMsForEachWeekDays();
            shiftsForEachDaySecondWeek = schedulePage.getTheContentOfShiftsForEachWeekDay();
            budgetNScheduledHoursSecondWeek = schedulePage.getBudgetNScheduledHoursFromSmartCard();
            isComplianceCardLoadedSecondWeek = schedulePage.isSpecificSmartCardLoaded(cardName);
            if (isComplianceCardLoadedFirstWeek) {
                complianceShiftCountSecondWeek = schedulePage.getComplianceShiftCountFromSmartCard(cardName);
            }

            if (hoursNTMsCountFirstWeek.equals(hoursNTMsCountSecondWeek)) {
                SimpleUtils.pass("Verified the scheduled hour and TMs of each week day are consistent with the copied schedule!");
            } else {
                SimpleUtils.fail("Verified the scheduled hour and TMs of each week day are inconsistent with the copied schedule", true);
            }
            if (SimpleUtils.compareHashMapByEntrySet(shiftsForEachDayFirstWeek, shiftsForEachDaySecondWeek)) {
                SimpleUtils.pass("Verified the shifts of each week day are consistent with the copied schedule!");
            } else {
                SimpleUtils.fail("Verified the shifts of each week day are inconsistent with the copied schedule!", true);
            }
            if (budgetNScheduledHoursFirstWeek.get("Scheduled").equals(budgetNScheduledHoursSecondWeek.get("Scheduled"))) {
                SimpleUtils.pass("The Scheduled hour is consistent with the copied scheudle: " + budgetNScheduledHoursFirstWeek.get("Scheduled"));
            } else {
                SimpleUtils.fail("The Scheduled hour is inconsistent, the first week is: " + budgetNScheduledHoursFirstWeek.get("Scheduled")
                        + ", but second week is: " + budgetNScheduledHoursSecondWeek.get("Scheduled"), true);
            }
            if ((isComplianceCardLoadedFirstWeek == isComplianceCardLoadedSecondWeek) && (complianceShiftCountFirstWeek == complianceShiftCountSecondWeek)) {
                SimpleUtils.pass("Verified Compliance is consistent with the copied schedule");
            } else {
                SimpleUtils.fail("Verified Compliance is inconsistent with the copied schedule!", true);
            }


            //Partial copy and select partial work roles

            isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            specificShiftAssigments.add("Lift Manager");
            specificShiftAssigments.add("Lift Maintenance");
            schedulePage.createScheduleForNonDGByWeekInfo(firstWeekInfo, weekDaysToClose, specificShiftAssigments);

            hoursNTMsCountSecondWeek = schedulePage.getTheHoursNTheCountOfTMsForEachWeekDays();
            shiftsForEachDaySecondWeek = schedulePage.getTheContentOfShiftsForEachWeekDay();
            budgetNScheduledHoursSecondWeek = schedulePage.getBudgetNScheduledHoursFromSmartCard();
            isComplianceCardLoadedSecondWeek = schedulePage.isSpecificSmartCardLoaded(cardName);
            if (isComplianceCardLoadedFirstWeek) {
                complianceShiftCountSecondWeek = schedulePage.getComplianceShiftCountFromSmartCard(cardName);
            }

            if (hoursNTMsCountFirstWeek.equals(hoursNTMsCountSecondWeek)) {
                SimpleUtils.pass("Verified the scheduled hour and TMs of each week day are consistent with the copied schedule!");
            } else {
                SimpleUtils.fail("Verified the scheduled hour and TMs of each week day are inconsistent with the copied schedule", true);
            }
            if (SimpleUtils.compareHashMapByEntrySet(shiftsForEachDayFirstWeek, shiftsForEachDaySecondWeek)) {
                SimpleUtils.pass("Verified the shifts of each week day are consistent with the copied schedule!");
            } else {
                SimpleUtils.fail("Verified the shifts of each week day are inconsistent with the copied schedule!", true);
            }
            if (budgetNScheduledHoursFirstWeek.get("Scheduled").equals(budgetNScheduledHoursSecondWeek.get("Scheduled"))) {
                SimpleUtils.pass("The Scheduled hour is consistent with the copied scheudle: " + budgetNScheduledHoursFirstWeek.get("Scheduled"));
            } else {
                SimpleUtils.fail("The Scheduled hour is inconsistent, the first week is: " + budgetNScheduledHoursFirstWeek.get("Scheduled")
                        + ", but second week is: " + budgetNScheduledHoursSecondWeek.get("Scheduled"), true);
            }
            if ((isComplianceCardLoadedFirstWeek == isComplianceCardLoadedSecondWeek) && (complianceShiftCountFirstWeek == complianceShiftCountSecondWeek)) {
                SimpleUtils.pass("Verified Compliance is consistent with the copied schedule");
            } else {
                SimpleUtils.fail("Verified Compliance is inconsistent with the copied schedule!", true);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the filter on schedule page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheFilterOnSchedulePageForMSAsInternalAdmin (String username, String password, String browser, String location) throws Exception {
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

            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if (!isActiveWeekGenerated) {
                schedulePage.createScheduleForNonDGFlowNewUI();
            }


            schedulePage.selectChildLocationFilterByText("Mountain View");
            if (schedulePage.getShiftsCount()>0){
                schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                schedulePage.deleteTMShiftInWeekView("Open");
                schedulePage.saveSchedule();
            }
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            // Verify the filter UI display correctly
            schedulePage.clickOnFilterBtn();
            schedulePage.verifyFilterDropdownList(true);

            // Verify the location filter has been moved to the left




        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
