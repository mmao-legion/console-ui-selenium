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

    @FindBy (className = "bell-container")
    private WebElement activityBell;
    @FindBy (className = "notification-bell-popup-filters-filter")
    private List<WebElement> activityFilters;
    @FindBy (className = "notification-bell-popup-filters-filter-title")
    private WebElement filterTitle;
    @FindBy (className = "notification-container")
    private List<WebElement> activityCards;

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
}
