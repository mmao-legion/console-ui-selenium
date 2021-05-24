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

import java.util.ArrayList;
import java.util.HashMap;
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
	public List<String> getShiftSwapDataFromActivity(String requestUserName, String respondUserName) throws Exception {
		List<String> swapData = new ArrayList<>();
		if (areListElementVisible(activityCards, 5)) {
			WebElement message = activityCards.get(0).findElement(By.className("notification-content-message"));
			if (message != null && message.getText().contains(requestUserName) && message.getText().contains(respondUserName)) {
				List<WebElement> swapResults = activityCards.get(0).findElements(By.cssSelector("[ng-repeat*=\"swapData\"]"));
				if (swapResults != null && swapResults.size() > 0) {
					for(WebElement swapResult : swapResults) {
						WebElement date = swapResult.findElement(By.className("date"));
						WebElement nameAndTitle = swapResult.findElement(By.className("name-and-title"));
						if (date != null && nameAndTitle != null) {
							swapData.add(nameAndTitle.getText() + "\n" + date.getText());
							SimpleUtils.report("Get the swap date: " + date.getText() + " and swap name title: " + nameAndTitle.getText() + " Successfully!");
						}else {
							SimpleUtils.fail("Failed to find the date and name elements!", false);
						}
					}
				}else {
					SimpleUtils.fail("Failed to find the swap elements!", false);
				}
			}
		}
		if (swapData.size() != 2) {
			SimpleUtils.fail("Failed to get the swap data!", false);
		}
		return swapData;
	}

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
			clickTheElement(activityBell);
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
				clickTheElement(activityFilters.get(index));
				waitForSeconds(3);
				if (isElementLoaded(filterTitle, 10)) {
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
	public void verifyNewShiftSwapCardShowsOnActivity(String requestUserName, String respondUserName, String actionLabel,
													  boolean isNewLabelShows) throws Exception {
		String newStatus = "New";
		String expectedMessage = actionLabel + " to swap shifts";
		waitForSeconds(5);
		if (areListElementVisible(activityCards, 15)) {
			if (isNewLabelShows) {
				WebElement newLabel = activityCards.get(0).findElement(By.className("notification-new-label"));
				if (newLabel != null && newLabel.getText().equalsIgnoreCase(newStatus)) {
					SimpleUtils.pass("Verified 'New' label shows correctly");
				}else {
					SimpleUtils.fail("Failed to find a new business profile update activity!", false);
				}
			}
			WebElement message = activityCards.get(0).findElement(By.className("notification-content-message"));
			if (message != null && message.getText().contains(requestUserName) && message.getText().contains(respondUserName)
					&& message.getText().toLowerCase().contains(expectedMessage)) {
				SimpleUtils.pass("Find Card: " + message.getText() + " Successfully!");
			}else {
				SimpleUtils.fail("Failed to find the card that is new and contain: " + expectedMessage + "! Actual card is: " + message.getText(), false);
			}
		}else {
			SimpleUtils.fail("Shift Swap Activity failed to Load!", false);
		}
	}

	@Override
	public void approveOrRejectShiftSwapRequestOnActivity(String requestUserName, String respondUserName, String action) throws Exception {
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
		waitForSeconds(5);
		if (areListElementVisible(activityCards, 15)) {
			WebElement message = activityCards.get(0).findElement(By.className("notification-content-message"));
			if (message != null && message.getText().contains(requestUserName) && message.getText().toLowerCase().contains(expectedMessage)) {
				SimpleUtils.pass("Find Card: " + message.getText() + " Successfully!");
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
		waitForSeconds(10);
		if (isElementLoaded(activityBell, 15)) {
			click(activityBell);
			SimpleUtils.pass("Click on Activity Close Button Successfully!");
		}else {
			SimpleUtils.fail("Activity Close Button failed to load!", false);
		}

	}

	@Override
	public void verifyActivityOfShiftOffer(String requestUserName) throws Exception {
		String expectedMessage = "open shift";
		waitForSeconds(5);
		if (areListElementVisible(activityCards, 15)) {
			WebElement message = activityCards.get(0).findElement(By.className("notification-content-message"));
			if (message != null && message.getText().contains(requestUserName)
					&& message.getText().toLowerCase().contains(expectedMessage)) {
				SimpleUtils.pass("Find Card: " + message.getText() + " Successfully!");
			}else if( message.getText().toLowerCase().contains("no activities available for the selected filter")) {
				SimpleUtils.report("No activities available for the selected filter");
			}else{
				SimpleUtils.fail("Failed to find the card that is new and contain: " + requestUserName + ", "
						+ ", " + expectedMessage + "! Actual card is: " + message.getText(), false);
			}
		}else {
			SimpleUtils.fail("Shift Offer Activity failed to Load1", false);
		}
	}

	@Override
	public void approveOrRejectShiftOfferRequestOnActivity(String requestUserName, String action) throws Exception {
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
						SimpleUtils.pass(action + " the shift offer request for: " + requestUserName +  " Successfully!");
					} else {
						SimpleUtils.fail(action + " message failed to load!", false);
					}
				}
			}else {
				SimpleUtils.fail("Action buttons: Approve and Reject failed to load!", false);
			}
		}else {
			SimpleUtils.fail("Failed to find a new Shift Offer activity!", false);
		}
	}

	@Override
	public void verifyApproveShiftOfferRequestAndGetErrorOnActivity(String requestUserName) throws Exception {
		if (activityCards.size()>0) {
			for (int i = 0; i<activityCards.size(); i++){
				WebElement shiftSwapCard = activityCards.get(i);
				if (i>3){
					SimpleUtils.fail("Didn't find the right notification!", false);
				}
				List<WebElement> actionButtons = shiftSwapCard.findElements(By.className("notification-buttons-button"));
				WebElement message = shiftSwapCard.findElement(By.className("notification-content-message"));
				if (actionButtons != null && actionButtons.size() == 2 && message.getText().contains(requestUserName)) {
					for (WebElement button : actionButtons) {
						if ("approve".equalsIgnoreCase(button.getText())) {
							click(button);
							break;
						}
					}
					// check the status of the card.
					waitForSeconds(2);
					SimpleUtils.assertOnFail("Approve and Reject buttons should be there!", areListElementVisible(activityCards.get(i).findElements(By.className("notification-buttons-button"))), false);
				}
			}
		}else {
			SimpleUtils.fail("Failed to find a new Shift Offer activity!", false);
		}
	}


    @Override
    public void verifyNewBusinessProfileCardShowsOnActivity(String userName, boolean isNewLabelShows) throws Exception {
		boolean isFound = false;
        String newStatus = "New";
        String expectedMessage = userName + " updated business profile photo.";
        waitForSeconds(5);
        if (areListElementVisible(activityCards, 15)) {
        	for (WebElement activityCard : activityCards) {
				WebElement message = activityCard.findElement(By.className("notification-content-message"));
				if (message != null && message.getText().equals(expectedMessage)) {
					List<WebElement> actionButtons = activityCard.findElements(By.className("notification-buttons-button"));
					if (actionButtons != null && actionButtons.size() == 2) {
						if (actionButtons.get(0).getText().equals("Approve") && actionButtons.get(1).getText().equals("Reject")) {
							SimpleUtils.pass("Approve and Reject buttons loaded Successfully on Business Profile Update Card!");
							if (isNewLabelShows) {
								WebElement newLabel = activityCard.findElement(By.className("notification-new-label"));
								if (newLabel != null && newLabel.getText().equalsIgnoreCase(newStatus) && message != null) {
									SimpleUtils.pass("Verified 'New' label shows correctly");
								} else {
									SimpleUtils.fail("Failed to find a new business profile update activity!", false);
								}
							}
							isFound = true;
							break;
						} else {
							SimpleUtils.fail("Approve and Reject buttons are not loaded on Business Profile Update Card!", false);
						}
					}
				}
			}
        	if (!isFound) {
				SimpleUtils.fail("Failed to find the card with the message: " + expectedMessage, false);
			}
        }else {
            SimpleUtils.fail("Business Profile Update Activity failed to Load!", false);
        }
    }

    @Override
    public void verifyNewWorkPreferencesCardShowsOnActivity(String userName) throws Exception {
        String expectedMessage = userName + " updated work preferences.";
        waitForSeconds(5);
        if (areListElementVisible(activityCards, 15)) {
            WebElement message = activityCards.get(0).findElement(By.className("notification-content-message"));
            if (message != null && message.getText().equals(expectedMessage)) {
                SimpleUtils.pass("Find Card: " + message.getText() + " Successfully!");
            }else {
                SimpleUtils.fail("Failed to find the card that contains: " + expectedMessage
                        + "! Actual card is: " + message.getText(), false);
            }
        }else {
            SimpleUtils.fail("Profile Update Activity failed to Load!", false);
        }
    }

    /*
     * Added by Haya
     * Verify the notification message and detail for time off request
     * */
    public void verifyTheNotificationForReqestTimeOff(String requestUserName, String startTime, String endTime,String timeOffAction) throws Exception {
        boolean isFound = false;
        String expectedCancelInfo = "Cancelled on ";
    	String expectedMessage = requestUserName +" requested time off on " + startTime.replace(",","").substring(0,4)+changeDateFormat(startTime.replace(",","").substring(4))+" - " + endTime.replace(",","").substring(0,4)+changeDateFormat(endTime.replace(",","").substring(4)) + ".";
        /*if (timeOffAction.toLowerCase().contains("cancel")){
            expectedMessage = requestUserName +" "+timeOffAction+" the time off request for "+ startTime.replace(",","").substring(0,4)+changeDateFormat(startTime.replace(",","").substring(4))+" - " + endTime.replace(",","").substring(0,4)+changeDateFormat(endTime.replace(",","").substring(4)) + ".";
        }*/
        String actualMessage = "";
        waitForSeconds(5);
        if (areListElementVisible(activityCards, 15)) {
        	for (WebElement activityCard : activityCards) {
				actualMessage = activityCard.findElement(By.className("notification-content-message")).getText();
				if (actualMessage != null && actualMessage.equals(expectedMessage)) {
					SimpleUtils.pass("Find Card: " + actualMessage + " Successfully!");
					isFound = true;
					if (timeOffAction.toLowerCase().contains("cancel")) {
						waitForSeconds(5);
						String cancelInfo = activityCard.findElement(By.cssSelector(".notification-approved")).getText();
						if (cancelInfo.contains(expectedCancelInfo)) {
							SimpleUtils.pass("Cancel Info load!");
						} else {
							SimpleUtils.fail("Cancel Info is not loaded!", false);
						}
					}
					//check the detail
					if (timeOffAction.equals("requested")) {
						waitForSeconds(3);
						WebElement detail = activityCard.findElement(By.cssSelector("div[ng-if=\"canShowDetails()\"]"));
						if (isElementLoaded(detail, 10)) {
							click(detail);
							click(detail);
							SimpleUtils.pass("detail load!");
						} else {
							SimpleUtils.fail("detail is not loaded!", false);
						}
					}
					break;
				}
			}
        }else {
            SimpleUtils.fail("Time Off Request Activity failed to Load!", false);
        }
        if (!isFound) {
			SimpleUtils.fail("Failed to find the card that is new and contain: " + expectedMessage + "! Actual card is: " + actualMessage, false);
		}
    }

    @Override
    public void approveOrRejectTTimeOffRequestOnActivity(String requestUserName, String respondUserName, String action) throws Exception {
        WebElement timeOffCard = activityCards.get(0);
        String approveOrRejectMessage = "";
        if (timeOffCard != null) {
            List<WebElement> actionButtons = timeOffCard.findElements(By.className("notification-buttons-button"));
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
                    approveOrRejectMessage = activityCards.get(0).findElement(By.className("notification-approved")).getText();
                    if (approveOrRejectMessage != null && approveOrRejectMessage.toLowerCase().contains(action.toLowerCase())) {
                        SimpleUtils.pass(action + " the request for: " + requestUserName + " and " + respondUserName + " Successfully!");
                    } else {
                        SimpleUtils.fail(action + " message failed to load!", false);
                    }
                }
            }else {
                SimpleUtils.fail("Action buttons: Approve and Reject failed to load!", false);
            }
        }else {
            SimpleUtils.fail("Failed to find a new activity!", false);
        }
    }

    @Override
    public void closeActivityWindow() throws Exception {
        if (isElementLoaded(activityBell, 10)) {
            click(activityBell);
        }else {
            SimpleUtils.fail("Close button is not Loaded Successfully!", false);
        }
    }

    @Override
    public void verifyNoNotificationForActivateTM() throws Exception {
        String actualMessage = "";
        if (areListElementVisible(activityCards, 15)) {
            actualMessage = activityCards.get(0).findElement(By.className("notification-content-message")).getText();
            if (actualMessage != null && !actualMessage.toLowerCase().contains("activated")) {
                SimpleUtils.pass("No message for activating team menmber!");
            }else {
                SimpleUtils.fail("There is message for activating team member! : " + actualMessage, false);
            }
        }else {
            SimpleUtils.fail("Notifications failed to Load!", false);
        }
    }

    @Override
    public void verifyNotificationForUpdateAvailability(String requestName,String isApprovalRequired,String requestOrCancelLabel,String weekInfo,String repeatChange) throws Exception {
        String expectedMessage = requestName+" updated availability.";
        String actualMessage = "";
        if (isApprovalRequired.toLowerCase().contains("not required")){
            if (areListElementVisible(activityCards, 15)) {
                actualMessage = activityCards.get(0).findElement(By.className("notification-content-message")).getText();
                if (actualMessage != null && actualMessage.equals(expectedMessage)) {
                    SimpleUtils.pass("Find Card: " + actualMessage + " Successfully!");
                }else {
                    SimpleUtils.fail("Failed to find the card that is new and contain: " + expectedMessage + "! Actual card is: " + actualMessage, false);
                }
            }else {
                SimpleUtils.fail("Profile update Activity failed to Load!", false);
            }
        } else if (requestOrCancelLabel.toLowerCase().contains("requested")) {// approval required!
                if (areListElementVisible(activityCards, 15)) {
                    expectedMessage = requestName+" requested an availability change.";
                    actualMessage = activityCards.get(0).findElement(By.className("notification-content-message")).getText();
                    if (actualMessage != null && actualMessage.equals(expectedMessage)) {
                        SimpleUtils.pass("Find Card: " + actualMessage + " Successfully!");
                        waitForSeconds(3);
                        WebElement detail = activityCards.get(0).findElement(By.cssSelector("div[ng-if=\"canShowDetails()\"]"));
                        if (isElementLoaded(detail,10)){
                            click(detail);
                            verifyAvailabilityNotificationDetail(weekInfo,repeatChange);
                            click(detail);
                            SimpleUtils.pass("detail load!");
                        }else{
                            SimpleUtils.fail("detail is not loaded!",true);
                        }
                    }else {
                        SimpleUtils.fail("Failed to find the card that is new and contain: " + expectedMessage + "! Actual card is: " + actualMessage, false);
                    }
                }else {
                    SimpleUtils.fail("Profile update Activity failed to Load!", false);
                }
        }
    }

    private void verifyAvailabilityNotificationDetail(String weekInfo,String repeatChange) throws Exception{
        verifyAvailabilityChangeHourDetail();
        String dateInfo = activityCards.get(0).findElement(By.cssSelector("div[class=\"notification-details-table\"] .date")).getText();
        if (repeatChange.toLowerCase().contains("this week only")){
            if (dateInfo.toLowerCase().contains(changeDateFormat(weekInfo.split("-")[0]).toLowerCase()) && dateInfo.toLowerCase().contains(changeDateFormat(weekInfo.split("-")[1]).toLowerCase())){
                SimpleUtils.pass("Week info of availability change is correct! WeekInfo: "+dateInfo);
            } else {
                SimpleUtils.fail("week info is not correct! expected weekinfo is: "+weekInfo+" actual is: "+dateInfo,true);
            }
        } else if (repeatChange.toLowerCase().contains("repeat forward")){
            if (dateInfo.toLowerCase().contains(changeDateFormat(weekInfo.split("-")[0]).toLowerCase()) && dateInfo.toLowerCase().contains("onward")){
                SimpleUtils.pass("Week info of availability change is correct! WeekInfo: "+dateInfo);
            } else {
                SimpleUtils.fail("week info is not correct! actual result is: " + dateInfo, true);
            }
        } else{
            SimpleUtils.fail("repeatchange label doesn't match anyone", true);
        }
    }

    //added by Haya
    private String changeDateFormat(String dateMMMddString) throws Exception{
        String result = dateMMMddString;
        if (dateMMMddString != null){
            if (dateMMMddString.split(" ")[1].length()==1){
                result = dateMMMddString.split(" ")[0]+" 0"+dateMMMddString.split(" ")[1];
                return result;
            }
        }
        return result;
    }

    //added by Haya
    private void verifyAvailabilityChangeHourDetail() throws Exception{
        double hoursTotal =0;
        double hoursDetail = 0;
        WebElement currentAvailability = activityCards.get(0).findElement(By.cssSelector("div[class=\"notification-details-table\"] .availability .current"));
        WebElement newAvailability = activityCards.get(0).findElement(By.cssSelector("div[class=\"notification-details-table\"] .availability .new"));
        double a=Double.parseDouble(newAvailability.getText().replace("\n"," ").split(" ")[2]);
        double b=Double.parseDouble(currentAvailability.getText().split(" ")[2]);
        hoursTotal = a - b;
        List <WebElement> availabilityChangeDays= activityCards.get(0).findElements(By.cssSelector("div[class=\"notification-details-table\"] .availability-week-day-change"));
        if (areListElementVisible(availabilityChangeDays,5) && availabilityChangeDays.size() > 0){
           for (WebElement availabilityChangeDay:availabilityChangeDays) {
               WebElement iconTriage = availabilityChangeDay.findElement(By.cssSelector("div[class=\"notification-details-table\"] .availability-week-day-change div[ng-if=\"dayAvailability.icon\"] "));
               if (iconTriage.getAttribute("class").toLowerCase().contains("triangle-down")){
                   double down = Double.parseDouble(availabilityChangeDay.getText().trim());
                   hoursDetail = hoursDetail - down;
               } else {
                   hoursDetail = hoursDetail + Double.parseDouble(availabilityChangeDay.getText().trim());
               }
           }
           if (hoursDetail==hoursTotal){
               SimpleUtils.pass("total availability change hour match summary of every day change hour!");
           } else{
               SimpleUtils.fail("total availability change hour doesn't match summary of every day change hour!", true);
           }
        } else{
            SimpleUtils.fail("No availability change in detail info!",true);
        }
    }

    //Added By Julie
    @FindBy (className = "notification-bell-popup-header-container")
    public WebElement notificationBellPopupHeader;
    @FindBy (css = ".notification-bell-popup-notifications-container.empty")
    public WebElement notificationsContainerEmpty;
	@FindBy (css = ".notification-bell-popup-notifications-container")
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
    	waitForSeconds(3);
        if (isElementLoaded(filterTitle,10) && (isElementLoaded(notificationsContainer, 10) || isElementLoaded(notificationsContainerEmpty, 10))) {
            if (filterTitle.getText().contains("Shift Swap")) {
                if (notificationsContainer.getText().contains("requested to swap shifts") || notificationsContainer.getText().contains("agreed to cover")) {
                    SimpleUtils.pass("The content of shift swap activity displays successfully");
                } else if (notificationsContainerEmpty.getText().contains("No activities available")) {
                    SimpleUtils.pass("No activities available for the selected filter");
                } else SimpleUtils.fail("The content of shift swap activity displays incorrectly", false);
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
