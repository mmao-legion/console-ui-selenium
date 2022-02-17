package com.legion.pages.core.OpsPortal;

import com.legion.pages.BasePage;
import com.legion.pages.OpsPortaPageFactories.ConfigurationPage;
import com.legion.pages.LocationSelectorPage;
import com.legion.pages.core.ConsoleLocationSelectorPage;
import com.legion.utils.SimpleUtils;
import cucumber.api.java.ro.Si;
import org.apache.commons.collections.ListUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.*;

import static com.legion.tests.TestBase.*;
import static com.legion.utils.MyThreadLocal.*;


public class OpsPortalConfigurationPage extends BasePage implements ConfigurationPage {

	public OpsPortalConfigurationPage() {
		PageFactory.initElements(getDriver(), this);
	}

	@FindBy(css=".console-navigation-item-label.Configuration")
	private WebElement configurationTab;

	@FindBy(css="div[ng-repeat=\"cat in module\"] h1.categoryName")
	private List<WebElement> categoryOfTemplateList;

	@FindBy(css="[class=\"tb-wrapper ng-scope\"] lg-dashboard-card h1")
	private List<WebElement> configurationCardsList;

	@FindBy(css = ".lg-dashboard-card")
	private List<WebElement> configurationCards;

	@FindBy(css="div.card-carousel-card-title")
	private List<WebElement> smartCardsList;

	@FindBy(css="span[class=\"lg-paged-search__showing top-right-action-button ng-scope\"] button")
	private WebElement newTemplateBTN;

	@FindBy(css="div.lg-tab-toolbar__search")
	private WebElement searchField;

	@FindBy(css="[class=\"lg-table lg-templates-table-improved ng-scope\"] .lg-templates-table-improved__grid-row.ng-scope")
	private List<WebElement> templatesList;

	@FindBy(css="[class=\"lg-table lg-templates-table-improved ng-scope\"] button span.ng-binding")
	private List<WebElement> templateNameList;
	@FindBy(css="lg-eg-status[type='Draft']")
	private List<WebElement> templateDraftStatusList;
	@FindBy(css="div.toggle i[class=\"fa fa-caret-right\"]")
	private WebElement templateToggleButton;

	@FindBy(css="lg-button[label=\"Edit\"]")
	private WebElement editButton;

	@FindBy(css="div[class=\"lg-modal\"]")
	private WebElement editTemplatePopupPage;

	@FindBy(css="lg-button[label=\"OK\"]")
	private WebElement okButton;

	@FindBy(css="lg-button[label=\"Cancel\"]")
	private WebElement cancelButton;

	@FindBy(css="a[ng-click=\"$ctrl.back()\"]")
	private WebElement backButton;

	@FindBy(css="div.lg-page-heading h1")
	private WebElement templateTitleOnDetailsPage;

	@FindBy(css="div.lg-tabs nav[class=\"lg-tabs__nav\"]")
	private WebElement templateDetailsAssociateTab;

	@FindBy(css="lg-tab[tab-title=\"Details\"]")
	private WebElement templateDetailsTab;

	@FindBy(css="lg-tab[tab-title=\"Association\"]")
	private WebElement templateAssociationTab;

	@FindBy(css="form[name=\"$ctrl.generalForm\"]")
	private WebElement templateDetailsPageForm;

	@FindBy(css="lg-button[label=\"Save as draft\"] i.fa.fa-sort-down")
	private WebElement dropdownArrowButton;

	@FindBy(css="i.fa.fa-sort-down")
	private WebElement dropdownArrowBTN;

	@FindBy(css="lg-button[label=\"Save as draft\"] h3[ng-click*=\"publishNow\"]")
	private WebElement publishNowButton;

	@FindBy(css="lg-button[label=\"Save as draft\"] button.pre-saveas")
	private WebElement saveAsDraftButton;

	@FindBy(css="lg-button[label=\"Save as draft\"] h3[ng-click*= publishLater]")
	private WebElement publishLaterButton;

	@FindBy(css="lg-button[label=\"Close\"]")
	private WebElement closeBTN;

	@FindBy(css="lg-template-rule-container img.settings-add-icon")
	private WebElement addIconOnRulesListPage;

	@FindBy(css="li[ng-click*=\"'Staffing\"]")
	private WebElement addStaffingRuleButton;

	@FindBy(css="li[ng-click*=\"AdvancedStaffing\"]")
	private WebElement addAdvancedStaffingRuleButton;

	@FindBy(css="sub-content-box[box-title=\"Days of Week\"]")
	private WebElement daysOfWeekSection;

	@FindBy(css="[title=\"Dynamic Employee Groups\"] div")
	private WebElement dynamicGroupSection;

	@FindBy(css="sub-content-box[box-title=\"Time of Day\"]")
	private WebElement timeOfDaySection;

	@FindBy(css="sub-content-box[box-title=\"Meal and Rest Breaks\"]")
	private WebElement mealAndRestBreaksSection;

	@FindBy(css="sub-content-box[box-title=\"Number of Shifts\"]")
	private WebElement numberOfShiftsSection;

	@FindBy(css="sub-content-box[box-title=\"Badges\"]")
	private WebElement badgesSection;

	@FindBy(css="lg-button[label=\"Save\"] button")
	private WebElement saveButton;



	public enum configurationLandingPageTemplateCards
	{
		OperatingHours("Operating Hours"),
		SchedulingPolicies("Scheduling Policies"),
		ScheduleCollaboration("Schedule Collaboration"),
		TimeAttendance("Time & Attendance"),
		Compliance("Compliance"),
		SchedulingRules("Scheduling Rules"),
		SchedulingPolicyGroups("Scheduling Policy Groups"),
		Communications("Communications"),
		MinorsRules("Minors Rules"),
		MealAndRest("Meal and Rest"),
		AdditionalPayRules("Additional Pay Rules");
		private final String value;

		configurationLandingPageTemplateCards(final String newValue) {
			value = newValue;
		}

		public String getValue() {
			return value;
		}
	}


	public enum DynamicEmployeeGroupLabels {

		MinorRule("Minor Rule"),
		DifferentialPay("Differential Pay");

		private final String value;

		DynamicEmployeeGroupLabels(final String newValue) {
			value = newValue;
		}

		public String getValue() {
			return value;
		}
	}

	public enum DynamicEmployeeGroupCriteria {

		WorkRole("Work Role"),
		JobTitle("Job Title"),
		Country("Country"),
		State("State"),
		City("City"),
		EmploymentType("Employment Type"),
		EmploymentStatus("Employment Status"),
		Exempt("Exempt"),
		Minor("Minor"),
		Badge("Badge"),
		Custom("Custom");

		private final String value;

		DynamicEmployeeGroupCriteria(final String newValue) {
			value = newValue;
		}

		public String getValue() {
			return value;
		}
	}

	public enum DynamicEmployeeGroupMinorCriteria {

		LessThan14("<14"),
		Equals14("14"),
		Equals15("15"),
		Equals16("16"),
		Equals17("17"),
		OlderOrEqualTo18(">=18");

		private final String value;

		DynamicEmployeeGroupMinorCriteria(final String newValue) {
			value = newValue;
		}

		public String getValue() {
			return value;
		}
	}
	@Override
	public void goToConfigurationPage() throws Exception {
		if (isElementEnabled(configurationTab,15)) {
			click(configurationTab);
			waitForSeconds(20);
			if(categoryOfTemplateList.size()!=0){
//				checkAllTemplateCards();
				SimpleUtils.pass("User can click configuration tab successfully");
				}else{
				SimpleUtils.fail("User can't click configuration tab",false);
			}
		}else
			SimpleUtils.fail("Configuration tab load failed",false);
	}

	@Override
	public void checkAllTemplateCards() throws Exception {
		List<String> opTemplateTypes = new ArrayList<String>(){{
			add("Operating Hours");
			add("Scheduling Policies");
			add("Schedule Collaboration");
			add("Time & Attendance");
			add("Compliance");
			add("Scheduling Rules");
		}};
		if(configurationCardsList.size() >0){
				for(String opTemplateType:opTemplateTypes){
					for(WebElement configurationCard:configurationCardsList) {
						String configurationCardName = configurationCard.getText().trim();
						if(opTemplateType.equals(configurationCardName)){
							SimpleUtils.pass(opTemplateType + " is shown.");
							break;
						}else {
							continue;
						}
				}
			}
		} else if(configurationCardsList.size()==10){
			for(WebElement configurationCard:configurationCardsList) {
				if(configurationCard.getText().equals(configurationLandingPageTemplateCards.OperatingHours.getValue())){
					SimpleUtils.pass(configurationLandingPageTemplateCards.OperatingHours.getValue() + " card is showing.");
					continue;
				}else if(configurationCard.getText().equals(configurationLandingPageTemplateCards.SchedulingPolicies.getValue())){
					SimpleUtils.pass(configurationLandingPageTemplateCards.SchedulingPolicies.getValue() + " card is showing.");
					continue;
				}else if(configurationCard.getText().equals(configurationLandingPageTemplateCards.ScheduleCollaboration.getValue())){
					SimpleUtils.pass(configurationLandingPageTemplateCards.ScheduleCollaboration.getValue() + " card is showing.");
					continue;
				}else if(configurationCard.getText().equals(configurationLandingPageTemplateCards.TimeAttendance.getValue())){
					SimpleUtils.pass(configurationLandingPageTemplateCards.TimeAttendance.getValue() + " card is showing.");
					continue;
				}else if(configurationCard.getText().equals(configurationLandingPageTemplateCards.Compliance.getValue())){
					SimpleUtils.pass(configurationLandingPageTemplateCards.Compliance.getValue() + " card is showing.");
					continue;
				}else if(configurationCard.getText().equals(configurationLandingPageTemplateCards.SchedulingRules.getValue())){
					SimpleUtils.pass(configurationLandingPageTemplateCards.SchedulingRules.getValue() + " card is showing.");
					continue;
				}else if(configurationCard.getText().equals(configurationLandingPageTemplateCards.Communications.getValue())){
					SimpleUtils.pass(configurationLandingPageTemplateCards.Communications.getValue() + " card is showing.");
					continue;
				}else if(configurationCard.getText().equals(configurationLandingPageTemplateCards.MinorsRules.getValue())){
					SimpleUtils.pass(configurationLandingPageTemplateCards.MinorsRules.getValue() + " card is showing.");
					continue;
				}else if(configurationCard.getText().equals(configurationLandingPageTemplateCards.MealAndRest.getValue())){
					SimpleUtils.pass(configurationLandingPageTemplateCards.MealAndRest.getValue() + " card is showing.");
					continue;
				}else if(configurationCard.getText().equals(configurationLandingPageTemplateCards.AdditionalPayRules.getValue())){
					SimpleUtils.pass(configurationLandingPageTemplateCards.AdditionalPayRules.getValue() + " card is showing.");
					continue;
				}else{
					SimpleUtils.fail("Configuration template cards are loaded incorrect",false);
				}
			}
		} else if(configurationCardsList.size()==11){
			for(WebElement configurationCard:configurationCardsList) {
				if(configurationCard.getText().equals(configurationLandingPageTemplateCards.OperatingHours.getValue())){
					SimpleUtils.pass(configurationLandingPageTemplateCards.OperatingHours.getValue() + " card is showing.");
					continue;
				}else if(configurationCard.getText().equals(configurationLandingPageTemplateCards.SchedulingPolicies.getValue())){
					SimpleUtils.pass(configurationLandingPageTemplateCards.SchedulingPolicies.getValue() + " card is showing.");
					continue;
				}else if(configurationCard.getText().equals(configurationLandingPageTemplateCards.ScheduleCollaboration.getValue())){
					SimpleUtils.pass(configurationLandingPageTemplateCards.ScheduleCollaboration.getValue() + " card is showing.");
					continue;
				}else if(configurationCard.getText().equals(configurationLandingPageTemplateCards.TimeAttendance.getValue())){
					SimpleUtils.pass(configurationLandingPageTemplateCards.TimeAttendance.getValue() + " card is showing.");
					continue;
				}else if(configurationCard.getText().equals(configurationLandingPageTemplateCards.Compliance.getValue())){
					SimpleUtils.pass(configurationLandingPageTemplateCards.Compliance.getValue() + " card is showing.");
					continue;
				}else if(configurationCard.getText().equals(configurationLandingPageTemplateCards.SchedulingRules.getValue())){
					SimpleUtils.pass(configurationLandingPageTemplateCards.SchedulingRules.getValue() + " card is showing.");
					continue;
				}else if(configurationCard.getText().equals(configurationLandingPageTemplateCards.Communications.getValue())){
					SimpleUtils.pass(configurationLandingPageTemplateCards.Communications.getValue() + " card is showing.");
					continue;
				}else if(configurationCard.getText().equals(configurationLandingPageTemplateCards.SchedulingPolicyGroups.getValue())){
					SimpleUtils.pass(configurationLandingPageTemplateCards.SchedulingPolicyGroups.getValue() + " card is showing.");
					continue;
				}else if(configurationCard.getText().equals(configurationLandingPageTemplateCards.MinorsRules.getValue())){
					SimpleUtils.pass(configurationLandingPageTemplateCards.MinorsRules.getValue() + " card is showing.");
					continue;
				}else if(configurationCard.getText().equals(configurationLandingPageTemplateCards.MealAndRest.getValue())){
					SimpleUtils.pass(configurationLandingPageTemplateCards.MealAndRest.getValue() + " card is showing.");
					continue;
				}else if(configurationCard.getText().equals(configurationLandingPageTemplateCards.AdditionalPayRules.getValue())){
					SimpleUtils.pass(configurationLandingPageTemplateCards.AdditionalPayRules.getValue() + " card is showing.");
					continue;
				}else{
					SimpleUtils.fail("Configuration template cards are loaded incorrect",false);
				}
			}
		}
		else{
			SimpleUtils.fail("Configuration landing page was loading failed",false);
		}
	}

	@Override
	public boolean isTemplateListPageShow() throws Exception {
		boolean flag = false;
			if(templatesList.size()!=0 && isElementEnabled(newTemplateBTN, 5) && isElementEnabled(searchField, 5)){
				SimpleUtils.pass("Template landing page shows well");
				flag = true;
			}else{
				SimpleUtils.fail("Template landing page was NOT loading well",false);
				flag = false;
			}
			return flag;
	}

// open the first one template on template list page
	@Override
	public void clickOnTemplateName(String templateType) throws Exception {
		if(isTemplateListPageShow()){
			String classValue = templatesList.get(0).getAttribute("class");
			if(classValue!=null && classValue.contains("hasChildren")){
				clickTheElement(templateToggleButton);
				waitForSeconds(3);
				clickTheElement(getDriver().findElement(By.cssSelector("[ng-repeat=\"child in item.childTemplate\"] button")));
				waitForSeconds(20);
				if(isElementEnabled(templateTitleOnDetailsPage)&&isElementEnabled(closeBTN)&&isElementEnabled(templateDetailsAssociateTab)
				&&isElementEnabled(templateDetailsPageForm)){
					SimpleUtils.pass("User can open one " + templateType + " template succseefully");
				}else{
					SimpleUtils.fail("User open one " + templateType + " template failed",false);
				}
			}else{
				clickTheElement(templatesList.get(0).findElement(By.cssSelector("button")));
				waitForSeconds(20);
				if(isElementEnabled(templateTitleOnDetailsPage)&&isElementEnabled(closeBTN)&&isElementEnabled(templateDetailsAssociateTab)
						&&isElementEnabled(templateDetailsPageForm)){
					SimpleUtils.pass("User can open one " + templateType + " template succseefully");
				}else{
					SimpleUtils.fail("User open one " + templateType + " template failed",false);
				}
			}
		}else{
			SimpleUtils.pass("There is No " + templateType + "template now");
		}
	}

	@Override
	public void clickOnConfigurationCrad(String templateType) throws Exception {
		if(templateType!=null){
			waitForSeconds(10);
			if(configurationCardsList.size()!=0) {
				for (WebElement configurationCard : configurationCardsList) {
					if(configurationCard.getText().contains(templateType)){
						clickTheElement(configurationCard);
						waitForSeconds(10);
						SimpleUtils.pass("User can click " + templateType + " configuration card successfully!");
						break;
					}
				}
			}else{
				SimpleUtils.fail("configuration landing page was loaded failed",false);
			}
		}else{
			SimpleUtils.fail("Please specify which type of template would you open?",false);
		}
	}

	@Override
	public void goToTemplateDetailsPage(String templateType) throws Exception {
		waitForSeconds(5);
		clickOnConfigurationCrad(templateType);
		clickOnTemplateName(templateType);
	}

	@FindBy(css="input[placeholder=\"You can search by template name, status and creator.\"]")
	private WebElement searchTemplateInputBox;
    @Override
	public boolean searchTemplate(String templateName) throws Exception{
		boolean exsiting=false;
		if(isElementEnabled(searchTemplateInputBox,5)){
			clickTheElement(searchTemplateInputBox);
			searchTemplateInputBox.clear();
			searchTemplateInputBox.sendKeys(templateName);
			searchTemplateInputBox.sendKeys(Keys.ENTER);
			waitForSeconds(2);
			if(templateNameList.size()>0)
				exsiting=true;

		}
		return exsiting;
	}

	//open the specify template to edit or view details
	@Override
	public void clickOnSpecifyTemplateName(String templateName,String editOrViewMode) throws Exception {
		waitForSeconds(5);
		if(isTemplateListPageShow()){
			searchTemplate(templateName);
			for(int i=0;i<templateNameList.size();i++){
				if(templateNameList.get(i).getText()!=null && templateNameList.get(i).getText().trim().equals(templateName)){
					String classValue = templatesList.get(i).getAttribute("class");
					if(classValue!=null && classValue.contains("hasChildren")){
						clickTheElement(templatesList.get(i).findElement(By.className("toggle")));
						waitForSeconds(3);
						if(editOrViewMode!=null && editOrViewMode.toLowerCase().contains("edit")){
							clickTheElement(getDriver().findElement(By.cssSelector("[ng-repeat=\"child in item.childTemplate\"] button")));
						}else{
							clickTheElement(templatesList.get(i).findElement(By.cssSelector("button")));
						}
						waitForSeconds(15);
						if(isElementEnabled(templateTitleOnDetailsPage)&&isElementEnabled(closeBTN)&&isElementEnabled(templateDetailsAssociateTab)
								&&isElementEnabled(templateDetailsPageForm)){
							SimpleUtils.pass("User can open " + templateName + " template succseefully");
						}else{
							SimpleUtils.fail("User open " + templateName + " template failed",false);
						}
					}else{
						clickTheElement(templatesList.get(i).findElement(By.cssSelector("button")));
						waitForSeconds(15);
						if(isElementEnabled(templateTitleOnDetailsPage)&&isElementEnabled(closeBTN)&&isElementEnabled(templateDetailsAssociateTab)
								&&isElementEnabled(templateDetailsPageForm)){
							SimpleUtils.pass("User can open " + templateName + " template succseefully");
						}else{
							SimpleUtils.fail("User open " + templateName + " template failed",false);
						}
					}
					break;
				}
//				else if(i==templateNameList.size()-1){
//					SimpleUtils.fail("Can't find the specify template",false);
//				}
			}
		}else{
			SimpleUtils.fail("There is No template now",false);
		}
	}

	@Override
	public void clickOnEditButtonOnTemplateDetailsPage() throws Exception {
		if(isElementEnabled(editButton, 10)){
			clickTheElement(editButton);
			waitForSeconds(3);
			if(isElementEnabled(editTemplatePopupPage)){
				SimpleUtils.pass("Click edit button successfully!");
				clickTheElement(okButton);
				if(isElementEnabled(dropdownArrowButton)){
					SimpleUtils.pass("Template is in edit mode now");
				}else{
					SimpleUtils.fail("Template is not in edit mode now",false);
				}
			}else{
				SimpleUtils.fail("Click edit button successfully!",false);
			}
		}else{
			SimpleUtils.fail("Template details page is loaded failed",false);
		}
	}

//	@FindBy(css="table[ng-if*=\"template.workRoles\"] tbody")
//	private List<WebElement> workRoleList;

	@FindBy(css="table[ng-if*=\"$ctrl.sortedRows.length\"] tbody")
	private List<WebElement> workRoleList;

	@Override
	public void selectWorkRoleToEdit(String workRole) throws Exception {
		if(workRoleList.size()!=0){
			for(WebElement workRoleItem:workRoleList){
				String workRoleName = workRoleItem.findElement(By.cssSelector("td.ng-binding")).getText().trim();
				//get first char of the work role name
				char fir = workRole.charAt(0);
				String newWorkRole = String.valueOf(fir).toUpperCase() + " " + workRole;
				if(workRoleName.equals(newWorkRole)){
					WebElement staffingRulesAddButton = workRoleItem.findElement(By.cssSelector("lg-button"));
					clickTheElement(staffingRulesAddButton);
					waitForSeconds(5);
					if(isElementEnabled(addIconOnRulesListPage)){
						SimpleUtils.pass("Successful to select " + workRole + " to edit");
					}
					else{
						SimpleUtils.fail("Failed to select " + workRole + " to edit",false);
					}
					break;
				}
			}
		}else{
			SimpleUtils.fail("There is no work role for enterprise now",false);
		}
	}

	public String getCountOfStaffingRules(String workRole) {
		String count = null;
		if (workRoleList.size() != 0) {
			for (WebElement workRoleItem : workRoleList) {
				String workRoleName = workRoleItem.findElement(By.cssSelector("td.ng-binding")).getText().trim();
				//get first char of the work role name
				char fir = workRole.charAt(0);
				String newWorkRole = String.valueOf(fir).toUpperCase() + " " + workRole;
				if (workRoleName.equals(newWorkRole)) {
					String firstLetter = workRoleItem.findElement(By.cssSelector("lg-button span.ng-binding")).getText().trim().split(" ")[0];
					if(firstLetter.equals("+")){
						count = "0";
						SimpleUtils.pass("There is no staffing rules for this work role");
					}else  {
						count = firstLetter;
						SimpleUtils.pass(workRole + " have " + count + " staffing rules now!");
					}
					break;
				}
			}
		} else {
			SimpleUtils.fail("There is no work role for enterprise now", false);
		}
		return count;
	}


