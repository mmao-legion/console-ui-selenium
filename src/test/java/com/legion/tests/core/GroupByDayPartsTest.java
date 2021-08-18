package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.OpsPortaPageFactories.ConfigurationPage;
import com.legion.pages.OpsPortaPageFactories.LocationsPage;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.core.OpsPortal.LocationsTest;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GroupByDayPartsTest extends TestBase {

    private static HashMap<String, String> parameterMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");

    public enum scheduleGroupByFilterOptions {
        groupbyAll("Group by All"),
        groupbyWorkRole("Group by Work Role"),
        groupbyTM("Group by TM"),
        groupbyJobTitle("Group by Job Title"),
        groupbyLocation("Group by Location"),
        groupbyDayParts("Group by Day Parts");

        private final String value;

        scheduleGroupByFilterOptions(final String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    public enum dayParts{
        LUNCH("Lunch"),
        DINNER("Dinner");
        private final String value;
        dayParts(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    public enum dayPartsTime{
        LUNCH_START("11am"),
        LUNCH_END("2pm"),
        DINNER_START("4pm"),
        DINNER_END("7pm");
        private final String value;
        dayPartsTime(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception {
        try {
            this.createDriver((String) params[0], "69", "Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
            if (MyThreadLocal.getDriver().getCurrentUrl().contains(parameterMap.get("KendraScott2_Enterprise")) && (MyThreadLocal.getCurrentOperatingTemplate()==null || MyThreadLocal.getCurrentOperatingTemplate().equals(""))){

            } else if (MyThreadLocal.getDriver().getCurrentUrl().contains(parameterMap.get("CinemarkWkdy_Enterprise")) && (MyThreadLocal.getCurrentOperatingTemplate()==null || MyThreadLocal.getCurrentOperatingTemplate().equals(""))){
                //getAndSetDefaultOperatingHoursTemplate((String) params[3]);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    //added by Haya.
    public void getAndSetDefaultOperatingHoursTemplate(String currentLocation) throws Exception{
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        CinemarkMinorPage cinemarkMinorPage = pageFactory.createConsoleCinemarkMinorPage();
        ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();

        //Go to OP page
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
        locationsPage.clickOnLocationsTab();
        locationsPage.goToSubLocationsInLocationsPage();
        locationsPage.searchLocation(currentLocation);               ;
        SimpleUtils.assertOnFail("Locations not searched out Successfully!",  locationsPage.verifyUpdateLocationResult(currentLocation), false);
        locationsPage.clickOnLocationInLocationResult(currentLocation);
        locationsPage.clickOnConfigurationTabOfLocation();
        HashMap<String, String> templateTypeAndName = locationsPage.getTemplateTypeAndNameFromLocation();
        MyThreadLocal.setCurrentOperatingTemplate(templateTypeAndName.get("Operating Hours"));
        //back to console.
        switchToConsoleWindow();
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate dayparts can be configured")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateDaypartsCanBeConfiguredAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            String templateName = "";

            // Verify dayparts can be cleared in Operation Hours or Working Hours
            if (MyThreadLocal.getDriver().getCurrentUrl().contains(parameterMap.get("KendraScott2_Enterprise"))) {
                controlsNewUIPage.clickOnControlsConsoleMenu();
                SimpleUtils.assertOnFail("Controls Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
                controlsNewUIPage.clickOnGlobalLocationButton();
                controlsNewUIPage.clickOnControlsWorkingHoursCard();
                SimpleUtils.assertOnFail("Controls Page: Working Hours Section not Loaded.", controlsNewUIPage.isControlsWorkingHoursLoaded(), false);
                controlsNewUIPage.disableAllDayparts();
            } else if (MyThreadLocal.getDriver().getCurrentUrl().contains(parameterMap.get("CinemarkWkdy_Enterprise"))) {
                locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
                SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
                locationsPage.clickOnLocationsTab();
                locationsPage.goToSubLocationsInLocationsPage();
                locationsPage.searchLocation(location);
                SimpleUtils.assertOnFail("Locations not searched out Successfully!",  locationsPage.verifyUpdateLocationResult(location), false);
                locationsPage.clickOnLocationInLocationResult(location);
                locationsPage.clickOnConfigurationTabOfLocation();
                HashMap<String, String> templateTypeAndName = locationsPage.getTemplateTypeAndNameFromLocation();
                templateName = templateTypeAndName.get("Operating Hours");
                configurationPage.goToConfigurationPage();
                configurationPage.clickOnConfigurationCrad("Operating Hours");
                configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
                configurationPage.clickOnEditButtonOnTemplateDetailsPage();
                configurationPage.disableAllDayparts();
                configurationPage.publishNowTheTemplate();
                switchToConsoleWindow();
            }

            // Verify Group by dayparts is not in the dropdown as an option when it is not configured
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()) , false);
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            if (!schedulePage.isGroupByDayPartsLoaded())
                SimpleUtils.pass("Schedule page 'Group by Day Parts' option isn't in the drop down list as expected");
            else
                SimpleUtils.fail("Schedule page 'Group by Day Parts' option is in the drop down list unexpectedly",true);

            // Verify dayparts can be configured in Global Configuration or Working Hours
            if (MyThreadLocal.getDriver().getCurrentUrl().contains(parameterMap.get("KendraScott2_Enterprise"))) {
                controlsNewUIPage.clickOnControlsConsoleMenu();
                SimpleUtils.assertOnFail("Controls Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
                controlsNewUIPage.clickOnGlobalLocationButton();
                controlsNewUIPage.clickOnControlsWorkingHoursCard();
                SimpleUtils.assertOnFail("Controls Page: Working Hours Section not Loaded.", controlsNewUIPage.isControlsWorkingHoursLoaded(), false);
                controlsNewUIPage.enableDaypart("Lunch");
                controlsNewUIPage.enableDaypart("Dinner");
            } else if (MyThreadLocal.getDriver().getCurrentUrl().contains(parameterMap.get("CinemarkWkdy_Enterprise"))) {
                locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
                SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
                locationsPage.clickOnLocationsTab();
                locationsPage.goToGlobalConfigurationInLocations();
                locationsPage.enableDaypart("Lunch");
                locationsPage.enableDaypart("Dinner");
            }

            // Verify dayparts can be configured in Operation Hours or Working Hours
            if (MyThreadLocal.getDriver().getCurrentUrl().contains(parameterMap.get("KendraScott2_Enterprise"))) {
                controlsNewUIPage.clickOnControlsConsoleMenu();
                SimpleUtils.assertOnFail("Controls Page not loaded Successfully!",controlsNewUIPage.isControlsPageLoaded() , false);
                controlsNewUIPage.clickOnGlobalLocationButton();
                controlsNewUIPage.clickOnControlsWorkingHoursCard();
                // todo: Blocked by bug https://legiontech.atlassian.net/browse/SCH-4355
                // controlsNewUIPage.setDaypart("Lunch", "11am", "2pm");
                // controlsNewUIPage.setDaypart("Dinner", "4pm", "7pm");
            } else if (MyThreadLocal.getDriver().getCurrentUrl().contains(parameterMap.get("CinemarkWkdy_Enterprise"))) {
                configurationPage.goToConfigurationPage();
                configurationPage.clickOnConfigurationCrad("Operating Hours");
                configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
                configurationPage.clickOnEditButtonOnTemplateDetailsPage();
                configurationPage.selectDaypart("Lunch");
                configurationPage.selectDaypart("Dinner");
                configurationPage.setDaypart("All days","Lunch", "11am", "2pm");
                configurationPage.setDaypart("All days","Dinner", "4pm", "7pm");
                configurationPage.publishNowTheTemplate();
                switchToConsoleWindow();
            }

            // Verify Group by dayparts in the dropdown as an option
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()) , false);
            if (schedulePage.isGroupByDayPartsLoaded())
                SimpleUtils.pass("Schedule page 'Group by Day Parts' option is in the drop down list as expected");
            else
                SimpleUtils.fail("Schedule page 'Group by Day Parts' option isn't in the drop down list unexpectedly",false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate group by dayparts is available in both week and day view")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateGroupByDayPartsIsAvailableInWeekNDayViewAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            List<String> dayPartsDefined =  Arrays.asList("LUNCH", "DINNER", "UNSPECIFIED");

            // Verify group by dayparts is available in week view
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            boolean isContained = false;
            SimpleUtils.assertOnFail("Schedule page 'Group by Day Parts' option isn't in the drop down list", schedulePage.isGroupByDayPartsLoaded(),false);
            schedulePage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyDayParts.value);
            List<String> dayPartNamesInWeekView = schedulePage.getWeekScheduleShiftTitles();
            for (int i = 0; i < dayPartNamesInWeekView.size(); i++) {
                if (dayPartsDefined.contains(dayPartNamesInWeekView.get(i))) {
                    isContained = true;
                } else {
                    isContained = false;
                    break;
                }
            }
            if (isContained)
                SimpleUtils.pass("Schedule page: The shifts in week view are within defined day parts or 'UNSPECIFIED'");
            else
                SimpleUtils.fail("Schedule page: The shifts in week view are within defined day parts or 'UNSPECIFIED'", false);


            // Verify group by dayparts is available in day view
            schedulePage.clickOnDayView();
            List<String> dayPartNamesInDayView = schedulePage.getDayScheduleGroupLabels();
            for (int i = 0; i < dayPartNamesInDayView.size(); i++) {
                if (dayPartsDefined.contains(dayPartNamesInDayView.get(i))) {
                    isContained = true;
                } else {
                    isContained = false;
                    break;
                }
            }
            if (isContained)
                SimpleUtils.pass("Schedule page: The shifts in day view are within defined day parts or 'UNSPECIFIED'");
            else
                SimpleUtils.fail("Schedule page: The shifts in day view are within defined day parts or 'UNSPECIFIED'", false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate shift end time is within a daypart")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateShiftEndTimeIsWithinADayPartAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

            // Verify shift shows in the daypart when shift starts outside a daypart in week view
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated) {
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            SimpleUtils.assertOnFail("Schedule page 'Group by Day Parts' option isn't in the drop down list", schedulePage.isGroupByDayPartsLoaded(), false);
            schedulePage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyDayParts.value);
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            int randomIndex = schedulePage.getRandomIndexOfShift();
            WebElement shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "10am", "1pm");
            int shiftIndex = schedulePage.getTheIndexOfEditedShift();
            SimpleUtils.assertOnFail("Schedule Page: The shift doesn't show in the daypart when shift starts outside a daypart and ends within a daypart in week view", schedulePage.isShiftInDayPartOrNotInWeekView(shiftIndex, "LUNCH"), false);
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            // Verify shift shows in the daypart when shift starts inside a daypart in week view
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            randomIndex = schedulePage.getRandomIndexOfShift();
            shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "12pm", "2pm");
            shiftIndex = schedulePage.getTheIndexOfEditedShift();
            SimpleUtils.assertOnFail("Schedule Page: The shift doesn't show in the daypart when shift starts inside a daypart and ends inside a daypart in week view", schedulePage.isShiftInDayPartOrNotInWeekView(shiftIndex, "LUNCH"), false);
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            // Verify shift shows in the daypart when shift starts inside another daypart in week view
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            randomIndex = schedulePage.getRandomIndexOfShift();
            shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "11am", "5pm");
            shiftIndex = schedulePage.getTheIndexOfEditedShift();
            SimpleUtils.assertOnFail("Schedule Page: The shift doesn't show in the daypart when shift starts inside another daypart and ends inside a daypart in week view", schedulePage.isShiftInDayPartOrNotInWeekView(shiftIndex, "LUNCH"), false);
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            // Verify shift shows in the daypart when shift starts outside a daypart in day view
            schedulePage.clickOnDayView();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            randomIndex = schedulePage.getRandomIndexOfShift();
            shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "9am", "1pm");
            shiftIndex = schedulePage.getTheIndexOfEditedShift();
            SimpleUtils.assertOnFail("Schedule Page: The shift doesn't show in the daypart when shift starts outside a daypart and ends inside a daypart in day view", schedulePage.isShiftInDayPartOrNotInDayView(shiftIndex, "LUNCH"), false);
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            // Verify shift shows in the daypart when shift starts inside a daypart in day view
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            randomIndex = schedulePage.getRandomIndexOfShift();
            shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "11pm", "2pm");
            shiftIndex = schedulePage.getTheIndexOfEditedShift();
            SimpleUtils.assertOnFail("Schedule Page: The shift doesn't show in the daypart when shift starts inside a daypart and ends inside a daypart in day view", schedulePage.isShiftInDayPartOrNotInDayView(shiftIndex, "LUNCH"), false);
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            // Verify shift shows in the daypart when shift starts inside another daypart in day view
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            randomIndex = schedulePage.getRandomIndexOfShift();
            shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "12pm", "5pm");
            shiftIndex = schedulePage.getTheIndexOfEditedShift();
            SimpleUtils.assertOnFail("Schedule Page: The shift doesn't show in the daypart when shift starts inside another daypart and ends inside a daypart in day view", schedulePage.isShiftInDayPartOrNotInDayView(shiftIndex, "LUNCH"), false);
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

        @Automated(automated = "Automated")
        @Owner(owner = "Julie")
        @Enterprise(name = "KendraScott2_Enterprise")
        @TestName(description = "Validate either shift start time nor end time falls within a daypart")
        @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
        public void validateEitherShiftStartTimeNorEndTimeFallsWithinADaypartAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
            try {
                DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
                SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
                SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
                schedulePage.clickOnScheduleConsoleMenuItem();
                schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
                SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                        schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
                schedulePage.navigateToNextWeek();

                // Verify shift shows in Unspecified when shift doesn't fall in a daypart in week view
                boolean isWeekGenerated = schedulePage.isWeekGenerated();
                if (!isWeekGenerated) {
                    schedulePage.createScheduleForNonDGFlowNewUI();
                }
                SimpleUtils.assertOnFail("Schedule page 'Group by Day Parts' option isn't in the drop down list", schedulePage.isGroupByDayPartsLoaded(), false);
                schedulePage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyDayParts.value);
                schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                int randomIndex = schedulePage.getRandomIndexOfShift();
                WebElement shift = schedulePage.getTheShiftByIndex(randomIndex);
                schedulePage.editTheShiftTimeForSpecificShift(shift, "10am", "9pm");
                int shiftIndex = schedulePage.getTheIndexOfEditedShift();
                SimpleUtils.assertOnFail("Schedule Page: The shift doesn't show in Unspecified when shift doesn't fall in a daypart in week view", schedulePage.isShiftInDayPartOrNotInWeekView(shiftIndex, "UNSPECIFIED"), false);
                schedulePage.saveSchedule();
                schedulePage.publishActiveSchedule();

                // Verify shift shows in Unspecified when shift start equals exactly the end of a daypart in week view
                schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                randomIndex = schedulePage.getRandomIndexOfShift();
                shift = schedulePage.getTheShiftByIndex(randomIndex);
                schedulePage.editTheShiftTimeForSpecificShift(shift, "2pm", "8pm");
                shiftIndex = schedulePage.getTheIndexOfEditedShift();
                SimpleUtils.assertOnFail("Schedule Page: The shift doesn't show in Unspecified when shift start equals exactly the end of a daypart in week view", schedulePage.isShiftInDayPartOrNotInWeekView(shiftIndex, "UNSPECIFIED"), false);
                schedulePage.saveSchedule();
                schedulePage.publishActiveSchedule();

                // Verify shift shows in Unspecified when shift end equals exactly the start of a daypart in week view
                schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                randomIndex = schedulePage.getRandomIndexOfShift();
                shift = schedulePage.getTheShiftByIndex(randomIndex);
                schedulePage.editTheShiftTimeForSpecificShift(shift, "10am", "4pm");
                shiftIndex = schedulePage.getTheIndexOfEditedShift();
                SimpleUtils.assertOnFail("Schedule Page: The shift doesn't show in Unspecified when shift end equals exactly the start of a daypart in week view", schedulePage.isShiftInDayPartOrNotInWeekView(shiftIndex, "UNSPECIFIED"), false);
                schedulePage.saveSchedule();
                schedulePage.publishActiveSchedule();

                // Verify shift shows in Unspecified when shift doesn't fall in a daypart in day view
                schedulePage.clickOnDayView();
                schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                randomIndex = schedulePage.getRandomIndexOfShift();
                shift = schedulePage.getTheShiftByIndex(randomIndex);
                schedulePage.editTheShiftTimeForSpecificShift(shift, "8am", "3pm");
                shiftIndex = schedulePage.getTheIndexOfEditedShift();
                schedulePage.saveSchedule();
                SimpleUtils.assertOnFail("Schedule Page: The shift doesn't show in Unspecified when shift end equals exactly the start of a daypart in week view", schedulePage.isShiftInDayPartOrNotInDayView(shiftIndex, "UNSPECIFIED"), false);
                schedulePage.publishActiveSchedule();

                // Verify shift shows in Unspecified when shift start equals exactly the end of a daypart in day view
                schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                randomIndex = schedulePage.getRandomIndexOfShift();
                shift = schedulePage.getTheShiftByIndex(randomIndex);
                schedulePage.editTheShiftTimeForSpecificShift(shift, "2pm", "8pm");
                shiftIndex = schedulePage.getTheIndexOfEditedShift();
                schedulePage.saveSchedule();
                SimpleUtils.assertOnFail("Schedule Page: The shift doesn't show in Unspecified when shift start equals exactly the end of a daypart in week view", schedulePage.isShiftInDayPartOrNotInDayView(shiftIndex, "UNSPECIFIED"), false);
                schedulePage.publishActiveSchedule();

                // Verify shift shows in Unspecified when shift end equals exactly the start of a daypart in day view
                schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                randomIndex = schedulePage.getRandomIndexOfShift();
                shift = schedulePage.getTheShiftByIndex(randomIndex);
                schedulePage.editTheShiftTimeForSpecificShift(shift, "10am", "4pm");
                shiftIndex = schedulePage.getTheIndexOfEditedShift();
                schedulePage.saveSchedule();
                SimpleUtils.assertOnFail("Schedule Page: The shift doesn't show in Unspecified when shift end equals exactly the start of a daypart in week view", schedulePage.isShiftInDayPartOrNotInDayView(shiftIndex, "UNSPECIFIED"), false);
                schedulePage.publishActiveSchedule();

            } catch (Exception e) {
                SimpleUtils.fail(e.getMessage(), false);
            }
        }

    @Automated(automated = "Automated")
    @Owner(owner = "Haya")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate shift start time is within a daypart")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateShiftStartTimeWithinDaypartAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            CinemarkMinorPage cinemarkMinorPage = pageFactory.createConsoleCinemarkMinorPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            if (MyThreadLocal.getDriver().getCurrentUrl().contains(parameterMap.get("KendraScott2_Enterprise"))) {
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

                //delete unassigned shifts and open shifts.
                schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                schedulePage.deleteTMShiftInWeekView("unassigned");
                schedulePage.deleteTMShiftInWeekView("open");
                schedulePage.saveSchedule();
                List<String> shiftInfo = new ArrayList<>();
                while (shiftInfo.size() == 0) {
                    shiftInfo = schedulePage.getTheShiftInfoByIndex(0);
                }
                String workRole = shiftInfo.get(4);
                schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                schedulePage.clickOnDayViewAddNewShiftButton();
                schedulePage.customizeNewShiftPage();
                String shiftEndTime = "3";
                String shiftStartTime = "11";
                schedulePage.moveSliderAtCertainPoint(shiftEndTime, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
                schedulePage.moveSliderAtCertainPoint(shiftStartTime, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
                schedulePage.selectWorkRole(workRole);
                schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
                schedulePage.clickOnCreateOrNextBtn();
                schedulePage.saveSchedule();
                schedulePage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyDayParts.getValue());
                schedulePage.verifyNewAddedShiftFallsInDayPart("open",dayParts.LUNCH.getValue());
            } else if (MyThreadLocal.getDriver().getCurrentUrl().contains(parameterMap.get("CinemarkWkdy_Enterprise"))) {
            /*=======================================
            //Go to OP page
            locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
            SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad("Operating Hours");
            configurationPage.clickOnSpecifyTemplateName(MyThreadLocal.getCurrentOperatingTemplate(), "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectDaypart("Lunch");
            configurationPage.selectDaypart("Dinner");
            configurationPage.setDaypart("All days","Lunch", "11am", "2pm");
            configurationPage.setDaypart("All days","Dinner", "4pm", "7pm");
            configurationPage.publishNowTheTemplate();
            //go to Configuration
            controlsNewUIPage.clickOnControlsOperatingHoursSection();

            //Find the template
            cinemarkMinorPage.findDefaultTemplate(MyThreadLocal.getCurrentOperatingTemplate());
            cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Edit.getValue());
            cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.OKWhenEdit.getValue());

            //back to console.
            switchToConsoleWindow();
            ========================================*/
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

                //delete unassigned shifts and open shifts.
                schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                schedulePage.deleteTMShiftInWeekView("unassigned");
                schedulePage.deleteTMShiftInWeekView("open");
                schedulePage.saveSchedule();
                List<String> shiftInfo = new ArrayList<>();
                while (shiftInfo.size() == 0) {
                    shiftInfo = schedulePage.getTheShiftInfoByIndex(0);
                }
                String workRole = shiftInfo.get(4);
                schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                schedulePage.clickOnDayViewAddNewShiftButton();
                schedulePage.customizeNewShiftPage();
                String shiftEndTime = "3";
                String shiftStartTime = "11";
                schedulePage.moveSliderAtCertainPoint(shiftEndTime, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
                schedulePage.moveSliderAtCertainPoint(shiftStartTime, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
                schedulePage.selectWorkRole(workRole);
                schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
                schedulePage.clickOnCreateOrNextBtn();
                schedulePage.saveSchedule();
                schedulePage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyDayParts.getValue());
                schedulePage.verifyNewAddedShiftFallsInDayPart("open",dayParts.LUNCH.getValue());
            }

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate a shift appears in one daypart only")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateAShiftAppearsInOneDaypartOnlyAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            schedulePage.navigateToNextWeek();
            schedulePage.navigateToNextWeek();

            // Verify shift just appears in the first daypart when a shift starts inside a daypart and ends inside another daypart in week view
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated)
                schedulePage.createScheduleForNonDGFlowNewUI();
            if(schedulePage.isPublishButtonLoadedOnSchedulePage() || schedulePage.isRepublishButtonLoadedOnSchedulePage())
                schedulePage.publishActiveSchedule();
            SimpleUtils.assertOnFail("Schedule page 'Group by Day Parts' option isn't in the drop down list", schedulePage.isGroupByDayPartsLoaded(), false);
            schedulePage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyDayParts.value);
            int shiftCountBefore = schedulePage.getShiftsCount();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            int randomIndex = schedulePage.getRandomIndexOfShift();
            WebElement shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "12pm", "5pm");
            int shiftIndex = schedulePage.getTheIndexOfEditedShift();
            int shiftCountAfter = schedulePage.getShiftsCount();
            SimpleUtils.assertOnFail("Schedule Page: The shift shift doesn't just appear in the first daypart when a shift starts inside a daypart and ends inside another daypart in week view", schedulePage.isShiftInDayPartOrNotInWeekView(shiftIndex, "LUNCH") && shiftCountBefore == shiftCountAfter, false);
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            // Verify shift just appears in the second daypart when a shift starts outside the first daypart and ends inside the second daypart in week view
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            randomIndex = schedulePage.getRandomIndexOfShift();
            shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "10am", "6pm");
            shiftIndex = schedulePage.getTheIndexOfEditedShift();
            schedulePage.saveSchedule();
            shiftCountAfter = schedulePage.getShiftsCount();
            SimpleUtils.assertOnFail("Schedule Page: The shift doesn't just appear in the second daypart when a shift starts outside the first daypart and ends inside the second daypart in week view", schedulePage.isShiftInDayPartOrNotInWeekView(shiftIndex, "DINNER") && shiftCountBefore == shiftCountAfter, false);
            schedulePage.publishActiveSchedule();

            // Verify shift just appears in the first daypart when a shift starts inside the first daypart and ends outside the second daypart in week view
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            randomIndex = schedulePage.getRandomIndexOfShift();
            shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "12pm", "8pm");
            shiftIndex = schedulePage.getTheIndexOfEditedShift();
            schedulePage.saveSchedule();
            shiftCountAfter = schedulePage.getShiftsCount();
            SimpleUtils.assertOnFail("Schedule Page: The shift doesn't just appear in the first daypart when a shift starts inside the first daypart and ends outside the second daypart in week view", schedulePage.isShiftInDayPartOrNotInWeekView(shiftIndex, "LUNCH") && shiftCountBefore == shiftCountAfter, false);
            schedulePage.publishActiveSchedule();

            // Verify shift just appears in the first daypart when a shift starts inside a daypart and ends inside another daypart in day view
            schedulePage.clickOnDayView();
            shiftCountBefore = schedulePage.getShiftsCount();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            randomIndex = schedulePage.getRandomIndexOfShift();
            shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "12pm", "6pm");
            shiftIndex = schedulePage.getTheIndexOfEditedShift();
            schedulePage.saveSchedule();
            shiftCountAfter = schedulePage.getShiftsCount();
            SimpleUtils.assertOnFail("Schedule Page: The shift doesn't just appear in the first daypart when a shift starts inside a daypart and ends inside another daypart in day view", schedulePage.isShiftInDayPartOrNotInDayView(shiftIndex, "LUNCH") && shiftCountBefore == shiftCountAfter, false);
            schedulePage.publishActiveSchedule();

            // Verify shift just appears in the second daypart when a shift starts outside the first daypart and ends inside the second daypart in day view
            shiftCountBefore = schedulePage.getShiftsCount();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            randomIndex = schedulePage.getRandomIndexOfShift();
            shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "9am", "7pm");
            shiftIndex = schedulePage.getTheIndexOfEditedShift();
            schedulePage.saveSchedule();
            shiftCountAfter = schedulePage.getShiftsCount();
            SimpleUtils.assertOnFail("Schedule Page: The shift doesn't just appear in the first daypart when a shift starts inside a daypart and ends inside another daypart in day view", schedulePage.isShiftInDayPartOrNotInDayView(shiftIndex, "DINNER") && shiftCountBefore == shiftCountAfter, false);
            schedulePage.publishActiveSchedule();

            // Verify shift just appears in the first daypart when a shift starts inside the first daypart and ends outside the second daypart in day view
            shiftCountBefore = schedulePage.getShiftsCount();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            randomIndex = schedulePage.getRandomIndexOfShift();
            shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "11am", "8pm");
            shiftIndex = schedulePage.getTheIndexOfEditedShift();
            schedulePage.saveSchedule();
            shiftCountAfter = schedulePage.getShiftsCount();
            SimpleUtils.assertOnFail("Schedule Page: The shift doesn't just appear in the first daypart when a shift starts inside the first daypart and ends outside the second daypart in day view", schedulePage.isShiftInDayPartOrNotInDayView(shiftIndex, "LUNCH") && shiftCountBefore == shiftCountAfter, false);
            schedulePage.publishActiveSchedule();

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate daypart doesn't show when it hasn't shifts in it")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateDaypartNotShowWhenNoShiftsInItAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            schedulePage.navigateToNextWeek();

            // Verify no UNSPECIFIED when no shifts are within it in day view
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated)
                schedulePage.createScheduleForNonDGFlowNewUI();
            if(schedulePage.isPublishButtonLoadedOnSchedulePage() || schedulePage.isRepublishButtonLoadedOnSchedulePage())
                schedulePage.publishActiveSchedule();
            SimpleUtils.assertOnFail("Schedule page 'Group by Day Parts' option isn't in the drop down list", schedulePage.isGroupByDayPartsLoaded(), false);
            schedulePage.clickOnDayView();
            schedulePage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyDayParts.value);
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteAllShiftsOfGivenDayPartInDayView("UNSPECIFIED");
            schedulePage.saveSchedule();
            List<String> label1 = schedulePage.getDayScheduleGroupLabels();
            SimpleUtils.assertOnFail("Schedule Page: The daypart UNSPECIFIED is still shown when no shifts are within it in day view", !label1.contains("UNSPECIFIED"), false);
            schedulePage.publishActiveSchedule();

            // Verify no UNSPECIFIED when no shifts are within it in week view
            schedulePage.clickOnWeekView();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteAllShiftsOfGivenDayPartInWeekView("UNSPECIFIED");
            schedulePage.saveSchedule();
            List<String> titles1 = schedulePage.getWeekScheduleShiftTitles();
            SimpleUtils.assertOnFail("Schedule Page: The daypart is still shown when no shifts are within it in week view", !titles1.contains("UNSPECIFIED"), false);
            schedulePage.publishActiveSchedule();

            // Verify the daypart is not shown when no shifts are within it in week view
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteAllShiftsOfGivenDayPartInWeekView("DINNER");
            schedulePage.saveSchedule();
            List<String> titles2 = schedulePage.getWeekScheduleShiftTitles();
            SimpleUtils.assertOnFail("Schedule Page: The daypart is still shown when no shifts are within it in week view", !titles2.contains("DINNER"), false);
            schedulePage.publishActiveSchedule();

            // Verify the daypart is not shown when no shifts are within it in day view
            schedulePage.clickOnDayView();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            schedulePage.deleteAllShiftsOfGivenDayPartInDayView("LUNCH");
            schedulePage.saveSchedule();
            List<String> label2 = schedulePage.getDayScheduleGroupLabels();
            SimpleUtils.assertOnFail("Schedule Page: The daypart is still shown when no shifts are within it in day view", !label2.contains("LUNCH"), false);
            schedulePage.publishActiveSchedule();

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate dayparts work with Parent/Child LG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateDaypartsWorkForMSAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("District Whistler");
            locationSelectorPage.changeLocation("Lift Ops_Parent");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            schedulePage.navigateToNextWeek();

            // Verify shift shows in the daypart when shift starts inside a daypart and ends outside another daypart in week vieww
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated)
                schedulePage.createScheduleForNonDGFlowNewUI();
            if(schedulePage.isPublishButtonLoadedOnSchedulePage() || schedulePage.isRepublishButtonLoadedOnSchedulePage())
                schedulePage.publishActiveSchedule();
            SimpleUtils.assertOnFail("Schedule page 'Group by Day Parts' option isn't in the drop down list", schedulePage.isGroupByDayPartsLoaded(), false);
            schedulePage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyDayParts.value);
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            int randomIndex = schedulePage.getRandomIndexOfShift();
            WebElement shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "11am", "8pm");
            int shiftIndex = schedulePage.getTheIndexOfEditedShift();
            SimpleUtils.assertOnFail("Schedule Page: The shift doesn't show in the daypart when shift starts inside a daypart and ends outside another daypart in week view", schedulePage.isShiftInDayPartOrNotInWeekView(shiftIndex, "LUNCH"), false);
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            // Verify shift shows in the daypart when shift starts inside a daypart and ends outside another daypart in day view
            schedulePage.clickOnDayView();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            randomIndex = schedulePage.getRandomIndexOfShift();
            shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "11am", "8pm");
            shiftIndex = schedulePage.getTheIndexOfEditedShift();
            SimpleUtils.assertOnFail("Schedule Page: The shift doesn't show in the daypart when shift starts inside a daypart and ends outside another daypart in day view", schedulePage.isShiftInDayPartOrNotInDayView(shiftIndex, "LUNCH"), false);
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            // Verify shift shows in Unspecified when shift doesn't fall in a daypart in week view
            schedulePage.clickOnWeekView();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            randomIndex = schedulePage.getRandomIndexOfShift();
            shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "2pm", "4pm");
            shiftIndex = schedulePage.getTheIndexOfEditedShift();
            SimpleUtils.assertOnFail("Schedule Page: The shift shift doesn't show in Unspecified when shift doesn't fall in a daypart in week view", schedulePage.isShiftInDayPartOrNotInWeekView(shiftIndex, "UNSPECIFIED"), false);
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            // Verify shift shows in Unspecified when shift doesn't fall in a daypart in day view
            schedulePage.clickOnDayView();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            randomIndex = schedulePage.getRandomIndexOfShift();
            shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "10am", "8pm");
            shiftIndex = schedulePage.getTheIndexOfEditedShift();
            SimpleUtils.assertOnFail("Schedule Page: The shift shift doesn't show in Unspecified when shift doesn't fall in a daypart in day view", schedulePage.isShiftInDayPartOrNotInDayView(shiftIndex, "UNSPECIFIED"), false);
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            // Verify the daypart is not shown when no shifts are within it in week view
            schedulePage.clickOnWeekView();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            randomIndex = schedulePage.getRandomIndexOfShift();
            shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "2pm", "4pm");
            shiftIndex = schedulePage.getTheIndexOfEditedShift();
            SimpleUtils.assertOnFail("Schedule Page: The shift shift doesn't show in Unspecified when shift doesn't fall in a daypart in week view", schedulePage.isShiftInDayPartOrNotInWeekView(shiftIndex, "UNSPECIFIED"), false);
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Julie")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "Validate dayparts work with P2P LG")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void validateDaypartsWorkForP2PAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("Bay Area District");
            locationSelectorPage.changeLocation("LocGroup2");
            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
            schedulePage.navigateToNextWeek();

            // Verify shift shows in the daypart when shift starts inside a daypart and ends outside another daypart in week vieww
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated)
                schedulePage.createScheduleForNonDGFlowNewUI();
            if(schedulePage.isPublishButtonLoadedOnSchedulePage() || schedulePage.isRepublishButtonLoadedOnSchedulePage())
                schedulePage.publishActiveSchedule();
            SimpleUtils.assertOnFail("Schedule page 'Group by Day Parts' option isn't in the drop down list", schedulePage.isGroupByDayPartsLoaded(), false);
            schedulePage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyDayParts.value);
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            int randomIndex = schedulePage.getRandomIndexOfShift();
            WebElement shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "11am", "8pm");
            int shiftIndex = schedulePage.getTheIndexOfEditedShift();
            SimpleUtils.assertOnFail("Schedule Page: The shift doesn't show in the daypart when shift starts inside a daypart and ends outside another daypart in week view", schedulePage.isShiftInDayPartOrNotInWeekView(shiftIndex, "LUNCH"), false);
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            // Verify shift shows in the daypart when shift starts inside a daypart and ends outside another daypart in day view
            schedulePage.clickOnDayView();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            randomIndex = schedulePage.getRandomIndexOfShift();
            shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "11am", "8pm");
            shiftIndex = schedulePage.getTheIndexOfEditedShift();
            SimpleUtils.assertOnFail("Schedule Page: The shift doesn't show in the daypart when shift starts inside a daypart and ends outside another daypart in day view", schedulePage.isShiftInDayPartOrNotInDayView(shiftIndex, "LUNCH"), false);
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            // Verify shift shows in Unspecified when shift doesn't fall in a daypart in week view
            schedulePage.clickOnWeekView();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            randomIndex = schedulePage.getRandomIndexOfShift();
            shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "2pm", "4pm");
            shiftIndex = schedulePage.getTheIndexOfEditedShift();
            SimpleUtils.assertOnFail("Schedule Page: The shift shift doesn't show in Unspecified when shift doesn't fall in a daypart in week view", schedulePage.isShiftInDayPartOrNotInWeekView(shiftIndex, "UNSPECIFIED"), false);
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            // Verify shift shows in Unspecified when shift doesn't fall in a daypart in day view
            schedulePage.clickOnDayView();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            randomIndex = schedulePage.getRandomIndexOfShift();
            shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "10am", "8pm");
            shiftIndex = schedulePage.getTheIndexOfEditedShift();
            SimpleUtils.assertOnFail("Schedule Page: The shift shift doesn't show in Unspecified when shift doesn't fall in a daypart in day view", schedulePage.isShiftInDayPartOrNotInDayView(shiftIndex, "UNSPECIFIED"), false);
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

            // Verify the daypart is not shown when no shifts are within it in week view
            schedulePage.clickOnWeekView();
            schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            randomIndex = schedulePage.getRandomIndexOfShift();
            shift = schedulePage.getTheShiftByIndex(randomIndex);
            schedulePage.editTheShiftTimeForSpecificShift(shift, "2pm", "4pm");
            shiftIndex = schedulePage.getTheIndexOfEditedShift();
            SimpleUtils.assertOnFail("Schedule Page: The shift shift doesn't show in Unspecified when shift doesn't fall in a daypart in week view", schedulePage.isShiftInDayPartOrNotInWeekView(shiftIndex, "UNSPECIFIED"), false);
            schedulePage.saveSchedule();
            schedulePage.publishActiveSchedule();

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(),false);
        }
    }
}
