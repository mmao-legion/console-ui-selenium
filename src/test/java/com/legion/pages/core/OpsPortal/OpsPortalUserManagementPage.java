package com.legion.pages.core.OpsPortal;

import com.legion.pages.BasePage;
import com.legion.pages.OpsPortaPageFactories.UserManagementPage;
import com.legion.utils.SimpleUtils;
import cucumber.api.java.ro.Si;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.*;
import static com.legion.utils.MyThreadLocal.*;


public class OpsPortalUserManagementPage extends BasePage implements UserManagementPage {

	public OpsPortalUserManagementPage() {
		PageFactory.initElements(getDriver(), this);
	}

	// Added by Estelle
	@FindBy(css="[class='console-navigation-item-label User Management']")
	private WebElement userMagenementTab;
	@FindBy(css="[title='Work Roles']")
	private WebElement workRoleTile;
	@FindBy(css="[title='Users and Roles']")
	private WebElement userAndRolesTile;
	@FindBy(css="[title='Dynamic Group']")
	private WebElement dynamicGroupTitle;



	@Override
	public void clickOnUserManagementTab() throws Exception {
		if(isElementLoaded(userMagenementTab,15)){
			click(userMagenementTab);
			if (isElementLoaded(workRoleTile,5)) {
				SimpleUtils.pass("User Management tab is clickable");
			}
		}else
			SimpleUtils.fail("User Management tab not load",false);
	}
	@FindBy(className="lg-dashboard-card__header")
	private List<WebElement> tilesHeaderInWorkRole;
	@FindBy(className="lg-dashboard-card__body")
	private List<WebElement> tilesBodyInWorkRole;

	@Override
	public void verifyWorkRolesTileDisplay() throws Exception {
		if (isElementLoaded(workRoleTile,5)) {
			if (tilesHeaderInWorkRole.get(0).getText().equalsIgnoreCase("Work Roles")) {
				SimpleUtils.pass("The header of Work Role tile show well");
			}else
				SimpleUtils.fail("The header of Work Role is bad",false);
			if (tilesBodyInWorkRole.get(0).getText().equalsIgnoreCase("Work Roles\n" +
					"Work Roles and Hourly Rates\n" +
					"Assignment Rules")) {
				SimpleUtils.pass("The body of Work Role tile show well");
			}else
				SimpleUtils.fail("The body of Work Role is bad",false);

		}else 
			SimpleUtils.fail("Work Roles Tile load failed",false);

	}

	@Override
	public void goToWorkRolesTile() throws Exception {
		if (isElementLoaded(workRoleTile,5)) {
			click(workRoleTile);
			if (isElementLoaded(editBtnInWorkRole,5)) {
				SimpleUtils.pass("Go to work roles tile successfully");
			}else
				SimpleUtils.fail("Failed to work roles tile",false);

		}else
			SimpleUtils.fail("Work Roles Tile load failed",false);

	}
	@FindBy(css = "lg-button[label=\"Edit\"]")
	private WebElement editBtnInWorkRole;
	@FindBy(css = "lg-button[label=\"Add Work Role\"")
	private WebElement addWorkRoleBtn;
	@FindBy(css = "lg-button[label=\"Cancel\"")
	private WebElement cancelBtn;
	@FindBy(css = "a[ng-click=\"$ctrl.back()\"]")
	private WebElement backBtnInWorkRole;
	@Override
	public void verifyEditBtnIsClickable() throws Exception {
		if (isElementLoaded(editBtnInWorkRole,5)) {
			click(editBtnInWorkRole);
			if (isElementLoaded(addWorkRoleBtn,5)) {
				SimpleUtils.pass("Edit button is clickable");
			}else
				SimpleUtils.fail("click Edit button failed",false);
		}else
			SimpleUtils.fail("Edit button load failed",false);

	}

	@Override
	public void verifyBackBtnIsClickable() throws Exception {
		if (isElementLoaded(backBtnInWorkRole,5)) {
			click(backBtnInWorkRole);
			if (isElementLoaded(workRoleTile,5)) {
				SimpleUtils.pass("Back button is clickable");
			}else
				SimpleUtils.pass("click Back button failed");
		}else
			SimpleUtils.fail("Back button load failed",false);


	}

	@FindBy(css = "input[placeholder=\"Work role name\"]")
	private WebElement workNameInputBox;
	@FindBy(css = "select[aria-label=\"Work Role Class\"]")
	private WebElement workRoleClassSelector;
	@FindBy(css = "input[aria-label=\"Hourly rate ($)\"]")
	private WebElement hourRateInputBox;
	@FindBy(css = "span[ng-click=\"showAddDropdownMenu($event)\"]")
	private WebElement plusBtnInAddWorkRolePage;
	@FindBy(css="lg-button[label=\"Leave this page\"]")
	private WebElement leaveThisPageBtn;
	@FindBy(id="workRoleConstraintDropDown")
	private WebElement teamMemberTitle;
	@FindBy(css="div[aria-labelledby=\"workRoleConstraintDropDown\"]>ul>li")
	private List<WebElement> teamMemberTitleDropDownList;
	@FindBy(css="ul[aria-labelledby=\"timeConstraintDropDown\"]>li")
	private List<WebElement> teamWhenApplied;
	@FindBy(id="timeConstraintDropDown")
	private WebElement timeConstraint;
	@FindBy(css="ul[aria-labelledby=\"limitConstraintDropDown\"]>li")
	private List<WebElement> limitConstraintDropDownList;
	@FindBy(id="unitConstraintDropDown")
	private WebElement unitConstraintDropDown;
	@FindBy(css="ul[aria-labelledby=\"unitConstraintDropDown\"]>li")
	private List<WebElement> unitConstraintDropDownList;
	@FindBy(css="modal[modal-title=\"Cancel Editing?\"]")
	private WebElement cancelEditingPage;
	@FindBy(css="lg-button[label=\"Yes\"]")
	private WebElement yesBtnOnCancelEditingPage;



	@Override
	public void cancelAddNewWorkRoleWithoutAssignmentRole(String workRoleName, String colour, String workRole, String hourlyRate) throws Exception {
		if (isElementLoaded(editBtnInWorkRole,5)) {
			click(editBtnInWorkRole);
			click(addWorkRoleBtn);
			workNameInputBox.sendKeys(workRoleName);
			selectByVisibleText(workRoleClassSelector,workRole);
			hourRateInputBox.sendKeys(hourlyRate);
			click(cancelBtn);
			if (isElementEnabled(leaveThisPageBtn,5)) {
				click(leaveThisPageBtn);
			}else
				SimpleUtils.fail("Leave page window load failed",false);
			click(cancelBtn);
			if (isElementEnabled(cancelEditingPage,5)) {
				click(yesBtnOnCancelEditingPage);
				SimpleUtils.pass("Can cancel editing work role successfully");
			}else
				SimpleUtils.fail("Cancel Editing page window load failed",false);
			waitForSeconds(10);

		}else
			SimpleUtils.fail("Edit button load failed",false);

	}