	@Override
	public void checkTheEntryOfAddAdvancedStaffingRule() throws Exception {
		waitForSeconds(5);
		if(isElementEnabled(addIconOnRulesListPage)){
			clickTheElement(addIconOnRulesListPage);
			if(isElementEnabled(addAdvancedStaffingRuleButton)){
				SimpleUtils.pass("Advance staffing rules tab is show");
				clickTheElement(addAdvancedStaffingRuleButton);
				if(isElementEnabled(dynamicGroupSection)){
					SimpleUtils.pass("Advance staffing rules tab is clickable");
				}
				else{
					SimpleUtils.fail("Advance staffing rules tab is NOT clickable",false);
				}
			}else {
				SimpleUtils.pass("Advance staffing rules tab is NOT show");
			}
		}else{
			SimpleUtils.fail("Work role's staffing rules list page was loaded failed",false);
		}
	}

	@Override
	public void verifyAdvancedStaffingRulePageShowWell() throws Exception {
		if(isElementEnabled(dynamicGroupSection) && isElementEnabled(daysOfWeekSection) && isElementEnabled(timeOfDaySection)
				&& isElementEnabled(mealAndRestBreaksSection) && isElementEnabled(numberOfShiftsSection)
				&& isElementEnabled(badgesSection) && isElementEnabled(cancelButton)){
			SimpleUtils.pass("New advanced staffing rule page shows well");
		}else{
			SimpleUtils.fail("New advanced staffing rule page doesn't show well",false);
		}
	}

	@FindBy(css="sub-content-box[box-title=\"Days of Week\"] input")
	private List<WebElement> daysOfWeekCheckBoxList;

	@FindBy(css="sub-content-box[box-title=\"Days of Week\"] label.input-label")
	private List<WebElement> daysOfWeekLabelList;

	@Override
	public boolean isDaysOfWeekFormulaCheckBoxChecked(){
		boolean flag = false;
		if(daysOfWeekCheckBoxList.size()!=0){
			String classValueOfCheckBox = daysOfWeekCheckBoxList.get(0).getAttribute("class");
			if(classValueOfCheckBox.contains("ng-not-empty")){
				SimpleUtils.pass("The formula check box of days of week is checked already!");
				flag = true;
			}else {
				SimpleUtils.pass("The formula check box of days of week is NOT checked yet!");
				flag = false;
			}
		}
		return flag;
	}

	@Override
	public void verifyCheckBoxOfDaysOfWeekSection() throws Exception {
		if(isDaysOfWeekFormulaCheckBoxChecked()){
			if(daysOfWeekCheckBoxList.size()==1){
				SimpleUtils.pass("The select day sub-section is disappeared after check the formula checkbox");
			}else{
				SimpleUtils.fail("The select day sub-section is NOT disappeared after check the formula checkbox",true);
			}
		}else {
			//verify check box for each day section
			for(int i = 1; i < daysOfWeekCheckBoxList.size(); i++){
				if(i<daysOfWeekCheckBoxList.size()-1){
					clickTheElement(daysOfWeekCheckBoxList.get(i));
				}
				if(daysOfWeekCheckBoxList.get(i).getAttribute("class").contains("ng-not-empty")){
					SimpleUtils.pass(daysOfWeekLabelList.get(i).getText().trim() + " has been selected successfully!");
				}else{
					SimpleUtils.fail(daysOfWeekLabelList.get(i).getText().trim() + " has NOT been selected successfully!",false);
				}
			}
			//verify check box for Custom formula
			clickTheElement(daysOfWeekCheckBoxList.get(0));
			if(daysOfWeekCheckBoxList.get(0).getAttribute("class").contains("ng-not-empty") && daysOfWeekCheckBoxList.size() ==1){
				SimpleUtils.pass(daysOfWeekLabelList.get(0).getText().trim() + " has been selected successfully!");
			}else{
				SimpleUtils.fail(daysOfWeekLabelList.get(0).getText().trim() + " has NOT been selected successfully!",false);
			}
		}
	}

	@FindBy(css="sub-content-box[box-title=\"Days of Week\"] textarea")
	private WebElement formulaTextAreaOfDaysOfWeekSection;

	@Override
	public void inputFormulaInForDaysOfWeekSection(String formula) throws Exception{
		if(isDaysOfWeekFormulaCheckBoxChecked()){
			clickTheElement(formulaTextAreaOfDaysOfWeekSection);
			formulaTextAreaOfDaysOfWeekSection.sendKeys(formula);
		}else{
			clickTheElement(daysOfWeekCheckBoxList.get(0));
			clickTheElement(formulaTextAreaOfDaysOfWeekSection);
			formulaTextAreaOfDaysOfWeekSection.sendKeys(formula);
		}

		String formulaValue = getDriver().findElement(By.cssSelector("sub-content-box[box-title=\"Days of Week\"] input-field[type=\"textarea\"] ng-form div")).getAttribute("innerText").trim();
		if(formulaValue.equals(formula)){
			SimpleUtils.pass("User can input formula for days of week successfully!");
		}else{
			SimpleUtils.fail("User can NOT input formula for days of week successfully!",false);
		}

	}

	@FindBy(css="sub-content-box[box-title=\"Days of Week\"] div.day-selector input-field")
	private List<WebElement> daysOfWeekList;

	@Override
	public void selectDaysForDaysOfWeekSection(List<String> days) throws Exception{
		if(daysOfWeekCheckBoxList.size()>1){
			for(String day:days){
				for(WebElement daysOfWeek:daysOfWeekList){
					 String dayname = daysOfWeek.findElement(By.cssSelector("label.input-label")).getText().trim();
					 WebElement checkBoxOfDay = daysOfWeek.findElement(By.cssSelector("input"));
					if(day.equals(dayname)){
						clickTheElement(checkBoxOfDay);
						if(checkBoxOfDay.getAttribute("class").contains("ng-not-empty")){
							SimpleUtils.pass(dayname + " has been selected successfully!");
						}else {
							SimpleUtils.fail("User failed to select " + dayname,false);
						}
						break;
					}else {
						continue;
					}
				}
			}
		}else{
			SimpleUtils.fail("The formula check box of days of week have been checked on.",false);
		}
	}

 //below are advanced staffing rule element
	@FindBy(css="div[class=\"mt-20\"] input-field[value*=\"timeEventOffsetMinutes\"] input")
	private WebElement shiftStartOffsetMinutes;
	@FindBy(css="div[class=\"mt-20\"] input-field[value*=\"timeEventOffsetMinutes\"] div")
	private WebElement shiftStartOffsetMinutesValue;
	@FindBy(css="div[class=\"mt-20\"] input-field[options*=\"timeUnitOptions\"] select")
	private WebElement shiftStartTimeUnit;
	@FindBy(css="div[class=\"mt-20\"] input-field[options*=\"eventPointOptions\"] select")
	private WebElement shiftStartEventPoint;
	@FindBy(css="div[class=\"mt-20\"] input-field[options*=\"eventPointOptions\"] select option")
	private List<WebElement> shiftStartEventPointList;
	@FindBy(css="div[class=\"mt-20\"] input-field[options*=\"timeEventOptions\"] select")
	private WebElement shiftStartTimeEvent;
	@FindBy(css="div[class=\"mt-20\"] input-field[options*=\"timeEventOptions\"] select option")
	private List<WebElement> shiftStartTimeEventList;


	@Override
	public void inputOffsetTimeForShiftStart(String startOffsetTime,String startEventPoint) throws Exception{
		if(isElementEnabled(shiftStartOffsetMinutes)){
			clickTheElement(shiftStartOffsetMinutes);
			shiftStartOffsetMinutes.clear();
			shiftStartOffsetMinutes.sendKeys(startOffsetTime);
		}else {
			selectByVisibleText(shiftStartEventPoint,startEventPoint);
			waitForSeconds(3);
			clickTheElement(shiftStartOffsetMinutes);
			shiftStartOffsetMinutes.clear();
			shiftStartOffsetMinutes.sendKeys(startOffsetTime);
		}
		String offsetTimeValue = shiftStartOffsetMinutesValue.getAttribute("innerText").trim();
		if(offsetTimeValue.equals(startOffsetTime)){
			SimpleUtils.pass("User can set shift Start Offset Minutes as " + startOffsetTime + " successfully!");
		}else {
			SimpleUtils.fail("User can't input value for shift Start Offset Minutes field",true);
		}
	}

	@FindBy(css="div[class=\"mt-20\"] input-field[options*=\"timeUnitOptions\"] select option")
	private List<WebElement> shiftStartTimeUnitList;
	List<String> unitList = new ArrayList<String>(){{
		add("minutes");
		add("am");
		add("pm");
	}};

 //verify list of start shift time unit
	@Override
	public void validateShiftStartTimeUnitList() throws Exception{
		if(isElementEnabled(shiftStartTimeUnit)){
			List<String> startTimeUnitList = new ArrayList<String>();
			clickTheElement(shiftStartTimeUnit);
			if(shiftStartTimeUnitList.size()!=0){
				for(WebElement shiftStartTimeUnit:shiftStartTimeUnitList){
					if(shiftStartTimeUnit!=null) {
						String startTimeUnit = shiftStartTimeUnit.getText().trim();
						SimpleUtils.report("shift start time unit list: " + startTimeUnit);
						startTimeUnitList.add(startTimeUnit);
					}
				}
				if(ListUtils.isEqualList(unitList,startTimeUnitList)){
					SimpleUtils.pass("The list of start time unit is correct");
				}else {
					SimpleUtils.fail("The list of start time unit is NOT correct",true);
				}
			}
		}else {
			SimpleUtils.fail("Shift start time unit isn't shown",false);
		}
	}

	@FindBy(css="div[class=\"mt-20\"] input-field[options*=\"timeUnitOptions\"] div.input-faked")
	private WebElement startTimeUnitValue;

	@Override
	public void selectShiftStartTimeUnit(String startTimeUnit) throws Exception{
		if(isElementEnabled(shiftStartTimeUnit)){
			selectByVisibleText(shiftStartTimeUnit,startTimeUnit);
			waitForSeconds(2);
			String startTimeUnitVal = startTimeUnitValue.getAttribute("innerText").trim();
			if(startTimeUnitVal.equals(startTimeUnit)){
				SimpleUtils.pass("User successfully select start event: " + startTimeUnitVal);
			}else {
				SimpleUtils.fail("User failed to select start event: " + startTimeUnitVal,false);
			}
		}
	}

//below are operating hour element
	@FindBy(css="div.dayparts span.add-circle")
	private WebElement addDayPartsBTNInOH;
	@FindBy(css="modal[modal-title=\"Manage Dayparts\"] div[class*=\"lg-modal__title-icon\"]")
	private WebElement manageDaypartsPageTitle;
	@FindBy(css="div.lg-paged-search div.lg-paged-search__pagination select")
	private WebElement dayPartsPagination;
	@FindBy(css="div.lg-paged-search div.lg-paged-search__pagination select option")
	private List<WebElement> dayPartsPaginationList;
	@FindBy(css=".dayparts table tbody tr")
	private List<WebElement> dayPartsList;

	//verify list of Shift Start Time Event
	@Override
	public List<String> getShiftStartTimeEventList() throws Exception{
		List<String> startTimeEventList = new ArrayList<String>();
		if(isElementEnabled(shiftStartTimeEvent)){
			clickTheElement(shiftStartTimeEvent);
			if(shiftStartTimeEventList.size()!=0){
				for(WebElement shiftStartTimeEvent:shiftStartTimeEventList){
					String startTimeEvent = shiftStartTimeEvent.getText().trim();
					if(startTimeEvent!=null){
						SimpleUtils.report("shift start time event list: " + startTimeEvent);
						startTimeEventList.add(startTimeEvent);
					}
				}
			}
		}else {
			SimpleUtils.fail("Shift start time event isn't shown",false);
		}
		return startTimeEventList;
	}

	@FindBy(css="div[class=\"mt-20\"] input-field[options*=\"timeEventOptions\"] div.input-faked")
	private WebElement shiftStartTimeEventValue;

	@Override
	public void selectShiftStartTimeEvent(String startEvent) throws Exception{
		if(isElementEnabled(shiftStartTimeEvent)){
			selectByVisibleText(shiftStartTimeEvent,startEvent);
			waitForSeconds(2);
			String shiftStartTimeEventVal = shiftStartTimeEventValue.getAttribute("innerText").trim();
			for(WebElement timeEvent:shiftStartTimeEventList){
				String eventName = timeEvent.getText().trim();
				String eventValue = timeEvent.getAttribute("value").trim();
				if(startEvent.equals(eventName) && eventValue.contains(shiftStartTimeEventVal)){
					SimpleUtils.pass("User can select start time event successfully.");
					break;
				}
			}
		}
	}

    @FindBy(css="div.dif.duartion input-field[type=\"radio\"] ng-form")
	private WebElement shiftDuartionRadioButton;
	@FindBy(css="div.dif.duartion input-field[type=\"number\"] input")
	private WebElement shiftDuartionMinutes;
	@FindBy(css="div.dif.duartion input-field[type=\"number\"] div")
	private WebElement shiftDuartionMinutesValue;
	@FindBy(css="div.dif.end-shift input-field[type=\"radio\"] ng-form")
	private WebElement shiftEndRadioButton;
	@FindBy(css="div.dif.end-shift input-field[value*=\"timeEventOffsetMinutes\"] input")
	private WebElement shiftEndOffsetMinutes;
	@FindBy(css="div.dif.end-shift input-field[value*=\"timeEventOffsetMinutes\"] div")
	private WebElement shiftEndOffsetMinutesValue;
	@FindBy(css="div.dif.end-shift input-field[options=\"$ctrl.timeUnitOptions\"] select")
	private WebElement shiftEndTimeUnit;
	@FindBy(css="div.dif.end-shift input-field[options=\"$ctrl.timeUnitOptions\"] select option")
	private List<WebElement> shiftEndTimeUnitList;
	@FindBy(css="div.dif.end-shift input-field[options=\"$ctrl.eventPointOptions\"] select")
	private WebElement shiftEndEventPoint;
	@FindBy(css="div.dif.end-shift input-field[options=\"$ctrl.eventPointOptions\"] select option")
	private List<WebElement> shiftEndEventPointList;
	@FindBy(css="div.dif.end-shift input-field[options=\"$ctrl.timeEventOptions\"] select")
	private WebElement shiftEndTimeEvent;
	@FindBy(css="div.dif.end-shift input-field[options=\"$ctrl.timeEventOptions\"] select option")
	private List<WebElement> shiftEndTimeEventList;
	@FindBy(css="div.dif.duartion input-field[type=\"number\"]+span")
	private WebElement shiftDuartionMinutesUnit;
	@FindBy(css="div.dif.end-shift input-field[options=\"$ctrl.timeEventOptions\"] div.input-faked")
	private WebElement shiftEndTimeEventValue;
	@FindBy(css="button.saveas-drop")
	private WebElement templateSaveDrop;
	@FindBy(css="div.saveas-list h3")
	private List<WebElement> templateSaveOptions;

	@Override
	public void selectShiftEndTimeEvent(String endEvent) throws Exception{
		if(isElementEnabled(shiftEndTimeEvent)){
			selectByVisibleText(shiftEndTimeEvent,endEvent);
			waitForSeconds(2);
			String shiftEndTimeEventVal = shiftEndTimeEventValue.getAttribute("innerText").trim();
            for(WebElement timeEvent:shiftEndTimeEventList){
            	String eventName = timeEvent.getText().trim();
            	String eventValue = timeEvent.getAttribute("value").trim();
            	if(endEvent.equals(eventName) && eventValue.contains(shiftEndTimeEventVal)){
            		SimpleUtils.pass("User can select end time event successfully.");
            		break;
				}
			}
		}
	}

	@FindBy(css="div[class=\"mt-20 dif\"] input-field[options*=\"timeUnitOptions\"] div.input-faked")
	private WebElement endTimeUnitValue;
	@Override
	public void selectShiftEndTimeUnit(String endTimeUnit) throws Exception{
		if(isElementEnabled(shiftEndTimeUnit)){
			selectByVisibleText(shiftEndTimeUnit,endTimeUnit);
			waitForSeconds(2);
			String endTimeUnitVal = endTimeUnitValue.getAttribute("innerText").trim();
			if(endTimeUnitVal.equals(endTimeUnit)){
				SimpleUtils.pass("User successfully select start event: " + endTimeUnitVal);
			}else {
				SimpleUtils.fail("User failed to select start event: " + endTimeUnitVal,false);
			}
		}
	}

	@Override
	public void verifyRadioButtonInTimeOfDayIsSingletonSelect() throws Exception{
		if(isElementEnabled(shiftDuartionRadioButton)&&isElementEnabled(shiftEndRadioButton)){
			if(shiftDuartionRadioButton.getAttribute("class").trim().contains("ng-valid-parse")){
				SimpleUtils.pass("shift Duartion Radio Button is selected now");
				shiftEndRadioButton.click();
				waitForSeconds(3);
				if(shiftEndRadioButton.getAttribute("class").trim().contains("ng-valid-parse")){
					if(shiftDuartionRadioButton.getAttribute("class").trim().contains("ng-valid-parse")){
						SimpleUtils.pass("Both Duartion Radio Button and End Radio Button are selected");
					}else{
						SimpleUtils.pass("End Radio Button is selected, Duartion Radio Button is dis-selected automatically");
					}
				}else{
					SimpleUtils.fail("User click shift End Radio Button failed",false);
				}
			}else{
				SimpleUtils.pass("shift Duartion Radio Button is NOT selected now");
				shiftDuartionRadioButton.click();
				waitForSeconds(3);
				if(shiftDuartionRadioButton.getAttribute("class").trim().contains("ng-valid-parse")){
					if(shiftEndRadioButton.getAttribute("class").trim().contains("ng-valid-parse")){
						SimpleUtils.pass("Both Duartion Radio Button and End Radio Button are selected");
					}else{
						SimpleUtils.pass("Duartion Radio Button is selected, End Radio Button is dis-selected automatically");
					}
				}else{
					SimpleUtils.fail("User click shift Duartion Radio Button failed",false);
				}
			}
		}
	}

	@Override
	public void inputShiftDurationMinutes(String duringTime) throws Exception{
		waitForSeconds(5);
		clickTheElement(shiftDuartionMinutes);
		shiftDuartionMinutes.clear();
		shiftDuartionMinutes.sendKeys(duringTime);
		String duringTimeValue = shiftDuartionMinutesValue.getAttribute("innerText").trim();
		if(duringTimeValue.equals(duringTime)){
			SimpleUtils.pass("User can set shift during Minutes as " + duringTimeValue + " successfully!");
		}else {
			SimpleUtils.fail("User can't input value for shift during Minutes field",true);
		}
	}

	@Override
	public void validateShiftDurationTimeUnit() throws Exception{
		String unit = "minutes";
		if(shiftDuartionMinutesUnit.getText().trim()!=null && shiftDuartionMinutesUnit.getText().equals(unit)){
			SimpleUtils.pass("The shift Duration Minutes Unit is: " + shiftDuartionMinutesUnit.getText().trim());
		}else{
			SimpleUtils.fail("The The shift Duration Minutes Unit is not correct.",false);
		}

	}

	@Override
	public void inputOffsetTimeForShiftEnd(String endOffsetTime,String endEventPoint) throws Exception{
		if(isElementEnabled(shiftEndOffsetMinutes)){
			clickTheElement(shiftEndOffsetMinutes);
			shiftEndOffsetMinutes.clear();
			shiftEndOffsetMinutes.sendKeys(endOffsetTime);
		}else {
			selectByVisibleText(shiftEndEventPoint,endEventPoint);
			waitForSeconds(3);
			clickTheElement(shiftEndOffsetMinutes);
			shiftEndOffsetMinutes.clear();
			shiftEndOffsetMinutes.sendKeys(endOffsetTime);
		}
		String offsetTimeValue = shiftEndOffsetMinutesValue.getAttribute("innerText").trim();
		if(offsetTimeValue.equals(endOffsetTime)){
			SimpleUtils.pass("User can set shift End Offset Minutes as " + offsetTimeValue + " successfully!");
		}else {
			SimpleUtils.fail("User can't input value for shift End Offset Minutes field",true);
		}
	}

	//verify list of end shift time unit
	@Override
	public void validateShiftEndTimeUnitList() throws Exception{
		if(isElementEnabled(shiftEndTimeUnit)){
			List<String> endTimeUnitList = new ArrayList<String>();
			clickTheElement(shiftEndTimeUnit);
			if(shiftEndTimeUnitList.size()!=0){
				for(WebElement shiftEndTimeUnit:shiftEndTimeUnitList){
					if(shiftEndTimeUnit!=null) {
						String endTimeUnit = shiftEndTimeUnit.getText().trim();
						SimpleUtils.report("shift end time unit list: " + endTimeUnit);
						endTimeUnitList.add(endTimeUnit);
					}
				}
				if(ListUtils.isEqualList(unitList,endTimeUnitList)){
					SimpleUtils.pass("The list of end time unit is correct");
				}else {
					SimpleUtils.fail("The list of end time unit is NOT correct",true);
				}
			}
		}else {
			SimpleUtils.fail("Shift end time unit isn't shown",false);
		}
	}

	@FindBy(css="sub-content-box[box-title=\"Time of Day\"] input-field[label=\"Custom Formula?\"] input")
	private WebElement checkBoxOfTimeOfDay;
	@FindBy(css="div[ng-if=\"$ctrl.isTimeOfTheDayFormula\"] textarea")
	private WebElement formulaTextAreaOfTimeOfDay;

	public boolean isTimeOfDayFormulaCheckBoxChecked(){
		boolean flag = false;
		if(isElementEnabled(checkBoxOfTimeOfDay)){
			String classValueOfCheckBox = checkBoxOfTimeOfDay.getAttribute("class");
			if(classValueOfCheckBox.contains("ng-not-empty")){
				flag = true;
			}else {
				flag = false;
			}
		}
		return flag;
	}

