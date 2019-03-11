package com.legion.tests.core;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.legion.pages.DashboardPage;
import com.legion.pages.ProfileNewUIPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;

public class ProfileNewUITest  extends TestBase{
    
	public enum profilePageTabs{
		aboutMe("About Me"),
		myWorkPreferences("My Work Preferences"),
		myTimeOff("My Time Off");
		private final String value;
		profilePageTabs(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
	}
	
	@Override
	@BeforeMethod
	public void firstTest(Method method, Object[] params) throws Exception {
		this.createDriver((String) params[0], "68", "Linux");
	      visitPage(method);
	      loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
	}
	
	@Automated(automated =  "Automated")
	  @Owner(owner = "Naval")
	  @Enterprise(name = "KendraScott2_Enterprise")
	  @TestName(description = "TP-159: Check the loading of all the web elements on Profile tab")
	  @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	  public void validateAllWebElementLoadedAsStoreManager(String browser, String username, String password, String location)
	  		throws Exception {
				
	      DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	      SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);  
	      
	      ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
	      profileNewUIPage.clickOnProfileConsoleMenu();
	      SimpleUtils.assertOnFail("Profile Page not loaded Successfully!",profileNewUIPage.isProfilePageLoaded() , false);
	      
	      String profilePageActiveLocation= profileNewUIPage.getProfilePageActiveLocation();
	      SimpleUtils.pass("Profile Page: Active Location '"+profilePageActiveLocation+"' loaded Successfully.");
	      Thread.sleep(1000);
	      SimpleUtils.pass("Profile Page: Active tab '"+profileNewUIPage.getProfilePageActiveTabLabel()+"' loaded Successfully.");
	      
	      boolean isEditingProfileSection = profileNewUIPage.isEditingProfileSectionLoaded();
	      SimpleUtils.assertOnFail("Profile Page:  Editing Profile Section not loaded.",isEditingProfileSection , true);
	      boolean isPersonalDetailsSection = profileNewUIPage.isPersonalDetailsSectionLoaded();
	      SimpleUtils.assertOnFail("Profile Page:  Personal Details Section not loaded.",isPersonalDetailsSection , true);
	      boolean isChangePasswordButton = profileNewUIPage.isChangePasswordButtonLoaded();
	      SimpleUtils.assertOnFail("Profile Page:  Change Password Button not loaded.",isChangePasswordButton , true);
	      boolean isEngagementDetrailsSection = profileNewUIPage.isEngagementDetrailsSectionLoaded();
	      SimpleUtils.assertOnFail("Profile Page: Engagement Detrails Section not loaded.",isEngagementDetrailsSection , true);
	      boolean isProfileBadgesSection = profileNewUIPage.isProfileBadgesSectionLoaded();
	      SimpleUtils.assertOnFail("Profile Page:  Profile Badges Section not loaded.",isProfileBadgesSection , true);
	      
	      profileNewUIPage.selectProfilePageSubSectionByLabel(profilePageTabs.myWorkPreferences.getValue());
	      if(profileNewUIPage.getProfilePageActiveTabLabel().toLowerCase().
	    		  contains(profilePageTabs.myWorkPreferences.getValue().toLowerCase()))
	      SimpleUtils.pass("Profile Page: Active tab '"+profilePageTabs.myWorkPreferences.getValue()+"' loaded Successfully.");
	      else
	    	  SimpleUtils.fail("Profile Page: '"+profilePageTabs.myWorkPreferences.getValue()+"'Tab not active.", true);
	      
	      boolean isShiftPreferenceSection = profileNewUIPage.isShiftPreferenceSectionLoaded();
	      SimpleUtils.assertOnFail("Profile Page:  Shift Preference Section not loaded on 'My Work Preferences' tab.",
	    		  isShiftPreferenceSection , true);
	  	  boolean isMyAvailabilitySection = profileNewUIPage.isMyAvailabilitySectionLoaded();
	  	  SimpleUtils.assertOnFail("Profile Page:  My Availability Section not loaded on 'My Work Preferences' tab.",
	  			  isMyAvailabilitySection , true);
	  	  
	  	profileNewUIPage.selectProfilePageSubSectionByLabel(profilePageTabs.myTimeOff.getValue());
	      if(profileNewUIPage.getProfilePageActiveTabLabel().toLowerCase().
	    		  contains(profilePageTabs.myTimeOff.getValue().toLowerCase()))
	      SimpleUtils.pass("Profile Page: Active tab '"+profilePageTabs.myTimeOff.getValue()+"' loaded Successfully.");
	      else
	    	  SimpleUtils.fail("Profile Page: '"+profilePageTabs.myTimeOff.getValue()+"'Tab not active.", true);
	      
	      boolean isCreateTimeOffButton = profileNewUIPage.isCreateTimeOffButtonLoaded();
	      SimpleUtils.assertOnFail("Profile Page: Create Time off button on 'My Time Off' tab not loaded.",
	    		  isCreateTimeOffButton , true);
	      
	  	boolean isTimeOffPendingBlock = profileNewUIPage.isTimeOffPendingBlockLoaded();
	  	SimpleUtils.assertOnFail("Profile Page: 'Time Off Pending Block' Section on 'My Time Off' tab not Loaded.",
	  			isTimeOffPendingBlock , true);
	  	
		boolean isTimeOffApprovedBlock = profileNewUIPage.isTimeOffApprovedBlockLoaded();
		SimpleUtils.assertOnFail("Profile Page: 'Time Off Approved Block' Section on 'My Time Off' tab not Loaded.",
				isTimeOffApprovedBlock , true);
		
		boolean isTimeOffRejectedBlock = profileNewUIPage.isTimeOffRejectedBlockLoaded();
		SimpleUtils.assertOnFail("Profile Page: 'Time Off Rejected Block' Section on 'My Time Off' tab not Loaded.",
				isTimeOffRejectedBlock , true);
		
		boolean isTimeOffRequestsSection = profileNewUIPage.isTimeOffRequestsSectionLoaded();
		SimpleUtils.assertOnFail("Profile Page: 'Time Off Requests Section' Section on 'My Time Off' tab not Loaded.",
				isTimeOffRequestsSection , true);
		
		int allTimeOffRequestCount = profileNewUIPage.getAllTimeOffRequestCount();
		SimpleUtils.pass("Profile Page: 'My Time Off' tab '"+allTimeOffRequestCount+"' Time off Requests found.");
	}
}
