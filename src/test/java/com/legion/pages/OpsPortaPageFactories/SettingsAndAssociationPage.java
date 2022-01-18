package com.legion.pages.OpsPortaPageFactories;

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
}
