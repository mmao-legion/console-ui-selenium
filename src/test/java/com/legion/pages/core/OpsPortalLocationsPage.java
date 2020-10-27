package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.LocationsPage;
import com.legion.utils.FileDownloadVerify;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.jayway.restassured.RestAssured.given;
import static com.legion.tests.TestBase.switchToNewWindow;
import static com.legion.utils.MyThreadLocal.*;


public class OpsPortalLocationsPage extends BasePage implements LocationsPage {

	public OpsPortalLocationsPage() {
		PageFactory.initElements(getDriver(), this);
	}
	private static Map<String, String> newLocationParas = JsonUtil.getPropertiesFromJsonFile("src/test/resources/AddANewLocation.json");
	private static HashMap<String, String> parameterMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");


	// Added by Estelle
	@FindBy(css="[class='console-navigation-item-label Locations']")
	private WebElement goToLocationsButton;
	@FindBy(css="[class='modeSwitchIcon']")
	private WebElement modeSwitchIcon;
//	@FindBy(css="[css='.menu-item-console_title.mt-12']")
	@FindBy(xpath = "//header-mode-switch-menu/div/ul/li[1]/div[2]/p")
	private WebElement opTitleMenu;
	@FindBy(xpath="//header-mode-switch-menu/div/ul/li[2]/div[2]/p")
	private WebElement consoleTitleMenu;

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
	@FindBy(css = "li.header-mode-switch-menu-item")
	private List<WebElement> modelSwitchOption;


	@Override
	public void clickModelSwitchIconInDashboardPage(String value) {
		if (isElementEnabled(modeSwitchIcon,10)) {
			click(modeSwitchIcon);
			waitForSeconds(5);
			if (modelSwitchOption.size() != 0) {
				for (WebElement subOption : modelSwitchOption) {
					if (subOption.getText().equalsIgnoreCase(value)) {
						click(subOption);
						waitForSeconds(3);
					}
				}

			}
			switchToNewWindow();

		}else
			SimpleUtils.fail("mode switch img load failed",false);


	}



	@Override
	public boolean isOpsPortalPageLoaded() throws Exception {
		boolean isLoaded = false;
		if (isElementLoaded(goToLocationsButton, 10))
			isLoaded = true;
		return isLoaded;
	}

	@Override
	public void clickOnLocationsTab() throws Exception {
		if(isElementLoaded(goToLocationsButton,15)){
			click(goToLocationsButton);
			SimpleUtils.pass("Locations tab is clickable");
		}else
			SimpleUtils.fail("locations tab not load",false);
	}

