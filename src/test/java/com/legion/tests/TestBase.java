package com.legion.tests;

import org.testng.annotations.AfterMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.legion.pages.BasePage;
import com.legion.pages.LocationSelectorPage;
import com.legion.pages.LoginPage;
import com.legion.pages.pagefactories.ConsoleWebPageFactory;
import com.legion.pages.pagefactories.PageFactory;
import com.legion.tests.testframework.ExtentReportManager;
import com.legion.tests.testframework.ExtentTestManager;
import com.legion.tests.testframework.LegionTestListener;
import com.legion.tests.testframework.LegionWebDriverEventListener;
import com.legion.tests.testframework.ScreenshotManager;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import static org.testng.AssertJUnit.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.UnexpectedException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.legion.test.testrail.APIClient;
import com.legion.test.testrail.APIException;

import javax.imageio.ImageIO;

import com.legion.tests.annotations.Enterprise;

//import org.apache.log4j.Logger;
import com.legion.tests.annotations.HasDependencies;

import static com.legion.utils.MyThreadLocal.*;


/**
 * DataProvider for multiple browser combinations.
 * Using SimpleUtils by default since we are not using any remote Selenium server
 * @author Yanming Tang
 *
 */

public abstract class TestBase {

    protected PageFactory pageFactory = null;
    String TestID = null;
    public static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json"); 
    private static ExtentReports extent = ExtentReportManager.getInstance();
    public static final int TEST_CASE_PASSED_STATUS = 1;
    public static final int TEST_CASE_FAILED_STATUS = 5;

    @BeforeClass
    protected void init () {
        ScreenshotManager.createScreenshotDirIfNotExist();
    }
    
    @BeforeMethod(alwaysRun = true)
    protected void initTestFramework(Method method) throws AWTException, IOException, APIException, JSONException {
    	Date date=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");  
    	String testName = ExtentTestManager.getTestName(method);
        String ownerName = ExtentTestManager.getOwnerName(method);
        String automatedName = ExtentTestManager.getAutomatedName(method);
        String enterpriseName =  SimpleUtils.getEnterprise(method);
        List<String> categories =  new ArrayList<String>();
        categories.add(getClass().getSimpleName());
//        categories.add(enterpriseName);
        List<String> enterprises =  new ArrayList<String>();
        enterprises.add(enterpriseName);
        ExtentTestManager.createTest(getClass().getSimpleName() + " - "
            + " " + method.getName() + " : " + testName + ""
            + " [" + ownerName + "/" + automatedName + "]", "", categories);
        extent.setSystemInfo(method.getName(), enterpriseName.toString());
        setCurrentMethod(method);
        setBrowserNeeded(true);
        setCurrentTestMethodName(method.getName());
        setSessionTimestamp(date.toString().replace(":", "_").replace(" ", "_"));
        String strDate = formatter.format(date);
        String strDateFinal = strDate.replaceAll(" ", "_");
        setVerificationMap(new HashMap<>());
    }

    protected void createDriver (String browser, String version, String os) throws Exception {
        if (getBrowserNeeded() && browser != null) {
            setDriverType(browser);
            setVersion(version);
            setOS(os);
            createDriver();
        }
    }

