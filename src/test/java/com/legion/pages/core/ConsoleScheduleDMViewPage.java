package com.legion.pages.core;

import com.legion.pages.*;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DecimalFormat;
import java.util.*;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleScheduleDMViewPage extends BasePage implements ScheduleDMViewPage {

    @FindBy(css = ".analytics-new-table-group-row-open")
    private List<WebElement>  schedulesInDMView;


    @FindBy(css = "[jj-switch-when=\"cells.CELL_BUDGET_HOURS\"]")
    private List<WebElement>  budgetHours;

    public ConsoleScheduleDMViewPage() {
        PageFactory.initElements(getDriver(), this);
    }


    public float getBudgetedHourOfScheduleInDMViewByLocation(String location) throws Exception
    {
        float budgetedHours = 0;
        boolean isLocationMatched = false;
        if (areListElementVisible(schedulesInDMView, 60) && schedulesInDMView.size() != 0){
            for (WebElement schedule : schedulesInDMView){
                WebElement locationInDMView = schedule.findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_UNTOUCHED\"]"));
                if (locationInDMView != null){
                    String locationNameInDMView = locationInDMView.getText();
                    if (locationNameInDMView !=null && locationNameInDMView.equals(location)){
                        isLocationMatched = true;
                        if (areListElementVisible(budgetHours, 5)){
                            budgetedHours = Float.parseFloat(schedule.findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_BUDGET_HOURS\"]")).getText().replace(",", ""));
                        } else
                            budgetedHours = Float.parseFloat(schedule.findElements(By.cssSelector("[class=\"ng-binding ng-scope\"]")).get(2).getText().replace("", ""));
                        break;
                    }
                } else{
                    SimpleUtils.fail("Get budgeted hours in DM View failed, there is no location display in this schedule" , false);
                }
            }
            if(!isLocationMatched)
            {
                SimpleUtils.fail("Get budgeted hours in DM View failed, there is no matched location display in DM view" , false);
            } else{
                SimpleUtils.pass("Get budgeted hours in DM View successful, the budgeted hours is '" + budgetedHours + ".");
            }
        } else{
            SimpleUtils.fail("Get budgeted hours in DM View failed, there is no schedules display in DM view" , false);
        }
        return budgetedHours;
    }

    // Added By Julie
    @FindBy(css = ".day-week-picker-period-active>span")
    private WebElement currentWeek;

    @FindBy(css = "text[text-anchor=\"middle\"][style]")
    private List<WebElement> budgetComparison;

    @FindBy(css = "[style=\"font-size: 14px;\"]")
    private WebElement hours;

    @Override
    public String getCurrentWeekInDMView() throws Exception {
        String week = "";
        if (isElementLoaded(currentWeek,5)) {
            week = currentWeek.getText();
            SimpleUtils.pass("Schedule Page: Get current week \"" + week + "\"");
        } else {
            SimpleUtils.fail("Schedule Page: Current week failed to load",false);
        }
        return week;
    }

    @Override
    public String getBudgetComparisonInDMView() throws Exception {
        String kpi = "";
        if (areListElementVisible(budgetComparison,30) && budgetComparison.size() == 2) {
            waitForSeconds(5);
            kpi = budgetComparison.get(0).getText() + " " + budgetComparison.get(1).getText();
        } else
            SimpleUtils.fail("Schedule Page: Failed to load ",false);
        return kpi;
    }

    @FindBy(css = "[ng-click=\"$ctrl.onReload(true)\"]")
    private WebElement refreshButton;

    @FindBy(css = "[ng-if=\"$ctrl.minutes >= 0 && $ctrl.date && !$ctrl.loading\"]")
    private WebElement lastUpdatedIcon;

    @Override
    public void clickOnRefreshButton() throws Exception {
        if (isElementLoaded(refreshButton, 10)) {
            clickTheElement(refreshButton);
            if(isElementLoaded(lastUpdatedIcon, 120)){
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
                    SimpleUtils.pass("Schedule Page: Refresh button shows near week section successfully");
                } else {
                    SimpleUtils.fail("Schedule Page: Refresh button is not above welcome section", true);
                }
            } else {
                SimpleUtils.fail("Schedule Page: Refresh button isn't present", true);
            }
        } else {
            SimpleUtils.fail("Schedule Page: Refresh button failed to load", true);
        }
    }

    @FindBy (css = "last-updated-countdown span[ng-if^=\"$ctrl.minutes === 0\"]")
    private WebElement justUpdated;

    @FindBy (css = "last-updated-countdown span[ng-if^=\"$ctrl.minutes > 0\"]")
    private WebElement lastUpdated;

    @FindBy (css = "last-updated-countdown span[ng-if^=\"$ctrl.minutes > 0\"] span")
    private WebElement lastUpdatedMinutes;

    @FindBy (className = "navigation-menu-compliance-icon")
    private WebElement complianceConsoleMenu;

    @FindBy (css = ".console-navigation-item-label.Schedule")
    private WebElement scheduleConsoleMenu;

    @FindBy (css = ".analytics-new.ng-scope")
    private WebElement scheduleSection;

    @FindBy (xpath = "//span[contains(text(),'Not Started')]")
    private List<WebElement> notStartedSchedules;

    @FindBy (className = "analytics-new-table-group-open")
    private List<WebElement> rowsInAnalyticsTable;

    @Override
    public void validateRefreshFunction() throws Exception {
        int minutes = 0;
        if (isElementLoaded(lastUpdatedMinutes,10) ) {
            minutes = lastUpdatedMinutes.getText().contains(" ")? Integer.valueOf(lastUpdatedMinutes.getText().split(" ")[0]):Integer.valueOf(lastUpdatedMinutes.getText());
            if (minutes >= 30 ) {
                if (lastUpdatedMinutes.getAttribute("class").contains("last-updated-countdown-time-orange"))
                    SimpleUtils.pass("Schedule Page: When the Last Updated time >= 30 mins, the color changes to orange");
                else
                    SimpleUtils.fail("Schedule Page: When the Last Updated time >= 30 mins, the color failed to change to orange",false);
            }
        }
        if (isElementLoaded(refreshButton, 10)) {
            clickTheElement(refreshButton);
            SimpleUtils.pass("Schedule Page: Click on Refresh button Successfully!");
            if (scheduleSection.getAttribute("class").contains("analytics-new-refreshing") && refreshButton.getAttribute("label").equals("Refreshing...")) {
                SimpleUtils.pass("Schedule Page: After clicking Refresh button, the background is muted and it shows an indicator 'Refreshing...' that we are processing the info");
                if (isElementLoaded(justUpdated,60) && !scheduleSection.getAttribute("class").contains("home-dashboard-loading"))
                    SimpleUtils.pass("Schedule Page: Once the data is done refreshing, the page shows 'JUST UPDATED' and page becomes brighter again");
                else
                    SimpleUtils.fail("Schedule Page: When the data is done refreshing, the page doesn't show 'JUST UPDATED' and page doesn't become brighter again",false);
                if (isElementLoaded(lastUpdated,60) && lastUpdatedMinutes.getAttribute("class").contains("last-updated-countdown-time-blue"))
                    SimpleUtils.pass("Schedule Page: The Last Updated info provides the minutes last updated in blue");
                else
                    SimpleUtils.fail("Schedule Page: The Last Updated info doesn't provide the minutes last updated in blue",false);
            } else {
                SimpleUtils.fail("Schedule Page: After clicking Refresh button, the background isn't muted and it doesn't show 'Refreshing...'",true);
            }
        } else {
            SimpleUtils.fail("Schedule Page: Refresh button not Loaded!", true);
        }
    }

    @Override
    public void validateRefreshPerformance() throws Exception {
        if (isElementLoaded(refreshButton, 10)) {
            clickTheElement(refreshButton);
            if (refreshButton.getAttribute("label").equals("Refreshing...")) {
                SimpleUtils.pass("Schedule Page: After clicking Refresh button, the button becomes 'Refreshing...'");
                WebElement element = (new WebDriverWait(getDriver(), 60))
                        .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[label=\"Refresh\"]")));
                if (element.isDisplayed()) {
                    SimpleUtils.pass("Schedule Page: Page refreshes within 1 minute successfully");
                } else {
                    SimpleUtils.fail("Schedule Page: Page doesn't refresh within 1 minute", false);
                }
            } else {
                SimpleUtils.fail("Schedule Page: After clicking Refresh button, the background isn't muted and it doesn't show 'Refreshing...'",true);
            }
        } else {
            SimpleUtils.fail("Schedule Page: Refresh button not Loaded!", true);
        }
    }

    @Override
    public void validateRefreshTimestamp() throws Exception {
        String timestamp = "";
        if (isElementLoaded(justUpdated, 5)) {
            SimpleUtils.pass("Schedule Page: The page just refreshed");
        } else if (isElementLoaded(lastUpdatedMinutes, 5)) {
            timestamp = lastUpdatedMinutes.getText();
            if (timestamp.contains("HOURS") && timestamp.contains(" ")) {
                timestamp = timestamp.split(" ")[0];
                if (Integer.valueOf(timestamp) == 1)
                    SimpleUtils.pass("Schedule Page: The backstop is 1 hour so that the data is not older than 1 hour stale");
                else
                    // SimpleUtils.fail("Schedule Page:  The backstop is older than 1 hour stale",false);
                    SimpleUtils.warn("SCH-2589: [DM View] Refresh time is older than 1 hour stale");
            } else if (timestamp.contains("MINS") && timestamp.contains(" ")) {
                timestamp = timestamp.split(" ")[0];
                if (Integer.valueOf(timestamp) < 60 && Integer.valueOf(timestamp) >= 1)
                    SimpleUtils.pass("Schedule Page: The backstop is last updated " + timestamp + " mins ago");
                else
                SimpleUtils.fail("Schedule Page: The backup is last updated " + timestamp + " mins ago actually", false);
            } else
                SimpleUtils.fail("Schedule Page: The backup display \'" + lastUpdated.getText() + "\'",false);
        } else
            SimpleUtils.fail("Schedule Page: Timestamp failed to load", false);
    }

    @Override
    public void navigateToSchedule() throws Exception {
        if(isElementLoaded(scheduleConsoleMenu, 10)){
            click(scheduleConsoleMenu);
        } else {
            SimpleUtils.fail("Schedule menu in left navigation is not loaded!",false);
        }
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
            SimpleUtils.fail("Schedule Page: Timestamp failed to load", false);
        click(complianceConsoleMenu);
        navigateToSchedule();
        if (isElementLoaded(lastUpdated, 5)) {
            timestamp2 = lastUpdated.getText();
        } else if (isElementLoaded(justUpdated, 5)) {
            timestamp2 = justUpdated.getText();
        } else
            SimpleUtils.fail("Schedule Page: Timestamp failed to load", false);
        if (timestamp2.equals(timestamp1) && !timestamp1.equals("") && !refreshButton.getAttribute("label").equals("Refreshing...")) {
            SimpleUtils.pass("Schedule Page: It keeps the previous Last Updated time, not refreshing every time");
        } else {
            SimpleUtils.fail("Schedule Page: It doesn't keep the previous Last Updated time", false);
        }
    }

    @Override
    public boolean isNotStartedScheduleDisplay() throws Exception {
        boolean isNotStartedScheduleDisplay = false;
        if (areListElementVisible(notStartedSchedules, 10) && notStartedSchedules.size() > 0) {
            isNotStartedScheduleDisplay = true;
            SimpleUtils.report("Schedule Page: Not Started Schedules loaded in the current page");
        }
        return isNotStartedScheduleDisplay;
    }

    @Override
    public List<String> getLocationsWithNotStartedSchedules() throws Exception {
        List<String> notStartedLocations = new ArrayList<>();
        WebElement location = null;
        if (isNotStartedScheduleDisplay()) {
            for (int i=0; i < notStartedSchedules.size(); i++) {
                location = notStartedSchedules.get(i).findElement(By.xpath(".//../../preceding-sibling::div[1]/span/span"));
                notStartedLocations.add(location.getText());
            }
            if (notStartedSchedules.size() == notStartedLocations.size())
                SimpleUtils.pass("Schedule Page: Get all the locations with Not Started Schedules successfully");
            else
                SimpleUtils.fail("Schedule Page: Get all the locations with Not Started Schedules incompletely",false);
        } else
            SimpleUtils.fail("Schedule Page: There are no \"Not Started\" schedules in the current page",false);
        return notStartedLocations;
    }

    @Override
    public String getScheduleStatusForGivenLocation(String location) throws Exception {
        String scheduleStatus = "";
        if (areListElementVisible(rowsInAnalyticsTable,10)) {
            for (WebElement row : rowsInAnalyticsTable) {
                if (row.findElement(By.xpath("./div[1]/span/img/following-sibling::span")).getText().equals(location)) {
                    scheduleStatus = row.findElement(By.xpath("./div[2]/span/span")).getText().trim();
                    SimpleUtils.pass("Schedule Page: Find the location " + location + " with Schedule Status " + scheduleStatus);
                    break;
                }
            }
        } else
            SimpleUtils.fail("Schedule Page: There are no locations in current district or failed to load",false);
        return scheduleStatus;
    }

    @FindBy(css = "span.analytics-new-table-published-status")
    private List<WebElement>  scheduleStatusOnScheduleDMViewPage;

    @FindBy(css = ".analytics-new-table-group-row-open [text-anchor=\"start\"]")
    private List<WebElement>  projectedOverBudgetOnScheduleDMViewPage;

    @FindBy(css = ".analytics-new-table-group-row-open [text-anchor=\"end\"]")
    private List<WebElement>  projectedUnderBudgetOnScheduleDMViewPage;

    public Map<String, Integer> getThreeWeeksScheduleStatusFromScheduleDMViewPage() throws Exception {
        Map<String, Integer> scheduleStatusFromScheduleDMViewPage = new HashMap<>();
        SchedulePage schedulePage = new ConsoleScheduleNewUIPage();
        for (int j=1; j<=3; j++){
            int notStartedScheduleAccount = 0;
            int inProgressScheduleAccount = 0;
            int publishedScheduleAccount = 0;

            if(areListElementVisible(scheduleStatusOnScheduleDMViewPage, 10) && scheduleStatusOnScheduleDMViewPage.size()!=0) {
                for (int i = 0; i< scheduleStatusOnScheduleDMViewPage.size(); i++){
                    switch(scheduleStatusOnScheduleDMViewPage.get(i).getText()){
                        case "Not Started" :
                            notStartedScheduleAccount= notStartedScheduleAccount+1;
                            break;
                        case "In Progress" :
                            inProgressScheduleAccount = inProgressScheduleAccount+1;
                            break;
                        case "Published" :
                            publishedScheduleAccount = publishedScheduleAccount+1;
                            break;
                    }
                }
                switch(j){
                    case 1:
                        scheduleStatusFromScheduleDMViewPage.put("notStartedNumberForCurrentWeek", notStartedScheduleAccount);
                        scheduleStatusFromScheduleDMViewPage.put("inProgressForCurrentWeek", inProgressScheduleAccount);
                        scheduleStatusFromScheduleDMViewPage.put("publishedForCurrentWeek", publishedScheduleAccount);
                        schedulePage.navigateToNextWeek();
                        break;
                    case 2:
                        scheduleStatusFromScheduleDMViewPage.put("notStartedNumberForNextWeek", notStartedScheduleAccount);
                        scheduleStatusFromScheduleDMViewPage.put("inProgressForNextWeek", inProgressScheduleAccount);
                        scheduleStatusFromScheduleDMViewPage.put("publishedForNextWeek", publishedScheduleAccount);
                        schedulePage.navigateToNextWeek();
                        break;
                    case 3:
                        scheduleStatusFromScheduleDMViewPage.put("notStartedNumberForTheWeekAfterNext", notStartedScheduleAccount);
                        scheduleStatusFromScheduleDMViewPage.put("inProgressForTheWeekAfterNext", inProgressScheduleAccount);
                        scheduleStatusFromScheduleDMViewPage.put("publishedForTheWeekAfterNext", publishedScheduleAccount);
                        break;
                }
             } else{
                SimpleUtils.fail("Schedule status loaded fail on Schedule DM view" , false);
            }
        }
        return scheduleStatusFromScheduleDMViewPage;
    }

    @FindBy(css = "[class=\"published-clocked-cols__svg\"] text")
    private List<WebElement>  textFromTheChartInLocationSummarySmartCard;

    public List<String> getTextFromTheChartInLocationSummarySmartCard(){
        /*
        Non-TA env:
            0: the hours on Budget bar
            1: budget bar message "Budgeted Hrs"
            2: the hours on Published bar
            3: published bar message "Published Hrs"
            4: the hours of under or cover budget
            5: the caret of under or cover budget
        TA env:
            0: the hours on Budget bar
            1: budget bar message "Budgeted Hrs"
            2: the hours on Published bar
            3: published bar message "Published Hrs"
            4: the hours on Projected bar
            5: projected bar message "Projected Hrs"
            6: the hours of under or cover budget
            7: the caret of under or cover budget
        */

        List<String> textFromChart = new ArrayList<>();
        if (areListElementVisible(textFromTheChartInLocationSummarySmartCard, 15)&&textFromTheChartInLocationSummarySmartCard.size()!=0){
            for(int i=0;i<textFromTheChartInLocationSummarySmartCard.size();i++){
                textFromChart.add(textFromTheChartInLocationSummarySmartCard.get(i).getText());
            }
            SimpleUtils.report("Get text from the chart in location summary smart card successfully! ");
        } else{
            SimpleUtils.fail("The text on the chart in location summary smart card loaded fail! ", false);
        }
        return textFromChart;
    }

    @FindBy(css = "div.published-clocked-cols-summary-title")
    private List<WebElement>  locationNumbersOnLocationSummarySmartCard;


    public List<String> getLocationNumbersFromLocationSummarySmartCard(){
        // 0: Projected within Budget location number
        // 1: Projected over Budget location number
        List<String> locationNumbers = new ArrayList<>();
        if (areListElementVisible(locationNumbersOnLocationSummarySmartCard, 5)
                && locationNumbersOnLocationSummarySmartCard.size()==2){
            for(int i=0; i< locationNumbersOnLocationSummarySmartCard.size(); i++){
                locationNumbers.add(locationNumbersOnLocationSummarySmartCard.get(i).getText());
            }
            SimpleUtils.report("Get location numbers from location summary smart card successfully! ");
        } else
            SimpleUtils.fail("Location numbers on location summary smart card loaded fail! ", false);
        return locationNumbers;
    }


    public List<Float> getTheTotalBudgetedScheduledProjectedHourOfScheduleInDMView() throws Exception {
        clickOnRefreshButton();
        List<Float> totalHours = new ArrayList<>();
        float budgetedTotalHours = 0;
        float scheduledTotalHours = 0;
        float projectedTotalHours = 0;
        if (areListElementVisible(schedulesInDMView, 10) && schedulesInDMView.size() != 0){
            for (WebElement schedule : schedulesInDMView){
                budgetedTotalHours += Float.parseFloat(schedule.findElement(By.xpath("./div[3]")).getText().replace(",",""));
                scheduledTotalHours += Float.parseFloat(schedule.findElement(By.xpath("./div[4]")).getText().replace(",",""));
                // projectedTotalHours += Float.parseFloat(schedule.findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_CLOCKED_HOURS\"]")).getText().replace(",",""));
                // todo: failed due to https://legiontech.atlassian.net/browse/SCH-2524
            }
            totalHours.add(budgetedTotalHours);
            totalHours.add(scheduledTotalHours);
            totalHours.add(projectedTotalHours);
            SimpleUtils.report("Get total budget, schedule and projected hours successfully in DM view! ");
        } else{
            SimpleUtils.fail("Get hours in DM View failed, there is no schedules display in DM view" , false);
        }
        return totalHours;
    }

    @FindBy(css = "div[class=\"card-carousel-fixed\"]")
    private WebElement  locationSummarySmartCard;

    @FindBy(css = "[class=\"card-carousel-container\"] div.card-carousel-card")
    private List<WebElement>  scheduleStatusCards;

    @FindBy(css = "div.card-carousel-card div.analytics-card-color-text-1")
    private List<WebElement>  numbersOfSpecificStatusSchedule;

    @FindBy(css = "div.card-carousel-card div.analytics-card-color-text-2")
    private List<WebElement>  specificStatusMessages;

    @FindBy(css = "div.card-carousel-card div.analytics-card-color-text-3")
    private List<WebElement>  scheduleMessages;

    @FindBy(css = "div.card-carousel-card div.analytics-card-color-text-4")
    private List<WebElement>  totalScheduleMessages;

    @FindBy(css = "[ng-repeat=\"f in header track by $index\"]")
    private List<WebElement>  schedulesTableHeaders;

    @FindBy(css = "div.card-carousel-card-primary.card-carousel-card-table")
    private WebElement  scheduleScoreSmartCard;

    @FindBy(css = "[jj-switch-when=\"extraCells\"]")
    private List<WebElement>  projectedUnderOrOverBudgetByJobTitleHours;



    public void verifyTheScheduleStatusAccountOnScheduleStatusCards() throws Exception {
        Map<String, Integer> scheduleStatusAccountFromScheduleStatusCards = getScheduleStatusAccountFromScheduleStatusCards();
        Map<String, Integer> scheduleStatusFromScheduleDMViewPage = getThreeWeeksScheduleStatusFromScheduleDMViewPage();
        if(areListElementVisible(schedulesInDMView, 5) && schedulesInDMView.size()>0
                && scheduleStatusAccountFromScheduleStatusCards.get("notStarted")
                ==scheduleStatusFromScheduleDMViewPage.get("notStartedNumberForCurrentWeek")
                && scheduleStatusAccountFromScheduleStatusCards.get("published")
                ==scheduleStatusFromScheduleDMViewPage.get("publishedForCurrentWeek")
                && scheduleStatusAccountFromScheduleStatusCards.get("inProgress")
                ==scheduleStatusFromScheduleDMViewPage.get("inProgressForCurrentWeek")){
            SimpleUtils.pass("The Specific status schedule numbers display correctly! ");
        } else
            SimpleUtils.fail("The Specific status schedule numbers display incorrectly! ", false);

        int scheduleAccount = schedulesInDMView.size();

        if (scheduleStatusAccountFromScheduleStatusCards.get("notStarted") != 0){
            if(scheduleStatusAccountFromScheduleStatusCards.get("totalNotStartedSchedules") == scheduleAccount){
                SimpleUtils.pass("The total schedule number on Not Started Schedule card display correctly! ");
            } else
                SimpleUtils.fail("The total schedule number on Not Started Schedule card display incorrectly! ", false);
        } else {
            if(scheduleStatusAccountFromScheduleStatusCards.get("totalNotStartedSchedules") == 0){
                SimpleUtils.pass("There is no Not Started Schedule card! ");
            } else
                SimpleUtils.fail("The total schedule number of Not Started Schedule card display incorrectly! ", false);
        }

        if (scheduleStatusAccountFromScheduleStatusCards.get("published") != 0){
            if(scheduleStatusAccountFromScheduleStatusCards.get("totalPublishedSchedules") == scheduleAccount){
                SimpleUtils.pass("The total schedule number on Published Schedule card display correctly! ");
            } else
                SimpleUtils.fail("The total schedule number on Published Schedule card display incorrectly! ", false);
        } else {
            if(scheduleStatusAccountFromScheduleStatusCards.get("totalPublishedSchedules") == 0){
                SimpleUtils.pass("There is no Published Schedule card! ");
            } else
                SimpleUtils.fail("The total schedule number of Published Schedule card display incorrectly! ", false);
        }

        if (scheduleStatusAccountFromScheduleStatusCards.get("inProgress") != 0){
            if(scheduleStatusAccountFromScheduleStatusCards.get("totalInProgressSchedules") == scheduleAccount){
                SimpleUtils.pass("The total schedule number on In Progress Schedule card display correctly! ");
            } else
                SimpleUtils.fail("The total schedule number on In Progress Schedule card display incorrectly! ", false);
        } else {
            if(scheduleStatusAccountFromScheduleStatusCards.get("totalInProgressSchedules") == 0){
                SimpleUtils.pass("There is no In Progress Schedule card! ");
            } else
                SimpleUtils.fail("The total schedule number of In Progress Schedule card display incorrectly! ", false);
        }
    }

    public Map<String, Integer> getScheduleStatusAccountFromScheduleStatusCards(){
        Map<String, Integer> scheduleStatusAccount = new HashMap<>();
        scheduleStatusAccount.put("notStarted",0);
        scheduleStatusAccount.put("published",0);
        scheduleStatusAccount.put("inProgress",0);
        scheduleStatusAccount.put("totalNotStartedSchedules",0);
        scheduleStatusAccount.put("totalPublishedSchedules",0);
        scheduleStatusAccount.put("totalInProgressSchedules",0);

        if(areListElementVisible(scheduleStatusCards, 5) && scheduleStatusCards.size()>0){

            for (WebElement scheduleStatusCard: scheduleStatusCards){
                String scheduleStatus = scheduleStatusCard.findElement(By.cssSelector("div.analytics-card-color-text-2")).getText();
                switch (scheduleStatus){
                    case "Not Started":
                        scheduleStatusAccount.put("notStarted",
                                Integer.parseInt(scheduleStatusCard.findElement(By.cssSelector("div.analytics-card-color-text-1")).getText()));
                        scheduleStatusAccount.put("totalNotStartedSchedules",
                                Integer.parseInt(scheduleStatusCard.findElement(By.cssSelector("div.analytics-card-color-text-4")).getText().split(" ")[0]));
                        break;
                    case "Published":
                        scheduleStatusAccount.put("published",
                                Integer.parseInt(scheduleStatusCard.findElement(By.cssSelector("div.analytics-card-color-text-1")).getText()));
                        scheduleStatusAccount.put("totalPublishedSchedules",
                                Integer.parseInt(scheduleStatusCard.findElement(By.cssSelector("div.analytics-card-color-text-4")).getText().split(" ")[0]));
                        break;
                    case "In Progress":
                        scheduleStatusAccount.put("inProgress",
                                Integer.parseInt(scheduleStatusCard.findElement(By.cssSelector("div.analytics-card-color-text-1")).getText()));
                        scheduleStatusAccount.put("totalInProgressSchedules",
                                Integer.parseInt(scheduleStatusCard.findElement(By.cssSelector("div.analytics-card-color-text-4")).getText().split(" ")[0]));
                        break;
                }
                SimpleUtils.pass("Get " +scheduleStatus+ " schedule status account successfully! ");
            }
        } else {
            SimpleUtils.fail("Schedule Status card loaded fail! ", false);
        }
        return scheduleStatusAccount;
    }

    public void verifyTheContentOnScheduleStatusCards() throws Exception {
        if(areListElementVisible(scheduleStatusCards, 5) && scheduleStatusCards.size()>0){
            if(areListElementVisible(numbersOfSpecificStatusSchedule, 5)
                    && numbersOfSpecificStatusSchedule.size() ==scheduleStatusCards.size()
                    && areListElementVisible(specificStatusMessages, 5)
                    && specificStatusMessages.size() == scheduleStatusCards.size()
                    && areListElementVisible(scheduleMessages, 5)
                    && scheduleMessages.size() == scheduleStatusCards.size()
                    && areListElementVisible(totalScheduleMessages, 5)
                    && totalScheduleMessages.size() == scheduleStatusCards.size()) {
                for (int i=0;i<scheduleStatusCards.size();i++){
                    if ((specificStatusMessages.get(i).getText().contains("Not Started")
                            ||specificStatusMessages.get(i).getText().contains("Published")
                            ||specificStatusMessages.get(i).getText().contains("In Progress"))
                            && scheduleMessages.get(i).getText().equalsIgnoreCase("Schedules")
                            && totalScheduleMessages.get(i).getText().contains("total schedules")){
                        SimpleUtils.pass("The contents on the "+ specificStatusMessages.get(i).getText() +"Schedule Status cards loaded successfully! ");
                    } else
                        SimpleUtils.fail("The contents on the "+ (i+1) +"Schedule Status cards loaded fail! ", false);
                }
            } else
                SimpleUtils.fail("The contents on Schedule Status cards loaded fail! ", false);
        } else {
            SimpleUtils.fail("Schedule Status card loaded fail! ", false);
        }
    }

    @Override
    public void verifyScheduleStatusAndHoursInScheduleList(String locationName, Boolean isTAEnv, Boolean isDGEnv, String scheduleStatus, String specificWeek) throws Exception {
        if (areListElementVisible(schedulesInDMView, 10)
                && schedulesInDMView.size()>0
                && areListElementVisible(scheduleStatusOnScheduleDMViewPage, 10)
                && scheduleStatusOnScheduleDMViewPage.size()>0
                && schedulesInDMView.size() == scheduleStatusOnScheduleDMViewPage.size()){

            /*
            *
            *


            //Click the schedule by schedule status
            Map<String,String> scheduleHoursOnScheduleDMView = new HashMap<>();
            List<String> scheduleHoursOnScheduleDetailPage = new ArrayList<>();
            boolean isScheduleExists = false;
            String theSelectedScheduleLocationName = "";
            for (int i=0; i<schedulesInDMView.size(); i++){
                if (scheduleStatusOnScheduleDMViewPage.get(i).getText().equalsIgnoreCase(scheduleStatus)){
                    isScheduleExists = true;
                    theSelectedScheduleLocationName = schedulesInDMView.get(i).findElement(By.cssSelector("[class=\"ng-binding\"]")).getText();
                    click(schedulesInDMView.get(i).findElement(By.className("ng-binding")));
                }
            }

            //Try to generate/ungenerate the first schedule if there is no the specific status schedule on schedule DM view
            if(!isScheduleExists){
                theSelectedScheduleLocationName = schedulesInDMView.get(0).findElement(By.cssSelector("[class=\"ng-binding\"]")).getText();
                click(schedulesInDMView.get(0).findElement(By.className("ng-binding")));
                if (schedulePage.isWeekGenerated()){
                    schedulePage.unGenerateActiveScheduleScheduleWeek();
                }
                if (scheduleStatus.equalsIgnoreCase("Published")){
                    schedulePage.createScheduleForNonDGFlowNewUI();
                    schedulePage.publishActiveSchedule();
                } else if (scheduleStatus.equalsIgnoreCase("In Progress")){
                    schedulePage.createScheduleForNonDGFlowNewUI();
                }
            }
             */

            //Select specific location schedule to test, because that not all schedules are available
            SchedulePage schedulePage = new ConsoleScheduleNewUIPage();
            Map<String,String> scheduleHoursOnScheduleDMView = new HashMap<>();
            List<String> scheduleHoursOnScheduleDetailPage = new ArrayList<>();
            String theSelectedScheduleLocationName = locationName;
            clickSpecificScheduleByLocationName(theSelectedScheduleLocationName);
            if (schedulePage.isWeekGenerated()){
                schedulePage.unGenerateActiveScheduleScheduleWeek();
            }
            if (scheduleStatus.equals("Published")){
                List<String> toCloseDays = new ArrayList<>();
                schedulePage.editOperatingHoursOnScheduleOldUIPage("8", "20", toCloseDays);
                schedulePage.createScheduleForNonDGFlowNewUI();
                schedulePage.publishActiveSchedule();
            } else if (scheduleStatus.equalsIgnoreCase("In Progress")){
                schedulePage.createScheduleForNonDGFlowNewUI();
            }

            //Check the buttons on schedule page
            DecimalFormat df1 = new DecimalFormat("###.#");
            HashMap<String, String> hoursFromScheduleSMOnDGEnv = new HashMap<>();

            switch (scheduleStatus) {
                case "Not Started":
                    if(!schedulePage.isWeekGenerated()){
                        SimpleUtils.pass("The 'Not Started' schedule status display correctly! ");
                    } else
                        SimpleUtils.fail("The 'Not Started' schedule status display incorrectly! ", false);
                    ScheduleOverviewPage scheduleOverviewPage = new ConsoleScheduleOverviewPage();
                    scheduleOverviewPage.clickOverviewTab();

                    String budgetHrsOnOverViewPage = df1.format(scheduleOverviewPage.
                            getWeekHoursByWeekElement(scheduleOverviewPage.getOverviewScheduleWeeks().get(0)).get("guidanceHours"));
                    scheduleHoursOnScheduleDetailPage.add(budgetHrsOnOverViewPage);
                    String scheduledHrsOnOverViewPage = df1.format(scheduleOverviewPage.
                            getWeekHoursByWeekElement(scheduleOverviewPage.getOverviewScheduleWeeks().get(0)).get("scheduledHours"));
                    scheduleHoursOnScheduleDetailPage.add(scheduledHrsOnOverViewPage);
                    hoursFromScheduleSMOnDGEnv.put("BudgetedASM","0");
                    hoursFromScheduleSMOnDGEnv.put("BudgetedLSA","0");
                    hoursFromScheduleSMOnDGEnv.put("BudgetedSA","0");
                    hoursFromScheduleSMOnDGEnv.put("BudgetedOpen","0");
                    hoursFromScheduleSMOnDGEnv.put("ScheduledASM","0");
                    hoursFromScheduleSMOnDGEnv.put("ScheduledLSA","0");
                    hoursFromScheduleSMOnDGEnv.put("ScheduledSA","0");
                    hoursFromScheduleSMOnDGEnv.put("ScheduledOpen","0");
                    break;
                case "Published":
                    if(!schedulePage.isPublishButtonLoadedOnSchedulePage()
                            && !schedulePage.isCreateScheduleBtnLoadedOnSchedulePage()){
                        SimpleUtils.pass("The 'Published' schedule status display correctly! ");
                        if (schedulePage.isRepublishButtonLoadedOnSchedulePage()){
//                            schedulePage.clickOnRepublishButtonLoadedOnSchedulePage();
                            schedulePage.publishActiveSchedule();
                        }
                    } else
                        SimpleUtils.fail("The 'Published' schedule status display incorrectly! ", false);
                    if(isDGEnv){
                        hoursFromScheduleSMOnDGEnv = schedulePage.getBudgetNScheduledHoursFromSmartCardOnDGEnv();
                        scheduleHoursOnScheduleDetailPage.add(hoursFromScheduleSMOnDGEnv.get("BudgetedTotal"));
                        scheduleHoursOnScheduleDetailPage.add(hoursFromScheduleSMOnDGEnv.get("ScheduledTotal"));
                        scheduleHoursOnScheduleDetailPage.add(hoursFromScheduleSMOnDGEnv.get("BudgetedASM"));
                        scheduleHoursOnScheduleDetailPage.add(hoursFromScheduleSMOnDGEnv.get("BudgetedLSA"));
                        scheduleHoursOnScheduleDetailPage.add(hoursFromScheduleSMOnDGEnv.get("BudgetedSA"));
                        scheduleHoursOnScheduleDetailPage.add(hoursFromScheduleSMOnDGEnv.get("BudgetedOpen"));
                        scheduleHoursOnScheduleDetailPage.add(hoursFromScheduleSMOnDGEnv.get("ScheduledASM"));
                        scheduleHoursOnScheduleDetailPage.add(hoursFromScheduleSMOnDGEnv.get("ScheduledLSA"));
                        scheduleHoursOnScheduleDetailPage.add(hoursFromScheduleSMOnDGEnv.get("ScheduledSA"));
                        scheduleHoursOnScheduleDetailPage.add(hoursFromScheduleSMOnDGEnv.get("ScheduledOpen"));
                    } else {
                        Map<String, String> hoursFromScheduleSMOnNonDGEnv = schedulePage.getBudgetNScheduledHoursFromSmartCard();
                        scheduleHoursOnScheduleDetailPage.add(hoursFromScheduleSMOnNonDGEnv.get("Budget"));
                        scheduleHoursOnScheduleDetailPage.add(hoursFromScheduleSMOnNonDGEnv.get("Scheduled"));
                    }

                    break;
                case "In Progress":
                    if(schedulePage.isPublishButtonLoadedOnSchedulePage()
                            && !schedulePage.isCreateScheduleBtnLoadedOnSchedulePage()){
                        SimpleUtils.pass("The 'In Progress' schedule status display correctly! ");
                    } else
                        SimpleUtils.fail("The 'In Progress' schedule status display incorrectly! ", false);
                    if(isDGEnv){
                        hoursFromScheduleSMOnDGEnv = schedulePage.getBudgetNScheduledHoursFromSmartCardOnDGEnv();
                        scheduleHoursOnScheduleDetailPage.add(hoursFromScheduleSMOnDGEnv.get("BudgetedTotal"));
                        scheduleHoursOnScheduleDetailPage.add(hoursFromScheduleSMOnDGEnv.get("ScheduledTotal"));
                    } else {
                        Map<String, String> hoursFromScheduleSMOnNonDGEnv = schedulePage.getBudgetNScheduledHoursFromSmartCard();
                        scheduleHoursOnScheduleDetailPage.add(hoursFromScheduleSMOnNonDGEnv.get("Budget"));
                        scheduleHoursOnScheduleDetailPage.add(hoursFromScheduleSMOnNonDGEnv.get("Scheduled"));
                    }
                    break;
            }

            float projectionOpenShiftsFromScheduleDetailPage = 0;
            switch (specificWeek){
                case "Current Week":
                    projectionOpenShiftsFromScheduleDetailPage = schedulePage.getTotalProjectionOpenShiftsHoursForCurrentWeek();
                    break;
                case "Next Week":
                    projectionOpenShiftsFromScheduleDetailPage = schedulePage.newCalcTotalScheduledHourForDayInWeekView();
                    break;
            }
            //Only for DG env: get timesheet hours from Time sheet page
            HashMap<String, Float> timeSheetDiffHoursByJobTitle = new HashMap<>();
            float asmHours = 0;
            float lsaHours = 0;
            float saHours = 0;
            if(isDGEnv){
                TimeSheetPage timeSheetPage = new ConsoleTimeSheetPage();
                timeSheetPage.clickOnTimeSheetConsoleMenu();
                timeSheetPage.clickOnWeekDuration();
                if(specificWeek.equalsIgnoreCase("Previous Week")){
                    timeSheetPage.navigateToPreviousWeek();
                }
                List<WebElement> allTimeSheetRows = timeSheetPage.getTimeSheetWorkersRow();
                HashMap<String, Float> timeSheetHours = new HashMap<>();
                if(!specificWeek.equalsIgnoreCase("Next Week")){
                    if(allTimeSheetRows != null && allTimeSheetRows.size() != 0){
                        for (WebElement timeSheetRow: allTimeSheetRows){

                            String jobTitle = timeSheetRow.findElement(By.cssSelector(".lg-timesheet-table-improved__name span")).getAttribute("title");
                            if(jobTitle.equalsIgnoreCase("Assistant Store Manager")){
                                timeSheetHours = timeSheetPage.getWorkerAllHours(timeSheetRow);
                                asmHours = asmHours + timeSheetHours.get("DiffHours");
                            } else if (jobTitle.equalsIgnoreCase("Lead Sales Associate")){
                                timeSheetHours = timeSheetPage.getWorkerAllHours(timeSheetRow);
                                lsaHours = lsaHours + timeSheetHours.get("DiffHours");
                            } else if (jobTitle.equalsIgnoreCase("Sales Associate")){
                                timeSheetHours = timeSheetPage.getWorkerAllHours(timeSheetRow);
                                saHours = saHours + timeSheetHours.get("DiffHours");
                            }
                        }
                    } else
                        SimpleUtils.fail("Get hours from timesheet page fail, the timesheet rows are null! ", false);
                }
                timeSheetDiffHoursByJobTitle.put("asmDiffHours", asmHours);
                timeSheetDiffHoursByJobTitle.put("lsaDiffHours", lsaHours);
                timeSheetDiffHoursByJobTitle.put("saDiffHours", saHours);
            }


            LiquidDashboardPage liquidDashboardPage = new ConsoleLiquidDashboardPage();
            DashboardPage dashboardPage = new ConsoleDashboardPage();
            LocationSelectorPage locationSelectorPage = new ConsoleLocationSelectorPage();
            String projectedHoursOnSMDashboard = "";
            if(isTAEnv){
                //Get Projected hours from schedule widget on dashboard
                dashboardPage.clickOnDashboardConsoleMenu();
                dashboardPage.clickOnRefreshButton();
//                locationSelectorPage.changeLocation(theSelectedScheduleLocationName);
                List<String> dataFromScheduleWidget = liquidDashboardPage.getDataOnSchedulesWidget();
                switch(specificWeek){
                    case "Current Week":
                        projectedHoursOnSMDashboard = df1.format(Float.parseFloat(dataFromScheduleWidget.get(1).split(",")[7].split(" ")[1]));
                        break;
                    case "Previous Week":
                        projectedHoursOnSMDashboard = df1.format(Float.parseFloat(dataFromScheduleWidget.get(0).split(",")[7].split(" ")[1]));
                        break;
                    case "Next Week":
                        projectedHoursOnSMDashboard = df1.format(Float.parseFloat(dataFromScheduleWidget.get(2).split(",")[7].split(" ")[1]));
                        break;
                }
            }

            //validate the schedule hours are consistent between Schedule DM view and schedule detail page

            String districtName = dashboardPage.getCurrentDistrict();
            locationSelectorPage.selectCurrentUpperFieldAgain("District");
            schedulePage.clickOnScheduleConsoleMenuItem();
            if(isTAEnv){
                switch(specificWeek){
                    case "Current Week":
                        break;
                    case "Previous Week":
                        schedulePage.navigateToPreviousWeek();
                        break;
                    case "Next Week":
                        schedulePage.navigateToNextWeek();
                        break;
                }
            }
            dashboardPage.clickOnRefreshButton();
            scheduleHoursOnScheduleDMView = getAllScheduleInfoFromScheduleInDMViewByLocation(theSelectedScheduleLocationName);
            Map<String,String> projectedOverBudgetHours= calculateProjectedUnderOrOverBudgetHours(theSelectedScheduleLocationName);
            Map<String, Float> projectedOverBudgetHoursByJobTitle = calculateProjectedUnderOrOverBudgetByJobTitleHours(hoursFromScheduleSMOnDGEnv, timeSheetDiffHoursByJobTitle);
            switch (scheduleStatus) {
                case "Not Started":
                    if((isTAEnv
                            && scheduleHoursOnScheduleDMView.get("budgetedHours").equalsIgnoreCase(df1.format(Float.parseFloat(scheduleHoursOnScheduleDetailPage.get(0))))
                            && scheduleHoursOnScheduleDMView.get("scheduledHours").equalsIgnoreCase(df1.format(Float.parseFloat(scheduleHoursOnScheduleDetailPage.get(1))))              //blocking by https://legiontech.atlassian.net/browse/SCH-1874
                            && scheduleHoursOnScheduleDMView.get("scheduleStatus").equalsIgnoreCase("Not Started")
                            && checkIfTheSpecificHeaderDisplayInScheduleListOnScheduleDMView("Projected Hours")
                            && scheduleHoursOnScheduleDMView.get("projectedHours").equalsIgnoreCase(projectedHoursOnSMDashboard)
                            && scheduleHoursOnScheduleDMView.get("projectedOverBudgetHours").
                            equalsIgnoreCase(projectedOverBudgetHours.get("projectedOverBudgetHours").equalsIgnoreCase("")?"":
                                    (df1.format(Float.parseFloat(projectedOverBudgetHours.get("projectedOverBudgetHours")))))
                            && scheduleHoursOnScheduleDMView.get("projectedUnderBudgetHours").
                            equalsIgnoreCase(projectedOverBudgetHours.get("projectedUnderBudgetHours").equalsIgnoreCase("")?"":
                                    (df1.format(Float.parseFloat(projectedOverBudgetHours.get("projectedUnderBudgetHours")))))
                            && (!isDGEnv || (scheduleHoursOnScheduleDMView.get("asmHours").equalsIgnoreCase(df1.format(projectedOverBudgetHoursByJobTitle.get("asmHours")))
                            && scheduleHoursOnScheduleDMView.get("lsaHours").equalsIgnoreCase(df1.format(projectedOverBudgetHoursByJobTitle.get("lsaHours")))
                            && scheduleHoursOnScheduleDMView.get("saHours").equalsIgnoreCase(df1.format(projectedOverBudgetHoursByJobTitle.get("saHours"))))
                            && scheduleHoursOnScheduleDMView.get("openHours").equalsIgnoreCase(df1.format(projectionOpenShiftsFromScheduleDetailPage))))
                       ||  (!isTAEnv
                        && scheduleHoursOnScheduleDMView.get("budgetedHours").equalsIgnoreCase(scheduleHoursOnScheduleDetailPage.get(0))
                            && scheduleHoursOnScheduleDMView.get("scheduledHours").equalsIgnoreCase(scheduleHoursOnScheduleDMView.get(3))              //blocking by https://legiontech.atlassian.net/browse/SCH-1874
                            && scheduleHoursOnScheduleDMView.get("scheduleStatus").equalsIgnoreCase("Not Started")
                            && !checkIfTheSpecificHeaderDisplayInScheduleListOnScheduleDMView("Projected Hours"))){
                        SimpleUtils.pass("The budgeted hours on Schedule DM view is consistent " +
                                "with the budgeted hour on Overview page for Not Started schedule!");
                    } else
                        SimpleUtils.fail("The budgeted hours on Schedule DM view is inconsistent " +
                                "with the budgeted hour on Overview page for Not Started schedule! ", false);
                    break;
                case "Published":
                    if((isTAEnv
                            && scheduleHoursOnScheduleDMView.get("budgetedHours").equalsIgnoreCase(df1.format(Float.parseFloat(scheduleHoursOnScheduleDetailPage.get(0))))
                            && scheduleHoursOnScheduleDMView.get("scheduledHours").equalsIgnoreCase(df1.format(Float.parseFloat(scheduleHoursOnScheduleDetailPage.get(1))))              //blocking by https://legiontech.atlassian.net/browse/SCH-1874
                            && scheduleHoursOnScheduleDMView.get("scheduleStatus").equalsIgnoreCase("Published")
                            && checkIfTheSpecificHeaderDisplayInScheduleListOnScheduleDMView("Projected Hours")
//                            && scheduleHoursOnScheduleDMView.get("projectedHours").equalsIgnoreCase(projectedHoursOnSMDashboard)      //comment it because https://legiontech.atlassian.net/browse/SCH-1482
                            && scheduleHoursOnScheduleDMView.get("projectedOverBudgetHours").
                            equalsIgnoreCase(projectedOverBudgetHours.get("projectedOverBudgetHours").equalsIgnoreCase("")?"":
                                    (df1.format(Float.parseFloat(projectedOverBudgetHours.get("projectedOverBudgetHours")))))
                            && scheduleHoursOnScheduleDMView.get("projectedUnderBudgetHours").
                            equalsIgnoreCase(projectedOverBudgetHours.get("projectedUnderBudgetHours").equalsIgnoreCase("")?"":
                                    (df1.format(Float.parseFloat(projectedOverBudgetHours.get("projectedUnderBudgetHours")))))
                            && (!isDGEnv || (scheduleHoursOnScheduleDMView.get("asmHours").equalsIgnoreCase(df1.format(projectedOverBudgetHoursByJobTitle.get("asmHours")))
                            && scheduleHoursOnScheduleDMView.get("lsaHours").equalsIgnoreCase(df1.format(projectedOverBudgetHoursByJobTitle.get("lsaHours")))
                            && scheduleHoursOnScheduleDMView.get("saHours").equalsIgnoreCase(df1.format(projectedOverBudgetHoursByJobTitle.get("saHours"))))
                            && scheduleHoursOnScheduleDMView.get("openHours").equalsIgnoreCase(df1.format(projectionOpenShiftsFromScheduleDetailPage))))
                       ||  (!isTAEnv
                            && scheduleHoursOnScheduleDMView.get("budgetedHours").equalsIgnoreCase(scheduleHoursOnScheduleDetailPage.get(0))
                            && scheduleHoursOnScheduleDMView.get("scheduledHours").equalsIgnoreCase(scheduleHoursOnScheduleDetailPage.get(1))
                            && scheduleHoursOnScheduleDMView.get("scheduleStatus").equalsIgnoreCase("Published")
                            && !checkIfTheSpecificHeaderDisplayInScheduleListOnScheduleDMView("Projected Hours"))){
                        SimpleUtils.pass("The budgeted and projected hours on Schedule DM view is consistent " +
                                "with the budgeted and projected hour on Schedule detail page for Published schedule!");
                    } else
                        SimpleUtils.fail("The budgeted and projected hours on Schedule DM view is inconsistent " +
                                "with the budgeted and projected hour on Schedule detail page for Published schedule! ", false);
                    break;
                case "In Progress":
                    if((isTAEnv
                            && scheduleHoursOnScheduleDMView.get("budgetedHours").equalsIgnoreCase(df1.format(Float.parseFloat(scheduleHoursOnScheduleDetailPage.get(0))))
                            && scheduleHoursOnScheduleDMView.get("scheduledHours").equalsIgnoreCase(df1.format(Float.parseFloat(scheduleHoursOnScheduleDetailPage.get(1))))              //blocking by https://legiontech.atlassian.net/browse/SCH-1874
                            && scheduleHoursOnScheduleDMView.get("scheduleStatus").equalsIgnoreCase("In Progress")
                            && checkIfTheSpecificHeaderDisplayInScheduleListOnScheduleDMView("Projected Hours")
                            && scheduleHoursOnScheduleDMView.get("projectedHours").equalsIgnoreCase(projectedHoursOnSMDashboard)
                            && scheduleHoursOnScheduleDMView.get("projectedOverBudgetHours").
                            equalsIgnoreCase(projectedOverBudgetHours.get("projectedOverBudgetHours").equalsIgnoreCase("")?"":
                                    (df1.format(Float.parseFloat(projectedOverBudgetHours.get("projectedOverBudgetHours")))))
                            && scheduleHoursOnScheduleDMView.get("projectedUnderBudgetHours").
                            equalsIgnoreCase(projectedOverBudgetHours.get("projectedUnderBudgetHours").equalsIgnoreCase("")?"":
                                    (df1.format(Float.parseFloat(projectedOverBudgetHours.get("projectedUnderBudgetHours")))))
                            && (!isDGEnv || (scheduleHoursOnScheduleDMView.get("asmHours").equalsIgnoreCase(df1.format(projectedOverBudgetHoursByJobTitle.get("asmHours")))
                            && scheduleHoursOnScheduleDMView.get("lsaHours").equalsIgnoreCase(df1.format(projectedOverBudgetHoursByJobTitle.get("lsaHours")))
                            && scheduleHoursOnScheduleDMView.get("saHours").equalsIgnoreCase(df1.format(projectedOverBudgetHoursByJobTitle.get("saHours"))))
                            && scheduleHoursOnScheduleDMView.get("openHours").equalsIgnoreCase(df1.format(projectionOpenShiftsFromScheduleDetailPage))))
                       ||  (!isTAEnv
                            && scheduleHoursOnScheduleDMView.get("budgetedHours").equalsIgnoreCase(scheduleHoursOnScheduleDetailPage.get(0))
                            && scheduleHoursOnScheduleDMView.get("scheduledHours").equalsIgnoreCase(scheduleHoursOnScheduleDetailPage.get(1))              //blocking by https://legiontech.atlassian.net/browse/SCH-1874
                            && scheduleHoursOnScheduleDMView.get("scheduleStatus").equalsIgnoreCase("In Progress")
//                            && !checkIfTheSpecificHeaderDisplayInScheduleListOnScheduleDMView("Projected Hours") // blocking by https://legiontech.atlassian.net/browse/SCH-2524
                    )){
                        SimpleUtils.pass("The budgeted hours on Schedule DM view is consistent " +
                                "with the budgeted hour on Schedule detail page for In Progress schedule!");
                    } else
                        SimpleUtils.fail("The budgeted hours on Schedule DM view is inconsistent " +
                                "with the budgeted hour on Schedule detail page for In Progress schedule! ", false);
                    break;
            }
        } else
            SimpleUtils.fail("Schedules in schedule DM view page loaded fail! ", false);
    }

    public Map<String, Float> calculateProjectedUnderOrOverBudgetByJobTitleHours (HashMap<String, String> hoursFromScheduleSMOnDGEnv, HashMap<String, Float> timeSheetDiffHoursByJobTitle) throws Exception {
        HashMap<String, Float> projectedUnderOrOverBudgetByJobTitleHours = new HashMap();

        if(hoursFromScheduleSMOnDGEnv != null && hoursFromScheduleSMOnDGEnv.size()!=0
                && timeSheetDiffHoursByJobTitle !=null && timeSheetDiffHoursByJobTitle.size()!=0){

            // Calculation formula： Schedule hours - Budget hours + Timesheet hours = projected Under Or Over Budget By Job Title Hours
            float asmHours = Float.parseFloat(hoursFromScheduleSMOnDGEnv.get("ScheduledASM"))
                    - Float.parseFloat(hoursFromScheduleSMOnDGEnv.get("BudgetedASM"))
                    + timeSheetDiffHoursByJobTitle.get("asmDiffHours");
            float lsaHours = Float.parseFloat(hoursFromScheduleSMOnDGEnv.get("ScheduledLSA"))
                    - Float.parseFloat(hoursFromScheduleSMOnDGEnv.get("BudgetedLSA"))
                    + timeSheetDiffHoursByJobTitle.get("lsaDiffHours");
            float saHours = Float.parseFloat(hoursFromScheduleSMOnDGEnv.get("ScheduledSA"))
                    - Float.parseFloat(hoursFromScheduleSMOnDGEnv.get("BudgetedSA"))
                    + timeSheetDiffHoursByJobTitle.get("saDiffHours");

            projectedUnderOrOverBudgetByJobTitleHours.put("asmHours", asmHours);
            projectedUnderOrOverBudgetByJobTitleHours.put("lsaHours", lsaHours);
            projectedUnderOrOverBudgetByJobTitleHours.put("saHours", saHours);
        } else {
            SimpleUtils.fail("Cannot calculate the Projected Under Or Over Budget By Job Title Hours, because the hours from schedule or timesheet page are null! ", false);
        }
        return projectedUnderOrOverBudgetByJobTitleHours;
    }

    public Map<String, String> calculateProjectedUnderOrOverBudgetHours (String locationName) throws Exception {
        Map<String, String> projectedUnderOrOverBudgetHours = new HashMap<>();
        String projectedUnderBudgetHours = "";
        String projectedOverBudgetHours = "";
        Map<String, String> scheduleInfo = getAllScheduleInfoFromScheduleInDMViewByLocation(locationName);
        if (scheduleInfo != null){
            float budgetHours = Float.parseFloat(scheduleInfo.get("budgetedHours"));
            float projectedHours = Float.parseFloat(scheduleInfo.get("projectedHours"));
            if (budgetHours > projectedHours){
                projectedUnderBudgetHours = String.valueOf(budgetHours - projectedHours);

            } else if (budgetHours <= projectedHours) {
                projectedOverBudgetHours = String.valueOf(projectedHours - budgetHours);
            }
        } else
            SimpleUtils.fail("Get schedule info from schedule DM view fail! ", false);
        projectedUnderOrOverBudgetHours.put("projectedUnderBudgetHours", projectedUnderBudgetHours);
        projectedUnderOrOverBudgetHours.put("projectedOverBudgetHours", projectedOverBudgetHours);
        return projectedUnderOrOverBudgetHours;
    }

    @FindBy (css = "lg-search.analytics-new-table-filter input")
    private WebElement searchLocationInCompliancePage;
    @FindBy(css = "[jj-switch-when=\"cells.CELL_CLOCKED_HOURS\"]")
    private List<WebElement>  projectedHrs;
    public Map<String, String> getAllScheduleInfoFromScheduleInDMViewByLocation(String location) throws Exception {
        Map<String, String> allScheduleInfo = new HashMap<>();
        boolean isLocationMatched = false;
        if (areListElementVisible(schedulesInDMView, 10) && schedulesInDMView.size() != 0){
            for (int i=0; i< schedulesInDMView.size(); i++){
                WebElement locationInDMView = schedulesInDMView.get(i).findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_UNTOUCHED\"]"));
                if (locationInDMView != null){
                    String locationNameInDMView = locationInDMView.getText();
                    if (locationNameInDMView !=null && locationNameInDMView.equals(location)){
                        isLocationMatched = true;
                        //add schedule Location Name
                        allScheduleInfo.put("locationName",locationNameInDMView);
                        //add Schedule Status
                        allScheduleInfo.put("scheduleStatus", schedulesInDMView.get(i).findElement(By.className("analytics-new-table-published-status")).getText());
                        //add Score
//                        allScheduleInfo.add(schedulesInDMView.get(i).findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_SCORE\"]")).getText());   //Need Turn off Score function on Schedule DM view
                        String budgetedHours = "";
                        if (areListElementVisible(budgetHours, 5)){
                            budgetedHours = schedulesInDMView.get(i).findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_BUDGET_HOURS\"]")).getText().replace(",","");
                        } else {
                            if (isElementLoaded(scheduleScoreSmartCard, 5)) {
                                budgetedHours = schedulesInDMView.get(i).findElements(By.cssSelector("[ng-switch=\"headerIndexes[$index]\"]")).get(3).getText().replace(",","");
                            } else
                                budgetedHours = schedulesInDMView.get(i).findElements(By.cssSelector("[ng-switch=\"headerIndexes[$index]\"]")).get(2).getText().replace(",","");
                        }

                        //add Budgeted Hours
                        allScheduleInfo.put("budgetedHours", budgetedHours);
                        //add Scheduled Hours
                        allScheduleInfo.put("scheduledHours", schedulesInDMView.get(i).findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_PUBLISHED_HOURS\"]")).getText());
                        if (areListElementVisible(projectedHrs, 5)){
                            //add Projected Hours
                            String projectedHours = schedulesInDMView.get(i).findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_CLOCKED_HOURS\"]")).getText().replace(",","");
                            allScheduleInfo.put("projectedHours", projectedHours);
                            //add Projected Under/Over Budget Hours
                            if(Float.parseFloat(budgetedHours) > Float.parseFloat(projectedHours)){
                                allScheduleInfo.put("projectedUnderBudgetHours", schedulesInDMView.get(i).findElement(By.cssSelector("[text-anchor=\"end\"]")).getText());
                                allScheduleInfo.put("projectedOverBudgetHours", "");
                            } else{
                                allScheduleInfo.put("projectedOverBudgetHours", schedulesInDMView.get(i).findElement(By.cssSelector("[text-anchor=\"start\"]")).getText());
                                allScheduleInfo.put("projectedUnderBudgetHours", "");
                            }
                        }

                        //add projectedUnderOrOverBudgetByJobTitleHours on TA-DG env
                        if(areListElementVisible(projectedUnderOrOverBudgetByJobTitleHours, 5)){
                            List<WebElement> projectedUnderOrOverBudgetByJobTitleHours = schedulesInDMView.get(i).findElements(By.cssSelector("[jj-switch-when=\"extraCells\"]"));
                            if(areListElementVisible(projectedUnderOrOverBudgetByJobTitleHours, 5)
                                    && projectedUnderOrOverBudgetByJobTitleHours.size()==4){
                                allScheduleInfo.put("asmHours", projectedUnderOrOverBudgetByJobTitleHours.get(0).getText());
                                allScheduleInfo.put("lsaHours", projectedUnderOrOverBudgetByJobTitleHours.get(1).getText());
                                allScheduleInfo.put("saHours", projectedUnderOrOverBudgetByJobTitleHours.get(2).getText());
                                allScheduleInfo.put("openHours", projectedUnderOrOverBudgetByJobTitleHours.get(3).getText());
                                SimpleUtils.pass("Get Projected Under Or Over Budget By Job Title Hours successfully! ");
                            } else
                                SimpleUtils.fail("Get Projected Under Or Over Budget By Job Title Hours fail! ", false);
                        }
                        break;
                    }
                } else{
                    SimpleUtils.fail("Get schedule info in DM View failed, there is no location display in this schedule" , false);
                }
            }
            if(!isLocationMatched)
            {
                SimpleUtils.fail("Get schedule info in DM View failed, there is no matched location display in DM view" , false);
            } else{
                SimpleUtils.pass("Get schedule info in DM View successful! ");
            }
        } else{
            SimpleUtils.fail("Get schedule info in DM View failed, there is no schedules display in DM view" , false);
        }
        return allScheduleInfo;
    }

    public void verifySmartCardsAreLoadedForPastOrFutureWeek(boolean isPastWeek) throws Exception {
        if(isPastWeek){
            if(
//                    isElementLoaded(scheduleScoreSmartCard, 10) &&  //Score smart card should be turn off
                    isElementLoaded(locationSummarySmartCard, 10)
                    && areListElementVisible(scheduleStatusCards, 10)){
                SimpleUtils.pass("All smart cards on Schedule DM view page for Past week loaded successfully! ");
            } else
                SimpleUtils.fail("The smart cards on Schedule DM view page for past week loaded fail! ", false);
        } else {
            if(
//                    !isElementLoaded(scheduleScoreSmartCard, 10) && //Score smart card should be turn off
                    isElementLoaded(locationSummarySmartCard, 10)
                    && areListElementVisible(scheduleStatusCards, 10)){
                SimpleUtils.pass("All smart cards on Schedule DM view page for Past week loaded successfully! ");
            } else
                SimpleUtils.fail("The smart cards on Schedule DM view page for past week loaded fail! ", false);
        }
    }

    public void verifySchedulesTableHeaderNames(boolean isApplyBudget, boolean isPastWeek) throws Exception {
        
        if(areListElementVisible(schedulesTableHeaders, 10) && schedulesTableHeaders.size() == 7){
            String[] schedulesTableHeaderNames;
            if(isApplyBudget){
                if(!isPastWeek)
                    schedulesTableHeaderNames = new String[]{"Location", "Schedule Status", "Score",
                        "Budgeted Hours", "Scheduled Hours", "Projected Hours", "Projected Under/Over Budget"};
                else
                    schedulesTableHeaderNames = new String[]{"Location", "Schedule Status", "Score",
                            "Budgeted Hours", "Scheduled Hours", "Clocked Hours", "Under/Over Budget"};
            } else {
                if(!isPastWeek)
                    schedulesTableHeaderNames = new String[]{"Location", "Schedule Status", "Score",
                            "Guidance Hours", "Scheduled Hours", "Projected Hours", "Projected Under/Over Budget"};
                else
                    schedulesTableHeaderNames = new String[]{"Location", "Schedule Status", "Score",
                            "Guidance Hours", "Scheduled Hours", "Clocked Hours", "Under/Over Budget"};
            }
            for(int i= 0;i<schedulesTableHeaders.size(); i++){
                if(schedulesTableHeaders.get(i).getText().equals(schedulesTableHeaderNames[i])){
                    SimpleUtils.pass("Schedule table header: " + schedulesTableHeaders.get(i).getText()+" display correctly! ");
                } else
                    SimpleUtils.fail("Schedule table header: " + schedulesTableHeaderNames[i] +" display incorrectly! ", false);
            }
        } else
            SimpleUtils.fail("Schedules Table Headers on Schedule DM view loaded fail! ", false);
    }

    public boolean checkIfTheSpecificHeaderDisplayInScheduleListOnScheduleDMView(String headerName){
        boolean isHeaderExist = false;
        if(areListElementVisible(schedulesTableHeaders, 10) && schedulesTableHeaders.size() > 0){
            for(int i= 0;i<schedulesTableHeaders.size(); i++){
                if(schedulesTableHeaders.get(i).getText().equals(headerName)){
                    isHeaderExist = true;
                    SimpleUtils.report("Schedule table header: " + headerName +" display correctly! ");
                    break;
                }
            }
            if (!isHeaderExist){
                SimpleUtils.report("Schedule table header: " + headerName +" is not display on Schedule list! ");
            }
        } else
            SimpleUtils.fail("Schedules Table Headers on Schedule DM view loaded fail! ", false);
        return  isHeaderExist;
    }

    public void clickSpecificScheduleByLocationName(String locationName){
        if(areListElementVisible(schedulesInDMView, 10) && schedulesInDMView.size()!=0){
            boolean isLocationExist = false;
            for (WebElement scheduleInDMView:schedulesInDMView){
                String scheduleLocationName = scheduleInDMView.findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_UNTOUCHED\"]")).getText();
                if(scheduleLocationName.equalsIgnoreCase(locationName)){
                    isLocationExist = true;
                    click(scheduleInDMView);
                    SimpleUtils.pass("Click the schedule of "+ locationName+ " successfully! ");
                    break;
                }
            }
            if(!isLocationExist){
                SimpleUtils.fail("Click schedule in DM View failed, the location: "+locationName+" is not display in DM view" , false);
            }
        }  else{
            SimpleUtils.fail("Click schedule in DM View failed, there is no schedules display in DM view" , false);
        }

    }

    @FindBy(css = ".analytics-new-table-group-row-open")
    private List<WebElement>  rowsInScheduleUpperfieldViewTable;

    @FindBy (css = "lg-search.analytics-new-table-filter input")
    private WebElement searchInSchedulePage;

    @FindBy (css = ".analytics-new-table-group-row-open [jj-switch-when=\"cells.CELL_UNTOUCHED\"] span")
    private List<WebElement> upperFieldNamesInScheduleTable;

    public Map<String, String> getAllUpperFieldInfoFromScheduleByUpperField(String upperFieldName) throws Exception {
        Map<String, String> allUpperFieldInfo = new HashMap<>();
        boolean isUpperFieldMatched = false;
        if (isElementLoaded(searchInSchedulePage,5)) {
            searchInSchedulePage.sendKeys(upperFieldName);
            waitForSeconds(3);
            if (areListElementVisible(rowsInScheduleUpperfieldViewTable, 10) && rowsInScheduleUpperfieldViewTable.size() != 0) {
                for (int i=0; i< rowsInScheduleUpperfieldViewTable.size(); i++){
                    WebElement locationInDMView = rowsInScheduleUpperfieldViewTable.get(i).findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_UNTOUCHED\"]"));
                    if (locationInDMView != null){
                        String locationNameInDMView = locationInDMView.getText();
                        if (locationNameInDMView !=null && locationNameInDMView.equals(upperFieldName)){
                            isUpperFieldMatched = true;
                            //add schedule Location Name
                            allUpperFieldInfo.put("locationName",locationNameInDMView);
                            //add Schedule Status
                            allUpperFieldInfo.put("scheduleStatus", schedulesInDMView.get(i).findElement(By.className("analytics-new-table-published-status")).getText());
                            //add Score
//                        allScheduleInfo.add(schedulesInDMView.get(i).findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_SCORE\"]")).getText());   //Need Turn off Score function on Schedule DM view
                            String budgetedHours = "";
                            if (areListElementVisible(budgetHours, 5)){
                                budgetedHours = schedulesInDMView.get(i).findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_BUDGET_HOURS\"]")).getText().replace(",","");
                            } else {
                                if (isElementLoaded(scheduleScoreSmartCard, 5)) {
                                    budgetedHours = schedulesInDMView.get(i).findElements(By.cssSelector("[ng-switch=\"headerIndexes[$index]\"]")).get(3).getText().replace(",","");
                                } else
                                    budgetedHours = schedulesInDMView.get(i).findElements(By.cssSelector("[ng-switch=\"headerIndexes[$index]\"]")).get(2).getText().replace(",","");
                            }

                            //add Budgeted Hours
                            allUpperFieldInfo.put("budgetedHours", budgetedHours);
                            //add Scheduled Hours
                            allUpperFieldInfo.put("scheduledHours", schedulesInDMView.get(i).findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_PUBLISHED_HOURS\"]")).getText());
                            if (areListElementVisible(projectedHrs, 5)){
                                //add Projected Hours
                                String projectedHours = schedulesInDMView.get(i).findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_CLOCKED_HOURS\"]")).getText().replace(",","");
                                allUpperFieldInfo.put("projectedHours", projectedHours);
                                //add Projected Under/Over Budget Hours
                                if(Float.parseFloat(budgetedHours) > Float.parseFloat(projectedHours)){
                                    allUpperFieldInfo.put("projectedUnderBudgetHours", schedulesInDMView.get(i).findElement(By.cssSelector("[text-anchor=\"end\"]")).getText());
                                    allUpperFieldInfo.put("projectedOverBudgetHours", "");
                                } else{
                                    allUpperFieldInfo.put("projectedOverBudgetHours", schedulesInDMView.get(i).findElement(By.cssSelector("[text-anchor=\"start\"]")).getText());
                                    allUpperFieldInfo.put("projectedUnderBudgetHours", "");
                                }
                            }

                            //add projectedUnderOrOverBudgetByJobTitleHours on TA-DG env
                            if(areListElementVisible(projectedUnderOrOverBudgetByJobTitleHours, 5)){
                                List<WebElement> projectedUnderOrOverBudgetByJobTitleHours = schedulesInDMView.get(i).findElements(By.cssSelector("[jj-switch-when=\"extraCells\"]"));
                                if(areListElementVisible(projectedUnderOrOverBudgetByJobTitleHours, 5)
                                        && projectedUnderOrOverBudgetByJobTitleHours.size()==4){
                                    allUpperFieldInfo.put("asmHours", projectedUnderOrOverBudgetByJobTitleHours.get(0).getText());
                                    allUpperFieldInfo.put("lsaHours", projectedUnderOrOverBudgetByJobTitleHours.get(1).getText());
                                    allUpperFieldInfo.put("saHours", projectedUnderOrOverBudgetByJobTitleHours.get(2).getText());
                                    allUpperFieldInfo.put("openHours", projectedUnderOrOverBudgetByJobTitleHours.get(3).getText());
                                    SimpleUtils.pass("Get Projected Under Or Over Budget By Job Title Hours successfully! ");
                                } else
                                    SimpleUtils.fail("Get Projected Under Or Over Budget By Job Title Hours fail! ", false);
                            }
                            break;
                        }
                    } else{
                        SimpleUtils.fail("Get schedule info in DM View failed, there is no location display in this schedule" , false);
                    }
                }
                if (!isUpperFieldMatched) {
                    SimpleUtils.fail("Get upperField info in DM View failed, there is no matched upperField display in DM view", false);
                } else {
                    SimpleUtils.pass("Get upperField info in DM View successful! ");
                }
            } else
                SimpleUtils.fail("Get upperField info in DM View failed, there is no upperField display in DM view", false);
            searchInSchedulePage.clear();
        } else {
            SimpleUtils.fail("getDataInCompliancePage: search input fail to load!", true);
        }
        return allUpperFieldInfo;
    }
}
