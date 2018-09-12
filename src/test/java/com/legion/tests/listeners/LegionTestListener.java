package com.legion.tests.listeners;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
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
import com.legion.tests.annotations.TestName;
//import com.legion.utils.ExtentManager;





import static com.legion.utils.MyThreadLocal.*;

public class LegionTestListener extends TestBase implements ITestListener,IInvokedMethodListener{
	
	public static ExtentReports extent = ExtentManager.createInstance("test-output/AutomationReport");
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	
	@Override
	public void onTestStart(ITestResult result) {  
		String testName = result.getMethod().getMethodName();
		setLoc(testName);
        initialize();	
	}

	@Override
	public void onTestSuccess(ITestResult result) {
        extentTest.log(Status.PASS, MarkupHelper.createLabel("Test case Passed:",ExtentColor.GREEN));
        String targetFile = takeScreenShot();
        String screenshotLoc = System.getProperty("user.dir") + File.separator + targetFile;
//		try {
//			extentTest.addScreenCaptureFromPath("<a href='"+screenshotLoc+ "'>" +"Screenshots"+"</a>");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		extentTest.log(Status.FAIL, MarkupHelper.createLabel("Test case Failed:",ExtentColor.RED));
		String targetFile = takeScreenShot();
        String screenshotLoc = System.getProperty("user.dir") + File.separator + targetFile;
		try {
			extentTest.addScreenCaptureFromPath("<a href='"+screenshotLoc+ "'>" +"Screenshots"+"</a>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     test.get().fail(result.getThrowable());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
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
	
	
	public static class ExtentManager {
	    
		private static ExtentReports extent;
	    
	    public ExtentReports getInstance() {
	    	
			if (extent == null)
	    		createInstance("test-output/Automation");
	    	
	        return extent;
	    }
	    
	    public static ExtentReports createInstance(String fileName) {
	        String reportPath = createReportPath(fileName);
	    	ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportPath);
	        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
	        htmlReporter.config().setChartVisibilityOnOpen(true);
	        htmlReporter.config().setTheme(Theme.STANDARD);
	        htmlReporter.config().setDocumentTitle(fileName);
	        htmlReporter.config().setEncoding("utf-8");
	        htmlReporter.config().setReportName("Custom Report");
	        
	        extent = new ExtentReports();
	        extent.attachReporter(htmlReporter);
	        return extent;
	    }
	    
	    public static String createReportPath(String fileName){
	    	Date date=new Date();
	    	SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");  
			String strDate = formatter.format(date);  
			String strDateFinal = strDate.replaceAll(" ", "_");
			String reportName=date.toString().replace(":", "_").replace(" ", "_")+".html";
			File file = new File(fileName + File.separator + strDateFinal);
			if (!file.exists()) {
				file.mkdir();
			}
			String reportPath =fileName + File.separator + strDateFinal + File.separator + reportName;
			return reportPath;
	    }
	}

	@Override
	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult result) {
		// TODO Auto-generated method stub
		
		if (!method.isTestMethod()) {
            return;
        }
        TestName set = method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(TestName.class);
        extentTest = extent.createTest(set.description());
        test.set(extentTest);     
	}


}
