package com.legion.pages.core.mobile;

import static com.legion.utils.MyThreadLocal.getAndroidDriver;
import static com.legion.utils.MyThreadLocal.getDriver;
import io.appium.java_client.MobileElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.legion.pages.BasePage;
import com.legion.pages.LoginPage;
import com.legion.pages.mobile.LoginPageAndroid;
import com.legion.tests.TestBase;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileLoginPage extends BasePage implements LoginPageAndroid {
	
	 @FindBy(id="co.legion.client.staging:id/loginBTN")
	 private WebElement btnSelectLogin;
	 
	 @FindBy(id="co.legion.client.staging:id/tv_title")
	 private WebElement titleLogin;
	 
	 @FindBy(id="co.legion.client.staging:id/buildType")
	 private WebElement clickEnterprise;
	 
	 @FindBy(id="co.legion.client.staging:id/usernameET")
	 private WebElement userNameMobile;
	 
	 @FindBy(id="co.legion.client.staging:id/passwordEditText")
	 private WebElement passwordMobile;
	 
	 @FindBy(id="co.legion.client.staging:id/login")
	 private WebElement loginBtn;

	@FindBy(xpath="//android.widget.TextView[@text='Shift Offers']")
	private WebElement shiftOffers;

	@FindBy(id="co.legion.client.staging:id/subDayLayout")
	private WebElement shiftOfferDay;

	@FindBy(id="co.legion.client.staging:id/statTimeTV")
	private WebElement shiftOfferStartTime;

	@FindBy(id="co.legion.client.staging:id/endTimeTV")
	private WebElement shiftOfferEndTime;

	@FindBy(id="co.legion.client.staging:id/shiftTypeNameTV")
	private WebElement shiftOfferAvail;



	public MobileLoginPage() {
    	PageFactory.initElements(getAndroidDriver(), this);
    }

	@Override
	public void clickFirstLoginBtn() throws Exception {
		// TODO Auto-generated method stub
		if(isElementLoadedOnMobile(btnSelectLogin)){
			clickOnMobileElement(btnSelectLogin);
			SimpleUtils.pass("First Login Button clicked Successfully!");
		}else{
			MyThreadLocal.setPlatformName("mobile");
			SimpleUtils.fail("First Login Button not clicked Successfully!", false);
		}

	}
	
	public void verifyLoginTitle(String textLogin) throws Exception{
		if(isElementLoadedOnMobile(titleLogin)){
			if(titleLogin.getText().equalsIgnoreCase(textLogin)){
				SimpleUtils.pass("Login Title "+ textLogin + " matches with "+titleLogin);
			}else{
				MyThreadLocal.setPlatformName("mobile");
				SimpleUtils.fail("Login Title "+ textLogin + " not matches with "+titleLogin, true);
			}
		}else{
			SimpleUtils.fail("Login Text not available on page!", true);
		}
	}
	
	public void selectEnterpriseName() throws Exception{
		waitForSeconds(2);
		if(isElementLoadedOnMobile(clickEnterprise)){
			clickOnMobileElement(clickEnterprise);
			waitForSeconds(2);
			getAndroidDriver().findElementByAndroidUIAutomator("new UiSelector().text(\"Staging\")").click();
			SimpleUtils.pass("Enterprise Stage clicked Successfully!");
		}else{
			MyThreadLocal.setPlatformName("mobile");
			SimpleUtils.fail("Enterprise Stage not clicked Successfully!",false);
		}
	}
	
	public void loginToLegionWithCredentialOnMobile(String userName, String Password) throws Exception
    {
    	waitForSeconds(2);
    	if(isElementLoadedOnMobile(userNameMobile)){
    		userNameMobile.sendKeys(userName);
    		waitForSeconds(3);
    		SimpleUtils.pass("Username entered Successfully!");
    	}else{
    		MyThreadLocal.setPlatformName("mobile");
    		SimpleUtils.fail("Username not entered Successfully!",false);
    	}
    	
    	if(isElementLoadedOnMobile(passwordMobile)){
    		passwordMobile.sendKeys(Password);
    		waitForSeconds(3);
    		SimpleUtils.pass("Password entered Successfully!");
    	}else{
    		MyThreadLocal.setPlatformName("mobile");
    		SimpleUtils.fail("Password not entered Successfully!",false);
    	}
    	
    	clickOnMobileElement(loginBtn);
    }


    public void clickShiftOffers(String teamMember) throws Exception{
		waitForSeconds(8);
		getAndroidDriver().findElementByAndroidUIAutomator("new UiSelector().text(\"Shift Offers\")").click();
		WebElement shiftOfferAvailable = getAndroidDriver().findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"OPEN SHIFTS\")");
		if(isElementLoadedOnMobile(shiftOfferAvail)){
			SimpleUtils.pass("Shift offer available for "+teamMember);
		}else{
			SimpleUtils.fail("Shift offer not available for "+teamMember,false);
		}

//

	}
	
}
