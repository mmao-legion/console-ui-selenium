package com.legion.pages;

import java.util.List;

public interface SmartTemplatePage {
    public void clickOnEditSmartTemplateBtn() throws Exception;
    public void clickOnEditBtn();
    public void clickOnResetBtn();
    public void checkOrUnCheckRecurringShift(boolean isCheck);
    public List<String> createShiftsWithSpecificValues(String workRole, String shiftName, String location, String startTime,
                                                          String endTime, int shiftPerDay, List<Integer> workDays, String assignment,
                                                          String shiftNotes, String tmName, boolean recurringShift) throws Exception;
    public void clickOnBackBtn();
}
