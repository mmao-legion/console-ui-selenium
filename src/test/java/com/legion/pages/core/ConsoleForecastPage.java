package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.ForecastPage;
import com.legion.pages.SchedulePage;
import com.legion.tests.core.ScheduleNewUITest;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import cucumber.api.java.ro.Si;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javax.xml.crypto.dsig.SignatureMethod;
import java.net.SocketImpl;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.legion.utils.MyThreadLocal.*;

public class ConsoleForecastPage extends BasePage implements ForecastPage {

	//	@FindBy(xpath="//[name()='svg']/[name()='g']")
//	private WebElement forecastGraph;
	private static HashMap<String, String> parametersMap2 = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ControlsPageLocationDetail.json");

	private ConsoleScheduleNewUIPage scheduleNewUIPage;
	@FindBy(xpath = "//*[name()='svg']/*[name()='g']//*[name()='rect' and @class ='bar']")
	private List<WebElement> forecastGraph;

	@FindBy(xpath = "//div[contains(@class,'sub-navigation-view')]//span[contains(text(),'Forecast')]")
	private WebElement forecastTab;

	@FindBy(css = "div.day-week-picker-arrow-right")
	private WebElement forecastCalendarNavigationNextWeekArrow;

	@FindBy(css = "div.day-week-picker-arrow-left")
	private WebElement forecastCalendarNavigationPreviousWeekArrow;

	@FindBy(css = ".ng-scope.lg-button-group-selected.lg-button-group-last")
	private WebElement weekViewButton;

	@FindBy(xpath = "//span[contains(@class,'buttonLabel')][contains(text(),'Day')]")
	private WebElement dayViewButton;

	@FindBy(css = ".ng-scope.lg-button-group-selected.lg-button-group-first")
	private WebElement shoppersTab;

	@FindBy(xpath = "//span[contains(@class,'buttonLabel')][contains(text(),'Labor')]")
	private WebElement laborTab;

	@FindBy(css = "div.day-week-picker-period-active")
	private WebElement currentActiveWeek;

	@FindBy(xpath = "//td[contains(text(),'Peak Shoppers')]/following-sibling::td")
	private WebElement peakShoppersForecast;

	@FindBy(css = "div.day-week-picker-period")
	private List<WebElement> dayViewOfDatePicker;

	@FindBy(css = "div.day-week-picker-period-active")
	private WebElement currentActiveDay;

	@FindBy(css = "div[ng-if='isClosed']")
	private WebElement storeClosedView;

	@FindBy(xpath = "//*[contains(@class,'day-week-picker-period-active')]/following-sibling::div[1]")
	private WebElement immediateNextToCurrentActiveWeek;

	@FindBy(xpath = "//*[contains(@class,'day-week-picker-period-active')]/preceding-sibling::div[1]")
	private WebElement immediatePastToCurrentActiveWeek;

	@FindBy(xpath = "//td[contains(text(),'Hours')]//following-sibling::td[@class='number ng-binding']")
	private WebElement laborSmartCardForecast;

	@FindBy(xpath = "//td[contains(text(),'Hours')]//following-sibling::td[@ng-if='hasBudget()']")
	private WebElement laborSmartCardBudget;

	@FindBy(xpath = "//div[contains(text(),'Holidays')]")
	private WebElement holidaysSmartcardHeader;

	@FindBy(xpath = "//span[contains(text(),'View All')]")
	private WebElement viewALLBtnInHolidays;

	@FindBy(css = ".lg-modal__title-icon.ng-binding")
	private WebElement holidayDetailsTitle;

	@FindBy(css = "[label=\"Close\"]")
	private WebElement closeBtnInHoliday;

	@FindBy(css = ".forecast-prediction-picker-text")
	private WebElement displayDropDownBtnInProjected;


	//use this to get the text of weeks which displayed,
	@FindBy(css = "div.day-week-picker-period-week")
	private WebElement currentWeekPeriod;

	@FindBy(css = "div > label:nth-child(1)")
	private WebElement checkBoxOfRecentTrendLine;

	@FindBy(css = "div > label:nth-child(2)")
	private WebElement checkBoxOfActualLine;

	@FindBy(css = "div > label:nth-child(3)")
	private WebElement checkBoxOfLstYearLine;

	@FindBy(css = "[stroke=\"#f49342\"]")
	private WebElement actualLine;

	@FindBy(css = "[stroke=\"#795548\"]")
	private WebElement recentTrendLine;

	@FindBy(css = "[stroke=\"#9c6ade\"]")
	private WebElement lastYearLine;

	@FindBy(css = ".ng-valid-parse.ng-touched.ng-not-empty")
	private List<WebElement> checkBoxSelected;

	@FindBy(css = "day-week-picker > div > div > div:nth-child(3)>span")
	private WebElement postWeekNextToCurrentWeek;

	@FindBy(css = "day-week-picker > div > div > div:nth-child(5)>span")
	private WebElement futureWeekNextToCurrentWeek;

	@FindBy(css = "div:nth-child(3) > lg-filter > div > input-field > ng-form")
	private WebElement filterWorkRoleButton;

	@FindBy(css = ".weather-forecast-day-name")
	private List<WebElement> weatherDaysOfWeek;

	@FindBy(css = ".card-carousel-card.card-carousel-card-default")
	private WebElement weatherSmartCard;

	@FindBy(xpath = "//*[contains(text(),'Weather - Week of')]")
	private WebElement weatherWeekSmartCardHeader;

	@FindBy(css = "div.card-carousel-arrow.card-carousel-arrow-right")
	private WebElement smartcardArrowRight;

	@FindBy(css = "span.weather-forecast-temperature")
	private List<WebElement> weatherTemperatures;

	@FindBy(css = "//div[contains(text(),'Work role')]")
	private WebElement workRoleFilterTitle;

	@FindBy(xpath = "//input-field[@label-unsafe=\"opt.labelUnsafe\"]")
	private List<WebElement> workRoleList;

