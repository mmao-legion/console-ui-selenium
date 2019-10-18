package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.ForecastPage;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleForecastPage extends BasePage implements ForecastPage {

//	@FindBy(xpath="//[name()='svg']/[name()='g']")
//	private WebElement forecastGraph;

	@FindBy(xpath = "//*[name()='svg']/*[name()='g']//*[name()='rect' and @class ='bar']")
	private List<WebElement> forecastGraph;

	@FindBy(xpath = "//div[contains(@class,'sub-navigation-view')]//span[contains(text(),'Forecast')]")
	private WebElement forecastTab;

	@FindBy(css = "div.day-week-picker-arrow-right")
	private WebElement forecastCalendarNavigationNextWeekArrow;

	@FindBy(xpath = "//span[contains(@class,'buttonLabel')][contains(text(),'Week')]")
	private WebElement weekViewButton;

	@FindBy(xpath = "//span[contains(@class,'buttonLabel')][contains(text(),'Day')]")
	private WebElement dayViewButton;

	@FindBy(xpath = "//span[contains(@class,'buttonLabel')][contains(text(),'Shoppers')]")
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
			}else{
				SimpleUtils.fail("Shoppers subtab of forecast tab not found",false);
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
				if (forecastGraph.size() != 0 && (Float.parseFloat(laborSmartCardForecast.getText())) > 0 && (Float.parseFloat(laborSmartCardBudget.getText()))>0) {
					SimpleUtils.pass("Labor Forecast Loaded in DayView Successfully! for " + currentActiveDay.getText() + " Labor Forecast is " + laborSmartCardForecast.getText() +  " Labor Budget is " + laborSmartCardBudget.getText() + " Hour");
				} else if (isElementLoaded(storeClosedView, 10)) {
					SimpleUtils.pass("Store Closed on " + currentActiveDay.getText());
				} else {
					SimpleUtils.fail("Labor Forecast Not Loaded in DayView for " + currentActiveDay.getText() + " Labor Forecast is " + laborSmartCardForecast.getText() +  " Labor Budget is " + laborSmartCardBudget.getText() + " Hour", true);
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
					SimpleUtils.pass("Labor Forecast Loaded in Week View Successfully! for week starting " + weekDuration[1] + " Labor Forecast is " + laborSmartCardForecast.getText() +  " Labor Budget is " + laborSmartCardBudget.getText() + " Hour");
					forecastLaborDayNavigation(weekDuration[1]);
				} else {
					SimpleUtils.fail("Labor Forecast Not Loaded in Week View for Week Starting " + weekDuration[1] +" Labor Forecast is " + laborSmartCardForecast.getText() +" Labor Budget is " + laborSmartCardBudget.getText() + " Hour", true);
				}
			}else{
				SimpleUtils.fail("Labor subtab of forecast tab not found",false);
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
}