package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.UserManagementPage;
import com.legion.utils.SimpleUtils;
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
			if (userMagenementTab == null) {
				
			}

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
}

