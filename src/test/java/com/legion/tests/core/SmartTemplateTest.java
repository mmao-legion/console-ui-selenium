package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
import com.legion.pages.core.ConsoleSmartTemplatePage;
import com.legion.pages.core.schedule.ConsoleEditShiftPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.*;

import static java.time.LocalDate.now;

public class SmartTemplateTest extends TestBase {
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
            smartTemplatePage = pageFactory.createSmartTemplatePage();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the shift pattern that converted by fixed staffing rule can display in smart template from the start week")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyFixedStaffingRuleShiftsCanShowInSmartTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            goToSchedulePageScheduleTab();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            BasePage basePage = new BasePage();
            String activeWeek = basePage.getActiveWeekText();
            String startOfWeek = activeWeek.split(" ")[3] + " " + activeWeek.split(" ")[4];
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            goToSmartTemplatePage();
            String fixedStaffingRuleName1 = "Cafe pattern Day 6";
            //Group by pattern
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
            //Check the fixed staffing rule shift should show
            ArrayList<HashMap<String,String>> patterns = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            List<String> patternNames = new ArrayList<>();
            for (HashMap<String, String> pattern : patterns) {
                patternNames.add(pattern.get("optionName"));
            }
            if (patternNames.contains(fixedStaffingRuleName1.toLowerCase())){
//                    && patternNames.contains(fixedStaffingRuleName2.toLowerCase())
//                    && patternNames.contains(fixedStaffingRuleName3.toLowerCase()) ) {
                SimpleUtils.pass("The fixed staffing rule shifts can show in smart template! ");
            } else
                SimpleUtils.fail("Fixed staffing rule shifts fail to show in smart template! ", false);

            //Check the shifts can be selected as a group
            smartTemplatePage.clickOnEditBtn();
            scheduleShiftTablePage.expandOnlyOneGroup(fixedStaffingRuleName1);
            HashSet<Integer> indexes = new HashSet<>();
            indexes.add(0);
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            String action = "Edit";
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            editShiftPage.verifyTheTitleOfEditShiftsWindow(3, startOfWeek);
            editShiftPage.clickOnCancelButton();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify all shifts that created from shift pattern can display in smart template")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyShiftPatternShiftsCanShowInSmartTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            goToSchedulePageScheduleTab();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            BasePage basePage = new BasePage();
            String activeWeek = basePage.getActiveWeekText();
            String startOfWeek = activeWeek.split(" ")[3] + " " + activeWeek.split(" ")[4];
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            goToSmartTemplatePage();
            smartTemplatePage.clickOnResetBtn();
            String shiftPatternName1 = "GM SP1";
            String shiftPatternName2 = "EM SP1";
            String shiftPatternName3 = "TM SP1";
            //Group by pattern
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
            //Check the fixed staffing rule shift should show
            ArrayList<HashMap<String,String>> patterns = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            List<String> patternNames = new ArrayList<>();
            for (HashMap<String, String> pattern : patterns) {
                patternNames.add(pattern.get("optionName"));
            }
            if (patternNames.contains(shiftPatternName1.toLowerCase())
                    && patternNames.contains(shiftPatternName2.toLowerCase())
                    && patternNames.contains(shiftPatternName3.toLowerCase()) ) {
                SimpleUtils.pass("The shift pattern shifts can show in smart template! ");
            } else
                SimpleUtils.fail("Shift pattern shifts fail to show in smart template! ", false);

            //Check the shifts can be selected as a group
            smartTemplatePage.clickOnEditBtn();
            scheduleShiftTablePage.expandOnlyOneGroup(shiftPatternName1);
            HashSet<Integer> indexes = new HashSet<>();
            indexes.add(0);
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            String action = "Edit";
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            editShiftPage.verifyTheTitleOfEditShiftsWindow(7, startOfWeek);
            editShiftPage.clickOnCancelButton();
            scheduleShiftTablePage.expandSpecificCountGroup(4);
            scheduleShiftTablePage.expandOnlyOneGroup(shiftPatternName3);
            indexes.add(0);
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            editShiftPage.verifyTheTitleOfEditShiftsWindow(10, startOfWeek);
            editShiftPage.clickOnCancelButton();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the recurring shifts can display in smart template")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyRecurringShiftCanDisplayInSmartTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            goToSchedulePageScheduleTab();
            BasePage basePage = new BasePage();
            String activeWeek = basePage.getActiveWeekText();
            String startOfWeek = activeWeek.split(" ")[3] + " " + activeWeek.split(" ")[4];
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            goToSmartTemplatePage();
            smartTemplatePage.clickOnResetBtn();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            ArrayList<HashMap<String,String>> workRoles = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
//            String workRole1 = workRoles.get(0).get("optionName");
            String workRole1 = "Cafe";
            String shiftName = "RecurringShifts";
            String shiftNote = "RecurringShiftsNote";
            String startTime = "9:00pm";
            String endTime = "11:00pm";
            smartTemplatePage.clickOnEditBtn();
            //Create one group of recurring shifts
//            List<HashMap<String, String>> segments = new ArrayList<>();
//            HashMap<String, String> segment = new HashMap<>();
//            segment.put("startTime", "8:00am");
//            segment.put("endTime", "11:00am");
//            segment.put("workRole", workRoles.get(0).get("optionName"));
//            segments.add(new HashMap<>(segment));
//            segment.clear();
//            segment.put("startTime", "11:00am");
//            segment.put("endTime", "1:00pm");
//            segment.put("workRole", workRoles.get(1).get("optionName"));
//            segments.add(new HashMap<>(segment));
//            segment.clear();
//            segment.put("startTime", "1:00pm");
//            segment.put("endTime", "3:00pm");
//            segment.put("workRole", workRoles.get(2).get("optionName"));
//            segments.add(new HashMap<>(segment));
//            smartTemplatePage.createShiftsWithWorkRoleTransition(segments, shiftName, 2, Arrays.asList(0,1,2,3,4,5),
//                    ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), shiftNote, "", true);
            smartTemplatePage.createShiftsWithOutWorkRoleTransition(workRole1, shiftName, "", startTime, endTime, 2,
                    Arrays.asList(0,1,2,3,4,5),
                    ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), shiftNote, "", true);
            scheduleMainPage.saveSchedule();

            //Verify the recurring shifts can display in smart template in current week
            //Group by pattern
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
            //Check the fixed staffing rule shift should show
            ArrayList<HashMap<String,String>> patterns = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            List<String> patternNames = new ArrayList<>();
            for (HashMap<String, String> pattern : patterns) {
                patternNames.add(pattern.get("optionName"));
            }
            if (patternNames.contains(shiftName.toLowerCase())) {
                SimpleUtils.pass("The shift pattern shifts can show in smart template! ");
            } else
                SimpleUtils.fail("Shift pattern shifts fail to show in smart template! ", false);

            //Check the shifts can be selected as a group
            smartTemplatePage.clickOnEditBtn();
            scheduleShiftTablePage.expandOnlyOneGroup(shiftName);
            HashSet<Integer> indexes = new HashSet<>();
            indexes.add(0);
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            String action = "Edit";
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            editShiftPage.verifyTheTitleOfEditShiftsWindow(12, startOfWeek);
            editShiftPage.clickOnCancelButton();

            //Verify the recurring shifts cannot display in smart template before start week
            //Go to previous week
            smartTemplatePage.clickOnBackBtn();
            smartTemplatePage.clickOnBackBtn();
            scheduleCommonPage.navigateToPreviousWeek();
            isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            goToSmartTemplatePage();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
            patterns.clear();
            patternNames.clear();
            patterns = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            for (HashMap<String, String> pattern : patterns) {
                patternNames.add(pattern.get("optionName"));
            }
            if (!patternNames.contains(shiftName.toLowerCase())) {
                SimpleUtils.pass("The shift pattern shifts should not show in previous smart template week! ");
            } else
                SimpleUtils.fail("Shift pattern shifts show in previous smart template week! ", false);

            //Verify the recurring shifts can display in smart template in future weeks
            smartTemplatePage.clickOnBackBtn();
            smartTemplatePage.clickOnBackBtn();
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            activeWeek = basePage.getActiveWeekText();
            startOfWeek = activeWeek.split(" ")[3] + " " + activeWeek.split(" ")[4];
            isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            goToSmartTemplatePage();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
            patterns = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            for (HashMap<String, String> pattern : patterns) {
                patternNames.add(pattern.get("optionName"));
            }
            if (patternNames.contains(shiftName.toLowerCase())) {
                SimpleUtils.pass("The shift pattern shifts can show in smart template! ");
            } else
                SimpleUtils.fail("Shift pattern shifts fail to show in smart template! ", false);
            smartTemplatePage.clickOnEditBtn();
            scheduleShiftTablePage.expandOnlyOneGroup(shiftName);
            indexes.add(0);
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            editShiftPage.verifyTheTitleOfEditShiftsWindow(12, startOfWeek);
            editShiftPage.clickOnCancelButton();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the non-recurring shifts can display in smart template")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyNonRecurringShiftCanDisplayInSmartTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            goToSchedulePageScheduleTab();
            BasePage basePage = new BasePage();
            String activeWeek = basePage.getActiveWeekText();
            String startOfWeek = activeWeek.split(" ")[3] + " " + activeWeek.split(" ")[4];
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            goToSmartTemplatePage();
            smartTemplatePage.clickOnResetBtn();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            ArrayList<HashMap<String,String>> workRoles = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
