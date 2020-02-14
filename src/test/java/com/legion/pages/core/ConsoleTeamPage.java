package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import java.util.HashMap;
import java.util.List;

import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.legion.pages.BasePage;
import com.legion.pages.TeamPage;
import com.legion.tests.core.TeamTestKendraScott2.timeOffRequestAction;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

public class ConsoleTeamPage extends BasePage implements TeamPage{
	
	private static Map<String, String> propertyMap = SimpleUtils.getParameterMap();
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
	 
	 @FindBy(css="input.search-input-box")
    private WebElement teamMemberSearchBox;

    @FindBy(css="span.name")
    private List<WebElement> teamMembersList;
    @FindBy(css="div.timeoff-requests-request.row-fx")
    private List<WebElement> timeOffRequestRows;

    @FindBy(css="span.request-buttons-approve")
    private WebElement timeOffApproveBtn;

    @FindBy(css="button.lgn-action-button-success")
    private WebElement timeOffRequestApprovalCOnfirmBtn;

    @FindBy(css="img[src*=\"img/legion/todos-none\"]")
    private WebElement toDoBtnToOpen;

    @FindBy(css="div[ng-click=\"closeTodoPanelClick()\"]")
    private WebElement toDoBtnToClose;

    @FindBy(css="div[ng-show=\"show\"]")
    private WebElement toDoPopUpWindow;

    @FindBy(css="//div[@ng-show='show']//h1[contains(text(),'TEAM')]")
	private WebElement toDoPopUpWindowLabel;

    @FindBy(css="todo-card[todo-type=\"todoType\"]")
    private List<WebElement> todoCards;

    @FindBy(css="a[ng-click=\"goRight()\"]")
    private WebElement nextToDoCardArrow;

    @FindBy(css="button.lgn-action-button-success")
    private WebElement confirmTimeOffApprovalBtn;

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
    
   //added by Gunjan
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

  		public boolean rosterTeamLoading() throws Exception{
  			boolean flag=false;
  			goToTeam();
  			if(isElementLoaded(rosterBodyElement)){
					SimpleUtils.pass("Roster sub-tab of team tab loaded successfully");
					flag=true;
					return flag;
  			}else{
				SimpleUtils.fail("Roster sub-tab of team tab not loaded successfully",true);
  			}
			return flag;

  		}

  		public boolean coverageTeamLoading() throws Exception{
  			boolean flag=false;
  			if(isElementLoaded(goToCoverageTab))
  			{
  				SimpleUtils.pass("Coverage tab present on Team Page");
  				goToCoverageTab.click();
  				if(isElementLoaded(coverageLoading)){
  					SimpleUtils.pass("Coverage Sub Tab of Team tab Loaded Successfully");
  					flag=true;
  					return flag;
  				}else{
  					SimpleUtils.fail("Coverage Sub Tab of Team tab Not Loaded Successfully",true);
  				}

  			}else{
  				SimpleUtils.fail("Coverage tab not present on Team Tab",true);
  			}
			return flag;

  		}

		@Override
		public boolean loadTeamTab() throws Exception {
			// TODO Auto-generated method stub
			boolean flag=false;
			boolean resultrosterTeamLoading = rosterTeamLoading();
			boolean resultcoverageTeamLoading = coverageTeamLoading();
			if(resultrosterTeamLoading == true && resultcoverageTeamLoading == true){
				SimpleUtils.pass("Team tab loaded successfully");
				flag = true;
				return flag;
			}else {
				SimpleUtils.fail("Team tab not loaded successfully",true);
			}
			return flag;
		}




		@Override
		public void searchAndSelectTeamMemberByName(String username) throws Exception {
			boolean isteamMemberFound = false;
			if(isElementLoaded(teamMemberSearchBox, 10)) {
				teamMemberSearchBox.sendKeys(username);
				waitForSeconds(2);
				if(teamMembersList.size() > 0) {
					for(WebElement teamMember : teamMembersList) {
						if(teamMember.getText().toLowerCase().contains(username.toLowerCase())) {
							click(teamMember);
							isteamMemberFound = true;
							SimpleUtils.pass("Team Page: Team Member '"+username+"' selected Successfully.");;
							break;
						}
					}
				}
			}
			if(!isteamMemberFound)
				SimpleUtils.fail("Team Page: Team Member '"+username+"' not found.", false);
		}

