package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.LoginPage;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;

import static com.legion.utils.MyThreadLocal.*;

public class ConsoleLoginPage extends BasePage implements LoginPage {
    //todo Manideep to replace it with Legion Console-UI login page he has
    
    /* Aug 03- Zorang Team- Variables declaration*/
    
    @FindBy(css="input[placeholder*='Usernam']")
    private WebElement userNameField;
    
    @FindBy(css="[ng-model='password']")
    private WebElement passwordField;
    
    @FindBy(className="login-button-icon")
    private WebElement loginButton;
    
    @FindBy(className="fa-sign-out")
    private WebElement logoutButton;
    
    @FindBy(className="home-dashboard")
    private WebElement legionDashboardSection;
    
    @FindBy (css = "div.console-navigation-item-label.Dashboard")
    private WebElement dashboardConsoleName;


    public ConsoleLoginPage() {
    	PageFactory.initElements(getDriver(), this);
    }

    /* Aug 03-The below line is commented by Zorang Team and new line is added as required */
    //public void goToDashboardHome() throws Exception {
    public void goToDashboardHome(HashMap<String,String> propertyMap) throws Exception {
    	checkElementVisibility(userNameField);
    	getActiveConsoleName(loginButton);
    	userNameField.sendKeys(propertyMap.get("DEFAULT_USERNAME"));
    	passwordField.sendKeys(propertyMap.get("DEFAULT_PASSWORD"));
		click(loginButton);
    }
    
    public void loginToLegionWithCredential(String userName, String Password) throws InterruptedException
    {
    	checkElementVisibility(userNameField);
    	getActiveConsoleName(loginButton);
    	userNameField.clear();
    	passwordField.clear();
    	userNameField.sendKeys(userName);
		passwordField.sendKeys(Password);
		clickTheElement(loginButton);
    }
    
    public boolean isLoginDone() throws Exception
    {
    	WebDriverWait tempWait = new WebDriverWait(getDriver(), 20); 
    	try {
    	    tempWait.until(ExpectedConditions.visibilityOf(legionDashboardSection)); 
    	    return true;
    	}
    	catch (TimeoutException te) {
    		return false;
    	}
    }
    
    public void logOut() throws Exception
    {
    	if(isElementLoaded(logoutButton))
    	{
    		clickTheElement(logoutButton);
    	}
    }
    
    public void verifyLoginDone(boolean isLoginDone, String selectedLocation) throws Exception
    {
    	if(isLoginDone){
            getActiveConsoleName(dashboardConsoleName);
    	    SimpleUtils.pass("Login to Legion Application "+displayCurrentURL()+ " Successfully with selected location: '"+selectedLocation+"'.");
    	}else{
    		SimpleUtils.fail("Not able to Login to Legion Application Successfully!",true);
    	}
    	
    }
    
    //added methods just for POC
    public void goToDashboardHomePage(String username, String pwd) throws Exception {
    	checkElementVisibility(userNameField);
    	getActiveConsoleName(loginButton);
    	userNameField.sendKeys(username);
    	passwordField.sendKeys(pwd);
		click(loginButton);
    }

    public void getActiveConsoleName(WebElement element){
    	activeConsoleName = element.getText();
    	System.out.println(activeConsoleName);
    	setScreenshotConsoleName(activeConsoleName);
    }

	@FindBy(css = "button.btn.sch-publish-confirm-btn")
	private WebElement continueBtnInNewTermsOfServicePopUpWindow;

	@FindBy(css = "div.modal-dialog")
	private WebElement newTermsOfServicePopUpWindow;

	@Override
	public void verifyNewTermsOfServicePopUp() throws Exception {
		if (isElementLoaded(newTermsOfServicePopUpWindow,3)
				&& isElementLoaded(continueBtnInNewTermsOfServicePopUpWindow,3)) {
			click(continueBtnInNewTermsOfServicePopUpWindow);
		}else
			SimpleUtils.report("There is no new terms of service");
	}
}
