package com.legion.pages.core;

import com.legion.pages.AnalyzePage;
import com.legion.pages.BasePage;
import org.openqa.selenium.support.PageFactory;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleAnalyzePage extends BasePage implements AnalyzePage {
    public ConsoleAnalyzePage() {
        PageFactory.initElements(getDriver(), this);
    }
}
