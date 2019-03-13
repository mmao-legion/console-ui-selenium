package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.legion.pages.BasePage;
import com.legion.pages.ProfileNewUIPage;
import com.legion.tests.core.TeamTestKendraScott2.timeOffRequestAction;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;

public class ConsoleProfileNewUIPage extends BasePage implements ProfileNewUIPage{
	
	public ConsoleProfileNewUIPage(){
		PageFactory.initElements(getDriver(), this);
	}
	
	final static String consoleProfileMenuItemText = "Profile";

	@FindBy(className="console-navigation-item")
	private List<WebElement>consoleNavigationMenuItems;
	@FindBy(css = "div.profile")
	private WebElement profilePageSection;
	@FindBy(css="div.collapsible-title-text")
	private List<WebElement> profilePageSubSections;
	@FindBy(css="a[ng-click=\"newTimeOff()\"]")
	private WebElement newTimeOffBtn;
	@FindBy(css = "div.reasons-reason ")
	private List<WebElement> timeOffReasons;
	@FindBy(css = "textarea[placeholder=\"Optional explanation\"]")
	private WebElement timeOffExplanationtextArea;
	@FindBy(css= "div.real-day")
	private List<WebElement> calendarDates;
	@FindBy(css="a.calendar-nav-left")
	private WebElement timeOffCalendarLeftArrow;
	@FindBy(css="a.calendar-nav-right")
	private WebElement timeOffCalendarRightArrow;
	@FindBy(css="ranged-calendar[range-start=\"rangeStart\"]")
	private List<WebElement> timeOffCalendarMonths;
	@FindBy(css="button[ng-click=\"apply($event)\"]")
	private WebElement timeOffApplyBtn;
	@FindBy(css="div[ng-if=\"!editing.profile\"]")
	private List<WebElement> editingProfileSections;
	@FindBy(css="div.quick-profile")
	private WebElement quickProfileDiv;
	@FindBy(css="lgn-action-button[ng-click=\"changePassword()\"]")
	private WebElement changePasswordBtn;
	@FindBy(css="div[ng-if=\"canViewWorkerEngagement()\"]")
	private WebElement engagementDetailsSection;
	@FindBy(css="div.badge-section")
	private WebElement badgeSection;
	@FindBy(css="div.collapsible-title.collapsible-title-open")
	private List<WebElement> collapsibleTabsTitle;
	@FindBy(css="div[ng-if=\"!isPreferenceEdit()\"]")
	private WebElement shiftPreferenceSection;
	@FindBy(css="div[ng-if=\"!isPreferenceEdit()\"]")
	private WebElement myAvailabilitySection;
	@FindBy(css="div.count-block.count-block-pending")
	private WebElement timeOffPendingBlock;
	@FindBy(css="div.count-block.count-block-approved")
	private WebElement timeOffApprovedBlock;
	@FindBy(css="div.count-block.count-block-rejected")
	private WebElement timeOffRejectedBlock;
	@FindBy(css="div.timeoff-requests")
	private WebElement timeOffRequestsSection;
	@FindBy(css="div.location-selector-location-name-text")
	private WebElement locationSelectorLocationName;
	@FindBy(css="div.timeoff-requests-request.row-fx")
	private List<WebElement> timeOffRequestRows;
	@FindBy(css="i[ng-click=\"editProfile()\"]")
	private WebElement profileEditPencilIcon;
	@FindBy(css="button[ng-click=\"cancelEdit()\"]")
	private WebElement userProfileCancelBtn;
	@FindBy(css="div[ng-form=\"profileEdit\"]")
	private WebElement profileEditForm;
	@FindBy(css="button[type=\"submit\"]")
	private WebElement userProfileSaveBtn;
	@FindBy(css="input[name=\"firstName\"]")
	private WebElement profileFirstNameInputBox;
	@FindBy(css="input[name=\"lastName\"]")
	private WebElement profileLastNameInputBox;
	@FindBy(css="input[aria-label=\"Nickname\"]")
	private WebElement profileNickNameInputBox;
	@FindBy(css="input[aria-label=\"Street Address 1\"]")
	private WebElement profileAddressStreetAddress1InputBox;
	@FindBy(css="input[aria-label=\"Street Address 2\"]")
	private WebElement profileAddressStreetAddress2InputBox;
	@FindBy(css="input[aria-label=\"City\"]")
	private WebElement profileAddressCityInputBox;
	@FindBy(css="select[aria-label=\"State\"]")
	private WebElement profileAddressStateInputBox;
	@FindBy(css="input[aria-label=\"Zip\"")
	private WebElement profileAddressZipInputBox;
	@FindBy(css="input[name=\"phone\"]")
	private WebElement profileContactPhoneInputBox;
	@FindBy(css="input[name=\"email\"]")
	private WebElement profileContactEmailInputBox;
	@FindBy(css="lgn-action-button[ng-click=\"invite()\"]")
	private WebElement userProfileInviteBtn;
	@FindBy(css="section[ng-form=\"inviteTm\"]")
	private WebElement inviteTeamMemberPopUp;
	@FindBy(css="input[id=\"phone\"]")
	private WebElement inviteTeamMemberPopUpPhoneField;
	@FindBy(css="input[id=\"email\"]")
	private WebElement inviteTeamMemberPopUpEmailField;
	@FindBy(css="textarea[name=\"message\"]")
	private WebElement inviteTeamMemberPopUpMessageField;
	@FindBy(css="button[ng-click=\"$dismiss()\"]")
	private WebElement inviteTeamMemberPopUpCancelBtn;
	@FindBy(css="button[ng-click=\"send()\"]")
	private WebElement inviteTeamMemberPopUpSendBtn;
	@FindBy(css="lgn-action-button[ng-click=\"changePassword()\"]")
	private WebElement userProfileChangePasswordBtn;
	@FindBy(css="div[ng-form=\"changePassword\"]")
	private WebElement changePasswordPopUp;
	@FindBy(css="input[ng-model=\"oldPassword\"]")
	private WebElement changePasswordPopUpOldPasswordField;
	@FindBy(css="input[ng-model=\"password\"]")
	private WebElement changePasswordPopUpNewPasswordField;
	@FindBy(css="input[ng-model=\"confirmPassword\"")
	private WebElement changePasswordPopUpConfirmPasswordField;
	@FindBy(css="div[ng-click=\"cancelCallback()\"]")
	private WebElement changePasswordPopUpPopUpCancelBtn;
	@FindBy(css="div[ng-click=\"okCallback()\"]")
	private WebElement changePasswordPopUpPopUpSendBtn;
	@FindBy(css="collapsible[identifier=\"'workpreference'\"]")
	private WebElement collapsibleWorkPreferenceSection;
	@FindBy(css="div.committed-hours")
	private WebElement commitedHoursDiv;
	@FindBy(css="div[ng-show=\"showPreference()\"]")
	private List<WebElement> showPreferenceOptionsDiv;
	@FindBy(css="div.badge-row")
	private WebElement profileBadgesRow;
	@FindBy(css="div.no-badges")
	private WebElement noBadgesDiv;
	@FindBy(css="div[ng-repeat=\"oneBadge in badges\"]")
	private List<WebElement> badgesDiv;
	@FindBy(css="div[ng-class=\"getClassForOtherLocationCheckButton()\"]")
	private WebElement otherLocationCheckButton;
	@FindBy(css="div[ng-class=\"tmPrefs.volunteerMoreHours ? 'enabled' : 'disabled'\"]")
	private WebElement volunteerMoreHoursCheckButton;
	@FindBy(css="div.edit-tm-avail.availability-slider")
	private List<WebElement> availabilitySliders;
	@FindBy(css="button[ng-click=\"savePreferences($event)\"]")
	private WebElement savePreferencesBtn;
	@FindBy(css="div.edit-availability-container")
	private WebElement ditAvailabilityContainerDiv;
	@FindBy(css="span.tm-hours-scheduled-data")
	private WebElement sheduleHoursData;
	@FindBy(css="span[ng-click=\"getLastWeekData()\"]")
	private WebElement pastWeekArrow;
	@FindBy(css="span[ng-click=\"getNextWeekData()\"]")
	private WebElement futureWeekArrow;
	@FindBy(css="span.week-nav-icon-main")
	private WebElement activeWeek;
	@FindBy(css="span.tm-remaining-hours-label-bold")
	private WebElement remainingHours;
	@FindBy(css="span.tm-total-hours-label-green")
	private WebElement totalHours;
	@FindBy(css="div.availability-shift")
	private List<WebElement> availableShifts;
	@FindBy(css="div.availability-row.availability-row-active")
	private List<WebElement> myAvailabilityDayOfWeekRows;
	@FindBy(css="button.lgn-action-button-success")
	private WebElement myAvailabilityUnLockBtn;
	@FindBy(css="button[ng-click=\"saveAvailability($event)\"]")
	private WebElement myAvailabilityEditModeSaveBtn;
	@FindBy(css="button[ng-click=\"save(false)\"]")
	private WebElement MyAvailabilityEditSaveThisWeekOnlyBtn;
	@FindBy(css="button[ng-click=\"save(false)\"]")
	private WebElement MyAvailabilityEditSaveRepeatForwordBtn;
	@FindBy(css="div.hour-cell.hour-cell-ghost.cursor-resizableE")
	private List<WebElement> hourCellsResizableCursorsRight;
	@FindBy(css="div.hour-cell.hour-cell-ghost.cursor-resizableW")
	private List<WebElement> hourCellsResizableCursorsLeft;
	@FindBy(css="div[ng-if=\"isAvailabilityEdit()\"]")
	private WebElement myAvailabilityEditModeHeader;
	@FindBy(css="div.count-block.count-block-pending")
	private WebElement pendingTimeOffCountDiv;
	@FindBy(css="div.count-block.count-block-approved")
	private WebElement approvedTimeOffCountDiv;
	@FindBy(css="div.count-block.count-block-rejected")
	private WebElement rejectedTimeOffCountDiv;
	@FindBy(css="todo-card[todo-type=\"todoType\"]")
	private List<WebElement> todoCards;
	@FindBy(css="a[ng-click=\"goRight()\"]")
	private WebElement nextToDoCardArrow;
	@FindBy(css="button.lgn-action-button-success")
	private WebElement confirmTimeOffApprovalBtn;
	@FindBy(css="span[ng-if=\"canCancel(r)\"]")
	private WebElement timeOffRequestCancelBtn;
	
