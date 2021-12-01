package com.legion.pages.core.opemployeemanagement;

import com.legion.pages.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class TimeOffPage extends BasePage {
    public TimeOffPage() {
        PageFactory.initElements(getDriver(), this);
    }

    // Added by Sophia
    @FindBy(css = "span[title=' Allene Mante']")
    private WebElement teamMember;
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

    //Edit time off balance
    @FindBy(css = "div.balance-action lg-button[label='Edit']>button")
    private WebElement editButton;
    @FindBy(css = "modal[modal-title='Edit Time Off Balance'] tbody tr:nth-child(2)>td:nth-child(3) input")
    private WebElement annualLeaveInput;
    @FindBy(css = "modal[modal-title='Edit Time Off Balance'] tbody tr:nth-child(3)>td:nth-child(3) input")
    private WebElement floatingHolidayInput;
    @FindBy(css = "modal[modal-title='Edit Time Off Balance'] tbody tr:nth-child(4)>td:nth-child(3) input")
    private WebElement sickInput;

    //history
    @FindBy(css = "div.balance-action lg-button[label='History']>button")
    private WebElement historyButton;

    public void goToTeamMemberDetail() {
        teamMember.click();
        waitForSeconds(5);
    }

    public void switchToTimeOffTab() {
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

    public void editTimeOffBalance(String annualB, String floatingB, String sickB) {
        editButton.click();
        annualLeaveInput.click();
        annualLeaveInput.sendKeys("annualB");
        floatingHolidayInput.clear();
        floatingHolidayInput.sendKeys("floatingB");
        sickInput.clear();
        sickInput.sendKeys("sickB");
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


}
