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
        Todays_Forcast("today's forcast"),
        Schedules("schedules");
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
    public void verifyUIOfHelpfulLinkswidgetAsStoreManager(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
        // Verifiy Edit mode Dashboard loaded
        liquidDashboardPage.enterEditMode();
        //verify switch off helpful links widget
        liquidDashboardPage.switchOffWidget(widgetType.Helpful_Links.getValue());
        //verify switch on helpful links widget
        liquidDashboardPage.switchOnWidget(widgetType.Helpful_Links.getValue());
        //verify close helpful links widget
        liquidDashboardPage.closeWidget(widgetType.Helpful_Links.getValue());
        //verify if there is update time info icon
        liquidDashboardPage.switchOnWidget(widgetType.Helpful_Links.getValue());
        liquidDashboardPage.saveAndExitEditMode();
        liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Helpful_Links.getValue());
    }
}
