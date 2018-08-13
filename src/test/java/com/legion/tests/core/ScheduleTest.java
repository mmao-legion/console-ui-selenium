package com.legion.tests.core;

import com.legion.pages.LoginPage;
import com.legion.pages.SchedulePage;
import com.legion.tests.TestBase;
import com.legion.utils.JsonUtil;
import org.openqa.selenium.WebDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * Manideep
 */
public class ScheduleTest extends TestBase{
    private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
    //todo refactor test method so first 7 lines to do driver init & login can be shared by all Console tests
    @Test(dataProvider = "browsers")
    public void shouldEditPublishedSchedule (String browser, String version, String os, String pageobject)
            throws Exception
    {
        this.createDriver(browser, version, os, pageobject);
        WebDriver driver = getWebDriver();
        LoginPage loginPage = pageFactory.createConsoleLoginPage(driver);
        loginPage.visitPage(propertyMap);
        loginPage.goToDashboardHome(propertyMap);
        SchedulePage schedulePage = pageFactory.createConsoleSchedulePage(driver);
        schedulePage.gotoToSchedulePage();
        AssertJUnit.assertTrue("Schedule Page Loaded Successfully", schedulePage.isSchedule());
        schedulePage.editWeeklySchedule(propertyMap);
        //todo fix failure due to waiting on Edit button on a week without any schedule and add assertion
    }
}
