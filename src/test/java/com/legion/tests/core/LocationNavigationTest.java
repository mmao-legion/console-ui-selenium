package com.legion.tests.core;

import com.legion.pages.DashboardPage;
import com.legion.pages.LiquidDashboardPage;
import com.legion.pages.LocationSelectorPage;
import com.legion.pages.SchedulePage;
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

import static com.legion.utils.MyThreadLocal.getDriver;

public class LocationNavigationTest extends TestBase {

    private static String nyCentralLocation = "NY CENTRAL";
    private static String austinDownTownLocation = "AUSTIN DOWNTOWN";
    private static String location00127 = "00127";
    private static String location00364 = "00364";

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
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
                locationSelectorPage.changeLocationDirect(austinDownTownLocation);
            } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
                locationSelectorPage.changeLocationDirect(location00364);
            }
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
                locationSelectorPage.changeLocationDirect(nyCentralLocation);
            } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
                locationSelectorPage.changeLocationDirect(location00127);
            }
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Forecast.getValue());
            if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))){
                locationSelectorPage.changeLocationDirect(austinDownTownLocation);
            } else if (getDriver().getCurrentUrl().contains(propertyMap.get("CinemarkWkdy_Enterprise"))) {
                locationSelectorPage.changeLocationDirect(location00364);
            }
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