	@Override
	public void tickOnCheckBoxOfTimeOfDay() throws Exception{
			if(!isTimeOfDayFormulaCheckBoxChecked()){
				clickTheElement(checkBoxOfTimeOfDay);
				waitForSeconds(3);
			}
			if(isElementEnabled(formulaTextAreaOfTimeOfDay)){
				SimpleUtils.pass("User checked on check box of time of day successfully!");
			}else{
				SimpleUtils.fail("User checked on check box of time of day successfully!",false);
			}
	}

	@Override
	public void inputFormulaInTextAreaOfTimeOfDay(String formulaOfTimeOfDay){
		String placeHolder = "Enter your custom formula here for the time of the day. The formula must evaluate to be an integer minutes.";
		//verify formula text area show well or not?
		if(formulaTextAreaOfTimeOfDay.getAttribute("placeholder").trim().equals(placeHolder)){
			SimpleUtils.pass("formula Text Area Of Time Of Day shows well.");
		}else {
			SimpleUtils.fail("formula Text Area Of Time Of Day shows well.",false);
		}
		//input formula in text area
		formulaTextAreaOfTimeOfDay.sendKeys(formulaOfTimeOfDay);
		String formulaValue = getDriver().findElement(By.cssSelector("sub-content-box[box-title=\"Time of Day\"] input-field[type=\"textarea\"] ng-form div")).getAttribute("innerText").trim();
		if(formulaValue.equals(formulaOfTimeOfDay)){
			SimpleUtils.pass("User can input formula for time of day successfully!");
		}else{
			SimpleUtils.fail("User can NOT input formula for time of day!",false);
		}
	}

	@FindBy(css="sub-content-box.breaks")
	private WebElement mealAndRestBreakSection;
	@FindBy(css="span[ng-click=\"$ctrl.addBreak('M')\"] span.ml-5.ng-binding")
	private WebElement addMealBreakButton;
	@FindBy(css="sub-content-box.breaks div.col-sm-5:nth-child(1) table tr[ng-repeat]")
	private List<WebElement> mealBreakList;
	@FindBy(css="sub-content-box.breaks div.col-sm-1.plr-0-0+div table tr[ng-repeat]")
	private List<WebElement> restBreakList;
	@FindBy(css="span[ng-click=\"$ctrl.addBreak('R')\"] span.ml-5.ng-binding")
	private WebElement addRestBreakButton;

	@Override
	public void addNewMealBreak(List<String> mealBreakValue) throws Exception {
		if(isElementEnabled(mealAndRestBreaksSection)){
			clickTheElement(addMealBreakButton);
			waitForSeconds(2);
			if(mealBreakList.size()!=0){
				SimpleUtils.pass("User click add button of Meal Break successfully");
			}else{
				SimpleUtils.fail("User click add button of Meal Break failed",false);
			}
			int index = mealBreakList.size() - 1;
			List<WebElement> startOffsetAndBreakDuration = mealBreakList.get(index).findElements(By.cssSelector("input-field"));
			for(int i = 0; i <=1; i++){
				WebElement startOffsetAndBreakDurationInput = startOffsetAndBreakDuration.get(i).findElement(By.cssSelector("input"));
				startOffsetAndBreakDurationInput.click();
				startOffsetAndBreakDurationInput.clear();
				startOffsetAndBreakDurationInput.sendKeys(mealBreakValue.get(i));
				String startOffsetValue = startOffsetAndBreakDuration.get(i).findElement(By.cssSelector("div")).getAttribute("innerText").trim();
				if(startOffsetValue.equals(mealBreakValue.get(i))){
					SimpleUtils.pass("User can add Meal Break successfully!");
				}else {
					SimpleUtils.fail("User can NOT add Meal Break successfully!",false);
				}
				waitForSeconds(2);
			}
		}
	}

	@Override
	public void addMultipleMealBreaks(List<String> mealBreakValue) throws Exception {
		int count = mealBreakList.size();
		for(int i =0;i<=9;i++){
			scrollToBottom();
			addNewMealBreak(mealBreakValue);
		}
		count = count + 10;
		if(mealBreakList.size()==count){
			SimpleUtils.pass("User can add multiple Meal Breaks successfully!");
			if(isElementEnabled(addMealBreakButton)&&isElementEnabled(addRestBreakButton)){
				verifyAdvancedStaffingRulePageShowWell();
				SimpleUtils.pass("The page shows well after adding multiple Meal Breaks");
			}else {
				SimpleUtils.fail("The page shows well after adding multiple Meal Breaks",false);
			}
		}else {
			SimpleUtils.fail("User can NOT add multiple Meal Breaks successfully!",false);
		}
	}

	@Override
	public void deleteMealBreak() throws Exception{
		int index = mealBreakList.size();
		if(index != 0){
			WebElement removeButton = mealBreakList.get(index-1).findElement(By.cssSelector("i"));
			if(isElementEnabled(removeButton)){
				clickTheElement(removeButton);
				waitForSeconds(1);
				if(mealBreakList.size() == index-1){
					SimpleUtils.pass("User can remove meal break successfully!");
				}else {
					SimpleUtils.fail("User can NOT remove meal break successfully!",false);
				}
			}else {
				SimpleUtils.fail("remove meal breaks button is not available.",false);
			}
		}else {
			SimpleUtils.fail("Still have no meal break info!",false);
		}
	}

	@Override
	public void addNewRestBreak(List<String> restBreakValue) throws Exception {
		if(isElementEnabled(mealAndRestBreaksSection)){
			clickTheElement(addRestBreakButton);
			waitForSeconds(2);
			if(restBreakList.size()!=0){
				SimpleUtils.pass("User click add button of Rest Break successfully");
			}else{
				SimpleUtils.fail("User click add button of Rest Break failed",false);
			}
			int index = restBreakList.size() - 1;
			List<WebElement> startOffsetAndBreakDuration = restBreakList.get(index).findElements(By.cssSelector("input-field"));
			for(int i = 0; i <=1; i++){
				WebElement startOffsetAndBreakDurationInput = startOffsetAndBreakDuration.get(i).findElement(By.cssSelector("input"));
				startOffsetAndBreakDurationInput.click();
				startOffsetAndBreakDurationInput.clear();
				startOffsetAndBreakDurationInput.sendKeys(restBreakValue.get(i));
				String startOffsetValue = startOffsetAndBreakDuration.get(i).findElement(By.cssSelector("div")).getAttribute("innerText").trim();
				if(startOffsetValue.equals(restBreakValue.get(i))){
					SimpleUtils.pass("User can add Rest Break successfully!");
				}else {
					SimpleUtils.fail("User can NOT add Rest Break successfully!",false);
				}
				waitForSeconds(2);
			}
		}
	}

	@Override
	public void addMultipleRestBreaks(List<String> restBreakValue) throws Exception {
		int count = restBreakList.size();
		for(int i =0;i<=9;i++){
			scrollToBottom();
			addNewRestBreak(restBreakValue);
		}
		count = count + 10;
		if(restBreakList.size()==count){
			SimpleUtils.pass("User can add multiple Rest Breaks successfully!");
			if(isElementEnabled(addMealBreakButton)&&isElementEnabled(addRestBreakButton)){
				verifyAdvancedStaffingRulePageShowWell();
				SimpleUtils.pass("The page shows well after adding multiple Rest Breaks");
			}else {
				SimpleUtils.fail("The page shows well after adding multiple Rest Breaks",false);
			}
		}else {
			SimpleUtils.fail("User can NOT add multiple Rest Breaks successfully!",false);
		}
	}

	@Override
	public void deleteRestBreak() throws Exception{
		int index = restBreakList.size();
		if(index != 0){
			WebElement removeButton = restBreakList.get(index-1).findElement(By.cssSelector("i"));
			if(isElementEnabled(removeButton)){
				clickTheElement(removeButton);
				waitForSeconds(1);
				if(restBreakList.size() == index-1){
					SimpleUtils.pass("User can remove rest break successfully!");
				}else {
					SimpleUtils.fail("User can NOT remove rest break successfully!",false);
				}
			}else {
				SimpleUtils.fail("remove rest breaks button is not available.",false);
			}
		}else {
			SimpleUtils.fail("Still have no rest break info!",false);
		}
	}

	@FindBy(css="sub-content-box.num-shifts input-field[type=\"number\"] input")
	private WebElement shiftsNumberInputField;
	@FindBy(css="sub-content-box.num-shifts input-field[type=\"number\"] div")
	private WebElement valueOfShiftsNumber;
	@FindBy(css="sub-content-box.num-shifts input-field[type=\"checkbox\"] input")
	private WebElement checkBoxOfNumberOfShifts;
	@FindBy(css="sub-content-box.num-shifts input-field[type=\"checkbox\"] input")
	private WebElement checkBoxStatusOfNumberOfShifts;
	@FindBy(css="sub-content-box.num-shifts input-field[type=\"textarea\"] textarea")
	private WebElement formulaTextAreaOfNumberOfShifts;
	@FindBy(css="sub-content-box.num-shifts input-field[type=\"textarea\"] div")
	private WebElement formulaOfNumberOfShifts;

	@Override
	public void inputNumberOfShiftsField(String shiftsNumber) throws Exception{
		if(isElementEnabled(shiftsNumberInputField)){
			shiftsNumberInputField.click();
			shiftsNumberInputField.clear();
			shiftsNumberInputField.sendKeys(shiftsNumber);
			waitForSeconds(2);
			String shiftsNumberValue = valueOfShiftsNumber.getAttribute("innerText").trim();
			if(shiftsNumberValue.equals(shiftsNumber)){
				SimpleUtils.pass("User can input value in shifts number field successfully!");
			}else{
				SimpleUtils.fail("User can NOT input value in shifts number field successfully!",false);
			}
		}
	}

	@Override
	public void validCheckBoxOfNumberOfShiftsIsClickable() throws Exception{
		if(isElementEnabled(checkBoxOfNumberOfShifts)){
			clickTheElement(checkBoxOfNumberOfShifts);
			if(isElementEnabled(formulaTextAreaOfNumberOfShifts)){
				SimpleUtils.pass("User can click the check box of Number Of Shifts successfully!");
			}else {
				SimpleUtils.fail("User failed to click the check box of Number Of Shifts!",false);
			}
		}else{
			SimpleUtils.fail("The check box of Number Of Shifts is not shown.",false);
		}
	}

	@Override
	public void inputFormulaInFormulaTextAreaOfNumberOfShifts(String shiftNumberFormula) throws Exception{
		if(isElementEnabled(formulaTextAreaOfNumberOfShifts)){
			clickTheElement(formulaTextAreaOfNumberOfShifts);
			formulaTextAreaOfNumberOfShifts.sendKeys(shiftNumberFormula);
			if(formulaOfNumberOfShifts.getAttribute("innerText").trim().equals(shiftNumberFormula)){
				SimpleUtils.pass("User can input formula for number of shifts successfully!");
			}else{
				SimpleUtils.fail("User can NOT input formula for number of shifts successfully!",false);
			}
		}else {
			SimpleUtils.fail("Formula text area of Number Of Shifts is not showing yet.",false);
		}
	}

	@FindBy(css="div.badges-edit-wrapper div.lg-button-group div")
	private List<WebElement> badgeOptions;
	@FindBy(css="div.badges-edit-wrapper tbody tr")
	private List<WebElement> badgesList;

	@Override
	public void selectBadgesForAdvanceStaffingRules() throws Exception{
		if(isElementEnabled(badgesSection)){
			for(WebElement badgeOption:badgeOptions){
				if(isElementEnabled(badgeOption)){
					clickTheElement(badgeOption);
					waitForSeconds(2);
					String classValue = badgeOption.getAttribute("class").trim();
					if(classValue.contains("lg-button-group-selected")){
						SimpleUtils.pass("User can click badge option successfully!");
					}else {
						SimpleUtils.fail("User failed to click badge option successfully!",false);
					}
				}else {
					SimpleUtils.fail(badgeOption.findElement(By.cssSelector("span")).getText().trim() + " is not shown for user!",false);
				}
			}
			if(badgesList.size()!=0){
				WebElement checkBoxInputField = badgesList.get(0).findElement(By.cssSelector("input"));
				checkBoxInputField.click();
				String classValue = checkBoxInputField.getAttribute("class").trim();
				if(classValue.contains("ng-not-empty")){
					String badgeName = badgesList.get(0).findElement(By.cssSelector("td")).getText().trim();
					SimpleUtils.pass("User can select " + badgeName + " Successfully!");
				}else {
					SimpleUtils.fail("User failed to select badge.",false);
				}
			}else{
				SimpleUtils.fail("There is no bage info in system so far!",false);
			}
		}
	}

	@FindBy(css="div.settings-work-rule-delete-icon")
	private WebElement crossButton;
	@FindBy(css="div.settings-work-rule-save-icon")
	private WebElement checkMarkButton;
	@FindBy(css="div.settings-work-role-detail-edit-rules div.settings-work-rule-container")
	private List<WebElement> staffingRulesList;

	@Override
	public void verifyCrossButtonOnAdvanceStaffingRulePage() throws Exception{
		if(isElementEnabled(crossButton)){
			clickTheElement(crossButton);
			String classValue = staffingRulesList.get(staffingRulesList.size()-1).getAttribute("class").trim();
			if(classValue.contains("deleted")){
				SimpleUtils.pass("User successfully to click the cross button.");
			}else {
				SimpleUtils.fail("User failed to click the cross button.",false);
			}
		}else {
			SimpleUtils.fail("The cross button is not shown on page now",false);
		}
	}

	@Override
	public void verifyCheckMarkButtonOnAdvanceStaffingRulePage() throws Exception{
		if(isElementEnabled(checkMarkButton)){
			clickTheElement(checkMarkButton);
			String classValue = staffingRulesList.get(staffingRulesList.size()-1).getAttribute("class").trim();
			if(classValue.contains("settings-work-rule-container-border")){
				SimpleUtils.pass("User successfully to click the checkmark button.");
			}else {
				SimpleUtils.fail("User failed to click the checkmark button.",false);
			}
		}else {
			SimpleUtils.fail("The checkmark button is not shown on page now",false);
		}
	}

	@FindBy(css="lg-button[label=\"Save\"] button")
	WebElement saveButtonOnAdvanceStaffingRulePage;
	@FindBy(css="div.banner-container")
	WebElement templateDescription;

	public void clickOnSaveButtonOfAdvanceStaffingRule(){
		if(isElementEnabled(saveButtonOnAdvanceStaffingRulePage)){
				clickTheElement(saveButtonOnAdvanceStaffingRulePage);
				waitForSeconds(2);
				if(isElementEnabled(templateDescription)){
					SimpleUtils.pass("User can click save button successfully!");
				}else {
					SimpleUtils.fail("User can NOT click save button successfully!",false);
				}
			}else {
			SimpleUtils.fail("Save button is not shown well now.",false);
		}
	}

	@Override
	public void saveOneAdvanceStaffingRule(String workRole,List<String> days) throws Exception{
		//get the staffing rules count before add one new rule
		int countBeforeSaving = Integer.valueOf(getCountOfStaffingRules(workRole));
		selectWorkRoleToEdit(workRole);
		checkTheEntryOfAddAdvancedStaffingRule();
		verifyAdvancedStaffingRulePageShowWell();
		selectDaysForDaysOfWeekSection(days);
		clickOnSaveButtonOfAdvanceStaffingRule();
        //get the staffing rules count after add one new rule
		int countAfterSaving = Integer.valueOf(getCountOfStaffingRules(workRole));

		if((countAfterSaving - countBeforeSaving) == 1){
			SimpleUtils.pass("User have successfully save one new staffing rule.");
		}else {
			SimpleUtils.fail("User failed to save one new staffing rule.",false);
		}
	}

    @FindBy(css="lg-button[label=\"Cancel\"] button")
	WebElement cancelButtonOnAdvanceStaffingRulePage;
	@FindBy(css="lg-button[label=\"Leave this page\"] button")
	WebElement leaveThisPageButton;

	public void clickOnCancelButtonOfAdvanceStaffingRule(){
		if(isElementEnabled(cancelButtonOnAdvanceStaffingRulePage)){
			clickTheElement(cancelButtonOnAdvanceStaffingRulePage);
			waitForSeconds(2);
			if(isElementEnabled(leaveThisPageButton)){
				clickTheElement(leaveThisPageButton);
				waitForSeconds(2);
				if(isElementEnabled(templateDescription)){
					SimpleUtils.pass("User can click cancel button successfully!");
				}
			}else {
				SimpleUtils.fail("User can NOT click cancel button successfully!",false);
			}
		}else {
			SimpleUtils.fail("Cancel button is not shown well now.",false);
		}
	}

	@Override
	public void cancelSaveOneAdvanceStaffingRule(String workRole,List<String> days) throws Exception{
		//get the staffing rules count before add one new rule
		int countBeforeSaving = Integer.valueOf(getCountOfStaffingRules(workRole));
		selectWorkRoleToEdit(workRole);
		checkTheEntryOfAddAdvancedStaffingRule();
		verifyAdvancedStaffingRulePageShowWell();
		selectDaysForDaysOfWeekSection(days);
		clickOnCancelButtonOfAdvanceStaffingRule();
		//get the staffing rules count after add one new rule
		int countAfterSaving = Integer.valueOf(getCountOfStaffingRules(workRole));

		if((countAfterSaving - countBeforeSaving) == 0){
			SimpleUtils.pass("User have successfully cancel save one new staffing rule.");
		}else {
			SimpleUtils.fail("User failed to cancel save one new staffing rule.",false);
		}
	}
	
	@Override
	public void addMultipleAdvanceStaffingRule(String workRole,List<String> days) throws Exception{
		//get the staffing rules count before add one new rule
		int countBeforeSaving = Integer.valueOf(getCountOfStaffingRules(workRole));
		selectWorkRoleToEdit(workRole);
		checkTheEntryOfAddAdvancedStaffingRule();
		verifyAdvancedStaffingRulePageShowWell();
		selectDaysForDaysOfWeekSection(days);
		verifyCheckMarkButtonOnAdvanceStaffingRulePage();
		checkTheEntryOfAddAdvancedStaffingRule();
		verifyAdvancedStaffingRulePageShowWell();
		selectDaysForDaysOfWeekSection(days);
		verifyCheckMarkButtonOnAdvanceStaffingRulePage();
		int countAfterSaving = staffingRulesList.size();
		if(countAfterSaving - countBeforeSaving == 2){
			SimpleUtils.pass("User can add multiple advance staffing rule successfully!");
		}else {
			SimpleUtils.fail("User can NOT add multiple advance staffing rule successfully!",false);
		}
	}

	@FindBy(css="div.settings-work-role-detail-edit-rules div[ng-if=\"ifAdvancedStaffingRule()\"]")
	private List<WebElement> advancedStaffingRuleList;

	@Override
	public void editAdvanceStaffingRule(String shiftsNumber) throws Exception{
		if(advancedStaffingRuleList.size()!=0){
			WebElement editButton = advancedStaffingRuleList.get(0).findElement(By.cssSelector("i.fa-pencil"));
			clickTheElement(editButton);
			waitForSeconds(2);
			inputNumberOfShiftsField(shiftsNumber);
			if(isElementEnabled(checkMarkButton)){
				clickTheElement(checkMarkButton);
				waitForSeconds(2);
				String shiftNumberValueInRule =advancedStaffingRuleList.get(0).findElement(By.cssSelector("div.rule-label span:nth-child(2)")).getText().trim();
				if(shiftNumberValueInRule.equals(shiftsNumber)){
					SimpleUtils.pass("User can edit advance staffing rule successfully!");
				}else {
					SimpleUtils.fail("User can't edit advance staffing rule successfully!",false);
				}
			}
		}else {
			SimpleUtils.fail("There is no advanced staffing rule.",false);
		}
	}

	@FindBy(css="div.modal-dialog button[ng-click=\"confirmDeleteAction()\"]")
	WebElement deleteButtonOnDialogPage;

	@Override
	public void deleteAdvanceStaffingRule() throws Exception{
		int countOfAdvancedStaffingRule = advancedStaffingRuleList.size();
		if(countOfAdvancedStaffingRule!=0){
			WebElement deleteButton = advancedStaffingRuleList.get(0).findElement(By.cssSelector("span.settings-work-rule-edit-delete-icon"));
			clickTheElement(deleteButton);
			if(isElementEnabled(deleteButtonOnDialogPage,2)){
				clickTheElement(deleteButtonOnDialogPage);
			}
			waitForSeconds(2);
			if(advancedStaffingRuleList.get(0).findElements(By.cssSelector("div[ng-if=\"$ctrl.isViewMode()\"]>div")).size()==1){
				SimpleUtils.pass("User can delete advance staffing rule successfully!");
			}else {
				SimpleUtils.fail("User can't delete advance staffing rule successfully!",false);
			}
		}else {
			SimpleUtils.fail("There is no advanced staffing rule.",false);
		}
	}
	@FindBy(css="div.settings-work-role-detail-edit-rules div[ng-if=\"ifStaffingRule()\"]")
	private List<WebElement> basicStaffingRuleList;
	@Override
	public void deleteBasicStaffingRule() throws Exception{
		int countOfAdvancedStaffingRule = basicStaffingRuleList.size();
		if(countOfAdvancedStaffingRule!=0){
			WebElement deleteButton = basicStaffingRuleList.get(0).findElement(By.cssSelector("span.settings-work-rule-edit-delete-icon"));
			clickTheElement(deleteButton);
			if(isElementEnabled(deleteButtonOnDialogPage,2)){
				clickTheElement(deleteButtonOnDialogPage);
			}
			waitForSeconds(2);
			if(basicStaffingRuleList.get(0).findElements(By.cssSelector("div[ng-if=\"$ctrl.isViewMode()\"]>div")).size()==1){
				SimpleUtils.pass("User can delete basic staffing rule successfully!");
			}else {
				SimpleUtils.fail("User can't delete advance staffing rule successfully!",false);
			}
		}else {
			SimpleUtils.fail("There is no advanced staffing rule.",false);
		}
	}

	@Override
	public void saveBtnIsClickable() throws Exception {
		scrollToBottom();
		if (isElementLoaded(saveButton,5)) {
			click(saveButton);
		}else
			SimpleUtils.fail("Save button load failed",false);
	}

	@FindBy(css="div.settings-work-rule-container")
	private WebElement scheduleRulesList;

