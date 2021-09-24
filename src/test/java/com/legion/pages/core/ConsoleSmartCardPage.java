package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.SmartCardPage;
import org.openqa.selenium.support.PageFactory;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleSmartCardPage extends BasePage implements SmartCardPage {
    public ConsoleSmartCardPage() {
        PageFactory.initElements(getDriver(), this);
    }
}
