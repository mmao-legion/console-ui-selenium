package com.legion.pages.core.schedule;

import com.legion.pages.*;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import static com.legion.utils.MyThreadLocal.*;

public class ConsoleCreateSchedulePage extends BasePage implements CreateSchedulePage {
    public ConsoleCreateSchedulePage() {
        PageFactory.initElements(getDriver(), this);
    }

    private static HashMap<String, String> propertyOperatingHoursLG = JsonUtil.getPropertiesFromJsonFile("src/test/resources/operatingHoursLG.json");
    private static HashMap<String, String> propertyOperatingHours = JsonUtil.getPropertiesFromJsonFile("src/test/resources/operatingHours.json");

    @FindBy(css = "[ng-click=\"regenerateFromOverview()\"] button")
    private WebElement generateSheduleButton;
    @FindBy(css = "lg-button[label=\"Generate schedule\"]")
    private WebElement generateScheduleBtn;
    @FindBy(css = "lg-button[label*=\"ublish\"]")
    private WebElement publishSheduleButton;
    @FindBy(css = "[ng-click*=\"regenerateFromManagerView()\"]")
    private WebElement reGenerateScheduleButton;
    @FindBy (css = "lg-button[ng-click=\"deleteSchedule()\"]")
    private WebElement deleteScheduleButton;
    @FindBy(className = "week-schedule-shift-wrapper")
    private List<WebElement> shiftsWeekView;
    @FindBy(css = "lg-button[ng-click=\"controlPanel.fns.editAction($event)\"]")
    private WebElement editScheduleButton;
    @FindBy(css = "div.sch-view-dropdown-summary-content-item-heading.ng-binding")
    private WebElement analyzePopupLatestVersionLabel;
    @FindBy(css = "[ng-click=\"goToSchedule()\"]")
    private WebElement checkOutTheScheduleButton;
    @FindBy(css = "button.btn-success")
    private WebElement upgradeAndGenerateScheduleBtn;
    @FindBy(className = "sch-publish-confirm-btn")
    private WebElement publishConfirmBtn;
    @FindBy (css = "span.wm-close-link")
    private WebElement closeButton;
    @FindBy(className = "successful-publish-message-btn-ok")
    private WebElement successfulPublishOkBtn;
    @FindBy(css = "[ng-click=\"regenerateFromOverview()\"]")
    private WebElement scheduleGenerateButton;
    @FindBy(css = "lg-button-group[buttons='scheduleTypeOptions'] div.lg-button-group-selected")
    private WebElement activScheduleType;
    @FindBy(css = "lg-button-group[buttons='scheduleTypeOptions'] div.lg-button-group-last")
    private WebElement scheduleTypeManager;
    @FindBy(className = "week-schedule-shift")
    private List<WebElement> weekShifts;
    @FindBy (className = "generate-modal-subheader-title")
    private WebElement generateModalTitle;
    @FindBy (css = "[ng-click=\"next()\"]")
    private WebElement nextButtonOnCreateSchedule;
    @FindBy(css = "[label='Generate Schedule']")
    private WebElement generateSheduleForEnterBudgetBtn;
    @FindBy(xpath = "//button[contains(text(),'UPDATE')]")
    private WebElement updateAndGenerateScheduleButton;
    @FindBy(css = "div.modal-instance.generate-modal")
    private WebElement copySchedulePopUp;
    @FindBy(css = "div.confirm")
    private WebElement btnContinue;
    @FindBy (css = ".generate-modal-week")
    private List<WebElement> createModalWeeks;
    @FindBy (css = "span.loading-icon.ng-scope")
    private WebElement loadingIcon;
    @FindBy(css = "div.edit-budget span.header-text")
    private WebElement popUpGenerateScheduleTitleTxt;
    @FindBy(css = "div[ng-if='canEditHours(budget)']")
    private List<WebElement> editBudgetHrs;
    @FindBy(css = "input[ng-class='hoursFieldClass(budget)']")
    private List<WebElement> inputHrs;
    @FindBy(css = "tr.table-row.ng-scope")
    private List<WebElement> budgetTableRow;
    @FindBy(css = "span.ok-action-text")
    private WebElement btnGenerateBudgetPopUP;
    @FindBy(css = "span[ng-if='canEditWages(budget)']")
    private List<WebElement> editWagesHrs;
    @FindBy (css = "generate-modal-operating-hours-step [label=\"Edit\"]")
    private WebElement operatingHoursEditBtn;
    @FindBy (css = ".generate-modal-operating-hours-step-container .lg-picker-input")
    private WebElement locationSelectorOnCreateSchedulePage;
    @FindBy (css = ".modal-instance .lg-search-options__option")
    private List<WebElement> locationsInLocationSelectorOnCreateSchedulePage;
    @FindBy (css = "input[placeholder=\"Search Location\"]")
    private WebElement searchLocationOnCreateSchedulePage;
    @FindBy(css = "lg-button[label=\"Cancel\"]")
    private WebElement operatingHoursCancelBtn;
    @FindBy(css = "lg-button[label=\"Save\"]")
    private WebElement operatingHoursSaveBtn;
    @FindBy (css = ".operating-hours-day-list-item.ng-scope")
    private List<WebElement> operatingHoursDayLists;
    @FindBy (css = ".generate-modal-subheader-location-name")
    private WebElement selectedLocationOnCreateSchedulePage;
    @FindBy(css = "div.generate-modal-budget-step-container")
    private WebElement enterBudgetTable;
    @FindBy (css = "generate-modal-budget-step [label=\"Edit\"]")
    private WebElement editBudgetBtn;
    @FindBy (css = "generate-modal-budget-step [ng-repeat=\"r in summary.staffingGuidance.roleHours\"]")
    private List<WebElement> roleHoursRows;
    @FindBy (css = "[value=\"config.partialSchedule\"]")
    private WebElement copyPartialScheduleSwitch;
    @FindBy (css = "[ng-repeat=\"assignment in assignments\"]")
    private List<WebElement> copyShiftAssignments;
    @FindBy (xpath = "//p[contains(text(),\"Target Budget: \")]/span")
    private WebElement targetBudget;
    @FindBy (css = "[x=\"25\"]")
    private List<WebElement> budgetHrsOnGraph;
    @FindBy (css = ".generate-modal-week-container.selected text[x=\"85\"]")
    private WebElement scheduledHrsOnGraph;
    @FindBy (css = ".modal-instance-header-title")
    private WebElement headerWhileCreateSchedule;
    @FindBy (css = ".generate-modal-location")
    private WebElement locationWhileCreateSchedule;
    @FindBy(css = "[label=\"Back\"]")
    private WebElement backButton;

    @Override
    public Boolean isWeekGenerated() throws Exception {
        if (isElementEnabled(generateSheduleButton, 10) && generateSheduleButton.getText().equalsIgnoreCase("Create schedule")) {
            return false;
        }else if(isElementEnabled(generateScheduleBtn, 10)){
            return false;
        }else if(isElementLoaded(publishSheduleButton, 10)) {
            return true;
        }else if(isElementLoaded(reGenerateScheduleButton, 10)) {
            return true;
        }else if(isElementLoaded(deleteScheduleButton, 10)) {
            return true;
        }
        if(areListElementVisible(shiftsWeekView,3) || isElementLoaded(editScheduleButton,5)){
            SimpleUtils.pass("Week: '" + getActiveWeekText() + "' Already Generated!");
            return true;
        }
        return false;
    }


    @Override
    public Boolean isWeekPublished() throws Exception {
        ScheduleMainPage scheduleMainPage = new ConsoleScheduleMainPage();
        AnalyzePage analyzePage = new ConsoleAnalyzePage();
        if (isElementLoaded(publishSheduleButton,5)) {
            if (publishSheduleButton.isEnabled()) {
                return false;
            } else {
                scheduleMainPage.clickOnScheduleAnalyzeButton();
                if (isElementLoaded(analyzePopupLatestVersionLabel)) {
                    String latestVersion = analyzePopupLatestVersionLabel.getText();
                    latestVersion = latestVersion.split(" ")[1].split(".")[0];
                    analyzePage.closeStaffingGuidanceAnalyzePopup();
                    if (Integer.valueOf(latestVersion) < 1) {
                        return false;
                    }
                }
            }
        } else if (isConsoleMessageError()) {
            return false;
        }
        SimpleUtils.pass("Week: '" + getActiveWeekText() + "' Already Published!");
        return true;

    }

