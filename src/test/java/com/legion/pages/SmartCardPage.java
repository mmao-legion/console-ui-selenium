package com.legion.pages;

import java.util.HashMap;
import java.util.List;

public interface SmartCardPage {
    public HashMap<String, Float> getScheduleLabelHoursAndWages() throws Exception;
    public List<HashMap<String, Float>> getScheduleLabelHoursAndWagesDataForEveryDayInCurrentWeek() throws Exception;
    public HashMap<String, Float> updateScheduleHoursAndWages(HashMap<String, Float> scheduleHoursAndWages,
                                                                     String hours, String hoursAndWagesKey);
    public boolean isSmartCardAvailableByLabel(String cardLabel) throws Exception;
    public String getsmartCardTextByLabel(String cardLabel);
    public String getWeatherTemperature() throws Exception;
    public HashMap<String, String> getHoursFromSchedulePage() throws Exception;
    public void weatherWeekSmartCardIsDisplayedForAWeek() throws Exception;
    boolean isSmartCardScrolledToRightActive() throws Exception;
    public void validateTheAvailabilityOfOpenShiftSmartcard() throws Exception;
    public boolean isViewShiftsBtnPresent() throws Exception;
    public boolean isRequiredActionSmartCardLoaded() throws Exception;
    public void clickOnViewShiftsBtnOnRequiredActionSmartCard() throws Exception;
    public void clickOnClearShiftsBtnOnRequiredActionSmartCard() throws Exception;
}