	@Override
	public void addNewWorkRoleWithoutAssignmentRole(String workRoleName, String colour, String workRole, String hourlyRate) throws Exception {
		if (isElementLoaded(editBtnInWorkRole,5)) {
			click(editBtnInWorkRole);
			click(addWorkRoleBtn);
			workNameInputBox.sendKeys(workRoleName);
			selectByVisibleText(workRoleClassSelector,workRole);
			hourRateInputBox.sendKeys(hourlyRate);
			click(saveBtn);
			waitForSeconds(10);

		}else
			SimpleUtils.fail("Edit button load failed",false);
	}
	@FindBy(css = "input[placeholder=\"Search by Work Role\"]")
	private  WebElement serchInputBox;
	@FindBy(css = "tr[ng-repeat=\"workRole in $ctrl.sortedRows\"]")
	private List<WebElement> workRolesRows;


	@FindBy(id="limitConstraintDropDown")
	private List<WebElement> limitConstraintDropDown;
	@Override
	public void verifySearchWorkRole(String workRoleName) throws Exception {
		if (isElementLoaded(serchInputBox,5)) {
			serchInputBox.sendKeys(workRoleName);
			serchInputBox.sendKeys(Keys.ENTER);
			waitForSeconds(10);
			if (workRolesRows.size()>0) {
				SimpleUtils.pass("Work Roles: "+ workRolesRows.size() + " was searched");
			}else
				SimpleUtils.report("There are no  work roles that match your criteria.");
		}else
			SimpleUtils.fail("Search work role input box load failed",false);

	}

	@Override
	public ArrayList<HashMap<String, String>> getWorkRoleInfo(String workRoleName) {
		ArrayList<HashMap<String,String>> workRoleInfo = new ArrayList<>();

		if (isElementEnabled(serchInputBox, 5)) {
			serchInputBox.clear();
			serchInputBox.sendKeys(workRoleName);
			serchInputBox.sendKeys(Keys.ENTER);
			waitForSeconds(5);
			if (workRolesRows.size() > 0) {
				SimpleUtils.pass(workRolesRows.size() + " was searched");
				for (WebElement row : workRolesRows) {
					HashMap<String, String> workRoleInfoInEachRow = new HashMap<>();
					workRoleInfoInEachRow.put("locationName", row.findElement(By.cssSelector("button[type='button']")).getText());
					workRoleInfoInEachRow.put("locationStatus", row.findElement(By.cssSelector("td:nth-child(2) ")).getText());
					workRoleInfo.add(workRoleInfoInEachRow);
				}
				return workRoleInfo;
			}else
				SimpleUtils.report("There are no  work roles that match your criteria.");
				return null;
		}else
			SimpleUtils.fail("Search work role input box load failed",false);
			return null;
	}
	@FindBy(css = "input.setting-work-rule-staffing-numeric-value-edit")
	private List<WebElement> shiftNumberInputAndPriority;
	@FindBy(css = "div[ng-click=\"saveRuleConfirmation($event)\"]")
	private WebElement saveRuleConfirmationBtn;
	@FindBy(css = "ng-click=\"cancelEditRule($event)\"")
	private WebElement cancelEditRuleBtn;
	@FindBy(css = "lg-button[label=\"Save\"]")
	private WebElement saveBtn;



	@Override
	public void updateWorkRole(String workRoleName, String colour, String workRole, String hourlyRate, String selectATeamMemberTitle, String defineTheTimeWhenThisRuleApplies, String specifyTheConditionAndNumber, String shiftNumber, String defineTheTypeAndFrequencyOfTimeRequiredAndPriority, String priority) throws Exception {
		if (workRolesRows.size()>0) {
			List<WebElement> workRoleDetailsLinks = workRolesRows.get(0).findElements(By.cssSelector("button[type='button']"));
			click(editBtnInWorkRole);
			click(workRoleDetailsLinks.get(0));
//			workNameInputBox.clear();
//			workNameInputBox.sendKeys(workRoleName);
			selectByVisibleText(workRoleClassSelector,workRole);
			hourRateInputBox.clear();
			hourRateInputBox.sendKeys(hourlyRate);
			click(plusBtnInAddWorkRolePage);
			//Select a Team Member Title
			click(teamMemberTitle);
			for (WebElement each:teamMemberTitleDropDownList
				 ) {
				if (each.getText().contains(selectATeamMemberTitle)) {
					click(each);
					break;
				}
			}
			//Define the time when this rule applies
			click(timeConstraint);
			for (WebElement each:teamWhenApplied
			) {
				if (each.getText().contains(defineTheTimeWhenThisRuleApplies)) {
					click(each);
					break;
				}
			}

			//Specify the condition and number
			click(limitConstraintDropDown.get(0));
			for (WebElement each:limitConstraintDropDownList
			) {
				if (each.getText().contains(specifyTheConditionAndNumber)) {
					click(each);
					break;
				}
			}

			//shift number and priority input
			shiftNumberInputAndPriority.get(0).sendKeys(shiftNumber);
			shiftNumberInputAndPriority.get(1).clear();
			shiftNumberInputAndPriority.get(1).sendKeys(priority);

			//Define the type and frequency of time required and priority
			click(unitConstraintDropDown);
			for (WebElement each:unitConstraintDropDownList
			) {
				if (each.getText().contains(defineTheTypeAndFrequencyOfTimeRequiredAndPriority)) {
					click(each);
					break;
				}
			}
			click(saveRuleConfirmationBtn);
			click(saveBtn);
			click(saveBtn);
			waitForSeconds(10);

		}
	}

	@Override
	public void addNewWorkRole(String workRoleName, String colour, String workRole, String hourlyRate, String selectATeamMemberTitle, String defineTheTimeWhenThisRuleApplies, String specifyTheConditionAndNumber, String shiftNumber, String defineTheTypeAndFrequencyOfTimeRequiredAndPriority, String priority) throws Exception {
		click(editBtnInWorkRole);
		click(addWorkRoleBtn);
		workNameInputBox.sendKeys(workRoleName);
		selectByVisibleText(workRoleClassSelector,workRole);
		hourRateInputBox.sendKeys(hourlyRate);
		click(plusBtnInAddWorkRolePage);
		//Select a Team Member Title
		click(teamMemberTitle);
		for (WebElement each:teamMemberTitleDropDownList
		) {
			if (each.getText().contains(selectATeamMemberTitle)) {
				click(each);
				break;
			}
		}
		//Define the time when this rule applies
		click(timeConstraint);
		for (WebElement each:teamWhenApplied
		) {
			if (each.getText().contains(defineTheTimeWhenThisRuleApplies)) {
				click(each);
				break;
			}
		}

		//Specify the condition and number
		click(limitConstraintDropDown.get(0));
		for (WebElement each:limitConstraintDropDownList
		) {
			if (each.getText().contains(specifyTheConditionAndNumber)) {
				click(each);
				break;
			}
		}

		//shift number and priority input
		shiftNumberInputAndPriority.get(0).sendKeys(shiftNumber);
		shiftNumberInputAndPriority.get(1).clear();
		shiftNumberInputAndPriority.get(1).sendKeys(priority);

		//Define the type and frequency of time required and priority
		click(unitConstraintDropDown);
		for (WebElement each:unitConstraintDropDownList
		) {
			if (each.getText().contains(defineTheTypeAndFrequencyOfTimeRequiredAndPriority)) {
				click(each);
				break;
			}
		}
		scrollToBottom();
		click(saveRuleConfirmationBtn);
		click(saveBtn);
		click(saveBtn);
		waitForSeconds(10);
	}