    public boolean isConsoleMessageError() throws Exception {
        List<WebElement> carouselCards = MyThreadLocal.getDriver().findElements(By.cssSelector("div.card-carousel-card.card-carousel-card-default"));
        WebElement activeWeek = MyThreadLocal.getDriver().findElement(By.className("day-week-picker-period-active"));
        if (carouselCards.size() != 0) {
            for (WebElement carouselCard : carouselCards) {
                if (carouselCard.getText().toUpperCase().contains("CONSOLE MESSAGE")) {
                    SimpleUtils.report("Week: '" + activeWeek.getText().replace("\n", " ") + "' Not Published because of Console Message Error: '" + carouselCard.getText().replace("\n", " ") + "'");
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void generateSchedule() throws Exception {
        if (isElementLoaded(generateSheduleButton)) {
            click(generateSheduleButton);
            Thread.sleep(2000);
            if (isElementLoaded(generateScheduleBtn))
                click(generateScheduleBtn);
            Thread.sleep(4000);
            if (isElementLoaded(checkOutTheScheduleButton)) {
                click(checkOutTheScheduleButton);
                SimpleUtils.pass("Schedule Generated Successfuly!");
            }

            Thread.sleep(2000);
            if (isElementLoaded(upgradeAndGenerateScheduleBtn)) {
                click(upgradeAndGenerateScheduleBtn);
                Thread.sleep(5000);
                if (isElementLoaded(checkOutTheScheduleButton)) {
                    click(checkOutTheScheduleButton);
                    SimpleUtils.pass("Schedule Generated Successfuly!");
                }
            }
        } else {
            SimpleUtils.assertOnFail("Schedule Already generated for active week!", false, true);
        }
    }

    public boolean isCurrentScheduleWeekPublished() {
        String scheduleStatus = "No Published Schedule";
        try {
            List<WebElement> noPublishedSchedule = MyThreadLocal.getDriver().findElements(By.className("holiday-text"));
            if (noPublishedSchedule.size() > 0) {
                if (noPublishedSchedule.get(0).getText().contains(scheduleStatus))
                    return false;
            } else if (isConsoleMessageError()) {
                return false;
            } else if (isElementLoaded(publishSheduleButton)) {
                return false;
            } else if (isElementLoaded(generateSheduleButton, 5)) {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        SimpleUtils.pass("Schedule is Published for current Week!");
        return true;
    }

    @Override
    public void publishActiveSchedule() throws Exception {
        if (!isCurrentScheduleWeekPublished()) {
            if (isConsoleMessageError())
                SimpleUtils.fail("Schedule Can not be publish because of Action Require for week: '" + getActiveWeekText() + "'", false);
            else {
                clickTheElement(publishSheduleButton);
                if (isElementLoaded(publishConfirmBtn)) {
                    clickTheElement(publishConfirmBtn);
                    SimpleUtils.pass("Schedule published successfully for week: '" + getActiveWeekText() + "'");
                    // It will pop up a window: Welcome to Legion!
                    if (isElementLoaded(closeButton, 5)) {
                        clickTheElement(closeButton);
                    }
                    if (isElementLoaded(successfulPublishOkBtn)) {
                        clickTheElement(successfulPublishOkBtn);
                    }
                    if (isElementLoaded(publishSheduleButton, 5)) {
                        // Wait for the Publish button to disappear.
                        waitForSeconds(10);
                    }
                }
            }

        }
    }



    public Boolean isGenerateButtonLoaded() throws Exception {
        if (isElementLoaded(scheduleGenerateButton,2)) {
            return true;
        }
        return false;
    }

    public Boolean isGenerateButtonLoadedForManagerView() throws Exception {
        if (isElementLoaded(generateScheduleBtn,2)) {
            return true;
        }
        return false;
    }

    public Boolean isReGenerateButtonLoadedForManagerView() throws Exception {
        if (isElementLoaded(reGenerateScheduleButton, 10)) {
            return true;
        }
        return false;
    }

    public void switchToManagerViewToCheckForSecondGenerate() throws Exception {
        try {
            ScheduleMainPage scheduleMainPage = new ConsoleScheduleMainPage();
            String activeWeekText = getActiveWeekText();
            if (scheduleMainPage.isScheduleTypeLoaded()) {
                if (activScheduleType.getText().equalsIgnoreCase("Suggested")) {
                    click(scheduleTypeManager);
                    waitForSeconds(3);
                    if (isReGenerateButtonLoadedForManagerView()) {
                        click(reGenerateScheduleButton);
                        generateScheduleFromCreateNewScheduleWindow(activeWeekText);
                        selectWhichWeekToCopyFrom("SUGGESTED");
                        clickOnFinishButtonOnCreateSchedulePage();
                    } else if (isElementLoaded(publishSheduleButton, 5)) {
                        SimpleUtils.pass("Generate the schedule for week: " + activeWeekText + " Successfully!");
                    } else if (areListElementVisible(weekShifts, 5)) {
                        SimpleUtils.pass("Generate the schedule for week: " + activeWeekText + " Successfully!");
                    } else {
                        SimpleUtils.fail("Generate button or Publish Button not found on page", false);
                    }
                } else {
                    if (isReGenerateButtonLoadedForManagerView()) {
                        click(reGenerateScheduleButton);
                        generateScheduleFromCreateNewScheduleWindow(activeWeekText);
                    } else if (isElementLoaded(publishSheduleButton, 10)) {
                        SimpleUtils.pass("Generate the schedule for week: " + activeWeekText + " Successfully!");
                    } else if (areListElementVisible(weekShifts, 10)) {
                        SimpleUtils.pass("Generate the schedule for week: " + activeWeekText + " Successfully!");
                    } else {
                        SimpleUtils.fail("Generate button or Publish not found on page", false);
                    }
                }
            } else {
                SimpleUtils.report("Schedule Type Suggested/Manager is disabled");
//                getDriver().navigate().refresh();
//                waitForSeconds(5);
                if (isReGenerateButtonLoadedForManagerView()) {
                    click(reGenerateScheduleButton);
                    generateScheduleFromCreateNewScheduleWindow(activeWeekText);
                } else if (isElementLoaded(publishSheduleButton, 5)) {
                    SimpleUtils.pass("Generate the schedule for week: " + activeWeekText + " Successfully!");
                } else if (areListElementVisible(weekShifts, 5)) {
                    SimpleUtils.pass("Generate the schedule for week: " + activeWeekText + " Successfully!");
                } else {
                    SimpleUtils.fail("Generate button or Publish not found on page", false);
                }
            }
            if (isElementEnabled(checkOutTheScheduleButton, 20)) {
                checkoutSchedule();
            }
            waitForSeconds(5);
            if (areListElementVisible(shiftsWeekView, 15) && shiftsWeekView.size() > 0) {
                SimpleUtils.pass("Create the schedule successfully!");
            } else {
                SimpleUtils.fail("Not able to generate the schedule successfully!", false);
            }
        } catch (Exception e) {
            // Do nothing
        }
    }


    @Override
    public void createScheduleForNonDGFlowNewUI() throws Exception {
        String subTitle = "Confirm Operating Hours";
        waitForSeconds(3);
        if (isElementLoaded(generateSheduleButton,120)) {
            clickTheElement(generateSheduleButton);
            openBudgetPopUp();
            if (isElementLoaded(generateModalTitle, 15) && subTitle.equalsIgnoreCase(generateModalTitle.getText().trim())
                    && isElementLoaded(nextButtonOnCreateSchedule, 15)) {
                editTheOperatingHours(new ArrayList<>());
                waitForSeconds(3);
                clickTheElement(nextButtonOnCreateSchedule);
                checkEnterBudgetWindowLoadedForNonDG();
                selectWhichWeekToCopyFrom("SUGGESTED");
                clickOnFinishButtonOnCreateSchedulePage();
                switchToManagerViewToCheckForSecondGenerate();
            }else if (isElementLoaded(generateSheduleForEnterBudgetBtn, 5)) {
                click(generateSheduleForEnterBudgetBtn);
                if (isElementEnabled(checkOutTheScheduleButton, 20)) {
                    checkoutSchedule();
                    switchToManagerViewToCheckForSecondGenerate();
                } else if (isElementLoaded(updateAndGenerateScheduleButton, 5)) {
                    updateAndGenerateSchedule();
                    switchToManagerViewToCheckForSecondGenerate();
                } else {
                    SimpleUtils.fail("Not able to generate Schedule Successfully!", false);
                }
            } else if (isElementLoaded(updateAndGenerateScheduleButton, 5)) {
                updateAndGenerateSchedule();
                switchToManagerViewToCheckForSecondGenerate();
            } else if (isElementEnabled(checkOutTheScheduleButton,20)) {
                checkOutGenerateScheduleBtn(checkOutTheScheduleButton);
                SimpleUtils.pass("Schedule Generated Successfully!");
                switchToManagerViewToCheckForSecondGenerate();
            } else {
                SimpleUtils.fail("Not able to generate schedule Successfully!", false);
            }
        }else {
            SimpleUtils.fail("Create Schedule button not loaded Successfully!", false);
        }
    }

    public void generateScheduleFromCreateNewScheduleWindow(String activeWeekText) throws Exception {
        if(isElementEnabled(copySchedulePopUp,5)){
            SimpleUtils.pass("Copy From Schedule Window opened for week " + activeWeekText);
            if(isElementEnabled(btnContinue,2)){
                JavascriptExecutor jse = (JavascriptExecutor) getDriver();
                jse.executeScript("arguments[0].click();", btnContinue);
            }else{
                SimpleUtils.fail("Continue button was not present on page",false);
            }
        }else if (isElementEnabled(publishSheduleButton,5)){
            SimpleUtils.pass("Copy From Schedule Window not opened for " + activeWeekText +" because there was no past week published schedule" + activeWeekText);
        }
        else{
            SimpleUtils.fail("Copy From Schedule Window not opened for week " + activeWeekText,false);
        }
    }


    @Override
    public void selectWhichWeekToCopyFrom(String weekInfo) throws Exception {
        boolean selectOtherWeek = false;
        try{
            if (areListElementVisible(createModalWeeks, 10)) {
                SimpleUtils.pass("Copy Schedule page loaded Successfully!");
                waitForSeconds(5);
                for (WebElement createModalWeek : createModalWeeks) {
                    WebElement weekName = createModalWeek.findElement(By.className("generate-modal-week-name"));
                    if (!selectOtherWeek) {
                        if (weekName != null && weekName.getText().toLowerCase().contains(weekInfo.toLowerCase())) {
                            WebElement weekContainer = createModalWeek.findElement(By.className("generate-modal-week-container"));
                            if (weekContainer != null) {
                                WebElement scheduledHours = weekContainer.findElement(By.cssSelector("svg > g > g:nth-child(2) > text"));
                                if (scheduledHours != null && !scheduledHours.getText().equals("0")) {
                                    int i = 0;
                                    while (isElementLoaded(loadingIcon)&& i<20){
                                        waitForSeconds(3);
                                        i = i+1;
                                    }
                                    clickTheElement(weekContainer);
                                    waitForSeconds(3);
//                                SimpleUtils.pass("Create Schedule: Select the " + weekName.getText() + " with scheduled hour: " + scheduledHours.getText() + " Successfully!");
                                    break;
                                } else {
                                    selectOtherWeek = true;
                                    SimpleUtils.warn("Scheduled Hour is 0, due to bug PLT-1082: [RC]Creating schedule keeps spinning and shows 0 scheduled hours! Will select another week as a workaround");
                                }
                            }
                        }
                    } else {
                        WebElement weekContainer = createModalWeek.findElement(By.className("generate-modal-week-container"));
                        if (weekContainer != null) {
                            WebElement scheduledHours = weekContainer.findElement(By.cssSelector("svg > g > g:nth-child(2) > text"));
                            if (scheduledHours != null && !scheduledHours.getText().equals("0")) {
                                clickTheElement(weekContainer);
                                SimpleUtils.pass("Create Schedule: Select the " + weekName.getText() + "with scheduled hour: " + scheduledHours.getText() + " Successfully!");
                                break;
                            }
                        }
                    }
                }
            }
        } catch (StaleElementReferenceException e){
            SimpleUtils.report(e.getMessage());
        }
    }


    @Override
    public void clickOnFinishButtonOnCreateSchedulePage() throws Exception {
        if (isElementLoaded(nextButtonOnCreateSchedule, 5)) {
            clickTheElement(nextButtonOnCreateSchedule);
            WebElement element = (new WebDriverWait(getDriver(), 120))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[ng-click=\"goToSchedule()\"]")));
            waitForSeconds(3);
            if (isElementLoaded(element, 5)) {
                checkoutSchedule();
                SimpleUtils.pass("Schedule Page: Schedule is generated within 2 minutes successfully");
            } else {
                SimpleUtils.fail("Schedule Page: Schedule isn't generated within 2 minutes", false);
            }
            if (areListElementVisible(shiftsWeekView, 60) && shiftsWeekView.size() > 0) {
                SimpleUtils.pass("Create the schedule successfully!");
            }else {
                SimpleUtils.fail("Not able to generate the schedule successfully!", false);
            }
        }
    }

    public void checkoutSchedule() {
        clickTheElement(checkOutTheScheduleButton);
        SimpleUtils.pass("Schedule Generated Successfuly!");
    }

    public void updateAndGenerateSchedule() {
        if (isElementEnabled(updateAndGenerateScheduleButton)) {
            click(updateAndGenerateScheduleButton);
            SimpleUtils.pass("Schedule Update and Generate button clicked Successfully!");
            if (isElementEnabled(checkOutTheScheduleButton)) {
                checkoutSchedule();
            }
        } else {
            SimpleUtils.fail("Not able to generate Schedule Successfully!", false);
        }
    }

    @Override
    public void generateOrUpdateAndGenerateSchedule() throws Exception {
        ScheduleMainPage scheduleMainPage = new ConsoleScheduleMainPage();
        if (isElementEnabled(generateSheduleButton,5)) {
            clickTheElement(generateSheduleButton);
            openBudgetPopUp();
//            openBudgetPopUpGenerateSchedule();
            if (isElementLoaded(generateSheduleForEnterBudgetBtn, 5)) {
                click(generateSheduleForEnterBudgetBtn);
                if (isElementEnabled(checkOutTheScheduleButton, 20)) {
                    checkoutSchedule();
                    scheduleMainPage.switchToManagerView();
                } else if (isElementLoaded(updateAndGenerateScheduleButton, 5)) {
                    updateAndGenerateSchedule();
                    scheduleMainPage.switchToManagerView();
                } else {
                    SimpleUtils.fail("Not able to generate Schedule Successfully!", false);
                }
            } else if (isElementLoaded(updateAndGenerateScheduleButton, 5)) {
                updateAndGenerateSchedule();
                scheduleMainPage.switchToManagerView();
            } else if (isElementEnabled(checkOutTheScheduleButton,20)) {
                checkOutGenerateScheduleBtn(checkOutTheScheduleButton);
                SimpleUtils.pass("Schedule Generated Successfully!");
                scheduleMainPage.switchToManagerView();
            } else {
                SimpleUtils.fail("Not able to generate Schedule Successfully!", false);
            }

        } else if(isElementEnabled(generateScheduleBtn,5)){
            scheduleMainPage.switchToManagerView();
        }
        else {
            SimpleUtils.assertOnFail("Schedule Already generated for active week!", false, true);
        }
    }

    public void openBudgetPopUp() throws Exception{
        if(isElementLoaded(popUpGenerateScheduleTitleTxt,5)){
            if(areListElementVisible(editBudgetHrs,5)){
                fillBudgetValues(editBudgetHrs);
                openBudgetPopUpGenerateSchedule();
            }else if(areListElementVisible(editWagesHrs,5)){
                fillBudgetValues(editWagesHrs);
                openBudgetPopUpGenerateSchedule();
            } else if(isElementLoaded(btnGenerateBudgetPopUP, 5)){
                openBudgetPopUpGenerateSchedule();
            }
        }
    }

    public void fillBudgetValues(List<WebElement> element) throws Exception {
        if(areListElementVisible(budgetTableRow,5)){
            for(int i=0; i<budgetTableRow.size()-1;i++){
                click(element.get(i));
                int fillBudgetInNumbers = SimpleUtils.generateRandomNumbers();
                inputHrs.get(i).clear();
                inputHrs.get(i).sendKeys(String.valueOf(fillBudgetInNumbers));
            }
        }else{
            SimpleUtils.fail("Not able to see Budget table row for filling up the data",false);
        }
    }

    @Override
    public void editTheOperatingHours(List<String> weekDaysToClose) throws Exception {
        try{
            if (isElementLoaded(operatingHoursEditBtn, 10)) {
                clickTheElement(operatingHoursEditBtn);
                if (isElementLoaded(locationSelectorOnCreateSchedulePage, 5)
                        && areListElementVisible(locationsInLocationSelectorOnCreateSchedulePage, 5)
                        && locationsInLocationSelectorOnCreateSchedulePage.size() > 0) {
                    click(locationSelectorOnCreateSchedulePage);
                    selectRandomLocationOnCreateScheduleEditOperatingHoursPage();
                }
                if (isElementLoaded(operatingHoursCancelBtn, 10) && isElementLoaded(operatingHoursSaveBtn, 10)) {
                    SimpleUtils.pass("Click on Operating Hours Edit button Successfully!");
                    if (areListElementVisible(operatingHoursDayLists, 15)) {
                        for (WebElement dayList : operatingHoursDayLists) {
                            WebElement weekDay = dayList.findElement(By.cssSelector(".operating-hours-day-list-item-day"));
                            if (weekDay != null) {
                                WebElement checkbox = dayList.findElement(By.cssSelector("input[type=\"checkbox\"]"));
                                if (!weekDaysToClose.contains(weekDay.getText())) {
                                    if (checkbox.getAttribute("class").contains("ng-empty")) {
                                        clickTheElement(checkbox);
                                    }
                                    String[] operatingHours = null;
                                    if (isElementLoaded(locationSelectorOnCreateSchedulePage, 5)) {
                                        operatingHours = propertyOperatingHoursLG.get(weekDay.getText()).split("-");
                                    } else
                                        operatingHours = propertyOperatingHours.get(weekDay.getText()).split("-");
                                    List<WebElement> startNEndTimes = dayList.findElements(By.cssSelector("[ng-if*=\"day.isOpened\"] input"));
                                    startNEndTimes.get(0).clear();
                                    startNEndTimes.get(1).clear();
                                    startNEndTimes.get(0).sendKeys(operatingHours[0].trim());
                                    startNEndTimes.get(1).sendKeys(operatingHours[1].trim());
                                } else {
                                    if (!checkbox.getAttribute("class").contains("ng-empty")) {
                                        clickTheElement(checkbox);
                                    }
                                }
                            } else {
                                SimpleUtils.fail("Failed to find weekday element!", false);
                            }
                        }
                        clickTheElement(operatingHoursSaveBtn);
                        if (isElementEnabled(operatingHoursEditBtn, 15)) {
                            SimpleUtils.pass("Create Schedule: Save the operating hours Successfully!");
                        } else {
                            SimpleUtils.fail("Create Schedule: Click on Save the operating hours button failed, Next button is not enabled!", false);
                        }
                    }
                } else {
                    SimpleUtils.fail("Click on Operating Hours Edit button failed!", false);
                }
            }else {
                SimpleUtils.fail("Operating Hours Edit button not loaded Successfully!", false);
            }
        } catch (StaleElementReferenceException e){
            SimpleUtils.report(e.getMessage());
        }
    }

    public void selectRandomLocationOnCreateScheduleEditOperatingHoursPage() throws Exception {
        int randomLocations = (new Random()).nextInt(locationsInLocationSelectorOnCreateSchedulePage.size());
        String randomLocationName = locationsInLocationSelectorOnCreateSchedulePage.get(randomLocations).getText();
        waitForSeconds(3);
        if(isElementLoaded(searchLocationOnCreateSchedulePage, 5)){
            searchLocationOnCreateSchedulePage.sendKeys(randomLocationName);
        }
        click(locationsInLocationSelectorOnCreateSchedulePage.get(0));
        if(selectedLocationOnCreateSchedulePage.getText().equals(randomLocationName)){
            SimpleUtils.pass("Select locations on Edit Operating hours successfully! ");
        } else
            SimpleUtils.fail("Select locations on Edit Operating hours failed! ", false);
    }

    @Override
    public float checkEnterBudgetWindowLoadedForNonDG() throws Exception {
        float budgetHour = 0;
        String title = "Enter Budget";
        waitForSeconds(2);
        try {
            if (isElementLoaded(generateModalTitle, 10) && title.equalsIgnoreCase(generateModalTitle.getText().trim())
                    && isElementLoaded(nextButtonOnCreateSchedule, 10)) {
                editTheBudgetForNondgFlow();
                waitForSeconds(5);
                try {
                    List<WebElement> trs = enterBudgetTable.findElements(By.tagName("tr"));
                    if (areListElementVisible(trs, 5) && trs.size() > 0) {
                        WebElement budget = trs.get(trs.size() - 1).findElement(By.cssSelector("th:nth-child(4)"));
                        budgetHour = Float.parseFloat(budget == null ? "" : budget.getText());
                        SimpleUtils.report("Enter Budget Window: Get the budget hour: " + budgetHour);
                    }
                } catch (Exception e) {
                    // Nothing
                }
                waitForSeconds(5);
                clickTheElement(nextButtonOnCreateSchedule);
            }
            if (isElementEnabled(checkOutTheScheduleButton, 20)) {
                checkoutSchedule();
            }
        } catch (Exception e) {
            // do nothing
        }
        return budgetHour;
    }


    public void checkOutGenerateScheduleBtn(WebElement checkOutTheScheduleButton) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(
                MyThreadLocal.getDriver()).withTimeout(Duration.ofSeconds(60))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        Boolean element = wait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver t) {
                boolean display = false;
                display = t.findElement(By.cssSelector("[ng-click=\"goToSchedule()\"]")).isEnabled();
                if (display)
                    return true;
                else
                    return false;
            }
        });
        if (element) {
            click(checkOutTheScheduleButton);
            SimpleUtils.pass("Schedule Generated Successfully!");
        } else {
            SimpleUtils.fail("Not able to generate Schedule Successfully!", false);
        }

    }

