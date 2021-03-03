package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.tests.testframework.ExtentTestManager;
import com.legion.utils.SimpleUtils;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
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
            ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict(locationInfoDetails.get(0).get("locationDistrict"));
            locationSelectorPage.changeLocation(locationInfoDetails.get(0).get("locationName"));
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Forecast.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Forecast' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Forecast.getValue()) , false);
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
//            //search created location
//            if (locationsPage.searchNewLocation(getLocationName())) {
//                SimpleUtils.pass("Create new mock location successfully");
//            }else
//                SimpleUtils.fail("Create new location failed or can't search created location",true);
            ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict(locationInfoDetails.get(0).get("locationDistrict"));
            locationSelectorPage.changeLocation(locationName+"-MOCK");
           SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
           schedulePage.clickOnScheduleConsoleMenuItem();
           schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
           SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
           schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Forecast.getValue());
           SimpleUtils.assertOnFail("Schedule page 'Forecast' sub tab not loaded Successfully!",
                   schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Forecast.getValue()) , false);
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
//            if (locationsPage.searchNewLocation(getLocationName())) {
//                SimpleUtils.pass("Create new mock location successfully");
//            }else
//                SimpleUtils.fail("Create new location failed or can't search created location",true);
            ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict(locationInfoDetails.get(0).get("locationDistrict"));
            locationSelectorPage.changeLocation(locationInfoDetails.get(0).get("locationName"));

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

                ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);
                //Verify the location relationship
                if (locationsPage.isItMSLG()) {
                    locationsPage.changeLGToMSOrP2P(locationGroupSwitchOperation.PTP.getValue());
                }else
                    SimpleUtils.fail("It's not MS location group,select another one pls",false);
                //search location again
                locationsPage.searchLocation(locationName);
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
                    //"LGPTP_NSO_Auto20201020152818";
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
            locationSelectorPage.changeDistrict(locationInfoDetails.get(0).get("locationDistrict"));

            for (int i = 0; i <childLocationNum+1 ; i++) {
                locationSelectorPage.changeLocation(locationInfoDetails.get(i).get("locationName"));
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
            //add new MS location group-parent and child
            String  parentRelationship = "Parent location";
            String locationType = "Regular";
            locationsPage.addParentLocation(locationType, locationName,searchCharactor, index,parentRelationship,locationGroupSwitchOperation.PTP.getValue());
            String childLocationName = "";
            try {
                for (int i = 0; i <childLocationNum ; i++) {
                    childLocationName = "childLocationForMS" + i +currentTime;
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
            //verify to change MS child location to None
            locationsPage.changeOneLocationToNone(childLocationName);
            //search this location group again
            ArrayList<HashMap<String, String>> locationInfoDetailsAftUpdate =locationsPage.getLocationInfo(locationName);

            if (locationInfoDetailsAftUpdate.size() < locationInfoDetails.size()) {
                SimpleUtils.pass("Child location:"+locationInfoDetails.get(locationInfoDetails.size()-1).get("locationName") +" was removed from this location group:"+LGMSLocationName);
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
            locationsPage.goToSubDistrictsInLocationsPage();
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
            String locationName = "LGP2PAuto" +currentTime;
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
                locationsPage.changeLGToMSOrP2P(locationGroupSwitchOperation.MS.getValue());
            }else
                SimpleUtils.fail("It's not P2P location group,select another one pls",false);
            //search location again
            locationsPage.searchLocation(locationName);
            if (locationsPage.isItMSLG()) {
                SimpleUtils.pass("Change P2P location group to MS successfully");
                setLGMSLocationName(locationName);
            }else
                SimpleUtils.fail("Change P2P location group to MS failed",true);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify district list function")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDistrictListPageLoading(String browser, String username, String password, String location) throws Exception {

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
            locationsPage.goToSubDistrictsInLocationsPage();
            if (locationsPage.verifyDistrictListShowWellOrNot()) {
                locationsPage.verifyBackBtnFunction();
                locationsPage.goToSubDistrictsInLocationsPage();
                locationsPage.verifyPaginationFunctionInDistrict();

            }else
                SimpleUtils.fail("District list page loading failed",false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }


    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify search district function")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifySearchDistrictFunction(String browser, String username, String password, String location) throws Exception {

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
            locationsPage.goToSubDistrictsInLocationsPage();

            String[] searchInfo = {"No Touch","status:enabled"};
            locationsPage.verifySearchFunction(searchInfo);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify Add New District or update")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAddOrUpdateDistrictFunction(String browser, String username, String password, String location) throws Exception {

        try{
                SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
                String currentTime =  dfs.format(new Date()).trim();
                String districtName = currentTime;
                String districtId = currentTime;
                String searchChara = "Auto";
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
                locationsPage.goToSubDistrictsInLocationsPage();
                locationsPage.addNewDistrictWithoutLocation( districtName, districtId);
                locationsPage.searchDistrict(districtName);
                locationsPage.updateDistrict(districtName,districtId,searchChara,index);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify disable and enable district")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDisableEnableDistrictFunction(String browser, String username, String password, String location) throws Exception {

       try{
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
            String currentTime =  dfs.format(new Date()).trim();
            String districtName = currentTime;
            String districtId = currentTime;
            String districtManager = "Estelle Yan";
            String disableAction = "Disable";
            String enableAction = "Enable";


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
            locationsPage.goToSubDistrictsInLocationsPage();
            locationsPage.addNewDistrictWithoutLocation( districtName, districtId);

            //disable and enable district
            locationsPage.disableEnableDistrict(districtName,disableAction);
            locationsPage.disableEnableDistrict(districtName,enableAction);

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
    public void verifyGlobalDynamicGroupFunction(String browser, String username, String password, String location) throws Exception {
//
//        try{
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime =  dfs.format(new Date()).trim();
            String groupName = "AutoCreate" +currentTime;
            String description = "AutoCreate" +currentTime;
            String criteria = "Location Name";
            String criteriaUpdate = "Country";
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
            //remove existing dynamic group
//            locationsPage.iCanDeleteExistingDG();
            //create new dynamic group
            String locationNum = locationsPage.addWorkforceSharingDGWithOneCriteria(groupName,description,criteria);
            String locationNumAftUpdate = locationsPage.updateDynamicGroup(groupName,criteriaUpdate);
            if (!locationNumAftUpdate.equalsIgnoreCase(locationNum)) {
                SimpleUtils.pass("Update dynamic group successfully");
            }
//        } catch (Exception e){
//            SimpleUtils.fail(e.getMessage(), false);
//        }

    }
}
