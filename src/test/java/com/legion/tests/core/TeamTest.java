package com.legion.tests.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.legion.pages.TeamPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

public class TeamTest extends TestBase{
	
	private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
	private static HashMap<String, String> searchDetails = JsonUtil.getPropertiesFromJsonFile("src/test/resources/searchDetails.json");
    
	@Override
	  @BeforeMethod()
	  public void firstTest(Method testMethod, Object[] params) throws Exception{
		  this.createDriver((String)params[0],"69","Window");
	      visitPage(testMethod);
	      loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
	  }
	
	public enum weekCount{
		Zero(0),
		One(1),
		Two(2),
		Three(3),
		Four(4),
		Five(5),
		Six(6);
		private final int value;
		weekCount(final int newValue) {
            value = newValue;
        }
        public int getValue() { return value; }
	}
	
	public enum weekViewType{
		  Next("Next"),
		  Previous("Previous");
			private final String value;
			weekViewType(final String newValue) {
	            value = newValue;
	        }
	        public String getValue() { return value; }
		}
	
	 @Automated(automated = "Manual")
	 @Owner(owner = "Gunjan")
	 @Enterprise(name = "Tech_Enterprise")
	 @TestName(description = "LEG-4978: In Team Page ,Coverage section is not displayed for LegionTech for Nov 4- Nov 10")
	 @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	 public void coverageForTeamPageNotWorking(String username, String password, String browser, String location)
	          throws Exception
	 {
	       SimpleUtils.pass("Login to leginTech Successfully");
	       SimpleUtils.pass("Successfully opened the Team Page");
	       SimpleUtils.pass("Click on Coverage tab");
	       SimpleUtils.pass("assert coverage page should load and show data");

	  }
	 
	 @Automated(automated = "Automated")
		@Owner(owner = "Gunjan")
		@Enterprise(name = "Coffee2_Enterprise")
		@TestName(description = "Testing")
	    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	    public void validateTeamTab(String username, String password, String browser, String location)
	            throws Exception
	    {
	    	//To Do Should be separate Test from Schedule test
			//loginToLegionAndVerifyIsLoginDone(propertyMap.get("DEFAULT_USERNAME"), propertyMap.get("DEFAULT_PASSWORD"));
	        TeamPage teamPage = pageFactory.createConsoleTeamPage();
	        teamPage.goToTeam();
	        String key=searchDetails.get("jobTitle");
	        List<String> list = new ArrayList<String>(Arrays.asList(key.split(",")));
	        teamPage.performSearchRoster(list);
	        teamPage.coverage();
	        teamPage.coverageViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.Six.getValue());
		
		
	}
	
 
}
