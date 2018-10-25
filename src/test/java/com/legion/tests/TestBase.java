package com.legion.tests;

import org.testng.annotations.AfterMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
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

import javax.imageio.ImageIO;

import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.FileName;
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

    Date date=new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");  
    //added by Nishant
    public static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
    private static Object[][] legionUsersCredentials =  JsonUtil.getArraysFromJsonFile("src/test/resources/UsersCredentials.json");
    private static ExtentReports extent = ExtentReportManager.getInstance();
//    public abstract void firstTest(Method testMethod, String enterprise) throws Exception;
       
//    protected static Logger log;

    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
    public static ExtentTest extentTest;
   
  //To do Browser and legionTeamCredentials dataProvider should be merged
    
    @DataProvider(name = "browsers", parallel = true)
    public synchronized static Object[][] browserDataProvider(Method testMethod) {
        return JsonUtil.getArraysFromJsonFile("src/test/resources/browsersCfg.json");
    }
    
    
    @DataProvider(name = "usersCredentials", parallel = true)
    public synchronized static Object[][] usersDataProvider(Method testMethod) {
        return JsonUtil.getArraysFromJsonFile("src/test/resources/legionUsersCredentials.json");
    }

    @DataProvider(name = "usersDataCredential", parallel = true)
    public synchronized static Object[][] usersDataCredentialProvider(Method testMethod) {
    	return SimpleUtils.getUsersDataCredential();
    }
    
    @BeforeClass
    protected void init () {
        System.out.println("YYYYY");
        ScreenshotManager.createScreenshotDirIfNotExist();
    }
    

    
    @BeforeMethod(alwaysRun = true)
    protected void initTestFramework(Method method) throws AWTException, IOException {
        String testName = ExtentTestManager.getTestName(method);
        String ownerName = ExtentTestManager.getOwnerName(method);
        String automatedName = ExtentTestManager.getAutomatedName(method);
        String enterpriseName =  SimpleUtils.getEnterprise(method);
        extent.setSystemInfo("Enterprise", enterpriseName);
        List<String> categories =  new ArrayList<String>();
        categories.add(getClass().getSimpleName());
        categories.add(enterpriseName);
        ExtentTestManager.createTest(getClass().getSimpleName() + " - "
            + " " + method.getName() + " : " + testName + ""
            + " [" + ownerName + "/" + automatedName + "]", "", categories);
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
        	if (getDriverType().equals(propertyMap.get("INTERNET_EXPLORER"))) {
                InternetExplorerOptions options = new InternetExplorerOptions()
                        .requireWindowFocus()
                        .ignoreZoomSettings()
                        .introduceFlakinessByIgnoringSecurityDomains();
                options.setCapability("silent", true);
                options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                setDriver(new InternetExplorerDriver(options));
                
            }
            if (getDriverType().equals(propertyMap.get("CHROME"))) {
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
//                try {
//                    setDriver(new RemoteWebDriver(new URL("http://192.168.230.127:4444/wd/hub"),capabilities));
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
            }
            if (getDriverType().equals(propertyMap.get("FIREFOX"))) {
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
	
	//added by Nishant
	
	@AfterMethod(alwaysRun = true)
    protected void tearDown(Method method,ITestResult result) throws IOException {
		
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
		ExtentTestManager.getTest().info("Inside After Method");
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
        	getDriver().manage().window().maximize();
        	getDriver().get(getURL() + "legion/?enterprise=" + getEnterprise() + " ");
        } catch (TimeoutException te) {
            try {
                getDriver().navigate().refresh();
            } catch (TimeoutException te1) {
                SimpleUtils.fail("Page failed to load", false);
            }
        }
    }

    
    public static String displayCurrentURL()
    {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        return (String) executor.executeScript("return document.location.href");
      
    }

    /*
     * Login to Legion With Credential and assert on failure
     */
    public void loginToLegionAndVerifyIsLoginDone(String username, String Password, String location) throws Exception
    {
    	LoginPage loginPage = pageFactory.createConsoleLoginPage();
    	loginPage.loginToLegionWithCredential(username, Password);
    	LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
	    boolean isLoginDone = loginPage.isLoginDone();
	    loginPage.verifyLoginDone(isLoginDone, location);
    }

	public abstract void firstTest(Method testMethod, Object[] params) throws Exception;
		// TODO Auto-generated method stub
	 
}
