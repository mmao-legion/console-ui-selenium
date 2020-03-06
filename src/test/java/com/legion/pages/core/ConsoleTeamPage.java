package com.legion.pages.core;

import java.lang.reflect.Array;
import java.net.SocketImpl;
import java.nio.file.WatchEvent;
import java.text.SimpleDateFormat;
import java.util.*;

import freemarker.template.SimpleDate;
import net.bytebuddy.TypeCache;
import net.sourceforge.htmlunit.corejs.javascript.EcmaError;
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
import org.openqa.selenium.support.ui.Select;

import static com.legion.utils.MyThreadLocal.*;

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
	@FindBy (css = "lgn-action-button.change-location-button button")
	private WebElement transferButton;
	@FindBy (className = "location-card-image-name-container")
	private List<WebElement> locationCards;
	@FindBy (className = "location-card-image-box")
	private List<WebElement> locationImages;
	@FindBy (className = "lgnCheckBox")
	private WebElement temporaryTransferButton;
	@FindBy (className = "check-image")
	private WebElement checkImage;
	@FindBy (css="div.row-container span.name")
	private List<WebElement> teamMemberNames;
	@FindBy (className = "transfer-heading")
	private List<WebElement> transferTitles;
	@FindBy (className = "lgncalendar")
	private List<WebElement> transferCalendars;
	@FindBy (css = "a.pull-left")
	private WebElement backArrow;
	@FindBy (css = "a.pull-right")
	private WebElement forwardArrow;
	@FindBy (css = "div.real-day")
	private List<WebElement> realDays;
	@FindBy (id = "dateHired")
	private WebElement dateHiredInput;
	@FindBy (className = "current-day")
	private WebElement currentDay;
	@FindBy (css = "div.day")
	private List<WebElement> daysOnCalendar;
	@FindBy (css = "button.save-btn.pull-right")
	private WebElement applyOnTransfer;
	@FindBy (className = "lgn-alert-modal")
	private WebElement confirmPopupWindow;
	@FindBy (className = "lgn-action-button-success")
	private WebElement confirmButton;
	@FindBy (className = "lgn-action-button-default")
	private WebElement cancelButton;
	@FindBy (css = "span.lgn-alert-message")
	private List<WebElement> alertMessages;
	@FindBy (css = "div.lgn-alert-message")
	private WebElement popupMessage;
	@FindBy (css = "div:nth-child(7) > div.value")
	private WebElement homeStoreLocation;
	@FindBy (css = "pre.change-location-msg")
	private WebElement changeLocationMsg;
	@FindBy (css = "div.badge-section div.profile-heading")
	private WebElement badgeTitle;
	@FindBy (css = "div.collapsible-title-open span.ng-binding")
	private WebElement profileTabTitle;
	@FindBy (className = "lgn-tm-manage-badges")
	private WebElement manageBadgesWindow;
	@FindBy (className = "lgnCheckBox")
	private List<WebElement> badgeCheckBoxes;
	@FindBy (className = "one-badge")
	private List<WebElement> badgesOnProfile;
	@FindBy (css = "div.one-badge path")
	private List<WebElement> otherBadges;
	@FindBy (css = "div.one-badge polygon")
	private List<WebElement> starBadges;
	@FindBy (css = "button[ng-switch-when=\"invite\"]")
	private List<WebElement> inviteButtons;
	@FindBy (className = "modal-content")
	private WebElement inviteTMWindow;
	@FindBy (id = "email")
	private WebElement emailInput;
	@FindBy (className = "help-block")
	private WebElement emailErrorMsg;
	@FindBy (css = "section[ng-form=\"inviteTm\"]")
	private WebElement inviteTMSection;
	@FindBy (css = "button.pull-left")
	private WebElement cancelInviteButton;
	@FindBy (css = "button.btn-success")
	private WebElement sendInviteButton;
	@FindBy (css = "input[placeholder=\"First\"]")
	private WebElement firstNameInput;
	@FindBy (css = "input[placeholder=\"Last\"]")
	private WebElement lastNameInput;
	@FindBy (css = "input[name=\"email\"]")
	private WebElement emailInputTM;
	@FindBy (css = "input[name=\"phone\"]")
	private WebElement phoneInput;
	@FindBy (css = "input[placeholder*=\"Employee\"]")
	private WebElement employeeIDInput;
	@FindBy (css = "select[ng-model*=\"role\"]")
	private WebElement jobTitleSelect;
	@FindBy (css = "select[ng-model*=\"Status\"]")
	private WebElement engagementStatusSelect;
	@FindBy (css = "select[ng-model*=\"hourly\"]")
	private WebElement hourlySelect;
	@FindBy (css = "select[ng-model*=\"salaried\"]")
	private WebElement salariedSelect;
	@FindBy (css = "select[ng-model*=\"exempt\"]")
	private WebElement exemptSelect;
	@FindBy (className = "current-location-text")
	private WebElement homeStoreLabel;
	@FindBy (className = "btn-success")
	private WebElement saveTMButton;
	@FindBy (className = "contact-error")
	private WebElement contactErrorMsg;
	@FindBy (className = "count")
	private WebElement tmCount;
	@FindBy (className = "pull-left")
	private WebElement cancelButtonAddTM;
	@FindBy (css = "span.invitationStatus")
	private List<WebElement> invitationStatus;
	@FindBy (css = "lgn-action-button.invite-button button")
	private WebElement inviteButton;
	@FindBy (css = "button[ng-switch-when=\"activate\"]")
	private List<WebElement> activateButtons;
	@FindBy (css = "profile-management div.collapsible-title")
	private WebElement profileTab;
	@FindBy (css = "lgn-action-button[label=\"'ACTIVATE'\"] button")
	private WebElement activateButton;
	@FindBy (css = "div.activate")
	private WebElement activateWindow;
	@FindBy (css = "button.save-btn.pull-right")
	private WebElement applyButton;
	@FindBy (css = "lgn-action-button[label=\"'DEACTIVATE'\"] button")
	private WebElement deactivateButton;
	@FindBy (css = "lgn-action-button[label=\"'TERMINATE'\"] button")
	private WebElement terminateButton;
	@FindBy (css = "lgn-action-button[label=\"'CANCEL TERMINATE'\"] button")
	private WebElement cancelTerminateButton;
	@FindBy (css = "div.legion-status div.invitation-status")
	private WebElement onBoardedDate;
	@FindBy (css = "div.legion-status>div:nth-child(2)")
	private WebElement tmStatus;
	@FindBy (css = "lgn-action-button[label=\"'CANCEL ACTIVATE'\"] button")
	private WebElement cancelActivateButton;
	@FindBy (className = "modal-content")
	private WebElement deactivateWindow;
	@FindBy (css = "button[ng-switch-when=\"update\"]")
	private List<WebElement> updateInfoButtons;
	@FindBy (className = "location-date-selector")
	private WebElement removeWindow;
	@FindBy (css = "lgn-tm-engagement-quick div:nth-child(5)>div")
	private WebElement employeeID;
	@FindBy (css = "i.next-month")
	private WebElement nextMonthArrow;
	@FindBy (css = "lgn-action-button[label=\"'MANUAL ONBOARD'\"] button")
	private WebElement manualOnBoardButton;

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
		if (isElementLoaded(searchTextBox, 5)){
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
		if (isElementLoaded(addNewMemberButton, 10)){
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
			if (isElementLoaded(currentMonthYear, 5) && isElementLoaded(todayHighlighted, 5)){
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

	@Override
	public String selectATeamMemberToTransfer() throws Exception {
		String transfer = "TRANSFER";
		String teamMember = null;
		if (areListElementVisible(teamMemberNames, 30)){
			Random random = new Random();
			int randomIndex = random.nextInt(teamMemberNames.size() - 1);
			teamMember = teamMemberNames.get(randomIndex).getText();
			click(teamMemberNames.get(randomIndex));
			if (isElementLoaded(transferButton, 5)) {
				if (transfer.equals(transferButton.getText())) {
					SimpleUtils.pass("Find a Team Member that can be transferred!");
					moveToElementAndClick(transferButton);
				} else {
					/*
					 * If the user already transferred, cancel transfer it.
					 */
					if (isCancelTransferSuccess()) {
						click(transferButton);
					}
				}
			}
		}else{
			SimpleUtils.fail("Team Members didn't load successfully!", false);
		}
		return teamMember;
	}

	@Override
	public void selectATeamMemberToCancelTransfer() throws Exception {
		String cancelTransfer = "CANCEL TRANSFER";
		selectATeamMemberToViewProfile();
		if (isElementLoaded(transferButton, 5)) {
			if (cancelTransfer.equals(transferButton.getText())) {
				SimpleUtils.pass("Find a Team Member that can be cancel transferred!");
				moveToElementAndClick(transferButton);
			} else {
				transferTheHomeStoreLocationAndCancelTransfer();
			}
		} else {
			SimpleUtils.fail("TRANSFER button failed to load!", true);
		}
	}

	@Override
	public String selectATeamMemberToViewProfile() throws Exception {
		String teamMember = null;
		if (areListElementVisible(teamMemberNames, 15)) {
			Random random = new Random();
			int randomIndex = random.nextInt(teamMemberNames.size() - 1);
			teamMember = teamMemberNames.get(randomIndex).getText();
			click(teamMemberNames.get(randomIndex));
		} else {
			SimpleUtils.fail("Team Members are failed to load!", true);
		}
		return teamMember;
	}

	@Override
	public boolean verifyCancelTransferWindowPopup() throws Exception {
		boolean isPopup = false;
		String expectedMessage = "Are you sure you want to cancel the transfer to the new location?";
		String actualMessage = null;
		if (isElementLoaded(confirmPopupWindow, 5) && isElementLoaded(popupMessage, 5)) {
			actualMessage = popupMessage.findElement(By.tagName("span")).getText();
			if (expectedMessage.trim().equals(actualMessage.trim())){
				isPopup = true;
				SimpleUtils.pass("Cancel Transfer window pops up!");
			}else {
				SimpleUtils.fail("The Message on Cancel Transfer window is incorrect!", true);
			}
		} else {
			SimpleUtils.fail("Cancel Transfer pop-up window doesn't show!", true);
		}
		return isPopup;
	}

	@Override
	public boolean verifyTransferButtonEnabledAfterCancelingTransfer() throws Exception {
		boolean isEnabled = false;
		String transfer = "TRANSFER";
		if (isElementLoaded(confirmButton, 5)) {
			click(confirmButton);
			if (isElementLoaded(popupMessage, 5)) {
				if (isElementEnabled(transferButton, 10)) {
					if (transferButton.getText().equals(transfer)) {
						isEnabled = true;
						SimpleUtils.pass("TRANSFER button is enabled!");
					} else {
						SimpleUtils.fail("CANCEL TRANSFER button doesn't change to TRANSFER", true);
					}
				} else {
					SimpleUtils.fail("Cancel Transfer failed!", true);
				}
			}
		}
		return isEnabled;
	}

	@Override
	public void verifyHomeLocationAfterCancelingTransfer(String homeLocation) throws Exception {
		if (verifyCancelTransferWindowPopup()) {
			click(confirmButton);
			if (!isElementLoaded(changeLocationMsg, 10)){
				SimpleUtils.pass("Change Location message doesn't show!");
			}else {
				SimpleUtils.fail("Change Location message is still shown!", true);
			}
			if (isElementLoaded(homeStoreLocation, 5)) {
				if (homeStoreLocation.getText().contains(homeLocation)){
					SimpleUtils.pass("Home Store location is the previous one!");
				}else {
					SimpleUtils.fail("Home Store location isn't the previous one!", true);
				}
			}
		}else {
			SimpleUtils.fail("Cancel Transfer window does't show up!", true);
		}
	}

	@Override
	public boolean isProfilePageLoaded() throws Exception {
		boolean isLoaded = false;
		String profile = "Profile";
		if (isElementLoaded(profileTabTitle, 5)){
			if (profileTabTitle.getText().equals(profile)){
				isLoaded = true;
				SimpleUtils.pass("Profile Page loaded successfully!");
			} else {
				SimpleUtils.fail("Profile Page doesn't load successfully!", true);
			}
		}
		return isLoaded;
	}

	private void transferTheHomeStoreLocationAndCancelTransfer() throws Exception {
		moveToElementAndClick(transferButton);
		verifyHomeLocationCanBeSelected();
		verifyDateCanBeSelectedOnTransfer();
		isApplyButtonEnabled();
		verifyClickOnApplyButtonOnTransfer();
		verifyTheFunctionOfConfirmTransferButton();
		waitForSeconds(3);
		click(transferButton);
	}

	private boolean isCancelTransferSuccess() throws Exception {
		boolean isSuccess = false;
		String cancelTransfer = "CANCEL TRANSFER";
		String transfer = "TRANSFER";
		if (isElementLoaded(transferButton, 5) && transferButton.getText().equals(cancelTransfer)) {
			click(transferButton);
			if (isElementLoaded(confirmButton, 10)) {
				click(confirmButton);
				if (isElementLoaded(transferButton, 10)){
					if (transferButton.getText().equals(transfer)) {
						isSuccess = true;
						SimpleUtils.pass("Cancel Transfer Successfully!");
					}else {
						SimpleUtils.fail("CANCEL TRANSFER button doesn't change to TRANSFER", true);
					}
				}else {
					SimpleUtils.fail("Cancel Transfer failed!", true);
				}
			}else {
				SimpleUtils.fail("A pop-up window doesn't show!", true);
			}
		}else {
			SimpleUtils.fail("Cancel Transfer button doesn't Load!", true);
		}
		return isSuccess;
	}

	@Override
	public String verifyHomeLocationCanBeSelected() throws Exception {
		String selectedLocation = null;
		String attribute = "style";
		if (areListElementVisible(locationImages, 30) && areListElementVisible(locationCards, 30)) {
			Random random = new Random();
			int index = random.nextInt(locationCards.size() - 1);
			WebElement locationCard = locationCards.get(index);
			selectedLocation = locationCard.findElement(By.className("location-card-name-text")).getText();
			click(locationCard);
			if (locationCard.getAttribute(attribute) != null && !locationCard.getAttribute(attribute).isEmpty()){
				SimpleUtils.pass("Select one Location successfully!");
			}else{
				SimpleUtils.fail("Failed to select the Location!", true);
			}
		}else{
			SimpleUtils.fail("Location Cards Failed to load!", true);
		}
		return selectedLocation;
	}

	@Override
	public void verifyClickOnTemporaryTransferButton() throws Exception {
		if (isElementLoaded(temporaryTransferButton, 5)) {
			click(temporaryTransferButton);
			if (isElementLoaded(checkImage, 5)){
				SimpleUtils.pass("Temporary Transfer button is checked!");
			}else{
				SimpleUtils.fail("Temporary Transfer button isn't checked", true);
			}
		}else{
			SimpleUtils.fail("Temporary Transfer button doesn't load!", true);
		}
	}

	@Override
	public void verifyTwoCalendarsForCurrentMonthAreShown(String currentDate) throws Exception {
		String className = "month-header";
		verifyClickOnTemporaryTransferButton();
		if (areListElementVisible(transferTitles, 10) && areListElementVisible(transferCalendars, 10)){
			if (transferTitles.size() == 2 && transferCalendars.size() == 2){
				String monthYearLeft = transferCalendars.get(0).findElement(By.className(className)).getText().toLowerCase();
				String monthYearRight = transferCalendars.get(1).findElement(By.className(className)).getText().toLowerCase();
				if (currentDate.toLowerCase().contains(monthYearLeft) && currentDate.toLowerCase().contains(monthYearRight)) {
					SimpleUtils.pass("Two Calendars for current month are shown!");
				}else{
					SimpleUtils.fail("Two Calendars are not for current month!", true);
				}
			}else {
				SimpleUtils.fail("Calendar counts are incorrect!", true);
			}
		}else {
			SimpleUtils.fail("Calendars are failed to loade!", true);
		}
	}

	@Override
	public void verifyTheCalendarCanNavToPreviousAndFuture() throws Exception {
		String monthAndYear = null;
		String attribute = "value";
		if (isClickOnCalendarImageSuccessfully()){
			if (isElementLoaded(backArrow, 5) && isElementLoaded(forwardArrow, 5)){
				navigateToPreviousAndFutureDate(backArrow);
				navigateToPreviousAndFutureDate(forwardArrow);
				navigateToPreviousAndFutureDate(forwardArrow);
				monthAndYear = currentMonthYear.getText();
				if (areListElementVisible(realDays)){
					/*
					 * Generate a random to select a day!
					 */
					Random random = new Random();
					WebElement realDay = realDays.get(random.nextInt(realDays.size()));
					String day = realDay.getText();
					String expectedDate = monthAndYear.substring(0,3) + " " + day + ", "
							+ monthAndYear.substring(monthAndYear.length() - 4);
					click(realDay);
					String selectedDate = dateHiredInput.getAttribute(attribute);
					if (expectedDate.equals(selectedDate)) {
						SimpleUtils.pass("Selected a day successfully!");
					}else {
						SimpleUtils.fail("Selected day is inconsistent with the date shown in Date Hired!", true);
					}
				}
			}else {
				SimpleUtils.fail("Back and Forward arrows are failed to load!", true);
			}
		}else {
			SimpleUtils.fail("Click on Calendar image failed!", true);
		}
	}

	@Override
	public void verifyTheCurrentDateAndSelectOtherDateOnTransfer() throws Exception {
		String colorOnWeb = "#fb7800";
		if (areListElementVisible(transferCalendars, 10) && isElementLoaded(currentDay, 10)) {
			String color = currentDay.getCssValue("color");
			/*
			 * color css value format: rgba(251, 120, 0, 1), need to convert it to Hex format
			 */
			if (color.contains("(") && color.contains(")") && color.contains(",")){
				String[] rgba = color.substring(color.indexOf("(") + 1, color.indexOf(")")).split(",");
				String colorHex = awtColorToWeb(rgba);
				if (colorHex.equals(colorOnWeb)){
					SimpleUtils.pass("Current Day is Highlighted!");
				}else{
					SimpleUtils.fail("Current day isn't highlighted!", true);
				}
			}
			verifyDateCanBeSelectedOnTransfer();
		}else {
			SimpleUtils.fail("Calendar failed to load!", true);
		}
	}

	@Override
	public void verifyDateCanBeSelectedOnTransfer() throws Exception {
		String className = "selected-day";
		int nextDayIndex = 0;
		int maxIndex = 0;
		Random random = new Random();
		if (areListElementVisible(daysOnCalendar, 10)) {
			/*
			 * Select a future date to transfer.
			 */
			nextDayIndex = getSpecificDayIndex(currentDay) + 1;
			maxIndex = daysOnCalendar.size() - 1;
			int randomIndex = nextDayIndex + random.nextInt(maxIndex - nextDayIndex);
			WebElement randomElement = daysOnCalendar.get(randomIndex);
			click(randomElement);
			if (randomElement.getAttribute("class").contains(className)) {
				SimpleUtils.pass("Select a date successfully!");
			} else {
				SimpleUtils.fail("Failed to select a date!", true);
			}
		}else {
			SimpleUtils.fail("Days on calendar failed to load!", true);
		}
	}

	@Override
	public boolean isApplyButtonEnabled() throws Exception {
		boolean isEnabled = false;
		isEnabled = isElementEnabled(applyOnTransfer, 3);
		return isEnabled;
	}

	@Override
	public void verifyClickOnApplyButtonOnTransfer() throws Exception {
		if (isApplyButtonEnabled()) {
			SimpleUtils.pass("Apply Button on Transfer page is enabled!");
			click(applyOnTransfer);
		}else{
			SimpleUtils.fail("Apply Button on Transfer Page is still disabled!", true);
		}
	}

	@Override
	public void verifyTheMessageOnPopupWindow(String currentLocation, String selectedLocation, String teamMemberName) throws Exception {
		if (teamMemberName.contains("“") && teamMemberName.contains("”")){
			teamMemberName = teamMemberName.substring(teamMemberName.indexOf("“") + 1, teamMemberName.indexOf("”"));
		}else if (teamMemberName.contains(" ")){
			teamMemberName = teamMemberName.split(" ")[0];
		}
		String expectedShiftMessage = "from this date onwards will be converted to Open Shifts.";
		if (isElementLoaded(confirmPopupWindow, 10) && areListElementVisible(alertMessages, 10)) {
			if (alertMessages.size() == 2) {
				String transferMessage = alertMessages.get(0).getText();
				String shiftMessage = alertMessages.get(1).getText();
				if (transferMessage.contains(currentLocation) && transferMessage.contains(selectedLocation) &&
				shiftMessage.contains(teamMemberName) && shiftMessage.contains(expectedShiftMessage)){
					SimpleUtils.pass("Message on pop-up window shows correctly!");
				}else{
					SimpleUtils.fail("Message on pop-up window shows incorrectly", true);
				}
			}
		}else{
			SimpleUtils.fail("Pop-up window failed to show!", true);
		}
	}

	@Override
	public void verifyTheFunctionOfConfirmTransferButton() throws Exception {
		String successfulMessage = "Successfully transferred the Team Member";
		String cancelTransfer = "CANCEL TRANSFER";
		if (isElementLoaded(confirmPopupWindow, 10) && isElementLoaded(confirmButton, 10)) {
			click(confirmButton);
			if (isElementLoaded(popupMessage, 10)) {
				String message = popupMessage.getText();
				if (message.equals(successfulMessage)) {
					if (isElementLoaded(transferButton, 10)){
						if (transferButton.getText().equals(cancelTransfer)) {
							SimpleUtils.pass("Transfer Successfully!");
						}else {
							SimpleUtils.fail("Button doesn't change to CANCEL TRANSFER!", true);
						}
					}
				}else {
					SimpleUtils.fail("The pop-up message is incorrect!", true);
				}
			}
		}
	}

	@Override
	public void	verifyTheFunctionOfCancelTransferButton() throws Exception {
		String transfer = "TRANSFER";
		if (isElementLoaded(confirmPopupWindow, 10) && isElementLoaded(cancelButton, 10)) {
			click(cancelButton);
			if (isElementLoaded(transferButton, 10)){
				if (transferButton.getText().equals(transfer)) {
					SimpleUtils.pass("Cancel Transfer Successfully!");
				}else {
					SimpleUtils.fail("Button doesn't remain TRANSFER!", true);
				}
			}
		}else {
			SimpleUtils.fail("Cancel button doesn't show on pop-up Window!", true);
		}
	}

	@Override
	public void verifyTheHomeStoreLocationOnProfilePage(String location, String selectedLocation) throws Exception {
		String actualLocationMessage = null;
		String date = null;
		boolean isCorrectFormat = false;
		SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
		if (isElementLoaded(homeStoreLocation, 10) && isElementLoaded(changeLocationMsg, 10)) {
			if (homeStoreLocation.getText().contains(location)){
				SimpleUtils.pass("Home Store Location is not updating!");
			}else {
				SimpleUtils.fail("Home Store Location is changed!", true);
			}
			actualLocationMessage = changeLocationMsg.getText();
			if (actualLocationMessage.contains("-")) {
				String[] values = actualLocationMessage.split("-");
				if (values.length == 2) {
					date = values[1].trim();
					/*
					 * Check whether the date format is correct, eg: 02/20/2020
					 */
					isCorrectFormat = SimpleUtils.isDateFormatCorrect(date, format);
				}
			}
			/*
			 * Since some string has two blank spaces, so remove the blank space.
			 */
			selectedLocation = selectedLocation.replaceAll("\\s*", "");
			actualLocationMessage = actualLocationMessage.replaceAll("\\s*", "");
			if (actualLocationMessage.contains(selectedLocation) && isCorrectFormat) {
				SimpleUtils.pass("Change Location Message is correct!");
			}else {
				SimpleUtils.fail("Change Location Message is incorrect!", true);
			}
		}
	}

	@Override
	public void verifyTheFunctionOfEditBadges() throws Exception {
		String badges = "BADGES";
		int badgeCount = 0;
		if (isElementLoaded(badgeTitle, 5)) {
			if (badgeTitle.getText().equals(badges)) {
				WebElement editBadge = badgeTitle.findElement(By.tagName("i"));
				click(editBadge);
				if (isManageBadgesLoaded()) {
					badgeCount = selectAllTheBadges();
					confirmButton.click();
					if (areListElementVisible(badgesOnProfile, 5)) {
						if (badgesOnProfile.size() == badgeCount) {
							SimpleUtils.pass("Select the badges successfully!");
						}else{
							SimpleUtils.fail("Selected count is inconsistent with the showing count!", true);
						}
					}
				}
			}
		}else{
			SimpleUtils.fail("BADGES failed to load!", true);
		}
	}

	@Override
	public void verifyTheVisibleOfBadgesOnTeamRoster() throws Exception {
		boolean areBadgesLoaded = areListElementVisible(otherBadges, 15) || areListElementVisible(starBadges, 15);
		if (areBadgesLoaded) {
			SimpleUtils.pass("Badges are visible on Team Roster!");
		} else {
			SimpleUtils.fail("Badges are invisible on Team Roster!", true);
		}
	}

	@Override
	public boolean isInviteTeamMemberWindowLoaded() throws Exception {
		boolean isLoaded = false;
		if (isElementLoaded(inviteTMWindow, 5) && isElementLoaded(inviteTMSection, 5)) {
			isLoaded = true;
			SimpleUtils.pass("Invite Team Member Window sections are loaded!");
		}else {
			SimpleUtils.fail("Invite Team Member window failed to load!", false);
		}
		return isLoaded;
	}

	@Override
	public void verifyTheEmailFormatOnInviteWindow(List<String> testEmails) throws Exception {
		String regex = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}" +
				"\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,10}))$";
		String errorMessage = "Email is invalid.";
		if (isElementEnabled(emailInput, 5) && testEmails.size() > 0) {
			for (String testEmail : testEmails) {
				emailInput.clear();
				emailInput.sendKeys(testEmail);
				if (testEmail.matches(regex)) {
					if (isElementLoaded(emailErrorMsg, 5)){
						SimpleUtils.fail("Email: " + testEmail + "'s format is correct, but error message shows", true);
					}else {
						SimpleUtils.pass("Email: " + testEmail + "'s format is correct!");
					}
				}else{
					if (isElementLoaded(emailErrorMsg, 5)) {
						if (emailErrorMsg.getText().equals(errorMessage)) {
							SimpleUtils.pass("Email: " + testEmail + "'s format is incorrect, error message shows!");
						}
					}else {
						SimpleUtils.fail("Email: " + testEmail + "'s format is incorrect, but error message doesn't show!", true);
					}
				}
			}
		}else {
			SimpleUtils.fail("Email Input failed to load!", true);
		}
	}

	@Override
	public void selectATeamMemberToInvite() throws Exception {
		if (areListElementVisible(inviteButtons, 15)) {
			Random random = new Random();
			int index = random.nextInt(inviteButtons.size());
			click(inviteButtons.get(index));
		}else {
			SimpleUtils.fail("Invite Buttons are not shown!", true);
		}
	}

	@Override
	public boolean isSendAndCancelLoadedAndEnabledOnInvite() throws Exception {
		boolean isEnabled = false;
		if (isElementLoaded(cancelInviteButton, 5) && isElementLoaded(sendInviteButton, 5)) {
			SimpleUtils.pass("Cancel and Send buttons are Loaded!");
			if (isElementEnabled(cancelInviteButton, 5) && isElementEnabled(sendInviteButton, 5)) {
				SimpleUtils.pass("Cancel and Send buttons are available!");
				isEnabled = true;
			} else {
				SimpleUtils.fail("Cancel and Send buttons are not available!", true);
			}
		}else {
			SimpleUtils.fail("Cancel and Send buttons are failed to load!", true);
		}
		return isEnabled;
	}

	@Override
	public String addANewTeamMemberToInvite(Map<String, String> newTMDetails) throws Exception {
		String firstName = null;
		String successfulMsg = "The team member has been added.";
		String email = "Email";
		String phoneNumber = "Phone Number";
		firstName = checkAndFillInTheFieldsToCreateInviteTM(newTMDetails);
		if (isElementEnabled(saveTMButton, 5)) {
			SimpleUtils.pass("Save button on new TM page is enabled!");
			scrollToBottom();
			click(saveTMButton);
			if (isElementLoaded(popupMessage, 5)) {
				if (popupMessage.getText().equals(successfulMsg)) {
					SimpleUtils.pass("The New Team member is added successfully");
				}else {
					SimpleUtils.fail("The message is incorrect!", true);
				}
			}else {
				SimpleUtils.fail("Successful message doesn't show!", true);
			}
		} else {
			SimpleUtils.fail("Save Button is not enabled!", true);
		}
		return firstName;
	}

	@Override
	public void saveTheNewTeamMember() throws Exception {
		String successfulMsg = "The team member has been added.";
		if (isSaveButtonOnNewTMPageEnabled()) {
			scrollToBottom();
			click(saveTMButton);
			if (isElementLoaded(popupMessage, 5)) {
				if (popupMessage.getText().equals(successfulMsg)) {
					SimpleUtils.pass("The New Team member is added successfully");
				}else {
					SimpleUtils.fail("The message is incorrect!", true);
				}
			}else {
				SimpleUtils.fail("Successful message doesn't show!", true);
			}
		}
	}

	@Override
	public String fillInMandatoryFieldsOnNewTMPage(Map<String, String> newTMDetails, String mandatoryField) throws Exception {
		String email = "Email";
		String phoneNumber = "Phone Number";
		String firstName = newTMDetails.get("FIRST_NAME") + new Random().nextInt(200) + new Random().nextInt(200);
		firstNameInput.sendKeys(firstName);
		lastNameInput.sendKeys(newTMDetails.get("LAST_NAME"));
		if (mandatoryField.equals(email)) {
			emailInputTM.sendKeys(newTMDetails.get("EMAIL"));
		}
		if (mandatoryField.equals(phoneNumber)) {
			phoneInput.sendKeys(newTMDetails.get("PHONE"));
		}
		click(dateHiredInput);
		if (areListElementVisible(realDays, 5) && isElementLoaded(todayHighlighted, 5)) {
			click(todayHighlighted);
		}
		employeeIDInput.sendKeys( "E" + new Random().nextInt(200) + new Random().nextInt(200) + new Random().nextInt(200));
		selectByVisibleText(jobTitleSelect, newTMDetails.get("JOB_TITLE"));
		selectByVisibleText(engagementStatusSelect, newTMDetails.get("ENGAGEMENT_STATUS"));
		selectByVisibleText(hourlySelect, newTMDetails.get("HOURLY"));
		selectByVisibleText(salariedSelect, newTMDetails.get("SALARIED"));
		selectByVisibleText(exemptSelect, newTMDetails.get("EXEMPT"));
		return firstName;
	}

	@Override
	public void checkAddATMMandatoryFieldsAreLoaded(String mandatoryField) throws Exception {
		String email = "Email";
		String phoneNumber = "Phone Number";
		isElementLoadedAndPrintTheMessage(firstNameInput, "FIRST NAME Input");
		isElementLoadedAndPrintTheMessage(lastNameInput, "LAST NAME Input");
		if (mandatoryField.equals(email)) {
			isElementLoadedAndPrintTheMessage(emailInputTM, "EMAIL Input");
		}
		if (mandatoryField.equals(phoneNumber)) {
			isElementLoadedAndPrintTheMessage(phoneInput, "PHONE Input");
		}
		isElementLoadedAndPrintTheMessage(dateHiredInput, "DATE HIRED Input");
		isElementLoadedAndPrintTheMessage(employeeIDInput, "EMPLOYEE ID Input");
		isElementLoadedAndPrintTheMessage(jobTitleSelect, "JOB TITLE SELECT");
		isElementLoadedAndPrintTheMessage(engagementStatusSelect, "ENGAGEMENT STATUS SELECT");
		isElementLoadedAndPrintTheMessage(hourlySelect, "HOURLY SELECT");
		isElementLoadedAndPrintTheMessage(salariedSelect, "SALARIED SELECT");
		isElementLoadedAndPrintTheMessage(exemptSelect, "EXEMPT SELECT");
		isElementLoadedAndPrintTheMessage(homeStoreLabel, "HOME STORE LOCATION");
	}

	private String checkAndFillInTheFieldsToCreateInviteTM(Map<String, String> newTMDetails) throws Exception {
		String firstName = newTMDetails.get("FIRST_NAME") + new Random().nextInt(200) + new Random().nextInt(200);
		isElementLoadedAndPrintTheMessage(firstNameInput, "FIRST NAME Input");
		isElementLoadedAndPrintTheMessage(lastNameInput, "LAST NAME Input");
		isElementLoadedAndPrintTheMessage(emailInputTM, "EMAIL Input");
		isElementLoadedAndPrintTheMessage(phoneInput, "PHONE Input");
		isElementLoadedAndPrintTheMessage(dateHiredInput, "DATE HIRED Input");
		isElementLoadedAndPrintTheMessage(employeeIDInput, "EMPLOYEE ID Input");
		isElementLoadedAndPrintTheMessage(jobTitleSelect, "JOB TITLE SELECT");
		isElementLoadedAndPrintTheMessage(engagementStatusSelect, "ENGAGEMENT STATUS SELECT");
		isElementLoadedAndPrintTheMessage(hourlySelect, "HOURLY SELECT");
		isElementLoadedAndPrintTheMessage(salariedSelect, "SALARIED SELECT");
		isElementLoadedAndPrintTheMessage(exemptSelect, "EXEMPT SELECT");
		isElementLoadedAndPrintTheMessage(homeStoreLabel, "HOME STORE LOCATION");
		firstNameInput.sendKeys(firstName);
		lastNameInput.sendKeys(newTMDetails.get("LAST_NAME"));
		emailInputTM.sendKeys(newTMDetails.get("EMAIL"));
		phoneInput.sendKeys(newTMDetails.get("PHONE"));
		click(dateHiredInput);
		if (areListElementVisible(realDays, 5) && isElementLoaded(todayHighlighted, 5)) {
			click(todayHighlighted);
		}
		employeeIDInput.sendKeys( "E" + new Random().nextInt(200) + new Random().nextInt(200) + new Random().nextInt(200));
		selectByVisibleText(jobTitleSelect, newTMDetails.get("JOB_TITLE"));
		selectByVisibleText(engagementStatusSelect, newTMDetails.get("ENGAGEMENT_STATUS"));
		selectByVisibleText(hourlySelect, newTMDetails.get("HOURLY"));
		selectByVisibleText(salariedSelect, newTMDetails.get("SALARIED"));
		selectByVisibleText(exemptSelect, newTMDetails.get("EXEMPT"));
		return firstName;
	}

	@Override
	public void inviteTheNewCreatedTeamMember(String firstName) throws Exception {
		verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		if (isElementLoaded(searchTextBox, 5)) {
			searchTextBox.sendKeys(firstName);
			if (areListElementVisible(inviteButtons, 5)){
				if (inviteButtons.size() > 0) {
					// The newly created team member is at last.
					click(inviteButtons.get(inviteButtons.size() - 1));
				} else {
					SimpleUtils.fail("Can't find the new team member!", false);
				}
			}else {
				SimpleUtils.fail("There is no Invite Button loaded!", true);
			}
		}else {
			SimpleUtils.fail("Search textBox failed to load!", false);
		}
	}

	@Override
	public void verifyThereSectionsAreLoadedOnInviteWindow() throws Exception {
		String contact = "Verify Contact Information";
		String availability = "Enter Committed Availability";
		String welcome = "Personalize Welcome Message";
		if (isElementLoaded(inviteTMSection, 5)) {
			List<WebElement> threeSections = inviteTMSection.findElements(By.tagName("h2"));
			if (areListElementVisible(threeSections, 10)) {
				if (threeSections.size() == 3) {
					if (threeSections.get(0).getText().equals(contact)) {
						SimpleUtils.pass(contact + " section loaded!");
					} else {
						SimpleUtils.fail(contact + " failed to load!", true);
					}
					if (threeSections.get(1).getText().equals(availability)) {
						SimpleUtils.pass(availability + " section loaded!");
					} else {
						SimpleUtils.fail(availability + " failed to load!", true);
					}
					if (threeSections.get(2).getText().equals(welcome)) {
						SimpleUtils.pass(welcome + " section loaded!");
					} else {
						SimpleUtils.fail(welcome + " failed to load!", true);
					}
				} else {
					SimpleUtils.fail("Should load 3 sections, but load " + threeSections.size() + " section(s).", true);
				}
			} else {
				SimpleUtils.fail("Three sections failed to load!", true);
			}
		} else {
			SimpleUtils.fail("Invite TM section failed to load!", true);
		}
	}

	@Override
	public boolean isSaveButtonOnNewTMPageEnabled() throws Exception {
		boolean isEnabled = false;
		if (isElementEnabled(saveTMButton, 5)) {
			isEnabled = true;
			SimpleUtils.pass("Save Button on Add New Team member page is enabled!");
		} else {
			SimpleUtils.fail("Save button isn't enabled!", true);
		}
		return isEnabled;
	}

	@Override
	public void verifyContactNumberFormatOnNewTMPage(List<String> contactNumbers) throws Exception {
		String regex = "^[(]{0,1}[0-9]{3}[)]{0,1}[-\\s\\.]{0,1}[0-9]{3}[-\\s\\.]{0,1}[0-9]{4}$";
		String phone = "Phone";
		if (isElementEnabled(phoneInput, 5) && contactNumbers.size() > 0) {
			for (String contactNumber : contactNumbers) {
				phoneInput.clear();
				phoneInput.sendKeys(contactNumber);
				if (contactNumber.matches(regex)) {
					if (isElementLoaded(contactErrorMsg, 5)) {
						if (contactErrorMsg.getText().contains(phone)) {
							SimpleUtils.fail("Phone: " + contactNumber + "'s format is correct, but error message shows", true);
						} else {
							SimpleUtils.pass("Phone: " + contactNumber + "'s format is correct!");
						}
					}else {
						SimpleUtils.pass("Phone: " + contactNumber + "'s format is correct!");
					}
				}else{
					if (isElementLoaded(contactErrorMsg, 5)) {
						if (contactErrorMsg.getText().contains(phone)) {
							SimpleUtils.pass("Phone: " + contactNumber + "'s format is incorrect, error message shows!");
						}
					}else {
						SimpleUtils.fail("Phone: " + contactNumber + "'s format is incorrect, but error message doesn't show!", true);
					}
				}
			}
		}else {
			SimpleUtils.fail("Phone Input failed to load!", true);
		}
	}

	@Override
	public void verifyTMCountIsCorrectOnRoster() throws Exception {
		int count = 0;
		if (areListElementVisible(teamMemberNames, 5) && isElementLoaded(tmCount, 5)) {
			String countOnRoster = tmCount.getText().substring(tmCount.getText().indexOf("(") + 1, tmCount.getText().indexOf(")"));
			try {
				count = Integer.parseInt(countOnRoster);
				if (count == teamMemberNames.size()) {
					SimpleUtils.pass("TM Count is correct On roster page!");
				} else {
					SimpleUtils.fail("TM Count is incorrect on roster page!", true);
				}
			}catch (Exception e){
				SimpleUtils.fail("Parse String to Integer failed!", false);
			}
		}
	}

	@Override
	public void verifyCancelButtonOnAddTMAndClick() throws Exception {
		if (isElementLoaded(cancelButtonAddTM, 5)) {
			SimpleUtils.pass("Cancel Button loaded successfully!");
			if (isElementEnabled(cancelButtonAddTM, 5)) {
				SimpleUtils.pass("Cancel Button is Enabled by default!");
				click(cancelButtonAddTM);
			} else {
				SimpleUtils.fail("Cancel Button is not enabled!", true);
			}
		}else {
			SimpleUtils.fail("Cancel Button failed to load!", true);
		}
	}

	@Override
	public void verifyTheMandatoryFieldsCannotEmpty() throws Exception {
		if (isMandatoryElement(firstNameInput))
			SimpleUtils.pass("Checked First Name Input cannot be empty when adding a new TM!");
		else
			SimpleUtils.fail("First Name Input can be empty, but it is a mandatory field!", true);
		if (isMandatoryElement(lastNameInput))
			SimpleUtils.pass("Checked Last Name Input cannot be empty when adding a new TM!");
		else
			SimpleUtils.fail("Last Name Input can be empty, but it is a mandatory field!", true);
		if (isMandatoryElement(emailInputTM))
			SimpleUtils.pass("Checked Email Input cannot be empty when adding a new TM!");
		else
			SimpleUtils.fail("Email Input can be empty, but it is a mandatory field!", true);
		if (isMandatoryElement(dateHiredInput))
			SimpleUtils.pass("Checked Date Hired Input cannot be empty when adding a new TM!");
		else
			SimpleUtils.fail("Date Hired Input can be empty, but it is a mandatory field!", true);
		if (isMandatoryElement(employeeIDInput))
			SimpleUtils.pass("Checked Employee ID Input cannot be empty when adding a new TM!");
		else
			SimpleUtils.fail("Employee ID Input can be empty, but it is a mandatory field!", true);
		if (isMandatoryElement(jobTitleSelect))
			SimpleUtils.pass("Checked Job Title Select cannot be empty when adding a new TM!");
		else
			SimpleUtils.fail("Job Title Select can be empty, but it is a mandatory field!", true);
		if (isMandatoryElement(engagementStatusSelect))
			SimpleUtils.pass("Checked Engagement Status Select cannot be empty when adding a new TM!");
		else
			SimpleUtils.fail("Engagement Status Select can be empty, but it is a mandatory field!", true);
		if (isMandatoryElement(hourlySelect))
			SimpleUtils.pass("Checked Hourly Select cannot be empty when adding a new TM!");
		else
			SimpleUtils.fail("Hourly Select can be empty, but it is a mandatory field!", true);
		if (isMandatoryElement(salariedSelect))
			SimpleUtils.pass("Checked Salaried Select cannot be empty when adding a new TM!");
		else
			SimpleUtils.fail("Salaried Select can be empty, but it is a mandatory field!", true);
		if (isMandatoryElement(exemptSelect))
			SimpleUtils.pass("Checked Exempt Select cannot be empty when adding a new TM!");
		else
			SimpleUtils.fail("Exempt Select can be empty, but it is a mandatory field!", true);
		if (isElementLoaded(homeStoreLabel) && homeStoreLabel != null && !homeStoreLabel.getText().isEmpty())
			SimpleUtils.pass("Checked Home Store has value!");
		else
			SimpleUtils.fail("Home Store is empty, but it is a mandatory field!", true);
	}

	@Override
	public void verifyTMIsVisibleAndInvitedOnTODO(String name) throws Exception {
		boolean isVisible = false;
		String cardTitle = "ONBOARD NEW TEAM MEMBER";
		String tmMessage = name + " is a new Team Member";
		String inviteMessage = "Invite TM to create a Legion account";
		String dismiss = "DISMISS";
		String invite = "INVITE TO LEGION";
		if (areListElementVisible(todoCards, 10)) {
			for (WebElement todoCard : todoCards) {
				WebElement title = todoCard.findElement(By.cssSelector("div.todo-header"));
				WebElement teamMember = todoCard.findElement(By.cssSelector("p.heading"));
				WebElement inviteMsgElement = todoCard.findElement(By.cssSelector("span[ng-bind-html=\"p.text\"]"));
				if (isElementLoaded(title, 10) && isElementLoaded(teamMember, 10) && isElementLoaded(inviteMsgElement, 10)) {
					if (title.getText().contains(cardTitle) && teamMember.getText().equals(tmMessage) && inviteMsgElement.getText().equals(inviteMessage)) {
						WebElement dismissButton = todoCard.findElement(By.cssSelector("a.text-grey"));
						WebElement inviteButton = todoCard.findElement(By.cssSelector("a.text-green"));
						if (isElementLoaded(dismissButton, 10) && isElementLoaded(inviteButton, 10)) {
							if (dismissButton.getText().equals(dismiss) && inviteButton.getText().equals(invite)) {
								SimpleUtils.pass("Team member: " + name + " is visible and invited on TODO!");
								isVisible = true;
								break;
							}
						}
					} else {
						continue;
					}
				} else {
					continue;
				}
			}
		} else {
			SimpleUtils.fail("TODO cards failed to load!", false);
		}
		if (!isVisible) {
			SimpleUtils.fail("Failed to find the team member:" + name + " on TODO!", false);
		}
	}

	@Override
	public void	selectAInvitedOrNotInvitedTeamMemberToView() throws Exception {
		boolean isAvailable = false;
		String invite = "(Invited)";
		String notInvite = "(Not invited)";
		String inviteStatus = null;
		WebElement parent = null;
		WebElement tmName = null;
		if (areListElementVisible(invitationStatus, 10)) {
			for (int i = 0; i < invitationStatus.size(); i++) {
				inviteStatus = invitationStatus.get(i).getText();
				if (inviteStatus != null && !inviteStatus.isEmpty()) {
					if (inviteStatus.equals(invite) || inviteStatus.equals(notInvite)) {
						parent = invitationStatus.get(i).findElement(By.xpath("./../.."));
						if (parent != null) {
							tmName = parent.findElement(By.cssSelector("span.name"));
						}
						if (tmName != null) {
							click(tmName);
							isProfilePageLoaded();
							if (isInviteButtonAvailable(inviteStatus)) {
								click(inviteButton);
								isInviteTeamMemberWindowLoaded();
								isAvailable = true;
							}
						} else {
							SimpleUtils.fail("Failed to find the team member name!", true);
						}
						break;
					}
				}
			}
		} else {
			SimpleUtils.fail("Invitation Status failed to load!", true);
		}
		if (!isAvailable) {
			SimpleUtils.fail("INVITE/REINVITE button is unavailable!", true);
		}
	}

	@Override
	public int selectATeamMemberToActivate() throws Exception {
		int activateCount = 0;
		if (areListElementVisible(activateButtons, 15)) {
			activateCount = activateButtons.size();
			Random random = new Random();
			int index = random.nextInt(activateButtons.size());
			click(activateButtons.get(index));
		}
		return activateCount;
	}

	@Override
	public boolean isProfilePageSelected() throws Exception {
		boolean isProfile = false;
		String titleOpen = "collapsible-title-open";
		scrollToTop();
		if (isElementLoaded(profileTab, 10)) {
			String className = profileTab.getAttribute("class");
			if (className.contains(titleOpen)) {
				SimpleUtils.pass("Profile Tab is open!");
				isProfile = true;
			}
		} else {
			SimpleUtils.fail("Profile tab failed to load!", false);
		}
		return isProfile;
	}

	@Override
	public void navigateToProfileTab() throws Exception {
		if (isElementLoaded(profileTab, 10)) {
			click(profileTab);
			if (isProfilePageSelected()) {
				SimpleUtils.pass("Navigate to profile tab successfully!");
			} else {
				SimpleUtils.fail("Failed to navigate to the profile tab.", false);
			}
		} else {
			SimpleUtils.fail("Profile tab failed to load!", false);
		}
	}

	@Override
	public void clickOnActivateButton() throws Exception {
		if (isElementLoaded(activateButton, 10)) {
			click(activateButton);
		} else {
			SimpleUtils.fail("Activate button failed to load on Profile Tab!", false);
		}
	}

	@Override
	public void isActivateWindowLoaded() throws Exception {
		if (isElementLoaded(activateWindow, 10)) {
			SimpleUtils.pass("Activate window loaded successfully!");
		} else {
			SimpleUtils.fail("Activate window failed to load!", false);
		}
	}

	@Override
	public void selectADateOnCalendarAndActivate() throws Exception {
		String successfulMsg = "Successfully scheduled activation of Team Member.";
		String actualMsg = null;
		if (isElementLoaded(currentDay, 10)) {
			click(currentDay);
			if (isElementEnabled(applyButton, 10)) {
				click(applyButton);
				if (isElementLoaded(popupMessage, 10)) {
					actualMsg = popupMessage.getText();
					if (actualMsg.equals(successfulMsg)) {
						SimpleUtils.pass("Activate the Team Member successfully!");
					} else {
						SimpleUtils.fail("Failed to activate the Team member", false);
					}
				} else {
					SimpleUtils.fail("Pop up message failed to show!", false);
				}
			}else {
				SimpleUtils.fail("Activate button on activate window is disabled!", false);
			}
		}else {
			SimpleUtils.fail("Calendar failed to load!", false);
		}
	}

	@Override
	public void verifyDeactivateAndTerminateEnabled() throws Exception {
		if (isElementLoaded(deactivateButton, 10) && isElementLoaded(terminateButton, 10)) {
			if (isElementEnabled(deactivateButton, 5) && isElementEnabled(terminateButton, 5)) {
				SimpleUtils.pass("DEACTIVATE and TERMINATE button are enabled!");
			}else {
				SimpleUtils.fail("DEACTIVATE and TERMINATE button are not enabled!", false);
			}
		}else {
			SimpleUtils.fail("DEACTIVATE and TERMINATE button are not loaded!", false);
		}
	}

	@Override
	public String getOnBoardedDate() throws Exception {
		if (isElementLoaded(onBoardedDate, 10)) {
			return onBoardedDate.getText();
		}else {
			return null;
		}
	}

	@Override
	public void isOnBoardedDateUpdated(String previousDate) throws Exception {
		if (isElementLoaded(onBoardedDate, 10)) {
			if (!onBoardedDate.getText().equals(previousDate)) {
				SimpleUtils.pass("On Boarded Date is updated!");
			}else {
				SimpleUtils.fail("On Boarded Date is not updated!", true);
			}
		}else {
			SimpleUtils.fail("On boarded date failed to load!", false);
		}
	}

	@Override
	public void verifyTheStatusOfTeamMember() throws Exception {
		String expectedStatus = "Active";
		if (isElementLoaded(tmStatus, 10)) {
			if (expectedStatus.equals(tmStatus.getText())) {
				SimpleUtils.pass("Team member's status is correct!");
			}else {
				SimpleUtils.fail("Team member's status is incorrect!", true);
			}
		}else {
			SimpleUtils.fail("Status Element failed to load!", true);
		}
	}

	@Override
	public boolean isActivateButtonLoaded() throws Exception {
		boolean isLoaded = false;
		if (isElementLoaded(activateButton, 10)) {
			isLoaded = true;
		}
		return isLoaded;
	}

	@Override
	public void cancelActivateOrDeactivateTeamMember() throws Exception {
		boolean isActivateLoaded = false;
		if (isElementLoaded(deactivateButton, 10)) {
			click(deactivateButton);
			isDeactivateWindowLoaded();
			selectADateOnCalendarAndApplyDeactivate();
			if (isElementLoaded(popupMessage, 10)) {
				if (isElementLoaded(activateButton, 10) && isElementEnabled(activateButton, 10)) {
					isActivateLoaded = true;
				}
			}
		}
		if (isElementLoaded(cancelActivateButton, 10)) {
			click(cancelActivateButton);
			if (isElementLoaded(confirmPopupWindow, 10)) {
				if (isElementLoaded(confirmButton)) {
					click(confirmButton);
					if (isElementLoaded(popupMessage, 10)) {
						if (isElementLoaded(activateButton, 10) && isElementEnabled(activateButton, 10)) {
							isActivateLoaded = true;
						}
					}
				}else {
					SimpleUtils.fail("Confirm Button failed to load!", false);
				}
			}else {
				SimpleUtils.fail("Confirm pop up window failed to load!", false);
			}
		}
		if (!isActivateLoaded) {
			SimpleUtils.fail("Activate Button is not enabled!", false);
		}
	}

	@Override
	public void searchForTeamMemberByStatus(String status) throws Exception {
		if (isElementEnabled(searchTextBox, 10)) {
			searchTextBox.sendKeys(status);
			// wait for the team members to updated according to the keyword
			waitForSeconds(1);
		}else {
			SimpleUtils.fail("Search Textbox failed to load!", false);
		}
	}

	@Override
	public void sendTheInviteViaEmail() throws Exception {
		if (isSendAndCancelLoadedAndEnabledOnInvite()) {
			click(sendInviteButton);
			waitUntilElementIsInVisible(inviteTMWindow);
		} else {
			SimpleUtils.fail("Send Invitation Button failed to load!", false);
		}
	}

	@Override
	public void searchTheNewTMAndUpdateInfo(String firstName) throws Exception {
		if (isElementLoaded(searchTextBox, 5)) {
			searchTextBox.sendKeys(firstName);
			waitForSeconds(2);
			if (areListElementVisible(updateInfoButtons, 5)){
				if (updateInfoButtons.size() > 0) {
					// The newly created team member is at the last.
					click(updateInfoButtons.get(updateInfoButtons.size() - 1));
				} else {
					SimpleUtils.fail("Can't find the new team member!", false);
				}
			}else {
				SimpleUtils.fail("There is no Update Info Button loaded!", true);
			}
		}else {
			SimpleUtils.fail("Search textBox failed to load!", false);
		}
	}

	@Override
	public boolean isEmailOrPhoneNumberEmptyAndUpdate(Map<String, String> newTMDetails, String mandatoryField) throws Exception {
		boolean isEmpty = false;
		String email = "Email";
		String phone = "Phone Number";
		String emailValue = null;
		String phoneValue = null;
		if (isElementLoaded(phoneInput, 10) && isElementLoaded(emailInputTM, 10)) {
			emailValue = emailInputTM.getText();
			phoneValue = phoneInput.getText();
			if (email.equals(mandatoryField) && (phoneValue == null || (phoneValue != null && phoneValue.isEmpty()))) {
				isEmpty = true;
				SimpleUtils.pass("Email is a mandatory field, and phone number is empty, waiting for updated");
				phoneInput.sendKeys(newTMDetails.get("PHONE"));
			}
			if (phone.equals(mandatoryField) && (emailValue == null || (emailValue != null && emailValue.isEmpty()))) {
				isEmpty = true;
				SimpleUtils.pass("Phone Number is a mandatory field, and email is empty, waiting for updated");
				emailInputTM.sendKeys(newTMDetails.get("EMAIL"));
			}
		} else {
			SimpleUtils.fail("Phone and Email inputs failed to load!", true);
		}
		if (isEmpty) {
			scrollToBottom();
			click(saveTMButton);
			waitUntilElementIsInVisible(saveTMButton);
		}else {
			SimpleUtils.fail("Update Info button shows when email or phone is empty, but they are not empty!", false);
		}
		return isEmpty;
	}

	@Override
	public void searchTheTMAndCheckUpdateInfoNotShow(String firstName) throws Exception {
		if (isElementLoaded(searchTextBox, 5)) {
			searchTextBox.sendKeys(firstName);
			waitForSeconds(2);
			if (areListElementVisible(updateInfoButtons, 10)) {
				SimpleUtils.fail("Update Info button still shows after updating the info!", false);
			} else {
				SimpleUtils.pass("Update Info Button doesn't show after updating the info!");
			}
		}else {
			SimpleUtils.fail("Search textBox failed to load!", false);
		}
	}

	@Override
	public boolean isTerminateButtonLoaded() throws Exception {
		boolean isLoaded = false;
		if (isElementLoaded(terminateButton, 10)) {
			SimpleUtils.pass("Terminate Button is Loaded!");
			isLoaded = true;
		}else {
			SimpleUtils.fail("Terminate Button failed to load!", false);
		}
		return isLoaded;
	}

	@Override
	public boolean isCancelTerminateButtonLoaded() throws Exception {
		boolean isLoaded = false;
		if (isElementLoaded(cancelTerminateButton, 10)) {
			SimpleUtils.pass("Cancel Terminate Button is Loaded!");
			isLoaded = true;
		}
		return isLoaded;
	}

	@Override
	public void terminateTheTeamMember(boolean isCurrentDay) throws Exception {
		String removeMsg = "Successfully scheduled removal of Team Member from Roster.";
		String actualMsg = "";
		scrollToBottom();
		click(terminateButton);
		isTerminateWindowLoaded();
		if (isElementLoaded(currentDay, 10) && isElementLoaded(applyButton)) {
			if (isCurrentDay) {
				click(currentDay);
			}else {
				selectAFutureDateFromCalendar();
			}
			click(applyButton);
			if (isElementLoaded(confirmPopupWindow, 15) && isElementLoaded(confirmButton, 15)) {
				click(confirmButton);
				if (isElementLoaded(popupMessage, 15))
				{
					actualMsg = popupMessage.getText();
					if (removeMsg.equals(actualMsg)) {
						SimpleUtils.pass("Terminate the team member successfully!");
					}else {
						SimpleUtils.fail("The pop up message is incorrect!", false);
					}
				}else {
					SimpleUtils.fail("Pop up message doesn't show!", false);
				}
			} else {
				SimpleUtils.fail("Confirm window doesn't show!", false);
			}
		}else {
			SimpleUtils.fail("Current day and apply button doesn't show!", false);
		}
	}

	@Override
	public String getEmployeeIDFromProfilePage() throws Exception {
		String employeeIDText = null;
		if (isElementLoaded(employeeID, 10)) {
			employeeIDText = employeeID.getText();
		}else {
			SimpleUtils.fail("EMPLOYEE ID failed to load!", false);
		}
		return employeeIDText;
	}

	@Override
	public void searchTheTeamMemberByEmployeeIDFromRoster(String employeeID, boolean isTerminated) throws Exception {
		if (isElementLoaded(searchTextBox, 5)) {
			searchTextBox.sendKeys(employeeID);
			waitForSeconds(2);
			if (isTerminated) {
				if (areListElementVisible(teamMemberNames, 10)) {
					SimpleUtils.fail("Team Member still shows after terminating it!", false);
				} else {
					SimpleUtils.pass("Team member can't be found from Roster after terminating it!");
				}
			}else {
				if (areListElementVisible(teamMemberNames, 10)) {
					SimpleUtils.pass("Team Member shows after cancel terminating it!");
				} else {
					SimpleUtils.fail("Team member should be found from Roster after cancel terminating it!", false);
				}
			}
		}else {
			SimpleUtils.fail("Search textBox failed to load!", false);
		}
	}

	@Override
	public void verifyTheFunctionOfCancelTerminate() throws Exception {
		String cancelMsg = "Successfully cancelled removal of Team Member from Roster.";
		String actualMsg = "";
		isCancelTerminateButtonLoaded();
		click(cancelTerminateButton);
		if (isElementLoaded(confirmPopupWindow, 15) && isElementLoaded(confirmButton, 15)) {
			click(confirmButton);
			if (isElementLoaded(popupMessage, 15)) {
				actualMsg = popupMessage.getText();
				if (cancelMsg.equals(actualMsg)) {
					SimpleUtils.pass("Cancelled Terminated successfully!");
				}else {
					SimpleUtils.fail("Failed to cancel the termination!", false);
				}
			}else{
				SimpleUtils.fail("Pop up message failed to load!", false);
			}
		}else{
			SimpleUtils.fail("Confirm pop up window failed to load!", false);
		}
	}

	@Override
	public boolean isManualOnBoardButtonLoaded() throws Exception {
		boolean isLoaded = false;
		if (isElementLoaded(manualOnBoardButton, 15)) {
			isLoaded = true;
			SimpleUtils.pass("Manual Onboard Button Loaded Successfully!");
		}else{
			SimpleUtils.fail("Manual Onboard Button failed to load!", false);
		}
		return isLoaded;
	}

	@Override
	public void manualOnBoardTeamMember() throws Exception {
		String successfulMsg = "Team member successfully On-boarded.";
		String actualMsg = "";
		click(manualOnBoardButton);
		if (isElementLoaded(confirmPopupWindow, 15) && isElementLoaded(confirmButton, 15)) {
			click(confirmButton);
			if (isElementLoaded(popupMessage, 15)) {
				actualMsg = popupMessage.getText();
				if (successfulMsg.equals(actualMsg)) {
					SimpleUtils.pass("Manual OnBoard the team member successfully!");
				}else {
					SimpleUtils.fail("Manual OnBoard message is incorrect! " + actualMsg, false);
				}
			}else {
				SimpleUtils.fail("Pop up message failed to load!", false);
			}
		}else {
			SimpleUtils.fail("Manual OnBoard Pop up window doesn't show!", false);
		}
	}

	private void selectAFutureDateFromCalendar() throws Exception {
		if (isElementLoaded(nextMonthArrow, 5)){
			click(nextMonthArrow);
			if (areListElementVisible(daysOnCalendar, 15)){
				/*
				 * Generate a random to select a day!
				 */
				Random random = new Random();
				WebElement realDay = daysOnCalendar.get(random.nextInt(daysOnCalendar.size()));
				click(realDay);
			}else {
				SimpleUtils.fail("Days on calendar failed to load!", false);
			}
		}else {
			SimpleUtils.fail("Back and Forward arrows are failed to load!", true);
		}
	}

	private boolean isTerminateWindowLoaded() throws Exception {
		boolean isLoaded = false;
		if (isElementLoaded(removeWindow)) {
			SimpleUtils.pass("Terminate window loaded successfully!");
			isLoaded = true;
		}else {
			SimpleUtils.fail("Terminate window failed to load!", false);
		}
		return isLoaded;
	}

	private void isDeactivateWindowLoaded() throws Exception {
		if (isElementLoaded(deactivateWindow, 10)) {
			SimpleUtils.pass("Deactivate window loaded successfully!");
		} else {
			SimpleUtils.fail("Deactivate window failed to load!", false);
		}
	}

	private void selectADateOnCalendarAndApplyDeactivate() throws Exception {
		if (isElementLoaded(currentDay, 10)) {
			click(currentDay);
			// Apply Deactivate button is the same css as applyOnActivate
			if (isElementEnabled(applyButton, 10)) {
				click(applyButton);
				if (isElementLoaded(confirmPopupWindow, 10)) {
					if (isElementLoaded(confirmButton)) {
						click(confirmButton);
					}else {
						SimpleUtils.fail("Confirm Button failed to load!", false);
					}
				}else {
					SimpleUtils.fail("Confirm pop up window failed to load!", false);
				}
			}else {
				SimpleUtils.fail("Apply button failed to load!", false);
			}
		}else {
			SimpleUtils.fail("Calendar failed to load!", false);
		}
	}

	private boolean isInviteButtonAvailable(String inviteStatus) throws Exception {
		boolean isAvailable = false;
		String invite = "(Invited)";
		String notInvite = "(Not invited)";
		String inviteButtonName = "INVITE";
		String reInviteButtonName = "REINVITE";
		if (isElementLoaded(inviteButton, 10)) {
			if (inviteStatus.equals(invite)) {
				if (inviteButton.getText().equals(reInviteButtonName)) {
					isAvailable = true;
					SimpleUtils.pass("REINVITE button is available when the status is invited");
				}else {
					SimpleUtils.fail("When status is invited, button should be REINVITE, but actual is: " + inviteButton.getText(), true);
				}
			}else if (inviteStatus.equals(notInvite)) {
				if (inviteButton.getText().equals(inviteButtonName)) {
					isAvailable = true;
					SimpleUtils.pass("INVITE button is available when the status is Not invited");
				}else {
					SimpleUtils.fail("When status is Not invited, button should be INVITE, but actual is: " + inviteButton.getText(), true);
				}
			} else {
				SimpleUtils.fail("Failed to find the INVITE/REINVITE button!", true);
			}
		}else {
			SimpleUtils.fail("INVITE/REINVITE button is not shown!", true);
		}
		return isAvailable;
	}

	private boolean isMandatoryElement(WebElement element) throws Exception{
		boolean isCannotEmpty = false;
		String notEmptyClassName = "ng-invalid-required";
		if (isElementLoaded(element, 5)){
			if (element != null){
				String className = element.getAttribute("class");
				if (className.contains(notEmptyClassName)){
					isCannotEmpty = true;
				}
			}
		}
		return isCannotEmpty;
	}

	private boolean isElementLoadedAndPrintTheMessage(WebElement element, String elementName) throws Exception {
		boolean isLoaded = false;
		if (isElementEnabled(element, 5)) {
			isLoaded = true;
			SimpleUtils.pass(elementName + " loaded Successfully!");
		}else {
			SimpleUtils.fail(elementName + " failed to load!", false);
		}
		return isLoaded;
	}

	private boolean isManageBadgesLoaded() throws Exception {
		boolean isLoaded = false;
		if (isElementLoaded(manageBadgesWindow, 10)) {
			isLoaded = true;
			SimpleUtils.pass("Manage Badges Window Loaded successfully!");
		}else {
			SimpleUtils.fail("Manage Badges Window failed to load!", true);
		}
		return isLoaded;
	}

	private int selectAllTheBadges() throws Exception {
		int badgeCount = 0;
		String checked = "checked";
		if (areListElementVisible(badgeCheckBoxes, 15)) {
			badgeCount = badgeCheckBoxes.size();
			for (WebElement badgeCheckBox : badgeCheckBoxes) {
				if (!badgeCheckBox.getAttribute("class").contains(checked)) {
					badgeCheckBox.click();
					if (badgeCheckBox.getAttribute("class").contains(checked)) {
						SimpleUtils.pass("Check the Badge successfully!");
					}
				}
			}
		}else {
			SimpleUtils.fail("Badge checkboxes are failed to load!", true);
		}
		return badgeCount;
	}

	private int getSpecificDayIndex(WebElement specificDay) {
		int index = 0;
		if (areListElementVisible(daysOnCalendar, 10)){
			for (int i = 0; i < daysOnCalendar.size(); i++) {
				String day = daysOnCalendar.get(i).getText();
				if (day.equals(specificDay.getText())){
					index = i;
					SimpleUtils.pass("Get current day's index successfully");
				}
			}
		}else {
			SimpleUtils.fail("Days on calendar failed to load!", true);
		}
		return index;
	}

	private void navigateToPreviousAndFutureDate(WebElement element) {
		SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy");
		click(element);
		if (SimpleUtils.isDateFormatCorrect(currentMonthYear.getText(), format)){
			SimpleUtils.pass("Navigate to previous/future date successfully and date format is correct!");
		}else {
			SimpleUtils.fail("Date format is incorrect!", true);
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
		if (isElementLoaded(calendarImage, 5)){
			click(calendarImage);
			if (isElementLoaded(calendar, 5)){
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