//            String workRole1 = workRoles.get(0).get("optionName");
            String workRole1 = "Cafe";
            String shiftName = "No Pattern";
            String shiftNote = "NonRecurringShiftsNote";
            String startTime = "9:00pm";
            String endTime = "11:00pm";
            smartTemplatePage.clickOnEditBtn();
            //Create one group of non-recurring shifts
//            List<HashMap<String, String>> segments = new ArrayList<>();
//            HashMap<String, String> segment = new HashMap<>();
//            segment.put("startTime", "8:00am");
//            segment.put("endTime", "11:00am");
//            segment.put("workRole", workRoles.get(0).get("optionName"));
//            segments.add(new HashMap<>(segment));
//            segment.clear();
//            segment.put("startTime", "11:00am");
//            segment.put("endTime", "1:00pm");
//            segment.put("workRole", workRoles.get(1).get("optionName"));
//            segments.add(new HashMap<>(segment));
//            segment.clear();
//            segment.put("startTime", "1:00pm");
//            segment.put("endTime", "3:00pm");
//            segment.put("workRole", workRoles.get(2).get("optionName"));
//            segments.add(new HashMap<>(segment));
//            smartTemplatePage.createShiftsWithWorkRoleTransition(segments, shiftName, 2, Arrays.asList(0,1,2,3,4,5),
//                    ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), shiftNote, "", false);
            smartTemplatePage.createShiftsWithOutWorkRoleTransition(workRole1, shiftName, "", startTime, endTime, 2, Arrays.asList(0,1,2,3,4,5),
                    ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), shiftNote, "", false);
            scheduleMainPage.saveSchedule();

            //Verify the recurring shifts can display in smart template in current week
            //Group by pattern
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
            //Check the fixed staffing rule shift should show
            ArrayList<HashMap<String,String>> patterns = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            List<String> patternNames = new ArrayList<>();
            for (HashMap<String, String> pattern : patterns) {
                patternNames.add(pattern.get("optionName"));
            }
            if (patternNames.contains(shiftName.toLowerCase())) {
                SimpleUtils.pass("The shift pattern shifts can show in smart template! ");
            } else
                SimpleUtils.fail("Shift pattern shifts fail to show in smart template! ", false);

            //Check the shifts can be selected as a group
            smartTemplatePage.clickOnEditBtn();
            scheduleShiftTablePage.expandOnlyOneGroup(shiftName);
            HashSet<Integer> indexes = new HashSet<>();
            indexes.add(0);
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            String action = "Edit";
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            editShiftPage.verifyTheTitleOfEditShiftsWindow(1, startOfWeek);
            editShiftPage.clickOnCancelButton();

            //Verify the recurring shifts cannot display in smart template before start week
            //Go to previous week
            smartTemplatePage.clickOnBackBtn();
            smartTemplatePage.clickOnBackBtn();
//            scheduleCommonPage.navigateToPreviousWeek();
//            isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
//            if(isActiveWeekGenerated){
//                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
//            }
//            goToSmartTemplatePage();
//            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
//            patterns.clear();
//            patternNames.clear();
//            patterns = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
//            for (HashMap<String, String> pattern : patterns) {
//                patternNames.add(pattern.get("optionName"));
//            }
//            if (!patternNames.contains(shiftName.toLowerCase())) {
//                SimpleUtils.pass("The shift pattern shifts should not show in previous smart template week! ");
//            } else
//                SimpleUtils.fail("Shift pattern shifts show in previous smart template week! ", false);

            //Verify the recurring shifts can display in smart template in future weeks
