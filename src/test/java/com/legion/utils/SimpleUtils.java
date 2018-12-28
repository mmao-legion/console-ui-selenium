package com.legion.utils;

import static com.legion.utils.MyThreadLocal.getVerificationMap;
import static org.testng.AssertJUnit.assertTrue;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.util.Strings;

import com.aventstack.extentreports.Status;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.testframework.ExtentTestManager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * Yanming
 */
public class SimpleUtils {

    static HashMap<String,String> parameterMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");

    static String chrome_driver_path = parameterMap.get("CHROME_DRIVER_PATH");
	
    private static HashMap< String,Object[][]> userCredentials = JsonUtil.getCredentialsFromJsonFile("src/test/resources/legionUsers.json");	

    public static DesiredCapabilities initCapabilities(String browser, String version, String os) {
        DesiredCapabilities caps = new DesiredCapabilities();
        if ("chrome".equals(browser)) {
            System.setProperty("webdriver.chrome.driver", chrome_driver_path);
        }
        return caps;
    }

    /*
     * //todo will set up a remote selenium server on localhost. for now return null
     */
    public static String getURL() {

        return null;
    }
    
    public static void fail(String message, boolean continueExecution, String... severity) {
        if (continueExecution) {
            try {
                assertTrue(false);
            } catch (Throwable e) {
                addVerificationFailure(e);
                ExtentTestManager.getTest().log(Status.ERROR, message);      
            }
        } else {
        	ExtentTestManager.getTest().log(Status.FAIL, message);
            throw new AssertionError(message);
        }
    }
    
    private static void addVerificationFailure(Throwable e) {
        List<Throwable> verificationFailures = getVerificationFailures();
        getVerificationMap().put(Reporter.getCurrentTestResult(), verificationFailures);
        verificationFailures.add(e);
    }
    
    private static List<Throwable> getVerificationFailures() {
        List<Throwable> verificationFailures = getVerificationMap().get(Reporter.getCurrentTestResult());
        return verificationFailures == null ? new ArrayList<>() : verificationFailures;
    }
    
    public static String getCurrentUsersJobTitle(String userName)
    {
    	Object[][] userDetails = JsonUtil.getArraysFromJsonFile("src/test/resources/legionUsersCredentials.json");
    	String currentUserRole = "NA";
    	for (Object[] user : userDetails) {
			String userNameFromJson = (String) user[0];
			String userTitleFromJson = (String) user[2];
			if(userNameFromJson.contains(userName))
				return userTitleFromJson;
    	}
    	return currentUserRole;
    }
    
    public synchronized static HashMap<String, String> getUserNameAndPwd()
    {
    	Object[][] userDetails = JsonUtil.getArraysFromJsonFile("src/test/resources/UsersCredentials.json");
    	String userNameFromJson= null;
    	String userPwdFromJson= null;
    	HashMap<String, String> userNameAndPwd = new HashMap<String, String>();
    	for (Object[] user : userDetails) {
			userNameFromJson = (String) user[0];
			userPwdFromJson = (String) user[1];
			break;
    	}
    	userNameAndPwd.put("UserName",userNameFromJson);
    	userNameAndPwd.put("UserPassword",userPwdFromJson);
    	return userNameAndPwd;
    }
    
    public static String getListElementTextAsString(List<WebElement> listWebElements, String separator)
 	{
 		String listWebElementsText = "";
 		for(WebElement listWebElement: listWebElements)
 		{
 			listWebElementsText = listWebElementsText + separator +listWebElement.getText();
 		}
 		return listWebElementsText;
 	}
    

	// ToDo - Missing locator ID for SubTabs
	public static WebElement getSubTabElement(List<WebElement> listWebElements, String subTabText)
	{
		for(WebElement listWebElement : listWebElements)
		{
			if(listWebElement.getText().toLowerCase().contains(subTabText.toLowerCase()))
			{
				return listWebElement;
			}
		}
		return null;
	}
	
	public static void assertOnFail(String message, boolean isAssert, Boolean isExecutionContinue) {
        if (isExecutionContinue) {
            try {
                assertTrue(isAssert);
            } catch (Throwable e) {
                addVerificationFailure(e);
                //TestBase.extentTest.log(Status.ERROR, message); 
                ExtentTestManager.getTest().log(Status.ERROR, "<div class=\"row\" style=\"background-color:#FDB45C; color:white; padding: 7px 5px;\">" + message
                        + "</div>");
            }
        } else {
        	try {
                assertTrue(isAssert);
            } catch (Throwable e) {
            	ExtentTestManager.getTest().log(Status.FAIL, message);     
                throw new AssertionError(message);
            }	         
        }
    }
	
	
	 public static int getCurrentDateDayOfYear()
		{
			LocalDate currentDate = LocalDate.now();
			return currentDate.getDayOfYear();
		}
	    
