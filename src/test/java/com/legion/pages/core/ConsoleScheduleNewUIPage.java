package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.*;
import static com.legion.utils.MyThreadLocal.setTeamMemberName;
import static org.testng.Assert.fail;

import com.gargoylesoftware.htmlunit.html.Keyboard;
import com.legion.tests.core.ScheduleNewUITest;
import com.legion.utils.FileDownloadVerify;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;

import cucumber.api.java.hu.Ha;
import cucumber.api.java.sl.In;
import org.apache.http.impl.execchain.TunnelRefusedException;
import org.apache.xpath.axes.HasPositionalPredChecker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.ClickElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.legion.pages.BasePage;
import com.legion.pages.SchedulePage;
import com.legion.tests.core.ScheduleNewUITest.SchedulePageSubTabText;
import com.legion.tests.core.ScheduleNewUITest.staffingOption;
import com.legion.utils.SimpleUtils;

import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import java.awt.*;
import java.lang.reflect.Method;
import java.net.SocketImpl;
import java.sql.Driver;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleScheduleNewUIPage extends BasePage implements SchedulePage {

    private static HashMap<String, String> propertySearchTeamMember = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SearchTeamMember.json");
    private static HashMap<String, String> propertyWorkRole = JsonUtil.getPropertiesFromJsonFile("src/test/resources/WorkRoleOptions.json");
    private static HashMap<String, String> propertyBudgetValue = JsonUtil.getPropertiesFromJsonFile("src/test/resources/Budget.json");
    private static HashMap<String, String> parameterMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
    private static HashMap<String, String> parametersMap2 = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ControlsPageLocationDetail.json");
    public enum scheduleHoursAndWagesData {
        scheduledHours("scheduledHours"),
        budgetedHours("budgetedHours"),
        otherHours("otherHours"),
        budgetedWages("budgetedWages"),
        scheduledWages("scheduledWages"),
        otherWages("otherWages"),
        wages("Wages"),
        hours("hours");
        private final String value;

        scheduleHoursAndWagesData(final String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    public enum scheduleGroupByFilterOptions {
        groupbyAll("Group by All"),
        groupbyWorkRole("Group by Work Role"),
        groupbyTM("Group by TM");
        private final String value;

        scheduleGroupByFilterOptions(final String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    public enum dayCount {
        Seven(7);
        private final int value;

        dayCount(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }
    }

    public ConsoleScheduleNewUIPage() {
        PageFactory.initElements(getDriver(), this);
    }

    @FindBy(css = "div.console-navigation-item-label.Schedule")
    private WebElement goToScheduleButton;

    @FindBy(css = "div[helper-text*='Work in progress Schedule'] span.legend-label")
    private WebElement draft;

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

    @FindBy(css = "lg-button[label=\"Analyze\"]")
    private WebElement analyze;

    @FindBy(css = "lg-button[label=\"Edit\"]")
    private WebElement edit;

    @FindBy(css = "lg-button[data-tootik=\"Edit Schedule\"]")
    private WebElement newEdit;

    @FindBy(xpath = "//span[contains(text(),'Projected Sales')]")
    private WebElement goToProjectedSalesTab;

    @FindBy(css = "ui-view[name='forecastControlPanel'] span.highlight-when-help-mode-is-on")
    private WebElement salesGuidance;

    @FindBy(css = "[ng-click=\"controlPanel.fns.refreshConfirmation($event)\"]")
    private WebElement refresh;

    @FindBy(css = "button.btn.sch-publish-confirm-btn")
    private WebElement confirmRefreshButton;

    @FindBy(css = "button.btn.successful-publish-message-btn-ok")
    private WebElement okRefreshButton;

    @FindBy(xpath = "//div[contains(text(),'Guidance')]")
    private WebElement guidance;

    @FindBy(xpath = "//span[contains(text(),'Staffing Guidance')]")
    private WebElement goToStaffingGuidanceTab;

//	@FindBy(className="sch-calendar-day-dimension")
//	private List<WebElement> ScheduleCalendarDayLabels;

    @FindBy(css = "div.sub-navigation-view-link")
    private List<WebElement> ScheduleSubTabsElement;

    @FindBy(className = "day-week-picker-arrow-right")
    private WebElement calendarNavigationNextWeekArrow;

    @FindBy(className = "day-week-picker-arrow-left")
    private WebElement calendarNavigationPreviousWeekArrow;

    @FindBy(css = "[ng-click=\"regenerateFromOverview()\"]")
    private WebElement generateSheduleButton;

    @FindBy(css = "[label='Generate Schedule']")
    private WebElement generateSheduleForEnterBudgetBtn;

    @FindBy(css = "lg-button[label*=\"ublish\"]")
    private WebElement publishSheduleButton;

    @FindBy(css = "lg-button[label*=\"ublish\"] span span")
    private WebElement txtPublishSheduleButton;

    @FindBy(css = "div.edit-budget span.header-text")
    private WebElement popUpGenerateScheduleTitleTxt;

    @FindBy(css = "span.ok-action-text")
    private WebElement btnGenerateBudgetPopUP;

    @FindBy(css = "div[ng-if='canEditHours(budget)']")
    private List<WebElement> editBudgetHrs;

    @FindBy(css = "span[ng-if='canEditWages(budget)']")
    private List<WebElement> editWagesHrs;

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

    @FindBy(css = "lg-button[label=\"Generate Schedule\"]")
    private WebElement generateScheduleBtn;

    @FindBy(className = "console-navigation-item")
    private List<WebElement> consoleNavigationMenuItems;

    @FindBy(css = "[ng-click=\"callOkCallback()\"]")
    private WebElement editAnywayPopupButton;

    @FindBy(css = "[ng-if=\"canShowNewShiftButton()\"]")
    private WebElement addNewShiftOnDayViewButton;

    @FindBy(css = "lg-button[label=\"Cancel\"]")
    private WebElement scheduleEditModeCancelButton;

    @FindBy(css = "[ng-click=\"regenerateFromOverview()\"]")
    private WebElement scheduleGenerateButton;

    @FindBy(css = "#legion-app navigation div:nth-child(4)")
    private WebElement analyticsConsoleName;

    //added by Nishant

    @FindBy(css = ".modal-content")
    private WebElement customizeNewShift;

    @FindBy(css = "div.sch-day-view-grid-header span:nth-child(1)")
    private WebElement shiftStartday;

    @FindBy(css = "div.lgn-time-slider-notch-selector-start span.lgn-time-slider-label")
    private WebElement customizeShiftStartdayLabel;

    @FindBy(css = "div.lgn-time-slider-notch-selector-end span.lgn-time-slider-label")
    private WebElement customizeShiftEnddayLabel;

    @FindBy(css = "div.lgn-time-slider-notch-selector-start")
    private WebElement sliderNotchStart;

    @FindBy(css = "div.lgn-time-slider-notch-selector-end")
    private WebElement sliderNotchEnd;

    @FindBy(css = "div.lgn-time-slider-notch.droppable")
    private List<WebElement> sliderDroppableCount;

    @FindBy(css = "div.lgn-dropdown.open ul li")
    private List<WebElement> listWorkRoles;

    @FindBy(css = "button.lgn-dropdown-button:nth-child(1)")
    private WebElement btnWorkRole;

    @FindBy(xpath = "//div[contains(text(),'Open Shift: Auto')]")
    private WebElement textOpenShift;

    @FindBy(xpath = "//div[contains(text(),'Open Shift: Auto')]/parent::div/parent::div/div/div[@class='tma-staffing-option-outer-circle']")
    private WebElement radioBtnOpenShift;

    @FindBy(css = ".tma-staffing-option-text-title")
    private List<WebElement> radioBtnShiftTexts;

    @FindBy(css = ".tma-staffing-option-radio-button")
    private List<WebElement> radioBtnStaffingOptions;

    @FindBy(xpath = "//div[contains(@class,'sch-day-view-right-gutter-text')]//parent::div//parent::div/parent::div[contains(@class,'sch-shift-container')]//div[@class='break-container']")
    private List<WebElement> shiftContainer;

    @FindBy(css = "button.tma-action")
    private WebElement btnSave;

    @FindBy(css = "div.sch-day-view-shift-delete")
    private List<WebElement> shiftDeleteBtn;

    @FindBy(xpath = "//div[contains(text(),'Delete')]")
    private List<WebElement> shiftDeleteGutterText;

    @FindBy(css = "div.sch-day-view-right-gutter-text")
    private List<WebElement> gutterText;

    @FindBy(css = "div.sch-day-view-right-gutter")
    private List<WebElement> gutterCount;

    @FindBy(css = "div.sch-day-view-grid-header.fill span")
    private List<WebElement> gridHeaderDayHour;

    @FindBy(xpath = "//div[contains(@class,'sch-day-view-grid-header fill')]/following-sibling::div//div[@data-tootik='TMs in Schedule']/parent::div")
    private List<WebElement> gridHeaderTeamCount;

    @FindBy(xpath = "//span[contains(text(),'Save')]")
    private WebElement scheduleSaveBtn;

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

    @FindBy(css = ".tma-search-field-input-text")
    private WebElement textSearch;

    @FindBy(css = "div.tab-label")
    private List<WebElement> btnSearchteamMember;

    @FindBy(css = ".sch-search")
    private WebElement searchIcon;

    @FindBy(css = ".table-row")
    private List<WebElement> tableRowCount;

    @FindBy(xpath = "//div[@class='worker-edit-availability-status']//span[contains(text(),'Available')]")
    private List<WebElement> availableStatus;

    @FindBy(xpath = "//div[@class='worker-edit-availability-status']")
    private List<WebElement> scheduleStatus;

	@FindBy(xpath="//div[@class='tma-search-action']/following-sibling::div[1]//div[@class='worker-edit-availability-status']")
	private List<WebElement> scheduleSearchTeamMemberStatus;

	@FindBy(xpath="//div[@class='tab-label']/span[text()='Search Team Members']")
	private WebElement btnSearchTeamMember;


	@FindBy(xpath="//span[contains(text(),'Best')]")
	private List<WebElement> scheduleBestMatchStatus;

    @FindBy(css="div.tma-empty-search-results")
    private WebElement scheduleNoAvailableMatchStatus;

    @FindBy(css = "div.worker-edit-search-worker-name")
    private List<WebElement> searchWorkerName;

	@FindBy(xpath="//div[@class='tma-search-action']/following-sibling::div[1]//div[@class='worker-edit-search-worker-name']")
	private List<WebElement> searchWorkerDisplayName;

    @FindBy(xpath="//div[@class='tma-search-action']/following-sibling::div[1]//div[@class='worker-edit-search-worker-name']/following-sibling::div")
    private List<WebElement> searchWorkerRole;

    @FindBy(xpath="//div[@class='tma-search-action']/following-sibling::div[1]//div[@class='worker-edit-search-worker-name']/following-sibling::div[2]")
    private List<WebElement> searchWorkerLocation;

    @FindBy(xpath="//div[@class='sch-day-view-shift ng-scope']//div[contains(@class,'sch-day-view-shift-time')]")
    private WebElement searchWorkerSchShiftTime;

    @FindBy(xpath="//div[@class='sch-day-view-shift ng-scope']//div[contains(@class,'sch-day-view-worker-time')]")
    private WebElement searchWorkerSchShiftDuration;

    @FindBy(css = "td.table-field.action-field.tr>div")
    private List<WebElement> radionBtnSelectTeamMembers;

	@FindBy(xpath="//div[@class='tma-search-action']/following-sibling::div[1]//div[@class='tma-staffing-option-outer-circle']")
	private List<WebElement> radionBtnSearchTeamMembers;

	@FindBy(css="button.tma-action.sch-save")
	private WebElement btnOffer;

    @FindBy(xpath = "//button[contains(text(),'UPDATE')]")
    private WebElement updateAndGenerateScheduleButton;



	//added by Naval

    @FindBy(css = "input-field[placeholder='None'] ng-form.input-form.ng-pristine.ng-valid-pattern")
    private WebElement filterButton;

    @FindBy(css = "[ng-repeat=\"(key, opts) in $ctrl.displayFilters\"]")
    private List<WebElement> scheduleFilterElements;

    @FindBy(css = "div.lg-filter__wrapper")
    private WebElement filterPopup;

    @FindBy(css = "div.sch-calendar-day-dimension")
    private List<WebElement> weekViewDaysAndDates;

    @FindBy(css = "div.sch-week-view-day-summary")
    private List<WebElement> weekDaySummeryHoursAndTeamMembers;

    @FindBy(css = "div.sch-shift-transpose-data-container")
    private List<WebElement> shiftsOnScheduleView;


    @FindBy(css = "div.sch-day-view-grid-header.fill")
    private List<WebElement> dayViewShiftsTimeDuration;

    @FindBy(css = "div.sch-day-view-grid-header.tm-count.guidance")
    private List<WebElement> dayViewbudgetedTMCount;

    @FindBy(xpath = "//div[contains(@class,'sch-day-view-grid-header tm-count') and not(contains(@class,'guidance'))]")
    private List<WebElement> dayViewScheduleTMsCount;

    @FindBy(css = "select.ng-valid-required")
    private WebElement scheduleGroupByButton;

    @FindBy(css = "div.tab.ng-scope")
    private List<WebElement> selectTeamMembersOption;

    @FindBy(xpath = "//span[contains(text(),'TMs')]")
    private WebElement selectRecommendedOption;

    @FindBy(css = "div.tma-scroll-table tr")
    private List<WebElement> recommendedScrollTable;

    @FindBy(css = "button.btn-success")
    private WebElement upgradeAndGenerateScheduleBtn;

    @FindBy(css = "div.version-label")
    private List<WebElement> versionHistoryLabels;

    @FindBy(className = "sch-schedule-analyze-dismiss-button")
    private WebElement dismissanAlyzeButton;

    @FindBy(className = "sch-publish-confirm-btn")
    private WebElement publishConfirmBtn;

    @FindBy(css = "span.wm-close-link")
    private WebElement closeLegionPopUp;

    @FindBy(className = "successful-publish-message-btn-ok")
    private WebElement successfulPublishOkBtn;

    @FindBy(css = "div.holiday-logo-container")
    private WebElement holidayLogoContainer;

    @FindBy(css = "tr[ng-repeat=\"day in summary.workingHours\"]")
    private List<WebElement> guidanceWeekOperatingHours;

    @FindBy(className = "sch-group-header")
    private List<WebElement> scheduleShiftHeaders;

    @FindBy(css = "div.card-carousel-card.card-carousel-card-smart-card-required")
    private WebElement requiredActionCard;

    @FindBy(className = "sch-day-view-shift-outer")
    private List<WebElement> dayViewAvailableShifts;

    @FindBy(css = "div.card-carousel-card")
    private List<WebElement> carouselCards;

    @FindBy(css = "div.sch-calendar-day-dimension.sch-calendar-day")
    private List<WebElement> ScheduleWeekCalendarDates;

    @FindBy(css = "div.card-carousel")
    private WebElement smartCardPanel;

    @FindBy(css = "div.sch-worker-popover")
    private WebElement shiftPopover;

    @FindBy(css = "button.sch-action.sch-save")
    private WebElement convertToOpenYesBtn;

    @FindBy(css = "div.card-carousel-arrow.card-carousel-arrow-right")
    private WebElement smartcardArrowRight;

    @FindBy(css = "div.card-carousel-arrow.card-carousel-arrow-left")
    private WebElement smartcardArrowLeft;

    @FindBy(css = "div.schedule-action-buttons")
    private List<WebElement> actionButtonDiv;

    @FindBy(css = "span.weather-forecast-temperature")
    private List<WebElement> weatherTemperatures;

    @FindBy(css = "input[ng-class='hoursFieldClass(budget)']")
    private List<WebElement> budgetEditHours;

    @FindBy(css = "div.sch-shift-container")
    private List<WebElement> scheduleShiftsRows;

    @FindBy(css = "div.sch-day-view-grid-header.fill")
    private List<WebElement> scheduleShiftTimeHeaderCells;

    @FindBy(css = "img[ng-if=\"hasViolateCompliance(line, scheduleWeekDay)\"]")
    private List<WebElement> complianceReviewDangerImgs;

    @FindBy(css = "lg-dropdown-base[ng-if=\"isAdmin\"]")
    private WebElement scheduleAdminDropDownBtn;

    @FindBy(css = "div[ng-repeat=\"action in supportedAdminActions.actions\"]")
    private List<WebElement> scheduleAdminDropDownOptions;

    @FindBy(css = "button[ng-click=\"yesClicked()\"]")
    private WebElement unGenerateBtnOnPopup;

	//added by Nishant

	@FindBy(css = "div.lgn-alert-modal")
	private WebElement popUpScheduleOverlap;

	@FindBy(css = "button.lgn-action-button-success")
	private WebElement btnAssignAnyway;

	@FindBy(css = "div.lgn-alert-message")
	private WebElement alertMessage;

    @FindBy(css = "div.schedule-filter-label")
    private WebElement scheduleType;

    @FindBy(css = "lg-button-group[buttons='scheduleTypeOptions'] div.lg-button-group-first")
    private WebElement scheduleTypeSystem;

    @FindBy(css = "lg-button-group[buttons='scheduleTypeOptions'] div.lg-button-group-last")
    private WebElement scheduleTypeManager;

    @FindBy(css = "lg-button-group[buttons='scheduleTypeOptions'] div.lg-button-group-selected")
    private WebElement activScheduleType;

    //moved from ConsoleSchedulePage
    String dayWeekPicker;

    @FindBy(css = "span[ng-if='canEditEstimatedHourlyWage(budget)']")
    private List<WebElement> scheduleDraftWages;

    @FindBy (xpath = "//span[contains(text(),'Schedule')]")
    private WebElement ScheduleSubMenu;

    @FindBy(className="schedule-status-title")
    private List<WebElement> scheduleOverviewWeeksStatus;

    @FindBy(css = "div.fx-center.left-banner")
    private List<WebElement> overviewPageScheduleWeekDurations;

    @FindBy(css = "[ng-click=\"gotoPreviousWeek($event)\"]")
    private WebElement salesForecastCalendarNavigationPreviousWeekArrow;

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

    @FindBy(xpath = "//*[text()=\"Day\"]")
    private WebElement daypButton;

    @FindBy(xpath = "//*[text()=\"Week\"]")
    private WebElement weekButton;

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

    @FindBy(css = "table:nth-child(2) tr.table-row.ng-scope td:nth-child(4)")
    private List<WebElement> guidanceWages;

    @FindBy(css = "table:nth-child(2) tr.table-row.ng-scope td:nth-child(5)")
    private List<WebElement> budgetHourWhenBudgetByWagesEnabled;

    @FindBy(css = "table td:nth-child(2)")
    private List<WebElement> budgetDisplayOnScheduleSmartcard;

    @FindBy (css = "table td:nth-child(3)")
    private List<WebElement> scheduleDisplayOnScheduleSmartcard;

//    @FindBy(xpath = "ng-include[ng-repeat='c in cards']")
//    private WebElement budgetOnbudgetSmartCardWhenNoBudgetEntered;

    @FindBy(css = "ng-include[ng-repeat='c in cards']")
    private WebElement budgetOnbudgetSmartCardWhenNoBudgetEntered;

    @FindBy(xpath = "//div[@class='card-carousel-card card-carousel-card-default']//div[contains(text(),'')]/following-sibling::h1")
    private WebElement budgetOnbudgetSmartCard;

    @FindBy (css = "div.console-navigation-item-label.Schedule")
    private WebElement consoleSchedulePageTabElement;

    @FindBy (css = "week-view-detail[weekly-schedule-data='weeklyScheduleData']")
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

    @FindBy (css = "div.day-week-picker-period-week.day-week-picker-period-active")
    private WebElement currentActiveWeek;

    @FindBy (css = "div.sch-shift-transpose-second-row")
    private List<WebElement> scheduleWeekViewGrid;

    @FindBy (css = "[on-change=\"updateGroupBy(value)\"]")
    private WebElement groupByAllIcon;

    @FindBy(css = "[ng-click=\"printAction($event)\"]")
    private WebElement printButton;

    @FindBy(xpath ="//*[text()=\"Portrait\"]")
    private WebElement PortraitButton;

    @FindBy(xpath ="//*[text()=\"Landscape\"]")
    private WebElement LandscapeButton;

    @FindBy(css = "[ng-click=\"showTodos($event)\"]")
    private WebElement todoButton;

    @FindBy (css = ".horizontal.is-shown")
    private WebElement todoSmartCard;

    @FindBy(css = "[label=\"Print\"]")
    private WebElement printButtonInPrintLayout;

    @FindBy(css = "[label=\"Cancel\"]")
    private WebElement cannelButtonInPrintLayout;

    List<String> scheduleWeekDate = new ArrayList<String>();
    List<String> scheduleWeekStatus = new ArrayList<String>();

    Map<String, String> weeklyTableRowsDatesAndStatus = new LinkedHashMap<String, String>();


    final static String consoleScheduleMenuItemText = "Schedule";


    public void clickOnScheduleConsoleMenuItem() {
        if (consoleNavigationMenuItems.size() != 0) {
            WebElement consoleScheduleMenuElement = SimpleUtils.getSubTabElement(consoleNavigationMenuItems, consoleScheduleMenuItemText);
            activeConsoleName = analyticsConsoleName.getText();
            click(consoleScheduleMenuElement);
            SimpleUtils.pass("'Schedule' Console Menu Loaded Successfully!");
        } else {
            SimpleUtils.fail("'Schedule' Console Menu Items Not Loaded Successfully!", false);
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
            if (activatedSubTabElement.getText().equalsIgnoreCase(SubTabText)) {
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

        if (isWeekGenerated()) {
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
    }


    @Override
    public void goToProjectedSales() throws Exception {
        checkElementVisibility(goToProjectedSalesTab);
        click(goToProjectedSalesTab);
        SimpleUtils.pass("ProjectedSales Page Loading..!");

        if(isElementLoaded(salesGuidance)){
        	SimpleUtils.pass("Sales Forecast is Displayed on the page");
        }else{
        	SimpleUtils.fail("Sales Forecast not Displayed on the page",true);
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
		/*WebElement scheduleWeekViewButton = MyThreadLocal.getDriver().
			findElement(By.cssSelector("[ng-click=\"selectDayWeekView($event, 'week')\"]"));*/

        WebElement scheduleWeekViewButton = MyThreadLocal.getDriver().
                findElement(By.cssSelector("div.lg-button-group-last"));
        if (isElementLoaded(scheduleWeekViewButton)) {
            if (!scheduleWeekViewButton.getAttribute("class").toString().contains("selected"))//selected
            {
                click(scheduleWeekViewButton);
            }
            SimpleUtils.pass("Schedule page week view loaded successfully!");
        } else {
            SimpleUtils.fail("Schedule Page Week View Button not Loaded Successfully!", true);
        }
    }


    @Override
    public void clickOnDayView() throws Exception {
		/*WebElement scheduleDayViewButton = MyThreadLocal.getDriver().
			findElement(By.cssSelector("[ng-click=\"selectDayWeekView($event, 'day')\"]"));*/
        WebElement scheduleDayViewButton = MyThreadLocal.getDriver().
                findElement(By.cssSelector("div.lg-button-group-first"));

        if (isElementLoaded(scheduleDayViewButton)) {
            if (!scheduleDayViewButton.getAttribute("class").toString().contains("enabled")) {
                click(scheduleDayViewButton);
            }
            SimpleUtils.pass("Schedule Page day view loaded successfully!");
        } else {
            SimpleUtils.fail("Schedule Page Day View Button not Loaded Successfully!", true);
        }
    }


    @FindBy(xpath = "//*[@class='shift-hover-seperator']/following-sibling::div[1]/div[1]")
    private WebElement shiftSize;

    @FindBy(css = "img[ng-if*='hasViolateCompliance']")
    private List<WebElement> infoIcon;


    public float calcTotalScheduledHourForDayInWeekView() {
        float sumOfAllShiftsLength = 0;
        for (int i = 0; i < infoIcon.size(); i++) {
            if (isElementEnabled(infoIcon.get(i))) {
                click(infoIcon.get(i));
                String[] TMShiftSize = shiftSize.getText().split(" ");
                float shiftSizeInHour = Float.valueOf(TMShiftSize[0]);
                sumOfAllShiftsLength = sumOfAllShiftsLength + shiftSizeInHour;

            } else {
                SimpleUtils.fail("Shift not loaded successfully in week view", false);
            }
        }
        return (sumOfAllShiftsLength);

    }


	@Override
	public HashMap<String, Float> getScheduleLabelHoursAndWages() throws Exception {
		HashMap<String, Float> scheduleHoursAndWages = new HashMap<String, Float>();
		WebElement budgetedScheduledLabelsDivElement = MyThreadLocal.getDriver().findElement(By.xpath("//div[@class='card-carousel-card card-carousel-card-primary card-carousel-card-table']"));
		if(isElementEnabled(budgetedScheduledLabelsDivElement))
		{
//			Thread.sleep(2000);
			String scheduleWagesAndHoursCardText = budgetedScheduledLabelsDivElement.getText();
			String[] scheduleWagesAndHours = scheduleWagesAndHoursCardText.split("\n");
			for(String wagesAndHours: scheduleWagesAndHours)
			{
				if(wagesAndHours.toLowerCase().contains(scheduleHoursAndWagesData.hours.getValue().toLowerCase()))
				{
					scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , wagesAndHours.split(" ")[1],
							scheduleHoursAndWagesData.budgetedHours.getValue());
					scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , wagesAndHours.split(" ")[2],
							scheduleHoursAndWagesData.scheduledHours.getValue());
					scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , wagesAndHours.split(" ")[3],
							scheduleHoursAndWagesData.otherHours.getValue());
				}
				else if(wagesAndHours.toLowerCase().contains(scheduleHoursAndWagesData.wages.getValue().toLowerCase()))
				{
					scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , wagesAndHours.split(" ")[1]
							.replace("$", ""), scheduleHoursAndWagesData.budgetedWages.getValue());
					scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , wagesAndHours.split(" ")[2]
							.replace("$", ""), scheduleHoursAndWagesData.scheduledWages.getValue());
					scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , wagesAndHours.split(" ")[3]
							.replace("$", ""), scheduleHoursAndWagesData.otherWages.getValue());
				}
			}
		}
		return scheduleHoursAndWages;
 	}


	private HashMap<String, Float> updateScheduleHoursAndWages(HashMap<String, Float> scheduleHoursAndWages,
			String hours, String hoursAndWagesKey) {
		scheduleHoursAndWages.put(hoursAndWagesKey, Float.valueOf(hours.replaceAll(",","")));
		return scheduleHoursAndWages;
	}

	@Override
	public synchronized List<HashMap<String, Float>> getScheduleLabelHoursAndWagesDataForEveryDayInCurrentWeek() throws Exception {
		List<HashMap<String, Float>> ScheduleLabelHoursAndWagesDataForDays = new ArrayList<HashMap<String, Float>>();
		List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
		if(isScheduleDayViewActive()) {
			if(ScheduleCalendarDayLabels.size() != 0) {
				for(WebElement ScheduleCalendarDayLabel: ScheduleCalendarDayLabels)
				{
					click(ScheduleCalendarDayLabel);
					ScheduleLabelHoursAndWagesDataForDays.add(getScheduleLabelHoursAndWages());
				}
			}
			else {
				SimpleUtils.fail("Schedule Page Day View Calender not Loaded Successfully!", true);
			}
		}
		else {
			SimpleUtils.fail("Schedule Page Day View Button not Active!", true);
		}
		return ScheduleLabelHoursAndWagesDataForDays;
	}


    @Override
    public void clickOnScheduleSubTab(String subTabString) throws Exception {
        if (ScheduleSubTabsElement.size() != 0 && !varifyActivatedSubTab(subTabString)) {
            for (WebElement ScheduleSubTabElement : ScheduleSubTabsElement) {
                if (ScheduleSubTabElement.getText().equalsIgnoreCase(subTabString)) {
                    click(ScheduleSubTabElement);
                    waitForSeconds(3);
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
        List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
        for (int i = 0; i < weekCount; i++) {
            if (ScheduleCalendarDayLabels.size() != 0) {
                currentWeekStartingDay = ScheduleCalendarDayLabels.get(0).getText();
            }

            int displayedWeekCount = ScheduleCalendarDayLabels.size();
            for (WebElement ScheduleCalendarDayLabel : ScheduleCalendarDayLabels) {
                if (ScheduleCalendarDayLabel.getAttribute("class").toString().contains("day-week-picker-period-active")) {
                    if (nextWeekViewOrPreviousWeekView.toLowerCase().contains("next") || nextWeekViewOrPreviousWeekView.toLowerCase().contains("future")) {
                        try {
                            int activeWeekIndex = ScheduleCalendarDayLabels.indexOf(ScheduleCalendarDayLabel);
                            if (activeWeekIndex < (displayedWeekCount - 1)) {
                                click(ScheduleCalendarDayLabels.get(activeWeekIndex + 1));
                            } else {
                                click(calendarNavigationNextWeekArrow);
                                click(ScheduleCalendarDayLabels.get(0));
                            }
                        } catch (Exception e) {
                            SimpleUtils.report("Schedule page Calender Next Week Arrows Not Loaded/Clickable after '" + ScheduleCalendarDayLabel.getText().replace("\n", "") + "'");
                        }
                    } else {
                        try {
                            int activeWeekIndex = ScheduleCalendarDayLabels.indexOf(ScheduleCalendarDayLabel);
                            if (activeWeekIndex > 0) {
                                click(ScheduleCalendarDayLabels.get(activeWeekIndex - 1));
                            } else {
                                click(calendarNavigationPreviousWeekArrow);
                                click(ScheduleCalendarDayLabels.get(displayedWeekCount - 1));
                            }
                        } catch (Exception e) {
                            SimpleUtils.fail("Schedule page Calender Previous Week Arrows Not Loaded/Clickable after '" + ScheduleCalendarDayLabel.getText().replace("\n", "") + "'", true);
                        }
                    }
                    break;
                }
            }
        }
    }


    @Override
    public Boolean isWeekGenerated() throws Exception {
        if (isElementLoaded(generateSheduleButton, 3)) {
            if (generateSheduleButton.isEnabled()) {
                return false;
            }
        }
        SimpleUtils.pass("Week: '" + getActiveWeekText() + "' Already Generated!");
        return true;
    }


    @Override
    public Boolean isWeekPublished() throws Exception {
        if (isElementLoaded(publishSheduleButton,5)) {
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
        } else if (isConsoleMessageError()) {
            return false;
        }
        SimpleUtils.pass("Week: '" + getActiveWeekText() + "' Already Published!");
        return true;

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
        if (isElementLoaded(analyze)) {
            click(analyze);
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
        WebElement scheduleCalendarActiveWeek = MyThreadLocal.getDriver().findElement(By.className("day-week-picker-period-active"));
        try {
            if (isElementLoaded(scheduleCalendarActiveWeek)) {
                scheduleWeekStartDuration = scheduleCalendarActiveWeek.getText().replace("\n", "");
            }
        } catch (Exception e) {
            SimpleUtils.fail("Calender duration bar not loaded successfully", true);
        }
        return scheduleWeekStartDuration;
    }

    public void clickOnEditButton() throws Exception {
        if (isElementEnabled(edit,2)) {
            click(edit);
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

    public void clickOnSuggestedButton() throws Exception {
        if (isElementEnabled(scheduleTypeSystem, 5)) {
            click(scheduleTypeSystem);
            SimpleUtils.pass("legion button is clickable");
        }else {
            SimpleUtils.fail("the schedule is not generated, generated schedule firstly",true);
        }
    }

    @FindBy(xpath = "//*[contains(@class,'day-week-picker-period-active')]/following-sibling::div[1]")
    private WebElement immediateNextToCurrentActiveWeek;

    @FindBy(xpath = "//*[contains(@class,'day-week-picker-period-active')]/preceding-sibling::div[1]")
    private WebElement immediatePastToCurrentActiveWeek;

    public void clickImmediateNextToCurrentActiveWeekInDayPicker() {
        if (isElementEnabled(immediateNextToCurrentActiveWeek, 30)) {
            click(immediateNextToCurrentActiveWeek);
        } else {
            SimpleUtils.report("This is a last week in Day Week picker");
        }
    }

    public void clickImmediatePastToCurrentActiveWeekInDayPicker() {
        if (isElementEnabled(immediatePastToCurrentActiveWeek, 30)) {
            click(immediatePastToCurrentActiveWeek);
        } else {
            SimpleUtils.report("This is a last week in Day Week picker");
        }
    }


    public Boolean isAddNewDayViewShiftButtonLoaded() throws Exception {
        if (isElementLoaded(addNewShiftOnDayViewButton)) {
            SimpleUtils.pass("User is not allowed to add new shift for past week!");
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
        if (isElementLoaded(scheduleGenerateButton,2)) {
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
        String scheduleStatus = "No Published Schedule";
        try {
            List<WebElement> noPublishedSchedule = MyThreadLocal.getDriver().findElements(By.className("holiday-text"));
            if (noPublishedSchedule.size() > 0) {
                if (noPublishedSchedule.get(0).getText().contains(scheduleStatus))
                    return false;
            } else if (isConsoleMessageError()) {
                return false;
            } else if (isElementLoaded(publishSheduleButton))
                return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        SimpleUtils.pass("Schedule is Published for current Week!");
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

    public String getActiveWeekText() throws Exception {
        WebElement activeWeek = MyThreadLocal.getDriver().findElement(By.className("day-week-picker-period-active"));
        if (isElementLoaded(activeWeek))
            return activeWeek.getText().replace("\n", " ");
        return "";
    }

    @Override
    public void validatingRefreshButtononPublishedSchedule() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void isGenerateScheduleButton() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void validatingScheduleRefreshButton() throws Exception {
        // TODO Auto-generated method stub

    }

    // Added by Nora
    @FindBy (css = "span.wm-close-link")
    private WebElement closeButton;

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
	
	//added by Nishant
	
	public void navigateDayViewToPast(String PreviousWeekView, int dayCount)
	{
		String currentWeekStartingDay = "NA";
		List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
		for(int i = 0; i < dayCount; i++)
		{
		
			try {
				click(ScheduleCalendarDayLabels.get(i));
				clickOnEditButton();
				clickOnCancelButtonOnEditMode();
				} catch (Exception e) {
					SimpleUtils.fail("Schedule page Calender Previous Week Arrows Not Loaded/Clickable", true);
				}
				
			
		}
	}
	
	
	//added by Nishant
	
	
	public String clickNewDayViewShiftButtonLoaded() throws Exception
	{
		String textStartDay = null;
		if(isElementEnabled(addNewShiftOnDayViewButton,5))
		{
			SimpleUtils.pass("User is allowed to add new shift for current or future week!");
			if(isElementEnabled(shiftStartday)){
				String[] txtStartDay = shiftStartday.getText().split(" ");
				textStartDay = txtStartDay[0];
			}else{
				SimpleUtils.fail("Shift Start day not getting Loaded!",true);
			}
			click(addNewShiftOnDayViewButton);
		}
		else
		{
			SimpleUtils.fail("User is not allowed to add new shift for current or future week!",true);
		}
		return textStartDay;
	}
	
	public void customizeNewShiftPage() throws Exception
	{
		if(isElementLoaded(customizeNewShift,10))
		{
			SimpleUtils.pass("Customize New Shift Page loaded Successfully!");
		}
		else
		{
			SimpleUtils.fail("Customize New Shift Page not loaded Successfully!",false);
		}
	}
	
	
	public void compareCustomizeStartDay(String textStartDay) throws Exception
	{
		if(isElementLoaded(customizeShiftStartdayLabel,10))
		{
			String[] actualTextStartDay = customizeShiftStartdayLabel.getText().split(":");
			if(actualTextStartDay[0].equals(textStartDay)){
				SimpleUtils.pass("Start time on Custimize New shift page "+actualTextStartDay[0]+" is same as "+textStartDay);
			}else{
				SimpleUtils.fail("Start time on Custimize New shift page "+actualTextStartDay[0]+" is not same as "+textStartDay,true);
			}
		}else
		{
			SimpleUtils.fail("Customize New Shift Page not loaded Successfully!",false);
		}
			
	}
	
	
	public void clickOnStartDay(String textStartDay) throws Exception
	{
		if(isElementLoaded(customizeShiftStartdayLabel))
		{
			String[] actualTextStartDay = customizeShiftStartdayLabel.getText().split(":");
			if(actualTextStartDay[0].equals(textStartDay)){
				SimpleUtils.pass("Start time on Customize New shift page "+actualTextStartDay[0]+" is same as "+textStartDay);
			}else{
				SimpleUtils.fail("Start time on Customize New shift page "+actualTextStartDay[0]+" is not same as "+textStartDay,true);
			}
		}else
		{
			SimpleUtils.fail("Customize New Shift Page not loaded Successfully!",false);
		}
			
	}
	
	
	public void moveSliderAtSomePoint(String shiftTime, int shiftStartingCount, String startingPoint) throws Exception
	{
		if(startingPoint.equalsIgnoreCase("End")){
			if(isElementLoaded(sliderNotchEnd,10) && sliderDroppableCount.size()>0){
				SimpleUtils.pass("Shift timings with Sliders loaded on page Successfully for End Point");
				for(int i= shiftStartingCount; i<= sliderDroppableCount.size();i++){
					if(i == (shiftStartingCount + Integer.parseInt(shiftTime))){
						WebElement element = getDriver().findElement(By.cssSelector("div.lgn-time-slider-notch.droppable:nth-child("+(i+Integer.parseInt(shiftTime))+")"));
						mouseHoverDragandDrop(sliderNotchEnd,element);
						MyThreadLocal.setScheduleHoursEndTime(customizeShiftEnddayLabel.getText());
						break;
					}
				}
				
			}else{
				SimpleUtils.fail("Shift timings with Sliders not loading on page Successfully", false);
			}
		}else if(startingPoint.equalsIgnoreCase("Start")){
			if(isElementLoaded(sliderNotchStart,10) && sliderDroppableCount.size()>0){
				SimpleUtils.pass("Shift timings with Sliders loaded on page Successfully for Starting point");
				for(int i= shiftStartingCount; i<= sliderDroppableCount.size();i++){
					if(i == (shiftStartingCount + Integer.parseInt(shiftTime))){
						WebElement element = getDriver().findElement(By.cssSelector("div.lgn-time-slider-notch.droppable:nth-child("+(i+Integer.parseInt(shiftTime))+")"));
						mouseHoverDragandDrop(sliderNotchStart,element);
						MyThreadLocal.setScheduleHoursStartTime(customizeShiftStartdayLabel.getText());
						break;
					}
				}
				
			}else{
				SimpleUtils.fail("Shift timings with Sliders not loading on page Successfully", false);
			}
		}		
	}
	
	public HashMap<String, String> calculateHourDifference() throws Exception {
		Float scheduleHoursDifference = 0.0f;
		HashMap<String, String> shiftTimeSchedule = new HashMap<String, String>();
		if(isElementLoaded(sliderNotchStart,10) && sliderDroppableCount.size()>0){
			String scheduledHoursStartTime = MyThreadLocal.getScheduleHoursStartTime();
			String scheduledHoursEndTime = MyThreadLocal.getScheduleHoursEndTime();
			scheduleHoursDifference =  SimpleUtils.convertDateIntotTwentyFourHrFormat(scheduledHoursStartTime , scheduledHoursEndTime);
			String[] customizeStartTimeLabel = customizeShiftStartdayLabel.getText().split(":");
			String[] customizeEndTimeLabel = customizeShiftEnddayLabel.getText().split(":");
			SimpleUtils.pass("Schedule hour difference is "+scheduleHoursDifference);
			shiftTimeSchedule.put("ScheduleHrDifference",Float.toString(scheduleHoursDifference));
			shiftTimeSchedule.put("CustomizeStartTimeLabel",customizeStartTimeLabel[0]);
			shiftTimeSchedule.put("CustomizeEndTimeLabel",customizeEndTimeLabel[0]);
		}
		return shiftTimeSchedule;
	}

    public void selectWorkRole(String workRoles) throws Exception {
        if (isElementLoaded(btnWorkRole, 10)) {
            click(btnWorkRole);
            SimpleUtils.pass("Work Role button clicked Successfully");
        } else {
            SimpleUtils.fail("Work Role button is not clickable", false);
        }
        if (listWorkRoles.size() > 0) {
            for (WebElement listWorkRole : listWorkRoles) {
                if (listWorkRole.getText().equalsIgnoreCase(workRoles)) {
                    click(listWorkRole);
                    SimpleUtils.pass("Work Role " + workRoles + "selected Successfully");
                    break;
                } else {
                    SimpleUtils.report("Work Role " + workRoles + " not selected");
                }
            }

        } else {
            SimpleUtils.fail("Work Roles size are empty", false);
        }

    }

    public void clickRadioBtnStaffingOption(String staffingOption) throws Exception {
        boolean flag = false;
        int index = -1;
        if (radioBtnStaffingOptions.size() != 0 && radioBtnShiftTexts.size() != 0 &&
                radioBtnStaffingOptions.size() == radioBtnShiftTexts.size()) {

            for (WebElement radioBtnShiftText : radioBtnShiftTexts) {
                index = index + 1;
                if (radioBtnShiftText.getText().contains(staffingOption)) {
                    click(radioBtnStaffingOptions.get(index));
                    SimpleUtils.pass(radioBtnShiftText.getText() + "Radio Button clicked Successfully!");
                    flag = true;
                    break;
                }
            }

            if (flag == false) {
                SimpleUtils.fail("No Radio Button Selected!", false);
            }

        } else {
            SimpleUtils.fail("Staffing option Radio Button is not clickable!", false);
        }
    }

    public void clickOnCreateOrNextBtn() throws Exception {
        if (isElementEnabled(btnSave)) {
            click(btnSave);
            SimpleUtils.pass("Create or Next Button clicked Successfully on Customize new Shift page!");
        } else {
            SimpleUtils.fail("Create or Next Button not clicked Successfully on Customize new Shift page!", false);
        }
    }

    public HashMap<List<String>, List<String>> calculateTeamCount() throws Exception {
        HashMap<List<String>, List<String>> gridDayHourTeamCount = new HashMap<List<String>, List<String>>();
        List<String> gridDayCount = new ArrayList<String>();
        List<String> gridTeamCount = new ArrayList<String>();
        if (gridHeaderDayHour.size() != 0 && gridHeaderTeamCount.size() != 0 &&
                gridHeaderDayHour.size() == gridHeaderTeamCount.size()) {
            for (int i = 0; i < gridHeaderDayHour.size(); i++) {
                gridDayCount.add(gridHeaderDayHour.get(i).getText());
                gridTeamCount.add(gridHeaderTeamCount.get(i).getText());
                SimpleUtils.pass("Day Hour is " + gridHeaderDayHour.get(i).getText() + " And Team Count is " + gridHeaderTeamCount.get(i).getText());
            }
        } else {
            SimpleUtils.fail("Team Count and Day hour does not match", false);
        }
        gridDayHourTeamCount.put(gridDayCount, gridTeamCount);
        return gridDayHourTeamCount;
    }

    public List<String> calculatePreviousTeamCount(
            HashMap<String, String> shiftTimeSchedule, HashMap<List<String>, List<String>>
            gridDayHourPrevTeamCount) throws Exception {
        int count = 0;
        waitForSeconds(3);
        List<String> gridDayHourTeamCount = new ArrayList<String>();
        exit:
        for (Map.Entry<List<String>, List<String>> entry : gridDayHourPrevTeamCount.entrySet()) {
            List<String> previousKeys = entry.getKey();
            List<String> val = entry.getValue();
            for (String previousKey : previousKeys) {
                String[] arrayPreviousKey = previousKey.split(" ");
                count = count + 1;
                if (shiftTimeSchedule.get("CustomizeStartTimeLabel").equals(arrayPreviousKey[0])) {
                    for (int j = 0; j < Float.parseFloat(shiftTimeSchedule.get("ScheduleHrDifference")); j++) {
                        count = count + 1;
                        gridDayHourTeamCount.add(val.get(count - 2));
                    }
                    break exit;
                }
            }
        }
        return gridDayHourTeamCount;
    }

    public List<String> calculateCurrentTeamCount(HashMap<String, String> shiftTiming) throws Exception {
        int count = 0;
        waitForSeconds(3);
        HashMap<List<String>, List<String>> gridDayHourCurrentTeamCount = calculateTeamCount();
        List<String> gridDayHourTeamCount = new ArrayList<String>();
        exit:
        for (Map.Entry<List<String>, List<String>> entry : gridDayHourCurrentTeamCount.entrySet()) {
            List<String> previousKeys = entry.getKey();
            List<String> val = entry.getValue();
            for (String previousKey : previousKeys) {
                String[] arrayPreviousKey = previousKey.split(" ");
                count = count + 1;
                if (shiftTiming.get("CustomizeStartTimeLabel").equals(arrayPreviousKey[0])) {
                    for (int j = 0; j < Float.parseFloat(shiftTiming.get("ScheduleHrDifference")); j++) {
                        count = count + 1;
                        gridDayHourTeamCount.add(val.get(count - 2));
                    }
                    break exit;
                }
            }
        }
        return gridDayHourTeamCount;
    }

    public void clickSaveBtn() throws Exception {
        if (isElementLoaded(scheduleSaveBtn)) {
            click(scheduleSaveBtn);
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

    public HashMap<String, ArrayList<WebElement>> getAvailableFilters() {
        HashMap<String, ArrayList<WebElement>> scheduleFilters = new HashMap<String, ArrayList<WebElement>>();
        try {
            if (isElementLoaded(filterButton,5)) {
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
            SimpleUtils.fail("Filters button not loaded successfully on Schedule page!", false);
        }
        return scheduleFilters;
    }

    public void filterScheduleByWorkRoleAndShiftType(boolean isWeekView) {
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

    public void filterScheduleByShiftTypeWeekView(ArrayList<WebElement> shiftTypeFilters) {
        //String shiftType = "";
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
                HashMap<String, Float> hoursAndWagesCardData = getScheduleLabelHoursAndWages();
                for (Entry<String, Float> hoursAndWages : hoursAndWagesCardData.entrySet()) {
                    if (cardHoursAndWagesText != "")
                        cardHoursAndWagesText = cardHoursAndWagesText + ", " + hoursAndWages.getKey() + ": '" + hoursAndWages.getValue() + "'";
                    else
                        cardHoursAndWagesText = hoursAndWages.getKey() + ": '" + hoursAndWages.getValue() + "'";
                }
                SimpleUtils.report("Active Week Card's Data: " + cardHoursAndWagesText);
                getHoursAndTeamMembersForEachDaysOfWeek();
                SimpleUtils.assertOnFail("Sum of Daily Schedule Hours not equal to Active Week Schedule Hours!", verifyActiveWeekDailyScheduleHoursInWeekView(), true);

                if (!getActiveGroupByFilter().toLowerCase().contains(scheduleGroupByFilterOptions.groupbyTM.getValue().toLowerCase())
                        && !shiftType.toLowerCase().contains("open"))
                    verifyActiveWeekTeamMembersCountAvailableShiftCount();
            } catch (Exception e) {
                SimpleUtils.fail("Unable to get Card data for active week!", true);
            }
        }
    }


    public void filterScheduleByShiftTypeDayView(ArrayList<WebElement> shiftTypeFilters) {
        for (WebElement shiftTypeFilter : shiftTypeFilters) {
            try {
                if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
                    click(filterButton);
                unCheckFilters(shiftTypeFilters);
                SimpleUtils.report("Data for Shift Type: '" + shiftTypeFilter.getText() + "'");
                click(shiftTypeFilter);
                click(filterButton);
                String cardHoursAndWagesText = "";
                HashMap<String, Float> hoursAndWagesCardData = getScheduleLabelHoursAndWages();
                for (Entry<String, Float> hoursAndWages : hoursAndWagesCardData.entrySet()) {
                    if (cardHoursAndWagesText != "")
                        cardHoursAndWagesText = cardHoursAndWagesText + ", " + hoursAndWages.getKey() + ": '" + hoursAndWages.getValue() + "'";
                    else
                        cardHoursAndWagesText = hoursAndWages.getKey() + ": '" + hoursAndWages.getValue() + "'";
                }
                SimpleUtils.report("Active Week Card's Data: " + cardHoursAndWagesText);
                String timeDurationText = "";
                for (String timeDuration : getScheduleDayViewGridTimeDuration()) {
                    if (timeDurationText == "")
                        timeDurationText = timeDuration;
                    else
                        timeDurationText = timeDurationText + " | " + timeDuration;
                }
                SimpleUtils.report("Schedule Day View Shift Duration: " + timeDurationText);

                String budgetedTeamMembersCount = "";
                for (String budgetedTeamMembers : getScheduleDayViewBudgetedTeamMembersCount()) {
                    if (budgetedTeamMembersCount == "")
                        budgetedTeamMembersCount = budgetedTeamMembers;
                    else
                        budgetedTeamMembersCount = budgetedTeamMembersCount + " | " + budgetedTeamMembers;
                }
                SimpleUtils.report("Schedule Day View budgeted Team Members count: " + budgetedTeamMembersCount);

                String scheduleTeamMembersCount = "";
                for (String scheduleTeamMembers : getScheduleDayViewScheduleTeamMembersCount()) {
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


    public ArrayList<String> getScheduleDayViewGridTimeDuration() {
        ArrayList<String> gridTimeDurations = new ArrayList<String>();
        if (dayViewShiftsTimeDuration.size() != 0) {
            for (WebElement timeDuration : dayViewShiftsTimeDuration) {
                gridTimeDurations.add(timeDuration.getText());
            }
        }

        return gridTimeDurations;
    }


    public ArrayList<String> getScheduleDayViewBudgetedTeamMembersCount() {
        ArrayList<String> BudgetedTMsCount = new ArrayList<String>();
        if (dayViewbudgetedTMCount.size() != 0) {
            for (WebElement BudgetedTMs : dayViewbudgetedTMCount) {
                BudgetedTMsCount.add(BudgetedTMs.getText());
            }
        }

        return BudgetedTMsCount;
    }

    public ArrayList<String> getScheduleDayViewScheduleTeamMembersCount() {
        ArrayList<String> BudgetedTMsCount = new ArrayList<String>();
        if (dayViewScheduleTMsCount.size() != 0) {
            for (WebElement scheduleTMs : dayViewScheduleTMsCount) {
                BudgetedTMsCount.add(scheduleTMs.getText());
            }
        }

        return BudgetedTMsCount;
    }


    public void unCheckFilters(ArrayList<WebElement> filterElements) {
        if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
            click(filterButton);
        waitForSeconds(2);
        for (WebElement filterElement : filterElements) {
            WebElement filterCheckBox = filterElement.findElement(By.cssSelector("input[type=\"checkbox\"]"));
            String elementClasses = filterCheckBox.getAttribute("class").toLowerCase();
            if (elementClasses.contains("ng-not-empty"))
                click(filterElement);

        }
    }

    public void getHoursAndTeamMembersForEachDaysOfWeek() {
        String weekDaysAndDatesText = "";
        String weekDaysHoursAndTMsCount = "";
        try {
            if (weekViewDaysAndDates.size() != 0) {
                for (WebElement weekViewDayAndDate : weekViewDaysAndDates) {
                    if (weekDaysAndDatesText != "")
                        weekDaysAndDatesText = weekDaysAndDatesText + " | " + weekViewDayAndDate.getText();
                    else
                        weekDaysAndDatesText = weekViewDayAndDate.getText();
                }
                SimpleUtils.report("Active Week Days And Dates: " + weekDaysAndDatesText);
            }
            if (weekDaySummeryHoursAndTeamMembers.size() != 0) {
                for (WebElement weekDayHoursAndTMs : weekDaySummeryHoursAndTeamMembers) {
                    if (weekDaysHoursAndTMsCount != "")
                        weekDaysHoursAndTMsCount = weekDaysHoursAndTMsCount + " | " + weekDayHoursAndTMs.getText();
                    else
                        weekDaysHoursAndTMsCount = weekDayHoursAndTMs.getText();
                }
                SimpleUtils.report("Active Week Hours And TeamMembers: " + weekDaysHoursAndTMsCount);
            }
        } catch (Exception e) {
            SimpleUtils.fail("Unable to get Hours & Team Members for active Week!", true);
        }
    }

    public boolean verifyActiveWeekDailyScheduleHoursInWeekView() {
        Float weekDaysScheduleHours = (float) 0;
        Float activeWeekScheduleHoursOnCard = (float) 0;
        try {
            activeWeekScheduleHoursOnCard = getScheduleLabelHoursAndWages().get(scheduleHoursAndWagesData.scheduledHours.getValue());
            if (weekDaySummeryHoursAndTeamMembers.size() != 0) {
                for (WebElement weekDayHoursAndTMs : weekDaySummeryHoursAndTeamMembers) {
                    float dayScheduleHours = Float.parseFloat(weekDayHoursAndTMs.getText().split("HRs")[0]);
                    weekDaysScheduleHours = (float) (weekDaysScheduleHours + Math.round(dayScheduleHours * 10.0) / 10.0);
                }
            }
            float totalShiftSizeForWeek = calcTotalScheduledHourForDayInWeekView();
//            System.out.println("sum" + totalShiftSizeForWeek);
            if (totalShiftSizeForWeek == activeWeekScheduleHoursOnCard) {
                SimpleUtils.pass("Sum of all the shifts in a week equal to Week Schedule Hours!('" + totalShiftSizeForWeek + "/" + activeWeekScheduleHoursOnCard + "')");
                return true;
            } else {
                SimpleUtils.fail("Sum of all the shifts in an week is not equal to Week scheduled Hour!('" + totalShiftSizeForWeek + "/" + activeWeekScheduleHoursOnCard + "')", false);
            }
//            if(weekDaysScheduleHours.equals(activeWeekScheduleHoursOnCard))
//            {
//                SimpleUtils.pass("Sum of Daily Schedule Hours equal to Week Schedule Hours! ('"+weekDaysScheduleHours+ "/"+activeWeekScheduleHoursOnCard+"')");
//                return true;
//            }
        } catch (Exception e) {
            SimpleUtils.fail("Unable to Verify Daily Schedule Hours with Week Schedule Hours!", true);
        }
        return false;
    }

    public boolean verifyActiveWeekTeamMembersCountAvailableShiftCount() {
        int weekDaysTMsCount = 0;
        int weekDaysShiftsCount = 0;
        try {
            if (weekDaySummeryHoursAndTeamMembers.size() != 0) {
                for (WebElement weekDayHoursAndTMs : weekDaySummeryHoursAndTeamMembers) {
                    String TeamMembersCount = weekDayHoursAndTMs.getText().split("HRs")[1].replace("TMs", "").trim();
                    weekDaysTMsCount = weekDaysTMsCount + Integer.parseInt(TeamMembersCount);
                }
            }


            if (shiftsOnScheduleView.size() != 0) {
                for (WebElement shiftOnScheduleView : shiftsOnScheduleView) {
                    if (shiftOnScheduleView.getText().trim().length() > 0 && shiftOnScheduleView.isDisplayed()) {
                        weekDaysShiftsCount = weekDaysShiftsCount + 1;
                    }
                }
            }

            if (weekDaysTMsCount == weekDaysShiftsCount) {
                SimpleUtils.pass("Sum of Daily Team Members Count equal to Sum of Daily Shifts Count! ('" + weekDaysTMsCount + "/" + weekDaysShiftsCount + "')");
                return true;
            } else {
                SimpleUtils.fail("Sum of Daily Team Members Count not equal to Sum of Daily Shifts Count! ('" + weekDaysTMsCount + "/" + weekDaysShiftsCount + "')", true);
            }
        } catch (Exception e) {
            SimpleUtils.fail("Unable to Verify Daily Team Members Count with Daily Shifts Count!", true);
        }
        return false;
    }

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

    public ArrayList<WebElement> getAllAvailableShiftsInWeekView() {
        ArrayList<WebElement> avalableShifts = new ArrayList<WebElement>();
        if (shiftsOnScheduleView.size() != 0) {
            for (WebElement shiftOnScheduleView : shiftsOnScheduleView) {
                if (shiftOnScheduleView.getText().trim().length() > 0 && shiftOnScheduleView.isDisplayed()) {
                    avalableShifts.add(shiftOnScheduleView);
                }
            }
        }
        return avalableShifts;
    }

    public ArrayList<HashMap<String, String>> getHoursAndShiftsCountForEachWorkRolesInWeekView() throws Exception {
        String workRoleFilterKey = "workrole";
        ArrayList<HashMap<String, String>> eachWorkRolesData = new ArrayList<HashMap<String, String>>();
        HashMap<String, ArrayList<WebElement>> availableFilters = getAvailableFilters();
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
                for (Entry<String, Float> e : getScheduleLabelHoursAndWages().entrySet())
                    workRole.put(e.getKey(), e.getValue().toString());
                // Adding Shifts Count
                workRole.put("shiftsCount", "" + getAllAvailableShiftsInWeekView().size());

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


    public ArrayList<Float> getAllVesionLabels() throws Exception {
        ArrayList<Float> allVersions = new ArrayList<Float>();
        if (isElementLoaded(analyze)) {
            click(analyze);
            if (versionHistoryLabels.size() != 0) {
                for (WebElement eachVersionLabel : versionHistoryLabels) {
                    String LabelsText = eachVersionLabel.getText().split("\\(Created")[0];
                    allVersions.add(Float.valueOf(LabelsText.split("Version")[1].trim()));
                }
            }
            closeAnalyticPopup();
        }


        return allVersions;
    }

    public void closeAnalyticPopup() throws Exception {
        if (isElementLoaded(dismissanAlyzeButton)) {
            click(dismissanAlyzeButton);
        }
    }


    @Override
    public void publishActiveSchedule() throws Exception {
        if (!isCurrentScheduleWeekPublished()) {
            if (isConsoleMessageError())
                SimpleUtils.fail("Schedule Can not be publish because of Action Require for week: '" + getActiveWeekText() + "'", false);
            else {
                click(publishSheduleButton);
                if (isElementLoaded(publishConfirmBtn)) {
                    click(publishConfirmBtn);
                    SimpleUtils.pass("Schedule published successfully for week: '" + getActiveWeekText() + "'");
                    // It will pop up a window: Welcome to Legion!
                    if (isElementLoaded(closeButton, 5)) {
                        click(closeButton);
                    }
                    if (isElementLoaded(successfulPublishOkBtn)) {
                        click(successfulPublishOkBtn);
                    }
                    if (isElementLoaded(publishSheduleButton, 5)) {
                        // Wait for the Publish button to disappear.
                        waitForSeconds(10);
                    }
                }
            }

        }
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


    @Override
    public boolean inActiveWeekDayClosed(int dayIndex) throws Exception {
        if (isWeekGenerated()) {
            navigateDayViewWithIndex(dayIndex);
            if (isElementLoaded(holidayLogoContainer))
                return true;
        } else {
            if (guidanceWeekOperatingHours.size() != 0) {
                if (guidanceWeekOperatingHours.get(dayIndex).getText().contains("Closed"))
                    return true;
            }
        }
        return false;

    }

    @Override
    public void navigateDayViewWithIndex(int dayIndex) {
        if (dayIndex < 7 && dayIndex >= 0) {
            try {
                clickOnDayView();
                List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
                if (ScheduleCalendarDayLabels.size() == 7) {
                    click(ScheduleCalendarDayLabels.get(dayIndex));
                }
            } catch (Exception e) {
                SimpleUtils.fail("Unable to navigate to in Day View", false);
            }
        } else {
            SimpleUtils.fail("Invalid dayIndex value to verify Store is Closed for the day", false);
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


    @Override
    public boolean isActiveWeekHasOneDayClose() throws Exception {
        for (int index = 0; index < dayCount.Seven.getValue(); index++) {
            if (inActiveWeekDayClosed(index))
                return true;
        }
        return false;
    }

    @Override
    public boolean isActiveWeekAssignedToCurrentUser(String userName) throws Exception {
        clickOnWeekView();
        if (shiftsOnScheduleView.size() != 0) {
            for (WebElement shiftOnScheduleView : shiftsOnScheduleView) {
                if (shiftOnScheduleView.getText().trim().length() > 0 && shiftOnScheduleView.isDisplayed()
                        && shiftOnScheduleView.getText().toLowerCase().contains(userName.toLowerCase())) {
                    SimpleUtils.pass("Active Week/Day: '" + getActiveWeekText() + "' assigned to '" + userName + "'.");
                    return true;
                }
            }
        }
        SimpleUtils.report("Active Week/Day: '" + getActiveWeekText() + "' not assigned to '" + userName + "'.");
        return false;
    }


    @Override
    public boolean isScheduleGroupByWorkRole(String workRoleOption) throws Exception {
        if (getActiveGroupByFilter().equalsIgnoreCase(workRoleOption)) {
            String filterType = "workrole";
            ArrayList<WebElement> availableWorkRoleFilters = getAvailableFilters().get(filterType);
            if (availableWorkRoleFilters.size() == scheduleShiftHeaders.size()) {
                return true;
            }

        }
        return false;
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
            if (availableWorkRoleFilter.getText().equalsIgnoreCase(workRoleLabel)) {
                click(availableWorkRoleFilter);
                SimpleUtils.pass("Schedule Work Role:'" + availableWorkRoleFilter.getText() + "' Filter selected Successfully!");
            }
        }
    }

    @Override
    public ArrayList<String> getSelectedWorkRoleOnSchedule() throws Exception {
        ArrayList<String> selectedScheduleTabWorkRoles = new ArrayList<String>();
        String filterType = "workrole";
        ArrayList<WebElement> availableWorkRoleFilters = getAvailableFilters().get(filterType);
        if (availableWorkRoleFilters.size() > 0) {
            for (WebElement filterElement : availableWorkRoleFilters) {
                WebElement filterCheckBox = filterElement.findElement(By.cssSelector("input[type=\"checkbox\"]"));
                String elementClasses = filterCheckBox.getAttribute("class").toLowerCase();
                if (elementClasses.contains("ng-not-empty")) {
                    selectedScheduleTabWorkRoles.add(filterElement.getText());
                    SimpleUtils.report("Selected Work Role: '" + filterElement.getText() + "'");
                }
            }
            if (!filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
                click(filterButton);
        }
        return selectedScheduleTabWorkRoles;
    }


    @Override
    public boolean isRequiredActionUnAssignedShiftForActiveWeek() throws Exception {
        String unAssignedShiftRequireActionText = "unassigned shift";
        if (isElementLoaded(requiredActionCard)) {
            if (requiredActionCard.getText().toLowerCase().contains(unAssignedShiftRequireActionText)) {
                SimpleUtils.report("Required Action for Unassigned Shift found for the week: '" + getActiveWeekText() + "'");
                return true;
            }
        }
        return false;
    }


    public void clickOnRefreshButton() throws Exception {
        if (isElementLoaded(refresh))
            click(refresh);
        if (isElementLoaded(confirmRefreshButton))
            click(confirmRefreshButton);
        if (isElementLoaded(okRefreshButton)) {
            click(okRefreshButton);
            SimpleUtils.pass("Active Week: '" + getActiveWeekText() + "' refreshed successfully!");
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


    public List<WebElement> getAvailableShiftsInDayView() {
        return dayViewAvailableShifts;
    }

    public void dragShiftToRightSide(WebElement shift, int xOffSet) {
        WebElement shiftRightSlider = shift.findElement(By.cssSelector("div.sch-day-view-shift-pinch.right"));
        moveDayViewCards(shiftRightSlider, xOffSet);
    }

    public void moveDayViewCards(WebElement webElement, int xOffSet) {
        Actions builder = new Actions(MyThreadLocal.getDriver());
        builder.moveToElement(webElement)
                .clickAndHold()
                .moveByOffset(xOffSet, 0)
                .release()
                .build()
                .perform();
    }


    public boolean isSmartCardAvailableByLabel(String cardLabel) throws Exception {
        if (carouselCards.size() != 0) {
            for (WebElement carouselCard : carouselCards) {
                smartCardScrolleToLeft();
                if (carouselCard.isDisplayed() && carouselCard.getText().toLowerCase().contains(cardLabel.toLowerCase())
                        && isSmartcardContainText(carouselCard))
                    return true;
                else if (!carouselCard.isDisplayed()) {
                    while (isSmartCardScrolledToRightActive() == true) {
                        if (carouselCard.isDisplayed() && carouselCard.getText().toLowerCase().contains(cardLabel.toLowerCase())
                                && isSmartcardContainText(carouselCard))
                            return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isSmartcardContainText(WebElement smartcardElement) throws Exception {
        if (smartcardElement.getText().trim().length() > 0) {
            return true;
        } else {
            SimpleUtils.fail("Schedule Page: Smartcard contains No Text or Spinning Icon on duration '" + getActiveWeekText() + "'.", true);
            return false;
        }
    }


    boolean isSmartCardScrolledToRightActive() throws Exception {
        if (isElementLoaded(smartcardArrowRight, 10) && smartcardArrowRight.getAttribute("class").contains("available")) {
            click(smartcardArrowRight);
            return true;
        }
        return false;
    }

    void smartCardScrolleToLeft() throws Exception {
        if (isElementLoaded(smartcardArrowLeft, 2)) {
            while (smartcardArrowLeft.getAttribute("class").contains("available")) {
                click(smartcardArrowLeft);
            }
        }

    }


    //added by Nishant

    @Override
    public HashMap<String, Float> getScheduleLabelHours() throws Exception {
        HashMap<String, Float> scheduleHours = new HashMap<String, Float>();
        WebElement budgetedScheduledLabelsDivElement = MyThreadLocal.getDriver().findElement(By.xpath("//div[@class='card-carousel-card card-carousel-card-primary card-carousel-card-table']"));
        if (isElementLoaded(budgetedScheduledLabelsDivElement)) {
            String scheduleWagesAndHoursCardText = budgetedScheduledLabelsDivElement.getText();
            String[] scheduleWagesAndHours = scheduleWagesAndHoursCardText.split("\n");
            for (String wagesAndHours : scheduleWagesAndHours) {
                if (wagesAndHours.toLowerCase().contains(scheduleHoursAndWagesData.hours.getValue().toLowerCase())) {
                    scheduleHours = updateScheduleHoursAndWages(scheduleHours, wagesAndHours.split(" ")[2],
                            scheduleHoursAndWagesData.scheduledHours.getValue());
                    scheduleHours = updateScheduleHoursAndWages(scheduleHours, wagesAndHours.split(" ")[3],
                            scheduleHoursAndWagesData.otherHours.getValue());
                }

            }
        }
        return scheduleHours;
    }


    public int getgutterSize() {
        int guttercount = 0;
        try {
            guttercount = gutterCount.size();
        } catch (Exception e) {
            SimpleUtils.fail("Gutter element not available on the page", false);
        }
        return guttercount;
    }

    public void verifySelectTeamMembersOption() throws Exception {
//  		waitForSeconds(4);
        if (areListElementVisible(recommendedScrollTable, 5)) {
            if (isElementEnabled(selectRecommendedOption)) {
                String[] txtRecommendedOption = selectRecommendedOption.getText().replaceAll("\\p{P}", "").split(" ");
                if (Integer.parseInt(txtRecommendedOption[2]) == 0) {
                    searchText(propertySearchTeamMember.get("AssignTeamMember"));
                    SimpleUtils.pass(txtRecommendedOption[0] + " Option selected By default for Select Team member option");
                } else {
                    boolean  scheduleBestMatchStatus = getScheduleBestMatchStatus();
                    if(scheduleBestMatchStatus){
                        SimpleUtils.pass(txtRecommendedOption[0] + " Option selected By default for Select Team member option");
                    }else{
                        if(areListElementVisible(btnSearchteamMember,5)){
                            click(btnSearchteamMember.get(1));
                            searchText(propertySearchTeamMember.get("AssignTeamMember"));
                        }

                    }

                }
            } else {
                SimpleUtils.fail("Recommended option not available on page", false);
            }
        } else if (isElementLoaded(textSearch, 5)) {
            searchText(propertySearchTeamMember.get("AssignTeamMember"));
        } else {
            SimpleUtils.fail("Select Team member option and Recommended options are not available on page", false);
        }

    }

    public void searchText(String searchInput) throws Exception {
        String[] searchAssignTeamMember = searchInput.split(",");
        if (isElementLoaded(textSearch, 10) && isElementLoaded(searchIcon, 10)) {
            for (int i = 0; i < searchAssignTeamMember.length; i++) {
                String[] searchTM = searchAssignTeamMember[i].split("\\.");
                textSearch.sendKeys(searchTM[0]);
                click(searchIcon);
                if (getScheduleStatus()) {
                    setTeamMemberName(searchAssignTeamMember[i]);
                    break;
                } else {
                    textSearch.clear();
                }
            }

        } else {
            SimpleUtils.fail("Search text not editable and icon are not clickable", false);
        }

    }

    public boolean getScheduleStatus() throws Exception {
        boolean ScheduleStatus = false;
//		waitForSeconds(5);
		if(areListElementVisible(scheduleSearchTeamMemberStatus,5) || isElementLoaded(scheduleNoAvailableMatchStatus,5)){
			for(int i=0; i<scheduleSearchTeamMemberStatus.size();i++){
				if(scheduleSearchTeamMemberStatus.get(i).getText().contains("Available")
						|| scheduleSearchTeamMemberStatus.get(i).getText().contains("Unknown")){
					click(radionBtnSearchTeamMembers.get(i));
					setWorkerRole(searchWorkerRole.get(i).getText());
					setWorkerLocation(searchWorkerLocation.get(i).getText());
					setWorkerShiftTime(searchWorkerSchShiftTime.getText());
					setWorkerShiftDuration(searchWorkerSchShiftDuration.getText());
					ScheduleStatus = true;
					break;
				}
			}
		}else{
			SimpleUtils.fail("Not able to found Available status in SearchResult", false);
		}

        return ScheduleStatus;
    }

	public boolean getScheduleBestMatchStatus()throws Exception {
		boolean ScheduleBestMatchStatus = false;
		if(areListElementVisible(scheduleStatus,5) || scheduleBestMatchStatus.size()!=0 && radionBtnSelectTeamMembers.size() == scheduleStatus.size() && searchWorkerName.size()!=0){
			for(int i=0; i<scheduleStatus.size();i++){
				if(scheduleBestMatchStatus.get(i).getText().contains("Best")
						|| scheduleStatus.get(i).getText().contains("Unknown") || scheduleStatus.get(i).getText().contains("Available")){
					if(searchWorkerName.get(i).getText().contains("Gordon.M") || searchWorkerName.get(i).getText().contains("Jayne.H")){
						click(radionBtnSelectTeamMembers.get(i));
						setTeamMemberName(searchWorkerName.get(i).getText());
						ScheduleBestMatchStatus = true;
						break;
					}
				}
			}
		}else{
			SimpleUtils.fail("Not able to found Available status in SearchResult", false);
		}

		return ScheduleBestMatchStatus;
	}

	public void getAvailableStatus()throws Exception {
		if(areListElementVisible(scheduleStatus) && availableStatus.size()!=0 && radionBtnSelectTeamMembers.size() == scheduleStatus.size()){
			for(int i=0; i<scheduleStatus.size();i++){
				if(scheduleStatus.get(i).getText().contains(availableStatus.get(i).getText())){
					click(radionBtnSelectTeamMembers.get(i));
					break;
				}
			}
		}else{
			SimpleUtils.fail("Not able to found Available status in SearchResult", false);
		}
	}

	public void clickOnOfferOrAssignBtn() throws Exception{
		if(isElementEnabled(btnOffer,5)){
			click(btnOffer);
		}else{
			SimpleUtils.fail("Offer Or Assign Button is not clickable", false);
		}
	}

	public void clickOnShiftContainer(int index) throws Exception{
		int guttercount = getgutterSize();
		waitForSeconds(2);
		if(shiftContainer.size()!=0 && index<=shiftContainer.size() && isElementLoaded(shiftContainer.get(0))){
			for(int i=0;i<index;i++){
				mouseHover(shiftContainer.get(i));
				waitForSeconds(2);
//				click(shiftContainer.get(i));
				deleteShift();
				waitForSeconds(2);
			}

		}else{
			SimpleUtils.report("Shift container does not any gutter text");
		}
	}


	public void deleteShift(){
		if(shiftDeleteBtn.size()!=0){
			for(int i=0;i<shiftDeleteBtn.size();i++){
				click(shiftDeleteBtn.get(i));
			}
		}else{
			SimpleUtils.fail("Delete button is not available on Shift container",false);
		}
	}


	public void deleteShiftGutterText(){
		if(shiftDeleteGutterText.size()!=0){
			for(int i=0;i<shiftDeleteGutterText.size();i++){
				if(shiftDeleteGutterText.get(i).equals("Delete")){
					SimpleUtils.pass(shiftDeleteGutterText.get(i)+" Option as gutter is present on shift container");
				}

			}
		}else{
			SimpleUtils.fail("Delete text is not present on Shift container gutter",true);
		}
	}

    public void disableNextWeekArrow() throws Exception{
        if(!calendarNavigationNextWeekArrow.isDisplayed()){
            SimpleUtils.pass("Next week arrow is disabled");
        }else{
            SimpleUtils.fail("Next week arrow is not disabled",false);
        }
    }

    @Override
    public void clickScheduleDraftAndGuidanceStatus(
            List<String> overviewScheduleWeeksStatus) {
        // TODO Auto-generated method stub

    }

    public void editBudgetHours(){
        if(budgetEditHours.size()>0){
            for(int i=0; i< budgetEditHours.size();i++){
                System.out.println(budgetEditHours.get(i).getText());
            }
        }
    }

	@Override
	public void navigateScheduleDayWeekView(String nextWeekView, int weekCount) {

	}

	public ArrayList<String> getActiveWeekCalendarDates() throws Exception
    {
        ArrayList<String> scheduleWeekCalendarDates = new ArrayList<String>();
        String catendarWeekDatesAsText = "";
        for(WebElement ScheduleWeekCalendarDate : ScheduleWeekCalendarDates)
        {
            scheduleWeekCalendarDates.add(ScheduleWeekCalendarDate.getText().replace("\n", " "));
            if(catendarWeekDatesAsText == "")
                catendarWeekDatesAsText = ScheduleWeekCalendarDate.getText().replace("\n", " ");
            else
                catendarWeekDatesAsText = catendarWeekDatesAsText+ " | " +ScheduleWeekCalendarDate.getText().replace("\n", " ");
        }
        SimpleUtils.report("Active Week Calendar Dates: '" + catendarWeekDatesAsText + "'");
        return scheduleWeekCalendarDates;
    }


	@Override
	public void refreshBrowserPage() throws Exception {
		MyThreadLocal.getDriver().navigate().refresh();
		Thread.sleep(5000);
		SimpleUtils.pass("Browser Refreshed Successfully for the Week/Day: '"+ getActiveWeekText() +"'!");

    }

    @Override
    public void addOpenShiftWithDefaultTime(String workRole) throws Exception {
        if (isElementLoaded(addNewShiftOnDayViewButton)) {
            click(addNewShiftOnDayViewButton);
            selectWorkRole(workRole);
            clickRadioBtnStaffingOption(staffingOption.OpenShift.getValue());
            clickOnCreateOrNextBtn();
            Thread.sleep(2000);
        } else
            SimpleUtils.fail("Day View Schedule edit mode, add new shift button not found for Week Day: '" +
                    getActiveWeekText() + "'", false);
    }

    @Override
    public boolean isNextWeekAvaibale() throws Exception {
        if (!isElementLoaded(calendarNavigationNextWeekArrow)) {
            List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
            if (ScheduleCalendarDayLabels.get(ScheduleCalendarDayLabels.size() - 1).getAttribute("class").toLowerCase().contains("active"))
                return false;
        }
        return true;
    }

    @Override
    public boolean isSmartCardPanelDisplay() throws Exception {
        if (isElementLoaded(smartCardPanel)) {
            return true;
        }
        return false;
    }

    @Override
    public void convertAllUnAssignedShiftToOpenShift() throws Exception {
        if (varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue())) {
            clickOnWeekView();
            clickOnEditButton();
            for (WebElement unAssignedShift : getUnAssignedShifts()) {
                convertUnAssignedShiftToOpenShift(unAssignedShift);
            }
            clickSaveBtn();
        } else {
            SimpleUtils.fail("Unable to convert UnAssigned Shift to Open Shift as 'Schedule' tab not active.", false);
        }

	}

    public void convertUnAssignedShiftToOpenShift(WebElement unAssignedShift) throws Exception {
        By isUnAssignedShift = By.cssSelector("[ng-if=\"isUnassignedShift()\"]");
        WebElement unAssignedPlusBtn = unAssignedShift.findElement(isUnAssignedShift);
        if (isElementLoaded(unAssignedPlusBtn)) {
            click(unAssignedPlusBtn);
            if (isElementLoaded(shiftPopover)) {
                WebElement convertToOpenOption = shiftPopover.findElement(By.cssSelector("[ng-if=\"canConvertToOpenShift()\"]"));
                if (isElementLoaded(convertToOpenOption)) {
                    click(convertToOpenOption);
                    if (isElementLoaded(convertToOpenYesBtn)) {
                        click(convertToOpenYesBtn);
                        Thread.sleep(2000);
                    }
                }
            }
        }
    }

    private List<WebElement> getUnAssignedShifts() {
        String unAssignedShiftsLabel = "unassigned";
        List<WebElement> unAssignedShiftsObj = new ArrayList<WebElement>();
        if (shiftsOnScheduleView.size() != 0) {
            for (WebElement shift : shiftsOnScheduleView) {
                if (shift.getText().toLowerCase().contains(unAssignedShiftsLabel) && shift.isDisplayed())
                    unAssignedShiftsObj.add(shift);
            }
        }
        return unAssignedShiftsObj;
    }

    @Override
    public void reduceOvertimeHoursOfActiveWeekShifts() throws Exception {
        List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
        for (WebElement activeWeekDay : ScheduleCalendarDayLabels) {
            click(activeWeekDay);
            List<WebElement> availableDayShifts = getAvailableShiftsInDayView();
            if (availableDayShifts.size() != 0) {
                clickOnEditButton();
                for (WebElement shiftWithOT : getAvailableShiftsInDayView()) {
                    WebElement shiftRightSlider = shiftWithOT.findElement(By.cssSelector("div.sch-day-view-shift-pinch.right"));
                    String OTString = "hrs ot";
                    int xOffSet = -50;
                    while (shiftWithOT.getText().toLowerCase().contains(OTString)) {
                        moveDayViewCards(shiftRightSlider, xOffSet);
                    }
                }
                clickSaveBtn();
                break;
            }
        }

    }


    @Override
    public boolean isActionButtonLoaded(String actionBtnText) throws Exception {
        if (actionButtonDiv.size() != 0) {
            if (actionButtonDiv.get(0).getText().toLowerCase().contains(actionBtnText.toLowerCase()))
                return true;
        }
        return false;
    }

    @Override
    public void navigateToNextDayIfStoreClosedForActiveDay() throws Exception {
        String dayType = "Next";
        int dayCount = 1;
        if (isStoreClosedForActiveWeek())
            navigateWeekViewOrDayViewToPastOrFuture(dayType, dayCount);
        if (!isStoreClosedForActiveWeek())
            SimpleUtils.pass("Navigated to Next day successfully!");
    }

    @Override
    public boolean isStoreClosedForActiveWeek() throws Exception {
        if (isElementLoaded(holidayLogoContainer, 10)) {
            SimpleUtils.report("Store is Closed for the Day/Week: '" + getActiveWeekText() + "'.");
            return true;
        }
        return false;
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
    public void validatingGenrateSchedule() throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    public void noBudgetDisplayWhenBudgetNotEntered(String nextWeekView,
                                                    int weekCount) {
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

    public Boolean verifyNoBudgetAvailableForWeek(String valueOfBudgetSmartcardWhenNoBudgetEntered, String weekDuration){
        Boolean budgetAvailable = false;
        if (valueOfBudgetSmartcardWhenNoBudgetEntered.contains(("-- Hours"))) {
            SimpleUtils.pass(weekDuration + " week has no budget entered");
            waitForSeconds(2);
            checkElementVisibility(returnToOverviewTab);
            click(returnToOverviewTab);
            budgetAvailable = true;
        }
        return budgetAvailable;
    }

    public void calculateBudgetValueForScheduleAndBudgetSmartCardWhenBudgetByHour(String weekDuration, String budgetDisplayOnBudgetSmartCardByHours, String budgetOnScheduleSmartcard){
        float totalBudgetedHourForBudgetSmartCard=0.0f;
        float totalBudgetHourforScheduleSmartcardIfBudgetEntered=0.0f;
        for (int j = 1; j < guidanceHour.size(); j++) {
            totalBudgetedHourForBudgetSmartCard = totalBudgetedHourForBudgetSmartCard + Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"));
            if (((Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"))) == 0)) {
                totalBudgetHourforScheduleSmartcardIfBudgetEntered = totalBudgetHourforScheduleSmartcardIfBudgetEntered + Float.parseFloat(guidanceHour.get(j - 1).getText());

            } else {
                totalBudgetHourforScheduleSmartcardIfBudgetEntered = totalBudgetHourforScheduleSmartcardIfBudgetEntered + Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"));
            }
        }
        if (totalBudgetedHourForBudgetSmartCard == (Float.parseFloat(budgetDisplayOnBudgetSmartCardByHours))) {
            SimpleUtils.pass("Budget " + (Float.parseFloat(budgetDisplayOnBudgetSmartCardByHours)) +" for week " +weekDuration + " on budget smartcard matches the budget entered " + totalBudgetedHourForBudgetSmartCard);
        } else {
            SimpleUtils.fail("Budget " + (Float.parseFloat(budgetDisplayOnBudgetSmartCardByHours))  +" for week " +weekDuration + " on budget smartcard doesn't match the budget entered " + totalBudgetedHourForBudgetSmartCard, true);
        }

        float finaltotalScheduledHourIfBudgetEntered = (float) (Math.round(totalBudgetHourforScheduleSmartcardIfBudgetEntered * 10) / 10.0);;
        if (finaltotalScheduledHourIfBudgetEntered == (Float.parseFloat(budgetOnScheduleSmartcard))) {
            SimpleUtils.pass("Budget " + (Float.parseFloat(budgetOnScheduleSmartcard))  +" for week " +weekDuration + " on schedule smartcard matches the budget calculated " + finaltotalScheduledHourIfBudgetEntered);
        } else {
            SimpleUtils.fail("Budget " + (Float.parseFloat(budgetOnScheduleSmartcard))  +" for week " +weekDuration + " on schedule smartcard doesn't match the budget calculated " + finaltotalScheduledHourIfBudgetEntered, true);
        }
        checkElementVisibility(enterBudgetCancelButton);
        click(enterBudgetCancelButton);
        checkElementVisibility(returnToOverviewTab);
        click(returnToOverviewTab);
    }


    public void calculateBudgetValueForScheduleAndBudgetSmartCardWhenBudgetByWages(String weekDuration, String budgetDisplayOnSmartCardWhenByWages,String budgetedWagesOnScheduleSmartcard, String budgetOnScheduleSmartcard, int tolerance){
        float totalBudgetedWagesForBudgetSmartCard=0.0f;
        float totalScheduledHourIfBudgetEntered=0.0f;
        float totalScheduledWagesIfBudgetEntered=0.0f;
        for (int j = 1; j < guidanceHour.size(); j++) {
            totalBudgetedWagesForBudgetSmartCard = totalBudgetedWagesForBudgetSmartCard + Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"));
            if (((Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"))) == 0)) {
                totalScheduledHourIfBudgetEntered = totalScheduledHourIfBudgetEntered + Float.parseFloat(guidanceHour.get(j - 1).getText());
                totalScheduledWagesIfBudgetEntered = totalScheduledWagesIfBudgetEntered + Float.parseFloat(guidanceWages.get(j-1).getText());
            } else {
                totalScheduledHourIfBudgetEntered = totalScheduledHourIfBudgetEntered + Float.parseFloat(budgetHourWhenBudgetByWagesEnabled.get(j - 1).getText());
                totalScheduledWagesIfBudgetEntered = totalScheduledWagesIfBudgetEntered + Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"));
            }
        }
        if (totalBudgetedWagesForBudgetSmartCard == (Float.parseFloat(budgetDisplayOnSmartCardWhenByWages.replaceAll(",","")))) {
            SimpleUtils.pass("Budget " + (Float.parseFloat(budgetDisplayOnSmartCardWhenByWages.replaceAll(",",""))) +" for week " +weekDuration + " on budget smartcard matches the budget entered " + totalBudgetedWagesForBudgetSmartCard);
        } else {
            SimpleUtils.fail("Budget " + (Float.parseFloat(budgetDisplayOnSmartCardWhenByWages))  +" for week " +weekDuration + " on budget smartcard doesn't match the budget entered " + totalBudgetedWagesForBudgetSmartCard, false);
        }

        float finaltotalScheduledHourIfBudgetEntered = (float) (Math.round(totalScheduledHourIfBudgetEntered * 10) / 10.0);
        float differenceBetweenBudInSCnCalcBudgbyHour = (Float.parseFloat(budgetOnScheduleSmartcard)) - finaltotalScheduledHourIfBudgetEntered;
        if (finaltotalScheduledHourIfBudgetEntered == (Float.parseFloat(budgetOnScheduleSmartcard)) ||
                (differenceBetweenBudInSCnCalcBudgbyHour <= Integer.parseInt(propertyBudgetValue.get("Tolerance_Value")))) {
            SimpleUtils.pass("Budget " + (Float.parseFloat(budgetOnScheduleSmartcard))  +" for week " +weekDuration + " on schedule smartcard matches the budget calculated " + finaltotalScheduledHourIfBudgetEntered);
        } else {
            SimpleUtils.fail("Budget " + (Float.parseFloat(budgetOnScheduleSmartcard))  +" for week " +weekDuration + " on schedule smartcard doesn't match the budget calculated " + finaltotalScheduledHourIfBudgetEntered, true);
        }
        int finaltotalScheduledWagesIfBudgetEntered = (int) (Math.round(totalScheduledWagesIfBudgetEntered * 10) / 10.0);
        int differenceBetweenBugInSCnCalcBudg = (Integer.parseInt(budgetedWagesOnScheduleSmartcard)) - finaltotalScheduledWagesIfBudgetEntered;
        if (finaltotalScheduledWagesIfBudgetEntered == (Integer.parseInt(budgetedWagesOnScheduleSmartcard)) || (differenceBetweenBugInSCnCalcBudg <= tolerance)) {
            SimpleUtils.pass("Budgeted Wages " + (Float.parseFloat(budgetedWagesOnScheduleSmartcard))  +" for week " +weekDuration + " on" +
                    " schedule smartcard matches the budget wages calculated " + finaltotalScheduledWagesIfBudgetEntered);
            setBudgetTolerance(1);
        } else {
            SimpleUtils.fail("Budget Wages" + (Float.parseFloat(budgetedWagesOnScheduleSmartcard))  +" for week " +weekDuration + " on schedule smartcard doesn't match the budget wages calculated " + finaltotalScheduledWagesIfBudgetEntered, true);
        }
        checkElementVisibility(enterBudgetCancelButton);
        click(enterBudgetCancelButton);
        checkElementVisibility(returnToOverviewTab);
        click(returnToOverviewTab);
    }


    @Override
    public void budgetInScheduleNBudgetSmartCard(String nextWeekView, int weekCount, int tolerance) {
        // TODO Auto-generated method stub
        waitForSeconds(3);
        for(int i = 0; i < weekCount; i++)
        {
            float totalBudgetedHourForBudgetSmartCard=0.0f;
            float totalBudgetHourforScheduleSmartcardIfBudgetEntered=0.0f;
            float totalBudgetedWagesForBudgetSmartCard=0.0f;
            float totalScheduledWagesIfBudgetEntered=0.0f;
            if(nextWeekView.toLowerCase().contains("next") || nextWeekView.toLowerCase().contains("future"))
            {
                try {
                    if(isElementLoaded(schedulesForWeekOnOverview.get(0))) {
                        waitForSeconds(3);
                        click(schedulesForWeekOnOverview.get(i));
                        waitForSeconds(4);
                        String[] daypickers = daypicker.getText().split("\n");
                        String valueOfBudgetSmartcardWhenNoBudgetEntered = budgetOnbudgetSmartCardWhenNoBudgetEntered.getText();
                        String[] budgetDisplayOnBudgetSmartcard = budgetOnbudgetSmartCard.getText().split(" ");
                        String budgetDisplayOnSmartCardWhenByWages = budgetOnbudgetSmartCard.getText().substring(1);
                        String budgetDisplayOnBudgetSmartCardByHours = budgetDisplayOnBudgetSmartcard[0];
                        String budgetOnScheduleSmartcard = budgetDisplayOnScheduleSmartcard.get(0).getText();
                        String budgetedWagesOnScheduleSmartcard = budgetDisplayOnScheduleSmartcard.get(1).getText().substring(1).replace(",","");
                        String weekDuration = daypickers[1];
                        if (verifyNoBudgetAvailableForWeek(valueOfBudgetSmartcardWhenNoBudgetEntered, weekDuration) == false) {
                            click(enterBudgetLink);
                            waitForSeconds(3);
                            if(areListElementVisible(editBudgetHrs,5)){
                                calculateBudgetValueForScheduleAndBudgetSmartCardWhenBudgetByHour(weekDuration, budgetDisplayOnBudgetSmartCardByHours, budgetOnScheduleSmartcard);
                                }else if(areListElementVisible(editWagesHrs,5)){
                                    calculateBudgetValueForScheduleAndBudgetSmartCardWhenBudgetByWages(weekDuration, budgetDisplayOnSmartCardWhenByWages, budgetedWagesOnScheduleSmartcard, budgetOnScheduleSmartcard, tolerance);
                                }

                        }
                    }
                }
                catch (Exception e) {
                    SimpleUtils.fail("Budget pop-up not opening ",false);
                }
            }
        }
    }

//    @Override
//    public void budgetHourByWagesInScheduleNBudgetedSmartCard(String nextWeekView,
//                                                       int weekCount) {
//        // TODO Auto-generated method stub
//        waitForSeconds(3);
//        for(int i = 0; i < weekCount; i++)
//        {
//            float totalBudgetedWagesForBudgetSmartCard=0.0f;
//            float totalScheduledHourIfBudgetEntered=0.0f;
//            float totalScheduledWagesIfBudgetEntered=0.0f;
//            if(nextWeekView.toLowerCase().contains("next") || nextWeekView.toLowerCase().contains("future"))
//            {
//                try {
//                    if(isElementLoaded(schedulesForWeekOnOverview.get(0))) {
//                        waitForSeconds(3);
//                        click(schedulesForWeekOnOverview.get(i));
//                        waitForSeconds(4);
//                        String[] daypickers = daypicker.getText().split("\n");
//                        String valueOfBudgetSmartcardWhenNoBudgetEntered = budgetOnbudgetSmartCardWhenNoBudgetEntered.getText();
//                        String budgetDisplayOnSmartCard = budgetOnbudgetSmartCard.getText().substring(1);
//                        String budgetOnScheduleSmartcard = budgetDisplayOnScheduleSmartcard.get(0).getText();
//                        String budgetedWagesOnScheduleSmartcard = budgetDisplayOnScheduleSmartcard.get(1).getText().substring(1).replace(",","");
//                        if (valueOfBudgetSmartcardWhenNoBudgetEntered.contains(("-- Hours"))) {
//                            SimpleUtils.pass(daypickers[1] + " week has no budget entered");
//                            waitForSeconds(2);
//                            checkElementVisibility(returnToOverviewTab);
//                            click(returnToOverviewTab);
//                        } else {
//                            click(enterBudgetLink);
//                            waitForSeconds(3);
//                            for (int j = 1; j < guidanceHour.size(); j++) {
//                                totalBudgetedWagesForBudgetSmartCard = totalBudgetedWagesForBudgetSmartCard + Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"));
//                                if (((Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"))) == 0)) {
//                                    totalScheduledHourIfBudgetEntered = totalScheduledHourIfBudgetEntered + Float.parseFloat(guidanceHour.get(j - 1).getText());
//                                    totalScheduledWagesIfBudgetEntered = totalScheduledWagesIfBudgetEntered + Float.parseFloat(guidanceWages.get(j-1).getText());
//                                } else {
//                                    totalScheduledHourIfBudgetEntered = totalScheduledHourIfBudgetEntered + Float.parseFloat(budgetHourWhenBudgetByWagesEnabled.get(j - 1).getText());
//                                    totalScheduledWagesIfBudgetEntered = totalScheduledWagesIfBudgetEntered + Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"));
//                                }
//                            }
//                            if (totalBudgetedWagesForBudgetSmartCard == (Float.parseFloat(budgetDisplayOnSmartCard))) {
//                                SimpleUtils.pass("Budget " + (Float.parseFloat(budgetDisplayOnSmartCard)) +" for week " +daypickers[1] + " on budget smartcard matches the budget entered " + totalBudgetedWagesForBudgetSmartCard);
//                            } else {
//                                SimpleUtils.fail("Budget " + (Float.parseFloat(budgetDisplayOnSmartCard))  +" for week " +daypickers[1] + " on budget smartcard doesn't match the budget entered " + totalBudgetedWagesForBudgetSmartCard, false);
//                            }
//
//                            float finaltotalScheduledHourIfBudgetEntered = (float) (Math.round(totalScheduledHourIfBudgetEntered * 10) / 10.0);
//                            if (finaltotalScheduledHourIfBudgetEntered == (Float.parseFloat(budgetOnScheduleSmartcard))) {
//                                SimpleUtils.pass("Budget " + (Float.parseFloat(budgetOnScheduleSmartcard))  +" for week " +daypickers[1] + " on schedule smartcard matches the budget calculated " + finaltotalScheduledHourIfBudgetEntered);
//                            } else {
//                                SimpleUtils.fail("Budget " + (Float.parseFloat(budgetOnScheduleSmartcard))  +" for week " +daypickers[1] + " on schedule smartcard doesn't match the budget calculated " + finaltotalScheduledHourIfBudgetEntered, true);
//                            }
//                            int finaltotalScheduledWagesIfBudgetEntered = (int) (Math.round(totalScheduledWagesIfBudgetEntered * 10) / 10.0);
//                            if (finaltotalScheduledWagesIfBudgetEntered == (Integer.parseInt(budgetedWagesOnScheduleSmartcard))) {
//                                SimpleUtils.pass("Budgeted Wages " + (Float.parseFloat(budgetedWagesOnScheduleSmartcard))  +" for week " +daypickers[1] + " on schedule smartcard matches the budget wages calculated " + finaltotalScheduledWagesIfBudgetEntered);
//                            } else {
//                                SimpleUtils.fail("Budget Wages" + (Float.parseFloat(budgetedWagesOnScheduleSmartcard))  +" for week " +daypickers[1] + " on schedule smartcard doesn't match the budget wages calculated " + finaltotalScheduledWagesIfBudgetEntered, true);
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


    @Override
    public String getsmartCardTextByLabel(String cardLabel) {
        if (carouselCards.size() != 0) {
            for (WebElement carouselCard : carouselCards) {
                if (carouselCard.isDisplayed() && carouselCard.getText().toLowerCase().contains(cardLabel.toLowerCase()))
                    return carouselCard.getText();
            }
        }
        return null;
    }


    @Override
    public String getWeatherTemperature() throws Exception {
        String temperatureText = "";
        if (weatherTemperatures.size() != 0)
            for (WebElement weatherTemperature : weatherTemperatures) {
                if (weatherTemperature.isDisplayed()) {
                    if (temperatureText == "")
                        temperatureText = weatherTemperature.getText();
                    else
                        temperatureText = temperatureText + " | " + weatherTemperature.getText();
                } else if (!weatherTemperature.isDisplayed()) {
                    while (isSmartCardScrolledToRightActive() == true) {
                        if (temperatureText == "")
                            temperatureText = weatherTemperature.getText();
                        else
                            temperatureText = temperatureText + " | " + weatherTemperature.getText();
                    }
                }
            }

        return temperatureText;
    }

//	@Override
//	public void generateOrUpdateAndGenerateSchedule() throws Exception {
//		if (isElementLoaded(generateSheduleButton)) {
//			click(generateSheduleButton);
//			waitForSeconds(4);
//			if(isElementLoaded(updateAndGenerateScheduleButton)){
//				click(updateAndGenerateScheduleButton);
//				SimpleUtils.pass("Schedule Update and Generate button clicked Successfully!");
//				checkOutGenerateScheduleBtn(checkOutTheScheduleButton);
//			}else if(isElementLoaded(checkOutTheScheduleButton)) {
//				checkOutGenerateScheduleBtn(checkOutTheScheduleButton);
//			}else{
//				SimpleUtils.fail("Not able to generate Schedule Successfully!",false);
//			}
//
//		} else {
//			SimpleUtils.assertOnFail("Schedule Already generated for active week!", false, true);
//		}
//	}

    public void checkoutSchedule() {
        click(checkOutTheScheduleButton);
        SimpleUtils.pass("Schedule Generated Successfuly!");
    }

    public void updateAndGenerateSchedule() {
        if (isElementEnabled(updateAndGenerateScheduleButton)) {
            click(updateAndGenerateScheduleButton);
            SimpleUtils.pass("Schedule Update and Generate button clicked Successfully!");
            if (isElementEnabled(checkOutTheScheduleButton)) {
                checkoutSchedule();
            } else {
                SimpleUtils.fail("Not able to generate Schedule Successfully!", false);
            }
        } else {
            SimpleUtils.fail("Not able to generate Schedule Successfully!", false);
        }
    }

    @Override
    public void generateOrUpdateAndGenerateSchedule() throws Exception {
        if (isElementEnabled(generateSheduleButton)) {
            click(generateSheduleButton);
            openBudgetPopUp();
//            openBudgetPopUpGenerateSchedule();
            if (isElementLoaded(generateSheduleForEnterBudgetBtn, 5)) {
                click(generateSheduleForEnterBudgetBtn);
                if (isElementEnabled(checkOutTheScheduleButton, 20)) {
                    checkoutSchedule();
                } else if (isElementLoaded(updateAndGenerateScheduleButton, 5)) {
                    updateAndGenerateSchedule();
                } else {
                    SimpleUtils.fail("Not able to generate Schedule Successfully!", false);
                }
            } else if (isElementLoaded(updateAndGenerateScheduleButton, 5)) {
                updateAndGenerateSchedule();
            } else if (isElementEnabled(checkOutTheScheduleButton,20)) {
                checkOutGenerateScheduleBtn(checkOutTheScheduleButton);
                SimpleUtils.pass("Schedule Generated Successfuly!");
            } else {
                SimpleUtils.fail("Not able to generate Schedule Successfully!", false);
            }

        } else {
            SimpleUtils.assertOnFail("Schedule Already generated for active week!", false, true);
        }
    }


    public void checkOutGenerateScheduleBtn(WebElement checkOutTheScheduleButton) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(
                MyThreadLocal.getDriver()).withTimeout(Duration.ofSeconds(60))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(org.openqa.selenium.NoSuchElementException.class);
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
            SimpleUtils.pass("Schedule Generated Successfuly!");
        } else {
            SimpleUtils.fail("Not able to generate Schedule Successfully!", false);
        }

	}


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
		return false;
	}


    @Override
    public HashMap<String, Integer> getScheduleBufferHours() throws Exception {
        HashMap<String, Integer> schedulePageBufferHours = new HashMap<String, Integer>();
        int gutterCellCount = 0;
        int totalHoursCountForShift = scheduleShiftTimeHeaderCells.size();
        int cellCountInAnHour = 2;
        int openingBufferHours = 0;
        int closingBufferHours = 0;
        if (scheduleShiftsRows.size() > 0) {
            for (WebElement scheduleShiftsRow : scheduleShiftsRows) {
                List<WebElement> scheduleShiftRowCells = scheduleShiftsRow.findElements(By.cssSelector("div.sch-day-view-grid-cell"));
                String backgroundColorToVerify = "";
                String[] styles = scheduleShiftRowCells.get(0).getAttribute("style").split(";");
                for (String styleAttr : styles) {
                    if (styleAttr.toLowerCase().contains("background-color"))
                        backgroundColorToVerify = styleAttr;
                }
                if (backgroundColorToVerify != "") {
                    cellCountInAnHour = Integer.valueOf(scheduleShiftRowCells.size() / totalHoursCountForShift);
                    for (WebElement scheduleShiftRowCell : scheduleShiftRowCells) {
                        if (scheduleShiftRowCell.getAttribute("style").contains(backgroundColorToVerify))
                            gutterCellCount++;
                        else {
                            if (openingBufferHours == 0) {
                                openingBufferHours = gutterCellCount;
                                gutterCellCount = 0;
                            }

						}
					}
					closingBufferHours += gutterCellCount;
				}
				else
					SimpleUtils.fail("Schedule Page: Unable to fetch backgroung color of 'Gutter Area'.", false);

                schedulePageBufferHours.put("closingBufferHours", (closingBufferHours / cellCountInAnHour));
                schedulePageBufferHours.put("openingBufferHours", (openingBufferHours / cellCountInAnHour));
                break;
            }
        } else
            SimpleUtils.fail("Schedule Page: Shift Rows not loaded.", false);

        return schedulePageBufferHours;
    }


    @Override
    public boolean isComlianceReviewRequiredForActiveWeek() throws Exception {
        if (complianceReviewDangerImgs.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void unGenerateActiveScheduleScheduleWeek() throws Exception {
        String unGenerateScheduleOptionText = "Ungenerate Schedule";
        if (isElementEnabled(scheduleAdminDropDownBtn)) {
            click(scheduleAdminDropDownBtn);
            if (scheduleAdminDropDownOptions.size() > 0) {
                for (WebElement scheduleAdminDropDownOption : scheduleAdminDropDownOptions) {
                    if (scheduleAdminDropDownOption.getText().toLowerCase().contains(unGenerateScheduleOptionText.toLowerCase())) {
                        click(scheduleAdminDropDownOption);
                        if (isElementEnabled(unGenerateBtnOnPopup)) {
                            click(unGenerateBtnOnPopup);
                            SimpleUtils.pass("Schedule Page: Active Week ('" + getActiveWeekText() + "') Ungenerated Successfully.");
                        } else
                            SimpleUtils.fail("Schedule Page: Ungenerate popup 'Ungenerate Button not loaded for the week: '"
                                    + getActiveWeekText() + "'.", false);
                        break;
                    }

                }
            } else
                SimpleUtils.fail("Schedule Page: Admin dropdown Options not loaded to Ungenerate the Schedule for the Week : '"
                        + getActiveWeekText() + "'.", false);
        } else
            SimpleUtils.fail("Schedule Page: Admin dropdown button not loaded to Ungenerate the Schedule for the Week : '"
                    + getActiveWeekText() + "'.", false);

    }

    @Override
    public int getScheduleShiftIntervalCountInAnHour() throws Exception {
        int schedulePageShiftIntervalMinutes = 0;
        float totalHoursCountForShift = 0;
        if (scheduleShiftsRows.size() > 0) {
            for (WebElement scheduleShiftTimeHeaderCell : scheduleShiftTimeHeaderCells) {
                if (scheduleShiftTimeHeaderCell.getText().trim().length() > 0)
                    totalHoursCountForShift++;
                else
                    totalHoursCountForShift = (float) (totalHoursCountForShift + 0.5);
            }
            List<WebElement> scheduleShiftRowCells = scheduleShiftsRows.get(0).findElements(By.cssSelector("div.sch-day-view-grid-cell"));
            int shiftIntervalCounts = scheduleShiftRowCells.size();
            schedulePageShiftIntervalMinutes = Math.round(shiftIntervalCounts / totalHoursCountForShift);
        } else
            SimpleUtils.fail("Schedule Page: Shift Rows not loaded.", false);

        return schedulePageShiftIntervalMinutes;
    }

    @Override
    public void toggleSummaryView() throws Exception {
        String toggleSummaryViewOptionText = "Toggle Summary View";
        if (isElementLoaded(scheduleAdminDropDownBtn, 10)) {
            click(scheduleAdminDropDownBtn);
            if (scheduleAdminDropDownOptions.size() > 0) {
                for (WebElement scheduleAdminDropDownOption : scheduleAdminDropDownOptions) {
                    if (scheduleAdminDropDownOption.getText().toLowerCase().contains(toggleSummaryViewOptionText.toLowerCase())) {
                        click(scheduleAdminDropDownOption);
                    }
                }
            } else
                SimpleUtils.fail("Schedule Page: Admin dropdown Options not loaded to Toggle Summary View for the Week : '"
                        + getActiveWeekText() + "'.", false);
        } else
            SimpleUtils.fail("Schedule Page: Admin dropdown not loaded to Toggle Summary View for the Week : '"
                    + getActiveWeekText() + "'.", false);
    }

    @FindBy(css = "div[ng-if=\"showSummaryView\"]")
    private WebElement summaryViewDiv;

    @Override
    public boolean isSummaryViewLoaded() throws Exception {
        if (isElementLoaded(summaryViewDiv))
            return true;
        return false;
    }

    @FindBy(css = "tr[ng-repeat=\"day in summary.workingHours\"]")
    private List<WebElement> operatingHoursRows;

    @FindBy(css = "div.lgn-time-slider-notch-selector-start")
    private WebElement scheduleOperatingStartHrsSlider;

    @FindBy(css = "div.lgn-time-slider-notch-selector-end")
    private WebElement scheduleOperatingEndHrsSlider;

    @FindBy(css = "lg-button[label=\"Save\"]")
    private WebElement operatingHoursSaveBtn;

    @FindBy(css = "lg-button[label=\"Cancel\"]")
    private WebElement operatingHoursCancelBtn;

    public void updateScheduleOperatingHours(String day, String startTime, String endTime) throws Exception {
        Thread.sleep(1000);
        if (operatingHoursRows.size() > 0) {
            for (WebElement operatingHoursRow : operatingHoursRows) {
                if (operatingHoursRow.getText().toLowerCase().contains(day.toLowerCase())) {
                    WebElement editBtn = operatingHoursRow.findElement(By.cssSelector("span[ng-if=\"canEditWorkingHours\"]"));
                    if (isElementLoaded(editBtn)) {
                        click(editBtn);
                        if (scheduleOperatingStartHrsSlider.getText().toLowerCase().contains(startTime.toLowerCase())
                                && scheduleOperatingEndHrsSlider.getText().toLowerCase().contains(endTime.toLowerCase())) {
                            SimpleUtils.pass("Operating Hours already updated for the day '" + day + "' with Start time '" + startTime
                                    + "' and End time '" + endTime + "'.");
                            if (isElementLoaded(operatingHoursCancelBtn)) {
                                click(operatingHoursCancelBtn);
                            }
                        } else {
                            dragRollerElementTillTextMatched(scheduleOperatingStartHrsSlider, startTime);
                            dragRollerElementTillTextMatched(scheduleOperatingEndHrsSlider, endTime);
                            if (isElementLoaded(operatingHoursSaveBtn)) {
                                click(operatingHoursSaveBtn);
                                SimpleUtils.pass("Operating Hours updated for the day '" + day + "' with Start time '" + startTime
                                        + "' and End time '" + endTime + "'.");
                            }
                        }
                    } else
                        SimpleUtils.fail("Operating Hours Table 'Edit' button not loaded.", false);
                }
            }
        } else
            SimpleUtils.fail("Operating Hours Rows not loaded.", false);
    }

    @Override
    public void dragRollerElementTillTextMatched(WebElement rollerElement, String textToMatch) throws Exception {

        int hourOnSlider = Integer.valueOf(rollerElement.getText().split(":")[0]);
        if (rollerElement.getText().toLowerCase().contains("pm"))
            hourOnSlider = hourOnSlider + 12;
        int openingHourOnJson = Integer.valueOf(textToMatch.split(":")[0]);
        if (textToMatch.toLowerCase().contains("pm"))
            openingHourOnJson = openingHourOnJson + 12;
        int sliderOffSet = 5;
        if (hourOnSlider > openingHourOnJson)
            sliderOffSet = -5;
        while (!rollerElement.getText().toLowerCase().contains(textToMatch.toLowerCase())) {
            Thread.sleep(500);
            moveDayViewCards(rollerElement, sliderOffSet);
        }
    }

    @Override
    public boolean isScheduleOperatingHoursUpdated(String startTime, String endTime) throws Exception {
        String scheduleShiftHeaderStartTime = "";
        float hoursBeforeStartTime = 0;
        String scheduleShiftHeaderEndTime = "";
        float hoursAfterEndTime = 0;
        if (scheduleShiftTimeHeaderCells.get(0).getText().trim().length() > 0)
            scheduleShiftHeaderStartTime = scheduleShiftTimeHeaderCells.get(0).getText().split(" ")[0];
        else {
            hoursBeforeStartTime = (float) 0.5;
            scheduleShiftHeaderStartTime = scheduleShiftTimeHeaderCells.get(1).getText().split(" ")[0];
        }
        //System.out.println("hoursBeforeStartTime : "+hoursBeforeStartTime);

        if (scheduleShiftTimeHeaderCells.get(scheduleShiftTimeHeaderCells.size() - 1).getText().trim().length() > 0)
            scheduleShiftHeaderEndTime = scheduleShiftTimeHeaderCells.get(scheduleShiftTimeHeaderCells.size() - 1).getText().split(" ")[0];
        else {
            hoursAfterEndTime = (float) 0.5;
            scheduleShiftHeaderEndTime = scheduleShiftTimeHeaderCells.get(scheduleShiftTimeHeaderCells.size() - 2).getText().split(" ")[0];
        }

		//System.out.println("hoursAfterEndTime : "+hoursAfterEndTime);

        HashMap<String, Integer> scheduleBufferHours = getScheduleBufferHours();
        for (Map.Entry<String, Integer> bufferHours : scheduleBufferHours.entrySet()) {
            //System.out.println(bufferHours.getKey() +" : "+bufferHours.getValue());
        }

        float startHours = Float.valueOf(scheduleShiftHeaderStartTime) + hoursBeforeStartTime + scheduleBufferHours.get("openingBufferHours");
        System.out.println("startHours: " + startHours);
        float endHours = Float.valueOf(scheduleShiftHeaderEndTime) - scheduleBufferHours.get("closingBufferHours") + 1;
        System.out.println("scheduleShiftHeaderEndTime : " + endHours);
        if (Integer.valueOf(startTime.split(":")[0]) == (int) startHours && Integer.valueOf(endTime.split(":")[0]) == (int) endHours)
            return true;

        return false;
    }

    @Override
    public void verifyScheduledHourNTMCountIsCorrect() throws Exception {
        getHoursAndTeamMembersForEachDaysOfWeek();
        verifyActiveWeekDailyScheduleHoursInWeekView();
        verifyActiveWeekTeamMembersCountAvailableShiftCount();
    }

    @FindBy(css = "card-carousel-card[ng-if='compliance'] div.card-carousel-card-smart-card-required")
    private WebElement complianceSmartCard;

    @FindBy(css = "img[ng-if='hasViolateCompliance(line, scheduleWeekDay)'] ")
    private List<WebElement> complianceInfoIcon;

    @FindBy(css = "card-carousel-card[ng-if='compliance'] span")
    private WebElement viewShift;
	@FindBy(css = "img[ng-if='hasViolateCompliance(shift)']")
	private List<WebElement> complianceInfoIconDayView;

    @FindBy(css = "div.sch-worker-display-name")
    private List<WebElement> workerName;

    @FindBy(xpath = "//*[contains(@class,'week-view-shift-hover-info-icon')]/preceding-sibling::div")
    private List<WebElement> shiftDurationInWeekView;

    @FindBy(xpath = "//*[contains(@class,'shift-hover-subheading')]/parent::div/div[1]")
    private WebElement workerNameInPopUp;

    @FindBy(xpath = "//*[contains(@class,'shift-hover-subheading')]/parent::div/div[2]")
    private WebElement workerRoleDetailsFromPopUp;

    @FindBy(xpath = "//*[@class='shift-hover-seperator']/preceding-sibling::div[1]/div[1]")
    private WebElement shiftDurationInPopUp;

    @FindBy(css = "card-carousel-card[ng-if='compliance'] h1")
    private WebElement numberOfComplianceShift;

    @FindBy(css = "div[ng-repeat*='getComplianceMessages'] span")
    private List<WebElement> complianceMessageInPopUp;

    @FindBy (css = "div.sch-day-view-shift-worker-name")
    private List<WebElement> workerStatus;


    public String timeFormatter(String formattedTime) {
        String UpdatedTime;
        String returnValue = null;
        if (formattedTime.contains(":00")) {
            UpdatedTime = formattedTime.replace(":00", "");
            returnValue = UpdatedTime;
        } else {
            returnValue = formattedTime;
        }
        return returnValue;
    }

    public void captureShiftDetails() {
//	    HashMap<String, String> shiftDetailsWeekView = new HashMap<>();
        HashMap<List<String>, List<String>> shiftWeekView = new HashMap<>();
        List<String> workerDetailsWeekView = new ArrayList<>();
        List<String> shiftDurationWeekView = new ArrayList<>();
        HashMap<List<String>, List<String>> shiftDetailsPopUpView = new HashMap<>();
        List<String> workerDetailsPopUpView = new ArrayList<>();
        List<String> shiftDurationPopUpView = new ArrayList<>();
        List<String> complianceMessage = new ArrayList<>();

//        boolean flag=true;
        int counter = 0;
        if (areListElementVisible(infoIcon)) {
            for (int i = 0; i < infoIcon.size(); i++) {
                if (areListElementVisible(complianceInfoIcon)) {
                    if (counter < complianceInfoIcon.size()) {
                        if (infoIcon.get(i).getAttribute("ng-if").equals(complianceInfoIcon.get(counter).getAttribute("ng-if"))) {
                            counter = counter + 1;
                            workerDetailsWeekView.add(workerName.get(i).getText().toLowerCase());
//							String formattedTime = shiftDurationInWeekView.get(i).getText();
//							timeFormatter(shiftDurationInWeekView.get(i).getText());
                            shiftDurationWeekView.add(shiftDurationInWeekView.get(i).getText());

                        }
                    }
                } else {
                    SimpleUtils.fail("Shift not loaded successfully in week view", true);
                }
            }
            shiftWeekView.put(workerDetailsWeekView, shiftDurationWeekView);
            if (isElementEnabled(viewShift, 5)) {
                click(viewShift);
                if (areListElementVisible(complianceInfoIcon)) {
                    for (int i = 0; i < complianceInfoIcon.size(); i++) {
                        click(complianceInfoIcon.get(i));
                        workerDetailsPopUpView.add(workerNameInPopUp.getText().toLowerCase());
                        shiftDurationPopUpView.add(timeFormatter(shiftDurationInPopUp.getText()));
                        ;
                        String str = "";
                        String finalstr = "";
                        for (int j = 0; j < complianceMessageInPopUp.size(); j++) {
                            str = complianceMessageInPopUp.get(j).getText();
                            finalstr = finalstr + " " + j + "." + str;
                        }
                        SimpleUtils.pass(workerNameInPopUp.getText() + " has following voilations " + finalstr);
                    }
                    shiftDetailsPopUpView.put(workerDetailsPopUpView, shiftDurationPopUpView);
                } else {
                    SimpleUtils.fail("Shift not loaded successfully in week view", true);
                }
            }
        } else {
            SimpleUtils.fail("Shift not loaded successfully in week view", true);
        }
        for (Map.Entry<List<String>, List<String>> entry : shiftWeekView.entrySet()) {
            List<String> keysShiftWeekView = entry.getKey();
            List<String> valuesShiftWeekView = entry.getValue();
            System.out.println(shiftWeekView.keySet());
            for (Map.Entry<List<String>, List<String>> entry1 : shiftDetailsPopUpView.entrySet()) {
                List<String> keysShiftDetailsPopUpView = entry1.getKey();
                List<String> valuesShiftDetailsPopUpView = entry1.getValue();
                int index = 0;
                int count = 0;
                for (int i = 0; i < keysShiftWeekView.size(); i++) {
                    for (int j = 0; j < keysShiftDetailsPopUpView.size(); j++) {
                        if (keysShiftWeekView.get(i).equals(keysShiftDetailsPopUpView.get(j))) {
                            if (valuesShiftWeekView.get(i).replace(" ", "").equals(valuesShiftDetailsPopUpView.get(j)))
                                SimpleUtils.pass("TM " + keysShiftWeekView.get(i) + " has shift " + valuesShiftWeekView.get(i));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void complianceShiftSmartCard() throws Exception {
        if (isElementEnabled(complianceSmartCard)) {
            String[] complianceShiftCountFromSmartCard = numberOfComplianceShift.getText().split(" ");
            int noOfcomplianceShiftFromSmartCard = Integer.valueOf(complianceShiftCountFromSmartCard[0]);
            int noOfComplianceShiftInWeekScheduleTable = complianceInfoIcon.size();
            captureShiftDetails();
        }

    }

    @FindBy(css = "div.allow-pointer-events.ng-scope")
    private List<WebElement> imageSize;
//    @FindBy(css = "span.sch-worker-action-label")
//    private List<WebElement> viewProfile;
    @FindBy(css = "div[ng-class*='ChangeRole'] span")
    private WebElement changeRole;
    @FindBy(css = "div[ng-class*='ConvertToOpen'] span")
    private WebElement convertOpen;
    @FindBy(css = "div[ng-class*='ViewProfile'] span")
    private WebElement viewProfile;
    @FindBy(css = "div[ng-class*='AssignTM'] span")
    private WebElement assignTM;
    @FindBy(xpath = "//span[contains(text(),'YES')]")
    private WebElement openPopYesButton;

    public void beforeEdit() {
        if (areListElementVisible(imageSize, 5)) {
            click(imageSize.get(5));
            WebElement element = getDriver().findElement(By.cssSelector("div.sch-worker-popover.allow-pointer-events.ng-scope"));
            JavascriptExecutor jse = (JavascriptExecutor) getDriver();
            String txt = jse.executeScript("return arguments[0].innerHTML;", element).toString();
            System.out.println(txt);
//		  	 	   		if(viewProfile.size()>0){
//				   		   click(viewProfile.get(1));
//			 	   		}

        }
    }


	public void selectTeamMembersOptionForOverlappingSchedule() throws Exception{
		if(isElementEnabled(btnSearchTeamMember,5)){
			click(btnSearchTeamMember);
			if(isElementLoaded(textSearch,5)) {
				searchWorkerName(propertySearchTeamMember.get("AssignTeamMember"));
			}
		}else{
			SimpleUtils.fail("Select Team member option not available on page",false);
		}

	}


	public boolean getScheduleOverlappingStatus()throws Exception {
		boolean ScheduleStatus = false;
		if(areListElementVisible(scheduleSearchTeamMemberStatus,5)){
			for(int i=0; i<scheduleSearchTeamMemberStatus.size();i++){
				if(scheduleSearchTeamMemberStatus.get(i).getText().contains("Scheduled")){
					click(radionBtnSearchTeamMembers.get(i));
					String workerDisplayFirstNameText =(searchWorkerDisplayName.get(i).getText().replace(" ","").split("\n"))[0];
					String workerDisplayLastNameText =(searchWorkerDisplayName.get(i).getText().replace(" ","").split("\n"))[1];
					setTeamMemberName(workerDisplayFirstNameText + workerDisplayLastNameText);
					boolean flag = displayAlertPopUp();
					if (flag) {
						ScheduleStatus = true;
						break;
					}
				}
			}
		}

		return ScheduleStatus;
	}

	public void searchWorkerName(String searchInput) throws Exception {
		String[] searchAssignTeamMember = searchInput.split(",");
		if(isElementLoaded(textSearch,10) && isElementLoaded(searchIcon,10)){
			for(int i=0; i<searchAssignTeamMember.length;i++){
				textSearch.sendKeys(searchAssignTeamMember[i]);
				click(searchIcon);
				if(getScheduleOverlappingStatus()){
					break;
				}else{
					textSearch.clear();
					if(i== searchAssignTeamMember.length-1){
                        SimpleUtils.fail("There is no data found for given team member. Please provide some other input", false);
                    }
				}
			}
		}else{
			SimpleUtils.fail("Search text not editable and icon are not clickable", false);
		}

	}


	public boolean displayAlertPopUp() throws Exception{
		boolean flag = true;
		String msgAlert = null;
		if(isElementLoaded(popUpScheduleOverlap,5)){
			if(isElementLoaded(alertMessage,5)){
				msgAlert = alertMessage.getText();
				if(isElementLoaded(btnAssignAnyway,5)){
					if(btnAssignAnyway.getText().toLowerCase().equals("OK".toLowerCase())){
						click(btnAssignAnyway);
						flag = false;
					}else{
						String startTime = ((msgAlert.split(": "))[1].split(" - "))[0];
						String startTimeFinal = SimpleUtils.convertTimeIntoHHColonMM(startTime);
						String endTime = (((msgAlert.split(": "))[1].split(" - "))[1].split(" "))[0];
						String endTimeFinal = SimpleUtils.convertTimeIntoHHColonMM(endTime);
						String timeDuration = startTimeFinal + "-" + endTimeFinal;
						setScheduleHoursTimeDuration(timeDuration);
						click(btnAssignAnyway);
						flag= true;
					}

				}else{
					SimpleUtils.fail("Schedule Overlap Assign Anyway button not displayed on the page",false);
				}
			}else{
				SimpleUtils.fail("Schedule Overlap alert message not displayed",false);
			}
		}else{
			SimpleUtils.fail("Schedule Overlap pop up not displayed",false);
		}
		return flag;
	}


	public void verifyScheduleStatusAsOpen() throws Exception {
	    boolean flag = true;
	    if(areListElementVisible(infoIcon)){
	        for(int i=0; i<infoIcon.size();i++){
                if(areListElementVisible(workerStatus,5)){
                    if(workerStatus.get(i).getText().toLowerCase().contains("Open".toLowerCase())){
                        click(infoIcon.get(i));
                        flag = verifyShiftDurationInComplianceImageIconPopUp(true);
						if(flag){
							SimpleUtils.pass("Worker status " +workerStatus.get(i).getText() + " matches with the expected result");
                            click(infoIcon.get(i));
							break;
						}
                    }else{
                        flag = false;
                    }
                }else{
                    SimpleUtils.fail("Worker status not available on the page",true);
                }
            }
        }else{
            SimpleUtils.fail("There is no image icon available on the page",false);
        }

        if(!flag) {
			SimpleUtils.report("Worker status does not match with the expected result");
		}

    }


    public boolean verifyShiftDurationInComplianceImageIconPopUp(boolean openStatus) throws Exception{
	    boolean flag = true;
	    if(openStatus){
			shiftDurationVerification(getScheduleHoursTimeDuration());
		}else{
			String scheduledHoursStartTime = MyThreadLocal.getScheduleHoursStartTime();
			String scheduledHoursEndTime = MyThreadLocal.getScheduleHoursEndTime();
			String scheduleTimeDuration = scheduledHoursStartTime + "-" + scheduledHoursEndTime;
			shiftDurationVerification(scheduleTimeDuration);
		}

        return flag;
    }



	public void verifyScheduleStatusAsTeamMember() throws Exception {
		boolean flag = true;
		if(areListElementVisible(infoIcon)){
			for(int i=0; i<infoIcon.size();i++){
				if(areListElementVisible(workerStatus,5)){
					if(workerStatus.get(i).getText().replace(" ","").toLowerCase().contains(getTeamMemberName().toLowerCase())){
						click(infoIcon.get(i));
						flag = verifyShiftDurationInComplianceImageIconPopUp(false);
						if(flag){
							SimpleUtils.pass("Worker status " +workerStatus.get(i).getText() + " matches with the expected result");
							break;
						}
					}else{
						flag = false;
					}
				}else{
					SimpleUtils.fail("Worker status not available on the page",true);
				}
			}
		}else{
			SimpleUtils.fail("There is no image icon available on the page",false);
		}

		if(!flag) {
			SimpleUtils.report("Worker status does not match with the expected result");
		}

	}


	public void shiftDurationVerification(String scheduleHourTimeDuration) throws Exception{
		if (isElementLoaded(shiftDurationInPopUp,5)){
			if(shiftDurationInPopUp.getText().toLowerCase().equals(scheduleHourTimeDuration.toLowerCase())){
				SimpleUtils.pass("Shift Time Duration " + shiftDurationInPopUp.getText().toLowerCase() + " matches with "+getScheduleHoursTimeDuration().toLowerCase());
			}else{
				SimpleUtils.report("Shift Time Duration value is " + shiftDurationInPopUp.getText().toLowerCase());
			}
		}else{
			SimpleUtils.fail("Compliance Image icon Pop up was unable to open Successfully",false);
		}
	}


	//added by Nishant

	public String getActiveAndNextDay() throws Exception{
		WebElement activeWeek = MyThreadLocal.getDriver().findElement(By.className("day-week-picker-period-active"));
		String activeDay = "";
		if(isElementLoaded(activeWeek)){
			activeDay = activeWeek.getText().replace("\n", " ").substring(0,3);
		}
		return activeDay;
	}

	@FindBy(css = "tr[ng-repeat='day in summary.workingHours'] td:nth-child(1)")
	private List<WebElement> operatingHoursScheduleDay;

	@FindBy(css = "tr[ng-repeat='day in summary.workingHours'] td.text-right.ng-binding")
	private List<WebElement> scheduleOperatingHrsTimeDuration;


	public HashMap<String, String> getOperatingHrsValue(String day) throws Exception {
		String currentDay = null;
		String nextDay = null;
		String finalDay = null;
		HashMap<String, String>  activeDayAndOperatingHrs = new HashMap<>();

		if(areListElementVisible(operatingHoursRows,5) &&
				areListElementVisible(operatingHoursScheduleDay,5) &&
				areListElementVisible(scheduleOperatingHrsTimeDuration,5)){
			for(int i =0; i<operatingHoursRows.size();i++){
				if(i == operatingHoursRows.size()-1){
					if(operatingHoursScheduleDay.get(i).getText().substring(0,3).equalsIgnoreCase(day)){
						currentDay = day;
						nextDay = operatingHoursScheduleDay.get(0).getText().substring(0,3);
						activeDayAndOperatingHrs.put("ScheduleOperatingHrs" , currentDay + "-" + scheduleOperatingHrsTimeDuration.get(i).getText());
						SimpleUtils.pass("Current day in Schedule " + day + " matches" +
								" with Operation hours Table " + operatingHoursScheduleDay.get(i).getText().substring(0,3));
						break;
					}else{
						SimpleUtils.fail("Current day in Schedule " + day + " does not match" +
								" with Operation hours Table " + operatingHoursScheduleDay.get(i).getText().substring(0,3),false);
					}
				}else if(operatingHoursScheduleDay.get(i).getText().substring(0,3).equalsIgnoreCase(day)){
					currentDay = day;
					nextDay = operatingHoursScheduleDay.get(i+1).getText().substring(0,3);
					activeDayAndOperatingHrs.put("ScheduleOperatingHrs" , currentDay + "-" + scheduleOperatingHrsTimeDuration.get(i).getText());
					SimpleUtils.pass("Current day in Schedule " + day + " matches" +
							" with Operation hours Table " + operatingHoursScheduleDay.get(i).getText().substring(0,3));
					break;
				}

			}
		}else{
			SimpleUtils.fail("Operating hours table not loaded Successfully",false);
		}
		return activeDayAndOperatingHrs;
	}


	public void moveSliderAtCertainPoint(String shiftTime, String startingPoint) throws Exception {
		if(startingPoint.equalsIgnoreCase("End")){
			if(isElementLoaded(sliderNotchEnd,10) && sliderDroppableCount.size()>0){
				SimpleUtils.pass("Shift timings with Sliders loaded on page Successfully for End Point");
				WebElement element = getDriver().findElement(By.xpath("//div[contains(@class,'lgn-time-slider-notch-label ng-binding ng-scope PM')][text()="+Integer.parseInt(shiftTime)+"]"));
				mouseHoverDragandDrop(sliderNotchEnd,element);
			} else{
				SimpleUtils.fail("Shift timings with Sliders not loaded on page Successfully", false);
			}
		}else if(startingPoint.equalsIgnoreCase("Start")){
			if(isElementLoaded(sliderNotchStart,10) && sliderDroppableCount.size()>0){
				SimpleUtils.pass("Shift timings with Sliders loaded on page Successfully for End Point");
				WebElement element = getDriver().findElement(By.xpath("//div[contains(@class,'lgn-time-slider-notch-label ng-binding ng-scope AM')][text()="+Integer.parseInt(shiftTime)+"]"));
				mouseHoverDragandDrop(sliderNotchStart,element);
			} else{
				SimpleUtils.fail("Shift timings with Sliders not loaded on page Successfully", false);
			}
		}
	}


	public void clickOnNextDaySchedule(String activeDay) throws Exception {
		List<WebElement> activeWeek = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
		for(int i=0; i<activeWeek.size();i++){
			String currentDay = activeWeek.get(i).getText().replace("\n", " ").substring(0,3);
			if(currentDay.equalsIgnoreCase(activeDay)){
				if(i== activeWeek.size()-1){
				navigateWeekViewOrDayViewToPastOrFuture(ScheduleNewUITest.weekViewType.Next.getValue(),
						ScheduleNewUITest.weekCount.One.getValue());
				waitForSeconds(3);
				}else{
					click(activeWeek.get(i+1));
				}
			}
		}

	}

	public void selectTeamMembersOptionForSchedule() throws Exception {
		if(isElementEnabled(btnSearchTeamMember,5)){
			click(btnSearchTeamMember);
			if(isElementLoaded(textSearch,5)) {
				searchText(propertySearchTeamMember.get("AssignTeamMember"));
			}
		}else{
			SimpleUtils.fail("Select Team member option not available on page",false);
		}

	}

    public void selectTeamMembersOptionForScheduleForClopening() throws Exception {
        if(isElementEnabled(btnSearchTeamMember,5)){
            click(btnSearchTeamMember);
            if(isElementLoaded(textSearch,5)) {
                if(getTeamMemberName()!=null){
                    searchText(getTeamMemberName());
                }else{
                    searchText(propertySearchTeamMember.get("AssignTeamMember"));
                }
            }
        }else{
            SimpleUtils.fail("Select Team member option not available on page",false);
        }

    }

    @FindBy(css = "div.week-view-shift-hover-info-icon")
    private List<WebElement> scheduleInfoIcon;

    @FindBy(css = "button[ng-click*='confirmSaveAction']")
    private WebElement saveOnSaveConfirmationPopup;

    @FindBy(css = "button[ng-click*='okAction']")
    private WebElement okAfterSaveConfirmationPopup;

    public void clickViewProfile(int i) {
        if (areListElementVisible(imageSize, 5)) {
            click(imageSize.get(i));
            if (isElementEnabled(viewProfile)) {
                click(viewProfile);
            }
        }
    }

    public void viewProfile() {
        int counter = 0;
        if (areListElementVisible(imageSize, 5)) {
            for (int i = 0; i < imageSize.size(); i++) {
                if (!workerName.get(i).getText().equalsIgnoreCase("open")) {
                    clickViewProfile(i);
                    saveSchedule();
                    counter = i;
                    break;
                }
            }
        }
        if (areListElementVisible(imageSize, 5)) {
            click(imageSize.get(5));
            click(viewProfile);
            WebElement element = getDriver().findElement(By.cssSelector("div.sch-worker-popover.allow-pointer-events.ng-scope"));
            JavascriptExecutor jse = (JavascriptExecutor) getDriver();
            String txt = jse.executeScript("return arguments[0].innerHTML;", element).toString();
            System.out.println(txt);
//		  	 	   		if(viewProfile.size()>0){
//				   		   click(viewProfile.get(1));
//			 	   		}

        }
    }

    public void verifyOpenShift(String TMName, String workerRole, String shiftDuration, int counter) {
        click(scheduleInfoIcon.get(counter));
        String workerRoleOpenShift = workerRoleDetailsFromPopUp.getText();
        String shiftDurationOpenShift = shiftDurationInPopUp.getText();
        if (workerRoleOpenShift.equalsIgnoreCase(workerRole) && shiftDurationOpenShift.equalsIgnoreCase(shiftDuration) && workerName.get(counter).getText().equalsIgnoreCase("open")) {
            SimpleUtils.pass(TMName + "'s " + workerRole + " shift of duration " + shiftDuration + " got converted to open shift successfully");
        } else {
            SimpleUtils.fail(TMName + "'s " + workerRole + " shift of duration " + shiftDuration + " was not converted to open shift successfully", false);
        }
    }

    public void saveSchedule() {
        if (isElementEnabled(scheduleSaveBtn)) {
            click(scheduleSaveBtn);
        } else {
            SimpleUtils.fail("Schedule save button not found", false);
        }
        if (isElementEnabled(saveOnSaveConfirmationPopup)) {
            click(saveOnSaveConfirmationPopup);
        } else {
            SimpleUtils.fail("Schedule save button not found", false);
        }
        if (isElementEnabled(okAfterSaveConfirmationPopup)) {
            click(okAfterSaveConfirmationPopup);
        } else {
            SimpleUtils.fail("Schedule save button not found", false);
        }
    }

    public void convertToOpen(int i) {
        if (areListElementVisible(imageSize, 5)) {
            click(imageSize.get(i));
            if (isElementEnabled(convertOpen)) {
                click(convertOpen);
                if (isElementEnabled(openPopYesButton,5)) {
                    click(openPopYesButton);
                    waitForSeconds(3);
                } else {
                    SimpleUtils.fail("Open pop-up Yes button not found", false);
                }
            } else {
                SimpleUtils.fail("Convert to open shift option not found", false);
            }
        } else {
            SimpleUtils.fail("shift images not loaded successfully", false);
        }
    }

    public void convertToOpenShift() {
        String TMWorkerRole = null;
        String shiftDuration = null;
        String TMName = null;
        int counter = 0;
        if (areListElementVisible(imageSize, 5)) {
            for (int i = 0; i < imageSize.size(); i++) {
                if (!workerName.get(i).getText().equalsIgnoreCase("open")) {
                    click(scheduleInfoIcon.get(i));
                    String[] workerRole = workerRoleDetailsFromPopUp.getText().split("as ");
                    TMName = workerNameInPopUp.getText();
                    TMWorkerRole = workerRole[1];
                    shiftDuration = shiftDurationInPopUp.getText();
                    convertToOpen(i);
                    saveSchedule();
                    counter = i;
                    break;
                }
            }
            verifyOpenShift(TMName, TMWorkerRole, shiftDuration, counter);

        } else {
            SimpleUtils.fail("shift images not loaded successfully", false);
        }
    }




    @FindBy (css= "div[ng-style*='roleChangeStyle'] span")
    private List<WebElement> changeRoleValues;

    @FindBy (css = "div[ng-click*='changeRoleMoveRight'] i")
    private WebElement rightarrow;

    @FindBy (css = "button.sch-save")
    private WebElement ApplybuttonChangeRole;


    public void verifyChangedRoleShift(String TMName, String workerRole, String shiftDuration, int counter) throws Exception {
//        selectWorkRoleFilterByText("FOOT", true);
        for(int i=counter;i<imageSize.size(); i+=7){
            click(scheduleInfoIcon.get(i));
            String workerName = workerNameInPopUp.getText();
            String[] workerRoleShift = workerRoleDetailsFromPopUp.getText().split("as ");
            String shiftDurationShift = shiftDurationInPopUp.getText();
            if (workerRoleShift[1].equalsIgnoreCase(propertyWorkRole.get("changeWorkRole")) && shiftDurationShift.equalsIgnoreCase(shiftDuration) && workerName.equalsIgnoreCase(TMName)) {
                SimpleUtils.pass(TMName + "'s shift work role changed from" + workerRole + " to " + workerRoleShift[1] + " for shift duration " + shiftDuration +"  successfully");
                break;
            } else {
                SimpleUtils.fail(TMName + "'s shift work role not got changed from" + workerRole + " to " + workerRoleShift[1] + " for shift duration " + shiftDuration +"  successfully", false);
            }
        }
    }

    public void changeRole(int i) {
        if (areListElementVisible(imageSize, 5)) {
            click(imageSize.get(i));
            if (isElementEnabled(changeRole)) {
                click(changeRole);

                for (int j = 0; j < changeRoleValues.size(); j++) {
                    if (changeRoleValues.get(j).getText().equalsIgnoreCase(propertyWorkRole.get("changeWorkRole"))) {
                        click(changeRoleValues.get(j));
                        break;
                    } else {
                        if (j == changeRoleValues.size() - 1) {
                            click(rightarrow);
                            j = 0;
                        }

                 }
                }
                if (isElementEnabled(ApplybuttonChangeRole)) {
                    click(ApplybuttonChangeRole);
                } else {
                    SimpleUtils.fail("Apply button on change role flyout not found", false);
                }
            }
        }
    }

    public void changeWorkerRole() throws Exception{
        String TMWorkerRole = null;
        String shiftDuration = null;
        String TMName = null;
        int counter = 0;
        if (areListElementVisible(imageSize, 5)) {
            for (int i = 0; i < imageSize.size(); i++) {
                if (!workerName.get(i).getText().equalsIgnoreCase("open")) {
                    click(scheduleInfoIcon.get(i));
                    String[] workerRole = workerRoleDetailsFromPopUp.getText().split("as ");
                    TMName = workerNameInPopUp.getText();
                    TMWorkerRole = workerRole[1];
                    shiftDuration = shiftDurationInPopUp.getText();
                    changeRole(i);
                    saveSchedule();
                    counter = i;
                    break;
                }
            }
            verifyChangedRoleShift(TMName, TMWorkerRole, shiftDuration,counter );

        }else {
            SimpleUtils.fail("shift images not loaded successfully", false);
        }
    }

    public void assignTM(int i) {
        if (areListElementVisible(imageSize, 5)) {
            click(imageSize.get(i));
            if (isElementEnabled(assignTM)) {
                click(assignTM);
            }
        }
    }


    public void assignTeamMember() throws Exception{
        String TMWorkerRole = null;
        String shiftDuration = null;
        String TMName = null;
        int counter = 0;
        if (areListElementVisible(imageSize, 5)) {
            for (int i = 0; i < imageSize.size(); i++) {
                if (!workerName.get(i).getText().equalsIgnoreCase("open")) {
                    click(scheduleInfoIcon.get(i));
                    String[] workerRole = workerRoleDetailsFromPopUp.getText().split("as ");
                    TMName = workerNameInPopUp.getText();
                    TMWorkerRole = workerRole[1];
                    shiftDuration = shiftDurationInPopUp.getText();
                    assignTM(i);
                    verifySelectTeamMembersOption();
                    clickOnOfferOrAssignBtn();
                    saveSchedule();
                    counter = i;
                    break;
                }
            }
//            verifyChangedRoleShift(TMName, TMWorkerRole, shiftDuration,counter );

        }else {
            SimpleUtils.fail("shift images not loaded successfully", false);
        }
    }

	public void verifyActiveScheduleType() throws Exception{
	    if(isElementLoaded(scheduleType, 5)){
            if(scheduleType.getText().equalsIgnoreCase("SCHEDULE TYPE")){
                SimpleUtils.pass("Schedule Type Label is displaying Successfully on page!");
                getActiveScheduleType();
            }else{
                SimpleUtils.fail("Schedule Type Label not displaying on page Successfully!",false);
            }
        }
    }

    public void getActiveScheduleType(){
	    if(isElementEnabled(activScheduleType,5)){
	        if(activScheduleType.getText().equalsIgnoreCase("Manager")){
                SimpleUtils.pass("Schedule Type " + activScheduleType.getText() + " is enabled on page");
            }else{
                SimpleUtils.fail("Schedule Type " + activScheduleType.getText() + " is disabled",false);
            }
        }else{
            SimpleUtils.fail("Schedule Type " + scheduleTypeManager.getText() + " is disabled",false);
        }
    }


    //added by Nishant for DM Test cases

    @FindBy(css = "div.analytics-new-table-group")
    private List<WebElement> DMtableRowCount;

    @FindBy(xpath = "//div[contains(@class,'analytics-new-table-group-row')]//span/img/following-sibling::span")
    private List<WebElement> locationName;

    @FindBy(xpath = "//div[contains(@class,'analytics-new-table-group-row')]//div[@class='ng-scope col-fx-1']")
    private List<WebElement> DMHours;

    public List<Float> validateScheduleAndBudgetedHours() throws Exception {
        HashMap<String,List<String>> budgetHours = new HashMap<>();
        HashMap<String,List<String>> publishHours = new HashMap<>();
        HashMap<String,List<String>> clockHours = new HashMap<>();
        List<Float> totalHoursFromSchTbl = new ArrayList<>();
        List<String> budgetHrs = new ArrayList<>();
        List<String> publishedHrs = new ArrayList<>();
        List<String> clockedHrs = new ArrayList<>();
        int counter = 0;
        if(areListElementVisible(DMtableRowCount,10) && DMtableRowCount.size()!=0){
            for(int i=0; i<DMtableRowCount.size();i++){
                if(areListElementVisible(DMHours,10) && DMHours.size()!=0){
//                    SimpleUtils.report("Budget Hours for " + locationName.get(i).getText() + " is : " + DMHours.get(counter).getText());
//                    SimpleUtils.report("Publish Hours for " + locationName.get(i).getText() + " is : " + DMHours.get(counter+1).getText());
//                    SimpleUtils.report("Clocked Hours for " + locationName.get(i).getText() + " is : " + DMHours.get(counter+2).getText());
                    budgetHrs.add(DMHours.get(counter+1).getText());
                    publishedHrs.add(DMHours.get(counter+2).getText());
                    clockedHrs.add(DMHours.get(counter+3).getText());
                    budgetHours.put("Budgeted Hours",budgetHrs);
                    publishHours.put("Published Hours",publishedHrs);
                    clockHours.put("Clocked Hours",clockedHrs);
                    counter = (i + 1) * 4;
                }
            }
            Float totalBudgetHoursFromSchTbl = calculateTotalHoursFromScheduleTable(budgetHours);
            Float totalPublishedHoursFromSchTbl = calculateTotalHoursFromScheduleTable(publishHours);
            Float totalClockedHoursFromSchTbl = calculateTotalHoursFromScheduleTable(clockHours);
            totalHoursFromSchTbl.add(totalBudgetHoursFromSchTbl);
            totalHoursFromSchTbl.add(totalPublishedHoursFromSchTbl);
            totalHoursFromSchTbl.add(totalClockedHoursFromSchTbl);

        }else{
            SimpleUtils.fail("No data available on Schedule table in DM view",false);
        }

        return totalHoursFromSchTbl;
    }


    public Float calculateTotalHoursFromScheduleTable(HashMap<String,List<String>> hoursCalulationFromSchTbl){
        Float totalActualHours = 0.0f;
        Float totalActualHoursFromSchTbl = 0.0f;
        for (Map.Entry<String, List<String>> entry : hoursCalulationFromSchTbl.entrySet()) {
            String key = entry.getKey();

            List<String> value = entry.getValue();
            for(String aString : value){
                totalActualHours = Float.parseFloat(aString);
                totalActualHoursFromSchTbl = totalActualHoursFromSchTbl + totalActualHours;
            }
        }

        return totalActualHoursFromSchTbl;
    }

    @FindBy(css = "span.dms-box-item-number-small")
    private List<WebElement> hoursOnDashboardPage;
    @FindBy(css = "div.dms-box-item-title")
    private List<WebElement> titleOnDashboardPage;
    @FindBy(xpath = "//*[name()='svg']//*[name()='text' and @text-anchor='middle']")
    private List<WebElement> locationSummaryOnSchedule;
    @FindBy(xpath = "//*[name()='svg']//*[name()='text' and @text-anchor='end']")
    private List<WebElement> projectedOverBudget;
    @FindBy(xpath = "//*[name()='svg']//*[name()='text' and @text-anchor='start']")
    private List<WebElement> projectedUnderBudget;
    @FindBy(css = "span.dms-box-item-unit-trend")
    private WebElement projectedWithinOrOverBudget;
    @FindBy(css = "div.dashboard-time div.text-right")
    private WebElement dateOnDashboard;
    @FindBy(css = "ng-include[src*=LocationSummary] div.dms-box-title")
    private WebElement locationsSummaryTitleOnDashboard;
    @FindBy(css = "div.card-carousel-card-title")
    private WebElement locationsSummaryTitleOnSchedule;
    @FindBy(css = "span.dms-box-item-title.dms-box-item-title-color-dark")
    private List<WebElement> locationsSummarySmartCardOnDashboard;
    @FindBy(css = "div.day-week-picker-period-active")
    private WebElement dateOnSchedule;
    @FindBy(css = "div.published-clocked-cols-summary-title")
    private List<WebElement> locationsSummarySmartCardOnSchedule;

    //added by Gunjan
    @FindBy(xpath = "//div[contains(@class,'dms-row1')]//div[contains(text(),'View Schedule')]")
    private WebElement viewScheduleLinkInlocationsSummarySmartCardDashboard;
    @FindBy(xpath = "//div[contains(@class,'dms-row2')]//div[contains(text(),'View Schedule')]")
    private WebElement viewScheduleLinkInPayRollProjectionSmartCardDashboard;
    @FindBy(css = "div.card-carousel-card.card-carousel-card-primary")
    private WebElement locationSummarySmartCardOnSchedule;
    @FindBy(css = "div.analytics-new-table-group-row-open div.analytics-new-table-group-row-action")
    private List<WebElement> DMtoSMNavigationArrow;
    @FindBy(css = "lg-select[search-hint='Search District'] input-field[class='picker-input ng-isolate-scope'] div.input-faked")
    private WebElement selectedDistrictSMView;
    @FindBy(css="lg-select[search-hint='Search Location']  input-field[class='picker-input ng-isolate-scope']  div.input-faked")
    private WebElement selectedLocationSMView;
    @FindBy(css = "[search-hint=\"Search District\"] div.lg-search-options")
    private WebElement districtDropDownButton;
    @FindBy(css = "div.lg-search-options__option")
    private List<WebElement> availableLocationCardsName;


    public void districtSelectionSMView(String districtName) throws Exception {
        waitForSeconds(4);
        try {
            Boolean isDistrictMatched = false;
            if (isElementLoaded(selectedDistrictSMView)) {
                click(selectedDistrictSMView);
                if (isElementLoaded(districtDropDownButton)) {
                    if (availableLocationCardsName.size() != 0) {
                        for (WebElement locationCardName : availableLocationCardsName) {
                            if (locationCardName.getText().contains(districtName)) {
                                isDistrictMatched = true;
                                click(locationCardName);
                                SimpleUtils.pass("District '" + districtName + " selected successfully");
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Added by Nora
    @FindBy (css = "lg-button-group[buttons*=\"custom\"] div.lg-button-group-first")
    private WebElement scheduleDayViewButton;
    @FindBy (className = "period-name")
    private WebElement periodName;
    @FindBy (className = "card-carousel-container")
    private WebElement cardCarousel;
    @FindBy(css = "table tr:nth-child(1)")
    private WebElement scheduleTableTitle;
    @FindBy(css = "table tr:nth-child(2)")
    private WebElement scheduleTableHours;
    @FindBy(className = "week-day-multi-picker-day")
    private List<WebElement> weekDays;
    @FindBy(css = "div[ng-if=\"!searchLoading\"] [ng-class*=\"selectActionClass\"]")
    private WebElement selectButton;
    @FindBy(css = "img[src*=\"added-shift\"]")
    private List<WebElement> addedShiftIcons;
    @FindBy(css = "[label=\"Create New Shift\"]")
    private WebElement createNewShiftWeekView;
    @FindBy(className = "week-schedule-shift-wrapper")
    private List<WebElement> shiftsWeekView;
    @FindBy(css = "div.popover div:nth-child(3)>div.ng-binding")
    private WebElement timeDuration;

    @Override
    public void verifyShiftsChangeToOpenAfterTerminating(List<Integer> indexes, String name, String currentTime) throws Exception {
        String open = "Open";
        String unAssigned = "Unassigned";
        String shiftTime = null;
        if (indexes.size() > 0 && areListElementVisible(shiftsWeekView, 5)) {
            for (int index : indexes) {
                WebElement workerName = shiftsWeekView.get(index).findElement(By.className("week-schedule-worker-name"));
                if (workerName != null) {
                    if (workerName.getText().contains(name)) {
                        shiftTime = shiftsWeekView.get(index).findElement(By.className("week-schedule-shift-time")).getText();
                        boolean isConvertToOpen = compareShiftTimeWithCurrentTime(shiftTime, currentTime);
                        SimpleUtils.report("IsConvertToOpen: " + isConvertToOpen + " index is: " + index);
                        if (!isConvertToOpen) {
                            SimpleUtils.pass("Shift isn't change to open or unassigned since the current is earlier than the shift end time!");
                        }else {
                            SimpleUtils.fail("Shift doesn't change to open or unassigned, worker name is: " + workerName.getText(), true);
                        }
                    }else if (workerName.getText().equalsIgnoreCase(open) || workerName.getText().equalsIgnoreCase(unAssigned)) {
                        SimpleUtils.report("Index is: " + index);
                        SimpleUtils.pass("Shift is changed to open or unassigned!");
                    }else {
                        SimpleUtils.fail("Shift doesn't change to open or unassigned, worker name is: " + workerName.getText(), true);
                    }
                }else {
                    SimpleUtils.fail("Failed to find the worker name element!", true);
                }
            }
        }else {
            SimpleUtils.fail("Shifts on week view failed to load!", false);
        }
    }

    public boolean compareShiftTimeWithCurrentTime(String shiftTime, String currentTime) {
        boolean isConvertToOpen = false;
        int shiftStartMinutes = 0;
        int currentMinutes = 0;
        String[] startAndEndTime = shiftTime.split("-");
        if (startAndEndTime.length == 2) {
            String startTime = startAndEndTime[0].trim();
            shiftStartMinutes = getMinutesFromTime(startTime);
            currentMinutes = getMinutesFromTime(currentTime);
            SimpleUtils.report(startTime);
            SimpleUtils.report("Convert start time to Minute: " + shiftStartMinutes);
            SimpleUtils.report(currentTime);
            SimpleUtils.report("Convert current time to Minute: " + currentMinutes);
        }
        if (currentMinutes < shiftStartMinutes) {
            isConvertToOpen = true;
        }
        return false;
    }

    public int getMinutesFromTime(String time) {
        int minutes = 0;
        if (time.contains(":")) {
            String minute = time.split(":")[1].substring(0, time.split(":")[1].length()-2).trim();
            minutes = (Integer.parseInt(time.split(":")[0].trim())) * 60 + Integer.parseInt(minute);
        }else {
            minutes = Integer.parseInt(time.substring(0, time.length()-2)) * 60;
        }
        if (time.toLowerCase().endsWith("pm")) {
            minutes += 12 * 60;
        }
        return minutes;
    }

    @Override
    public void isScheduleForCurrentDayInDayView(String dateFromDashboard) throws Exception {
        String tagName = "span";
        if (isElementLoaded(scheduleDayViewButton, 5) && isElementLoaded(periodName, 5)) {
            if (scheduleDayViewButton.getAttribute("class").contains("lg-button-group-selected")){
                SimpleUtils.pass("The Schedule Day View Button is selected!");
            }else{
                SimpleUtils.fail("The Schedule Day View Button isn't selected!", true);
            }
            /*
             * @periodName format "Schedule for Wednesday, February 12"
             */
            if (periodName.getText().contains(dateFromDashboard)) {
                SimpleUtils.pass("The Schedule is for current day!");
            }else{
                SimpleUtils.fail("The Schedule isn't for current day!", true);
            }
        }else{
            SimpleUtils.fail("The Schedule Day View Button isn't loaded!",true);
        }
    }

    @Override
    public HashMap<String, String> getHoursFromSchedulePage() throws Exception {
        // Wait for the hours to load
        waitForSeconds(5);
        HashMap<String, String> scheduleHours = new HashMap<>();
            if (isElementLoaded(scheduleTableTitle, 5) && isElementLoaded(scheduleTableHours, 5)) {
                List<WebElement> titles = scheduleTableTitle.findElements(By.tagName("th"));
                List<WebElement> hours = scheduleTableHours.findElements(By.tagName("td"));
                if (titles != null && hours != null) {
                    if (titles.size() == 4 && hours.size() == 4) {
                        for (int i = 1; i < titles.size(); i++) {
                            scheduleHours.put(titles.get(i).getText(), hours.get(i).getText());
                            SimpleUtils.pass("Get Title: " + titles.get(i).getText() + " and related Hours: " +
                                    hours.get(i).getText());
                        }
                    }
                } else {
                    SimpleUtils.fail("Failed to find the Schedule table elementes!", true);
                }
            }else {
                SimpleUtils.fail("Elements are not Loaded!", true);
            }
        return scheduleHours;
    }

    @Override
    public HashMap<String, String> getFourUpComingShifts(boolean isStartTomorrow, String currentTime) throws Exception {
        HashMap<String, String> fourShifts = new HashMap<>();
        String activeDay = null;
        if (isStartTomorrow) {
            activeDay = getActiveAndNextDay();
            clickOnNextDaySchedule(activeDay);
            fourShifts = getAvailableShiftsForDayView(fourShifts);
        }else {
            fourShifts = getShiftsForCurrentDayIfStartingSoon(fourShifts, currentTime);
        }
        while (fourShifts.size() < 4) {
            activeDay = getActiveAndNextDay();
            clickOnNextDaySchedule(activeDay);
            fourShifts = getAvailableShiftsForDayView(fourShifts);
        }
        if (fourShifts.size() == 4) {
            SimpleUtils.pass("Get four shifts successfully!");
        }else {
            SimpleUtils.fail("Failed to get four shifts!", false);
        }
        return fourShifts;
    }

    @Override
    public void selectDaysFromCurrentDay(String currentDay) throws Exception {
        int index = 7;
        String[] items = currentDay.split(" ");
        if (items.length == 3) {
            currentDay = items[0].substring(0, 3) + items[1].substring(0, 3) + (items[2].length() == 1 ? "0"+items[2] : items[2]);
        }
        if (areListElementVisible(weekDays, 5) && weekDays.size() == 7) {
            for (int i = 0; i < weekDays.size(); i++) {
                String weekDay = weekDays.get(i).getText().replaceAll("\\s*", "");
                if (weekDay.equalsIgnoreCase(currentDay)) {
                    index = i;
                }
                if (i >= index) {
                    if (!weekDays.get(i).getAttribute("class").contains("week-day-multi-picker-day-selected")) {
                        click(weekDays.get(i));
                        SimpleUtils.pass("Select the day: " + weekDays.get(i).getText() + " successfully!");
                    }
                }
            }
        }else{
            SimpleUtils.fail("Weeks Days failed to load!", true);
        }
    }

    @Override
    public void searchTeamMemberByName(String name) throws Exception {
        if(areListElementVisible(btnSearchteamMember,5)) {
            if (btnSearchteamMember.size() == 2) {
                click(btnSearchteamMember.get(1));
                if (isElementLoaded(textSearch, 5) && isElementLoaded(searchIcon, 5)) {
                    textSearch.sendKeys(name);
                    click(searchIcon);
                    if (isElementLoaded(selectButton, 5) && isElementEnabled(selectButton, 5)) {
                        click(selectButton);
                    }else {
                        SimpleUtils.fail("Failed to find the team member!", false);
                    }
                }else {
                    SimpleUtils.fail("Search text not editable and icon are not clickable", false);
                }
            }else {
                SimpleUtils.fail("Search team member should have two tabs, failed to load!", false);
            }
        }
    }

    @Override
    public void verifyNewShiftsAreShownOnSchedule(String name) throws Exception {
        boolean isFound = false;
        if (areListElementVisible(addedShiftIcons, 5)) {
            for (WebElement addedShiftIcon : addedShiftIcons) {
                WebElement parent = addedShiftIcon.findElement(By.xpath("./../../../.."));
                if (parent != null) {
                    WebElement teamMemberName = parent.findElement(By.className("week-schedule-worker-name"));
                    if (teamMemberName != null && teamMemberName.getText().contains(name)) {
                        isFound = true;
                        SimpleUtils.pass("Added a New shift for: " + name + " is successful!");
                    }
                }else {
                    SimpleUtils.fail("Failed to find the parent element for adding icon!", false);
                }
            }
        }else {
            SimpleUtils.fail("Failed to find the new added shift icons!", false);
        }
        if (!isFound) {
            SimpleUtils.fail("Cannot find the new shift for team member: " + name, true);
        }
    }

    @Override
    public List<Integer> getAddedShiftIndexes(String name) throws Exception {
        // Wait for the shifts to be loaded
        waitForSeconds(3);
        List<Integer> indexes = new ArrayList<>();
        if (areListElementVisible(shiftsWeekView, 5)) {
            for (int i = 0; i < shiftsWeekView.size(); i++) {
                WebElement workerName = shiftsWeekView.get(i).findElement(By.className("week-schedule-worker-name"));
                if (workerName != null) {
                    if (workerName.getText().contains(name)) {
                        indexes.add(i);
                        SimpleUtils.pass("Get the index: " + i + " successfully!");
                    }
                }
            }
        }
        if (indexes.size() == 0) {
            SimpleUtils.fail("Failed to get the index of the newly added shifts!", true);
        }
        return indexes;
    }

    public HashMap<String, String> getAvailableShiftsForDayView(HashMap<String, String> fourShifts) throws Exception {
        String name = null;
        String role = null;
        if (areListElementVisible(dayViewAvailableShifts, 15)) {
            for (WebElement dayViewAvailableShift : dayViewAvailableShifts) {
                name = dayViewAvailableShift.findElement(By.className("sch-day-view-shift-worker-name")).getText().toLowerCase();
                if (name.contains("(")) {
                    name = name.substring(0, name.indexOf("(") - 1);
                }
                if (!name.contains("open") && !name.contains("unassigned")) {
                    role = dayViewAvailableShift.findElement(By.className("sch-day-view-shift-worker-title-role")).getText().toLowerCase();
                    if (fourShifts.size() < 4) {
                        fourShifts.put(name, role);
                    }
                }
                if (fourShifts.size() >= 4) {
                    break;
                }
            }
        } else {
            SimpleUtils.fail("Day View Available shifts failed to load!", true);
        }
        return fourShifts;
    }

    public HashMap<String, String> getShiftsForCurrentDayIfStartingSoon(HashMap<String, String> fourShifts, String currentTime) throws Exception {
        String name = null;
        String role = null;
        if (areListElementVisible(dayViewAvailableShifts, 15)) {
            for (WebElement dayViewAvailableShift : dayViewAvailableShifts) {
                WebElement hoverInfo = dayViewAvailableShift.findElement(By.className("day-view-shift-hover-info-icon"));
                if (hoverInfo != null) {
                    click(hoverInfo);
                    if (isElementLoaded(timeDuration, 5)) {
                        String startTime = timeDuration.getText().split("-")[0];
                        click(hoverInfo);
                        int shiftStartMinutes = getMinutesFromTime(startTime);
                        int currentMinutes = getMinutesFromTime(currentTime);
                        if (shiftStartMinutes > currentMinutes) {
                            name = dayViewAvailableShift.findElement(By.className("sch-day-view-shift-worker-name")).getText().toLowerCase();
                            if (name.contains("(")) {
                                name = name.substring(0, name.indexOf("(") - 1);
                            }
                            if (!name.contains("open") && !name.contains("unassigned")) {
                                role = dayViewAvailableShift.findElement(By.className("sch-day-view-shift-worker-title-role")).getText().toLowerCase();
                                if (fourShifts.size() < 4) {
                                    fourShifts.put(name, role);
                                }
                            }
                            if (fourShifts.size() >= 4) {
                                break;
                            }
                        }
                    }else {
                        SimpleUtils.fail("Failed to get the time duration!", true);
                    }
                }else {
                    SimpleUtils.fail("Failed to get the hover info element!", true);
                }
            }
        }else {
            SimpleUtils.fail("Day View Available shifts failed to load!", true);
        }
        return fourShifts;
    }

    @Override
    public void verifyUpComingShiftsConsistentWithSchedule(HashMap<String, String> dashboardShifts, HashMap<String, String> scheduleShifts) throws Exception {
        boolean isConsistent = SimpleUtils.compareHashMapByEntrySet(dashboardShifts, scheduleShifts);
        if (isConsistent) {
            SimpleUtils.pass("Up coming shifts from dashboard is consistent with the shifts in schedule!");
        }else {
            SimpleUtils.fail("Up coming shifts from dashboard isn't consistent with the shifts in schedule!", true);
        }
    }

    @Override
    public void clickOnCreateNewShiftWeekView() throws Exception {
        if (isElementLoaded(createNewShiftWeekView, 5)) {
            click(createNewShiftWeekView);
            SimpleUtils.pass("Click on Create New Shift button successfully!");
        }else {
            SimpleUtils.fail("Create New Shift button failed to load on Week View!", false);
        }
    }

    @Override
    public void verifyTeamCount(List<String> previousTeamCount, List<String> currentTeamCount) throws Exception {
        if (previousTeamCount.size() == currentTeamCount.size()) {
            for (int i = 0; i < currentTeamCount.size(); i++) {
                String currentCount = currentTeamCount.get(i);
                String previousCount = previousTeamCount.get(i);
                if (Integer.parseInt(currentCount) == Integer.parseInt(previousCount) + 1) {
                    SimpleUtils.pass("Current Team Count is greater than Previous Team Count");
                } else {
                    SimpleUtils.fail("Current Team Count is not greater than Previous Team Count", true);
                }
            }
        } else {
            SimpleUtils.fail("Size of Current Team Count should be equal to Previous Team Count", false);
        }
    }

    @Override
    public void printButtonIsClickable() throws Exception {
        if (isElementLoaded(printButton,10)){
            scrollToTop();
            click(printButton);
            if(isElementLoaded(printButtonInPrintLayout)) {
                SimpleUtils.pass("Print button is  clickable");
            }else {
                SimpleUtils.fail("Print button is not  clickable",true);
            }
        }else{
            SimpleUtils.fail("there is no print button",true);
        }
    }

    @Override
    public void todoButtonIsClickable() throws Exception {
        if(isElementLoaded(todoButton,10)) {
            scrollToTop();
            click(todoButton);
            if(isElementLoaded(todoSmartCard,5)) {
                SimpleUtils.pass("Todo button is  clickable");
            }else {
                SimpleUtils.fail("click todo button failed",true);
            }
        }else {
            SimpleUtils.fail("there is no todo button", true);
        }
    }

    @Override
    public void closeButtonIsClickable() {
        getDriver().close();
        SimpleUtils.pass("close button is clickable");
    }

    @Override
    public void legionButtonIsClickableAndHasNoEditButton() throws Exception {
        clickOnSuggestedButton();
        if(!isElementLoaded(edit,5)){
            SimpleUtils.pass("Legion schedule has no edit button");
        }else{
            SimpleUtils.fail("it's not in legion schedule page", true);
        }
    }


    public void clickOnViewScheduleLocationSummaryDMViewDashboard() {
        if (isElementEnabled(viewScheduleLinkInlocationsSummarySmartCardDashboard, 2)) {
            click(viewScheduleLinkInlocationsSummarySmartCardDashboard);
            SimpleUtils.pass("'View Schedule' link in location summary smartcard Loaded Successfully!");
        } else {
            SimpleUtils.fail("'View Schedule' link in location summary smartcard not Loaded!", false);
        }
    }

    public void clickOnViewSchedulePayrollProjectionDMViewDashboard() {
        if (isElementEnabled(viewScheduleLinkInPayRollProjectionSmartCardDashboard, 2)) {
            click(viewScheduleLinkInPayRollProjectionSmartCardDashboard);
            SimpleUtils.pass("'View Schedule' link in PayRoll Projection smartcard Loaded Successfully!");
        } else {
            SimpleUtils.fail("'View Schedule' link in PayRoll Projection smartcard not Loaded!", false);
        }
    }


    public void loadingOfDMViewSchedulePage(String SelectedWeek) throws Exception {
        if(isElementLoaded(locationSummarySmartCardOnSchedule, 2)){
            SimpleUtils.pass("'Location Summary' smartcard on DM View Schedule Page Loaded Successfully! for week "+ SelectedWeek);
        } else {
            SimpleUtils.fail("'Location Summary' smartcard on DM View Schedule Page not Loaded! for week "  + SelectedWeek, true);
        }

        if(DMtableRowCount.size()>0){
            SimpleUtils.pass("Locations Table on DM View Schedule Page Loaded Successfully! for week " +SelectedWeek );
        } else {
            SimpleUtils.fail("Location Table on DM View Schedule Page not Loaded! for week " + SelectedWeek, true);
        }
    }

    public void validateCorrectnessOfDMToSMNavigation(String locationToSelect, String districtName, String selectedWeek, String selectedDistrict, String selectedLocation, String activeWeekSMView) throws Exception {
        if(selectedDistrict.equalsIgnoreCase(districtName) && selectedLocation.equalsIgnoreCase(locationToSelect)){
            SimpleUtils.pass("Navigation from DM to SM View Works fine. " + "\n"
                    + "Expected selection of District from DM view i.e. " + districtName + " matches the selection in SM View i.e. " + selectedDistrict + ". \n"
                    + "Expected selection of Location from DM view i.e. " + locationToSelect + " matches the selection in SM View i.e. " + selectedLocation + ".");
            if(compareDMAndSMViewDatePickerText(selectedWeek) == true) {
                if (areListElementVisible(carouselCards,10,1)) {
                    SimpleUtils.pass("Smartcard in SM Schedule loaded successfully! for selected week i.e " + selectedWeek);
                } else {
                    SimpleUtils.fail("Smartcard in SM Schedule not loaded successfully! for selected week i.e " + selectedWeek, true);
                }
                if (areListElementVisible(shiftsOnScheduleView,10,1)) {
                    SimpleUtils.pass("SM Schedule table loaded successfully! for selected week i.e " + selectedWeek);
                } else {
                    SimpleUtils.fail("SM Schedule table not loaded successfully! for selected week i.e " + selectedWeek, true);
                }
            }else{
                SimpleUtils.fail("Wrong week selected in SM View, expected week is " +selectedWeek + " and selected week is "+activeWeekSMView, true);
            }
        }else{
            SimpleUtils.fail("Navigation from DM to SM View is not correct. " + " \n"
                    + "Expected selection of District from DM view i.e. " + districtName + " doesn't match the selection in SM View i.e. " + selectedDistrict + ". \n"
                    + "Expected selection of Location from DM view i.e. " + locationToSelect + " doesn't match the selection in SM View i.e. " + selectedLocation + ". ", true);
        }
    }


    public void checkNavDMtoSMScheduleNSMScheduleLoading(String locationToSelect, String districtName, String selectedWeek) throws Exception {
        String selectedDistrict = null;
        String selectedLocation = null;
        String activeWeekSMView = null;
        if (areListElementVisible(DMtableRowCount, 3) && DMtableRowCount.size() != 0) {
            for (int i = 0; i < DMtableRowCount.size(); i++) {
                if (DMtableRowCount.get(i).getText().contains(locationToSelect)) {
                    DMtoSMNavigationArrow.get(i).click();
                    selectedDistrict = selectedDistrictSMView.getText();
                    selectedLocation = selectedLocationSMView.getText();
                    activeWeekSMView = getActiveWeekText();
                    validateCorrectnessOfDMToSMNavigation(locationToSelect, districtName, selectedWeek, selectedDistrict, selectedLocation, activeWeekSMView);
//                    selectedDistrictSMView.click();
                    districtSelectionSMView(districtName);
                    if(compareDMAndSMViewDatePickerText(activeWeekSMView) == true){
                        SimpleUtils.pass("Backward navigation from SM to DM view is working fine, week selected in SM View " + activeWeekSMView + " , active week in DM view on backward navigation is " + daypicker.getText().replace("\n"," "));
                    }else{
                        SimpleUtils.fail("Backward navigation from SM to DM view is not correct, week selected in SM View is " + activeWeekSMView + " , active week in DM view on backward navigation is " + daypicker.getText().replace("\n"," "), true);
                    }
                }
            }
        }
    }

    @Override
    public void toNFroNavigationFromDMToSMSchedule(String CurrentWeek, String locationToSelectFromDMViewSchedule, String districtName, String nextWeekViewOrPreviousWeekView) throws Exception {
        String weekSelected = null;
        loadingOfDMViewSchedulePage(CurrentWeek);
        checkNavDMtoSMScheduleNSMScheduleLoading(locationToSelectFromDMViewSchedule, districtName, CurrentWeek);
        if (nextWeekViewOrPreviousWeekView.toLowerCase().contains("next") || nextWeekViewOrPreviousWeekView.toLowerCase().contains("future")) {
            clickImmediateNextToCurrentActiveWeekInDayPicker();
            weekSelected = daypicker.getText().replace("\n", " ");
            checkNavDMtoSMScheduleNSMScheduleLoading(locationToSelectFromDMViewSchedule, districtName, weekSelected);
        } else {
            clickImmediatePastToCurrentActiveWeekInDayPicker();
            weekSelected = daypicker.getText().replace("\n", " ");
            checkNavDMtoSMScheduleNSMScheduleLoading(locationToSelectFromDMViewSchedule, districtName, weekSelected);
        }

    }

    public void toNFroNavigationFromDMDashboardToDMSchedule(String CurrentWeek) throws Exception{
        String weekSelected = null;
        loadingOfDMViewSchedulePage(CurrentWeek);
    }



    public void compareHoursFromScheduleAndDashboardPage(List<Float> totalHoursFromSchTbl) throws Exception{

        List<Float> totalHoursFromDashboardTbl = new ArrayList<>();
        if(areListElementVisible(hoursOnDashboardPage,10) && hoursOnDashboardPage.size()!=0){
            for(int i =0; i < hoursOnDashboardPage.size();i++){
                totalHoursFromDashboardTbl.add(Float.parseFloat(hoursOnDashboardPage.get(i).getText()));
            }
            for(int j=0; j < totalHoursFromSchTbl.size();j++){
                if(totalHoursFromSchTbl.get(j).equals(totalHoursFromDashboardTbl.get(j))){
                    SimpleUtils.pass(titleOnDashboardPage.get(j).getText() +
                            " Hours from Dashboard page " + totalHoursFromDashboardTbl.get(j)
                            + " matching with the hours present on Schedule Page " + totalHoursFromSchTbl.get(j));
                }else{
                    SimpleUtils.fail(titleOnDashboardPage.get(j).getText() +
                            " Hours from Dashboard page " + totalHoursFromDashboardTbl.get(j)
                            + " not matching with the hours present on Schedule Page " + totalHoursFromSchTbl.get(j),true);
                }
            }
        }else{
            SimpleUtils.fail("No data available for Hours on Dashboard page in DM view",false);
        }
    }


    public List<Float> getHoursOnLocationSummarySmartCard() throws Exception{
        List<Float> totalHoursFromDashboardTbl = new ArrayList<>();
        if(areListElementVisible(locationSummaryOnSchedule,10) && locationSummaryOnSchedule.size()!=0){
            totalHoursFromDashboardTbl.add(Float.parseFloat(locationSummaryOnSchedule.get(0).getText()));
            totalHoursFromDashboardTbl.add(Float.parseFloat(locationSummaryOnSchedule.get(2).getText()));
            totalHoursFromDashboardTbl.add(Float.parseFloat(locationSummaryOnSchedule.get(6).getText()));
        }else{
            SimpleUtils.fail("No data available on Location Summary section Smart Card in DM view",false);
        }
        return totalHoursFromDashboardTbl;
    }


    public void compareHoursFromScheduleSmartCardAndDashboardSmartCard(List<Float> totalHoursFromSchTbl) throws Exception{

        List<Float> totalHoursFromDashboardTbl = new ArrayList<>();
        if(areListElementVisible(hoursOnDashboardPage,10) && hoursOnDashboardPage.size()!=0){
            for(int i =0; i < hoursOnDashboardPage.size();i++){
                totalHoursFromDashboardTbl.add(Float.parseFloat(hoursOnDashboardPage.get(i).getText()));
            }
            for(int j=0; j < totalHoursFromSchTbl.size();j++){
                if(totalHoursFromSchTbl.get(j).equals(totalHoursFromDashboardTbl.get(j))){
                    SimpleUtils.pass(titleOnDashboardPage.get(j).getText() +
                            " Hours from Dashboard page " + totalHoursFromDashboardTbl.get(j)
                            + " matching with the hours present on Schedule Page " + totalHoursFromSchTbl.get(j));
                }
            }
        }else{
            SimpleUtils.fail("No data available for Hours on Dashboard page in DM view",false);
        }
    }


    public int getProjectedUnderBudget(){
        int totalCountProjectedUnderBudget = 0;
        if(areListElementVisible(projectedUnderBudget,10) && projectedUnderBudget.size()!=0){
            for(int i=0;i<projectedUnderBudget.size();i++){
                int countProjectedUnderBudget = Integer.parseInt(projectedUnderBudget.get(i).getText());
                totalCountProjectedUnderBudget = totalCountProjectedUnderBudget + countProjectedUnderBudget;
            }
        }else{
            SimpleUtils.fail("No data available for Projected Under Budget section on location specific date in DM view",false);
        }
        return totalCountProjectedUnderBudget;
    }


    public int getProjectedOverBudget(){
        int totalCountProjectedOverBudget = 0;
        if(areListElementVisible(projectedOverBudget,10) && projectedOverBudget.size()!=0){
            for(int i=0;i<projectedOverBudget.size();i++){
                int countProjectedOverBudget = Integer.parseInt(projectedOverBudget.get(i).getText());
                totalCountProjectedOverBudget = totalCountProjectedOverBudget + countProjectedOverBudget;
            }
        }else{
            SimpleUtils.fail("No data available for Projected Over Budget section on location specific date in DM view",false);
        }
        return totalCountProjectedOverBudget;
    }

    public void compareProjectedWithinBudget(int totalCountProjectedOverBudget) throws Exception{
        if(isElementLoaded(projectedWithinOrOverBudget,10)){
            String ProjectedWithinOrOverBudget = (projectedWithinOrOverBudget.getText().split(" "))[0];
            if(totalCountProjectedOverBudget == Integer.parseInt(ProjectedWithinOrOverBudget)){
                SimpleUtils.pass("Count of Projected Over/Under Budget on Dashboard page" +
                        " " + Integer.parseInt(ProjectedWithinOrOverBudget) + " is same as Schedule page " + totalCountProjectedOverBudget);
            }else{
                SimpleUtils.fail("Count of Projected Over/Under Budget on Dashboard page" +
                        " " + Integer.parseInt(ProjectedWithinOrOverBudget) + " not matching with Schedule page " + totalCountProjectedOverBudget,false);
            }
        }else{
            SimpleUtils.fail("No data available for Projected Over/Under Budget section on Dashboard in DM view",false);
        }

    }

    public String getDateFromDashboard() throws Exception {
        String DateOnDashboard = null;
        if(isElementLoaded(dateOnDashboard,10)){
            DateOnDashboard = dateOnDashboard.getText().substring(8);
        }else{
            SimpleUtils.fail("Week Date not available on Dashboard in DM view",false);
        }

        return DateOnDashboard;
    }
    @FindBy(css = "div.console-navigation-item.active")
    private WebElement activeConsoleMenuItem;
    public void compareDashboardAndScheduleWeekDate(String DateOnSchdeule, String DateOnDashboard) throws Exception {
        activeConsoleName = activeConsoleMenuItem.getText();
        String splitFirstDate = null;
        String splitSecondDate = null;
        String strDateOnSchedule = DateOnSchdeule.substring(9).trim();
        String[] splitDateOnSchedule = strDateOnSchedule.split(" ");
        if(splitDateOnSchedule[1].length()>1){
            splitFirstDate = splitDateOnSchedule[1];
        }else{
            splitFirstDate = "0" + splitDateOnSchedule[1];
        }
        if(splitDateOnSchedule[4].length()>1){
            splitSecondDate = splitDateOnSchedule[4];
        }else{
            splitSecondDate = "0" + splitDateOnSchedule[4];
        }

        String actualDateOnSchedule = splitDateOnSchedule[0] + " " + splitFirstDate
                + " " + splitDateOnSchedule[2] + " " + splitDateOnSchedule[3] + " " + splitSecondDate;

        if(actualDateOnSchedule.equals(DateOnDashboard)){
            SimpleUtils.pass("Week Date on Dashboard " + DateOnDashboard + " matching with DM view of " + activeConsoleName + " date " + actualDateOnSchedule);
        }else{
            SimpleUtils.fail("Week Date on Dashboard " + DateOnDashboard + " not matching with Schedule date " + actualDateOnSchedule,true);
        }

    }


    public List<String> getLocationSummaryDataFromDashBoard() throws Exception{
        String locationSummaryTitleOnDashboard = null;
        List<String> ListLocationSummaryOnDashboard = new ArrayList<>();
        if(isElementLoaded(locationsSummaryTitleOnDashboard, 10)){
            locationSummaryTitleOnDashboard = locationsSummaryTitleOnDashboard.getText();
            ListLocationSummaryOnDashboard.add(locationSummaryTitleOnDashboard);
        }else{
            SimpleUtils.fail("Location Summary Title not available on Dashboard Page", true);
        }

        if(areListElementVisible(locationsSummarySmartCardOnDashboard,10) && locationsSummarySmartCardOnDashboard.size()!=0){
            for(int i =0; i< locationsSummarySmartCardOnDashboard.size();i++){
                ListLocationSummaryOnDashboard.add(locationsSummarySmartCardOnDashboard.get(i).getText());
            }
        }else{
            SimpleUtils.fail("Location Summary Smart Card not available on Dashboard Page", true);
        }

        return ListLocationSummaryOnDashboard;
    }


    public List<String> getLocationSummaryDataFromSchedulePage() throws Exception{
        String locationSummaryTitleOnSchedule = null;
        List<String> ListLocationSummaryOnSchedule = new ArrayList<>();
        if(isElementLoaded(locationsSummaryTitleOnSchedule, 10)){
            locationSummaryTitleOnSchedule = locationsSummaryTitleOnSchedule.getText();
            ListLocationSummaryOnSchedule.add(locationSummaryTitleOnSchedule);
        }else{
            SimpleUtils.fail("Location Summary Title not available on Dashboard Page", true);
        }

        if(areListElementVisible(locationsSummarySmartCardOnSchedule,10) && locationsSummarySmartCardOnSchedule.size()!=0){
            for(int i =0; i< locationsSummarySmartCardOnSchedule.size();i++){
                ListLocationSummaryOnSchedule.add(locationsSummarySmartCardOnSchedule.get(i).getText());
            }
        }else{
            SimpleUtils.fail("Location Summary Smart Card not available on Dashboard Page", true);
        }

        return ListLocationSummaryOnSchedule;
    }


    public void compareLocationSummaryFromDashboardAndSchedule(List<String> ListLocationSummaryOnDashboard, List<String> ListLocationSummaryOnSchedule){
        for(int i=0; i<ListLocationSummaryOnDashboard.size();i++){
            if(ListLocationSummaryOnDashboard.get(i).equalsIgnoreCase(ListLocationSummaryOnSchedule.get(i))){
                SimpleUtils.pass("Location Summary on Dashboard "
                        + ListLocationSummaryOnDashboard.get(i) + " matches with location" +
                        " summary on Schedule page " +ListLocationSummaryOnSchedule.get(i));
            }else{
                SimpleUtils.fail("Location Summary on Dashboard "
                        + ListLocationSummaryOnDashboard.get(i) + " matches with location" +
                        " summary on Schedule page " +ListLocationSummaryOnSchedule.get(i),true);
            }
        }
    }

    @FindBy(css = "div.edit-budget span.header-text")
    private List<WebElement> tblBudgetRow;
    @FindBy(css = "span[ng-if='canEditWages(budget)'] span")
    private List<WebElement> editListWagesHrs;

    public void openBudgetPopUpGenerateSchedule() throws Exception{
        if(isElementEnabled(btnGenerateBudgetPopUP,5)){
            click(btnGenerateBudgetPopUP);
        }else{
            SimpleUtils.fail("Generate btn not clickable on Budget pop up", false);
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
            }
        }
    }

    @FindBy(css = "input[ng-class='hoursFieldClass(budget)']")
    private List<WebElement> inputHrs;
    @FindBy(css = "tr.table-row.ng-scope")
    private List<WebElement> budgetTableRow;

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

    public void updatebudgetInScheduleNBudgetSmartCard(String nextWeekView, int weekCount) {
        // TODO Auto-generated method stub
        waitForSeconds(3);
        for(int i = 0; i < weekCount; i++)
        {
            float totalBudgetedHourForBudgetSmartCard=0.0f;
            float totalBudgetHourforScheduleSmartcardIfBudgetEntered=0.0f;
            float totalBudgetedWagesForBudgetSmartCard=0.0f;
            float totalScheduledWagesIfBudgetEntered=0.0f;
            if(nextWeekView.toLowerCase().contains("next") || nextWeekView.toLowerCase().contains("future"))
            {
                try {
                    if(isElementLoaded(schedulesForWeekOnOverview.get(0))) {
                        waitForSeconds(3);
                        click(schedulesForWeekOnOverview.get(i));
                        waitForSeconds(4);
                        String[] daypickers = daypicker.getText().split("\n");
                        String valueOfBudgetSmartcardWhenNoBudgetEntered = budgetOnbudgetSmartCardWhenNoBudgetEntered.getText();
                        String[] budgetDisplayOnBudgetSmartcard = budgetOnbudgetSmartCard.getText().split(" ");
                        String budgetDisplayOnSmartCardWhenByWages = budgetOnbudgetSmartCard.getText().substring(1);
                        String budgetDisplayOnBudgetSmartCardByHours = budgetDisplayOnBudgetSmartcard[0];
                        String budgetOnScheduleSmartcard = budgetDisplayOnScheduleSmartcard.get(0).getText();
                        String budgetedWagesOnScheduleSmartcard = budgetDisplayOnScheduleSmartcard.get(1).getText().substring(1).replace(",","");
                        String weekDuration = daypickers[1];
                        if (verifyNoBudgetAvailableForWeek(valueOfBudgetSmartcardWhenNoBudgetEntered, weekDuration) == false) {
                            click(enterBudgetLink);
                            waitForSeconds(3);
                            if(areListElementVisible(editBudgetHrs,5)){
                                fillBudgetValues(editBudgetHrs);
                                compareBudgetValueForScheduleAndBudgetSmartCardWhenBudgetByHour(weekDuration);
                            }else if(areListElementVisible(editWagesHrs,5)){
                                fillBudgetValues(editWagesHrs);
                                compareBudgetValueForScheduleAndBudgetSmartCardWhenBudgetByWages(weekDuration);
                            }

                        }
                    }
                }
                catch (Exception e) {
                    SimpleUtils.fail("Budget pop-up not opening ",false);
                }
            }
        }
    }


    public void compareBudgetValueForScheduleAndBudgetSmartCardWhenBudgetByHour(String weekDuration){
        float totalBudgetedHourForBudgetSmartCard=0.0f;
        float totalBudgetHourforScheduleSmartcardIfBudgetEntered=0.0f;
        String budgetOnScheduleSmartcard = null;
        String budgetDisplayOnBudgetSmartCardByHours = null;
        for (int j = 1; j < guidanceHour.size(); j++) {
            totalBudgetedHourForBudgetSmartCard = totalBudgetedHourForBudgetSmartCard + Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"));
            if (((Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"))) == 0)) {
                totalBudgetHourforScheduleSmartcardIfBudgetEntered = totalBudgetHourforScheduleSmartcardIfBudgetEntered + Float.parseFloat(guidanceHour.get(j - 1).getText());

            } else {
                totalBudgetHourforScheduleSmartcardIfBudgetEntered = totalBudgetHourforScheduleSmartcardIfBudgetEntered + Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"));
            }
        }

        if(isElementEnabled(okAfterSaveConfirmationPopup,5)){
            click(okAfterSaveConfirmationPopup);
            SimpleUtils.pass("Apply Budget button is clickable");
        }else{
            SimpleUtils.fail("Apply Budget button is not clickable",false);
        }

        getDriver().navigate().refresh();
        if(areListElementVisible(scheduleWeekViewGrid,10)){
            budgetOnScheduleSmartcard = budgetDisplayOnScheduleSmartcard.get(0).getText();
            String[] budgetDisplayOnBudgetSmartcard = budgetOnbudgetSmartCard.getText().split(" ");
            budgetDisplayOnBudgetSmartCardByHours = budgetDisplayOnBudgetSmartcard[0];
        }

        if (totalBudgetedHourForBudgetSmartCard == (Float.parseFloat(budgetDisplayOnBudgetSmartCardByHours))) {
            SimpleUtils.pass("Budget " + (Float.parseFloat(budgetDisplayOnBudgetSmartCardByHours)) +" for week " +weekDuration + " on budget smartcard matches the budget entered " + totalBudgetedHourForBudgetSmartCard);
        } else {
            SimpleUtils.fail("Budget " + (Float.parseFloat(budgetDisplayOnBudgetSmartCardByHours))  +" for week " +weekDuration + " on budget smartcard doesn't match the budget entered " + totalBudgetedHourForBudgetSmartCard, true);
        }

        float finaltotalScheduledHourIfBudgetEntered = (float) (Math.round(totalBudgetHourforScheduleSmartcardIfBudgetEntered * 10) / 10.0);;
        if (finaltotalScheduledHourIfBudgetEntered == (Float.parseFloat(budgetOnScheduleSmartcard))) {
            SimpleUtils.pass("Budget " + (Float.parseFloat(budgetOnScheduleSmartcard))  +" for week " +weekDuration + " on schedule smartcard matches the budget calculated " + finaltotalScheduledHourIfBudgetEntered);
        } else {
            SimpleUtils.fail("Budget " + (Float.parseFloat(budgetOnScheduleSmartcard))  +" for week " +weekDuration + " on schedule smartcard doesn't match the budget calculated " + finaltotalScheduledHourIfBudgetEntered, true);
        }
        if(isElementEnabled(returnToOverviewTab,5)){
            click(returnToOverviewTab);
        }else{
            SimpleUtils.fail("Unable to click on Overview tab",false);
        }

    }

    public void compareBudgetValueForScheduleAndBudgetSmartCardWhenBudgetByWages(String weekDuration){
        float totalBudgetedWagesForBudgetSmartCard=0.0f;
        float totalScheduledHourIfBudgetEntered=0.0f;
        float totalScheduledWagesIfBudgetEntered=0.0f;
        String budgetDisplayOnBudgetSmartCardByHours = null;
        String budgetOnScheduleSmartcard = null;
        String budgetDisplayOnSmartCardWhenByWages = null;
        String budgetedWagesOnScheduleSmartcard = null;
        String valueOfBudgetSmartcardWhenNoBudgetEntered = budgetOnbudgetSmartCardWhenNoBudgetEntered.getText();

        for (int j = 1; j < guidanceHour.size(); j++) {
            totalBudgetedWagesForBudgetSmartCard = totalBudgetedWagesForBudgetSmartCard + Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"));
            if (((Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"))) == 0)) {
                totalScheduledHourIfBudgetEntered = totalScheduledHourIfBudgetEntered + Float.parseFloat(guidanceHour.get(j - 1).getText());
                totalScheduledWagesIfBudgetEntered = totalScheduledWagesIfBudgetEntered + Float.parseFloat(guidanceWages.get(j-1).getText());
            } else {
                totalScheduledHourIfBudgetEntered = totalScheduledHourIfBudgetEntered + Float.parseFloat(budgetHourWhenBudgetByWagesEnabled.get(j - 1).getText());
                totalScheduledWagesIfBudgetEntered = totalScheduledWagesIfBudgetEntered + Float.parseFloat(budgetEditHours.get(j - 1).getAttribute("value"));
            }
        }

        if(isElementEnabled(okAfterSaveConfirmationPopup,5)){
            click(okAfterSaveConfirmationPopup);
            SimpleUtils.pass("Apply Budget button is clickable");
        }else{
            SimpleUtils.fail("Apply Budget button is not clickable",false);
        }

        getDriver().navigate().refresh();
        if(areListElementVisible(scheduleWeekViewGrid,10)){
            budgetOnScheduleSmartcard = budgetDisplayOnScheduleSmartcard.get(0).getText();
            String[] budgetDisplayOnBudgetSmartcard = budgetOnbudgetSmartCard.getText().split(" ");
            budgetDisplayOnSmartCardWhenByWages = budgetOnbudgetSmartCard.getText().substring(1);
            budgetedWagesOnScheduleSmartcard = budgetDisplayOnScheduleSmartcard.get(1).getText().substring(1).replace(",","");

        }
        if (totalBudgetedWagesForBudgetSmartCard == (Float.parseFloat(budgetDisplayOnSmartCardWhenByWages.replaceAll(",","")))) {
            SimpleUtils.pass("Budget " + (Float.parseFloat(budgetDisplayOnSmartCardWhenByWages.replaceAll(",",""))) +" for week " +weekDuration + " on budget smartcard matches the budget entered " + totalBudgetedWagesForBudgetSmartCard);
        } else {
            SimpleUtils.fail("Budget " + (Float.parseFloat(budgetDisplayOnSmartCardWhenByWages))  +" for week " +weekDuration + " on budget smartcard doesn't match the budget entered " + totalBudgetedWagesForBudgetSmartCard, false);
        }

        float finaltotalScheduledHourIfBudgetEntered = (float) (Math.round(totalScheduledHourIfBudgetEntered * 10) / 10.0);
        float differenceBetweenBudInSCnCalcBudgbyHour = (Float.parseFloat(budgetOnScheduleSmartcard)) - finaltotalScheduledHourIfBudgetEntered;
        if (finaltotalScheduledHourIfBudgetEntered == (Float.parseFloat(budgetOnScheduleSmartcard)) ||
                (differenceBetweenBudInSCnCalcBudgbyHour <= Integer.parseInt(propertyBudgetValue.get("Tolerance_Value")))) {
            SimpleUtils.pass("Budget " + (Float.parseFloat(budgetOnScheduleSmartcard))  +" for week " +weekDuration + " on schedule smartcard matches the budget calculated " + finaltotalScheduledHourIfBudgetEntered);
        } else {
            SimpleUtils.fail("Budget " + (Float.parseFloat(budgetOnScheduleSmartcard))  +" for week " +weekDuration + " on schedule smartcard doesn't match the budget calculated " + finaltotalScheduledHourIfBudgetEntered, true);
        }
        int finaltotalScheduledWagesIfBudgetEntered = (int) (Math.round(totalScheduledWagesIfBudgetEntered * 10) / 10.0);
        int differenceBetweenBugInSCnCalcBudg = (Integer.parseInt(budgetedWagesOnScheduleSmartcard)) - finaltotalScheduledWagesIfBudgetEntered;
        waitForSeconds(3);
        if (finaltotalScheduledWagesIfBudgetEntered == (Integer.parseInt(budgetedWagesOnScheduleSmartcard)) || (differenceBetweenBugInSCnCalcBudg <= Integer.parseInt(propertyBudgetValue.get("Tolerance_Value")))){
            SimpleUtils.pass("Budgeted Wages " + (Float.parseFloat(budgetedWagesOnScheduleSmartcard))  +" for week " +weekDuration + " on schedule smartcard matches the budget wages calculated " + finaltotalScheduledWagesIfBudgetEntered);
            setBudgetTolerance(0);
        }else{
            SimpleUtils.fail("Budget Wages" + (Float.parseFloat(budgetedWagesOnScheduleSmartcard))  +" for week " +weekDuration + " on schedule smartcard doesn't match the budget wages calculated " + finaltotalScheduledWagesIfBudgetEntered, true);
        }
        if(isElementEnabled(returnToOverviewTab,5)){
            click(returnToOverviewTab);
        }else{
            SimpleUtils.fail("Unable to click on Overview tab",false);
        }
    }

    //added by Nishant

    public void searchTextForClopeningHrs(String searchInput) throws Exception {
        String[] searchAssignTeamMember = searchInput.split(",");
        if (isElementLoaded(textSearch, 10) && isElementLoaded(searchIcon, 10)) {
            for (int i = 0; i < searchAssignTeamMember.length; i++) {
                String[] searchTM = searchAssignTeamMember[i].split("\\.");
                textSearch.sendKeys(searchTM[0]);
                click(searchIcon);
                if (getScheduleStatus()) {
                    setTeamMemberName(searchAssignTeamMember[i]);
                    break;
                } else {
                    textSearch.clear();
                }
            }

        } else {
            SimpleUtils.fail("Search text not editable and icon are not clickable", false);
        }

    }

    @FindBy(xpath = "//span[text()='Clopening']")
    private WebElement clopeningFlag;

    public void verifyClopeningHrs() throws Exception {
        boolean flag = true;
        if(areListElementVisible(infoIcon,5)){
            for(int i=0; i<infoIcon.size();i++){
                if(areListElementVisible(workerStatus,5)){
                    if(workerStatus.get(i).getText().toLowerCase().contains(getTeamMemberName().toLowerCase())){
                        click(infoIcon.get(i));
                        if(isElementLoaded(clopeningFlag,5)){
                            SimpleUtils.pass("Clopening Flag is present for team member " + getTeamMemberName());
                            break;
                        }else{
                            SimpleUtils.fail("Clopening Flag is not present for team member " + getTeamMemberName(),false);
                        }
                    }else{
                        flag = false;
                    }
                }else{
                    SimpleUtils.fail("Worker status not available on the page",true);
                }
            }
        }else{
            SimpleUtils.fail("There is no image icon available on the page",false);
        }

        if(!flag) {
            SimpleUtils.report("Worker status does not match with the expected result");
        }
    }


    public void clickOnPreviousDaySchedule(String activeDay) throws Exception {
        List<WebElement> activeWeek = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
        for(int i=0; i<activeWeek.size();i++){
            String currentDay = activeWeek.get(i).getText().replace("\n", " ").substring(0,3);
            if(currentDay.equalsIgnoreCase(activeDay)){
                if(i== activeWeek.size()-1){
                    navigateWeekViewOrDayViewToPastOrFuture(ScheduleNewUITest.weekViewType.Previous.getValue(),
                            ScheduleNewUITest.weekCount.One.getValue());
                    waitForSeconds(3);
                }else{
                    click(activeWeek.get(i));
                }
            }
        }

    }

    public void legionIsDisplayingTheSchedul() throws Exception {
        if(isElementLoaded(groupByAllIcon,10)){
         SimpleUtils.pass("Legion schedule is displaying");
        }else {
            SimpleUtils.fail("Legion Schedule load failed", true);
        }
    }


    public void currentWeekIsGettingOpenByDefault() throws Exception {
        String jsonTimeZoon = parametersMap2.get("Time_Zone");
        TimeZone timeZone = TimeZone.getTimeZone(jsonTimeZoon);
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
        dfs.setTimeZone(timeZone);
        String currentTime =  dfs.format(new Date());
        Date currentDate = dfs.parse(currentTime);
        String weekBeginEndByCurrentDate = SimpleUtils.getThisWeekTimeInterval(currentDate);
        String weekBeginEndByCurrentDate2 = weekBeginEndByCurrentDate.replace("-","").replace(",","");
        String weekBeginBYCurrentDate = weekBeginEndByCurrentDate2.substring(6,8);
        String weekEndBYCurrentDate = weekBeginEndByCurrentDate2.substring(weekBeginEndByCurrentDate2.length()-2);
        SimpleUtils.report("weekBeginBYCurrentDate is : "+ weekBeginBYCurrentDate);
        SimpleUtils.report("weekEndBYCurrentDate is : "+ weekEndBYCurrentDate);
        String activeWeekText =getActiveWeekText();
        String weekDefaultBegin = activeWeekText.substring(14,17);
        SimpleUtils.report("weekDefaultBegin is :"+weekDefaultBegin);
        String weekDefaultEnd = activeWeekText.substring(activeWeekText.length()-2);
        SimpleUtils.report("weekDefaultEnd is :"+weekDefaultEnd);
        if(weekBeginBYCurrentDate.trim().equals(weekDefaultBegin.trim()) && weekEndBYCurrentDate.trim().equals(weekDefaultEnd.trim())){
            SimpleUtils.pass("Current week is getting open by default");
        }
        else {
            SimpleUtils.fail("Current week is not getting open by default",true);
        }
    }

    public void goToScheduleNewUI() throws Exception {

        if (isElementLoaded(goToScheduleButton,5)) {
            click(goToScheduleButton);
            click(ScheduleSubMenu);
            if (isElementLoaded(todoButton,5)) {
                SimpleUtils.pass("Schedule New UI load successfully");
            }else{
                SimpleUtils.fail("Schedule New UI load failed", true);
            }

        }

    }


    public void dayWeekPickerSectionNavigatingCorrectly()  throws Exception{
        String weekIcon = "Mon - Sun";
        String activeWeekText = getActiveWeekText();
        if(activeWeekText.contains(weekIcon)){
            SimpleUtils.pass("Week pick show correctly");
        }else {
            SimpleUtils.fail("it's not week mode", true);
        }
        click(daypButton);
        if(isElementLoaded(daypicker,3)){
            SimpleUtils.pass("Day pick show correctly");
        }else {
            SimpleUtils.fail("change to day pick failed", true);
        }

    }


    public void landscapePortraitModeShowWellInWeekView() throws Exception {
        if (isElementLoaded(printButton,10)) {
            click(printButton);
            if(isElementLoaded(LandscapeButton)&isElementLoaded(PortraitButton)){
                SimpleUtils.pass("Landscape and Portrait mode show well");
            }else {
                SimpleUtils.fail("Landscape and Portrait load failed", true);
            }
            click(PortraitButton);
            click(LandscapeButton);
            SimpleUtils.pass("In Week view should be able to change the mode between Landscape and Portrait ");
            click(cannelButtonInPrintLayout);
        } else {
            SimpleUtils.fail("Print button can not work", true);
        }
    }

    public void landscapeModeWorkWellInWeekView() throws Exception {
        String currentWindow =getDriver().getWindowHandle();
        if (isElementLoaded(printButton,5)) {

            click(printButton);
            click(LandscapeButton);
            click(printButtonInPrintLayout);
            if(!isElementLoaded(LandscapeButton,6)){
                String downloadPath = parameterMap.get("Download_File_Default_Dir");
                Assert.assertTrue(FileDownloadVerify.isFileDownloaded_Ext(downloadPath, "WeekViewSchedulePdf"), "print successfully");
                SimpleUtils.pass("Landscape print work well");
            }else{
                SimpleUtils.fail("Can not print by Landscape", true);
            }
            getDriver().switchTo().window(currentWindow);
        } else {
            SimpleUtils.fail("Print button is not clickable", true);
        }

    }

    public void portraitModeWorkWellInWeekView() throws Exception {
        String currentWindow =getDriver().getWindowHandle();

        if (isElementLoaded(printButton,3)) {
            click(printButton);
            click(PortraitButton);
            click(printButtonInPrintLayout);
            if(!isElementLoaded(PortraitButton,6)){
                String downloadPath = parameterMap.get("Download_File_Default_Dir");
                Assert.assertTrue(FileDownloadVerify.isFileDownloaded_Ext(downloadPath, "WeekViewSchedulePdf"), "print successfully");
                SimpleUtils.pass("Portrait print work well");
            }else{
                SimpleUtils.fail("Can not print by portrait", true);
            }
            getDriver().switchTo().window(currentWindow);
        } else {
            SimpleUtils.fail("Print button is not clickable", true);
        }

    }

    // Added by Nora: for Team Member View
    @FindBy(className = "sch-shift-worker-img-28-28")
    private List<WebElement> tmIcons;
    @FindBy(className = "sch-worker-popover")
    private WebElement popOverLayout;
    @FindBy(css = "span.sch-worker-action-label")
    private List<WebElement> shiftRequests;
    @FindBy(css = "div.lg-modal")
    private WebElement popUpWindow;
    @FindBy(className = "lg-modal__title-icon")
    private WebElement popUpWindowTitle;
    @FindBy(css = "[label=\"Cancel\"]")
    private WebElement cancelButton;
    @FindBy(css = "[label=\"Submit\"]")
    private WebElement submitButton;
    @FindBy(css = "[label=\"Back\"]")
    private WebElement backButton;
    @FindBy(css = "textarea[placeholder]")
    private WebElement messageText;
    @FindBy(className = "lgn-alert-modal")
    private WebElement confirmWindow;
    @FindBy(className = "lgn-action-button-success")
    private WebElement okBtnOnConfirm;
    @FindBy(css = "[ng-repeat*=\"shift in results\"]")
    private List<WebElement> comparableShifts;
    @FindBy(css = "[label=\"Next\"]")
    private WebElement nextButton;
    @FindBy(css = "[label=\"Cancel Cover Request\"]")
    private WebElement cancelCoverBtn;
    @FindBy(css = "[label=\"Cancel Swap Request\"]")
    private WebElement cancelSwapBtn;
    @FindBy(css = ".shift-swap-modal-table th.ng-binding")
    private WebElement resultCount;
    @FindBy(css = "td.shift-swap-modal-shift-table-select>div")
    private List<WebElement> selectBtns;
    @FindBy(css = ".modal-content .sch-day-view-shift-outer")
    private List<WebElement> swapRequestShifts;
    @FindBy(css = "[label=\"Accept\"]")
    private List<WebElement> acceptButtons;
    @FindBy(css = "[label=\"I agree\"]")
    private WebElement agreeButton;
    @FindBy(css = "lg-close.dismiss")
    private WebElement closeDialogBtn;
    @FindBy(className = "shift-swap-offer-time")
    private WebElement shiftOfferTime;
    @FindBy(className = "shift-swap-modal-table-shift-status")
    private List<WebElement> shiftStatus;

    @Override
    public void verifyShiftRequestStatus(String expectedStatus) throws Exception {
        if (areListElementVisible(shiftStatus, 5)) {
            for (WebElement status : shiftStatus) {
                if (expectedStatus.equalsIgnoreCase(status.getText())) {
                    SimpleUtils.pass("Verified shift status is correct!");
                }else {
                    SimpleUtils.fail("Shift status is incorrect, expected is: " + expectedStatus + ", but actual is: "
                    + status.getText(), false);
                }
            }
        }else {
            SimpleUtils.fail("Shift Status not loaded Successfully!", false);
        }
    }

    @Override
    public void verifyClickAcceptSwapButton() throws Exception {
        String title = "Confirm Shift Swap";
        if (areListElementVisible(acceptButtons, 5) && acceptButtons.size() > 0) {
            click(acceptButtons.get(0));
            SimpleUtils.assertOnFail(title + " not loaded Successfully!", isPopupWindowLoaded(title), false);
            if (isElementLoaded(agreeButton, 5)) {
                click(agreeButton);
                if (isElementLoaded(closeDialogBtn, 5)) {
                    click(closeDialogBtn);
                }
            }else {
                SimpleUtils.fail("I Agree button not loaded Successfully!", false);
            }
        }else {
            SimpleUtils.fail("Accept Button not loaded Successfully!", false);
        }
    }

    @Override
    public void verifySwapRequestShiftsLoaded() throws Exception {
        if (areListElementVisible(swapRequestShifts, 5)) {
            SimpleUtils.pass("Swap Request Shifts loaded Successfully!");
        }else {
            SimpleUtils.fail("Swap Request Shifts not loaded Successfully!", false);
        }
    }

    @Override
    public void verifyTheRedirectionOfBackButton() throws Exception {
        verifyBackNSubmitBtnLoaded();
        click(backButton);
        String title = "Find Shifts to Swap";
        if (isPopupWindowLoaded(title)) {
            SimpleUtils.pass("Redirect to Find Shifts to Swap windows Successfully!");
        }else {
            SimpleUtils.fail("Failed to redirect to Find Shifts to Swap window!", false);
        }
    }

    @Override
    public void verifyBackNSubmitBtnLoaded() throws Exception {
        if (isElementLoaded(backButton, 5)) {
            SimpleUtils.pass("Back button loaded Successfully on submit swap request!");
        }else {
            SimpleUtils.fail("Back button not loaded Successfully on submit swap request!", true);
        }
        if (isElementLoaded(submitButton, 5)) {
            SimpleUtils.pass("Submit button loaded Successfully on submit swap request!");
        }else {
            SimpleUtils.fail("Submit button not loaded Successfully on submit swap request!", false);
        }
    }

    @Override
    public void verifyClickOnNextButtonOnSwap() throws Exception {
        verifySelectOneShiftNVerifyNextButtonEnabled();
        click(nextButton);
    }

    @Override
    public void verifySelectMultipleSwapShifts() throws Exception {
        String selected = "selected";
        if (areListElementVisible(selectBtns, 5) && selectBtns.size() > 0) {
            for (WebElement selectBtn : selectBtns) {
                String className = selectBtn.getAttribute("class");
                if (className.isEmpty()) {
                    click(selectBtn);
                    className = selectBtn.getAttribute("class");
                    if (className.contains(selected)) {
                        SimpleUtils.pass("Select one shift Successfully!");
                    }else {
                        SimpleUtils.fail("Failed to select the shift!", false);
                    }
                }
            }
        }else {
            SimpleUtils.fail("Select Buttons not loaded Successfully!", false);
        }
    }

    @Override
    public void verifySelectOneShiftNVerifyNextButtonEnabled() throws Exception {
        String selected = "selected";
        if (areListElementVisible(selectBtns, 10) && selectBtns.size() > 0) {
            if (selectBtns.get(0).getAttribute("class").isEmpty()) {
                click(selectBtns.get(0));
            }
            if (selectBtns.get(0).getAttribute("class").contains(selected)) {
                SimpleUtils.pass("Select one shift Successfully!");
                if (isElementEnabled(nextButton, 5)) {
                    SimpleUtils.pass("Next Button is enabled after selecting one shift!");
                }else {
                    SimpleUtils.fail("Next button is still disabled after selecting one shift!", false);
                }
            }else {
                SimpleUtils.fail("Failed to select the shift!", false);
            }
        }else {
            SimpleUtils.fail("Select Buttons not loaded Successfully!", false);
        }
    }

    @Override
    public void verifyNextButtonIsLoadedAndDisabledByDefault() throws Exception {
        String notAllowed = "not-allowed";
        if (isElementLoaded(nextButton, 5)) {
            SimpleUtils.pass("Next button loaded Successfully!");
            WebElement button = nextButton.findElement(By.tagName("button"));
            String cursorAttribute = button == null ? "" : button.getCssValue("cursor");
            if (notAllowed.equalsIgnoreCase(cursorAttribute)) {
                SimpleUtils.pass("Next button is disabled by default!");
            }else {
                SimpleUtils.fail("Next button should be disabled by default, but it is enabled!", false);
            }
        }else {
            SimpleUtils.fail("Next button not loaded Successfully!", false);
        }
    }

    @Override
    public void verifyTheSumOfSwapShifts() throws Exception {
        int sum = 0;
        if (isElementLoaded(resultCount, 5) && areListElementVisible(comparableShifts, 5)) {
            String result = resultCount.getText();
            if (result.length() > 7) {
                sum = Integer.parseInt(result.substring(0, result.length() - 7).trim());
            }
            if (sum == comparableShifts.size()) {
                SimpleUtils.pass("Verified Sum of swap shifts is correct!");
            }else {
                SimpleUtils.fail("Sum of swap shifts is incorrect, showing sum is: " + sum + ", but actual is: " + comparableShifts.size(), false);
            }
        }else {
            SimpleUtils.fail("Sum results and comparable shifts not loaded Successfully!", false);
        }
    }

    @Override
    public void verifyTheDataOfComparableShifts() throws Exception {
        if (areListElementVisible(comparableShifts, 5)) {
            for (WebElement comparableShift : comparableShifts) {
                WebElement name = comparableShift.findElement(By.className("shift-swap-modal-table-name"));
                WebElement dateNTime = comparableShift.findElement(By.className("shift-swap-modal-table-shift-info"));
                WebElement location = comparableShift.findElement(By.className("shift-swap-modal-table-home-location"));
                if (name != null && dateNTime != null && location != null && !name.getText().isEmpty() &&
                !dateNTime.getText().isEmpty() && !location.getText().isEmpty()) {
                    SimpleUtils.report("Verified name: " + name.getText() + ", date and time: " + dateNTime.getText() +
                            ", location: " + location.getText() + " are loaded!");
                }else {
                    SimpleUtils.fail("The data of the comparable shift is incorrect!", false);
                    break;
                }
            }
        }else {
            SimpleUtils.fail("Comparable shifts not loaded Successfully!", false);
        }
    }

    @Override
    public void clickCancelButtonOnPopupWindow() throws Exception {
        if (isElementLoaded(cancelButton, 5)) {
            click(cancelButton);
            if (!isElementLoaded(popUpWindow, 5)) {
                SimpleUtils.pass("Pop up window get closed after clicking cancel button!");
            }else {
                SimpleUtils.fail("Pop up window still shows after clicking cancel button!", false);
            }
        }else {
            SimpleUtils.fail("Cancel Button not loaded Successfully on pop up window!", false);
        }
    }

    @Override
    public void verifyClickCancelSwapOrCoverRequest() throws Exception {
        if (isElementLoaded(cancelCoverBtn, 5)) {
            click(cancelCoverBtn);
        }
        if (isElementLoaded(cancelSwapBtn, 5)) {
            click(cancelSwapBtn);
        }
        if (isElementLoaded(okBtnOnConfirm, 5)) {
            click(okBtnOnConfirm);
        }else {
            SimpleUtils.fail("Confirm Button failed to load!", true);
        }
    }

    @Override
    public String selectOneTeamMemberToSwap() throws Exception {
        String tmName = "";
        if (areListElementVisible(comparableShifts, 5) && isElementLoaded(nextButton, 5)) {
            int randomIndex = (new Random()).nextInt(comparableShifts.size());
            WebElement selectBtn = comparableShifts.get(randomIndex).findElement(By.cssSelector("td.shift-swap-modal-shift-table-select>div"));
            WebElement name = comparableShifts.get(randomIndex).findElement(By.className("shift-swap-modal-table-name"));
            click(selectBtn);
            tmName = name.getText();
            SimpleUtils.pass("Select team member: " + tmName + " Successfully!");
            click(nextButton);
            verifyClickOnSubmitButton();
        }else {
            SimpleUtils.fail("Comparable Shifts not loaded Successfully!", false);
        }
        return tmName;
    }

    @Override
    public void verifyComparableShiftsAreLoaded() throws Exception {
        if (areListElementVisible(comparableShifts, 5)) {
            SimpleUtils.pass("Comparable Shifts loaded Successfully!");
        }else {
            SimpleUtils.fail("Comparable Shifts not loaded Successfully!", false);
        }
    }

    @Override
    public boolean verifyShiftRequestButtonOnPopup(List<String> requests) throws Exception {
        boolean isConsistent = false;
        List<String> shiftRequestsOnUI = new ArrayList<>();
        if (areListElementVisible(shiftRequests, 5)) {
            for (WebElement shiftRequest : shiftRequests) {
                shiftRequestsOnUI.add(shiftRequest.getText());
            }
        }
        if (shiftRequestsOnUI.containsAll(requests) && requests.containsAll(shiftRequestsOnUI)) {
            isConsistent = true;
            SimpleUtils.pass("Shift Requests loaded Successfully!");
        }
        return isConsistent;
    }

    @Override
    public void clickOnShiftByIndex(int index) throws Exception {
        if (areListElementVisible(tmIcons, 5)) {
            if (index < tmIcons.size()) {
                click(tmIcons.get(index));
            }else {
                SimpleUtils.fail("Index: " + index + " is out of range, the total size is: " + tmIcons.size(), true);
            }
        }else {
            SimpleUtils.fail("Shift Requests not loaded Successfully!", true);
        }
    }

    @Override
    public void verifyClickOnSubmitButton() throws Exception {
        if (isElementLoaded(submitButton, 10)) {
            click(submitButton);
            if (isElementLoaded(confirmWindow, 5) && isElementLoaded(okBtnOnConfirm, 5)) {
                click(okBtnOnConfirm);
                SimpleUtils.pass("Confirm window loaded Successfully!");
            }else {
                SimpleUtils.fail("Confirm window not loaded Successfully!", true);
            }
        }else {
            SimpleUtils.fail("Submit button on Submit Cover Request not loaded Successfully!", true);
        }
    }

    @Override
    public void verifyComponentsOnSubmitCoverRequest() throws Exception {
        if (isElementLoaded(messageText, 5)) {
            SimpleUtils.pass("Message textbox loaded Successfully!");
        }else {
            SimpleUtils.fail("Message textbox not loaded Successfully!", true);
        }
        if (isElementLoaded(cancelButton, 5)) {
            SimpleUtils.pass("Cancel button on Submit Cover Request loaded Successfully!");
        }else {
            SimpleUtils.fail("Cancel button on Submit Cover Request not loaded Successfully!", true);
        }
        if (isElementLoaded(submitButton, 5)) {
            SimpleUtils.pass("Submit button on Submit Cover Request loaded Successfully!");
        }else {
            SimpleUtils.fail("Submit button on Submit Cover Request not loaded Successfully!", true);
        }
    }

    @Override
    public void verifyTheContentOfMessageOnSubmitCover() throws Exception {
        if (isElementLoaded(messageText, 5) && isElementLoaded(shiftOfferTime, 5)) {
            String expectedText = "Hey, I have a commitment " + shiftOfferTime.getText() + " that conflicts with my shift, would you be able to help cover my shift?";
            String actualText = messageText.getAttribute("value");
            if (expectedText.equalsIgnoreCase(actualText)) {
                SimpleUtils.pass("The content of Add Message text box is correct!");
                messageText.clear();
            }else {
                SimpleUtils.fail("The content of Add Message text box is incorrect! Expected is: " + expectedText
                + " but actual is: " + actualText, false);
            }
        }else {
            SimpleUtils.fail("Message text box not loaded Successfully!", true);
        }
    }

    @Override
    public boolean isPopupWindowLoaded(String title) throws Exception {
        boolean isLoaded = false;
        if (isElementLoaded(popUpWindow, 5) && isElementLoaded(popUpWindowTitle, 5)) {
            if (title.equalsIgnoreCase(popUpWindowTitle.getText())) {
                SimpleUtils.pass(title + " window loaded Successfully!");
                isLoaded = true;
            }
        }
        return isLoaded;
    }

    @Override
    public void clickTheShiftRequestByName(String requestName) throws Exception {
        if (areListElementVisible(shiftRequests, 5)) {
            for (WebElement shiftRequest : shiftRequests) {
                if (shiftRequest.getText().equalsIgnoreCase(requestName)) {
                    click(shiftRequest);
                    SimpleUtils.pass("Click " + requestName + " button Successfully!");
                    break;
                }
            }
        }else {
            SimpleUtils.fail("Shift Request buttons not loaded Successfully!", true);
        }
    }

    @Override
    public boolean areShiftsPresent() throws Exception {
        boolean arePresent = false;
        if (areListElementVisible(dayViewAvailableShifts, 5)) {
            arePresent = true;
        }
        return arePresent;
    }

    @Override
    public int verifyClickOnAnyShift() throws Exception {
        List<String> expectedRequests = new ArrayList<>(Arrays.asList("Request to Swap Shift", "Request to Cover Shift"));
        int index = 100;
        if (areListElementVisible(tmIcons, 5)) {
            for (int i = 0; i < tmIcons.size(); i++) {
                moveToElementAndClick(tmIcons.get(i));
                if (isPopOverLayoutLoaded()) {
                    if (verifyShiftRequestButtonOnPopup(expectedRequests)) {
                        index = i;
                        break;
                    }
                }
            }
            if (index == 100) {
                // Doesn't find any shift that can swap or cover, cancel the previous
                index = cancelSwapOrCoverRequests(expectedRequests);
            }
        }else {
            SimpleUtils.fail("Team Members' Icons not loaded Successfully!", true);
        }
        return index;
    }

    public int cancelSwapOrCoverRequests(List<String> expectedRequests) throws Exception {
        List<String> swapRequest = new ArrayList<>(Arrays.asList("View Swap Request Status"));
        List<String> coverRequest = new ArrayList<>(Arrays.asList("View Cover Request Status"));
        int index = 100;
        if (areListElementVisible(tmIcons, 5)) {
            for (int i = 0; i < tmIcons.size(); i++) {
                moveToElementAndClick(tmIcons.get(i));
                if (isPopOverLayoutLoaded()) {
                    if (verifyShiftRequestButtonOnPopup(swapRequest)) {
                        cancelRequestByTitle(swapRequest, swapRequest.get(0).substring(5).trim());
                    }
                    if (verifyShiftRequestButtonOnPopup(coverRequest)) {
                        cancelRequestByTitle(coverRequest, coverRequest.get(0).substring(5).trim());
                    }
                    moveToElementAndClick(tmIcons.get(i));
                    if (verifyShiftRequestButtonOnPopup(expectedRequests)) {
                        index = i;
                        break;
                    }
                }
            }
        }else {
            SimpleUtils.fail("Team Members' Icons not loaded Successfully!", true);
        }
        if (index == 100) {
            SimpleUtils.fail("Failed to find a shift that can swap or cover!", false);
        }
        return index;
    }

    public void cancelRequestByTitle(List<String> requests, String title) throws Exception {
        if (requests.size() > 0) {
            clickTheShiftRequestByName(requests.get(0));
            if (isPopupWindowLoaded(title)) {
                verifyClickCancelSwapOrCoverRequest();
            }
        }
    }

    public boolean isPopOverLayoutLoaded() throws Exception {
        boolean isLoaded = false;
        if (isElementLoaded(popOverLayout, 5)) {
            isLoaded = true;
            SimpleUtils.pass("Pop over layout loaded Successfully!");
        }
        return isLoaded;
    }

    // Added by Nora: for Team schedule option as team member
    @FindBy (css = "week-schedule-shift .week-schedule-shift-wrapper")
    private List<WebElement> wholeWeekShifts;
    @FindBy (css = ".day-week-picker-period-week")
    private List<WebElement> currentWeeks;
    @FindBy (className = "period-name")
    private WebElement weekPeriod;
    @FindBy (className = "card-carousel-card")
    private List<WebElement> smartCards;
    @FindBy (className = "card-carousel-link")
    private List<WebElement> cardLinks;
    @FindBy (css = "[src*=\"print.svg\"]")
    private WebElement printIcon;

    public enum monthsOfCalendar {
        Jan("January"),
        Feb("February"),
        Mar("March"),
        Apr("April"),
        May("May"),
        Jun("June"),
        Jul("July"),
        Aug("August"),
        Sep("September"),
        Oct("October"),
        Nov("November"),
        Dec("December");
        private final String value;

        monthsOfCalendar(final String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Override
    public void verifyThePrintFunction() throws Exception {
        if (isPrintIconLoaded()) {
            click(printIcon);
            if (isElementLoaded(printButtonInPrintLayout, 5)) {
                click(printButtonInPrintLayout);
                if (!isElementLoaded(printButtonInPrintLayout, 6)) {
                    String downloadPath = parameterMap.get("Download_File_Default_Dir");
                    SimpleUtils.assertOnFail("Failed to download the team schedule", FileDownloadVerify.isFileDownloaded_Ext(downloadPath, "WeekViewSchedulePdf"), false);
                }else {
                    SimpleUtils.fail("Failed to print the team schedule!", true);
                }
            }else {
                SimpleUtils.fail("Print Layout not loaded Successfully!", false);
            }
        }else {
            SimpleUtils.fail("Print icon not loaded Successfully on Schedule page!", false);
        }
    }

    @Override
    public boolean isPrintIconLoaded() throws Exception {
        boolean isLoaded = false;
        if (isElementLoaded(printIcon, 5)) {
            isLoaded = true;
            SimpleUtils.pass("Print Icon loaded Successfully!");
        }
        return isLoaded;
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
                        if (shiftType.equalsIgnoreCase("Open")) {
                            if (!name.getText().equalsIgnoreCase("Open")) {
                                SimpleUtils.fail("Shift: " + name.getText() + " isn't for shift type: " + shiftType, true);
                            }
                        }else {
                            if (name.getText().equalsIgnoreCase("Open")) {
                                SimpleUtils.fail("Shift: " + name.getText() + " isn't for shift type: " + shiftType, true);
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
                            if (!name.getText().equalsIgnoreCase("Open")) {
                                SimpleUtils.fail("Shift: " + name.getText() + " isn't for shift type: " + shiftType, true);
                            }
                        }else {
                            if (name.getText().equalsIgnoreCase("Open")) {
                                SimpleUtils.fail("Shift: " + name.getText() + " isn't for shift type: " + shiftType, true);
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
    public int getShiftsCount() throws Exception {
        int count = 0;
        if (areListElementVisible(wholeWeekShifts, 5)) {
            count = wholeWeekShifts.size();
        }else {
            SimpleUtils.fail("Whole Week Shifts not loaded Successfully!", false);
        }
        return count;
    }

    @Override
    public void clickLinkOnSmartCardByName(String linkName) throws Exception {
        if (areListElementVisible(cardLinks, 5)) {
            for (WebElement cardLink : cardLinks) {
                if (cardLink.getText().equalsIgnoreCase(linkName)) {
                    click(cardLink);
                    SimpleUtils.pass("Click the link: " + linkName + " Successfully!");
                    break;
                }
            }
        }else {
            SimpleUtils.report("There are no smart card links!");
        }
    }

    @Override
    public int getCountFromSmartCardByName(String cardName) throws Exception {
        int count = -1;
        if (areListElementVisible(smartCards, 5)) {
            for (WebElement smartCard : smartCards) {
                WebElement title = smartCard.findElement(By.className("card-carousel-card-title"));
                if (title != null && title.getText().trim().equalsIgnoreCase(cardName)) {
                    WebElement h1 = smartCard.findElement(By.tagName("h1"));
                    String h1Title = h1 == null ? "" : h1.getText();
                    if (h1Title.contains(" ")) {
                        String[] items = h1Title.split(" ");
                        for (String item : items) {
                            if (SimpleUtils.isNumeric(item)) {
                                count = Integer.parseInt(item);
                                SimpleUtils.report("Get " + cardName + " count is: " + count);
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (count == -1) {
            SimpleUtils.fail("Failed to get the count from " + cardName + " card!", false);
        }
        return count;
    }

    @Override
    public boolean isSpecificSmartCardLoaded(String cardName) throws Exception {
        boolean isLoaded = false;
        if (areListElementVisible(smartCards, 5)) {
            for (WebElement smartCard : smartCards) {
                WebElement title = smartCard.findElement(By.className("card-carousel-card-title"));
                if (title != null && title.getText().trim().equalsIgnoreCase(cardName)) {
                    isLoaded = true;
                    break;
                }
            }
        }
        return isLoaded;
    }

    @Override
    public void goToSchedulePageAsTeamMember() throws Exception {
        if (isElementLoaded(goToScheduleButton, 5)) {
            click(goToScheduleButton);
            if (areListElementVisible(ScheduleSubTabsElement, 5)) {
                SimpleUtils.pass("Navigate to schedule page successfully!");
            }else {
                SimpleUtils.fail("Schedule page not loaded Successfully!", true);
            }
        }else {
            SimpleUtils.fail("Go to Schedule button not loaded Successfully!", false);
        }
    }

    @Override
    public void gotoScheduleSubTabByText(String subTitle) throws Exception {
        if (areListElementVisible(ScheduleSubTabsElement, 5)) {
            for (WebElement scheduleSubTab : ScheduleSubTabsElement) {
                if (isElementEnabled(scheduleSubTab, 5) && isClickable(scheduleSubTab, 5)
                        && scheduleSubTab.getText().equalsIgnoreCase(subTitle)) {
                    click(scheduleSubTab);
                    break;
                }
            }
            if (isElementLoaded(activatedSubTabElement, 5) && activatedSubTabElement.getText().equalsIgnoreCase(subTitle)) {
                SimpleUtils.pass("Navigate to sub tab: " + subTitle + " Successfully!");
            }else {
                SimpleUtils.fail("Failed navigating to sub tab: " + subTitle, false);
            }
        }else {
            SimpleUtils.fail("Schedule sub tab elements not loaded SUccessfully!", false);
        }
    }

    @Override
    public void verifyTeamScheduleInViewMode() throws Exception {
        if (isElementLoaded(edit, 5)) {
            SimpleUtils.fail("Team Member shouldn't see the Edit button!", false);
        }else {
            SimpleUtils.pass("Verified Team Schedule is in View Mode!");
        }
    }

    @Override
    public List<String> getWholeWeekSchedule() throws Exception {
        List<String> weekShifts = new ArrayList<>();
        if (areListElementVisible(wholeWeekShifts, 5)) {
            for (WebElement shift : wholeWeekShifts) {
                WebElement name = shift.findElement(By.className("week-schedule-worker-name"));
                if (!name.getText().contains("Open")) {
                    weekShifts.add(shift.getText());
                }
            }
        }else {
            SimpleUtils.fail("Whole Week Shifts not loaded Successfully!", true);
        }
        if (weekShifts.size() == 0) {
            SimpleUtils.fail("Failed to get the whole week shifts!", false);
        }
        return weekShifts;
    }

    @Override
    public String getSelectedWeek() throws Exception {
        String selectedWeek = "";
        if (isElementLoaded(currentActiveWeek, 5)) {
            selectedWeek = currentActiveWeek.getText();
            SimpleUtils.report("Get current active week: " + selectedWeek);
        }else {
            SimpleUtils.fail("Current Active Week not loaded Successfully!", false);
        }
        return selectedWeek;
    }

    @Override
    public void verifySelectOtherWeeks() throws Exception {
        String currentWeekPeriod = "";
        String weekDate = "";
        if (areListElementVisible(currentWeeks, 5)) {
            for (int i = 0; i < currentWeeks.size(); i++) {
                click(currentWeeks.get(i));
                if (isElementLoaded(periodName, 5)) {
                    currentWeekPeriod = periodName.getText().length() > 12 ? periodName.getText().substring(12) : "";
                }
                if (currentWeeks.get(i).getText().contains("\n")) {
                    weekDate = currentWeeks.get(i).getText().split("\n").length >= 2 ? currentWeeks.get(i).getText().split("\n")[1] : "";
                    if (weekDate.length() > 3) {
                        String shortMonth = weekDate.substring(0, 3);
                        String fullMonth = getFullMonthName(shortMonth);
                        weekDate = weekDate.replaceAll(shortMonth, fullMonth);
                    }
                }
                if (weekDate.trim().equalsIgnoreCase(currentWeekPeriod.trim())) {
                    SimpleUtils.pass("Selected week is: " + currentWeeks.get(i).getText() + " and current week is: " + currentWeekPeriod);
                }else {
                    SimpleUtils.fail("Selected week is: " + currentWeeks.get(i).getText() + " but current week is: " + currentWeekPeriod, false);
                }
                if (i == (currentWeeks.size() - 1) && isElementLoaded(calendarNavigationNextWeekArrow, 5)) {
                    click(calendarNavigationNextWeekArrow);
                    verifySelectOtherWeeks();
                }
            }
        }else {
            SimpleUtils.fail("Current weeks' elements not loaded Successfully!", false);
        }
    }

    public String getFullMonthName(String shortName) {
        String fullName = "";
        monthsOfCalendar[] shortNames = monthsOfCalendar.values();
        for (int i = 0; i < shortNames.length; i++) {
            if (shortNames[i].name().equalsIgnoreCase(shortName)) {
                fullName = shortNames[i].value;
                SimpleUtils.report("Get the full name of " + shortName + ", is: " + fullName);
                break;
            }
        }
        return fullName;
    }
}