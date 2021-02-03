package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.ConfigurationPage;
import com.legion.utils.FileDownloadVerify;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		SchedulingRules("Scheduling Rules");
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
				}else{
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
			SimpleUtils.fail("There is No template now",false);
		}
	}

	@Override
	public void clickOnConfigurationCrad(String templateType) throws Exception {
		if(templateType!=null){
			waitForSeconds(30);
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


}