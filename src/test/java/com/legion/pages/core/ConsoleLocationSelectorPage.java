package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;
import static com.legion.utils.MyThreadLocal.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.legion.api.ApiList;
import com.legion.utils.JsonUtil;
import com.legion.utils.ProxyUtils;
import org.apache.commons.compress.utils.SeekableInMemoryByteChannel;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.legion.pages.BasePage;
import com.legion.pages.LocationSelectorPage;
import com.legion.utils.SimpleUtils;

public class ConsoleLocationSelectorPage extends BasePage implements LocationSelectorPage {

    @FindBy(css = "lg-select[search-hint='Search Location'] div.input-faked")
    private WebElement locationSelectorButton;

    @FindBy(css = "div.console-navigation-item")
    private List<WebElement> consoleMenuItems;

    @FindBy(css = "div.console-navigation-item.active")
    private WebElement activeConsoleMenuItem;

    @FindBy(css = "[search-hint=\"Search Location\"] .lg-search-options")
    private WebElement locationDropDownButton;

    @FindBy(className = "location-selector-dropdown-menu-items")
    private List<WebElement> locationDropDownItems;

    @FindBy(css = "div.lg-search-options__option")
    private List<WebElement> availableLocationCardsName;

    @FindBy(className = "div.lg-new-location-chooser__highlight > lg-select > div > lg-picker-input > div > input-field > ng-form > div")
    private WebElement dashboardSelectedLocationText;

    @FindBy (className = "location-selection-action-cancel")
    private WebElement dashboardLocationsPopupCancelButton;

    @FindBy (css = "div.console-navigation-item-label.Dashboard")
    private WebElement dashboardConsoleName;

    @FindBy (css = ".lg-new-location-chooser__highlight .lg-search-options__option-wrapper--selected")
    private WebElement selectedLocationCardsName;

    @FindBy (css = "input[placeholder='Search Location']")
    private WebElement searchTextbox;

    @FindBy (css = "div.lg-new-location-chooser__highlight div.lg-search-options")
    private WebElement locationDropDownList;

    @FindBy (css = "lg-search-options[search-hint='Search Location'] div.lg-search-options__scroller")
    private WebElement locationItems;

    @FindBy (css = "div.lg-location-chooser__highlight div.lg-search-options__option")
    private  List<WebElement> detailLocations;

    @FindBy (css = ".lg-new-location-chooser__highlight [placeholder=\"Select...\"] .input-faked")
    private WebElement changeLocationButton;

    @FindBy(css = "[search-hint='Search District'] div>input-field div.input-faked")
    private WebElement districtSelectorButton;
    @FindBy(css = "[search-hint=\"Search District\"] div.lg-search-options")
    private WebElement districtDropDownButton;
    @FindBy(css = "lg-search[placeholder=\"Search District\"] input")
    private WebElement searchDistrictInput;
    @FindBy(className = "lg-search-icon")
    private WebElement searchIcon;
    @FindBy(css="lg-select[search-hint=\"Search Location\"]")
    private WebElement locationButton;
    @FindBy(css="[class=\"lg-search-options\"]")
    private List<WebElement> districtAndLocationDropDownList;

    String dashboardConsoleMenuText = "Dashboard";
    private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");

    public enum typeOfUpperFields {
        BusinessUnit("Business Unit"),
        Region("Region"),
        District("District");
        private final String value;

        typeOfUpperFields(final String name) {
            value = name;
        }
    }

    
    public ConsoleLocationSelectorPage(){
        PageFactory.initElements(getDriver(), this);
    }
    
    @Override
    public Boolean isChangeLocationButtonLoaded() throws Exception
    {
        if(isElementLoaded(locationSelectorButton,20)) {
            return true;
        }
        return false;
    }

