package com.legion.pages;

import cucumber.api.java.it.Ma;
import org.apache.xpath.operations.Bool;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface TeamPage {
	public void goToTeam() throws Exception;
//    public boolean isTeam() throws Exception;
//    public void goToCoverage() throws Exception;
//    public boolean isCoverage() throws Exception;
//    public void verifyTeamPage(boolean isTeamPage) throws Exception;
//    public void verifyCoveragePage(boolean isCoveragePage) throws Exception;
    public void performSearchRoster(List<String> list)throws Exception;
	public void coverage();
	public void coverageViewToPastOrFuture(String nextWeekView, int weekCount);
	public boolean loadTeamTab() throws Exception;
	public String searchAndSelectTeamMemberByName(String username) throws Exception;
	public void approvePendingTimeOffRequest() throws Exception;
	public int getPendingTimeOffRequestCount() throws Exception;
	public void openToDoPopupWindow() throws Exception;
	public void approveOrRejectTimeOffRequestFromToDoList(String userName, String timeOffStartDuration, String timeOffEndDuration, String action) throws Exception;
	public void closeToDoPopupWindow() throws Exception;
	public void verifyTeamPageLoadedProperlyWithNoLoadingIcon() throws Exception;
	public void verifyTheFunctionOfSearchTMBar(List<String> testStrings) throws Exception;
	public void verifyTheFunctionOfAddNewTeamMemberButton() throws Exception;
	public void verifyTheMonthAndCurrentDayOnCalendar(String currentDateForSelectedLocation) throws Exception;
	public String selectATeamMemberToTransfer() throws Exception;
	public String verifyHomeLocationCanBeSelected() throws Exception;
	public void verifyClickOnTemporaryTransferButton() throws Exception;
	public void verifyTwoCalendarsForCurrentMonthAreShown(String currentDate) throws Exception;
	public void verifyTheCalendarCanNavToPreviousAndFuture() throws Exception;
	public void verifyTheCurrentDateAndSelectOtherDateOnTransfer() throws Exception;
	public void verifyDateCanBeSelectedOnTempTransfer() throws Exception;
	public boolean isApplyButtonEnabled() throws Exception;
	public void verifyClickOnApplyButtonOnTransfer() throws Exception;
	public void verifyTheMessageOnPopupWindow(String currentLocation, String selectedLocation, String teamMemberName) throws Exception;
	public void verifyTheFunctionOfConfirmTransferButton() throws Exception;
	public void verifyTheHomeStoreLocationOnProfilePage(String homeLocation, String selectedLocation) throws Exception;
	public boolean verifyCancelTransferWindowPopup() throws Exception;
	public boolean verifyTransferButtonEnabledAfterCancelingTransfer() throws Exception;
	public void verifyHomeLocationAfterCancelingTransfer(String homeLocation) throws Exception;
	public boolean isProfilePageLoaded() throws Exception;
	public String verifyTheFunctionOfEditBadges() throws Exception;
	public void verifyTheVisibleOfBadgesOnTeamRoster(String firstName, String badgeID) throws Exception;
	public boolean isInviteTeamMemberWindowLoaded() throws Exception;
	public void verifyTheEmailFormatOnInviteWindow(List<String> testEmails) throws Exception;
	public boolean isSendAndCancelLoadedAndEnabledOnInvite() throws Exception;
	public String addANewTeamMemberToInvite(Map<String, String> newTMDetails) throws Exception;
	public void checkAddATMMandatoryFieldsAreLoaded(String mandatoryField) throws Exception;
	public void verifyThereSectionsAreLoadedOnInviteWindow() throws Exception;
	public String fillInMandatoryFieldsOnNewTMPage(Map<String, String> newTMDetails, String mandatoryField) throws Exception;
	public boolean isSaveButtonOnNewTMPageEnabled() throws Exception;
	public void verifyContactNumberFormatOnNewTMPage(List<String> contactNumbers) throws Exception;
	public int verifyTMCountIsCorrectOnRoster() throws Exception;
	public void verifyCancelButtonOnAddTMIsEnabled() throws Exception;
	public void verifyTheMandatoryFieldsCannotEmpty() throws Exception;
	public void verifyTMIsVisibleAndInvitedOnTODO(String name) throws Exception;
	public void	verifyInviteAndReInviteButtonThenInvite() throws Exception;
	public boolean isProfilePageSelected() throws Exception;
	public void navigateToProfileTab() throws Exception;
	public void navigateToWorkPreferencesTab() throws Exception;
	public boolean isWorkPreferencesPageLoaded() throws Exception;
	public void clickOnActivateButton() throws Exception;
	public void isActivateWindowLoaded() throws Exception;
	public void selectADateOnCalendarAndActivate() throws Exception;
    public void verifyDeactivateAndTerminateEnabled() throws Exception;
    public String getOnBoardedDate() throws Exception;
    public void isOnBoardedDateUpdated(String previousDate) throws Exception;
    public void verifyTheStatusOfTeamMember(String expectedStatus) throws Exception;
    public boolean isActivateButtonLoaded() throws Exception;
    public void sendTheInviteViaEmail(String email) throws Exception;
    public void saveTheNewTeamMember() throws Exception;
    public void searchTheNewTMAndUpdateInfo(String firstName) throws Exception;
    public boolean isEmailOrPhoneNumberEmptyAndUpdate(Map<String, String> newTMDetails, String mandatoryField) throws Exception;
    public void searchTheTMAndCheckUpdateInfoNotShow(String firstName) throws Exception;
    public boolean isTerminateButtonLoaded() throws Exception;
    public boolean isCancelTerminateButtonLoaded() throws Exception;
    public void terminateTheTeamMember(boolean isCurrentDay) throws Exception;
    public String getEmployeeIDFromProfilePage() throws Exception;
    public void searchTheTeamMemberByEmployeeIDFromRoster(String employeeID, boolean isTerminated) throws Exception;
    public void verifyTheFunctionOfCancelTerminate() throws Exception;
    public boolean isManualOnBoardButtonLoaded() throws Exception;
    public void manualOnBoardTeamMember() throws Exception;
    public void clickCancelButton() throws Exception;
    public void isCancelTransferButtonLoadedAndClick() throws Exception;
    public String selectATeamMemberToViewProfile() throws Exception;
    public List<String> getShiftPreferences() throws Exception;
    public void clickOnEditShiftPreference() throws Exception;
    public boolean isEditShiftPreferLayoutLoaded() throws Exception;
	public List<String> setSliderForShiftPreferences() throws Exception;
	public List<String> changeShiftPreferencesStatus() throws Exception;
	public void clickCancelEditShiftPrefBtn() throws Exception;
	public void clickSaveShiftPrefBtn() throws Exception;
	public void verifyCurrentShiftPrefIsConsistentWithTheChanged(List<String> shiftPrefs, List<String> changedShiftPrefs,
																 List<String> status) throws Exception;
	public void editOrUnLockAvailability() throws Exception;
	public boolean areCancelAndSaveAvailabilityBtnLoaded() throws Exception;
	public void changePreferredHours() throws Exception;
	public void changeBusyHours() throws Exception;
	public int verifyTimeOffRequestShowsOnToDoList(String userName, String timeOffStartDuration, String timeOffEndDuration) throws Exception;
	public int getTimeOffCountByStartAndEndDate(List<String> timeOffStartNEndDate) throws Exception;
	public void updatePhoneNumberAndEmailID(String phoneNumber, String emailID) throws Exception;
	public void updateEngagementDetails(Map<String, String> tmDetails) throws Exception;
	public List<String> getCurrentBadgesOnEngagement() throws Exception;
	public void clickOnEditBadgeButton() throws Exception;
	public List<String> updateTheSelectedBadges() throws Exception;
	public void updateProfilePicture(String filePath) throws Exception;
	public void clickOnJobTitleFilter() throws Exception;
	public void clickOnClearFilterBtn() throws Exception;
	public void navigateToSubTabOnCoverage(String subTabName) throws Exception;
	public HashMap<Integer, List<String>> getTimeOffWeekTableOnCoverage(HashMap<Integer, String> indexAndTimes, LinkedHashMap<String, List<String>> regularHours) throws Exception;
	public String selectAJobTitleByRandom() throws Exception;
	public HashMap<Integer, String> generateIndexAndRelatedTimes(LinkedHashMap<String, List<String>> regularHours) throws Exception;
	public void navigateToTimeOffPage() throws Exception;
	public void	selectTheJobTitleByName(String jobTitleName) throws Exception;
	public HashMap<Integer, List<String>> getTimeOffWeekTableByDateNTime(HashMap<Integer, List<String>> previousTimeOffs,
																		 HashMap<String, List<String>> selectedDateNTime, HashMap<Integer, String> indexAndTimes) throws Exception;
	public void updateBusinessProfilePicture(String filePath) throws Exception;
	public void rejectAllTheTimeOffRequests() throws Exception;
	public List<String> getTMNameList() throws Exception;
	public void rejectAllTeamMembersTimeOffRequest(ProfileNewUIPage profileNewUIPage, int index) throws Exception;
	public boolean verifyThereIsLocationColumnForMSLocationGroup() throws Exception;
}
