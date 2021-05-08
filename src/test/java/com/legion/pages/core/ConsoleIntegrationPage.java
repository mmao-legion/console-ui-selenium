package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.InsightPage;
import com.legion.pages.IntegrationPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ConsoleIntegrationPage extends BasePage implements IntegrationPage {

	@FindBy(css = "div.console-navigation-item-label.Integration")
	private WebElement integrationConsoleMenuDiv;
	@FindBy(className = "console-navigation-item")
	private List<WebElement> consoleNavigationMenuItems;
	@FindBy(css = "div[data-content=\"Legion Dashboard\"]")
	private List<WebElement> dataContentForDashboard;
	final static String consoleIntegrationMenuItemText = "Integration";

	@Override
	public void clickOnConsoleIntegrationPage() throws Exception {
		if (consoleNavigationMenuItems.size() != 0) {
			WebElement consoleIntegrationMenuElement = SimpleUtils.getSubTabElement(consoleNavigationMenuItems, consoleIntegrationMenuItemText);
			clickTheElement(consoleIntegrationMenuElement);
			SimpleUtils.pass("'Integration' Console Menu loaded successfully!");
		} else
			SimpleUtils.fail("'Integration' Console Menu failed to load!", false);
	}
}