	@Override
	public void validateItemsInLocations() throws Exception {
		if(isElementLoaded(goToConfigurationButton,5)){
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
	private WebElement locationNameInput;
	@FindBy(css = "span[class *=locationDefault]")
	private WebElement mockLocationName;
	@FindBy(css = "input[aria-label=\"Location Id\"]")
	private WebElement locationId;
	@FindBy(css = "select[aria-label=\"Location Group Setting\"]")
	private WebElement locationGroupSettingSelect;
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
	@FindBy(css="input[aria-label=\"primary contact\"]")
	private WebElement primaryContact;
	@FindBy(css="input[aria-label=\"Phone number\"]")
	private WebElement phoneNumber;
	@FindBy(css="input[aria-label=\"Email address\"]")
	private WebElement emailAddress;
	@FindBy(css="input-field[label=\"Source Location\"] > ng-form > div.input-choose > span")
	private WebElement selectOneInSourceLocation;
	@FindBy(css=".lg-modal__title-icon")
	private WebElement selectALocationTitle;
	@FindBy(css="div.lg-tab-toolbar__search >lg-search >input-field>ng-form>input")
	private WebElement searchInputInSelectALocation;
	@FindBy(css="tr[ng-repeat=\"item in $ctrl.currentPageItems\"]")
	private List<WebElement> locationRowsInSelectLocation;

	@FindBy(css="tr[ng-repeat=\"location in filteredCollection\"]")
	private List<WebElement> locationRows;
	@FindBy(css="tr[ng-repeat=\"location in filteredCollection\"] > td:nth-child(4) > lg-eg-status")
	private List<WebElement> locationStatus;

	@FindBy(css="lg-button[label=\"OK\"]")
	private WebElement  okBtnInSelectLocation;
	@FindBy(css="input-field[label=\"Choose a District\"] > ng-form > div.input-choose > span")
	private WebElement selectOneInChooseDistrict;

	@FindBy(css = "select[aria-label=\"Configuration Type\"]")
	private WebElement configTypeSelect;

	@FindBy(css="input-field[label=\"Effective date\"]")
	private WebElement effectiveDateSelect;
	@FindBy(xpath = "//lg-single-calendar/div[2]/div[8]/div[7]")
	private List<WebElement> firstDay;
	@FindBy(css = "a[ng-click=\"$ctrl.changeMonth(-1)\"]")
	private List<WebElement> previousMonthBtn;

	@FindBy(css="lg-button[label=\"Create location\"]")
	private WebElement createLocationBtn;
	@FindBy(css="lg-button[label=\"Cancel\"]")
	private WebElement cancelBtn;

	@Override
	public void addNewRegularLocationWithMandatoryFields(String locationName) throws Exception {

		if (isElementEnabled(addLocationBtn,5)) {
			click(addLocationBtn);
			locationNameInput.sendKeys(locationName);
			setLocationName(locationName);
			locationId.sendKeys(getLocationName());
			selectByVisibleText(timeZoonSelect,newLocationParas.get("Time_Zone"));
			LocationAddress1.sendKeys(newLocationParas.get("Location_Address"));
			selectByVisibleText(countrySelect,newLocationParas.get("Country"));
			city.sendKeys(newLocationParas.get("City"));
			selectByVisibleText(stateSelect,newLocationParas.get("State"));
			zipCode.sendKeys(newLocationParas.get("Zip_Code"));
			if (isElementEnabled(configTypeSelect,5)) {
				selectByVisibleText(configTypeSelect,newLocationParas.get("Configuration_Type"));
			}
			click(effectiveDateSelect);
			click(firstDay.get(0));
			scrollToBottom();
			click(createLocationBtn);
			waitForSeconds(5);
			SimpleUtils.pass("New location creation done");

		}else
			SimpleUtils.fail("New location page load failed",false);

	}

	@FindBy(css="input[placeholder*=\"You can search by name, id, district, country, state and city.\"]")
	private WebElement searchInput;
	@FindBy(css = ".lg-search-icon")
	private WebElement searchBtn;
	@FindBy(xpath = "//table/tbody/tr[2]/td[1]/lg-button/button/span/span")
	private WebElement locationsName;

	@Override
	public boolean searchNewLocation(String locationName) {

		if (isElementEnabled(searchInput,10)) {
			searchInput.sendKeys(locationName);
			searchInput.sendKeys(Keys.ENTER);
			waitForSeconds(5);
			if (locationsName.getText().trim().equalsIgnoreCase(locationName.trim())) {
				SimpleUtils.pass("the location is searched ");
				return true;
			}
		}else
          SimpleUtils.fail("search filed load failed",false);
		   return false;
	}

	@Override
	public void addNewRegularLocationWithAllFields(String locationName, String searchCharactor,int index) throws Exception {
		if (isElementEnabled(addLocationBtn,5)) {
			click(addLocationBtn);
			locationNameInput.sendKeys(locationName);
			setLocationName(locationName);
			locationId.sendKeys(getLocationName());
			selectByVisibleText(timeZoonSelect,newLocationParas.get("Time_Zone"));
			LocationAddress1.sendKeys(newLocationParas.get("Location_Address"));
			selectByVisibleText(countrySelect,newLocationParas.get("Country"));
			city.sendKeys(newLocationParas.get("City"));
			selectByVisibleText(stateSelect,newLocationParas.get("State"));
			zipCode.sendKeys(newLocationParas.get("Zip_Code"));
			primaryContact.sendKeys(newLocationParas.get("Primary_Contact"));
			phoneNumber.sendKeys(newLocationParas.get("Phone_Number"));
			emailAddress.sendKeys(newLocationParas.get("Email_Address"));
			click(selectOneInSourceLocation);
			selectLocationOrDistrict(searchCharactor,index);
			if (isElementEnabled(configTypeSelect,5)) {
				selectByVisibleText(configTypeSelect,newLocationParas.get("Configuration_Type"));
			}
			click(selectOneInChooseDistrict);
			selectLocationOrDistrict(searchCharactor,index);
			click(effectiveDateSelect);
			click(firstDay.get(0));
			scrollToBottom();
			click(createLocationBtn);
			waitForSeconds(5);
			SimpleUtils.pass("New location creation done");

		}else
			SimpleUtils.fail("New location page load failed",false);
	}


	private void selectLocationOrDistrict(String searchCharactor,int index) {
			if (isElementEnabled(selectALocationTitle,5)) {
				searchInputInSelectALocation.sendKeys(searchCharactor);
				searchInputInSelectALocation.sendKeys(Keys.ENTER);
				waitForSeconds(5);
				if (locationRowsInSelectLocation.size()>0) {
				WebElement firstRow = locationRowsInSelectLocation.get(index).findElement(By.cssSelector("input[type=\"radio\"]"));
				click(firstRow);
				click(okBtnInSelectLocation);
				}else
					SimpleUtils.report("Search location result is 0");

			}else
				SimpleUtils.fail("Select a location window load failed",true);

	}

	private void selectLocationOrDistrictWhenExport(String searchCharactor,int index) {
		if (isElementEnabled(selectALocationTitle,5)) {
			searchInputInSelectALocation.sendKeys(searchCharactor);
			searchInputInSelectALocation.sendKeys(Keys.ENTER);
			waitForSeconds(5);
			if (locationRowsInSelectLocation.size()>0) {
				WebElement firstRow = locationRowsInSelectLocation.get(index).findElement(By.cssSelector("input[type=\"checkbox\"]"));
				click(firstRow);
				click(okBtnInSelectLocation);
			}else
				SimpleUtils.report("Search location result is 0");

		}else
			SimpleUtils.fail("Select a location window load failed",true);

	}

	@FindBy(css = "select[aria-label=\"location Type\"]")
	private WebElement locationTypeSelector;
	@FindBy(css="input-field[label=\"Select Base Location to Mock \"] > ng-form > div.input-choose > span")
	private WebElement selectOneInBaseLocation;
	@FindBy(css="lg-button[label=\"Leave this page\"]")
	private WebElement leaveThisPageBtn;
	@Override
	public void addNewMockLocationWithAllFields(String locationName, String searchCharactor, int index) throws Exception {
		if (isElementEnabled(addLocationBtn,5)) {
			click(addLocationBtn);
			selectByVisibleText(locationTypeSelector,newLocationParas.get("Location_Type_Mock"));
			click(selectOneInBaseLocation);
			selectLocationOrDistrict(searchCharactor,index);
			waitForSeconds(2);
			setLocationName(mockLocationName.getText().toLowerCase() +"-mock");
			scrollToBottom();
			waitForSeconds(2);
			click(createLocationBtn);
			waitForSeconds(5);
			SimpleUtils.report("Mock location create done");

		}else
			SimpleUtils.fail("New location creation page load failed",true);
	}

	@FindBy(css = "div.lg-modal__title-icon")
	private WebElement importLocationsTitle;
	@FindBy(css = "div.sampleDownload")
	private WebElement contextOfDownload;
	@FindBy(css = "div >a[uploader=\"uploader\"]")
	private WebElement uploaderBtn;
	@FindBy(css = "input[type=\"file\"]")
	private WebElement uploaderFileInputBtn;
	@FindBy(css = "div [ng-if=\"!chooseFile && !checkValid\"]")
	private WebElement contextOfUploader;
	@FindBy(css = "[href=\"/legion/img/legion/BusinessImportTemplate.csv\"]")
	private WebElement downloadHereBtn;
	@FindBy(css = "lg-button[label=\"Cancel\"]")
	private WebElement cancelBtnInImportLocationPage;
	@FindBy(css = "lg-button[label=\"Import\"]")
	private WebElement importBtnInImportLocationPage;
	@FindBy(css = "lg-button[label=\"Ok\"]")
	private WebElement okBtnInImportLocationPage;
	@FindBy(css = "div[ng-if=\"loaded\"]>div:nth-child(4)")
	private WebElement importFileLoadSuccessMessage;


	@Override
	public void verifyImportLocationDistrict() {
		if (isElementEnabled(importBtn,5)) {
			click(importBtn);
			if (verifyImportLocationsPageShow()) {
				SimpleUtils.pass("Import location page show well");
			}else
				SimpleUtils.fail("Import location page load failed",true);
			uploaderFileInputBtn.sendKeys("C:\\Users\\DMF\\Desktop\\template\\Import_LocationGroup_Example.csv");
			waitForSeconds(5);
			click(importBtnInImportLocationPage);
			click(okBtnInImportLocationPage);
			SimpleUtils.pass("File import action done");

		}else
			SimpleUtils.fail("Import button load failed",true);
	}

	private boolean verifyImportLocationsPageShow() {
		String downloadText = "Want to use a csv location sample form? Download here";
		String uploadText = "Upload csv file here";
		if (isElementEnabled(importLocationsTitle,5) && isElementEnabled(contextOfUploader)
		&& isElementEnabled(contextOfDownload) && isElementEnabled(importBtnInImportLocationPage) &&
		isElementEnabled(cancelBtnInImportLocationPage)) {

			if (contextOfDownload.getText().trim().contains(downloadText) &&
					contextOfUploader.getText().trim().contains(uploadText)) {
				return  true;
			}
		}
		return false;
	}


	@Override
	public void verifyThereIsNoLocationGroupField() {
		if (isElementEnabled(addLocationBtn,5)) {
			click(addLocationBtn);
			if (isElementEnabled(locationGroupSettingSelect,5)) {
				SimpleUtils.fail("Location Group Setting is still show",true);
			}else
				SimpleUtils.pass("There is no Location Group Setting");
		}
	}

	@FindBy(css="input-field[label=\"Launch date\"]")
	private WebElement launchDateSelecter;
	@FindBy(css="input-field[label=\"End Comparable-store date\"]")
	private WebElement comparableStoreDateSelecter;
	@FindBy(css="input-field[label=\"Location for Demand Channel\"] > ng-form > div.input-choose > span[ng-click]")
	private WebElement selectOneComparableLocation;
	@FindBy(css="td[ng-repeat=\"item in value\"]>input-field>ng-form>input")
	private List<WebElement> itemsAndTransactionInoutField;

	@Override
	public void addNewNSOLocation(String locationName, String searchCharactor, int index) throws Exception {
		if (isElementEnabled(addLocationBtn,5)) {
			click(addLocationBtn);
			selectByVisibleText(locationTypeSelector,newLocationParas.get("Location_Type_NSO"));
			locationNameInput.sendKeys(locationName);
			setLocationName(locationName);
			locationId.sendKeys(getLocationName());
			selectByVisibleText(timeZoonSelect,newLocationParas.get("Time_Zone"));
			LocationAddress1.sendKeys(newLocationParas.get("Location_Address"));
			selectByVisibleText(countrySelect,newLocationParas.get("Country"));
			city.sendKeys(newLocationParas.get("City"));
			selectByVisibleText(stateSelect,newLocationParas.get("State"));
			zipCode.sendKeys(newLocationParas.get("Zip_Code"));
			primaryContact.sendKeys(newLocationParas.get("Primary_Contact"));
			phoneNumber.sendKeys(newLocationParas.get("Phone_Number"));
			emailAddress.sendKeys(newLocationParas.get("Email_Address"));
			click(selectOneInSourceLocation);
			selectLocationOrDistrict(searchCharactor,index);
			if (isElementEnabled(configTypeSelect,5)) {
				selectByVisibleText(configTypeSelect,newLocationParas.get("Configuration_Type"));
			}
			click(selectOneInChooseDistrict);
			selectLocationOrDistrict(searchCharactor,index);
			click(effectiveDateSelect);
			click(previousMonthBtn.get(0));
			click(firstDay.get(0));

			click(launchDateSelecter);
			click(previousMonthBtn.get(1));
			click(firstDay.get(1));
			waitForSeconds(2);
			click(selectOneComparableLocation);
			selectLocationOrDistrict(searchCharactor,index);
			waitForSeconds(2);
			scrollToBottom();
			click(comparableStoreDateSelecter);
			click(firstDay.get(2));
			itemsAndTransactionInoutField.get(0).sendKeys("1");
			itemsAndTransactionInoutField.get(1).sendKeys("1");

			click(createLocationBtn);
			waitForSeconds(20);
			SimpleUtils.pass("New location creation done");

		}else
			SimpleUtils.fail("New location creation page load failed",true);
	}


	@FindBy(css = "lg-button[label=\"Disable\"]")
	private WebElement disableBtn;
	@FindBy(css = "lg-button[label=\"Enable\"]")
	private WebElement enableBtn;
	@FindBy(css = "a[ng-click=\"$ctrl.back()\"]")
	private WebElement backBtnInLocationDetailsPage;


	public void searchLocation(String searchInputText) throws Exception {
		String[] searchLocationCha = searchInputText.split(",");
		if (isElementLoaded(searchInput, 10) ) {
			for (int i = 0; i < searchLocationCha.length; i++) {
				searchInput.sendKeys(searchLocationCha[0]);
				searchInput.sendKeys(Keys.ENTER);
				waitForSeconds(3);
				if (locationRows.size()>0) {
					SimpleUtils.pass("Locations: " + locationRows.size() + " location(s) found  ");
					break;
				} else {
					searchInput.clear();
				}
			}

		} else {
			SimpleUtils.fail("Search input is not clickable", true);
		}

	}

	@FindBy(css = " page-heading > div > h1")
	private  WebElement locationNameText;
	@Override
	public String disableLocation(String searchInputText) throws Exception {
		String disableLocationName = "";
		searchLocation(searchInputText);
		if (locationRows.size() > 0) {
			List<WebElement> locationDetailsLinks = locationRows.get(0).findElements(By.cssSelector("button[type='button']"));
			List<String> locationStatusAfterFirstSearch = getLocationStatus();
			waitForSeconds(3);
			for (int i = 0; i <locationDetailsLinks.size() ; i++) {
				if (locationDetailsLinks.size() > 0 && locationStatusAfterFirstSearch.get(i).equals("ENABLED")) {
					click(locationDetailsLinks.get(i));
					break;
				}
			}
			disableLocationName = locationNameText.getText();
			if (isElementLoaded(disableBtn,5)) {
				click(disableBtn);
				if (validateDisableLocationAlertPage()) {
					click(disableBtn);
					waitForSeconds(2);
				}
				click(backBtnInLocationDetailsPage);
				waitForSeconds(15);
//				getDriver().navigate().refresh();
				searchLocation(disableLocationName);
				List<String> locationStatusAfterSecondSearch = getLocationStatus();
				if (locationStatusAfterSecondSearch.get(0).equals("DISABLED")) {
					SimpleUtils.pass("Disable location successfully");
				}
			}else
					SimpleUtils.report("This location has disabled");
			}else
			SimpleUtils.fail("Location can not be clickable",true);
		  return disableLocationName;
		}

	@FindBy(className = "modal-content")
	private WebElement disableLocationAlertPage;
	@FindBy(className = "lg-modal__title")
	private WebElement enableLocationAlertPage;

	@FindBy(className = "lg-modal__content")
	private WebElement disableLocationAlertPageDescription;

	public boolean validateDisableLocationAlertPage(){
		if (isElementEnabled(disableLocationAlertPage,5) && isElementEnabled(disableLocationAlertPageDescription,5)
		&& isElementEnabled(disableBtn,5) && isElementEnabled(cancelBtn,5)) {
			if (disableLocationAlertPageDescription.getText().trim().equals("Are you sure you want to disable this location?")) {
			return  true;
			}
		}
		return false;
	}

	public boolean validateEnableLocationAlertPage(){
		if (isElementEnabled(enableLocationAlertPage,5) && isElementEnabled(disableLocationAlertPageDescription,5)
				&& isElementEnabled(enableBtn,5) && isElementEnabled(cancelBtn,5)) {
			if (disableLocationAlertPageDescription.getText().trim().equals("Are you sure you want to enable this location?")) {
				return  true;
			}
		}
		return false;
	}

	public List<String> getLocationStatus(){
		List<String> locationStatusListContext = new ArrayList<String>();
		for (WebElement status:locationStatus) {
			locationStatusListContext.add(status.getAttribute("type"));
			return locationStatusListContext;
		}

		return null;
	}


	@Override
	public void verifyExportAllLocationDistrict() {
		if (isElementEnabled(exportBtn,5)) {
			click(exportBtn);
			if (verifyExportLocationsPageShow()) {
				SimpleUtils.pass("Export location page show well");
			}else
				SimpleUtils.fail("Export location page load failed",true);

			click(exportAllRadio);
			click(okBtnInExportLocationPage);
			waitForSeconds(10);

			TimeZone timeZone = TimeZone.getTimeZone("America/Chicago");
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM");
			dfs.setTimeZone(timeZone);
			String currentTime =  dfs.format(new Date());
			String downloadPath = "C:\\Users\\DMF\\Downloads";//when someone run ,need to change this path
			Assert.assertTrue(FileDownloadVerify.isFileDownloaded_Ext(downloadPath, "LEG-"+currentTime), "Download successfully");

		}else
			SimpleUtils.fail("Export button load failed",true);
	}

	@FindBy(css = "input-field[label=\"Export All\"]")
	private WebElement exportAllRadio;
	@FindBy(css = "input-field[label=\"Export specific Locations\"]")
	private WebElement exportSpecificLocationsRadio;
	@FindBy(css = "lg-button[label=\"OK\"]")
	private WebElement okBtnInExportLocationPage;
	private boolean verifyExportLocationsPageShow() {

		if (isElementEnabled(importLocationsTitle,5) && isElementEnabled(exportAllRadio)
				&& isElementEnabled(exportSpecificLocationsRadio) && isElementEnabled(okBtnInExportLocationPage) &&
				isElementEnabled(cancelBtnInImportLocationPage)) {

				return  true;

		}
		return false;
	}

	@Override
	public void verifyExportSpecificLocationDistrict(String searchCharactor, int index) {
		if (isElementEnabled(exportBtn,5)) {
			click(exportBtn);
			if (verifyExportLocationsPageShow()) {
				SimpleUtils.pass("Export location page show well");
			}else
				SimpleUtils.fail("Export location page load failed",true);

			click(exportSpecificLocationsRadio);
			selectLocationOrDistrictWhenExport("*",0);
			click(okBtnInExportLocationPage);
			waitForSeconds(10);

			TimeZone timeZone = TimeZone.getTimeZone("America/Chicago");
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM");
			dfs.setTimeZone(timeZone);
			String currentTime =  dfs.format(new Date());
			String downloadPath = "C:\\Users\\DMF\\Downloads";//when someone run ,need to change this path
			Assert.assertTrue(FileDownloadVerify.isFileDownloaded_Ext(downloadPath, "LEG-"+currentTime), "Download successfully");

		}else
			SimpleUtils.fail("Export button load failed",true);
	}

	@Override
	public void enableLocation(String disableLocationName) throws Exception {
		searchInput.clear();
		searchLocation(disableLocationName);
		if (locationRows.size() > 0) {
			List<WebElement> locationDetailsLinks = locationRows.get(0).findElements(By.cssSelector("button[type='button']"));
			List<String> locationStatusAfterFirstSearch = getLocationStatus();
			waitForSeconds(3);
			for (int i = 0; i <locationDetailsLinks.size() ; i++) {
				if (locationDetailsLinks.size() > 0 && locationStatusAfterFirstSearch.get(i).equals("DISABLED")) {
					click(locationDetailsLinks.get(i));
					break;
				}
			}
			disableLocationName = locationNameText.getText();
			if (isElementLoaded(enableBtn,5)) {
				click(enableBtn);
				if (validateEnableLocationAlertPage()) {
					click(enableBtn);
					waitForSeconds(10);
				}
				click(backBtnInLocationDetailsPage);
				waitForSeconds(15);
//				getDriver().navigate().refresh();
				searchLocation(disableLocationName);
				List<String> locationStatusAfterSecondSearch = getLocationStatus();
				if (locationStatusAfterSecondSearch.get(0).equals("ENABLED")) {
					SimpleUtils.pass("Enable location successfully");
				}
			}else
				SimpleUtils.report("This location has enabled");
		}else
			SimpleUtils.fail("Location can not be clickable",true);

	}

	@FindBy(css = "lg-button[label=\"Edit Location\"]")
	private WebElement editLocationBtn;
	@FindBy(css = "lg-button[label=\"Save\"]")
	private WebElement saveBtnInUpdateLocationPage;
	@Override
	public void updateLocation(String locationName) throws Exception {
		searchInput.clear();
		searchLocation(locationName);
		if (locationRows.size()>0) {
			List<WebElement> locationDetailsLinks = locationRows.get(0).findElements(By.cssSelector("button[type='button']"));
			click(locationDetailsLinks.get(0));
			click(editLocationBtn);
			locationNameInput.clear();
			locationNameInput.sendKeys(locationName+"update");
			setLocationName(locationName);
			locationId.clear();
			locationId.sendKeys(getLocationName());
			scrollToBottom();
			click(saveBtnInUpdateLocationPage);
			waitForSeconds(5);
			SimpleUtils.pass("Location update done");
		}
	}

	public enum smartCardData {
		createNum("Create"),
		enableNum("Enable"),
		disableNum("Disable");
		private final String value;

		smartCardData(final String newValue) {
			value = newValue;
		}

		public String getValue() {
			return value;
		}
	}

	@Override
	public boolean verifyUpdateLocationResult(String locationName) throws Exception {
		searchLocation(locationName);
		if (locationsName.getText().contains(locationName)) {
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<HashMap<String, String>> getLocationInfo(String locationName) {
		ArrayList<HashMap<String,String>> locationinfo = new ArrayList<>();

		if (isElementEnabled(searchInput, 10)) {
			searchInput.clear();
			searchInput.sendKeys(locationName);
			searchInput.sendKeys(Keys.ENTER);
			waitForSeconds(5);
			if (locationRows.size() > 0) {

				for (WebElement row : locationRows) {
					HashMap<String, String> locationInfoInEachRow = new HashMap<>();
					locationInfoInEachRow.put("locationName", row.findElement(By.cssSelector("button[type='button']")).getText());
					locationInfoInEachRow.put("locationStatus", row.findElement(By.cssSelector("td:nth-child(4) > lg-eg-status ")).getAttribute("type"));
					locationInfoInEachRow.put("locationEffectiveDate", row.findElement(By.cssSelector("td:nth-child(5)")).getText());
					locationInfoInEachRow.put("locationCity", row.findElement(By.cssSelector("td:nth-child(6) ")).getText());
					locationInfoInEachRow.put("locationDistrict", row.findElement(By.cssSelector("td:nth-child(7) ")).getText());
					locationinfo.add(locationInfoInEachRow);
				}


				return locationinfo;
			}else
				SimpleUtils.fail(locationName + "can't been searched", true);
			}

		return null;
	}

	@FindBy(css = "select[aria-label=\"Location Group Setting\"]")
	private WebElement locationGroupSelect;
	@FindBy(css = "input-field[value=\"'Select parent location'\"] > ng-form > div.input-choose.ng-scope > span")
	private WebElement selectParentLocation;

	@Override
	public void addChildLocation(String locationType, String childlocationName, String locationName, String searchCharactor, int index, String childRelationship) throws Exception {
		if (isElementEnabled(addLocationBtn,5)) {
			click(addLocationBtn);
			selectByVisibleText(locationTypeSelector,locationType);
			locationNameInput.sendKeys(childlocationName);
			setLocationName(childlocationName);
			selectByVisibleText(locationGroupSelect,newLocationParas.get(childRelationship));
			click(selectParentLocation);
			selectLocationOrDistrict(locationName,index);
			locationId.sendKeys(getLocationName());
			selectByVisibleText(timeZoonSelect,newLocationParas.get("Time_Zone"));
			LocationAddress1.sendKeys(newLocationParas.get("Location_Address"));
			selectByVisibleText(countrySelect,newLocationParas.get("Country"));
			city.sendKeys(newLocationParas.get("City"));
			selectByVisibleText(stateSelect,newLocationParas.get("State"));
			zipCode.sendKeys(newLocationParas.get("Zip_Code"));
			primaryContact.sendKeys(newLocationParas.get("Primary_Contact"));
			phoneNumber.sendKeys(newLocationParas.get("Phone_Number"));
			emailAddress.sendKeys(newLocationParas.get("Email_Address"));
			click(selectOneInSourceLocation);
			selectLocationOrDistrict(searchCharactor,index);
			if (isElementEnabled(configTypeSelect,5)) {
				selectByVisibleText(configTypeSelect,newLocationParas.get("Configuration_Type"));
			}
			click(effectiveDateSelect);
			click(firstDay.get(0));
			scrollToBottom();
			click(createLocationBtn);
			waitForSeconds(10);
			SimpleUtils.pass("Child location creation done");

		}else
			SimpleUtils.fail("New location page load failed",false);
	}

	@Override
	public void addParentLocation(String locationType, String locationName, String searchCharactor, int index, String parentRelationship, String value) throws Exception {
		if (isElementEnabled(addLocationBtn,5)) {
			click(addLocationBtn);
			selectByVisibleText(locationTypeSelector,locationType);
			locationNameInput.sendKeys(locationName);
			setLocationName(locationName);
			selectByVisibleText(locationGroupSelect,newLocationParas.get(parentRelationship));
			click(getDriver().findElement(By.cssSelector("input[aria-label=\""+value+"\"] ")));
			locationId.sendKeys(getLocationName());
			selectByVisibleText(timeZoonSelect,newLocationParas.get("Time_Zone"));
			LocationAddress1.sendKeys(newLocationParas.get("Location_Address"));
			selectByVisibleText(countrySelect,newLocationParas.get("Country"));
			city.sendKeys(newLocationParas.get("City"));
			selectByVisibleText(stateSelect,newLocationParas.get("State"));
			zipCode.sendKeys(newLocationParas.get("Zip_Code"));
			primaryContact.sendKeys(newLocationParas.get("Primary_Contact"));
			phoneNumber.sendKeys(newLocationParas.get("Phone_Number"));
			emailAddress.sendKeys(newLocationParas.get("Email_Address"));
			click(selectOneInSourceLocation);
			selectLocationOrDistrict(searchCharactor,index);
			if (isElementEnabled(configTypeSelect,5)) {
				selectByVisibleText(configTypeSelect,newLocationParas.get("Configuration_Type"));
			}
			click(selectOneInChooseDistrict);
			selectLocationOrDistrict(searchCharactor,index);
			click(effectiveDateSelect);
			click(firstDay.get(0));
			scrollToBottom();
			click(createLocationBtn);
			waitForSeconds(10);
			SimpleUtils.pass("location creation done");

		}else
			SimpleUtils.fail("New location page load failed",false);
	}

	@Override
	public void addParentLocationForNsoType(String locationType, String locationName, String searchCharactor, int index, String parentRelationship, String value) throws Exception {
		if (isElementEnabled(addLocationBtn,5)) {
			click(addLocationBtn);
			selectByVisibleText(locationTypeSelector,locationType);
			locationNameInput.sendKeys(locationName);
			setLocationName(locationName);
			selectByVisibleText(locationGroupSelect,newLocationParas.get(parentRelationship));
			click(getDriver().findElement(By.cssSelector("input[aria-label=\""+value+"\"] ")));
			locationId.sendKeys(getLocationName());
			selectByVisibleText(timeZoonSelect,newLocationParas.get("Time_Zone"));
			LocationAddress1.sendKeys(newLocationParas.get("Location_Address"));
			selectByVisibleText(countrySelect,newLocationParas.get("Country"));
			city.sendKeys(newLocationParas.get("City"));
			selectByVisibleText(stateSelect,newLocationParas.get("State"));
			zipCode.sendKeys(newLocationParas.get("Zip_Code"));
			primaryContact.sendKeys(newLocationParas.get("Primary_Contact"));
			phoneNumber.sendKeys(newLocationParas.get("Phone_Number"));
			emailAddress.sendKeys(newLocationParas.get("Email_Address"));
			click(selectOneInSourceLocation);
			selectLocationOrDistrict(searchCharactor,index);
			if (isElementEnabled(configTypeSelect,5)) {
				selectByVisibleText(configTypeSelect,newLocationParas.get("Configuration_Type"));
			}
			click(selectOneInChooseDistrict);
			selectLocationOrDistrict(searchCharactor,index);
			click(effectiveDateSelect);
			click(previousMonthBtn.get(0));
			click(firstDay.get(0));

			click(launchDateSelecter);
			click(previousMonthBtn.get(1));
			click(firstDay.get(1));
			waitForSeconds(2);
			click(selectOneComparableLocation);
			selectLocationOrDistrict(searchCharactor,index);
			waitForSeconds(2);
			scrollToBottom();
			click(comparableStoreDateSelecter);
			click(firstDay.get(2));
			itemsAndTransactionInoutField.get(0).sendKeys("1");
			itemsAndTransactionInoutField.get(1).sendKeys("1");

			click(createLocationBtn);
			waitForSeconds(20);
			SimpleUtils.pass("New location creation done");

		}else
			SimpleUtils.fail("New location creation page load failed",true);
	}

	@Override
	public void addChildLocationForNSO(String locationType, String childLocationName, String locationName, String searchCharactor, int index, String childRelationship) throws Exception {
		if (isElementEnabled(addLocationBtn,5)) {
			click(addLocationBtn);
			selectByVisibleText(locationTypeSelector,locationType);
			locationNameInput.sendKeys(childLocationName);
			setLocationName(childLocationName);
			selectByVisibleText(locationGroupSelect,newLocationParas.get(childRelationship));
			click(selectParentLocation);
			selectLocationOrDistrict(locationName,index);
			locationId.sendKeys(getLocationName());
			selectByVisibleText(timeZoonSelect,newLocationParas.get("Time_Zone"));
			LocationAddress1.sendKeys(newLocationParas.get("Location_Address"));
			selectByVisibleText(countrySelect,newLocationParas.get("Country"));
			city.sendKeys(newLocationParas.get("City"));
			selectByVisibleText(stateSelect,newLocationParas.get("State"));
			zipCode.sendKeys(newLocationParas.get("Zip_Code"));
			primaryContact.sendKeys(newLocationParas.get("Primary_Contact"));
			phoneNumber.sendKeys(newLocationParas.get("Phone_Number"));
			emailAddress.sendKeys(newLocationParas.get("Email_Address"));
			click(selectOneInSourceLocation);
			selectLocationOrDistrict(searchCharactor,index);
			if (isElementEnabled(configTypeSelect,5)) {
				selectByVisibleText(configTypeSelect,newLocationParas.get("Configuration_Type"));
			}

			click(effectiveDateSelect);
			click(previousMonthBtn.get(0));
			click(firstDay.get(0));

			click(launchDateSelecter);
			click(previousMonthBtn.get(1));
			click(firstDay.get(1));
			waitForSeconds(2);
			click(selectOneComparableLocation);
			selectLocationOrDistrict(searchCharactor,index);
			waitForSeconds(2);
			scrollToBottom();
			click(comparableStoreDateSelecter);
			click(firstDay.get(2));
			itemsAndTransactionInoutField.get(0).sendKeys("1");
			itemsAndTransactionInoutField.get(1).sendKeys("1");

			click(createLocationBtn);
			waitForSeconds(20);
			SimpleUtils.pass("New location creation done");

		}else
			SimpleUtils.fail("New location creation page load failed",true);
	}

	@Override
	public void checkThereIsNoLocationGroupSettingFieldWhenLocationTypeIsMock() throws Exception {
		if (isElementEnabled(addLocationBtn, 5)) {
			click(addLocationBtn);
			selectByVisibleText(locationTypeSelector, newLocationParas.get("Location_Type_Mock"));
			if (isElementEnabled(locationGroupSelect,5) ) {
				SimpleUtils.fail("Location Group Setting filed show,it's not expect behavior",true);
			}else
				SimpleUtils.pass("There is no Location Group Setting filed  after select mock");

		}else
			SimpleUtils.fail("Add location btn load failed",true);
	}

	@Override
	public void changeOneLocationToParent(String locationName, String locationRelationship, String locationGroupType) throws Exception {
		if (locationRows.size() > 0) {
			List<WebElement> locationDetailsLinks = locationRows.get(0).findElements(By.cssSelector("button[type='button']"));
			click(locationDetailsLinks.get(0));
			click(editLocationBtn);
			selectByVisibleText(locationGroupSelect, locationRelationship);
			click(getDriver().findElement(By.cssSelector("input[aria-label=\""+locationGroupType+"\"] ")));
			scrollToBottom();
			click(saveBtnInUpdateLocationPage);
			waitForSeconds(5);
			SimpleUtils.pass("Location update done");
		}else
			SimpleUtils.fail("No search result",true);
		waitForSeconds(10);
		searchLocation(locationName);
		if (verifyIsThisLocationGroup()) {
			SimpleUtils.pass("Change None location to parent successfully");
		}else
			SimpleUtils.fail("Change location to parent Location failed",true);
	}

	@Override
	public void changeOneLocationToChild(String locationName, String locationRelationship, String parentLocation) throws Exception {
		if (locationRows.size() > 0) {
			List<WebElement> locationDetailsLinks = locationRows.get(0).findElements(By.cssSelector("button[type='button']"));
			click(locationDetailsLinks.get(0));
			click(editLocationBtn);
			selectByVisibleText(locationGroupSelect, locationRelationship);
			click(selectParentLocation);
			selectLocationOrDistrict(parentLocation,1);
			locationId.sendKeys(parentLocation);
			scrollToBottom();
			click(saveBtnInUpdateLocationPage);
			waitForSeconds(5);
			SimpleUtils.pass("Location update done");
		}else
			SimpleUtils.fail("No search result",true);
		waitForSeconds(10);
		searchLocation(locationName);
		if (!verifyIsThisLocationGroup()) {
			SimpleUtils.pass("Change None location to child successfully");
		}else
			SimpleUtils.fail("Change location to child Location failed",true);
	}

	@Override
	public void updateParentLocationDistrict(String searchCharacter, int index) {
		if (locationRows.size() > 0) {
			List<WebElement> locationDetailsLinks = locationRows.get(0).findElements(By.cssSelector("button[type='button']"));
			click(locationDetailsLinks.get(0));
			click(editLocationBtn);
			click(selectOneInChooseDistrict);
			selectLocationOrDistrict(searchCharacter,index);
			scrollToBottom();
			click(saveBtnInUpdateLocationPage);
			waitForSeconds(5);
			SimpleUtils.pass("Location update done");
		}else
			SimpleUtils.fail("No search result",true);

	}

	@Override
	public void disableEnableLocation(String locationName, String action) throws Exception {
		searchInput.clear();
		searchLocation(locationName);
		if (locationRows.size() > 0) {
			List<WebElement> locationDetailsLinks = locationRows.get(0).findElements(By.cssSelector("button[type='button']"));
			click(locationDetailsLinks.get(0));
			click(getDriver().findElement(By.cssSelector("lg-button[label=\""+action+"\"] ")));
			click(getDriver().findElement(By.cssSelector("lg-button[label=\""+action+"\"] ")));
			waitForSeconds(3);
			if (!getDriver().findElement(By.xpath("//div[1]/form-buttons/div[2]/lg-button[1]/button")).getText().equals(action)) {
				SimpleUtils.pass(action+" " +locationName +" successfully");
			}else
				SimpleUtils.fail(action+" " +locationName +" successfully",true);
			click(backBtnInLocationDetailsPage);
		}else
			SimpleUtils.fail("No search result",true);
	}

	@Override
	public boolean verifyLGIconShowWellOrNot(String locationName, int childLocationNum) {
		HashMap<String,Object> locationGroupIcons =  new HashMap<>();
		if (locationRows.size()>0 && locationRows.size()==childLocationNum+1) {
			for (int i = 0; i <locationRows.size() ; i++) {
				locationGroupIcons.put("locationGroupIcon",locationRows.get(i).findElement(By.cssSelector("td:nth-child(1) >div")).getAttribute("src"));
			}
			return true;

		}
		return false;
	}


	@FindBy(css = "lg-button[label=\"Ok\"]")
	private WebElement okBtnInLocationGroupConfirmPage;

	@Override
	public void changeOneLocationToNone(String locationToNone) throws Exception {
		//update parent location to None
		if (locationRows.size()>0) {
			if (verifyIsThisLocationGroup()) {
				for (WebElement eachRow: locationRows
					 ) {
					if (eachRow.findElement(By.cssSelector("button[type='button']")).getText().contains(locationToNone)) {
						click(eachRow.findElement(By.cssSelector("button[type='button']")));
					}
				}
//				List<WebElement> locationDetailsLinks = locationRows.get(0).findElements(By.cssSelector("button[type='button']"));
//				click(locationDetailsLinks.get(0));
				click(editLocationBtn);
				selectByVisibleText(locationGroupSelect, "None");
				click(okBtnInLocationGroupConfirmPage);
				scrollToBottom();
				click(saveBtnInUpdateLocationPage);
			}else
				SimpleUtils.fail("It's not a parent location",true);
		}
		waitForSeconds(10);
		searchLocation(locationToNone);
		if (!verifyIsThisLocationGroup()) {
			SimpleUtils.pass(locationToNone+" was updated to None");
		}else
			SimpleUtils.fail("Update failed",true);

	}

	private boolean verifyIsThisLocationGroup() {
		ArrayList<String> iconInfoOfLG = new ArrayList<>();

		List<WebElement> locationIcon = getDriver().findElements(By.cssSelector(".group-type-icon >img"));
		for (WebElement icon:locationIcon
			 ) {
			iconInfoOfLG.add(icon.getAttribute("ng-src"));
		}
		if (iconInfoOfLG.contains("img/legion/lgComponents/group.svg") ) {
			return true;
		}else
			return false;

	}

	@Override
	public void updateChangePTPLocationToNone(String LGPTPLocationName) {

	}

	// Added by Fiona
	// sub dsitrict page
	@FindBy(css = "lg-button[label=\"Add District\"]")
	private WebElement addDistrictButton;

	@FindBy(css = "input[placeholder=\"You can search by name,id.\"]")
	private WebElement districtSearchInputBox;

	@FindBy(css = ".lg-search-icon")
	private WebElement searchDistrictBtn;

	@FindBy(css = ".lg-pagination__pages.ng-binding")
	private WebElement pageNumberText;

	@FindBy(xpath = "//table/tbody/tr[2]/td[1]/lg-button/button/span/span")
	private WebElement districtName;

	@FindBy(css = "tr[ng-repeat=\"district in filteredDistricts\"]")
	private List<WebElement> districtsRows;

	@FindBy(css = ".lg-pagination__pages.ng-binding select")
	private WebElement pageNumSelector;

	@FindBy(css = "tbody > tr:nth-child(2) > td.number.ng-binding")
	private WebElement enableDistrcit;

	@FindBy(css = "lg-select[search-hint='Search Location'] div.input-faked")
	private WebElement locationSelectorButton;

	@FindBy(css = "lg-button[label=\"Edit District\"]")
	private WebElement editDistrictBtn;

	@FindBy(css = "lg-button[label=\"Manage\"]")
	private  WebElement managementLocationBtn;

	@FindBy(css = "table.lg-table tbody")
	private WebElement locationsInManageLocationPopup;

	@Override
	public void clickModelSwitchIconInOpsPage() {
		if (isElementEnabled(modeSwitchIcon, 10)) {
			click(modeSwitchIcon);
			waitForSeconds(3);
			click(consoleTitleMenu);
			switchToNewWindow();
			if (isElementEnabled(locationSelectorButton, 5)) {
				SimpleUtils.pass("switch to Console page successfully");
			} else
				SimpleUtils.fail("switch to Console portal failed", false);
		} else
			SimpleUtils.fail("mode switch img load failed", false);
	}


	@Override
	public void goToSubDistrictsInLocationsPage() throws Exception {
		if (isElementLoaded(districtsInLocations, 20)) {
			click(districtsInLocations);
			if (isElementEnabled(addDistrictButton, 20)) {
				SimpleUtils.pass("sub-district page load successfully");
			} else
				SimpleUtils.fail("sub-dsitrict page load failed", false);
		} else
			SimpleUtils.fail("locations tab load failed in location overview page", false);
	}

	@Override
	public void validateTheAddDistrictBtn() throws Exception {
		if (isElementLoaded(addDistrictButton, 5)) {
			SimpleUtils.pass("Add new district button shows well");
		} else {
			SimpleUtils.pass("Add new district button doesn't show");
		}
	}


	@Override
	public void searchDistrict(String searchInputText) throws Exception {
		if (isElementLoaded(districtSearchInputBox, 10)) {
			clickTheElement(districtSearchInputBox);
			districtSearchInputBox.sendKeys(searchInputText);
			districtSearchInputBox.sendKeys(Keys.ENTER);
			waitForSeconds(3);
			if (districtsRows.size() > 0) {
				SimpleUtils.pass("Can search out location by using " + searchInputText);
			} else {
				SimpleUtils.pass("Can't search out any locations by using " + searchInputText);
				districtSearchInputBox.clear();
			}
		} else {
			SimpleUtils.fail("Search input is not clickable", true);
		}
	}

	//get total enabled status district count
	@Override
	public int getTotalEnabledDistrictsCount() throws Exception {
		waitForSeconds(10);
		int enableDistrcitCount = 0;
		//get enable districts count from smart card
		if (isElementLoaded(enableDistrcit, 15)) {
			if (!enableDistrcit.getText().isEmpty()) {
				enableDistrcitCount = Integer.parseInt(enableDistrcit.getText().trim());
				SimpleUtils.pass("The count of enabled status districts shows in district samrt card: " + enableDistrcitCount);
			}
		} else {
			SimpleUtils.pass("District smart card loaded failed");
		}
		return enableDistrcitCount;
	}

	// Search district with characters and return the districts count, which maybe include disabled ditricts
	@Override
	public List<Integer> getSearchDistrictsResultsCount(String searchInputText) throws Exception {

		List<Integer> searchResultsList = new ArrayList<Integer>();
		String[] searchLocationCha = searchInputText.split(",");
		int searchedDistrictsCount = 0;

		if (isElementLoaded(districtSearchInputBox, 15)) {
			for (int i = 0; i < searchLocationCha.length; i++) {

				searchDistrict(searchLocationCha[i]);

				//Get the total count of search results
				String totalResultsPages = null;
				String[] pageText = pageNumberText.getText().trim().split(" ");
				if (pageText.length > 0 && !pageText[1].isEmpty()) {
					totalResultsPages = pageText[1];
					selectByVisibleText(pageNumSelector, totalResultsPages);
					if (districtsRows.size() > 0) {
						int maxPageNumber = Integer.parseInt(totalResultsPages);
						searchedDistrictsCount = (maxPageNumber - 1) * 10 + districtsRows.size();
						SimpleUtils.pass("Districts: " + searchedDistrictsCount + " district(s) found by " + searchLocationCha[i]);
						searchResultsList.add(searchedDistrictsCount);
						districtSearchInputBox.clear();
					} else {
						SimpleUtils.pass("Can Not search out any locations");
					}
				} else {
					SimpleUtils.pass("District list page number load failed");
				}
			}
		} else {
			SimpleUtils.fail("District search input box is not clickable", true);
		}
		return searchResultsList;
	}

	@Override
	public List<String> getLocationsInDistrict(String districtName) throws Exception {
		List<WebElement> locationsInManageLocation = new ArrayList<>();
		List<String> locations = new ArrayList<>();
		if (isElementLoaded(districtSearchInputBox, 15)) {
			if (districtName != null && !districtName.isEmpty()) {
				districtSearchInputBox.clear();
				searchDistrict(districtName);
				waitForSeconds(10);
				if (districtsRows.size() > 0) {
					click(districtsRows.get(0).findElement(By.cssSelector("lg-button")));
					waitUntilElementIsVisible(editDistrictBtn);
					click(editDistrictBtn);
					click(managementLocationBtn);
					if(isElementLoaded(locationsInManageLocationPopup,5)){
						SimpleUtils.pass("Manage location popup window is showing Now");
						locationsInManageLocation = locationsInManageLocationPopup.findElements(By.cssSelector("div.lg-select-list__name span"));
						for(WebElement location:locationsInManageLocation){
							String locationName = location.getText();
							locations.add(locationName);
						}
						SimpleUtils.pass("There is " + locations.size() + " locations in " + districtName);
					}else{
						SimpleUtils.pass("Manage location popup window is not showing");
					}
				} else {
					SimpleUtils.pass("Can Not search out any locations");
				}
			}else{
				SimpleUtils.pass("Search test is empty!");
			}
		}else{
			SimpleUtils.pass("District search input box is not loaded!");
		}
		return locations;
	}


}

