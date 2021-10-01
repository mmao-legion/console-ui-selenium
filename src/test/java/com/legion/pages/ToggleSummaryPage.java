package com.legion.pages;

import java.util.HashMap;

public interface ToggleSummaryPage {
    public HashMap<String, String> getOperatingHrsValue(String day) throws Exception;
    public String getTheEarliestAndLatestTimeInSummaryView(HashMap<String, Integer> schedulePoliciesBufferHours) throws Exception;
    public void toggleSummaryView() throws Exception;
    public boolean isSummaryViewLoaded() throws Exception;
}
