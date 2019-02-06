package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.legion.pages.BasePage;
import com.legion.pages.TeamPage;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

public class ConsoleTeamPage extends BasePage implements TeamPage{
	
	private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
	private static HashMap<String, String> searchDetails = JsonUtil.getPropertiesFromJsonFile("src/test/resources/searchDetails.json");
	int teamMemberRecordsCount=0;
	
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
	 @FindBy(css="[class='console-navigation-item-label Team']")
	 private WebElement goToTeamTab;
		
	 @FindBy(css=".search-input-box")
	 private WebElement searchTextBox;
		
	 @FindBy(css=".search-icon")
	 private WebElement searchBtn;
		
	 @FindBy(css="[class='sub-navigation-view-link active']")
	 private WebElement activeTab;
		
	 @FindBy(xpath="//span[contains(text(),'Coverage')]")
	 private WebElement goToCoverageTab;
		
	 @FindBy(css="[class='tmroster ng-scope']")
	 private WebElement rosterLoading;
		
	 @FindBy(css=".title.ng-binding")
	  private List<WebElement> jobTitle;
		
	 @FindBy(css="div.coverage-view.ng-scope")
	 private WebElement coverageLoading;
		
	 @FindBy(css=".fa-angle-right.sch-calendar-navigation-arrow")
	 private  WebElement coverageNextArrow;
		
	 @FindBy(css=".fa-angle-left.sch-calendar-navigation-arrow")
	 private WebElement coveragePreviousArrow;
		
	 @FindBy(css=".coverage-title")
	 private WebElement coverageTitle;
		
	 @FindBy(css=".count.ng-binding")
	 private WebElement teamTabSize;	
	 
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
    
  //Check the presence of desired workrole in the TeamPage
  	public boolean checkRole(String key){
  		for (WebElement jobTitles : jobTitle){
  			if(key.equalsIgnoreCase(jobTitles.getText())){
  				return true;
  			}
  		}
  	    return true;
  	}
  	
  	
  	//Check the count of Team Members shown on Team Page matches no. of TM records
  	public void teamMemberCountEqualsNoOfTMRecord(){
  		String noOfMemberShown=teamTabSize.getText().replaceAll("\\p{P}","");
  		int countOfTMShown = Integer.parseInt(noOfMemberShown);
  		SimpleUtils.assertOnFail("Count of TM records exists on Team tab matches the count shown = " +jobTitle.size() , countOfTMShown==calcListLength(jobTitle), true);
  	}

  	@Override
  	public void performSearchRoster(List<String> list) throws Exception {
  		// TODO Auto-generated method stub
  		if(isElementLoaded(rosterLoading)){
  			waitForSeconds(3);
  			SimpleUtils.pass("Team ROASTER Page Loaded Successfully");
  			teamMemberCountEqualsNoOfTMRecord();
  			for(int j=0;j<list.size();j++){
  				//noOfMembers++;
  				int count=0;
  				String key=(String) list.get(j);
  				boolean bolCheckRole = checkRole(key);
  				if(bolCheckRole == true){
  					searchTextBox.sendKeys(key);
  					searchBtn.click();
  					int Size=jobTitle.size();
  						for (int i=0;i<jobTitle.size();i++){
  							if(key.equalsIgnoreCase(jobTitle.get(i).getText()) || (jobTitle.get(i).getText()).contains(key)){
  								count=count+1;
  							}else{ 
  								SimpleUtils.fail("Incorrect Search Result",false);
  							}	
  						}
  						if(Size>0){
  							if(Size==count){
  								SimpleUtils.pass("No. of Search Result for "+key+ " JobTitle is " +count);
  							}else{
  								SimpleUtils.fail("Search result is not correct",false);
  							}
  						}else{
  							SimpleUtils.report(key+" work role is not deployed to this environment");
  						}
  				}
  				searchTextBox.clear();
  			}	
  		}else{
  			SimpleUtils.fail("Page not loaded successfully",false);
  		}
  	}
  	
  	
  	@Override
  	public void coverage() {
  		// TODO Auto-generated method stub
  		try {
  			if(isElementLoaded(goToCoverageTab))
  			{
  				SimpleUtils.pass("Coverage tab present on Team Page");
  				goToCoverageTab.click();
  				if(isElementLoaded(coverageLoading)){
  					SimpleUtils.pass("Coverage Loaded Successfully for current week "+coverageTitle.getText());
  				}else{
  					SimpleUtils.fail("Coverage not-loaded for "+coverageTitle.getText(),false);
  				}
  			
  			}else{
  				SimpleUtils.fail("Coverage tab not present on Team Tab",false);
  			}
  		}catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  	}
  		
  		@Override
  		public void coverageViewToPastOrFuture(String nextWeekView, int weekCount)
  		{
  			for(int i = 0; i < weekCount; i++)
  			{
  				if(nextWeekView.toLowerCase().contains("next") || nextWeekView.toLowerCase().contains("future"))
  				{
  					try {
  						if(isElementLoaded(coverageNextArrow)){
  							coverageNextArrow.click();
  							SimpleUtils.pass("Coverage page for next week loaded successfully! "+coverageTitle.getText());
  						}
  					}
  					catch (Exception e) {
  						SimpleUtils.fail("Coverage page for Next Week Arrows Not Loaded/Clickable after ' "+coverageTitle.getText()+ "'",false);
  					}
  				}
  			}
  		}
    
//    public boolean isTeam() throws Exception
//	{
//    	if(isElementLoaded(rosterBodyElement))
//    	{
//    		return true;
//    	}else{
//    		return false;
//    	}
//    	
//	}
//    
//    public void goToCoverage() throws Exception
//    {
//    	if(isElementLoaded(TeamPageHeaderTabs.get(0)))
//    	{
//    		for(WebElement teamPageHeaderTabElement : TeamPageHeaderTabs)
//    		{
//    			if(teamPageHeaderTabElement.getText().contains("COVERAGE"))
//    			{
//        			click(teamPageHeaderTabElement);
//    			}
//    		}
//    	}
//    }
//    
//    public boolean isCoverage() throws Exception
//    {
//    	if(isElementLoaded(coverageViewElement))
//    	{
//    		return true;
//    	}else{
//    		return false;
//    	}
//    	
//    }
//    
//    public void verifyTeamPage(boolean isTeamPage){
//    	if(isTeamPage){
//    		SimpleUtils.pass("Team Page Loaded Successfully!");
//    	}else{
//    		SimpleUtils.fail("Team Page not loaded Successfully!",true);
//    	}
//    }
//    
//    public void verifyCoveragePage(boolean isCoveragePage){
//    	if(isCoveragePage){
//    		SimpleUtils.pass("Team Page - Coverage Section Loaded Successfully!");
//    	}else{
//    		SimpleUtils.fail("Team Page - Coverage Section not Loaded Successfully for LegionTech Environment! (Jira Ticket :4978) ",false);
//    	}
//    }
//    
}