    public void openBudgetPopUpGenerateSchedule() throws Exception{
        if(isElementEnabled(btnGenerateBudgetPopUP,5)){
            click(btnGenerateBudgetPopUP);
        }else{
            SimpleUtils.fail("Generate btn not clickable on Budget pop up", false);
        }
    }

    @Override
    public void editTheBudgetForNondgFlow() throws Exception {
        if (isElementLoaded(editBudgetBtn, 20)) {
            clickTheElement(editBudgetBtn);
            // Cancel and Save buttons are consistent with operating hours
            if (isElementLoaded(operatingHoursCancelBtn, 10) && isElementLoaded(operatingHoursSaveBtn, 10)) {
                SimpleUtils.pass("Create Schedule - Enter Budget: Click on Edit button Successfully!");
                if (areListElementVisible(roleHoursRows, 5)) {
                    for (WebElement roleHoursRow : roleHoursRows) {
                        try {
                            WebElement forecastHour = roleHoursRow.findElement(By.cssSelector("td:nth-child(3)"));
                            WebElement budgetHour = roleHoursRow.findElement(By.cssSelector("input[type=\"number\"]"));
                            if (forecastHour != null && budgetHour != null) {
                                String forecastHourString = "";
                                if (forecastHour.getText().trim().contains(".")) {
                                    forecastHourString = forecastHour.getText().trim().substring(0, forecastHour.getText().trim().indexOf("."));
                                }
                                budgetHour.clear();
                                budgetHour.sendKeys(forecastHourString);
                            }
                        }catch (Exception e) {
                            continue;
                        }
                    }
                }
                clickTheElement(operatingHoursSaveBtn);
                waitForSeconds(3);
                if (isElementEnabled(editBudgetBtn, 10)) {
                    SimpleUtils.pass("Create Schedule: Save the budget hours Successfully!");
                }else {
                    SimpleUtils.fail("Create Schedule: Click on Save the budget hours button failed, Next button is not enabled!", false);
                }
            }
        }else {
            SimpleUtils.fail("Create Schedule - Enter Budget: Edit button not loaded Successfully!", false);
        }
    }

