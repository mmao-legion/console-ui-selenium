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
			SimpleUtils.fail("Template landing page was NOT loaing well",false);
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
}