	@FindBy(css = "span.forecast-prediction-top-legend-entry.ng-binding.ng-scope")
	private List<WebElement> hoursOfWorkRole;

	@FindBy(xpath = "//lg-filter[@label=\"Work role\"]/div/input-field")
	private WebElement filterButton;

	@FindBy(xpath = "//lg-filter[@label=\"Filter\"]/div/input-field")
	private WebElement filterButtonForShopper;

	@FindBy(css = "div.row-fx.schedule-search-options>div:nth-child(3)>lg-filter>div>input-field>ng-form>div")
	private WebElement filterButtonText;

	@FindBy(css = "a.lg-filter__clear.ng-scope.lg-filter__clear-active")
	private WebElement clearFilterBtn;

	@FindBy(css = "div.lg-filter__wrapper")
	private WebElement filterPopup;

	@FindBy(css = "[label=\"Refresh\"]")
	private WebElement refreshBtn;

	@FindBy(css = ".card-carousel-fixed")
	private WebElement insightSmartCard;

	//added by Estell to close the stop

	@FindBy(css = "[ng-click=\"editWorkingHours(day, summary.businessId)\"]")
	private List<WebElement> editBtnInOperationHours;

	@FindBy(css = "[ng-repeat=\"day in summary.workingHours\"]")
	private List<WebElement> textInOperationHours;

	@FindBy(css = ".forecast-prediction-tooltip")
	private WebElement tooltipInProjected;

	@FindBy(css = "svg#forecast-prediction")
	private WebElement barChartInProjected;

	@FindBy(css = "#forecast-prediction>g>circle")
	private List<WebElement> circleInProjected;

	@FindBy(css = "#forecast-prediction>g>rect")
	private List<WebElement> forecastBarInProjected;

	@FindBy(css = "div.day-week-picker-arrow.day-week-picker-arrow-left")
	private WebElement schedulingWindowArrowLeft;

	@FindBy(css = "div.day-week-picker-arrow.day-week-picker-arrow-right")
	private WebElement schedulingWindowArrowRight;

	@FindBy(css = "[ng-if=\"!!getSummaryField('totalActualUnits')\"]")
	private List<WebElement> actualDataInSightSmartCard;


	public ConsoleForecastPage() {
		PageFactory.initElements(getDriver(), this);
	}

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

	public void forecastShoppersDayNavigation(String weekDuration) throws Exception {
		if (isElementEnabled(dayViewButton)) {
			click(dayViewButton);
			SimpleUtils.pass("Clicked on Day View For Shoppers Sub Tab of Forecast Tab for Week Staring " + weekDuration);
			for (int i = 0; i < dayViewOfDatePicker.size(); i++) {
				//int forecastShoppersValueFinal = Integer.parseInt(forecastShoppersValue.getText());
				click(dayViewOfDatePicker.get(i));
				if (forecastGraph.size() != 0 && Integer.parseInt(peakShoppersForecast.getText()) > 0) {
					SimpleUtils.pass("Shoopers Forecast Loaded in DayView Successfully! for " + currentActiveDay.getText() + " and Value for Forecast is " + peakShoppersForecast.getText());
				} else if (isElementLoaded(storeClosedView, 10)) {
					SimpleUtils.pass("Store Closed on " + currentActiveDay.getText());
				} else {
					SimpleUtils.fail("Forecast Not Loaded in DayView for " + currentActiveDay.getText() + " and Value for Forecast is " + peakShoppersForecast.getText(), true);
				}
			}
			click(weekViewButton);
		} else {
			SimpleUtils.fail("Day View button not found in Forecast", false);
		}
	}


	public void forecastShopperWeekNavigation() throws Exception {
		if (isElementEnabled(weekViewButton)) {
			click(weekViewButton);
			String weekDuration[] = currentActiveWeek.getText().split("\n");
			SimpleUtils.pass("Current active forecast week is " + weekDuration[1]);
			if (isElementEnabled(shoppersTab)) {
				click(shoppersTab);
				if (forecastGraph.size() != 0 && (Integer.parseInt(peakShoppersForecast.getText())) > 0) {
					SimpleUtils.pass("Shoppers Forecast Loaded in Week View Successfully! for week starting " + weekDuration[1] + " Number of Shoppers is " + peakShoppersForecast.getText());
					forecastShoppersDayNavigation(weekDuration[1]);
				} else {
					SimpleUtils.fail("Shoppers Forecast Loaded in Week View Successfully! for week starting " + weekDuration[1] + " Number of Shoppers is " + peakShoppersForecast.getText(), true);
				}
			} else {
				SimpleUtils.fail("Shoppers subtab of forecast tab not found", false);
			}
		} else {
			SimpleUtils.fail("Week View button not found in Forecast", false);
		}

	}


	@Override
	public void loadShoppersForecastforCurrentNFutureWeek(String nextWeekView, int weekCount) throws Exception {
		//boolean flag=false;
		if (isElementLoaded(forecastTab, 5)) {
			click(forecastTab);
			SimpleUtils.pass("Clicked on Forecast Sub Tab");
			for (int i = 0; i < weekCount; i++) {
				if (nextWeekView.toLowerCase().contains("next") || nextWeekView.toLowerCase().contains("future")) {
					forecastShopperWeekNavigation();
				}
				clickImmediateNextToCurrentActiveWeekInDayPicker();
			}
		} else {
			SimpleUtils.fail("Forecast Sub Menu Tab Not Found", false);
		}
	}

	@Override
	public void loadShoppersForecastforPastWeek(String nextWeekView, int weekCount) throws Exception {
		//boolean flag=false;
		if (isElementLoaded(forecastTab, 5)) {
			click(forecastTab);
			SimpleUtils.pass("Clicked on Forecast Sub Tab");
			for (int i = 0; i < weekCount; i++) {
				if (nextWeekView.toLowerCase().contains("next") || nextWeekView.toLowerCase().contains("future")) {
					clickImmediatePastToCurrentActiveWeekInDayPicker();
					forecastShopperWeekNavigation();
				}
			}
		} else {
			SimpleUtils.fail("Forecast Sub Menu Tab Not Found", false);
		}
	}


