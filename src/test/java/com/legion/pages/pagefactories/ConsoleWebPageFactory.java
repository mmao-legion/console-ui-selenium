package com.legion.pages.pagefactories;

import com.legion.pages.core.ConsoleAnalyticsPage;
import com.legion.pages.core.ConsoleControlsPage;
import com.legion.pages.core.ConsoleLoginPage;
import com.legion.pages.core.ConsoleSchedulePage;
import com.legion.pages.core.ConsoleScheduleProjectedSalesPage;
import com.legion.pages.core.ConsoleTeamPage;
import com.legion.pages.core.ConsoleUserAuthorizationPage;
import com.legion.pages.core.ConsoleDashboardPage;
import com.legion.pages.AnalyticsPage;
import com.legion.pages.ControlsPage;
import com.legion.pages.DashboardPage;
import com.legion.pages.LoginPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.ScheduleProjectedSalesPage;
import com.legion.pages.TeamPage;
import com.legion.pages.UserAuthorizationPage;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

/**
 * Yanming
 */
public class ConsoleWebPageFactory implements PageFactory {
    @Override
    public LoginPage createConsoleLoginPage() {
    	return new ConsoleLoginPage();
    }

    @Override
    public DashboardPage createConsoleDashboardPage() {
    	Reporter.log("Logged-in Successfully!");
        return new ConsoleDashboardPage();
    }
    
    /* Aug 03- Zorang Added Below code */
    @Override
    public TeamPage createConsoleTeamPage() {
    	return new ConsoleTeamPage();
    }
    
    @Override
    public SchedulePage createConsoleSchedulePage() {
    	return new ConsoleSchedulePage();
    }
    
    @Override
    public AnalyticsPage createConsoleAnalyticsPage() {
    	return new ConsoleAnalyticsPage();
    }
    
    @Override  
    public ControlsPage createConsoleControlsPage() {
    	return new ConsoleControlsPage();
    }

	@Override
	public UserAuthorizationPage createConsoleUserAuthorizationPage() {
		// TODO Auto-generated method stub
		return new ConsoleUserAuthorizationPage();
	}
	
	@Override
	public ScheduleProjectedSalesPage createScheduleProjectedSalesPage() {
		return new ConsoleScheduleProjectedSalesPage();
	}
}
