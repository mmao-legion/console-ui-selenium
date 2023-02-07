package com.legion.tests.core.OpsPortal;

import com.alibaba.fastjson.JSONObject;
import com.legion.api.login.LoginAPI;
import com.legion.api.toggle.ToggleAPI;
import com.legion.api.toggle.Toggles;
import com.legion.pages.*;
import com.legion.pages.OpsPortaPageFactories.ConfigurationPage;
import com.legion.pages.OpsPortaPageFactories.LocationsPage;
import com.legion.pages.OpsPortaPageFactories.SettingsAndAssociationPage;
import com.legion.pages.OpsPortaPageFactories.UserManagementPage;
import com.legion.pages.core.ConsoleLocationSelectorPage;
import com.legion.pages.core.OpCommons.OpsCommonComponents;
import com.legion.pages.core.OpCommons.OpsPortalNavigationPage;
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
import com.legion.utils.Constants;
import com.legion.utils.HttpUtil;
import com.legion.utils.SimpleUtils;
import cucumber.api.java.ro.Si;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
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
import java.util.stream.Collectors;

import static com.legion.utils.MyThreadLocal.getDriver;
import static com.legion.utils.MyThreadLocal.getEnterprise;


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
    @BeforeMethod(alwaysRun = true)
    public void firstTest(Method testMethod, Object[] params) throws Exception{


        this.createDriver((String)params[0],"83","Window");
//        ToggleAPI.updateToggle(Toggles.DynamicGroupV2.getValue(), "jane.meng+006@legion.co", "P@ssword123", false);
//        ToggleAPI.updateToggle(Toggles.EnableDemandDriverTemplate.getValue(), "jane.meng+006@legion.co", "P@ssword123", true);
//        ToggleAPI.updateToggle(Toggles.MixedModeDemandDriverSwitch.getValue(), "jane.meng+006@legion.co", "P@ssword123", true);
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
//https://legiontech.atlassian.net/browse/SCH-8338
    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "E2E Verify number of shift function in advance staffing rule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class,enabled = false)
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
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, enabled = false)
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
            String currentOperatingHour = "7AM-12AM";
            String futureOperatingHour = "7:30 AM-12AM";

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
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            String openString = "09:00AM";
            String closeString ="08:30PM";

            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.goToLocationDetailsPage(locationName);
            locationsPage.goToConfigurationTabInLocationLevel();
            if(locationsPage.verifyIsOverrideStatusAtLocationLevel("Operating Hours")){
                locationsPage.clickActionsForTemplate("Operating Hours", "Reset");
            }
            //user can view location level OH template
            locationsPage.clickActionsForTemplate("Operating Hours", "View");
            locationsPage.backToConfigurationTabInLocationLevel();
            //user can edit location level OH template
            locationsPage.clickActionsForTemplate("Operating Hours", "Edit");
            locationsPage.editBtnIsClickableInBusinessHours();
            locationsPage.updateOpenCloseHourForOHTemplate(openString,closeString);
            locationsPage.selectDayInWorkingHoursPopUpWin(6);
            configurationPage.saveBtnIsClickable();
            configurationPage.saveBtnIsClickable();
            if(locationsPage.verifyIsOverrideStatusAtLocationLevel("Operating Hours")){
                SimpleUtils.pass("User can update location level template successfully");
            }else {
                SimpleUtils.fail("User failed to update location level template",false);
            }

            //reset
            locationsPage.clickActionsForTemplate("Operating Hours", "Reset");
            if(!locationsPage.verifyIsOverrideStatusAtLocationLevel("Operating Hours")){
                SimpleUtils.pass("User can reset successfully");
            }else {
                SimpleUtils.fail("User failed to reset location level template",false);
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "User can only view location level OH when the button is on")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyUserOnlyCanViewOperatingHoursInLocationLevelAsInternalAdmin (String browser, String username, String password, String location) throws Exception {

        try {

            String locationName = "updateOHViaInteTest";
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();

            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.goToLocationDetailsPage(locationName);
            locationsPage.goToConfigurationTabInLocationLevel();

            //Verify user can only see view button in location level
            List<String> actions = new ArrayList<>();
            actions = locationsPage.actionsForTemplateInLocationLevel("Operating Hours");
            if(actions.size()==1 && actions.get(0).equalsIgnoreCase("View")){
                SimpleUtils.pass("User can only view location level Operating Hours when Override via integration is on");
            }else {
                SimpleUtils.fail("User can do other actions and not only view for location level OH when Override via integration is on",false);
            }
            //user can view location level OH template
            locationsPage.clickActionsForTemplate("Operating Hours", "View");
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify other template don't have Override via integration button")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyOtherTemplateNoOverrideViaIntegrationButtonAsInternalAdmin (String browser, String username, String password, String location) throws Exception {

        try {
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            String templateType = "Scheduling Policies";
            String templateName ="UsedByAuto";

            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.searchTemplate(templateName);
            configurationPage.clickOnSpecifyTemplateName(templateName,"view");
            if(!configurationPage.verifyOverrideViaIntegrationButtonShowingOrNot()){
                SimpleUtils.pass("There is no Override via integration button for other template types");
            }else {
                SimpleUtils.fail("There is Override via integration button for other template types",false);
            }
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    public void importBusinessHours(String sessionId) {
        String filePath = "src/test/resources/uploadFile/LocationLevelWithoutDayparts.csv";
        String url = "https://staging-enterprise.dev.legion.work/legion/integration/testUploadBusinessHoursData?isTest=false&fileName=" + filePath + "&encrypted=false";
        String responseInfo = HttpUtil.fileUploadByHttpPost(url, sessionId, filePath);
        if (StringUtils.isNotBlank(responseInfo)) {
            JSONObject json = JSONObject.parseObject(responseInfo);
            if (!json.isEmpty()) {
                String value = json.getString("responseStatus");
                System.out.println(value);
            }
        }
    }

    //blocked by API error
    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify user can update location level operating hours via integration")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class,enabled = false)
    public void verifyUserCanUpdateLocationOHViaIntegrationAsInternalAdmin (String browser, String username, String password, String location) throws Exception {

        try {
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            String locationName = "updateOHViaInteTest";
            String templateName="updateOHViaInteTest";
            String sessionId = LoginAPI.getSessionIdFromLoginAPI("fiona+99@legion.co","admin11.a");
            importBusinessHours(sessionId);

            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.goToLocationDetailsPage(locationName);
            locationsPage.goToConfigurationTabInLocationLevel();
            //verify location level OH is overridden or not?
            if(locationsPage.isOverrideStatusAtLocationLevel("Operating Hours")){
                SimpleUtils.pass("User can update location level OH via integration");
            }else {
                SimpleUtils.fail("User can NOT update location level OH via integration",false);
            }
            //Go to configuration and edit this template's override button to false then reset location level OH
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad("Operating Hours");
            configurationPage.clickOnSpecifyTemplateName(templateName,"edit");
            configurationPage.turnOnOffOverrideViaIntegrationButton();
            configurationPage.publishNowTemplate();
            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.goToLocationDetailsPage(locationName);
            locationsPage.goToConfigurationTabInLocationLevel();
            locationsPage.clickActionsForTemplate("Operating Hours", "Reset");
            //back to template details page to turn on the button
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad("Operating Hours");
            configurationPage.clickOnSpecifyTemplateName(templateName,"edit");
            configurationPage.turnOnOffOverrideViaIntegrationButton();
            configurationPage.publishNowTemplate();
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify the dynamic group should be shown in advance staffing rule page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyBasicFunctionOfDynamicGroupInADVAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "AutoADVDynamicGroup";
            String workRole = "AMBASSADOR";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            configurationPage.checkTheEntryOfAddAdvancedStaffingRule();
            configurationPage.verifyAdvancedStaffingRulePageShowWell();
            configurationPage.verifyAddButtonOfDynamicLocationGroupOfAdvancedStaffingRuleIsClickable();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify each field of dynamic location group of advance staffing rule")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyEachFieldsOfDynamicGroupInADVAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "AutoADVDynamicGroup";
            String workRole = "AMBASSADOR";
            String dynamicGpName = "FionaAutoTest";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            configurationPage.checkTheEntryOfAddAdvancedStaffingRule();
            configurationPage.verifyAdvancedStaffingRulePageShowWell();
            configurationPage.verifyAddButtonOfDynamicLocationGroupOfAdvancedStaffingRuleIsClickable();
            //check dynamic group dialog UI add dynamic group
            configurationPage.advanceStaffingRuleDynamicGroupDialogUICheck(dynamicGpName);
            //edit delete dynamic group
            configurationPage.advanceStaffingRuleEditDeleteADynamicGroup(dynamicGpName);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Yang")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify Create Each Template with Dynamic Group Association ")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateEachTemplateWithDynamicGroupAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime=dfs.format(new Date()).trim();
            String templateNameVerify="LizzyUsingToCreateTempTest"+currentTime;
            String[] tempType={"Operating Hours","Scheduling Policies","Schedule Collaboration","Time & Attendance"};
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
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Create dynamic group with specify criteria")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateDynamicGroupWithSpecifyCriteriaAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "AutoADVDynamicGroup";
            String workRole = "AMBASSADOR";
            String dynamicGpName = "FionaAutoTest123";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            configurationPage.checkTheEntryOfAddAdvancedStaffingRule();
            configurationPage.verifyAdvancedStaffingRulePageShowWell();
            configurationPage.verifyAddButtonOfDynamicLocationGroupOfAdvancedStaffingRuleIsClickable();
            //Create dynamic group with specify criteria
            configurationPage.createAdvanceStaffingRuleDynamicGroup(dynamicGpName);
            //edit delete dynamic group
            configurationPage.advanceStaffingRuleEditDeleteADynamicGroup(dynamicGpName);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Dynamic group dropdown list checking and custom formula description checking")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDynamicGroupDropdownListCheckingAndCustomFormulaDescriptionCheckingAsInternalAdmin(String browser, String username, String password, String location) throws Exception {

        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "AutoADVDynamicGroup";
            String workRole = "AMBASSADOR";
            String dynamicGpName = "FionaAutoTest123";

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.selectWorkRoleToEdit(workRole);
            configurationPage.checkTheEntryOfAddAdvancedStaffingRule();
            configurationPage.verifyAdvancedStaffingRulePageShowWell();
            configurationPage.verifyAddButtonOfDynamicLocationGroupOfAdvancedStaffingRuleIsClickable();
            //Create dynamic group with specify criteria
            configurationPage.advanceStaffingRuleDynamicGroupCriteriaListChecking(dynamicGpName);
            configurationPage.advanceStaffingRuleEditDeleteADynamicGroup(dynamicGpName);
            configurationPage.advanceStaffingRuleDynamicGroupCustomFormulaDescriptionChecking();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify dynamic group item in advanced staffing rules is optional")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDynamicGroupOfAdvanceStaffingRulesIsOptionalAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String templateType = "Scheduling Rules";
            String mode = "edit";
            String templateName = "AutoADVDynamicGroup";
            String workRole = "AMBASSADOR";
            List<String> days = new ArrayList<String>(){{
                add("Sunday");
                add("Friday");
            }};

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.clickOnConfigurationCrad(templateType);
            configurationPage.clickOnSpecifyTemplateName(templateName,mode);
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.verifyDynamicGroupOfAdvanceStaffingRuleIsOptional(workRole,days);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    //https://legiontech.atlassian.net/browse/OPS-5643; https://legiontech.atlassian.net/browse/OPS-6254
    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Advance staffing rule dynamic group E2E flow")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class,enabled = false)
    public void verifyAdvanceStaffingRulesDynamicGroupE2EAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
        try{
            String locationName1 = "AutoADVDynamicGroup02";
            String locationName2 = "AutoADVDynamicGroup03";
            List<String> days1 = new ArrayList<String>(){{
                add("Sunday");
                add("Friday");
            }};
            List<String> days2 = new ArrayList<String>(){{
                add("Monday");
                add("Tuesday");
                add("Wednesday");
            }};
            List<String> daysAbbr1 = new ArrayList<String>();
            List<String> daysHasShifts1 = new ArrayList<String>();
            List<String> daysAbbr2 = new ArrayList<String>();
            List<String> daysHasShifts2 = new ArrayList<String>();

            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
            ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            //Back to console
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.Console.getValue());
            locationSelectorPage.changeUpperFieldsByMagnifyGlassIcon(locationName1);
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
            daysHasShifts1 = scheduleShiftTablePage.verifyDaysHasShifts();

            for(String day:days1){
                String dayAbbr = day.substring(0,3);
                daysAbbr1.add(dayAbbr);
            }
            Collections.sort(daysHasShifts1);
            Collections.sort(daysAbbr1);
            if(days1.size()==daysHasShifts1.size()){
                if(ListUtils.isEqualList(daysAbbr1,daysHasShifts1)){
                    SimpleUtils.pass("User can create shifts correctly according to AVD staffing rule");
                }else {
                    SimpleUtils.fail("User can't create correct shifts according to AVD staffing rule",false);
                }
            }
            //switch to another location to check the shifts
            locationSelectorPage.changeUpperFieldsByMagnifyGlassIcon(locationName2);
            //go to schedule function
            scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
            // Navigate to a week
            scheduleCommonPage.navigateToNextWeek();
            scheduleCommonPage.navigateToNextWeek();
            // create the schedule if not created
            boolean isWeekGenerated1 = createSchedulePage.isWeekGenerated();
            if (isWeekGenerated1){
                createSchedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            createSchedulePage.createScheduleForNonDGFlowNewUIWithoutUpdateOH();
            daysHasShifts2 = scheduleShiftTablePage.verifyDaysHasShifts();

            for(String day:days2){
                String dayAbbr = day.substring(0,3);
                daysAbbr2.add(dayAbbr);
            }
            Collections.sort(daysHasShifts2);
            Collections.sort(daysAbbr2);
            if(days2.size()==daysHasShifts2.size()){
                if(ListUtils.isEqualList(daysAbbr2,daysHasShifts2)){
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
    @Enterprise(name = "opauto")
    @TestName(description = "Work Roles hourly rate regression test")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void workRolesHourlyRateRegressionTestAsInternalAdmin(String username, String password, String browser, String location) throws Exception {
        try {
            String hourlyRate = "15";
            String locationName="AutoADVDynamicGroup02";
            String workRoleName="AMBASSADOR";
            //Turn off WorkRoleSettingsTemplateOP toggle
            ToggleAPI.updateToggle(Toggles.WorkRoleSettingsTemplateOP.getValue(), "fiona+99@legion.co", "admin11.a", false);

            //Verify user can update hourly rate in work role details page
            UserManagementPage userManagementPage = pageFactory.createOpsPortalUserManagementPage();
            userManagementPage.clickOnUserManagementTab();
            userManagementPage.goToUserAndRoles();
            userManagementPage.verifyBackBtnIsClickable();
            userManagementPage.clickOnUserManagementTab();
            userManagementPage.goToWorkRolesTile();
            userManagementPage.updateWorkRoleHourlyRate(hourlyRate);
            //Verify location level hourly rate is read only
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.goToLocationDetailsPage(locationName);
            locationsPage.goToConfigurationTabInLocationLevel();
            locationsPage.clickActionsForTemplate("Assignment Rules", "Edit");
            userManagementPage.verifySearchWorkRole(workRoleName);
            userManagementPage.goToWorkRolesDetails(workRoleName);
            //userManagementPage.verifyLocationLevelHourlyRateIsReadOnly();
        }catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "opauto")
    @TestName(description = "Work role hourly rate is NOT showing on work role details page when toggle is on")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void workRolesHourlyRateIsNOTShowingWhenToggleIsOnAsInternalAdmin(String username, String password, String browser, String location) throws Exception {
        try {
            String locationName="AutoADVDynamicGroup02";
            String workRoleName="AMBASSADOR";
            //Turn on WorkRoleSettingsTemplateOP toggle
            ToggleAPI.updateToggle(Toggles.WorkRoleSettingsTemplateOP.getValue(), "fiona+99@legion.co", "admin11.a", true);

            //Verify user can update hourly rate in work role details page
            UserManagementPage userManagementPage = pageFactory.createOpsPortalUserManagementPage();
            userManagementPage.clickOnUserManagementTab();
            userManagementPage.goToUserAndRoles();
            userManagementPage.verifyBackBtnIsClickable();
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickOnLocationsTab();

            userManagementPage.clickOnUserManagementTab();
            userManagementPage.goToWorkRolesTile();
            userManagementPage.goToWorkRolesDetails(workRoleName);
            userManagementPage.hourlyRateFieldIsNotShowing();

            //Verify location level hourly rate is read only
            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.goToLocationDetailsPage(locationName);
            locationsPage.goToConfigurationTabInLocationLevel();
            locationsPage.clickActionsForTemplate("Assignment Rules", "Edit");
            userManagementPage.verifySearchWorkRole(workRoleName);
            userManagementPage.goToWorkRolesDetails(workRoleName);
            userManagementPage.hourlyRateFieldIsNotShowing();
        }catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "opauto")
    @TestName(description = "Work role template common checking")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void workRolesTemplateCommonCheckingAsInternalAdmin(String username, String password, String browser, String location) throws Exception {
        try {
            String templateName ="Default";
            String mode = "edit";
            //Turn on WorkRoleSettingsTemplateOP toggle
            ToggleAPI.updateToggle(Toggles.WorkRoleSettingsTemplateOP.getValue(), "fiona+99@legion.co", "admin11.a", true);

            UserManagementPage userManagementPage = pageFactory.createOpsPortalUserManagementPage();
            userManagementPage.clickOnUserManagementTab();
            userManagementPage.goToUserAndRoles();

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.goToWorkRoleSettingsTile();
            configurationPage.verifyWorkRoleSettingsTemplateListUIAndDetailsUI(templateName,mode);
        }catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "opauto")
    @TestName(description = "Create work role setting template")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void createWorkRolesSettingTemplateAsInternalAdmin(String username, String password, String browser, String location) throws Exception {
        try {
            String templateName ="Default";
            String mode = "Edit";
            SimpleDateFormat dfs=new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime=dfs.format(new Date()).trim();
            String templateName1="AutoCreate"+currentTime;
            String dynamicGpName = "AutoCreateDynamicGp"+currentTime;
            HashMap<String,String> workRoleHourlyRate= new HashMap<String,String>();
            workRoleHourlyRate.put("WRSAuto1","12");
            workRoleHourlyRate.put("WRSAuto2","15");
            workRoleHourlyRate.put("WRSAuto3","16");
            HashMap<String,String> workRoleHourlyRateInTemplate= new HashMap<String,String>();
            String workRole ="WRSAuto3";
            String updateValue="22";
            int date = 14;

            //Turn on WorkRoleSettingsTemplateOP toggle
            ToggleAPI.updateToggle(Toggles.WorkRoleSettingsTemplateOP.getValue(), "fiona+99@legion.co", "admin11.a", true);


            //go to user management -> work roles page to check the count of the work roles
            UserManagementPage userManagementPage = pageFactory.createOpsPortalUserManagementPage();
            userManagementPage.clickOnUserManagementTab();
            userManagementPage.goToWorkRolesTile();
            int workRoleCount = userManagementPage.getTotalWorkRoleCount();

//            //go to WRS template details page to check the count is equal with above count or not
            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.goToConfigurationPage();
            configurationPage.goToWorkRoleSettingsTile();
            configurationPage.verifyWorkRoleSettingsTemplateListUIAndDetailsUI(templateName,mode);
            configurationPage.checkWorkRoleListShowingWell(workRoleCount);

            //Create new WRS template
            configurationPage.goToConfigurationPage();
            configurationPage.goToWorkRoleSettingsTile();
            configurationPage.publishNewTemplate(templateName1,dynamicGpName,"Custom","AutoCreatedDynamic---Format Script"+currentTime);

            //Go to the new created template to check the default hourly rate
            configurationPage.clickOnSpecifyTemplateName(templateName1,mode);
            List<String> workRoles = workRoleHourlyRate.keySet().stream().collect(Collectors.toList());
            workRoleHourlyRateInTemplate = configurationPage.getDefaultHourlyRate(workRoles);
            if(workRoleHourlyRateInTemplate.equals(workRoleHourlyRate)){
                SimpleUtils.pass("The default value is correct");
            }else {
                SimpleUtils.fail("The default value is NOT correct",false);
            }

            //update the hourly rate
            configurationPage.clickOnEditButtonOnTemplateDetailsPage();
            configurationPage.updateWorkRoleHourlyRate(workRole,updateValue);
            configurationPage.chooseSaveOrPublishBtnAndClickOnTheBtn("publish now");

            //create work role settings template and publish at different time
            configurationPage.createFutureWRSTemplateBasedOnExistingTemplate(templateName1,"publish at different time",date,"edit");
        }catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "update and reset location level work role settings template")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyOverriddenLocationLevelWorkRoleSettingsAsInternalAdmin (String browser, String username, String password, String location) throws Exception {

        try {
            String locationName = "WorkRoleSettings";
            String workRole ="WRSAuto3";
            Random random=new Random();
            int number=random.nextInt(90)+10;
            String updateValue=String.valueOf(number);

            //Turn on WorkRoleSettingsTemplateOP toggle
            ToggleAPI.updateToggle(Toggles.WorkRoleSettingsTemplateOP.getValue(), "fiona+99@legion.co", "admin11.a", true);


            //go to user management -> work roles page to check the count of the work roles
            UserManagementPage userManagementPage = pageFactory.createOpsPortalUserManagementPage();
            userManagementPage.clickOnUserManagementTab();
            userManagementPage.goToWorkRolesTile();

            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickOnLocationsTab();
            locationsPage.goToSubLocationsInLocationsPage();
            locationsPage.goToLocationDetailsPage(locationName);
            locationsPage.goToConfigurationTabInLocationLevel();
            locationsPage.clickActionsForTemplate("Work Role Settings", "View");
            locationsPage.backToConfigurationTabInLocationLevel();
            locationsPage.clickActionsForTemplate("Work Role Settings", "Edit");

            ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
            configurationPage.updateWorkRoleHourlyRate(workRole,updateValue);
            configurationPage.saveBtnIsClickable();
            locationsPage.verifyOverrideStatusAtLocationLevel("Work Role Settings", "Yes");
            // there is no reset button for location level work role setting template
//            locationsPage.clickActionsForTemplate("Work Role Settings", "Reset");
//            locationsPage.verifyOverrideStatusAtLocationLevel("Work Role Settings", "No");
        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}