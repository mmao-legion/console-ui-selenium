package com.legion.utils;

import static com.legion.utils.MyThreadLocal.*;
import static org.testng.AssertJUnit.assertTrue;

import com.legion.tests.testframework.ScreenshotManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.util.Strings;

import com.aventstack.extentreports.Status;
import com.legion.test.testrail.APIClient;
import com.legion.test.testrail.APIException;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.testframework.ExtentTestManager;
import com.legion.tests.testframework.LegionTestListener;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Yanming
 */
public class SimpleUtils {

    static HashMap<String,String> parameterMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");

    static HashMap<String,String> testRailConfig = JsonUtil.getPropertiesFromJsonFile("src/test/resources/TestRailCfg.json");

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
		SimpleUtils.addTestResultIntoTestRail(5, message);
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
	    
	    
    public static HashMap<String, String> getDayMonthDateFormatForCurrentPastAndFutureWeek(int dayOfYear, int isoYear) {
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
		SimpleUtils.addTestResultIntoTestRail(1, message);
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

	public static void verifyTeamCount(List<String> previousTeamCount, List<String> currentTeamCount) throws Exception {
		if(previousTeamCount.size() == currentTeamCount.size()){
			for(int i =0; i<currentTeamCount.size();i++){
				String currentCount = currentTeamCount.get(i);
				String previousCount = previousTeamCount.get(i);
				if(Integer.parseInt(currentCount) == Integer.parseInt(previousCount)+1){
					SimpleUtils.pass("Current Team Count is greater than Previous Team Count");
				}else{
					SimpleUtils.fail("Current Team Count is not greater than Previous Team Count",true);
				}
			}
		}else{
			SimpleUtils.fail("Size of Current Team Count should be equal to Previous Team Count",false);
		}
	}
	public static String getCurrentDateMonthYearWithTimeZone(String timeZone)
	{
		String date = "";
		SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MMM-dd");
		dateTimeInGMT.setTimeZone(TimeZone.getTimeZone(timeZone));
		date = dateTimeInGMT.format(new Date());
		return date;
	}

	public static String dateWeekPickerDateComparision(String weekActiveDate) {
        int i = 0;
        List<String> listWeekActiveDate = new ArrayList();
        String dateRangeDayPicker = null;
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher match = pattern.matcher(weekActiveDate);
        String[] dateRange = weekActiveDate.split("-");
        while (match.find()) {
            if (Integer.parseInt(match.group(1)) < 10) {
                String padded = String.format("%02d", Integer.parseInt(match.group(1)));
                listWeekActiveDate.add(dateRange[i].replace(match.group(1), padded));
            } else {
                listWeekActiveDate.add(dateRange[i]);
            }
            i++;
        }
        dateRangeDayPicker = listWeekActiveDate.get(0) + "-" + listWeekActiveDate.get(1);
        return dateRangeDayPicker;

    }

	// method for mobile test cases incase of failure

	public static void fail(String message, boolean continueExecution, String platform) {
		SimpleUtils.addTestResultIntoTestRail(5,message);
    	if (continueExecution) {
            try {
                assertTrue(false);
            } catch (Throwable e) {
                addVerificationFailure(e);
                ExtentTestManager.getTest().log(Status.ERROR, message + " " + platform);
            }
        } else {
        	ExtentTestManager.getTest().log(Status.FAIL, message);
            throw new AssertionError(message);
        }
    }

    // added code for TestRail connection

	public static void addTestResult(int statusID, String comment)
	{
		/*
		 * TestRail Status ID : Description
		 * 1 : Passed
		 * 2 : Blocked
		 * 4 : Retest
		 * 5 : Failed
		 */

		MyThreadLocal myThreadLocal = new MyThreadLocal();
		String testCaseId = Integer.toString(ExtentTestManager.getTestRailId(myThreadLocal.getCurrentMethod()));
		String testName = ExtentTestManager.getTestName(myThreadLocal.getCurrentMethod());
		String addResultString = "add_result_for_case/1/"+testCaseId+"";
		String testRailURL = testRailConfig.get("TEST_RAIL_URL");
		String testRailUser = testRailConfig.get("TEST_RAIL_USER");
		String testRailPassword = testRailConfig.get("TEST_RAIL_PASSWORD");

		if(testCaseId != null && Integer.valueOf(testCaseId) > 0)
		{
			try {
				// Make a connection with Testrail Server
		        APIClient client = new APIClient(testRailURL);
		        client.setUser(testRailUser);
		        client.setPassword(testRailPassword);

		        JSONObject c = (JSONObject) client.sendGet("get_case/"+testCaseId);
		        String TestRailTitle = (String) c.get("title");
		        if(! TestRailTitle.equals(testName))
		        {
		        	Map<String, Object> updateTestTitle = new HashMap<String, Object>();
		        	updateTestTitle.put("title", testName);
		        	client.sendPost("update_case/"+testCaseId, updateTestTitle);
		        }

		        Map<String, Object> data = new HashMap<String, Object>();
		        data.put("status_id", statusID);
		        data.put("comment", comment);
		        client.sendPost(addResultString,data );

			}


			catch(IOException ioException)
			{
				System.err.println(ioException.getMessage());
			}
			catch(APIException aPIException)
			{
				System.err.println(aPIException.getMessage());
			}
		}
		
	}
	
	
	public static void addTestCase(String scenario, String summary, String testSteps, String expectedResult,
			String actualResult, String testData, String preconditions, String testCaseType, String priority,
			String isAutomated, String result, String actions, int sectionID)
	{
		MyThreadLocal myThreadLocal = new MyThreadLocal();
    	String testCaseId = Integer.toString(ExtentTestManager.getTestRailId(myThreadLocal.getCurrentMethod()));
    	String testName = ExtentTestManager.getTestName(myThreadLocal.getCurrentMethod());
		String addResultString = "add_case/"+sectionID;
		String testRailURL = testRailConfig.get("TEST_RAIL_URL");
		String testRailUser = testRailConfig.get("TEST_RAIL_USER");
		String testRailPassword = testRailConfig.get("TEST_RAIL_PASSWORD");
		try {
			// Make a connection with Testrail Server
	        APIClient client = new APIClient(testRailURL);
	        client.setUser(testRailUser);
	        client.setPassword(testRailPassword);

	        Map<String, Object> data = new HashMap<String, Object>();
	        data.put("title", summary);
	        data.put("priority_id", getPriorityIntegerValue(priority));
	        data.put("custom_custom_testdata",testData) ;
	        data.put("custom_steps", testSteps);
	        data.put("custom_custom_automated",isAutomated) ;
	        //data.put("custom_custom_useraccess",testCaseType);
	        data.put("custom_expected", expectedResult);
	        data.put("custom_preconds", preconditions);

	        System.out.println(client.sendPost(addResultString,data ));
		}

		catch(IOException ioException)
		{
			System.err.println(ioException.getMessage());
		}
		catch(APIException aPIException)
		{
			System.err.println(aPIException.getMessage());
		}
	}



	public static void updateTestCase(String scenario, String summary, String testSteps, String expectedResult,
			String actualResult, String testData, String preconditions, String testCaseType, String priority,
			String isAutomated, String result, String actions, int sectionID)
	{
		MyThreadLocal myThreadLocal = new MyThreadLocal();
		//String testCaseId = Integer.toString(ExtentTestManager.getTestRailId(myThreadLocal.getCurrentMethod()));
		String testName = ExtentTestManager.getTestName(myThreadLocal.getCurrentMethod());
		String updateResultString = "update_case";
		String testRailURL = testRailConfig.get("TEST_RAIL_URL");
		String testRailUser = testRailConfig.get("TEST_RAIL_USER");
		String testRailPassword = testRailConfig.get("TEST_RAIL_PASSWORD");
		int projectId = Integer.valueOf(testRailConfig.get("TEST_RAIL_PROJECT_ID"));
		try {
			// Make a connection with Testrail Server
			APIClient client = new APIClient(testRailURL);
			client.setUser(testRailUser);
			client.setPassword(testRailPassword);

	        int testCaseID = getTestCaseIDFromTitle(summary, projectId, client, sectionID);
	        System.out.println("testCaseID : "+testCaseID);
	        if(testCaseID > 0)
	        {
	        	Map<String, Object> testCaseDataToUpdate = new HashMap<String, Object>();
	        	testCaseDataToUpdate.put("priority_id", getPriorityIntegerValue(priority));
	        	testCaseDataToUpdate.put("custom_custom_testdata",testData) ;
	        	testCaseDataToUpdate.put("custom_steps", testSteps);
	        	testCaseDataToUpdate.put("custom_custom_automated",isAutomated) ;
		        //data.put("custom_custom_useraccess",testCaseType);
	        	testCaseDataToUpdate.put("custom_expected", expectedResult);
	        	testCaseDataToUpdate.put("custom_preconds", preconditions);

		        JSONObject updateTestCaseResult = (JSONObject) client.sendPost(updateResultString + "/" + testCaseID, testCaseDataToUpdate);
		        pass("Test Case with ID :'"+ testCaseID +"' Updated Successfully ('"+ updateTestCaseResult +"");
	        }
	        else {
	        	report("No Test Case found with the title :'"+ summary +"'.");
	        }

		}

		catch(IOException ioException)
		{
			System.err.println(ioException.getMessage());
			fail(ioException.getMessage(), true);
		}
		catch(APIException aPIException)
		{
			System.err.println(aPIException.getMessage());
			fail(aPIException.getMessage(), true);
		}
	}


	public static int getPriorityIntegerValue(String priority)
	{
		priority = priority.toLowerCase();
		int integerPriority = 0;
		switch (priority) {
        case "highest":
        	integerPriority = 4;
            break;
        case "high":
        	integerPriority = 3;
            break;
        case "Medium":
        	integerPriority = 2;
            break;
        default:
        	integerPriority = 1;
            break;
        }

		return integerPriority;
	}

	public static int getTestCaseIDFromTitle(String title, int projectID, APIClient client, int sectionID)
	{
		JSONArray testCasesList;
		JSONObject jsonTestCase;
        int suiteId = Integer.valueOf(testRailConfig.get("TEST_RAIL_SUITE_ID"));
		int testCaseID = 0;
		try {
			testCasesList = (JSONArray) client.sendGet("get_cases/"+projectID+"/&suite_id="+suiteId+"&section_id="+sectionID);
			for(Object testCase : testCasesList)
			{

				jsonTestCase = (JSONObject) testCase;
				if(title.trim().toLowerCase().equals(jsonTestCase.get("title").toString().trim().toLowerCase()))
				{
					testCaseID = Integer.valueOf(jsonTestCase.get("id").toString());
				}
			}
		} catch (IOException | APIException | NullPointerException e) {
			fail(e.getMessage(), true);
		}
        return testCaseID;
	}

	public static void deleteTestCaseByID(int testCaseID)
	{
		String deleteResultString = "delete_case";
		String testRailURL = testRailConfig.get("TEST_RAIL_URL");
		String testRailUser = testRailConfig.get("TEST_RAIL_USER");
		String testRailPassword = testRailConfig.get("TEST_RAIL_PASSWORD");

		try {
			// Make a connection with Testrail Server
			APIClient client = new APIClient(testRailURL);
			client.setUser(testRailUser);
			client.setPassword(testRailPassword);

			Map<String, Object> testCaseDataToDelete = new HashMap<String, Object>();
			testCaseDataToDelete.put("id", testCaseID);
			JSONObject deleteTestCaseResult = (JSONObject) client.sendPost(deleteResultString+ "/" + testCaseID, testCaseDataToDelete)/* client.sendGet(deleteResultString + "/" + testCaseID)*/;

			pass("Test Case with ID :'"+ testCaseID +"' Deleted Successfully ('"+ deleteTestCaseResult +"').");
			System.out.println("Test Case with ID :'"+ testCaseID +"' Deleted Successfully ('"+ deleteTestCaseResult +"').");
		}

		catch(IOException | APIException exception)
		{
			System.err.println(exception.getMessage());
			fail(exception.getMessage(), true);
		}
	}

	public static void deleteTestCaseByTitle(String title, int projectId, int sectionID)
	{
		String testRailURL = testRailConfig.get("TEST_RAIL_URL");
		String testRailUser = testRailConfig.get("TEST_RAIL_USER");
		String testRailPassword = testRailConfig.get("TEST_RAIL_PASSWORD");

		// Make a connection with Testrail Server
		APIClient client = new APIClient(testRailURL);
		client.setUser(testRailUser);
		client.setPassword(testRailPassword);

		int testCaseID = getTestCaseIDFromTitle(title, projectId, client, sectionID);

		if(testCaseID > 0)
		{
			deleteTestCaseByID(testCaseID);
		}

	}

   public static Float convertDateIntotTwentyFourHrFormat(String startDate, String endDate) throws ParseException {
	   SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
	   SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
	   int shiftHourcalculation =0;
	   Float shiftMinutecalculation =0.0f;
	   Float scheduleHoursDifference = 0.0f;
	   Date startDateFormat = parseFormat.parse(startDate.substring(0,startDate.length()-2) + " " +startDate.substring(startDate.length()-2));
	   Date endDateFormat = parseFormat.parse(endDate.substring(0,endDate.length()-2) + " " +endDate.substring(endDate.length()-2));
	   String strEndDate = displayFormat.format(endDateFormat).toString();
	   String strStartDate = displayFormat.format(startDateFormat).toString();
	   String[] arrEndDate = strEndDate.split(":");
	   String[] arrStartDate = strStartDate.split(":");
	   if(endDate.contains("AM")){
	   	   shiftHourcalculation = (24 + Integer.parseInt(arrEndDate[0]))-(Integer.parseInt(arrStartDate[0]));
	   	   shiftMinutecalculation =  (Float.parseFloat(arrEndDate[1]) -  Float.parseFloat(arrEndDate[1]))/60;
		   scheduleHoursDifference = shiftHourcalculation + shiftMinutecalculation ;
	   }else{
		   shiftHourcalculation = Integer.parseInt(arrEndDate[0])-Integer.parseInt(arrStartDate[0]);
		   shiftMinutecalculation =  (Float.parseFloat(arrEndDate[1]) -  Float.parseFloat(arrStartDate[1]))/60;
		   scheduleHoursDifference = shiftHourcalculation + shiftMinutecalculation ;
	   }

	   return scheduleHoursDifference;
   }

	//added by Nishant

	public static int addNUpdateTestCaseIntoTestRail(String testName, int sectionID,ITestContext context)
	{
		int testCaseID = 0;

		if(sectionID > 0){

//	    String testName = ExtentTestManager.getTestName(MyThreadLocal.getCurrentMethod());
			String addResultString = "add_case/"+sectionID;
			String testRailURL = testRailConfig.get("TEST_RAIL_URL");
			String testRailUser = testRailConfig.get("TEST_RAIL_USER");
			String testRailPassword = testRailConfig.get("TEST_RAIL_PASSWORD");
			String testRailProjectID = testRailConfig.get("TEST_RAIL_PROJECT_ID");
			String testRailSuiteID = testRailConfig.get("TEST_RAIL_SUITE_ID");
			try {
				// Make a connection with TestRail Server
				APIClient client = new APIClient(testRailURL);
				client.setUser(testRailUser);
				client.setPassword(testRailPassword);
				testCaseID = getTestCaseIDFromTitle(testName, Integer.parseInt(testRailProjectID), client, sectionID);
				if(testCaseID > 0){
//					addNUpdateTestCaseIntoTestRun(testName,sectionID,testCaseID);
					addNUpdateTestCaseIntoTestRun1(testName,sectionID,testCaseID,context);
					return testCaseID;
				}else{
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("title", testName);
					client.sendPost(addResultString,data );
					testCaseID = getTestCaseIDFromTitle(testName, Integer.parseInt(testRailProjectID), client, sectionID);
//					addNUpdateTestCaseIntoTestRun(testName,sectionID,testCaseID);
					addNUpdateTestCaseIntoTestRun1(testName,sectionID,testCaseID,context);
					return testCaseID;
				}


			}catch(IOException ioException){
				System.err.println(ioException.getMessage());
			}
			catch(APIException aPIException){
				System.err.println(aPIException.getMessage());
			}


		}
//		String testCaseId = Integer.toString(ExtentTestManager.getTestRailId(MyThreadLocal.getCurrentMethod()));
		return testCaseID;
	}


	//added by Nishant

	public static void addTestResultIntoTestRail(int statusID, String comment)
	{
		/*
		 * TestRail Status ID : Description
		 * 1 : Passed
		 * 2 : Blocked
		 * 4 : Retest
		 * 5 : Failed
		 */
		int testCaseId = MyThreadLocal.getTestCaseId();
		String testName = ExtentTestManager.getTestName(MyThreadLocal.getCurrentMethod());
		String testRailURL = testRailConfig.get("TEST_RAIL_URL");
		String testRailUser = testRailConfig.get("TEST_RAIL_USER");
		String testRailPassword = testRailConfig.get("TEST_RAIL_PASSWORD");
		int testRailRunId = getTestRailRunId();
		String addResultString = "add_result_for_case/"+testRailRunId+"/"+testCaseId+"";

		if(testCaseId > 0)
		{
			try {
				// Make a connection with Testrail Server
				APIClient client = new APIClient(testRailURL);
				client.setUser(testRailUser);
				client.setPassword(testRailPassword);
				JSONObject jSONObject = (JSONObject) client.sendGet("get_case/"+testCaseId);
				if(statusID == 5) {
					Map<String, Object> data = new HashMap<String, Object>();
					takeScreenShotOnFailure();
					String finalLink = getscreenShotURL();
					data.put("status_id", statusID);
					data.put("comment", comment +"\n" + "[Link To ScreenShot]" +"("+finalLink +")");
//					data.put("screen_shot", getscreenShotURL());
					client.sendPost(addResultString, data);
				}else{
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("status_id", statusID);
					data.put("comment", comment);
					client.sendPost(addResultString, data);
				}

			}catch(IOException ioException){
				System.err.println(ioException.getMessage());
			}
			catch(APIException aPIException){
				System.err.println(aPIException.getMessage());
			}
		}

	}



	public static int addNUpdateTestCaseIntoTestRun(String testName, int sectionID, int testCaseId)
	{
		String testRailURL = testRailConfig.get("TEST_RAIL_URL");
		String testRailUser = testRailConfig.get("TEST_RAIL_USER");
		String testRailPassword = testRailConfig.get("TEST_RAIL_PASSWORD");
		String testRailProjectID = testRailConfig.get("TEST_RAIL_PROJECT_ID");
		int suiteId = Integer.valueOf(testRailConfig.get("TEST_RAIL_SUITE_ID"));

		int TestRailRunId = 0;
		int count = 0;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date =null;
		String strDate = null;

		if((getTestRailRunId()!=null && getTestRailRunId() > 0)){
			String addResultString = "update_run/" + getTestRailRunId();
			try {
				// Make a connection with TestRail Server
				APIClient client = new APIClient(testRailURL);
				client.setUser(testRailUser);
				client.setPassword(testRailPassword);
				List cases = new ArrayList();
				cases.add(new Integer(testCaseId));

				Map<String, Object> data = new HashMap<String, Object>();
				data.put("title", testName);
				try{
					date = format.parse(timestamp.toString());
					String[] arrDate = format.format(date).split(" ");
					strDate = arrDate[1];
					System.out.println(format.format(date));
				}catch(ParseException e){
					System.err.println(e.getMessage());
				}

				data.put("name", "Automation Suite Test Run"+"" +strDate);
				data.put("suite_id", suiteId);
				data.put("include_all", true);
				data.put("case_ids", cases);
				JSONObject c = (JSONObject) client.sendPost(addResultString, data);
//			JSONObject c = (JSONObject) client.sendGet("get_run/"+testCaseId);
				long longTestRailRunId = (Long) c.get("id");
				TestRailRunId = (int) longTestRailRunId;
				System.out.println(TestRailRunId);
				setTestRailRunId(TestRailRunId);
			} catch (IOException ioException) {
				System.err.println(ioException.getMessage());
			} catch (APIException aPIException) {
				System.err.println(aPIException.getMessage());
			}
		}else {
			String addResultString = "add_run/" + testRailProjectID;
			try {
				// Make a connection with TestRail Server
				APIClient client = new APIClient(testRailURL);
				client.setUser(testRailUser);
				client.setPassword(testRailPassword);
				List cases = new ArrayList();
				cases.add(new Integer(testCaseId));

				Map<String, Object> data = new HashMap<String, Object>();
				data.put("title", testName);
				try{
					date = format.parse(timestamp.toString());
					String[] arrDate = format.format(date).split(" ");
					strDate = arrDate[1];
				}catch(ParseException e){
					System.err.println(e.getMessage());
				}
				data.put("suite_id", suiteId);
				data.put("name", "Automation Smoke"+"" +strDate);
				data.put("include_all", false);
				data.put("case_ids", cases);
				JSONObject c = (JSONObject) client.sendPost(addResultString, data);
//			JSONObject c = (JSONObject) client.sendGet("get_run/"+testCaseId);
				long longTestRailRunId = (Long) c.get("id");
				TestRailRunId = (int) longTestRailRunId;
				System.out.println(TestRailRunId);
				setTestRailRunId(TestRailRunId);
			} catch (IOException ioException) {
				System.err.println(ioException.getMessage());
			} catch (APIException aPIException) {
				System.err.println(aPIException.getMessage());
			}
		}

		return TestRailRunId;

	}


	public static void takeScreenShotOnFailure(){
		String targetFile = ScreenshotManager.takeScreenShot();
		String screenshotLoc = parameterMap.get("Screenshot_Path") + File.separator + targetFile;
		String screenShotURL = "file:///" + screenshotLoc;
		setscreenShotURL(screenShotURL);
	}

   public static int getDirectoryFilesCount(String directoryPath) {
	   File directory = new File(directoryPath);
	   File[] files = directory.listFiles();
	   return files.length;
   }

   public static File getLatestFileFromDirectory(String directoryPath) {
	    File dir = new File(directoryPath);
	    File[] files = dir.listFiles();
	    if (files == null || files.length == 0) {
	        return null;
	    }

	    File lastModifiedFile = files[0];
	    for (int i = 1; i < files.length; i++) {
	       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
	           lastModifiedFile = files[i];
	       }
	    }
	    return lastModifiedFile;
   }



