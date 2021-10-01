package com.legion.pages.core;

import com.legion.pages.*;
import com.legion.tests.core.ScheduleNewUITest;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleShiftOperatePage extends BasePage implements ShiftOperatePage {
    public ConsoleShiftOperatePage() {
        PageFactory.initElements(getDriver(), this);
    }
    private static HashMap<String, String> propertySearchTeamMember = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SearchTeamMember.json");

    @FindBy(css = "div.sch-day-view-shift-delete")
    private WebElement shiftDeleteBtn;

    @FindBy(css = ".sch-day-view-shift")
    private List<WebElement> dayViewAvailableShifts;

    @FindBy(css = "div.sch-worker-popover")
    private WebElement shiftPopover;

    @FindBy(css = "button.sch-action.sch-save")
    private WebElement convertToOpenYesBtn;

    public void deleteShift() throws Exception {
        if(isElementLoaded(shiftDeleteBtn, 10)){
            clickTheElement(shiftDeleteBtn);
        }else{
            SimpleUtils.fail("Delete button is not available on Shift container",false);
        }
    }

    public void deleteAllShiftsInDayView() throws Exception {
        if (areListElementVisible(dayViewAvailableShifts,10)){
            int count = dayViewAvailableShifts.size();
            for (int i = 0; i < count; i++) {
                List<WebElement> tempShifts = getDriver().findElements(By.cssSelector(".sch-day-view-shift-outer .right-shift-box"));
                scrollToElement(tempShifts.get(i));
                moveToElementAndClick(tempShifts.get(i));
                deleteShift();
            }
        }
    }

    @Override
    public void convertAllUnAssignedShiftToOpenShift() throws Exception {
        ScheduleCommonPage scheduleCommonPage = new ConsoleScheduleCommonPage();
        ScheduleMainPage scheduleMainPage = new ConsoleScheduleMainPage();
        ScheduleShiftTablePage scheduleShiftTablePage = new ConsoleScheduleShiftTablePage();
        CreateSchedulePage createSchedulePage = new ConsoleCreateSchedulePage();
        if (scheduleCommonPage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue())) {
            scheduleCommonPage.clickOnWeekView();
            scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
            for (WebElement unAssignedShift : scheduleShiftTablePage.getUnAssignedShifts()) {
                convertUnAssignedShiftToOpenShift(unAssignedShift);
            }
            scheduleMainPage.saveSchedule();
        } else {
            SimpleUtils.fail("Unable to convert UnAssigned Shift to Open Shift as 'Schedule' tab not active.", false);
        }

    }

    public void convertUnAssignedShiftToOpenShift(WebElement unAssignedShift) throws Exception {
//        By isUnAssignedShift = By.cssSelector("[ng-if=\"isUnassignedShift()\"]");
        By isUnAssignedShift = By.cssSelector(".rows .week-view-shift-image-optimized span");
        WebElement unAssignedPlusBtn = unAssignedShift.findElement(isUnAssignedShift);
        if (isElementLoaded(unAssignedPlusBtn)) {
            scrollToElement(unAssignedPlusBtn);
            clickTheElement(unAssignedPlusBtn);
            if (isElementLoaded(shiftPopover)) {
                WebElement convertToOpenOption = shiftPopover.findElement(By.cssSelector("[ng-if=\"canConvertToOpenShift() && !isTmView()\"]"));
                if (isElementLoaded(convertToOpenOption)) {
                    scrollToElement(convertToOpenOption);
                    click(convertToOpenOption);
                    if (isElementLoaded(convertToOpenYesBtn)) {
                        click(convertToOpenYesBtn);
                        Thread.sleep(2000);
                    }
                }
            }
        }
    }

    @FindBy(css = ".week-schedule-shift .shift-container .rows .worker-image-optimized img")
    private List<WebElement> profileIcons;

    @FindBy(css = "div[ng-click=\"deleteShift($event, shift)\"]")
    private WebElement deleteShift;

    @FindBy(css = "[label=\"okLabel()\"]")
    private WebElement deleteBtnInDeleteWindows;

    @FindBy(css = "[src=\"img/legion/edit/deleted-shift-week.png\"]")
    private WebElement deleteShiftImg;

    @FindBy(xpath ="//div[contains(@ng-class,'selectManualOpenShiftActionClass()')]")
    private WebElement radioBtnManualOpenShift;

    @FindBy(css="button.sch-action.sch-save")
    private WebElement btnYesOpenSchedule;

    @Override
    public void  verifyDeleteShift() throws Exception {
        ScheduleMainPage scheduleMainPage = new ConsoleScheduleMainPage();
        int count1 = profileIcons.size();
        System.out.println(count1);
        clickOnProfileIcon();
        clickTheElement(deleteShift);
        if (isDeleteShiftShowWell ()) {
            click(deleteBtnInDeleteWindows);
            if (isElementLoaded(deleteShiftImg,5)) {
                SimpleUtils.pass("delete shift draft successfully");
            }else
                SimpleUtils.fail("delete shift draft failed",false);
        }
        scheduleMainPage.saveSchedule();
        waitForSeconds(3);
        int count2 = profileIcons.size();
        System.out.println(count2);
        if (count1 > count2) {
            SimpleUtils.pass("delete shift successfully");
        }else
            SimpleUtils.fail("delete shift draft failed",false);
    }

    public String convertToOpenShiftAndOfferToSpecificTMs() throws Exception {
        NewShiftPage newShiftPage = new ConsoleNewShiftPage();
        String selectedTMName = null;
        if (isElementEnabled(radioBtnManualOpenShift, 5) && isElementEnabled(btnYesOpenSchedule)) {
            click(radioBtnManualOpenShift);
            click(btnYesOpenSchedule);
            waitForSeconds(3);
            selectedTMName = newShiftPage.searchAndGetTMName(propertySearchTeamMember.get("AssignTeamMember"));
            newShiftPage.clickOnOfferOrAssignBtn();
            SimpleUtils.pass("Shift been convert to open shift and offer to Specific TM successfully");
        } else {
            SimpleUtils.fail("Buttons on convert To Open PopUp windows load failed", false);
        }
        return selectedTMName;
    }

    @FindBy(css = "div.lgn-alert-title.ng-binding.warning")
    private WebElement titleInDeleteWindows;

    @FindBy(css = ".lgn-alert-message.ng-binding.ng-scope.warning")
    private WebElement alertMsgInDeleteWindows;

    @FindBy(css = "[label=\"cancelLabel()\"]")
    private WebElement cancelBtnInDeleteWindows;
    public boolean isDeleteShiftShowWell() throws Exception {
        if (isElementLoaded(titleInDeleteWindows,3) && isElementLoaded(alertMsgInDeleteWindows,3)
                && isElementLoaded(cancelBtnInDeleteWindows,3) && isElementLoaded(deleteBtnInDeleteWindows,3)) {
            SimpleUtils.pass("delete shift pop up window show well");
            return true;
        }else
            SimpleUtils.fail("delete shift pop up window load failed",true);
        return false;
    }

    @FindBy(css = "[label=\"Create New Shift\"]")
    private WebElement createNewShiftWeekView;

    @FindBy(css = "img[src*=\"openShift\"]")
    private List<WebElement> blueIconsOfOpenShift;
    @Override
    public void deleteLatestOpenShift() throws Exception {
        boolean isDeleted = false;
        if (isElementLoaded(createNewShiftWeekView, 5) && areListElementVisible(blueIconsOfOpenShift,5)) {
            for (int i = blueIconsOfOpenShift.size() - 1; i >= 0; i--) {
                moveToElementAndClick(blueIconsOfOpenShift.get(i));
                if (isPopOverLayoutLoaded()) {
                    clickTheElement(deleteShift);
                    if (isDeleteShiftShowWell()) {
                        click(deleteBtnInDeleteWindows);
                        if (isElementLoaded(deleteShiftImg, 5)) {
                            isDeleted = true;
                            SimpleUtils.pass("Schedule Week View: Existing shift: \" + Open Shift + \" deleted successfully");
                            break;
                        }
                    }
                }
            }
        } else
            SimpleUtils.fail("Delete shift button not found for Week Day: '" +
                    getActiveWeekText() + "'", true);
        if (!isDeleted)
            SimpleUtils.fail("Failed to delete the open shift on Last Day!", false);
    }

    @FindBy(className = "sch-worker-popover")
    private WebElement popOverLayout;
    public boolean isPopOverLayoutLoaded() throws Exception {
        boolean isLoaded = false;
        if (isElementLoaded(popOverLayout, 15)) {
            isLoaded = true;
            SimpleUtils.pass("Pop over layout loaded Successfully!");
        }
        return isLoaded;
    }


    @FindBy (className = "sch-calendar-day")
    private List<WebElement> scheduleCalendarDays;
    @Override
    public void selectAShiftToAssignTM(String username) throws Exception {
        NewShiftPage newShiftPage = new ConsoleNewShiftPage();
        boolean isFound = false;
        if (areListElementVisible(scheduleCalendarDays,10)) {
            for (WebElement day: scheduleCalendarDays) {
                if (!day.getAttribute("class").contains("sch-calendar-day-past")) {
                    WebElement dataDay = day.findElement(By.xpath("./../.."));
                    String data = dataDay.getAttribute("data-day");
                    List<WebElement> shifts = MyThreadLocal.getDriver().findElements(By.cssSelector("div[data-day=\"" + data + "\"].week-schedule-shift"));
                    if (shifts.size() > 0) {
                        int randomIndex = (new Random()).nextInt(shifts.size());
                        WebElement shiftImg = shifts.get(randomIndex).findElement(By.cssSelector(".rows span img"));
                        moveToElementAndClick(shiftImg);
                        if (isPopOverLayoutLoaded()) {
                            clickTheElement(popOverLayout.findElement(By.xpath("//span[contains(text(), \"Assign Team Member\")]")));
                            if (isAssignTeamMemberShowWell()) {
                                newShiftPage.searchText(username);
                                isFound = true;
                                SimpleUtils.pass("Assign Team Member: Select a shift and search the team member successfully");
                                break;
                            }
                        }
                    }
                }
            }
        } else
            SimpleUtils.fail("Schedule Page: Failed to find the schedule days",false);
        if (!isFound)
            SimpleUtils.fail("Assign Team Member: Failed to select a shift and search the team member",false);
    }


    @FindBy (className = "tma-header-text")
    private WebElement titleInSelectTeamMemberWindow;

    @FindBy(css = "div.tab-label")
    private List<WebElement> btnSearchteamMember;

    @FindBy(css = ".tma-search-field-input-text")
    private WebElement textSearch;

    @FindBy(css = ".sch-search")
    private WebElement searchIcon;
    private boolean isAssignTeamMemberShowWell() throws Exception {
        if (isElementLoaded(titleInSelectTeamMemberWindow,3) && areListElementVisible(btnSearchteamMember,3)
                && isElementLoaded(textSearch, 5) && isElementLoaded(searchIcon, 5)) {
            SimpleUtils.pass("Assign Team Member pop up window show well");
            return true;
        } else
            SimpleUtils.fail("Assign Team Member pop up window load failed",false);
        return false;
    }

    @FindBy (className = "week-schedule-shift-title")
    private List<WebElement> weekScheduleShiftTitles;

    @FindBy(css = "[src=\"img/legion/edit/deleted-shift-week.png\"]")
    private List<WebElement> deleteShiftImgsInWeekView;
    @Override
    public void deleteAllShiftsOfGivenDayPartInWeekView(String dayPart) throws Exception {
        boolean isFound = false;
        for (int i = 0; i < weekScheduleShiftTitles.size(); i++) {
            if (weekScheduleShiftTitles.get(i).getText().equals(dayPart)) {
                isFound = true;
                if (i == weekScheduleShiftTitles.size() - 1) {
                    List<WebElement> shifts = weekScheduleShiftTitles.get(i).findElements(By.xpath("./../../following-sibling::div//div[@class=\"rows\"]//span/span"));
                    int count1 = shifts.size();
                    for (int j = 0; j < count1; j++) {
                        clickTheElement(shifts.get(j));
                        if (isPopOverLayoutLoaded()) {
                            clickTheElement(deleteShift);
                            if (isDeleteShiftShowWell())
                                click(deleteBtnInDeleteWindows);
                        }
                    }
                    int count2 = deleteShiftImgsInWeekView.size();
                    if (count1 == count2)
                        SimpleUtils.pass("Schedule Page: Delete all the shifts in '" + dayPart + "' in week view successfully");
                    else
                        SimpleUtils.fail("Schedule Page: Failed to delete all the shifts in '\" + dayPart + \"' in week view",false);
                } else {
                    List<WebElement> shifts = weekScheduleShiftTitles.get(i).findElements(By.xpath("./../../following-sibling::div//div[@class=\"rows\"]//span/span"));
                    List<WebElement> shiftsOfNextDayPart = weekScheduleShiftTitles.get(i + 1).findElements(By.xpath("./../../following-sibling::div//div[@class=\"rows\"]//span/span"));
                    int count1 = shifts.size() - shiftsOfNextDayPart.size();
                    for (int j = 0; j < count1; j++) {
                        clickTheElement(shifts.get(j));
                        if (isPopOverLayoutLoaded()) {
                            clickTheElement(deleteShift);
                            if (isDeleteShiftShowWell())
                                click(deleteBtnInDeleteWindows);
                        }
                    }
                    int count2 = deleteShiftImgsInWeekView.size();
                    if (count1 == count2)
                        SimpleUtils.pass("Schedule Page: Delete all the shifts in '" + dayPart + "' in week view successfully");
                    else
                        SimpleUtils.fail("Schedule Page: Failed to delete all the shifts in '" + dayPart + "' in week view",false);
                }
                break;
            }
        }
        if (!isFound)
            SimpleUtils.report("Schedule Page: Not find the given day part in week view");
    }

    @FindBy(css = ".tma-dismiss-button")
    private WebElement closeViewStatusBtn;
    @Override
    public void closeViewStatusContainer() throws Exception{
        if(isElementEnabled(closeViewStatusBtn,5)){
            click(closeViewStatusBtn);
            SimpleUtils.pass("Close button is available and clicked");
        }
        else {
            SimpleUtils.fail("Close Button is not enabled ", true);
        }

    }


    @FindBy (className = "sch-group-label")
    private List<WebElement> dayScheduleGroupLabels;

    @FindBy(css = "img[ng-src=\"img/legion/edit/deleted-shift-day@2x.png\"]")
    private List<WebElement> deleteShiftImgsInDayView;
    @Override
    public void deleteAllShiftsOfGivenDayPartInDayView(String dayPart) throws Exception {
        boolean isFound = false;
        int count1 = 0;
        for (int i = 0; i < dayScheduleGroupLabels.size(); i++) {
            if (dayScheduleGroupLabels.get(i).getText().equals(dayPart)) {
                isFound = true;
                for (int j = 0; j < dayScheduleGroupLabels.get(i).findElements(By.xpath("./../../following-sibling::div//worker-detail/div")).size(); j++) {
                    List<WebElement> shifts = dayScheduleGroupLabels.get(i).findElements(By.xpath("./../../following-sibling::div//worker-detail/div"));
                    count1 = shifts.size();
                    click(shifts.get(j));
                    if (isPopOverLayoutLoaded()) {
                        clickTheElement(deleteShift);
                        if (isDeleteShiftShowWell())
                            click(deleteBtnInDeleteWindows);
                    }
                }
                int count2 = deleteShiftImgsInDayView.size();
                if (count1 == count2)
                    SimpleUtils.pass("Schedule Page: Delete all the shifts in '" + dayPart + "' in day view successfully");
                else
                    SimpleUtils.fail("Schedule Page: Failed to delete all the shifts in '" + dayPart + "' in day view",false);
            }
            break;
        }
        if (!isFound)
            SimpleUtils.report("Schedule Page: Not find the given day part in week view");
    }

    @FindBy(css = "div.sch-worker-change-role-title")
    private WebElement schWorkerInfoPrompt;

    @FindBy(xpath ="//div[contains(@ng-click,'changeRoleMoveLeft($event)')]")
    private WebElement moveLeftWorkerInfoPrompt;

    @FindBy(xpath ="//div[contains(@ng-click,'changeRoleMoveRight($event)')]")
    private WebElement moveRightWorkerInfoPrompt;

    @FindBy(xpath= "//span[contains(@class,'sch-worker-change-role-name')]")
    private List<WebElement> schWrokersList;

    @FindBy(css= "div.sch-worker-change-role-body")
    private List<WebElement> shiftRoleList;

    @FindBy(css= "div.lgn-alert-modal")
    private WebElement roleViolationAlter;

    @FindBy(css= "button.lgn-action-button-success")
    private WebElement roleViolationAlterOkButton;

    @FindBy (css= "div[ng-style*='roleChangeStyle'] span")
    private List<WebElement> changeRoleValues;

    @FindBy (css = "div[ng-click*='changeRoleMoveRight'] i")
    private WebElement rightarrow;

    @FindBy (css = "button.sch-save")
    private WebElement applyButtonChangeRole;

    @FindBy (css = "button.sch-cancel")
    private WebElement cancelButtonChangeRole;

    @FindBy(css = "div[ng-class*='ChangeRole'] span")
    private WebElement changeRole;
    public void changeWorkRoleInPrompt(boolean isApplyChange) throws Exception {
        WebElement clickedShift = clickOnProfileIcon();
        clickOnChangeRole();
        if(isElementEnabled(schWorkerInfoPrompt,5)) {
            SimpleUtils.pass("Various Work Role Prompt is displayed ");
            String newSelectedWorkRoleName = null;
            String originSelectedWorkRoleName = null;
            if (areListElementVisible(shiftRoleList, 5) && shiftRoleList.size() > 0) {
                if (shiftRoleList.size() == 1) {
                    SimpleUtils.pass("There is only one Work Role in Work Role list ");
                    return;
                } else {
                    for (WebElement shiftRole : shiftRoleList) {
                        if (shiftRole.getAttribute("class").contains("sch-worker-change-role-body-selected")) {
                            originSelectedWorkRoleName = shiftRole.findElement(By.cssSelector("span.sch-worker-change-role-name")).getText();
                            SimpleUtils.pass("The original selected Role is '" + originSelectedWorkRoleName);
                            break;
                        }
                    }
                    for (WebElement shiftRole : shiftRoleList) {
                        if (!shiftRole.getAttribute("class").contains("sch-worker-change-role-body-selected")) {
                            click(shiftRole);
                            newSelectedWorkRoleName = shiftRole.findElement(By.cssSelector("span.sch-worker-change-role-name")).getText();
                            SimpleUtils.pass("Role '" + newSelectedWorkRoleName + "' is selected!");
                            break;
                        }
                    }

                }
            } else {
                SimpleUtils.fail("Work roles are doesn't show well ", true);
            }

            if (isElementEnabled(applyButtonChangeRole, 5) && isElementEnabled(cancelButtonChangeRole, 5)) {
                SimpleUtils.pass("Apply and Cancel buttons are enabled");
                if (isApplyChange) {
                    click(applyButtonChangeRole);
                    if (isElementEnabled(roleViolationAlter, 5)) {
                        click(roleViolationAlterOkButton);
                    }
                    //to close the popup
                    waitForSeconds(5);
                    clickTheElement(clickedShift);

                    clickTheElement(clickedShift.findElement(By.cssSelector(".rows .worker-image-optimized img")));
                    SimpleUtils.pass("Apply button has been clicked ");
                } else {
                    click(cancelButtonChangeRole);
                    SimpleUtils.pass("Cancel button has been clicked ");
                }
            } else {
                SimpleUtils.fail("Apply and Cancel buttons are doesn't show well ", true);
            }

            //check the shift role
            if (!isElementEnabled(changeRole, 5)) {
                click(clickedShift.findElement(By.cssSelector(".rows .worker-image-optimized img")));
            }
            clickOnChangeRole();
            if (areListElementVisible(shiftRoleList, 5) && shiftRoleList.size() >1) {
                for (WebElement shiftRole : shiftRoleList) {
                    if (shiftRole.getAttribute("class").contains("sch-worker-change-role-body-selected")) {
                        if (isApplyChange) {
                            if (shiftRole.findElement(By.cssSelector("span.sch-worker-change-role-name")).getText().equals(newSelectedWorkRoleName)) {
                                SimpleUtils.pass("Shift role been changed successfully ");
                            } else {
                                SimpleUtils.fail("Shift role failed to change ", true);
                            }
                        } else {
                            if (shiftRole.findElement(By.cssSelector("span.sch-worker-change-role-name")).getText().equals(originSelectedWorkRoleName)) {
                                SimpleUtils.pass("Shift role is not change ");
                            } else {
                                SimpleUtils.fail("Shift role still been changed when click Cancel button ", true);
                            }
                        }
                        break;
                    }
                }
            } else {
                SimpleUtils.fail("Shift roles are doesn't show well ", true);
            }
            if(isElementLoaded(cancelButtonChangeRole, 5)){
                click(cancelButtonChangeRole);
            }
        }
    }

    public void clickOnChangeRole() throws Exception {
        if(isElementLoaded(changeRole,5))
        {
            clickTheElement(changeRole);
            SimpleUtils.pass("Change Role option is clicked");
        }
        else
            SimpleUtils.fail("Change Role option is not enable",false);
    }


    @Override
    public void  verifyDeleteShiftCancelButton() throws Exception {
        ShiftOperatePage shiftOperatePage = new ConsoleShiftOperatePage();
        WebElement shift = clickOnProfileIcon();
        clickTheElement(deleteShift);
        if (shiftOperatePage.isDeleteShiftShowWell ()) {
            click(cancelBtnInDeleteWindows);
            if (isElementEnabled(shift, 5)) {
                SimpleUtils.pass("Shift not been deleted after click cancel button");
            } else {
                SimpleUtils.fail("Shift still been deleted after click cancel button", false);
            }
        }
    }


    @FindBy(css = ".shift-container.week-schedule-shift-wrapper")
    private List<WebElement> shifts;

    @FindBy (css = "div.sch-day-view-shift-worker-detail")
    private List<WebElement> scheduleTableWeekViewWorkerDetail;



    @FindBy(xpath = "//div[@ng-if=\"!forceShowOpen && showWorkerImage(shift)\"]/worker-image/div/div")
    private List<WebElement> profileIconsDayView;

    @Override
    public WebElement clickOnProfileIcon() throws Exception {
        WebElement selectedShift = null;
        if(isProfileIconsEnable()&& areListElementVisible(shifts, 10)) {
            int randomIndex = (new Random()).nextInt(profileIcons.size());
            while (profileIcons.get(randomIndex).getAttribute("src").contains("openShiftImage")){
                randomIndex = (new Random()).nextInt(profileIcons.size());
            }
            clickTheElement(profileIcons.get(randomIndex));
            selectedShift = shifts.get(randomIndex);
        } else if (areListElementVisible(scheduleTableWeekViewWorkerDetail, 10) && areListElementVisible(dayViewAvailableShifts, 10)) {
            int randomIndex = (new Random()).nextInt(scheduleTableWeekViewWorkerDetail.size());
            while (dayViewAvailableShifts.get(randomIndex).findElement(By.className("sch-day-view-shift-worker-name")).getText().contains("Open")){
                randomIndex = (new Random()).nextInt(scheduleTableWeekViewWorkerDetail.size());
            }
            clickTheElement(scheduleTableWeekViewWorkerDetail.get(randomIndex));
            selectedShift = dayViewAvailableShifts.get(randomIndex);
        } else
            SimpleUtils.fail("Can't Click on Profile Icon due to unavailability ",false);

        return selectedShift;
    }

    @Override
    public boolean isProfileIconsEnable() throws Exception {
        if(areListElementVisible(profileIcons,10)){
            SimpleUtils.pass("Profile Icon is present for selected Employee");
            return true;
        } else if (areListElementVisible(profileIconsDayView,10)) {
            SimpleUtils.pass("Profile Icon is present for selected Employee");
            return true;
        }
        else {
            SimpleUtils.fail("Profile Icon is not present for selected Employee", false);
            return false;
        }
    }


    @FindBy(css = "div[ng-click=\"editBreaksTime()\"]")
    private WebElement editMealBreakTime;

    @FindBy(css = "div.modal-instance-header-title")
    private WebElement editMealBreakTitle;

    @FindBy(css = "[data-tootik=\"Add a meal break\"] img")
    private WebElement addMealBreakButton;

    @FindBy(css = "[ng-click=\"closeModal()\"]")
    private WebElement cannelBtnInMealBreakButton;

    @FindBy(css = "[ng-click=\"confirm()\"]")
    private WebElement continueBtnInMealBreakButton;

    @FindBy(css = "day-part-weekday.ng-isolate-scope")
    private WebElement sliderInMealBreakButton;

    @FindBy(css = "[ng-click=\"removeBreak(b)\"]")
    private List<WebElement> deleteMealBreakButtons;

    @FindBy(css = "div.noUi-draggable")
    private List<WebElement> mealBreaks;

    @FindBy(css = "div.slider-section-description-break-time-item-meal")
    private List<WebElement> mealBreakTimes;

    @FindBy(css = "[id=\"unconstrained\"]")
    private WebElement mealBreakBar;

    @FindBy(className = "lgn-alert-modal")
    private WebElement confirmWindow;

    @FindBy(className = "lgn-action-button-success")
    private WebElement okBtnOnConfirm;

    @FindBy(css="div.worker-shift-container")
    private WebElement shiftInfoContainer;

    @FindBy(xpath ="//div[contains(@class,'day-week-picker-period-week')][3]")
    private WebElement pickNextWeekOnSchedule;

    @FindBy(css = "div[ng-click=\"editShiftTime($event, shift)\"]")
    private WebElement editShiftTime;

    @FindBy(css = "div[ng-class*='AssignTM'] span")
    private WebElement assignTM;

    @FindBy(css = "div[ng-class*='ConvertToOpen'] span")
    private WebElement convertOpen;

    @FindBy(css = "div[ng-class*='OfferTMs']")
    private WebElement OfferTMS;
    public boolean isEditMealBreakEnabled() throws Exception {
        clickOnProfileIcon();
        boolean isEditMealBreakEnabled = false;
        if(isElementLoaded(editMealBreakTime,5) )
        {
            if(editMealBreakTime.getText().equalsIgnoreCase("Edit Meal Break Time")){
                isEditMealBreakEnabled = true;
                SimpleUtils.report("Edit Meal Break function is enabled! ");
            } else{
                SimpleUtils.report("We can only view breaks!");
            }
        }
        else
            SimpleUtils.fail("Edit Meal Break Time is disabled or not available to Click ", false);
        return isEditMealBreakEnabled;
    }

    @Override
    public void verifyMealBreakTimeDisplayAndFunctionality(boolean isEditMealBreakEnabled) throws Exception {
        clickOnProfileIcon();
        clickOnEditMeaLBreakTime();
        if (isMealBreakTimeWindowDisplayWell(isEditMealBreakEnabled)) {
            if (isEditMealBreakEnabled){
                click(addMealBreakButton);
                click(continueBtnInMealBreakButton);
                SimpleUtils.pass("add meal break time successfully");
            } else {
                click(continueBtnInMealBreakButton);
            }
        }else
            SimpleUtils.report("add meal break failed");
    }

    @Override
    public void verifyDeleteMealBreakFunctionality() throws Exception {
        WebElement selectedShift = clickOnProfileIcon();
        clickOnEditMeaLBreakTime();
        if (isMealBreakTimeWindowDisplayWell(true)) {
            while (!areListElementVisible(deleteMealBreakButtons, 5) && deleteMealBreakButtons.size()>0) {
                click(cannelBtnInMealBreakButton);
                selectedShift = clickOnProfileIcon();
            }
            while(deleteMealBreakButtons.size()>0){
                click(deleteMealBreakButtons.get(0));
            }
            click(continueBtnInMealBreakButton);
            if (isElementEnabled(confirmWindow, 5)) {
                click(okBtnOnConfirm);
            }

        }else
            SimpleUtils.fail("Delete meal break window load failed",true);

        click(selectedShift.findElement(By.cssSelector(".rows .worker-image-optimized img")));
        clickOnEditMeaLBreakTime();
        if (isMealBreakTimeWindowDisplayWell(true)) {
            if (!areListElementVisible(deleteMealBreakButtons, 5)) {
                SimpleUtils.pass("Delete meal break times successfully");
            } else {
                SimpleUtils.fail("Delete meal break failed",false);
            }

        }else
            SimpleUtils.fail("Delete meal break window load failed",false);
        click(cannelBtnInMealBreakButton);
    }

    public void verifyEditMealBreakTimeFunctionality(boolean isSavedChange) throws Exception {
        String mealBreakTimeBeforeEdit = null;
        String mealBreakTimeAfterEdit = null;

        WebElement selectedShift = clickOnProfileIcon();
        clickOnEditMeaLBreakTime();
        if (isMealBreakTimeWindowDisplayWell(true)) {
            if (mealBreakBar.getAttribute("class").contains("disabled")) {
                click(addMealBreakButton);
                click(continueBtnInMealBreakButton);
                if (isElementEnabled(confirmWindow, 5)) {
                    click(okBtnOnConfirm);
                }
                click(selectedShift.findElement(By.cssSelector(".rows .worker-image-optimized img")));
                clickOnEditMeaLBreakTime();
            }
            mealBreakTimeBeforeEdit = mealBreakTimes.get(0).getText();
            moveDayViewCards(mealBreaks.get(0), 40);
            mealBreakTimeAfterEdit = mealBreakTimes.get(0).getText();
            if (isSavedChange) {
                click(continueBtnInMealBreakButton);
                if (isElementEnabled(confirmWindow, 5)) {
                    click(okBtnOnConfirm);
                }
            } else {
                click(cannelBtnInMealBreakButton);
            }
        }else
            SimpleUtils.fail("Meal break window load failed",true);

        click(selectedShift.findElement(By.cssSelector(".rows .worker-image-optimized img")));
        clickOnEditMeaLBreakTime();
        if (isMealBreakTimeWindowDisplayWell(true)) {
            if (isSavedChange) {
                if (mealBreakTimes.get(0).getText().equals(mealBreakTimeAfterEdit)) {
                    SimpleUtils.pass("Edit meal break times successfully");
                } else
                    SimpleUtils.fail("Edit meal break time failed",true);
            } else {
                if (mealBreakTimes.get(0).getText().equals(mealBreakTimeBeforeEdit)) {
                    SimpleUtils.pass("Edit meal break times not been changed after click Cancel button");
                } else
                    SimpleUtils.fail("Edit meal break times still been changed after click Cancel button",true);
            }
        }else
            SimpleUtils.fail("Meal break window load failed",true);
        click(cannelBtnInMealBreakButton);
    }

    @Override
    public boolean isMealBreakTimeWindowDisplayWell(boolean isEditMealBreakEnabled) throws Exception {
        if (isEditMealBreakEnabled){
            if (isElementLoaded(editMealBreakTitle,5) && isElementLoaded(addMealBreakButton,5) &&
                    isElementLoaded(cannelBtnInMealBreakButton,5) && isElementLoaded(continueBtnInMealBreakButton,5)
                    && isElementLoaded(sliderInMealBreakButton,5) && isElementEnabled(shiftInfoContainer, 5)) {
                SimpleUtils.pass("the Edit Meal break windows is pop up which include: 1.profile info 2.add meal break button 3.Specify meal break time period 4 cancel ,continue button");
                return  true;
            }else
                SimpleUtils.fail("edit meal break time windows load failed",true);
            return false;
        } else {
            if (isElementLoaded(editMealBreakTitle,5) && isElementLoaded(continueBtnInMealBreakButton,5)
                    && isElementLoaded(sliderInMealBreakButton,5) && isElementEnabled(shiftInfoContainer, 5)) {
                SimpleUtils.pass("the Edit Meal break windows is pop up which include: 1.profile info 2.Specify meal break time period 3 continue button");
                return  true;
            }else
                SimpleUtils.fail("edit meal break time windows load failed",true);
            return false;
        }
    }


    @Override
    public void clickOnEditMeaLBreakTime() throws Exception{
        if(isElementLoaded(editMealBreakTime,5))
        {
            click(editMealBreakTime);
            SimpleUtils.pass("Clicked on Edit Meal Break Time ");
        }
        else
            SimpleUtils.fail("Edit Meal Break Timeis disabled or not available to Click ", false);
    }


    @Override
    public void editAndVerifyShiftTime(boolean isSaveChange) throws Exception{
        List<String> shiftTime;

        WebElement selectedShift = clickOnProfileIcon();
        clickOnEditShiftTime();
        shiftTime = editShiftTime();
        if (isSaveChange) {
            clickOnUpdateEditShiftTimeButton();
        } else {
            clickOnCancelEditShiftTimeButton();
        }

        clickTheElement(selectedShift.findElement(By.cssSelector(".rows .worker-image-optimized img")));
        clickOnEditShiftTime();

        if (isSaveChange) {
            verifyShiftTime(shiftTime.get(1));
        } else {
            verifyShiftTime(shiftTime.get(0));
        }

        clickOnCancelEditShiftTimeButton();
    }

    @FindBy(css="div.slider-section.slider-section-old")
    private WebElement shiftStartAndEndTimeContainer;

    @FindBy(css="[ng-click=\"closeModal()\"]")
    private WebElement cancelButtonInEditShiftTimeWindow;

    @FindBy(css="[ng-click=\"confirm()\"]")
    private WebElement updateButtonInEditShiftTimeWindow;

    @FindBy(css="div.noUi-handle.noUi-handle-lower")
    private WebElement shiftStartTimeButton;

    @FindBy(css="div.noUi-handle.noUi-handle-upper")
    private WebElement shiftEndTimeButton;

    @FindBy(css="div.slider-section-description-break-time-item-blue")
    private WebElement shiftTimeInEditShiftWindow;

    @FindBy(css = ".noUi-marker")
    private List<WebElement> noUiMakers;

    @FindBy(css = ".noUi-value")
    private List<WebElement> noUiValues;

    @FindBy(css = "div[ng-class=\"workerActionClass('ViewProfile')\"]")
    private WebElement viewProfileOnIcon;

    @FindBy(css = "div[ng-class=\"workerActionClass('ViewOpenShift')\"]")
    private WebElement viewOpenShift;
    @Override
    public void clickOnEditShiftTime() throws Exception{
        if(isElementLoaded(editShiftTime,5))
        {
            clickTheElement(editShiftTime);
            SimpleUtils.pass("Clicked on Edit Shift Time ");
        }
        else
            SimpleUtils.fail("Edit Shift Time is disabled or not available to Click ", false);
    }

    public List<String> editShiftTime() throws Exception {
        List<String> shiftTimes= new ArrayList<>();
        if (isElementEnabled(shiftStartAndEndTimeContainer, 5) && isElementEnabled(shiftStartTimeButton, 5)
                && isElementEnabled(shiftEndTimeButton, 5) && isElementEnabled(shiftTimeInEditShiftWindow, 5)) {

            String shiftTimeBeforeUpdate = shiftTimeInEditShiftWindow.getText();
            shiftTimes.add(0, shiftTimeBeforeUpdate);
            if (areListElementVisible(noUiMakers, 5) && areListElementVisible(noUiValues, 5) && noUiMakers.size() == noUiValues.size()) {
                String currentNow = shiftEndTimeButton.getAttribute("aria-valuenow");
                int currentValue = Integer.parseInt(currentNow.substring(0, currentNow.indexOf('.')));
                mouseHoverDragandDrop(shiftEndTimeButton, noUiMakers.get(currentValue - 1));
                waitForSeconds(2);
            }
            String shiftTimeAfterUpdate = shiftTimeInEditShiftWindow.getText();
            if (!shiftTimeBeforeUpdate.equals(shiftTimeAfterUpdate)) {
                SimpleUtils.pass("Edit Shift Time successfully");
                shiftTimes.add(1, shiftTimeAfterUpdate);
            } else {
                SimpleUtils.fail("Shift Time doesn't change", false);
            }

        } else {
            SimpleUtils.fail("Edit Shift Time container load failed", false);
        }
        return shiftTimes;
    }


    @Override
    public void clickOnUpdateEditShiftTimeButton() throws Exception{
        if(isElementLoaded(updateButtonInEditShiftTimeWindow,5))
        {
            click(updateButtonInEditShiftTimeWindow);
            SimpleUtils.pass("Clicked on Update Edit Shift Time button");
        }
        else
            SimpleUtils.fail("Update button is disabled or not available to Click ", false);
    }

    @Override
    public void clickOnCancelEditShiftTimeButton() throws Exception{
        if(isElementLoaded(cancelButtonInEditShiftTimeWindow,5))
        {
            click(cancelButtonInEditShiftTimeWindow);
            SimpleUtils.pass("Clicked on Cancel Edit Shift Time button");
        }
        else
            SimpleUtils.fail("Cancel button is disabled or not available to Click ", false);
    }


    public void verifyShiftTime(String shiftTime) throws Exception {
        if (isElementEnabled(shiftStartAndEndTimeContainer, 5)) {
            if (shiftTimeInEditShiftWindow.getText().equals(shiftTime)) {
                SimpleUtils.pass("Edit Shift Time PopUp window load successfully");
            }

        } else {
            SimpleUtils.fail("Edit Shift Time container load failed", false);
        }
    }


    @Override
    public boolean verifyContextOfTMDisplay() throws Exception {
        clickOnProfileIcon();
        if (isViewProfileEnable()
                &&  isChangeRoleEnable()
                && isAssignTMEnable()
                && isConvertToOpenEnable()
                && isEditShiftTimeEnable()
                && isEditMealBreakTimeEnable()
                && isDeleteShiftEnable()) {
            SimpleUtils.pass("context of any TM show well and include: 1. View profile 2. Change shift role  3.Assign TM 4.  Convert to open shift is enabled for current and future week day 5.Edit meal break time 6. Delete shift");
            return true;

        }else
            return false;
    }


    @Override
    public boolean isViewProfileEnable() throws Exception {
        if(isElementEnabled(viewProfileOnIcon,5)){
            SimpleUtils.pass("View Profile  is enable/available on Pop Over Style!");
            return true;
        }
        else{
            SimpleUtils.fail("View Profile option is not enable/available on Pop Over Style ",true);
            return false;
        }
    }

    @Override
    public void clickOnViewProfile() throws Exception {
        if(isViewProfileEnable())
        {
            clickTheElement(viewProfileOnIcon);
            SimpleUtils.pass("View Profile Clicked on Pop Over Style!");
        }
        else {
            SimpleUtils.fail("View Profile can not be clicked ",false);
        }
    }


    @Override
    public boolean isViewOpenShiftEnable() throws Exception {
        if(isElementEnabled(viewOpenShift,5)){
            SimpleUtils.pass("View Open Shift  is enable/available on Pop Over Style!");
            return true;
        }
        else{
            SimpleUtils.fail("View Open Shift option is not enable/available on Pop Over Style ",true);
            return false;
        }
    }

    @Override
    public boolean isChangeRoleEnable() throws Exception {
        if(isElementEnabled(changeRole,5)){
            SimpleUtils.pass("Change Role is available on Pop Over Style!");
            return true;
        }
        else{
            SimpleUtils.fail("Change Role option is not enable/available on Pop Over Style ",true);
            return false;
        }
    }

    public boolean isEditShiftTimeEnable() throws Exception {
        if(isElementEnabled(editShiftTime,5)){
            SimpleUtils.pass("Edit Shift Time is available on Pop Over Style!");
            return true;
        }
        else{
            SimpleUtils.fail("Edit Shift Time is not enable/available on Pop Over Style ",true);
            return false;
        }
    }

    public boolean isEditMealBreakTimeEnable() throws Exception {
        if(isElementEnabled(editMealBreakTime,5)){
            SimpleUtils.pass("Edit Meal Break Time is available on Pop Over Style!");
            return true;
        }
        else{
            SimpleUtils.fail("Edit Meal Break Time is not enable/available on Pop Over Style ",true);
            return false;
        }
    }

    public boolean isDeleteShiftEnable() throws Exception {
        if(isElementEnabled(deleteShift,5)){
            SimpleUtils.pass("Delete Shift is available on Pop Over Style!");
            return true;
        }
        else{
            SimpleUtils.fail("Delete Shift is not enable/available on Pop Over Style ",true);
            return false;
        }
    }


    @Override
    public boolean isAssignTMEnable() throws Exception {
        if(isElementEnabled(assignTM,5)){
            SimpleUtils.pass("Assign TM is available on Pop Over Style!");
            return true;
        }
        else{
            SimpleUtils.fail("Assign TM option is not enable/available on Pop Over Style ",true);
            return false;
        }
    }
    @Override
    public void clickonAssignTM() throws Exception{
        if(isAssignTMEnable())
        {
            click(assignTM);
            SimpleUtils.pass("Clicked on Assign TM ");
        }
        else
            SimpleUtils.fail("Assign TM is disabled or not available to Click ", false);
    }

    @Override
    public void clickOnConvertToOpenShift() throws Exception{
        if(isConvertToOpenEnable())
        {
            clickTheElement(convertOpen);
            SimpleUtils.pass("Clicked on Convert to open shift successfully ");
        } else
            SimpleUtils.fail(" Convert to open shift is disabled or not available to Click ", false);
    }

    @Override
    public void verifyOfferTMOptionIsAvailable() throws Exception{
        if(isConvertToOpenEnable())
        {
            clickTheElement(convertOpen);
            SimpleUtils.pass("Clicked on Convert to open shift successfully ");
        } else
            SimpleUtils.fail(" Convert to open shift is disabled or not available to Click ", false);
    }



    @Override
    public boolean isConvertToOpenEnable() throws Exception {
        if(isElementEnabled(convertOpen,5)){
            SimpleUtils.pass("Convert To Open option is available on Pop Over Style!");
            return true;
        }
        else{
            SimpleUtils.fail("Convert To Open option is not enable/available on Pop Over Style ",true);
            return false;
        }
    }

    @Override
    public boolean isOfferTMOptionVisible() throws Exception {
        if(isElementEnabled(OfferTMS,5)){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public boolean isOfferTMOptionEnabled() throws Exception {
        if(isElementEnabled(OfferTMS,5) && !OfferTMS.getAttribute("class").toLowerCase().contains("graded-out")){
            return true;
        } else{
            return false;
        }
    }


    public WebElement clickOnProfileIconOfOpenShift() throws Exception {
        WebElement selectedShift = null;
        if(isProfileIconsEnable()&& areListElementVisible(shifts, 10)) {
            int i;
            for (i=0; i<profileIcons.size(); i++){
                if (profileIcons.get(i).getAttribute("src").contains("openShiftImage")){
                    break;
                }
            }
            clickTheElement(profileIcons.get(i));
            selectedShift = shifts.get(i);
        } else if (areListElementVisible(scheduleTableWeekViewWorkerDetail, 10) && areListElementVisible(dayViewAvailableShifts, 10)) {
            int i;
            for (i=0; i<dayViewAvailableShifts.size(); i++){
                if (dayViewAvailableShifts.get(i).findElement(By.className("sch-day-view-shift-worker-name")).getText().contains("Open")){
                    break;
                }
            }
            clickTheElement(scheduleTableWeekViewWorkerDetail.get(i));
            selectedShift = dayViewAvailableShifts.get(i);
        } else {
            SimpleUtils.fail("Can't Click on Profile Icon due to unavailability ",false);
        }
        return selectedShift;
    }

    @FindBy(css="div.tmprofile.profile-container.ng-scope")
    private WebElement tmpProfileContainer;

    @FindBy(css = "span.tm-nickname.ng-binding")
    private WebElement personalDetailsName;

    @FindBy(css = "div.profile-close-button-container")
    private WebElement closeViewProfileContainer;
    public String getTMDetailNameFromProfilePage(WebElement shift) throws Exception {
        ScheduleCommonPage scheduleCommonPage = new ConsoleScheduleCommonPage();
        String tmDetailName = null;
        if (scheduleCommonPage.isScheduleDayViewActive()) {
            clickTheElement(shift.findElement(By.cssSelector(".sch-day-view-shift .sch-shift-worker-img-cursor")));
        } else {
            clickTheElement(shift.findElement(By.cssSelector(".rows .worker-image-optimized img")));
        }

        clickOnViewProfile();
        if (isElementEnabled(tmpProfileContainer, 15)) {
            if (isElementEnabled(personalDetailsName, 5)) {
                tmDetailName = personalDetailsName.getText();
            } else
                SimpleUtils.fail("TM detail name fail to load!", false);
        } else
            SimpleUtils.fail("Profile page fail to load!", false);
        clickTheElement(closeViewProfileContainer);
        return tmDetailName;
    }

    @FindBy(css = ".noUi-connect.color_meal")
    private List<WebElement> mealBreakDurations;
    @FindBy(css = ".noUi-connect.color_rest")
    private List<WebElement> restBreakDurations;
    @FindBy(css = "[ng-click*=\"addBreak\"]")
    private List<WebElement> addBreakBtns;
    @FindBy(css = ".noUi-touch-area.color_meal")
    private List<WebElement> mealStartEndAreas;
    @FindBy(css = ".noUi-touch-area.color_rest")
    private List<WebElement> restStartEndAreas;
    @FindBy(css = "div.slider-section-description-break-time-item-rest")
    private List<WebElement> restBreakTimes;
    @Override
    public List<String> verifyEditBreaks() throws Exception {
        List<String> breakTimes = new ArrayList<>();
        if (isMealBreakTimeWindowDisplayWell(true)) {
            // Verify delete breaks functionality
            while(deleteMealBreakButtons.size()>0){
                click(deleteMealBreakButtons.get(0));
            }
            // Verify adding breaks functionality
            if (areListElementVisible(addBreakBtns, 5)) {
                for (WebElement addBreakBtn : addBreakBtns) {
                    clickTheElement(addBreakBtn);
                }
            } else {
                SimpleUtils.fail("Add meal & rest break buttons not loaded successfully!", false);
            }
            if (areListElementVisible(mealBreakDurations, 5) && areListElementVisible(restBreakDurations, 5) && mealBreakDurations.size() == 1
                    && restBreakDurations.size() == 1) {
                moveDayViewCards(mealBreakDurations.get(0), 40);
                moveDayViewCards(restBreakDurations.get(0), 40);
            } else {
                SimpleUtils.fail("Meal and rest breaks are not added after clicking the add button!", false);
            }
            if (areListElementVisible(mealStartEndAreas, 5) && areListElementVisible(restStartEndAreas, 5)) {
                moveDayViewCards(mealStartEndAreas.get(0), 40);
                moveDayViewCards(restStartEndAreas.get(0), 40);
            } else {
                SimpleUtils.fail("Meal and rest start/end area not loaded successfully!", false);
            }
            breakTimes.add(mealBreakTimes.get(0).getText());
            breakTimes.add(restBreakTimes.get(0).getText());
        }else
            SimpleUtils.fail("Edit meal break window load failed",true);
        return breakTimes;
    }


    @Override
    public void changeWorkRoleInPromptOfAShift(boolean isApplyChange, WebElement shift) throws Exception {
        WebElement clickedShift = shift;
        clickOnChangeRole();
        if(isElementEnabled(schWorkerInfoPrompt,5)) {
            SimpleUtils.pass("Various Work Role Prompt is displayed ");
            String newSelectedWorkRoleName = null;
            String originSelectedWorkRoleName = null;
            if (areListElementVisible(shiftRoleList, 5) && shiftRoleList.size() > 0) {
                if (shiftRoleList.size() == 1) {
                    SimpleUtils.pass("There is only one Work Role in Work Role list ");
                    return;
                } else {
                    for (WebElement shiftRole : shiftRoleList) {
                        if (shiftRole.getAttribute("class").contains("sch-worker-change-role-body-selected")) {
                            originSelectedWorkRoleName = shiftRole.findElement(By.cssSelector("span.sch-worker-change-role-name")).getText();
                            SimpleUtils.pass("The original selected Role is '" + originSelectedWorkRoleName);
                            break;
                        }
                    }
                    for (WebElement shiftRole : shiftRoleList) {
                        if (!shiftRole.getAttribute("class").contains("sch-worker-change-role-body-selected")) {
                            click(shiftRole);
                            newSelectedWorkRoleName = shiftRole.findElement(By.cssSelector("span.sch-worker-change-role-name")).getText();
                            SimpleUtils.pass("Role '" + newSelectedWorkRoleName + "' is selected!");
                            break;
                        }
                    }

                }
            } else {
                SimpleUtils.fail("Work roles are doesn't show well ", true);
            }

            if (isElementEnabled(applyButtonChangeRole, 5) && isElementEnabled(cancelButtonChangeRole, 5)) {
                SimpleUtils.pass("Apply and Cancel buttons are enabled");
                if (isApplyChange) {
                    click(applyButtonChangeRole);
                    if (isElementEnabled(roleViolationAlter, 5)) {
                        click(roleViolationAlterOkButton);
                    }
                    //to close the popup
                    waitForSeconds(5);
                    clickTheElement(clickedShift);

                    clickTheElement(clickedShift.findElement(By.cssSelector(".rows .worker-image-optimized img")));
                    SimpleUtils.pass("Apply button has been clicked ");
                } else {
                    click(cancelButtonChangeRole);
                    SimpleUtils.pass("Cancel button has been clicked ");
                }
            } else {
                SimpleUtils.fail("Apply and Cancel buttons are doesn't show well ", true);
            }

            //check the shift role
            if (!isElementEnabled(changeRole, 5)) {
                click(clickedShift.findElement(By.cssSelector(".rows .worker-image-optimized img")));
            }
            clickOnChangeRole();
            if (areListElementVisible(shiftRoleList, 5) && shiftRoleList.size() >1) {
                for (WebElement shiftRole : shiftRoleList) {
                    if (shiftRole.getAttribute("class").contains("sch-worker-change-role-body-selected")) {
                        if (isApplyChange) {
                            if (shiftRole.findElement(By.cssSelector("span.sch-worker-change-role-name")).getText().equals(newSelectedWorkRoleName)) {
                                SimpleUtils.pass("Shift role been changed successfully ");
                            } else {
                                SimpleUtils.fail("Shift role failed to change ", true);
                            }
                        } else {
                            if (shiftRole.findElement(By.cssSelector("span.sch-worker-change-role-name")).getText().equals(originSelectedWorkRoleName)) {
                                SimpleUtils.pass("Shift role is not change ");
                            } else {
                                SimpleUtils.fail("Shift role still been changed when click Cancel button ", true);
                            }
                        }
                        break;
                    }
                }
            } else {
                SimpleUtils.fail("Shift roles are doesn't show well ", true);
            }
            if(isElementLoaded(cancelButtonChangeRole, 5)){
                click(cancelButtonChangeRole);
            }
        }
    }

    @Override
    public void changeWorkRoleInPromptOfAShiftInDayView(boolean isApplyChange, String shiftid) throws Exception {
        ScheduleShiftTablePage scheduleShiftTablePage = new ConsoleScheduleShiftTablePage();
        clickOnChangeRole();
        if(isElementEnabled(schWorkerInfoPrompt,5)) {
            SimpleUtils.pass("Various Work Role Prompt is displayed ");
            String newSelectedWorkRoleName = null;
            String originSelectedWorkRoleName = null;
            if (areListElementVisible(shiftRoleList, 5) && shiftRoleList.size() > 0) {
                if (shiftRoleList.size() == 1) {
                    SimpleUtils.pass("There is only one Work Role in Work Role list ");
                    return;
                } else {
                    for (WebElement shiftRole : shiftRoleList) {
                        if (shiftRole.getAttribute("class").contains("sch-worker-change-role-body-selected")) {
                            originSelectedWorkRoleName = shiftRole.findElement(By.cssSelector("span.sch-worker-change-role-name")).getText();
                            SimpleUtils.pass("The original selected Role is '" + originSelectedWorkRoleName);
                            break;
                        }
                    }
                    for (WebElement shiftRole : shiftRoleList) {
                        if (!shiftRole.getAttribute("class").contains("sch-worker-change-role-body-selected")) {
                            click(shiftRole);
                            newSelectedWorkRoleName = shiftRole.findElement(By.cssSelector("span.sch-worker-change-role-name")).getText();
                            SimpleUtils.pass("Role '" + newSelectedWorkRoleName + "' is selected!");
                            break;
                        }
                    }

                }
            } else {
                SimpleUtils.fail("Work roles are doesn't show well ", true);
            }

            if (isElementEnabled(applyButtonChangeRole, 5) && isElementEnabled(cancelButtonChangeRole, 5)) {
                SimpleUtils.pass("Apply and Cancel buttons are enabled");
                if (isApplyChange) {
                    click(applyButtonChangeRole);
                    if (isElementEnabled(roleViolationAlter, 5)) {
                        click(roleViolationAlterOkButton);
                    }
                    //to close the popup
                    waitForSeconds(5);
                    clickTheElement(scheduleShiftTablePage.getShiftById(shiftid));

                    clickTheElement(scheduleShiftTablePage.getShiftById(shiftid).findElement(By.cssSelector(".sch-shift-worker-img-cursor")));
                    SimpleUtils.pass("Apply button has been clicked ");
                } else {
                    click(cancelButtonChangeRole);
                    SimpleUtils.pass("Cancel button has been clicked ");
                }
            } else {
                SimpleUtils.fail("Apply and Cancel buttons are doesn't show well ", true);
            }

            //check the shift role
            if (!isElementEnabled(changeRole, 5)) {
                click(scheduleShiftTablePage.getShiftById(shiftid).findElement(By.cssSelector(".sch-shift-worker-img-cursor")));
            }
            clickOnChangeRole();
            if (areListElementVisible(shiftRoleList, 5) && shiftRoleList.size() >1) {
                for (WebElement shiftRole : shiftRoleList) {
                    if (shiftRole.getAttribute("class").contains("sch-worker-change-role-body-selected")) {
                        if (isApplyChange) {
                            if (shiftRole.findElement(By.cssSelector("span.sch-worker-change-role-name")).getText().equals(newSelectedWorkRoleName)) {
                                SimpleUtils.pass("Shift role been changed successfully ");
                            } else {
                                SimpleUtils.fail("Shift role failed to change ", true);
                            }
                        } else {
                            if (shiftRole.findElement(By.cssSelector("span.sch-worker-change-role-name")).getText().equals(originSelectedWorkRoleName)) {
                                SimpleUtils.pass("Shift role is not change ");
                            } else {
                                SimpleUtils.fail("Shift role still been changed when click Cancel button ", true);
                            }
                        }
                        break;
                    }
                }
            } else {
                SimpleUtils.fail("Shift roles are doesn't show well ", true);
            }
            if(isElementLoaded(cancelButtonChangeRole, 5)){
                click(cancelButtonChangeRole);
            }
        }
    }

    @Override
    public void verifyEditMealBreakTimeFunctionalityForAShift(boolean isSavedChange, WebElement shift) throws Exception {
        String mealBreakTimeBeforeEdit = null;
        String mealBreakTimeAfterEdit = null;

        WebElement selectedShift = shift;
        clickOnEditMeaLBreakTime();
        if (isMealBreakTimeWindowDisplayWell(true)) {
            if (mealBreakBar.getAttribute("class").contains("disabled")) {
                click(addMealBreakButton);
                click(continueBtnInMealBreakButton);
                if (isElementEnabled(confirmWindow, 5)) {
                    click(okBtnOnConfirm);
                }
                click(selectedShift.findElement(By.cssSelector(".rows .worker-image-optimized img")));
                clickOnEditMeaLBreakTime();
            }
            mealBreakTimeBeforeEdit = mealBreakTimes.get(0).getText();
            moveDayViewCards(mealBreaks.get(0), 40);
            mealBreakTimeAfterEdit = mealBreakTimes.get(0).getText();
            if (isSavedChange) {
                click(continueBtnInMealBreakButton);
                if (isElementEnabled(confirmWindow, 5)) {
                    click(okBtnOnConfirm);
                }
            } else {
                click(cannelBtnInMealBreakButton);
            }
        }else
            SimpleUtils.fail("Meal break window load failed",true);

        click(selectedShift.findElement(By.cssSelector(".rows .worker-image-optimized img")));
        clickOnEditMeaLBreakTime();
        if (isMealBreakTimeWindowDisplayWell(true)) {
            if (isSavedChange) {
                if (mealBreakTimes.get(0).getText().equals(mealBreakTimeAfterEdit)) {
                    SimpleUtils.pass("Edit meal break times successfully");
                } else
                    SimpleUtils.fail("Edit meal break time failed",true);
            } else {
                if (mealBreakTimes.get(0).getText().equals(mealBreakTimeBeforeEdit)) {
                    SimpleUtils.pass("Edit meal break times not been changed after click Cancel button");
                } else
                    SimpleUtils.fail("Edit meal break times still been changed after click Cancel button",true);
            }
        }else
            SimpleUtils.fail("Meal break window load failed",true);
        click(cannelBtnInMealBreakButton);
    }

    @Override
    public void verifyEditMealBreakTimeFunctionalityForAShiftInDayView(boolean isSavedChange, String shiftid) throws Exception {
        ScheduleShiftTablePage scheduleShiftTablePage = new ConsoleScheduleShiftTablePage();
        String mealBreakTimeBeforeEdit = null;
        String mealBreakTimeAfterEdit = null;

        WebElement selectedShift = scheduleShiftTablePage.getShiftById(shiftid);
        clickOnEditMeaLBreakTime();
        if (isMealBreakTimeWindowDisplayWell(true)) {
            if (mealBreakBar.getAttribute("class").contains("disabled")) {
                click(addMealBreakButton);
                click(continueBtnInMealBreakButton);
                if (isElementEnabled(confirmWindow, 5)) {
                    click(okBtnOnConfirm);
                }
                click(scheduleShiftTablePage.getShiftById(shiftid).findElement(By.cssSelector(".sch-shift-worker-img-cursor")));
                clickOnEditMeaLBreakTime();
            }
            mealBreakTimeBeforeEdit = mealBreakTimes.get(0).getText();
            moveDayViewCards(mealBreaks.get(0), 40);
            mealBreakTimeAfterEdit = mealBreakTimes.get(0).getText();
            if (isSavedChange) {
                click(continueBtnInMealBreakButton);
                if (isElementEnabled(confirmWindow, 5)) {
                    click(okBtnOnConfirm);
                }
            } else {
                click(cannelBtnInMealBreakButton);
            }
        }else
            SimpleUtils.fail("Meal break window load failed",true);

        click(scheduleShiftTablePage.getShiftById(shiftid).findElement(By.cssSelector(".sch-shift-worker-img-cursor")));
        clickOnEditMeaLBreakTime();
        if (isMealBreakTimeWindowDisplayWell(true)) {
            if (isSavedChange) {
                if (mealBreakTimes.get(0).getText().equals(mealBreakTimeAfterEdit)) {
                    SimpleUtils.pass("Edit meal break times successfully");
                } else
                    SimpleUtils.fail("Edit meal break time failed",true);
            } else {
                if (mealBreakTimes.get(0).getText().equals(mealBreakTimeBeforeEdit)) {
                    SimpleUtils.pass("Edit meal break times not been changed after click Cancel button");
                } else
                    SimpleUtils.fail("Edit meal break times still been changed after click Cancel button",true);
            }
        }else
            SimpleUtils.fail("Meal break window load failed",true);
        click(cannelBtnInMealBreakButton);
    }


    @FindBy(css="div.edit-breaks-time-modal")
    private WebElement editShiftTimePopUp;
    @FindBy(css = ".modal-instance-button.confirm.ng-binding")
    private WebElement confirmBtnOnDragAndDropConfirmPage;
    public void editTheShiftTimeForSpecificShift(WebElement shift, String startTime, String endTime) throws Exception {
        ScheduleCommonPage scheduleCommonPage = new ConsoleScheduleCommonPage();
        By isUnAssignedShift = null;
        if (!scheduleCommonPage.isScheduleDayViewActive())
            isUnAssignedShift = By.cssSelector(".rows .week-view-shift-image-optimized span");
        else
            isUnAssignedShift = By.cssSelector(".sch-shift-worker-initials span");
        WebElement shiftPlusBtn = shift.findElement(isUnAssignedShift);
        if (isElementLoaded(shiftPlusBtn)) {
            click(shiftPlusBtn);
            if (isElementLoaded(shiftPopover)) {
                WebElement editShiftTimeOption = shiftPopover.findElement(By.cssSelector("[ng-if=\"canEditShiftTime && !isTmView()\"]"));
                if (isElementLoaded(editShiftTimeOption)) {
                    scrollToElement(editShiftTimeOption);
                    click(editShiftTimeOption);
                    if (isElementEnabled(editShiftTimePopUp, 5)) {
                        moveSliderAtCertainPointOnEditShiftTimePage(startTime, "Start");
                        moveSliderAtCertainPointOnEditShiftTimePage(endTime, "End");
                        waitForSeconds(2);
                        click(confirmBtnOnDragAndDropConfirmPage);
                    } else {
                        SimpleUtils.fail("Edit Shift Time PopUp window load failed", false);
                    }
                }
            }
        }
    }

    public void moveSliderAtCertainPointOnEditShiftTimePage(String shiftTime, String startingPoint) throws Exception {
        WebElement element = null;
        String time = "am";
        if(areListElementVisible(noUiValues, 15) && noUiValues.size() >0){
            for (WebElement noUiValue: noUiValues){
                if (noUiValue.getAttribute("class").contains("pm")) {
                    time = "pm";
                } else if (noUiValue.getAttribute("class").contains("am")){
                    time = "am";
                }
                if (time.equalsIgnoreCase(shiftTime.substring(shiftTime.length() - 2))) {
                    if(noUiValue.getText().equals(shiftTime.substring(0, shiftTime.length() - 2))){
                        element = noUiValue;
                        break;
                    }
                }
            }
        }
        if (element == null){
            SimpleUtils.fail("Cannot found the operating hour on edit operating hour page! ", false);
        }
        if(startingPoint.equalsIgnoreCase("End")){
            if(isElementLoaded(shiftEndTimeButton,10)){
                SimpleUtils.pass("Shift timings with Sliders loaded on page Successfully for End Point");
                mouseHoverDragandDrop(shiftEndTimeButton,element);
            } else{
                SimpleUtils.fail("Shift timings with Sliders not loaded on page Successfully", false);
            }
        }else if(startingPoint.equalsIgnoreCase("Start")){
            if(isElementLoaded(shiftStartTimeButton,10)){
                SimpleUtils.pass("Shift timings with Sliders loaded on page Successfully for End Point");
                mouseHoverDragandDrop(shiftStartTimeButton,element);
            } else{
                SimpleUtils.fail("Shift timings with Sliders not loaded on page Successfully", false);
            }
        }
    }

    @Override
    public void deleteMealBreakForOneShift(WebElement shift) throws Exception {
        click(shift.findElement(By.cssSelector(".rows .worker-image-optimized img")));
        clickOnEditMeaLBreakTime();

        if (areListElementVisible(deleteMealBreakButtons, 5)) {
            while(deleteMealBreakButtons.size()>0){
                click(deleteMealBreakButtons.get(0));
            }

            SimpleUtils.pass("Delete meal break times successfully");
        } else {
            SimpleUtils.report("Delete meal break fail to load! ");
        }
        click(continueBtnInMealBreakButton);
        if (isElementEnabled(confirmWindow, 5)) {
            click(okBtnOnConfirm);
        }
    }

    @Override
    public String getRandomWorkRole() throws Exception {
        ScheduleShiftTablePage scheduleShiftTablePage = new ConsoleScheduleShiftTablePage();
        List<String> shiftInfo = new ArrayList<>();
        while (shiftInfo.size() == 0) {
            shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
        }
        return shiftInfo.get(4);
    }

    @FindBy(className = "week-schedule-shift-wrapper")
    private List<WebElement> shiftsWeekView;
    @Override
    public void deleteAllOOOHShiftInWeekView() throws Exception {
        SmartCardPage smartCardPage = new ConsoleSmartCardPage();
        ScheduleShiftTablePage scheduleShiftTablePage = new ConsoleScheduleShiftTablePage();
        if (areListElementVisible(shiftsWeekView, 15) && smartCardPage.isRequiredActionSmartCardLoaded()) {
            smartCardPage.clickOnViewShiftsBtnOnRequiredActionSmartCard();
            for (WebElement shiftWeekView : shiftsWeekView) {
                try {
                    if (scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup(shiftWeekView).contains("Outside Operating hours")) {
                        WebElement image = shiftWeekView.findElement(By.cssSelector(".rows .week-view-shift-image-optimized span"));
                        clickTheElement(image);
                        waitForSeconds(3);
                        if (isElementLoaded(deleteShift, 5)) {
                            clickTheElement(deleteShift);
                            if (isElementLoaded(deleteBtnInDeleteWindows, 10)) {
                                click(deleteBtnInDeleteWindows);
                                SimpleUtils.pass("Schedule Week View: OOOH shift been deleted successfully");
                            } else
                                SimpleUtils.fail("delete confirm button load failed", false);
                        } else
                            SimpleUtils.fail("delete item for this OOOH shift load failed", false);
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            smartCardPage.clickOnClearShiftsBtnOnRequiredActionSmartCard();
        }else
            SimpleUtils.report("Schedule Week View: there is no shifts or Action Required smart card in this week");
    }


    @FindBy(css=".noUi-marker-large")
    private List<WebElement> shiftTimeLarges;
    @Override
    public void editShiftTimeToTheLargest() throws Exception {
        if (isElementLoaded(shiftStartTimeButton, 10) && isElementLoaded(shiftEndTimeButton, 10)
                && areListElementVisible(shiftTimeLarges, 10) && shiftTimeLarges.size() == 2) {
            mouseHoverDragandDrop(shiftStartTimeButton, shiftTimeLarges.get(0));
            mouseHoverDragandDrop(shiftEndTimeButton, shiftTimeLarges.get(1));
        } else {
            SimpleUtils.fail("Shift time elements failed to load!", false);
        }
    }
}
