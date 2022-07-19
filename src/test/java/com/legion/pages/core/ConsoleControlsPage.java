package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import cucumber.api.java.ro.Si;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.legion.pages.BasePage;
import com.legion.pages.ControlsPage;
import com.legion.utils.SimpleUtils;

public class ConsoleControlsPage extends BasePage implements ControlsPage{
	
	@FindBy (css = "div.console-navigation-item-label.Controls")
	private WebElement controlsConsoleName;
	
	@FindBy (css = ".lg-new-location-chooser__global.ng-scope")
	private WebElement globalIconControls;
	@FindBy (css = ".center.ng-scope")
	private WebElement controlsPage;
	
	public ConsoleControlsPage(){
		PageFactory.initElements(getDriver(), this);
	}

	@Override
	public void gotoControlsPage() throws Exception {
		// TODO Auto-generated method stub
		if(isElementLoaded(controlsConsoleName)){
			clickTheElement(controlsConsoleName);
			//SimpleUtils.fail("Control not Loaded1", true);
			checkElementVisibility(globalIconControls);
			//SimpleUtils.fail("Control not Loaded2", true);
			SimpleUtils.pass("Controls loaded successfully and Global icon present");
		}else{
			SimpleUtils.fail("Control not Loaded", false);
		}
		
	}
	
	@Override
	public void clickGlobalSettings(){
		//To do add try catch statement
		try{
			globalIconControls.click();
			SimpleUtils.pass("Navigating back to Dashboard from Controls after clicking on Global icon on Controls");
		}catch (Exception e) {
  			// TODO Auto-generated catch block
			SimpleUtils.fail("Not able to click on global icon of Controls page", false);
  		}
		
	}

	@Override
	public void clickOnConsoleInsightPage() throws Exception {
		if(isElementLoaded(controlsConsoleName,5)) {
			click(controlsConsoleName);
			if (controlsConsoleName.findElement(By.xpath("./..")).getAttribute("class").contains("active"))
				SimpleUtils.pass("Controls Page: Click on Controls console menu successfully");
			else
				SimpleUtils.fail("Controls Page: It doesn't navigate to Controls console menu after clicking", false);
		} else
			SimpleUtils.fail("Controls Console Menu not loaded Successfully!", false);
	}

	@FindBy(css = "[title='Tasks and Work Roles']")
	private WebElement tasksAndWorkRoles;
	@Override
	public void goToTaskAndWorkRolePage() throws Exception{
		if (isElementLoaded(tasksAndWorkRoles, 5)) {
			click(tasksAndWorkRoles);
			SimpleUtils.pass("Tasks and work roles is clickable");
		}else
			SimpleUtils.fail("tasks and work roles loaded failed",false);
	}

	@FindBy(css = "div.lg-tabs__nav-item.ng-binding.ng-scope:nth-child(2)")
	private WebElement workRoles;
	@Override
	public void goToWorkRolePage() throws Exception{
		if (isElementLoaded(workRoles, 5)) {
			click(workRoles);
			SimpleUtils.pass("Work roles is clickable");
		}else
			SimpleUtils.fail("Work roles loaded failed",false);
	}

	@FindBy(css = "lg-button[label='Auto']>button")
	private WebElement firstWorkRole;
	@Override
	public void goToFirstWorkRoleDetail() throws Exception{
		if (isElementLoaded(firstWorkRole, 5)) {
			click(firstWorkRole);
			SimpleUtils.pass("Add work role button is clickable");
		}else
			SimpleUtils.fail("Add wWork role button loaded failed",false);
	}

	@FindBy(xpath = "//rule-container[3]/div/div/div[1]/div[2]/span[1]/img")
	private WebElement assignmentRuleAddButton;
	@FindBy(id = "workRoleConstraintDropDown")
	private WebElement teamMemberTitleButton;

	@Override
	public void goToTeamMemberSearchBox() throws Exception{
		assignmentRuleAddButton.click();
		teamMemberTitleButton.click();
	}
}
