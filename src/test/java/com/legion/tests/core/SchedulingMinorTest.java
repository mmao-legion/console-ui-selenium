package com.legion.tests.core;

import com.legion.pages.DashboardPage;
import com.legion.pages.LocationSelectorPage;
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
import org.openqa.selenium.WebElement;
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
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict("Demo District");
        locationSelectorPage.changeLocation("Santana Row");
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

        schedulePage.navigateToNextWeek();
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

        //set shift time as 10:00 AM - 1:00 PM
        schedulePage.moveSliderAtCertainPoint("1", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.moveSliderAtCertainPoint("10", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());

        schedulePage.selectWorkRole("Lift Maintenance");
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.searchText(firstNameOfTM1 + " " + lastNameOfTM.substring(0,1));
        SimpleUtils.assertOnFail("There should no minor warning message display when shift is not avoid the minor setting! ",
                !schedulePage.getTheMessageOfTMScheduledStatus().contains("Minor"), false);
        schedulePage.clickOnRadioButtonOfSearchedTeamMemberByName(firstNameOfTM1);
        if(schedulePage.ifWarningModeDisplay()){
            String warningMessage = schedulePage.getWarningMessageInDragShiftWarningMode();
            if (!warningMessage.contains("Minor")){
                SimpleUtils.pass("There is no minor warning message display when shift is not avoid the minor setting! ");
            } else
                SimpleUtils.fail("There should no minor warning message display when shift is not avoid the minor setting! ", false);
            schedulePage.clickOnAssignAnywayButton();
        } else
            SimpleUtils.pass("There is no minor warning message display when shift is not avoid the minor setting! ");

        schedulePage.clickOnOfferOrAssignBtn();
        schedulePage.saveSchedule();

        //check the violation in i icon popup of new create shift
        WebElement newAddedShift = schedulePage.getTheShiftByIndex(schedulePage.getAddedShiftIndexes(firstNameOfTM1).get(0));
        String test = schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).toString();
        if (newAddedShift != null) {
            SimpleUtils.assertOnFail("There should no minor warning message display when shift is not avoid the minor setting! ",
                    !schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Minor"), false);
        } else
            SimpleUtils.fail("Get new added shift failed! ", false);

        //Create new shift for TM2
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.customizeNewShiftPage();

        //set shift time as 10:00 AM - 1:00 PM
        schedulePage.moveSliderAtCertainPoint("1", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.moveSliderAtCertainPoint("10", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());

        schedulePage.selectWorkRole("Lift Maintenance");
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.searchText(firstNameOfTM2 + " " + lastNameOfTM.substring(0,1));

        //check the violation message in Status column
        SimpleUtils.assertOnFail("There should no minor warning message display when shift is not avoid the minor setting! ",
                !schedulePage.getTheMessageOfTMScheduledStatus().contains("Minor"), false);

        //check the message in warning mode
        schedulePage.clickOnRadioButtonOfSearchedTeamMemberByName(firstNameOfTM2);
        if(schedulePage.ifWarningModeDisplay()){
            String warningMessage = schedulePage.getWarningMessageInDragShiftWarningMode();
            if (!warningMessage.contains("Minor")){
                SimpleUtils.pass("There is no minor warning message display when shift is not avoid the minor setting! ");
            } else
                SimpleUtils.fail("There should no minor warning message display when shift is not avoid the minor setting! ", false);
            schedulePage.clickOnAssignAnywayButton();
        } else
            SimpleUtils.pass("There is no minor warning message display when shift is not avoid the minor setting! ");

        schedulePage.clickOnOfferOrAssignBtn();
        schedulePage.saveSchedule();

        //check the violation in i icon popup of new create shift
        newAddedShift = schedulePage.getTheShiftByIndex(schedulePage.getAddedShiftIndexes(firstNameOfTM2).get(0));
        if (newAddedShift != null) {
            SimpleUtils.assertOnFail("There should no minor warning message display when shift is not avoid the minor setting! ",
                    !schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Minor"), false);
        } else
            SimpleUtils.fail("Get new added shift failed! ", false);
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the warning message and violation when minor's shift is not during the setting time")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheWarningMessageAndViolationWhenMinorShiftIsNotDuringTheSettingTimeAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict("Demo District");
        locationSelectorPage.changeLocation("Santana Row");
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

        schedulePage.navigateToNextWeek();
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

        //set shift time as 9:00 AM - 2:00 PM
        schedulePage.moveSliderAtCertainPoint("2", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.moveSliderAtCertainPoint("9", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());

        schedulePage.selectWorkRole("Lift Maintenance");
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.searchText(firstNameOfTM1 + " " + lastNameOfTM.substring(0,1));

        //check the message in warning mode
//        schedulePage.clickOnRadioButtonOfSearchedTeamMemberByName(firstNameOfTM1);
        if(schedulePage.ifWarningModeDisplay()){
            String warningMessage1 = "As a minor, "+firstNameOfTM1+" should be scheduled from 9:30AM - 7:30PM";
            String warningMessage2 = "Please confirm that you want to make this change.";
            String test = schedulePage.getWarningMessageInDragShiftWarningMode();
            if (schedulePage.getWarningMessageInDragShiftWarningMode().contains(warningMessage1)
                    && schedulePage.getWarningMessageInDragShiftWarningMode().contains(warningMessage2)){
                SimpleUtils.pass("The message in warning mode display correctly! ");
            } else
                SimpleUtils.fail("The message in warning mode display incorrectly! ", false);
            schedulePage.clickOnAssignAnywayButton();
        } else
            SimpleUtils.fail("There should have warning mode display with minor warning message! ",false);


        //check the violation message in Status column
        SimpleUtils.assertOnFail("There should have minor warning message display as: Minor hrs 9:30AM - 7:30PM! ",
                schedulePage.getTheMessageOfTMScheduledStatus().contains("Minor hrs 9:30AM - 7:30PM"), false);

        schedulePage.clickOnOfferOrAssignBtn();
        schedulePage.saveSchedule();

        //check the violation in i icon popup of new create shift
        WebElement newAddedShift = schedulePage.getTheShiftByIndex(schedulePage.getAddedShiftIndexes(firstNameOfTM1).get(0));
        String test = schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).toString();
        if (newAddedShift != null) {
            SimpleUtils.assertOnFail("The minor violation message display incorrectly in i icon popup! ",
                    schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Minor hrs 9:30AM - 7:30PM"), false);
        } else
            SimpleUtils.fail("Get new added shift failed! ", false);

        //Create new shift for TM2
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.clickOnDayViewAddNewShiftButton();
        schedulePage.customizeNewShiftPage();

        //set shift time as 9:00 AM - 2:00 PM
        schedulePage.moveSliderAtCertainPoint("2", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
        schedulePage.moveSliderAtCertainPoint("9", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());

        schedulePage.selectWorkRole("Lift Maintenance");
        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
        schedulePage.clickOnCreateOrNextBtn();
        schedulePage.searchText(firstNameOfTM2 + " " + lastNameOfTM.substring(0,1));

        //check the message in warning mode
//        schedulePage.clickOnRadioButtonOfSearchedTeamMemberByName(firstNameOfTM2);
        if(schedulePage.ifWarningModeDisplay()){
            String warningMessage1 = "As a minor, "+firstNameOfTM2+" should be scheduled from 10AM - 7PM";
            String warningMessage2 = "Please confirm that you want to make this change.";
            if (schedulePage.getWarningMessageInDragShiftWarningMode().contains(warningMessage1)
                    && schedulePage.getWarningMessageInDragShiftWarningMode().contains(warningMessage2)){
                SimpleUtils.pass("The message in warning mode display correctly! ");
            } else
                SimpleUtils.fail("The message in warning mode display incorrectly! ", false);
            schedulePage.clickOnAssignAnywayButton();
        } else
            SimpleUtils.fail("There should have warning mode display with minor warning message! ",false);

        //check the violation message in Status column
        SimpleUtils.assertOnFail("There should have minor warning message display as: Minor hrs 10AM - 7PM! ",
                schedulePage.getTheMessageOfTMScheduledStatus().contains("Minor hrs 10AM - 7PM"), false);

        schedulePage.clickOnOfferOrAssignBtn();
        schedulePage.saveSchedule();

        //check the violation in i icon popup of new create shift
        newAddedShift = schedulePage.getTheShiftByIndex(schedulePage.getAddedShiftIndexes(firstNameOfTM2).get(0));
        if (newAddedShift != null) {
            SimpleUtils.assertOnFail("The minor violation message display incorrectly in i icon popup! ",
                    schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Minor hrs 10AM - 7PM"), false);
        } else
            SimpleUtils.fail("Get new added shift failed", false);
    }


}
