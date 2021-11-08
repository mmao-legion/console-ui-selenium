package com.legion.pages;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface MySchedulePage {
    public void validateTheDataAccordingToTheSelectedWeek() throws Exception;
    public void verifySelectOtherWeeks() throws Exception;
    public void validateTheNumberOfOpenShifts() throws Exception;
    public void verifyTheAvailabilityOfClaimOpenShiftPopup() throws Exception;
    public boolean verifyShiftRequestButtonOnPopup(List<String> requests) throws Exception;
    public int verifyClickOnAnyShift() throws Exception;
    public void clickTheShiftRequestByName(String requestName) throws Exception;
    public boolean isPopupWindowLoaded(String title) throws Exception;
    public void verifyClickCancelSwapOrCoverRequest() throws Exception;
    public int cancelClaimRequest(List<String> expectedRequests) throws Exception;
    public void verifyReConfirmDialogPopup() throws Exception;
    public void verifyClickOnYesButton() throws Exception;
    public void verifyThePopupMessageOnTop(String expectedMessage) throws Exception;
    public int selectOneShiftIsClaimShift(List<String> claimShift) throws Exception;
    public void validateProfilePictureInAShiftClickable() throws Exception;
    public void clickTheShiftRequestToClaimShift(String requestName, String requestUserName) throws Exception;
    public void verifyTheRedirectionOfBackButton() throws Exception;
    public void verifyBackNSubmitBtnLoaded() throws Exception;
    public void verifyClickAcceptSwapButton() throws Exception;
    public void verifyClickAgreeBtnOnClaimShiftOffer() throws Exception;
    public void verifyClickAgreeBtnOnClaimShiftOfferWhenDontNeedApproval() throws Exception;
    public void verifyTheColorOfCancelClaimRequest(String cancelClaim) throws Exception;
    public void validateViewShiftsClickable() throws Exception;
    public void verifyComponentsOnSubmitCoverRequest() throws Exception;
    public void verifyClickOnSubmitButton() throws Exception;
    public void clickOnShiftByIndex(int index) throws Exception;
    public void	verifyComparableShiftsAreLoaded() throws Exception;
    public String selectOneTeamMemberToSwap() throws Exception;
    public void goToSchedulePageAsTeamMember() throws Exception;
    public void gotoScheduleSubTabByText(String subTitle) throws Exception;
    public void	verifyTeamScheduleInViewMode() throws Exception;
    public List<String> getWholeWeekSchedule() throws Exception;
    public String getSelectedWeek() throws Exception;
    public void verifyTheDataOfComparableShifts() throws Exception;
    public void verifyTheSumOfSwapShifts() throws Exception;
    public void verifyNextButtonIsLoadedAndDisabledByDefault() throws Exception;
    public void verifySelectOneShiftNVerifyNextButtonEnabled() throws Exception;
    public void verifySelectMultipleSwapShifts() throws Exception;
    public void verifyClickOnNextButtonOnSwap() throws Exception;
    public void verifySwapRequestShiftsLoaded() throws Exception;
    public void verifyTheContentOfMessageOnSubmitCover() throws Exception;
    public void verifyShiftRequestStatus(String expectedStatus) throws Exception;
    void verifyScheduledNOpenFilterLoaded() throws Exception;
    void verifyClaimShiftOfferNBtnsLoaded() throws Exception;
    List<String> getShiftHoursFromInfoLayout() throws Exception;
    void verifyTheShiftHourOnPopupWithScheduleTable(String scheduleShiftTime, String weekDay) throws Exception;
    String getSpecificShiftWeekDay(int index) throws Exception;
    void verifyClickNoButton() throws Exception;
    void verifyTheFunctionalityOfClearFilter() throws Exception;
    void validateTheAvailabilityOfScheduleTable(String userName) throws Exception;
    void validateTheAvailabilityOfScheduleMenu() throws Exception;
    void validateTheFocusOfSchedule() throws Exception;
    void validateTheDefaultFilterIsSelectedAsScheduled() throws Exception;
    void validateTheFocusOfWeek(String currentDate) throws Exception;
    void validateTheSevenDaysIsAvailableInScheduleTable() throws Exception;
    String getTheEarliestAndLatestTimeInScheduleTable() throws Exception;
    void compareOperationHoursBetweenAdminAndTM(String theEarliestAndLatestTimeInScheduleSummary, String theEarliestAndLatestTimeInScheduleTable) throws Exception;
    void validateThatHoursAndDateIsVisibleOfShifts() throws Exception;
    void validateTheDisabilityOfLocationSelectorOnSchedulePage() throws Exception;
    void validateTheAvailabilityOfInfoIcon() throws Exception;
    void validateInfoIconClickable() throws Exception;
    void verifyShiftsAreSwapped(List<String> swapData) throws Exception;
    public void clickCloseDialogButton () throws Exception;
}
