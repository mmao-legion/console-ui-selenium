package com.legion.pages.core;

import com.aventstack.extentreports.Status;
import com.legion.pages.ActivityPage;
import com.legion.pages.BasePage;
import com.legion.pages.LocationsPage;
import com.legion.tests.testframework.ExtentTestManager;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;
import cucumber.api.java.ro.Si;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.legion.utils.MyThreadLocal.*;
import static com.legion.utils.MyThreadLocal.setURL;

public class OpsPortalLocationsPage extends BasePage implements LocationsPage {

	public OpsPortalLocationsPage() {
		PageFactory.initElements(getDriver(), this);
	}
	private static Map<String, String> newLocationParas = JsonUtil.getPropertiesFromJsonFile("src/test/resources/AddANewLocation.json");
	// Added by Estelle
	@FindBy(css="[class='console-navigation-item-label Locations']")
	private WebElement goToLocationsButton;
	@FindBy(css="[class='console-navigation-item-label Configuration']")
	private WebElement goToConfigurationButton;
	@FindBy(css="[class='console-navigation-item-label Jobs']")
	private WebElement goToJobsButton;
	@FindBy(css="[title='Enterprise Profile']")
	private WebElement enterPriseProfileInLocations;
	@FindBy(css="[title='Global Configuration']")
	private WebElement globalConfigurationInLocations;
	@FindBy(css="[title='Locations']")
	private WebElement locationsInLocations;
	@FindBy(css="[title='Districts']")
	private WebElement districtsInLocations;

	// sub-location page
	@FindBy(css = "lg-button[label=\"Add Location\"]")
	private WebElement addLocationBtn;
	@FindBy(css = "lg-button[label=\"Export\"]")
	private WebElement exportBtn;
	@FindBy(css = "lg-button[label=\"Import\"]")
	private WebElement importBtn;



	@Override
	public void clickOpsPortalIconInDashboardPage() {

	}

	@Override
	public boolean isOpsPortalPageLoaded() throws Exception {
		boolean isLoaded = false;
		if (isElementLoaded(goToLocationsButton, 10) && isElementLoaded(goToJobsButton,10))
			isLoaded = true;
		return isLoaded;
	}

	@Override
	public void clickOnLocationsTab() throws Exception {
		if(isElementLoaded(goToLocationsButton,10)){
			click(goToLocationsButton);
			SimpleUtils.pass("Locations tab is clickable");
		}else
			SimpleUtils.fail("locations tab not load",false);
	}

	@Override
	public void validateItemsInLocations() throws Exception {
		if(isElementLoaded(goToConfigurationButton,10)){
			if (isElementLoaded(enterPriseProfileInLocations,5) && isElementLoaded(globalConfigurationInLocations,5)
			&& isElementLoaded(locationsInLocations,5) && isElementLoaded(districtsInLocations)) {
				SimpleUtils.pass("Location overview page show well when OPview turn on");
			}else
				SimpleUtils.fail("Location overview page load failed when OPview turn on",false);
		}else {
			if (isElementLoaded(enterPriseProfileInLocations,5)
					&& isElementLoaded(locationsInLocations,5) && isElementLoaded(districtsInLocations)) {
				SimpleUtils.pass("Location overview page show well when OPview turn off");
			}else
				SimpleUtils.fail("Location overview page load failed when OPview turn off",false);
		}

	}

	@Override
	public void goToSubLocationsInLocationsPage() throws Exception {
		if ( isElementLoaded(locationsInLocations,5)) {
			click(locationsInLocations);
			if (isElementEnabled(addLocationBtn,5)) {
				SimpleUtils.pass("sub-location page load successfully");
			}else
				SimpleUtils.fail("sub-location page load failed",false);
		}else
			SimpleUtils.fail("locations tab load failed in location overview page",false);
	}



	//new location page
	@FindBy(css = "input[aria-label=\"location Name\"]")
	private WebElement locationName;
	@FindBy(css = "input[aria-label=\"Location Id\"]")
	private WebElement locationId;
	@FindBy(css = "select[aria-label=\"Time Zone\"]")
	private WebElement timeZoonSelect;
	@FindBy(css="input[aria-label=\"Location Address\"]")
	private WebElement LocationAddress1;
	@FindBy(css = "select[aria-label=\"Country\"]")
	private WebElement countrySelect;
	@FindBy(css="input[aria-label=\"City\"]")
	private WebElement city;
	@FindBy(css = "select[aria-label=\"State\"]")
	private WebElement stateSelect;
	@FindBy(css="input[aria-label=\"Zip Code\"]")
	private WebElement zipCode;
	@FindBy(css = "select[aria-label=\"Configuration Type\"]")
	private WebElement configTypeSelect;

	@FindBy(css="input-field[label=\"Effective date\"]")
	private WebElement effectiveDateSelect;
	@FindBy(xpath = "//lg-single-calendar/div[2]/div[8]/div[4]")
	private WebElement firstDay;

	@FindBy(css="lg-button[label=\"Create location\"]")
	private WebElement createLocationBtn;
	@FindBy(css="lg-button[label=\"Cancel\"]")
	private WebElement cancelBtn;

	@Override
	public void addNewRegularLocationWithMandatoryFields() throws Exception {
		
		if (isElementEnabled(addLocationBtn,5)) {
			click(addLocationBtn);
			locationName.sendKeys(newLocationParas.get("Location_Name"));
			locationId.sendKeys(newLocationParas.get("Location_Id"));
			selectByVisibleText(timeZoonSelect,newLocationParas.get("Time_Zone"));
			LocationAddress1.sendKeys(newLocationParas.get("Location_Address"));
			selectByVisibleText(countrySelect,newLocationParas.get("Country"));
			city.sendKeys(newLocationParas.get("City"));
			selectByVisibleText(stateSelect,newLocationParas.get("State"));
			zipCode.sendKeys(newLocationParas.get("Zip_Code"));
			selectByVisibleText(configTypeSelect,newLocationParas.get("Configuration_Type"));
			click(effectiveDateSelect);
			click(firstDay);
			scrollToBottom();
			click(createLocationBtn);
			SimpleUtils.pass("New location creation done");

		}else
			SimpleUtils.fail("New location page load failed",false);

	}

	@Override
	public void searchNewLocation() {


	}
}

