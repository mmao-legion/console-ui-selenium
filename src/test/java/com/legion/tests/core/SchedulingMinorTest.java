package com.legion.tests.core;

import com.legion.pages.DashboardPage;
import com.legion.pages.LocationSelectorPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.*;
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
import java.util.List;

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
        try{
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
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate Scheduling Minors rules (Ages 14 & 15 and Ages 16 & 17) can be edit successfully")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyEditMinorRulesAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsPage.gotoControlsPage();
            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);

            controlsNewUIPage.clickOnControlsComplianceSection();
            SimpleUtils.assertOnFail("collaboration page not loaded successfully!", controlsNewUIPage.isCompliancePageLoaded(), false);
            controlsNewUIPage.setSchedulingMinorRuleFor14N15("9:30 AM", "7:30 PM", "15", "6", "3", "5");
            controlsNewUIPage.setSchedulingMinorRuleFor16N17("10:00 AM", "7:00 PM", "20", "7", "5", "6");
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the warning message and violation when minor's shift exceed the weekend or holiday hours")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyWarningMessageForExceedWeekendHrsAsInternalAdmin(String browser, String username, String password, String location) {
        try {
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
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            String holidaySmartCard = "HOLIDAYS";
            List<String> holidays = null;
            if (schedulePage.isSpecificSmartCardLoaded(holidaySmartCard)){
                schedulePage.clickLinkOnSmartCardByName("View All");
                holidays = schedulePage.getHolidaysOfCurrentWeek();
                //close popup window
                schedulePage.closeAnalyzeWindow();
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
            schedulePage.clearAllSelectedDays();
            schedulePage.selectDaysByIndex(0,0,0);
            //set shift time as 10:00 AM - 6:00 PM
            schedulePage.moveSliderAtCertainPoint("6", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("10", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.selectWorkRole("Lift Maintenance");
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM1 + " " + lastNameOfTM.substring(0,1));
            schedulePage.verifyMessageIsExpected("minor daily max 6 hrs");
            schedulePage.clickOnRadioButtonOfSearchedTeamMemberByName(firstNameOfTM1);
            if(schedulePage.ifWarningModeDisplay()){
                String warningMessage = schedulePage.getWarningMessageInDragShiftWarningMode();
                if (warningMessage.contains("daily schedule should not exceed 6 hours")){
                    SimpleUtils.pass("Minor warning message for exceed the weekend or holiday hours displays");
                } else {
                    SimpleUtils.fail("There is no minor warning message display when shift exceed the weekend or holiday hours displays", false);
                }
                schedulePage.clickOnAssignAnywayButton();
            } else {
                SimpleUtils.fail("There is no minor warning message display when shift exceed the weekend or holiday hours displays",false);
            }
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();
            WebElement newAddedShift = schedulePage.getTheShiftByIndex(schedulePage.getAddedShiftIndexes(firstNameOfTM1).get(0));
            String test = schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).toString();
            if (newAddedShift != null) {
                SimpleUtils.assertOnFail("Get new added shift failed",schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Minor daily max 6 hrs"), false);
            } else {
                SimpleUtils.fail("Get new added shift failed", false);
            }


            //Create new shift for TM2, check create shift on holiday
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            if (holidays!=null){
                //i: 1-5 weekday
                int index =1;
                boolean flag = false;
                for (;index<=5; index++){
                    for (String s: holidays){
                        if (s.contains(schedulePage.getWeekDayTextByIndex(index))){
                            flag = true;
                        }
                    }
                    if (flag){
                        break;
                    }
                }
                schedulePage.selectDaysByIndex(index,index,index);
            } else {
                schedulePage.selectDaysByIndex(0,0,0);
            }


            //set shift time as 10:00 AM - 6:00 PM
            schedulePage.moveSliderAtCertainPoint("6", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("10", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.selectWorkRole("Lift Maintenance");
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM2 + " " + lastNameOfTM.substring(0,1));
            schedulePage.verifyMessageIsExpected("minor daily max 7 hrs");
            schedulePage.clickOnRadioButtonOfSearchedTeamMemberByName(firstNameOfTM2);
            if(schedulePage.ifWarningModeDisplay()){
                String warningMessage = schedulePage.getWarningMessageInDragShiftWarningMode();
                if (warningMessage.contains("daily schedule should not exceed 7 hours")){
                    SimpleUtils.pass("Minor warning message for exceed the weekend or holiday hours displays");
                } else {
                    SimpleUtils.fail("There is no minor warning message display when shift exceed the weekend or holiday hours displays", false);
                }
                schedulePage.clickOnAssignAnywayButton();
            } else {
                SimpleUtils.fail("There is no minor warning message display when shift exceed the weekend or holiday hours displays",false);
            }
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();
            newAddedShift = schedulePage.getTheShiftByIndex(schedulePage.getAddedShiftIndexes(firstNameOfTM2).get(0));
            if (newAddedShift != null) {
                SimpleUtils.assertOnFail("Get new added shift failed",
                        schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Minor daily max 7 hrs"), false);
            } else {
                SimpleUtils.fail("Get new added shift failed", false);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the warning message and violation when minor's shift exceed the weekday hours")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyWarningMessageForExceedWeekdayHrsAsInternalAdmin(String browser, String username, String password, String location) {
        try {
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
            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            String holidaySmartCard = "HOLIDAYS";
            List<String> holidays = null;
            if (schedulePage.isSpecificSmartCardLoaded(holidaySmartCard)){
                schedulePage.clickLinkOnSmartCardByName("View All");
                holidays = schedulePage.getHolidaysOfCurrentWeek();
                //close popup window
                schedulePage.closeAnalyzeWindow();
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
            schedulePage.clearAllSelectedDays();
            int index =1;
            if (holidays!=null){
                //i: 1-5 weekday
                boolean flag = false;
                for (;index<=5; index++){
                    for (String s: holidays){
                        if (s.contains(schedulePage.getWeekDayTextByIndex(index))){
                            flag = true;
                        }
                    }
                    if (!flag){
                        break;
                    }
                }
            }
            schedulePage.selectDaysByIndex(index,index,index);
            //set shift time as 10:00 AM - 6:00 PM
            schedulePage.moveSliderAtCertainPoint("4", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("10", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.selectWorkRole("Lift Maintenance");
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM1 + " " + lastNameOfTM.substring(0,1));
            schedulePage.verifyMessageIsExpected("minor daily max 3 hrs");
            schedulePage.clickOnRadioButtonOfSearchedTeamMemberByName(firstNameOfTM1);
            if(schedulePage.ifWarningModeDisplay()){
                String warningMessage = schedulePage.getWarningMessageInDragShiftWarningMode();
                if (warningMessage.contains("daily schedule should not exceed 3 hours")){
                    SimpleUtils.pass("Minor warning message for exceed the weekend or holiday hours displays");
                } else {
                    SimpleUtils.fail("There is no minor warning message display when shift exceed the weekend or holiday hours displays", false);
                }
                schedulePage.clickOnAssignAnywayButton();
            } else {
                SimpleUtils.fail("There is no minor warning message display when shift exceed the weekend or holiday hours displays",false);
            }
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();
            WebElement newAddedShift = schedulePage.getTheShiftByIndex(schedulePage.getAddedShiftIndexes(firstNameOfTM1).get(0));
            String test = schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).toString();
            if (newAddedShift != null) {
                SimpleUtils.assertOnFail("Get new added shift failed",schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Minor daily max 3 hrs"), false);
            } else {
                SimpleUtils.fail("Get new added shift failed", false);
            }


            //Create new shift for TM2
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.clearAllSelectedDays();
            schedulePage.selectDaysByIndex(index,index,index);

            //set shift time as 10:00 AM - 6:00 PM
            schedulePage.moveSliderAtCertainPoint("4", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("10", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.selectWorkRole("Lift Maintenance");
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM2 + " " + lastNameOfTM.substring(0,1));
            schedulePage.verifyMessageIsExpected("minor daily max 5 hrs");
            schedulePage.clickOnRadioButtonOfSearchedTeamMemberByName(firstNameOfTM2);
            if(schedulePage.ifWarningModeDisplay()){
                String warningMessage = schedulePage.getWarningMessageInDragShiftWarningMode();
                if (warningMessage.contains("daily schedule should not exceed 5 hours")){
                    SimpleUtils.pass("Minor warning message for exceed the weekend or holiday hours displays");
                } else {
                    SimpleUtils.fail("There is no minor warning message display when shift exceed the weekend or holiday hours displays", false);
                }
                schedulePage.clickOnAssignAnywayButton();
            } else {
                SimpleUtils.fail("There is no minor warning message display when shift exceed the weekend or holiday hours displays",false);
            }
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();
            newAddedShift = schedulePage.getTheShiftByIndex(schedulePage.getAddedShiftIndexes(firstNameOfTM2).get(0));
            if (newAddedShift != null) {
                SimpleUtils.assertOnFail("Get new added shift failed",
                        schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Minor daily max 5 hrs"), false);
            } else {
                SimpleUtils.fail("Get new added shift failed", false);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the warning message and violation when minor's shift is not during the setting time")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheWarningMessageAndViolationWhenMinorShiftIsNotDuringTheSettingTimeAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
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
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the warning message and violation when minor's shifts exceed the weekly hours")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheWarningMessageAndViolationWhenMinorShiftsExceedTheWeeklyHoursAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
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

            //Create 5 shifts for TM1 and the shifts have 15 hours totally
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            //set shift time as 11:00 AM - 2:00 PM
            schedulePage.moveSliderAtCertainPoint("2", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("11", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clearAllSelectedDays();
            schedulePage.selectSpecificWorkDay(5);
            schedulePage.selectWorkRole("Lift Maintenance");
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM1 + " " + lastNameOfTM.substring(0,1));
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();

            //Create the sixth shift for TM1
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            //set shift time as 11:00 AM - 2:00 PM
            schedulePage.moveSliderAtCertainPoint("2", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("11", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clearAllSelectedDays();
            schedulePage.selectDaysByIndex(5,5,5);
            schedulePage.selectWorkRole("Lift Maintenance");
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchText(firstNameOfTM1 + " " + lastNameOfTM.substring(0,1));

            //check the message in warning mode
            if(schedulePage.ifWarningModeDisplay()){
                String warningMessage1 = "As a minor, "+firstNameOfTM1+"'s weekly schedule should not exceed 15 hours";
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
            SimpleUtils.assertOnFail("There should have minor warning message display as: Minor weekly max 15 hrs! ",
                    schedulePage.getTheMessageOfTMScheduledStatus().contains("Minor weekly max 15 hrs"), false);

            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();

            //check the violation in i icon popup of new create shift
            WebElement newAddedShift = schedulePage.getOneDayShiftByName(5, firstNameOfTM1).get(0);
            if (newAddedShift != null) {
                SimpleUtils.assertOnFail("The minor violation message display incorrectly in i icon popup! ",
                        schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Minor weekly max 15 hrs"), false);
            } else
                SimpleUtils.fail("Get new added shift failed! ", false);


            //Create 4 shifts for TM2 and the shifts have 20 hours totally
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            //set shift time as 11:00 AM - 4:00 PM
            schedulePage.moveSliderAtCertainPoint("4", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("11", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clearAllSelectedDays();
            schedulePage.selectSpecificWorkDay(4);
            schedulePage.selectWorkRole("Lift Maintenance");
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM2 + " " + lastNameOfTM.substring(0,1));
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();

            //Create the fifth shift for TM2
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            //set shift time as 11:00 AM - 4:00 PM
            schedulePage.moveSliderAtCertainPoint("4", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("11", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clearAllSelectedDays();
            schedulePage.selectDaysByIndex(4,4,4);
            schedulePage.selectWorkRole("Lift Maintenance");
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchText(firstNameOfTM2 + " " + lastNameOfTM.substring(0,1));

            //check the message in warning mode
            if(schedulePage.ifWarningModeDisplay()){
                String warningMessage1 = "As a minor, "+firstNameOfTM2+"'s weekly schedule should not exceed 20 hours";
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
            SimpleUtils.assertOnFail("There should have minor warning message display as: Minor weekly max 20 hrs! ",
                    schedulePage.getTheMessageOfTMScheduledStatus().contains("Minor weekly max 20 hrs"), false);

            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();

            //check the violation in i icon popup of new create shift
            newAddedShift = schedulePage.getOneDayShiftByName(4, firstNameOfTM2).get(0);
            if (newAddedShift != null) {
                SimpleUtils.assertOnFail("The minor violation message display incorrectly in i icon popup! ",
                        schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Minor weekly max 20 hrs"), false);
            } else
                SimpleUtils.fail("Get new added shift failed! ", false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the warning message and violation when minor's shift days exceed the week days in setting")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheWarningMessageAndViolationWhenMinorShiftDaysExceedTheWeekDaysInSettingAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
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
            String firstNameOfTM1 = "Minor16";
            String firstNameOfTM2 = "Minor14";
            String lastNameOfTM = "RC";
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM2);

            //Create 6 shifts in 6 days for TM1
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            //set shift time as 11:00 AM - 1:00 PM
            schedulePage.moveSliderAtCertainPoint("1", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("11", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clearAllSelectedDays();
            schedulePage.selectSpecificWorkDay(6);
            schedulePage.selectWorkRole("Lift Maintenance");
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM1 + " " + lastNameOfTM.substring(0,1));
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();

            //Create the shift on seventh day for TM1
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            //set shift time as 11:00 AM - 1:00 PM
            schedulePage.moveSliderAtCertainPoint("1", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("11", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clearAllSelectedDays();
            schedulePage.selectDaysByIndex(6,6,6);
            schedulePage.selectWorkRole("Lift Maintenance");
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchText(firstNameOfTM1 + " " + lastNameOfTM.substring(0,1));

            //check the message in warning mode
            if(schedulePage.ifWarningModeDisplay()){
                String warningMessage1 = "As a minor, "+firstNameOfTM1+"'s weekly schedule should not exceed 6 days";
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
            SimpleUtils.assertOnFail("There should have minor warning message display as: Minor weekly max 6 days! ",
                    schedulePage.getTheMessageOfTMScheduledStatus().contains("Minor weekly max 6 days"), false);

            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();

            //check the violation in i icon popup of new create shift
            WebElement newAddedShift = schedulePage.getOneDayShiftByName(6, firstNameOfTM1).get(0);
            if (newAddedShift != null) {
                SimpleUtils.assertOnFail("The minor violation message display incorrectly in i icon popup! ",
                        schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Minor weekly max 6 days"), false);
            } else
                SimpleUtils.fail("Get new added shift failed! ", false);


            //Create 5 shifts in 5 days for TM2
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            //set shift time as 11:00 AM - 1:00 PM
            schedulePage.moveSliderAtCertainPoint("1", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("11", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clearAllSelectedDays();
            schedulePage.selectSpecificWorkDay(5);
            schedulePage.selectWorkRole("Lift Maintenance");
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(firstNameOfTM2 + " " + lastNameOfTM.substring(0,1));
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();

            //Create the shift in sixth day for TM2
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            //set shift time as 11:00 AM - 1:00 PM
            schedulePage.moveSliderAtCertainPoint("1", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("11", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.clearAllSelectedDays();
            schedulePage.selectDaysByIndex(5,5,5);
            schedulePage.selectWorkRole("Lift Maintenance");
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchText(firstNameOfTM2 + " " + lastNameOfTM.substring(0,1));

            //check the message in warning mode
            if(schedulePage.ifWarningModeDisplay()){
                String warningMessage1 = "As a minor, "+firstNameOfTM2+"'s weekly schedule should not exceed 5 days";
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
            SimpleUtils.assertOnFail("There should have minor warning message display as: Minor weekly max 5 days! ",
                    schedulePage.getTheMessageOfTMScheduledStatus().contains("Minor weekly max 5 days"), false);

            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();

            //check the violation in i icon popup of new create shift
            newAddedShift = schedulePage.getOneDayShiftByName(5, firstNameOfTM2).get(0);
            if (newAddedShift != null) {
                SimpleUtils.assertOnFail("The minor violation message display incorrectly in i icon popup! ",
                        schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Minor weekly max 5 days"), false);
            } else
                SimpleUtils.fail("Get new added shift failed! ", false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate the warning message and violation when minor's is < 14yr old")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheWarningMessageAndViolationWhenMinorIsUnder14YearsOldAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
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
            String firstNameOfTM1 = "Minor13";
            String lastNameOfTM = "RC";
            schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);


            //Create the shift for TM1
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            //set shift time as 0:00 AM - 1:00 PM
            schedulePage.moveSliderAtCertainPoint("1", ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.moveSliderAtCertainPoint("9", ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
            schedulePage.selectWorkRole("Lift Maintenance");
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchText(firstNameOfTM1 + " " + lastNameOfTM.substring(0,1));

            //check the message in warning mode
            if(schedulePage.ifWarningModeDisplay()){
                String warningMessage1 = firstNameOfTM1+" is < 14 years old";
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
            SimpleUtils.assertOnFail("There should have minor warning message display as: Age < 14yr old! ",
                    schedulePage.getTheMessageOfTMScheduledStatus().contains("Age < 14 yr old"), false);

            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();

            //check the violation in i icon popup of new create shift
            WebElement newAddedShift = schedulePage.getTheShiftByIndex(schedulePage.getAddedShiftIndexes(firstNameOfTM1).get(0));
            if (newAddedShift != null) {
                SimpleUtils.assertOnFail("The minor violation message display incorrectly in i icon popup! ",
                        schedulePage.getComplianceMessageFromInfoIconPopup(newAddedShift).contains("Age < 14 yr old"), false);
            } else
                SimpleUtils.fail("Get new added shift failed! ", false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
