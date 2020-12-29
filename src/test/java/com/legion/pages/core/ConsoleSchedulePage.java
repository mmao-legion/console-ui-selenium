package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.SchedulePage;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.*;

import static com.legion.utils.MyThreadLocal.getCurrentTestMethodName;
import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleSchedulePage extends BasePage implements SchedulePage {


    String dayWeekPicker;

    @FindBy(xpath = "//*[@id='legion-app']/div/div[2]/div/div/div/div[1]/navigation/div/div[6]")
    private WebElement goToScheduleButton;

    @FindBy(css = "div[helper-text*='Work in progress Schedule'] span.legend-label")
    private WebElement draft;

    @FindBy(css = "span[ng-if='canEditEstimatedHourlyWage(budget)']")
    private List<WebElement> scheduleDraftWages;

    @FindBy (xpath = "//span[contains(text(),'Schedule')]")
    private WebElement ScheduleSubMenu;

    @FindBy(css = "div[helper-text-position='top'] span.legend-label")
    private WebElement published;

    @FindBy(css = "div[helper-text*='final per schedule changes'] span.legend-label")
    private WebElement finalized;

    @FindBy(className = "overview-view")
    private WebElement overviewSectionElement;

    @FindBy(css = "div.sub-navigation-view-link.active")
    private WebElement activatedSubTabElement;

    @FindBy(xpath = "//div[contains(@class,'sub-navigation-view')]//span[contains(text(),'Schedule')]")
    private WebElement goToScheduleTab;

    @FindBy(css = "div[ng-click*='analyze'] span.sch-control-button-label")
    private WebElement analyze;

    @FindBy(css = "div[ng-click*='edit'] span.sch-control-button-label")
    private WebElement edit;

    @FindBy(className="schedule-status-title")
    private List<WebElement> scheduleOverviewWeeksStatus;

    @FindBy(css = "div.fx-center.left-banner")
    private List<WebElement> overviewPageScheduleWeekDurations;

    @FindBy(xpath = "//span[contains(text(),'Projected Sales')]")
    private WebElement goToProjectedSalesTab;

    @FindBy(css = "ui-view[name='forecastControlPanel'] span.highlight-when-help-mode-is-on")
    private WebElement salesGuidance;

    @FindBy(css = "div[ng-click*='refresh'] span.sch-control-button-label")
    private WebElement refresh;

    @FindBy(xpath = "//div[contains(text(),'Guidance')]")
    private WebElement guidance;

    @FindBy(xpath = "//span[contains(text(),'Staffing Guidance')]")
    private WebElement goToStaffingGuidanceTab;

//	@FindBy(className="sch-calendar-day-dimension")
//	private List<WebElement> ScheduleCalendarDayLabels;

    @FindBy(css = "div.sub-navigation-view-link")
    private List<WebElement> ScheduleSubTabsElement;

    @FindBy(css = "[ng-click='gotoNextWeek($event)']")
    private WebElement calendarNavigationNextWeekArrow;

    @FindBy(css = "[ng-click=\"gotoPreviousWeek($event)\"]")
    private WebElement salesForecastCalendarNavigationPreviousWeekArrow;

    @FindBy(css = "[ng-click=\"regenerateFromOverview()\"]")
    private WebElement generateSheduleButton;

    @FindBy(css = "[ng-click=\"controlPanel.fns.publishConfirmation($event, false)\"]")
    private WebElement publishSheduleButton;

    @FindBy(css = "div.sch-view-dropdown-summary-content-item-heading.ng-binding")
    private WebElement analyzePopupLatestVersionLabel;

    @FindBy(css = "[ng-click=\"controlPanel.fns.analyzeAction($event)\"]")
    private WebElement scheduleAnalyzeButton;

    @FindBy(className = "sch-schedule-analyze-content")
    private WebElement scheduleAnalyzePopup;

    @FindBy(className = "sch-schedule-analyze-dismiss")
    private WebElement scheduleAnalyzePopupCloseButton;

    @FindBy(css = "[ng-click=\"goToSchedule()\"]")
    private WebElement checkOutTheScheduleButton;

    @FindBy(xpath = "//button[contains(text(),'UPDATE')]")
    private WebElement updateAndGenerateScheduleButton;

    @FindBy(className = "console-navigation-item")
    private List<WebElement> consoleNavigationMenuItems;

    @FindBy(css = "[ng-click=\"callOkCallback()\"]")
    private WebElement editAnywayPopupButton;

    @FindBy(css = "[ng-if=\"canShowNewShiftButton()\"]")
    private WebElement addNewShiftOnDayViewButton;

    @FindBy(className = "sch-control-button-cancel")
    private WebElement scheduleEditModeCancelButton;

    @FindBy(css = "[ng-click=\"regenerateFromOverview()\"]")
    private WebElement scheduleGenerateButton;

    @FindBy(css = "#legion-app navigation div:nth-child(4)")
    private WebElement analyticsConsoleName;

    @FindBy(className = "left-banner")
    private List<WebElement> weeklyScheduleDateElements;


//    @FindBy(css = "[ng-click=\"printAction($event)\"]")
//    private WebElement printButton;
//
//    @FindBy(css = "[ng-click=\"showTodos($event)\"]")
//    private WebElement todoButton;
//
//    @FindBy (css = ".horizontal.is-shown")
//    private WebElement todoSmartCard;



    @FindBy(css = "[label=\"Print\"]")
    private WebElement printButtonInPrintLayout;

    @FindBy(css = "[ng-click=\"controlPanel.fns.publishConfirmation($event, false)\"]")
    private WebElement publishButton;

    @FindBy(css = "[ng-if='!loading']")
    private WebElement weeklyScheduleTableBodyElement;

    @FindBy(css = "[ng-if='!isLocationGroup()']")
    private List<WebElement> weeklyScheduleStatusElements;

    @FindBy(css = "[ng-click=\"confirmPublishAction()\"]")
    private WebElement schedulePublishButton;

    @FindBy(css = "[ng-click=\"OkAction()\"]")
    private WebElement successfullyPublishedOkOption;

    @FindBy(css = "span[ng-click='c.action()']")
    private WebElement enterBudgetLink;

    @FindBy(css = "span.header-text.fl-left.ng-binding")
    private WebElement budgetHeader;

    @FindBy(css = "div.day-week-picker-period-active")
    private WebElement daypicker;

    @FindBy (css = "div.day-week-picker-period")
    private List<WebElement> dayPickerAllDaysInDayView;

    @FindBy(css = ".day-week-picker-period fx-center ng-scope day-week-picker-period-active day-week-picker-period-week")
    private WebElement currentActiveWeeks;


    @FindBy(css = "div.row-fx.schedule-table-row.ng-scope")
    private List<WebElement> schedulesForWeekOnOverview;

    @FindBy(css = ".cancel-action-text")
    private WebElement enterBudgetCancelButton;

    @FindBy(css = "div.col-sm-10.plr-0-0 > div:nth-child(1) > div > span")
    private WebElement returnToOverviewTab;

    @FindBy(css = "div.row-name-field.ng-binding.ng-scope")
    private WebElement sumOfBudgetHour;

    @FindBy(css = "table:nth-child(2) tr.table-row.ng-scope")
    private WebElement budgetPopUpRows;

    @FindBy(css = "table:nth-child(2) tr.table-row.ng-scope td:nth-child(3)")
    private List<WebElement> guidanceHour;

    @FindBy(css = "input[ng-class='hoursFieldClass(budget)']")
    private List<WebElement> budgetEditHours;

    @FindBy(css = "table td:nth-child(2)")
    private List<WebElement> budgetDisplayOnScheduleSmartcard;

    @FindBy (css = "table td:nth-child(3)")
    private List<WebElement> scheduleDisplayOnScheduleSmartcard;

    @FindBy(xpath = "//div[contains(text(),'Weekly')]/following-sibling::h1")
    private WebElement budgetOnbudgetSmartCard;

    @FindBy (css = "div.console-navigation-item-label.Schedule")
    private WebElement consoleSchedulePageTabElement;

    @FindBy (css = "week-schedule[legacy='legacyObjectForNewSchedule']")
    private WebElement scheduleTableWeekView;

    @FindBy (css = "div.sch-day-view-grid")
    private WebElement scheduleTableDayView;

    @FindBy (css = "div.sch-day-view-shift-worker-detail")
    private List<WebElement> scheduleTableWeekViewWorkerDetail;

    @FindBy (css = "div.lg-button-group-first")
    private WebElement scheduleDayView;

    @FindBy (css = "div.lg-button-group-last")
    private WebElement scheduleWeekView;

    @FindBy (css = "div.card-carousel-carousel")
    private WebElement smartcard;

    @FindBy (css = "img.holiday-logo-image")
    private WebElement storeClosed;

    @FindBy (css = "div.day-week-picker-period-active")
    private WebElement currentActiveDay;

    public ConsoleSchedulePage() {
        PageFactory.initElements(getDriver(), this);
    }

    List<String> scheduleWeekDate = new ArrayList<String>();
    List<String> scheduleWeekStatus = new ArrayList<String>();

    Map<String, String> weeklyTableRowsDatesAndStatus = new LinkedHashMap<String, String>();

    final static String consoleScheduleMenuItemText = "Schedule";

    public void clickOnScheduleConsoleMenuItem() {
        if (consoleNavigationMenuItems.size() != 0) {
            WebElement consoleScheduleMenuElement = SimpleUtils.getSubTabElement(consoleNavigationMenuItems, consoleScheduleMenuItemText);
            activeConsoleName = analyticsConsoleName.getText();
            click(consoleScheduleMenuElement);
            SimpleUtils.pass("Console Menu Loaded Successfully!");
        } else {
            SimpleUtils.fail("Console Menu Items Not Loaded Successfully!", false);
        }
    }

    @Override
    public void goToSchedulePage() throws Exception {

        checkElementVisibility(goToScheduleButton);
        activeConsoleName = analyticsConsoleName.getText();
        click(goToScheduleButton);
        SimpleUtils.pass("Schedule Page Loading..!");

        if (isElementLoaded(draft)) {
            SimpleUtils.pass("Draft is Displayed on the page");
        } else {
            SimpleUtils.fail("Draft not displayed on the page", true);
        }

        if (isElementLoaded(published)) {
            SimpleUtils.pass("Published is Displayed on the page");
        } else {
            SimpleUtils.fail("Published not displayed on the page", true);
        }

        if (isElementLoaded(finalized)) {
            SimpleUtils.pass("Finalized is Displayed on the page");
        } else {
            SimpleUtils.fail("Finalized not displayed on the page", true);
        }
    }


    @Override
    public boolean isSchedulePage() throws Exception {
        if (isElementLoaded(overviewSectionElement)) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public Boolean verifyActivatedSubTab(String SubTabText) throws Exception {
        if (isElementLoaded(activatedSubTabElement)) {
            if (activatedSubTabElement.getText().toUpperCase().contains(SubTabText)) {
                return true;
            }
        } else {
            SimpleUtils.fail("Schedule Page not loaded successfully", true);
        }
        return false;
    }

    @Override
    public void goToSchedule() throws Exception {

        checkElementVisibility(goToScheduleTab);
        activeConsoleName = analyticsConsoleName.getText();
        click(goToScheduleTab);
        SimpleUtils.pass("Schedule Page Loading..!");

        if (isElementLoaded(analyze)) {
            SimpleUtils.pass("Analyze is Displayed on Schdule page");
        } else {
            SimpleUtils.fail("Analyze not Displayed on Schedule page", true);
        }
        if (isElementLoaded(edit)) {
            SimpleUtils.pass("Edit is Displayed on Schedule page");
        } else {
            SimpleUtils.fail("Edit not Displayed on Schedule page", true);
        }
    }


    @Override
    public void goToProjectedSales() throws Exception {
        checkElementVisibility(goToProjectedSalesTab);
        click(goToProjectedSalesTab);
        SimpleUtils.pass("ProjectedSales Page Loading..!");

        if (isElementLoaded(salesGuidance)) {
            SimpleUtils.pass("Sales Forecast is Displayed on the page");
        } else {
            SimpleUtils.fail("Sales Forecast not Displayed on the page", true);
        }

        verifyRefreshBtnOnSalesForecast();
    }

    public void verifyRefreshBtnOnSalesForecast() throws Exception {
        if (getCurrentTestMethodName().contains("InternalAdmin")) {
            if (isElementLoaded(refresh)) {
                SimpleUtils.pass("Refresh button is Displayed on Sales Forecast for Legion Internal Admin");
            } else {
                SimpleUtils.fail("Refresh button not Displayed on Sales Forecast for Legion Internal Admin", true);
            }
        } else {
            if (!isElementLoaded(refresh)) {
                SimpleUtils.pass("Refresh button should not be Displayed on Sales Forecast for other than Legion Internal Admin");
            } else {
                SimpleUtils.fail("Refresh button Displayed on Sales Forecast for other than Internal Admin", true);
            }
        }
    }


    @Override
    public void goToStaffingGuidance() throws Exception {
        checkElementVisibility(goToStaffingGuidanceTab);
        click(goToStaffingGuidanceTab);
        SimpleUtils.pass("StaffingGuidance Page Loading..!");

        if (isElementLoaded(guidance)) {
            SimpleUtils.pass("Guidance is Displayed on Staffing Guidance page");
        } else {
            SimpleUtils.fail("Guidance not Displayed on Staffing Guidance page", true);
        }

        if (isElementLoaded(analyze)) {
            SimpleUtils.pass("Analyze is Displayed on Staffing Guidance page");
        } else {
            SimpleUtils.fail("Analyze not Displayed on Staffing Guidance page", true);
        }
    }

    @Override
    public boolean isSchedule() throws Exception {
        if (isElementLoaded(goToScheduleTab)) {
            return true;
        } else {
            return false;
        }

    }


    @Override
    public void clickOnWeekView() throws Exception {
        WebElement scheduleWeekViewButton = MyThreadLocal.getDriver().
                findElement(By.cssSelector("[ng-click=\"selectDayWeekView($event, 'week')\"]"));
        if (isElementLoaded(scheduleWeekViewButton)) {
            if (!scheduleWeekViewButton.getAttribute("class").toString().contains("enabled")) {
                click(scheduleWeekViewButton);
            }
            SimpleUtils.pass("Schedule page week view loaded successfully!");
        } else {
            SimpleUtils.fail("Schedule Page Week View Button not Loaded Successfully!", true);
        }
    }


    @Override
    public void clickOnDayView() throws Exception {
        WebElement scheduleDayViewButton = MyThreadLocal.getDriver().
                findElement(By.cssSelector("[ng-click=\"selectDayWeekView($event, 'day')\"]"));
        if (isElementLoaded(scheduleDayViewButton)) {
            if (!scheduleDayViewButton.getAttribute("class").toString().contains("enabled")) {
                click(scheduleDayViewButton);
            }
            SimpleUtils.pass("Schedule Page day view loaded successfully!");
        } else {
            SimpleUtils.fail("Schedule Page Day View Button not Loaded Successfully!", true);
        }
    }


    @Override
    public HashMap<String, Float> getScheduleLabelHoursAndWages() throws Exception {

        String budgetedHours = "";
        String scheduledHours = "";
        String otherHours = "";
        String wagesBudgetedCount = "";
        String wagesScheduledCount = "";
        HashMap<String, Float> scheduleHoursAndWages = new HashMap<String, Float>();
        List<WebElement> budgetedScheduledLabelsDivElement = MyThreadLocal.getDriver().findElements(By.className("mt-18"));
        if (isElementLoaded(budgetedScheduledLabelsDivElement.get(0))) {
            for (WebElement budgetedScheduledLabelDiv : budgetedScheduledLabelsDivElement) {

                if (budgetedScheduledLabelDiv.getText().contains("Wages") && budgetedScheduledLabelDiv.getText().contains("Guidance")
                        || budgetedScheduledLabelDiv.getText().contains("Wages") && budgetedScheduledLabelDiv.getText().contains("Budgeted")) {
                    wagesBudgetedCount = budgetedScheduledLabelDiv.findElement(By.className("sch-control-kpi")).getText().replace(" Wages", "").replace("$", "");
                    scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages, wagesBudgetedCount, "wagesBudgetedCount");
                } else if (budgetedScheduledLabelDiv.getText().contains("Wages") && budgetedScheduledLabelDiv.getText().contains("Scheduled")) {
                    wagesScheduledCount = budgetedScheduledLabelDiv.findElement(By.className("sch-control-kpi")).getText().replace(" Wages", "").replace("$", "");
                    scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages, wagesScheduledCount, "wagesScheduledCount");

                } else if (budgetedScheduledLabelDiv.getText().contains("Budgeted") || budgetedScheduledLabelDiv.getText().contains("Guidance")) {
                    budgetedHours = budgetedScheduledLabelDiv.findElement(By.className("sch-control-kpi")).getText().replace(" Hrs", "");
                    scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages, budgetedHours, "budgetedHours");
                } else if (budgetedScheduledLabelDiv.getText().contains("Scheduled")) {
                    scheduledHours = budgetedScheduledLabelDiv.findElement(By.className("sch-control-kpi")).getText().replace(" Hrs", "");
                    scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages, scheduledHours, "scheduledHours");
                } else if (budgetedScheduledLabelDiv.getText().contains("Other")) {
                    otherHours = budgetedScheduledLabelDiv.findElement(By.className("sch-control-kpi")).getText().replace(" Hrs", "");
                    scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages, otherHours, "otherHours");
                }
            }
        }
        return scheduleHoursAndWages;
    }


    private HashMap<String, Float> updateScheduleHoursAndWages(HashMap<String, Float> scheduleHoursAndWages,
                                                               String hours, String hoursAndWagesKey) {
        scheduleHoursAndWages.put(hoursAndWagesKey, Float.valueOf(hours));
        return scheduleHoursAndWages;
    }

    @Override
    public synchronized List<HashMap<String, Float>> getScheduleLabelHoursAndWagesDataForEveryDayInCurrentWeek() throws Exception {
        List<HashMap<String, Float>> ScheduleLabelHoursAndWagesDataForDays = new ArrayList<HashMap<String, Float>>();
        List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("sch-calendar-day-dimension"));
        if (isScheduleDayViewActive()) {
            if (ScheduleCalendarDayLabels.size() != 0) {
                for (WebElement ScheduleCalendarDayLabel : ScheduleCalendarDayLabels) {
                    click(ScheduleCalendarDayLabel);
                    ScheduleLabelHoursAndWagesDataForDays.add(getScheduleLabelHoursAndWages());
                }
            } else {
                SimpleUtils.fail("Schedule Page Day View Calender not Loaded Successfully!", true);
            }
        } else {
            SimpleUtils.fail("Schedule Page Day View Button not Active!", true);
        }
        return ScheduleLabelHoursAndWagesDataForDays;
    }


    @Override
    public void clickOnScheduleSubTab(String subTabString) throws Exception {
        System.out.println("clickOnScheduleSubTab called1: " + subTabString);
        if (ScheduleSubTabsElement.size() != 0 && !verifyActivatedSubTab(subTabString)) {
            System.out.println("clickOnScheduleSubTab size: " + ScheduleSubTabsElement.size());
            for (WebElement ScheduleSubTabElement : ScheduleSubTabsElement) {
                System.out.println("ScheduleSubTabElement Text: " + ScheduleSubTabElement.getText());
                if (ScheduleSubTabElement.getText().equalsIgnoreCase(subTabString)) {
                    System.out.println("CLicked");
                    click(ScheduleSubTabElement);
                }
            }

        }

        if (verifyActivatedSubTab(subTabString)) {
            SimpleUtils.pass("Schedule Page: '" + subTabString + "' tab loaded Successfully!");
        } else {
            SimpleUtils.fail("Schedule Page: '" + subTabString + "' tab not loaded Successfully!", true);
        }
    }

    @Override
    public void navigateWeekViewOrDayViewToPastOrFuture(String nextWeekViewOrPreviousWeekView, int weekCount) {
        String currentWeekStartingDay = "NA";
        List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("sch-calendar-day-dimension"));
        for (int i = 0; i < weekCount; i++) {
            if (ScheduleCalendarDayLabels.size() != 0) {
                currentWeekStartingDay = ScheduleCalendarDayLabels.get(0).getText();
            }

            if (nextWeekViewOrPreviousWeekView.toLowerCase().contains("next") || nextWeekViewOrPreviousWeekView.toLowerCase().contains("future")) {
                try {
                    if (isElementLoaded(calendarNavigationNextWeekArrow)) {
                        calendarNavigationNextWeekArrow.click();
                        SimpleUtils.pass("Schedule Page Calender view for next week loaded successfully!");
                    }
                } catch (Exception e) {
//					SimpleUtils.fail("Schedule page Calender Next Week Arrows Not Loaded/Clickable after '"+currentWeekStartingDay+ "'", true);
                    SimpleUtils.report("Schedule page Calender Next Week Arrows Not Loaded/Clickable after '" + currentWeekStartingDay + "'");
                }
            } else {
                try {
                    if (isElementLoaded(salesForecastCalendarNavigationPreviousWeekArrow)) {
                        salesForecastCalendarNavigationPreviousWeekArrow.click();
                        SimpleUtils.pass("Schedule Page Calender view for Previous week loaded successfully!");
                    }
                } catch (Exception e) {
                    SimpleUtils.fail("Schedule page Calender Previous Week Arrows Not Loaded/Clickable after '" + currentWeekStartingDay + "'", true);
                }

            }
			/*if(! currentWeekStartingDay.equals(ScheduleCalendarDayLabels.get(0).getText()))
			{
				SimpleUtils.fail("Week After '"+currentWeekStartingDay+"' not Clickable!", true);
			}
			else {
				SimpleUtils.fail("Schedule page Calender Previous Week Arrows Not Loaded!", true);
			}*/
        }
    }


    @Override
    public Boolean isWeekGenerated() throws Exception {
        if (isElementLoaded(generateSheduleButton)) {
            if (generateSheduleButton.isEnabled()) {
                return false;
            }
        }
        return true;
    }


    @Override
    public Boolean isWeekPublished() throws Exception {
        if (isElementLoaded(publishSheduleButton)) {
            if (publishSheduleButton.isEnabled()) {
                return false;
            } else {
                clickOnScheduleAnalyzeButton();
                if (isElementLoaded(analyzePopupLatestVersionLabel)) {
                    String latestVersion = analyzePopupLatestVersionLabel.getText();
                    latestVersion = latestVersion.split(" ")[1].split(".")[0];
                    closeStaffingGuidanceAnalyzePopup();
                    if (Integer.valueOf(latestVersion) < 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    @Override
    public void generateSchedule() throws Exception {
        if (isElementLoaded(generateSheduleButton)) {
            click(generateSheduleButton);
            waitForSeconds(4);
            if(isElementLoaded(updateAndGenerateScheduleButton)){
                click(updateAndGenerateScheduleButton);
                SimpleUtils.pass("Schedule Update and Generate button clicked Successfully!");
                if (isElementLoaded(checkOutTheScheduleButton)) {
                    click(checkOutTheScheduleButton);
                    SimpleUtils.pass("Schedule Generated Successfuly!");
                }else{
                    SimpleUtils.fail("Not able to generate Schedule Successfully!",false);
                }
            }else if(isElementLoaded(checkOutTheScheduleButton)) {
                click(checkOutTheScheduleButton);
                SimpleUtils.pass("Schedule Generated Successfuly!");
            }else{
                SimpleUtils.fail("Not able to generate Schedule Successfully!",false);
            }

        } else {
            SimpleUtils.assertOnFail("Schedule Already generated for active week!", false, true);
        }
    }


    public Boolean isScheduleWeekViewActive() {
        WebElement scheduleWeekViewButton = MyThreadLocal.getDriver().
                findElement(By.cssSelector("[ng-click=\"selectDayWeekView($event, 'week')\"]"));
        if (scheduleWeekViewButton.getAttribute("class").toString().contains("enabled")) {
            return true;
        }
        return false;
    }


    public Boolean isScheduleDayViewActive() {
        WebElement scheduleDayViewButton = MyThreadLocal.getDriver().
                findElement(By.cssSelector("[ng-click=\"selectDayWeekView($event, 'day')\"]"));
        if (scheduleDayViewButton.getAttribute("class").toString().contains("enabled")) {
            return true;
        }
        return false;
    }

    public void clickOnScheduleAnalyzeButton() throws Exception {
        if (isElementLoaded(scheduleAnalyzeButton)) {
            click(scheduleAnalyzeButton);
        } else {
            SimpleUtils.fail("Schedule Analyze Button not loaded successfully!", false);
        }
    }

    public void closeStaffingGuidanceAnalyzePopup() throws Exception {
        if (isStaffingGuidanceAnalyzePopupAppear()) {
            click(scheduleAnalyzePopupCloseButton);
        }
    }

    public Boolean isStaffingGuidanceAnalyzePopupAppear() throws Exception {
        if (isElementLoaded(scheduleAnalyzePopup)) {
            return true;
        }
        return false;
    }

    public String getScheduleWeekStartDayMonthDate() {
        String scheduleWeekStartDuration = "NA";
        List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("sch-calendar-day-dimension"));
        if (ScheduleCalendarDayLabels.size() != 0) {
            scheduleWeekStartDuration = ScheduleCalendarDayLabels.get(0).getText().replace("\n", "");
        }
        return scheduleWeekStartDuration;
    }

    public void clickOnEditButton() throws Exception {
        if (isElementLoaded(edit)) {
            click(edit);
            if (isElementLoaded(editAnywayPopupButton)) {
                click(editAnywayPopupButton);
                SimpleUtils.pass("Schedule edit shift page loaded successfully!");
            }
        }
    }

    @Override
    public void clickImmediateNextToCurrentActiveWeekInDayPicker() throws Exception {

    }

    public Boolean isAddNewDayViewShiftButtonLoaded() throws Exception {
        if (isElementLoaded(addNewShiftOnDayViewButton)) {
            return true;
        } else {
            return false;
        }

    }

    public void clickOnCancelButtonOnEditMode() throws Exception {
        if (isElementLoaded(scheduleEditModeCancelButton)) {
            click(scheduleEditModeCancelButton);
            SimpleUtils.pass("Schedule edit shift page cancelled successfully!");
        }
    }

    public Boolean isGenerateButtonLoaded() throws Exception {
        if (isElementLoaded(scheduleGenerateButton)) {
            return true;
        }
        return false;
    }

    public String getActiveWeekDayMonthAndDateForEachDay() throws Exception {
        String activeWeekTimeDuration = "";
        List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("sch-calendar-day-dimension"));
        if (ScheduleCalendarDayLabels.size() != 0) {
            for (WebElement ScheduleCalendarDayLabel : ScheduleCalendarDayLabels) {
                if (activeWeekTimeDuration != "")
                    activeWeekTimeDuration = activeWeekTimeDuration + "," + ScheduleCalendarDayLabel.getText().replace("\n", " ");
                else
                    activeWeekTimeDuration = ScheduleCalendarDayLabel.getText().replace("\n", " ");
            }
        }
        return activeWeekTimeDuration;
    }

    public Boolean validateScheduleActiveWeekWithOverviewCalendarWeek(String overviewCalendarWeekDate, String overviewCalendarWeekDays, String scheduleActiveWeekDuration) {
        String[] overviewCalendarDates = overviewCalendarWeekDate.split(",");
        String[] overviewCalendarDays = overviewCalendarWeekDays.split(",");
        String[] scheduleActiveDays = scheduleActiveWeekDuration.split(",");
        int index;
        if (overviewCalendarDates.length == overviewCalendarDays.length && overviewCalendarDays.length == scheduleActiveDays.length) {
            for (index = 0; index < overviewCalendarDates.length; index++) {
                // Verify Days on Schedule Active week with Overview Calendar week
                if (scheduleActiveDays[index].startsWith(overviewCalendarDays[index])) {
                    // Verify Days on Schedule Active week with Overview Calendar week
                    if (scheduleActiveDays[index].contains(overviewCalendarDays[index])) {
                        SimpleUtils.pass("Schedule week dayAndDate matched with Overview calendar DayAndDate for '" + scheduleActiveDays[index] + "'");
                    }
                }
            }
            if (index != 0)
                return true;

        }
        return false;
    }

    public boolean isCurrentScheduleWeekPublished() {
        //todo yt 2018.10.28 this looks like a hack
        String scheduleStatus = "No Published Schedule";
        try {
            WebElement noPublishedSchedule = MyThreadLocal.getDriver().findElement(By.className("holiday-text"));
            if (isElementLoaded(noPublishedSchedule)) {
                if (noPublishedSchedule.getText().contains(scheduleStatus))
                    return false;
            }
        } catch (Exception e) {
            SimpleUtils.pass("Schedule is Published for current Week!");
            return true;
        }
        return true;
    }

    @Override
    public void filterScheduleByWorkRoleAndShiftType(boolean isWeekView) {
        // TODO Auto-generated method stub

    }

    @Override
    public void selectGroupByFilter(String optionVisibleText) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getActiveWeekText() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<WebElement> getAllAvailableShiftsInWeekView() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<HashMap<String, String>> getHoursAndShiftsCountForEachWorkRolesInWeekView() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<Float> getAllVesionLabels() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void publishActiveSchedule() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isPublishButtonLoaded() {
        // TODO Auto-generated method stub
        return false;
    }


    //added by manideep

    public void isGenerateScheduleButton() throws Exception {
        if (isElementLoaded(scheduleGenerateButton)) {
            SimpleUtils.pass("Generate Schedule Button is Displayed on Schedule page");
        } else {
            SimpleUtils.fail("Generate Schedule Button is Displayed on Schedule page", true);
        }
    }

    public void validatingRefreshButtononPublishedSchedule() throws Exception {
        if (isElementLoaded(refresh)) {
            SimpleUtils.fail("Refresh Button is Displayed on Schedule page", true);
        } else {
            SimpleUtils.pass("Refresh Button is not Displayed on Schedule page");
        }

    }


    public void validateSchedulePageRefreshButton() throws Exception {

        goToSchedulePage();
        int weekDate = 0;

        if (isElementLoaded(weeklyScheduleTableBodyElement) && isElementLoaded(weeklyScheduleDateElements.get(0))) {

            for (WebElement weeklyScheduleDateElement : weeklyScheduleDateElements) {
                scheduleWeekDate.add(weeklyScheduleDateElement.getText());
                int weekStatus = 0;
                for (WebElement weeklyScheduleStatusElement : weeklyScheduleStatusElements) {

                    if (weekDate == weekStatus) {
                        scheduleWeekStatus.add(weeklyScheduleStatusElement.getText());
                        weeklyTableRowsDatesAndStatus.put(weeklyScheduleDateElement.getText().replace("\n", ""),
                                weeklyScheduleStatusElement.getText().replace("\n", ""));

                        break;
                    }
                    weekStatus++;
                }
                weekDate++;

            }
            int mapIndex = 0;

            for (Map.Entry<String, String> tableRowIndex : weeklyTableRowsDatesAndStatus.entrySet()) {
                SimpleUtils.pass("SCHEDULE WEEK: " + scheduleWeekDate.get(mapIndex) + "  SCHEDULE STATUS: "
                        + scheduleWeekStatus.get(mapIndex));
                if (tableRowIndex.getValue().contains("Finalized") || tableRowIndex.getValue().contains("Published")) {

                    weeklyScheduleDateElements.get(mapIndex).click();

                    checkElementVisibility(goToScheduleTab);
                    SimpleUtils.pass("Schedule Page Loading..!");
                    validatingRefreshButtononPublishedSchedule();

                } else if (tableRowIndex.getValue().contains("Draft")) {
                    weeklyScheduleDateElements.get(mapIndex).click();

                    checkElementVisibility(goToScheduleTab);

                    SimpleUtils.pass("Schedule Page Loading..!");
                    if (isElementLoaded(publishButton)) {
                        SimpleUtils.pass(" PublishButton is Displayed on Schedule page");
                        validatingScheduleRefreshButton();
                        clickOnSchedulePublishButton();
                        validatingRefreshButtononPublishedSchedule();
                    } else {
                        SimpleUtils.fail(" Publish Button is not Displayed on Schedule page", true);
                    }

                } else if (tableRowIndex.getValue().contains("Guidance")) {
                    weeklyScheduleDateElements.get(mapIndex).click();
                    isGenerateScheduleButton();

                }

                click(goToScheduleButton);
                mapIndex++;

            }

        }

    }


    @Override
    public void clickOnSchedulePublishButton() throws Exception {
        if (isElementLoaded(publishButton)) {
            click(publishButton);
            if (isElementLoaded(schedulePublishButton)) {
                waitForSeconds(5);
                click(schedulePublishButton);
                if (isElementLoaded(successfullyPublishedOkOption)) {
                    click(successfullyPublishedOkOption);
                    SimpleUtils.pass("New Published Schedule page loaded successfully!");
                }
            }
        }
    }

    @Override
    public void validatingScheduleRefreshButton() throws Exception {
        if (isElementLoaded(refresh)) {
            SimpleUtils.pass("Refresh Button is Displayed on Schedule page");
        } else {
            SimpleUtils.fail("Refresh Button not Displayed on Schedule page", true);
        }

    }

    @Override
    public void navigateDayViewToPast(String nextWeekViewOrPreviousWeekView,
                                      int weekCount) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public String clickNewDayViewShiftButtonLoaded() throws Exception {
        return null;
        // TODO Auto-generated method stub

    }

    @Override
    public void customizeNewShiftPage() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void compareCustomizeStartDay(String textStartDay) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void moveSliderAtSomePoint(String shiftTime, int shiftStartingCount, String startingPoint)
            throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public HashMap<String, String> calculateHourDifference() throws Exception {
        HashMap<String, String> shiftTimeSchedule = new HashMap<String, String>();
        return shiftTimeSchedule;
        // TODO Auto-generated method stub

    }

    @Override
    public void selectWorkRole(String workRoles) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void clickRadioBtnStaffingOption(String staffingOption) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void clickOnCreateOrNextBtn() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public HashMap<List<String>, List<String>> calculateTeamCount() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> calculatePreviousTeamCount(
            HashMap<String, String> previousTeamCount, HashMap<List<String>, List<String>>
            gridDayHourPrevTeamCount) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> calculateCurrentTeamCount(
            HashMap<String, String> shiftTiming) throws Exception {
        return null;
        // TODO Auto-generated method stub

    }

    @Override
    public void clickSaveBtn() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void clickOnVersionSaveBtn() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void clickOnPostSaveBtn() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public HashMap<String, Float> getScheduleLabelHours() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getgutterSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void verifySelectTeamMembersOption() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void searchText(String searchInput) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void getAvailableStatus() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void clickOnOfferOrAssignBtn() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void clickOnShiftContainer(int index) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteShift() {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAllShiftsInDayView(){
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteShiftGutterText() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean getScheduleStatus() throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean inActiveWeekDayClosed(int dayIndex) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void navigateDayViewWithIndex(int dayIndex) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getActiveGroupByFilter() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isActiveWeekHasOneDayClose() throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isActiveWeekAssignedToCurrentUser(String userName) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isScheduleGroupByWorkRole(String workRoleOption) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void selectWorkRoleFilterByIndex(int index, boolean isClearWorkRoleFilters) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public ArrayList<String> getSelectedWorkRoleOnSchedule() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isRequiredActionUnAssignedShiftForActiveWeek() throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void clickOnRefreshButton() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void selectShiftTypeFilterByText(String filterText) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public List<WebElement> getAvailableShiftsInDayView() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void dragShiftToRightSide(WebElement shift, int xOffSet) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isSmartCardAvailableByLabel(String cardLabel) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ArrayList<String> getActiveWeekCalendarDates() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void refreshBrowserPage() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void addOpenShiftWithDefaultTime(String workRole) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void addOpenShiftWithFirstDay(String workRole) throws Exception {

    }

    @Override
    public boolean isSmartCardPanelDisplay() throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isNextWeekAvaibale() throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void convertAllUnAssignedShiftToOpenShift() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void selectWorkRoleFilterByText(String workRoleLabel, boolean isClearWorkRoleFilters) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void reduceOvertimeHoursOfActiveWeekShifts() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isActionButtonLoaded(String actionBtnText) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void navigateToNextDayIfStoreClosedForActiveDay() throws Exception {
        // TODO Auto-generated method stub

    }


    @Override
    public void validateBudgetPopUpHeader(String nextWeekView, int weekCount) {
        // TODO Auto-generated method stub

        for (int i = 0; i < weekCount; i++) {
            if (nextWeekView.toLowerCase().contains("next") || nextWeekView.toLowerCase().contains("future")) {
                try {
                    if (isElementLoaded(schedulesForWeekOnOverview.get(0))) {
                        click(schedulesForWeekOnOverview.get(i));
                        dayWeekPicker = daypicker.getText();
                        String[] weekActiveArray = daypicker.getText().split("\n");
                        String weekActiveDate = weekActiveArray[1];
                        String budgetPopUpHeader = "Budget - Week of " + SimpleUtils.dateWeekPickerDateComparision(weekActiveDate);
                        checkElementVisibility(enterBudgetLink);
                        waitForSeconds(2);
                        click(enterBudgetLink);
                        if (budgetPopUpHeader.equalsIgnoreCase(budgetHeader.getText())) {
                            SimpleUtils.pass("Budget pop-up header week duration " + budgetHeader.getText() + " matches " + weekActiveDate);
                            checkElementVisibility(enterBudgetCancelButton);
                            click(enterBudgetCancelButton);
                            checkElementVisibility(returnToOverviewTab);
                            click(returnToOverviewTab);
                        } else {
                            SimpleUtils.fail("Budget-PopUp opens up for " + SimpleUtils.dateWeekPickerDateComparision(weekActiveDate), false);
                        }
                    }
                } catch (Exception e) {
                    SimpleUtils.fail("Budget pop-up not opening ", false);
                }
            }


        }

    }

    @Override
    public void disableNextWeekArrow() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void clickScheduleDraftAndGuidanceStatus(
            List<String> overviewScheduleWeeksStatus) {
        // TODO Auto-generated method stub

    }

    @Override
    public void editBudgetHours() {
        // TODO Auto-generated method stub

    }

    public void scheduleDayNavigation(String[] dayPickers) throws Exception{
        if(isElementLoaded(scheduleDayView)){
            click(scheduleDayView);
            SimpleUtils.pass("Clicked on Day View for Week "+ dayPickers[1]);
            for(int i=0; i<dayPickerAllDaysInDayView.size();i++) {
                click(dayPickerAllDaysInDayView.get(i));
                String[] currentDate = currentActiveDay.getText().split("\n");
                if (isElementLoaded(smartcard,10)) {
                    SimpleUtils.pass("Smartcard Section Loaded Successfully! for " + currentDate[1]);
                } else {
                    SimpleUtils.fail("Smartcard Section Not Loaded for " + currentDate[1], true);
                }
                if (isElementLoaded(scheduleTableDayView,10) && Float.parseFloat(budgetDisplayOnScheduleSmartcard.get(0).getText()) > 0 && Float.parseFloat(scheduleDisplayOnScheduleSmartcard.get(0).getText()) > 0) {
                    SimpleUtils.pass("Schedule Successfully Loaded for  " + currentDate[1] + ", value for Budgeted Hour is " + budgetDisplayOnScheduleSmartcard.get(0).getText() + " Hours and Scheduled Hour is " + scheduleDisplayOnScheduleSmartcard.get(0).getText() + " Hours");
                } else if (!isElementLoaded(scheduleTableDayView,3) && Float.parseFloat(budgetDisplayOnScheduleSmartcard.get(0).getText()) == 0 && Float.parseFloat(scheduleDisplayOnScheduleSmartcard.get(0).getText()) == 0 && isElementLoaded(storeClosed, 10)) {
                    SimpleUtils.pass("Store Closed on  " + currentDate[1] + ", value for Budgeted Hour is " + budgetDisplayOnScheduleSmartcard.get(0).getText() + " Hours and Scheduled Hour is " + scheduleDisplayOnScheduleSmartcard.get(0).getText() + " Hours");
                } else {
                    SimpleUtils.fail("Schedule Not Loaded for  " + currentDate[1] + ", value for Budgeted Hour is " + budgetDisplayOnScheduleSmartcard.get(0).getText() + " Hours and Scheduled Hour is " + scheduleDisplayOnScheduleSmartcard.get(0).getText() + " Hours", true);
                }
            }
        }else{
            SimpleUtils.pass("Day View button not found in Schedule Sub Tab for Week " + dayPickers[1]);
        }

    }


    public void scheduleDayWeekNavigation() throws Exception {
        String[] daypickers = null;
        if(areListElementVisible(scheduleTableWeekViewWorkerDetail)){
            daypickers = daypicker.getText().split("\n");
            if (isElementLoaded(smartcard,10)) {
                SimpleUtils.pass("Smartcard Section Loaded Successfully! for Week " + daypickers[1]);
            } else {
                SimpleUtils.fail("Smartcard Section Not Loaded for Week " + daypickers[1], true);
            }
            if (isElementEnabled(scheduleTableWeekViewWorkerDetail.get(0)) && Float.parseFloat(budgetDisplayOnScheduleSmartcard.get(0).getText()) > 0 && Float.parseFloat(scheduleDisplayOnScheduleSmartcard.get(0).getText().replaceAll(",","")) > 0) {
                SimpleUtils.pass("Schedule Loaded Successfully! for Week " + daypickers[1] + " value for Budgeted Hour is " + budgetDisplayOnScheduleSmartcard.get(0).getText() + "Hours and Scheduled Hour is " + scheduleDisplayOnScheduleSmartcard.get(0).getText() + "Hours");
            } else {
                SimpleUtils.fail("Schedule Not Loaded for Week " + daypickers[1] + " value for Budgeted Hour is " + budgetDisplayOnScheduleSmartcard.get(0).getText() + "Hours and Scheduled Hour is " + scheduleDisplayOnScheduleSmartcard.get(0).getText() + "Hours", true);
            }
            scheduleDayNavigation(daypickers);
        }else{
            SimpleUtils.fail("Day picker Not Loaded for Week " + daypickers[1], false);
        }

    }

//    public void scheduleDayWeekNavigation() throws Exception {
//            waitForSeconds(4);
//            String[] daypickers = daypicker.getText().split("\n");
//            if (isElementLoaded(smartcard,10)) {
//                SimpleUtils.pass("Smartcard Section Loaded Successfully! for Week " + daypickers[1]);
//            } else {
//                SimpleUtils.fail("Smartcard Section Not Loaded for Week " + daypickers[1], true);
//            }
//            if (isElementEnabled(scheduleTableWeekViewWorkerDetail.get(0)) && Float.parseFloat(budgetDisplayOnScheduleSmartcard.get(0).getText()) > 0 && Float.parseFloat(scheduleDisplayOnScheduleSmartcard.get(0).getText()) > 0) {
//                SimpleUtils.pass("Schedule Loaded Successfully! for Week " + daypickers[1] + " value for Budgeted Hour is " + budgetDisplayOnScheduleSmartcard.get(0).getText() + "Hours and Scheduled Hour is " + scheduleDisplayOnScheduleSmartcard.get(0).getText() + "Hours");
//            } else {
//                SimpleUtils.fail("Schedule Not Loaded for Week " + daypickers[1] + " value for Budgeted Hour is " + budgetDisplayOnScheduleSmartcard.get(0).getText() + "Hours and Scheduled Hour is " + scheduleDisplayOnScheduleSmartcard.get(0).getText() + "Hours", true);
//            }
//            scheduleDayNavigation(daypickers);
//
//    }
//    public void skipGuidanceWeek(){
//        for(int i=0;i<scheduleOverviewWeeksStatus.size();i++){
//            if(scheduleOverviewWeeksStatus.get(i).getText().equalsIgnoreCase("Guidance")){
//                SimpleUtils.pass("Guidance week found "+ overviewPageScheduleWeekDurations.get(i).getText());
//            }
//        }
//
//    }


    @Override
    public void navigateScheduleDayWeekView(String nextWeekView, int weekCount) {
        for(int i = 0; i < weekCount; i++)
        {
            if(nextWeekView.toLowerCase().contains("next") || nextWeekView.toLowerCase().contains("future"))
            {
                try {
                    if (areListElementVisible(schedulesForWeekOnOverview)) {
                        if(scheduleOverviewWeeksStatus.get(i).getText().equalsIgnoreCase("Guidance")){
                            SimpleUtils.pass("Guidance week found "+ overviewPageScheduleWeekDurations.get(i).getText());
                        }else {
                            click(schedulesForWeekOnOverview.get(i));
                            scheduleDayWeekNavigation();
                        }
                        checkElementVisibility(returnToOverviewTab);
                        click(returnToOverviewTab);
                    }
                }catch (Exception e) {
                    SimpleUtils.fail("!!!",true);
                }
            }


        }
    }


    @Override
    public void noBudgetDisplayWhenBudgetNotEntered(String nextWeekView, int weekCount) {
        // TODO Auto-generated method stub

        String valueWhenBudgetNotEntered = "-- Hours";

        for(int i = 0; i < weekCount; i++)
        {
            if(nextWeekView.toLowerCase().contains("next") || nextWeekView.toLowerCase().contains("future"))
            {
                try {
                    if(isElementLoaded(schedulesForWeekOnOverview.get(0))){
                        click(schedulesForWeekOnOverview.get(i));
                        waitForSeconds(3);
                        String budgetDisplayOnSmartCardSchedule = budgetOnbudgetSmartCard.getText();
                        String[] budgetDisplayOnSmartCard = budgetOnbudgetSmartCard.getText().split(" ");
                        String[] daypickers = daypicker.getText().split("\n");
                        checkElementVisibility(enterBudgetLink);
                        click(enterBudgetLink);
                        waitForSeconds(3);
                        String totalOfEnteredBudget = sumOfBudgetHour.getText();
                        if(Float.parseFloat(totalOfEnteredBudget)==0 && budgetDisplayOnSmartCardSchedule.equalsIgnoreCase(valueWhenBudgetNotEntered)){
                            SimpleUtils.pass("No Budget Entered for week "+daypickers[1]+", Budget SmartCard shows " + budgetDisplayOnSmartCard[0] );
                        }
                        else if(Float.parseFloat(totalOfEnteredBudget)>0 && Float.parseFloat(totalOfEnteredBudget)==Float.parseFloat(budgetDisplayOnSmartCard[0])){
                            SimpleUtils.pass("value on Budget Smart Card "+budgetDisplayOnSmartCard[0]+ " for week "+daypickers[1]+ ", and entered Budget is " + Float.parseFloat(totalOfEnteredBudget) );
                        }
                        else{
                            SimpleUtils.fail("Budget Smartcard shows wrong values "+Float.parseFloat(totalOfEnteredBudget),false);
                        }
                        checkElementVisibility(enterBudgetCancelButton);
                        click(enterBudgetCancelButton);
                        checkElementVisibility(returnToOverviewTab);
                        click(returnToOverviewTab);
                    }

                }
                catch (Exception e) {
                    SimpleUtils.fail("Budget pop-up not opening ",false);
                }
            }


        }

    }

    @Override
    public void budgetInScheduleNBudgetSmartCard(String nextWeekView, int weekCount, int tolerance) {

    }


    @Override
    public String getsmartCardTextByLabel(String cardLabel) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getWeatherTemperature() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }


//	@Override
//	public void budgetHourInScheduleNBudgetedSmartCard(String nextWeekView,
//			int weekCount) {
//		// TODO Auto-generated method stub
//
//	}

//	@Override
//	public void budgetHourInScheduleNBudgetedSmartCard(String nextWeekView,int weekCount) {
//		waitForSeconds(3);
//		for(int i = 0; i < weekCount; i++)
//			{
//				float totalBudgetedHourForSmartCard=0.0f;
//				float totalScheduledHourIfBudgetEntered=0.0f;
//				if(nextWeekView.toLowerCase().contains("next") || nextWeekView.toLowerCase().contains("future"))
//				{
//					try {
//						if(isElementLoaded(schedulesForWeekOnOverview.get(0))) {
//							waitForSeconds(3);
//							click(schedulesForWeekOnOverview.get(i));
//							waitForSeconds(4);
//							String[] daypickers = daypicker.getText().split("\n");
//							String[] budgetDisplayOnSmartCard = budgetOnbudgetSmartCard.getText().split(" ");
//							String budgetOnScheduleSmartcard = budgetDisplayOnScheduleSmartcard.get(0).getText();
//							if (budgetOnbudgetSmartCard.getText().equalsIgnoreCase("-- Hours")) {
//								SimpleUtils.pass(daypickers[1] + " week has no budget entered");
//								waitForSeconds(2);
//								checkElementVisibility(returnToOverviewTab);
//								click(returnToOverviewTab);
//							} else {
//								click(enterBudgetLink);
//								waitForSeconds(3);
//								for (int j = 1; j < guidanceHour.size(); j++) {
//									totalBudgetedHourForSmartCard = totalBudgetedHourForSmartCard + Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"));
//									if (((Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"))) == 0)) {
//										totalScheduledHourIfBudgetEntered = totalScheduledHourIfBudgetEntered + Float.parseFloat(guidanceHour.get(j - 1).getText());
//
//									} else {
//										totalScheduledHourIfBudgetEntered = totalScheduledHourIfBudgetEntered + Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"));
//									}
//								}
//                                if (totalBudgetedHourForSmartCard == (Float.parseFloat(budgetDisplayOnSmartCard[0]))) {
//									SimpleUtils.pass("Budget " + (Float.parseFloat(budgetDisplayOnSmartCard[0])) +" for week " +daypickers[1] + " on budget smartcard matches the budget entered " + totalBudgetedHourForSmartCard);
//								} else {
//									SimpleUtils.fail("Budget " + (Float.parseFloat(budgetDisplayOnSmartCard[0]))  +" for week " +daypickers[1] + " on budget smartcard doesn't match the budget entered " + totalBudgetedHourForSmartCard, false);
//								}
//
//                                float finaltotalScheduledHourIfBudgetEntered = (float) (Math.round(totalScheduledHourIfBudgetEntered * 10) / 10.0);;
//								if (finaltotalScheduledHourIfBudgetEntered == (Float.parseFloat(budgetOnScheduleSmartcard))) {
//									SimpleUtils.pass("Budget " + (Float.parseFloat(budgetOnScheduleSmartcard))  +" for week " +daypickers[1] + " on schedule smartcard matches the budget entered " + finaltotalScheduledHourIfBudgetEntered);
//								} else {
//									SimpleUtils.fail("Budget " + (Float.parseFloat(budgetOnScheduleSmartcard))  +" for week " +daypickers[1] + " on schedule smartcard doesn't match the budget entered " + finaltotalScheduledHourIfBudgetEntered, false);
//								}
//								checkElementVisibility(enterBudgetCancelButton);
//								click(enterBudgetCancelButton);
//								checkElementVisibility(returnToOverviewTab);
//								click(returnToOverviewTab);
//							}
//						}
//					}
//
//					catch (Exception e) {
//					SimpleUtils.fail("Budget pop-up not opening ",false);
//					}
//				}
//			}
//	}
//
//    public void budgetHourByWagesInScheduleNBudgetedSmartCard(String nextWeekView,int weekCount) {
//        waitForSeconds(3);
//        for(int i = 0; i < weekCount; i++)
//        {
//            float totalBudgetedWagesForSmartCard=0.0f;
//            float totalScheduledWagesIfBudgetEntered=0.0f;
//            if(nextWeekView.toLowerCase().contains("next") || nextWeekView.toLowerCase().contains("future"))
//            {
//                try {
//                    if (isElementLoaded(schedulesForWeekOnOverview.get(0))) {
//                        waitForSeconds(3);
//                        click(schedulesForWeekOnOverview.get(i));
//                        waitForSeconds(4);
//                        String[] daypickers = daypicker.getText().split("\n");
//                        String[] budgetDisplayOnSmartCard = budgetOnbudgetSmartCard.getText().split(" ");
//                        String budgetOnScheduleSmartcard = budgetDisplayOnScheduleSmartcard.get(0).getText();
//                        if (budgetOnbudgetSmartCard.getText().equalsIgnoreCase("-- Hours")) {
//                            SimpleUtils.pass(daypickers[1] + " week has no budget entered");
//                            waitForSeconds(2);
//                            checkElementVisibility(returnToOverviewTab);
//                            click(returnToOverviewTab);
//                        } else {
//                            click(enterBudgetLink);
//                            waitForSeconds(3);
//                            for (int j = 1; j < guidanceHour.size(); j++) {
//                                totalBudgetedWagesForSmartCard = totalBudgetedWagesForSmartCard + Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"));
//                                if (((Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"))) == 0)) {
//                                    totalScheduledWagesIfBudgetEntered = totalScheduledWagesIfBudgetEntered + Float.parseFloat(guidanceHour.get(j - 1).getText());
//
//                                } else {
//                                    totalScheduledWagesIfBudgetEntered = totalScheduledWagesIfBudgetEntered + Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"));
//                                }
//                            }
//                            if (totalBudgetedWagesForSmartCard == (Float.parseFloat(budgetDisplayOnSmartCard[0]))) {
//                                SimpleUtils.pass("Budget " + (Float.parseFloat(budgetDisplayOnSmartCard[0])) + " for week " + daypickers[1] + " on budget smartcard matches the budget entered " + totalBudgetedWagesForSmartCard);
//                            } else {
//                                SimpleUtils.fail("Budget " + (Float.parseFloat(budgetDisplayOnSmartCard[0])) + " for week " + daypickers[1] + " on budget smartcard doesn't match the budget entered " + totalBudgetedWagesForSmartCard, false);
//                            }
//
//                            float finaltotalScheduledHourIfBudgetEntered = (float) (Math.round(totalScheduledWagesIfBudgetEntered * 10) / 10.0);
//                            ;
//                            if (finaltotalScheduledHourIfBudgetEntered == (Float.parseFloat(budgetOnScheduleSmartcard))) {
//                                SimpleUtils.pass("Budget " + (Float.parseFloat(budgetOnScheduleSmartcard)) + " for week " + daypickers[1] + " on schedule smartcard matches the budget entered " + finaltotalScheduledHourIfBudgetEntered);
//                            } else {
//                                SimpleUtils.fail("Budget " + (Float.parseFloat(budgetOnScheduleSmartcard)) + " for week " + daypickers[1] + " on schedule smartcard doesn't match the budget entered " + finaltotalScheduledHourIfBudgetEntered, false);
//                            }
//                            checkElementVisibility(enterBudgetCancelButton);
//                            click(enterBudgetCancelButton);
//                            checkElementVisibility(returnToOverviewTab);
//                            click(returnToOverviewTab);
//                        }
//                    }
//                }
//                catch (Exception e) {
//                    SimpleUtils.fail("Budget pop-up not opening ",false);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void budgetInScheduleNBudgetSmartCard(String nextWeekView, int weekCount) {
//
//    }


    @Override
    public void validatingGenrateSchedule() throws Exception {
        // TODO Auto-generated method stub
    }
    //added by Gunjan
    @Override
    public boolean loadSchedule() throws Exception {
        // TODO Auto-generated method stub
        boolean flag=false;
        if(isElementLoaded(ScheduleSubMenu)){
            click(ScheduleSubMenu);
            SimpleUtils.pass("Clicked on Schedule Sub Menu... ");
            if(isElementLoaded(scheduleDayView)){
                click(scheduleDayView);
                SimpleUtils.pass("Clicked on Day View of Schedule Tab");
                if(isElementLoaded(smartcard)){
                    flag = true;
                    SimpleUtils.pass("Smartcard Section in Day View Loaded Successfully!");
                }else{
                    SimpleUtils.fail("Smartcard Section in Day View Not Loaded Successfully!", true);
                }
                if(isElementLoaded(scheduleTableDayView)){
                    flag = true;
                    SimpleUtils.pass("Schedule in Day View Loaded Successfully!");
                }else{
                    SimpleUtils.fail("Schedule in Day View Not Loaded Successfully!", true);
                }
            }else{
                SimpleUtils.fail("Day View button not found in Schedule Sub Tab",false);
            }
            if(isElementLoaded(scheduleWeekView,10)){
                click(scheduleWeekView);
                SimpleUtils.pass("Clicked on Week View of Schedule Tab");
                if(isElementLoaded(smartcard,10)){
                    flag = true;
                    SimpleUtils.pass("Smartcard Section in Week View Loaded Successfully!");
                }else{
                    SimpleUtils.fail("Smartcard Section in Week View Not Loaded Successfully!", true);
                }
                if(isElementLoaded(scheduleTableWeekView,10)){
                    flag = true;
                    SimpleUtils.pass("Schedule in Week View Loaded Successfully!");
                }else{
                    SimpleUtils.fail("Schedule in Week View Not Loaded Successfully!", false);
                }
            }else{
                SimpleUtils.pass("Week View button not found in Schedule Sub Tab");
            }
        }else{
            SimpleUtils.fail("Schedule Sub Menu Tab Not Found", false);
        }
        return flag;
    }


    @Override
    public HashMap<String, Integer> getScheduleBufferHours() throws Exception {
        return null;
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isComlianceReviewRequiredForActiveWeek() throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void unGenerateActiveScheduleScheduleWeek() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isStoreClosedForActiveWeek() throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getScheduleShiftIntervalCountInAnHour() throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void toggleSummaryView() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isSummaryViewLoaded() throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void updateScheduleOperatingHours(String day, String startTime, String endTime) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void selectWhichWeekToCopyFrom(String weekInfo) throws Exception{

    }

    @Override
    public boolean isScheduleOperatingHoursUpdated(String startTime, String endTime) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void verifyScheduledHourNTMCountIsCorrect() throws Exception {

    }

    @Override
    public void complianceShiftSmartCard() throws Exception {

    }

    @Override
    public void viewProfile() {

    }

    @Override
    public void changeWorkerRole() {

    }

    @Override
    public void assignTeamMember() {

    }


    @Override
    public void convertToOpenShift() {

    }

    @Override
    public void beforeEdit() {

    }


    @Override
    public void generateOrUpdateAndGenerateSchedule() throws Exception {

    }

    @Override
    public void createScheduleForNonDGFlowNewUI() throws Exception {

    }

    public void clickWorkerImage(){

    }

    public void selectTeamMembersOptionForOverlappingSchedule(){

    }

    public boolean getScheduleOverlappingStatus()throws Exception{
        return true;
    }

    public void searchWorkerName(String searchInput) throws Exception{

    }

    public void verifyScheduleStatusAsOpen() throws Exception{

    }

    public void verifyScheduleStatusAsTeamMember() throws Exception{

    }

    public String getActiveAndNextDay() throws Exception{
        return "";
    }

    public HashMap<String, String> getOperatingHrsValue(String day) throws Exception {
        return null;
    }

    public void moveSliderAtCertainPoint(String shiftTime, String startingPoint) throws Exception {

    }

    public void clickOnNextDaySchedule(String activeDay) throws Exception {

    }

    public void selectTeamMembersOptionForSchedule() throws Exception {

    }
    public void verifyActiveScheduleType() throws Exception{

    }

    public List<Float> validateScheduleAndBudgetedHours() throws Exception {
        return null;
    }

    public void compareHoursFromScheduleAndDashboardPage(List<Float> totalHoursFromSchTbl) throws Exception{

    }

    public void compareHoursFromScheduleSmartCardAndDashboardSmartCard(List<Float> totalHoursFromSchTbl) throws Exception{

    }
    public List<Float> getHoursOnLocationSummarySmartCard() throws Exception{
        return null;
    }

    public void compareProjectedWithinBudget(int totalCountProjectedOverBudget) throws Exception {

    }

    public int getProjectedOverBudget() {
        return 0;
    }

    public String getDateFromDashboard() throws Exception{
        return null;
    }
    public void compareDashboardAndScheduleWeekDate(String DateOnSchdeule, String DateOnDashboard) throws Exception{

    }
    public List<String> getLocationSummaryDataFromDashBoard() throws Exception{
        return null;
    }
    public List<String> getLocationSummaryDataFromSchedulePage() throws Exception{
        return null;
    }
    public void compareLocationSummaryFromDashboardAndSchedule(List<String> ListLocationSummaryOnDashboard, List<String> ListLocationSummaryOnSchedule){

    }

    public void openBudgetPopUpGenerateSchedule() throws Exception{

    }

    public void updatebudgetInScheduleNBudgetSmartCard(String nextWeekView, int weekCount) {

    }

    public void selectTeamMembersOptionForScheduleForClopening() throws Exception{

    }

    public void verifyClopeningHrs() throws Exception {

    }

    public void clickOnPreviousDaySchedule(String activeDay) throws Exception {

    }

    public void toNFroNavigationFromDMToSMSchedule(String CurrentWeek, String locationToSelectFromDMViewSchedule, String districtName, String nextWeekViewOrPreviousWeekView) throws Exception {

    }
    public void toNFroNavigationFromDMDashboardToDMSchedule(String CurrentWeek) throws Exception{

    }
    public void clickOnViewScheduleLocationSummaryDMViewDashboard(){

    }
    public void clickOnViewSchedulePayrollProjectionDMViewDashboard(){

    }
    public void loadingOfDMViewSchedulePage(String SelectedWeek) throws Exception{

    }
    public void districtSelectionSMView(String districtName) throws Exception{}

    @Override
    public void isScheduleForCurrentDayInDayView(String dateFromDashboard) throws Exception {

    }

    @Override
    public HashMap<String, String> getHoursFromSchedulePage() throws Exception {
        return null;
    }

    public void printButtonIsClickable() throws Exception {

    }
    public void todoButtonIsClickable() throws Exception {

    }

    @Override
    public void legionButtonIsClickableAndHasNoEditButton() {

    }

    @Override
    public void clickOnSuggestedButton() throws Exception {

    }

    @Override
    public void legionIsDisplayingTheSchedul() throws Exception {

    }

    @Override
    public void currentWeekIsGettingOpenByDefault() {

    }

    @Override
    public void goToScheduleNewUI() {

    }

    @Override
    public void dayWeekPickerSectionNavigatingCorrectly() throws Exception {

    }

    @Override
    public void landscapePortraitModeShowWellInWeekView() throws Exception {

    }

    @Override
    public void landscapeModeWorkWellInWeekView() throws Exception {

    }

    @Override
    public void portraitModeWorkWellInWeekView() throws Exception {

    }

    @Override
    public HashMap<String, String> getFourUpComingShifts(boolean isStartTomorrow, String currentTime) throws Exception {
        return null;
    }

    @Override
    public void verifyUpComingShiftsConsistentWithSchedule(HashMap<String, String> dashboardShifts, HashMap<String, String> scheduleShifts) throws Exception {

    }

    @Override
    public void clickOnCreateNewShiftButton() throws Exception {

    }

    @Override
    public void verifyTeamCount(List<String> previousTeamCount, List<String> currentTeamCount) throws Exception {

    }

    @Override
    public void selectDaysFromCurrentDay(String currentDay) throws Exception {

    }

    @Override
    public void searchTeamMemberByName(String name) throws Exception {

    }

    @Override
    public void verifyNewShiftsAreShownOnSchedule(String name) throws Exception {

    }

    @Override
    public void verifyShiftsChangeToOpenAfterTerminating(List<Integer> indexes, String name, String currentTime) throws Exception {

    }

    @Override
    public List<Integer> getAddedShiftIndexes(String name) throws Exception {
        return null;
    }

    @Override
    public void landscapeModeOnlyInDayView() throws Exception {

    }

    @Override
    public void weatherWeekSmartCardIsDisplayedForAWeek() throws Exception {

    }

    @Override
    public void scheduleUpdateAccordingToSelectWeek() throws Exception {

    }

    @Override
    public boolean verifyRedFlagIsVisible() throws Exception {
        return false;
    }

    @Override
    public boolean verifyComplianceShiftsSmartCardShowing() throws Exception {
        return false;
    }



    @Override
    public boolean clickViewShift() throws Exception {
        return false;
    }

    @Override
    public void verifyComplianceFilterIsSelectedAftClickingViewShift() throws Exception {

    }

    @Override
    public void verifyComplianceShiftsShowingInGrid() throws Exception {

    }

    @Override
    public void verifyClearFilterFunction() throws Exception {

    }

    @Override
    public void clickOnFilterBtn() throws Exception {

    }


    @Override
    public void verifyShiftSwapCoverRequestedIsDisplayInTo() {

    }

    @Override
    public void verifyAnalyzeBtnFunctionAndScheduleHistoryScroll() throws Exception {

    }

    @Override
    public HashMap<String, Float> getScheduleBudgetedHoursInScheduleSmartCard() throws Exception {

        return null;
    }

    public Boolean isGenerateButtonLoadedForManagerView() throws Exception {
        return false;
    }



    @Override
    public boolean areShiftsPresent() throws Exception {
        return false;
    }

    @Override
    public int verifyClickOnAnyShift() throws Exception {
        return 0;
    }

    @Override
    public void clickTheShiftRequestByName(String requestName) throws Exception {

    }

    @Override
    public boolean isPopupWindowLoaded(String title) throws Exception {
        return false;
    }

    @Override
    public void verifyComponentsOnSubmitCoverRequest() throws Exception {

    }

    @Override
    public void verifyClickOnSubmitButton() throws Exception {

    }

    @Override
    public void clickOnShiftByIndex(int index) throws Exception {

    }

    @Override
    public boolean verifyShiftRequestButtonOnPopup(List<String> requests) throws Exception {
        return true;
    }

    @Override
    public void verifyComparableShiftsAreLoaded() throws Exception {

    }

    @Override
    public void validateGroupBySelectorSchedulePage() throws Exception {

    }

    @Override
    public boolean checkEditButton() throws Exception {
        return false;
    }

    @Override
    public void verifyEditButtonFuntionality() throws Exception {

    }


    @Override
    public boolean checkCancelButton() throws Exception {
        return false;
    }

    @Override
    public void selectCancelButton() throws Exception {

    }

    @Override
    public boolean checkSaveButton() throws Exception {
        return false;
    }

    @Override
    public void selectSaveButton() throws Exception {

    }

    @Override
    public boolean isScheduleFinalized() throws Exception {
        return false;
    }

    @Override
    public boolean isProfileIconsEnable() throws Exception {
        return false;
    }

    @Override
    public WebElement clickOnProfileIcon() throws Exception {
        return null;
    }

    @Override
    public boolean isViewProfileEnable() throws Exception {
        return false;
    }

    @Override
    public boolean isViewOpenShiftEnable() throws Exception {
        return false;
    }

    @Override
    public boolean isChangeRoleEnable() throws Exception {
        return false;
    }

    @Override
    public boolean isAssignTMEnable() throws Exception {
        return false;
    }

    @Override
    public boolean isConvertToOpenEnable() throws Exception {
        return false;
    }

    @Override
    public void selectNextWeekSchedule() throws Exception {

    }

    @Override
    public void clickOnViewProfile() throws Exception {

    }

    @Override
    public void clickOnChangeRole() throws Exception {

    }

    @Override
    public boolean validateVariousWorkRolePrompt() throws Exception {
        return false;
    }

    @Override
    public void verifyRecommendedAndSearchTMEnabled() throws Exception {

    }

    @Override
    public void clickonAssignTM() throws Exception {

    }

    @Override
    public void clickOnConvertToOpenShift() throws Exception {

    }

    @Override
    public void verifyPersonalDetailsDisplayed() throws Exception {

    }

    @Override
    public void verifyWorkPreferenceDisplayed() throws Exception {

    }

    @Override
    public void verifyAvailabilityDisplayed() throws Exception {

    }

    @Override
    public void closeViewProfileContainer() throws Exception {

    }


    @Override
    public boolean verifyContextOfTMDisplay() throws Exception {
        return false;
    }

    @Override
    public void verifyChangeRoleFunctionality() throws Exception {

    }

    @Override
    public void verifyMealBreakTimeDisplayAndFunctionality(boolean isEditMealBreakEnabled) {

    }

    @Override
    public void verifyDeleteShift() throws Exception {

    }

    @Override
    public void clickOnEditMeaLBreakTime() throws Exception {

    }

    @Override
    public void clickOnEditButtonNoMaterScheduleFinalizedOrNot() throws Exception {

    }

    @Override
    public void clickOnOpenShitIcon() {

    }

    @Override
    public String getTimeDurationWhenCreateNewShift() throws Exception {
        return null;
    }

    @Override
    public void verifyConvertToOpenShiftBySelectedSpecificTM() throws Exception {

    }


    @Override
    public int getOTShiftCount() {
        return 0;
    }

    @Override
    public void saveSchedule() {

    }

    @Override
    public void validateXButtonForEachShift() throws Exception {

    }

    @Override
    public void selectSpecificWorkDay(int dayCountInOneWeek) {

    }

    @Override
    public float getShiftHoursByTMInWeekView(String teamMember) {
        return 0;
    }

    @Override
    public void selectSpecificTMWhileCreateNewShift(String teamMemberName) {

    }

    @Override
    public void verifyWeeklyOverTimeAndFlag(String teamMemberName) throws Exception {

    }

    @Override
    public void deleteTMShiftInWeekView(String teamMemberName) {

    }

    @Override
    public void filterScheduleByJobTitle(boolean isWeekView) {

    }

    @Override
    public void filterScheduleByWorkRoleAndJobTitle(boolean isWeekView) {

    }

    @Override
    public void filterScheduleByShiftTypeAndJobTitle(boolean isWeekView) {

    }

    @Override
    public boolean verifyConvertToOpenPopUpDisplay(String firstNameOfTM) throws Exception {
        return false;
    }

    @Override
    public void convertToOpenShiftDirectly() {

    }

    @Override
    public void verifyScheduledNOpenFilterLoaded() throws Exception {

    }

    @Override
    public void checkAndUnCheckTheFilters() throws Exception {

    }

    @Override
    public void filterScheduleByBothAndNone() throws Exception {

    }

    @Override
    public String selectOneFilter() throws Exception {
        return null;
    }

    @Override
    public void verifySelectedFilterPersistsWhenSelectingOtherWeeks(String selectedFilter) throws Exception {

    }

    @Override
    public int selectOneShiftIsClaimShift(List<String> claimShift) throws Exception {
        return 0;
    }

    @Override
    public void verifyClaimShiftOfferNBtnsLoaded() throws Exception {

    }

    @Override
    public List<String> getShiftHoursFromInfoLayout() throws Exception {
        return null;
    }

    @Override
    public void verifyTheShiftHourOnPopupWithScheduleTable(String scheduleShiftTime, String weekDay) throws Exception {

    }

    @Override
    public String getSpecificShiftWeekDay(int index) throws Exception {
        return null;
    }

    @Override
    public void verifyClickAgreeBtnOnClaimShiftOffer() throws Exception {

    }

    @Override
    public void verifyClickCancelBtnOnClaimShiftOffer() throws Exception {

    }

    @Override
    public void verifyTheColorOfCancelClaimRequest(String cancelClaim) throws Exception {

    }

    @Override
    public void verifyReConfirmDialogPopup() throws Exception {

    }

    @Override
    public void verifyClickNoButton() throws Exception {

    }

    @Override
    public void verifyClickOnYesButton() throws Exception {

    }

    @Override
    public void verifyTheFunctionalityOfClearFilter() throws Exception {

    }


    @Override
    public String selectOneTeamMemberToSwap() throws Exception {
        return null;
    }

    @Override
    public void verifyClickCancelSwapOrCoverRequest() throws Exception {

    }

    @Override
    public void goToSchedulePageAsTeamMember() throws Exception {

    }

    @Override
    public void gotoScheduleSubTabByText(String subTitle) throws Exception {

    }

    @Override
    public void verifyTeamScheduleInViewMode() throws Exception {

    }

    @Override
    public List<String> getWholeWeekSchedule() throws Exception {
        return null;
    }

    @Override
    public String getSelectedWeek() throws Exception {
        return null;
    }

    @Override
    public void verifySelectOtherWeeks() throws Exception {

    }

    @Override
    public boolean isSpecificSmartCardLoaded(String cardName) throws Exception {
        return false;
    }

    @Override
    public int getCountFromSmartCardByName(String cardName) throws Exception {
        return 0;
    }

    @Override
    public void clickLinkOnSmartCardByName(String linkName) throws Exception {

    }

    @Override
    public int getShiftsCount() throws Exception {
        return 0;
    }

    @Override
    public void filterScheduleByShiftTypeAsTeamMember(boolean isWeekView) throws Exception {

    }

    @Override
    public boolean isPrintIconLoaded() throws Exception {
        return false;
    }

    @Override
    public void verifyThePrintFunction() throws Exception {

    }

    @Override
    public void clickCancelButtonOnPopupWindow() throws Exception {

    }

    @Override
    public void verifyTheDataOfComparableShifts() throws Exception {

    }

    @Override
    public void verifyTheSumOfSwapShifts() throws Exception {

    }

    @Override
    public void verifyNextButtonIsLoadedAndDisabledByDefault() throws Exception {

    }

    @Override
    public void verifySelectOneShiftNVerifyNextButtonEnabled() throws Exception {

    }

    @Override
    public void verifySelectMultipleSwapShifts() throws Exception {

    }

    @Override
    public void verifyClickOnNextButtonOnSwap() throws Exception {

    }

    @Override
    public void verifyBackNSubmitBtnLoaded() throws Exception {

    }

    @Override
    public void verifyTheRedirectionOfBackButton() throws Exception {

    }

    @Override
    public void verifySwapRequestShiftsLoaded() throws Exception {

    }

    @Override
    public void verifyClickAcceptSwapButton() throws Exception {

    }

    @Override
    public void verifyTheContentOfMessageOnSubmitCover() throws Exception {

    }

    @Override
    public void verifyShiftRequestStatus(String expectedStatus) throws Exception {

    }

    @Override
    public void dragRollerElementTillTextMatched(WebElement rollerElement, String textToMatch, boolean startHrsSlider) throws Exception {

    }

    //Added by Julie
    @Override
    public List<String> getWeekScheduleShiftTimeListOfMySchedule() throws Exception {
        return null;
    }

    @Override
    public List<String> getWeekScheduleShiftTimeListOfWeekView(String teamMemberName) throws Exception {
        return null;
    }

    @Override
    public void navigateToNextWeek() throws Exception {

    }

    @Override
    public void verifyShiftsAreSwapped(List<String> swapData) throws Exception {

    }

    @Override
    public void clickOnDayViewAddNewShiftButton() throws Exception {

    }

    @Override
    public void addNewShiftsByNames(List<String> names) throws Exception {

    }

    @Override
    public void validateTheAvailabilityOfScheduleTable(String userName) throws Exception {

    }

    @Override
    public void validateTheDisabilityOfLocationSelectorOnSchedulePage() throws Exception {

    }

    @Override
    public void validateTheAvailabilityOfScheduleMenu() throws Exception {

    }

    @Override
    public void validateTheFocusOfSchedule() throws Exception {

    }

    @Override
    public void validateTheDefaultFilterIsSelectedAsScheduled() throws Exception {

    }

    @Override
    public void validateTheFocusOfWeek(String dateFromLocation) throws Exception {

    }

    @Override
    public void validateForwardAndBackwardButtonClickable() throws Exception {

    }

    @Override
    public void validateTheDataAccordingToTheSelectedWeek() throws Exception {

    }

    @Override
    public void validateTheSevenDaysIsAvailableInScheduleTable() throws Exception {

    }

    @Override
    public String getTheEarliestAndLatestTimeInSummaryView(HashMap<String, Integer> schedulePoliciesBufferHours) throws Exception {
        return null;
    }

    @Override
    public String getTheEarliestAndLatestTimeInScheduleTable() throws Exception {
        return null;
    }

    @Override
    public void compareOperationHoursBetweenAdminAndTM(String theEarliestAndLatestTimeInScheduleSummary, String theEarliestAndLatestTimeInScheduleTable) throws Exception {

    }

    @Override
    public void validateThatHoursAndDateIsVisibleOfShifts() throws Exception {

    }

    @Override
    public void goToConsoleScheduleAndScheduleSubMenu() throws Exception {

    }

    @Override
    public void validateProfilePictureInAShiftClickable() throws Exception {

    }

    @Override
    public void validateTheDataOfProfilePopupInAShift() throws Exception {

    }


    @Override
    public void validateTheAvailabilityOfInfoIcon() throws Exception {

    }

    @Override
    public void validateInfoIconClickable() throws Exception {

    }

    @Override
    public void validateTheAvailabilityOfOpenShiftSmartcard() throws Exception {

    }

    @Override
    public void validateViewShiftsClickable() throws Exception {

    }

    @Override
    public void validateTheNumberOfOpenShifts() throws Exception {

    }

    @Override
    public void verifyTheAvailabilityOfClaimOpenShiftPopup() throws Exception {

    }

    @Override
    public void clickTheShiftRequestToClaimShift(String requestName, String requestUserName) throws Exception {

    }

    @Override
    public boolean displayAlertPopUp() throws Exception {
        return false;
    }

    @Override
    public void displayAlertPopUpForRoleViolation() throws Exception {

    }

    @Override
    public void unGenerateActiveScheduleFromCurrentWeekOnward(int loopCount) throws Exception {
    }

    public List<String> getOverviewData() throws Exception {
        return null;
    }

    @Override
    public void addOpenShiftWithLastDay(String workRole) throws Exception {

    }

    @Override
    public void deleteLatestOpenShift() throws Exception {

    }

    @Override
    public void addManualShiftWithLastDay(String workRole, String tmName) throws Exception {

    }

    @Override
    public HashMap<String, String> getTheHoursNTheCountOfTMsForEachWeekDays() throws Exception {
        return null;
    }

    @Override
    public HashMap<String, List<String>> getTheContentOfShiftsForEachWeekDay() throws Exception {
        return null;
    }

    @Override
    public HashMap<String, String> getBudgetNScheduledHoursFromSmartCard() throws Exception {
        return null;
    }

    @Override
    public int getComplianceShiftCountFromSmartCard(String cardName) throws Exception {
        return 0;
    }

    @Override
    public float createScheduleForNonDGByWeekInfo(String weekInfo, List<String> weekDaysToClose) throws Exception {
        return 0;
    }

    public void createScheduleByCopyFromOtherWeek(String weekInfo) throws Exception {

    }
    public void clickCreateScheduleBtn() throws Exception {

    }

    @Override
    public void editOperatingHoursWithGivingPrameters(String day, String startTime, String endTime) throws Exception {

    }

    @Override
    public void createScheduleForNonDGFlowNewUIWithGivingParameters(String day, String startTime, String endTime) throws Exception {

    }

    @Override
    public void goToToggleSummaryView() throws Exception {

    }

    @Override
    public void verifyOperatingHrsInToggleSummary(String day, String startTime, String endTime) throws Exception {

    }

    @Override
    public void verifyDayHasShifts(String day) throws Exception {

    }

    @Override
    public void verifyNoShiftsForSpecificWeekDay(List<String> weekDaysToClose) throws Exception {

    }

    @Override
    public void verifyStoreIsClosedForSpecificWeekDay(List<String> weekDaysToClose) throws Exception {

    }

    @Override
    public void verifyClosedDaysInToggleSummaryView(List<String> weekDaysToClose) throws Exception {

    }

    @Override
    public String getWeekInfoBeforeCreateSchedule() throws Exception {
        return null;
    }

    @Override
    public void verifyTheContentOnEnterBudgetWindow(String weekInfo, String location) throws Exception {

    }

    @Override
    public List<String> setAndGetBudgetForNonDGFlow() throws Exception {
        return null;
    }

    @Override
    public HashMap<String, String> verifyNGetBudgetNScheduleWhileCreateScheduleForNonDGFlowNewUI(String weekInfo, String location) throws Exception {
        return null;
    }

    @Override
    public List<String> getBudgetedHoursOnSTAFF() throws Exception {
        return null;
    }

    @Override
    public String getBudgetOnWeeklyBudget() throws Exception {
        return null;
    }

    @Override
    public void verifyChangesNotPublishSmartCard(int changesNotPublished) throws Exception {

    }

    @Override
    public void dragOneShiftToMakeItOverTime() throws Exception {

    }

    @Override
    public String getChangesOnActionRequired() throws Exception {
        return null;
    }

    @Override
    public String getTooltipOfUnpublishedDeleted() throws Exception {
        return null;
    }

    @Override
    public void verifyLabelOfPublishBtn(String labelExpected) throws Exception {

    }

    @Override
    public void verifyMessageIsExpected(String messageExpected) throws Exception {

    }

    @Override
    public void verifyWarningModelForAssignTMOnTimeOff(String nickName) throws Exception {

    }

    @Override
    public void selectAShiftToAssignTM(String username) throws Exception {

    }

    @Override
    public void verifyInactiveMessageNWarning(String username, String date) throws Exception {

    }

    @Override
    public List<String> getTheShiftInfoByIndex(int index) throws Exception {
        return null;
    }

    @Override
    public void selectWorkingDaysOnNewShiftPageByIndex(int index) throws Exception {

    }

    @Override
    public void validateScheduleTableWhenSelectAnyOfGroupByOptions() throws Exception {

    }

    @Override
    public void verifyScheduledWarningWhenAssigning(String userName, String shiftTime) throws Exception {

    }

    @Override
    public void changeWorkRoleInPrompt(boolean isApplyChange) throws Exception {

    }

    @Override
    public void clickOnAnalyzeBtn() throws Exception {

    }

    @Override
    public void switchSearchTMAndRecommendedTMsTab() throws Exception {

    }

    @Override
    public void verifyScheduleVersion(String version) throws Exception {

    }

    @Override
    public String convertToOpenShiftAndOfferToSpecificTMs() throws Exception {
        return null;
    }

    @Override
    public void clickOnEditShiftTime() throws Exception{

    }

    @Override
    public void verifyEditShiftTimePopUpDisplay() throws Exception {

    }

    @Override
    public List<String> editShiftTime() throws Exception {
        return null;
    }

    @Override
    public void clickOnCancelEditShiftTimeButton() throws Exception{

    }

    @Override
    public void clickOnUpdateEditShiftTimeButton() throws Exception{

    }

    @Override
    public void closeAnalyzeWindow() throws Exception {

    }

    @Override
    public void verifyShiftTime(String shiftTime) throws Exception {

    }

    @Override
    public void verifyVersionInSaveMessage(String version) throws Exception {

    }

    @Override
    public String getShiftTime() {
        return null;
    }

    @Override
    public void  verifyDeleteShiftCancelButton() throws Exception {

    }

    @Override
    public void verifyDeleteMealBreakFunctionality() throws Exception {

    }

    @Override
    public void verifyEditMealBreakTimeFunctionality(boolean isSavedChange) throws Exception {

    }

    @Override
    public void editAndVerifyShiftTime(boolean isSaveChange) throws Exception{

    }

    @Override
    public String selectTeamMembers() throws Exception {
        return null;
    }

    @Override
    public void clickOnManagerButton() throws Exception {

    }

    @Override
    public void verifyAllShiftsAssigned() throws Exception {

    }

    @Override
    public void clickProfileIconOfShiftByIndex(int index) throws Exception {

    }

    @Override
    public void clickViewStatusBtn() throws Exception {

    }

    @Override
    public void verifyListOfOfferNotNull() throws Exception {

    }

    @Override
    public void clickOnOpenSearchBoxButton() throws Exception {

    }

    @Override
    public void verifyGhostTextInSearchBox () throws Exception{

    }

    @Override
    public List<WebElement> searchShiftOnSchedulePage(String searchText) throws Exception {
        return null;
    }

    @Override
    public void verifySearchResult (String firstNameOfTM, String lastNameOfTM, String workRole, String jobTitle, List<WebElement> searchResults) throws Exception {

    }

    @Override
    public void clickOnCloseSearchBoxButton() throws Exception {

    }

    @Override
    public void verifySearchBoxNotDisplayInDayView() throws Exception {

    }

    @Override
    public int getRandomIndexOfShift() {
        return 0;
    }

    @Override
    public void goToSpecificWeekByDate(String date) throws Exception {

    }

    @Override
    public void clearAllSelectedDays() throws Exception {

    }

    @Override
    public List<Integer> selectDaysByCountAndCannotSelectedDate(int count, String cannotSelectedDate) throws Exception {
        return null;
    }

    @Override
    public void dragOneAvatarToAnother(int startIndex, String firstName, int endIndex) throws Exception {

    }

    @Override
    public int getTheIndexOfTheDayInWeekView(String date) throws Exception {
        return 0;
    }

    @Override
    public HashMap<String,Integer> dragOneAvatarToAnotherSpecificAvatar(int startIndexOfTheDay, String user1, int endIndexOfTheDay, String user2) throws Exception {
        return null;
    }

    @Override
    public void verifyMessageInConfirmPage(String expectedMassageInSwap, String expectedMassageInAssign) throws Exception {

    }

    @Override
    public void selectSwapOrAssignOption(String action) throws Exception {

    }

    @Override
    public void clickConfirmBtnOnDragAndDropConfirmPage() throws Exception {

    }

    @Override
    public WebElement getShiftById(String id) throws Exception {
        return null;
    }


    @Override
    public List<String> getShiftSwapDataFromConfirmPage(String action) throws Exception {
        return null;
    }

    @Override
    public void createScheduleForNonDGFlowNewUIWithGivingTimeRange(String startTime, String endTime) throws Exception {

    }

    @Override
    public int verifyDayHasShiftByName(int indexOfDay, String name) throws Exception {
        return 0;
    }

    @Override
    public String getWeekDayTextByIndex(int index) throws Exception {
        return null;
    }

    @Override
    public void selectDaysByIndex(int index1, int index2, int index3) throws Exception {

    }

    @Override
    public boolean verifySwapAndAssignWarningMessageInConfirmPage(String expectedMessage, String action) throws Exception {
        return false;
    }

    @Override
    public void clickCancelBtnOnDragAndDropConfirmPage() throws Exception {

    }

    @Override
    public List<String> getOpenShiftInfoByIndex(int index) throws Exception {
        return null;
    }

    @Override
    public List<WebElement> getOneDayShiftByName(int indexOfDay, String name) throws Exception {
        return null;
    }

    @Override
    public List<String> getComplianceMessageFromInfoIconPopup(WebElement shift) throws Exception {
        return null;
    }

    @Override
    public void dragOneShiftToAnotherDay(int startIndex, String firstName, int endIndex) throws Exception {

    }

    @Override
    public boolean ifWarningModeDisplay() throws Exception {
        return false;
    }

    @Override
    public String getWarningMessageInDragShiftWarningMode() throws Exception{
        return null;
    }

    @Override
    public void clickOnOkButtonInWarningMode() throws Exception {

    }

    @Override
    public List<String> getSelectedDayInfoFromCreateShiftPage() throws Exception {
        return null;
    }

    @Override
    public void moveAnywayWhenChangeShift() throws Exception {

    }

    @Override
    public boolean ifMoveAnywayDialogDisplay() throws Exception {
        return false;
    }

    @Override
    public void verifyShiftIsMovedToAnotherDay(int startIndex, String firstName, int endIndex) throws Exception {
        
    }

    public String getTheMessageOfTMScheduledStatus() throws Exception {
        return null;
    }

    @Override
    public void verifyWarningModelMessageAssignTMInAnotherLocWhenScheduleNotPublished() throws Exception {

    }

    @Override
    public void verifyTMNotSelected() throws Exception {

    }

    @Override
    public void verifyAlertMessageIsExpected(String messageExpected) throws Exception {

    }

    @Override
    public void clickOnRadioButtonOfSearchedTeamMemberByName(String name) throws Exception{

    }

    @Override
    public void clickOnAssignAnywayButton() throws Exception{

    }

    @Override
    public WebElement getTheShiftByIndex(int index) throws Exception {
        return null;
    }

    @Override
    public void editShiftTimeToTheLargest() throws Exception {

    }

    @Override
    public void closeCustomizeNewShiftWindow() throws Exception {

    }

    public List<String> getHolidaysOfCurrentWeek() throws Exception {
        return null;
    }

    @Override
    public String getAllTheWarningMessageOfTMWhenAssign() throws Exception {
        return null;
    }

    @Override
    public int getTheIndexOfCurrentDayInDayView() throws Exception {
        return 0;
    }

    @Override
    public void selectWeekDaysByDayName(String dayName) throws Exception {

    }

    @Override
    public void editOperatingHoursOnScheduleOldUIPage(String startTime, String endTime, List<String> weekDaysToClose) throws Exception {

    }

    @Override
    public int getTheIndexOfEditedShift() throws Exception {
        return 0;
    }

    @Override
    public void navigateToTheRightestSmartCard() throws Exception {

    }

    @Override
    public boolean isEditMealBreakEnabled() throws Exception {
        return false;
    }

    @Override
    public void verifyTMSchedulePanelDisplay() throws Exception {

    }

    @Override
    public void verifyPreviousWeekWhenCreateAndCopySchedule(String weekInfo, boolean shouldBeSelected) throws Exception {

    }

    @Override
    public void clickNextBtnOnCreateScheduleWindow() throws Exception {

    }

    @Override
    public void clickBackBtnAndExitCreateScheduleWindow() throws Exception {

    }

    @Override
    public void editTheOperatingHours(List<String> weekDaysToClose) throws Exception {

    }

    @Override
    public float getStaffingGuidanceHrs() throws Exception {
        return (float) 0;
    }

    @Override
    public void verifyTooltipForCopyScheduleWeek(String weekInfo) throws Exception {

    }

    @Override
    public String convertDateStringFormat(String dateString) throws Exception{
        return null;
    }

    @Override
    public void verifyDifferentOperatingHours(String weekInfo) throws Exception {

    }

    @Override
    public void clickOnFinishButtonOnCreateSchedulePage() throws Exception{

    }

    @Override
    public List<String> getDayShifts(String index) throws Exception {
        return null;
    }
}