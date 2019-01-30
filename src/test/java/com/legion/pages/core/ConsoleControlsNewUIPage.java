package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.legion.pages.BasePage;
import com.legion.pages.ControlsNewUIPage;
import com.legion.utils.SimpleUtils;



public class ConsoleControlsNewUIPage extends BasePage implements ControlsNewUIPage{

	public ConsoleControlsNewUIPage(){
		PageFactory.initElements(getDriver(), this);
	}
	
	
	@FindBy(css = "div.console-navigation-item-label.Controls")
	private WebElement controlsConsoleMenuDiv;
	
	@FindBy(css = "div.header-navigation-label")
	private WebElement controlsPageHeaderLabel;
	
	String timeSheetHeaderLabel = "Controls";
	
	@Override
	public void clickOnControlsConsoleMenu() throws Exception {
		if(isElementLoaded(controlsConsoleMenuDiv))
			click(controlsConsoleMenuDiv);
		else
			SimpleUtils.fail("Controls Console Menu not loaded Successfully!", false);
	}


	@Override
	public boolean isControlsPageLoaded() throws Exception {
		if(isElementLoaded(controlsPageHeaderLabel))
			if(controlsPageHeaderLabel.getText().toLowerCase().contains(timeSheetHeaderLabel.toLowerCase())) {
				SimpleUtils.pass("Controls Page loaded Successfully!");
				return true;
			}
		return false;
	}


	@FindBy(css = "input-field[placeholder=\"Global\"]")
	private WebElement globalLocationButton;
	
	@Override
	public void clickOnGlobalLocationButton() throws Exception {
		
		if(isElementLoaded(globalLocationButton))
			click(globalLocationButton);
		else
			SimpleUtils.fail("Controls Page: Global Location Button not Loaded!", false);
	}


	@FindBy(css = "lg-dashboard-card[title=\"Company Profile\"]")
	private WebElement companyProfileCard;
	
	@Override
	public void clickOnControlsCompanyProfileCard() throws Exception {
		if(isElementLoaded(companyProfileCard))
			click(companyProfileCard);
		else
			SimpleUtils.fail("Controls Page: Company Profile Card not Loaded!", false);
	}
	
	@FindBy(css = "input[aria-label=\"Company Name\"]")
	private WebElement locationCompanyNameField;
	
	@FindBy(css = "input[aria-label=\"Business Address\"]")
	private WebElement locationBusinessAddressField;
	
	@FindBy(css = "input[aria-label=\"City\"]")
	private WebElement locationCityField;
	
	@FindBy(css = "select[aria-label=\"State\"]")
	private WebElement locationStateField;

	@FindBy(css = "select[aria-label=\"Province\"]")
	private WebElement locationProvinceField;
	
	@FindBy(css = "select[aria-label=\"Country\"]")
	private WebElement locationCountryField;
	
	@FindBy(css = "input[aria-label=\"Zip Code\"]")
	private WebElement locationZipCodeField;
	
	@FindBy(css = "input[aria-label=\"Postal Code\"]")
	private WebElement locationPostalCodeField;
	
	@FindBy(css = "select[aria-label=\"Time Zone\"]")
	private WebElement locationTimeZoneField;
	
	@FindBy(css = "input[aria-label=\"Website\"]")
	private WebElement locationWebsiteField;
	
	
	@FindBy(css = "input[aria-label=\"First Name\"]")
	private WebElement locationFirstNameField;
	
	@FindBy(css = "input[aria-label=\"Last Name\"]")
	private WebElement locationLastNameField;
	
	@FindBy(css = "input[aria-label=\"E-mail\"]")
	private WebElement locationEmailField;
	
	@FindBy(css = "input[aria-label=\"Phone\"]")
	private WebElement locationPhoneField;
	
	@FindBy(css = "lg-button[label=\"Save\"]")
	private List<WebElement> locationProfileSaveBtn;

	@Override
	public void updateUserLocationProfile(String companyName, String businessAddress, String city, String state,
			String country, String zipCode, String timeZone, String website, String firstName, String lastName,
			String email, String phone) throws Exception {
		
		System.out.println("updateUserLocationProfile called");
		locationCompanyNameField.clear();
		locationCompanyNameField.sendKeys(companyName);
		
		locationBusinessAddressField.clear();
		locationBusinessAddressField.sendKeys(businessAddress);
		
		locationCityField.clear();
		locationCityField.sendKeys(city);
		
		Select drpCountry = new Select(locationCountryField);
		drpCountry.selectByVisibleText(country);
		
		try {
			Select drpStates = new Select(locationStateField);
			drpStates.selectByVisibleText(state);			
		}catch (Exception e) {
			Select drpStates = new Select(locationProvinceField);
			drpStates.selectByVisibleText(state);
		}
	
		try {
			locationZipCodeField.clear();
			locationZipCodeField.sendKeys(zipCode);	
		}catch (Exception e) {
			locationPostalCodeField.clear();
			locationPostalCodeField.sendKeys(zipCode);	
		}
		
		
		
		
		Select drpTimeZone = new Select(locationTimeZoneField);
		drpTimeZone.selectByVisibleText(timeZone);

		locationWebsiteField.clear();
		locationWebsiteField.sendKeys(website);
		
		locationFirstNameField.clear();
		locationFirstNameField.sendKeys(firstName);
		
		locationLastNameField.clear();
		locationLastNameField.sendKeys(lastName);
		
		locationEmailField.clear();
		locationEmailField.sendKeys(email);
		
		locationPhoneField.clear();
		locationPhoneField.sendKeys(phone);
		
		if(locationProfileSaveBtn.size() > 0)
			click(locationProfileSaveBtn.get(0));
		else
			SimpleUtils.fail("Controls Page: Location Profile Save button not loaded.", false);
		
	}
	
