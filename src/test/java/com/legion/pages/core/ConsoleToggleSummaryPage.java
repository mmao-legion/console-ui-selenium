package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.ToggleSummaryPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleToggleSummaryPage extends BasePage implements ToggleSummaryPage {
    public ConsoleToggleSummaryPage() {
        PageFactory.initElements(getDriver(), this);
    }

    @FindBy(css = "tr[ng-repeat='day in summary.workingHours'] td:nth-child(1)")
    private List<WebElement> operatingHoursScheduleDay;

    @FindBy(css = "tr[ng-repeat='day in summary.workingHours'] td.text-right.ng-binding")
    private List<WebElement> scheduleOperatingHrsTimeDuration;

    @FindBy(css = "tr[ng-repeat=\"day in summary.workingHours\"]")
    private List<WebElement> operatingHoursRows;
    public HashMap<String, String> getOperatingHrsValue(String day) throws Exception {
        String currentDay = null;
        String nextDay = null;
        String finalDay = null;
        HashMap<String, String>  activeDayAndOperatingHrs = new HashMap<>();

        if(areListElementVisible(operatingHoursRows,5) &&
                areListElementVisible(operatingHoursScheduleDay,5) &&
                areListElementVisible(scheduleOperatingHrsTimeDuration,5)){
            for(int i =0; i<operatingHoursRows.size();i++){
                if(i == operatingHoursRows.size()-1){
                    if(operatingHoursScheduleDay.get(i).getText().substring(0,3).equalsIgnoreCase(day)){
                        currentDay = day;
                        nextDay = operatingHoursScheduleDay.get(0).getText().substring(0,3);
                        activeDayAndOperatingHrs.put("ScheduleOperatingHrs" , currentDay + "-" + scheduleOperatingHrsTimeDuration.get(i).getText());
                        SimpleUtils.pass("Current day in Schedule " + day + " matches" +
                                " with Operation hours Table " + operatingHoursScheduleDay.get(i).getText().substring(0,3));
                        break;
                    }else{
                        SimpleUtils.fail("Current day in Schedule " + day + " does not match" +
                                " with Operation hours Table " + operatingHoursScheduleDay.get(i).getText().substring(0,3),false);
                    }
                }else if(operatingHoursScheduleDay.get(i).getText().substring(0,3).equalsIgnoreCase(day)){
                    currentDay = day;
                    nextDay = operatingHoursScheduleDay.get(i+1).getText().substring(0,3);
                    activeDayAndOperatingHrs.put("ScheduleOperatingHrs" , currentDay + "-" + scheduleOperatingHrsTimeDuration.get(i).getText());
                    SimpleUtils.pass("Current day in Schedule " + day + " matches" +
                            " with Operation hours Table " + operatingHoursScheduleDay.get(i).getText().substring(0,3));
                    break;
                }

            }
        }else{
            SimpleUtils.fail("Operating hours table not loaded Successfully",false);
        }
        return activeDayAndOperatingHrs;
    }


    @Override
    public String getTheEarliestAndLatestTimeInSummaryView(HashMap<String, Integer> schedulePoliciesBufferHours) throws Exception {
        String day = null;
        String shiftStartTime = null;
        String shiftEndTime = null;
        double shiftStartTimeDouble = 12.0;
        double shiftEndTimeDouble = 0.0;
        HashMap<String, String> activeDayAndOperatingHrs = new HashMap<>();
        if (areListElementVisible(operatingHoursRows, 5) &&
                areListElementVisible(operatingHoursScheduleDay, 5) &&
                areListElementVisible(scheduleOperatingHrsTimeDuration, 5)) {
            for (int i = 0; i < operatingHoursRows.size(); i++) {
                if (scheduleOperatingHrsTimeDuration.get(i).getText().contains("Closed"))
                    continue;
                day = operatingHoursScheduleDay.get(i).getText().substring(0, 3);
                activeDayAndOperatingHrs = getOperatingHrsValue(day);
                shiftStartTime = (activeDayAndOperatingHrs.get("ScheduleOperatingHrs").split("-"))[1];
                if (shiftStartTime.endsWith("pm"))
                    continue;
                shiftEndTime = (activeDayAndOperatingHrs.get("ScheduleOperatingHrs").split("-"))[2];
                if (shiftStartTime.contains(":"))
                    shiftStartTime = shiftStartTime.replace(":", ".");
                if (shiftEndTime.contains(":"))
                    shiftEndTime = shiftEndTime.replace(":", ".");
                if (shiftStartTime.contains("am") && shiftStartTime.startsWith("12"))
                    shiftStartTime = shiftStartTime.replace("12", "0").replaceAll("[a-zA-Z]", "");
                if (shiftStartTime.contains("am") && !shiftStartTime.startsWith("12"))
                    shiftStartTime = shiftStartTime.replaceAll("[a-zA-Z]", "");
                if (shiftStartTime.contains("pm") && shiftStartTime.startsWith("12"))
                    shiftStartTime = shiftStartTime.replaceAll("[a-zA-Z]", "");
                if (shiftStartTime.contains("pm") && !shiftStartTime.startsWith("12"))
                    shiftStartTime = String.valueOf(Double.valueOf(shiftEndTime.replaceAll("[a-zA-Z]", "")) + 12);
                if (shiftEndTime.contains("am"))
                    shiftEndTime = shiftEndTime.replaceAll("[a-zA-Z]", "");
                if (shiftEndTime.contains("pm") && !shiftEndTime.startsWith("12"))
                    shiftEndTime = String.valueOf(Double.valueOf(shiftEndTime.replaceAll("[a-zA-Z]", "")) + 12);
                if (shiftStartTimeDouble > Double.valueOf(shiftStartTime))
                    shiftStartTimeDouble = Double.valueOf(shiftStartTime);
                if (shiftEndTimeDouble < Double.valueOf(shiftEndTime))
                    shiftEndTimeDouble = Double.valueOf(shiftEndTime);
            }
        } else {
            SimpleUtils.fail("Operating hours table not loaded Successfully", true);
        }
        return Integer.valueOf(((int) shiftStartTimeDouble) - schedulePoliciesBufferHours.get("openingBufferHours")).toString() + "-" +
                Integer.valueOf(((int) shiftEndTimeDouble) + schedulePoliciesBufferHours.get("closingBufferHours")).toString();
    }

    @FindBy(css = "lg-dropdown-menu[actions=\"moreActions\"]")
    private WebElement scheduleAdminDropDownBtn;

    @FindBy(css = "div[ng-repeat=\"action in actions\"]")
    private List<WebElement> scheduleAdminDropDownOptions;
    @Override
    public void toggleSummaryView() throws Exception {
        String toggleSummaryViewOptionText = "Toggle Summary View";
        if (isElementLoaded(scheduleAdminDropDownBtn, 10)) {
            click(scheduleAdminDropDownBtn);
            if (scheduleAdminDropDownOptions.size() > 0) {
                for (WebElement scheduleAdminDropDownOption : scheduleAdminDropDownOptions) {
                    if (scheduleAdminDropDownOption.getText().toLowerCase().contains(toggleSummaryViewOptionText.toLowerCase())) {
                        click(scheduleAdminDropDownOption);
                    }
                }
            } else
                SimpleUtils.fail("Schedule Page: Admin dropdown Options not loaded to Toggle Summary View for the Week : '"
                        + getActiveWeekText() + "'.", false);
        } else
            SimpleUtils.fail("Schedule Page: Admin dropdown not loaded to Toggle Summary View for the Week : '"
                    + getActiveWeekText() + "'.", false);
    }

    @FindBy(css = "div[ng-if=\"showSummaryView\"]")
    private WebElement summaryViewDiv;

    @Override
    public boolean isSummaryViewLoaded() throws Exception {
        if (isElementLoaded(summaryViewDiv))
            return true;
        return false;
    }

    // Added by Nora: For non dg flow create schedule
    @FindBy (className = "generate-modal-subheader-title")
    private WebElement generateModalTitle;
    @FindBy (css = "[ng-click=\"next()\"]")
    private WebElement nextButtonOnCreateSchedule;
    @FindBy (className = "generate-modal-week-container")
    private List<WebElement> availableCopyWeeks;
    @FindBy (css = "generate-modal-operating-hours-step [label=\"Edit\"]")
    private WebElement operatingHoursEditBtn;
    @FindBy (css = ".operating-hours-day-list-item.ng-scope")
    private List<WebElement> operatingHoursDayLists;
    @FindBy (css = "generate-modal-budget-step [label=\"Edit\"]")
    private WebElement editBudgetBtn;
    @FindBy (css = "generate-modal-budget-step [ng-repeat=\"r in summary.staffingGuidance.roleHours\"]")
    private List<WebElement> roleHoursRows;
    @FindBy (className = "sch-calendar-day-dimension")
    private List<WebElement> weekDayDimensions;
    @FindBy (css = "tbody tr")
    private List<WebElement> smartCardRows;
    @FindBy (css = ".generate-modal-week")
    private List<WebElement> createModalWeeks;
    @FindBy (css = ".holiday-text")
    private WebElement storeClosedText;
    @FindBy (css = "[ng-repeat*=\"summary.workingHours\"]")
    private List<WebElement> summaryWorkingHoursRows;
    @FindBy (css = "span.loading-icon.ng-scope")
    private WebElement loadingIcon;
    @FindBy (css = ".operating-hours-day-list-item.ng-scope")
    private List<WebElement> currentOperatingHours;


    @Override
    public void verifyClosedDaysInToggleSummaryView(List<String> weekDaysToClose) throws Exception {
        if (areListElementVisible(summaryWorkingHoursRows, 15) && summaryWorkingHoursRows.size() == 7) {
            for (WebElement row : summaryWorkingHoursRows) {
                List<WebElement> tds = row.findElements(By.tagName("td"));
                if (tds != null && tds.size() == 2) {
                    if (weekDaysToClose.contains(tds.get(0).getText())) {
                        if (tds.get(1).getText().equals("Closed")) {
                            SimpleUtils.pass("Verfied " + tds.get(0).getText() + " is \"Closed\"");
                        } else {
                            SimpleUtils.fail("Verified " + tds.get(0).getText() + " is not \"Closed\"", false);
                        }
                    }
                } else {
                    SimpleUtils.fail("Summary Operating Hours: Failed to find two td elements!", false);
                }
            }
        } else {
            SimpleUtils.fail("Summary Operating Hours rows not loaded Successfully!", false);
        }
    }

    @FindBy(css = "[ng-if=\"isGenerateOverview()\"] h1")
    private WebElement weekInfoBeforeCreateSchedule;
    @Override
    public String getWeekInfoBeforeCreateSchedule() throws Exception {
        String weekInfo = "";
        if (isElementLoaded(weekInfoBeforeCreateSchedule,10)){
            weekInfo = weekInfoBeforeCreateSchedule.getText().trim();
            if (weekInfo.contains("Week")) {
                weekInfo = weekInfo.substring(weekInfo.indexOf("Week"));
            }
        }
        return weekInfo;
    }

    @FindBy (css = ".text-right[ng-if=\"hasBudget\"]")
    private List<WebElement> budgetedHoursOnSTAFF;

    @FindBy (xpath = "//div[contains(text(), \"Weekly Budget\")]/following-sibling::h1[1]")
    private WebElement budgetHoursOnWeeklyBudget;
    @Override
    public List<String> getBudgetedHoursOnSTAFF() throws Exception {
        List<String> budgetedHours = new ArrayList<>();
        if (areListElementVisible(budgetedHoursOnSTAFF,10)) {
            for (WebElement e : budgetedHoursOnSTAFF) {
                budgetedHours.add(e.getText().trim());
            }
        } else
            SimpleUtils.fail("Budgeted Hours On STAFF failed to load",true);
        return budgetedHours;
    }

    @Override
    public String getBudgetOnWeeklyBudget() throws Exception {
        String budgetOnWeeklyBudget = "";
        if (budgetHoursOnWeeklyBudget.getText().contains(" ")) {
            budgetOnWeeklyBudget = budgetHoursOnWeeklyBudget.getText().split(" ")[0];
        }
        return budgetOnWeeklyBudget;
    }


    @FindBy(css = ".generate-schedule-staffing tr:not([ng-repeat]) th[class=\"text-right ng-binding\"]")
    private WebElement staffingGuidanceHrs;
    @Override
    public float getStaffingGuidanceHrs() throws Exception {
        float staffingGuidanceHours = 0;
        if (isElementLoaded(staffingGuidanceHrs,20) && SimpleUtils.isNumeric(staffingGuidanceHrs.getText().replace("\n",""))){
            staffingGuidanceHours = Float.parseFloat(staffingGuidanceHrs.getText().replace("\n",""));
        } else {
            SimpleUtils.fail("There is no Staffing guidance hours!", false);
        }
        return staffingGuidanceHours;
    }


    @FindBy(css = ".schedule-summary-search-dropdown [icon*=\"search.svg'\"]")
    private WebElement searchLocationBtn;
    @Override
    public boolean isLocationGroup() {
        try {
            if (isElementLoaded(searchLocationBtn, 10)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    //added by Haya
    @FindBy (css = "div.generate-schedule-stats")
    private WebElement scheduleSummary;
    @Override
    public void verifyOperatingHrsInToggleSummary(String day, String startTime, String endTime) throws Exception {
        if (isElementLoaded(scheduleSummary) && isElementLoaded(scheduleSummary.findElement(By.cssSelector("div[ng-class=\"hideItem('projected.sales')\"] table")))){
            List<WebElement> dayInSummary = scheduleSummary.findElements(By.cssSelector("div[ng-class=\"hideItem('projected.sales')\"] tr[ng-repeat=\"day in summary.workingHours\"]"));
            for (WebElement e : dayInSummary){
                if (e.getText().contains(day) && e.getText().contains(getTimeFormat(startTime)) && e.getText().contains(getTimeFormat(endTime))){
                    SimpleUtils.pass("Operating Hours is consistent with setting!");
                }
            }
        } else {
            SimpleUtils.fail("schedule summary fail to load!", false);
        }
    }


    //added by Haya. 09:00AM-->9am
    private String getTimeFormat(String time) throws Exception{
        String result = time.substring(0,2);
        if (time.contains("AM") | time.contains("am")){
            result = result.concat("am");
        } else {
            result = result.concat("pm");
        }
        if (result.indexOf("0")==0){
            result = result.substring(1);
        }
        return result;
    }
}