	public void forecastLaborDayNavigation(String weekDuration) throws Exception {
		if (isElementEnabled(dayViewButton)) {
			click(dayViewButton);
			SimpleUtils.pass("Clicked on Day View For Labor Sub Tab of Forecast Tab for Week Staring " + weekDuration);
			for (int i = 0; i < dayViewOfDatePicker.size(); i++) {
				click(dayViewOfDatePicker.get(i));
				if (forecastGraph.size() != 0 && (Float.parseFloat(laborSmartCardForecast.getText())) > 0 && (Float.parseFloat(laborSmartCardBudget.getText())) > 0) {
					SimpleUtils.pass("Labor Forecast Loaded in DayView Successfully! for " + currentActiveDay.getText() + " Labor Forecast is " + laborSmartCardForecast.getText() + " Labor Budget is " + laborSmartCardBudget.getText() + " Hour");
				} else if (isElementLoaded(storeClosedView, 10)) {
					SimpleUtils.pass("Store Closed on " + currentActiveDay.getText());
				} else {
					SimpleUtils.fail("Labor Forecast Not Loaded in DayView for " + currentActiveDay.getText() + " Labor Forecast is " + laborSmartCardForecast.getText() + " Labor Budget is " + laborSmartCardBudget.getText() + " Hour", true);
				}
			}
			click(weekViewButton);
		} else {
			SimpleUtils.fail("Day View button not found in Forecast", false);
		}
	}


	public void forecastLaborWeekNavigation() throws Exception {
		if (isElementEnabled(weekViewButton)) {
			click(weekViewButton);
			String weekDuration[] = currentActiveWeek.getText().split("\n");
			SimpleUtils.pass("Current active labor week is " + weekDuration[1]);
			if (isElementEnabled(laborTab)) {
				click(laborTab);
				if (forecastGraph.size() != 0 && (Float.parseFloat(laborSmartCardForecast.getText())) > 0 && (Float.parseFloat(laborSmartCardBudget.getText())) > 0) {
					SimpleUtils.pass("Labor Forecast Loaded in Week View Successfully! for week starting " + weekDuration[1] + " Labor Forecast is " + laborSmartCardForecast.getText() + " Labor Budget is " + laborSmartCardBudget.getText() + " Hour");
					forecastLaborDayNavigation(weekDuration[1]);
				} else {
					SimpleUtils.fail("Labor Forecast Not Loaded in Week View for Week Starting " + weekDuration[1] + " Labor Forecast is " + laborSmartCardForecast.getText() + " Labor Budget is " + laborSmartCardBudget.getText() + " Hour", true);
				}
			} else {
				SimpleUtils.fail("Labor subtab of forecast tab not found", false);
			}
		} else {
			SimpleUtils.fail("Week View button not found in Forecast", false);
		}

	}

	@Override
	public void loadLaborForecastforCurrentNFutureWeek(String nextWeekView, int weekCount) throws Exception {
		//boolean flag=false;
		if (isElementLoaded(forecastTab, 5)) {
			click(forecastTab);
			SimpleUtils.pass("Clicked on Forecast Sub Tab");
			for (int i = 0; i < weekCount; i++) {
				if (nextWeekView.toLowerCase().contains("next") || nextWeekView.toLowerCase().contains("future")) {
					forecastLaborWeekNavigation();
				}
				clickImmediateNextToCurrentActiveWeekInDayPicker();
			}
		} else {
			SimpleUtils.fail("Forecast Sub Menu Tab Not Found", false);
		}
	}

	@Override
	public void loadLaborForecastforPastWeek(String nextWeekView, int weekCount) throws Exception {
		//boolean flag=false;
		if (isElementLoaded(forecastTab, 5)) {
			click(forecastTab);
			SimpleUtils.pass("Clicked on Forecast Sub Tab");
			for (int i = 0; i < weekCount; i++) {
				if (nextWeekView.toLowerCase().contains("next") || nextWeekView.toLowerCase().contains("future")) {
					clickImmediatePastToCurrentActiveWeekInDayPicker();
					forecastLaborWeekNavigation();
				}
			}
		} else {
			SimpleUtils.fail("Forecast Sub Menu Tab Not Found", false);
		}
	}

	@Override
	public void holidaySmartCardIsDisplayedForCurrentAWeek() throws Exception {
		if (isElementLoaded(forecastTab, 5)) {
			click(forecastTab);
			SimpleUtils.pass("Clicked on Forecast Sub Tab");
			if (true == isHolidaySmartcardDispaly()) {
				click(viewALLBtnInHolidays);
				SimpleUtils.pass("View all button is clickable");
				if (isElementLoaded(holidayDetailsTitle, 5)) {
					SimpleUtils.pass("current holiday is showing");
					click(closeBtnInHoliday);
					SimpleUtils.pass("Close button is clickable in holidays window");
				}
			} else {
				SimpleUtils.report("this week has no holiday");
			}
		} else {
			SimpleUtils.fail("Forecast Sub Menu Tab Not Found", true);
		}
	}


	public void clickForecast() throws Exception {
		if (isElementLoaded(forecastTab, 5)) {
			click(forecastTab);
			SimpleUtils.pass("Clicked on Forecast Sub Tab");
		} else {
			SimpleUtils.fail("Forecast Sub Menu Tab Not Found", false);
		}
	}