	@FindBy(css = "card-carousel-card")
	private WebElement workRoleSmartCard;
	public HashMap<String, Integer> getUpperfieldsSmartCardInfo() {

		HashMap<String, Integer> upperfieldSmartCardText = new HashMap<>();
		if (isElementEnabled(workRoleSmartCard,5)) {
			upperfieldSmartCardText.put("With assignment rule", Integer.valueOf(workRoleSmartCard.findElement(By.cssSelector("  div > ng-transclude > table > tbody > tr:nth-child(2)")).getText().split(" ")[1]));
			upperfieldSmartCardText.put("Without assignment rule", Integer.valueOf(workRoleSmartCard.findElement(By.cssSelector("  div > ng-transclude > table > tbody > tr:nth-child(3)")).getText().split(" ")[1]));
			return upperfieldSmartCardText;
		}

		return null;
	}
	@FindBy(css = "lg-dashboard-card[title=\"Dynamic Group\"]")
	private  WebElement dynamicGroupCard;
	@FindBy(css = "lg-global-dynamic-group-table[dynamic-groups=\"newsFeedDg\"]")
	private  WebElement newsfeedDg;
	@FindBy(css = "form-section[form-title=\"News Feed\"]")
	private  WebElement newsFeedForm;
	@FindBy(css = "lg-button[label=\"Test\"]")
	private  WebElement testBtn;
	@FindBy(css = "input[aria-label=\"Group Name\"]")
	private  WebElement groupNameInput;
	@FindBy(css = "input-field[value=\"$ctrl.dynamicGroup.description\"] >ng-form>input")
	private  WebElement groupDescriptionInput;
	@FindBy(css = "div.fl-left.groupField > input-field > ng-form > div.select-wrapper.ng-scope>select")
	private  List<WebElement> criteriaSelect;
	@FindBy(css = "div.fl-left.groupField > input-field > ng-form > div.select-wrapper.ng-scope>select>option")
	private  List<WebElement> criteriaSelectItems;
	@FindBy(css = "lg-button[label=\"Add More\"]")
	private  WebElement addMoreBtn;

	@FindBy(css = "i.deleteRule")
	private  List<WebElement> deleteRuleIcon;
	@FindBy(css = "lg-button[icon=\"'img/legion/add.png'\"]")
	private  List<WebElement> addDynamicGroupBtn;
	@FindBy(css = "input[placeholder=\"You can search by name and description\"]")
	private  WebElement dgSearchInput;
	@FindBy(css = "lg-global-dynamic-group-table[dynamic-groups=\"newsFeedDg\"] > lg-paged-search-new > div > ng-transclude > table > tbody > tr:nth-child(2) > td.tr > div > lg-button:nth-child(1)")
	private  List<WebElement> editIconInNewsFeedGroup;
	@FindBy(css = "lg-global-dynamic-group-table[dynamic-groups=\"newsFeedDg\"] > lg-paged-search-new > div > ng-transclude > table > tbody > tr.ng-scope > td.tr > div > lg-button:nth-child(2)")
	private  List<WebElement> deleteIconInNewsFeedGroup;


	@FindBy(css = "tr[ng-repeat=\"group in filterdynamicGroups\"]")
	private  List<WebElement> groupRows;
	@FindBy(css = "lg-picker-input[value=\"group.values\"]")
	private  WebElement criteriaValue;
	@FindBy(css = "input[placeholder=\"Search \"")
	private  WebElement searchBoxInCriteriaValue;
	@FindBy(css = "input-field[type=\"checkbox\"]")
	private  List<WebElement> checkboxInCriteriaValue;
	@FindBy(css = "modal[modal-title=\"Remove Dynamic Group\"]")
	private  WebElement removeDGPopup;
	@FindBy(css = "ng-transclude.lg-modal__body")
	private  WebElement removeDGPopupDes;
	@FindBy(css = "lg-button[label=\"Remove\"]")
	private  WebElement removeBtnInRemovDGPopup;
	@FindBy(css = "div.mappingLocation.mt-20> span")
	private  WebElement testBtnInfo;
	@Override
	public boolean iCanSeeDynamicGroupItemTileInUserManagementTab() {
		if (isElementEnabled(dynamicGroupCard,5)) {
			SimpleUtils.pass("Dynamic group card is shown");
			String contextInfo = dynamicGroupCard.getText();
			if (contextInfo.contains("Dynamic Group") && contextInfo.contains("Dynamic Group Configuration") &&
					contextInfo.contains("Newsfeed Group") ) {
				SimpleUtils.pass("Title and description show well");
				return true;
			}else
				SimpleUtils.fail("Title and description are wrong",false);
		}else
			SimpleUtils.report("Dynamic group switch is off");
		    return false;
	}

	@Override
	public void goToDynamicGroup() {
		if (isElementEnabled(dynamicGroupCard,5)) {
			click(dynamicGroupCard);
			waitForSeconds(5);
			if (isElementEnabled(newsfeedDg,5)) {
				SimpleUtils.pass("Can go to dynamic group page successfully");
			}else
				SimpleUtils.fail("Go to dynamic group page failed",false);
		}else
			SimpleUtils.fail("Dynamic group tile load failed",false);
	}

	@Override
	public void searchNewsFeedDynamicGroup(String groupName) throws Exception {
		String[] searchGroupText = groupName.split(",");
		if (isElementLoaded(dgSearchInput, 10) ) {
			for (int i = 0; i < searchGroupText.length; i++) {
				dgSearchInput.clear();
				dgSearchInput.sendKeys(searchGroupText[0]);
				dgSearchInput.sendKeys(Keys.ENTER);
				waitForSeconds(3);
				if (groupRows.size()>0) {
					SimpleUtils.pass("Dynamic group: " + groupRows.size() + " group(s) found  ");
					break;
				} else {
					SimpleUtils.report("There is no groups which you searched");
				}
			}

		} else {
			SimpleUtils.fail("Search input is not clickable", true);
		}
	}

	@Override
	public void iCanDeleteExistingWFSDG() {
		waitForSeconds(10);
		if (groupRows.size()>0) {
			if (areListElementVisible(deleteIconInNewsFeedGroup,30)) {
				for (WebElement dg: deleteIconInNewsFeedGroup) {
					click(dg);
					if (isRemoveDynamicGroupPopUpShowing()) {
						waitForSeconds(3);
						click(removeBtnInRemovDGPopup);
					}else
						SimpleUtils.fail("loRemove dynamic group page load failed ",false);
				}

			}else
				SimpleUtils.report("There is not dynamic group yet");
		}else
			SimpleUtils.report("There is no groups which selected");
	}

