package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.CreateSchedulePage;
import org.openqa.selenium.support.PageFactory;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleCreateSchedulePage extends BasePage implements CreateSchedulePage {
    public ConsoleCreateSchedulePage() {
        PageFactory.initElements(getDriver(), this);
    }
}
