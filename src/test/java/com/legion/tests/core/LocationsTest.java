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
        ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict(locationInfoDetails.get(0).get("locationDistrict"));
        locationSelectorPage.changeLocation(locationInfoDetails.get(0).get("locationName"));

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
        ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict(locationInfoDetails.get(0).get("locationDistrict"));
        locationSelectorPage.changeLocation(locationInfoDetails.get(0).get("locationName"));
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
        ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict(locationInfoDetails.get(0).get("locationDistrict"));
        locationSelectorPage.changeLocation(locationInfoDetails.get(0).get("locationName"));
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
    @TestName(description = "Validate Master Slave Location group creation with regular type")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateMSLocationGroupWithRegularTypeFunction(String browser, String username, String password, String location) throws Exception {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
        String currentTime =  dfs.format(new Date());
        String locationName = "LGMSAuto" +currentTime;
        setLGMSLocationName(locationName);
        int index =0;
        int childLocationNum = 2;
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
        String locationType = "Regular";
        locationsPage.addParentLocation(locationType, locationName,searchCharactor, index,parentRelationship,locationGroupSwitchOperation.MS.getValue());
//        String childLocationName = "childLocationForMS" +currentTime;
//        String  childRelationship = "Part of a location group";
//        locationsPage.addChildLocation(locationType, childLocationName,locationName,searchCharactor,index,childRelationship);
        //add child location by child number
        try {
            for (int i = 0; i <childLocationNum ; i++) {
                String childLocationName = "childLocationForMS" + i +currentTime;
                String  childRelationship = "Part of a location group";
                locationsPage.addChildLocation(locationType, childLocationName,locationName,searchCharactor,index,childRelationship);
            }
        }catch (Exception e){
            SimpleUtils.fail("Child location creation failed",true);
        }

        //get location's  info
        ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);
        //check location group navigation
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict(locationInfoDetails.get(0).get("locationDistrict"));
        for (int i = 0; i <childLocationNum ; i++) {
            locationSelectorPage.changeLocation(locationInfoDetails.get(i).get("locationName"));
        }
        //Go to Team tab to check location column for MS location group
        locationSelectorPage.changeLocation(locationInfoDetails.get(0).get("locationName"));
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
    @TestName(description = "Validate Master Slave Location group creation with regular type")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDisableEnableParentLocationInLGMS(String browser, String username, String password, String location) throws Exception {
        String LGMSLocationName ="LGMS0912";
                //getLGMSLocationName();

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


        //get location's  info
        ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(LGMSLocationName);

        //disable each location
        String action="Disable";
        locationsPage.disableEnableLocation(locationInfoDetails.get(0).get("locationName"),action);
        ArrayList<HashMap<String, String>> locationInfoDetailsAfterUpdate =locationsPage.getLocationInfo(LGMSLocationName);
        for (int i = 1; i <locationInfoDetailsAfterUpdate.size() ; i++) {
            if (locationInfoDetailsAfterUpdate.get(i).get("locationStatus").equals(locationInfoDetails.get(i).get("locationStatus"))) {
                SimpleUtils.pass("There is no impact for child location status after changed parent location ");
            }else
                SimpleUtils.fail("Child location status is changed after changed parent location, it's not expect behavior ",true);
        }
        //check location group navigation
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict(locationInfoDetails.get(0).get("locationDistrict"));
        locationSelectorPage.changeLocation(locationInfoDetails.get(0).get("locationName"));


        //revert status to enable
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

        //go to locations tab
        locationsPage.clickOnLocationsTab();
        //check locations item
        locationsPage.validateItemsInLocations();
        //go to sub-locations tab
        locationsPage.goToSubLocationsInLocationsPage();
        //search this location
        locationsPage.searchLocation(locationInfoDetails.get(0).get("locationName"));
        String actionEnable="Enable";
        locationsPage.disableEnableLocation(locationInfoDetails.get(0).get("locationName"),actionEnable);

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate Master Slave Location group creation with regular type")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyUpdateMSLocationGroupDistrictFunction(String browser, String username, String password, String location) throws Exception {
        String LGMSLocationName =getLGMSLocationName();
        int index =0;
        String searchCharacter = "a";
        String searchDistrict = "No touch";
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
        //get location's  info
        ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(LGMSLocationName);
        String originalDistrict = locationInfoDetails.get(0).get("locationDistrict");
        locationsPage.updateParentLocationDistrict(searchCharacter,index);
        ArrayList<HashMap<String, String>> locationInfoDetailsAfterUpdate =locationsPage.getLocationInfo(LGMSLocationName);
        String districtAfterUpdate = locationInfoDetailsAfterUpdate.get(0).get("locationDistrict");
        if (!districtAfterUpdate.equals(originalDistrict)) {
            SimpleUtils.pass("District updated successfully");
        }else
            SimpleUtils.fail("Update failed",true);

        //check location group navigation
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict(districtAfterUpdate);
        //to check locations is showing under new district
        locationSelectorPage.changeLocation(locationInfoDetails.get(0).get("locationName"));


        //revert the update
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
        locationsPage.clickOnLocationsTab();
        locationsPage.validateItemsInLocations();
        locationsPage.goToSubLocationsInLocationsPage();
        locationsPage.searchLocation(LGMSLocationName);
        locationsPage.updateParentLocationDistrict(searchDistrict,index);
        ArrayList<HashMap<String, String>> locationInfoDetailsAfterRevert =locationsPage.getLocationInfo(LGMSLocationName);
        String districtAfterRevert= locationInfoDetailsAfterRevert.get(0).get("locationDistrict");
        if (districtAfterRevert.equals(originalDistrict)) {
            SimpleUtils.pass("Revert successfully");
        }else
            SimpleUtils.fail("Revert failed",true);
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate Peer to peer Location group creation  with regular type")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateP2PLocationGroupWithRegularTypeFunction(String browser, String username, String password, String location) throws Exception {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
        String currentTime =  dfs.format(new Date());
        String locationName = "LGPTPAuto" +currentTime;
        setLGPTPLocationName(locationName);
        int index =0;
        int childLocationNum = 2;
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
        String locationType = "Regular";
        locationsPage.addParentLocation(locationType, locationName,searchCharactor, index,parentRelationship,locationGroupSwitchOperation.PTP.getValue());
        try {
            for (int i = 0; i <childLocationNum ; i++) {
                String childLocationName = "childLocationForMS" + i +currentTime;
                String  childRelationship = "Part of a location group";
                locationsPage.addChildLocation(locationType, childLocationName,locationName,searchCharactor,index,childRelationship);
            }
        }catch (Exception e){
            SimpleUtils.fail("Child location creation failed",true);
        }


        //get location's  info
        ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);

        //check location group navigation
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict(locationInfoDetails.get(0).get("locationDistrict"));

        for (int i = 0; i <childLocationNum+1 ; i++) {
            locationSelectorPage.changeLocation(locationInfoDetails.get(i).get("locationName"));
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate Master Slave Location group creation with regular type")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyUpdateP2PLocationGroupDistrictFunction(String browser, String username, String password, String location) throws Exception {
        String LGMSLocationName =getLGMSLocationName();
        int index =0;
        String searchCharacter = "a";
        String searchDistrict = "No touch";
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
        //get location's  info
        ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(LGMSLocationName);
        String originalDistrict = locationInfoDetails.get(0).get("locationDistrict");
        locationsPage.updateParentLocationDistrict(searchCharacter,index);
        ArrayList<HashMap<String, String>> locationInfoDetailsAfterUpdate =locationsPage.getLocationInfo(LGMSLocationName);
        String districtAfterUpdate = locationInfoDetailsAfterUpdate.get(0).get("locationDistrict");
        if (!districtAfterUpdate.equals(originalDistrict)) {
            SimpleUtils.pass("District updated successfully");
        }else
            SimpleUtils.fail("Update failed",true);

        //check location group navigation
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict(districtAfterUpdate);
        //to check locations is showing under new district
        for (int i = 0; i <locationInfoDetails.size() ; i++) {
            locationSelectorPage.changeLocation(locationInfoDetails.get(i).get("locationName"));

        }
        //revert the update
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
        locationsPage.clickOnLocationsTab();
        locationsPage.validateItemsInLocations();
        locationsPage.goToSubLocationsInLocationsPage();
        locationsPage.searchLocation(LGMSLocationName);
        locationsPage.updateParentLocationDistrict(searchDistrict,index);
        ArrayList<HashMap<String, String>> locationInfoDetailsAfterRevert =locationsPage.getLocationInfo(LGMSLocationName);
        String districtAfterRevert= locationInfoDetailsAfterRevert.get(0).get("locationDistrict");
        if (districtAfterRevert.equals(originalDistrict)) {
            SimpleUtils.pass("Revert successfully");
        }else
            SimpleUtils.fail("Revert failed",true);
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate Master Slave Location group creation with SSO type")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateMSLocationGroupWithNSOTypeFunction(String browser, String username, String password, String location) throws Exception {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
        String currentTime =  dfs.format(new Date());
        String locationName = "LGMS_NSO_Auto" +currentTime;
                //"LGPTP_NSO_Auto20201020152818";
        setLGMSNsoLocationName(locationName);
        int index =0;
        int childLocationNum = 2;
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
        String locationType = "NSO";
        locationsPage.addParentLocationForNsoType(locationType,locationName,searchCharactor, index,parentRelationship,locationGroupSwitchOperation.MS.getValue());
        String childLocationName = "NSOChild" +currentTime;
        String  childRelationship = "Part of a location group";
        locationsPage.addChildLocationForNSO(locationType, childLocationName,locationName,searchCharactor,index,childRelationship);
        //get location's  info
        ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);
        //check location group navigation
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict(locationInfoDetails.get(0).get("locationDistrict"));
        for (int i = 0; i <childLocationNum ; i++) {
            locationSelectorPage.changeLocation(locationInfoDetails.get(i).get("locationName"));
        }
        locationSelectorPage.changeLocation(locationInfoDetails.get(0).get("locationName"));
        //Go to Team tab to check location column for MS location group
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
    @TestName(description = "Validate Peer to peer Location group creation  with regular type")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateP2PLocationGroupWithNsoTypeFunction(String browser, String username, String password, String location) throws Exception {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
        String currentTime =  dfs.format(new Date());
        String locationName = "LGPTP_NSO_Auto" +currentTime;
        setLGPTPNsoLocationName(locationName);
        int index =0;
        int childLocationNum = 2;
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
        String locationType = "NSO";
        locationsPage.addParentLocationForNsoType(locationType, locationName,searchCharactor, index,parentRelationship,locationGroupSwitchOperation.PTP.getValue());
        try {
            for (int i = 0; i <childLocationNum ; i++) {
                String childLocationName = "NSOMSChild" + i +currentTime;
                String  childRelationship = "Part of a location group";
                locationsPage.addChildLocationForNSO(locationType,childLocationName,locationName,searchCharactor,index,childRelationship);
            }
        }catch (Exception e){
            SimpleUtils.fail("Child location creation failed",true);
        }


        //get location's  info
        ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);

        //check location group navigation
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict(locationInfoDetails.get(0).get("locationDistrict"));
        locationSelectorPage.changeLocation(locationInfoDetails.get(0).get("locationName"));


    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate update one child location to None")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyChangeChildLocationToNoneFunction(String browser, String username, String password, String location) throws Exception {

        String LGMSLocationName =getLGMSLocationName();

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
        //search location
        locationsPage.searchLocation(LGMSLocationName);
        //get search result location info
        ArrayList<HashMap<String, String>> locationInfoDetailsBeforeUpdate =locationsPage.getLocationInfo(LGMSLocationName);
        //verify to change MS parent location to None
        String locationToNone = locationInfoDetailsBeforeUpdate.get(locationInfoDetailsBeforeUpdate.size()-1).get("locationName");
        locationsPage.changeOneLocationToNone(locationToNone);
        //search this location group again
        locationsPage.searchLocation(LGMSLocationName);
        ArrayList<HashMap<String, String>> locationInfoDetailsAftUpdate =locationsPage.getLocationInfo(LGMSLocationName);

        if (locationInfoDetailsAftUpdate.size() < locationInfoDetailsBeforeUpdate.size()) {
            SimpleUtils.pass("Child location:"+locationInfoDetailsBeforeUpdate.get(locationInfoDetailsBeforeUpdate.size()-1).get("locationName") +" was removed from this location group:"+LGMSLocationName);
        }else
            SimpleUtils.fail("Update child location to None failed",true);


    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to update MS parent location to None")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyChangeMSParentLocationToNoneFunction(String browser, String username, String password, String location) throws Exception {

        String LGMSLocationName = getLGMSLocationName();
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
        //search location
        locationsPage.searchLocation(LGMSLocationName);
        //get search result location info
        ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(LGMSLocationName);
        //verify to change MS parent location to None
        locationsPage.changeOneLocationToNone(LGMSLocationName);
        //navigate to parent location and child location
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict(locationInfoDetails.get(0).get("locationDistrict"));
        for (int i = 0; i <locationInfoDetails.size() ; i++) {
            locationSelectorPage.changeLocation(locationInfoDetails.get(i).get("locationName"));
        }

    }


    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to update P2P parent location to None")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyChangeP2PParentLocationToNoneFunction(String browser, String username, String password, String location) throws Exception {

        String LGPTPLocationName = getLGPTPLocationName();
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
        //search location
        locationsPage.searchLocation(LGPTPLocationName);
        //get search result location info
        ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(LGPTPLocationName);
        //verify to change MS parent location to None
        locationsPage.changeOneLocationToNone(LGPTPLocationName);
        //navigate to parent location and child location
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict(locationInfoDetails.get(0).get("locationDistrict"));
        for (int i = 0; i <locationInfoDetails.size() ; i++) {
            locationSelectorPage.changeLocation(locationInfoDetails.get(i).get("locationName"));
        }


    }


    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate There is no location group related field for MOCK location")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyNoLocationGroupSettingForMock(String browser, String username, String password, String location) throws Exception {

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
        //check location group setting filed when type is mock
        locationsPage.checkThereIsNoLocationGroupSettingFieldWhenLocationTypeIsMock();

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to change None location to MS parent")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyChangeNoneLocationToMSParent(String browser, String username, String password, String location) throws Exception {

        SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
        String currentTime =  dfs.format(new Date());
        String locationName = "NoneToMSParent" +currentTime;
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
        //search created location
        if (locationsPage.searchNewLocation(locationName)) {
            SimpleUtils.pass("Create new location successfully");
        }else
            SimpleUtils.fail("Create new location failed or can't search created location",true);
       //change None to MS parent
        String  locationRelationship = "Parent location";
        String locationGroupType=locationGroupSwitchOperation.MS.getValue();
        locationsPage.changeOneLocationToParent(locationName, locationRelationship,locationGroupType);
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to change None location to P2P parent")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyChangeNoneLocationToP2PParent(String browser, String username, String password, String location) throws Exception {

        SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
        String currentTime =  dfs.format(new Date());
        String locationName = "NoneToMSParent" +currentTime;
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
        //search created location
        if (locationsPage.searchNewLocation(locationName)) {
            SimpleUtils.pass("Create new location successfully");
        }else
            SimpleUtils.fail("Create new location failed or can't search created location",true);
        //change None to MS parent
        String  locationRelationship = "Parent location";
        String locationGroupType=locationGroupSwitchOperation.PTP.getValue();
        locationsPage.changeOneLocationToParent(locationName, locationRelationship,locationGroupType);
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to change None location to P2P parent")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyChangeNoneLocationToChild(String browser, String username, String password, String location) throws Exception {

        SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
        String currentTime =  dfs.format(new Date());
        String locationName = "NoneToMSParent" +currentTime;
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
        //search created location
        if (locationsPage.searchNewLocation(locationName)) {
            SimpleUtils.pass("Create new location successfully");
        }else
            SimpleUtils.fail("Create new location failed or can't search created location",true);
        //change None to child
        String  locationRelationship = "Part of a location group";
        String parentLocation = "LGMS";
        locationsPage.changeOneLocationToChild(locationName,locationRelationship,parentLocation);
    }
}
