package com.legion.tests.testframework;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import com.legion.pages.BasePage;
import com.legion.tests.TestBase;

import static com.legion.utils.MyThreadLocal.getDriver;
import static com.legion.utils.MyThreadLocal.getURL;
import static com.legion.utils.MyThreadLocal.*;

public class LegionWebDriverEventListener implements WebDriverEventListener {
	
	private String elementTextToReport = "";
    private String elementType = "";
    private boolean selected, isPopover, isClearable;

	@Override
	public void beforeAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeNavigateTo(String url, WebDriver driver) {
		// TODO Auto-generated method stub	 
	}

	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeClickOn(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
        ScreenshotManager.takeScreenShot();
		
	}

	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			setScreenshotConsoleName(BasePage.activeConsoleName);
			ScreenshotManager.takeScreenShot();
		}
	
	}

	@Override
	public void beforeChangeValueOf(WebElement element, WebDriver driver,
			CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterChangeValueOf(WebElement element, WebDriver driver,
			CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeSwitchToWindow(String windowName, WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterSwitchToWindow(String windowName, WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onException(Throwable throwable, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	
}
