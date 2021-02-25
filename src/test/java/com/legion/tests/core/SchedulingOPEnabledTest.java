package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
import com.legion.test.core.mobile.LoginTest;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;

import static com.legion.utils.MyThreadLocal.*;

public class SchedulingOPEnabledTest  extends TestBase {

    private static HashMap<String, String> scheduleWorkRoles = JsonUtil.getPropertiesFromJsonFile("src/test/resources/WorkRoleOptions.json");
    private static HashMap<String, String> propertyCustomizeMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ScheduleCustomizeNewShift.json");
    private static HashMap<String, String> schedulePolicyData = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SchedulingPoliciesData.json");
    private static HashMap<String, String> propertySearchTeamMember = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SearchTeamMember.json");


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
    @Owner(owner = "Estelle/Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the Schedule functionality - Week View - Context Menu")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyScheduleFunctionalityWeekView(String username, String password, String browser, String location)
            throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        schedulePage.navigateToNextWeek();
        boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
        if(!isActiveWeekGenerated){
            schedulePage.createScheduleForNonDGFlowNewUI();
        }
        //In week view, Group by All filter have 4 filters:1.Group by all  2. Group by work role  3. Group by TM 4.Group by job title
        schedulePage.validateGroupBySelectorSchedulePage();
        //Selecting any of them, check the schedule table
        schedulePage.validateScheduleTableWhenSelectAnyOfGroupByOptions();

        //Edit button should be clickable
        //While click on edit button,if Schedule is finalized then prompt is available and Prompt is in proper alignment and correct msg info.
        //Edit anyway and cancel button is clickable
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

        //click on the context of any TM, 1. View profile 2. Change shift role  3.Assign TM 4.  Convert to open shift is enabled for current and future week day 5.Edit meal break time 6. Delete shift
        schedulePage.selectNextWeekSchedule();
        schedulePage.navigateToNextWeek();
        boolean isActiveWeekGenerated2 = schedulePage.isWeekGenerated();
        if(isActiveWeekGenerated2){
           schedulePage.unGenerateActiveScheduleScheduleWeek();
        }
        schedulePage.createScheduleForNonDGFlowNewUI();
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.deleteTMShiftInWeekView("Unassigned");
        schedulePage.saveSchedule();
        schedulePage.publishActiveSchedule();
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        SimpleUtils.assertOnFail(" context of any TM display doesn't show well" , schedulePage.verifyContextOfTMDisplay(), false);

        //"After Click on view profile,then particular TM profile is displayed :1. Personal details 2. Work Preferences 3. Availability
        schedulePage.clickOnViewProfile();
        schedulePage.verifyPersonalDetailsDisplayed();
        schedulePage.verifyWorkPreferenceDisplayed();
        schedulePage.verifyAvailabilityDisplayed();
        schedulePage.closeViewProfileContainer();

        //"After Click on the Change shift role, one prompt is enabled:various work role any one of them can be selected"
        schedulePage.clickOnProfileIcon();
        schedulePage.clickOnChangeRole();
        schedulePage.verifyChangeRoleFunctionality();
        //check the work role by click Apply button
        schedulePage.changeWorkRoleInPrompt(true);
        //check the work role by click Cancel button
        schedulePage.changeWorkRoleInPrompt(false);

        //After Click on Assign TM-Select TMs window is opened,Recommended and search TM tab is enabled
        schedulePage.clickOnProfileIcon();
        schedulePage.clickonAssignTM();
        schedulePage.verifyRecommendedAndSearchTMEnabled();

        //Search and select any TM,Click on the assign: new Tm is updated on the schedule table
        //Select new TM from Search Team Member tab
        WebElement selectedShift = null;
        selectedShift = schedulePage.clickOnProfileIcon();
        String selectedShiftId= selectedShift.getAttribute("id").toString();
        schedulePage.clickonAssignTM();
        String firstNameOfSelectedTM = schedulePage.selectTeamMembers();
        schedulePage.clickOnOfferOrAssignBtn();
        SimpleUtils.assertOnFail(" New selected TM doesn't display in scheduled table" , firstNameOfSelectedTM.equals(schedulePage.getShiftById(selectedShiftId).findElement(By.className("week-schedule-worker-name")).getText()), false);
        //Select new TM from Recommended TMs tab
//        selectedShift = schedulePage.clickOnProfileIcon();
//        String selectedShiftId2 = selectedShift.getAttribute("id").toString();
//        schedulePage.clickonAssignTM();
//        schedulePage.switchSearchTMAndRecommendedTMsTab();
//        String firstNameOfSelectedTM2 = schedulePage.selectTeamMembers();
//        schedulePage.clickOnOfferOrAssignBtn();
//        SimpleUtils.assertOnFail(" New selected TM doesn't display in scheduled table" , firstNameOfSelectedTM2.equals(schedulePage.getShiftById(selectedShiftId2).findElement(By.className("week-schedule-worker-name")).getText()), false);

        //Click on the Convert to open shift, checkbox is available to offer the shift to any specific TM[optional] Cancel /yes
        //if checkbox is unselected then, shift is convert to open
        selectedShift = schedulePage.clickOnProfileIcon();
        String tmFirstName = selectedShift.findElement(By.className("week-schedule-worker-name")).getText();
        schedulePage.clickOnConvertToOpenShift();
        if (schedulePage.verifyConvertToOpenPopUpDisplay(tmFirstName)) {
            schedulePage.convertToOpenShiftDirectly();
        }
        //if checkbox is select then select team member page will display
        selectedShift = schedulePage.clickOnProfileIcon();
        tmFirstName = selectedShift.findElement(By.className("week-schedule-worker-name")).getText();
        schedulePage.clickOnConvertToOpenShift();
        if (schedulePage.verifyConvertToOpenPopUpDisplay(tmFirstName)) {
            schedulePage.convertToOpenShiftAndOfferToSpecificTMs();
        }

        //After click on Edit Shift Time, the Edit Shift window will display
        schedulePage.clickOnProfileIcon();
        schedulePage.clickOnEditShiftTime();
        schedulePage.verifyEditShiftTimePopUpDisplay();
        schedulePage.clickOnCancelEditShiftTimeButton();
        //Edit shift time and click update button
        schedulePage.editAndVerifyShiftTime(true);
        //Edit shift time and click Cancel button
        schedulePage.editAndVerifyShiftTime(false);

        //Verify Edit/View Meal Break
        if (schedulePage.isEditMealBreakEnabled()){
            //After click on Edit Meal Break Time, the Edit Meal Break window will display
            schedulePage.verifyMealBreakTimeDisplayAndFunctionality(true);
            //Verify Delete Meal Break
            schedulePage.verifyDeleteMealBreakFunctionality();
            //Edit meal break time and click update button
            schedulePage.verifyEditMealBreakTimeFunctionality(true);
            //Edit meal break time and click cancel button
            schedulePage.verifyEditMealBreakTimeFunctionality(false);
        } else
            schedulePage.verifyMealBreakTimeDisplayAndFunctionality(false);

        //verify cancel button
        schedulePage.verifyDeleteShiftCancelButton();

        //verify delete shift
        schedulePage.verifyDeleteShift();
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the budget hour in DM view schedule page for non dg flow")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyBudgetHourInDMViewSchedulePageForNonDGFlowAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();
            float budgetHoursInSchedule = Float.parseFloat(schedulePage.getBudgetNScheduledHoursFromSmartCard().get("Budget"));

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrictDirect();

            ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
            float budgetedHoursInDMViewSchedule = scheduleDMViewPage.getBudgetedHourOfScheduleInDMViewByLocation(location);
            if (budgetHoursInSchedule != 0 && budgetHoursInSchedule == budgetedHoursInDMViewSchedule) {
                SimpleUtils.pass("Verified the budget hour in DM view schedule page is consistent with the value saved in create schedule page!");
            } else {
                SimpleUtils.fail("Verified the budget hour in DM view schedule page is consistent with the value saved in create schedule page! The budget hour in DM view schedule page is " +
                        budgetedHoursInDMViewSchedule + ". The value saved in create schedule page is " + budgetHoursInSchedule, false);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }



