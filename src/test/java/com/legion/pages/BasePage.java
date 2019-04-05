package com.legion.pages;

import static com.legion.utils.MyThreadLocal.getDriver;

import java.time.Duration;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
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

    public static String activeConsoleName;

    public void click(WebElement element, boolean... shouldWait) {
    	try {
            waitUntilElementIsVisible(element);
            element.click();
        } catch (TimeoutException te) {
        	ExtentTestManager.getTest().log(Status.WARNING,te);
        }
    }
    
    //click method for mobile app
    
    public void clickOnMobileElement(WebElement element, boolean... shouldWait) {
    	try {
            waitUntilElementIsVisibleOnMobile(element);
            element.click();
        } catch (TimeoutException te) {
        	ExtentTestManager.getTest().log(Status.WARNING,te);
        }
    }
    

    public int calcListLength(List<WebElement> listLength){
    	return listLength.size();
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
        WebDriverWait wait = new WebDriverWait(MyThreadLocal.getDriver(), 30);
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
    
    // method for mobile application
    
    public boolean isElementLoadedOnMobile(WebElement element) throws Exception
    {
    	WebDriverWait tempWait = new WebDriverWait(MyThreadLocal.getAndroidDriver(), 30);
    	 
    	try {
    	    tempWait.until(ExpectedConditions.visibilityOf(element)); 
    	    return true;
    	}
    	catch (NoSuchElementException | TimeoutException te) {
    		return false;	
    	}
    	
    }

    public boolean isElementLoadedOnMobile(WebElement element, long timeOutInSeconds) throws Exception
    {
        WebDriverWait tempWait = new WebDriverWait(MyThreadLocal.getAndroidDriver(), timeOutInSeconds);

        try {
            tempWait.until(ExpectedConditions.visibilityOf(element));
            return true;
        }
        catch (NoSuchElementException | TimeoutException te) {
            return false;
        }

    }




    public boolean isElementLoaded(WebElement element, long timeOutInSeconds) throws Exception
    {
    	WebDriverWait tempWait = new WebDriverWait(MyThreadLocal.getDriver(), timeOutInSeconds);
    	 
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
    
    // method created for mobile app
    
    public static void waitUntilElementIsVisibleOnMobile(final WebElement element) {
        ExpectedCondition<Boolean> expectation = _driver -> element.isDisplayed();

        Wait<WebDriver> wait = new WebDriverWait(MyThreadLocal.getAndroidDriver(), 60);
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
    
    public void mouseHover(WebElement element)
    {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(element,10,15).click().build().perform();
    }

    
    public void mouseHoverDragandDrop(WebElement fromDestination, WebElement toDestination)
    {
        Actions actions = new Actions(getDriver());
        actions.dragAndDrop(fromDestination, toDestination).build().perform();
    }


    //added by Nishant for Optimization of code

    public boolean isElementEnabled(WebElement enabledElement){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(
                MyThreadLocal.getDriver()).withTimeout(Duration.ofSeconds(60))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(org.openqa.selenium.NoSuchElementException.class);
        Boolean element =false;

        try{
            element = wait.until(new Function<WebDriver, Boolean>() {
                @Override
                public Boolean apply(WebDriver t) {
                    boolean display = false;
                    display = enabledElement.isEnabled();
                    if(display )
                        return true;
                    else
                        return false;
                }
            });
        }catch(NoSuchElementException | TimeoutException te){
            return element;
        }
        return element;
    }

    //added by Gunjan
    public boolean isElementEnabled(WebElement enabledElement, long timeOutInSeconds){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(
                MyThreadLocal.getDriver()).withTimeout(Duration.ofSeconds(timeOutInSeconds))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(org.openqa.selenium.NoSuchElementException.class);
        Boolean element =false;

        try{
            element = wait.until(new Function<WebDriver, Boolean>() {
                @Override
                public Boolean apply(WebDriver t) {
                    boolean display = false;
                    display = enabledElement.isEnabled();
                    if(display )
                        return true;
                    else
                        return false;
                }
            });
        }catch(NoSuchElementException | TimeoutException te){
            return element;
        }
        return element;
    }





//    public boolean isElementPresent(WebElement displayElement){
//        Wait<WebDriver> wait = new FluentWait<WebDriver>(
//                MyThreadLocal.getDriver()).withTimeout(Duration.ofSeconds(5))
//                .pollingEvery(Duration.ofSeconds(5))
//                .ignoring(org.openqa.selenium.NoSuchElementException.class);
//        try{
//            Boolean element = wait.until(new Function<WebDriver, Boolean>() {
//                @Override
//                public Boolean apply(WebDriver t) {
//                    boolean display = false;
//
//                    display = displayElement.isDisplayed();
//                    if(display )
//                        return true;
//                    else
//                        return false;
//                }
//            });
//        }catch()
//
//        return false;
//    }



    public boolean areListElementVisible(List<WebElement> listElement){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(
                MyThreadLocal.getDriver()).withTimeout(Duration.ofSeconds(60))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(org.openqa.selenium.NoSuchElementException.class);
        Boolean element =false;
        try{
            element = wait.until(new Function<WebDriver, Boolean>() {
                @Override
                public Boolean apply(WebDriver t) {
                    int size = 0;
                    size = listElement.size();
                    if(size > 0 )
                        return true;
                    else
                        return false;
                }
            });
        }catch(NoSuchElementException | TimeoutException te){
            return element;
        }

        return element;
    }


    public boolean areListElementVisible(List<WebElement> listElement, long timeOutInSeconds ){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(
                MyThreadLocal.getDriver()).withTimeout(Duration.ofSeconds(timeOutInSeconds))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(org.openqa.selenium.NoSuchElementException.class);
        Boolean element =false;
        try{
            element = wait.until(new Function<WebDriver, Boolean>() {
                @Override
                public Boolean apply(WebDriver t) {
                    int size = 0;
                    size = listElement.size();
                    if(size > 0 )
                        return true;
                    else
                        return false;
                }
            });
        }catch(NoSuchElementException | TimeoutException te){
            return element;
        }

        return element;
    }


}
