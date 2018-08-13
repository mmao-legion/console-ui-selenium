package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

public class ConsoleLoginPage extends BasePage implements LoginPage {
    //todo Manideep to replace it with Legion Console-UI login page he has
    public ConsoleLoginPage(WebDriver driver) {
        super(driver);
    }
    /* Aug 03- Zorang Team- Variables declaration*/
    private By userNameField = By.cssSelector("[ng-model='username']");
    private By passwordField = By.cssSelector("[ng-model='password']");
    private By loginButton = By.cssSelector("[ng-click='loginClicked($event)']");


    public void visitPage(HashMap<String, String> propertyMap) {
        this.driver.get(String.format("%s/legion/?enterprise=%s#",propertyMap.get("DEVURL"),propertyMap.get("ENTERPRISE")));
    }


    /* Aug 03-The below line is commented by Zorang Team and new line is added as required */
    //public void goToDashboardHome() throws Exception {
    public void goToDashboardHome(HashMap<String,String> propertyMap) throws Exception {
    	waitForElement(userNameField);
    	driver.findElement(userNameField).sendKeys(propertyMap.get("DEFAULT_USERNAME"));
		driver.findElement(passwordField).sendKeys(propertyMap.get("DEFAULT_PASSWORD"));
		waitForElement(loginButton);
		click(loginButton);
    }
}
