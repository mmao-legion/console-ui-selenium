package com.legion.tests.core.OpsPortal;

import com.legion.api.toggle.ToggleAPI;
import com.legion.api.toggle.Toggles;
import com.legion.pages.*;
import com.legion.pages.OpsPortaPageFactories.ConfigurationPage;
import com.legion.pages.OpsPortaPageFactories.LocationsPage;
import com.legion.pages.OpsPortaPageFactories.SettingsAndAssociationPage;
import com.legion.pages.core.ConsoleLocationSelectorPage;
import com.legion.pages.core.OpCommons.OpsCommonComponents;
import com.legion.pages.core.OpsPortal.OpsPortalLocationsPage;
import com.legion.pages.core.opemployeemanagement.TimeOffPage;
import com.legion.pages.core.schedule.ConsoleScheduleCommonPage;
import com.legion.pages.core.schedule.ConsoleToggleSummaryPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.core.ScheduleTestKendraScott2;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;
import cucumber.api.java.ro.Si;
import org.apache.commons.collections.ListUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;


public class ConfigurationTest extends TestBase {

    public enum modelSwitchOperation{
        Console("Console"),
        OperationPortal("Control Center");

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
        ToggleAPI.disableToggle(Toggles.DynamicGroupV2.getValue(), "stoneman@legion.co", "admin11.a");
        ToggleAPI.enableToggle(Toggles.EnableDemandDriverTemplate.getValue(), "stoneman@legion.co", "admin11.a");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDoneWithoutUpdateUpperfield((String)params[1], (String)params[2],(String)params[3]);
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.verifyNewTermsOfServicePopUp();
        SimpleUtils.assertOnFail("Control Center not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

    }


