package com.legion.pages;

public interface ScheduleCommonPage {
    public void clickOnScheduleConsoleMenuItem();
    public void goToSchedulePage() throws Exception;
    public boolean isSchedulePage() throws Exception;
    public void clickOnWeekView() throws Exception;
    public void clickOnDayView() throws Exception;
    public void navigateDayViewWithIndex(int dayIndex);
    public void navigateDayViewWithDayName(String dayName) throws Exception;
    public void navigateWeekViewOrDayViewToPastOrFuture(String nextWeekViewOrPreviousWeekView, int weekCount);
    public void navigateToNextDayIfStoreClosedForActiveDay() throws Exception;
    public boolean isStoreClosedForActiveWeek() throws Exception;
//    public void verifySelectOtherWeeks() throws Exception;
    public void clickOnNextDaySchedule(String activeDay) throws Exception;
    public void clickOnPreviousDaySchedule(String activeDay) throws Exception;
    public void validateForwardAndBackwardButtonClickable() throws Exception;
//    public void validateTheDataAccordingToTheSelectedWeek() throws Exception;
    public void clickOnScheduleSubTab(String subTabString) throws Exception;
    public Boolean verifyActivatedSubTab(String SubTabText) throws Exception;
    public Boolean isScheduleDayViewActive();
    public String getScheduleWeekStartDayMonthDate();
    public void navigateDayViewToPast(String PreviousWeekView, int dayCount);
    public void clickImmediateNextToCurrentActiveWeekInDayPicker();
    public void clickImmediatePastToCurrentActiveWeekInDayPicker();
    public String getActiveAndNextDay() throws Exception;
    public void isScheduleForCurrentDayInDayView(String dateFromDashboard) throws Exception;
    public void currentWeekIsGettingOpenByDefault(String location) throws Exception;
    public void goToScheduleNewUI() throws Exception;
    public void dayWeekPickerSectionNavigatingCorrectly() throws Exception;
    public int getMinutesFromTime(String time);
    public int getTheIndexOfCurrentDayInDayView() throws Exception;
    public String getActiveWeekText() throws Exception;
}