		@Override
		public void approvePendingTimeOffRequest() throws Exception {
			String pendingStatusLabel = "PENDING";
			if(timeOffRequestRows.size() > 0) {
				for(WebElement timeOffRequestRow : timeOffRequestRows) {
					if(timeOffRequestRow.getText().toLowerCase().contains(pendingStatusLabel.toLowerCase())) {
						click(timeOffRequestRow);
						if(isElementLoaded(timeOffApproveBtn)) {
							WebElement timeOffRequestDuration = timeOffRequestRow.findElement(By.cssSelector("div.request-date"));
							click(timeOffApproveBtn);
							if(isElementLoaded(timeOffRequestApprovalCOnfirmBtn))
								click(timeOffRequestApprovalCOnfirmBtn);
							SimpleUtils.pass("Team Page: Time Off Request for the duration '"
									+timeOffRequestDuration.getText().replace("\n", "")+"' Approved.");
						}
					}
				}
			}
			else
				SimpleUtils.fail("Team Page: No Time off request found.", false);
		}

		@Override
		public int getPendingTimeOffRequestCount() throws Exception {
			String pendingStatusLabel = "PENDING";
			int pendingRequestCount = 0;
			if(timeOffRequestRows.size() > 0) {
				for(WebElement timeOffRequestRow : timeOffRequestRows) {
					if(timeOffRequestRow.getText().toLowerCase().contains(pendingStatusLabel.toLowerCase())) {
						pendingRequestCount = pendingRequestCount + 1;
					}
				}
			}
			return pendingRequestCount;
		}

		@Override
		public void openToDoPopupWindow() throws Exception {
		waitForSeconds(2);
	 	if(isElementLoaded(toDoBtnToOpen,5)) {
				click(toDoBtnToOpen);
//				Thread.sleep(1000);
				if(isToDoWindowOpened())
					SimpleUtils.pass("Team Page: 'ToDo' popup window loaded successfully.");
				else
					SimpleUtils.fail("Team Page: 'ToDo' popup window not loaded.", false);
			}
		}

		@Override
		public void closeToDoPopupWindow() throws Exception {
			if(isElementLoaded(toDoBtnToClose)) {
				click(toDoBtnToClose);
				Thread.sleep(1000);
				if(! isToDoWindowOpened())
					SimpleUtils.pass("Team Page: 'ToDo' popup window closed successfully.");
				else
					SimpleUtils.fail("Team Page: 'ToDo' popup window not closed.", false);
			}
		}

		public boolean isToDoWindowOpened() throws Exception{
			if(isElementLoaded(toDoPopUpWindow,5) && areListElementVisible(todoCards,5)) {
				if(toDoPopUpWindow.getAttribute("class").contains("is-shown"))
					return true;
			}
			return false;
		}
		

		@Override
		public void approveOrRejectTimeOffRequestFromToDoList(String userName, String timeOffStartDuration,
				String timeOffEndDuration, String action) throws Exception{
			boolean isTimeOffRequestToDoCardFound = false;
			String timeOffRequestCardText = "TIME OFF REQUEST";
			String timeOffStartDate = timeOffStartDuration.split(", ")[1];
			String timeOffEndDate =  timeOffEndDuration.split(", ")[1];
			if(isElementLoaded(todoCards.get(0))) {
				for(WebElement todoCard :todoCards) {
					if(isElementLoaded(nextToDoCardArrow, 10) && !todoCard.isDisplayed())
						click(nextToDoCardArrow);
					if(todoCard.getText().toLowerCase().contains(timeOffRequestCardText.toLowerCase())) {
						if(todoCard.getText().toLowerCase().contains(timeOffStartDate.toLowerCase())
								&& todoCard.getText().toLowerCase().contains(timeOffEndDate.toLowerCase())) {
							isTimeOffRequestToDoCardFound = true;
							if(action.toLowerCase().contains(timeOffRequestAction.Approve.getValue().toLowerCase())) {
								WebElement timeOffApproveButton = todoCard.findElement(By.cssSelector("a[ng-click=\"askConfirm('approve')\"]"));
								if(isElementLoaded(timeOffApproveButton)) {
									click(timeOffApproveButton);
									if(isElementLoaded(confirmTimeOffApprovalBtn)) {
										click(confirmTimeOffApprovalBtn);
										SimpleUtils.pass("Team Page: Time Off Request 'Approved' successfully for ToDo list.");
									}
								}
								else
									SimpleUtils.fail("Team Page: ToDo list time off request 'Approve' button not found.", false);
							}
							else if(action.toLowerCase().contains(timeOffRequestAction.Reject.getValue().toLowerCase())) {
								WebElement timeOffRejectButton = todoCard.findElement(By.cssSelector("a[ng-click=\"askConfirm('reject')\"]"));
								if(isElementLoaded(timeOffRejectButton)) {
									click(timeOffRejectButton);
									WebElement confirmRejectRequestBtn = todoCard.findElement(By.cssSelector("a[ng-click=\"action('reject')\"]"));
									if(isElementLoaded(confirmRejectRequestBtn)) {
										click(confirmRejectRequestBtn);
										SimpleUtils.pass("Team Page: Time Off Request 'Rejected' successfully for ToDo list.");
									}
								}
								else
									SimpleUtils.fail("Team Page: ToDo list time off request 'Reject' button not found.", false);
							}
						}
					}
				}
				if(! isTimeOffRequestToDoCardFound)
					SimpleUtils.fail("Team Page: ToDo list Time Off Request not found with given details.", false);
			}
			else
				SimpleUtils.fail("Team Page: ToDo cards not loaded.", false);
		}

