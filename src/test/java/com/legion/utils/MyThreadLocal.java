package com.legion.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.legion.tests.TestBase;

public class MyThreadLocal {
	
	public static final ThreadLocal<EventFiringWebDriver> driver = new ThreadLocal<>();
	public static final ThreadLocal<File> file = new ThreadLocal<>();
	public static final ThreadLocal<String> loc = new ThreadLocal<>();
	public static final ThreadLocal<Integer> screenNum = new ThreadLocal<>();
	public static final ThreadLocal<HashMap<ITestResult, List<Throwable>>> verificationFailuresMap = new ThreadLocal<>();
	public static final ThreadLocal<String> driver_type = new ThreadLocal<>();
	public static final ThreadLocal<String> environment = new ThreadLocal<>();
	public static final ThreadLocal<String> url = new ThreadLocal<>();
	public static final ThreadLocal<File> ieDriver = new ThreadLocal<>();
	public static final ThreadLocal<File> chromeDriver = new ThreadLocal<>();
	public static final ThreadLocal<Boolean> browserNeeded = new ThreadLocal<>();
	public static final ThreadLocal<String> currentTestMethod = new ThreadLocal<>();
	public static final ThreadLocal<HashMap<String, String>> method = new ThreadLocal<>();
	public static final ThreadLocal<Boolean> beforeInvocationCalled = new ThreadLocal<>();
	public static final ThreadLocal<String> enterprise = new ThreadLocal<>();
	public static final ThreadLocal<Method> currentMethod = new ThreadLocal<>();
	public static final ThreadLocal<Integer> totalSuiteTestCases = new ThreadLocal<>();
	public static final ThreadLocal<Integer> totalSuiteTestCounter = new ThreadLocal<>();
	public static final ThreadLocal<String> sessionID = new ThreadLocal<>();
	public static final ThreadLocal<String> sessionTimestamp = new ThreadLocal<>();
	public static final ThreadLocal<String> version = new ThreadLocal<>();
	public static final ThreadLocal<String> os = new ThreadLocal<>();
	public static final ThreadLocal<String> testMethodName = new ThreadLocal<>();
	public static void setCurrentTestMethodName(String value) { testMethodName.set(value); }
    public static String getCurrentTestMethodName() { return testMethodName.get(); }
    public static ThreadLocal<String> consoleName = new ThreadLocal<>();
    public static final ThreadLocal<Integer> scheduleHoursStartTime = new ThreadLocal<>();
    public static final ThreadLocal<Integer> scheduleHoursEndTime = new ThreadLocal<>();

	public static void setScheduleHoursStartTime(Integer value) { scheduleHoursStartTime.set(value); }

	public static Integer getScheduleHoursStartTime() { return scheduleHoursStartTime.get(); }
	public static void setScheduleHoursEndTime(Integer value) { scheduleHoursEndTime.set(value); }
	public static Integer getScheduleHoursEndTime() { return scheduleHoursEndTime.get(); }
		
	public static void setTotalSuiteTestCases(Integer value) {
		totalSuiteTestCases.set(value);
	}

	public static Integer getTotalSuiteTestCases() {
		return totalSuiteTestCases.get();
	}

	public static void setTotalSuiteTestCounter(Integer value) {
		totalSuiteTestCounter.set(value);
	}

	public static Integer getTotalSuiteTestCounter() {
		return totalSuiteTestCounter.get();
	}

	public static void setCurrentMethod(Method value) {
		currentMethod.set(value);
	}

	public static Method getCurrentMethod() {
		return currentMethod.get();
	}

	public static void setBeforeInvocationCalled(Boolean value) {
		beforeInvocationCalled.set(value);
	}

	public static Boolean getBeforeInvocationCalled() {
		return beforeInvocationCalled.get();
	}

	public static void setMethod(HashMap<String, String> methodMap) {
		method.set(methodMap);
	}

	public static HashMap<String, String> getMethod() {
		return method.get();
	}

	public static void setCurrentTestMethod(String value) {
		currentTestMethod.set(value);
	}

	public static String getCurrentTestMethod() {
		return currentTestMethod.get();
	}

	public static void setBrowserNeeded(boolean _browserNeeded) {
		browserNeeded.set(_browserNeeded);
	}

	public static boolean getBrowserNeeded() {
		return browserNeeded.get();
	}

	public static void setEnvironment(String _environment) {
		environment.set(_environment);
	}

	public static String getEnvironment() {
		return environment.get();
	}

	public static void setChromeDriver(File _file) {
		chromeDriver.set(_file);
	}

	public static File getChromeDriver() {
		return chromeDriver.get();
	}

	public static void setIEDriver(File _file) {
		ieDriver.set(_file);
	}

	public static File getIEDriver() {
		return ieDriver.get();
	}

	public static void setURL(String _url) {
		url.set(_url);
	}

	public static String getURL() {
		return url.get();
	}
	
	public static void setLoc(String _loc) {
		loc.set(_loc);
	}

	public static String getLoc() {
		return loc.get();
	}

	public static void setDriverType(String driverType) {
		driver_type.set(driverType);
		MyThreadLocal.setVerificationMap(new HashMap<ITestResult, List<Throwable>>());
	}

	public static String getDriverType() {
		return driver_type.get();
	}

	public static void setVerificationMap(HashMap<ITestResult, List<Throwable>> vMap) {
		verificationFailuresMap.set(vMap);
	}

	public static HashMap<ITestResult, List<Throwable>> getVerificationMap() {
		return verificationFailuresMap.get();
	}

	public static void setFile(File _file) {
		file.set(_file);
	}

	public static File getFile() {
		return file.get();
	}

	public static void setScreenNum(int _screenNum) {
		screenNum.set(_screenNum);
	}

	public static int getScreenNum() {
		return screenNum.get();
	}

	public static void setDriver(EventFiringWebDriver _driver) {
		driver.set(_driver);
	}

	public static void setDriver(WebDriver _driver) {
		driver.set(new EventFiringWebDriver(_driver));
	}

	public static EventFiringWebDriver getDriver() {
		return driver.get();
	}

	public static void setEnterprise(String _enterprise) {
		enterprise.set(_enterprise);
	}

	public static String getEnterprise() {
		return enterprise.get();
	}
	
	public static void setSessionID(String _sessionID) {
		sessionID.set(_sessionID);
	}

	public static String getSessionID() {
		return sessionID.get();
	}
	
	public static void setSessionTimestamp(String _sessionTimestamp) {
		sessionTimestamp.set(_sessionTimestamp);
	}

	public static String getSessionTimestamp() {
		return sessionTimestamp.get();
	}
	
	public static void setVersion(String _version) {
		version.set(_version);
	}

	public static String getVersion() {
		return version.get();
	}
	
	public static void setOS(String _os) {
		os.set(_os);
	}

	public static String getOS() {
		return os.get();
	}
	
	
	//set screenshot Console name
	public static void setScreenshotConsoleName(String screenshotFolder){
		consoleName.set(screenshotFolder);
	}

	//get screenshot Console name
	public static String getScreenshotConsoleName(){
		return consoleName.get();
	}

	

}