	@Override
	public void verifyNextPreviousBtnCorrectOrNot() throws Exception {
		if (isElementLoaded(postWeekNextToCurrentWeek,5) & isElementLoaded(futureWeekNextToCurrentWeek,5)) {
			SimpleUtils.pass("post current future week is visible");
			String currentWeekPeriodText = currentWeekPeriod.getText().trim().replace("\n", "").replace(" ", "").replace("-", "");
			SimpleUtils.report("currentWeekPeriodText======" + currentWeekPeriodText);
			navigateToPreviousAndFutureWeek(forecastCalendarNavigationPreviousWeekArrow);
			String WeekPeriodTextAftBack = currentWeekPeriod.getText().trim().replace("\n", "").replace(" ", "").replace("-", "");
			SimpleUtils.report("WeekPeriodTextAftBack======" + WeekPeriodTextAftBack);
			if (WeekPeriodTextAftBack.trim().equals(currentWeekPeriodText.trim())) {
				SimpleUtils.fail(" Back buttons are not working", true);
			} else {
				SimpleUtils.pass(" Back button is working normally");
			}
			navigateToPreviousAndFutureWeek(forecastCalendarNavigationNextWeekArrow);
			String WeekPeriodTextAftForeword = currentWeekPeriod.getText().trim().replace("\n", "").replace(" ", "").replace("-", "");
			if (WeekPeriodTextAftForeword.trim().equals(currentWeekPeriodText.trim())) {
				SimpleUtils.pass(" Foreword button is working normally");
			} else {
				SimpleUtils.fail(" Foreword buttons are not working normally",true);
			}
		}
			else {
			SimpleUtils.fail("Foreword and Back buttons is not displayed", false);
		}


	}

	@Override
	public void verifyDisplayOfActualLineSelectedByDefaultInOrangeColor() throws Exception {

		if (isElementLoaded(displayDropDownBtnInProjected, 5)) {
			click(displayDropDownBtnInProjected);
			if (isElementLoaded(actualLine, 5)) {
				click(checkBoxOfActualLine);
				if (checkBoxSelected.size() == 0) {
					SimpleUtils.pass(" Display of Actual line should be selected bydefault and in Orange color");
				}

			} else {
				SimpleUtils.fail("Actual is not selected by default", true);
			}
		} else {
			SimpleUtils.fail("Display dropdown list load failed", true);
		}
	}

	@Override
	public void verifyRecentTrendLineIsSelectedAndColorInBrown() throws Exception {
		if (isElementLoaded(displayDropDownBtnInProjected, 20)) {
//			click(displayDropDownBtnInProjected);
			click(checkBoxOfRecentTrendLine);
			if (isElementLoaded(recentTrendLine, 5)) {
				SimpleUtils.pass(" Display of RecentTrend line should be selected  and in Brown color");
			} else {
				SimpleUtils.fail("RecentTrend line selected failed", false);
			}

//			click(displayDropDownBtnInProjected);
			click(checkBoxOfRecentTrendLine);
		} else {

			SimpleUtils.fail("Display dropdown list load failed", false);
		}
	}

	@Override
	public void verifyLastYearLineIsSelectedAndColorInPurple() throws Exception {
		if (isElementLoaded(displayDropDownBtnInProjected, 20)) {
//			click(displayDropDownBtnInProjected);
			click(checkBoxOfLstYearLine);
			if (isElementLoaded(lastYearLine, 5)) {
				SimpleUtils.pass(" Display of LastYear line should be selected  and in purple color");
			} else {
				SimpleUtils.fail("RecentTrend line selected failed", false);
			}
		}
		click(checkBoxOfLstYearLine);

	}

	@Override
	public void verifyForecastColourIsBlue() throws Exception {
		String forecastLegendColor = Color.fromString(getDriver().findElement(By.cssSelector(".forecast-prediction-rect.forecast-prediction-rect-baseline")).getCssValue("background-color")).asHex();;
		if (forecastGraph.size()>=0) {
			String forecastBarColor =  Color.fromString(forecastGraph.get(1).getAttribute("fill")).asHex();
			if (forecastBarColor.equals(forecastLegendColor)) {
				SimpleUtils.pass("Forecast color is correct and it's blue");
			}
		}else {
			SimpleUtils.pass("There is no forecast bar in this week");
		}
	}

	private void navigateToPreviousAndFutureWeek(WebElement element) throws Exception {

		if (isElementLoaded(element, 5)) {
			click(element);
			SimpleUtils.pass("can navigate to next or previous week");

		} else {
			SimpleUtils.fail("element is not displayed", true);
		}
	}

	private boolean isHolidaySmartcardDispaly() throws Exception {
		if (isElementLoaded(holidaysSmartcardHeader, 15)) {
			SimpleUtils.pass("Current week's Holidays are showing");
			return true;
		} else {
			SimpleUtils.report("There is no holiday in current week");
			return false;
		}

	}

	@Override
	public void verifyWorkRoleIsSelectedAftSwitchToPastFutureWeek() throws Exception {
		click(laborTab);

		if (isElementLoaded(filterWorkRoleButton, 10)) {
			String workRoleText = filterWorkRoleButton.getText();
			goToPostWeekNextToCurrentWeek();
			String postWeekFilterText = filterWorkRoleButton.getText();
			goToFutureWeekNextToCurrentWeek();
			String futureWeekFilterText = filterWorkRoleButton.getText();
			if (workRoleText.equals(postWeekFilterText)) {
				SimpleUtils.pass("work role is  remain  seleced after switching to post week");
			} else {
				SimpleUtils.fail("Work Role is changed after switch to post week", true);
			}
			if (workRoleText.equals(futureWeekFilterText)) {
				SimpleUtils.pass("work role is remain seleced after switching to future week");
			} else {
				SimpleUtils.fail("Work Role is changed after switch to future week", true);
			}
		}
	}

