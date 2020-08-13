package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.InboxPage;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.apache.bcel.generic.Select;
import org.apache.bcel.generic.Visitor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.ClickElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleInboxPage  extends BasePage implements InboxPage {
    public ConsoleInboxPage(){
        PageFactory.initElements(getDriver(), this);
    }

    //Added by Nora

    //Added by Julie
    final static String consoleInboxMenuItemText = "Inbox";

    @FindBy(css = "a[ng-click=\"createAnnouncement()\"]")
    private WebElement createAnnouncementIcon;

    @FindBy(css = "option[label=\"Message\"]")
    private WebElement messageType;

    @FindBy(css = "option[label=\"Good Faith Estimate\"]")
    private WebElement GFEType;

    @FindBy(className = "console-navigation-item")
    private List<WebElement> consoleNavigationMenuItems;

    @FindBy(css = ".select-wrapper")
    private WebElement announcementType;

    @Override
    public void clickOnInboxConsoleMenuItem() throws Exception {
        if (consoleNavigationMenuItems.size() != 0) {
            WebElement consoleInboxMenuElement = SimpleUtils.getSubTabElement(consoleNavigationMenuItems, consoleInboxMenuItemText);
            clickTheElement(consoleInboxMenuElement);
            SimpleUtils.pass("'Inbox' Console Menu loaded successfully!");
        } else
            SimpleUtils.fail("'Inbox' Console Menu failed to load!", false);
    }

    @Override
    public void createGFEAnnouncement() throws Exception {
        if (isElementLoaded(createAnnouncementIcon,10)) {
            clickTheElement(createAnnouncementIcon);
            if (isElementLoaded(announcementType,5)) {
                clickTheElement(announcementType);
                waitForSeconds(5);
                selectByVisibleText(GFEType, "Good Faith Estimate");
                SimpleUtils.pass("Inbox: A new announcement with type \"Good Faith Estimate\" is selected successfully");
            } else
                SimpleUtils.fail("Inbox: Create New Announcement window failed to load after clicking + icon",false);
        } else
            SimpleUtils.fail("Inbox: Create Announcement + icon failed to load",false);
    }

    //Added by Marym

    //Added by Haya
    @FindBy(css = "form-section[form-title=\"Predictable Schedule\"] .lg-question-input")
    private List<WebElement> predictableScheduleSectionToggles;

    @Override
    public void turnGFEToggleOnOrOff(boolean isTurnOn) throws Exception {
        if (areListElementVisible(predictableScheduleSectionToggles,5) && predictableScheduleSectionToggles.size()>3){
            if (isTurnOn){
                if (predictableScheduleSectionToggles.get(3).findElement(By.cssSelector("input")).getAttribute("class").contains("ng-empty")){
                    click(predictableScheduleSectionToggles.get(3).findElement(By.cssSelector("span")));
                    SimpleUtils.pass("GFE toggle is turned on!");
                } else {
                    SimpleUtils.report("GFE toggle is already on!");
                }
            } else {
                if (predictableScheduleSectionToggles.get(3).findElement(By.cssSelector("input")).getAttribute("class").contains("ng-not-empty")){
                    click(predictableScheduleSectionToggles.get(3).findElement(By.cssSelector("span")));
                    SimpleUtils.pass("GFE toggle is turned off !");
                } else {
                    SimpleUtils.report("GFE toggle is already off!");
                }
            }
        } else {
            SimpleUtils.fail("There is no predictable schedule settings!", false);
        }
    }

    @Override
    public void turnVSLToggleOnOrOff(boolean isTurnOn) throws Exception {
        if (areListElementVisible(predictableScheduleSectionToggles,5) && predictableScheduleSectionToggles.size()>1){
            if (isTurnOn){
                if (predictableScheduleSectionToggles.get(1).findElement(By.cssSelector("input")).getAttribute("class").contains("ng-empty")){
                    click(predictableScheduleSectionToggles.get(1).findElement(By.cssSelector("span")));
                    SimpleUtils.pass("GFE toggle is turned on!");
                } else {
                    SimpleUtils.report("GFE toggle is already on!");
                }
            } else {
                if (predictableScheduleSectionToggles.get(1).findElement(By.cssSelector("input")).getAttribute("class").contains("ng-not-empty")){
                    click(predictableScheduleSectionToggles.get(1).findElement(By.cssSelector("span")));
                    SimpleUtils.pass("GFE toggle is turned off !");
                } else {
                    SimpleUtils.report("GFE toggle is already off!");
                }
            }
        } else {
            SimpleUtils.fail("There is no predictable schedule settings!", false);
        }
    }
}