	@Override
	public boolean isUserLocationProfileUpdated(String companyName, String businessAddress, String city, String state,
			String country, String zipCode, String timeZone, String website, String firstName, String lastName,
			String email, String phone) throws Exception {
		boolean isProfileValueUpdated = true;
		
		if(! locationCompanyNameField.getAttribute("value").contains(companyName))
		{
			isProfileValueUpdated = false;
			SimpleUtils.fail("Controls Page: Updating Location Profile - 'Company Name' not updated.", true);
		}
			
		if(! locationBusinessAddressField.getAttribute("value").contains(businessAddress))
		{
			isProfileValueUpdated = false;
			SimpleUtils.fail("Controls Page: Updating Location Profile - 'Business Address' not updated.", true);
		}
		
		
		if(! locationCityField.getAttribute("value").contains(city))
		{
			isProfileValueUpdated = false;
			SimpleUtils.fail("Controls Page: Updating Location Profile - 'City' not updated.", true);
		}
		
		/*Select drpCountry = new Select(locationCountryField);
		if(! drpCountry.getFirstSelectedOption().getText().contains(country))
		{
			isProfileValueUpdated = false;
			SimpleUtils.fail("Controls Page: Updating Location Profile - 'Country' not updated.", true);
		}*/
		
		try {
			Select drpStates = new Select(locationStateField);		
			if(! drpStates.getFirstSelectedOption().getText().contains(state))
			{
				isProfileValueUpdated = false;
				SimpleUtils.fail("Controls Page: Updating Location Profile - 'State' not updated.", true);
			}			
		}catch (Exception e) {
			Select drpStates = new Select(locationProvinceField);		
			if(! drpStates.getFirstSelectedOption().getText().contains(state))
			{
				isProfileValueUpdated = false;
				SimpleUtils.fail("Controls Page: Updating Location Profile - 'Province' not updated.", true);
			}
		}
	
		try {
			if(! locationZipCodeField.getAttribute("value").contains(zipCode))
			{
				isProfileValueUpdated = false;
				SimpleUtils.fail("Controls Page: Updating Location Profile - 'Zip Code' not updated.", true);
			}
		}catch (Exception e) {
			if(! locationPostalCodeField.getAttribute("value").contains(zipCode))
			{
				isProfileValueUpdated = false;
				SimpleUtils.fail("Controls Page: Updating Location Profile - 'Postal Code' not updated.", true);
			}
		}
		
		
		/*if(! locationZipCodeField.getAttribute("value").contains(zipCode))
		{
			isProfileValueUpdated = false;
			SimpleUtils.fail("Controls Page: Updating Location Profile - 'Zip Code' not updated.", true);
		}*/
		
		Select drpTimeZone = new Select(locationTimeZoneField);
		if(! drpTimeZone.getFirstSelectedOption().getText().contains(timeZone))
		{
			isProfileValueUpdated = false;
			SimpleUtils.fail("Controls Page: Updating Location Profile - 'Time Zone' not updated.", true);
		}
		
		if(! locationWebsiteField.getAttribute("value").contains(website))
		{
			isProfileValueUpdated = false;
			SimpleUtils.fail("Controls Page: Updating Location Profile - 'Website' not updated.", true);
		}
		
		if(! locationFirstNameField.getAttribute("value").contains(firstName))
		{
			isProfileValueUpdated = false;
			SimpleUtils.fail("Controls Page: Updating Location Profile - 'First Name' not updated.", true);
		}

		if(! locationLastNameField.getAttribute("value").contains(lastName))
		{
			isProfileValueUpdated = false;
			SimpleUtils.fail("Controls Page: Updating Location Profile - 'Last Name' not updated.", true);
		}

		if(! locationEmailField.getAttribute("value").contains(email))
		{
			isProfileValueUpdated = false;
			SimpleUtils.fail("Controls Page: Updating Location Profile - 'E-Mail' not updated.", true);
		}

		if(! locationPhoneField.getAttribute("value").contains(phone))
		{
			isProfileValueUpdated = false;
			SimpleUtils.fail("Controls Page: Updating Location Profile - 'Phone' not updated.", true);
		}
		
		return isProfileValueUpdated;
	}
	
}
