package com.legion.pages.pagefactories;

import com.legion.pages.core.ConsoleAnalyticsPage;
import com.legion.pages.core.ConsoleControlsPage;
import com.legion.pages.core.ConsoleLoginPage;
import com.legion.pages.core.ConsoleSchedulePage;
import com.legion.pages.core.ConsoleStaffingGuidancePage;
import com.legion.pages.core.ConsoleSalesForecastPage;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
import com.legion.pages.core.ConsoleScheduleOverviewPage;
import com.legion.pages.core.ConsoleTeamPage;
import com.legion.pages.core.ConsoleTeamPage_Gunjan;
import com.legion.pages.core.ConsoleUserAuthorizationPage;
import com.legion.pages.core.ConsoleDashboardPage;
import com.legion.pages.core.ConsoleLocationSelectorPage;
import com.legion.pages.AnalyticsPage;
import com.legion.pages.ControlsPage;
import com.legion.pages.DashboardPage;
import com.legion.pages.LocationSelectorPage;
import com.legion.pages.LoginPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.StaffingGuidancePage;
import com.legion.pages.SalesForecastPage;
import com.legion.pages.ScheduleOverviewPage;
import com.legion.pages.TeamPage;
import com.legion.pages.TeamPage_Gunjan;
import com.legion.pages.UserAuthorizationPage;

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
		return new ConsoleUserAuthorizationPage();
	}
	
	@Override
	public SalesForecastPage createSalesForecastPage() {
		return new ConsoleSalesForecastPage();
	}

	@Override
	public StaffingGuidancePage createStaffingGuidancePage() {
		return new ConsoleStaffingGuidancePage();
	}

	@Override
	public LocationSelectorPage createLocationSelectorPage() {
		return new ConsoleLocationSelectorPage();
	}

	@Override
	public ScheduleOverviewPage createScheduleOverviewPage() {
		return new ConsoleScheduleOverviewPage();
	}

	@Override
	public SchedulePage createConsoleScheduleNewUIPage() {
		return new ConsoleScheduleNewUIPage();
	}

	@Override
	public TeamPage_Gunjan createConsoleTeamPage_Gunjan() {
		// TODO Auto-generated method stub
		return new ConsoleTeamPage_Gunjan();
	}
}
