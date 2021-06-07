package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.core.ConsoleLocationSelectorPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.legion.utils.MyThreadLocal.*;


public class LocationsTest extends TestBase {

    private static HashMap<String, String> scheduleWorkRoles = JsonUtil.getPropertiesFromJsonFile("src/test/resources/WorkRoleOptions.json");
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

        MS("Parent Child"),
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
        try {
            this.createDriver((String)params[0],"83","Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
//          AdminPage adminPage = pageFactory.createConsoleAdminPage();
//          adminPage.goToAdminTab();
//          adminPage.rebuildSearchIndex();
//            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//            dashboardPage.navigateToDashboard();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to create location with mandatory fields")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateRegularLocationWithMandatoryFields(String browser, String username, String password, String location) throws Exception {
        try {
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
                SimpleUtils.pass("Create new location successfully: "+locationName);
            }else
                SimpleUtils.fail("Create new location failed or can't search created location",true);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = " create a Type Regular location with effective date as a past date")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateRegularLocationWithAllFieldsAndNavigate(String browser, String username, String password, String location) throws Exception {
        try {
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

               //search created location
            if (locationsPage.searchNewLocation(locationName)) {
                SimpleUtils.pass("Create new location successfully: "+locationName);
            }else
                SimpleUtils.fail("Create new location failed or can't search created location",true);
//            ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);
//            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
//            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
//            locationSelectorPage.changeDistrict(locationInfoDetails.get(0).get("locationDistrict"));
//            locationSelectorPage.changeLocation(locationInfoDetails.get(0).get("locationName"));
//            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
//            schedulePage.clickOnScheduleConsoleMenuItem();
//            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
//            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
//            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Forecast.getValue());
//            SimpleUtils.assertOnFail("Schedule page 'Forecast' sub tab not loaded Successfully!",
//                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Forecast.getValue()) , false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "create a Type MOCK location that based on a ENABLED status regular location ")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateMockLocationAndNavigate(String browser, String username, String password, String location) throws Exception {
       try{
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
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
            //search created location
            if (locationsPage.searchNewLocation(locationName+"-MOCK")) {
                SimpleUtils.pass("Create new mock location successfully");
            }else
                SimpleUtils.fail("Create new location failed or can't search created location",true);
//            ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);
//            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
//            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
//            locationSelectorPage.changeDistrict(locationInfoDetails.get(0).get("locationDistrict"));
//            locationSelectorPage.changeLocation(locationName+"-MOCK");
//           SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
//           schedulePage.clickOnScheduleConsoleMenuItem();
//           schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
//           SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
//           schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Forecast.getValue());
//           SimpleUtils.assertOnFail("Schedule page 'Forecast' sub tab not loaded Successfully!",
//                   schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Forecast.getValue()) , false);
       } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Create a Type NSO location with below conditions successfully")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateNSOLocationAndNavigate(String browser, String username, String password, String location) throws Exception {
        try{
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
            if (locationsPage.searchNewLocation(getLocationName())) {
                SimpleUtils.pass("Create new mock location successfully");
            }else
                SimpleUtils.fail("Create new location failed or can't search created location",true);
//            ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);
//            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
//            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
//            locationSelectorPage.changeDistrict(locationInfoDetails.get(0).get("locationDistrict"));
//            locationSelectorPage.changeLocation(locationInfoDetails.get(0).get("locationName"));

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify disable the Type Regular locations")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDisableEnableLocationFunction(String browser, String username, String password, String location) throws Exception {
        try{
            String searchInputText="status:Enabled";
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

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Import locations common function")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyImportLocationDistrict(String browser, String username, String password, String location) throws Exception {
        try{
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
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }



    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Export all/specific location function")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyExportLocationDistrict(String browser, String username, String password, String location) throws Exception {
        try{
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
            //go to sub-locations
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.verifyExportAllLocationDistrict();
            locationsPage.verifyExportSpecificLocationDistrict(searchCharactor,index);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify MS location group function for Regular")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyMSLocationGroupFunctionForRegular(String browser, String username, String password, String location) throws Exception {

        try{
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
            String currentTime =  dfs.format(new Date());
            String locationName = "LGMSAuto" +currentTime;
            setLGMSLocationName(locationName);

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
            String locationType = "Regular";
            locationsPage.verifyTheFiledOfLocationSetting();
            locationsPage.addParentLocation(locationType, locationName,searchCharactor, index,parentRelationship,locationGroupSwitchOperation.MS.getValue());

            //add child location by child number
            try {
                for (int i = 0; i <childLocationNum ; i++) {
                    String childLocationName = "childLocationForMS" + i +currentTime;
                    setLGMSLocationName(childLocationName);
                    String  childRelationship = "Part of a location group";
                    locationsPage.addChildLocation(locationType, childLocationName,locationName,searchCharactor,index,childRelationship);
                }
            }catch (Exception e){
                SimpleUtils.fail("Child location creation failed",true);
            }


            //get location's  info
            ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);

            //Validate to update MS location Group district
            String originalDistrict = locationInfoDetails.get(0).get("locationDistrict");
            locationsPage.updateParentLocationDistrict("QA",index);
            ArrayList<HashMap<String, String>> locationInfoDetailsAfterUpdate =locationsPage.getLocationInfo(locationName);
            String districtAfterUpdate = locationInfoDetailsAfterUpdate.get(0).get("locationDistrict");
            if (!districtAfterUpdate.equals(originalDistrict)) {
                SimpleUtils.pass("District updated successfully");
            }else
                SimpleUtils.fail("Update failed",true);

            //get location's  info
            ArrayList<HashMap<String, String>> locationInfoDetailsSec =locationsPage.getLocationInfo(locationName);

            //disable each location
            String action="Disable";
            locationsPage.disableEnableLocation(locationInfoDetailsSec.get(0).get("locationName"),action);
            ArrayList<HashMap<String, String>> locationInfoDetailsAfterDisable =locationsPage.getLocationInfo(locationName);
            for (int i = 1; i <locationInfoDetailsAfterDisable.size() ; i++) {
                if (locationInfoDetailsAfterDisable.get(i).get("locationStatus").equals(locationInfoDetails.get(i).get("locationStatus"))) {
                    SimpleUtils.pass("There is no impact for child location status after changed parent location ");
                }else
                    SimpleUtils.fail("Child location status is changed after changed parent location, it's not expect behavior ",true);
            }

            //disable child location
            locationsPage.disableEnableLocation(locationInfoDetailsSec.get(1).get("locationName"),action);

            //revert status to enable

            String actionEnable="Enable";
            for (int i = 0; i <locationInfoDetailsSec.size() ; i++) {
                locationsPage.disableEnableLocation(locationInfoDetailsSec.get(i).get("locationName"),actionEnable);
            }

            //verify to change MS child location to None
            String locationToNone = locationInfoDetails.get(1).get("locationName");
            locationsPage.changeOneLocationToNone(locationToNone);
            //search this location group again
            ArrayList<HashMap<String, String>> locationInfoDetailsAftToNone =locationsPage.getLocationInfo(locationName);

            if (locationInfoDetailsAftToNone.size() < locationInfoDetailsSec.size()) {
                SimpleUtils.pass("Child location:"+locationInfoDetailsAftToNone.get(locationInfoDetailsAftToNone.size()-1).get("locationName") +" was removed from this location group:");
            }else
                SimpleUtils.fail("Update child location to None failed",true);

//
        } catch (Exception e){ //check location group navigation
//            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
//            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
//            locationSelectorPage.changeDistrict(locationInfoDetailsAftToNone.get(0).get("locationDistrict"));
//            locationSelectorPage.changeLocation(locationInfoDetailsAftToNone.get(0).get("locationName"));
//            //Go to Team tab to check location column for MS location group
//            TeamPage teamPage = pageFactory.createConsoleTeamPage();
//            teamPage.goToTeam();
//            if (teamPage.verifyThereIsLocationColumnForMSLocationGroup()) {
//                SimpleUtils.pass("Location column in Team Tab is showing for MS location group");
//            }else
//                SimpleUtils.fail("There is no location column in Team Tab for MS location group",true);
            SimpleUtils.fail(e.getMessage(), false);
        }
    }



    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify change MS location group to P2P")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyChangeMSToP2P(String browser, String username, String password, String location) throws Exception {
            try{

                SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
                String currentTime =  dfs.format(new Date());
                String locationName ="LGMSAuto" +currentTime;
                setLGMSLocationName(locationName);
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
                String locationType = "Regular";
                locationsPage.addParentLocation(locationType, locationName,searchCharactor, index,parentRelationship,locationGroupSwitchOperation.MS.getValue());

                //add child location by child number
                try {
                    for (int i = 0; i <childLocationNum ; i++) {
                        String childLocationName = "childLocationForMS" + i +currentTime;
                        setLGMSLocationName(childLocationName);
                        String  childRelationship = "Part of a location group";
                        locationsPage.addChildLocation(locationType, childLocationName,locationName,searchCharactor,index,childRelationship);
                    }
                }catch (Exception e){
                    SimpleUtils.fail("Child location creation failed",true);
                }

                ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);
                //Verify the location relationship
                if (locationsPage.isItMSLG()) {
                    locationsPage.changeLGToMSOrP2P(locationGroupSwitchOperation.PTP.getValue(),locationInfoDetails.get(0).get("locationName"));
                }else
                    SimpleUtils.fail("It's not MS location group,select another one pls",false);
                //search location again
                locationsPage.searchLocation("Change "+locationName+" to P2P or MS");
                if (!locationsPage.isItMSLG()) {
                    SimpleUtils.pass("Change MS location group to P2P successfully");
                    setLGPTPLocationName(locationName);
                }else
                    SimpleUtils.fail("Change MS location group to P2P failed",true);

            } catch (Exception e){
                SimpleUtils.fail(e.getMessage(), false);
            }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify Peer to Peer location group function for Regular")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyP2PLocationGroupFunctionForRegular(String browser, String username, String password, String location) throws Exception {

        try{
                SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
                String currentTime =  dfs.format(new Date());
                String locationName = "LGPTPAuto" +currentTime;
                setLGPTPLocationName(locationName);
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
                String locationType = "Regular";
                locationsPage.addParentLocation(locationType, locationName,searchCharactor, index,parentRelationship,locationGroupSwitchOperation.PTP.getValue());
                try {
                    for (int i = 0; i <childLocationNum ; i++) {
                        String childLocationName = "childLocationForPTP" + i +currentTime;
                        String  childRelationship = "Part of a location group";
                        locationsPage.addChildLocation(locationType, childLocationName,locationName,searchCharactor,index,childRelationship);
                    }
                }catch (Exception e){
                    SimpleUtils.fail("Child location creation failed",true);
                }


                //get location's  info
                ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);

                //update p2p location Group district
                String originalDistrict = locationInfoDetails.get(0).get("locationDistrict");
                locationsPage.updateParentLocationDistrict("QA",index);
                ArrayList<HashMap<String, String>> locationInfoDetailsAfterUpdate =locationsPage.getLocationInfo(locationName);
                String districtAfterUpdate = locationInfoDetailsAfterUpdate.get(0).get("locationDistrict");
                if (!districtAfterUpdate.equals(originalDistrict)) {
                    SimpleUtils.pass("District updated successfully");
                }else
                    SimpleUtils.fail("Update failed",true);

            //disable parent location
            String action="Disable";
            locationsPage.disableEnableLocation(locationInfoDetails.get(0).get("locationName"),action);
            ArrayList<HashMap<String, String>> locationInfoDetailsAfterDisable =locationsPage.getLocationInfo(locationName);

            for (int i = 1; i <locationInfoDetailsAfterDisable.size() ; i++) {
                if (locationInfoDetailsAfterDisable.get(i).get("locationStatus").equals(locationInfoDetails.get(i).get("locationStatus"))) {
                    SimpleUtils.pass("There is no impact for child location status after changed parent location ");
                }else
                    SimpleUtils.fail("Child location status is changed after changed parent location, it's not expect behavior ",true);
            }

            //disable child location
            locationsPage.disableEnableLocation(locationInfoDetails.get(1).get("locationName"),action);

            //revert status to enable
            String actionEnable="Enable";
            locationsPage.disableEnableLocation(locationInfoDetails.get(0).get("locationName"),actionEnable);
            locationsPage.disableEnableLocation(locationInfoDetails.get(1).get("locationName"),actionEnable);

//                //check location group navigation
//                locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
//                LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
//                locationSelectorPage.changeDistrict(locationInfoDetailsAfterUpdate.get(0).get("locationDistrict"));
//                locationSelectorPage.changeLocation(locationInfoDetailsAfterUpdate.get(0).get("locationName"));
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate Master Slave Location group creation with regular type")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyUpdateP2PLocationGroupDistrictFunction(String browser, String username, String password, String location) throws Exception {

        try{
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


        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify MS location group function for NSO")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateMSLocationGroupWithNSOTypeFunction(String browser, String username, String password, String location) throws Exception {

        try{
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
            String currentTime =  dfs.format(new Date());
            String locationName = "LGMS_NSO_Auto" +currentTime;
            setLGMSNsoLocationName(locationName);
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
            String locationType = "NSO";
            locationsPage.addParentLocationForNsoType(locationType,locationName,searchCharactor, index,parentRelationship,locationGroupSwitchOperation.MS.getValue());

            try {
                for (int i = 0; i <childLocationNum ; i++) {
                    String childLocationName = "NSOMSChild" + i +currentTime;
                    String  childRelationship = "Part of a location group";
                    locationsPage.addChildLocationForNSO(locationType,childLocationName,locationName,searchCharactor,index,childRelationship);
                }
            }catch (Exception e){
                SimpleUtils.fail("Child location creation failed",true);
            }
//            //get location's  info
//            ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);
//            //check location group navigation
//            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
//            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
//            locationSelectorPage.changeDistrict(locationInfoDetails.get(0).get("locationDistrict"));
//            locationSelectorPage.changeLocation(locationInfoDetails.get(0).get("locationName"));
//
//            //Go to Team tab to check location column for MS location group
//            TeamPage teamPage = pageFactory.createConsoleTeamPage();
//            teamPage.goToTeam();
//            if (teamPage.verifyThereIsLocationColumnForMSLocationGroup()) {
//                SimpleUtils.pass("Location column in Team Tab is showing for MS location group");
//            }else
//                SimpleUtils.fail("There is no location column in Team Tab for MS location group",true);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify Peer to peer location group function for NSO")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateP2PLocationGroupWithNsoTypeFunction(String browser, String username, String password, String location) throws Exception {

        try{
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
            String currentTime =  dfs.format(new Date());
            String locationName = "LGPTP_NSO_Auto" +currentTime;
            setLGPTPNsoLocationName(locationName);
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
//            locationSelectorPage.changeDistrict(locationInfoDetails.get(0).get("locationDistrict"));

            for (int i = 0; i <childLocationNum+1 ; i++) {
                locationSelectorPage.changeUpperFieldsByMagnifyGlassIcon(locationInfoDetails.get(i).get("locationName"));
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }


    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to disable P2P child /parent location")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDisableEnableParentChildLocationInLGP2P(String browser, String username, String password, String location) throws Exception {

        try{
            String LGPTPLocationName =getLGPTPLocationName();

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
            ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(LGPTPLocationName);

            //disable parent location
            String action="Disable";
            locationsPage.disableEnableLocation(locationInfoDetails.get(0).get("locationName"),action);
            ArrayList<HashMap<String, String>> locationInfoDetailsAfterUpdate =locationsPage.getLocationInfo(LGPTPLocationName);

            for (int i = 1; i <locationInfoDetailsAfterUpdate.size() ; i++) {
                if (locationInfoDetailsAfterUpdate.get(i).get("locationStatus").equals(locationInfoDetails.get(i).get("locationStatus"))) {
                    SimpleUtils.pass("There is no impact for child location status after changed parent location ");
                }else
                    SimpleUtils.fail("Child location status is changed after changed parent location, it's not expect behavior ",true);
            }

            //disable child location
            locationsPage.disableEnableLocation(locationInfoDetails.get(1).get("locationName"),action);

            //revert status to enable
            String actionEnable="Enable";
            locationsPage.disableEnableLocation(locationInfoDetails.get(0).get("locationName"),actionEnable);
            locationsPage.disableEnableLocation(locationInfoDetails.get(1).get("locationName"),actionEnable);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to update MS location group to None")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyChangeMSLocationsToNoneFunction(String browser, String username, String password, String location) throws Exception {

        try{
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime =  dfs.format(new Date());
            String locationName = "LGMSAuto" +currentTime;
            setLGMSLocationName(locationName);

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
            String  parentRelationship = "Parent location";
            String locationType = "Regular";
            locationsPage.addParentLocation(locationType, locationName,searchCharactor, index,parentRelationship,locationGroupSwitchOperation.MS.getValue());

            //add child location by child number
            String childLocationName = "";
            try {
                for (int i = 0; i <childLocationNum ; i++) {
                    childLocationName = "childLocationForMS" + i +currentTime;
                    setLGMSChildLocationName(childLocationName);
                    String  childRelationship = "Part of a location group";
                    locationsPage.addChildLocation(locationType, childLocationName,locationName,searchCharactor,index,childRelationship);
                }
            }catch (Exception e){
                SimpleUtils.fail("Child location creation failed",true);
            }
            //get search result location info
            ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);
            //verify to change MS child location to None
            locationsPage.changeOneLocationToNone(childLocationName);
            //search this location group again
            ArrayList<HashMap<String, String>> locationInfoDetailsAftUpdate =locationsPage.getLocationInfo(locationName);

            if (locationInfoDetailsAftUpdate.size() < locationInfoDetails.size()) {
                SimpleUtils.pass("Child location:"+locationInfoDetails.get(locationInfoDetails.size()-1).get("locationName") +" was removed from this location group");
            }else
                SimpleUtils.fail("Update child location to None failed",true);
            //change MS parent location to None
            locationsPage.changeOneLocationToNone(locationName);


        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to update P2P location to None")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyChangeP2PLocationsToNoneFunction(String browser, String username, String password, String location) throws Exception {

        try{

            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
            String currentTime =  dfs.format(new Date());
            String locationName = "LGPTPAuto" +currentTime;
            setLGPTPLocationName(locationName);
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
            //add new p2p location group-parent and child
            String  parentRelationship = "Parent location";
            String locationType = "Regular";
            locationsPage.addParentLocation(locationType, locationName,searchCharactor, index,parentRelationship,locationGroupSwitchOperation.PTP.getValue());
            String childLocationName = "";
            try {
                for (int i = 0; i <childLocationNum ; i++) {
                    childLocationName = "childLocationForP2P" + i +currentTime;
                    setLGPTPChildLocationName(childLocationName);
                    String  childRelationship = "Part of a location group";
                    locationsPage.addChildLocation(locationType, childLocationName,locationName,searchCharactor,index,childRelationship);
                }
            }catch (Exception e){
                SimpleUtils.fail("Child location creation failed",true);
            }
            //search location
            locationsPage.searchLocation(locationName);
            //get search result location info
            ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);
            //verify to change P2P child location to None
            locationsPage.changeOneLocationToNone(childLocationName);
            //search this location group again
            ArrayList<HashMap<String, String>> locationInfoDetailsAftUpdate =locationsPage.getLocationInfo(locationName);

            if (locationInfoDetailsAftUpdate.size() < locationInfoDetails.size()) {
                SimpleUtils.pass("Child location:"+locationInfoDetails.get(locationInfoDetails.size()-1).get("locationName") +" was removed from this location group:"+LGPTPLocationName.get());
            }else
                SimpleUtils.fail("Child location was not removed from parent location",true);
            //change MS parent location to None
            locationsPage.changeOneLocationToNone(locationName);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }


    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify location group common function")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyNoLocationGroupSettingForMock(String browser, String username, String password, String location) throws Exception {

        try{
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
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to change None location to MS parent")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyChangeNoneLocationToMSParent(String browser, String username, String password, String location) throws Exception {

        try{
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
                SimpleUtils.pass("Create new location successfully"+locationName);
            }else
                SimpleUtils.fail("Create new location failed or can't search created location",true);
            //change None to MS parent
            String  locationRelationship = "Parent location";
            String locationGroupType=locationGroupSwitchOperation.MS.getValue();
            locationsPage.changeOneLocationToParent(locationName, locationRelationship,locationGroupType);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to change None location to P2P parent")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyChangeNoneLocationToP2PParent(String browser, String username, String password, String location) throws Exception {

        try{
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
            String currentTime =  dfs.format(new Date());
            String locationName = "NoneToPTPParent" +currentTime;
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
                SimpleUtils.pass("Create new location successfully: " + locationName);
            }else
                SimpleUtils.fail("Create new location failed or can't search created location",true);
            //change None to MS parent
            String  locationRelationship = "Parent location";
            String locationGroupType=locationGroupSwitchOperation.PTP.getValue();
            locationsPage.changeOneLocationToParent(locationName, locationRelationship,locationGroupType);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to change None location to P2P child")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyChangeNoneLocationToChild(String browser, String username, String password, String location) throws Exception {

        try{
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
            String currentTime =  dfs.format(new Date());
            String locationName = "NoneToPTPChild" +currentTime;
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
                SimpleUtils.pass("Create new location successfully: "+locationName);
            }else
                SimpleUtils.fail("Create new location failed or can't search created location",true);
//            //change None to child
            String  locationRelationship = "Part of a location group";
            String parentLocation = "LGPTP";
            locationsPage.changeOneLocationToChild(locationName,locationRelationship,parentLocation);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify the create new button on the district landing page.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAddNewDistricBtnAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            //go to locations tab
            locationsPage.clickOnLocationsTab();
            //check locations item
            locationsPage.validateItemsInLocations();
            //go to sub-district tab
            locationsPage.goToUpperFieldsPage();
            locationsPage.validateTheAddDistrictBtn();

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to change P2P location group to MS")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyChangeP2PToMS(String browser, String username, String password, String location) throws Exception {

        try{

            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
            String currentTime =  dfs.format(new Date());
            String locationName ="LGP2PAuto" +currentTime;

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
            String locationType = "Regular";
            locationsPage.verifyTheFiledOfLocationSetting();
            locationsPage.addParentLocation(locationType, locationName,searchCharactor, index,parentRelationship,locationGroupSwitchOperation.PTP.getValue());

            //add child location by child number
            String childLocationName = "";
            try {
                for (int i = 0; i <childLocationNum ; i++) {
                    childLocationName = "childLocationForP2P" + i +currentTime;
                    setLGPTPChildLocationName(childLocationName);
                    String  childRelationship = "Part of a location group";
                    locationsPage.addChildLocation(locationType, childLocationName,locationName,searchCharactor,index,childRelationship);
                }
            }catch (Exception e){
                SimpleUtils.fail("Child location creation failed",true);
            }

            ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);
            //Verify the location relationship
            if (!locationsPage.isItMSLG()) {
                locationsPage.changeLGToMSOrP2P(locationName, locationGroupSwitchOperation.MS.getValue());
            }else
                SimpleUtils.fail("It's not P2P location group,select another one pls",false);
            //search location again
            locationsPage.searchLocation("Change "+locationName+" to MS group");
            if (locationsPage.isItMSLG()) {
                SimpleUtils.pass("Change P2P location group to MS successfully");
//                setLGMSLocationName(locationName);
            }else
                SimpleUtils.fail("Change P2P location group to MS failed",true);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify UpperFields list page and search function")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyUpperFieldsListPageAndSearchFunction(String browser, String username, String password, String location) throws Exception {

        try{
            String[] searchInfo = {"BU1","Level: District","Level: Region","status:enabled","status: disabled"};
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            //go to locations tab
            locationsPage.clickOnLocationsTab();
            //check locations item
            locationsPage.validateItemsInLocations();
            //go to sub-district  tab
            locationsPage.goToUpperFieldsPage();
            if (locationsPage.verifyUpperFieldListShowWellOrNot()) {
                locationsPage.verifyBackBtnFunction();
                locationsPage.goToUpperFieldsPage();
                locationsPage.verifyPaginationFunctionInDistrict();
            }else
                SimpleUtils.fail("UpperFields list page loading failed",false);

            locationsPage.verifySearchUpperFieldsFunction(searchInfo);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }



    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify Add New Upperfields with different level")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAddUpperFieldsWithDiffLevel(String browser, String username, String password, String location) throws Exception {

        try{
                SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
                String currentTime =  dfs.format(new Date()).trim();
                String upperfieldsName = currentTime;
                String upperfieldsId = currentTime;
                String searchChara = "test";
                int index = 0;

                DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
                SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
                LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
                locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
                SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

                //go to locations tab
                locationsPage.clickOnLocationsTab();
                //check locations item
                locationsPage.validateItemsInLocations();

                //get organization hierarchy info
                locationsPage.goToGlobalConfigurationInLocations();
                ArrayList<HashMap<String ,String>> organizationHierarchyInfo = locationsPage.getOrganizationHierarchyInfo();
                locationsPage.goBackToLocationsTab();
                //go to sub-upperfield  tab
                locationsPage.goToUpperFieldsPage();
                locationsPage.verifyBackBtnInCreateNewUpperfieldPage();
                locationsPage.addNewUpperfieldsWithoutParentAndChild( upperfieldsName, upperfieldsId,searchChara,index,organizationHierarchyInfo);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify disable and enable upperfield")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDisableEnableUpperFieldFunction(String browser, String username, String password, String location) throws Exception {

       try{
            String disableAction = "Disable";
            String enableAction = "Enable";
           String searchChara = "status:Disabled";
           int index = 0;

            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            //go to locations tab
            locationsPage.clickOnLocationsTab();
            //check locations item
            locationsPage.validateItemsInLocations();
            //go to sub-district  tab
            locationsPage.goToUpperFieldsPage();
           String upperfieldsName = "";
           ArrayList<HashMap<String, String>> upperfieldInfo = locationsPage.getUpperfieldsInfo(searchChara);
           for (int i = 0; i <upperfieldInfo.size() ; i++) {
               if (upperfieldInfo.get(i).get("upperfieldStatus").equalsIgnoreCase("DISABLED") &&
                       upperfieldInfo.get(i).get("numOfLocations").equals("0")) {
                   upperfieldsName = upperfieldInfo.get(i).get("upperfieldName");
                   break;
               }
           }
            //disable and enable upperfield
            locationsPage.disableEnableUpperfield(upperfieldsName,enableAction);
            locationsPage.disableEnableUpperfield(upperfieldsName,disableAction);

       } catch (Exception e){
           SimpleUtils.fail(e.getMessage(), false);
       }

    }
    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify update upperfield")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyUpdateUpperFieldFunction(String browser, String username, String password, String location) throws Exception {

        try{

            String upperfieldsName = "Level:Region";
            String searchChara = "reg";
            int index = 1;

            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            //go to locations tab
            locationsPage.clickOnLocationsTab();
            //check locations item
            locationsPage.validateItemsInLocations();
            //go to sub-district  tab
            locationsPage.goToUpperFieldsPage();

            //update upperfield
            String updateUpperfield = locationsPage.updateUpperfield(upperfieldsName, upperfieldsName,  searchChara, index);

            ArrayList<HashMap<String, String>> upperfieldInfo = locationsPage.getUpperfieldsInfo(updateUpperfield);
            if (upperfieldInfo.get(0).get("upperfieldLevel").equalsIgnoreCase("District")) {
                SimpleUtils.pass("Upperfield update successfully");
            }else
                SimpleUtils.fail("Upperfield update failed",false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify cancel creating upperfield")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCancelCreatingUpperfieldFunction(String browser, String username, String password, String location) throws Exception {

        try{

            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime =  dfs.format(new Date()).trim();
            String upperfieldsName = currentTime;
            String upperfieldsId = currentTime;
            String level = "District";

            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            //go to locations tab
            locationsPage.clickOnLocationsTab();
            //check locations item
            locationsPage.validateItemsInLocations();
            //go to sub-district  tab
            locationsPage.goToUpperFieldsPage();

            //cancel creating upperfield
            locationsPage.cancelCreatingUpperfield(level,upperfieldsName,upperfieldsId);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify upperfield smartcard data")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyUpperFieldSmartCardData(String browser, String username, String password, String location) throws Exception {

        try{

            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            //go to locations tab
            locationsPage.clickOnLocationsTab();
            //check locations item
            locationsPage.validateItemsInLocations();
            //go to sub-district  tab
            locationsPage.goToUpperFieldsPage();

            //get upperfield smart card data

            HashMap<String, Integer> upperfieldSmartCardInfo = locationsPage.getUpperfieldsSmartCardInfo();
            locationsPage.searchUpperFields("Status:Enabled");
            int  searchResultNum = locationsPage.getSearchResultNum();
            if (searchResultNum==upperfieldSmartCardInfo.get("Enabled")) {
                SimpleUtils.pass("Enabled data in smart card is correct");
            }else
                SimpleUtils.fail("Enabled data in smart card not equals upperfield list data",false);
            locationsPage.searchUpperFields("Status:Disabled");
            int  searchResultNumforDisable = locationsPage.getSearchResultNum();
            if (searchResultNumforDisable==upperfieldSmartCardInfo.get("Disabled")) {
                SimpleUtils.pass("Disabled data in smart card is correct");
            }else
                SimpleUtils.fail("Disabled data in smart card not equals upperfield list data",false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }
    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "verify internal location picture")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyInternalLocationPicFunction(String browser, String username, String password, String location) throws Exception {

        try{
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
            String currentTime =  dfs.format(new Date()).trim();
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
            //go to enterprise profile to get enterprise logo and default pic
            HashMap<String, String> enterpriseInfo = locationsPage.getEnterpriseLogoAndDefaultLocationInfo();
            locationsPage.verifyBackBtnFunction();
            //go to sub-locations tab
            locationsPage.goToSubLocationsInLocationsPage();

            //add new regular location
            locationsPage.addNewRegularLocationWithMandatoryFields(locationName);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }


    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Global dynamic group in Locations tab  ")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyGlobalDynamicGroupFunctionInLocationsTab(String browser, String username, String password, String location) throws Exception {

        try{
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime =  dfs.format(new Date()).trim();
            String groupNameForWFS = "AutoWFS" +currentTime;
            String groupNameForCloIn = "AutoClockIn" +currentTime;
            String description = "AutoCreate" +currentTime;
            String criteria = "Location Name";
            String criteriaUpdate = "Country";
            String searchText = "AutoCreate";
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            //go to locations tab
            locationsPage.clickOnLocationsTab();
            //check dynamic group item
            locationsPage.iCanSeeDynamicGroupItemInLocationsTab();
            //go to dynamic group
            locationsPage.goToDynamicGroup();
            locationsPage.searchWFSDynamicGroup(searchText);
            //remove existing dynamic group
            locationsPage.iCanDeleteExistingWFSDG();
            //create new workforce sharing dynamic group
            String locationNum = locationsPage.addWorkforceSharingDGWithOneCriteria(groupNameForWFS,description,criteria);
            String locationNumAftUpdate = locationsPage.updateWFSDynamicGroup(groupNameForWFS,criteriaUpdate);
            if (!locationNumAftUpdate.equalsIgnoreCase(locationNum)) {
                SimpleUtils.pass("Update workforce sharing dynamic group successfully");
            }
            locationsPage.searchClockInDynamicGroup(searchText);
            locationsPage.iCanDeleteExistingClockInDG();
            String locationNumForClockIn = locationsPage.addClockInDGWithOneCriteria(groupNameForCloIn,description,criteria);
            String locationNumForClockInAftUpdate = locationsPage.updateClockInDynamicGroup(groupNameForCloIn,criteriaUpdate);
            if (!locationNumForClockInAftUpdate.equalsIgnoreCase(locationNumForClockIn)) {
                SimpleUtils.pass("Update clock in dynamic group successfully");
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }


    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Global dynamic group for Clock in")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyGlobalDynamicGroupInClockInFunction(String browser, String username, String password, String location) throws Exception {

        try{

            List<String> clockInGroup = new ArrayList<>();
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            //go to locations tab
            locationsPage.clickOnLocationsTab();
            //check dynamic group item
            locationsPage.iCanSeeDynamicGroupItemInLocationsTab();
            //go to dynamic group
            locationsPage.goToDynamicGroup();
            clockInGroup = locationsPage.getClockInGroupFromGlobalConfig();
            String templateType = "Time & Attendance";
            String mode = "edit";
            String templateName = "UsedByAuto_NoTouchNoDelete";

            //go to configuration page
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.verifyClockInDisplayAndSelect(clockInGroup);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify abnormal scenarios")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyGlobalDynamicGroupAbnormalScenarios(String browser, String username, String password, String location) throws Exception {

        try{

            List<String> wfsGroup = new ArrayList<>();
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            //go to locations tab
            locationsPage.clickOnLocationsTab();
            //check dynamic group item
            locationsPage.iCanSeeDynamicGroupItemInLocationsTab();
            //go to dynamic group
            locationsPage.goToDynamicGroup();
            wfsGroup = locationsPage.getWFSGroupFromGlobalConfig();
            //go to dynamic group
            locationsPage.verifyCreateExistingDGAndGroupNameIsNull(wfsGroup.get(0));


        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Global dynamic group for Workforce Sharing")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyGlobalDynamicGroupInWFS(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Schedule Collaboration";
            String mode = "edit";
            String templateName = "UsedByAuto_NoTouchNoDelete";
            String wfsMode = "Yes";
            String wfsName = "WFS";
            String locationName = "OMLocation16";
            String criteria = "Custom";

            List<String> wfsGroup = new ArrayList<>();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeUpperFieldsByMagnifyGlassIcon(locationName);
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            schedulePage.navigateToNextWeek();
            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                schedulePage.clickOnDayViewAddNewShiftButton();
                schedulePage.customizeNewShiftPage();
                if (getDriver().getCurrentUrl().contains(propertyMap.get("Op_Enterprise"))) {
                    schedulePage.selectWorkRole(scheduleWorkRoles.get("AMBASSADOR"));
                } else if (getDriver().getCurrentUrl().contains(propertyMap.get("Op_Enterprise"))) {
                    schedulePage.selectWorkRole(scheduleWorkRoles.get("MGR ON DUTY"));
                }
                schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
                schedulePage.clickOnCreateOrNextBtn();
                schedulePage.searchTeamMemberByName("aglae");
                if (!schedulePage.verifyWFSFunction()) {
                    //to check WFS group exist or not
                    LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
                    locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
                    SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

                    //go to locations tab
                    locationsPage.clickOnLocationsTab();
                    //check dynamic group item
                    locationsPage.iCanSeeDynamicGroupItemInLocationsTab();
                    //go to dynamic group
                    locationsPage.goToDynamicGroup();
                    wfsGroup = locationsPage.getWFSGroupFromGlobalConfig();
                    for (int i = 0; i <wfsGroup.size() ; i++) {
                        if (wfsGroup.get(i).contains(wfsName)) {
                            SimpleUtils.report("Workforce sharing group for automation existing");
                            break;
                        }else
                            locationsPage.addWorkforceSharingDGWithOneCriteria(wfsName,"Used by auto",criteria);
                    }

                    //to check wfs is on or off in schedule collaboration configuration page
                    ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
                    configurationPage.goToConfigurationPage();
                    configurationPage.clickOnConfigurationCrad(templateType);
                    configurationPage.clickOnSpecifyTemplateName(templateName,mode);
                    configurationPage.clickOnEditButtonOnTemplateDetailsPage();
                    configurationPage.setWFS(wfsMode);
                    configurationPage.selectWFSGroup(wfsName);
                    configurationPage.publishNowTheTemplate();

                    //go to schedule to generate schedule

                    locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
                    SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
                }else
                    SimpleUtils.pass("Workforce sharing function work well");

            } else {
                schedulePage.createScheduleForNonDGFlowNewUI();
                schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                schedulePage.clickOnDayViewAddNewShiftButton();
                schedulePage.customizeNewShiftPage();
                if (getDriver().getCurrentUrl().contains(propertyMap.get("Op_Enterprise"))) {
                    schedulePage.selectWorkRole(scheduleWorkRoles.get("AMBASSADOR"));
                } else if (getDriver().getCurrentUrl().contains(propertyMap.get("Op_Enterprise"))) {
                    schedulePage.selectWorkRole(scheduleWorkRoles.get("MGR ON DUTY"));
                }
                schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
                schedulePage.clickOnCreateOrNextBtn();
                schedulePage.searchTeamMemberByName("Aglae");
                if (!schedulePage.verifyWFSFunction()) {
                    SimpleUtils.fail("Workforce sharing function work failed",false);
                }else
                    SimpleUtils.pass("Workforce sharing function work well");
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Parent formula in Workforce Sharing")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyParentFormulaInWFS(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Schedule Collaboration";
            String mode = "edit";
            String templateName = "ParentFormular";
            String wfsMode = "Yes";
            String wfsName = "WFS";
            String locationName = "SeaTac AirportSEA";
            String districtName = "OMDistrict1";
            String criteria = "Custom";

            List<String> wfsGroup = new ArrayList<>();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeUpperFieldsByMagnifyGlassIcon(locationName);
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            schedulePage.navigateToNextWeek();
            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                schedulePage.clickOnDayViewAddNewShiftButton();
                schedulePage.customizeNewShiftPage();
                if (getDriver().getCurrentUrl().contains(propertyMap.get("Op_Enterprise"))) {
                    schedulePage.selectWorkRole(scheduleWorkRoles.get("AMBASSADOR"));
                } else if (getDriver().getCurrentUrl().contains(propertyMap.get("Op_Enterprise"))) {
                    schedulePage.selectWorkRole(scheduleWorkRoles.get("MGR ON DUTY"));
                }
                schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
                schedulePage.clickOnCreateOrNextBtn();
                schedulePage.searchTeamMemberByName("Alysha");
                if (!schedulePage.verifyWFSFunction()) {
                    //to check WFS group exist or not
                    LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
                    locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
                    SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

                    //go to locations tab
                    locationsPage.clickOnLocationsTab();
                    //check dynamic group item
                    locationsPage.iCanSeeDynamicGroupItemInLocationsTab();
                    //go to dynamic group
                    locationsPage.goToDynamicGroup();
                    wfsGroup = locationsPage.getWFSGroupFromGlobalConfig();
                    for (int i = 0; i <wfsGroup.size() ; i++) {
                        if (wfsGroup.get(i).contains(wfsName)) {
                            SimpleUtils.report("Workforce sharing group for automation existing");
                            break;
                        }else
                            locationsPage.addWorkforceSharingDGWithOneCriteria(wfsName,"Used by auto",criteria);
                    }

                    //to check wfs is on or off in schedule collaboration configuration page
                    ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
                    configurationPage.goToConfigurationPage();
                    configurationPage.clickOnConfigurationCrad(templateType);
                    configurationPage.clickOnSpecifyTemplateName(templateName,mode);
                    configurationPage.clickOnEditButtonOnTemplateDetailsPage();
                    configurationPage.setWFS(wfsMode);
                    configurationPage.selectWFSGroup(wfsName);
                    configurationPage.publishNowTheTemplate();

                    //go to schedule to generate schedule

                    locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
                    SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
                }else
                    SimpleUtils.pass("Workforce sharing function work well");

            } else {
                schedulePage.createScheduleForNonDGFlowNewUI();
                schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                schedulePage.clickOnDayViewAddNewShiftButton();
                schedulePage.customizeNewShiftPage();
                if (getDriver().getCurrentUrl().contains(propertyMap.get("Op_Enterprise"))) {
                    schedulePage.selectWorkRole(scheduleWorkRoles.get("AMBASSADOR"));
                } else if (getDriver().getCurrentUrl().contains(propertyMap.get("Op_Enterprise"))) {
                    schedulePage.selectWorkRole(scheduleWorkRoles.get("MGR ON DUTY"));
                }
                schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
                schedulePage.clickOnCreateOrNextBtn();
                schedulePage.searchTeamMemberByName("Alysha");
                if (!schedulePage.verifyWFSFunction()) {
                    SimpleUtils.fail("Workforce sharing function work failed",false);
                }else
                    SimpleUtils.pass("Workforce sharing function work well");
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify default value of Organization Hierarchy")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDefaultOrganizationHierarchyShow(String browser, String username, String password, String location) throws Exception {

        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            locationsPage.clickOnLocationsTab();
            locationsPage.goToGlobalConfigurationInLocations();
            locationsPage.verifyDefaultOrganizationHierarchy();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate add edit remove organization hierarchy")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAddEditRemoveOrganizationHierarchy(String browser, String username, String password, String location) throws Exception {

        try{
            List<String> hierarchyNames = new ArrayList<String>(){{
                add("AutoDistrcit");
                add("AutoRegion");
                add("AutoBU");
            }};
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            locationsPage.clickOnLocationsTab();
            locationsPage.goToGlobalConfigurationInLocations();
            locationsPage.addOrganizationHierarchy(hierarchyNames);
            locationsPage.deleteOrganizationHierarchy(hierarchyNames);
            locationsPage.updateOrganizationHierarchyDisplayName();
            locationsPage.updateEnableUpperfieldViewOfHierarchy();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "abnormal cases of hierarchy")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAbnormalCasesOfOrganizationHierarchy(String browser, String username, String password, String location) throws Exception {

        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            locationsPage.clickOnLocationsTab();
            locationsPage.goToGlobalConfigurationInLocations();
            locationsPage.abnormalCaseOfEmptyDisplayNameForHierarchy();
            locationsPage.abnormalCaseOfLongDisplayNameForHierarchy();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    //added by Estelle to verify overridden function in location level
    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify user can see template value via click template name in location level and compare")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyUserCanSeeEachTypeOfTemViaClickingTemName(String browser, String username, String password, String location) throws Exception {

        try{
            String locationName = "OMLocation16";
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.goToLocationDetailsPage(locationName);
            locationsPage.goToConfigurationTabInLocationLevel();
            List<HashMap<String,String>>  templateInfo = locationsPage.getLocationTemplateInfoInLocationLevel();
            locationsPage.canGoToAssignmentRoleViaTemNameInLocationLevel();
            List<HashMap<String,String>> workRolesListInAssignmentRules = locationsPage.getAssignmentRolesInLocationLevel();
            locationsPage.backToConfigurationTabInLocationLevel();
            locationsPage.canGoToOperationHoursViaTemNameInLocationLevel();
            String contextInOHTemplate = locationsPage.getOHTemplateValueInLocationLevel();
            locationsPage.backToConfigurationTabInLocationLevel();
            locationsPage.canGoToSchedulingRulesViaTemNameInLocationLevel();
            List<HashMap<String,String>> contextInScheRulesTemplate = locationsPage.getScheRulesTemplateValueInLocationLevel();
            locationsPage.backToConfigurationTabInLocationLevel();
            locationsPage.canGoToScheduleCollaborationViaTemNameInLocationLevel();
            String contextInScheCollTemplate = locationsPage.getScheCollTemplateValueInLocationLevel();
            locationsPage.backToConfigurationTabInLocationLevel();
            locationsPage.canGoToTAViaTemNameInLocationLevel();
            String contextInTATemplate = locationsPage.getTATemplateValueInLocationLevel();
            locationsPage.backToConfigurationTabInLocationLevel();
            locationsPage.canGoToSchedulingPoliciesViaTemNameInLocationLevel();
            String contextInSchedulingPoliciesTemplate = locationsPage.getSchedulingPoliciesTemplateValueInLocationLevel();
            locationsPage.backToConfigurationTabInLocationLevel();
            locationsPage.canGoToComplianceViaTemNameInLocationLevel();
            String contextInComplianceTemplate = locationsPage.getComplianceTemplateValueInLocationLevel();
            locationsPage.backToConfigurationTabInLocationLevel();
            locationsPage.canGoToLaborModelViaTemNameInLocationLevel();
            List<HashMap<String,String>> workRolesListInLaborModel = locationsPage.getLaborModelInLocationLevel();
            locationsPage.backToConfigurationTabInLocationLevel();

            //go to configuration tab to check each template value in template level
            UserManagementPage userManagementPage = pageFactory.createOpsPortalUserManagementPage();
            userManagementPage.clickOnUserManagementTab();
            userManagementPage.goToWorkRolesTile();
            List<HashMap<String,String>> workRolesListInGlobal = locationsPage.getAssignmentRolesInLocationLevel();
            //get template level info of Operation hours
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateInfo.get(1).get("Template Type"));
            configurationPage.clickOnSpecifyTemplateName(templateInfo.get(1).get("Template Name"),"view");
            String specificOHInTemplateLevel = locationsPage.getOHTemplateValueInLocationLevel();
            //get template level info of Scheduling rules
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateInfo.get(2).get("Template Type"));
            configurationPage.clickOnSpecifyTemplateName(templateInfo.get(2).get("Template Name"),"view");
            List<HashMap<String,String>> specificSchRolesInTemplateLevel = locationsPage.getScheRulesTemplateValueInLocationLevel();

            //get template level info of Scheduling collaboration
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateInfo.get(3).get("Template Type"));
            configurationPage.clickOnSpecifyTemplateName(templateInfo.get(3).get("Template Name"),"view");
            String specificSchCollInTemplateLevel = locationsPage.getScheCollTemplateValueInLocationLevel();

            //get template level info of TA
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad("Time & Attendance");
            configurationPage.clickOnSpecifyTemplateName(templateInfo.get(4).get("Template Name"),"view");
            String specificTAInTemplateLevel = locationsPage.getTATemplateValueInLocationLevel();

            //get template level info of Schedule policy
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateInfo.get(5).get("Template Type"));
            configurationPage.clickOnSpecifyTemplateName(templateInfo.get(5).get("Template Name"),"view");
            String specificSchPolicyInTemplateLevel = locationsPage.getSchedulingPoliciesTemplateValueInLocationLevel();

            //get template level info of Compliance
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateInfo.get(6).get("Template Type"));
            configurationPage.clickOnSpecifyTemplateName(templateInfo.get(6).get("Template Name"),"view");
            String specificComplianceInTemplateLevel = locationsPage.getTATemplateValueInLocationLevel();

            //go to labor model tab to get specific template value
            LaborModelPage laborModelPage = pageFactory.createOpsPortalLaborModelPage();
            laborModelPage.clickOnLaborModelTab();
            laborModelPage.goToLaborModelTile();
            laborModelPage.clickOnSpecifyTemplateName(templateInfo.get(7).get("Template Name"),"view");
            List<HashMap<String,String>> workRolesListInLaborModelTemplateLevel = laborModelPage.getLaborModelInTemplateLevel();

            //compare location level value with template level
            if (contextInOHTemplate.equalsIgnoreCase(specificOHInTemplateLevel)) {
                SimpleUtils.pass("Operation Hours template value in location level equals to template level");
            }else
                SimpleUtils.fail("Operation Hours template value in location level doesn't equals to template level",false);

            if (contextInSchedulingPoliciesTemplate.contains(specificSchPolicyInTemplateLevel)) {
                SimpleUtils.pass("Schedule Policy value in location level equals to template level");
            }else
                SimpleUtils.fail("Schedule Policy value in location level doesn't equals to template level",false);


            if (contextInScheCollTemplate.contains(specificSchCollInTemplateLevel)) {
                SimpleUtils.pass("Schedule Collaboration template value in location level equals to template level");
            }else
                SimpleUtils.fail("Schedule Collaboration template value in location level doesn't equals to template level",false);

            if (contextInTATemplate.contains(specificTAInTemplateLevel)) {
                SimpleUtils.pass("Time Attendance value in location level equals to template level");
            }else
                SimpleUtils.fail("Time Attendance value in location level doesn't equals to template level",false);


            if (contextInComplianceTemplate.contains(specificComplianceInTemplateLevel)) {
                SimpleUtils.pass("Compliance template value in location level equals to template level");
            }else
                SimpleUtils.fail("Compliance template value in location level doesn't equals to template level",false);


//            String[] contextInScheRulesTemplateAft = contextInScheRulesTemplate.toArray(new String[]{});
//            String[] specificSchRolesInTemplateLevelAft = specificSchRolesInTemplateLevel.toArray(new String[]{});
//            if (contextInScheRulesTemplateAft.equals(specificSchRolesInTemplateLevelAft)) {
//                SimpleUtils.pass("Scheduling rules in location level equals to template level");
//            }else
//                SimpleUtils.fail("Scheduling rules in location level doesn't equals to template level",false);
//
//            //compare assignment rules in location level with work roles list in user management
//            String[] workRolesListInGlobalAft = workRolesListInGlobal.toArray(new String[]{});
//            String[] workRolesListInAssignmentRulesAft = workRolesListInAssignmentRules.toArray(new String[]{});
//            if (workRolesListInGlobalAft.equals(workRolesListInAssignmentRulesAft)) {
//                SimpleUtils.pass("Assignment Rules in location level equals to template level");
//            }else
//                SimpleUtils.fail("Assignment Rules in location level doesn't equals to template level",false);
//
//            String[] workRolesListInLaborModelTemplateLevelAft = workRolesListInLaborModelTemplateLevel.toArray(new String[]{});
//            String[] workRolesListInLaborModelAft = workRolesListInLaborModel.toArray(new String[]{});
//            if (workRolesListInLaborModelAft.equals(workRolesListInLaborModelTemplateLevelAft)) {
//                SimpleUtils.pass("Labor model in location level equals to template level");
//            }else
//                SimpleUtils.fail("Labor model in location level doesn't equals to template level",false);


        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "View template of Scheduling policy schedule collaboration TA and Compliance")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyViewFunctionOfSchedulingPolicyScheduleCollaborationTAComplianceInLocationLevel(String browser, String username, String password, String location) throws Exception {

        try{

            String locationName = "OMLocation16";
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.goToLocationDetailsPage(locationName);
            locationsPage.goToConfigurationTabInLocationLevel();
            List<HashMap<String,String>>  templateInfo = locationsPage.getLocationTemplateInfoInLocationLevel();
            //get template level info of Scheduling collaboration
            locationsPage.actionsForEachTypeOfTemplate(templateInfo.get(3).get("Template Type"),"View");
            String specificSchCollInTemplateLevel = locationsPage.getScheCollTemplateValueInLocationLevel();
            if (!(specificSchCollInTemplateLevel == null)) {
                SimpleUtils.pass("Can view Scheduling collaboration successfully via view button in location level");
            }else
                SimpleUtils.fail("View Scheduling collaboration via view button in location level failed",false);

            locationsPage.backToConfigurationTabInLocationLevel();


            //get template level info of TA
            locationsPage.actionsForEachTypeOfTemplate(templateInfo.get(4).get("Template Type"),"View");
            String contextInTATemplate = locationsPage.getTATemplateValueInLocationLevel();
            if (!(contextInTATemplate == null)) {
                SimpleUtils.pass("Can view Time Attendance successfully via view button in location level");
            }else
                SimpleUtils.fail("View Time Attendance via view button in location level failed",false);
            locationsPage.backToConfigurationTabInLocationLevel();

            //get template level info of Schedule policy
            locationsPage.actionsForEachTypeOfTemplate(templateInfo.get(5).get("Template Type"),"View");
            String contextInSchedulingPoliciesTemplate = locationsPage.getSchedulingPoliciesTemplateValueInLocationLevel();
            if (!(contextInSchedulingPoliciesTemplate == null)) {
                SimpleUtils.pass("Can view Scheduling Policies successfully via view button in location level");
            }else
                SimpleUtils.fail("View Scheduling Policies via view button in location level failed",false);
            locationsPage.backToConfigurationTabInLocationLevel();

            //get template level info of Compliance
            locationsPage.actionsForEachTypeOfTemplate(templateInfo.get(6).get("Template Type"),"View");
            String contextInComplianceTemplate = locationsPage.getComplianceTemplateValueInLocationLevel();
            if (!(contextInComplianceTemplate == null)) {
                SimpleUtils.pass("Can view Compliance successfully via view button in location level");
            }else
                SimpleUtils.fail("View Compliance via view button in location level failed",false);
            locationsPage.backToConfigurationTabInLocationLevel();

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Overridden scheduling rules template in location level")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyOverriddenSchedulingRulesInLocationLevel(String browser, String username, String password, String location) throws Exception {

        try{

            String locationName = "OMLocation16";
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.goToLocationDetailsPage(locationName);
            locationsPage.goToConfigurationTabInLocationLevel();
            List<HashMap<String,String>>  templateInfo = locationsPage.getLocationTemplateInfoInLocationLevel();
            locationsPage.actionsForEachTypeOfTemplate(templateInfo.get(2).get("Template Type"),"View");
//            List<HashMap<String,String>> initial = locationsPage.getScheRulesTemplateValueInLocationLevel();
            locationsPage.backToConfigurationTabInLocationLevel();
            locationsPage.editLocationBtnIsClickableInLocationDetails();
            locationsPage.actionsForEachTypeOfTemplate(templateInfo.get(2).get("Template Type"),"Edit");

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToWorkRolesWithStaffingRules();
            configurationPage.deleteBasicStaffingRule();
            configurationPage.saveBtnIsClickable();
            configurationPage.saveBtnIsClickable();
            List<HashMap<String,String>>  templateInfoAftOverridden = locationsPage.getLocationTemplateInfoInLocationLevel();
            if (templateInfoAftOverridden.get(2).get("Overridden").equalsIgnoreCase("Yes")) {
//                locationsPage.viewBtnForSchedulingRulesBtnIsClickable();
//                List<HashMap<String,String>> schedulingRulesDetailsAftOverridden = locationsPage.getScheRulesTemplateValueInLocationLevel();
//                if (!schedulingRulesDetailsAftOverridden.equals(initial)) {
                SimpleUtils.pass("Overridden scheduling rules successfully");
//                }
            }else
                SimpleUtils.fail("Overridden scheduling rules failed",false);

            //reset
            locationsPage.editLocationBtnIsClickableInLocationDetails();
            locationsPage.actionsForEachTypeOfTemplate(templateInfo.get(2).get("Template Type"),"Reset");
            List<HashMap<String,String>>  templateInfoAftReset = locationsPage.getLocationTemplateInfoInLocationLevel();
            if (templateInfoAftReset.get(2).get("Overridden").equalsIgnoreCase("No")) {
                SimpleUtils.pass("Reset scheduling rules successfully");
            } else
                SimpleUtils.fail("Reset scheduling rules failed",false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
