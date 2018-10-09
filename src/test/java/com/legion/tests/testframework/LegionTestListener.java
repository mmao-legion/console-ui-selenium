package com.legion.tests.testframework;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.testng.IAnnotationTransformer;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.ITestAnnotation;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
//import com.legion.utils.ExtentManager;







import com.legion.utils.JsonUtil;

import static com.legion.utils.MyThreadLocal.*;

public class LegionTestListener implements ITestListener,IInvokedMethodListener {
	
	public static ExtentTest test ;
	private static ExtentReports extent = ExtentReportManager.getInstance();
	private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");

	
	@Override
	public void onTestStart(ITestResult result) {  
		String testName = result.getMethod().getMethodName();
		setLoc(testName);
		TestBase.initialize();	
	}

	@Override
	public void onTestSuccess(ITestResult result) {  
        	ExtentTestManager.getTest().log(Status.PASS, MarkupHelper.createLabel("Test case Passed:",ExtentColor.GREEN));
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		
			ExtentTestManager.getTest().log(Status.FAIL, MarkupHelper.createLabel("Test case Failed:",ExtentColor.RED));
			String targetFile = TestBase.takeScreenShot();
	        String screenshotLoc = propertyMap.get("Screenshot_Path") + File.separator + targetFile;
			try {
				ExtentTestManager.getTest().addScreenCaptureFromPath("<a href='"+screenshotLoc+ "'>" +"Screenshots"+"</a>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ExtentTestManager.getTest().fail(result.getThrowable());
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		if (result != null && result.getThrowable() != null) {   
			result.getThrowable().printStackTrace();
	        }
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	

	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub
		if(testResult.getMethod().isTest()){
			if(getVerificationMap().size() > 0){
				if (testResult.getStatus() == ITestResult.SUCCESS) {
		            ITestContext testContext = Reporter.getCurrentTestResult().getTestContext();
		            testContext.getPassedTests().addResult(testResult, Reporter.getCurrentTestResult().getMethod());
		            testContext.getPassedTests().getAllMethods().remove(Reporter.getCurrentTestResult().getMethod());
		            Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
		            Reporter.getCurrentTestResult().setThrowable(new Exception("Found none-fatal error(s) at page level running test steps!"));
		            testContext.getFailedTests().addResult(testResult, Reporter.getCurrentTestResult().getMethod());
		        }
			
			}   

		}
		
	}

}
