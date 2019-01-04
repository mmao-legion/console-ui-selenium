package com.legion.pages;

import java.util.HashMap;

public interface AnalyticsPage {
	public void gotoAnalyticsPage() throws Exception;

	public HashMap<String, Float> getForecastedHours() throws Exception;

	public HashMap<String, Float> getScheduleHours() throws Exception;

	public void navigateToNextWeek() throws Exception;

	public String getAnalyticsActiveDuration();
}
