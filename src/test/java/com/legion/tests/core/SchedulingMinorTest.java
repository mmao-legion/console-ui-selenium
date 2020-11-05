package com.legion.tests.core;

import com.legion.pages.DashboardPage;
import com.legion.pages.ProfileNewUIPage;
import com.legion.pages.SchedulePage;
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
import java.util.HashMap;

public class SchedulingMinorTest extends TestBase {

    private static HashMap<String, String> propertyCustomizeMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ScheduleCustomizeNewShift.json");

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception {
        this.createDriver((String) params[0], "69", "Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate there is no warning message and violation when minor's shift is not avoid the minor settings")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyNoWarningMessageAndViolationDisplayWhenMinorIsNotAvoidMinorSettingsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

        boolean isWeekGenerated = schedulePage.isWeekGenerated();
        if (isWeekGenerated){
            schedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        schedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( "08:00AM", "9:00PM");
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        String firstNameOfTM1 = "Minor14";
        String firstNameOfTM2 = "Minor16";
        String lastNameOfTM = "RC";
        schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);
        schedulePage.deleteTMShiftInWeekView(firstNameOfTM2);

        //Create new shift for TM1
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.customizeNewShiftPage();
        schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME_2"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount2.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME_2"), ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
        schedulePage.clearAllSelectedDays();
        schedulePage.selectDaysByIndex(1, 1, 1);
        schedulePage.selectWorkRole("Lift Maintenance");
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.searchText(firstNameOfTM1 + " " + lastNameOfTM.substring(0,1));
        schedulePage.getTheMessageOfTMScheduledStatus();
        schedulePage.clickOnRadioButtonOfSearchedTeamMemberByName(firstNameOfTM1);
        schedulePage.clickOnAssignAnywayButton();
        schedulePage.clickOnOfferOrAssignBtn();

    }
}
