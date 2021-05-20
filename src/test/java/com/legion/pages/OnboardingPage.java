package com.legion.pages;

public interface OnboardingPage {

    public void openOnboardingPage(String invitationCode, String firstName, boolean isRehired, String enterpriseDisplayName);
    public void verifyTheContentOfCreateAccountPage(String firstName, String invitationCode) throws Exception;
}
