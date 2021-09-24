package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.MySchedulePage;
import com.legion.pages.ScheduleCommonPage;
import com.legion.tests.core.ScheduleNewUITest;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

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
}
