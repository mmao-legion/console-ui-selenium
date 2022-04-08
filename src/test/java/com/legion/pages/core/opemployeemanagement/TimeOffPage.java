package com.legion.pages.core.opemployeemanagement;

import com.legion.pages.BasePage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;
import static java.lang.Integer.parseInt;

public class TimeOffPage extends BasePage {
    public TimeOffPage() {
        PageFactory.initElements(getDriver(), this);
    }

    // Added by Sophia
    @FindBy(css = "timeoff-management div.collapsible-title")
    private WebElement timeOffTab;
    //
    @FindBy(css = "lg-button[label='Create time off']>button")
    private WebElement createTimeOff;//New Time Off Request

    @FindBy(css = "div.accural-heading-content.ng-binding")
    private WebElement createTimeOffModalTitle;//New Time Off Request
    @FindBy(css = "lg-select#reason-type-select")
    private WebElement timeOffReasonSelect;
    @FindBy(css = "lg-select#reason-type-select div.lg-search-options div[title]")
    private List<WebElement> timeOffReasonOptions;
    @FindBy(css = "lg-select#reason-type-select div.lg-search-options div[title*='Annual Leave']")
    private WebElement annualLeave;
    @FindBy(css = "lg-select#reason-type-select div.lg-search-options div[title*='Floating Holiday']")
    private WebElement floatingHoliday;
    @FindBy(css = "lg-select#reason-type-select div.lg-search-options div[title*='New Year Holiday']")
    private WebElement newYearHoliday;
    @FindBy(css = "lg-select#reason-type-select div.lg-search-options div[title*='Sick']")
    private WebElement sick;
    @FindBy(css = "lg-select#reason-type-select div.lg-search-options div[title*='PTO']")
    private WebElement pto;
    @FindBy(css = "div.col-fix:nth-child(1) div.row-fx.ranged-calendar__days:nth-child(2)")
    private WebElement currentMonth;
    @FindBy(css = "div.col-fix:nth-child(2) div.row-fx.ranged-calendar__days:nth-child(2)")
    private WebElement nextMonth;
    @FindBy(css = "div.ranged-calendar__day.ng-binding.ng-scope.real-day")
    private List<WebElement> daysOnCalendar;
    @FindBy(css = "div.real-day.is-today")
    private WebElement today;
    @FindBy(css = "div.ranged-calendar__day.ng-binding.ng-scope.real-day")
    private WebElement timeOffEndDay;
    @FindBy(css = "all-day-control[options='startOptions'] lgn-check-box[checked='options.fullDay']>div")
    private WebElement startDateFullDayCheckBox;
    @FindBy(css = "all-day-control[options='startOptions'] lgn-check-box[checked='options.partialDay']>div")
    private WebElement startDatePartialDayCheckBox;
    @FindBy(css = "all-day-control[options='endOptions'] lgn-check-box[checked='options.fullDay']>div")
    private WebElement endDateFullDayCheckBox;
    @FindBy(css = "all-day-control[options='endOptions'] lgn-check-box[checked='options.partialDay']>div")
    private WebElement endDatePartialDayCheckBox;
    @FindBy(css = "span.all-day-label")
    private WebElement dayLabel;


    @FindBy(css = "div.text-danger.text-invalid-range.ng-binding")
    private WebElement requestErrorMessage;

    //balance board
    @FindBy(css = "div.balance-wrapper>div>span.count-block-label")
    private List<WebElement> timeOffTypes;
    @FindBy(css = "div.balance-wrapper>div:nth-child(1) span.count-block-counter-hours")
    private WebElement annualLeaveBal;