	//Added by Nora
	@FindBy (className = "loading-icon")
	private WebElement teamTabLoadingIcon;
	@FindBy(css="div.row-container div.row.ng-scope")
	private List<WebElement> teamMembers;
	@FindBy (className = "lgnToggleIconButton")
	private WebElement addNewMemberButton;
	@FindBy (className = "col-sm-6")
	private List<WebElement> sectionsOnAddNewTeamMemberTab;
	@FindBy (css = "label[for=\"dateHired\"] img")
	private WebElement calendarImage;
	@FindBy (css = "div.single-calendar-picker.ng-scope")
	private WebElement calendar;
	@FindBy (className = "ranged-calendar__month-name")
	private WebElement currentMonthYear;
	@FindBy (css = "div.is-today")
	private WebElement todayHighlighted;

	@Override
	public void verifyTeamPageLoadedProperlyWithNoLoadingIcon() throws Exception {
		waitUntilElementIsInVisible(teamTabLoadingIcon);
		if(areListElementVisible(teamMembers, 60)){
			SimpleUtils.pass("Team Page is Loaded Successfully!");
		}else{
			SimpleUtils.fail("Team Page isn't Loaded Successfully", true);
		}
	}

	@Override
	public void verifyTheFunctionOfSearchTMBar(List<String> testStrings) throws Exception {
		if (isElementLoaded(searchTextBox)){
			if (testStrings.size() > 0){
				for (String testString : testStrings){
					searchTextBox.sendKeys(testString);
					if (teamMembers.size() > 0){
						for (WebElement teamMember : teamMembers){
							WebElement tr = teamMember.findElement(By.className("tr"));
							if (tr != null) {
								List<WebElement> respectiveElements = tr.findElements(By.tagName("div"));
								/*
								 * It will get the respective elements of Team Member, they are Image, Name, Job Title, Status, Badges and Actions.
								 */
								if (respectiveElements != null && respectiveElements.size() == 6) {
									String nameJobTitleStatus = respectiveElements.get(1).getText() + respectiveElements.get(2).getText()
											+ respectiveElements.get(3).getText();
									if (nameJobTitleStatus.toLowerCase().contains(testString)) {
										SimpleUtils.pass("Verified " + teamMember.getText() + " contains test string: " + testString);
									} else {
										SimpleUtils.fail("Team member: " + teamMember.getText() + " doesn't contain the test String: "
												+ testString, true);
									}
								}
							}
						}
					}else{
						SimpleUtils.fail("Team members failed to load!", true);
					}
					searchTextBox.clear();
				}
			}
		}
	}

