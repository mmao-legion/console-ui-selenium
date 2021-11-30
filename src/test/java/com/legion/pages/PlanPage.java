package com.legion.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface PlanPage {
    
    public void clickOnPlanConsoleMenuItem() throws Exception;
    public void createANewPlan(String planName) throws Exception;
    public void clickOnSavePlanOKBtn() throws Exception;
    public void verifyScenarioPlanAutoCreated(String scenarioName) throws Exception;
    public void searchAPlan(String keywords) throws Exception;
    public void takeOperationToPlan(String parentPlanName, String scenarioPlanName, String status) throws Exception;
//    public int getAllPlansInList() throws Exception;


}
