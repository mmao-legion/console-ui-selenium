package com.legion.pages;

import org.openqa.selenium.WebElement;

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
    public void clickOnDayViewAddNewShiftButton() throws Exception;
    public void selectDaysByIndex(int index1, int index2, int index3) throws Exception;
    public void selectWorkingDaysOnNewShiftPageByIndex(int index) throws Exception;
    public void searchTeamMemberByName(String name) throws Exception;
    public WebElement selectAndGetTheSelectedTM() throws Exception;
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
}
