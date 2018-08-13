package com.legion.pages.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;

import com.legion.pages.BasePage;
import com.legion.pages.SchedulePage;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConsoleSchedulePage extends BasePage implements SchedulePage {

	public ConsoleSchedulePage(WebDriver driver)
	{
		super(driver);
	}
	
	By goToScheduleButton = By.xpath("//*[@id=\"legion-app\"]/div/div[2]/div/div/div/div[1]/navigation/div/div[4]");
	//By goToTeamCoverageTab = By.cssSelector("[class='sub-navigation-view-label ng-binding']");
	//By goToScheduleButton = By.cssSelector("[class='console-navigation-item-label Schedule']");

    By goToScheduleOverviewTab = By.xpath(".//*[@id='legion-app']/div/div[2]/div/div/div/div[1]/navigation/div/div[4]/div[1]/div");
    By goToProjectedSalesTab = By.xpath(".//*[@id='legion-app']/div/div[2]/div/div/div/div[2]/div/div/div[1]/sub-navigation/div/div[3]/div[1]/div[2]/div/span");

    By goToStaffingGuidanceTab = By.xpath(".//*[@id='legion-app']/div/div[2]/div/div/div/div[2]/div/div/div[1]/sub-navigation/div/div[3]/div[1]/div[3]/div/span");

    By goToScheduleTab = By.xpath(".//*[@id='legion-app']/div/div[2]/div/div/div/div[2]/div/div/div[1]/sub-navigation/div/div[3]/div[1]/div[4]/div/span");


    public void gotoToSchedulePage() throws Exception {

        waitForElement(goToScheduleButton);
        click(goToScheduleButton);
        Reporter.log("Schedule Page Loading..!");


        WebElement Draft = driver.findElement(By.xpath(".//*[@id='legion-app']/div/div[2]/div/div/div/div[2]/div/div/div[4]/div/div[1]/div/div[1]/div/span"));
        //	WebElement Draft = driver.findElement(By.className("[class='legend-label ng-binding']"));
        Assert.assertEquals(true, Draft.isDisplayed());
        Reporter.log("Draft is Displayed");

        WebElement Published = driver.findElement(By.xpath(".//*[@id='legion-app']/div/div[2]/div/div/div/div[2]/div/div/div[4]/div/div[1]/div/div[2]/div/span"));
        Assert.assertEquals(true, Published.isDisplayed());
        Reporter.log("Published is Displayed");

        WebElement Finalized = driver.findElement(By.xpath(".//*[@id='legion-app']/div/div[2]/div/div/div/div[2]/div/div/div[4]/div/div[1]/div/div[3]/div/span"));
        Assert.assertEquals(true, Finalized.isDisplayed());
        Reporter.log("Finalized is Displayed");

        WebElement ScheduleWeekandStatus = driver.findElement(By.xpath(".//*[@id='legion-app']/div/div[2]/div/div/div/div[2]/div/div/div[4]/div/div[1]/div/div[2]/div/span"));
        // WebElement ScheduleWeekandStatus  = driver.findElement(By.cssSelector("[ng-class='nameClass()']"));
        Assert.assertEquals(true, ScheduleWeekandStatus.isDisplayed());
        Reporter.log("ScheduleWeekandStatus is Displayed");
    }

    public boolean isSchedulePage() throws Exception {

        //	Reporter.log("isSchedulePage method Called..!");
        By overviewSectionElement = By.className("overview-view");
        waitForElement(overviewSectionElement);
        Reporter.log("Schedule Page Loaded Successfully!");
        return true;
    }


    public void goToProjectedSales() throws Exception {

        waitForElement(goToProjectedSalesTab);
        click(goToProjectedSalesTab);
        Reporter.log("ProjectedSales Page Loading..!");

        WebElement SalesGuidance = driver.findElement(By.xpath(".//*[@id='legion-app']/div/div[2]/div/div/div/div[2]/div/div/div[2]/control-panel/ui-view/ui-view/div/div[1]/div[2]/span"));
        Assert.assertEquals(true, SalesGuidance.isDisplayed());
        Reporter.log("SalesGuidance is Displayed");

        WebElement Refresh = driver.findElement(By.xpath(".//*[@id='legion-app']/div/div[2]/div/div/div/div[2]/div/div/div[2]/control-panel/ui-view/ui-view/div/div[2]/div/span"));
        Assert.assertEquals(true, Refresh.isDisplayed());
        Reporter.log("Refresh is Displayed");


    }

    public boolean isProjectedSales() throws Exception {

        //	Reporter.log("isProjectedSales method Called..!");
        By projectedsalesSectionElement = By.xpath(".//*[@id='legion-app']/div/div[2]/div/div/div/div[2]/div/div/div[1]/sub-navigation/div/div[3]/div[1]/div[3]/div/span");
        waitForElement(projectedsalesSectionElement);
        Reporter.log("ProjectedSales Loaded Successfully!");
        return true;
    }


    public void goToStaffingGuidance() throws Exception {

        waitForElement(goToStaffingGuidanceTab);
        click(goToStaffingGuidanceTab);
        Reporter.log("StaffingGuidance Page Loading..!");

        WebElement Guidance = driver.findElement(By.xpath(".//*[@id='legion-app']/div/div[2]/div/div/div/div[2]/div/div/div[2]/control-panel/ui-view/div[2]/div[1]/div[2]"));
        Assert.assertEquals(true, Guidance.isDisplayed());
        Reporter.log("Guidance is Displayed");

        WebElement Workroles = driver.findElement(By.xpath(".//*[@id='legion-app']/div/div[2]/div/div/div/div[2]/div/div/div[2]/control-panel/ui-view/div[1]/div[3]/lgn-drop-down/div/button"));
        Assert.assertEquals(true, Workroles.isDisplayed());
        Reporter.log("Workroles is Displayed");

        //	 WebElement Analyze = driver.findElement(By.xpath(".//*[@id='legion-app']/div/div[2]/div/div/div/div[2]/div/div/div[2]/control-panel/ui-view/div[2]/div[4]/div/span"));
        WebElement Analyze = driver.findElement(By.cssSelector("[class='sch-control-button-label']"));
        Assert.assertEquals(true, Analyze.isDisplayed());
        Reporter.log("Analyze is Displayed");

    }

    public boolean isStaffingGuidance() throws Exception {

        //Reporter.log("isStaffingGuidance method Called..!");
        By staffingguidanceSectionElement = By.xpath(".//*[@id='legion-app']/div/div[2]/div/div/div/div[2]/div/div/div[1]/sub-navigation/div/div[3]/div[1]/div[3]/div/span");
        waitForElement(staffingguidanceSectionElement);
        Reporter.log("StaffingGuidance Loaded Successfully!");
        return true;
    }

    public void goToSchedule() throws Exception {

        waitForElement(goToScheduleTab);
        click(goToScheduleTab);
        Reporter.log("Schedule Page Loading..!");

        WebElement Refresh = driver.findElement(By.cssSelector("[class='sch-control-button-label']"));
        Assert.assertEquals(true, Refresh.isDisplayed());
        Reporter.log("Refresh is Displayed");

        WebElement Analyze = driver.findElement(By.cssSelector("[class='sch-control-button ng-scope']"));
        Assert.assertEquals(true, Analyze.isDisplayed());
        Reporter.log("Analyze is Displayed");

        WebElement Edit = driver.findElement(By.cssSelector("[class='sch-control-button']"));
        Assert.assertEquals(true, Edit.isDisplayed());
        Reporter.log("Edit is Displayed");

    }

    public boolean isSchedule() throws Exception {

        //	Reporter.log("isSchedule method Called..!");
        By scheduleSectionElement = By.xpath(".//*[@id='legion-app']/div/div[2]/div/div/div/div[2]/div/div/div[1]/sub-navigation/div/div[3]/div[1]/div[4]/div/span");
        waitForElement(scheduleSectionElement);
        Reporter.log("Schedule Loaded Successfully!");
        return true;
    }

    @Override
    //public boolean editAnywayPopupTest() throws Exception {
    //todo change method to edit published week schedule
    public boolean editWeeklySchedule(HashMap<String,String> propertyMap) throws Exception {
        //	driver.manage().window().maximize();
        waitForElement(goToScheduleButton);
        click(goToScheduleButton);
        // todo please declare all elements outside the page object method
        // todo add a property named setting.schedulingWindow and instead of keep going all the schedule table row, go only by setting.schedulingWindow
        waitForElement(By.className("schedule-table-row"));
        WebElement ScheduleTable = driver.findElement(By.cssSelector("[ng-if='!loading']"));
        List<WebElement> scheduleTableRowElement = ScheduleTable.findElements(By.className("schedule-table-row"));
        Map<String, String> WeeklyTableRowsDatesAndStatus = new LinkedHashMap<String, String>();
        By gotoNextWeekArrow = By.cssSelector("[ng-click='gotoNextWeek($event)']");
        for (WebElement ScheduleTableRow : scheduleTableRowElement) {
            WebElement ScheduleTableLeftColumn = ScheduleTableRow.findElement(By.cssSelector("[ng-class='nameClass()']"));
            String ScheduleWeekDate = ScheduleTableLeftColumn.findElement(By.className("left-banner")).getText();
            String ScheduleWeekStatus = ScheduleTableLeftColumn.findElement(By.cssSelector("[ng-if='!isLocationGroup()']")).getText();
            //todo filter by ScheduleWeekStatus. Only Finalized or Published should be added to map
            //todo return if there are no finalized or published week found
            WeeklyTableRowsDatesAndStatus.put(ScheduleWeekDate.replace("\n", ""), ScheduleWeekStatus.replace("\n", ""));
        }
        //driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        scheduleTableRowElement.get(0).click();
        By WeeklyScheduleEditButton = By.cssSelector("[ng-if=\"controlPanel.fns.getVisibility('EDIT')\"]");
        By WeeklyScheduleCancelEditingButton = By.cssSelector("[ng-if=\"controlPanel.fns.getVisibility('CANCEL')\"]");
        for (Map.Entry<String, String> tableRowIndex : WeeklyTableRowsDatesAndStatus.entrySet()) {
            waitForElement(By.className("sch-shift-transpose-grid"));
            click(WeeklyScheduleEditButton);
            if (driver.findElements(By.cssSelector("[label='okLabel()']")).size() != 0) {
                System.out.println("Edit Anyway PopUp Available: ");
                waitForElement(By.cssSelector("[label='cancelLabel()']"));
                click(By.cssSelector("[label='cancelLabel()']"));
                Reporter.log("Edit Anyway Model found for Week - '" + tableRowIndex.getKey() + "' & Status - '" + tableRowIndex.getValue() + "'");
            } else {
                Reporter.log("Edit Anyway Model not found for Week - '" + tableRowIndex.getKey() + "' & Status - '" + tableRowIndex.getValue() + "'");
                waitForElement(WeeklyScheduleCancelEditingButton);
                click(WeeklyScheduleCancelEditingButton);
            }
            WebElement element = driver.findElement(gotoNextWeekArrow);
            Actions action = new Actions(driver);
            action.moveToElement(element).click().perform();
        }
        return true;

    }


}