    public static int getCurrentISOYear()
	{
		LocalDate currentDate = LocalDate.now();
		return currentDate.getYear();
	}
	    
	    
    public static HashMap<String, String> getDayMonthDateFormatForCurrentPastAndFutureWeek(int dayOfYear, int isoYear)
	{
		LocalDate dateBasedOnGivenParameter = Year.of(isoYear).atDay(dayOfYear);
	    LocalDate pastWeekDate = dateBasedOnGivenParameter.minusWeeks(1);
	    LocalDate futureWeekDate = dateBasedOnGivenParameter.plusWeeks(1);
	    HashMap<String, String> dateMonthOfCurrentPastAndFutureWeek = new HashMap<String, String>();
	    dateMonthOfCurrentPastAndFutureWeek.put("currentWeekDate", getDayMonthDateFormat(dateBasedOnGivenParameter));
	    dateMonthOfCurrentPastAndFutureWeek.put("pastWeekDate", getDayMonthDateFormat(pastWeekDate));
	    dateMonthOfCurrentPastAndFutureWeek.put("futureWeekDate", getDayMonthDateFormat(futureWeekDate));
	    return dateMonthOfCurrentPastAndFutureWeek;
	}
    
    public static LocalDate getCurrentLocalDateObject()
    {
    	return Year.of(LocalDate.now().getYear()).atDay(LocalDate.now().getDayOfYear());
    }
	    
    public static String getDayMonthDateFormat(LocalDate localDate) {
		String dayMonthDateFormat = null;
		DayOfWeek dayOfWeek = localDate.getDayOfWeek();
	    Month currentMonth = localDate.getMonth();
	    int currentDate = localDate.getDayOfMonth();
	    if(currentDate > 9)
	    {
		    dayMonthDateFormat = dayOfWeek.toString().substring(0, 3) + " " + currentMonth.toString().substring(0, 3) + " " +currentDate;
	    }
	    else
	    {
	    	dayMonthDateFormat = dayOfWeek.toString().substring(0, 3) + " " + currentMonth.toString().substring(0, 3) + " 0" +currentDate;
	    }

		return dayMonthDateFormat;
	}
	    
    public static void pass(String message) {
    	
    	ExtentTestManager.getTest().log(Status.PASS,"<div class=\"row\" style=\"background-color:#44aa44; color:white; padding: 7px 5px;\">" + message
                + "</div>");
    }
    
    public static void report(String message) {
    	
    	ExtentTestManager.getTest().log(Status.INFO,"<div class=\"row\" style=\"background-color:#0000FF; color:white; padding: 7px 5px;\">" + message
                + "</div>");
    }
	     
  
    public static HashMap<String, Object[][]> getEnvironmentBasedUserCredentialsFromJson(String fileName)
    {

    	return JsonUtil.getCredentialsFromJsonFile("src/test/resources/"+fileName);

    }
    
    public static String getDefaultEnterprise () {
		return parameterMap.get("ENTERPRISE");
	}

	public static String getEnterprise (String enterpriseKey) {
    	String result = null;
		if (!Strings.isNullOrEmpty(enterpriseKey)) {
			result = parameterMap.get(enterpriseKey);
		}
		return result != null ? result : getDefaultEnterprise();
	}

	public static String getEnterprise (Method testMethod) {
		Enterprise e = testMethod.getAnnotation(Enterprise.class);
		String enterpriseName = null;
		if (e != null ) {
			enterpriseName = SimpleUtils.getEnterprise(e.name());
		}
		else {
			enterpriseName = SimpleUtils.getDefaultEnterprise();
		}
		return enterpriseName;
	}
	
	
	public static void sortHashMapbykey(HashMap<String, Object[][]> hashMap) 
    { 
        TreeMap<String, Object[][]> sorted = new TreeMap<>(); 
        sorted.putAll(hashMap);        
    } 
	
	public static Object[][] concatenateObjects(Object[][] browersData, Object[][] credentialsByRole) 
    { 
		Object[][] combinedresult = new Object[credentialsByRole.length * browersData.length][];
		int index = 0;
       	for(Object[] credentialByRole: credentialsByRole)
       	{
   		    Object[] result = new Object[credentialByRole.length + 1]; 
       		for(Object[] browerData : browersData)
            {
	       		System.arraycopy(browerData, 0, result, 0, 1); 
	   	        System.arraycopy(credentialByRole, 0, result, 1, credentialByRole.length);
		       	combinedresult[index] = result;
		       	index = index + 1;
            }	       	
        }
	    return combinedresult;
    } 
	
	public static int countDuplicates(ArrayList list)
	   {
	       int duplicates = 0;
	       for (int i = 0; i < list.size()-1;i++) {
	           boolean found = false;
	           for (int j = i+1; !found && j < list.size(); j++)  {
	               if (list.get(i).equals(list.get(j)))
	               {
	            	   System.out.println("list.get(i) vs (list.get(j): "+list.get(i)+" "+list.get(j));
	            	   found = true;
		               duplicates++;
	               }
	               
	           }
	       }
	       return duplicates;
	   }
	public static String getCurrentDateMonthYearWithTimeZone(String timeZone)
	{
		String date = "";
		SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MMM-dd");
		dateTimeInGMT.setTimeZone(TimeZone.getTimeZone(timeZone));
		date = dateTimeInGMT.format(new Date());
		return date;
	}
}
