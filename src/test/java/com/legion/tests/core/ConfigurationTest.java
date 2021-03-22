package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;
import org.apache.commons.collections.ListUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.legion.utils.MyThreadLocal.setJobName;


public class ConfigurationTest extends TestBase {

    public enum modelSwitchOperation{
        Console("Console"),
        OperationPortal("Operation Portal");

        private final String value;
        modelSwitchOperation(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{


        this.createDriver((String)params[0],"83","Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

    }


    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Sanity Test Check point")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyUserCanOpenEachTypeTemplate(String browser, String username, String password, String location) throws Exception {

        try{
            List<String> templateTypeList = new ArrayList<String>(){{
                add("Operating Hours");
                add("Scheduling Policies");
                add("Schedule Collaboration");
                add("Time & Attendance");
                add("Compliance");
                add("Scheduling Rules");
                add("Communications");
            }};

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            for(String templateType:templateTypeList) {
                configurationPage.goToConfigurationPage();
                configurationPage.goToTemplateDetailsPage(templateType);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "New advanced staffing rules page verify")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyNewAdvancedStaffingRulePage(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Test";
            String workRole = "New Work Role";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
                configurationPage.goToConfigurationPage();
                configurationPage.clickOnConfigurationCrad(templateType);
                configurationPage.clickOnSpecifyTemplateName(templateName,mode);
                configurationPage.clickOnEditButtonOnTemplateDetailsPage();
                configurationPage.selectWorkRoleToEdit(workRole);
                configurationPage.checkTheEntryOfAddAdvancedStaffingRule();
                configurationPage.verifyAdvancedStaffingRulePageShowWell();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Days of Week check box validation")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCheckBoxOfDaysOfWeek(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Test";
            String workRole = "New Work Role";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            configurationPage.checkTheEntryOfAddAdvancedStaffingRule();
            configurationPage.verifyAdvancedStaffingRulePageShowWell();
            configurationPage.verifyCheckBoxOfDaysOfWeekSection();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Days of Week formula validation")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyInputFormulaForDaysOfWeek(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Test";
            String workRole = "New Work Role";
            String formula ="IsDay(p_Truck_Date,-1)";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            configurationPage.checkTheEntryOfAddAdvancedStaffingRule();
            configurationPage.verifyAdvancedStaffingRulePageShowWell();
            configurationPage.inputFormulaInForDaysOfWeekSection(formula);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Time of Day Start Section")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTimeOfDayStartSection(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Test";
            String workRole = "New Work Role";
            String offsetTime ="10";
            String startEventPoint = "before";
            List<String> dayPartsInGlobalConfig = new ArrayList<String>();
            List<String> ShiftStartTimeEventList = new ArrayList<String>();
            List<String> publicEventList = new ArrayList<String>(){{
                add("Opening Operating Hours");
                add("Closing Operating Hours");
                add("Opening Business Hours");
                add("Closing Business Hours");
            }};

            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            //go to locations tab
            locationsPage.clickOnLocationsTab();
            //go to Global Configuration tab
            locationsPage.goToGlobalConfigurationInLocations();
            dayPartsInGlobalConfig = locationsPage.getAllDayPartsFromGlobalConfiguration();
            publicEventList.addAll(dayPartsInGlobalConfig);

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            configurationPage.checkTheEntryOfAddAdvancedStaffingRule();
            configurationPage.verifyAdvancedStaffingRulePageShowWell();
            configurationPage.inputOffsetTimeForShiftStart(offsetTime,startEventPoint);
            configurationPage.validateShiftStartTimeUnitList();
            ShiftStartTimeEventList = configurationPage.getShiftStartTimeEventList();
            if (ListUtils.isEqualList(ShiftStartTimeEventList, publicEventList)) {
                SimpleUtils.pass("The list of start time event is correct");
            } else {
                SimpleUtils.fail("The list of start time event is NOT correct", false);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Time of Day During Section")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTimeOfDayDuringSection(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Test";
            String workRole = "New Work Role";
            String duringTime ="10";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            configurationPage.checkTheEntryOfAddAdvancedStaffingRule();
            configurationPage.verifyAdvancedStaffingRulePageShowWell();
            configurationPage.verifyRadioButtonInTimeOfDayIsSingletonSelect();
            configurationPage.inputShiftDuartionMinutes(duringTime);
            configurationPage.validateShiftDuartionTimeUnit();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Time of Day End Section")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTimeOfDayEndSection(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Test";
            String workRole = "New Work Role";
            String endOffsetTime ="10";
            String endEventPoint = "before";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            configurationPage.checkTheEntryOfAddAdvancedStaffingRule();
            configurationPage.verifyAdvancedStaffingRulePageShowWell();
            configurationPage.inputOffsetTimeForShiftEnd(endOffsetTime,endEventPoint);
            configurationPage.validateShiftEndTimeUnitList();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Time of Day Formula Section")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTimeOfDayFormulaSection(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Test";
            String workRole = "New Work Role";
            String formulaOfTimeOfDay = "123";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            configurationPage.checkTheEntryOfAddAdvancedStaffingRule();
            configurationPage.verifyAdvancedStaffingRulePageShowWell();
            configurationPage.tickOnCheckBoxOfTimeOfDay();
            configurationPage.inputFormulaInTextAreaOfTimeOfDay(formulaOfTimeOfDay);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Meal and Rest Breaks")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyMealAndRestBreaks(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Test";
            String workRole = "New Work Role";
            List<String> mealBreakInfo = new ArrayList<String>(){{
                add("30");
                add("60");
            }};
            List<String> restBreakInfo = new ArrayList<String>(){{
                add("20");
                add("45");
            }};

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            configurationPage.checkTheEntryOfAddAdvancedStaffingRule();
            configurationPage.verifyAdvancedStaffingRulePageShowWell();
            configurationPage.addNewMealBreak(mealBreakInfo);
            configurationPage.addMultipleMealBreaks(mealBreakInfo);
            configurationPage.deleteMealBreak();
            configurationPage.addNewRestBreak(restBreakInfo);
            configurationPage.addMultipleRestBreaks(restBreakInfo);
            configurationPage.deleteRestBreak();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Number of Shifts")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyNumberOfShifts(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Test";
            String workRole = "New Work Role";
            String shiftsNumber = "6";
            String shiftsNumberFormula = "5";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            configurationPage.checkTheEntryOfAddAdvancedStaffingRule();
            configurationPage.verifyAdvancedStaffingRulePageShowWell();
            configurationPage.inputNumberOfShiftsField(shiftsNumber);
            configurationPage.validCheckBoxOfNumberOfShiftsIsClickable();
            configurationPage.inputFormulaInFormulaTextAreaOfNumberOfShifts(shiftsNumberFormula);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Badges")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyBadgesOfAdvanceStaffingRules(String browser, String username, String password, String location) throws Exception {
        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Test";
            String workRole = "New Work Role";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            configurationPage.checkTheEntryOfAddAdvancedStaffingRule();
            configurationPage.verifyAdvancedStaffingRulePageShowWell();
            configurationPage.selectBadgesForAdvanceStaffingRules();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "X button and Check Mark button")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCrossAndCheckMarkButtonOfAdvanceStaffingRules(String browser, String username, String password, String location) throws Exception {
        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Test";
            String workRole = "New Work Role";
            List<String> days = new ArrayList<String>(){{
                add("Sunday");
                add("Friday");
            }};

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            configurationPage.checkTheEntryOfAddAdvancedStaffingRule();
            configurationPage.verifyAdvancedStaffingRulePageShowWell();
            configurationPage.selectDaysForDaysOfWeekSection(days);
            configurationPage.verifyCrossButtonOnAdvanceStaffingRulePage();
            configurationPage.checkTheEntryOfAddAdvancedStaffingRule();
            configurationPage.verifyAdvancedStaffingRulePageShowWell();
            configurationPage.selectDaysForDaysOfWeekSection(days);
            configurationPage.verifyCheckMarkButtonOnAdvanceStaffingRulePage();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Cancel button and Save button")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifySaveAndCancelButtonOfAdvanceStaffingRules(String browser, String username, String password, String location) throws Exception {
        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Test";
            String workRole1 = "New Work Role";
            String workRole2 = "1223add";
            List<String> days = new ArrayList<String>(){{
                add("Sunday");
                add("Friday");
            }};

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.saveOneAdvanceStaffingRule(workRole1,days);
            configurationPage.cancelSaveOneAdvanceStaffingRule(workRole2,days);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Add Edit and Delete advance staffing rule")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAddEditDeleteFunctionOfAdvanceStaffingRules(String browser, String username, String password, String location) throws Exception {
        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Test";
            String workRole = "New Work Role";
            String shiftsNumber = "7";
            List<String> days = new ArrayList<String>(){{
                add("Sunday");
                add("Friday");
            }};

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.addMutipleAdvanceStaffingRule(workRole,days);
            configurationPage.editAdvanceStaffingRule(shiftsNumber);
            configurationPage.deleteAdvanceStaffingRule();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


}
