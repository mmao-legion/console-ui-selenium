package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.LaborModelPage;
import com.legion.pages.UserManagementPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.legion.pages.ConfigurationPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;


public class OpsPortalLaborModelPage extends BasePage implements LaborModelPage {

	public OpsPortalLaborModelPage() {
		PageFactory.initElements(getDriver(), this);
	}

	// Added by Estelle
	@FindBy(css="[class='console-navigation-item-label Labor Model']")
	private WebElement laborModelTab;
	@FindBy(css="[title='Labor Model']")
	private WebElement laborModelTile;
	@FindBy(css="[title='Labor Model Repository']")
	private WebElement laborModelRepositoryTile;
	@FindBy(css = "lg-button[label=\"New Template\"]")
	private WebElement newTemplateBtn;

	@Override
	public void goToLaborModelTile() throws Exception {
		if (isElementLoaded(laborModelTile,5)) {
			click(laborModelTile);
			waitForSeconds(5);
			if (isElementLoaded(newTemplateBtn,5)) {
				SimpleUtils.pass("Go to labor model tile successfully");
			}else
				SimpleUtils.fail("Failed to labor model tile",false);
		}else
			SimpleUtils.fail("Work Roles Tile load failed",false);
	}
	@FindBy(css="[class=\"lg-table ng-scope\"] tbody")
	private List<WebElement> templatesList;
	@FindBy(css="[class=\"lg-table ng-scope\"] button span.ng-binding")
	private List<WebElement> templateNameList;
	@FindBy(css="span[class=\"lg-paged-search__showing top-right-action-button ng-scope\"] button")
	private WebElement newTemplateBTN;
	@FindBy(css="div.lg-tab-toolbar__search")
	private WebElement searchField;
	@FindBy(css="input[placeholder=\"You can search by template name, status and creator.\"]")
	private WebElement searchTemplateInputBox;
	@FindBy(css="div.lg-page-heading h1")
	private WebElement templateTitleOnDetailsPage;
	@FindBy(css="form[name=\"$ctrl.generalForm\"]")
	private WebElement templateDetailsPageForm;
	@FindBy(css="lg-button[label=\"Close\"]")
	private WebElement closeBTN;
	@FindBy(css="div.lg-tabs nav[class=\"lg-tabs__nav\"]")
	private WebElement templateDetailsAssociateTab;


	public boolean isTemplateListPageShow() throws Exception {
		boolean flag = false;
		if(templatesList.size()!=0 && isElementEnabled(newTemplateBTN, 5) && isElementEnabled(searchField, 5)){
			SimpleUtils.pass("Template landing page shows well");
			flag = true;
		}else{
			SimpleUtils.fail("Template landing page was NOT loaded well or there is no template now",false);
			flag = false;
		}
		return flag;
	}

	public void searchTemplate(String templateName) throws Exception{
		if(isElementEnabled(searchTemplateInputBox,5)){
			clickTheElement(searchTemplateInputBox);
			searchTemplateInputBox.sendKeys(templateName);
			searchTemplateInputBox.sendKeys(Keys.ENTER);
			waitForSeconds(2);
		}
	}

