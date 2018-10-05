package com.legion.pages;

import java.util.List;
import java.util.Map;

public interface ScheduleOverviewPage {

	public List<String> getScheduleWeeksStatus();
	public Map<String, String> getWeekStartDayAndCurrentWeekDates() throws Exception;
}
