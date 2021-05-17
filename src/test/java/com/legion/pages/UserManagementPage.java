package com.legion.pages;

import java.util.ArrayList;
import java.util.HashMap;

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
}