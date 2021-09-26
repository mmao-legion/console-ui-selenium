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

}