    @Override
    public void changeLocation(String locationName)
    {
        // Avoid "New Feature Enhancement" pops up
        waitForSeconds(6);
        //getDriver().navigate().refresh();
        //waitForSeconds(2);
        try {
            Boolean isLocationMatched = false;
            if (isElementLoaded(activeConsoleMenuItem, 10)) {
                activeConsoleName = activeConsoleMenuItem.getText();
            }
            setScreenshotConsoleName(activeConsoleName);
            if (activeConsoleMenuItem.getText().contains(dashboardConsoleMenuText)) {
                if (isChangeLocationButtonLoaded()) {
                    if (isLocationSelected(locationName)) {
                        SimpleUtils.pass("Given Location '" + locationName + "' already selected!");
                    } else {
                        if (isElementLoaded(locationSelectorButton, 10)){
                            click(locationSelectorButton);
                        }
                        List<WebElement> locationItems = new ArrayList<>();
                        if (areListElementVisible(districtAndLocationDropDownList, 5) && districtAndLocationDropDownList.size() > 0){
                            locationItems = districtAndLocationDropDownList.get(districtAndLocationDropDownList.size() - 1).findElements(By.cssSelector("div.lg-search-options__option"));
                        }
                        if (areListElementVisible(locationItems, 10) || isElementLoaded(locationDropDownButton)) {
                            if (locationItems.size() > 0) {
                                for (WebElement locationItem : locationItems) {
                                    if (locationItem.getText().contains(locationName)) {
                                        isLocationMatched = true;
                                        click(locationItem);
                                        SimpleUtils.pass("Location changed successfully to '" + locationName + "'");
                                        break;
                                    }
                                }
                                if (!isLocationMatched) {
                                    //updated by Estelle because the default location dropdown list show more than 50 location ,it's not efficient for navigation latest logic
                                    searchLocationAndSelect(locationName);
                                    waitForSeconds(10);
//                                    availableLocationCardsName = getDriver().findElements(By.cssSelector("div.lg-search-options__option"));
                                    locationItems = districtAndLocationDropDownList.get(districtAndLocationDropDownList.size() - 1).findElements(By.cssSelector("div.lg-search-options__option"));
                                    if (locationItems.size() > 0) {
                                        for (WebElement locationItem : locationItems) {
                                            if (locationItem.getText().contains(locationName)) {
                                                isLocationMatched = true;
                                                click(locationItem);
                                                SimpleUtils.pass("Location changed successfully to '" + locationName + "'");
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (!isLocationMatched) {
                                    SimpleUtils.fail("Location does not match with '" + locationName + "'", false);
                                }
                            }else
                                SimpleUtils.report("No mapping data for this location,maybe it's disabled or child location for Master Slave ");
                        }
                    }
                }
            } else {
                WebElement dashboardConsoleMenu = SimpleUtils.getSubTabElement(consoleMenuItems, dashboardConsoleMenuText);
                if (isElementLoaded(dashboardConsoleMenu)) {
                    click(dashboardConsoleMenu);
                    changeLocation(locationName);
                }
            }
        }
        catch(Exception e) {
            SimpleUtils.fail("Unable to change location! Get Exception: " + e.toString(), false);
        }

    }

    @Override
    public void selectLocationByIndex(int index) throws Exception {
        waitForSeconds(2);
        try {
            Boolean isLocationMatched = false;
            activeConsoleName = activeConsoleMenuItem.getText();
            setScreenshotConsoleName(activeConsoleName);
            if (activeConsoleMenuItem.getText().contains(dashboardConsoleMenuText)) {
                        if (isElementLoaded(locationSelectorButton, 10)){
                            click(locationSelectorButton);
                        }
                        List<WebElement> locationItems = new ArrayList<>();
                        if (areListElementVisible(districtAndLocationDropDownList, 5) && districtAndLocationDropDownList.size() == 2){
                            locationItems = districtAndLocationDropDownList.get(1).findElements(By.cssSelector("div.lg-search-options__option"));
                        }
                        if (areListElementVisible(locationItems, 10) || isElementLoaded(locationDropDownButton)) {
                            if (locationItems.size() > 0) {
                                        click(locationItems.get(index));
                                        SimpleUtils.pass("Location changed successfully to '" + locationButton.getText()+ "'");
                                } else
                                SimpleUtils.report("There is no location for this district");
                        }
                 }
            } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //added by estelle to search location if the location is not in recent list
    @FindBy(css = "input[placeholder=\"Search Location\"]")
    private WebElement locationSearchInput;
    private void searchLocationAndSelect(String locationName) throws Exception {
        if (isElementLoaded(locationSearchInput,5)) {
            locationSearchInput.sendKeys(locationName);
            waitForSeconds(30);
            locationSearchInput.sendKeys(Keys.ENTER);
        }else
           SimpleUtils.fail("Location search input filed load failed",true);
    }

    @Override
    public Boolean isLocationSelected(String locationName)
    {
    	try {
    		if(isChangeLocationButtonLoaded()) {
                if(locationSelectorButton.getText().contains(locationName)) {
                    return true;
                }
            }
    	}
    	catch(Exception e) {
    		SimpleUtils.fail("Change Location Button not loaded!", true);
    	}

        return false;
    }

    public String getCurrentUserLocation() throws Exception
    {
    	String selectedLocation = "";
    	if(isElementLoaded(dashboardSelectedLocationText)) {
    		selectedLocation = dashboardSelectedLocationText.getText();
    	}
    	else {
        	SimpleUtils.fail("Active Location not appear on Dashboard!", false);
    	}
    	return selectedLocation;
    }

    //added by Gunjan
//    @FindBy(css = "lg-select[search-hint='Search District'] div.input-faked")
//    private WebElement districtSelectorButton;
//    @FindBy(css = "[search-hint=\"Search District\"] div.lg-search-options")
//    private WebElement districtDropDownButton;
    @Override
    public Boolean isChangeDistrictButtonLoaded() throws Exception
    {
        if(isElementLoaded(districtSelectorButton,10)) {
            return true;
        }
        return false;
    }

    @Override
    public void verifyTheDisplayLocationWithSelectedLocationConsistent() throws Exception {
        Boolean isConsistent = false;
        String displayLocation = changeLocationButton.getText();
        click(changeLocationButton);
        WebElement detailLocation = selectedLocationCardsName.findElement(By.className("ng-binding"));
        String detailLocationName = detailLocation.getText();
        if (detailLocationName.contains(displayLocation)) {
            isConsistent = true;
        }
        if (isConsistent){
            SimpleUtils.pass("Display location is consistent with the selected location");
        }
        else
        {
            SimpleUtils.fail("Display location is not consistent with the selected location", true);
        }
    }

    @Override
    public void verifyClickChangeLocationButton() throws Exception {
        if (isElementLoaded(changeLocationButton, 10)){
            click(changeLocationButton);
            if (isElementLoaded(locationDropDownList, 10)){
                SimpleUtils.pass("The layout shows!");
                if (isElementLoaded(searchTextbox, 5) && isElementLoaded(locationItems, 5)){
                    SimpleUtils.pass("List of locations and search textbox show.");
                }
                else{
                    SimpleUtils.fail("List of locations and search textbox don't show.", true);
                }
            }
            else{
                SimpleUtils.fail("The layout doesn't show!", true);
            }
        }
    }

    @Override
    public void verifyTheContentOfDetailLocations() throws Exception {
        verifyClickChangeLocationButton();
        if(detailLocations.size() > 0){
            for(WebElement detailLocation : detailLocations)
            {
                String locationName = detailLocation.getText();
                if(locationName != null && !locationName.isEmpty()){
                    SimpleUtils.pass("Location: " + locationName + " is displayed!");
                }
                else{
                    SimpleUtils.fail("Got the empty Location Name", true);
                }
            }
        }
    }

    @Override
    public void verifyTheFunctionOfSearchTextBox(List<String> testStrings) throws Exception {
        try {
            if(isChangeLocationButtonLoaded()) {
                verifyClickChangeLocationButton();
                click(searchTextbox);
                if (testStrings.size() > 0) {
                    for (String testString : testStrings) {
                        searchTextbox.sendKeys(testString);
                        waitForSeconds(4);
                        if(detailLocations.size() > 0) {
                            for (WebElement detailLocation : detailLocations) {
                                String locationName = detailLocation.getText();
                                if (locationName.toLowerCase().contains(testString.toLowerCase())) {
                                    SimpleUtils.pass("Verified " + locationName + " contains test string: " + testString);
                                } else {
                                    SimpleUtils.fail("Verify failed, " + locationName + " doesn't contain test string: " + testString, true);
                                }
                            }
                        }
                        searchTextbox.clear();
                    }
                } else {
                    SimpleUtils.fail("Test string is empty!", true);
                }
            }
            else{
                SimpleUtils.fail("Change Location Button does't Load Successfully!", true);
            }
        }
        catch(Exception e){
            SimpleUtils.fail("Verify the function of Search TextBox failed!", true);
        }
    }

    @Override
    public Boolean isDistrictSelected(String districtName)
    {
        try {
            if(isChangeDistrictButtonLoaded()) {
                if(districtSelectorButton.getText().contains(districtName)) {
                    return true;
                }
            }
        }
        catch(Exception e) {
            SimpleUtils.fail("Change District Button not loaded!", true);
        }
        return false;
    }

    @FindBy(css = "[label=\"Refresh\"]")
    private WebElement refreshButton;

    @Override
    public void changeDistrict(String districtName) {
        waitForSeconds(4);
        try {
            Boolean isDistrictMatched = false;
            if (isElementLoaded(activeConsoleMenuItem, 10)) {
                activeConsoleName = activeConsoleMenuItem.getText();
            }
            setScreenshotConsoleName(activeConsoleName);
            if (activeConsoleMenuItem.getText().contains(dashboardConsoleMenuText)) {
                if (isChangeDistrictButtonLoaded()) {
                    if (isDistrictSelected(districtName)) {
                        SimpleUtils.pass("Given District '" + districtName + "' already selected!");
                    } else {
                        if(isElementLoaded(districtSelectorButton, 10)){
                            click(districtSelectorButton);
                        }
                        if (isElementLoaded(districtDropDownButton, 5)) {
                            availableLocationCardsName = getDriver().findElements(By.cssSelector("div.lg-search-options__option"));
                            if (availableLocationCardsName.size() != 0) {
                                for (WebElement locationCardName : availableLocationCardsName) {
                                    if (locationCardName.getText().contains(districtName)) {
                                        isDistrictMatched = true;
                                        clickTheElement(locationCardName);
                                        SimpleUtils.pass("District changed successfully to '" + districtName + "'");
                                        break;
                                    }
                                }
                                if (!isDistrictMatched) {
                                    //updated by Estelle because the default location dropdown list show more than 50 location ,it's not efficient for navigation latest logic
                                    searchDistrictAndSelect(districtName);
                                    waitForSeconds(3);
                                    availableLocationCardsName = getDriver().findElements(By.cssSelector("div.lg-search-options__option"));
                                    if (availableLocationCardsName.size() > 0) {
                                        for (WebElement locationCardName : availableLocationCardsName) {
                                            if (locationCardName.getText().contains(districtName)) {
                                                isDistrictMatched = true;
                                                click(locationCardName);
                                                SimpleUtils.pass("District changed successfully to '" + districtName + "'");
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (!isDistrictMatched) {
                                    SimpleUtils.fail("District does matched with '" + districtName + "'", true);
                                }
                            }
                        }
                        //verifyDMDashboardIsFinishedRefreshing();
                    }
                }
            } else {
                WebElement dashboardConsoleMenu = SimpleUtils.getSubTabElement(consoleMenuItems, dashboardConsoleMenuText);
                if (isElementLoaded(dashboardConsoleMenu)) {
                    click(dashboardConsoleMenu);
                    changeDistrict(districtName);
                }
            }
        }
        catch(Exception e) {
            SimpleUtils.fail("Unable to change District!", true);
        }
    }

    @Override
    public void changeUpperFields(String upperFields) throws Exception {
        try {
            String[] upperFieldsList = null;
            if (upperFields != null && !upperFields.isEmpty()) {
                if (upperFields.contains(">")) {
                    upperFieldsList = upperFields.split(">");
                    if (upperFieldsList.length == 3) {
                        changeUpperFieldsByName(typeOfUpperFields.BusinessUnit.value, upperFieldsList[0].trim());
                        changeUpperFieldsByName(typeOfUpperFields.Region.value, upperFieldsList[1].trim());
                        changeUpperFieldsByName(typeOfUpperFields.District.value, upperFieldsList[2].trim());
                    } else if (upperFieldsList.length == 2) {
                        changeUpperFieldsByName(typeOfUpperFields.Region.value, upperFieldsList[0].trim());
                        changeUpperFieldsByName(typeOfUpperFields.District.value, upperFieldsList[1].trim());
                    } else {
                        SimpleUtils.fail("Upperfield List is incorrect, please update the Upperfiled in UpperfieldsForDifferentEnterprises.json file", false);
                    }
                } else {
                    changeUpperFieldsByName(typeOfUpperFields.District.value, upperFields.trim());
                }
            }
        } catch (Exception e) {
            SimpleUtils.fail("Failed to get the Upperfields List!", false);
        }
    }

    @Override
    public void changeUpperFieldsByName(String upperFieldType, String upperFieldName) {
        try {
            Boolean isUpperFieldMatched = false;
            if (isElementLoaded(activeConsoleMenuItem, 10)) {
                activeConsoleName = activeConsoleMenuItem.getText();
            }
            setScreenshotConsoleName(activeConsoleName);
            WebElement upperFieldSelectorButton = getDriver().findElement(By.cssSelector("[search-hint='Search " + upperFieldType + "'] div.input-faked"));
            if (activeConsoleMenuItem.getText().contains(dashboardConsoleMenuText)) {
                if (isElementLoaded(upperFieldSelectorButton, 5)) {
                    if (upperFieldSelectorButton.getText().equalsIgnoreCase(upperFieldName)) {
                        SimpleUtils.pass("Given '" + upperFieldType + " " + upperFieldName + "' already selected!");
                    } else {
                        if(isElementLoaded(upperFieldSelectorButton, 10)){
                            click(upperFieldSelectorButton);
                        }
                        WebElement upperFieldDropDownButton = getDriver().findElement(By.cssSelector("[search-hint=\"Search " +
                                upperFieldType + "\"] div.lg-search-options"));
                        if (isElementLoaded(upperFieldDropDownButton, 5)) {
                            if (availableLocationCardsName.size() != 0) {
                                for (WebElement upperFieldCardName : availableLocationCardsName) {
                                    if (upperFieldCardName.getText().equalsIgnoreCase(upperFieldName)) {
                                        isUpperFieldMatched = true;
                                        clickTheElement(upperFieldCardName);
                                        SimpleUtils.pass(upperFieldType + " changed successfully to '" + upperFieldName + "'");
                                        break;
                                    }
                                }
                                if (!isUpperFieldMatched) {
                                    WebElement upperFieldSearchInput = getDriver().findElement(By.cssSelector("input[placeholder=\"Search " + upperFieldType + "\"]"));
                                    if (isElementLoaded(upperFieldSearchInput,5)) {
                                        upperFieldSearchInput.sendKeys(upperFieldName);
                                        upperFieldSearchInput.sendKeys(Keys.ENTER);
                                    }else {
                                        SimpleUtils.fail("Search " + upperFieldType + "input failed to load!", false);
                                    }
                                    waitForSeconds(3);
                                    availableLocationCardsName = getDriver().findElements(By.cssSelector("div.lg-search-options__option"));
                                    if (availableLocationCardsName.size() > 0) {
                                        for (WebElement upperFieldCardName : availableLocationCardsName) {
                                            if (upperFieldCardName.getText().equalsIgnoreCase(upperFieldName)) {
                                                isUpperFieldMatched = true;
                                                click(upperFieldCardName);
                                                SimpleUtils.pass(upperFieldType + " changed successfully to '" + upperFieldName + "'");
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (!isUpperFieldMatched) {
                                    SimpleUtils.fail(upperFieldType + " does matched with '" + upperFieldName + "'", false);
                                }
                            }
                        }
                    }
                }
            } else {
                WebElement dashboardConsoleMenu = SimpleUtils.getSubTabElement(consoleMenuItems, dashboardConsoleMenuText);
                if (isElementLoaded(dashboardConsoleMenu)) {
                    click(dashboardConsoleMenu);
                    changeDistrict(upperFieldName);
                }
            }
        }
        catch(Exception e) {
            // Do nothing
        }
    }

    private void verifyDMDashboardIsFinishedRefreshing() throws Exception {
        if (isElementLoaded(refreshButton, 30)) {
            SimpleUtils.pass("DM Dashbord is finished refreshing!");
        } else {
            SimpleUtils.fail("DM Dashboard: Refresh button is not loaded Successfully!", false);
        }
    }

    @FindBy(css = "lg-select[search-hint=\"Search District\"]")
    private WebElement selectedDistrict;

    public void changeDistrictDirect() throws Exception {
        waitForSeconds(4);
        String districtName = null;
        if (isElementLoaded(selectedDistrict, 5)) {
            districtName = selectedDistrict.getText();
        }
        try {
            Boolean isDistrictMatched = false;
            if (isChangeDistrictButtonLoaded()) {
                click(districtSelectorButton);
                if (isElementLoaded(districtDropDownButton)) {
                    if (availableLocationCardsName.size() != 0) {
                        for (WebElement locationCardName : availableLocationCardsName) {
                            if (locationCardName.getText().contains(districtName)) {
                                isDistrictMatched = true;
                                click(locationCardName);
                                SimpleUtils.pass("District changed successfully to '" + districtName + "'");
                                break;
                            }
                        }
                        if (!isDistrictMatched) {
                            if (isChangeDistrictButtonLoaded()) {
                                click(districtSelectorButton);
                            }
                            SimpleUtils.fail("District does not matched with '" + districtName + "'", true);
                        }
                    }
                }
            }
        }
        catch(Exception e) {
            SimpleUtils.fail("Unable to change District!", true);
        }
    }
    
    //added by estelle to search location if the location is not in recent list
    @FindBy(css = "input[placeholder=\"Search District\"]")
    private WebElement districtSearchInput;
    private void searchDistrictAndSelect(String districtName) throws Exception {
        if (isElementLoaded(districtSearchInput,5)) {
            districtSearchInput.sendKeys(districtName);
            districtSearchInput.sendKeys(Keys.ENTER);
        }else {
            SimpleUtils.fail("Search District input failed to load!", false);
        }
    }

    //added by Fiona
    //get the default location value

    public String getCurrentUserDefaultLocation() throws Exception
    {
        String defaultLocation = "";
        if(isChangeLocationButtonLoaded()){
            defaultLocation=locationSelectorButton.getText();
        }else {
            SimpleUtils.fail("Default Location not appear on Dashboard!", false);
        }
        return defaultLocation;
    }

    //Check the navigation bar - location fiels shows as 'All locations' or not?
    @Override
    public void isDMView() throws Exception {
        String locationFieldsText = "All Locations";
        if(getCurrentUserDefaultLocation().contains(locationFieldsText)){
            SimpleUtils.pass("Dashboard page shows as DM view!");
        }else {
            SimpleUtils.fail("Dashboard page shows as NOT DM view!",true);
        }
    }
    @Override
    public void isSMView() throws Exception {
        String locationFieldsText = "All Locations";
        if(getCurrentUserDefaultLocation().contains(locationFieldsText)){
            SimpleUtils.fail("Dashboard page shows as NOT SM view!",true);
        }else {
            SimpleUtils.pass("Dashboard page shows as SM view!");
        }
    }

    @FindBy(css = "lg-search-options[search-hint='Search District'] div.lg-search-options__scroller div.cachedDisrictInfo")
    private WebElement districtCountInDropdownList;

    @FindBy(css = "lg-search-options[search-hint='Search District'] div.lg-search-options__scroller div[ng-repeat]")
    private List<WebElement> districDetailsInDropdownList;

    @Override
    public void verifyClickChangeDistrictButton() throws Exception {
        if (isElementLoaded(districtSelectorButton, 10)){
            click(districtSelectorButton);
            if (isElementLoaded(districtDropDownButton, 10)){
                SimpleUtils.pass("The district list layout shows!");
                if (isElementLoaded(searchDistrictInput, 5) && areListElementVisible(districDetailsInDropdownList, 5)){
                    SimpleUtils.pass("List of districts and search textbox show.");
                }
                else{
                    SimpleUtils.fail("List of districts and search textbox don't show.", true);
                }
            }
            else{
                SimpleUtils.fail("The district list layout doesn't show!", true);
            }
        }
    }

    @Override
    public List<Integer> searchDistrict(String searchInputText) throws Exception {
        List<Integer> searchedDistrictCount = new ArrayList<>();
        try {
            String[] searchLocationCha = searchInputText.split(",");
            if(isChangeDistrictButtonLoaded()) {
                verifyClickChangeDistrictButton();
                if (searchLocationCha.length > 0) {
                    for (int i =0; i<searchLocationCha.length; i++){
                        searchDistrictInput.sendKeys(searchLocationCha[i]);
                        searchDistrictInput.sendKeys(Keys.ENTER);
                        waitForSeconds(4);
                        List<String> districtCountList = Arrays.asList(districtCountInDropdownList.getText().trim().split(" "));
                        int displayDistrictCount = Integer.parseInt(districtCountList.get(2));
                        int totalDistrictCount = Integer.parseInt(districtCountList.get(4));

                        if(totalDistrictCount > 50){
                            if(searchLocationCha[i].contains("*")){
                                searchedDistrictCount.add(totalDistrictCount);
                                SimpleUtils.pass("User can search " + totalDistrictCount + " district using " + searchLocationCha[i] + "in navigation.");
                            }else{
                                for (WebElement detailDistrict : districDetailsInDropdownList) {
                                    String districtName = detailDistrict.getText();
                                    if (districtName.toLowerCase().contains(searchLocationCha[i].toLowerCase())) {
                                        SimpleUtils.pass("Verified " + districtName + " contains test string: " + searchLocationCha[i]);
                                    } else {
                                        SimpleUtils.fail("Verify failed, " + districtName + " doesn't contain test string: " + searchLocationCha[i], true);
                                    }
                                }
                                searchedDistrictCount.add(totalDistrictCount);
                                SimpleUtils.pass("User can search " + totalDistrictCount + " district using " + searchLocationCha[i] + "in navigation.");
                            }
                        }else{
                            if(searchLocationCha[i].contains("*")){
                                searchedDistrictCount.add(displayDistrictCount);
                                SimpleUtils.pass("User can search " + displayDistrictCount + " district using " + searchLocationCha[i] + "in navigation.");
                            }else{
                                for (WebElement detailDistrict : districDetailsInDropdownList) {
                                    String districtName = detailDistrict.getText();
                                    if (districtName.toLowerCase().contains(searchLocationCha[i].toLowerCase())) {
                                        SimpleUtils.pass("Verified " + districtName + " contains test string: " + searchLocationCha[i] + "The serach results is correct");
                                    } else {
                                        SimpleUtils.fail("Verify failed, " + districtName + " doesn't contain test string: " + searchLocationCha[i], true);
                                    }
                                }
                                searchedDistrictCount.add(displayDistrictCount);
                                SimpleUtils.pass("User can search " + displayDistrictCount + " district using " + searchLocationCha[i] + "in navigation.");
                            }
                        }
                    }
                } else {
                    SimpleUtils.fail("Test string is empty!", true);
                }
            }
            else{
                SimpleUtils.fail("Change Location Button does't Load Successfully!", true);
            }
        }
        catch(Exception e){
            SimpleUtils.fail("Verify the function of Search TextBox failed!", true);
        }
        return searchedDistrictCount;
    }

    @Override
    public List<String> searchLocation(String searchInputText) throws Exception {
        List<String> locations = null;
        try {
            locations = new ArrayList<>();
            if (isChangeLocationButtonLoaded()) {
                click(locationButton);
                if (!searchInputText.isEmpty() && searchInputText != null) {
                    click(searchTextbox);
                    searchTextbox.sendKeys(searchInputText);
                    searchTextbox.sendKeys(Keys.ENTER);
                    waitForSeconds(4);
                    if (isElementLoaded(locationItems, 10)) {
                        List<WebElement> locationList = locationItems.findElements(By.cssSelector("div.lg-search-options__option"));
                        for (WebElement location : locationList) {
                            String locationName = location.getText();
                            locations.add(locationName);
                            SimpleUtils.pass("Location: " + locationName + " is showing.");
                        }
                        SimpleUtils.pass("In this District, totally have " + locations.size() + " locations!");
                    } else {
                        SimpleUtils.pass("There is no locations in selected district");
                    }
                } else {
                    SimpleUtils.fail("Test string is empty!", true);
                }
            } else {
                SimpleUtils.fail("Change Location Button does't Load Successfully!", true);
            }
        } catch (Exception e) {
            SimpleUtils.fail("Verify the function of Search TextBox failed!", true);
        }
        return locations;
    }

    // Added by Julie
    @FindBy (css = "[ng-class=\"{'lg-new-location-chooser__highlight' : $ctrl.location && $ctrl.parentLocation}\"]")
    private WebElement currentLocationName;

    @FindBy (css = ".wm-visual-design-canvas.walkme-to-remove")
    private WebElement windowNewFeatureEnhancements;

    @FindBy (css = ".wm-ignore-css-reset path")
    private WebElement closeBtnInNewFeatureEnhancements;

    @FindBy(css= ".dif .header-company-icon .company-icon-img")
    private WebElement companyIcon;

    @Override
    public String getLocationNameFromDashboard() throws Exception {
        String currentLocation = "";
        if (isElementEnabled(currentLocationName, 5)) {
            currentLocation = currentLocationName.getText();
        }
        return currentLocation;
    }

    @Override
    public void verifyTheDisplayDistrictWithSelectedDistrictConsistent(String districtName) throws Exception {
        waitForSeconds(3);
        if (isDistrictSelected(districtName))
            SimpleUtils.pass("Dashboard Page: Display district is consistent with the selected district");
        else
            SimpleUtils.fail("Dashboard Page: Display district is not consistent with the selected district", false);
    }

    @Override
    public void reSelectDistrict(String districtName) throws Exception {
        waitForSeconds(4);
        Boolean isDistrictMatched = false;
        activeConsoleName = activeConsoleMenuItem.getText();
        setScreenshotConsoleName(activeConsoleName);
        if(isElementLoaded(districtSelectorButton, 10)){
            click(districtSelectorButton);
        }
        if (isElementLoaded(districtDropDownButton, 5)) {
            if (availableLocationCardsName.size() != 0) {
                for (WebElement locationCardName : availableLocationCardsName) {
                    if (locationCardName != null && locationCardName.getText() != null && !locationCardName.getText().isEmpty()
                            && locationCardName.getText().contains(districtName)) {
                        isDistrictMatched = true;
                        clickTheElement(locationCardName);
                        if (isElementLoaded(windowNewFeatureEnhancements,5))
                            click(closeBtnInNewFeatureEnhancements);
                        click(companyIcon);
                        SimpleUtils.pass("District changed successfully to '" + districtName + "'");
                        waitForSeconds(8);
                        break;
                    }
                }
                if (!isDistrictMatched) {
                    searchDistrictAndSelect(districtName);
                    waitForSeconds(3);
                    availableLocationCardsName = getDriver().findElements(By.cssSelector("div.lg-search-options__option"));
                    if (availableLocationCardsName.size() > 0) {
                        for (WebElement locationCardName : availableLocationCardsName) {
                            if (locationCardName.getText().contains(districtName)) {
                                isDistrictMatched = true;
                                click(locationCardName);
                                SimpleUtils.pass("District changed successfully to '" + districtName + "'");
                                break;
                            }
                        }
                    }
                }
                if (!isDistrictMatched) {
                    SimpleUtils.fail("District does matched with '" + districtName + "'", true);
                }
            }
        }
    }

    @FindBy(css = "[search-hint=\"Search District\"] div.lg-search-options__option")
    private List<WebElement> availableDistrictCardsName;

    @Override
    public void changeAnotherDistrict() throws Exception {
        waitForSeconds(4);
        String districtName = selectedDistrict.getText();
        try {
            if (activeConsoleMenuItem.getText().contains(dashboardConsoleMenuText)) {
                if (isChangeDistrictButtonLoaded()) {
                        if(isElementLoaded(districtSelectorButton, 10)){
                            click(districtSelectorButton);
                        }
                        if (isElementLoaded(districtDropDownButton, 5)) {
                            if (availableDistrictCardsName.size() != 0) {
                                for (WebElement districtCardName : availableDistrictCardsName) {
                                    if (!districtCardName.getText().contains(districtName)) {
                                        clickTheElement(districtCardName);
                                        SimpleUtils.pass("District changed successfully to '" + districtCardName.getText() + "'");
                                        break;
                                    }
                                }
                            }
                        }
                }
            } else {
                WebElement dashboardConsoleMenu = SimpleUtils.getSubTabElement(consoleMenuItems, dashboardConsoleMenuText);
                if (isElementLoaded(dashboardConsoleMenu)) {
                    click(dashboardConsoleMenu);
                    changeDistrict(districtName);
                }
            }
        }
        catch(Exception e) {
            SimpleUtils.fail("Unable to change District!", true);
        }
    }

    @Override
    public void changeAnotherDistrictInDMView() throws Exception {
        waitForSeconds(4);
        String districtName = selectedDistrict.getText();
        try {
            if (isChangeDistrictButtonLoaded()) {
                if(isElementLoaded(districtSelectorButton, 10)){
                    click(districtSelectorButton);
                }
                if (isElementLoaded(districtDropDownButton, 5)) {
                    if (availableDistrictCardsName.size() != 0) {
                        for (WebElement districtCardName : availableDistrictCardsName) {
                            if (!districtCardName.getText().contains(districtName)) {
                                clickTheElement(districtCardName);
                                SimpleUtils.pass("District changed successfully to '" + districtCardName.getText() + "'");
                                waitForSeconds(1);
                                break;
                            }
                        }
                    }
                }
            }
        } catch(Exception e) {
            SimpleUtils.fail("Unable to change District!", true);
        }
    }

    @Override
    public void reSelectDistrictInDMView(String districtName) throws Exception {
        waitForSeconds(4);
        try {
            Boolean isDistrictMatched = false;
            if(isElementLoaded(districtSelectorButton, 10)){
                click(districtSelectorButton);
            }
            if (isElementLoaded(districtDropDownButton, 5)) {
                if (availableLocationCardsName.size() != 0) {
                    for (WebElement locationCardName : availableLocationCardsName) {
                        if (locationCardName.getText().contains(districtName)) {
                            isDistrictMatched = true;
                            clickTheElement(locationCardName);
                            if (isElementLoaded(windowNewFeatureEnhancements,5))
                                click(closeBtnInNewFeatureEnhancements);
                            SimpleUtils.pass("District changed successfully to '" + districtName + "'");
                            waitForSeconds(8);
                            break;
                        }
                    }
                    if (!isDistrictMatched) {
                        searchDistrictAndSelect(districtName);
                        waitForSeconds(3);
                        availableLocationCardsName = getDriver().findElements(By.cssSelector("div.lg-search-options__option"));
                        if (availableLocationCardsName.size() > 0) {
                            for (WebElement locationCardName : availableLocationCardsName) {
                                if (locationCardName.getText().contains(districtName)) {
                                    isDistrictMatched = true;
                                    click(locationCardName);
                                    SimpleUtils.pass("District changed successfully to '" + districtName + "'");
                                    break;
                                }
                            }
                        }
                    }
                    if (!isDistrictMatched) {
                        SimpleUtils.fail("District does matched with '" + districtName + "'", true);
                    }
                }
            }
        } catch(Exception e) {
            SimpleUtils.fail("Unable to change District!", true);
        }
    }

    //added by Estelle for upperfield view
    @FindBy(css = "div[ng-repeat-start=\"hierarchy in $ctrl.getNavHierarchy()\"]")
    private List<WebElement> levelDisplay;
    @FindBy(css = "input[placeholder=\"Search BU\"]")
    private WebElement buSearchInput;
    @FindBy(css = "input[placeholder=\"Search Region\"]")
    private WebElement regionSearchInput;


    @Override
    public void verifyDefaultLevelForBUOrAdmin() {
        if (areListElementVisible(levelDisplay,5)) {
            if (levelDisplay.size()==2) {
                SimpleUtils.pass("The default location navigation level for BU ,admin or communication role is correct");
            }else
                SimpleUtils.fail("The default location navigation level for BU ,admin or communication role is wrong and the size is : "+levelDisplay.size(), false);
        }else
            SimpleUtils.fail("Location navigation bar load failed",false);

    }

    @Override
    public void searchSpecificBUAndNavigateTo(String buText) {
       click(levelDisplay.get(0));
        if (isElementEnabled(buSearchInput,5)) {
            buSearchInput.sendKeys(buText);
            buSearchInput.sendKeys(Keys.ENTER);
        }

    }

    @Override
    public void searchSpecificRegionAndNavigateTo(String regionText) {

    }

    @Override
    public void searchSpecificDistrictAndNavigateTo(String districtText) {

    }

    @Override
    public void searchSpecificLocationAndNavigateTo(String locationText) {

    }

    @Override
    public void verifyMagnifyGlassIconShowOrNot() {

    }

}