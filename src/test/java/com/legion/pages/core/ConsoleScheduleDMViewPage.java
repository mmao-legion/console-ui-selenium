package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.ScheduleDMViewPage;
import com.legion.pages.SchedulePage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleScheduleDMViewPage extends BasePage implements ScheduleDMViewPage {

    @FindBy(css = ".analytics-new-table-group-row-open")
    private List<WebElement>  SchedulesInDMView;


    @FindBy(css = "[jj-switch-when=\\\"cells.CELL_BUDGET_HOURS\\\"]")
    private List<WebElement>  budgetHours;

    public ConsoleScheduleDMViewPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public float getBudgetedHourOfScheduleInDMViewByLocation(String location) throws Exception
    {
        float budgetedHours = 0;
        boolean isLocationMatched = false;
        if (areListElementVisible(SchedulesInDMView, 10) && SchedulesInDMView.size() != 0){
            for (WebElement schedule : SchedulesInDMView){
                WebElement locationInDMView = schedule.findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_UNTOUCHED\"]"));
                if (locationInDMView != null){
                    String locationNameInDMView = locationInDMView.getText();
                    if (locationNameInDMView !=null && locationNameInDMView.equals(location)){
                        isLocationMatched = true;
                        if (areListElementVisible(budgetHours, 5)){
                            budgetedHours = Float.parseFloat(schedule.findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_BUDGET_HOURS\"]")).getText());
                        } else
                            budgetedHours = Float.parseFloat(schedule.findElements(By.cssSelector("[ng-switch=\"headerIndexes[$index]\"]")).get(2).getText());
                        break;
                    }
                } else{
                    SimpleUtils.fail("Get budgeted hours in DM View failed, there is no location display in this schedule" , false);
                }
            }
            if(!isLocationMatched)
            {
                SimpleUtils.fail("Get budgeted hours in DM View failed, there is no matched location display in DM view" , false);
            } else{
                SimpleUtils.pass("Get budgeted hours in DM View successful, the budgeted hours is '" + budgetedHours + ".");
            }
        } else{
            SimpleUtils.fail("Get budgeted hours in DM View failed, there is no schedules display in DM view" , false);
        }
        return budgetedHours;
    }

    @FindBy(css = "span.analytics-new-table-published-status")
    private List<WebElement>  scheduleStatusOnScheduleDMViewPage;

    public Map<String, Integer> getThreeWeeksScheduleStatusFromScheduleDMViewPage() throws Exception {
        Map<String, Integer> scheduleStatusFromScheduleDMViewPage = new HashMap<>();
        SchedulePage schedulePage = new ConsoleScheduleNewUIPage();
        for (int j=1; j<=3; j++){
            int notStartedScheduleAccount = 0;
            int inProgressScheduleAccount = 0;
            int publishedScheduleAccount = 0;

            if(areListElementVisible(scheduleStatusOnScheduleDMViewPage, 10) && scheduleStatusOnScheduleDMViewPage.size()!=0) {
                for (int i = 0; i< scheduleStatusOnScheduleDMViewPage.size(); i++){
                    switch(scheduleStatusOnScheduleDMViewPage.get(i).getText()){
                        case "Not Started" :
                            notStartedScheduleAccount= notStartedScheduleAccount+1;
                            break;
                        case "In Progress" :
                            inProgressScheduleAccount = inProgressScheduleAccount+1;
                            break;
                        case "Published" :
                            publishedScheduleAccount = publishedScheduleAccount+1;
                            break;
                    }
                }
                switch(j){
                    case 1:
                        scheduleStatusFromScheduleDMViewPage.put("notStartedNumberForCurrentWeek", notStartedScheduleAccount);
                        scheduleStatusFromScheduleDMViewPage.put("inProgressForCurrentWeek", inProgressScheduleAccount);
                        scheduleStatusFromScheduleDMViewPage.put("publishedForCurrentWeek", publishedScheduleAccount);
                        schedulePage.navigateToNextWeek();
                        break;
                    case 2:
                        scheduleStatusFromScheduleDMViewPage.put("notStartedNumberForNextWeek", notStartedScheduleAccount);
                        scheduleStatusFromScheduleDMViewPage.put("inProgressForNextWeek", inProgressScheduleAccount);
                        scheduleStatusFromScheduleDMViewPage.put("publishedForNextWeek", publishedScheduleAccount);
                        schedulePage.navigateToNextWeek();
                        break;
                    case 3:
                        scheduleStatusFromScheduleDMViewPage.put("notStartedNumberForTheWeekAfterNext", notStartedScheduleAccount);
                        scheduleStatusFromScheduleDMViewPage.put("inProgressForTheWeekAfterNext", inProgressScheduleAccount);
                        scheduleStatusFromScheduleDMViewPage.put("publishedForTheWeekAfterNext", publishedScheduleAccount);
                        break;
                }
             } else{
                SimpleUtils.fail("Schedule status loaded fail on Schedule DM view" , false);
            }
        }
        return scheduleStatusFromScheduleDMViewPage;
    }
}
