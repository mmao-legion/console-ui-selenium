package com.legion.pages;

import java.util.HashMap;
import java.util.List;

public interface CompliancePage {
    public String getTheTotalViolationHrsFromSmartCard() throws Exception;
    public String getTheTotalLocationsFromSmartCard() throws Exception;
    public String getTheTotalLocationsWithViolationsFromSmartCard() throws Exception;
    public List<String> getComplianceViolationsOnDMViewSmartCard() throws Exception;
    public boolean isCompliancePageLoaded() throws Exception;
    public HashMap<String, Float> getValuesAndVerifyInfoForTotalViolationSmartCardInDMView() throws Exception;
    public void verifyDiffFlag(String upOrDown) throws Exception;
    public HashMap<String, Integer> getValueOnLocationsWithViolationCardAndVerifyInfo() throws Exception;
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
}
