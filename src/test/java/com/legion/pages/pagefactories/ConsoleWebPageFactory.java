package com.legion.pages.pagefactories;

import com.legion.pages.core.ConsoleAnalyticsPage;
import com.legion.pages.core.ConsoleControlsNewUIPage;
import com.legion.pages.core.ConsoleControlsPage;
import com.legion.pages.core.ConsoleLoginPage;
import com.legion.pages.core.ConsoleProfileNewUIPage;
import com.legion.pages.core.ConsoleProjectedSalesPage;
import com.legion.pages.core.ConsoleSchedulePage;
import com.legion.pages.core.ConsoleStaffingGuidancePage;
import com.legion.pages.core.ConsoleSalesForecastPage;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
import com.legion.pages.core.ConsoleScheduleOverviewPage;
import com.legion.pages.core.ConsoleTeamPage;
import com.legion.pages.core.ConsoleTimeSheetPage;
import com.legion.pages.core.ConsoleTrafficForecastPage;
import com.legion.pages.core.ConsoleUserAuthorizationPage;
import com.legion.pages.core.ConsoleDashboardPage;
import com.legion.pages.core.ConsoleLocationSelectorPage;
import com.legion.pages.AnalyticsPage;
import com.legion.pages.ControlsNewUIPage;
import com.legion.pages.ControlsPage;
import com.legion.pages.DashboardPage;
import com.legion.pages.LocationSelectorPage;
import com.legion.pages.LoginPage;
import com.legion.pages.ProfileNewUIPage;
import com.legion.pages.ProjectedSalesPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.StaffingGuidancePage;
import com.legion.pages.SalesForecastPage;
import com.legion.pages.ScheduleOverviewPage;
import com.legion.pages.TeamPage;
import com.legion.pages.TimeSheetPage;
import com.legion.pages.TrafficForecastPage;
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
	public TrafficForecastPage createTrafficForecastPage() {
		return new ConsoleTrafficForecastPage();
	}

	@Override
	public TimeSheetPage createTimeSheetPage() {
		return new ConsoleTimeSheetPage();
	}
	
	@Override
	public ControlsNewUIPage createControlsNewUIPage() {
		return new ConsoleControlsNewUIPage();
	}
	
	@Override
	public ProjectedSalesPage createProjectedSalesPage() {
		return new ConsoleProjectedSalesPage();
	}

	@Override
	public ProfileNewUIPage createProfileNewUIPage() {
		return new ConsoleProfileNewUIPage();
	}
}
