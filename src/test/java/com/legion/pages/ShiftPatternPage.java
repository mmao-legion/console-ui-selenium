package com.legion.pages;

public interface ShiftPatternPage {

    public void verifyTheContentOnShiftPatternDetails(String workRole) throws Exception;
    public void verifyTheMandatoryFields() throws Exception;
    public void inputNameDescriptionNInstances(String name, String description, String instances) throws Exception;
    public String selectTheCurrentWeek() throws Exception;
    public void selectAddOnOrOffWeek(boolean isOn) throws Exception;
    public void verifyTheContentOnOffWeekSection() throws Exception;
    public void checkOrUnCheckAutoSchedule(boolean isCheck) throws Exception;
    public void checkOrUnCheckSpecificDay(boolean isCheck, String day) throws Exception;
    public void verifyTheContentOnOnWeekSection() throws Exception;
    public void verifyTheFunctionalityOfExpandWeekIcon(int weekNumber, boolean isExpanded) throws Exception;
    public void verifyTheFunctionalityOfArrowIcon(int weekNumber, boolean isExpanded) throws Exception;
    public int getWeekCount() throws Exception;
    public void deleteTheWeek(int weekNumber) throws Exception;
    public void clickOnAddShiftButton() throws Exception;
}
