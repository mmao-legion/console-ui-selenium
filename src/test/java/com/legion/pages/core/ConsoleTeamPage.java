package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.legion.pages.BasePage;
import com.legion.pages.TeamPage;
import com.legion.utils.SimpleUtils;

public class ConsoleTeamPage extends BasePage implements TeamPage{
	
	 @FindBy(css="[class='console-navigation-item-label Team']")
	 private WebElement goToTeamButton;
	 
	 @FindBy(css="div.sub-navigation div.row div:nth-child(2) div.sub-navigation-view-link")
	 private WebElement goToTeamCoverageTab;
	 
	 @FindBy(className="roster-body")
	 private WebElement rosterBodyElement;
	 
	 @FindBy(className="coverage-view")
	 private WebElement coverageViewElement;
	 
	 @FindBy(css="[ng-if=\"canShowTodos()\"]")
	 private WebElement teamPageShowToDoButton;
	 
	 @FindBy(css="[ng-click=\"closeTodoPanelClick()\"]")
	 private WebElement teamPageHideToDoButton;
	 
	 @FindBy(className="todos-container")
	 private WebElement teamPageToDoContainer;
	 
	 @FindBy(css="[ng-show=\"subNavigation.canShowSelf\"]")
	 private WebElement teamPageSubNavigationBar;
	 
	 @FindBy(css="[ng-click=\"setMode('availability')\"]")
	 private WebElement teamPageCoverageAvailabilityButton;
	 
	 @FindBy(css="teamPageAvailabilityTeamMemberDataSection")
	 private WebElement teamPageAvailabilityTeamMemberDataSection;
	 
	 @FindBy(css="[ng-click=\"openProfileModal(w.worker)\"]")
	 private WebElement teamPageChangeInTeamMemberAvailability;
	 
	 @FindBy(className="coverage-info-row")
	 private WebElement teamPageCoverageLabelData;
	 
	 @FindBy(css="[ng-style=\"dropDownButtonStyle()\"]")
	 private WebElement teamPageCoverageJobTitleFilterButton;
	 
	 @FindBy(css="[ng-click=\"selectChoice($event, choice)\"]")
	 private WebElement teamPageCoverageDropdownList;
	 
	 @FindBy(className="coverage-info-row")
	 private WebElement teamPageCoverageLabelSection;
	 
	 @FindBy(css="[ng-click=\"setMode('timeoff')\"]")
	 private WebElement teamPageCoverageTimeOff;
	 
	 @FindBy(css="ul.dropdown-menu.dropdown-menu-right")
	 private WebElement teamPageCoverageDropdownMenu;
	 
	 @FindBy(className="sub-navigation-view-link")
	 private List<WebElement> TeamPageHeaderTabs;
	 
	 @FindBy (css = "div.console-navigation-item-label.Team")
	 private WebElement teamConsoleName;
	
	public ConsoleTeamPage() {
		PageFactory.initElements(getDriver(), this);
    }
    
    public void goToTeam() throws Exception
	{
    	
    	if(isElementLoaded(goToTeamButton))
    	{
    		activeConsoleName = teamConsoleName.getText();
    		click(goToTeamButton);
    	}else{
    		SimpleUtils.fail("Team button not present on the page",false);
    	}
	}
    
    public boolean isTeam() throws Exception
	{
    	if(isElementLoaded(rosterBodyElement))
    	{
    		return true;
    	}else{
    		return false;
    	}
    	
	}
    
    public void goToCoverage() throws Exception
    {
    	if(isElementLoaded(TeamPageHeaderTabs.get(0)))
    	{
    		for(WebElement teamPageHeaderTabElement : TeamPageHeaderTabs)
    		{
    			if(teamPageHeaderTabElement.getText().contains("COVERAGE"))
    			{
        			click(teamPageHeaderTabElement);
    			}
    		}
    	}
    }
    
    public boolean isCoverage() throws Exception
    {
    	if(isElementLoaded(coverageViewElement))
    	{
    		return true;
    	}else{
    		return false;
    	}
    	
    }
    
    public void verifyTeamPage(boolean isTeamPage){
    	if(isTeamPage){
    		SimpleUtils.pass("Team Page Loaded Successfully!");
    	}else{
    		SimpleUtils.fail("Team Page not loaded Successfully!",true);
    	}
    }
    
    public void verifyCoveragePage(boolean isCoveragePage){
    	if(isCoveragePage){
    		SimpleUtils.pass("Team Page - Coverage Section Loaded Successfully!");
    	}else{
    		SimpleUtils.fail("Team Page - Coverage Section not Loaded Successfully for LegionTech Environment! (Jira Ticket :4978) ",false);
    	}
    }
    
}
