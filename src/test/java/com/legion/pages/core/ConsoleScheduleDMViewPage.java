package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.ScheduleDMViewPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

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

}
