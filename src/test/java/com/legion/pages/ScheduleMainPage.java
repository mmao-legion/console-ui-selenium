package com.legion.pages;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ScheduleMainPage {
    public void clickOnScheduleAnalyzeButton() throws Exception;
    public void clickOnCancelButtonOnEditMode() throws Exception;
    public void clickOnEditButton() throws Exception;
    public void clickSaveBtn() throws Exception;
    public boolean isScheduleTypeLoaded();
    public void switchToManagerView() throws Exception;
    public void clickOnEditButtonNoMaterScheduleFinalizedOrNot() throws Exception;
    public boolean checkEditButton() throws Exception;
    public boolean checkSaveButton() throws Exception;
    public boolean checkCancelButton() throws Exception;
    public void verifyEditButtonFuntionality() throws Exception;
    public boolean isScheduleFinalized() throws Exception;
    public void selectCancelButton() throws Exception;
    public void selectSaveButton() throws Exception;
    public Boolean isAddNewDayViewShiftButtonLoaded() throws Exception;
    public void selectGroupByFilter(String optionVisibleText);
    public void selectShiftTypeFilterByText(String filterText) throws Exception;
    public void saveSchedule() throws Exception;
    public void printButtonIsClickable() throws Exception;
    public void legionButtonIsClickableAndHasNoEditButton() throws Exception;
    public void clickOnSuggestedButton() throws Exception;
    public void legionIsDisplayingTheSchedul() throws Exception;
    public String getActiveGroupByFilter() throws Exception;
    public void selectWorkRoleFilterByIndex(int index, boolean isClearWorkRoleFilters) throws Exception;
    public void selectWorkRoleFilterByText(String workRoleLabel, boolean isClearWorkRoleFilters) throws Exception;
    public void checkAndUnCheckTheFilters() throws Exception;
    public void filterScheduleByShiftTypeAsTeamMember(boolean isWeekView) throws Exception;
    public String selectOneFilter() throws Exception;
    public void filterScheduleByBothAndNone() throws Exception;
    public void filterScheduleByWorkRoleAndJobTitle(boolean isWeekView) throws Exception;
    public void filterScheduleByShiftTypeAndJobTitle(boolean isWeekView) throws Exception;
    public void selectLocationFilterByText(String filterText) throws Exception;
    public void selectAllChildLocationsToFilter() throws Exception;
    public void verifyAllChildLocationsShiftsLoadPerformance() throws Exception;
    public void verifyShiftsDisplayThroughLocationFilter(String childLocation) throws Exception;
    public void verifyChildLocationShiftsLoadPerformance(String childLocation) throws Exception;
    public void selectChildLocationFilterByText(String location) throws Exception;
    public void filterScheduleByJobTitle(boolean isWeekView) throws Exception;
    public void filterScheduleByWorkRoleAndShiftType(boolean isWeekView) throws Exception;
    public ArrayList<HashMap<String, String>> getHoursAndShiftsCountForEachWorkRolesInWeekView() throws Exception;
}
