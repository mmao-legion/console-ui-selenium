package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.MySchedulePage;
import com.legion.pages.ScheduleCommonPage;
import com.legion.pages.SmartCardPage;
import com.legion.tests.core.ScheduleNewUITest;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleMySchedulePage extends BasePage implements MySchedulePage {
    public ConsoleMySchedulePage() {
        PageFactory.initElements(getDriver(), this);
    }

    public enum monthsOfCalendar {
        Jan("January"),
        Feb("February"),
        Mar("March"),
        Apr("April"),
        May("May"),
        Jun("June"),
        Jul("July"),
        Aug("August"),
        Sep("September"),
        Oct("October"),
        Nov("November"),
        Dec("December");
        private final String value;

        monthsOfCalendar(final String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @FindBy(css = "div[ng-attr-class^=\"sch-date-title\"]")
    private List<WebElement> weekScheduleShiftsDateOfMySchedule;

    @FindBy (css = "div.day-week-picker-period-week.day-week-picker-period-active")
    private WebElement currentActiveWeek;

    @FindBy(css = ".my-schedule-no-schedule")
    private WebElement myScheduleNoSchedule;
    @FindBy(className = "day-week-picker-arrow-right")
    private WebElement calendarNavigationNextWeekArrow;

    @FindBy(className = "day-week-picker-arrow-left")
    private WebElement calendarNavigationPreviousWeekArrow;

    @FindBy (css = ".day-week-picker-period-week")
    private List<WebElement> currentWeeks;

    @FindBy (className = "period-name")
    private WebElement periodName;

    @Override
    public void validateTheDataAccordingToTheSelectedWeek() throws Exception {
        ScheduleCommonPage scheduleCommonPage = new ConsoleScheduleCommonPage();
        if (isElementLoaded(calendarNavigationPreviousWeekArrow, 10)) {
            scheduleCommonPage.navigateWeekViewOrDayViewToPastOrFuture(ScheduleNewUITest.weekViewType.Previous.getValue(), ScheduleNewUITest.weekCount.Two.getValue());
        } else if (isElementLoaded(calendarNavigationNextWeekArrow, 10)) {
            scheduleCommonPage.navigateWeekViewOrDayViewToPastOrFuture(ScheduleNewUITest.weekViewType.Next.getValue(), ScheduleNewUITest.weekCount.Two.getValue());
        } else
            SimpleUtils.fail("My Schedule Page: Forward and backward button failed to load to view previous or upcoming week", true);
        verifySelectOtherWeeks();
        validateTheScheduleShiftsAccordingToTheSelectedWeek();
    }

    public void validateTheScheduleShiftsAccordingToTheSelectedWeek() throws Exception {
        if (areListElementVisible(weekScheduleShiftsDateOfMySchedule, 20) && weekScheduleShiftsDateOfMySchedule.size() == 7 && isElementLoaded(currentActiveWeek, 5)) {
            String activeWeek = currentActiveWeek.getText();
            String weekScheduleShiftStartDate = weekScheduleShiftsDateOfMySchedule.get(0).getText();
            String weekScheduleShiftEndDate = weekScheduleShiftsDateOfMySchedule.get(6).getText();
            if (activeWeek.contains("\n") && weekScheduleShiftStartDate.contains(",") && weekScheduleShiftStartDate.contains(" ") && weekScheduleShiftEndDate.contains(",") && weekScheduleShiftEndDate.contains(" ") && activeWeek.contains("-")) {
                try {
                    if (weekScheduleShiftStartDate.split(",")[1].trim().split(" ")[1].startsWith("0")) {
                        weekScheduleShiftStartDate = weekScheduleShiftStartDate.split(",")[1].trim().split(" ")[0] + " " + weekScheduleShiftStartDate.split(",")[1].split(" ")[2].substring(1, 2);
                    } else {
                        weekScheduleShiftStartDate = weekScheduleShiftStartDate.split(",")[1].trim();
                    }
                    if (weekScheduleShiftEndDate.split(",")[1].trim().split(" ")[1].startsWith("0")) {
                        weekScheduleShiftEndDate = weekScheduleShiftEndDate.split(",")[1].trim().split(" ")[0] + " " + weekScheduleShiftEndDate.split(",")[1].split(" ")[2].substring(1, 2);
                    } else {
                        weekScheduleShiftEndDate = weekScheduleShiftEndDate.split(",")[1].trim();
                    }
                    activeWeek = activeWeek.split("\n")[1];
                    SimpleUtils.report("weekScheduleShiftStartDate is " + weekScheduleShiftStartDate);
                    SimpleUtils.report("weekScheduleShiftEndDate is " + weekScheduleShiftEndDate);
                    SimpleUtils.report("activeWeek is " + activeWeek);
                    if (weekScheduleShiftStartDate.equalsIgnoreCase(activeWeek.split("-")[0].trim()) && weekScheduleShiftEndDate.equalsIgnoreCase(activeWeek.split("-")[1].trim())) {
                        SimpleUtils.pass("My Schedule Page: The schedule shifts show according to the selected week successfully");
                    } else
                        SimpleUtils.fail("My Schedule Page: The schedule shifts failed to show according to the selected week", true);
                } catch (Exception e) {
                    SimpleUtils.fail("My Schedule Page: The schedule shifts texts don't have enough length ", true);
                }
            }
        } else if (isElementLoaded(myScheduleNoSchedule, 10)) {
            SimpleUtils.report("My Schedule Page: Schedule has not been generated.");
        } else {
            SimpleUtils.fail("My Schedule Page: Failed to load shifts", true);
        }
    }

    @Override
    public void verifySelectOtherWeeks() throws Exception {
        String currentWeekPeriod = "";
        String weekDate = "";
        if (areListElementVisible(currentWeeks, 10)) {
            for (int i = 0; i < currentWeeks.size(); i++) {
                click(currentWeeks.get(i));
                if (isElementLoaded(periodName, 5)) {
                    currentWeekPeriod = periodName.getText().length() > 12 ? periodName.getText().substring(12) : "";
                }
                if (currentWeeks.get(i).getText().contains("\n")) {
                    weekDate = currentWeeks.get(i).getText().split("\n").length >= 2 ? currentWeeks.get(i).getText().split("\n")[1] : "";
                    if (weekDate.contains("-")) {
                        String[] dates = weekDate.split("-");
                        String shortMonth1 = dates[0].trim().substring(0, 3);
                        String shortMonth2 = dates[1].trim().substring(0, 3);
                        String fullMonth1 = getFullMonthName(shortMonth1);
                        String fullMonth2 = getFullMonthName(shortMonth2);
                        weekDate = weekDate.replaceAll(shortMonth1, fullMonth1);
                        if (!shortMonth1.equalsIgnoreCase(shortMonth2)) {
                            weekDate = weekDate.replaceAll(shortMonth2, fullMonth2);
                        }
                    }
                }
                if (weekDate.trim().equalsIgnoreCase(currentWeekPeriod.trim())) {
                    SimpleUtils.pass("Selected week is: " + currentWeeks.get(i).getText() + " and current week is: " + currentWeekPeriod);
                }else {
                    SimpleUtils.fail("Selected week is: " + currentWeeks.get(i).getText() + " but current week is: " + currentWeekPeriod, false);
                }
                if (i == (currentWeeks.size() - 1) && isElementLoaded(calendarNavigationNextWeekArrow, 5)) {
                    click(calendarNavigationNextWeekArrow);
                    verifySelectOtherWeeks();
                }
            }
        }else {
            SimpleUtils.fail("Current weeks' elements not loaded Successfully!", false);
        }
    }


    public String getFullMonthName(String shortName) {
        String fullName = "";
        monthsOfCalendar[] shortNames = monthsOfCalendar.values();
        for (int i = 0; i < shortNames.length; i++) {
            if (shortNames[i].name().equalsIgnoreCase(shortName)) {
                fullName = shortNames[i].value;
                SimpleUtils.report("Get the full name of " + shortName + ", is: " + fullName);
                break;
            }
        }
        return fullName;
    }

    @FindBy(css = ".sch-day-view-shift")
    private List<WebElement> dayViewAvailableShifts;

    @FindBy(css = "h1[ng-if=\"weeklyScheduleData.hasSchedule !== 'FALSE'\"]")
    private WebElement openShiftData;
    @Override
    public void validateTheNumberOfOpenShifts() throws Exception {
        SmartCardPage smartCardPage = new ConsoleSmartCardPage();
        if (smartCardPage.isViewShiftsBtnPresent()) {
            if (areListElementVisible(dayViewAvailableShifts, 10)) {
                if (dayViewAvailableShifts.size() == Integer.valueOf(openShiftData.getText().replaceAll("[^0-9]", "")))
                    SimpleUtils.pass("My Schedule Page: The total number of open shifts in smartcard matches with the open shifts available in the schedule table successfully");
                else
                    SimpleUtils.fail("My Schedule Page: The total number of open shifts in smartcard doesn't match with the open shifts available in the schedule table", true);
            } else SimpleUtils.fail("My Schedule Page: Open shifts failed to load in the schedule table", true);
        }
    }

    @FindBy(className = "sch-worker-popover")
    private WebElement popOverLayout;
    @Override
    public void verifyTheAvailabilityOfClaimOpenShiftPopup() throws Exception {
        SmartCardPage smartCardPage = new ConsoleSmartCardPage();
        List<String> claimShift = new ArrayList<>(Arrays.asList("Claim Shift"));
        if (smartCardPage.isViewShiftsBtnPresent()) {
            if (areListElementVisible(dayViewAvailableShifts, 10)) {
                int randomIndex = (new Random()).nextInt(dayViewAvailableShifts.size());
                moveToElementAndClick(dayViewAvailableShifts.get(randomIndex));
                if (isPopOverLayoutLoaded()) {
                    if (verifyShiftRequestButtonOnPopup(claimShift))
                        SimpleUtils.pass("My Schedule Page: A popup to claim the open shift shows successfully");
                    else SimpleUtils.fail("My Schedule Page: A popup to claim the open shift doesn't show", true);
                } else SimpleUtils.fail("My Schedule Page: No popup appears", true);
            } else SimpleUtils.fail("My Schedule Page: Open shifts failed to load in the schedule table", true);
        }
    }


    public boolean isPopOverLayoutLoaded() throws Exception {
        boolean isLoaded = false;
        if (isElementLoaded(popOverLayout, 15)) {
            isLoaded = true;
            SimpleUtils.pass("Pop over layout loaded Successfully!");
        }
        return isLoaded;
    }

    @FindBy(css = "span.sch-worker-action-label")
    private List<WebElement> shiftRequests;
    @Override
    public boolean verifyShiftRequestButtonOnPopup(List<String> requests) throws Exception {
        boolean isConsistent = false;
        List<String> shiftRequestsOnUI = new ArrayList<>();
        if (areListElementVisible(shiftRequests, 5)) {
            for (WebElement shiftRequest : shiftRequests) {
                shiftRequestsOnUI.add(shiftRequest.getText());
            }
        }
        if (shiftRequestsOnUI.containsAll(requests) && requests.containsAll(shiftRequestsOnUI)) {
            isConsistent = true;
            SimpleUtils.pass("Shift Requests loaded Successfully!");
        }
        return isConsistent;
    }

    // Added by Nora: for Team Member View
    @FindBy(className = "sch-day-view-shift-worker-detail")
    private List<WebElement> tmIcons;
    @FindBy(css = "div.lg-modal")
    private WebElement popUpWindow;
    @FindBy(className = "lg-modal__title-icon")
    private WebElement popUpWindowTitle;
    @FindBy(css = "[label=\"Cancel\"]")
    private WebElement cancelButton;
    @FindBy(css = "[label=\"Submit\"]")
    private WebElement submitButton;
    @FindBy(css = "[label=\"Back\"]")
    private WebElement backButton;
    @FindBy(css = "textarea[placeholder]")
    private WebElement messageText;
    @FindBy(className = "lgn-alert-modal")
    private WebElement confirmWindow;
    @FindBy(className = "lgn-action-button-success")
    private WebElement okBtnOnConfirm;
    @FindBy(css = "[ng-repeat*=\"shift in results\"]")
    private List<WebElement> comparableShifts;
    @FindBy(css = "[label=\"Next\"]")
    private WebElement nextButton;
    @FindBy(css = "[label=\"Cancel Cover Request\"]")
    private WebElement cancelCoverBtn;
    @FindBy(css = "[label=\"Cancel Swap Request\"]")
    private WebElement cancelSwapBtn;
    @FindBy(css = ".shift-swap-modal-table th.ng-binding")
    private WebElement resultCount;
    @FindBy(css = "td.shift-swap-modal-shift-table-select>div")
    private List<WebElement> selectBtns;
    @FindBy(css = ".modal-content .sch-day-view-shift-outer")
    private List<WebElement> swapRequestShifts;
    @FindBy(css = "[label=\"Accept\"] button")
    private List<WebElement> acceptButtons;
    @FindBy(css = "[label=\"I agree\"]")
    private WebElement agreeButton;
    @FindBy(css = "lg-close.dismiss")
    private WebElement closeDialogBtn;
    @FindBy(className = "shift-swap-offer-time")
    private WebElement shiftOfferTime;
    @FindBy(className = "shift-swap-modal-table-shift-status")
    private List<WebElement> shiftStatus;
    @FindBy (css = ".week-schedule-shift .week-schedule-shift-wrapper")
    private List<WebElement> wholeWeekShifts;
    @FindBy (className = "period-name")
    private WebElement weekPeriod;
    @FindBy (className = "card-carousel-card")
    private List<WebElement> smartCards;
    @FindBy (className = "card-carousel-link")
    private List<WebElement> cardLinks;
    @FindBy (css = "[src*=\"print.svg\"]")
    private WebElement printIcon;
    @FindBy (css = "[src*=\"No-Schedule\"]")
    private WebElement noSchedule;
    @FindBy(css = "[ng-repeat=\"opt in opts\"]")
    private List<WebElement> filters;
    @FindBy(css = ".accept-shift")
    private WebElement claimShiftWindow;
    @FindBy(css = ".redesigned-button-ok")
    private WebElement agreeClaimBtn;
    @FindBy(css = ".redesigned-button-cancel-outline")
    private WebElement declineBtn;
    @FindBy(css = ".redesigned-modal")
    private WebElement popUpModal;
    @FindBy(css = "img[src*='shift-info']")
    private List<WebElement> infoIcons;
    @FindBy(css = ".sch-shift-hover div:nth-child(3)>div.ng-binding")
    private WebElement shiftDuration;
    @FindBy(css = ".shift-hover-subheading.ng-binding:not([ng-if])")
    private WebElement shiftJobTitleAsWorkRole;
    @FindBy(className = "accept-shift-shift-info")
    private WebElement shiftDetail;
    @FindBy(css = ".lg-toast")
    private WebElement msgOnTop;
    @FindBy(css = "[label=\"Yes\"]")
    private WebElement yesButton;
    @FindBy(css = "[label=\"No\"]")
    private WebElement noButton;
    @Override
    public int verifyClickOnAnyShift() throws Exception {
        List<String> expectedRequests = new ArrayList<>(Arrays.asList("Request to Swap Shift", "Request to Cover Shift"));
        int index = 100;
        if (areListElementVisible(tmIcons, 15) && tmIcons.size() > 1) {
            for (int i = 0; i < tmIcons.size(); i++) {
                scrollToElement(tmIcons.get(i));
                waitForSeconds(1);
                clickTheElement(tmIcons.get(i));
                if (isPopOverLayoutLoaded()) {
                    if (verifyShiftRequestButtonOnPopup(expectedRequests)) {
                        index = i;
                        break;
                    }else {
                        clickTheElement(tmIcons.get(i));
                    }
                }
            }
            if (index == 100) {
                // Doesn't find any shift that can swap or cover, cancel the previous
                index = cancelSwapOrCoverRequests(expectedRequests);
            }
        }else {
            SimpleUtils.fail("Team Members' Icons not loaded Successfully!", false);
        }
        return index;
    }

    public int cancelSwapOrCoverRequests(List<String> expectedRequests) throws Exception {
        List<String> swapRequest = new ArrayList<>(Arrays.asList("View Swap Request Status"));
        List<String> coverRequest = new ArrayList<>(Arrays.asList("View Cover Request Status"));
        int index = 100;
        if (areListElementVisible(tmIcons, 10)) {
            for (int i = 0; i < tmIcons.size(); i++) {
                moveToElementAndClick(tmIcons.get(i));
                if (isPopOverLayoutLoaded()) {
                    if (verifyShiftRequestButtonOnPopup(swapRequest)) {
                        cancelRequestByTitle(swapRequest, swapRequest.get(0).substring(5).trim());
                    }
                    if (verifyShiftRequestButtonOnPopup(coverRequest)) {
                        cancelRequestByTitle(coverRequest, coverRequest.get(0).substring(5).trim());
                    }
                    moveToElementAndClick(tmIcons.get(i));
                    if (verifyShiftRequestButtonOnPopup(expectedRequests)) {
                        index = i;
                        break;
                    }
                }
            }
        }else {
            SimpleUtils.fail("Team Members' Icons not loaded Successfully!", false);
        }
        if (index == 100) {
            SimpleUtils.fail("Failed to find a shift that can swap or cover!", false);
        }
        return index;
    }

    public void cancelRequestByTitle(List<String> requests, String title) throws Exception {
        if (requests.size() > 0) {
            clickTheShiftRequestByName(requests.get(0));
            if (isPopupWindowLoaded(title)) {
                verifyClickCancelSwapOrCoverRequest();
            }
        }
    }

    @Override
    public void clickTheShiftRequestByName(String requestName) throws Exception {
        waitForSeconds(2);
        if (areListElementVisible(shiftRequests, 5)) {
            for (WebElement shiftRequest : shiftRequests) {
                if (shiftRequest.getText().trim().equalsIgnoreCase(requestName.trim())) {
                    click(shiftRequest);
                    SimpleUtils.pass("Click " + requestName + " button Successfully!");
                    break;
                }
            }
        }else {
            SimpleUtils.fail("Shift Request buttons not loaded Successfully!", true);
        }
    }

    @Override
    public boolean isPopupWindowLoaded(String title) throws Exception {
        boolean isLoaded = false;
        if (isElementLoaded(popUpWindow, 5) && isElementLoaded(popUpWindowTitle, 5)) {
            if (title.equalsIgnoreCase(popUpWindowTitle.getText())) {
                SimpleUtils.pass(title + " window loaded Successfully!");
                isLoaded = true;
            }
        }
        return isLoaded;
    }

    @Override
    public void verifyClickCancelSwapOrCoverRequest() throws Exception {
        if (isElementLoaded(cancelCoverBtn, 5)) {
            click(cancelCoverBtn);
        }
        if (isElementLoaded(cancelSwapBtn, 5)) {
            click(cancelSwapBtn);
        }
        if (isElementLoaded(okBtnOnConfirm, 5)) {
            click(okBtnOnConfirm);
        }else {
            SimpleUtils.fail("Confirm Button failed to load!", true);
        }
    }


    public int cancelClaimRequest(List<String> expectedRequests) throws Exception {
        List<String> claimStatus = new ArrayList<>(Arrays.asList("Claim Shift Approval Pending", "Cancel Claim Request"));
        int index = -1;
        if (areListElementVisible(tmIcons, 10)) {
            for (int i = 0; i < tmIcons.size(); i++) {
                moveToElementAndClick(tmIcons.get(i));
                if (isPopOverLayoutLoaded()) {
                    if (verifyShiftRequestButtonOnPopup(claimStatus)) {
                        clickTheShiftRequestByName(claimStatus.get(1));
                        verifyReConfirmDialogPopup();
                        verifyClickOnYesButton();
                        moveToElementAndClick(tmIcons.get(i));
                        if (verifyShiftRequestButtonOnPopup(expectedRequests)) {
                            index = i;
                            break;
                        }
                    }
                }
            }
        }else {
            SimpleUtils.fail("Team Members' Icons not loaded Successfully!", false);
        }
        if (index == -1) {
            SimpleUtils.fail("Failed to find a shift that can swap or cover!", false);
        }
        return index;
    }

    @Override
    public void verifyReConfirmDialogPopup() throws Exception {
        String title = "Are you sure you want to cancel your claim for this shift?";
        if (isPopupWindowLoaded(title)) {
            if (isElementLoaded(yesButton, 5) && isElementLoaded(noButton, 5)) {
                SimpleUtils.pass("Yes and No Buttons loaded Successfully!");
            }else {
                SimpleUtils.fail("Yes and No Buttons not loaded Successfully!", false);
            }
        }else {
            SimpleUtils.fail(title + " window not loaded Successfully!", false);
        }
    }


    @Override
    public void verifyClickOnYesButton() throws Exception {
        if (isElementLoaded(yesButton, 5)) {
            click(yesButton);
            String message = "Cancelled successfully";
            verifyThePopupMessageOnTop(message);
        }else {
            SimpleUtils.fail("Yes Buttons not loaded Successfully!", false);
        }
    }

    @Override
    public void verifyThePopupMessageOnTop(String expectedMessage) throws Exception {
        if (isElementLoaded(msgOnTop, 20)) {
            String message = msgOnTop.getText();
            if (message.contains(expectedMessage)) {
                SimpleUtils.pass("Verified Message shows correctly!");
            }else {
                SimpleUtils.fail("Message on top is incorrect, expected is: " + expectedMessage + ", but actual is: " + message, false);
            }
        }else {
            SimpleUtils.fail("Message on top not loaded Successfully!", false);
        }
    }

    @Override
    public int selectOneShiftIsClaimShift(List<String> claimShift) throws Exception {
        int index = -1;
        if (areListElementVisible(tmIcons, 5)) {
            for (int i = 0; i < tmIcons.size(); i++) {
                moveToElementAndClick(tmIcons.get(i));
                if (isPopOverLayoutLoaded()) {
                    if (verifyShiftRequestButtonOnPopup(claimShift)) {
                        index = i;
                        break;
                    }
                }
            }
            if (index == -1) {
                // Doesn't find any shift that is Claim Shift, cancel the previous
                index = cancelClaimRequest(claimShift);
            }
        }else {
            SimpleUtils.fail("Team Members' Icons not loaded Successfully!", false);
        }
        return index;
    }


    @FindBy (css = "div.sch-day-view-shift-worker-detail")
    private List<WebElement> scheduleTableWeekViewWorkerDetail;
    @Override
    public void validateProfilePictureInAShiftClickable() throws Exception {
        Boolean isShiftDetailsShowed = false;
        if (areListElementVisible(weekScheduleShiftsDateOfMySchedule, 20)) {
            if (scheduleTableWeekViewWorkerDetail.size() != 0) {
                int randomIndex = (new Random()).nextInt(scheduleTableWeekViewWorkerDetail.size());
                moveToElementAndClick(scheduleTableWeekViewWorkerDetail.get(randomIndex));
                SimpleUtils.pass("My Schedule Page: Profile picture is clickable successfully");
                if (isPopOverLayoutLoaded()) {
                    if (!popOverLayout.getText().isEmpty() && popOverLayout.getText() != null) {
                        isShiftDetailsShowed = true;
                    }
                    moveToElementAndClick(scheduleTableWeekViewWorkerDetail.get(randomIndex));
                }
                if (isShiftDetailsShowed == true)
                    SimpleUtils.pass("My Schedule Page: Profile picture shows details of TM successfully");
                else
                    SimpleUtils.fail("My Schedule Page: Profile picture failed to details of TM", true);
            } else if (scheduleTableWeekViewWorkerDetail.size() == 0)
                SimpleUtils.report("My Schedule Page: No shift hours in the schedule table");
        } else if (isElementLoaded(myScheduleNoSchedule, 10))
            SimpleUtils.report("My Schedule Page: Schedule has not been generated.");
        else SimpleUtils.fail("My Schedule Page: Failed to load shifts", true);
    }


    @Override
    public void clickTheShiftRequestToClaimShift(String requestName, String requestUserName) throws Exception {
        int index = 0;
        if (areListElementVisible(tmIcons, 15)) {
            for (int i = 0; i < tmIcons.size(); i++) {
                moveToElementAndClick(tmIcons.get(i));
                if (isPopOverLayoutLoaded()) {
                    if (popOverLayout.getText().contains(requestName) && popOverLayout.getText().contains(requestUserName)) {
                        index = 1;
                        clickTheElement(popOverLayout.findElement(By.cssSelector("span.sch-worker-action-label")));
                        SimpleUtils.pass("Click " + requestName + " button Successfully!");
                        break;
                    }
                }
            }
            if (index == 0) {
                SimpleUtils.fail("Failed to select one shift to claim", false);
            }
        } else {
            SimpleUtils.fail("Team Members' Icons not loaded", false);
        }
    }

    @Override
    public void verifyTheRedirectionOfBackButton() throws Exception {
        verifyBackNSubmitBtnLoaded();
        click(backButton);
        String title = "Find Shifts to Swap";
        if (isPopupWindowLoaded(title)) {
            SimpleUtils.pass("Redirect to Find Shifts to Swap windows Successfully!");
        }else {
            SimpleUtils.fail("Failed to redirect to Find Shifts to Swap window!", false);
        }
    }

    @Override
    public void verifyBackNSubmitBtnLoaded() throws Exception {
        if (isElementLoaded(backButton, 5)) {
            SimpleUtils.pass("Back button loaded Successfully on submit swap request!");
        }else {
            SimpleUtils.fail("Back button not loaded Successfully on submit swap request!", true);
        }
        if (isElementLoaded(submitButton, 5)) {
            SimpleUtils.pass("Submit button loaded Successfully on submit swap request!");
        }else {
            SimpleUtils.fail("Submit button not loaded Successfully on submit swap request!", false);
        }
    }


    @Override
    public void verifyClickAcceptSwapButton() throws Exception {
        String title = "Confirm Shift Swap";
        String expectedMessage = "Success";
        if (areListElementVisible(acceptButtons, 5) && acceptButtons.size() > 0) {
            clickTheElement(acceptButtons.get(0));
            SimpleUtils.assertOnFail(title + " not loaded Successfully!", isPopupWindowLoaded(title), false);
            if (isElementLoaded(agreeButton, 5)) {
                click(agreeButton);
                verifyThePopupMessageOnTop(expectedMessage);
                verifySwapRequestDeclinedDialogPopUp();
                if (isElementLoaded(closeDialogBtn, 5)) {
                    clickTheElement(closeDialogBtn);
                }
            }else {
                SimpleUtils.fail("I Agree button not loaded Successfully!", false);
            }
        }else {
            SimpleUtils.fail("Accept Button not loaded Successfully!", false);
        }
    }

    @FindBy(css = ".redesigned-modal-title")
    private WebElement deleteScheduleTitle;

    @FindBy (css = ".redesigned-button-ok")
    private WebElement deleteButtonOnDeleteSchedulePopup;
    private void verifySwapRequestDeclinedDialogPopUp() throws Exception {
        try {
            // Same elements sa Delete Schedule pop up
            if (isElementLoaded(deleteScheduleTitle, 10) && deleteScheduleTitle.getText().equalsIgnoreCase("Swap Request Declined")) {
                if (isElementLoaded(deleteButtonOnDeleteSchedulePopup, 10)) {
                    clickTheElement(deleteButtonOnDeleteSchedulePopup);
                    SimpleUtils.pass("Click on 'OK' button Successfully on 'Swap Request Declined' dialog!");
                }
            }
        } catch (Exception e) {
            // Do nothing
        }
    }


    @Override
    public void verifyClickAgreeBtnOnClaimShiftOffer() throws Exception {
        if (isElementLoaded(agreeClaimBtn, 5)) {
            click(agreeClaimBtn);
            String expectedMessage = "Your claim request has been received and sent for approval";
            verifyThePopupMessageOnTop(expectedMessage);
        }else {
            SimpleUtils.fail("I Agree Button not loaded Successfully!", false);
        }
    }

    @Override
    public void verifyClickAgreeBtnOnClaimShiftOfferWhenDontNeedApproval() throws Exception {
        if (isElementLoaded(agreeClaimBtn, 5)) {
            click(agreeClaimBtn);
            String expectedMessage = "Success! This shift is yours, and has been added to your schedule.";
            verifyThePopupMessageOnTop(expectedMessage);
        }else {
            SimpleUtils.fail("I Agree Button not loaded Successfully!", false);
        }
    }


    @Override
    public void verifyTheColorOfCancelClaimRequest(String cancelClaim) throws Exception {
        String red = "#ff0000";
        String color = "";
        if (areListElementVisible(shiftRequests, 5)) {
            for (WebElement shiftRequest : shiftRequests) {
                if (shiftRequest.getText().equalsIgnoreCase(cancelClaim)) {
                    color = Color.fromString(shiftRequest.getCssValue("color")).asHex();
                }
            }
        }else {
            SimpleUtils.fail("Shift Requests not loaded Successfully!", false);
        }
        if (red.equalsIgnoreCase(color)) {
            SimpleUtils.pass("Cancel Claim Request option is in red color");
        }else {
            SimpleUtils.fail("Cancel Claim Request option should be there in red color, but the actual color is: "
                    + color + ", expected is red: " + red, false);
        }
    }
    @FindBy(className = "card-carousel-link")
    private WebElement viewShiftsBtn;

    @FindBy(css = "[ng-repeat=\"opt in opts\"] input-field")
    private List<WebElement> shiftTypes;

    @FindBy(css = "[ng-click=\"$ctrl.openFilter()\"]")
    private WebElement filterButton;
    @Override
    public void validateViewShiftsClickable() throws Exception {
        SmartCardPage smartCardPage = new ConsoleSmartCardPage();
        if (smartCardPage.isViewShiftsBtnPresent()) {
            click(viewShiftsBtn);
            SimpleUtils.pass("My Schedule Page: View shift is clickable and a filter for Open shift is applied after that successfully");
            click(filterButton);
            if (shiftTypes.size() > 0) {
                for (WebElement shiftType : shiftTypes) {
                    WebElement filterCheckBox = shiftType.findElement(By.tagName("input"));
                    if (filterCheckBox.getAttribute("class").contains("ng-not-empty")) {
                        if (shiftType.getText().equals("Open"))
                            SimpleUtils.pass("My Schedule Page: only open shifts for the selected week should show successfully");
                        else
                            SimpleUtils.fail("My Schedule Page: Not only open shifts for the selected week show", true);
                    }
                }
            }
        }
    }


}
