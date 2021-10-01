package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.ScheduleCommonPage;
import com.legion.pages.ScheduleMainPage;
import com.legion.tests.core.ScheduleNewUITest;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleScheduleCommonPage extends BasePage implements ScheduleCommonPage {
    public ConsoleScheduleCommonPage() {
        PageFactory.initElements(getDriver(), this);
    }

    private static HashMap<String, String> propertyTimeZoneMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/LocationTimeZone.json");


    @FindBy(className = "console-navigation-item")
    private List<WebElement> consoleNavigationMenuItems;

    @FindBy(css = "div.console-navigation-item-label.Schedule")
    private WebElement goToScheduleButton;

    @FindBy(css = "#legion-app navigation div:nth-child(4)")
    private WebElement analyticsConsoleName;

    @FindBy(css = "div[helper-text*='Work in progress Schedule'] span.legend-label")
    private WebElement draft;

    @FindBy(css = "div[helper-text-position='top'] span.legend-label")
    private WebElement published;

    @FindBy(css = "div[helper-text*='final per schedule changes'] span.legend-label")
    private WebElement finalized;

    @FindBy(className = "overview-view")
    private WebElement overviewSectionElement;

    @FindBy(css = "lg-button[data-tootik=\"Edit Schedule\"]")
    private WebElement newEdit;

    @FindBy(css = "[ng-click=\"callOkCallback()\"]")
    private WebElement editAnywayPopupButton;

    final static String consoleScheduleMenuItemText = "Schedule";


    public void clickOnScheduleConsoleMenuItem() {
        if (areListElementVisible(consoleNavigationMenuItems, 10) && consoleNavigationMenuItems.size() != 0) {
            WebElement consoleScheduleMenuElement = SimpleUtils.getSubTabElement(consoleNavigationMenuItems, consoleScheduleMenuItemText);
            clickTheElement(consoleScheduleMenuElement);
            SimpleUtils.pass("'Schedule' Console Menu Loaded Successfully!");
        } else {
            SimpleUtils.fail("'Schedule' Console Menu Items Not Loaded Successfully!", false);
        }
    }

    @Override
    public void goToSchedulePage() throws Exception {

        checkElementVisibility(goToScheduleButton);
        activeConsoleName = analyticsConsoleName.getText();
        click(goToScheduleButton);
        SimpleUtils.pass("Schedule Page Loading..!");

        if (isElementLoaded(draft)) {
            SimpleUtils.pass("Draft is Displayed on the page");
        } else {
            SimpleUtils.fail("Draft not displayed on the page", true);
        }

        if (isElementLoaded(published)) {
            SimpleUtils.pass("Published is Displayed on the page");
        } else {
            SimpleUtils.fail("Published not displayed on the page", true);
        }

        if (isElementLoaded(finalized)) {
            SimpleUtils.pass("Finalized is Displayed on the page");
        } else {
            SimpleUtils.fail("Finalized not displayed on the page", true);
        }
    }


    @Override
    public boolean isSchedulePage() throws Exception {
        if (isElementLoaded(overviewSectionElement)) {
            return true;
        } else {
            return false;
        }
    }


    @FindBy(className = "day-week-picker-arrow-right")
    private WebElement calendarNavigationNextWeekArrow;

    @FindBy(className = "day-week-picker-arrow-left")
    private WebElement calendarNavigationPreviousWeekArrow;

    @Override
    public void clickOnWeekView() throws Exception {
		/*WebElement scheduleWeekViewButton = MyThreadLocal.getDriver().
			findElement(By.cssSelector("[ng-click=\"selectDayWeekView($event, 'week')\"]"));*/

        WebElement scheduleWeekViewButton = MyThreadLocal.getDriver().
                findElement(By.cssSelector("div.lg-button-group-last"));
        if (isElementLoaded(scheduleWeekViewButton,15)) {
            if (!scheduleWeekViewButton.getAttribute("class").toString().contains("selected"))//selected
            {
                clickTheElement(scheduleWeekViewButton);
            }
            SimpleUtils.pass("Schedule page week view loaded successfully!");
        } else {
            SimpleUtils.fail("Schedule Page Week View Button not Loaded Successfully!", true);
        }
    }


    @Override
    public void clickOnDayView() throws Exception {
		/*WebElement scheduleDayViewButton = MyThreadLocal.getDriver().
			findElement(By.cssSelector("[ng-click=\"selectDayWeekView($event, 'day')\"]"));*/
        WebElement scheduleDayViewButton = MyThreadLocal.getDriver().
                findElement(By.cssSelector("div.lg-button-group-first"));

        if (isElementLoaded(scheduleDayViewButton)) {
            if (!scheduleDayViewButton.getAttribute("class").toString().contains("enabled")) {
                click(scheduleDayViewButton);
            }
            SimpleUtils.pass("Schedule Page day view loaded successfully!");
        } else {
            SimpleUtils.fail("Schedule Page Day View Button not Loaded Successfully!", true);
        }
    }

    @Override
    public void navigateDayViewWithIndex(int dayIndex) {
        if (dayIndex < 7 && dayIndex >= 0) {
            try {
                clickOnDayView();
                List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
                if (ScheduleCalendarDayLabels.size() == 7) {
                    click(ScheduleCalendarDayLabels.get(dayIndex));
                }
            } catch (Exception e) {
                SimpleUtils.fail("Unable to navigate to in Day View", false);
            }
        } else {
            SimpleUtils.fail("Invalid dayIndex value to verify Store is Closed for the day", false);
        }

    }

    @Override
    public void navigateDayViewWithDayName(String dayName) throws Exception {
        // The day name should be: Fri, Sat, Sun, Mon, Tue, Wed, Thu
        clickOnDayView();
        List<WebElement> scheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
        if (scheduleCalendarDayLabels.size() == 7) {
            boolean isDayNameExists = false;
            for (WebElement day: scheduleCalendarDayLabels ){
                if (day.getText().contains(dayName)) {
                    click(day);
                    isDayNameExists = true;
                    break;
                }
            }
            if(!isDayNameExists){
                SimpleUtils.fail("The day name is not exists", false);
            }
        } else
            SimpleUtils.fail("Week day picker display incorrectly! ", false);
    }



    @FindBy(css = "div.holiday-logo-container")
    private WebElement holidayLogoContainer;

    @Override
    public void navigateWeekViewOrDayViewToPastOrFuture(String nextWeekViewOrPreviousWeekView, int weekCount) {
        String currentWeekStartingDay = "NA";
        List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
        for (int i = 0; i < weekCount; i++) {
            if (ScheduleCalendarDayLabels.size() != 0) {
                currentWeekStartingDay = ScheduleCalendarDayLabels.get(0).getText();
            }

            int displayedWeekCount = ScheduleCalendarDayLabels.size();
            for (WebElement ScheduleCalendarDayLabel : ScheduleCalendarDayLabels) {
                if (ScheduleCalendarDayLabel.getAttribute("class").toString().contains("day-week-picker-period-active")) {
                    if (nextWeekViewOrPreviousWeekView.toLowerCase().contains("next") || nextWeekViewOrPreviousWeekView.toLowerCase().contains("future")) {
                        try {
                            int activeWeekIndex = ScheduleCalendarDayLabels.indexOf(ScheduleCalendarDayLabel);
                            if (activeWeekIndex < (displayedWeekCount - 1)) {
                                click(ScheduleCalendarDayLabels.get(activeWeekIndex + 1));
                            } else {
                                click(calendarNavigationNextWeekArrow);
                                click(ScheduleCalendarDayLabels.get(0));
                            }
                        } catch (Exception e) {
                            SimpleUtils.report("Schedule page Calender Next Week Arrows Not Loaded/Clickable after '" + ScheduleCalendarDayLabel.getText().replace("\n", "") + "'");
                        }
                    } else {
                        try {
                            int activeWeekIndex = ScheduleCalendarDayLabels.indexOf(ScheduleCalendarDayLabel);
                            if (activeWeekIndex > 0) {
                                click(ScheduleCalendarDayLabels.get(activeWeekIndex - 1));
                            } else {
                                click(calendarNavigationPreviousWeekArrow);
                                click(ScheduleCalendarDayLabels.get(displayedWeekCount - 1));
                            }
                        } catch (Exception e) {
                            SimpleUtils.fail("Schedule page Calender Previous Week Arrows Not Loaded/Clickable after '" + ScheduleCalendarDayLabel.getText().replace("\n", "") + "'", true);
                        }
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void navigateToNextDayIfStoreClosedForActiveDay() throws Exception {
        String dayType = "Next";
        int dayCount = 1;
        if (isStoreClosedForActiveWeek())
            navigateWeekViewOrDayViewToPastOrFuture(dayType, dayCount);
        if (!isStoreClosedForActiveWeek())
            SimpleUtils.pass("Navigated to Next day successfully!");
    }

    @Override
    public boolean isStoreClosedForActiveWeek() throws Exception {
        if (isElementLoaded(holidayLogoContainer, 10)) {
            SimpleUtils.report("Store is Closed for the Day/Week: '" + getActiveWeekText() + "'.");
            return true;
        }
        return false;
    }

    public void clickOnPreviousDaySchedule(String activeDay) throws Exception {
        List<WebElement> activeWeek = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
        for(int i=0; i<activeWeek.size();i++){
            String currentDay = activeWeek.get(i).getText().replace("\n", " ").substring(0,3);
            if(currentDay.equalsIgnoreCase(activeDay)){
                if(i== activeWeek.size()-1){
                    navigateWeekViewOrDayViewToPastOrFuture(ScheduleNewUITest.weekViewType.Previous.getValue(),
                            ScheduleNewUITest.weekCount.One.getValue());
                    waitForSeconds(3);
                }else{
                    click(activeWeek.get(i));
                }
            }
        }

    }

    public void clickOnNextDaySchedule(String activeDay) throws Exception {
        List<WebElement> activeWeek = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
        for(int i=0; i<activeWeek.size();i++){
            String currentDay = activeWeek.get(i).getText().replace("\n", " ").substring(0,3);
            if(currentDay.equalsIgnoreCase(activeDay)){
                if(i== activeWeek.size()-1){
                    navigateWeekViewOrDayViewToPastOrFuture(ScheduleNewUITest.weekViewType.Next.getValue(),
                            ScheduleNewUITest.weekCount.One.getValue());
                    waitForSeconds(3);
                }else{
                    click(activeWeek.get(i+1));
                }
            }
        }
    }


    @Override
    public void validateForwardAndBackwardButtonClickable() throws Exception {
        String activeWeekText = getActiveWeekText();
        if (isElementLoaded(calendarNavigationNextWeekArrow, 10)) {
            try {
                navigateWeekViewOrDayViewToPastOrFuture(ScheduleNewUITest.weekViewType.Next.getValue(), ScheduleNewUITest.weekCount.Three.getValue());
                navigateWeekViewOrDayViewToPastOrFuture(ScheduleNewUITest.weekViewType.Previous.getValue(), ScheduleNewUITest.weekCount.Three.getValue());
                if (activeWeekText.equals(getActiveWeekText()))
                    SimpleUtils.pass("My Schedule Page: Forward and backward button to view previous or upcoming week is clickable successfully");
            } catch (Exception e) {
                SimpleUtils.fail("My Schedule Page: Exception occurs when clicking forward and backward button", true);
            }
        } else if (isElementLoaded(calendarNavigationPreviousWeekArrow, 10)) {
            try {
                navigateWeekViewOrDayViewToPastOrFuture(ScheduleNewUITest.weekViewType.Previous.getValue(), ScheduleNewUITest.weekCount.Three.getValue());
                navigateWeekViewOrDayViewToPastOrFuture(ScheduleNewUITest.weekViewType.Next.getValue(), ScheduleNewUITest.weekCount.Three.getValue());
                if (activeWeekText.equals(getActiveWeekText()))
                    SimpleUtils.pass("My Schedule Page: Forward and backward button to view previous or upcoming week is clickable successfully");
            } catch (Exception e) {
                SimpleUtils.fail("My Schedule Page: Exception occurs when clicking forward and backward button", true);
            }
        } else
            SimpleUtils.fail("My Schedule Page: Forward and backward button failed to load to view previous or upcoming week", true);
    }

    @FindBy(css = "div.sub-navigation-view-link")
    private List<WebElement> ScheduleSubTabsElement;
    @FindBy(css = "div.sub-navigation-view-link.active")
    private WebElement activatedSubTabElement;

    @Override
    public void clickOnScheduleSubTab(String subTabString) throws Exception {
        if (ScheduleSubTabsElement.size() != 0 && !verifyActivatedSubTab(subTabString)) {
            for (WebElement ScheduleSubTabElement : ScheduleSubTabsElement) {
                if (ScheduleSubTabElement.getText().equalsIgnoreCase(subTabString)) {
                    waitForSeconds(5);
                    clickTheElement(ScheduleSubTabElement);
                    waitForSeconds(3);
                    break;
                }
            }

        }

        if (verifyActivatedSubTab(subTabString)) {
            SimpleUtils.pass("Schedule Page: '" + subTabString + "' tab loaded Successfully!");
        } else {
            SimpleUtils.fail("Schedule Page: '" + subTabString + "' tab not loaded Successfully!", true);
        }
    }

    @Override
    public Boolean verifyActivatedSubTab(String SubTabText) throws Exception {
        if (isElementLoaded(activatedSubTabElement,15)) {
            if (activatedSubTabElement.getText().toUpperCase().contains(SubTabText.toUpperCase())) {
                return true;
            }
        } else {
            SimpleUtils.fail("Schedule Page not loaded successfully", true);
        }
        return false;
    }

    public Boolean isScheduleDayViewActive() {
//        WebElement scheduleDayViewButton = MyThreadLocal.getDriver().
//                findElement(By.cssSelector("[ng-click=\"selectDayWeekView($event, 'day')\"]"));
        WebElement scheduleDayViewButton = MyThreadLocal.getDriver().
                findElement(By.cssSelector("div.lg-button-group-first"));
        if (scheduleDayViewButton.getAttribute("class").contains("selected")) {
            return true;
        }
        return false;
    }

    public String getScheduleWeekStartDayMonthDate() {
        String scheduleWeekStartDuration = "NA";
        WebElement scheduleCalendarActiveWeek = MyThreadLocal.getDriver().findElement(By.className("day-week-picker-period-active"));
        try {
            if (isElementLoaded(scheduleCalendarActiveWeek)) {
                scheduleWeekStartDuration = scheduleCalendarActiveWeek.getText().replace("\n", "");
            }
        } catch (Exception e) {
            SimpleUtils.fail("Calender duration bar not loaded successfully", true);
        }
        return scheduleWeekStartDuration;
    }

    public void navigateDayViewToPast(String PreviousWeekView, int dayCount)
    {
        ScheduleMainPage scheduleMainPage = new ConsoleScheduleMainPage();
        String currentWeekStartingDay = "NA";
        List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
        for(int i = 0; i < dayCount; i++)
        {

            try {
                click(ScheduleCalendarDayLabels.get(i));
                scheduleMainPage.clickOnEditButton();
                scheduleMainPage.clickOnCancelButtonOnEditMode();
            } catch (Exception e) {
                SimpleUtils.fail("Schedule page Calender Previous Week Arrows Not Loaded/Clickable", true);
            }


        }
    }


    @FindBy(xpath = "//*[contains(@class,'day-week-picker-period-active')]/following-sibling::div[1]")
    private WebElement immediateNextToCurrentActiveWeek;

    @FindBy(xpath = "//*[contains(@class,'day-week-picker-period-active')]/preceding-sibling::div[1]")
    private WebElement immediatePastToCurrentActiveWeek;

    public void clickImmediateNextToCurrentActiveWeekInDayPicker() {
        if (isElementEnabled(immediateNextToCurrentActiveWeek, 30)) {
            click(immediateNextToCurrentActiveWeek);
        } else {
            SimpleUtils.report("This is a last week in Day Week picker");
        }
    }

    public void clickImmediatePastToCurrentActiveWeekInDayPicker() {
        if (isElementEnabled(immediatePastToCurrentActiveWeek, 30)) {
            click(immediatePastToCurrentActiveWeek);
        } else {
            SimpleUtils.report("This is a last week in Day Week picker");
        }
    }

    @Override
    public String getActiveWeekText() throws Exception {
        if (isElementLoaded(MyThreadLocal.getDriver().findElement(By.className("day-week-picker-period-active")),15))
            return MyThreadLocal.getDriver().findElement(By.className("day-week-picker-period-active")).getText().replace("\n", " ");
        return "";
    }

    public String getActiveAndNextDay() throws Exception{
        WebElement activeWeek = MyThreadLocal.getDriver().findElement(By.className("day-week-picker-period-active"));
        String activeDay = "";
        if(isElementLoaded(activeWeek)){
            activeDay = activeWeek.getText().replace("\n", " ").substring(0,3);
        }
        return activeDay;
    }

    @FindBy (css = "lg-button-group[buttons*=\"custom\"] div.lg-button-group-first")
    private WebElement scheduleDayViewButton;
    @FindBy (className = "period-name")
    private WebElement periodName;
    @Override
    public void isScheduleForCurrentDayInDayView(String dateFromDashboard) throws Exception {
        String tagName = "span";
        if (isElementLoaded(scheduleDayViewButton, 5) && isElementLoaded(periodName, 5)) {
            if (scheduleDayViewButton.getAttribute("class").contains("lg-button-group-selected")){
                SimpleUtils.pass("The Schedule Day View Button is selected!");
            }else{
                SimpleUtils.fail("The Schedule Day View Button isn't selected!", true);
            }
            /*
             * @periodName format "Schedule for Wednesday, February 12"
             */
            if (periodName.getText().contains(dateFromDashboard)) {
                SimpleUtils.pass("The Schedule is for current day!");
            }else{
                SimpleUtils.fail("The Schedule isn't for current day!", true);
            }
        }else{
            SimpleUtils.fail("The Schedule Day View Button isn't loaded!",true);
        }
    }


    public void currentWeekIsGettingOpenByDefault(String location) throws Exception {
        String jsonTimeZoon = propertyTimeZoneMap.get(location);
        TimeZone timeZone = TimeZone.getTimeZone(jsonTimeZoon);
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
        dfs.setTimeZone(timeZone);
        String currentTime =  dfs.format(new Date());
        Date currentDate = dfs.parse(currentTime);
        String weekBeginEndByCurrentDate = SimpleUtils.getThisWeekTimeInterval(currentDate);
        String weekBeginEndByCurrentDate2 = weekBeginEndByCurrentDate.replace("-","").replace(",","");
        String weekBeginBYCurrentDate = weekBeginEndByCurrentDate2.substring(6,8);
        String weekEndBYCurrentDate = weekBeginEndByCurrentDate2.substring(weekBeginEndByCurrentDate2.length()-2);
        SimpleUtils.report("weekBeginBYCurrentDate is : "+ weekBeginBYCurrentDate);
        SimpleUtils.report("weekEndBYCurrentDate is : "+ weekEndBYCurrentDate);
        String activeWeekText =getActiveWeekText();
        String weekDefaultBegin = activeWeekText.substring(14,16);
        SimpleUtils.report("weekDefaultBegin is :"+weekDefaultBegin);
        String weekDefaultEnd = activeWeekText.substring(activeWeekText.length()-2);
        SimpleUtils.report("weekDefaultEnd is :"+weekDefaultEnd);
        if (SimpleUtils.isNumeric(weekBeginBYCurrentDate.trim()) && SimpleUtils.isNumeric(weekDefaultBegin.trim()) &&
                SimpleUtils.isNumeric(weekEndBYCurrentDate.trim()) && SimpleUtils.isNumeric(weekDefaultEnd.trim())) {
            if (Math.abs(Integer.parseInt(weekBeginBYCurrentDate.trim()) - Integer.parseInt(weekDefaultBegin.trim())) <= 1 &&
                    Math.abs(Integer.parseInt(weekEndBYCurrentDate.trim()) - Integer.parseInt(weekDefaultEnd.trim())) <= 1 &&
                    (Math.abs(Integer.parseInt(weekBeginBYCurrentDate.trim()) - Integer.parseInt(weekDefaultBegin.trim())) ==
                            Math.abs(Integer.parseInt(weekEndBYCurrentDate.trim()) - Integer.parseInt(weekDefaultEnd.trim())))) {
                SimpleUtils.pass("Current week is getting open by default");
            } else {
                SimpleUtils.fail("Current week is not getting open by default", false);
            }
        }else {
            SimpleUtils.fail("The date is not the numeric format!", false);
        }
    }


    @FindBy (xpath = "//span[contains(text(),'Schedule')]")
    private WebElement ScheduleSubMenu;

    @FindBy(css = "[ng-click=\"showTodos($event)\"]")
    private WebElement todoButton;

    public void goToScheduleNewUI() throws Exception {

        if (isElementLoaded(goToScheduleButton,5)) {
            click(goToScheduleButton);
            click(ScheduleSubMenu);
            if (isElementLoaded(todoButton,5)) {
                SimpleUtils.pass("Schedule New UI load successfully");
            }
        }else{
            SimpleUtils.fail("Schedule New UI load failed", true);
        }

    }


    @FindBy(css = "div.day-week-picker-period-active")
    private WebElement daypicker;

    @FindBy(xpath = "//*[text()=\"Day\"]")
    private WebElement daypButton;
    public void dayWeekPickerSectionNavigatingCorrectly()  throws Exception{
        String weekIcon = "Sun - Sat";
        String activeWeekText = getActiveWeekText();
        if(activeWeekText.contains(weekIcon)){
            SimpleUtils.pass("Week pick show correctly");
        }else {
            SimpleUtils.fail("it's not week mode", true);
        }
        click(daypButton);
        if(isElementLoaded(daypicker,3)){
            SimpleUtils.pass("Day pick show correctly");
        }else {
            SimpleUtils.fail("change to day pick failed", true);
        }

    }


    public int getMinutesFromTime(String time) {
        int minutes = 0;
        if (time.contains(":")) {
            String minute = time.split(":")[1].substring(0, time.split(":")[1].length()-2).trim();
            minutes = (Integer.parseInt(time.split(":")[0].trim())) * 60 + Integer.parseInt(minute);
        }else {
            minutes = Integer.parseInt(time.substring(0, time.length()-2)) * 60;
        }
        if (time.toLowerCase().endsWith("pm")) {
            minutes += 12 * 60;
        }
        return minutes;
    }


    @FindBy (css = "div.day-week-picker-period")
    private List<WebElement> dayPickerAllDaysInDayView;
    @Override
    public int getTheIndexOfCurrentDayInDayView() throws Exception {
        int index = 7;
        if (areListElementVisible(dayPickerAllDaysInDayView, 10)) {
            for (int i = 0; i < dayPickerAllDaysInDayView.size(); i++) {
                if (dayPickerAllDaysInDayView.get(i).getAttribute("class").contains("day-week-picker-period-active")) {
                    index = i;
                    SimpleUtils.pass("Schedule Day view: Get the current day index: " + index);
                }
            }
        }
        if (index == 7) {
            SimpleUtils.fail("Schedule Day view: Failed to get the index of CurrentDay", false);
        }
        return index;
    }
}
