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

	@FindBy(css = "lg-select[search-hint='Search Location'] div.input-faked")
	private WebElement locationSelectorButton;

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
		int retryTime = 0;
		boolean isLoaded = isUserNameInputLoaded();
		while (!isLoaded) {
			getDriver().navigate().refresh();
			isLoaded = isUserNameInputLoaded();
			retryTime = retryTime + 1;
			if (retryTime == 6) {
				SimpleUtils.fail("Login page failed to load after waiting for several minutes!", false);
				break;
			}
		}
    	getActiveConsoleName(loginButton);
    	userNameField.clear();
    	passwordField.clear();
    	userNameField.sendKeys(userName);
		passwordField.sendKeys(Password);
		clickTheElement(loginButton);
    }

	private boolean isUserNameInputLoaded() {
		boolean isLoaded = false;
		try {
			if (isElementLoaded(userNameField, 90)) {
				isLoaded = true;
			}
		} catch (Exception e) {
			isLoaded = false;
		}
		return isLoaded;
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

	@FindBy(className = "header-legion-icon")
	private WebElement legionHeaderIcon;
	@Override
	public boolean isLoginSuccess() throws Exception {
		WebDriverWait tempWait = new WebDriverWait(getDriver(), 30);
		try {
			tempWait.until(ExpectedConditions.visibilityOf(legionHeaderIcon));
			return true;
		}
		catch (TimeoutException te) {
			return false;
		}
	}
    
    public void logOut() throws Exception
    {
    	if(isElementLoaded(logoutButton, 10))
    	{
    		clickTheElement(logoutButton);
    	}
    }
    
    public void verifyLoginDone(boolean isLoginDone, String selectedLocation) throws Exception
    {
    	if(isLoginDone){
            getActiveConsoleName(dashboardConsoleName);
            setConsoleWindowHandle(getDriver().getWindowHandle());
            waitForSeconds(5);
            if (isElementLoaded(locationSelectorButton, 30) && locationSelectorButton.getText().contains(selectedLocation)) {
				SimpleUtils.pass("Login to Legion Application " + displayCurrentURL() + " Successfully with selected location: '" + selectedLocation + "'.");
			} else {
				SimpleUtils.fail("Not able to select the location: " + selectedLocation + " Successfully!",false);
			}
    	}else{
    		SimpleUtils.fail("Not able to Login to Legion Application Successfully!",false);
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


	@FindBy(css = "div[class=\"auth-form\"]")
	private WebElement loginPanel;
	@Override
	public void verifyLoginPageIsLoaded() throws Exception {
		try{
			if (isElementLoaded(loginPanel,15)
					&& isElementLoaded(userNameField,5)
					&& isElementLoaded(passwordField, 5)
					&& isElementLoaded(loginButton, 5)) {
				SimpleUtils.pass("Login page is loaded successfully! ");
			}else
				SimpleUtils.fail("Login page not loaded successfully!", false);
		}catch(Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}

	}


	@FindBy(css = "div.create-account")
	private WebElement createAccountMessage;

	@FindBy(css = "[ng-click=\"createAccount()\"]")
	private WebElement signUpLink;

	@FindBy(css = "div[class=\"user-onboarding-content\"]")
	private WebElement createAccountPanel;

	@FindBy(css = "input[placeholder=\"Last name\"]")
	private WebElement lastNameInput;

	@FindBy(css = "input[placeholder=\"Invitation code\"]")
	private WebElement invitationCodeInput;

	@FindBy(css = "button[ng-click=\"checkCodeAndLastName()\"]")
	private WebElement verifyButton;

	@FindBy(css = "div.lg-toast")
	private WebElement errorToast;

	@FindBy(css = "[ng-click=\"confirmEnteredEmail()\"]")
	private WebElement createAccountButton;

	@FindBy(css = "[placeholder=\"Email\"]")
	private WebElement emailInput;

	@FindBy(css = "[placeholder=\"Password\"]")
	private WebElement passwordInput;

	@FindBy(css = "[placeholder=\"Confirm Password\"]")
	private WebElement confirmPasswordInput;


	@Override
	public void verifyCreateAccountMessageDisplayCorrectly() throws Exception {
		if (isElementLoaded(createAccountMessage,15)
				&& createAccountMessage.getText().equalsIgnoreCase("Don't have an account? Sign Up")) {
			SimpleUtils.pass("Create Account Message display correctly! ");
		}else
			SimpleUtils.fail("Create Account Message display incorrectly!", false);
	}

	@Override
	public void clickSignUpLink() throws Exception {
		if (isElementLoaded(signUpLink,15)) {
			clickTheElement(signUpLink);
			SimpleUtils.pass("Click Sign Up link successfully! ");
		}else
			SimpleUtils.fail("Click Sign Up link fail to loaded!", false);
	}

	@Override
	public boolean isVerifyLastNameAndInvitationCodePageLoaded() throws Exception {
		boolean isVerifyLastNameAndInvitationCodePageLoaded = false;
		if (isElementLoaded(createAccountPanel,15)) {
			isVerifyLastNameAndInvitationCodePageLoaded = true;
			SimpleUtils.report("Verify Last Name And Invitation Code Page is loaded successfully! ");
		}else
			SimpleUtils.fail("Verify Last Name And Invitation Code Page fail to loaded!", false);
		return isVerifyLastNameAndInvitationCodePageLoaded;
	}

	@Override
	public void verifyLastNameAndInvitationCode(String lastName, String invitationCode) throws Exception {

		if (isElementLoaded(lastNameInput,15)
				&& isElementLoaded(invitationCodeInput, 15)
				&& isElementLoaded(verifyButton, 15)) {
			lastNameInput.clear();
			lastNameInput.sendKeys(lastName);
			invitationCodeInput.clear();
			invitationCodeInput.sendKeys(invitationCode);
			clickTheElement(verifyButton);

			SimpleUtils.pass("Verify last name and invitation code successfully! ");
		}else
			SimpleUtils.fail("Create Account page fail to loaded!", false);
	}

	@Override
	public boolean isErrorToastLoaded() throws Exception {
		boolean isErrorToastLoaded = false;
		if (isElementLoaded(errorToast,5)) {
			if(errorToast.getText().equals("Error! Last name or Invitation code is incorrect")){
				isErrorToastLoaded = true;
				SimpleUtils.pass("Error toast is loaded successfully! ");
			} else
				SimpleUtils.fail("Error toast is loaded successfully! ", false);
		}else
			SimpleUtils.fail("Error toast failed to load!", false);
		return isErrorToastLoaded = true;
	}

	@Override
	public boolean isCreateAccountPageLoaded() throws Exception {
		boolean isCreateAccountPageLoaded = false;
		if (isElementLoaded(createAccountButton,5)
				&& isElementLoaded(emailInput, 5)
				&& isElementLoaded(passwordInput, 5)
				&& isElementLoaded(confirmPasswordInput, 5)) {
			isCreateAccountPageLoaded = true;
			SimpleUtils.report("Create Account page is loaded successfully! ");
		}else
			SimpleUtils.report("Create Account page fail to loaded!");
		return isCreateAccountPageLoaded;
	}



}
