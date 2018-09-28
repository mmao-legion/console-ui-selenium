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
		@TestName(description = "LEG-4947: should be able to convert to open shift for Current date")
	    @Test(dataProvider = "browsers")
	    public void shouldConvertToOpenShiftOption(String browser, String version, String os, String pageobject)
	            throws Exception
	    {
			SimpleUtils.pass("Login to leginTech Successfully");
			SimpleUtils.pass("Successfully opened the Schedule app");
			SimpleUtils.pass("Successfully Opened a Schedule of Present day in Day view");
			SimpleUtils.pass("Successfully created shift using Assign team member option");
			SimpleUtils.pass("Click on edit button");
			SimpleUtils.fail("Convert to Open shift option is not coming for the shift created in previous step",false);
			
	    }

}
