package com.legion.pages.core.opusermanagement;

import com.legion.pages.BasePage;
import com.legion.utils.SimpleUtils;
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
    @FindBy(css = "table.lg-table.ng-scope>tbody>tr>td:first-child>lg-button")
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
    @FindBy(css = "div.lg-toast")
    private WebElement popUpMsg;


    public void goBack() {
        backButton.click();
    }

    public void searchByWorkRole(String workRole) throws Exception {
        waitForSeconds(2);
        if (isElementLoaded(searchBox, 15)) {
            searchBox.clear();
            searchBox.sendKeys(workRole);
            searchButton.click();
        } else {
            SimpleUtils.fail("Work Role Page: Search box failed to load!", false);
        }
    }

    public String getTheFirstWorkRoleInTheList() throws Exception {
        String workRoleLabel = "";
        if (isElementLoaded(theFirstWorkRoleInTheList, 15)) {
            workRoleLabel = theFirstWorkRoleInTheList.getAttribute("label");
        } else {
            SimpleUtils.fail("Work Role Page: The first work role failed to load in the list!", false);
        }
        return workRoleLabel;
    }

    public String getNoResultNotice() {
        String notice = noResultNotice.getText();
        return notice;
    }

    public void goToWorkRoleDetails() throws Exception {
        if (isElementLoaded(theFirstWorkRoleInTheList, 15)) {
            clickTheElement(theFirstWorkRoleInTheList);
        } else {
            SimpleUtils.fail("Work Role Page: The first work role failed to load in the list!", false);
        }
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

    public void editAnExistingWorkRole(String oldName) throws Exception {
        waitForSeconds(2);
        clickEditButton();
        searchByWorkRole(oldName);
        goToWorkRoleDetails();//go to work role details page
    }

    public void addNewWorkRole() {
        waitForSeconds(2);
        clickEditButton();
        addWorkRoleButton.click();//go to work role details page
    }

    public void save() throws Exception {
        if (isElementLoaded(saveButton, 10)) {
            saveButton.click();
            if (isElementLoaded(popUpMsg, 5) ) {
                String successMessage = popUpMsg.getText();
                if (successMessage.contains("Success")) {
                    SimpleUtils.pass("Work Role Page: Click on Save button successfully!");
                } else
                    SimpleUtils.fail("The Success message display incorrectly! the actual message is: " + successMessage, false);
            } else {
                SimpleUtils.fail("Work Role Page: Failed to Save!", false);
            }
        } else {
            SimpleUtils.fail("Work Role Page: Save button failed to load!", false);
        }
    }

    //cancel editing
    public void cancel() {
        waitForSeconds(2);
        cancelButton.click();
    }

    public String getCancelDialogTitle() {
        return cancelDialog.getText();
    }

    public void keepEditing() {
        keepEditing.click();
    }

    public void cancelEditing() {
        waitForSeconds(2);
        yesToCancelEditing.click();
    }


    //disable action
    public void disableAWorkRole(String name) throws Exception {
        waitForSeconds(2);
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
        waitForSeconds(2);
        okToDisableAction.click();
    }

    @FindBy (css = ".lg-pagination__arrow--right")
    private WebElement pageRightBtn;
    public List<String> getWorkRoleList() throws Exception {
        ArrayList<String> wr = new ArrayList<String>();
        for(WebElement workRole:workRoleList){
            wr.add(workRole.getAttribute("label"));
        }
        while (isElementLoaded(pageRightBtn, 5)
                && !pageRightBtn.getAttribute("class").contains("disabled")){
            clickTheElement(pageRightBtn);
            for(WebElement workRole:workRoleList){
                wr.add(workRole.getAttribute("label"));
            }
        }
        return wr;
    }
}
