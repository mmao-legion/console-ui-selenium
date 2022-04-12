package com.legion.pages.core.OpCommons;

import com.legion.pages.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleNavigationPage extends BasePage {
    public ConsoleNavigationPage() {
        PageFactory.initElements(getDriver(), this);
    }
    // Added by Sophia

    //LocationBar
    @FindBy(css = "div#id_upperfield-search")
    private WebElement upperFieldSearch;
    @FindBy(css = "lg-upperfield-search lg-search>input-field input")
    private WebElement searchBox;
    @FindBy(css = "lg-upperfield-search div.lg-search-icon.ng-scope")
    private WebElement searchIcon;
    @FindBy(css = "lg-search-options[search-hint='Search'] div.lg-search-options__scroller div.lg-search-options__option-wrapper")
    private WebElement searchResult;

    //Console navigation panel
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(1)")
    private WebElement dashBoard;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(2)")
    private WebElement team;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(3)")
    private WebElement schedule;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(4)")
    private WebElement timeSheet;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(5)")
    private WebElement compliance;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(6)")
    private WebElement report;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(7)")
    private WebElement inbox;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(8)")
    private WebElement news;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(9)")
    private WebElement controls;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(10)")
    private WebElement logout;

    public void searchLocation(String locationName) {
        upperFieldSearch.click();
        searchBox.clear();
        searchBox.sendKeys(locationName);
        searchIcon.click();
        waitForSeconds(3);
        if (isElementDisplayed(searchResult)) {
            searchResult.click();
            waitForSeconds(5);
        } else {
            System.out.println("There is no location: " + locationName);
        }
    }

    public void navigateTo(String module) {
        WebElement element = null;
        if (module.equalsIgnoreCase("Dashboard")) {
            element = dashBoard;
        } else if (module.equalsIgnoreCase("Team")) {
            element = team;
        } else if (module.equalsIgnoreCase("Schedule")) {
            element = schedule;
        } else if (module.equalsIgnoreCase("Timesheet")) {
            element = timeSheet;
        } else if (module.equalsIgnoreCase("Compliance")) {
            element = compliance;
        } else if (module.equalsIgnoreCase("Report")) {
            element = report;
        } else if (module.equalsIgnoreCase("Inbox")) {
            element = inbox;
        } else if (module.equalsIgnoreCase("News")) {
            element = news;
        } else if (module.equalsIgnoreCase("Controls")) {
            element = controls;
        } else if (module.equalsIgnoreCase("Logout")) {
            element = logout;
        } else {
            System.out.println("No such Module!");
        }
        element.click();
        waitForSeconds(5);
    }

}
