package com.legion.pages;

import java.util.LinkedHashMap;
import java.util.List;

public interface InboxPage {

    //Added by Nora

    //Added by Julie
    public void createGFEAnnouncement() throws Exception;
    public void clickOnInboxConsoleMenuItem() throws Exception;
    public LinkedHashMap<String, List<String>> getGFEWorkingHours() throws Exception;
    public String getGFEFirstDayOfWeek() throws Exception;
    public boolean compareGFEWorkingHrsWithRegularWorkingHrs(LinkedHashMap<String, List<String>> GFEWorkingHours,
                                                             LinkedHashMap<String, List<String>> regularHoursFromControl) throws Exception;
    public void verifyVSLInfo(boolean isVSLTurnOn) throws Exception;

    //Added by Marym
    public void checkCreateAnnouncementPageWithGFETurnOnOrTurnOff(boolean isTurnOn) throws Exception;
    public void checkCreateGFEPage() throws Exception;

    //Added by Haya
    public void sendToTM(String nickName) throws Exception;
}
