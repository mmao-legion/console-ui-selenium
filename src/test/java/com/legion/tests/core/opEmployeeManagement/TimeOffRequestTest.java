package com.legion.tests.core.opEmployeeManagement;

import com.legion.pages.TeamPage;
import com.legion.pages.core.OpCommons.*;
import com.legion.pages.core.opemployeemanagement.AbsentManagePage;
import com.legion.pages.core.opemployeemanagement.EmployeeManagementPanelPage;
import com.legion.pages.core.opemployeemanagement.TimeOffPage;
import com.legion.pages.core.opemployeemanagement.TimeOffReasonConfigurationPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.DBConnection;
import com.legion.utils.SimpleUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

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
    public void verifyEmployeeCanRequestAsInternalAdminOfTimeOffRequestTest(String browser, String username, String password, String location) throws Exception {
        OpsPortalNavigationPage navigationPage = new OpsPortalNavigationPage();
        navigationPage.navigateToEmployeeManagement();
        EmployeeManagementPanelPage panelPage = new EmployeeManagementPanelPage();
        panelPage.goToTimeOffManagementPage();
        AbsentManagePage absentManagePage = new AbsentManagePage();
        //search template
        String tempName = "AccrualAuto-Don't touch!!!";
        absentManagePage.configureTemplate(tempName);
        //template details page
        //configure time off reason1
        absentManagePage.removeTimeOffRules("Annual Leave");
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
        configurationPage.setValueForTimeOffRequestRules("Probation Period", "1900");
        configurationPage.setProbationUnitAs("Days");
        configurationPage.setValueForTimeOffRequestRules("Annual Use Limit", "9999");
        configurationPage.addSpecifiedServiceLever(0, "12", "3", "15");
        configurationPage.saveTimeOffConfiguration(true);

        //configure time off reason2
        absentManagePage.removeTimeOffRules("Floating Holiday");
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
        configurationPage.addSpecifiedServiceLever(0, "12", "3", "15");
        configurationPage.saveTimeOffConfiguration(true);


        //configure time off reason3
        absentManagePage.removeTimeOffRules("PTO");
        absentManagePage.configureTimeOffRules("PTO");
        //set value for request rules
        configurationPage.setTimeOffRequestRuleAs("Employee can request ?", true);
        configurationPage.setTimeOffRequestRuleAs("Employee can request partial day ?", false);
        configurationPage.setTimeOffRequestRuleAs("Manager can submit in timesheet ?", false);
        configurationPage.setValueForTimeOffRequestRules("Weekly limits(hours)", "20");
        configurationPage.setValueForTimeOffRequestRules("Days request must be made in advance", "2");
        configurationPage.setValueForTimeOffRequestRules("Configure all day time off default", "7");
        configurationPage.setValueForTimeOffRequestRules("Days an employee can request at one time", "3");
        configurationPage.setTimeOffRequestRuleAs("Auto reject time off which exceed accrued hours ?", false);
        configurationPage.setTimeOffRequestRuleAs("Allow Paid Time Off to compute to overtime ?", false);
        configurationPage.setTimeOffRequestRuleAs("Does this time off reason track Accruals ?", false);
        configurationPage.setValueForTimeOffRequestRules("Max hours in advance of what you earn", "8");
        configurationPage.setTimeOffRequestRuleAs("Enforce Yearly Limits", true);
        configurationPage.setValueForTimeOffRequestRules("Probation Period", "3");
        configurationPage.setProbationUnitAsMonths();
        configurationPage.setValueForTimeOffRequestRules("Annual Use Limit", "10");
        configurationPage.addSpecifiedServiceLever(0, "12", "3", "20");
        configurationPage.saveTimeOffConfiguration(true);

        //configure time off reason4
        absentManagePage.removeTimeOffRules("Sick");
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
        configurationPage.addSpecifiedServiceLever(0, "12", "3", "15");
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
        timeOffPage.goToTeamMemberDetail("Allene Mante");
        timeOffPage.switchToTimeOffTab();
        //Edit annual leave balance
        timeOffPage.editTimeOffBalance("Annual Leave", "5");
        commonComponents.okToActionInModal(true);
        //request rules validation
        //annual Leave    --can request true   track accrual---true
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
        //in probation period
        commonComponents.okToActionInModal(false);
        timeOffPage.createTimeOff("Annual Leave", true, 3, 3);
        commonComponents.okToActionInModal(true);
        Assert.assertEquals(timeOffPage.getRequestErrorMessage(), "Time off request cannot be submitted during probation period");
        //out of probation period
        commonComponents.okToActionInModal(false);
        timeOffPage.createTimeOff("PTO", false, 3, 3);//-7hours
        commonComponents.okToActionInModal(true);

        //Assert.assertEquals(timeOffPage.getRequestErrorMessage(), "Time off request cannot be submitted during probation period");
        /*//get the employee workerId
        String workId = getWorkId();
        //delete time off request
        deleteRequestedTimeOffDateByWorkerId(workId);*/


        //4 Weekly limits(hours)  request 2 days 2*7=14; 7hours have been requested last time,3*7=21 hours, more than weekly limit 20 hours;
        /*commonComponents.okToActionInModal(false);*/
        timeOffPage.createTimeOff("PTO", true, 16, 17);
        commonComponents.okToActionInModal(true);
        Assert.assertEquals(timeOffPage.getRequestErrorMessage(), "Above weekly max");

        //8 Auto reject time off which exceed accrued hours ? request 1 days 6 hours; for the auto reject is yes and only have 5 hours balance;
        commonComponents.okToActionInModal(false);
        timeOffPage.createTimeOff("Annual Leave", false, 17, 17);
        commonComponents.okToActionInModal(true);
        Assert.assertEquals(timeOffPage.getRequestErrorMessage(), "Not enough accrued hours for Annual Leave");
        //7 Days an employee can request at one time
        //5 Days request must be made in advance
        //3 Manager can submit in timesheet ?
        //11 Max hours in advance of what you earn
        //12 Enforce Yearly Limits
        //14 Annual Use Limit   //
        commonComponents.okToActionInModal(false);
        timeOffPage.createTimeOff("PTO", false, 20, 22);
        commonComponents.okToActionInModal(true);
        Assert.assertEquals(timeOffPage.getRequestErrorMessage(), "Time off request exceeds available year limit");
        commonComponents.okToActionInModal(false);

        switchToNewWindow();
        //search template
        absentManagePage.configureTemplate(tempName);
        //template details page

        //configure time off reason1
        absentManagePage.configureTimeOffRules("Annual Leave");
        //set value for request rules
        configurationPage.setValueForTimeOffRequestRules("Weekly limits(hours)", "999");
        configurationPage.setValueForTimeOffRequestRules("Days request must be made in advance", "2");
        configurationPage.setValueForTimeOffRequestRules("Configure all day time off default", "7");
        configurationPage.setValueForTimeOffRequestRules("Days an employee can request at one time", "2");
        configurationPage.setTimeOffRequestRuleAs("Auto reject time off which exceed accrued hours ?", false);
        configurationPage.setValueForTimeOffRequestRules("Max hours in advance of what you earn", "8");
        configurationPage.setTimeOffRequestRuleAs("Enforce Yearly Limits", false);
        configurationPage.setValueForTimeOffRequestRules("Annual Use Limit", "4");
        configurationPage.saveTimeOffConfiguration(true);

        //ask for 2 days leave , failed , 1day passed,(default hours,)


        //configure time off reason2
        absentManagePage.configureTimeOffRules("Sick");
        //set value for request rules
        configurationPage.setTimeOffRequestRuleAs("Employee can request ?", true);
        configurationPage.setValueForTimeOffRequestRules("Weekly limits(hours)", "40");
        configurationPage.setValueForTimeOffRequestRules("Days request must be made in advance", "2");
        configurationPage.setValueForTimeOffRequestRules("Configure all day time off default", "7");
        configurationPage.setValueForTimeOffRequestRules("Days an employee can request at one time", "2");
        configurationPage.setTimeOffRequestRuleAs("Auto reject time off which exceed accrued hours ?", false);
        configurationPage.setValueForTimeOffRequestRules("Max hours in advance of what you earn", "8");
        configurationPage.setTimeOffRequestRuleAs("Enforce Yearly Limits", false);
        configurationPage.setValueForTimeOffRequestRules("Annual Use Limit", "4");
        configurationPage.addSpecifiedServiceLever(0, "12", "3", "15");
        configurationPage.saveTimeOffConfiguration(true);

        //configure time off reason3
        absentManagePage.configureTimeOffRules("PTO");
        //set value for request rules
        configurationPage.setTimeOffRequestRuleAs("Employee can request ?", true);
        configurationPage.setValueForTimeOffRequestRules("Weekly limits(hours)", "40");
        configurationPage.setValueForTimeOffRequestRules("Days request must be made in advance", "2");
        configurationPage.setValueForTimeOffRequestRules("Configure all day time off default", "7");
        configurationPage.setValueForTimeOffRequestRules("Days an employee can request at one time", "5");
        configurationPage.setTimeOffRequestRuleAs("Auto reject time off which exceed accrued hours ?", false);
        configurationPage.setValueForTimeOffRequestRules("Max hours in advance of what you earn", "8");
        configurationPage.setTimeOffRequestRuleAs("Enforce Yearly Limits", false);
        configurationPage.setValueForTimeOffRequestRules("Probation Period", "3");
        configurationPage.setProbationUnitAsMonths();
        configurationPage.setValueForTimeOffRequestRules("Annual Use Limit", "3");
        configurationPage.saveTimeOffConfiguration(true);

        //publish
        absentManagePage.saveTemplateAs("Publish now");
        SimpleUtils.pass("Succeeded in creating template: " + tempName + " !");

        //switch to console.
        switchToNewWindow();
        consoleNavigationPage.navigateTo("Team");
        timeOffPage.goToTeamMemberDetail("Allene Mante");
        timeOffPage.switchToTimeOffTab();

        //Days request at one time.
        timeOffPage.createTimeOff("Sick", false, 20, 22);
        commonComponents.okToActionInModal(true);
        Assert.assertEquals(timeOffPage.getRequestErrorMessage(), "Exceeds Sick request limit");
        commonComponents.okToActionInModal(false);

        //Annual Use Limit
        timeOffPage.createTimeOff("PTO", false, 20, 23);
        commonComponents.okToActionInModal(true);
        Assert.assertEquals(timeOffPage.getRequestErrorMessage(), "Time off request exceeds annual use limit");
        commonComponents.okToActionInModal(false);

        //Max hours in advance of what you earn
        //5+8=13hours, not enough for 2 days(2*7=14)
        timeOffPage.createTimeOff("Annual Leave", false, 20, 21);
        commonComponents.okToActionInModal(true);
        Assert.assertEquals(timeOffPage.getRequestErrorMessage(), "Not enough accrued hours for Annual Leave");
        commonComponents.okToActionInModal(false);

        //5+8=13hours, enough for 1 days(1*7=7)
        timeOffPage.createTimeOff("Annual Leave", false, 20, 20);
        commonComponents.okToActionInModal(true);
        //successful
        //6 Configure all day time off default: 7hours, 5+8=13hours, 13-7=6
        Assert.assertTrue(timeOffPage.getAnnualLeaveBalance().equals("6"), "Incorrect balance!");
        timeOffPage.rejectTimeOffRequest();
        consoleNavigationPage.navigateTo("Logout");
    }

    public String getWorkId() {
        String url = getUrl();
        String workId = url.substring(url.lastIndexOf("/") + 1);
        System.out.println(workId);
        return workId;
    }


    public void deleteRequestedTimeOffDateByWorkerId(String workerId) {
        //delete the Access Role just create successfully.
        String enterpriseId = "aee2dfb5-387d-4b8b-b3f5-62e86d1a9d95";
        String sql1 = "delete from legionrc.TimeOffRequest where workerId='" + workerId + "' and enterpriseId='" + enterpriseId + "'";
        System.out.println("sql1： "+sql1);
        String sql2 = "delete from legionrc.TimeOffRequestHistory where workerId='" + workerId + "' and enterpriseId='" + enterpriseId + "'";
        System.out.println("sql2： "+sql2);
        String sql3 = "delete from legionrc.WorkerAccrualHistory where workerId='" + workerId + "' and enterpriseId='" + enterpriseId + "'";
        System.out.println("sql3： "+sql3);
        DBConnection.updateDB(sql1);
        DBConnection.updateDB(sql2);
        DBConnection.updateDB(sql3);
        String queryResult1 = DBConnection.queryDB("legionrc.TimeOffRequest", "objectId", "workerId='" + workerId + "' and enterpriseId='" + enterpriseId + "'");
        Assert.assertEquals(queryResult1, "No item returned!", "Failed to clear the data just generated in DB!");
        String queryResult2 = DBConnection.queryDB("legionrc.TimeOffRequestHistory", "objectId", "workerId='" + workerId + "' and enterpriseId='" + enterpriseId + "'");
        Assert.assertEquals(queryResult2, "No item returned!", "Failed to clear the data just generated in DB!");
        String queryResult3 = DBConnection.queryDB("legionrc.WorkerAccrualHistory", "objectId", "workerId='" + workerId + "' and enterpriseId='" + enterpriseId + "'");
        Assert.assertEquals(queryResult3, "No item returned!", "Failed to clear the data just generated in DB!");
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Nancy")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Time off activity")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTimeOffActivityAsInternalAdminOfTimeOffRequestTest(String browser, String username, String password, String location) throws Exception {
        // delete created time off
        deleteRequestedTimeOffDateByWorkerId("648acc0c-64e7-4458-ad95-4a72057cb17b");

        // delete activity
        String sql = "delete from legionrc.Activity where createdBy = 'e335a98b-c0fb-42b0-ba2f-d9b24ea346d3' and enterpriseId = 'aee2dfb5-387d-4b8b-b3f5-62e86d1a9d95'";
        DBConnection.updateDB(sql);

        String queryResult = DBConnection.queryDB("legionrc.Activity", "objectId", "createdBy = 'e335a98b-c0fb-42b0-ba2f-d9b24ea346d3' and enterpriseId = 'aee2dfb5-387d-4b8b-b3f5-62e86d1a9d95'");
        Assert.assertEquals(queryResult, "No item returned!", "Failed to clear the data just generated in DB!");

        // Verify activity doesn't display for admin
        OpsPortalNavigationPage navigationPage = new OpsPortalNavigationPage();
        ActivityPage activityPage = new ActivityPage();
        activityPage.switchToNewWindow();
        Assert.assertEquals(activityPage.verifyActivityDisplay(), false);

        //create approved time off for tm
        ConsoleNavigationPage consoleNavigationPage = new ConsoleNavigationPage();
        consoleNavigationPage.searchLocation("verifyMock");
        TeamPage teamPage = pageFactory.createConsoleTeamPage();
        teamPage.goToTeam();
        teamPage.searchAndSelectTeamMemberByName("Nancy TM");
        TimeOffPage timeOffPage = new TimeOffPage();
        timeOffPage.switchToTimeOffTab();
        OpsCommonComponents commonComponents = new OpsCommonComponents();
        timeOffPage.createTimeOff("Annual Leave",false,27,27);
        commonComponents.okToActionInModal(true);

        navigationPage.logout();
        // Verify activity doesn't display for team member
        loginToLegionAndVerifyIsLoginDoneWithoutUpdateUpperfield("nancy.nan+tm@legion.co", "admin11.a","verifyMock");
        OpsPortalNavigationPage navigationPage1 = new OpsPortalNavigationPage();
        Assert.assertEquals(activityPage.verifyActivityDisplay(), false);
        consoleNavigationPage.searchLocation("verifyMock");

        teamPage.goToTeam();
        teamPage.searchAndSelectTeamMemberByName("Nancy TM");

        timeOffPage.switchToTimeOffTab();
        timeOffPage.createTimeOff("Annual Leave",false,28,28);
        commonComponents.okToActionInModal(true);
        timeOffPage.createTimeOff("Annual Leave",false,29,29);
        commonComponents.okToActionInModal(true);
        timeOffPage.createTimeOff("Annual Leave",false,30,30);
        commonComponents.okToActionInModal(true);
        RightHeaderBarPage rightHeaderBarPage = new RightHeaderBarPage();
        rightHeaderBarPage.navigateToTimeOff();
        timeOffPage.cancelCreatedTimeOffRequest();
        navigationPage1.logout();
        // Verify activity  display for store member
        loginToLegionAndVerifyIsLoginDoneWithoutUpdateUpperfield("nancy.nan+sm@legion.co", "admin11.a","verifyMock");
        Assert.assertEquals(activityPage.verifyActivityDisplay(), true);

        //search and go to the target location
        consoleNavigationPage.searchLocation("verifyMock");

        // click activity
        Assert.assertEquals(activityPage.verifyActivityBoxDisplay(), true);
        activityPage.clickTimeOff();
        activityPage.clickTimeOffDetail();

        //verify approve is clickable
        activityPage.verifyApproveIsClickable();

        //verify reject is clickable
        activityPage.verifyRejectIsClickable();

        //verify activity title
        activityPage.verifyActivityTitle();

        //verify time off status
        activityPage.verifyActivityTimeOffStatus();

        //verify first activity is cancelled
        activityPage.verifyCancel();

        //approve second activity and verify it's approved
        activityPage.approveActivityTimeOff();
        activityPage.verifyApprove();

        //reject third activity and verify it's rejected
        activityPage.rejectActivityTimeOff();
        activityPage.verifyReject();

        OpsPortalNavigationPage navigationPage2 = new OpsPortalNavigationPage();
        navigationPage2.logout();

        //tm login to check its time off status have changed
        loginToLegionAndVerifyIsLoginDoneWithoutUpdateUpperfield("nancy.nan+tm@legion.co", "admin11.a","verifyMock");
        consoleNavigationPage.searchLocation("verifyMock");
        rightHeaderBarPage.navigateToTimeOff();
        timeOffPage.verifyTimeOffStatus();
    }
}