	@Override
	public void weatherWeekSmartCardIsDisplayedForAWeek() throws Exception {
		clickOnLabor();
		String jsonTimeZoon = parametersMap2.get("Time_Zone");
		TimeZone timeZone = TimeZone.getTimeZone(jsonTimeZoon);
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
		dfs.setTimeZone(timeZone);
		String currentTime = dfs.format(new Date());
		int currentDay = Integer.valueOf(currentTime.substring(currentTime.length() - 2));
		//String firstDayInWeatherSmtCad2 = getDriver().findElement(By.xpath("//*[contains(text(),'Weather - Week of')]")).getText();
		try{
			String firstDayInWeatherSmtCad2 = getDriver().findElement(By.xpath("//*[contains(text(),'Weather - Week of')]")).getText();
			int firstDayInWeatherSmtCad = Integer.valueOf(firstDayInWeatherSmtCad2.substring(firstDayInWeatherSmtCad2.length() - 2));
			System.out.println("firstDayInWeatherSmtCad:" + firstDayInWeatherSmtCad);
			if ((firstDayInWeatherSmtCad + 7) > currentDay) {
				SimpleUtils.pass("The week smartcard is current week");
				if (areListElementVisible(weatherTemperatures, 8)) {
					String weatherWeekTest = getWeatherDayOfWeek();
					SimpleUtils.report("Weather smart card is displayed for a week from mon to sun" + weatherWeekTest);
					for (ConsoleScheduleNewUIPage.DayOfWeek e : ConsoleScheduleNewUIPage.DayOfWeek.values()) {
						if (weatherWeekTest.contains(e.toString())) {
							SimpleUtils.pass("Weather smartcard include one week weather");
						} else {
							SimpleUtils.fail("Weather Smart card is not one whole week", false);
						}
					}

				} else {
					SimpleUtils.fail("there is no week weather smartcard", false);
				}

			} else {
				SimpleUtils.fail("This is not current week weather smartcard ", false);
			}
		} catch (Exception e){
			SimpleUtils.warn("there is no week weather smartcard!");
		}
	}

	@Override
	public void clickOnLabor() throws Exception {
		if (isElementEnabled(laborTab, 2)) {
			click(laborTab);
			if (isElementLoaded(workRoleFilterTitle, 2)) {
				SimpleUtils.pass("labor tab is clickable");
			}
		} else {
			SimpleUtils.fail("labor tab load failed", true);
		}
	}
	@Override
	public void clickOnShopper() throws Exception {
		if (isElementEnabled(shoppersTab, 2)) {
			click(shoppersTab);
			if (isElementLoaded(workRoleFilterTitle, 2)) {
				SimpleUtils.pass("Shopper tab is clickable");
			}
		} else {
			SimpleUtils.fail("Shopper tab load failed", true);
		}
	}

	public void clickOnClearFilterInWorkRole() throws Exception {
		if (isElementEnabled(clearFilterBtn, 2)) {
			click(clearFilterBtn);
			if (isElementLoaded(workRoleFilterTitle, 2)) {
				SimpleUtils.pass("clear Filter Btn is clickable");
			}
		} else {
			SimpleUtils.fail("clear Filter Btn load failed", true);
		}
	}

	public void verifyWorkRoleSelection() throws Exception {

		if (isElementLoaded(filterButton, 10)) {
//			defaultValueIsAll();
			click(filterButton);
			clickOnClearFilterInWorkRole();
//			selectWorkRole();
		} else {
			SimpleUtils.fail("Work role filter load failed", false);
		}
	}



	private void defaultValueIsAll() throws Exception {
		String defaultWorkRoleText = "All";
		if (isElementLoaded(filterButton, 15)) {
			click(filterButton);
			String workRoleDefaultText = filterButtonText.getText().trim();
			if (defaultWorkRoleText.equals(workRoleDefaultText)) {
				SimpleUtils.pass("Work role filter is selected all roles by default");
			} else {
				SimpleUtils.fail("default work role value is not all", false);
			}

		}
	}

	public String getWeatherDayOfWeek() throws Exception {
		String daysText = "";
		if (weatherDaysOfWeek.size() != 0)
			for (WebElement weatherDay : weatherDaysOfWeek) {
				if (weatherDay.isDisplayed()) {
					if (daysText == "")
						daysText = weatherDay.getText();
					else
						daysText = daysText + " | " + weatherDay.getText();
				} else if (!weatherDay.isDisplayed()) {
					while (isSmartCardScrolledToRightActive() == true) {
						if (daysText == "")
							daysText = weatherDay.getText();
						else
							daysText = daysText + " | " + weatherDay.getText();
					}
				}
			}

		return daysText;
	}

	boolean isSmartCardScrolledToRightActive() throws Exception {
		if (isElementLoaded(smartcardArrowRight, 10) && smartcardArrowRight.getAttribute("class").contains("available")) {
			click(smartcardArrowRight);
			return true;
		}
		return false;
	}


	private void goToPostWeekNextToCurrentWeek() throws Exception {
		if (isElementLoaded(postWeekNextToCurrentWeek, 5)) {
			click(postWeekNextToCurrentWeek);
			SimpleUtils.pass("navigate to post week successfully");
		} else {
			SimpleUtils.fail("post week tab load failed", true);
		}
	}

	private void goToFutureWeekNextToCurrentWeek() throws Exception {
		if (isElementLoaded(futureWeekNextToCurrentWeek, 5)) {
			click(futureWeekNextToCurrentWeek);
			SimpleUtils.pass("navigate to future week successfully");
		} else {
			SimpleUtils.fail("future week tab load failed", true);
		}
	}

	@Override
	public HashMap<String, Float> getSummaryLaborHoursAndWages() throws Exception {
		HashMap<String, Float> summaryHoursAndWages = new HashMap<String, Float>();
		WebElement summaryLabelsDivElement = MyThreadLocal.getDriver().findElement(By.cssSelector(".card-carousel-card.card-carousel-card-primary.card-carousel-card-table"));
		if (isElementLoaded(summaryLabelsDivElement,5)) {
			String sumarySmartCardText = summaryLabelsDivElement.getText();
			String[] hoursAndBudgetInSummary = sumarySmartCardText.split("\n");
			for (String hoursAndBudget: hoursAndBudgetInSummary) {

				if(hoursAndBudget.toLowerCase().contains(ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.hours.getValue().toLowerCase()))
				{
					summaryHoursAndWages = ConsoleScheduleNewUIPage.updateScheduleHoursAndWages(summaryHoursAndWages , hoursAndBudget.split(" ")[1],
							"ForecastHours");
				}
				else if(hoursAndBudget.toLowerCase().contains(ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.wages.getValue().toLowerCase()))
				{
					summaryHoursAndWages = ConsoleScheduleNewUIPage.updateScheduleHoursAndWages(summaryHoursAndWages , hoursAndBudget.split(" ")[1]
							.replace("$", ""), "ForecastWages");
				}
			}
		}else {
			SimpleUtils.fail("there is no summary smart card",false);
		}
		return summaryHoursAndWages;
	}


