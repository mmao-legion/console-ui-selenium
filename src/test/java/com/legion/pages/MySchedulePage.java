package com.legion.pages;

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
}
