package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.CreateSchedulePage;
import com.legion.pages.ScheduleMainPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleScheduleMainPage extends BasePage implements ScheduleMainPage {
    public ConsoleScheduleMainPage() {
        PageFactory.initElements(getDriver(), this);
    }


    @FindBy(css = "lg-button[label=\"Analyze\"]")
    private WebElement analyze;

    @FindBy(xpath = "//div[contains(text(),'Schedule History')]")
    private WebElement scheduleHistoryInAnalyzePopUp;

    @FindBy(css = "[label=\"Cancel\"]")
    private WebElement scheduleEditModeCancelButton;

    @FindBy(css = "lg-button[data-tootik=\"Edit Schedule\"]")
    private WebElement newEdit;

    @FindBy(css = "[ng-click=\"callOkCallback()\"]")
    private WebElement editAnywayPopupButton;

    @FindBy(css = "lg-button[data-tootik=\"Save changes\"]")
    private WebElement newScheduleSaveButton;

    @FindBy(xpath = "//div[contains(@ng-if,'PostSave')]")
    private WebElement popUpPostSave;

    @FindBy(css = "button.btn.sch-ok-single-btn")
    private WebElement btnOK;

    @FindBy(xpath = "//div[contains(@ng-if,'PreSave')]")
    private WebElement popUpPreSave;

    @FindBy(css = "button.btn.sch-save-confirm-btn")
    private WebElement scheduleVersionSaveBtn;

    @FindBy(css = "[label=\"Create New Shift\"]")
    private WebElement createNewShiftWeekView;

    @FindBy(css = "lg-button-group[buttons='scheduleTypeOptions'] div.lg-button-group-selected")
    private WebElement activScheduleType;

    @FindBy(css = "lg-button-group[buttons='scheduleTypeOptions'] div.lg-button-group-last")
    private WebElement scheduleTypeManager;

    @FindBy(css = "lg-button[label=\"Generate schedule\"]")
    private WebElement generateScheduleBtn;

    @FindBy(css = "lg-button[ng-click=\"controlPanel.fns.editAction($event)\"]")
    private WebElement editScheduleButton;

    @FindBy(css = "div.modal-content")
    private WebElement popupAlertPremiumPay;

    @FindBy(css = "button.btn.lgn-action-button.lgn-action-button-success")
    private WebElement btnEditAnyway;

    @FindBy(css = "button.btn.lgn-action-button.lgn-action-button-default")
    private WebElement btnCancelOnAlertPopup;

    @FindBy(css = "lg-button[ng-click=\"controlPanel.fns.saveConfirmation($event)\"]")
    private WebElement btnSaveOnSchedulePage;

    @FindBy(css = "lg-button[ng-click=\"controlPanel.fns.cancelAction($event)\"]")
    private WebElement btnCancelOnSchedulePage;

    @FindBy(css = "[ng-click=\"controlPanel.fns.publishConfirmation($event, false)\"]")
    private WebElement publishButton;

    public void clickOnScheduleAnalyzeButton() throws Exception {
        if (isElementLoaded(analyze)) {
            click(analyze);
            if (isElementLoaded(scheduleHistoryInAnalyzePopUp,5)) {
                SimpleUtils.pass("Analyze button is clickable and pop up page displayed");
            }
        } else {
            SimpleUtils.fail("Schedule Analyze Button not loaded successfully!", false);
        }
    }

    public void clickOnCancelButtonOnEditMode() throws Exception {
        if (isElementLoaded(scheduleEditModeCancelButton)) {
            click(scheduleEditModeCancelButton);
            SimpleUtils.pass("Schedule edit shift page cancelled successfully!");
        }
    }


    public void clickOnEditButton() throws Exception {
        if (isElementEnabled(newEdit,2)) {
            click(newEdit);
            if (isElementLoaded(editAnywayPopupButton, 2)) {
                click(editAnywayPopupButton);
                SimpleUtils.pass("Schedule edit shift page loaded successfully!");
            } else {
                SimpleUtils.pass("Schedule edit shift page loaded successfully for Draft or Publish Status");
            }
        } else {
            SimpleUtils.pass("Schedule Edit button is not enabled Successfully!");
        }
    }

    public void clickSaveBtn() throws Exception {
        if (isElementLoaded(newScheduleSaveButton)) {
            click(newScheduleSaveButton);
            clickOnVersionSaveBtn();
            clickOnPostSaveBtn();
        } else {
            SimpleUtils.fail("Schedule Save button not clicked Successfully!", false);
        }
    }

    public void clickOnVersionSaveBtn() throws Exception {
        if (isElementLoaded(popUpPreSave) && isElementLoaded(scheduleVersionSaveBtn)) {
            click(scheduleVersionSaveBtn);
            SimpleUtils.pass("Schedule Version Save button clicked Successfully!");
            waitForSeconds(3);
        } else {
            SimpleUtils.fail("Schedule Version Save button not clicked Successfully!", false);
        }
    }

    public void clickOnPostSaveBtn() throws Exception {
        if (isElementLoaded(popUpPostSave) && isElementLoaded(btnOK)) {
            click(btnOK);
            SimpleUtils.pass("Schedule Ok button clicked Successfully!");
            waitForSeconds(3);
        } else {
            SimpleUtils.fail("Schedule Ok button not clicked Successfully!", false);
        }
    }

    public Boolean isAddNewDayViewShiftButtonLoaded() throws Exception {
        if (isElementLoaded(createNewShiftWeekView)) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isScheduleTypeLoaded() {
        try {
            if (isElementEnabled(activScheduleType, 10)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public void switchToManagerView() throws Exception {
        CreateSchedulePage createSchedulePage = new ConsoleCreateSchedulePage();
        String activeWeekText = getActiveWeekText();
        if(isElementEnabled(activScheduleType,5)){
            if(activScheduleType.getText().equalsIgnoreCase("Suggested")){
                click(scheduleTypeManager);
                if(createSchedulePage.isGenerateButtonLoadedForManagerView()){
                    click(generateScheduleBtn);
                    createSchedulePage.generateScheduleFromCreateNewScheduleWindow(activeWeekText);
                }else{
                    SimpleUtils.fail("Generate button not found on page",false);
                }
            }else{
                if(createSchedulePage.isGenerateButtonLoadedForManagerView()){
                    click(generateScheduleBtn);
                    createSchedulePage.generateScheduleFromCreateNewScheduleWindow(activeWeekText);
                }else{
                    SimpleUtils.fail("Generate button not found on page",false);
                }
            }
        }else{
            SimpleUtils.fail("Schedule Type " + scheduleTypeManager.getText() + " is disabled",false);
        }
    }

    public void clickOnEditButtonNoMaterScheduleFinalizedOrNot() throws Exception {
        CreateSchedulePage createSchedulePage = new ConsoleCreateSchedulePage();
        waitForSeconds(5);
        if(checkEditButton())
        {
            // Validate what happens next to the Edit!
            // When Status is finalized, look for extra popup.
            clickTheElement(editScheduleButton);
            waitForSeconds(3);
            if(isElementLoaded(popupAlertPremiumPay,10) ) {
                SimpleUtils.pass("Edit button is clickable and Alert(premium pay pop-up) is appeared on Screen");
                // Validate CANCEL and EDIT ANYWAY Buttons are enabled.
                if(isElementEnabled(btnEditAnyway,10) && isElementEnabled(btnCancelOnAlertPopup,10)){
                    SimpleUtils.pass("CANCEL And EDIT ANYWAY Buttons are enabled on Alert Pop up");
                    SimpleUtils.report("Click on EDIT ANYWAY button and check for next save and cancel buttons");
                    clickTheElement(btnEditAnyway);
                } else {
                    SimpleUtils.fail("CANCEL And EDIT ANYWAY Buttons are not enabled on Alert Popup ",false);
                }
            }
            waitForSeconds(5);
            if(checkSaveButton() && checkCancelButton()) {
                SimpleUtils.pass("Save and Cancel buttons are enabled ");
            } else{
                SimpleUtils.fail("Save and Cancel buttons are not enabled. ", false);
            }
        }else{
            createSchedulePage.generateOrUpdateAndGenerateSchedule();
        }

/*        if(checkEditButton())
        {
            // Validate what happens next to the Edit!
            // When Status is finalized, look for extra popup.
            if(isScheduleFinalized())
            {
                click(editScheduleButton);
                String warningMessage1 = "Editing finalized schedule\n" + "Editing a finalized schedule after the ";
                String warningMessage2 = "-day advance notice period may incur a schedule change premium.";
                String editFinalizedScheduleWarning = editFinalizedScheduleWarningTitle.getText() + "\n" + editFinalizedScheduleWarningMessage.getText();
                if(isElementLoaded(popupAlertPremiumPay,5) && editFinalizedScheduleWarning.contains(warningMessage1) &&
                editFinalizedScheduleWarning.contains(warningMessage2)) {
                    SimpleUtils.pass("Edit button is clickable and Alert(premium pay pop-up) is appeared on Screen");
                    // Validate CANCEL and EDIT ANYWAY Buttons are enabled.
                    if(isElementEnabled(btnEditAnyway,5) && isElementEnabled(btnCancelOnAlertPopup,5)){
                        SimpleUtils.pass("CANCEL And EDIT ANYWAY Buttons are enabled on Alert Pop up");
                        SimpleUtils.report("Click on EDIT ANYWAY button and check for next save and cancel buttons");
                        click(btnEditAnyway);
                        if(checkSaveButton() && checkCancelButton()) {
                            SimpleUtils.pass("Save and Cancel buttons are enabled ");
                        }
                        else
                            SimpleUtils.fail("Save and Cancel buttons are not enabled. ", false);
                    }
                    else
                        SimpleUtils.fail("CANCEL And EDIT ANYWAY Buttons are not enabled on Alert Popup ",false);
                }
            }
            else
            {
                clickTheElement(editScheduleButton);
                // Validate Save and cancel buttons are enabled!
                if(checkSaveButton() && checkCancelButton()) {
                    SimpleUtils.pass("Save and Cancel buttons are enabled ");
                }
                else
                    SimpleUtils.fail("Save and Cancel buttons are not enabled. ", false);
            }
        }else
            generateOrUpdateAndGenerateSchedule(); */
    }

    @Override
    public boolean checkEditButton() throws Exception {
        if(isElementLoaded(editScheduleButton,10))
        {

            SimpleUtils.pass("Edit button is Editable");
            return true;
        }
        else {
            SimpleUtils.fail("Edit button is not Enable on screen", false);
            return false;
        }
    }

    @Override
    public boolean checkSaveButton() throws Exception {
        if(isElementEnabled(btnSaveOnSchedulePage,10))
        {
            SimpleUtils.pass("Save button is enabled ");
            return true;
        }
        else
        {
            SimpleUtils.fail("Save button is not enabled. ", true);
            return false;
        }
    }

    @Override
    public boolean checkCancelButton() throws Exception {
        if(isElementEnabled(btnCancelOnSchedulePage,10))
        {
            SimpleUtils.pass("Cancel button is enabled ");
            return true;
        }
        else
        {
            SimpleUtils.fail("Cancel button is not enabled. ", true);
            return false;
        }
    }

    @Override
    public void verifyEditButtonFuntionality() throws Exception {

        if(checkEditButton())
        {
            // Validate what happens next to the Edit!
            // When Status is finalized, look for extra popup.
            if(isScheduleFinalized())
            {
                click(editScheduleButton);
                if(isElementLoaded(popupAlertPremiumPay,5)) {
                    SimpleUtils.pass("Alert(premium pay pop-up) is appeared on Screen");
                    // Validate CANCEL and EDIT ANYWAY Buttons are enabled.
                    if(isElementEnabled(btnEditAnyway,5) && isElementEnabled(btnCancelOnAlertPopup,5)){
                        SimpleUtils.pass("CANCEL And EDIT ANYWAY Buttons are enabled on Alert Pop up");
                        click(btnEditAnyway);
                        if(checkSaveButton() && checkCancelButton()) {
                            SimpleUtils.pass("Save and Cancel buttons are enabled ");
                            selectCancelButton();
                        }
                        else
                            SimpleUtils.fail("Save and Cancel buttons are not enabled. ", false);

                    }
                    else
                        SimpleUtils.fail("CANCEL And EDIT ANYWAY Buttons are not enabled on Alert Popup ",false);
                }
                else
                    SimpleUtils.fail("Alert(premium pay pop-up) is not appeared on Screen",false);
            }
            else
            {
                click(editScheduleButton);
                SimpleUtils.pass("Edit button is clickable");
                // Validate Save and cancel buttons are enabled!
                if(checkSaveButton() && checkCancelButton()) {
                    SimpleUtils.pass("Save and Cancel buttons are enabled ");
                    selectCancelButton();
                }
                else
                    SimpleUtils.fail("Save and Cancel buttons are not enabled. ", false);
            }
        }
    }


    @Override
    public boolean isScheduleFinalized() throws Exception {
        if(isElementLoaded(publishButton,5))
        {
            SimpleUtils.report("Publish button is loaded on screen, Hence We don't expect Alert Popup.  ");
            return false;  }
        else {
            SimpleUtils.report("Publish button is not loaded on screen, Hence We have to expect Alert Popup.  ");
            return true;  }
    }

    @Override
    public void selectCancelButton() throws Exception {
        if(checkCancelButton())
        {
            click(btnCancelOnSchedulePage);
            SimpleUtils.pass("Cancel button is clicked ! ");
        }
        else
        {
            SimpleUtils.fail("Cancel Button cannot be clicked! ",false);
        }
    }

    @Override
    public void selectSaveButton() throws Exception {
        if(checkCancelButton())
        {
            click(btnSaveOnSchedulePage);
            SimpleUtils.pass("Save button is clicked ! ");
        }
        else
        {
            SimpleUtils.fail("Save Button cannot be clicked! ",false);
        }

    }
}
