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




}