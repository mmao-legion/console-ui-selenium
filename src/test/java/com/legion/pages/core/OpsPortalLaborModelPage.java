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
// added by fiona
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
	public void createNewTemplatePageWithoutSaving(String templateName) throws Exception{
		if(isTemplateListPageShow()){
			clickTheElement(newTemplateBTN);
			waitForSeconds(1);
			if(isElementEnabled(createNewTemplatePopupWindow)){
				SimpleUtils.pass("User can click new template button successfully!");
				clickTheElement(newTemplateName);
				newTemplateName.sendKeys(templateName);
				clickTheElement(newTemplateDescription);
				newTemplateDescription.sendKeys(templateName);
				clickTheElement(continueBTN);
				waitForSeconds(5);
				if(isElementEnabled(welcomeCloseButton)){
					clickTheElement(welcomeCloseButton);
				}
				if(isElementEnabled(taTemplateSpecialField)){
					clickTheElement(taTemplateSpecialField.findElement(By.cssSelector("input")));
					taTemplateSpecialField.findElement(By.cssSelector("input")).clear();
					taTemplateSpecialField.findElement(By.cssSelector("input")).sendKeys("5");
				}
				if(isElementEnabled(saveAsDraftButton)){
					SimpleUtils.pass("User can click continue button successfully!");
					waitForSeconds(5);
				}else {
					SimpleUtils.fail("User can't click continue button successfully!",false);
				}
			}else {
				SimpleUtils.fail("User can't click new template button successfully!",false);
			}
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
	public void publishNewLaborModelTemplate(String templateName,String dynamicGroupName,String dynamicGroupCriteria,String dynamicGroupFormula) throws Exception {
		ConfigurationPage configurationPage = new OpsPortalConfigurationPage();
		if(isElementEnabled(laborModelTab)){
			clickOnLaborModelTab();
			if(isElementLoaded(laborModelTile,5)){
				SimpleUtils.pass("Labor Model tile is loaded successfully!");
				clickTheElement(laborModelCard);
				waitForSeconds(5);
				if(isTemplateListPageShow()){
					SimpleUtils.pass("Labor model template list is showing now");
					configurationPage.publishNewTemplate(templateName,dynamicGroupName,dynamicGroupCriteria,dynamicGroupFormula);
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

	@FindBy(css="lg-button[label=\"Edit\"] button")
	private WebElement editButton;
	@FindBy(css="lg-button[label=\"Add Task\"] button")
	private WebElement addTaskButton;
	@FindBy(css="div.button-container lg-button[label=\"Cancel\"] button")
	private WebElement cancelButton;
	@FindBy(css="lg-button[label=\"Save\"] button")
	private WebElement saveButton;
	@FindBy(css="div[ng-click=\"$ctrl.addAttributeClick()\"]")
	private WebElement addAttributeButton;
	@FindBy(css="tbody tr[ng-if=\"$ctrl.showInput\"]")
	private WebElement newAttributeInputRow;

	@FindBy(css = "div.lg-tabs__nav-item")
	private List<WebElement> subTabs;

	@Override
	public void selectLaborStandardRepositorySubTabByLabel(String label) throws Exception {
		boolean isTabFound = false;
		if (areListElementVisible(subTabs,10) && subTabs.size() > 0) {
			for (WebElement subTab : subTabs) {
				if (subTab.getText().toLowerCase().contains(label.toLowerCase())) {
					click(subTab);
					isTabFound = true;
				}
			}
			if (isTabFound)
				SimpleUtils.pass("Labor Model Page: Labor Standard Repository section- '" + label + "' tab selected successfully.");
			else
				SimpleUtils.fail("Labor Model Page: Labor Standard Repository section - '" + label + "' tab not found.", true);
		} else
			SimpleUtils.fail("Labor Model Page: Labor Standard Repository section - sub tabs not loaded.", false);
	}

	@Override
	public void clickOnEditButton() throws Exception {
		if(isElementEnabled(editButton,5)){
			clickTheElement(editButton);
			if(isElementEnabled(addTaskButton,2)&&isElementEnabled(cancelButton,2)&&isElementEnabled(saveButton,2)){
				SimpleUtils.pass("User click edit button successfully on Labor Standard Repository page");
			}else {
				SimpleUtils.fail("User failed to click edit button on Labor Standard Repository page.",false);
			}
		}
	}

	@Override
	public void clickOnSaveButton() throws Exception {
		if(isElementEnabled(saveButton,5)){
			clickTheElement(saveButton);
			waitForSeconds(5);
		}
	}

	@FindBy(css="div.modal-dialog lg-button[label=\"Yes\"] button")
	private WebElement yesButtonOnCancelDialog;

	@Override
	public void clickOnCancelButton() throws Exception {
		if(isElementEnabled(cancelButton,5)){
			clickTheElement(cancelButton);
			waitForSeconds(5);
			if(isElementEnabled(yesButtonOnCancelDialog,5)){
				SimpleUtils.pass("User can click cancel button successfully!");
				clickTheElement(yesButtonOnCancelDialog);
			}else {
				SimpleUtils.fail("User can click cancel button successfully!",false);
			}
		}
	}


	public void clickOnAddAttributeButton() throws Exception{
		if(isElementEnabled(addAttributeButton,5)){
			clickTheElement(addAttributeButton);
			if(isElementEnabled(newAttributeInputRow,2)){
				SimpleUtils.pass("User click add Attribute button successfully on Labor Standard Repository - External Attributes page");
			}else {
				SimpleUtils.fail("User failed to click add Attribute button on Labor Standard Repository - External Attributes page.",false);
			}
		}
	}

	@FindBy(css="tbody tr[ng-if=\"$ctrl.showInput\"] input-field[label=\"Attribute Name\"] input")
	private WebElement newAttributeNameInputField;
	@FindBy(css="tbody tr[ng-if=\"$ctrl.showInput\"] input-field[label=\"Default Value\"] input")
	private WebElement newAttributeValueInputField;
	@FindBy(css="tbody tr[ng-if=\"$ctrl.showInput\"] input-field[label=\"Description\"] input")
	private WebElement newAttributeDescriptionInputField;
	@FindBy(css="tbody tr[ng-if=\"$ctrl.showInput\"] td.action-buttons i.fa-check-circle")
	private WebElement newAttributeCheckButton;
	@FindBy(css="tbody tr[ng-if=\"$ctrl.showInput\"] td.action-buttons i.fa-times-circle")
	private WebElement newAttributeCrossButton;
	@FindBy(css="table.lg-table tbody.ng-scope")
	private List<WebElement> attributesList;
	@FindBy(css="tbody tr td[ng-if=\"!(type.edit && type.isNew)\"]")
	private List<WebElement> attributesNameList;
	@FindBy(css="tbody tr td[ng-if=\"!(type.edit || !$ctrl.showHeader)\"]")
	private List<WebElement> attributesValueList;
	@FindBy(css="tbody tr td[ng-if=\"$ctrl.modifyDescription && !(type.edit || !$ctrl.showHeader)\"]")
	private List<WebElement> attributesDescriptionList;

	public void inputAllFieldsForNewAttribute(String attributeName,String attributeValue,String attributeDescription) throws Exception{
		if(isElementEnabled(newAttributeInputRow,2)){
			newAttributeNameInputField.sendKeys(attributeName);
			newAttributeValueInputField.sendKeys(attributeValue);
			newAttributeDescriptionInputField.sendKeys(attributeDescription);
		}
	}

	@Override
	public void cancelCreateNewAttribute(String attributeName,String attributeValue,String attributeDescription) throws Exception{
//		Click x button when creating new attribute
		int beforeCreate = attributesList.size();
		inputAllFieldsForNewAttribute(attributeName,attributeValue,attributeDescription);
		clickTheElement(newAttributeCrossButton);
		int afterCreate1 = attributesList.size();
		if(afterCreate1-beforeCreate == 0){
			SimpleUtils.pass("User can click cross button successfully when creating new attributes");
		}else {
			SimpleUtils.fail("User failed to click cross button when creating new attributes",false);
		}
		clickOnAddAttributeButton();
//		Click cancel button when creating new attribute
		inputAllFieldsForNewAttribute(attributeName,attributeValue,attributeDescription);
		clickTheElement(newAttributeCheckButton);
		clickOnCancelButton();
	}

	@Override
	public void createNewAttribute(String attributeName,String attributeValue,String attributeDescription) throws Exception{
		int beforeCreate = attributesList.size();
		//Create new attribute
		inputAllFieldsForNewAttribute(attributeName,attributeValue,attributeDescription);
		clickTheElement(newAttributeCheckButton);

		int afterCreate = attributesList.size();
		if(afterCreate-beforeCreate == 1){
			SimpleUtils.pass("User can click check button successfully when creating new attributes");
		}else {
			SimpleUtils.fail("User failed to click check button when creating new attributes",false);
		}
		clickOnSaveButton();
	}

	@Override
	public boolean isSpecifyAttributeExisting(String attributeName) throws Exception{
		boolean flag = false;
		if(areListElementVisible(attributesList,10)){
			for(WebElement attributesName:attributesNameList){
				String name = attributesName.getText().trim();
				if(name.equals(attributeName)){
					SimpleUtils.pass(attributeName + " is shown in attribute list");
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	@Override
	public void goToLaborStandardRepositoryTile() throws Exception {
		if (isElementLoaded(laborModelRepositoryTile,5)) {
			click(laborModelRepositoryTile);
			waitForSeconds(5);
			if (areListElementVisible(subTabs,5) && isElementEnabled(editButton,5)) {
				SimpleUtils.pass("Go to labor model repository tile successfully");
			}else
				SimpleUtils.fail("Failed to labor model repository tile",false);
		}else
			SimpleUtils.fail("labor model repository tile load failed",false);
	}

	@FindBy(css="tbody tr td.edit-button i.fa-pencil")
	private WebElement pencilButton;
	@FindBy(css="tbody tr td.edit-button i.fa-times")
	private WebElement deleteAttributeButton;
	@FindBy(css="div.lg-modal h1 div")
	private WebElement disableExternalAttributePopupTitle;

	@Override
	public boolean checkDeleteAttributeButtonForEachAttribute() throws Exception {
		boolean flag = true;
		if (areListElementVisible(attributesList, 10)) {
			for (WebElement attribute : attributesList) {
				WebElement deleteBTN = attribute.findElement(By.cssSelector("td:nth-child(4) i.fa-times"));
				if (!isElementEnabled(deleteBTN, 1)) {
					flag = false;
					SimpleUtils.fail("There is no delete button!", false);
				}
			}
		}
		return flag;
	}

	@Override
	public void clickOnDeleteAttributeButton(String attributeName) throws Exception {
		if(areListElementVisible(attributesList,10)){
			for(WebElement attribute:attributesList){
				String name = attribute.findElement(By.cssSelector("td:nth-child(1)")).getText().trim();
				if(name.equals(attributeName)){
					WebElement deleteBTN = attribute.findElement(By.cssSelector("td:nth-child(4) i.fa-times"));
					clickTheElement(deleteBTN);
					waitForSeconds(5);
					String title = disableExternalAttributePopupTitle.getText().trim();
					if(isElementEnabled(disableExternalAttributePopupTitle,3) && title.contains("Disable External Attribute")){
						SimpleUtils.pass("User can click delete attribute successfully!");
					}else{
						SimpleUtils.fail("User failed to click delete attribute!",false);
					}
				}
			}
		}
	}

	@Override
	public List<String> clickOnPencilButtonAndUpdateAttribute(String attributeName,String attributeValueUpdate,String attributeDescriptionUpdate) throws Exception {
		List<String> updateValues = new ArrayList<>();
		if (areListElementVisible(attributesList, 10)) {
			for (WebElement attribute : attributesList) {
				String name = attribute.findElement(By.cssSelector("td:nth-child(1)")).getText().trim();
				if (name.equals(attributeName)) {
					WebElement editBTN = attribute.findElement(By.cssSelector("td:nth-child(4) i.fa-pencil"));
					clickTheElement(editBTN);
					WebElement updateAttributeValueInputField = attribute.findElement(By.cssSelector("td:nth-child(2) input"));
					clickTheElement(updateAttributeValueInputField);
					updateAttributeValueInputField.clear();
					updateAttributeValueInputField.sendKeys(attributeValueUpdate);
					WebElement updateAttributeDescriptionInputField = attribute.findElement(By.cssSelector("td:nth-child(3) input"));
					clickTheElement(updateAttributeDescriptionInputField);
					updateAttributeDescriptionInputField.clear();
					updateAttributeDescriptionInputField.sendKeys(attributeDescriptionUpdate);
					WebElement updateAttributeCheckButton = attribute.findElement(By.cssSelector("td:nth-child(4) i.fa-check-circle"));
					clickTheElement(updateAttributeCheckButton);
					String updateValue = attribute.findElement(By.cssSelector("td:nth-child(2)")).getText().trim();
					String updateDes = attribute.findElement(By.cssSelector("td:nth-child(3)")).getText().trim();
					updateValues.add(updateValue);
					updateValues.add(updateDes);
					clickOnSaveButton();
					break;
				}
			}
		}
		return updateValues;
	}

	@FindBy(css="div.lg-modal lg-button[label=\"Ok\"] button")
	private WebElement okButtonOnDeleteAttributeDialog;

	@FindBy(css="div.lg-modal lg-button[label=\"Cancel\"] button")
	private WebElement cancelButtonOnDeleteAttributeDialog;

	@Override
	public void clickOkBtnOnDeleteAttributeDialog() throws Exception {
		if(isElementEnabled(disableExternalAttributePopupTitle,10)){
			clickTheElement(okButtonOnDeleteAttributeDialog);
			waitForSeconds(1);
		}
	}

	@Override
	public void clickCancelBtnOnDeleteAttributeDialog() throws Exception {
		if(isElementEnabled(disableExternalAttributePopupTitle,10)){
			clickTheElement(cancelButtonOnDeleteAttributeDialog);
			waitForSeconds(1);
		}
	}

	@Override
	public HashMap<String, List<String>> getValueAndDescriptionForEachAttributeAtGlobalLevel() throws Exception{
		HashMap<String, List<String>> infoForEachAttribute = new HashMap<>();
		if(areListElementVisible(attributesList,5)){
			for (WebElement attribute : attributesList) {
				List<String> infos = new ArrayList<>();
				String name = attribute.findElement(By.cssSelector("td:nth-child(1)")).getText().trim();
				String value = attribute.findElement(By.cssSelector("td:nth-child(2)")).getText().trim();
				String des = attribute.findElement(By.cssSelector("td:nth-child(3)")).getText().trim();
				infos.add(0,value);
				infos.add(1,des);
				infoForEachAttribute.put(name,infos);
			}
		}
		return infoForEachAttribute;
	}

	@Override
	public HashMap<String, List<String>> getValueAndDescriptionForEachAttributeAtTemplateLevel() throws Exception{
		HashMap<String, List<String>> infoForEachAttribute = new HashMap<>();
		if(areListElementVisible(attributesList,5)){
			for (WebElement attribute : attributesList) {
				List<String> infos = new ArrayList<>();
				String name = attribute.findElement(By.cssSelector("td:nth-child(1)")).getText().trim();
				String value = attribute.findElement(By.cssSelector("td:nth-child(2) div")).getAttribute("innerText").trim();
				String des = attribute.findElement(By.cssSelector("td:nth-child(3)")).getText().trim();
				infos.add(0,value);
				infos.add(1,des);
				infoForEachAttribute.put(name,infos);
			}
		}
		return infoForEachAttribute;
	}

	@Override
	public void selectLaborModelTemplateDetailsPageSubTabByLabel(String label) throws Exception {
		boolean isTabFound = false;
		if (areListElementVisible(subTabs,10) && subTabs.size() > 0) {
			for (WebElement subTab : subTabs) {
				if (subTab.getText().toLowerCase().contains(label.toLowerCase())) {
					click(subTab);
					isTabFound = true;
				}
			}
			if (isTabFound)
				SimpleUtils.pass("Labor Model Page: Labor Model Details page - '" + label + "' tab selected successfully.");
			else
				SimpleUtils.fail("Labor Model Page: Labor Model Details page - '" + label + "' tab not found.", true);
		} else
			SimpleUtils.fail("Labor Model Page: Labor Model Details page - sub tabs not loaded.", false);
	}

	@Override
	public void archivePublishedOrDeleteDraftTemplate(String templateName, String action) throws Exception {
		ConfigurationPage configurationPage = new OpsPortalConfigurationPage();
		if(isTemplateListPageShow()){
			SimpleUtils.pass("Labor model template list is showing now");
			searchTemplate(templateName);
			if (templatesList.size()>1) {
				SimpleUtils.report("There are :" +templatesList.size()+" found");
				for(int i=0;i<templateNameList.size();i++){
					if (isItMultipVersion(i)) {
						expandTemplate(i);
					}
					configurationPage.archivePublishedOrDeleteDraftTemplate(templateName,action);
					break;

				}
			}else
				SimpleUtils.fail("There are no template that match your criteria",false);


		}else {
			SimpleUtils.fail("Labor model template list is not loaded well",false);
		}
	}

	private boolean isItMultipVersion(int i) {
		String classValue = templatesList.get(i).findElement(By.cssSelector("tr")).getAttribute("class");
		if (classValue != null && classValue.contains("hasChildren")) {
			return true;
		} else
			return false;
	}

	private void expandTemplate(int i) {
		clickTheElement(templatesList.get(i).findElement(By.className("toggle")));
		waitForSeconds(3);
	}

	@FindBy(css="div[class=\"lg-modal\"]")
	private WebElement editTemplatePopupPage;
	@FindBy(css="lg-button[label=\"OK\"]")
	private WebElement okButton;
	@FindBy(css="lg-button[label=\"Save as draft\"] i.fa.fa-sort-down")
	private WebElement dropdownArrowButton;

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

	@Override
	public void updateAttributeValueInTemplate(String attributeName,String attributeValueUpdate) throws Exception {
		if (areListElementVisible(attributesList, 10)) {
			for (WebElement attribute : attributesList) {
				String name = attribute.findElement(By.cssSelector("td:nth-child(1)")).getText().trim();
				if (name.equals(attributeName)) {
					WebElement updateAttributeValueInputField = attribute.findElement(By.cssSelector("td:nth-child(2) input"));
					clickTheElement(updateAttributeValueInputField);
					updateAttributeValueInputField.clear();
					updateAttributeValueInputField.sendKeys(attributeValueUpdate);
					clickOnSaveButton();
					break;
				}
			}
		}
	}
	@FindBy(css="lg-button[label=\"Save as draft\"] button.pre-saveas")
	private WebElement saveAsDraftButton;

	@Override
	public void saveAsDraftTemplate() throws Exception {
		if (isElementEnabled(saveAsDraftButton)) {
			clickTheElement(saveAsDraftButton);
			waitForSeconds(5);
		}
	}

	@FindBy(css="[ng-if=\"$ctrl.saveAsLabel\"] button")
	private WebElement publishNowBTN;
	@FindBy(css="i.fa.fa-sort-down")
	private WebElement dropdownArrowBTN;
	@FindBy(css="lg-button[label=\"Save as draft\"] h3[ng-click*=\"publishNow\"]")
	private WebElement publishNowButton;
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

}