    @Override
    public void createScheduleForNonDGFlowNewUIWithGivingParameters(String day, String startTime, String endTime) throws Exception {
        String subTitle = "Confirm Operating Hours";
        if (isElementLoaded(generateSheduleButton,10)) {
            moveToElementAndClick(generateSheduleButton);
            openBudgetPopUp();
            if (isElementLoaded(generateModalTitle, 15) && subTitle.equalsIgnoreCase(generateModalTitle.getText().trim())
                    && isElementLoaded(nextButtonOnCreateSchedule, 15)) {
                editOperatingHoursWithGivingPrameters(day, startTime, endTime);
                waitForSeconds(3);
                clickTheElement(nextButtonOnCreateSchedule);
                checkEnterBudgetWindowLoadedForNonDG();
                selectWhichWeekToCopyFrom("SUGGESTED");
                clickOnFinishButtonOnCreateSchedulePage();
                switchToManagerViewToCheckForSecondGenerate();
            }else if (isElementLoaded(generateSheduleForEnterBudgetBtn, 5)) {
                click(generateSheduleForEnterBudgetBtn);
                if (isElementEnabled(checkOutTheScheduleButton, 20)) {
                    checkoutSchedule();
                    switchToManagerViewToCheckForSecondGenerate();
                } else if (isElementLoaded(updateAndGenerateScheduleButton, 5)) {
                    updateAndGenerateSchedule();
                    switchToManagerViewToCheckForSecondGenerate();
                } else {
                    SimpleUtils.fail("Not able to generate Schedule Successfully!", false);
                }
            } else if (isElementLoaded(updateAndGenerateScheduleButton, 5)) {
                updateAndGenerateSchedule();
                switchToManagerViewToCheckForSecondGenerate();
            } else if (isElementEnabled(checkOutTheScheduleButton,20)) {
                checkOutGenerateScheduleBtn(checkOutTheScheduleButton);
                SimpleUtils.pass("Schedule Generated Successfully!");
                switchToManagerViewToCheckForSecondGenerate();
            } else {
                SimpleUtils.fail("Not able to generate schedule Successfully!", false);
            }
        }else {
            SimpleUtils.fail("Create Schedule button not loaded Successfully!", false);
        }
    }


    @Override
    public void createScheduleForNonDGFlowNewUIWithGivingTimeRange(String startTime, String endTime) throws Exception {
        String subTitle = "Confirm Operating Hours";
        if (isElementLoaded(generateSheduleButton,10)) {
            moveToElementAndClick(generateSheduleButton);
            openBudgetPopUp();
            if (isElementLoaded(generateModalTitle, 15) && subTitle.equalsIgnoreCase(generateModalTitle.getText().trim())
                    && isElementLoaded(nextButtonOnCreateSchedule, 15)) {
                editOperatingHoursWithGivingPrameters("Sunday", startTime, endTime);
                editOperatingHoursWithGivingPrameters("Monday", startTime, endTime);
                editOperatingHoursWithGivingPrameters("Tuesday", startTime, endTime);
                editOperatingHoursWithGivingPrameters("Wednesday", startTime, endTime);
                editOperatingHoursWithGivingPrameters("Thursday", startTime, endTime);
                editOperatingHoursWithGivingPrameters("Friday", startTime, endTime);
                editOperatingHoursWithGivingPrameters("Saturday", startTime, endTime);
                waitForSeconds(3);
                clickTheElement(nextButtonOnCreateSchedule);
                waitForSeconds(2);
                checkEnterBudgetWindowLoadedForNonDG();
                selectWhichWeekToCopyFrom("SUGGESTED");
                clickOnFinishButtonOnCreateSchedulePage();
                switchToManagerViewToCheckForSecondGenerate();
            }else if (isElementLoaded(generateSheduleForEnterBudgetBtn, 5)) {
                click(generateSheduleForEnterBudgetBtn);
                if (isElementEnabled(checkOutTheScheduleButton, 20)) {
                    checkoutSchedule();
                    switchToManagerViewToCheckForSecondGenerate();
                } else if (isElementLoaded(updateAndGenerateScheduleButton, 5)) {
                    updateAndGenerateSchedule();
                    switchToManagerViewToCheckForSecondGenerate();
                } else {
                    SimpleUtils.fail("Not able to generate Schedule Successfully!", false);
                }
            } else if (isElementLoaded(updateAndGenerateScheduleButton, 5)) {
                updateAndGenerateSchedule();
                switchToManagerViewToCheckForSecondGenerate();
            } else if (isElementEnabled(checkOutTheScheduleButton,20)) {
                checkOutGenerateScheduleBtn(checkOutTheScheduleButton);
                SimpleUtils.pass("Schedule Generated Successfully!");
                switchToManagerViewToCheckForSecondGenerate();
            } else {
                SimpleUtils.fail("Not able to generate schedule Successfully!", false);
            }
        }else {
            SimpleUtils.fail("Create Schedule button not loaded Successfully!", false);
        }
    }


    @Override
    public void editOperatingHoursWithGivingPrameters(String day, String startTime, String endTime) throws Exception {
        if (isElementLoaded(operatingHoursEditBtn, 10)) {
            clickTheElement(operatingHoursEditBtn);
            if (isElementLoaded(operatingHoursCancelBtn, 10) && isElementLoaded(operatingHoursSaveBtn, 10)) {
                SimpleUtils.pass("Click on Operating Hours Edit button Successfully!");
                if (areListElementVisible(operatingHoursDayLists, 15)) {
                    for (WebElement dayList : operatingHoursDayLists) {
                        WebElement checkbox = dayList.findElement(By.cssSelector("input[type=\"checkbox\"]"));
                        WebElement weekDay = dayList.findElement(By.cssSelector(".operating-hours-day-list-item-day"));
                        List<WebElement> startNEndTimes = dayList.findElements(By.cssSelector("[ng-if*=\"day.isOpened\"] input"));
                        if (checkbox != null && weekDay != null && startNEndTimes != null && startNEndTimes.size() == 2) {
                            if (checkbox.getAttribute("class").contains("ng-empty")) {
                                SimpleUtils.warn("editOperatingHoursWithGivingPrameters: All seven day of a week should be checked by default when create schedule.");
                                clickTheElement(checkbox);
                            }
                            if (weekDay.getText().toLowerCase().contains(day.toLowerCase())){
                                startNEndTimes = dayList.findElements(By.cssSelector("[ng-if*=\"day.isOpened\"] input"));
                                String openTime = startNEndTimes.get(0).getAttribute("value");
                                String closeTime = startNEndTimes.get(1).getAttribute("value");
                                if (!openTime.equals(startTime) || !closeTime.equals(endTime)) {
                                    startNEndTimes.get(0).clear();
                                    startNEndTimes.get(1).clear();
                                    startNEndTimes.get(0).sendKeys(startTime);
                                    startNEndTimes.get(1).sendKeys(endTime);
                                }

                            }
                        }else {
                            SimpleUtils.fail("Failed to find the checkbox, weekday or start and end time elements!", false);
                        }
                    }
                    clickTheElement(operatingHoursSaveBtn);
                    if (isElementEnabled(operatingHoursEditBtn, 10)) {
                        SimpleUtils.pass("Create Schedule: Save the operating hours Successfully!");
                    }else {
                        SimpleUtils.fail("Create Schedule: Click on Save the operating hours button failed, Next button is not enabled!", false);
                    }
                }
            }else {
                SimpleUtils.fail("Click on Operating Hours Edit button failed!", false);
            }
        }else {
            SimpleUtils.fail("Operating Hours Edit button not loaded Successfully!", false);
        }
    }

