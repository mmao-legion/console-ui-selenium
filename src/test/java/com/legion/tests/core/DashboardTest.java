package com.legion.tests.core;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.legion.pages.AnalyticsPage;
import com.legion.pages.ControlsPage;
import com.legion.pages.DashboardPage;
import com.legion.pages.LoginPage;
import com.legion.pages.core.ConsoleControlsPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

public class DashboardTest extends TestBase{
	
	private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
    
	@Override
	@BeforeMethod()
  	public void firstTest(Method testMethod, Object[] params) throws Exception{
	  this.createDriver((String)params[0],"69","Window");
      visitPage(testMethod);
      loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
    }
	
	@Automated(automated ="Automated")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "LEG-4961: Empty Dashboard issue ")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void navigateToDashboardFromGlobalSettingInternalAdmin(String username, String password, String browser, String location) throws Throwable { 
    	DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
    	ControlsPage consoleControlsPage = pageFactory.createConsoleControlsPage();
    	consoleControlsPage.gotoControlsPage();
    	dashboardPage.verifySuccessfulNavToDashboardnLoading();
    	consoleControlsPage.gotoControlsPage();
    	consoleControlsPage.clickGlobalSettings();
    	dashboardPage.verifySuccessfulNavToDashboardnLoading();    	
    }
    
    @Automated(automated ="Manual")
	@Owner(owner = "Gunjan")
    @Enterprise(name = "Coffee2_Enterprise")
	@TestName(description = "LEG-5231: Team Lead Should not see Today's Forecast and Projected Demand Graph present in Dashboard Section")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
    public void todayForecastAndProjectedDemandGraphTeamLead(String username, String password, String browser, String location) throws Exception { 
    	SimpleUtils.pass("Login into LegionCooffee2 Application Successfully!");
    	SimpleUtils.pass("Navigate to Dashboard Page Successfully!");
    	SimpleUtils.pass("assert Today's Forecast and Projected Demand Graph should not be present for Team lead and Team member");	
    }  
    
}
