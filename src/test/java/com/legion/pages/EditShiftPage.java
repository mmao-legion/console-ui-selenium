package com.legion.pages;

import java.util.List;

public interface EditShiftPage {
    public boolean isEditShiftWindowLoaded() throws Exception;
    public void verifyTheTitleOfEditShiftsWindow(int selectedShiftCount) throws Exception;
    public void verifySelectedWorkDays(int selectedShitCount, List<String> selectedDays) throws Exception;
    public void verifyLocationNameShowsCorrectly(String locationName) throws Exception;
    public void verifyTheVisibilityOfButtons() throws Exception;
    public void verifyTheContentOfOptionsSection() throws Exception;
    public boolean isClearEditedFieldsBtnLoaded() throws Exception;
    public void verifyThreeColumns() throws Exception;
    public void verifyEditableTypesShowOnShiftDetail() throws Exception;
    public void clickOnXButton() throws Exception;
    public void clickOnCancelButton() throws Exception;
}
