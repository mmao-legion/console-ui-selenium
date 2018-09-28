package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import com.aventstack.extentreports.ExtentTest;
import com.legion.pages.BasePage;
import com.legion.pages.LoginPage;
import com.legion.utils.SimpleUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;

public class ConsoleLoginPage extends BasePage implements LoginPage {
    //todo Manideep to replace it with Legion Console-UI login page he has
    
    /* Aug 03- Zorang Team- Variables declaration*/
    
    @FindBy(css="[ng-model='username']")
    private WebElement userNameField;
    
    @FindBy(css="[ng-model='password']")
    private WebElement passwordField;
    
    @FindBy(xpath="//div[@ng-click='loginClicked($event)']")
    private WebElement loginButton;
    
    @FindBy(className="fa-sign-out")
    private WebElement logoutButton;
    
    @FindBy(className="home-dashboard")
    private WebElement legionDashboardSection;
    
    
    public ConsoleLoginPage() {
//    	super(driver);
    	PageFactory.initElements(getDriver(), this);
    }

    /* Aug 03-The below line is commented by Zorang Team and new line is added as required */
    //public void goToDashboardHome() throws Exception {
    public void goToDashboardHome(HashMap<String,String> propertyMap) throws Exception {
    	checkElementVisibility(userNameField);
    	userNameField.sendKeys(propertyMap.get("DEFAULT_USERNAME"));
    	passwordField.sendKeys(propertyMap.get("DEFAULT_PASSWORD"));
		click(loginButton);
    }
    
    public void loginToLegionWithCredential(HashMap<String,String> propertyMap, String userName, String Password) throws Exception
    {
    	checkElementVisibility(userNameField);
    	userNameField.clear();
    	passwordField.clear();
    	userNameField.sendKeys(userName);
		passwordField.sendKeys(Password);
		click(loginButton);
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
    		click(logoutButton);
    	}
    }
    
    public void verifyLoginDone(boolean isLoginDone) throws Exception
    {
    	if(isLoginDone){
    		SimpleUtils.pass("Login to Legion Application Successfully!");
    	}else{
    		SimpleUtils.fail("Not bale to Login to Legion Application Successfully!",true);
    	}
    	
    }
    
    


}
