package com.legion.pages;

import java.util.HashMap;
import java.util.Map;

public interface ForecastPage {

	public void loadShoppersForecastforCurrentNFutureWeek(String nextWeekView, int weekCount) throws Exception;
	public void loadShoppersForecastforPastWeek(String nextWeekView, int weekCount) throws Exception;
	public void loadLaborForecastforCurrentNFutureWeek(String nextWeekView, int weekCount) throws Exception;
	public void loadLaborForecastforPastWeek(String nextWeekView, int weekCount) throws Exception;

	public void holidaySmartCardIsDisplayedForCurrentAWeek() throws Exception;
	public void clickForecast()  throws Exception;
	public void verifyNextPreviousBtnCorrectOrNot() throws Exception;
}
