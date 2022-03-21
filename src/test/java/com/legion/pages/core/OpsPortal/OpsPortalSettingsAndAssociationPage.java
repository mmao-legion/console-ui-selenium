package com.legion.pages.core.OpsPortal;

import com.legion.pages.BasePage;
import com.legion.pages.OpsPortaPageFactories.SettingsAndAssociationPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
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

    @FindBy(css = "lg-button[label=\"Remove\"] button")
    private WebElement removeConfirmButton;
    @Override
    public void clickOnRemoveBtnToRemoveDynamicGroupOnAssociationPage() throws Exception {
        if (isElementLoaded(removeDynamicGroupBtn, 10)){
            clickTheElement(removeDynamicGroupBtn);
            if (isElementLoaded(removeConfirmButton, 10)){
                clickTheElement(removeConfirmButton);
                waitForSeconds(1);
            }
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
        } else {
            SimpleUtils.fail("Save dynamic group button fail to load!", false);
        }
    }

    @FindBy(css = ".modal-dialog [modal-title=\"Conflict Detected\"]")
    private WebElement conflictDetectedWindow;
    @FindBy(css = ".modal-dialog [modal-title=\"Conflict Detected\"] [label=\"Save\"]")
    private WebElement saveBtnOnConflictDetectedWindow;
    @Override
    public boolean ifConflictDetectedWindowShowUP() throws Exception {
        if (isElementLoaded(conflictDetectedWindow, 10)){
            return true;
        }
        return false;
    }

    @Override
    public void clickOnTheSaveBtnOnConflictDetectedWindow() throws Exception {
        if (isElementLoaded(saveBtnOnConflictDetectedWindow, 10)){
            clickTheElement(saveBtnOnConflictDetectedWindow);
        } else {
            SimpleUtils.fail("Fail to find Save button!", false);
        }
    }

    @Override
    public boolean isSaveBtnEnabledOnConflictDetectedWindow() throws Exception {
        if (isElementLoaded(saveBtnOnConflictDetectedWindow, 10)){
            return true;
        }
        return false;
    }

    @FindBy(css = "lg-button[ng-click*=\"addDynamicGroup\"]")
    private WebElement addDynamicGroupBtn;
    @Override
    public void clickOnAddBtnForDynamicGroupOnAssociationPage() throws Exception {
        if (isAddGroupBtnEnabled()){
            clickTheElement(addDynamicGroupBtn);
            SimpleUtils.pass("Create dynamic group button is clicked!");
        } else {
            SimpleUtils.fail("Create dynamic group button is not loaded!", false);
        }
    }

    @Override
    public boolean isAddGroupBtnEnabled() throws Exception {
        if (isElementLoaded(addDynamicGroupBtn, 10)){
            return true;
        }
        return false;
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

    @Override
    public List<String> getCriteriaListFromTheAssociationPage() throws Exception {
        List<String> resultList = new ArrayList<>();
        if (areListElementVisible(criteriaOnTheAssociationPage, 10)){
            for (WebElement criteriaLine: criteriaOnTheAssociationPage){
                resultList.add(criteriaLine.findElement(By.cssSelector("lg-select[class=\"ng-scope ng-isolate-scope\"] div.input-faked.ng-binding")).getAttribute("innerText").replace("\n","").trim());
            }
        }
        return resultList;
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

    @FindBy(css = "[ng-repeat*=\"baseFieldList\"]")
    private List<WebElement> fieldListFromSettingsTab;

    @Override
    public List<String> getFieldListFromSettingsTab() throws Exception {
        List<String> resultList = new ArrayList<>();
        if (areListElementVisible(fieldListFromSettingsTab, 10)){
            for (WebElement fieldRow: fieldListFromSettingsTab){
                if (isElementLoaded(fieldRow.findElement(By.cssSelector("td.ng-binding")), 10)){
                    resultList.add(fieldRow.findElement(By.cssSelector("td.ng-binding")).getText());
                }
            }
        } else {
            SimpleUtils.fail("Fail to find fields!", false);
        }
        return resultList;
    }

    @Override
    public void setupRequiredFields(List<String> fields) throws Exception {
        if (areListElementVisible(fieldListFromSettingsTab, 10)){
            clearUpSelectedRequiredFields();
            for (WebElement fieldRow: fieldListFromSettingsTab){
                if (isElementLoaded(fieldRow.findElement(By.cssSelector("td.ng-binding"))) && fields.contains(fieldRow.findElement(By.cssSelector("td.ng-binding")).getText())){
                    if (isElementLoaded(fieldRow.findElement(By.tagName("input")), 10) && fieldRow.findElement(By.tagName("input")).getAttribute("class").contains("ng-empty")){
                        waitForSeconds(2);
                        clickTheElement(fieldRow.findElement(By.tagName("input")));
                    }
                }
            }
            SimpleUtils.assertOnFail("There is fields failed to be selected!", getDriver().findElements(By.cssSelector("lg-template-setting div.setting-box:first-of-type [ng-repeat*=\"baseFieldList\"] input[class*=\"not-empty\"]")).size() == fields.size(), false);
        } else {
            SimpleUtils.fail("Fail to find fields!", false);
        }
    }

    private void clearUpSelectedRequiredFields() throws Exception{
        if (areListElementVisible(fieldListFromSettingsTab, 10)){
            for (WebElement fieldRow: fieldListFromSettingsTab){
                if (isElementLoaded(fieldRow.findElement(By.tagName("input")), 10)
                    && fieldRow.findElement(By.tagName("input")).getAttribute("class").contains("not-empty")){
                    waitForSeconds(2);
                    clickTheElement(fieldRow.findElement(By.tagName("input")));
                }
            }
        } else {
            SimpleUtils.fail("Fail to find fields!", false);
        }
    }

    @FindBy(css = "lg-button[label=\"Test\"] button")
    private WebElement testButton;
    @FindBy(css = "[ng-if*=\"testMappingNum\"]")
    private WebElement testMappingResult;
    @Override
    public String clickOnTestBtnAndGetResultString() throws Exception {
        if (isElementLoaded(testButton, 10)){
            clickTheElement(testButton);
            if (isElementLoaded(testMappingResult, 10)){
                return testMappingResult.getText();
            }
        } else {
            SimpleUtils.fail("Test button is not on the page!", false);
        }
        return null;
    }

    @Override
    public boolean areFieldsCheckInputEnabled() throws Exception {
        if (areListElementVisible(fieldListFromSettingsTab, 10)){
            for (WebElement fieldRow: fieldListFromSettingsTab){
                if (!isElementLoaded(fieldRow.findElement(By.cssSelector("input[disabled]")), 10)){
                    return true;
                }
            }
        } else {
            SimpleUtils.fail("Fail to find fields!", false);
        }
        return false;
    }

    @Override
    public void verifyConflictDetectionInfo() throws Exception {
        if (isElementLoaded(conflictDetectedWindow.findElement(By.cssSelector(".lg-modal__title-icon")), 10)
                && conflictDetectedWindow.findElement(By.cssSelector(".lg-modal__title-icon")).getAttribute("innerText").equalsIgnoreCase("conflict detected")
                && conflictDetectedWindow.findElement(By.cssSelector(".dynamic-group-conflict-title")).getAttribute("innerText").trim().contains("The Dynamic Group is conflicting with the dynamic groups below")
                && conflictDetectedWindow.findElements(By.cssSelector(".dynamic-group-conflict-list")).size() > 0){
            SimpleUtils.pass("Title and text and content are expected!");
        } else {
            SimpleUtils.fail("Title and text and content are not expected!", false);
        }
    }

    @Override
    public void clickOnButtonOnTheConflictDetectedWindow(String cancelOrSave) throws Exception {
        if (cancelOrSave.toLowerCase().contains("save") && isElementLoaded(conflictDetectedWindow.findElement(By.cssSelector("lg-button[label=\"Save\"] button")))){
            clickTheElement(conflictDetectedWindow.findElement(By.cssSelector("lg-button[label=\"Save\"] button")));
            SimpleUtils.pass("Save button is clicked!");
        } else if (cancelOrSave.toLowerCase().contains("cancel") && isElementLoaded(conflictDetectedWindow.findElement(By.cssSelector("lg-button[label=\"Cancel\"] button")))){
            clickTheElement(conflictDetectedWindow.findElement(By.cssSelector("lg-button[label=\"Cancel\"] button")));
            SimpleUtils.pass("Cancel button is clicked!");
        } else {
            SimpleUtils.fail("Input string is not expected or buttons are not loaded!", false);
        }
    }
}