//            smartTemplatePage.clickOnBackBtn();
//            smartTemplatePage.clickOnBackBtn();
            scheduleCommonPage.navigateToNextWeek();
            activeWeek = basePage.getActiveWeekText();
            startOfWeek = activeWeek.split(" ")[3] + " " + activeWeek.split(" ")[4];
            isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            goToSmartTemplatePage();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
            patterns = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            for (HashMap<String, String> pattern : patterns) {
                patternNames.add(pattern.get("optionName"));
            }
            if (patternNames.contains(shiftName.toLowerCase())) {
                SimpleUtils.pass("The shift pattern shifts not show in future week smart template! ");
            } else
                SimpleUtils.fail("Shift pattern shifts should not show in future week smart template! ", false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the shift segment on edit shift page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheShiftSegmentOnEditShiftPageAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            goToSchedulePageScheduleTab();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            goToSmartTemplatePage();
            smartTemplatePage.clickOnResetBtn();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            ArrayList<HashMap<String,String>> workRoles = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            String shiftName = "No Pattern";
            String shiftNote = "CreatedShiftsNote";
            boolean recurringShift = false;
            LocalDate date = now();
            String dayOfWeek = date.getDayOfWeek().toString();
            if (dayOfWeek.equalsIgnoreCase("Monday")
                    ||dayOfWeek.equalsIgnoreCase("Tuesday")
                    ||dayOfWeek.equalsIgnoreCase("Wednesday") ) {
                shiftName = "NewCreatedShifts";
                recurringShift = true;
            }
            smartTemplatePage.clickOnEditBtn();
            //Create one group of recurring shifts
            List<HashMap<String, String>> segments = new ArrayList<>();
            HashMap<String, String> segment = new HashMap<>();
            segment.put("startTime", "8:00am");
            segment.put("endTime", "11:00am");
            segment.put("workRole", workRoles.get(0).get("optionName"));
            segments.add(new HashMap<>(segment));
            segment.clear();
            segment.put("startTime", "11:00am");
            segment.put("endTime", "1:00pm");
            segment.put("workRole", workRoles.get(1).get("optionName"));
            segments.add(new HashMap<>(segment));
            segment.clear();
            segment.put("startTime", "1:00pm");
            segment.put("endTime", "3:00pm");
            segment.put("workRole", workRoles.get(2).get("optionName"));
            segments.add(new HashMap<>(segment));
            smartTemplatePage.createShiftsWithWorkRoleTransition(segments, shiftName, 2, Arrays.asList(0,1,2,3,4,5),
                    ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), shiftNote, "", false);
            scheduleMainPage.saveSchedule();

            //Group by pattern
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
            //Edit the new created shift
            smartTemplatePage.clickOnEditBtn();
            scheduleShiftTablePage.expandOnlyOneGroup(shiftName);


            HashSet<Integer> indexes = new HashSet<>();
            indexes.add(0);
            String action = "Edit";
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);

            // add one more segment
            segments = new ArrayList<>();
            segment.put("startTime", "3:00pm");
            segment.put("endTime", "4:00pm");
            segment.put("workRole", workRoles.get(0).get("optionName"));
            segments.add(new HashMap<>(segment));
            newShiftPage.addShiftSegments(segments, false);
            newShiftPage.clickOnCreateOrNextBtn();
            editShiftPage.clickOnUpdateAnywayButton();
            scheduleMainPage.saveSchedule();
            List<HashMap<String, String>> segmentInfo = scheduleShiftTablePage.getSegmentFromInfoPopupByIndex(0);
            SimpleUtils.assertOnFail("This shift should has 4 segments",
                    segmentInfo.size() ==4, false);
            SimpleUtils.assertOnFail("This shift's segment shift time is incorrectly, the actual is: "+segmentInfo.get(3).get("shiftTime"),
                    segmentInfo.get(3).get("shiftTime").equalsIgnoreCase("3:00 PM - 4:00 PM") , false);
            SimpleUtils.assertOnFail("This shift's segment work role is incorrectly, the actual is: "+segmentInfo.get(3).get("workRole"),
                    segmentInfo.get(3).get("workRole").equalsIgnoreCase(workRoles.get(0).get("optionName")) , false);

            //delete segments
            smartTemplatePage.clickOnEditBtn();
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            newShiftPage.removeSpecificCountSegments(2);
            newShiftPage.clickOnCreateOrNextBtn();
            editShiftPage.clickOnUpdateAnywayButton();
            scheduleMainPage.saveSchedule();
            segmentInfo = scheduleShiftTablePage.getSegmentFromInfoPopupByIndex(0);
            SimpleUtils.assertOnFail("This shift should has 2 segments",
                    segmentInfo.size() ==2, false);
            SimpleUtils.assertOnFail("This shift's segment shift time is incorrectly, the actual is: "+segmentInfo.get(1).get("shiftTime"),
                    segmentInfo.get(1).get("shiftTime").equalsIgnoreCase("11:00 AM - 1:00 PM") , false);
            SimpleUtils.assertOnFail("This shift's segment work role is incorrectly, the actual is: "+segmentInfo.get(1).get("workRole"),
                    segmentInfo.get(1).get("workRole").equalsIgnoreCase(workRoles.get(1).get("optionName")) , false);

            //Check the start time and end time of every segment
            smartTemplatePage.clickOnEditBtn();
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            newShiftPage.clickOnAddSegmentBtnOnNewOrEditPage();
            List<HashMap<String, String>> allSegmentsInfo = newShiftPage.getAllSegmentsOnNewOrEditPage();
            String newSegmentStartTime = allSegmentsInfo.get(allSegmentsInfo.size()-1).get("startTime");
            String previousSegmentEndTime = allSegmentsInfo.get(allSegmentsInfo.size()-2).get("endTime");
            SimpleUtils.assertOnFail("This shift's segment work role is incorrectly, the actual is: "+newSegmentStartTime +" and "+previousSegmentEndTime,
                    newSegmentStartTime.equalsIgnoreCase(previousSegmentEndTime) && newSegmentStartTime.equalsIgnoreCase("01:00 PM") ,
                    false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify breaks on edit shift page for recurring shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyBreaksOnEditShiftPageForRecurringShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            goToSchedulePageScheduleTab();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            goToSmartTemplatePage();
            smartTemplatePage.clickOnResetBtn();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            ArrayList<HashMap<String,String>> workRoles = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
//            String workRole1 = workRoles.get(0).get("optionName");
            String workRole1 = "Cafe";
            String shiftName = "RecurringShifts";
            String shiftNote = "RecurringShiftsNote";
            String startTime = "9:00am";
            String endTime = "5:00pm";
            smartTemplatePage.clickOnEditBtn();
            //Create one group of recurring shifts
            smartTemplatePage.createShiftsWithOutWorkRoleTransition(workRole1, shiftName, "", startTime, endTime, 2,
                    Arrays.asList(0,1,2,3,4,5),
                    ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), shiftNote, "", true);
            
            scheduleMainPage.saveSchedule();

            //Verify the recurring shifts can display in smart template in current week
            //Group by pattern
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());

            //Edit the recurring shift pattern
            smartTemplatePage.clickOnEditBtn();
            scheduleShiftTablePage.expandOnlyOneGroup(shiftName);
            HashSet<Integer> indexes = new HashSet<>();
            indexes.add(0);
            String action = "Edit";
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            //Check meal and rest break on recurring edit shift page
            editShiftPage.checkOrUncheckAutomaticallyScheduleOptimizedBreak(false);
            Map<String, String> breakTimesOnSingleEditPage = editShiftPage.getMealBreakTimes().get(0);
            String breakStartTime = breakTimesOnSingleEditPage.get("mealStartTime");
            String breakEndTime = breakTimesOnSingleEditPage.get("mealEndTime");
            SimpleUtils.assertOnFail("The actual is: "+ (breakStartTime+ " "+breakEndTime),
                    "12:45 PM".equals(breakStartTime)
                            && "01:15 PM".equals(breakEndTime), false);
            Map<String, String> restTimesOnSingleEditPage = editShiftPage.getRestBreakTimes().get(0);
            String restStartTime = restTimesOnSingleEditPage.get("restStartTime");
            String restEndTime = restTimesOnSingleEditPage.get("restEndTime");
            SimpleUtils.assertOnFail("The actual is: "+ (restStartTime+ " "+restEndTime),
                    "11:15 AM".equals(restStartTime)
                            && "11:30 AM".equals(restEndTime), false);

            //Delete meal and rest break for recurring shift
            editShiftPage.removeAllRestBreaks();
            editShiftPage.removeAllMealBreaks();
            editShiftPage.clickOnUpdateButton();
            editShiftPage.clickOnUpdateAnywayButton();
            scheduleMainPage.saveSchedule();
            smartTemplatePage.clickOnEditBtn();
            //Check the meal and rest break been removed successfully
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            int mealBreakCount = editShiftPage.getMealBreakCount();
            int restBreakCount = editShiftPage.getRestBreakCount();
            SimpleUtils.assertOnFail("All breaks should been added, but actual there are "+mealBreakCount+" meal breaks and "
                    +restBreakCount+" rest breaks", mealBreakCount == 0
                    && restBreakCount==0, false);

            //Add meal and rest break for recurring shift
            String mealBreakStartTime = "10:00 AM";
            String mealBreakEndTime = "10:20 AM";
            String restBreakStartTime = "11:00 AM";
            String restBreakEndTime = "11:30 AM";
            editShiftPage.clickOnAddMealBreakButton();
            editShiftPage.clickOnAddRestBreakButton();
            editShiftPage.inputMealBreakTimes(mealBreakStartTime, mealBreakEndTime, 0);
            editShiftPage.inputRestBreakTimes(restBreakStartTime, restBreakEndTime, 0);
            editShiftPage.clickOnUpdateButton();
            editShiftPage.clickOnUpdateAnywayButton();
            scheduleMainPage.saveSchedule();
            smartTemplatePage.clickOnEditBtn();
            //Edit the shift again and check the breaks
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            SimpleUtils.assertOnFail("Edit Shifts window failed to load!", editShiftPage.isEditShiftWindowLoaded(), false);
            //All breaks been added successfully
            mealBreakCount = editShiftPage.getMealBreakCount();
            restBreakCount = editShiftPage.getRestBreakCount();
            SimpleUtils.assertOnFail("All breaks should been added, but actual there are "+mealBreakCount+" meal breaks and "
                    +restBreakCount+" rest breaks", mealBreakCount == 1
                    && restBreakCount==1, false);

            //Verify the meal and rest breaks time are display correctly in Edit column
            breakTimesOnSingleEditPage = editShiftPage.getMealBreakTimes().get(0);
            breakStartTime = breakTimesOnSingleEditPage.get("mealStartTime");
            breakEndTime = breakTimesOnSingleEditPage.get("mealEndTime");
            SimpleUtils.assertOnFail("The expected break time is: "+mealBreakStartTime+ " "+ mealBreakEndTime
                            + ". The actual is: "+ (breakStartTime+ " "+breakEndTime),
                    mealBreakStartTime.equals(breakStartTime)
                            && mealBreakEndTime.equals(breakEndTime), false);

            //Edit meal and rest break for recurring shift
            mealBreakStartTime = "02:00 PM";
            mealBreakEndTime = "02:30 PM";
            restBreakStartTime = "01:00 PM";
            restBreakEndTime = "01:15 PM";
            editShiftPage.inputMealBreakTimes(mealBreakStartTime, mealBreakEndTime, 0);
            editShiftPage.inputRestBreakTimes(restBreakStartTime, restBreakEndTime, 0);
            editShiftPage.clickOnUpdateButton();
            editShiftPage.clickOnUpdateAnywayButton();
            scheduleMainPage.saveSchedule();
            smartTemplatePage.clickOnEditBtn();
            //Edit the shift again and check the breaks
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            SimpleUtils.assertOnFail("Edit Shifts window failed to load!", editShiftPage.isEditShiftWindowLoaded(), false);
            //All breaks been added successfully
            mealBreakCount = editShiftPage.getMealBreakCount();
            restBreakCount = editShiftPage.getRestBreakCount();
            SimpleUtils.assertOnFail("All breaks should been added, but actual there are "+mealBreakCount+" meal breaks and "
                    +restBreakCount+" rest breaks", mealBreakCount == 1
                    && restBreakCount==1, false);

            //Verify the meal and rest breaks time are display correctly in Edit column
            breakTimesOnSingleEditPage = editShiftPage.getMealBreakTimes().get(0);
            breakStartTime = breakTimesOnSingleEditPage.get("mealStartTime");
            breakEndTime = breakTimesOnSingleEditPage.get("mealEndTime");
            SimpleUtils.assertOnFail("The expected break time is: "+mealBreakStartTime+ " "+ mealBreakEndTime
                            + ". The actual is: "+ (breakStartTime+ " "+breakEndTime),
                    mealBreakStartTime.equals(breakStartTime)
                            && mealBreakEndTime.equals(breakEndTime), false);


        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify breaks on edit shift page for non recurring shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyBreaksOnEditShiftPageForNonRecurringShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            goToSchedulePageScheduleTab();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            goToSmartTemplatePage();
            smartTemplatePage.clickOnResetBtn();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            ArrayList<HashMap<String,String>> workRoles = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            smartTemplatePage.clickOnEditBtn();
            //Create one group of recurring shifts
