package com.legion.pages.core.schedule;

import com.legion.pages.BasePage;
import com.legion.pages.EditShiftPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleEditShiftPage extends BasePage implements EditShiftPage {
    public ConsoleEditShiftPage() {
        PageFactory.initElements(getDriver(), this);
    }

    @FindBy (css = ".modal-content")
    private WebElement editShiftWindow;
    @FindBy (css = ".modal-instance-header-title")
    private WebElement windowTitle;
    @FindBy (css = ".sc-lgvKYA")
    private WebElement subTitle;
    @FindBy (css = ".generate-modal-location")
    private WebElement locationInfo;
    @FindBy (css = ".tma-dismiss-button")
    private WebElement xButton;
    @FindBy (css = "[ng-click*=back]")
    private WebElement cancelButton;
    @FindBy (css = ".confirm")
    private WebElement updateButton;
    @FindBy (css = ".sc-cYvgmZ.hKLEOW")
    private WebElement optionsSection;
    @FindBy (css = "[data-testid=ReplayIcon]")
    private WebElement clearEditedFieldsBtn;
    @FindBy (css = ".MuiGrid-container")
    private List<WebElement> gridContainers;

    @Override
    public boolean isEditShiftWindowLoaded() throws Exception {
        waitForSeconds(3);
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
        String expectedSubTitle = "SHIFTS SELECTED: " + selectedShitCount + "\n" + "Days: " +
                selectedDays.toString().substring(selectedDays.toString().indexOf("[") + 1, selectedDays.toString().indexOf("]"));
        if (isElementLoaded(subTitle, 3) && subTitle.getText().equalsIgnoreCase(expectedSubTitle)) {
            SimpleUtils.pass("The sub title of the Edit Shift window is correct: " + expectedSubTitle);
        } else {
            SimpleUtils.fail("The sub title of the Edit Shift window is incorrect!", false);
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
}
