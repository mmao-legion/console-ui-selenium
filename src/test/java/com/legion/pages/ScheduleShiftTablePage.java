package com.legion.pages;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ScheduleShiftTablePage {
    public void reduceOvertimeHoursOfActiveWeekShifts() throws Exception;
    public List<WebElement> getAvailableShiftsInDayView();
    public void verifyNewAddedShiftFallsInDayPart(String nameOfTheShift, String dayPart) throws Exception;
    public ArrayList<WebElement> getAllAvailableShiftsInWeekView();
    public List<WebElement> getUnAssignedShifts();
    public HashMap<String, String> getFourUpComingShifts(boolean isStartTomorrow, String currentTime) throws Exception;
    public void scheduleUpdateAccordingToSelectWeek() throws Exception;
    public void getHoursAndTeamMembersForEachDaysOfWeek();
    public boolean verifyActiveWeekDailyScheduleHoursInWeekView();
    public void verifyScheduledHourNTMCountIsCorrect() throws Exception;
    public boolean verifyActiveWeekTeamMembersCountAvailableShiftCount();
    public ArrayList<String> getScheduleDayViewGridTimeDuration();
    public ArrayList<String> getScheduleDayViewBudgetedTeamMembersCount();
    public ArrayList<String> getScheduleDayViewScheduleTeamMembersCount();
    public void verifyShiftsChangeToOpenAfterTerminating(List<Integer> indexes, String name, String currentTime) throws Exception;
    public float newCalcTotalScheduledHourForDayInWeekView() throws Exception;
    public boolean newVerifyActiveWeekDailyScheduleHoursInWeekView() throws Exception;
    public float getActiveShiftHoursInDayView();
    public ArrayList<String> getAvailableJobTitleListInWeekView();
    public ArrayList<String> getAvailableJobTitleListInDayView();
    public WebElement clickOnProfileOfUnassignedShift() throws Exception;
    public List<String> getTheShiftInfoByIndex(int index) throws Exception;
    public List<String> getTheShiftInfoInDayViewByIndex(int index) throws Exception;
    public String getIIconTextInfo(WebElement shift) throws Exception;
    public int getShiftIndexById(String id) throws Exception;
    public List<WebElement> getAllShiftsOfOneTM(String name) throws Exception;
    public List<WebElement> getAllOOOHShifts() throws Exception;
    public List<String> getComplianceMessageFromInfoIconPopup(WebElement shift) throws Exception;
    public WebElement getShiftById(String id) throws Exception;
    public WebElement clickOnProfileIconOfShiftInDayView(String openOrNot) throws Exception;
    public String getTheShiftInfoByIndexInDayview(int index) throws Exception;
    public void verifySearchResult (String firstNameOfTM, String lastNameOfTM, String workRole, String jobTitle, List<WebElement> searchResults) throws Exception;
}
