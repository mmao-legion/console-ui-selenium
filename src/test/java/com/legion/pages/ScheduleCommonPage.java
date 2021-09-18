package com.legion.pages;

public interface ScheduleCommonPage {
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
}
