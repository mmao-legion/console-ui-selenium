package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;
import cucumber.api.java.ro.Si;
import org.apache.xmlbeans.impl.xb.xmlconfig.impl.NsconfigImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.legion.utils.MyThreadLocal.*;

public class LocationsTest extends TestBase {

    public enum modelSwitchOperation{

        Console("Console"),
        OperationPortal("Operation Portal");

        private final String value;
        modelSwitchOperation(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }
    public enum locationGroupSwitchOperation{

        MS("Master Slave"),
        PTP("Peer to Peer");

        private final String value;
        locationGroupSwitchOperation(final String newValue) {
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
    @TestName(description = "Validate to create location with mandatory fields")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateRegularLocationWithMandatoryFields(String browser, String username, String password, String location) throws Exception {

        SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
        String currentTime =  dfs.format(new Date());
        String locationName = "AutoCreate" +currentTime;
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

        //go to locations tab
        locationsPage.clickOnLocationsTab();
        //check locations item
        locationsPage.validateItemsInLocations();
        //go to sub-locations tab
        locationsPage.goToSubLocationsInLocationsPage();
        //add new regular location
        locationsPage.addNewRegularLocationWithMandatoryFields(locationName);
        //search created location
        if (locationsPage.searchNewLocation(locationName)) {
            SimpleUtils.pass("Create new location successfully");
        }else
            SimpleUtils.fail("Create new location failed or can't search created location",true);

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to create location with mandatory fields")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateRegularLocationWithAllFieldsAndNavigate(String browser, String username, String password, String location) throws Exception {

        SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
        String currentTime =  dfs.format(new Date());
        String locationName = "AutoCreate" +currentTime;
        int index =0;
        String searchCharactor = "No touch";
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

        //go to locations tab
        locationsPage.clickOnLocationsTab();
        //check locations item
        locationsPage.validateItemsInLocations();
        //go to sub-locations tab
        locationsPage.goToSubLocationsInLocationsPage();
        //add new regular location
        locationsPage.addNewRegularLocationWithAllFields(locationName,searchCharactor, index);

//           //search created location
//        if (locationsPage.searchNewLocation(locationName)) {
//            SimpleUtils.pass("Create new location successfully");
//        }else
//            SimpleUtils.fail("Create new location failed or can't search created location",true);
        HashMap<String, String> locationInfoDetails =locationsPage.getLocationInfo(locationName);
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict(locationInfoDetails.get("locationDistrict"));
        locationSelectorPage.changeLocation(locationInfoDetails.get("locationName"));

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to create location with mandatory fields")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateMockLocationAndNavigate(String browser, String username, String password, String location) throws Exception {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
        String currentTime =  dfs.format(new Date());
        String locationName = "AutoCreate" +currentTime;
        int index =0;
        String searchCharactor = "No touch";
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

        //go to locations tab
        locationsPage.clickOnLocationsTab();
        //check locations item
        locationsPage.validateItemsInLocations();
        //go to sub-locations tab
        locationsPage.goToSubLocationsInLocationsPage();
        //add one mock location，first create one new location and then to mock that -to avoid duplication
        locationsPage.addNewRegularLocationWithAllFields(locationName,searchCharactor, index);;
        locationsPage.addNewMockLocationWithAllFields(locationName,locationName,index);
//        //search created location
//        if (locationsPage.searchNewLocation(getLocationName())) {
//            SimpleUtils.pass("Create new mock location successfully");
//        }else
//            SimpleUtils.fail("Create new location failed or can't search created location",true);
        HashMap<String, String> locationInfoDetails =locationsPage.getLocationInfo(locationName);
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict(locationInfoDetails.get("locationDistrict"));
        locationSelectorPage.changeLocation(locationInfoDetails.get("locationName"));
    }
    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to create NSO location with all fields")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateNSOLocationAndNavigate(String browser, String username, String password, String location) throws Exception {

        SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
        String currentTime =  dfs.format(new Date());
        String locationName = "AutoCreate" +currentTime;
        int index =0;
        String searchCharactor = "No touch";

        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

        //go to locations tab
        locationsPage.clickOnLocationsTab();
        //check locations item
        locationsPage.validateItemsInLocations();
        //go to sub-locations tab
        locationsPage.goToSubLocationsInLocationsPage();
        locationsPage.addNewNSOLocation(locationName,searchCharactor, index);
//        if (locationsPage.searchNewLocation(getLocationName())) {
//            SimpleUtils.pass("Create new mock location successfully");
//        }else
//            SimpleUtils.fail("Create new location failed or can't search created location",true);
        HashMap<String, String> locationInfoDetails =locationsPage.getLocationInfo(locationName);
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict(locationInfoDetails.get("locationDistrict"));
        locationSelectorPage.changeLocation(locationInfoDetails.get("locationName"));
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to disable location")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDisableEnableLocationFunction(String browser, String username, String password, String location) throws Exception {

        String searchInputText="b,AutoCreate,a";
        String disableLocationName ="";
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

        //go to locations tab
        locationsPage.clickOnLocationsTab();
        //check locations item
        locationsPage.validateItemsInLocations();
        //go to sub-locations tab
        locationsPage.goToSubLocationsInLocationsPage();
        disableLocationName = locationsPage.disableLocation(searchInputText);
        locationsPage.enableLocation(disableLocationName);
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to import location")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyImportLocationDistrict(String browser, String username, String password, String location) throws Exception {

        String searchInputText="Child_LocationGroup_Example";
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

        //go to locations tab
        locationsPage.clickOnLocationsTab();
        //check locations item
        locationsPage.validateItemsInLocations();
        //go to sub-locations tab
        locationsPage.goToSubLocationsInLocationsPage();
        locationsPage.verifyImportLocationDistrict();
        if (locationsPage.searchNewLocation(searchInputText)) {
            SimpleUtils.pass("Create new mock location successfully");
        }else
            SimpleUtils.fail("Create new location failed or can't search created location",true);

    }



    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to export location that include export all and export specific location")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyExportLocationDistrict(String browser, String username, String password, String location) throws Exception {

