package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.OpsPortaPageFactories.ConfigurationPage;
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
import java.util.Arrays;
import java.util.List;

public class MasterTemplatePCLGTest extends TestBase {
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
    private SmartTemplatePage smartTemplatePage;
    private SmartCardPage smartCardPage;
    private ProfileNewUIPage profileNewUIPage;
    private LoginPage loginPage;
    private ConfigurationPage configurationPage;
    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) {
        try {

            this.createDriver((String) params[0], "69", "Window");
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
            smartTemplatePage = pageFactory.createSmartTemplatePage();
            smartCardPage = pageFactory.createSmartCardPage();
            profileNewUIPage = pageFactory.createProfileNewUIPage();
            loginPage = pageFactory.createConsoleLoginPage();
            configurationPage = pageFactory.createOpsPortalConfigurationPage();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Ashutosh")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "validate new added shifts can show in master template and schedule for PCLG schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyNewAddedShiftsForPCLGCanShowInMasterTemplateAndScheduleAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            profileNewUIPage.clickOnUserProfileImage();
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage("My Profile");
            String tmFullName = profileNewUIPage.getUserProfileName().get("fullName");
            String firstName = tmFullName.split(" ")[0];
            loginPage.logOut();
            loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
            goToSchedulePageScheduleTab();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            String workRole = scheduleMainPage.getStaffWorkRoles().get(scheduleMainPage.getStaffWorkRoles().size()-1);
            //Go to master template
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();
            //Check the shifts in master template can load
            SimpleUtils.assertOnFail("Shifts failed to load in master template! ",
                    scheduleShiftTablePage.getShiftsCount()>=0, false);

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView(firstName);
            scheduleMainPage.saveSchedule();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //Create new shift for one employee
            String shiftStartTime= "10:00am";
            String shiftEndTime = "5:00pm";
            List<String> childLocationNames = scheduleMainPage.getSpecificFilterNames("location");
            createShiftsWithSpecificValues(workRole, "", childLocationNames.get(0), shiftStartTime, shiftEndTime,
                    1, Arrays.asList(0,1), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
            createShiftsWithSpecificValues(workRole, "", childLocationNames.get(1), shiftStartTime, shiftEndTime,
                    1, Arrays.asList(2,3), ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "", tmFullName);
            scheduleMainPage.saveSchedule();

            //Verify the new created shifts saved in master template successfully
            SimpleUtils.assertOnFail("The new created shift failed display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(firstName)==4, false );

            //Verify the new created shift can show in previous week
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            scheduleCommonPage.navigateToPreviousWeek();
            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();
            SimpleUtils.assertOnFail("The new created shift failed to display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(firstName)==4, false );
            //Verify the new created shift can show in future week
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            smartCardPage.clickViewTemplateLinkOnMasterTemplateSmartCard();
            SimpleUtils.assertOnFail("The new created shift failed to display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(firstName)==4, false );


            //Verify the new created shift can show in schedule
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            String sheduleStartTime = "06:00AM";
            String scheduleEndTime = "11:00PM";
            createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( sheduleStartTime, scheduleEndTime);
            SimpleUtils.assertOnFail("The new created shift failed to display in master template! ",
                    scheduleShiftTablePage.getShiftsNumberByName(firstName)==4, false );

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
