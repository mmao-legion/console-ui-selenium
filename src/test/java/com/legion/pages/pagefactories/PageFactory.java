package com.legion.pages.pagefactories;
import com.legion.pages.AnalyticsPage;
import com.legion.pages.ControlsPage;
import com.legion.pages.LoginPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.ScheduleProjectedSalesPage;
import com.legion.pages.TeamPage;
import com.legion.pages.UserAuthorizationPage;
import com.legion.pages.DashboardPage;

import org.openqa.selenium.WebDriver;

/**
 * Yanming
 */
public interface PageFactory {
    LoginPage createConsoleLoginPage();

    DashboardPage createConsoleDashboardPage();
    
    /* Aug 03- Zorang Added Below code */
    TeamPage createConsoleTeamPage();
    
    SchedulePage createConsoleSchedulePage();
    
    UserAuthorizationPage createConsoleUserAuthorizationPage();
    
    AnalyticsPage createConsoleAnalyticsPage();
    
    ControlsPage createConsoleControlsPage();
    
    ScheduleProjectedSalesPage createScheduleProjectedSalesPage();
   
}