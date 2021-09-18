package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.ScheduleMainPage;
import org.openqa.selenium.support.PageFactory;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleScheduleMainPage extends BasePage implements ScheduleMainPage {
    public ConsoleScheduleMainPage() {
        PageFactory.initElements(getDriver(), this);
    }
}
