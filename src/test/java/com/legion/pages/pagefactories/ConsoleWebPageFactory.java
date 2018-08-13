package com.legion.pages.pagefactories;

import com.legion.pages.core.ConsoleLoginPage;
import com.legion.pages.core.ConsoleSchedulePage;
import com.legion.pages.core.ConsoleTeamPage;
import com.legion.pages.core.ConsoleDashboardPage;
import com.legion.pages.DashboardPage;
import com.legion.pages.LoginPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.TeamPage;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

/**
 * Yanming
 */
public class ConsoleWebPageFactory implements PageFactory {
    @Override
    public LoginPage createConsoleLoginPage(WebDriver driver) {
        return new ConsoleLoginPage(driver);
    }

    @Override
    public DashboardPage createConsoleDashboardPage(WebDriver driver) {
    	Reporter.log("Logged-in Successfully!");
        return new ConsoleDashboardPage(driver);
    }
    
    /* Aug 03- Zorang Added Below code */
    @Override
    public TeamPage createConsoleTeamPage(WebDriver driver) {
    	return new ConsoleTeamPage(driver);
    }
    
    @Override
    public SchedulePage createConsoleSchedulePage(WebDriver driver) {
    	return new ConsoleSchedulePage(driver);
    }
}
