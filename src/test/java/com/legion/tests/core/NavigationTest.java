package com.legion.tests.core;

import static com.legion.utils.MyThreadLocal.setCurrentTestMethodName;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.AssertJUnit;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;
import com.legion.pages.AnalyticsPage;
import com.legion.pages.LoginPage;
import com.legion.pages.SalesForecastPage;
import com.legion.pages.ScheduleOverviewPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.StaffingGuidancePage;
import com.legion.pages.TeamPage;
import com.legion.pages.DashboardPage;
import com.legion.tests.TestBase;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.testng.annotations.*;

import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.tests.testframework.ExtentTestManager;

import static com.legion.utils.MyThreadLocal.*;


/**
 * Manideep
 */

public class NavigationTest extends TestBase {
    private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");

    @Override
	@BeforeMethod()
	public void firstTest(Method testMethod, Object[] params) throws Exception{
	  this.createDriver((String)params[0],"69","Window");
      visitPage(testMethod);
      loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
	}
    
    @Automated(automated = "Automated")
	@Owner(owner = "Naval")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify all the console navigations for Legion web application at high level")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void legionConsoleNavigationFlowStoreManager(String username, String password, String browser, String location)

            throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        dashboardPage.verifyDashboardPageLoadedProperly();
        SchedulePage schedulePage = dashboardPage.goToToday();
        schedulePage.goToSchedulePage();
        schedulePage.goToProjectedSales();
        schedulePage.goToStaffingGuidance();
        schedulePage.goToSchedule();
        ExtentTestManager.getTest().log(Status.PASS,"Schedule Page - Navigation sales, guidance and schedule finish Successfully!"); 
    }
    
    
    @Automated(automated = "Automated")
	@Owner(owner = "Naval")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify all the console navigations for Legion web application at high level")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
    public void legionConsoleNavigationFlowInternalAdmin(String username, String password, String browser, String location)

            throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        dashboardPage.verifyDashboardPageLoadedProperly();
        SchedulePage schedulePage = dashboardPage.goToToday();
        schedulePage.goToSchedulePage();
        schedulePage.goToProjectedSales();
        schedulePage.goToStaffingGuidance();
        schedulePage.goToSchedule();
        ExtentTestManager.getTest().log(Status.PASS,"Schedule Page - Navigation sales, guidance and schedule finish Successfully!"); 
    }
    
    @Automated(automated = "Automated")
   	@Owner(owner = "Gunjan")
       @Enterprise(name = "KendraScott2_Enterprise")
       @TestName(description = "TP-144 : Validate navigation to below tabs and loading of data[No spinning icon]")
       @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
       public void legionAppNavigationAllTabsStoreManager(String username, String password, String browser, String location) throws Exception {
    	   AnalyticsPage analyticsPage = pageFactory.createConsoleAnalyticsPage();
    	   analyticsPage.loadAnalyticsTab();
    	   TeamPage teamPage = pageFactory.createConsoleTeamPage();
    	   teamPage.loadTeamTab();
    	   DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
           dashboardPage.isToday();
           ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
           scheduleOverviewPage.loadScheduleOverview();
           SalesForecastPage salesForecastPage = pageFactory.createSalesForecastPage();
           salesForecastPage.loadSalesForecast();
           StaffingGuidancePage staffingGuidancePage = pageFactory.createStaffingGuidancePage();
           staffingGuidancePage.loadStaffingGuidance();
           SchedulePage schedulePage = pageFactory.createConsoleSchedulePage();
           schedulePage.loadSchedule();
           
       }
    
    
    @Automated(automated = "Manual")
    @Owner(owner = "Gunjan")
    @Enterprise(name = "Coffee2_Enterprise")
    @TestName(description = "LEG-5112:LocationGroup forecast, guidance and dashboard not loading on 10.09 master build for Carmel Club on LegionCoffee2")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
    public void DataNotLoadingForCarmelClubLocation(String username, String password, String browser, String location)
           throws Exception
    {
        SimpleUtils.pass("Login to LegionCoffee2 Successfully");
        SimpleUtils.pass("Navigate to Carmel Club location");
        SimpleUtils.pass("assert navigation for carmel club location should load successfully ");

    }
	
    
}