    @Override
    public void editOperatingHoursWithGivingPrameters(String startTime, String endTime) throws Exception {
        if (isElementLoaded(operatingHoursEditBtn, 10)) {
            clickTheElement(operatingHoursEditBtn);
            if (isElementLoaded(operatingHoursCancelBtn, 10) && isElementLoaded(operatingHoursSaveBtn, 10)) {
                SimpleUtils.pass("Click on Operating Hours Edit button Successfully!");
                if (areListElementVisible(operatingHoursDayLists, 15)) {
                    for (WebElement dayList : operatingHoursDayLists) {
                        WebElement checkbox = dayList.findElement(By.cssSelector("input[type=\"checkbox\"]"));
                        List<WebElement> startNEndTimes = dayList.findElements(By.cssSelector("[ng-if*=\"day.isOpened\"] input"));
                        if (checkbox != null && startNEndTimes != null && startNEndTimes.size() == 2) {
                            if (checkbox.getAttribute("class").contains("ng-empty")) {
                                clickTheElement(checkbox);
                            }
                            startNEndTimes = dayList.findElements(By.cssSelector("[ng-if*=\"day.isOpened\"] input"));
                            String openTime = startNEndTimes.get(0).getAttribute("value");
                            String closeTime = startNEndTimes.get(1).getAttribute("value");
                            if (!openTime.equals(startTime) || !closeTime.equals(endTime)) {
                                startNEndTimes.get(0).clear();
                                startNEndTimes.get(1).clear();
                                startNEndTimes.get(0).sendKeys(startTime);
                                startNEndTimes.get(1).sendKeys(endTime);
                            }
                        }else {
                            SimpleUtils.fail("Failed to find the checkbox, weekday or start and end time elements!", false);
                        }
                    }
                    clickTheElement(operatingHoursSaveBtn);
                    if (isElementEnabled(operatingHoursEditBtn, 10)) {
                        SimpleUtils.pass("Create Schedule: Save the operating hours Successfully!");
                    }else {
                        SimpleUtils.fail("Create Schedule: Click on Save the operating hours button failed, Next button is not enabled!", false);
                    }
                }
            }else {
                SimpleUtils.fail("Click on Operating Hours Edit button failed!", false);
            }
        }else {
            SimpleUtils.fail("Operating Hours Edit button not loaded Successfully!", false);
        }
    }


    @Override
    public float createScheduleForNonDGByWeekInfo(String weekInfo, List<String> weekDaysToClose, List<String> copyShiftAssignments) throws Exception {
        float budgetHours = 0;
        String subTitle = "Confirm Operating Hours";
        waitForSeconds(2);
        if (isElementLoaded(generateSheduleButton,60)) {
            clickTheElement(generateSheduleButton);
            if (isElementLoaded(generateModalTitle, 20) && subTitle.equalsIgnoreCase(generateModalTitle.getText().trim())
                    && isElementLoaded(nextButtonOnCreateSchedule, 20)) {
                editTheOperatingHours(weekDaysToClose);
                waitForSeconds(1);
                clickTheElement(nextButtonOnCreateSchedule);
                budgetHours = checkEnterBudgetWindowLoadedForNonDG();
                selectWhichWeekToCopyFrom(weekInfo);
                if(copyShiftAssignments !=null && copyShiftAssignments.size()>0){
                    if (isElementLoaded(copyPartialScheduleSwitch, 10)){
                        click(copyPartialScheduleSwitch);
                    } else
                        SimpleUtils.fail("Copy Partial Schedule Switch loaded fail! ", false);
                    clickTheElement(nextButtonOnCreateSchedule);
                    selectSpecificCopyShiftAssignments(copyShiftAssignments);
                    clickTheElement(nextButtonOnCreateSchedule);
                } else
                    clickOnFinishButtonOnCreateSchedulePage();
                if (isElementEnabled(checkOutTheScheduleButton, 20)) {
                    checkoutSchedule();
                }

            } else {
                SimpleUtils.fail("Not able to generate schedule Successfully!", false);
            }
        }else {
            SimpleUtils.fail("Create Schedule button not loaded Successfully!", false);
        }
        return budgetHours;
    }


    @Override
    public void clickCreateScheduleBtn() throws Exception {
        if (isElementEnabled(generateSheduleButton,10)) {
            click(generateSheduleButton);
            openBudgetPopUp();

        }else {
            SimpleUtils.fail("Create Schedule button not loaded Successfully!", false);
        }
    }

    @Override
    public HashMap<String, String> verifyNGetBudgetNScheduleWhileCreateScheduleForNonDGFlowNewUI(String weekInfo, String location) throws Exception {
        String subTitle = "Confirm Operating Hours";
        String totalBudget = "";
        String targetBudgetHrs = "";
        List<String> budgetForNonDGFlow = new ArrayList<>();
        HashMap<String, String> budgetNSchedule = new HashMap<>();
        if (isElementLoaded(generateSheduleButton, 10)) {
            moveToElementAndClick(generateSheduleButton);
            openBudgetPopUp();
            if (isElementLoaded(generateModalTitle, 5) && subTitle.equalsIgnoreCase(generateModalTitle.getText().trim())
                    && isElementLoaded(nextButtonOnCreateSchedule, 5)) {
                editTheOperatingHours(new ArrayList<>());
                waitForSeconds(3);
                clickTheElement(nextButtonOnCreateSchedule);
                verifyTheContentOnEnterBudgetWindow(weekInfo, location);
                budgetForNonDGFlow = setAndGetBudgetForNonDGFlow();
                clickTheElement(nextButtonOnCreateSchedule);
                selectWhichWeekToCopyFrom("SUGGESTED");
                targetBudgetHrs = targetBudget.getText().trim();
                if (targetBudgetHrs.contains(" ")) {
                    targetBudgetHrs = targetBudgetHrs.split(" ")[0];
                }
                if (budgetForNonDGFlow.size() > 1)
                    totalBudget = budgetForNonDGFlow.get(budgetForNonDGFlow.size() - 1);
                if (targetBudgetHrs.equals(totalBudget)) {
                    budgetNSchedule.put("Budget", targetBudgetHrs);
                    SimpleUtils.pass("Total budget in enter budget window and target budget in copy schedule window are consistent");
                } else
                    SimpleUtils.fail("Total budget in enter budget window and target budget in copy schedule window are inconsistent", false);
                for (WebElement budgetHrs : budgetHrsOnGraph) {
                    if (budgetHrs.getText().equals(targetBudgetHrs))
                        SimpleUtils.pass("The budget in graph is consistent with the target budget in copy schedule window");
                    else
                        SimpleUtils.fail("The budget in graph is inconsistent with the target budget in copy schedule window", false);
                }
                if (isElementLoaded(scheduledHrsOnGraph,10))
                    budgetNSchedule.put("Scheduled", scheduledHrsOnGraph.getText());
                clickOnFinishButtonOnCreateSchedulePage();
            } else if (isElementLoaded(generateSheduleForEnterBudgetBtn, 5)) {
                click(generateSheduleForEnterBudgetBtn);
                if (isElementEnabled(checkOutTheScheduleButton, 20)) {
                    checkoutSchedule();
                    switchToManagerViewToCheckForSecondGenerate();
                } else if (isElementLoaded(updateAndGenerateScheduleButton, 5)) {
                    updateAndGenerateSchedule();
                    switchToManagerViewToCheckForSecondGenerate();
                } else
                    SimpleUtils.fail("Not able to generate Schedule Successfully!", false);
            } else if (isElementLoaded(updateAndGenerateScheduleButton, 5)) {
                updateAndGenerateSchedule();
                switchToManagerViewToCheckForSecondGenerate();
            } else if (isElementEnabled(checkOutTheScheduleButton, 20)) {
                checkOutGenerateScheduleBtn(checkOutTheScheduleButton);
                SimpleUtils.pass("Schedule Generated Successfully!");
                switchToManagerViewToCheckForSecondGenerate();
            } else
                SimpleUtils.fail("Not able to generate schedule Successfully!", false);
        } else
            SimpleUtils.fail("Create Schedule button not loaded Successfully!", false);
        return budgetNSchedule;
    }

