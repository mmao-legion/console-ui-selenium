package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.core.OpsPortalLocationsPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;

import static com.legion.utils.MyThreadLocal.getTimeOffEndTime;
import static com.legion.utils.MyThreadLocal.getTimeOffStartTime;

public class LocationsTest extends TestBase {


    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{
        this.createDriver((String)params[0],"83","Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
    }



    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the locations tab show well")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyLocationsTabShowWell(String browser, String username, String password, String location) throws Exception {

        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
//        locationsPage.clickOpsPortalIconInDashboardPage();
//        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

        //go to locations tab
        locationsPage.clickOnLocationsTab();
        //check locations item
        locationsPage.validateItemsInLocations();
        //go to sub-locations tab
        locationsPage.goToSubLocationsInLocationsPage();
        //add new regular location
        locationsPage.addNewRegularLocationWithMandatoryFields();
//        locationsPage.searchNewLocation();

    }
}
