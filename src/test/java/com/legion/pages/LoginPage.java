package com.legion.pages;

import java.util.HashMap;

/**
 * Yanming
 */
public interface LoginPage {
    /* Aug 03-The below line is commented by Zorang Team and new line is added as required */
    //public void goToDashboardHome() throws Exception;
    public void goToDashboardHome(HashMap<String,String> propertyMap) throws Exception;
    
    public void loginToLegionWithCredential(HashMap<String,String> propertyMap, String userName, String Password) throws Exception;
    public boolean isLoginDone() throws Exception;
    
    public void logOut() throws Exception;
    public void verifyLoginDone(boolean isLoginDone) throws Exception;
}
