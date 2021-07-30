package com.legion.tests.core.opusermanagement;

import com.legion.pages.core.opusermanagement.*;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DynamicGroupsTest extends TestBase {
    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception {
        this.createDriver((String) params[0], "83", "Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDoneWithoutUpdateUpperfield((String) params[1], (String) params[2], (String) params[3]);
        ModelSwitchPage modelSwitchPage = new ModelSwitchPage();
        modelSwitchPage.switchToOpsPortal();
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Sophia")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Dynamic employee group validation")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDynamicEmployeeGroupAsInternalAdminForUserManagement(String browser, String username, String password, String location) throws Exception {
        try {
            OpsPortalNavigationPage navigationPage = new OpsPortalNavigationPage();
            navigationPage.navigateToUserManagement();
            OpsPortalUserManagementPanelPage panelPage = new OpsPortalUserManagementPanelPage();
            //get work role list
            panelPage.goToWorkRolesPage();
            OpsPortalWorkRolesPage workRolesPage = new OpsPortalWorkRolesPage();
            List<String> wrList1 = workRolesPage.getWorkRoleList();
            workRolesPage.goBack();
            //get badge list
            panelPage.goToUsersAndRoles();
            UsersAndRolesPage usersAndRolesPage = new UsersAndRolesPage();
            usersAndRolesPage.goToBadges();
            List<String> badgeList = usersAndRolesPage.getBadgeList();
            usersAndRolesPage.back();
            //verified dynamic groups
            panelPage.goToDynamicGroups();
            DynamicEmployeePage dynamicEmployeePage = new DynamicEmployeePage();
            dynamicEmployeePage.addGroup();
            Assert.assertEquals(dynamicEmployeePage.getModalTitle(), "Manage Dynamic Group", "Failed to open manage dynamic group modal");
            //work role
            List<String> wrList2 = dynamicEmployeePage.getCriteriaValues("Work Role");
            Assert.assertTrue(wrList1.size() == wrList2.size() && wrList1.containsAll(wrList2), "Failed to assert that work role options in dynamic group are in accord with Work role list in work roles");

            //employment type  Hourly/Salary - Eligible for Overtime/Salary - No Overtime
            ArrayList<String> empType = new ArrayList<String>();
            empType.add("Hourly");
            empType.add("Salary - Eligible for Overtime");
            empType.add("Salary - No Overtime");
            List<String> empType1 = dynamicEmployeePage.getCriteriaValues("Employment Type");
            Assert.assertTrue(empType1.size() == empType.size() && empType1.containsAll(empType), "The Employment Type value validate failed!");

            //employment status FullTime/PartTime
            ArrayList<String> empStatus = new ArrayList<String>();
            empStatus.add("FullTime");
            empStatus.add("PartTime");
            List<String> empStatus1 = dynamicEmployeePage.getCriteriaValues("Employment Status");
            Assert.assertTrue(empStatus1.size() == empStatus.size() && empStatus1.containsAll(empStatus), "The Employment Status validate failed! ");

            //Exempt No/Yes
            ArrayList<String> status = new ArrayList<String>();
            status.add("No");
            status.add("Yes");
            List<String> exempt = dynamicEmployeePage.getCriteriaValues("Exempt");
            Assert.assertTrue(exempt.size() == status.size() && exempt.containsAll(status), "The Exempt value validate failed!");

            //Minor No/Yes
            List<String> minor = dynamicEmployeePage.getCriteriaValues("Minor");
            Assert.assertTrue(minor.size() == status.size() && minor.containsAll(status), "The Exempt value validate failed!");

            //Badge list
            List<String> badge = dynamicEmployeePage.getCriteriaValues("Badge");
            Assert.assertTrue(badgeList.containsAll(badge), "The Exempt value validate failed!");

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @AfterMethod
    public void tearDown() {
        OpsPortalNavigationPage navigationPage = new OpsPortalNavigationPage();
        navigationPage.logout();
    }
}
