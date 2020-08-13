package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.InboxPage;
import org.openqa.selenium.support.PageFactory;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleInboxPage  extends BasePage implements InboxPage {
    public ConsoleInboxPage(){
        PageFactory.initElements(getDriver(), this);
    }
}