//            String workRoleOfNonRecurring = workRoles.get(1).get("optionName");
            String shiftNameOfNonRecurring = "No Pattern";
            String workRoleOfNonRecurring = "Cafe";
            String shiftNoteOfNonRecurring = "NonRecurringShiftsNote";
            String startTimeOfNonRecurring = "9:00am";
            String endTimeOfNonRecurring = "5:00pm";
            //Create one group of non-recurring shifts
            smartTemplatePage.createShiftsWithOutWorkRoleTransition(workRoleOfNonRecurring, shiftNameOfNonRecurring, "",
                    startTimeOfNonRecurring, endTimeOfNonRecurring, 2, Arrays.asList(0,1,2,3,4,5),
                    ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), shiftNoteOfNonRecurring, "", false);
            scheduleMainPage.saveSchedule();

            //Verify the recurring shifts can display in smart template in current week
            //Group by pattern
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());

            //Edit the non-recurring shift pattern
            smartTemplatePage.clickOnEditBtn();
            scheduleShiftTablePage.expandOnlyOneGroup(shiftNameOfNonRecurring);
            HashSet<Integer> indexes = new HashSet<>();
            indexes.add(0);
            String action = "Edit";
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            //Check meal and rest break on recurring edit shift page
            editShiftPage.checkOrUncheckAutomaticallyScheduleOptimizedBreak(false);
            Map<String, String> breakTimesOnSingleEditPage = editShiftPage.getMealBreakTimes().get(0);
            String breakStartTime = breakTimesOnSingleEditPage.get("mealStartTime");
            String breakEndTime = breakTimesOnSingleEditPage.get("mealEndTime");
            SimpleUtils.assertOnFail("The actual is: "+ (breakStartTime+ " "+breakEndTime),
                    "12:45 PM".equals(breakStartTime)
                            && "01:15 PM".equals(breakEndTime), false);
            Map<String, String> restTimesOnSingleEditPage = editShiftPage.getRestBreakTimes().get(0);
            String restStartTime = restTimesOnSingleEditPage.get("restStartTime");
            String restEndTime = restTimesOnSingleEditPage.get("restEndTime");
            SimpleUtils.assertOnFail("The actual is: "+ (restStartTime+ " "+restEndTime),
                    "11:15 AM".equals(restStartTime)
                            && "11:30 AM".equals(restEndTime), false);

            //Delete meal and rest break for non-recurring shift
            editShiftPage.removeAllRestBreaks();
            editShiftPage.removeAllMealBreaks();
            editShiftPage.clickOnUpdateButton();
            editShiftPage.clickOnUpdateAnywayButton();
            scheduleMainPage.saveSchedule();
            smartTemplatePage.clickOnEditBtn();
            //Check the meal and rest break been removed successfully
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            int mealBreakCount = editShiftPage.getMealBreakCount();
            int restBreakCount = editShiftPage.getRestBreakCount();
            SimpleUtils.assertOnFail("All breaks should been added, but actual there are "+mealBreakCount+" meal breaks and "
                    +restBreakCount+" rest breaks", mealBreakCount == 0
                    && restBreakCount==0, false);

            //Add meal and rest break for non-recurring shift
            String mealBreakStartTime = "10:00 AM";
            String mealBreakEndTime = "10:20 AM";
            String restBreakStartTime = "11:00 AM";
            String restBreakEndTime = "11:30 AM";
            editShiftPage.clickOnAddMealBreakButton();
            editShiftPage.clickOnAddRestBreakButton();
            editShiftPage.inputMealBreakTimes(mealBreakStartTime, mealBreakEndTime, 0);
            editShiftPage.inputRestBreakTimes(restBreakStartTime, restBreakEndTime, 0);
            editShiftPage.clickOnUpdateButton();
            editShiftPage.clickOnUpdateAnywayButton();
            scheduleMainPage.saveSchedule();
            smartTemplatePage.clickOnEditBtn();
            //Edit the shift again and check the breaks
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            SimpleUtils.assertOnFail("Edit Shifts window failed to load!", editShiftPage.isEditShiftWindowLoaded(), false);
            //All breaks been added successfully
            mealBreakCount = editShiftPage.getMealBreakCount();
            restBreakCount = editShiftPage.getRestBreakCount();
            SimpleUtils.assertOnFail("All breaks should been added, but actual there are "+mealBreakCount+" meal breaks and "
                    +restBreakCount+" rest breaks", mealBreakCount == 1
                    && restBreakCount==1, false);

            //Verify the meal and rest breaks time are display correctly in Edit column
            breakTimesOnSingleEditPage = editShiftPage.getMealBreakTimes().get(0);
            breakStartTime = breakTimesOnSingleEditPage.get("mealStartTime");
            breakEndTime = breakTimesOnSingleEditPage.get("mealEndTime");
            SimpleUtils.assertOnFail("The expected break time is: "+mealBreakStartTime+ " "+ mealBreakEndTime
                            + ". The actual is: "+ (breakStartTime+ " "+breakEndTime),
                    mealBreakStartTime.equals(breakStartTime)
                            && mealBreakEndTime.equals(breakEndTime), false);

            //Edit meal and rest break for non-recurring shift
            mealBreakStartTime = "02:00 PM";
            mealBreakEndTime = "02:30 PM";
            restBreakStartTime = "01:00 PM";
            restBreakEndTime = "01:15 PM";
            editShiftPage.inputMealBreakTimes(mealBreakStartTime, mealBreakEndTime, 0);
            editShiftPage.inputRestBreakTimes(restBreakStartTime, restBreakEndTime, 0);
            editShiftPage.clickOnUpdateButton();
            editShiftPage.clickOnUpdateAnywayButton();
            scheduleMainPage.saveSchedule();
            smartTemplatePage.clickOnEditBtn();
            //Edit the shift again and check the breaks
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            SimpleUtils.assertOnFail("Edit Shifts window failed to load!", editShiftPage.isEditShiftWindowLoaded(), false);
            //All breaks been added successfully
            mealBreakCount = editShiftPage.getMealBreakCount();
            restBreakCount = editShiftPage.getRestBreakCount();
            SimpleUtils.assertOnFail("All breaks should been added, but actual there are "+mealBreakCount+" meal breaks and "
                    +restBreakCount+" rest breaks", mealBreakCount == 1
                    && restBreakCount==1, false);

            //Verify the meal and rest breaks time are display correctly in Edit column
            breakTimesOnSingleEditPage = editShiftPage.getMealBreakTimes().get(0);
            breakStartTime = breakTimesOnSingleEditPage.get("mealStartTime");
            breakEndTime = breakTimesOnSingleEditPage.get("mealEndTime");
            SimpleUtils.assertOnFail("The expected break time is: "+mealBreakStartTime+ " "+ mealBreakEndTime
                            + ". The actual is: "+ (breakStartTime+ " "+breakEndTime),
                    mealBreakStartTime.equals(breakStartTime)
                            && mealBreakEndTime.equals(breakEndTime), false);


        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the shift name and note can be add, edit or delete for non recurring shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyShiftNameAndNotesForNonRecurringShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            goToSchedulePageScheduleTab();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            goToSmartTemplatePage();
            smartTemplatePage.clickOnResetBtn();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            ArrayList<HashMap<String,String>> workRoles = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            smartTemplatePage.clickOnEditBtn();
            //Create one group of non recurring shifts
