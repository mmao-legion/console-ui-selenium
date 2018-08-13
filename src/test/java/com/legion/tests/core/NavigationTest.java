package com.legion.tests.core;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Reporter;
import com.legion.pages.LoginPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.TeamPage;
import com.legion.pages.DashboardPage;
import com.legion.tests.TestBase;
import com.legion.utils.JsonUtil;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;


/**
 * Manideep
 */

public class NavigationTest extends TestBase {
    private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");

    @Test(dataProvider = "browsers")
    public void navigationAsLegionAdmin(String browser, String version, String os, String pageobject)

            throws Exception {
        this.createDriver(browser, version, os, pageobject);
        WebDriver driver = getWebDriver();
        LoginPage loginPage = pageFactory.createConsoleLoginPage(driver);
        loginPage.visitPage(propertyMap);
        /* Aug 03-The below line is commented by Zorang Team and new line is added as required */
        //loginPage.goToDashboardHome(); 
        loginPage.goToDashboardHome(propertyMap);
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage(driver);
        dashboardPage.goToToday();
        AssertJUnit.assertTrue("Invalid today",
                dashboardPage.isToday());


        TeamPage teamPage = pageFactory.createConsoleTeamPage(driver);
        teamPage.goToTeam();
        if (teamPage.isTeam()) {
            Reporter.log("Team Page Loaded Successfully!");
        }

        teamPage.goToCoverage();
        if (teamPage.isCoverage()) {
            Reporter.log("Team Page - Coverage Section Loaded Successfully!");
        }

        SchedulePage schedulePage = pageFactory.createConsoleSchedulePage(driver);
        schedulePage.gotoToSchedulePage();
        AssertJUnit.assertTrue("Schedule Page Loaded Successfully", schedulePage.isSchedule());
        schedulePage.goToProjectedSales();
        AssertJUnit.assertTrue("ProjectedSales Page Loaded Successfully", schedulePage.isProjectedSales());
        schedulePage.goToStaffingGuidance();
        AssertJUnit.assertTrue("StaffingGuidance Page Loaded Successfully", schedulePage.isStaffingGuidance());
        schedulePage.goToSchedule();
        AssertJUnit.assertTrue("Schedule Page Loaded Successfully", schedulePage.isSchedule());
        Reporter.log("Schedule Page - Navigation sales, guidance and schedule finish Successfully!");
    }


}