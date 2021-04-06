package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.LocationsPage;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;
import org.apache.commons.collections.ListUtils;
import cucumber.api.java.ro.Si;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
	@FindBy(css="[title='Upperfields']")
	private WebElement upperfieldsInLocations;

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
		waitForSeconds(3);
		if (isElementEnabled(modeSwitchIcon,10)) {
			clickTheElement(modeSwitchIcon);
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
		if (isElementLoaded(goToLocationsButton, 150))
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
			&& isElementLoaded(locationsInLocations,5) && isElementLoaded(upperfieldsInLocations)) {
				SimpleUtils.pass("Location overview page show well when OPview turn on");
			}else
				SimpleUtils.fail("Location overview page load failed when OPview turn on",false);
		}else {
			if (isElementLoaded(enterPriseProfileInLocations,5)
					&& isElementLoaded(locationsInLocations,5) && isElementLoaded(upperfieldsInLocations)) {
				SimpleUtils.pass("Location overview page show well when OPview turn off");
			}else
				SimpleUtils.fail("Location overview page load failed when OPview turn off",false);
		}

	}

	@Override
	public void goToSubLocationsInLocationsPage() throws Exception {
		if ( isElementLoaded(locationsInLocations,5)) {
			click(locationsInLocations);
			waitForSeconds(20);
			if (isElementEnabled(addLocationBtn,5)) {
				SimpleUtils.pass("sub-location page load successfully");
			}else
				SimpleUtils.fail("sub-location page load failed",false);
		}else
			SimpleUtils.fail("locations tab load failed in location overview page",false);
	}



	//new location page
	@FindBy(css = "input[aria-label=\"Display Name\"]")
	private WebElement displayNameInput;
	@FindBy(css = "input[aria-label=\"Name\"]")
	private WebElement nameInput;
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
	@FindBy(css = "input-field[label=\"State\"]>ng-form")
	private WebElement state;
	@FindBy(css = "div.lg-search-options__scroller")
	private WebElement stateList;
	@FindBy(css = "div.lg-search-options__scroller>div:nth-child(1)")
	private WebElement firstState;
	@FindBy(css="input[aria-label=\"Zip Code\"]")
	private WebElement zipCode;
	@FindBy(css="input[aria-label=\"Primary Contact\"]")
	private WebElement primaryContact;
	@FindBy(css="input[aria-label=\"Phone Number\"]")
	private WebElement phoneNumber;
	@FindBy(css="input[aria-label=\"Email Address\"]")
	private WebElement emailAddress;
	@FindBy(css="input-field[label=\"Source Location\"] > ng-form > div.input-choose > span")
	private WebElement selectOneInSourceLocation;
	@FindBy(css=".lg-modal__title-icon")
	private WebElement selectALocationTitle;
	@FindBy(css="div.lg-tab-toolbar__search >lg-search >input-field>ng-form>input")
	private WebElement searchInputInSelectALocation;
	@FindBy(css="tr[ng-repeat=\"item in $ctrl.currentPageItems track by $index\"]")
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

	@FindBy(css="input-field[label=\"Effective Date\"]")
	private WebElement effectiveDateSelect;
	@FindBy(css = "div.lg-single-calendar-date-wrapper")
	private WebElement firstDay;
	@FindBy(css = "a[ng-click=\"$ctrl.changeMonth(-1)\"]")
	private List<WebElement> previousMonthBtn;

	@FindBy(css="lg-button[label=\"Create Location\"]")
	private WebElement createLocationBtn;
	@FindBy(css="lg-button[label=\"Cancel\"]")
	private WebElement cancelBtn;

	@Override
	public void addNewRegularLocationWithMandatoryFields(String locationName) throws Exception {

		if (isElementEnabled(addLocationBtn,15)) {
			click(addLocationBtn);
			displayNameInput.sendKeys(locationName);
			setLocationName(locationName);
			locationId.sendKeys(getLocationName());
			nameInput.sendKeys(getLocationName());
			selectByVisibleText(timeZoonSelect,newLocationParas.get("Time_Zone"));
			LocationAddress1.sendKeys(newLocationParas.get("Location_Address"));
			selectByVisibleText(countrySelect,newLocationParas.get("Country"));
			waitForSeconds(3);
//			selectByVisibleText(stateSelect,newLocationParas.get("State"));
			click(state);
			if (!isElementEnabled(stateList, 10)) {
				click(state);
			}
			click(firstState);
			city.sendKeys(newLocationParas.get("City"));
			zipCode.sendKeys(newLocationParas.get("Zip_Code"));
			if (isElementEnabled(configTypeSelect,5)) {
				selectByVisibleText(configTypeSelect,newLocationParas.get("Configuration_Type"));
			}
			click(effectiveDateSelect);
			click(firstDay.findElement(By.cssSelector("div:nth-child(8)")));
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
	@FindBy(xpath = "//table/tbody/tr[2]/td[1]/div/lg-button/button/span/span")
	private WebElement locationsName;

	@Override
	public boolean searchNewLocation(String locationName) {

		if (isElementEnabled(searchInput,10)) {
			searchInput.sendKeys(locationName);
			searchInput.sendKeys(Keys.ENTER);
			waitForSeconds(5);
			if (locationsName.getText().trim().equalsIgnoreCase(locationName.trim())) {
				SimpleUtils.pass("the location is searched");
				return true;
			}
		}else
          SimpleUtils.fail("search filed load failed",false);
		   return false;
	}

	@Override
	public void addNewRegularLocationWithAllFields(String locationName, String searchCharactor,int index) throws Exception {
		if (isElementEnabled(addLocationBtn,15)) {
			click(addLocationBtn);
			displayNameInput.sendKeys(locationName);
			setLocationName(locationName);
			locationId.sendKeys(getLocationName());
			nameInput.sendKeys(getLocationName());
			selectByVisibleText(timeZoonSelect,newLocationParas.get("Time_Zone"));
			LocationAddress1.sendKeys(newLocationParas.get("Location_Address"));
			selectByVisibleText(countrySelect,newLocationParas.get("Country"));
//			selectByVisibleText(stateSelect,newLocationParas.get("State"));
			click(state);
			if (!isElementEnabled(stateList, 10)) {
				click(state);
			}
			click(firstState);
			city.sendKeys(newLocationParas.get("City"));
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
			click(firstDay.findElement(By.cssSelector("div:nth-child(8)")));
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
				waitForSeconds(20);
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
			waitForSeconds(15);
			if (locationRowsInSelectLocation.size()>0) {
				WebElement firstRow = locationRowsInSelectLocation.get(index).findElement(By.cssSelector("input[type=\"checkbox\"]"));
				click(firstRow);
				click(okBtnInSelectLocation);
			}else
				SimpleUtils.report("Search location result is 0");

		}else
			SimpleUtils.fail("Select a location window load failed",true);

	}

	@FindBy(css = "select[aria-label=\"Location Type\"]")
	private WebElement locationTypeSelector;
	@FindBy(css=" input-field:nth-child(2) > ng-form > div.input-choose.ng-scope > span")
	private WebElement selectOneInBaseLocation;
	@FindBy(css="lg-button[label=\"Leave this page\"]")
	private WebElement leaveThisPageBtn;
	@Override
	public void addNewMockLocationWithAllFields(String locationName, String searchCharactor, int index) throws Exception {
		if (isElementEnabled(addLocationBtn,15)) {
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
	@FindBy(css = "lg-button[label=\"OK\"]")
	private WebElement okBtnInImportLocationPage;
	@FindBy(css = "div[ng-if=\"loaded\"]>div:nth-child(4)")
	private WebElement importFileLoadSuccessMessage;


	@Override
	public void verifyImportLocationDistrict() {
		  String pth = System.getProperty("user.dir");
		if (isElementEnabled(importBtn,5)) {
			click(importBtn);
			if (verifyImportLocationsPageShow()) {
				SimpleUtils.pass("Import location page show well");
			}else
				SimpleUtils.fail("Import location page load failed",true);
			uploaderFileInputBtn.sendKeys(pth+"/src/test/resources/LocationImportTemplate.csv");
			waitForSeconds(5);
			click(importBtnInImportLocationPage);
			waitForSeconds(15);
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
		if (isElementEnabled(addLocationBtn,15)) {
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
		if (isElementEnabled(addLocationBtn,15)) {
			click(addLocationBtn);
			selectByVisibleText(locationTypeSelector,newLocationParas.get("Location_Type_NSO"));
			displayNameInput.sendKeys(locationName);
			setLocationName(locationName);
			locationId.sendKeys(getLocationName());
			nameInput.sendKeys(getLocationName());
			selectByVisibleText(timeZoonSelect,newLocationParas.get("Time_Zone"));
			LocationAddress1.sendKeys(newLocationParas.get("Location_Address"));
			selectByVisibleText(countrySelect,newLocationParas.get("Country"));
//			selectByVisibleText(stateSelect,newLocationParas.get("State"));
			click(state);
			if (!isElementEnabled(stateList, 10)) {
				click(state);
			}
			click(firstState);
			city.sendKeys(newLocationParas.get("City"));
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
			click(getDriver().findElement(By.cssSelector("lg-picker-input[label=\"Effective Date\"] > div > div > ng-transclude > lg-single-calendar > div.lg-single-calendar-body > div.lg-single-calendar-date-wrapper > div:nth-child(8)")));

			click(launchDateSelecter);
			click(previousMonthBtn.get(1));
			scrollToBottom();
			click(getDriver().findElement(By.cssSelector("lg-picker-input[label=\"Launch date\"] > div > div > ng-transclude > lg-single-calendar > div.lg-single-calendar-body > div.lg-single-calendar-date-wrapper > div:nth-child(9)")));
			waitForSeconds(2);
			click(selectOneComparableLocation);
			selectLocationOrDistrict(searchCharactor,index);
			waitForSeconds(2);

			click(comparableStoreDateSelecter);
			click(previousMonthBtn.get(2));
			click(getDriver().findElement(By.cssSelector("lg-picker-input[label=\"End Comparable-store date\"] > div > div > ng-transclude > lg-single-calendar > div.lg-single-calendar-body > div.lg-single-calendar-date-wrapper > div:nth-child(10)")));
			itemsAndTransactionInoutField.get(0).sendKeys("1");
			itemsAndTransactionInoutField.get(1).sendKeys("1");
			scrollToBottom();
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
				searchInput.clear();
				searchInput.sendKeys(searchLocationCha[0]);
				searchInput.sendKeys(Keys.ENTER);
				waitForSeconds(20);
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
			waitForSeconds(10);
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
					waitForSeconds(5);
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
			SimpleUtils.fail("Location can not been searched",true);
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
			waitForSeconds(2);
			click(exportAllRadio);
			click(okBtnInExportLocationPage);
			waitForSeconds(10);

//			TimeZone timeZone = TimeZone.getTimeZone("America/Chicago");
//			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM");
//			dfs.setTimeZone(timeZone);
//			String currentTime =  dfs.format(new Date());
//			String downloadPath = propertyMap.get("Download_File_Default_Dir");//when someone run ,need to change this path
//			Assert.assertTrue(FileDownloadVerify.isFileDownloaded_Ext(downloadPath, "LEG-"), "Download successfully");

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

//			TimeZone timeZone = TimeZone.getTimeZone("America/Chicago");
//			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM");
//			dfs.setTimeZone(timeZone);
//			String currentTime =  dfs.format(new Date());
//			String downloadPath = propertyMap.get("Download_File_Default_Dir");//when someone run ,need to change this path
//			System.out.println(downloadPath);
//			Assert.assertTrue(FileDownloadVerify.isFileDownloaded_Ext(downloadPath, "LEG-"), "Download successfully");

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
			displayNameInput.clear();
			displayNameInput.sendKeys(locationName+"update");
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
				SimpleUtils.pass("Search parent location successfully and show parent and all sub-location ");
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
	@FindBy(css = "inline-input:nth-child(2) > ng-transclude > div > input-field > ng-form > div.input-choose.ng-scope > span")
	private WebElement selectParentLocation;

	@Override
	public void addChildLocation(String locationType, String childlocationName, String locationName, String searchCharactor, int index, String childRelationship) throws Exception {
		if (isElementEnabled(addLocationBtn,15)) {
			click(addLocationBtn);
			selectByVisibleText(locationTypeSelector,locationType);
			displayNameInput.sendKeys(childlocationName);
			setLocationName(childlocationName);
			selectByVisibleText(locationGroupSelect,newLocationParas.get(childRelationship));
			click(selectParentLocation);
			selectLocationOrDistrict(locationName,index);
			locationId.sendKeys(getLocationName());
			nameInput.sendKeys(getLocationName());
			selectByVisibleText(timeZoonSelect,newLocationParas.get("Time_Zone"));
			LocationAddress1.sendKeys(newLocationParas.get("Location_Address"));
			selectByVisibleText(countrySelect,newLocationParas.get("Country"));
//			selectByVisibleText(stateSelect,newLocationParas.get("State"));
			click(state);
			if (!isElementEnabled(stateList, 10)) {
				click(state);
			}
			click(firstState);
			city.sendKeys(newLocationParas.get("City"));
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
			click(firstDay.findElement(By.cssSelector("div:nth-child(8)")));
			scrollToBottom();
			click(createLocationBtn);
			waitForSeconds(10);
			SimpleUtils.pass("Child location creation done");

		}else
			SimpleUtils.fail("New location page load failed",false);
	}

	@Override
	public void addParentLocation(String locationType, String locationName, String searchCharactor, int index, String parentRelationship, String value) throws Exception {
		if (isElementEnabled(addLocationBtn,20)) {
			click(addLocationBtn);
			selectByVisibleText(locationTypeSelector,locationType);
			displayNameInput.sendKeys(locationName);
			setLocationName(locationName);
			selectByVisibleText(locationGroupSelect,newLocationParas.get(parentRelationship));
			click(getDriver().findElement(By.cssSelector("input[aria-label=\""+value+"\"] ")));
			locationId.sendKeys(getLocationName());
			nameInput.sendKeys(getLocationName());
			selectByVisibleText(timeZoonSelect,newLocationParas.get("Time_Zone"));
			LocationAddress1.sendKeys(newLocationParas.get("Location_Address"));
			selectByVisibleText(countrySelect,newLocationParas.get("Country"));
//			selectByVisibleText(stateSelect,newLocationParas.get("State"));
			click(state);
			if (!isElementEnabled(stateList, 10)) {
				click(state);
			}
			click(firstState);
			city.sendKeys(newLocationParas.get("City"));
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
			click(firstDay.findElement(By.cssSelector("div:nth-child(8)")));
			scrollToBottom();
			click(createLocationBtn);
			waitForSeconds(10);
			SimpleUtils.pass("location creation done");

		}else
			SimpleUtils.fail("New location page load failed",false);
	}

	@Override
	public void addParentLocationForNsoType(String locationType, String locationName, String searchCharactor, int index, String parentRelationship, String value) throws Exception {
		if (isElementEnabled(addLocationBtn,20)) {
			click(addLocationBtn);
			selectByVisibleText(locationTypeSelector,locationType);
			displayNameInput.sendKeys(locationName);
			setLocationName(locationName);
			selectByVisibleText(locationGroupSelect,newLocationParas.get(parentRelationship));
			click(getDriver().findElement(By.cssSelector("input[aria-label=\""+value+"\"] ")));
			locationId.sendKeys(getLocationName());
			nameInput.sendKeys(getLocationName());
			selectByVisibleText(timeZoonSelect,newLocationParas.get("Time_Zone"));
			LocationAddress1.sendKeys(newLocationParas.get("Location_Address"));
			selectByVisibleText(countrySelect,newLocationParas.get("Country"));
//			selectByVisibleText(stateSelect,newLocationParas.get("State"));
			click(state);
			if (!isElementEnabled(stateList, 10)) {
				click(state);
			}
			click(firstState);
			city.sendKeys(newLocationParas.get("City"));
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
			click(getDriver().findElement(By.cssSelector("lg-picker-input[label=\"Effective Date\"] > div > div > ng-transclude > lg-single-calendar > div.lg-single-calendar-body > div.lg-single-calendar-date-wrapper > div:nth-child(8)")));

			click(launchDateSelecter);
			click(previousMonthBtn.get(1));
			scrollToBottom();
			click(getDriver().findElement(By.cssSelector("lg-picker-input[label=\"Launch date\"] > div > div > ng-transclude > lg-single-calendar > div.lg-single-calendar-body > div.lg-single-calendar-date-wrapper > div:nth-child(9)")));
			waitForSeconds(2);
			click(selectOneComparableLocation);
			selectLocationOrDistrict(searchCharactor,index);
			waitForSeconds(2);

			click(comparableStoreDateSelecter);
			click(previousMonthBtn.get(2));
			click(getDriver().findElement(By.cssSelector("lg-picker-input[label=\"End Comparable-store date\"] > div > div > ng-transclude > lg-single-calendar > div.lg-single-calendar-body > div.lg-single-calendar-date-wrapper > div:nth-child(10)")));
			itemsAndTransactionInoutField.get(0).sendKeys("1");
			itemsAndTransactionInoutField.get(1).sendKeys("1");
			scrollToBottom();
			click(createLocationBtn);
			waitForSeconds(20);
			SimpleUtils.pass("New location creation done");

		}else
			SimpleUtils.fail("New location creation page load failed",true);
	}

	@Override
	public void addChildLocationForNSO(String locationType, String childLocationName, String locationName, String searchCharactor, int index, String childRelationship) throws Exception {
		if (isElementEnabled(addLocationBtn,20)) {
			click(addLocationBtn);
			selectByVisibleText(locationTypeSelector,locationType);
			displayNameInput.sendKeys(childLocationName);
			setLocationName(childLocationName);
			selectByVisibleText(locationGroupSelect,newLocationParas.get(childRelationship));
			click(selectParentLocation);
			selectLocationOrDistrict(locationName,index);
			locationId.sendKeys(getLocationName());
			nameInput.sendKeys(getLocationName());
			selectByVisibleText(timeZoonSelect,newLocationParas.get("Time_Zone"));
			LocationAddress1.sendKeys(newLocationParas.get("Location_Address"));
			selectByVisibleText(countrySelect,newLocationParas.get("Country"));
//			selectByVisibleText(stateSelect,newLocationParas.get("State"));
			click(state);
			if (!isElementEnabled(stateList, 10)) {
				click(state);
			}
			click(firstState);
			city.sendKeys(newLocationParas.get("City"));
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
			click(getDriver().findElement(By.cssSelector("lg-picker-input[label=\"Effective Date\"] > div > div > ng-transclude > lg-single-calendar > div.lg-single-calendar-body > div.lg-single-calendar-date-wrapper > div:nth-child(8)")));

			click(launchDateSelecter);
			click(previousMonthBtn.get(1));
			scrollToBottom();
			click(getDriver().findElement(By.cssSelector("lg-picker-input[label=\"Launch date\"] > div > div > ng-transclude > lg-single-calendar > div.lg-single-calendar-body > div.lg-single-calendar-date-wrapper > div:nth-child(9)")));
			waitForSeconds(2);
			click(selectOneComparableLocation);
			selectLocationOrDistrict(searchCharactor,index);
			waitForSeconds(2);

			click(comparableStoreDateSelecter);
			click(previousMonthBtn.get(2));
			click(getDriver().findElement(By.cssSelector("lg-picker-input[label=\"End Comparable-store date\"] > div > div > ng-transclude > lg-single-calendar > div.lg-single-calendar-body > div.lg-single-calendar-date-wrapper > div:nth-child(10)")));
			itemsAndTransactionInoutField.get(0).sendKeys("1");
			itemsAndTransactionInoutField.get(1).sendKeys("1");
			scrollToBottom();
			click(createLocationBtn);
			waitForSeconds(20);
			SimpleUtils.pass("New location creation done");

		}else
			SimpleUtils.fail("New location creation page load failed",true);
	}

	@Override
	public void checkThereIsNoLocationGroupSettingFieldWhenLocationTypeIsMock() throws Exception {
		if (isElementEnabled(addLocationBtn, 20)) {
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
			scrollToBottom();
			click(saveBtnInUpdateLocationPage);
			waitForSeconds(5);
			SimpleUtils.pass("Location update done");
		}else
			SimpleUtils.fail("No search result",true);
		waitForSeconds(10);
		searchLocation(locationName);
		if (verifyIsThisLocationGroup()) {
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
	public boolean isItMSLG() {
		ArrayList<String> iconInfoOfLG = new ArrayList<>();

		List<WebElement> locationIcon = getDriver().findElements(By.cssSelector(".group-type-icon >img"));
		for (WebElement icon:locationIcon
		) {
			iconInfoOfLG.add(icon.getAttribute("ng-src"));
		}
		if (iconInfoOfLG.contains("img/legion/lgComponents/group.svg") && iconInfoOfLG.contains("img/legion/lgComponents/group-master-slave.svg") ) {
			return true;
		}else
			return false;

	}

	@Override
	public void changeLGToMSOrP2P(String value) throws Exception {
		if (locationRows.size() > 0) {
			List<WebElement> locationDetailsLinks = locationRows.get(0).findElements(By.cssSelector("button[type='button']"));
			click(locationDetailsLinks.get(0));
			click(editLocationBtn);
			click(getDriver().findElement(By.cssSelector("input[aria-label=\""+value+"\"] ")));
			scrollToBottom();
			click(saveBtnInUpdateLocationPage);
			waitForSeconds(5);
			SimpleUtils.pass("Location update done");
		}else
			SimpleUtils.fail("No search result",true);
		waitForSeconds(10);
//		searchLocation(locationName);
//		if (!isItMSLG()) {
//			SimpleUtils.pass("Change None location to parent successfully");
//		}else
//			SimpleUtils.fail("Change location to parent Location failed",true);
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
		//update  location group  to None
		searchInput.clear();
		searchLocation(locationToNone);
		if (locationRows.size()>0) {
			if (verifyIsThisLocationGroup()) {
				List<WebElement> locationDetailsLinks = locationRows.get(0).findElements(By.cssSelector("button[type='button']"));
				click(locationDetailsLinks.get(0));
				click(editLocationBtn);
				selectByVisibleText(locationGroupSelect, "None");
				waitForSeconds(5);
				click(okBtnInLocationGroupConfirmPage);
				scrollToBottom();
				click(saveBtnInUpdateLocationPage);
			}else
				SimpleUtils.fail("It's not a parent location",false);
		}
		waitForSeconds(10);
		searchLocation(locationToNone);
		if (!verifyIsThisLocationGroup()) {
			SimpleUtils.pass(locationToNone+" was updated to None successfully");
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
		if (iconInfoOfLG.contains("img/legion/lgComponents/group.svg")){
			return true;
		} else if (iconInfoOfLG.contains("img/legion/lgComponents/group-master-slave.svg")){
			return true;
		}else if (iconInfoOfLG.contains("img/legion/lgComponents/group-peer-to-peer.svg")){
			return true;
		}else
			return false;

	}

	@Override
	public void updateChangePTPLocationToNone(String LGPTPLocationName) {

	}

	// Added by Fiona
	// sub dsitrict page
	@FindBy(css = "lg-button[label=\"Add Upperfield\"]")
	private WebElement addUpperfieldsButton;

	@FindBy(css = "input[placeholder=\"You can search upperfield by name, status or creator.\"]")
	private WebElement upperfieldsSearchInputBox;

	@FindBy(css = ".lg-search-icon")
	private WebElement searchDistrictBtn;

	@FindBy(css = ".lg-pagination__pages.ng-binding")
	private WebElement pageNumberText;

	@FindBy(xpath = "//table/tbody/tr[2]/td[1]/lg-button/button/span/span")
	private WebElement districtName;

	@FindBy(css = "tr[ng-repeat=\"upperfield in filteredUpperfields\"]")
	private List<WebElement> upperfieldRows;

	@FindBy(css = "select[ng-attr-aria-label=\"{{$ctrl.label}}\"]")
	private WebElement pageNumSelector;

	@FindBy(css = "tbody > tr:nth-child(2) > td.number.ng-binding")
	private WebElement enableDistrcit;

	@FindBy(css = "lg-select[search-hint='Search Location'] div.input-faked")
	private WebElement locationSelectorButton;

	@FindBy(css = "lg-button[label=\"Edit Upperfield\"]")
	private WebElement editUpperfieldBtn;

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
	public void goToUpperFieldsPage() throws Exception {
		if (isElementLoaded(upperfieldsInLocations, 20)&& upperfieldsInLocations.getText().contains("Upperfields") &&
				upperfieldsInLocations.getText().contains("Upperfields Configured")&&upperfieldsInLocations.getText().contains("Upperfield Information")
		&&upperfieldsInLocations.getText().contains("Add, remove upperfields")) {
			click(upperfieldsInLocations);
			if (isElementEnabled(addUpperfieldsButton, 20)) {
				SimpleUtils.pass("UpperFields tile load successfully");
			} else
				SimpleUtils.fail("UpperFields load failed", false);
		} else
			SimpleUtils.fail("locations tab load failed in location overview page", false);
	}

	@Override
	public void validateTheAddDistrictBtn() throws Exception {
		if (isElementLoaded(addUpperfieldsButton, 5)) {
			SimpleUtils.pass("Add new district button shows well");
		} else {
			SimpleUtils.pass("Add new district button doesn't show");
		}
	}



	public void searchUpperFields(String searchInputText) throws Exception {
		String[] searchLocationCha = searchInputText.split(",");
		if (isElementLoaded(upperfieldsSearchInputBox, 10)) {
			for (int i = 0; i < searchLocationCha.length; i++) {
				upperfieldsSearchInputBox.clear();
				upperfieldsSearchInputBox.sendKeys(searchInputText);
				upperfieldsSearchInputBox.sendKeys(Keys.ENTER);
				waitForSeconds(3);
				if (upperfieldRows.size() > 0) {
					SimpleUtils.pass("Can search out upperfield by using " + searchInputText);
					break;
				} else {
					SimpleUtils.report("There are no upperfields that match your criteria by using " + searchInputText);
					waitForSeconds(5);
					upperfieldsSearchInputBox.clear();
				}
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

		if (isElementLoaded(upperfieldsInLocations, 15)) {
			for (int i = 0; i < searchLocationCha.length; i++) {

				searchUpperFields(searchLocationCha[i]);

				//Get the total count of search results
				String totalResultsPages = null;
				String[] pageText = pageNumberText.getText().trim().split(" ");
				if (pageText.length > 0 && !pageText[1].isEmpty()) {
					totalResultsPages = pageText[1];
					selectByVisibleText(pageNumSelector, totalResultsPages);
					if (upperfieldRows.size() > 0) {
						int maxPageNumber = Integer.parseInt(totalResultsPages);
						searchedDistrictsCount = (maxPageNumber - 1) * 10 + upperfieldRows.size();
						SimpleUtils.pass("Districts: " + searchedDistrictsCount + " district(s) found by " + searchLocationCha[i]);
						searchResultsList.add(searchedDistrictsCount);
						upperfieldsInLocations.clear();
					} else {
						SimpleUtils.pass("Can Not search out any district");
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
		if (isElementLoaded(upperfieldsInLocations, 15)) {
			if (districtName != null && !districtName.isEmpty()) {
				upperfieldsInLocations.clear();
				searchUpperFields(districtName);
				waitForSeconds(10);
				if (upperfieldRows.size() > 0) {
					click(upperfieldRows.get(0).findElement(By.cssSelector("lg-button")));
					waitUntilElementIsVisible(editUpperfieldBtn);
					click(editUpperfieldBtn);
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


	//added by Estelle for district
    @FindBy (css = "a[ng-click=\"$ctrl.back()\"]")
	private WebElement backBtnInDistrictListPage;
	@FindBy (css = "div.card-carousel-fixed")
	private WebElement smartCardInDistrictListPage;
	@FindBy (css = ".lg-pagination__arrow--left")
	private WebElement pageLeftBtnInDistrict;
	@FindBy (css = ".lg-pagination__arrow--right")
	private WebElement pageRightBtnInDistrict;

	@Override
	public boolean verifyUpperFieldListShowWellOrNot() throws Exception {

		waitForSeconds(30);
		if (isElementLoaded(backBtnInDistrictListPage,3) && isElementLoaded(addUpperfieldsButton,3)
		&& isElementLoaded(upperfieldsSearchInputBox,3) && isElementLoaded(smartCardInDistrictListPage,3)
		&& isElementLoaded(pageLeftBtnInDistrict,3) && isElementLoaded(pageRightBtnInDistrict,3)
		) {
			return true;
		}
		return false;
	}

	@Override
	public void verifyBackBtnFunction() throws Exception {
		if (isElementLoaded(backBtnInDistrictListPage,3) ) {
			click(backBtnInDistrictListPage);
			if (isElementLoaded(enterPriseProfileInLocations,3)) {
				SimpleUtils.pass("Back button in district list page work well");
			}else
				SimpleUtils.fail("Back button in district page doesn't work",true);
		}
	}

	@Override
	public void verifyPaginationFunctionInLocation() throws Exception {
		waitForSeconds(20);
		if (isElementLoaded(pageNumSelector,3)) {
			int minPageNum = 1;
			String iniPageText = pageNumberText.getText().trim();
			String[] maxPageNumberOri = iniPageText.split("of");
			int maxPageNumber = Integer.valueOf(maxPageNumberOri[1].trim());
			if (maxPageNumber == minPageNum && pageLeftBtnInDistrict.getAttribute("class").contains("disabled")
					&& pageRightBtnInDistrict.getAttribute("class").contains("disabled")) {
				SimpleUtils.pass("There is only one page");
			}else {
				for (int i = 1; i <= Integer.valueOf(maxPageNumber); i++) {
					selectByVisibleText(pageNumSelector,String.valueOf(i));
					if (i <= Integer.valueOf(maxPageNumber)) {
						SimpleUtils.pass("Page " +i +" Select work well");
					}else
						SimpleUtils.fail("Page select doesn't work",true);
				}
				waitForSeconds(5);
				String firstLineText = locationRows.get(0).getText();
				click(pageLeftBtnInDistrict);
				String firstLineTextAftLeft = locationRows.get(0).getText();
				if (!firstLineTextAftLeft.equalsIgnoreCase(firstLineText) ) {
					SimpleUtils.pass("Left pagination button work well" );
				}else
					SimpleUtils.fail("Left pagination button work wrong",false);
				click(pageRightBtnInDistrict);
				String firstLineTextAftRight = locationRows.get(0).getText();
				if (!firstLineTextAftRight.equalsIgnoreCase(firstLineTextAftLeft) ) {
					SimpleUtils.pass("Right pagination button work well");
				}else
					SimpleUtils.fail("Right pagination button work wrong",false);

			}

		}else
			SimpleUtils.fail("Page select load failed",true);

	}


	@Override
	public void verifyPaginationFunctionInDistrict() throws Exception {
		waitForSeconds(20);
		if (isElementLoaded(pageNumSelector,3)) {
			int minPageNum = 1;
            String iniPageText = pageNumberText.getText().trim();
			String[] maxPageNumberOri = iniPageText.split("of");
			int maxPageNumber = Integer.valueOf(maxPageNumberOri[1].trim());
			if (maxPageNumber == minPageNum && pageLeftBtnInDistrict.getAttribute("class").contains("disabled")
			&& pageRightBtnInDistrict.getAttribute("class").contains("disabled")) {
				SimpleUtils.pass("There is only one page");
			}else {
				for (int i = 1; i <= Integer.valueOf(maxPageNumber); i++) {
					selectByVisibleText(pageNumSelector,String.valueOf(i));
					if (i <= Integer.valueOf(maxPageNumber)) {
						SimpleUtils.pass("Page " +i +" Select work well");
					}else
						SimpleUtils.fail("Page select doesn't work",true);
				}
				waitForSeconds(5);
				String firstLineText = upperfieldRows.get(0).getText();
				click(pageLeftBtnInDistrict);
				String firstLineTextAftLeft = upperfieldRows.get(0).getText();
				if (!firstLineTextAftLeft.equalsIgnoreCase(firstLineText) ) {
					SimpleUtils.pass("Left pagination button work well" );
				}else
					SimpleUtils.fail("Left pagination button work wrong",false);
				click(pageRightBtnInDistrict);
				String firstLineTextAftRight = upperfieldRows.get(0).getText();
				if (!firstLineTextAftRight.equalsIgnoreCase(firstLineTextAftLeft) ) {
					SimpleUtils.pass("Right pagination button work well");
				}else
					SimpleUtils.fail("Right pagination button work wrong",false);

			}

		}else
			SimpleUtils.fail("Page select load failed",true);

	}

	@Override
	public void verifySearchUpperFieldsFunction(String[] searchInfo) throws Exception {
		if (isElementEnabled(upperfieldsSearchInputBox,3)) {
			for (String info:searchInfo) {
				searchUpperFields(info);
			}
		}else
			SimpleUtils.fail("District search input element load failed",false);
	}

	@FindBy( css =".console-detail")
	private WebElement districtDetailsPage;
	@FindBy( css ="input[aria-label=\"Upperfield Name\"]")
	private WebElement upperfieldNameInput;
	@FindBy( css ="input[aria-label=\"Upperfield Id\"]")
	private WebElement upperfieldIdInput;
	@FindBy( css ="select[aria-label=\"Upperfield Manager\"]")
	private WebElement upperfieldManagerSelector;
	@FindBy( css ="input-field[label=\"Upperfield Manager Phone\"]")
	private WebElement upperfieldManagerPhone;
	@FindBy( css ="input-field[label=\"Upperfield Manager Email\"]")
	private WebElement upperfieldManagerEmail;
	@FindBy( css ="lg-button[label=\"Upload image\"]")
	private WebElement uploadImageBtn;
	@FindBy( css ="lg-button[label=\"Manage\"]")
	private WebElement ManagerBtnInDistrictCreationPage;
	@FindBy( css ="lg-button[label=\"Create Upperfield\"]")
	private WebElement createUpperfieldBtnInDistrictCreationPage;
	@FindBy( css ="lg-button[label=\"Cancel\"]")
	private WebElement CancelDistrictBtnInDistrictCreationPage;

	@Override
	public void addNewDistrict(String districtName, String districtId, String searchChara,int index) throws Exception {
		click(addUpperfieldsButton);
		if (upperfieldCreateLandingPageShowWell()) {
			upperfieldNameInput.sendKeys(districtName);
			upperfieldIdInput.sendKeys(districtId);
			selectByIndex(upperfieldManagerSelector,1);
			waitForSeconds(3);
			click(ManagerBtnInDistrictCreationPage);
			managerDistrictLocations(searchChara,index);
			click(createUpperfieldBtnInDistrictCreationPage);
			SimpleUtils.report("District creation done");
			waitForSeconds(10);
		}else
			SimpleUtils.fail("District landing page load failed",true);
	}

	private boolean upperfieldCreateLandingPageShowWell() {
			waitForSeconds(10);
		if (isElementEnabled(upperfieldNameInput,3)&&isElementEnabled(upperfieldIdInput,3)
		&& isElementEnabled(upperfieldManagerSelector,3) && isElementEnabled(upperfieldManagerPhone,3)
		&& isElementEnabled(upperfieldManagerEmail,3)&& isElementEnabled(cancelBtn,5)&&
		isElementEnabled(createUpperfieldBtnInDistrictCreationPage)) {
			return true;
		}
		return false;
	}
	@FindBy(css = ".lg-modal__title")
	private WebElement selectDistrictPopUpWins;
	@FindBy(css = "input[placeholder=\"Search by upperfield name\"]")
	private WebElement searchDistrictInputInSelectDistrictPopUpWins;
	private void managerDistrictLocations(String searchChara,int index) {
		if (isElementEnabled(selectALocationTitle, 5)) {
			searchInputInSelectALocation.sendKeys(searchChara);
			searchInputInSelectALocation.sendKeys(Keys.ENTER);
			waitForSeconds(5);
			if (locationRowsInSelectLocation.size() > 0) {
				for (int i = 0; i < index; i++) {
					WebElement firstRow = locationRowsInSelectLocation.get(i).findElement(By.cssSelector("input[type=\"checkbox\"]"));
					click(firstRow);
				}
				click(okBtnInSelectLocation);
			} else
				SimpleUtils.fail("Select a upperfield window load failed", true);

		}
	}
	@FindBy(css = ".modal-dialog")
	private WebElement districtIdChangePopUpWin;
	@FindBy(css = "modal[modal-title=\"Upperfield Level Change\"]")
	private WebElement upperfieldLevelChangeWin;
	@Override
	public void updateUpperfield(String upperfieldsName, String upperfieldsId,  String searchChara, int index) throws Exception {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
		String currentTime =  dfs.format(new Date()).trim();
		searchUpperFields(upperfieldsName);
		if (upperfieldRows.size() > 0) {
			List<WebElement> districtDetailsLinks = upperfieldRows.get(0).findElements(By.cssSelector("button[type='button']"));
			click(districtDetailsLinks.get(0));
			click(editUpperfieldBtn);
			selectByVisibleText(levelDropDownList,"District");

			if (isElementEnabled(upperfieldLevelChangeWin,10)) {
				click(okBtnInLocationGroupConfirmPage);
				SimpleUtils.pass("Upperfield Level Change done");
			}else
				SimpleUtils.fail("Upperfield Level Change window load failed",false);
			upperfieldNameInput.clear();
			upperfieldNameInput.sendKeys(upperfieldsName.replaceAll(":","")+"ToDistrict");
			upperfieldIdInput.clear();
			waitForSeconds(2);
			if (isElementEnabled(districtIdChangePopUpWin,3)) {
				click(okBtnInLocationGroupConfirmPage);
				upperfieldIdInput.sendKeys(upperfieldsName.replaceAll(":","")+currentTime);
			}else
				SimpleUtils.fail("Upperfield id change window not show",true);
			scrollToBottom();
			click(ManagerBtnInDistrictCreationPage);
			managerDistrictLocations(searchChara,index);
			scrollToBottom();
			click(saveBtnInUpdateLocationPage);
			waitForSeconds(20);
			if (isElementEnabled(selectDistrictPopUpWins, 5)) {
				searchDistrictInputInSelectDistrictPopUpWins.sendKeys("No touch no delete");
				searchDistrictInputInSelectDistrictPopUpWins.sendKeys(Keys.ENTER);
				waitForSeconds(5);
				if (locationRowsInSelectLocation.size() > 0) {
					for (int i = 0; i < locationRowsInSelectLocation.size(); i++) {
						WebElement firstRow = locationRowsInSelectLocation.get(i).findElement(By.cssSelector("input[type=\"radio\"]"));
						click(firstRow);
					}
					click(okBtnInSelectLocation);
				}
			} else
				SimpleUtils.report("Search location result is 0");
			SimpleUtils.pass("Upperfield update done");
		}else
			SimpleUtils.fail("No search result",true);

	}



	public ArrayList<HashMap<String, String>> getUpperfieldsInfo(String districtName) {
		ArrayList<HashMap<String,String>> upperfieldInfo = new ArrayList<>();

		if (isElementEnabled(upperfieldsSearchInputBox, 10)) {
			upperfieldsSearchInputBox.clear();
			upperfieldsSearchInputBox.sendKeys(districtName);
			upperfieldsSearchInputBox.sendKeys(Keys.ENTER);
			waitForSeconds(5);
			if (upperfieldRows.size() > 0) {

				for (WebElement upperfield : upperfieldRows) {
					HashMap<String, String> upperfieldInfoInEachRow = new HashMap<>();
					upperfieldInfoInEachRow.put("upperfieldName", upperfield.findElement(By.cssSelector("button[type='button']")).getText());
					upperfieldInfoInEachRow.put("upperfieldLevel", upperfield.findElement(By.cssSelector("td:nth-child(2) ")).getText());
					upperfieldInfoInEachRow.put("upperfieldCreator", upperfield.findElement(By.cssSelector("td:nth-child(3)")).getText());
					upperfieldInfoInEachRow.put("upperfieldStatus", upperfield.findElement(By.cssSelector("td:nth-child(4) > lg-eg-status ")).getAttribute("type"));
					upperfieldInfoInEachRow.put("numOfLocations", upperfield.findElement(By.cssSelector("td:nth-child(5)")).getText());
					upperfieldInfo.add(upperfieldInfoInEachRow);
				}


				return upperfieldInfo;
			}else
				SimpleUtils.fail(districtName + "can't been searched", true);
		}

		return null;
	}

	@Override
	public void addNewDistrictWithoutLocation(String districtName, String districtId) throws Exception {
		click(addUpperfieldsButton);
		if (upperfieldCreateLandingPageShowWell()) {
			upperfieldNameInput.sendKeys(districtName);
			upperfieldIdInput.sendKeys(districtId);
//			selectByIndex(districtManagerSelector,1);
			scrollToBottom();
			click(createUpperfieldBtnInDistrictCreationPage);

			SimpleUtils.report("District creation done  :"+districtName);
			waitForSeconds(10);
		}else
			SimpleUtils.fail("District landing page load failed",true);
	}
	@FindBy(css = "div.lg-modal")
	private WebElement enabledDisableUpperfieldModal;

	@Override
	public void disableEnableUpperfield(String upperfieldName, String action) throws Exception {
		upperfieldsSearchInputBox.clear();
		searchUpperFields(upperfieldName);
		if (upperfieldRows.size() > 0) {
			List<WebElement> upperfieldDetailsLinks = upperfieldRows.get(0).findElements(By.cssSelector("button[type='button']"));
			click(upperfieldDetailsLinks.get(0));
			click(getDriver().findElement(By.cssSelector("lg-button[label=\""+action+"\"] ")));
			if (isElementEnabled(enabledDisableUpperfieldModal,10)) {
				click(getDriver().findElement(By.cssSelector("lg-button[label=\""+action+"\"] ")));
			}else
				SimpleUtils.fail("Enable/Disabled Upperfield windows load failed",false);
			waitForSeconds(5);
			if (!getDriver().findElement(By.xpath("//div[1]/form-buttons/div[2]/lg-button[1]/button")).getText().equals(action)) {
				SimpleUtils.pass(action+" " +upperfieldName +" successfully");
			}else
				SimpleUtils.fail(action+" " +upperfieldName +" failed",true);
			click(backBtnInLocationDetailsPage);
		}else
			SimpleUtils.fail("No search result",true);
	}


	//added by Estelle to verify internal location picture
	@FindBy(css="form-section[form-title=\"Default Location Picture\"]")
	private WebElement defaultLocationForm;
	@FindBy(css="form-section[form-title=\"Enterprise Logo\"]")
	private WebElement enterpriseLogoForm;
	@Override
	public HashMap<String, String> getEnterpriseLogoAndDefaultLocationInfo() {
		HashMap<String, String> enterpriseLogoAndDefaultLocationInfo= new HashMap<>();
		if (isElementEnabled(enterPriseProfileInLocations,3)) {
			click(enterPriseProfileInLocations);
			if (isElementEnabled(defaultLocationForm,3)&&isElementEnabled(enterpriseLogoForm,3)) {
				enterpriseLogoAndDefaultLocationInfo.put("Enterprise logo",enterpriseLogoForm.findElement(By.cssSelector("ng-transclude > content-box > ng-transclude > image-input > div > ng-form > div > img")).getAttribute("src"));
				enterpriseLogoAndDefaultLocationInfo.put("Default location pircture",defaultLocationForm.findElement(By.cssSelector("ng-transclude > content-box > ng-transclude > image-input > div > ng-form > div > img")).getAttribute("src"));
			return enterpriseLogoAndDefaultLocationInfo;
			}else
				SimpleUtils.fail("Enterprise logo info and default location picture load failed",false);
		}
		return null;
	}


	//added by Estelle
	@FindBy(css = "input-field[label=\"Parent Child\"]")
	private  WebElement msRadio;
	@FindBy(css = "input-field[label=\"Peer to Peer\"]")
	private  WebElement p2pRadio;

	@FindBy(css = "lg-button[label=\"Leave this page\"]")
	private  WebElement leaveThisPage;
	@Override
	public void verifyTheFiledOfLocationSetting() throws Exception {
		if (isElementEnabled(addLocationBtn,20)) {
			clickTheElement(addLocationBtn);
			clickTheElement(locationGroupSelect);
			//			if (locationGroupSelect.getAttribute("option").contains("None") && locationGroupSelect.getAttribute("option").contains("Part of a location group")&&
//					locationGroupSelect.getAttribute("option").contains("Parent location")) {
//				SimpleUtils.pass("Location group setting field should include:None, Parent location ,Part of a location group");
				selectByVisibleText(locationGroupSelect,"Parent location");
				if (isElementEnabled(msRadio,3)&&isElementEnabled(p2pRadio,3)) {
					SimpleUtils.pass("there are two radio button:Parent child(default) ,Peer to peer after select parent location");
					selectByVisibleText(locationGroupSelect,"Part of a location group");
					if (isElementEnabled(selectParentLocation,3)) {
						SimpleUtils.pass("there is one link named select parent location after select part of a location group");

						//back to location list page
						clickTheElement(backBtnInLocationDetailsPage);
						clickTheElement(leaveThisPage);
						waitForSeconds(3);
						SimpleUtils.pass("The fields of location group show well");
					}else {
						SimpleUtils.fail("select parent location load failed",false);
					}
				} else {
					SimpleUtils.fail("MS and p2p radio button load failed",false);
				}
			}else {
				SimpleUtils.fail("Location group setting field show wrong",false);
			}
		}

	@FindBy(css = "lg-dashboard-card[title=\"Dynamic Groups\"]")
	private  WebElement dynamicGroupCard;
	@FindBy(css = "lg-global-dynamic-group-table[dynamic-groups=\"workForceSharingDg\"]")
	private  WebElement workForceSharingDg;
	@FindBy(css = "lg-global-dynamic-group-table[dynamic-groups=\"clockinDg\"]")
	private  WebElement clockInDg;
	@FindBy(css = "lg-button[label=\"Test\"]")
	private  WebElement testBtn;
	@FindBy(css = "input[aria-label=\"Group Name\"]")
	private  WebElement groupNameInput;
	@FindBy(css = "input-field[value=\"$ctrl.dynamicGroup.description\"] >ng-form>input")
	private  WebElement groupDescriptionInput;
	@FindBy(css = "select.ng-pristine.ng-untouched.ng-valid")
	private  WebElement criteriaSelect;
	@FindBy(css = "lg-button[label=\"Add More\"]")
	private  WebElement addMoreBtn;

	@FindBy(css = "i.deleteRule")
	private  List<WebElement> deleteRuleIcon;
	@FindBy(css = "lg-button[icon=\"'img/legion/add.png'\"]")
	private  List<WebElement> addDynamicGroupBtn;
	@FindBy(css = "input[placeholder=\"You can search by name and description\"]")
	private  List<WebElement> dgSearchInput;
	@FindBy(css = "lg-global-dynamic-group-table[dynamic-groups=\"clockinDg\"] > lg-paged-search-new > div > ng-transclude > table > tbody > tr:nth-child(2) > td.tr > div > lg-button:nth-child(1) > button")
	private  List<WebElement> editDGIconInClockIn;
	@FindBy(css = "lg-global-dynamic-group-table[dynamic-groups=\"workForceSharingDg\"] > lg-paged-search-new > div > ng-transclude > table > tbody > tr:nth-child(2) > td.tr > div > lg-button:nth-child(1)")
	private  List<WebElement> editDGIconInWFS;
	@FindBy(css = "lg-global-dynamic-group-table[dynamic-groups=\"workForceSharingDg\"] > lg-paged-search-new > div > ng-transclude > table > tbody > tr.ng-scope > td.tr > div > lg-button:nth-child(2)")
	private  List<WebElement> deleteDGIconInWFS;
	@FindBy(css = "lg-global-dynamic-group-table[dynamic-groups=\"clockinDg\"] > lg-paged-search-new > div > ng-transclude > table > tbody > tr.ng-scope > td.tr > div > lg-button:nth-child(2)")
	private  List<WebElement> deleteDGIconInClockIn;

	@FindBy(css = "tr[ng-repeat=\"group in filterdynamicGroups\"]")
	private  List<WebElement> groupRows;
	@FindBy(css = "lg-picker-input[value=\"group.values\"]")
	private  WebElement criteriaValue;
	@FindBy(css = "input[placeholder=\"Search \"")
	private  WebElement searchBoxInCriteriaValue;
	@FindBy(css = "input-field[type=\"checkbox\"]")
	private  List<WebElement> checkboxInCriteriaValue;
	@FindBy(css = "modal[modal-title=\"Remove Dynamic Group\"]")
	private  WebElement removeDGPopup;
	@FindBy(css = "ng-transclude.lg-modal__body")
	private  WebElement removeDGPopupDes;
	@FindBy(css = "lg-button[label=\"Remove\"]")
	private  WebElement removeBtnInRemovDGPopup;
	@FindBy(css = "div.mappingLocation.mt-20.ng-scope > span")
	private  WebElement testBtnInfo;





	@Override
	public void iCanSeeDynamicGroupItemInLocationsTab() {
		if (isElementEnabled(dynamicGroupCard,5)) {
			SimpleUtils.pass("Dynamic group card is shown");
			String contextInfo = dynamicGroupCard.getText();
			if (contextInfo.contains("Dynamic Group") && contextInfo.contains("Dynamic Group Configuration") &&
				contextInfo.contains("Work Force Sharing Group") &&contextInfo.contains("Dynamic Group") ) {
				SimpleUtils.pass("Title and description show well");
			}else
				SimpleUtils.fail("Title and description are wrong",false);
		}else
			SimpleUtils.fail("There is no dynamic group card",false);
	}

	@Override
	public void goToDynamicGroup() {
		if (isElementEnabled(dynamicGroupCard,5)) {
			click(dynamicGroupCard);
			waitForSeconds(15);
			if (isElementEnabled(workForceSharingDg,5)) {
				SimpleUtils.pass("Can go to dynamic group page successfully");
			}else
				SimpleUtils.fail("Go to dynamic group page failed",false);
		}
	}
	@FindBy(css = "textarea[id=\"omjob\"]")
	private WebElement formulaInputBox;
	@Override
	public String addWorkforceSharingDGWithOneCriteria(String groupName, String description, String criteria) throws Exception {
		if (areListElementVisible(addDynamicGroupBtn)) {
			click(addDynamicGroupBtn.get(0));
			if (isManagerDGpopShowWell()) {
				groupNameInput.sendKeys(groupName);
				groupDescriptionInput.sendKeys(description);
				selectByVisibleText(criteriaSelect,criteria);
				if (!isElementEnabled(formulaInputBox)) {
					click(criteriaValue);
					click(checkboxInCriteriaValue.get(0));
					click(testBtn);
					String testInfo = testBtnInfo.getText().trim();
					click(okBtnInSelectLocation);
					waitForSeconds(3);
					searchWFSDynamicGroup(groupName);
					if (groupRows.size()>0) {
						SimpleUtils.pass("Dynamic group create successfully");
					}else
						SimpleUtils.fail("Dynamic group create failed",false);
					return testInfo;
				}else
					formulaInputBox.sendKeys("Parent(1)");
					click(okBtnInSelectLocation);
					waitForSeconds(3);


			}else
				SimpleUtils.fail("Manager Dynamic Group win load failed",false);
		}else
			SimpleUtils.fail("Global dynamic group page load failed",false);

		 return null;
	}

	@Override
	public void iCanDeleteExistingWFSDG() {
		waitForSeconds(20);
		if (groupRows.size()>0) {
			if (areListElementVisible(deleteDGIconInWFS,30)) {
				for (WebElement dg: deleteDGIconInWFS) {
					waitForSeconds(10);
					click(dg);
					if (isRemoveDynamicGroupPopUpShowing()) {
						waitForSeconds(3);
						click(removeBtnInRemovDGPopup);
					}else
						SimpleUtils.fail("loRemove dynamic group page load failed ",false);
				}

			}else
				SimpleUtils.report("There is not dynamic group yet");
		}else
			SimpleUtils.report("There is no groups which selected");


	}
	@Override
	public void iCanDeleteExistingClockInDG() {
		waitForSeconds(20);
		if (groupRows.size()>0) {
			if (areListElementVisible(deleteDGIconInClockIn,30)) {
				for (WebElement dg: deleteDGIconInClockIn) {
					waitForSeconds(10);
					click(dg);
					if (isRemoveDynamicGroupPopUpShowing()) {
						waitForSeconds(3);
						click(removeBtnInRemovDGPopup);
					}else
						SimpleUtils.fail("loRemove dynamic group page load failed ",false);
				}

			}else
				SimpleUtils.report("There is not dynamic group yet");
		}else
			SimpleUtils.report("There is no groups which selected");


	}

	@Override
	public String updateWFSDynamicGroup(String groupName, String criteriaUpdate) throws Exception {
		waitForSeconds(3);
		click(editDGIconInWFS.get(0));
		if (isManagerDGpopShowWell()) {
			groupNameInput.clear();
			groupNameInput.sendKeys(groupName+"Update");
			selectByVisibleText(criteriaSelect,criteriaUpdate);
			click(criteriaValue);
			click(checkboxInCriteriaValue.get(0));
			click(testBtn);
			String testInfo = testBtnInfo.getText().trim();
			click(okBtnInSelectLocation);
			waitForSeconds(3);
			searchWFSDynamicGroup(groupName);
			if (groupRows.size()>0) {
				SimpleUtils.pass("Dynamic group create successfully");
			}else
				SimpleUtils.fail("Dynamic group create failed",false);
			return testInfo;
		}else
			SimpleUtils.fail("Manager Dynamic Group win load failed",false);
		return null;
	}

	private boolean isRemoveDynamicGroupPopUpShowing() {
		if (isElementEnabled(removeDGPopup,5) && removeDGPopupDes.getText().contains("Are you sure you want to remove this dynamic group?")
		&& isElementEnabled(removeBtnInRemovDGPopup,5)) {
			SimpleUtils.pass("Remove dynamic group page show well");
			return true;
		}
		return false;
	}

	@FindBy(css = "modal[modal-title=\"Manage Dynamic Group\"]>div")
	private WebElement managerDGpop;
	private boolean isManagerDGpopShowWell() {
		if (isElementEnabled(managerDGpop,5)&& isElementEnabled(groupNameInput,5)&&
				isElementEnabled(groupDescriptionInput,5)&&isElementEnabled(criteriaSelect,5)
		&& isElementEnabled(testBtn,5) && isElementEnabled(addMoreBtn,5)) {
			SimpleUtils.pass("Manager Dynamic Group win show well");
			return true;
		}else
			return false;
	}
	@Override
	public void searchClockInDynamicGroup(String searchInputText) throws Exception {
		scrollToBottom();
		String[] searchLocationCha = searchInputText.split(",");
		if (areListElementVisible(dgSearchInput, 10) ) {
			for (int i = 0; i < searchLocationCha.length; i++) {
				dgSearchInput.get(1).clear();
				dgSearchInput.get(1).sendKeys(searchLocationCha[0]);
//				dgSearchInput.get(0).sendKeys(Keys.ENTER);
				waitForSeconds(3);
				if (groupRows.size()>0) {
					SimpleUtils.pass("Dynamic group: " + groupRows.size() + " group(s) found  ");
					break;
				} else {
					dgSearchInput.clear();
				}
			}

		} else {
			SimpleUtils.fail("Search input is not clickable", true);
		}

	}

	@Override
	public String addClockInDGWithOneCriteria(String groupName, String description, String criteria) throws Exception {

		if (areListElementVisible(addDynamicGroupBtn)) {
			click(addDynamicGroupBtn.get(1));
			if (isManagerDGpopShowWell()) {
				groupNameInput.sendKeys(groupName);
				groupDescriptionInput.sendKeys(description);
				selectByVisibleText(criteriaSelect,criteria);
				click(criteriaValue);
				click(checkboxInCriteriaValue.get(0));
				click(testBtn);
				String testInfo = testBtnInfo.getText().trim();
				click(okBtnInSelectLocation);
				waitForSeconds(3);
				searchClockInDynamicGroup(groupName);
				if (groupRows.size()>0) {
					SimpleUtils.pass("Dynamic group create successfully");
				}else
					SimpleUtils.fail("Dynamic group create failed",false);
				return testInfo;
			}else
				SimpleUtils.fail("Manager Dynamic Group win load failed",false);
		}else
			SimpleUtils.fail("Global dynamic group page load failed",false);

		return null;
	}

	@Override
	public String updateClockInDynamicGroup(String groupNameForCloIn, String criteriaUpdate) throws Exception {
		waitForSeconds(3);
		click(editDGIconInClockIn.get(0));
		if (isManagerDGpopShowWell()) {
			groupNameInput.clear();
			groupNameInput.sendKeys(groupNameForCloIn+"Update");
			selectByVisibleText(criteriaSelect,criteriaUpdate);
			click(criteriaValue);
			click(checkboxInCriteriaValue.get(0));
			click(testBtn);
			String testInfo = testBtnInfo.getText().trim();
			click(okBtnInSelectLocation);
			waitForSeconds(3);
			searchClockInDynamicGroup(groupNameForCloIn);
			if (groupRows.size()>0) {
				SimpleUtils.pass("Dynamic group create successfully");
			}else
				SimpleUtils.fail("Dynamic group create failed",false);
			return testInfo;
		}else
			SimpleUtils.fail("Manager Dynamic Group win load failed",false);
		return null;
	}

	// elements on global configuration page
	@FindBy(css="lg-button[label=\"Edit\"]")
	private WebElement editOnGlobalConfigPage;
	@FindBy(css="form-section[form-title=\"Day Parts\"]")
	private WebElement dayPartsSection;
	@FindBy(css="div.daypart-container tbody tr")
	private List<WebElement> dayPartsList;

	public void goToGlobalConfigurationInLocations() throws Exception{
		waitForSeconds(10);
		if (isElementLoaded(globalConfigurationInLocations, 20)) {
			click(globalConfigurationInLocations);
			waitForSeconds(10);
			if (isElementEnabled(editOnGlobalConfigPage, 20)) {
				SimpleUtils.pass("global configuration page load successfully");
			} else
				SimpleUtils.fail("global configuration page load failed", false);
		} else
			SimpleUtils.fail("locations tab load failed in location overview page", false);
	}

	@Override
	public void searchWFSDynamicGroup(String searchText) {
		String[] searchGroupText = searchText.split(",");
		if (areListElementVisible(dgSearchInput, 10) ) {
			for (int i = 0; i < searchGroupText.length; i++) {
				dgSearchInput.clear();
				dgSearchInput.get(0).sendKeys(searchGroupText[0]);
				dgSearchInput.get(0).sendKeys(Keys.ENTER);
				waitForSeconds(3);
				if (groupRows.size()>0) {
					SimpleUtils.pass("Dynamic group: " + groupRows.size() + " group(s) found  ");
					break;
				} else {
					dgSearchInput.clear();
				}
			}

		} else {
			SimpleUtils.fail("Search input is not clickable", true);
		}
	}
	public List<String> getAllDayPartsFromGlobalConfiguration() throws Exception{
		List<String> dayPartsNameList = new ArrayList<String>();
		if(dayPartsList.size()!=0){
			for(WebElement dayParts:dayPartsList){
				String dayPartsName = dayParts.findElement(By.cssSelector("td")).getText().trim();
				if(dayPartsName!=null){
					dayPartsNameList.add(dayPartsName);
				}
			}
		}else{
			SimpleUtils.pass("There is no day parts for this enterprise");
		}
        return dayPartsNameList;
	}
	@FindBy (css = " lg-global-dynamic-group-table[dynamic-groups=\"clockinDg\"] > lg-paged-search-new > div > ng-transclude > table > tbody > tr.ng-scope")
	private  List<WebElement> clockInGroups;
	@Override
	public List<String> getClockInGroupFromGlobalConfig() {
		waitForSeconds(15);
		List<String> clockInGroup = new ArrayList<>();
		if (clockInGroups.size()>0) {
			for(WebElement clockIn:clockInGroups){
				String clockInName = clockIn.findElement(By.cssSelector("td")).getText().trim();
				if(clockInName!=""){
					clockInGroup.add(clockInName);
				}
			}
			return clockInGroup;
		}else
			return null;
	}

	@FindBy(css = "lg-input-error[ng-if=\"!$ctrl.hideInputErrorHint\"]>div>span>i")
	private WebElement groupNameRequired;

	@Override
	public void verifyCreateExistingDGAndGroupNameIsNull(String s) throws Exception {
		if (areListElementVisible(addDynamicGroupBtn)) {
			click(addDynamicGroupBtn.get(0));
			if (isManagerDGpopShowWell()) {
				//verify group name is null
				groupNameInput.sendKeys(s);
				groupNameInput.clear();
				if (isElementEnabled(groupNameRequired,5)) {
					SimpleUtils.pass("Group Name is required show well if the name is null");
				}
				//verify create existing group
				groupNameInput.sendKeys(s);
				click(okBtnInSelectLocation);
				waitForSeconds(5);
				click(cancelBtn);
				searchWFSDynamicGroup(s);
				int a = wfsGroups.size();
				if (wfsGroups.size()>1) {
					SimpleUtils.fail("Should not create existing group",false);
				}else
					SimpleUtils.pass("Can not create existing Dynamic group");
				//verify existing criteria ,but group name is not same
			}else
				SimpleUtils.fail("Manager Dynamic Group win load failed",false);
		}else
			SimpleUtils.fail("Global dynamic group page load failed",false);

	}
	@FindBy (css = " lg-global-dynamic-group-table[dynamic-groups=\"workForceSharingDg\"] > lg-paged-search-new > div > ng-transclude > table > tbody > tr.ng-scope")
	private  List<WebElement> wfsGroups;
	@Override
	public List<String> getWFSGroupFromGlobalConfig() {
		List<String> wfsGroup = new ArrayList<>();
		if (wfsGroups.size()>0) {
			for(WebElement clockIn:wfsGroups){
				String wfsGroupName = clockIn.findElement(By.cssSelector("td")).getText().trim();
				if(wfsGroupName!=""){
					wfsGroup.add(wfsGroupName);
				}
			}
			return wfsGroup;
		}else
			return null;
	}
	@FindBy(css = "select[aria-label=\"Level\"]")
	private  WebElement levelDropDownList;
	@Override
	public void addNewUpperfieldsWithoutParentAndChild(String upperfieldsName, String upperfieldsId, String searchChara, int index, ArrayList<HashMap<String, String>> organizationHierarchyInfo) throws Exception {
		ArrayList<String> levelInfo = new ArrayList<>();
			for (int i = 0; i <organizationHierarchyInfo.size() ; i++) {
				levelInfo.add(organizationHierarchyInfo.get(i).get("Display Name"));
			}
		for (int i = 1; i <levelInfo.size() ; i++) {
			click(addUpperfieldsButton);
			if (upperfieldCreateLandingPageShowWell()) {
				selectByVisibleText(levelDropDownList,levelInfo.get(i));
				upperfieldNameInput.sendKeys(levelInfo.get(i)+upperfieldsName);
				upperfieldIdInput.sendKeys(levelInfo.get(i).replace(" ","")+upperfieldsId);
				selectByIndex(upperfieldManagerSelector,1);
				waitForSeconds(3);
//				click(ManagerBtnInDistrictCreationPage);
//				managerDistrictLocations(searchChara,index);
				scrollToBottom();
				click(createUpperfieldBtnInDistrictCreationPage);
				SimpleUtils.report("Upperfield creation done");
				waitForSeconds(20);
				searchUpperFields(levelInfo.get(i)+upperfieldsName);
			}else
				SimpleUtils.fail("Upperfield landing page load failed",true);
		}

	}
	@FindBy(css ="tr[ng-if=\"!hierarchy.isEditing\"]")
	private List<WebElement> hierarchyRows;
	@Override
	public ArrayList<HashMap<String, String>> getOrganizationHierarchyInfo() {

		ArrayList<HashMap<String,String>> hierarchyInfo = new ArrayList<>();

		if (areListElementVisible(hierarchyRows, 10)) {
			if (hierarchyRows.size() > 0) {
				for (WebElement row : hierarchyRows) {
					HashMap<String, String> hierarchyInfoEachRow = new HashMap<>();
					hierarchyInfoEachRow.put("Level", row.findElement(By.cssSelector("td:nth-child(1)")).getText());
					hierarchyInfoEachRow.put("Level Name", row.findElement(By.cssSelector("td:nth-child(2)")).getText());
					hierarchyInfoEachRow.put("Display Name", row.findElement(By.cssSelector("td:nth-child(3)")).getText());
					hierarchyInfoEachRow.put("Enable Upperfield View", row.findElement(By.cssSelector("td:nth-child(4)>input-field>ng-form")).getAttribute("class"));

					hierarchyInfo.add(hierarchyInfoEachRow);
				}
				return hierarchyInfo;
			}else
				SimpleUtils.fail("Default Organization Hierarchy info is missing", true);
		}else
			SimpleUtils.fail("Organization Hierarchy load failed in global configuration page",false);

		return null;
	}

	@Override
	public void goBackToLocationsTab() {
		if (isElementEnabled(backBtnInLocationDetailsPage,3)) {
			click(backBtnInLocationDetailsPage);
			scrollToBottom();
			if (isElementEnabled(upperfieldsInLocations,3)) {
				SimpleUtils.pass("Back to Locations Tab successfully");
			}else
				SimpleUtils.fail("Failed to back to Locations Tab",false);
		}else
			SimpleUtils.fail("Back button in add upperfields page load failed",false);
	}

	@Override
	public void verifyBackBtnInCreateNewUpperfieldPage() {
		if (isElementEnabled(addUpperfieldsButton,5)) {
			click(addUpperfieldsButton);
			if (upperfieldCreateLandingPageShowWell()) {
				click(backBtnInLocationDetailsPage);
				if (isElementEnabled(addUpperfieldsButton,5)) {
					SimpleUtils.pass("Back button on the create new Upperfield page work well");
				}else
					SimpleUtils.fail("Back to upperfield landing page faield",false);
			}

		}else
			SimpleUtils.fail("Upperfield landing page load failed",false);
	}

	@Override
	public void verifyCancelBtnInCreateNewUpperfieldPage() {

	}

	@Override
	public void addNewUpperfieldsWithRandomLevel(String upperfieldsName, String upperfieldsId, String searchChara, int index) throws Exception {
		click(addUpperfieldsButton);
		if (upperfieldCreateLandingPageShowWell()) {
			selectByIndex(levelDropDownList,1);
			upperfieldNameInput.sendKeys(upperfieldsName);
			upperfieldIdInput.sendKeys(upperfieldsId);
			selectByIndex(upperfieldManagerSelector,1);
			waitForSeconds(3);
//				click(ManagerBtnInDistrictCreationPage);
//				managerDistrictLocations(searchChara,index);
			scrollToBottom();
			click(createUpperfieldBtnInDistrictCreationPage);
			SimpleUtils.report("Upperfield creation done");
		}else
			SimpleUtils.fail("Upperfield landing page load failed",true);
	}


//	@FindBy(css = "")
//	private WebElement T;
//	@Override
//	public ArrayList<HashMap<String, String>> getWFSGroupForm() {
//		ArrayList<> wfsGroupInfo = new ArrayList();
//		HashMap<String,String> eachLineGroupInfo = new HashMap<>();
//		if (areListElementVisible(wfsGroups,5)&& wfsGroups.size()!=0) {
//			for (WebElement eachRow: wfsGroups) {
//				eachLineGroupInfo.put("groupName", eachRow.findElement(By.cssSelector("td:nth-child(4) ")));
//				String groupName = each.findElement(By.cssSelector("td")).getText().trim();
//				String
//		}
//		}
//	}
	//add by Fiona for Organization Hierarchy
	@FindBy(css="form-section[form-title=\"Organization Hierarchy\"]")
	private WebElement organizationHierarchySection;
	@FindBy(css="div.hierarchy-container tbody tr")
	private List<WebElement> hierarchyList;
	@FindBy(css="div.hierarchy-header.dif div")
	private WebElement addHierarchyBTN;


	@Override
	public void verifyDefaultOrganizationHierarchy() throws Exception{
		List<String> levelNameList = new ArrayList<String>(){{
			add("Location");
			add("District");
		}};
		List<String> hierarchyLevelNameList = new ArrayList<>();
		if(isElementEnabled(organizationHierarchySection)){
			SimpleUtils.pass("The organization hierarchy section show correctly.");
			clickTheElement(editOnGlobalConfigPage);
			if(hierarchyList.size() != 0){
				if(hierarchyList.size() == 1){
					clickTheElement(addHierarchyBTN);
					waitForSeconds(2);
					for(WebElement hierarchy:hierarchyList){
						String hierarchyLevelName = hierarchy.findElement(By.cssSelector("td:nth-child(2)")).getText().trim();
						hierarchyLevelNameList.add(hierarchyLevelName);
					}
				}else {
					for(int i=0;i<=1;i++){
						String hierarchyLevelName = hierarchyList.get(i).findElement(By.cssSelector("td:nth-child(2)")).getText().trim();
						hierarchyLevelNameList.add(hierarchyLevelName);
					}
				}
			}
		}else {
			SimpleUtils.fail("The organization hierarchy section Can NOT show correctly.",false);
		}

		if(ListUtils.isEqualList(levelNameList,hierarchyLevelNameList)){
			SimpleUtils.pass("The hierarchy level name is correct.");
		}else{
			SimpleUtils.fail("The hierarchy level name is NOT correct.",false);
		}

	}







}

