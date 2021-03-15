package com.legion.pages;

import cucumber.api.java.an.E;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ConfigurationPage {
    public void goToConfigurationPage() throws Exception;
    public void checkAllTemplateCards() throws Exception;
    public boolean isTemplateListPageShow() throws Exception;
    public void clickOnConfigurationCrad(String templateType) throws Exception;
    public void clickOnTemplateName(String templateType) throws Exception;
    public void goToTemplateDetailsPage(String templateType) throws Exception;
    public void clickOnSpecifyTemplateName(String templateName,String editOrViewMode) throws Exception;
    public void clickOnEditButtonOnTemplateDetailsPage() throws Exception;
    public void selectWorkRoleToEdit(String workRole) throws Exception;
    public void checkTheEntryOfAddAdvancedStaffingRule() throws Exception;
    public void verifyAdvancedStaffingRulePageShowWell() throws Exception;
    public void verifyCheckBoxOfDaysOfWeekSection() throws Exception;
    public boolean isDaysOfWeekFormulaCheckBoxChecked();
    public void inputFormulaInForDaysOfWeekSection(String formula) throws Exception;
    public void inputOffsetTimeForShiftStart(String startOffsetTime,String startEventPoint) throws Exception;
    public void validateShiftStartTimeUnitList() throws Exception;
    public List<String> getShiftStartTimeEventList() throws Exception;
    public void verifyRadioButtonInTimeOfDayIsSingletonSelect() throws Exception;
    public void inputShiftDuartionMinutes(String duringTime) throws Exception;
    public void validateShiftDuartionTimeUnit() throws Exception;
    public void inputOffsetTimeForShiftEnd(String endOffsetTime,String endEventPoint) throws Exception;
    public void validateShiftEndTimeUnitList() throws Exception;
    public void tickOnCheckBoxOfTimeOfDay() throws Exception;
    public void inputFormulaInTextAreaOfTimeOfDay(String formulaOfTimeOfDay);
    public void addNewMealBreak(List<String> mealBreakValue) throws Exception;
    public void addMultipleMealBreaks(List<String> mealBreakValue) throws Exception;
    public void deleteMealBreak() throws Exception;
    public void addNewRestBreak(List<String> restBreakValue) throws Exception;
    public void addMultipleRestBreaks(List<String> restBreakValue) throws Exception;
    public void deleteRestBreak() throws Exception;
}