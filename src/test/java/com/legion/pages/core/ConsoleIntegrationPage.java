package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.InsightPage;
import com.legion.pages.IntegrationPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleIntegrationPage extends BasePage implements IntegrationPage {

	public ConsoleIntegrationPage() {
		PageFactory.initElements(getDriver(), this);
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