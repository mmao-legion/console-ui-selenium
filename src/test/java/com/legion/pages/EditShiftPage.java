package com.legion.pages;

import java.util.ArrayList;
import java.util.List;

public interface EditShiftPage {
    public boolean isEditShiftWindowLoaded() throws Exception;
    public void verifyTheTitleOfEditShiftsWindow(int selectedShiftCount, String startOfWeek) throws Exception;
    public void verifySelectedWorkDays(int selectedShitCount, List<String> selectedDays) throws Exception;
    public void verifyLocationNameShowsCorrectly(String locationName) throws Exception;
    public void verifyTheVisibilityOfButtons() throws Exception;
    public void verifyTheContentOfOptionsSection() throws Exception;
    public boolean isClearEditedFieldsBtnLoaded() throws Exception;
    public void verifyThreeColumns() throws Exception;
    public void verifyEditableTypesShowOnShiftDetail() throws Exception;
    public void clickOnXButton() throws Exception;
    public void clickOnCancelButton() throws Exception;
    public void verifyTheTextInCurrentColumn(String type, String value) throws Exception;
    public List<String> getOptionsFromSpecificSelect() throws Exception;
    public void clickOnDateSelect() throws Exception;
    public void clickOnWorkRoleSelect() throws Exception;
    public void clickOnAssignmentSelect() throws Exception;
    public void clickOnLocationSelect() throws Exception;
    public void selectSpecificOptionByText(String text) throws Exception;
    public String getSelectedWorkRole() throws Exception;
    public String getSelectedDate() throws Exception;
    public String getSelectedAssignment() throws Exception;
    public void clickOnClearEditedFieldsButton() throws Exception;
    public void clickOnUpdateButton() throws Exception;
    public void inputShiftName(String shiftName) throws Exception;
    public void inputShiftNotes(String shiftNote) throws Exception;
    public void inputStartOrEndTime(String time, boolean isStartTime) throws Exception;
    public void checkUseOffset(boolean isStartTimeSection, boolean check) throws Exception;
    public void verifyTheFunctionalityOfOffsetTime(String hours, String mins, String earlyOrLate, boolean isStartTimeSection) throws Exception;
    public void checkOrUnCheckNextDayOnBulkEditPage(boolean isCheck) throws Exception;
    public void checkOrUncheckOptionsByName(String optionName, boolean isCheck) throws Exception;
    public ArrayList getErrorMessageOfTime() throws Exception;
}
