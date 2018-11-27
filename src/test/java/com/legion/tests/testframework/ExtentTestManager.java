package com.legion.tests.testframework;

import java.lang.reflect.Method;
import java.util.List;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;

public class ExtentTestManager {
	
	public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static ExtentReports extent = ExtentReportManager.getInstance();

    public synchronized static ExtentTest getTest() {
        return extentTest.get();
    }

    public synchronized static ExtentTest createTest(String name, String description, List<String> categories) {
    	
    	ExtentTest test = extent.createTest(name, description);
        
        for (String category : categories) {
            test.assignCategory(category);
           
        }
      
        extentTest.set(test);
        return getTest();
    }
    
    public synchronized static ExtentTest createTest(String name, String description, List<String> categories, List<String> enterprises) {
    	
    	ExtentTest test = extent.createTest(name, description);
        
        for (String category : categories) {
            test.assignCategory(category);
        }
        for (String enterprise : enterprises) {
        	extent.setSystemInfo("Enterprise",enterprise);
        }
      
        extentTest.set(test);
        return getTest();
    }

    public synchronized static ExtentTest createTest(String name, String description) {
        return createTest(name, description, null,null);
    }

    public synchronized static ExtentTest createTest(String name) {
        return createTest(name, null);
    }

    public synchronized static void log(String message) {
        getTest().info(message);
    }
    
    public synchronized static void setEnterpriseInfo(String categories) {
    	 
    	extent.setSystemInfo("Environment",categories);
    }
    
    
    public synchronized static String getTestName(Method testMethod) {
		
        String testName = "";
        // check if there is a Test annotation and get the test name
        TestName testCaseDescription = testMethod.getAnnotation(TestName.class);
        if (testCaseDescription != null && testCaseDescription.description().length() > 0) {
            testName = testCaseDescription.description();
        }
        
        return testName;
    }
    
    public synchronized static String getOwnerName(Method testMethod) {
		
        String ownerName = "";
        // check if there is a Test annotation and get the test name
        Owner own = testMethod.getAnnotation(Owner.class);
        if (own != null &&  own.owner().length() > 0) {
        	ownerName =  own.owner();
        }
       
        return ownerName;
    }
    
    public synchronized static String getAutomatedName(Method testMethod) {
		
        String automatedName = "";
        // check if there is a Test annotation and get the test name
        Automated automated = testMethod.getAnnotation(Automated.class);
        if (automated != null && automated.automated().length() > 0) {
        	automatedName = automated.automated();
        }
       
        return automatedName;
    }
    
   

     
}
