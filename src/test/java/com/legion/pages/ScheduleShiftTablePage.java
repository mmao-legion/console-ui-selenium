package com.legion.pages;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ScheduleShiftTablePage {
    public void reduceOvertimeHoursOfActiveWeekShifts() throws Exception;
    public List<WebElement> getAvailableShiftsInDayView();
    public void verifyNewAddedShiftFallsInDayPart(String nameOfTheShift, String dayPart) throws Exception;
    public ArrayList<WebElement> getAllAvailableShiftsInWeekView();
    public List<WebElement> getUnAssignedShifts();
    public HashMap<String, String> getFourUpComingShifts(boolean isStartTomorrow, String currentTime) throws Exception;
    public void scheduleUpdateAccordingToSelectWeek() throws Exception;
    public void getHoursAndTeamMembersForEachDaysOfWeek();
    public boolean verifyActiveWeekDailyScheduleHoursInWeekView();
    public void verifyScheduledHourNTMCountIsCorrect() throws Exception;
    public boolean verifyActiveWeekTeamMembersCountAvailableShiftCount();
    public ArrayList<String> getScheduleDayViewGridTimeDuration();
    public ArrayList<String> getScheduleDayViewBudgetedTeamMembersCount();
    public ArrayList<String> getScheduleDayViewScheduleTeamMembersCount();
    public void verifyShiftsChangeToOpenAfterTerminating(List<Integer> indexes, String name, String currentTime) throws Exception;
    public float newCalcTotalScheduledHourForDayInWeekView() throws Exception;
    public boolean newVerifyActiveWeekDailyScheduleHoursInWeekView() throws Exception;
    public float getActiveShiftHoursInDayView();
    public ArrayList<String> getAvailableJobTitleListInWeekView();
    public ArrayList<String> getAvailableJobTitleListInDayView();
    public WebElement clickOnProfileOfUnassignedShift() throws Exception;
    public List<String> getTheShiftInfoByIndex(int index) throws Exception;
    public List<String> getTheGreyedShiftInfoByIndex(int index) throws Exception;
    public String getTotalHrsFromRightStripCellByIndex(int index) throws Exception;
    public List<String> getTheShiftInfoInDayViewByIndex(int index) throws Exception;
    public String getIIconTextInfo(WebElement shift) throws Exception;
    public int getShiftIndexById(String id) throws Exception;
    public List<WebElement> getAllShiftsOfOneTM(String name) throws Exception;
    public List<WebElement> getAllOOOHShifts() throws Exception;
    public List<String> getComplianceMessageFromInfoIconPopup(WebElement shift) throws Exception;
    public WebElement getShiftById(String id) throws Exception;
    public WebElement clickOnProfileIconOfShiftInDayView(String openOrNot) throws Exception;
    public String getTheShiftInfoByIndexInDayview(int index) throws Exception;
    public void verifySearchResult (String firstNameOfTM, String lastNameOfTM, String workRole, String jobTitle, List<WebElement> searchResults) throws Exception;
    public void verifyUpComingShiftsConsistentWithSchedule(HashMap<String, String> dashboardShifts, HashMap<String, String> scheduleShifts) throws Exception;
    public void verifyNewShiftsAreShownOnSchedule(String name) throws Exception;
    public List<Integer> getAddedShiftIndexes(String name) throws Exception;
    public boolean areShiftsPresent() throws Exception;
    public int getShiftsCount() throws Exception;
    public boolean isProfileIconsClickable() throws Exception;
    public int getOTShiftCount();
    public void validateXButtonForEachShift() throws Exception;
    public float getShiftHoursByTMInWeekView(String teamMember);
    public void verifyWeeklyOverTimeAndFlag(String teamMemberName) throws Exception;
    public Map<String, String> getHomeLocationInfo() throws Exception;
    List<String> getWeekScheduleShiftTimeListOfWeekView(String teamMemberName) throws Exception;
    public HashMap<String, String> getTheHoursNTheCountOfTMsForEachWeekDays() throws Exception;
    public HashMap<String, List<String>> getTheContentOfShiftsForEachWeekDay() throws Exception;
    public void verifyDayHasShifts(String day) throws Exception;
    public List<String> getDayShifts(String index) throws Exception;
    public void verifyNoShiftsForSpecificWeekDay(List<String> weekDaysToClose) throws Exception;
    public void verifyStoreIsClosedForSpecificWeekDay(List<String> weekDaysToClose) throws Exception;
    public void dragOneShiftToMakeItOverTime() throws Exception;
    public void clickProfileIconOfShiftByIndex(int index) throws Exception;
    public void clickViewStatusBtn() throws Exception;
    public int getRandomIndexOfShift();
    public void dragOneAvatarToAnother(int startIndex, String firstName, int endIndex) throws Exception;
    public void verifyConfirmStoreOpenCloseHours() throws Exception;
    public boolean ifMoveAnywayDialogDisplay() throws Exception;
    public int getTheIndexOfTheDayInWeekView(String date) throws Exception;
    public HashMap<String,Integer> dragOneAvatarToAnotherSpecificAvatar(int startIndexOfTheDay, String user1, int endIndexOfTheDay, String user2) throws Exception;
    public void verifyMessageInConfirmPage(String expectedMassageInSwap, String expectedMassageInAssign) throws Exception;
    public void verifyMessageOnCopyMoveConfirmPage(String expectedMsgInCopy, String expectedMsgInMove) throws Exception;
    public void verifyConfirmBtnIsDisabledForSpecificOption(String optionName) throws Exception;
    public void selectCopyOrMoveByOptionName(String optionName) throws Exception;
    public void selectSwapOrAssignOption(String action) throws Exception;
    public void clickConfirmBtnOnDragAndDropConfirmPage() throws Exception;
    public List<String> getShiftSwapDataFromConfirmPage(String action) throws Exception;
    public int verifyDayHasShiftByName(int indexOfDay, String name) throws Exception;
    public String getWeekDayTextByIndex(int index) throws Exception;
    public boolean verifySwapAndAssignWarningMessageInConfirmPage(String expectedMessage, String action) throws Exception;
    public void clickCancelBtnOnDragAndDropConfirmPage() throws Exception;
    public List<String> getOpenShiftInfoByIndex(int index) throws Exception;
    public List<WebElement> getOneDayShiftByName(int indexOfDay, String name) throws Exception;
    public void dragOneShiftToAnotherDay(int startIndex, String firstName, int endIndex) throws Exception;
    public String getNameOfTheFirstShiftInADay(int dayIndex) throws Exception;
    public String getWarningMessageInDragShiftWarningMode() throws Exception;
    public void clickOnOkButtonInWarningMode() throws Exception;
    public void moveAnywayWhenChangeShift() throws Exception;
    public void verifyShiftIsMovedToAnotherDay(int startIndex, String firstName, int endIndex) throws Exception;
    public void verifyShiftIsCopiedToAnotherDay(int startIndex, String firstName, int endIndex) throws Exception;
    public WebElement getTheShiftByIndex(int index) throws Exception;
    public int getTheIndexOfEditedShift() throws Exception;
    public int getShiftsNumberByName(String name) throws Exception;
    public boolean isDragAndDropConfirmPageLoaded() throws Exception;
    public List<WebElement> getShiftsByNameOnDayView(String name) throws Exception;
    public List<String> getWeekScheduleShiftTitles() throws Exception;
    public List<String> getDayScheduleGroupLabels() throws Exception;
    public boolean isShiftInDayPartOrNotInWeekView(int shiftIndex, String dayPart) throws Exception;
    public int getTheIndexOfShift(WebElement shift) throws Exception;
    public boolean isShiftInDayPartOrNotInDayView(int shiftIndex, String dayPart) throws Exception;
    public List<String> verifyDaysHasShifts() throws Exception;
    public void verifyShiftTimeInReadMode(String index,String shiftTime) throws Exception;
    public List<String> getIndexOfDaysHaveShifts() throws Exception;
    public void verifyShiftsHasMinorsColorRing(String minorsType) throws Exception;
    public void verifyGroupCanbeCollapsedNExpanded() throws Exception;
    public List<String> verifyGroupByTitlesOrder() throws Exception;
    public void verifyGroupByTMOrderResults() throws Exception;
    public boolean inActiveWeekDayClosed(int dayIndex) throws Exception;
    public String getFullNameOfOneShiftByIndex (int index);
    public void verifyShiftsOrderByStartTime() throws Exception;
    public void expandOnlyOneGroup(String groupName) throws Exception;
    public void verifyGroupByTitlesAreExpanded() throws Exception;
    public void verifyTimeOffCardShowInCorrectDay(int dayIndex) throws Exception;
}
