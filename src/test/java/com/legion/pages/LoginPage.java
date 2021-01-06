package com.legion.pages;

import java.util.HashMap;


public interface LoginPage {
    /* Aug 03-The below line is commented by Zorang Team and new line is added as required */
    //public void goToDashboardHome() throws Exception;
    public void goToDashboardHome(HashMap<String,String> propertyMap) throws Exception;
    public void loginToLegionWithCredential(String userName, String Password) throws Exception;
    public boolean isLoginDone() throws Exception;
    public void goToDashboardHomePage(String username, String password) throws Exception;
    public void logOut() throws Exception;
    public void verifyLoginDone(boolean isLoginDone, String selectedLocation) throws Exception;
    public void verifyNewTermsOfServicePopUp() throws Exception;
}
