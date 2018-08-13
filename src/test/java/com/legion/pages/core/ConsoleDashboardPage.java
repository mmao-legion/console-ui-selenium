package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.DashboardPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

public class ConsoleDashboardPage extends BasePage implements DashboardPage {


    //By goToTodayButton = By.cssSelector("#submit");
	By goToTodayButton = By.cssSelector("[ng-click='openSchedule()']");

    public ConsoleDashboardPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void goToToday() throws Exception {
    	Reporter.log("Dashboard Page Loaded Successfully!");
    	waitForElement(goToTodayButton);
        click(goToTodayButton);
    }

    @Override
    public boolean isToday() throws Exception {
        //todo add validation on date string
    	By goToTodayScheduleView = By.cssSelector("[ng-class = 'subNavigationViewLinkActiveClass(view)']");
    	waitForElement(goToTodayScheduleView);
    	Reporter.log("Today's schedule Section Loaded Successfully!");
        return true;
    }

}
