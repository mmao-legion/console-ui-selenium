package com.legion.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface EditShiftPage {
    public boolean isEditShiftWindowLoaded() throws Exception;
    public void verifyTheTitleOfEditShiftsWindow(int selectedShiftCount, String startOfWeek) throws Exception;
    public void verifySelectedWorkDays(int selectedShitCount, List<String> selectedDays) throws Exception;
    public void verifyLocationNameShowsCorrectly(String locationName) throws Exception;
    public void verifyTheVisibilityOfButtons() throws Exception;
    public void verifyTheContentOfOptionsSection() throws Exception;
    public boolean isClearEditedFieldsBtnLoaded() throws Exception;
    public void verifyTwoColumns() throws Exception;
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
    public void verifyShiftInfoCard(List<String> shiftInfo) throws Exception;
    public void verifyTheContentOfOptionsSectionIsNotLoaded () throws Exception;
    public void verifyEditableTypesShowOnSingleEditShiftDetail() throws Exception;
    public void verifyTheTextInCurrentColumnOnSingleEditShiftPage(String type, String value) throws Exception;
    public void removeAllMealBreaks();
    public void removeAllRestBreaks() throws Exception;
    public List<String> getErrorMessageOfTime() throws Exception;
    public void inputMealBreakTimes(String startMealTime, String endMealTime, int index) throws Exception;
    public void inputRestBreakTimes(String startRestTime, String endRestTime, int index) throws Exception;
    public List<Map<String, String>> getMealBreakTimes() throws Exception;
    public List<Map<String, String>> getRestBreakTimes() throws Exception;
    public void clickOnAddMealBreakButton() throws Exception;
    public void clickOnAddRestBreakButton() throws Exception;
    public List<String> getMealBreakWarningMessage();
    public List<String> getRestBreakWarningMessage();
    public int getMealBreakCount () throws Exception;
    public int getRestBreakCount () throws Exception;
    public void clickOnUpdateAnywayButton() throws Exception;
    public void checkOrUncheckAutomaticallyScheduleOptimizedBreak(boolean isCheck) throws Exception;
    public void inputShiftNotesForEmptyInput(String shiftNote) throws Exception;

    public void clickOnStaffingOption(String staffingOption) throws Exception;

}
