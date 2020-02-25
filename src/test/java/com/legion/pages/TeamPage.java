package com.legion.pages;

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
	public void searchAndSelectTeamMemberByName(String username) throws Exception;
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
	public void verifyDateCanBeSelectedOnTransfer() throws Exception;
	public boolean isApplyButtonEnabled() throws Exception;
	public void verifyClickOnApplyButtonOnTransfer() throws Exception;
	public void verifyTheMessageOnPopupWindow(String currentLocation, String selectedLocation, String teamMemberName) throws Exception;
	public void verifyTheFunctionOfConfirmTransferButton() throws Exception;
	public void	verifyTheFunctionOfCancelTransferButton() throws Exception;
	public void verifyTheHomeStoreLocationOnProfilePage(String homeLocation, String selectedLocation) throws Exception;
	public String selectATeamMemberToViewProfile() throws Exception;
	public void selectATeamMemberToCancelTransfer() throws Exception;
	public boolean verifyCancelTransferWindowPopup() throws Exception;
	public boolean verifyTransferButtonEnabledAfterCancelingTransfer() throws Exception;
	public void verifyHomeLocationAfterCancelingTransfer(String homeLocation) throws Exception;
	public boolean isProfilePageLoaded() throws Exception;
	public void verifyTheFunctionOfEditBadges() throws Exception;
	public void verifyTheVisibleOfBadgesOnTeamRoster() throws Exception;
	public void selectATeamMemberToInvite() throws Exception;
	public boolean isInviteTeamMemberWindowLoaded() throws Exception;
	public void verifyTheEmailFormatOnInviteWindow(List<String> testEmails) throws Exception;
	public boolean isSendAndCancelLoadedAndEnabledOnInvite() throws Exception;
	public boolean addANewTeamMember(Map<String, String> newTMDetails) throws Exception;
	public void checkAddATMMandatoryFieldsAreLoaded() throws Exception;
	public void inviteTheNewCreatedTeamMember(String firstName) throws Exception;
	public void verifyThereSectionsAreLoadedOnInviteWindow() throws Exception;
	public void fillInMandatoryFieldsOnNewTMPage(Map<String, String> newTMDetails) throws Exception;
	public boolean isSaveButtonOnNewTMPageEnabled() throws Exception;
	public void verifyContactNumberFormatOnNewTMPage(List<String> contactNumbers) throws Exception;
	public void verifyTMCountIsCorrectOnRoster() throws Exception;
	public void verifyCancelButtonOnAddTMAndClick() throws Exception;
	public void verifyTheMandatoryFieldsCannotEmpty() throws Exception;
	public void verifyTMIsVisibleAndInvitedOnTODO(String name) throws Exception;
	public void	selectAInvitedOrNotInvitedTeamMemberToView() throws Exception;
}
