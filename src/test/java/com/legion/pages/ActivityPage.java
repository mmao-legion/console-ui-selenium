package com.legion.pages;

import org.openqa.selenium.WebElement;

public interface ActivityPage {
    public void verifyActivityBellIconLoaded() throws Exception;
    public void verifyClickOnActivityIcon() throws Exception;
    public void clickActivityFilterByIndex(int index, String filterName) throws Exception;
    public WebElement verifyNewShiftSwapCardShowsOnActivity(String requestUserName, String respondUserName) throws Exception;
    public void approveOrRejectShiftSwapRequestOnActivity(String requestUserName, String respondUserName, String action) throws Exception;
}
