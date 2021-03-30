package com.legion.pages;

import java.util.HashMap;
import java.util.List;

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

	public HashMap<String, String> getHoursBySelectedWorkRoleInLaborWeek(String workRole)  throws Exception;

	public void verifyBudgetedHoursInLaborSummaryWhileSelectDifferentWorkRole() throws Exception;

	public void verifyRefreshBtnInLaborWeekView() throws Exception;

	public void  verifyRefreshBtnInShopperWeekView()throws Exception;

	public void verifySmartcardAreAvailableInShoppers() throws Exception;

	public HashMap<String, Float> getInsightDataInShopperWeekView() throws Exception;

	public void verifyPeakShopperPeakDayData() throws Exception;

    public void verifyActualDataForPastWeek() throws Exception;

	public void verifyFilterFunctionInForecast() throws Exception;

	public boolean verifyIsWeekForecastVisibleAndOpenByDefault() throws Exception;

	public boolean verifyIsShopperTypeSelectedByDefaultAndLaborTabIsClickable() throws Exception;
	public void verifyAndClickEditBtn();
	public void verifyAndClickCancelBtn();
	public void verifyAndClickSaveBtn();
	public void verifyDoubleClickAndUpdateForecastBarValue(String index, String value);
	public String getTooltipInfo(String index);
	//public List<String>  getTooltipInfos();
	public String getTickByIndex(int index);
	public void verifyWarningEditingForecast();
	public void verifyLegionPeakShopperFromForecastGraphInWeekView();
	public void clickOnDayView() throws Exception;
	public void verifyEditBtnVisible() throws Exception;
	public void verifyContentInEditMode() throws Exception;
	public void navigateToOtherDay() throws Exception;
	public void verifyTooltipWhenMouseEachBarGraph() throws Exception;
	public void verifyPeakShoppersPeakTimeTotalShoppersLegionDataInDayView () throws Exception;
	public void verifyDraggingBarGraph() throws Exception;
	public void verifyContentOfSpecifyAValueLayout(String index, String value) throws Exception;
	public void verifyDoubleClickBarGraph(String index) throws Exception;
	public String getLegionValueFromBarTooltip(int index) throws Exception;
	public void verifyAndClickCloseBtn() throws Exception;
	public void verifyAndClickOKBtn() throws Exception;
	public String getEditedValueFromBarTooltip(int index) throws Exception;
	public String getPercentageFromBarTooltip(int index) throws Exception;
	public void verifyConfirmMessageWhenSaveForecast(String dayOrWeek, String activeDayOrWeekText) throws Exception;
	public String getActiveDayText() throws Exception;
	public void clickOnCancelBtnOnConfirmPopup() throws Exception;
	public void clickOnSaveBtnOnConfirmPopup() throws Exception;
	public void verifyPeakShoppersPeakTimeTotalShoppersEditedDataInDayView () throws Exception;
	public List<String> getTooltipInfoWhenView() throws Exception;
	public HashMap<String, Float> getInsightDataInShopperDayView() throws Exception;
}
