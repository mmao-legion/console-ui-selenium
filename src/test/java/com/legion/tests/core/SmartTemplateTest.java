package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
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
//        try {
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
            String workRole1 = workRoles.get(0).get("optionName");
            String shiftName = "RecurringShifts";
            String shiftNote = "RecurringShiftsNote";
            String startTime = "9:00pm";
            String endTime = "11:00pm";
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
//        } catch (Exception e) {
//            SimpleUtils.fail(e.getMessage(), false);
//        }
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
            String shiftName = "No Pattern";
            String shiftNote = "NonRecurringShiftsNote";
            smartTemplatePage.clickOnEditBtn();
            //Create one group of non-recurring shifts
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
//            smartTemplatePage.createShiftsWithSpecificValues(workRole1, shiftName, "", startTime, endTime, 2, Arrays.asList(0,1,2,3,4,5),
//                    ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), shiftNote, "", false);
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
            if (!patternNames.contains(shiftName.toLowerCase())) {
                SimpleUtils.pass("The shift pattern shifts can show in smart template! ");
            } else
                SimpleUtils.fail("Shift pattern shifts fail to show in smart template! ", false);
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
            String workRole1 = workRoles.get(0).get("optionName");
            String shiftName = "NewCreatedShifts";
            String shiftNote = "NewCreatedShiftsNote";
            String startTime = "9:00pm";
            String endTime = "11:00pm";
            boolean recurringShift = false;
            LocalDate date = now();
            String dayOfWeek = date.getDayOfWeek().toString();
            if (dayOfWeek.equalsIgnoreCase("Monday")
                    ||dayOfWeek.equalsIgnoreCase("Tuesday")
                    ||dayOfWeek.equalsIgnoreCase("Wednesday") ) {
                recurringShift = true;
            }
            smartTemplatePage.clickOnEditBtn();
            //Create one group of recurring shifts
//            smartTemplatePage.createShiftsWithSpecificValues(workRole1, shiftName, "", startTime, endTime, 2, Arrays.asList(0,1,2,3,4,5),
//                    ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), shiftNote, "", recurringShift);
            scheduleMainPage.saveSchedule();

            //Group by pattern
            scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyPattern.getValue());
            //Edit the new created shift
            smartTemplatePage.clickOnEditBtn();
            if (recurringShift){
                scheduleShiftTablePage.expandOnlyOneGroup(shiftName);
            } else{
                scheduleShiftTablePage.expandOnlyOneGroup("No Pattern");
            }

            HashSet<Integer> indexes = new HashSet<>();
            indexes.add(0);
            scheduleShiftTablePage.rightClickOnSelectedShifts(indexes);
            String action = "Edit";
            scheduleShiftTablePage.clickOnBtnOnBulkActionMenuByText(action);

            //Verify we can add one or more shift segments
            editShiftPage.verifyTheTitleOfEditShiftsWindow(1, startOfWeek);
            editShiftPage.clickOnCancelButton();


        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
