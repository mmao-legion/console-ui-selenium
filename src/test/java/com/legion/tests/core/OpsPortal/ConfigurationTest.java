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
        ToggleAPI.disableToggle(Toggles.DynamicGroupV2.getValue(), "jane.meng+006@legion.co", "P@ssword123");
        ToggleAPI.enableToggle(Toggles.EnableDemandDriverTemplate.getValue(), "jane.meng+006@legion.co", "P@ssword123");
        ToggleAPI.enableToggle(Toggles.MixedModeDemandDriverSwitch.getValue(), "jane.meng+006@legion.co", "P@ssword123");
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
    public void ceateMultipleHistortForOHTempInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Operating Hours";
            String mode = "edit";
            //OH templaye  3153 on RC OPauto
            String templateName = "3153";
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            int now=configurationPage.historyRecordLimitCheck(templateName);
            configurationPage.closeTemplateHistoryPanel();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            if (now<101) {
                SimpleUtils.pass("History records displayed successfully!");
                //create another 100 records
                for (int i = 0; i < 103-now; i++) {
                    configurationPage.searchTemplate(templateName);
                    configurationPage.clickOnTemplateName(templateName);
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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, enabled = false)
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
            locationSelectorPage.changeUpperFieldsByMagnifyGlassIcon("NancyTimeOff");

            TeamPage teamPage = pageFactory.createConsoleTeamPage();
            teamPage.goToTeam();
            teamPage.searchAndSelectTeamMemberByName("Nancy TimeOff01");

            TimeOffPage timeOffPage = new TimeOffPage();
            timeOffPage.switchToTimeOffTab();
            OpsCommonComponents commonComponents = new OpsCommonComponents();
            timeOffPage.createTimeOff("Annual Leave1", false, 10, 10);
            String Month = timeOffPage.getMonth();
            commonComponents.okToActionInModal(true);
            timeOffPage.cancelTimeOffRequest();

            teamPage.goToTeam();
            teamPage.searchAndSelectTeamMemberByName("Nancy TimeOff02");
            timeOffPage.switchToTimeOffTab();

            timeOffPage.createTimeOff("Annual Leave1", false, 10, 10);
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
            String locationToAssociate = "Auto_ForDemandDriver";
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
            if(configurationPage.searchTemplate(templateName)){
                configurationPage.clickOnTemplateName(templateName);
                configurationPage.clickOnEditButtonOnTemplateDetailsPage();
                settingsAndAssociationPage.goToAssociationTabOnTemplateDetailsPage();
                configurationPage.deleteOneDynamicGroup(templateName);
                configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
                configurationPage.setLeaveThisPageButton();
                configurationPage.archiveOrDeleteTemplate(templateName);
            }
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
            configurationPage.createDynamicGroup(templateName, "Location Name", locationToAssociate);
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
            Calendar calendar = Calendar.getInstance();
            dfs.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
            Month month = LocalDate.now().getMonth();
            String shortMonth = month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " ";
            String currentDate = (shortMonth + dfs.format(calendar.getTime())).replace(" 0", " ");

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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, priority = 0)
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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, priority = 1)
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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, priority = 2)
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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, priority = 7)
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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, priority = 6)
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
            ToggleAPI.enableToggle(Toggles.DynamicGroupV2.getValue(), "jane.meng+006@legion.co", "admin11.a");
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
            ToggleAPI.disableToggle(Toggles.DynamicGroupV2.getValue(), "jane.meng+006@legion.co", "admin11.a");
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
            ToggleAPI.enableToggle(Toggles.DynamicGroupV2.getValue(), "jane.meng+006@legion.co", "admin11.a");
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
            ToggleAPI.disableToggle(Toggles.DynamicGroupV2.getValue(), "jane.meng+006@legion.co", "admin11.a");
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
            ToggleAPI.enableToggle(Toggles.EnableTahoeStorage.getValue(), "jane.meng+006@legion.co", "admin11.a");
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
            ToggleAPI.disableToggle(Toggles.EnableTahoeStorage.getValue(), "jane.meng+006@legion.co", "admin11.a");
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
            ToggleAPI.enableToggle(Toggles.EnableTahoeStorage.getValue(), "jane.meng+006@legion.co", "admin11.a");
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
            ToggleAPI.disableToggle(Toggles.EnableTahoeStorage.getValue(), "jane.meng+006@legion.co", "admin11.a");
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
            ToggleAPI.enableToggle(Toggles.EnableTahoeStorage.getValue(), "jane.meng+006@legion.co", "admin11.a");
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
            ToggleAPI.disableToggle(Toggles.EnableTahoeStorage.getValue(), "jane.meng+006@legion.co", "admin11.a");
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate Creation&Modification for Remote Demand Driver.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreationAndEditForRemoteDemandDriverAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            SimpleDateFormat dfs = new SimpleDateFormat("MMddHHmm ");
            String currentTime =  dfs.format(new Date()).trim();
            String templateName = "testCreation_Remote" + currentTime;
            String warningMsg = "can not be set as remote location because it is associated with the same demand driver template";
            HashMap<String, String> parentLocationDriver = new HashMap<String, String>(){
                {
                    put("Name", "ParentLocationDriver");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Enrollments");
                    put("Show in App", "Yes");
                    put("Order", "1");
                    put("Forecast Source", "Remote");
                    put("Specify Location", "Parent Location");
                    put("Parent Level", "2");
                }
            };
            HashMap<String, String> remoteLocationDriver_inAssociation = new HashMap<String, String>(){
                {
                    put("Name", "RemoteLocationDriver");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Verifications");
                    put("Show in App", "Yes");
                    put("Order", "1");
                    put("Forecast Source", "Remote");
                    put("Specify Location", "Remote Location");
                    put("Choose Remote Location", "Austin");
                }
            };
            HashMap<String, String> remoteLocationDriver = new HashMap<String, String>(){
                {
                    put("Name", "RemoteLocationDriver");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Verifications");
                    put("Show in App", "Yes");
                    put("Order", "1");
                    put("Forecast Source", "Remote");
                    put("Specify Location", "Remote Location");
                    put("Choose Remote Location", "Boston");
                }
            };

            //Turn on EnableTahoeStorage toggle
            ToggleAPI.enableToggle(Toggles.EnableTahoeStorage.getValue(), "jane.meng+006@legion.co", "admin11.a");
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
            //Add a Remote:ParentLocation demand driver
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(parentLocationDriver);
            //Add a Remote:RemoteLocation demand driver
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(remoteLocationDriver_inAssociation);
            //Add association
            configurationPage.selectOneDynamicGroup("testDerived_NotDelete");
            //Should not be able to publish
            configurationPage.clickOnTemplateDetailTab();
            configurationPage.chooseSaveOrPublishBtnAndClickOnTheBtn("publish now");
            SimpleUtils.assertOnFail("Please check if there's a warning message and the content!", configurationPage.verifyWarningForDemandDriver(warningMsg), false);
            SimpleUtils.assertOnFail("Can't find the driver you search!", configurationPage.searchDriverInTemplateDetailsPage(remoteLocationDriver_inAssociation.get("Name")), false);
            //Edit the remote location driver and publish once again
            configurationPage.clickAddOrEditForDriver("Edit");
            configurationPage.addOrEditDemandDriverInTemplate(remoteLocationDriver);
            configurationPage.clickOnTemplateDetailTab();
            configurationPage.publishNowTemplate();
            configurationPage.archiveOrDeleteTemplate(templateName);
            ToggleAPI.disableToggle(Toggles.EnableTahoeStorage.getValue(), "jane.meng+006@legion.co", "admin11.a");
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate Creation&Modification for Distributed Demand Driver.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreationAndEditForDistributedDemandDriverAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
//        try {
            String templateType = "Demand Drivers";
            SimpleDateFormat dfs = new SimpleDateFormat("MMddHHmm");
            String currentTime =  dfs.format(new Date()).trim();
            String templateName = "testCreation_Distributed" + currentTime;
            HashMap<String, String> distributedDriver = new HashMap<String, String>(){
                {
                    put("Name", "DistributedDriver");
                    put("Type", "Items");
                    put("Channel", "Channel01");
                    put("Category", "Enrollments");
                    put("Show in App", "No");
                    put("Order", "1");
                    put("Forecast Source", "Distributed");
                    put("Source Demand Driver", "LegionMLDriver");
                    put("Distribution of Demand Driver", "ImportedDriver");
                }
            };
            HashMap<String, String> legionMLDriver = new HashMap<String, String>(){
                {
                    put("Name", "LegionMLDriver");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Enrollments");
                    put("Show in App", "No");
                    put("Order", "1");
                    put("Forecast Source", "Legion ML");
                    put("Input Stream", "Items:EDW:Enrollments");
                }
            };
            HashMap<String, String> importedDriver = new HashMap<String, String>(){
                {
                    put("Name", "ImportedDriver");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Verifications");
                    put("Show in App", "Yes");
                    put("Order", "2");
                    put("Forecast Source", "Imported");
                    put("Input Stream", "Items:EDW:Verifications");
                }
            };
            HashMap<String, String> distributedChangedToremoteDriver = new HashMap<String, String>(){
                {
                    put("Name", "RemoteLocationDriver");
                    put("Type", "Items");
                    put("Channel", "Channel01");
                    put("Category", "Verifications");
                    put("Show in App", "Yes");
                    put("Order", "3");
                    put("Forecast Source", "Remote");
                    put("Specify Location", "Remote Location");
                    put("Choose Remote Location", "Boston");
                }
            };
            //Turn on EnableTahoeStorage toggle
            ToggleAPI.enableToggle(Toggles.EnableTahoeStorage.getValue(), "jane.meng+006@legion.co", "admin11.a");
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
            //Add a distributed demand driver, can't save, cancel
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(distributedDriver);
            //Add only one basic driver first
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(legionMLDriver);
            //Add distributed driver, can't save, cancel
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(distributedDriver);
            //Add the second basic driver then the distributed driver
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(importedDriver);
            //Add distributed driver, and publish
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(distributedDriver);
            SimpleUtils.assertOnFail("Can't find the distributed driver you search!", configurationPage.searchDriverInTemplateDetailsPage(distributedDriver.get("Name")), false);
            configurationPage.selectOneDynamicGroup("testDerived_NotDelete");
            configurationPage.clickOnTemplateDetailTab();
            configurationPage.publishNowTemplate();

            //distributed changed to remote driver
            configurationPage.clickOnSpecifyTemplateName(templateName, "Edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.searchDriverInTemplateDetailsPage(distributedDriver.get("Name"));
            configurationPage.clickAddOrEditForDriver("Edit");
            configurationPage.addOrEditDemandDriverInTemplate(distributedChangedToremoteDriver);

            SimpleUtils.assertOnFail("Can't find the remote driver you search!", configurationPage.searchDriverInTemplateDetailsPage(distributedChangedToremoteDriver.get("Name")), false);
            configurationPage.searchDriverInTemplateDetailsPage("");
            //Publish again after modify the driver
            configurationPage.clickOnTemplateDetailTab();
            configurationPage.publishNowTemplate();
            configurationPage.archiveOrDeleteTemplate(templateName);
            ToggleAPI.disableToggle(Toggles.EnableTahoeStorage.getValue(), "jane.meng+006@legion.co", "admin11.a");
//        } catch (Exception e) {
//            SimpleUtils.fail(e.getMessage(), false);
//        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate Creation&Modification for Aggregated Demand Driver.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreationAndEditForAggregatedDemandDriverAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            SimpleDateFormat dfs = new SimpleDateFormat("MMddHHmm");
            String currentTime =  dfs.format(new Date()).trim();
            String templateName = "testCreation_Aggregated" + currentTime;
            HashMap<String, String> aggregatedDriver = new HashMap<String, String>(){
                {
                    put("Name", "AggregatedDriver");
                    put("Type", "Items");
                    put("Channel", "Channel01");
                    put("Category", "Enrollments");
                    put("Show in App", "No");
                    put("Order", "1");
                    put("Forecast Source", "Aggregated");
                    put("Options", "LegionMLDriver,-1.78;ImportedDriver,3.21");
                }
            };
            HashMap<String, String> legionMLDriver = new HashMap<String, String>(){
                {
                    put("Name", "LegionMLDriver");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Enrollments");
                    put("Show in App", "No");
                    put("Order", "2");
                    put("Forecast Source", "Legion ML");
                    put("Input Stream", "Items:EDW:Enrollments");
                }
            };
            HashMap<String, String> importedDriver = new HashMap<String, String>(){
                {
                    put("Name", "ImportedDriver");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Verifications");
                    put("Show in App", "Yes");
                    put("Order", "3");
                    put("Forecast Source", "Imported");
                    put("Input Stream", "Items:EDW:Verifications");
                }
            };
            HashMap<String, String> aggregatedChangedToremoteDriver = new HashMap<String, String>(){
                {
                    put("Name", "DistributedDriver");
                    put("Type", "Items");
                    put("Channel", "Channel01");
                    put("Category", "Verifications");
                    put("Show in App", "Yes");
                    put("Order", "4");
                    put("Forecast Source", "Distributed");
                    put("Source Demand Driver", "LegionMLDriver");
                    put("Distribution of Demand Driver", "ImportedDriver");
                }
            };

            //Turn on EnableTahoeStorage toggle
            ToggleAPI.enableToggle(Toggles.EnableTahoeStorage.getValue(), "jane.meng+006@legion.co", "admin11.a");
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
            //Add a aggregated demand driver, can't save, cancel
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(aggregatedDriver);
            //Add only one basic driver first
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(legionMLDriver);
            //Add aggregated driver, can't save, cancel
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(aggregatedDriver);
            //Add the second basic driver then the aggregated driver
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(importedDriver);
            //Add aggregated driver, and publish
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(aggregatedDriver);
            SimpleUtils.assertOnFail("Can't find the aggregated driver you search!", configurationPage.searchDriverInTemplateDetailsPage(aggregatedDriver.get("Name")), false);
            configurationPage.selectOneDynamicGroup("testDerived_NotDelete");
            configurationPage.clickOnTemplateDetailTab();
            configurationPage.publishNowTemplate();

            //aggregated changed to aggregated driver
            configurationPage.clickOnSpecifyTemplateName(templateName, "Edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.searchDriverInTemplateDetailsPage(aggregatedDriver.get("Name"));
            configurationPage.clickAddOrEditForDriver("Edit");
            configurationPage.addOrEditDemandDriverInTemplate(aggregatedChangedToremoteDriver);

            SimpleUtils.assertOnFail("Can't find the aggregated driver you search!", configurationPage.searchDriverInTemplateDetailsPage(aggregatedChangedToremoteDriver.get("Name")), false);
            configurationPage.searchDriverInTemplateDetailsPage("");
            //Publish again after modify the driver
            configurationPage.clickOnTemplateDetailTab();
            configurationPage.publishNowTemplate();
            configurationPage.archiveOrDeleteTemplate(templateName);
            ToggleAPI.disableToggle(Toggles.EnableTahoeStorage.getValue(), "jane.meng+006@legion.co", "admin11.a");
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate view for Aggregated Demand Driver.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAggregatedDemandDriverInReadOnlyModeAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            SimpleDateFormat dfs = new SimpleDateFormat("MMddHHmm");
            String currentTime =  dfs.format(new Date()).trim();
            String templateName = "testView_Aggregated" + currentTime;
            HashMap<String, String> aggregatedDriver = new HashMap<String, String>(){
                {
                    put("Name", "AggregatedDriver");
                    put("Type", "Items");
                    put("Channel", "Channel01");
                    put("Category", "Enrollments");
                    put("Show in App", "No");
                    put("Order", "1");
                    put("Forecast Source", "Aggregated");
                    put("Options", "LegionMLDriver,-1.78;ImportedDriver,3.21");
                }
            };
            HashMap<String, String> legionMLDriver = new HashMap<String, String>(){
                {
                    put("Name", "LegionMLDriver");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Enrollments");
                    put("Show in App", "No");
                    put("Order", "2");
                    put("Forecast Source", "Legion ML");
                    put("Input Stream", "Items:EDW:Enrollments");
                }
            };
            HashMap<String, String> importedDriver = new HashMap<String, String>(){
                {
                    put("Name", "ImportedDriver");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Verifications");
                    put("Show in App", "Yes");
                    put("Order", "3");
                    put("Forecast Source", "Imported");
                    put("Input Stream", "Items:EDW:Verifications");
                }
            };
            //Turn on EnableTahoeStorage toggle
            ToggleAPI.enableToggle(Toggles.EnableTahoeStorage.getValue(), "jane.meng+006@legion.co", "admin11.a");
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
            //Add two basic drivers first
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(legionMLDriver);
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(importedDriver);
            //Add aggregated driver, and publish
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(aggregatedDriver);
            configurationPage.selectOneDynamicGroup("testDerived_NotDelete");
            configurationPage.clickOnTemplateDetailTab();
            configurationPage.publishNowTemplate();

            //check in view mode
            configurationPage.clickOnSpecifyTemplateName(templateName, "Edit");
            configurationPage.searchDriverInTemplateDetailsPage(aggregatedDriver.get("Name"));
            SimpleUtils.assertOnFail("Verify aggregated driver page failed in read only mode!", configurationPage.verifyDriverInViewMode(aggregatedDriver), false);
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.archiveOrDeleteTemplate(templateName);
            ToggleAPI.disableToggle(Toggles.EnableTahoeStorage.getValue(), "jane.meng+006@legion.co", "admin11.a");
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate view for Distributed Demand Driver.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDistributedDemandDriverInReadOnlyModeAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            SimpleDateFormat dfs = new SimpleDateFormat("MMddHHmm");
            String currentTime =  dfs.format(new Date()).trim();
            String templateName = "testView_Distributed" + currentTime;
            HashMap<String, String> distributedDriver = new HashMap<String, String>(){
                {
                    put("Name", "DistributedDriver");
                    put("Type", "Items");
                    put("Channel", "Channel01");
                    put("Category", "Enrollments");
                    put("Show in App", "No");
                    put("Order", "1");
                    put("Forecast Source", "Distributed");
                    put("Source Demand Driver", "LegionMLDriver");
                    put("Distribution of Demand Driver", "ImportedDriver");
                }
            };
            HashMap<String, String> legionMLDriver = new HashMap<String, String>(){
                {
                    put("Name", "LegionMLDriver");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Enrollments");
                    put("Show in App", "No");
                    put("Order", "2");
                    put("Forecast Source", "Legion ML");
                    put("Input Stream", "Items:EDW:Enrollments");
                }
            };
            HashMap<String, String> importedDriver = new HashMap<String, String>(){
                {
                    put("Name", "ImportedDriver");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Verifications");
                    put("Show in App", "Yes");
                    put("Order", "3");
                    put("Forecast Source", "Imported");
                    put("Input Stream", "Items:EDW:Verifications");
                }
            };
            //Turn on EnableTahoeStorage toggle
            ToggleAPI.enableToggle(Toggles.EnableTahoeStorage.getValue(), "jane.meng+006@legion.co", "admin11.a");
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
            //Add two basic drivers first
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(legionMLDriver);
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(importedDriver);
            //Add distributed driver, and publish
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(distributedDriver);
            configurationPage.selectOneDynamicGroup("testDerived_NotDelete");
            configurationPage.clickOnTemplateDetailTab();
            configurationPage.publishNowTemplate();

            //check in view mode
            configurationPage.clickOnSpecifyTemplateName(templateName, "Edit");
            configurationPage.searchDriverInTemplateDetailsPage(distributedDriver.get("Name"));
            SimpleUtils.assertOnFail("Verify distributed driver page failed in read only mode!", configurationPage.verifyDriverInViewMode(distributedDriver), false);
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.archiveOrDeleteTemplate(templateName);
            ToggleAPI.disableToggle(Toggles.EnableTahoeStorage.getValue(), "jane.meng+006@legion.co", "admin11.a");
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate view for Remote Demand Driver.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyRemoteDemandDriverInReadOnlyModeAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String templateType = "Demand Drivers";
            SimpleDateFormat dfs = new SimpleDateFormat("MMddHHmm");
            String currentTime =  dfs.format(new Date()).trim();
            String templateName = "testView_Remote" + currentTime;
            HashMap<String, String> parentLocationDriver = new HashMap<String, String>(){
                {
                    put("Name", "ParentLocationDriver");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Enrollments");
                    put("Show in App", "Yes");
                    put("Order", "1");
                    put("Forecast Source", "Remote");
                    put("Specify Location", "Parent Location");
                    put("Parent Level", "2");
                }
            };
            HashMap<String, String> remoteLocationDriver = new HashMap<String, String>(){
                {
                    put("Name", "RemoteLocationDriver");
                    put("Type", "Items");
                    put("Channel", "EDW");
                    put("Category", "Verifications");
                    put("Show in App", "No");
                    put("Order", "2");
                    put("Forecast Source", "Remote");
                    put("Specify Location", "Remote Location");
                    put("Choose Remote Location", "Boston");
                }
            };
            //Turn on EnableTahoeStorage toggle
            ToggleAPI.enableToggle(Toggles.EnableTahoeStorage.getValue(), "jane.meng+006@legion.co", "admin11.a");
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
            //Add remote driver, and publish
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(parentLocationDriver);
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(remoteLocationDriver);
            configurationPage.selectOneDynamicGroup("testDerived_NotDelete");
            configurationPage.clickOnTemplateDetailTab();
            configurationPage.publishNowTemplate();

            //check in view mode for remote location driver
            configurationPage.clickOnSpecifyTemplateName(templateName, "Edit");
            configurationPage.searchDriverInTemplateDetailsPage(parentLocationDriver.get("Name"));
            SimpleUtils.assertOnFail("Verify remote:parent driver page failed in read only mode!", configurationPage.verifyDriverInViewMode(parentLocationDriver), false);
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            //check in view mode for parent location driver
            configurationPage.searchDriverInTemplateDetailsPage(remoteLocationDriver.get("Name"));
            SimpleUtils.assertOnFail("Verify remote:remote driver page failed in read only mode!", configurationPage.verifyDriverInViewMode(remoteLocationDriver), false);
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            ToggleAPI.disableToggle(Toggles.EnableTahoeStorage.getValue(), "jane.meng+006@legion.co", "admin11.a");
            configurationPage.archiveOrDeleteTemplate(templateName);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify user not allow to remove channels and categories in settings")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, enabled = false)
    public void verifyRemoveChannelsAndCategoriesNotAllowedAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            //-------------------will make it enabled when bug OPS-4600 fixed--------------
            String templateType = "Demand Drivers";
            SimpleDateFormat dfs = new SimpleDateFormat("MMddHHmm");
            String currentTime =  dfs.format(new Date()).trim();
            String templateName = "testRemove_ChannelAndCategory";
            String channelName = "Channel_testRemove";
            String channelDes = "This is a test for channel remove!";
            String categoryName = "Category_testRemove";
            String categoryDes = "This is a test for Category remove!";
            HashMap<String, String> driverSpecificInfo = new HashMap<String, String>(){
                {
                    put("Name", "DriverTest_RemoveChannelAndCategory");
                    put("Type", "Items");
                    put("Channel", channelName);
                    put("Category", categoryName);
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
            //Go to Settings tab
            settingsAndAssociationPage.goToTemplateListOrSettings("Settings");
            //Add new channel in settings.
            settingsAndAssociationPage.createNewChannelOrCategory("channel", channelName, channelDes);
            //Add new category in settings.
            settingsAndAssociationPage.createNewChannelOrCategory("category", categoryName, categoryDes);
            //Go to Template tab
            settingsAndAssociationPage.goToTemplateListOrSettings("Template");
            configurationPage.createNewTemplate(templateName);
            configurationPage.clickOnSpecifyTemplateName(templateName, "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            //Add legionMl driver, and publish
            configurationPage.clickAddOrEditForDriver("Add");
            configurationPage.addOrEditDemandDriverInTemplate(driverSpecificInfo);
            configurationPage.selectOneDynamicGroup("testDerived_NotDelete");
            configurationPage.clickOnTemplateDetailTab();
            configurationPage.publishNowTemplate();
            //Remove channel and category in Settings
            settingsAndAssociationPage.goToTemplateListOrSettings("Settings");
            settingsAndAssociationPage.clickOnRemoveBtnInSettings("channel", channelName);
            settingsAndAssociationPage.clickOnRemoveBtnInSettings("category", categoryName);
            SimpleUtils.assertOnFail("Channel used in published template should not be able to remove!",
                    settingsAndAssociationPage.searchSettingsForDemandDriver("channel", channelName) != null, false);
            SimpleUtils.assertOnFail("Category used in published template should not be able to remove!",
                    settingsAndAssociationPage.searchSettingsForDemandDriver("category", categoryName) != null, false);
            //Archive the template
            settingsAndAssociationPage.goToTemplateListOrSettings("Template");
            configurationPage.archiveOrDeleteTemplate(templateName);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify time configuration changed to text input")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTimeConfigurationChangedToTextInputAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
        String OHTemplateType = "Operating Hours";
        String SchedulingRulesTemplateType = "Scheduling Rules";
        SimpleDateFormat dfs = new SimpleDateFormat("MMddHHmm");
        String currentTime =  dfs.format(new Date()).trim();
        String OHTemplate = "OH" + currentTime;
        String SchedulingRulesTemplate = "SchedulingRules" + currentTime;
        String dayParts = "DP1-forAuto";
        String workRole = "Auto";

        //Go to OH template and create one
        ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
        configurationPage.goToConfigurationPage();
        configurationPage.clickOnConfigurationCrad(OHTemplateType);
        configurationPage.createNewTemplate(OHTemplate);
        //Check time configuration changed to text input
        configurationPage.clickOnSpecifyTemplateName(OHTemplate, "edit");
        configurationPage.clickOnEditButtonOnTemplateDetailsPage();
        //Check it for "None"
        configurationPage.selectDaypart(dayParts);
        configurationPage.goToBusinessHoursEditPage("sunday");
        configurationPage.checkOpenAndCloseTime();
        configurationPage.clickOnCancelButton();
        //Check it for "Open / Close"
        configurationPage.selectOperatingBufferHours("StartEnd");
        configurationPage.clickOpenCloseTimeLink();
        configurationPage.checkOpenAndCloseTime();
        configurationPage.clickOnCancelButton();
        configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
        configurationPage.setLeaveThisPageButton();
        configurationPage.archiveOrDeleteTemplate(OHTemplate);
        configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();

        //Go to Scheduling Rules template and create one
        configurationPage.clickOnConfigurationCrad(SchedulingRulesTemplateType);
        configurationPage.createNewTemplate(SchedulingRulesTemplate);
        //Check time configuration changed to text input
        configurationPage.clickOnSpecifyTemplateName(SchedulingRulesTemplate, "edit");
        configurationPage.clickOnEditButtonOnTemplateDetailsPage();
        configurationPage.selectWorkRoleToEdit(workRole);
        configurationPage.checkTheEntryOfAddBasicStaffingRule();
        configurationPage.verifyStaffingRulePageShowWell();
        configurationPage.selectStartTimeEvent("Specified Hours");
        configurationPage.clickOpenCloseTimeLink();
        configurationPage.checkOpenAndCloseTime();
        configurationPage.clickOnCancelButton();
        configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
        configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
        configurationPage.archiveOrDeleteTemplate(OHTemplate);
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify time validation for Operating Hours")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTimeValidationForOperatingHoursAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String OHTemplateType = "Operating Hours";
            SimpleDateFormat dfs = new SimpleDateFormat("MMddHHmm");
            String currentTime =  dfs.format(new Date()).trim();
            String OHTemplate = "OH" + currentTime;
            String dayParts = "DP1-forAuto";
            String crossNextDay = "Yes";
            List<String> dayOfWeek = new ArrayList<>(Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"));

            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();

            //Go to OH template and create one
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(OHTemplateType);
            configurationPage.createNewTemplate(OHTemplate);
            configurationPage.clickOnSpecifyTemplateName(OHTemplate, "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            //Check default time configuration could save successfully
            configurationPage.selectDaypart(dayParts);
            configurationPage.createDynamicGroup(OHTemplate, "Custom", "Auto--Custom script" + currentTime);
            configurationPage.selectOneDynamicGroup(OHTemplate);
            settingsAndAssociationPage.goToTemplateListOrSettings("Template");
            configurationPage.publishNowTemplate();
            configurationPage.clickOnSpecifyTemplateName(OHTemplate, "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.goToBusinessHoursEditPage("sunday");

            configurationPage.setOpenCloseTime("Open/Close", "6:00AM", "5:00AM", "No");
            configurationPage.setOpenCloseTime("Open/Close", "12:00AM", "1:00AM", crossNextDay);
            configurationPage.clickOnCancelButton();
            configurationPage.goToBusinessHoursEditPage("sunday");
            configurationPage.setOpenCloseTime("Open/Close", "6:00AM", "5:00AM", crossNextDay);
            configurationPage.setOpenCloseTime("Dayparts", "6:00AM", "5:00AM", "No");
            configurationPage.setOpenCloseTime("Dayparts", "12:00AM", "1:00AM", crossNextDay);
            configurationPage.setOpenCloseTime("Dayparts", "6:00AM", "12:00PM", "No");
            configurationPage.saveBtnIsClickable();

            for (String day : dayOfWeek){
                if (day.equalsIgnoreCase("sunday")){
                    SimpleUtils.assertOnFail(day + "'s start time and end time is not as expected!", configurationPage.verifyStartEndTimeForDays("6:00 AM", "5:00 AM", "sunday"), false);
                }else{
                    SimpleUtils.assertOnFail(day + "'s start time and end time is not as expected!", configurationPage.verifyStartEndTimeForDays("12:00 AM", "12:00 AM", day), false);
                }
            }
            configurationPage.goToBusinessHoursEditPage("sunday");
            configurationPage.selectDaysForOpenCloseTime(dayOfWeek);
            configurationPage.saveBtnIsClickable();

            for (String day : dayOfWeek){
                SimpleUtils.assertOnFail(day + "'s start time and end time is not as expected!", configurationPage.verifyStartEndTimeForDays("6:00 AM", "5:00 AM", day), false);
            }
            configurationPage.publishNowTemplate();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Jane")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify time validation for Scheduling Rules")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTimeValidationForSchedulingRulesAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            String SchedulingRulesTemplateType = "Scheduling Rules";
            SimpleDateFormat dfs = new SimpleDateFormat("MMddHHmm");
            String currentTime =  dfs.format(new Date()).trim();
            String SchedulingRulesTemplate = "SchedulingRules" + currentTime;
            String workRole = "Auto";
            String crossNextDay = "Yes";

            //Go to Scheduling Rules template and create one
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            SettingsAndAssociationPage settingsAndAssociationPage = pageFactory.createSettingsAndAssociationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(SchedulingRulesTemplateType);
            configurationPage.createNewTemplate(SchedulingRulesTemplate);
            //Default open and close time in scheduling rules template
            configurationPage.clickOnSpecifyTemplateName(SchedulingRulesTemplate, "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            configurationPage.checkTheEntryOfAddBasicStaffingRule();
            configurationPage.verifyStaffingRulePageShowWell();
            configurationPage.selectStartTimeEvent("Specified Hours");
            configurationPage.clickOnSaveButtonOnScheduleRulesListPage();
            configurationPage.createDynamicGroup(SchedulingRulesTemplate, "Custom", "Auto--Custom script" + currentTime);
            configurationPage.selectOneDynamicGroup(SchedulingRulesTemplate);
            settingsAndAssociationPage.goToTemplateListOrSettings("Template");
            configurationPage.publishNowTemplate();
            //Set open and close time in scheduling rules template
            configurationPage.clickOnSpecifyTemplateName(SchedulingRulesTemplate, "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            configurationPage.editBasicStaffingRules();
            configurationPage.verifyStaffingRulePageShowWell();
            configurationPage.clickOpenCloseTimeLink();
            configurationPage.setOpenCloseTime("Open/Close", "6:00AM", "5:00AM", "No");
            configurationPage.setOpenCloseTime("Open/Close", "12:00AM", "1:00AM", crossNextDay);
            configurationPage.setOpenCloseTime("Open/Close", "6:00AM", "5:00AM", crossNextDay);
            configurationPage.saveBtnIsClickable();
            configurationPage.clickOnSaveButtonOnScheduleRulesListPage();
            configurationPage.publishNowTemplate();
            //Archive or delete the template
            configurationPage.clickOnSpecifyTemplateName(SchedulingRulesTemplate, "edit");
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.clickOnAssociationTabOnTemplateDetailsPage();
            configurationPage.deleteOneDynamicGroup(SchedulingRulesTemplate);
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.setLeaveThisPageButton();
            configurationPage.archiveOrDeleteTemplate(SchedulingRulesTemplate);
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
    @TestName(description = "Audit Log History Button")
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

//            configurationPage.goToItemInConfiguration("Minors Rules");
//            configurationPage.searchTemplate("AuditLog");
//            configurationPage.clickOnTemplateName("AuditLog");
//            configurationPage.verifyHistoryButtonDisplay();
//            configurationPage.verifyHistoryButtonIsClickable();
//            configurationPage.verifyCloseIconNotDisplayDefault();
//            configurationPage.clickHistoryAndClose();
//            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
//            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();

//            configurationPage.goToItemInConfiguration("Additional Pay Rules");
//            configurationPage.searchTemplate("AuditLog");
//            configurationPage.clickOnTemplateName("AuditLog");
//            configurationPage.verifyHistoryButtonDisplay();
//            configurationPage.verifyHistoryButtonIsClickable();
//            configurationPage.verifyCloseIconNotDisplayDefault();
//            configurationPage.clickHistoryAndClose();
//            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
//            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();

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

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Audit Log UI Checking")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void auditLogUIVerificationAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String templateType = "Operating Hours";
            String mode = "view";
            String templateName = "AuditLogAutoUsing";
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickHistoryButton();
            configurationPage.verifyTemplateHistoryUI();
        }catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Audit Log Button")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void auditLogHistoryButtonVerificationAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String templateType = "Operating Hours";
            String mode = "view";
            String templateName = "AuditLogAutoUsing";
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickHistoryButton();
            configurationPage.verifyRecordIsClickable();
        }catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Template History Content Check")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void auditLogContentVerificationAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String templateType = "Operating Hours";
            String mode = "view";
            String templateName = "AuditLogAutoUsing";
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickHistoryButton();
            configurationPage.verifyTemplateHistoryContent();
            configurationPage.verifyOrderOfTheTemplateHistory();
        }catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Audit Log E2E")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void auditLogE2EAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime =dfs.format(new Date()).trim();
            SimpleDateFormat dfs1 = new SimpleDateFormat("hh:mm:ss a','MM/dd/yyyy");
            String templateName = "AuditLog" + currentTime;
            String dynamicGpName = "AuditLog" + currentTime;
            String criteriaType ="Custom";


            String criteriaValue="AutoCreatedDynamic---Format Script" + currentTime;
            String viewMode = "view";
            String editMode ="edit";
            String templateType ="Scheduling Policies";
            String createOption = "Created";
            String publishOption = "Published";
            String publishFutureOption ="set to publish on";
            String archiveOption ="Archived";
            String deleteOption="Deleted";
            String button = "publish at different time";
            int date = 14;
            Random random1=new Random();
            int number1=random1.nextInt(90)+10;
            String count1=String.valueOf(number1);
            Random random2=new Random();
            int number2=random2.nextInt(90)+10;
            String count2=String.valueOf(number2);

            ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
            profileNewUIPage.clickOnUserProfileImage();
            profileNewUIPage.selectProfileSubPageByLabelOnProfileImage("My Profile");
            String userFullName = profileNewUIPage.getUserProfileName().get("fullName");

            //check create new template and save as draft template history
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.createNewTemplate(templateName);
            String time1 =dfs1.format(new Date()).trim();
            configurationPage.clickOnSpecifyTemplateName(templateName,viewMode);
            configurationPage.clickHistoryButton();
            configurationPage.verifyNewCreatedTemplateHistoryContent(createOption,userFullName,time1);
            configurationPage.verifyRecordIsClickable();
            configurationPage.verifyUpdatedSchedulePolicyTemplateFirstFieldCorrectOrNot("35");

            //check publish template history can show correct and verify data in template via template history
            configurationPage.closeTemplateHistoryPanel();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.clickOnSpecifyTemplateName(templateName,editMode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.updateSchedulePolicyTemplateFirstField(count1);
            configurationPage.createDynamicGroup(dynamicGpName,criteriaType,criteriaValue);
            configurationPage.selectOneDynamicGroup(dynamicGpName);
            configurationPage.clickOnTemplateDetailTab();
            configurationPage.publishNowTemplate();
            configurationPage.clickOnSpecifyTemplateName(templateName,viewMode);
            configurationPage.clickHistoryButton();
            configurationPage.verifyPublishTemplateHistoryContent(publishOption,userFullName);
            configurationPage.verifyRecordIsClickable();
            configurationPage.verifyUpdatedSchedulePolicyTemplateFirstFieldCorrectOrNot(count1);

            //Check publish future template history and data showing in each version
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.clickOnSpecifyTemplateName(templateName,editMode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.updateSchedulePolicyTemplateFirstField(count2);
            configurationPage.publishAtDifferentTimeForTemplate(button,date);
            configurationPage.openCurrentPublishInMultipleTemplate(templateName);
            configurationPage.clickHistoryButton();
            configurationPage.verifyPublishFutureTemplateHistoryContent(publishFutureOption,userFullName);
            configurationPage.verifyRecordIsClickable();
            configurationPage.verifyUpdatedSchedulePolicyTemplateFirstFieldCorrectOrNot(count2);

            //check archive template history
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.openCurrentPublishInMultipleTemplate(templateName);
            configurationPage.clickOnArchiveButton();
            configurationPage.clickOnSpecifyTemplateName(templateName,editMode);
            configurationPage.clickHistoryButton();
            configurationPage.verifyArchiveTemplateHistoryContent(archiveOption,userFullName);

            //Check delete template history
            configurationPage.closeTemplateHistoryPanel();
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
            configurationPage.clickOnSpecifyTemplateName(templateName,editMode);
            configurationPage.clickOnDeleteButtonOnTemplateDetailsPage();
            configurationPage.clickOnSpecifyTemplateName(templateName,editMode);
            configurationPage.clickHistoryButton();
            configurationPage.verifyDeleteTemplateHistoryContent(deleteOption,userFullName);
        }catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Each Template type have audit log")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAuditLogForEachTemplateTypeAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            //check create new template and save as draft template history
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.verifyAllTemplateTypeHasAuditLog();
        }catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Location Level Audit Log Checking")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void auditLogAtLocationLevelTemplateAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String locationName = "AuditLogAutoLocation";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.goToLocationDetailsPage(locationName);
            locationsPage.goToConfigurationTabInLocationLevel();
            locationsPage.clickActionsForTemplate("Assignment Rules","View");
            configurationPage.verifyLocationLevelTemplateNoHistoryButton();
            locationsPage.backToConfigurationTabInLocationLevel();
            locationsPage.clickActionsForTemplate("Scheduling Policies","View");
            configurationPage.verifyLocationLevelTemplateNoHistoryButton();
            locationsPage.backToConfigurationTabInLocationLevel();
            locationsPage.clickActionsForTemplate("Compliance","View");
            configurationPage.verifyLocationLevelTemplateNoHistoryButton();
            locationsPage.backToConfigurationTabInLocationLevel();
            locationsPage.clickActionsForTemplate("Schedule Collaboration","View");
            configurationPage.verifyLocationLevelTemplateNoHistoryButton();
            locationsPage.backToConfigurationTabInLocationLevel();
            locationsPage.clickActionsForTemplate("Time and Attendance","View");
            configurationPage.verifyLocationLevelTemplateNoHistoryButton();
            locationsPage.backToConfigurationTabInLocationLevel();
            locationsPage.clickActionsForTemplate("Demand Drivers","View");
            configurationPage.verifyLocationLevelTemplateNoHistoryButton();
            locationsPage.backToConfigurationTabInLocationLevel();
            locationsPage.clickActionsForTemplate("Scheduling Rules","View");
            configurationPage.verifyLocationLevelTemplateNoHistoryButton();
            locationsPage.backToConfigurationTabInLocationLevel();
            locationsPage.clickActionsForTemplate("Labor Model","View");
            configurationPage.verifyLocationLevelTemplateNoHistoryButton();
            locationsPage.backToConfigurationTabInLocationLevel();
            locationsPage.clickActionsForTemplate("Operating Hours","View");
            configurationPage.verifyLocationLevelTemplateNoHistoryButton();
            locationsPage.backToConfigurationTabInLocationLevel();
        }catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Basic Staffing Rule E2E")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void basicStaffingRuleE2EAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String locationName = "BasicStaffingRuleE2E";
            String shiftsNumber = "2";
            String scheduleDayViewGridTimeDurationStart ="8 AM";
            String scheduleDayViewGridTimeDurationEnd="5 PM";
            String workRoleName ="Auto Using";
            String shiftStartTime ="8:30 am";
            String shiftEndTime ="5:00 pm";

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
            // create the schedule if not created and set as day view
            boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUIWithoutUpdateOH();
            scheduleCommonPage.clickOnDayView();

            //verify schedule table time duration and verify how many TMs in each slot
            if(scheduleCommonPage.isScheduleDayViewActive()){
                List<String> scheduleDayViewGridTimeDuration= scheduleShiftTablePage.getScheduleDayViewGridTimeDuration();
                if(scheduleDayViewGridTimeDuration.get(0).equalsIgnoreCase(scheduleDayViewGridTimeDurationStart)
                        && scheduleDayViewGridTimeDuration.get(scheduleDayViewGridTimeDuration.size()-1).equalsIgnoreCase(scheduleDayViewGridTimeDurationEnd)){
                    SimpleUtils.pass("Schedule Day View Grid Time Duration is correct");
                }else {
                    SimpleUtils.fail("Schedule Day View Grid Time Duration is NOT correct",false);
                }

                List<String> scheduleDayViewBudgetedTeamMembersCount = scheduleShiftTablePage.getScheduleDayViewBudgetedTeamMembersCount();
                for (int i =1;i<scheduleDayViewBudgetedTeamMembersCount.size()-1;i++) {
                    if(scheduleDayViewBudgetedTeamMembersCount.get(i).equalsIgnoreCase(shiftsNumber)){
                        SimpleUtils.pass("Number of shifts set in Basic staffing rule can work well");
                    }else {
                        SimpleUtils.fail("Number of shifts set in Basic staffing rule can work well",false);
                    }
                }

                List<WebElement> allShifts = scheduleShiftTablePage.getAvailableShiftsInDayView();
                List<String> shiftInfo = new ArrayList<>();
                int index = 0;
                List<String> shiftStartTimeList = new ArrayList<>();
                List<String> shiftEndTimeList = new ArrayList<>();
                List<String> workRoleNameList = new ArrayList<>();
                for(int i=0;i<allShifts.size();i++){
                    shiftInfo = scheduleShiftTablePage.getTheShiftInfoInDayViewByIndex(i);
                    workRoleNameList.add(shiftInfo.get(4));
                    String[] shiftDuration = shiftInfo.get(2).split("-");
                    shiftStartTimeList.add(shiftDuration[0]);
                    shiftEndTimeList.add(shiftDuration[1]);
                }

                //verify the work role is correct or not
                for(String workRole:workRoleNameList){
                    if(workRole.equalsIgnoreCase(workRoleName)){
                        SimpleUtils.pass("Work role set in basic staffing rule can work well");
                    }else {
                        SimpleUtils.fail("Work role set in basic staffing rule can work well",false);
                    }
                }

                //verify shift start time is correct or not
                if(shiftStartTimeList.get(0).equalsIgnoreCase(shiftStartTime)){
                    SimpleUtils.pass("Shift start time set in basic staffing rule can work well");
                }else {
                    SimpleUtils.fail("Shift start time set in basic staffing rule can't work well",false);
                }

                //verify shift end time is correct or not
                if(shiftEndTimeList.get(shiftEndTimeList.size()-1).equalsIgnoreCase(shiftEndTime)){
                    SimpleUtils.pass("Shift end time set in basic staffing rule can work well");
                }else {
                    SimpleUtils.fail("Shift end time set in basic staffing rule can't work well",false);
                }
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
    
    @Automated(automated = "Automated")
    @Owner(owner = "Yang")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate the layout of the new template page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void ValidateLayoutOfNewTemplatePageAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {
            List<String> templateTypes = new ArrayList<String>() {{
                add("Operating Hours");
                add("Scheduling Policies");
                add("Scheduling Rules");
            }};
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime = dfs.format(new Date()).trim();
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            for (String templateType : templateTypes) {
                String templateName = templateType + currentTime;
                configurationPage.goToConfigurationPage();
                configurationPage.clickOnConfigurationCrad(templateType);
                configurationPage.createNewTemplate(templateName);
                configurationPage.searchTemplate(templateName);
                configurationPage.clickOnTemplateName(templateName);
                configurationPage.verifyTheLayoutOfTemplateDetailsPage();
                configurationPage.clickOnEditButtonOnTemplateDetailsPage();
                configurationPage.verifyTheLayoutOfTemplateAssociationPage();
                configurationPage.verifyCriteriaTypeOfDynamicGroup();
                configurationPage.clickOnBackBtnOnTheTemplateDetailAndListPage();
                configurationPage.searchTemplate(templateName);
                configurationPage.archivePublishedOrDeleteDraftTemplate(templateName, "Delete");
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify default value of override via integration button")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void ValidateDefaultValueOfOverrideViaIntegrationButtonAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try {

            String templateType = "Operating Hours";
            String templateName ="FionaUsingUpdateLocationLevelOH";
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime = dfs.format(new Date()).trim();
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();

            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.searchTemplate(templateName);
            configurationPage.clickOnTemplateName(templateName);
            configurationPage.verifyDefaultValueOfOverrideViaIntegrationButton();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Update Reset location level OH when the button is off")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyOverriddenOperatingHoursInLocationLevelAsInternalAdmin (String browser, String username, String password, String location) throws Exception {

        try {

            String locationName = "updateOHViaIntegration";
            int moveCount = 4;
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();

            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.goToLocationDetailsPage(locationName);
            locationsPage.goToConfigurationTabInLocationLevel();
            locationsPage.clickActionsForTemplate("Operating Hours", "View");
            locationsPage.backToConfigurationTabInLocationLevel();
            locationsPage.clickActionsForTemplate("Operating Hours", "Edit");

            locationsPage.editBtnIsClickableInBusinessHours();
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            locationsPage.selectDayInWorkingHoursPopUpWin(6);
            configurationPage.saveBtnIsClickable();
            configurationPage.saveBtnIsClickable();
            locationsPage.verifyOverrideStatusAtLocationLevel("Operating Hours", "Yes");
            //reset
            locationsPage.clickActionsForTemplate("Operating Hours", "Reset");
            locationsPage.verifyOverrideStatusAtLocationLevel("Operating Hours", "No");

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

}