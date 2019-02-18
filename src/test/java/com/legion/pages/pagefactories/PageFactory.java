package com.legion.pages.pagefactories;
import com.legion.pages.AnalyticsPage;
import com.legion.pages.ControlsNewUIPage;
import com.legion.pages.ControlsPage;
import com.legion.pages.LoginPage;
import com.legion.pages.ProjectedSalesPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.StaffingGuidancePage;
import com.legion.pages.SalesForecastPage;
import com.legion.pages.ScheduleOverviewPage;
import com.legion.pages.TeamPage;
import com.legion.pages.TimeSheetPage;
import com.legion.pages.TrafficForecastPage;
import com.legion.pages.UserAuthorizationPage;
import com.legion.pages.DashboardPage;
import com.legion.pages.LocationSelectorPage;
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
   
}