    //Edit time off balance
    @FindBy(css = "div.balance-action lg-button[label='Edit']>button")
    private WebElement editButton;
    @FindBy(css = "modal[modal-title='Edit Time Off Balance'] tbody tr:nth-child(2)>td:nth-child(3) input")
    private WebElement annualLeaveInput;
    @FindBy(css = "modal[modal-title='Edit Time Off Balance'] tbody tr:nth-child(3)>td:nth-child(3) input")
    private WebElement floatingHolidayInput;
    @FindBy(css = "modal[modal-title='Edit Time Off Balance'] tbody tr:nth-child(4)>td:nth-child(3) input")
    private WebElement sickInput;
    @FindBy(css = "modal[modal-title='Edit Time Off Balance'] tr:last-child>td:nth-child(3) input")
    private WebElement theLastTimeOffInputInEditModal;
    @FindBy(css = "modal[modal-title='Edit Time Off Balance'] table.lg-table tr.ng-scope>td:first-child")
    private List<WebElement> timeOffs;
    @FindBy(css = "modal[modal-title='Edit Time Off Balance'] table.lg-table td>input-field input")
    private List<WebElement> editInputs;


    //history
    @FindBy(css = "div.balance-action lg-button[label='History']>button")
    private WebElement historyButton;
    @FindBy(css = "h1.lg-slider-pop__title img.lg-slider-pop__title-dismiss")
    private WebElement historyCloseButton;
    @FindBy(css = "ul.session>li")
    private List<WebElement> historyItems;
    @FindBy(css = "ul.session>li div.templateInfo")
    private List<WebElement> balanceChanges;
    @FindBy(css = "ul.session>li p")
    private List<WebElement> accrualDates;

    //time off request
    @FindBy(css = "span.request-status.request-status-Approved")
    private WebElement approveStatus;
    @FindBy(css = "span.request-buttons-reject")
    private WebElement rejectButton;


    //balance check
    @FindBy(css = "div.balance-wrapper Span.count-block-label.ng-binding")
    private List<WebElement> timeOffKeys;
    @FindBy(css = "div.balance-wrapper Span.count-block-counter-hours")
    private List<WebElement> balances;

    //month
    @FindBy(css = "ranged-calendar.ng-isolate-scope")
    private WebElement Month;

    //cancel button
    @FindBy(css = "lg-button[label=Cancel]" )
    private WebElement cancelButton;


    public void goToTeamMemberDetail(String memberName) {
        String teamMemCssLocator = "span[title=' " + memberName + "']";
        WebElement teamMem = getDriver().findElement(By.cssSelector(teamMemCssLocator));
        scrollToElement(teamMem);
        teamMem.click();
    }

    public void switchToTimeOffTab() {
        waitForSeconds(15);
        scrollToElement(timeOffTab);
        timeOffTab.click();
    }

    public Boolean isTimeOffDisplayedInSelectList(String timeOffReason) {
        Boolean displayed = null;
        ArrayList<String> reasonOptions = getTimeOffOptions();
        for (int i = 0; i < reasonOptions.size(); i++) {
            displayed = reasonOptions.get(i).contains(timeOffReason);
            if (displayed)
                break;
        }
        return displayed;
    }

    public int getTimeOffIndex(String timeOffReason) {
        int i;
        ArrayList<String> reasonOptions = getTimeOffOptions();
        for (i = 0; i < reasonOptions.size(); i++) {
            if (reasonOptions.get(i).contains(timeOffReason)) {
                break;

            }
        }
        return i;
    }

    public boolean isPartialDayEnabled() {
        return isElementDisplayed(startDatePartialDayCheckBox);
    }

    public void checkTimeOffSelect() {
        createTimeOff.click();
        timeOffReasonSelect.click();
        waitForSeconds(3);
    }

    public void selectTimeOff(String timeOffReason) {
        checkTimeOffSelect();
        if (isTimeOffDisplayedInSelectList(timeOffReason)) {
            int index = getTimeOffIndex(timeOffReason);
            timeOffReasonOptions.get(index).click();
        } else {
            System.out.println("This type of time off can't be request or There is no this type of time off!");
        }
    }

