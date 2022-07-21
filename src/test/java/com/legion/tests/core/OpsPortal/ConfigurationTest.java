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
import com.legion.pages.core.OpsPortal.OpsPortalSettingsAndAssociationPage;
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
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.security.auth.login.Configuration;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
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
        ToggleAPI.enableToggle(Toggles.MixedModeDemandDriverSwitch.getValue(), "stoneman@legion.co", "admin11.a");
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
            String locationName = "AutoCreate20220227202919";

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
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(locationName);
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
            String locationName = "AutoCreate20220227202919";

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
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(locationName);
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
            String inputStreamName1 = "InputStream-Base";
            String inputStreamName2 = "InputStream-Aggregated";
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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, priority = 0)
    public void verifyCreateDemandDriverTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            String templateName = "testDemand-NotDelete";
            String noDriverWarningMsg = "At least one demand driver is required when publishing a template";
            String noAssociationWarningMsg = "At least one association when publish template";
            String addOrEdit = "Add";
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
            configurationPage.clickAddOrEditForDriver(addOrEdit);
            configurationPage.addOrEditDemandDriverInTemplate(driverSpecificInfo);
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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, priority = 1)
    public void verifyDemandDriverTemplateDetailsDuplicatedAddingCheckAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateName = "testDemand-NotDelete";
            String templateType = "Demand Drivers";
            String addOrEdit = "Add";

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
            configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.clickAddOrEditForDriver(addOrEdit);
            configurationPage.addOrEditDemandDriverInTemplate(driverWithExistingName);

            //Choose an existing template, add a driver with existing Type+Channel+Category
            configurationPage.clickAddOrEditForDriver(addOrEdit);
            configurationPage.addOrEditDemandDriverInTemplate(driverWithExistingCombination);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Very demand drivers template as publish now")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, priority = 2)
    public void verifyDemandDriverTemplateAsPublishNowAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateName = "testDemand-NotDelete";
            String templateType = "Demand Drivers";
            SimpleDateFormat dfs = new SimpleDateFormat("dd, yyyy");
            Month month = LocalDate.now().getMonth();
            String shortMonth = month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            String currentDate = shortMonth + " " + dfs.format(new Date()).trim();

           //Go to Demand Driver template
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();

            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            //Go to Templates tab
            settingsAndAssociationPage.goToTemplateListOrSettings("Templates");

            //Check the template with published and draft version
            configurationPage.verifyPublishedTemplateAfterEdit(templateName);
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            //Get and verify effective date
            String effectiveDate = configurationPage.getEffectiveDateForTemplate(templateName).get(0);
            SimpleUtils.assertOnFail("the effective Date is not Today!", effectiveDate.equalsIgnoreCase(currentDate), false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Very demand drivers template details editing")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, priority = 3)
    public void verifyDemandDriverTemplateDetailsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
        String templateName = "testDemand-NotDelete";
        String templateType = "Demand Drivers";
        HashMap<String, String> driverSpecificInfo = new HashMap<String, String>(){
            {
                put("Name", "Items:EDW:Verifications");
                put("Type", "Items");
                put("Channel", "EDW");
                put("Category", "Verifications");
                put("Show in App", "Yes");
                put("Order", "1");
                put("Forecast Source", "Imported");
                put("Input Stream", "Items:EDW:Verifications");
            }
        };

        HashMap<String, String> driverSpecificInfoUpdated = new HashMap<String, String>(){
            {
                put("Name", "Items:EDW:Verifications-Updated");
                put("Type", "Items");
                put("Channel", "EDW");
                put("Category", "Verifications");
                put("Show in App", "No");
                put("Order", "2");
                put("Forecast Source", "Legion ML");
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

        //Enter the template, add a driver
        configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
        configurationPage.clickOnEditButtonOnTemplateDetailsPage();
        configurationPage.clickAddOrEditForDriver("Add");
        configurationPage.addOrEditDemandDriverInTemplate(driverSpecificInfo);
        SimpleUtils.assertOnFail("Can not find the driver you search!", configurationPage.searchDriverInTemplateDetailsPage(driverSpecificInfo.get("Name")), false);

        //Update the added driver
        configurationPage.clickAddOrEditForDriver("Edit");
        configurationPage.addOrEditDemandDriverInTemplate(driverSpecificInfoUpdated);
        SimpleUtils.assertOnFail("Can not find the driver you search!", configurationPage.searchDriverInTemplateDetailsPage(driverSpecificInfoUpdated.get("Name")), false);

        //Remove the added driver
        configurationPage.clickRemove();
        SimpleUtils.assertOnFail("Failed to removed the driver!", !configurationPage.searchDriverInTemplateDetailsPage(driverSpecificInfoUpdated.get("Name")), false);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate Creation for Input Stream in Settings page.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreationForInputStreamInSettingsPageCAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
        String templateType = "Demand Drivers";
        String verifyType = "input stream";
        String baseInputStreamName1 = "InputStreamTest-Base01";
        String aggregatedInputStreamName1 = "InputStreamTest-Aggregated01";
        String aggregatedInputStreamName2 = "InputStreamTest-Aggregated02";

        //input stream specification information to add
        HashMap<String, String> baseInputStreamInfoToAdd = new HashMap<String, String>(){
            {
                put("Name", baseInputStreamName1);
                put("Type", "Base");
                put("Tag", "Items:EDW:Base01");
            }
        };
        HashMap<String, String> aggregatedInputStreamInfoToAdd1 = new HashMap<String, String>(){
            {
                put("Name", aggregatedInputStreamName1);
                put("Type", "Aggregated");
                put("Operator", "IN");
                put("Streams", "All");
                put("Tag", aggregatedInputStreamName1);
            }
        };
        HashMap<String, String> aggregatedInputStreamInfoToAdd2 = new HashMap<String, String>(){
            {
                put("Name", aggregatedInputStreamName2);
                put("Type", "Aggregated");
                put("Operator", "NOT IN");
                put("Streams", baseInputStreamName1);
                put("Tag", aggregatedInputStreamName2);
            }
        };

        //Go to Demand Driver template
        ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
        SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();

        configurationPage.goToConfigurationPage();
        configurationPage.clickOnConfigurationCrad(templateType);
        //Go to Settings tab
        settingsAndAssociationPage.goToTemplateListOrSettings("Settings");

        //Add new Base input stream in settings
        settingsAndAssociationPage.createInputStream(baseInputStreamInfoToAdd);
        WebElement baseSearchElement = settingsAndAssociationPage.searchSettingsForDemandDriver(verifyType, baseInputStreamName1);
        settingsAndAssociationPage.verifyInputStreamInList(baseInputStreamInfoToAdd, baseSearchElement);
        settingsAndAssociationPage.searchSettingsForDemandDriver(verifyType, "");

        //Add new Aggregated input stream with IN Operator in settings
        settingsAndAssociationPage.createInputStream(aggregatedInputStreamInfoToAdd1);
        WebElement aggregatedSearchElement = settingsAndAssociationPage.searchSettingsForDemandDriver(verifyType, aggregatedInputStreamName1);
        settingsAndAssociationPage.verifyInputStreamInList(aggregatedInputStreamInfoToAdd1, aggregatedSearchElement);
        settingsAndAssociationPage.searchSettingsForDemandDriver(verifyType, "");

        //Add an input stream with existing name
        settingsAndAssociationPage.createInputStream(baseInputStreamInfoToAdd);

        //Add new Aggregated input stream with NOT IN Operator in settings
        settingsAndAssociationPage.createInputStream(aggregatedInputStreamInfoToAdd2);
        WebElement aggregatedSearchElement2 = settingsAndAssociationPage.searchSettingsForDemandDriver(verifyType, aggregatedInputStreamName2);
        settingsAndAssociationPage.verifyInputStreamInList(aggregatedInputStreamInfoToAdd2, aggregatedSearchElement2);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify Edit input stream in settings.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyEditForInputStreamInSettingsPageAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            String verifyType = "input stream";
            String baseInputStreamName1 = "InputStreamTest-Base01";
            String aggregatedInputStreamName1 = "InputStreamTest-Aggregated01";
            String aggregatedInputStreamName2 = "InputStreamTest-Aggregated02";

            //input stream specification information to edit
            HashMap<String, String> baseInputStreamInfo = new HashMap<String, String>(){
                {
                    put("Name", baseInputStreamName1);
                    put("Type", "Base");
                    put("Tag", "Items:EDW:Base01");
                }
            };
            HashMap<String, String> aggregatedInputStreamInfo1 = new HashMap<String, String>(){
                {
                    put("Name", aggregatedInputStreamName1);
                    put("Type", "Aggregated");
                    put("Operator", "IN");
                    put("Streams", "All");
                    put("Tag", aggregatedInputStreamName1);
                }
            };
            HashMap<String, String> aggregatedInputStreamInfo2 = new HashMap<String, String>(){
                {
                    put("Name", aggregatedInputStreamName2);
                    put("Type", "Aggregated");
                    put("Operator", "NOT IN");
                    put("Streams", baseInputStreamName1);
                    put("Tag", aggregatedInputStreamName2);
                }
            };

            //update input stream to below specification information
            HashMap<String, String> baseInputStreamInfoUpdated = new HashMap<String, String>(){
                {
                    put("Name", baseInputStreamName1);
                    put("Type", "Base");
                    put("Tag", "Items:EDW:Base01-Update");
                }
            };
            HashMap<String, String> aggregatedInputStreamInfoUpdated1 = new HashMap<String, String>(){
                {
                    put("Name", aggregatedInputStreamName1);
                    put("Type", "Aggregated");
                    put("Operator", "NOT IN");
                    put("Streams", "Items:EDW:Verifications");
                    put("Tag", aggregatedInputStreamName1 + "Updated");
                }
            };
            HashMap<String, String> aggregatedInputStreamInfoUpdated2 = new HashMap<String, String>(){
                {
                    put("Name", aggregatedInputStreamName2);
                    put("Type", "Aggregated");
                    put("Operator", "IN");
                    put("Streams", "All");
                    put("Tag", aggregatedInputStreamName2 + "Updated");
                }
            };

            //update input stream to below specification information
            HashMap<String, String> baseInputStreamToAggregated = new HashMap<String, String>(){
                {
                    put("Name", baseInputStreamName1);
                    put("Type", "Aggregated");
                    put("Operator", "IN");
                    put("Streams", "All");
                    put("Tag", "Items:EDW:Base-to-Aggregated");
                }
            };
            HashMap<String, String> aggregatedInputStreamToBase = new HashMap<String, String>(){
                {
                    put("Name", aggregatedInputStreamName1);
                    put("Type", "Base");
                    put("Tag", aggregatedInputStreamName1 + "-to-Base");
                }
            };
            HashMap<String, String> baseInputStreamToAggregatedWithNotIn = new HashMap<String, String>(){
                {
                    put("Name", aggregatedInputStreamName1);
                    put("Type", "Aggregated");
                    put("Operator", "NOT IN");
                    put("Streams", "Items:EDW:Verifications");
                    put("Tag", aggregatedInputStreamName2 + "-to-Base-Aggregated-NotIn");
                }
            };

            //Go to Demand Driver template
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            //Go to Settings tab
            settingsAndAssociationPage.goToTemplateListOrSettings("Settings");

            //Choose the Base input stream to edit, change the data tag
            settingsAndAssociationPage.clickOnEditBtnForInputStream(baseInputStreamInfo, baseInputStreamInfoUpdated);
            WebElement baseSearchElement = settingsAndAssociationPage.searchSettingsForDemandDriver(verifyType, baseInputStreamName1);
            settingsAndAssociationPage.verifyInputStreamInList(baseInputStreamInfoUpdated, baseSearchElement);
            settingsAndAssociationPage.searchSettingsForDemandDriver(verifyType, "");

            //Choose the Aggregated input stream to edit, change IN to NOT IN
            settingsAndAssociationPage.clickOnEditBtnForInputStream(aggregatedInputStreamInfo1, aggregatedInputStreamInfoUpdated1);
            WebElement aggregatedSearchElement = settingsAndAssociationPage.searchSettingsForDemandDriver(verifyType, aggregatedInputStreamName1);
            settingsAndAssociationPage.verifyInputStreamInList(aggregatedInputStreamInfoUpdated1, aggregatedSearchElement);
            settingsAndAssociationPage.searchSettingsForDemandDriver(verifyType, "");

            //Choose the Aggregated input stream to edit, change NOT IN to IN
            settingsAndAssociationPage.clickOnEditBtnForInputStream(aggregatedInputStreamInfo2, aggregatedInputStreamInfoUpdated2);
            WebElement aggregatedSearchElement01 = settingsAndAssociationPage.searchSettingsForDemandDriver(verifyType, aggregatedInputStreamName2);
            settingsAndAssociationPage.verifyInputStreamInList(aggregatedInputStreamInfoUpdated2, aggregatedSearchElement01);
            settingsAndAssociationPage.searchSettingsForDemandDriver(verifyType, "");

            //Choose the Base input stream to edit, change to Aggregated With IN
            settingsAndAssociationPage.clickOnEditBtnForInputStream(baseInputStreamInfoUpdated, baseInputStreamToAggregated);
            WebElement baseToAggregatedSearchElement = settingsAndAssociationPage.searchSettingsForDemandDriver(verifyType, baseInputStreamName1);
            settingsAndAssociationPage.verifyInputStreamInList(baseInputStreamToAggregated, baseToAggregatedSearchElement);
            settingsAndAssociationPage.searchSettingsForDemandDriver(verifyType, "");

            //Choose the Aggregated input stream to edit, change to Base
            settingsAndAssociationPage.clickOnEditBtnForInputStream(aggregatedInputStreamInfoUpdated1, aggregatedInputStreamToBase);
            WebElement aggregatedToBaseSearchElement = settingsAndAssociationPage.searchSettingsForDemandDriver(verifyType, aggregatedInputStreamName1);
            settingsAndAssociationPage.verifyInputStreamInList(aggregatedInputStreamToBase, aggregatedToBaseSearchElement);
            settingsAndAssociationPage.searchSettingsForDemandDriver(verifyType, "");

            //Choose the Base input stream to edit, change to Aggregated with NOT IN
            settingsAndAssociationPage.clickOnEditBtnForInputStream(aggregatedInputStreamToBase, baseInputStreamToAggregatedWithNotIn);
            WebElement baseToAggregatedWithNotInSearchElement = settingsAndAssociationPage.searchSettingsForDemandDriver(verifyType, aggregatedInputStreamName1);
            settingsAndAssociationPage.verifyInputStreamInList(baseInputStreamToAggregatedWithNotIn, baseToAggregatedWithNotInSearchElement);
            settingsAndAssociationPage.searchSettingsForDemandDriver(verifyType, "");
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify remove input stream in settings.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, dependsOnMethods = {"verifyEditForInputStreamInSettingsPageAsInternalAdmin"})
    public void verifyRemoveInputStreamAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
        String templateType = "Demand Drivers";
        String verifyType = "input stream";
        String baseInputStreamName1 = "InputStreamTest-Base01";
        String aggregatedInputStreamName1 = "InputStreamTest-Aggregated01";
        String aggregatedInputStreamName2 = "InputStreamTest-Aggregated02";

        //Go to Demand Driver template
        ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
        SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
        configurationPage.goToConfigurationPage();
        configurationPage.clickOnConfigurationCrad(templateType);
        //Go to Settings tab
        settingsAndAssociationPage.goToTemplateListOrSettings("Settings");

        //Remove
        settingsAndAssociationPage.clickOnRemoveBtnInSettings(verifyType, baseInputStreamName1);
        settingsAndAssociationPage.clickOnRemoveBtnInSettings(verifyType, aggregatedInputStreamName1);
        settingsAndAssociationPage.clickOnRemoveBtnInSettings(verifyType, aggregatedInputStreamName2);
                } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify demand drivers template as draft")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, priority = 4)
    public void verifyDemandDriverTemplatesAsDraftAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            String templateName = "testDemand-NotDelete";
            String driverNameToEdit = "Items:EDW:Enrollments";

            HashMap<String, String> driverSpecificInfoUpdated = new HashMap<String, String>(){
                {
                    put("Name", "Items:EDW:Enrollments-Updated");
                    put("Show in App", "No");
                }
            };
            HashMap<String, String> driverSpecificInfoToAdd = new HashMap<String, String>(){
                {
                    put("Name", "Items:EDW:Verifications");
                    put("Type", "Amount");
                    put("Channel", "EDW");
                    put("Category", "Verifications");
                    put("Show in App", "Yes");
                    put("Order", "1");
                    put("Forecast Source", "Imported");
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

            //Choose one demand driver, edit
            configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            //Search and edit a driver
            configurationPage.searchDriverInTemplateDetailsPage(driverNameToEdit);
            configurationPage.clickAddOrEditForDriver("Edit");
            configurationPage.addOrEditDemandDriverInTemplate(driverSpecificInfoUpdated);
            configurationPage.searchDriverInTemplateDetailsPage(driverSpecificInfoUpdated.get("Name"));

            //Add a driver
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(driverSpecificInfoToAdd);
            configurationPage.searchDriverInTemplateDetailsPage(driverSpecificInfoToAdd.get("Name"));
            configurationPage.chooseSaveOrPublishBtnAndClickOnTheBtn("Save as Draft");

            //Check the template information and effective date
            configurationPage.verifyMultipleTemplateListUI(templateName);
            SimpleUtils.assertOnFail("The effective date for the draft version should be empty!", configurationPage.getEffectiveDateForTemplate(templateName).get(1).equals(""), false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify demand drivers template archive")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, priority = 5)
    public void verifyDemandDriverTemplatesToArchiveAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
        String templateType = "Demand Drivers";
        String templateName = "testDemand-NotDelete";

        //Go to Demand Driver template
        ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
        SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
        configurationPage.goToConfigurationPage();
        configurationPage.clickOnConfigurationCrad(templateType);
        //Go to Templates tab
        settingsAndAssociationPage.goToTemplateListOrSettings("Templates");
        //Delete the association and Archive the template
        configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
        configurationPage.clickOnEditButtonOnTemplateDetailsPage();
        settingsAndAssociationPage.goToAssociationTabOnTemplateDetailsPage();
        configurationPage.deleteOneDynamicGroup(templateName);
        configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
        configurationPage.setLeaveThisPageButton();
        configurationPage.archiveOrDeleteTemplate(templateName);
    } catch (Exception e) {
        SimpleUtils.fail(e.getMessage(), false);
    }
}

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Check UI for Input Stream in Settings page.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyUIForInputStreamsInSettingsPageAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            HashMap<String, String> baseInputStreamInfoToAdd = new HashMap<String, String>(){
                {
                    put("Name", "baseInputStream-test");
                    put("Type", "Base");
                    put("Tag", "Items:EDW:Base01");
                }
            };
            //Go to Demand Driver template
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            //Go to Templates tab
            settingsAndAssociationPage.goToTemplateListOrSettings("Settings");
            //Archive the template
            settingsAndAssociationPage.createInputStream(baseInputStreamInfoToAdd);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Very demand drivers template landing page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDemandDriversTemplateLandingPageAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            String templateName = "testDemand-NotDelete";

            //Go to Demand Driver template
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            //Go to Templates tab
            settingsAndAssociationPage.goToTemplateListOrSettings("Template");
            //Check information for the template
            configurationPage.verifyMultipleTemplateListUI(templateName);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify default input streams when enter a new enterprise.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheDefaultInputStreamsWhenEnterANewEnterpriseAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            List<String> allStreamNames = new ArrayList<>();
            String verifyType = "input stream";
            int streamsCount = 0;
            int channelsCount = 0;
            int categoriesCount = 0;

            //Go to Demand Driver template
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            //Go to Settings tab
            settingsAndAssociationPage.goToTemplateListOrSettings("Settings");
            allStreamNames = settingsAndAssociationPage.getStreamNamesInList("All");
            if (allStreamNames.size() != 0){
                for (String streamName : allStreamNames){
                    settingsAndAssociationPage.clickOnRemoveBtnInSettings(verifyType, streamName);
                }
            }

            //After remove all the input streams, calculate the generated default input stream.
            streamsCount = settingsAndAssociationPage.getTotalNumberForChannelOrCategory(verifyType);
            channelsCount = settingsAndAssociationPage.getTotalNumberForChannelOrCategory("channel");
            categoriesCount = settingsAndAssociationPage.getTotalNumberForChannelOrCategory("category");

            if (streamsCount == (channelsCount * categoriesCount)){
                SimpleUtils.pass("Default input streams are generated correctly!");
            }else if(streamsCount == 0 ){
                SimpleUtils.fail("No default input stream generated! ", false);
            }else{
                SimpleUtils.fail("Total number of default input stream is not correct! ", false);
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify default demand driver template when enter a new enterprise.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTheDefaultDemandDriverTemplateWhenEnterANewEnterpriseAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";

            //Go to Demand Driver template
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);

            //Go to template association page, delete all templates
            configurationPage.clickOnSpecifyTemplateName("Default", "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            settingsAndAssociationPage.goToAssociationTabOnTemplateDetailsPage();
            configurationPage.deleteOneDynamicGroup("Default");
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.setLeaveThisPageButton();

            //Remove all the current templates
            configurationPage.removeAllDemandDriverTemplates();

            //Check if default template generated
            SimpleUtils.assertOnFail("There is no default template generated after delete All!", configurationPage.searchTemplate("Default"), false);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify input stream in demand driver")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyInputStreamInDemandDriverTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            String templateName = "Default";
            List<String> streamNameList = new ArrayList<>();
            List<String> streamNamesInDriverPage = new ArrayList<>();

            //Go to Demand Driver template
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);

            //Go to settings page, get all the input streams
            settingsAndAssociationPage.goToTemplateListOrSettings("Settings");
            streamNameList = settingsAndAssociationPage.getStreamNamesInList("All");
            //Go to driver details page, get all the input streams
            settingsAndAssociationPage.goToTemplateListOrSettings("Template");
            configurationPage.clickOnSpecifyTemplateName("Default", "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.clickAddOrEditForDriver("Add");
            streamNamesInDriverPage = configurationPage.getInputStreamInDrivers();

            Collections.sort(streamNameList);
            Collections.sort(streamNamesInDriverPage);
            if(streamNameList.size()==streamNamesInDriverPage.size()){
                if(ListUtils.isEqualList(streamNameList, streamNamesInDriverPage)){
                    SimpleUtils.pass("Input Streams in driver details page and Settings page are totally the same!");
                }else {
                    SimpleUtils.fail("Input Streams in driver details page and Settings page are NOT the same!",false);
                }
            }else {
                SimpleUtils.fail("Input Streams in driver details page and Settings page are NOT the same!",false);
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify Forecast page when only default demand driver template exist")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, enabled = false)
    public void verifyVisibilityOnForecastPageForDefaultDemandDriverTemplateOnlyAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            String templateName = "Default";
            List<String> channelNameList = new ArrayList<>();
            List<String> categoryNameList = new ArrayList<>();
            String locationToTest = "Boston";

            //Go to Demand Driver template
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            settingsAndAssociationPage.goToAssociationTabOnTemplateDetailsPage();
            configurationPage.deleteOneDynamicGroup(templateName);
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.setLeaveThisPageButton();

            //Remove all the current templates
            configurationPage.removeAllDemandDriverTemplates();
            //Go to Settings tab
            settingsAndAssociationPage.goToTemplateListOrSettings("Settings");
            channelNameList = settingsAndAssociationPage.getAllChannelsOrCategories("Channel");
            categoryNameList = settingsAndAssociationPage.getAllChannelsOrCategories("Category");
            //Verify channel/category visibility in Forecast page
            refreshCache("Template");
            switchToNewWindow();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(locationToTest);
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            ForecastPage forecastPage = pageFactory.createForecastPage();
            SalesForecastPage salesForecastPage = pageFactory.createSalesForecastPage();
            scheduleCommonPage.goToSchedulePage();
            forecastPage.clickForecast();
            salesForecastPage.navigateToSalesForecastTab();
            //Check visibility for each channel&category
            for (int i = 0; i < channelNameList.size(); i++){
                SimpleUtils.assertOnFail("The channel " + channelNameList.get(i) + " should show up in forecast page!",
                        salesForecastPage.verifyChannelOrCategoryExistInForecastPage("channel", channelNameList.get(i)), false);
            }
            for (int i = 0; i < categoryNameList.size(); i++){
                SimpleUtils.assertOnFail("The category " + categoryNameList.get(i) + " should show up in forecast page!",
                        salesForecastPage.verifyChannelOrCategoryExistInForecastPage("demand", categoryNameList.get(i)), false);
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify Forecast page for one newly created demand driver template")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, enabled = false)
    public void verifyForecastPageForOneNewlyCreatedDemandDriverTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
        String templateType = "Demand Drivers";
        String templateName = "testVisibility";
        HashMap<String, String> visibledriverInfo = new HashMap<String, String>(){
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
        HashMap<String, String> invisibledriverInfo = new HashMap<String, String>(){
            {
                put("Name", "Items:EDW:Verifications");
                put("Type", "Items");
                put("Channel", "EDW");
                put("Category", "Verifications");
                put("Show in App", "No");
                put("Order", "2");
                put("Forecast Source", "Imported");
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
        //Add new demand driver template
        configurationPage.createNewTemplate(templateName);
        configurationPage.clickOnTemplateName(templateName);
        configurationPage.clickOnEditButtonOnTemplateDetailsPage();

        //Add two drivers, set up visible and invisible
        configurationPage.clickAddOrEditForDriver("Add");
        configurationPage.addOrEditDemandDriverInTemplate(visibledriverInfo);
        configurationPage.clickAddOrEditForDriver("Add");
        configurationPage.addOrEditDemandDriverInTemplate(invisibledriverInfo);
        //Add association and save
        configurationPage.createDynamicGroup(templateName, "Location Name", location);
        configurationPage.selectOneDynamicGroup(templateName);
        //Could publish normally
        configurationPage.clickOnTemplateDetailTab();
        configurationPage.publishNowTemplate();

        //Verify channel/category visibility in Forecast page
        refreshCache("Template");
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
        SimpleUtils.assertOnFail("The channel EDW should show up in forecast page!",
                salesForecastPage.verifyChannelOrCategoryExistInForecastPage("channel", visibledriverInfo.get("Channel")), false);
        SimpleUtils.assertOnFail("The category Enrollments should show up in forecast page!",
                salesForecastPage.verifyChannelOrCategoryExistInForecastPage("demand",  visibledriverInfo.get("Category")), false);
        SimpleUtils.assertOnFail("The category Verifications should not show up in forecast page!",
                !salesForecastPage.verifyChannelOrCategoryExistInForecastPage("demand", invisibledriverInfo.get("Category")), false);
        //Remove the associated locations and templates
        configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
        configurationPage.clickOnEditButtonOnTemplateDetailsPage();
        settingsAndAssociationPage.goToAssociationTabOnTemplateDetailsPage();
        configurationPage.deleteOneDynamicGroup(templateName);
        configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
        configurationPage.setLeaveThisPageButton();
        configurationPage.deleteTemplate(templateName);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify Forecast page for different newly created demand driver template")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, enabled = false)
    public void verifyForecastPageForDifferentNewlyCreatedDemandDriverTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            String templateName1 = "testVisibility_Diff1";
            String templateName2 = "testVisibility_Diff2";
            String locationName1 = "Boston";
            String locationName2 = "Cleveland";
            HashMap<String, String> invisibleInfo_location1 = new HashMap<String, String>(){
                {
                    put("Name", "Items:EDW:Enrollments");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Enrollments");
                    put("Show in App", "No");
                    put("Order", "1");
                    put("Forecast Source", "Legion ML");
                    put("Input Stream", "Items:EDW:Enrollments");
                }
            };
            HashMap<String, String> invisibleInfo_location2 = new HashMap<String, String>(){
                {
                    put("Name", "Items:EDW:Verifications");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Verifications");
                    put("Show in App", "No");
                    put("Order", "1");
                    put("Forecast Source", "Imported");
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
            //Add first demand driver template
            configurationPage.createNewTemplate(templateName1);
            configurationPage.clickOnTemplateName(templateName1);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();

            //Add one driver, set up visibility
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(invisibleInfo_location1);
            //Add association and save
            configurationPage.createDynamicGroup(templateName1, "Location Name", locationName1);
            configurationPage.selectOneDynamicGroup(templateName1);
            //Could publish normally
            configurationPage.clickOnTemplateDetailTab();
            configurationPage.publishNowTemplate();

            //Add second demand driver template
            configurationPage.createNewTemplate(templateName2);
            configurationPage.clickOnTemplateName(templateName2);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();

            //Add one driver, set up visibility
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(invisibleInfo_location2);
            //Add association and save
            configurationPage.createDynamicGroup(templateName2, "Location Name", locationName2);
            configurationPage.selectOneDynamicGroup(templateName2);
            //Could publish normally
            configurationPage.clickOnTemplateDetailTab();
            configurationPage.publishNowTemplate();

            //Verify channel/category visibility in Forecast page
            refreshCache("Template");
            switchToNewWindow();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(locationName1);
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            ForecastPage forecastPage = pageFactory.createForecastPage();
            SalesForecastPage salesForecastPage = pageFactory.createSalesForecastPage();
            scheduleCommonPage.goToSchedulePage();
            forecastPage.clickForecast();
            salesForecastPage.navigateToSalesForecastTab();
            //Check channel/category visibility for location1
            SimpleUtils.assertOnFail("The channel EDW should not show up in forecast page!",
                    !salesForecastPage.verifyChannelOrCategoryExistInForecastPage("channel", invisibleInfo_location1.get("Channel")), false);
            SimpleUtils.assertOnFail("The category Verifications should show up in forecast page!",
                    salesForecastPage.verifyChannelOrCategoryExistInForecastPage("demand",  invisibleInfo_location2.get("Category")), false);
            SimpleUtils.assertOnFail("The category Enrollments should not show up in forecast page!",
                    !salesForecastPage.verifyChannelOrCategoryExistInForecastPage("demand", invisibleInfo_location1.get("Category")), false);

            //Check channel/category visibility for location2
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(locationName1);
            forecastPage.clickForecast();
            salesForecastPage.navigateToSalesForecastTab();
            SimpleUtils.assertOnFail("The channel EDW should not show up in forecast page!",
                    !salesForecastPage.verifyChannelOrCategoryExistInForecastPage("channel", invisibleInfo_location2.get("Channel")), false);
            SimpleUtils.assertOnFail("The category Enrollments should show up in forecast page!",
                    salesForecastPage.verifyChannelOrCategoryExistInForecastPage("demand",  invisibleInfo_location1.get("Category")), false);
            SimpleUtils.assertOnFail("The category Verifications should not show up in forecast page!",
                    !salesForecastPage.verifyChannelOrCategoryExistInForecastPage("demand", invisibleInfo_location2.get("Category")), false);
            switchToNewWindow();

            //Remove the associated locations and templates
            configurationPage.clickOnSpecifyTemplateName(templateName1, "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            settingsAndAssociationPage.goToAssociationTabOnTemplateDetailsPage();
            configurationPage.deleteOneDynamicGroup(templateName1);
            configurationPage.deleteOneDynamicGroup(templateName2);
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.setLeaveThisPageButton();
            configurationPage.deleteTemplate(templateName1);
            configurationPage.deleteTemplate(templateName2);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify Forecast page for one newly created and default demand driver template")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, enabled = false)
    public void verifyForecastPageForOneNewlyCreatedDemandDriverTemplateForDGV2AsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
        String templateType = "Demand Drivers";
        String templateName = "testVisibility_V2";
        String locationName1 = "Boston_V2";
        String locationName2 = "Cleveland_V2";
        HashMap<String, String> invisibleInfo_location = new HashMap<String, String>(){
            {
                put("Name", "Items:EDW:Verifications");
                put("Type", "Items");
                put("Channel", "EDW");
                put("Category", "Verifications");
                put("Show in App", "No");
                put("Order", "1");
                put("Forecast Source", "Imported");
                put("Input Stream", "Items:EDW:Verifications");
            }
        };
        HashMap<String, String> visibleInfo_location = new HashMap<String, String>(){
            {
                put("Name", "Items:EDW:Enrollments");
                put("Type", "Items");
                put("Channel", "EDW");
                put("Category", "Enrollments");
                put("Show in App", "Yes");
                put("Order", "2");
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

        //Add first demand driver template
        configurationPage.createNewTemplate(templateName);
        configurationPage.clickOnTemplateName(templateName);
        configurationPage.clickOnEditButtonOnTemplateDetailsPage();

        //Add one driver, set up visibility
        configurationPage.clickAddOrEditForDriver("Add");
        configurationPage.addOrEditDemandDriverInTemplate(invisibleInfo_location);
        configurationPage.clickAddOrEditForDriver("Add");
        configurationPage.addOrEditDemandDriverInTemplate(visibleInfo_location);
        //Add association and save
        configurationPage.createDynamicGroup(templateName, "Location Name", locationName1);
        configurationPage.selectOneDynamicGroup(templateName);
        //Could publish normally
        configurationPage.clickOnTemplateDetailTab();
        configurationPage.publishNowTemplate();

        //Verify channel/category visibility in Forecast page
        refreshCache("Template");
        switchToNewWindow();
        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
        locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(locationName1);
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
        ForecastPage forecastPage = pageFactory.createForecastPage();
        SalesForecastPage salesForecastPage = pageFactory.createSalesForecastPage();
        scheduleCommonPage.goToSchedulePage();
        forecastPage.clickForecast();
        salesForecastPage.navigateToSalesForecastTab();
        //Check channel/category visibility for location1
        SimpleUtils.assertOnFail("The channel EDW should show up in forecast page!",
                salesForecastPage.verifyChannelOrCategoryExistInForecastPage("channel", visibleInfo_location.get("Channel")), false);
        SimpleUtils.assertOnFail("The category Verifications should not show up in forecast page!",
                !salesForecastPage.verifyChannelOrCategoryExistInForecastPage("demand",  invisibleInfo_location.get("Category")), false);
        SimpleUtils.assertOnFail("The category Enrollments should show up in forecast page!",
                salesForecastPage.verifyChannelOrCategoryExistInForecastPage("demand", visibleInfo_location.get("Category")), false);

        //Check channel/category visibility for location2
        locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(locationName2);
        forecastPage.clickForecast();
        salesForecastPage.navigateToSalesForecastTab();
        SimpleUtils.assertOnFail("The channel EDW should show up in forecast page!",
                salesForecastPage.verifyChannelOrCategoryExistInForecastPage("channel", visibleInfo_location.get("Channel")), false);
        SimpleUtils.assertOnFail("The category Verifications should  show up in forecast page!",
                salesForecastPage.verifyChannelOrCategoryExistInForecastPage("demand",  invisibleInfo_location.get("Category")), false);
        SimpleUtils.assertOnFail("The category Enrollments should show up in forecast page!",
                salesForecastPage.verifyChannelOrCategoryExistInForecastPage("demand", visibleInfo_location.get("Category")), false);
        //Remove the associated locations and templates
        configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
        configurationPage.clickOnEditButtonOnTemplateDetailsPage();
        settingsAndAssociationPage.goToAssociationTabOnTemplateDetailsPage();
        configurationPage.deleteOneDynamicGroup(templateName);
        configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
        configurationPage.setLeaveThisPageButton();
        configurationPage.archiveOrDeleteTemplate(templateName);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify Forecast page for different newly created demand driver template for V2")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, enabled = false)
    public void verifyForecastPageForDifferentNewlyCreatedDemandDriverTemplateForDGV2AsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            String templateName1 = "testVisibility1_V2";
            String templateName2= "testVisibility2_V2";
            String locationName1 = "Boston_V2";
            String locationName2 = "Cleveland_V2";
            HashMap<String, String> invisibleInfo_location1 = new HashMap<String, String>(){
                {
                    put("Name", "Items:EDW:Enrollments");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Enrollments");
                    put("Show in App", "No");
                    put("Order", "1");
                    put("Forecast Source", "Imported");
                    put("Input Stream", "Items:EDW:Enrollments");
                }
            };
            HashMap<String, String> visibleInfo_location1 = new HashMap<String, String>(){
                {
                    put("Name", "Items:EDW:Verifications");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Verifications");
                    put("Show in App", "Yes");
                    put("Order", "2");
                    put("Forecast Source", "Legion ML");
                    put("Input Stream", "Items:EDW:Verifications");
                }
            };
            HashMap<String, String> invisibleInfo_location2 = new HashMap<String, String>(){
                {
                    put("Name", "Items:EDW:Verifications");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Verifications");
                    put("Show in App", "No");
                    put("Order", "1");
                    put("Forecast Source", "Imported");
                    put("Input Stream", "Items:EDW:Verifications");
                }
            };
            HashMap<String, String> visibleInfo_location2 = new HashMap<String, String>(){
                {
                    put("Name", "Items:EDW:Enrollments");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Enrollments");
                    put("Show in App", "Yes");
                    put("Order", "2");
                    put("Forecast Source", "Legion ML");
                    put("Input Stream", "Items:EDW:Enrollments");
                }
            };
            //Turn on DynamicGroupV2 toggle
            ToggleAPI.enableToggle(Toggles.DynamicGroupV2.getValue(), "stoneman@legion.co", "admin11.a");
            //Go to Demand Driver template
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);

            //Go to Templates tab
            settingsAndAssociationPage.goToTemplateListOrSettings("Templates");
            //Add first demand driver template
            configurationPage.createNewTemplate(templateName1);
            configurationPage.searchTemplate("Default");
            configurationPage.archivePublishedOrDeleteDraftTemplate("Default", "Archive");
            configurationPage.searchTemplate(templateName1);
            configurationPage.clickOnTemplateName(templateName1);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();

            //Add one driver, set up visibility
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(invisibleInfo_location1);
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(visibleInfo_location1);
            //Add association and save
            configurationPage.createDynamicGroup(templateName1, "Location Name", locationName1);
            configurationPage.selectOneDynamicGroup(templateName1);
            //Could publish normally
            configurationPage.clickOnTemplateDetailTab();
            configurationPage.publishNowTemplate();

            //Add second demand driver template
            configurationPage.createNewTemplate(templateName2);
            configurationPage.clickOnTemplateName(templateName2);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();

            //Add one driver, set up visibility
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(invisibleInfo_location2);
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(visibleInfo_location2);
            //Add association and save
            configurationPage.createDynamicGroup(templateName2, "Location Name", locationName2);
            configurationPage.selectOneDynamicGroup(templateName2);
            //Could publish normally
            configurationPage.clickOnTemplateDetailTab();
            configurationPage.publishNowTemplate();

            //Verify channel/category visibility in Forecast page
            refreshCache("Template");
            switchToNewWindow();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(locationName1);
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            ForecastPage forecastPage = pageFactory.createForecastPage();
            SalesForecastPage salesForecastPage = pageFactory.createSalesForecastPage();
            scheduleCommonPage.goToSchedulePage();
            forecastPage.clickForecast();
            salesForecastPage.navigateToSalesForecastTab();
            //Check channel/category visibility for location1
            SimpleUtils.assertOnFail("The channel EDW should show up in forecast page!",
                    salesForecastPage.verifyChannelOrCategoryExistInForecastPage("channel", visibleInfo_location1.get("Channel")), false);
            SimpleUtils.assertOnFail("The category Enrollments should not show up in forecast page!",
                    !salesForecastPage.verifyChannelOrCategoryExistInForecastPage("demand",  invisibleInfo_location1.get("Category")), false);
            SimpleUtils.assertOnFail("The category Verifications should show up in forecast page!",
                    salesForecastPage.verifyChannelOrCategoryExistInForecastPage("demand", visibleInfo_location1.get("Category")), false);

            //Check channel/category visibility for location2
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(locationName1);
            forecastPage.clickForecast();
            salesForecastPage.navigateToSalesForecastTab();
            SimpleUtils.assertOnFail("The channel EDW should show up in forecast page!",
                    salesForecastPage.verifyChannelOrCategoryExistInForecastPage("channel", visibleInfo_location2.get("Channel")), false);
            SimpleUtils.assertOnFail("The category Enrollments should not show up in forecast page!",
                    !salesForecastPage.verifyChannelOrCategoryExistInForecastPage("demand",  invisibleInfo_location2.get("Category")), false);
            SimpleUtils.assertOnFail("The category Verifications should show up in forecast page!",
                    salesForecastPage.verifyChannelOrCategoryExistInForecastPage("demand", visibleInfo_location2.get("Category")), false);
            //Remove the associated locations and templates
            configurationPage.archiveOrDeleteAllTemplates();
            //Turn off DynamicGroupV2 toggle
            ToggleAPI.disableToggle(Toggles.DynamicGroupV2.getValue(), "stoneman@legion.co", "admin11.a");
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify Forecast page for newly created demand drivers with new Channel/Category for V2")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, enabled = false)
    public void verifyForecastPageForDemandDriversTemplateWithNewChannelAndCategoryAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            String templateName = "testVisibility_NewChannelAndCategory";
            String channelName = "Channel_Gate";
            String categoryName = "Category_Coffee";
            String locationName = "Boston";
            HashMap<String, String> invisibleInfo = new HashMap<String, String>(){
                {
                    put("Name", "Items:EDW:Category_Coffee");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", categoryName);
                    put("Show in App", "No");
                    put("Order", "1");
                    put("Forecast Source", "Imported");
                    put("Input Stream", "Items:EDW:Enrollments");
                }
            };
            HashMap<String, String> visibleInfo = new HashMap<String, String>(){
                {
                    put("Name", "Items:Channel_Gate:Verifications");
                    put("Type", "Items");
                    put("Channel", channelName);
                    put("Category", "Verifications");
                    put("Show in App", "Yes");
                    put("Order", "2");
                    put("Forecast Source", "Imported");
                    put("Input Stream", "Items:EDW:Enrollments");
                }
            };
            //Turn on DynamicGroupV2 toggle
            ToggleAPI.enableToggle(Toggles.DynamicGroupV2.getValue(), "stoneman@legion.co", "admin11.a");
            //Go to Demand Driver template
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            //Go to Settings tab
            settingsAndAssociationPage.goToTemplateListOrSettings("Settings");
            //Add new category in settings.
            settingsAndAssociationPage.createNewChannelOrCategory("category", categoryName, "This is a test for new Category visibility!");
            settingsAndAssociationPage.createNewChannelOrCategory("channel", channelName, "This is a test for new Channel visibility!");
            //Go to Templates tab
            settingsAndAssociationPage.goToTemplateListOrSettings("Templates");
            //Add first demand driver template
            configurationPage.createNewTemplate(templateName);
            configurationPage.clickOnTemplateName(templateName);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();

            //Add two drivers, set up visibility
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(invisibleInfo);
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(visibleInfo);
            //Add association and save
            configurationPage.createDynamicGroup(templateName, "Location Name", locationName);
            configurationPage.selectOneDynamicGroup(templateName);

            //Could publish normally
            configurationPage.clickOnTemplateDetailTab();
            configurationPage.publishNowTemplate();

            //Verify channel/category visibility in Forecast page
            refreshCache("Template");
            switchToNewWindow();
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(locationName);
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            ForecastPage forecastPage = pageFactory.createForecastPage();
            SalesForecastPage salesForecastPage = pageFactory.createSalesForecastPage();
            scheduleCommonPage.goToSchedulePage();
            forecastPage.clickForecast();
            salesForecastPage.navigateToSalesForecastTab();
            //Check channel/category visibility for location
            SimpleUtils.assertOnFail("The channel EDW should not show up in forecast page!",
                    !salesForecastPage.verifyChannelOrCategoryExistInForecastPage("channel", invisibleInfo.get("Channel")), false);
            SimpleUtils.assertOnFail("The category Category_Coffee should not show up in forecast page!",
                    !salesForecastPage.verifyChannelOrCategoryExistInForecastPage("channel", invisibleInfo.get("Category")), false);
            SimpleUtils.assertOnFail("The channel Channel_Gate should show up in forecast page!",
                    salesForecastPage.verifyChannelOrCategoryExistInForecastPage("demand",  visibleInfo.get("Channel")), false);
            SimpleUtils.assertOnFail("The category Verifications should show up in forecast page!",
                    salesForecastPage.verifyChannelOrCategoryExistInForecastPage("demand", visibleInfo.get("Category")), false);

            //Turn off DynamicGroupV2 toggle
            ToggleAPI.disableToggle(Toggles.DynamicGroupV2.getValue(), "stoneman@legion.co", "admin11.a");
            switchToNewWindow();
            //Remove the associated locations and templates
            configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            settingsAndAssociationPage.goToAssociationTabOnTemplateDetailsPage();
            configurationPage.deleteOneDynamicGroup(templateName);
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.setLeaveThisPageButton();
            configurationPage.archiveOrDeleteTemplate(templateName);

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Check UI for Distributed demand driver page.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyForDistributedDemandDriverUIAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            String templateName = "testDistributed";
            String derivedType = "Distributed";

            //Turn on EnableTahoeStorage toggle
            ToggleAPI.enableToggle(Toggles.EnableTahoeStorage.getValue(), "stoneman@legion.co", "admin11.a");
            refreshPage();
            //Go to Demand Driver template
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            //Go to Settings tab
            settingsAndAssociationPage.goToTemplateListOrSettings("Template");
            configurationPage.createNewTemplate(templateName);
            configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.verifyForDerivedDemandDriverUI(derivedType, null);
            ToggleAPI.disableToggle(Toggles.EnableTahoeStorage.getValue(), "stoneman@legion.co", "admin11.a");
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Check UI for Remote demand driver page.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyForRemoteDemandDriverUIAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            String templateName = "testRemote";
            String derivedType = "Remote";
            String remoteType = "Remote Location";
            String parentType = "Parent Location";

            //Turn on EnableTahoeStorage toggle
            ToggleAPI.enableToggle(Toggles.EnableTahoeStorage.getValue(), "stoneman@legion.co", "admin11.a");
            refreshPage();
            //Go to Demand Driver template
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            //Go to Settings tab
            settingsAndAssociationPage.goToTemplateListOrSettings("Template");
            configurationPage.createNewTemplate(templateName);
            configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.verifyForDerivedDemandDriverUI(derivedType, remoteType);
            configurationPage.clickOnCancelButton();
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.verifyForDerivedDemandDriverUI(derivedType, parentType);
            ToggleAPI.disableToggle(Toggles.EnableTahoeStorage.getValue(), "stoneman@legion.co", "admin11.a");
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Check UI for Aggregated demand driver page.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyForAggregatedDemandDriverUIAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            String templateName = "testAggregated";
            String derivedType = "Aggregated";

            //Turn on EnableTahoeStorage toggle
            ToggleAPI.enableToggle(Toggles.EnableTahoeStorage.getValue(), "stoneman@legion.co", "admin11.a");
            refreshPage();
            //Go to Demand Driver template
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            //Go to Template tab
            settingsAndAssociationPage.goToTemplateListOrSettings("Template");
            configurationPage.createNewTemplate(templateName);
            configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.verifyForDerivedDemandDriverUI(derivedType, null);
            ToggleAPI.disableToggle(Toggles.EnableTahoeStorage.getValue(), "stoneman@legion.co", "admin11.a");
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
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Basic staffing rules page verify")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyStaffingRulePageAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

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
            configurationPage.checkTheEntryOfAddBasicStaffingRule();
            configurationPage.verifyStaffingRulePageShowWell();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Nancy")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify workforce sharing in dynamic group in schedule collabration")
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
            SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
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
            configurationPage.verifyWorkforceSharingGroup();

            configurationPage.clickOnAssociationTabOnTemplateDetailsPage();

            OpsPortalLocationsPage opsPortalLocationsPage = new OpsPortalLocationsPage();
            opsPortalLocationsPage.eidtExistingDGP();
            opsPortalLocationsPage.searchWFSDynamicGroup(searchText);
            opsPortalLocationsPage.removedSearchedWFSDG();

            opsPortalLocationsPage.addWorkforceSharingDGWithMutiplyCriteria();
            opsPortalLocationsPage.removedSearchedWFSDG();

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

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Basic Staffing Rule Fields Verification")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void basicStaffingRuleFieldsVerificationAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Fiona Auto Using";
            String workRole = "AutoUsing2";
            List<String> workRoleList=new ArrayList<>();
            List<String> startTimeEventOptionsList = new ArrayList<>();
            List<String> TimeEventOptionsList = new ArrayList<>(Arrays.asList("All Hours", "Opening Business Hours", "Closing Business Hours", "Opening Operating Hours", "Closing Operating Hours", "Incoming Hours",
                    "Outgoing Hours", "Specified Hours", "Peak Hours", "non Peak Hours","DP1-forAuto","DP2-forAuto"));
            boolean flag = true;
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            configurationPage.checkTheEntryOfAddBasicStaffingRule();
            configurationPage.verifyStaffingRulePageShowWell();
            configurationPage.verifyConditionAndNumberFiledCanShowWell();
            configurationPage.verifyNumberInputFieldOfBasicStaffingRule();
            workRoleList = configurationPage.verifyWorkRoleListOfBasicStaffingRule();
            if(workRoleList.size()==2){
                if(workRoleList.get(0).equals(workRole) && workRoleList.get(1).equals("Any")){
                    SimpleUtils.pass("Work role option list in adding new basic staffing rule is correct");
                }else {
                    SimpleUtils.fail("Work role option list in adding new basic staffing rule is NOT correct",false);
                }
            }
            configurationPage.verifyUnitOptionsListOfBasicStaffingRule();
            configurationPage.verifyStartEndOffsetMinutesShowingByDefault();
            configurationPage.verifyStartEndEventPointOptionsList();
            startTimeEventOptionsList = configurationPage.verifyStartEndTimeEventOptionsList();
            for(String startTimeEventOptions:startTimeEventOptionsList){
                for(String TimeEventOptions:TimeEventOptionsList){
                    if(startTimeEventOptions.equalsIgnoreCase(TimeEventOptions)){
                        flag=true;
                        SimpleUtils.pass(startTimeEventOptions + " is showing in Time Event Options List");
                        break;
                    }else {
                        flag=false;
                    }
                }
            }
            if(flag){
                SimpleUtils.pass("Time Event Options List is correct!");
            }else {
                SimpleUtils.fail("Time Event Options List is NOT correct!",false);
            }
            configurationPage.verifyDaysListShowWell();
            configurationPage.verifyDefaultValueAndSelectDaysForBasicStaffingRule("Tue");
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Basic Staffing Rule special fields validation")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void basicStaffingRuleSpecialFieldsVerificationAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        try {
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Fiona Auto Using";
            String workRole = "AutoUsing2";
            String start = "8:30 AM";
            String end = "9:30 PM";
            String dayParts1 = "DP1-forAuto";
            String dayParts2 = "DP2-forAuto";
            String startEventPoint = "before";
            String endEventPoint = "after";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            configurationPage.checkTheEntryOfAddBasicStaffingRule();
            configurationPage.verifyStaffingRulePageShowWell();
            configurationPage.setSpecifiedHours(start,end);
            configurationPage.verifyBeforeAndAfterDayPartsShouldBeSameWhenSetAsDayParts(dayParts1,dayParts2,startEventPoint,endEventPoint);
        }catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Basic Staffing Rule Cross and Check button")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void basicStaffingRuleCrossCheckVerificationAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        try {
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
            configurationPage.checkTheEntryOfAddBasicStaffingRule();
            configurationPage.verifyStaffingRulePageShowWell();
            configurationPage.verifyCrossAndCheckButtonOfBasicStaffingRule();
        }catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Basic Staffing Rule badge section")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void basicStaffingRuleBadgeVerificationAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        try {
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Fiona Auto Using";
            String workRole = "AutoUsing2";
            String hasBadgeOrNot = "yes";
            String badgeName ="AutoUsing";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            configurationPage.checkTheEntryOfAddBasicStaffingRule();
            configurationPage.verifyStaffingRulePageShowWell();
            configurationPage.defaultSelectedBadgeOption();
            configurationPage.selectBadgesOfBasicStaffingRule(hasBadgeOrNot,badgeName);
        }catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Nancy")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Audit Log")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void auditLogVerificationAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();

            configurationPage.goToConfigurationPage();

            configurationPage.goToItemInConfiguration("Time & Attendance");
            configurationPage.searchTemplate("AuditLog");
            configurationPage.clickOnTemplateName("AuditLog");
            configurationPage.verifyHistoryButtonDisplay();
            configurationPage.verifyHistoryButtonIsClickable();
            configurationPage.verifyCloseIconNotDisplayDefault();
            configurationPage.clickHistoryAndClose();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();

//            configurationPage.goToItemInConfiguration("Demand Drivers");
//            configurationPage.searchTemplate("AuditLog");
//            configurationPage.clickOnTemplateName("AuditLog");
//            configurationPage.verifyHistoryButtonDisplay();
//            configurationPage.verifyHistoryButtonIsClickable();
//            configurationPage.verifyCloseIconNotDisplayDefault();
//            configurationPage.clickHistoryAndClose();
//            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
//            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();

            configurationPage.goToItemInConfiguration("Operating Hours");
            configurationPage.searchTemplate("AuditLog");
            configurationPage.clickOnTemplateName("AuditLog");
            configurationPage.verifyHistoryButtonDisplay();
            configurationPage.verifyHistoryButtonIsClickable();
            configurationPage.verifyCloseIconNotDisplayDefault();
            configurationPage.clickHistoryAndClose();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();

            configurationPage.goToItemInConfiguration("Scheduling Policies");
            configurationPage.searchTemplate("AuditLog");
            configurationPage.clickOnTemplateName("AuditLog");
            configurationPage.verifyHistoryButtonDisplay();
            configurationPage.verifyHistoryButtonIsClickable();
            configurationPage.verifyCloseIconNotDisplayDefault();
            configurationPage.clickHistoryAndClose();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();

            configurationPage.goToItemInConfiguration("Schedule Collaboration");
            configurationPage.searchTemplate("AuditLog");
            configurationPage.clickOnTemplateName("AuditLog");
            configurationPage.verifyHistoryButtonDisplay();
            configurationPage.verifyHistoryButtonIsClickable();
            configurationPage.verifyCloseIconNotDisplayDefault();
            configurationPage.clickHistoryAndClose();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();

            configurationPage.goToItemInConfiguration("Compliance");
            configurationPage.searchTemplate("AuditLog");
            configurationPage.clickOnTemplateName("AuditLog");
            configurationPage.verifyHistoryButtonDisplay();
            configurationPage.verifyHistoryButtonIsClickable();
            configurationPage.verifyCloseIconNotDisplayDefault();
            configurationPage.clickHistoryAndClose();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();

            configurationPage.goToItemInConfiguration("Scheduling Rules");
            configurationPage.searchTemplate("AuditLog");
            configurationPage.clickOnTemplateName("AuditLog");
            configurationPage.verifyHistoryButtonDisplay();
            configurationPage.verifyHistoryButtonIsClickable();
            configurationPage.verifyCloseIconNotDisplayDefault();
            configurationPage.clickHistoryAndClose();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();

            configurationPage.goToItemInConfiguration("Minors Rules");
            configurationPage.searchTemplate("AuditLog");
            configurationPage.clickOnTemplateName("AuditLog");
            configurationPage.verifyHistoryButtonDisplay();
            configurationPage.verifyHistoryButtonIsClickable();
            configurationPage.verifyCloseIconNotDisplayDefault();
            configurationPage.clickHistoryAndClose();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();

            configurationPage.goToItemInConfiguration("Additional Pay Rules");
            configurationPage.searchTemplate("AuditLog");
            configurationPage.clickOnTemplateName("AuditLog");
            configurationPage.verifyHistoryButtonDisplay();
            configurationPage.verifyHistoryButtonIsClickable();
            configurationPage.verifyCloseIconNotDisplayDefault();
            configurationPage.clickHistoryAndClose();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();


            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();

            configurationPage.verifyHistoryButtonNotDisplay();
            locationsPage.goBack();

            locationsPage.goToUpperFieldsPage();
            configurationPage.verifyHistoryButtonNotDisplay();
            locationsPage.goBack();

            locationsPage.getEnterpriseLogoAndDefaultLocationInfo();
            configurationPage.verifyHistoryButtonNotDisplay();
            locationsPage.goBack();

            locationsPage.goToGlobalConfigurationInLocations();
            configurationPage.verifyHistoryButtonNotDisplay();
            locationsPage.goBack();

            locationsPage.goToDynamicGroup();
            configurationPage.verifyHistoryButtonNotDisplay();
            locationsPage.goBack();
			}catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Basic Staffing Rule can show well in rule list")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void basicStaffingRuleCanShowWellInListAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        try {
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Fiona Auto Using";
            String workRole = "AutoUsing2";
            String startTimeEvent ="Opening Operating Hours";
            String endTimeEvent ="Closing Operating Hours";
            String startEventPoint="after";
            String endEventPoint="before";
            String workRoleName="AutoUsing2";
            String unit="Shifts";
            String condition ="A Minimum";
            List<String>days= new ArrayList<>(Arrays.asList("Mon","Thu"));
            String number = "2";
            String startOffset ="30";
            String endOffset = "45";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            configurationPage.checkTheEntryOfAddBasicStaffingRule();
            configurationPage.verifyStaffingRulePageShowWell();
            configurationPage.createBasicStaffingRule(startTimeEvent,endTimeEvent,startEventPoint,endEventPoint,
                    workRoleName,unit,condition,days,number,
                    startOffset,endOffset);
            configurationPage.verifyBasicStaffingRuleIsCorrectInRuleList(startTimeEvent,endTimeEvent,startEventPoint,endEventPoint,
                    workRoleName,unit,condition,days,number,
                    startOffset,endOffset);

        }catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Skill Coverage")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void skillCoverageOfBasicStaffingRuleAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        try {
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "Fiona Auto Using";
            String workRole1 = "AutoUsing2";
            String workRole2="ForAutomation";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.verifySkillCoverageBasicStaffingRule(workRole1,workRole2);
        }catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}