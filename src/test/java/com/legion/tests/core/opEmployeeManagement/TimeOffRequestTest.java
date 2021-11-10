package com.legion.tests.core.opEmployeeManagement;

import com.legion.pages.core.OpCommons.ConsoleNavigationPage;
import com.legion.pages.core.OpCommons.OpsCommonComponents;
import com.legion.pages.core.opemployeemanagement.AbsentManagePage;
import com.legion.pages.core.opemployeemanagement.EmployeeManagementPanelPage;
import com.legion.pages.core.OpCommons.RightHeaderBarPage;
import com.legion.pages.core.OpCommons.OpsPortalNavigationPage;
import com.legion.pages.core.opemployeemanagement.TimeOffPage;
import com.legion.pages.core.opemployeemanagement.TimeOffReasonConfigurationPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Random;

public class TimeOffRequestTest extends TestBase {

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception {
        this.createDriver((String) params[0], "83", "Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDoneWithoutUpdateUpperfield((String) params[1], (String) params[2], (String) params[3]);
        RightHeaderBarPage modelSwitchPage = new RightHeaderBarPage();
        modelSwitchPage.switchToOpsPortal();
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Sophia")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Time Off Request")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyEmployeeCanRequestAsInternalAdminForEmployeeManagement(String browser, String username, String password, String location) {
        OpsPortalNavigationPage navigationPage = new OpsPortalNavigationPage();
        navigationPage.navigateToEmployeeManagement();
        EmployeeManagementPanelPage panelPage = new EmployeeManagementPanelPage();
        panelPage.goToAbsentManagementPage();
        AbsentManagePage absentManagePage = new AbsentManagePage();

        //create a new template
        Random random = new Random();
        String tempName = "autoTemp" + random.nextInt(1000);
        absentManagePage.createANewTemplate(tempName, "automation used!");
        absentManagePage.submit();
        absentManagePage.closeWelcomeModal();
        //template details page
        //can employee request yes
        absentManagePage.setTemplateLeverCanRequest(true);
        //weekly limit 40
        absentManagePage.setTemplateLeverWeeklyLimits("40");
        //configure time off reason1
        absentManagePage.configureTimeOffRules("Annual Leave");
        TimeOffReasonConfigurationPage configurationPage = new TimeOffReasonConfigurationPage();
        //set value for request rules
        configurationPage.setTimeOffRequestRuleAs("Employee can request ?", true);
        configurationPage.setTimeOffRequestRuleAs("Employee can request partial day ?", true);
        configurationPage.setTimeOffRequestRuleAs("Manager can submit in timesheet ?", true);
        configurationPage.setValueForTimeOffRequestRules("Weekly limits(hours)", "10");
        configurationPage.setValueForTimeOffRequestRules("Days request must be made in advance", "2");
        configurationPage.setValueForTimeOffRequestRules("Configure all day time off default", "6");
        configurationPage.setValueForTimeOffRequestRules("Days an employee can request at one time", "2");
        configurationPage.setTimeOffRequestRuleAs("Auto reject time off which exceed accrued hours ?", true);
        configurationPage.setTimeOffRequestRuleAs("Allow Paid Time Off to compute to overtime ?", true);
        configurationPage.setTimeOffRequestRuleAs("Does this time off reason track Accruals ?", true);
        configurationPage.setValueForTimeOffRequestRules("Max hours in advance of what you earn", "8");
        configurationPage.setTimeOffRequestRuleAs("Enforce Yearly Limits", true);
        configurationPage.setValueForTimeOffRequestRules("Probation Period", "3");
        configurationPage.setProbationUnitAsMonths();
        configurationPage.setValueForTimeOffRequestRules("Annual Use Limit", "5");

        configurationPage.addServiceLever();
        configurationPage.setMaxAvailableHours("15");
        configurationPage.saveTimeOffConfiguration(true);

        //configure time off reason2
        absentManagePage.configureTimeOffRules("Floating Holiday");
        //set value for request rules
        configurationPage.setTimeOffRequestRuleAs("Employee can request ?", false);
        configurationPage.setTimeOffRequestRuleAs("Employee can request partial day ?", false);
        configurationPage.setTimeOffRequestRuleAs("Manager can submit in timesheet ?", false);
        configurationPage.setValueForTimeOffRequestRules("Weekly limits(hours)", "7");
        configurationPage.setValueForTimeOffRequestRules("Days request must be made in advance", "2");
        configurationPage.setValueForTimeOffRequestRules("Configure all day time off default", "8");
        configurationPage.setValueForTimeOffRequestRules("Days an employee can request at one time", "3");
        configurationPage.setTimeOffRequestRuleAs("Auto reject time off which exceed accrued hours ?", false);
        configurationPage.setTimeOffRequestRuleAs("Allow Paid Time Off to compute to overtime ?", false);
        configurationPage.setTimeOffRequestRuleAs("Does this time off reason track Accruals ?", false);
        configurationPage.setValueForTimeOffRequestRules("Max hours in advance of what you earn", "8");
        configurationPage.setTimeOffRequestRuleAs("Enforce Yearly Limits", false);
        configurationPage.setValueForTimeOffRequestRules("Probation Period", "3");
        configurationPage.setProbationUnitAsMonths();
        configurationPage.setValueForTimeOffRequestRules("Annual Use Limit", "4");

        configurationPage.addServiceLever();
        configurationPage.saveTimeOffConfiguration(true);

        //configure time off reason3
        absentManagePage.configureTimeOffRules("Sick");
        //set value for request rules
        configurationPage.setTimeOffRequestRuleAs("Employee can request ?", false);
        configurationPage.setTimeOffRequestRuleAs("Employee can request partial day ?", false);
        configurationPage.setTimeOffRequestRuleAs("Manager can submit in timesheet ?", false);
        configurationPage.setValueForTimeOffRequestRules("Weekly limits(hours)", "24");
        configurationPage.setValueForTimeOffRequestRules("Days request must be made in advance", "2");
        configurationPage.setValueForTimeOffRequestRules("Configure all day time off default", "8");
        configurationPage.setValueForTimeOffRequestRules("Days an employee can request at one time", "3");
        configurationPage.setTimeOffRequestRuleAs("Auto reject time off which exceed accrued hours ?", false);
        configurationPage.setTimeOffRequestRuleAs("Allow Paid Time Off to compute to overtime ?", false);
        configurationPage.setTimeOffRequestRuleAs("Does this time off reason track Accruals ?", false);
        configurationPage.setValueForTimeOffRequestRules("Max hours in advance of what you earn", "8");
        configurationPage.setTimeOffRequestRuleAs("Enforce Yearly Limits", false);
        configurationPage.setValueForTimeOffRequestRules("Probation Period", "3");
        configurationPage.setProbationUnitAsMonths();
        configurationPage.setValueForTimeOffRequestRules("Annual Use Limit", "4");

        configurationPage.addServiceLever();
        configurationPage.saveTimeOffConfiguration(true);

        //draft the template
        absentManagePage.saveTemplateAs("Save as draft");

        //associate
        absentManagePage.configureTemplate(tempName);
        String dynamicGroupName = "Newark-for auto";
        absentManagePage.switchToAssociation();
        OpsCommonComponents commonComponents = new OpsCommonComponents();
        commonComponents.associateWithDynamicGroups(dynamicGroupName);
        absentManagePage.switchToDetails();

        //publish
        absentManagePage.saveTemplateAs("Publish now");
        SimpleUtils.pass("Succeeded in creating template: " + tempName + " !");

        //switch to console.
        //OpsCommonComponents commonComponents=new OpsCommonComponents();//added for debug ,need to delete
        RightHeaderBarPage headerBarPage = new RightHeaderBarPage();
        headerBarPage.switchToNewTab();
        ConsoleNavigationPage consoleNavigationPage = new ConsoleNavigationPage();
        consoleNavigationPage.searchLocation("Newark-Mock");
        consoleNavigationPage.navigateTo("Team");
        TimeOffPage timeOffPage = new TimeOffPage();
        timeOffPage.goToTeamMemberDetail();
        timeOffPage.switchToTimeOffTab();
        timeOffPage.checkTimeOffSelect();
        Assert.assertTrue(timeOffPage.isTimeOffDisplayedInSelectList("Annual Leave"), "Failed to validate rule: Employee can request ?");
        commonComponents.okToActionInModal(false);
        timeOffPage.createTimeOff("Annual Leave", true, "both", 12, 12);
        commonComponents.okToActionInModal(true);
        LocalDate date=LocalDate.now();



        //request time off to validate the request rules


        //configure it
        //request rules validation
        //1 Employee can request ?
        //2 Employee can request partial day ?

        //3 Manager can submit in timesheet ?

        //4 Weekly limits(hours)

        //5 Days request must be made in advance

        //6 Configure all day time off default

        //7 Days an employee can request at one time

        //8 Auto reject time off which exceed accrued hours ?

        //9 Allow Paid Time Off to compute to overtime ?

        //10 Does this time off reason track Accruals ?

        //11 Max hours in advance of what you earn

        //12 Enforce Yearly Limits

        //13 Probation Period
        //14 Annual Use Limit

        //save
        //publish now
        //logout
        //login with a TM credential
        //go to profile/time off
        //request time off


    }


}