	//updated by haya
	public HashMap<String, Float> getHoursBySelectedWorkRoleInLaborWeek(String workRole) throws Exception {
		HashMap<String, Float> hoursByWorkRole = new HashMap<String,Float>();
		if (areListElementVisible(hoursOfWorkRole,5)) {
			for (WebElement e :hoursOfWorkRole
				 ) {
				if (hoursByWorkRole.size()<0) {
					hoursByWorkRole.put(" operation - other role",0.0f);
				}else
				hoursByWorkRole.put(e.getText().split(":")[0],Float.valueOf(e.getText().split(":")[1].replaceAll("H","")));
			}
		}else {
			//SimpleUtils.fail("work roles hours load failed",false);
			hoursByWorkRole.put(workRole,Float.valueOf(0));
			SimpleUtils.warn("No data for selected work role!");
		}

		return hoursByWorkRole;
	}

	@Override
	public HashMap<String, Float> getInsightDataInShopperWeekView() throws Exception {
		HashMap<String, Float> insightData = new HashMap<String, Float>();
		WebElement insightIsDivElement = MyThreadLocal.getDriver().findElement(By.cssSelector(".card-carousel-fixed"));
		if (isElementLoaded(insightIsDivElement,5)) {
			String insightSmartCardText = insightIsDivElement.getText();

			String[] peakShopperDayInInsight = insightSmartCardText.split("\n");

			for (String peakShopperDay: peakShopperDayInInsight) {
				if (actualDataInSightSmartCard.size()> 0) {
					if(peakShopperDay.toLowerCase().contains("peak items"))
					{
						insightData = ConsoleScheduleNewUIPage.updateScheduleHoursAndWages(insightData , peakShopperDay.split(" ")[2],
								"peakItems");
						insightData = ConsoleScheduleNewUIPage.updateScheduleHoursAndWages(insightData , peakShopperDay.split(" ")[3],
								"actualPeakItems");
					}
					else if(peakShopperDay.toLowerCase().contains("peak shoppers"))
					{
						insightData = ConsoleScheduleNewUIPage.updateScheduleHoursAndWages(insightData , peakShopperDay.split(" ")[2],
								"peakShoppers");
						insightData = ConsoleScheduleNewUIPage.updateScheduleHoursAndWages(insightData , peakShopperDay.split(" ")[3],
								"actualPeakShoppers");
					}
					else if(peakShopperDay.toLowerCase().contains("peak day"))
					{
						Float peakday=null;
						Float actualPeakday=null;
						insightData.put(peakShopperDay.split(" ")[2],peakday);
						insightData.put(peakShopperDay.split(" ")[3],actualPeakday);
					}
					else if(peakShopperDay.toLowerCase().contains("total items"))
					{
						insightData = ConsoleScheduleNewUIPage.updateScheduleHoursAndWages(insightData , peakShopperDay.split(" ")[2],
								"totalItems");
						insightData = ConsoleScheduleNewUIPage.updateScheduleHoursAndWages(insightData , peakShopperDay.split(" ")[3],
								"actualTotalItems");
					}
					else if(peakShopperDay.toLowerCase().contains("total shoppers"))
					{
						insightData = ConsoleScheduleNewUIPage.updateScheduleHoursAndWages(insightData , peakShopperDay.split(" ")[2],
								"totalShoppers");
						insightData = ConsoleScheduleNewUIPage.updateScheduleHoursAndWages(insightData , peakShopperDay.split(" ")[3],
								"actualTotalShoppers");
					}
	//				else {
	//				SimpleUtils.fail("this data is not which i wanted,ig",true);
	//				}
				}else {
					if(peakShopperDay.toLowerCase().contains("peak items"))
					{
						insightData = ConsoleScheduleNewUIPage.updateScheduleHoursAndWages(insightData , peakShopperDay.split(" ")[2],
								"peakItems");
					}
					else if(peakShopperDay.toLowerCase().contains("peak shoppers"))
					{
						insightData = ConsoleScheduleNewUIPage.updateScheduleHoursAndWages(insightData , peakShopperDay.split(" ")[2],
								"peakShoppers");
					}
					else if(peakShopperDay.toLowerCase().contains("peak day"))
					{
						Float peakday=null;
						insightData.put(peakShopperDay.split(" ")[2],peakday);
					}
					else if(peakShopperDay.toLowerCase().contains("total items"))
					{
						insightData = ConsoleScheduleNewUIPage.updateScheduleHoursAndWages(insightData , peakShopperDay.split(" ")[2],
								"totalItems");
					}
					else if(peakShopperDay.toLowerCase().contains("total shoppers"))
					{
						insightData = ConsoleScheduleNewUIPage.updateScheduleHoursAndWages(insightData , peakShopperDay.split(" ")[2],
								"totalShoppers");
					}
	//				else {
	//				SimpleUtils.fail("this data is not which i wanted,ig",true);
	//				}
				}

			}
		}else {
			SimpleUtils.fail("there is no insight smart card",false);
		}

		return insightData;
	}


