package com.legion.tests.core.ops_portal;

import com.legion.pages.*;
import com.legion.pages.core.opusermanagement.OpsPortalNavigationPage;
import com.legion.pages.core.opusermanagement.OpsPortalUserManagementPanelPage;
import com.legion.pages.core.opusermanagement.OpsPortalWorkRolesPage;
import com.legion.pages.core.opusermanagement.WorkRoleDetailsPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
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
            workRoleDetailsPage.editWorkRoleDetails("autoWorkRole001", 3, "Deployed", "3");
            workRoleDetailsPage.addAssignmentRule("3","2","2098");
            workRoleDetailsPage.saveAssignRule();
            workRoleDetailsPage.submit();
            workRolesPage.save();
            workRolesPage.searchByWorkRole("autoWorkRole001");
            Assert.assertEquals(workRolesPage.getTheFirstWorkRoleInTheList(), "autoWorkRole001", "Failed to add new work role!");

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
            workRolesPage.editAnExistingWorkRole("autoWorkRole001");
            workRoleDetailsPage.editWorkRoleDetails("autoWorkRole001-edit", 2, "Deployed", "1.5");
            workRoleDetailsPage.submit();
            workRolesPage.save();
            workRolesPage.searchByWorkRole("autoWorkRole001-edit");
            Assert.assertEquals(workRolesPage.getTheFirstWorkRoleInTheList(), "autoWorkRole001-edit", "Failed to Edit new work role!");

            //edit an existing work role and cancel the editing
            workRolesPage.editAnExistingWorkRole("autoWorkRole001-edit");
            workRoleDetailsPage.editWorkRoleDetails("autoWorkRole-cancelEdit", 9, "Deployed", "2");
            workRoleDetailsPage.submit();
            workRolesPage.cancel();
            Assert.assertEquals(workRolesPage.getCancelDialogTitle(), "Cancel Editing?", "Cancel Dialog is not displayed");
            workRolesPage.cancelEditing();
            workRolesPage.searchByWorkRole("autoWorkRole-cancelEdit");
            Assert.assertTrue(workRolesPage.getNoResultNotice().contains("A Work Role defines a category of work that needs to be scheduled."));
            workRolesPage.searchByWorkRole("autoWorkRole001-edit");
            Assert.assertEquals(workRolesPage.getTheFirstWorkRoleInTheList(), "autoWorkRole001-edit", "Failed to cancel the editing!");

            //disable the work role added and it can't be searched out
            workRolesPage.disableAWorkRole("autoWorkRole001-edit");
            Assert.assertEquals(workRolesPage.getDisableDialogTitle(), "Disable Work Role", "The disable work role dialog is not displayed.");
            workRolesPage.okToDisableAction();
            workRolesPage.save();
            workRolesPage.searchByWorkRole("autoWorkRole001-edit");
            Assert.assertTrue(workRolesPage.getNoResultNotice().contains("A Work Role defines a category of work that needs to be scheduled."));

        } catch (Exception e) {
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

}
