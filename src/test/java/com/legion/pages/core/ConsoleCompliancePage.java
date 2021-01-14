package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.CompliancePage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleCompliancePage extends BasePage implements CompliancePage {
    public ConsoleCompliancePage() {
        PageFactory.initElements(getDriver(), this);
    }
    @FindBy(css = "div.analytics-new-violations-count.ng-binding")
    private WebElement totalViolationHrs;

    public String getTheTotalViolationHrsFromSmartCard() throws Exception {
        String hrsOfTotalViolation = "";
        if (isElementLoaded(totalViolationHrs, 5)){
            hrsOfTotalViolation = totalViolationHrs.getText();
            SimpleUtils.pass("Get the total violation hrs successfully");
        } else {
            SimpleUtils.fail("Total violation hours not loaded successfully", false);
        }
        return hrsOfTotalViolation;
    }

    // Added By Julie
    @FindBy(className = "analytics-card-color-text-4")
    private WebElement totalLocations;

    @Override
    public String getTheTotalLocationsFromSmartCard() throws Exception {
        String numberOfTotalLocations = "";
        if (isElementLoaded(totalLocations, 5)) {
            numberOfTotalLocations = totalLocations.getText().trim();
            if (!numberOfTotalLocations.isEmpty()) {
                SimpleUtils.pass("Compliance Page: Get the total locations: \"" + numberOfTotalLocations + "\" on DM View smart card successfully");
            } else {
                SimpleUtils.fail("Compliance Page: Failed to get the total locations on DM View smart card", false);
            }
        } else {
            SimpleUtils.fail("Compliance Page: Total locations not loaded", false);
        }
        return numberOfTotalLocations;
    }

    @FindBy(className = "analytics-card-color-text-1")
    private WebElement totalLocationsWithViolations;

    @Override
    public String getTheTotalLocationsWithViolationsFromSmartCard() throws Exception {
        String numberOfTotalLocationsWithViolations = "";
        if (isElementLoaded(totalLocationsWithViolations, 5)) {
            numberOfTotalLocationsWithViolations = totalLocationsWithViolations.getText().trim();
            if (!numberOfTotalLocationsWithViolations.isEmpty()) {
                SimpleUtils.pass("Compliance Page: Get the total locations with violations: \"" + numberOfTotalLocationsWithViolations + "\" on DM View smart card successfully");
            } else {
                SimpleUtils.fail("Compliance Page: Failed to get the total locations with violations on DM View smart card", false);
            }
        } else {
            SimpleUtils.fail("Compliance Page: Total locations not loaded", false);
        }
        return numberOfTotalLocationsWithViolations;
    }

    @Override
    public List<String> getComplianceViolationsOnDMViewSmartCard() throws Exception {
        List<String> complianceViolationsOnDMViewSmartCard = new ArrayList<>();
        String totalHrs = getTheTotalViolationHrsFromSmartCard();
        totalHrs = totalHrs.contains(" ")? totalHrs.split(" ")[0]:totalHrs;
        totalHrs = totalHrs + " Total Hrs";
        complianceViolationsOnDMViewSmartCard.add(totalHrs);
        SimpleUtils.report("Compliance Page: Get the total Hrs: \"" + totalHrs + "\" on DM View smart card successfully");
        String violations = getTheTotalLocationsWithViolationsFromSmartCard();
        violations = violations + " Violations";
        complianceViolationsOnDMViewSmartCard.add(violations);
        SimpleUtils.report("Compliance Page: Get the total violations: \"" + violations + "\" on DM View smart card successfully");
        String totalLocations = getTheTotalLocationsFromSmartCard();
        totalLocations = totalLocations.contains(" ")? totalLocations.split(" ")[0]:totalLocations;
        totalLocations = totalLocations + " Locations";
        complianceViolationsOnDMViewSmartCard.add(totalLocations);
        SimpleUtils.report("Compliance Page: Get the total locations: \"" + totalLocations + "\" on DM View smart card successfully");
        return complianceViolationsOnDMViewSmartCard;
    }
}