	@Override
	public void verifyTheFunctionOfAddNewTeamMemberButton() throws Exception{
		final String personalDetails = "Personal Details";
		final String engagementDetails = "Engagement Details";
		final String titleClassName = "header-label";
		verifyTheVisibilityAndClickableOfPlusIcon();
		click(addNewMemberButton);
		if (areListElementVisible(sectionsOnAddNewTeamMemberTab, 10)){
			if (sectionsOnAddNewTeamMemberTab.size() == 2){
				SimpleUtils.pass("Two sections on Add New Team Member Tab loaded successfully!");
				WebElement personalElement = sectionsOnAddNewTeamMemberTab.get(0).findElement(By.className(titleClassName));
				WebElement engagementElement = sectionsOnAddNewTeamMemberTab.get(1).findElement(By.className(titleClassName));
				if (personalElement != null && engagementElement != null){
					if (personalDetails.equals(personalElement.getText()) && engagementDetails.equals(engagementElement.getText())){
						SimpleUtils.pass("Personal Details and Engagement Details sections loaded!");
					}else{
						SimpleUtils.fail("Personal Details and Engagement Details sections failed to load", true);
					}
				}
			}else{
				SimpleUtils.fail("Two sections on Add New Team Member Tab failed to load", false);
			}
		}
	}

	private void verifyTheVisibilityAndClickableOfPlusIcon() throws Exception {
		if (isElementLoaded(addNewMemberButton)){
			SimpleUtils.pass("\"+\" icon is visible on team tab!");
			if (isClickable(addNewMemberButton, 10)){
				SimpleUtils.pass("\"+\" icon is clickable on team tab!");
			}else{
				SimpleUtils.fail("\"+\" icon isn't clickable on team tab!", true);
			}
		}else{
			SimpleUtils.fail("\"+\" icon is visible on team tab!", false);
		}
	}

	@Override
	public void verifyTheMonthAndCurrentDayOnCalendar(String currentDateForSelectedLocation) throws Exception{
		String colorOnWeb = "#fb7800";
		if (isClickOnCalendarImageSuccessfully()){
			if (isElementLoaded(currentMonthYear) && isElementLoaded(todayHighlighted)){
				String currentDateOnCalendar = currentMonthYear.getText() + " " + todayHighlighted.getText();
				String color = todayHighlighted.getCssValue("color");
				/*
				 * color css value format: rgba(251, 120, 0, 1), need to convert it to Hex format
				 */
				if (color.contains("(") && color.contains(")") && color.contains(",")){
					String[] rgba = color.substring(color.indexOf("(") + 1, color.indexOf(")")).split(",");
					String colorHex = awtColorToWeb(rgba);
					if (colorHex.equals(colorOnWeb)){
						SimpleUtils.pass("Verified the color of current day is correct!");
					}else{
						SimpleUtils.fail("Failed to verify the color, expected is: " + colorOnWeb + " actual is: "
						+ colorHex, true);
					}
				}
				if (currentDateForSelectedLocation.equals(currentDateOnCalendar)){
					SimpleUtils.pass("It displays the calendar for current month and current day!");
				}else{
					SimpleUtils.fail("It doesn't display the calendar for current month and current day, current day is: "
							+ currentDateForSelectedLocation + ", but calendar displayed day is: " + currentDateOnCalendar, true);
				}
			}
		}
	}

	private String convertIntToHexString(int value){
		String hexString = Integer.toHexString(value);
		return hexString.length()== 1 ? "0"+ hexString : hexString;
	}

	private String awtColorToWeb(String[] rgba) {
		StringBuilder builder = new StringBuilder();
		try {
			if (rgba.length == 4) {
				builder.append("#");
				/*
				 * Need to convert the r, g, b.
				 */
				for (int i = 0; i < 3; i++) {
					builder.append(convertIntToHexString(Integer.parseInt(rgba[i].trim())));
				}
			}
		}catch (Exception e){
			SimpleUtils.fail("Convert failed!", false);
		}
		return builder.toString();
	}

	private boolean isClickOnCalendarImageSuccessfully() throws Exception {
		boolean isSuccess = false;
		if (isElementLoaded(calendarImage)){
			click(calendarImage);
			if (isElementLoaded(calendar)){
				isSuccess = true;
				SimpleUtils.pass("Click on Calendar Image, Calendar shows successfully!");
			}else{
				SimpleUtils.fail("Failed to show the Calendar", true);
			}
		}else{
			SimpleUtils.fail("Calendar Image failed to show.", true);
		}
		return isSuccess;
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
