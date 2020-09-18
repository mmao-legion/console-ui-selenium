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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
        locationSelectorPage.changeDistrict("SanityTest");

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

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "LegionBasic_Enterprise")
    @TestName(description = "Validate legionbasic navigation function")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyLegionBasicNavigationFunctionIn(String browser, String username, String password, String location) throws Exception {

        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

        String defaultLocationAfterLogin = dashboardPage.getCurrentLocation();

        //get all locations from location navigation bar
        List<String> locationsInDashboard = dashboardPage.getLocationListInDashboard();
        //validate there is no district
        boolean isShow = dashboardPage.IsThereDistrictNavigationForLegionBasic();
        if (!isShow ) {
            SimpleUtils.pass("There is no district show");
        }else
            SimpleUtils.fail("For Legionbasic ,it should show district",true);
        //Validation the location list on navigation bar
           // go to Controls->global location>get all locations ->compare with navigation bar show
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        controlsNewUIPage.clickOnControlsConsoleMenu();
        SimpleUtils.assertOnFail("Controls Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
        controlsNewUIPage.clickOnGlobalLocationButton();
        boolean isLocationsTabShow = controlsNewUIPage.isControlsLocationsCard();
        if (isLocationsTabShow ) {
            controlsNewUIPage.clickOnLocationsTabInGlobalModel();
            List<String> locationList = controlsNewUIPage.getAllLocationsInGlobalModel();
            if ((locationList .size()!= 0 && locationsInDashboard.size() !=0)&& (locationList.size() ==locationsInDashboard.size()))
            {


                String[] locationListSwitch = locationList.toArray(new String[]{});
                String[] locationsInDashboardSwitch = locationsInDashboard.toArray(new String[]{});
                Arrays.sort(locationListSwitch);
                Arrays.sort(locationsInDashboardSwitch);
                if (Arrays.equals(locationListSwitch, locationsInDashboardSwitch)) {
                    SimpleUtils.pass("Legion basic show all locations successfully");
                }
            } else
                SimpleUtils.fail("Legion basic location not show well",true);
            }else
            SimpleUtils.report("Locations Tab is not show ,maybe OPView switch is on");

        //Validated navigation bar show after switch to other tabs and then return to dashboard page

        SchedulePage schedulePage = pageFactory.createConsoleSchedulePage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        controlsNewUIPage.clickOnControlsConsoleMenu();
        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        teamPage.goToTeam();
        dashboardPage.clickOnDashboardConsoleMenu();
        String locationAfterSwitchToOtherPageAndBackToDashboard = dashboardPage.getCurrentLocation();
        if (locationAfterSwitchToOtherPageAndBackToDashboard .equals(defaultLocationAfterLogin)) {
            SimpleUtils.pass("After back to dashboard page the location info should not be changed.");
        }else
            SimpleUtils.fail("After back to dashboard page the location info was changed",true);

    }
}
