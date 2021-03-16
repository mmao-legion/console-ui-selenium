package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.ConfigurationPage;
import com.legion.utils.FileDownloadVerify;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import cucumber.api.java.an.E;
import org.apache.commons.collections.ListUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.ClickElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.legion.tests.TestBase.flagForTestRun;
import static com.legion.tests.TestBase.propertyMap;
import static com.legion.utils.MyThreadLocal.getDriver;


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

	@FindBy(css="div.card-carousel-card-title")
	private List<WebElement> smartCardsList;

	@FindBy(css="span[class=\"lg-paged-search__showing top-right-action-button ng-scope\"]")
	private WebElement newTemplateBTN;

	@FindBy(css="div.lg-tab-toolbar__search")
	private WebElement searchField;

	@FindBy(css="[class=\"lg-table ng-scope\"] tbody")
	private List<WebElement> templatesList;

	@FindBy(css="[class=\"lg-table ng-scope\"] button span.ng-binding")
	private List<WebElement> templateNameList;

	@FindBy(css="td.toggle i[class=\"fa fa-caret-right\"]")
	private WebElement templateToggleButton;

	@FindBy(css="lg-button[label=\"Edit\"]")
	private WebElement editButton;

	@FindBy(css="div[class=\"lg-modal\"]")
	private WebElement editTemplatePopupPage;

	@FindBy(css="lg-button[label=\"OK\"]")
	private WebElement okButton;

	@FindBy(css="lg-button[label=\"Cancel\"]")
	private WebElement cancelButton;

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

	@FindBy(css="lg-button[label=\"Save as draft\"] h3[ng-click*=\"publishNow\"]")
	private WebElement publishNowButton;

	@FindBy(css="lg-button[label=\"Save as draft\"] h3[ng-click*=\"saveAsDraft\"]")
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

	@FindBy(css="sub-content-box[box-title=\"Dynamic Group\"]")
	private WebElement dynamicGroupSection;

	@FindBy(css="sub-content-box[box-title=\"Time of Day\"]")
	private WebElement timeOfDaySection;

	@FindBy(css="sub-content-box[box-title=\"Meal and Rest Breaks\"]")
	private WebElement mealAndRestBreaksSection;

	@FindBy(css="sub-content-box[box-title=\"Number of Shifts\"]")
	private WebElement numberOfShiftsSection;

	@FindBy(css="sub-content-box[box-title=\"Badges\"]")
	private WebElement badgesSection;

	@FindBy(css="lg-button[label=\"Save\"]")
	private WebElement saveButton;



	public enum configurationLandingPageTemplateCards{
		OperatingHours("Operating Hours"),
		SchedulingPolicies("Scheduling Policies"),
		ScheduleCollaboration("Schedule Collaboration"),
		TimeAttendance("Time & Attendance"),
		Compliance("Compliance"),
		SchedulingRules("Scheduling Rules"),
		Communications("Communications");
		private final String value;

		configurationLandingPageTemplateCards(final String newValue) {
			value = newValue;
		}

		public String getValue() {
			return value;
		}
	}

	@Override
	public void goToConfigurationPage() throws Exception {
		if (isElementEnabled(configurationTab,3)) {
			click(configurationTab);
			waitForSeconds(20);
			if(categoryOfTemplateList.size()!=0){
				checkAllTemplateCards();
				SimpleUtils.pass("User can click configuration tab successfully");
				}else{
				SimpleUtils.fail("User can't click configuration tab",false);
			}
		}else
			SimpleUtils.fail("Configuration tab load failed",false);
	}

	@Override
	public void checkAllTemplateCards() throws Exception {
		if(configurationCardsList.size()!=0){
			for(WebElement configurationCard:configurationCardsList){
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
				}
				else{
					SimpleUtils.fail("Configuration template cards are loaded incorrect",false);
				}
			}
		}else{
			SimpleUtils.fail("Configuration landing page was loading failed",false);
		}
	}

	@Override
	public boolean isTemplateListPageShow() throws Exception {
			if(templatesList.size()!=0 && isElementEnabled(newTemplateBTN) && isElementEnabled(searchField)){
				SimpleUtils.pass("Template landing page shows well");
				return true;
			}else{
				SimpleUtils.fail("Template landing page was NOT loaing well",false);
				return false;
			}
	}

