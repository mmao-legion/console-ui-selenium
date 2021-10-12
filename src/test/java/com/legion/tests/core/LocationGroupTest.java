package com.legion.tests.core;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.legion.pages.*;
import com.legion.pages.core.ConsoleScheduleCommonPage;
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

import javax.swing.plaf.multi.MultiButtonUI;
import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

import static com.legion.utils.MyThreadLocal.getDriver;

public class LocationGroupTest extends TestBase {

    private static HashMap<String, String> parameterMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
    private HashMap<String, Object[][]> swapCoverCredentials = null;
    private List<String> swapCoverNames = null;
    private String workRoleName = "";

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
    public void validateTheGenerationOfLGScheduleAsInternalAdminPC(String username, String password, String browser, String location)
            throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("District Whistler");
            locationSelectorPage.changeLocation("Lift Ops_Parent");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }

            // Edit random location's operating hours before generate schedule
            createSchedulePage.selectRandomOrSpecificLocationOnUngenerateScheduleEditOperatingHoursPage(null);
            List<String> toCloseDays = new ArrayList<>();
            newShiftPage.editOperatingHoursOnScheduleOldUIPage("9", "17", toCloseDays);
            // Edit random location's operating hours during generate schedule
            createSchedulePage.createScheduleForNonDGFlowNewUI();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "P2P:Validate the generation of LG schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheGenerationOfLGScheduleAsInternalAdminP2P(String username, String password, String browser, String location)
            throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isActiveWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            scheduleCommonPage.navigateToNextWeek();
            isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isActiveWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            // Edit random location's operating hours before generate schedule
            createSchedulePage.selectRandomOrSpecificLocationOnUngenerateScheduleEditOperatingHoursPage(null);
            List<String> toCloseDays = new ArrayList<>();
            newShiftPage.editOperatingHoursOnScheduleOldUIPage("9am", "5pm", toCloseDays);
            // Edit random location's operating hours during generate schedule
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            createSchedulePage.publishActiveSchedule();

            dashboardPage.clickOnDashboardConsoleMenu();
            locationSelectorPage.changeLocation("Mountain View");
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    !createSchedulePage.isWeekGenerated(), false);
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            createSchedulePage.publishActiveSchedule();

            scheduleCommonPage.navigateToNextWeek();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    createSchedulePage.isWeekGenerated(), false);

            dashboardPage.clickOnDashboardConsoleMenu();
            locationSelectorPage.changeLocation("Carmel Club DG Oregon");
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    !createSchedulePage.isWeekGenerated(), false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate mananger cannot edit operating hours when disable it's Manage Working Hours Setting permission")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateManagerCannotEditOperatingHoursWhenDisableItsManageWorkingHoursSettingPermissionAsInternalAdminPC(String username, String password, String browser, String location)
            throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("District Whistler");
            locationSelectorPage.changeLocation("Lift Ops_Parent");

            //Log in as admin, uncheck the Working Hours Setting Permission to SM.
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            CinemarkMinorPage cinemarkMinorPage = pageFactory.createConsoleCinemarkMinorPage();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnGlobalLocationButton();
            controlsNewUIPage.clickOnControlsUsersAndRolesSection();
            controlsNewUIPage.selectUsersAndRolesSubTabByLabel(ControlsNewUITest.usersAndRolesSubTabs.AccessByJobTitles.getValue());

            String permissionSection = "Controls";
            String permission = "Manage Working Hours Settings";
            String actionOff = "off";
            String actionOn = "on";
            cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Edit.getValue());
            controlsNewUIPage.turnOnOrOffSpecificPermissionForSM(permissionSection, permission, actionOff);
            cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Save.getValue());
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            //Log in as SM
            loginAsDifferentRole(AccessRoles.StoreManagerLG.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Check SM cannot edit operating hours now
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            //Check the edit buttons on ungenerate schedule page
            SimpleUtils.assertOnFail("Edit operating hours buttons are shown on ungenerate schedule page! ",
                    !createSchedulePage.checkIfEditOperatingHoursButtonsAreShown() , false);

            //Check the edit button on create schedule page
            createSchedulePage.clickCreateScheduleBtn();
            SimpleUtils.assertOnFail("Edit operating hours buttons are shown on ungenerate schedule page! ",
                    !createSchedulePage.checkIfEditOperatingHoursButtonsAreShown() , false);
            createSchedulePage.clickExitBtnToExitCreateScheduleWindow();
            loginPage.logOut();

            //Log in as admin, grant the Working Hours Setting Permission to SM.
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
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
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            //Check the edit buttons on ungenerate schedule page
            SimpleUtils.assertOnFail("Edit operating hours buttons are shown on ungenerate schedule page! ",
                    createSchedulePage.checkIfEditOperatingHoursButtonsAreShown() , false);

            //Check the edit button on create schedule page
            createSchedulePage.clickCreateScheduleBtn();
            SimpleUtils.assertOnFail("Edit operating hours buttons are shown on ungenerate schedule page! ",
                    createSchedulePage.checkIfEditOperatingHoursButtonsAreShown() , false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "P2P:Validate mananger cannot edit operating hours when disable it's Manage Working Hours Setting permission")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateManagerCannotEditOperatingHoursWhenDisableItsManageWorkingHoursSettingPermissionAsInternalAdminP2P(String username, String password, String browser, String location)
            throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }

            //Log in as admin, uncheck the Working Hours Setting Permission to SM.

            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            CinemarkMinorPage cinemarkMinorPage = pageFactory.createConsoleCinemarkMinorPage();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnGlobalLocationButton();
            controlsNewUIPage.clickOnControlsUsersAndRolesSection();
            controlsNewUIPage.selectUsersAndRolesSubTabByLabel(ControlsNewUITest.usersAndRolesSubTabs.AccessByJobTitles.getValue());

            String permissionSection = "Controls";
            String permission = "Manage Working Hours Settings";
            String actionOff = "off";
            String actionOn = "on";
            cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Edit.getValue());
            controlsNewUIPage.turnOnOrOffSpecificPermissionForSM(permissionSection, permission, actionOff);
            cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Save.getValue());
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            //Log in as SM
            loginAsDifferentRole(AccessRoles.StoreManagerLG.getValue());
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Check SM cannot edit operating hours now
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            //Check the edit buttons on ungenerate schedule page
            SimpleUtils.assertOnFail("Edit operating hours buttons are shown on ungenerate schedule page! ",
                    !createSchedulePage.checkIfEditOperatingHoursButtonsAreShown() , false);

            //Check the edit button on create schedule page
            createSchedulePage.clickCreateScheduleBtn();
            SimpleUtils.assertOnFail("Edit operating hours buttons are shown on ungenerate schedule page! ",
                    !createSchedulePage.checkIfEditOperatingHoursButtonsAreShown() , false);
            createSchedulePage.clickExitBtnToExitCreateScheduleWindow();
            loginPage.logOut();

            //Log in as admin, grant the Working Hours Setting Permission to SM.
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
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
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            //Check the edit buttons on ungenerate schedule page
            SimpleUtils.assertOnFail("Edit operating hours buttons are shown on ungenerate schedule page! ",
                    createSchedulePage.checkIfEditOperatingHoursButtonsAreShown() , false);

            //Check the edit button on create schedule page
            createSchedulePage.clickCreateScheduleBtn();
            SimpleUtils.assertOnFail("Edit operating hours buttons are shown on ungenerate schedule page! ",
                    createSchedulePage.checkIfEditOperatingHoursButtonsAreShown() , false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate all smart cards display correctly after generate schedule ")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateAllSmartCardsDisplayCorrectlyAfterGenerateScheduleAsInternalAdminPC (String username, String password, String browser, String location)
            throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("District Whistler");
            locationSelectorPage.changeLocation("Lift Ops_Parent");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }

            createSchedulePage.selectRandomOrSpecificLocationOnUngenerateScheduleEditOperatingHoursPage("Child1");
            List<String> toCloseDays = new ArrayList<>();
            newShiftPage.editOperatingHoursOnScheduleOldUIPage("5am", "11pm", toCloseDays);
            // Edit random location's operating hours during generate schedule
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Child1");
            scheduleMainPage.clickOnFilterBtn();

            //Check Schedule not published smart card is display
            SimpleUtils.assertOnFail("Schedule not published smart card should display for new generate schedule! ",
                    smartCardPage.isScheduleNotPublishedSmartCardLoaded(),false);

            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.clickOnEditShiftTime();
            shiftOperatePage.verifyEditShiftTimePopUpDisplay();
            shiftOperatePage.editShiftTimeToTheLargest();
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            scheduleMainPage.saveSchedule();
            //verify Compliance SmartCard Functionality
            if(smartCardPage.verifyComplianceShiftsSmartCardShowing() && smartCardPage.verifyRedFlagIsVisible()){
                smartCardPage.verifyComplianceFilterIsSelectedAftClickingViewShift();
                smartCardPage.verifyComplianceShiftsShowingInGrid();
                smartCardPage.verifyClearFilterFunction();
            }else
                SimpleUtils.fail("There is no compliance and no red flag", false);

            //verifyScheduleFunctionalityScheduleSmartCard
            ArrayList<LinkedHashMap<String, Float>> scheduleOverviewAllWeekHours = new ArrayList<LinkedHashMap<String, Float>>();
            HashMap<String, Float> scheduleSmartCardHoursWages = new HashMap<>();
            HashMap<String, Float> overviewData = new HashMap<>();

            scheduleSmartCardHoursWages = smartCardPage.getScheduleBudgetedHoursInScheduleSmartCard();
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
    public void validateAllSmartCardsDisplayCorrectlyAfterGenerateScheduleAsInternalAdminP2P (String username, String password, String browser, String location)
            throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }

            createSchedulePage.selectRandomOrSpecificLocationOnUngenerateScheduleEditOperatingHoursPage("Mountain View");
            List<String> toCloseDays = new ArrayList<>();
            newShiftPage.editOperatingHoursOnScheduleOldUIPage("5am", "11pm", toCloseDays);
            // Edit random location's operating hours during generate schedule
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Mountain View");
            scheduleMainPage.clickOnFilterBtn();

            //Check Schedule not published smart card is display
            SimpleUtils.assertOnFail("Schedule not published smart card should display for new generate schedule! ",
                    smartCardPage.isScheduleNotPublishedSmartCardLoaded(),false);

            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.clickOnEditShiftTime();
            shiftOperatePage.verifyEditShiftTimePopUpDisplay();
            shiftOperatePage.editShiftTimeToTheLargest();
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            scheduleMainPage.saveSchedule();
            //verify Compliance SmartCard Functionality
            if(smartCardPage.verifyComplianceShiftsSmartCardShowing() && smartCardPage.verifyRedFlagIsVisible()){
                smartCardPage.verifyComplianceFilterIsSelectedAftClickingViewShift();
                smartCardPage.verifyComplianceShiftsShowingInGrid();
                smartCardPage.verifyClearFilterFunction();
            }else
                SimpleUtils.fail("There is no compliance and no red flag", false);

            //verify Schedule Functionality Schedule SmartCard
            ArrayList<LinkedHashMap<String, Float>> scheduleOverviewAllWeekHours = new ArrayList<LinkedHashMap<String, Float>>();
            HashMap<String, Float> scheduleSmartCardHoursWages = new HashMap<>();
            HashMap<String, Float> overviewData = new HashMap<>();

            scheduleSmartCardHoursWages = smartCardPage.getScheduleBudgetedHoursInScheduleSmartCard();
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
    public void validateTheButtonsOnSchedulePageAsInternalAdminPC(String username, String password, String browser, String location)
            throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            AnalyzePage analyzePage = pageFactory.createAnalyzePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            ToggleSummaryPage toggleSummaryPage = pageFactory.createToggleSummaryPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("District Whistler");
            locationSelectorPage.changeLocation("Lift Ops_Parent");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }

            // Edit random location's operating hours during generate schedule
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            // Check Edit button
            scheduleMainPage.checkEditButton();
            scheduleMainPage.verifyEditButtonFuntionality();

            // Check Publish button
            createSchedulePage.isPublishButtonLoadedOnSchedulePage();
            createSchedulePage.publishActiveSchedule();
            SimpleUtils.assertOnFail("Schedule should be published! ", createSchedulePage.isCurrentScheduleWeekPublished(), false);

            // Check Republish button
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.clickOnEditShiftTime();
            shiftOperatePage.verifyEditShiftTimePopUpDisplay();
            shiftOperatePage.editShiftTime();
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            scheduleMainPage.saveSchedule();
            SimpleUtils.assertOnFail("The Republish button should display! ", createSchedulePage.isRepublishButtonLoadedOnSchedulePage(), false);
            createSchedulePage.publishActiveSchedule();
            SimpleUtils.assertOnFail("Schedule should be published! ", createSchedulePage.isCurrentScheduleWeekPublished(), false);

            // Check Analyze button
            analyzePage.verifyAnalyzeBtnFunctionAndScheduleHistoryScroll();


            // Check Toggle Summary View
            scheduleMainPage.goToToggleSummaryView();
            //verify the operating hours in Toggle Summary View
            createSchedulePage.checkIfEditOperatingHoursButtonsAreShown();
            scheduleMainPage.clickToggleSummaryViewButton();

            // Ungenerate button
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            SimpleUtils.assertOnFail("Schedule should been ungenerated", !createSchedulePage.isWeekGenerated(), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "P2P:Validate the buttons on schedule page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheButtonsOnSchedulePageAsInternalAdminP2P(String username, String password, String browser, String location)
            throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            AnalyzePage analyzePage = pageFactory.createAnalyzePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }

            // Edit random location's operating hours during generate schedule
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            // Check Edit button
            scheduleMainPage.checkEditButton();
            scheduleMainPage.verifyEditButtonFuntionality();

            // Check Publish button
            createSchedulePage.isPublishButtonLoadedOnSchedulePage();
            createSchedulePage.publishActiveSchedule();
            SimpleUtils.assertOnFail("Schedule should be published! ", createSchedulePage.isCurrentScheduleWeekPublished(), false);

            // Check Republish button
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.clickOnEditShiftTime();
            shiftOperatePage.verifyEditShiftTimePopUpDisplay();
            shiftOperatePage.editShiftTime();
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            scheduleMainPage.saveSchedule();
            SimpleUtils.assertOnFail("The Republish button should display! ", createSchedulePage.isRepublishButtonLoadedOnSchedulePage(), false);
            schedulePage.clickOnSchedulePublishButton();
            SimpleUtils.assertOnFail("Schedule should be published! ", createSchedulePage.isCurrentScheduleWeekPublished(), false);

            // Check Analyze button
            analyzePage.verifyAnalyzeBtnFunctionAndScheduleHistoryScroll();


            // Check Toggle Summary View
            scheduleMainPage.goToToggleSummaryView();
            //verify the operating hours in Toggle Summary View
            createSchedulePage.checkIfEditOperatingHoursButtonsAreShown();
            scheduleMainPage.clickToggleSummaryViewButton();

            // Ungenerate button
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            SimpleUtils.assertOnFail("Schedule should been ungenerated", !createSchedulePage.isWeekGenerated(), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the group by dropdown list")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheGroupByDropdownListAsInternalAdminPC(String username, String password, String browser, String location)
            throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("District Whistler");
            locationSelectorPage.changeLocation("Lift Ops_Parent");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            //Check schedule defaulted with group by location
//            SimpleUtils.assertOnFail("Schedule table should defaulted with group by location! ",scheduleMainPage.getActiveGroupByFilter().equals("Group by Location"), false);
            //In week view, Group by All filter have 4 filters:1.Group by all  2. Group by work role  3. Group by TM 4.Group by job title
            scheduleMainPage.validateGroupBySelectorSchedulePage(true);
            //Selecting any of them, check the schedule table
            scheduleMainPage.validateScheduleTableWhenSelectAnyOfGroupByOptions(true);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "P2P:Validate the group by dropdown list")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheGroupByDropdownListAsInternalAdminP2P(String username, String password, String browser, String location)
            throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            //Check schedule defaulted with group by location
