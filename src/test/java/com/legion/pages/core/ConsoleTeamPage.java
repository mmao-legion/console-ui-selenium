package com.legion.pages.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.legion.pages.BasePage;
import com.legion.pages.TeamPage;

public class ConsoleTeamPage extends BasePage implements TeamPage{
	
	By goToTeamButton = By.cssSelector("[class='console-navigation-item-label Team']");
	//By goToTeamCoverageTab = By.cssSelector("[class='sub-navigation-view-label ng-binding']");
	By goToTeamCoverageTab = By.xpath("//*[@id=\"legion-app\"]/div/div[2]/div/div/div/div[2]/div/div/div[1]/sub-navigation/div/div[3]/div[1]/div[2]");

	public ConsoleTeamPage(WebDriver driver) {
        super(driver);
    }
    
    public void goToTeam() throws Exception
	{
    	waitForElement(goToTeamButton);
		click(goToTeamButton);
	}
    
    public boolean isTeam() throws Exception
	{
    	By rosterBodyElement = By.className("roster-body");
    	waitForElement(rosterBodyElement);
    	return true;
	}
    
    public void goToCoverage() throws Exception
    {
    	waitForElement(goToTeamCoverageTab);
		click(goToTeamCoverageTab);
    }
    public boolean isCoverage() throws Exception
    {
    	By coverageViewElement = By.className("coverage-view");
    	waitForElement(coverageViewElement);
    	return true;
    }
}
