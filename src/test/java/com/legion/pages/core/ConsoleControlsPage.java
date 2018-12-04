package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.legion.pages.BasePage;
import com.legion.pages.ControlsPage;
import com.legion.utils.SimpleUtils;

public class ConsoleControlsPage extends BasePage implements ControlsPage{
	
	@FindBy (css = "div.console-navigation-item-label.Controls")
	private WebElement controlsConsoleName;
	
	@FindBy (css = ".lg-location-chooser__global.ng-scope")
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
			controlsConsoleName.click();
			SimpleUtils.fail("Control not Loaded1", true);
			checkElementVisibility(globalIconControls);
			SimpleUtils.fail("Control not Loaded2", true);
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
	
	
	

}
