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
    private static HashMap<String, String> propertyCustomizeMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ScheduleCustomizeNewShift.json");
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

    public enum ohSliderDroppable{
        StartPoint("Start"),
        EndPoint("End");
        private final String value;
        ohSliderDroppable(final String newValue) {
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

               //search created location blocked by https://legiontech.atlassian.net/browse/OPS-2757
//            if (locationsPage.searchNewLocation(locationName)) {
//                SimpleUtils.pass("Create new location successfully: "+locationName);
//            }else
//                SimpleUtils.fail("Create new location failed or can't search created location",true);
//            ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);
//            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
//            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
//            locationSelectorPage.changeDistrict(locationInfoDetails.get(0).get("locationDistrict"));
//            locationSelectorPage.changeLocation(locationInfoDetails.get(0).get("locationName"));
//
//            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
//            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
//            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
//            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Forecast.getValue());
//            SimpleUtils.assertOnFail("Schedule page 'Forecast' sub tab not loaded Successfully!",
//                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Forecast.getValue()) , false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
      //mock location creation is blocked by https://legiontech.atlassian.net/browse/OPS-2503
//    @Automated(automated = "Automated")
//    @Owner(owner = "Estelle")
//    @Enterprise(name = "Op_Enterprise")
//    @TestName(description = "create a Type MOCK location that based on a ENABLED status regular location ")
//    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
//    public void verifyCreateMockLocationAndNavigate(String browser, String username, String password, String location) throws Exception {
//       try{
//            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
//            String currentTime =  dfs.format(new Date());
//            String locationName = "AutoCreate" +currentTime;
//            int index =0;
//            String searchCharactor = "No touch";
//            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
//            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
//            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
//            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
//
//            //go to locations tab
//            locationsPage.clickOnLocationsTab();
//            //check locations item
//            locationsPage.validateItemsInLocations();
//            //go to sub-locations tab
//            locationsPage.goToSubLocationsInLocationsPage();
//            //add one mock location，first create one new location and then to mock that -to avoid duplication
//            locationsPage.addNewRegularLocationWithAllFields(locationName,searchCharactor, index);;
//            locationsPage.addNewMockLocationWithAllFields(locationName,locationName,index);
//            //search created location
//            if (locationsPage.searchNewLocation(locationName+"-MOCK")) {
//                SimpleUtils.pass("Create new mock location successfully");
//            }else
//                SimpleUtils.fail("Create new location failed or can't search created location",true);
////            ArrayList<HashMap<String, String>> locationInfoDetails =locationsPage.getLocationInfo(locationName);
////            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
////            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
////            locationSelectorPage.changeDistrict(locationInfoDetails.get(0).get("locationDistrict"));
////            locationSelectorPage.changeLocation(locationName+"-MOCK");
////
////           scheduleCommonPage.clickOnScheduleConsoleMenuItem();
////           scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
////           SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
////           scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Forecast.getValue());
////           SimpleUtils.assertOnFail("Schedule page 'Forecast' sub tab not loaded Successfully!",
////                   scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Forecast.getValue()) , false);
//       } catch (Exception e){
//            SimpleUtils.fail(e.getMessage(), false);
//        }
//    }

    // NSO location is blocked by https://legiontech.atlassian.net/browse/OPS-2757
//    @Automated(automated = "Automated")
//    @Owner(owner = "Estelle")
//    @Enterprise(name = "Op_Enterprise")
//    @TestName(description = "Create a Type NSO location with below conditions successfully")
//    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
//    public void verifyCreateNSOLocationAndNavigate(String browser, String username, String password, String location) throws Exception {
//        try{
//            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
//            String currentTime =  dfs.format(new Date());
//            String locationName = "AutoCreateNSO" +currentTime;
//            int index =0;
//            String searchCharactor = "No touch";
//
//            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
//            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
//            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
//            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
//
//            //go to locations tab
//            locationsPage.clickOnLocationsTab();
//            //check locations item
//            locationsPage.validateItemsInLocations();
//            //go to sub-locations tab
//            locationsPage.goToSubLocationsInLocationsPage();
//            locationsPage.addNewNSOLocation(locationName,searchCharactor, index);
//            if (locationsPage.searchNewLocation(getLocationName())) {
//                SimpleUtils.pass("Create new NSO location successfully");
//            }else
//                SimpleUtils.fail("Create new location failed or can't search created location",true);
//
//            //go to console to and vigate to NSO
//            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
//            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
//            locationSelectorPage.changeUpperFieldsByMagnifyGlassIcon(locationName);
//
//
//        } catch (Exception e){
//            SimpleUtils.fail(e.getMessage(), false);
//        }
//    }

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


//    @Automated(automated = "Automated")
//    @Owner(owner = "Estelle")
//    @Enterprise(name = "Op_Enterprise")
//    @TestName(description = "Import locations common function")
//    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
//    public void verifyImportLocationDistrict(String browser, String username, String password, String location) throws Exception {
//        try{
//            String searchInputText="Child_LocationGroup_Example";
//            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
//            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
//            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
//            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
//
//            //go to locations tab
//            locationsPage.clickOnLocationsTab();
//            //check locations item
//            locationsPage.validateItemsInLocations();
//            //go to sub-locations tab
//            locationsPage.goToSubLocationsInLocationsPage();
//            locationsPage.verifyImportLocationDistrict();
//            if (locationsPage.searchNewLocation(searchInputText)) {
//                SimpleUtils.pass("Create new mock location successfully");
//            }else
//                SimpleUtils.fail("Create new location failed or can't search created location",true);
//        } catch (Exception e){
//            SimpleUtils.fail(e.getMessage(), false);
//        }
//    }

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
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify the create new button on the district landing page.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAddNewDistricBtnAsAInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
    @TestName(description = "Verify UpperFields list page and search function")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyUpperFieldsListPageAndSearchFunctionAsInternalAdminForUpperFieldTile(String browser, String username, String password, String location) throws Exception {

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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAddUpperFieldsWithDiffLevelAsInternalAdminForUpperFieldTile(String browser, String username, String password, String location) throws Exception {

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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDisableEnableUpperFieldFunctionAsInternalAdminForUpperFieldTile(String browser, String username, String password, String location) throws Exception {

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
    //blocked by https://legiontech.atlassian.net/browse/OPS-2286
//    @Automated(automated = "Automated")
//    @Owner(owner = "Estelle")
//    @Enterprise(name = "Op_Enterprise")
//    @TestName(description = "Verify update upperfield")
//    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
//    public void verifyUpdateUpperFieldFunctionAsInternalAdminForUpperFieldTile(String browser, String username, String password, String location) throws Exception {
//
//        try{
//
//            String upperfieldsName = "Level:Region";
//            String searchChara = "re";
//            int index = 1;
//
//            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
//            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
//            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
//            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
//
//            //go to locations tab
//            locationsPage.clickOnLocationsTab();
//            //check locations item
//            locationsPage.validateItemsInLocations();
//            //go to sub-district  tab
//            locationsPage.goToUpperFieldsPage();
//
//            //update upperfield
//            String updateUpperfield = locationsPage.updateUpperfield(upperfieldsName, upperfieldsName,  searchChara, index);
//
//            ArrayList<HashMap<String, String>> upperfieldInfo = locationsPage.getUpperfieldsInfo(updateUpperfield);
//            if (upperfieldInfo.get(0).get("upperfieldLevel").equalsIgnoreCase("District")) {
//                SimpleUtils.pass("Upperfield update successfully");
//            }else
//                SimpleUtils.fail("Upperfield update failed",false);
//
//        } catch (Exception e){
//            SimpleUtils.fail(e.getMessage(), false);
//        }
//
//    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify cancel creating upperfield")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCancelCreatingUpperfieldFunctionAsInternalAdminForUpperFieldTile(String browser, String username, String password, String location) throws Exception {

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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyUpperFieldSmartCardDataAsInternalAdminForUpperFieldTile(String browser, String username, String password, String location) throws Exception {

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
//    @Automated(automated = "Automated")
//    @Owner(owner = "Estelle")
//    @Enterprise(name = "Op_Enterprise")
//    @TestName(description = "verify internal location picture")
//    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
//    public void verifyInternalLocationPicFunction(String browser, String username, String password, String location) throws Exception {
//
//        try{
//            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
//            String currentTime =  dfs.format(new Date()).trim();
//            String locationName = "AutoCreate" +currentTime;
//            int index =0;
//            String searchCharactor = "No touch";
//            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
//            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
//            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
//            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
//
//            //go to locations tab
//            locationsPage.clickOnLocationsTab();
//            //check locations item
//            locationsPage.validateItemsInLocations();
//            //go to enterprise profile to get enterprise logo and default pic
//            HashMap<String, String> enterpriseInfo = locationsPage.getEnterpriseLogoAndDefaultLocationInfo();
//            locationsPage.verifyBackBtnFunction();
//            //go to sub-locations tab
//            locationsPage.goToSubLocationsInLocationsPage();
//
//            //add new regular location
//            locationsPage.addNewRegularLocationWithMandatoryFields(locationName);
//
//        } catch (Exception e){
//            SimpleUtils.fail(e.getMessage(), false);
//        }
//
//    }


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
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            locationSelectorPage.changeUpperFieldsByMagnifyGlassIcon(locationName);
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                newShiftPage.clickOnDayViewAddNewShiftButton();
                newShiftPage.customizeNewShiftPage();
                if (getDriver().getCurrentUrl().contains(propertyMap.get("Op_Enterprise"))) {
                    newShiftPage.selectWorkRole(scheduleWorkRoles.get("AMBASSADOR"));
                } else if (getDriver().getCurrentUrl().contains(propertyMap.get("Op_Enterprise"))) {
                    newShiftPage.selectWorkRole(scheduleWorkRoles.get("MGR ON DUTY"));
                }
                newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
                newShiftPage.clickOnCreateOrNextBtn();
                newShiftPage.searchTeamMemberByName("aglae");
                if (!shiftOperatePage.verifyWFSFunction()) {
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
                createSchedulePage.createScheduleForNonDGFlowNewUI();
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                newShiftPage.clickOnDayViewAddNewShiftButton();
                newShiftPage.customizeNewShiftPage();
                if (getDriver().getCurrentUrl().contains(propertyMap.get("Op_Enterprise"))) {
                    newShiftPage.selectWorkRole(scheduleWorkRoles.get("AMBASSADOR"));
                } else if (getDriver().getCurrentUrl().contains(propertyMap.get("Op_Enterprise"))) {
                    newShiftPage.selectWorkRole(scheduleWorkRoles.get("MGR ON DUTY"));
                }
                newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
                newShiftPage.clickOnCreateOrNextBtn();
                newShiftPage.searchTeamMemberByName("Aglae");
                if (!shiftOperatePage.verifyWFSFunction()) {
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
            String criteria = "Custom";

            List<String> wfsGroup = new ArrayList<>();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            locationSelectorPage.changeUpperFieldsByMagnifyGlassIcon(locationName);
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                newShiftPage.clickOnDayViewAddNewShiftButton();
                newShiftPage.customizeNewShiftPage();
                if (getDriver().getCurrentUrl().contains(propertyMap.get("Op_Enterprise"))) {
                    newShiftPage.selectWorkRole(scheduleWorkRoles.get("AMBASSADOR"));
                } else if (getDriver().getCurrentUrl().contains(propertyMap.get("Op_Enterprise"))) {
                    newShiftPage.selectWorkRole(scheduleWorkRoles.get("MGR ON DUTY"));
                }
                newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
                newShiftPage.clickOnCreateOrNextBtn();
                newShiftPage.searchTeamMemberByName("Alysha");
                if (!shiftOperatePage.verifyWFSFunction()) {
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
                createSchedulePage.createScheduleForNonDGFlowNewUI();
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                newShiftPage.clickOnDayViewAddNewShiftButton();
                newShiftPage.customizeNewShiftPage();
                if (getDriver().getCurrentUrl().contains(propertyMap.get("Op_Enterprise"))) {
                    newShiftPage.selectWorkRole(scheduleWorkRoles.get("AMBASSADOR"));
                } else if (getDriver().getCurrentUrl().contains(propertyMap.get("Op_Enterprise"))) {
                    newShiftPage.selectWorkRole(scheduleWorkRoles.get("MGR ON DUTY"));
                }
                newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
                newShiftPage.clickOnCreateOrNextBtn();
                newShiftPage.searchTeamMemberByName("Alysha");
                if (!shiftOperatePage.verifyWFSFunction()) {
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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDefaultOrganizationHierarchyShowAsInternalAdminForUpperFieldTile(String browser, String username, String password, String location) throws Exception {

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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAddEditRemoveOrganizationHierarchyAsInternalAdminForUpperFieldTile(String browser, String username, String password, String location) throws Exception {

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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAbnormalCasesOfOrganizationHierarchyAsInternalAdminForUpperFieldTile(String browser, String username, String password, String location) throws Exception {

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

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Overridden Operating Hours template in location level")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyOverriddenOperatingHoursInLocationLevel(String browser, String username, String password, String location) throws Exception {

        try{

            String locationName = "OMLocation16";
            int moveCount = 4;
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.goToLocationDetailsPage(locationName);
            locationsPage.goToConfigurationTabInLocationLevel();
            List<HashMap<String,String>>  templateInfo = locationsPage.getLocationTemplateInfoInLocationLevel();
            locationsPage.actionsForEachTypeOfTemplate(templateInfo.get(1).get("Template Type"),"View");
            locationsPage.backToConfigurationTabInLocationLevel();
            locationsPage.editLocationBtnIsClickableInLocationDetails();
            locationsPage.actionsForEachTypeOfTemplate(templateInfo.get(1).get("Template Type"),"Edit");
            locationsPage.editBtnIsClickableInBusinessHours();
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.moveSliderAtSomePoint(moveCount, ohSliderDroppable.EndPoint.getValue());
            locationsPage.selectDayInWorkingHoursPopUpWin(7);
            locationsPage.clickSaveBtnInWorkingHoursPopUpWin();
            locationsPage.clickSaveBtnInWorkingHoursPopUpWin();
            List<HashMap<String,String>>  templateInfoAftOverridden = locationsPage.getLocationTemplateInfoInLocationLevel();
            if (templateInfoAftOverridden.get(1).get("Overridden").equalsIgnoreCase("Yes")) {
                SimpleUtils.pass("Overridden scheduling rules successfully");
            }else
                SimpleUtils.fail("Overridden scheduling rules failed",false);

            //reset
            locationsPage.editLocationBtnIsClickableInLocationDetails();
            locationsPage.actionsForEachTypeOfTemplate(templateInfo.get(1).get("Template Type"),"Reset");
            List<HashMap<String,String>>  templateInfoAftReset = locationsPage.getLocationTemplateInfoInLocationLevel();
            if (templateInfoAftReset.get(1).get("Overridden").equalsIgnoreCase("No")) {
                SimpleUtils.pass("Reset scheduling rules successfully");
            } else
                SimpleUtils.fail("Reset scheduling rules failed",false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Overridden assignment rules template in location level")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyOverriddenAssignmentRulesInLocationLevel(String browser, String username, String password, String location) throws Exception {

        try{

            String locationName = "OMLocation16";
            String workRoleName = "ForAutomation";
            int index = 0;
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.goToLocationDetailsPage(locationName);
            locationsPage.goToConfigurationTabInLocationLevel();
            List<HashMap<String,String>>  templateInfo = locationsPage.getLocationTemplateInfoInLocationLevel();
            locationsPage.actionsForEachTypeOfTemplate(templateInfo.get(0).get("Template Type"),"View");
            locationsPage.backToConfigurationTabInLocationLevel();
            locationsPage.editLocationBtnIsClickableInLocationDetails();
            locationsPage.actionsForEachTypeOfTemplate(templateInfo.get(0).get("Template Type"),"Edit");
            UserManagementPage userManagementPage = pageFactory.createOpsPortalUserManagementPage();
            userManagementPage.verifySearchWorkRole(workRoleName);
            userManagementPage.goToWorkRolesDetails(workRoleName);
            userManagementPage.overriddenAssignmentRule(index);
            locationsPage.clickSaveBtnInWorkingHoursPopUpWin();
            locationsPage.clickSaveBtnInWorkingHoursPopUpWin();
            List<HashMap<String,String>>  templateInfoAftOverridden = locationsPage.getLocationTemplateInfoInLocationLevel();
            if (templateInfoAftOverridden.get(0).get("Overridden").equalsIgnoreCase("Yes")) {
                SimpleUtils.pass("Overridden scheduling rules successfully");
            }else
                SimpleUtils.fail("Overridden scheduling rules failed",false);

            //reset
            locationsPage.editLocationBtnIsClickableInLocationDetails();
            locationsPage.actionsForEachTypeOfTemplate(templateInfo.get(0).get("Template Type"),"Reset");
            List<HashMap<String,String>>  templateInfoAftReset = locationsPage.getLocationTemplateInfoInLocationLevel();
            if (templateInfoAftReset.get(0).get("Overridden").equalsIgnoreCase("No")) {
                SimpleUtils.pass("Reset scheduling rules successfully");
            } else
                SimpleUtils.fail("Reset scheduling rules failed",false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Overridden Labor model template in location level")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyOverriddenLaborModelInLocationLevel(String browser, String username, String password, String location) throws Exception {

        try{

            String locationName = "OMLocation16";
            String workRoleName = "ForAutomation";
            int index = 0;
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.goToLocationDetailsPage(locationName);
            locationsPage.goToConfigurationTabInLocationLevel();
            List<HashMap<String,String>>  templateInfo = locationsPage.getLocationTemplateInfoInLocationLevel();
            locationsPage.actionsForEachTypeOfTemplate(templateInfo.get(7).get("Template Type"),"View");
            locationsPage.backToConfigurationTabInLocationLevel();
            locationsPage.editLocationBtnIsClickableInLocationDetails();
            locationsPage.actionsForEachTypeOfTemplate(templateInfo.get(7).get("Template Type"),"Edit");
            LaborModelPage laborModelPage = pageFactory.createOpsPortalLaborModelPage();
            laborModelPage.overriddenLaborModelRuleInLocationLevel(index);
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.saveBtnIsClickable();

            List<HashMap<String,String>>  templateInfoAftOverridden = locationsPage.getLocationTemplateInfoInLocationLevel();
            if (templateInfoAftOverridden.get(7).get("Overridden").equalsIgnoreCase("Yes")) {
                SimpleUtils.pass("Overridden scheduling rules successfully");
            }else
                SimpleUtils.fail("Overridden scheduling rules failed",false);

            //reset
            locationsPage.editLocationBtnIsClickableInLocationDetails();
            locationsPage.actionsForEachTypeOfTemplate(templateInfo.get(7).get("Template Type"),"Reset");
            List<HashMap<String,String>>  templateInfoAftReset = locationsPage.getLocationTemplateInfoInLocationLevel();
            if (templateInfoAftReset.get(7).get("Overridden").equalsIgnoreCase("No")) {
                SimpleUtils.pass("Reset scheduling rules successfully");
            } else
                SimpleUtils.fail("Reset scheduling rules failed",false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
