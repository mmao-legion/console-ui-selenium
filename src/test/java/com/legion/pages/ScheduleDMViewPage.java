package com.legion.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ScheduleDMViewPage {
    public float getBudgetedHourOfScheduleInDMViewByLocation(String location) throws Exception;
    public Map<String, Integer> getThreeWeeksScheduleStatusFromScheduleDMViewPage() throws Exception;
    public List<String> getTextFromTheChartInLocationSummarySmartCard();
    public List<String> getLocationNumbersFromLocationSummarySmartCard();
    public List<Float> getTheTotalBudgetedScheduledProjectedHourOfScheduleInDMView() throws Exception;
    public String getCurrentWeekInDMView() throws Exception;
    public String getBudgetComparisonInDMView() throws Exception;
    public void verifySmartCardsAreLoadedForPastOrFutureWeek(boolean isApplyBudget, boolean isPastWeek) throws Exception;
    public void verifySchedulesTableHeaderNames(boolean isApplyBudget, boolean isPastWeek) throws Exception;
    public void validateThePresenceOfRefreshButton() throws Exception;
    public void validateRefreshTimestamp() throws Exception;
    public void validateRefreshWhenNavigationBack() throws Exception;
    public void validateRefreshFunction() throws Exception;
    public void validateRefreshPerformance() throws Exception;
    public void navigateToSchedule() throws Exception;
    public void clickOnRefreshButton() throws Exception;
    public boolean isNotStartedScheduleDisplay() throws Exception;
    public List<String> getLocationsWithNotStartedSchedules() throws Exception;
    public String getScheduleStatusForGivenLocation(String location) throws Exception;
    public void verifyScheduleStatusAndHoursInScheduleList(String locationName, Boolean isTAEnv, Boolean isDGEnv, String scheduleStatus, String specificWeek) throws Exception;
    public void verifyTheContentOnScheduleStatusCards() throws Exception;
    public void verifyTheScheduleStatusAccountOnScheduleStatusCards() throws Exception;
    public Map<String, String> getAllScheduleInfoFromScheduleInDMViewByLocation(String location) throws Exception;
    public Map<String, String> getAllUpperFieldInfoFromScheduleByUpperField(String upperFieldName) throws Exception;
    public List<String> getLocationsWithInProgressSchedules() throws Exception;
    public List<String> getLocationSummaryDataFromSchedulePage() throws Exception;
    public HashMap<String, Float> getValuesAndVerifyInfoForLocationSummaryInDMView(String upperFieldType, String weekType) throws Exception;
    public boolean isScheduleDMView() throws Exception;
    public List<String> getLocationsInScheduleDMViewLocationsTable() throws Exception;
    public void verifySortByColForLocationsInDMView(int index) throws Exception;
    public void verifySearchLocationInScheduleDMView(String location) throws Exception;
    public void clickOnLocationNameInDMView(String location) throws Exception;
    public void verifyClockedOrProjectedInDMViewTable(String expected) throws Exception;
    public List<String> getListByColInTimesheetDMView(int index) throws Exception;
    public List<Float> transferStringToFloat(List<String> listString) throws Exception;
    public int getIndexOfColInDMViewTable(String colName) throws Exception;
    public HashMap<String, Integer> getValueOnUnplannedClocksSummaryCardAndVerifyInfo() throws Exception;
    public HashMap<String, Integer> getValueOnUnplannedClocksSmartCardAndVerifyInfo() throws Exception;
    public HashMap<String, String> getBudgetNScheduledHoursFromSmartCardOnDGEnv() throws Exception;
    public void clickSpecificLocationInDMViewAnalyticTable(String location) throws Exception;
    public boolean hasNextWeek() throws Exception;
}
