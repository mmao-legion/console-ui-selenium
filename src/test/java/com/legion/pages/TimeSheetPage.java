package com.legion.pages;

import java.util.ArrayList;

public interface TimeSheetPage {

	public void clickOnTimeSheetConsoleMenu()  throws Exception;

	public boolean isTimeSheetPageLoaded() throws Exception;

	public void OpenATimeSheetWithClockInAndOut() throws Exception;

	public void clickOnEditTimesheetClock(int index) throws Exception;

	public void clickOnDeleteClockButton() throws Exception;

	public void closeTimeSheetDetailPopUp() throws Exception;
	
	public ArrayList<String> hoverOnClockIconAndGetInfo() throws Exception;

	public void addNewTimeClock(String location, String date, String employee, String workRole, 
			String startTime, String endTime, String notes) throws Exception;

	public void valiadteTimeClock(String location, String timeClockDate, String employee, String workRole, String startTime,
			String endTime, String notes) throws Exception;

	public void clickOnPayPeriodDuration() throws Exception;

}
