package com.legion.pages.core;

import com.legion.pages.ActivityPage;
import com.legion.pages.BasePage;
import com.legion.pages.DashboardPage;
import com.legion.utils.SimpleUtils;
import org.junit.rules.ExpectedException;
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
    public WebElement verifyNewShiftSwapCardShowsOnActivity(String requestUserName, String respondUserName) throws Exception {
        String expectedMessage = "requested to swap shifts";
        WebElement shiftSwapCard = null;
        waitForSeconds(5);
        if (areListElementVisible(activityCards, 15)) {
            WebElement message = activityCards.get(0).findElement(By.className("notification-content-message"));
            if (message != null && message.getText().contains(requestUserName) && message.getText().contains(respondUserName)
                    && message.getText().toLowerCase().contains(expectedMessage)) {
                SimpleUtils.pass("Find Card: " + message.getText() + " Successfully!");
                shiftSwapCard = activityCards.get(0);
            }else {
                SimpleUtils.fail("Failed to find the card that is new and contain: " + requestUserName + ", "
                        + respondUserName + ", " + expectedMessage + "! Actual card is: " + message.getText(), false);
            }
        }else {
            SimpleUtils.fail("Shift Swap Activity failed to Load1", false);
        }
        return shiftSwapCard;
    }

    @Override
    public void approveOrRejectShiftSwapRequestOnActivity(String requestUserName, String respondUserName, String action) throws Exception {
        WebElement shiftSwapCard = verifyNewShiftSwapCardShowsOnActivity(requestUserName, respondUserName);
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

    //Added By Julie
    @FindBy (className = "notification-bell-popup-header-container")
    public WebElement notificationBellPopupHeader;
    @FindBy (className = "notification-bell-popup-notifications-container")
    public WebElement notificationsContainer;

    @Override
    public boolean isActivityBellIconLoaded() throws Exception {
        boolean isLoaded = false;
        if (isElementLoaded(activityBell, 10))
            isLoaded = true;
        return isLoaded;
    }

    @Override
    public void verifyTheContentOnActivity() throws Exception {
        if (isElementEnabled(notificationBellPopupHeader, 10)) {
            if (notificationBellPopupHeader.getText().contains("Activities") && activityFilters.size() == 5 ) {
                SimpleUtils.pass("Activities window appears successfully, the content in every activity tab will be verified in other test cases");
            } else {
                SimpleUtils.fail("Activities window's content is not as expected",true);
            }
        } else {
            SimpleUtils.fail("Failed to load Activities window",true);
        }
    }

    @Override
    public void verifyTheContentOfShiftSwapActivity() throws Exception {
        if (isElementLoaded(filterTitle,10) && isElementLoaded(notificationsContainer, 10)) {
            if (filterTitle.getText().contains("Shift Swap")) {
                if (notificationsContainer.getText().contains("requested to swap shifts") || notificationsContainer.getText().contains("agreed to cover")) {
                    SimpleUtils.pass("The content of shift swap activity displays successfully");
                } else if ( notificationsContainer.getText().toLowerCase().contains("no activities available for the selected filter")) {
                    SimpleUtils.pass("No activities available for the selected filter");
                } else SimpleUtils.fail("The content of shift swap activity displays incorrectly", true);
            } else SimpleUtils.fail("The content of Shift Swap Activity is incorrect", true);
        } else SimpleUtils.fail("Shift Swap Activity failed to Load",true);
    }

    @Override
    public WebElement verifyNewShiftCoverCardShowsOnActivity(String requestUserName, String respondUserName) throws Exception {
        String expectedMessage = "agreed to cover";
        WebElement shiftCoverCard = null;
        waitForSeconds(5);
        Boolean isShiftCoverCardPresent = false;
        WebElement message = null;
        if (areListElementVisible(activityCards, 15)) {
            for (WebElement activityCard: activityCards) {
                message = activityCard.findElement(By.className("notification-content-message"));
                if (message != null && message.getText().contains(requestUserName) && message.getText().contains(respondUserName)
                        && message.getText().toLowerCase().contains(expectedMessage)) {
                    SimpleUtils.pass("Find Card: " + message.getText() + " Successfully!");
                    shiftCoverCard = activityCard;
                    isShiftCoverCardPresent = true;
                    break;
                }
            }
            if (isShiftCoverCardPresent)
                SimpleUtils.pass("Find Card: " + message.getText() + " Successfully!");
            else
                SimpleUtils.fail("Failed to find the card that is new and contain: " + requestUserName + ", "
                        + respondUserName + ", " + expectedMessage + "! Actual card is: " + message.getText(), true);
        } else {
            SimpleUtils.fail("Shift Swap Activity failed to Load", true);
        }
        return shiftCoverCard;
    }

    @Override
    public void approveOrRejectShiftCoverRequestOnActivity(String requestUserName, String respondUserName, String action) throws Exception {
        WebElement shiftCoverCard = verifyNewShiftCoverCardShowsOnActivity(requestUserName, respondUserName);
        if (shiftCoverCard != null) {
            List<WebElement> actionButtons = shiftCoverCard.findElements(By.className("notification-buttons-button"));
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
                        SimpleUtils.pass(action + " the shift cover request for: " + requestUserName + " and " + respondUserName + " Successfully!");
                    } else {
                        SimpleUtils.fail(action + " message failed to load!", false);
                    }
                }
            } else {
                SimpleUtils.fail("Action buttons: Approve and Reject failed to load!", false);
            }
        } else {
            SimpleUtils.fail("Failed to find a new Shift Cover activity!", false);
        }
    }
}
