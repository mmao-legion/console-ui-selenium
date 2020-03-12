package com.legion.pages;

import java.util.HashMap;

public interface ForecastPage<pubic> {

	public void loadShoppersForecastforCurrentNFutureWeek(String nextWeekView, int weekCount) throws Exception;
	public void loadShoppersForecastforPastWeek(String nextWeekView, int weekCount) throws Exception;
	public void loadLaborForecastforCurrentNFutureWeek(String nextWeekView, int weekCount) throws Exception;
	public void loadLaborForecastforPastWeek(String nextWeekView, int weekCount) throws Exception;

	public void holidaySmartCardIsDisplayedForCurrentAWeek() throws Exception;
	public void clickForecast()  throws Exception;
	public void verifyNextPreviousBtnCorrectOrNot() throws Exception;

	public void verifyDisplayOfActualLineSelectedByDefaultInOrangeColor() throws Exception;

	public void verifyRecentTrendLineIsSelectedAndColorInBrown() throws Exception;

	public void verifyLastYearLineIsSelectedAndColorInPurple() throws Exception;

	public void verifyForecastColourIsBlue() throws Exception;
	public void verifyWorkRoleIsSelectedAftSwitchToPastFutureWeek() throws Exception;

	public void weatherWeekSmartCardIsDisplayedForAWeek() throws Exception;

	public void clickOnLabor() throws Exception;
	public void clickOnShopper() throws Exception;

	public void verifyWorkRoleSelection() throws Exception;

	public HashMap<String, Float> getSummaryLaborHoursAndWages()  throws Exception;

	public HashMap<String, String> getHoursBySelectedWorkRoleInLaborWeek()  throws Exception;

	public void verifyBudgetedHoursInLaborSummaryWhileSelectDifferentWorkRole() throws Exception;

	public void verifyRefreshBtnInLaborWeekView() throws Exception;

	public void  verifyRefreshBtnInShopperWeekView()throws Exception;

	public void verifySmartcardAreAvailableInShoppers() throws Exception;

	public HashMap<String, Float> getInsightDataInShopperWeekView() throws Exception;

	public void verifyPeakShopperPeakDayData() throws Exception;

    public void verifyActualDataForPastWeek() throws Exception;

	public void verifyFilterFunctionInForecast() throws Exception;
}