	@Override
	public boolean verifyLayoutOfDGDisplay() throws Exception {
		if (isElementLoaded(newsFeedForm,5)) {
			if (newsFeedForm.getText().contains("News Feed") && newsFeedForm.getText().contains("Group")
					&& newsFeedForm.getText().contains("Name") &&newsFeedForm.getText().contains("Description")) {
				SimpleUtils.pass("News Feed Group form show well");
				return true;
			}else
				SimpleUtils.fail("News Feed Group form show wrong",false);
		}else
			SimpleUtils.fail("News Feed form load failed",false);
			return false;
	}

	@FindBy(css = " lg-global-dynamic-group-table > lg-paged-search-new > div > ng-transclude > div.no-record")
	private WebElement noGroupsMessage;

	@Override
	public void verifyDefaultMessageIfThereIsNoGroup() throws Exception {
		if (isElementLoaded(noGroupsMessage,5)) {
			if (noGroupsMessage.getText().contains("There is no dynamic group created yet")) {
				SimpleUtils.pass("Default message when there is no group show well");
			}
		}else
			SimpleUtils.fail("Default message when there is no group load failed",false);
	}

	@Override
	public List<HashMap<String, String>> getExistingGroups() {
		List<HashMap<String,String>> groupsInfo = new ArrayList<HashMap<String,String>>();
		HashMap<String,String> groupText = new HashMap<>();
		if (groupRows.size()>0) {
			for (WebElement groupRow : groupRows) {
				groupText.put("Name",groupRow.findElement(By.cssSelector("td:nth-child(1)")).getText());
				groupText.put("Description",groupRow.findElement(By.cssSelector("td:nth-child(2)")).getText());
			}
			groupsInfo.add(groupText);
			return groupsInfo;
		}else
			return null;
	}


	@Override
	public void iCanGoToManageDynamicGroupPage() {
		if (areListElementVisible(addDynamicGroupBtn)) {
			click(addDynamicGroupBtn.get(0));
			if (isManagerDGpopShowWell()) {
				SimpleUtils.pass("\"+\" button next to dynamic group is clickable ");
				SimpleUtils.pass("Can go to manage dynamic group page successfully");
			} else
				SimpleUtils.fail("Manager Dynamic Group win load failed", false);
		}
	}

	@Override
	public void verifyCriteriaList() throws Exception {
		if (areListElementVisible(criteriaSelect,5)) {
			for (WebElement s : criteriaSelectItems) {
				if(s.getText().contains("Exempt")) {
					SimpleUtils.pass("Criteria include: Exempt");
				}
				else if(s.getText().contains("Work Role")) {
					SimpleUtils.pass("Criteria include: Work Role");
				}
				else if(s.getText().contains("Employment Type")) {
					SimpleUtils.pass("Criteria include: Employment Type");
				}
				else if(s.getText().contains("Employment Status")) {
					SimpleUtils.pass("Criteria include: Employment Status");
				}
				else if(s.getText().contains("Minor")) {
					SimpleUtils.pass("Criteria include: Minor");
				}
				else if(s.getText().contains("Badge")) {
					SimpleUtils.pass("Criteria include: Badge");
				}
				else if(s.getText().contains("Custom")) {
					SimpleUtils.pass("Criteria include: Custom");
				}
			}
		}else
			SimpleUtils.fail("Criteria select load failed",false);
	}

	@Override
	public void testButtonIsClickable() throws Exception {
		if (isElementLoaded(testBtn,5)) {
			click(testBtn);
			if (isElementLoaded(testBtnInfo,5)) {
				SimpleUtils.pass("Test button is clickable");
			}else
				SimpleUtils.fail("Test Mapping info doesn't show",false);
		}else
			SimpleUtils.fail("Test button load failed",false);
	}

	@Override
	public void addMoreButtonIsClickable() throws Exception {
		int currentCriteriaNum = criteriaSelect.size();
		if (isElementLoaded(addMoreBtn,5)) {
			click(addMoreBtn);
			int criteriaNumAftAddMore = criteriaSelect.size();
			if (criteriaNumAftAddMore >currentCriteriaNum) {
				SimpleUtils.pass("Add more button is clickable and can add one more criteria");
			}else
				SimpleUtils.fail("Add more button work failed",false);
		}else
			SimpleUtils.fail("Add more button load failed",false);
	}
	@FindBy(css="div.CodeMirror-scroll")
	private WebElement DescriptionFormForCustom;
	@Override
	public void criteriaDescriptionDisplay() throws Exception {
		if (areListElementVisible(criteriaSelect,5)) {
			selectByVisibleText(criteriaSelect.get(0),"Custom");
			if (isElementLoaded(DescriptionFormForCustom,5) && DescriptionFormForCustom.getText().contains("Enter your expression. The dynamic group will only be created if the expresion evaluates to be true.")) {
				SimpleUtils.pass("Default description for custom criteria show well");
			}else
				SimpleUtils.fail("Default description for custom criteria show wrong",false);
		}else
			SimpleUtils.fail("Criteria selector load failed",false);
	}

	@FindBy(css = "div.fl-left>i.fa[ng-click=\"$ctrl.deleteRule($index)\"]")
	private List<WebElement> removeCriteriaBtn;
	@Override
	public void removeCriteriaBtnIsClickAble() {
		int beforeRemove = criteriaSelect.size();
		if (areListElementVisible(removeCriteriaBtn,5)) {
			click(removeCriteriaBtn.get(0));
			int aftRemove = criteriaSelect.size();
			if (aftRemove <beforeRemove) {
				SimpleUtils.pass("X button next to criteria is clickable");
			}
		}else
			SimpleUtils.fail("X button next to criteria load failed",false);
	}

	@Override
	public void cancelBtnIsClickable() throws Exception {
		if (isElementLoaded(cancelBtn,5)) {
			click(cancelBtn);
			if (isElementLoaded(newsFeedForm,5)) {
				SimpleUtils.pass("Cancel Button is clickable");
			}else
				SimpleUtils.fail("Cancel creating failed",false);
		}else
			SimpleUtils.fail("Cancel Button load failed",false);
	}
	@FindBy(css="lg-button[label=\"OK\"]")
	private WebElement  okBtnInCreateNewsFeedGroupPage;
	@Override
	public String addNewsFeedGroupWithOneCriteria(String groupNameForNewsFeed, String description, String criteria) throws Exception {
		if (areListElementVisible(addDynamicGroupBtn)) {
			click(addDynamicGroupBtn.get(0));
			if (isManagerDGpopShowWell()) {
				groupNameInput.sendKeys(groupNameForNewsFeed);
				groupDescriptionInput.sendKeys(description);
				selectByVisibleText(criteriaSelect.get(0),criteria);
				click(criteriaValue);
				click(checkboxInCriteriaValue.get(0));
				click(criteriaValue);
				click(testBtn);
				waitForSeconds(25);
				String testInfo = testBtnInfo.getText().trim();
				click(okBtnInCreateNewsFeedGroupPage);
				waitForSeconds(3);
				if (isElementLoaded(newsFeedForm,5)) {
					SimpleUtils.pass("Ok button in Dynamic group creating page is clickable");
				}
				searchNewsFeedDynamicGroup(groupNameForNewsFeed);
				if (groupRows.size()>0) {
					SimpleUtils.pass("News Feed Dynamic group create successfully");
				}else
					SimpleUtils.fail("News Feed  Dynamic group create failed",false);
				return testInfo;
			}else
				SimpleUtils.fail("Manager Dynamic Group win load failed",false);
		}else
			SimpleUtils.fail("Global dynamic group page load failed",false);

		return null;
	}

