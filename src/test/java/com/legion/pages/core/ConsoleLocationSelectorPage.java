package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;
import static com.legion.utils.MyThreadLocal.*;

import java.util.HashMap;
import java.util.List;

import com.legion.utils.JsonUtil;
import org.openqa.selenium.By;
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

    @FindBy(css = ".lg-new-location-chooser__highlight .lg-search-options")
    private WebElement locationDropDownButton;

    @FindBy(className = "location-selector-dropdown-menu-items")
    private List<WebElement> locationDropDownItems;

    @FindBy(css = "div.lg-search-options__option")
    private List<WebElement> availableLocationCardsName;

    @FindBy(className = "location-selector-location-name-text")
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

    @FindBy(css = "[search-hint='Search District'] div.input-faked")
    private WebElement districtSelectorButton;
    @FindBy(css = "[search-hint=\"Search District\"] div.lg-search-options")
    private WebElement districtDropDownButton;
    @FindBy(css = "lg-search[placeholder=\"Search District\"] input")
    private WebElement searchDistrictInput;
    @FindBy(className = "lg-search-icon")
    private WebElement searchIcon;

    String dashboardConsoleMenuText = "Dashboard";
    private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");

    
    public ConsoleLocationSelectorPage(){
        PageFactory.initElements(getDriver(), this);
    }
    
    @Override
    public Boolean isChangeLocationButtonLoaded() throws Exception
    {
        if(isElementLoaded(locationSelectorButton,10)) {
            return true;
        }
        return false;
    }
    
    @Override
    public void changeLocation(String locationName)
    {
        waitForSeconds(4);
        getDriver().navigate().refresh();
        waitForSeconds(4);
        try {
            Boolean isLocationMatched = false;
            activeConsoleName = activeConsoleMenuItem.getText();
            setScreenshotConsoleName(activeConsoleName);
            if (activeConsoleMenuItem.getText().contains(dashboardConsoleMenuText)) {
                if (isChangeLocationButtonLoaded()) {
                    if (isLocationSelected(locationName)) {
                        SimpleUtils.pass("Given Location '" + locationName + "' already selected!");
                    } else {
                        click(locationSelectorButton);
                        if (areListElementVisible(availableLocationCardsName, 10) || isElementLoaded(locationDropDownButton)) {
                            if (availableLocationCardsName.size() != 0) {
                                for (WebElement locationCardName : availableLocationCardsName) {
                                    if (locationCardName.getText().contains(locationName)) {
                                        isLocationMatched = true;
                                        click(locationCardName);
                                        SimpleUtils.pass("Location changed successfully to '" + locationName + "'");
                                        break;
                                    }
                                }
                            if (!isLocationMatched) {
                                if (isElementLoaded(dashboardLocationsPopupCancelButton)) {
                                    click(dashboardLocationsPopupCancelButton);
                                }
                                SimpleUtils.fail("Location does not match with '" + locationName + "'", true);
                            }

                            }
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
    		SimpleUtils.fail("Unable to change location!", true);
    	}

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
    @Override
    public void changeDistrict(String districtName) {
        waitForSeconds(4);
        try {
            Boolean isDistrictMatched = false;
            activeConsoleName = activeConsoleMenuItem.getText();
            setScreenshotConsoleName(activeConsoleName);
            if (activeConsoleMenuItem.getText().contains(dashboardConsoleMenuText)) {
                if (isChangeDistrictButtonLoaded()) {
                    if (isDistrictSelected(districtName)) {
                        SimpleUtils.pass("Given District '" + districtName + "' already selected!");
                    } else {
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
                                    if (isElementLoaded(dashboardLocationsPopupCancelButton)) {
                                        click(dashboardLocationsPopupCancelButton);
                                    }
                                    SimpleUtils.fail("District does matched with '" + districtName + "'", true);
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
}