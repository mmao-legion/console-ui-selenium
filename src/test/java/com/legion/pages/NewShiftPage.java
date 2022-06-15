package com.legion.pages;

import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;

public interface NewShiftPage {
    public void customizeNewShiftPage() throws Exception;
    public void moveSliderAtSomePoint(String shiftTime, int shiftStartingCount, String startingPoint) throws Exception;
    public void displayAlertPopUpForRoleViolation() throws Exception;
    public void clickRadioBtnStaffingOption(String staffingOption) throws Exception;
    public void clickOnCreateOrNextBtn() throws Exception;
    public void verifySelectTeamMembersOption() throws Exception;
    public void searchText(String searchInput) throws Exception;
    public void clickOnOfferOrAssignBtn() throws Exception;
    public void selectWorkRole(String workRoles) throws Exception;
    public void addOpenShiftWithDefaultTime(String workRole) throws Exception;
    public void addOpenShiftWithDefaultTime(String workRole, String location) throws Exception;
    public boolean ifWarningModeDisplay() throws Exception;
    public void addOpenShiftWithFirstDay(String workRole) throws Exception;
    public void clearAllSelectedDays() throws Exception;
    public void moveSliderAtCertainPoint(String shiftTime, String startingPoint) throws Exception;
    public void addNewShiftsByNames(List<String> names, String workRole) throws Exception;
    public void searchTeamMemberByNameAndAssignOrOfferShift(String name, Boolean isOffering) throws Exception;
    public void clickOnDayViewAddNewShiftButton() throws Exception;
    public void selectDaysByIndex(int index1, int index2, int index3) throws Exception;
    public void selectWorkingDaysOnNewShiftPageByIndex(int index) throws Exception;
    public void searchTeamMemberByName(String name) throws Exception;
    public String selectAndGetTheSelectedTM() throws Exception;
    public String selectTeamMembers() throws Exception;
    public void selectSpecificTMWhileCreateNewShift(String teamMemberName) throws Exception;
    public void addOpenShiftWithLastDay(String workRole) throws Exception;
    public void addManualShiftWithLastDay(String workRole, String tmName) throws Exception;
    public String searchAndGetTMName(String searchInput) throws Exception;
    public void editOperatingHoursOnScheduleOldUIPage(String startTime, String endTime, List<String> weekDaysToClose) throws Exception;
    public void selectChildLocInCreateShiftWindow(String location) throws Exception;
    public List<String> getAllLocationGroupLocationsFromCreateShiftWindow() throws Exception;
    public void searchTeamMemberByNameNLocation(String name, String location) throws Exception;
    public void selectTeamMembersOptionForSchedule() throws Exception;
    public void selectTeamMembersOptionForOverlappingSchedule() throws Exception;
    public boolean displayAlertPopUp() throws Exception;
    public void selectDaysFromCurrentDay(String currentDay) throws Exception;
    public String getTimeDurationWhenCreateNewShift() throws Exception;
    public void selectSpecificWorkDay(int dayCountInOneWeek);
    public void selectMultipleOrSpecificWorkDay(int dayCountInOneWeek, Boolean isSingleDay);
    public List<Integer> selectDaysByCountAndCannotSelectedDate(int count, String cannotSelectedDate) throws Exception;
    public void selectWeekDaysByDayName(String dayName) throws Exception;
    public List<String> getAllOperatingHrsOnCreateShiftPage() throws Exception;
    public List<String> getSelectedDayInfoFromCreateShiftPage() throws Exception;
    public void closeCustomizeNewShiftWindow() throws Exception;
    public void clickOnBackButton () throws Exception;
    public boolean checkIfWarningModalDisplay () throws Exception;
    public String getWarningMessageFromWarningModal () throws Exception;
    public void clickOnOkButtonOnWarningModal () throws Exception;
    public boolean checkIfNewCreateShiftPageDisplay() throws Exception;
    public void searchWithOutSelectTM(String tmName) throws Exception;
    public void checkOrUnCheckNextDayOnCreateShiftModal(boolean toCheck) throws Exception;
    public void clickClearAssignmentsLink() throws Exception;
    public boolean areWorkRoleDisplayOrderCorrect(HashMap<String, Integer> workRoleNOrders) throws Exception;
    public boolean checkIfWorkRoleDropDownIsLoadedOnNewCreateShiftPage () throws Exception;
    public List<String> searchWorkRoleOnNewCreateShiftPage (String workRole) throws Exception;
    public boolean checkIfShiftNameInputIsLoadedOnNewCreateShiftPage () throws Exception;
    public boolean checkIfShiftStartAndEndInputsAreLoadedOnNewCreateShiftPage () throws Exception;
    public boolean checkIfNextButtonIsLoadedOnNewCreateShiftPage () throws Exception;
    public boolean checkIfCreateButtonIsLoadedOnNewCreateShiftPage () throws Exception;
    public boolean checkIfCancelButtonIsLoadedOnNewCreateShiftPage () throws Exception;
    public boolean checkIfBackButtonIsLoadedOnNewCreateShiftPage () throws Exception;
    public boolean checkIfShiftPerDayInputIsLoadedOnNewCreateShiftPage () throws Exception;
    public int getShiftPerDayValue () throws Exception;
    public boolean checkIfSelectDaysCheckBoxAreLoadedOnNewCreateShiftPage () throws Exception;
    public boolean checkIfAssignmentDropDownListIsLoadedOnNewCreateShiftPage () throws Exception;
    public boolean checkIfShiftNotesTextAreaIsLoadedOnNewCreateShiftPage () throws Exception;
    public boolean checkIfCloseIconIsLoadedOnNewCreateShiftPage () throws Exception;
    public void closeNewCreateShiftPage () throws Exception;
    public boolean checkIfWorkRoleWarningMessageIsLoaded() throws Exception;
    public boolean checkIfAssignmentWarningMessageIsLoaded() throws Exception;
    public boolean checkIfSelectDaysWarningMessageIsLoaded() throws Exception;
    public String getShiftStartWarningMessage() throws Exception;
    public String getShiftEndWarningMessage() throws Exception;
    public String getShiftPerDayWarningMessage() throws Exception;
    public void setShiftPerDayOnNewCreateShiftPage (int shiftPerDay) throws Exception;
    public boolean checkClosedDayTooltipIsLoaded() throws Exception;
    public void moveMouseToSpecificWeekDayOnNewCreateShiftPage (String weekDay) throws Exception;
    public boolean checkIfShiftAssignAndOffersSectionsAreLoaded() throws Exception;
    public String getShiftAssignedMessage() throws Exception;
    public String getShiftOffersMessage() throws Exception;
    public int getOpenShiftCountOnShiftAssignedSection();
    public int getNoOfShiftPerDayOnSearchTMPage() throws Exception;
    public String getDaysScheduledOnSearchTMPage() throws Exception;
    public String getShiftCardInfoOnSearchTMPage() throws Exception;
    public boolean checkAssignShiftsForEachDaySwitchIsLoaded() throws Exception;
    public boolean checkAssignShiftsForEachDaySwitchIfEnabled() throws Exception;
    public void openOrCloseAssignShiftsForEachDaySwitch(boolean toOpen) throws Exception;
    public List<String> getAssignShiftsMessageOfEachDays();
    public List<WebElement> getSearchAndRecommendedResult();
    public List<String> getAssignedShiftOnShiftAssignedSection();
    public List<String> getShiftOffersOnShiftAssignedSection();
    public void clickClearOfferLink() throws Exception;
    public void removeAllAssignedShiftByClickRemoveIcon();
    public void removeAllOfferedShiftByClickRemoveIcon();
    public boolean checkConfirmPopupIsLoaded() throws Exception;
    public String getTitleOfConfirmPopup() throws Exception;
    public String getMessageOfConfirmPopup() throws Exception;
    public void clickOkBtnOnConfirmPopup() throws Exception;
    public void selectAssignShiftDaysByIndex(int index);
    public void selectAssignShiftDaysByDayName(String dayName);
    public void clickAssignShiftsForEachDaySwitch() throws Exception;
    public void setShiftNotesOnNewCreateShiftPage (String shiftNotes) throws Exception;
    public void setShiftNameOnNewCreateShiftPage (String shiftName) throws Exception;

}
