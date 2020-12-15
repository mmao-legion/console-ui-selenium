package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.core.ConsoleForecastPage;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.*;

import static java.lang.Math.abs;

public class ForecastTest extends TestBase{


	public enum ForecastCalenderWeekCount{
		ZERO(0),
		One(1),
		Two(2),	
		Three(3);
		private final int value;
		ForecastCalenderWeekCount(final int newValue) {
            value = newValue;
        }
        public int getValue() { return value; }
	}

	@Override
	  @BeforeMethod()
	  public void firstTest(Method testMethod, Object[] params) throws Exception{
		  this.createDriver((String)params[0],"69","Window");
	      visitPage(testMethod);
	      loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
	  }


	@Automated(automated ="Automated")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate navigation and data loading in Day/Week view for Shoppers Subtab Of Forecast Tab ")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void forecastShoppersSubTabNavigationStoreManager(String username, String password, String browser, String location) throws Exception {
		ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
		scheduleOverviewPage.loadScheduleOverview();
		ForecastPage ForecastPage = pageFactory.createForecastPage();
		ForecastPage.loadShoppersForecastforPastWeek(ScheduleTest.weekViewType.Next.getValue(), ScheduleTest.weekCount.One.getValue());
		scheduleOverviewPage.loadScheduleOverview();
		ForecastPage.loadShoppersForecastforCurrentNFutureWeek(ScheduleTest.weekViewType.Next.getValue(), ScheduleTest.weekCount.Two.getValue());
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate navigation and data loading in Day/Week view for Labor Subtab Of Forecast Tab")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void forecastLaborSubTabNavigationStoreManager(String username, String password, String browser, String location) throws Exception {
		ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
		scheduleOverviewPage.loadScheduleOverview();
		ForecastPage ForecastPage = pageFactory.createForecastPage();
		ForecastPage.loadLaborForecastforPastWeek(ScheduleTest.weekViewType.Next.getValue(), ScheduleTest.weekCount.One.getValue());
		scheduleOverviewPage.loadScheduleOverview();
		ForecastPage.loadLaborForecastforCurrentNFutureWeek(ScheduleTest.weekViewType.Next.getValue(), ScheduleTest.weekCount.Two.getValue());
	}



		@Automated(automated = "Automated")
		@Owner(owner = "Estelle")
		@Enterprise(name = "KendraScott2_Enterprise")
		@TestName(description = "Verify the Schedule functionality  Shopper Forecast")
		@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
		public void verifyShopperForecastFunctionality(String username, String password, String browser, String location)
				throws Exception {
			ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			scheduleOverviewPage.loadScheduleOverview();
			ForecastPage ForecastPage  = pageFactory.createForecastPage();
			ForecastPage.clickForecast();
		    //In Shoppers,Smartcard are available in correct alignment
			ForecastPage.verifySmartcardAreAvailableInShoppers();
			//In Holiday smartcard,View All button is clickable and Current week's Holidays are showing
			ForecastPage.holidaySmartCardIsDisplayedForCurrentAWeek();
			//in Shopper insight smartcard, Peak Shopper and Peek day data is matching with Forecast Bar chart
			ForecastPage.verifyPeakShopperPeakDayData();
			//there is no data if store is closed
			HashMap<String, Float> insightData1 = new HashMap<String, Float>();
			Float[] peakItemsShoppers = new Float[7];
			Float[] totalItemsShoppers = new Float[7];
			Float sum = 0.0f;
			HashMap<String, Float> insightDataInWeek = new HashMap<String, Float>();
			insightDataInWeek = ForecastPage.getInsightDataInShopperWeekView();
			schedulePage.clickOnDayView();
			//click the first day of a week
			schedulePage.clickOnPreviousDaySchedule("Sun");
			for (int index = 0; index < ConsoleScheduleNewUIPage.dayCount.Seven.getValue(); index++) {
				schedulePage.clickOnNextDaySchedule(schedulePage.getActiveAndNextDay());
				if (schedulePage.inActiveWeekDayClosed(index)){
					SimpleUtils.report("Store is closed and there is no insight smartc");
				}else {
					insightData1 = ForecastPage.getInsightDataInShopperWeekView();
					peakItemsShoppers[index] =insightData1.get("peakShoppers");
					totalItemsShoppers[index] =insightData1.get("totalShoppers");
					sum+=totalItemsShoppers[index];
					SimpleUtils.report("Store is Open for the Day/Week: '" + schedulePage.getActiveWeekText() + ":"+insightData1);
				}
			}
			if (insightDataInWeek.get("totalShoppers").equals(sum)) {
				SimpleUtils.pass("Total Shoppers data is sum of total data in day view and Peak shoppers data is correct");
			}else {
				//SimpleUtils.fail("verifyShopperForecastFunctionality: Total Shoppers data is wrong",true);
				SimpleUtils.warn("BUG existed-->SF-418:Total Shoppers data is wrong!");
			}
			schedulePage.clickOnWeekView();
			//navigate to <> buttons are working
			ForecastPage.verifyNextPreviousBtnCorrectOrNot();
			//After selecting of all display filter option, data in Projected shoppers is showing according to filters
			//Todo: Run failed by LEG-10179
//			ForecastPage.verifyFilterFunctionInForecast();
			//Navigate to 2 past week, match the Actual data of smartcard with Projected shoppers of week
			//Todo: Run failed by LEG-10237
			ForecastPage.verifyActualDataForPastWeek();
			//verify display lines color
			ForecastPage.verifyDisplayOfActualLineSelectedByDefaultInOrangeColor();
			ForecastPage.verifyRecentTrendLineIsSelectedAndColorInBrown();
			ForecastPage.verifyLastYearLineIsSelectedAndColorInPurple();
			ForecastPage.verifyForecastColourIsBlue();
			//After Click on Refresh Button,it should navigate back to page
			ForecastPage.verifyRefreshBtnInShopperWeekView();

		}


	@Automated(automated = "Automated")
	@Owner(owner = "Estelle")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the Schedule functionality > Shopper Forecast> Weather smartcard")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void validateWeatherSmartCardOnForecastPage(String username, String password, String browser, String location)
			throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
		SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
				schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

		String WeatherCardText = "WEATHER";
		ForecastPage forecastPage = pageFactory.createForecastPage();
		forecastPage.clickForecast();
		//Validate Weather Smart card on Week View
		schedulePage.clickOnWeekView();

		Thread.sleep(5000);
		String activeWeekText = schedulePage.getActiveWeekText();

		if (schedulePage.isSmartCardAvailableByLabel(WeatherCardText)) {
			SimpleUtils.pass("Weather Forecart Smart Card appeared for week view duration: '" + activeWeekText + "'");
			String[] splitActiveWeekText = activeWeekText.split(" ");
			String smartCardTextByLabel = schedulePage.getsmartCardTextByLabel(WeatherCardText);
			String weatherTemperature = schedulePage.getWeatherTemperature();

			SimpleUtils.assertOnFail("Weather Forecart Smart Card not contain starting day('" + splitActiveWeekText[0] + "') of active week: '" + activeWeekText + "'",
					smartCardTextByLabel.toLowerCase().contains(splitActiveWeekText[0].toLowerCase()), true);

			SimpleUtils.assertOnFail("Weather Forecart Smart Card not contain Ending day('" + splitActiveWeekText[0] + "') of active week: '" + activeWeekText + "'",
					smartCardTextByLabel.toLowerCase().contains(splitActiveWeekText[0].toLowerCase()), true);
			if (weatherTemperature != "")
				SimpleUtils.pass("Weather Forecart Smart Card contains Temperature value: '" + weatherTemperature + "' for the duration: '" +
						activeWeekText + "'");
			else
				SimpleUtils.fail("Weather Forecart Smart Card not contains Temperature value for the duration: '" + activeWeekText + "'", true);
		} else {
			//SimpleUtils.fail("Weather Forecart Smart Card not appeared for week view duration: '" + activeWeekText + "'", true);
			SimpleUtils.warn("Weather Forecart Smart Card not appeared for week view duration: '" + activeWeekText + "'");
		}

		//Validate Weather Smart card on day View
		schedulePage.clickOnDayView();
		for (int index = 0; index < ScheduleNewUITest.dayCount.Seven.getValue(); index++) {
			if (index != 0)
				schedulePage.navigateWeekViewOrDayViewToPastOrFuture(ScheduleNewUITest.weekViewType.Next.getValue(), ScheduleNewUITest.weekCount.One.getValue());

			String activeDayText = schedulePage.getActiveWeekText();
			if (schedulePage.isSmartCardAvailableByLabel(WeatherCardText)) {
				SimpleUtils.pass("Weather Forecart Smart Card appeared for week view duration: '" + activeDayText + "'");
				String[] splitActiveWeekText = activeDayText.split(" ");
				String smartCardTextByLabel = schedulePage.getsmartCardTextByLabel(WeatherCardText);
				SimpleUtils.assertOnFail("Weather Forecart Smart Card not contain starting day('" + splitActiveWeekText[1] + "') of active day: '" + activeDayText + "'",
						smartCardTextByLabel.toLowerCase().contains(splitActiveWeekText[1].toLowerCase()), true);
				String weatherTemperature = schedulePage.getWeatherTemperature();
				if (weatherTemperature != "")
					SimpleUtils.pass("Weather Forecart Smart Card contains Temperature value: '" + weatherTemperature + "' for the duration: '" +
							activeWeekText + "'");
				else
					SimpleUtils.pass("Weather Forecart Smart Card not contains Temperature value for the duration: '" + activeWeekText + "'");
			} else {
				//SimpleUtils.fail("Weather Forecart Smart Card not appeared for week view duration: '" + activeWeekText + "'", true);
				SimpleUtils.warn("Weather Forecart Smart Card not appeared for week view duration: '" + activeWeekText + "'");
			}
		}
	}



		@Automated(automated = "Automated")
		@Owner(owner = "Estelle")
		@Enterprise(name = "KendraScott2_Enterprise")
		@TestName(description = "Verify the Schedule functionality forecast")
		@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
		public void verifyScheduleFunctionalityForecast(String username, String password, String browser, String location)
				throws Exception {
			ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
			scheduleOverviewPage.loadScheduleOverview();
			ForecastPage ForecastPage  = pageFactory.createForecastPage();
			ForecastPage.clickForecast();
			boolean isWeekForecastVisibleAndOpen = ForecastPage.verifyIsWeekForecastVisibleAndOpenByDefault();
			boolean isShopperSelectedByDefaultAndLaborClickable = ForecastPage.verifyIsShopperTypeSelectedByDefaultAndLaborTabIsClickable();
			if (isWeekForecastVisibleAndOpen) {
				if (isShopperSelectedByDefaultAndLaborClickable){
					SimpleUtils.pass("Forecast Functionality show well");
				} else {
					SimpleUtils.warn("there is no shopper in this enterprise!");
				}
			}else {
				SimpleUtils.warn("forecast default functionality work error");
			}
		}

		@Automated(automated = "Automated")
		@Owner(owner = "Estelle")
		@Enterprise(name = "KendraScott2_Enterprise")
		@TestName(description = "Verify the Schedule functionality  Labor Forecast")
		@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
		public void verifyScheduleLaborForeCastFunctionality(String username, String password, String browser, String location)
				throws Exception {
			ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
			scheduleOverviewPage.loadScheduleOverview();
			ForecastPage ForecastPage  = pageFactory.createForecastPage();
			ForecastPage.clickForecast();
			ForecastPage.clickOnLabor();
			//past+current+future week is visible and enable to navigate to future and past week by '>' and '<' button
			ForecastPage.verifyNextPreviousBtnCorrectOrNot();
			//Work role filter is selected all roles by default, can be selected one or more
			ForecastPage.verifyWorkRoleSelection();
           //After selecting any workrole, Projected Labor bar will display according to work role
			ForecastPage.verifyBudgetedHoursInLaborSummaryWhileSelectDifferentWorkRole();
			//Weather week smartcard is displayed for a week[sun-sat]
			ForecastPage.weatherWeekSmartCardIsDisplayedForAWeek();
			//If some work role has been selected in one week then these will remain selected in every past and future week
			ForecastPage.verifyWorkRoleIsSelectedAftSwitchToPastFutureWeek();
			//After click on refresh, page should get refresh and back to previous page only
			ForecastPage.verifyRefreshBtnInLaborWeekView();

		}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "Coffee_Enterprise")
	@TestName(description = "Verify Edit Forecast in Week view")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyEditForecastInWeekViewViewAsInternalAdmin(String browser, String username, String password, String location) {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			ForecastPage forecastPage  = pageFactory.createForecastPage();
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Forecast.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Forecast' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Forecast.getValue()) , false);
			//verify edit forecast button
			int index = 3;
			String value = "510";
			String weekDayInfo = forecastPage.getTickByIndex(index);
			String editedValueInfo = value+" Edited";
			forecastPage.verifyAndClickEditBtn();
			forecastPage.verifyAndClickCancelBtn();
			forecastPage.verifyAndClickEditBtn();

			//verify double click graph bar
			forecastPage.verifyDoubleClickAndUpdateForecastBarValue(String.valueOf(index), value);
			String tooltipInfo =forecastPage.getTooltipInfo(String.valueOf(index));
			boolean flag = tooltipInfo.contains("Actual")||tooltipInfo.contains("Last Year")||tooltipInfo.contains("Recent Trend");
//		SimpleUtils.assertOnFail("Info on tooltip is incorrect!",tooltipInfo.contains(weekDayInfo+" Forecast")&&tooltipInfo.contains(editedValueInfo)&&tooltipInfo.contains("Comparison")&&flag,false);
			//Save forecast and check the value.
			forecastPage.verifyAndClickSaveBtn();
			tooltipInfo =forecastPage.getTooltipInfo(String.valueOf(index));
//		SimpleUtils.assertOnFail("Edited value is not saved!",tooltipInfo.contains(value),false);
			forecastPage.verifyAndClickEditBtn();
			schedulePage.navigateToNextWeek();
			forecastPage.verifyWarningEditingForecast();
			forecastPage.verifyLegionPeakShopperFromForecastGraphInWeekView();
			//Verify graph bars are draggable.
			forecastPage.verifyDraggingBarGraph();
			//Save forecast and check the value.
			forecastPage.verifyAndClickSaveBtn();
			forecastPage.verifyLegionPeakShopperFromForecastGraphInWeekView();
			forecastPage.verifyAndClickCancelBtn();
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Julie")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify Edit Forecast in Day View")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyEditForecastInDayViewViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

		ForecastPage forecastPage  = pageFactory.createForecastPage();
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Forecast.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Forecast' sub tab not loaded Successfully!",
				schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Forecast.getValue()),false);
        forecastPage.clickOnDayView();
        String currentDay = forecastPage.getActiveDayText();

		// Verify the presence of Edit button on Forecast page
        forecastPage.verifyEditBtnVisible();

        // Verify Edit button is clickable
		forecastPage.verifyAndClickEditBtn();

		// Verify the content after clicking on Edit button on Forecast page
		forecastPage.verifyContentInEditMode();

		// Verify warning message pops up when clicking other day in Edit mode, and verify OK button is clickable on the warning pop up model
        forecastPage.navigateToOtherDay();
        forecastPage.verifyWarningEditingForecast();

        // Verify cancel button is clickable
		forecastPage.verifyAndClickCancelBtn();

		// Verify mouse hover on each bar graph
		forecastPage.verifyAndClickEditBtn();
		forecastPage.verifyTooltipWhenMouseEachBarGraph();

		// Verify the Peak value, Peak Time, Total Sum are correct on smart card
		forecastPage.verifyPeakShoppersPeakTimeTotalShoppersLegionDataInDayView();

		// Verify dragging the bar graph
       forecastPage.verifyDraggingBarGraph();

        // Verify double clicking the bar graph
		String index = "3";
		String legionValueInBar = forecastPage.getLegionValueFromBarTooltip(Integer.valueOf(index));
		forecastPage.verifyDoubleClickBarGraph(index);

		// Verify the content of Specify a value layout
		forecastPage.verifyContentOfSpecifyAValueLayout(index,legionValueInBar);

		// Verify Close button on pop up is clickable
        forecastPage.verifyAndClickCloseBtn();

        // Verify Cancel button on Pop up is clickable
		forecastPage.verifyDoubleClickBarGraph(index);
		forecastPage.verifyAndClickCancelBtn();

		// Verify inputting the new value when double clicking the bar graph
		String editedValue = "2";
		forecastPage.verifyDoubleClickAndUpdateForecastBarValue(index, editedValue);

		// Verify OK button on pop up is clickable
		forecastPage.verifyDoubleClickBarGraph(index);
		forecastPage.verifyAndClickOKBtn();

		// Verify the value is changed to the new value and percentage when mouse hovering
		String editedValueInBar = forecastPage.getEditedValueFromBarTooltip(Integer.valueOf(index));
		String percentageInBar = forecastPage.getPercentageFromBarTooltip(Integer.valueOf(index));
		String percentageExpected = "";
		if (legionValueInBar.equals("0"))
			percentageExpected = "↑%";
		else {
			if (Integer.valueOf(legionValueInBar) > Integer.valueOf(editedValue))
				percentageExpected = "↓" + (Integer.valueOf(legionValueInBar) - Integer.valueOf(editedValue))* 100/Integer.valueOf(legionValueInBar)  + "%";
			else
				percentageExpected = "↑" + (Integer.valueOf(editedValue) - Integer.valueOf(legionValueInBar))*100/Integer.valueOf(legionValueInBar) + "%";
		}
		if (editedValueInBar.equals(editedValue) && percentageInBar.equals(percentageExpected))
			SimpleUtils.pass("Forecast Page: The value is changed to the new value and percentage when mouse hovering");
		else
			SimpleUtils.warn("SCH-2396: Failed to update the value when double clicking the bar graph");

		// Verify the Confirm Message when saving the edits
		forecastPage.verifyAndClickSaveBtn();
		forecastPage.verifyConfirmMessageWhenSaveForecast("day", currentDay);

		// Verify the Cancel button is clickable on the confirm pop up
        forecastPage.clickOnCancelBtnOnConfirmPopup();

		// Verify Save forecast button is clickable on the confirm pop up
		forecastPage.verifyAndClickSaveBtn();
		forecastPage.clickOnSaveBtnOnConfirmPopup();

		// Verify the values are saved successfully
		String newValueInBar = forecastPage.getLegionValueFromBarTooltip(Integer.valueOf(index));
		if (newValueInBar.equals(editedValue))
			SimpleUtils.pass("Forecast Page: the values are saved successfully");
		else
			SimpleUtils.fail("Forecast Page: the values failed to save",false);

		// Verify the value on smart card is correct after saving
		forecastPage.verifyPeakShoppersPeakTimeTotalShoppersEditedDataInDayView();
	}
}
