package com.legion.pages.core;

import com.legion.pages.ActivityPage;
import com.legion.pages.BasePage;
import com.legion.pages.DashboardPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleActivityPage extends BasePage implements ActivityPage {

    public ConsoleActivityPage() {
        PageFactory.initElements(getDriver(), this);
    }

    // Added by Nora
    @FindBy (className = "bell-container")
    private WebElement activityBell;
    @FindBy (className = "notification-bell-popup-filters-filter")
    private List<WebElement> activityFilters;
    @FindBy (className = "notification-bell-popup-filters-filter-title")
    private WebElement filterTitle;
    @FindBy (className = "notification-container")
    private List<WebElement> activityCards;
    @FindBy (css = "[ng-click=\"close()\"]")
    private WebElement closeActivityFeedBtn;
    @FindBy (className = "notification-bell-popup-container")
    private WebElement activityContainer;

    @Override
    public boolean isActivityContainerPoppedUp() throws Exception {
        boolean isLoaded = false;
        if (isElementLoaded(activityContainer, 5)) {
            isLoaded = true;
            SimpleUtils.pass("Activity pop up Container loaded Successfully!");
        }
        return isLoaded;
    }

    @Override
    public void verifyFiveActivityButtonsLoaded() throws Exception {
        if (areListElementVisible(activityFilters, 10) && activityFilters.size() == 5) {
            if (activityFilters.get(0).getAttribute("src").contains("time-off")) {
                SimpleUtils.pass("Find the first filter 'Time Off' Successfully!");
            }else {
                SimpleUtils.fail("Filter 'Time Off' not loaded Successfully!", false);
            }
            if (activityFilters.get(1).getAttribute("src").contains("offer")) {
                SimpleUtils.pass("Find the second filter 'Shift Offer' Successfully!");
            }else {
                SimpleUtils.fail("Filter 'Shift Offer' not loaded Successfully!", false);
            }
            if (activityFilters.get(2).getAttribute("src").contains("shift-swap")) {
                SimpleUtils.pass("Find the third filter 'Shift Swap' Successfully!");
            }else {
                SimpleUtils.fail("Filter 'Shift Swap' not loaded Successfully!", false);
            }
            if (activityFilters.get(3).getAttribute("src").contains("team")) {
                SimpleUtils.pass("Find the forth filter 'Profile Update' Successfully!");
            }else {
                SimpleUtils.fail("Filter 'Profile Update' not loaded Successfully!", false);
            }
            if (activityFilters.get(4).getAttribute("src").contains("calendar")) {
                SimpleUtils.pass("Find the fifth filter 'Schedule' Successfully");
            }else {
                SimpleUtils.fail("Filter 'Schedule' not loaded Successfully!", false);
            }
        }
    }

    @Override
    public void verifyActivityBellIconLoaded() throws Exception {
        if (isElementLoaded(activityBell, 10)) {
            SimpleUtils.pass("Activity Bell Icon Loaded Successfully!");
        }else {
            SimpleUtils.fail("Activity Bell Icon not Loaded Successfully!", false);
        }
    }

    @Override
    public void verifyClickOnActivityIcon() throws Exception {
        if (isElementLoaded(activityBell, 10)) {
            click(activityBell);
            if (areListElementVisible(activityFilters, 10)) {
                SimpleUtils.pass("Click on Activity Bell icon Successfully!");
            }else {
                SimpleUtils.fail("Activity Layout failed to load!", false);
            }
        }else {
            SimpleUtils.fail("Activity Bell Icon not Loaded Successfully!", false);
        }
    }

    @Override
    public void clickActivityFilterByIndex(int index, String filterName) throws Exception {
        if (areListElementVisible(activityFilters, 10)) {
            if (index < activityFilters.size()) {
                click(activityFilters.get(index));
                if (isElementLoaded(filterTitle, 5)) {
                    if (filterName.equalsIgnoreCase(filterTitle.getText().replaceAll("\\s*", ""))) {
                        SimpleUtils.pass("Switch to :" + filterTitle.getText() + " tab Successfully!");
                    }else {
                        SimpleUtils.fail("Failed to switch to: " + filterName + " page, current page is: "
                                + filterTitle.getText(), false);
                    }
                }
            }else {
                SimpleUtils.fail("Index: " + index + " is out of range, the size of Activity Filter is: " +
                        activityFilters.size(), false);
            }
        }else {
            SimpleUtils.fail("Activity Filters failed to load!", false);
        }
    }

    @Override
    public void verifyNewShiftSwapCardShowsOnActivity(String requestUserName, String respondUserName) throws Exception {
        String expectedMessage = "requested to swap shifts";
        waitForSeconds(5);
        if (areListElementVisible(activityCards, 15)) {
            WebElement message = activityCards.get(0).findElement(By.className("notification-content-message"));
            if (message != null && message.getText().contains(requestUserName) && message.getText().contains(respondUserName)
                    && message.getText().toLowerCase().contains(expectedMessage)) {
                SimpleUtils.pass("Find Card: " + message.getText() + " Successfully!");
            }else {
                SimpleUtils.fail("Failed to find the card that is new and contain: " + requestUserName + ", "
                        + respondUserName + ", " + expectedMessage + "! Actual card is: " + message.getText(), false);
            }
        }else {
            SimpleUtils.fail("Shift Swap Activity failed to Load1", false);
        }
    }

    @Override
    public void approveOrRejectShiftSwapRequestOnActivity(String requestUserName, String respondUserName, String action) throws Exception {
        verifyNewShiftSwapCardShowsOnActivity(requestUserName, respondUserName);
        WebElement shiftSwapCard = activityCards.get(0);
        if (shiftSwapCard != null) {
            List<WebElement> actionButtons = shiftSwapCard.findElements(By.className("notification-buttons-button"));
            if (actionButtons != null && actionButtons.size() == 2) {
                for (WebElement button : actionButtons) {
                    if (action.equalsIgnoreCase(button.getText())) {
                        click(button);
                        break;
                    }
                }
                // Wait for the card to change the status message, such as approved or rejected
                waitForSeconds(3);
                if (areListElementVisible(activityCards, 15)) {
                    WebElement approveOrRejectMessage = activityCards.get(0).findElement(By.className("notification-approved"));
                    if (approveOrRejectMessage != null && approveOrRejectMessage.getText().toLowerCase().contains(action.toLowerCase())) {
                        SimpleUtils.pass(action + " the shift swap request for: " + requestUserName + " and " + respondUserName + " Successfully!");
                    } else {
                        SimpleUtils.fail(action + " message failed to load!", false);
                    }
                }
            }else {
                SimpleUtils.fail("Action buttons: Approve and Reject failed to load!", false);
            }
        }else {
            SimpleUtils.fail("Failed to find a new Shift Swap activity!", false);
        }
    }

    //Added by Estelle
    @Override
    public void verifyActivityOfPublishSchedule(String requestUserName) throws Exception {
        String expectedMessage = "published the schedule for";
        WebElement shiftSwapCard = null;
        waitForSeconds(5);
        if (areListElementVisible(activityCards, 15)) {
            WebElement message = activityCards.get(0).findElement(By.className("notification-content-message"));
            if (message != null && message.getText().contains(requestUserName) && message.getText().toLowerCase().contains(expectedMessage)) {
                SimpleUtils.pass("Find Card: " + message.getText() + " Successfully!");
                shiftSwapCard = activityCards.get(0);
            }else if( message.getText().toLowerCase().contains("no activities available for the selected filter")){
               SimpleUtils.report("No activities available for the selected filter");
            }else {
                SimpleUtils.fail("Failed to find the card that is new and contain: " + requestUserName + ", "
                        +  expectedMessage + "! Actual card is: " + message.getText(), false);
            }

        }else {
            SimpleUtils.fail("Schedule Activity failed to Load", false);
        }

    }

    @Override
    public void verifyActivityOfUpdateSchedule(String requestUserName) throws Exception {
        String expectedMessage = "updated the schedule for";
        WebElement shiftSwapCard = null;
        waitForSeconds(5);
        if (areListElementVisible(activityCards, 15)) {
            WebElement message = activityCards.get(0).findElement(By.className("notification-content-message"));
            if (message != null && message.getText().contains(requestUserName) && message.getText().toLowerCase().contains(expectedMessage)) {
                SimpleUtils.pass("Find Card: " + message.getText() + " Successfully!");
                shiftSwapCard = activityCards.get(0);
            }else if( message.getText().toLowerCase().contains("no activities available for the selected filter")){
                SimpleUtils.report("No activities available for the selected filter");
            }else {
                SimpleUtils.fail("Failed to find the card that is new and contain: " + requestUserName + ", "
                        +  expectedMessage + "! Actual card is: " + message.getText(), false);
            }
        }else {
            SimpleUtils.fail("Schedule Activity failed to Load", false);
        }

    }
    @Override
    public void verifyClickOnActivityCloseButton() throws Exception {
        if (isElementLoaded(closeActivityFeedBtn, 10)) {
            click(closeActivityFeedBtn);
            SimpleUtils.pass("Click on Activity Close Button Successfully!");
        }else {
                SimpleUtils.fail("Activity Close Button failed to load!", false);
            }

    }
}