    @Override
    public void editTheOperatingHoursForLGInPopupWinodw(List<String> weekDaysToClose) throws Exception {
        if (isElementLoaded(operatingHoursEditBtn, 10)) {
            clickTheElement(operatingHoursEditBtn);
            if (isElementLoaded(operatingHoursCancelBtn, 10) && isElementLoaded(operatingHoursSaveBtn, 10)) {
                SimpleUtils.pass("Click on Operating Hours Edit button Successfully!");
                if (areListElementVisible(operatingHoursDayLists, 15)) {
                    for (WebElement dayList : operatingHoursDayLists) {
                        WebElement weekDay = dayList.findElement(By.cssSelector(".operating-hours-day-list-item-day"));
                        if (weekDay != null) {
                            WebElement checkbox = dayList.findElement(By.cssSelector("input[type=\"checkbox\"]"));
                            if (!weekDaysToClose.contains(weekDay.getText())) {
                                if (checkbox.getAttribute("class").contains("ng-empty")) {
                                    clickTheElement(checkbox);
                                }
                                String[] operatingHours = propertyOperatingHours.get(weekDay.getText()).split("-");
                                List<WebElement> startNEndTimes = dayList.findElements(By.cssSelector("[ng-if*=\"day.isOpened\"] input"));
                                startNEndTimes.get(0).clear();
                                startNEndTimes.get(1).clear();
                                startNEndTimes.get(0).sendKeys(operatingHours[0].trim());
                                startNEndTimes.get(1).sendKeys(operatingHours[1].trim());
                            } else {
                                if (!checkbox.getAttribute("class").contains("ng-empty")) {
                                    clickTheElement(checkbox);
                                }
                            }
                        } else {
                            SimpleUtils.fail("Failed to find weekday element!", false);
                        }
                    }
                    clickTheElement(operatingHoursSaveBtn);
                    if (isElementEnabled(operatingHoursEditBtn, 15)) {
                        SimpleUtils.pass("Create Schedule: Save the operating hours Successfully!");
                    }else {
                        SimpleUtils.fail("Create Schedule: Click on Save the operating hours button failed, Next button is not enabled!", false);
                    }
                }
            }else {
                SimpleUtils.fail("Click on Operating Hours Edit button failed!", false);
            }
        }else {
            SimpleUtils.fail("Operating Hours Edit button not loaded Successfully!", false);
        }
    }

    public void selectSpecificCopyShiftAssignments (List<String> specificShiftAssignments) {
        if (areListElementVisible(copyShiftAssignments, 10) && copyShiftAssignments.size()>0) {
            for (WebElement copyShiftAssignment: copyShiftAssignments){
                if (specificShiftAssignments.contains(copyShiftAssignment.getText())){
                    click(copyShiftAssignment);
                    SimpleUtils.pass("Selected shift assignment: " + copyShiftAssignment.getText() +" successfully! ");
                }
            }
        } else
            SimpleUtils.fail("Copy shift assignments loaded fail! ", false);
    }

    @Override
    public void clickNextBtnOnCreateScheduleWindow() throws Exception {
        if (isElementLoaded(nextButtonOnCreateSchedule, 15)){
            clickTheElement(nextButtonOnCreateSchedule);
            checkEnterBudgetWindowLoadedForNonDG();
        } else {
            SimpleUtils.fail("There is not next button!", false);
        }
    }



    @Override
    public void verifyTheContentOnEnterBudgetWindow(String weekInfo, String location) throws Exception {
        if (isElementLoaded(headerWhileCreateSchedule,5) && headerWhileCreateSchedule.getText().contains(weekInfo)){
            SimpleUtils.pass("Create Schedule - Enter Budget: \"" + weekInfo +"\" as header displays correctly");
        } else
            SimpleUtils.fail("Enter Budget: Week information as header not loaded or displays incorrectly",true);
        if (isElementLoaded(locationWhileCreateSchedule,5) && locationWhileCreateSchedule.getText().contains(location)){
            SimpleUtils.pass("Create Schedule - Enter Budget: \"" + location +"\" as location displays correctly");
        } else
            SimpleUtils.fail("Enter Budget: Location not loaded or displays correctly",true);
        if (isElementLoaded(generateModalTitle,5) && generateModalTitle.getText().contains("Enter Budget")){
            SimpleUtils.pass("Create Schedule - Enter Budget: Enter Budget as subhead displays correctly");
        } else
            SimpleUtils.fail("Enter Budget: Enter Budget as subhead not loaded or displays incorrectly",true);
        if (isElementLoaded(editBudgetBtn,5) && editBudgetBtn.getText().contains("Edit")){
            SimpleUtils.pass("Create Schedule - Enter Budget: Edit button displays correctly");
        } else
            SimpleUtils.fail("Enter Budget: Edit button not loaded or displays incorrectly",true);
        if (isElementLoaded(editBudgetBtn,5) && editBudgetBtn.getText().contains("Edit")){
            SimpleUtils.pass("Create Schedule - Enter Budget: Edit button displays correctly");
        } else
            SimpleUtils.fail("Enter Budget: Edit button not loaded or displays incorrectly",true);
        if (isElementLoaded(backButton,5) && backButton.getText().contains("Back")){
            SimpleUtils.pass("Create Schedule - Enter Budget: Back button displays correctly");
        } else
            SimpleUtils.fail("Create Schedule - Enter Budget:  Back button not loaded or displays incorrectly",true);
        if (isElementLoaded(nextButtonOnCreateSchedule,5) && nextButtonOnCreateSchedule.getText().contains("Next")){
            SimpleUtils.pass("Create Schedule - Enter Budget: Next button displays correctly");
        } else
            SimpleUtils.fail("Create Schedule - Enter Budget: Next button not loaded or displays incorrectly",true);
    }

