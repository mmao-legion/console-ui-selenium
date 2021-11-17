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
        //search template
        String tempName = "AccrualAuto-Don't touch!!!";
        absentManagePage.configureTemplate(tempName);
        //template details page
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
        configurationPage.setTimeOffRequestRuleAs("Enforce Yearly Limits", false);
        configurationPage.setValueForTimeOffRequestRules("Probation Period", "190");
        configurationPage.setProbationUnitAs("Days");
        configurationPage.setValueForTimeOffRequestRules("Annual Use Limit", "9999");
        configurationPage.setMaxAvailableHours("120");
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
        configurationPage.setTimeOffRequestRuleAs("Does this time off reason track Accruals ?", true);
        configurationPage.setValueForTimeOffRequestRules("Max hours in advance of what you earn", "8");
        configurationPage.setTimeOffRequestRuleAs("Enforce Yearly Limits", false);
        configurationPage.setValueForTimeOffRequestRules("Probation Period", "3");
        configurationPage.setProbationUnitAsMonths();
        configurationPage.setValueForTimeOffRequestRules("Annual Use Limit", "4");
        configurationPage.saveTimeOffConfiguration(true);


        //configure time off reason3
        absentManagePage.configureTimeOffRules("PTO");
        //set value for request rules
        configurationPage.setTimeOffRequestRuleAs("Employee can request ?", true);
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
        configurationPage.saveTimeOffConfiguration(true);

        //configure time off reason4
        absentManagePage.configureTimeOffRules("Sick");
        //set value for request rules
        configurationPage.setTimeOffRequestRuleAs("Employee can request ?", false);
        configurationPage.setTimeOffRequestRuleAs("Employee can request partial day ?", true);
        configurationPage.setTimeOffRequestRuleAs("Manager can submit in timesheet ?", true);
        configurationPage.setValueForTimeOffRequestRules("Weekly limits(hours)", "24");
        configurationPage.setValueForTimeOffRequestRules("Days request must be made in advance", "2");
        configurationPage.setValueForTimeOffRequestRules("Configure all day time off default", "8");
        configurationPage.setValueForTimeOffRequestRules("Days an employee can request at one time", "3");
        configurationPage.setTimeOffRequestRuleAs("Auto reject time off which exceed accrued hours ?", true);
        configurationPage.setTimeOffRequestRuleAs("Allow Paid Time Off to compute to overtime ?", false);
        configurationPage.setTimeOffRequestRuleAs("Does this time off reason track Accruals ?", false);
        configurationPage.setValueForTimeOffRequestRules("Max hours in advance of what you earn", "8");
        configurationPage.setTimeOffRequestRuleAs("Enforce Yearly Limits", false);
        configurationPage.setValueForTimeOffRequestRules("Probation Period", "3");
        configurationPage.setProbationUnitAsMonths();
        configurationPage.setValueForTimeOffRequestRules("Annual Use Limit", "4");
        configurationPage.saveTimeOffConfiguration(true);

        //associate
        String dynamicGroupName = "Newark-for auto";
        absentManagePage.switchToAssociation();
        OpsCommonComponents commonComponents = new OpsCommonComponents();
        commonComponents.associateWithDynamicGroups(dynamicGroupName);
        absentManagePage.switchToDetails();

        //can employee request yes
        absentManagePage.setTemplateLeverCanRequest(true);
        //weekly limit 40
        absentManagePage.setTemplateLeverWeeklyLimits("40");

        //publish
        absentManagePage.saveTemplateAs("Publish now");
        SimpleUtils.pass("Succeeded in creating template: " + tempName + " !");

        //switch to console.
        RightHeaderBarPage headerBarPage = new RightHeaderBarPage();
        headerBarPage.switchToNewTab();
        ConsoleNavigationPage consoleNavigationPage = new ConsoleNavigationPage();
        consoleNavigationPage.searchLocation("Newark-Mock");
        consoleNavigationPage.navigateTo("Team");
        TimeOffPage timeOffPage = new TimeOffPage();
        timeOffPage.goToTeamMemberDetail();
        timeOffPage.switchToTimeOffTab();
        //request rules validation

        //annualLeave    --can request true   track accrual---true
        //floatingHoliday--can request false  track accrual---true
        //PTO            --can request true   track accrual---false
        //Sick           --can request false  track accrual---false

        //10 Does this time off reason track Accruals ?
        timeOffPage.getTimeOffTypes();
        Assert.assertTrue(timeOffPage.getTimeOffTypes().contains("Annual Leave") && timeOffPage.getTimeOffTypes().contains("Floating Holiday"));
        Assert.assertFalse(timeOffPage.getTimeOffTypes().contains("PTO") && timeOffPage.getTimeOffTypes().contains("Sick"));

        //1 Employee can request ?
        //Annual Leave - Bal
        //PTO
        timeOffPage.checkTimeOffSelect();
        Assert.assertTrue(timeOffPage.getTimeOffOptions().get(0).contains("Annual Leave - Bal") && timeOffPage.getTimeOffOptions().contains("PTO"));
        Assert.assertFalse(timeOffPage.getTimeOffOptions().contains("PTO - Bal"));
        Assert.assertFalse(timeOffPage.getTimeOffOptions().contains("floatingHoliday") && timeOffPage.getTimeOffOptions().contains("Sick"));

        //2 Employee can request partial day ?
        //yes
        commonComponents.okToActionInModal(false);
        timeOffPage.selectTimeOff("Annual Leave");
        Assert.assertTrue(timeOffPage.isPartialDayEnabled(), "Failed to request partial day!!!");
        //no
        commonComponents.okToActionInModal(false);
        timeOffPage.selectTimeOff("PTO");
        Assert.assertFalse(timeOffPage.isPartialDayEnabled(), "The partial day should not be displayed as we set PTO can't request partial day!");

        //13 Probation Period
        commonComponents.okToActionInModal(false);
        timeOffPage.createTimeOff("Annual Leave", true, 3, 3);
        commonComponents.okToActionInModal(true);
        Assert.assertEquals(timeOffPage.getRequestErrorMessage(), "Time off request cannot be submitted during probation period");

        //4 Weekly limits(hours)  request 2 days 2*6=12; less than balance: 5+8=13, but more than weekly limit 10 hours;
        commonComponents.okToActionInModal(false);
        timeOffPage.createTimeOff("Annual Leave", true, 16, 17);
        commonComponents.okToActionInModal(true);
        Assert.assertEquals(timeOffPage.getRequestErrorMessage(), "Above weekly max");

        //8 Auto reject time off which exceed accrued hours ? request 1 days 6 hours; for the auto reject is yes and only have 5 hours balance;
        commonComponents.okToActionInModal(false);
        timeOffPage.createTimeOff("Annual Leave", false, 17, 17);
        commonComponents.okToActionInModal(true);
        Assert.assertEquals(timeOffPage.getRequestErrorMessage(), "Not enough accrued hours for Annual Leave");
        //7 Days an employee can request at one time
        //5 Days request must be made in advance
        //6 Configure all day time off default
        //3 Manager can submit in timesheet ?
        //11 Max hours in advance of what you earn
        //9 Allow Paid Time Off to compute to overtime ?
        //12 Enforce Yearly Limits
        //14 Annual Use Limit

        //logout
        //login with a TM credential
        //go to profile/time off
        //request time off
    }


}