//            String workRoleOfNonRecurring = workRoles.get(1).get("optionName");
            String workRoleOfNonRecurring = "Cafe";
            String shiftName = "No Pattern";
            String shiftNote = "NonRecurringShiftsNote";
            String startTimeOfNonRecurring = "9:00am";
            String endTimeOfNonRecurring = "5:00pm";
            //Create one group of non-recurring shifts
            smartTemplatePage.createShiftsWithOutWorkRoleTransition(workRoleOfNonRecurring, shiftName, "",
                    startTimeOfNonRecurring, endTimeOfNonRecurring, 2, Arrays.asList(0,1,2,3,4,5),
                    ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), shiftNote, "", false);
            scheduleMainPage.saveSchedule();
            //Group by pattern
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
            scheduleShiftTablePage.expandOnlyOneGroup(shiftName);
            //Check the shift name and notes been added successfully
            List<String> shiftInfo1 = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
            SimpleUtils.assertOnFail("Shift Name is not updated!", shiftName.equalsIgnoreCase(shiftInfo1.get(9)), false);
            SimpleUtils.assertOnFail("Shift Notes is not updated!", shiftNote.equalsIgnoreCase(shiftInfo1.get(10)), false);

            //Edit the recurring shift pattern
            smartTemplatePage.clickOnEditBtn();
            HashSet<Integer> indexes = new HashSet<>();
            indexes.add(0);
            String action = "Edit";
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            //Edit shift name and notes for shifts
            shiftName = "NonRecurringShifts - Updated";
            shiftNote = "NonRecurringShiftsNote - Updated";
            editShiftPage.inputShiftName(shiftName);
            editShiftPage.inputShiftNotes(shiftNote);
            editShiftPage.clickOnUpdateButton();
            editShiftPage.clickOnUpdateAnywayButton();
            scheduleMainPage.saveSchedule();
            shiftInfo1 = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
            SimpleUtils.assertOnFail("Shift Name is not updated!", shiftName.equalsIgnoreCase(shiftInfo1.get(9)), false);
            SimpleUtils.assertOnFail("Shift Notes is not updated!", shiftNote.equalsIgnoreCase(shiftInfo1.get(10)), false);
            //Delete shift name and notes for shifts
            smartTemplatePage.clickOnEditBtn();
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            shiftName = "";
            shiftNote = "";
            editShiftPage.inputShiftName(shiftName);
            editShiftPage.inputShiftNotes(shiftNote);
            editShiftPage.clickOnUpdateButton();
            editShiftPage.clickOnUpdateAnywayButton();
            scheduleMainPage.saveSchedule();
            shiftInfo1 = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
            SimpleUtils.assertOnFail("Shift Name is not updated!", shiftName.equalsIgnoreCase(shiftInfo1.get(9)), false);
            SimpleUtils.assertOnFail("Shift Notes is not updated!", shiftNote.equalsIgnoreCase(shiftInfo1.get(10)), false);
            //Add shift name and notes for shifts

            smartTemplatePage.clickOnEditBtn();
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            shiftName = "New shift name";
            shiftNote = "New shift notes";
            editShiftPage.inputShiftName(shiftName);
            editShiftPage.inputShiftNotes(shiftNote);
            editShiftPage.clickOnUpdateButton();
            editShiftPage.clickOnUpdateAnywayButton();
            scheduleMainPage.saveSchedule();
            shiftInfo1 = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
            SimpleUtils.assertOnFail("Shift Name is not updated!", shiftName.equalsIgnoreCase(shiftInfo1.get(9)), false);
            SimpleUtils.assertOnFail("Shift Notes is not updated!", shiftNote.equalsIgnoreCase(shiftInfo1.get(10)), false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the shift name and note can be add, edit or delete for recurring shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyShiftNameAndNotesForRecurringShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            goToSchedulePageScheduleTab();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            goToSmartTemplatePage();
            smartTemplatePage.clickOnResetBtn();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            ArrayList<HashMap<String,String>> workRoles = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
//            String workRole1 = workRoles.get(0).get("optionName");
            String workRole1 = "Cafe";
            String shiftName = "RecurringShifts";
            String shiftNote = "RecurringShiftsNote";
            String startTime = "9:00am";
            String endTime = "5:00pm";
            smartTemplatePage.clickOnEditBtn();
            //Create one group of recurring shifts
            smartTemplatePage.createShiftsWithOutWorkRoleTransition(workRole1, shiftName, "", startTime, endTime, 2,
                    Arrays.asList(0,1,2,3,4,5),
                    ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), shiftNote, "", true);

            scheduleMainPage.saveSchedule();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
            scheduleShiftTablePage.expandOnlyOneGroup(shiftName);
            //Check the shift name and notes been added successfully
            List<String> shiftInfo1 = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
            SimpleUtils.assertOnFail("Shift Name is not updated!", shiftName.equalsIgnoreCase(shiftInfo1.get(9)), false);
            SimpleUtils.assertOnFail("Shift Notes is not updated!", shiftNote.equalsIgnoreCase(shiftInfo1.get(10)), false);
            //Verify the recurring shifts can display in smart template in current week
            //Group by pattern
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());

            //Edit the recurring shift pattern
            smartTemplatePage.clickOnEditBtn();
