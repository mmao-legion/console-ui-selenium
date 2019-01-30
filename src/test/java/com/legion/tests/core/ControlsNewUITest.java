package com.legion.tests.core;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.legion.pages.DashboardPage;
import com.legion.pages.ControlsNewUIPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;


public class ControlsNewUITest extends TestBase{
	
	
	public enum dayWeekOrPayPeriodViewType{
		  Next("Next"),
		  Previous("Previous");
			private final String value;
			dayWeekOrPayPeriodViewType(final String newValue) {
	            value = newValue;
	        }
	        public String getValue() { return value; }
	}
	
	public enum dayWeekOrPayPeriodCount{
		Zero(0),
		One(1),
		Two(2),
		Three(3),
		Four(4),
		Five(5);		
		private final int value;
		dayWeekOrPayPeriodCount(final int newValue) {
          value = newValue;
      }
      public int getValue() { return value; }
	}
	
	@Override
	@BeforeMethod
	public void firstTest(Method method, Object[] params) throws Exception {
		this.createDriver((String) params[0], "68", "Linux");
	      visitPage(method);
	      loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
	}
	
	private static HashMap<String, String> controlsLocationDetail = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ControlsPageLocationDetail.json");
	
	//
	
	
	@Automated(automated =  "Automated")
	@Owner(owner = "Naval")
  @Enterprise(name = "KendraScott2_Enterprise")
  @TestName(description = "TP-119: Automation TA module : validate if it is possible to delete a clock in entry.")
  @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
  public void updateUserLocationAsInternalAdmin(String browser, String username, String password, String location)
  		throws Exception {
			
      DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
      SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);      
      ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
      controlsNewUIPage.clickOnControlsConsoleMenu();
      SimpleUtils.assertOnFail("TimeSheet Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
      controlsNewUIPage.clickOnGlobalLocationButton();
      controlsNewUIPage.clickOnControlsCompanyProfileCard();
      
      String companyName = controlsLocationDetail.get("Company_Name");
	  String businessAddress = controlsLocationDetail.get("Business_Address");
	  String city = controlsLocationDetail.get("City");
	  String state = controlsLocationDetail.get("State");
	  String country = controlsLocationDetail.get("Country");
	  String zipCode = controlsLocationDetail.get("Zip_Code");
	  String timeZone = controlsLocationDetail.get("Time_Zone");
	  String website = controlsLocationDetail.get("Website");
	  String firstName = controlsLocationDetail.get("First_Name");
	  String lastName = controlsLocationDetail.get("Last_Name");
	  String email = controlsLocationDetail.get("E_mail");
	  String phone =controlsLocationDetail.get("Phone");
	  
	  controlsNewUIPage.updateUserLocationProfile(companyName, businessAddress, city, state, country, zipCode, timeZone, website,
			  firstName, lastName, email, phone);
	  boolean isUserLocationProfileUpdated = controlsNewUIPage.isUserLocationProfileUpdated(companyName, businessAddress, city, state, country, zipCode,
    		  timeZone, website, firstName, lastName, email, phone);
	  if(isUserLocationProfileUpdated)
		  SimpleUtils.pass("User Location Profile Updated successfully.");
  }
}