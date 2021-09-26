package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.ScheduleCommonPage;
import com.legion.pages.SmartCardPage;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleSmartCardPage extends BasePage implements SmartCardPage {
    public ConsoleSmartCardPage() {
        PageFactory.initElements(getDriver(), this);
    }

    @Override
    public HashMap<String, Float> getScheduleLabelHoursAndWages() throws Exception {
        HashMap<String, Float> scheduleHoursAndWages = new HashMap<String, Float>();
        WebElement budgetedScheduledLabelsDivElement = MyThreadLocal.getDriver().findElement(By.cssSelector("[ng-if*=\"isTitleBasedBudget()\"] .card-carousel-card"));
        if(isElementEnabled(budgetedScheduledLabelsDivElement,5))
        {
//			Thread.sleep(2000);
            String scheduleWagesAndHoursCardText = budgetedScheduledLabelsDivElement.getText();
            String [] tmp =  scheduleWagesAndHoursCardText.split("\n");
            String[] scheduleWagesAndHours = new String[5];
            if (tmp.length>6) {
                scheduleWagesAndHours[0] = tmp[0];
                scheduleWagesAndHours[1] = tmp[1];
                scheduleWagesAndHours[2] = tmp[2];
                scheduleWagesAndHours[3] = tmp[3]+" "+tmp[4]+" "+tmp[5];
                scheduleWagesAndHours[4] = tmp[6];
            }else
                scheduleWagesAndHours =tmp;
            for(int i = 0; i < scheduleWagesAndHours.length; i++)
            {
                if(scheduleWagesAndHours[i].toLowerCase().contains(ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.hours.getValue().toLowerCase()))
                {
                    if (scheduleWagesAndHours[i].split(" ").length == 4) {
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , scheduleWagesAndHours[i].split(" ")[1],
                                ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.budgetedHours.getValue());
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , scheduleWagesAndHours[i].split(" ")[2],
                                ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.scheduledHours.getValue());
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , scheduleWagesAndHours[i].split(" ")[3],
                                ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.otherHours.getValue());
                    } else {
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages, scheduleWagesAndHours[i].split(" ")[1],
                                ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.budgetedHours.getValue());
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages, scheduleWagesAndHours[i + 1],
                                ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.scheduledHours.getValue());
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages, scheduleWagesAndHours[i + 2],
                                ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.otherHours.getValue());
                    }
                    break;
                }
                else if(scheduleWagesAndHours[i].toLowerCase().contains(ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.wages.getValue().toLowerCase()))
                {
                    if (scheduleWagesAndHours[i].split(" ").length == 4) {
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , scheduleWagesAndHours[i].split(" ")[1]
                                .replace("$", ""), ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.budgetedWages.getValue());
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , scheduleWagesAndHours[i].split(" ")[2]
                                .replace("$", ""), ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.scheduledWages.getValue());
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , scheduleWagesAndHours[i].split(" ")[3]
                                .replace("$", ""), ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.otherWages.getValue());
                    } else {
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages, scheduleWagesAndHours[i].split(" ")[1]
                                .replace("$", ""), ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.budgetedWages.getValue());
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages, scheduleWagesAndHours[i + 1]
                                .replace("$", ""), ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.scheduledWages.getValue());
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages, scheduleWagesAndHours[i + 1]
                                .replace("$", ""), ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.otherWages.getValue());
                    }
                    break;
                }
            }
        }
        return scheduleHoursAndWages;
    }

    public HashMap<String, Float> updateScheduleHoursAndWages(HashMap<String, Float> scheduleHoursAndWages,
                                                                     String hours, String hoursAndWagesKey) {
        scheduleHoursAndWages.put(hoursAndWagesKey, Float.valueOf(hours.replaceAll(",","")));
        return scheduleHoursAndWages;
    }

    @Override
    public synchronized List<HashMap<String, Float>> getScheduleLabelHoursAndWagesDataForEveryDayInCurrentWeek() throws Exception {
        List<HashMap<String, Float>> ScheduleLabelHoursAndWagesDataForDays = new ArrayList<HashMap<String, Float>>();
        List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
        ScheduleCommonPage scheduleCommonPage = new ConsoleScheduleCommonPage();
        if(scheduleCommonPage.isScheduleDayViewActive()) {
            if(ScheduleCalendarDayLabels.size() != 0) {
                for(WebElement ScheduleCalendarDayLabel: ScheduleCalendarDayLabels)
                {
                    click(ScheduleCalendarDayLabel);
                    ScheduleLabelHoursAndWagesDataForDays.add(getScheduleLabelHoursAndWages());
                }
            }
            else {
                SimpleUtils.fail("Schedule Page Day View Calender not Loaded Successfully!", true);
            }
        }
        else {
            SimpleUtils.fail("Schedule Page Day View Button not Active!", true);
        }
        return ScheduleLabelHoursAndWagesDataForDays;
    }
}
