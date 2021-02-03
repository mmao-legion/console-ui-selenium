package com.legion.pages;

import cucumber.api.java.an.E;

import java.util.ArrayList;
import java.util.HashMap;

public interface ConfigurationPage {
    public void goToConfigurationPage() throws Exception;
    public void checkAllTemplateCards() throws Exception;
    public boolean isTemplateListPageShow() throws Exception;
    public void clickOnConfigurationCrad(String templateType) throws Exception;
    public void clickOnTemplateName(String templateType) throws Exception;
    public void goToTemplateDetailsPage(String templateType) throws Exception;
    public void clickOnSpecifyTemplateName(String templateName,String editOrViewMode) throws Exception;
}
