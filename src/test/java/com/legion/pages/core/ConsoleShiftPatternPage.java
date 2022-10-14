package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.ShiftPatternPage;
import com.legion.utils.SimpleUtils;
import cucumber.api.java.ro.Si;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleShiftPatternPage extends BasePage implements ShiftPatternPage {

    public ConsoleShiftPatternPage() {
        PageFactory.initElements(getDriver(), this);
    }

    @FindBy (css = "content-box:nth-child(1) .lg-sub-content-box-title")
    private WebElement shiftPatternDetailsTitle;
    @FindBy (css = "[aria-label=Role]")
    private WebElement roleInput;
    @FindBy (css = "[aria-label=Name]")
    private WebElement nameInput;
    @FindBy (css = "[aria-label=Description]")
    private WebElement descriptionInput;
    @FindBy (css = "[aria-label=\"No. of instances\"]")
    private WebElement instanceInput;
    @FindBy (css = "[label=\"Start Date\"] span")
    private WebElement selectStartDateButton;
    @FindBy (css = ".add-week-btn")
    private WebElement addWeekButton;
    @FindBy (css = "content-box:nth-child(3) .lg-sub-content-box-title")
    private WebElement badgesTitle;
    @FindBy (css = ".badges-edit-wrapper .buttonLabel")
    private List<WebElement> badgesButtons;
    @FindBy (css = ".current-day")
    private WebElement currentDay;
    @FindBy (css = ".lg-modal [type=submit]")
    private WebElement okBtnOnSelectStartDate;
    @FindBy (css = "[label=\"Start Date\"] span:nth-child(2)")
    private WebElement selectedDateLabel;
    @FindBy (css = ".week-type-dropdown-menu li")
    private List<WebElement> onOffWeekOptions;
    @FindBy (css = ".week-title")
    private List<WebElement> weekTitles;
    @FindBy (css = ".auto-schedule input")
    private WebElement autoScheduleCheckbox;
    @FindBy (css = "[label=\"Auto schedule team members during off weeks\"]>label")
    private WebElement autoScheduleLabel;
    @FindBy (css = ".auto-schedule-title")
    private WebElement autoScheduleTitle;
    @FindBy (css = ".lg-checkbox-group input")
    private List<WebElement> eachDayCheckboxes;
    @FindBy (css = ".lg-checkbox-group input-field>label")
    private List<WebElement> eachDayLabels;
    @FindBy (css =".settings-shift-pattern-week-details-edit-expanded")
    private List<WebElement> expandedIcons;
    @FindBy (css = ".settings-shift-pattern-week-details-edit-collapsed")
    private List<WebElement> collapsedIcons;
    @FindBy (css = ".fa-times")
    private List<WebElement> deleteWeekBtns;
    @FindBy (css = ".fa-chevron-down")
    private List<WebElement> chevronDownBtns;
    @FindBy (css = ".fa-chevron-up")
    private List<WebElement> chevronUpBtns;
    @FindBy (css = "[ng-click*=\"addOrEditShift\"] span")
    private List<WebElement> addShiftBtns;
    @FindBy (css = "[modal-title=\"Create New Shift\"]")
    private WebElement createNewShiftWindow;

    @Override
    public void verifyTheContentOnShiftPatternDetails(String workRole) throws Exception {
        if (isElementLoaded(shiftPatternDetailsTitle, 3) && shiftPatternDetailsTitle.getText().equalsIgnoreCase("Shift Pattern Details")
        && isElementLoaded(roleInput, 3) && roleInput.getAttribute("value").equalsIgnoreCase(workRole) &&
        isElementLoaded(nameInput, 3) && isElementLoaded(descriptionInput, 3) && isElementLoaded(instanceInput, 3)
        && isElementLoaded(selectStartDateButton, 3) && isElementLoaded(addWeekButton, 3) &&
        isElementLoaded(badgesTitle, 3) && badgesTitle.getText().equalsIgnoreCase("Badges") &&
        areListElementVisible(badgesButtons)) {
            SimpleUtils.pass("The content on Shift Pattern details loaded successfully!");
        } else {
            SimpleUtils.fail("The content on Shift Pattern details are incorect!", false);
        }
    }

    @Override
    public void verifyTheMandatoryFields() throws Exception {
        if (nameInput.getAttribute("required") != null && instanceInput.getAttribute("required") != null &&
                nameInput.getAttribute("required").equalsIgnoreCase("true") && instanceInput.getAttribute("required")
        .equalsIgnoreCase("true")) {
            SimpleUtils.pass("Name and No. of Instances are mandatory fields!");
        } else {
            SimpleUtils.fail("Name and No. of Instances should be the mandatory fields!", false);
        }
    }

    @Override
    public void inputNameDescriptionNInstances(String name, String description, String instances) throws Exception {
        if (name != null && !name.isEmpty()) {
            nameInput.sendKeys(name);
            if (nameInput.getAttribute("value").equalsIgnoreCase(name)) {
                SimpleUtils.pass("Input name successfully!");
            } else {
                SimpleUtils.fail("Failed to input the name!", false);
            }
        }
        if (description != null && !description.isEmpty()) {
            descriptionInput.sendKeys(description);
            if (descriptionInput.getAttribute("value").equalsIgnoreCase(description)) {
                SimpleUtils.pass("Input Description successfully!");
            } else {
                SimpleUtils.fail("Failed to input the Description!", false);
            }
        }
        if (instances != null && !instances.isEmpty()) {
            instanceInput.sendKeys(instances);
            if (instanceInput.getAttribute("value").equalsIgnoreCase(instances)) {
                SimpleUtils.pass("Input instances successfully!");
            } else {
                SimpleUtils.fail("Failed to input the instances!", false);
            }
        }
    }

    @Override
    public String selectTheCurrentWeek() throws Exception {
        String selectedFirstDayOfWeek = "";
        clickTheElement(selectStartDateButton);
        if (isElementLoaded(currentDay, 3) && isElementLoaded(okBtnOnSelectStartDate, 3)) {
            clickTheElement(currentDay);
            clickTheElement(okBtnOnSelectStartDate);
        }
        if (isElementLoaded(selectedDateLabel, 5)) {
            selectedFirstDayOfWeek = selectedDateLabel.getText();
            SimpleUtils.report("Selected the first day of week: " + selectedFirstDayOfWeek + " successfully!");
        }
        return selectedFirstDayOfWeek;
    }

    @Override
    public void selectAddOnOrOffWeek(boolean isOn) throws Exception {
        if (isElementLoaded(addWeekButton, 3)) {
            clickTheElement(addWeekButton);
            if (areListElementVisible(onOffWeekOptions, 3)) {
                if (isOn) {
                    clickTheElement(onOffWeekOptions.get(0));
                } else {
                    clickTheElement(onOffWeekOptions.get(1));
                }
                if (areListElementVisible(weekTitles, 3)) {
                    SimpleUtils.pass("Select the week option successfully!");
                } else {
                    SimpleUtils.fail("Failed to click on the week option!", false);
                }
            } else {
                SimpleUtils.fail("On Week/Off Week options not loaded!", false);
            }
        } else {
            SimpleUtils.fail("+ Add Week button failed to load!", false);
        }
    }

    @Override
    public void verifyTheContentOnOffWeekSection() throws Exception {
        if (isElementLoaded(autoScheduleCheckbox, 3) && isElementLoaded(autoScheduleLabel, 3) &&
        autoScheduleLabel.getText().equalsIgnoreCase("Auto schedule team members during off weeks")) {
            SimpleUtils.pass("The content on Off Week section is correct!");
        } else {
            SimpleUtils.fail("The content on Off Week section is incorrect!", false);
        }
    }

    @Override
    public void verifyTheContentOnOnWeekSection() throws Exception {
        if (areListElementVisible(expandedIcons, 3) && areListElementVisible(chevronUpBtns, 3) &&
        areListElementVisible(deleteWeekBtns, 3) && areListElementVisible(addShiftBtns, 3)) {
            SimpleUtils.pass("The content on On Week section is correct!");
        } else {
            SimpleUtils.fail("The content on On Week section is incorrect!", false);
        }
    }

    @Override
    public void checkOrUnCheckAutoSchedule(boolean isCheck) throws Exception {
        if (isElementLoaded(autoScheduleCheckbox, 3)) {
            if (isCheck) {
                if (autoScheduleCheckbox.getAttribute("class").contains("ng-empty")) {
                    clickTheElement(autoScheduleCheckbox);
                    if (autoScheduleCheckbox.getAttribute("class").contains("ng-not-empty")) {
                        SimpleUtils.pass("Check the Auto schedule option successfully!");
                    } else {
                        SimpleUtils.fail("Failed to check the auto schedule option!", false);
                    }
                }
            } else {
                if (autoScheduleCheckbox.getAttribute("class").contains("ng-not-empty")) {
                    clickTheElement(autoScheduleCheckbox);
                    if (autoScheduleCheckbox.getAttribute("class").contains("ng-empty")) {
                        SimpleUtils.pass("Uncheck the Auto schedule option successfully!");
                    } else {
                        SimpleUtils.fail("Failed to uncheck the auto schedule option!", false);
                    }
                }
            }
        }
    }

    @Override
    public void checkOrUnCheckSpecificDay(boolean isCheck, String day) throws Exception {
        if (areListElementVisible(eachDayCheckboxes, 3) && areListElementVisible(eachDayLabels, 3)) {
            for (int i = 0; i < eachDayLabels.size(); i++) {
                if (day.equalsIgnoreCase(eachDayLabels.get(i).getText())) {
                    if (isCheck) {
                        if (eachDayCheckboxes.get(i).getAttribute("class").contains("ng-empty")) {
                            clickTheElement(eachDayCheckboxes.get(i));
                            if (!day.equalsIgnoreCase("Everyday")) {
                                if (eachDayCheckboxes.get(i).getAttribute("class").contains("ng-not-empty")) {
                                    SimpleUtils.pass("Check the day: " + eachDayLabels.get(i).getText() + " successfully!");
                                } else {
                                    SimpleUtils.fail("Failed to Check the day: " + eachDayLabels.get(i).getText(), false);
                                }
                            } else {
                                for (WebElement checkbox : eachDayCheckboxes) {
                                    if (checkbox.getAttribute("class").contains("ng-empty")) {
                                        SimpleUtils.fail("Failed to check the everyday option!", false);
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        if (eachDayCheckboxes.get(i).getAttribute("class").contains("ng-not-empty")) {
                            clickTheElement(eachDayCheckboxes.get(i));
                            if (!day.equalsIgnoreCase("Everyday")) {
                                if (eachDayCheckboxes.get(i).getAttribute("class").contains("ng-empty")) {
                                    SimpleUtils.pass("Uncheck the day: " + eachDayLabels.get(i).getText() + " successfully!");
                                } else {
                                    SimpleUtils.fail("Failed to uncheck the day: " + eachDayLabels.get(i).getText(), false);
                                }
                            } else {
                                for (WebElement checkbox : eachDayCheckboxes) {
                                    if (checkbox.getAttribute("class").contains("ng-not-empty")) {
                                        SimpleUtils.fail("Failed to uncheck the everyday option!", false);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
            }
        } else {
            SimpleUtils.fail("Each day checkboxes and labels failed to load!", false);
        }
    }

    @Override
    public void verifyTheFunctionalityOfExpandWeekIcon(int weekNumber, boolean isExpanded) throws Exception {
        int index = weekNumber - 1;
        if (isExpanded) {
            if (isElementLoaded(collapsedIcons.get(index), 3)) {
                clickTheElement(collapsedIcons.get(index));
                if (isElementLoaded(expandedIcons.get(index))) {
                    SimpleUtils.pass("Expand the week section for Week" + weekNumber);
                } else {
                    SimpleUtils.fail("Failed to expand the week section for Week" + weekNumber, false);
                }
            }
        } else {
            if (isElementLoaded(expandedIcons.get(index), 3)) {
                clickTheElement(expandedIcons.get(index));
                if (isElementLoaded(collapsedIcons.get(index))) {
                    SimpleUtils.pass("Collapse the week section for Week" + weekNumber);
                } else {
                    SimpleUtils.fail("Failed to collapse the week section for Week" + weekNumber, false);
                }
            }
        }
    }

    @Override
    public void verifyTheFunctionalityOfArrowIcon(int weekNumber, boolean isExpanded) throws Exception {
        int index = weekNumber - 1;
        if (isExpanded) {
            if (isElementLoaded(chevronDownBtns.get(index), 3)) {
                clickTheElement(chevronDownBtns.get(index));
                if (isElementLoaded(chevronUpBtns.get(index))) {
                    SimpleUtils.pass("Expand the week section for Week" + weekNumber);
                } else {
                    SimpleUtils.fail("Failed to expand the week section for Week" + weekNumber, false);
                }
            }
        } else {
            if (isElementLoaded(chevronUpBtns.get(index), 3)) {
                clickTheElement(chevronUpBtns.get(index));
                if (isElementLoaded(chevronDownBtns.get(index))) {
                    SimpleUtils.pass("Collapse the week section for Week" + weekNumber);
                } else {
                    SimpleUtils.fail("Failed to collapse the week section for Week" + weekNumber, false);
                }
            }
        }
    }

    @Override
    public int getWeekCount() throws Exception {
        if (areListElementVisible(weekTitles, 3)) {
            return weekTitles.size();
        } else {
            return 0;
        }
    }

    @Override
    public void deleteTheWeek(int weekNumber) throws Exception {
        if (areListElementVisible(deleteWeekBtns, 3)) {
            clickTheElement(deleteWeekBtns.get(weekNumber - 1));
        } else {
            SimpleUtils.fail("There is no delete week buttons!", false);
        }
    }

    @Override
    public void clickOnAddShiftButton() throws Exception {
        if (areListElementVisible(addShiftBtns, 3)) {
            clickTheElement(addShiftBtns.get(0));
        } else {
            selectAddOnOrOffWeek(true);
            clickOnAddShiftButton();
        }
        if (isElementLoaded(createNewShiftWindow, 3)) {
            SimpleUtils.pass("Click on + Add Shift button successfullt!");
        } else {
            SimpleUtils.fail("Create New Shift windows failed to load!", false);
        }
    }
}
