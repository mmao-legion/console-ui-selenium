package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.core.OpsPortalLocationsPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;

import static com.legion.utils.MyThreadLocal.*;

public class LocationsTest extends TestBase {

    public enum enterpriseOption{

        Op("2d8bebbd-32fd-4157-a0db-b97fb3ffe6ce"),
        Dgstage("fac86359-4880-45c3-943e-d414abbdf7aa"),
        LC("4f31d62f-b3cd-4b67-87ab-40b39c9c5626"),
        KS2("67840cca-5c4a-4411-8c6a-211fe631eb10");
        private final String value;
        enterpriseOption(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    public enum switchOption{

        OPView("OPView"),
        EnableLocationLifeCycle("EnableLocationLifeCycle"),
        EnableLocationGroupV2("EnableLocationGroupV2"),
        EnableOMJobs("EnableOMJobs"),
        UseConfigTemplateSwitch("UseConfigTemplateSwitch");
        private final String value;
        switchOption(final String newValue) {
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
    public void verifyCreateUpdateRegularLocationWithMandatoryFields(String browser, String username, String password, String location) throws Exception {

        Calendar currentTime = Calendar.getInstance();
        long currentTimeMillis = currentTime.getTimeInMillis();
        String locationName = "AutoCreate" +currentTimeMillis;
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage();
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
        locationsPage.updateLocation(locationName);
        if (locationsPage.searchNewLocation(locationName+"Update")) {
            SimpleUtils.pass("Update location successfully");
        }else
            SimpleUtils.fail("Update location failed or can't search update location",true);

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to create location with mandatory fields")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateUpdateRegularLocationWithAllFields(String browser, String username, String password, String location) throws Exception {

        Calendar currentTime = Calendar.getInstance();
        long currentTimeMillis = currentTime.getTimeInMillis();
        String locationName = "AutoCreate" +currentTimeMillis;
        int index =0;
        String searchCharactor = "*";
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage();
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
        //search created location
        if (locationsPage.searchNewLocation(locationName)) {
            SimpleUtils.pass("Create new location successfully");
        }else
            SimpleUtils.fail("Create new location failed or can't search created location",true);
        locationsPage.updateLocation(locationName);
        if (locationsPage.searchNewLocation(locationName+"Update")) {
            SimpleUtils.pass("Update location successfully");
        }else
            SimpleUtils.fail("Update location failed or can't search update location",true);

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to create location with mandatory fields")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateMockLocation(String browser, String username, String password, String location) throws Exception {
        Calendar currentTime = Calendar.getInstance();
        long currentTimeMillis = currentTime.getTimeInMillis();
        String locationName = "AutoCreate" +currentTimeMillis;
        int index =0;
        String searchCharactor = "*";
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage();
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

        //go to locations tab
        locationsPage.clickOnLocationsTab();
        //check locations item
        locationsPage.validateItemsInLocations();
        //go to sub-locations tab
        locationsPage.goToSubLocationsInLocationsPage();
        //add one mock location，first create one new location and then to mock that -to avoid duplication
        locationsPage.addNewRegularLocationWithMandatoryFields(locationName);
        locationsPage.addNewMockLocationWithAllFields(locationName,locationName,index);
        //search created location
        if (locationsPage.searchNewLocation(getLocationName())) {
            SimpleUtils.pass("Create new mock location successfully");
        }else
            SimpleUtils.fail("Create new location failed or can't search created location",true);

    }
    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to create NSO location with all fields")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateUpdateNSOLocation(String browser, String username, String password, String location) throws Exception {

        Calendar currentTime = Calendar.getInstance();
        long currentTimeMillis = currentTime.getTimeInMillis();
        String locationName = "AutoCreate" +currentTimeMillis;
        int index =0;
        String searchCharactor = "*";

        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage();
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
        locationsPage.updateLocation(locationName);
        if (locationsPage.searchNewLocation(locationName+"Update")) {
            SimpleUtils.pass("Update location successfully");
        }else
            SimpleUtils.fail("Update location failed or can't search update location",true);

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
        locationsPage.clickModelSwitchIconInDashboardPage();
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
        locationsPage.clickModelSwitchIconInDashboardPage();
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
        locationsPage.clickModelSwitchIconInDashboardPage();
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

}
