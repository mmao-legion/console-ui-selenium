package com.legion.pages;

import java.util.HashMap;

/**
 * Yanming
 */
public interface LoginPage {
    public void visitPage(HashMap<String, String> propertyMap);

    /* Aug 03-The below line is commented by Zorang Team and new line is added as required */
    //public void goToDashboardHome() throws Exception;
    public void goToDashboardHome(HashMap<String,String> propertyMap) throws Exception;
}
