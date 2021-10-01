package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.ToggleSummaryPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
}
