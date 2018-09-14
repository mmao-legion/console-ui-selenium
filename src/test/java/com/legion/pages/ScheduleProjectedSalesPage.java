package com.legion.pages;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebElement;

public interface ScheduleProjectedSalesPage {
	//public void projectedSalesForecastData(HashMap<String, String> propertyMap, Boolean isStoreManager) throws Exception;
	public void navigateToProjectedSalesForecastTab() throws Exception;
	public Boolean isScheduleProjectedSalesTabActive() throws Exception;
	public void navigateToProjectedSalesForecastTabWeekView() throws Exception;
	public Boolean isProjectedSalesForecastTabWeekViewActive() throws Exception;
	public Boolean validateProjectedSalesItemOptionWithUserJobTitle(String userJobTitle) throws Exception;
	public Boolean validateWeekViewWithDateFormat(String legionDateFormat);
	public Map<String, String> getScheduleProjectedSalesForeCastData() throws Exception;
	public void navigateProjectedSalesWeekViewTpPastOrFuture(String nextWeekViewOrPreviousWeekView, int weekCount) throws Exception;
}
