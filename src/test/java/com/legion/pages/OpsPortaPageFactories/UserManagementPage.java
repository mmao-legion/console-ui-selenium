package com.legion.pages.OpsPortaPageFactories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface UserManagementPage {

    public void clickOnUserManagementTab() throws Exception;

    public void verifyWorkRolesTileDisplay() throws Exception;

    public void goToWorkRolesTile() throws Exception;

    public void verifyEditBtnIsClickable() throws Exception;

    public void verifyBackBtnIsClickable() throws Exception;

    public void cancelAddNewWorkRoleWithoutAssignmentRole(String workRoleName, String colour, String workRole, String hourlyRate) throws Exception;

    public void addNewWorkRoleWithoutAssignmentRole(String workRoleName, String colour, String workRole, String hourlyRate) throws Exception;

    public void verifySearchWorkRole(String workRoleName) throws Exception;

    public ArrayList<HashMap<String, String>> getWorkRoleInfo(String workRoleName);

    public void updateWorkRole(String workRoleName, String colour, String workRole, String hourlyRate, String selectATeamMemberTitle, String defineTheTimeWhenThisRuleApplies, String specifyTheConditionAndNumber, String shiftNumber, String defineTheTypeAndFrequencyOfTimeRequiredAndPriority, String priority) throws Exception;

    public void addNewWorkRole(String workRoleName, String colour, String workRole, String hourlyRate, String selectATeamMemberTitle, String defineTheTimeWhenThisRuleApplies, String specifyTheConditionAndNumber, String shiftNumber, String defineTheTypeAndFrequencyOfTimeRequiredAndPriority, String priority) throws Exception;

    public boolean iCanSeeDynamicGroupItemTileInUserManagementTab();

    public void goToDynamicGroup();

    public void searchNewsFeedDynamicGroup(String searchText) throws Exception;

    public void iCanDeleteExistingWFSDG();

    public boolean verifyLayoutOfDGDisplay() throws Exception;

    public void verifyDefaultMessageIfThereIsNoGroup() throws Exception;

    public List<HashMap<String, String>> getExistingGroups();

    public void verifyNameInputField(String groupNameForNewsFeed) throws Exception;

    public void iCanGoToManageDynamicGroupPage();

    public void verifyCriteriaList() throws Exception;

    public void testButtonIsClickable() throws Exception;

    public void addMoreButtonIsClickable() throws Exception;

    public void criteriaDescriptionDisplay() throws Exception;

    public void removeCriteriaBtnIsClickAble();

    public void cancelBtnIsClickable() throws Exception;

    public String addNewsFeedGroupWithOneCriteria(String groupNameForNewsFeed, String description, String criteria) throws Exception;

    public String updateNewsFeedDynamicGroup(String groupNameForNewsFeed, String criteriaUpdate) throws Exception;

    public void verifyAddNewsFeedGroupWithExistingGroupName(String groupNameForNewsFeed, String description) throws Exception;

    public void verifyAddNewsFeedGroupWithDifNameSameCriterias(String groupNameForNewsFeed2, String description, String criteria) throws Exception;

    public void goToUserAndRoles();

    public void goToAccessRolesTab();

    public void verifyManageItemInUserManagementAccessRoleTab() throws Exception;

    public void verifyRemoveTheConditionFromDropDownListIfItSelected() throws Exception;

    public void goToWorkRolesDetails(String workRoleName) throws Exception;

    public void disableAssignmentRulesInLocationLevel(int index);

    public void enableAssignmentRulesInLocationLevel(int index);

    public void overriddenAssignmentRule(int index);

    public void verifyPlanItemInUserManagementAccessRoleTab() throws Exception;

    public int getIndexOfRolesInPermissionsTable(String role) throws Exception;

    public boolean verifyPermissionIsCheckedOrNot(int index) throws Exception;

}