	//added by haya
	@FindBy(css = "lg-filter[label=\"Work role\"] .lg-filter__wrapper.lg-ng-animate div[ng-mouseover]")
	private List<WebElement> workRoleFilter;
	@Override
	public void verifyBudgetedHoursInLaborSummaryWhileSelectDifferentWorkRole() throws Exception {
		for (WebElement e:workRoleFilter
			 ) {
			e.click();
			String workRoleText = e.getText();
			SimpleUtils.pass("work role ‘ " + workRoleText + " ’ is selected");
			HashMap<String, Float> HoursBySelectedWorkRoleInLaborWeek = getHoursBySelectedWorkRoleInLaborWeek(workRoleText.toUpperCase());
			HashMap<String, Float> hoursAndWedgetInSummary = getSummaryLaborHoursAndWages();
			System.out.println(hoursAndWedgetInSummary.get("ForecastHours"));
			System.out.println(HoursBySelectedWorkRoleInLaborWeek.get(workRoleText.toUpperCase()));
			System.out.println(hoursAndWedgetInSummary.get("ForecastHours") == HoursBySelectedWorkRoleInLaborWeek.get(workRoleText.toUpperCase()));
			if (hoursAndWedgetInSummary.get("ForecastHours") >= HoursBySelectedWorkRoleInLaborWeek.get(workRoleText.toUpperCase()) && hoursAndWedgetInSummary.get("ForecastHours") <= HoursBySelectedWorkRoleInLaborWeek.get(workRoleText.toUpperCase())){
				SimpleUtils.pass("Smartcard's budgeted hours are matching with the sum of work role hours");
			}else
				SimpleUtils.fail("Smartcard budget hours are not matching with selected work roles hours",true);
		    e.click();
		}

	}

		@Override
		public void verifyRefreshBtnInLaborWeekView() throws Exception {
			if (isElementLoaded(weatherWeekSmartCardHeader,10)) {
		        String defaultText = getDriver().findElement(By.cssSelector(".card-carousel.row-fx")).getText();
				SimpleUtils.report("content before default is : "+defaultText);
				if (isElementLoaded(refreshBtn,10)){
					click(refreshBtn);
					SimpleUtils.pass("refresh is clickable");
				}else{
					SimpleUtils.fail("Refresh button load failed",true);
				}
				waitForSeconds(10);//wait to load the page data
				String textAftRefresh =  getDriver().findElement(By.cssSelector(".card-carousel.row-fx")).getText();
				SimpleUtils.report("content after refresh is:"+textAftRefresh);
				if(defaultText.equals(textAftRefresh)){
					SimpleUtils.pass("page get refresh ");
				}
			}else {
				//SimpleUtils.fail("Refresh button load failed",true);
				SimpleUtils.warn("Weather smart card is not loaded!");
			}
		}

	@Override
	public void verifyRefreshBtnInShopperWeekView() throws Exception {
		if (isElementLoaded(weatherSmartCard,10)) {
			String defaultText1 = insightSmartCard.getText();
			SimpleUtils.report(defaultText1);
			if (isElementLoaded(refreshBtn,10)){
				click(refreshBtn);
				SimpleUtils.pass("refresh is clickable");
			}else{
				SimpleUtils.fail("Refresh button load failed",true);
			}
			waitForSeconds(10);//wait to load the page data
			String textAftRefresh1 = insightSmartCard.getText();
			SimpleUtils.report(textAftRefresh1);
			if((textAftRefresh1).equals(defaultText1)){
				SimpleUtils.pass("page back to previous page ");
			}else {
				SimpleUtils.fail("after refresh, the page changed",true);
			}
		}else {
			//SimpleUtils.fail("Refresh button load failed",true);
			SimpleUtils.warn("Weather smart card is not loaded!");
		}
	}

	@Override
	public void verifySmartcardAreAvailableInShoppers() throws Exception {
		if (isElementLoaded(insightSmartCard,5) & isElementLoaded(weatherSmartCard,5)) {
			SimpleUtils.pass("Insight and Weather smart card is displayed");
		}
		else {
			//SimpleUtils.fail("smart card load failed",false);
			SimpleUtils.warn("insightSmartCard or weatherSmartCard load failed");
		}
	}


