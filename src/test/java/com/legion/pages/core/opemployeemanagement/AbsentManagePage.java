package com.legion.pages.core.opemployeemanagement;

import com.legion.pages.BasePage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class AbsentManagePage extends BasePage {
    public AbsentManagePage() {
        PageFactory.initElements(getDriver(), this);
    }

    // Added by Sophia
    @FindBy(css = "div.lg-page-heading>div>a")
    private WebElement backButton;

    //templates
    @FindBy(css = "div.lg-tabs>nav>div:nth-child(1)")
    private WebElement templatesTab;
    //settings
    @FindBy(css = "div.lg-tabs>nav>div:nth-child(2)")
    private WebElement settingsTab;

    //new template
    @FindBy(css = "lg-button[label='New Template']>button")
    private WebElement newTemplateButton;
    @FindBy(css = "div.lg-modal>h1.lg-modal__title")
    private WebElement newTemplateModalTitle;
    @FindBy(css = "input-field[label='Name this template'] input")
    private WebElement templateName;
    @FindBy(css = "input-field[label='Description'] textarea")
    private WebElement templateDesc;
    @FindBy(css = "lg-button[label='Cancel']>button")
    private WebElement cancelCreatingTemp;
    @FindBy(css = "lg-button[label='Continue']>button")
    private WebElement continueCreatingTemp;
    @FindBy(css = "div.lg-templates-table-improved__grid-row--header>div span")
    private List<WebElement> templateTableHeaders;
    @FindBy(css = "lg-search>input-field input")
    private WebElement templateSearchBox;
    @FindBy(css = "div.lg-search-icon.ng-scope")
    private WebElement searchIcon;
    @FindBy(css = "div.lg-templates-table-improved__grid-row.ng-scope>div>lg-button.name")
    private List<WebElement> templateNameOfSearchResult;
    @FindBy(css = "table.lg-table.ng-scope>tbody>tr:first-child>td:nth-child(4)")
    private List<WebElement> creatorOfSearchResult;
    @FindBy(css = "div.default.ng-binding.ng-scope")
    private WebElement defaultLabel;
    //welcome modal
    @FindBy(css = "div.wm-close-button.walkme-x-button")
    private WebElement welComeModalCloseButton;

    //smart card
    @FindBy(css = "div.card-carousel-container card-carousel-card div.card-carousel-card-title")
    private List<WebElement> smartCardTitle;
    @FindBy(css = "span.card-carousel-link.ng-binding.ng-scope")
    private List<WebElement> smartCardLink;
    @FindBy(css = "lg-eg-status")
    private List<WebElement> templateStatus;

    @FindBy(css = "i.fa.fa-caret-right")
    private WebElement caretRight;
    @FindBy(css = "i.fa.fa-caret-down")
    private WebElement caretDown;
    @FindBy(css = "div.lg-templates-table-improved__grid-row.child-row>div>lg-button")
    private WebElement theChildTemplate;

    @FindBy(css = "div.lg-paged-search h4")
    private WebElement noMatchMessage;

    //template details
    @FindBy(css = "lg-button[label='History']>button")
    private WebElement historyButton;
    //div.lg-slider-pop>h1
    @FindBy(css = "div.lg-slider-pop>h1>div:nth-child(2)")
    private WebElement historyCloseButton;
    @FindBy(css = "div.lg-slider-pop>div.lg-slider-pop__content li:last-child")
    private WebElement createdRecordInHistory;
    @FindBy(css = "form-buttons lg-button[label='Archive']>button")
    private WebElement archiveButton;
    @FindBy(css = "lg-button[label='Delete']>button")
    private WebElement deleteButton;
    //delete modal
    @FindBy(css = "modal div.model-content")
    private WebElement deleteConfirmMessage;

    @FindBy(css = "lg-button[label='Edit']>button")
    private WebElement editTemplate;
    //edit template modal
    @FindBy(css = "div.templateName>input-field input")
    private WebElement tempName;
    @FindBy(css = "div.mt-10.description>input-field input")
    private WebElement tempDesc;
    @FindBy(css = "input-field[label='Edit Notes'] textarea")
    private WebElement tempNotes;
    @FindBy(css = "form-buttons lg-button[label='OK']>button")
    private WebElement okToEditing;
    @FindBy(css = "form-buttons lg-button[label='Cancel']>button")
    private WebElement cancelEditing;
    @FindBy(css = "div.title-breadcrumbs>h1")
    private WebElement titleBreadCrumb;


    @FindBy(css = "div.lg-tabs>nav>div:nth-child(1)")
    private WebElement detailsTab;
    @FindBy(css = "div.lg-tabs>nav>div:nth-child(2)")
    private WebElement associationTab;
    @FindBy(css = "input-field[label='Mark this as default template'] input")
    private WebElement markAsDefaultTemplate;
    @FindBy(css = "div.model-content.ng-scope")
    private WebElement markAsDefaultTempConfirmMes;

    //template lever
    @FindBy(css = "question-input[question-title='Can employees request time off ?'] h3")
    private WebElement canEmployeeRequestLabel;
    @FindBy(css = "yes-no lg-button-group>div>div.lg-button-group-first")
    private WebElement templateLeverCanRequestYes;
    @FindBy(css = "yes-no lg-button-group>div>div.lg-button-group-last")
    private WebElement templateLeverCanRequestNo;
    @FindBy(css = "question-input[question-title='Weekly limit (Time Off + Hours Worked)'] h3")
    private WebElement weeklyLimitLabel;
    @FindBy(css = "question-input[question-title='Weekly limit (Time Off + Hours Worked)'] input-field input")
    private WebElement templateLeverWeeklyLimitInput;
    @FindBy(css = "question-input[question-title='Weekly limit (Time Off + Hours Worked)'] div.input-faked")
    private WebElement weeklyLimitHrs;

    @FindBy(css = "table.lg-table.ng-scope>tbody>tr>td:nth-child(1)")
    private List<WebElement> timeOffReasonsInTemplateDetail;
    @FindBy(css = "tbody lg-button[label='View']")
    private WebElement view;
    @FindBy(css = "tbody lg-button[label='Edit']")
    private WebElement edit;
    @FindBy(css = "tbody lg-button[label='Remove']")
    private WebElement remove;
    @FindBy(css = "tbody lg-button[label='Configure']")
    private WebElement configure;
    @FindBy(css = "tbody lg-button[label='Not Configured']>button")
    private WebElement notConfigured;
    @FindBy(css = "lg-button[label='Cancel']>button")
    private WebElement cancelEditingTemplate;

    //template save
    @FindBy(css = "button.saveas-drop")
    private WebElement saveAsDrop;
    @FindBy(css = "div.saveas-list.ng-scope>h3:nth-child(1)")
    private WebElement saveAsDraft;
    @FindBy(css = "div.saveas-list.ng-scope>h3:nth-child(2)")
    private WebElement publishNow;
    @FindBy(css = "div.saveas-list.ng-scope>h3:nth-child(3)")
    private WebElement publishLater;
    //modal
    @FindBy(css = "div.lg-modal")
    private WebElement dateOfPublishModal;
    @FindBy(css = "div.modal-dialog ng-form.input-form.ng-pristine")
    private WebElement dateSelect;
    @FindBy(css = "div.lg-single-calendar-toolbar>a:last-child")
    private WebElement nextMonthArrow;
    @FindBy(css = "div.lg-single-calendar-date-wrapper>div:nth-child(20)")
    private WebElement dayChosen;
    //
    @FindBy(css = "form-buttons:nth-child(2)>div:nth-child(2)>lg-button:nth-child(2)>button.ng-scope.pre-saveas")
    private WebElement saveTemplate;

    //template association
    @FindBy(css = "div.templateAssociation_title>span")
    private WebElement templateAssociationTitle;
    @FindBy(css = "table.lg-table.templateAssociation_table.ng-scope>tbody>tr:nth-child(2)>td>input")
    private WebElement theFirstAssociateGroup;
    @FindBy(css = "lg-button[label='Save']")
    private WebElement saveAssociate;


    //setting page
    @FindBy(css = "div.basic-setting-info>question-input")
    private WebElement useAccrual;
    @FindBy(css = "lg-switch>label.switch span")
    private WebElement toggleSlide;
    @FindBy(css = "div.col-sm-2.addTimeOffReason>lg-button>button")
    private WebElement addTimeOffButton;
    @FindBy(css = "div.lg-modal>h1")
    private WebElement CreateNewTimeOffModalTitle;
    @FindBy(css = "input-field[label='Reason name'] input")
    private WebElement reasonName;
    @FindBy(css = "input-field[label='Reason name']>ng-form>lg-input-error span")
    private WebElement errorMes;
    @FindBy(css = "input-field[label='Accrual Code'] input")
    private WebElement accrualCode;
    @FindBy(css = "lg-button[label='Cancel']>button")
    private WebElement cancelCreating;
    @FindBy(css = "lg-button[label='OK']>button")
    private WebElement okCreating;

    @FindBy(css = "lg-accrual-setting table tr>td:nth-child(1)")
    private List<WebElement> timeOffReasonNames;
    @FindBy(css = "div.time-off-reason-setting table tr:last-child>td:first-child")
    private WebElement timeOffReasonAdded;
    @FindBy(css = "div.time-off-reason-setting table tr:last-child>td:last-child>lg-button[label='Edit']>button")
    private WebElement editTimeOff;
    @FindBy(css = "div.time-off-reason-setting table tr:last-child>td:last-child>lg-button[label='Remove']>button")
    private WebElement removeButton;
    @FindBy(css = "modal form p.lg-modal__content.lg-modal__text")
    private WebElement removeConfirmMes;

    //associate
    @FindBy(css = "lg-search[placeholder='You can search by name, label and description']>input-field input")
    private WebElement associateSearch;


    //home page methods
    public void back() {
        backButton.click();
    }

    public boolean isTemplateTabDisplayed() {
        return templatesTab.isDisplayed();
    }

    public boolean isSettingsTabDisplayed() {
        return templatesTab.isDisplayed();
    }

    public void switchToTemplates() {
        templatesTab.click();
    }

    public void switchToSettings() {
        settingsTab.click();
    }

    public boolean isNewTemplateButtonDisplayedAndEnabled() {
        try {
            if (isElementLoaded(newTemplateButton, 20) && isElementEnabled(newTemplateButton, 20)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public void createANewTemplate(String tempName, String tempDesc) throws Exception {
        if (isElementLoaded(newTemplateButton, 20)) {
            newTemplateButton.click();
            if (isElementLoaded(templateName, 10) && isElementLoaded(templateDesc, 10)) {
                templateName.clear();
                templateName.sendKeys(tempName);
                templateDesc.clear();
                templateDesc.sendKeys(tempDesc);
            } else {
                SimpleUtils.fail("Absence Management Page: Click on New Template button failed!", false);
            }
        } else {
            SimpleUtils.fail("Absence Management Page: New Template button failed to load!", false);
        }
    }

    public void cancel() {
        cancelCreatingTemp.click();
    }
    @FindBy(css = ".walkme-action-destroy-1.wm-close-link")
    private WebElement closeWakeme;
    public void submit() throws Exception {
        continueCreatingTemp.click();
        if (isElementLoaded(closeWakeme,10)){
            click(closeWakeme);
        }
    }

    public void closeWelcomeModal() {
        welComeModalCloseButton.click();
    }

    public String getTemplateSearchBoxPlaceHolder() {
        return templateSearchBox.getAttribute("placeholder");
    }

    public ArrayList<String> getTemplateTableHeaders() {
        return getWebElementsLabels(templateTableHeaders);
    }

    public void search(String searchText){
            templateSearchBox.clear();
            templateSearchBox.sendKeys(searchText);
            //searchIcon.click();
            waitForSeconds(3);
    }

    public String noMatch() {
        return noMatchMessage.getText();
    }

    public boolean isNoMatchMessageDisplayed(){
        return isElementDisplayed(noMatchMessage);
    }

    public int templateNumber(){
        return templateNameOfSearchResult.size();
    }

    public String getResult() {
        String name = "";
        if (areListElementVisible(templateNameOfSearchResult, 10)) {
            name = templateNameOfSearchResult.get(0).getText();
        }
        return name;
    }

    public ArrayList getWebElementsLabels(List<WebElement> webElementsList) {
        ArrayList<String> labelList = new ArrayList<>();
        webElementsList.forEach((e) -> {
            labelList.add(e.getText());
        });
        return labelList;
    }

    public boolean isRelated(String text) {
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> list1 = new ArrayList<String>();
        boolean contain = true;
        list = getWebElementsLabels(creatorOfSearchResult);
        list1 = getWebElementsLabels(templateNameOfSearchResult);
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i) + "&" + list1.get(i);
            if (!str.toLowerCase().contains(text.toLowerCase())) {
                contain = false;
                break;
            }
        }
        return contain;
    }

    public Boolean smartCardFilter() {
        Boolean isItFiltered = true;
        try {
            if (getWebElementsLabels(smartCardTitle).get(0).equals("UPCOMING DUES")) {
                smartCardLink.get(0).click();
                for (String status : getTemplateStatus()) {
                    if (!status.equals("Pending")) {
                        isItFiltered = false;
                        break;
                    }
                }
            } else if (getWebElementsLabels(smartCardTitle).get(0).equals("PUBLISHED")) {
                smartCardLink.get(0).click();
                for (String status : getTemplateStatus()) {
                    if (!status.equals("Published")) {
                        isItFiltered = false;
                        break;
                    }
                }
            }
            waitForSeconds(3);
            smartCardLink.get(0).click();
        } catch (Exception NoSuchElementException) {
            System.out.println("No published template!");
        }
        return isItFiltered;
    }

    public ArrayList<String> getTemplateStatus() {
        ArrayList<String> status = new ArrayList<>();
        templateStatus.forEach((e) -> {
            status.add(e.getAttribute("type"));
        });
        return status;
    }

    public void caretDown() {
        caretRight.click();
    }

    public void clickInDetails() {//into edit mode
        if (isElementDisplayed(caretRight)) {
            caretDown();
            theChildTemplate.click();
        } else if (isElementDisplayed(caretDown)) {
            theChildTemplate.click();
        } else {
            templateNameOfSearchResult.get(0).click();
        }
    }

    //template details page methods
    public boolean isHistoryButtonDisplayed() {
        return isElementDisplayed(historyButton);
    }

    public void openTemplateHistory() {
        historyButton.click();
    }

    public void closeHistory() {
        historyCloseButton.click();
    }

    public String getCreatedRecord() {
        return createdRecordInHistory.getText();
    }

    public boolean isArchiveButtonDisplayed() {
        return isElementDisplayed(archiveButton);
    }

    public void archivePublishedTemplate() {
        archiveButton.click();

    }

    public boolean isDeleteButtonDisplayed() {
        return isElementDisplayed(deleteButton);
    }

    public boolean isEditButtonDisplayed() {
        return isElementDisplayed(editTemplate);
    }

    public void switchToDetails() {
        detailsTab.click();
    }

    public void switchToAssociation() {
        associationTab.click();
    }

    public void associateTemplate(String groupName) {
        switchToAssociation();
        associateSearch.clear();
        associateSearch.sendKeys(groupName);
        theFirstAssociateGroup.click();
        scrollToBottom();
        saveAssociate.click();
    }

    public String getCanEmployeeRequestLabel() {
        return canEmployeeRequestLabel.getText();
    }

    public String getTemplateAssociationTitle() {
        return templateAssociationTitle.getText();
    }


    public void editTemplateInfo(String tpName, String tpDesc, String tpNotes) {
        editTemplate.click();
        tempName.clear();
        tempName.sendKeys(tpName);
        if (tpDesc != null) {
            tempDesc.clear();
            tempDesc.sendKeys(tpDesc);
        }
        tempNotes.clear();
        tempNotes.sendKeys(tpNotes);
    }

    public String getTemplateTitle() {
        return titleBreadCrumb.getText();
    }

    public void configureTemplate(String templateName) throws Exception {
        search(templateName);
        clickInDetails();
        waitForSeconds(5);
        editTemplate.click();
        okToActionInModal(true);
    }

    public void markAsDefaultTemplate() {
        markAsDefaultTemplate.click();
        okToActionInModal(true);
    }

    public String getDefaultLabel() {
        return defaultLabel.getText();
    }


    public String deleteTheTemplate() {
        deleteButton.click();
        String mes = deleteConfirmMessage.getText();
        return mes;
    }

    public void setTemplateLeverCanRequest(Boolean canRequest) {
        if (canRequest) {
            templateLeverCanRequestYes.click();

        } else {
            templateLeverCanRequestNo.click();
        }
    }

    public boolean isToggleAsSetted(Boolean toggleStatus) {
        String bgColor = "";
        String toggleOn = "rgba(244, 246, 248, 1)";
        if (toggleStatus) {
            bgColor = templateLeverCanRequestYes.getCssValue("background-color");
        } else {
            bgColor = templateLeverCanRequestNo.getCssValue("background-color");
        }
        return bgColor.equals(toggleOn);
    }


    public void setTemplateLeverWeeklyLimits(String hours) {
        templateLeverWeeklyLimitInput.clear();
        templateLeverWeeklyLimitInput.sendKeys(hours);
    }

    public String getWeeklyLimitHrs() {
        return weeklyLimitHrs.getText();
    }

    public void viewTimeOffConfigure(String timeOff) throws Exception {
        search(timeOff);
        isButtonClickable(view);
        System.out.println("View button is shown and clickable!");
        view.click();
    }

    public void configureTimeOffRules(String timeOff) throws Exception {
        search(timeOff);
        waitForSeconds(3);
        if (isButtonClickable(configure)) {
            System.out.println("Configure button is shown and clickable!");
            configure.click();
        } else if (isButtonClickable(edit)) {
            System.out.println("Edit button is shown and clickable!");
            edit.click();
        } else {
            System.out.println("Something is wrong with the time off UI in the template detail page!");
        }
    }

    public boolean isButtonClickable(WebElement button) {
        Boolean shown = null;
        Boolean status = null;
        try {
            shown = button.isDisplayed();
            status = button.isEnabled();
            return shown && status;
        } catch (Exception NoSuchElement) {
            return false;
        }
    }

    public void removeTimeOffRules(String timeOff) throws Exception {
        search(timeOff);
        if (isButtonClickable(remove)) {
            System.out.println("Remove button is shown and clickable!");
            remove.click();
        } else {
            System.out.println("Time off rule can't be removed before configured!");
        }
    }

    public String getNotConfigured() {
        return notConfigured.getText();
    }

    public void saveTemplateAs(String method) {
        scrollToBottom();
        saveAsDrop.click();
        switch (method) {
            case "Save as draft":
                saveAsDraft.click();
                break;
            case "Publish now":
                publishNow.click();
                break;
            case "Publish later":
                publishLater.click();
                break;
        }
        saveTemplate.click();
        try {
            if (dateOfPublishModal.isDisplayed()) {
                dateSelect.click();
                nextMonthArrow.click();
                dayChosen.click();
                okToActionInModal(true);
            }
        } catch (Exception NoSuchElementException) {
            System.out.println("Doesn't use publish later");
        }
        waitForSeconds(5);
    }


    //settings
    public String getQuestionTitle() {
        return useAccrual.getAttribute("question-title");
    }

    public void setAccrualToggle(Boolean tureOrFalse) {
        if (tureOrFalse != isAccrualToggleOn()) {
            toggleSlide.click();
        }
    }

    public Boolean isAccrualToggleOn() {
        String toggleColor = toggleSlide.getCssValue("background-color");
        String toggleOn = "rgba(49, 61, 146, 1)";
        if (toggleColor.equals(toggleOn)) {
            System.out.println("Accrual toggle is on!");
            return true;
        } else {
            System.out.println("Accrual toggle is off!");
            return false;
        }
    }

    public void addTimeOff(String reaName) {
        addTimeOffButton.click();
        reasonName.clear();
        reasonName.sendKeys(reaName);
    }

    public void okCreatingTimeOff() {
        okCreating.click();
    }

    public void cancelCreatingTimeOff() {
        cancelCreating.click();
    }

    public String getErrorMessage() {
        return errorMes.getText();
    }

    public Boolean isOKButtonEnabled() {//time off reason name is required
        addTimeOffButton.click();
        reasonName.clear();
        return okCreating.isEnabled();
    }

    public ArrayList<String> getAllTheTimeOffReasons() {
        return getWebElementsLabels(timeOffReasonNames);
        // use contains() to judge if the new time off is in the ArrayList
    }

    public Boolean isTimeOffReasonDisplayed(String timeOff) {
        return getAllTheTimeOffReasons().contains(timeOff);
    }


    public void editTimeOffReason(String reaName) throws Exception {
        if (isElementLoaded(editTimeOff, 10)) {
            clickTheElement(editTimeOff);
            if (isElementLoaded(reasonName, 5)) {
                reasonName.clear();
                reasonName.sendKeys(reaName);
            }
        } else
            SimpleUtils.fail("Edit button or reason name input box fail to load! ", false);
    }

    public String removeTimeOffInSettings() {
        scrollToElement(removeButton);
        removeButton.click();
        return removeConfirmMes.getText();
    }
    //ok to action in modal and get reason names , not contain now.

    public void okToActionInModal(boolean okToAction) {
        if (okToAction) {
            okToEditing.click();
        } else {
            cancelEditing.click();
        }
        waitForSeconds(3);
    }


    @FindBy(css = ".time-off-reason-setting tr.ng-scope")
    private List<WebElement> timeOffReasons;
    public void removeTimeOffReasons(String timeOffReasonName) {
        if (areListElementVisible(timeOffReasons, 10) && timeOffReasons.size()>0) {
            for (WebElement reason: timeOffReasons) {
                if (reason.findElement(By.cssSelector(".one-line-overflow")).getText().equalsIgnoreCase(timeOffReasonName)){
                    clickTheElement(reason.findElement(By.cssSelector("[label=\"Remove\"]")));
                    okCreatingTimeOff();
                    System.out.println("Delete the time off reason successfull!");
                    break;
                }
            }
        } else
            System.out.println("There is no time off reason been listed!");
    }
}
