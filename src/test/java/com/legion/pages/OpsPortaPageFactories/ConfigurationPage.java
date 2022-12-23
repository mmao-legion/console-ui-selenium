package com.legion.pages.OpsPortaPageFactories;

import org.openqa.selenium.WebElement;

import java.nio.charset.Charset;
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
    public void checkTheEntryOfAddShiftPatternRule() throws Exception;
    public boolean verifyWarningInfoForDemandDriver(String warningMsg) throws Exception;
    public void verifyStaffingRulePageShowWell() throws Exception;
    public void addOrEditDemandDriverInTemplate(HashMap<String, String> driverSpecificInfo) throws Exception;
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
    public void verifyDefaultValueAndSelectDaysForBasicStaffingRule(String day) throws Exception;
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
    public void setLeaveThisPageButton() throws Exception;
    public void selectWorkRoleOfBasicStaffingRule(String workRoleName) throws Exception;
    public void selectEndTimeEvent(String endTimeEvent) throws Exception;
    public void selectConditionMaxMinExactly(String condition) throws Exception;
    public void selectUnitOptionsOfBasicStaffingRule(String unit) throws Exception;
    public void inputStartOffsetMinutesOfBasicStaffingRule(String startOffset) throws Exception;
    public void inputEndOffsetMinutesOfBasicStaffingRule(String endOffset) throws Exception;
    public void createBasicStaffingRule(String startTimeEvent,String endTimeEvent,String startEventPoint,String endEventPoint,
                                        String workRoleName,String unit,String condition,List<String> days,String number,
                                        String startOffset,String endOffset) throws Exception;
    public void selectStartEventPointForBasicStaffingRule(String startEventPoint) throws Exception;
    public void selectEndEventPointForBasicStaffingRule(String endEventPoint) throws Exception;
    public void selectDaysForBasicStaffingRule(List<String> days) throws Exception;
    public void verifyBasicStaffingRuleIsCorrectInRuleList(String startTimeEvent,String endTimeEvent,String startEventPoint,String endEventPoint,
                                                           String workRoleName,String unit,String condition,List<String> days,String number,
                                                           String startOffset,String endOffset) throws Exception;
    public void removeAllDemandDriverTemplates() throws Exception;
    public void clickAddOrEditForDriver(String addOrEdit) throws Exception;
    public List<String> getInputStreamInDrivers() throws Exception;
    public void addSkillCoverageBasicStaffingRule() throws Exception;
    public void verifySkillCoverageBasicStaffingRule(String workRole1, String workRole2) throws Exception;
    public void verifySkillCoverageBasicStaffingRuleInList() throws Exception;
    public void verifyMinorRulesTileIsLoaded() throws Exception;
    public boolean checkIfMinorSectionsLoaded () throws Exception;
    public void clickOnBackButton () throws Exception;
    public void verifyTheContentOnSpecificCard(String cardName, List<String> content) throws Exception;
    public void setStrictlyEnforceMinorViolations(String yesOrNo) throws Exception;
    public boolean isStrictlyEnforceMinorViolationSettingEnabled() throws Exception;
    public void updateCanManagerAddAnotherLocationsEmployeeInScheduleBeforeTheEmployeeHomeLocationHasPublishedTheSchedule(String option) throws Exception;
    public void verifySpecificAssociationIsSaved(String name) throws Exception;
    public boolean checkIfApproveShiftInHomeLocationSettingEnabled() throws Exception;
    public void updateLaborPreferencesForForecastSummarySmartcardSettingDropdownOption(String option) throws Exception;
    public void verifyForDerivedDemandDriverUI(String derivedType, String remoteType) throws Exception;
    public void clickOnCancelButton() throws Exception;
    public void clickHistoryButton() throws Exception;
    public void verifyRecordIsClickable() throws Exception;
    public boolean verifyWarningForDemandDriver(String warningMsg) throws Exception;
    public void verifyTemplateHistoryUI() throws Exception;
    public void verifyTemplateHistoryContent() throws Exception;
    public void verifyOrderOfTheTemplateHistory() throws Exception;
    public void verifyNewTemplateIsClickable() throws Exception;
    public boolean verifyDriverInViewMode(HashMap<String, String> driverToCheck) throws Exception;
    public void verifyNewCreatedTemplateHistoryContent(String option,String userName,String time) throws Exception;
    public void closeTemplateHistoryPanel();
    public void updateSchedulePolicyTemplateFirstField(String count);
    public void verifyPublishTemplateHistoryContent(String option,String userName) throws Exception;
    public void verifyUpdatedSchedulePolicyTemplateFirstFieldCorrectOrNot(String count);
    public void publishAtDifferentTimeForTemplate(String button,int date) throws Exception;
    public void openCurrentPublishInMultipleTemplate(String templateName) throws Exception;
    public void verifyPublishFutureTemplateHistoryContent(String option,String userName) throws Exception;
    public void clickOnArchiveButton() throws Exception;
    public void verifyArchiveTemplateHistoryContent(String option,String userName) throws Exception;
    public void verifyDeleteTemplateHistoryContent(String option,String userName) throws Exception;
    public void clickOnDeleteButtonOnTemplateDetailsPage() throws Exception;
    public void verifyAllTemplateTypeHasAuditLog() throws Exception;
    public void verifyLocationLevelTemplateNoHistoryButton() throws Exception;
    public void verifyTheLayoutOfTemplateDetailsPage() throws Exception;
    public void verifyTheLayoutOfTemplateAssociationPage() throws Exception;
    public void verifyCriteriaTypeOfDynamicGroup() throws Exception;
    public void goToBusinessHoursEditPage(String workkday) throws Exception;
    public void checkOpenAndCloseTime() throws Exception;
    public void clickOpenCloseTimeLink() throws Exception;
    public void setOpenCloseTime(String settingTab, String openTime, String closeTime, String crossNextDay) throws Exception;

    public boolean verifyStartEndTimeForDays(String startTime, String endTime, String day) throws Exception;
    public void selectDaysForOpenCloseTime(List<String> dayOfWeek) throws Exception;
    public void editBasicStaffingRules() throws Exception;
    public boolean verifyWarningIconsDisplay(String templateName2, String expectedWarningMsg) throws Exception;
    public int getUnassignedNumber() throws Exception;
    public boolean verifyUnassignedSmartCardDownloadFile(String fileName, Map<String, String> criteriaAndValue) throws Exception;
    public void verifyDefaultValueOfOverrideViaIntegrationButton();
    public void updateMaximumNumberOfShiftsPerDay(int maximumNumber) throws Exception;
    public int getMaximumNumberOfShiftsPerDay() throws Exception;
    public void updateMinimumTimeBetweenShifts(int minimumTime) throws Exception;
    public int getMinimumTimeBetweenShifts() throws Exception;
    public boolean verifyTemplateCardExist(String templateType) throws Exception;
    public List<String> getAllForecastSourceType() throws Exception;
    public boolean verifyPredictabilityScoreExist() throws Exception;
    public boolean verifyOverrideViaIntegrationButtonShowingOrNot();
    public void verifyEachFieldsWithInvalidTexts();
    public void inputTemplateName(String templateName) throws Exception;
    public boolean isGetPredictabilityScoreEnabled() throws Exception;
    public void clickGetPredictabilityScore() throws Exception;
    public void turnOnOffOverrideViaIntegrationButton();
    public void verifyAddButtonOfDynamicLocationGroupOfAdvancedStaffingRuleIsClickable() throws Exception;
    public void clickOnAddButtonOfDynamicLocationGroupOfAdvancedStaffingRule() throws Exception;
    public void advanceStaffingRuleDynamicGroupDialogUICheck(String name) throws Exception;
    public void advanceStaffingRuleEditDeleteADynamicGroup(String dyname) throws Exception;
    public void createAdvanceStaffingRuleDynamicGroup(String name) throws Exception;
    public void advanceStaffingRuleDynamicGroupCriteriaListChecking(String name) throws Exception;
    public List<String> getStaffingRules() throws Exception;
    public void advanceStaffingRuleDynamicGroupCustomFormulaDescriptionChecking() throws Exception;
    public void verifyDynamicGroupOfAdvanceStaffingRuleIsOptional(String workRole, List<String> days) throws Exception;
    public String getGranularityForCertainDriver() throws Exception;
    public void goToWorkRoleSettingsTile();
    public void verifyWorkRoleSettingsTemplateListUIAndDetailsUI(String templateName,String mode) throws Exception;
    public void checkWorkRoleListShowingWell(int workRoleCount);
    public HashMap<String,String> getDefaultHourlyRate(List<String> workRoles);
    public void updateWorkRoleHourlyRate(String workRole,String updateValue);
    public void createFutureWRSTemplateBasedOnExistingTemplate(String templateName, String button, int date, String editOrViewMode) throws Exception;
}

