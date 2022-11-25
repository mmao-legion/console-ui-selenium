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
    public void publishOrRepublishSchedule() throws Exception;
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
    public boolean isPrintIconLoaded() throws Exception;
    public void verifyThePrintFunction() throws Exception;
    public void validateGroupBySelectorSchedulePage(boolean isLocationGroup) throws Exception;
    public HashMap<String, ArrayList<WebElement>> getAvailableFilters();
    void verifySelectedFilterPersistsWhenSelectingOtherWeeks(String selectedFilter) throws Exception;
    public void goToToggleSummaryView() throws Exception;
    public void clickToggleSummaryViewButton() throws Exception;
    public void validateScheduleTableWhenSelectAnyOfGroupByOptions(boolean isLocationGroup) throws Exception;
    public void verifyVersionInSaveMessage(String version) throws Exception;
    public void clickOnManagerButton() throws Exception;
    public void clickOnOpenSearchBoxButton() throws Exception;
    public void verifyGhostTextInSearchBox () throws Exception;
    public List<WebElement> searchShiftOnSchedulePage(String searchText) throws Exception;
    public void clickOnCloseSearchBoxButton() throws Exception;
    public void verifyFilterDropdownList(boolean isLG) throws Exception;
    public void clickOnClearFilterOnFilterDropdownPopup() throws Exception;
    public String getTooltipOfPublishButton() throws Exception;
    public boolean isDeleteScheduleButtonLoaded() throws Exception;
    public void verifyClickOnDeleteScheduleButton() throws Exception;
    public void verifyTheContentOnDeleteScheduleDialog(String confirmMessage, String week) throws Exception;
    public String getDeleteScheduleForWhichWeekText() throws Exception;
    public void verifyDeleteBtnDisabledOnDeleteScheduleDialog() throws Exception;
    public void verifyDeleteButtonEnabledWhenClickingCheckbox() throws Exception;
    public void verifyClickOnCancelBtnOnDeleteScheduleDialog() throws Exception;
    public void verifyLocationFilterInLeft(boolean isLG) throws Exception;
    public String selectRandomChildLocationToFilter() throws Exception;
    public void verifyShiftTypeInLeft(boolean isLG) throws Exception;
    public void verifyShiftTypeFilters() throws Exception;
    public int getSpecificFiltersCount (String filterText) throws Exception;
    public boolean isGroupByDayPartsLoaded() throws Exception;
    public void closeShiftInfoPopup() throws Exception;
    public void clickOnFilterBtn() throws Exception;
    public ArrayList<HashMap<String, String>> getWorkRoleInfoFromFilter() throws Exception;
    public ArrayList<HashMap<String,String>> getToggleSummaryStaffWorkRoleStyleInfo() throws Exception;
    public void selectJobTitleFilterByText(String filterText) throws Exception;
    public boolean verifyDisplayOrderWhenGroupingByWorkRole(HashMap<String, Integer> workRoleNOrders) throws Exception;
    public boolean areDisplayOrderCorrectOnFilterPopup(HashMap<String, Integer> workRoleNOrders) throws Exception;
    public List<String> getStaffWorkRoles ();
    public List<String> getSpecificFilterNames (String filterText) throws Exception;
    public boolean isScheduleMainPageLoaded () throws Exception;
    public boolean isManagerViewSelected () throws Exception;
    public boolean isMoreActionsBtnClickable() throws Exception;
    public void goToEditOperatingHoursView() throws Exception;
    public void checkOperatingHoursOnToggleSummary() throws Exception;
    public void checkOperatingHoursOnEditDialog() throws Exception;
    public void clickCancelBtnOnEditOpeHoursPage() throws Exception;
    public void editTheOperatingHoursWithFixedValue(List<String> weekDaysToClose, String startTime, String endTime) throws Exception;
    public void closeTheParticularOperatingDay(List<String> weekDaysToClose) throws Exception;
    public void openTheParticularOperatingDay(List<String> weekDaysToOpen) throws Exception;
    public void clickSaveBtnOnEditOpeHoursPage() throws Exception;
    public void checkClosedDayOnToggleSummary(List<String> weekDays) throws Exception;
    public void isSaveBtnLoadedOnEditOpeHoursPage() throws Exception;
    public void isCancelBtnLoadedOnEditOpeHoursPage() throws Exception;
    public void checkOpeHrsOfParticualrDayOnToggleSummary(List<String> weekDays, String duration) throws Exception;
    public void clickEditBtnOnToggleSummary() throws Exception;
    public void isCancelBtnLoadedOnEditOpeHoursPageForOP() throws Exception;
    public void isSaveBtnLoadedOnEditOpeHoursPageForOP() throws Exception;
    public void clickCancelBtnOnEditOpeHoursPageForOP() throws Exception;
    public void clickSaveBtnOnEditOpeHoursPageForOP() throws Exception;
    public void saveScheduleWithoutChange() throws Exception;

}