	@Override
	public void deleteAllScheduleRules() throws Exception{
		if(staffingRulesList.size()!=0){
			for(WebElement staffingRule:staffingRulesList){
				WebElement deleteButton = staffingRule.findElement(By.cssSelector("span.settings-work-rule-edit-delete-icon"));
				if(isElementEnabled(deleteButton,2)){
					clickTheElement(deleteButton);
					waitForSeconds(2);
					clickTheElement(deleteButtonOnDialogPage);
					if(staffingRule.findElements(By.cssSelector("div[ng-if=\"$ctrl.isViewMode()\"]>div")).size()==1){
						SimpleUtils.pass("User can delete staffing rules successfully!");
					}else {
						SimpleUtils.fail("User failed to delete staffing rules.",false);
					}
				}
			}
		}else {
			SimpleUtils.report("There is not staffing rule so far.");
		}
	}

	@Override
	public void clickOnSaveButtonOnScheduleRulesListPage() throws Exception{
		if(isElementEnabled(saveButton,5)){
			clickTheElement(saveButton);
			waitForSeconds(30);
			if (isElementEnabled(dropdownArrowBTN,5) || isElementEnabled(dropdownArrowBTN,5)) {
				SimpleUtils.pass("User click on save button on schedule rule list page successfully!");
			}else
				SimpleUtils.fail("User failed to click on save button on schedule rule list page!",false);
		}else {
			SimpleUtils.fail("No save button displayed on page",false);
		}
	}

	//added by Estelle to verify ClockIn
	@FindBy(css="input-field[options=\"$ctrl.dynamicGroupList\"] > ng-form > div.select-wrapper>select")
	private WebElement clockInSelector;
	@FindBy(css="form-section[form-title=\"Clock in Group\"")
	private WebElement clockInForm;
	@Override
	public void verifyClockInDisplayAndSelect(List<String> clockInGroup) throws Exception {
		scrollToBottom();
		if (isElementLoaded(clockInForm,5)&&clockInForm.getText().contains("Locations that employees can clock-in\n")&&isElementLoaded(clockInSelector,5)) {
			SimpleUtils.pass("Clock in form show well");
			click(clockInSelector);
			List<WebElement> clockInOptionList = clockInSelector.findElements(By.cssSelector("option"));
			List<String> clockInOptionListText = new ArrayList<>();
			for (WebElement option:clockInOptionList) {
				if (!option.getText().equalsIgnoreCase("")) {
					clockInOptionListText.add(option.getText());
				}
			}
			if (clockInOptionListText.size() != clockInGroup.size()) {
				SimpleUtils.fail("Clock-in list size in TA is not as same as Clock-in list size in Global dynamic group",false);

			}else
				for (Object object : clockInOptionListText) {
					if (!clockInGroup.contains(object)){
						SimpleUtils.fail("Clock-in list in TA is not as same as Clock-in list in Global dynamic group",false);
						break;
					}else
						SimpleUtils.pass("Clock-in list size in TA is as same as Clock-in list size in Global dynamic group");
				}
			for (int i = 0; i < Integer.valueOf(clockInOptionListText.size()); i++) {
				selectByVisibleText(clockInSelector,clockInOptionListText.get(i));
			}
		}else
			SimpleUtils.fail("Clock-in form load failed",false);
	}

	@FindBy(css ="question-input[question-title=\"Do you want to send Shift Offers to other locations?\"] > div > div.lg-question-input__wrapper > ng-transclude > yes-no > ng-form > lg-button-group >div>div")
	private List<WebElement> yesNoForWFS;
	@Override
	public void setWFS(String wfsMode) {
		if (areListElementVisible(yesNoForWFS,5)) {
			for (WebElement yesNoOption : yesNoForWFS
				 ) {
				if (yesNoOption.getText().equalsIgnoreCase(wfsMode)) {
					click(yesNoOption);
					if (wfsMode.equalsIgnoreCase("yes")) {
						setWFSStatus(true);
					} else
						setWFSStatus(false);
					break;
				}
			}
 			SimpleUtils.pass("Do you want to send Shift Offers to other locations?  to "+ wfsMode);
		}else
			SimpleUtils.fail("Workforce sharing group ",false);
	}

	@Override
	public boolean isWFSEnabled() {
		boolean isWFSEnabled = false;
		if (areListElementVisible(yesNoForWFS,5)) {
			if (yesNoForWFS.get(0).getAttribute("class").contains("selected")) {
				isWFSEnabled = true;
			}
		}else
			SimpleUtils.fail("Workforce sharing group settings fail to load! ",false);
		setWFSStatus(true);
		return isWFSEnabled;
	}

	@FindBy(css = "input-field[options=\"$ctrl.groupOptions\"]>ng-form>div:nth-child(3)>select")
	private WebElement wfsSelector;
	@Override
	public void selectWFSGroup(String wfsName) throws Exception {
		if (isElementLoaded(wfsSelector,5)) {
			selectByVisibleText(wfsSelector,wfsName);
		}else
			SimpleUtils.fail("Workforce sharing group selector load failed",false);

	}

	@FindBy(css = "[ng-if=\"$ctrl.saveAsLabel\"]")
	private WebElement publishTemplateButton;

	@FindBy(css = "div.modal-dialog")
	private WebElement publishTemplateConfirmModal;

	@FindBy(css = "[ng-click=\"$ctrl.submit(true)\"]")
	private WebElement okButtonOnPublishTemplateConfirmModal;

	@FindBy(css = "div.lg-toast")
	private WebElement successMsg;

	public void displaySuccessMessage() throws Exception {
		if (isElementLoaded(successMsg, 20) && successMsg.getText().contains("Success!")) {
			SimpleUtils.pass("Success message displayed successfully." + successMsg.getText());
			waitForSeconds(2);
		} else {
			SimpleUtils.report("Success pop up not displayed successfully.");
			waitForSeconds(3);
		}
	}

	@Override
	public void commitTypeCheck() throws Exception{
		String[] supportedType={"Save as draft","Publish now","Publish later"};
		//scroll to the bottom of page
		scrollToBottom();
		clickTheElement(templateSaveDrop);
		if(areListElementVisible(templateSaveOptions)) {
			SimpleUtils.pass("Save and publish options loaded successfully");
			waitForSeconds(2);
			for (String sp : supportedType) {
				for(WebElement optionele:templateSaveOptions){
					if(optionele.getText().trim().equals(sp)) {
						SimpleUtils.pass("Option"+sp+"showed in save and publish options list");
						continue;
					}
				}
			}
		}
		else
			SimpleUtils.fail("No save and publish options loaded!",false);
		scrollToTop();
	}

	@Override
	public void publishNowTheTemplate() throws Exception {
		if (isElementLoaded(dropdownArrowButton,5)) {
			scrollToElement(dropdownArrowButton);
			click(dropdownArrowButton);
			click(publishNowButton);
			click(publishTemplateButton);
			if(isElementLoaded(publishTemplateConfirmModal, 5)){
				click(okButtonOnPublishTemplateConfirmModal);
				displaySuccessMessage();
			} else
				SimpleUtils.fail("Publish template confirm modal fail to load", false);
		}else

			SimpleUtils.fail("Publish template dropdown button load failed",false);
	}

	@FindBy(css="[ng-if=\"$ctrl.saveAsLabel\"] button")
	private WebElement publishNowBTN;

	@Override
	public void publishNowTemplate() throws Exception {
		if (isElementLoaded(dropdownArrowBTN,5)) {
			clickTheElement(dropdownArrowBTN);
			waitForSeconds(2);
			clickTheElement(publishNowButton);
			waitForSeconds(2);
			clickTheElement(publishNowBTN);
			waitForSeconds(5);
			if(isElementLoaded(publishTemplateConfirmModal, 5)){
				click(okButtonOnPublishTemplateConfirmModal);
				displaySuccessMessage();
			}
		}else
			SimpleUtils.fail("Publish template dropdown button load failed",false);
	}

	@Override
	public void validateAdvanceStaffingRuleShowing(String startEvent,String startOffsetTime,String startEventPoint,String startTimeUnit,
															 String endEvent,String endOffsetTime,String endEventPoint,String endTimeUnit,
															 List<String> days,String shiftsNumber) throws Exception{
		selectShiftStartTimeEvent(startEvent);
		inputOffsetTimeForShiftStart(startOffsetTime,startEventPoint);
		selectShiftStartTimeUnit(startTimeUnit);
		selectShiftEndTimeEvent(endEvent);
		inputOffsetTimeForShiftEnd(endOffsetTime,endEventPoint);
		selectShiftEndTimeUnit(endTimeUnit);
		selectDaysForDaysOfWeekSection(days);
		inputNumberOfShiftsField(shiftsNumber);
		verifyCheckMarkButtonOnAdvanceStaffingRulePage();
		List<WebElement> staffingRuleText = staffingRulesList.get(staffingRulesList.size()-1).findElements(By.cssSelector("span[ng-bind-html=\"$ctrl.ruleLabelText\"] span"));
		List<String> daysInRule = Arrays.asList(staffingRuleText.get(2).getText().trim().split(","));
		List<String> newDaysInRule = new ArrayList<>();
		for(String dayInRules:daysInRule){
			String newDayInRule = dayInRules.trim().toLowerCase();
			newDaysInRule.add(newDayInRule);
		}
		List<String> newDays = new ArrayList<>();
		for(String day:days){
			String newDay = day.substring(0,3).toLowerCase();
			newDays.add(newDay);
		}
		Collections.sort(newDaysInRule);
		Collections.sort(newDays);
		if(ListUtils.isEqualList(newDaysInRule,newDays)){
			SimpleUtils.pass("The days info in rule are correct");
		}else{
			SimpleUtils.fail("The days info in rule are NOT correct",false);
		}
		String shiftsNumberInRule = staffingRuleText.get(1).getText().trim();
		if(shiftsNumberInRule.equals(shiftsNumber)){
			SimpleUtils.pass("The shifts number info in rule is correct");
		}else {
			SimpleUtils.fail("The shifts number info in rule is NOT correct",false);
		}

		String startTimeInfo = staffingRuleText.get(0).getText().trim().split(",")[0];
		String endTimeInfo = staffingRuleText.get(0).getText().trim().split(",")[1];
		String startEventInRule = "";
		for(int i = 5;i<startTimeInfo.split(" ").length;i++){
			startEventInRule = startEventInRule + startTimeInfo.split(" ")[i] + " ";
		}
		startEventInRule.trim();
		if(startEventInRule.contains(startEvent)){
			SimpleUtils.pass("The start event info in rule is correct");
		}else {
			SimpleUtils.fail("The start event info in rule is NOT correct",false);
		}
		String startOffsetTimeInRule = startTimeInfo.split(" ")[2].trim();
		if(startOffsetTimeInRule.equals(startOffsetTime)){
			SimpleUtils.pass("The start Offset Time info in rule is correct");
		}else {
			SimpleUtils.fail("The start Offset Time info in rule is NOT correct",false);
		}
		String startEventPointInRule = startTimeInfo.split(" ")[4].trim();
		if(startEventPointInRule.equals(startEventPoint)){
			SimpleUtils.pass("The start Event Point info in rule is correct");
		}else {
			SimpleUtils.fail("The start Event Point info in rule is NOT correct",false);
		}
		String startTimeUnitInRule = startTimeInfo.split(" ")[3].trim();
		if(startTimeUnitInRule.equals(startTimeUnit)){
			SimpleUtils.pass("The start Time Unit info in rule is correct");
		}else {
			SimpleUtils.fail("The start Time Unit info in rule is NOT correct",false);
		}
		String endEventInRule = "";
		for(int i = 5;i<endTimeInfo.split(" ").length;i++){
			endEventInRule = endEventInRule + endTimeInfo.split(" ")[i] + " ";
		}
		endEventInRule.trim();
		if(endEventInRule.contains(endEvent)){
			SimpleUtils.pass("The end Event info in rule is correct");
		}else {
			SimpleUtils.fail("The end Event info in rule is NOT correct",false);
		}
		String endOffsetTimeInRule = endTimeInfo.split(" ")[3].trim();
		if(endOffsetTimeInRule.contains(endOffsetTime)){
			SimpleUtils.pass("The end Offset Time in rule is correct");
		}else {
			SimpleUtils.fail("The end Offset Time in rule is NOT correct",false);
		}
		String endEventPointInRule = endTimeInfo.split(" ")[5].trim();
		if(endEventPointInRule.contains(endEventPoint)){
			SimpleUtils.pass("The end Event Point in rule is correct");
		}else {
			SimpleUtils.fail("The end Event Point in rule is NOT correct",false);
		}
		String endTimeUnitInRule = endTimeInfo.split(" ")[4].trim();
		if(endTimeUnitInRule.contains(endTimeUnit)){
			SimpleUtils.pass("The end Time Unit in rule is correct");
		}else {
			SimpleUtils.fail("The end Time Unit in rule is NOT correct",false);
		}
	}

	@FindBy(css="div.lg-modal")
	private WebElement createNewTemplatePopupWindow;
	@FindBy(css="input-field[label=\"Name this template\"] input")
	private WebElement newTemplateName;
	@FindBy(css="input-field[label=\"Description\"] textarea")
	WebElement newTemplateDescription;
	@FindBy(css="lg-button[label=\"Continue\"] button")
	private WebElement continueBTN;
	@FindBy(css="span.wm-close-link")
	private WebElement welcomeCloseButton;
	@FindBy(css="question-input[question-title=\"How many minutes late can employees clock in to scheduled shifts?\"]")
	private WebElement taTemplateSpecialField;

	@Override
	public void createNewTemplate(String templateName) throws Exception{
		if(isElementLoaded(newTemplateBTN, 10)){
			clickTheElement(newTemplateBTN);
			waitForSeconds(1);
			if(isElementEnabled(createNewTemplatePopupWindow, 10)){
				SimpleUtils.pass("User can click new template button successfully!");
				clickTheElement(newTemplateName);
				newTemplateName.sendKeys(templateName);
				clickTheElement(newTemplateDescription);
				newTemplateDescription.sendKeys(templateName);
				clickTheElement(continueBTN);
				waitForSeconds(5);
				if(isElementEnabled(welcomeCloseButton, 5)){
					clickTheElement(welcomeCloseButton);
				}
				if(isElementEnabled(taTemplateSpecialField, 5)){
					clickTheElement(taTemplateSpecialField.findElement(By.cssSelector("input")));
					taTemplateSpecialField.findElement(By.cssSelector("input")).clear();
					taTemplateSpecialField.findElement(By.cssSelector("input")).sendKeys("5");
				}
				if(isElementEnabled(saveAsDraftButton, 5)
						&& isElementLoaded(templateDetailsAssociateTab, 10)
						&& isElementLoaded(templateDetailsBTN, 10)
						&& isElementLoaded(templateExternalAttributesBTN, 10)){
					SimpleUtils.pass("User can click continue button successfully!");
					clickTheElement(saveAsDraftButton);
					waitForSeconds(5);
				}else {
					SimpleUtils.fail("User can't click continue button successfully!",false);
				}
			}else {
				SimpleUtils.fail("User can't click new template button successfully!",false);
			}
		}
		searchTemplate(templateName);
		String newTemplateName = templateNameList.get(0).getText().trim();
		if(newTemplateName.contains(templateName)){
			SimpleUtils.pass("User can add new template successfully!");
		}else {
			SimpleUtils.fail("User can't add new template successfully",false);
		}
	}

	@FindBy(css="lg-button[ng-click=\"deleteTemplate()\"] button")
	private WebElement deleteTemplateButton;

	@FindBy(css="modal[modal-title=\"Deleting Template\"]")
	private WebElement deleteTemplateDialog;

	@FindBy(css="modal[modal-title=\"Archive Template\"]")
	private WebElement archiveTemplateDialog;

	@FindBy(css="modal[modal-title=\"Deleting Template\"] lg-button[label=\"OK\"] button")
	private WebElement okButtonOnDeleteTemplateDialog;

	@Override
	public void deleteNewCreatedTemplate(String templateName) throws Exception{
		if(areListElementVisible(templateNameList, 5) && templateNameList.size() > 0){
			for (WebElement templateNameElement: templateNameList) {
				if (templateName.equalsIgnoreCase(templateNameElement.getText())) {
					clickTheElement(templateNameElement);
					waitForSeconds(5);

					if (isElementEnabled(deleteTemplateButton, 3)) {
						clickTheElement(deleteTemplateButton);
						if (isElementEnabled(deleteTemplateDialog, 3)) {
							clickTheElement(okButtonOnDeleteTemplateDialog);
							waitForSeconds(5);
							String firstTemplateName = templateNameList.get(0).getText().trim();
							if (!firstTemplateName.equals(templateName)) {
								SimpleUtils.pass("User has deleted new created template successfully!");
							} else {
								SimpleUtils.fail("User failed to delete new created template!", false);
							}
						}
					} else {
						SimpleUtils.fail("Clicking the template failed.", false);
					}
					break;
				}
			}
		}else {
			SimpleUtils.fail("Create new template failed.",false);
		}

	}

    @Override
	public void addAllTypeOfTemplate(String templateName) throws Exception {
			for(int i = 0 ;i <=5; i++){
				clickTheElement(configurationCardsList.get(i));
				waitForSeconds(1);
				createNewTemplate(templateName);
				deleteNewCreatedTemplate(templateName);
				goToConfigurationPage();
			}
	}

	//Added by Mary to check 'Automatically convert unassigned shifts to open shifts when creating a new schedule?' and 'Automatically convert unassigned shifts to open shifts when coping a schedule?'
	@FindBy(css = "question-input[question-title=\"Automatically convert unassigned shifts to open shifts when creating a new schedule?\"]")
	private WebElement convertUnassignedShiftsToOpenWhenCreatingScheduleSetting;

	@FindBy(css = "question-input[question-title=\"Automatically convert unassigned shifts to open shifts when creating a new schedule?\"] .lg-question-input__text")
	private WebElement convertUnassignedShiftsToOpenWhenCreatingScheduleSettingMessage;

	@FindBy(css = "question-input[question-title=\"Automatically convert unassigned shifts to open shifts when creating a new schedule?\"] select[ng-change=\"$ctrl.handleChange()\"]")
	private WebElement convertUnassignedShiftsToOpenWhenCreatingScheduleSettingDropdown;

	@FindBy(css = "question-input[question-title=\"Automatically convert unassigned shifts to open shifts when copying a schedule?\"]")
	private WebElement convertUnassignedShiftsToOpenWhenCopyingScheduleSetting;

	@FindBy(css = "question-input[question-title=\"Automatically convert unassigned shifts to open shifts when copying a schedule?\"] .lg-question-input__text")
	private WebElement convertUnassignedShiftsToOpenWhenCopyingScheduleSettingMessage;

	@FindBy(css = "question-input[question-title=\"Automatically convert unassigned shifts to open shifts when copying a schedule?\"] select[ng-change=\"$ctrl.handleChange()\"]")
	private WebElement convertUnassignedShiftsToOpenWhenCopyingScheduleSettingDropdown;

	@Override
	public void verifyConvertUnassignedShiftsToOpenSetting() throws Exception {
		if (isElementLoaded(convertUnassignedShiftsToOpenWhenCreatingScheduleSetting, 10)
				&& isElementLoaded(convertUnassignedShiftsToOpenWhenCreatingScheduleSettingMessage, 10)
				&& isElementLoaded(convertUnassignedShiftsToOpenWhenCreatingScheduleSettingDropdown, 10)
				&& isElementLoaded(convertUnassignedShiftsToOpenWhenCopyingScheduleSetting, 10)
				&& isElementLoaded(convertUnassignedShiftsToOpenWhenCopyingScheduleSettingMessage, 10)
				&& isElementLoaded(convertUnassignedShiftsToOpenWhenCopyingScheduleSettingDropdown, 10)) {

			//Check the message
			String createScheduleMessage = "Automatically convert unassigned shifts to open shifts when creating a new schedule?";
			String copyScheduleMessage = "Automatically convert unassigned shifts to open shifts when copying a schedule?";
			if (convertUnassignedShiftsToOpenWhenCreatingScheduleSettingMessage.getText().equalsIgnoreCase(createScheduleMessage)){
				SimpleUtils.pass("OP - Schedule Collaboration: Open Shift : Convert unassigned shifts to open settings creating schedule settings message display correctly! ");
			} else
				SimpleUtils.fail("OP - Schedule Collaboration: Open Shift : Convert unassigned shifts to open when creating schedule settings message display incorrectly!  Expected message is :'"
						+ createScheduleMessage + "'. Actual message is : '" +convertUnassignedShiftsToOpenWhenCreatingScheduleSettingMessage.getText()+ "'", false);

			if (convertUnassignedShiftsToOpenWhenCopyingScheduleSettingMessage.getText().equalsIgnoreCase(copyScheduleMessage)){
				SimpleUtils.pass("OP - Schedule Collaboration: Open Shift : Convert unassigned shifts to open when coping schedule settings message display correctly! ");
			} else
				SimpleUtils.fail("OP - Schedule Collaboration: Open Shift : Convert unassigned shifts to open when coping schedule settings message display incorrectly!  Expected message is :'"
						+ createScheduleMessage + "'. Actual message is : '" +convertUnassignedShiftsToOpenWhenCopyingScheduleSettingMessage.getText()+ "'", false);



			List<String> convertUnassignedShiftsToOpenSettingOptions = new ArrayList<>();
			convertUnassignedShiftsToOpenSettingOptions.add("Yes, all unassigned shifts");
			convertUnassignedShiftsToOpenSettingOptions.add("Yes, except opening/closing shifts");
			convertUnassignedShiftsToOpenSettingOptions.add("No, keep as unassigned");

			//Check the options
			Select dropdown = new Select(convertUnassignedShiftsToOpenWhenCreatingScheduleSettingDropdown);
			List<WebElement> dropdownOptions = dropdown.getOptions();
			for (int i = 0; i< dropdownOptions.size(); i++) {
				if (dropdownOptions.get(i).getText().equalsIgnoreCase(convertUnassignedShiftsToOpenSettingOptions.get(i))) {
					SimpleUtils.pass("OP - Schedule Collaboration: Open Shift : Convert unassigned shifts to open when creating schedule settings option: '" +dropdownOptions.get(i).getText()+ "' display correctly! ");
				} else
					SimpleUtils.fail("OP - Schedule Collaboration: Open Shift : Convert unassigned shifts to open when creating schedule settings option display incorrectly, expected is : '" +convertUnassignedShiftsToOpenSettingOptions.get(i)+
							"' , the actual is : '"+ dropdownOptions.get(i).getText()+"'. ", false);
			}

			//Check the options
			dropdown = new Select(convertUnassignedShiftsToOpenWhenCopyingScheduleSettingDropdown);
			dropdownOptions = dropdown.getOptions();
			for (int i = 0; i< dropdownOptions.size(); i++) {
				if (dropdownOptions.get(i).getText().equalsIgnoreCase(convertUnassignedShiftsToOpenSettingOptions.get(i))) {
					SimpleUtils.pass("OP - Schedule Collaboration: Open Shift : Convert unassigned shifts to open when coping schedule settings option: '" +dropdownOptions.get(i).getText()+ "' display correctly! ");
				} else
					SimpleUtils.fail("OP - Schedule Collaboration: Open Shift : Convert unassigned shifts to open when coping schedule settings option display incorrectly, expected is : '" +convertUnassignedShiftsToOpenSettingOptions.get(i)+
							"' , the actual is : '"+ dropdownOptions.get(i).getText()+"'. ", false);
			}

		} else
			SimpleUtils.fail("OP Configuration Page: Schedule Collaboration: Open Shift : Convert unassigned shifts to open when coping schedule settings not loaded.", false);
	}


