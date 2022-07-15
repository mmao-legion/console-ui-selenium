package com.legion.pages.OpsPortaPageFactories;

import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ConfigurationPage {
    public void goToConfigurationPage() throws Exception;
    public int historyRecordLimitCheck(String templateName) throws Exception;
    public void changeOHtemp() throws Exception;
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
    public void OHListPageCheck() throws Exception;
    public void createOHTemplateUICheck(String tpname) throws Exception;
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
    public boolean searchTemplate(String templateName) throws Exception;
    public void selectOperatingBufferHours(String option) throws Exception;
    public void setOpeningAndClosingBufferHours (int openingBufferHour, int closingBufferHour) throws Exception;
    public void setScheduleCopyRestrictions(String yesOrNo) throws Exception;
    public void disableAllDayparts() throws Exception;
    public void goToUserManagementPage() throws Exception;
    public void selectDaypart(String dayPart) throws Exception;
    public void setDaypart(String day, String dayPart, String startTime, String endTime) throws Exception;
    public HashMap<String, List<String>> getDayPartsDataFromBusinessHours() throws Exception;
    public void enableOrDisableApproveShiftInHomeLocationSetting(String yesOrNo) throws Exception;
    public void enableOrDisableApproveShiftInNonHomeLocationSetting(String yesOrNo) throws Exception;
    public void publishNowTemplate() throws Exception;
    public void goToWorkRolesWithStaffingRules();
    public void deleteBasicStaffingRule() throws Exception;
    public void saveBtnIsClickable() throws Exception;
    public void validateAdvanceStaffingRuleShowingAtLocationLevel(String startEvent,String startOffsetTime,String startEventPoint,String startTimeUnit,
                                                                  String endEvent,String endOffsetTime,String endEventPoint,String endTimeUnit,
                                                                  List<String> days,String shiftsNumber) throws Exception;
    public void selectOneDynamicGroup(String dynamicGroupName) throws Exception;
    public void publishNewTemplate(String templateName,String dynamicGName,String criteria,String formula) throws Exception;
    public void moveSliderAtSomePoint(int moveCount, String value) throws Exception;
    public void archivePublishedOrDeleteDraftTemplate(String templateName, String action) throws Exception;
    public void createDynamicGroup(String name,String criteria,String formula) throws Exception;
    public void dynamicGroupDialogUICheck(String name) throws Exception;
    public void deleteOneDynamicGroup(String name) throws Exception;
    public void createTmpAndPublishAndArchive(String type, String tpname,String gpname) throws Exception;
    public void archiveIsClickable() throws Exception;
    public void saveADraftTemplate() throws Exception;
    public void editADynamicGroup(String name) throws Exception;
    public void holidaysDataCheckAndSelect(String name) throws Exception;
    public void commitTypeCheck() throws Exception;
    public void verifyArchivePopUpShowWellOrNot() throws Exception;
    public void cancelArchiveDeleteWorkWell(String templateName) throws Exception;
    public void archiveOrDeleteTemplate(String templateName) throws Exception;
    public void setMoveExistingShiftWhenTransfer(String yesOrNo) throws Exception;
    public boolean isMoveExistingShiftWhenTransferSettingEnabled() throws Exception;
    public void deleteTemplate(String templateName) throws Exception;
    public void clearSearchTemplateBox() throws Exception;
    public boolean isWFSEnabled();
    public boolean hasCompanyMobilePolicyURLOrNotOnOP () throws Exception;
    public void goToDynamicEmployeeGroupPage();
    public void deleteAllDynamicEmployeeGroupsInList() throws Exception;
    public void createNewDynamicEmployeeGroup(String groupTitle, String description, String groupLabels, List<String> groupCriteria) throws Exception;
    public void archiveOrDeleteAllTemplates() throws Exception;
    public void clickOnTemplateDetailTab() throws Exception;
    public void clickOnAssociationTabOnTemplateDetailsPage() throws Exception;
    public void deleteSpecifyDynamicEmployeeGroupsInList(String groupName) throws Exception;
    public void clickOnBackBtnOnTheTemplateDetailAndListPage() throws Exception;
    public void chooseSaveOrPublishBtnAndClickOnTheBtn(String button) throws Exception;
    public void clickEdit() throws Exception;
    public void clickOK() throws Exception;
    public void verifyTimeOff() throws Exception;
    public void verifymaxNumEmployeesInput(String num) throws Exception;
    public void switchToControlWindow() throws Exception;
    public void verifyMultipleTemplateListUI(String templateName) throws Exception;
    public void publishAtDifferentTimeTemplate(String templateName,String dynamicGName,String criteria,String formula,String button,int effectiveDate) throws Exception;
    public void createFutureTemplateBasedOnExistingTemplate(String templateName,String button,int date,String editOrViewMode) throws Exception;
    public void createDraftForEachPublishInMultipleTemplate(String templateName,String button,String editOrViewMode) throws Exception;
    public HashMap<String, List<String>> verifyMenuListForMultipleTemplate(String templateName) throws Exception;
    public void expandMultipleVersionTemplate(String templateName) throws Exception;
    public void verifyButtonsShowingOnPublishedTemplateDetailsPage() throws Exception;
    public void verifyButtonsShowingOnDraftTemplateDetailsPage() throws Exception;
    public void createMultipleTemplateForAllTypeOfTemplate(String templateName,String dynamicGpName,String criteriaType,String criteriaValue,String button,int date,String editOrViewMode) throws Exception;
    public void archiveMultipleTemplate(String templateName) throws Exception;
    public void verifyAdvanceStaffRuleFromLocationLevel(List<String> advanceStaffingRule) throws Exception;
    public void verifyAdvanceStaffRuleStatusFromLocationLevel(List<String> advanceStaffingRuleStatus) throws Exception;
    public void changeAdvanceStaffRuleStatusFromLocationLevel(int i) throws Exception;
    public void verifyCanNotAddAdvancedStaffingRuleFromTemplateLevel() throws Exception;
    public void verifyCanNotEditDeleteAdvancedStaffingRuleFromTemplateLevel() throws Exception;
    public String updateEffectiveDateOfFutureTemplate(String templateName,String button,int date) throws Exception;
    public List<String> getEffectiveDateForTemplate(String templateName) throws Exception;
    public void checkTheEntryOfAddBasicStaffingRule() throws Exception;
    public boolean verifyWarningInfoForDemandDriver(String warningMsg) throws Exception;
    public void verifyStaffingRulePageShowWell() throws Exception;
    public void addOrEditDemandDriverInTemplate(HashMap<String, String> driverSpecificInfo, String addOrEdit) throws Exception;
    public void verifyPublishedTemplateAfterEdit(String templateName) throws Exception;

    public boolean searchDriverInTemplateDetailsPage(String driverName) throws Exception;
    public void clickRemove() throws Exception;
    public void selectStartTimeEvent(String startTimeEvent) throws Exception;
    public void verifyConditionAndNumberFiledCanShowWell() throws Exception;
    public void verifyNumberInputFieldOfBasicStaffingRule() throws Exception;
    public List<String> verifyWorkRoleListOfBasicStaffingRule() throws Exception;
    public void verifyUnitOptionsListOfBasicStaffingRule() throws Exception;
    public void verifyStartEndOffsetMinutesShowingByDefault() throws Exception;
    public void verifyStartEndEventPointOptionsList() throws Exception;
    public List<String> verifyStartEndTimeEventOptionsList() throws Exception;
    public void selectDaysForBasicStaffingRule(String day) throws Exception;
    public void verifyDaysListShowWell() throws Exception;
    public void setSpecifiedHours(String start, String end) throws Exception;
    public void selectEventPointForBasicStaffingRule(String startEventPoint,String endEventPoint) throws Exception;
    public void verifyBeforeAndAfterDayPartsShouldBeSameWhenSetAsDayParts(String dayParts1,String dayParts2,String startEventPoint,String endEventPoint) throws Exception;
    public void verifyWorkforceSharingGroup() throws Exception;
    public void verifyCrossAndCheckButtonOfBasicStaffingRule() throws Exception;
    public void clickCheckButtonOfBasicStaffingRule() throws Exception;
    public void defaultSelectedBadgeOption() throws Exception;
    public void selectBadgesOfBasicStaffingRule(String hasBadgeOrNot, String badgeName) throws Exception;
    public void verifyHistoryButtonNotDisplay() throws Exception;
    public void verifyHistoryButtonDisplay() throws Exception;
    public void verifyHistoryButtonIsClickable() throws Exception;
    public void verifyCloseIconNotDisplayDefault() throws Exception;
    public void clickHistoryAndClose() throws Exception;
    public void goToItemInConfiguration(String item) throws Exception;
}