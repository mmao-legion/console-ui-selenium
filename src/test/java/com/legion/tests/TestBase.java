package com.legion.tests;


import com.legion.pages.pagefactories.ConsoleWebPageFactory;
import com.legion.pages.pagefactories.PageFactory;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.UnexpectedException;


/**
 * DataProvider for multiple browser combinations.
 * Using SimpleUtils by default since we are not using any remote Selenium server
 * @author Yanming Tang
 *
 */
public class TestBase  {

    protected PageFactory pageFactory = null;

    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();


    @DataProvider(name = "browsers", parallel = true)
    public static Object[][] browserDataProvider(Method testMethod) {
        return JsonUtil.getArraysFromJsonFile("src/test/resources/browsersCfg.json");
    }


    public WebDriver getWebDriver() {
        return webDriver.get();
    }


    protected void createDriver(String browser, String version, String os, String pageobject)
            throws MalformedURLException, UnexpectedException {

        //todo replace Chrome driver initializaton with what Manideep has
        DesiredCapabilities capabilities = null;
        String url = "";

        capabilities = SimpleUtils.initCapabilities(browser, version, os);
        url = SimpleUtils.getURL();
        // Initialize browser
        if (url == null) {
            if ("chrome".equals(browser)) {
                //ytang 08.03 on Chrome 67, need to set the option so that
                // chrome-is-being-controlled-by-automated-test-software message will not be displayed on the browser"
                ChromeOptions options = new ChromeOptions();
                options.addArguments("disable-infobars");
                WebDriver driver = new ChromeDriver(options);

                // Maximize browser
                driver.manage().window().maximize();
                // todo set implicit wait, etc
                webDriver.set(driver);
            }
        }
        else {
            // Launch remote browser and set it as the current thread
            webDriver.set(new RemoteWebDriver(
                    new URL(url),
                    capabilities));
        }

        pageFactory = createPageFactory(pageobject);
    }

    private PageFactory createPageFactory(String pageobject) {

        return new ConsoleWebPageFactory();

    }

    @AfterMethod
    public void tearDown(ITestResult result) throws Exception {
        WebDriver driver = getWebDriver();
        Boolean status = result.isSuccess();
        if (driver != null) {
            // ytang 08.03 required to close the browser after each test method
            driver.quit();
        }
    }

}
