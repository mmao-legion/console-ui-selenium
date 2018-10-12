package com.legion.utils;

import static com.legion.utils.MyThreadLocal.getVerificationMap;
import static org.testng.AssertJUnit.assertTrue;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import com.aventstack.extentreports.Status;
import com.legion.tests.testframework.ExtentTestManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Yanming
 */
public class SimpleUtils {

    static HashMap<String,String> parameterMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");

    static String chrome_driver_path = parameterMap.get("CHROME_DRIVER_PATH");


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
			if(listWebElement.getText().contains(subTabText))
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
	     
	    public static Object[][] getUsersDataCredential(){
	    	Object[][] userDetails = JsonUtil.getArraysFromJsonFile("src/test/resources/UsersCredentials.json");
	    	String userNameFromJson= null;
	    	String userPwdFromJson= null;
	    	String location= null;
	    	for (Object[] user : userDetails) {
				userNameFromJson = (String) user[0];
				userPwdFromJson = (String) user[1];
				location = (String) user[2];
	    	}
	    	return userDetails;
	    }

	    
}