	@Override
	public void clickOnSpecifyTemplateName(String template_name, String editOrViewMode) throws Exception {

		if(isTemplateListPageShow()){
			searchTemplate(template_name);
			for(int i=0;i<templateNameList.size();i++){
				if(templateNameList.get(i).getText()!=null && templateNameList.get(i).getText().trim().equals(template_name)){
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
							SimpleUtils.pass("User can open " + template_name + " template succseefully");
						}else{
							SimpleUtils.fail("User open " + template_name + " template failed",false);
						}
					}else{
						clickTheElement(templatesList.get(i).findElement(By.cssSelector("button")));
						waitForSeconds(15);
						if(isElementEnabled(templateTitleOnDetailsPage)&&isElementEnabled(closeBTN)&&isElementEnabled(templateDetailsAssociateTab)
								&&isElementEnabled(templateDetailsPageForm)){
							SimpleUtils.pass("User can open " + template_name + " template succseefully");
						}else{
							SimpleUtils.fail("User open " + template_name + " template failed",false);
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
	public void clickOnLaborModelTab() throws Exception {
		if(isElementLoaded(laborModelTab,15)){
			click(laborModelTab);
			if (isElementLoaded(laborModelTile,5)) {
				SimpleUtils.pass("Labor model tab is clickable");
			}
		}else
			SimpleUtils.fail("Labor model tab not load",false);
	}

	@FindBy(css="tbody[ng-repeat=\"workRole in $ctrl.template.workRoles\"]")
	private List<WebElement> workRolesInLobarModelInTemplateLevel;
	@Override
	public List<HashMap<String, String>> getLaborModelInTemplateLevel() {
		List<HashMap<String,String>> assignmentRulesInfo = new ArrayList<>();

		if (areListElementVisible(workRolesInLobarModelInTemplateLevel,5)) {
			for (WebElement s: workRolesInLobarModelInTemplateLevel) {
				HashMap<String, String> workRoleInfoInEachRow = new HashMap<>();
				workRoleInfoInEachRow.put("WorkRole Name",s.findElement(By.cssSelector("tr>td:nth-child(1)")).getText().replaceAll(" ",""));
//				workRoleInfoInEachRow.put("Labor Standard",s.findElement(By.cssSelector("tr>td:nth-child(2)")).getText());
				String enableOrDisWorkRoleInTemplateLevel = s.findElement(By.cssSelector("tr>td:nth-child(3)>input-field>ng-form>input")).getAttribute("class");
				if (enableOrDisWorkRoleInTemplateLevel.contains("not-empty")) {
					workRoleInfoInEachRow.put("enableOrDisWorkRoleInTemplateLevel","Yes");
				}else
					workRoleInfoInEachRow.put("enableOrDisWorkRoleInTemplateLevel","No");
				assignmentRulesInfo.add(workRoleInfoInEachRow);
			}
			return assignmentRulesInfo;
		}else
			SimpleUtils.fail("Failed go to labor model in template level ",false);
			return null;
	}

	@FindBy(css="[title='Labor Model'] h1")
	private WebElement laborModelCard;

	@Override
	public void addNewLaborModelTemplate(String templateName) throws Exception {
		ConfigurationPage configurationPage = new OpsPortalConfigurationPage();
		if(isElementEnabled(laborModelTab)){
			clickOnLaborModelTab();
			if(isElementLoaded(laborModelTile,5)){
				SimpleUtils.pass("Labor Model tile is loaded successfully!");
				clickTheElement(laborModelCard);
				waitForSeconds(5);
				if(isTemplateListPageShow()){
					SimpleUtils.pass("Labor model template list is showing now");
					configurationPage.createNewTemplate(templateName);
				}else {
					SimpleUtils.fail("Labor model template list is not loaded well",false);
				}
			}else {
				SimpleUtils.fail("Labor Model tile is not loaded",false);
			}
		}else {
			SimpleUtils.fail("Labor model tab is not loaded",false);
		}
	}

	@Override
	public void deleteDraftLaborModelTemplate(String templateName) throws Exception {
		ConfigurationPage configurationPage = new OpsPortalConfigurationPage();
		if(isElementEnabled(laborModelTab)){
			clickOnLaborModelTab();
			if(isElementLoaded(laborModelTile,5)){
				SimpleUtils.pass("Labor Model tile is loaded successfully!");
				clickTheElement(laborModelCard);
				waitForSeconds(5);
				if(isTemplateListPageShow()){
					SimpleUtils.pass("Labor model template list is showing now");
					searchTemplate(templateName);
					configurationPage.deleteNewCreatedTemplate(templateName);
				}else {
					SimpleUtils.fail("Labor model template list is not loaded well",false);
				}
			}else {
				SimpleUtils.fail("Labor Model tile is not loaded",false);
			}
		}else {
			SimpleUtils.fail("Labor model tab is not loaded",false);
		}
	}

	@Override
	public void publishNewLaborModelTemplate(String templateName,String dynamicGroupName) throws Exception {
		ConfigurationPage configurationPage = new OpsPortalConfigurationPage();
		if(isElementEnabled(laborModelTab)){
			clickOnLaborModelTab();
			if(isElementLoaded(laborModelTile,5)){
				SimpleUtils.pass("Labor Model tile is loaded successfully!");
				clickTheElement(laborModelCard);
				waitForSeconds(5);
				if(isTemplateListPageShow()){
					SimpleUtils.pass("Labor model template list is showing now");
					configurationPage.publishNewTemplate(templateName,dynamicGroupName);
				}else {
					SimpleUtils.fail("Labor model template list is not loaded well",false);
				}
			}else {
				SimpleUtils.fail("Labor Model tile is not loaded",false);
			}
		}else {
			SimpleUtils.fail("Labor model tab is not loaded",false);
		}
	}
//location level work roles
	@FindBy(className="workRoleContainer")
	private List<WebElement> workRolesInLocationLevel;

	@FindBy(css="label > ng-form > input[type=\"checkbox\"]")
	private List<WebElement> checkBoxForEachWorkRolesInLocationLevel;

	@FindBy(css="label.switch> ng-form>span")
	private List<WebElement> checkBoxForEachWorkRolesInLocationLevel2;
	@Override
	public void overriddenLaborModelRuleInLocationLevel(int index) {
		waitForSeconds(5);
		if (workRolesInLocationLevel.size()>0) {
			for (int i = index; i <workRolesInLocationLevel.size() ; i++) {
				if (checkBoxForEachWorkRolesInLocationLevel.get(i).getAttribute("class").contains("not-empty")) {
					disableLaborModelInLocationLevel(index);
				}else
					enableLaborModelInLocationLevel(index);
			}
		}else
			SimpleUtils.report("There is no assignment rule");
	}

	private void enableLaborModelInLocationLevel(int index) {
		if (checkBoxForEachWorkRolesInLocationLevel.size()>0) {
			for (int i = index; i <checkBoxForEachWorkRolesInLocationLevel.size() ; i++) {
				if (checkBoxForEachWorkRolesInLocationLevel.get(i).getAttribute("class").contains("ng-empty")) {
					click(checkBoxForEachWorkRolesInLocationLevel2.get(i));
					if (checkBoxForEachWorkRolesInLocationLevel.get(i).getAttribute("class").contains("ng-not-empty")) {
						SimpleUtils.pass("Enable work role successfully");
					}else
						SimpleUtils.fail("Enable work role failed",false);
					break;
				}
			}
		}else
			SimpleUtils.report("There is no work role");
	}

	private void disableLaborModelInLocationLevel(int index) {
		if (checkBoxForEachWorkRolesInLocationLevel.size()>0) {
			for (int i = index; i <checkBoxForEachWorkRolesInLocationLevel.size() ; i++) {
				if (checkBoxForEachWorkRolesInLocationLevel.get(i).getAttribute("class").contains("ng-not-empty")) {
					click(checkBoxForEachWorkRolesInLocationLevel2.get(i));
					if (checkBoxForEachWorkRolesInLocationLevel.get(i).getAttribute("class").contains("ng-empty")) {
						SimpleUtils.pass("Disable work role successfully");
					}else
						SimpleUtils.fail("Disable work role failed",false);
					break;
				}
			}
		}else
			SimpleUtils.report("There is no work role");
	}

}