	@Override
	public void clickOnProfileConsoleMenu() throws Exception {
		if(consoleNavigationMenuItems.size() != 0)
		{
			WebElement consoleScheduleMenuElement = SimpleUtils.getSubTabElement(consoleNavigationMenuItems, consoleProfileMenuItemText);
			click(consoleScheduleMenuElement);
			SimpleUtils.pass("'Profile' Console Menu Loaded Successfully!");
		}
		else {
			SimpleUtils.fail("'Profile' Console Menu Items Not Loaded Successfully!",false);
		}
	}
	
	@Override
	public boolean isProfilePageLoaded() throws Exception
	{
		if(isElementLoaded(profilePageSection)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void selectProfilePageSubSectionByLabel(String profilePageSubSectionLabel) throws Exception {
		boolean isSubSectionSelected = false;
		if(isElementLoaded(profilePageSubSections.get(0))) {
			for(WebElement profilePageSubSection : profilePageSubSections) {
				if(profilePageSubSection.getText().toLowerCase().contains(profilePageSubSectionLabel.toLowerCase())) {
					click(profilePageSubSection);
					isSubSectionSelected = true;
					break;
				}
			}
			if(isSubSectionSelected)
				SimpleUtils.pass("Controls Page: '"+profilePageSubSectionLabel+"' sub section selected Successfully.");
			else
				SimpleUtils.fail("Controls Page: '"+profilePageSubSectionLabel+"' sub section not loaded.",false);
		}
		else
			SimpleUtils.fail("Profile Page: Sub Section not loaded.", false);
	}

	@Override
	public void clickOnCreateTimeOffBtn() throws Exception {
		if(isElementLoaded(newTimeOffBtn)) {
			click(newTimeOffBtn);
			SimpleUtils.pass("Controls Page: 'Create Time Off' button Clicked.");
		}
		else
			SimpleUtils.fail("Controls Page: 'Create Time Off' button not loaded.", false);
	}
	
	
	@Override
	public void selectTimeOffReason(String reasonLabel) throws Exception
	{
		boolean isTimeOffReasonSelected = false;
		if(timeOffReasons.size() > 0) {
			for(WebElement timeOffReason : timeOffReasons) {
				if(timeOffReason.getText().toLowerCase().contains(reasonLabel.toLowerCase())) {
					click(timeOffReason);
					isTimeOffReasonSelected = true;
				}
			}
			if(isTimeOffReasonSelected)
				SimpleUtils.pass("Controls Page: Time Off Reason '"+ reasonLabel +"' selected successfully.");
			else
				SimpleUtils.fail("Controls Page: Time Off Reason '"+ reasonLabel +"' not found.", false);
		}
		else
			SimpleUtils.fail("Controls Page: 'Time Off Reasons' not loaded.", false);
	}
	
	
	@Override
	public void updateTimeOffExplanation(String explanationText) throws Exception
	{
		if(isElementLoaded(timeOffExplanationtextArea)) {
			timeOffExplanationtextArea.sendKeys(explanationText);
			SimpleUtils.pass("Controls Page: Time Off Optional explanation updated successfully.");
		}
		else
			SimpleUtils.fail("Controls Page: 'Optional explanation' text Area not loaded.", false);
	}
	

	@Override
	public void selectTimeOffDuration(String startDate, String endDate) throws Exception
	{		
		String disabledCalendarDayClass = "can-not-select";
		String timeOffStartDate = startDate.split(",")[0].split(" ")[1];
		String timeOffStartMonth = startDate.split(",")[0].split(" ")[0];
		int timeOffStartYear = Integer.valueOf(startDate.split(",")[1].trim());
		
		String timeOffEndDate = endDate.split(",")[0].split(" ")[1];
		String timeOffEndMonth = endDate.split(",")[0].split(" ")[0];
		int timeOffEndYear = Integer.valueOf(endDate.split(",")[1].trim());
		
		WebElement changeMonthArrow = timeOffCalendarRightArrow;
		WebElement timeOffMonthAndYear = timeOffCalendarMonths.get(0).findElement(By.cssSelector("div.ranged-calendar__month-name"));
		// Selecting Start date
		while(timeOffStartYear != Integer.valueOf(timeOffMonthAndYear.getText().split(" ")[1])
				|| ! timeOffMonthAndYear.getText().toLowerCase().contains(timeOffStartMonth.toLowerCase()))
		{
			if(timeOffMonthAndYear.getText().toLowerCase().contains(timeOffStartMonth.toLowerCase())) {
				if(timeOffStartYear > Integer.valueOf(timeOffMonthAndYear.getText().split(" ")[1]))
					changeMonthArrow = timeOffCalendarRightArrow;
				else
					changeMonthArrow = timeOffCalendarLeftArrow;
			}
			click(changeMonthArrow);
		}	
		if(calendarDates.size() > 0) {
			for(WebElement calendarDate : calendarDates) {
				if(Integer.valueOf(calendarDate.getText()) == Integer.valueOf(timeOffStartDate)) {
					if(calendarDate.getAttribute("class").contains(disabledCalendarDayClass)) {
						SimpleUtils.fail("Profile Page: Time Off requests must be made at least 15 days in advance, given Start Date '"
								+startDate+"'.", false);
					}
					else {
						click(calendarDate);
						SimpleUtils.pass("Profile Page: New Time Off Request Start Date '" +startDate+" selected successfully'.");
						break;
					}
				}
			}
		}
		else
			SimpleUtils.fail("Controls Page: 'Calendar' not loaded.", false);
		
		
		// Selecting End date
		while(timeOffEndYear != Integer.valueOf(timeOffMonthAndYear.getText().split(" ")[1])
				|| ! timeOffMonthAndYear.getText().toLowerCase().contains(timeOffEndMonth.toLowerCase()))
		{
			if(timeOffMonthAndYear.getText().toLowerCase().contains(timeOffEndMonth.toLowerCase())) {
				if(timeOffEndYear > Integer.valueOf(timeOffMonthAndYear.getText().split(" ")[1]))
					changeMonthArrow = timeOffCalendarRightArrow;
				else
					changeMonthArrow = timeOffCalendarLeftArrow;
			}
			click(changeMonthArrow);
		}	
		if(calendarDates.size() > 0) {
			for(WebElement calendarDate : calendarDates) {
				if(Integer.valueOf(calendarDate.getText()) == Integer.valueOf(timeOffEndDate)) {
					if(calendarDate.getAttribute("class").contains(disabledCalendarDayClass)) {
						SimpleUtils.fail("Profile Page: Time Off requests must be made at least 15 days in advance, given End Date '"
								+endDate+"'.", false);
					}
					else {
						click(calendarDate);
						SimpleUtils.pass("Profile Page: New Time Off Request End Date '" +endDate+" selected successfully'.");
						break;
					}
				}
			}
		}
		else
			SimpleUtils.fail("Controls Page: 'Calendar' not loaded.", false);
	}

	@Override
	public void clickOnSaveTimeOffRequestBtn() throws Exception
	{
		if(timeOffApplyBtn.isEnabled()) {
			click(timeOffApplyBtn);
		}
		else
			SimpleUtils.fail("Profile Page: Unable to save New Time Off Request.", false);
			
	}
	
	
	@Override
	public void createNewTimeOffRequest(String timeOffReasonLabel, String timeOffExplanationText,
			String timeOffStartDate, String timeOffEndDate) throws Exception {
		final int timeOffRequestCount = timeOffRequestRows.size();
		clickOnCreateTimeOffBtn();
		Thread.sleep(1000);
		selectTimeOffReason(timeOffReasonLabel);
        updateTimeOffExplanation(timeOffExplanationText);
        selectTimeOffDuration(timeOffStartDate, timeOffEndDate);
        clickOnSaveTimeOffRequestBtn();
        Thread.sleep(1000);
        if(timeOffRequestRows.size() > timeOffRequestCount) 
        	SimpleUtils.pass("Profile Page: New Time Off Save Successfully.");
        else
        	SimpleUtils.fail("Profile Page: New Time Off not Save Successfully.", false);
	}

	@Override
	public String getTimeOffRequestStatus(String timeOffReasonLabel, String timeOffExplanationText,
			String timeOffStartDuration, String timeOffEndDuration) throws Exception {
		String timeOffStartDate = timeOffStartDuration.split(",")[0].split(" ")[1];
		String timeOffStartMonth = timeOffStartDuration.split(",")[0].split(" ")[0];
		String timeOffEndDate = timeOffEndDuration.split(",")[0].split(" ")[1];
		String timeOffEndMonth = timeOffEndDuration.split(",")[0].split(" ")[0];
		
		String requestStatusText = "";
		int timeOffRequestCount = timeOffRequestRows.size();
		if(timeOffRequestCount > 0) {
			for(WebElement timeOffRequest : timeOffRequestRows) {
				WebElement requestType = timeOffRequest.findElement(By.cssSelector("span.request-type"));
				if(requestType.getText().toLowerCase().contains(timeOffReasonLabel.toLowerCase())) {
					WebElement requestDate = timeOffRequest.findElement(By.cssSelector("div.request-date"));
					String[] requestDateText = requestDate.getText().replace("\n", "").split("-");
					if(requestDateText.length > 1) {
						if(requestDateText[0].toLowerCase().contains(timeOffStartMonth.toLowerCase()) 
								&& requestDateText[0].toLowerCase().contains(timeOffStartDate.toLowerCase())
								&& requestDateText[1].toLowerCase().contains(timeOffEndMonth.toLowerCase()) 
								&& requestDateText[1].toLowerCase().contains(timeOffEndDate.toLowerCase())) {
							WebElement requestStatus = timeOffRequest.findElement(By.cssSelector("span.request-status"));
							requestStatusText = requestStatus.getText();
						}
					}
					else {
						String requestDateText1 = requestDate.getText().replace("\n", "");
						if(requestDateText1.toLowerCase().contains(timeOffStartMonth.toLowerCase()) 
								&& requestDateText1.toLowerCase().contains(timeOffStartDate.toLowerCase())
								&& requestDateText1.toLowerCase().contains(timeOffEndMonth.toLowerCase()) 
								&& requestDateText1.toLowerCase().contains(timeOffEndDate.toLowerCase())) {
							WebElement requestStatus = timeOffRequest.findElement(By.cssSelector("span.request-status"));
							requestStatusText = requestStatus.getText();
						}
					}
				}
			}
		}
		else
			SimpleUtils.fail("Profile Page: No Time off request found.", false);
		
		return requestStatusText;
	}
	

	@Override
	public boolean isEditingProfileSectionLoaded() throws Exception
	{
		if(editingProfileSections.size() > 0)
		{
			
			WebElement profileSection = editingProfileSections.get(0);
			if(profileSection.getText().length() > 0) {
				SimpleUtils.pass("Profile Page: Editing Profile Section Loaded successfully.");
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isPersonalDetailsSectionLoaded() throws Exception
	{
		if(isElementLoaded(quickProfileDiv))
		{
			if(quickProfileDiv.getText().length() > 0) {
				SimpleUtils.pass("Profile Page: Personal Details Section on 'About Me' tab Loaded successfully.");
				return true;
			}
		}
		return false;
	}
	

	
	@Override
	public boolean isChangePasswordButtonLoaded() throws Exception
	{
		if(isElementLoaded(changePasswordBtn) && changePasswordBtn.isDisplayed())
		{
			SimpleUtils.pass("Profile Page: Change Password Button on 'About Me' tab Loaded successfully.");
			return true;
		}
		return false;
	}
	

	@Override
	public boolean isEngagementDetrailsSectionLoaded() throws Exception
	{
		if(isElementLoaded(engagementDetailsSection))
		{
			if(engagementDetailsSection.getText().length() > 0) {
				SimpleUtils.pass("Profile Page: Engagement Detrails Section on 'About Me' tab Loaded successfully.");
				return true;
			}
		}
		return false;
	}
	

	@Override
	public boolean isProfileBadgesSectionLoaded() throws Exception
	{
		if(isElementLoaded(badgeSection))
		{
			if(badgeSection.getText().length() > 0) {
				SimpleUtils.pass("Profile Page: Badges Section on 'About Me' tab Loaded successfully.");
				return true;
			}
		}
		return false;
	}
	

	@Override
	public String getProfilePageActiveTabLabel() throws Exception
	{
		String activeTabLabel = "";
		if(collapsibleTabsTitle.size() > 0) {
			for(WebElement tabTitle: collapsibleTabsTitle) {
				if(tabTitle.isDisplayed()) {
					activeTabLabel = tabTitle.getText();
					break;
				}
			}
		}
		else
			SimpleUtils.fail("Profile Page: Tabs not loaded.", true);
		return activeTabLabel;
	}
	

	@Override
	public boolean isShiftPreferenceSectionLoaded() throws Exception
	{
		String myShiftPreferencesTabTitle = "My Shift Preferences";
		if(isElementLoaded(shiftPreferenceSection))
		{
			if(! shiftPreferenceSection.isDisplayed())
				selectProfilePageSubSectionByLabel(myShiftPreferencesTabTitle);
			if(shiftPreferenceSection.getText().length() > 0) {
				SimpleUtils.pass("Profile Page: "+myShiftPreferencesTabTitle+" Section on 'My Work Preferences' tab Loaded successfully.");
				return true;
			}
		}
		return false;
	}
	

	@Override
	public boolean isMyAvailabilitySectionLoaded() throws Exception
	{
		String myAvailabilityTabTitle = "My Availability";
		if(isElementLoaded(myAvailabilitySection))
		{
			if(! myAvailabilitySection.isDisplayed())
				selectProfilePageSubSectionByLabel(myAvailabilityTabTitle);
			if(myAvailabilitySection.getText().length() > 0) {
				SimpleUtils.pass("Profile Page: "+myAvailabilityTabTitle+" Section on 'My Work Preferences' tab Loaded successfully.");
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isCreateTimeOffButtonLoaded() throws Exception
	{
		if(isElementLoaded(newTimeOffBtn) && newTimeOffBtn.isDisplayed()) {
			SimpleUtils.pass("Profile Page: Create Time off button on 'My Time Off' tab Loaded successfully.");
				return true;
		}
		return false;
	}
	

	@Override
	public boolean isTimeOffPendingBlockLoaded() throws Exception
	{
		if(isElementLoaded(timeOffPendingBlock) && timeOffPendingBlock.isDisplayed() && timeOffPendingBlock.getText().length() > 0)
		{
				SimpleUtils.pass("Profile Page: 'Time Off Pending Block' Section on 'My Time Off' tab Loaded successfully.");
				return true;
		}
		return false;
	}
	

	@Override
	public boolean isTimeOffApprovedBlockLoaded() throws Exception
	{
		if(isElementLoaded(timeOffApprovedBlock) && timeOffApprovedBlock.isDisplayed() && timeOffApprovedBlock.getText().length() > 0)
		{
				SimpleUtils.pass("Profile Page: 'Time Off Approved Block' Section on 'My Time Off' tab Loaded successfully.");
				return true;
		}
		return false;
	}
	

	@Override
	public boolean isTimeOffRejectedBlockLoaded() throws Exception
	{
		if(isElementLoaded(timeOffRejectedBlock) && timeOffRejectedBlock.isDisplayed() && timeOffRejectedBlock.getText().length() > 0)
		{
				SimpleUtils.pass("Profile Page: 'Time Off Rejected Block' Section on 'My Time Off' tab Loaded successfully.");
				return true;
		}
		return false;
	}
	

	@Override
	public boolean isTimeOffRequestsSectionLoaded() throws Exception
	{
		if(isElementLoaded(timeOffRequestsSection) && timeOffRequestsSection.isDisplayed() && timeOffRequestsSection.getText().length() > 0)
		{
				SimpleUtils.pass("Profile Page: 'Time Off Requests Section' Section on 'My Time Off' tab Loaded successfully.");
				return true;
		}
		return false;
	}
	
	
	@Override
	public String getProfilePageActiveLocation() throws Exception
	{
		String activeLocation = "";
		if(isElementLoaded(locationSelectorLocationName) && locationSelectorLocationName.isDisplayed())
		{
			activeLocation = locationSelectorLocationName.getText();
		}
		return activeLocation;
	}
	
	@Override
	public int getAllTimeOffRequestCount() {
		int timeOffRequestCount = timeOffRequestRows.size();
		return timeOffRequestCount;
	}
	
	@Override
	public void updateUserProfile(String firstName, String lastname, String nickName, String streetAddress1,
			String streetAddress2, String city, String state, String zip, String phone, String email) throws Exception
	{
		clickOnEditUserProfilePencilIcon();
		updateUserProfileName("Jamie", "Ward", "Mr. Jamie.W");
		updateUserProfileHomeAddress("", "", "", "", "");
		updateUserProfileContacts("2025550124", "jamie.w@kendrascott2.legion.co");
		//clickOnSaveUserProfileBtn();
		clickOnCancelUserProfileBtn();
	}
	
	private void clickOnCancelUserProfileBtn() throws Exception {
		if(isElementLoaded(userProfileCancelBtn))
			click(userProfileCancelBtn);
		if(!isElementLoaded(profileEditForm))
			SimpleUtils.pass("Profile Page: User profile Cancel Button clicked.");
		else
			SimpleUtils.fail("Profile Page: unable to cancel edit User profile popup.", false);
	}

	
	public void clickOnEditUserProfilePencilIcon() throws Exception
	{
		if(isElementLoaded(profileEditPencilIcon))
			click(profileEditPencilIcon);
		if(isElementLoaded(profileEditForm))
			SimpleUtils.pass("Profile Page: User profile edit form loaded successfully.");
		else
			SimpleUtils.fail("Profile Page: User profile edit form not loaded.", false);
	}
	
	public void clickOnSaveUserProfileBtn() throws Exception
	{
		if(isElementLoaded(userProfileSaveBtn))
			click(userProfileSaveBtn);
		if(!isElementLoaded(profileEditForm))
			SimpleUtils.pass("Profile Page: User profile successfully saved.");
		else
			SimpleUtils.fail("Profile Page: unable to save User profile.", false);
	}
	
	public void updateUserProfileName(String firstName, String lastname, String nickName) throws Exception
	{
		// Updating First Name
		if(isElementLoaded(profileFirstNameInputBox)) {
			if(profileFirstNameInputBox.getAttribute("value").toLowerCase().contains(firstName.toLowerCase()))
				SimpleUtils.pass("Profile Page: User Profile 'First Name' already updated with value: '"+firstName+"'.");
			else {
				profileFirstNameInputBox.clear();
				profileFirstNameInputBox.sendKeys(firstName);
				SimpleUtils.pass("Profile Page: User Profile 'First Name' updated with value: '"+firstName+"'.");
			}
		}
		
		// Updating Last Name
		if(isElementLoaded(profileLastNameInputBox)) {
			if(profileLastNameInputBox.getAttribute("value").toLowerCase().contains(lastname.toLowerCase()))
				SimpleUtils.pass("Profile Page: User Profile 'Last Name' already updated with value: '"+lastname+"'.");
			else {
				profileLastNameInputBox.clear();
				profileLastNameInputBox.sendKeys(lastname);
				SimpleUtils.pass("Profile Page: User Profile 'Last Name' updated with value: '"+lastname+"'.");
			}
		}
		
		// Updating First Name
		if(isElementLoaded(profileNickNameInputBox)) {
			if(profileNickNameInputBox.getAttribute("value").toLowerCase().contains(nickName.toLowerCase()))
				SimpleUtils.pass("Profile Page: User Profile 'Nick Name' already updated with value: '"+nickName+"'.");
			else {
				profileNickNameInputBox.clear();
				profileNickNameInputBox.sendKeys(nickName);
				SimpleUtils.pass("Profile Page: User Profile 'Nick Name' updated with value: '"+nickName+"'.");
			}
		}
	}
					
	public void updateUserProfileHomeAddress(String streetAddress1, String streetAddress2, String city, String state, String zip) throws Exception
	{
		// Updating Home Address Street Address 1
		if(isElementLoaded(profileAddressStreetAddress1InputBox)) {
			if(profileAddressStreetAddress1InputBox.getAttribute("value").toLowerCase().contains(streetAddress1.toLowerCase()))
				SimpleUtils.pass("Profile Page: User Profile Home Address 'Street Address 1' already updated with value: '"
						+streetAddress1+"'.");
			else {
				profileAddressStreetAddress1InputBox.clear();
				profileAddressStreetAddress1InputBox.sendKeys(streetAddress1);
				SimpleUtils.pass("Profile Page: User Profile Home Address 'Street Address 1' updated with value: '"
						+streetAddress1+"'.");
			}
		}
		
		// Updating Home Address Street Address 2
		if(isElementLoaded(profileAddressStreetAddress2InputBox)) {
			if(profileAddressStreetAddress2InputBox.getAttribute("value").toLowerCase().contains(streetAddress2.toLowerCase()))
				SimpleUtils.pass("Profile Page: User Profile Home Address 'Street Address 2' already updated with value: '"
						+streetAddress2+"'.");
			else {
				profileAddressStreetAddress2InputBox.clear();
				profileAddressStreetAddress2InputBox.sendKeys(streetAddress2);
				SimpleUtils.pass("Profile Page: User Profile Home Address 'Street Address 2' updated with value: '"
						+streetAddress2+"'.");
			}
		}
		
		// Updating Home Address City
		if(isElementLoaded(profileAddressCityInputBox)) {
			if(profileAddressCityInputBox.getAttribute("value").toLowerCase().contains(city.toLowerCase()))
				SimpleUtils.pass("Profile Page: User Profile Home Address 'City' already updated with value: '"+city+"'.");
			else {
				profileAddressCityInputBox.clear();
				profileAddressCityInputBox.sendKeys(city);
				SimpleUtils.pass("Profile Page: User Profile Home Address 'City' updated with value: '"+city+"'.");
			}
		}
		
		// Updating Home Address State
		if(isElementLoaded(profileAddressStateInputBox)) {
			boolean isStateSelected = false;
			Select statesDropdown = new Select(profileAddressStateInputBox);
			if(statesDropdown.getFirstSelectedOption().getText().toLowerCase().contains(state.toLowerCase()))
				SimpleUtils.pass("Profile Page: User Profile Nick Name already updated with value: '"+state+"'.");
			else {
				for(WebElement stateOption : statesDropdown.getOptions()) {
					if(stateOption.getText().toLowerCase().contains(state.toLowerCase())) {
						click(stateOption);
						isStateSelected = true;
					}
				}
				if(isStateSelected)
					SimpleUtils.pass("Profile Page: User Profile Home Address 'State' updated with value: '"+state+"'.");
				else
					SimpleUtils.fail("Profile Page: User Profile Home Address State: '"+state+"' not found.", true);
			}
		}
		
		// Updating Home Address Zip
		if(isElementLoaded(profileAddressZipInputBox)) {
			if(profileAddressZipInputBox.getAttribute("value").toLowerCase().contains(zip.toLowerCase()))
				SimpleUtils.pass("Profile Page: User Profile Home Address 'Zip' already updated with value: '"+zip+"'.");
			else {
				profileAddressZipInputBox.clear();
				profileAddressZipInputBox.sendKeys(zip);
				SimpleUtils.pass("Profile Page: User Profile Home Address 'Zip' updated with value: '"+zip+"'.");
			}
		}
	}
	
	
	
	public void updateUserProfileContacts(String phone, String email) throws Exception
	{
		// Updating Home Address Phone
		if(isElementLoaded(profileContactPhoneInputBox)) {
			if(profileContactPhoneInputBox.getAttribute("value").toLowerCase().contains(phone.toLowerCase()))
				SimpleUtils.pass("Profile Page: User Profile Contact 'Phone' already updated with value: '"+phone+"'.");
			else {
				profileContactPhoneInputBox.clear();
				profileContactPhoneInputBox.sendKeys(phone);
				SimpleUtils.pass("Profile Page: User Profile Contact 'Phone' updated with value: '"+phone+"'.");
			}
		}
		
		// Updating Home Address Email
		if(isElementLoaded(profileContactEmailInputBox)) {
			if(profileContactEmailInputBox.getAttribute("value").toLowerCase().contains(email.toLowerCase()))
				SimpleUtils.pass("Profile Page: User Profile Contact 'Email' already updated with value: '"+email+"'.");
			else {
				profileContactEmailInputBox.clear();
				profileContactEmailInputBox.sendKeys(email);
				SimpleUtils.pass("Profile Page: User Profile Contact 'Email' updated with value: '"+email+"'.");
			}
		}
	}

	@Override
	public TreeMap<String, String> getUserProfileEngagementDetails() throws Exception {
		TreeMap<String, String> userProfileEngagementDetails = new TreeMap<String, String>();
		if(isElementLoaded(engagementDetailsSection)) {
			
			List<WebElement> rows = engagementDetailsSection.findElements(By.cssSelector("div.row"));
			for(int index = 1; index < rows.size() ; index++) {
				if(rows.get(index).getText().toLowerCase().contains("date hired") 
						&& rows.get(index).getText().toLowerCase().contains("job title")) {
					String[] rowValues = rows.get(index + 1).getText().split("\n");
					if(rowValues.length > 1) {
						userProfileEngagementDetails.put("dateHired", rowValues[0]);
						userProfileEngagementDetails.put("jobTitle", rowValues[1]);
					}
					else {
						SimpleUtils.fail("Profile Page: Unable to get Date Hired and Job Title value from ' Engagement Details Section'", true);
					}
				}
				else if(rows.get(index).getText().toLowerCase().contains("employee id")) {
					userProfileEngagementDetails.put("employeeId", rows.get(index + 1).getText());
				}
				
				else if(rows.get(index).getText().toLowerCase().contains("home store")) {
					userProfileEngagementDetails.put("homeStore", rows.get(index + 1).getText());
				}
				else if(rows.get(index).getText().toLowerCase().contains("engagement status")) {
					userProfileEngagementDetails.put("engagementStatus", rows.get(index + 1).getText());
				}
				else if(rows.get(index).getText().toLowerCase().contains("hourly")) {
					userProfileEngagementDetails.put("hourly", rows.get(index + 1).getText());
				}
				else if(rows.get(index).getText().toLowerCase().contains("salaried")) {
					userProfileEngagementDetails.put("salaried", rows.get(index + 1).getText());
				}
				
				else if(rows.get(index).getText().toLowerCase().contains("exempt")) {
					userProfileEngagementDetails.put("exempt", rows.get(index + 1).getText());
				}
				else if(rows.get(index).getText().toLowerCase().contains("status")) {
					String[] statusValue = rows.get(index).getText().split("\n");
					if(statusValue.length == 3)
						userProfileEngagementDetails.put("status", statusValue[1] + " "+ statusValue[2]);
					else
						SimpleUtils.fail("Profile Page: Unable to get Status value from ' Engagement Details Section'", true);

				}
			}
		}
		return userProfileEngagementDetails;
	}

	
	@Override
	public void userProfileInviteTeamMember() throws Exception {
		if(isElementLoaded(userProfileInviteBtn, 2)) {
			click(userProfileInviteBtn);
			SimpleUtils.pass("Profile Page: user profile 'Invite' button clicked successfully.");
			
			if(isElementLoaded(inviteTeamMemberPopUp)) {
				SimpleUtils.pass("Profile Page: user profile 'Invite Team Member' popup loaded successfully.");
				
				if(isElementLoaded(inviteTeamMemberPopUpPhoneField)) {
					String inviteTeamMemberPopUpPhoneFieldValue = inviteTeamMemberPopUpPhoneField.getAttribute("value");
					if(inviteTeamMemberPopUpPhoneFieldValue.length() > 0)
						SimpleUtils.pass("'Invite Team Member' popup 'Phone' Input field loaded with value:'"+inviteTeamMemberPopUpPhoneFieldValue+"'.");
					else
						SimpleUtils.fail("'Invite Team Member' popup 'Phone' Input field contains Blank value.", true);
				}
				else
					SimpleUtils.fail("'Invite Team Member' popup 'Phone' Input field not loaded.", true);
				
				if(isElementLoaded(inviteTeamMemberPopUpEmailField)) {
					String inviteTeamMemberPopUpEmailFieldValue = inviteTeamMemberPopUpEmailField.getAttribute("value");
					if(inviteTeamMemberPopUpEmailFieldValue.length() > 0)
						SimpleUtils.pass("'Invite Team Member' popup 'Email' Input field loaded with value:'"+inviteTeamMemberPopUpEmailFieldValue+"'.");
					else
						SimpleUtils.fail("'Invite Team Member' popup 'Email' Input field contains Blank value.", true);
				}
				else
					SimpleUtils.fail("'Invite Team Member' popup 'Email' Input field not loaded.", true);
				
				if(isElementLoaded(inviteTeamMemberPopUpMessageField)) {
					String inviteTeamMemberPopUpMessageFieldValue = inviteTeamMemberPopUpMessageField.getAttribute("value");
					if(inviteTeamMemberPopUpMessageFieldValue.length() > 0)
						SimpleUtils.pass("'Invite Team Member' popup 'Message' Input field loaded with value:'"+inviteTeamMemberPopUpMessageFieldValue+"'.");
					else
						SimpleUtils.fail("'Invite Team Member' popup 'Message' Input field contains Blank value.", true);
				}
				else
					SimpleUtils.fail("'Invite Team Member' popup 'Message' Input field not loaded.", true);
				
				if(isElementLoaded(inviteTeamMemberPopUpSendBtn) && inviteTeamMemberPopUpSendBtn.isEnabled()) {
					SimpleUtils.pass("'Invite Team Member' popup 'Send' Button not loaded successfully.");
				}
				else
					SimpleUtils.fail("'Invite Team Member' popup 'Send' Button not loaded.", true);
				
				if(isElementLoaded(inviteTeamMemberPopUpCancelBtn) && inviteTeamMemberPopUpCancelBtn.isEnabled()) {
					SimpleUtils.pass("'Invite Team Member' popup 'Cancel' Button not loaded successfully.");
					click(inviteTeamMemberPopUpCancelBtn);
					SimpleUtils.pass("'Invite Team Member' popup 'Cancel' Button clicked successfully.");
				}
				else
					SimpleUtils.fail("'Invite Team Member' popup 'Cancel' Button not loaded.", true);	
				
			}
			else
				SimpleUtils.fail("Profile Page: user profile 'Invite Team Memeber' popup not loaded.", false);			
		}
		else
			SimpleUtils.report("Profile Page: user profile 'Invite' button not Available.");
	}
			
	@Override
	public void userProfileChangePassword(String oldPassword, String newPassword, String confirmPassword) throws Exception {
		if(isElementLoaded(userProfileChangePasswordBtn, 2)) {
			click(userProfileChangePasswordBtn);
			SimpleUtils.pass("Profile Page: user profile 'Change Password' button clicked successfully.");
			
			if(isElementLoaded(changePasswordPopUp)) {
				SimpleUtils.pass("Profile Page: user profile 'Change Password' popup loaded successfully.");
				
				if(isElementLoaded(changePasswordPopUpOldPasswordField)) {
					SimpleUtils.pass("'Change Password' popup 'Old Password' Input field loaded successfully.");
					changePasswordPopUpOldPasswordField.clear();
					changePasswordPopUpOldPasswordField.sendKeys(oldPassword);
				}
				else
					SimpleUtils.fail("'Change Password' popup 'Old Password' Input field not loaded.", true);
				
				if(isElementLoaded(changePasswordPopUpNewPasswordField)) {
					SimpleUtils.pass("'Change Password' popup 'New Password' Input field loaded successfully.");
					changePasswordPopUpNewPasswordField.clear();
					changePasswordPopUpNewPasswordField.sendKeys(newPassword);
				}
				else
					SimpleUtils.fail("'Change Password' popup 'New Password' Input field not loaded.", true);
				
				if(isElementLoaded(changePasswordPopUpConfirmPasswordField)) {
					SimpleUtils.pass("'Change Password' popup 'Confirm New Password' Input field loaded successfully.");
					changePasswordPopUpConfirmPasswordField.clear();
					changePasswordPopUpConfirmPasswordField.sendKeys(confirmPassword);
				}
				else
					SimpleUtils.fail("'Change Password' popup 'Confirm New Password' Input field not loaded.", true);
				
				
				if(isElementLoaded(changePasswordPopUpPopUpSendBtn) && changePasswordPopUpPopUpSendBtn.isEnabled()) {
					SimpleUtils.pass("'Change Password' popup 'Ok' Button not loaded successfully.");
				}
				else
					SimpleUtils.fail("'Change Password' popup 'Ok' Button not loaded.", true);
				
				if(isElementLoaded(changePasswordPopUpPopUpCancelBtn) && changePasswordPopUpPopUpCancelBtn.isEnabled()) {
					SimpleUtils.pass("'Change Password' popup 'Cancel' Button not loaded successfully.");
					click(changePasswordPopUpPopUpCancelBtn);
					SimpleUtils.pass("'Change Password' popup 'Cancel' Button clicked successfully.");
				}
				else
					SimpleUtils.fail("'Change Password' popup 'Cancel' Button not loaded.", true);	
				
			}
			else
				SimpleUtils.fail("Profile Page: user profile 'Change Password' popup not loaded.", false);			
		}
		else
			SimpleUtils.report("Profile Page: user profile 'Change Password' button not loaded.");
	}
	
	
	@Override
	public boolean isShiftPreferenceCollapsibleWindowOpen() throws Exception
	{
		if(isElementLoaded(collapsibleWorkPreferenceSection)) {
			WebElement collapsibleRowDiv = collapsibleWorkPreferenceSection.findElement(By.cssSelector("div.collapsible-title"));
			if(isElementLoaded(collapsibleRowDiv)) {
				if(collapsibleRowDiv.getAttribute("class").contains("open"))
					return true;
			}
			else 
				SimpleUtils.fail("Profile Page: Collapsible Work Preference window not loaded under 'My Shift Preference' Tab.", true);
		}
		else 
			SimpleUtils.fail("Profile Page: Collapsible Work Preference Section not loaded under 'My Shift Preference' Tab.", false);
		
		return false;
	}
	
	@Override
	public void clickOnShiftPreferenceCollapsibleWindowHeader() throws Exception
	{
		if(isElementLoaded(collapsibleWorkPreferenceSection)) {
			WebElement collapsibleRowDiv = collapsibleWorkPreferenceSection.findElement(By.cssSelector("div.collapsible-title"));
			if(isElementLoaded(collapsibleRowDiv)) {
				click(collapsibleRowDiv);
				SimpleUtils.pass("Profile Page: Collapsible Work Preference window Header clicked under 'My Shift Preference' Tab.");
			}
			else 
				SimpleUtils.fail("Profile Page: Collapsible Work Preference window not loaded under 'My Shift Preference' Tab.", true);
		}
		else 
			SimpleUtils.fail("Profile Page: Collapsible Work Preference Section not loaded under 'My Shift Preference' Tab.", false);
	}
	
	
	@Override
	public HashMap<String, String> getMyShiftPreferenceData() throws Exception
	{
		HashMap<String, String> shiftPreferenceData = new HashMap<String, String>();
		if(isElementLoaded(collapsibleWorkPreferenceSection)) {
			List<WebElement> myShiftPreferenceDataLabels = collapsibleWorkPreferenceSection.findElements(By.cssSelector("div.quick-schedule-preference.label"));
			List<WebElement> myShiftPreferenceDataValues = collapsibleWorkPreferenceSection.findElements(By.cssSelector("div.quick-schedule-preference.value"));
			if(myShiftPreferenceDataLabels.size() > 0 && myShiftPreferenceDataLabels.size() == myShiftPreferenceDataValues.size()) {
				for(int index = 0; index < myShiftPreferenceDataLabels.size(); index++) {
					if(myShiftPreferenceDataLabels.get(index).isDisplayed()) {
						if(myShiftPreferenceDataLabels.get(index).getText().toLowerCase().contains("hours/shift")) {
							shiftPreferenceData.put("hoursPerShift", myShiftPreferenceDataValues.get(index).getText());
						}
						else if(myShiftPreferenceDataLabels.get(index).getText().toLowerCase().contains("hours/wk")) {
							shiftPreferenceData.put("hoursPerWeek", myShiftPreferenceDataValues.get(index).getText());
						}
						else if(myShiftPreferenceDataLabels.get(index).getText().toLowerCase().contains("shifts/wk")) {
							shiftPreferenceData.put("shiftsPerWeek", myShiftPreferenceDataValues.get(index).getText());
						}
					}
				}
				
				// Get Enterprise preferred Hours
				if(isElementLoaded(commitedHoursDiv)) {
					String[] preferredHoursText = commitedHoursDiv.getText().split(":");
					if(preferredHoursText.length > 0)
						shiftPreferenceData.put("enterprisePreferredHours",preferredHoursText[1]);
					else
						SimpleUtils.fail("Profile Page: Enterprise Preferred Hours not loaded under ' My Shift Preferences' Section.", true);
				}
				
				//
				if(showPreferenceOptionsDiv.size() > 0) {
					for(WebElement preferenceOptions : showPreferenceOptionsDiv) {
						if(preferenceOptions.isDisplayed() && preferenceOptions.isEnabled()) {
							String[] preferenceOptionsText = preferenceOptions.getText().split("\n");
							if(preferenceOptionsText.length > 0) {
								for(String preferenceOptionsTextLine : preferenceOptionsText) {
									if(preferenceOptionsTextLine.toLowerCase().contains("volunteer for additional work")) {
										String[] volunteerOptionText = preferenceOptionsTextLine.split(":");
										if(volunteerOptionText.length > 1) {
											shiftPreferenceData.put("volunteerForAdditionalWork",volunteerOptionText[1]);
										}
										else 
											SimpleUtils.fail("Profile Page: Volunteer for additional work not loaded under ' My Shift Preferences' Section.", true);	
									}
									
									if(preferenceOptionsTextLine.toLowerCase().contains("other preferred locations")) {
										String[] otherPreferredLocationsOptionText = preferenceOptionsTextLine.split(":");
										if(otherPreferredLocationsOptionText.length > 1) {
											shiftPreferenceData.put("otherPreferredLocations",otherPreferredLocationsOptionText[1]);
										}
										else 
											SimpleUtils.fail("Profile Page: 'Other preferred locations' not loaded under ' My Shift Preferences' Section.", true);	
									}
								}
							}
						}
					}	
				}
				else 
					SimpleUtils.fail("Profile Page: 'Volunteer for additional work' and 'Other preferred locations' not loaded under ' My Shift Preferences' Section.", true);
				
				
			}
		}
		return shiftPreferenceData;
	}

	
	@Override
	public ArrayList<String> getUserProfileBadgesDetails() throws Exception {
		ArrayList<String> badgesText = new ArrayList<String>();
		if(isElementLoaded(profileBadgesRow)) {
			SimpleUtils.pass("Profile Page: Badges Section loaded under 'About Me' Tab.");
			if(badgesDiv.size() > 0) {
				for(WebElement badge : badgesDiv) {
					badgesText.add(badge.getAttribute("data-original-title"));
				}
			}
			else
				SimpleUtils.report("Profile Page: Badges Section 'No badges' found under 'About Me' Tab.");
		}
		else {
			if(isElementLoaded(noBadgesDiv))
				SimpleUtils.report("Profile Page: Badges Section 'No badges' found under 'About Me' Tab.");
			else
				SimpleUtils.fail("Profile Page: Badges Section not loaded under 'About Me' Tab.", false);
		}
		return badgesText;
	}
	
	
	public void updateReceivesShiftOffersForOtherLocationCheckButton(boolean isOfferForOtherLocation) throws Exception 
	{
		if(isElementLoaded(otherLocationCheckButton)) {
			if(isOfferForOtherLocation && otherLocationCheckButton.getAttribute("class").contains("enable"))
				SimpleUtils.pass("Profile Page: 'Receives Shift Offers for other locations' already enabled.");
			else if(!isOfferForOtherLocation && otherLocationCheckButton.getAttribute("class").contains("disabled"))
				SimpleUtils.pass("Profile Page: 'Receives Shift Offers for other locations' already Disabled.");
			else if(! isOfferForOtherLocation && otherLocationCheckButton.getAttribute("class").contains("enable")) {
				click(otherLocationCheckButton);
				SimpleUtils.pass("Profile Page: 'Receives Shift Offers for other locations' Disabled successfully.");
			}
			else {
				click(otherLocationCheckButton);
				SimpleUtils.pass("Profile Page: 'Receives Shift Offers for other locations' Enabled successfully.");
			}
	    }
		else
			SimpleUtils.fail("Profile Page: 'Receives Shift Offers for other locations' CheckBox not loaded.", false);
	}
	
	
	
	public void updateVolunteersForAdditionalWorkCheckButton(boolean isOfferForOtherLocation) throws Exception 
	{
		if(isElementLoaded(volunteerMoreHoursCheckButton)) {
			if(isOfferForOtherLocation && volunteerMoreHoursCheckButton.getAttribute("class").contains("enable"))
				SimpleUtils.pass("Profile Page: 'Volunteers for Additional Work' CheckBox already enabled.");
			else if(!isOfferForOtherLocation && volunteerMoreHoursCheckButton.getAttribute("class").contains("disabled"))
				SimpleUtils.pass("Profile Page: 'Volunteers for Additional Work' CheckBox already Disabled.");
			else if(! isOfferForOtherLocation && volunteerMoreHoursCheckButton.getAttribute("class").contains("enable")) {
				click(volunteerMoreHoursCheckButton);
				SimpleUtils.pass("Profile Page: 'Volunteers for Additional Work' CheckBox Disabled successfully.");
			}
			else {
				click(volunteerMoreHoursCheckButton);
				SimpleUtils.pass("Profile Page: 'Volunteers for Additional Work' CheckBox Enabled successfully.");
			}
		}
		else
			SimpleUtils.fail("Profile Page: 'Volunteers for Additional Work' CheckBox not loaded.", false);
	}
	
	
	public void updateMyShiftPreferencesAvailabilitySliders(int minCount, int maxCount, String sliderType) throws Exception
	{
		int minPreferenceCount = 0;
		int maxPreferenceCount = 0;
		if(availabilitySliders.size() > 0) {
			for(WebElement availabilitySlider : availabilitySliders) {
				if(availabilitySlider.getText().toLowerCase().contains(sliderType.toLowerCase())) {
					WebElement minSlider = availabilitySlider.findElement(By.cssSelector("span[ng-style=\"minPointerStyle\"]"));
					WebElement maxSlider = availabilitySlider.findElement(By.cssSelector("span[ng-style=\"maxPointerStyle\"]"));
					WebElement editPrefValuesDiv = availabilitySlider.findElement(By.cssSelector("div.edit-pref-values"));
					if(isElementLoaded(editPrefValuesDiv)) {
						String[] editPrefValuesDivText = editPrefValuesDiv.getText().split("-");
						if(editPrefValuesDivText.length > 1) {
							minPreferenceCount = Integer.valueOf(editPrefValuesDivText[0].trim());
							maxPreferenceCount = Integer.valueOf(editPrefValuesDivText[1].trim());
						}
					}
					
					// Update Min Slider
					if(isElementLoaded(minSlider)) {
						int sliderOffSet = 5;
						if(minCount < minPreferenceCount)
							sliderOffSet = -5;
						while(Integer.valueOf(editPrefValuesDiv.getText().split("-")[0].trim()) != minCount)
						{
							String minMaxSlidersValue = editPrefValuesDiv.getText();
							moveElement(minSlider, sliderOffSet);
							if(minMaxSlidersValue.equals(editPrefValuesDiv.getText())){
								SimpleUtils.fail("Profile Page: 'Min Slider' for "+sliderType+" not moving.", true);
								break;
							}
						}
					}
					else
						SimpleUtils.fail("Profile Page: 'Min Slider' for "+sliderType+" not loaded.", false);
					
					// Update Max Slider
					if(isElementLoaded(maxSlider)) {
						int sliderOffSet = 5;
						if(maxCount < maxPreferenceCount)
							sliderOffSet = -5;
						while(Integer.valueOf(editPrefValuesDiv.getText().split("-")[1].trim()) != maxCount)
						{
							String maxMaxSlidersValue = editPrefValuesDiv.getText();
							moveElement(maxSlider, sliderOffSet);
							if(maxMaxSlidersValue.equals(editPrefValuesDiv.getText())){
								SimpleUtils.fail("Profile Page: 'Max Slider' for "+sliderType+" not moving.", true);
								break;
							}
						}
					}
					else
						SimpleUtils.fail("Profile Page: 'Max Slider' for "+sliderType+" not loaded.", false);
					break;
				}
				
			}
		}
		else
			SimpleUtils.fail("Profile Page: Edit My Shift Preferences - Availability Sliders not loaded.", false);
	}
	
	public void moveElement(WebElement webElement, int xOffSet)
	{
		Actions builder = new Actions(MyThreadLocal.getDriver());
		builder.moveToElement(webElement)
	         .clickAndHold()
	         .moveByOffset(xOffSet, 0)
	         .release()
	         .build()
	         .perform();
	}
			
	@Override
	public void updateMyShiftPreferenceData(boolean canReceiveOfferFromOtherLocation, boolean isVolunteersForAdditional, int minHoursPerShift,
			int maxHoursPerShift, int minShiftLength, int maxShiftLength, int minShiftsPerWeek, int maxShiftsPerWeek) throws Exception
	{
		boolean isShiftPreferenceWindowOpen = isShiftPreferenceCollapsibleWindowOpen();
	      if(! isShiftPreferenceWindowOpen)
	    	  clickOnShiftPreferenceCollapsibleWindowHeader();
	      
	      clickOnEditMyShiftPreferencePencilIcon();
	      if(isMyShiftPreferenceEditContainerLoaded()) {
	    	  SimpleUtils.pass("Profile Page: 'My Shift Preference' edit Container loaded successfully.");
	    	  updateReceivesShiftOffersForOtherLocationCheckButton(canReceiveOfferFromOtherLocation);
	    	  updateVolunteersForAdditionalWorkCheckButton(isVolunteersForAdditional);
	    	  
	    	  updateMyShiftPreferencesAvailabilitySliders(minHoursPerShift, maxHoursPerShift, "Hours per week");
	    	  updateMyShiftPreferencesAvailabilitySliders(minShiftLength, maxShiftLength, "Shift length");
	    	  updateMyShiftPreferencesAvailabilitySliders(minShiftsPerWeek, maxShiftsPerWeek, "Shifts per week");
	    	  saveMyShiftPreferencesData();
	      }
	      else 
	    	  SimpleUtils.fail("Profile Page: 'My Shift Preference' edit Container not loaded.", false);
	    	  
	}

	
	private void saveMyShiftPreferencesData() throws Exception {
		if(isElementLoaded(savePreferencesBtn)) {
			click(savePreferencesBtn);
			 if(! isMyShiftPreferenceEditContainerLoaded()) {
		    	  SimpleUtils.pass("Profile Page: 'My Shift Preference' data saved successfully.");
			 }
			 else
				 SimpleUtils.pass("Profile Page: Unable to save 'My Shift Preference' data.");
		}
		else
			SimpleUtils.fail("Profile Page: 'My Shift Preference' edit container 'Save' button not loaded.", false);
	}

	public void clickOnEditMyShiftPreferencePencilIcon()  throws Exception {
		// 
		if(isElementLoaded(collapsibleWorkPreferenceSection)) {
			//WebElement collapsibleRowDiv = collapsibleWorkPreferenceSection.findElement(By.cssSelector("div.collapsible-title"));
			WebElement workPreferenceEditButton = collapsibleWorkPreferenceSection.findElement(By.cssSelector("a[ng-click=\"callEdit($event)\"]"));
			if(isElementLoaded(workPreferenceEditButton)) {
				click(workPreferenceEditButton);
			}
			else 
				SimpleUtils.fail("Profile Page: 'Edit Pencil Icon' not loaded under 'My Shift Preference' Header.", false);
		}
		else 
			SimpleUtils.fail("Profile Page: Collapsible Work Preference Section not loaded under 'My Shift Preference' Tab.", false);
	}
	
	
	
	@FindBy(css="span[ng-if=\"canApprove(r)\"]")
	private WebElement timeOffRequestApproveBtn;
	public boolean isMyShiftPreferenceEditContainerLoaded() throws Exception
	{
		if(isElementLoaded(ditAvailabilityContainerDiv) && ditAvailabilityContainerDiv.isDisplayed()) {
			return true;
		}
		return false;
	}
	
	
	@FindBy(css="collapsible[identifier=\"'availability'\"]")
	private WebElement collapsibleAvailabilitySection;
	@Override
	public boolean isMyAvailabilityCollapsibleWindowOpen() throws Exception
	{
		if(isElementLoaded(collapsibleAvailabilitySection)) {
			WebElement collapsibleRowDiv = collapsibleAvailabilitySection.findElement(By.cssSelector("div.collapsible-title"));
			if(isElementLoaded(collapsibleRowDiv)) {
				if(collapsibleRowDiv.getAttribute("class").contains("open"))
					return true;
			}
			else 
				SimpleUtils.fail("Profile Page: Collapsible 'availability' window not loaded under 'My Work Preference' Tab.", true);
		}
		else 
			SimpleUtils.fail("Profile Page: Collapsible 'availability' Section not loaded under 'My Work Preference' Tab.", false);
		
		return false;
	}
	
	@Override
	public void clickOnMyAvailabilityCollapsibleWindowHeader() throws Exception
	{
		if(isElementLoaded(collapsibleAvailabilitySection)) {
			WebElement collapsibleRowDiv = collapsibleAvailabilitySection.findElement(By.cssSelector("div.collapsible-title"));
			if(isElementLoaded(collapsibleRowDiv)) {
				click(collapsibleRowDiv);
				SimpleUtils.pass("Profile Page: Collapsible Availability window Header clicked under 'My Work Preference' Tab.");
			}
			else 
				SimpleUtils.fail("Profile Page: Collapsible Availability window not loaded under 'My Work Preference' Tab.", true);
		}
		else 
			SimpleUtils.fail("Profile Page: Collapsible Availability Section not loaded under 'My Work Preference' Tab.", false);
	}
 
	@Override
	public HashMap<String, Object> getMyAvailabilityData() throws Exception {
		
		HashMap<String, Object> myAvailabilityData = new HashMap<String, Object>();
		boolean isMyAvailabilityWindowOpen = isMyAvailabilityCollapsibleWindowOpen();
	      if(! isMyAvailabilityWindowOpen)
	    	  clickOnMyAvailabilityCollapsibleWindowHeader();
	      
	      float scheduleHoursValue = 0;
	      float remainingHoursValue = 0;
	      float totalHoursValue = 0;
	      int shiftsCount = 0;
	      String activeWeekText = "";
	      if(isElementLoaded(sheduleHoursData)) {
	    	  scheduleHoursValue = Float.valueOf(sheduleHoursData.getText().replace("(", "").replace(")", ""));
	      }
	      
	      if(isElementLoaded(activeWeek)) {
	    	  activeWeekText = activeWeek.getText();
	      }
	      
	      if(isElementLoaded(remainingHours)) {
	    	  remainingHoursValue = Float.valueOf(remainingHours.getText());
	      }
	      
	      if(isElementLoaded(totalHours)) {
	    	  totalHoursValue = Float.valueOf(totalHours.getText());
	      }
	      
	      shiftsCount = availableShifts.size();
	      
	      
	     // }
	      //else 
	    	//  SimpleUtils.fail("Profile Page: 'My Availability' edit Container not loaded.", false);
	      
	      myAvailabilityData.put("scheduleHoursValue", scheduleHoursValue);
	      myAvailabilityData.put("remainingHoursValue", remainingHoursValue);
	      myAvailabilityData.put("totalHoursValue", totalHoursValue);
		  myAvailabilityData.put("shiftsCount", shiftsCount);
		  myAvailabilityData.put("activeWeekText", activeWeekText);
		return myAvailabilityData;
	}
	
	@Override
	public boolean isMyAvailabilityLocked() throws Exception
	{
		if(isElementLoaded(collapsibleAvailabilitySection)) {
			WebElement collapsibleRowDiv = collapsibleAvailabilitySection.findElement(By.cssSelector("div.collapsible-title"));
			if(isElementLoaded(collapsibleRowDiv)) {
				WebElement myAvailabilityHeaderLockBtn = collapsibleAvailabilitySection.findElement(
						By.cssSelector("a[ng-click=\"sideIconClicked($event)\"]"));
				if(isElementLoaded(myAvailabilityHeaderLockBtn) && myAvailabilityHeaderLockBtn.isDisplayed())
					return true;
			}
			else 
				SimpleUtils.fail("Profile Page: Collapsible 'availability' window not loaded under 'My Work Preference' Tab.", true);
		}
		return false;
	}

	
	@Override
	public ArrayList<HashMap<String, ArrayList<String>>> getMyAvailabilityPreferredAndBusyHours() {
		ArrayList<HashMap<String, ArrayList<String>>> result = new ArrayList<HashMap<String, ArrayList<String>>>();
		if(myAvailabilityDayOfWeekRows.size() > 0) {
			for(WebElement myAvailabilityDayOfWeekRow :myAvailabilityDayOfWeekRows) {
				WebElement rowDowLabelDiv = myAvailabilityDayOfWeekRow.findElement(By.cssSelector("div.dow-label")); 
				HashMap<String, ArrayList<String>> preferredAndBusyDuration = new HashMap<String, ArrayList<String>>();
				List<WebElement> preferredHoursDurations = myAvailabilityDayOfWeekRow.findElements(
						By.cssSelector("div.availability-zone.green-zone"));
				List<WebElement> busyHoursDurations = myAvailabilityDayOfWeekRow.findElements(
						By.cssSelector("div.availability-zone.red-zone"));
				
				ArrayList<String> preferredDuration = new ArrayList<String>();
				for(WebElement preferredHoursDuration :preferredHoursDurations) {
					preferredDuration.add(preferredHoursDuration.getAttribute("innerText"));
				}
				
				ArrayList<String> busyDuration = new ArrayList<String>();
				for(WebElement busyHoursDuration :busyHoursDurations) {
					busyDuration.add(busyHoursDuration.getAttribute("innerText"));
				}
				preferredAndBusyDuration.put("preferredDuration", preferredDuration);
				preferredAndBusyDuration.put("busyDuration", busyDuration);
				result.add(preferredAndBusyDuration);
			}
		}
		else
			SimpleUtils.fail("Profile Page: 'My Availability section' Day of Week Rows not loaded.", true);
		return result;
	}

	
	@Override
	public void updateLockedAvailabilityPreferredOrBusyHoursSlider(String hoursType, int sliderIndex, String leftOrRightSliderArrow,
			int durationMinutes, String repeatChanges) throws Exception {
		if(isMyAvailabilityLocked()) {
			if(isElementLoaded(collapsibleAvailabilitySection)) {
				WebElement collapsibleRowDiv = collapsibleAvailabilitySection.findElement(By.cssSelector("div.collapsible-title"));
				if(isElementLoaded(collapsibleRowDiv)) {
					WebElement myAvailabilityHeaderLockBtn = collapsibleAvailabilitySection.findElement(
							By.cssSelector("a[ng-click=\"sideIconClicked($event)\"]"));
					if(isElementLoaded(myAvailabilityHeaderLockBtn) && myAvailabilityHeaderLockBtn.isDisplayed()) {
						click(myAvailabilityHeaderLockBtn);
						if(isElementLoaded(myAvailabilityUnLockBtn)) {
							click(myAvailabilityUnLockBtn);
							if(isElementLoaded(myAvailabilityUnLockBtn)) {
								click(myAvailabilityUnLockBtn);
								updatePreferredOrBusyHoursDuration(sliderIndex,durationMinutes,leftOrRightSliderArrow, hoursType);
								saveMyAvailabilityEditMode(repeatChanges);
							}
						}
					}
				}
			}
		}
		else {
			SimpleUtils.fail("Profile Page: 'My Availability section' not locked for active week:'"
					+getMyAvailabilityData().get("activeWeek")+"'.", true);
		}
	}
	
	
	private void saveMyAvailabilityEditMode(String availabilityChangesRepeat ) throws Exception {
		if(isElementLoaded(myAvailabilityEditModeSaveBtn)) {
			click(myAvailabilityEditModeSaveBtn);
			if(availabilityChangesRepeat.toLowerCase().contains("repeat forward")) {
				if(isElementLoaded(MyAvailabilityEditSaveRepeatForwordBtn))
					click(MyAvailabilityEditSaveRepeatForwordBtn);
			}
			else {
				if(isElementLoaded(MyAvailabilityEditSaveThisWeekOnlyBtn))
					click(MyAvailabilityEditSaveThisWeekOnlyBtn);
			}
			if(! isElementLoaded(myAvailabilityEditModeHeader, 2)) 
				SimpleUtils.pass("Profile Page: 'My Availability section' edit mode Saved successfully.");
			else
				SimpleUtils.fail("Profile Page: 'My Availability section' edit mode not Saved.", false);
		}
		else
			SimpleUtils.fail("Profile Page: 'My Availability section' edit mode 'save' button not loaded.", true);
		
	}

	
	private void updatePreferredOrBusyHoursDuration(int rowIndex, int durationMinutes, String leftOrRightDuration, String hoursType) throws Exception {
		String preferredHoursTabText = "Preferred";
		String busyHoursTabText = "Busy";
		if(hoursType.toLowerCase().contains(preferredHoursTabText.toLowerCase()))
			selectMyAvaliabilityEditHoursTabByLabel(preferredHoursTabText);
		else
			selectMyAvaliabilityEditHoursTabByLabel(busyHoursTabText);
		
		int xOffSet = ((durationMinutes / 60) * 100) / 2;
		if(leftOrRightDuration.toLowerCase().contains("right")) {
			if(hourCellsResizableCursorsRight.size() > rowIndex) {
				moveElement(hourCellsResizableCursorsRight.get(rowIndex), xOffSet);
				SimpleUtils.pass("My Availability Edit Mode - '"+hoursType+"' Hours Row updated with index - '"+rowIndex+"'.");
			}
			else {
				SimpleUtils.fail("My Availability Edit Mode - '"+hoursType+"' Hours Row not loaded with index - '"+rowIndex+"'.", false);
			}
		}
		else {
			if(hourCellsResizableCursorsLeft.size() > rowIndex) {
				moveElement(hourCellsResizableCursorsLeft.get(rowIndex), xOffSet);
				SimpleUtils.pass("My Availability Edit Mode - '"+hoursType+"' Hours Row updated with index - '"+rowIndex+"'.");
			}
			else {
				SimpleUtils.fail("My Availability Edit Mode - '"+hoursType+"' Hours Row not loaded with index - '"+rowIndex+"'.", false);
			}
		}
	}
	
	
	public void selectMyAvaliabilityEditHoursTabByLabel(String tabLabel) throws Exception
	{
		boolean isTabFound = false;
		if(isElementLoaded(myAvailabilityEditModeHeader)) {
			List<WebElement> myAvailabilityHoursTabs = myAvailabilityEditModeHeader.findElements(
					By.cssSelector("div[ng-click=\"selectTab($event, t)\"]"));
			if(myAvailabilityHoursTabs.size() > 0) {
				for(WebElement myAvailabilityHoursTab : myAvailabilityHoursTabs) {
					if(myAvailabilityHoursTab.getText().toLowerCase().contains(tabLabel.toLowerCase())) {
						isTabFound = true;
						if(myAvailabilityHoursTab.getAttribute("class").contains("select")) {
							SimpleUtils.pass("My Availability Edit Mode - Hours Tab '"+tabLabel+"' already active.");
						}
						else {
							click(myAvailabilityHoursTab);
							SimpleUtils.pass("My Availability Edit Mode - Hours Tab '"+tabLabel+"' selected successfully.");
						}
					}
				}
				if(! isTabFound)
					SimpleUtils.fail("My Availability Edit Mode Hours Tabs '"+tabLabel+"' not found.", false);
			}
			else
				SimpleUtils.fail("My Availability Edit Mode Hours Tabs not loaded.", false);
		}
		else 
			SimpleUtils.fail("My Availability Edit Mode not loaded successfully.", false);
	}
	
	@Override
	public HashMap<String, Integer> getTimeOffRequestsStatusCount() throws Exception{
		HashMap<String, Integer> timeOffRequestsStatusCount = new HashMap<String, Integer>();
		if(isElementLoaded(pendingTimeOffCountDiv)) {
			WebElement pendingBlockCounter = pendingTimeOffCountDiv.findElement(By.cssSelector("span.count-block-counter"));
			if(isElementLoaded(pendingBlockCounter))
				timeOffRequestsStatusCount.put("pendingRequestCount", Integer.valueOf(pendingBlockCounter.getText()));
		}
		
		if(isElementLoaded(approvedTimeOffCountDiv)) {
			WebElement approvedBlockCounter = approvedTimeOffCountDiv.findElement(By.cssSelector("span.count-block-counter"));
			if(isElementLoaded(approvedBlockCounter))
				timeOffRequestsStatusCount.put("approvedRequestCount", Integer.valueOf(approvedBlockCounter.getText()));
		}
		
		if(isElementLoaded(rejectedTimeOffCountDiv)) {
			WebElement rejectedBlockCounter = rejectedTimeOffCountDiv.findElement(By.cssSelector("span.count-block-counter"));
			if(isElementLoaded(rejectedBlockCounter))
				timeOffRequestsStatusCount.put("rejectedRequestCount", Integer.valueOf(rejectedBlockCounter.getText()));
		}
		return timeOffRequestsStatusCount;
	}
	
	
	@Override
	public void approveOrRejectTimeOffRequestFromToDoList(String timeOffReasonLabel, String timeOffStartDuration, 
			String timeOffEndDuration, String action) throws Exception{
		
		String timeOffStartDate = timeOffStartDuration.split(",")[0].split(" ")[1];
		String timeOffStartMonth = timeOffStartDuration.split(",")[0].split(" ")[0];
		String timeOffEndDate = timeOffEndDuration.split(",")[0].split(" ")[1];
		String timeOffEndMonth = timeOffEndDuration.split(",")[0].split(" ")[0];
		
		int timeOffRequestCount = timeOffRequestRows.size();
		if(timeOffRequestCount > 0) {
			for(WebElement timeOffRequest : timeOffRequestRows) {
				WebElement requestType = timeOffRequest.findElement(By.cssSelector("span.request-type"));
				if(requestType.getText().toLowerCase().contains(timeOffReasonLabel.toLowerCase())) {
					WebElement requestDate = timeOffRequest.findElement(By.cssSelector("div.request-date"));
					String[] requestDateText = requestDate.getText().replace("\n", "").split("-");
					if(requestDateText[0].toLowerCase().contains(timeOffStartMonth.toLowerCase()) 
							&& requestDateText[0].toLowerCase().contains(timeOffStartDate.toLowerCase())
							&& requestDateText[1].toLowerCase().contains(timeOffEndMonth.toLowerCase()) 
							&& requestDateText[1].toLowerCase().contains(timeOffEndDate.toLowerCase())) {
						click(timeOffRequest);
						if(action.toLowerCase().contains("cancel")) {
							if(isElementLoaded(timeOffRequestCancelBtn)) {
								click(timeOffRequestCancelBtn);
								SimpleUtils.pass("My Time Off: Time off request cancel button clicked.");
							}
							else
								SimpleUtils.fail("My Time Off: Time off request cancel button not loaded.", true);
						}
						else if(action.toLowerCase().contains("approve")) {
							if(isElementLoaded(timeOffRequestApproveBtn)) {
								click(timeOffRequestApproveBtn);
								SimpleUtils.pass("My Time Off: Time off request Approve button clicked.");
							}
							else
								SimpleUtils.fail("My Time Off: Time off request Approve button not loaded.", true);
						}
					}
				}
			}
		}
		else
			SimpleUtils.fail("Profile Page: No Time off request found.", false);
	}
}
