package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.ScheduleShiftTablePage;
import org.openqa.selenium.support.PageFactory;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleScheduleShiftTablePage extends BasePage implements ScheduleShiftTablePage {
    public ConsoleScheduleShiftTablePage() {
        PageFactory.initElements(getDriver(), this);
    }
}
