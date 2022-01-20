package com.legion.pages.core.OpsPortal;

import com.legion.pages.BasePage;
import com.legion.pages.OpsPortaPageFactories.SettingsAndAssociationPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class OpsPortalSettingsAndAssociationPage extends BasePage implements SettingsAndAssociationPage {
    public OpsPortalSettingsAndAssociationPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public enum requiredFieldsForLocationGroup {
        ConfigType("Config Type"),
        District("District"),
        Country("Country"),
        State("State"),
        City("City"),
        LocationName("Location Name"),
        LocationId("Location Id"),
        LocationType("Location Type"),
        UpperField("UpperField");
        private final String value;

        requiredFieldsForLocationGroup(final String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    public enum requiredFieldsForEmployeeGroup {
        WorkRole("Work Role"),
        JobTitle("Job Title"),
        Country("Country"),
        State("State"),
        City("City"),
        EmploymentType("Employment Type"),
        EmploymentStatus("Employment Status"),
        Exempt("Exempt"),
        Minor("Minor"),
        Badge("Badge");
        private final String value;

        requiredFieldsForEmployeeGroup(final String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @FindBy(css = ".lg-tabs__nav .lg-tabs__nav-item")
    private List<WebElement> tabsOnTheTemplateListPage;
    @Override
    public void goToTemplateListOrSettings(String tab) throws Exception {
        if (areListElementVisible(tabsOnTheTemplateListPage, 10)){
            if (tab.toLowerCase().contains("setting") && tabsOnTheTemplateListPage.size()==2){
                clickTheElement(tabsOnTheTemplateListPage.get(1));
            } else {
                clickTheElement(tabsOnTheTemplateListPage.get(0));
            }
        } else {
            SimpleUtils.fail("There is no tab showing!", false);
        }
    }

    @FindBy(css = "lg-tab[tab-title=\"Settings\"] .title")
    private WebElement titleForSettingTab;
    @Override
    public void verifyTitleOnTheSettingsPage(String groupType) throws Exception {
        if (isElementLoaded(titleForSettingTab, 10)){
            if (titleForSettingTab.getText().contains("Required fields for Dynamic " + groupType + " Group")){
                SimpleUtils.pass("Title is expected!");
            } else {
                SimpleUtils.fail("Title is not expected! actual is: " + titleForSettingTab.getText(), false);
            }
        } else {
            SimpleUtils.fail("There is no title!", false);
        }
    }

    @FindBy(css = ".templateAssociation_titleText")
    private WebElement titleForAssociationTab;
    @Override
    public void verifyTitleOnTheSAssociationPage(String groupType) throws Exception {
        if (isElementLoaded(titleForAssociationTab, 10)){
            if (titleForAssociationTab.getText().contains("Dynamic " + groupType + " Groups")){
                SimpleUtils.pass("Title is expected!");
            } else {
                SimpleUtils.fail("Title is not expected! actual is: " + titleForSettingTab.getText(), false);
            }
        } else {
            SimpleUtils.fail("There is no title!", false);
        }
    }

    @FindBy(css = "content-box[ng-if*=\"enableDynamicGroupForAssociation \"] lg-button[label=\"Edit\"]")
    private WebElement editDynamicGroupBtn;
    @FindBy(css = "content-box[ng-if*=\"enableDynamicGroupForAssociation \"] lg-button[label=\"Done\"]")
    private WebElement doneDynamicGroupBtn;
    @FindBy(css = "content-box[ng-if*=\"enableDynamicGroupForAssociation \"] lg-button[label=\"Cancel\"]")
    private WebElement cancelDynamicGroupBtn;
    @FindBy(css = "content-box[ng-if*=\"enableDynamicGroupForAssociation \"] lg-button[label=\"Remove\"]")
    private WebElement removeDynamicGroupBtn;

    @Override
    public void clickOnEditBtnForDynamicGroupOnAssociationPage() throws Exception {
        if (isElementLoaded(editDynamicGroupBtn, 10)){
            clickTheElement(editDynamicGroupBtn);
            if (isElementLoaded(doneDynamicGroupBtn, 10)){
                SimpleUtils.pass("Edit mode!");
            } else {
                SimpleUtils.fail("Fail to go into edit mode!", false);
            }
        } else {
            SimpleUtils.fail("Edit dynamic group button fail to load!", false);
        }
    }

    @Override
    public void clickOnRemoveBtnForDynamicGroupOnAssociationPage() throws Exception {
        if (isElementLoaded(removeDynamicGroupBtn, 10)){
            clickTheElement(removeDynamicGroupBtn);
            if (isElementLoaded(addDynamicGroupBtn, 10)){
                SimpleUtils.pass("Deleted!");
            } else {
                SimpleUtils.fail("Fail to delete the group!", false);
            }
        } else {
            SimpleUtils.fail("Remove dynamic group button fail to load!", false);
        }
    }

    @Override
    public void clickOnDoneBtnForDynamicGroupOnAssociationPage() throws Exception {
        if (isElementLoaded(doneDynamicGroupBtn, 10)){
            clickTheElement(doneDynamicGroupBtn);
            if (isElementLoaded(removeDynamicGroupBtn, 10)){
                SimpleUtils.pass("Saved!");
            } else {
                SimpleUtils.fail("Fail to save the group!", false);
            }
        } else {
            SimpleUtils.fail("Save dynamic group button fail to load!", false);
        }
    }

    @FindBy(css = "lg-button[ng-click*=\"addDynamicGroup\"]")
    private WebElement addDynamicGroupBtn;
    @Override
    public void clickOnAddBtnForDynamicGroupOnAssociationPage() throws Exception {
        if (isElementLoaded(addDynamicGroupBtn, 10)){
            clickTheElement(addDynamicGroupBtn);
            SimpleUtils.pass("Create dynamic group button is clicked!");
        } else {
            SimpleUtils.fail("Create dynamic group button is not loaded!", false);
        }
    }

    @FindBy(css = "input[aria-label=\"Group Name\"]")
    private WebElement groupNameInput;
    @Override
    public void inputGroupNameForDynamicGroupOnAssociationPage(String groupName) throws Exception {
        if (isElementLoaded(groupNameInput, 10)){
            groupNameInput.clear();
            groupNameInput.sendKeys(groupName);
        } else {
            SimpleUtils.fail("Create dynamic group button is not loaded!", false);
        }
    }

    @FindBy(css = ".condition_line")
    private List<WebElement> criteriaOnTheAssociationPage;

    @Override
    public void selectAnOptionForCriteria(String criteria, String operator, String option) throws Exception {
        boolean flag1 = false;
        boolean flag2 = false;
        if (areListElementVisible(criteriaOnTheAssociationPage, 10)){
            for (WebElement criteriaLine: criteriaOnTheAssociationPage){
                if (criteriaLine.findElement(By.cssSelector("div[disabled=\"disabled\"]")).getAttribute("innerText").replace("\n", "").trim().equalsIgnoreCase(criteria)){
                    click(criteriaLine.findElement(By.cssSelector("lg-cascade-select[required=\"true\"] lg-select[ng-if*=\"operatorSelect\"] div.lg-select")));
                    List<WebElement> operators = criteriaLine.findElements(By.cssSelector("lg-cascade-select[required=\"true\"] lg-select[ng-if*=\"operatorSelect\"] div.lg-search-options__subLabel"));
                    for (WebElement optionValue: operators){
                        if (operator.equalsIgnoreCase(optionValue.getAttribute("innerText").replace("\n", "").trim())){
                            scrollToElement(optionValue);
                            click(optionValue);
                            flag1 = true;
                            break;
                        }
                    }
                    click(criteriaLine.findElement(By.cssSelector("lg-cascade-select[required=\"true\"] lg-cascade-select lg-multiple-select")));
                    List<WebElement> options = criteriaLine.findElements(By.cssSelector("lg-cascade-select[required=\"true\"] div.select-list-item"));
                    for (WebElement optionValue: options){
                        if (option.equalsIgnoreCase(optionValue.getAttribute("innerText").replace("\n", "").trim())){
                            scrollToElement(optionValue.findElement(By.cssSelector("input-field")));
                            click(optionValue.findElement(By.cssSelector("input-field")));
                            flag2 = true;
                            break;
                        }
                    }
                }
            }
            if (!flag1 && !flag2){
                SimpleUtils.fail("Didn't find the criteria and option you want!", false);
            }
        } else {
            SimpleUtils.fail("There is no criteria on the page!", false);
        }
    }

    @FindBy(css = "i[ng-click*=\"deleteCondition\"]")
    private List<WebElement> deleteCriteriaBtnsOnTheAssociationPage;
    @Override
    public void deleteAllCriteriaOnTheAssociationPageIfExist() throws Exception {
        if (areListElementVisible(deleteCriteriaBtnsOnTheAssociationPage, 10)){
            for (int i = deleteCriteriaBtnsOnTheAssociationPage.size()-1; i>=0; i--){
                clickTheElement(deleteCriteriaBtnsOnTheAssociationPage.get(i));
            }
        }
    }

    @FindBy(css="nav.lg-tabs__nav>div:nth-last-child(2)")
    private WebElement templateAssociationBTN;
    @Override
    public void goToAssociationTabOnTemplateDetailsPage() throws Exception{
        if(isElementEnabled(templateAssociationBTN,10)){
            clickTheElement(templateAssociationBTN);
        }else {
            SimpleUtils.fail("Failed to find Association Tab!",false);
        }
    }
}