	@Override
	public void updateConvertUnassignedShiftsToOpenWhenCreatingScheduleSettingOption(String option) throws Exception {
		if (isElementLoaded(convertUnassignedShiftsToOpenWhenCreatingScheduleSettingDropdown, 10)) {
			Select dropdown = new Select(convertUnassignedShiftsToOpenWhenCreatingScheduleSettingDropdown);
			dropdown.selectByVisibleText(option);
			SimpleUtils.pass("OP Page: Schedule Collaboration: Open Shift : Convert unassigned shifts to open when creating schedule settings been changed successfully");
		} else {
			SimpleUtils.fail("OP - Schedule Collaboration: Open Shift : Convert unassigned shifts to open when creating schedule settings dropdown list not loaded.", false);
		}
	}

	@Override
	public void updateConvertUnassignedShiftsToOpenWhenCopyingScheduleSettingOption(String option) throws Exception {
		if (isElementLoaded(convertUnassignedShiftsToOpenWhenCopyingScheduleSettingDropdown, 10)) {
			Select dropdown = new Select(convertUnassignedShiftsToOpenWhenCopyingScheduleSettingDropdown);
			dropdown.selectByVisibleText(option);
			SimpleUtils.pass("OP Page: Schedule Collaboration: Open Shift : Convert unassigned shifts to open when copying schedule settings been changed successfully");
		} else {
			SimpleUtils.fail("OP - Schedule Collaboration: Open Shift : Convert unassigned shifts to open when copying schedule settings dropdown list not loaded.", false);
		}
	}

	@FindBy(css="input-field[value=\"$ctrl.bufferHourMode\"]")
	private List<WebElement> operatingBufferHoursOptions;

	@FindBy(css="[value=\"$ctrl.openingBufferHours\"] input")
	private WebElement openingBufferHours;

	@FindBy(css="[value=\"$ctrl.closingBufferHours\"] input")
	private WebElement closingBufferHours;
	@FindBy(css="span[ng-if*='getSelectedHolidays']")
	private WebElement selectHolidayLink;
	@FindBy(css="modal[modal-title=\"Manage Holidays\"]")
	private WebElement holidayDialog;
	@FindBy(css="modal[modal-title=\"Manage Holidays\"] h1 div")
	private WebElement holidayDialogTitle;
	@FindBy(css="select[aria-label=\"Country\"]")
	private WebElement holidayDialogCountrySelection;
	@FindBy(css="tr[ng-style*='item.selected']")
	private List<WebElement> holidayItems;
	@FindBy(css="input[placeholder=\"You can search by holiday name.\"]")
	private WebElement holidaySearchInput;
	@FindBy(css="tr[ng-repeat*=\"customHolidays\"] input-field[ng-if=\"item.isEditing\"] input")
	private List<WebElement> customerHolidayName;
	@FindBy(css="tr[ng-repeat*=\"customHolidays\"] lg-calendar-input div.lg-picker-input>input-field")
	private List<WebElement> calendarPicker;
	@FindBy(css="tr[ng-repeat*=\"customHolidays\"] input[type=\"checkbox\"]")
	private List<WebElement> customerHolidayCheckbox;
	@FindBy(css="i.fa.fa-check-circle")
	private List<WebElement> customerHolidaySaveIcon;
	@FindBy(css="tr[ng-repeat*=\"customHolidays\"] span[ng-if=\"!item.isEditing\"].edit")
	private WebElement customerHolidayEdit;
	@FindBy(css="tr[ng-repeat*=\"customHolidays\"] span[ng-if=\"!item.isEditing\"].remove")
	private WebElement customerHolidayRemove;
	@FindBy(css="i.fa.fa-times-circle")
	private WebElement customerHolidayCacelIcon;
	@FindBy(css="tr[ng-repeat*=\"customHolidays\"] span[ng-if='!item.isEditing'].fs-14")
	private WebElement customerHolidayEditedName;
	@FindBy(css="tr[ng-repeat='holiday in $ctrl.getSelectedHolidays()']")
	private List<WebElement> selectedHolidaysInTemplate;




	private void createAcustomerHoliday(String holidaName){
		//set a customer name and save
		customerHolidayName.get(0).sendKeys(holidaName);
		waitForSeconds(2);
		clickTheElement(calendarPicker.get(0));
		waitForSeconds(2);
		selectDateForTimesheet(3);
		clickTheElement(customerHolidayCheckbox.get(0));
		clickTheElement(customerHolidaySaveIcon.get(0));
		waitForSeconds(2);
	}

	public void holidaysDataCheckAndSelect(String custoHolyName)throws Exception{
		String checkBoxCss="td>div>input-field[type=\"checkbox\"] input";
		String selectHoliday=null;
		//click the Select Holidays link
        clickTheElement(selectHolidayLink);
        waitForSeconds(2);
        if(isElementLoaded(holidayDialog)&&getText(holidayDialogTitle).trim().equals("Manage Holidays")){
        	SimpleUtils.pass("Select holiday dialog pop up successfully");
			//filter holidays
			holidaySearchInput.sendKeys("Memorial");
			waitForSeconds(2);
			if(areListElementVisible(holidayItems))
				SimpleUtils.report("Holiday search with resulted");
			holidaySearchInput.clear();
			selectByVisibleText(holidayDialogCountrySelection,"United States");
			waitForSeconds(2);
			//select a holiday
			if(areListElementVisible(holidayItems)){
				SimpleUtils.pass("Holidays options loaded successfully");
			    //select the first holiday
			    clickTheElement(holidayItems.get(0).findElement(By.cssSelector(checkBoxCss)));
			    waitForSeconds(2);
			    //get the holiday name
				selectHoliday=holidayItems.get(0).findElement(By.cssSelector("span.fs-14")).getText().trim();
			}
			else
				SimpleUtils.fail("Holidays options loaded fail",false);
			//create a customer holiday
			createAcustomerHoliday(custoHolyName);
			//edit the holiday name to check button works or not
			clickTheElement(customerHolidayEdit);
			waitForSeconds(2);
			//modify the holiday name
			customerHolidayName.get(0).clear();
			customerHolidayName.get(0).sendKeys(custoHolyName+" Modified");
			//save the change
			clickTheElement(customerHolidaySaveIcon.get(0));
			waitForSeconds(2);
			//get the modified holiday name
			String modifiedName=customerHolidayEditedName.getText().trim();
			if(modifiedName.equals(custoHolyName+" Modified"))
				SimpleUtils.pass("Customer holiday name modified successfully");
			else
				SimpleUtils.report("Customer holiday name modified Failed");
			//remove the customer holiday
			clickTheElement(customerHolidayRemove);
			//create the customer holiday again
			createAcustomerHoliday(custoHolyName);
			//save
			clickTheElement(saveBtnInManageDayparts);
			waitForSeconds(2);
			//check the selected or created customer holiday show on template page or not
			if(areListElementVisible(selectedHolidaysInTemplate)){
				SimpleUtils.pass("Selected holidays shows on template detail page");
				//check the customer selected holiday name
				for(WebElement es:selectedHolidaysInTemplate) {
					if (es.findElement(By.cssSelector(" td >span")).getText().trim().equals(custoHolyName + " Modified"))
						SimpleUtils.pass("The customer holiday show on the page successfully");
					else if (selectHoliday!=null&&es.findElement(By.cssSelector(" td >span")).getText().trim().equals(selectHoliday))
						SimpleUtils.pass("The specified selected holiday show on the page successfully");
				}

			}
			else
				SimpleUtils.fail("Selected holidays not show on template detail page",true);
			//back to customer holiday to remove the customer holiday and unselected specified holiday
			clickTheElement(selectHolidayLink);
			waitForSeconds(2);
			//unselect the specified holiday
			if(areListElementVisible(holidayItems)) {
				SimpleUtils.pass("Holidays options loaded successfully");
				//select the first holiday
				clickTheElement(holidayItems.get(0).findElement(By.cssSelector(checkBoxCss)));
			}
			//remove the customer holiday
			clickTheElement(customerHolidayRemove);
			//click save
			clickTheElement(saveBtnInManageDayparts);
			waitForSeconds(2);
			//check if holiday show on template detail or not
			if(!areListElementVisible(selectedHolidaysInTemplate,20))
				SimpleUtils.pass("Unselect the specified holiday and remove the customer holiday is successfully!");
			//save the template as draft again
			if(isElementEnabled(saveAsDraftButton)) {
				SimpleUtils.pass("User can click save as draft button!");
				clickTheElement(saveAsDraftButton);
				waitForSeconds(3);
			}
		}
        else
        	SimpleUtils.fail("Select holiday dialog not pop up",false);


	}


	// Option: None, StartEnd, BufferHour, ContinuousOperation
	public void selectOperatingBufferHours(String option) throws Exception {
		if (areListElementVisible(operatingBufferHoursOptions, 10) && operatingBufferHoursOptions.size() == 4) {
			for (WebElement operatingBufferHours: operatingBufferHoursOptions){
				if(operatingBufferHours.getAttribute("assigned-value").contains(option)){
					WebElement inputButton = operatingBufferHours.findElement(By.className("input-form"));
					if (!inputButton.getAttribute("class").contains("ng-valid-parse")) {
						click(inputButton);
						SimpleUtils.pass("OP Page: Operating Hours: Operating / Buffer Hours : The '"+option+"' option been selected successfully! ");
					} else
						SimpleUtils.pass("OP Page: Operating Hours: Operating / Buffer Hours : The '"+option+"' option has been selected! ");
					break;
				}
			}
		} else {
			SimpleUtils.fail("OP - Operating Hours: Operating / Buffer Hours : Operating hours options not loaded.", false);
		}
	}

	public void setOpeningAndClosingBufferHours (int openingBufferHour, int closingBufferHour) throws Exception {

		if (isElementLoaded(openingBufferHours, 5) && isElementLoaded(closingBufferHours, 5)){
			openingBufferHours.clear();
			closingBufferHours.clear();
			openingBufferHours.sendKeys(String.valueOf(openingBufferHour));
			closingBufferHours.sendKeys(String.valueOf(closingBufferHour));
		} else
			SimpleUtils.fail("OP - Operating Hours: Operating / Buffer Hours : Operating buffer hours and closing buffer hours are not loaded.", false);
	}


	@FindBy(css ="question-input[question-title=\"Enable schedule copy restrictions\"] > div > div.lg-question-input__wrapper > ng-transclude > yes-no > ng-form > lg-button-group >div>div")
	private List<WebElement> yesNoForScheduleCopyRestrictions;
	@Override
	public void setScheduleCopyRestrictions(String yesOrNo) throws Exception {
		if (areListElementVisible(yesNoForScheduleCopyRestrictions,5)) {
			for (WebElement option : yesNoForScheduleCopyRestrictions) {
				if (option.getText().equalsIgnoreCase(yesOrNo)) {
					click(option);
					break;
				}
			}
			SimpleUtils.pass("Set copy restriction to "+ yesOrNo + " successfully! ");
		}else
			SimpleUtils.fail("Set copy restriction setting fail to load!  ",false);
	}

	// Added by Julie
	@FindBy (css = ".dayparts span.manage-action-wrapper")
	private WebElement manageDaypartsBtn;

	@FindBy (css = "[ng-if=\"!$ctrl.getSelectedDayparts().length\"]")
	private WebElement selectDapartsBtn;

	@FindBy (css = "table div input.ng-not-empty")
	private List<WebElement> checkedBoxes;

	@FindBy (css = "table div.lg-select-list__thumbnail-wrapper input-field[type=\"checkbox\"]")
	private List<WebElement> allCheckBoxes;

	@FindBy (css = "div.lg-select-list__table-wrapper table tbody tr")
	private List<WebElement> rowsInManageDayparts;

	@FindBy (xpath = "//body/div[1]//lg-daypart-weekday/div[@class=\"availability-row availability-row-active ng-scope\"]")
	private List<WebElement> rowsWhenSetDaypart;

	@FindBy (css = ".availability-row-time [ng-repeat=\"r in $ctrl.hoursRange\"]")
	private List<WebElement> timeRangeWhenSetDaypart;

	@FindBy (css = ".daypart-legend .item")
	private List<WebElement> itemsOfDaypart;

	@FindBy(css = "lg-button[label=\"Save\"] button")
	private WebElement saveBtnInManageDayparts;

	@FindBy(css = ".business-hours lg-button[label=\"Edit\"]")
	private List<WebElement> editBtnsOfBusinessHours;

	@FindBy(css = ".location-working-hours nav.lg-tabs__nav div")
	private List<WebElement> tabsWhenEditBusinessHours;

	@FindBy(xpath = "//body/div[1]//input-field/ng-form")
	private List<WebElement> daysCheckBoxes;

	@FindBy(className = "col-sm-4")
	private List<WebElement> daysOfDayParts;

	@FindBy(className = "row-fx")
	private List<WebElement> rowsOfDayParts;

	@Override
	public void disableAllDayparts() throws Exception {
		if (areListElementVisible(dayPartsList,10)) {
			click(manageDaypartsBtn);
			if (areListElementVisible(allCheckBoxes,10)) {
				for (int i = 0; i < allCheckBoxes.size(); i++) {
					if (allCheckBoxes.get(i).findElement(By.tagName("input")).getAttribute("class").contains("ng-not-empty"))
						click(allCheckBoxes.get(i));
				}
				if (checkedBoxes.size() == 0 )
					SimpleUtils.pass("Operation Hours: Day parts have been disabled");
				else
					SimpleUtils.fail("Operation Hours: Day parts have been not disabled",false);
				scrollToBottom();
				clickTheElement(saveBtnInManageDayparts);
			} else
				SimpleUtils.fail("Operation Hours: Checked boxes failed to load in day parts",false);
		} else if (isElementLoaded(selectDapartsBtn,10))
			SimpleUtils.pass("Operation Hours: No Dayparts Available");
		else
			SimpleUtils.fail("Operation Hours: Day parts failed to load",false);
	}

	@Override
	public void selectDaypart(String dayPart) throws Exception {
		boolean isDayPartPresent = false;
		if (isElementEnabled(selectDapartsBtn, 10)) {
			clickTheElement(selectDapartsBtn);
			if (areListElementVisible(rowsInManageDayparts, 10)) {
				for (int i = 0; i < rowsInManageDayparts.size(); i++) {
					WebElement daypartName = rowsInManageDayparts.get(i).findElement(By.xpath("./td[3]//span"));
					WebElement daypartCheckBox = rowsInManageDayparts.get(i).findElement(By.xpath("./td[1]//input"));
					if (daypartName.getText().equals(dayPart)) {
						if (daypartCheckBox.getAttribute("class").contains("ng-empty"))
							click(daypartCheckBox);
						break;
					}
				}
				scrollToBottom();
				clickTheElement(saveBtnInManageDayparts);
			} else
				SimpleUtils.fail("Operation Hours: Rows failed to load in manage day parts", false);
		}
		if (areListElementVisible(dayPartsList, 10)) {
			for (WebElement row: dayPartsList) {
				WebElement daypartName = row.findElement(By.xpath("./td[1]"));
				if (daypartName.getText().equals(dayPart)) {
					isDayPartPresent = true;
					break;
				}
			}
			if (!isDayPartPresent) {
				click(manageDaypartsBtn);
				if (areListElementVisible(rowsInManageDayparts, 10)) {
					for (int i = 0; i < rowsInManageDayparts.size(); i++) {
						WebElement daypartName = rowsInManageDayparts.get(i).findElement(By.xpath("./td[3]//span"));
						WebElement daypartCheckBox = rowsInManageDayparts.get(i).findElement(By.xpath("./td[1]//input"));
						if (daypartName.getText().equals(dayPart)) {
							if (daypartCheckBox.getAttribute("class").contains("ng-empty"))
								click(daypartCheckBox);
							break;
						}
					}
					scrollToBottom();
					clickTheElement(saveBtnInManageDayparts);
				} else
					SimpleUtils.fail("Operation Hours: Rows failed to load in manage day parts", false);
				waitForSeconds(3);
				for (WebElement row: dayPartsList) {
					WebElement daypartName = row.findElement(By.xpath("./td[1]"));
					if (daypartName.getText().equals(dayPart)) {
						isDayPartPresent = true;
						break;
					}
				}
			}
		} else
			SimpleUtils.fail("Operation Hours: Day parts failed to load", false);
		if (isDayPartPresent)
			SimpleUtils.pass("Operation Hours: Operation Hours: '" + dayPart + "' is selected successfully");
		else
			SimpleUtils.fail("Operation Hours: Operation Hours: '" + dayPart + "' doesn't exist", false);
	}

	@Override
	public void setDaypart(String day, String dayPart, String startTime, String endTime) throws Exception {
		// Please set the start time and end time's format like 11am, 2pm
		String daypartColor = "";
		List<WebElement> hourCells = null;
		WebElement colorInRow = null;
		if (areListElementVisible(daysOfDayParts,10)) {
			for (int h = 0; h < daysOfDayParts.size(); h++) {
				if (day.equals("All days")) {
					clickTheElement(editBtnsOfBusinessHours.get(h));
					break;
				}
				if (daysOfDayParts.get(h).getText().toUpperCase().equals(day.toUpperCase())) {
					clickTheElement(editBtnsOfBusinessHours.get(h));
					break;
				}
			}
		}
		int l=0;
		int k = 0;
		if (areListElementVisible(tabsWhenEditBusinessHours,10))
			click(tabsWhenEditBusinessHours.get(1));
		int j = 0;
		int i = 0;
		if (areListElementVisible(itemsOfDaypart, 10)) {
			for (WebElement item : itemsOfDaypart) {
				WebElement itemName = item.findElement(By.className("ng-binding"));
				if (itemName.getText().equals(dayPart)) {
					WebElement itemColor = item.findElement(By.className("icon"));
					daypartColor = itemColor.getAttribute("style");
					break;
				}
			}
		} else
			SimpleUtils.fail("Operation Hours: Daypart legend failed to load when editing daypart", false);
		if (areListElementVisible(rowsWhenSetDaypart, 10)) {
			for (i = 0; i < rowsWhenSetDaypart.size(); i++) {
				try {
					colorInRow = rowsWhenSetDaypart.get(i).findElement(By.cssSelector("day-part-color>div>div"));
				} catch (Exception e) {
					continue;
				}
				String color = colorInRow.getAttribute("style");
				if (color.contains(daypartColor)) {
					hourCells = rowsWhenSetDaypart.get(i).findElements(By.cssSelector(".availability-box div"));
					break;
				}
			}
		} else
			SimpleUtils.fail("Operation Hours: Daypart rows failed to load when editing daypart",false);
		if (areListElementVisible(timeRangeWhenSetDaypart, 10)) {
			for (int m = 0; m < timeRangeWhenSetDaypart.size(); m++) {
				String hourRange = timeRangeWhenSetDaypart.get(m).getText().trim().replace("\n","");
				if (hourRange.contains("12PM")) {
					j = m;
					break;
				}
			}
			if (startTime.contains("am")) {
				for (k = 0; k < j; k++) {
					String hourRange = timeRangeWhenSetDaypart.get(k).getText().trim().replace("\n","");
					if (hourRange.equals(startTime.replace("am",""))) {
						break;
					}
				}
			} else if (startTime.contains("pm")) {
				for (k = j; k < timeRangeWhenSetDaypart.size(); k++) {
					String hourRange = timeRangeWhenSetDaypart.get(k).getText().trim().replace("\n","");
					if (hourRange.equals(startTime.replace("pm",""))) {
						break;
					}
				}
			}
			if (endTime.contains("am")) {
				for (l = 0; l < j; l++) {
					String hourRange = timeRangeWhenSetDaypart.get(l).getText().trim().replace("\n","");
					if (hourRange.equals(endTime.replace("am",""))) {
						break;
					}
				}
			} else if (endTime.contains("pm")) {
				for (l = j; l < timeRangeWhenSetDaypart.size(); l++) {
					String hourRange = timeRangeWhenSetDaypart.get(l).getText().trim().replace("\n","");
					if (hourRange.equals(endTime.replace("pm",""))) {
						break;
					}
				}
			}
			mouseHoverDragandDrop(hourCells.get(k),hourCells.get(l-1));
			if (day.equals("All days"))
				click(daysCheckBoxes.get(daysCheckBoxes.size() - 1));
			WebElement dayPartMap = rowsWhenSetDaypart.get(i).findElement(By.cssSelector("[ng-if=\"$ctrl.daypartMap[daypart.objectId]\"] span"));
			if (dayPartMap.getText().equals(startTime + " - " + endTime))
			    SimpleUtils.pass("Operation Hours: Day Part with '" + startTime + " - " + endTime + "' has been set");
			else
				SimpleUtils.fail("Operation Hours: Actual Day Part is '" + dayPartMap.getText() + "', expected Day Part is '" + startTime + " - " + endTime + "'",false);
			click(saveBtnInManageDayparts);
		} else
			SimpleUtils.fail("Operation Hours: Daypart rows failed to load when editing daypart",false);
	}

