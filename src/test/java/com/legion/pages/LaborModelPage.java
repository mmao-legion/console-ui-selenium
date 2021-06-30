package com.legion.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public interface LaborModelPage {

    public void goToLaborModelTile() throws Exception;

    public void clickOnSpecifyTemplateName(String template_name, String edit) throws Exception;

    public void clickOnLaborModelTab() throws Exception;

    public List<HashMap<String, String>> getLaborModelInTemplateLevel();

    public void addNewLaborModelTemplate(String templateName) throws Exception;

    public void deleteDraftLaborModelTemplate(String templateName) throws Exception;

    public void publishNewLaborModelTemplate(String templateName,String dynamicGroupName) throws Exception;

    public void selectLaborStandardRepositorySubTabByLabel(String label) throws Exception;

    public void goToLaborStandardRepositoryTile() throws Exception;

    public void clickOnEditButton() throws Exception;

    public void clickOnSaveButton() throws Exception;

    public void clickOnCancelButton() throws Exception;

    public void clickOnAddAttributeButton() throws Exception;

    public void createNewAttribute(String attributeName,String attributeValue,String attributeDescription) throws Exception;

    public void cancelCreateNewAttribute(String attributeName,String attributeValue,String attributeDescription) throws Exception;

    public boolean isSpecifyAttributeExisting(String attributeName) throws Exception;

    public void clickOnDeleteAttributeButton(String attributeName) throws Exception;

    public void clickOkBtnOnDeleteAttributeDialog() throws Exception;

    public void clickCancelBtnOnDeleteAttributeDialog() throws Exception;

    public boolean checkDeleteAttributeButtonForEachAttribute() throws Exception;

    public List<String> clickOnPencilButtonAndUpdateAttribute(String attributeName,String attributeValueUpdate,String attributeDescriptionUpdate) throws Exception;

    public HashMap<String, List<String>> getValueAndDescriptionForEachAttribute() throws Exception;

    public void selectLaborModelTemplateDetailsPageSubTabByLabel(String label) throws Exception;

    public void overriddenLaborModelRuleInLocationLevel(int index);
}