package com.legion.pages.core.opemployeemanagement;

import com.legion.pages.BasePage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;

import static com.legion.utils.MyThreadLocal.getDriver;
import static java.lang.Integer.parseInt;

public class TimeOffPage extends BasePage {
    public TimeOffPage() {
        PageFactory.initElements(getDriver(), this);
    }

    // Added by Sophia
    @FindBy(css = "timeoff-management div.collapsible-title")
    private WebElement timeOffTab;
    @FindBy(css = "div.lg-toast")
    private WebElement toastMessage;
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
    @FindBy(css = "div.ranged-calendar__day.ng-binding.ng-scope.real-day.is-today.in-range")
    private WebElement currentDay;
    @FindBy(css = "div.ranged-calendar__month-name")
    private WebElement targetMonth;
    @FindBy(css = "a.calendar-nav-left")
    private WebElement calendarNavArrow;
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
    private WebElement ptoInput;
    @FindBy(css = "modal[modal-title='Edit Time Off Balance'] tbody tr:nth-child(5)>td:nth-child(3) input")
    private WebElement sickInput;
    @FindBy(css = "modal[modal-title='Edit Time Off Balance'] tr:last-child>td:nth-child(3) input")
    private WebElement theLastTimeOffInputInEditModal;
    @FindBy(css = "modal[modal-title='Edit Time Off Balance'] table.lg-table tr.ng-scope>td:first-child")
    private List<WebElement> timeOffs;
    @FindBy(css = "modal[modal-title='Edit Time Off Balance'] table.lg-table td>input-field input")
    private List<WebElement> editInputs;
    @FindBy(css = "modal[modal-title='Edit Time Off Balance'] tbody tr")
    private List<WebElement> editTr;

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
    @FindBy(css = "div.timeoff-requests-request.row-fx")
    private List<WebElement> timeOffRequests;

    //balance check
    @FindBy(css = "div.balance-wrapper Span.count-block-label.ng-binding")
    private List<WebElement> timeOffKeys;
    @FindBy(css = "div.balance-wrapper Span.count-block-counter-hours")
    private List<WebElement> balances;

    //TimeSheet
    @FindBy(css = "lg-button[label='Add Timeclock']>button")
    private WebElement addTimeClockBtn;
    @FindBy(css = "modal[modal-title='Add Timeclock'] input-field[label='date'] ng-form")
    private WebElement dateForm;
    //

