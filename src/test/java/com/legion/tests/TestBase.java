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

//import org.apache.log4j.Logger;
import com.legion.tests.annotations.HasDependencies;

import static com.legion.utils.MyThreadLocal.*;


/**
 * DataProvider for multiple browser combinations.
 * Using SimpleUtils by default since we are not using any remote Selenium server
 * @author Yanming Tang
 *
 */

public class TestBase {

    protected PageFactory pageFactory = null;
    public String targetFinalFile = null;
    protected static String screenshotLocation = null;
    Date date=new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");  
    //added by Nishant
    private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
    
	private static Object[][] legionUsersCredentials =  JsonUtil.getArraysFromJsonFile("src/test/resources/UsersCredentials.json");

    protected boolean screenshotsWanted = Boolean.parseBoolean(propertyMap.get("TAKE_SCREENSHOTS")) ;
    protected String strEnterprise = propertyMap.get("ENTERPRISE");
    public static String activeTabLogin = null;
    public static String activeTabSchedule = null;
    public static String activeTabDashboard = null;
    protected static String screenshotFinalLocation = null;
    protected static String appURL = null;
    private static ExtentReports extent = ExtentReportManager.getInstance();
           
//    protected static Logger log;

    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
    public static ExtentTest extentTest;


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
    
    
    /*
     * add by Naval for Tean Schedule 
     */

    @DataProvider(name = "legionTeamCredentials", parallel = true)
    public synchronized static Object[][] legionTeamCredentialsProvider(Method testMethod) {
        return JsonUtil.getArraysFromJsonFile("src/test/resources/UsersCredentials.json");
    }
    
    //added by Nishant
   
    @Parameters({"browser", "enterprise","environment"})
    @BeforeMethod(alwaysRun = true)
    protected void openBrowser(Method method, @Optional String browser,
                               @Optional String enterprise, @Optional String environment) throws AWTException, IOException {
    	if (environment != null) {
        	setEnvironment(environment);
        } else {
            setEnvironment(propertyMap.get("ENVIRONMENT"));
        }
    	
    	if (enterprise != null) {
            setEnterprise(enterprise);
        } else {
            setEnterprise(strEnterprise);
        }
    	
    	String testName = ExtentTestManager.getTestName(method);
    	String ownerName = ExtentTestManager.getOwnerName(method);
    	String automatedName = ExtentTestManager.getAutomatedName(method);
    	
    	extentTest = ExtentTestManager.createTest(getClass().getSimpleName()  + " - "
    	+" " + method.getName() + " : " + testName + ""
    			+ " [" +ownerName + "/" + automatedName + "]","", getClass().getSimpleName());	
        setCurrentMethod(method);
        setBrowserNeeded(true);
        setCurrentTestMethodName(method.getName());
        setSessionTimestamp(date.toString().replace(":", "_").replace(" ", "_"));
        String strDate = formatter.format(date);  
		String strDateFinal = strDate.replaceAll(" ", "_");
        screenshotLocation = "Screenshots" + File.separator + "Results";
        if (method.getAnnotation(Test.class)!= null
                && method.getAnnotation(Test.class).dependsOnMethods().length == 0) {
            if (getBrowserNeeded() && browser != null) {
            	setVerificationMap(new HashMap<>());
            	setDriverType(browser);
                setVersion(propertyMap.get("VERSION"));
                setOS(propertyMap.get("VERSION"));
                createDriver();
         
            }
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

	public static void initialize(){
    
        switch (getEnvironment()){
			case "QA":
				if(getEnterprise().equalsIgnoreCase(propertyMap.get("Coffee_Enterprise"))){
					setURL(propertyMap.get("QAURL"));
					loadURL();
					break;
				}
				if(getEnterprise().equalsIgnoreCase(propertyMap.get("LegionTech_Enterprise"))){
					setURL(propertyMap.get("QAURL"));
					loadURL();
					break;
				}
			case "DEV":
				if(getEnterprise().equalsIgnoreCase(propertyMap.get("Coffee_Enterprise"))){
					setURL(propertyMap.get("DEVURL"));
					loadURL();
					break;
				}
				if(getEnterprise().equalsIgnoreCase(propertyMap.get("LegionTech_Enterprise"))){
					setURL(propertyMap.get("DEVURL"));
					loadURL();
					break;
				}
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

    
    public static String displayCurrentURL()
    {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        return (String) executor.executeScript("return document.location.href");
      
    }

    /*
     * Login to Legion With Credential and assert on failure
     */
    public void loginToLegionAndVerifyIsLoginDone(String username, String Password) 
    {
    	try {
    		LoginPage loginPage = pageFactory.createConsoleLoginPage();
			loginPage.loginToLegionWithCredential(username, Password);
			LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
			String selectedLocation = locationSelectorPage.getCurrentUserLocation();
			boolean isLoginDone = loginPage.isLoginDone();
			loginPage.verifyLoginDone(isLoginDone, selectedLocation);
    	} catch (Exception e) {
			SimpleUtils.fail("Unable to Login with given credential", false);
		}
    }
    
    public HashMap<String, List<String>> getUserCredentialsAndLocations()
    {
    	HashMap<String, List<String>> userCredentials = new HashMap<String, List<String>>();
    	for(Object[] legionUsersCredential : legionUsersCredentials)
		  {
			  List<String> credentialsAndLocation = new ArrayList<String>();
			  credentialsAndLocation.add((String) legionUsersCredential[0]);
			  credentialsAndLocation.add((String) legionUsersCredential[1]);
			  credentialsAndLocation.add((String) legionUsersCredential[2]);
			  userCredentials.put((String) legionUsersCredential[3], credentialsAndLocation);
		  }
    	return userCredentials;
    }

    
}
