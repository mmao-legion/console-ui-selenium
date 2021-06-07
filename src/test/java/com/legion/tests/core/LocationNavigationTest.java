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
import java.util.List;
import java.util.Map;

import static com.legion.utils.MyThreadLocal.getDriver;

public class LocationNavigationTest extends TestBase {

    private static String nyCentralLocation = "NY CENTRAL";
    private static String austinDownTownLocation = "AUSTIN DOWNTOWN";
    private static String liftOpsParentLocation = "Lift Ops_Parent";
    private static String location00127 = "00127";
    private static String location00364 = "00364";
    private static String location00808 = "00808";
    private static String hQ = "HQ";
    public static Map<String, String> districtsMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/UpperfieldsForDifferentEnterprises.json");

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
    public void verifySearchingAndSelectingTheLocationOnSMScheduleTabAsInternalAdmin(String browser, String username, String password, String location) {
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
        locationSelectorPage.searchSpecificLocationAndNavigateTo(locationName);

        List<String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
        if (upperFields.length > 1) {
            SimpleUtils.assertOnFail("The selected upperfields is incorrect",
                    selectedUpperFields.get(0).equalsIgnoreCase(locationName)
                            && selectedUpperFields.get(1).equalsIgnoreCase(upperFields[1].trim())
                            && selectedUpperFields.get(2).equalsIgnoreCase(upperFields[0].trim()), false);
        } else {
            SimpleUtils.assertOnFail("The selected upperfields is incorrect",
                    selectedUpperFields.get(0).equalsIgnoreCase(locationName)
                            && selectedUpperFields.get(1).equalsIgnoreCase(upperFields[0].trim())
                            && selectedUpperFields.get(2).equalsIgnoreCase(hQ), false);
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
            locationSelectorPage.changeDistrictDirect(districtName);

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
            locationSelectorPage.changeDistrictDirect(districtName);

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
            locationSelectorPage.changeDistrictDirect(districtName);

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
            locationSelectorPage.searchSpecificDistrictAndNavigateTo(upperFields[1].trim());
        } else {
            locationSelectorPage.searchSpecificDistrictAndNavigateTo(upperFields[0].trim());
        }
        List<String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();

        //Verify the upperfiled should be correct
        //Verify district is selected in the navigation bar
        if (upperFields.length > 1) {
            SimpleUtils.assertOnFail("The selected upperfields is incorrect",
                    selectedUpperFields.get(1).equalsIgnoreCase(upperFields[1].trim())
                            && selectedUpperFields.get(2).equalsIgnoreCase(upperFields[0].trim()), false);
        } else {
            SimpleUtils.assertOnFail("The selected upperfields is incorrect",
                    selectedUpperFields.get(1).equalsIgnoreCase(upperFields[0].trim())
                            && selectedUpperFields.get(2).equalsIgnoreCase(hQ), false);
        }
        //Verify the DM page loaded
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        SimpleUtils.assertOnFail("Schedule page DM page not loaded Successfully!",
                schedulePage.isScheduleDMView(), false);
    }
}
