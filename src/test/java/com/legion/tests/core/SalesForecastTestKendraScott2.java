package com.legion.tests.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.legion.pages.DashboardPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.SalesForecastPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.tests.testframework.ExtentTestManager;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

public class SalesForecastTestKendraScott2 extends TestBase{

	@Override
	  @BeforeMethod()
	  public void firstTest(Method testMethod, Object[] params) throws Exception{
		  this.createDriver((String)params[0],"69","Window");
	      visitPage(testMethod);
	      loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
	  }
	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "Kendrascott2_Enterprise")
	@TestName(description = "TP-44: Coffee Cups in All Sales Item filter is not showing any data")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
    public void shouldAllSalesItemDisplayEnabledFilter(String username, String password, String browser, String location)
            throws Exception
    {
		SimpleUtils.pass("Login as Store Manager Successfully");
		SimpleUtils.pass("Successfully opened the Schedule app");
		SimpleUtils.pass("Navigate to previous weeks in Projected Sales");
		SimpleUtils.pass("Look for the Actual's value for Coffee Cups filter");
		SimpleUtils.pass("If Actuals for the Coffee Cups has some value then assert the presence of Projected Sales graph");
    }
	
	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "Kendrascott2_Enterprise")
	@TestName(description = "LEG-5192: Sales Guidance Graphs are missing for both Day view and Week view in Projected Sales in LegionTech Env (Location-Toronto)")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
    public void salesForecastGraphMissing(String username, String password, String browser, String location)
            throws Exception
    {
		SimpleUtils.pass("Login into Legiontech application successfully");
		SimpleUtils.pass("Change location to Totonto");
		SimpleUtils.pass("Click on Schedule button");
		SimpleUtils.pass("Click on Projected Sales Tab");
		SimpleUtils.pass("assert Sales Guidance Graphs are missing for both Day view and Week view");
    }
	
	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "Kendrascott2_Enterprise")
	@TestName(description = "LEG-5196: Graphs are not changing in Sales Forecast in day view if user selects any locations from All locations filter")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
    public void salesForecastGraphNotChangingForAllLocationFilter(String username, String password, String browser, String location)
            throws Exception
    {
		SimpleUtils.pass("Login into Legion Coffee application successfully");
		SimpleUtils.pass("Select Location as Bay Area");
		SimpleUtils.pass("Click on Schedule button");
		SimpleUtils.pass("Click on Projected Sales Tab");
		SimpleUtils.pass("Click on Day view and navigate for any week");
		SimpleUtils.fail("Graphs should be changing for each day",false);
    }

	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "Kendrascott2_Enterprise")
	@TestName(description = "LEG-5293: Actuals showing as NA Oct17(Wed) onwards in LegionCoffee")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
    public void smartCardActualsShowingAsNAForPastDate(String username, String password, String browser, String location)
            throws Exception
    {
		SimpleUtils.pass("Login into Legion Coffee application successfully");
		SimpleUtils.pass("Select Location as Bay Area");
		SimpleUtils.pass("Click on Schedule button");
		SimpleUtils.pass("Click on Projected Sales Tab");
		SimpleUtils.pass("Click on Day view select Oct17");
		SimpleUtils.fail("Actuals should not have NA there should be some value for past date",false);
    }
	
	
}
