package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.OpsPortaPageFactories.LocationsPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.core.OpsPortal.LocationsTest;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import cucumber.api.java.it.Ma;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;

public class P2PLGTest extends TestBase {

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) {
        try {
            this.createDriver((String) params[0], "69", "Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the assign TMs workflow for new create shift UI on P2P LG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheAssignTMsWorkFlowForNewCreateShiftUIOnP2PLGAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            scheduleCommonPage.navigateToNextWeek();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(!isActiveWeekGenerated){
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            scheduleMainPage.clickOnFilterBtn();
            String locationName = scheduleMainPage.selectRandomChildLocationToFilter();
            int shiftCount = scheduleShiftTablePage.getShiftsCount();
            ArrayList<HashMap<String,String>> locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);
            String workRole = shiftOperatePage.getRandomWorkRole();
            //Verify the assign workflow with one shift for one days
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            SimpleUtils.assertOnFail("New create shift page is not display! ",
                    newShiftPage.checkIfNewCreateShiftPageDisplay(), false);
            //Fill the required option
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow(locationName);
            String shiftStartTime = "8:00am";
            String shiftEndTime = "11:00am";
            String totalHrs = "3 Hrs";
            newShiftPage.moveSliderAtCertainPoint(shiftEndTime, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint(shiftStartTime, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            int count = 1;
            newShiftPage.setShiftPerDayOnNewCreateShiftPage(count);
            newShiftPage.clearAllSelectedDays();
            int dayCount = 1;
            newShiftPage.selectSpecificWorkDay(dayCount);
            String shiftName = "ShiftNameForBulkCreateShiftUIAuto";
            String shiftNotes = "Shift Notes For Bulk Create Shift UI Auto";
            newShiftPage.setShiftNameOnNewCreateShiftPage(shiftName);
            newShiftPage.setShiftNotesOnNewCreateShiftPage(shiftNotes);
            newShiftPage.clickOnCreateOrNextBtn();
            //Select 3 TMs to assign and click Create button
            shiftOperatePage.switchSearchTMAndRecommendedTMsTab();
            MyThreadLocal.setAssignTMStatus(true);
            String selectedTM1 = newShiftPage.selectTeamMembers();
            newShiftPage.clickOnCreateOrNextBtn();

            locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);
            SimpleUtils.assertOnFail("It should has "+count+1+" shifts display, but actual is has :"+scheduleShiftTablePage.getShiftsCount(),
                    shiftCount == scheduleShiftTablePage.getShiftsCount() -1, false);
            List<WebElement> shiftsOfOneDay = scheduleShiftTablePage.getOneDayShiftByName(0, selectedTM1.split(" ")[0]);
            SimpleUtils.assertOnFail("The "+selectedTM1+ "shift is not exist on the first day! ",
                    shiftsOfOneDay.size()==1, false);
            scheduleMainPage.saveSchedule();
            Thread.sleep(5000);
            locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);
            SimpleUtils.assertOnFail("It should has "+count+1+" shifts display, but actual is has :"+scheduleShiftTablePage.getShiftsCount(),
                    shiftCount == scheduleShiftTablePage.getShiftsCount() -1, false);
            shiftsOfOneDay = scheduleShiftTablePage.getOneDayShiftByName(0, selectedTM1.split(" ")[0]);
            SimpleUtils.assertOnFail("The open shift is not exist on the first day! ",
                    shiftsOfOneDay.size()==1, false);
            createSchedulePage.publishActiveSchedule();
            locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);
            SimpleUtils.assertOnFail("It should has "+count+1+" shifts display, but actual is has :"+scheduleShiftTablePage.getShiftsCount(),
                    shiftCount == scheduleShiftTablePage.getShiftsCount() -1, false);
            shiftsOfOneDay = scheduleShiftTablePage.getOneDayShiftByName(0, selectedTM1.split(" ")[0]);
            SimpleUtils.assertOnFail("The open shift is not exist on the first day! ",
                    shiftsOfOneDay.size()==1, false);

            String shiftId = shiftsOfOneDay.get(0).getAttribute("id").toString();
            int index = scheduleShiftTablePage.getShiftIndexById(shiftId);
            List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            String shiftTime = shiftInfo.get(2);
            String workRoleOfNewShift = shiftInfo.get(4);
            String shiftHrs = shiftInfo.get(8);
            String shiftNameOfNewShift = shiftInfo.get(9);
            String shiftNotesOfNewShift = shiftInfo.get(10);
            SimpleUtils.assertOnFail("The new shift's shift time display incorrectly, the expected is:"+shiftStartTime+"-"+shiftEndTime
                            + " the actual is: "+ shiftTime,
                    shiftTime.equalsIgnoreCase(shiftStartTime+"-"+shiftEndTime), false);
            SimpleUtils.assertOnFail("The new shift's work role display incorrectly, the expected is:"+ workRole
                            + " the actual is: "+ workRoleOfNewShift,
                    workRoleOfNewShift.equalsIgnoreCase(workRole), false);
            SimpleUtils.assertOnFail("The new shift's hrs display incorrectly, the expected is:"+ totalHrs
                            + " the actual is: "+ shiftHrs,
                    totalHrs.equalsIgnoreCase(shiftHrs), false);
            SimpleUtils.assertOnFail("The new shift's name display incorrectly, the expected is:"+ shiftName
                            + " the actual is: "+ shiftNameOfNewShift,
                    shiftName.equals(shiftNameOfNewShift), false);
            SimpleUtils.assertOnFail("The new shift's notes display incorrectly, the expected is:"+ shiftNotes
                            + " the actual is: "+ shiftNotesOfNewShift,
                    shiftNotes.equalsIgnoreCase(shiftNotesOfNewShift), false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the manuel offer TMs workflow for new create shift UI on P2P LG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheManuelOfferTMsWorkFlowForNewCreateShiftUIOnP2PLGAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();

            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
            scheduleCommonPage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.deleteTMShiftInWeekView("open");
            shiftOperatePage.deleteTMShiftInWeekView("unassigned");
            scheduleMainPage.saveSchedule();
            scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            scheduleMainPage.clickOnFilterBtn();
            String locationName = scheduleMainPage.selectRandomChildLocationToFilter();
            ArrayList<HashMap<String,String>> locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            int shiftCount = scheduleShiftTablePage.getShiftsCount();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);
            String workRole = shiftOperatePage.getRandomWorkRole();

            //Verify the manual offer workflow with one shift for one days
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            SimpleUtils.assertOnFail("New create shift page is not display! ",
                    newShiftPage.checkIfNewCreateShiftPageDisplay(), false);
            //Fill the required option
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow(locationName);
            String shiftStartTime = "8:00am";
            String shiftEndTime = "11:00am";
            String totalHrs = "3 Hrs";
            newShiftPage.moveSliderAtCertainPoint(shiftEndTime, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint(shiftStartTime, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
            int count = 1;
            newShiftPage.setShiftPerDayOnNewCreateShiftPage(count);
            newShiftPage.clearAllSelectedDays();
            int dayCount = 1;
            newShiftPage.selectSpecificWorkDay(dayCount);
            String shiftName = "ShiftNameForBulkCreateShiftUIAuto";
            String shiftNotes = "Shift Notes For Bulk Create Shift UI Auto";
            newShiftPage.setShiftNameOnNewCreateShiftPage(shiftName);
            newShiftPage.setShiftNotesOnNewCreateShiftPage(shiftNotes);
            newShiftPage.clickOnCreateOrNextBtn();
            //Select 3 TMs to offer and click Create button
            shiftOperatePage.switchSearchTMAndRecommendedTMsTab();
            MyThreadLocal.setAssignTMStatus(false);
            String selectedTM1 = newShiftPage.selectTeamMembers();
            String selectedTM2 = newShiftPage.selectTeamMembers();
            String selectedTM3 = newShiftPage.selectTeamMembers();
            newShiftPage.clickOnCreateOrNextBtn();
            locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);
            SimpleUtils.assertOnFail("It should has "+count+1+" shifts display, but actual is has :"+scheduleShiftTablePage.getShiftsCount(),
                    shiftCount == scheduleShiftTablePage.getShiftsCount() -1, false);
            scheduleMainPage.saveSchedule();
            Thread.sleep(5000);
            locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);
            SimpleUtils.assertOnFail("It should has "+count+1+" shifts display, but actual is has :"+scheduleShiftTablePage.getShiftsCount(),
                    shiftCount == scheduleShiftTablePage.getShiftsCount() -1, false);
            //There are 1 open shift been created and shift offer will been sent to the multiple TMs
            SimpleUtils.assertOnFail("The open shift is not exist on the first day! ",
                    scheduleShiftTablePage.getOneDayShiftByName(0, "Open").size()==1, false);
            int index = scheduleShiftTablePage.getAddedShiftIndexes("Open").get(0);
            scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
            scheduleShiftTablePage.clickViewStatusBtn();

            SimpleUtils.assertOnFail("The "+selectedTM1+" should display on the offer list! ",
                    !shiftOperatePage.getOfferStatusFromOpenShiftStatusList(selectedTM1).equals(""), false);
            SimpleUtils.assertOnFail("The "+selectedTM2+" should display on the offer list! ",
                    !shiftOperatePage.getOfferStatusFromOpenShiftStatusList(selectedTM2).equals(""), false);
            SimpleUtils.assertOnFail("The "+selectedTM3+" should display on the offer list! ",
                    !shiftOperatePage.getOfferStatusFromOpenShiftStatusList(selectedTM3).equals(""), false);
            shiftOperatePage.closeViewStatusContainer();

            //The work role, shifts time, daily hrs and weekly hrs are display correctly
            List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(index);
            String shiftTime = shiftInfo.get(2);
            String workRoleOfNewShift = shiftInfo.get(4);
            String shiftHrs = shiftInfo.get(6);
            String shiftNameOfNewShift = shiftInfo.get(7);
            String shiftNotesOfNewShift = shiftInfo.get(8);
            SimpleUtils.assertOnFail("The new shift's shift time display incorrectly, the expected is:"+shiftStartTime+"-"+shiftEndTime
                            + " the actual is: "+ shiftTime,
                    shiftTime.equalsIgnoreCase(shiftStartTime+"-"+shiftEndTime), false);
            SimpleUtils.assertOnFail("The new shift's work role display incorrectly, the expected is:"+ workRole
                            + " the actual is: "+ workRoleOfNewShift,
                    workRoleOfNewShift.equalsIgnoreCase(workRole), false);
            SimpleUtils.assertOnFail("The new shift's hrs display incorrectly, the expected is:"+ totalHrs
                            + " the actual is: "+ shiftHrs,
                    totalHrs.equalsIgnoreCase(shiftHrs), false);
            SimpleUtils.assertOnFail("The new shift's name display incorrectly, the expected is:"+ shiftName
                            + " the actual is: "+ shiftNameOfNewShift,
                    shiftName.equals(shiftNameOfNewShift), false);
            SimpleUtils.assertOnFail("The new shift's notes display incorrectly, the expected is:"+ shiftNotes
                            + " the actual is: "+ shiftNotesOfNewShift,
                    shiftNotes.equalsIgnoreCase(shiftNotesOfNewShift), false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
//    @Enterprise(name = "Vailqacn_Enterprise")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the auto offer TMs workflow for new create shift UI on P2P LG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateTheAutoOfferTMsWorkFlowForNewCreateShiftUIOnP2PLGAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();

            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
            scheduleCommonPage.navigateToNextWeek();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.deleteTMShiftInWeekView("open");
            scheduleMainPage.saveSchedule();
            String workRole = shiftOperatePage.getRandomWorkRole();
            scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            scheduleMainPage.clickOnFilterBtn();
            String locationName = scheduleMainPage.selectRandomChildLocationToFilter();
            ArrayList<HashMap<String,String>> locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            int shiftCount = scheduleShiftTablePage.getShiftsCount();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);

            //Verify the auto offer workflow with one shift for one days
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            SimpleUtils.assertOnFail("New create shift page is not display! ",
                    newShiftPage.checkIfNewCreateShiftPageDisplay(), false);
            //Fill the required option
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow(locationName);
            String shiftStartTime = "11:00am";
            String shiftEndTime = "2:00pm";
            String totalHrs = "3 Hrs";
            newShiftPage.moveSliderAtCertainPoint(shiftEndTime, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            newShiftPage.moveSliderAtCertainPoint(shiftStartTime, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
            int count = 1;
            newShiftPage.setShiftPerDayOnNewCreateShiftPage(count);
            newShiftPage.clearAllSelectedDays();
            int dayCount = 1;
            newShiftPage.selectSpecificWorkDay(dayCount);
            String shiftName = "ShiftNameForBulkCreateShiftUIAuto";
            String shiftNotes = "Shift Notes For Bulk Create Shift UI Auto";
            newShiftPage.setShiftNameOnNewCreateShiftPage(shiftName);
            newShiftPage.setShiftNotesOnNewCreateShiftPage(shiftNotes);
            newShiftPage.clickOnCreateOrNextBtn();
            locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);
            SimpleUtils.assertOnFail("It should has "+count+1+" shifts display, but actual is has :"+scheduleShiftTablePage.getShiftsCount(),
                    shiftCount == scheduleShiftTablePage.getShiftsCount() -1, false);
            scheduleMainPage.saveSchedule();
            locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);
            SimpleUtils.assertOnFail("It should has "+count+1+" shifts display, but actual is has :"+scheduleShiftTablePage.getShiftsCount(),
                    shiftCount == scheduleShiftTablePage.getShiftsCount() -1, false);
            SimpleUtils.assertOnFail("The open shift is not exist on the first day! ",
                    scheduleShiftTablePage.getOneDayShiftByName(0, "Open").size()==1, false);
            List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getAddedShiftIndexes("Open").get(0));
            String shiftTime = shiftInfo.get(2);
            String workRoleOfNewShift = shiftInfo.get(4);
            String shiftHrs = shiftInfo.get(6);
            String shiftNameOfNewShift = shiftInfo.get(7);
            String shiftNotesOfNewShift = shiftInfo.get(8);
            SimpleUtils.assertOnFail("The new shift's shift time display incorrectly, the expected is:"+shiftStartTime+"-"+shiftEndTime
                            + " the actual is: "+ shiftTime,
                    shiftTime.equalsIgnoreCase(shiftStartTime+"-"+shiftEndTime), false);
            SimpleUtils.assertOnFail("The new shift's work role display incorrectly, the expected is:"+ workRole
                            + " the actual is: "+ workRoleOfNewShift,
                    workRoleOfNewShift.equalsIgnoreCase(workRole), false);
            SimpleUtils.assertOnFail("The new shift's hrs display incorrectly, the expected is:"+ totalHrs
                            + " the actual is: "+ shiftHrs,
                    totalHrs.equalsIgnoreCase(shiftHrs), false);
            SimpleUtils.assertOnFail("The new shift's name display incorrectly, the expected is:"+ shiftName
                            + " the actual is: "+ shiftNameOfNewShift,
                    shiftName.equals(shiftNameOfNewShift), false);
            SimpleUtils.assertOnFail("The new shift's notes display incorrectly, the expected is:"+ shiftNotes
                            + " the actual is: "+ shiftNotesOfNewShift,
                    shiftNotes.equalsIgnoreCase(shiftNotesOfNewShift), false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate open shifts will been created when drag and drop shifts to same day and same location on P2P LG schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyBulkDragAndDropShiftsToSameDayAndSameLocationOnP2PLGScheduleAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

            // Navigate to next week
            scheduleCommonPage.navigateToNextWeek();
            // create the schedule.
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            if (scheduleShiftTablePage.getAllShiftsOfOneTM("open").size()>0) {
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("open");
                scheduleMainPage.saveSchedule();
            }
            scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectRandomChildLocationToFilter();
            ArrayList<HashMap<String,String>> locations = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            SimpleUtils.assertOnFail("It should has one location display, but actual is has :"+locations.size(),
                    locations.size() ==1, false);

            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            // Verify can select multiple shifts by pressing Ctrl/Cmd(Mac)
            int selectedShiftCount = 2;
            List<WebElement> selectedShifts = scheduleShiftTablePage.
                    selectMultipleDifferentAssignmentShiftsOnOneDay(selectedShiftCount, 1);
            //Drag the selected shifts to same day
            scheduleShiftTablePage.dragBulkShiftToAnotherDay(selectedShifts, 1, false);

            //Two open shifts been created
            List<WebElement> newAddedShifts = scheduleShiftTablePage.getOneDayShiftByName(1, "open");
            SimpleUtils.assertOnFail("The expected new added shifts count is "+selectedShiftCount
                            + " The actual new added shift count is:"+newAddedShifts.size(),
                    newAddedShifts.size()==0, false);

            scheduleMainPage.saveSchedule();
            newAddedShifts = scheduleShiftTablePage.getOneDayShiftByName(1, "open");
            SimpleUtils.assertOnFail("The expected new added shifts count is "+selectedShiftCount
                            + " The actual new added shift count is:"+newAddedShifts.size(),
                    newAddedShifts.size()==2, false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify shifts can be moved to same day and another location on p2p LG schedule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyBulkDragAndDropShiftsToSameDayAndAnotherLocationOnP2PLGScheduleAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

            // Navigate to next week
            scheduleCommonPage.navigateToNextWeek();
            // create the schedule.
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            if (scheduleShiftTablePage.getAllShiftsOfOneTM("open").size()>0) {
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("open");
                scheduleMainPage.saveSchedule();
            }
            scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            scheduleShiftTablePage.expandSpecificCountGroup(2);
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //Get shift count before drag and drop
            int allShiftsCountBefore = scheduleShiftTablePage.getShiftsCount();
            int oneDayShiftsCountBefore = scheduleShiftTablePage.getOneDayShiftCountByIndex(1);
            // Verify can select multiple shifts by pressing Ctrl/Cmd(Mac)
            int selectedShiftCount = 2;
            List<WebElement> selectedShifts = scheduleShiftTablePage.
                    selectMultipleDifferentAssignmentShiftsOnOneDay(selectedShiftCount, 1);
            List<String> shiftNames = new ArrayList<>();
            for (int i=0; i< selectedShiftCount;i++) {
                int index = scheduleShiftTablePage.getTheIndexOfShift(selectedShifts.get(i));
                shiftNames.add(scheduleShiftTablePage.getTheShiftInfoByIndex(index).get(0));
            }

            //Drag the selected shifts to same day
            scheduleShiftTablePage.dragBulkShiftToAnotherDay(selectedShifts, 1, true);

            //Select move option
            scheduleShiftTablePage.selectMoveOrCopyBulkShifts("move");
            scheduleShiftTablePage.enableOrDisableAllowComplianceErrorSwitch(true);
            scheduleShiftTablePage.enableOrDisableAllowConvertToOpenSwitch(true);
            scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();

            //Check the shift count after drag and drop
            int allShiftsCountAfter = scheduleShiftTablePage.getShiftsCount();
            int oneDayShiftsCountAfter = scheduleShiftTablePage.getOneDayShiftCountByIndex(1);
            SimpleUtils.assertOnFail("The expected count are: "+allShiftsCountBefore + " and "+ oneDayShiftsCountBefore
                            + ", but the actual are: "+allShiftsCountAfter + " and "+ oneDayShiftsCountAfter,
                    allShiftsCountAfter == allShiftsCountBefore && oneDayShiftsCountAfter == oneDayShiftsCountBefore, false);
            for (int i=0; i< selectedShiftCount;i++) {
                SimpleUtils.assertOnFail("Bulk Drag and drop: the shifts fail to be moved! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size()>0, false);
            }
            //Verify changes can be saved
            scheduleMainPage.saveSchedule();
            allShiftsCountAfter = scheduleShiftTablePage.getShiftsCount();
            oneDayShiftsCountAfter = scheduleShiftTablePage.getOneDayShiftCountByIndex(1);
            SimpleUtils.assertOnFail("The expected count are: "+allShiftsCountBefore + " and "+ oneDayShiftsCountBefore
                            + ", but the actual are: "+allShiftsCountAfter + " and "+ oneDayShiftsCountAfter,
                    allShiftsCountAfter == allShiftsCountBefore && oneDayShiftsCountAfter == oneDayShiftsCountBefore, false);
            for (int i=0; i< selectedShiftCount;i++) {
                SimpleUtils.assertOnFail("Bulk Drag and drop: the shifts fail to be moved! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size()>0, false);
            }
            //Verify changes can be published
            createSchedulePage.publishActiveSchedule();
            allShiftsCountAfter = scheduleShiftTablePage.getShiftsCount();
            oneDayShiftsCountAfter = scheduleShiftTablePage.getOneDayShiftCountByIndex(1);
            SimpleUtils.assertOnFail("The expected count are: "+allShiftsCountBefore + " and "+ oneDayShiftsCountBefore
                            + ", but the actual are: "+allShiftsCountAfter + " and "+ oneDayShiftsCountAfter,
                    allShiftsCountAfter == allShiftsCountBefore && oneDayShiftsCountAfter == oneDayShiftsCountBefore, false);
            for (int i=0; i< selectedShiftCount;i++) {
                SimpleUtils.assertOnFail("Bulk Drag and drop: the shifts fail to be moved! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size()>0, false);
            }
            //Check the shifts are not display on first location
            scheduleShiftTablePage.expandSpecificCountGroup(1);
            for (int i=0; i< selectedShiftCount;i++) {
                SimpleUtils.assertOnFail("Bulk Drag and drop: the shifts fail to be moved! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size()==0, false);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the functionality of peer locations in the Schedule Overview page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheFunctionalityOfPeerLocationsInTheScheduleOverviewPageAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            boolean isLocationUsingControlsConfiguration = controlsNewUIPage.checkIfTheLocationUsingControlsConfiguration();
            String isBudgetEnabled = "";
            //Check the budget is enabled or not
            if (isLocationUsingControlsConfiguration) {
                controlsNewUIPage.clickOnControlsConsoleMenu();
                controlsNewUIPage.clickOnControlsSchedulingPolicies();
                Thread.sleep(10000);
                isBudgetEnabled = controlsNewUIPage.getApplyLaborBudgetToSchedulesActiveBtnLabel();
            } else {
                LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
                locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
                SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
                locationsPage.clickOnLocationsTab();
                locationsPage.goToGlobalConfigurationInLocations();
                Thread.sleep(10000);
                isBudgetEnabled = controlsNewUIPage.getApplyLaborBudgetToSchedulesActiveBtnLabel();
                switchToConsoleWindow();
            }
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

            // create the schedule.
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            List<String> locationNames = scheduleMainPage.getSpecificFilterNames("location");
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            //Check that the peer locations should be listed
            for (String name : locationNames) {
                scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(name);
            }

            //Corresponding peer locations should be loaded according to the search strings
            for (String name : locationNames) {
                scheduleDMViewPage.getAllUpperFieldInfoFromScheduleByUpperField(name);
            }

            //Verify the columns of the location list
            scheduleDMViewPage.verifyP2PSchedulesTableHeaderNames(isBudgetEnabled.equalsIgnoreCase("Yes"));

            //Verify the Not Started status when the peer location schedule has not been created yet
            String peerLocation = locationNames.get(new Random().nextInt(locationNames.size()));
            scheduleDMViewPage.clickOnLocationNameInDMView(peerLocation);
            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(location);
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            Thread.sleep(3000);
            scheduleDMViewPage.clickOnRefreshButton();
            String publishStatus = scheduleDMViewPage.getAllUpperFieldInfoFromScheduleByUpperField(peerLocation)
                    .get("publishedStatus");
            SimpleUtils.assertOnFail("The schedule status should be Not Started, but actual is:"+publishStatus,
                    publishStatus.equalsIgnoreCase("Not Started"), false);

            //Verify the In Progress status when the peer location schedule is created but was never published
            Thread.sleep(3000);
            scheduleDMViewPage.clickOnLocationNameInDMView(peerLocation);
            isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(location);
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleDMViewPage.clickOnRefreshButton();
            publishStatus = scheduleDMViewPage.getAllUpperFieldInfoFromScheduleByUpperField(peerLocation)
                    .get("publishedStatus");
            SimpleUtils.assertOnFail("The schedule status should be In progress, but actual is:"+publishStatus,
                    publishStatus.equalsIgnoreCase("In Progress"), false);

            //Verify the Published status when the peer location schedule has been published
            Thread.sleep(3000);
            scheduleDMViewPage.clickOnLocationNameInDMView(peerLocation);
            if (smartCardPage.isRequiredActionSmartCardLoaded()) {
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Unassigned");
                scheduleMainPage.saveSchedule();
            }
            createSchedulePage.publishActiveSchedule();
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(location);
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            Thread.sleep(3000);
            scheduleDMViewPage.clickOnRefreshButton();
            publishStatus = scheduleDMViewPage.getAllUpperFieldInfoFromScheduleByUpperField(peerLocation)
                    .get("publishedStatus");
            SimpleUtils.assertOnFail("The schedule status should be Published, but actual is:"+publishStatus,
                    publishStatus.equalsIgnoreCase("Published"), false);

            //Verify the Published status when the peer location schedule has been updated after the publish can be republished
            Thread.sleep(3000);
            scheduleDMViewPage.clickOnLocationNameInDMView(peerLocation);
            String workRole = shiftOperatePage.getRandomWorkRole();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            shiftOperatePage.switchSearchTMAndRecommendedTMsTab();
            newShiftPage.selectTeamMembers();
            newShiftPage.clickOnCreateOrNextBtn();
            scheduleMainPage.saveSchedule();
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(location);
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            Thread.sleep(3000);
            scheduleDMViewPage.clickOnRefreshButton();
            publishStatus = scheduleDMViewPage.getAllUpperFieldInfoFromScheduleByUpperField(peerLocation)
                    .get("publishedStatus");
            SimpleUtils.assertOnFail("The schedule status should be Published, but actual is:"+publishStatus,
                    publishStatus.equalsIgnoreCase("Published"), false);

            //Verify the functionality of "View Group Schedule" button
            Map<String, String> weekInfoBeforeClick = scheduleCommonPage.getActiveDayInfo();
            scheduleOverviewPage.clickOnViewGroupScheduleButton();
            SimpleUtils.assertOnFail("The schedule main page should be loaded! ",
                    scheduleMainPage.isScheduleMainPageLoaded(), false);
            Map<String, String> weekInfoAfterClick = scheduleCommonPage.getActiveDayInfo();
            //Verify the correct week is shown
            SimpleUtils.assertOnFail("The week info before click is: "+weekInfoBeforeClick+
                            " The week info after click is: "+weekInfoAfterClick,
                    weekInfoAfterClick.equals(weekInfoBeforeClick), false);

            //Verify can navigate back to overview page when click the back button of the browser
            MyThreadLocal.getDriver().navigate().back();
            SimpleUtils.assertOnFail("The P2P overview page should display! ",
                    scheduleDMViewPage.isScheduleDMView(), false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Ting")
    @Enterprise(name = "")
    @TestName(description = "Verify copy or move shifts to sub-locations in same day using location group")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyCopyOrMoveShiftsToSubLocationsInSameDayUsingLocationGroupAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

            // Navigate to next week
            scheduleCommonPage.navigateToNextWeek();

            // create the schedule.
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            if (scheduleShiftTablePage.getAllShiftsOfOneTM("open").size()>0) {
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("open");
                scheduleMainPage.saveSchedule();
            }
            scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            scheduleShiftTablePage.expandSpecificCountGroup(2);
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            //Get shift count before drag and drop
            int allShiftsCountBeforeForCopy = scheduleShiftTablePage.getShiftsCount();
            int oneDayShiftsCountBeforeForCopy = scheduleShiftTablePage.getOneDayShiftCountByIndex(1);

            // Verify can select multiple shifts by pressing Ctrl/Cmd(Mac)
            int selectedShiftCount = 1;
            List<WebElement> selectedShifts = scheduleShiftTablePage.
                    selectMultipleDifferentAssignmentShiftsOnOneDay(selectedShiftCount, 1);
            List<String> shiftNames = new ArrayList<>();
            for (int i=0; i< selectedShiftCount; i++) {
                int index = scheduleShiftTablePage.getTheIndexOfShift(selectedShifts.get(i));
                shiftNames.add(scheduleShiftTablePage.getTheShiftInfoByIndex(index).get(0));
            }

            //Drag the selected shifts to same day
            scheduleShiftTablePage.dragBulkShiftToAnotherDay(selectedShifts, 1, true);

            //Select copy option
            scheduleShiftTablePage.selectCopyOrMoveByOptionName("Copy");
            scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();

            //Check the shift count after drag and drop
            int allShiftsCountAfterForCopy = scheduleShiftTablePage.getShiftsCount();
            int oneDayShiftsCountAfterForCopy = scheduleShiftTablePage.getOneDayShiftCountByIndex(1);
            SimpleUtils.assertOnFail("The expected count are: " + allShiftsCountBeforeForCopy + " and " + oneDayShiftsCountBeforeForCopy
                            + ", but the actual are: " + allShiftsCountAfterForCopy + " and " + oneDayShiftsCountAfterForCopy,
                    allShiftsCountAfterForCopy - allShiftsCountBeforeForCopy == 1 && oneDayShiftsCountAfterForCopy - oneDayShiftsCountBeforeForCopy == 1, false);
            for (int i=0; i< selectedShiftCount; i++) {
                SimpleUtils.assertOnFail("Drag and drop: the shift failed to be copied! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size() > 0, false);
            }
            SimpleUtils.assertOnFail("An open shift should be created!", scheduleShiftTablePage.getAllShiftsOfOneTM("open").size() == 1, false);

            /*
            // TODO: Can't be saved due to repetitive shifts created instead of converting it to an open shift
            // Issue has been raised: https://legiontech.atlassian.net/browse/SCH-7077
            */

            //Verify changes can be saved
            scheduleMainPage.saveSchedule();
            allShiftsCountAfterForCopy = scheduleShiftTablePage.getShiftsCount();
            oneDayShiftsCountAfterForCopy = scheduleShiftTablePage.getOneDayShiftCountByIndex(1);
            SimpleUtils.assertOnFail("The expected count are: " + allShiftsCountBeforeForCopy + " and "+ oneDayShiftsCountBeforeForCopy
                            + ", but the actual are: " + allShiftsCountAfterForCopy + " and "+ oneDayShiftsCountAfterForCopy,
                    allShiftsCountAfterForCopy - allShiftsCountBeforeForCopy == 1 && oneDayShiftsCountAfterForCopy - oneDayShiftsCountBeforeForCopy == 1, false);
            for (int i=0; i< selectedShiftCount; i++) {
                SimpleUtils.assertOnFail("Drag and drop: the shift failed to be copied! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size() > 0, false);
            }
            SimpleUtils.assertOnFail("An open shift should be created!", scheduleShiftTablePage.getAllShiftsOfOneTM("open").size() == 1, false);

            // Edit schedule
            scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            scheduleShiftTablePage.expandSpecificCountGroup(2);
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

            //Get shift count before drag and drop
            int allShiftsCountBeforeForMove = scheduleShiftTablePage.getShiftsCount();
            int oneDayShiftsCountBeforeForMove = scheduleShiftTablePage.getOneDayShiftCountByIndex(1);

            //Drag the selected shifts to same day
            scheduleShiftTablePage.dragBulkShiftToAnotherDay(selectedShifts, 1, true);

            //Select move option
            scheduleShiftTablePage.selectCopyOrMoveByOptionName("Move");
            scheduleShiftTablePage.clickConfirmBtnOnDragAndDropConfirmPage();

            //Check the shift count after drag and drop
            int allShiftsCountAfterForMove = scheduleShiftTablePage.getShiftsCount();
            int oneDayShiftsCountAfterForMove = scheduleShiftTablePage.getOneDayShiftCountByIndex(1);
            SimpleUtils.assertOnFail("The expected count are: "+ allShiftsCountBeforeForMove + " and "+ oneDayShiftsCountBeforeForMove
                            + ", but the actual are: "+allShiftsCountAfterForMove + " and "+ oneDayShiftsCountAfterForMove,
                    allShiftsCountAfterForMove == allShiftsCountBeforeForMove && oneDayShiftsCountAfterForMove == oneDayShiftsCountBeforeForMove, false);
            for (int i=0; i< selectedShiftCount; i++) {
                SimpleUtils.assertOnFail("Drag and drop: the shift failed to be copied! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size() > 0, false);
            }
            SimpleUtils.assertOnFail("An open shift should be there!", scheduleShiftTablePage.getAllShiftsOfOneTM("open").size() == 1, false);

            //Verify changes can be saved
            scheduleMainPage.saveSchedule();
            allShiftsCountAfterForMove = scheduleShiftTablePage.getShiftsCount();
            oneDayShiftsCountAfterForMove = scheduleShiftTablePage.getOneDayShiftCountByIndex(1);
            SimpleUtils.assertOnFail("The expected count are: " + allShiftsCountBeforeForMove + " and " + oneDayShiftsCountBeforeForMove
                            + ", but the actual are: "+allShiftsCountAfterForMove + " and "+ oneDayShiftsCountAfterForMove,
                    allShiftsCountAfterForMove == allShiftsCountBeforeForMove && oneDayShiftsCountAfterForMove == oneDayShiftsCountBeforeForMove, false);
            for (int i=0; i< selectedShiftCount; i++) {
                SimpleUtils.assertOnFail("Drag and drop: the shift failed to be copied! ",
                        scheduleShiftTablePage.getOneDayShiftByName(1, shiftNames.get(i)).size() > 0, false);
            }
            SimpleUtils.assertOnFail("An open shift should be there!", scheduleShiftTablePage.getAllShiftsOfOneTM("open").size() == 1, false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify can create the schedule for all peer locations")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyCanCreateScheduleForAllPeerLocationsAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

            // create the schedule.
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            List<String> locationNames = scheduleMainPage.getSpecificFilterNames("location");
            if (smartCardPage.isRequiredActionSmartCardLoaded()) {
                scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyAll.getValue());
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("unassigned");
                scheduleMainPage.saveSchedule();
            }
            scheduleMainPage.publishOrRepublishSchedule();

            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            //Check that the peer locations should be listed
            for (String name : locationNames) {
                scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(name);
                scheduleDMViewPage.clickOnRefreshButton();
                scheduleDMViewPage.clickOnRefreshButton();
                String publishStatus = scheduleDMViewPage.getAllUpperFieldInfoFromScheduleByUpperField(name)
                        .get("publishedStatus");
                SimpleUtils.assertOnFail("The schedule status should be Published, but actual is:"+publishStatus,
                        publishStatus.equalsIgnoreCase("Published"), false);
            }
            scheduleOverviewPage.clickOnViewGroupScheduleButton();
            scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            scheduleShiftTablePage.verifyGroupCanbeCollapsedNExpanded();

            //Verify the value on LOCATION GROUP smart card when schedule is published
            String messageOnSmartCard = smartCardPage.getsmartCardTextByLabel("LOCATION GROUP").replace("\n", " ");
            String expectedMessage1 = "0 Not Started";
            String expectedMessage2 = "0 In Progress";
            String expectedMessage3 = locationNames.size()+" Published";
            String expectedMessage4 = locationNames.size()+" Total Locations";
            SimpleUtils.assertOnFail("The expected message is: "+expectedMessage1
                            + expectedMessage2+ " "
                            + expectedMessage3+ " "
                            + expectedMessage4+ " The actual message is: "+messageOnSmartCard,
                    messageOnSmartCard.contains(expectedMessage1)
                            && messageOnSmartCard.contains(expectedMessage2)
                            && messageOnSmartCard.contains(expectedMessage3)
                            && messageOnSmartCard.contains(expectedMessage4), false);

            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            //Check that the peer locations should be listed
            for (String name : locationNames) {
                scheduleDMViewPage.getAllScheduleInfoFromScheduleInDMViewByLocation(name);
                scheduleDMViewPage.clickOnRefreshButton();
                String publishStatus = scheduleDMViewPage.getAllUpperFieldInfoFromScheduleByUpperField(name)
                        .get("publishedStatus");
                SimpleUtils.assertOnFail("The schedule status should be Published, but actual is:"+publishStatus,
                        publishStatus.equalsIgnoreCase("Not Started"), false);
            }
            scheduleOverviewPage.clickOnViewGroupScheduleButton();
            scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            scheduleShiftTablePage.verifyGroupCannotbeCollapsedNExpanded();

            //Verify the value on LOCATION GROUP smart card when schedule is published
            messageOnSmartCard = smartCardPage.getsmartCardTextByLabel("LOCATION GROUP").replace("\n", " ");
            expectedMessage1 = locationNames.size()+" Not Started";
            expectedMessage2 = "0 In Progress";
            expectedMessage3 = "0 Published";
            expectedMessage4 = locationNames.size()+" Total Locations";
            SimpleUtils.assertOnFail("The expected message is: "+expectedMessage1
                            + expectedMessage2+ " "
                            + expectedMessage3+ " "
                            + expectedMessage4+ " The actual message is: "+messageOnSmartCard,
                    messageOnSmartCard.contains(expectedMessage1)
                            && messageOnSmartCard.contains(expectedMessage2)
                            && messageOnSmartCard.contains(expectedMessage3)
                            && messageOnSmartCard.contains(expectedMessage4), false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the actions for each peer locations in different status")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheActionForEachPeerLocationsInDifferentStatusAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

            //Delete the schedule.
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            String locationName = scheduleMainPage.getSpecificFilterNames("location").get(0);
            createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            //The status is changed to Not Started
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            String status = scheduleShiftTablePage.getSpecificGroupByChildLocationStatus(locationName);
            SimpleUtils.assertOnFail("The expected status is Not Started, the actual status is: "+status,
                    status.equalsIgnoreCase("Not Started"), false);
            scheduleShiftTablePage.clickActionIconForSpecificGroupByChildLocation(locationName);

            //Verify "Edit Operating Hours" action when peer location is Not Started
            String editOperatingHoursButton = "Edit Operating Hours";
            String deleteButton = "Delete Schedule";
            String createScheduleButton = "Create Schedule";
            String publishScheduleButton = "Publish Schedule";
            String republishButton = "Republish Schedule";
            List<String> buttonsFromPopup = scheduleShiftTablePage.getButtonNamesFromGroupByActionPopup();
            SimpleUtils.assertOnFail("The buttons on group by location action popup display incorrectly! The expected is"
                    + createScheduleButton + " button and "+ editOperatingHoursButton + " button."
                    +" The actual is"+buttonsFromPopup,
                    buttonsFromPopup.contains(editOperatingHoursButton)
                            && buttonsFromPopup.contains(createScheduleButton), false);
            scheduleShiftTablePage.clickOnSpecificButtonsGroupByActionPopup(editOperatingHoursButton);
            newShiftPage.customizeNewShiftPage();
            newShiftPage.closeNewCreateShiftPage();

            //Verify "Create Schedule" action when peer location is Not Started
            scheduleShiftTablePage.clickActionIconForSpecificGroupByChildLocation(locationName);
            scheduleShiftTablePage.clickOnSpecificButtonsGroupByActionPopup(createScheduleButton);
            createSchedulePage.clickNextBtnOnCreateScheduleWindow();
            Thread.sleep(3000);
            if (createSchedulePage.isCopyScheduleWindow()) {
                createSchedulePage.selectWhichWeekToCopyFrom("SUGGESTED");
                createSchedulePage.clickOnFinishButtonOnCreateSchedulePage();
            }
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            if (smartCardPage.isRequiredActionSmartCardLoaded()) {
                scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyAll.getValue());
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("unassigned");
                scheduleMainPage.saveSchedule();
                scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyLocation.getValue());
            }

            //The status is changed to In Progress
            status = scheduleShiftTablePage.getSpecificGroupByChildLocationStatus(locationName);
            SimpleUtils.assertOnFail("The expected status is In Progress, the actual status is: "+status,
                    status.equalsIgnoreCase("In Progress"), false);

            //Verify "Edit Operating Hours" action when peer location is In Progress
            scheduleShiftTablePage.clickActionIconForSpecificGroupByChildLocation(locationName);
            buttonsFromPopup = scheduleShiftTablePage.getButtonNamesFromGroupByActionPopup();
            SimpleUtils.assertOnFail("The buttons on group by location action popup display incorrectly! The expected is"
                            + editOperatingHoursButton + " button and "+ publishScheduleButton + " button and "+ deleteButton + " button."
                            +" The actual is"+buttonsFromPopup,
                    buttonsFromPopup.contains(editOperatingHoursButton)
                            && buttonsFromPopup.contains(publishScheduleButton)
                            && buttonsFromPopup.contains(deleteButton), false);
            scheduleShiftTablePage.clickOnSpecificButtonsGroupByActionPopup(editOperatingHoursButton);
            newShiftPage.customizeNewShiftPage();
            newShiftPage.closeNewCreateShiftPage();

            //Verify "Publish" action when peer location is In Progress
            scheduleShiftTablePage.clickActionIconForSpecificGroupByChildLocation(locationName);
            scheduleShiftTablePage.clickOnSpecificButtonsGroupByActionPopup(publishScheduleButton);
            scheduleShiftTablePage.clickOnOkButtonInWarningMode();
            Thread.sleep(5000);
            //The status is changed to Published
            status = scheduleShiftTablePage.getSpecificGroupByChildLocationStatus(locationName);
            SimpleUtils.assertOnFail("The expected status is Published, the actual status is: "+status,
                    status.equalsIgnoreCase("Published"), false);

            //Verify the actions when peer locations has unpublished changes
            String workRole = shiftOperatePage.getRandomWorkRole();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            newShiftPage.clickOnDayViewAddNewShiftButton();
            newShiftPage.customizeNewShiftPage();
            newShiftPage.selectWorkRole(workRole);
            newShiftPage.selectChildLocInCreateShiftWindow(locationName);
            newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            newShiftPage.clickOnCreateOrNextBtn();
            shiftOperatePage.switchSearchTMAndRecommendedTMsTab();
            newShiftPage.selectTeamMembers();
            newShiftPage.clickOnCreateOrNextBtn();
            scheduleMainPage.saveSchedule();
            scheduleShiftTablePage.clickActionIconForSpecificGroupByChildLocation(locationName);
            buttonsFromPopup = scheduleShiftTablePage.getButtonNamesFromGroupByActionPopup();
            SimpleUtils.assertOnFail("The buttons on group by location action popup display incorrectly! The expected is"
                            + editOperatingHoursButton + " button and "+ republishButton + " button and "+ deleteButton + " button."
                            +" The actual is"+buttonsFromPopup,
                    buttonsFromPopup.contains(editOperatingHoursButton)
                            && buttonsFromPopup.contains(republishButton)
                            && buttonsFromPopup.contains(deleteButton), false);
            scheduleShiftTablePage.clickOnSpecificButtonsGroupByActionPopup(republishButton);
            scheduleShiftTablePage.clickOnOkButtonInWarningMode();
            Thread.sleep(5000);
            //The status is changed to Published
            status = scheduleShiftTablePage.getSpecificGroupByChildLocationStatus(locationName);
            SimpleUtils.assertOnFail("The expected status is Published, the actual status is: "+status,
                    status.equalsIgnoreCase("Published"), false);

            //Verify "Delete" when peer location is Published
            scheduleShiftTablePage.clickActionIconForSpecificGroupByChildLocation(locationName);
            buttonsFromPopup = scheduleShiftTablePage.getButtonNamesFromGroupByActionPopup();
            SimpleUtils.assertOnFail("The buttons on group by location action popup display incorrectly! The expected is"
                            + deleteButton + " button and "+ editOperatingHoursButton + " button."
                            +" The actual is"+buttonsFromPopup,
                    buttonsFromPopup.contains(editOperatingHoursButton)
                            && buttonsFromPopup.contains(deleteButton), false);
            scheduleShiftTablePage.clickOnSpecificButtonsGroupByActionPopup(deleteButton);
            createSchedulePage.confirmDeleteSchedule();
            Thread.sleep(5000);
            //The status is changed to Not Started
            status = scheduleShiftTablePage.getSpecificGroupByChildLocationStatus(locationName);
            SimpleUtils.assertOnFail("The expected status is Not Started, the actual status is: "+status,
                    status.equalsIgnoreCase("Not Started"), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the status of P2P in DM view")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheStatusOfP2PInDMViewsAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
            ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
            SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), true);

            //Verify Not Started will show if all the peer locations are Not Started
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            Map<String, String> selectedUpperFields = locationSelectorPage.getSelectedUpperFields();
            String districtName = selectedUpperFields.get("District");
            locationSelectorPage.changeUpperFieldDirect("District", districtName);
            scheduleDMViewPage.clickOnRefreshButton();
            String publishStatus = scheduleDMViewPage.getAllUpperFieldInfoFromScheduleByUpperField(location)
                    .get("publishedStatus");
            SimpleUtils.assertOnFail("The schedule status should be Not Started, but actual is:"+publishStatus,
                    publishStatus.equalsIgnoreCase("Not Started"), false);
            //Verify In Progress will show if some peer locations are Not Started, some are In Progress
            scheduleDMViewPage.clickOnLocationNameInDMView(location);
            createSchedulePage.createScheduleForNonDGFlowNewUI();
            locationSelectorPage.changeUpperFieldDirect("District", districtName);
            scheduleDMViewPage.clickOnRefreshButton();
            publishStatus = scheduleDMViewPage.getAllUpperFieldInfoFromScheduleByUpperField(location)
                    .get("publishedStatus");
            SimpleUtils.assertOnFail("The schedule status should be In Progress, but actual is:"+publishStatus,
                    publishStatus.equalsIgnoreCase("In Progress"), false);
            //Verify Published will show if all the peer locations are published
            scheduleDMViewPage.clickOnLocationNameInDMView(location);
            if (smartCardPage.isRequiredActionSmartCardLoaded()) {
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("Unassigned");
                scheduleMainPage.saveSchedule();
            }
            createSchedulePage.publishActiveSchedule();
            locationSelectorPage.changeUpperFieldDirect("District", districtName);
            scheduleDMViewPage.clickOnRefreshButton();
            publishStatus = scheduleDMViewPage.getAllUpperFieldInfoFromScheduleByUpperField(location)
                    .get("publishedStatus");
            SimpleUtils.assertOnFail("The schedule status should be Published, but actual is:"+publishStatus,
                    publishStatus.equalsIgnoreCase("Published"), false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the content on Multiple Edit Shifts window for P2P location group")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOnMultipleEditShiftsWindowForP2PAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            EditShiftPage editShiftPage = pageFactory.createEditShiftPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();

            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Schedule page, Schedule tab
            goToSchedulePageScheduleTab();

            // Create schedule if it is not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                createSchedulePage.createScheduleForNonDGFlowNewUI();
            }
            BasePage basePage = new BasePage();
            String activeWeek = basePage.getActiveWeekText();
            String startOfWeek = activeWeek.split(" ")[3] + " " + activeWeek.split(" ")[4];
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            // Verify can select multiple shifts by pressing Ctrl/Cmd(Mac)
            int selectedShiftCount = 2;
            HashSet<Integer> set = scheduleShiftTablePage.verifyCanSelectMultipleShifts(selectedShiftCount);
            List<String> selectedDays = scheduleShiftTablePage.getSelectedWorkDays(set);
            // Verify action menu will pop up when right clicking on anywhere of the selected shifts
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            scheduleShiftTablePage.verifyTheContentOnBulkActionMenu(selectedShiftCount);
            // Verify Edit action is visible when right clicking the selected shifts in week view
            // Verify the functionality of Edit button in week view
            String action = "Edit";
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            SimpleUtils.assertOnFail("Edit Shifts window failed to load!", editShiftPage.isEditShiftWindowLoaded(), false);
            // Verify the title of Edit Shifts window
            editShiftPage.verifyTheTitleOfEditShiftsWindow(selectedShiftCount, startOfWeek);
            // Verify the selected days show correctly
            editShiftPage.verifySelectedWorkDays(selectedShiftCount, selectedDays);
            // Verify the Location Name shows correctly
            editShiftPage.verifyLocationNameShowsCorrectly(location);
            // Verify the visibility of buttons
            editShiftPage.verifyTheVisibilityOfButtons();
            // Verify the content of options section
            editShiftPage.verifyTheContentOfOptionsSection();
            // Verify the visibility of Clear Edited Fields button
            SimpleUtils.assertOnFail("Clear Edited Fields button failed to load!", editShiftPage.isClearEditedFieldsBtnLoaded(), false);
            // Verify the three columns show on Shift Details section
            editShiftPage.verifyThreeColumns();
            // Verify the editable types show on Shift Detail section
            editShiftPage.verifyEditableTypesShowOnShiftDetail();
            // Verify the functionality of x button
            editShiftPage.clickOnXButton();
            SimpleUtils.assertOnFail("Click on X button failed!", !editShiftPage.isEditShiftWindowLoaded(), false);
            // Verify the functionality of Cancel button
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            SimpleUtils.assertOnFail("Edit Shifts window failed to load!", editShiftPage.isEditShiftWindowLoaded(), false);
            editShiftPage.clickOnCancelButton();

            scheduleMainPage.clickOnCancelButtonOnEditMode();
            scheduleCommonPage.clickOnDayView();
            String weekDay = basePage.getActiveWeekText();
            String fullWeekDay = SimpleUtils.getFullWeekDayName(weekDay.split(" ")[0].trim());
            selectedDays = new ArrayList<>();
            selectedDays.add(fullWeekDay);
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            set = scheduleShiftTablePage.verifyCanSelectMultipleShifts(selectedShiftCount);
            scheduleShiftTablePage.rightClickOnSelectedShifts(set);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            SimpleUtils.assertOnFail("Edit Shifts window failed to load!", editShiftPage.isEditShiftWindowLoaded(), false);
            // Verify the title of Edit Shifts window in day view
            editShiftPage.verifyTheTitleOfEditShiftsWindow(selectedShiftCount, startOfWeek);
            // Verify the selected days show correctly in day view
            editShiftPage.verifySelectedWorkDays(selectedShiftCount, selectedDays);
            // Verify the Location Name shows correctly in day view
            editShiftPage.verifyLocationNameShowsCorrectly(location);
            // Verify the visibility of buttons in day view
            editShiftPage.verifyTheVisibilityOfButtons();
            // Verify the content of options section in day view
            editShiftPage.verifyTheContentOfOptionsSection();
            // Verify the visibility of Clear Edited Fields button in day view
            SimpleUtils.assertOnFail("Clear Edited Fields button failed to load!", editShiftPage.isClearEditedFieldsBtnLoaded(), false);
            // Verify the three columns show on Shift Details section in day view
            editShiftPage.verifyThreeColumns();
            // Verify the editable types show on Shift Detail section in day view
            editShiftPage.verifyEditableTypesShowOnShiftDetail();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
