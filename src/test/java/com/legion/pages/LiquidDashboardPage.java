package com.legion.pages;

import org.openqa.selenium.WebElement;
import java.util.HashMap;
import java.util.List;

public interface LiquidDashboardPage {
    public void enterEditMode() throws Exception;
    public void switchOnWidget(String widget) throws Exception;
    public void switchOffWidget(String widget) throws Exception;
    public void closeWidget(String widgetTitle) throws Exception;
    public void verifyUpdateTimeInfoIcon(String widgetTitle) throws Exception;
    public void saveAndExitEditMode() throws Exception;
    public void cancelAndExitEditMode() throws Exception;
    public void verifyBackBtn() throws Exception;
    public void verifySearchInput(String widgetTitle) throws Exception;
    public void clickOnLinkByWidgetNameAndLinkName(String widgetName, String LinkName) throws Exception;
    public boolean isSpecificWidgetLoaded(String widgetName) throws Exception;
    public String getTheStartOfCurrentWeekFromSchedulesWidget() throws Exception;
    public List<String> verifyTheContentOnAlertsWidgetLoaded(String currentWeek) throws Exception;
    public void verifyEditLinkOfHelpgulLinks() throws Exception;
    public void addLinkOfHelpfulLinks() throws Exception;
    public void deleteAllLinks() throws Exception;
    public void saveLinks() throws  Exception;
    public void cancelLinks() throws Exception;
    public void verifyLinks() throws Exception;
    public void verifyNoLinksOnHelpfulLinks() throws Exception;
    public void verifyIsGraphExistedOnWidget() throws Exception;
    public HashMap <String, Float> getDataOnTodayForecast() throws Exception;
    public List<String> getDataOnSchedulesWidget() throws Exception;
    public void verifyTheContentOnTimesheetApprovalStatusWidgetLoaded(String currentWeek) throws Exception;
    public void verifyTheContentOfOpenShiftsWidgetLoaded(String currentWeek) throws Exception;
    public boolean isOpenShiftsPresent() throws Exception;
    public boolean isOpenShiftsNoContent() throws Exception;
    public void switchWeeksOnOpenShiftsWidget(String lastWeek, String currentWeek, String nextWeek) throws Exception;
    public String getTheStartOfLastWeekFromSchedulesWidget() throws Exception;
    public String getTheStartOfNextWeekFromSchedulesWidget() throws Exception;
    public HashMap<String, int[]> getDataFromOpenShiftsWidget() throws Exception;
    public int getTimeSheetApprovalStatusFromPieChart() throws Exception;
    public void verifyWeekInfoOnWidget(String widgetTitle, String startdayOfWeek) throws Exception;
    public void clickOnCarouselOnWidget(String widgetTitle, String rightOrLeft) throws Exception;
    public void clickFirstWeekOnSchedulesGoToSchedule() throws Exception;
    public List<String> getDataOnComplianceViolationWidget() throws Exception;
    public void goToCompliancePage() throws Exception;
    public List<String> getDataInCompliancePage(String location) throws Exception;
    public void verifyNoContentOfSwapsNCoversWidget() throws Exception;
    public int getApprovalRateOnTARWidget() throws Exception;
    public void verifyTheContentOfSwapNCoverWidget(String swapOrCover) throws Exception;
}
