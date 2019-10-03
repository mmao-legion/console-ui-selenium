package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getCurrentTestMethodName;
import static com.legion.utils.MyThreadLocal.getDriver;

import com.legion.test.core.mobile.LoginTest;
import com.legion.tests.core.ScheduleNewUITest;
import com.legion.tests.core.ScheduleRoleBasedTest.scheduleHoursAndWagesData;
import com.legion.tests.core.ScheduleTest.SchedulePageSubTabText;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.legion.pages.BasePage;
import com.legion.pages.ScheduleOverviewPage;
import com.legion.pages.SchedulePage;
import com.legion.utils.SimpleUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleSchedulePage extends BasePage implements SchedulePage {


    String dayWeekPicker;

    @FindBy(xpath = "//*[@id='legion-app']/div/div[2]/div/div/div/div[1]/navigation/div/div[4]")
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

    @FindBy(css = "table:nth-child(2) tr.table-row.ng-scope td:nth-child(2)")
    private List<WebElement> guidanceHour;

    @FindBy(css = "input[ng-class='hoursFieldClass(budget)']")
    private List<WebElement> budgetEditHours;

    @FindBy(css = "table td:nth-child(2)")
    private List<WebElement> budgetDisplayOnScheduleSmartcard;

    @FindBy (css = "table td:nth-child(3)")
    private List<WebElement> scheduleDisplayOnScheduleSmartcard;

    @FindBy(xpath = "//div[contains(@class,'card-carousel-card-sub-title')][contains(text(),'Hours')]")
    private WebElement budgetOnbudgetSmartCard;

    @FindBy (css = "div.console-navigation-item-label.Schedule")
	private WebElement consoleSchedulePageTabElement;

    @FindBy (css = "div[ng-if*='guidance-week-shifts']")
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
    public Boolean varifyActivatedSubTab(String SubTabText) throws Exception {
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
        if (ScheduleSubTabsElement.size() != 0 && !varifyActivatedSubTab(subTabString)) {
            System.out.println("clickOnScheduleSubTab size: " + ScheduleSubTabsElement.size());
            for (WebElement ScheduleSubTabElement : ScheduleSubTabsElement) {
                System.out.println("ScheduleSubTabElement Text: " + ScheduleSubTabElement.getText());
                if (ScheduleSubTabElement.getText().equalsIgnoreCase(subTabString)) {
                    System.out.println("CLicked");
                    click(ScheduleSubTabElement);
                }
            }

        }

        if (varifyActivatedSubTab(subTabString)) {
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

	@Override
	public void budgetHourInScheduleNBudgetedSmartCard(String nextWeekView,int weekCount) {
		waitForSeconds(3);
		for(int i = 0; i < weekCount; i++)
			{
				float totalBudgetedHourForSmartCard=0.0f;
				float totalScheduledHourIfBudgetEntered=0.0f;
				if(nextWeekView.toLowerCase().contains("next") || nextWeekView.toLowerCase().contains("future"))
				{
					try {
						if(isElementLoaded(schedulesForWeekOnOverview.get(0))) {
							waitForSeconds(3);
							click(schedulesForWeekOnOverview.get(i));
							waitForSeconds(4);
							String[] daypickers = daypicker.getText().split("\n");
							String[] budgetDisplayOnSmartCard = budgetOnbudgetSmartCard.getText().split(" ");
							String budgetOnScheduleSmartcard = budgetDisplayOnScheduleSmartcard.get(0).getText();
							if (budgetOnbudgetSmartCard.getText().equalsIgnoreCase("-- Hours")) {
								SimpleUtils.pass(daypickers[1] + " week has no budget entered");
								waitForSeconds(2);
								checkElementVisibility(returnToOverviewTab);
								click(returnToOverviewTab);
							} else {
								click(enterBudgetLink);
								waitForSeconds(3);
								for (int j = 1; j < guidanceHour.size(); j++) {
									totalBudgetedHourForSmartCard = totalBudgetedHourForSmartCard + Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"));
									if (((Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"))) == 0)) {
										totalScheduledHourIfBudgetEntered = totalScheduledHourIfBudgetEntered + Float.parseFloat(guidanceHour.get(j - 1).getText());

									} else {
										totalScheduledHourIfBudgetEntered = totalScheduledHourIfBudgetEntered + Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"));
									}
								}
								if (totalBudgetedHourForSmartCard == (Float.parseFloat(budgetDisplayOnSmartCard[0]))) {
									SimpleUtils.pass("Budget " + (Float.parseFloat(budgetDisplayOnSmartCard[0])) +" for week " +daypickers[1] + " on budget smartcard matches the budget entered " + totalBudgetedHourForSmartCard);
								} else {
									SimpleUtils.fail("Budget " + (Float.parseFloat(budgetDisplayOnSmartCard[0]))  +" for week " +daypickers[1] + " on budget smartcard doesn't match the budget entered " + totalBudgetedHourForSmartCard, false);
								}

								if (totalScheduledHourIfBudgetEntered == (Float.parseFloat(budgetOnScheduleSmartcard))) {
									SimpleUtils.pass("Budget " + (Float.parseFloat(budgetOnScheduleSmartcard))  +" for week " +daypickers[1] + " on schedule smartcard matches the budget entered " + totalScheduledHourIfBudgetEntered);
								} else {
									SimpleUtils.fail("Budget " + (Float.parseFloat(budgetOnScheduleSmartcard))  +" for week " +daypickers[1] + " on scchedule smartcard doesn't match the budget entered " + totalScheduledHourIfBudgetEntered, false);
								}
								checkElementVisibility(enterBudgetCancelButton);
								click(enterBudgetCancelButton);
								checkElementVisibility(returnToOverviewTab);
								click(returnToOverviewTab);
							}
						}
					}

					catch (Exception e) {
					SimpleUtils.fail("Budget pop-up not opening ",false);
					}
				}
			}
	}


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
			if(isElementLoaded(scheduleWeekView)){
				click(scheduleWeekView);
				SimpleUtils.pass("Clicked on Week View of Schedule Tab");
				if(isElementLoaded(smartcard)){
					flag = true;
					SimpleUtils.pass("Smartcard Section in Week View Loaded Successfully!");
				}else{
					SimpleUtils.fail("Smartcard Section in Week View Not Loaded Successfully!", true);
				}
				if(isElementLoaded(scheduleTableWeekView)){
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
	public void dragRollerElementTillTextMatched(WebElement rollerElement, String textToMatch) throws Exception {
		// TODO Auto-generated method stub

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
}