    @Automated(automated = "Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Assign TM warning: TM status is already Scheduled at Home location")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAssignTMWarningForTMIsAlreadyScheduledAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            List<String> firstShiftInfo = schedulePage.getTheShiftInfoByIndex(0);
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.selectWorkRole(scheduleWorkRoles.get("GENERAL MANAGER"));
            schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount3.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.selectWorkingDaysOnNewShiftPageByIndex(Integer.parseInt(firstShiftInfo.get(1)));
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.verifyScheduledWarningWhenAssigning(firstShiftInfo.get(0), firstShiftInfo.get(2));
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    // need change case when create new shift
    @Automated(automated = "Automated")
    @Owner(owner = "haya")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify smart card for schedule not publish(include past weeks)")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifySmartCardForScheduleNotPublishAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab("Schedule");
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            if (schedulePage.isWeekGenerated()){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //make edits
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount3.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.selectWorkRole(scheduleWorkRoles.get("GENERAL MANAGER"));
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.saveSchedule();
            //generate and save, should not display number of changes, we set it as 0.
            int changesNotPublished = 0;
            //Verify changes not publish smart card.
            SimpleUtils.assertOnFail("Changes not publish smart card is not loaded!",schedulePage.isSpecificSmartCardLoaded("ACTION REQUIRED"),false);
            schedulePage.verifyChangesNotPublishSmartCard(changesNotPublished);
            schedulePage.verifyLabelOfPublishBtn("Publish");
            String activeWeek = schedulePage.getActiveWeekText();
            schedulePage.clickOnScheduleSubTab("Overview");
            List<String> resultListInOverview = schedulePage.getOverviewData();
            for (String s : resultListInOverview){
                String a = s.substring(1,7);
                if (activeWeek.toLowerCase().contains(a.toLowerCase())){
                    if (s.contains("Unpublished Edits")){
                        SimpleUtils.pass("Warning message in overview page is correct!");
                    } else {
                        SimpleUtils.fail("Warning message is not expected: "+ s.split(",")[4],false);
                    }
                }
            }
        }
        catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "haya")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify smart card for schedule not publish(include past weeks) - republish")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyNumberOnSmartCardForScheduleNotPublishAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab("Schedule");
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();
            if (schedulePage.isWeekGenerated()){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            //make edits and publish
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount3.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.selectWorkRole(scheduleWorkRoles.get("GENERAL MANAGER"));
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();
            //make edits and save
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.customizeNewShiftPage();
            schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount3.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
            schedulePage.selectWorkRole(scheduleWorkRoles.get("GENERAL MANAGER"));
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.saveSchedule();
            //generate and save, should not display number of changes, we set it as 0.
            int changesNotPublished = 1;
            //Verify changes not publish smart card.
            SimpleUtils.assertOnFail("Changes not publish smart card is not loaded!",schedulePage.isSpecificSmartCardLoaded("ACTION REQUIRED"),false);
            schedulePage.verifyChangesNotPublishSmartCard(changesNotPublished);
            schedulePage.verifyLabelOfPublishBtn("Republish");
            String activeWeek = schedulePage.getActiveWeekText();
            schedulePage.clickOnScheduleSubTab("Overview");
            List<String> resultListInOverview = schedulePage.getOverviewData();
            for (String s : resultListInOverview){
                String a = s.substring(1,7);
                if (activeWeek.toLowerCase().contains(a.toLowerCase())){
                    if (s.contains("Unpublished Edits")){
                        SimpleUtils.pass("Warning message in overview page is correct!");
                    } else {
                        SimpleUtils.fail("Warning message is not expected: "+ s.split(",")[4],false);
                    }
                }
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    //Blocking by Refresh button bug: https://legiontech.atlassian.net/browse/SCH-3116
//    @Automated(automated = "Automated")
//    @Owner(owner = "Mary")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
//    @TestName(description = "verify the Unpublished Edits on dashboard and overview page")
//    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
//    public void verifyUnpublishedEditsTextOnDashboardAndOverviewPageAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
//        try{
//            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
//            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
//            schedulePage.clickOnScheduleConsoleMenuItem();
//            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
//                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
//            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
//            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
//                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
//
//            boolean isWeekGenerated = schedulePage.isWeekGenerated();
//            if (!isWeekGenerated){
//                schedulePage.createScheduleForNonDGFlowNewUI();
//            }
//            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
//            schedulePage.addOpenShiftWithLastDay("GENERAL MANAGER");
//            schedulePage.saveSchedule();
//
//            //Verify the Unpublished Edits text on overview page
//            schedulePage.clickOnScheduleConsoleMenuItem();
//            ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
//            List<WebElement> schedulesInOverviewPage = scheduleOverviewPage.getOverviewScheduleWeeks();
//            if (schedulesInOverviewPage != null && schedulesInOverviewPage.size()>0){
//                WebElement warningTextOfCurrentScheduleWeek = schedulesInOverviewPage.get(0).findElement(By.cssSelector("div.text-small.ng-binding"));
//                if (warningTextOfCurrentScheduleWeek != null){
//                    String warningText = warningTextOfCurrentScheduleWeek.getText();
//                    if (warningText !=null && warningText.equals("Unpublished Edits")){
//                        SimpleUtils.pass("Verified the Unpublished Edits on Overview page display correctly. ");
//                    } else{
//                        SimpleUtils.fail("Verified the Unpublished Edits on Overview page display incorrectly. The actual warning text is " + warningText +".", true);
//                    }
//                }
//            } else{
//                SimpleUtils.fail("Overview Page: Schedule weeks not found!" , true);
//            }
//
//            //Verify the Unpublished Edits text on dashboard page
//            dashboardPage.navigateToDashboard();
//            dashboardPage.clickOnRefreshButton();
//            List<WebElement> dashboardScheduleWeeks = dashboardPage.getDashboardScheduleWeeks();
//            if (dashboardScheduleWeeks != null && dashboardScheduleWeeks.size()>0){
//                WebElement warningTextOfCurrentScheduleWeek = dashboardScheduleWeeks.get(1).findElement(By.cssSelector("div.text-small.ng-binding"));
//                if (warningTextOfCurrentScheduleWeek != null){
//                    String warningText = warningTextOfCurrentScheduleWeek.getText();
//                    if (warningText !=null && warningText.equals("Unpublished Edits")){
//                        SimpleUtils.pass("Verified the Unpublished Edits text on Dashboard page display correctly. ");
//                    } else{
//                        SimpleUtils.fail("Verified the Unpublished Edits text on Dashboard page display incorrectly. The actual warning text is " + warningText +".", false);
//                    }
//                }
//            } else{
//                SimpleUtils.fail("Dashboard Page: Schedule weeks not found!" , false);
//            }
//        } catch (Exception e){
//            SimpleUtils.fail(e.getMessage(), false);
//        }
//
//    }



    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the Schedule functionality >  Compliance smartcard")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyComplianceSmartCardFunctionality(String username, String password, String browser, String location)
            throws Exception {
        SchedulePage schedulePage  = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.goToScheduleNewUI();
        if(schedulePage.verifyComplianceShiftsSmartCardShowing() && schedulePage.verifyRedFlagIsVisible()){
            schedulePage.verifyComplianceFilterIsSelectedAftClickingViewShift();
            schedulePage.verifyComplianceShiftsShowingInGrid();
            schedulePage.verifyClearFilterFunction();
        }else
            SimpleUtils.report("There is no compliance and no red flag");
    }



    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the Schedule functionality > Schedule smartcard")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyScheduleFunctionalityScheduleSmartCard(String username, String password, String browser, String location)
            throws Exception {
        ArrayList<LinkedHashMap<String, Float>> scheduleOverviewAllWeekHours = new ArrayList<LinkedHashMap<String, Float>>();
        HashMap<String, Float> scheduleSmartCardHoursWages = new HashMap<>();
        HashMap<String, Float> overviewData = new HashMap<>();
        SchedulePage schedulePage  = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.goToScheduleNewUI();
        boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
        if(!isActiveWeekGenerated){
            schedulePage.createScheduleForNonDGFlowNewUI();
        }
        scheduleSmartCardHoursWages = schedulePage.getScheduleBudgetedHoursInScheduleSmartCard();
        SimpleUtils.report("scheduleSmartCardHoursWages :"+scheduleSmartCardHoursWages);
        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
        scheduleOverviewPage.clickOverviewTab();
        List<WebElement> scheduleOverViewWeeks =  scheduleOverviewPage.getOverviewScheduleWeeks();
        overviewData = scheduleOverviewPage.getWeekHoursByWeekElement(scheduleOverViewWeeks.get(0));
        SimpleUtils.report("overview data :"+scheduleOverviewPage.getWeekHoursByWeekElement(scheduleOverViewWeeks.get(0)));
        if (Math.abs(overviewData.get("guidanceHours") - (scheduleSmartCardHoursWages.get("budgetedHours"))) <= 0.05
                && Math.abs(overviewData.get("scheduledHours") - (scheduleSmartCardHoursWages.get("scheduledHours"))) <= 0.05
                && Math.abs(overviewData.get("otherHours") - (scheduleSmartCardHoursWages.get("otherHours"))) <= 0.05) {
            SimpleUtils.pass("Schedule/Budgeted smartcard-is showing the values in Hours and wages, it is displaying the same data as overview page have for the current week .");
        }else {
            SimpleUtils.fail("Scheduled Hours and Overview Schedule Hours not same",true);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify offers generated for open shift")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyOffersGeneratedForOpenShiftsAsInternalAdmin(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();

            //verify shifts are auto assigned.
            //schedulePage.verifyAllShiftsAssigned();
            //schedulePage.clickOnEditButton();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnProfileIcon();
            schedulePage.clickOnConvertToOpenShift();
            schedulePage.convertToOpenShiftDirectly();
            int index = schedulePage.getTheIndexOfEditedShift();
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();
            schedulePage.clickProfileIconOfShiftByIndex(index);
            schedulePage.clickViewStatusBtn();
            schedulePage.verifyListOfOfferNotNull();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the Schedule functionality > Job Title Filter Functionality > Week View")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void viewAndFilterScheduleWithGroupByJobTitleInWeekView(String username, String password, String browser, String location)
            throws Exception {

        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage = dashboardPage.goToTodayForNewUI();
        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()) , true);

        /*
         *  Navigate to Schedule Week view
         */
        boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
        if(!isActiveWeekGenerated){
            schedulePage.createScheduleForNonDGFlowNewUI();
        }
        boolean isWeekView = true;
        schedulePage.clickOnWeekView();
        schedulePage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyJobTitle.getValue());
        schedulePage.filterScheduleByJobTitle(isWeekView);
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.filterScheduleByJobTitle(isWeekView);
        schedulePage.clickOnCancelButtonOnEditMode();
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the Schedule functionality > Job Title Filter Functionality > Day View")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void viewAndFilterScheduleWithGroupByJobTitleInDayView(String username, String password, String browser, String location)
            throws Exception {

        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage = dashboardPage.goToTodayForNewUI();
        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()) , true);

        /*
         *  Navigate to Schedule day view
         */
        boolean isWeekView = false;
        boolean isWeekGenerated = schedulePage.isWeekGenerated();
        if (!isWeekGenerated){
            schedulePage.createScheduleForNonDGFlowNewUI();
        }
        schedulePage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyJobTitle.getValue());
        schedulePage.filterScheduleByJobTitle(isWeekView);
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.filterScheduleByJobTitle(isWeekView);
        schedulePage.clickOnCancelButtonOnEditMode();
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the Schedule functionality > Job Title Filter Functionality > Combination")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void viewAndFilterScheduleWithGroupByJobTitleFilterCombinationInWeekView(String username, String password, String browser, String location)
            throws Exception {

        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
        /*
         *  Navigate to Schedule week view
         */
        boolean isWeekView = true;
        boolean isWeekGenerated = schedulePage.isWeekGenerated();
        if (!isWeekGenerated){
            schedulePage.createScheduleForNonDGFlowNewUI();
        }

        schedulePage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyJobTitle.getValue());
        schedulePage.filterScheduleByWorkRoleAndJobTitle(isWeekView);
        schedulePage.filterScheduleByShiftTypeAndJobTitle(isWeekView);
        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
        schedulePage.filterScheduleByJobTitle(isWeekView);
        schedulePage.clickOnCancelButtonOnEditMode();
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify Schedules widget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifySchedulesWidgetsAsStoreManager(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
            // Verify Edit mode Dashboard loaded
            liquidDashboardPage.enterEditMode();
            liquidDashboardPage.switchOnWidget(LiquidDashboardTest.widgetType.Schedules.getValue());
            liquidDashboardPage.saveAndExitEditMode();

            // Refresh the dashboard to get the value updated
            dashboardPage.clickOnRefreshButton();

            //verify view schedules link
            List<String> resultListOnWidget = liquidDashboardPage.getDataOnSchedulesWidget();
            liquidDashboardPage.clickOnLinkByWidgetNameAndLinkName(LiquidDashboardTest.widgetType.Schedules.getValue(), LiquidDashboardTest.linkNames.View_Schedules.getValue());
            //verify value on widget
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            List<String> resultListInOverview = schedulePage.getOverviewData();
            if (resultListOnWidget.size()==resultListInOverview.size()){
                for (int i=0;i<resultListInOverview.size();i++){
                    boolean flag = resultListInOverview.get(i).equals(resultListOnWidget.get(i));
                    if (flag){
                        SimpleUtils.pass("Schedules widget: Values on widget are consistent with the one in overview");
                    } else {
                        SimpleUtils.fail("Schedules widget: Values on widget are not consistent with the one in overview!",false);
                    }
                }
            } else {
                SimpleUtils.fail("Schedules widget: something wrong with the number of week displayed!",true);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify UI for common widget")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyCommonUIOfWidgetsAsStoreManager(String browser, String username, String password, String location) {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            LiquidDashboardPage liquidDashboardPage = pageFactory.createConsoleLiquidDashboardPage();
            // Verifiy Edit mode Dashboard loaded
            liquidDashboardPage.enterEditMode();

            //verify switch off Todays_Forcast widget
            liquidDashboardPage.switchOffWidget(LiquidDashboardTest.widgetType.Todays_Forecast.getValue());
            //verify switch on Todays_Forcast widget
            liquidDashboardPage.switchOnWidget(LiquidDashboardTest.widgetType.Todays_Forecast.getValue());
            //verify close Todays_Forcast widget
            liquidDashboardPage.closeWidget(LiquidDashboardTest.widgetType.Todays_Forecast.getValue());
            liquidDashboardPage.switchOnWidget(LiquidDashboardTest.widgetType.Todays_Forecast.getValue());


            //verify switch off Timesheet_Approval_Status widget
            liquidDashboardPage.switchOffWidget(LiquidDashboardTest.widgetType.Timesheet_Approval_Status.getValue());
            //verify switch on Timesheet_Approval_Status widget
            liquidDashboardPage.switchOnWidget(LiquidDashboardTest.widgetType.Timesheet_Approval_Status.getValue());
            //verify close Timesheet_Approval_Status widget
            liquidDashboardPage.closeWidget(LiquidDashboardTest.widgetType.Timesheet_Approval_Status.getValue());
            liquidDashboardPage.switchOnWidget(LiquidDashboardTest.widgetType.Timesheet_Approval_Status.getValue());

/*
        //verify switch off Timesheet_Approval_Rate widget
        liquidDashboardPage.switchOffWidget(widgetType.Timesheet_Approval_Rate.getValue());
        //verify switch on Timesheet_Approval_Rate widget
        liquidDashboardPage.switchOnWidget(widgetType.Timesheet_Approval_Rate.getValue());
        //verify close Timesheet_Approval_Rate widget
        liquidDashboardPage.closeWidget(widgetType.Timesheet_Approval_Rate.getValue());
        liquidDashboardPage.switchOnWidget(widgetType.Timesheet_Approval_Rate.getValue());
*/

            //verify switch off Alerts widget
            liquidDashboardPage.switchOffWidget(LiquidDashboardTest.widgetType.Alerts.getValue());
            //verify switch on Alerts widget
            liquidDashboardPage.switchOnWidget(LiquidDashboardTest.widgetType.Alerts.getValue());
            //verify close Alerts widget
            liquidDashboardPage.closeWidget(LiquidDashboardTest.widgetType.Alerts.getValue());
            liquidDashboardPage.switchOnWidget(LiquidDashboardTest.widgetType.Alerts.getValue());

/*
        //verify switch off Swaps_Covers widget
        liquidDashboardPage.switchOffWidget(widgetType.Swaps_Covers.getValue());
        //verify switch on Swaps_Covers widget
        liquidDashboardPage.switchOnWidget(widgetType.Swaps_Covers.getValue());
        //verify close Swaps_Covers widget
        liquidDashboardPage.closeWidget(widgetType.Swaps_Covers.getValue());
        liquidDashboardPage.switchOnWidget(widgetType.Swaps_Covers.getValue());
*/

            //verify switch off Starting_Soon widget
            liquidDashboardPage.switchOffWidget(LiquidDashboardTest.widgetType.Starting_Soon.getValue());
            //verify switch on Starting_Soon widget
            liquidDashboardPage.switchOnWidget(LiquidDashboardTest.widgetType.Starting_Soon.getValue());
            //verify close Starting_Soon widget
            liquidDashboardPage.closeWidget(LiquidDashboardTest.widgetType.Starting_Soon.getValue());
            liquidDashboardPage.switchOnWidget(LiquidDashboardTest.widgetType.Starting_Soon.getValue());


            //verify switch off Schedules widget
            liquidDashboardPage.switchOffWidget(LiquidDashboardTest.widgetType.Schedules.getValue());
            //verify switch on Schedules widget
            liquidDashboardPage.switchOnWidget(LiquidDashboardTest.widgetType.Schedules.getValue());
            //verify close Schedules widget
            liquidDashboardPage.closeWidget(LiquidDashboardTest.widgetType.Schedules.getValue());
            liquidDashboardPage.switchOnWidget(LiquidDashboardTest.widgetType.Schedules.getValue());

/*
        //verify switch off Open_Shifts widget
        liquidDashboardPage.switchOffWidget(widgetType.Open_Shifts.getValue());
        //verify switch on Open_Shifts widget
        liquidDashboardPage.switchOnWidget(widgetType.Open_Shifts.getValue());
        //verify close Open_Shifts widget
        liquidDashboardPage.closeWidget(widgetType.Open_Shifts.getValue());
        liquidDashboardPage.switchOnWidget(widgetType.Open_Shifts.getValue());
*/

            //verify switch off compliance violation widget
            liquidDashboardPage.switchOffWidget(LiquidDashboardTest.widgetType.Compliance_Violation.getValue());
            //verify switch on compliance violation widget
            liquidDashboardPage.switchOnWidget(LiquidDashboardTest.widgetType.Compliance_Violation.getValue());
            //verify close compliance violation widget
            liquidDashboardPage.closeWidget(LiquidDashboardTest.widgetType.Compliance_Violation.getValue());
            liquidDashboardPage.switchOnWidget(LiquidDashboardTest.widgetType.Compliance_Violation.getValue());

            //verify switch off helpful links widget
            liquidDashboardPage.switchOffWidget(LiquidDashboardTest.widgetType.Helpful_Links.getValue());
            //verify switch on helpful links widget
            liquidDashboardPage.switchOnWidget(LiquidDashboardTest.widgetType.Helpful_Links.getValue());
            //verify close helpful links widget
            liquidDashboardPage.closeWidget(LiquidDashboardTest.widgetType.Helpful_Links.getValue());
            liquidDashboardPage.switchOnWidget(LiquidDashboardTest.widgetType.Helpful_Links.getValue());
            //verify back button to get out of manage page
            liquidDashboardPage.verifyBackBtn();
            //verify if there is update time info icon
            liquidDashboardPage.saveAndExitEditMode();
            liquidDashboardPage.verifyUpdateTimeInfoIcon(LiquidDashboardTest.widgetType.Compliance_Violation.getValue());
            //liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Open_Shifts.getValue());
            liquidDashboardPage.verifyUpdateTimeInfoIcon(LiquidDashboardTest.widgetType.Schedules.getValue());
            liquidDashboardPage.verifyUpdateTimeInfoIcon(LiquidDashboardTest.widgetType.Starting_Soon.getValue());
            //liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Swaps_Covers.getValue());
            liquidDashboardPage.verifyUpdateTimeInfoIcon(LiquidDashboardTest.widgetType.Alerts.getValue());
            //liquidDashboardPage.verifyUpdateTimeInfoIcon(widgetType.Timesheet_Approval_Rate.getValue());
            liquidDashboardPage.verifyUpdateTimeInfoIcon(LiquidDashboardTest.widgetType.Timesheet_Approval_Status.getValue());
            liquidDashboardPage.verifyUpdateTimeInfoIcon(LiquidDashboardTest.widgetType.Helpful_Links.getValue());
            liquidDashboardPage.verifyUpdateTimeInfoIcon(LiquidDashboardTest.widgetType.Todays_Forecast.getValue());
            //verify search input
            liquidDashboardPage.enterEditMode();
            liquidDashboardPage.verifySearchInput(LiquidDashboardTest.widgetType.Helpful_Links.getValue());
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the Schedule functionality > Forecast")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyScheduleFunctionalityForecast(String username, String password, String browser, String location)
            throws Exception {
        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
        scheduleOverviewPage.loadScheduleOverview();
        ForecastPage ForecastPage  = pageFactory.createForecastPage();
        ForecastPage.clickForecast();
        boolean isWeekForecastVisibleAndOpen = ForecastPage.verifyIsWeekForecastVisibleAndOpenByDefault();
        boolean isShopperSelectedByDefaultAndLaborClickable = ForecastPage.verifyIsShopperTypeSelectedByDefaultAndLaborTabIsClickable();
        if (isWeekForecastVisibleAndOpen) {
            if (isShopperSelectedByDefaultAndLaborClickable){
                SimpleUtils.pass("Forecast Functionality show well");
            } else {
                SimpleUtils.warn("there is no shopper in this enterprise!");
            }
        }else {
            SimpleUtils.warn("forecast default functionality work error");
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the Schedule functionality > Labor Forecast")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyScheduleLaborForeCastFunctionality(String username, String password, String browser, String location)
            throws Exception {
        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
        scheduleOverviewPage.loadScheduleOverview();
        ForecastPage ForecastPage  = pageFactory.createForecastPage();
        ForecastPage.clickForecast();
        ForecastPage.clickOnLabor();
        //past+current+future week is visible and enable to navigate to future and past week by '>' and '<' button
        ForecastPage.verifyNextPreviousBtnCorrectOrNot();
        //Work role filter is selected all roles by default, can be selected one or more
        ForecastPage.verifyWorkRoleSelection();
        //After selecting any workrole, Projected Labor bar will display according to work role
        ForecastPage.verifyBudgetedHoursInLaborSummaryWhileSelectDifferentWorkRole();
        //Weather week smartcard is displayed for a week[sun-sat]
        ForecastPage.weatherWeekSmartCardIsDisplayedForAWeek();
        //If some work role has been selected in one week then these will remain selected in every past and future week
        ForecastPage.verifyWorkRoleIsSelectedAftSwitchToPastFutureWeek();
        //After click on refresh, page should get refresh and back to previous page only
        ForecastPage.verifyRefreshBtnInLaborWeekView();

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the Schedule functionality > Shopper Forecast> Weather smartcard")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void validateWeatherSmartCardOnForecastPage(String username, String password, String browser, String location)
            throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
                schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

        String WeatherCardText = "WEATHER";
        ForecastPage forecastPage = pageFactory.createForecastPage();
        forecastPage.clickForecast();
        //Validate Weather Smart card on Week View
        schedulePage.clickOnWeekView();

        Thread.sleep(5000);
        String activeWeekText = schedulePage.getActiveWeekText();

        if (schedulePage.isSmartCardAvailableByLabel(WeatherCardText)) {
            SimpleUtils.pass("Weather Forecart Smart Card appeared for week view duration: '" + activeWeekText + "'");
            String[] splitActiveWeekText = activeWeekText.split(" ");
            String smartCardTextByLabel = schedulePage.getsmartCardTextByLabel(WeatherCardText);
            String weatherTemperature = schedulePage.getWeatherTemperature();

            SimpleUtils.assertOnFail("Weather Forecart Smart Card not contain starting day('" + splitActiveWeekText[0] + "') of active week: '" + activeWeekText + "'",
                    smartCardTextByLabel.toLowerCase().contains(splitActiveWeekText[0].toLowerCase()), true);

            SimpleUtils.assertOnFail("Weather Forecart Smart Card not contain Ending day('" + splitActiveWeekText[0] + "') of active week: '" + activeWeekText + "'",
                    smartCardTextByLabel.toLowerCase().contains(splitActiveWeekText[0].toLowerCase()), true);
            if (weatherTemperature != "")
                SimpleUtils.pass("Weather Forecart Smart Card contains Temperature value: '" + weatherTemperature + "' for the duration: '" +
                        activeWeekText + "'");
            else
                SimpleUtils.fail("Weather Forecart Smart Card not contains Temperature value for the duration: '" + activeWeekText + "'", true);
        } else {
            //SimpleUtils.fail("Weather Forecart Smart Card not appeared for week view duration: '" + activeWeekText + "'", true);
            SimpleUtils.warn("Weather Forecart Smart Card not appeared for week view duration: '" + activeWeekText + "'");
        }

        //Validate Weather Smart card on day View
        schedulePage.clickOnDayView();
        for (int index = 0; index < ScheduleNewUITest.dayCount.Seven.getValue(); index++) {
            if (index != 0)
                schedulePage.navigateWeekViewOrDayViewToPastOrFuture(ScheduleNewUITest.weekViewType.Next.getValue(), ScheduleNewUITest.weekCount.One.getValue());

            String activeDayText = schedulePage.getActiveWeekText();
            if (schedulePage.isSmartCardAvailableByLabel(WeatherCardText)) {
                SimpleUtils.pass("Weather Forecart Smart Card appeared for week view duration: '" + activeDayText + "'");
                String[] splitActiveWeekText = activeDayText.split(" ");
                String smartCardTextByLabel = schedulePage.getsmartCardTextByLabel(WeatherCardText);
                SimpleUtils.assertOnFail("Weather Forecart Smart Card not contain starting day('" + splitActiveWeekText[1] + "') of active day: '" + activeDayText + "'",
                        smartCardTextByLabel.toLowerCase().contains(splitActiveWeekText[1].toLowerCase()), true);
                String weatherTemperature = schedulePage.getWeatherTemperature();
                if (weatherTemperature != "")
                    SimpleUtils.pass("Weather Forecart Smart Card contains Temperature value: '" + weatherTemperature + "' for the duration: '" +
                            activeWeekText + "'");
                else
                    SimpleUtils.pass("Weather Forecart Smart Card not contains Temperature value for the duration: '" + activeWeekText + "'");
            } else {
                //SimpleUtils.fail("Weather Forecart Smart Card not appeared for week view duration: '" + activeWeekText + "'", true);
                SimpleUtils.warn("Weather Forecart Smart Card not appeared for week view duration: '" + activeWeekText + "'");
            }
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the content of new profile page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheContentOfNewProfilePageAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Select one team member to view profile
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            teamPage.selectATeamMemberToViewProfile();

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            //Verify User Profile Section is loaded
            profileNewUIPage.verifyUserProfileSectionIsLoaded();
            //Verify HR Profile Information Section is loaded
            profileNewUIPage.verifyHRProfileInformationSectionIsLoaded();
            //Verify Legion Information Section is loaded
            profileNewUIPage.verifyLegionInformationSectionIsLoaded();
            //Verify Actions Section is loaded
            profileNewUIPage.verifyActionSectionIsLoaded();
            //Verify the fields in User Profile Section are display correctly
            profileNewUIPage.verifyFieldsInUserProfileSection();
            //Verify the fields in HR Profile Information Section are display correctly
            profileNewUIPage.verifyFieldsInHRProfileInformationSection();
            //Verify the fields in Legion Information Section are display correctly
            profileNewUIPage.verifyFieldsInLegionInformationSection();
            //Verify the contents in Actions Section are display correctly
            profileNewUIPage.verifyContentsInActionsSection();
            //Verify Edit and Sync TM Info buttons are display correctly
            profileNewUIPage.verifyEditUserProfileButtonIsLoaded();
            profileNewUIPage.verifySyncTMInfoButtonIsLoaded();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }


    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the content of new profile page in TM View")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheContentOfNewProfilePageInTMViewAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Select one team member to view profile
            dashboardPage.clickOnSubMenuOnProfile("My Profile");

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            //Verify User Profile Section is loaded
            profileNewUIPage.verifyUserProfileSectionIsLoaded();
            //Verify HR Profile Information Section is loaded
            profileNewUIPage.verifyHRProfileInformationSectionIsLoaded();
            //Verify Legion Information Section is loaded
            profileNewUIPage.verifyLegionInformationSectionIsLoaded();
            //Verify Actions Section is loaded
            profileNewUIPage.verifyActionSectionIsLoaded();
            //Verify the fields in User Profile Section are display correctly
            profileNewUIPage.verifyFieldsInUserProfileSection();
            //Verify the fields in HR Profile Information Section are display correctly
            profileNewUIPage.verifyFieldsInHRProfileInformationSection();
            //Verify the fields in Legion Information Section are display correctly
            profileNewUIPage.verifyFieldsInLegionInformationSection();
            //Verify the contents in Actions Section are display correctly
            profileNewUIPage.verifyContentsInActionsSectionInTMView();
            //Verify Edit button is display correctly
            profileNewUIPage.verifyEditUserProfileButtonIsLoaded();

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the Team functionality > Work Preferences")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyTheTeamFunctionalityInWorkPreferencesAsStoreManager(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            dashboardPage.isDashboardPageLoaded();
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.selectATeamMemberToViewProfile();
            teamPage.isProfilePageLoaded();
            teamPage.navigateToWorkPreferencesTab();
            // Verify shift preferences- can be Edit by clicking on pencil icon Changes are being Cancel by clicking on cancel button
            List<String> previousPreferences = teamPage.getShiftPreferences();
            teamPage.clickOnEditShiftPreference();
            SimpleUtils.assertOnFail("Edit Shift Preferences layout failed to load!", teamPage.isEditShiftPreferLayoutLoaded(), true);
            teamPage.setSliderForShiftPreferences();
            teamPage.changeShiftPreferencesStatus();
            teamPage.clickCancelEditShiftPrefBtn();
            List<String> currentPreferences = teamPage.getShiftPreferences();
            if (previousPreferences.containsAll(currentPreferences) && currentPreferences.containsAll(previousPreferences)) {
                SimpleUtils.pass("Shift preferences don't change after cancelling!");
            } else {
                SimpleUtils.fail("Shift preferences are changed after cancelling!", true);
            }
            // Verify shift preferences- can be Edit by clicking on pencil icon Changes are being Saved by clicking on Save button
            teamPage.clickOnEditShiftPreference();
            SimpleUtils.assertOnFail("Edit Shift Preferences layout failed to load!", teamPage.isEditShiftPreferLayoutLoaded(), true);
            List<String> changedShiftPrefs = teamPage.setSliderForShiftPreferences();
            List<String> status = teamPage.changeShiftPreferencesStatus();
            teamPage.clickSaveShiftPrefBtn();
            currentPreferences = teamPage.getShiftPreferences();
            teamPage.verifyCurrentShiftPrefIsConsistentWithTheChanged(currentPreferences, changedShiftPrefs, status);
            // Verify Availability Graph [Edited by manager/Admin]:Weekly Availability/Unavailability is showing by green/Red color
            teamPage.editOrUnLockAvailability();
            SimpleUtils.assertOnFail("Edit Availability layout failed to load!", teamPage.areCancelAndSaveAvailabilityBtnLoaded(), true);
            teamPage.changePreferredHours();
            teamPage.changeBusyHours();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

//
//    @Automated(automated ="Automated")
//    @Owner(owner = "Nora")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
//    @TestName(description = "Verify the team functionality in Roster - Sort")
//    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
//    public void verifyTheTeamFunctionalityInRosterForSort(String browser, String username, String password, String location) throws Exception {
//        try {
//            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
//
//            // Check whether the location is location group or not
//            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
//            schedulePage.clickOnScheduleConsoleMenuItem();
//            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
//            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
//            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
//            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
//            if(isActiveWeekGenerated){
//                schedulePage.unGenerateActiveScheduleScheduleWeek();
//            }
//            boolean isLocationGroup = schedulePage.isLocationGroup();
//
//            // Verify TM Count is correct from roster
//            TeamPage teamPage = pageFactory.createConsoleTeamPage();
//            teamPage.goToTeam();
//            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
//            teamPage.verifyTMCountIsCorrectOnRoster();
//            // Verify Search Team Members is working correctly
//            List<String> testStrings = new ArrayList<>(Arrays.asList("jam", "boris", "Retail", "a"));
//            teamPage.verifyTheFunctionOfSearchTMBar(testStrings);
//            // Verify the column in roster page
//            teamPage.verifyTheColumnInRosterPage(isLocationGroup);
//            // Verify NAME column can be sorted in ascending or descending order
//            teamPage.verifyTheSortFunctionInRosterByColumnName("NAME");
//            // Verify EMPLOYEE ID column can be sorted in ascending or descending order
//            teamPage.verifyTheSortFunctionInRosterByColumnName("EMPLOYEE ID");
//            // Verify JOB TITLE column can be sorted in ascending or descending order
//            teamPage.verifyTheSortFunctionInRosterByColumnName("JOB TITLE");
//        } catch (Exception e){
//            SimpleUtils.fail(e.getMessage(), false);
//        }
//    }

//
//    @Automated(automated ="Automated")
//    @Owner(owner = "Estelle")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
//    @TestName(description = "Verify the Schedule functionality > Overview")
//    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
//    public void verifyScheduleFunctionalityOverviewAsStoreManager(String username, String password, String browser, String location) throws Exception {
//        try {
//            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
//            schedulePage.clickOnScheduleConsoleMenuItem();
//            schedulePage.clickOnScheduleSubTab(ScheduleTest.SchedulePageSubTabText.Overview.getValue());
//            ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
//
//            //	Current +2 month calendar is visible
//            SimpleUtils.assertOnFail("Current +2 month calendar is visible", scheduleOverviewPage.isCurrent2MonthCalendarVisible(), false);
//            //"<" Button is navigate to previous month calendar, After Clicking on "<" , ">" button is enabled and navigating to future month calendar
//            scheduleOverviewPage.verifyNavigation();
//            //	Schedule week & its status is visible: if it is finalized,draft,guidance,published
//            List<HashMap<String, String>> weekDuration = scheduleOverviewPage.getOverviewPageWeeksDuration();
//            List<String> scheduleWeekStatus = scheduleOverviewPage.getScheduleWeeksStatus();
//            if (scheduleWeekStatus.contains("Published") || scheduleWeekStatus.contains("Draft")
//                    || scheduleWeekStatus.contains("Finalized") || scheduleWeekStatus.contains("Guidance")) {
//                SimpleUtils.pass("Schedule week & its status is visible");
//            } else if (weekDuration.size() > 0) {
//                SimpleUtils.pass("Schedule week is visible");
//            } else
//                SimpleUtils.fail("Schedule week & its status is not visible", true);
//            //	Current week is in dark blue color
//            SimpleUtils.assertOnFail("Current Week not Highlighted!", scheduleOverviewPage.isCurrentWeekDarkBlueColor(), false);
//            //	Current Date is in Red color
//            SimpleUtils.assertOnFail("Current Date is not in Red color", scheduleOverviewPage.isCurrentDateRed(), false);
//
//            //	Weekly Budgeted/Scheduled,other hour are showing in overview and matching with the Schedule smartcard of Schedule page
//            List<WebElement> scheduleOverViewWeeks = scheduleOverviewPage.getOverviewScheduleWeeks();
//            HashMap<String, Float> overviewData = scheduleOverviewPage.getWeekHoursByWeekElement(scheduleOverViewWeeks.get(0));
//            //	user can click on Schedule week which will navigate to Schedule page
//            scheduleOverviewPage.clickOnCurrentWeekToOpenSchedule();
//            SimpleUtils.pass("user can click on Schedule week which will navigate to Schedule page");
//
//            boolean isWeekGenerated = schedulePage.isWeekGenerated();
//            if (!isWeekGenerated) {
//                schedulePage.createScheduleForNonDGFlowNewUI();
//            }
//            HashMap<String, Float> scheduleSmartCardHoursWages = schedulePage.getScheduleBudgetedHoursInScheduleSmartCard();
//            if ((scheduleSmartCardHoursWages.get("budgetedHours") - overviewData.get("guidanceHours") <= 0.05)
//                    & (scheduleSmartCardHoursWages.get("scheduledHours") - overviewData.get("scheduledHours") <= 0.05)
//                    & (scheduleSmartCardHoursWages.get("otherHours") - overviewData.get("otherHours") <= 0.05)) {
//                SimpleUtils.pass("Schedule/Budgeted smartcard-is showing the values in Hours and wages, it is displaying the same data as overview page have for the current week .");
//            } else {
//                SimpleUtils.fail("Scheduled Hours and Overview Schedule Hours not same", false);
//            }
//
//            schedulePage.clickOnScheduleSubTab(ScheduleTest.SchedulePageSubTabText.Overview.getValue());
//            //	After Generating Schedule, status will be in draft and scheduled hour will also updated.
//            List<WebElement> overviewPageScheduledWeeks = scheduleOverviewPage.getOverviewScheduleWeeks();
//            Float guidanceHoursForGuidanceSchedule = 0.0f;
//            Float scheduledHoursForGuidanceSchedule = 0.0f;
//            Float otherHoursForGuidanceSchedule = 0.0f;
//            for (int i = 0; i < overviewPageScheduledWeeks.size(); i++) {
//                if (overviewPageScheduledWeeks.get(i).getText().toLowerCase().contains(LoginTest.overviewWeeksStatus.Guidance.getValue().toLowerCase())) {
//                    HashMap<String, Float> overviewDataInGuidance = scheduleOverviewPage.getWeekHoursByWeekElement(scheduleOverViewWeeks.get(i));
//                    if (!overviewDataInGuidance.get("guidanceHours").equals(guidanceHoursForGuidanceSchedule) & overviewDataInGuidance.get("scheduledHours").equals(scheduledHoursForGuidanceSchedule) & overviewDataInGuidance.get("otherHours").equals(otherHoursForGuidanceSchedule)) {
//                        SimpleUtils.pass("If any week is in Guidance status, then only Budgeted hours are showing, scheduledHours and otherHours are all zero");
//                    } else
//                        SimpleUtils.fail("this status of this week is not in Guidance", false);
//
//                    String activityInfoBeforeGenerated = null;
//                    String scheduleStatusAftGenerated = null;
//                    scheduleOverviewPage.clickOnGuidanceBtnOnOverview(i);
//                    Thread.sleep(5000);
//                    if (!schedulePage.isWeekGenerated()) {
//                        schedulePage.createScheduleForNonDGFlowNewUI();
//                        schedulePage.clickOnScheduleSubTab(ScheduleTest.SchedulePageSubTabText.Overview.getValue());
//
//                        List<String> scheduleActivityInfo = scheduleOverviewPage.getScheduleActivityInfo();
//                        String activityInfoAfterGenerateSchedule = scheduleActivityInfo.get(i);
//                        List<String> scheduleWeekStatus2 = scheduleOverviewPage.getScheduleWeeksStatus();
//                        scheduleStatusAftGenerated = scheduleWeekStatus2.get(i);
//                        if (scheduleStatusAftGenerated.equals("Draft")) {
//                            SimpleUtils.pass("After Generating Schedule, status will be in draft");
//                        }
//                        if (activityInfoAfterGenerateSchedule.contains(username.substring(0, 6)) & !activityInfoAfterGenerateSchedule.equals(activityInfoBeforeGenerated)) {
//                            //	whoever made the changes in Schedule defined in Activity
//                            //	Profile icon of user is in round shape and his/her name is showing along with Time and date[when he/she has made the changes]
//                            SimpleUtils.pass("Profile icon of user is updated by current user");
//                        }
//                    }
//                    break;
//
//                } else {
//                    SimpleUtils.report("there is no guidance schedule");
//                }
//            }
//        } catch (Exception e) {
//            SimpleUtils.fail(e.getMessage(), false);
//        }
//    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the content of Activities page after click on the Activities button")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOfActivityPageAsStoreManager(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.verifyFiveActivityButtonsLoaded();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate to close Activity Feed")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyToCloseActivityFeedAsStoreManager(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.verifyClickOnActivityCloseButton();
            if (activityPage.isActivityContainerPoppedUp()) {
                SimpleUtils.fail("Activity pop up container is not closed!", false);
            } else {
                SimpleUtils.pass("Activity pop up container is closed Successfully!");
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the navigation in each tab is normal on Activities page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyNavigationOfEachTabOnActivityAsStoreManager(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.TimeOff.getValue(), ActivityTest.indexOfActivityType.TimeOff.name());
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftOffer.getValue(), ActivityTest.indexOfActivityType.ShiftOffer.name());
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftSwap.getValue(), ActivityTest.indexOfActivityType.ShiftSwap.name());
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ProfileUpdate.getValue(), ActivityTest.indexOfActivityType.ProfileUpdate.name());
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.Schedule.getValue(), ActivityTest.indexOfActivityType.Schedule.name());
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate access controls on Activities page when logon with Admin/TM or SM switch to employer view")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyAccessControlsOnActivitiesPage(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();

            //Verify Activity Feed as admin
            if (!activityPage.isActivityBellIconLoaded())
                SimpleUtils.pass("Admin view have no access to see Activity Feed as expected");
            else SimpleUtils.fail("Admin view can see Activity Feed unexpectedly",true);
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            // Verify Activity Feed as Store Manager
            String fileName = "UsersCredentials.json";
            fileName = SimpleUtils.getEnterprise("CinemarkWkdy_Enterprise")+fileName;
            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
            Object[][] teamMemberCredentials = userCredentials.get("StoreManager");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                    , String.valueOf(teamMemberCredentials[0][2]));
            dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            if (activityPage.isActivityBellIconLoaded()) {
                SimpleUtils.pass("SM view have access to see Activity Feed successfully");
            } else {
                SimpleUtils.fail("SM view failed to see Activity Feed",true);
            }

            // Verify Activity Feed as Store Manager Employee View
            dashboardPage.clickOnProfileIconOnDashboard();
            dashboardPage.clickOnSwitchToEmployeeView();
            if (!activityPage.isActivityBellIconLoaded()) {
                SimpleUtils.pass("SM Employee view have no access to see Activity Feed successfully");
            } else {
                SimpleUtils.warn("SM Employee view still have access to see Activity Feed unexpectedly since this bug: https://legiontech.atlassian.net/browse/SF-323");
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated ="Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the content when there is no notification in every activity tab")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheContentOnActivityAsStoreManager(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyClickOnActivityIcon();
            activityPage.verifyTheContentOnActivity();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }


//    @Automated(automated ="Automated")
//    @Owner(owner = "Estelle")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
//    @TestName(description = "Validate the activity of publish or update schedule")
//    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
//    public void verifyActivityOfPublishUpdateScheduleAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
//
//        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
//        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
//        schedulePage.clickOnScheduleConsoleMenuItem();
//        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
//        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
//        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
//
//        schedulePage.navigateToNextWeek();
//        //make publish schedule activity
//        boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
//        if (isActiveWeekGenerated){
//            schedulePage.unGenerateActiveScheduleScheduleWeek();
//        }
//        schedulePage.createScheduleForNonDGFlowNewUI();
//        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
//        schedulePage.deleteTMShiftInWeekView("Unassigned");
//        schedulePage.saveSchedule();
//        schedulePage.publishActiveSchedule();
//        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
//        String requestUserName = profileNewUIPage.getNickNameFromProfile();
//        LoginPage loginPage = pageFactory.createConsoleLoginPage();
//        loginPage.logOut();
//
//
//        // Login as Store Manager
//        String fileName = "UserCredentialsForComparableSwapShifts.json";
//        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
//        fileName = "UsersCredentials.json";
//        fileName = SimpleUtils.getEnterprise("CinemarkWkdy_Enterprise")+fileName;
//        userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
//        Object[][] teamMemberCredentials = userCredentials.get("StoreManager");
//        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
//                , String.valueOf(teamMemberCredentials[0][2]));
//        dashboardPage = pageFactory.createConsoleDashboardPage();
//        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
//        schedulePage.clickOnScheduleConsoleMenuItem();
//        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
//        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
//        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
//        schedulePage.navigateToNextWeek();
//
//        // Verify Schedule publish activity are loaded
//
//        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
//        activityPage.verifyActivityBellIconLoaded();
//        activityPage.verifyClickOnActivityIcon();
//        activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.Schedule.getValue(), ActivityTest.indexOfActivityType.Schedule.name());
//        activityPage.verifyActivityOfPublishSchedule(requestUserName);
//        activityPage.verifyClickOnActivityCloseButton();
//
//        //make update schedule activity to add one open shift
//        //schedulePage.clickOnDayView();
//        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
//        schedulePage.deleteTMShiftInWeekView("Unassigned");
//        // This method is used for the old UI
//        //schedulePage.clickNewDayViewShiftButtonLoaded();
//        schedulePage.clickOnDayViewAddNewShiftButton();
//        schedulePage.customizeNewShiftPage();
//        schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount3.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
//        schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
//        schedulePage.selectWorkRole(scheduleWorkRoles.get("GENERAL MANAGER"));
//        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
//        schedulePage.clickOnCreateOrNextBtn();
//        schedulePage.saveSchedule();
//        schedulePage.publishActiveSchedule();
//
//
//        // Verify Schedule update activity are
//
//        String requestUserNameSM = profileNewUIPage.getNickNameFromProfile();
//        activityPage.verifyActivityBellIconLoaded();
//        activityPage.verifyClickOnActivityIcon();
//        activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.Schedule.getValue(), ActivityTest.indexOfActivityType.Schedule.name());
//        activityPage.verifyActivityOfUpdateSchedule(requestUserNameSM);
//    }


//
//    @Automated(automated = "Automated")
//    @Owner(owner = "Estelle")
//    @Enterprise(name = "KendraScott2_Enterprise")
//    @TestName(description = "Validate activity for claim the open shift")
//    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
//    public void verifyActivityOfClaimOpenShiftAsTeamLead(String browser, String username, String password, String location) throws Exception {
//        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
//        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
//        String teamMemberName = profileNewUIPage.getNickNameFromProfile();
//        LoginPage loginPage = pageFactory.createConsoleLoginPage();
//        loginPage.logOut();
//
//        String fileName = "UsersCredentials.json";
//        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
//        fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise") + fileName;
//        userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
//        Object[][] credential = userCredentials.get("InternalAdmin");
//        loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1]), String.valueOf(credential[0][2]));
//
//        // 1.Checking configuration in controls
//        String option = "Always";
//        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
//        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
//        controlsNewUIPage.clickOnControlsConsoleMenu();
//        controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
//        boolean isScheduleCollaboration = controlsNewUIPage.isControlsScheduleCollaborationLoaded();
//        SimpleUtils.assertOnFail("Controls Page: Schedule Collaboration Section not Loaded.", isScheduleCollaboration, true);
//        //String selectedOption = controlsNewUIPage.getIsApprovalByManagerRequiredWhenEmployeeClaimsOpenShiftSelectedOption();
//        controlsNewUIPage.updateOpenShiftApprovedByManagerOption(option);
//        // 2.admin create one manual open shift and assign to specific TM
//        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
//        schedulePage.clickOnScheduleConsoleMenuItem();
//        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
//        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
//        schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
//        //to generate schedule  if current week is not generated
//        schedulePage.navigateToNextWeek();
//        boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
//        if(!isActiveWeekGenerated){
//            schedulePage.createScheduleForNonDGFlowNewUI();
//        }
//        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
//        schedulePage.deleteTMShiftInWeekView("Unassigned");
//        schedulePage.deleteTMShiftInWeekView(teamMemberName);
//        schedulePage.saveSchedule();
//
//        schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
//        schedulePage.clickOnDayViewAddNewShiftButton();
//        schedulePage.customizeNewShiftPage();
//        schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
//        schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
//        schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
//        schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
//        schedulePage.clickOnCreateOrNextBtn();
//        schedulePage.searchTeamMemberByName(teamMemberName);
//        schedulePage.clickOnOfferOrAssignBtn();
//        schedulePage.saveSchedule();
//        schedulePage.publishActiveSchedule();
//        loginPage.logOut();
//
//        // 3.Login with the TM to claim the shift
//        loginToLegionAndVerifyIsLoginDone(username, password, location);
//        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
//        dashboardPage.goToTodayForNewUI();
//        schedulePage.navigateToNextWeek();
//        schedulePage.isSchedule();
//        String cardName = "WANT MORE HOURS?";
//        SimpleUtils.assertOnFail("Smart Card: " + cardName + " not loaded Successfully!", schedulePage.isSpecificSmartCardLoaded(cardName), false);
//        String linkName = "View Shifts";
//        schedulePage.clickLinkOnSmartCardByName(linkName);
//        SimpleUtils.assertOnFail("Open shifts not loaed Successfully!", schedulePage.areShiftsPresent(), false);
//        List<String> claimShift = new ArrayList<>(Arrays.asList("Claim Shift"));
//        schedulePage.selectOneShiftIsClaimShift(claimShift);
//        schedulePage.clickTheShiftRequestByName(claimShift.get(0));
//        schedulePage.verifyClickAgreeBtnOnClaimShiftOffer();
//
//        loginPage.logOut();
//
//        // 4.Login with SM to check activity
//        Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
//        loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
//                , String.valueOf(storeManagerCredentials[0][2]));
//        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
//        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
//        activityPage.verifyActivityBellIconLoaded();
//        activityPage.verifyClickOnActivityIcon();
//        activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftOffer.getValue(), ActivityTest.indexOfActivityType.ShiftOffer.name());
//        activityPage.verifyActivityOfShiftOffer(teamMemberName);
//        activityPage.approveOrRejectShiftOfferRequestOnActivity(teamMemberName, ActivityTest.approveRejectAction.Approve.getValue());
//
//    }


//    @Automated(automated ="Automated")
//    @Owner(owner = "Julie")
//    @Enterprise(name = "KendraScott2_Enterprise")
//    @TestName(description = "Validate the functioning of Reject button on pending Reject for Cover the shift")
//    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
//    public void verifyRejectCoverRequestOfShiftSwapActivityAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
//        // Configuration in controls :Is approval by Manager required when an employee claims a shift swap or cover request?-Always
//        // TM's next week's schedule must be published before running this test case
//        // Cover TM should be in the list of Cover Request Status window
//        // Cover TM should be not on the schedule at the same day with requested TM and is defined in "UserCredentialsForComparableSwapShifts.json"
//        try {
//            prepareTheSwapShiftsAsInternalAdmin(browser, username, password, location);
//            SimpleUtils.report("Need to set 'Is approval by Manager required when an employee claims a shift swap or cover request?' to 'Always' First!");
//            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
//            controlsPage.gotoControlsPage();
//            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
//            SimpleUtils.assertOnFail("Controls Page not loaded Successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
//            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
//            SimpleUtils.assertOnFail("Schedule Collaboration Page not loaded Successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
//            String option = "Always";
//            controlsNewUIPage.updateSwapAndCoverRequestIsApprovalRequired(option);
//
//            LoginPage loginPage = pageFactory.createConsoleLoginPage();
//            loginPage.logOut();
//
//            List<String> swapNames = new ArrayList<>();
//            String fileName = "UserCredentialsForComparableSwapShifts.json";
//            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
//            for (Map.Entry<String, Object[][]> entry : userCredentials.entrySet()) {
//                if (!entry.getKey().equals("Cover TM")) {
//                    swapNames.add(entry.getKey());
//                    SimpleUtils.pass("Get Swap User name: " + entry.getKey());
//                }
//            }
//            Object[][] credential = null;
//            credential = userCredentials.get(swapNames.get(0));
//            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
//                    , String.valueOf(credential[0][2]));
//            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
//            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
//            String requestUserName = profileNewUIPage.getNickNameFromProfile();
//            if (dashboardPage.isSwitchToEmployeeViewPresent()) {
//                dashboardPage.clickOnSwitchToEmployeeView();
//            }
//            SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
//            schedulePage.isSchedule();
//            schedulePage.navigateToNextWeek();
//            schedulePage.navigateToNextWeek();
//
//            // For Cover Feature
//            List<String> swapCoverRequests = new ArrayList<>(Arrays.asList("Request to Swap Shift", "Request to Cover Shift"));
//            int index = schedulePage.verifyClickOnAnyShift();
//            String request = "Request to Cover Shift";
//            schedulePage.clickTheShiftRequestByName(request);
//            // Validate the Submit button feature
//            String title = "Submit Cover Request";
//            SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), false);
//            schedulePage.verifyClickOnSubmitButton();
//
//            loginPage.logOut();
//
//            credential = userCredentials.get(swapNames.get(1));
//            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
//                    , String.valueOf(credential[0][2]));
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
//            String coverName = profileNewUIPage.getNickNameFromProfile();
//            if (dashboardPage.isSwitchToEmployeeViewPresent())
//                dashboardPage.clickOnSwitchToEmployeeView();
//            dashboardPage.goToTodayForNewUI();
//            schedulePage.isSchedule();
//            schedulePage.navigateToNextWeek();
//            schedulePage.navigateToNextWeek();
//
//            // Validate that smartcard is available to recipient team member
//            String smartCard = "WANT MORE HOURS?";
//            SimpleUtils.assertOnFail("Smart Card: " + smartCard + " not loaded Successfully!", schedulePage.isSpecificSmartCardLoaded(smartCard), false);
//            // Validate the availability of all cover request shifts in schedule table
//            String linkName = "View Shifts";;
//            schedulePage.clickLinkOnSmartCardByName(linkName);
//            SimpleUtils.assertOnFail("Open shifts not loaded Successfully!", schedulePage.areShiftsPresent(), false);
//            // Validate the availability of Claim Shift Request popup
//            String requestName = "Claim Shift";
//            schedulePage.clickTheShiftRequestToClaimShift(requestName, requestUserName);
//            // Validate the clickability of I Agree button
//            schedulePage.verifyClickAgreeBtnOnClaimShiftOffer();
//
//            loginPage.logOut();
//
//            // Login as Store Manager
//            fileName = "UsersCredentials.json";
//            fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
//            userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
//            Object[][] teamMemberCredentials = userCredentials.get("StoreManager");
//            loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
//                    , String.valueOf(teamMemberCredentials[0][2]));
//            dashboardPage = pageFactory.createConsoleDashboardPage();
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
//
//            // Verify Activity Icon is loaded and approve the cover shift request
//            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
//            activityPage.verifyActivityBellIconLoaded();
//            activityPage.verifyClickOnActivityIcon();
//            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftSwap.getValue(), ActivityTest.indexOfActivityType.ShiftSwap.name());
//            activityPage.approveOrRejectShiftCoverRequestOnActivity(requestUserName, coverName, ActivityTest.approveRejectAction.Reject.getValue());
//        } catch (Exception e) {
//            SimpleUtils.fail(e.getMessage(),false);
//        }
//    }

//    @Automated(automated ="Automated")
//    @Owner(owner = "Nora")
//    @Enterprise(name = "KendraScott2_Enterprise")
//    @TestName(description = "Validate the content of Shift Swap activity when TM request to swap the shift")
//    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
//    public void verifyTheContentOfShiftSwapActivityAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
//        prepareTheSwapShiftsAsInternalAdmin(browser, username, password, location);
//        SimpleUtils.report("Need to set 'Is approval by Manager required when an employee claims a shift swap or cover request?' to 'Always' First!");
//        ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
//        controlsPage.gotoControlsPage();
//        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
//        SimpleUtils.assertOnFail("Controls Page not loaded Successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
//        controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
//        SimpleUtils.assertOnFail("Schedule Collaboration Page not loaded Successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
//        String option = "Always";
//        controlsNewUIPage.updateSwapAndCoverRequestIsApprovalRequired(option);
//
//        LoginPage loginPage = pageFactory.createConsoleLoginPage();
//        loginPage.logOut();
//
//        List<String> swapNames = new ArrayList<>();
//        String fileName = "UserCredentialsForComparableSwapShifts.json";
//        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
//        for (Map.Entry<String, Object[][]> entry : userCredentials.entrySet()) {
//            if (!entry.getKey().equals("Cover TM")) {
//                swapNames.add(entry.getKey());
//                SimpleUtils.pass("Get Swap User name: " + entry.getKey());
//            }
//        }
//        Object[][] credential = null;
//        credential = userCredentials.get(swapNames.get(0));
//        loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
//                , String.valueOf(credential[0][2]));
//        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//        SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
//        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
//        String requestUserName = profileNewUIPage.getNickNameFromProfile();
//        if (dashboardPage.isSwitchToEmployeeViewPresent()) {
//            dashboardPage.clickOnSwitchToEmployeeView();
//        }
//        SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
//        schedulePage.isSchedule();
//        schedulePage.navigateToNextWeek();
//        schedulePage.navigateToNextWeek();
//
//        // For Swap Feature
//        List<String> swapCoverRequsts = new ArrayList<>(Arrays.asList("Request to Swap Shift", "Request to Cover Shift"));
//        int index = schedulePage.verifyClickOnAnyShift();
//        String request = "Request to Swap Shift";
//        String title = "Find Shifts to Swap";
//        schedulePage.clickTheShiftRequestByName(request);
//        SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), true);
//        schedulePage.verifyComparableShiftsAreLoaded();
//        schedulePage.verifySelectMultipleSwapShifts();
//        // Validate the Submit button feature
//        schedulePage.verifyClickOnNextButtonOnSwap();
//        title = "Submit Swap Request";
//        SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), false);
//        schedulePage.verifyClickOnSubmitButton();
//        // Validate the disappearence of Request to Swap and Request to Cover option
//        schedulePage.clickOnShiftByIndex(index);
//        if (!schedulePage.verifyShiftRequestButtonOnPopup(swapCoverRequsts)) {
//            SimpleUtils.pass("Request to Swap and Request to Cover options are disappear");
//        }else {
//            SimpleUtils.fail("Request to Swap and Request to Cover options are still shown!", false);
//        }
//
//        loginPage.logOut();
//        credential = userCredentials.get(swapNames.get(1));
//        loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
//                , String.valueOf(credential[0][2]));
//        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
//        String respondUserName = profileNewUIPage.getNickNameFromProfile();
//        if (dashboardPage.isSwitchToEmployeeViewPresent()) {
//            dashboardPage.clickOnSwitchToEmployeeView();
//        }
//        dashboardPage.goToTodayForNewUI();
//        schedulePage.isSchedule();
//        schedulePage.navigateToNextWeek();
//        schedulePage.navigateToNextWeek();
//
//        // Validate that swap request smartcard is available to recipient team member
//        String smartCard = "SWAP REQUESTS";
//        schedulePage.isSmartCardAvailableByLabel(smartCard);
//        // Validate the availability of all swap request shifts in schedule table
//        String linkName = "View All";
//        schedulePage.clickLinkOnSmartCardByName(linkName);
//        schedulePage.verifySwapRequestShiftsLoaded();
//        // Validate that recipient can claim the swap request shift.
//        schedulePage.verifyClickAcceptSwapButton();
//
//        loginPage.logOut();
//
//        // Login as Store Manager
//        fileName = "UsersCredentials.json";
//        fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
//        userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
//        Object[][] teamMemberCredentials = userCredentials.get("StoreManager");
//        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
//                , String.valueOf(teamMemberCredentials[0][2]));
//        dashboardPage = pageFactory.createConsoleDashboardPage();
//        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
//
//        // Verify Activity Icon is loaded
//        String actionLabel = "requested";
//        ActivityPage activityPage = pageFactory.createConsoleActivityPage();
//        activityPage.verifyActivityBellIconLoaded();
//        activityPage.verifyClickOnActivityIcon();
//        activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, respondUserName, actionLabel, true);
//        activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftSwap.getValue(), ActivityTest.indexOfActivityType.ShiftSwap.name());
//        activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, respondUserName, actionLabel, false);
//        activityPage.approveOrRejectShiftSwapRequestOnActivity(requestUserName, respondUserName, ActivityTest.approveRejectAction.Approve.getValue());
//    }
//
//    @Automated(automated ="Automated")
//    @Owner(owner = "Julie")
//    @Enterprise(name = "KendraScott2_Enterprise")
//    @TestName(description = "Validate the content of Shift Swap activity when TM request to swap the shift")
//    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
//    public void verifyTheContentOfShiftSwapActivityAsStoreManager(String browser, String username, String password, String location) throws Exception {
//        try {
//            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
//            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
//            activityPage.verifyClickOnActivityIcon();
//            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftSwap.getValue(), ActivityTest.indexOfActivityType.ShiftSwap.name());
//            activityPage.verifyTheContentOfShiftSwapActivity();
//        } catch (Exception e) {
//            SimpleUtils.fail(e.getMessage(),false);
//        }
//    }
//
//    @Automated(automated ="Automated")
//    @Owner(owner = "Nora")
//    @Enterprise(name = "KendraScott2_Enterprise")
//    @TestName(description = "Validate the functioning of Approve button on pending Approval for swap the shift")
//    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
//    public void verifyTheFunctionOfShiftSwapActivityAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
//        try {
//            prepareTheSwapShiftsAsInternalAdmin(browser, username, password, location);
//            SimpleUtils.report("Need to set 'Is approval by Manager required when an employee claims a shift swap or cover request?' to 'Always' First!");
//            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
//            controlsPage.gotoControlsPage();
//            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
//            SimpleUtils.assertOnFail("Controls Page not loaded Successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
//            controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
//            SimpleUtils.assertOnFail("Schedule Collaboration Page not loaded Successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
//            String option = "Always";
//            controlsNewUIPage.updateSwapAndCoverRequestIsApprovalRequired(option);
//
//            LoginPage loginPage = pageFactory.createConsoleLoginPage();
//            loginPage.logOut();
//
//            List<String> swapNames = new ArrayList<>();
//            String fileName = "UserCredentialsForComparableSwapShifts.json";
//            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
//            for (Map.Entry<String, Object[][]> entry : userCredentials.entrySet()) {
//                if (!entry.getKey().equals("Cover TM")) {
//                    swapNames.add(entry.getKey());
//                    SimpleUtils.pass("Get Swap User name: " + entry.getKey());
//                }
//            }
//            Object[][] credential = null;
//            credential = userCredentials.get(swapNames.get(0));
//            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
//                    , String.valueOf(credential[0][2]));
//            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
//            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
//            String requestUserName = profileNewUIPage.getNickNameFromProfile();
//            if (dashboardPage.isSwitchToEmployeeViewPresent()) {
//                dashboardPage.clickOnSwitchToEmployeeView();
//            }
//            SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
//            schedulePage.isSchedule();
//            schedulePage.navigateToNextWeek();
//            schedulePage.navigateToNextWeek();
//
//            // For Swap Feature
//            List<String> swapCoverRequsts = new ArrayList<>(Arrays.asList("Request to Swap Shift", "Request to Cover Shift"));
//            int index = schedulePage.verifyClickOnAnyShift();
//            String request = "Request to Swap Shift";
//            String title = "Find Shifts to Swap";
//            schedulePage.clickTheShiftRequestByName(request);
//            SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), true);
//            schedulePage.verifyComparableShiftsAreLoaded();
//            schedulePage.verifySelectMultipleSwapShifts();
//            // Validate the Submit button feature
//            schedulePage.verifyClickOnNextButtonOnSwap();
//            title = "Submit Swap Request";
//            SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), false);
//            schedulePage.verifyClickOnSubmitButton();
//            // Validate the disappearence of Request to Swap and Request to Cover option
//            schedulePage.clickOnShiftByIndex(index);
//            if (!schedulePage.verifyShiftRequestButtonOnPopup(swapCoverRequsts)) {
//                SimpleUtils.pass("Request to Swap and Request to Cover options are disappear");
//            } else {
//                SimpleUtils.fail("Request to Swap and Request to Cover options are still shown!", false);
//            }
//
//            loginPage.logOut();
//
//            credential = userCredentials.get(swapNames.get(1));
//            loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
//                    , String.valueOf(credential[0][2]));
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
//            String respondUserName = profileNewUIPage.getNickNameFromProfile();
//            if (dashboardPage.isSwitchToEmployeeViewPresent()) {
//                dashboardPage.clickOnSwitchToEmployeeView();
//            }
//            dashboardPage.goToTodayForNewUI();
//            schedulePage.isSchedule();
//            schedulePage.navigateToNextWeek();
//            schedulePage.navigateToNextWeek();
//
//            // Validate that swap request smartcard is available to recipient team member
//            String smartCard = "SWAP REQUESTS";
//            schedulePage.isSmartCardAvailableByLabel(smartCard);
//            // Validate the availability of all swap request shifts in schedule table
//            String linkName = "View All";
//            schedulePage.clickLinkOnSmartCardByName(linkName);
//            schedulePage.verifySwapRequestShiftsLoaded();
//            // Validate that recipient can claim the swap request shift.
//            schedulePage.verifyClickAcceptSwapButton();
//
//            loginPage.logOut();
//
//            // Login as Store Manager
//            fileName = "UsersCredentials.json";
//            fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise") + fileName;
//            userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
//            Object[][] teamMemberCredentials = userCredentials.get("StoreManager");
//            loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
//                    , String.valueOf(teamMemberCredentials[0][2]));
//            dashboardPage = pageFactory.createConsoleDashboardPage();
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
//
//            // Verify Activity Icon is loaded
//            String actionLabel = "requested";
//            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
//            activityPage.verifyActivityBellIconLoaded();
//            activityPage.verifyClickOnActivityIcon();
//            activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, respondUserName, actionLabel, true);
//            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ShiftSwap.getValue(), ActivityTest.indexOfActivityType.ShiftSwap.name());
//            activityPage.verifyNewShiftSwapCardShowsOnActivity(requestUserName, respondUserName, actionLabel, false);
//            List<String> swapData = activityPage.getShiftSwapDataFromActivity(requestUserName, respondUserName);
//            activityPage.approveOrRejectShiftSwapRequestOnActivity(requestUserName, respondUserName, ActivityTest.approveRejectAction.Approve.getValue());
//            activityPage.closeActivityWindow();
//
//            // Go to Schedule page to check whether the shifts are swapped
//            schedulePage.clickOnScheduleConsoleMenuItem();
//            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
//            schedulePage.isSchedule();
//            schedulePage.navigateToNextWeek();
//            schedulePage.navigateToNextWeek();
//            schedulePage.verifyShiftsAreSwapped(swapData);
//        } catch (Exception e){
//            SimpleUtils.fail(e.getMessage(), false);
//        }
//    }

// Blocking by https://legiontech.atlassian.net/browse/SCH-3170
//    @Owner(owner = "Haya")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
//    @TestName(description = "Verify the notification when TM is requesting time off")
//    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
//    public void verifyTheNotificationForRequestTimeOffAsTeamMember(String browser, String username, String password, String location) {
//        try {
//            // Login as Team Member to create time off
//            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
//            LoginPage loginPage = pageFactory.createConsoleLoginPage();
//
//            String fileName = "UsersCredentials.json";
//            fileName = SimpleUtils.getEnterprise("CinemarkWkdy_Enterprise")+fileName;
//            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
//            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
//            String requestUserName = profileNewUIPage.getNickNameFromProfile();
//            String myTimeOffLabel = "My Time Off";
//            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myTimeOffLabel);
//            profileNewUIPage.cancelAllTimeOff();
//            profileNewUIPage.clickOnCreateTimeOffBtn();
//            SimpleUtils.assertOnFail("New time off request window not loaded Successfully!", profileNewUIPage.isNewTimeOffWindowLoaded(), false);
//            String timeOffReasonLabel = "JURY DUTY";
//            // select time off reason
//            profileNewUIPage.selectTimeOffReason(timeOffReasonLabel);
//            profileNewUIPage.selectStartAndEndDate();
//            profileNewUIPage.clickOnSaveTimeOffRequestBtn();
//            loginPage.logOut();
//
//            // Login as Store Manager again to check message and reject
//            String RequstTimeOff = "requested";
//            Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
//            loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
//                    , String.valueOf(storeManagerCredentials[0][2]));
//            String respondUserName = profileNewUIPage.getNickNameFromProfile();
//            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
//            activityPage.verifyClickOnActivityIcon();
//            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.TimeOff.getValue(), ActivityTest.indexOfActivityType.TimeOff.name());
//            activityPage.verifyTheNotificationForReqestTimeOff(requestUserName, getTimeOffStartTime(),getTimeOffEndTime(), RequstTimeOff);
//            activityPage.approveOrRejectTTimeOffRequestOnActivity(requestUserName,respondUserName, ActivityTest.approveRejectAction.Approve.getValue());
//            //activityPage.closeActivityWindow();
//            loginPage.logOut();
//
//            // Login as Team Member to create time off
//            loginToLegionAndVerifyIsLoginDone(username, password, location);
//            profileNewUIPage.clickOnUserProfileImage();
//            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myTimeOffLabel);
//            profileNewUIPage.cancelAllTimeOff();
//            profileNewUIPage.clickOnCreateTimeOffBtn();
//            SimpleUtils.assertOnFail("New time off request window not loaded Successfully!", profileNewUIPage.isNewTimeOffWindowLoaded(), false);
//            //select time off reason
//            profileNewUIPage.selectTimeOffReason(timeOffReasonLabel);
//            profileNewUIPage.selectStartAndEndDate();
//            profileNewUIPage.clickOnSaveTimeOffRequestBtn();
//            loginPage.logOut();
//
//            // Login as Store Manager again to check message and approve
//            loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
//                    , String.valueOf(storeManagerCredentials[0][2]));
//            activityPage.verifyClickOnActivityIcon();
//            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.TimeOff.getValue(), ActivityTest.indexOfActivityType.TimeOff.name());
//            activityPage.verifyTheNotificationForReqestTimeOff(requestUserName, getTimeOffStartTime(),getTimeOffEndTime(), RequstTimeOff);
//            activityPage.approveOrRejectTTimeOffRequestOnActivity(requestUserName,respondUserName, ActivityTest.approveRejectAction.Reject.getValue());
//            //activityPage.closeActivityWindow();
//            loginPage.logOut();
//        } catch (Exception e){
//            SimpleUtils.fail(e.getMessage(), false);
//        }
//    }
//
//    @Automated(automated ="Automated")
//    @Owner(owner = "Haya")
//    @Enterprise(name = "KendraScott2_Enterprise")
//    @TestName(description = "Verify the notification when TM cancels time off request")
//    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
//    public void verifyTheNotificationForCancelTimeOffAsTeamMember(String browser, String username, String password, String location) {
//        try {
//            // Login as Team member to create the time off request
//            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
//            String fileName = "UsersCredentials.json";
//            fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
//            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
//            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
//            String requestUserName = profileNewUIPage.getNickNameFromProfile();
//            String myProfileLabel = "My Profile";
//            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myProfileLabel);
//            SimpleUtils.assertOnFail("Profile page not loaded Successfully!", profileNewUIPage.isProfilePageLoaded(), false);
//            String aboutMeLabel = "About Me";
//            profileNewUIPage.selectProfilePageSubSectionByLabel(aboutMeLabel);
//            String myTimeOffLabel = "My Time Off";
//            profileNewUIPage.selectProfilePageSubSectionByLabel(myTimeOffLabel);
//            String timeOffReasonLabel = "JURY DUTY";
//            profileNewUIPage.cancelAllTimeOff();
//            profileNewUIPage.clickOnCreateTimeOffBtn();
//            SimpleUtils.assertOnFail("New time off request window not loaded Successfully!", profileNewUIPage.isNewTimeOffWindowLoaded(), false);
//            // select time off reason
//            profileNewUIPage.selectTimeOffReason(timeOffReasonLabel);
//            List<String> startNEndDates = profileNewUIPage.selectStartAndEndDate();
//            profileNewUIPage.clickOnSaveTimeOffRequestBtn();
//            profileNewUIPage.cancelAllTimeOff();
//            LoginPage loginPage = pageFactory.createConsoleLoginPage();
//            loginPage.logOut();
//
//            // Login as Store Manager again to check message
//            String RequstTimeOff = "cancelled";
//            Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
//            loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
//                    , String.valueOf(storeManagerCredentials[0][2]));
//            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
//            activityPage.verifyClickOnActivityIcon();
//            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.TimeOff.getValue(), ActivityTest.indexOfActivityType.TimeOff.name());
//            activityPage.verifyTheNotificationForReqestTimeOff(requestUserName,getTimeOffStartTime(),getTimeOffEndTime(),RequstTimeOff);
//        } catch (Exception e){
//            SimpleUtils.fail(e.getMessage(), false);
//        }
//    }

//    Need update control setting
//    @Automated(automated ="Automated")
//    @Owner(owner = "Haya")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
//    @TestName(description = "Verify the notification when TM updates availability for a specific week with config Not required")
//    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
//    public void verifyNotificationForUpdateAvailability4SpecificWeekWithConfNOAsInternalAdmin(String browser, String username, String password, String location) {
//        try {
//            // Login with Store Manager Credentials
//            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
//            // Set availability policy
//            Thread.sleep(5000);
//            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
//            controlsPage.gotoControlsPage();
//            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
//            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
//            controlsNewUIPage.clickOnControlsSchedulingPolicies();
//            SimpleUtils.assertOnFail("Scheduling policy page not loaded successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
//            controlsNewUIPage.clickOnGlobalLocationButton();
//            String isApprovalRequired = "Not required";
//            controlsNewUIPage.updateAvailabilityManagementIsApprovalRequired(isApprovalRequired);
//            LoginPage loginPage = pageFactory.createConsoleLoginPage();
//            loginPage.logOut();
//
//            //Login as Team Member to change availability
//            String fileName = "UsersCredentials.json";
//            fileName = SimpleUtils.getEnterprise("CinemarkWkdy_Enterprise")+fileName;
//            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
//            Object[][] teamMemberCredentials = userCredentials.get("TeamMember");
//            loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
//                    , String.valueOf(teamMemberCredentials[0][2]));
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
//            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
//            String requestUserName = profileNewUIPage.getNickNameFromProfile();
//            loginPage.logOut();
//
//            // Login as Internal Admin again
//            loginToLegionAndVerifyIsLoginDone(username, password, location);
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
//
//            // Go to Team Roster, search the team member
//            TeamPage teamPage = pageFactory.createConsoleTeamPage();
//            teamPage.goToTeam();
//            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
//            teamPage.searchAndSelectTeamMemberByName(requestUserName);
//            String workPreferencesLabel = "Work Preferences";
//            profileNewUIPage.selectProfilePageSubSectionByLabel(workPreferencesLabel);
//            profileNewUIPage.approveAllPendingAvailabilityRequest();
//            loginPage.logOut();
//
//            // Login as Team Member again
//            loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
//                    , String.valueOf(teamMemberCredentials[0][2]));
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
//            profileNewUIPage.getNickNameFromProfile();
//            String myWorkPreferencesLabel = "My Work Preferences";
//            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myWorkPreferencesLabel);
//            //Update Preferred And Busy Hours
//            while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
//                profileNewUIPage.clickNextWeek();
//            }
//            String weekInfo = profileNewUIPage.getAvailabilityWeek();
//            int sliderIndex = 1;
//            double hours = -0.5;//move 1 metric 0.5h left
//            String leftOrRightDuration = "Right"; //move the right bar
//            String hoursType = "Preferred";
//            String repeatChanges = "This week only";
//            profileNewUIPage.updateMyAvailability(hoursType, sliderIndex, leftOrRightDuration,
//                    hours, repeatChanges);
//            loginPage.logOut();
//
//            // Login as Store Manager again to check message
//            Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
//            loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
//                    , String.valueOf(storeManagerCredentials[0][2]));
//            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
//            activityPage.verifyClickOnActivityIcon();
//            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ProfileUpdate.getValue(), ActivityTest.indexOfActivityType.ProfileUpdate.name());
//            String requestAwailabilityChangeLabel = "request";
//            activityPage.verifyNotificationForUpdateAvailability(requestUserName,isApprovalRequired,requestAwailabilityChangeLabel,weekInfo,repeatChanges);
//        } catch (Exception e){
//            SimpleUtils.fail(e.getMessage(), false);
//        }
//    }

// blocking by https://legiontech.atlassian.net/browse/SCH-3109
//    @Automated(automated ="Automated")
//    @Owner(owner = "Haya")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
//    @TestName(description = "Verify the notification when TM requests availability from a week onwards")
//    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
//    public void verifyNotificationForUpdateAvailabilityRepeatForwardWithConfYesAsInternalAdmin(String browser, String username, String password, String location) {
//        try {
//            // Login with Store Manager Credentials
//            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
//            // Set availability policy
//            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
//            controlsPage.gotoControlsPage();
//            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
//            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
//
//            dashboardPage.navigateToDashboard();
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
//            controlsPage.gotoControlsPage();
//            SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
//
//            controlsNewUIPage.clickOnControlsSchedulingPolicies();
//            SimpleUtils.assertOnFail("Scheduling policy page not loaded successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
//
//            controlsNewUIPage.clickOnGlobalLocationButton();
//            String isApprovalRequired = "Required for all changes";
//            controlsNewUIPage.updateAvailabilityManagementIsApprovalRequired(isApprovalRequired);
//            LoginPage loginPage = pageFactory.createConsoleLoginPage();
//            loginPage.logOut();
//
//            //Login as Team Member to change availability
//            String fileName = "UsersCredentials.json";
//            fileName = SimpleUtils.getEnterprise("CinemarkWkdy_Enterprise")+fileName;
//            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
//            Object[][] teamMemberCredentials = userCredentials.get("TeamMember");
//            loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
//                    , String.valueOf(teamMemberCredentials[0][2]));
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
//            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
//            String requestUserName = profileNewUIPage.getNickNameFromProfile();
//            loginPage.logOut();
//
//            // Login as Internal Admin again
//            loginToLegionAndVerifyIsLoginDone(username, password, location);
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
//
//            // Go to Team Roster, search the team member
//            TeamPage teamPage = pageFactory.createConsoleTeamPage();
//            teamPage.goToTeam();
//            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
//            teamPage.searchAndSelectTeamMemberByName(requestUserName);
//            String workPreferencesLabel = "Work Preferences";
//            profileNewUIPage.selectProfilePageSubSectionByLabel(workPreferencesLabel);
//            profileNewUIPage.approveAllPendingAvailabilityRequest();
//            loginPage.logOut();
//
//            // Login as Team Member again
//            loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
//                    , String.valueOf(teamMemberCredentials[0][2]));
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
//            profileNewUIPage.getNickNameFromProfile();
//            String myWorkPreferencesLabel = "My Work Preferences";
//            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myWorkPreferencesLabel);
//            //Update Preferred And Busy Hours
//            while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
//                profileNewUIPage.clickNextWeek();
//            }
//            String weekInfo = profileNewUIPage.getAvailabilityWeek();
//            int sliderIndex = 1;
//            double hours = -0.5;//move 1 metric 0.5h left
//            String leftOrRightDuration = "Right";
//            String hoursType = "Preferred";
//            String repeatChanges = "repeat forward";
//            profileNewUIPage.updateMyAvailability(hoursType, sliderIndex, leftOrRightDuration,
//                    hours, repeatChanges);
//            loginPage.logOut();
//
//            // Login as Store Manager again to check message
//            Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
//            loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
//                    , String.valueOf(storeManagerCredentials[0][2]));
//            String respondUserName = profileNewUIPage.getNickNameFromProfile();
//            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
//            activityPage.verifyClickOnActivityIcon();
//            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ProfileUpdate.getValue(), ActivityTest.indexOfActivityType.ProfileUpdate.name());
//            String requestAwailabilityChangeLabel = "requested";
//            activityPage.verifyNotificationForUpdateAvailability(requestUserName,isApprovalRequired,requestAwailabilityChangeLabel,weekInfo,repeatChanges);
//            activityPage.approveOrRejectTTimeOffRequestOnActivity(requestUserName,respondUserName, ActivityTest.approveRejectAction.Reject.getValue());
//        } catch (Exception e){
//            SimpleUtils.fail(e.getMessage(), false);
//        }
//    }


    @Automated(automated ="Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the notification when TM updates work preferences")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheNotificationOfWorkPreferencesAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String tmName = profileNewUIPage.getNickNameFromProfile();
            String myProfileLabel = "My Profile";
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myProfileLabel);
            SimpleUtils.assertOnFail("Profile page not loaded Successfully!", profileNewUIPage.isProfilePageLoaded(), false);
            String workPreferencesLabel = "My Work Preferences";
            profileNewUIPage.selectProfilePageSubSectionByLabel(workPreferencesLabel);
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.isWorkPreferencesPageLoaded();

            // Team Member updates the work Preferences
            teamPage.clickOnEditShiftPreference();
            SimpleUtils.assertOnFail("Edit Shift Preferences layout failed to load!", teamPage.isEditShiftPreferLayoutLoaded(), true);
            teamPage.setSliderForShiftPreferences();
            teamPage.changeShiftPreferencesStatus();
            teamPage.clickSaveShiftPrefBtn();

            // Team Member logout
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            // Login as Store Manager to check the activity
            String fileName = "UsersCredentials.json";
            fileName = SimpleUtils.getEnterprise("CinemarkWkdy_Enterprise") + fileName;
            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
            Object[][] teamMemberCredentials = userCredentials.get("StoreManager");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                    , String.valueOf(teamMemberCredentials[0][2]));
            dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Verify Activity Icon is loaded
            ActivityPage activityPage = pageFactory.createConsoleActivityPage();
            activityPage.verifyActivityBellIconLoaded();
            activityPage.verifyClickOnActivityIcon();
            activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.ProfileUpdate.getValue(), ActivityTest.indexOfActivityType.ProfileUpdate.name());
            activityPage.verifyNewWorkPreferencesCardShowsOnActivity(tmName);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


