package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.CompliancePage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    @FindBy(css = "[ng-click=\"$ctrl.onReload(true)\"]")
    private WebElement refreshButton;

    @FindBy(css = "[ng-if=\"$ctrl.minutes >= 0 && $ctrl.date && !$ctrl.loading\"]")
    private WebElement lastUpdatedIcon;

    @FindBy (css = "last-updated-countdown span[ng-if^=\"$ctrl.minutes === 0\"]")
    private WebElement justUpdated;

    @FindBy (css = "last-updated-countdown span[ng-if^=\"$ctrl.minutes > 0\"]")
    private WebElement lastUpdated;

    @FindBy (css = "last-updated-countdown span[ng-if^=\"$ctrl.minutes > 0\"] span")
    private WebElement lastUpdatedMinutes;

    @FindBy (css = ".console-navigation-item-label.Compliance")
    private WebElement complianceConsoleMenu;

    @FindBy (css = ".console-navigation-item-label.Schedule")
    private WebElement scheduleConsoleMenu;

    @FindBy (css = ".analytics-new.ng-scope")
    private WebElement complianceSection;

    @FindBy(className = "analytics-new-table-group-row-open")
    private List<WebElement> rowsInAnalyticsTable;

    @FindBy (css = ".console-navigation-item-label.Timesheet")
    private WebElement timesheetConsoleMenu;

    @FindBy (css = ".day-week-picker-period-week")
    private List<WebElement> currentWeeks;

    @FindBy(className = "day-week-picker-arrow-right")
    private WebElement calendarNavigationNextWeekArrow;

    @FindBy(className = "day-week-picker-arrow-left")
    private WebElement calendarNavigationPreviousWeekArrow;

    @Override
    public void clickOnRefreshButton() throws Exception {
        if (isElementLoaded(refreshButton, 10)) {
            clickTheElement(refreshButton);
            if(isElementLoaded(lastUpdatedIcon, 60)){
                SimpleUtils.pass("Click on Refresh button Successfully!");
            } else
                SimpleUtils.fail("Refresh timeout! ", false);
        } else {
            SimpleUtils.fail("Refresh button not Loaded!", true);
        }
    }

    @Override
    public void validateThePresenceOfRefreshButton() throws Exception {
        if (isElementLoaded(refreshButton,10)) {
            if (refreshButton.isDisplayed() && !refreshButton.getText().isEmpty() && refreshButton.getText() != null) {
                if (getDriver().findElement(By.xpath("//body//day-week-picker/following-sibling::last-updated-countdown/div/lg-button")).equals(refreshButton)) {
                    SimpleUtils.pass("Compliance Page: Refresh button shows near week section successfully");
                } else {
                    SimpleUtils.fail("Compliance Page: Refresh button is not above welcome section", true);
                }
            } else {
                SimpleUtils.fail("Compliance Page: Refresh button isn't present", true);
            }
        } else {
            SimpleUtils.fail("Compliance Page: Refresh button failed to load", true);
        }
    }

    @Override
    public void validateRefreshFunction() throws Exception {
        int minutes = 0;
        if (isElementLoaded(lastUpdatedMinutes,10) ) {
            minutes = lastUpdatedMinutes.getText().contains(" ")? Integer.valueOf(lastUpdatedMinutes.getText().split(" ")[0]):0;
            if (minutes >= 30 ) {
                if (lastUpdatedMinutes.getAttribute("class").contains("last-updated-countdown-time-orange"))
                    SimpleUtils.pass("Compliance Page: When the Last Updated time >= 30 mins, the color changes to orange");
                else
                    SimpleUtils.fail("Compliance Page: When the Last Updated time >= 30 mins, the color failed to change to orange",false);
            }
        }
        if (isElementLoaded(refreshButton, 10)) {
            clickTheElement(refreshButton);
            SimpleUtils.pass("Compliance Page: Click on Refresh button Successfully!");
            if (complianceSection.getAttribute("class").contains("analytics-new-refreshing") && refreshButton.getAttribute("label").equals("Refreshing...")) {
                SimpleUtils.pass("Compliance Page: After clicking Refresh button, the background is muted and it shows an indicator 'Refreshing...' that we are processing the info");
                if (isElementLoaded(justUpdated,60) && complianceSection.getAttribute("class").contains("home-dashboard-loading"))
                    SimpleUtils.pass("Compliance Page: Once the data is done refreshing, the page shows 'JUST UPDATED' and page becomes brighter again");
                else
                    SimpleUtils.warn("SCH-2857: [DM View] DURATION.X-MINS displays instead of actual refresh time stamp");
                    // SimpleUtils.fail("Compliance Page: When the data is done refreshing, the page doesn't show 'JUST UPDATED' and page doesn't become brighter again",false);
                if (isElementLoaded(lastUpdated,60) && lastUpdatedMinutes.getAttribute("class").contains("last-updated-countdown-time-blue"))
                    SimpleUtils.pass("Compliance Page: The Last Updated info provides the minutes last updated in blue");
                else
                    SimpleUtils.warn("SCH-2857: [DM View] DURATION.X-MINS displays instead of actual refresh time stamp");
                    // SimpleUtils.fail("Compliance Page: The Last Updated info doesn't provide the minutes last updated in blue",false);
            } else {
                SimpleUtils.fail("Compliance Page: After clicking Refresh button, the background isn't muted and it doesn't show 'Refreshing...'",true);
            }
        } else {
            SimpleUtils.fail("Compliance Page: Refresh button not Loaded!", true);
        }
    }

    @Override
    public void validateRefreshPerformance() throws Exception {
        if (isElementLoaded(refreshButton, 10)) {
            clickTheElement(refreshButton);
            if (refreshButton.getAttribute("label").equals("Refreshing...")) {
                SimpleUtils.pass("Compliance Page: After clicking Refresh button, the button becomes 'Refreshing...'");
                WebElement element = (new WebDriverWait(getDriver(), 60))
                        .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[label=\"Refresh\"]")));
                if (element.isDisplayed()) {
                    SimpleUtils.pass("Compliance Page: Page refreshes within 1 minute successfully");
                } else {
                    SimpleUtils.fail("Compliance Page: Page doesn't refresh within 1 minute", false);
                }
            } else {
                SimpleUtils.fail("Compliance Page: After clicking Refresh button, the background isn't muted and it doesn't show 'Refreshing...'",true);
            }
        } else {
            SimpleUtils.fail("Compliance Page: Refresh button not Loaded!", true);
        }
    }

    @Override
    public void validateRefreshTimestamp() throws Exception {
        String timestamp = "";
        if (isElementLoaded(justUpdated, 5)) {
            SimpleUtils.pass("Compliance Page: The page just refreshed");
        } else if (isElementLoaded(lastUpdatedMinutes, 5)) {
            timestamp = lastUpdatedMinutes.getText();
            if (timestamp.contains("HOUR") && timestamp.contains(" ")) {
                timestamp = timestamp.split(" ")[0];
                if (Integer.valueOf(timestamp) == 1)
                    SimpleUtils.pass("Compliance Page: The backstop is 1 hour so that the data is not older than 1 hour stale");
                else
                    // SimpleUtils.fail("Schedule Page: The backstop is older than 1 hour stale",false);
                    SimpleUtils.warn("SCH-2589: [DM View] Refresh time is older than 1 hour stale");
            } else if (timestamp.contains("MINS") && timestamp.contains(" ")) {
                timestamp = timestamp.split(" ")[0];
                if (Integer.valueOf(timestamp) < 60 && Integer.valueOf(timestamp) >= 1)
                    SimpleUtils.pass("Compliance Page: The backstop is last updated " + timestamp + " mins ago");
                else
                    SimpleUtils.fail("Compliance Page: The backup is last updated " + timestamp + " mins ago actually", false);
            } else
                // SimpleUtils.fail("Compliance Page: The backup display \'" + lastUpdated.getText() + "\'",false);
                SimpleUtils.warn("SCH-2857: [DM View] DURATION.X-MINS displays instead of actual refresh time stamp");
        } else
            SimpleUtils.fail("Compliance Page: Timestamp failed to load", false);
    }

    @Override
    public void navigateToSchedule() throws Exception {
        if (isElementLoaded(scheduleConsoleMenu, 10)) {
            click(scheduleConsoleMenu);
            if (scheduleConsoleMenu.findElement(By.xpath("./..")).getAttribute("class").contains("active"))
                SimpleUtils.pass("Schedule Page: Click on Schedule console menu successfully");
            else
                SimpleUtils.fail("Schedule Page: It doesn't navigate to Schedule console menu after clicking", false);
        } else {
            SimpleUtils.fail("Schedule menu in left navigation is not loaded!",false);
        }
    }

    @Override
    public void clickOnComplianceConsoleMenu() throws Exception {
        if(isElementLoaded(complianceConsoleMenu,20)) {
            click(complianceConsoleMenu);
            if (complianceConsoleMenu.findElement(By.xpath("./..")).getAttribute("class").contains("active"))
                SimpleUtils.pass("Compliance Page: Click on Compliance console menu successfully");
            else
                SimpleUtils.fail("Compliance Page: It doesn't navigate to Compliance console menu after clicking", false);
        } else
            SimpleUtils.fail("Compliance Console Menu not loaded Successfully!", false);
    }

    @Override
    public void validateRefreshWhenNavigationBack() throws Exception {
        String timestamp1 = "";
        String timestamp2 = "";
        if (isElementLoaded(lastUpdated, 5)) {
            timestamp1 = lastUpdated.getText();
        } else if (isElementLoaded(justUpdated, 5)) {
            timestamp1 = justUpdated.getText();
        } else
            SimpleUtils.fail("Compliance Page: Timestamp failed to load", false);
        navigateToSchedule();
        clickOnComplianceConsoleMenu();
        if (isElementLoaded(lastUpdated, 5)) {
            timestamp2 = lastUpdated.getText();
        } else if (isElementLoaded(justUpdated, 5)) {
            timestamp2 = justUpdated.getText();
        } else
            SimpleUtils.fail("Compliance Page: Timestamp failed to load", false);
        if (timestamp2.equals(timestamp1) && !timestamp1.equals("") && !refreshButton.getAttribute("label").equals("Refreshing...")) {
            SimpleUtils.pass("Compliance Page: It keeps the previous Last Updated time, not refreshing every time");
        } else {
            SimpleUtils.fail("Compliance Page: It doesn't keep the previous Last Updated time", false);
        }
    }

    @Override
    public void navigateToPreviousWeek() throws Exception {
        int currentWeekIndex = -1;
        if (areListElementVisible(currentWeeks, 10)) {
            for (int i = 0; i < currentWeeks.size(); i++) {
                String className = currentWeeks.get(i).getAttribute("class");
                if (className.contains("day-week-picker-period-active")) {
                    currentWeekIndex = i;
                }
            }
            if (currentWeekIndex == 0 && isElementLoaded(calendarNavigationPreviousWeekArrow, 5)) {
                clickTheElement(calendarNavigationPreviousWeekArrow);
                if (areListElementVisible(currentWeeks, 5)) {
                    clickTheElement(currentWeeks.get(currentWeeks.size()-1));
                    SimpleUtils.pass("Navigate to previous week: '" + currentWeeks.get(currentWeeks.size()-1).getText() + "' Successfully!");
                }
            } else {
                clickTheElement(currentWeeks.get(currentWeekIndex - 1));
                SimpleUtils.pass("Navigate to previous week: '" + currentWeeks.get(currentWeekIndex - 1).getText() + "' Successfully!");
            }
        } else {
            SimpleUtils.fail("Current weeks' elements not loaded Successfully!", false);
        }
    }

    @Override
    public void navigateToNextWeek() throws Exception {
        int currentWeekIndex = -1;
        if (areListElementVisible(currentWeeks, 10)) {
            for (int i = 0; i < currentWeeks.size(); i++) {
                String className = currentWeeks.get(i).getAttribute("class");
                if (className.contains("day-week-picker-period-active")) {
                    currentWeekIndex = i;
                }
            }
            if (currentWeekIndex == (currentWeeks.size() - 1) && isElementLoaded(calendarNavigationNextWeekArrow, 5)) {
                clickTheElement(calendarNavigationNextWeekArrow);
                if (areListElementVisible(currentWeeks, 5)) {
                    clickTheElement(currentWeeks.get(0));
                    SimpleUtils.pass("Navigate to next week: '" + currentWeeks.get(0).getText() + "' Successfully!");
                }
            } else {
                clickTheElement(currentWeeks.get(currentWeekIndex + 1));
                SimpleUtils.pass("Navigate to next week: '" + currentWeeks.get(currentWeekIndex + 1).getText() + "' Successfully!");
            }
        } else {
            SimpleUtils.fail("Current weeks' elements not loaded Successfully!", false);
        }
    }

    @Override
    public List<String> getDataFromComplianceTableForGivenLocationInDMView(String location) throws Exception {
        List<String> complianceViolationsOnDMViewSmartCard = new ArrayList<>();
        boolean isLocationFound = false;
        if (areListElementVisible(rowsInAnalyticsTable,10)) {
            for (WebElement row :rowsInAnalyticsTable) {
                if (row.findElement(By.xpath("./div[1]/span/img/following-sibling::span")).getText().equals(location)) {
                    isLocationFound = true;
                    List<WebElement> dataElements = row.findElements(By.cssSelector(".ng-scope.col-fx-1"));
                    for (WebElement dataElement: dataElements) {
                        if (!dataElement.getAttribute("class").contains("ng-hide") && dataElement.getText() != null)
                            complianceViolationsOnDMViewSmartCard.add(dataElement.getText());
                    }
                    break;
                }
            }
        } else
            SimpleUtils.fail("Compliance Page: There are no locations in current district or failed to load",false);
        if (isLocationFound)
            SimpleUtils.pass("Compliance Page: Find the location " + location + " successfully");
        else
            SimpleUtils.fail("Compliance Page: Failed to find the location, try another location again",false);
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