//            scheduleShiftTablePage.expandOnlyOneGroup(shiftName);
            HashSet<Integer> indexes = new HashSet<>();
            indexes.add(0);
            String action = "Edit";
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            //Edit shift name and notes for shifts
            shiftName = "RecurringShifts - Updated";
            shiftNote = "RecurringShiftsNote - Updated";
            editShiftPage.inputShiftName(shiftName);
            editShiftPage.inputShiftNotes(shiftNote);
            editShiftPage.clickOnUpdateButton();
            editShiftPage.clickOnUpdateAnywayButton();
            scheduleMainPage.saveSchedule();
            shiftInfo1 = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
            SimpleUtils.assertOnFail("Shift Name is not updated!", shiftName.equalsIgnoreCase(shiftInfo1.get(9)), false);
            SimpleUtils.assertOnFail("Shift Notes is not updated!", shiftNote.equalsIgnoreCase(shiftInfo1.get(10)), false);
            //Delete shift name and notes for shifts
            smartTemplatePage.clickOnEditBtn();
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            shiftName = "";
            shiftNote = "";
            editShiftPage.inputShiftName(shiftName);
            editShiftPage.inputShiftNotes(shiftNote);
            editShiftPage.clickOnUpdateButton();
            editShiftPage.clickOnUpdateAnywayButton();
            scheduleMainPage.saveSchedule();
            shiftInfo1 = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
            SimpleUtils.assertOnFail("Shift Name is not updated!", shiftName.equalsIgnoreCase(shiftInfo1.get(9)), false);
            SimpleUtils.assertOnFail("Shift Notes is not updated!", shiftNote.equalsIgnoreCase(shiftInfo1.get(10)), false);
            //Add shift name and notes for shifts

            smartTemplatePage.clickOnEditBtn();
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            shiftName = "New shift name";
            shiftNote = "New shift notes";
            editShiftPage.inputShiftName(shiftName);
            editShiftPage.inputShiftNotes(shiftNote);
            editShiftPage.clickOnUpdateButton();
            editShiftPage.clickOnUpdateAnywayButton();
            scheduleMainPage.saveSchedule();
            shiftInfo1 = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
            SimpleUtils.assertOnFail("Shift Name is not updated!", shiftName.equalsIgnoreCase(shiftInfo1.get(9)), false);
            SimpleUtils.assertOnFail("Shift Notes is not updated!", shiftNote.equalsIgnoreCase(shiftInfo1.get(10)), false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Ashutosh")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify Auto Assign option of Assignment is working fine")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyAutoAssignOptionOfAssignmentIsWorkingFineAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            goToSchedulePageScheduleTab();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            goToSmartTemplatePage();
            smartTemplatePage.clickOnResetBtn();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            ArrayList<HashMap<String,String>> workRoles = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            String workRole1 = "Event Manager";
            String shiftName = "AssignRecurringShiftPattern";
            String shiftNote = "RecurringShiftsNote";
            String startTime = "10:00am";
            String endTime = "6:00pm";
            smartTemplatePage.clickOnEditBtn();
            //Create one group of recurring shifts
            smartTemplatePage.createShiftsWithOutWorkRoleTransition(workRole1, shiftName, "", startTime, endTime, 1,
                    Arrays.asList(0,1,2),
                    ScheduleTestKendraScott2.staffingOption.AssignToSpecificTMShift.getValue(), shiftNote, "", true);

            scheduleMainPage.saveSchedule();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
            scheduleShiftTablePage.expandOnlyOneGroup(shiftName);
            //Check the shift name and notes been added successfully
            List<String> shiftInfo1 = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
            SimpleUtils.assertOnFail("Shift Name is not updated!", shiftName.equalsIgnoreCase(shiftInfo1.get(9)), false);
            SimpleUtils.assertOnFail("Shift Notes is not updated!", shiftNote.equalsIgnoreCase(shiftInfo1.get(10)), false);

            //Edit the recurring shift pattern
            smartTemplatePage.clickOnEditBtn();
//            scheduleShiftTablePage.expandOnlyOneGroup(shiftName);
            HashSet<Integer> indexes = new HashSet<>();
            indexes.add(0);
            String action = "Edit";
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            editShiftPage.clickOnStaffingOption(ScheduleTestKendraScott2.staffingOption.AutoAssignShift.getValue());
            editShiftPage.clickOnUpdateButton();
            editShiftPage.clickOnUpdateAnywayButton();
            scheduleMainPage.saveSchedule();

            smartTemplatePage.clickOnBackBtn();

            isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
               if(!isActiveWeekGenerated){
                    createSchedulePage.createScheduleForNonDGFlowNewUI();
                            }
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
            scheduleShiftTablePage.expandOnlyOneGroup(shiftName);

       } catch (Exception e) {
           SimpleUtils.fail(e.getMessage(), false);
       }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the shift info display correctly on shift info tooltip for recurring shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyShiftInfoDisplayCorrectlyForRecurringShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            goToSchedulePageScheduleTab();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            goToSmartTemplatePage();
            smartTemplatePage.clickOnResetBtn();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            ArrayList<HashMap<String,String>> workRoles = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            String workRole1 = "Cafe";
            String shiftName = "RecurringShifts";
            String shiftNote = "RecurringShiftsNote";
            String startTime = "9:00am";
            String endTime = "5:00pm";
