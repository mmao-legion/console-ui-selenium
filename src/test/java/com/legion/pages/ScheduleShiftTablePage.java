package com.legion.pages;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface ScheduleShiftTablePage {
    public void reduceOvertimeHoursOfActiveWeekShifts() throws Exception;
    public List<WebElement> getAvailableShiftsInDayView();
}