    public String getToastMessage() {
        return toastMessage.getText();
    }

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
            //endDatePartialDayCheckBox.click();
            daysOnCalendar.get(startDateIndex).click();
            daysOnCalendar.get(endDateIndex).click();
            startDatePartialDayCheckBox.click();
            waitForSeconds(5);
        } else {
            System.out.println("This type of time off can't request partial day!");
        }
    }

    public void createTimeOff(String timeOffReason, boolean takePartialDay, int startDateIndex, int endDateIndex) {
        selectTimeOff(timeOffReason);
        if (takePartialDay) {
            takePartialDay(startDateIndex, endDateIndex);
        } else {
            takeAllDayLeave(startDateIndex, endDateIndex);
        }
    }

    public void takeAllDayLeave(int startDateIndex, int endDateIndex) {
        daysOnCalendar.get(startDateIndex).click();
        daysOnCalendar.get(endDateIndex).click();
        waitForSeconds(5);
    }

    public void createOneDayTimeOff(String timeOffReason, String month, boolean takePartialDay, int startDateIndex, int endDateIndex) {
        selectTimeOff(timeOffReason);
        goToTargetMonth(month);
        if (takePartialDay) {
            takePartialDay(startDateIndex, endDateIndex);
        } else {
            takeAllDayLeave(startDateIndex, endDateIndex);
        }
    }

    public void createManyDaysTimeOff(String timeOffReason, String month, int startDateIndex, int endDateIndex) {
        selectTimeOff(timeOffReason);
        goToTargetMonth(month);
        takeAllDayLeave(startDateIndex, endDateIndex);
    }

    public String getRequestErrorMessage() {
        return requestErrorMessage.getText();
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

    public int getCurrentDay() {
        String day = currentDay.getText();
        return parseInt(day);
    }

    public HashMap<String, String> getTimeOffBalance() {
        waitForSeconds(3);
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

    //month
    @FindBy(css = "ranged-calendar.ng-isolate-scope")
    private WebElement Month;

    //cancel button
    @FindBy(css = "lg-button[label=Cancel]")
    private WebElement cancelButton;

    public String getMonth() {
        return Month.getText().substring(0, 3);
    }

    public void cancelTimeOffRequest() throws Exception {
        if (isElementLoaded(cancelButton, 5)) {
            click(cancelButton);
        }
    }

    @FindBy(css = "div.timeoff-requests-request.row-fx.cursor-pointer")
    private List<WebElement> timeOffList;

    public Integer getTimeOffSize() throws Exception {
        return timeOffList.size();
    }

    @FindBy(css = "span[data-tootik='Cancel']")
    private WebElement cancelCreatedTimeOff;

    public void cancelCreatedTimeOffRequest() throws Exception {
        waitForSeconds(5);
        scrollToElement(timeOffList.get(1));
        clickTheElement(timeOffList.get(1));
        clickTheElement(cancelCreatedTimeOff);
    }

    @FindBy(xpath = "//span[contains(@class,'request-status')]")
    private List<WebElement> timeOffStatus;

    public void verifyTimeOffStatus() throws Exception {
        waitForSeconds(5);
        if (timeOffStatus.get(1).getAttribute("innerText").toUpperCase().equals("CANCELLED") && timeOffStatus.get(3).getAttribute("innerText").toUpperCase().equals("REJECTED") && timeOffStatus.get(4).getAttribute("innerText").toUpperCase().equals("APPROVED")) {
            SimpleUtils.pass("Time off status is correct");
        } else
            SimpleUtils.fail("Time off status is wrong", false);
    }

    public String getWorkerId() {
        String url = getDriver().getCurrentUrl();
        return url.substring(url.lastIndexOf('/') + 1);
    }

    public int getTimeOffRequestNum() {
        return timeOffRequests.size();
    }

    public String getTimeOffRequestDate(int index) {
        return timeOffRequests.get(index).findElement(By.cssSelector("div.request-date")).getText();
    }

    public String getTimeOffRequestType(int index) {
        return timeOffRequests.get(index).findElement(By.cssSelector("div.request-body")).getText();
    }

    public String getTimeOffRequestStatus(int index) {
        return timeOffRequests.get(index).findElement(By.cssSelector("div.request-stat.text-right.col-fx-1")).getText();
    }

    public void goToTargetMonth(String month) {
        while (!targetMonth.getText().equals(month)) {//"March 2022"
            calendarNavArrow.click();
        }
    }

    public void editTimeOffBalance(String timeOff, String bal) {
        editButton.click();
        int timeOffNum = editTr.size() - 1;
        for (int i = 1; i <= timeOffNum; i++) {
            String timeOffName = editTr.get(i).findElement(By.cssSelector("td:nth-child(1)")).getText();
            if (timeOffName.contains(timeOff)) {
                WebElement inputBox = editTr.get(i).findElement(By.cssSelector("td:nth-child(3)>input-field input"));
                inputBox.clear();
                inputBox.sendKeys(bal);
                break;
            }
        }
    }

    @FindBy(css = "span.ml-5")
    private List<WebElement> units;

    public HashMap<String, String> getTimeOffUnit() {
        ArrayList<String> keys = getWebElementsText(timeOffKeys);
        ArrayList<String> values = getWebElementsText(units);
        HashMap timeOffUnit = new HashMap();
        int mapSize = keys.size();
        for (int i = 0; i < mapSize; i++) {
            timeOffUnit.put(keys.get(i), values.get(i));
        }
        return timeOffUnit;
    }

    @FindBy(css = "tr[ng-repeat = 'timeoffType in accruedHours track by $index'] > td:nth-child(1)")
    private List<WebElement> timeOffKeysInEdit;
    @FindBy(css = "tr[ng-repeat = 'timeoffType in accruedHours track by $index'] > td:nth-child(1) > span")
    private List<WebElement> timeOffUnitInUnit;

    public HashMap<String, String> getTimeOffUnitInEdit() {
        click(editButton);
        ArrayList<String> keys = getWebElementsText(timeOffKeysInEdit);
        ArrayList<String> values = getWebElementsText(timeOffUnitInUnit);
        HashMap timeOffUnitInEdit = new HashMap();
        int mapSize = keys.size();
        for (int i = 0; i < mapSize; i++) {
            timeOffUnitInEdit.put(keys.get(i).split("-")[0].trim(), values.get(i));
        }
        scrollToElement(cancelButton);
        click(cancelButton);
        return timeOffUnitInEdit;
    }

    public HashMap<String, String> getTimeOffUnitInCreateTimeOff() {
        click(createTimeOff);
        click(timeOffReasonSelect);
        ArrayList<String> keys = getWebElementsText(timeOffReasonOptions);
        HashMap timeOffUnitInCreateTimeOff = new HashMap();
        int mapSize = keys.size();
        for (int i = 0; i < mapSize; i++) {
            timeOffUnitInCreateTimeOff.put(keys.get(i).split("-")[0].trim(), keys.get(i).split("-")[1].trim());
        }
        scrollToElement(cancelButton);
        click(cancelButton);
        return timeOffUnitInCreateTimeOff;
    }

    @FindBy(css = "img.lg-slider-pop__title-dismiss")
    private WebElement closeIcon;
    @FindBy(css = "div.balance-action lg-button[label='History']>button")
    private WebElement history;
    @FindBy(css = "div#logContainer.lg-slider-pop__content.mt-20")
    private WebElement historyDetail;
    @FindBy(css = "lg-button[label= 'OK']")
    private WebElement okButton;
    public void verifyHistoryUnitType() throws Exception {
        scrollToElement(okButton);
        click(okButton);
        if(isElementEnabled(history,5)){
            highlightElement(history);
            click(history);
            if(isElementEnabled(historyDetail,5)){
                highlightElement(historyDetail);
                if (historyDetail.getText().contains("Balance days Edited by") && historyDetail.getText().contains("DayUnit Edited from 0 days to 0.2 days + 0.2 days")){
                    SimpleUtils.pass("Unit type is correct in history");
                    click(closeIcon);
                }else
                    SimpleUtils.fail("Unit type display wrong in history",false);
            }else
                SimpleUtils.fail("user history detail loaded failed",false);
        }else
            SimpleUtils.fail("user history loaded failed",false);
    }

    @FindBy(css = "input-field[label = 'Time Off Type']")
    private WebElement timeOffType;
    @FindBy(css = "input-field[label = 'History Type']")
    private WebElement historyType;
    @FindBy(css = "input-field[label = 'Action']")
    private WebElement action;
    @FindBy(css = "input-field[label = 'Time Off Type']>ng-form>input")
    private WebElement timeOffTypeInput;
    @FindBy(css = "input-field[label = 'History Type']>ng-form>input")
    private WebElement historyTypeInput;
    @FindBy(css = "input-field[label = 'Action']>ng-form>input")
    private WebElement actionInput;

    public void verifyHistoryType() throws Exception{
        click(history);
        if(isElementLoaded(timeOffType,5) && isElementLoaded(historyType,5) && isElementLoaded(action)){
            if(timeOffType.getAttribute("innerText").contains("Time Off Type") && historyType.getAttribute("innerText").contains("History Type") && action.getAttribute("innerText").contains("Action"))
                SimpleUtils.pass("History filter text is correct");
            else
                SimpleUtils.fail("History filter text is wrong",false);
        }else
            SimpleUtils.fail("Time Off Type or History Type or Action doesn't dispaly",false);
    }

    public void verifyHistoryTypeDefaultValue() throws Exception{
        if(isElementLoaded(timeOffTypeInput,5) && isElementLoaded(historyTypeInput,5) && isElementLoaded(actionInput,5)){
            if(timeOffTypeInput.getAttribute("placeholder").contains("All") && historyTypeInput.getAttribute("placeholder").contains("All") && actionInput.getAttribute("placeholder").contains("All"))
                SimpleUtils.pass("History filter default value is All");
            else
                SimpleUtils.fail("History filter default value is not All",false);
        }else
            SimpleUtils.fail("Select field loaded failed",false);
    }

    @FindBy(css = "div.show-more")
    private WebElement showMoreButton;
    @FindBy(css = "div[title = 'Time Off Request']")
    private WebElement timeOffRequest;
    @FindBy(css = "input-field[label = 'Time Off Type']>ng-form")
    private WebElement timeOffTypeSelect;
    @FindBy(css = "input-field[label = 'History Type']>ng-form")
    private WebElement historyTypeSelect;
    @FindBy(css = "input-field[label = 'Action']>ng-form")
    private WebElement actionSelect;

    public void timeOffRequestFilter() throws Exception{
        click(historyTypeSelect);
        click(timeOffRequest);

        if(isElementLoaded(showMoreButton)) {
            scrollToElement(showMoreButton);
            click(showMoreButton);
        }
        for(int i = 0; i < historyItems.size(); i++){
            if(historyItems.get(i).getAttribute("innerText").contains("taken"))
                SimpleUtils.pass("Time Off Request filter successfully");
            else
                SimpleUtils.fail("Time Off Request filter failed",false);
        }
    }

    @FindBy(css = "input[aria-label = 'Annual Leave']")
    private WebElement annualLeaveTimeOffReason;
    @FindBy(css = "input[aria-label = 'Annual Leave1']")
    private WebElement annualLeave1TimeOffReason;
    @FindBy(css = "input[aria-label = 'Annual Leave2']")
    private WebElement annualLeave2TimeOffReason;

    public void timeOffTypeSingleFilter() throws Exception{
        //click(timeOffTypeSelect);
        click(annualLeaveTimeOffReason);
        click(annualLeave1TimeOffReason);
        click(annualLeave2TimeOffReason);

        if(isElementLoaded(showMoreButton)) {
            scrollToElement(showMoreButton);
            click(showMoreButton);
        }
        for(int i = 0; i < historyItems.size(); i++){
            if(historyItems.get(i).getAttribute("innerText").contains("Annual Leave") && !historyItems.get(i).getAttribute("innerText").contains("Annual Leave1") && !historyItems.get(i).getAttribute("innerText").contains("Annual Leave2"))
                SimpleUtils.pass("Accrual Ledger filter successfully");
            else
                SimpleUtils.fail("Accrual Ledger filter failed",false);
        }
    }

    public void timeOffTypeMutiplyFilter() throws Exception{
        click(timeOffTypeSelect);
        click(annualLeave1TimeOffReason);
        click(annualLeave2TimeOffReason);

        if(isElementLoaded(showMoreButton)) {
            scrollToElement(showMoreButton);
            click(showMoreButton);
        }
        for(int i = 0; i < historyItems.size(); i++){
            if(historyItems.get(i).getAttribute("innerText").contains("Annual Leave") || historyItems.get(i).getAttribute("innerText").contains("Annual Leave1") || historyItems.get(i).getAttribute("innerText").contains("Annual Leave2"))
                SimpleUtils.pass("Accrual Ledger filter successfully");
            else
                SimpleUtils.fail("Accrual Ledger filter failed",false);
        }
    }

    @FindBy(css = "input[aria-label = 'All']")
    private WebElement timeOffTypeAll;

    public void timeOffTypeAllFilter() throws Exception{
        click(timeOffTypeAll);
        verifyHistorySize();
    }

    @FindBy(css = "div[title = 'Accrual Ledger']")
    private WebElement accrualLedger;
    @FindBy(css = "div[title = 'All']")
    private WebElement historyTypeAll;
    public void accrualLedgerFilter() throws Exception{
        click(historyTypeSelect);
        click(accrualLedger);

        if(isElementLoaded(showMoreButton)) {
            scrollToElement(showMoreButton);
            click(showMoreButton);
        }
        for(int i = 0; i < historyItems.size(); i++){
            if(!historyItems.get(i).getAttribute("innerText").contains("taken"))
                SimpleUtils.pass("Accrual Ledger filter successfully");
            else
                SimpleUtils.fail("Accrual Ledger filter failed",false);
        }

        click(historyTypeSelect);
        click(historyTypeAll);
    }

    public void historyTypeAllFilter() throws Exception{
        Boolean accrualLedgerFlag = false, timeOffRequestFlag = false;
        for(int i = 0; i < historyItems.size(); i++){
            if(!historyItems.get(i).getAttribute("innerText").contains("taken"))
                accrualLedgerFlag = true;
            if(historyItems.get(i).getAttribute("innerText").contains("taken"))
                timeOffRequestFlag = true;
        }

        if(accrualLedgerFlag == true && timeOffRequestFlag == true)
            SimpleUtils.pass("History type all filter successfully");
        else
            SimpleUtils.fail("History type all filter failed",false);
    }

    public void actionAllFilter() throws Exception{
        Boolean accrualFlag = false, accrualCapFlag = false;
        for(int i = 0; i < historyItems.size(); i++){
            if(historyItems.get(i).getAttribute("innerText").contains("Accrued"))
                accrualFlag = true;
            if(historyItems.get(i).getAttribute("innerText").contains("Deducted"))
                accrualCapFlag = true;
        }

        if(accrualFlag == true && accrualCapFlag == true)
            SimpleUtils.pass("Action all filter successfully");
        else
            SimpleUtils.fail("Action all filter failed",false);
    }

    @FindBy(css = "input[aria-label = 'Accrual']")
    private WebElement accrual;
    public void actionAccrualFilter() throws Exception{
        click(actionSelect);
        //accrualLedger.sendKeys(Keys.ENTER);
        click(accrual);

        Boolean accrualFlag = false, accrualCapFlag = false;
        for(int i = 0; i < historyItems.size(); i++){
            if(historyItems.get(i).getAttribute("innerText").contains("Accrued"))
                accrualFlag = true;
            if(historyItems.get(i).getAttribute("innerText").contains("Deducted"))
                accrualCapFlag = true;
        }

        if(accrualFlag == true && accrualCapFlag == false)
            SimpleUtils.pass("Action accrual filter successfully");
        else
            SimpleUtils.fail("Action accrual all filter failed",false);

        click(accrual);
    }

    @FindBy(css = "input-field[label = 'Accrual Cap']")
    private WebElement accrualCap;
    public void actionAccrualCapFilter() throws Exception{
        click(accrualCap);

        Boolean accrualFlag = false, accrualCapFlag = false;
        for(int i = 0; i < historyItems.size(); i++){
            if(historyItems.get(i).getAttribute("innerText").contains("Accrued"))
                accrualFlag = true;
            if(historyItems.get(i).getAttribute("innerText").contains("Deducted"))
                accrualCapFlag = true;
        }

        if(accrualFlag == false && accrualCapFlag == true)
            SimpleUtils.pass("Action accrual cap filter successfully");
        else
            SimpleUtils.fail("Action accrual cap filter failed",false);

        click(accrualCap);
        click(actionSelect);
    }

    public void verifyActionisDisable() throws Exception{
        if(actionSelect.getAttribute("class").contains("input-field-disable"))
            SimpleUtils.pass("Action select is disable");
        else
            SimpleUtils.fail("Action select is enable",false);
    }

    public void verifyHistorySize() throws Exception{
        if(isElementLoaded(showMoreButton,5)){
            scrollToElement(showMoreButton);
            click(showMoreButton);
        }
        if(isElementLoaded(showMoreButton,5)){
            scrollToElement(showMoreButton);
            click(showMoreButton);
        }
        System.out.println(historyItems.size());
        if(historyItems.size() == 27)
            SimpleUtils.pass("All accrual display successfully");
        else
            SimpleUtils.fail("All accrual display failed",false);
    }

    @FindBy(css = "div.lg-slider-pop")
    private WebElement historyTab;

    public void closeHistory() throws Exception{
        click(historyCloseButton);
        if(isElementLoaded(historyTab))
            SimpleUtils.fail("Close history failed",false);
    }

    @FindBy(css = "input-field[label = 'Time Off Type']>label")
    private WebElement timeOffTypeText;
    @FindBy(css = "input-field[label = 'History Type']>label")
    private WebElement historyTypeText;
    @FindBy(css = "input-field[label = 'Action']>label")
    private WebElement actionText;

    public void verifyHistoryFilterUIText() throws Exception{
        if(timeOffTypeText.getAttribute("innerText").equals("Time Off Type") && historyTypeText.getAttribute("innerText").equals("History Type") && actionText.getAttribute("innerText").equals("Action"))
            SimpleUtils.pass("Filter text is correct");
        else
            SimpleUtils.fail("Filter text is wrong",false);
    }
}

