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
import org.apache.regexp.RE;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static com.legion.utils.MyThreadLocal.*;

public class LocationNavigationTest extends TestBase {

    private static String nyCentralLocation = "NY CENTRAL";
    private static String austinDownTownLocation = "AUSTIN DOWNTOWN";
    private static String liftOpsParentLocation = "Lift Ops_Parent";
    private static String location00127 = "00127";
    private static String location00364 = "00364";
    private static String location00808 = "00808";
    private static String hQ = "HQ";
    private static String rockVilleLocation = "Rock ville";
    private static String mountainViewLocation = "Mountain View";

    public static Map<String, String> districtsMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/UpperfieldsForDifferentEnterprises.json");

    private static String Location = "Location";
    private static String District = "District";
    private static String Region = "Region";
    private static String BusinessUnit = "Business Unit";

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{
        try {
            this.createDriver((String) params[0], "83", "Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify changing location on SM schedule tab")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyChangingLocationOnSMScheduleTabAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
                locationSelectorPage.changeLocationDirect(austinDownTownLocation);
            } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
                locationSelectorPage.changeLocationDirect(location00364);
            }
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
                locationSelectorPage.changeLocationDirect(nyCentralLocation);
            } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
                locationSelectorPage.changeLocationDirect(location00127);
            }
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Forecast.getValue());
            if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
                locationSelectorPage.changeLocationDirect(austinDownTownLocation);
            } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
                locationSelectorPage.changeLocationDirect(location00364);
            }
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify searching and selecting the location on SM schedule tab")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySearchingAndSelectingTheLocationOnSMScheduleTabAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
            String[] upperFields = null;
            if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
                upperFields = districtsMap.get("KendraScott2_Enterprise2").split(">");
                searchAndCheckTheUpperFields(liftOpsParentLocation, upperFields);
            } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
                upperFields = districtsMap.get("CinemarkWkdy_Enterprise2").split(">");
                searchAndCheckTheUpperFields(location00808, upperFields);
            }
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);

            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
                upperFields = districtsMap.get("KendraScott2_Enterprise").split(">");
                searchAndCheckTheUpperFields(austinDownTownLocation, upperFields);
            } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
                upperFields = districtsMap.get("CinemarkWkdy_Enterprise").split(">");
                searchAndCheckTheUpperFields(location00364, upperFields);
            }
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);

            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Forecast.getValue());
            if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
                upperFields = districtsMap.get("KendraScott2_Enterprise2").split(">");
                searchAndCheckTheUpperFields(liftOpsParentLocation, upperFields);
            } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
                upperFields = districtsMap.get("CinemarkWkdy_Enterprise2").split(">");
                searchAndCheckTheUpperFields(location00808, upperFields);
            }
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    private void searchAndCheckTheUpperFields (String locationName, String[] upperFields) throws Exception {
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(locationName);

        Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
        if (upperFields.length > 1) {
            SimpleUtils.assertOnFail("The selected upperfields is incorrect",
                    selectedUpperFields.get(Location).equalsIgnoreCase(locationName)
                            && selectedUpperFields.get(District).equalsIgnoreCase(upperFields[upperFields.length-1].trim())
                            && selectedUpperFields.get(Region).equalsIgnoreCase(upperFields[upperFields.length-2].trim()), false);
        } else {
            SimpleUtils.assertOnFail("The selected upperfields is incorrect",
                    selectedUpperFields.get(Location).equalsIgnoreCase(locationName)
                            && selectedUpperFields.get(District).equalsIgnoreCase(upperFields[upperFields.length-1].trim())
                            && selectedUpperFields.get(hQ).equalsIgnoreCase(hQ), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify changing district on SM schedule tab")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyChangingDistrictOnSMScheduleTabAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            //Go to Schedule tab -> Overview page
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            //Click on change district button to change the district
            String[] upperFields = null;
            String locationName = "";
            if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
                upperFields = districtsMap.get("KendraScott2_Enterprise2").split(">");
                locationName = liftOpsParentLocation;
            } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
                upperFields = districtsMap.get("CinemarkWkdy_Enterprise2").split(">");
                locationName = location00808;
            }
            String districtName = upperFields[upperFields.length-1].trim();
            locationSelectorPage.changeUpperFieldDirect(District, districtName);

            //Verify the page loaded
            SimpleUtils.assertOnFail("Schedule page DM page not loaded Successfully!",
                    schedulePage.isScheduleDMView(), false);
            //Verify the locations listed
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(locationName);

            //Go to Schedule tab -> Schedule page
            locationSelectorPage.changeLocationDirect(locationName);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            //Click on change district button to change the district
            if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
                upperFields = districtsMap.get("KendraScott2_Enterprise").split(">");
                locationName = austinDownTownLocation;
            } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
                upperFields = districtsMap.get("CinemarkWkdy_Enterprise").split(">");
                locationName = location00127;
            }
            districtName = upperFields[upperFields.length-1].trim();
            locationSelectorPage.changeUpperFieldDirect(District, districtName);

            //Verify the page loaded
            SimpleUtils.assertOnFail("Schedule page DM page not loaded Successfully!",
                    schedulePage.isScheduleDMView(), false);
            //Verify the locations listed
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(locationName);

            //Go to Schedule tab -> Forecast page
            locationSelectorPage.changeLocationDirect(locationName);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Forecast.getValue());
            //Click on change district button to change the district
            if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
                upperFields = districtsMap.get("KendraScott2_Enterprise2").split(">");
                locationName = liftOpsParentLocation;
            } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
                upperFields = districtsMap.get("CinemarkWkdy_Enterprise2").split(">");
                locationName = location00808;
            }
            districtName = upperFields[upperFields.length-1].trim();
            locationSelectorPage.changeUpperFieldDirect(District, districtName);

            //Verify the page loaded
            SimpleUtils.assertOnFail("Schedule page DM page not loaded Successfully!",
                    schedulePage.isScheduleDMView(), false);
            //Verify the locations listed
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(locationName);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify searching and selecting the district on SM schedule tab")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySearchingAndSelectingTheDistrictOnSMScheduleTabAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();

            //Go to Schedule tab -> Overview page
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
            String[] upperFields = null;
            String locationName = "";
            //Click on Search button to search the district and select
            if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
                upperFields = districtsMap.get("KendraScott2_Enterprise2").split(">");
                searchDistrictAndCheckTheUpperFields(upperFields);
                locationName = liftOpsParentLocation;
            } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
                upperFields = districtsMap.get("CinemarkWkdy_Enterprise2").split(">");
                searchDistrictAndCheckTheUpperFields(upperFields);
                locationName = location00808;
            }
            //Verify the locations listed
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(locationName);

            //Go to Schedule tab -> Forecast page
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeLocationDirect(locationName);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Forecast.getValue());
            //Click on Search button to search the district and select
            if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
                upperFields = districtsMap.get("KendraScott2_Enterprise").split(">");
                searchDistrictAndCheckTheUpperFields(upperFields);
                locationName = austinDownTownLocation;
            } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
                upperFields = districtsMap.get("CinemarkWkdy_Enterprise").split(">");
                searchDistrictAndCheckTheUpperFields(upperFields);
                locationName = location00127;
            }
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(locationName);
            //Go to Schedule tab -> Schedule page
            locationSelectorPage.changeLocationDirect(locationName);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            //Click on Search button to search the district and select
            if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
                upperFields = districtsMap.get("KendraScott2_Enterprise2").split(">");
                searchDistrictAndCheckTheUpperFields(upperFields);
                locationName = liftOpsParentLocation;
            } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
                upperFields = districtsMap.get("CinemarkWkdy_Enterprise2").split(">");
                searchDistrictAndCheckTheUpperFields(upperFields);
                locationName = location00808;
            }
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(locationName);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    private void searchDistrictAndCheckTheUpperFields (String[] upperFields) throws Exception {
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        if (upperFields.length > 1) {
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(upperFields[1].trim());
        } else {
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(upperFields[0].trim());
        }
        Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();

        //Verify the upperfiled should be correct
        //Verify district is selected in the navigation bar
        if (upperFields.length > 1) {
            SimpleUtils.assertOnFail("The selected upperfields is incorrect",
                    selectedUpperFields.get(District).equalsIgnoreCase(upperFields[upperFields.length-1].trim())
                            && selectedUpperFields.get(Region).equalsIgnoreCase(upperFields[upperFields.length-2].trim()), false);
        } else {
            SimpleUtils.assertOnFail("The selected upperfields is incorrect",
                    selectedUpperFields.get(Region).equalsIgnoreCase(upperFields[upperFields.length-1].trim())
                            && selectedUpperFields.get(hQ).equalsIgnoreCase(hQ), false);
        }
        //Verify the DM page loaded
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        SimpleUtils.assertOnFail("Schedule page DM page not loaded Successfully!",
                schedulePage.isScheduleDMView(), false);
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify changing Region on SM schedule tab")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyChangingRegionOnSMScheduleTabAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            //Go to Schedule tab -> Overview page
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            //Click on change region button to change the region
            String[] upperFields = districtsMap.get("Coffee_Enterprise2").split(">");
            String locationName = mountainViewLocation;
            String regionName = upperFields[upperFields.length-2].trim();
            String districtName = upperFields[upperFields.length-1].trim();
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);

            //Verify the page loaded
            SimpleUtils.assertOnFail("Schedule page Region view page not loaded Successfully!",
                    schedulePage.isScheduleDMView(), false);
            //Verify the locations listed
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(districtName);


            //Go to Schedule tab -> Schedule page
            locationSelectorPage.changeUpperFieldsByName("District", districtName);
            locationSelectorPage.changeLocationDirect(locationName);
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            //Click on change region button to change the region
            upperFields = districtsMap.get("Coffee_Enterprise").split(">");
            locationName = rockVilleLocation;
            regionName = upperFields[upperFields.length-2].trim();
            districtName = upperFields[upperFields.length-1].trim();
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);

            //Verify the page loaded
            SimpleUtils.assertOnFail("Schedule page Region view page not loaded Successfully!",
                    schedulePage.isScheduleDMView(), false);
            //Verify the locations listed
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(districtName);

            //Go to Schedule tab -> Forecast page
            locationSelectorPage.changeUpperFieldsByName("District", districtName);
            locationSelectorPage.changeLocationDirect(locationName);
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Forecast.getValue());
            //Click on change region button to change the region
            upperFields = districtsMap.get("Coffee_Enterprise2").split(">");
            regionName = upperFields[upperFields.length-2].trim();
            districtName = upperFields[upperFields.length-1].trim();
            locationSelectorPage.changeUpperFieldDirect(Region, regionName);

            //Verify the page loaded
            SimpleUtils.assertOnFail("Schedule page Region view page not loaded Successfully!",
                    schedulePage.isScheduleDMView(), false);
            //Verify the locations listed
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(districtName);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify searching and selecting the region on SM schedule tab")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySearchingAndSelectingTheRegionOnSMScheduleTabAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();

            //Go to Schedule tab -> Overview page
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
            //Click on change region button to change the region
            String[] upperFields = districtsMap.get("Coffee_Enterprise2").split(">");
            String locationName = mountainViewLocation;
            searchRegionAndCheckTheUpperFields(upperFields, locationName);

            //Go to Schedule tab -> Forecast page
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Forecast.getValue());
            locationName = rockVilleLocation;
            upperFields = districtsMap.get("Coffee_Enterprise").split(">");
            searchRegionAndCheckTheUpperFields(upperFields, locationName);

            //Go to Schedule tab -> Schedule page
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            locationName = mountainViewLocation;
            upperFields = districtsMap.get("Coffee_Enterprise2").split(">");
            searchRegionAndCheckTheUpperFields(upperFields, locationName);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    private void searchRegionAndCheckTheUpperFields (String[] upperFields, String locationName) throws Exception {
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(upperFields[upperFields.length-2].trim());
        Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
        String bUName = upperFields[upperFields.length-3].trim();
        String regionName = upperFields[upperFields.length-2].trim();
        String districtName = upperFields[upperFields.length-1].trim();
        //Verify the upperfield should be correct
        //Verify district is selected in the navigation bar
        SimpleUtils.assertOnFail("The selected upperfields is incorrect",
                selectedUpperFields.get(Region).equalsIgnoreCase(regionName)
                        && selectedUpperFields.get(BusinessUnit).equalsIgnoreCase(bUName), false);

        //Verify the Region page loaded
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        SimpleUtils.assertOnFail("Schedule page Region view not loaded Successfully!",
                schedulePage.isScheduleDMView(), false);

        //Verify the district listed
        ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
        scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(districtName);

        locationSelectorPage.changeUpperFieldDirect(District, districtName);
        locationSelectorPage.changeLocationDirect(locationName);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify changing business unit on SM schedule tab")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyChangingBusinessUnitOnSMScheduleTabAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            //Go to Schedule tab -> Overview page
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            //Click on change region button to change the region
            String[] upperFields = districtsMap.get("Coffee_Enterprise2").split(">");
            String locationName = mountainViewLocation;
            String buName = upperFields[upperFields.length-3].trim();
            String regionName = upperFields[upperFields.length-2].trim();
            String districtName = upperFields[upperFields.length-1].trim();
            locationSelectorPage.selectCurrentUpperFieldAgain(Region);
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);

            //Verify the page loaded
            SimpleUtils.assertOnFail("Schedule page BU view page not loaded Successfully!",
                    schedulePage.isScheduleDMView(), false);
            //Verify the region listed
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(regionName);


            //Go to Schedule tab -> Schedule page
            locationSelectorPage.changeUpperFieldsByName(Region, regionName);
            locationSelectorPage.changeUpperFieldsByName(District, districtName);
            locationSelectorPage.changeLocationDirect(locationName);
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            //Click on change BU button to change the region
            upperFields = districtsMap.get("Coffee_Enterprise").split(">");
            buName = upperFields[upperFields.length-3].trim();
            locationName = rockVilleLocation;
            regionName = upperFields[upperFields.length-2].trim();
            districtName = upperFields[upperFields.length-1].trim();
            locationSelectorPage.selectCurrentUpperFieldAgain(Region);
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);

            //Verify the page loaded
            SimpleUtils.assertOnFail("Schedule page Region view page not loaded Successfully!",
                    schedulePage.isScheduleDMView(), false);
            //Verify the Region listed
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(regionName);

            //Go to Schedule tab -> Forecast page
            locationSelectorPage.changeUpperFieldsByName(Region, regionName);
            locationSelectorPage.changeUpperFieldsByName(District, districtName);
            locationSelectorPage.changeLocationDirect(locationName);
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Forecast.getValue());
            //Click on change region button to change the region
            upperFields = districtsMap.get("Coffee_Enterprise2").split(">");
            buName = upperFields[upperFields.length-3].trim();
            regionName = upperFields[upperFields.length-2].trim();
            locationSelectorPage.selectCurrentUpperFieldAgain(Region);
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);

            //Verify the page loaded
            SimpleUtils.assertOnFail("Schedule page Region view page not loaded Successfully!",
                    schedulePage.isScheduleDMView(), false);
            //Verify the region listed
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(regionName);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify searching and selecting the business unit on SM schedule tab")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySearchingAndSelectingTheBusinessUnitOnSMScheduleTabAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();

            //Go to Schedule tab -> Overview page
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
            //Click on change BU button to change the BU
            String[] upperFields = districtsMap.get("Coffee_Enterprise2").split(">");
            String locationName = mountainViewLocation;
            searchBusinessUnitAndCheckTheUpperFields(upperFields, locationName);

            //Go to Schedule tab -> Forecast page
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Forecast.getValue());
            locationName = rockVilleLocation;
            upperFields = districtsMap.get("Coffee_Enterprise").split(">");
            searchBusinessUnitAndCheckTheUpperFields(upperFields, locationName);

            //Go to Schedule tab -> Schedule page
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            locationName = mountainViewLocation;
            upperFields = districtsMap.get("Coffee_Enterprise2").split(">");
            searchBusinessUnitAndCheckTheUpperFields(upperFields, locationName);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    private void searchBusinessUnitAndCheckTheUpperFields (String[] upperFields, String locationName) throws Exception {
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(upperFields[upperFields.length-3].trim());
        Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
        String bUName = upperFields[upperFields.length-3].trim();
        String regionName = upperFields[upperFields.length-2].trim();
        String districtName = upperFields[upperFields.length-1].trim();
        //Verify the upperfield should be correct
        SimpleUtils.assertOnFail("The selected upperfields is incorrect",
                selectedUpperFields.get(BusinessUnit).equalsIgnoreCase(bUName), false);

        //Verify the BU page loaded
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        SimpleUtils.assertOnFail("Schedule page BU view not loaded Successfully!",
                schedulePage.isScheduleDMView(), false);

        //Verify the region listed
        ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
        scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(regionName);
        locationSelectorPage.changeUpperFieldDirect(Region, regionName);
        locationSelectorPage.changeUpperFieldDirect(District, districtName);
        locationSelectorPage.changeLocationDirect(locationName);
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify selecting HQ on business unit schedule tab")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySelectingHQOnBusinessUnitScheduleTabAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            //Click on change HQ button to change the HQ
            String[] upperFields = districtsMap.get("Coffee_Enterprise2").split(">");
            String locationName = mountainViewLocation;
            String buName = upperFields[upperFields.length-3].trim();
            String regionName = upperFields[upperFields.length-2].trim();
            String districtName = upperFields[upperFields.length-1].trim();
            locationSelectorPage.selectCurrentUpperFieldAgain(Region);
            locationSelectorPage.selectCurrentUpperFieldAgain(BusinessUnit);
            locationSelectorPage.changeUpperFieldDirect(hQ, hQ);

            //Verify the No data page loaded
            SimpleUtils.assertOnFail("The No data to show page fail to load! ",
                    locationSelectorPage.isNoDataToShowPageLoaded(), false);

            //Go to Schedule tab
            locationSelectorPage.changeUpperFieldsByName(BusinessUnit, buName);
            locationSelectorPage.changeUpperFieldsByName(Region, regionName);
            locationSelectorPage.changeUpperFieldsByName(District, districtName);
            locationSelectorPage.changeLocationDirect(locationName);
            schedulePage.clickOnScheduleConsoleMenuItem();

            //Click on change BU button to change the BU
            upperFields = districtsMap.get("Coffee_Enterprise").split(">");
            buName = upperFields[upperFields.length-3].trim();
            locationName = rockVilleLocation;
            regionName = upperFields[upperFields.length-2].trim();
            districtName = upperFields[upperFields.length-1].trim();
            locationSelectorPage.selectCurrentUpperFieldAgain(Region);
            locationSelectorPage.selectCurrentUpperFieldAgain(BusinessUnit);
            locationSelectorPage.changeUpperFieldDirect(hQ, hQ);

            //Verify the No data page loaded
            SimpleUtils.assertOnFail("The No data to show page fail to load! ",
                    locationSelectorPage.isNoDataToShowPageLoaded(), false);

            //Go to Schedule tab
            locationSelectorPage.changeUpperFieldsByName(BusinessUnit, buName);
            locationSelectorPage.changeUpperFieldsByName(Region, regionName);
            locationSelectorPage.changeUpperFieldsByName(District, districtName);
            locationSelectorPage.changeLocationDirect(locationName);
            schedulePage.clickOnScheduleConsoleMenuItem();
            //Click on change Bu button to change the BU
            upperFields = districtsMap.get("Coffee_Enterprise2").split(">");
            locationSelectorPage.selectCurrentUpperFieldAgain(Region);
            locationSelectorPage.selectCurrentUpperFieldAgain(BusinessUnit);
            locationSelectorPage.changeUpperFieldDirect(hQ, hQ);

            //Verify the No data page loaded
            SimpleUtils.assertOnFail("The No data to show page fail to load! ",
                    locationSelectorPage.isNoDataToShowPageLoaded(), false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify searching HQ on different level of schedule tab")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySearchingAndSelectingTheHQOnSMScheduleTabAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();

            //Click on change region button to change the region
            String[] upperFields = districtsMap.get("Coffee_Enterprise2").split(">");
            String locationName = mountainViewLocation;
            searchHQAndCheckTheUpperFields(upperFields, locationName);

            //Go to Schedule tab -> Forecast page
            locationName = rockVilleLocation;
            upperFields = districtsMap.get("Coffee_Enterprise").split(">");
            searchHQAndCheckTheUpperFields(upperFields, locationName);

            //Go to Schedule tab -> Schedule page
            locationName = mountainViewLocation;
            upperFields = districtsMap.get("Coffee_Enterprise2").split(">");
            searchHQAndCheckTheUpperFields(upperFields, locationName);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    private void searchHQAndCheckTheUpperFields (String[] upperFields, String locationName) throws Exception {
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(hQ);
        Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
        String bUName = upperFields[upperFields.length-3].trim();
        String regionName = upperFields[upperFields.length-2].trim();
        String districtName = upperFields[upperFields.length-1].trim();
        //Verify the upperfield should be correct
        SimpleUtils.assertOnFail("The selected upperfields is incorrect",
                selectedUpperFields.get(hQ).equalsIgnoreCase(hQ), false);

        //Verify the No data page loaded
        SimpleUtils.assertOnFail("The No data to show page fail to load! ",
                locationSelectorPage.isNoDataToShowPageLoaded(), false);

        //Verify the district listed
        locationSelectorPage.changeUpperFieldDirect(BusinessUnit, bUName);
        locationSelectorPage.changeUpperFieldDirect(Region, regionName);
        locationSelectorPage.changeUpperFieldDirect(District, districtName);
        locationSelectorPage.changeLocationDirect(locationName);
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify selecting different level of upperfields and locations on HQ schedule tab")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySelectingDifferentLevelOfUpperFieldsAndLocationsOnHQScheduleTabAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(hQ);
            //Verify the No data page loaded
            SimpleUtils.assertOnFail("The No data to show page fail to load! ",
                    locationSelectorPage.isNoDataToShowPageLoaded(), false);

            //Click on change HQ button to change the HQ
            String[] upperFields = districtsMap.get("Coffee_Enterprise").split(">");
            String locationName = rockVilleLocation;
            String buName = upperFields[upperFields.length-3].trim();
            String regionName = upperFields[upperFields.length-2].trim();
            String districtName = upperFields[upperFields.length-1].trim();

            //Go to Schedule tab
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(regionName);

            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(districtName);

            locationSelectorPage.changeUpperFieldDirect(District, districtName);
            scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(locationName);
            locationSelectorPage.changeLocationDirect(locationName);
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);


        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify selecting different level of upperfields and locations on HQ compliance tab")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySelectingDifferentLevelOfUpperFieldsAndLocationsOnHQComplianceTabAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            dashboardPage.isConsoleNavigationBarIsGray("Compliance");
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            compliancePage.clickOnComplianceConsoleMenu();

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(hQ);
            //Verify the No data page loaded
            SimpleUtils.assertOnFail("The No data to show page fail to load! ",
                    locationSelectorPage.isNoDataToShowPageLoaded(), false);

            String[] upperFields = districtsMap.get("Coffee_Enterprise").split(">");
            String locationName = rockVilleLocation;
            String buName = upperFields[upperFields.length-3].trim();
            String regionName = upperFields[upperFields.length-2].trim();
            String districtName = upperFields[upperFields.length-1].trim();

            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);
            ComplianceDMViewPage complianceDMViewPage = pageFactory.createComplianceDMViewPage();
            complianceDMViewPage.getAllUpperFieldInfoFromComplianceDMViewByUpperField(regionName);

            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            complianceDMViewPage.getAllUpperFieldInfoFromComplianceDMViewByUpperField(districtName);

            locationSelectorPage.changeUpperFieldDirect(District, districtName);
            complianceDMViewPage.getAllUpperFieldInfoFromComplianceDMViewByUpperField(locationName);
            locationSelectorPage.changeLocationDirect(locationName);
            //Verify the No data page loaded
            SimpleUtils.assertOnFail("The No data to show page fail to load! ",
                    locationSelectorPage.isNoDataToShowPageLoaded(), false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify selecting different level of upperfields on SM compliance tab")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySelectingDifferentLevelOfUpperFieldsOnSMComplianceTabAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            CompliancePage compliancePage = pageFactory.createConsoleCompliancePage();
            compliancePage.clickOnComplianceConsoleMenu();

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            //Verify the No data page loaded
            SimpleUtils.assertOnFail("The No data to show page fail to load! ",
                    locationSelectorPage.isNoDataToShowPageLoaded(), false);
            String[] upperFields = districtsMap.get("Coffee_Enterprise").split(">");
            String locationName = rockVilleLocation;
            String buName = upperFields[upperFields.length-3].trim();
            String regionName = upperFields[upperFields.length-2].trim();
            String districtName = upperFields[upperFields.length-1].trim();
            ComplianceDMViewPage complianceDMViewPage = pageFactory.createComplianceDMViewPage();

            locationSelectorPage.changeUpperFieldDirect(District, districtName);
            complianceDMViewPage.getAllUpperFieldInfoFromComplianceDMViewByUpperField(locationName);

            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            complianceDMViewPage.getAllUpperFieldInfoFromComplianceDMViewByUpperField(districtName);

            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);
            complianceDMViewPage.getAllUpperFieldInfoFromComplianceDMViewByUpperField(regionName);

            locationSelectorPage.changeUpperFieldDirect(hQ, hQ);
            //Verify the No data page loaded
            SimpleUtils.assertOnFail("The No data to show page fail to load! ",
                    locationSelectorPage.isNoDataToShowPageLoaded(), false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify selecting different level of upperfields and locations on HQ Report tab")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySelectingDifferentLevelOfUpperFieldsAndLocationsOnHQReportTabAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            dashboardPage.isConsoleNavigationBarIsGray("Report");
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            //Check Report page is display after click Report tab
            AnalyticsPage analyticsPage = pageFactory.createConsoleAnalyticsPage();
            analyticsPage.clickOnAnalyticsConsoleMenu();
            SimpleUtils.assertOnFail("Report Page not loaded Successfully!", analyticsPage.isReportsPageLoaded(), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(hQ);

            //Verify the No data page loaded
            SimpleUtils.assertOnFail("The No data to show page fail to load! ",
                    locationSelectorPage.isNoDataToShowPageLoaded(), false);

            String[] upperFields = districtsMap.get("Coffee_Enterprise").split(">");
            String locationName = rockVilleLocation;
            String buName = upperFields[upperFields.length-3].trim();
            String regionName = upperFields[upperFields.length-2].trim();
            String districtName = upperFields[upperFields.length-1].trim();
            String reportMenuTab = "Report";
            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);
            SimpleUtils.assertOnFail("Report Page not loaded Successfully!",
                    analyticsPage.isReportsPageLoaded(), false);
            SimpleUtils.assertOnFail("Report is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(reportMenuTab), false);
            SimpleUtils.assertOnFail("Report tab is not selected correctly !",
                    analyticsPage.isSpecificReportsTabBeenSelected(buName), false);

            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            SimpleUtils.assertOnFail("Report Page not loaded Successfully!",
                    analyticsPage.isReportsPageLoaded(), false);
            SimpleUtils.assertOnFail("Report is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(reportMenuTab), false);
            SimpleUtils.assertOnFail("Report tab is not selected correctly !",
                    analyticsPage.isSpecificReportsTabBeenSelected(regionName), false);

            locationSelectorPage.changeUpperFieldDirect(District, districtName);
            SimpleUtils.assertOnFail("Report Page not loaded Successfully!",
                    analyticsPage.isReportsPageLoaded(), false);
            SimpleUtils.assertOnFail("Report is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(reportMenuTab), false);
            SimpleUtils.assertOnFail("Report tab is not selected correctly !",
                    analyticsPage.isSpecificReportsTabBeenSelected(districtName), false);

            locationSelectorPage.changeLocationDirect(locationName);
            SimpleUtils.assertOnFail("Report Page not loaded Successfully!",
                    analyticsPage.isReportsPageLoaded(), false);
            SimpleUtils.assertOnFail("Report is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(reportMenuTab), false);
            SimpleUtils.assertOnFail("Report tab is not selected correctly !",
                    analyticsPage.isSpecificReportsTabBeenSelected(locationName), false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify selecting different level of upperfields on location Report tab")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySelectingDifferentLevelOfUpperFieldsOnLocationReportTabAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            AnalyticsPage analyticsPage = pageFactory.createConsoleAnalyticsPage();
            analyticsPage.clickOnAnalyticsConsoleMenu();
            String reportMenuTab = "Report";
            String[] upperFields = districtsMap.get("Coffee_Enterprise").split(">");
            String locationName = rockVilleLocation;
            String buName = upperFields[upperFields.length-3].trim();
            String regionName = upperFields[upperFields.length-2].trim();
            String districtName = upperFields[upperFields.length-1].trim();

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            SimpleUtils.assertOnFail("Report Page not loaded Successfully!",
                    analyticsPage.isReportsPageLoaded(), false);
            SimpleUtils.assertOnFail("Report is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(reportMenuTab), false);
            SimpleUtils.assertOnFail("Report tab is not selected correctly !",
                    analyticsPage.isSpecificReportsTabBeenSelected(locationName), false);

            locationSelectorPage.changeUpperFieldDirect(District, districtName);
            SimpleUtils.assertOnFail("Report Page not loaded Successfully!",
                    analyticsPage.isReportsPageLoaded(), false);
            SimpleUtils.assertOnFail("Report is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(reportMenuTab), false);
            SimpleUtils.assertOnFail("Report tab is not selected correctly !",
                    analyticsPage.isSpecificReportsTabBeenSelected(districtName), false);

            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            SimpleUtils.assertOnFail("Report Page not loaded Successfully!",
                    analyticsPage.isReportsPageLoaded(), false);
            SimpleUtils.assertOnFail("Report is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(reportMenuTab), false);
            SimpleUtils.assertOnFail("Report tab is not selected correctly !",
                    analyticsPage.isSpecificReportsTabBeenSelected(regionName), false);

            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);
            SimpleUtils.assertOnFail("Report Page not loaded Successfully!",
                    analyticsPage.isReportsPageLoaded(), false);
            SimpleUtils.assertOnFail("Report is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(reportMenuTab), false);
            SimpleUtils.assertOnFail("Report tab is not selected correctly !",
                    analyticsPage.isSpecificReportsTabBeenSelected(buName), false);

            locationSelectorPage.changeUpperFieldDirect(hQ, hQ);
            //Verify the No data page loaded
            SimpleUtils.assertOnFail("The No data to show page fail to load! ",
                    locationSelectorPage.isNoDataToShowPageLoaded(), false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify selecting different level of upperfields and locations on HQ News tab - Newsfeed page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySelectingDifferentLevelOfUpperFieldsAndLocationsOnHQNewsTabAsStoreManager(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            NewsPage newsPage = pageFactory.createNewsPage();
            newsPage.clickOnNewsConsoleMenu();
            List<String> postInfo = newsPage.createPost();
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            String newsMenuTab = "News";
            String[] upperFields = districtsMap.get("Coffee_Enterprise").split(">");
            String locationName = rockVilleLocation;
            String buName = upperFields[upperFields.length-3].trim();
            String regionName = upperFields[upperFields.length-2].trim();
            String districtName = upperFields[upperFields.length-1].trim();

            loginAsDifferentRole("InternalAdmin");

            //Check Report page is display after click Report tab
            newsPage.clickOnNewsConsoleMenu();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(hQ);

            SimpleUtils.assertOnFail("The news page fail to load! ",
                    newsPage.checkIfNewsPageLoaded(), false);
            SimpleUtils.assertOnFail("The location level post fail to load! ",
                    !newsPage.checkIfPostExists(postInfo.get(0)), false);
            SimpleUtils.assertOnFail("News menu tab is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(newsMenuTab), false);


            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);
            SimpleUtils.assertOnFail("The news page fail to load! ",
                    newsPage.checkIfNewsPageLoaded(), false);
            SimpleUtils.assertOnFail("The location level post fail to load! ",
                    !newsPage.checkIfPostExists(postInfo.get(0)), false);
            SimpleUtils.assertOnFail("News menu tab is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(newsMenuTab), false);

            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            SimpleUtils.assertOnFail("The news page fail to load! ",
                    newsPage.checkIfNewsPageLoaded(), false);
            SimpleUtils.assertOnFail("The location level post fail to load! ",
                    !newsPage.checkIfPostExists(postInfo.get(0)), false);
            SimpleUtils.assertOnFail("News menu tab is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(newsMenuTab), false);

            locationSelectorPage.changeUpperFieldDirect(District, districtName);
            SimpleUtils.assertOnFail("The news page fail to load! ",
                    newsPage.checkIfNewsPageLoaded(), false);
            SimpleUtils.assertOnFail("The location level post fail to load! ",
                    !newsPage.checkIfPostExists(postInfo.get(0)), false);
            SimpleUtils.assertOnFail("News menu tab is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(newsMenuTab), false);

            locationSelectorPage.changeLocationDirect(locationName);
            SimpleUtils.assertOnFail("The news page fail to load! ",
                    newsPage.checkIfNewsPageLoaded(), false);
            SimpleUtils.assertOnFail("The location level post fail to load! ",
                    newsPage.checkIfPostExists(postInfo.get(0)), false);
            SimpleUtils.assertOnFail("News menu tab is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(newsMenuTab), false);

            newsPage.deletePost(postInfo.get(0));

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }



    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify selecting different level of upperfields on location Newsfeed page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySelectingDifferentLevelOfUpperFieldsOnLocationsNewsfeedPageAsStoreManager(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            NewsPage newsPage = pageFactory.createNewsPage();
            newsPage.clickOnNewsConsoleMenu();
            List<String> postInfo = newsPage.createPost();

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            String newsMenuTab = "News";
            String[] upperFields = districtsMap.get("Coffee_Enterprise").split(">");
            String locationName = rockVilleLocation;
            String buName = upperFields[upperFields.length-3].trim();
            String regionName = upperFields[upperFields.length-2].trim();
            String districtName = upperFields[upperFields.length-1].trim();

            loginAsDifferentRole("InternalAdmin");
            newsPage.clickOnNewsConsoleMenu();
            SimpleUtils.assertOnFail("The news page fail to load! ",
                    newsPage.checkIfNewsPageLoaded(), false);
            SimpleUtils.assertOnFail("The location level post fail to load! ",
                    newsPage.checkIfPostExists(postInfo.get(0)), false);
            SimpleUtils.assertOnFail("News menu tab is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(newsMenuTab), false);

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeUpperFieldDirect(District, districtName);
            SimpleUtils.assertOnFail("The news page fail to load! ",
                    newsPage.checkIfNewsPageLoaded(), false);
            SimpleUtils.assertOnFail("The location level post fail to load! ",
                    !newsPage.checkIfPostExists(postInfo.get(0)), false);
            SimpleUtils.assertOnFail("News menu tab is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(newsMenuTab), false);

            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            SimpleUtils.assertOnFail("The news page fail to load! ",
                    newsPage.checkIfNewsPageLoaded(), false);
            SimpleUtils.assertOnFail("The location level post fail to load! ",
                    !newsPage.checkIfPostExists(postInfo.get(0)), false);
            SimpleUtils.assertOnFail("News menu tab is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(newsMenuTab), false);

            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);
            SimpleUtils.assertOnFail("The news page fail to load! ",
                    newsPage.checkIfNewsPageLoaded(), false);
            SimpleUtils.assertOnFail("The location level post fail to load! ",
                    !newsPage.checkIfPostExists(postInfo.get(0)), false);
            SimpleUtils.assertOnFail("News menu tab is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(newsMenuTab), false);

            locationSelectorPage.changeUpperFieldDirect(hQ, hQ);
            SimpleUtils.assertOnFail("The news page fail to load! ",
                    newsPage.checkIfNewsPageLoaded(), false);
            SimpleUtils.assertOnFail("The location level post fail to load! ",
                    !newsPage.checkIfPostExists(postInfo.get(0)), false);
            SimpleUtils.assertOnFail("News menu tab is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(newsMenuTab), false);

            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(locationName);
            newsPage.deletePost(postInfo.get(0));

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify location navigation should not show on Moderation page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyLocationNavigationShouldNotShowOnModerationPageAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            NewsPage newsPage = pageFactory.createNewsPage();
            newsPage.clickOnNewsConsoleMenu();

            String newsMenuTab = "News";
            String[] upperFields = districtsMap.get("Coffee_Enterprise").split(">");
            String locationName = rockVilleLocation;
            String buName = upperFields[upperFields.length-3].trim();
            String regionName = upperFields[upperFields.length-2].trim();
            String districtName = upperFields[upperFields.length-1].trim();

            newsPage.clickOnNewsConsoleMenu();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(hQ);
            SimpleUtils.assertOnFail("The news page fail to load! ",
                    newsPage.checkIfNewsPageLoaded(), false);
            newsPage.clickModerationTab();
            SimpleUtils.assertOnFail("The upperfield navigation should not display! ",
                    !locationSelectorPage.isUpperFieldNavigationLoaded() , false);
            SimpleUtils.assertOnFail("News menu tab is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(newsMenuTab), false);
            newsPage.clickNewsfeedTab();

            locationSelectorPage.changeUpperFieldDirect(BusinessUnit, buName);
            SimpleUtils.assertOnFail("The news page fail to load! ",
                    newsPage.checkIfNewsPageLoaded(), false);
            newsPage.clickModerationTab();
            SimpleUtils.assertOnFail("The upperfield navigation should not display! ",
                    !locationSelectorPage.isUpperFieldNavigationLoaded() , false);
            SimpleUtils.assertOnFail("News menu tab is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(newsMenuTab), false);
            newsPage.clickNewsfeedTab();

            locationSelectorPage.changeUpperFieldDirect(Region, regionName);
            SimpleUtils.assertOnFail("The news page fail to load! ",
                    newsPage.checkIfNewsPageLoaded(), false);
            newsPage.clickModerationTab();
            SimpleUtils.assertOnFail("The upperfield navigation should not display! ",
                    !locationSelectorPage.isUpperFieldNavigationLoaded() , false);
            SimpleUtils.assertOnFail("News menu tab is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(newsMenuTab), false);
            newsPage.clickNewsfeedTab();

            locationSelectorPage.changeUpperFieldDirect(District, districtName);
            SimpleUtils.assertOnFail("The news page fail to load! ",
                    newsPage.checkIfNewsPageLoaded(), false);
            newsPage.clickModerationTab();
            SimpleUtils.assertOnFail("The upperfield navigation should not display! ",
                    !locationSelectorPage.isUpperFieldNavigationLoaded() , false);
            SimpleUtils.assertOnFail("News menu tab is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(newsMenuTab), false);
            newsPage.clickNewsfeedTab();

            locationSelectorPage.changeLocationDirect(locationName);
            SimpleUtils.assertOnFail("The news page fail to load! ",
                    newsPage.checkIfNewsPageLoaded(), false);
            newsPage.clickModerationTab();
            SimpleUtils.assertOnFail("The upperfield navigation should not display! ",
                    !locationSelectorPage.isUpperFieldNavigationLoaded() , false);
            SimpleUtils.assertOnFail("News menu tab is not selected Successfully!",
                    dashboardPage.isConsoleNavigationBarBeenSelected(newsMenuTab), false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

}