	public void shopperFilterInForecast(ArrayList<WebElement> shiftTypeFilters) {

		try {
			if (areListElementVisible(shiftTypeFilters, 10)) {
				for (WebElement e : shiftTypeFilters
				) {
					click(e);
					String filterName = e.getText();
					SimpleUtils.pass( filterName + " is selected");
					SimpleUtils.report(filterName+"insight data's is:"+getInsightDataInShopperWeekView());

				}
			} else {
				SimpleUtils.fail("can't select work role", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public List<String> getForecastBarGraphData() throws Exception {
		List<String> barGraphDataForEachDay = new ArrayList<>();
		if (isElementLoaded(insightSmartCard, 5) & isElementLoaded(barChartInProjected, 5)) {
			for (int i = 0; i < forecastBarInProjected.size(); i++) {
				mouseToElement(forecastBarInProjected.get(i));
				/*
				 *wait tooptip data load
				 * */
				waitForSeconds(2);
				if (tooltipInProjected.getText().contains("N/A")){
					barGraphDataForEachDay.add(tooltipInProjected.getText().replace("\n", " ").replace("N/A","0"));
				} else {
					barGraphDataForEachDay.add(tooltipInProjected.getText().replace("\n", " "));
				}
				waitForSeconds(1);
			}
		}
		return barGraphDataForEachDay;
	}


		@Override
		public void verifyPeakShopperPeakDayData () throws Exception {
			HashMap<String, Float> insightDataInWeek = new HashMap<String, Float>();
			insightDataInWeek = getInsightDataInShopperWeekView();
			List<String> dataInBar = getForecastBarGraphData();
			SimpleUtils.report("data in bar graph is :"+dataInBar);
			Float max = 0.0f;
			Float totalItemsInbar = 0.0f;

			for (int i = 0; i < dataInBar.size(); i++) {
				String totalShoppersInBar = dataInBar.get(i).split(" ")[3];
				String forecastInBar = dataInBar.get(i).split(" ")[6];
				if (totalShoppersInBar.contains(",")) {
					totalShoppersInBar = totalShoppersInBar.replaceAll(",","");
				}else totalShoppersInBar = totalShoppersInBar;
				if (forecastInBar.contains(",")){
					forecastInBar = forecastInBar.replaceAll(",","");
				}else forecastInBar=forecastInBar;
				try {
					totalItemsInbar +=Float.valueOf (totalShoppersInBar);
					if (max <= Float.valueOf (forecastInBar)) {
						max=Float.valueOf(forecastInBar);
					}
					else {
						max=max;
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

			}
			SimpleUtils.report("max number in bar graph:"+max);
			SimpleUtils.report("total items in bar graph:"+totalItemsInbar);
			Float totalShoppersInInsightcard = insightDataInWeek.get("totalShoppers");
			try {
				if (insightDataInWeek.get("totalShoppers").equals(totalItemsInbar) & insightDataInWeek.get("peakShoppers").equals(max)) {
				   SimpleUtils.pass("In smart card ,total shoppers and peak shoppers  are  matching with bar graph");
				}
				else {
					SimpleUtils.warn("BUG existed-->SF-418:data in Insight smart card is not matching with bar graph");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

   public void schedulingWindowScrolleToLeft() throws Exception {
		if (isElementLoaded(schedulingWindowArrowLeft, 2)) {
				click(schedulingWindowArrowLeft);
		}
	}

	@Override
	public void verifyActualDataForPastWeek() throws Exception {
		HashMap<String, Float> insightDataInWeek = new HashMap<String, Float>();
		schedulingWindowScrolleToLeft();
		goToPostWeekNextToCurrentWeek();
		insightDataInWeek = getInsightDataInShopperWeekView();
		List<String> dataInBar = getForecastBarGraphData();
		Float max = 0.0f;
		Float actualTotalShoppersInbar =0.0f;

		for (int i = 0; i < dataInBar.size(); i++) {
			if (dataInBar.get(i).split(" ").length > 9){
				String actualShoppers = dataInBar.get(i).split(" ")[9];
				String forecastInBar = dataInBar.get(i).split(" ")[6];
				if (actualShoppers.contains(",")) {
					actualShoppers = actualShoppers.replaceAll(",","");
				}else actualShoppers = actualShoppers;
				try {
					actualTotalShoppersInbar +=Float.valueOf(actualShoppers);
					if (max <=Float.valueOf(actualShoppers)) {
						max=Float.valueOf(actualShoppers);
					}
					else {
						max=max;
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			} else {
				SimpleUtils.fail("actual value in tooltip is not loaded!",true);
			}
		}
		SimpleUtils.report("max actual data in bar graph for this week is :"+max);
		SimpleUtils.report("actual total shoppers in bar graph for this week is "+actualTotalShoppersInbar);

		try {
			if (insightDataInWeek.get("actualTotalShoppers").equals(actualTotalShoppersInbar)  & insightDataInWeek.get("actualPeakShoppers").equals(max) )  {
				SimpleUtils.pass("In smart card ,total shoppers and peak shoppers  are  matching with bar graph");
			}
			else {
				//SimpleUtils.fail("data in Insight smart card is not matching with bar graph",false);
				SimpleUtils.warn("actual total data in Insight smart card is not matching with bar graph");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void verifyFilterFunctionInForecast() throws Exception {
		ArrayList<WebElement> availableFilters = getAvailableFilters();
		shopperFilterInForecast(availableFilters);

	}

	@FindBy(css = "[ng-repeat=\"(key, opts) in $ctrl.displayFilters\"]")
	private List<WebElement> scheduleFilterElements;

	public ArrayList<WebElement> getAvailableFilters() {
		ArrayList<WebElement> filterList = new ArrayList<WebElement>();
		try {
			if (isElementLoaded(filterButtonForShopper,10)) {
				if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
					click(filterButtonForShopper);
				for (WebElement scheduleFilterElement : scheduleFilterElements) {
					List<WebElement> filters = scheduleFilterElement.findElements(By.cssSelector("input-field[type=\"checkbox\"]"/*"[ng-repeat=\"opt in opts\"]"*/));
					for (WebElement filter : filters) {
						if (filter != null) {
							filterList.add(filter);
						}

					}
				}
			} else {
				SimpleUtils.fail("Filters button not found on forcast page!", false);
			}
		} catch (Exception e) {
			SimpleUtils.fail("Filters button not loaded successfully on Schedule page!", true);
		}
		return filterList;
	}


	public boolean verifyIsShopperTypeSelectedByDefaultAndLaborTabIsClickable() throws Exception {
		boolean flag=false;
		if (isElementLoaded(shoppersTab,5)) {
			if (shoppersTab.findElement(By.cssSelector("span")).getText().toLowerCase().contains("shopper")){
				if (shoppersTab.getAttribute("class").contains("selected")) {
					SimpleUtils.pass("shopper forecast is selected by default");
					clickOnLabor();
					flag = true;
				}else {
					SimpleUtils.fail("shopper forecast is not selected by default",false);
				}
			} else {
				SimpleUtils.warn("Shopper tap is not loaded!");
			}
		}else {
			flag = false;
			SimpleUtils.fail("verifyIsShopperTypeSelectedByDefaultAndLaborTabIsClickable : forecast tab load failed",false);
		}
		return flag;
	}


	public boolean verifyIsWeekForecastVisibleAndOpenByDefault() throws Exception {
		boolean flag=false;
		goToPostWeekNextToCurrentWeek();
		if (isElementLoaded(weekViewButton,25)) {
			String aaa = weekViewButton.getAttribute("class");
			if (weekViewButton.getAttribute("class").contains("selected")) {
				SimpleUtils.pass("week forecast is selected by default");
				if (weatherTemperatures.size()>=6) {
					flag=true;
					SimpleUtils.pass("week forecast is open");
				} else {
					flag=true;
					SimpleUtils.warn("No weather smart card!");
				}
			}else {
				SimpleUtils.fail("weekly forecast is not selected by default",false);
			}
		}else {
			flag =false;
			SimpleUtils.fail("weekly button load failed",false);
		}
		return flag;
	}

}
