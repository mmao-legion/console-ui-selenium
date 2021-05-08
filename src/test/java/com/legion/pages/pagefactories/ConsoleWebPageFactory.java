package com.legion.pages.pagefactories;

import com.legion.pages.*;
import com.legion.pages.core.*;
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

	@Override
	public ForecastPage createForecastPage() {
		return new ConsoleForecastPage();
	}

	@Override
	public GmailPage createConsoleGmailPage() { return new ConsoleGmailPage(); }

	@Override
	public ActivityPage createConsoleActivityPage() { return new ConsoleActivityPage(); }

	@Override
	public LocationsPage createOpsPortalLocationsPage() { return new OpsPortalLocationsPage();
	}

	@Override
	public LiquidDashboardPage createConsoleLiquidDashboardPage() { return new ConsoleLiquidDashboardPage(); }

	@Override
	public JobsPage createOpsPortalJobsPage() { return new OpsPortalJobsPage();}

	@Override
	public ScheduleDMViewPage createScheduleDMViewPage() { return new ConsoleScheduleDMViewPage(); }

	@Override
	public InboxPage createConsoleInboxPage() { return new ConsoleInboxPage(); }

	@Override
	public CinemarkMinorPage createConsoleCinemarkMinorPage() { return new ConsoleCinemarkMinorPage(); }

	@Override
	public AdminPage createConsoleAdminPage() { return new ConsoleAdminPage(); }

	@Override
	public ConfigurationPage createOpsPortalConfigurationPage() { return new OpsPortalConfigurationPage();}

	@Override
	public CompliancePage createConsoleCompliancePage() { return new ConsoleCompliancePage(); }

	@Override
	public ReportPage createConsoleReportPage() { return new ConsoleReportPage(); }
	@Override
	public InsightPage createConsoleInsightPage() { return new ConsoleInsightPage(); }
	@Override
	public NewsPage createConsoleNewsPage() { return new ConsoleNewsPage(); }

	@Override
	public IntegrationPage createConsoleIntegrationPage() { return new ConsoleIntegrationPage(); }
}
