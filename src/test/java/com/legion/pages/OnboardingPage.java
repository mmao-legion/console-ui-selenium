package com.legion.pages;

public interface OnboardingPage {

    public void openOnboardingPage(String invitationCode, String firstName, boolean isRehired, String enterpriseDisplayName);
    public void verifyTheContentOfCreateAccountPage(String firstName, String invitationCode) throws Exception;
    public void verifyLastName(String lastName) throws Exception;
    public boolean isCreateAccountPageLoadedAfterVerifyingLastName() throws Exception;
    public void createAccountForNewHire(String password) throws Exception;
    public void verifyIsEmailCorrectDialogPopup() throws Exception;
    public void clickYesBtnOnIsEmailCorrectDialog() throws Exception;
}