// open the first one template on template list page
	@Override
	public void clickOnTemplateName(String templateType) throws Exception {
		if(isTemplateListPageShow()){
			String classValue = templatesList.get(0).findElement(By.cssSelector("tr")).getAttribute("class");
			if(classValue!=null && classValue.contains("hasChildren")){
				clickTheElement(templateToggleButton);
				waitForSeconds(3);
				clickTheElement(templatesList.get(0).findElement(By.cssSelector("button")));
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
			waitForSeconds(15);
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


//open the specify template to edit or view details
	@Override
	public void clickOnSpecifyTemplateName(String templateName,String editOrViewMode) throws Exception {

		if(isTemplateListPageShow()){
			for(int i=0;i<templateNameList.size();i++){
				if(templateNameList.get(i).getText()!=null && templateNameList.get(i).getText().trim().equals(templateName)){
					String classValue = templatesList.get(i).findElement(By.cssSelector("tr")).getAttribute("class");
					if(classValue!=null && classValue.contains("hasChildren")){
						clickTheElement(templatesList.get(i).findElement(By.className("toggle")));
						waitForSeconds(3);
						if(editOrViewMode!=null && editOrViewMode.toLowerCase().contains("edit")){
							clickTheElement(templatesList.get(i).findElement(By.cssSelector("tr.child-row.ng-scope button")));
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
				}else if(i==templateNameList.size()-1){
					SimpleUtils.fail("Can't find the specify template",false);
				}
			}
		}else{
			SimpleUtils.fail("There is No template now",false);
		}
	}

	@Override
	public void clickOnEditButtonOnTemplateDetailsPage() throws Exception {
		if(isElementEnabled(editButton)){
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

	@FindBy(css="table[ng-if*=\"template.workRoles\"] tbody")
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
				&& isElementEnabled(badgesSection) && isElementEnabled(cancelButton) && isElementEnabled(saveButton)){
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
	@FindBy(css="div[class=\"mt-20\"] input-field[options*=\"timeEventOptions\"]")
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

//below are operation hour element
	@FindBy(css="div.dayparts span.add-circle")
	private WebElement addDayPartsBTNInOH;
	@FindBy(css="modal[modal-title=\"Manage Dayparts\"] div[class*=\"lg-modal__title-icon\"]")
	private WebElement manageDaypartsPageTitle;
	@FindBy(css="div.lg-paged-search div.lg-paged-search__pagination select")
	private WebElement dayPartsPagination;
	@FindBy(css="div.lg-paged-search div.lg-paged-search__pagination select option")
	private List<WebElement> dayPartsPaginationList;
	@FindBy(css="table tr")
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
	private WebElement shiftEndEventPointList;
	@FindBy(css="div.dif.end-shift input-field[options=\"$ctrl.timeEventOptions\"]")
	private WebElement shiftEndTimeEvent;
	@FindBy(css="div[class=\"mt-20 dif\"] div.dif.end-shift input-field[options=\"$ctrl.timeEventOptions\"] select option")
	private WebElement shiftEndTimeEventList;
	@FindBy(css="div.dif.duartion input-field[type=\"number\"]+span")
	private WebElement shiftDuartionMinutesUnit;

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
	public void inputShiftDuartionMinutes(String duringTime) throws Exception{
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
	public void validateShiftDuartionTimeUnit() throws Exception{
		String unit = "minutes";
		if(shiftDuartionMinutesUnit.getText().trim()!=null && shiftDuartionMinutesUnit.getText().equals(unit)){
			SimpleUtils.pass("The shift Duartion Minutes Unit is: " + shiftDuartionMinutesUnit.getText().trim());
		}else{
			SimpleUtils.fail("The The shift Duartion Minutes Unit is not correct.",false);
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
	@FindBy(css="div.badges-edit-wrapper-scroll tbody tr")
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


}