package com.legion.pages.core.opusermanagement;

import com.legion.pages.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ModelSwitchPage extends BasePage {
    public ModelSwitchPage() {
        PageFactory.initElements(getDriver(), this);
        waitForPageLoaded(getDriver());
    }

    // Added by Sophia
    @FindBy(css = "img.modeSwitchIcon")
    private WebElement modeSwitchIcon;
    @FindBy(css = "p.ng-binding.menu-item-op_title.mt-18")
    private WebElement operationPortal;
    @FindBy(css = "p.ng-binding.menu-item-console_title")
    private WebElement console;
    @FindBy(css = "div.console-item>p.ng-binding.menu-item-op_title")
    private WebElement timeClock;

    public void openModeSwitchMenu() {
        waitForSeconds(5);
        modeSwitchIcon.click();
        waitForSeconds(3);
    }

    public void switchToOpsPortal() {
        openModeSwitchMenu();
        operationPortal.click();
        switchToNewTab();
        waitForSeconds(10);
    }

    public void switchToConsole() {
        openModeSwitchMenu();
        console.click();
        switchToNewTab();
        waitForSeconds(10);
    }

    public void switchToTimeClock() {
        openModeSwitchMenu();
        timeClock.click();
        switchToNewTab();
        waitForSeconds(10);
    }

    public void switchToNewTab() {
        String currentWindow = getDriver().getWindowHandle();
        for (String handle : getDriver().getWindowHandles()) {
            if (!handle.equals(currentWindow)) {
                getDriver().switchTo().window(handle);
            }
        }
    }

}
