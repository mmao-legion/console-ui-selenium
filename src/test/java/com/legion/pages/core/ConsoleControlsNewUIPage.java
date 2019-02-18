package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.legion.pages.BasePage;
import com.legion.pages.ControlsNewUIPage;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;



public class ConsoleControlsNewUIPage extends BasePage implements ControlsNewUIPage{

	public ConsoleControlsNewUIPage(){
		PageFactory.initElements(getDriver(), this);
	}
	
	
	@FindBy(css = "div.console-navigation-item-label.Controls")
	private WebElement controlsConsoleMenuDiv;
	
	@FindBy(css = "div.header-navigation-label")
	private WebElement controlsPageHeaderLabel;
	
	@FindBy(css = "lg-dashboard-card[title=\"Company Profile\"]")
	private WebElement companyProfileCard;
	
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
	
	@FindBy(css = "lg-dashboard-card[title=\"Working Hours\"]")
	private WebElement workingHoursCard;
	
	@FindBy(css = "collapsible[block-title=\"'Regular'\"]")
	private WebElement regularHoursBlock;
	
	@FindBy(css = "day-working-hours[id=\"day.dayOfTheWeek\"]")
	private List<WebElement> regularHoursRows;
	
	@FindBy(css = "input-field[type=\"checkbox\"]")
	private List<WebElement> regularHoursDaysCheckboxs;
	
	@FindBy(css = "div.lgn-time-slider-post")
	private List<WebElement> editRegularHoursSliders;
	
	@FindBy(css = "lg-button[ng-click=\"saveWorkingHours()\"]")
	private WebElement saveWorkersHoursBtn;
	
	@FindBy(css = "lg-button[ng-click=\"saveWorkdayHours()\"]")
	private WebElement saveAllRegularHoursBtn;
	
	
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

	
	@Override
	public void clickOnControlsCompanyProfileCard() throws Exception {
		if(isElementLoaded(companyProfileCard))
			click(companyProfileCard);
		else
			SimpleUtils.fail("Controls Page: Company Profile Card not Loaded!", false);
	}
	

	@Override
	public void updateUserLocationProfile(String companyName, String businessAddress, String city, String state,
			String country, String zipCode, String timeZone, String website, String firstName, String lastName,
			String email, String phone) throws Exception {
		
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
	
	@Override
	public void clickOnControlsWorkingHoursCard() throws Exception {
		if(isElementLoaded(workingHoursCard))
			click(workingHoursCard);
		else
			SimpleUtils.fail("Controls Page: Working Hours Card not Loaded!", false);
	}
	

	@Override
	public void updateControlsRegularHours(String isStoreClosed, String openingHours, String closingHours, String day)
			throws Exception {
		openingHours = openingHours.replace(" ", "");
		closingHours = closingHours.replace(" ", "");
		WebElement collapsibleHeader = regularHoursBlock.findElement(By.cssSelector("div.collapsible.row"));
		boolean isRegularHoursSectionOpened = collapsibleHeader.getAttribute("class").contains("open");
		if(! isRegularHoursSectionOpened)
			click(regularHoursBlock);
		
		if(regularHoursRows.size() > 0)
		{
			for(WebElement regularHoursRow : regularHoursRows)
			{
				if(regularHoursRow.getText().toLowerCase().contains(day.toLowerCase()))
				{
					WebElement regularHoursEditBtn = regularHoursRow.findElement(By.cssSelector("lg-button[label=\"Edit\"]"));
					if(isElementLoaded(regularHoursEditBtn))
					{
						click(regularHoursEditBtn);

						// Select Opening Hours
						int openingHourOnSlider = Integer.valueOf(editRegularHoursSliders.get(0).getText().split(":")[0]);
						if(editRegularHoursSliders.get(0).getText().toLowerCase().contains("pm"))
							openingHourOnSlider = openingHourOnSlider + 12;
						int openingHourOnJson = Integer.valueOf(openingHours.split(":")[0]);
						if(openingHours.toLowerCase().contains("pm"))
							openingHourOnJson = openingHourOnJson + 12;
						int sliderOffSet = 5;
						
						if(openingHourOnSlider > openingHourOnJson)
							sliderOffSet = -5;
						
						while(! editRegularHoursSliders.get(0).getText().toLowerCase().contains(openingHours.toLowerCase()))
						{
							moveDayViewCards(editRegularHoursSliders.get(0), sliderOffSet);
						}
						
						// Select Closing Hours
						int closingHourOnSlider = Integer.valueOf(editRegularHoursSliders.get(1).getText().split(":")[0]);
						if(editRegularHoursSliders.get(1).getText().toLowerCase().contains("pm"))
							closingHourOnSlider = closingHourOnSlider + 12;
						int closingHourOnJson = Integer.valueOf(closingHours.split(":")[0]);
						if(closingHours.toLowerCase().contains("pm"))
							closingHourOnJson = closingHourOnJson + 12;
						if(closingHourOnSlider > closingHourOnJson)
							sliderOffSet = -5;
						else
							sliderOffSet = 5;

						while(! editRegularHoursSliders.get(1).getText().toLowerCase().contains(closingHours.toLowerCase()))
						{
							moveDayViewCards(editRegularHoursSliders.get(1), sliderOffSet);
						}
						
						if(isElementLoaded(saveWorkersHoursBtn))
						{
							click(saveWorkersHoursBtn);
							SimpleUtils.pass("Controls Working Hours Section: Regular Hours Updated for the day ('"+ day  +"'). ");
						}
						else
							SimpleUtils.fail("Controls Working Hours Section: Editing Regular Hours 'Save' Button not loaded.", true);
					}
					else {
						SimpleUtils.fail("Controls Working Hours Section: Regular Hours 'Edit' Button not loaded.", true);
					}
				}
				
			}
		}
		else {
			SimpleUtils.fail("Controls Working Hours Section: Regular Hours not loaded.", true);
		}
	}
	
	
	public void moveDayViewCards(WebElement webElement, int xOffSet)
	{
		Actions builder = new Actions(MyThreadLocal.getDriver());
		builder.moveToElement(webElement)
	         .clickAndHold()
	         .moveByOffset(xOffSet, 0)
	         .release()
	         .build()
	         .perform();
	}
	
	
	@Override
	public void clickOnSaveRegularHoursBtn() throws Exception
	{
		if(isElementLoaded(saveAllRegularHoursBtn))
		{
			click(saveAllRegularHoursBtn);
			SimpleUtils.pass("Controls Working Hours Section: Regular Hours Saved successfully. ");
		}else {
			SimpleUtils.fail("Controls Working Hours Section: Regular Hours 'Save' Button not loaded.", true);
		}
	}
}
