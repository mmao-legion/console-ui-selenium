package com.legion.pages;

import java.util.HashMap;
import java.util.List;

public interface CompliancePage {
    public String getTheTotalViolationHrsFromSmartCard() throws Exception;
    public String getTheTotalLocationsFromSmartCard() throws Exception;
    public String getTheTotalLocationsWithViolationsFromSmartCard() throws Exception;
    public List<String> getComplianceViolationsOnDMViewSmartCard() throws Exception;
    public boolean isCompliancePageLoaded() throws Exception;
    public HashMap<String, Float> getValuesAndVerifyInfoForTotalViolationSmartCardInDMView() throws Exception;
    public void verifyDiffFlag(String upOrDown) throws Exception;
    public HashMap<String, Integer> getValueOnLocationsWithViolationCardAndVerifyInfo() throws Exception;
    public HashMap<String, Float> getViolationHrsFromTop1ViolationCardAndVerifyInfo() throws Exception;
    public float getTopOneViolationHrsOrNumOfACol(List<Float> list) throws Exception;
}
