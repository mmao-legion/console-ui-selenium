package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.InsightPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class ConsoleInsightPage extends BasePage implements InsightPage {

	@FindBy(css = ".console-navigation-item-label.Insights")
	private WebElement insightConsoleMenuDiv;
	@FindBy(className = "console-navigation-item")
	private List<WebElement> consoleNavigationMenuItems;
	final static String consoleInsightsMenuItemText = "Insights";

	@Override
	public void clickOnConsoleInsightPage() throws Exception {
		if(isElementLoaded(insightConsoleMenuDiv,5)) {
			click(insightConsoleMenuDiv);
			if (insightConsoleMenuDiv.findElement(By.xpath("./..")).getAttribute("class").contains("active"))
				SimpleUtils.pass("Insights Page: Click on Compliance console menu successfully");
			else
				SimpleUtils.fail("Insights Page: It doesn't navigate to Compliance console menu after clicking", false);
		} else
			SimpleUtils.fail("Insights Console Menu not loaded Successfully!", false);
	}
}