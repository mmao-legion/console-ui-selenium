package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
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

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception {
        try {
            this.createDriver((String) params[0], "69", "Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
            if (MyThreadLocal.getDriver().getCurrentUrl().contains(parameterMap.get("KendraScott2_Enterprise")) && (MyThreadLocal.getCurrentOperatingTemplate()==null || MyThreadLocal.getCurrentOperatingTemplate().equals(""))){

            } else if (MyThreadLocal.getDriver().getCurrentUrl().contains(parameterMap.get("CinemarkWkdy_Enterprise")) && (MyThreadLocal.getCurrentOperatingTemplate()==null || MyThreadLocal.getCurrentOperatingTemplate().equals(""))){
                getAndSetDefaultOperatingHoursTemplate((String) params[3]);
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
        //go to Configuration
        cinemarkMinorPage.clickConfigurationTabInOP();
        controlsNewUIPage.clickOnControlsOperatingHoursSection();

        //Find the template
        cinemarkMinorPage.findDefaultTemplate(MyThreadLocal.getCurrentOperatingTemplate());
        cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.Edit.getValue());
        cinemarkMinorPage.clickOnBtn(CinemarkMinorTest.buttonGroup.OKWhenEdit.getValue());
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
                configurationPage.setDaypart("Lunch", "11am", "2pm");
                configurationPage.setDaypart("Dinner", "4pm", "7pm");
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
                    schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()) , false);
            List<String> dayPartsDefined =  Arrays.asList("LUNCH", "DINNER", "UNSPECIFIED");

            // Verify group by dayparts is available in week view
            boolean isWeekGenerated = schedulePage.isWeekGenerated();
            if (!isWeekGenerated){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }
            boolean isContained = false;
            if (schedulePage.isGroupByDayPartsLoaded()) {
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
            } else
                SimpleUtils.fail("Schedule page 'Group by Day Parts' option isn't in the drop down list",true);

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

    
}
