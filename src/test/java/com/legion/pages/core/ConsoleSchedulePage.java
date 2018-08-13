package com.legion.pages.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.legion.pages.BasePage;
import com.legion.pages.SchedulePage;

public class ConsoleSchedulePage extends BasePage implements SchedulePage {

	public ConsoleSchedulePage(WebDriver driver)
	{
		super(driver);
	}
	
	By goToScheduleButton = By.xpath("//*[@id=\"legion-app\"]/div/div[2]/div/div/div/div[1]/navigation/div/div[4]");
	//By goToTeamCoverageTab = By.cssSelector("[class='sub-navigation-view-label ng-binding']");
	//By goToScheduleButton = By.cssSelector("[class='console-navigation-item-label Schedule']");

	By goToScheduleOverviewTab = By.xpath(".//*[@id='legion-app']/div/div[2]/div/div/div/div[1]/navigation/div/div[4]/div[1]/div");
	
	public void gotoToSchedulePage() throws Exception{
		
		waitForElement(goToScheduleButton);
		click(goToScheduleButton);
		Reporter.log("Schedule Page Loading..!");
	}
	public boolean isSchedulePage() throws Exception
	{
		
		Reporter.log("isSchedulePage method Called..!");
		By scheduleSectionElement = By.className("schedule-view");
		waitForElement(scheduleSectionElement);
		Reporter.log("Schedule Page Loaded Successfully!");
		return true;
	}
	
}