	@Override
	public HashMap<String, List<String>> getDayPartsDataFromBusinessHours() throws Exception {
		HashMap<String, List<String>> dataFromBusinessHours = new HashMap<>();
		List<String> nameColorDuration = new ArrayList<>();
		if (areListElementVisible(rowsOfDayParts, 10)) {
			for (WebElement row: rowsOfDayParts) {
				WebElement day = row.findElement(By.className("col-sm-4"));
				List<WebElement> progressBars = row.findElements(By.className("progress-bar"));
				for (WebElement bar: progressBars) {
					click(bar);
					String progress = bar.getAttribute("innerHTML");
					System.out.println(progress);
					String tool = bar.getAttribute("data-tootik");
					System.out.println(tool);

					nameColorDuration.add(progress);
				}
				dataFromBusinessHours.put(day.getText(),nameColorDuration);
			}
		} else
			SimpleUtils.fail("Operation Hours: Business Hours rows failed to load ",false);
		return dataFromBusinessHours;
	}

	@FindBy(css=".console-navigation-item-label.User.Management")
	private WebElement userManagementTab;
	@Override
	public void goToUserManagementPage() throws Exception {
		if (isElementEnabled(userManagementTab,10)) {
			click(userManagementTab);
			waitForSeconds(20);
			if(categoryOfTemplateList.size()!=0){
				SimpleUtils.pass("User can click user management tab successfully");
			}else{
				SimpleUtils.fail("User can't click user management tab",false);
			}
		}else
			SimpleUtils.fail("User management tab load failed",false);
	}

	@FindBy(css = "question-input[question-title=\"Is approval required by Manager when an employee claims an Open Shift in a home location?\"] yes-no")
	private WebElement approveShiftInHomeLocationSetting;

	@FindBy(css = "question-input[question-title=\"Is approval required by Manager and non-home Manager when an employee claims an Open Shift in a non-home location?\"] yes-no")
	private WebElement approveShiftInNonHomeLocationSetting;

	@Override
	public void enableOrDisableApproveShiftInHomeLocationSetting(String yesOrNo) throws Exception {
		if (isElementLoaded(approveShiftInHomeLocationSetting,10)){
			scrollToElement(approveShiftInHomeLocationSetting);
			if (yesOrNo.equalsIgnoreCase("yes")){
				if (isElementLoaded(approveShiftInHomeLocationSetting.findElement(By.cssSelector(".lg-button-group-first")),10)){
					click(approveShiftInHomeLocationSetting.findElement(By.cssSelector(".lg-button-group-first")));
					SimpleUtils.pass("Turned on 'Is approval required by Manager when an employee claims an Open Shift in a home location?!' setting successfully! ");
				} else {
					SimpleUtils.fail("Yes button fail to load!", false);
				}
			} else if (yesOrNo.equalsIgnoreCase("no")){
				if (isElementLoaded(approveShiftInHomeLocationSetting.findElement(By.cssSelector(".lg-button-group-last")),10)){
					click(approveShiftInHomeLocationSetting.findElement(By.cssSelector(".lg-button-group-last")));
					SimpleUtils.pass("Turned off 'Is approval required by Manager when an employee claims an Open Shift in a home location?!' setting successfully! ");
				} else {
					SimpleUtils.fail("No button fail to load!", false);
				}
			} else {
				SimpleUtils.warn("You have to input the right command: yes or no");
			}
		} else {
			SimpleUtils.fail("'Is approval required by Manager when an employee claims an Open Shift in a home location?!' setting is not loaded!", false);
		}
	}


	@Override
	public void enableOrDisableApproveShiftInNonHomeLocationSetting(String yesOrNo) throws Exception {
		if (isElementLoaded(approveShiftInNonHomeLocationSetting,10)){
			scrollToElement(approveShiftInNonHomeLocationSetting);
			if (yesOrNo.equalsIgnoreCase("yes")){
				if (isElementLoaded(approveShiftInNonHomeLocationSetting.findElement(By.cssSelector(".lg-button-group-first")),10)){
					click(approveShiftInNonHomeLocationSetting.findElement(By.cssSelector(".lg-button-group-first")));
					SimpleUtils.pass("Turned on 'Is approval required by Manager and non-home Manager when an employee claims an Open Shift in a non-home location?' setting successfully! ");
				} else {
					SimpleUtils.fail("Yes button fail to load!", false);
				}
			} else if (yesOrNo.equalsIgnoreCase("no")){
				if (isElementLoaded(approveShiftInNonHomeLocationSetting.findElement(By.cssSelector(".lg-button-group-last")),10)){
					click(approveShiftInNonHomeLocationSetting.findElement(By.cssSelector(".lg-button-group-last")));
					SimpleUtils.pass("Turned off 'Is approval required by Manager and non-home Manager when an employee claims an Open Shift in a non-home location?' setting successfully! ");
				} else {
					SimpleUtils.fail("No button fail to load!", false);
				}
			} else {
				SimpleUtils.warn("You have to input the right command: yes or no");
			}
		} else {
			SimpleUtils.fail("'Is approval required by Manager and non-home Manager when an employee claims an Open Shift in a non-home location?' setting is not loaded!", false);
		}
	}
	
	@FindBy(css = "tbody[ng-repeat=\"workRole in $ctrl.sortedRows\"]>tr>td:nth-child(2)>lg-button>button[type='button']")
	private List<WebElement> staffingRulesForWorkRoleInSchedulingRoles;
	@Override
	public void goToWorkRolesWithStaffingRules() {
		if (areListElementVisible(staffingRulesForWorkRoleInSchedulingRoles,5)) {
			for (WebElement s:staffingRulesForWorkRoleInSchedulingRoles) {
				if (!s.getText().contains("Add")) {
					click(s);
					break;
				}
			}
		}else
			SimpleUtils.fail("staffing rules link show wrong for each work role",false);

	}

	@Override
	public void validateAdvanceStaffingRuleShowingAtLocationLevel(String startEvent,String startOffsetTime,String startEventPoint,String startTimeUnit,
												   String endEvent,String endOffsetTime,String endEventPoint,String endTimeUnit,
												   List<String> days,String shiftsNumber) throws Exception{
		List<WebElement> staffingRuleText = staffingRulesList.get(staffingRulesList.size()-1).findElements(By.cssSelector("span[ng-bind-html=\"$ctrl.ruleLabelText\"] span"));
		List<String> daysInRule = Arrays.asList(staffingRuleText.get(2).getText().trim().split(","));
		List<String> newDaysInRule = new ArrayList<>();
		for(String dayInRules:daysInRule){
			String newDayInRule = dayInRules.trim().toLowerCase();
			newDaysInRule.add(newDayInRule);
		}
		List<String> newDays = new ArrayList<>();
		for(String day:days){
			String newDay = day.substring(0,3).toLowerCase();
			newDays.add(newDay);
		}
		Collections.sort(newDaysInRule);
		Collections.sort(newDays);
		if(ListUtils.isEqualList(newDaysInRule,newDays)){
			SimpleUtils.pass("The days info in rule are correct");
		}else{
			SimpleUtils.fail("The days info in rule are NOT correct",false);
		}
		String shiftsNumberInRule = staffingRuleText.get(1).getText().trim();
		if(shiftsNumberInRule.equals(shiftsNumber)){
			SimpleUtils.pass("The shifts number info in rule is correct");
		}else {
			SimpleUtils.fail("The shifts number info in rule is NOT correct",false);
		}

		String startTimeInfo = staffingRuleText.get(0).getText().trim().split(",")[0];
		String endTimeInfo = staffingRuleText.get(0).getText().trim().split(",")[1];
		String startEventInRule = "";
		for(int i = 5;i<startTimeInfo.split(" ").length;i++){
			startEventInRule = startEventInRule + startTimeInfo.split(" ")[i] + " ";
		}
		startEventInRule.trim();
		if(startEventInRule.contains(startEvent)){
			SimpleUtils.pass("The start event info in rule is correct");
		}else {
			SimpleUtils.fail("The start event info in rule is NOT correct",false);
		}
		String startOffsetTimeInRule = startTimeInfo.split(" ")[2].trim();
		if(startOffsetTimeInRule.equals(startOffsetTime)){
			SimpleUtils.pass("The start Offset Time info in rule is correct");
		}else {
			SimpleUtils.fail("The start Offset Time info in rule is NOT correct",false);
		}
		String startEventPointInRule = startTimeInfo.split(" ")[4].trim();
		if(startEventPointInRule.equals(startEventPoint)){
			SimpleUtils.pass("The start Event Point info in rule is correct");
		}else {
			SimpleUtils.fail("The start Event Point info in rule is NOT correct",false);
		}
		String startTimeUnitInRule = startTimeInfo.split(" ")[3].trim();
		if(startTimeUnitInRule.equals(startTimeUnit)){
			SimpleUtils.pass("The start Time Unit info in rule is correct");
		}else {
			SimpleUtils.fail("The start Time Unit info in rule is NOT correct",false);
		}
		String endEventInRule = "";
		for(int i = 5;i<endTimeInfo.split(" ").length;i++){
			endEventInRule = endEventInRule + endTimeInfo.split(" ")[i] + " ";
		}
		endEventInRule.trim();
		if(endEventInRule.contains(endEvent)){
			SimpleUtils.pass("The end Event info in rule is correct");
		}else {
			SimpleUtils.fail("The end Event info in rule is NOT correct",false);
		}
		String endOffsetTimeInRule = endTimeInfo.split(" ")[3].trim();
		if(endOffsetTimeInRule.contains(endOffsetTime)){
			SimpleUtils.pass("The end Offset Time in rule is correct");
		}else {
			SimpleUtils.fail("The end Offset Time in rule is NOT correct",false);
		}
		String endEventPointInRule = endTimeInfo.split(" ")[5].trim();
		if(endEventPointInRule.contains(endEventPoint)){
			SimpleUtils.pass("The end Event Point in rule is correct");
		}else {
			SimpleUtils.fail("The end Event Point in rule is NOT correct",false);
		}
		String endTimeUnitInRule = endTimeInfo.split(" ")[4].trim();
		if(endTimeUnitInRule.contains(endTimeUnit)){
			SimpleUtils.pass("The end Time Unit in rule is correct");
		}else {
			SimpleUtils.fail("The end Time Unit in rule is NOT correct",false);
		}
	}

	@FindBy(css = "lg-search input")
	private WebElement searchAssociateFiled;
	@FindBy(css="lg-tabs.ng-isolate-scope nav div:nth-child(1)")
	private WebElement templateDetailsBTN;
	@FindBy(css="lg-tabs.ng-isolate-scope nav div:nth-child(2)")
	private WebElement templateExternalAttributesBTN;
//	@FindBy(css="lg-tabs.ng-isolate-scope nav div:nth-child(3)")
	@FindBy(css="nav.lg-tabs__nav>div:nth-last-child(2)")
	private WebElement templateAssociationBTN;
	@FindBy(css="lg-button[label=\"Remove\"]")
	private WebElement dynamicGroupRemoveBTN;
	@FindBy(css="div[ng-if*=showAction] lg-button[label=\"Edit\"]")
	private WebElement dynamicGroupEditBTN;
	@FindBy(css="modal[modal-title=\"Remove Dynamic Group\"] lg-button[label=\"Remove\"]")
	private WebElement dynamicGroupRemoveBTNOnDialog;


	public void clickOnAssociationTabOnTemplateDetailsPage() throws Exception{
		if(isElementEnabled(templateAssociationBTN,10)){
			scrollToElement(templateAssociationBTN);
			clickTheElement(templateAssociationBTN);
			if(isElementEnabled(searchAssociateFiled,2)){
				SimpleUtils.pass("Click Association Tab successfully!");
			}else {
				SimpleUtils.fail("Failed to Click Association Tab!",false);
			}
		}

	}

	@FindBy(css = "table.templateAssociation_table tr[ng-repeat=\"group in filterdynamicGroups\"]")
	private List<WebElement> templateAssociationRows;

	public boolean searchOneDynamicGroup(String dynamicGroupName) throws Exception{
		boolean dataExist=false;
		clickOnAssociationTabOnTemplateDetailsPage();
		searchAssociateFiled.clear();
		searchAssociateFiled.sendKeys(dynamicGroupName);
		waitForSeconds(5);
		if(templateAssociationRows.size()>0){
			dataExist=true;
			SimpleUtils.pass("User can search out association named: " + dynamicGroupName);
		}else {
			SimpleUtils.report("User can NOT search out association named: " + dynamicGroupName);
		}
		return dataExist;
	}

    @Override
	public void deleteOneDynamicGroup(String dyname) throws Exception{
		if(searchOneDynamicGroup(dyname)) {
			//remove the dynamic group
			clickTheElement(dynamicGroupRemoveBTN);
			waitForSeconds(2);
			clickTheElement(dynamicGroupRemoveBTNOnDialog);
			waitForSeconds(1);
		}
        if(!searchOneDynamicGroup(dyname))
        	SimpleUtils.pass("Dynamic group removed successfully");
        else
        	SimpleUtils.fail("Dynamic group removed failed",false);

	}

	@Override
	public void editADynamicGroup(String dyname) throws Exception{
		if(searchOneDynamicGroup(dyname)) {
			//edit the dynamic group
			clickTheElement(dynamicGroupEditBTN);
			waitForSeconds(2);
			if(isElementLoaded(manageDynamicGroupPopupTitle)){
				SimpleUtils.pass("The edit dynamic group dialog pop up successfully!");
				//cancel
				clickTheElement(okButtonOnManageDynamicGroupPopup.findElement(By.xpath("./preceding-sibling::lg-button/button")));
			}
			else
			   SimpleUtils.fail("The edit dynamic group dialog not pop up!",true);
		}

	}

	@FindBy(css="lg-tab[tab-title=\"Association\"] lg-button[label=\"Save\"] button")
	private WebElement saveBTNOnAssociationPage;

	@Override
	public void selectOneDynamicGroup(String dynamicGroupName) throws Exception{
		searchOneDynamicGroup(dynamicGroupName);
		for(WebElement templateAssociationRow:templateAssociationRows){
			String associationName = templateAssociationRow.findElement(By.cssSelector("td:nth-child(2)")).getText().trim();
			if(associationName.equals(dynamicGroupName)){
				clickTheElement(templateAssociationRow.findElement(By.cssSelector("td:nth-child(1) input")));
				break;
			}
		}
		clickTheElement(saveBTNOnAssociationPage);
		waitForSeconds(10);
	}

	@FindBy(css="div.groupAction lg-button[ng-click=\"$ctrl.addDynamicGroup()\"] button")
	private WebElement addDynamicGroupButton;
	@FindBy(css="div.lg-modal h1.lg-modal__title")
	private WebElement manageDynamicGroupPopupTitle;
	@FindBy(css="input-field[label=\"Group Name\"] input")
	private WebElement dynamicGroupName;
	@FindBy(css="input-field[label=\"Group Name\"] ng-form")
	private WebElement manageDynamicGroupPopupEditTitle;
	@FindBy(css="i.fa-exclamation-circle")
	private WebElement dynamicGroupNameRequiredMsg;
	@FindBy(css="input-field[value=\"$ctrl.dynamicGroup.description\"] input")
	private WebElement dynamicGroupDescription;
	@FindBy(css="input-field[type=\"select\"] select")
	private WebElement dynamicGroupCriteria;
	@FindBy(css="input-field[value=\"$ctrl.displayValue\"]")
	private WebElement dynamicGroupCriteriaValue;
	@FindBy(css="input[placeholder=\"Search \"]")
	private WebElement dynamicGroupCriteriaSearchInput;
	@FindBy(css="input-field[type=\"checkbox\"]")
	private List<WebElement> dynamicGroupCriteriaResults;
	@FindBy(css="div.add-label-button")
	private WebElement dynamicGroupCriteriaAddIcon;
	@FindBy(css="lg-button[label=\"Add More\"]")
	private WebElement dynamicGroupCriteriaAddMoreLink;
	@FindBy(css="i.deleteRule")
	private WebElement dynamicGroupCriteriaAddDelete;
	@FindBy(css="lg-button[label=\"Test\"]")
	private WebElement dynamicGroupTestButton;
	@FindBy(css="span.testInfo")
	private WebElement dynamicGroupTestInfo;
	@FindBy(css="div.CodeMirror textarea")
	private WebElement formulaTextAreaOfDynamicGroup;
	@FindBy(css="lg-button[label=\"OK\"]")
	private WebElement okButtonOnManageDynamicGroupPopup;
	@FindBy(css="lg-button[label=\"Cancel\"]")
	private WebElement cancelButtonOnManageDynamicGroupPopup;
	@Override
	public void createDynamicGroup(String name,String criteria,String formula) throws Exception{
		clickOnAssociationTabOnTemplateDetailsPage();
		clickTheElement(addDynamicGroupButton);
		if(isElementEnabled(manageDynamicGroupPopupTitle,5)){
			SimpleUtils.pass("User click add DynamicGroup button successfully!");
			clickTheElement(dynamicGroupName);
			dynamicGroupName.sendKeys(name);
			waitForSeconds(5);
			selectByVisibleText(dynamicGroupCriteria,criteria);
			waitForSeconds(3);
			formulaTextAreaOfDynamicGroup.sendKeys(Keys.TAB);
			formulaTextAreaOfDynamicGroup.sendKeys(formula);
			clickTheElement(okButtonOnManageDynamicGroupPopup);
			waitForSeconds(3);
		}else {
			SimpleUtils.fail("User failed to clicking add DynamicGroup button!",false);
		}
	}

	@Override
	public void createTmpAndPublishAndArchive(String tempType, String tempName,String dygpname) throws Exception{
		//create a new template
		publishNewTemplate(tempName,dygpname,"Custom","AutoCreatedDynamicTodelete---Foremat Script");
		//check if created successfully
		if(searchTemplate(tempName)) {
				archivePublishedOrDeleteDraftTemplate(tempName,"Archive");
		}
		else
			SimpleUtils.fail("Create and Publish" + tempType + "template failed",true);
	}


    @Override
	public void dynamicGroupDialogUICheck(String name) throws Exception{
		clickOnAssociationTabOnTemplateDetailsPage();
		waitForSeconds(3);
		//check if the dynamic group existing or not
		deleteOneDynamicGroup(name);
		if(isElementLoaded(addDynamicGroupButton,5)){
			SimpleUtils.pass("The "+" icon for adding dynamic group button show as expected");
			clickTheElement(addDynamicGroupButton);
			if(manageDynamicGroupPopupTitle.getText().trim().equalsIgnoreCase("Manage Dynamic Group")){
				SimpleUtils.pass("Dynamic group dialog title show as expected");
				//check the group name is required
				if(dynamicGroupName.getAttribute("required").equals("true")){
					SimpleUtils.pass("Group name is required");
					//input group name
					dynamicGroupName.sendKeys(name);
					//clear group name
					dynamicGroupName.clear();
					//get the required message
					if(isElementLoaded(dynamicGroupNameRequiredMsg)&&dynamicGroupNameRequiredMsg.getText().contains("Group Name is required"))
						SimpleUtils.pass("group name is required message displayed if not input");
					dynamicGroupName.sendKeys(name);
					waitForSeconds(5);
					//check every criteria options is selectable
					clickTheElement(dynamicGroupCriteria);
					waitForSeconds(4);
					String[] criteriaOps={"Config Type","District","Country","State","City","Location Name",
							"Location Id","Location Type","UpperField","Custom"};
					for(String ss:criteriaOps){
						selectByVisibleText(dynamicGroupCriteria,ss);
						waitForSeconds(3);
					}
					//set a criteria
					selectByVisibleText(dynamicGroupCriteria,"Config Type");
					waitForSeconds(3);
					//set up value
					clickTheElement(dynamicGroupCriteriaValue);
					waitForSeconds(2);
					if(areListElementVisible(dynamicGroupCriteriaResults,5)){
						SimpleUtils.pass("The current selected Criteria has value options");
						clickTheElement(dynamicGroupCriteriaResults.get(0));
						waitForSeconds(3);
						clickTheElement(dynamicGroupCriteriaAddIcon);
						//Check the delete icon showed
						if(isElementLoaded(dynamicGroupCriteriaAddDelete)){
							clickTheElement(dynamicGroupCriteriaAddDelete);
							waitForSeconds(2);
							//check no criteria showed after deleted
							if(isElementLoaded(dynamicGroupCriteriaAddDelete))
							  SimpleUtils.fail("The criteria still show after it was deleted!",false);
							//click add more link
							clickTheElement(dynamicGroupCriteriaAddMoreLink);
							waitForSeconds(3);
							//set a criteria
							selectByVisibleText(dynamicGroupCriteria,"Country");
							waitForSeconds(3);
							//set up value
							clickTheElement(dynamicGroupCriteriaValue);
							//input search key words
							dynamicGroupCriteriaSearchInput.sendKeys("United States");
							waitForSeconds(2);
							clickTheElement(dynamicGroupCriteriaResults.get(0));
							waitForSeconds(2);
							clickTheElement(dynamicGroupCriteriaAddIcon);
							//click the test button to chek value
							clickTheElement(dynamicGroupTestButton);
							waitForSeconds(3);
							//get the result
							if(isElementLoaded(dynamicGroupTestInfo)){
								SimpleUtils.pass("Get results for the dynamic group");
								String mappedRes=dynamicGroupTestInfo.getText().split("Location")[0].trim();
								if(Integer.parseInt(mappedRes)>0)
									SimpleUtils.pass("Get mapped location for the dynamic group");
							}
							else
								SimpleUtils.fail("No result get for the dynamic group",true);
							//click save
							clickTheElement(okButtonOnManageDynamicGroupPopup);
							waitForSeconds(3);
						}
						else
						   SimpleUtils.fail("The delete criteria icon is not displayed!", false);
					}
					else
						SimpleUtils.fail("The current selected Criteria has no options can be selected",true);
				}
				else
					SimpleUtils.fail("Group name is not required on UI",true);
			}
			else
				SimpleUtils.fail("Dynamic group dialog title is not show as designed!", true);
		}
		else
			SimpleUtils.fail("The "+" icon for adding dynamic group missing!",false);
	}


