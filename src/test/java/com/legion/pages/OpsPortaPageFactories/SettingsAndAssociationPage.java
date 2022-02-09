package com.legion.pages.OpsPortaPageFactories;

import java.util.List;

public interface SettingsAndAssociationPage {
    public void goToTemplateListOrSettings(String tab) throws Exception;
    public void verifyTitleOnTheSettingsPage(String groupType) throws Exception;
    public void verifyTitleOnTheSAssociationPage(String groupType) throws Exception;
    public void clickOnEditBtnForDynamicGroupOnAssociationPage() throws Exception;
    public void clickOnRemoveBtnForDynamicGroupOnAssociationPage() throws Exception;
    public void clickOnDoneBtnForDynamicGroupOnAssociationPage() throws Exception;
    public void clickOnAddBtnForDynamicGroupOnAssociationPage() throws Exception;
    public void inputGroupNameForDynamicGroupOnAssociationPage(String groupName) throws Exception;
    public void selectAnOptionForCriteria(String criteria, String operator, String option) throws Exception;
    public void deleteAllCriteriaOnTheAssociationPageIfExist() throws Exception;
    public void goToAssociationTabOnTemplateDetailsPage() throws Exception;
    public boolean ifConflictDetectedWindowShowUP() throws Exception;
    public void clickOnTheSaveBtnOnConflictDetectedWindow() throws Exception;
    public List<String> getFieldListFromSettingsTab() throws Exception;
    public void setupRequiredFields(List<String> fields) throws Exception;
    public List<String> getCriteriaListFromTheAssociationPage() throws Exception;
    public String clickOnTestBtnAndGetResultString() throws Exception;
    public boolean areFieldsCheckInputEnabled() throws Exception;
}
