package com.legion.tests.core;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

public class TeamTest extends TestBase{
	
	private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
    
	 @Automated(automated = "Manual")
	 @Owner(owner = "Gunjan")
	 @TestName(description = "LEG-4978: In Team Page ,Coverage section is not displayed for LegionTech")
	 @Test(dataProvider = "browsers")
	 public void coverageForTeamPageNotWorking(String browser, String version, String os, String pageobject)
	          throws Exception
	 {
	       SimpleUtils.pass("Login to leginTech Successfully");
	       SimpleUtils.pass("Successfully opened the Team Page");
	       SimpleUtils.pass("Click on Coverage tab");
	       SimpleUtils.fail("assert coverage page should load and show data",false);

	  }
 
}
