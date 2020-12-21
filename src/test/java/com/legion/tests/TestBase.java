package com.legion.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.jayway.restassured.response.Response;
import com.legion.pages.LocationSelectorPage;
import com.legion.pages.LoginPage;
import com.legion.pages.pagefactories.ConsoleWebPageFactory;
import com.legion.pages.pagefactories.PageFactory;
import com.legion.pages.pagefactories.mobile.MobilePageFactory;
import com.legion.pages.pagefactories.mobile.MobileWebPageFactory;
import com.legion.test.testrail.APIException;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.testframework.*;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.json.JSONException;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Optional;
import org.testng.annotations.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.UnexpectedException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.given;
import static com.legion.utils.MyThreadLocal.*;
import static com.legion.utils.MyThreadLocal.getDriver;

//import org.apache.log4j.Logger;


/**
 * DataProvider for multiple browser combinations.
 * Using SimpleUtils by default since we are not using any remote Selenium server
 * @author Yanming Tang
 *
 */

public abstract class TestBase {

    protected PageFactory pageFactory = null;
    protected MobilePageFactory mobilePageFactory = null;
    String TestID = null;
//  public static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
    public static Map<String, String> propertyMap = SimpleUtils.getParameterMap();
    public static Map<String, String> districtsMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/DistrictsForDifferentEnterprises.json");
    private static ExtentReports extent = ExtentReportManager.getInstance();
    static HashMap<String,String> testRailCfg = JsonUtil.getPropertiesFromJsonFile("src/test/resources/TestRailCfg.json");
    public static AndroidDriver<MobileElement> driver;
    public static String versionString;
    public static int version;
    public static  int flagForTestRun = 0;
    public String enterpriseName;
    public static String pth=System.getProperty("user.dir");
    public static String reportFilePath=pth+"/Reports/";
    public static String screenshotFilePath=pth+"/screenshots/";
    public static String excelData=pth+"/TestData/";
    public static String apkpath=pth+"/Resources";
    public static AppiumDriverLocalService service;
    private static AppiumServiceBuilder builder;
    public static final int TEST_CASE_PASSED_STATUS = 1;
    public static final int TEST_CASE_FAILED_STATUS = 5;

    @Parameters({ "platform", "executionon", "runMode","testRail","testSuiteName","testRailRunName"})
    @BeforeSuite
    public void startServer(@Optional String platform, @Optional String executionon,
                            @Optional String runMode, @Optional String testRail, @Optional String testSuiteName, @Optional String testRailRunName, ITestContext context) throws Exception {
        MyThreadLocal.setTestSuiteID(testRailCfg.get("TEST_RAIL_SUITE_ID"));
        MyThreadLocal.setTestRailRunName(testRailRunName);
        if (MyThreadLocal.getTestCaseIDList()==null){
            MyThreadLocal.setTestCaseIDList(new ArrayList<Integer>());
        }
        if(platform!= null && executionon!= null && runMode!= null){
            if (platform.equalsIgnoreCase("android") && executionon.equalsIgnoreCase("realdevice")
                    && runMode.equalsIgnoreCase("mobile") || runMode.equalsIgnoreCase("mobileAndWeb")){
                startServer();
                mobilePageFactory = createMobilePageFactory();
            } else{
                Reporter.log("Script will be executing only for Web");
            }
        }else{
            Reporter.log("Script will be executing only for Web");
        }
        if(testRail!=null && testRail.equalsIgnoreCase("yes")){
            setTestRailReporting("Y");
        }
    }

    // Set the Desired Capabilities to launch the app in Andriod mobile
    public static void launchMobileApp() throws Exception{
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", propertyMap.get("deviceName"));
        caps.setCapability("platformName", "Android");
        caps.setCapability("noReset",true);
        caps.setCapability("platformVersion", propertyMap.get("platformVersion"));
        caps.setCapability("autoAcceptAlerts", true);
        caps.setCapability("appPackage", "co.legion.client");
        caps.setCapability("appActivity", "activities.LegionSplashActivity");
        caps.setCapability("newCommandTimeout", "360");
        setAndroidDriver( new AndroidDriver<MobileElement>(new URL("https://127.0.0.1:4723/wd/hub"), caps));
        getAndroidDriver().manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
        Thread.sleep(10000);
        ExtentTestManager.getTest().log(Status.PASS, "Launched Mobile Application Successfully!");
    }

    @BeforeClass
    protected void init () {
        ScreenshotManager.createScreenshotDirIfNotExist();
    }

