package com.legion.pages;

import java.util.List;

public interface CompliancePage {
    public String getTheTotalViolationHrsFromSmartCard() throws Exception;
    public String getTheTotalLocationsFromSmartCard() throws Exception;
    public String getTheTotalLocationsWithViolationsFromSmartCard() throws Exception;
    public List<String> getComplianceViolationsOnDMViewSmartCard() throws Exception;
}
