package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.LoginPage;
import com.legion.pages.OnboardingPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.legion.utils.MyThreadLocal.*;
import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleOnboardingPage extends BasePage implements OnboardingPage {

    public ConsoleOnboardingPage() {
        PageFactory.initElements(getDriver(), this);
    }

    private String invitingMessage = getEnterprise() + " has invited you to use Legion";
    private String welcomeMessage = "Welcome to Legion, ";
    private String loadSuccessfully = " loaded successfully!";
    private String failedLoad = " failed to load!";
    private String expectedCreateAccount = "Create Account";
    private String expectedLoginDescription = "Please enter your last name and the invitation code.";
    private String value = "value";

    @FindBy (css = ".user-onboarding-top-ribbon span")
    private WebElement enterpriseInviteMsg;
    @FindBy (css = ".text-center h3")
    private WebElement welcomeMsg;
    @FindBy (css = "[class=\"user-onboarding-content\"] .user-onboarding-login-heading")
    private WebElement createAccount;
    @FindBy (css = "[class=\"user-onboarding-content\"] .user-onboarding-login-description")
    private WebElement loginDescription;
    @FindBy (css = "[placeholder=\"Last Name\"]")
    private WebElement lastNameInput;
    @FindBy (css = "#code")
    private WebElement invitationCodeInput;
    @FindBy (css = "[class=\"user-onboarding-content\"] .user-onboarding-login-button")
    private WebElement verifyBtn;

    @Override
    public void openOnboardingPage(String invitationCode, String firstName, boolean isRehired, String enterpriseDisplayName) {
        try {
            getDriver().get(getURL() + "legion/?enterprise=" + getEnterprise() + "#/user-onboarding?code=" + invitationCode
            + "&firstName=" + firstName + "&isRehired=" + isRehired + "&enterpriseDisplayName=" + enterpriseDisplayName);
        } catch (TimeoutException te) {
            try {
                getDriver().navigate().refresh();
            } catch (TimeoutException te1) {
                SimpleUtils.fail("Page failed to load", false);
            }
        } catch (WebDriverException we) {
            try {
                getDriver().navigate().refresh();
            } catch (TimeoutException te1) {
                SimpleUtils.fail("Page failed to load", false);
            }
        }
    }

    @Override
    public void verifyTheContentOfCreateAccountPage(String firstName, String invitationCode) throws Exception {
        try {
            if (isElementLoaded(enterpriseInviteMsg, 10) && enterpriseInviteMsg.getText().equalsIgnoreCase(invitingMessage)) {
                SimpleUtils.pass(enterpriseInviteMsg.getText() + loadSuccessfully);
            } else {
                SimpleUtils.fail(invitingMessage + failedLoad, false);
            }
            if (isElementLoaded(welcomeMsg, 10) && welcomeMsg.getText().equalsIgnoreCase(welcomeMessage + firstName + "!")) {
                SimpleUtils.pass(welcomeMsg.getText() + loadSuccessfully);
            } else {
                SimpleUtils.fail(welcomeMessage + firstName + "!" + failedLoad, false);
            }
            if (isElementLoaded(createAccount, 10) && createAccount.getText().equalsIgnoreCase(expectedCreateAccount) &&
            isElementLoaded(loginDescription, 10) && loginDescription.getText().equalsIgnoreCase(expectedLoginDescription) &&
            isElementLoaded(lastNameInput, 10) && isElementLoaded(invitationCodeInput, 10) &&
            invitationCodeInput.getAttribute(value).equalsIgnoreCase(invitationCode) && isElementLoaded(verifyBtn, 10)) {
                SimpleUtils.pass(expectedCreateAccount + loadSuccessfully);
            } else {
                SimpleUtils.fail(expectedCreateAccount + failedLoad, false);
            }
        } catch (Exception e) {
            SimpleUtils.fail("The content on Create Account page is incorrect!", false);
        }
    }
}