        int index =0;
        String searchCharactor = "*";
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

        //go to locations tab
        locationsPage.clickOnLocationsTab();
        //check locations item
        locationsPage.validateItemsInLocations();
        //go to sub-locations tab
        locationsPage.goToSubLocationsInLocationsPage();
        locationsPage.verifyExportAllLocationDistrict();
        locationsPage.verifyExportSpecificLocationDistrict(searchCharactor,index);
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate Master Slave Location group creation")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyMSLocationGroupFunction(String browser, String username, String password, String location) throws Exception {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
        String currentTime =  dfs.format(new Date());
        String locationName = "LGMSAuto" +currentTime;
        int index =0;
        String searchCharactor = "No touch";
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

        //go to locations tab
        locationsPage.clickOnLocationsTab();
        //check locations item
        locationsPage.validateItemsInLocations();
        //go to sub-locations tab
        locationsPage.goToSubLocationsInLocationsPage();
        //add new MS location group-parent and child
        String  parentRelationship = "Parent location";
        locationsPage.addParentLocation(locationName,searchCharactor, index,parentRelationship,locationGroupSwitchOperation.MS.getValue());
        String childLocationName = "childLocationForMS" +currentTime;
        String  childRelationship = "Part of a location group";
        locationsPage.addChildLocation(childLocationName,locationName,searchCharactor,index,childRelationship);
        //get location's  info
        HashMap<String, String> locationInfoDetails =locationsPage.getLocationInfo(locationName);
        //check location group navigation
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict(locationInfoDetails.get("locationDistrict"));
//        if (!locationSelectorPage.searchLocationIsExist(childLocationName)) {
//            SimpleUtils.pass("Right behavior:MS child location should not show in location navigation bar");
//        }else
//            SimpleUtils.fail("Error behavior:MS child location show in location navigation bar",true);
        locationSelectorPage.changeLocation(locationInfoDetails.get("locationName"));

        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        teamPage.goToTeam();
        if (teamPage.verifyThereIsLocationColumnForMSLocationGroup()) {
            SimpleUtils.pass("Location column in Team Tab is showing for MS location group");
        }else
            SimpleUtils.fail("There is no location column in Team Tab for MS location group",true);

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate Peer to peer Location group creation")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyPTTLocationGroupFunction(String browser, String username, String password, String location) throws Exception {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
        String currentTime =  dfs.format(new Date());
        String locationName = "LGPTPAuto" +currentTime;
        int index =0;
        int childLocationNum = 1;
        String searchCharactor = "No touch";
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

        //go to locations tab
        locationsPage.clickOnLocationsTab();
        //check locations item
        locationsPage.validateItemsInLocations();
        //go to sub-locations tab
        locationsPage.goToSubLocationsInLocationsPage();
        //add new MS location group-parent and child
        String  parentRelationship = "Parent location";
        locationsPage.addParentLocation(locationName,searchCharactor, index,parentRelationship,locationGroupSwitchOperation.PTP.getValue());
        try {
            for (int i = 0; i <childLocationNum ; i++) {
                String childLocationName = "childLocationForMS" +currentTime;
                String  childRelationship = "Part of a location group";
                locationsPage.addChildLocation(childLocationName,locationName,searchCharactor,index,childRelationship);
            }
        }catch (Exception e){
            SimpleUtils.fail("Child location creation failed",true);
        }


        //get location's  info
        HashMap<String, String> locationInfoDetails =locationsPage.getLocationInfo(locationName);
        if (locationsPage.verifyLGIconShowWellOrNot(locationName,childLocationNum)) {

        }
        //check location group navigation
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict(locationInfoDetails.get("locationDistrict"));
//        if (!locationSelectorPage.searchLocationIsExist(childLocationName)) {
//            SimpleUtils.pass("Right behavior:MS child location should not show in location navigation bar");
//        }else
//            SimpleUtils.fail("Error behavior:MS child location show in location navigation bar",true);
        locationSelectorPage.changeLocation(locationInfoDetails.get("locationName"));

        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        teamPage.goToTeam();
        if (teamPage.verifyThereIsLocationColumnForMSLocationGroup()) {
            SimpleUtils.pass("Location column in Team Tab is showing for MS location group");
        }else
            SimpleUtils.fail("There is no location column in Team Tab for MS location group",true);

    }

}
