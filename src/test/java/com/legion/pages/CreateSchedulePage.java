package com.legion.pages;

import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;

public interface CreateSchedulePage {
    public Boolean isWeekGenerated() throws Exception;
    public Boolean isWeekPublished() throws Exception;
    public void generateSchedule() throws Exception;
    public void publishActiveSchedule() throws Exception;
    public boolean isCurrentScheduleWeekPublished();
    public Boolean isGenerateButtonLoaded() throws Exception;
    public Boolean isGenerateButtonLoadedForManagerView() throws Exception;
    public Boolean isReGenerateButtonLoadedForManagerView() throws Exception;
    public void switchToManagerViewToCheckForSecondGenerate() throws Exception;
    public void createScheduleForNonDGFlowNewUI() throws Exception;
    public void generateScheduleFromCreateNewScheduleWindow(String activeWeekText) throws Exception;
    public void selectWhichWeekToCopyFrom(String weekInfo) throws Exception;
    public void clickOnFinishButtonOnCreateSchedulePage() throws Exception;
    public void generateOrUpdateAndGenerateSchedule() throws Exception;
    public void editTheOperatingHours(List<String> weekDaysToClose) throws Exception;
    public float checkEnterBudgetWindowLoadedForNonDG() throws Exception;
    public void editTheBudgetForNondgFlow() throws Exception;
    public void createScheduleForNonDGFlowNewUIWithGivingParameters(String day, String startTime, String endTime) throws Exception;
    public void createScheduleForNonDGFlowNewUIWithGivingTimeRange(String startTime, String endTime) throws Exception;
    public void editOperatingHoursWithGivingPrameters(String day, String startTime, String endTime) throws Exception;
    public void fillBudgetValues(List<WebElement> element) throws Exception;
    public void editTheOperatingHoursForLGInPopupWinodw(List<String> weekDaysToClose) throws Exception;
    public float createScheduleForNonDGByWeekInfo(String weekInfo, List<String> weekDaysToClose, List<String> copyShiftAssignments) throws Exception;
    public void clickCreateScheduleBtn() throws Exception;
    public HashMap<String, String> verifyNGetBudgetNScheduleWhileCreateScheduleForNonDGFlowNewUI(String weekInfo, String location) throws Exception;
    public void clickNextBtnOnCreateScheduleWindow() throws Exception;
    public void verifyTheContentOnEnterBudgetWindow(String weekInfo, String location) throws Exception;
    public List<String> setAndGetBudgetForNonDGFlow() throws Exception;
    public boolean isComplianceWarningMsgLoad() throws Exception;
    public String getMessageForComplianceWarningInPublishConfirmModal() throws Exception;
    public boolean isPublishButtonLoaded();
    public void unGenerateActiveScheduleScheduleWeek() throws Exception;
    public void unGenerateActiveScheduleFromCurrentWeekOnward(int loopCount) throws Exception;
    public boolean isPublishButtonLoadedOnSchedulePage() throws Exception;
    public boolean isRepublishButtonLoadedOnSchedulePage() throws Exception;
    public boolean isPartialCopyOptionLoaded() throws Exception;
    public void clickBackBtnAndExitCreateScheduleWindow() throws Exception;
    public void clickExitBtnToExitCreateScheduleWindow() throws Exception;
    public void verifyLabelOfPublishBtn(String labelExpected) throws  Exception;
    public void verifyPreviousWeekWhenCreateAndCopySchedule(String weekInfo, boolean shouldBeSelected) throws Exception;
    public void verifyTooltipForCopyScheduleWeek(String weekInfo) throws Exception;
    public void verifyDifferentOperatingHours(String weekInfo) throws Exception;
    public boolean isCreateScheduleBtnLoadedOnSchedulePage() throws Exception;
    public void selectRandomOrSpecificLocationOnUngenerateScheduleEditOperatingHoursPage(String locationName) throws Exception;
    public boolean checkIfEditOperatingHoursButtonsAreShown() throws Exception;
    public void chooseLocationInCreateSchedulePopupWindow(String location) throws Exception;
    public boolean isCopyScheduleWindow() throws Exception;
    public void clickOnSchedulePublishButton() throws Exception;
    public void clickConfirmBtnOnPublishConfirmModal() throws Exception;
    public void copyAllPartialSchedule () throws Exception;
    public boolean checkOnlyCopyShiftsSwitchDisplayOrNot () throws Exception;
    public boolean checkOnlyCopyShiftsSwitchEnableOrNot () throws Exception;
    public void turnOnOrTurnOffOnlyCopyShiftsSwitch (boolean action) throws Exception;
    public boolean checkOnlyCopyPartialAssignmentSwitchEnableOrNot () throws Exception;

}
