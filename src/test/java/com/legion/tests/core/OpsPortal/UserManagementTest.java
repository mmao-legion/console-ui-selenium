package com.legion.tests.core.OpsPortal;

import com.legion.pages.OpsPortaPageFactories.LocationsPage;
import com.legion.pages.OpsPortaPageFactories.UserManagementPage;
import com.legion.pages.core.OpCommons.OpsPortalNavigationPage;
import com.legion.pages.core.opusermanagement.*;
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
import java.text.SimpleDateFormat;
import java.util.*;


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
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

    }


    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Common function for newsfeed group")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyDynamicGroupFunctionInUserManagementTabAsInternalAdminForUserManagement(String browser, String username, String password, String location) throws Exception {

        try{
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime =  dfs.format(new Date()).trim();
            String groupNameForNewsFeed =   "AutoNewsFeed" +currentTime;
            String groupNameForNewsFeed2 = "difName" +currentTime;
            String description = "AutoCreate" +currentTime;
            String criteria = "Work Role";
            String criteriaUpdate = "Minor";
            String searchText = "AutoNewsFeed";


            //go to User Management tab
            UserManagementPage userManagementPage = pageFactory.createOpsPortalUserManagementPage();
            userManagementPage.clickOnUserManagementTab();
            //check dynamic group item
            if (userManagementPage.iCanSeeDynamicGroupItemTileInUserManagementTab()) {
                //go to dynamic group
                userManagementPage.goToDynamicGroup();
                if (userManagementPage.verifyLayoutOfDGDisplay()) {
                    List<HashMap<String,String>> groupRows =userManagementPage.getExistingGroups();
                    if (groupRows!=null) {
                        userManagementPage.searchNewsFeedDynamicGroup(searchText);
                        //remove existing dynamic group
                        userManagementPage.iCanDeleteExistingWFSDG();
                        userManagementPage.iCanGoToManageDynamicGroupPage();
                        //verifyEachField
                        userManagementPage.verifyNameInputField(groupNameForNewsFeed);
                        userManagementPage.verifyCriteriaList();
                        userManagementPage.testButtonIsClickable();
                        userManagementPage.addMoreButtonIsClickable();
                        userManagementPage.removeCriteriaBtnIsClickAble();
                        userManagementPage.criteriaDescriptionDisplay();
                        userManagementPage.cancelBtnIsClickable();
                        //create new workforce sharing dynamic group
                        String groupTestMess = userManagementPage.addNewsFeedGroupWithOneCriteria(groupNameForNewsFeed,description,criteria);

                        //verify add group with existing group name
                        userManagementPage.verifyAddNewsFeedGroupWithExistingGroupName(groupNameForNewsFeed,description);
                        //verify existing criteria ,but group name is not same
                        userManagementPage.verifyAddNewsFeedGroupWithDifNameSameCriterias(groupNameForNewsFeed2,description,criteria);

                        String groupTestMessAftUpdate = userManagementPage.updateNewsFeedDynamicGroup(groupNameForNewsFeed,criteriaUpdate);
                        if (!groupTestMessAftUpdate.equalsIgnoreCase(groupTestMess)) {
                            SimpleUtils.pass("Update News Feed dynamic group successfully");
                        }
                    }else
                        userManagementPage.verifyDefaultMessageIfThereIsNoGroup();
                }else
                    SimpleUtils.fail("Newsfeed group show wrong",false);
            }else
                SimpleUtils.report("Dynamic group for newsfeed is off");

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    //blocked by one bug
//    @Automated(automated = "Automated")
//    @Owner(owner = "Estelle")
//    @Enterprise(name = "Op_Enterprise")
//    @TestName(description = "Remove the condition from drop down list if it's selected")
//    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
//    public void verifyRemoveTheConditionFromDropDownListIfItSelectedAsInternalAdminForUserManagement(String browser, String username, String password, String location) throws Exception {
//
//        try{
//            //go to User Management tab
//            UserManagementPage userManagementPage = pageFactory.createOpsPortalUserManagementPage();
//            userManagementPage.clickOnUserManagementTab();
//            //check dynamic group item
//            userManagementPage.iCanSeeDynamicGroupItemTileInUserManagementTab();
//            //go to dynamic group
//            userManagementPage.goToDynamicGroup();
//            userManagementPage.verifyRemoveTheConditionFromDropDownListIfItSelected();
//        } catch (Exception e){
//            SimpleUtils.fail(e.getMessage(), false);
//        }
//
//    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify manage item in User Management access role tab")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyManageItemInUserManagementAccessRoleTabAsInternalAdminForUserManagement(String browser, String username, String password, String location) throws Exception {

        try{
            //go to User Management tab
            UserManagementPage userManagementPage = pageFactory.createOpsPortalUserManagementPage();
            userManagementPage.clickOnUserManagementTab();
            //go to dynamic group
            userManagementPage.goToUserAndRoles();
            userManagementPage.goToAccessRolesTab();
            userManagementPage.verifyManageItemInUserManagementAccessRoleTab();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Sophia")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify add and update work role")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAddEditSearchAndDisableWorkRoleAsInternalAdminForUserManagement(String browser, String username, String password, String location) throws Exception {
        try {
            OpsPortalNavigationPage navigationPage = new OpsPortalNavigationPage();
            navigationPage.navigateToUserManagement();
            OpsPortalUserManagementPanelPage panelPage = new OpsPortalUserManagementPanelPage();
            panelPage.goToWorkRolesPage();
            OpsPortalWorkRolesPage workRolesPage = new OpsPortalWorkRolesPage();

            //add a new work role and save it
            workRolesPage.addNewWorkRole();
            WorkRoleDetailsPage workRoleDetailsPage = new WorkRoleDetailsPage();
            Random random = new Random();
            String workRoleName="autoWorkRole" + random.nextInt(1000);
            workRoleDetailsPage.editWorkRoleDetails(workRoleName, 3, "Deployed", "3");
            workRoleDetailsPage.addAssignmentRule("3","2","2098");
            workRoleDetailsPage.saveAssignRule();
            workRoleDetailsPage.submit();
            workRolesPage.save();
            workRolesPage.searchByWorkRole(workRoleName);
            Assert.assertEquals(workRolesPage.getTheFirstWorkRoleInTheList(), workRoleName, "Failed to add new work role!");

            //search by work role
            //testcase1: exact matching
            workRolesPage.searchByWorkRole("Ambassador");
            Assert.assertTrue(workRolesPage.getTheFirstWorkRoleInTheList().equalsIgnoreCase("Ambassador"));

            //testcase2: partial matching
            workRolesPage.searchByWorkRole("test");
            Assert.assertFalse(workRolesPage.getTheFirstWorkRoleInTheList().equalsIgnoreCase("test"));
            Assert.assertTrue(workRolesPage.getTheFirstWorkRoleInTheList().contains("test"));

            //testcase3: no matching item
            workRolesPage.searchByWorkRole("m*");
            Assert.assertTrue(workRolesPage.getNoResultNotice().contains("A Work Role defines a category of work that needs to be scheduled."));

            //cancel the creating of new work role
            workRolesPage.addNewWorkRole();
            workRoleDetailsPage.editWorkRoleDetails("testCancelCreating", 6, "Deployed", "2");
            workRoleDetailsPage.submit();
            workRolesPage.cancel();
            Assert.assertEquals(workRolesPage.getCancelDialogTitle(), "Cancel Editing?", "Failed to popup the cancel dialog. ");
            workRolesPage.cancelEditing();
            workRolesPage.searchByWorkRole("testCancelCreating");
            Assert.assertTrue(workRolesPage.getNoResultNotice().contains("A Work Role defines a category of work that needs to be scheduled."));

            //edit an existing work role and save the editing
            workRolesPage.editAnExistingWorkRole(workRoleName);
            workRoleDetailsPage.editWorkRoleDetails(workRoleName+"-edit", 2, "Deployed", "1.5");
            workRoleDetailsPage.submit();
            workRolesPage.save();
            workRolesPage.searchByWorkRole(workRoleName+"-edit");
            Assert.assertEquals(workRolesPage.getTheFirstWorkRoleInTheList(), workRoleName+"-edit", "Failed to Edit new work role!");

            //edit an existing work role and cancel the editing
            workRolesPage.editAnExistingWorkRole(workRoleName+"-edit");
            workRoleDetailsPage.editWorkRoleDetails(workRoleName+"-cancelEdit", 9, "Deployed", "2");
            workRoleDetailsPage.submit();
            workRolesPage.cancel();
            Assert.assertEquals(workRolesPage.getCancelDialogTitle(), "Cancel Editing?", "Cancel Dialog is not displayed");
            workRolesPage.cancelEditing();
            workRolesPage.searchByWorkRole(workRoleName+"-cancelEdit");
            Assert.assertTrue(workRolesPage.getNoResultNotice().contains("A Work Role defines a category of work that needs to be scheduled."));
            workRolesPage.searchByWorkRole(workRoleName+"-edit");
            Assert.assertEquals(workRolesPage.getTheFirstWorkRoleInTheList(), workRoleName+"-edit", "Failed to cancel the editing!");

            //disable the work role added and it can't be searched out
            workRolesPage.disableAWorkRole(workRoleName+"-edit");
            Assert.assertEquals(workRolesPage.getDisableDialogTitle(), "Disable Work Role", "The disable work role dialog is not displayed.");
            workRolesPage.okToDisableAction();
            workRolesPage.save();
            workRolesPage.searchByWorkRole(workRoleName+"-edit");
            Assert.assertTrue(workRolesPage.getNoResultNotice().contains("A Work Role defines a category of work that needs to be scheduled."));

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
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
            ArrayList<String> minorsExpected = new ArrayList<String>();
            minorsExpected.add(" <14");
            minorsExpected.add("14");
            minorsExpected.add("15");
            minorsExpected.add("16");
            minorsExpected.add("17");
            minorsExpected.add(">=18");
            Assert.assertTrue(minor.containsAll(minorsExpected), "The Minor value validate failed!");

            //Badge list
            List<String> badge = dynamicEmployeePage.getCriteriaValues("Badge");
            Assert.assertTrue(badgeList.containsAll(badge), "The Badge value validate failed!");
            dynamicEmployeePage.cancelCreating();

            //create a new employee group
            dynamicEmployeePage.addGroup();
            Assert.assertEquals(dynamicEmployeePage.getModalTitle(), "Manage Dynamic Group", "Failed to open manage dynamic group modal!");
            dynamicEmployeePage.editEmployeeGroup("AutoTestCreating", "create a new group", "autoTest", "Work Role");
            dynamicEmployeePage.saveCreating();

            //cancel creating
            dynamicEmployeePage.addGroup();
            Assert.assertEquals(dynamicEmployeePage.getModalTitle(), "Manage Dynamic Group", "Failed to open manage dynamic group modal!");
            dynamicEmployeePage.editEmployeeGroup("AutoTestCancel", "give up creating a new group", "cancel", "Employment Type");
            dynamicEmployeePage.cancelCreating();

            //search a group
            dynamicEmployeePage.searchGroup("AutoTestCreating");

            //edit an existing group
            dynamicEmployeePage.edit();
            Assert.assertEquals(dynamicEmployeePage.getModalTitle(), "Manage Dynamic Group", "Failed to open manage dynamic group modal!");
            dynamicEmployeePage.editEmployeeGroup("TestEdit", "edit an existing group", "edit", "Employment Status");
            dynamicEmployeePage.saveCreating();

            //cancel editing
            dynamicEmployeePage.searchGroup("TestEdit");
            dynamicEmployeePage.edit();
            Assert.assertEquals(dynamicEmployeePage.getModalTitle(), "Manage Dynamic Group", "Failed to open manage dynamic group modal!");
            dynamicEmployeePage.editEmployeeGroup("TestCancelEdit", "cancel edit", "cancel edit", "Minor");
            dynamicEmployeePage.cancelCreating();

            //cancel remove
            dynamicEmployeePage.remove();
            Assert.assertEquals(dynamicEmployeePage.getContentOfRemoveModal(), "Are you sure you want to remove this dynamic group?", "Failed to open the remove modal!");
            dynamicEmployeePage.cancelRemove();

            //remove an existing group
            dynamicEmployeePage.remove();
            Assert.assertEquals(dynamicEmployeePage.getContentOfRemoveModal(), "Are you sure you want to remove this dynamic group?", "Failed to open the remove modal!");
            dynamicEmployeePage.removeTheGroup();
            SimpleUtils.pass("Succeeded in removing dynamic group!");

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }

    }

}