//    @Automated(automated = "Automated")
//    @Owner(owner = "Julie")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
//    @TestName(description = "Verify dashboard functionality when login through TM View")
//    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
//    public void verifyDashboardFunctionalityAsTeamMember(String browser, String username, String password, String location) throws Exception {
//        try {
//            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
//            String nickName = profileNewUIPage.getNickNameFromProfile();
//
//            //T1838579 Validate the TM accessible tabs.
//            dashboardPage.validateTMAccessibleTabs();
//
//            //T1838580 Validate the presence of location.
//            dashboardPage.validateThePresenceOfLocation();
//
//            //T1838581 Validate the accessible location.
//            dashboardPage.validateTheAccessibleLocation();
//
//            //T1838582 Validate the presence of logo.
//            dashboardPage.validateThePresenceOfLogo();
//
//            //T1838584 Validate the visibility of Username.
//            dashboardPage.validateTheVisibilityOfUsername(nickName);
//
//            //T1838583 Validate the information after selecting different location.
//            LoginPage loginPage = pageFactory.createConsoleLoginPage();
//            loginPage.logOut();
//
//            String fileName = "UsersCredentials.json";
//            fileName = SimpleUtils.getEnterprise("CinemarkWkdy_Enterprise") + fileName;
//            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
//            Object[][] internalAdminCredentials = userCredentials.get("InternalAdmin");
//            loginToLegionAndVerifyIsLoginDone(String.valueOf(internalAdminCredentials[0][0]), String.valueOf(internalAdminCredentials[0][1])
//                    , String.valueOf(internalAdminCredentials[0][2]));
//
//            TeamPage teamPage = pageFactory.createConsoleTeamPage();
//            teamPage.goToTeam();
//            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
//            teamPage.searchAndSelectTeamMemberByName(nickName);
//            profileNewUIPage.selectProfilePageSubSectionByLabel("Time Off");
//            profileNewUIPage.rejectAllTimeOff();
//
//            SchedulePage schedulePageAdmin = pageFactory.createConsoleScheduleNewUIPage();
//            schedulePageAdmin.goToConsoleScheduleAndScheduleSubMenu();
//            schedulePageAdmin.navigateToNextWeek();
//            schedulePageAdmin.navigateToNextWeek();//BUG
//            boolean isWeekGenerated = schedulePageAdmin.isWeekGenerated();
//            if (!isWeekGenerated) {
//                schedulePageAdmin.createScheduleForNonDGFlowNewUI();
//            }
//            schedulePageAdmin.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
//            schedulePageAdmin.deleteTMShiftInWeekView(nickName);
//            schedulePageAdmin.deleteTMShiftInWeekView("Unassigned");
//            schedulePageAdmin.clickOnDayViewAddNewShiftButton();
//            schedulePageAdmin.customizeNewShiftPage();
//            schedulePageAdmin.selectWorkRole("GENERAL MANAGER");
//            schedulePageAdmin.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
//            schedulePageAdmin.clickOnCreateOrNextBtn();
//            schedulePageAdmin.searchTeamMemberByName(nickName);
////		if (schedulePageAdmin.displayAlertPopUp())
////			schedulePageAdmin.displayAlertPopUpForRoleViolation();
//            schedulePageAdmin.clickOnOfferOrAssignBtn();
//            schedulePageAdmin.saveSchedule();
//            schedulePageAdmin.publishActiveSchedule();
//            List<String> scheduleListAdmin = schedulePageAdmin.getWeekScheduleShiftTimeListOfWeekView(nickName);
//            loginPage.logOut();
//
//            loginToLegionAndVerifyIsLoginDone(username, password, location);
//            dashboardPage.validateDateAndTimeAfterSelectingDifferentLocation();
//            SchedulePage schedulePageTM = pageFactory.createConsoleScheduleNewUIPage();
//            schedulePageTM.clickOnScheduleConsoleMenuItem();
//            schedulePageTM.navigateToNextWeek();
//            schedulePageTM.navigateToNextWeek();//BUG
//            List<String> scheduleListTM = new ArrayList<>();
//            if (schedulePageTM.getShiftHoursFromInfoLayout().size() > 0) {
//                for (String tmShiftTime : schedulePageTM.getShiftHoursFromInfoLayout()) {
//                    tmShiftTime = tmShiftTime.replaceAll(":00", "");
//                    scheduleListTM.add(tmShiftTime);
//                }
//            }
//            if (scheduleListTM != null && scheduleListTM.size() > 0 && scheduleListAdmin != null && scheduleListAdmin.size() > 0) {
//                if (scheduleListTM.size() == scheduleListAdmin.size() && scheduleListTM.containsAll(scheduleListAdmin)) {
//                    SimpleUtils.pass("Schedules in TM view is consistent with the Admin view of the location successfully");
//                } else
//                    SimpleUtils.fail("Schedule doesn't show of the location correctly", true);
//            } else {
//                SimpleUtils.report("Schedule may have not been generated");
//            }
//
//            //T1838585 Validate date and time.
//            dashboardPage.navigateToDashboard();
//            dashboardPage.validateDateAndTime();
//
//            //T1838586 Validate the upcoming schedules.
//            dashboardPage.validateTheUpcomingSchedules(nickName);
//
//            //T1838587 Validate the click ability of VIEW MY SCHEDULE button.
//            dashboardPage.validateVIEWMYSCHEDULEButtonClickable();
//            dashboardPage.navigateToDashboard();
//
//            //T1838588 Validate the visibility of profile picture.
//            dashboardPage.validateTheVisibilityOfProfilePicture();
//
//            //T1838589 Validate the click ability of Profile picture icon.
//            dashboardPage.validateProfilePictureIconClickable();
//
//            //T1838590 Validate the visibility of Profile.
//            dashboardPage.validateTheVisibilityOfProfile();
//
//            //T1838591 Validate the click ability of My profile, My Work Preferences, My Time off.
//            dashboardPage.validateProfileDropdownClickable();
//
//            //T1838592 Validate the data of My profile.
//            dashboardPage.validateTheDataOfMyProfile();
//
//            //T1838593 Validate the functionality My Work Preferences and My Availability.
//            dashboardPage.navigateToDashboard();
//            String dateFromDashboard = dashboardPage.getCurrentDateFromDashboard();
//            dashboardPage.validateTheDataOfMyWorkPreferences(dateFromDashboard);
//
//            //T1838594 Validate the presence of data on Time off page.
//            dashboardPage.validateTheDataOfMyTimeOff();
//
//        } catch (Exception e) {
//            SimpleUtils.fail(e.getMessage(),false);
//        }
//    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify Work Preference details by updating the information")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyWorkPreferenceDetailsByUpdatingTheInformationAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), true);
            dashboardPage.clickOnSubMenuOnProfile("My Work Preferences");
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            //SimpleUtils.assertOnFail("Profile Page not loaded Successfully!", profileNewUIPage.isProfilePageLoaded(), false);

            //T1838598 Validate the update of shift preferences.
            profileNewUIPage.validateTheUpdateOfShiftPreferences(true, false);

            //T1838599 Validate the update of Availability.
            profileNewUIPage.validateTheUpdateOfAvailability("Preferred", 1, "Right",
                    120, "This week only");
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verification of My Schedule Page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verificationOfMySchedulePageAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();

            //T1838603 Validate the availability of schedule table.
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String nickName = profileNewUIPage.getNickNameFromProfile();
            schedulePage.validateTheAvailabilityOfScheduleTable(nickName);

            //T1838604 Validate the disability of location selector on Schedule page.
            schedulePage.validateTheDisabilityOfLocationSelectorOnSchedulePage();

            //T1838605 Validate the availability of profile menu.
            schedulePage.validateTheAvailabilityOfScheduleMenu();

            //T1838606 Validate the focus of schedule.
            schedulePage.validateTheFocusOfSchedule();

            //T1838607 Validate the default filter is selected as Scheduled.
            schedulePage.validateTheDefaultFilterIsSelectedAsScheduled();

            //T1838608 Validate the focus of week.
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            dashboardPage.navigateToDashboard();
            String currentDate = dashboardPage.getCurrentDateFromDashboard();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.validateTheFocusOfWeek(currentDate);

            //T1838609 Validate the selection of previous and upcoming week.
            schedulePage.verifySelectOtherWeeks();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verification of To and Fro navigation of week picker")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verificationOfToAndFroNavigationOfWeekPickerAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            String nickName = profileNewUIPage.getNickNameFromProfile();

            //T1838610 Validate the click ability of forward and backward button.
            schedulePage.validateForwardAndBackwardButtonClickable();

            //T1838611 Validate the data according to the selected week.
            schedulePage.validateTheDataAccordingToTheSelectedWeek();

            //T1838612 Validate the seven days - Sunday to Saturday is available in schedule table.
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.validateTheSevenDaysIsAvailableInScheduleTable();
            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            ///Log in as admin to get the operation hours
            String fileName = "UsersCredentials.json";
            fileName = SimpleUtils.getEnterprise("CinemarkWkdy_Enterprise") + fileName;
            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
            Object[][] internalAdminCredentials = userCredentials.get("InternalAdmin");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(internalAdminCredentials[0][0]), String.valueOf(internalAdminCredentials[0][1])
                    , String.valueOf(internalAdminCredentials[0][2]));
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

            // Go to Scheduling Policies to get the additional Scheduled Hour
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
            controlsPage.gotoControlsPage();
            SimpleUtils.assertOnFail("Controls Page not loaded Successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            SimpleUtils.assertOnFail("Scheduling Policies Page not loaded Successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
            HashMap<String, Integer> schedulePoliciesBufferHours = controlsNewUIPage.getScheduleBufferHours();

            SchedulePage schedulePageAdmin = pageFactory.createConsoleScheduleNewUIPage();
            schedulePageAdmin.clickOnScheduleConsoleMenuItem();
            schedulePageAdmin.clickOnScheduleSubTab("Schedule");
            schedulePageAdmin.navigateToNextWeek();
        schedulePageAdmin.navigateToNextWeek(); //BUG

            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView("Unassigned");
            schedulePage.saveSchedule();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.clickOnDayViewAddNewShiftButton();
            schedulePage.selectWorkRole("GENERAL MANAGER");
            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
            schedulePage.clickOnCreateOrNextBtn();
            schedulePage.searchTeamMemberByName(nickName);
            if(schedulePage.displayAlertPopUp())
                schedulePage.displayAlertPopUpForRoleViolation();
            schedulePage.clickOnOfferOrAssignBtn();
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();
            if (!schedulePage.isSummaryViewLoaded())
                schedulePage.toggleSummaryView();
            String theEarliestAndLatestTimeInSummaryView = schedulePage.getTheEarliestAndLatestTimeInSummaryView(schedulePoliciesBufferHours);
            SimpleUtils.report("theEarliestAndLatestOperationHoursInSummaryView is " + theEarliestAndLatestTimeInSummaryView);
            loginPage.logOut();

            ///Log in as team member again to compare the operation hours
            loginToLegionAndVerifyIsLoginDone(username, password, location);
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.navigateToNextWeek();
        schedulePage.navigateToNextWeek(); //BUG
            String theEarliestAndLatestTimeInScheduleTable = schedulePage.getTheEarliestAndLatestTimeInScheduleTable();
            SimpleUtils.report("theEarliestAndLatestOperationHoursInScheduleTable is " + theEarliestAndLatestTimeInScheduleTable);
            schedulePage.compareOperationHoursBetweenAdminAndTM(theEarliestAndLatestTimeInSummaryView, theEarliestAndLatestTimeInScheduleTable);

            //T1838613 Validate that hours and date is visible of shifts.
            schedulePage.validateThatHoursAndDateIsVisibleOfShifts();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify Profile picture functionality")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyProfilePictureFunctionalityAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();

            //T1838614 Validate the clickability of Profile picture in a shift.
            schedulePage.validateProfilePictureInAShiftClickable();

            //T1838615 Validate the data of profile popup in a shift.
            //schedulePage.validateTheDataOfProfilePopupInAShift();
            // todo: <Here is an incident LEG-10929>
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify Info icon functionality")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyInfoIconFunctionalityAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();

            //T1838616 Validate the availability of info icon.
            schedulePage.validateTheAvailabilityOfInfoIcon();

            //T1838617 Validate the clickability of info icon.
            schedulePage.validateInfoIconClickable();

            //T1838618 Validate the availability of Meal break as per the control settings.
            //schedulePage.validateMealBreakPerControlSettings();
            // todo: <Meal break is a postpone feature>
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verification of Open Shift Schedule Smart Card")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyOpenShiftScheduleSmartCardAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try {
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();

            //T1838619 Validate the availability of Open shift Smartcard.
            schedulePage.validateTheAvailabilityOfOpenShiftSmartcard();

            //T1838620 Validate the clickability of View shifts in Open shift smartcard.
            schedulePage.validateViewShiftsClickable();

            //T1838621 Validate the number of open shifts in smartcard and schedule table.
            schedulePage.validateTheNumberOfOpenShifts();

            //T1838622 Verify the availability of claim open shift popup.
            schedulePage.verifyTheAvailabilityOfClaimOpenShiftPopup();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

//    @Automated(automated = "Automated")
//    @Owner(owner = "Nora")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
//    @TestName(description = "Verify the availibility and functionality of claiming open shift popup")
//    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
//    public void verifyTheFunctionalityOfClaimOpenShiftAsTeamLead(String browser, String username, String password, String location)
//            throws Exception {
//        try {
//            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
//            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
//            String tmName = profileNewUIPage.getNickNameFromProfile();
//            LoginPage loginPage = pageFactory.createConsoleLoginPage();
//            loginPage.logOut();
//
//            String fileName = "UsersCredentials.json";
//            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
//            fileName = SimpleUtils.getEnterprise("CinemarkWkdy_Enterprise") + fileName;
//            userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
//            Object[][] credentials = userCredentials.get("InternalAdmin");
//            loginToLegionAndVerifyIsLoginDone(String.valueOf(credentials[0][0]), String.valueOf(credentials[0][1]), String.valueOf(credentials[0][2]));
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
//            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
//            schedulePage.clickOnScheduleConsoleMenuItem();
//            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
//            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
//            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), true);
//            schedulePage.navigateToNextWeek();
//            schedulePage.navigateToNextWeek();
//
//            boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
//            if (isActiveWeekGenerated) {
//                schedulePage.unGenerateActiveScheduleScheduleWeek();
//            }
//            schedulePage.createScheduleForNonDGFlowNewUI();
//            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
//            schedulePage.deleteTMShiftInWeekView(tmName);
//            schedulePage.deleteTMShiftInWeekView("Unassigned");
//            schedulePage.saveSchedule();
//            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
//            schedulePage.clickOnDayViewAddNewShiftButton();
//            schedulePage.customizeNewShiftPage();
//            schedulePage.selectWorkRole("GENERAL MANAGER");
//            schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
//            schedulePage.clickOnCreateOrNextBtn();
//            schedulePage.searchTeamMemberByName(tmName);
//            schedulePage.clickOnOfferOrAssignBtn();
//            schedulePage.saveSchedule();
//            schedulePage.publishActiveSchedule();
//
//            loginPage.logOut();
//
//            loginToLegionAndVerifyIsLoginDone(username, password, location);
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
//            schedulePage = dashboardPage.goToTodayForNewUI();
//            schedulePage.isSchedule();
//            schedulePage.navigateToNextWeek();
//            schedulePage.navigateToNextWeek();
//            // Validate the clickability of claim open text in popup
//            String cardName = "WANT MORE HOURS?";
//            SimpleUtils.assertOnFail("Smart Card: " + cardName + " not loaded Successfully!", schedulePage.isSpecificSmartCardLoaded(cardName), false);
//            String linkName = "View Shifts";
//            schedulePage.clickLinkOnSmartCardByName(linkName);
//            SimpleUtils.assertOnFail("Open shifts not loaed Successfully!", schedulePage.areShiftsPresent(), false);
//            List<String> shiftHours = schedulePage.getShiftHoursFromInfoLayout();
//            List<String> claimShift = new ArrayList<>(Arrays.asList("Claim Shift"));
//            int index = schedulePage.selectOneShiftIsClaimShift(claimShift);
//            String weekDay = schedulePage.getSpecificShiftWeekDay(index);
//            // Validate the availability of Claim Shift Request popup
//            schedulePage.clickTheShiftRequestByName(claimShift.get(0));
//            // Validate the availability of Cancel and I Agree buttons in popup
//            schedulePage.verifyClaimShiftOfferNBtnsLoaded();
//            // Validate the date and time of Claim Shift Request popup
//            schedulePage.verifyTheShiftHourOnPopupWithScheduleTable(shiftHours.get(index), weekDay);
//            // Validate the clickability of Cancel button
//            schedulePage.verifyClickCancelBtnOnClaimShiftOffer();
//            // Validate the clickability of I Agree button
//            schedulePage.clickOnShiftByIndex(index);
//            schedulePage.clickTheShiftRequestByName(claimShift.get(0));
//            schedulePage.verifyClickAgreeBtnOnClaimShiftOffer();
//            // Validate the status of Claim request
//            schedulePage.clickOnShiftByIndex(index);
//            List<String> claimStatus = new ArrayList<>(Arrays.asList("Claim Shift Approval Pending", "Cancel Claim Request"));
//            schedulePage.verifyShiftRequestButtonOnPopup(claimStatus);
//            // Validate the availability of Cancel Claim Request option.
//            schedulePage.verifyTheColorOfCancelClaimRequest(claimStatus.get(1));
//            // Validate that Cancel claim request is clickable and popup is displaying by clicking on it to reconfirm the cancellation
//            schedulePage.clickTheShiftRequestByName(claimStatus.get(1));
//            schedulePage.verifyReConfirmDialogPopup();
//            // Validate that Claim request remains in Pending state after clicking on No button
//            schedulePage.verifyClickNoButton();
//            schedulePage.clickOnShiftByIndex(index);
//            schedulePage.verifyShiftRequestButtonOnPopup(claimStatus);
//            // Validate the Cancellation of Claim request by clicking  on Yes
//            schedulePage.clickTheShiftRequestByName(claimStatus.get(1));
//            schedulePage.verifyReConfirmDialogPopup();
//            schedulePage.verifyClickOnYesButton();
//            schedulePage.clickOnShiftByIndex(index);
//            schedulePage.verifyShiftRequestButtonOnPopup(claimShift);
//            // Validate the functionality of clear filter in Open shift smart card
//            schedulePage.verifyTheFunctionalityOfClearFilter();
//        } catch (Exception e){
//            SimpleUtils.fail(e.getMessage(), false);
//        }
//    }


    @Automated(automated = "Automated")
    @Owner(owner = "Nora")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the feature of filter")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyTheFeatureOfFilterAsInternalAdmin(String browser, String username, String password, String location)
            throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();//BUG
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            schedulePage.createScheduleForNonDGFlowNewUI();
            // Deleting the existing shifts for swap team members
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteTMShiftInWeekView("Unassigned");
            schedulePage.addOpenShiftWithLastDay("GENERAL MANAGER");
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            LoginPage loginPage = pageFactory.createConsoleLoginPage();
            loginPage.logOut();

            // Login as Team Member
            String fileName = "UsersCredentials.json";
            fileName = SimpleUtils.getEnterprise("CinemarkWkdy_Enterprise") + fileName;
            HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
            Object[][] teamMemberCredentials = userCredentials.get("TeamMember");
            loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]), String.valueOf(teamMemberCredentials[0][1])
                    , String.valueOf(teamMemberCredentials[0][2]));

            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            schedulePage.goToSchedulePageAsTeamMember();
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek(); //BUG
            String subTitle = "Team Schedule";
            schedulePage.gotoScheduleSubTabByText(subTitle);
            // Validate the feature of filter
            schedulePage.verifyScheduledNOpenFilterLoaded();
            // Validate the filter - Schedule and Open
            schedulePage.checkAndUnCheckTheFilters();
            // Validate the filter results by applying scheduled filter
            // Validate the filter results by applying Open filter
            schedulePage.filterScheduleByShiftTypeAsTeamMember(true);
            // Validate the filter results by applying both filters and none of them
            schedulePage.filterScheduleByBothAndNone();
            // Validate the filter value by moving to other weeks
            String selectedFilter = schedulePage.selectOneFilter();
            schedulePage.verifySelectedFilterPersistsWhenSelectingOtherWeeks(selectedFilter);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the left navigation menu on login using admin access")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheLeftNavigationMenuOnLoginUsingAdminAccessAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            verifyTheLeftNavigationMenuOnLoginUsingDifferentAccess("Admin");
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the left navigation menu on login using SM access")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheLeftNavigationMenuOnLoginUsingSMAccessAsStoreManager(String browser, String username, String password, String location) throws Exception {
        try{
            verifyTheLeftNavigationMenuOnLoginUsingDifferentAccess("StoreManager");
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the left navigation menu on login using TL access")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheLeftNavigationMenuOnLoginUsingTLAccessAsTeamLead(String browser, String username, String password, String location) throws Exception {
        try{
            verifyTheLeftNavigationMenuOnLoginUsingDifferentAccess("TeamLead");
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Validate the left navigation menu on login using TM access")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheLeftNavigationMenuOnLoginUsingTMAccessAsTeamMember(String browser, String username, String password, String location) throws Exception {
        try{
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            dashboardPage.closeNewFeatureEnhancementsPopup();
            verifyTheLeftNavigationMenuOnLoginUsingDifferentAccess("TeamMember");
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }
//
//    @Automated(automated = "Automated")
//    @Owner(owner = "Mary")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
//    @TestName(description = "Validate the left navigation menu on login using DM access")
//    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
//    public void verifyTheLeftNavigationMenuOnLoginUsingDMAccessAsDistrictManager(String browser, String username, String password, String location) throws Exception {
//        try{
//            verifyTheLeftNavigationMenuOnLoginUsingDifferentAccess("DistrictManager");
//        } catch (Exception e){
//            SimpleUtils.fail(e.getMessage(), false);
//        }
//    }
//
//    @Automated(automated = "Automated")
//    @Owner(owner = "Mary")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
//    @TestName(description = "Validate the left navigation menu on login using CA (Customer Admin) access")
//    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
//    public void verifyTheLeftNavigationMenuOnLoginUsingCAAccessAsCustomerAdmin(String browser, String username, String password, String location) throws Exception {
//        try{
//            verifyTheLeftNavigationMenuOnLoginUsingDifferentAccess("CustomerAdmin");
//        } catch (Exception e){
//            SimpleUtils.fail(e.getMessage(), false);
//        }
//    }

    private void verifyTheLeftNavigationMenuOnLoginUsingDifferentAccess(String userRole) throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        //Check DashBoard console menu is display
        SimpleUtils.assertOnFail("DashBoard console menu not loaded Successfully!", dashboardPage.isDashboardConsoleMenuDisplay(), false);
        //Check dashboard page is display after click Dashboard console menu
        dashboardPage.clickOnDashboardConsoleMenu();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        dashboardPage.verifyHeaderNavigationMessage("Dashboard");

        //Check Legion logo is display or not
        if (userRole.contains("Admin") || userRole.contains("StoreManager")
                || userRole.contains("DistrictManager") || userRole.contains("CustomerAdmin")) {
            SimpleUtils.assertOnFail("Legion logo should be loaded!", dashboardPage.isLegionLogoDisplay(), false);
        } else
            SimpleUtils.assertOnFail("Legion logo should not be loaded!", !dashboardPage.isLegionLogoDisplay(), false);

        //Check District selector is display or not
        if (userRole.contains("Admin") || userRole.contains("CustomerAdmin") || userRole.contains("DistrictManager")) {
            SimpleUtils.assertOnFail("District selector should be loaded!", dashboardPage.IsThereDistrictNavigationForLegionBasic(), false);
            //Check District dropdown is display after click District selector
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.verifyClickChangeDistrictButton();
        } else
            SimpleUtils.assertOnFail("District selector should not be loaded!", !dashboardPage.IsThereDistrictNavigationForLegionBasic(), false);

        //Check Team console menu is display or not
        if (userRole.contains("TeamMember")) {
            SimpleUtils.assertOnFail("Team console menu should not loaded!", !dashboardPage.isTeamConsoleMenuDisplay(), false);
        } else{
            SimpleUtils.assertOnFail("Team console menu not loaded Successfully!", dashboardPage.isTeamConsoleMenuDisplay(), false);
            //Check Team page is display after click Team tab
            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
            dashboardPage.verifyHeaderNavigationMessage("Team");
        }

        //Check Schedule console menu is display
        SimpleUtils.assertOnFail("Schedule console menu not loaded Successfully!", dashboardPage.isScheduleConsoleMenuDisplay(), false);
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        //Check Schedule overview page is display after click Schedule tab
        if (userRole.contains("TeamLead") || userRole.contains("TeamMember")) {
            schedulePage.verifyTMSchedulePanelDisplay();
        } else {
            ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
            SimpleUtils.assertOnFail("Schedule page not loaded Successfully!", scheduleOverviewPage.loadScheduleOverview(), false);
        }
        dashboardPage.verifyHeaderNavigationMessage("Schedule");

        //Check TimeSheet console menu is display or not
        if (getDriver().getCurrentUrl().contains(propertyMap.get("Coffee_Enterprise"))) {
            if (userRole.contains("Admin") || userRole.contains("StoreManager")
                    || userRole.contains("CustomerAdmin") || userRole.contains("DistrictManager")){
                SimpleUtils.assertOnFail("Timesheet console menu not loaded Successfully!", dashboardPage.isTimesheetConsoleMenuDisplay(), false);
                TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
                timeSheetPage.clickOnTimeSheetConsoleMenu();
                SimpleUtils.assertOnFail("Timesheet console page not loaded Successfully!", timeSheetPage.isTimeSheetPageLoaded(), false);
            } else{
                SimpleUtils.assertOnFail("Timesheet console menu should not loaded!", !dashboardPage.isTimesheetConsoleMenuDisplay(), false);
            }
        }

        //Check Analytics console menu is display or not
        if (userRole.contains("TeamLead") || userRole.contains("TeamMember")) {
            SimpleUtils.assertOnFail("Analytics console menu should not be loaded Successfully!",
                    !dashboardPage.isAnalyticsConsoleMenuDisplay(), false);
        } else {
            SimpleUtils.assertOnFail("Analytics console menu not loaded Successfully!", dashboardPage.isAnalyticsConsoleMenuDisplay(), false);
            //Check Analytics page is display after click Analytics tab
            AnalyticsPage analyticsPage = pageFactory.createConsoleAnalyticsPage();
            analyticsPage.clickOnAnalyticsConsoleMenu();
            SimpleUtils.assertOnFail("Analytics Page not loaded Successfully!", analyticsPage.isReportsPageLoaded(), false);
            dashboardPage.verifyHeaderNavigationMessage("Analytics");
        }


        //Check Inbox console menu is display
        SimpleUtils.assertOnFail("Inbox console menu not loaded Successfully!", dashboardPage.isInboxConsoleMenuDisplay(), false);
        //Check Inbox page is display after click Inbox tab
        InboxPage inboxPage = pageFactory.createConsoleInboxPage();
        inboxPage.clickOnInboxConsoleMenuItem();
        SimpleUtils.assertOnFail("Inbox console menu not loaded Successfully!", inboxPage.isAnnouncementListPanelDisplay(), false);
        dashboardPage.verifyHeaderNavigationMessage("Inbox");

        //Check Admin console menu is display
        if (userRole.equalsIgnoreCase("Admin")){
            SimpleUtils.assertOnFail("Admin console menu not loaded Successfully!", dashboardPage.isAdminConsoleMenuDisplay(), false);
            //Check Admin page is display after click Admin tab
            dashboardPage.clickOnAdminConsoleMenu();
            dashboardPage.verifyAdminPageIsLoaded();
            dashboardPage.verifyHeaderNavigationMessage("Admin");
        } else
            SimpleUtils.assertOnFail("Admin console menu should not be loaded!", !dashboardPage.isAdminConsoleMenuDisplay(), false);

        //Check Integration console menu is display
        if (userRole.equalsIgnoreCase("Admin")) {
            SimpleUtils.assertOnFail("Integration console menu not loaded Successfully!", dashboardPage.isIntegrationConsoleMenuDisplay(), false);
            //Check Integration page is display after click Integration tab
            dashboardPage.clickOnIntegrationConsoleMenu();
            dashboardPage.verifyIntegrationPageIsLoaded();
            dashboardPage.verifyHeaderNavigationMessage("Integration");
        } else
            SimpleUtils.assertOnFail("Integration console menu should not be loaded!", !dashboardPage.isIntegrationConsoleMenuDisplay(), false);

        //Check Controls console menu is display
        if (userRole.contains("TeamLead") || userRole.contains("TeamMember")){
            SimpleUtils.assertOnFail("Controls console menu should not be loaded!", !dashboardPage.isControlsConsoleMenuDisplay(), false);
        } else {
            SimpleUtils.assertOnFail("Controls console menu not loaded Successfully!", dashboardPage.isControlsConsoleMenuDisplay(), false);
            //Check Controls page is display after click Controls tab
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.isControlsPageLoaded();
            dashboardPage.verifyHeaderNavigationMessage("Controls");
        }

        //Check Logout console menu is display
        SimpleUtils.assertOnFail("Logout console menu not loaded Successfully!", dashboardPage.isLogoutConsoleMenuDisplay(), false);
        //Check Login page is display after click Logout tab
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();
        loginPage.verifyLoginPageIsLoaded();
    }
}
