package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.CompliancePage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleCompliancePage extends BasePage implements CompliancePage {
    public ConsoleCompliancePage() {
        PageFactory.initElements(getDriver(), this);
    }
    @FindBy(css = "div.analytics-new-violations-count.ng-binding")
    private WebElement totalViolationHrs;
    String complianceHeaderLabel = "Compliance";

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

    @FindBy(css = "div.header-navigation-label")
    private WebElement compliancePageHeaderLabel;
    @Override
    public boolean isCompliancePageLoaded() throws Exception {
        if(isElementLoaded(compliancePageHeaderLabel,10)){
            String s = compliancePageHeaderLabel.getText();
            if(compliancePageHeaderLabel.getText().toLowerCase().contains(complianceHeaderLabel.toLowerCase())) {
                SimpleUtils.pass("Compliance Page is loaded Successfully!");
                return true;
            }
        }
        return false;
    }

    @FindBy(css = "div.card-carousel-fixed")
    private WebElement totalViolationCardInDMView;
    @FindBy(css = "div.card-carousel-container")
    private WebElement cardContainerInDMView;
    @Override
    public HashMap<String, Float> getValuesAndVerifyInfoForTotalViolationSmartCardInDMView() throws Exception {
        HashMap<String, Float> result = new HashMap<String, Float>();
        if (isElementLoaded(totalViolationCardInDMView,10) && isElementLoaded(totalViolationCardInDMView.findElement(By.cssSelector("div.card-carousel-card-primary-small")),10)){
             List<String> strList = Arrays.asList(totalViolationCardInDMView.findElement(By.cssSelector("div.card-carousel-card-primary-small")).getText().split("\n"));
            if (strList.size()==5 && strList.get(0).contains("TOTAL VIOLATION HRS") && strList.get(strList.size()-1).contains("Last week") && SimpleUtils.isNumeric(strList.get(1).replace("Hrs","")) && SimpleUtils.isNumeric(strList.get(2).replace("Hour","").replace("s","")) && SimpleUtils.isNumeric(strList.get(4).replace("Hrs Last week",""))){
                result.put("vioHrsCurrentWeek",Float.parseFloat(strList.get(1).replace("Hrs","")));
                result.put("diffHrs", Float.parseFloat(strList.get(2).replace("Hour","").replace("s","")));
                result.put("vioHrsPastWeek",Float.parseFloat(strList.get(4).replace("Hrs Last week","")));
                SimpleUtils.pass("All info on total violation smart card is displayed!");
            } else {
                SimpleUtils.fail("Info on total violation smart card is not expected!", false);
            }
        } else {
            SimpleUtils.fail("Total violation smart card fail to load!", false);
        }
        return result;
    }

    @Override
    public void verifyDiffFlag(String upOrDown) throws Exception{
        if ( isElementLoaded(totalViolationCardInDMView.findElement(By.cssSelector("div.published-clocked-cols-summary-arrow")),10)){
            if (totalViolationCardInDMView.findElement(By.cssSelector("div.published-clocked-cols-summary-arrow")).getAttribute("class").contains(upOrDown)){
                SimpleUtils.pass("Diff flag displays correctly");
            } else {
                SimpleUtils.fail("Diff flag displays incorrectly", false);
            }
        } else {
            SimpleUtils.fail("Diff flag doesn't show up!", false);
        }
    }

    @Override
    public HashMap<String, Integer> getValueOnLocationsWithViolationCardAndVerifyInfo() throws Exception {
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        if (isElementLoaded(cardContainerInDMView,10) && isElementLoaded(cardContainerInDMView.findElement(By.cssSelector("div.card-carousel-card-analytics-card-color-yellow")),10)){
            List<String> strList = Arrays.asList(cardContainerInDMView.findElement(By.cssSelector("div.card-carousel-card-analytics-card-color-yellow")).getText().split("\n"));
            if (strList.size()==4 && strList.get(1).toLowerCase().contains("locations") && strList.get(2).toLowerCase().contains("with violations") && SimpleUtils.isNumeric(strList.get(0)) && SimpleUtils.isNumeric(strList.get(3).replace(" total locations", ""))){
                result.put("LocationsWithViolation", Integer.parseInt(strList.get(0)));
                result.put("total locations", Integer.parseInt(strList.get(3).replace(" total locations", "")));
                SimpleUtils.pass("All info on Locations With Violation Card is expected!");
            } else {
                SimpleUtils.fail("Info on Locations With Violation Card is not expected!", false);
            }
        } else {
            SimpleUtils.fail("Locations With Violation Card fail to load!", false);
        }
        return result;
    }

    @Override
    public HashMap<String, Float> getViolationHrsFromTop1ViolationCardAndVerifyInfo() throws Exception {
        HashMap<String, Float> result = new HashMap<String, Float>();
        if (isElementLoaded(cardContainerInDMView,10) && isElementLoaded(cardContainerInDMView.findElement(By.cssSelector("div.card-carousel-card-card-carousel-card-yellow-top")),10)){
            String infoForEveryViolation = null;
            String keyTemp = "";
            List<WebElement> violations = cardContainerInDMView.findElements(By.cssSelector("div.analytics-new-common-violations-row div[ng-repeat]"));
            String cardTitle = cardContainerInDMView.findElement(By.cssSelector("div.card-carousel-card-card-carousel-card-yellow-top div.card-carousel-card-title")).getText();
            SimpleUtils.assertOnFail("The number of total violation is incorrect!", cardTitle.contains(String.valueOf(violations.size())), false);
            List<String> strList = new ArrayList<>();
            for (WebElement element: violations){
                keyTemp = "";
                infoForEveryViolation = element.getText();
                strList = Arrays.asList(infoForEveryViolation.split("\n"));
                for (int i = 0; i<strList.size()-1; i++){
                    keyTemp = keyTemp +" "+ strList.get(i);
                }
                if (SimpleUtils.isNumeric(strList.get(strList.size()-1))){
                    result.put(keyTemp, Float.valueOf(strList.get(strList.size()-1)));
                } else {
                    SimpleUtils.fail("The violation hrs count is not numeric, please check!", false);
                }
            }
        } else {
            SimpleUtils.fail("Unplanned clocks card fail to load!", false);
        }
        return result;
    }

    @Override
    public float getTopOneViolationHrsOrNumOfACol(List<Float> list) throws Exception {
        float result = 0;
        if (list.size()>0){
            result = list.stream().sorted(Float::compareTo).collect(Collectors.toList()).get(list.size()-1);
        }
        return result;
    }
}