   //added by Nishant

	public static int addNUpdateTestCaseIntoTestRun1(String testName, int sectionID, int testCaseId, ITestContext context)
	{
		String testRailURL = testRailConfig.get("TEST_RAIL_URL");
		String testRailUser = testRailConfig.get("TEST_RAIL_USER");
		String testRailPassword = testRailConfig.get("TEST_RAIL_PASSWORD");
		String testRailProjectID = testRailConfig.get("TEST_RAIL_PROJECT_ID");
		int suiteId = Integer.valueOf(testRailConfig.get("TEST_RAIL_SUITE_ID"));

		int TestRailRunId = 0;
		int count = 0;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date =null;
		String strDate = null;

		if((Integer) context.getAttribute("TestRailId")!=null){
			int testRailId = (Integer) context.getAttribute("TestRailId");
//			String addResultString = "update_run/" + getTestRailRunId();
			String addResultString = "update_run/" + testRailId;
			try {
				// Make a connection with TestRail Server
				APIClient client = new APIClient(testRailURL);
				client.setUser(testRailUser);
				client.setPassword(testRailPassword);
				List cases = new ArrayList();
				cases.add(new Integer(testCaseId));

				Map<String, Object> data = new HashMap<String, Object>();
				data.put("title", testName);
//				try{
//					date = format.parse(timestamp.toString());
//					String[] arrDate = format.format(date).split(" ");
//					strDate = arrDate[1];
//					System.out.println(format.format(date));
//				}catch(ParseException e){
//					System.err.println(e.getMessage());
//				}
//
//				data.put("name", "Automation Suite Test Run"+"" +strDate);
				data.put("suite_id", suiteId);
				data.put("include_all", false);
				data.put("case_ids", cases);
				JSONObject c = (JSONObject) client.sendPost(addResultString, data);
//			JSONObject c = (JSONObject) client.sendGet("get_run/"+testCaseId);
				long longTestRailRunId = (Long) c.get("id");
				TestRailRunId = (int) longTestRailRunId;
				System.out.println(TestRailRunId);
				setTestRailRunId(TestRailRunId);
			} catch (IOException ioException) {
				System.err.println(ioException.getMessage());
			} catch (APIException aPIException) {
				System.err.println(aPIException.getMessage());
			}
		}else {
			String addResultString = "add_run/" + testRailProjectID;
			try {
				// Make a connection with TestRail Server
				APIClient client = new APIClient(testRailURL);
				client.setUser(testRailUser);
				client.setPassword(testRailPassword);
				List cases = new ArrayList();
				cases.add(new Integer(testCaseId));

				Map<String, Object> data = new HashMap<String, Object>();
				data.put("title", testName);
				try{
					date = format.parse(timestamp.toString());
					String[] arrDate = format.format(date).split(" ");
					strDate = arrDate[1];
				}catch(ParseException e){
					System.err.println(e.getMessage());
				}
				data.put("suite_id", suiteId);
				data.put("name", "Automation Smoke"+"" +strDate);
				data.put("include_all", false);
				data.put("case_ids", cases);
				JSONObject c = (JSONObject) client.sendPost(addResultString, data);
//			JSONObject c = (JSONObject) client.sendGet("get_run/"+testCaseId);
				long longTestRailRunId = (Long) c.get("id");
				TestRailRunId = (int) longTestRailRunId;
				System.out.println(TestRailRunId);
				setTestRailRunId(TestRailRunId);
				context.setAttribute("TestRailId", getTestRailRunId());
			} catch (IOException ioException) {
				System.err.println(ioException.getMessage());
			} catch (APIException aPIException) {
				System.err.println(aPIException.getMessage());
			}
		}

		return TestRailRunId;

	}





}