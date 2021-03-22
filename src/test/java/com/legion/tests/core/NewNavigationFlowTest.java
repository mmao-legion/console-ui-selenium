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
import java.util.*;

public class NewNavigationFlowTest extends TestBase {

    public enum modelSwitchOperation{

        Console("Console"),
        OperationPortal("Operation Portal");

        private final String value;
        modelSwitchOperation(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }


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
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.selectLocationByIndex(1);
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
//        locationSelectorPage.changeDistrict("No touch no delete");

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

//        //change location to default one in order to avoid other test case
//        locationSelectorPage.changeDistrict(currentDistrict);

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
    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate navigation bar view for admin user")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDashboardViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        SimpleUtils.assertOnFail("Navigation Bar - Location field not loaded successfuly!", locationSelectorPage.isChangeLocationButtonLoaded(), false);
        locationSelectorPage.isDMView();
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate navigation bar view for district manager")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDashboardViewAsDistrictManager(String browser, String username, String password, String location) throws Exception {

        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        SimpleUtils.assertOnFail("Navigation Bar - Location field not loaded successfuly!", locationSelectorPage.isChangeLocationButtonLoaded(), false);
        locationSelectorPage.isDMView();
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate navigation bar for SM user")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDashboardViewAsStoreManager(String browser, String username, String password, String location) throws Exception {

        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        SimpleUtils.assertOnFail("Navigation Bar - Location field not loaded successfuly!", locationSelectorPage.isChangeLocationButtonLoaded(), false);
        locationSelectorPage.isSMView();
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate search district function in navigation bar")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyNavigationBarSearchDistrictFunctionInternalAdmin(String browser, String username, String password, String location) throws Exception {

        String searchDistrictText = "*";

        List<Integer> districtsCountOnDashboardPage = new ArrayList<>();

        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        SimpleUtils.assertOnFail("Navigation Bar - Location field not loaded successfuly!", locationSelectorPage.isChangeLocationButtonLoaded(), false);

        //Search districts in Navigation
        districtsCountOnDashboardPage = locationSelectorPage.searchDistrict(searchDistrictText);

        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
        //go to locations tab
        locationsPage.clickOnLocationsTab();
        //go to sub-district tab
        locationsPage.goToSubDistrictsInLocationsPage();

        //get the count of all enabled status districts in location-district smart card
        int alldistrictsCountOnDistrcitsPage = locationsPage.getTotalEnabledDistrictsCount();

        //get the count of search result of distrcits in navigation
        int allLocationsInDashboardPage = districtsCountOnDashboardPage.get(0);

