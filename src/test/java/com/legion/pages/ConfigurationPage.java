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
    public void selectDaysForDaysOfWeekSection(List<String> days) throws Exception;
    public void inputOffsetTimeForShiftStart(String startOffsetTime,String startEventPoint) throws Exception;
    public void validateShiftStartTimeUnitList() throws Exception;
    public List<String> getShiftStartTimeEventList() throws Exception;
    public void verifyRadioButtonInTimeOfDayIsSingletonSelect() throws Exception;
    public void inputShiftDurationMinutes(String duringTime) throws Exception;
    public void validateShiftDurationTimeUnit() throws Exception;
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
    public void inputNumberOfShiftsField(String shiftsNumber) throws Exception;
    public void validCheckBoxOfNumberOfShiftsIsClickable() throws Exception;
    public void inputFormulaInFormulaTextAreaOfNumberOfShifts(String shiftNumberFormula) throws Exception;
    public void selectBadgesForAdvanceStaffingRules() throws Exception;
    public void verifyCrossButtonOnAdvanceStaffingRulePage() throws Exception;
    public void verifyCheckMarkButtonOnAdvanceStaffingRulePage() throws Exception;
    public void saveOneAdvanceStaffingRule(String workRole,List<String> days) throws Exception;
    public void cancelSaveOneAdvanceStaffingRule(String workRole,List<String> days) throws Exception;
    public void addMultipleAdvanceStaffingRule(String workRole,List<String> days) throws Exception;
    public void editAdvanceStaffingRule(String shiftsNumber) throws Exception;
    public void deleteAdvanceStaffingRule() throws Exception;
    public void verifyClockInDisplayAndSelect(List<String> clockInGroup) throws Exception;
    public void setWFS(String wfsMode);
    public void selectWFSGroup(String wfsName) throws Exception;
    public void publishNowTheTemplate() throws Exception;
    public void selectShiftStartTimeEvent(String startEvent) throws Exception;
    public void selectShiftStartTimeUnit(String startTimeUnit) throws Exception;
    public void selectShiftEndTimeEvent(String endEvent) throws Exception;
    public void createNewTemplate(String templateName) throws Exception;
    public void deleteNewCreatedTemplate(String templateName) throws Exception;
    public void addAllTypeOfTemplate(String templateName) throws Exception;
    public void verifyConvertUnassignedShiftsToOpenSetting() throws Exception;
    public void updateConvertUnassignedShiftsToOpenWhenCreatingScheduleSettingOption(String option) throws Exception;
    public void updateConvertUnassignedShiftsToOpenWhenCopyingScheduleSettingOption(String option) throws Exception;
    public void selectShiftEndTimeUnit(String endTimeUnit) throws Exception;
    public void validateAdvanceStaffingRuleShowing(String startEvent,String startOffsetTime,String startEventPoint,String startTimeUnit,
                                                             String endEvent,String endOffsetTime,String endEventPoint,String endTimeUnit,
                                                             List<String> days,String shiftsNumber) throws Exception;
    public void deleteAllScheduleRules() throws Exception;
    public void clickOnSaveButtonOnScheduleRulesListPage() throws Exception;
    public void searchTemplate(String templateName) throws Exception;
    public void selectOperatingBufferHours(String option) throws Exception;
    public void setOpeningAndClosingBufferHours (int openingBufferHour, int closingBufferHour) throws Exception;
    public void setScheduleCopyRestrictions(String yesOrNo) throws Exception;
    public void disableAllDayparts() throws Exception;
    public void goToUserManagementPage() throws Exception;
    public void selectDaypart(String dayPart) throws Exception;
    public void setDaypart(String day, String dayPart, String startTime, String endTime) throws Exception;
    public HashMap<String, List<String>> getDayPartsDataFromBusinessHours() throws Exception;
    public void publishNowTemplate() throws Exception;
    public void goToWorkRolesWithStaffingRules();
    public void deleteBasicStaffingRule() throws Exception;
    public void saveBtnIsClickable() throws Exception;
    public void validateAdvanceStaffingRuleShowingAtLocationLevel(String startEvent,String startOffsetTime,String startEventPoint,String startTimeUnit,
                                                                  String endEvent,String endOffsetTime,String endEventPoint,String endTimeUnit,
                                                                  List<String> days,String shiftsNumber) throws Exception;
    public void selectOneDynamicGroup(String dynamicGroupName) throws Exception;
    public void publishNewTemplate(String templateName,String name,String criteria,String formula) throws Exception;
    public void moveSliderAtSomePoint(int moveCount, String value) throws Exception;
    public void archivePublishedOrDeleteDraftTemplate(String templateName, String action);
    public void createDynamicGroup(String name,String criteria,String formula) throws Exception;
}