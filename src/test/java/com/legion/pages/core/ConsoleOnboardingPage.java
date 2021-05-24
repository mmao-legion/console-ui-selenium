package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.LoginPage;
import com.legion.pages.OnboardingPage;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.net.SocketImpl;
import java.util.List;

import static com.legion.utils.MyThreadLocal.*;
import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleOnboardingPage extends BasePage implements OnboardingPage {

    public ConsoleOnboardingPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public enum onboardingStepsText {
        ReviewCompanyPolicy("Review Company Policy"),
        VerifyProfile("Verify Profile"),
        SetAvailability("Set Availability"),
        ThatsIt("That's it!");
        private final String value;
        onboardingStepsText(final String newValue){
            value = newValue;
        }
        public String getValue(){
            return value;
        }
    }

    private String invitingMessage = getEnterprise() + " has invited you to use Legion";
    private String welcomeMessage = "Welcome to Legion, ";
    private String welcomeBackMsg = "Welcome Back to Legion, ";
    private String loadSuccessfully = " loaded successfully!";
    private String failedLoad = " failed to load!";
    private String expectedCreateAccount = "Create Account";
    private String expectedLoginDescription = "Please enter your last name and the invitation code.";
    private String value = "value";
    private String verifyText = "Verify";
    private String verifyEmailText = "Please verify your email";
    private String importantNotice = "Important Notice from your Employer";
    private String onboarding = "Onboarding";
    private String logInToYourAccountText = "Log in to your account";

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
    private WebElement onboardingBtn;
    @FindBy (id = "email")
    private WebElement emailInput;
    @FindBy (css = "[name=\"password\"]")
    private WebElement passwordInput;
    @FindBy (css = "[name=\"passwordRepeat\"]")
    private WebElement confirmPasswordInput;
    @FindBy (css = ".confirm-email-modal")
    private WebElement confirmEmailDialog;
    @FindBy (css = ".confirm-email-button.confirm")
    private WebElement yesBtn;
    @FindBy (css = ".user-onboarding-login-heading.mb-20")
    private WebElement verifyEmailHeading;
    @FindBy (css = "[class=\"user-onboarding-content ng-scope\"] .user-onboarding-login-button")
    private WebElement resendEmailBtn;
    @FindBy (css = ".header-blue h1")
    private WebElement blueHeader;
    @FindBy (css = "[steps=\"onboaridngSteps\"]")
    private WebElement onboardingSteps;
    @FindBy (css = ".user-onboarding-step-current")
    private WebElement currentOnboardingStep;
    @FindBy (css = "[label=\"Continue\"] [type=\"submit\"]")
    private WebElement continueBtn;
    @FindBy (css = "[form-title=\"My Profile\"]")
    private WebElement myProfileSection;
    @FindBy (css = "[label=\"Back\"] [type=\"button\"]")
    private WebElement backBtn;
    @FindBy (css = "[label=\"Next\"] [type=\"submit\"]")
    private WebElement nextBtn;
    @FindBy (css = ".wm-close-link")
    private WebElement closeBtnOnWelcomeDialog;
    @FindBy (css = "[ng-if*=\"views.LOG_IN\"] .user-onboarding-login-heading")
    private WebElement logInToYourAccount;
    @FindBy (css = "[placeholder=\"Email or Username\"]")
    private WebElement userNameInput;
    @FindBy (css = "[ng-submit=\"rehireLogin()\"] [placeholder=\"Password\"]")
    private WebElement rehirePasswordInput;
    @FindBy (css = "[ng-click=\"rehireLogin()\"]")
    private WebElement rehireSignInBtn;

    @Override
    public void verifyTheContentOfLoginToYourAccountPage() throws Exception {
        try {
            if (isElementLoaded(enterpriseInviteMsg, 10) && enterpriseInviteMsg.getText().equalsIgnoreCase(invitingMessage)) {
                SimpleUtils.pass(enterpriseInviteMsg.getText() + loadSuccessfully);
            } else {
                SimpleUtils.fail(invitingMessage + failedLoad, false);
            }
            if (isElementLoaded(welcomeMsg, 10) && welcomeMsg.getText().equalsIgnoreCase(welcomeBackMsg + getFirstNameForNewHire() + "!")) {
                SimpleUtils.pass(welcomeMsg.getText() + loadSuccessfully);
            } else {
                SimpleUtils.fail(welcomeMessage + getFirstNameForNewHire() + "!" + failedLoad, false);
            }
            if (isElementLoaded(logInToYourAccount, 5) && logInToYourAccount.getText().equalsIgnoreCase(logInToYourAccountText) &&
            isElementLoaded(userNameInput, 5) && isElementLoaded(rehirePasswordInput, 5) && isElementLoaded(rehireSignInBtn, 5)) {
                SimpleUtils.pass(logInToYourAccountText + loadSuccessfully);
            } else {
                SimpleUtils.fail(logInToYourAccountText + failedLoad, false);
            }
        } catch (Exception e) {
            SimpleUtils.fail("Get Exception: " + e.getMessage() + "in Method: verifyTheContentOfLoginToYourAccountPage()", false);
        }
    }

    @Override
    public void verifyRehireLoginToPreviousCredential(String username, String password) throws Exception {
        try {
            if (isElementLoaded(userNameInput, 5) && isElementLoaded(rehirePasswordInput, 5) && isElementLoaded(rehireSignInBtn, 5)) {
                userNameInput.sendKeys(username);
                rehirePasswordInput.sendKeys(password);
                clickTheElement(rehireSignInBtn);
                SimpleUtils.pass("Rehire: Click on Sign In button successfully!");
            } else {
                SimpleUtils.fail(logInToYourAccountText + failedLoad, false);
            }
        } catch (Exception e) {
            SimpleUtils.fail("Get Exception: " + e.getMessage() + "in Method: verifyRehireLoginToPreviousCredential()", false);
        }
    }

    @Override
    public void validateVerifyProfilePageLoaded() throws Exception {
        try {
            if (isElementLoaded(blueHeader, 10) && blueHeader.getText().equalsIgnoreCase(onboarding)
                    && isOnboardingStepsLoaded() && isElementLoaded(currentOnboardingStep, 10) &&
                    currentOnboardingStep.getText().contains(onboardingStepsText.VerifyProfile.getValue())) {
                SimpleUtils.pass(onboarding + loadSuccessfully);
                verifyTheContentOnVerifyProfilePage();
                verifyBackAndNextButtonsLoaded();
            } else {
                SimpleUtils.fail(onboarding + failedLoad, false);
            }
        } catch (Exception e) {
            SimpleUtils.fail("Get Exception: " + e.getMessage() + "in Method: validateVerifyProfilePageLoaded()", false);
        }
    }

    private void verifyTheContentOnVerifyProfilePage() throws Exception {
        if (isElementLoaded(myProfileSection, 10) && myProfileSection.getText().contains("My Profile") &&
        myProfileSection.getText().contains("First Name*") && myProfileSection.getText().contains("Last Name*") && myProfileSection.getText().contains("Nickname") &&
        myProfileSection.getText().contains("Phone Number") && myProfileSection.getText().contains("Email") && myProfileSection.getText().contains("This email cannot be edited during onboarding.")
        && myProfileSection.getText().contains("Address") && myProfileSection.getText().contains("Apt, Suite, Unit #") &&
                myProfileSection.getText().contains("Country") && myProfileSection.getText().contains("City") && myProfileSection.getText().contains("State")
        && myProfileSection.getText().contains("Zip Code")) {
            SimpleUtils.pass("My Profile section" + loadSuccessfully);
        } else {
            SimpleUtils.fail("My profile section" + failedLoad, false);
        }
    }

    private void verifyBackAndNextButtonsLoaded() throws Exception {
        if (isElementLoaded(backBtn, 5) && isElementLoaded(nextBtn, 5)) {
            SimpleUtils.pass("Back and Next buttons are loaded successfully!");
        } else {
            SimpleUtils.fail("Back and Next buttons failed to load!", false);
        }
    }

    @Override
    public void clickOnButtonByLabel(String label) throws Exception {
        try {
            if (isElementLoaded(closeBtnOnWelcomeDialog, 5)) {
                clickTheElement(closeBtnOnWelcomeDialog);
            }
            scrollToBottom();
            waitForSeconds(1);
            String locator = "[label=\"" + label + "\"] [type=\"submit\"]";
            if (isElementLoaded(getDriver().findElement(By.cssSelector(locator)), 10)) {
                clickTheElement(getDriver().findElement(By.cssSelector(locator)));
                SimpleUtils.pass("Click on \"" + label + "\" Successfully!");
            } else {
                SimpleUtils.fail(label + failedLoad, false);
            }
        } catch (Exception e) {
            SimpleUtils.fail("Get Exception: " + e.getMessage() + "in Method: clickOnButtonByLabel()", false);
        }
    }

    @Override
    public void verifyImportantNoticeFromYourEmployerPageLoaded() throws Exception {
        try {
            // Need to wait for seconds since first load, the page title will show "Onboarding", then it will show the "Important Notice from your Employer"
            waitForSeconds(5);
            if (isElementLoaded(blueHeader, 10) && blueHeader.getText().equalsIgnoreCase(importantNotice)
                    && isOnboardingStepsLoaded() && isElementLoaded(currentOnboardingStep, 10) &&
            currentOnboardingStep.getText().contains(onboardingStepsText.ReviewCompanyPolicy.getValue()) && isElementLoaded(continueBtn, 10)) {
                SimpleUtils.pass(importantNotice + loadSuccessfully);
            } else {
                SimpleUtils.fail(importantNotice + failedLoad, false);
            }
        } catch (Exception e) {
            SimpleUtils.fail("Get Exception: " + e.getMessage() + "in Method: verifyImportantNoticeFromYourEmployerPageLoaded()", false);
        }
    }

    private boolean isOnboardingStepsLoaded() throws Exception {
        boolean isLoaded = false;
        if (isElementLoaded(onboardingSteps, 10)) {
            if (getCompanyPolicy()) {
                if (onboardingSteps.getText().contains(onboardingStepsText.ReviewCompanyPolicy.getValue()) &&
                onboardingSteps.getText().contains(onboardingStepsText.VerifyProfile.getValue()) &&
                        onboardingSteps.getText().contains(onboardingStepsText.SetAvailability.getValue()) &&
                        onboardingSteps.getText().contains(onboardingStepsText.ThatsIt.getValue())) {
                    isLoaded = true;
                }
            } else {
                if (onboardingSteps.getText().contains(onboardingStepsText.VerifyProfile.getValue()) &&
                        onboardingSteps.getText().contains(onboardingStepsText.SetAvailability.getValue()) &&
                        onboardingSteps.getText().contains(onboardingStepsText.ThatsIt.getValue())) {
                    isLoaded = true;
                }
            }
        }
        return isLoaded;
    }

    @Override
    public void verifyPleaseVerifyYourEmailPageLoaded() throws Exception {
        try {
            if (isElementLoaded(verifyEmailHeading, 10) && verifyEmailHeading.getText().equalsIgnoreCase(verifyEmailText)
            && isElementLoaded(resendEmailBtn, 10)) {
                SimpleUtils.pass(verifyEmailText + loadSuccessfully);
            } else {
                SimpleUtils.fail(verifyEmailText + failedLoad, false);
            }
        } catch (Exception e) {
            SimpleUtils.fail("Get Exception: " + e.getMessage() + "in Method: verifyPleaseVerifyYourEmailPageLoaded()", false);
        }
    }

    @Override
    public void clickYesBtnOnIsEmailCorrectDialog() throws Exception {
        try {
            if (isElementLoaded(yesBtn, 10)) {
                clickTheElement(yesBtn);
                SimpleUtils.pass("Click on Yes button on \"Is this email correct?\" dialog successfully!");
            } else {
                SimpleUtils.fail("Yes button failed to load on \"Is this email correct?\" dialog!", false);
            }
        } catch (Exception e) {
            SimpleUtils.fail("Get Exception: " + e.getMessage() + "in Method: clickYesBtnOnIsEmailCorrectDialog()", false);
        }
    }

    @Override
    public void verifyIsEmailCorrectDialogPopup() throws Exception {
        try {
            if (isElementLoaded(confirmEmailDialog, 10)) {
                SimpleUtils.pass(" \"Is this email correct?\" dialog pops up!");
            } else {
                SimpleUtils.fail(" \"Is this email correct?\" dialog failed to pop up!", false);
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Override
    public void createAccountForNewHire(String password) throws Exception {
        try {
            if (isElementLoaded(emailInput, 10) && isElementLoaded(passwordInput, 10) && isElementLoaded(confirmPasswordInput, 10)) {
                emailInput.sendKeys(getEmailAccount());
                passwordInput.sendKeys(password);
                confirmPasswordInput.sendKeys(password);
                if (isElementLoaded(onboardingBtn, 10) && onboardingBtn.getText().equalsIgnoreCase(expectedCreateAccount)) {
                    clickTheElement(onboardingBtn);
                    SimpleUtils.pass("Click on " + expectedCreateAccount + " button successfully!");
                } else {
                    SimpleUtils.fail(expectedCreateAccount + failedLoad, false);
                }
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Override
    public boolean isCreateAccountPageLoadedAfterVerifyingLastName() throws Exception {
        boolean isLoaded = false;
        try {
            if (isElementLoaded(emailInput, 10)) {
                isLoaded = true;
            }
        } catch (Exception e) {
            isLoaded = false;
        }
        return isLoaded;
    }

    @Override
    public void verifyLastName(String lastName) throws Exception {
        try {
            if (isElementLoaded(lastNameInput, 10) && isElementLoaded(onboardingBtn, 10)
            && onboardingBtn.getText().equalsIgnoreCase(verifyText)) {
                lastNameInput.sendKeys(lastName);
                clickTheElement(onboardingBtn);
            } else {
                SimpleUtils.fail("Verify Last Name failed!", false);
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

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
            invitationCodeInput.getAttribute(value).equalsIgnoreCase(invitationCode) && isElementLoaded(onboardingBtn, 10) && onboardingBtn.getText().equalsIgnoreCase(verifyText)) {
                SimpleUtils.pass(expectedCreateAccount + loadSuccessfully);
            } else {
                SimpleUtils.fail(expectedCreateAccount + failedLoad, false);
            }
        } catch (Exception e) {
            SimpleUtils.fail("The content on Create Account page is incorrect!", false);
        }
    }


    @Override
    public void verifySetAvailabilityPageLoaded() throws Exception {
        try {
            if (isElementLoaded(blueHeader, 10) && blueHeader.getText().equalsIgnoreCase(onboarding)
                    && isOnboardingStepsLoaded() && isElementLoaded(currentOnboardingStep, 10) &&
                    currentOnboardingStep.getText().contains(onboardingStepsText.SetAvailability.getValue())) {
                SimpleUtils.pass(onboarding +onboardingStepsText.SetAvailability.getValue() + " page " + loadSuccessfully);
                verifyTheContentOnSetAvailabilityPage();
            } else {
                SimpleUtils.fail(onboarding +onboardingStepsText.SetAvailability.getValue() + " page " + failedLoad, false);
            }
        } catch (Exception e) {
            SimpleUtils.fail("Get Exception: " + e.getMessage() + "in Method: verifySetAvailabilityPageLoaded()", false);
        }
    }


    @FindBy (css = "form-section[form-title=\"My Work Preferences\"]")
    private WebElement myWorkPreferencesSection;

    @FindBy (css = "form-section[form-title=\"My availability\"]")
    private WebElement myAvailabilitySection;

    @FindBy (css = "[ng-show=\"index === 2\"] lg-button[label=\"Back\"]")
    private WebElement backButtonOnSetAvailabilityPage;

    @FindBy (css = "[ng-show=\"index === 2\"] lg-button[label=\"Next\"] button")
    private WebElement nextButtonOnSetAvailabilityPage;

    @FindBy (css = "div.text-center.user-onboarding-steps__final")
    private List<WebElement> sectionsOnThatsItPage;

    @FindBy (css = "lg-button[label=\"Go Back\"]")
    private WebElement goBackButtonOnThatsItPage;

    @FindBy (css = "lg-button[label=\"Done\"]")
    private WebElement doneButtonOnThatsItPage;


    private void verifyTheContentOnSetAvailabilityPage() throws Exception {

        if (isElementLoaded(myAvailabilitySection, 5)
                && isElementLoaded(myWorkPreferencesSection, 5)
                && isElementLoaded(backButtonOnSetAvailabilityPage, 5)
                && isElementLoaded(nextButtonOnSetAvailabilityPage, 5)){
            SimpleUtils.pass("The content on set availability page display correctly ! ");
        } else
            SimpleUtils.fail("The content on set availability page display incorrectly! ", false);
    }

    public void clickOnNextButtonOnSetAvailabilityPage() throws Exception {
        if (isElementLoaded(nextButtonOnSetAvailabilityPage, 10)) {
            clickTheElement(nextButtonOnSetAvailabilityPage);
            SimpleUtils.pass("Click Next button on set availability page successfully! ");
        } else
            SimpleUtils.fail("Next button on set availability page fail to load! ", false);
    }

    @Override
    public void verifyThatsItPageLoaded() throws Exception {
        try {
            if (isElementLoaded(blueHeader, 10) && blueHeader.getText().equalsIgnoreCase(onboarding)
                    && isOnboardingStepsLoaded() && isElementLoaded(currentOnboardingStep, 10) &&
                    currentOnboardingStep.getText().contains(onboardingStepsText.ThatsIt.getValue())) {
                SimpleUtils.pass(onboarding +onboardingStepsText.ThatsIt.getValue() + " page " + loadSuccessfully);
                verifyTheContentOnThatsItPage();
            } else {
                SimpleUtils.fail(onboarding +onboardingStepsText.ThatsIt.getValue() + " page " + failedLoad, false);
            }
        } catch (Exception e) {
            SimpleUtils.fail("Get Exception: " + e.getMessage() + "in Method: verifyThatIsItPageLoaded()", false);
        }
    }

    private void verifyTheContentOnThatsItPage() throws Exception {

        if (areListElementVisible(sectionsOnThatsItPage, 5)
                && sectionsOnThatsItPage.size()==2
                && isElementLoaded(goBackButtonOnThatsItPage, 5)
                && isElementLoaded(doneButtonOnThatsItPage, 5)){
            SimpleUtils.pass("The content on That's It page display correctly ! ");
        } else
            SimpleUtils.fail("The content on That's It page display incorrectly! ", false);
    }

    public void clickOnDoneOnThatsItPage() throws Exception {
        if (isElementLoaded(doneButtonOnThatsItPage, 5)) {
            click(doneButtonOnThatsItPage);
            SimpleUtils.pass("Click Done button on That's It page successfully! ");
        } else
            SimpleUtils.fail("Done button on That's It page fail to load! ", false);
    }
}