//            List<String> selectedTM = new ArrayList<>();
//            selectedTM.add("Kerstin Hahn");
            smartTemplatePage.clickOnEditBtn();
            //Create one group of recurring shifts
            List<String> selectedTM = smartTemplatePage.createShiftsWithOutWorkRoleTransition(workRole1, shiftName, "", startTime, endTime, 1,
                    Arrays.asList(0,1,2,3,4,5),
                    ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), shiftNote, "", true);

            scheduleMainPage.saveSchedule();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
            scheduleShiftTablePage.expandOnlyOneGroup(shiftName);
            //Check the shift name and notes been added successfully
            List<String> shiftInfo1 = scheduleShiftTablePage.getTheShiftInfoByIndex(0);

            //Verify the header in i tooltip of recurring shifts
            SimpleUtils.assertOnFail("The recurring header should display on recurring shift's i icon popup! ", scheduleShiftTablePage.
                    checkIfTheRecurringHeaderOnIIconPopupDisplayOrNot(0), false);
            //Verify the employee name in i tooltip of recurring shifts
            String expectedEmployeeName = selectedTM.get(0);
            String actualEmployeeName = shiftInfo1.get(0)+ " "+ shiftInfo1.get(5);
            SimpleUtils.assertOnFail("The expected employee name is: "+expectedEmployeeName
                            +" The actual employee name is: "+actualEmployeeName+"!",
                    expectedEmployeeName.equalsIgnoreCase(actualEmployeeName), false);
            //Verify the Work Role names in i tooltip of recurring shifts
            String actualWorkRoleName = shiftInfo1.get(4);
            SimpleUtils.assertOnFail("The expected work role is: "+workRole1
                            +" The actual work role is: "+actualWorkRoleName+"!",
                    workRole1.equalsIgnoreCase(actualWorkRoleName), false);
            //Verify the shift times in i tooltip of recurring shifts
            String expectedShiftTime = startTime+"-"+endTime;
            String actualShiftTime = shiftInfo1.get(2);
            SimpleUtils.assertOnFail("The expected shift time is: "+expectedShiftTime
                            +" The actual shift time is: "+actualShiftTime+"!",
                    expectedShiftTime.equalsIgnoreCase(actualShiftTime), false);
            //Verify the break info (break included) in i tooltip of recurring shifts
            String expectedMealBreakTime = "12:45 PM - 1:15 PM";
            String expectedRestBreakTime = "11:15 AM - 11:30 AM";
            String actualMealBreakTime = shiftInfo1.get(11);
            String actualRestBreakTime = shiftInfo1.get(12);
            SimpleUtils.assertOnFail("The expected meal break is: "+expectedMealBreakTime
                            +" The actual meal break is: "+actualMealBreakTime+"!",
                    expectedMealBreakTime.equalsIgnoreCase(actualMealBreakTime), false);
            SimpleUtils.assertOnFail("The expected rest break is: "+expectedRestBreakTime
                            +" The actual rest break is: "+actualRestBreakTime+"!",
                    expectedRestBreakTime.equalsIgnoreCase(actualRestBreakTime), false);
            //Verify the Shift Segments in i tooltip of recurring shifts
            //Verify the Shift daily hours | shift weekly hours this week in i tooltip of recurring shifts
            String expectedDailyHours = "7.5 Hrs";
            String expectedWeeklyHours = "45 Hrs";
            String actualDailyHours = shiftInfo1.get(8);
            String actalWeeklyHours = shiftInfo1.get(7);
            SimpleUtils.assertOnFail("The expected daily hours is: "+expectedDailyHours
                            +" The actual daily hours is: "+actualDailyHours+"!",
                    expectedDailyHours.equalsIgnoreCase(actualDailyHours), false);
            SimpleUtils.assertOnFail("The expected weekly hours is: "+expectedWeeklyHours
                            +" The actual weekly hours is: "+actalWeeklyHours+"!",
                    actalWeeklyHours.contains(expectedWeeklyHours), false);
            //Verify the Shift Name and Notes in i tooltip of recurring shifts
            SimpleUtils.assertOnFail("Shift Name is not updated!", shiftName.equalsIgnoreCase(shiftInfo1.get(9)), false);
            SimpleUtils.assertOnFail("Shift Notes is not updated!", shiftNote.equalsIgnoreCase(shiftInfo1.get(10)), false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the shift info display correctly on shift info tooltip for non-recurring shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyShiftInfoDisplayCorrectlyForNonRecurringShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            goToSchedulePageScheduleTab();
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            goToSmartTemplatePage();
            smartTemplatePage.clickOnResetBtn();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            ArrayList<HashMap<String,String>> workRoles = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
//            String workRole1 = workRoles.get(0).get("optionName");
            String workRole1 = "Cafe";
            String shiftName = "No Pattern";
            String shiftNote = "NonRecurringShiftsNote";
            String startTime = "9:00am";
            String endTime = "5:00pm";
//            List<String> selectedTM = new ArrayList<>();
//            selectedTM.add("Kerstin Hahn");
            smartTemplatePage.clickOnEditBtn();
            //Create one group of recurring shifts
            List<String> selectedTM = smartTemplatePage.createShiftsWithOutWorkRoleTransition(workRole1, shiftName, "", startTime, endTime, 1,
                    Arrays.asList(0,1,2,3,4,5),
                    ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), shiftNote, "", false);

            scheduleMainPage.saveSchedule();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
            scheduleShiftTablePage.expandOnlyOneGroup(shiftName);
            //Check the shift name and notes been added successfully
            List<String> shiftInfo1 = scheduleShiftTablePage.getTheShiftInfoByIndex(0);

            //Verify the header in i tooltip of recurring shifts
            SimpleUtils.assertOnFail("The recurring header should not display on recurring shift's i icon popup! ", !scheduleShiftTablePage.
                    checkIfTheRecurringHeaderOnIIconPopupDisplayOrNot(0), false);
            //Verify the employee name in i tooltip of recurring shifts
            String expectedEmployeeName = selectedTM.get(0);
            String actualEmployeeName = shiftInfo1.get(0)+ " "+ shiftInfo1.get(5);
            SimpleUtils.assertOnFail("The expected employee name is: "+expectedEmployeeName
                            +" The actual employee name is: "+actualEmployeeName+"!",
                    expectedEmployeeName.equalsIgnoreCase(actualEmployeeName), false);
            //Verify the Work Role names in i tooltip of recurring shifts
            String actualWorkRoleName = shiftInfo1.get(4);
            SimpleUtils.assertOnFail("The expected work role is: "+workRole1
                            +" The actual work role is: "+actualWorkRoleName+"!",
                    workRole1.equalsIgnoreCase(actualWorkRoleName), false);
            //Verify the shift times in i tooltip of recurring shifts
            String expectedShiftTime = startTime+"-"+endTime;
            String actualShiftTime = shiftInfo1.get(2);
            SimpleUtils.assertOnFail("The expected shift time is: "+expectedShiftTime
                            +" The actual shift time is: "+actualShiftTime+"!",
                    expectedShiftTime.equalsIgnoreCase(actualShiftTime), false);
            //Verify the break info (break included) in i tooltip of recurring shifts
            String expectedMealBreakTime = "12:45 PM - 1:15 PM";
            String expectedRestBreakTime = "11:15 AM - 11:30 AM";
            String actualMealBreakTime = shiftInfo1.get(11);
            String actualRestBreakTime = shiftInfo1.get(12);
            SimpleUtils.assertOnFail("The expected meal break is: "+expectedMealBreakTime
                            +" The actual meal break is: "+actualMealBreakTime+"!",
                    expectedMealBreakTime.equalsIgnoreCase(actualMealBreakTime), false);
            SimpleUtils.assertOnFail("The expected rest break is: "+expectedRestBreakTime
                            +" The actual rest break is: "+actualRestBreakTime+"!",
                    expectedRestBreakTime.equalsIgnoreCase(actualRestBreakTime), false);
            //Verify the Shift Segments in i tooltip of recurring shifts
            //Verify the Shift daily hours | shift weekly hours this week in i tooltip of recurring shifts
            String expectedDailyHours = "7.5 Hrs";
            String expectedWeeklyHours = "45 Hrs";
            String actualDailyHours = shiftInfo1.get(8);
            String actalWeeklyHours = shiftInfo1.get(7);
            SimpleUtils.assertOnFail("The expected daily hours is: "+expectedDailyHours
                            +" The actual daily hours is: "+actualDailyHours+"!",
                    expectedDailyHours.equalsIgnoreCase(actualDailyHours), false);
            SimpleUtils.assertOnFail("The expected weekly hours is: "+expectedWeeklyHours
                            +" The actual weekly hours is: "+actalWeeklyHours+"!",
                    actalWeeklyHours.contains(expectedWeeklyHours), false);
            //Verify the Shift Name and Notes in i tooltip of recurring shifts
            SimpleUtils.assertOnFail("Shift Name is not updated!", shiftName.equalsIgnoreCase(shiftInfo1.get(9)), false);
            SimpleUtils.assertOnFail("Shift Notes is not updated!", shiftNote.equalsIgnoreCase(shiftInfo1.get(10)), false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify recurring shifts been change to non-recurring shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyRecurringShiftsBeenChangeToNonRecurringShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            goToSchedulePageScheduleTab();
            BasePage basePage = new BasePage();
            String activeWeek = basePage.getActiveWeekText();
            String startOfWeek = activeWeek.split(" ")[3] + " " + activeWeek.split(" ")[4];
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            goToSmartTemplatePage();
            smartTemplatePage.clickOnResetBtn();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            ArrayList<HashMap<String,String>> workRoles = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
//            String workRole1 = workRoles.get(0).get("optionName");
            String workRole1 = "Cafe";
            String shiftName = "RecurringShifts";
            String shiftNote = "RecurringShiftsNote";
            String startTime = "9:00am";
            String endTime = "5:00pm";
//            List<String> selectedTM = new ArrayList<>();
//            selectedTM.add("Kerstin Hahn");
            smartTemplatePage.clickOnEditBtn();
            //Create one group of recurring shifts
            List<String> selectedTM = smartTemplatePage.createShiftsWithOutWorkRoleTransition(workRole1, shiftName, "", startTime, endTime, 2,
                    Arrays.asList(0,1,2,3,4,5),
                    ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), shiftNote, "", true);

            scheduleMainPage.saveSchedule();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
            scheduleShiftTablePage.expandOnlyOneGroup(shiftName);

            smartTemplatePage.clickOnEditBtn();
            HashSet<Integer> indexes = new HashSet<>();
            indexes.add(0);
            String action = "Edit";
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            //Change recurring shift to non-recurring shift
            smartTemplatePage.checkOrUnCheckRecurringShift(false);
            editShiftPage.clickOnUpdateButton();
            editShiftPage.clickOnUpdateAnywayButton();
            scheduleMainPage.saveSchedule();
            //Check the recurring shifts name been change to no pattern
            ArrayList<HashMap<String,String>> patterns = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            List<String> patternNames = new ArrayList<>();
            for (HashMap<String, String> pattern : patterns) {
                patternNames.add(pattern.get("optionName"));
            }
            if (!patternNames.contains(shiftName.toLowerCase())) {
                SimpleUtils.pass("The shift pattern shifts can show in smart template! ");
            } else
                SimpleUtils.fail("Shift pattern shifts fail to show in smart template! ", false);
            String noPatternName = "No Pattern";
            if (patternNames.contains(noPatternName.toLowerCase())) {
                SimpleUtils.pass("The shift pattern shifts can show in smart template! ");
            } else
                SimpleUtils.fail("Shift pattern shifts fail to show in smart template! ", false);
            //Check the shifts cannot be selected as a group
            smartTemplatePage.clickOnEditBtn();
            indexes.add(0);
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            editShiftPage.verifyTheTitleOfEditShiftsWindow(1, startOfWeek);
            editShiftPage.clickOnCancelButton();

            //Go to previous week
            smartTemplatePage.clickOnBackBtn();
            smartTemplatePage.clickOnBackBtn();
            scheduleCommonPage.navigateToNextWeek();
            isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            goToSmartTemplatePage();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
            patterns.clear();
            patternNames.clear();
            patterns = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            for (HashMap<String, String> pattern : patterns) {
                patternNames.add(pattern.get("optionName"));
            }
            if (!patternNames.contains(shiftName.toLowerCase())
                    && !patternNames.contains(noPatternName.toLowerCase())) {
                SimpleUtils.pass("The shift pattern shifts not show in future week smart template! ");
            } else
                SimpleUtils.fail("Shift pattern shifts should not show in future week smart template! ", false);


        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }



    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify non-recurring shifts been change to recurring shifts")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyNonRecurringShiftsBeenChangeToRecurringShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            goToSchedulePageScheduleTab();
            BasePage basePage = new BasePage();
            String activeWeek = basePage.getActiveWeekText();
            String startOfWeek = activeWeek.split(" ")[3] + " " + activeWeek.split(" ")[4];
            boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            goToSmartTemplatePage();
            smartTemplatePage.clickOnResetBtn();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
            ArrayList<HashMap<String,String>> workRoles = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
