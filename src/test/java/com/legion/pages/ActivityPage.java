package com.legion.pages;

import org.openqa.selenium.WebElement;

public interface ActivityPage {
    public void verifyActivityBellIconLoaded() throws Exception;
    public void verifyClickOnActivityIcon() throws Exception;
    public void clickActivityFilterByIndex(int index, String filterName) throws Exception;
    public void verifyNewShiftSwapCardShowsOnActivity(String requestUserName, String respondUserName) throws Exception;
    public void approveOrRejectShiftSwapRequestOnActivity(String requestUserName, String respondUserName, String action) throws Exception;
    public void verifyActivityOfPublishSchedule(String requestUserName) throws Exception;
    public void verifyClickOnActivityCloseButton() throws Exception;
    public void verifyActivityOfUpdateSchedule(String requestUserName)throws Exception;
    public void verifyFiveActivityButtonsLoaded() throws Exception;
    public boolean isActivityContainerPoppedUp() throws Exception;
}
