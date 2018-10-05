package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.legion.pages.BasePage;
import com.legion.pages.LocationSelectorPage;
import com.legion.utils.SimpleUtils;

public class ConsoleLocationSelectorPage extends BasePage implements LocationSelectorPage {

	@FindBy(className = "location-selector-location-bullet-container")
	private WebElement locationSelectorButton;
	
	@FindBy(css = "div.console-navigation-item")
	private List<WebElement> consoleMenuItems;
	
	@FindBy(css = "div.console-navigation-item.active")
	private WebElement activeConsoleMenuItem;
	
	@FindBy(id = "locationDropDown")
	private WebElement locationDropDownButton;
	
	@FindBy(className = "location-selector-dropdown-menu-items")
	private List<WebElement> locationDropDownItems;
	
	@FindBy(className = "location-card-name-text")
	private List<WebElement> availableLocationCardsName;
	
	@FindBy(className = "location-selector-location-name-text")
	private WebElement dashboardSelectedLocationText;
	
	@FindBy (className = "location-selection-action-cancel")
	private WebElement dashboardLocationsPopupCancelButton;
	
	String dashboardConsoleMenuText = "Dashboard";


	
	public ConsoleLocationSelectorPage(){
		PageFactory.initElements(getDriver(), this);
	}
	
	public Boolean isChangeLocationButtonLoaded() throws Exception
	{
		if(isElementLoaded(locationSelectorButton)) {
			return true;
		}
		return false;
	}
	
	public void changeLocation(String locationName) throws Exception
	{
		Boolean isLocationMatched = false;
		if(activeConsoleMenuItem.getText().contains(dashboardConsoleMenuText)) {
			if(isChangeLocationButtonLoaded()) {
				if(dashboardSelectedLocationText.getText().contains(locationName)) {
					SimpleUtils.pass("Given Location '"+ locationName +"' already selected!");
				}
				else {
					click(locationSelectorButton);
					if(isElementLoaded(locationDropDownButton)) {
						click(locationDropDownButton);
						if(locationDropDownItems.size() > 1) {
							click(locationDropDownItems.get(1));
							if(availableLocationCardsName.size() != 0) {
								for(WebElement locationCardName : availableLocationCardsName) {
									System.out.println("Locations text: "+locationCardName.getText());
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
		}
		else {
			WebElement dashboardConsoleMenu = SimpleUtils.getSubTabElement(consoleMenuItems, dashboardConsoleMenuText);
			if(isElementLoaded(dashboardConsoleMenu)) {
				click(dashboardConsoleMenu);
				changeLocation(locationName);
			}
		}
	}
}
