package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.OpsPortaPageFactories.LocationsPage;
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

public class AdvanceStaffingRuleTest  extends TestBase {
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
    private LoginPage loginPage;
    private LocationsPage locationsPage;
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
            loginPage = pageFactory.createConsoleLoginPage();
            locationsPage = pageFactory.createOpsPortalLocationsPage();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify advance staffing rule shifts can show correctly in schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAdvanceStaffingRuleShiftsCanShowCorrectlyInScheduleAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {

            String shiftTime1 = "11:15 am-3:30 pm";
            String shiftTime2 = "8:00 am-4:00 pm";
            String workRoleName1 = "GENERAL MANAGER";
            String workRoleName2 = "TEAM MEMBER CORPORATE-THEATRE";
            String mealBreakTime = "12:15 pm - 12:45 pm";
            String restBreakTime = "1:15 pm - 1:35 pm";
            HashMap<String, String> mealRestBreaks = new HashMap<String, String>();
            List<String> indexes = new ArrayList<String>();

            if (location.equalsIgnoreCase("2707484 Spring Hill")){
                workRoleName1 = "CSR";
                workRoleName2 = "Assistant Manager";
            }
            //go to schedule function
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            // Navigate to a week
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            // create the schedule if not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange("06:00AM", "06:00PM");
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectWorkRoleFilterByText(workRoleName1, true);
            indexes = scheduleShiftTablePage.getIndexOfDaysHaveShifts();
            for (String index : indexes) {
                scheduleShiftTablePage.verifyShiftTimeInReadMode(index, shiftTime1);
            }

            //Click on edit button on week view

            String mealBreakTimeOnIIcon = scheduleShiftTablePage.getTheShiftInfoByIndex(0).get(11);
            String restBreakTimeOnIIcon = scheduleShiftTablePage.getTheShiftInfoByIndex(0).get(12);

            if (mealBreakTimeOnIIcon.compareToIgnoreCase(mealBreakTime) == 0) {
                SimpleUtils.pass("The Meal Break info is correct");
            } else
                SimpleUtils.fail("The Meal Break info is incorrect", false);
            if (restBreakTimeOnIIcon.compareToIgnoreCase(restBreakTime) == 0) {
                SimpleUtils.pass("The Rest Break info is correct");
            } else
                SimpleUtils.fail("The Rest Break info is correct", false);


            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectWorkRoleFilterByText(workRoleName2, true);
            indexes = scheduleShiftTablePage.getIndexOfDaysHaveShifts();
            for (String index : indexes) {
                scheduleShiftTablePage.verifyShiftTimeInReadMode(index, shiftTime2);
            }

            //Click on edit button on week view

            mealBreakTimeOnIIcon = scheduleShiftTablePage.getTheShiftInfoByIndex(0).get(11);
            restBreakTimeOnIIcon = scheduleShiftTablePage.getTheShiftInfoByIndex(0).get(12);

            if (mealBreakTimeOnIIcon.compareToIgnoreCase(mealBreakTime) == 0) {
                SimpleUtils.pass("The Meal Break info is correct");
            } else
                SimpleUtils.fail("The Meal Break info is incorrect", false);
            if (restBreakTimeOnIIcon.compareToIgnoreCase(restBreakTime) == 0) {
                SimpleUtils.pass("The Rest Break info is correct");
            } else
                SimpleUtils.fail("The Rest Break info is correct", false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