        //compare above two data
        if (allLocationsInDashboardPage == alldistrictsCountOnDistrcitsPage) {
            SimpleUtils.pass("User can search district on dashboard page successfully");
        } else {
            SimpleUtils.fail("The district searching function can't works", true);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate search location function in navigation bar")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyNavigationBarSearchLocationFunctionAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

        //change district to show all locations
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict("Test District");

        String searchLocationText = "*";
        Thread.sleep(4000);
        String currentDistrict = dashboardPage.getCurrentDistrict();
        SimpleUtils.report(currentDistrict);

        List<String> locationsInNavigationBar = new ArrayList<>();
        List<String> locationsInDistrictPage = new ArrayList<>();

        //input * to search all locations in specify district
        locationsInNavigationBar = locationSelectorPage.searchLocation(searchLocationText);

        //go to OPS -> Locations -> District function
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
        //go to locations tab
        locationsPage.clickOnLocationsTab();
        //go to sub-district tab
        locationsPage.goToSubDistrictsInLocationsPage();
        //get all locations in specify district in OPS-District function
        locationsInDistrictPage = locationsPage.getLocationsInDistrict(currentDistrict);
        //compare these two list
        if(locationsInNavigationBar.size() == locationsInDistrictPage.size()){
            for(String locationInNavigationBar:locationsInNavigationBar){
                for(String locationInDistrictPage:locationsInDistrictPage){
                    if(locationInNavigationBar.contains(locationInDistrictPage)){
                        SimpleUtils.pass(locationInNavigationBar + " is showing both in navigation bar and district function!");
                        break;
                    }else{
                        continue;
//                        SimpleUtils.pass(locationInNavigationBar + " is NOT showing both in navigation bar and district function!");
                    }
                }
            }
            SimpleUtils.pass("Locations in navigation bar are matched with which in dsitrict.");
        }else {
            SimpleUtils.pass("Locations in navigation bar are NOT matched with which in dsitrict.");
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validated navigation bar show after switch to other tabs and then return to dashboard page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyNavigationBarWhenSwitchDifferentTabsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        String districtName="Cinemark";
        String locationName="00840";
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        SimpleUtils.assertOnFail("Navigation Bar - Location field not loaded successfuly!", locationSelectorPage.isChangeLocationButtonLoaded(), false);

        locationSelectorPage.changeDistrict(districtName);
        Thread.sleep(4000);
        locationSelectorPage.changeLocation(locationName);

        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        teamPage.goToTeam();
        if(teamPage.loadTeamTab()){
            String teamPageDistrcit = dashboardPage.getCurrentDistrict();
            String teamPageLocation = dashboardPage.getCurrentLocationInDMView();
            if(teamPageDistrcit.equals(districtName) && teamPageLocation.equals(locationName)){
                SimpleUtils.pass("The navigation bar shows well on team page");
            }else{
                SimpleUtils.fail("The navigation bar shows incorrect on team page",true);
            }
        }

        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
        if(scheduleOverviewPage.loadScheduleOverview()){
            String schedulePageDistrcit = dashboardPage.getCurrentDistrict();
            String schedulePageLocation = dashboardPage.getCurrentLocationInDMView();
            if(schedulePageDistrcit.equals(districtName) && schedulePageLocation.equals(locationName)){
                SimpleUtils.pass("The navigation bar shows well on schedule page");
            }else{
                SimpleUtils.fail("The navigation bar shows incorrect on schedule page",true);
            }
        }

        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        controlsNewUIPage.clickOnControlsConsoleMenu();
        if(controlsNewUIPage.isControlsPageLoaded()){
            Thread.sleep(5000);
            String controlsPageLocation = controlsNewUIPage.getCurrentLocationInControls();
            if(controlsPageLocation.equals(locationName)){
                SimpleUtils.pass("The navigation bar shows well on controls page");
            }else{
                SimpleUtils.fail("The navigation bar shows incorrect on controls page",true);
                SimpleUtils.report("The locations filed on controls page is: " + controlsPageLocation);
            }
        }

        dashboardPage.navigateToDashboard();
        if(dashboardPage.isDashboardPageLoaded()){
            String dashboardPageDistrict = dashboardPage.getCurrentDistrict();
            String dashboardPageLocation = dashboardPage.getCurrentLocationInDMView();
            if(dashboardPageDistrict.equals(districtName) && dashboardPageLocation.equals(locationName)){
                SimpleUtils.pass("The navigation bar shows well after back to dashboard page");
            }else{
                SimpleUtils.fail("The navigation bar shows incorrect after back to controls page",true);
            }
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validated the recently views list should show correctly for user")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyNavigationBarRecentlyViewListAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        List<String> searchDistrictsList = new ArrayList<String>(){{
            add("Cinemark");
            add("Central Texas Region");
            add("Southwest Region");
            add("Atlantic Central Region");
            add("Upper Midwest Region");
            add("Western Region");
        }};

        List<String> finalDistrictsList = new ArrayList<String>();

        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        SimpleUtils.assertOnFail("Navigation Bar - Location field not loaded successfuly!", locationSelectorPage.isChangeLocationButtonLoaded(), false);

        for(String district:searchDistrictsList){
            if(district!=null) {
                locationSelectorPage.changeDistrict(district);
            }
        }

        finalDistrictsList = dashboardPage.getDistrcitListInDashboard();
        Collections.reverse(searchDistrictsList);

        if(finalDistrictsList.size()==5){
        for(int i=0;i<finalDistrictsList.size();i++){
            if(finalDistrictsList.get(i).equals(searchDistrictsList.get(i))){
                SimpleUtils.pass("The " + (i+1) + " location is " + finalDistrictsList.get(i));
            }else{
                SimpleUtils.fail("The order of districts list is NOT correct",true);

            }
        }
        }else {
            SimpleUtils.fail("The count of districts is NOT correct",true);
        }
    }
}