    @Automated(automated = "Automated")
    @Owner(owner = "Lizzy")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify Dynamic Group Function->In Template Association")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDynamicGroupFunctionAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String OHtemplate = "Operating Hours";
            //scheduling rules is not included as some exception, will added later
            String mode = "edit";
            String templateName = "LizzyUsingDynamicCheckNoDelete";
            String dynamicGpName = "LZautoTest";
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(OHtemplate);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            //check the default save options
            configurationPage.commitTypeCheck();
            //go to Association to check dynamic group dialog UI
            configurationPage.dynamicGroupDialogUICheck(dynamicGpName);
            //edit the dynamic group
            configurationPage.editADynamicGroup(dynamicGpName);
            //search the dynamic group to delete
            configurationPage.deleteOneDynamicGroup(dynamicGpName);
            //save draft template
            configurationPage.saveADraftTemplate();

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    // Blocked by https://legiontech.atlassian.net/browse/OPS-4223
    @Automated(automated = "Automated")
    @Owner(owner = "Lizzy")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify Create Each Template with Dynamic Group Association ")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, enabled = false)
    public void verifyCreateEachTemplateWithDynamicGroupAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String OHtemplate = "Operating Hours";
            //scheduling rules is not included as some exception, will added later
            String[] tempType={"Operating Hours","Scheduling Policies","Schedule Collaboration","Compliance","Communications","Time & Attendance"};
            String templateNameVerify = "LizzyUsingToCreateTempTest";
            String dynamicGpNameTempTest = "LZautoTestDyGpName";
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            //create other types of templates
            for(String type:tempType){
                configurationPage.goToConfigurationPage();
                configurationPage.clickOnConfigurationCrad(type);
                configurationPage.createTmpAndPublishAndArchive(type,templateNameVerify,dynamicGpNameTempTest);
            }

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Lizzy")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Operating Hours")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyOperatiingHoursAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String OHtemplate = "Operating Hours";
            String mode = "edit";
            String templateName = "OHTemplateCreationCheck";
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(OHtemplate);
            //check the operating hours list
            configurationPage.OHListPageCheck();
            //create a OH template and check create page
            configurationPage.createOHTemplateUICheck(templateName);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Lizzy")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Add country field to holidays")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAddCountryFieldToHolidayAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String OHtemplate = "Operating Hours";
            //scheduling rules is not included as some exception, will added later
            String mode = "edit";
            String templateName = "LizzyUsingDynamicCheckNoDelete";
            String customerHolidayName = "LZautoTestHoliday";
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(OHtemplate);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            //check the Holidays pops up
            configurationPage.holidaysDataCheckAndSelect(customerHolidayName);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify open each type template")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyUserCanOpenEachTypeTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        try{
            List<String> templateTypeList = new ArrayList<String>(){{
                add("Operating Hours");
                add("Scheduling Policies");
                add("Schedule Collaboration");
                add("Time & Attendance");
                add("Compliance");
                add("Scheduling Rules");
//                add("Communications");
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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyNewAdvancedStaffingRulePageAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Fiona Auto Using";
            String workRole = "AutoUsing2";

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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCheckBoxOfDaysOfWeekAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Fiona Auto Using";
            String workRole = "AutoUsing2";

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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyInputFormulaForDaysOfWeekAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Fiona Auto Using";
            String workRole = "AutoUsing2";
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

    //requirement from ops-3151
    @Automated(automated = "Automated")
    @Owner(owner = "Lizzy")
    @Enterprise(name = "Op_Enterprise")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void ceateMultipleHistortForOHTempInternalAdminForConfiguration(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Operating Hours";
            String mode = "edit";
            //OH templaye  3153 on RC OPauto
            String templateName = "3153";
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            int now=configurationPage.historyRecordLimitCheck(templateName);
            if (now<101) {
                SimpleUtils.pass("History records displayed successfully!");
                //create another 100 records
                for (int i = 0; i < 103-now; i++) {
                    configurationPage.clickOnSpecifyTemplateName(templateName, mode);
                    configurationPage.clickOnEditButtonOnTemplateDetailsPage();
                    configurationPage.changeOHtemp();
                }
            } else {
                SimpleUtils.fail("History records excedded 100!", false);
            }
        }
        catch(Exception e){
                SimpleUtils.fail(e.getMessage(), false);
            }
        }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Time of Day Start Section")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTimeOfDayStartSectionAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Fiona Auto Using";
            String workRole = "AutoUsing2";
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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTimeOfDayDuringSectionAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Fiona Auto Using";
            String workRole = "AutoUsing2";
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
            configurationPage.inputShiftDurationMinutes(duringTime);
            configurationPage.validateShiftDurationTimeUnit();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Time of Day End Section")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTimeOfDayEndSectionAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Fiona Auto Using";
            String workRole = "AutoUsing2";
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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTimeOfDayFormulaSectionAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Fiona Auto Using";
            String workRole = "AutoUsing2";
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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyMealAndRestBreaksAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Fiona Auto Using";
            String workRole = "AutoUsing2";
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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyNumberOfShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Fiona Auto Using";
            String workRole = "AutoUsing2";
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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyBadgesOfAdvanceStaffingRulesAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Fiona Auto Using";
            String workRole = "AutoUsing2";

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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCrossAndCheckMarkButtonOfAdvanceStaffingRulesAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Fiona Auto Using";
            String workRole = "AutoUsing2";
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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifySaveAndCancelButtonOfAdvanceStaffingRulesAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Fiona Auto Using";
            String workRole1 = "AutoUsing2";
            String workRole2 = "Mgr on Duty";
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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAddEditDeleteFunctionOfAdvanceStaffingRulesAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Fiona Auto Using";
            String workRole = "AutoUsing2";
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
            configurationPage.addMultipleAdvanceStaffingRule(workRole,days);
            configurationPage.editAdvanceStaffingRule(shiftsNumber);
            configurationPage.deleteAdvanceStaffingRule();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Create all type template")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateAllTypeTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{

            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime=dfs.format(new Date()).trim();
            String templateName="AutoCreate"+currentTime;

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.addAllTypeOfTemplate(templateName);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate advance staffing rule should be shown correct")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAdvancedStaffingRulesShowWellAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Fiona Auto Using";
            String workRole = "AutoUsing2";
            String shiftsNumber = "7";
            List<String> days = new ArrayList<String>(){{
                add("Sunday");
                add("Friday");
            }};
            String startOffsetTime = "30";
            String startTimeUnit = "minutes";
            String startEventPoint = "after";
            String startEvent = "Opening Operating Hours";
            String endOffsetTime = "35";
            String endTimeUnit = "minutes";
            String endEventPoint = "before";
            String endEvent = "Closing Operating Hours";

            String shiftsNumber1 = "10";
            List<String> days1 = new ArrayList<String>(){{
                add("Tuesday");
                add("Saturday");
            }};
            String startOffsetTime1 = "40";
            String startTimeUnit1 = "minutes";
            String startEventPoint1 = "after";
            String startEvent1 = "Opening Business Hours";
            String endOffsetTime1 = "55";
            String endTimeUnit1 = "minutes";
            String endEventPoint1 = "before";
            String endEvent1 = "Closing Business Hours";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            configurationPage.checkTheEntryOfAddAdvancedStaffingRule();
            configurationPage.verifyAdvancedStaffingRulePageShowWell();
            configurationPage.validateAdvanceStaffingRuleShowing(startEvent,startOffsetTime,startEventPoint,startTimeUnit,
                    endEvent,endOffsetTime,endEventPoint,endTimeUnit,days,shiftsNumber);
            configurationPage.checkTheEntryOfAddAdvancedStaffingRule();
            configurationPage.verifyAdvancedStaffingRulePageShowWell();
            configurationPage.validateAdvanceStaffingRuleShowing(startEvent1,startOffsetTime1,startEventPoint1,startTimeUnit1,
                    endEvent1,endOffsetTime1,endEventPoint1,endTimeUnit1,days1,shiftsNumber1);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "E2E days of week validation")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void daysOfWeekE2EAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String locationName = "AutoUsingByFiona1";
            List<String> days = new ArrayList<String>(){{
                add("Friday");
                add("Sunday");
            }};
            List<String> daysAbbr = new ArrayList<String>();
            List<String> daysHasShifts = new ArrayList<String>();

            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            //Back to console
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
            locationSelectorPage.changeUpperFieldsByMagnifyGlassIcon(locationName);
            //go to schedule function
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            // Navigate to a week
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            // create the schedule if not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUIWithoutUpdateOH();
            daysHasShifts = scheduleShiftTablePage.verifyDaysHasShifts();

            for(String day:days){
                String dayAbbr = day.substring(0,3);
                daysAbbr.add(dayAbbr);
            }
            Collections.sort(daysHasShifts);
            Collections.sort(daysAbbr);
            if(days.size()==daysHasShifts.size()){
                if(ListUtils.isEqualList(daysAbbr,daysHasShifts)){
                    SimpleUtils.pass("User can create shifts correctly according to AVD staffing rule");
                }else {
                    SimpleUtils.fail("User can't create correct shifts according to AVD staffing rule",false);
                }
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "E2E Verify number of shift function in advance staffing rule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void numberOfShiftsInADVRuleE2EAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String locationName = "AutoUsingByFiona1";
            String shiftsNumber = "9";
            List<String> days = new ArrayList<String>(){{
                add("Friday");
                add("Sunday");
            }};
            List<String> daysAbbr = new ArrayList<String>();
            HashMap<String, String> hoursNTeamMembersCount = new HashMap<>();

            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            //Back to console to select one location
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
            locationSelectorPage.changeUpperFieldsByMagnifyGlassIcon(locationName);

            //go to schedule function
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            // Navigate to a week
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            // create the schedule if not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUIWithoutUpdateOH();
            hoursNTeamMembersCount = scheduleShiftTablePage.getTheHoursNTheCountOfTMsForEachWeekDays();

            List<String> numbersOfShifts = new ArrayList<String>();
            //get abbr for each work day which have shifts
            for(String day:days){
                String dayAbbr = day.substring(0,3);
                daysAbbr.add(dayAbbr);
            }
            //get TMs number for each work day which have shifts
            for(String dayAbbr:daysAbbr){
                String hoursAndTeamMembersCount = hoursNTeamMembersCount.get(dayAbbr);
                String tms = hoursAndTeamMembersCount.trim().split(" ")[1];
                String numberOfTM = tms.replaceAll("TMs", "");
                numbersOfShifts.add(numberOfTM);
            }
            for(String numbersOfShift:numbersOfShifts){
                if(numbersOfShift.equals(shiftsNumber)){
                    SimpleUtils.pass("Shifts number is aligned with advance staffing rule");
                }else {
                    SimpleUtils.fail("Shifts number is NOT aligned with advance staffing rule",false);
                }
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "E2E -> Verify the time of day setting in advance staffing rule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void timeOfDayInADVRuleE2EAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String locationName = "AutoUsingByFiona1";
            String shiftTime = "8:30 am - 4:00 pm";
            List<String> indexes = new ArrayList<String>();

            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            //back to console mode
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
            locationSelectorPage.changeUpperFieldsByMagnifyGlassIcon(locationName);

            //go to schedule function
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            // Navigate to a week
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            // create the schedule if not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUIWithoutUpdateOH();
            scheduleMainPage.clickOnFilterBtn();
            scheduleMainPage.selectShiftTypeFilterByText("Assigned");
            indexes = scheduleShiftTablePage.getIndexOfDaysHaveShifts();
            for(String index:indexes){
                scheduleShiftTablePage.verifyShiftTimeInReadMode(index,shiftTime);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "E2E Verify meal and rest break function in advance staffing rule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void mealAndRestBreakInADVRuleE2EAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String locationName = "AutoUsingByFiona1";
            String mealBreakTime = "1:30 pm - 2:15 pm";
            String restBreakTime = "2:30 pm - 3:15 pm";
            HashMap<String,String> mealRestBreaks= new HashMap<String,String>();

            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();

            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            //back to console mode
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
            locationSelectorPage.changeUpperFieldsByMagnifyGlassIcon(locationName);

            //go to schedule function
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            // Navigate to a week
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            // create the schedule if not created
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUIWithoutUpdateOH();
//          Click on edit button on week view
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            shiftOperatePage.clickOnProfileIcon();
            SimpleUtils.assertOnFail(" context of any TM display doesn't show well" , shiftOperatePage.verifyContextOfTMDisplay(), false);
//          Click On Profile icon -> Breaks
            shiftOperatePage.clickOnEditMeaLBreakTime();
            mealRestBreaks = shiftOperatePage.getMealAndRestBreaksTime();

            if(mealRestBreaks.get("Meal Break").compareToIgnoreCase(mealBreakTime) == 0){
                SimpleUtils.pass("The Meal Break info is correct");
            }else
                SimpleUtils.fail("The Meal Break info is incorrect",false);
            if(mealRestBreaks.get("Rest Break").compareToIgnoreCase(restBreakTime) == 0){
                SimpleUtils.pass("The Rest Break info is correct");
            }else
                SimpleUtils.fail("The Rest Break info is correct",false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Location Level Advance Staffing Rule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void locationLevelAdvanceStaffingRuleAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String shiftsNumber = "9";
            List<String> days = new ArrayList<String>(){{
                add("Sunday");
                add("Friday");
            }};
            String startOffsetTime = "30";
            String startTimeUnit = "minutes";
            String startEventPoint = "after";
            String startEvent = "Opening Operating Hours";
            String endOffsetTime = "60";
            String endTimeUnit = "minutes";
            String endEventPoint = "before";
            String endEvent = "Closing Operating Hours";
            String locationName = "AutoUsingByFiona1";
            String workRole = "Auto Using";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.goToLocationDetailsPage(locationName);
            locationsPage.goToConfigurationTabInLocationLevel();
            locationsPage.clickActionsForTemplate("Scheduling Rules","View");
            locationsPage.goToScheduleRulesListAtLocationLevel(workRole);
            configurationPage.validateAdvanceStaffingRuleShowingAtLocationLevel(startEvent,startOffsetTime,startEventPoint,startTimeUnit,
                    endEvent,endOffsetTime,endEventPoint,endTimeUnit,days,shiftsNumber);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify archive published template")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyArchivePublishedTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String action = "Archive";
            String templateType = "Scheduling Policies";
            String templateName = "UsedByAuto";
            String mode = "view";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.archiveIsClickable();
            configurationPage.verifyArchivePopUpShowWellOrNot();
            configurationPage.cancelArchiveDeleteWorkWell(templateName);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Nancy")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Schedule Policy")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTimeOffInSchedulePolicyAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String action = "Archive";
            String templateType = "Scheduling Policies";
            String templateName = "timeOffLimit";
            String mode = "edit";


            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);

            configurationPage.clickOnSpecifyTemplateName(templateName, mode);
            configurationPage.clickEdit();
            configurationPage.clickOK();
            configurationPage.verifyTimeOff();
            configurationPage.verifymaxNumEmployeesInput("0");
            configurationPage.verifymaxNumEmployeesInput("-1");
            configurationPage.verifymaxNumEmployeesInput("-1.0");
            configurationPage.verifymaxNumEmployeesInput("1.1");
            configurationPage.verifymaxNumEmployeesInput("1");
            configurationPage.publishNowTemplate();

            configurationPage.switchToControlWindow();

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeUpperFieldsByMagnifyGlassIcon("Newark-Don't Touch!!!");

            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.searchAndSelectTeamMemberByName("Della Murphy");

            TimeOffPage timeOffPage = new TimeOffPage();
            timeOffPage.switchToTimeOffTab();
            OpsCommonComponents commonComponents = new OpsCommonComponents();
            timeOffPage.createTimeOff("Annual Leave", false, 10, 10);
            String Month = timeOffPage.getMonth();
            commonComponents.okToActionInModal(true);
            timeOffPage.cancelTimeOffRequest();

            teamPage.goToTeam();
            teamPage.searchAndSelectTeamMemberByName("Allene Mante");
            timeOffPage.switchToTimeOffTab();

            timeOffPage.createTimeOff("Annual Leave", false, 10, 10);
            commonComponents.okToActionInModal(true);
            Assert.assertEquals(timeOffPage.getRequestErrorMessage(), "Maximum numbers of workers on time off exceeded on day " + Month + " 11");
            commonComponents.okToActionInModal(false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify multiple version template UI and Order")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyMultipleVersionTemplateCanShowWellInListAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{

            String templateType = "Operating Hours";
            String templateName = "ForMultipleAutoUITesting";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);

            configurationPage.verifyMultipleTemplateListUI(templateName);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Multiple version regression")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyMultipleVersionRegressionAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String templateType="Operating Hours";
            String templateName = "FionaMultipleTemp";
            String dynamicGpName = "ForMultipleAutoRegression";
            String button = "publish at different time";
            int date = 14;
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            //create Operating Hour template and published it
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.publishNewTemplate(templateName,dynamicGpName,"Custom","AutoCreatedDynamic---Format Script");
            configurationPage.archivePublishedOrDeleteDraftTemplate(templateName,"Archive");
            //create Operating Hour template and save as draft
            configurationPage.createNewTemplate(templateName);
            configurationPage.archivePublishedOrDeleteDraftTemplate(templateName,"Delete");
            //create Operating Hour template and publish at different time
            configurationPage.publishAtDifferentTimeTemplate(templateName,dynamicGpName,"Custom","AutoCreatedDynamic---Format Script",button,date);
            configurationPage.archivePublishedOrDeleteDraftTemplate(templateName,"Archive");

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Create future published version template")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyMultipleVersionTemplateCreationAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime =dfs.format(new Date()).trim();
            String templateType="Operating Hours";
            String templateName = "MultipleTemplate" + currentTime;
            String dynamicGpName = "MultipleTemplate" + currentTime;
            String formula ="AutoCreatedDynamic---Format Script" + currentTime;
            String button1 = "publish at different time";
            String button2 ="save as draft";
            int date = 14;
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            //create Operating Hour template and published it
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            //Create one current published version template
            configurationPage.publishNewTemplate(templateName,dynamicGpName,"Custom",formula);
            //Create future publish version based on current published version
            configurationPage.createFutureTemplateBasedOnExistingTemplate(templateName,button1,date,"edit");
            //Create draft version for current published version template and then create future publish template based on it

            //Verify it will replace itself when click 'Publish at different time' button in future template

            //Verify user can create draft template for each published version template
            configurationPage.createDraftForEachPublishInMultipleTemplate(templateName,button2,"edit");


        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify menu list for multiple version template")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyMenuListForMultipleVersionTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String templateType="Operating Hours";
            String templateName = "MultipleTemplateMenuListCheckingAutoUsing";
            List<String> current =new ArrayList<>(Arrays.asList("Save as draft","Publish now","Publish at different time"));
            List<String> future =new ArrayList<>(Arrays.asList("Save as draft","replacing","Publish at different time"));
            List<String> current1= new ArrayList<String>();
            List<String> future1= new ArrayList<String>();
            HashMap<String, List<String>> allMenuInfo= new HashMap<>();
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();

            //get menu list for current template and future template
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            allMenuInfo = configurationPage.verifyMenuListForMultipleTemplate(templateName);
            current1 = allMenuInfo.get("current");
            future1 = allMenuInfo.get("future");
            //Verify menu list of current template
            for(String menuName:current1){
                for(String menu:current){
                    if(menuName.contains(menu)){
                        SimpleUtils.pass(menuName + " is showing for current template");
                        break;
                    }
                }
            }
            //Verify menu list of future template
            for(String menuName:future1){
                for(String menu:future){
                    if(menuName.contains(menu)){
                        SimpleUtils.pass(menuName + " is showing for future template");
                        break;
                    }
                }
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify buttons showing for multiple template")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyButtonsShowingForMultipleTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String templateType="Operating Hours";
            String templateName = "MultipleTemplateMenuListCheckingAutoUsing";
            //Go to OH template list page
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            //Expand multiple version template
            configurationPage.expandMultipleVersionTemplate(templateName);
            //Verify buttons on published template details page
            configurationPage.verifyButtonsShowingOnPublishedTemplateDetailsPage();
            //Expand multiple version template
            configurationPage.expandMultipleVersionTemplate(templateName);
            //Verify buttons on draft template details page
            configurationPage.verifyButtonsShowingOnDraftTemplateDetailsPage();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "User can create/archive multiple template for all template type")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyUserCanCreateMultipleTemplateForAllTemplateTypeAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime =dfs.format(new Date()).trim();
            String templateName = "MultipleTemplate" + currentTime;
            String dynamicGpName = "MultipleTemplate" + currentTime;
            String button = "publish at different time";
            String criteriaType ="Custom";
            String criteriaValue="AutoCreatedDynamic---Format Script" + currentTime;
            int date = 14;
            String editOrViewMode = "Edit";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.createMultipleTemplateForAllTypeOfTemplate(templateName,dynamicGpName,criteriaType,criteriaValue,button,date,editOrViewMode);
//            configurationPage.archiveMultipleTemplate(templateName);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
    
    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify category configuration in settings")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCategoryConfigurationInSettingsForDemandDriverTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            String categoryName = "CategoryTest";
            String categoryEditName = "CategoryTest-Update";
            String description = "This is a test for Category configuration!";
            String verifyType = "category";

            //Go to Demand Driver template
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            //Go to Settings tab
            settingsAndAssociationPage.goToTemplateListOrSettings("Settings");
            //Add new category in settings.
            settingsAndAssociationPage.createNewChannelOrCategory(verifyType, categoryName, description);
            //Verify newly added category is in Forecast page
            switchToNewWindow();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(location);
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            ForecastPage forecastPage = pageFactory.createForecastPage();
            SalesForecastPage salesForecastPage = pageFactory.createSalesForecastPage();
            scheduleCommonPage.goToSchedulePage();
            forecastPage.clickForecast();
            salesForecastPage.navigateToSalesForecastTab();
            SimpleUtils.assertOnFail("The newly added category not exist in forecast page!",
                    salesForecastPage.verifyChannelOrCategoryExistInForecastPage("demand", categoryName), false);

            //edit the category in settings
            switchToNewWindow();
            settingsAndAssociationPage.clickOnEditBtnInSettings(verifyType, categoryName, categoryEditName);
            //verify edited category is in Forecast page
            switchToNewWindow();
            refreshPage();
            SimpleUtils.assertOnFail("The edited category not exist in forecast page!",
                    salesForecastPage.verifyChannelOrCategoryExistInForecastPage("demand", categoryEditName), false);

            //remove the category in settings
            switchToNewWindow();
            settingsAndAssociationPage.clickOnRemoveBtnInSettings(verifyType, categoryEditName);
            //verify the removed category not show up in forecast page.
            switchToNewWindow();
            refreshPage();
            SimpleUtils.assertOnFail("The removed edited category should not display in forecast page!",
                    !salesForecastPage.verifyChannelOrCategoryExistInForecastPage("demand", "categoryEditName"), false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Multiple Template E2E flow")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyMultipleTemplateE2EAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String templateType="Operating Hours";
            String templateName ="MultipleTemplateE2EUsing";
            String button = "publish at different time";
            int date = 14;
            String locationName="MultipleTemplateE2EUsing";
            HashMap<String, String> activeDayAndOperatingHrs = new HashMap<>();
            String currentOperatingHour = "6AM-12AM";
            String futureOperatingHour = "7AM-12AM";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            //get the future template's effective date, such as Jun 15, 2022
            String[] effectiveDate = configurationPage.updateEffectiveDateOfFutureTemplate(templateName,button,date).split(",");
            Collections.reverse(Arrays.asList(effectiveDate));
            //2022 Jun 15
            String effectiveDate1 = String.join(" ", effectiveDate).trim();
            String day = effectiveDate1.split(" ")[2];

            // go to console side to check the result
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
            LocationSelectorPage locationSelectorPage= new ConsoleLocationSelectorPage();
            locationSelectorPage.changeUpperFieldsByMagnifyGlassIcon(locationName);
            //go to schedule function
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            // Navigate to the specified week
            scheduleCommonPage.goToSpecificWeekByDate(effectiveDate1);
            scheduleCommonPage.clickOnFirstWeekInWeekPicker();

            // Ungenerate and create the schedule
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }

            ToggleSummaryPage consoleToggleSummaryPage = pageFactory.createToggleSummaryPage();
            //Check future template can show well or not
            if(!scheduleCommonPage.isSpecifyDayEqualWithFirstDayOfActivateWeek(day)){
                //go to next week
                scheduleCommonPage.navigateToNextWeek();
            }
            activeDayAndOperatingHrs = consoleToggleSummaryPage.getOperatingHrsValue("Sun");
            String futureOH = activeDayAndOperatingHrs.get("ScheduleOperatingHrs");
            if(futureOH.contains(futureOperatingHour)){
                SimpleUtils.pass("Future template can work well");
            }else {
                SimpleUtils.fail("Future template can NOT work well",false);
            }
            //Check current template can show well or not
            scheduleCommonPage.navigateToPreviousWeek();
            activeDayAndOperatingHrs = consoleToggleSummaryPage.getOperatingHrsValue("Sun");
            String currentOH = activeDayAndOperatingHrs.get("ScheduleOperatingHrs");
            if(currentOH.contains(currentOperatingHour)){
                SimpleUtils.pass("Current template can work well");
            }else {
                SimpleUtils.fail("Current template can NOT work well",false);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify channel configuration in settings")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyChannelConfigurationInSettingsForDemandDriverTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            String channelName = "ChannelTest";
            String channelEditName = "ChannelTest-Update";
            String description = "This is a test for channel configuration!";
            String verifyType = "channel";

            //Go to Demand Driver template
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            //Go to Settings tab
            settingsAndAssociationPage.goToTemplateListOrSettings("Settings");
            //Add new channel in settings.
            settingsAndAssociationPage.createNewChannelOrCategory(verifyType, channelName, description);
            //Verify newly added channel is in Forecast page
            switchToNewWindow();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(location);
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            ForecastPage forecastPage = pageFactory.createForecastPage();
            SalesForecastPage salesForecastPage = pageFactory.createSalesForecastPage();
            scheduleCommonPage.goToSchedulePage();
            forecastPage.clickForecast();
            salesForecastPage.navigateToSalesForecastTab();
            SimpleUtils.assertOnFail("The newly added channel not exist in forecast page!",
                    salesForecastPage.verifyChannelOrCategoryExistInForecastPage(verifyType, channelName), false);

            //edit the channel in settings
            switchToNewWindow();
            settingsAndAssociationPage.clickOnEditBtnInSettings(verifyType, channelName, channelEditName);
            //verify edited channel is in Forecast page
            switchToNewWindow();
            refreshPage();
            SimpleUtils.assertOnFail("The edited channel not exist in forecast page!",
                    salesForecastPage.verifyChannelOrCategoryExistInForecastPage(verifyType, channelEditName), false);

            //remove the channel in settings
            switchToNewWindow();
            settingsAndAssociationPage.clickOnRemoveBtnInSettings(verifyType, channelEditName);
            //verify the removed channel not show up in forecast page.
            switchToNewWindow();
            refreshPage();
            SimpleUtils.assertOnFail("The removed edited channel should not display in forecast page!",
                    !salesForecastPage.verifyChannelOrCategoryExistInForecastPage(verifyType, channelEditName), false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify Input Streams configuration in settings")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyInputStreamsConfigurationInSettingsForDemandDriverTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            String inputStreamName1 = "InputStreamTest-Base";
            String inputStreamName2 = "InputStreamTest-Aggregated";
            String verifyType = "input stream";

            //input stream specification information to add
            List<HashMap<String, String>> inputStreamInfoToAdd = new ArrayList<>();
            HashMap<String, String> inputStreamInfoToAdd1 = new HashMap<String, String>(){
                {
                    put("Name", inputStreamName1);
                    put("Type", "Base");
                    put("Tag", "Items:EDW:Enrollments");
                }
            };
            HashMap<String, String> inputStreamInfoToAdd2 = new HashMap<String, String>(){
                {
                    put("Name", inputStreamName2);
                    put("Type", "Aggregated");
                    put("Operator", "IN");
                    put("Streams", "All");
                    put("Tag", "Items:EDW:Aggregated");
                }
            };
            inputStreamInfoToAdd.add(inputStreamInfoToAdd1);
            inputStreamInfoToAdd.add(inputStreamInfoToAdd2);

            //input stream specification information to edit
            List<HashMap<String, String>> inputStreamInfoToEdit = new ArrayList<>();
            HashMap<String, String> inputStreamInfoToEdit1 = new HashMap<String, String>(){
                {
                    put("Name", inputStreamName1);
                    put("Type", "Base");
                    put("Tag", "Items:EDW:Enrollments-Updated");
                }
            };
            HashMap<String, String> inputStreamInfoToEdit2 = new HashMap<String, String>(){
                {
                    put("Name", inputStreamName2);
                    put("Type", "Aggregated");
                    put("Operator", "NOT IN");
                    put("Streams", inputStreamName1);
                    put("Tag", "Items:EDW:Aggregated-Updated");
                }
            };
            inputStreamInfoToEdit.add(inputStreamInfoToEdit1);
            inputStreamInfoToEdit.add(inputStreamInfoToEdit2);

            //Go to Demand Driver template
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            //Go to Settings tab
            settingsAndAssociationPage.goToTemplateListOrSettings("Settings");
            //Add new input stream in settings
            for (HashMap<String, String> inputStreamToAdd : inputStreamInfoToAdd){
                settingsAndAssociationPage.createInputStream(inputStreamToAdd);
            }

            //edit the input stream in settings
            settingsAndAssociationPage.clickOnEditBtnForInputStream(inputStreamInfoToAdd1, inputStreamInfoToEdit1);
            settingsAndAssociationPage.clickOnEditBtnForInputStream(inputStreamInfoToAdd2, inputStreamInfoToEdit2);

            //remove the category in settings
            settingsAndAssociationPage.clickOnRemoveBtnInSettings(verifyType, inputStreamName1);
            settingsAndAssociationPage.clickOnRemoveBtnInSettings(verifyType, inputStreamName2);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Very creating demand drivers template successfully")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateDemandDriverTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            String templateName = "testDemand-NotDelete";
            String noDriverWarningMsg = "At least one demand driver is required when publishing a template";
            String noAssociationWarningMsg = "At least one association when publish template";
            HashMap<String, String> driverSpecificInfo = new HashMap<String, String>(){
                {
                    put("Name", "Items:EDW:Enrollments");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Enrollments");
                    put("Show in App", "Yes");
                    put("Order", "1");
                    put("Forecast Source", "Legion ML");
                    put("Input Stream", "Items:EDW:Enrollments");
                }
            };

            //Go to Demand Driver template
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();

            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            //Go to Templates tab
            settingsAndAssociationPage.goToTemplateListOrSettings("Templates");
            //Add new demand driver template, warning message will show up when no driver created
            configurationPage.createNewTemplate(templateName);
            configurationPage.clickOnTemplateName(templateName);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.chooseSaveOrPublishBtnAndClickOnTheBtn("Publish Now");
            SimpleUtils.assertOnFail("There should be a warning message for no drivers", configurationPage.verifyWarningInfoForDemandDriver(noDriverWarningMsg), false);

            //Add new demand driver, warning message will show up when no association
            configurationPage.addDemandDriverInTemplate(driverSpecificInfo);
            configurationPage.chooseSaveOrPublishBtnAndClickOnTheBtn("Publish Now");
            SimpleUtils.assertOnFail("There should be a warning message for no association", configurationPage.verifyWarningInfoForDemandDriver(noAssociationWarningMsg), false);

            //Add association and save
            configurationPage.createDynamicGroup(templateName, "Location Name", null);
            configurationPage.selectOneDynamicGroup(templateName);
            //Could publish normally
            configurationPage.clickOnTemplateDetailTab();
            configurationPage.publishNowTemplate();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify demand driver template detail duplicated adding check")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDemandDriverTemplateDetailsDuplicatedAddingCheckAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateName = "testDemand-NotDelete";
            String templateType = "Demand Drivers";

            HashMap<String, String> driverWithExistingName = new HashMap<String, String>()
            {
                {
                    put("Name", "Items:EDW:Enrollments");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Verifications");
                    put("Input Stream", "Items:EDW:Verifications");
                }
            };
            HashMap<String, String> driverWithExistingCombination = new HashMap<String, String>()
            {
                {
                    put("Name", "Items:EDW:New");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Enrollments");
                    put("Input Stream", "Items:EDW:Verifications");
                }
            };
            //Go to Demand Driver template
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();

            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            //Go to Templates tab
            settingsAndAssociationPage.goToTemplateListOrSettings("Templates");

            //Choose an existing template, add a driver with existing name
            configurationPage.clickOnTemplateName(templateName);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.addDemandDriverInTemplate(driverWithExistingName);

            //Choose an existing template, add a driver with existing Type+Channel+Category
            configurationPage.addDemandDriverInTemplate(driverWithExistingCombination);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Location Level override")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyLocationLevelOverriddenAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String templateType="Operating Hours";
            String templateName ="MultipleTemplateE2EUsing";
            String locationName="MultipleTemplateLocationLevelChecking";
            HashMap<String, String> activeDayAndOperatingHrs = new HashMap<>();
            String overriddenOperatingHour = "8AM-12AM";
            List<String> effectiveDatesOfMultipleTemplates = new ArrayList<String>();

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            //get the future template's effective date, such as Jun 15, 2022
            effectiveDatesOfMultipleTemplates = configurationPage.getEffectiveDateForTemplate(templateName);
            String[] effectiveDate = effectiveDatesOfMultipleTemplates.get(effectiveDatesOfMultipleTemplates.size()-1).split(",");
            Collections.reverse(Arrays.asList(effectiveDate));
            //2022 Jun 15
            String effectiveDate1 = String.join(" ", effectiveDate).trim();
            String day = effectiveDate1.split(" ")[2];

            // go to console side to check the result
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
            LocationSelectorPage locationSelectorPage= new ConsoleLocationSelectorPage();
            locationSelectorPage.changeUpperFieldsByMagnifyGlassIcon(locationName);
            //go to schedule function
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            // Navigate to the specified week
            scheduleCommonPage.goToSpecificWeekByDate(effectiveDate1);
            scheduleCommonPage.clickOnFirstWeekInWeekPicker();

            // Ungenerate and create the schedule
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated) {
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }

            ToggleSummaryPage consoleToggleSummaryPage = pageFactory.createToggleSummaryPage();
            //Check future template can show well or not
            if(!scheduleCommonPage.isSpecifyDayEqualWithFirstDayOfActivateWeek(day)){
                //go to next week
                scheduleCommonPage.navigateToNextWeek();
            }
            activeDayAndOperatingHrs = consoleToggleSummaryPage.getOperatingHrsValue("Sun");
            String futureOH = activeDayAndOperatingHrs.get("ScheduleOperatingHrs");
            if(futureOH.contains(overriddenOperatingHour)){
                SimpleUtils.pass("Future week's OH is showing with overridden value.");
            }else {
                SimpleUtils.fail("Future week's OH isn't showing with overridden value.",false);
            }
            //Check current template can show well or not
            scheduleCommonPage.navigateToPreviousWeek();
            activeDayAndOperatingHrs = consoleToggleSummaryPage.getOperatingHrsValue("Sun");
            String currentOH = activeDayAndOperatingHrs.get("ScheduleOperatingHrs");
            if(currentOH.contains(overriddenOperatingHour)){
                SimpleUtils.pass("Current week's OH is showing with overridden value.");
            }else {
                SimpleUtils.fail("Current week's OH isn't showing with overridden value.",false);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Nancy")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify Dynamic Group Function>In Workforce Sharing")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyScheduleCollaborationOfDynamicGroupAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
        try{
            String templateType = "Schedule Collaboration";
            String templateName = "testDynamicGroup";
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime = dfs.format(new Date()).trim();
            String groupNameForWFS = "AutoWFS" + currentTime;
            String description = "AutoCreate" + currentTime;
            String criteria = "Config Type";
            String criteriaUpdate = "Country";
            String searchText = "AutoCreate";


            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);

            boolean flag = configurationPage.isTemplateListPageShow();
            if(flag){
                SimpleUtils.pass("Template landing page shows well");
            }else
                SimpleUtils.fail("Template landing page loads failed",false);

            configurationPage.searchTemplate(templateName);

            configurationPage.clickOnTemplateName(templateName);

            configurationPage.clickEdit();
            configurationPage.clickOK();

            configurationPage.clickOnAssociationTabOnTemplateDetailsPage();

            OpsPortalLocationsPage opsPortalLocationsPage = new OpsPortalLocationsPage();
            opsPortalLocationsPage.eidtExistingDGP();
            opsPortalLocationsPage.searchWFSDynamicGroup(searchText);

            opsPortalLocationsPage.verifyCriteriaList();
            String locationNum = opsPortalLocationsPage.addWorkforceSharingDGWithOneCriteria(groupNameForWFS,description,criteria);
            String locationNumAftUpdate = opsPortalLocationsPage.editWFSDynamicGroup(groupNameForWFS,criteriaUpdate);

            if (!locationNumAftUpdate.equalsIgnoreCase(locationNum)) {
                SimpleUtils.pass("Update workforce sharing dynamic group successfully");
            }

            opsPortalLocationsPage.removedSearchedWFSDG();
            opsPortalLocationsPage.addWorkforceSharingDGWithOneCriteria(groupNameForWFS,"",criteria);
            opsPortalLocationsPage.removedSearchedWFSDG();

            opsPortalLocationsPage.addWorkforceSharingDGWithOneCriteria(groupNameForWFS+"Custom",description,"Custom");
            opsPortalLocationsPage.addWorkforceSharingDGWithOneCriteria(groupNameForWFS+"Custom",description,"Custom");
            opsPortalLocationsPage.verifyDuplicatedDGErrorMessage();
            opsPortalLocationsPage.removedSearchedWFSDG();
        }catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

}