	@Override
	public void publishNewTemplate(String templateName,String name,String criteria,String formula) throws Exception{
		LocationSelectorPage locationSelectorPage = new ConsoleLocationSelectorPage();
		if(isTemplateListPageShow()){
			//check if template existing or not
			if(searchTemplate(templateName)) {
				if (templateDraftStatusList.size() > 0)
					//Delete the temp
					archivePublishedOrDeleteDraftTemplate(templateName, "Delete");
				else
//					archive the temp
					archivePublishedOrDeleteDraftTemplate(templateName, "Archive");
			}
			clickTheElement(newTemplateBTN);
			waitForSeconds(1);
			if(isElementEnabled(createNewTemplatePopupWindow)){
				SimpleUtils.pass("User can click new template button successfully!");
				clickTheElement(newTemplateName);
				newTemplateName.sendKeys(templateName);
				clickTheElement(newTemplateDescription);
				newTemplateDescription.sendKeys(templateName);
				clickTheElement(continueBTN);
				waitForSeconds(4);
				if(isElementEnabled(welcomeCloseButton)){
					clickTheElement(welcomeCloseButton);
				}
				//change to association tan
				clickTheElement(templateAssociationBTN);
				waitForSeconds(3);
				if(searchOneDynamicGroup(name)){
					selectOneDynamicGroup(name);
				}
				else{
					createDynamicGroup(name,criteria,formula);
				    selectOneDynamicGroup(name);}
				locationSelectorPage.refreshTheBrowser();
				waitForSeconds(4);
				if(isElementEnabled(taTemplateSpecialField,20)){
					clickTheElement(taTemplateSpecialField.findElement(By.cssSelector("input")));
					taTemplateSpecialField.findElement(By.cssSelector("input")).clear();
					taTemplateSpecialField.findElement(By.cssSelector("input")).sendKeys("5");
				}
				publishNowTemplate();
			}else {
				SimpleUtils.fail("User can't click new template button successfully!",false);
			}
		}
		searchTemplate(templateName);
		String newTemplateName = templateNameList.get(0).getText().trim();
		if(newTemplateName.contains(templateName)){
			SimpleUtils.pass("User can add new template successfully!");
		}else {
			SimpleUtils.fail("User can't add new template successfully",false);
		}
	}

