package com.legion.pages;

import java.util.HashMap;
import java.util.List;

public interface SmartCardPage {
    public HashMap<String, Float> getScheduleLabelHoursAndWages() throws Exception;
    public List<HashMap<String, Float>> getScheduleLabelHoursAndWagesDataForEveryDayInCurrentWeek() throws Exception;
    public HashMap<String, Float> updateScheduleHoursAndWages(HashMap<String, Float> scheduleHoursAndWages,
                                                                     String hours, String hoursAndWagesKey);
}
