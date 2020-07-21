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
}
