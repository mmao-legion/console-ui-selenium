package com.legion.pages.core;

import com.legion.pages.*;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @FindBy(css = "select.ng-valid-required")
    private WebElement scheduleGroupByButton;

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

    @FindBy(css = "div.lg-filter__wrapper")
    private WebElement filterPopup;

    @FindBy(css = "[ng-click=\"$ctrl.openFilter()\"]")
    private WebElement filterButton;

    @FindBy(css = "[ng-repeat=\"(key, opts) in $ctrl.displayFilters\"]")
    private List<WebElement> scheduleFilterElements;

    @FindBy(css = "[ng-repeat=\"opt in opts\"]")
    private List<WebElement> filters;

    public void selectGroupByFilter(String optionVisibleText) {
        Select groupBySelectElement = new Select(scheduleGroupByButton);
        List<WebElement> scheduleGroupByButtonOptions = groupBySelectElement.getOptions();
        groupBySelectElement.selectByIndex(1);
        for (WebElement scheduleGroupByButtonOption : scheduleGroupByButtonOptions) {
            if (scheduleGroupByButtonOption.getText().toLowerCase().contains(optionVisibleText.toLowerCase())) {
                groupBySelectElement.selectByIndex(scheduleGroupByButtonOptions.indexOf(scheduleGroupByButtonOption));
                SimpleUtils.report("Selected Group By Filter: '" + optionVisibleText + "'");
            }
        }
    }

    public void selectShiftTypeFilterByText(String filterText) throws Exception {
        String shiftTypeFilterKey = "shifttype";
        ArrayList<WebElement> shiftTypeFilters = getAvailableFilters().get(shiftTypeFilterKey);
        unCheckFilters(shiftTypeFilters);
        for (WebElement shiftTypeOption : shiftTypeFilters) {
            if (shiftTypeOption.getText().toLowerCase().contains(filterText.toLowerCase())) {
                click(shiftTypeOption);
                break;
            }
        }
        if (!filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
            click(filterButton);
    }

    public HashMap<String, ArrayList<WebElement>> getAvailableFilters() {
        HashMap<String, ArrayList<WebElement>> scheduleFilters = new HashMap<String, ArrayList<WebElement>>();
        try {
            if (isElementLoaded(filterButton,15)) {
                if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
                    click(filterButton);
                for (WebElement scheduleFilterElement : scheduleFilterElements) {
                    WebElement filterLabel = scheduleFilterElement.findElement(By.className("lg-filter__category-label"));
                    String filterType = filterLabel.getText().toLowerCase().replace(" ", "");
                    List<WebElement> filters = scheduleFilterElement.findElements(By.cssSelector("input-field[type=\"checkbox\"]"/*"[ng-repeat=\"opt in opts\"]"*/));
                    ArrayList<WebElement> filterList = new ArrayList<WebElement>();
                    for (WebElement filter : filters) {
                        filterList.add(filter);
                    }
                    scheduleFilters.put(filterType, filterList);
                }
            } else {
                SimpleUtils.fail("Filters button not found on Schedule page!", false);
            }
        } catch (Exception e) {
            SimpleUtils.fail("Filters button not loaded successfully on Schedule page!", true);
        }
        return scheduleFilters;
    }


    public void unCheckFilters(ArrayList<WebElement> filterElements) throws Exception {
        if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
            click(filterButton);
        waitForSeconds(2);
        for (WebElement filterElement : filterElements) {
            if (isElementLoaded(filterElement, 5)) {
                WebElement filterCheckBox = filterElement.findElement(By.cssSelector("input[type=\"checkbox\"]"));
                String elementClasses = filterCheckBox.getAttribute("class").toLowerCase();
                if (elementClasses.contains("ng-not-empty"))
                    click(filterElement);
            }
        }
    }

    public void unCheckFilters() throws Exception {
        if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
            click(filterButton);
        if (areListElementVisible(filters, 10)) {
            for (WebElement filterElement : filters) {
                waitForSeconds(2);
                WebElement filterCheckBox = filterElement.findElement(By.cssSelector("input[type=\"checkbox\"]"));
                String elementClasses = filterCheckBox.getAttribute("class").toLowerCase();
                if (elementClasses.contains("ng-not-empty"))
                    clickTheElement(filterCheckBox);
            }
        }
    }


    @FindBy(xpath = "//span[contains(text(),'Save')]")
    private WebElement scheduleSaveBtn;

    @FindBy(css = "button[ng-click*='confirmSaveAction']")
    private WebElement saveOnSaveConfirmationPopup;

    @FindBy(css = ".lg-toast")
    private WebElement msgOnTop;

    public void saveSchedule() throws Exception {
        if (isElementEnabled(scheduleSaveBtn, 10)) {
            scrollToTop();
            waitForSeconds(3);
            clickTheElement(scheduleSaveBtn);
        } else {
            SimpleUtils.fail("Schedule save button not found", false);
        }
        if (isElementEnabled(saveOnSaveConfirmationPopup, 3)) {
            clickTheElement(saveOnSaveConfirmationPopup);
            waitForSeconds(3);
            try{
                if (isElementLoaded(msgOnTop, 20) && msgOnTop.getText().contains("Success")) {
                    SimpleUtils.pass("Save the Schedule Successfully!");
                } else if (isElementLoaded(editScheduleButton, 10)) {
                    SimpleUtils.pass("Save the Schedule Successfully!");
                } else {
                    SimpleUtils.fail("Save Schedule Failed!", false);
                }
            } catch(StaleElementReferenceException e){
                SimpleUtils.report("stale element reference: element is not attached to the page document");
            }
            waitForSeconds(3);
        } else {
            SimpleUtils.fail("Schedule save button not found", false);
        }
    }


    @FindBy(css = "[ng-click=\"printActionInProgress() || printAction($event)\"]")
    private WebElement printButton;

    @FindBy(css = "[label=\"Print\"]")
    private WebElement printButtonInPrintLayout;
    @Override
    public void printButtonIsClickable() throws Exception {
        if (isElementLoaded(printButton,10)){
            scrollToTop();
            click(printButton);
            if(isElementLoaded(printButtonInPrintLayout, 5)) {
                click(printButtonInPrintLayout);
            }
        }else{
            SimpleUtils.fail("There is no print button",false);
        }
    }


    @FindBy(css = "lg-button[label=\"Edit\"]")
    private WebElement edit;

    @FindBy(css = "lg-button-group[buttons='scheduleTypeOptions'] div.lg-button-group-first")
    private WebElement scheduleTypeSystem;

    @FindBy (css = "[on-change=\"updateGroupBy(value)\"]")
    private WebElement groupByAllIcon;

    @Override
    public void legionButtonIsClickableAndHasNoEditButton() throws Exception {
        clickOnSuggestedButton();
        if(!isElementLoaded(edit,5)){
            SimpleUtils.pass("Legion schedule has no edit button");
        }else{
            SimpleUtils.fail("it's not in legion schedule page", true);
        }
    }

    public void clickOnSuggestedButton() throws Exception {
        if (isElementEnabled(scheduleTypeSystem, 5)) {
            clickTheElement(scheduleTypeSystem);
            SimpleUtils.pass("legion button is clickable");
        }else {
            SimpleUtils.fail("the schedule is not generated, generated schedule firstly",true);
        }
    }

    public void legionIsDisplayingTheSchedul() throws Exception {
        if(isElementLoaded(groupByAllIcon,10)){
            SimpleUtils.pass("Legion schedule is displaying");
        }else {
            SimpleUtils.fail("Legion Schedule load failed", true);
        }
    }

    public void filterScheduleByShiftTypeWeekView(ArrayList<WebElement> shiftTypeFilters) {
        //String shiftType = "";
        ScheduleShiftTablePage scheduleShiftTablePage = new ConsoleScheduleShiftTablePage();
        for (WebElement shiftTypeFilter : shiftTypeFilters) {
            try {
                Thread.sleep(1000);
                if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
                    click(filterButton);
                unCheckFilters(shiftTypeFilters);
                String shiftType = shiftTypeFilter.getText();
                SimpleUtils.report("Data for Shift Type: '" + shiftType + "'");
                click(shiftTypeFilter);
                click(filterButton);
                String cardHoursAndWagesText = "";
                SmartCardPage smartCardPage = new ConsoleSmartCardPage();
                HashMap<String, Float> hoursAndWagesCardData = smartCardPage.getScheduleLabelHoursAndWages();
                for (Map.Entry<String, Float> hoursAndWages : hoursAndWagesCardData.entrySet()) {
                    if (cardHoursAndWagesText != "")
                        cardHoursAndWagesText = cardHoursAndWagesText + ", " + hoursAndWages.getKey() + ": '" + hoursAndWages.getValue() + "'";
                    else
                        cardHoursAndWagesText = hoursAndWages.getKey() + ": '" + hoursAndWages.getValue() + "'";
                }
                SimpleUtils.report("Active Week Card's Data: " + cardHoursAndWagesText);
                scheduleShiftTablePage.getHoursAndTeamMembersForEachDaysOfWeek();
                SimpleUtils.assertOnFail("Sum of Daily Schedule Hours not equal to Active Week Schedule Hours!", scheduleShiftTablePage.verifyActiveWeekDailyScheduleHoursInWeekView(), true);

                if (!getActiveGroupByFilter().toLowerCase().contains(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyTM.getValue().toLowerCase())
                        && !shiftType.toLowerCase().contains("open"))
                    scheduleShiftTablePage.verifyActiveWeekTeamMembersCountAvailableShiftCount();
            } catch (Exception e) {
                SimpleUtils.fail("Unable to get Card data for active week!", true);
            }
        }
    }

    public void filterScheduleByShiftTypeDayView(ArrayList<WebElement> shiftTypeFilters) {
        ScheduleShiftTablePage scheduleShiftTablePage = new ConsoleScheduleShiftTablePage();
        for (WebElement shiftTypeFilter : shiftTypeFilters) {
            try {
                if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
                    click(filterButton);
                unCheckFilters(shiftTypeFilters);
                SimpleUtils.report("Data for Shift Type: '" + shiftTypeFilter.getText() + "'");
                click(shiftTypeFilter);
                click(filterButton);
                String cardHoursAndWagesText = "";
                SmartCardPage smartCardPage = new ConsoleSmartCardPage();
                HashMap<String, Float> hoursAndWagesCardData = smartCardPage.getScheduleLabelHoursAndWages();
                for (Map.Entry<String, Float> hoursAndWages : hoursAndWagesCardData.entrySet()) {
                    if (cardHoursAndWagesText != "")
                        cardHoursAndWagesText = cardHoursAndWagesText + ", " + hoursAndWages.getKey() + ": '" + hoursAndWages.getValue() + "'";
                    else
                        cardHoursAndWagesText = hoursAndWages.getKey() + ": '" + hoursAndWages.getValue() + "'";
                }
                SimpleUtils.report("Active Week Card's Data: " + cardHoursAndWagesText);
                String timeDurationText = "";
                for (String timeDuration : scheduleShiftTablePage.getScheduleDayViewGridTimeDuration()) {
                    if (timeDurationText == "")
                        timeDurationText = timeDuration;
                    else
                        timeDurationText = timeDurationText + " | " + timeDuration;
                }
                SimpleUtils.report("Schedule Day View Shift Duration: " + timeDurationText);

                String budgetedTeamMembersCount = "";
                for (String budgetedTeamMembers : scheduleShiftTablePage.getScheduleDayViewBudgetedTeamMembersCount()) {
                    if (budgetedTeamMembersCount == "")
                        budgetedTeamMembersCount = budgetedTeamMembers;
                    else
                        budgetedTeamMembersCount = budgetedTeamMembersCount + " | " + budgetedTeamMembers;
                }
                SimpleUtils.report("Schedule Day View budgeted Team Members count: " + budgetedTeamMembersCount);

                String scheduleTeamMembersCount = "";
                for (String scheduleTeamMembers : scheduleShiftTablePage.getScheduleDayViewScheduleTeamMembersCount()) {
                    if (scheduleTeamMembersCount == "")
                        scheduleTeamMembersCount = scheduleTeamMembers;
                    else
                        scheduleTeamMembersCount = scheduleTeamMembersCount + " | " + scheduleTeamMembers;
                }
                SimpleUtils.report("Schedule Day View budgeted Team Members count: " + scheduleTeamMembersCount);
            } catch (Exception e) {
                SimpleUtils.fail("Unable to get Card data for active week!", true);
            }
        }
    }

    @Override
    public String getActiveGroupByFilter() throws Exception {
        String selectedGroupByFilter = "";
        if (isElementLoaded(scheduleGroupByButton)) {
            Select groupBySelectElement = new Select(scheduleGroupByButton);
            selectedGroupByFilter = groupBySelectElement.getFirstSelectedOption().getText();
        } else {
            SimpleUtils.fail("Group By Filter not loaded successfully for active Week/Day: '" + getActiveWeekText() + "'", false);
        }
        return selectedGroupByFilter;
    }


    public void filterScheduleByWorkRoleAndShiftType(boolean isWeekView) throws Exception {
        waitForSeconds(10);
        String shiftTypeFilterKey = "shifttype";
        String workRoleFilterKey = "workrole";
        HashMap<String, ArrayList<WebElement>> availableFilters = getAvailableFilters();
        if (availableFilters.size() > 1) {
            ArrayList<WebElement> shiftTypeFilters = availableFilters.get(shiftTypeFilterKey);
            ArrayList<WebElement> workRoleFilters = availableFilters.get(workRoleFilterKey);
            for (WebElement workRoleFilter : workRoleFilters) {
                if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
                    click(filterButton);
                unCheckFilters(workRoleFilters);
                click(workRoleFilter);
                SimpleUtils.report("Data for Work Role: '" + workRoleFilter.getText() + "'");
                if (isWeekView)
                    filterScheduleByShiftTypeWeekView(shiftTypeFilters);
                else
                    filterScheduleByShiftTypeDayView(shiftTypeFilters);
            }
        } else {
            SimpleUtils.fail("Filters are not appears on Schedule page!", false);
        }
    }


    public ArrayList<HashMap<String, String>> getHoursAndShiftsCountForEachWorkRolesInWeekView() throws Exception {
        ScheduleShiftTablePage scheduleShiftTablePage = new ConsoleScheduleShiftTablePage();
        String workRoleFilterKey = "workrole";
        ArrayList<HashMap<String, String>> eachWorkRolesData = new ArrayList<HashMap<String, String>>();
        HashMap<String, ArrayList<WebElement>> availableFilters = getAvailableFilters();
        SmartCardPage smartCardPage = new ConsoleSmartCardPage();
        if (availableFilters.size() > 1) {
            ArrayList<WebElement> workRoleFilters = availableFilters.get(workRoleFilterKey);
            for (WebElement workRoleFilter : workRoleFilters) {
                if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
                    click(filterButton);
                unCheckFilters(workRoleFilters);
                click(workRoleFilter);

                //adding workrole name
                HashMap<String, String> workRole = new HashMap<String, String>();
                workRole.put("workRole", workRoleFilter.getText());

                //Adding Card data (Hours & Wages)
                for (Map.Entry<String, Float> e : smartCardPage.getScheduleLabelHoursAndWages().entrySet())
                    workRole.put(e.getKey(), e.getValue().toString());
                // Adding Shifts Count
                workRole.put("shiftsCount", "" + scheduleShiftTablePage.getAllAvailableShiftsInWeekView().size());

                eachWorkRolesData.add(workRole);
            }
            unCheckFilters(workRoleFilters);
            if (!filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
                click(filterButton);
        } else {
            SimpleUtils.fail("Filters are not appears on Schedule page!", false);
        }

        return eachWorkRolesData;
    }


    public void selectWorkRoleFilterByIndex(int index, boolean isClearWorkRoleFilters) throws Exception {
        String filterType = "workrole";
        ArrayList<WebElement> availableWorkRoleFilters = getAvailableFilters().get(filterType);
        if (availableWorkRoleFilters.size() >= index) {
            if (isClearWorkRoleFilters)
                unCheckFilters(availableWorkRoleFilters);
            click(availableWorkRoleFilters.get(index));
            SimpleUtils.pass("Schedule Work Role:'" + availableWorkRoleFilters.get(index).getText() + "' Filter selected Successfully!");
        }
    }

    @Override
    public void selectWorkRoleFilterByText(String workRoleLabel, boolean isClearWorkRoleFilters) throws Exception {
        String filterType = "workrole";
        ArrayList<WebElement> availableWorkRoleFilters = getAvailableFilters().get(filterType);
        if (isClearWorkRoleFilters)
            unCheckFilters(availableWorkRoleFilters);
        for (WebElement availableWorkRoleFilter : availableWorkRoleFilters) {
            if (availableWorkRoleFilter.getText().contains(workRoleLabel)) {
                click(availableWorkRoleFilter);
                SimpleUtils.pass("Schedule Work Role:'" + availableWorkRoleFilter.getText() + "' Filter selected Successfully!");
                break;
            }
        }
        if (!filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
            click(filterButton);
    }

    @FindBy (css = ".week-schedule-shift .week-schedule-shift-wrapper")
    private List<WebElement> wholeWeekShifts;
    public void filterScheduleByShiftTypeWeekViewAsTeamMember(ArrayList<WebElement> shiftTypeFilters) throws Exception {
        if (shiftTypeFilters.size() > 0) {
            for (WebElement shiftTypeFilter : shiftTypeFilters) {
                if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
                    click(filterButton);
                unCheckFilters(shiftTypeFilters);
                String shiftType = shiftTypeFilter.getText();
                SimpleUtils.report("Data for Shift Type: '" + shiftType + "'");
                click(shiftTypeFilter);
                click(filterButton);
                if (areListElementVisible(wholeWeekShifts, 5)) {
                    for (WebElement shift : wholeWeekShifts) {
                        WebElement name = shift.findElement(By.className("week-schedule-worker-name"));
                        if (shiftType.contains("Open")) {
                            if (!name.getText().equalsIgnoreCase("Open")) {
                                SimpleUtils.fail("Shift: " + name.getText() + " isn't for shift type: " + shiftType, false);
                            }
                        }else {
                            if (name.getText().contains("Open")) {
                                SimpleUtils.fail("Shift: " + name.getText() + " isn't for shift type: " + shiftType, false);
                            }
                        }
                    }
                }else {
                    SimpleUtils.report("Didn't find shift for type: " + shiftType);
                }
            }
        }else {
            SimpleUtils.fail("Shift Type Filters not loaded Successfully!", false);
        }
    }

    @FindBy(css = ".sch-day-view-shift")
    private List<WebElement> dayViewAvailableShifts;
    public void filterScheduleByShiftTypeDayViewAsTeamMember(ArrayList<WebElement> shiftTypeFilters) throws Exception {
        if (shiftTypeFilters.size() > 0) {
            for (WebElement shiftTypeFilter : shiftTypeFilters) {
                if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
                    click(filterButton);
                unCheckFilters(shiftTypeFilters);
                String shiftType = shiftTypeFilter.getText();
                SimpleUtils.report("Data for Shift Type: '" + shiftType + "'");
                click(shiftTypeFilter);
                click(filterButton);
                if (areListElementVisible(dayViewAvailableShifts, 5)) {
                    for (WebElement shift : dayViewAvailableShifts) {
                        WebElement name = shift.findElement(By.className("sch-day-view-shift-worker-name"));
                        if (shiftType.equalsIgnoreCase("Open")) {
                            if (!name.getText().contains("Open")) {
                                SimpleUtils.fail("Shift: " + name.getText() + " isn't for shift type: " + shiftType, false);
                            }
                        }else {
                            if (name.getText().contains("Open")) {
                                SimpleUtils.fail("Shift: " + name.getText() + " isn't for shift type: " + shiftType, false);
                            }
                        }
                    }
                }else {
                    SimpleUtils.report("Didn't find shift for type: " + shiftType);
                }
            }
        }else {
            SimpleUtils.fail("Shift Type Filters not loaded Successfully!", false);
        }
    }


    @Override
    public void checkAndUnCheckTheFilters() throws Exception {
        if (areListElementVisible(filters, 10)) {
            unCheckFilters();
            for (WebElement filter : filters) {
                String filterName = filter.findElement(By.className("input-label")) == null ? "" : filter.findElement(By.className("input-label")).getText();
                WebElement filterCheckBox = filter.findElement(By.cssSelector("input[type=\"checkbox\"]"));
                String elementClass = filterCheckBox.getAttribute("class").toLowerCase();
                if (elementClass.contains("ng-not-empty")) {
                    SimpleUtils.fail("Uncheck the filter: " + filterName + " not Successfully!", false);
                }else {
                    SimpleUtils.pass("Uncheck the filter: " + filterName + " Successfully!");
                }
                click(filterCheckBox);
                elementClass = filterCheckBox.getAttribute("class").toLowerCase();
                if (elementClass.contains("ng-not-empty")) {
                    SimpleUtils.pass("Check the filter: " + filterName + " Successfully!");
                }else {
                    SimpleUtils.fail("Check the filter: " + filterName + " not Successfully!", false);
                }
            }
        }else {
            SimpleUtils.fail("Filters on Schedule page not loaded Successfully!", false);
        }
    }

    @Override
    public void filterScheduleByShiftTypeAsTeamMember(boolean isWeekView) throws Exception {
        String shiftTypeFilterKey = "shifttype";
        HashMap<String, ArrayList<WebElement>> availableFilters = getAvailableFilters();
        if (availableFilters.size() > 0) {
            ArrayList<WebElement> shiftTypeFilters = availableFilters.get(shiftTypeFilterKey);
            if (isWeekView) {
                filterScheduleByShiftTypeWeekViewAsTeamMember(shiftTypeFilters);
            }else {
                filterScheduleByShiftTypeDayViewAsTeamMember(shiftTypeFilters);
            }
        }
    }


    @Override
    public String selectOneFilter() throws Exception {
        String selectedFilter = null;
        if (areListElementVisible(filters, 10)) {
            unCheckFilters();
            waitForSeconds(2);
            WebElement filterCheckBox = filters.get(1).findElement(By.cssSelector("input[type=\"checkbox\"]"));
            String elementClass = filterCheckBox.getAttribute("class").toLowerCase();
            if (elementClass.contains("ng-empty")) {
                clickTheElement(filterCheckBox);
                waitForSeconds(2);
                elementClass = filterCheckBox.getAttribute("class").toLowerCase();
            }
            selectedFilter = filters.get(1).findElement(By.className("input-label")) == null ? "" : filters.get(1).findElement(By.className("input-label")).getText();
            if (elementClass.contains("ng-not-empty")) {
                SimpleUtils.pass("Check the filter: " + selectedFilter + " Successfully!");
            }else {
                SimpleUtils.fail("Check the filter: " + selectedFilter + " not Successfully!", false);
            }
        }else {
            SimpleUtils.fail("Filters on Schedule page not loaded Successfully!", false);
        }
        return selectedFilter;
    }


    @Override
    public void filterScheduleByBothAndNone() throws Exception {
        String shiftTypeFilterKey = "shifttype";
        HashMap<String, ArrayList<WebElement>> availableFilters = getAvailableFilters();
        ArrayList<WebElement> shiftTypeFilters = null;
        int bothSize = 0;
        int noneSize = 0;
        if (availableFilters.size() > 0) {
            shiftTypeFilters = availableFilters.get(shiftTypeFilterKey);
            unCheckFilters(shiftTypeFilters);
            waitForSeconds(2);
            if (areListElementVisible(wholeWeekShifts, 5)) {
                noneSize = wholeWeekShifts.size();
            }
            checkFilters(shiftTypeFilters);
            waitForSeconds(2);
            if (areListElementVisible(wholeWeekShifts, 5)) {
                bothSize = wholeWeekShifts.size();
            }
            if (noneSize != 0 && bothSize != 0 && noneSize == bothSize) {
                SimpleUtils.pass("Scheduled and open shifts are shown when applying both filters and none of them!");
            } else {
                SimpleUtils.fail("Applying both filters size is: " + bothSize + ", but applying none of them size is: " + noneSize
                        + ", they are inconsistent!", false);
            }
        } else {
            SimpleUtils.fail("Failed to get the available filters!", false);
        }
    }


    public void checkFilters(ArrayList<WebElement> filterElements) {
        if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
            click(filterButton);
        waitForSeconds(2);
        for (WebElement filterElement : filterElements) {
            WebElement filterCheckBox = filterElement.findElement(By.cssSelector("input[type=\"checkbox\"]"));
            String elementClasses = filterCheckBox.getAttribute("class").toLowerCase();
            if (elementClasses.contains("ng-empty"))
                click(filterElement);
        }
    }


    public void filterScheduleByJobTitleWeekView(ArrayList<WebElement> jobTitleFilters, ArrayList<String> availableJobTitleList) throws Exception{
        ScheduleShiftTablePage scheduleShiftTablePage = new ConsoleScheduleShiftTablePage();
        for (WebElement jobTitleFilter : jobTitleFilters) {
            Thread.sleep(1000);
            if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
                click(filterButton);
            unCheckFilters(jobTitleFilters);
            String jobTitle = jobTitleFilter.getText();
            SimpleUtils.report("Data for job title: '" + jobTitle + "' as bellow");
            clickTheElement(jobTitleFilter.findElement(By.cssSelector("input[type=\"checkbox\"]")));
            click(filterButton);
            String cardHoursAndWagesText = "";
            SmartCardPage smartCardPage = new ConsoleSmartCardPage();
            HashMap<String, Float> hoursAndWagesCardData = smartCardPage.getScheduleLabelHoursAndWages();
            for (Map.Entry<String, Float> hoursAndWages : hoursAndWagesCardData.entrySet()) {
                if (cardHoursAndWagesText != "")
                    cardHoursAndWagesText = cardHoursAndWagesText + ", " + hoursAndWages.getKey() + ": '" + hoursAndWages.getValue() + "'";
                else
                    cardHoursAndWagesText = hoursAndWages.getKey() + ": '" + hoursAndWages.getValue() + "'";
            }
            SimpleUtils.report("Active Week Card's Data: " + cardHoursAndWagesText);
            if (availableJobTitleList.contains(jobTitle.toLowerCase().trim())) {
                SimpleUtils.assertOnFail("Sum of Daily Schedule Hours not equal to Active Week Schedule Hours!",
                        scheduleShiftTablePage.newVerifyActiveWeekDailyScheduleHoursInWeekView(), true);
            }else
                SimpleUtils.report("there is no data for this job title: '" + jobTitle+ "'");
        }
    }



    public void filterScheduleByJobTitleDayView(ArrayList<WebElement> jobTitleFilters,ArrayList<String> availableJobTitleList) throws Exception{
        ScheduleShiftTablePage scheduleShiftTablePage = new ConsoleScheduleShiftTablePage();
        for (WebElement jobTitleFilter : jobTitleFilters) {
            if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
                click(filterButton);
            unCheckFilters(jobTitleFilters);
            String jobTitle = jobTitleFilter.getText();
            SimpleUtils.report("Data for job title: '" + jobTitle + "' as bellow");
            click(jobTitleFilter);
            click(filterButton);
            String cardHoursAndWagesText = "";
            SmartCardPage smartCardPage = new ConsoleSmartCardPage();
            HashMap<String, Float> hoursAndWagesCardData = smartCardPage.getScheduleLabelHoursAndWages();
            for (Map.Entry<String, Float> hoursAndWages : hoursAndWagesCardData.entrySet()) {
                if (cardHoursAndWagesText != "")
                    cardHoursAndWagesText = cardHoursAndWagesText + ", " + hoursAndWages.getKey() + ": '" + hoursAndWages.getValue() + "'";
                else
                    cardHoursAndWagesText = hoursAndWages.getKey() + ": '" + hoursAndWages.getValue() + "'";
            }
            SimpleUtils.report("Active Day Card's Data: " + cardHoursAndWagesText);
            float activeDayScheduleHoursOnCard = smartCardPage.getScheduleLabelHoursAndWages().get(ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.scheduledHours.getValue());
            float totalShiftsWorkTime = scheduleShiftTablePage.getActiveShiftHoursInDayView();
            SimpleUtils.report("Active Day Total Work Time Data: " + totalShiftsWorkTime);
            if (availableJobTitleList.contains(jobTitle.toLowerCase().trim())) {
                if (activeDayScheduleHoursOnCard - totalShiftsWorkTime <= 0.05) {
                    SimpleUtils.pass("Schedule Hours in smart card  equal to total Active Schedule Hours by job title filter ");
                }else
                    SimpleUtils.fail("the job tile filter hours not equal to schedule hours in schedule samrtcard",false);
            }else
                SimpleUtils.report( "there is no data for this job title: '" + jobTitle + "'");
        }
    }


    public void filterScheduleByJobTitle(boolean isWeekView) throws Exception{
        ScheduleShiftTablePage scheduleShiftTablePage = new ConsoleScheduleShiftTablePage();
        ArrayList<String> availableJobTitleList = new ArrayList<>();
        if (isWeekView == true) {
            availableJobTitleList = scheduleShiftTablePage.getAvailableJobTitleListInWeekView();
        }else
            availableJobTitleList = scheduleShiftTablePage.getAvailableJobTitleListInDayView();

        waitForSeconds(10);
        String jobTitleFilterKey = "jobtitle";
        HashMap<String, ArrayList<WebElement>> availableFilters = getAvailableFilters();
        if (availableFilters.size() > 1) {
            ArrayList<WebElement> jobTitleFilters = availableFilters.get(jobTitleFilterKey);
            if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
                click(filterButton);
            unCheckFilters(jobTitleFilters);
            if (isWeekView) {
                filterScheduleByJobTitleWeekView(jobTitleFilters, availableJobTitleList);
            }
            else {
                filterScheduleByJobTitleDayView(jobTitleFilters, availableJobTitleList);
            }
        } else {
            SimpleUtils.fail("Filters are not appears on Schedule page!", false);
        }
    }



    @Override
    public void filterScheduleByWorkRoleAndJobTitle(boolean isWeekView) throws Exception{
        ScheduleShiftTablePage scheduleShiftTablePage = new ConsoleScheduleShiftTablePage();
        waitForSeconds(10);
        ArrayList<String> availableJobTitleList = new ArrayList<>();
        if (isWeekView == true) {
            availableJobTitleList = scheduleShiftTablePage.getAvailableJobTitleListInWeekView();
        }else
            availableJobTitleList = scheduleShiftTablePage.getAvailableJobTitleListInDayView();

        String workRoleFilterKey = "workrole";
        String jobTitleFilterKey = "jobtitle";

        HashMap<String, ArrayList<WebElement>> availableFilters = getAvailableFilters();
        if (availableFilters.size() > 1) {
            ArrayList<WebElement> workRoleFilters = availableFilters.get(workRoleFilterKey);
            ArrayList<WebElement> jobTitleFilters = availableFilters.get(jobTitleFilterKey);
            for (WebElement workRoleFilter : workRoleFilters) {
                if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
                    click(filterButton);
                unCheckFilters(workRoleFilters);
                click(workRoleFilter.findElement(By.cssSelector("input[type=\"checkbox\"]")));
                SimpleUtils.report("Data for Work Role: '" + workRoleFilter.getText() + "'");
                if (isWeekView) {
                    filterScheduleByJobTitleWeekView(jobTitleFilters, availableJobTitleList);
                }else {
                    filterScheduleByJobTitleDayView(jobTitleFilters, availableJobTitleList);
                }
            }
        } else {
            SimpleUtils.fail("Filters are not appears on Schedule page!", false);
        }
    }
    @Override
    public void filterScheduleByShiftTypeAndJobTitle(boolean isWeekView) throws Exception{
        ScheduleShiftTablePage scheduleShiftTablePage = new ConsoleScheduleShiftTablePage();
        waitForSeconds(10);
        ArrayList<String> availableJobTitleList = new ArrayList<>();
        if (isWeekView == true) {
            availableJobTitleList = scheduleShiftTablePage.getAvailableJobTitleListInWeekView();
        }else
            availableJobTitleList = scheduleShiftTablePage.getAvailableJobTitleListInDayView();

        String shiftTypeFilterKey = "shifttype";
        String jobTitleFilterKey = "jobtitle";

        HashMap<String, ArrayList<WebElement>> availableFilters = getAvailableFilters();
        if (availableFilters.size() > 1) {
            ArrayList<WebElement> shiftTypeFilters = availableFilters.get(shiftTypeFilterKey);
            ArrayList<WebElement> jobTitleFilters = availableFilters.get(jobTitleFilterKey);
            for (WebElement shiftTypeFilter : shiftTypeFilters) {
                if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
                    click(filterButton);
                unCheckFilters(shiftTypeFilters);
                click(shiftTypeFilter);
                SimpleUtils.report("Data for Work Role: '" + shiftTypeFilter.getText() + "'");
                if (isWeekView) {
                    filterScheduleByJobTitleWeekView(jobTitleFilters, availableJobTitleList);
                }else {
                    filterScheduleByJobTitleDayView(jobTitleFilters, availableJobTitleList);
                }
            }
        } else {
            SimpleUtils.fail("Filters are not appears on Schedule page!", false);
        }
    }


    @Override
    public void selectLocationFilterByText(String filterText) throws Exception {
        String locationFilterKey = "location";
        ArrayList<WebElement> locationFilters = getAvailableFilters().get(locationFilterKey);
        unCheckFilters(locationFilters);
        for (WebElement locationOption : locationFilters) {
            if (locationOption.getText().toLowerCase().contains(filterText.toLowerCase())) {
                click(locationOption);
                break;
            }
        }
        if (!filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
            click(filterButton);
    }


    @Override
    public void selectAllChildLocationsToFilter() throws Exception {
        if (isElementLoaded(filterPopup,5)) {
            String locationFilterKey = "location";
            ArrayList<WebElement> locationFilters = getAvailableFilters().get(locationFilterKey);
            unCheckFilters(locationFilters);
            checkFilters(locationFilters);
        } else
            SimpleUtils.fail("Schedule Page: The drop down list does not pop up", false);
    }

    @FindBy(css = "div.rows")
    private List<WebElement> weekScheduleShiftsOfWeekView;
    @Override
    public void verifyAllChildLocationsShiftsLoadPerformance() throws Exception {
        if (isElementLoaded(filterPopup,5)) {
            String locationFilterKey = "location";
            ArrayList<WebElement> locationFilters = getAvailableFilters().get(locationFilterKey);
            unCheckFilters(locationFilters);
            for (WebElement locationOption: locationFilters) {
                click(locationOption);
                if (areListElementVisible(weekScheduleShiftsOfWeekView,3))
                    SimpleUtils.pass("Schedule Page: The performance target is < 3 seconds to load");
                else
                    SimpleUtils.fail("Schedule Page: The performance target is more than 3 seconds to load",false);
            }
        } else
            SimpleUtils.fail("Schedule Page: The drop down list does not pop up", false);
    }


    @FindBy(css = "[class=\"week-schedule-shift-title\"]")
    private List<WebElement> scheduleShiftTitles;
    @Override
    public void verifyShiftsDisplayThroughLocationFilter(String childLocation) throws Exception {
        String locationFilterKey = "location";
        boolean isChildLocationPresent = false;
        ArrayList<WebElement> locationFilters = getAvailableFilters().get(locationFilterKey);
        unCheckFilters(locationFilters);
        for (WebElement locationOption: locationFilters) {
            if (locationOption.getText().contains(childLocation)) {
                isChildLocationPresent = true;
                click(locationOption);
                if (areListElementVisible(scheduleShiftTitles,3)) {
                    for (WebElement title: scheduleShiftTitles) {
                        if (childLocation.toUpperCase().contains(title.getText())) {
                            SimpleUtils.pass("Schedule Page: The shifts change successfully according to the filter");
                            break;
                        } else
                            SimpleUtils.fail("Schedule Page: The shifts don't change according to the filter",false);
                    }
                } else
                    SimpleUtils.fail("Schedule Page: The shifts of the child location failed to load or loaded slowly",false);
                break;
            }
        }
        if (!isChildLocationPresent)
            SimpleUtils.fail("Schedule Page: The filtered child location does not exist",false);
        if (!filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
            click(filterButton);
    }


    @Override
    public void verifyChildLocationShiftsLoadPerformance(String childLocation) throws Exception {
        String locationFilterKey = "location";
        boolean isChildLocationPresent = false;
        ArrayList<WebElement> locationFilters = getAvailableFilters().get(locationFilterKey);
        unCheckFilters(locationFilters);
        for (WebElement locationOption: locationFilters) {
            if (locationOption.getText().contains(childLocation)) {
                isChildLocationPresent = true;
                click(locationOption);
                if (areListElementVisible(weekScheduleShiftsOfWeekView,3))
                    SimpleUtils.pass("Schedule Page: The performance target is < 3 seconds to load");
                else
                    SimpleUtils.fail("Schedule Page: The performance target is more than 3 seconds to load",false);
                break;
            }
        }
        if (!isChildLocationPresent)
            SimpleUtils.fail("Schedule Page: The filtered child location does not exist",false);
    }

    @Override
    public void selectChildLocationFilterByText(String location) throws Exception {
        String shiftTypeFilterKey = "location";
        ArrayList<WebElement> shiftTypeFilters = getAvailableFilters().get(shiftTypeFilterKey);
        unCheckFilters(shiftTypeFilters);
        for (WebElement shiftTypeOption : shiftTypeFilters) {
            if (shiftTypeOption.getText().toLowerCase().contains(location.toLowerCase())) {
                click(shiftTypeOption);
                break;
            }
        }
        if (!filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
            click(filterButton);
    }

}
