package com.legion.tests.core;

import com.legion.pages.*;
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
        schedulePage.selectRandomLocationOnUngenerateScheduleEditOperatingHoursPage();
        List<String> toCloseDays = new ArrayList<>();
        schedulePage.editOperatingHoursOnScheduleOldUIPage("8:00", "20:00", toCloseDays);
        // Edit random location's operating hours during generate schedule
        schedulePage.createScheduleForNonDGFlowNewUI();
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the generation of LG schedule")
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
}
