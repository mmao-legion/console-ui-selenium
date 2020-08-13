package com.legion.tests.core;

import com.legion.pages.ControlsNewUIPage;
import com.legion.pages.ControlsPage;
import com.legion.pages.DashboardPage;
import com.legion.pages.InboxPage;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;

public class InboxTest extends TestBase {

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{
        this.createDriver((String)params[0],"83","Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
    }

    //Added by Nora
    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "verify reports will be available for export")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyGFEReportsAreAbleToExportAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
    }

    //Added by Julie
    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Verify the content of operating hours and the first day of week are correct")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheContentOfOperationHoursAndTheFirstDayOfWeekAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        InboxPage inboxPage = pageFactory.createConsoleInboxPage();

        // Make sure that GFE is turned on
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
        ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
        controlsPage.gotoControlsPage();
        SimpleUtils.assertOnFail("Controls Page not loaded Successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
        boolean isControlsComplianceCardSection = controlsNewUIPage.isControlsComplianceCard();
        SimpleUtils.assertOnFail("Controls Page: Compliance Section not Loaded.", isControlsComplianceCardSection, false);
        controlsNewUIPage.clickOnControlsComplianceSection();
        boolean isTurnOn = true;
        controlsNewUIPage.turnGFEToggleOnOrOff(isTurnOn);

        // Get Regular hours from Controls-> Working hours -> Regular
        String workingHoursType = "Regular";
        controlsPage.gotoControlsPage();
        SimpleUtils.assertOnFail("Controls page not loaded Successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
        controlsNewUIPage.clickOnControlsWorkingHoursCard();
        SimpleUtils.assertOnFail("Working Hours Card not loaded Successfully!", controlsNewUIPage.isControlsWorkingHoursLoaded(), false);
        controlsNewUIPage.clickOnWorkHoursTypeByText(workingHoursType);
        LinkedHashMap<String, List<String>> regularHours = controlsNewUIPage.getRegularWorkingHours();

        // Create a GFE announcement to verify its content of operation hours and the first day of week
        inboxPage.clickOnInboxConsoleMenuItem();
        inboxPage.createGFEAnnouncement();
    }

    //Added by Mary

    //Added by Haya
}
