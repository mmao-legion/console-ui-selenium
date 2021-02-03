package com.legion.pages;

import java.util.List;
import java.util.Map;

public interface ScheduleDMViewPage {
    public float getBudgetedHourOfScheduleInDMViewByLocation(String location) throws Exception;
    public Map<String, Integer> getThreeWeeksScheduleStatusFromScheduleDMViewPage() throws Exception;
    public List<String> getTextFromTheChartInLocationSummarySmartCard();
    public List<String> getLocationNumbersFromLocationSummarySmartCard();
    public List<Float> getTheTotalBudgetedScheduledProjectedHourOfScheduleInDMView();
    public String getCurrentWeekInDMView() throws Exception;
    public String getBudgetSurplusInDMView() throws Exception;
    public void verifySmartCardsAreLoadedForPastOrFutureWeek(boolean isPastWeek) throws Exception;
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
}
