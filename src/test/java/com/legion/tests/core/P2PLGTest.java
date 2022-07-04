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
}
