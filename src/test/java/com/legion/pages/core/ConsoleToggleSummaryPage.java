package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.ToggleSummaryPage;
import org.openqa.selenium.support.PageFactory;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleToggleSummaryPage extends BasePage implements ToggleSummaryPage {
    public ConsoleToggleSummaryPage() {
        PageFactory.initElements(getDriver(), this);
    }
}
