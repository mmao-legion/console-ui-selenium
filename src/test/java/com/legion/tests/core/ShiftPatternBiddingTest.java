package com.legion.tests.core;

import com.legion.api.ShiftPatternBidding.AutoAssignmentTaskAPI;
import com.legion.pages.*;
import com.legion.pages.OpsPortaPageFactories.ConfigurationPage;
import com.legion.pages.OpsPortaPageFactories.LocationsPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.core.OpsPortal.LocationsTest;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;
import org.junit.Rule;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class ShiftPatternBiddingTest extends TestBase {
    private DashboardPage dashboardPage;
    private CreateSchedulePage createSchedulePage;
    private ScheduleMainPage scheduleMainPage;
    private ScheduleShiftTablePage scheduleShiftTablePage;
    private ScheduleCommonPage scheduleCommonPage;
    private EditShiftPage editShiftPage;
    private NewShiftPage newShiftPage;
    private ShiftOperatePage shiftOperatePage;
    private ControlsPage controlsPage;
    private ControlsNewUIPage controlsNewUIPage;
    private MySchedulePage mySchedulePage;
    private BasePage basePage;
    private SmartCardPage smartCardPage;
    private NewShiftPatternBiddingPage newShiftPatternBiddingPage;
    private LocationsPage locationsPage;
    private ConfigurationPage configurationPage;
    private LoginPage loginPage;
    private BidShiftPatternBiddingPage bidShiftPatternBiddingPage;
    private ProfileNewUIPage profileNewUIPage;
    private RulePage rulePage;
    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{
        try {
            this.createDriver((String) params[0], "83", "Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
            dashboardPage = pageFactory.createConsoleDashboardPage();
            createSchedulePage = pageFactory.createCreateSchedulePage();
            scheduleMainPage = pageFactory.createScheduleMainPage();
            scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            scheduleCommonPage = pageFactory.createScheduleCommonPage();
            editShiftPage = pageFactory.createEditShiftPage();
            newShiftPage = pageFactory.createNewShiftPage();
            shiftOperatePage = pageFactory.createShiftOperatePage();
            controlsPage = pageFactory.createConsoleControlsPage();
            controlsNewUIPage = pageFactory.createControlsNewUIPage();
            mySchedulePage = pageFactory.createMySchedulePage();
            basePage = new BasePage();
            smartCardPage = pageFactory.createSmartCardPage();
            newShiftPatternBiddingPage = pageFactory.createNewShiftPatternBiddingPage();
            locationsPage = pageFactory.createOpsPortalLocationsPage();
            configurationPage = pageFactory.createOpsPortalConfigurationPage();
            loginPage = pageFactory.createConsoleLoginPage();
            bidShiftPatternBiddingPage = pageFactory.createBidShiftPatternBiddingPage();
            profileNewUIPage = pageFactory.createProfileNewUIPage();
            rulePage = pageFactory.createRulePage();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the end to end flow of shift bidding")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheEndToEndFlowOfShiftBiddingAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            profileNewUIPage.clickOnUserProfileImage();
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage("My Profile");
            String tmFullName = profileNewUIPage.getUserProfileName().get("fullName");
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());

            goToSchedulePageScheduleTab();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            //Remove all assignment on schedule rule page
            rulePage.clickRuleButton();
            rulePage.removeAllShiftPatternsAssignmentsOnScheduleRulePage();
            SimpleUtils.assertOnFail("Fail to delete all assignments on rule page! ",
                    !rulePage.checkIfThereAreAssignmentOnRulePage(), false);
            //Go to shift pattern bidding template and create new shift bidding
            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.searchLocation(location);
            SimpleUtils.assertOnFail("Locations not searched out Successfully!",  locationsPage.verifyUpdateLocationResult(location), false);
            locationsPage.clickOnLocationInLocationResult(location);
            locationsPage.clickOnConfigurationTabOfLocation();
            HashMap<String, String> templateTypeAndName = locationsPage.getTemplateTypeAndNameFromLocation();

            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad("Shift Pattern Bidding");
            configurationPage.clickOnSpecifyTemplateName(templateTypeAndName.get("Shift Pattern Bidding"), "edit");
//            configurationPage.clickOnSpecifyTemplateName("SeaspanRegularLocation2", "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();

            //Cancel or delete all current shift biddings
            if (newShiftPatternBiddingPage.getCurrentShiftBiddingsCount()>0){
                newShiftPatternBiddingPage.removeOrCancelAllCurrentShiftBidding();
                configurationPage.publishNowTheTemplate();
                configurationPage.clickOnSpecifyTemplateName("SeaspanRegularLocation2", "edit");
                configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            }

            //Verify new bidding page will display after click Add button on bidding list
            newShiftPatternBiddingPage.clickAddShiftBiddingButton();

            //Verify Shift bidding window start date/time and end date/time
            newShiftPatternBiddingPage.setShiftBiddingWindowStartAndEndDateAndTime();

            //Verify select Schedule start week
            newShiftPatternBiddingPage.selectTheFirstScheduleStartWeek();

            //Verify save the new shift bidding
            newShiftPatternBiddingPage.clickSaveButtonOnCreateShiftBiddingPage();
            //Verify publish the shift bidding template
            configurationPage.publishNowTemplate();
            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.Console.getValue());
            refreshCachesAfterChangeTemplate();
            //Verify employee can see the bidding after bidding started
            loginPage.logOut();
            Thread.sleep(60000);
            loginAsDifferentRole(AccessRoles.TeamMember.getValue());
            Thread.sleep(60000);
            dashboardPage.clickOnDashboardConsoleMenu();
            int i=0;
            while (!bidShiftPatternBiddingPage.checkIfTheShiftBiddingWidgetLoaded()&& i<10){
                Thread.sleep(30000);
                dashboardPage.clickOnDashboardConsoleMenu();
                i++;
            }
            SimpleUtils.assertOnFail("The shift bidding widget fail to load! ",
                    bidShiftPatternBiddingPage.checkIfTheShiftBiddingWidgetLoaded(), false);
            bidShiftPatternBiddingPage.clickSubmitBidButton();
            bidShiftPatternBiddingPage.clickNextButton();
            //Verify employee can select shift patterns
            bidShiftPatternBiddingPage.addAllShiftPatterns();
            bidShiftPatternBiddingPage.clickNextButton();
            bidShiftPatternBiddingPage.clickNextButton();
            //Verify employee can submit shift bidding
            bidShiftPatternBiddingPage.clickSubmitButton();
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            //Verify employee can be assign to the shift pattern after run auto assignment task API
            AutoAssignmentTaskAPI.runAutoAssignmentTaskAPI(getUserNameNPwdForCallingAPI().get(0),
                    getUserNameNPwdForCallingAPI().get(1));
            //Verify employee can be assign to the shifts after generated schedule
            goToSchedulePageScheduleTab();
            rulePage.clickRuleButton();

            //There is one assignment on rule page
            SimpleUtils.assertOnFail("Fail to delete all assignments on rule page! ",
                    rulePage.checkIfThereAreAssignmentOnRulePage(), false);

            //Check the employee display on rule page
            List<String> employeeNames = rulePage.getAllShiftPatternsAssignmentsOnScheduleRulePage();
            SimpleUtils.assertOnFail("The employee not display on rule page, the all assignment are:"+employeeNames
                            +", the employee name is:"+tmFullName,
                    employeeNames.contains(tmFullName), false);

            //Back to schedule page
            rulePage.clickBackButton();

            //Regenerate schedule and check the employee has shifts in the schedule
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            SimpleUtils.assertOnFail("The employee "+tmFullName+ " should display in schedule! ",
                    scheduleShiftTablePage.getAllShiftsOfOneTM(tmFullName.split(" ")[0]).size()>0, false);


        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
