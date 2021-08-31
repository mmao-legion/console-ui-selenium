package com.legion.pages.core.opusermanagement;

import com.legion.pages.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class OpsPortalWorkRolesPage extends BasePage {

    public OpsPortalWorkRolesPage() {
        PageFactory.initElements(getDriver(), this);
    }

    // Added by Sophia
    @FindBy(css = "div.lg-page-heading__breadcrumbs>a.ng-binding")
    private WebElement backButton;
    @FindBy(css = "lg-search>input-field.ng-isolate-scope>ng-form>input")
    private WebElement searchBox;
    @FindBy(css = "div.lg-search-icon")
    private WebElement searchButton;
    @FindBy(css = "table.lg-table.ng-scope>tbody>tr>td:first-child>lg-button")
    private List<WebElement> workRoleList;
    @FindBy(css = "table.lg-table.ng-scope>tbody>tr:first-child>td:first-child>lg-button")
    private WebElement theFirstWorkRoleInTheList;
    @FindBy(css = "div.lg-work-roles__placeholder-content.ng-binding")
    private WebElement noResultNotice;
    @FindBy(css = "lg-button[label='Edit']>button")
    private WebElement editButton;
    @FindBy(css = "lg-button[label='Add Work Role']>button")
    private WebElement addWorkRoleButton;

    @FindBy(css = "div.button-container lg-button[label='Cancel']>button")
    private WebElement cancelButton;
    // Cancel Dialog
    @FindBy(css = "h1.lg-modal__title>div")
    private WebElement cancelDialog;
    @FindBy(css = "lg-button[label='Keep editing']")
    private WebElement keepEditing;
    @FindBy(css = "lg-button[label='Yes']")
    private WebElement yesToCancelEditing;

    @FindBy(css = "div.button-container lg-button[label='Save']>button")
    private WebElement saveButton;

    @FindBy(css = "lg-button[label='Disable']")
    private WebElement disableButton;
    // Disable Dialog
    @FindBy(css = "div.lg-modal__title-icon.ng-binding")
    private WebElement disableDialog;
    @FindBy(css = "div.modal-content lg-button[label='Cancel']")
    private WebElement cancelDisableAction;
    @FindBy(css = "div.modal-content lg-button[label='OK']")
    private WebElement okToDisableAction;


    public void goBack() {
        backButton.click();
    }

    public void searchByWorkRole(String workRole) {
        searchBox.clear();
        searchBox.sendKeys(workRole);
        searchButton.click();
    }

    public String getTheFirstWorkRoleInTheList() {
        String workRoleLabel = theFirstWorkRoleInTheList.getAttribute("label");
        return workRoleLabel;
    }

    public String getNoResultNotice() {
        String notice = noResultNotice.getText();
        return notice;
    }

    public void goToWorkRoleDetails() {
        theFirstWorkRoleInTheList.click();
    }

    public void clickEditButton() {
        if (editButton.isDisplayed() && editButton.isEnabled()) {
            editButton.click();
        }
    }

    public void clickDisableButton() {
        if (disableButton.isDisplayed() && disableButton.isEnabled()) {
            disableButton.click();
        }
    }

    public void editAnExistingWorkRole(String oldName) {
        clickEditButton();
        searchByWorkRole(oldName);
        goToWorkRoleDetails();//go to work role details page
    }

    public void addNewWorkRole() {
        clickEditButton();
        addWorkRoleButton.click();//go to work role details page
    }

    public void save() {
        saveButton.click();
    }

    //cancel editing
    public void cancel() {
        cancelButton.click();
    }

    public String getCancelDialogTitle() {
        return cancelDialog.getText();
    }

    public void keepEditing() {
        keepEditing.click();
    }

    public void cancelEditing() {
        yesToCancelEditing.click();
    }


    //disable action
    public void disableAWorkRole(String name) {
        clickEditButton();
        searchByWorkRole(name);
        clickDisableButton();
    }

    public String getDisableDialogTitle() {
        return disableDialog.getText();// Disable Work Role
    }

    public void cancelDisableAction() {
        cancelDisableAction.click();
    }

    public void okToDisableAction() {
        okToDisableAction.click();
    }

    public List<String> getWorkRoleList() {
        ArrayList<String> wr = new ArrayList<String>();
        for(WebElement workRole:workRoleList){
            wr.add(workRole.getAttribute("label"));
        }
        return wr;
    }
}
