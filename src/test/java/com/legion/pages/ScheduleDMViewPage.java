package com.legion.pages;

import java.util.List;
import java.util.Map;

public interface ScheduleDMViewPage {
    public float getBudgetedHourOfScheduleInDMViewByLocation(String location) throws Exception;
    public Map<String, Integer> getThreeWeeksScheduleStatusFromScheduleDMViewPage() throws Exception;
    public List<String> getTextFromTheChartInLocationSummarySmartCard();
}
