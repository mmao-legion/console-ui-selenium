package com.legion.pages;

import cucumber.api.java.an.E;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ConfigurationPage {
    public void goToConfigurationPage() throws Exception;
    public void checkAllTemplateCards() throws Exception;
    public boolean isTemplateListPageShow() throws Exception;
    public void clickOnConfigurationCrad(String templateType) throws Exception;
    public void clickOnTemplateName(String templateType) throws Exception;
    public void goToTemplateDetailsPage(String templateType) throws Exception;
    public void clickOnSpecifyTemplateName(String templateName,String editOrViewMode) throws Exception;
    public void clickOnEditButtonOnTemplateDetailsPage() throws Exception;
    public void selectWorkRoleToEdit(String workRole) throws Exception;
    public void checkTheEntryOfAddAdvancedStaffingRule() throws Exception;
    public void verifyAdvancedStaffingRulePageShowWell() throws Exception;
    public void verifyCheckBoxOfDaysOfWeekSection() throws Exception;
    public boolean isDaysOfWeekFormulaCheckBoxChecked();
    public void inputFormulaInForDaysOfWeekSection(String formula) throws Exception;
//    public List<String> getAllDayPartsNameInOH() throws Exception;
    public void inputOffsetTimeForShiftStart(String startOffsetTime,String startEventPoint) throws Exception;
    public void validateShiftStartTimeUnitList() throws Exception;
    public List<String> getShiftStartTimeEventList() throws Exception;
}