//            String workRole1 = workRoles.get(0).get("optionName");
            String workRole1 = "Cafe";
            String shiftName = "No Pattern";
            String shiftNote = "NonRecurringShiftsNote";
            String startTime = "9:00am";
            String endTime = "5:00pm";
//            List<String> selectedTM = new ArrayList<>();
//            selectedTM.add("Kerstin Hahn");
            smartTemplatePage.clickOnEditBtn();
            //Create one group of recurring shifts
            List<String> selectedTM = smartTemplatePage.createShiftsWithOutWorkRoleTransition(workRole1, shiftName, "", startTime, endTime, 1,
                    Arrays.asList(0),
                    ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), shiftNote, "", false);

            scheduleMainPage.saveSchedule();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
            scheduleShiftTablePage.expandOnlyOneGroup(shiftName);

            smartTemplatePage.clickOnEditBtn();
            HashSet<Integer> indexes = new HashSet<>();
            indexes.add(0);
            String action = "Edit";
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            //Change recurring shift to non-recurring shift
            smartTemplatePage.checkOrUnCheckRecurringShift(true);
            String recurringShiftName = "RecurringShifts";
            editShiftPage.inputShiftName(recurringShiftName);
            editShiftPage.clickOnUpdateButton();
            editShiftPage.clickOnUpdateAnywayButton();
            scheduleMainPage.saveSchedule();
            //Check the recurring shifts name been change to no pattern
            ArrayList<HashMap<String,String>> patterns = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            List<String> patternNames = new ArrayList<>();
            for (HashMap<String, String> pattern : patterns) {
                patternNames.add(pattern.get("optionName"));
            }
            if (!patternNames.contains(shiftName.toLowerCase())) {
                SimpleUtils.pass("The shift pattern shifts can show in smart template! ");
            } else
                SimpleUtils.fail("Shift pattern shifts fail to show in smart template! ", false);

            if (patternNames.contains(recurringShiftName.toLowerCase())) {
                SimpleUtils.pass("The shift pattern shifts can show in smart template! ");
            } else
                SimpleUtils.fail("Shift pattern shifts fail to show in smart template! ", false);
            //Check the shifts cannot be selected as a group
            smartTemplatePage.clickOnEditBtn();
            indexes.add(0);
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);
            editShiftPage.verifyTheTitleOfEditShiftsWindow(1, startOfWeek);
            editShiftPage.clickOnCancelButton();

            //Go to previous week
            smartTemplatePage.clickOnBackBtn();
            smartTemplatePage.clickOnBackBtn();
            scheduleCommonPage.navigateToNextWeek();
            isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
            if(isActiveWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            goToSmartTemplatePage();
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
            patterns.clear();
            patterns = scheduleShiftTablePage.getGroupByOptionsStyleInfo();
            for (HashMap<String, String> pattern : patterns) {
                patternNames.add(pattern.get("optionName"));
            }
            if (!patternNames.contains(shiftName.toLowerCase())
                    && patternNames.contains(recurringShiftName.toLowerCase())) {
                SimpleUtils.pass("The shift pattern shifts not show in future week smart template! ");
            } else
                SimpleUtils.fail("Shift pattern shifts should not show in future week smart template! ", false);


        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
