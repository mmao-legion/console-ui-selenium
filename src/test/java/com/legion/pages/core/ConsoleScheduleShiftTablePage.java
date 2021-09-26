package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.ScheduleMainPage;
import com.legion.pages.ScheduleShiftTablePage;
import com.legion.utils.MyThreadLocal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleScheduleShiftTablePage extends BasePage implements ScheduleShiftTablePage {
    public ConsoleScheduleShiftTablePage() {
        PageFactory.initElements(getDriver(), this);
    }



    @FindBy(css = ".sch-day-view-shift")
    private List<WebElement> dayViewAvailableShifts;

    @Override
    public void reduceOvertimeHoursOfActiveWeekShifts() throws Exception {
        List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
        ScheduleMainPage scheduleMainPage = new ConsoleScheduleMainPage();
        for (WebElement activeWeekDay : ScheduleCalendarDayLabels) {
            click(activeWeekDay);
            List<WebElement> availableDayShifts = getAvailableShiftsInDayView();
            if (availableDayShifts.size() != 0) {
                scheduleMainPage.clickOnEditButton();
                for (WebElement shiftWithOT : getAvailableShiftsInDayView()) {
                    WebElement shiftRightSlider = shiftWithOT.findElement(By.cssSelector("div.sch-day-view-shift-pinch.right"));
                    String OTString = "hrs ot";
                    int xOffSet = -50;
                    while (shiftWithOT.getText().toLowerCase().contains(OTString)) {
                        moveDayViewCards(shiftRightSlider, xOffSet);
                    }
                }
                scheduleMainPage.clickSaveBtn();
                break;
            }
        }
    }


    public List<WebElement> getAvailableShiftsInDayView() {
        return dayViewAvailableShifts;
    }
}
