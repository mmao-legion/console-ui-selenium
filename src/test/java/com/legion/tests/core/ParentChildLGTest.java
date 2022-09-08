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
import com.legion.utils.Constants;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ParentChildLGTest extends TestBase {

    private static HashMap<String, String> parameterMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
    private HashMap<String, Object[][]> swapCoverCredentials = null;
    private List<String> swapCoverNames = null;
    private String workRoleName = "";
    private static String Location = "Location";
    private static String District = "District";
    private static String Region = "Region";
    private static String BusinessUnit = "Business Unit";

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
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the generation of LG schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheGenerationOfLGScheduleAsInternalAdmin(String username, String password, String browser, String location)
            throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }

            // Edit random location's operating hours before generate schedule
//            createSchedulePage.selectRandomOrSpecificLocationOnUngenerateScheduleEditOperatingHoursPage(null);
//            List<String> toCloseDays = new ArrayList<>();
//            newShiftPage.editOperatingHoursOnScheduleOldUIPage("9", "17", toCloseDays);
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

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            CinemarkMinorPage cinemarkMinorPage = pageFactory.createConsoleCinemarkMinorPage();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnGlobalLocationButton();
            controlsNewUIPage.clickOnControlsUsersAndRolesSection();
            controlsNewUIPage.selectUsersAndRolesSubTabByLabel(ScheduleTestKendraScott2.usersAndRolesSubTabs.AccessByJobTitles.getValue());

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
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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
            controlsNewUIPage.selectUsersAndRolesSubTabByLabel(ScheduleTestKendraScott2.usersAndRolesSubTabs.AccessByJobTitles.getValue());
            cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Edit.getValue());
            controlsNewUIPage.turnOnOrOffSpecificPermissionForSM(permissionSection, permission, actionOn);
            cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Save.getValue());
            loginPage.logOut();

            // Check SM cannot edit operating hours now
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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
            controlsNewUIPage.selectUsersAndRolesSubTabByLabel(ScheduleTestKendraScott2.usersAndRolesSubTabs.AccessByJobTitles.getValue());

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
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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
            controlsNewUIPage.selectUsersAndRolesSubTabByLabel(ScheduleTestKendraScott2.usersAndRolesSubTabs.AccessByJobTitles.getValue());
            cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Edit.getValue());
            controlsNewUIPage.turnOnOrOffSpecificPermissionForSM(permissionSection, permission, actionOn);
            cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Save.getValue());
            loginPage.logOut();

            // Check SM cannot edit operating hours now
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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
            createSchedulePage.clickOnSchedulePublishButton();
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

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Open");
                scheduleMainPage.saveSchedule();
            }
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            String workRole = shiftOperatePage.getRandomWorkRole();
            //Create auto open shift.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Mountain View");
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("28", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
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

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Open");
                scheduleMainPage.saveSchedule();
            }
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            String workRole = shiftOperatePage.getRandomWorkRole();
            //Create auto open shift.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Child1");
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("28", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
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

        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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
        //scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Open");
        scheduleMainPage.saveSchedule();


        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        String workRole = shiftOperatePage.getRandomWorkRole();
        //Create auto open shift.
        newShiftPage.clickOnDayViewAddNewShiftButton();
        newShiftPage.selectWorkRole(workRole);
        newShiftPage.selectChildLocInCreateShiftWindow("Child1");
        newShiftPage.moveSliderAtSomePoint("36", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
        newShiftPage.moveSliderAtSomePoint("20", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
        newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
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

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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
            //scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Open");
            scheduleMainPage.saveSchedule();


            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            String workRole = shiftOperatePage.getRandomWorkRole();
            //Create auto open shift.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Mountain View");
            newShiftPage.moveSliderAtSomePoint("36", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("20", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
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

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Open");
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Abigayle");
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectChildLocationFilterByText("Mountain View");
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Open");
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Marlon");
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            String workRole = shiftOperatePage.getRandomWorkRole();
            //Create shift and assign to TM.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Mountain View");
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("28", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.searchTeamMemberByName("Marlon");
            newShiftPage.clickOnOfferOrAssignBtn();

            //Create shift and assign to TM.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Mountain View");
            newShiftPage.moveSliderAtSomePoint("40", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.searchTeamMemberByName("Marlon");
            newShiftPage.clickOnOfferOrAssignBtn();

            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Marlon");
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            //Create auto open shift.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Carmel Club DG Oregon");
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("28", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
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
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("28", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.searchTeamMemberByName("Marlon");
            newShiftPage.clickOnOfferOrAssignBtn();

            //verify travel violation message when assign TM.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Carmel Club DG Oregon");
            newShiftPage.moveSliderAtSomePoint("44", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
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

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Open");
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Abigayle");
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectChildLocationFilterByText("Child1");
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Open");
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Abigayle");
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            String workRole = shiftOperatePage.getRandomWorkRole();
            //Create shift and assign to TM.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Child1");
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("28", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.searchTeamMemberByName("Abigayle");
            newShiftPage.clickOnOfferOrAssignBtn();

            //Create shift and assign to TM.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Child2");
            newShiftPage.moveSliderAtSomePoint("40", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.searchTeamMemberByName("Abigayle");
            newShiftPage.clickOnOfferOrAssignBtn();

            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Abigayle");
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            //Create auto open shift.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Child1");
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("28", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
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
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("28", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.searchTeamMemberByName("Abigayle");
            newShiftPage.clickOnOfferOrAssignBtn();

            //verify travel violation message when assign TM.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Child2");
            newShiftPage.moveSliderAtSomePoint("44", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
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

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("20", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.searchTeamMemberByName("Abigayle");
            newShiftPage.clickOnOfferOrAssignBtn();

            //Create shift and assign to TM.
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Child2");
            newShiftPage.moveSliderAtSomePoint("44", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
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
            newShiftPage.moveSliderAtSomePoint("44", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
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
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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
        scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(firstNameOfTM1);
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
        newShiftPage.moveSliderAtCertainPoint("9", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
        newShiftPage.moveSliderAtCertainPoint("11", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
        //newShiftPage.moveSliderAtSomePoint("32", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
        //newShiftPage.moveSliderAtSomePoint("20", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
        newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
        newShiftPage.clickOnCreateOrNextBtn();
        newShiftPage.customizeNewShiftPage();
        newShiftPage.searchTeamMemberByName(firstNameOfTM1);
        newShiftPage.clickOnOfferOrAssignBtn();

        //Create manual open shift and assign TM.
        newShiftPage.clickOnDayViewAddNewShiftButton();
        newShiftPage.selectWorkRole(workRoleOfTM1);
        newShiftPage.selectChildLocInCreateShiftWindow("Child1");
        newShiftPage.selectSpecificWorkDay(6);
        newShiftPage.moveSliderAtCertainPoint("9", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
        newShiftPage.moveSliderAtCertainPoint("11", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
        newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
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

        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());

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
        scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Unassigned");
        scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(teamMemberName);
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
        newShiftPage.moveSliderAtSomePoint("40", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
        newShiftPage.moveSliderAtSomePoint("20", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
        newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
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
        scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
        String cardName = "WANT MORE HOURS?";
        SimpleUtils.assertOnFail("Smart Card: " + cardName + " not loaded Successfully!", smartCardPage.isSpecificSmartCardLoaded(cardName), false);
        String linkName = "View Shifts";
        smartCardPage.clickLinkOnSmartCardByName(linkName);
        SimpleUtils.assertOnFail("Open shifts not loaed Successfully!", scheduleShiftTablePage.areShiftsPresent(), false);
        List<String> claimShift = new ArrayList<>(Arrays.asList("Claim Shift"));
        mySchedulePage.selectOneShiftIsClaimShift(claimShift);
        mySchedulePage.clickTheShiftRequestByName(claimShift.get(0));
        mySchedulePage.verifyClickAgreeBtnOnClaimShiftOfferWithMessage(Constants.ClaimRequestBeenSendForApprovalMessage);

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
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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
        scheduleMainPage.clickOnOpenSearchBoxButton();
        scheduleMainPage.searchShiftOnSchedulePage(teamMemberName);
        scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Unassigned");
        scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(teamMemberName);
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
        newShiftPage.moveSliderAtSomePoint("44", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
        newShiftPage.moveSliderAtSomePoint("22", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
        newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
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
        scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
        String cardName = "WANT MORE HOURS?";
        SimpleUtils.assertOnFail("Smart Card: " + cardName + " not loaded Successfully!", smartCardPage.isSpecificSmartCardLoaded(cardName), false);
        String linkName = "View Shifts";
        smartCardPage.clickLinkOnSmartCardByName(linkName);
        SimpleUtils.assertOnFail("Open shifts not loaed Successfully!", scheduleShiftTablePage.areShiftsPresent(), false);
        List<String> claimShift = new ArrayList<>(Arrays.asList("Claim Shift"));
        mySchedulePage.selectOneShiftIsClaimShift(claimShift);
        mySchedulePage.clickTheShiftRequestByName(claimShift.get(0));
        mySchedulePage.verifyClickAgreeBtnOnClaimShiftOfferWithMessage(Constants.ClaimRequestBeenSendForApprovalMessage);

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
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
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
            scheduleMainPage.selectGroupByFilter("Group by All");
            scheduleMainPage.clickOnOpenSearchBoxButton();
            scheduleMainPage.searchShiftOnSchedulePage(swapCoverNames.get(0));
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(swapCoverNames.get(0));
            scheduleMainPage.searchShiftOnSchedulePage(swapCoverNames.get(1));
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(swapCoverNames.get(1));
            scheduleMainPage.clickOnCloseSearchBoxButton();
            if(smartCardPage.isRequiredActionSmartCardLoaded()){
                smartCardPage.clickOnViewShiftsBtnOnRequiredActionSmartCard();
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Unassigned");
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

        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
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
            List<String> weekDaysToClose = new ArrayList<>();
            weekDaysToClose.add("Sunday");
            createSchedulePage.createScheduleForNonDGByWeekInfo("SUGGESTED", weekDaysToClose, null);
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Unassigned");
            scheduleMainPage.saveSchedule();
            //Create auto open shift.

            //Create auto open shift.
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            String workRole = shiftOperatePage.getRandomWorkRole();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow("Child1");
            newShiftPage.moveSliderAtSomePoint("40", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtSomePoint("22", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
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
            scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
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
    @Owner(owner = "Haya")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "Verify automatically expand when clicking group by on P2P LG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAutomaticallyExpandWhenGroupByInP2PLGAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
        NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

        scheduleCommonPage.navigateToNextWeek();
        boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (!isWeekGenerated) {
            createSchedulePage.createScheduleForNonDGFlowNewUI();
        }
        //Group by work role and check the group.
        scheduleMainPage.selectGroupByFilter(ScheduleTestKendraScott2.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
        scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();
        //Group by job title and check the group.
        scheduleMainPage.selectGroupByFilter(ScheduleTestKendraScott2.scheduleGroupByFilterOptions.groupbyJobTitle.getValue());
        scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();
        //Group by day parts and check the group.
        scheduleMainPage.selectGroupByFilter(ScheduleTestKendraScott2.scheduleGroupByFilterOptions.groupbyDayParts.getValue());
        scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();
        //Group by locations and check the group.
        scheduleMainPage.selectGroupByFilter(ScheduleTestKendraScott2.scheduleGroupByFilterOptions.groupbyLocation.getValue());
        scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();

        //Edit-mode
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        //Group by work role and check the group.
        scheduleMainPage.selectGroupByFilter(ScheduleTestKendraScott2.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
        scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();
        //Group by job title and check the group.
        scheduleMainPage.selectGroupByFilter(ScheduleTestKendraScott2.scheduleGroupByFilterOptions.groupbyJobTitle.getValue());
        scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();
        //Group by day parts and check the group.
        scheduleMainPage.selectGroupByFilter(ScheduleTestKendraScott2.scheduleGroupByFilterOptions.groupbyDayParts.getValue());
        scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();
        //Group by locations and check the group.
        scheduleMainPage.selectGroupByFilter(ScheduleTestKendraScott2.scheduleGroupByFilterOptions.groupbyLocation.getValue());
        scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Vailqacn_Enterprise")
    @TestName(description = "Verify automatically expand when clicking group by on Parent/Child LG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAutomaticallyExpandWhenGroupByInPCLGAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        String PCLocationGroup = getCrendentialInfo("PCLGInfo");
        locationSelectorPage.changeLocation(PCLocationGroup);
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
        ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
        ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
        scheduleCommonPage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
        scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

        scheduleCommonPage.navigateToNextWeek();
        boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
        if (!isWeekGenerated) {
            createSchedulePage.createScheduleForNonDGFlowNewUI();
        }
        //Group by work role and check the group.
        scheduleMainPage.selectGroupByFilter(ScheduleTestKendraScott2.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
        scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();
        //Group by job title and check the group.
        scheduleMainPage.selectGroupByFilter(ScheduleTestKendraScott2.scheduleGroupByFilterOptions.groupbyJobTitle.getValue());
        scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();
        //Group by day parts and check the group.
        scheduleMainPage.selectGroupByFilter(ScheduleTestKendraScott2.scheduleGroupByFilterOptions.groupbyDayParts.getValue());
        scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();
        //Group by locations and check the group.
        scheduleMainPage.selectGroupByFilter(ScheduleTestKendraScott2.scheduleGroupByFilterOptions.groupbyLocation.getValue());
        scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();

        //Edit-mode
        scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        //Group by work role and check the group.
        scheduleMainPage.selectGroupByFilter(ScheduleTestKendraScott2.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
        scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();
        //Group by job title and check the group.
        scheduleMainPage.selectGroupByFilter(ScheduleTestKendraScott2.scheduleGroupByFilterOptions.groupbyJobTitle.getValue());
        scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();
        //Group by day parts and check the group.
        scheduleMainPage.selectGroupByFilter(ScheduleTestKendraScott2.scheduleGroupByFilterOptions.groupbyDayParts.getValue());
        scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();
        //Group by locations and check the group.
        scheduleMainPage.selectGroupByFilter(ScheduleTestKendraScott2.scheduleGroupByFilterOptions.groupbyLocation.getValue());
        scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the location filter on Demand tab")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheLocationFilterOnDemandTabAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(getCrendentialInfo("LGInfo"));
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            Thread.sleep(3000);
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Forecast.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Forecast' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Forecast.getValue()), false);
            ForecastPage forecastPage = pageFactory.createForecastPage();

            //Location filter dropdown will display on the Demand tab in forecast page
            SimpleUtils.assertOnFail("Location filter dropdown fail to load on the Demand tab in forecast page! ",
                    forecastPage.checkIsLocationFilterLoaded(), false);

            //All locations been selected in locations filter and get all info in smart card
            SimpleUtils.assertOnFail("All locations should be selected in filter dropdown by default! ",
                    forecastPage.checkIfAllLocationBeenSelected(), false);
            HashMap<String, Float> forecastDataForAllLocations= forecastPage.getInsightDataInShopperWeekView();
            float peakDemand = forecastDataForAllLocations.get("peakDemand");
            float totalDemand = forecastDataForAllLocations.get("totalDemand");
            //The relevant info will display when user filters the location
            List<String> locations = forecastPage.getAllLocationsFromFilter();
            int index = (new Random()).nextInt(locations.size());
            forecastPage.checkOrUncheckLocationInFilter(false, locations.get(index));
            HashMap<String, Float> forecastDataForPartOfLocations= forecastPage.getInsightDataInShopperWeekView();
            SimpleUtils.assertOnFail("The peak demand for all locations should more than or equal to the value for part of locations! ",
                    peakDemand >=forecastDataForPartOfLocations.get("peakDemand"), false);
            SimpleUtils.assertOnFail("The total demand for all locations should more than or equal to the value for part of locations! ",
                    totalDemand >=forecastDataForPartOfLocations.get("totalDemand"), false);

            forecastPage.checkOrUncheckLocationInFilter(true, locations.get(index));
            forecastDataForPartOfLocations= forecastPage.getInsightDataInShopperWeekView();
            SimpleUtils.assertOnFail("The peak demand for all locations should equal with before! ",
                    peakDemand == forecastDataForPartOfLocations.get("peakDemand"), false);
            SimpleUtils.assertOnFail("The total demand for all locations should equal with before! ",
                    totalDemand == forecastDataForPartOfLocations.get("totalDemand"), false);

            //Verify locations filter in day view
            scheduleCommonPage.clickOnDayView();

            //Location filter dropdown will display on the Demand tab in forecast page
            SimpleUtils.assertOnFail("Location filter dropdown fail to load on the Demand tab in forecast page! ",
                    forecastPage.checkIsLocationFilterLoaded(), false);

            //All locations been selected in locations filter and get all info in smart card
            SimpleUtils.assertOnFail("All locations should be selected in filter dropdown by default! ",
                    forecastPage.checkIfAllLocationBeenSelected(), false);
            forecastDataForAllLocations= forecastPage.getInsightDataInShopperWeekView();
            peakDemand = forecastDataForAllLocations.get("peakDemand");
            totalDemand = forecastDataForAllLocations.get("totalDemand");
            //The relevant info will display when user filters the location
            locations = forecastPage.getAllLocationsFromFilter();
            index = (new Random()).nextInt(locations.size());
            forecastPage.checkOrUncheckLocationInFilter(false, locations.get(index));
            forecastDataForPartOfLocations= forecastPage.getInsightDataInShopperWeekView();
            SimpleUtils.assertOnFail("The peak demand for all locations should more than or equal to the value for part of locations! ",
                    peakDemand >=forecastDataForPartOfLocations.get("peakDemand"), false);
            SimpleUtils.assertOnFail("The total demand for all locations should more than or equal to the value for part of locations! ",
                    totalDemand >=forecastDataForPartOfLocations.get("totalDemand"), false);

            forecastPage.checkOrUncheckLocationInFilter(true, locations.get(index));
            forecastDataForPartOfLocations= forecastPage.getInsightDataInShopperWeekView();
            SimpleUtils.assertOnFail("The peak demand for all locations should equal with before! ",
                    peakDemand == forecastDataForPartOfLocations.get("peakDemand"), false);
            SimpleUtils.assertOnFail("The total demand for all locations should equal with before! ",
                    totalDemand == forecastDataForPartOfLocations.get("totalDemand"), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }


    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the location filter on Labor tab")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheLocationFilterOnLabelTabAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(getCrendentialInfo("LGInfo"));
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Forecast.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Forecast' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Forecast.getValue()), false);
            ForecastPage forecastPage = pageFactory.createForecastPage();
            Thread.sleep(10000);
            forecastPage.clickOnLabor();
            //Location filter dropdown will display on the Labor tab in forecast page
            SimpleUtils.assertOnFail("Location filter dropdown fail to load on the Labor tab in forecast page! ",
                    forecastPage.checkIsLocationFilterLoaded(), false);

            //All locations been selected in locations filter and get all info in smart card
            SimpleUtils.assertOnFail("All locations should be selected in filter dropdown by default! ",
                    forecastPage.checkIfAllLocationBeenSelected(), false);
            HashMap<String, Float> forecastDataForAllLocations= forecastPage.getInsightDataInShopperWeekView();
            float forecastHrs = forecastDataForAllLocations.get("ForecastHrs");
            //The relevant info will display when user filters the location
            List<String> locations = forecastPage.getAllLocationsFromFilter();
            int index = (new Random()).nextInt(locations.size());
            forecastPage.checkOrUncheckLocationInFilter(false, locations.get(index));
            HashMap<String, Float> forecastDataForPartOfLocations= forecastPage.getInsightDataInShopperWeekView();
            SimpleUtils.assertOnFail("The Forecast Hrs for all locations should more than or equal to the value for part of locations! ",
                    forecastHrs >=forecastDataForPartOfLocations.get("ForecastHrs"), false);

            forecastPage.checkOrUncheckLocationInFilter(true, locations.get(index));
            forecastDataForPartOfLocations= forecastPage.getInsightDataInShopperWeekView();
            SimpleUtils.assertOnFail("The Forecast Hrs for all locations should more than or equal to the value for part of locations! ",
                    forecastHrs == forecastDataForPartOfLocations.get("ForecastHrs"), false);

            //Verify locations filter in day view
            scheduleCommonPage.clickOnDayView();

            //Location filter dropdown will display on the Labor tab in forecast page
            SimpleUtils.assertOnFail("Location filter dropdown fail to load on the Labor tab in forecast page! ",
                    forecastPage.checkIsLocationFilterLoaded(), false);

            //All locations been selected in locations filter and get all info in smart card
            SimpleUtils.assertOnFail("All locations should be selected in filter dropdown by default! ",
                    forecastPage.checkIfAllLocationBeenSelected(), false);
            forecastDataForAllLocations= forecastPage.getInsightDataInShopperWeekView();
            forecastHrs = forecastDataForAllLocations.get("ForecastHrs");
            //The relevant info will display when user filters the location
            locations = forecastPage.getAllLocationsFromFilter();
            index = (new Random()).nextInt(locations.size());
            forecastPage.checkOrUncheckLocationInFilter(false, locations.get(index));
            forecastDataForPartOfLocations= forecastPage.getInsightDataInShopperWeekView();
            SimpleUtils.assertOnFail("The Forecast Hrs for all locations should more than or equal to the value for part of locations! ",
                    forecastHrs >=forecastDataForPartOfLocations.get("ForecastHrs"), false);

            forecastPage.checkOrUncheckLocationInFilter(true, locations.get(index));
            forecastDataForPartOfLocations= forecastPage.getInsightDataInShopperWeekView();
            SimpleUtils.assertOnFail("The Forecast Hrs for all locations should more than or equal to the value for part of locations! ",
                    forecastHrs ==forecastDataForPartOfLocations.get("ForecastHrs"), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the assign TMs workflow for new create shift UI on Parent Child LG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheAssignTMsWorkFlowForNewCreateShiftUIOnParentChildLGAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            scheduleMainPage.clickOnFilterBtn();
            String locationName = scheduleMainPage.selectRandomChildLocationToFilter();
            int shiftCount = scheduleShiftTablePage.getShiftsCount();
            ArrayList<HashMap<String,String>> locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);
            String workRole = shiftOperatePage.getRandomWorkRole();
            //Verify the assign workflow with one shift for one days
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            SimpleUtils.assertOnFail("New create shift page is not display! ",
                    newShiftPage.checkIfNewCreateShiftPageDisplay(), false);
            //Fill the required option
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow(locationName);
            String shiftStartTime = "8:00am";
            String shiftEndTime = "11:00am";
            String totalHrs = "3 Hrs";
            newShiftPage.moveSliderAtCertainPoint(shiftEndTime, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint(shiftStartTime, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            int count = 1;
            newShiftPage.setShiftPerDayOnNewCreateShiftPage(count);
            newShiftPage.clearAllSelectedDays();
            int dayCount = 1;
            newShiftPage.selectSpecificWorkDay(dayCount);
            String shiftName = "ShiftNameForBulkCreateShiftUIAuto";
            String shiftNotes = "Shift Notes For Bulk Create Shift UI Auto";
            newShiftPage.setShiftNameOnNewCreateShiftPage(shiftName);
            newShiftPage.setShiftNotesOnNewCreateShiftPage(shiftNotes);
            newShiftPage.clickOnCreateOrNextBtn();
            //Select 3 TMs to assign and click Create button
            shiftOperatePage.switchSearchTMAndRecommendedTMsTab();
            MyThreadLocal.setAssignTMStatus(true);
            String selectedTM1 = newShiftPage.selectTeamMembers();
            newShiftPage.clickOnCreateOrNextBtn();

            locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);
            SimpleUtils.assertOnFail("It should has "+count+1+" shifts display, but actual is has :"+scheduleShiftTablePage.getShiftsCount(),
                    shiftCount == scheduleShiftTablePage.getShiftsCount() -1, false);
            List<WebElement> shiftsOfOneDay = scheduleShiftTablePage.getOneDayShiftByName(0, selectedTM1.split(" ")[0]);
            SimpleUtils.assertOnFail("The "+selectedTM1+ "shift is not exist on the first day! ",
                    shiftsOfOneDay.size()>=1, false);
            scheduleMainPage.saveSchedule();
            Thread.sleep(5000);
            locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);
            SimpleUtils.assertOnFail("It should has "+count+1+" shifts display, but actual is has :"+scheduleShiftTablePage.getShiftsCount(),
                    shiftCount == scheduleShiftTablePage.getShiftsCount() -1, false);
            shiftsOfOneDay = scheduleShiftTablePage.getOneDayShiftByName(0, selectedTM1.split(" ")[0]);
            SimpleUtils.assertOnFail("The open shift is not exist on the first day! ",
                    shiftsOfOneDay.size()>=1, false);
            createSchedulePage.publishActiveSchedule();
            locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);
            SimpleUtils.assertOnFail("It should has "+count+1+" shifts display, but actual is has :"+scheduleShiftTablePage.getShiftsCount(),
                    shiftCount == scheduleShiftTablePage.getShiftsCount() -1, false);
            shiftsOfOneDay = scheduleShiftTablePage.getOneDayShiftByName(0, selectedTM1.split(" ")[0]);
            SimpleUtils.assertOnFail("The open shift is not exist on the first day! ",
                    shiftsOfOneDay.size()>=1, false);

            String shiftId = shiftsOfOneDay.get(0).getAttribute("id").toString();
            int index = scheduleShiftTablePage.getShiftIndexById(shiftId);
            List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            String shiftTime = shiftInfo.get(2);
            String workRoleOfNewShift = shiftInfo.get(4);
            String shiftHrs = shiftInfo.get(8);
            String shiftNameOfNewShift = shiftInfo.get(9);
            String shiftNotesOfNewShift = shiftInfo.get(10);
            SimpleUtils.assertOnFail("The new shift's shift time display incorrectly, the expected is:"+shiftStartTime+"-"+shiftEndTime
                            + " the actual is: "+ shiftTime,
                    shiftTime.equalsIgnoreCase(shiftStartTime+"-"+shiftEndTime), false);
            SimpleUtils.assertOnFail("The new shift's work role display incorrectly, the expected is:"+ workRole
                            + " the actual is: "+ workRoleOfNewShift,
                    workRoleOfNewShift.equalsIgnoreCase(workRole), false);
            SimpleUtils.assertOnFail("The new shift's hrs display incorrectly, the expected is:"+ totalHrs
                            + " the actual is: "+ shiftHrs,
                    totalHrs.equalsIgnoreCase(shiftHrs), false);
            SimpleUtils.assertOnFail("The new shift's name display incorrectly, the expected is:"+ shiftName
                            + " the actual is: "+ shiftNameOfNewShift,
                    shiftName.equals(shiftNameOfNewShift), false);
            SimpleUtils.assertOnFail("The new shift's notes display incorrectly, the expected is:"+ shiftNotes
                            + " the actual is: "+ shiftNotesOfNewShift,
                    shiftNotes.equalsIgnoreCase(shiftNotesOfNewShift), false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the manuel offer TMs workflow for new create shift UI on Parent Child LG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheManuelOfferTMsWorkFlowForNewCreateShiftUIOnParentChildLGAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();

            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
            scheduleCommonPage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("open");
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("unassigned");
            scheduleMainPage.saveSchedule();
            scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            scheduleMainPage.clickOnFilterBtn();
            String locationName = scheduleMainPage.selectRandomChildLocationToFilter();
            ArrayList<HashMap<String,String>> locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            int shiftCount = scheduleShiftTablePage.getShiftsCount();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);
            String workRole = shiftOperatePage.getRandomWorkRole();

            //Verify the manual offer workflow with one shift for one days
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            SimpleUtils.assertOnFail("New create shift page is not display! ",
                    newShiftPage.checkIfNewCreateShiftPageDisplay(), false);
            //Fill the required option
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow(locationName);
            String shiftStartTime = "8:00am";
            String shiftEndTime = "11:00am";
            String totalHrs = "3 Hrs";
            newShiftPage.moveSliderAtCertainPoint(shiftEndTime, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint(shiftStartTime, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
            int count = 1;
            newShiftPage.setShiftPerDayOnNewCreateShiftPage(count);
            newShiftPage.clearAllSelectedDays();
            int dayCount = 1;
            newShiftPage.selectSpecificWorkDay(dayCount);
            String shiftName = "ShiftNameForBulkCreateShiftUIAuto";
            String shiftNotes = "Shift Notes For Bulk Create Shift UI Auto";
            newShiftPage.setShiftNameOnNewCreateShiftPage(shiftName);
            newShiftPage.setShiftNotesOnNewCreateShiftPage(shiftNotes);
            newShiftPage.clickOnCreateOrNextBtn();
            //Select 3 TMs to offer and click Create button
            shiftOperatePage.switchSearchTMAndRecommendedTMsTab();
            MyThreadLocal.setAssignTMStatus(false);
            String selectedTM1 = newShiftPage.selectTeamMembers();
            String selectedTM2 = newShiftPage.selectTeamMembers();
            String selectedTM3 = newShiftPage.selectTeamMembers();
            newShiftPage.clickOnCreateOrNextBtn();
            locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);
            SimpleUtils.assertOnFail("It should has "+count+1+" shifts display, but actual is has :"+scheduleShiftTablePage.getShiftsCount(),
                    shiftCount == scheduleShiftTablePage.getShiftsCount() -1, false);
            scheduleMainPage.saveSchedule();
            Thread.sleep(5000);
            locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);
            SimpleUtils.assertOnFail("It should has "+count+1+" shifts display, but actual is has :"+scheduleShiftTablePage.getShiftsCount(),
                    shiftCount == scheduleShiftTablePage.getShiftsCount() -1, false);
            //There are 1 open shift been created and shift offer will been sent to the multiple TMs
            SimpleUtils.assertOnFail("The open shift is not exist on the first day! ",
                    scheduleShiftTablePage.getOneDayShiftByName(0, "Open").size()==1, false);
            int index = scheduleShiftTablePage.getAddedShiftIndexes("Open").get(0);
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            scheduleShiftTablePage.clickViewStatusBtn();

            SimpleUtils.assertOnFail("The "+selectedTM1+" should display on the offer list! ",
                    !shiftOperatePage.getOfferStatusFromOpenShiftStatusList(selectedTM1).equals(""), false);
            SimpleUtils.assertOnFail("The "+selectedTM2+" should display on the offer list! ",
                    !shiftOperatePage.getOfferStatusFromOpenShiftStatusList(selectedTM2).equals(""), false);
            SimpleUtils.assertOnFail("The "+selectedTM3+" should display on the offer list! ",
                    !shiftOperatePage.getOfferStatusFromOpenShiftStatusList(selectedTM3).equals(""), false);
            shiftOperatePage.closeViewStatusContainer();

            //The work role, shifts time, daily hrs and weekly hrs are display correctly
            List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            String shiftTime = shiftInfo.get(2);
            String workRoleOfNewShift = shiftInfo.get(4);
            String shiftHrs = shiftInfo.get(6);
            String shiftNameOfNewShift = shiftInfo.get(7);
            String shiftNotesOfNewShift = shiftInfo.get(8);
            SimpleUtils.assertOnFail("The new shift's shift time display incorrectly, the expected is:"+shiftStartTime+"-"+shiftEndTime
                            + " the actual is: "+ shiftTime,
                    shiftTime.equalsIgnoreCase(shiftStartTime+"-"+shiftEndTime), false);
            SimpleUtils.assertOnFail("The new shift's work role display incorrectly, the expected is:"+ workRole
                            + " the actual is: "+ workRoleOfNewShift,
                    workRoleOfNewShift.equalsIgnoreCase(workRole), false);
            SimpleUtils.assertOnFail("The new shift's hrs display incorrectly, the expected is:"+ totalHrs
                            + " the actual is: "+ shiftHrs,
                    totalHrs.equalsIgnoreCase(shiftHrs), false);
            SimpleUtils.assertOnFail("The new shift's name display incorrectly, the expected is:"+ shiftName
                            + " the actual is: "+ shiftNameOfNewShift,
                    shiftName.equals(shiftNameOfNewShift), false);
            SimpleUtils.assertOnFail("The new shift's notes display incorrectly, the expected is:"+ shiftNotes
                            + " the actual is: "+ shiftNotesOfNewShift,
                    shiftNotes.equalsIgnoreCase(shiftNotesOfNewShift), false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
//    @Enterprise(name = "Vailqacn_Enterprise")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the auto offer TMs workflow for new create shift UI on Parent Child LG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheAutoOfferTMsWorkFlowForNewCreateShiftUIOnParentChildLGAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();

            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
            scheduleCommonPage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("open");
            scheduleMainPage.saveSchedule();
            String workRole = shiftOperatePage.getRandomWorkRole();
            scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            scheduleMainPage.clickOnFilterBtn();
            String locationName = scheduleMainPage.selectRandomChildLocationToFilter();
            ArrayList<HashMap<String,String>> locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            int shiftCount = scheduleShiftTablePage.getShiftsCount();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);

            //Verify the auto offer workflow with one shift for one days
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            SimpleUtils.assertOnFail("New create shift page is not display! ",
                    newShiftPage.checkIfNewCreateShiftPageDisplay(), false);
            //Fill the required option
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow(locationName);
            String shiftStartTime = "11:00am";
            String shiftEndTime = "2:00pm";
            String totalHrs = "3 Hrs";
            newShiftPage.moveSliderAtCertainPoint(shiftEndTime, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint(shiftStartTime, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
            int count = 1;
            newShiftPage.setShiftPerDayOnNewCreateShiftPage(count);
            newShiftPage.clearAllSelectedDays();
            int dayCount = 1;
            newShiftPage.selectSpecificWorkDay(dayCount);
            String shiftName = "ShiftNameForBulkCreateShiftUIAuto";
            String shiftNotes = "Shift Notes For Bulk Create Shift UI Auto";
            newShiftPage.setShiftNameOnNewCreateShiftPage(shiftName);
            newShiftPage.setShiftNotesOnNewCreateShiftPage(shiftNotes);
            newShiftPage.clickOnCreateOrNextBtn();
            locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);
            SimpleUtils.assertOnFail("It should has "+count+1+" shifts display, but actual is has :"+scheduleShiftTablePage.getShiftsCount(),
                    shiftCount == scheduleShiftTablePage.getShiftsCount() -1, false);
            scheduleMainPage.saveSchedule();
            locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);
            SimpleUtils.assertOnFail("It should has "+count+1+" shifts display, but actual is has :"+scheduleShiftTablePage.getShiftsCount(),
                    shiftCount == scheduleShiftTablePage.getShiftsCount() -1, false);
            SimpleUtils.assertOnFail("The open shift is not exist on the first day! ",
                    scheduleShiftTablePage.getOneDayShiftByName(0, "Open").size()==1, false);
            List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getAddedShiftIndexes("Open").get(0));
            String shiftTime = shiftInfo.get(2);
            String workRoleOfNewShift = shiftInfo.get(4);
            String shiftHrs = shiftInfo.get(6);
            String shiftNameOfNewShift = shiftInfo.get(7);
            String shiftNotesOfNewShift = shiftInfo.get(8);
            SimpleUtils.assertOnFail("The new shift's shift time display incorrectly, the expected is:"+shiftStartTime+"-"+shiftEndTime
                            + " the actual is: "+ shiftTime,
                    shiftTime.equalsIgnoreCase(shiftStartTime+"-"+shiftEndTime), false);
            SimpleUtils.assertOnFail("The new shift's work role display incorrectly, the expected is:"+ workRole
                            + " the actual is: "+ workRoleOfNewShift,
                    workRoleOfNewShift.equalsIgnoreCase(workRole), false);
            SimpleUtils.assertOnFail("The new shift's hrs display incorrectly, the expected is:"+ totalHrs
                            + " the actual is: "+ shiftHrs,
                    totalHrs.equalsIgnoreCase(shiftHrs), false);
            SimpleUtils.assertOnFail("The new shift's name display incorrectly, the expected is:"+ shiftName
                            + " the actual is: "+ shiftNameOfNewShift,
                    shiftName.equals(shiftNameOfNewShift), false);
            SimpleUtils.assertOnFail("The new shift's notes display incorrectly, the expected is:"+ shiftNotes
                            + " the actual is: "+ shiftNotesOfNewShift,
                    shiftNotes.equalsIgnoreCase(shiftNotesOfNewShift), false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate open shifts will been created when drag and drop shifts to same day and same location on parent child LG schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyBulkDragAndDropShiftsToSameDayAndSameLocationOnParentChildLGScheduleAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

            // Navigate to next week
            scheduleCommonPage.navigateToNextWeek();
            // create the schedule.
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            if (scheduleShiftTablePage.getAllShiftsOfOneTM("open").size()>0) {
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("open");
                scheduleMainPage.saveSchedule();
            }
            scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectRandomChildLocationToFilter();
            ArrayList<HashMap<String,String>> locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            // Verify can select multiple shifts by pressing Ctrl/Cmd(Mac)
            int selectedShiftCount = 2;
            List<WebElement> selectedShifts = scheduleShiftTablePage.
                    selectMultipleDifferentAssignmentShiftsOnOneDay(selectedShiftCount, 1);
            //Drag the selected shifts to same day
            scheduleShiftTablePage.dragBulkShiftToAnotherDay(selectedShifts, 1, false);

            //Two open shifts been created
            List<WebElement> newAddedShifts = scheduleShiftTablePage.getOneDayShiftByName(1, "open");
            SimpleUtils.assertOnFail("The expected new added shifts count is "+selectedShiftCount
                            + " The actual new added shift count is:"+newAddedShifts.size(),
                    newAddedShifts.size()==0, false);

            scheduleMainPage.saveSchedule();
            newAddedShifts = scheduleShiftTablePage.getOneDayShiftByName(1, "open");
            SimpleUtils.assertOnFail("The expected new added shifts count is "+selectedShiftCount
                            + " The actual new added shift count is:"+newAddedShifts.size(),
                    newAddedShifts.size()==2, false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify shifts can be moved to same day and another location on parent child LG schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyMoveShiftsToSameDayAndAnotherLocationOnParentChildLGScheduleAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

            // Navigate to next week
            scheduleCommonPage.navigateToNextWeek();
            // create the schedule.
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            if (scheduleShiftTablePage.getAllShiftsOfOneTM("open").size()>0) {
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("open");
                scheduleMainPage.saveSchedule();
            }
            scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            scheduleShiftTablePage.expandSpecificCountGroup(2);
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //Get shift count before drag and drop
            int allShiftsCountBefore = scheduleShiftTablePage.getShiftsCount();
            int oneDayShiftsCountBefore = scheduleShiftTablePage.getOneDayShiftCountByIndex(1);
            // Verify can select multiple shifts by pressing Ctrl/Cmd(Mac)
            int selectedShiftCount = 2;
            List<WebElement> selectedShifts = scheduleShiftTablePage.
                    selectMultipleDifferentAssignmentShiftsOnOneDay(selectedShiftCount, 1);
            List<String> shiftNames = new ArrayList<>();
            for (int i=0; i< selectedShiftCount;i++) {
                int index = scheduleShiftTablePage.getTheIndexOfShift(selectedShifts.get(i));
                shiftNames.add(scheduleShiftTablePage.getTheShiftInfoByIndex(index).get(0));
            }

            //Drag the selected shifts to same day
            scheduleShiftTablePage.dragBulkShiftToAnotherDay(selectedShifts, 1, true);

            //Select move option
            scheduleShiftTablePage.selectMoveOrCopyBulkShifts("move");
            scheduleShiftTablePage.enableOrDisableAllowComplianceErrorSwitch(true);
            scheduleShiftTablePage.enableOrDisableAllowConvertToOpenSwitch(true);
            scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();

            //Check the shift count after drag and drop
            int allShiftsCountAfter = scheduleShiftTablePage.getShiftsCount();
            int oneDayShiftsCountAfter = scheduleShiftTablePage.getOneDayShiftCountByIndex(1);
            SimpleUtils.assertOnFail("The expected count are: "+allShiftsCountBefore + " and "+ oneDayShiftsCountBefore
                            + ", but the actual are: "+allShiftsCountAfter + " and "+ oneDayShiftsCountAfter,
                    allShiftsCountAfter == allShiftsCountBefore && oneDayShiftsCountAfter == oneDayShiftsCountBefore, false);
            for (int i=0; i< selectedShiftCount;i++) {
                SimpleUtils.assertOnFail("Bulk Drag and drop: the shifts fail to be moved! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size()>0, false);
            }
            //Verify changes can be saved
            scheduleMainPage.saveSchedule();
            allShiftsCountAfter = scheduleShiftTablePage.getShiftsCount();
            oneDayShiftsCountAfter = scheduleShiftTablePage.getOneDayShiftCountByIndex(1);
            SimpleUtils.assertOnFail("The expected count are: "+allShiftsCountBefore + " and "+ oneDayShiftsCountBefore
                            + ", but the actual are: "+allShiftsCountAfter + " and "+ oneDayShiftsCountAfter,
                    allShiftsCountAfter == allShiftsCountBefore && oneDayShiftsCountAfter == oneDayShiftsCountBefore, false);
            for (int i=0; i< selectedShiftCount;i++) {
                SimpleUtils.assertOnFail("Bulk Drag and drop: the shifts fail to be moved! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size()>0, false);
            }
            //Verify changes can be published
            createSchedulePage.publishActiveSchedule();
            allShiftsCountAfter = scheduleShiftTablePage.getShiftsCount();
            oneDayShiftsCountAfter = scheduleShiftTablePage.getOneDayShiftCountByIndex(1);
            SimpleUtils.assertOnFail("The expected count are: "+allShiftsCountBefore + " and "+ oneDayShiftsCountBefore
                            + ", but the actual are: "+allShiftsCountAfter + " and "+ oneDayShiftsCountAfter,
                    allShiftsCountAfter == allShiftsCountBefore && oneDayShiftsCountAfter == oneDayShiftsCountBefore, false);
            for (int i=0; i< selectedShiftCount;i++) {
                SimpleUtils.assertOnFail("Bulk Drag and drop: the shifts fail to be moved! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size()>0, false);
            }
            //Check the shifts are not display on first location
            scheduleShiftTablePage.expandSpecificCountGroup(1);
            for (int i=0; i< selectedShiftCount;i++) {
                SimpleUtils.assertOnFail("Bulk Drag and drop: the shifts fail to be moved! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size()==0, false);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the content on Multiple Edit Shifts window for Parent/Child location group")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOnMultipleEditShiftsWindowForPCAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            EditShiftPage editShiftPage = pageFactory.createEditShiftPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();

            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            goToSchedulePageScheduleTab();

            // Create schedule if it is not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            BasePage basePage = new BasePage();
            String activeWeek = basePage.getActiveWeekText();
            String startOfWeek = activeWeek.split(" ")[3] + " " + activeWeek.split(" ")[4];
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            // Verify can select multiple shifts by pressing Ctrl/Cmd(Mac)
            int selectedShiftCount = 2;
            HashSet<Integer> set = scheduleShiftTablePage.verifyCanSelectMultipleShifts(selectedShiftCount);
            List<String> selectedDays = scheduleShiftTablePage.getSelectedWorkDays(set);
            // Verify action menu will pop up when right clicking on anywhere of the selected shifts
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            scheduleShiftTablePage.verifyTheContentOnBulkActionMenu(selectedShiftCount);
            // Verify Edit action is visible when right clicking the selected shifts in week view
            // Verify the functionality of Edit button in week view
            String action = "Edit";
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            SimpleUtils.assertOnFail("Edit Shifts window failed to load!", editShiftPage.isEditShiftWindowLoaded(), false);
            // Verify the title of Edit Shifts window
            editShiftPage.verifyTheTitleOfEditShiftsWindow(selectedShiftCount, startOfWeek);
            // Verify the selected days show correctly
            editShiftPage.verifySelectedWorkDays(selectedShiftCount, selectedDays);
            // Verify the Location Name shows correctly
            editShiftPage.verifyLocationNameShowsCorrectly(location);
            // Verify the visibility of buttons
            editShiftPage.verifyTheVisibilityOfButtons();
            // Verify the content of options section
            editShiftPage.verifyTheContentOfOptionsSection();
            // Verify the visibility of Clear Edited Fields button
            SimpleUtils.assertOnFail("Clear Edited Fields button failed to load!", editShiftPage.isClearEditedFieldsBtnLoaded(), false);
            // Verify the three columns show on Shift Details section
            editShiftPage.verifyThreeColumns();
            // Verify the editable types show on Shift Detail section
            editShiftPage.verifyEditableTypesShowOnShiftDetail();
            // Verify the functionality of x button
            editShiftPage.clickOnXButton();
            SimpleUtils.assertOnFail("Click on X button failed!", !editShiftPage.isEditShiftWindowLoaded(), false);
            // Verify the functionality of Cancel button
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            SimpleUtils.assertOnFail("Edit Shifts window failed to load!", editShiftPage.isEditShiftWindowLoaded(), false);
            editShiftPage.clickOnCancelButton();

            scheduleMainPage.clickOnCancelButtonOnEditMode();
            scheduleCommonPage.clickOnDayView();
            String weekDay = basePage.getActiveWeekText();
            String fullWeekDay = SimpleUtils.getFullWeekDayName(weekDay.split(" ")[0].trim());
            selectedDays = new ArrayList<>();
            selectedDays.add(fullWeekDay);
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            set = scheduleShiftTablePage.verifyCanSelectMultipleShifts(selectedShiftCount);
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            SimpleUtils.assertOnFail("Edit Shifts window failed to load!", editShiftPage.isEditShiftWindowLoaded(), false);
            // Verify the title of Edit Shifts window in day view
            editShiftPage.verifyTheTitleOfEditShiftsWindow(selectedShiftCount, startOfWeek);
            // Verify the selected days show correctly in day view
            editShiftPage.verifySelectedWorkDays(selectedShiftCount, selectedDays);
            // Verify the Location Name shows correctly in day view
            editShiftPage.verifyLocationNameShowsCorrectly(location);
            // Verify the visibility of buttons in day view
            editShiftPage.verifyTheVisibilityOfButtons();
            // Verify the content of options section in day view
            editShiftPage.verifyTheContentOfOptionsSection();
            // Verify the visibility of Clear Edited Fields button in day view
            SimpleUtils.assertOnFail("Clear Edited Fields button failed to load!", editShiftPage.isClearEditedFieldsBtnLoaded(), false);
            // Verify the three columns show on Shift Details section in day view
            editShiftPage.verifyThreeColumns();
            // Verify the editable types show on Shift Detail section in day view
            editShiftPage.verifyEditableTypesShowOnShiftDetail();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify TM cannot have a shift in more than one location without buffer time for Parent Child LG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTMCannotHaveShiftInMoreThanOneLocationWithoutBufferTimeForParentChildLGAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            EditShiftPage editShiftPage = pageFactory.createEditShiftPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createLGScheduleWithGivingTimeRange("06:00am", "06:00am");
            scheduleMainPage.clickOnFilterBtn();
            List<String> childLocationNames = scheduleMainPage.getSpecificFilterNames("location");
            List<String> shiftInfo = new ArrayList<>();
            String firstNameOfTM = "";
            while (firstNameOfTM.equals("") || firstNameOfTM.equalsIgnoreCase("Open")
                    || firstNameOfTM.equalsIgnoreCase("Unassigned")) {
                shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getRandomIndexOfShift());
                //Search shift by TM names: first name and last name
                firstNameOfTM = shiftInfo.get(0);
            }
            String workRole = shiftInfo.get(4);
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(firstNameOfTM);
            scheduleMainPage.saveSchedule();
            //Go to day view, check for TM: A at child location 1, has one shift, ex: 6am - 8am
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectSpecificWorkDay(1);
            newShiftPage.selectChildLocInCreateShiftWindow(childLocationNames.get(0));
            newShiftPage.moveSliderAtCertainPoint("8:00am", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint("6:00am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchTeamMemberByName(firstNameOfTM);
            newShiftPage.clickOnCreateOrNextBtn();
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectSpecificWorkDay(1);
            newShiftPage.selectChildLocInCreateShiftWindow(childLocationNames.get(1));
            newShiftPage.moveSliderAtCertainPoint("10:15am", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint("8:15am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            newShiftPage.searchWithOutSelectTM(firstNameOfTM);
            String shiftWarningMessage = shiftOperatePage.getTheMessageOfTMScheduledStatus();
            SimpleUtils.assertOnFail("30 mins travel time needed violation message fail to load!",
                    !shiftWarningMessage.toLowerCase().contains("30 mins travel time needed"), false);
            shiftOperatePage.clickOnRadioButtonOfSearchedTeamMemberByName(firstNameOfTM);
            if(newShiftPage.ifWarningModeDisplay()){
//                String warningMessage = newShiftPage.getWarningMessageFromWarningModal();
//                if (warningMessage.contains("0 mins travel time needed violation")){
//                    SimpleUtils.pass("30 mins travel time needed violation message displays");
//                } else {
//                    SimpleUtils.fail("There is no '30 mins travel time needed violation' warning message displaying", false);
//                }
                shiftOperatePage.clickOnAssignAnywayButton();
            }
//            else {
//                SimpleUtils.fail("There is no '30 mins travel time needed violation' warning modal displaying!",false);
//            }
            newShiftPage.clickOnOfferOrAssignBtn();
            scheduleMainPage.saveSchedule();
            List<WebElement> shiftsOfFirstDay = scheduleShiftTablePage.getOneDayShiftByName(0, firstNameOfTM);
            SimpleUtils.assertOnFail("'30 mins travel time needed violation' compliance message display failed",
                    !scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup(shiftsOfFirstDay.get(1)).contains("Max shift per day violation") , false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify shifts can be copyed to same day and another location on parent child LG schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyCopyShiftsToSameDayAndAnotherLocationOnParentChildLGScheduleAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

            // Navigate to next week
            scheduleCommonPage.navigateToNextWeek();
            // create the schedule.
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            if (scheduleShiftTablePage.getAllShiftsOfOneTM("open").size()>0) {
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("open");
                scheduleMainPage.saveSchedule();
            }
            scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            scheduleShiftTablePage.expandSpecificCountGroup(2);
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //Get shift count before drag and drop
            int allShiftsCountBefore = scheduleShiftTablePage.getShiftsCount();
            int oneDayShiftsCountBefore = scheduleShiftTablePage.getOneDayShiftCountByIndex(1);
            // Verify can select multiple shifts by pressing Ctrl/Cmd(Mac)
            int selectedShiftCount = 2;
            List<WebElement> selectedShifts = scheduleShiftTablePage.
                    selectMultipleDifferentAssignmentShiftsOnOneDay(selectedShiftCount, 1);
            List<String> shiftNames = new ArrayList<>();
            for (int i=0; i< selectedShiftCount;i++) {
                int index = scheduleShiftTablePage.getTheIndexOfShift(selectedShifts.get(i));
                shiftNames.add(scheduleShiftTablePage.getTheShiftInfoByIndex(index).get(0));
            }

            //Drag the selected shifts to same day
            scheduleShiftTablePage.dragBulkShiftToAnotherDay(selectedShifts, 1, true);

            //Select move option
            scheduleShiftTablePage.selectMoveOrCopyBulkShifts("copy");
            scheduleShiftTablePage.enableOrDisableAllowComplianceErrorSwitch(true);
            scheduleShiftTablePage.enableOrDisableAllowConvertToOpenSwitch(true);
            scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();

            //Check the shift count after drag and drop
            int allShiftsCountAfter = scheduleShiftTablePage.getShiftsCount();
            int oneDayShiftsCountAfter = scheduleShiftTablePage.getOneDayShiftCountByIndex(1);
            SimpleUtils.assertOnFail("The expected count are: "+allShiftsCountBefore+2 + " and "+ oneDayShiftsCountBefore+2
                            + ", but the actual are: "+allShiftsCountAfter + " and "+ oneDayShiftsCountAfter,
                    allShiftsCountAfter == allShiftsCountBefore+2 && oneDayShiftsCountAfter == oneDayShiftsCountBefore+2, false);
            for (int i=0; i< selectedShiftCount;i++) {
                SimpleUtils.assertOnFail("Bulk Drag and drop: the shifts fail to be copied! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size()>0, false);
            }
            //Verify changes can be saved
            scheduleMainPage.saveSchedule();
            allShiftsCountAfter = scheduleShiftTablePage.getShiftsCount();
            oneDayShiftsCountAfter = scheduleShiftTablePage.getOneDayShiftCountByIndex(1);
            SimpleUtils.assertOnFail("The expected count are: "+allShiftsCountBefore + " and "+ oneDayShiftsCountBefore
                            + ", but the actual are: "+allShiftsCountAfter + " and "+ oneDayShiftsCountAfter,
                    allShiftsCountAfter == allShiftsCountBefore+2 && oneDayShiftsCountAfter == oneDayShiftsCountBefore+2, false);
            for (int i=0; i< selectedShiftCount;i++) {
                SimpleUtils.assertOnFail("Bulk Drag and drop: the shifts fail to be moved! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size()>0, false);
            }
            //Verify changes can be published
            createSchedulePage.publishActiveSchedule();
            allShiftsCountAfter = scheduleShiftTablePage.getShiftsCount();
            oneDayShiftsCountAfter = scheduleShiftTablePage.getOneDayShiftCountByIndex(1);
            SimpleUtils.assertOnFail("The expected count are: "+allShiftsCountBefore + " and "+ oneDayShiftsCountBefore
                            + ", but the actual are: "+allShiftsCountAfter + " and "+ oneDayShiftsCountAfter,
                    allShiftsCountAfter == allShiftsCountBefore+2 && oneDayShiftsCountAfter == oneDayShiftsCountBefore+2, false);
            for (int i=0; i< selectedShiftCount;i++) {
                SimpleUtils.assertOnFail("Bulk Drag and drop: the shifts fail to be moved! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size()>0, false);
            }
            //Check the shifts are not display on first location
            scheduleShiftTablePage.expandSpecificCountGroup(1);
            for (int i=0; i< selectedShiftCount;i++) {
                SimpleUtils.assertOnFail("Bulk Drag and drop: the shifts fail to be moved! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size()==0, false);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate shifts can be moved to another day and another location on parent child LG schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyMoveShiftsToAnotherDayAndAnotherLocationOnParentChildLGScheduleAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

            // Navigate to next week
            scheduleCommonPage.navigateToNextWeek();
            // create the schedule.
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            if (scheduleShiftTablePage.getAllShiftsOfOneTM("open").size()>0) {
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("open");
                scheduleMainPage.saveSchedule();
            }
            scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            scheduleShiftTablePage.expandSpecificCountGroup(2);
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //Get shift count before drag and drop
            int allShiftsCountBefore = scheduleShiftTablePage.getShiftsCount();
            int oneDayShiftsCountBefore = scheduleShiftTablePage.getOneDayShiftCountByIndex(2);
            // Verify can select multiple shifts by pressing Ctrl/Cmd(Mac)
            int selectedShiftCount = 2;
            List<WebElement> selectedShifts = scheduleShiftTablePage.
                    selectMultipleDifferentAssignmentShiftsOnOneDay(selectedShiftCount, 1);
            List<String> shiftNames = new ArrayList<>();
            for (int i=0; i< selectedShiftCount;i++) {
                int index = scheduleShiftTablePage.getTheIndexOfShift(selectedShifts.get(i));
                shiftNames.add(scheduleShiftTablePage.getTheShiftInfoByIndex(index).get(0));
            }

            //Drag the selected shifts to same day
            scheduleShiftTablePage.dragBulkShiftToAnotherDay(selectedShifts, 2, true);

            //Select move option
            scheduleShiftTablePage.selectMoveOrCopyBulkShifts("move");
            scheduleShiftTablePage.enableOrDisableAllowComplianceErrorSwitch(true);
            scheduleShiftTablePage.enableOrDisableAllowConvertToOpenSwitch(true);
            scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();

            //Check the shift count after drag and drop
            int allShiftsCountAfter = scheduleShiftTablePage.getShiftsCount();
            int oneDayShiftsCountAfter = scheduleShiftTablePage.getOneDayShiftCountByIndex(2);
            SimpleUtils.assertOnFail("The expected count are: "+allShiftsCountBefore + " and "+ oneDayShiftsCountBefore
                            + ", but the actual are: "+allShiftsCountAfter + " and "+ oneDayShiftsCountAfter,
                    allShiftsCountAfter == allShiftsCountBefore && oneDayShiftsCountAfter == oneDayShiftsCountBefore, false);
            for (int i=0; i< selectedShiftCount;i++) {
                SimpleUtils.assertOnFail("Bulk Drag and drop: the shifts fail to be moved! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size()>0, false);
            }
            //Verify changes can be saved
            scheduleMainPage.saveSchedule();
            allShiftsCountAfter = scheduleShiftTablePage.getShiftsCount();
            oneDayShiftsCountAfter = scheduleShiftTablePage.getOneDayShiftCountByIndex(2);
            SimpleUtils.assertOnFail("The expected count are: "+allShiftsCountBefore + " and "+ oneDayShiftsCountBefore
                            + ", but the actual are: "+allShiftsCountAfter + " and "+ oneDayShiftsCountAfter,
                    allShiftsCountAfter == allShiftsCountBefore && oneDayShiftsCountAfter == oneDayShiftsCountBefore, false);
            for (int i=0; i< selectedShiftCount;i++) {
                SimpleUtils.assertOnFail("Bulk Drag and drop: the shifts fail to be moved! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size()>0, false);
            }
            //Verify changes can be published
            createSchedulePage.publishActiveSchedule();
            allShiftsCountAfter = scheduleShiftTablePage.getShiftsCount();
            oneDayShiftsCountAfter = scheduleShiftTablePage.getOneDayShiftCountByIndex(2);
            SimpleUtils.assertOnFail("The expected count are: "+allShiftsCountBefore + " and "+ oneDayShiftsCountBefore
                            + ", but the actual are: "+allShiftsCountAfter + " and "+ oneDayShiftsCountAfter,
                    allShiftsCountAfter == allShiftsCountBefore && oneDayShiftsCountAfter == oneDayShiftsCountBefore, false);
            for (int i=0; i< selectedShiftCount;i++) {
                SimpleUtils.assertOnFail("Bulk Drag and drop: the shifts fail to be moved! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size()>0, false);
            }
            //Check the shifts are not display on first location
            scheduleShiftTablePage.expandSpecificCountGroup(1);
            for (int i=0; i< selectedShiftCount;i++) {
                SimpleUtils.assertOnFail("Bulk Drag and drop: the shifts fail to be moved! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size()==0, false);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate shifts can be copied to another day and another location on parent child LG schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyCopyShiftsToAnotherDayAndAnotherLocationOnParentChildLGScheduleAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

            // Navigate to next week
            scheduleCommonPage.navigateToNextWeek();
            // create the schedule.
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            if (scheduleShiftTablePage.getAllShiftsOfOneTM("open").size()>0) {
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("open");
                scheduleMainPage.saveSchedule();
            }
            scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            scheduleShiftTablePage.expandSpecificCountGroup(2);
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //Get shift count before drag and drop
            int allShiftsCountBefore = scheduleShiftTablePage.getShiftsCount();
            int oneDayShiftsCountBefore = scheduleShiftTablePage.getOneDayShiftCountByIndex(2);
            // Verify can select multiple shifts by pressing Ctrl/Cmd(Mac)
            int selectedShiftCount = 2;
            List<WebElement> selectedShifts = scheduleShiftTablePage.
                    selectMultipleDifferentAssignmentShiftsOnOneDay(selectedShiftCount, 1);
            List<String> shiftNames = new ArrayList<>();
            for (int i=0; i< selectedShiftCount;i++) {
                int index = scheduleShiftTablePage.getTheIndexOfShift(selectedShifts.get(i));
                shiftNames.add(scheduleShiftTablePage.getTheShiftInfoByIndex(index).get(0));
            }

            //Drag the selected shifts to same day
            scheduleShiftTablePage.dragBulkShiftToAnotherDay(selectedShifts, 2, true);

            //Select move option
            scheduleShiftTablePage.selectMoveOrCopyBulkShifts("copy");
            scheduleShiftTablePage.enableOrDisableAllowComplianceErrorSwitch(true);
            scheduleShiftTablePage.enableOrDisableAllowConvertToOpenSwitch(true);
            scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();

            //Check the shift count after drag and drop
            int allShiftsCountAfter = scheduleShiftTablePage.getShiftsCount();
            int oneDayShiftsCountAfter = scheduleShiftTablePage.getOneDayShiftCountByIndex(2);
            SimpleUtils.assertOnFail("The expected count are: "+allShiftsCountBefore+2 + " and "+ oneDayShiftsCountBefore+2
                            + ", but the actual are: "+allShiftsCountAfter + " and "+ oneDayShiftsCountAfter,
                    allShiftsCountAfter == allShiftsCountBefore+2 && oneDayShiftsCountAfter == oneDayShiftsCountBefore+2, false);
            for (int i=0; i< selectedShiftCount;i++) {
                SimpleUtils.assertOnFail("Bulk Drag and drop: the shifts fail to be copied! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size()>0, false);
            }
            //Verify changes can be saved
            scheduleMainPage.saveSchedule();
            allShiftsCountAfter = scheduleShiftTablePage.getShiftsCount();
            oneDayShiftsCountAfter = scheduleShiftTablePage.getOneDayShiftCountByIndex(2);
            SimpleUtils.assertOnFail("The expected count are: "+allShiftsCountBefore + " and "+ oneDayShiftsCountBefore
                            + ", but the actual are: "+allShiftsCountAfter + " and "+ oneDayShiftsCountAfter,
                    allShiftsCountAfter == allShiftsCountBefore+2 && oneDayShiftsCountAfter == oneDayShiftsCountBefore+2, false);
            for (int i=0; i< selectedShiftCount;i++) {
                SimpleUtils.assertOnFail("Bulk Drag and drop: the shifts fail to be moved! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size()>0, false);
            }
            //Verify changes can be published
            createSchedulePage.publishActiveSchedule();
            allShiftsCountAfter = scheduleShiftTablePage.getShiftsCount();
            oneDayShiftsCountAfter = scheduleShiftTablePage.getOneDayShiftCountByIndex(2);
            SimpleUtils.assertOnFail("The expected count are: "+allShiftsCountBefore + " and "+ oneDayShiftsCountBefore
                            + ", but the actual are: "+allShiftsCountAfter + " and "+ oneDayShiftsCountAfter,
                    allShiftsCountAfter == allShiftsCountBefore+2 && oneDayShiftsCountAfter == oneDayShiftsCountBefore+2, false);
            for (int i=0; i< selectedShiftCount;i++) {
                SimpleUtils.assertOnFail("Bulk Drag and drop: the shifts fail to be moved! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size()>0, false);
            }
            //Check the shifts are not display on first location
            scheduleShiftTablePage.expandSpecificCountGroup(1);
            for (int i=0; i< selectedShiftCount;i++) {
                SimpleUtils.assertOnFail("Bulk Drag and drop: the shifts fail to be moved! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size()==0, false);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate navigation, roster, schedule and dashboard of Parent Child LG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void validateNavigationRosterScheduleAndDashboardOfParentChildLGAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            ForecastPage forecastPage = pageFactory.createForecastPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            goToSchedulePageScheduleTab();
            //Verify the all slave locations will be generated when generate the master location for Master-Slave LG
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createLGScheduleWithGivingTimeRange("06:00am", "06:00am");
            if(smartCardPage.isRequiredActionSmartCardLoaded()) {
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Unassigned");
                scheduleMainPage.saveSchedule();
            }
            createSchedulePage.publishActiveSchedule();
            String workRole = shiftOperatePage.getRandomWorkRole();
            List<String> locationNames = scheduleMainPage.getSpecificFilterNames("location");
            scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            ArrayList<HashMap<String,String>> locations  = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            SimpleUtils.assertOnFail("The sub location count is incorrectly on schedule! ",
                    locationNames.size() == locations.size(), false);
            List<String> locationsFromGroupByOption = new ArrayList<>();
            for (HashMap<String, String> stringStringHashMap : locations) {
                locationsFromGroupByOption.add(stringStringHashMap.get("optionName"));
            }

            SimpleUtils.assertOnFail("The locations from group by option are:"+locationsFromGroupByOption
                            + " the locations from filter are:"+ locationNames,
                    locationsFromGroupByOption.toString().equalsIgnoreCase(locationNames.toString()), false);

            teamPage.goToTeam();
            int parentRosterCount = teamPage.verifyTMCountIsCorrectOnRoster();
            //Verify the roster can be at master or slave location level for Master-Slave LG
            SimpleUtils.assertOnFail("The parent location roster count is:"+parentRosterCount,
                    parentRosterCount > 0, false);
            String tmName = teamPage.selectATeamMemberToViewProfile();
            //Verify the SM dashboard of P2P LG, SM Dashboard will aggregate data for the widgets
            dashboardPage.clickOnDashboardConsoleMenu();
            Thread.sleep(3000);
            int upcomingShiftCountOnParentSMDashboard = dashboardPage.getUpComingShifts().size();
            SimpleUtils.assertOnFail("The parent location upcoming shift count is:"+upcomingShiftCountOnParentSMDashboard,
                    upcomingShiftCountOnParentSMDashboard == 8
                            || upcomingShiftCountOnParentSMDashboard == 0, false);
            //Verify all sub-locations of master-slave LG have their own demand forecasts/labor forecast/staffing guidance
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Forecast.getValue());
            forecastPage.clickOnShopper();
            forecastPage.verifyDemandForecastCanLoad();
            forecastPage.clickOnLabor();
            forecastPage.verifyLaborForecastCanLoad();

            //Verify TMs in parent location can be searched out on other locations for Master-Slave LG
            if (!tmName.equalsIgnoreCase("")) {
                goToSchedulePageScheduleTab();
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(tmName);
                createShiftsWithSpecificValues(workRole, null, locationNames.get(0), "9:00am", "12:00pm",
                        1, Arrays.asList(1), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), null, tmName);
                List<WebElement> tmShifts = scheduleShiftTablePage.getAllShiftsOfOneTM(tmName.split(" ")[0]);
                SimpleUtils.assertOnFail("TM "+ tmName+"'s shift should be created! ",
                        tmShifts.size()>0,false);
                scheduleMainPage.clickOnCancelButtonOnEditMode();
            }else
                SimpleUtils.fail("The TM from parent child LG roster should not be null! ", false);

            //Verify the all slave location will be ungenerated when ungenerate the master location for Master-Slave LG
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            SimpleUtils.assertOnFail("The schedule should be ungenerate successfully! ",
                    !createSchedulePage.isWeekGenerated() && createSchedulePage.isGenerateButtonLoaded(), false);
            //Verify the location navigation of Master-Slave LG
            List<String> allLocations = locationSelectorPage.getAllUpperFieldNamesInUpperFieldDropdownList(Location);
            SimpleUtils.assertOnFail("Parent location should display in location navigation list",
                    allLocations.contains(location), false);
            for (int i=0;i< locationNames.size(); i++){
                SimpleUtils.assertOnFail("Child location "+ locationNames.get(i)+" should not display in location navigation list: "+ allLocations,
                        !allLocations.contains(locationNames.get(i)), false);
            }

            //Verify all sub-locations of master-slave LG have their own operating hours
            createSchedulePage.clickCreateScheduleButton();
            for (int i=0;i< locationNames.size(); i++) {
                createSchedulePage.selectLocationOnCreateScheduleEditOperatingHoursPage(locationNames.get(i));
                createSchedulePage.editOperatingHoursWithGivingPrameters("8:00AM", "8:00PM");
            }
            createSchedulePage.clickNextBtnOnCreateScheduleWindow();
            createSchedulePage.selectWhichWeekToCopyFrom("SUGGESTED");
            createSchedulePage.clickOnFinishButtonOnCreateSchedulePage();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Cosimo")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the functionality of \"Edit Operating hours\" option for location group")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyEditOperatingHoursForLGAsInternalAdmin(String username, String password, String browser, String location)
            throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();

            //Go to the schedule page
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(FTSERelevantTest.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.clickOnWeekView();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isActiveWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            Thread.sleep(5000);
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            //The P2P location has a dropdown list which includes child locations
            scheduleMainPage.goToToggleSummaryView();
            scheduleMainPage.checkOperatingHoursOnToggleSummary();
            SimpleUtils.assertOnFail("The drop down list is unavailable!", locationSelectorPage.isChangeLocationButtonLoaded(), false);

            //Compare the operating hours between Editing and Toggle Summary pages
            scheduleMainPage.goToToggleSummaryView();
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            Thread.sleep(3);
            String childLocation = "Child002";
            createSchedulePage.selectRandomOrSpecificLocationOnUngenerateScheduleEditOperatingHoursPage(childLocation);
            scheduleMainPage.checkOperatingHoursOnToggleSummary();
            scheduleMainPage.clickEditBtnOnToggleSummary();
            createSchedulePage.selectLocationOnEditOperatingHoursPage(childLocation);
            scheduleMainPage.checkOperatingHoursOnEditDialog();

            //Go to Edit Operating Hours page and check relevant content
            scheduleMainPage.isSaveBtnLoadedOnEditOpeHoursPageForOP();
            scheduleMainPage.isCancelBtnLoadedOnEditOpeHoursPageForOP();

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Cosimo")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the functionality of SAVE button on \"Edit operating hours\" window for location group")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyEditOperatingHoursFunctionForLGAsInternalAdmin(String username, String password, String browser, String location)
            throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();

            //Go to the schedule page
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(FTSERelevantTest.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.clickOnWeekView();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isActiveWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            Thread.sleep(5000);
            createSchedulePage.createScheduleForNonDGFlowNewUI();

            //Edit the operating day and cancel all actions.
            scheduleMainPage.goToToggleSummaryView();
            scheduleMainPage.clickEditBtnOnToggleSummary();
            String childLocation1 = "Child001";
            String childLocation2 = "Child002";
            createSchedulePage.selectLocationOnEditOperatingHoursPage(childLocation2);
            List<String> weekDay = new ArrayList<>(Arrays.asList("Sunday"));
            scheduleMainPage.closeTheParticularOperatingDay(weekDay);
            scheduleMainPage.openTheParticularOperatingDay(weekDay);
            scheduleMainPage.editTheOperatingHoursWithFixedValue(weekDay, "10:00AM","10:00PM");
            scheduleMainPage.clickCancelBtnOnEditOpeHoursPageForOP();
            scheduleMainPage.checkOperatingHoursOnToggleSummary();

            //Edit the operating day and save all actions.
            scheduleMainPage.clickEditBtnOnToggleSummary();
            createSchedulePage.selectLocationOnEditOperatingHoursPage(childLocation1);
            List<String> weekDays = new ArrayList<>(Arrays.asList("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"));
            scheduleMainPage.editTheOperatingHoursWithFixedValue(weekDays, "10:00AM","10:00PM");
            scheduleMainPage.clickSaveBtnOnEditOpeHoursPageForOP();
            scheduleMainPage.clickEditBtnOnToggleSummary();
            createSchedulePage.selectLocationOnEditOperatingHoursPage(childLocation2);
            scheduleMainPage.editTheOperatingHoursWithFixedValue(weekDays, "10:00AM","10:00PM");
            scheduleMainPage.clickSaveBtnOnEditOpeHoursPageForOP();

            scheduleMainPage.goToToggleSummaryView();
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            createSchedulePage.selectRandomOrSpecificLocationOnUngenerateScheduleEditOperatingHoursPage(childLocation1);
            scheduleMainPage.checkOpeHrsOfParticualrDayOnToggleSummary(weekDays, "10AM-10PM");
            createSchedulePage.closeSearchBoxForLocations();
            createSchedulePage.selectRandomOrSpecificLocationOnUngenerateScheduleEditOperatingHoursPage(childLocation2);
            scheduleMainPage.checkOpeHrsOfParticualrDayOnToggleSummary(weekDays, "10AM-10PM");
            createSchedulePage.closeSearchBoxForLocations();

            //Check the time duration on the day view
            createSchedulePage.createScheduleForNonDGFlowNewUIWithoutUpdate();
            scheduleCommonPage.clickOnDayView();
            ArrayList<String> timeDurations = scheduleShiftTablePage.getScheduleDayViewGridTimeDuration();
            String timeDuration = timeDurations.get(0) + "-" + timeDurations.get(timeDurations.size()-1);
            SimpleUtils.assertOnFail("The time duration is not matched between day view and toggle summary view!", timeDuration.equalsIgnoreCase("8 AM-12 AM"), false);

            //Check the closed operating day.
            scheduleMainPage.goToToggleSummaryView();
            scheduleMainPage.clickEditBtnOnToggleSummary();
            createSchedulePage.selectLocationOnEditOperatingHoursPage(childLocation1);
            scheduleMainPage.closeTheParticularOperatingDay(weekDays);
            scheduleMainPage.clickSaveBtnOnEditOpeHoursPageForOP();
            Thread.sleep(3);

            scheduleMainPage.clickEditBtnOnToggleSummary();
            createSchedulePage.selectLocationOnEditOperatingHoursPage(childLocation2);
            scheduleMainPage.closeTheParticularOperatingDay(weekDays);
            scheduleMainPage.clickSaveBtnOnEditOpeHoursPageForOP();
            Thread.sleep(3);

            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(FTSERelevantTest.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.clickOnWeekView();
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            createSchedulePage.selectRandomOrSpecificLocationOnUngenerateScheduleEditOperatingHoursPage(childLocation1);
            scheduleMainPage.checkClosedDayOnToggleSummary(weekDays);
            createSchedulePage.closeSearchBoxForLocations();
            createSchedulePage.selectRandomOrSpecificLocationOnUngenerateScheduleEditOperatingHoursPage(childLocation2);
            scheduleMainPage.checkClosedDayOnToggleSummary(weekDays);
            createSchedulePage.closeSearchBoxForLocations();

            createSchedulePage.createScheduleForNonDGFlowNewUIWithoutUpdate();
            scheduleCommonPage.clickOnWeekView();
            int shiftCountWeek = scheduleShiftTablePage.getShiftsCount();
            SimpleUtils.assertOnFail("The schedule is not empty!", shiftCountWeek == 0, false);
            scheduleCommonPage.clickOnDayView();
            int shiftCountDay = scheduleShiftTablePage.getShiftsCount();
            SimpleUtils.assertOnFail("The current day is not empty!", shiftCountDay == 0, false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

}