	@Override
	public String updateNewsFeedDynamicGroup(String groupNameForNewsFeed, String criteriaUpdate) throws Exception {
		dgSearchInput.clear();
		dgSearchInput.sendKeys(groupNameForNewsFeed);
		dgSearchInput.sendKeys(Keys.ENTER);
		click(editIconInNewsFeedGroup.get(0));
		if (isManagerDGpopShowWell()) {
			groupNameInput.clear();
			groupNameInput.sendKeys(groupNameForNewsFeed+"Update");
			selectByVisibleText(criteriaSelect.get(0),criteriaUpdate);
			click(criteriaValue);
			click(checkboxInCriteriaValue.get(0));
			click(criteriaValue);
			click(testBtn);
			waitForSeconds(25);
			String testInfo = testBtnInfo.getText().trim();
			click(okBtnInCreateNewsFeedGroupPage);
			waitForSeconds(3);
			searchNewsFeedDynamicGroup(groupNameForNewsFeed+"Update");
			if (groupRows.size()>0) {
				SimpleUtils.pass("News Feed Dynamic group update successfully");
			}else
				SimpleUtils.fail("News Feed Dynamic group create failed",false);
			return testInfo;
		}else
			SimpleUtils.fail("Manager Dynamic Group win load failed",false);
		return null;
	}

	@Override
	public void verifyAddNewsFeedGroupWithExistingGroupName(String groupNameForNewsFeed, String description) throws Exception {
		if (areListElementVisible(addDynamicGroupBtn)) {
			click(addDynamicGroupBtn.get(0));
			if (isManagerDGpopShowWell()) {
				groupNameInput.sendKeys(groupNameForNewsFeed);
				groupDescriptionInput.sendKeys(description);
				click(okBtnInCreateNewsFeedGroupPage);
				waitForSeconds(3);
				click(cancelBtn);
				searchNewsFeedDynamicGroup(groupNameForNewsFeed);
				if (groupRows.size()==1) {
					SimpleUtils.pass("Can not create Dynamic group with existing group name");
				}else
					SimpleUtils.fail("Should not create group with existing group name",false);
			}else
				SimpleUtils.fail("Manager Dynamic Group win load failed",false);
		}else
			SimpleUtils.fail("Global dynamic group page load failed",false);
	}

	@Override
	public void verifyAddNewsFeedGroupWithDifNameSameCriterias(String groupNameForNewsFeed2, String description, String criteria) throws Exception {
		if (areListElementVisible(addDynamicGroupBtn)) {
			click(addDynamicGroupBtn.get(0));
			if (isManagerDGpopShowWell()) {
				groupNameInput.sendKeys(groupNameForNewsFeed2);
				groupDescriptionInput.sendKeys(description);
				selectByVisibleText(criteriaSelect.get(0),criteria);
				click(criteriaValue);
				click(checkboxInCriteriaValue.get(0));
				click(criteriaValue);
				click(okBtnInCreateNewsFeedGroupPage);
				waitForSeconds(3);
				click(cancelBtn);
				searchNewsFeedDynamicGroup(groupNameForNewsFeed2);
				if (groupRows.size()==0) {
					SimpleUtils.pass("Can not create Dynamic group with different name and same criteria");
				}else
					SimpleUtils.fail("Should not create group with different name and same criteria",false);
			}else
				SimpleUtils.fail("Manager Dynamic Group win load failed",false);
		}else
			SimpleUtils.fail("Global dynamic group page load failed",false);
	}

	@FindBy(css = "modal[modal-title=\"Manage Dynamic Group\"]>div")
	private WebElement managerDGpop;

	private boolean isManagerDGpopShowWell() {
		if (isElementEnabled(managerDGpop,5)&& isElementEnabled(groupNameInput,5)&&
				isElementEnabled(groupDescriptionInput,5)&&areListElementVisible(criteriaSelect,5)
				&& isElementEnabled(testBtn,5) && isElementEnabled(addMoreBtn,5)) {
			SimpleUtils.pass("Manager Dynamic Group win show well");
			return true;
		}else
			return false;
	}

	@FindBy(css = "div.fl-left.groupName > input-field[label=\"Group Name\"]> ng-form > lg-input-error > div > span")
	private WebElement groupNameErrorInput;
	@Override
	public void verifyNameInputField(String groupNameForNewsFeed) throws Exception {
		if (isElementLoaded(groupNameInput,5)) {
			groupNameInput.sendKeys(groupNameForNewsFeed);
			groupNameInput.clear();
			if (groupNameErrorInput.getText().contains("Group Name is required")) {
				SimpleUtils.pass("Group name blank validation show well");
			}else
				SimpleUtils.fail("Missing validation of group name when it's blank",false);
		}else
			SimpleUtils.fail("Group name input field load failed",false);
	}


	private boolean isRemoveDynamicGroupPopUpShowing() {
		if (isElementEnabled(removeDGPopup,5) && removeDGPopupDes.getText().contains("Are you sure you want to remove this dynamic group?")
				&& isElementEnabled(removeBtnInRemovDGPopup,5)) {
			SimpleUtils.pass("Remove dynamic group page show well");
			return true;
		}
		return false;
	}

	@FindBy(css = "lg-dashboard-card[title=\"Users and Roles\"]")
	private  WebElement usersAndRolesCard;
	@FindBy(css = "lg-button[label=\"Add New User\"]")
	private WebElement addNewUserBtn;
	@Override
	public void goToUserAndRoles() {
		if (isElementEnabled(usersAndRolesCard,5)) {
			click(usersAndRolesCard);
		//	waitForSeconds(15);
			if (isElementEnabled(addNewUserBtn,5)) {
				SimpleUtils.pass("Can go to Users and Roles page successfully");
			}else
				SimpleUtils.fail("Go to Users and Roles page failed",false);
		}
	}
	@FindBy(css = "div.lg-tabs__nav-item:nth-child(2)")
	private WebElement accessRoleTab;
	@FindBy(css = "div.group-header-row")
	private List<WebElement> accessRolePermissions;
	@Override
	public void goToAccessRolesTab() {
		if (isElementEnabled(accessRoleTab,5)) {
			click(accessRoleTab);
			if (areListElementVisible(accessRolePermissions,5)) {
				SimpleUtils.pass("Can go to Access Role successfully");
			}else
				SimpleUtils.fail("Go to Access Role failed",false);
		}

	}
	@FindBy(css = "div.table-row:nth-child(6)>div:nth-child(1)")
	private WebElement templateLocalization;
	@FindBy(css = "div.table-row:nth-child(3)>div:nth-child(1)")
	private WebElement createEditTemplates;
	@FindBy(css = "div.table-row:nth-child(8)>div:nth-child(1)")
	private WebElement operationManagement;
	@FindBy(css = "div.table-row:nth-child(9)>div:nth-child(1)")
	private WebElement viewTemplate;
	@Override
	public void verifyManageItemInUserManagementAccessRoleTab() throws Exception {
		if (areListElementVisible(accessRolePermissions,5)) {
			for (WebElement element: accessRolePermissions){
				if (element.getText().equalsIgnoreCase("Manage")){
					clickTheElement(element);
					if (templateLocalization.getText().contains("Template Localization")&& createEditTemplates.getText().contains("Create/Edit Templates")
							&& operationManagement.getText().contains("Operation Management") &&viewTemplate.getText().contains("View Template") ) {
						SimpleUtils.pass("Template Localization,Create/Edit Templates,Operation Management and View Template load successfully ");
					}else
						SimpleUtils.fail("Template Localization,Create/Edit Templates,Operation Management and View Template load failed",false);
				}
			}
		}else
			SimpleUtils.fail("Access Role Permissions items load failed",false);
	}

