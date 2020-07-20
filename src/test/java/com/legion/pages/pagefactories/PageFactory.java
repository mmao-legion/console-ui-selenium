package com.legion.pages.pagefactories;
import com.legion.pages.*;

/**
 * Yanming
 */
public interface PageFactory {
    LoginPage createConsoleLoginPage();

    DashboardPage createConsoleDashboardPage();
    
    /* 
     * Aug 03- Zorang Added Below code 
     * */
    TeamPage createConsoleTeamPage();
    
    SchedulePage createConsoleSchedulePage();
    
    UserAuthorizationPage createConsoleUserAuthorizationPage();
    
    AnalyticsPage createConsoleAnalyticsPage();
    
    ControlsPage createConsoleControlsPage();
    
    SalesForecastPage createSalesForecastPage();
    
    StaffingGuidancePage createStaffingGuidancePage();
    
    LocationSelectorPage createLocationSelectorPage();
    
    ScheduleOverviewPage createScheduleOverviewPage();
    
    SchedulePage createConsoleScheduleNewUIPage();
    
    TrafficForecastPage createTrafficForecastPage();
    
    TimeSheetPage createTimeSheetPage(); 
    
    ControlsNewUIPage createControlsNewUIPage();

	ProjectedSalesPage createProjectedSalesPage(); 
	
	ProfileNewUIPage createProfileNewUIPage();

    ForecastPage createForecastPage();

    GmailPage createConsoleGmailPage();

    ActivityPage createConsoleActivityPage();

    LocationsPage createOpsPortalLocationsPage();

    LiquidDashboardPage createConsoleLiquidDashboardPage();
}