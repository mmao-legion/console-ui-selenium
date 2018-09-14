package com.legion.pages;

import static com.legion.utils.MyThreadLocal.getCurrentTestMethodName;
import static com.legion.utils.MyThreadLocal.getDriver;
import static com.legion.utils.MyThreadLocal.getFile;
import static com.legion.utils.MyThreadLocal.getScreenNum;
import static com.legion.utils.MyThreadLocal.getVerificationMap;
import static com.legion.utils.MyThreadLocal.setScreenNum;
import static org.testng.AssertJUnit.assertTrue;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.legion.tests.TestBase;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;

/**
 * Yanming
 */
public class BasePage {

    protected WebDriver driver;
//    public static ExtentTest extentTest;
    

    public void click(WebElement element, boolean... shouldWait) {
    	try {
            waitUntilElementIsVisible(element);
            element.click();
        } catch (TimeoutException te) {
            TestBase.extentTest.log(Status.WARNING,te);
        }
    }

    public void waitForElement(String element) {
  
		Wait<WebDriver> wait = new FluentWait<WebDriver>(
                 MyThreadLocal.getDriver()).withTimeout(java.time.Duration.ofSeconds(60))
                 .pollingEvery(5, TimeUnit.SECONDS)
                 .ignoring(NoSuchElementException.class);
         wait.until(new Function<WebDriver, WebElement>() {
             @Override
             public WebElement apply(WebDriver t) {
                 return t.findElement(By
                         .cssSelector(element));
             }
         });
    	
    }

    public static String getText(WebElement element) {
    	waitUntilElementIsVisible(element);
        return element.getText();
    }
    
    public void checkElementVisibility(WebElement element)
    {
        WebDriverWait wait = new WebDriverWait(MyThreadLocal.getDriver(),30);
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            pass("Element is present");
        }
        catch (NoSuchElementException e)
        {
            e.printStackTrace();
//            fail("Element is not present");
        }
    }
    
   
    public boolean isElementLoaded(WebElement element) throws Exception
    {
    	WebDriverWait tempWait = new WebDriverWait(MyThreadLocal.getDriver(), 30); 
    	try {
    	    tempWait.until(ExpectedConditions.visibilityOf(element)); 
    	    return true;
    	}
    	catch (NoSuchElementException | TimeoutException te) {
    		return false;
    	}
    	
    }
    
    public static void waitUntilElementIsVisible(final WebElement element) {
        ExpectedCondition<Boolean> expectation = _driver -> element.isDisplayed();

        Wait<WebDriver> wait = new WebDriverWait(getDriver(), 60);
        try {
            wait.until(webDriver -> expectation);
        } catch (Throwable ignored) {
        }
    }
    
    //added by Nishant
    
    public void waitForPageLoaded(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(webDriver -> getDriver().executeScript("return document.readyState").equals("complete"));
        } catch (Exception error) {
            try {
                getDriver().navigate().refresh();
            } catch (TimeoutException ignored) {
            }
            
        }
    }

    public void waitForSeconds(long waitSeconds) {
        waitSeconds = waitSeconds * 1000;
        Calendar currentTime = Calendar.getInstance();
        long currentTimeMillis = currentTime.getTimeInMillis();
        long secCounter = 0;
        while (secCounter < waitSeconds) {
            Calendar newTime = Calendar.getInstance();
            secCounter = (newTime.getTimeInMillis()) - (currentTimeMillis);
        }
    }
    
    public WebElement waitForElementPresence(String element) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(
                MyThreadLocal.getDriver()).withTimeout(java.time.Duration.ofSeconds(60))
                .ignoring(NoSuchElementException.class);
        WebElement elementPresent = wait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver t) {
                return t.findElement(By
                        .xpath(element));
            }
        });
        return elementPresent;
    }
    
    protected boolean verifyElementPresent(WebElement element) {
        boolean actual = true;
        try {
            element.isDisplayed();
        } catch (NoSuchElementException e) {
            actual = false;
        }
        return actual;
    }
    
    public static void pass(String message) {
    	
    	TestBase.extentTest.log(Status.PASS,"<div class=\"row\" style=\"background-color:#44aa44; color:white; padding: 7px 5px;\">" + message
                + "</div>");
    }
    
    public static void fail(String message, boolean continueExecution, String... severity) {
        if (continueExecution) {
            try {
                assertTrue(false);
            } catch (Throwable e) {
                addVerificationFailure(e);
                TestBase.extentTest.log(Status.ERROR, message);      
            }
        } else {
        	TestBase.extentTest.log(Status.FAIL, message);
            throw new AssertionError(message);
        }
    }
    
    private static void addVerificationFailure(Throwable e) {
        List<Throwable> verificationFailures = getVerificationFailures();
        System.out.println(Reporter.getCurrentTestResult());
        System.out.println(verificationFailures);
        getVerificationMap().put(Reporter.getCurrentTestResult(), verificationFailures);
        verificationFailures.add(e);
    }
    
    private static List<Throwable> getVerificationFailures() {
        List<Throwable> verificationFailures = getVerificationMap().get(Reporter.getCurrentTestResult());
        return verificationFailures == null ? new ArrayList<>() : verificationFailures;
    }
    
    public String getCurrentUsersJobTitle(String userName)
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
    
    public String getListElementTextAsString(List<WebElement> listWebElements, String separator)
 	{
 		String listWebElementsText = "";
 		for(WebElement listWebElement: listWebElements)
 		{
 			listWebElementsText = listWebElementsText + separator +listWebElement.getText();
 		}
 		return listWebElementsText;
 	}
    
}
