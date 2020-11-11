package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.CinemarkMinorPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleCinemarkMinorPage extends BasePage implements CinemarkMinorPage {
    public ConsoleCinemarkMinorPage() {
        PageFactory.initElements(getDriver(), this);
    }

    @FindBy(css = "div[class=\"console-navigation-item-label Configuration\"]")
    private WebElement configurationTabInOP;
    @Override
    public void clickConfigurationTabInOP() throws Exception {
        if(isElementLoaded(configurationTabInOP, 15))
        {
            activeConsoleName = configurationTabInOP.getText();
            click(configurationTabInOP);
        }else{
            SimpleUtils.fail("Configuration button not present on the page",false);
        }
    }
}
