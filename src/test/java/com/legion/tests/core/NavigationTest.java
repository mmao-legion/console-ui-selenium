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

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

import org.testng.annotations.*;

import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;;


/**
 * Manideep
 */

public class NavigationTest extends TestBase {
    private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");

    @Automated(automated =  "/"+"Automated]")
	@Owner(owner = "[Naval")
    @TestName(description = ":Verify all the console navigations for Legion web application at high level")
    @Test(dataProvider = "browsers")
    public void legionConsoleNavigationFlow(String browser, String version, String os, String pageobject)

            throws Exception {
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
//        /* Aug 03-The below line is commented by Zorang Team and new line is added as required */
//        //loginPage.goToDashboardHome(); 
        loginPage.goToDashboardHome(propertyMap);
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        dashboardPage.verifyDashboardPageLoadedProperly();
        dashboardPage.goToToday();
        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        teamPage.goToTeam();
        boolean isTeamPage = teamPage.isTeam();
        teamPage.verifyTeamPage(isTeamPage);
        teamPage.goToCoverage();
        boolean isCoveragePage = teamPage.isCoverage();
        teamPage.verifyCoveragePage(isCoveragePage);
        SchedulePage schedulePage = pageFactory.createConsoleSchedulePage();
        schedulePage.gotoToSchedulePage();
//        AssertJUnit.assertTrue("Schedule Page Loaded Successfully", schedulePage.isSchedule());
        schedulePage.goToProjectedSales();
//        AssertJUnit.assertTrue("ProjectedSales Page Loaded Successfully", schedulePage.isProjectedSales());
        schedulePage.goToStaffingGuidance();
//        AssertJUnit.assertTrue("StaffingGuidance Page Loaded Successfully", schedulePage.isStaffingGuidance());
        schedulePage.goToSchedule();
//        AssertJUnit.assertTrue("Schedule Page Loaded Successfully", schedulePage.isSchedule());
        extentTest.log(Status.PASS,"Schedule Page - Navigation sales, guidance and schedule finish Successfully!");
       
        
    }
}