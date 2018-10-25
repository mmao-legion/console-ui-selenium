package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;
import static com.legion.utils.MyThreadLocal.*;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.legion.pages.BasePage;
import com.legion.pages.LocationSelectorPage;
import com.legion.tests.testframework.ScreenshotManager;
import com.legion.utils.SimpleUtils;

public class ConsoleLocationSelectorPage extends BasePage implements LocationSelectorPage {

    @FindBy(className = "location-selector-location-bullet-container")
    private WebElement locationSelectorButton;
    
    @FindBy(css = "div.console-navigation-item")
    private List<WebElement> consoleMenuItems;
    
    @FindBy(css = "div.console-navigation-item.active")
    private WebElement activeConsoleMenuItem;
    
    @FindBy(css = "div.lg-search-options")
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


    String dashboardConsoleMenuText = "Dashboard";


    
    public ConsoleLocationSelectorPage(){
        PageFactory.initElements(getDriver(), this);
    }
    
    @Override
    public Boolean isChangeLocationButtonLoaded() throws Exception
    {
        if(isElementLoaded(locationSelectorButton)) {
            return true;
        }
        return false;
    }
    
    @Override
    public void changeLocation(String locationName)
    {
        Boolean isLocationMatched = false;
        activeConsoleName = activeConsoleMenuItem.getText();
        setScreenshotConsoleName(activeConsoleName);
        if(activeConsoleMenuItem.getText().contains(dashboardConsoleMenuText)) {
            if(isChangeLocationButtonLoaded()) {
                if(isLocationSelected(locationName)) {
                    SimpleUtils.pass("Given Location '"+ locationName +"' already selected!");
                }
                else {
                    click(locationSelectorButton);
                    if(isElementLoaded(locationDropDownButton)) {
                        if(availableLocationCardsName.size() != 0) {
                            for(WebElement locationCardName : availableLocationCardsName) {
                                if(locationCardName.getText().contains(locationName)) {
                                    isLocationMatched = true;
                                    click(locationCardName);
                                    SimpleUtils.pass("Location changed successfully to '" + locationName + "'");
                                    break;
                                }
                            }
                            if(! isLocationMatched) {
                                if(isElementLoaded(dashboardLocationsPopupCancelButton)) {
                                    click(dashboardLocationsPopupCancelButton);
                                }
                                SimpleUtils.fail("Location does matched with '" + locationName + "'", true);
                            }
                        
                        }
                    }
                }
            }
        }
        else {
            WebElement dashboardConsoleMenu = SimpleUtils.getSubTabElement(consoleMenuItems, dashboardConsoleMenuText);
            if(isElementLoaded(dashboardConsoleMenu)) {
                click(dashboardConsoleMenu);
                changeLocation(locationName);
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
                if(dashboardSelectedLocationText.getText().contains(locationName)) {
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
}