	@Override
	public void verifyRemoveTheConditionFromDropDownListIfItSelected() throws Exception {
		if (areListElementVisible(addDynamicGroupBtn)) {
			click(addDynamicGroupBtn.get(0));
			if (isManagerDGpopShowWell()) {
				selectByVisibleText(criteriaSelect.get(0), "Work Role");
				click(criteriaValue);
				click(checkboxInCriteriaValue.get(0));
				click(criteriaValue);
				//add second criteria
				click(addMoreBtn);
				if (areListElementVisible(criteriaSelectItems, 5)) {
					for (int i = 8; i < criteriaSelectItems.size(); i++) {
						if (criteriaSelectItems.get(i).getText().contains("Work Role")) {
							SimpleUtils.fail("Selected condition is still shown",false);
							break;
						}
					}
				} else
					SimpleUtils.fail("Value list load failed", false);
			} else
				SimpleUtils.fail("Manager dynamic group page load failed", false);
		}else
			SimpleUtils.fail("Add Global dynamic group button load failed", false);
	}

	@Override
	public void goToWorkRolesDetails(String workRoleName) throws Exception {
		if (workRolesRows.size()>0) {
			for (WebElement e :workRolesRows) {
				e.getText().contains(workRoleName);
				click(e.findElement(By.cssSelector("button[type=\"button\"]")));
				if (isElementLoaded(workNameInputBox,5)) {
					SimpleUtils.pass("Can go to "+workRoleName +" details page successfully");
				}else
					SimpleUtils.fail("Go to "+workRoleName +" details page failed",false);
				break;
			}
		}else 
			SimpleUtils.fail("",false);
	}
	@FindBy(className = "settings-work-rule-number")
	private List<WebElement> enableDisableIcons;
	@FindBy(css = "div[ng-click=\"clickRuleEnable()\"]")
	private List<WebElement> enableDisableRuleBtn;
	@Override
	public void disableAssignmentRulesInLocationLevel(int index) {
		click(enableDisableIcons.get(index));
		if (enableDisableIcons.size()>0) {
			for (int i = index; i <enableDisableIcons.size() ; i++) {
				if (enableDisableIcons.get(i).getAttribute("data-tootik").contains("enable")) {
					click(enableDisableRuleBtn.get(i));
					if (enableDisableIcons.get(i).getAttribute("data-tootik").contains("disable")) {
						SimpleUtils.pass("Disable assignment rule successfully");
					}else
						SimpleUtils.fail("Disable assignment rule failed",false);
					break;
				}
			}
			click(saveRuleConfirmationBtn);
		}else
			SimpleUtils.report("There is no assignment rule");
	}

	@Override
	public void enableAssignmentRulesInLocationLevel(int index) {
		click(enableDisableIcons.get(index));
		if (enableDisableIcons.size()>0) {
			for (int i = index; i <enableDisableIcons.size() ; i++) {
				if (enableDisableIcons.get(i).getAttribute("data-tootik").contains("disable")) {
					click(enableDisableRuleBtn.get(i));
					if (enableDisableIcons.get(i).getAttribute("data-tootik").contains("enable")) {
						SimpleUtils.pass("Enable assignment rule successfully");
					}else
						SimpleUtils.fail("Enable assignment rule failed",false);
					break;
				}
			}
			click(saveRuleConfirmationBtn);
		}else
			SimpleUtils.report("There is no assignment rule");
	}

	@Override
	public void overriddenAssignmentRule(int index) {
		if (enableDisableIcons.size()>0) {
			for (int i = index; i <enableDisableIcons.size() ; i++) {
				if (enableDisableIcons.get(i).getAttribute("data-tootik").contains("enable")) {
					disableAssignmentRulesInLocationLevel(i);
					break;
				}else
					enableAssignmentRulesInLocationLevel(i);
				    break;
			}
		}else
			SimpleUtils.report("There is no assignment rule");
	}

	@FindBy(css = "div.table-row:nth-child(2)>div:nth-child(1)")
	private WebElement archiveBudgetPlanScenario;
	@FindBy(css = "div.table-row:nth-child(3)>div:nth-child(1)")
	private WebElement viewBudgetPlan;
	@FindBy(css = "div.table-row:nth-child(4)>div:nth-child(1)")
	private WebElement setBudgetPlanInEffect;
	@FindBy(css = "div.table-row:nth-child(5)>div:nth-child(1)")
	private WebElement approveBudgetPlan;
	@FindBy(css = "div.table-row:nth-child(6)>div:nth-child(1)")
	private WebElement submitForReview;
	@FindBy(css = "div.table-row:nth-child(7)>div:nth-child(1)")
	private WebElement createBudgetPlanScenario;
	@FindBy(css = "div.table-row:nth-child(8)>div:nth-child(1)")
	private WebElement createBudgetPlan;
	@FindBy(css="div.table-row:nth-child(1)>div[ng-repeat]")
	private List<WebElement> accessRolesList;
	@FindBy(css="div.table-container.ng-scope div[ng-repeat=\"permission in value\"]")
	private List<WebElement> permissionsList;


	@Override
	public void verifyPlanItemInUserManagementAccessRoleTab() throws Exception {
		if (areListElementVisible(accessRolePermissions,5)) {
			for (WebElement element: accessRolePermissions){
				if (element.getText().equalsIgnoreCase("Plan")){
					clickTheElement(element);
					if (createBudgetPlanScenario.getText().contains("Create Budget Plan Scenario")&& archiveBudgetPlanScenario.getText().contains("Archive Budget Plan Scenario")
							&& viewBudgetPlan.getText().contains("View Budget Plan") && setBudgetPlanInEffect.getText().contains("Set Budget Plan in Effect")
							&& createBudgetPlan.getText().contains("Create Budget Plan") && approveBudgetPlan.getText().contains("Approve Budget Plan")
					        && submitForReview.getText().contains("Submit for Review")) {
						SimpleUtils.pass("Plan permission items loaded successfully!");
					}else
						SimpleUtils.fail("Plan permission items loaded failed",false);
				}
			}
		}else
			SimpleUtils.fail("Access Role Permissions items load failed",false);
	}

	@Override
	public int getIndexOfRolesInPermissionsTable(String role) throws Exception {
		int index = 0;
		if(areListElementVisible(accessRolesList,5)){
			for(WebElement accessRole:accessRolesList){
				if(accessRole.getText().trim().equalsIgnoreCase(role)){
					index  = accessRolesList.indexOf(accessRole);
					break;
				}
			}
		}
		return index;
	}

