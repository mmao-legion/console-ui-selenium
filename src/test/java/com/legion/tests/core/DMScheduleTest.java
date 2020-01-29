package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.*;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class DMScheduleTest extends TestBase{
    private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
    private static HashMap<String, String> propertyCustomizeMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ScheduleCustomizeNewShift.json");
    private static HashMap<String, String> scheduleWorkRoles = JsonUtil.getPropertiesFromJsonFile("src/test/resources/WorkRoleOptions.json");
    private static HashMap<String, String> propertySearchTeamMember = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SearchTeamMember.json");
    private static HashMap<String, String> searchDetails = JsonUtil.getPropertiesFromJsonFile("src/test/resources/searchDetails.json");
    SchedulePage schedulePage = null;
    private static HashMap<String, String> controlsLocationDetail = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ControlsPageLocationDetail.json");
    private static HashMap<String, String> schedulingPoliciesData = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SchedulingPoliciesData.json");
    private static HashMap<String, String> propertySelectLocation = JsonUtil.getPropertiesFromJsonFile("src/test/resources/LocationSelector.json");
    private static HashMap<String, String> dmViewTestData = JsonUtil.getPropertiesFromJsonFile("src/test/resources/DMViewTestData.json");
    @Override
    @BeforeMethod
    public void firstTest(Method method, Object[] params) throws Exception {
        this.createDriver((String)params[0],"69","Window");
        visitPage(method);
        loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
    }


    public enum weekCount{
        Zero(0),
        One(1),
        Two(2),
        Three(3),
        Four(4),
        Five(5);
        private final int value;
        weekCount(final int newValue) {
            value = newValue;
        }
        public int getValue() { return value; }
    }

    public enum filtersIndex{
        Zero(0),
        One(1),
        Two(2),
        Three(3),
        Four(4),
        Five(5);
        private final int value;
        filtersIndex(final int newValue) {
            value = newValue;
        }
        public int getValue() { return value; }
    }


    public enum dayCount{
        Seven(7);
        private final int value;
        dayCount(final int newValue) {
            value = newValue;
        }
        public int getValue() { return value; }
    }

    public enum schedulePlanningWindow{
        Eight(8);
        private final int value;
        schedulePlanningWindow(final int newValue) {
            value = newValue;
        }
        public int getValue() { return value; }
    }

    public enum sliderShiftCount{
        SliderShiftStartCount(2),
        SliderShiftEndTimeCount(10);
        private final int value;
        sliderShiftCount(final int newValue) {
            value = newValue;
        }
        public int getValue() { return value; }
    }

    public enum staffingOption{
        OpenShift("Auto"),
        ManualShift("Manual"),
        AssignTeamMemberShift("Assign Team Member");
        private final String value;
        staffingOption(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    public enum overviewWeeksStatus{
        NotAvailable("Not Available"),
        Draft("Draft"),
        Guidance("Guidance"),
        Published("Published"),
        Finalized("Finalized");

        private final String value;
        overviewWeeksStatus(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }


    public enum SchedulePageSubTabText{
        Overview("OVERVIEW"),
        ProjectedSales("PROJECTED SALES"),
        StaffingGuidance("STAFFING GUIDANCE"),
        Schedule("SCHEDULE"),
        ProjectedTraffic("PROJECTED TRAFFIC");
        private final String value;
        SchedulePageSubTabText(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    public enum weekViewType{
        Next("Next"),
        Previous("Previous");
        private final String value;
        weekViewType(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    public enum shiftSliderDroppable{
        StartPoint("Start"),
        EndPoint("End");
        private final String value;
        shiftSliderDroppable(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    public enum scheduleHoursAndWagesData{
        scheduledHours("scheduledHours"),
        budgetedHours("budgetedHours"),
        otherHours("otherHours"),
        wagesBudgetedCount("wagesBudgetedCount"),
        wagesScheduledCount("wagesScheduledCount");
        private final String value;
        scheduleHoursAndWagesData(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    public enum scheduleGroupByFilterOptions{
        groupbyAll("Group by All"),
        groupbyWorkRole("Group by Work Role"),
        groupbyTM("Group by TM");
        private final String value;
        scheduleGroupByFilterOptions(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }




    public void verifyScheduleLabelHours(String shiftTimeSchedule,
                                         Float scheduledHoursBeforeEditing, Float scheduledHoursAfterEditing) throws Exception{
        Float scheduledHoursExpectedValueEditing = 0.0f;
        if(Float.parseFloat(shiftTimeSchedule) >= 6){
            scheduledHoursExpectedValueEditing = (float)(scheduledHoursBeforeEditing +(Float.parseFloat(shiftTimeSchedule)-0.5));
            if(scheduledHoursExpectedValueEditing.equals(scheduledHoursAfterEditing)){
                SimpleUtils.pass("Scheduled Hours Expected value "+scheduledHoursExpectedValueEditing+" matches with Scheduled Hours Actual value "+scheduledHoursAfterEditing);
            }else{
                SimpleUtils.fail("Scheduled Hours Expected value "+scheduledHoursExpectedValueEditing+" does not match with Scheduled Hours Actual value "+scheduledHoursAfterEditing,false);
            }
        }else{
            // If meal break is not applicable
            scheduledHoursExpectedValueEditing = (float)(scheduledHoursBeforeEditing + Float.parseFloat(shiftTimeSchedule));
            if(scheduledHoursExpectedValueEditing.equals(scheduledHoursAfterEditing)){
                SimpleUtils.pass("Scheduled Hours Expected value "+scheduledHoursExpectedValueEditing+" matches with Scheduled Hours Actual value "+scheduledHoursAfterEditing);
            }else{
                SimpleUtils.fail("Scheduled Hours Expected value "+scheduledHoursExpectedValueEditing+" does not match with Scheduled Hours Actual value "+scheduledHoursAfterEditing,false);
            }
        }

    }


    public void verifyTeamCount(List<String> previousTeamCount, List<String> currentTeamCount) throws Exception {
        if(previousTeamCount.size() == currentTeamCount.size()){
            for(int i =0; i<currentTeamCount.size();i++){
                String currentCount = currentTeamCount.get(i);
                String previousCount = previousTeamCount.get(i);
                if(Integer.parseInt(currentCount) == Integer.parseInt(previousCount)+1){
                    SimpleUtils.pass("Current Team Count is greater than Previous Team Count");
                }else{
                    SimpleUtils.fail("Current Team Count is not greater than Previous Team Count",true);
                }
            }
        }else{
            SimpleUtils.fail("Size of Current Team Count should be equal to Previous Team Count",false);
        }
    }

    public void verifyGutterCount(int previousGutterCount, int updatedGutterCount){
        if(updatedGutterCount == previousGutterCount + 1){
            SimpleUtils.pass("Size of gutter is "+updatedGutterCount+" greater than previous value "+previousGutterCount);
        }else{
            SimpleUtils.fail("Size of gutter is "+updatedGutterCount+" greater than previous value "+previousGutterCount, false);
        }
    }


    public void scheduleNavigationTest(int previousGutterCount) throws Exception{
        schedulePage.clickOnEditButton();
        boolean bolDeleteShift = checkAddedShift(previousGutterCount);
        if(bolDeleteShift){
            schedulePage.clickSaveBtn();
            schedulePage.clickOnEditButton();
        }
    }

    public boolean checkAddedShift(int guttercount)throws Exception {
        boolean bolDeleteShift = false;
        if (guttercount > 0) {
            schedulePage.clickOnShiftContainer(guttercount);
            bolDeleteShift = true;
        }
        return bolDeleteShift;
    }

    //added by Nishant for Sanity Suite

    @Automated(automated =  "Automated")
    @Owner(owner = "Nishant")
    @SanitySuite(sanity =  "Sanity")
    @Enterprise(name = "DGStage_Enterprise")
    @TestName(description = "Validate the correctness of data present in location details section on DM Schedule page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void validateDMScheduleAsInternalAdmin(String browser, String username, String password, String location)
            throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
		List<Float> totalHoursFromSchTbl = schedulePage.validateScheduleAndBudgetedHours();
		dashboardPage.navigateToDashboard();
		schedulePage.compareHoursFromScheduleAndDashboardPage(totalHoursFromSchTbl);
    }


    @Automated(automated =  "Automated")
    @Owner(owner = "Nishant")
    @SanitySuite(sanity =  "Sanity")
    @Enterprise(name = "DGStage_Enterprise")
    @TestName(description = "Validate the correctness of data present on DM Dashboard")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void validateProjectedHoursFromDashBoardAndScheduleAsInternalAdmin(String browser, String username, String password, String location)
            throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        SimpleUtils.pass("Comments added Successfully in Test Rail");
		List<Float> totalHoursFromSchTbl = schedulePage.getHoursOnLocationSummarySmartCard();
		int totalCountProjectedUnderBudget = schedulePage.getProjectedOverBudget();
		dashboardPage.navigateToDashboard();
		schedulePage.compareHoursFromScheduleSmartCardAndDashboardSmartCard(totalHoursFromSchTbl);
		schedulePage.compareProjectedWithinBudget(totalCountProjectedUnderBudget);
    }


    @Automated(automated =  "Automated")
    @Owner(owner = "Nishant")
    @SanitySuite(sanity =  "Sanity")
    @Enterprise(name = "DGStage_Enterprise")
    @TestName(description = "Validate the correctness of data present on DM View Schedule smartcards ")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void validateDMDashboardAsInternalAdmin(String browser, String username, String password, String location)
            throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        dashboardPage.navigateToDashboard();
        String DateOnDashboard = schedulePage.getDateFromDashboard();
        List<String> ListLocationSummaryOnDashboard = schedulePage.getLocationSummaryDataFromDashBoard();
        schedulePage.clickOnScheduleConsoleMenuItem();
        String dateOnSchdeule = schedulePage.getActiveWeekText();
        schedulePage.compareDashboardAndScheduleWeekDate(dateOnSchdeule, DateOnDashboard);
        List<String> ListLocationSummaryOnSchedule = schedulePage.getLocationSummaryDataFromSchedulePage();
        schedulePage.compareLocationSummaryFromDashboardAndSchedule(ListLocationSummaryOnDashboard,ListLocationSummaryOnSchedule);
    }

    // Added by Nishant

    @Automated(automated =  "Automated")
    @Owner(owner = "Nishant")
    @SanitySuite(sanity =  "Sanity")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verification of values from DM view of Unplanned clock smartcard and detail smartcard with SM view")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void validateDMViewDataWithSMViewAsInternalAdmin(String browser, String username, String password, String location)
            throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        BasePage basePage = new BasePage();
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict("Demo District");
        dashboardPage.navigateToDashboard();
        String DateOnDashboard = schedulePage.getDateFromDashboard();
        TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
        timeSheetPage.clickOnTimeSheetConsoleMenu();
        String dateOnTimesheet = basePage.getActiveWeekText();
        schedulePage.compareDashboardAndScheduleWeekDate(dateOnTimesheet, DateOnDashboard);
        timeSheetPage.validateLoadingOfTimeSheetSmartCard();
//        List<String> listTotalUnplannedHrsText = timeSheetPage.getUnplannedClocksValueNtext();
//        List<String> listDetailUnplannedHrsText = timeSheetPage.getUnplannedClocksDetailSummaryValue();
        List<String> listLocationName = timeSheetPage.getLocationName();
        int totalUnplannedClocksOnDMView = timeSheetPage.getUnplannedClocksOnDMView();
        int totalTimesheetsOnDMView = timeSheetPage.getTotalTimesheetsOnDMView();
        timeSheetPage.goToSMView(listLocationName, dateOnTimesheet,
                Integer.parseInt(propertySelectLocation.get("LOCATION_COUNT")), totalUnplannedClocksOnDMView, totalTimesheetsOnDMView);

    }

    @Automated(automated =  "Automated")
    @Owner(owner = "Nishant")
    @SanitySuite(sanity =  "Sanity")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "Verification of Unplanned clocks and total timesheet columns from table view")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void validateDMViewUnplannedClockAndTimesheetCountAsInternalAdmin(String browser, String username, String password, String location)
            throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        BasePage basePage = new BasePage();
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.changeDistrict("Demo District");
        dashboardPage.navigateToDashboard();
        String DateOnDashboard = schedulePage.getDateFromDashboard();
        TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
        timeSheetPage.clickOnTimeSheetConsoleMenu();
        String dateOnTimesheet = basePage.getActiveWeekText();
        schedulePage.compareDashboardAndScheduleWeekDate(dateOnTimesheet, DateOnDashboard);
        timeSheetPage.validateLoadingOfTimeSheetSmartCard();
        int totalUnplannedClockSmartCardValOnDMView = timeSheetPage.getUnplannedClockSmartCardOnDMView();
        int totalUnplannedClocksOnDMViewSmartCardDetailSummary = timeSheetPage.getUnplannedClocksDetailSummaryValue();
        int totalUnplannedClocksOnTblView = timeSheetPage.getUnplannedClocksOnDMView();
        int totalTimesheetsOnTblView = timeSheetPage.getTotalTimesheetsOnDMView();
        int totalTimesheetOnDMViewSmartCard = timeSheetPage.getTotalTimesheetFromSmartCardOnDMView();
        verifyUnplannedClockOnDMView(totalUnplannedClockSmartCardValOnDMView, totalUnplannedClocksOnDMViewSmartCardDetailSummary,
                totalUnplannedClocksOnTblView);
        verifyTimesheetOnDMView(totalTimesheetOnDMViewSmartCard, totalTimesheetsOnTblView);
    }

    public void verifyUnplannedClockOnDMView(int totalUnplannedClockSmartCardValOnDMView,
                                                                         int totalUnplannedClocksOnDMViewSmartCardDetailSummary,
                                             int totalUnplannedClocksOnTblView){
        if(totalUnplannedClockSmartCardValOnDMView == totalUnplannedClocksOnDMViewSmartCardDetailSummary){
            SimpleUtils.pass("Unplanned Clock from Smart Card " + totalUnplannedClockSmartCardValOnDMView + " matches " +
                    "with Unplanned Clock in Details Summary Card " + totalUnplannedClocksOnDMViewSmartCardDetailSummary + " on DM View");
        }else{
            SimpleUtils.fail("Unplanned Clock from Smart Card " + totalUnplannedClockSmartCardValOnDMView + " do not match " +
                    "with Unplanned Clock in Details Summary Card " + totalUnplannedClocksOnDMViewSmartCardDetailSummary + " on DM View",true);
        }
        if(totalUnplannedClockSmartCardValOnDMView == totalUnplannedClocksOnTblView){
            SimpleUtils.pass("Unplanned Clock from Smart Card " + totalUnplannedClockSmartCardValOnDMView + " matches " +
                    "with sum of Unplanned Clock per location in Timesheet table " + totalUnplannedClocksOnTblView + " on DM View");
        }else{
            SimpleUtils.fail("Unplanned Clock from Smart Card " + totalUnplannedClockSmartCardValOnDMView + " do not match " +
                    "with sum of Unplanned Clock per location in Timesheet table " + totalUnplannedClocksOnTblView + " on DM View",true);
        }
    }

    //added by Gunjan

    @Automated(automated =  "Automated")
    @Owner(owner = "Gunjan")
    @SanitySuite(sanity =  "Sanity")
    @Enterprise(name = "Coffee_Enterprise")
    @TestName(description = "To and fro navigation from DM to SM view and DM view dashboard to respective tabs")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void validateDMtoSMNavigationNViceVersaAsInternalAdmin(String browser, String username, String password, String location)
            throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        String locationToSelectFromDMViewSchedule = dmViewTestData.get("Location");
        locationSelectorPage.changeDistrict(dmViewTestData.get("District"));
        schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        dashboardPage.navigateToDashboard();
        String DateOnDashboard = schedulePage.getDateFromDashboard();
        schedulePage.clickOnViewScheduleLocationSummaryDMViewDashboard();
        String dateOnScheduleOnNavigatingFromLocSummary = schedulePage.getActiveWeekText();
        schedulePage.compareDashboardAndScheduleWeekDate(dateOnScheduleOnNavigatingFromLocSummary, DateOnDashboard);
        schedulePage.toNFroNavigationFromDMToSMSchedule(dateOnScheduleOnNavigatingFromLocSummary, locationToSelectFromDMViewSchedule, dmViewTestData.get("District"), weekViewType.Next.getValue());
        dashboardPage.navigateToDashboard();
        schedulePage.clickOnViewSchedulePayrollProjectionDMViewDashboard();
        String dateOnSchdeuleOnNavigatingFromPayroleProjection = schedulePage.getActiveWeekText();
        schedulePage.compareDashboardAndScheduleWeekDate(dateOnSchdeuleOnNavigatingFromPayroleProjection, DateOnDashboard);
        schedulePage.toNFroNavigationFromDMDashboardToDMSchedule(dateOnSchdeuleOnNavigatingFromPayroleProjection);
        TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
        timeSheetPage.clickOnTimeSheetConsoleMenu();
        timeSheetPage.validateLoadingOfTimeSheetSmartCard();
        timeSheetPage.goToSMView();
        dashboardPage.navigateToDashboard();
        schedulePage.districtSelectionSMView(dmViewTestData.get("District"));
        timeSheetPage.clickOnTimeSheetConsoleMenu();
        timeSheetPage.validateLoadingOfTimeSheetSmartCard(weekViewType.Previous.getValue());
        timeSheetPage.goToSMView();
        dashboardPage.navigateToDashboard();
        schedulePage.districtSelectionSMView(dmViewTestData.get("District"));
        timeSheetPage.clickOnComplianceConsoleMenu();
        timeSheetPage.validateLoadingOfComplianceOnDMView(weekViewType.Previous.getValue(),true);
        timeSheetPage.validateLoadingOfComplianceOnDMView(weekViewType.Previous.getValue(),false);
        dashboardPage.navigateToDashboard();
        timeSheetPage.clickOnComplianceViolationSectionOnDashboard();

    }


    public void verifyTimesheetOnDMView(int totalTimesheetOnDMViewSmartCard,
                                                                        int totalTimesheetsOnTblView){
        if(totalTimesheetOnDMViewSmartCard == totalTimesheetsOnTblView){
            SimpleUtils.pass("Total Timesheet Count from Smart Card " + totalTimesheetOnDMViewSmartCard + " matches " +
                    "with sum of Timesheet entries per location in Timesheet table " + totalTimesheetsOnTblView + " on DM View");
        }else{
            SimpleUtils.fail("Total Timesheet Count from Smart Card " + totalTimesheetOnDMViewSmartCard + " do not match " +
                    "with sum of Timesheet entries per location in Timesheet table  " + totalTimesheetsOnTblView + " on DM View",true);
        }

    }


}

