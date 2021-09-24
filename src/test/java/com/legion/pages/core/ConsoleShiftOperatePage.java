package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.ShiftOperatePage;
import org.openqa.selenium.support.PageFactory;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleShiftOperatePage extends BasePage implements ShiftOperatePage {
    public ConsoleShiftOperatePage() {
        PageFactory.initElements(getDriver(), this);
    }
}