//            SimpleUtils.assertOnFail("Schedule table should defaulted with group by location! ",scheduleMainPage.getActiveGroupByFilter().equals("Group by Location"), false);
            //In week view, Group by All filter have 4 filters:1.Group by all  2. Group by work role  3. Group by TM 4.Group by job title
            scheduleMainPage.validateGroupBySelectorSchedulePage(true);
            //Selecting any of them, check the schedule table
            scheduleMainPage.validateScheduleTableWhenSelectAnyOfGroupByOptions(true);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Validate the function of auto open shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheFunctionOfAutoOpenShiftAsInternalAdminP2P (String username, String password, String browser, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            //scheduleCommonPage.navigateToNextWeek();
            //scheduleCommonPage.navigateToNextWeek();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isActiveWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectChildLocationFilterByText("Mountain View");
            if (scheduleShiftTablePage.getShiftsCount()>0){
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                shiftOperatePage.deleteTMShiftInWeekView("Open");
                scheduleMainPage.saveSchedule();
            }
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            String workRole = shiftOperatePage.getRandomWorkRole();
            //Create auto open shift.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Mountain View");
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("28", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();


            //edit shift time
            WebElement selectedShift = shiftOperatePage.clickOnProfileIconOfOpenShift();
            String selectedShiftId= selectedShift.getAttribute("id");
            int index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoBefore = scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            shiftOperatePage.clickOnEditShiftTime();
            shiftOperatePage.verifyEditShiftTimePopUpDisplay();
            shiftOperatePage.editShiftTimeToTheLargest();
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoAfter= scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            SimpleUtils.assertOnFail("Auto shift time is not updated!", !shiftInfoBefore.containsAll(shiftInfoAfter), false);


            //change work role
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            shiftOperatePage.clickOnChangeRole();
            shiftOperatePage.verifyChangeRoleFunctionality();
            //check the work role by click Apply button
            shiftOperatePage.changeWorkRoleInPromptOfAShift(true, selectedShift);
            index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoAfterChangeRole= scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            SimpleUtils.assertOnFail("Work role is not updated!", !shiftInfoBefore.containsAll(shiftInfoAfterChangeRole), false);


            //Edit meal break
            //scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            //shiftOperatePage.verifyEditMealBreakTimeFunctionalityForAShift(true, selectedShift);

            //View status
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            scheduleShiftTablePage.clickViewStatusBtn();
            //shiftOperatePage.verifyListOfOfferNotNull();
            shiftOperatePage.closeViewStatusContainer();

            //Assign TM
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            shiftOperatePage.clickonAssignTM();
            newShiftPage.verifySelectTeamMembersOption();
            newShiftPage.clickOnOfferOrAssignBtn();
            index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoAfterAssignTM= scheduleShiftTablePage.getTheShiftInfoByIndex(index);
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
    public void validateTheFunctionOfAutoOpenShiftAsInternalAdminPC (String username, String password, String browser, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("District Whistler");
            locationSelectorPage.changeLocation("Lift Ops_Parent");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            //scheduleCommonPage.navigateToNextWeek();
            //scheduleCommonPage.navigateToNextWeek();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isActiveWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectChildLocationFilterByText("Child1");
            scheduleMainPage.clickOnFilterBtn();
            if (scheduleShiftTablePage.getShiftsCount()>0){
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                shiftOperatePage.deleteTMShiftInWeekView("Open");
                scheduleMainPage.saveSchedule();
            }
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            String workRole = shiftOperatePage.getRandomWorkRole();
            //Create auto open shift.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Child1");
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("28", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();

            //Edit meal break
            WebElement selectedShift = null;
            selectedShift = shiftOperatePage.clickOnProfileIconOfOpenShift();
            shiftOperatePage.verifyEditMealBreakTimeFunctionalityForAShift(true, selectedShift);

            //edit shift time
            selectedShift = shiftOperatePage.clickOnProfileIconOfOpenShift();
            String selectedShiftId= selectedShift.getAttribute("id");
            int index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoBefore = scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            shiftOperatePage.clickOnEditShiftTime();
            shiftOperatePage.verifyEditShiftTimePopUpDisplay();
            shiftOperatePage.editShiftTimeToTheLargest();
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoAfter= scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            SimpleUtils.assertOnFail("Auto shift time is not updated!", !shiftInfoBefore.containsAll(shiftInfoAfter), false);


            //change work role
            selectedShift = shiftOperatePage.clickOnProfileIconOfOpenShift();
            shiftOperatePage.clickOnChangeRole();
            shiftOperatePage.verifyChangeRoleFunctionality();
            //check the work role by click Apply button
            shiftOperatePage.changeWorkRoleInPromptOfAShift(true, selectedShift);
            index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoAfterChangeRole= scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            SimpleUtils.assertOnFail("Work role is not updated!", !shiftInfoBefore.containsAll(shiftInfoAfterChangeRole), false);


            //View status
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            scheduleShiftTablePage.clickViewStatusBtn();
            //shiftOperatePage.verifyListOfOfferNotNull();
            shiftOperatePage.closeViewStatusContainer();

            //Assign TM
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            shiftOperatePage.clickonAssignTM();
            newShiftPage.verifySelectTeamMembersOption();
            newShiftPage.clickOnOfferOrAssignBtn();
            index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoAfterAssignTM= scheduleShiftTablePage.getTheShiftInfoByIndex(index);
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
    public void validateTheFunctionOfAutoOpenShiftInDayViewAsInternalAdminPC (String username, String password, String browser, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict("District Whistler");
        locationSelectorPage.changeLocation("Lift Ops_Parent");
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
        if (!isActiveWeekGenerated) {
            createSchedulePage.createScheduleForNonDGFlowNewUI();
        }

        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.selectChildLocationFilterByText("Child1");
        scheduleMainPage.clickOnFilterBtn();
        scheduleCommonPage.clickOnDayView();

        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        shiftOperatePage.deleteAllShiftsInDayView();
        //shiftOperatePage.deleteTMShiftInWeekView("Open");
        scheduleMainPage.saveSchedule();


        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        String workRole = shiftOperatePage.getRandomWorkRole();
        //Create auto open shift.
        newShiftPage.clickOnDayViewAddNewShiftButton();
        newShiftPage.selectWorkRole(workRole);
        newShiftPage.selectChildLocInCreateShiftWindow("Child1");
        newShiftPage.moveSliderAtSomePoint("36", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        newShiftPage.moveSliderAtSomePoint("20", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
        newShiftPage.clickOnCreateOrNextBtn();

        //Edit meal break
        WebElement selectedShift = null;
        selectedShift = scheduleShiftTablePage.clickOnProfileIconOfShiftInDayView("open");
        String selectedShiftId= selectedShift.getAttribute("id");
        shiftOperatePage.verifyEditMealBreakTimeFunctionalityForAShiftInDayView(true, selectedShiftId);

        //edit shift time
        int index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
        String shiftInfoBefore = scheduleShiftTablePage.getTheShiftInfoByIndexInDayview(index);
        scheduleShiftTablePage.clickOnProfileIconOfShiftInDayView("open");
        shiftOperatePage.clickOnEditShiftTime();
        shiftOperatePage.verifyEditShiftTimePopUpDisplay();
        shiftOperatePage.editShiftTimeToTheLargest();
        shiftOperatePage.clickOnUpdateEditShiftTimeButton();
        index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
        String shiftInfoAfter= scheduleShiftTablePage.getTheShiftInfoByIndexInDayview(index);
        SimpleUtils.assertOnFail("Auto shift time is not updated!", !shiftInfoBefore.contains(shiftInfoAfter), false);


        //change work role
        scheduleShiftTablePage.clickOnProfileIconOfShiftInDayView("open");
        shiftOperatePage.changeWorkRoleInPromptOfAShiftInDayView(true, selectedShiftId);
        index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
        String shiftInfoAfterChangeRole= scheduleShiftTablePage.getTheShiftInfoByIndexInDayview(index);
        SimpleUtils.assertOnFail("Work role is not updated!", !shiftInfoBefore.contains(shiftInfoAfterChangeRole), false);


        //View status
        scheduleShiftTablePage.clickOnProfileIconOfShiftInDayView("open");
        scheduleShiftTablePage.clickViewStatusBtn();
        //shiftOperatePage.verifyListOfOfferNotNull();
        shiftOperatePage.closeViewStatusContainer();

        //Assign TM
        scheduleShiftTablePage.clickOnProfileIconOfShiftInDayView("open");
        shiftOperatePage.clickonAssignTM();
        newShiftPage.verifySelectTeamMembersOption();
        newShiftPage.clickOnOfferOrAssignBtn();
        index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
        String shiftInfoAfterAssignTM= scheduleShiftTablePage.getTheShiftInfoByIndexInDayview(index);
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
    public void validateTheFunctionOfAutoOpenShiftInDayViewAsInternalAdminP2P (String username, String password, String browser, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isActiveWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectChildLocationFilterByText("Mountain View");
            scheduleMainPage.clickOnFilterBtn();
            scheduleCommonPage.clickOnDayView();

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.deleteAllShiftsInDayView();
            //shiftOperatePage.deleteTMShiftInWeekView("Open");
            scheduleMainPage.saveSchedule();


            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            String workRole = shiftOperatePage.getRandomWorkRole();
            //Create auto open shift.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Mountain View");
            newShiftPage.moveSliderAtSomePoint("36", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("20", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();

            WebElement selectedShift = scheduleShiftTablePage.clickOnProfileIconOfShiftInDayView("open");
            String selectedShiftId= selectedShift.getAttribute("id");

            //Edit meal break
            //shiftOperatePage.verifyEditMealBreakTimeFunctionalityForAShiftInDayView(true, selectedShiftId);

            //edit shift time
            int index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            String shiftInfoBefore = scheduleShiftTablePage.getTheShiftInfoByIndexInDayview(index);
            scheduleShiftTablePage.clickOnProfileIconOfShiftInDayView("open");
            shiftOperatePage.clickOnEditShiftTime();
            shiftOperatePage.verifyEditShiftTimePopUpDisplay();
            shiftOperatePage.editShiftTimeToTheLargest();
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            String shiftInfoAfter= scheduleShiftTablePage.getTheShiftInfoByIndexInDayview(index);
            SimpleUtils.assertOnFail("Auto shift time is not updated!", !shiftInfoBefore.contains(shiftInfoAfter), false);


            //change work role
            scheduleShiftTablePage.clickOnProfileIconOfShiftInDayView("open");
            shiftOperatePage.changeWorkRoleInPromptOfAShiftInDayView(true, selectedShiftId);
            index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            String shiftInfoAfterChangeRole= scheduleShiftTablePage.getTheShiftInfoByIndexInDayview(index);
            SimpleUtils.assertOnFail("Work role is not updated!", !shiftInfoBefore.contains(shiftInfoAfterChangeRole), false);


            //View status
            scheduleShiftTablePage.clickOnProfileIconOfShiftInDayView("open");
            scheduleShiftTablePage.clickViewStatusBtn();
            //shiftOperatePage.verifyListOfOfferNotNull();
            shiftOperatePage.closeViewStatusContainer();

            //Assign TM
            scheduleShiftTablePage.clickOnProfileIconOfShiftInDayView("open");
            shiftOperatePage.clickonAssignTM();
            newShiftPage.verifySelectTeamMembersOption();
            newShiftPage.clickOnOfferOrAssignBtn();
            index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            String shiftInfoAfterAssignTM= scheduleShiftTablePage.getTheShiftInfoByIndexInDayview(index);
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
    public void validateTheFunctionOfManualOpenShiftAsInternalAdminP2P (String username, String password, String browser, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            //scheduleCommonPage.navigateToNextWeek();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            List<String> toCloseDays = new ArrayList<>();
            createSchedulePage.clickCreateScheduleBtn();
            createSchedulePage.chooseLocationInCreateSchedulePopupWindow("Mountain View");
            createSchedulePage.editTheOperatingHoursForLGInPopupWinodw(toCloseDays);
            createSchedulePage.chooseLocationInCreateSchedulePopupWindow("Carmel Club DG Oregon");
            createSchedulePage.editTheOperatingHoursForLGInPopupWinodw(toCloseDays);
            createSchedulePage.clickNextBtnOnCreateScheduleWindow();
            createSchedulePage.selectWhichWeekToCopyFrom("SUGGESTED");
            createSchedulePage.clickNextBtnOnCreateScheduleWindow();


            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectChildLocationFilterByText("Carmel Club DG Oregon");
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.deleteTMShiftInWeekView("Open");
            shiftOperatePage.deleteTMShiftInWeekView("Abigayle");
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectChildLocationFilterByText("Mountain View");
            shiftOperatePage.deleteTMShiftInWeekView("Open");
            shiftOperatePage.deleteTMShiftInWeekView("Marlon");
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            String workRole = shiftOperatePage.getRandomWorkRole();
            //Create shift and assign to TM.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Mountain View");
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("28", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.searchTeamMemberByName("Marlon");
            newShiftPage.clickOnOfferOrAssignBtn();

            //Create shift and assign to TM.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Mountain View");
            newShiftPage.moveSliderAtSomePoint("40", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.searchTeamMemberByName("Marlon");
            newShiftPage.clickOnOfferOrAssignBtn();

            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.deleteTMShiftInWeekView("Marlon");
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            //Create auto open shift.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Carmel Club DG Oregon");
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("28", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.searchTeamMemberByName("Marlon");
            //newShiftPage.verifySelectTeamMembersOption();
            newShiftPage.clickOnOfferOrAssignBtn();


            //Edit meal break
            WebElement selectedShift = null;
            selectedShift = shiftOperatePage.clickOnProfileIconOfOpenShift();
            shiftOperatePage.verifyEditMealBreakTimeFunctionalityForAShift(true, selectedShift);

            //edit shift time

            selectedShift = shiftOperatePage.clickOnProfileIconOfOpenShift();
            String selectedShiftId= selectedShift.getAttribute("id");
            int index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoBefore = scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            shiftOperatePage.clickOnEditShiftTime();
            shiftOperatePage.verifyEditShiftTimePopUpDisplay();
            shiftOperatePage.editShiftTimeToTheLargest();
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoAfter= scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            SimpleUtils.assertOnFail("Auto shift time is not updated!", !shiftInfoBefore.containsAll(shiftInfoAfter), false);


            //change work role
            selectedShift = shiftOperatePage.clickOnProfileIconOfOpenShift();
            shiftOperatePage.clickOnChangeRole();
            shiftOperatePage.verifyChangeRoleFunctionality();
            //check the work role by click Apply button
            shiftOperatePage.changeWorkRoleInPromptOfAShift(true, selectedShift);
            index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoAfterChangeRole= scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            SimpleUtils.assertOnFail("Work role is not updated!", !shiftInfoBefore.containsAll(shiftInfoAfterChangeRole), false);


            //View status
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            scheduleShiftTablePage.clickViewStatusBtn();
            //shiftOperatePage.verifyListOfOfferNotNull();
            shiftOperatePage.closeViewStatusContainer();

            scheduleMainPage.clickOnCancelButtonOnEditMode();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            //Create shift and assign to TM.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Mountain View");
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("28", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.searchTeamMemberByName("Marlon");
            newShiftPage.clickOnOfferOrAssignBtn();

            //verify travel violation message when assign TM.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Carmel Club DG Oregon");
            newShiftPage.moveSliderAtSomePoint("44", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.searchTeamMemberByName("Marlon");
            String actualMessage = shiftOperatePage.getAllTheWarningMessageOfTMWhenAssign();
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
    public void validateTheFunctionOfManualOpenShiftAsInternalAdminPC (String username, String password, String browser, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("District Whistler");
            locationSelectorPage.changeLocation("Lift Ops_Parent");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            //scheduleCommonPage.navigateToNextWeek();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            List<String> toCloseDays = new ArrayList<>();
            createSchedulePage.clickCreateScheduleBtn();
            createSchedulePage.chooseLocationInCreateSchedulePopupWindow("Child1");
            createSchedulePage.editTheOperatingHoursForLGInPopupWinodw(toCloseDays);
            createSchedulePage.chooseLocationInCreateSchedulePopupWindow("Child2");
            createSchedulePage.editTheOperatingHoursForLGInPopupWinodw(toCloseDays);
            createSchedulePage.clickNextBtnOnCreateScheduleWindow();
            createSchedulePage.selectWhichWeekToCopyFrom("SUGGESTED");
            createSchedulePage.clickNextBtnOnCreateScheduleWindow();

            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectChildLocationFilterByText("Child2");
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.deleteTMShiftInWeekView("Open");
            shiftOperatePage.deleteTMShiftInWeekView("Abigayle");
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectChildLocationFilterByText("Child1");
            shiftOperatePage.deleteTMShiftInWeekView("Open");
            shiftOperatePage.deleteTMShiftInWeekView("Abigayle");
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            String workRole = shiftOperatePage.getRandomWorkRole();
            //Create shift and assign to TM.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Child1");
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("28", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.searchTeamMemberByName("Abigayle");
            newShiftPage.clickOnOfferOrAssignBtn();

            //Create shift and assign to TM.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Child2");
            newShiftPage.moveSliderAtSomePoint("40", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.searchTeamMemberByName("Abigayle");
            newShiftPage.clickOnOfferOrAssignBtn();

            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.deleteTMShiftInWeekView("Abigayle");
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            //Create auto open shift.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Child1");
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("28", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.searchTeamMemberByName("Abigayle");
            //newShiftPage.verifySelectTeamMembersOption();
            newShiftPage.clickOnOfferOrAssignBtn();


            //Edit meal break
            WebElement selectedShift = null;
            selectedShift = shiftOperatePage.clickOnProfileIconOfOpenShift();
            shiftOperatePage.verifyEditMealBreakTimeFunctionalityForAShift(true, selectedShift);

            //edit shift time

            selectedShift = shiftOperatePage.clickOnProfileIconOfOpenShift();
            String selectedShiftId= selectedShift.getAttribute("id");
            int index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoBefore = scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            shiftOperatePage.clickOnEditShiftTime();
            shiftOperatePage.verifyEditShiftTimePopUpDisplay();
            shiftOperatePage.editShiftTimeToTheLargest();
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoAfter= scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            SimpleUtils.assertOnFail("Auto shift time is not updated!", !shiftInfoBefore.containsAll(shiftInfoAfter), false);


            //change work role
            selectedShift = shiftOperatePage.clickOnProfileIconOfOpenShift();
            shiftOperatePage.clickOnChangeRole();
            shiftOperatePage.verifyChangeRoleFunctionality();
            //check the work role by click Apply button
            shiftOperatePage.changeWorkRoleInPromptOfAShift(true, selectedShift);
            index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            List<String> shiftInfoAfterChangeRole= scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            SimpleUtils.assertOnFail("Work role is not updated!", !shiftInfoBefore.containsAll(shiftInfoAfterChangeRole), false);


            //View status
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            scheduleShiftTablePage.clickViewStatusBtn();
            //shiftOperatePage.verifyListOfOfferNotNull();
            shiftOperatePage.closeViewStatusContainer();

            scheduleMainPage.clickOnCancelButtonOnEditMode();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            //Create shift and assign to TM.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Child1");
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("28", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.searchTeamMemberByName("Abigayle");
            newShiftPage.clickOnOfferOrAssignBtn();

            //verify travel violation message when assign TM.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Child2");
            newShiftPage.moveSliderAtSomePoint("44", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.searchTeamMemberByName("Abigayle");
            String actualMessage = shiftOperatePage.getAllTheWarningMessageOfTMWhenAssign();
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
    public void validateTheFunctionOfManualOpenShiftInDayViewAsInternalAdminPC (String username, String password, String browser, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("District Whistler");
            locationSelectorPage.changeLocation("Lift Ops_Parent");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            List<String> toCloseDays = new ArrayList<>();
            createSchedulePage.clickCreateScheduleBtn();
            createSchedulePage.chooseLocationInCreateSchedulePopupWindow("Child1");
            createSchedulePage.editTheOperatingHoursForLGInPopupWinodw(toCloseDays);
            createSchedulePage.chooseLocationInCreateSchedulePopupWindow("Child2");
            createSchedulePage.editTheOperatingHoursForLGInPopupWinodw(toCloseDays);
            createSchedulePage.clickNextBtnOnCreateScheduleWindow();
            createSchedulePage.selectWhichWeekToCopyFrom("SUGGESTED");
            createSchedulePage.clickNextBtnOnCreateScheduleWindow();

            scheduleCommonPage.clickOnDayView();
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectChildLocationFilterByText("Child1");
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.deleteAllShiftsInDayView();
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectChildLocationFilterByText("Child2");
            shiftOperatePage.deleteAllShiftsInDayView();
            scheduleMainPage.saveSchedule();

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            String workRole = shiftOperatePage.getRandomWorkRole();
            //Create shift and assign to TM.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Child1");
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("20", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.searchTeamMemberByName("Abigayle");
            newShiftPage.clickOnOfferOrAssignBtn();

            //Create shift and assign to TM.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Child2");
            newShiftPage.moveSliderAtSomePoint("44", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.searchTeamMemberByName("Abigayle");
            newShiftPage.clickOnOfferOrAssignBtn();

            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.deleteAllShiftsInDayView();
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            //Create manual open shift.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Child2");
            newShiftPage.moveSliderAtSomePoint("44", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.searchTeamMemberByName("Abigayle");
            String actualMessage = shiftOperatePage.getAllTheWarningMessageOfTMWhenAssign();
            SimpleUtils.assertOnFail("No travel time needed violation!", actualMessage.toLowerCase().contains("travel time needed"), false);
            newShiftPage.clickOnOfferOrAssignBtn();


            //Edit meal break
            WebElement selectedShift = null;
            selectedShift = scheduleShiftTablePage.clickOnProfileIconOfShiftInDayView("open");
            String selectedShiftId= selectedShift.getAttribute("id");
            shiftOperatePage.verifyEditMealBreakTimeFunctionalityForAShiftInDayView(true, selectedShiftId);

            //edit shift time
            int index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            String shiftInfoBefore = scheduleShiftTablePage.getTheShiftInfoByIndexInDayview(index);
            scheduleShiftTablePage.clickOnProfileIconOfShiftInDayView("open");
            shiftOperatePage.clickOnEditShiftTime();
            shiftOperatePage.verifyEditShiftTimePopUpDisplay();
            shiftOperatePage.editShiftTimeToTheLargest();
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();
            index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            String shiftInfoAfter= scheduleShiftTablePage.getTheShiftInfoByIndexInDayview(index);
            SimpleUtils.assertOnFail("Auto shift time is not updated!", !shiftInfoBefore.contains(shiftInfoAfter), false);


            //change work role
            scheduleShiftTablePage.clickOnProfileIconOfShiftInDayView("open");
            //check the work role by click Apply button
            shiftOperatePage.changeWorkRoleInPromptOfAShiftInDayView(true, selectedShiftId);
            index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            String shiftInfoAfterChangeRole= scheduleShiftTablePage.getTheShiftInfoByIndexInDayview(index);
            SimpleUtils.assertOnFail("Work role is not updated!", !shiftInfoBefore.contains(shiftInfoAfterChangeRole), false);

            //View status
            scheduleShiftTablePage.clickOnProfileIconOfShiftInDayView("open");
            scheduleShiftTablePage.clickViewStatusBtn();
            //shiftOperatePage.verifyListOfOfferNotNull();
            shiftOperatePage.closeViewStatusContainer();

            //assign TM.
            scheduleShiftTablePage.clickOnProfileIconOfShiftInDayView("open");
            shiftOperatePage.clickonAssignTM();
            newShiftPage.verifySelectTeamMembersOption();
            newShiftPage.clickOnOfferOrAssignBtn();
            index = scheduleShiftTablePage.getShiftIndexById(selectedShiftId);
            String shiftInfoAfterAssignTM= scheduleShiftTablePage.getTheShiftInfoByIndexInDayview(index);
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
    public void validateAssignTMWhenTMHasMaxNoOfShiftsScheduledAsInternalAdminPC (String username, String password, String browser, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict("District Whistler");
        locationSelectorPage.changeLocation("Lift Ops_Parent");
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        //Go to controls set max no. of shifts per week
        ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
        controlsPage.gotoControlsPage();
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        controlsNewUIPage.clickOnControlsSchedulingPolicies();
        controlsNewUIPage.clickOnGlobalLocationButton();
        controlsNewUIPage.updateSchedulingPolicyGroupsShiftsPerWeek(3, 5, 5);


        //Go to schedule page to check assign TM.
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        scheduleCommonPage.navigateToNextWeek();
        scheduleCommonPage.navigateToNextWeek();
/*        boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
        if(isActiveWeekGenerated){
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        List<String> toCloseDays = new ArrayList<>();
        createSchedulePage.clickCreateScheduleBtn();
        createSchedulePage.chooseLocationInCreateSchedulePopupWindow("Child1");
        createSchedulePage.editTheOperatingHoursForLGInPopupWinodw(toCloseDays);
        createSchedulePage.clickNextBtnOnCreateScheduleWindow();
        createSchedulePage.selectWhichWeekToCopyFrom("SUGGESTED");
        createSchedulePage.clickNextBtnOnCreateScheduleWindow();
*/        //scheduleMainPage.clickOnFilterBtn();
        //scheduleMainPage.selectChildLocationFilterByText("Child1");
        List<String> shiftInfo = new ArrayList<>();
        while (shiftInfo.size() == 0) {
            shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
        }
        String firstNameOfTM1 = shiftInfo.get(0);
        String workRoleOfTM1 = shiftInfo.get(4);
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        scheduleMainPage.searchShiftOnSchedulePage(firstNameOfTM1);
        shiftOperatePage.deleteTMShiftInWeekView(firstNameOfTM1);
        scheduleMainPage.clickOnCloseSearchBoxButton();
        scheduleMainPage.saveSchedule();

        scheduleMainPage.clickOnFilterBtn();
        scheduleMainPage.selectChildLocationFilterByText("Child1");
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

        //Create shift and assign to TM.
        newShiftPage.clickOnDayViewAddNewShiftButton();
        newShiftPage.selectWorkRole(workRoleOfTM1);
        newShiftPage.selectChildLocInCreateShiftWindow("Child1");
        newShiftPage.selectSpecificWorkDay(1);
        newShiftPage.selectSpecificWorkDay(2);
        newShiftPage.selectSpecificWorkDay(3);
        newShiftPage.selectSpecificWorkDay(4);
        newShiftPage.selectSpecificWorkDay(5);
        newShiftPage.moveSliderAtCertainPoint("9", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        newShiftPage.moveSliderAtCertainPoint("11", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        //newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        //newShiftPage.moveSliderAtSomePoint("20", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
        newShiftPage.clickOnCreateOrNextBtn();
        newShiftPage.customizeNewShiftPage();
        newShiftPage.searchTeamMemberByName(firstNameOfTM1);
        newShiftPage.clickOnOfferOrAssignBtn();

        //Create manual open shift and assign TM.
        newShiftPage.clickOnDayViewAddNewShiftButton();
        newShiftPage.selectWorkRole(workRoleOfTM1);
        newShiftPage.selectChildLocInCreateShiftWindow("Child1");
        newShiftPage.selectSpecificWorkDay(6);
        newShiftPage.moveSliderAtCertainPoint("9", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        newShiftPage.moveSliderAtCertainPoint("11", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
        newShiftPage.clickOnCreateOrNextBtn();
        newShiftPage.customizeNewShiftPage();
        newShiftPage.searchTeamMemberByName(firstNameOfTM1);
        String actualMessage = shiftOperatePage.getAllTheWarningMessageOfTMWhenAssign();
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
    public void verifyActivityOfClaimOpenShiftAsTeamMemberLGPC(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String teamMemberName = profileNewUIPage.getNickNameFromProfile();
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
        loginPage.logOut();

        loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
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
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());

        //to generate schedule  if current week is not generated
        scheduleCommonPage.navigateToNextWeek();
        scheduleCommonPage.navigateToNextWeek();
        boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
        if(!isActiveWeekGenerated){
            createSchedulePage.createScheduleForNonDGFlowNewUI();
        }
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        scheduleMainPage.clickOnOpenSearchBoxButton();
        scheduleMainPage.searchShiftOnSchedulePage(teamMemberName);
        shiftOperatePage.deleteTMShiftInWeekView("Unassigned");
        shiftOperatePage.deleteTMShiftInWeekView(teamMemberName);
        scheduleMainPage.saveSchedule();

        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        String workRole = shiftOperatePage.getRandomWorkRole();
        newShiftPage.clickOnDayViewAddNewShiftButton();
        newShiftPage.customizeNewShiftPage();
        newShiftPage.selectWorkRole(workRole);
        newShiftPage.clearAllSelectedDays();
        newShiftPage.selectSpecificWorkDay(1);
        List<String> locations = newShiftPage.getAllLocationGroupLocationsFromCreateShiftWindow();
        newShiftPage.selectChildLocInCreateShiftWindow(locations.get((new Random()).nextInt(locations.size()-1)+1));
        newShiftPage.moveSliderAtSomePoint("40", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        newShiftPage.moveSliderAtSomePoint("20", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
        newShiftPage.clickOnCreateOrNextBtn();
        if(newShiftPage.ifWarningModeDisplay()){
            scheduleShiftTablePage.clickOnOkButtonInWarningMode();
        }
        newShiftPage.searchTeamMemberByName(teamMemberName);
        newShiftPage.clickOnOfferOrAssignBtn();
        scheduleMainPage.saveSchedule();
        createSchedulePage.publishActiveSchedule();
        loginPage.logOut();

        // 3.Login with the TM to claim the shift
        loginToLegionAndVerifyIsLoginDone(username, password, location);
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
//        dashboardPage.goToTodayForNewUI();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        scheduleCommonPage.navigateToNextWeek();
        scheduleCommonPage.navigateToNextWeek();
        scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        String cardName = "WANT MORE HOURS?";
        SimpleUtils.assertOnFail("Smart Card: " + cardName + " not loaded Successfully!", smartCardPage.isSpecificSmartCardLoaded(cardName), false);
        String linkName = "View Shifts";
        smartCardPage.clickLinkOnSmartCardByName(linkName);
        SimpleUtils.assertOnFail("Open shifts not loaed Successfully!", scheduleShiftTablePage.areShiftsPresent(), false);
        List<String> claimShift = new ArrayList<>(Arrays.asList("Claim Shift"));
        mySchedulePage.selectOneShiftIsClaimShift(claimShift);
        mySchedulePage.clickTheShiftRequestByName(claimShift.get(0));
        mySchedulePage.verifyClickAgreeBtnOnClaimShiftOffer();

        loginPage.logOut();

        // 4.Login with SM to check activity
        loginAsDifferentRole(AccessRoles.StoreManagerLG.getValue());
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftOffer.getValue(), ActivityTest.indexOfActivityType.ShiftOffer.name());
        activityPage.verifyActivityOfShiftOffer(teamMemberName, "");
        activityPage.approveOrRejectShiftOfferRequestOnActivity(teamMemberName, ActivityTest.approveRejectAction.Approve.getValue());

        //Check the shift been scheduled
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        //to generate schedule  if current week is not generated
        scheduleCommonPage.navigateToNextWeek();
        scheduleCommonPage.navigateToNextWeek();
        scheduleMainPage.clickOnOpenSearchBoxButton();
        scheduleMainPage.searchShiftOnSchedulePage(teamMemberName);
        SimpleUtils.assertOnFail("", scheduleShiftTablePage.getOneDayShiftByName(0, teamMemberName).size()>0, false);
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "P2P:Validate TMs Can Receive & Accept Offers for Multiple Shifts and Multiple Locations")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyActivityOfClaimOpenShiftAsTeamMemberLGP2P(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        String teamMemberName = profileNewUIPage.getNickNameFromProfile();
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();

        loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
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
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        //to generate schedule  if current week is not generated
        scheduleCommonPage.navigateToNextWeek();
        boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
        if(!isActiveWeekGenerated){
            createSchedulePage.createScheduleForNonDGFlowNewUI();
        }
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        scheduleMainPage.clickOnOpenSearchBoxButton();
        scheduleMainPage.searchShiftOnSchedulePage(teamMemberName);
        shiftOperatePage.deleteTMShiftInWeekView("Unassigned");
        shiftOperatePage.deleteTMShiftInWeekView(teamMemberName);
        scheduleMainPage.saveSchedule();

        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        String workRole = shiftOperatePage.getRandomWorkRole();
        newShiftPage.clickOnDayViewAddNewShiftButton();
        newShiftPage.customizeNewShiftPage();
        newShiftPage.selectWorkRole(workRole);
        newShiftPage.clearAllSelectedDays();
        newShiftPage.selectSpecificWorkDay(1);
//        List<String> locations = newShiftPage.getAllLocationGroupLocationsFromCreateShiftWindow();
        newShiftPage.selectChildLocInCreateShiftWindow("Carmel Club DG Oregon");
        newShiftPage.moveSliderAtSomePoint("44", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        newShiftPage.moveSliderAtSomePoint("22", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
        newShiftPage.clickOnCreateOrNextBtn();
        if(newShiftPage.ifWarningModeDisplay()){
            scheduleShiftTablePage.clickOnOkButtonInWarningMode();
        }
        newShiftPage.searchTeamMemberByName(teamMemberName);
        newShiftPage.clickOnOfferOrAssignBtn();
        scheduleMainPage.saveSchedule();
        createSchedulePage.publishActiveSchedule();
        loginPage.logOut();

        // 3.Login with the TM to claim the shift
        loginToLegionAndVerifyIsLoginDone(username, password, location);
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
//        dashboardPage.goToTodayForNewUI();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        scheduleCommonPage.navigateToNextWeek();
        scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        String cardName = "WANT MORE HOURS?";
        SimpleUtils.assertOnFail("Smart Card: " + cardName + " not loaded Successfully!", smartCardPage.isSpecificSmartCardLoaded(cardName), false);
        String linkName = "View Shifts";
        smartCardPage.clickLinkOnSmartCardByName(linkName);
        SimpleUtils.assertOnFail("Open shifts not loaed Successfully!", scheduleShiftTablePage.areShiftsPresent(), false);
        List<String> claimShift = new ArrayList<>(Arrays.asList("Claim Shift"));
        mySchedulePage.selectOneShiftIsClaimShift(claimShift);
        mySchedulePage.clickTheShiftRequestByName(claimShift.get(0));
        mySchedulePage.verifyClickAgreeBtnOnClaimShiftOffer();

        loginPage.logOut();

        // 4.Login with SM to check activity
        loginAsDifferentRole(AccessRoles.StoreManagerLG.getValue());
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
//        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftOffer.getValue(), ActivityTest.indexOfActivityType.ShiftOffer.name());
        activityPage.verifyActivityOfShiftOffer(teamMemberName,"");
        activityPage.approveOrRejectShiftOfferRequestOnActivity(teamMemberName, ActivityTest.approveRejectAction.Approve.getValue());

        //Check the shift been scheduled
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        //to generate schedule  if current week is not generated
        scheduleCommonPage.navigateToNextWeek();
        scheduleMainPage.clickOnOpenSearchBoxButton();
        scheduleMainPage.searchShiftOnSchedulePage(teamMemberName);
        SimpleUtils.assertOnFail("", scheduleShiftTablePage.getOneDayShiftByName(0, teamMemberName).size()>0, false);
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate that operate LG schedule by different user")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateOperateLGScheduleByDifferentUserAsInternalAdminPC (String username, String password, String browser, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("District Whistler");
            locationSelectorPage.changeLocation("Lift Ops_Parent");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();

            // Verify operate schedule by admin user
            /// Generate schedule
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            /// Publish schedule
            if(createSchedulePage.isPublishButtonLoadedOnSchedulePage() || createSchedulePage.isRepublishButtonLoadedOnSchedulePage())
                createSchedulePage.publishActiveSchedule();

            /// Add shifts in schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.addOpenShiftWithDefaultTime(shiftOperatePage.getRandomWorkRole(),"Child2");

            /// Edit shifts(include edit shift time, assign TM, delete...)
            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.clickOnEditShiftTime();
            shiftOperatePage.editShiftTime();
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();

            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.clickonAssignTM();
            newShiftPage.selectTeamMembers();
            newShiftPage.clickOnOfferOrAssignBtn();
            scheduleMainPage.saveSchedule();

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleMainPage.selectShiftTypeFilterByText("Compliance Review");
            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.verifyDeleteShift();

            /// Republish schedule
            createSchedulePage.publishActiveSchedule();

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            // Verify operate schedule by SM user
            /// Login as Store Manager
            loginAsDifferentRole(AccessRoles.StoreManagerLG.getValue());
            dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();

            /// Generate schedule
            isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            /// Publish schedule
            if(createSchedulePage.isPublishButtonLoadedOnSchedulePage() || createSchedulePage.isRepublishButtonLoadedOnSchedulePage())
                createSchedulePage.publishActiveSchedule();

            /// Add shifts in schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.addOpenShiftWithDefaultTime(shiftOperatePage.getRandomWorkRole(),"Child2");

            /// Edit shifts(include edit shift time, assign TM, delete...)
            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.clickOnEditShiftTime();
            shiftOperatePage.editShiftTime();
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();

            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.clickonAssignTM();
            newShiftPage.selectTeamMembers();
            newShiftPage.clickOnOfferOrAssignBtn();
            scheduleMainPage.saveSchedule();

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleMainPage.selectShiftTypeFilterByText("Compliance Review");
            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.verifyDeleteShift();

            /// Republish schedule
            createSchedulePage.publishActiveSchedule();

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Validate that operate LG schedule by different user")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateOperateLGScheduleByDifferentUserAsInternalAdminP2P (String username, String password, String browser, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            // Verify operate schedule by admin user

            /// Generate schedule
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            /// Publish schedule
            if(createSchedulePage.isPublishButtonLoadedOnSchedulePage() || createSchedulePage.isRepublishButtonLoadedOnSchedulePage())
                createSchedulePage.publishActiveSchedule();

            /// Add shifts in schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.addOpenShiftWithDefaultTime(shiftOperatePage.getRandomWorkRole(),"Mountain View");

            /// Edit shifts(include edit shift time, assign TM, delete...)
            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.clickOnEditShiftTime();
            shiftOperatePage.editShiftTime();
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();

            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.clickonAssignTM();
            newShiftPage.selectTeamMembers();
            newShiftPage.clickOnOfferOrAssignBtn();
            scheduleMainPage.saveSchedule();

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleMainPage.selectShiftTypeFilterByText("Compliance Review");
            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.verifyDeleteShift();

            /// Republish schedule
            createSchedulePage.publishActiveSchedule();

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            // Verify operate schedule by SM user
            /// Login as Store Manager
            loginAsDifferentRole(AccessRoles.StoreManagerLG.getValue());
            dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();

            /// Generate schedule
            isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            /// Publish schedule
            if(createSchedulePage.isPublishButtonLoadedOnSchedulePage() || createSchedulePage.isRepublishButtonLoadedOnSchedulePage())
                createSchedulePage.publishActiveSchedule();

            /// Add shifts in schedule
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.addOpenShiftWithDefaultTime(shiftOperatePage.getRandomWorkRole());

            /// Edit shifts(include edit shift time, assign TM, delete...)
            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.clickOnEditShiftTime();
            shiftOperatePage.editShiftTime();
            shiftOperatePage.clickOnUpdateEditShiftTimeButton();

            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.clickonAssignTM();
            newShiftPage.selectTeamMembers();
            newShiftPage.clickOnOfferOrAssignBtn();
            scheduleMainPage.saveSchedule();

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleMainPage.selectShiftTypeFilterByText("Compliance Review");
            shiftOperatePage.clickOnProfileIcon();
            shiftOperatePage.verifyDeleteShift();

            /// Republish schedule
            createSchedulePage.publishActiveSchedule();

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate UI performance for large roster (500 employees) with one location as well as multiple location groups")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateUIPerformanceForLargeRosterAsInternalAdminPC (String username, String password, String browser, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("District Whistler");
            locationSelectorPage.changeLocation("Lift Ops_Parent");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            // Verify LG schedule can be generated with large TMs in 2 mins
            /// Generate one schedule with more than 500 TMs
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            // Verify LG schedule can be edited with large TMs in 2 mins
            /// Edit this schedule, save and publish it
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleMainPage.saveSchedule();
            createSchedulePage.publishActiveSchedule();

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Validate UI performance for large roster (500 employees) with one location as well as multiple location groups")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateUIPerformanceForLargeRosterAsInternalAdminP2P (String username, String password, String browser, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            // Verify LG schedule can be generated with large TMs in 2 mins
            /// Generate one schedule with more than 500 TMs
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            // Verify LG schedule can be edited with large TMs in 2 mins
            /// Edit this schedule, save and publish it
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleMainPage.saveSchedule();
            createSchedulePage.publishActiveSchedule();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }



    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate Print Schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validatePrintScheduleAsInternalAdminPC (String username, String password, String browser, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("District Whistler");
            locationSelectorPage.changeLocation("Lift Ops_Parent");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            // Verify the LG schedule can be printed and the shift display correctly in print file in week view
            /// Go to one generated schedule
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            /// In week view, print the schedule by click Print button
            SimpleUtils.assertOnFail("Print Icon not loaded Successfully!", scheduleMainPage.isPrintIconLoaded(), false);
            String handle = getDriver().getWindowHandle();
            scheduleMainPage.verifyThePrintFunction();

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
            scheduleMainPage.selectLocationFilterByText(subLocation);
            HashMap<String, String> hoursOnSchedule = smartCardPage.getHoursFromSchedulePage();
            int shiftsCount = scheduleShiftTablePage.getShiftsCount();

            /// Compare the data for one sub location in printed file and schedule page
            if (content.contains(hoursOnSchedule.get("Scheduled")) && content.contains(""+shiftsCount)) {
                SimpleUtils.report("The scheduled hours of " + subLocation+ " is " + hoursOnSchedule.get("Scheduled") + " Hrs");
                SimpleUtils.report("The shifts count  of " + subLocation + " is " + shiftsCount + " Shifts");
                SimpleUtils.pass("Schedule page: The content in printed file in week view displays correctly");
            } else
                SimpleUtils.fail("Schedule page: The content in printed file in week view displays incorrectly",false);

            // Verify the LG schedule can be printed and the shift display correctly in print file in day view
            /// In day view, print the schedule by click Print button
            scheduleCommonPage.clickOnDayView();
            SimpleUtils.assertOnFail("Print Icon not loaded Successfully!", scheduleMainPage.isPrintIconLoaded(), false);
            scheduleMainPage.verifyThePrintFunction();

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
            scheduleMainPage.selectLocationFilterByText(subLocation);
            hoursOnSchedule = smartCardPage.getHoursFromSchedulePage();
            shiftsCount = scheduleShiftTablePage.getAvailableShiftsInDayView().size();

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
    public void validatePrintScheduleAsInternalAdminP2P (String username, String password, String browser, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();

            // Verify the LG schedule can be printed and the shift display correctly in print file in week view
            /// Go to one generated schedule
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isActiveWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            /// In week view, print the schedule by click Print button
            SimpleUtils.assertOnFail("Print Icon not loaded Successfully!", scheduleMainPage.isPrintIconLoaded(), false);
            String handle = getDriver().getWindowHandle();
            scheduleMainPage.verifyThePrintFunction();

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
            scheduleMainPage.selectLocationFilterByText(subLocation);
            HashMap<String, String> hoursOnSchedule = smartCardPage.getHoursFromSchedulePage();
            int shiftsCount = scheduleShiftTablePage.getShiftsCount();

            /// Compare the data for one sub location in printed file and schedule page
            if (content.contains(hoursOnSchedule.get("Scheduled")) && content.contains("" + shiftsCount)) {
                SimpleUtils.report("The scheduled hours of " + subLocation + " is " + hoursOnSchedule.get("Scheduled") + " Hrs");
                SimpleUtils.report("The shifts count  of " + subLocation + " is " + shiftsCount + " Shifts");
                SimpleUtils.pass("Schedule page: The content in printed file in week view displays correctly");
            } else
                SimpleUtils.fail("Schedule page: The content in printed file in week view displays incorrectly", false);

            // Verify the LG schedule can be printed and the shift display correctly in print file in day view
            /// In day view, print the schedule by click Print button
            scheduleCommonPage.clickOnDayView();
            SimpleUtils.assertOnFail("Print Icon not loaded Successfully!", scheduleMainPage.isPrintIconLoaded(), false);
            scheduleMainPage.verifyThePrintFunction();

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
            scheduleMainPage.selectLocationFilterByText(subLocation);
            hoursOnSchedule = smartCardPage.getHoursFromSchedulePage();
            shiftsCount = scheduleShiftTablePage.getAvailableShiftsInDayView().size();

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
    public void prepareTheSwapShiftsAsInternalAdminPC(String browser, String username, String password, String location) throws Exception {
        try {
            swapCoverNames = new ArrayList<>();
            swapCoverCredentials = getSwapCoverUserCredentials(location);
            for (Map.Entry<String, Object[][]> entry : swapCoverCredentials.entrySet()) {
                swapCoverNames.add(entry.getKey());
            }
            workRoleName = String.valueOf(swapCoverCredentials.get(swapCoverNames.get(0))[0][3]);

            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            // Deleting the existing shifts for swap team members
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleMainPage.selectGroupByFilter("Group by All");
            scheduleMainPage.clickOnOpenSearchBoxButton();
            scheduleMainPage.searchShiftOnSchedulePage(swapCoverNames.get(0));
            shiftOperatePage.deleteTMShiftInWeekView(swapCoverNames.get(0));
            scheduleMainPage.searchShiftOnSchedulePage(swapCoverNames.get(1));
            shiftOperatePage.deleteTMShiftInWeekView(swapCoverNames.get(1));
            scheduleMainPage.clickOnCloseSearchBoxButton();
            if(smartCardPage.isRequiredActionSmartCardLoaded()){
                smartCardPage.clickOnViewShiftsBtnOnRequiredActionSmartCard();
                shiftOperatePage.deleteTMShiftInWeekView("Unassigned");
                scheduleMainPage.clickOnFilterBtn();
                scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
                scheduleMainPage.clickOnFilterBtn();
            }
            scheduleMainPage.saveSchedule();
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
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the content of Shift Swap activity when TM request to swap the shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOfShiftSwapActivityAsInternalAdminPC(String browser, String username, String password, String location) throws Exception {
        prepareTheSwapShiftsAsInternalAdminPC(browser, username, password, location);
        SimpleUtils.report("Need to set 'Is approval by Manager required when an employee claims a shift swap or cover request?' to 'Always' First!");
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict("District Whistler");
        locationSelectorPage.changeLocation("Lift Ops_Parent");
        ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
        controlsPage.gotoControlsPage();
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
        SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
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
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
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
        scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
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
        loginAsDifferentRole(AccessRoles.StoreManagerLG.getValue());
        locationSelectorPage.changeDistrict("District Whistler");
        locationSelectorPage.changeLocation("Lift Ops_Parent");
        dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

        // Verify Activity Icon is loaded
        String actionLabel = "requested";
        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
        activityPage.verifyActivityBellIconLoaded();
        activityPage.verifyClickOnActivityIcon();
        activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, respondUserName, actionLabel, true, location);
        activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftSwap.getValue(), ActivityTest.indexOfActivityType.ShiftSwap.name());
        activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, respondUserName, actionLabel, false, location);
        activityPage.approveOrRejectShiftSwapRequestOnActivity(requestUserName, respondUserName, ActivityTest.approveRejectAction.Approve.getValue());
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the content of copy schedule for non dg flow")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheContentOfCopyScheduleAsInternalAdminPC(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("District Whistler");
            locationSelectorPage.changeLocation("Lift Ops_Parent");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            List<String> weekDaysToClose = new ArrayList<>();
            weekDaysToClose.add("Sunday");
            createSchedulePage.createScheduleForNonDGByWeekInfo("SUGGESTED", weekDaysToClose, null);
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.deleteTMShiftInWeekView("Unassigned");
            scheduleMainPage.saveSchedule();
            //Create auto open shift.

            //Create auto open shift.
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            String workRole = shiftOperatePage.getRandomWorkRole();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Child1");
            newShiftPage.moveSliderAtSomePoint("40", 0, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("22", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            scheduleMainPage.saveSchedule();
            createSchedulePage.publishActiveSchedule();

            // Get the hours and the count of the tms for each day, ex: "37.5 Hrs 5TMs"
            HashMap<String, String> hoursNTMsCountFirstWeek = scheduleShiftTablePage.getTheHoursNTheCountOfTMsForEachWeekDays();
            HashMap<String, List<String>> shiftsForEachDayFirstWeek = scheduleShiftTablePage.getTheContentOfShiftsForEachWeekDay();
            HashMap<String, String> budgetNScheduledHoursFirstWeek = smartCardPage.getBudgetNScheduledHoursFromSmartCard();
            String cardName = "COMPLIANCE";
            boolean isComplianceCardLoadedFirstWeek = smartCardPage.isSpecificSmartCardLoaded(cardName);
            int complianceShiftCountFirstWeek = 0;
            if (isComplianceCardLoadedFirstWeek) {
                complianceShiftCountFirstWeek = smartCardPage.getComplianceShiftCountFromSmartCard(cardName);
            }
            String firstWeekInfo = scheduleCommonPage.getActiveWeekText();
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

            scheduleCommonPage.navigateToNextWeek();

            //Full copy
            scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }


            createSchedulePage.createScheduleForNonDGByWeekInfo(firstWeekInfo, weekDaysToClose, null);

            HashMap<String, String> hoursNTMsCountSecondWeek = scheduleShiftTablePage.getTheHoursNTheCountOfTMsForEachWeekDays();
            HashMap<String, List<String>> shiftsForEachDaySecondWeek = scheduleShiftTablePage.getTheContentOfShiftsForEachWeekDay();
            HashMap<String, String> budgetNScheduledHoursSecondWeek = smartCardPage.getBudgetNScheduledHoursFromSmartCard();
            boolean isComplianceCardLoadedSecondWeek = smartCardPage.isSpecificSmartCardLoaded(cardName);
            int complianceShiftCountSecondWeek = 0;
            if (isComplianceCardLoadedFirstWeek) {
                complianceShiftCountSecondWeek = smartCardPage.getComplianceShiftCountFromSmartCard(cardName);
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
            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            List<String> specificShiftAssigments = new ArrayList<>();
            specificShiftAssigments.add("Lift Manager");
            specificShiftAssigments.add("Lift Maintenance");
            specificShiftAssigments.add("Lift Operator");
            createSchedulePage.createScheduleForNonDGByWeekInfo(firstWeekInfo, weekDaysToClose, specificShiftAssigments);

            hoursNTMsCountSecondWeek = scheduleShiftTablePage.getTheHoursNTheCountOfTMsForEachWeekDays();
            shiftsForEachDaySecondWeek = scheduleShiftTablePage.getTheContentOfShiftsForEachWeekDay();
            budgetNScheduledHoursSecondWeek = smartCardPage.getBudgetNScheduledHoursFromSmartCard();
            isComplianceCardLoadedSecondWeek = smartCardPage.isSpecificSmartCardLoaded(cardName);
            if (isComplianceCardLoadedFirstWeek) {
                complianceShiftCountSecondWeek = smartCardPage.getComplianceShiftCountFromSmartCard(cardName);
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

            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            specificShiftAssigments.add("Lift Manager");
            specificShiftAssigments.add("Lift Maintenance");
            createSchedulePage.createScheduleForNonDGByWeekInfo(firstWeekInfo, weekDaysToClose, specificShiftAssigments);

            hoursNTMsCountSecondWeek = scheduleShiftTablePage.getTheHoursNTheCountOfTMsForEachWeekDays();
            shiftsForEachDaySecondWeek = scheduleShiftTablePage.getTheContentOfShiftsForEachWeekDay();
            budgetNScheduledHoursSecondWeek = smartCardPage.getBudgetNScheduledHoursFromSmartCard();
            isComplianceCardLoadedSecondWeek = smartCardPage.isSpecificSmartCardLoaded(cardName);
            if (isComplianceCardLoadedFirstWeek) {
                complianceShiftCountSecondWeek = smartCardPage.getComplianceShiftCountFromSmartCard(cardName);
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
    public void validateTheFilterOnSchedulePageAsInternalAdminPC (String username, String password, String browser, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("District Whistler");
            locationSelectorPage.changeLocation("Lift Ops_Parent");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();

            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isActiveWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            if (createSchedulePage.isPublishButtonLoadedOnSchedulePage()) {
               createSchedulePage.publishActiveSchedule();
            }

            // Verify the filter UI display correctly
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.verifyFilterDropdownList(true);

            // Verify the location filter has been moved to the left
            scheduleMainPage.verifyLocationFilterInLeft(true);

            // Verify performance target < 3 seconds to load
            scheduleMainPage.verifyAllChildLocationsShiftsLoadPerformance();
            String childLocation = scheduleMainPage.selectRandomChildLocationToFilter();
            scheduleMainPage.verifyChildLocationShiftsLoadPerformance(childLocation);

            // Verify shifts will display according to location filter
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            scheduleMainPage.clickOnFilterBtn();
            String childLocation1 = scheduleMainPage.selectRandomChildLocationToFilter();
            scheduleMainPage.verifyShiftsDisplayThroughLocationFilter(childLocation1);
            scheduleMainPage.clickOnFilterBtn();
            String childLocation2 = scheduleMainPage.selectRandomChildLocationToFilter();
            scheduleMainPage.verifyShiftsDisplayThroughLocationFilter(childLocation2);

            // Verify budget smart cards default view will be able to show location aggregated
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectAllChildLocationsToFilter();
            HashMap<String, String> budgetNScheduledHoursForAllLocations = smartCardPage.getBudgetNScheduledHoursFromSmartCard();
            scheduleMainPage.selectRandomChildLocationToFilter();
            HashMap<String, String> budgetNScheduledHoursForOneChild = smartCardPage.getBudgetNScheduledHoursFromSmartCard();
            if (budgetNScheduledHoursForAllLocations.equals(budgetNScheduledHoursForOneChild))
                SimpleUtils.pass("Schedule Page: The numbers in compliance smart card change according to the filter ");
            else
                SimpleUtils.fail("Schedule Page: The numbers in compliance smart card don't change according to the filter ",false);

            // Verify compliance smart cards default view will be able to show location aggregated
            scheduleMainPage.selectAllChildLocationsToFilter();
            int complianceShiftCountForAllLocations = 0;
            if (smartCardPage.verifyComplianceShiftsSmartCardShowing())
                complianceShiftCountForAllLocations = smartCardPage.getComplianceShiftCountFromSmartCard("COMPLIANCE");
            String location1 = scheduleMainPage.selectRandomChildLocationToFilter();
            int  complianceShiftCountForOneChild = 0;
            if (smartCardPage.verifyComplianceShiftsSmartCardShowing())
                complianceShiftCountForOneChild = smartCardPage.getComplianceShiftCountFromSmartCard("COMPLIANCE");
            if (complianceShiftCountForAllLocations >= complianceShiftCountForOneChild)
               SimpleUtils.pass("Schedule Page: The numbers in budget smart card change according to the filter ");
            else
               SimpleUtils.fail("Schedule Page: The numbers in budget smart card don't change according to the filter ",false);

            // Verify changes not publish smart card default view will be able to show location aggregated
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.addOpenShiftWithDefaultTime(shiftOperatePage.getRandomWorkRole(), location1);
            scheduleMainPage.saveSchedule();
            String changes1 = smartCardPage.getChangesOnActionRequired().contains(" ")? smartCardPage.getChangesOnActionRequired().split(" ")[0]: smartCardPage.getChangesOnActionRequired();
            scheduleMainPage.clickOnFilterBtn();
            String location2 = scheduleMainPage.selectRandomChildLocationToFilter();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.addOpenShiftWithDefaultTime(shiftOperatePage.getRandomWorkRole(), location2);
            scheduleMainPage.saveSchedule();
            String changes2 = smartCardPage.getChangesOnActionRequired().contains(" ")? smartCardPage.getChangesOnActionRequired().split(" ")[0]: smartCardPage.getChangesOnActionRequired();
            if (Integer.parseInt(changes2) > Integer.parseInt(changes1))
               SimpleUtils.pass("Schedule Page: The numbers in changes not publish smart card change according to the filter");
            else
               SimpleUtils.fail("Schedule Page: The numbers in changes not publish smart card don't change according to the filter",false);

            // Verify shifts, all smart cards are display according to the other filter options except locations
            scheduleMainPage.filterScheduleByJobTitle(true);
            int shiftsCount1 = scheduleShiftTablePage.getShiftsCount();
            int  complianceShiftCount1 = 0;
            if (smartCardPage.verifyComplianceShiftsSmartCardShowing())
                complianceShiftCount1 = smartCardPage.getComplianceShiftCountFromSmartCard("COMPLIANCE");
            scheduleMainPage.selectShiftTypeFilterByText("Compliance Review");
            int shiftsCount2 = scheduleShiftTablePage.getShiftsCount();
            int  complianceShiftCount2 = 0;
            if (smartCardPage.verifyComplianceShiftsSmartCardShowing())
               smartCardPage.getComplianceShiftCountFromSmartCard("COMPLIANCE");
            if (shiftsCount1 != shiftsCount2 && complianceShiftCount1 != complianceShiftCount2)
                SimpleUtils.pass("Schedule Page: The shifts and compliance smart card display according to the filter");
            else
                SimpleUtils.fail("Schedule Page: The shifts and compliance smart card don't change according to the filter",false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Validate the filter on schedule page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheFilterOnSchedulePageAsInternalAdminP2P (String username, String password, String browser, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();

            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isActiveWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            if (createSchedulePage.isPublishButtonLoadedOnSchedulePage()) {
                createSchedulePage.publishActiveSchedule();
            }

            // Verify the filter UI display correctly
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.verifyFilterDropdownList(true);

            // Verify the location filter has been moved to the left
            scheduleMainPage.verifyLocationFilterInLeft(true);

            // Verify performance target < 3 seconds to load
            scheduleMainPage.verifyAllChildLocationsShiftsLoadPerformance();
            String childLocation = scheduleMainPage.selectRandomChildLocationToFilter();
            scheduleMainPage.verifyChildLocationShiftsLoadPerformance(childLocation);

            // Verify shifts will display according to location filter
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            scheduleMainPage.clickOnFilterBtn();
            String childLocation1 = scheduleMainPage.selectRandomChildLocationToFilter();
            scheduleMainPage.verifyShiftsDisplayThroughLocationFilter(childLocation1);
            scheduleMainPage.clickOnFilterBtn();
            String childLocation2 = scheduleMainPage.selectRandomChildLocationToFilter();
            scheduleMainPage.verifyShiftsDisplayThroughLocationFilter(childLocation2);

            // Verify budget smart cards default view will be able to show location aggregated
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectAllChildLocationsToFilter();
            HashMap<String, String> budgetNScheduledHoursForAllLocations = smartCardPage.getBudgetNScheduledHoursFromSmartCard();
            scheduleMainPage.selectRandomChildLocationToFilter();
            HashMap<String, String> budgetNScheduledHoursForOneChild = smartCardPage.getBudgetNScheduledHoursFromSmartCard();
            if (budgetNScheduledHoursForAllLocations.equals(budgetNScheduledHoursForOneChild))
                SimpleUtils.pass("Schedule Page: The numbers in compliance smart card change according to the filter ");
            else
                SimpleUtils.fail("Schedule Page: The numbers in compliance smart card don't change according to the filter ",false);

            // Verify compliance smart cards default view will be able to show location aggregated
            scheduleMainPage.selectAllChildLocationsToFilter();
            int complianceShiftCountForAllLocations = 0;
            if (smartCardPage.verifyComplianceShiftsSmartCardShowing())
                complianceShiftCountForAllLocations = smartCardPage.getComplianceShiftCountFromSmartCard("COMPLIANCE");
            String location1 = scheduleMainPage.selectRandomChildLocationToFilter();
            int  complianceShiftCountForOneChild = 0;
            if (smartCardPage.verifyComplianceShiftsSmartCardShowing())
                complianceShiftCountForOneChild = smartCardPage.getComplianceShiftCountFromSmartCard("COMPLIANCE");
            if (complianceShiftCountForAllLocations >= complianceShiftCountForOneChild)
                SimpleUtils.pass("Schedule Page: The numbers in budget smart card change according to the filter ");
            else
                SimpleUtils.fail("Schedule Page: The numbers in budget smart card don't change according to the filter ",false);

            // Verify changes not publish smart card default view will be able to show location aggregated
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.addOpenShiftWithDefaultTime(shiftOperatePage.getRandomWorkRole(), location1);
            scheduleMainPage.saveSchedule();
            String changes1 = smartCardPage.getChangesOnActionRequired().contains(" ")? smartCardPage.getChangesOnActionRequired().split(" ")[0]: smartCardPage.getChangesOnActionRequired();
            scheduleMainPage.clickOnFilterBtn();
            String location2 = scheduleMainPage.selectRandomChildLocationToFilter();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.addOpenShiftWithDefaultTime(shiftOperatePage.getRandomWorkRole(), location2);
            scheduleMainPage.saveSchedule();
            String changes2 = smartCardPage.getChangesOnActionRequired().contains(" ")? smartCardPage.getChangesOnActionRequired().split(" ")[0]: smartCardPage.getChangesOnActionRequired();
            if (Integer.parseInt(changes2) > Integer.parseInt(changes1))
                SimpleUtils.pass("Schedule Page: The numbers in changes not publish smart card change according to the filter");
            else
                SimpleUtils.fail("Schedule Page: The numbers in changes not publish smart card don't change according to the filter",false);

            // Verify shifts, all smart cards are display according to the other filter options except locations
            scheduleMainPage.filterScheduleByJobTitle(true);
            int shiftsCount1 = scheduleShiftTablePage.getShiftsCount();
            int  complianceShiftCount1 = 0;
            if (smartCardPage.verifyComplianceShiftsSmartCardShowing())
                complianceShiftCount1 = smartCardPage.getComplianceShiftCountFromSmartCard("COMPLIANCE");
            scheduleMainPage.selectShiftTypeFilterByText("Compliance Review");
            int shiftsCount2 = scheduleShiftTablePage.getShiftsCount();
            int  complianceShiftCount2 = 0;
            if (smartCardPage.verifyComplianceShiftsSmartCardShowing())
                smartCardPage.getComplianceShiftCountFromSmartCard("COMPLIANCE");
            if (shiftsCount1 != shiftsCount2 && complianceShiftCount1 != complianceShiftCount2)
                SimpleUtils.pass("Schedule Page: The shifts and compliance smart card display according to the filter");
            else
                SimpleUtils.fail("Schedule Page: The shifts and compliance smart card don't change according to the filter",false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
