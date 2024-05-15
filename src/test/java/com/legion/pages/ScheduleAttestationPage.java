package com.legion.pages;

import java.util.Map;

public interface ScheduleAttestationPage {
    public boolean isScheduleChangeReasonModalLoaded();
    public Map<String, String> getScheduleChangesByIndex(int index);
    public void clickSaveButtonOnScheduleChangeReasonModal();
}
