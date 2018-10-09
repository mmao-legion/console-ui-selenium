package com.legion.tests.core;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.AssertJUnit;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;
import com.legion.pages.LoginPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.TeamPage;
import com.legion.pages.DashboardPage;
import com.legion.tests.TestBase;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

import org.testng.annotations.*;

import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.testframework.ExtentTestManager;


/**
 * Manideep
 */

public class NavigationTest extends TestBase {
    private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");

    @Automated(automated = "Automated")
	@Owner(owner = "Naval")
    @TestName(description = "Verify all the console navigations for Legion web application at high level")
    @Test(dataProvider = "browsers")
    public void legionConsoleNavigationFlow(String browser, String version, String os, String pageobject)

            throws Exception {
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
//        /* Aug 03-The below line is commented by Zorang Team and new line is added as required */
//        //loginPage.goToDashboardHome(); 
        loginPage.goToDashboardHome(propertyMap);
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        dashboardPage.verifyDashboardPageLoadedProperly();
        SchedulePage schedulePage = dashboardPage.goToToday();
        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        teamPage.goToTeam();
        boolean isTeamPage = teamPage.isTeam();
        teamPage.verifyTeamPage(isTeamPage);
        teamPage.goToCoverage();
        boolean isCoveragePage = teamPage.isCoverage();
        teamPage.verifyCoveragePage(isCoveragePage);
        schedulePage.goToSchedulePage();
        schedulePage.goToProjectedSales();
        schedulePage.goToStaffingGuidance();
        schedulePage.goToSchedule();
        ExtentTestManager.getTest().log(Status.PASS,"Schedule Page - Navigation sales, guidance and schedule finish Successfully!"); 
    }
    
    
    @Automated(automated = "Manual")
    @Owner(owner = "Gunjan")
    @TestName(description = "LEG-5112:LocationGroup forecast, guidance and dashboard not loading on 10.09 master build for Carmel Club on LegionCoffee2")
    @Test(dataProvider = "browsers")
    public void DataNotLoadingForCarmelClubLocation(String browser, String version, String os, String pageobject)
           throws Exception
    {
        SimpleUtils.pass("Login to LegionCoffee2 Successfully");
        SimpleUtils.pass("Navigate to Carmel Club location");
        SimpleUtils.fail("assert navigation for carmel club location should load successfully ",false);

    }
    
    
}