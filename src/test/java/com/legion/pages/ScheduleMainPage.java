package com.legion.pages;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface ScheduleMainPage {
    public void clickOnScheduleAnalyzeButton() throws Exception;
    public void clickOnCancelButtonOnEditMode() throws Exception;
    public void clickOnEditButton() throws Exception;
    public void clickSaveBtn() throws Exception;
    public boolean isScheduleTypeLoaded();
    public void switchToManagerView() throws Exception;
    public void clickOnEditButtonNoMaterScheduleFinalizedOrNot() throws Exception;
    public boolean checkEditButton() throws Exception;
    public boolean checkSaveButton() throws Exception;
    public boolean checkCancelButton() throws Exception;
    public void verifyEditButtonFuntionality() throws Exception;
    public boolean isScheduleFinalized() throws Exception;
    public void selectCancelButton() throws Exception;
    public void selectSaveButton() throws Exception;
    public Boolean isAddNewDayViewShiftButtonLoaded() throws Exception;
}
