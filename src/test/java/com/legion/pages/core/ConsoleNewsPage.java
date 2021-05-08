package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.NewsPage;
import com.legion.pages.ReportPage;
import com.legion.utils.SimpleUtils;
import cucumber.api.java.ro.Si;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ConsoleNewsPage extends BasePage implements NewsPage {

	@FindBy(css = ".console-navigation-item-label.News")
	private WebElement newsConsoleMenuDiv;
	@FindBy(className = "sub-navigation-view-link")
	private List<WebElement> newsSubTab;
	@FindBy(css = "section.sc-kYrkKh")
	private WebElement createPostPanel;
	@Override
	public void clickOnConsoleNewsMenu() throws Exception {
		if(isElementLoaded(newsConsoleMenuDiv,20)) {
			click(newsConsoleMenuDiv);
			if (newsConsoleMenuDiv.findElement(By.xpath("./..")).getAttribute("class").contains("active"))
				SimpleUtils.pass("News Page: Click on Compliance console menu successfully");
			else
				SimpleUtils.fail("News Page: It doesn't navigate to Compliance console menu after clicking", false);
		} else
			SimpleUtils.fail("News Console Menu not loaded Successfully!", false);
	}


	@Override
	public boolean isNewsTabLoadWell() throws Exception {
		if (newsSubTab.size()>0) {
			click(newsSubTab.get(0));
			if (isElementLoaded(createPostPanel,5)) {
				SimpleUtils.pass("News tab load successfully");
			}
		}else
			SimpleUtils.fail("News tab load failed",false);
		return false;
	}

}