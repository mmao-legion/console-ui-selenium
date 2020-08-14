package com.legion.pages;

public interface InboxPage {

    //Added by Nora

    //Added by Julie
    public void createGFEAnnouncement() throws Exception;
    public void clickOnInboxConsoleMenuItem() throws Exception;

    //Added by Marym
    public void checkCreateAnnouncementPageWithGFETurnOnOrTurnOff(boolean isTurnOn) throws Exception;

    //Added by Haya
    public void sendToTM(String nickName) throws Exception;
}