    @Override
	public boolean verifyPermissionIsCheckedOrNot(int index) throws Exception{
		boolean flag = false;
		if(areListElementVisible(permissionsList,5)){
			for(WebElement permission:permissionsList){
				WebElement permissionValue = permission.findElements(By.cssSelector("div[ng-repeat]")).get(index);
				String permissionName = permission.findElement(By.cssSelector("div[title]")).getText().trim();
				if(permissionValue.getAttribute("checked") != null){
					SimpleUtils.pass(permissionName + " is checked already!");
					flag = true;
				}else {
					SimpleUtils.report(permissionName + " is NOT checked!");
				}
			}
		}
		return flag;
	}

	@FindBy(css = "input[placeholder='You can search by name, job title, location, etc.']")
	private WebElement usersSearchBox;
	@FindBy(css = "lg-button.ng-isolate-scope")
	private WebElement user;

	@Override
	public void goToUserDetailPage(String users) throws Exception{
		if (isElementEnabled(accessRoleTab,5)) {
			highlightElement(usersSearchBox);
			usersSearchBox.sendKeys(users);
			usersSearchBox.sendKeys(Keys.ENTER);
			waitForSeconds(3);
			if(isElementEnabled(user,5)){
				highlightElement(user);
				click(user);
			}else
				SimpleUtils.fail("user " + users + "search failed",false);
		}else{
			SimpleUtils.fail("usersSearchBox loaded failed",false);
		}
	}

	@FindBy(css = "input-field[label='HR'] input")
	private WebElement HR;
	@FindBy(css = "input-field[label='Operations Manager'] input")
	private WebElement OperationsManager;
	@FindBy(css = "input-field[label='Communications'] input")
	private WebElement Communications;
	@FindBy(css = "input-field[label='Budget Planner'] input")
	private WebElement BudgetPlanner;

	public int verifyAccessRoleSelected() throws Exception{
		waitForSeconds(10);
		BasePage.scrollToBottom();
		if (isElementLoaded(HR,10) && isElementLoaded(OperationsManager,10) && isElementLoaded(Communications,10) && isElementLoaded(BudgetPlanner,10)) {
			int flag;
			if(HR.isSelected() && OperationsManager.isSelected() && Communications.isSelected() && BudgetPlanner.isSelected()){
				flag = 1;
			}else if(!HR.isSelected() && !OperationsManager.isSelected() && !Communications.isSelected() && !BudgetPlanner.isSelected()){
				flag = 2;
			}else
				flag =3;

			return flag;
		}else{
			SimpleUtils.fail("access role loaded failed",false);
			return 0;
		}
	}

	@FindBy(css = "timeoff-management div.collapsible-title")
	private WebElement timeOffTab;
	@FindBy(css = "div.balance-action lg-button[label='History']>button")
	private WebElement history;
	@FindBy(css = "div#logContainer.lg-slider-pop__content.mt-20")
	private WebElement historyDetail;

	@Override
	public void verifyHistoryDeductType() throws Exception {
		if(isElementEnabled(timeOffTab,10)){
			highlightElement(timeOffTab);
			click(timeOffTab);
			if(isElementEnabled(history,5)){
				highlightElement(history);
				click(history);
				if(isElementEnabled(historyDetail,5)){
					highlightElement(historyDetail);
					if (historyDetail.getText().contains("max carryover") && historyDetail.getText().contains("max available") && historyDetail.getText().contains("annual earn limit")){
						SimpleUtils.pass("deducted type display");
					}else
						SimpleUtils.fail("deducted type doesn't display",false);
				}else
					SimpleUtils.fail("user history detail loaded failed",false);
			}else
				SimpleUtils.fail("user history loaded failed",false);
		}else
			SimpleUtils.fail("user time off tab loaded failed",false);
	}

	@FindBy(xpath = "//nav[@class='lg-tabs__nav']/div[3]")
	private WebElement jobTitleAccess;

	public void goToJobTitleAccess() throws Exception{
		if(isElementEnabled(jobTitleAccess,5)){
			click(jobTitleAccess);
			SimpleUtils.pass("Job Title Access is clickable");
		}else
			SimpleUtils.fail("Job Title Access loaded failed",false);
	}

	@FindBy(css = "lg-button[label='Add Job Title']>button")
	private WebElement addJobTitle;

	public void clickAddJobTitle() throws Exception{
		if(isElementEnabled(addJobTitle,5)){
			click(addJobTitle);
			SimpleUtils.pass("Add job title is clickable");
		}else
			SimpleUtils.fail("Add job title loaded failed",false);
	}

	@FindBy(css = "input[placeholder='Name of Job Title']")
	private WebElement jobTitleName;

	public void inputJobTitleName(String name) throws Exception{
		if(isElementLoaded(jobTitleName,5)){
			jobTitleName.sendKeys(name);
			SimpleUtils.pass("Input job title name successfully");
		}else
			SimpleUtils.fail("Job title name input box loaded failed",false);
	}

	@FindBy(css = "input[aria-label='Admin']")
	private WebElement accessRole;

	public void selectAccessRole() throws Exception{
		if(isElementLoaded(accessRole,5)){
			click(accessRole);
			SimpleUtils.pass("Select access role successfully");
		}else
			SimpleUtils.fail("Access role loaded failed",false);
	}

	@FindBy(css = "lg-button[label='Save']>button")
	private WebElement saveJobTitleButton;

	public void saveJobTitle() throws Exception{
		if(isElementLoaded(saveJobTitleButton,5)){
			click(saveJobTitleButton);
			SimpleUtils.pass("Save job title successfully");
		}else
			SimpleUtils.fail("Job title save button loaded failed",false);
	}

	@FindBy(css = "lg-button[label='cancel']>button")
	private WebElement cancelJobTitleButton;

	public void cancelJobTitle() throws Exception{
		if(isElementLoaded(cancelJobTitleButton,5)){
			click(cancelJobTitleButton);
			SimpleUtils.pass("Cancel job title successfully");
		}else
			SimpleUtils.fail("Cancel title save button loaded failed",false);
	}

	@FindBy(css = "input[placeholder='You can search by employee job title.']")
	private WebElement searchJobTitleInputBox;
	@FindBy(css ="td.ng-binding")
	private WebElement searchJobTitleResult;

	public void searchJobTitle(String name) throws Exception{
		if(isElementLoaded(searchJobTitleInputBox,5)){
			searchJobTitleInputBox.clear();
			searchJobTitleInputBox.sendKeys(name);
			if(isElementLoaded(searchJobTitleResult,5)){
				SimpleUtils.pass("Search job title " + name + " successfully");
			}else
				SimpleUtils.fail("Job title is not match with searched",false);
		}else
			SimpleUtils.fail("Job title search input box loaded failed",false);
	}

	@FindBy(css = "lg-button[label = 'Remove']>button")
	private WebElement removeJobTitleButton;

	public void removeJobTitle() throws Exception{
		if(isElementLoaded(removeJobTitleButton,5)){
			click(removeJobTitleButton);
			SimpleUtils.pass("Remove job title successfully");
		}else
			SimpleUtils.fail("Remove job title button loaded failed",false);
	}

