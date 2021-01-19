package com.legion.pages.core;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import com.legion.pages.*;
import com.legion.utils.SimpleUtils;
import cucumber.api.java.ro.Si;
import cucumber.api.java.sl.In;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.DecimalFormat;
import java.util.*;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleScheduleDMViewPage extends BasePage implements ScheduleDMViewPage {

    @FindBy(css = ".analytics-new-table-group-row-open")
    private List<WebElement>  schedulesInDMView;


    @FindBy(css = "[jj-switch-when=\\\"cells.CELL_BUDGET_HOURS\\\"]")
    private List<WebElement>  budgetHours;

    public ConsoleScheduleDMViewPage() {
        PageFactory.initElements(getDriver(), this);
    }


    public float getBudgetedHourOfScheduleInDMViewByLocation(String location) throws Exception
    {
        float budgetedHours = 0;
        boolean isLocationMatched = false;
        if (areListElementVisible(schedulesInDMView, 10) && schedulesInDMView.size() != 0){
            for (WebElement schedule : schedulesInDMView){
                WebElement locationInDMView = schedule.findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_UNTOUCHED\"]"));
                if (locationInDMView != null){
                    String locationNameInDMView = locationInDMView.getText();
                    if (locationNameInDMView !=null && locationNameInDMView.equals(location)){
                        isLocationMatched = true;
                        if (areListElementVisible(budgetHours, 5)){
                            budgetedHours = Float.parseFloat(schedule.findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_BUDGET_HOURS\"]")).getText());
                        } else
                            budgetedHours = Float.parseFloat(schedule.findElements(By.cssSelector("[ng-switch=\"headerIndexes[$index]\"]")).get(2).getText());
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
    private List<WebElement> budgetSurplus;

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
    public String getBudgetSurplusInDMView() throws Exception {
        String kpi = "";
        if (areListElementVisible(budgetSurplus,30) && budgetSurplus.size() == 2) {
            waitForSeconds(5);
            kpi = budgetSurplus.get(0).getText() + " " + budgetSurplus.get(1).getText();
        } else
            SimpleUtils.fail("Schedule Page: Failed to load ",false);
        return kpi;
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
        if (areListElementVisible(textFromTheChartInLocationSummarySmartCard)&&textFromTheChartInLocationSummarySmartCard.size()!=0){
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


    public List<Float> getTheTotalBudgetedScheduledProjectedHourOfScheduleInDMView() {
        List<Float> totalHours = new ArrayList<>();
        float budgetedTotalHours = 0;
        float scheduledTotalHours = 0;
        float projectedTotalHours = 0;
        if (areListElementVisible(schedulesInDMView, 10) && schedulesInDMView.size() != 0){
            for (WebElement schedule : schedulesInDMView){
                budgetedTotalHours += Float.parseFloat(schedule.findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_BUDGET_HOURS\"]")).getText());
                scheduledTotalHours += Float.parseFloat(schedule.findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_PUBLISHED_HOURS\"]")).getText());
                projectedTotalHours += Float.parseFloat(schedule.findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_CLOCKED_HOURS\"]")).getText());
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
    public void verifyScheduleStatusAndHoursInScheduleList(String scheduleStatus) throws Exception {
        if (areListElementVisible(schedulesInDMView, 5)
                && schedulesInDMView.size()>0
                && areListElementVisible(scheduleStatusOnScheduleDMViewPage, 5)
                && scheduleStatusOnScheduleDMViewPage.size()>0
                && schedulesInDMView.size() == scheduleStatusOnScheduleDMViewPage.size()){

            SchedulePage schedulePage = new ConsoleScheduleNewUIPage();
            //Click the schedule by schedule status
            List<String> scheduleHoursOnScheduleDMView = new ArrayList<>();
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

            //Check the buttons on schedule page
            switch (scheduleStatus) {
                case "Not Started":
                    if(!schedulePage.isWeekGenerated()){
                        SimpleUtils.pass("The 'Not Started' schedule status display correctly! ");
                    } else
                        SimpleUtils.fail("The 'Not Started' schedule status display incorrectly! ", false);
                    ScheduleOverviewPage scheduleOverviewPage = new ConsoleScheduleOverviewPage();
                    scheduleOverviewPage.clickOverviewTab();
                    DecimalFormat df1 = new DecimalFormat("###.#");
                    String budgetHrsOnOverViewPage = df1.format(scheduleOverviewPage.
                            getWeekHoursByWeekElement(scheduleOverviewPage.getOverviewScheduleWeeks().get(0)).get("guidanceHours"));
                    scheduleHoursOnScheduleDetailPage.add(budgetHrsOnOverViewPage);
                    String scheduledHrsOnOverViewPage = df1.format(scheduleOverviewPage.
                            getWeekHoursByWeekElement(scheduleOverviewPage.getOverviewScheduleWeeks().get(0)).get("scheduledHours"));
                    scheduleHoursOnScheduleDetailPage.add(scheduledHrsOnOverViewPage);
                    break;
                case "Published":
                    if(!schedulePage.isPublishButtonLoadedOnSchedulePage()
                            && !schedulePage.isCreateScheduleBtnLoadedOnSchedulePage()){
                        SimpleUtils.pass("The 'Published' schedule status display correctly! ");
                        if (schedulePage.isRepublishButtonLoadedOnSchedulePage()){
                            schedulePage.clickOnRepublishButtonLoadedOnSchedulePage();
                        }
                    } else
                        SimpleUtils.fail("The 'Published' schedule status display incorrectly! ", false);
                    scheduleHoursOnScheduleDetailPage.add(schedulePage.getBudgetNScheduledHoursFromSmartCard().get("Budget"));
                    scheduleHoursOnScheduleDetailPage.add(schedulePage.getBudgetNScheduledHoursFromSmartCard().get("Scheduled"));
                    break;
                case "In Progress":
                    if(schedulePage.isPublishButtonLoadedOnSchedulePage()
                            && !schedulePage.isCreateScheduleBtnLoadedOnSchedulePage()){
                        SimpleUtils.pass("The 'In Progress' schedule status display correctly! ");
                    } else
                        SimpleUtils.fail("The 'In Progress' schedule status display incorrectly! ", false);
                    scheduleHoursOnScheduleDetailPage.add(schedulePage.getBudgetNScheduledHoursFromSmartCard().get("Budget"));
                    scheduleHoursOnScheduleDetailPage.add(schedulePage.getBudgetNScheduledHoursFromSmartCard().get("Scheduled"));
                    break;
            }


            //validate the schedule hours are consistent between Schedule DM view and schedule detail page
            DashboardPage dashboardPage = new ConsoleDashboardPage();
            String districtName = dashboardPage.getCurrentDistrict();
            LocationSelectorPage locationSelectorPage = new ConsoleLocationSelectorPage();
            locationSelectorPage.changeDistrictDirect();
            dashboardPage.clickOnRefreshButton();
            scheduleHoursOnScheduleDMView = getAllScheduleInfoFromScheduleInDMViewByLocation(theSelectedScheduleLocationName);

            switch (scheduleStatus) {
                case "Not Started":
                    if(scheduleHoursOnScheduleDMView.get(3).equalsIgnoreCase(scheduleHoursOnScheduleDetailPage.get(0))
                            && scheduleHoursOnScheduleDMView.get(1).equalsIgnoreCase("Not Started")){
                        SimpleUtils.pass("The budgeted hours on Schedule DM view is consistent " +
                                "with the budgeted hour on Overview page for Not Started schedule!");
                    } else
                        SimpleUtils.fail("The budgeted hours on Schedule DM view is inconsistent " +
                                "with the budgeted hour on Overview page for Not Started schedule! ", false);
                    break;
                case "Published":
                    if(scheduleHoursOnScheduleDMView.get(3).equalsIgnoreCase(scheduleHoursOnScheduleDetailPage.get(0))
                            && scheduleHoursOnScheduleDMView.get(4).equalsIgnoreCase(scheduleHoursOnScheduleDetailPage.get(1))
                            && scheduleHoursOnScheduleDMView.get(1).equalsIgnoreCase("Published")){
                        SimpleUtils.pass("The budgeted and projected hours on Schedule DM view is consistent " +
                                "with the budgeted and projected hour on Overview page for Published schedule!");
                    } else
                        SimpleUtils.fail("The budgeted and projected hours on Schedule DM view is inconsistent " +
                                "with the budgeted and projected hour on Overview page for Published schedule! ", false);
                    break;
                case "In Progress":
                    if(scheduleHoursOnScheduleDMView.get(3).equalsIgnoreCase(scheduleHoursOnScheduleDetailPage.get(0))
                            && scheduleHoursOnScheduleDMView.get(1).equalsIgnoreCase("In Progress")){
                        SimpleUtils.pass("The budgeted hours on Schedule DM view is consistent " +
                                "with the budgeted hour on Overview page for In Progress schedule!");
                    } else
                        SimpleUtils.fail("The budgeted hours on Schedule DM view is inconsistent " +
                                "with the budgeted hour on Overview page for In Progress schedule! ", false);
                    break;
            }

        } else
            SimpleUtils.fail("Schedules in schedule DM view page loaded fail! ", false);
    }

    public List<String> getAllScheduleInfoFromScheduleInDMViewByLocation(String location) throws Exception
    {
        List<String> allScheduleInfo = new ArrayList<>();
        boolean isLocationMatched = false;
        if (areListElementVisible(schedulesInDMView, 10) && schedulesInDMView.size() != 0){
            for (int i=0; i< schedulesInDMView.size(); i++){
                WebElement locationInDMView = schedulesInDMView.get(i).findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_UNTOUCHED\"]"));
                if (locationInDMView != null){
                    String locationNameInDMView = locationInDMView.getText();
                    if (locationNameInDMView !=null && locationNameInDMView.equals(location)){
                        isLocationMatched = true;
                        //add schedule Location Name
                        allScheduleInfo.add(locationNameInDMView);
                        //add Schedule Status
                        allScheduleInfo.add(schedulesInDMView.get(i).findElement(By.className("analytics-new-table-published-status")).getText());
                        //add Score
                        allScheduleInfo.add(schedulesInDMView.get(i).findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_SCORE\"]")).getText());
                        //add Budgeted Hours
                        allScheduleInfo.add(schedulesInDMView.get(i).findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_BUDGET_HOURS\"]")).getText());
                        //add Scheduled Hours
                        allScheduleInfo.add(schedulesInDMView.get(i).findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_PUBLISHED_HOURS\"]")).getText());
                        //add Projected Hours
                        allScheduleInfo.add(schedulesInDMView.get(i).findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_CLOCKED_HOURS\"]")).getText());
                        //add Projected Under/Over Budget Hours
                        if(areListElementVisible(projectedOverBudgetOnScheduleDMViewPage, 5)){
                            allScheduleInfo.add(projectedOverBudgetOnScheduleDMViewPage.get(0).getText());
                        } else if(areListElementVisible(projectedUnderBudgetOnScheduleDMViewPage, 5)){
                            allScheduleInfo.add(projectedUnderBudgetOnScheduleDMViewPage.get(0).getText());
                        } else {
                            SimpleUtils.fail("The Projected Under/Over Budget Hours loaded fail! ", false);
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
            if(isElementLoaded(scheduleScoreSmartCard, 10)
                    && isElementLoaded(locationSummarySmartCard, 10)
                    && areListElementVisible(scheduleStatusCards, 10)){
                SimpleUtils.pass("All smart cards on Schedule DM view page for Past week loaded successfully! ");
            } else
                SimpleUtils.fail("The smart cards on Schedule DM view page for past week loaded fail! ", false);
        } else {
            if(!isElementLoaded(scheduleScoreSmartCard, 10)
                    && isElementLoaded(locationSummarySmartCard, 10)
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
}
