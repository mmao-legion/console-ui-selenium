package com.legion.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;

public interface TimeSheetPage {

	public void clickOnTimeSheetConsoleMenu()  throws Exception;

	public boolean isTimeSheetPageLoaded() throws Exception;

	public void openATimeSheetWithClockInAndOut() throws Exception;

	public void clickOnEditTimesheetClock(int index) throws Exception;

	public void clickOnDeleteClockButton() throws Exception;

	public void closeTimeSheetDetailPopUp() throws Exception;
	
	public ArrayList<String> hoverOnClockIconAndGetInfo() throws Exception;

	public void addNewTimeClock(String location, String date, String employee, String workRole, 
			String startTime, String endTime, String notes) throws Exception;

	public void valiadteTimeClock(String location, String timeClockDate, String employee, String workRole, String startTime,
			String endTime, String notes) throws Exception;

	public void clickOnPayPeriodDuration() throws Exception;

	public HashMap<String, Float> getTimeClockHoursByDate(String timeClockDate, String timeClockEmployee) throws Exception;

	public String getActiveDayWeekOrPayPeriod() throws Exception;
	
	public void navigateDayWeekOrPayPeriodToPastOrFuture(String nextWeekViewOrPreviousWeekView, int weekCount);
	
	public void clickOnDayView() throws Exception;

	public void openFirstPendingTimeSheet() throws Exception;

	public boolean isTimeSheetPopupApproveButtonActive() throws Exception;

	public boolean seachAndSelectWorkerByName(String workerName) throws Exception;

	public List<WebElement> getTimeSheetDisplayedWorkersDayRows();

	public HashMap<String, Float> getTimesheetWorkerHoursByDay(WebElement workersDayRow);

	public String getWorkerTimeSheetAlert(WebElement workersDayRow)  throws Exception;

	public void openWorkerDayTimeSheetByElement(WebElement workersDayRow) throws Exception;

	public boolean isTimesheetPopupModelContainsKeyword(String keyword) throws Exception;

	public boolean isWorkerDayRowStatusPending(WebElement workerDayRow) throws Exception;

	public void clickOnApproveButton() throws Exception;

	public boolean isTimeSheetApproved() throws Exception;

	public String getTimeClockHistoryText() throws Exception;

	public void displayTimeClockHistory() throws Exception;

	public List<WebElement> getAllTimeSheetEditBtnElements() throws Exception;

	public void clickOnEditTimesheetClock(WebElement webElement) throws Exception;

	public boolean isTimeSheetWorkerRowContainsCheckbox(WebElement workerRow);

	public List<WebElement> getTimeSheetWorkersRow()  throws Exception;

	public String getWorkerNameByWorkerRowElement(WebElement workerRow) throws Exception;

	public HashMap<String, Float> getWorkerTotalHours(WebElement workerRow);

	public void vadidateWorkerTimesheetLocationsForAllTimeClocks(WebElement workersDayRow) throws Exception;

	public void addBreakToOpenedTimeClock(String breakStartTime, String breakEndTime) throws Exception;

	public void closeTimeClockHistoryView() throws Exception;

	public void addTimeClockCheckInOnDetailPopupWithDefaultValue() throws Exception;

}
