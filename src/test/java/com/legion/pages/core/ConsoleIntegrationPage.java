package com.legion.pages.core;

import com.legion.pages.ActivityPage;
import com.legion.pages.BasePage;
import com.legion.pages.IntegrationPage;
import com.legion.utils.SimpleUtils;
import cucumber.api.java.eo.Se;
import com.legion.pages.BasePage;
import com.legion.pages.InsightPage;
import com.legion.pages.IntegrationPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleIntegrationPage extends BasePage implements IntegrationPage {

	public ConsoleIntegrationPage() {
		PageFactory.initElements(getDriver(), this);
	}

	// Added by Mary
	@FindBy (css = "input[placeholder=\"You can search by Integration Channel, Application, etc...\"]")
	private WebElement searchConfigInput;

	@FindBy (css = "tr[ng-repeat=\"config in filteredConfigs\"]")
	private List<WebElement> configs;

	@FindBy (css = "input-field[label=\"Channel\"] select")
	private WebElement channelDropDown;

	@FindBy (css = "input-field[label=\"Application Type\"] select")
	private WebElement applicationTypeDropDown;

	@FindBy (css = "input-field[label=\"Status\"] select")
	private WebElement statusDropDown;

	@FindBy (css = "input-field[label=\"Time Zone Option\"] select")
	private WebElement timeZoneOptionDropDown;

	@FindBy (css = "lg-button[label=\"Create\"]")
	private WebElement createButton;

	@FindBy (css = "lg-button[label=\"Create Config\"]")
	private WebElement createConfigButton;

	@Override
	public boolean checkIsConfigExists(String channel, String application) throws Exception {
		boolean isConfigExists = false;
		if (isElementLoaded(searchConfigInput, 5)) {
			searchConfigInput.clear();
			searchConfigInput.sendKeys(channel);
			waitForSeconds(2);
			if (areListElementVisible(configs, 5) && configs.size()>0){
				for (WebElement config: configs){
					List<WebElement> detailsOfConfig = config.findElements(By.tagName("td"));
					if (detailsOfConfig.get(0).getText().equalsIgnoreCase(channel) &&
							detailsOfConfig.get(1).getText().equalsIgnoreCase(application)){
						isConfigExists = true;
						SimpleUtils.report("The config : "+channel+" " +application+" is exists! ");
						break;
					}
				}
			} else
				SimpleUtils.report("Cannot find the config: "+ channel);
		} else
			SimpleUtils.fail("Search configs input fail to load! ", false);
		return isConfigExists;
	}

	/*
	* configInfo:
	*
	* channel
	* applicationType
	* status
	* timeZoneOption
	*
	* */

	@Override
	public void createConfig(Map<String, String> configInfo) throws Exception {
		clickOnCreateConfigButton();
		if (isElementLoaded(channelDropDown, 5) &&
				isElementLoaded(applicationTypeDropDown, 5) &&
				isElementLoaded(statusDropDown, 5) &&
				isElementLoaded(timeZoneOptionDropDown, 5) && configInfo.size()>=4) {


			Select channel = new Select(channelDropDown);
			Select applicationType = new Select(applicationTypeDropDown);
			Select status = new Select(statusDropDown);
			Select timeZoneOption = new Select(timeZoneOptionDropDown);

			channel.selectByVisibleText(configInfo.get("channel"));
			applicationType.selectByVisibleText(configInfo.get("applicationType"));
			status.selectByVisibleText(configInfo.get("status"));
			timeZoneOption.selectByVisibleText(configInfo.get("timeZoneOption"));

			click(createButton);

		} else
				SimpleUtils.fail("Cannot find the config options! ", false);
	}

	public void clickOnCreateConfigButton () throws Exception {
		if (isElementLoaded(createConfigButton, 5)) {
			click(createConfigButton);
			SimpleUtils.pass("Click create config button successfully! ");
		} else
			SimpleUtils.fail("Create config button fail to load! ", false);
	}

	@FindBy(css = "div.console-navigation-item-label.Integration")
	private WebElement integrationConsoleMenuDiv;
	@FindBy(className = "console-navigation-item")
	private List<WebElement> consoleNavigationMenuItems;
	@FindBy(css = "div[data-content=\"Legion Dashboard\"]")
	private List<WebElement> dataContentForDashboard;
	final static String consoleIntegrationMenuItemText = "Integration";

	@Override
	public void clickOnConsoleIntegrationPage() throws Exception {
		if(isElementLoaded(integrationConsoleMenuDiv,20)) {
			click(integrationConsoleMenuDiv);
			if (integrationConsoleMenuDiv.findElement(By.xpath("./..")).getAttribute("class").contains("active"))
				SimpleUtils.pass("Integration Page: Click on Integration console menu successfully");
			else
				SimpleUtils.fail("Integration Page: It doesn't navigate to Integration console menu after clicking", false);
		} else
			SimpleUtils.fail("Integration Console Menu not loaded Successfully!", false);
	}
}
