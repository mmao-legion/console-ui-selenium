package com.legion.pages.pagefactories;
import com.legion.pages.LoginPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.TeamPage;
import com.legion.pages.DashboardPage;
import org.openqa.selenium.WebDriver;

/**
 * Yanming
 */
public interface PageFactory {
    LoginPage createConsoleLoginPage(WebDriver driver);

    DashboardPage createConsoleDashboardPage(WebDriver driver);
    
    /* Aug 03- Zorang Added Below code */
    TeamPage createConsoleTeamPage(WebDriver driver);
    
    SchedulePage createConsoleSchedulePage(WebDriver driver);
}