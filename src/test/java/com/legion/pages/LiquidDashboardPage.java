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
}
