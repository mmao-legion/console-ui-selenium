package com.legion.pages;

import static com.legion.utils.MyThreadLocal.getDriver;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.legion.tests.testframework.ExtentTestManager;
import com.legion.tests.testframework.ScreenshotManager;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;

/**
 * Yanming
 */
public class BasePage {

    protected WebDriver driver;
    public static String activeConsoleName;
//    public static ExtentTest extentTest;
    

    public void click(WebElement element, boolean... shouldWait) {
    	try {
            waitUntilElementIsVisible(element);
            element.click();
        } catch (TimeoutException te) {
        	ExtentTestManager.getTest().log(Status.WARNING,te);
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
    
    public static String displayCurrentURL()
    {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        return (String) executor.executeScript("return document.location.href");
      
    }
   
}
