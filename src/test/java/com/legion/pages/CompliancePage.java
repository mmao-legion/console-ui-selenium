package com.legion.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CompliancePage {
    public String getTheTotalViolationHrsFromSmartCard() throws Exception;
    public String getTheTotalLocationsFromSmartCard() throws Exception;
    public String getTheTotalLocationsWithViolationsFromSmartCard() throws Exception;
    public List<String> getComplianceViolationsOnSmartCard() throws Exception;
    public boolean isCompliancePageLoaded() throws Exception;
    public HashMap<String, Float> getValuesAndVerifyInfoForTotalViolationSmartCardInDMView() throws Exception;
    public void verifyDiffFlag(String upOrDown) throws Exception;
    public HashMap<String, Integer> getValueOnLocationsWithViolationCardAndVerifyInfo(String upperFieldType) throws Exception;
    public HashMap<String, Float> getViolationHrsFromTop1ViolationCardAndVerifyInfo() throws Exception;
    public float getTopOneViolationHrsOrNumOfACol(List<Float> list) throws Exception;
    public void validateThePresenceOfRefreshButton() throws Exception;
    public void validateRefreshTimestamp() throws Exception;
    public void validateRefreshWhenNavigationBack() throws Exception;
    public void validateRefreshFunction() throws Exception;
    public void validateRefreshPerformance() throws Exception;
    public void navigateToSchedule() throws Exception;
    public void clickOnRefreshButton() throws Exception;
    public void clickOnComplianceConsoleMenu() throws Exception;
    public void navigateToPreviousWeek() throws Exception;
    public void navigateToNextWeek() throws Exception;
    public List<String> getDataFromComplianceTableForGivenLocationInDMView(String location) throws Exception;
    public boolean isLocationInCompliancePageClickable() throws Exception;
    public void verifyFieldNamesInAnalyticsTable(String upperFieldType) throws Exception;
    public void verifySortByColForLocationsInDMView(int index) throws Exception;
    public List<Float> transferStringToFloat(List<String> listString) throws Exception;
    public List<String> getListByColInTimesheetDMView(int index) throws Exception;
    public List<String> getLocationsInScheduleDMViewLocationsTable() throws Exception;
    public boolean isComplianceUpperFieldView() throws Exception;
    public List<String> getAllUpperFieldNamesOnAnalyticsTable() throws Exception;
    public boolean isRefreshButtonDisplay() throws Exception;
    public Map<String, String> getAllUpperFieldInfoFromComplianceDMViewByUpperField(String upperFieldName) throws Exception;
    public List<String> getAllUpperFieldNames();
    public boolean isUpperFieldsWithViolationSmartCardDisplay () throws Exception;
    public boolean isTop1ViolationSmartCardDisplay () throws Exception;
    public void turnOnOrTurnOff7thConsecutiveOTToggle(boolean action) throws Exception;
    public String getConsecutiveOTSettingContent() throws Exception;
    public void editConsecutiveOTSetting(String daysCount, String condition, boolean saveOrNot) throws Exception;
    public void turnOnOrTurnOffConsecutiveDTToggle(boolean action) throws Exception;
    public String getConsecutiveDTSettingContent() throws Exception;
    public void turnOnOrTurnOffWeelyDTToggle(boolean action) throws Exception;
    public String getWeeklyDTSettingContent() throws Exception;
    public void turnOnOrTurnOffDailyDTToggle(boolean action) throws Exception;
    public String getDailyDTSettingContent() throws Exception;
    public String getDayOTSettingContent() throws Exception;
    public String getWeeklyOTSettingContent() throws Exception;
    public void turnOnOrTurnOffWeeklyOTToggle(boolean action) throws Exception;
    public void editWeeklyOTSetting(String optionVisibleText) throws Exception;
    public void turnOnOrTurnOffDayOTToggle(boolean action) throws Exception;
    public void editDayOTSetting(String dailyHours, String workDayType, boolean saveOrNot) throws Exception;
    public void displaySuccessMessage() throws Exception;
}