	//added by Estelle to edit operating hours
	@FindBy(className = "lgn-time-slider-notch-selector")
	private List<WebElement> startEndSliderInBusinessHoursPopUp;
	@FindBy(css = "div[ng-repeat=\"notch in notches\"]")
	private List<WebElement> sliderScaleInBusinessHoursPopUp;
	@Override
	public void moveSliderAtSomePoint( int moveCount, String slideType) throws Exception {
		try {
			int startSelectTime = Integer.parseInt(startEndSliderInBusinessHoursPopUp.get(0).getText().trim().split(":")[0]);
			int endSelectTime = Integer.parseInt(startEndSliderInBusinessHoursPopUp.get(1).getText().trim().split(":")[0]) + 12;
			String startSliderTimeText = sliderScaleInBusinessHoursPopUp.get(0).getText().trim();
			int startSliderTime = Integer.parseInt(startSliderTimeText.split("\n")[0]);;

			if (startSliderTime == 12) {
				startSliderTime = 0;
			}else
				startSliderTime =startSliderTime;

			String  endSliderTimeText = sliderScaleInBusinessHoursPopUp.get(96).getText().trim();
			int endSliderTime = Integer.parseInt(endSliderTimeText.split("\n")[0]);
			if (endSliderTimeText.contains("AM")&&endSliderTime>11) {
				endSliderTime = endSliderTime+12;
			}else
				endSliderTime = endSliderTime;


			if(slideType.equalsIgnoreCase("End")){
				if(isElementLoaded(startEndSliderInBusinessHoursPopUp.get(1),5) && endSelectTime<endSliderTime){
					SimpleUtils.pass("Business hours with Sliders loaded on page Successfully for End Point");
					if (endSelectTime<endSliderTime) {
						for(int i= endSelectTime*4+moveCount; i< sliderScaleInBusinessHoursPopUp.size();i++){
							WebElement element = getDriver().findElement(By.cssSelector("div.lgn-time-slider-notch.droppable:nth-child("+(i+2)+")"));
							mouseHoverDragandDrop(startEndSliderInBusinessHoursPopUp.get(1),element);
							break;
						}
					}else if (endSelectTime==endSliderTime){
						for(int i= endSelectTime*4-moveCount; i> sliderScaleInBusinessHoursPopUp.size();i++){
							WebElement element = getDriver().findElement(By.cssSelector("div.lgn-time-slider-notch.droppable:nth-child("+(i+2)+")"));
							mouseHoverDragandDrop(startEndSliderInBusinessHoursPopUp.get(1),element);
							break;
						}
					}


				}else{
					SimpleUtils.fail("Business hours with End Sliders load failed", false);
				}
			}else if(slideType.equalsIgnoreCase("Start")){
				if(isElementLoaded(startEndSliderInBusinessHoursPopUp.get(0),10) && startEndSliderInBusinessHoursPopUp.size()>0){
					SimpleUtils.pass("Business hours with Sliders loaded on page Successfully for Starting point");
					if (startSelectTime>endSliderTime) {
						for(int i= endSelectTime*4-moveCount; i< sliderScaleInBusinessHoursPopUp.size();i++){
							WebElement element = getDriver().findElement(By.cssSelector("div.lgn-time-slider-notch.droppable:nth-child("+(i+2)+")"));
							mouseHoverDragandDrop(startEndSliderInBusinessHoursPopUp.get(1),element);
							break;
						}
					}else if (startSelectTime==endSliderTime){
						for(int i= endSelectTime*4+moveCount; i> sliderScaleInBusinessHoursPopUp.size();i++){
							WebElement element = getDriver().findElement(By.cssSelector("div.lgn-time-slider-notch.droppable:nth-child("+(i+2)+")"));
							mouseHoverDragandDrop(startEndSliderInBusinessHoursPopUp.get(1),element);
							break;
						}
					}
				}else{
					SimpleUtils.fail("Business hours with Start Sliders load failed", false);
				}
			}
		}catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}

	}

	@Override
	public void saveADraftTemplate() throws Exception{
		//click Details to back detail tab
		clickTheElement(templateDetailsBTN);
		waitForSeconds(3);
		if(isElementEnabled(saveAsDraftButton)){
			SimpleUtils.pass("User can click save as draft button!");
			clickTheElement(saveAsDraftButton);
			waitForSeconds(3);
		}
		else
			SimpleUtils.fail("Not stayed at template detail page!",false);

	}

	@Override
	public void archivePublishedOrDeleteDraftTemplate(String templateName, String action) throws Exception {

			if (templatesList.size()>0) {
				SimpleUtils.report("There are :" +templatesList.size()+" found");

				//expand published template with draft version
				if (isItMultipVersion()) {
					expandTemplate();
				}
				clickTheElement(templateNameList.get(0));
				waitForSeconds(5);
				List<WebElement> deleteArchiveBtn = getDriver().findElements(By.cssSelector("button[type=\"button\"]"));
				for (WebElement e: deleteArchiveBtn) {
					if (e.getText().equalsIgnoreCase(action)) {
						click(e);
						break;
					}
				}

				//verify deleting / archive pop up
				if(isElementEnabled(archiveTemplateDialog,3)||isElementLoaded(deleteTemplateDialog,3)){
					clickTheElement(okButton);
					waitForSeconds(5);

					searchTemplate(templateName);
					if (templateNameList.size()==0) {
						SimpleUtils.pass("User has " + action + "  template successfully!");
					}else {
						SimpleUtils.fail("User has " + action + "  template failed!",false);
					}
				} else
					SimpleUtils.fail(action+" template dialog pop up window load failed.",false);
			}else
				SimpleUtils.fail("There are no template that match your criteria",false);
	}

	private boolean isItMultipVersion() {
		String classValue = templatesList.get(0).getAttribute("class");
		if (classValue != null && classValue.contains("hasChildren")) {
			return true;
		} else
			return false;
	}

	public void archiveOrDeleteTemplate(String templateName) throws Exception {
		if (areListElementVisible(templateNameList, 20)
				&& areListElementVisible(templatesList, 20)
				&& templateNameList.size() == templatesList.size()
				&& templateNameList.size()>0) {
			for (int i = 0; i< templateNameList.size(); i++) {
				if (templateNameList.get(i).getText().equalsIgnoreCase(templateName)) {
					String classValue = templatesList.get(i).getAttribute("class");
					if(classValue!=null && classValue.contains("hasChildren")){
						clickTheElement(templateToggleButton);
						waitForSeconds(3);
						clickTheElement(templatesList.get(i).findElements(By.cssSelector("button")).get(0));
						if (isElementLoaded(templateDetailsBTN, 20)) {
							SimpleUtils.pass("Go to template detail page successfully! ");
						} else
							SimpleUtils.fail("Go to template detail page fail! ", false);
					}else{
						clickTheElement(templatesList.get(i).findElement(By.cssSelector("button")));
						if (isElementLoaded(templateDetailsBTN, 20)) {
							SimpleUtils.pass("Go to template detail page successfully! ");
						} else
							SimpleUtils.fail("Go to template detail page fail! ", false);
					}
					if (isElementLoaded(archiveBtn, 10)) {
						clickTheElement(archiveBtn);
						if(isElementEnabled(archiveTemplateDialog,10)){
							clickTheElement(okButton);
							displaySuccessMessage();
						} else
							SimpleUtils.fail("Archive template dialog pop up window load failed.",false);
					} else if (isElementLoaded(deleteTemplateButton, 10)) {
						clickTheElement(deleteTemplateButton);
						if (isElementEnabled(deleteTemplateDialog,10)){
							clickTheElement(okButton);
							displaySuccessMessage();
						} else
							SimpleUtils.fail("Delete template dialog pop up window load failed.",false);
					} else
						SimpleUtils.fail("Archive and delete button fail to load! ", false);
					break;
				}
			}

		} else
			SimpleUtils.report("There is no template in the list! ");
	}

	@FindBy(css ="question-input[question-title=\"Move existing shifts to Open when transfers occur within the Workforce Sharing Group.\"] > div > div.lg-question-input__wrapper > ng-transclude > yes-no > ng-form > lg-button-group >div>div")
	private List<WebElement> yesNoForMoveExistingShiftsToOpenWhenTransfer;
	@Override
	public void setMoveExistingShiftWhenTransfer(String yesOrNo) throws Exception {
		if (areListElementVisible(yesNoForMoveExistingShiftsToOpenWhenTransfer,5)) {
			for (WebElement option : yesNoForMoveExistingShiftsToOpenWhenTransfer) {
				if (option.getText().equalsIgnoreCase(yesOrNo)) {
					click(option);
					break;
				}
			}
			SimpleUtils.pass("Set 'Move existing shifts to Open when transfers occur within the Workforce Sharing Group' to "+ yesOrNo + " successfully! ");
		}else
			SimpleUtils.fail("Set 'Move existing shifts to Open when transfers occur within the Workforce Sharing Group' setting fail to load!  ",false);
	}

	@Override
	public boolean isMoveExistingShiftWhenTransferSettingEnabled() throws Exception {
		boolean isMoveExistingShiftWhenTransferSettingEnabled = false;
		if (areListElementVisible(yesNoForMoveExistingShiftsToOpenWhenTransfer, 5)) {
			if (yesNoForMoveExistingShiftsToOpenWhenTransfer.get(0).getAttribute("class").contains("selected")){
				isMoveExistingShiftWhenTransferSettingEnabled = true;
			}
		}
		return isMoveExistingShiftWhenTransferSettingEnabled;
	}

	@Override
	public void deleteTemplate(String templateName) throws Exception {
		clearSearchTemplateBox();
		if(isTemplateListPageShow()){
			searchTemplate(templateName);
			for(int i=0;i<templateNameList.size();i++){
				if(templateNameList.get(i).getText()!=null && templateNameList.get(i).getText().trim().equals(templateName)){
					String classValue = templatesList.get(i).getAttribute("class");
					if(classValue!=null && classValue.contains("hasChildren")){
						clickTheElement(templatesList.get(i).findElement(By.className("toggle")));
						waitForSeconds(3);
						clickTheElement(templatesList.get(i).findElement(By.cssSelector("tr.child-row.ng-scope button")));
						waitForSeconds(15);
						if(isElementEnabled(templateTitleOnDetailsPage)&&isElementEnabled(closeBTN)&&isElementEnabled(templateDetailsAssociateTab)
								&&isElementEnabled(templateDetailsPageForm)){
							SimpleUtils.pass("User can open " + templateName + " template succseefully");
						}else{
							SimpleUtils.fail("User open " + templateName + " template failed",false);
						}
					}else{
						clickTheElement(templatesList.get(i).findElement(By.cssSelector("button")));
						waitForSeconds(15);
						if(isElementEnabled(templateTitleOnDetailsPage)&&isElementEnabled(closeBTN)&&isElementEnabled(templateDetailsAssociateTab)
								&&isElementEnabled(templateDetailsPageForm)){
							SimpleUtils.pass("User can open " + templateName + " template succseefully");
						}else{
							SimpleUtils.fail("User open " + templateName + " template failed",false);
						}
					}
				}else if(i==templateNameList.size()-1){
					SimpleUtils.fail("Can't find the specify template",false);
				}

				if(isElementEnabled(deleteTemplateButton,3)){
					clickTheElement(deleteTemplateButton);
					if(isElementEnabled(deleteTemplateDialog,3)){
						clickTheElement(okButtonOnDeleteTemplateDialog);
						waitForSeconds(5);
						String firstTemplateName = templateNameList.get(0).getText().trim();
						if(!firstTemplateName.equals(templateName)){
							SimpleUtils.pass("User has deleted new created template successfully!");
							break;
						}else {
							SimpleUtils.fail("User failed to delete new created template!",false);
						}
					}
				}else {
					SimpleUtils.fail("Clicking the template failed.",false);
				}
			}
		}else{
			SimpleUtils.fail("There is No template now",false);
		}
	}

	@Override
	public void clearSearchTemplateBox() throws Exception {
		if(isElementEnabled(searchTemplateInputBox,5)){
			clickTheElement(searchTemplateInputBox);
			searchTemplateInputBox.clear();
			waitForSeconds(2);
		}
	}


	@FindBy(css ="textarea[placeholder=\"http://...\"]")
	private WebElement companyMobilePolicyURL;

	@Override
	public boolean hasCompanyMobilePolicyURLOrNotOnOP () throws Exception {
		boolean hasCompanyMobilePolicyURL = false;
		waitForSeconds(10);
		if (isElementLoaded(companyMobilePolicyURL, 5)){
			String url = companyMobilePolicyURL.getAttribute("value");
			if (!url.equals("") && !url.equals("http://...")){
				hasCompanyMobilePolicyURL = true;
			} else
				SimpleUtils.report("The company mobile policy URL is empty");
		} else
			SimpleUtils.fail("The company mobile policy fail to load! ", false);
		setCompanyPolicy(hasCompanyMobilePolicyURL);
		return hasCompanyMobilePolicyURL;
	}

	@FindBy(css ="lg-global-dynamic-group-table[dynamic-groups=\"newsFeedDg\"]")
	private WebElement dynamicEmployeeGroup;
	@Override
	public void goToDynamicEmployeeGroupPage() {
		if (isElementEnabled(dynamicGroupSection, 20)) {
			clickTheElement(dynamicGroupSection);
			if (isElementEnabled(dynamicEmployeeGroup, 20)) {
				SimpleUtils.pass("Can go to dynamic group page successfully");
			} else
				SimpleUtils.fail("Go to dynamic group page failed", false);
		} else
			SimpleUtils.fail("The dynamic group section fail to load! ", false);
	}

	@FindBy(css = "[ng-click=\"$ctrl.removeDynamicGroup(group.id,'remove')\"]")
	private List<WebElement> deleteIconsDynamicEmployeeGroupList;

	@FindBy(css = "[ng-repeat=\"group in filterdynamicGroups\"]")
	private List<WebElement> groupRowsInDynamicEmployeeGroupList;

	@FindBy(css = "lg-button[label=\"Remove\"]")
	private WebElement removeBtnInRemoveDGPopup;

	@FindBy(css = "lg-search input")
	private WebElement searchDynamicEmployeeGroupsField;
	@Override
	public void deleteAllDynamicEmployeeGroupsInList() throws Exception {
		OpsPortalLocationsPage opsPortalLocationsPage = new OpsPortalLocationsPage();
		if (areListElementVisible(groupRowsInDynamicEmployeeGroupList, 20)&&groupRowsInDynamicEmployeeGroupList.size() > 0) {
			if (areListElementVisible(deleteIconsDynamicEmployeeGroupList, 30)) {
				int i = 0;
				while (deleteIconsDynamicEmployeeGroupList.size()>0 && i< 50) {
					click(deleteIconsDynamicEmployeeGroupList.get(0));
					if (opsPortalLocationsPage.isRemoveDynamicGroupPopUpShowing()) {
						click(removeBtnInRemoveDGPopup);
						displaySuccessMessage();
					} else
						SimpleUtils.fail("loRemove dynamic group page load failed ", false);
				}
			} else
				SimpleUtils.report("There is not dynamic group yet");
		} else
			SimpleUtils.report("There is no groups which selected");


	}


	@Override
	public void deleteSpecifyDynamicEmployeeGroupsInList(String groupName) throws Exception {
		OpsPortalLocationsPage opsPortalLocationsPage = new OpsPortalLocationsPage();
		if (areListElementVisible(groupRowsInDynamicEmployeeGroupList, 20)&&groupRowsInDynamicEmployeeGroupList.size() > 0) {
			if (isElementLoaded(searchDynamicEmployeeGroupsField, 5)) {
				searchDynamicEmployeeGroupsField.clear();
				searchDynamicEmployeeGroupsField.sendKeys(groupName);
				int i = 0;
				while (deleteIconsDynamicEmployeeGroupList.size()>0 && i< 50) {
					click(deleteIconsDynamicEmployeeGroupList.get(0));
					if (opsPortalLocationsPage.isRemoveDynamicGroupPopUpShowing()) {
						click(removeBtnInRemoveDGPopup);
						displaySuccessMessage();
					} else
						SimpleUtils.fail("loRemove dynamic group page load failed ", false);
					searchDynamicEmployeeGroupsField.clear();
					searchDynamicEmployeeGroupsField.sendKeys(groupName);
				}
			} else
				SimpleUtils.fail("Search Dynamic Employee Groups Field fail to load! ", false);
		} else
			SimpleUtils.report("There is no groups in group list! ");
	}

	@FindBy(css = "lg-button[icon=\"'img/legion/add.png'\"]")
	private WebElement addDynamicGroupBtn;
	@FindBy(css = "[label=\"Labels\"] .lg-picker-input ng-form [placeholder=\"Select...\"]")
	private WebElement labelsSelector;
	@FindBy(css = ".item.ng-scope")
	private List<WebElement> labelsItems;
	@FindBy(css = "[class=\"input-form ng-pristine ng-invalid ng-invalid-required ng-valid-pattern ng-valid-maxlength\"] input[placeholder=\"Select...\"]")
	private List<WebElement> subCriteriaSelector;
	@FindBy(css = ".select-list-item")
	private List<WebElement> subCriteriaSelectorItems;
	@FindBy(css = "input[placeholder=\"Search Label\"]")
	private WebElement searchLabelBox;
	@FindBy(css = "div.new-label")
	private WebElement newLabel;
	@FindBy(css = ".lg-search-options__option")
	private List<WebElement> criteriaSelectorItems;
	@FindBy(css = "[class=\"lg-picker-input__wrapper lg-ng-animate\"]")
	private WebElement pickerPopup;

	@Override
	public void createNewDynamicEmployeeGroup(String groupTitle, String description, String groupLabels, List<String> groupCriteria) throws Exception {
		if (isElementLoaded(addDynamicGroupBtn, 15)) {
			clickTheElement(addDynamicGroupBtn);
			if (isManagerDGpopShowWell()) {
				//Send the group title
				groupNameInput.sendKeys(groupTitle);
				//Send the group description
				groupDescriptionInput.sendKeys(description);
				//Select the label
				clickTheElement(labelsSelector);
				waitForSeconds(3);
				if (isElementLoaded(searchLabelBox, 10)) {
					searchLabelBox.clear();
					searchLabelBox.sendKeys(groupLabels);
				} else
					SimpleUtils.fail("Search label box fail to load! ", false);

				if (isElementLoaded(newLabel, 5)) {
					clickTheElement(newLabel);
				} else {
					for (WebElement item: labelsItems) {
						if (item.getText().equalsIgnoreCase(groupLabels)) {
							clickTheElement(item.findElement(By.tagName("input")));
							break;
						}
					}
				}
				//To close the label pick popup
				if (isElementLoaded(pickerPopup, 5)){
					clickTheElement(newLabel);
				}

				//Add more criteria if criteria more than 1
				if (groupCriteria.size()>1) {
					for (int i=0; i< groupCriteria.size()-2; i++) {
						clickTheElement(addMoreBtn);
					}
				}
				waitForSeconds(3);
				//Select criteria and sub-criteria
				if (criteriaSelectors.size() == groupCriteria.size()) {
					for (int i = 0; i< groupCriteria.size(); i++) {
						String criteria = groupCriteria.get(i).split("-")[0];
						String subCriteria = groupCriteria.get(i).split("-")[1];
						//Select criteria
						clickTheElement(criteriaSelectors.get(i));
						if (areListElementVisible(criteriaSelectorItems, 15)) {
							for (WebElement item: criteriaSelectorItems) {
								if (item.getText().equalsIgnoreCase(criteria)){
									clickTheElement(item);
									break;
								}
							}
						} else
							SimpleUtils.fail("Criteria selector items fail to load! ", false);
//						selectByVisibleText(criteriaSelectors.get(i), criteria);
						waitForSeconds(3);
						//Select sub-criteria
						clickTheElement(subCriteriaSelector.get(i));
						for (WebElement item: subCriteriaSelectorItems) {
							if (item.getText().equalsIgnoreCase(subCriteria)) {
								clickTheElement(item.findElement(By.tagName("input")));
								break;
							}
						}
					}
				} else
					SimpleUtils.fail("Criteria selector fail to load! ", false);

				//Click on OK button
				clickTheElement(okButton);
				waitForSeconds(2);
				displaySuccessMessage();
				if (isManagerDGpopShowWell()) {
					SimpleUtils.fail("Fail to save the Dynamic Employee Group! ", false);
				}
			} else
				SimpleUtils.fail("Manage Dynamic Group window load failed", false);
		} else
			SimpleUtils.fail("Add dynamic group button load failed", false);
	}


	@FindBy(css = "modal[modal-title=\"Manage Dynamic Employee Group\"]>div")
	private WebElement managerDGpop;
	@FindBy(css = "input[aria-label=\"Group Name\"]")
	private WebElement groupNameInput;
	@FindBy(css = "input-field[value=\"$ctrl.dynamicGroup.description\"] >ng-form>input")
	private WebElement groupDescriptionInput;

	@FindBy(css = "input[placeholder=\"Select one\"]")
	private List<WebElement> criteriaSelectors;

	@FindBy(css = "lg-button[label=\"Test\"]")
	private WebElement testBtn;

	@FindBy(css = "lg-button[label=\"Add More\"]")
	private WebElement addMoreBtn;

	private boolean isManagerDGpopShowWell() throws Exception {
		if (isElementEnabled(managerDGpop, 15) && isElementEnabled(groupNameInput, 15) &&
				isElementEnabled(groupDescriptionInput, 15)
				&& isElementLoaded(labelsSelector, 15)
				&& areListElementVisible(criteriaSelectors, 15)
				&& isElementEnabled(testBtn, 15) && isElementEnabled(addMoreBtn, 15)) {
			SimpleUtils.pass("Manager Dynamic Group win show well");
			return true;
		} else
			return false;
	}

	private void expandTemplate() {
		clickTheElement(templatesList.get(0).findElement(By.className("toggle")));
		waitForSeconds(3);
	}

	@Override
	public void archiveOrDeleteAllTemplates() throws Exception {
		if(isTemplateListPageShow()){
			SimpleUtils.pass("Labor model template list is showing now");
			if (areListElementVisible(templateNameList, 20) && templatesList.size()>0) {
				int j = 0;
				while (templateNameList.size() > 0 && j <10) {
					String templateName = templateNameList.get(0).getText();
					archiveOrDeleteTemplate(templateName);
					j++;
				}

			}else
				SimpleUtils.fail("There are no template in the list",false);
		}else {
			SimpleUtils.fail("Labor model template list is not loaded well",false);
		}
	}

	@Override
	public void clickOnTemplateDetailTab() throws Exception {
		if (isElementLoaded(templateDetailsBTN, 10)) {
			clickTheElement(templateDetailsBTN);
			waitForSeconds(3);
		} else
			SimpleUtils.fail("The template detail tab fail to load! ", false);
	}

	//added by Estelle for archive template part

	@FindBy(css = "lg-button[label=\"Archive\"]")
	private WebElement archiveBtn;
	@Override
	public void archiveIsClickable() throws Exception {
		if (isElementLoaded(archiveBtn,3)) {
			SimpleUtils.pass("Archive button show well in publish template");
		}else
			SimpleUtils.fail("Archive button load failed ",false);
	}

	@Override
	public void verifyArchivePopUpShowWellOrNot() throws Exception {
		click(archiveBtn);
		waitForSeconds(3);
		if (isElementLoaded(archiveTemplateDialog,3) && isElementLoaded(okButton,3) && isElementLoaded(cancelButton,3)) {
			SimpleUtils.pass("Archive Template dialog load well");
		}else
			SimpleUtils.fail("Archive Template dialog load failed",false);
	}

	@Override
	public void cancelArchiveDeleteWorkWell(String templateName) throws Exception {
		click(cancelButton);
		click(backButton);
		searchTemplate(templateName);
		if ( templateNameList.size()>0) {
			SimpleUtils.pass("Cancel archive template successfully");
		}else
			SimpleUtils.fail("Published template was archived",false);
	}



	@FindBy(css = "[title=\"Minors Rules\"] div")
	private WebElement minorRulesTile;
	@Override
	public void verifyMinorRulesTileIsLoaded() throws Exception {
		if (isElementLoaded(minorRulesTile, 10)) {
			String textOnTile1 = "Scheduling Rules for Minors";
			String textOnTile2 = "Min/Max Hours for school days and non-school days";
			String textOnTile3 = "Min/Max Hours for school weeks and non-school weeks";
			String textOnTile4 = "Meal and Rest break rules for minors";
			String messageOnTile = minorRulesTile.findElement(By.className("lg-dashboard-card__body")).getText();
			if (messageOnTile.contains(textOnTile1)
					&& messageOnTile.contains(textOnTile2)
					&& messageOnTile.contains(textOnTile3)
					&& messageOnTile.contains(textOnTile4)) {
				SimpleUtils.pass("The message on the Minor Rule tile display correctly! ");
			} else
				SimpleUtils.fail("The message on the Minor Rule tile display incorrectly! ", false);
		} else
			SimpleUtils.fail("Minor Rules tile fail to loaded! ", false);
	}


	@FindBy(css = "[form-title=\"Minor Schedule by Week\"]")
	private WebElement minorScheduleByWeekSection;

	@FindBy(css = "[form-title=\"Minor Schedule by Day\"]")
	private WebElement minorScheduleByDaySection;

	@FindBy(css = "[form-title=\"Meal Breaks\"]")
	private WebElement mealBreakSection;

	@FindBy(css = "[form-title=\"Rest Breaks\"]")
	private WebElement restBreakSection;

	@FindBy(css = "[form-title=\"Meal Breaks\"] .lg-question-input__text")
	private List<WebElement> mealBreakTexts;

	@FindBy(css = "[form-title=\"Rest Breaks\"] .lg-question-input__text")
	private List<WebElement> restBreakTexts;

	@FindBy(css = "[form-title=\"Meal Breaks\"] .lg-button-group div")
	private List<WebElement> mealBreakPaidBtns;

	@FindBy(css = "[form-title=\"Rest Breaks\"] .lg-button-group div")
	private List<WebElement> restBreakPaidBtns;

	@FindBy(css = "[form-title=\"Meal Breaks\"] th.ng-binding")
	private List<WebElement> mealBreakTableTitles;

	@FindBy(css = "[form-title=\"Rest Breaks\"] th.ng-binding")
	private List<WebElement> restBreakTableTitles;

	@FindBy(css = "[form-title=\"Meal Breaks\"] [label=\"+ Add\"]")
	private WebElement mealAddBtn;

	@FindBy(css = "[form-title=\"Rest Breaks\"] [label=\"+ Add\"]")
	private WebElement restAddBtn;

	@FindBy(css = "[form-title=\"Meal Breaks\"] input[type=\"number\"]")
	private List<WebElement> mealInputs;

	@FindBy(css = "[form-title=\"Rest Breaks\"] table input[type=\"number\"]")
	private List<WebElement> restInputs;

	@FindBy(css = "[form-title=\"Meal Breaks\"] .delete-action")
	private List<WebElement> mealDeleteBtns;

	@FindBy(css = "[form-title=\"Meal Breaks\"] .delete-action")
	private List<WebElement> restDeleteBtns;

	public boolean checkIfMinorSectionsLoaded () throws Exception {
		boolean ifSectionLoaded = false;
		if (isElementLoaded(minorScheduleByWeekSection, 5)
				&& isElementLoaded(minorScheduleByDaySection, 5)) {
			ifSectionLoaded = true;
			SimpleUtils.pass("The sections display correctly on the minor template page! ");
		} else
			SimpleUtils.report("The sections display incorrectly on the minor template page! ");
		return ifSectionLoaded;
	}

	public void clickOnBackButton () throws Exception {
		if (isElementLoaded(backButton, 5)) {
			clickTheElement(backButton);
			if(isElementEnabled(leaveThisPageButton)){
				clickTheElement(leaveThisPageButton);
				waitForSeconds(2);
			}
			SimpleUtils.pass("Click back button successfully! ");
		} else
			SimpleUtils.fail("Back button fail to loaded! ", false);
	}

	@Override
	public void verifyTheContentOnSpecificCard(String cardName, List<String> content) throws Exception {
		if (areListElementVisible(configurationCards, 5) && configurationCards.size() > 0) {
			for (WebElement card : configurationCards) {
				if (card.findElement(By.tagName("h1")).getText().equalsIgnoreCase(cardName)) {
					WebElement body = card.findElement(By.className("lg-dashboard-card__body"));
					String bodyText = body.getText();
					String [] temp = bodyText.split("\n");
					List<String> actualContent = Arrays.asList(temp);
					if (actualContent.containsAll(content) && content.containsAll(actualContent)) {
						SimpleUtils.pass("The content on card: " + cardName + " is correct!");
					} else {
						SimpleUtils.fail("The content on card: " + cardName + " is incorrect!", false);
					}
					break;
				}
			}
		} else {
			SimpleUtils.fail("Cards failed to load on Configuration page!", false);
		}
	}

	@Override
	public void verifyTheContentOnMealBreaksSection() throws Exception {
		if (isElementLoaded(mealBreakSection, 10)) {
			if (isElementLoaded(mealBreakSection.findElement(By.className("info"))) &&
					mealBreakSection.findElement(By.className("info")).getText().equalsIgnoreCase("Meal Breaks")
			&& areListElementVisible(mealBreakTexts, 5) && mealBreakTexts.size() == 2 &&
			mealBreakTexts.get(0).getText().equalsIgnoreCase("Are meal breaks paid?") && mealBreakTexts.get(1).getText().equalsIgnoreCase("Meal Breaks")
			&& areListElementVisible(mealBreakPaidBtns, 5) && mealBreakPaidBtns.size() == 2) {
				SimpleUtils.pass("Meal breaks content shows correctly");
				if (areListElementVisible(mealBreakTableTitles, 5) && mealBreakTableTitles.size() == 7 &&
				mealBreakTableTitles.get(0).getText().equalsIgnoreCase("Meal Break #") &&
				mealBreakTableTitles.get(1).getText().equalsIgnoreCase("Min Shift Length (inclusive)") &&
				mealBreakTableTitles.get(2).getText().equalsIgnoreCase("Max Shift Length (exclusive)") &&
				mealBreakTableTitles.get(3).getText().equalsIgnoreCase("Duration") &&
				mealBreakTableTitles.get(4).getText().equalsIgnoreCase("Left Offset") &&
				mealBreakTableTitles.get(5).getText().equalsIgnoreCase("Right Offset") &&
				mealBreakTableTitles.get(6).getText().equalsIgnoreCase("Latest Start") && isElementLoaded(mealAddBtn, 5)) {
					SimpleUtils.pass("Meal Break setting table and + Add buttons loaded successfully!");
				} else {
					SimpleUtils.fail("Meal Break setting table and + Add buttons failed to load!", false);
				}
			} else {
				SimpleUtils.fail("Meal breaks content not loaded!", false);
			}
		} else {
			SimpleUtils.fail("Meal Breaks section not loaded on Meal And Rest template!", false);
		}
	}

	@Override
	public void verifyTheContentOnRestBreaksSection() throws Exception {
		if (isElementLoaded(restBreakSection, 5) && isElementLoaded(restBreakSection.findElement(By.className("info"))) &&
				restBreakSection.findElement(By.className("info")).getText().equalsIgnoreCase("Rest Breaks") &&
		areListElementVisible(restBreakTexts, 5) && restBreakTexts.size() == 4 && restBreakTexts.get(0).getText().equals("Are rest breaks paid?")
		&& restBreakTexts.get(1).getText().equals("Maximum allowed minutes between 2 consecutive breaks") && restBreakTexts.get(2).getText().equals("Rest break duration")
		&& restBreakTexts.get(3).getText().equals("Rest Breaks") && areListElementVisible(restBreakPaidBtns, 5) && restBreakPaidBtns.size() == 2
		&& areListElementVisible(restBreakTableTitles, 5) && restBreakTableTitles.size() == 3 && restBreakTableTitles.get(0).getText().equals(
				"Min Shift Length (inclusive)") && restBreakTableTitles.get(1).getText().equals("Max Shift Length (exclusive)") &&
				restBreakTableTitles.get(2).getText().equals("Number of Rest Breaks") && isElementLoaded(restAddBtn, 5)) {
			SimpleUtils.pass("The content on Rest Breaks section is correct!");
		} else {
			SimpleUtils.fail("Rest Breaks section not loaded on Meal And Rest template!", false);
		}
	}

	@Override
	public void selectYesOrNoOnMealOrRest(String mealOrRest, String yesOrNo) throws Exception {
		if (mealOrRest.equalsIgnoreCase("Meal")) {
			if (areListElementVisible(mealBreakPaidBtns, 5) && mealBreakPaidBtns.size() == 2) {
				for (WebElement button : mealBreakPaidBtns) {
					if (button.getText().equalsIgnoreCase(yesOrNo)) {
						clickTheElement(button);
						waitForSeconds(1);
						if (button.getAttribute("class").contains("lg-button-group-selected")) {
							SimpleUtils.report("Click the: " + yesOrNo + " button in Meal Breaks Section successfully");
						} else {
							SimpleUtils.fail("Failed to click the: " + yesOrNo + " button in Meal Breaks Section!", false);
						}
						break;
					}
				}
			}
		} else if (mealOrRest.equalsIgnoreCase("Rest")) {
			if (areListElementVisible(restBreakPaidBtns, 5) && restBreakPaidBtns.size() == 2) {
				for (WebElement button : restBreakPaidBtns) {
					if (button.getText().equalsIgnoreCase(yesOrNo)) {
						clickTheElement(button);
						waitForSeconds(1);
						if (button.getAttribute("class").contains("lg-button-group-selected")) {
							SimpleUtils.report("Click the: " + yesOrNo + " button in Rest Breaks Section successfully");
						} else {
							SimpleUtils.fail("Failed to click the: " + yesOrNo + " button in Rest Breaks Section!", false);
						}
						break;
					}
				}
			}
		} else {
			SimpleUtils.fail("Please send the correct Param: Meal Or Rest!", false);
		}
	}

	@Override
	public void clickOnAddButtonOnMealOrRestSection(String mealOrRest) throws Exception {
		String zero = "0";
		if (mealOrRest.equalsIgnoreCase("Meal")) {
			if (isElementLoaded(mealAddBtn, 5)) {
				clickTheElement(mealAddBtn);
				if (areListElementVisible(mealInputs, 10) && mealInputs.size() == 7) {
					for (WebElement input : mealInputs) {
						if (!input.getAttribute("value").equalsIgnoreCase(zero)) {
							SimpleUtils.fail("The Default value is not zero!", false);
							break;
						}
					}
				} else {
					SimpleUtils.fail("Seven inputs failed to show when clicking + Add button!", false);
				}
			}
		} else if (mealOrRest.equalsIgnoreCase("Rest")) {
			if (isElementLoaded(restAddBtn, 5)) {
				clickTheElement(restAddBtn);
				if (areListElementVisible(restInputs, 10) && restInputs.size() == 3) {
					for (WebElement input : restInputs) {
						if (!input.getAttribute("value").equalsIgnoreCase(zero)) {
							SimpleUtils.fail("The Default value is not zero!", false);
							break;
						}
					}
				} else {
					SimpleUtils.fail("Three inputs failed to show when clicking + Add button!", false);
				}
			}
		} else {
			SimpleUtils.fail("Please send the correct Param: Meal Or Rest!", false);
		}
	}

	@Override
	public void verifyTheFunctionalityOfInputsInMealOrRest(String mealOrRest) throws Exception {
		int number = 10;
		String text = "a";
		if (mealOrRest.equalsIgnoreCase("Meal")) {
			if (areListElementVisible(mealInputs, 5) && mealInputs.size() == 7) {
				clickTheElement(mealInputs.get(0));
				mealInputs.get(0).clear();
				mealInputs.get(0).sendKeys(String.valueOf(number));
				if (mealInputs.get(0).getAttribute("value").equalsIgnoreCase(String.valueOf(number))) {
					SimpleUtils.pass("Meal inputs: number is supported!");
				} else {
					SimpleUtils.fail("Number is not updated successfully!", false);
				}
				mealInputs.get(0).clear();
				mealInputs.get(0).sendKeys(text);
				if (mealInputs.get(0).getAttribute("value").equalsIgnoreCase(String.valueOf(number))) {
					SimpleUtils.fail("Meal inputs: only number is supported!", false);
				} else if (mealInputs.get(0).getAttribute("value").equalsIgnoreCase("#")) {
					SimpleUtils.pass("Meal inputs: only number is supported! If inputing other format, it should show #");
				}
			} else {
				SimpleUtils.fail("Seven inputs failed to show when clicking + Add button!", false);
			}
		} else if (mealOrRest.equalsIgnoreCase("Rest")) {
			if (areListElementVisible(restInputs, 5) && restInputs.size() == 3) {
				clickTheElement(restInputs.get(0));
				mealInputs.get(0).clear();
				restInputs.get(0).sendKeys(String.valueOf(number));
				if (restInputs.get(0).getAttribute("value").equalsIgnoreCase(String.valueOf(number))) {
					SimpleUtils.pass("Rest inputs: number is supported!");
				} else {
					SimpleUtils.fail("Number is not updated successfully!", false);
				}
				restInputs.get(0).clear();
				restInputs.get(0).sendKeys(text);
				if (restInputs.get(0).getAttribute("value").equalsIgnoreCase(String.valueOf(number))) {
					SimpleUtils.fail("Rest inputs: only number is supported!", false);
				} else if (restInputs.get(0).getAttribute("value").equalsIgnoreCase("#")) {
					SimpleUtils.pass("Rest inputs: only number is supported! If inputing other format, it should show #");
				}
			} else {
				SimpleUtils.fail("Three inputs failed to show when clicking + Add button!", false);
			}
		} else {
			SimpleUtils.fail("Please send the correct Param: Meal Or Rest!", false);
		}
	}

	@Override
	public void verifyXbuttonOnMealOrRest(String mealOrRest) throws Exception {
		if (mealOrRest.equalsIgnoreCase("Meal")) {
			if (areListElementVisible(mealDeleteBtns, 5) && mealDeleteBtns.size() > 0) {
				clickTheElement(mealDeleteBtns.get(mealDeleteBtns.size() - 1));
			} else {
				SimpleUtils.fail("X buttons not loaded on Meal Breaks template!", false);
			}
		} else if (mealOrRest.equalsIgnoreCase("Rest")) {
			if (areListElementVisible(restDeleteBtns, 5) && restDeleteBtns.size() > 0) {
				clickTheElement(restDeleteBtns.get(restDeleteBtns.size() - 1));
			} else {
				SimpleUtils.fail("X buttons not loaded on Rest Breaks template!", false);
			}
		} else {
			SimpleUtils.fail("Please send the correct Param: Meal Or Rest!", false);
		}
	}
	@FindBy(css ="[question-title=\"Strictly enforce minor violations?\"] yes-no")
	private WebElement yesNoForStrictlyEnforceMinorViolations;
	@Override
	public void setStrictlyEnforceMinorViolations(String yesOrNo) throws Exception {
		if (isElementLoaded(yesNoForStrictlyEnforceMinorViolations,10)){
			scrollToElement(yesNoForStrictlyEnforceMinorViolations);
			if (yesOrNo.equalsIgnoreCase("yes")){
				if (isElementLoaded(yesNoForStrictlyEnforceMinorViolations.findElement(By.cssSelector(".lg-button-group-first")),10)){
					click(yesNoForStrictlyEnforceMinorViolations.findElement(By.cssSelector(".lg-button-group-first")));
					SimpleUtils.pass("Turned on 'Strictly enforce minor violations?' setting successfully! ");
				} else {
					SimpleUtils.fail("Yes button fail to load!", false);
				}
			} else if (yesOrNo.equalsIgnoreCase("no")){
				if (isElementLoaded(yesNoForStrictlyEnforceMinorViolations.findElement(By.cssSelector(".lg-button-group-last")),10)){
					click(yesNoForStrictlyEnforceMinorViolations.findElement(By.cssSelector(".lg-button-group-last")));
					SimpleUtils.pass("Turned off 'Strictly enforce minor violations?' setting successfully! ");
				} else {
					SimpleUtils.fail("No button fail to load!", false);
				}
			} else {
				SimpleUtils.warn("You have to input the right command: yes or no");
			}
		} else {
			SimpleUtils.fail("'Strictly enforce minor violations?' setting is not loaded!", false);
		}
	}


	@Override
	public boolean isStrictlyEnforceMinorViolationSettingEnabled() throws Exception {
		boolean isStrictlyEnforceMinorViolationSettingEnabled = false;
		if (isElementLoaded(yesNoForStrictlyEnforceMinorViolations, 5)) {
			if (yesNoForStrictlyEnforceMinorViolations.
					findElement(By.cssSelector(".lg-button-group-first")).getAttribute("class").contains("selected")){
				isStrictlyEnforceMinorViolationSettingEnabled = true;
			}
		}
		return isStrictlyEnforceMinorViolationSettingEnabled;
	}

	@FindBy(css = "question-input[question-title=\"Can a manager add another locations' employee in schedule before the employee''s home location has published the schedule?\"] input-field")
	private WebElement canManagerAddAnotherLocationsEmployeeInSchedule;
	@Override
	public void updateCanManagerAddAnotherLocationsEmployeeInScheduleBeforeTheEmployeeHomeLocationHasPublishedTheSchedule(String option) throws Exception {

		WebElement confSelect = canManagerAddAnotherLocationsEmployeeInSchedule.findElement(By.cssSelector("select"));
		if(isElementLoaded(confSelect,5)) {
			selectByVisibleText(confSelect,option);
			displaySuccessMessage();
		} else{
			SimpleUtils.fail("Can a manager add another locations' employee in schedule before the employee's home location has published the schedule? input field not loaded.", false);
		}
	}
}