    protected void createDriver()
            throws MalformedURLException, UnexpectedException {

        //todo replace Chrome driver initializaton with what Manideep has
        DesiredCapabilities capabilities = null;
        String url = "";
        
        capabilities = SimpleUtils.initCapabilities(getDriverType(), getVersion(), getOS());
        url = SimpleUtils.getURL();
        // Initialize browser
        if (url == null) {
        	if (getDriverType().equalsIgnoreCase(propertyMap.get("INTERNET_EXPLORER"))) {
                InternetExplorerOptions options = new InternetExplorerOptions()
                        .requireWindowFocus()
                        .ignoreZoomSettings()
                        .introduceFlakinessByIgnoringSecurityDomains();
                options.setCapability("silent", true);
                options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                setDriver(new InternetExplorerDriver(options));
                
            }
            if (getDriverType().equalsIgnoreCase(propertyMap.get("CHROME"))) {
            	System.setProperty("webdriver.chrome.driver",propertyMap.get("CHROME_DRIVER_PATH"));
            	ChromeOptions options = new ChromeOptions();
        		options.addArguments("disable-infobars");

                options.addArguments("test-type", "new-window", "disable-extensions","start-maximized");
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("password_manager_enabled", false);
                options.setExperimentalOption("prefs", prefs);
                options.addArguments("disable-logging", "silent", "ignore-certificate-errors");
                options.setExperimentalOption("useAutomationExtension", false);
                options.setExperimentalOption("excludeSwitches",
                        Collections.singletonList("enable-automation"));
                options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                options.setCapability(ChromeOptions.CAPABILITY, options);
                options.setCapability("chrome.switches", Arrays.asList("--disable-extensions", "--disable-logging",
                        "--ignore-certificate-errors", "--log-level=0", "--silent"));
                options.setCapability("silent", true);
                System.setProperty("webdriver.chrome.silentOutput", "true");
                setDriver(new ChromeDriver(options));
            }
            if (getDriverType().equalsIgnoreCase(propertyMap.get("FIREFOX"))) {
            	System.setProperty("webdriver.gecko.driver",propertyMap.get("FIREFOX_DRIVER_PATH"));
            	FirefoxProfile profile = new FirefoxProfile();
                profile.setAcceptUntrustedCertificates(true);
                FirefoxOptions options = new FirefoxOptions();
                options.setProfile(profile);
                setDriver(new FirefoxDriver(options));
            }
            
            pageFactory = createPageFactory();
            LegionWebDriverEventListener webDriverEventListener = new LegionWebDriverEventListener();
            getDriver().register(webDriverEventListener);

        }
        else {
            // Launch remote browser and set it as the current thread
        	setDriver(new RemoteWebDriver(
                    new URL(url),
                    capabilities));
        }

       
    }


    private PageFactory createPageFactory() {
        return new ConsoleWebPageFactory();
    }

	@AfterMethod(alwaysRun = true)
    protected void tearDown(Method method,ITestResult result) throws IOException {
		ExtentTestManager.getTest().info("tearDown started");
		if (Boolean.parseBoolean(propertyMap.get("close_browser"))) {
            try {
                getDriver().manage().deleteAllCookies();
                getDriver().quit();
            } catch (Exception exp) {
                Reporter.log("Error closing browser");
            }
        }
		
		if (getVerificationMap() != null) {
            getVerificationMap().clear();
        }
		ExtentTestManager.getTest().info("tearDown finished");
		extent.flush();
    }

	
	public static void visitPage(Method testMethod){
		setEnvironment(propertyMap.get("ENVIRONMENT"));
        Enterprise e = testMethod.getAnnotation(Enterprise.class);
        String enterpriseName = null;
        if (e != null ) {
            enterpriseName = SimpleUtils.getEnterprise(e.name());
        }
        else {
            enterpriseName = SimpleUtils.getDefaultEnterprise();
        }
        setEnterprise(enterpriseName);
		switch (getEnvironment()){
			case "QA":
					setURL(propertyMap.get("QAURL"));
					loadURL();
					break;
			case "DEV":
					setURL(propertyMap.get("DEVURL"));
					loadURL();
					break;
			default:
				ExtentTestManager.getTest().log(Status.FAIL,"Unable to set the URL");
			}
	}

   
    public static void loadURL() {
        try {
        	getDriver().get(getURL() + "legion/?enterprise=" + getEnterprise() + " ");
        } catch (TimeoutException te) {
            try {
                getDriver().navigate().refresh();
            } catch (TimeoutException te1) {
                SimpleUtils.fail("Page failed to load", false);
            }
        }
    }

    /*
     * Login to Legion With Credential and assert on failure
     */
    public synchronized void loginToLegionAndVerifyIsLoginDone(String username, String Password, String location) throws Exception
    {
    	LoginPage loginPage = pageFactory.createConsoleLoginPage();
    	loginPage.loginToLegionWithCredential(username, Password);
    	LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
    	//locationSelectorPage.changeLocation(location);
	    boolean isLoginDone = loginPage.isLoginDone();
	    loginPage.verifyLoginDone(isLoginDone, location);
    }

	public abstract void firstTest(Method testMethod, Object[] params) throws Exception;
		// TODO Auto-generated method stub
	 
}