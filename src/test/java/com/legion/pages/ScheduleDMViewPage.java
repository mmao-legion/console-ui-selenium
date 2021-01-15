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
    public void verifyScheduleStatusOnScheduleDMView(String scheduleStatus) throws Exception;
}
