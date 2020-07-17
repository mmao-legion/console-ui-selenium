package com.legion.tests.core;

import com.legion.pages.DashboardPage;
import com.legion.pages.LiquidDashboardPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class LiquidDashboardTest extends TestBase {
    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{
        this.createDriver((String)params[0],"83","Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
    }

    public enum widgetType{
        Helpful_Links("helpful links"),
        Todays_Forecast("today’s forecast"),
        Schedules("schedules"),
        Timesheet_Approval_Status("timesheet approval status"),
        Timesheet_Approval_Rate("timesheet approval rate"),
        Alerts("alerts"),
        Swaps_Covers("swaps & covers"),
        Starting_Soon("starting soon"),
        Open_Shifts("open shifts"),
        Compliance_Violation("compilance violation");
        private final String value;
        widgetType(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verify UI for Helpful Links widget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyCommonUIOfwidgetsAsStoreManager(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
        // Verifiy Edit mode Dashboard loaded
        liquidDashboardPage.enterEditMode();

        //verify switch off Todays_Forcast widget
        liquidDashboardPage.switchOffWidget(widgetType.Todays_Forecast.getValue());
        //verify switch on Todays_Forcast widget
        liquidDashboardPage.switchOnWidget(widgetType.Todays_Forecast.getValue());
        //verify close Todays_Forcast widget
        liquidDashboardPage.closeWidget(widgetType.Todays_Forecast.getValue());
        liquidDashboardPage.switchOnWidget(widgetType.Todays_Forecast.getValue());


        //verify switch off Timesheet_Approval_Status widget
        liquidDashboardPage.switchOffWidget(widgetType.Timesheet_Approval_Status.getValue());
        //verify switch on Timesheet_Approval_Status widget
        liquidDashboardPage.switchOnWidget(widgetType.Timesheet_Approval_Status.getValue());
        //verify close Timesheet_Approval_Status widget
        liquidDashboardPage.closeWidget(widgetType.Timesheet_Approval_Status.getValue());
        liquidDashboardPage.switchOnWidget(widgetType.Timesheet_Approval_Status.getValue());


        //verify switch off Timesheet_Approval_Rate widget
        liquidDashboardPage.switchOffWidget(widgetType.Timesheet_Approval_Rate.getValue());
        //verify switch on Timesheet_Approval_Rate widget
        liquidDashboardPage.switchOnWidget(widgetType.Timesheet_Approval_Rate.getValue());
        //verify close Timesheet_Approval_Rate widget
        liquidDashboardPage.closeWidget(widgetType.Timesheet_Approval_Rate.getValue());
        liquidDashboardPage.switchOnWidget(widgetType.Timesheet_Approval_Rate.getValue());


        //verify switch off Alerts widget
        liquidDashboardPage.switchOffWidget(widgetType.Alerts.getValue());
        //verify switch on Alerts widget
        liquidDashboardPage.switchOnWidget(widgetType.Alerts.getValue());
        //verify close Alerts widget
        liquidDashboardPage.closeWidget(widgetType.Alerts.getValue());
        liquidDashboardPage.switchOnWidget(widgetType.Alerts.getValue());


        //verify switch off Swaps_Covers widget
        liquidDashboardPage.switchOffWidget(widgetType.Swaps_Covers.getValue());
        //verify switch on Swaps_Covers widget
        liquidDashboardPage.switchOnWidget(widgetType.Swaps_Covers.getValue());
        //verify close Swaps_Covers widget
        liquidDashboardPage.closeWidget(widgetType.Swaps_Covers.getValue());
        liquidDashboardPage.switchOnWidget(widgetType.Swaps_Covers.getValue());


        //verify switch off Starting_Soon widget
        liquidDashboardPage.switchOffWidget(widgetType.Starting_Soon.getValue());
        //verify switch on Starting_Soon widget
        liquidDashboardPage.switchOnWidget(widgetType.Starting_Soon.getValue());
        //verify close Starting_Soon widget
        liquidDashboardPage.closeWidget(widgetType.Starting_Soon.getValue());
        liquidDashboardPage.switchOnWidget(widgetType.Starting_Soon.getValue());


        //verify switch off Schedules widget
        liquidDashboardPage.switchOffWidget(widgetType.Schedules.getValue());
        //verify switch on Schedules widget
        liquidDashboardPage.switchOnWidget(widgetType.Schedules.getValue());
        //verify close Schedules widget
        liquidDashboardPage.closeWidget(widgetType.Schedules.getValue());
        liquidDashboardPage.switchOnWidget(widgetType.Schedules.getValue());


        //verify switch off Open_Shifts widget
        liquidDashboardPage.switchOffWidget(widgetType.Open_Shifts.getValue());
        //verify switch on Open_Shifts widget
        liquidDashboardPage.switchOnWidget(widgetType.Open_Shifts.getValue());
        //verify close Open_Shifts widget
        liquidDashboardPage.closeWidget(widgetType.Open_Shifts.getValue());
        liquidDashboardPage.switchOnWidget(widgetType.Open_Shifts.getValue());


        //verify switch off compliance violation widget
        liquidDashboardPage.switchOffWidget(widgetType.Compliance_Violation.getValue());
        //verify switch on compliance violation widget
        liquidDashboardPage.switchOnWidget(widgetType.Compliance_Violation.getValue());
        //verify close compliance violation widget
        liquidDashboardPage.closeWidget(widgetType.Compliance_Violation.getValue());
        liquidDashboardPage.switchOnWidget(widgetType.Compliance_Violation.getValue());

        //verify switch off helpful links widget
        liquidDashboardPage.switchOffWidget(widgetType.Helpful_Links.getValue());
        //verify switch on helpful links widget
        liquidDashboardPage.switchOnWidget(widgetType.Helpful_Links.getValue());
        //verify close helpful links widget
        liquidDashboardPage.closeWidget(widgetType.Helpful_Links.getValue());
        liquidDashboardPage.switchOnWidget(widgetType.Helpful_Links.getValue());
        //verify back button to get out of manage page
        liquidDashboardPage.verifyBackBtn();
        //verify if there is update time info icon
        liquidDashboardPage.saveAndExitEditMode();
        liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Compliance_Violation.getValue());
        liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Open_Shifts.getValue());
        liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Schedules.getValue());
        liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Starting_Soon.getValue());
        liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Swaps_Covers.getValue());
        liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Alerts.getValue());
        liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Timesheet_Approval_Rate.getValue());
        liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Timesheet_Approval_Status.getValue());
        liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Helpful_Links.getValue());
        liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Todays_Forecast.getValue());
        //verify search input
        liquidDashboardPage.enterEditMode();
        liquidDashboardPage.verifySearchInput(widgetType.Helpful_Links.getValue());
    }

}