    @Override
    public List<String> setAndGetBudgetForNonDGFlow() throws Exception {
        List<String> budgetForNonDGFlow = new ArrayList<>();
        Float sumOfBudgetHours = 0.00f;
        if (isElementLoaded(editBudgetBtn, 5)) {
            clickTheElement(editBudgetBtn);
            if (isElementLoaded(operatingHoursCancelBtn, 10) && isElementLoaded(operatingHoursSaveBtn, 10)) {
                SimpleUtils.pass("Create Schedule - Enter Budget: Click on Edit button Successfully!");
                if (areListElementVisible(roleHoursRows, 5)) {
                    for (WebElement roleHoursRow : roleHoursRows) {
                        try {
                            WebElement forecastHour = roleHoursRow.findElement(By.cssSelector("td:nth-child(3)"));
                            WebElement budgetHour = roleHoursRow.findElement(By.cssSelector("input[type=\"number\"]"));
                            if (forecastHour != null && budgetHour != null) {
                                String forecastHourString = "";
                                forecastHourString = forecastHour.getText().trim().replaceAll("[a-zA-Z]", "");
                                float forecastHourFloat= Float.valueOf(forecastHourString);
                                float random = (float) (Math.random() * forecastHourFloat);
                                budgetHour.clear();
                                DecimalFormat decimalFormat =new DecimalFormat("#.00");
                                String value = decimalFormat.format(random);
                                System.out.println(forecastHourString);
                                System.out.println(forecastHourFloat);
                                System.out.println(random);
                                System.out.println(value);
                                budgetHour.sendKeys(value);
                                sumOfBudgetHours += Float.valueOf(value);
                                budgetForNonDGFlow.add(value);
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    clickTheElement(operatingHoursSaveBtn);
                    if (isElementEnabled(editBudgetBtn, 5)) {
                        SimpleUtils.pass("Create Schedule: Save the budget hours Successfully!");
                    } else
                        SimpleUtils.fail("Create Schedule: Click on Save the budget hours button failed, Next button is not enabled!", false);
                    String totalBudget = MyThreadLocal.getDriver().findElement(By.xpath("//th[contains(text(), \"Total\")]/following-sibling::th[2]")).getText().trim();
                    System.out.println("sumOfBudgetHours is " + sumOfBudgetHours);
                    if (sumOfBudgetHours == Float.valueOf(totalBudget)) {
                        budgetForNonDGFlow.add(sumOfBudgetHours.toString());
                        SimpleUtils.pass("Create Schedule - Enter Budget: The total budget value is consistent with the summary of the edited value");
                    } else
                        SimpleUtils.fail("Create Schedule - Enter Budget: The total budget value is inconsistent with the summary of the edited value, please check",true);
                }
            }
        } else
            SimpleUtils.fail("Create Schedule - Enter Budget: Edit button not loaded Successfully!", false);
        return budgetForNonDGFlow;
    }


    @FindBy(css = ".modal-dialog ")
    WebElement popUpDialog;
    @FindBy(css = ".modal-dialog .publish-confirm-modal-message-container-compliance")
    WebElement complianceWarningMsgInConfirmModal;
    @Override
    public String getMessageForComplianceWarningInPublishConfirmModal() throws Exception {
        if (isElementLoaded(popUpDialog, 5)){
            return popUpDialog.findElement(By.cssSelector(".publish-confirm-modal-message-container-compliance")).getText();
        } else {
            SimpleUtils.fail("No dialog pop up.", false);
        }
        return null;
    }

    @Override
    public boolean isComplianceWarningMsgLoad() throws Exception {
        if (isElementLoaded(complianceWarningMsgInConfirmModal, 10)){
            return true;
        }
        return false;
    }

    public boolean isPublishButtonLoaded() {
        try {
            if (isElementLoaded(publishSheduleButton))
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }


    @FindBy (css = "div.redesigned-modal")
    private WebElement deleteSchedulePopup;

    @FindBy (css = ".redesigned-modal input")
    private WebElement deleteScheduleCheckBox;

    @FindBy (css = ".redesigned-button-ok")
    private WebElement deleteButtonOnDeleteSchedulePopup;

    @FindBy (css = ".redesigned-button-cancel-gray")
    private WebElement cancelButtonOnDeleteSchedulePopup;

    @Override
    public void unGenerateActiveScheduleScheduleWeek() throws Exception {

        if(isElementLoaded(deleteScheduleButton, 60)){
            click(deleteScheduleButton);
            if(isElementLoaded(deleteSchedulePopup, 25)
                    && isElementLoaded(deleteScheduleCheckBox, 5)
                    && isElementLoaded(deleteButtonOnDeleteSchedulePopup, 5)){
                click(deleteScheduleCheckBox);
                waitForSeconds(1);
                click(deleteButtonOnDeleteSchedulePopup);
                if (isElementLoaded(generateSheduleButton, 60)) {
                    SimpleUtils.pass("Schedule Page: Active Week ('" + getActiveWeekText() + "') Ungenerated Successfully.");
                } else {
                    SimpleUtils.fail("Schedule Page: Active Week ('" + getActiveWeekText() + "') isn't deleted successfully!", false);
                }
            } else
                SimpleUtils.fail("Schedule Page: Delete schedule popup or delete schedule Button not loaded for the week: '"
                        + getActiveWeekText() + "'.", false);

        } else
            SimpleUtils.fail("Schedule Page: Delete schedule button not loaded to Ungenerate the Schedule for the Week : '"
                    + getActiveWeekText() + "'.", false);
    }

    @FindBy (css = ".day-week-picker-period-week")
    private List<WebElement> currentWeeks;

    @FindBy(className = "day-week-picker-arrow-right")
    private WebElement calendarNavigationNextWeekArrow;
    @Override
    public void unGenerateActiveScheduleFromCurrentWeekOnward(int loopCount) throws Exception {
        if (areListElementVisible(currentWeeks, 10)) {
            for (int i = 0; i < currentWeeks.size(); i++) {
                // Current week is at the center by default, since we don't need to ungenerate the schedule for previous week
                if (loopCount == 0) {
                    if (i == 0) {
                        continue;
                    }
                }
                click(currentWeeks.get(i));
                CreateSchedulePage createSchedulePage = new ConsoleCreateSchedulePage();
                if (createSchedulePage.isWeekGenerated()) {
                    unGenerateActiveScheduleScheduleWeek();
                }
                if (i == (currentWeeks.size() - 1) && isElementLoaded(calendarNavigationNextWeekArrow, 5)) {
                    click(calendarNavigationNextWeekArrow);
                    loopCount += 1;
                    unGenerateActiveScheduleFromCurrentWeekOnward(loopCount);
                }
            }
        }else {
            SimpleUtils.fail("Current Weeks' elements not loaded Successfully!", false);
        }
    }



    @FindBy(css = "[ng-click=\"controlPanel.fns.publishConfirmation($event, false)\"]")
    private WebElement publishButton;
    @FindBy(css = "lg-button[label*=\"Republish\"]")
    private WebElement republishButton;

    public boolean isPublishButtonLoadedOnSchedulePage() throws Exception {
        boolean isPublishButtonLoaded = false;
        if (isElementLoaded(publishButton, 4)){
            isPublishButtonLoaded = true;
            SimpleUtils.report("Publish button loaded successfully on schedule page! ");
        } else
            SimpleUtils.report("Publish button loaded fail on schedule page! ");
        return isPublishButtonLoaded;
    }

    public boolean isRepublishButtonLoadedOnSchedulePage() throws Exception {
        boolean isRepublishButtonLoaded = false;
        if (isElementLoaded(republishButton, 4)){
            isRepublishButtonLoaded = true;
            SimpleUtils.report("Republish button loaded successfully on schedule page! ");
        } else
            SimpleUtils.report("Republish button loaded fail on schedule page! ");
        return isRepublishButtonLoaded;
    }


    @FindBy(css = "[ng-class=\"{'active': config.partialSchedule}\"]")
    WebElement partialCopyOption;
    @Override
    public boolean isPartialCopyOptionLoaded() throws Exception {
        if (isElementLoaded(partialCopyOption, 10)){
            return true;
        }
        return false;
    }


    @FindBy(css = "div[ng-click=\"back()\"]")
    private WebElement backBtnOnCreateScheduleWindow;
    @Override
    public void clickBackBtnAndExitCreateScheduleWindow() throws Exception {
        if (isElementEnabled(backBtnOnCreateScheduleWindow,10)) {
            click(backBtnOnCreateScheduleWindow);
            click(backBtnOnCreateScheduleWindow);
            if (isElementEnabled(backBtnOnCreateScheduleWindow,10)) {
                click(backBtnOnCreateScheduleWindow);
            }
        }else {
            SimpleUtils.fail("Back button on create schedule popup window is not loaded Successfully!", false);
        }
    }

    @Override
    public void clickExitBtnToExitCreateScheduleWindow() throws Exception {
        if (isElementEnabled(backBtnOnCreateScheduleWindow,10)) {
            click(backBtnOnCreateScheduleWindow);
        }else {
            SimpleUtils.fail("Exit button on create schedule popup window is not loaded Successfully!", false);
        }
    }


    @FindBy(css = "lg-button[label*=\"ublish\"] span span")
    private WebElement txtPublishSheduleButton;
    @Override
    public void verifyLabelOfPublishBtn(String labelExpected) throws Exception {
        if (isElementLoaded(txtPublishSheduleButton,5)){
            if (txtPublishSheduleButton.getText().equals(labelExpected)){
                SimpleUtils.pass("Label on publish button is correct!");
            } else {
                SimpleUtils.fail("Label on publish button is incorrect!",false);
            }
        } else {
            SimpleUtils.fail("publish button fail to load!",false);
        }
    }


    @FindBy(css = "div[ng-repeat=\"schedule in previousWeeksSchedules\"] div")
    private List<WebElement> previousWeeks;
    @FindBy(css = ".schedule-disabled-tooltip")
    private WebElement scheduleDisabledTooltip;
    @Override
    public void verifyPreviousWeekWhenCreateAndCopySchedule(String weekInfo, boolean shouldBeSelected) throws Exception {
        //Need to prepare 2 previous week to check.
        if (areListElementVisible(previousWeeks, 10) && previousWeeks.size()>=2){
            for (WebElement element: previousWeeks){
                String weekDayInfo = element.findElement(By.cssSelector(".generate-modal-week-name")).getText().split("\n")[1];
                if (weekInfo.equalsIgnoreCase(weekDayInfo)){
                    if (!element.getAttribute("class").contains("disabled")) {
                        if (shouldBeSelected == !element.findElement(By.cssSelector(".generate-modal-week")).getAttribute("class").contains("disabled")){
                            SimpleUtils.pass("Should the week:"+weekInfo+" be selected is correct!");
                        } else {
                            SimpleUtils.fail("Should the week:"+weekInfo+" be selected is not the expected!", false);
                        }
                    } else
                        SimpleUtils.fail("This week is disbled and cannot be selected! ", false);
                }
            }
        } else {
            SimpleUtils.fail("There is no previous week to copy", false);
        }
    }

    @Override
    public void verifyTooltipForCopyScheduleWeek(String weekInfo) throws Exception {
        //Need to prepare 2 previous week to check.
        if (areListElementVisible(previousWeeks, 10) && previousWeeks.size()>=2){
            for (WebElement element: previousWeeks){
                String weekDayInfo = element.findElement(By.cssSelector(".generate-modal-week-name")).getText().split("\n")[1];
                if (weekInfo.equalsIgnoreCase(weekDayInfo)){
                    mouseHover(element);
                    String tooltipText = "Policy: Max. 2 violations and 0% over budget";
                    if (scheduleDisabledTooltip.getAttribute("style").contains("visible") && tooltipText.equalsIgnoreCase(scheduleDisabledTooltip.getText())){
                        SimpleUtils.pass("Tooltip is expected!");
                    } else {
                        SimpleUtils.fail("Tooltip should display when mouse hover the week!", false);
                    }
                }
            }
        } else {
            SimpleUtils.fail("There is no previous week to copy", false);
        }
    }


    @FindBy(css = ".generate-modal-week-violations-different-hours")
    private WebElement differrentOperatingHoursInfo;
    @Override
    public void verifyDifferentOperatingHours(String weekInfo) throws Exception {
        if (areListElementVisible(previousWeeks, 10) && previousWeeks.size()>=2){
            for (WebElement element: previousWeeks){
                String weekDayInfo = element.findElement(By.cssSelector(".generate-modal-week-name")).getText().split("\n")[1];
                if (weekInfo.equalsIgnoreCase(weekDayInfo)){
                    String differentOperatingHrsInfo = "*Different operating hours";
                    if (isElementLoaded(differrentOperatingHoursInfo,5) && differrentOperatingHoursInfo.getText().contains(differentOperatingHrsInfo)){
                        SimpleUtils.pass("Differrent Operating Hours info is expected!");
                    } else {
                        SimpleUtils.fail("Differrent Operating Hours info is not loaded!", false);
                    }
                }
            }
        } else {
            SimpleUtils.fail("There is no previous week to copy", false);
        }
    }

    public boolean isCreateScheduleBtnLoadedOnSchedulePage() throws Exception {
        boolean isCreateScheduleBtnLoaded = false;
        if (isElementLoaded(generateSheduleButton, 4)) {
            isCreateScheduleBtnLoaded = true;
            SimpleUtils.report("Create Schedule button loaded successfully on schedule page! ");
        } else
            SimpleUtils.report("Create Schedule button loaded fail on schedule page! ");
        return isCreateScheduleBtnLoaded;
    }


    @FindBy (css = "[ng-click=\"openSearchDropDown()\"]")
    private WebElement openSearchLocationBoxButton;

    @FindBy (css = "[ng-click=\"closeSearchDropDown()\"]")
    private WebElement closeSearchLocationBoxButton;

    @FindBy (css = "input[placeholder=\"Search Locations\"]")
    private WebElement searchLocationOnUngenerateSchedulePage;

    @FindBy (css = "div[class=\"lg-picker-input__wrapper lg-ng-animate\"] .lg-search-options__option-wrapper")
    private List<WebElement> locationsInLocationSelectorOnUngenerateSchedulePage;

    @FindBy (css = "div.schedule-summary-location-picker span")
    private WebElement selectedLocationOnUngenerateSchedulePage;

    public void selectRandomOrSpecificLocationOnUngenerateScheduleEditOperatingHoursPage(String specificLocationName) throws Exception {
        if (isElementLoaded(openSearchLocationBoxButton, 60)||(isElementLoaded(closeSearchLocationBoxButton, 60))){
            if(isElementLoaded(openSearchLocationBoxButton, 5)){
                click(openSearchLocationBoxButton);
            }
            if(isElementLoaded(searchLocationOnUngenerateSchedulePage, 5)){
                click(searchLocationOnUngenerateSchedulePage);
            } else
                SimpleUtils.fail("Ungenerate schedule page: Search locations box fail to open! ", false);

            if(areListElementVisible(locationsInLocationSelectorOnUngenerateSchedulePage, 5)
                    && locationsInLocationSelectorOnUngenerateSchedulePage.size() >0){
                String locationName = specificLocationName;
                if (locationName == null){
                    int randomLocations = (new Random()).nextInt(locationsInLocationSelectorOnUngenerateSchedulePage.size());
                    locationName = locationsInLocationSelectorOnUngenerateSchedulePage.get(randomLocations).getText();
                }

                if(isElementLoaded(searchLocationOnUngenerateSchedulePage, 5)){
                    searchLocationOnUngenerateSchedulePage.sendKeys(locationName);
                }
                waitForSeconds(3);
                click(locationsInLocationSelectorOnUngenerateSchedulePage.get(0));
//                if(areListElementVisible(locationsInLocationSelectorOnUngenerateSchedulePage)){
//
//                    click(locationsInLocationSelectorOnUngenerateSchedulePage.get(randomLocations));
//                }

                if(selectedLocationOnUngenerateSchedulePage.getText().equals(locationName)){
                    SimpleUtils.pass("Ungenerate schedule page: Select locations on Edit Operating hours successfully! ");
                } else
                    SimpleUtils.fail("Ungenerate schedule page: Select locations on Edit Operating hours failed! ", false);

            } else
                SimpleUtils.fail("Ungenerate schedule page: Locations fail to list! ", false);
        } else
            SimpleUtils.fail("Ungenerate schedule page: Search location buttons fail to load! ", false);
    }



    @FindBy(css = "span[ng-if=\"canEditWorkingHours\"]")
    private List<WebElement> editOperatingHousButtonOnUngenerateSchedulePage;

    public boolean checkIfEditOperatingHoursButtonsAreShown() throws Exception {
        boolean areEditButtonShown = false;
        if(areListElementVisible(editOperatingHousButtonOnUngenerateSchedulePage, 10)){
            areEditButtonShown = true;
            SimpleUtils.report("Edit operating hours buttons are shown on ungenerate schedule page! ");
        } else if(isElementLoaded(operatingHoursEditBtn, 5)){
            areEditButtonShown = true;
            SimpleUtils.report("Edit operating hours button are shown on create schedule page! ");
        } else
            SimpleUtils.report("Edit operating hours buttons are not shown! ");
        return areEditButtonShown;
    }


    @FindBy(css = ".modal-dialog .lg-picker-input")
    private WebElement locationInput;
    @FindBy(css = ".modal-dialog input[placeholder=\"Search Location\"]")
    private WebElement searchInputForLocationInPopupWindow;
    @FindBy (css = ".modal-dialog .lg-picker-input__wrapper.lg-ng-animate.ng-hide .input-faked")
    private WebElement selectedLocationOnCreateScheduleWindow;
    @Override
    public void chooseLocationInCreateSchedulePopupWindow(String location) throws Exception{
        if (isElementLoaded(locationInput, 60)){
            click(locationInput);
            if(areListElementVisible(locationsInLocationSelectorOnUngenerateSchedulePage, 5)
                    && locationsInLocationSelectorOnUngenerateSchedulePage.size() >0){
                if (location == null){
                    int randomLocations = (new Random()).nextInt(locationsInLocationSelectorOnUngenerateSchedulePage.size());
                    location = locationsInLocationSelectorOnUngenerateSchedulePage.get(randomLocations).getText();
                }

                if(isElementLoaded(searchInputForLocationInPopupWindow, 5)){
                    searchInputForLocationInPopupWindow.sendKeys(location);
                }
                waitForSeconds(3);
                click(locationsInLocationSelectorOnUngenerateSchedulePage.get(0));
                Actions actions = new Actions(getDriver());
                actions.moveByOffset(0, 0).click().build().perform();
                if(selectedLocationOnCreateScheduleWindow.getAttribute("innerHTML").replace("\n","").trim().equalsIgnoreCase(location)){
                    SimpleUtils.pass("Create schedule window: Select locations on Edit Operating hours successfully! ");
                } else
                    SimpleUtils.fail("Create schedule window: Select locations on Edit Operating hours failed! ", false);

            } else {
                SimpleUtils.fail("Create schedule window: Locations fail to list! ", false);
            }
        } else
            SimpleUtils.fail("location input in popup window fail to load! ", false);
    }


    @Override
    public boolean isCopyScheduleWindow() throws Exception {
        if (areListElementVisible(createModalWeeks,10)){
            return true;
        }
        return false;
    }


    @Override
    public void clickOnSchedulePublishButton() throws Exception {
        // TODO Auto-generated method stub
        if(isElementEnabled(publishSheduleButton)){
            click(publishSheduleButton);
            if(isElementEnabled(publishConfirmBtn))
            {
//                WebElement switchIframe = getDriver().findElement(By.xpath("//iframe[@id='walkme-proxy-iframe']"));
//			    getDriver().switchTo().frame(switchIframe);
//			    if(isElementEnabled(closeLegionPopUp)){
//			        click(closeLegionPopUp);
//                }
//                getDriver().switchTo().defaultContent();
                click(publishConfirmBtn);
//			    if(isElementLoaded(closeLegionPopUp)){
//			        click(closeLegionPopUp);
//                }
                SimpleUtils.pass("Schedule published successfully for week: '"+ getActiveWeekText() +"'");
                // It will pop up a window: Welcome to Legion!
                if (isElementLoaded(closeButton, 5)) {
                    click(closeButton);
                }
                if(isElementEnabled(successfulPublishOkBtn))
                {
                    click(successfulPublishOkBtn);
                }
            }
        }
    }


    @Override
    public void clickConfirmBtnOnPublishConfirmModal() throws Exception {
        if (isElementLoaded(publishConfirmBtn)) {
            clickTheElement(publishConfirmBtn);
            SimpleUtils.pass("Schedule published successfully for week: '" + getActiveWeekText() + "'");
            // It will pop up a window: Welcome to Legion!
            if (isElementLoaded(closeButton, 5)) {
                clickTheElement(closeButton);
            }
            if (isElementLoaded(successfulPublishOkBtn)) {
                clickTheElement(successfulPublishOkBtn);
            }
            if (isElementLoaded(publishSheduleButton, 5)) {
                // Wait for the Publish button to disappear.
                waitForSeconds(10);
            }
        } else {
            SimpleUtils.fail("Comfirm button is not loaded successfully!", false);
        }
    }


    @FindBy (css = "div.generate-modal-assignments-select-all input-field input")
    private WebElement selectAllShiftAssignment;
    public void copyAllPartialSchedule () throws Exception {
        if (isElementLoaded(copyPartialScheduleSwitch, 10)){
            click(copyPartialScheduleSwitch);
        } else
            SimpleUtils.fail("Copy Partial Schedule Switch loaded fail! ", false);
        clickTheElement(nextButtonOnCreateSchedule);
        if (isElementLoaded(selectAllShiftAssignment, 10)) {
            clickTheElement(selectAllShiftAssignment);
        } else
            SimpleUtils.fail("Select All Shift Assignment loaded fail! ", false);
    }


    @FindBy (css = "[value=\"config.createAsOpenShifts\"] span")
    private WebElement onlyCopyShiftsSwitch;
    @FindBy (xpath = "//div[contains(text(),'Only copy shifts')]")
    private WebElement onlyCopyShiftsSwitchMessage;
    @FindBy (css = "[data-tootik=\"Option to copy assignments from selected week based on work roles\"]")
    private WebElement onlyCopyShiftsSwitchTooltip;
    @FindBy (xpath = "//div[contains(text(),'Only copy partial assignments')]")
    private WebElement onlyCopyPartialAssignmentsSwitchMessage;

    @Override
    public boolean checkOnlyCopyShiftsSwitchDisplayOrNot () throws Exception {
        boolean switchDisplay = false;
        if (isElementLoaded(onlyCopyShiftsSwitch, 5)
                && isElementLoaded(onlyCopyShiftsSwitchMessage, 5)
                && isElementLoaded(onlyCopyShiftsSwitchTooltip, 5)) {
            switchDisplay = true;
            SimpleUtils.report("The only copy shifts switch is display! ");
        } else
            SimpleUtils.report("The only copy shifts switch is not display! ");
        return switchDisplay;
    }

    @Override
    public boolean checkOnlyCopyShiftsSwitchEnableOrNot () throws Exception {
        boolean switchEnabled = false;
        if (isElementLoaded(onlyCopyShiftsSwitch, 5)
                && isElementLoaded(onlyCopyShiftsSwitchMessage, 5)) {
            if (onlyCopyShiftsSwitchMessage.getAttribute("class").contains("active")){
                switchEnabled = true;
            }
        } else
            SimpleUtils.fail("The only copy shifts switch fail to load! ", false);
        return switchEnabled;
    }

    @Override
    public void turnOnOrTurnOffOnlyCopyShiftsSwitch (boolean action) throws Exception {
        if (isElementLoaded(onlyCopyShiftsSwitch, 5)
                && isElementLoaded(onlyCopyShiftsSwitchMessage, 5)) {
            if (onlyCopyShiftsSwitchMessage.getAttribute("class").contains("active")){
                if (action) {
                    SimpleUtils.pass("The switch already been enabled! ");
                } else {
                    clickTheElement(onlyCopyShiftsSwitch);
                    SimpleUtils.pass("The switch been enabled successfully! ");
                }
            } else {
                if (action) {
                    clickTheElement(onlyCopyShiftsSwitch);
                    SimpleUtils.pass("The switch been disabled successfully! ");
                } else {
                    SimpleUtils.pass("The switch already been disabled! ");
                }
            }
        } else
            SimpleUtils.fail("The only copy shifts switch fail to load! ", false);
    }

    @Override
    public boolean checkOnlyCopyPartialAssignmentSwitchEnableOrNot () throws Exception {
        boolean switchEnabled = false;
        if (isElementLoaded(copyPartialScheduleSwitch, 5)
                && isElementLoaded(onlyCopyPartialAssignmentsSwitchMessage, 5)) {
            if (onlyCopyPartialAssignmentsSwitchMessage.getAttribute("class").contains("active")){
                switchEnabled = true;
            }
        } else
            SimpleUtils.fail("The Only copy partial assignments switch fail to load! ", false);
        return switchEnabled;
    }
}