    @BeforeMethod(alwaysRun = true)
    protected void initTestFramework(Method method, ITestContext context) throws AWTException, IOException, APIException, JSONException {
        Date date=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        String testName = ExtentTestManager.getTestName(method);
        String ownerName = ExtentTestManager.getOwnerName(method);
        String automatedName = ExtentTestManager.getAutomatedName(method);
        String enterpriseName =  SimpleUtils.getEnterprise(method);
        String platformName =  ExtentTestManager.getMobilePlatformName(method);
//        int sectionId = ExtentTestManager.getTestRailSectionId(method);
        String testRunPhaseName = ExtentTestManager.getTestRunPhase(method);
        List<String> categories =  new ArrayList<String>();
        categories.add(getClass().getSimpleName());
//        categories.add(enterpriseName);
        List<String> enterprises =  new ArrayList<String>();
        enterprises.add(enterpriseName);
        ExtentTestManager.createTest(getClass().getSimpleName() + " - "
                + " " + method.getName() + " : " + testName + ""
                + " [" + ownerName + "/" + automatedName + "/" + platformName + "]", "", categories);
        extent.setSystemInfo(method.getName(), enterpriseName.toString());
        //setTestRailRunId(0);
        if (MyThreadLocal.getTestRailRunId()==null){
            setTestRailRunId(0);
        }
        List<Integer> testRailId =  new ArrayList<Integer>();
        setTestRailRun(testRailId);

        if(getTestRailReporting()!=null){
            SimpleUtils.addNUpdateTestCaseIntoTestRail(testName,context);
        }
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
        url = SimpleUtils.getURL();
        // Initialize browser
        if (propertyMap.get("isGridEnabled").equalsIgnoreCase("false")) {
            capabilities = SimpleUtils.initCapabilities(getDriverType(), getVersion(), getOS());
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
                System.setProperty("webdriver.chrome.driver", propertyMap.get("CHROME_DRIVER_PATH"));
                ChromeOptions options = new ChromeOptions();
                if (propertyMap.get("isHeadlessBrowser").equalsIgnoreCase("true")) {
//                    options.addArguments("headless");
                    options.addArguments("--headless", "--disable-gpu", "--no-sandbox", "--disable-setuid-sandbox", "--disable-dev-shm-usage");
                    options.addArguments("window-size=1200x600");
                    runScriptOnHeadlessOrBrowser(options);
                } else {
                    runScriptOnHeadlessOrBrowser(options);
                }

            }
            if (getDriverType().equalsIgnoreCase(propertyMap.get("FIREFOX"))) {
                System.setProperty("webdriver.gecko.driver", propertyMap.get("FIREFOX_DRIVER_PATH"));
                FirefoxProfile profile = new FirefoxProfile();
                profile.setAcceptUntrustedCertificates(true);
                FirefoxOptions options = new FirefoxOptions();
                options.setProfile(profile);
                setDriver(new FirefoxDriver(options));
            }

            pageFactory = createPageFactory();
            LegionWebDriverEventListener webDriverEventListener = new LegionWebDriverEventListener();
            getDriver().register(webDriverEventListener);

        } else {
            // Launch remote browser and set it as the current thread
            createRemoteChrome(url);
        }
        }


    private void createRemoteChrome(String url){
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserName", "chrome");
//        caps.setCapability("version", "5.4.0-1029-aws");
        caps.setCapability("platform", "LINUX");

        caps.setCapability("network", true);
        caps.setCapability("visual", true);
        caps.setCapability("video", true);
        caps.setCapability("console", true);

//        caps.setCapability("selenium_version","3.141.59");
        caps.setCapability("chrome.driver","87.0");
        Assert.assertNotNull(url,"Error grid url is not configured, please review it in envCFg.json file and add it.");
        try {
            setDriver(new RemoteWebDriver(new URL(url),caps));

            pageFactory = createPageFactory();
            LegionWebDriverEventListener webDriverEventListener = new LegionWebDriverEventListener();
            getDriver().register(webDriverEventListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PageFactory createPageFactory() {
        return new ConsoleWebPageFactory();
    }

    private MobilePageFactory createMobilePageFactory() {
        return new MobileWebPageFactory();
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
//        stopServer();
    }


    public static void visitPage(Method testMethod){

        setEnvironment(propertyMap.get("ENVIRONMENT"));
        Enterprise e = testMethod.getAnnotation(Enterprise.class);
        String enterpriseName = null;
        if (System.getProperty("enterprise")!=null) {
            enterpriseName = System.getProperty("enterprise");
        }else if(e != null ){
            enterpriseName = SimpleUtils.getEnterprise(e.name());
        }else{
            enterpriseName = SimpleUtils.getDefaultEnterprise();
        }
        setEnterprise(enterpriseName);
        switch (getEnvironment()){
            case "QA":
                if (System.getProperty("env")!=null) {
                    setURL(System.getProperty("env"));
                }else {
                    setURL(propertyMap.get("QAURL"));
                }
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
            getDriver().manage().window().maximize();

        } catch (TimeoutException te) {
            try {
                getDriver().navigate().refresh();
            } catch (TimeoutException te1) {
                SimpleUtils.fail("Page failed to load", false);
            }
        } catch (WebDriverException we) {
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
        loginPage.verifyNewTermsOfServicePopUp();
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        changeDistrictAccordingToEnterprise(locationSelectorPage);
        locationSelectorPage.changeLocation(location);
        boolean isLoginDone = loginPage.isLoginDone();
        loginPage.verifyLoginDone(isLoginDone, location);
    }

    private void changeDistrictAccordingToEnterprise(LocationSelectorPage locationSelectorPage) {
        if (getDriver().getCurrentUrl().contains(propertyMap.get("Coffee_Enterprise"))) {
            locationSelectorPage.changeDistrict(districtsMap.get("Coffee_Enterprise"));
        }
        if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
            locationSelectorPage.changeDistrict(districtsMap.get("KendraScott2_Enterprise"));
        }
        if (getDriver().getCurrentUrl().contains(propertyMap.get("OP_Enterprise"))) {
            locationSelectorPage.changeDistrict(districtsMap.get("OP_Enterprise"));
        }
        if (getDriver().getCurrentUrl().contains(propertyMap.get("DGStage_Enterprise"))) {
            locationSelectorPage.changeDistrict(districtsMap.get("DGStage_Enterprise"));
        }
    }

    public abstract void firstTest(Method testMethod, Object[] params) throws Exception;
    // TODO Auto-generated method stub


    // Method for Start the appium server and arguments should be appium installation path upto node.exe and appium.js
    public static void appiumServerStart(String appiumServerPath, String appiumJSPath){
        service=AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingDriverExecutable(new File(appiumServerPath))
                .withAppiumJS(new File(appiumJSPath)));
    }

    //Start appium programatically
    public static void startServer() {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("noReset", "true");
        cap.setCapability("autoGrantPermissions", true);
        //Build the Appium service
        builder = new AppiumServiceBuilder();
        builder.withIPAddress("127.0.0.1");
        builder.usingPort(4723);
        builder.withCapabilities(cap);
        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
        //Start the server with the builder
        service = AppiumDriverLocalService.buildService(builder);
        service.start();
    }

    //Stop appium programatically
    public void stopServer() {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("taskkill /F /IM node.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void runScriptOnHeadlessOrBrowser(ChromeOptions options){
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

    public  static void switchToNewWindow() {
        String winHandleBefore =getDriver().getWindowHandle();
        for(String winHandle : getDriver().getWindowHandles()) {
            if (winHandle.equals(winHandleBefore)) {
                continue;
            }
            getDriver().switchTo().window(winHandle);
            break;
        }
    }

    public static void disableSwitch(String switchName,String enterpriseName) {

        Response response = given().params("enterpriseName","op","sourceSystem","legion","passwordPlainText","AutoTesting.AD1","userName","AutoTesting.AD1")
                .when().get("https://staging-enterprise.dev.legion.work/legion/authentication/login").then().statusCode(200).extract().response();
        String sessionId = response.header("sessionid");
        //get ABSwitch to confirm the switch is on or off
        Response response2= given().log().all().header("sessionId",sessionId).param("switchName", switchName).when().get("https://staging-enterprise.dev.legion.work/legion/business/queryABSwitch").then().log().all().extract().response();
        String enabled = response2.jsonPath().get("records.enabled[0]").toString();

        if (enabled.equals("true")) {

            //disable location group switch
            HashMap<String, Object> recordContext = new HashMap<>();
            recordContext.put( "name", switchName);
            recordContext.put("resource", "Business");
            recordContext.put("value", enterpriseName);
            recordContext.put("controlValue", "");
            recordContext.put("enabled", false);
            recordContext.put("adminOnly", false);
            HashMap<String, Object>  jsonAsMap = new HashMap<>();
            jsonAsMap.put("level", "Enterprise");
            jsonAsMap.put("valid", true);
            jsonAsMap.put("record",recordContext);
            Response responseAfterDisable= given().log().all().headers("sessionId",sessionId,"Content-Type","application/json").body(jsonAsMap)
                    .when().post("https://staging-enterprise.dev.legion.work/legion/business/updateABSwitch").then().log().all().extract().response();
            responseAfterDisable.then().statusCode(200);

        }
    }

    public static void enableSwitch(String switchName,String enterpriseName) {
        Response response = given().params("enterpriseName","dgstage","sourceSystem","legion","passwordPlainText","admin2.a","userName","admin2.a")
                .when().get("https://rc-enterprise.dev.legion.work/legion/authentication/login").then().statusCode(200).extract().response();
        String sessionId = response.header("sessionid");
        //get ABSwitch to confirm the switch is on or off
        Response response2= given().log().all().header("sessionId",sessionId).param("switchName", switchName).when().get("https://rc-enterprise.dev.legion.work/legion/business/queryABSwitch").then().log().all().extract().response();
        String enabled = response2.jsonPath().get("records.enabled[0]").toString();

        if (enabled.equals("false")) {

            //disable location group switch
            HashMap<String, Object> recordContext = new HashMap<>();
            recordContext.put( "name", switchName);
            recordContext.put("resource", "Business");
            recordContext.put("value", enterpriseName);
            recordContext.put("controlValue", "");
            recordContext.put("enabled", true);
            recordContext.put("adminOnly", false);
            HashMap<String, Object>  jsonAsMap = new HashMap<>();
            jsonAsMap.put("level", "Enterprise");
            jsonAsMap.put("valid", true);
            jsonAsMap.put("record",recordContext);
            Response responseAfterDisable= given().log().all().headers("sessionId",sessionId,"Content-Type","application/json").body(jsonAsMap)
                    .when().post("https://rc-enterprise.dev.legion.work/legion/business/updateABSwitch").then().log().all().extract().response();
            responseAfterDisable.then().statusCode(200);

        }
    }


}