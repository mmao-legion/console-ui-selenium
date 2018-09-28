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
	
	public static ExtentReports extent = ExtentReportManager.createInstance("test-output");
//	public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");

	
	@Override
	public void onTestStart(ITestResult result) {  
		String testName = result.getMethod().getMethodName();
//		set(result);
		setLoc(testName);
		TestBase.initialize();	
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		if(getVerificationMap().size() > 0){
        	 ITestContext testContext = result.getTestContext();
             testContext.getPassedTests().removeResult(result);
             result.setStatus(ITestResult.FAILURE);
             ExtentTestManager.extentTest.get().log(Status.FAIL,"Found none-fatal error(s) at page level running test steps!");
        }else{
        	ExtentTestManager.extentTest.get().log(Status.PASS, MarkupHelper.createLabel("Test case Passed:",ExtentColor.GREEN));
        }
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		ExtentTestManager.extentTest.get().log(Status.FAIL, MarkupHelper.createLabel("Test case Failed:",ExtentColor.RED));
		String targetFile = TestBase.takeScreenShot();
        String screenshotLoc = propertyMap.get("Screenshot_Path") + File.separator + targetFile;
		try {
			ExtentTestManager.extentTest.get().addScreenCaptureFromPath("<a href='"+screenshotLoc+ "'>" +"Screenshots"+"</a>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExtentTestManager.extentTest.get().fail(result.getThrowable());
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
		 extent.flush();
		
	}
	
	
	@Override
	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult result) {
		// TODO Auto-generated method stub
		//need to modify line no 129 and 130 code
		if(!method.isTestMethod()){
			return;
		}
		String testName = getTestName(result);
		String ownerName = getOwnerName(result);
		String getAutomatedName = getAutomatedName(result);
        ExtentTestManager.createTest(result.getTestClass().getRealClass().getSimpleName()  + " - "  +" " +result.getMethod().getMethodName() + " : " + testName + " [" +ownerName + "/" + getAutomatedName + "]","",result.getTestClass().getRealClass().getSimpleName());	
	}


	
	private String getTestName( ITestResult result) {
		
        String testName = "";
        // check if there is a Test annotation and get the test name
        Method testCaseMethod = result.getMethod().getConstructorOrMethod().getMethod();
        TestName testCaseDescription = testCaseMethod.getAnnotation(TestName.class);
        if (testCaseDescription != null && testCaseDescription.description().length() > 0) {
            testName = testCaseDescription.description();
        }
        
        return testName;
    }
	
	private String getOwnerName( ITestResult result) {
			
	        String ownerName = "";
	        // check if there is a Owner annotation and get the owner name
	        Method testCaseMethod = result.getMethod().getConstructorOrMethod().getMethod();
	      
	        Owner own = testCaseMethod.getAnnotation(Owner.class);
	        if (own != null && own.owner().length() > 0) {
	        	ownerName = own.owner();
	        }
	              
	        return ownerName;
	    }
	
	private String getAutomatedName( ITestResult result) {
		
        String automatedName = "";
        // check if there is a Automated annotation and get the automated name
        Method testCaseMethod = result.getMethod().getConstructorOrMethod().getMethod();
      
        Automated automated = testCaseMethod.getAnnotation(Automated.class);
        if (automated != null && automated.automated().length() > 0) {
        	automatedName = automated.automated();
        }
       
        return automatedName;
    }
		

}
