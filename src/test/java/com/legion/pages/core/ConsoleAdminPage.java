package com.legion.pages.core;

import com.legion.pages.ActivityPage;
import com.legion.pages.AdminPage;
import com.legion.pages.BasePage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleAdminPage extends BasePage implements AdminPage {

	public ConsoleAdminPage() {
		PageFactory.initElements(getDriver(), this);
	}

		// added by Estelle

	@FindBy(css="div.console-navigation-item-label.Admin")
	private WebElement consoleAdminPageTabElement;

	@FindBy(className = "lgn-action-dropdown-button")
	private WebElement actionDropDownBtn;
	@Override
	public void goToAdminTab() throws Exception {
		if (isElementLoaded(consoleAdminPageTabElement,5) ) {
			clickTheElement(consoleAdminPageTabElement);
			if (isElementLoaded(actionDropDownBtn, 5)) {
				SimpleUtils.pass("Admin page load successfully");
			} else
				SimpleUtils.fail("Admin page load failed", false);
		}else
			SimpleUtils.fail("Admin tab is not clickable", false);
	}

	@FindBy(xpath = "//lgn-action-drop-down/div/ul/li[4]/a")
	private WebElement rebuildSearchIndexAction;

	@Override
	public void rebuildSearchIndex() throws Exception {
		if (isElementLoaded(actionDropDownBtn,5)) {
			clickTheElement(actionDropDownBtn);
			waitForSeconds(3);
			if (isElementLoaded(rebuildSearchIndexAction,5)) {
				SimpleUtils.pass("Action button is clickable");
				clickTheElement(rebuildSearchIndexAction);
				if (!isElementLoaded(rebuildSearchIndexAction,5)) {
					SimpleUtils.pass("Rebuild search index button is clickable");
				}
				waitForSeconds(30);
			}else
				SimpleUtils.fail("Action button is not clickable",false);

		}
	}

	@Override
	public void clickOnConsoleAdminMenu() throws Exception {
		if(isElementLoaded(consoleAdminPageTabElement,20)) {
			click(consoleAdminPageTabElement);
			if (consoleAdminPageTabElement.findElement(By.xpath("./..")).getAttribute("class").contains("active"))
				SimpleUtils.pass("Admin Page: Click on Admin console menu successfully");
			else
				SimpleUtils.fail("Admin Page: It doesn't navigate to Admin console menu after clicking", false);
		} else
			SimpleUtils.fail("Admin Console Menu not loaded Successfully!", false);
	}
}
