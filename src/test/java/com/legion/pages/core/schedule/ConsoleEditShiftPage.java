package com.legion.pages.core.schedule;

import com.legion.pages.BasePage;
import com.legion.pages.EditShiftPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleEditShiftPage extends BasePage implements EditShiftPage {
    public ConsoleEditShiftPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public enum sectionType {
        WorkRole("Work Role"),
        ShiftName("Shift Name"),
        Location("Location"),
        StartTime("Start Time"),
        EndTime("End Time"),
        Date("Date"),
        Assignment("Assignment"),
        ShiftNotes("Shift Notes");
        private final String type;
        sectionType(final String newType){
            type = newType;
        }
        public String getType(){
            return type;
        }
    }

    public enum twoOptions {
        AllowConflicts("allowConflicts"),
        AllowComplianceErrors("allowComplianceErrors");

        private final String option;
        twoOptions(final String specificOption) {
            option = specificOption;
        }
        public String getOption() {
            return option;
        }
    }

    public enum assignmentOptions {
        DoNotChangeAssignments("Do not change assignments"),
        OpenShift("Open Shift: Auto Offer to TMs"),
        AssignOrOffer("Assign or Offer to Specific TM's");

        private final String option;
        assignmentOptions(final String specificOption) {
            option = specificOption;
        }
        public String getOption() {
            return option;
        }
    }

    @FindBy (css = ".modal-content")
    private WebElement editShiftWindow;
    @FindBy (css = ".modal-instance-header-title")
    private WebElement windowTitle;
    @FindBy (css = ".sc-cvXFvT.kFidmJ")
    private WebElement subTitle;
    @FindBy (css = ".generate-modal-location")
    private WebElement locationInfo;
    @FindBy (css = ".tma-dismiss-button")
    private WebElement xButton;
    @FindBy (css = "[ng-click*=back]")
    private WebElement cancelButton;
    @FindBy (css = ".confirm")
    private WebElement updateButton;
    @FindBy (css = ".sc-elryUO.kmaJgm")
    private WebElement optionsSection;
    @FindBy (css = "[data-testid=ReplayIcon]")
    private WebElement clearEditedFieldsBtn;
    @FindBy (css = ".MuiGrid-container")
    private List<WebElement> gridContainers;
    @FindBy (css = ".react-select__option")
    private List<WebElement> dropDownListOnReact;
    @FindBy (css = ".MuiInputAdornment-positionEnd")
    private WebElement nextDayIcon;
    @FindBy (css="[role=tooltip] input")
    private WebElement nextDayPopup;
    @FindBy (name = "allowComplianceErrors")
    private WebElement allowComplianceOption;
    @FindBy (name = "allowConflicts")
    private WebElement allowConflictsOption;

    @Override
    public boolean isEditShiftWindowLoaded() throws Exception {
        waitForSeconds(5);
        if (isElementLoaded(editShiftWindow, 5) && areListElementVisible(gridContainers, 10)) {
            return true;
        }
        return false;
    }

    @Override
    public void verifyTheTitleOfEditShiftsWindow(int selectedShiftCount, String startOfWeek) throws Exception {
        String expectedTitle = "Edit " + selectedShiftCount + " Shift" + (selectedShiftCount == 0 ? "" : "s") + ": Week of "
                + startOfWeek;
        if (isElementLoaded(windowTitle, 5) && windowTitle.getText().trim().equalsIgnoreCase(expectedTitle)) {
            SimpleUtils.pass("The title of the Edit Shift window is correct: " + expectedTitle);
        } else {
            SimpleUtils.fail("The title of the Edit Shift window is incorrect!", false);
        }
    }

    @Override
    public void verifySelectedWorkDays(int selectedShitCount, List<String> selectedDays) throws Exception {
        String selectedDaysString = "";
        if (selectedDays.size() == 2) {
            if (selectedDays.get(0) == selectedDays.get(1)) {
                selectedDaysString = selectedDays.get(0);
            } else {
               selectedDaysString = selectedDays.toString().substring(selectedDays.toString().indexOf("[") + 1, selectedDays.toString().indexOf("]"));
            }
        } else if (selectedDays.size() == 1) {
            selectedDaysString = selectedDays.get(0);
        }
        String expectedSubTitle = "SHIFTS SELECTED: " + selectedShitCount + "\n" + "Days: " + selectedDaysString;
        if (isElementLoaded(subTitle, 3) && subTitle.getText().equalsIgnoreCase(expectedSubTitle)) {
            SimpleUtils.pass("The sub title of the Edit Shift window is correct: " + expectedSubTitle);
        } else {
            SimpleUtils.fail("The sub title of the Edit Shift window is incorrect!, Actual: '" + subTitle.getText()
                    + "', Expected: '" + expectedSubTitle + "'", false);
        }
    }

    @Override
    public void verifyLocationNameShowsCorrectly(String locationName) throws Exception {
        if (isElementLoaded(locationInfo, 3) && locationInfo.getText().trim().equalsIgnoreCase(locationName)) {
            SimpleUtils.pass("Location Name shows on the Edit Shift window!");
        } else {
            SimpleUtils.fail("Location Name is incorrect on Edit Shift window!", false);
        }
    }

    @Override
    public void verifyTheVisibilityOfButtons() throws Exception {
        if (isElementLoaded(xButton, 3) && isElementLoaded(cancelButton, 3) && isElementLoaded(updateButton)) {
            SimpleUtils.pass("x, CANCEL, UPDATE buttons loaded successfully on Edit Shift Window!");
        } else {
            SimpleUtils.fail("x, CANCEL, UPDATE buttons failed to load on Edit Shift Window!", false);
        }
    }

    @Override
    public void verifyTheContentOfOptionsSection() throws Exception {
        if (isElementLoaded(optionsSection, 3) && optionsSection.getText().contains(
                "Your bulk edit may result in some shifts incurring a compliance violation or scheduling conflict.") &&
        optionsSection.getText().contains("Allow shift edits that result in scheduling conflicts. Existing shifts will convert to open.")
        && optionsSection.getText().contains("Allow shift edits that result in compliance violations such as overtime.")) {
            SimpleUtils.pass("The content on options section is correct!");
        } else {
            SimpleUtils.fail("The content on options section is incorrect!", false);
        }
    }

    @Override
    public boolean isClearEditedFieldsBtnLoaded() throws Exception {
        if (isElementLoaded(clearEditedFieldsBtn, 3)) {
            return true;
        }
        return false;
    }

    @Override
    public void verifyThreeColumns() throws Exception {
        if (areListElementVisible(gridContainers, 3) && gridContainers.size() > 0) {
            List<WebElement> columns = gridContainers.get(0).findElements(By.cssSelector(".MuiTypography-root"));
            if (columns.size() == 3 && columns.get(0).getText().equals("Value") && columns.get(1).getText().equals("Current")
            && columns.get(2).getText().equals("Edited")) {
                SimpleUtils.pass("The columns are correct: Value, Current and Edited");
            } else {
                SimpleUtils.fail("The columns on Edit Shift window is incorrect!", false);
            }
        } else {
            SimpleUtils.fail("The content failed to load on Edit Shift window!", false);
        }
    }

    @Override
    public void verifyEditableTypesShowOnShiftDetail() throws Exception {
        if (areListElementVisible(gridContainers, 3)) {
            if (gridContainers.size() == 8) {
                if (gridContainers.get(1).findElement(By.cssSelector(".MuiGrid-item:nth-child(1)")).getText().equals("Work Role")
                && gridContainers.get(2).findElement(By.cssSelector(".MuiGrid-item:nth-child(1)")).getText().equals("Shift Name")
                && gridContainers.get(3).findElement(By.cssSelector(".MuiGrid-item:nth-child(1)")).getText().equals("Start Time")
                && gridContainers.get(4).findElement(By.cssSelector(".MuiGrid-item:nth-child(1)")).getText().equals("End Time")
                && gridContainers.get(5).findElement(By.cssSelector(".MuiGrid-item:nth-child(1)")).getText().equals("Date")
                && gridContainers.get(6).findElement(By.cssSelector(".MuiGrid-item:nth-child(1)")).getText().equals("Assignment")
                && gridContainers.get(7).findElement(By.cssSelector(".MuiGrid-item:nth-child(1)")).getText().equals("Shift Notes")) {
                    SimpleUtils.pass("'Work Role', 'Shift Name', 'Start Time', 'End Time', 'Date', 'Assignment', 'Shift Notes' " +
                            "sections are loaded successfully!");
                } else {
                    SimpleUtils.fail("Sections on Shift Details is incorrect!", false);
                }
            } else if (gridContainers.size() == 9) {
                if (gridContainers.get(1).findElement(By.cssSelector(".MuiGrid-item:nth-child(1)")).getText().equals("Work Role")
                        && gridContainers.get(2).findElement(By.cssSelector(".MuiGrid-item:nth-child(1)")).getText().equals("Shift Name")
                        && gridContainers.get(3).findElement(By.cssSelector(".MuiGrid-item:nth-child(1)")).getText().equals("Location")
                        && gridContainers.get(4).findElement(By.cssSelector(".MuiGrid-item:nth-child(1)")).getText().equals("Start Time")
                        && gridContainers.get(5).findElement(By.cssSelector(".MuiGrid-item:nth-child(1)")).getText().equals("End Time")
                        && gridContainers.get(6).findElement(By.cssSelector(".MuiGrid-item:nth-child(1)")).getText().equals("Date")
                        && gridContainers.get(7).findElement(By.cssSelector(".MuiGrid-item:nth-child(1)")).getText().equals("Assignment")
                        && gridContainers.get(8).findElement(By.cssSelector(".MuiGrid-item:nth-child(1)")).getText().equals("Shift Notes")) {
                    SimpleUtils.pass("'Work Role', 'Shift Name', 'Location', 'Start Time', 'End Time', 'Date', 'Assignment', 'Shift Notes' " +
                            "sections are loaded successfully for Location Group!");
                } else {
                    SimpleUtils.fail("Sections on Shift Details is incorrect!", false);
                }
            }
        } else {
            SimpleUtils.fail("Shift Details section failed to load!", false);
        }
    }

    @Override
    public void clickOnXButton() throws Exception {
        if (isElementLoaded(xButton, 1)) {
            clickTheElement(xButton);
        } else {
            SimpleUtils.fail("X button failed to load!", false);
        }
    }

    @Override
    public void clickOnCancelButton() throws Exception {
        if (isElementLoaded(cancelButton, 1)) {
            clickTheElement(cancelButton);
        } else {
            SimpleUtils.fail("Cancel button failed to load!", false);
        }
    }

    @Override
    public void verifyTheTextInCurrentColumn(String type, String value) throws Exception {
        WebElement gridRow = null;
        if (areListElementVisible(gridContainers, 3)) {
            if (gridContainers.size() == 8) {
                if (type.equals(sectionType.WorkRole.getType())) {
                    gridRow = gridContainers.get(1);
                } else if (type.equals(sectionType.ShiftName.getType())) {
                    gridRow = gridContainers.get(2);
                } else if (type.equals(sectionType.StartTime.getType())) {
                    gridRow = gridContainers.get(3);
                } else if (type.equals(sectionType.EndTime.getType())) {
                    gridRow = gridContainers.get(4);
                } else if (type.equals(sectionType.Date.getType())) {
                    gridRow = gridContainers.get(5);
                } else if (type.equals(sectionType.Assignment.getType())) {
                    gridRow = gridContainers.get(6);
                } else if (type.equals(sectionType.ShiftNotes.getType())) {
                    gridRow = gridContainers.get(7);
                }
            } else if (gridContainers.size() == 9) {
                if (type.equals(sectionType.WorkRole.getType())) {
                    gridRow = gridContainers.get(1);
                } else if (type.equals(sectionType.Location.getType())) {
                    gridRow = gridContainers.get(2);
                } else if (type.equals(sectionType.ShiftName.getType())) {
                    gridRow = gridContainers.get(3);
                } else if (type.equals(sectionType.StartTime.getType())) {
                    gridRow = gridContainers.get(4);
                } else if (type.equals(sectionType.EndTime.getType())) {
                    gridRow = gridContainers.get(5);
                } else if (type.equals(sectionType.Date.getType())) {
                    gridRow = gridContainers.get(6);
                } else if (type.equals(sectionType.Assignment.getType())) {
                    gridRow = gridContainers.get(7);
                } else if (type.equals(sectionType.ShiftNotes.getType())) {
                    gridRow = gridContainers.get(8);
                }
            }
            if (gridRow.findElement(By.cssSelector(".MuiGrid-item:nth-child(2)")).getText().trim().equalsIgnoreCase(value)) {
                SimpleUtils.pass("Verified for " + type + ", the value is correct!");
            } else {
                SimpleUtils.fail("Verified for " + type + ", the value is incorrect! Expected: " + value +
                        ", But actual is: " + gridRow.findElement(By.cssSelector(".MuiGrid-item:nth-child(2)")).getText().trim(), false);
            }
        } else {
            SimpleUtils.fail("Shift Details section failed to load!", false);
        }
    }

    @Override
    public List<String> getOptionsFromSpecificSelect() throws Exception {
        List<String> options = new ArrayList<>();
        if (areListElementVisible(dropDownListOnReact, 5)) {
            for (WebElement option : dropDownListOnReact) {
                options.add(option.getText());
            }
        } else {
            SimpleUtils.fail("Options failed to load!", false);
        }
        return options;
    }

    @Override
    public void clickOnDateSelect() throws Exception {
        WebElement dateSection = getSpecificElementByTypeAndColumn(sectionType.Date.getType(), "Edited");
        scrollToElement(dateSection);
        waitForSeconds(1);
        if (isElementLoaded(dateSection, 5)) {
            moveToElementAndClick(dateSection.findElement(By.cssSelector(".react-select__value-container")));
            if (dropDownListOnReact.size() > 0) {
                SimpleUtils.pass("Click on Date select successfully!");
            } else {
                SimpleUtils.fail("Failed to click on date select!", false);
            }
        } else {
            SimpleUtils.fail("Date section on Edited column failed to load!", false);
        }
    }

    @Override
    public void clickOnWorkRoleSelect() throws Exception {
        WebElement workRoleSection = getSpecificElementByTypeAndColumn(sectionType.WorkRole.getType(), "Edited");
        if (isElementLoaded(workRoleSection, 5)) {
            moveToElementAndClick(workRoleSection.findElement(By.cssSelector(".react-select__indicators")));
        } else {
            SimpleUtils.fail("Work Role section on Edited column failed to load!", false);
        }
    }

    @Override
    public void clickOnLocationSelect() throws Exception {
        WebElement locationSection = getSpecificElementByTypeAndColumn(sectionType.Location.getType(), "Edited");
        if (isElementLoaded(locationSection, 5)) {
            moveToElementAndClick(locationSection.findElement(By.cssSelector(".react-select__indicators")));
        } else {
            SimpleUtils.fail("Location section on Edited column failed to load!", false);
        }
    }

    @Override
    public void clickOnAssignmentSelect() throws Exception {
        WebElement assignmentSection = getSpecificElementByTypeAndColumn(sectionType.Assignment.getType(), "Edited");
        if (isElementLoaded(assignmentSection, 5)) {
            moveToElementAndClick(assignmentSection.findElement(By.cssSelector(".react-select__indicators")));
        } else {
            SimpleUtils.fail("Assignment section on Edited column failed to load!", false);
        }
    }

    @Override
    public void selectSpecificOptionByText(String text) throws Exception {
        selectOptionByLabel(text);
    }

    @Override
    public String getSelectedWorkRole() throws Exception {
        String selectedWorkRole = "";
        WebElement editedWorkRoleSection = getSpecificElementByTypeAndColumn(sectionType.WorkRole.getType(), "Edited");
        if (editedWorkRoleSection != null) {
            selectedWorkRole = editedWorkRoleSection.getText();
        }
        return selectedWorkRole;
    }

    @Override
    public String getSelectedDate() throws Exception {
        String selectedDate = "";
        WebElement editedDateSection = getSpecificElementByTypeAndColumn(sectionType.Date.getType(), "Edited");
        if (editedDateSection != null) {
            selectedDate = editedDateSection.getText();
        }
        return selectedDate;
    }

    @Override
    public String getSelectedAssignment() throws Exception {
        String assignment = "";
        WebElement assignmentSection = getSpecificElementByTypeAndColumn(sectionType.Assignment.getType(), "Edited");
        if (assignmentSection != null) {
            assignment = assignmentSection.getText();
        }
        return assignment;
    }

    @Override
    public void clickOnClearEditedFieldsButton() throws Exception {
        clickTheElement(clearEditedFieldsBtn.findElement(By.xpath("./..")));
    }

    @Override
    public void clickOnUpdateButton() throws Exception {
        waitForSeconds(2);
        if (isElementLoaded(updateButton, 5)) {
            clickTheElement(updateButton);
        }
    }

    @Override
    public void inputShiftName(String shiftName) throws Exception {
        WebElement shiftNameInputSection = getSpecificElementByTypeAndColumn(sectionType.ShiftName.getType(), "Edited");
        WebElement input = shiftNameInputSection.findElement(By.cssSelector("[placeholder=\"Shift Name (Optional)\"]"));
        input.clear();
        input.sendKeys(shiftName);
        if (input.getAttribute("value").equals(shiftName)) {
            SimpleUtils.pass("Input the string in Shift Name successfully!");
        } else {
            SimpleUtils.fail("Input the string in Shift Name failed!", false);
        }
    }

    @Override
    public void inputShiftNotes(String shiftNote) throws Exception {
        WebElement shiftNotesSection = getSpecificElementByTypeAndColumn(sectionType.ShiftNotes.getType(), "Edited");
        WebElement input = shiftNotesSection.findElement(By.name("notes"));
        input.clear();
        input.sendKeys(shiftNote);
        if (input.getAttribute("value").equals(shiftNote)) {
            SimpleUtils.pass("Input the string in Shift Notes successfully!");
        } else {
            SimpleUtils.fail("Input the string in Shift Notes failed!", false);
        }
    }

    @Override
    public void inputStartOrEndTime(String time, boolean isStartTime) throws Exception {
        WebElement timeSection = null;
        if (isStartTime) {
            timeSection = getSpecificElementByTypeAndColumn(sectionType.StartTime.getType(), "Edited");
        } else {
            timeSection = getSpecificElementByTypeAndColumn(sectionType.EndTime.getType(), "Edited");
        }
        WebElement input = timeSection.findElement(By.cssSelector("[placeholder*=\"Time\"]"));
        input.click();
        input.sendKeys(Keys.CONTROL, "a");
        input.sendKeys(Keys.DELETE);
        input.sendKeys(time);
//        waitForSeconds(15);
        WebElement shiftNameInputSection = getSpecificElementByTypeAndColumn(sectionType.ShiftName.getType(), "Edited");
        WebElement shiftNameInput = shiftNameInputSection.findElement(By.cssSelector("[placeholder=\"Shift Name (Optional)\"]"));
        shiftNameInput.click();
        if (input.getAttribute("value").toLowerCase().contains(time.toLowerCase())) {
            SimpleUtils.pass("Input the string in Time successfully!");
        } else {
            SimpleUtils.fail("Input the string in Time failed!", false);
        }
    }

    @Override
    public void checkUseOffset(boolean isStartTimeSection, boolean check) throws Exception {
        WebElement timeSection = null;
        if (isStartTimeSection) {
            timeSection = getSpecificElementByTypeAndColumn(sectionType.StartTime.getType(), "Edited");
        } else {
            timeSection = getSpecificElementByTypeAndColumn(sectionType.EndTime.getType(), "Edited");
        }
        WebElement checkbox = timeSection.findElement(By.cssSelector("[type=checkbox]"));
        WebElement parent = checkbox.findElement(By.xpath("./../.."));
        if (check) {
            if (!parent.getAttribute("class").contains("Mui-checked")) {
                clickTheElement(checkbox);
                if (areListElementVisible(timeSection.findElements(By.cssSelector(".MuiFormControl-root")), 3) &&
                        isElementLoaded(timeSection.findElement(By.cssSelector(".react-select__control")), 3)) {
                    SimpleUtils.pass("Check on Use Offset button successfully!");
                } else {
                    SimpleUtils.fail("Failed to check on Use Offset button!", false);
                }
            }
        } else {
            if (parent.getAttribute("class").contains("Mui-checked")) {
                clickTheElement(checkbox);
                if (isElementLoaded(timeSection.findElement(By.cssSelector("[placeholder*=\"Time\"]")), 3)) {
                    SimpleUtils.pass("Uncheck on Use Offset button successfully!");
                } else {
                    SimpleUtils.fail("Failed to uncheck on Use Offset button!", false);
                }
            }
        }
    }

    @Override
    public void verifyTheFunctionalityOfOffsetTime(String hours, String mins, String earlyOrLate, boolean isStartTimeSection) throws Exception {
        WebElement timeSection = null;
        if (isStartTimeSection) {
            timeSection = getSpecificElementByTypeAndColumn(sectionType.StartTime.getType(), "Edited");
        } else {
            timeSection = getSpecificElementByTypeAndColumn(sectionType.EndTime.getType(), "Edited");
        }
        WebElement hoursInput = timeSection.findElements(By.cssSelector("[type=number]")).get(0);
        WebElement minsInput = timeSection.findElements(By.cssSelector("[type=number]")).get(1);
        WebElement select = timeSection.findElement(By.cssSelector(".react-select__dropdown-indicator"));

        hoursInput.click();
        for (int i = 0; i < 4; i++) {
            hoursInput.sendKeys(Keys.BACK_SPACE);
        }
        minsInput.click();
        for (int i = 0; i < 4; i++) {
            minsInput.sendKeys(Keys.BACK_SPACE);
        }
        if (hours != null && !hours.isEmpty()) {
            hoursInput.sendKeys(hours);
            if (Integer.parseInt(hours) >= 12) {
                WebElement warning = timeSection.findElement(By.cssSelector(".MuiFormHelperText-root"));
                SimpleUtils.assertOnFail("Warning message 'Max 12 Hrs' failed to show!'", warning.getText()
                        .trim().equalsIgnoreCase("Max 12 Hrs"), false);
            }
        }
        if (mins != null && !mins.isEmpty()) {
            minsInput.sendKeys(mins);
            if (Integer.parseInt(mins) != 0 && Integer.parseInt(mins) != 15 && Integer.parseInt(mins) != 45 && Integer.parseInt(mins) != 60) {
                hoursInput.click();
                hoursInput.clear();
                WebElement warning = timeSection.findElement(By.cssSelector(".MuiFormHelperText-root"));
                SimpleUtils.assertOnFail("Warning message 'Must match slots' failed to show!'", warning.getText()
                        .trim().equalsIgnoreCase("Must match slots"), false);
            }
        }
        if (earlyOrLate != null && !earlyOrLate.isEmpty()) {
            clickTheElement(select);
            selectOptionByLabel(earlyOrLate);
        }
    }

    @Override
    public void checkOrUnCheckNextDayOnBulkEditPage(boolean isCheck) throws Exception {
        if (isCheck) {
            if (isElementLoaded(nextDayIcon, 10)) {
                if (nextDayIcon.findElement(By.tagName("svg")).getAttribute("height").equals("16")) {
                    moveElement(nextDayIcon, 0);
                    moveToElementAndClick(nextDayPopup);
                    if (nextDayIcon.findElement(By.tagName("svg")).getAttribute("height").equals("8")) {
                        SimpleUtils.pass("The next day checkbox been checked successfully! ");
                    } else
                        SimpleUtils.fail("The next day checkbox been checked fail! ", false);
                } else
                    SimpleUtils.pass("The next day checkbox already checked! ");
            } else
                SimpleUtils.fail("The next day img fail to load! ", false);

        } else {
            if (isElementLoaded(nextDayIcon, 10)) {
                if (nextDayIcon.findElement(By.tagName("svg")).getAttribute("height").equals("8")) {
                    moveElement(nextDayIcon, 0);
                    clickTheElement(nextDayPopup);
                    if (nextDayIcon.findElement(By.tagName("svg")).getAttribute("height").equals("16")) {
                        SimpleUtils.pass("The next day checkbox been unchecked successfully! ");
                    } else
                        SimpleUtils.fail("The next day checkbox been unchecked fail! ", false);
                } else
                    SimpleUtils.pass("The next day checkbox already unchecked! ");
            } else
                SimpleUtils.report("The next day img is not loaded! ");
        }
    }

    @Override
    public void checkOrUncheckOptionsByName(String optionName, boolean isCheck) throws Exception {
        WebElement option = null;
        if (optionName.equals(twoOptions.AllowConflicts.getOption())) {
            option = allowConflictsOption;
        } else {
            option = allowComplianceOption;
        }
        WebElement parent = option.findElement(By.xpath("./../.."));
        if (isCheck) {
            if (parent != null) {
                if (!parent.getAttribute("class").contains("Mui-checked")) {
                    clickTheElement(option);
                    if (parent.getAttribute("class").contains("Mui-checked")) {
                        SimpleUtils.pass("Check the option successfully!");
                    } else {
                        SimpleUtils.fail("Failed to check the option!", false);
                    }
                } else {
                    SimpleUtils.report("The option is already checked!");
                }
            }
        } else {
            if (parent != null) {
                if (parent.getAttribute("class").contains("Mui-checked")) {
                    clickTheElement(option);
                    if (!parent.getAttribute("class").contains("Mui-checked")) {
                        SimpleUtils.pass("UnCheck the option successfully!");
                    } else {
                        SimpleUtils.fail("Failed to uncheck the option!", false);
                    }
                } else {
                    SimpleUtils.report("The option is already unchecked!");
                }
            }
        }
    }

    private WebElement getSpecificElementByTypeAndColumn(String type, String column) throws Exception {
        WebElement element = null;
        String selector = "";
        if (column.equals("Value")) {
            selector = ".MuiGrid-item:nth-child(1)";
        } else if (column.equals("Current")) {
            selector = ".MuiGrid-item:nth-child(2)";
        } else if (column.equals("Edited")) {
            selector = ".MuiGrid-item:nth-child(3)";
        }
        if (areListElementVisible(gridContainers, 3)) {
            if (gridContainers.size() == 8) {
                if (type.equals(sectionType.WorkRole.getType())) {
                    element = gridContainers.get(1).findElement(By.cssSelector(selector));
                } else if (type.equals(sectionType.ShiftName.getType())) {
                    element = gridContainers.get(2).findElement(By.cssSelector(selector));
                } else if (type.equals(sectionType.StartTime.getType())) {
                    element = gridContainers.get(3).findElement(By.cssSelector(selector));
                } else if (type.equals(sectionType.EndTime.getType())) {
                    element = gridContainers.get(4).findElement(By.cssSelector(selector));
                } else if (type.equals(sectionType.Date.getType())) {
                    element = gridContainers.get(5).findElement(By.cssSelector(selector));
                } else if (type.equals(sectionType.Assignment.getType())) {
                    element = gridContainers.get(6).findElement(By.cssSelector(selector));
                } else if (type.equals(sectionType.ShiftNotes.getType())) {
                    element = gridContainers.get(7).findElement(By.cssSelector(selector));
                }
            } else if (gridContainers.size() == 9) {
                if (type.equals(sectionType.WorkRole.getType())) {
                    element = gridContainers.get(1).findElement(By.cssSelector(selector));
                } else if (type.equals(sectionType.ShiftName.getType())) {
                    element = gridContainers.get(2).findElement(By.cssSelector(selector));
                } else if (type.equals(sectionType.Location.getType())) {
                    element = gridContainers.get(3).findElement(By.cssSelector(selector));
                } else if (type.equals(sectionType.StartTime.getType())) {
                    element = gridContainers.get(4).findElement(By.cssSelector(selector));
                } else if (type.equals(sectionType.EndTime.getType())) {
                    element = gridContainers.get(5).findElement(By.cssSelector(selector));
                } else if (type.equals(sectionType.Date.getType())) {
                    element = gridContainers.get(6).findElement(By.cssSelector(selector));
                } else if (type.equals(sectionType.Assignment.getType())) {
                    element = gridContainers.get(7).findElement(By.cssSelector(selector));
                } else if (type.equals(sectionType.ShiftNotes.getType())) {
                    element = gridContainers.get(8).findElement(By.cssSelector(selector));
                }
            }
        } else {
            SimpleUtils.fail("Shift Details section failed to load!", false);
        }
        return element;
    }
}
