package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.legion.utils.MyThreadLocal.setJobName;


public class UserManagementTest extends TestBase {

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
        loginToLegionAndVerifyIsLoginDoneWithoutUpdateUpperfield((String)params[1], (String)params[2],(String)params[3]);
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

    }
    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify add and update work role")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAddUpdateWorkRole(String browser, String username, String password, String location) throws Exception {
        try{
//            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
//            String currentTime =  dfs.format(new Date());
            String workRoleName = "ForAutomation";;
            String colour = "";
            String workRole = "Deployed";
            String hourlyRate = "0";
            String selectATeamMemberTitle = "Manager";
            String defineTheTimeWhenThisRuleApplies = "At all Hours";
            String specifyTheConditionAndNumber = "At least";
            String shiftNumber = "1";
            String defineTheTypeAndFrequencyOfTimeRequiredAndPriority = "Shifts";
            String priority = "0";

            UserManagementPage userManagementPage = pageFactory.createOpsPortalUserManagementPage();
            userManagementPage.clickOnUserManagementTab();
            userManagementPage.verifyWorkRolesTileDisplay();
            userManagementPage.goToWorkRolesTile();
            userManagementPage.verifyEditBtnIsClickable();
            userManagementPage.verifyBackBtnIsClickable();
            userManagementPage.goToWorkRolesTile();
            userManagementPage.cancelAddNewWorkRoleWithoutAssignmentRole(workRoleName,colour,workRole,hourlyRate);
            List<HashMap<String, String>> workRolesDetails =userManagementPage.getWorkRoleInfo(workRoleName);
            if (workRolesDetails!=null) {
                userManagementPage.updateWorkRole(workRoleName,colour,workRole,hourlyRate,selectATeamMemberTitle,defineTheTimeWhenThisRuleApplies,specifyTheConditionAndNumber,shiftNumber,defineTheTypeAndFrequencyOfTimeRequiredAndPriority,priority);
                userManagementPage.verifySearchWorkRole(workRoleName);
            }else {
                userManagementPage.addNewWorkRole(workRoleName, colour, workRole, hourlyRate, selectATeamMemberTitle, defineTheTimeWhenThisRuleApplies, specifyTheConditionAndNumber, shiftNumber, defineTheTypeAndFrequencyOfTimeRequiredAndPriority, priority);
                userManagementPage.verifySearchWorkRole(workRoleName);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }



}
