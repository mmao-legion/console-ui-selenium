package com.legion.pages;

import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;

public interface ShiftOperatePage {
    public void deleteShift() throws Exception;
	public void deleteAllShiftsInDayView() throws Exception;
    public void convertAllUnAssignedShiftToOpenShift() throws Exception;
    public void convertUnAssignedShiftToOpenShift(WebElement unAssignedShift) throws Exception;
    public void  verifyDeleteShift() throws Exception;
    public boolean isDeleteShiftShowWell() throws Exception;
    public void deleteLatestOpenShift() throws Exception;
    public void selectAShiftToAssignTM(String username) throws Exception;
    public void deleteAllShiftsOfGivenDayPartInWeekView(String dayPart) throws Exception;
    public void closeViewStatusContainer() throws Exception;
    public void deleteAllShiftsOfGivenDayPartInDayView(String dayPart) throws Exception;
    public void  verifyDeleteShiftCancelButton() throws Exception;
    public WebElement clickOnProfileIcon() throws Exception;
    public boolean isProfileIconsEnable() throws Exception;
    public void verifyMealBreakTimeDisplayAndFunctionality(boolean isEditMealBreakEnabled) throws Exception;
    public void verifyDeleteMealBreakFunctionality() throws Exception;
    public void verifyEditMealBreakTimeFunctionality(boolean isSavedChange) throws Exception;
    public boolean isMealBreakTimeWindowDisplayWell(boolean isEditMealBreakEnabled) throws Exception;
    public void clickOnEditMeaLBreakTime() throws Exception;
    public void editAndVerifyShiftTime(boolean isSaveChange) throws Exception;
    public void clickOnEditShiftTime() throws Exception;
    public List<String> editShiftTime() throws Exception;
    public void clickOnUpdateEditShiftTimeButton() throws Exception;
    public void clickOnCancelEditShiftTimeButton() throws Exception;
    public boolean verifyContextOfTMDisplay() throws Exception;
    public boolean isViewProfileEnable() throws Exception;
    public void clickOnViewProfile() throws Exception;
    public boolean isViewOpenShiftEnable() throws Exception;
    public boolean isChangeRoleEnable() throws Exception;
    public boolean isAssignTMEnable() throws Exception;
    public boolean isEditShiftNotesEnable() throws Exception;
    public void clickonAssignTM() throws Exception;
    public void clickOnConvertToOpenShift() throws Exception;
    public void verifyOfferTMOptionIsAvailable() throws Exception;
    public boolean isConvertToOpenEnable() throws Exception;
    public boolean isOfferTMOptionVisible() throws Exception;
    public boolean isOfferTMOptionEnabled() throws Exception;
    public WebElement clickOnProfileIconOfOpenShift() throws Exception;
    public String getTMDetailNameFromProfilePage(WebElement shift) throws Exception;
    public List<String> verifyEditBreaks() throws Exception;
    public void changeWorkRoleInPromptOfAShift(boolean isApplyChange, WebElement shift) throws Exception;
    public void changeWorkRoleInPromptOfAShiftInDayView(boolean isApplyChange, String shiftid) throws Exception;
    public void verifyEditMealBreakTimeFunctionalityForAShift(boolean isSavedChange, WebElement shift) throws Exception;
    public void verifyEditMealBreakTimeFunctionalityForAShiftInDayView(boolean isSavedChange, String shiftid) throws Exception;
    public void editTheShiftTimeForSpecificShift(WebElement shift, String startTime, String endTime) throws Exception;
    public void deleteMealBreakForOneShift(WebElement shift) throws Exception;
    public String getRandomWorkRole() throws Exception;
    public void deleteAllOOOHShiftInWeekView() throws Exception;
    public void editShiftTimeToTheLargest() throws Exception;
    public void clickOnChangeRole() throws Exception;
    public void changeWorkRoleInPrompt(boolean isApplyChange) throws Exception;
    public String convertToOpenShiftAndOfferToSpecificTMs() throws Exception;
    public boolean isEditMealBreakEnabled() throws Exception;
    public boolean validateVariousWorkRolePrompt() throws Exception;
    public void verifyRecommendedAndSearchTMEnabled() throws Exception;
    public void verifyPersonalDetailsDisplayed() throws Exception;
    public void verifyWorkPreferenceDisplayed() throws Exception;
    public void verifyAvailabilityDisplayed() throws Exception;
    public void closeViewProfileContainer() throws Exception;
    public void verifyChangeRoleFunctionality() throws Exception;
    public void deleteTMShiftInWeekView(String teamMemberName) throws Exception;
    public boolean verifyConvertToOpenPopUpDisplay(String firstNameOfTM) throws Exception;
    void convertToOpenShiftDirectly();
    public void verifyMessageIsExpected(String messageExpected) throws Exception;
    public String getAllTheWarningMessageOfTMWhenAssign() throws Exception;
    public void verifyWarningModelForAssignTMOnTimeOff(String nickName) throws Exception;
    public void verifyInactiveMessageNWarning(String username, String date) throws Exception;
    public void verifyScheduledWarningWhenAssigning(String userName, String shiftTime) throws Exception;
    public void switchSearchTMAndRecommendedTMsTab() throws Exception;
    public void verifyEditShiftTimePopUpDisplay() throws Exception;
    public void verifyListOfOfferNotNull() throws Exception;
    public String getTheMessageOfTMScheduledStatus() throws Exception;
    public void verifyWarningModelMessageAssignTMInAnotherLocWhenScheduleNotPublished() throws Exception;
    public void verifyTMNotSelected() throws Exception;
    public void verifyAlertMessageIsExpected(String messageExpected) throws Exception;
    public void clickOnRadioButtonOfSearchedTeamMemberByName(String name) throws Exception;
    public void clickOnAssignAnywayButton() throws Exception;
    public boolean verifyWFSFunction();
    public void deleteAllShiftsInWeekView() throws Exception;

    public void clickOnOfferTMOption() throws Exception;
    public void verifyRecommendedTableHasTM() throws Exception;
    public void verifyTMInTheOfferList(String firstName, String expectedStatus) throws Exception;
    public String getViewStatusShiftsInfo() throws Exception;
    public List<String> getStartAndEndOperatingHrsOnEditShiftPage() throws Exception;
    public HashMap<String, String> getMealAndRestBreaksTime() throws Exception;
    public void verifySpecificOptionEnabledOnShiftMenu(String optionName) throws Exception;
    public void verifyShiftInfoIsCorrectOnMealBreakPopUp(List<String> expectedShiftInfo) throws Exception;
    public void verifyMealBreakAndRestBreakArePlacedCorrectly() throws Exception;
    public void verifySpecificShiftHaveEditIcon(int index) throws Exception;
    public void verifyBreakTimesAreUpdated(List<String> expectedBreakTimes) throws Exception;
    public void verifyShiftNotesContent(String shiftNotes) throws Exception;
    public void addShiftNotesToTextarea(String notes) throws Exception;
    public String getShiftInfoInEditShiftDialog() throws Exception;
    public void clickOnEditShiftNotesOption() throws Exception;
    public boolean checkIfTMExistsInRecommendedTab (String fullNameOfTM);
    public void moveMealAndRestBreaksOnEditBreaksPage(String breakTime, int index, boolean isMealBreak) throws Exception;
}
