package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Calendar;

public class NewNavigationFlowTest extends TestBase {


    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{
        this.createDriver((String)params[0],"83","Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
    }



    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate location profile page in controls")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyLocationProfilePageInControls(String browser, String username, String password, String location) throws Exception {

        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        controlsNewUIPage.clickOnControlsConsoleMenu();
        SimpleUtils.assertOnFail("Controls Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
        // Validate Controls Location Profile Section
        controlsNewUIPage.clickOnControlsLocationProfileSection();
        boolean isLocationProfile = controlsNewUIPage.isControlsLocationProfileLoaded();
        SimpleUtils.assertOnFail("Controls Page: Location Profile Section not Loaded.", isLocationProfile, true);


    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate manager location for one user in controls")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyManagerLocationForOneUserInControlsInControls(String browser, String username, String password, String location) throws Exception {


        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        controlsNewUIPage.clickOnControlsConsoleMenu();
        SimpleUtils.assertOnFail("Controls Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);

        //search one user and to see edit
        controlsNewUIPage.clickOnControlsUsersAndRolesSection();
        String userFirstName = "a";

        //Validate manager location for one user
        controlsNewUIPage.verifyUpdateUserAndRolesOneUserLocationInfo(userFirstName);
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate location list in Timesheet page")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyLocationListFunctionInTimesheet(String browser, String username, String password, String location) throws Exception {


        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

        String currentDistrict = dashboardPage.getCurrentDistrict();
        //change district to show all locations
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict("SST District1");

        TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();

        // Click on "Timesheet" option menu.
        timeSheetPage.clickOnTimeSheetConsoleMenu();
        SimpleUtils.assertOnFail("TimeSheet Page not loaded Successfully!",timeSheetPage.isTimeSheetPageLoaded() , false);

        //check location list
        String locationName = timeSheetPage.verifyLocationList();
        dashboardPage.clickOnDashboardConsoleMenu();
        String updatedLocationName = dashboardPage.getCurrentLocation();
        if (updatedLocationName.equals(locationName)) {
            SimpleUtils.pass("Location switch in Timesheet successfully");

        }else
            SimpleUtils.fail("Location switch in Timesheet failed",false);

        //change location to default one in order to avoid other test case
        locationSelectorPage.changeDistrict(currentDistrict);


    }
}