	@FindBy(css = "lg-button[label = 'Show Rate']>button")
	private WebElement showRate;
	@FindBy(css = "lg-button[label = 'Hide Rate']>button")
	private WebElement hideRate;
	@FindBy(css = "div[ng-if='canViewHourlyRate']>div.value.ng-binding")
	private WebElement hourlyRate;

	public boolean isHourlyRateExist() throws Exception{
		if(!isExist(showRate)&&!isExist(hideRate)&&!isExist(hourlyRate))
			return false;
		else
			return true;
	}

	public void clickShowRate() throws Exception{
		if(isElementLoaded(showRate,5)){
			click(showRate);
			SimpleUtils.pass("Click show rate button successfully");
		}else
			SimpleUtils.fail("Show rate button loaded failed",false);
	}

	public void clickHideShowRate() throws Exception{
		if(isElementLoaded(hideRate,5)){
			click(hideRate);
			SimpleUtils.pass("Click hide rate button successfully");
		}else
			SimpleUtils.fail("Hide rate button loaded failed",false);
	}

	public String getHourlyRateValue() throws Exception{
		String hourly = "";
		if(isElementLoaded(hourlyRate,5)){
			scrollToElement(hourlyRate);
			hourly = hourlyRate.getAttribute("innerText");
		}else
			SimpleUtils.fail("Hide rate value loaded failed",false);
		return hourly;
	}

	@FindBy(css = "div[title = ' View Hourly Rate']")
	private WebElement viewHourlyRate;

	public void verifyViewHourlyRate() throws Exception{
		if(isElementLoaded(viewHourlyRate,5)){
			if(viewHourlyRate.getText().equals("View Hourly Rate"))
				SimpleUtils.pass("View hourly rate permission text is correct");
			else
				SimpleUtils.fail("View hourly rate permission text is wrong",false);
		}else{
			SimpleUtils.fail("View hourly rate display failed",false);
		}
	}

	@FindBy(css = "div.group.ng-scope:nth-child(8)")
	private WebElement profile;

	public void clickProfile() throws Exception{
		if(isElementLoaded(profile,5)){
			click(profile);
			SimpleUtils.pass("Click profile successfully");
		}else{
			SimpleUtils.fail("profile loaded failed",false);
		}
	}

	@FindBy(css = "span[ng-click= 'back()']")
	private WebElement backButton;

	public void goBack() throws Exception{
		if(isElementLoaded(backButton,5)){
			click(backButton);
			SimpleUtils.pass("Click back button successfully");
		}else{
			SimpleUtils.fail("Back button loaded failed",false);
		}
	}

	@FindBy(css = "div[title = ' View Employee Phone']")
	private WebElement viewEmployeePhone;
	@FindBy(css = "div[title = ' View Employee Email']")
	private WebElement viewEmployeeEmail;
	@FindBy(css = "div[title = ' View Employee Address']")
	private WebElement viewEmployeeAddress;
	@FindBy(css = "div[title = ' View Employee Work Preferences']")
	private WebElement viewEmployeeWorkPreferences;
	@FindBy(css = "div[title = ' View Employee Work Preferences Requests']")
	private WebElement viewEmployeeWorkPreferencesRequests;
	@FindBy(css = "div[title = ' View Employee Time Off']")
	private WebElement viewEmployeeTimeOff;
	@FindBy(css = "div[title = ' View Employee Time Off Requests']")
	private WebElement viewEmployeeTimeOffRequests;

	public boolean profileViewPermissionExist() throws Exception{
		if(isElementDisplayed(viewHourlyRate) && isElementDisplayed(viewEmployeePhone) && isElementDisplayed(viewEmployeeEmail) && isElementDisplayed(viewEmployeeAddress) && isElementDisplayed(viewEmployeeTimeOffRequests)
				&& isElementDisplayed(viewEmployeeWorkPreferences) && isElementDisplayed(viewEmployeeWorkPreferencesRequests) && isElementDisplayed(viewEmployeeTimeOff))
			return true;
		else
			return false;
	}

	@FindBy(css = "div[ng-if = '(isViewMode() || (!isMe && !tm.worker.requiresOnboarding)) && canViewEmployeeAddress']")
	private WebElement profileAddress;
	@FindBy(css = "span.email.ng-binding")
	private WebElement profileEmail;
	@FindBy(css = "span.phone.ng-binding")
	private WebElement profilePhone;
	@FindBy(css = "div:nth-child(2) > collapsible > collapsible-base > div > div.collapsible-title > div.collapsible-title-text")
	private WebElement workPreferences;
	@FindBy(css = "lg-button[label = 'Edit']>button")
	private WebElement createWorkPreferences;
	@FindBy(css = "timeoff-management div.collapsible-title")
	private WebElement timeOff;
	@FindBy(css = "lg-button[label = 'Create time off']>button")
	private WebElement createTimeOff;
	@FindBy(css = "div[ng-if = 'canViewEmployeeAddress']>div.value.ng-binding")
	private WebElement HRFileAddress;

	public boolean verifyProfilePermission() throws Exception {
		waitForSeconds(3);
		if (isElementDisplayed(HRFileAddress) && isElementDisplayed(profileAddress) && isElementDisplayed(profileEmail) && isElementDisplayed(profilePhone) && isElementDisplayed(workPreferences) && isElementDisplayed(timeOff)) {
			click(workPreferences);
			if (isExist(createWorkPreferences)) {
				click(timeOff);
				if (isExist(createTimeOff)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}else{
			return false;
		}
	}

	@FindBy(css = "div.group.ng-scope:nth-child(7)")
	private WebElement manage;

	public void clickManage() throws Exception{
		if(isElementLoaded(manage,5)){
			click(manage);
			SimpleUtils.pass("Click manage successfully");
		}else{
			SimpleUtils.fail("manage loaded failed",false);
		}
	}

	@FindBy(css = "div[title = ' Recalculate Accrual Balance']")
	private WebElement recalculatePermission;

	public void verifyRecalculatePermission() throws Exception{
		if(isElementLoaded(recalculatePermission,5)){
			if(recalculatePermission.getText().equals("Recalculate Accrual Balance"))
				SimpleUtils.pass("Recalculate Accrual Balance text is correct");
			else
				SimpleUtils.fail("Recalculate Accrual Balance text is wrong",false);
		}else
			SimpleUtils.fail("Recalculate Accrual Balance display failed",false);
	}

	@FindBy(css = "lg-button[label = 'Refresh Balances']>button")
	private WebElement refreshBalances;

	public void clickRefreshBalances() throws Exception{
		if(isElementLoaded(refreshBalances,5)){
			click(refreshBalances);
			SimpleUtils.pass("Click Refresh Balances successfully");
		}else
			SimpleUtils.fail("Refresh Balances display failed",false);
	}

	public void isRefreshBalancesDisplayed() throws Exception{
		if(!isElementLoaded(refreshBalances,5)){
			SimpleUtils.pass("Refresh Balances button doesn't display");
		}else{
			SimpleUtils.fail("Refresh Balances button display",false);
		}
	}
}