    public void takePartialDay(int startDateIndex, int endDateIndex) {
        if (isPartialDayEnabled()) {
            startDatePartialDayCheckBox.click();
            endDatePartialDayCheckBox.click();
            daysOnCalendar.get(startDateIndex).click();
            daysOnCalendar.get(endDateIndex).click();
            waitForSeconds(5);
        } else {
            System.out.println("This type of time off can't request partial day!");
        }
    }

    public void takeAllDayLeave(int startDateIndex, int endDateIndex) {
        daysOnCalendar.get(startDateIndex).click();
        daysOnCalendar.get(endDateIndex).click();
        waitForSeconds(5);
    }

    public void createTimeOff(String timeOffReason, boolean takePartialDay, int startDateIndex, int endDateIndex) {
        selectTimeOff(timeOffReason);
        if (takePartialDay) {
            takePartialDay(startDateIndex, endDateIndex);
        } else {
            takeAllDayLeave(startDateIndex, endDateIndex);
        }
    }

    public String getRequestErrorMessage() {
        return requestErrorMessage.getText();
    }

    public void editTimeOffBalance(String timeOffName, String bal) {
        editButton.click();
        if (timeOffName.equalsIgnoreCase("Annual Leave")) {
            annualLeaveInput.clear();
            annualLeaveInput.sendKeys(bal);
        } else if (timeOffName.equalsIgnoreCase("Floating holiday")) {
            floatingHolidayInput.clear();
            floatingHolidayInput.sendKeys(bal);
        } else {
            System.out.println("No this type of time off!");
        }
    }

    public ArrayList<String> getTimeOffTypes() {
        ArrayList<String> timeOffs = getWebElementsText(timeOffTypes);
        return timeOffs;
    }

    public ArrayList<String> getTimeOffOptions() {
        ArrayList<String> timeOffOpts = new ArrayList<>();
        timeOffReasonOptions.forEach((e) -> {
            timeOffOpts.add(e.getAttribute("title"));
        });
        return timeOffOpts;
    }

    public String getAnnualLeaveBalance() {
        return annualLeaveBal.getText();
    }

    public void rejectTimeOffRequest() {
        approveStatus.click();
        if (isElementDisplayed(rejectButton)) {
            rejectButton.click();
        } else {
            System.out.println("The reject button doesn't displayed!");
        }
    }

    public int getToday() {
        String day = today.getText();
        return parseInt(day);
    }

    public HashMap<String, String> getTimeOffBalance() {
        ArrayList<String> keys = getWebElementsText(timeOffKeys);
        ArrayList<String> values = getWebElementsText(balances);
        HashMap timeOffBalance = new HashMap();
        int mapSize = keys.size();
        for (int i = 0; i < mapSize; i++) {
            timeOffBalance.put(keys.get(i), values.get(i));
        }
        return timeOffBalance;
    }

    public void editTheLastTimeOff(String balance) {
        editButton.click();
        theLastTimeOffInputInEditModal.clear();
        theLastTimeOffInputInEditModal.sendKeys(balance);
    }

    public void editTimeOff(HashMap<String, String> editNameValues) {
        editButton.click();
        ArrayList<String> timeOffNames = getWebElementsText(timeOffs);
        for (String key : editNameValues.keySet()
        ) {
            int index = timeOffNames.indexOf(key);
            editInputs.get(index).clear();
            editInputs.get(index).sendKeys(editNameValues.get(key));
        }
    }

    public HashMap<String, String> getAccrualHistory() {
        historyButton.click();
        HashMap<String, String> history = new HashMap();
        int size = getAccrualHistorySize();
        for (int i = 0; i < size; i++) {
            history.put(balanceChanges.get(i).getText(), accrualDates.get(i).getText());
        }
        historyCloseButton.click();
        return history;
    }

    public int getAccrualHistorySize() {
        return historyItems.size();
    }

    public String getMonth(){
        return Month.getText().substring(0,3);
    }

    public void cancelTimeOffRequest() throws Exception{
        if(isElementLoaded(cancelButton,5)){
            click(cancelButton);
        }
    }
}
