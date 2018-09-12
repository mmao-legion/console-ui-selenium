package com.legion.tests.core;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;
import com.legion.pages.LoginPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.TeamPage;
import com.legion.pages.UserAuthorizationPage;
import com.legion.pages.BasePage;
import com.legion.pages.DashboardPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.TestName;
import com.legion.utils.JsonUtil;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;


/**
 * Manideep
 */

public class UserAuthorizationTest extends TestBase {
    private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
    private static Object[][] userDetails = JsonUtil.getArraysFromJsonFile("src/test/resources/legionUsersCredentials.json");
//	WebDriver driver = null;
	LoginPage loginPage = null;

	@TestName(description = "userAuthorizationTest (Verify User Authorization flow)")
    @Test(dataProvider = "browsers", priority = 1)
    public void userAuthorizationTest(String browser, String version, String os, String pageobject) throws Exception{
//		driver = getWebDriver();
		loginPage = pageFactory.createConsoleLoginPage();
	    TestBase.extentTest.assignCategory("User Authorization");
        for (Object[] user : userDetails) {
			String userName = (String) user[0];
			String password = (String) user[1];
			String userTitle = (String) user[2];
			loginPage.loginToLegionWithCredential(propertyMap, userName, password);
	        if(loginPage.isLoginDone())
	        {
	        	UserAuthorizationPage userAuthorizationPage = pageFactory.createConsoleUserAuthorizationPage();
	        	userAuthorizationPage.findAllVisibleMenu();
	        	loginPage.logOut();
	        }
	        else
	        {
	        	TestBase.extentTest.log(Status.INFO,"Invalid Credentials for the User: '"+userName+"'");
	  
	        }
	        
	       
		}
        
    
        
    }
}