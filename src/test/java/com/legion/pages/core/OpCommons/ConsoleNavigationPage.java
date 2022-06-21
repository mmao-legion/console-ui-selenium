package com.legion.pages.core.OpCommons;

import com.legion.pages.BasePage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleNavigationPage extends BasePage {
    public ConsoleNavigationPage() {
        PageFactory.initElements(getDriver(), this);
    }
    // Added by Sophia

    //LocationBar
    @FindBy(css = "div#id_upperfield-search")
    private WebElement upperFieldSearch;
    @FindBy(css = "lg-upperfield-search lg-search>input-field input")
    private WebElement searchBox;
    @FindBy(css = "lg-upperfield-search div.lg-search-icon.ng-scope")
    private WebElement searchIcon;
    @FindBy(css = "lg-search-options[search-hint='Search'] div.lg-search-options__scroller div.lg-search-options__option-wrapper")
    private WebElement searchResult;

    //Console navigation panel
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(1)")
    private WebElement dashBoard;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(2)")
    private WebElement team;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(3)")
    private WebElement schedule;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(4)")
    private WebElement timeSheet;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(5)")
    private WebElement compliance;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(6)")
    private WebElement report;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(7)")
    private WebElement inbox;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(9)")
    private WebElement admin;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(10)")
    private WebElement intergation;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(8)")
    private WebElement news;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(11)")
    private WebElement controls;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(8)")
    private WebElement controlsCustomer;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(10)")
    private WebElement logout;
    @FindBy(css = "div.console-navigation-item.ng-scope:nth-child(12)")
    private WebElement logoutAdmin;

    public void searchLocation(String locationName) {
        upperFieldSearch.click();
        searchBox.clear();
        searchBox.sendKeys(locationName);
        searchIcon.click();
        waitForSeconds(3);
        if (isElementDisplayed(searchResult)) {
            searchResult.click();
            waitForSeconds(5);
        } else {
            System.out.println("There is no location: " + locationName);
        }
    }

    public void navigateTo(String module) {
        WebElement element = null;
        if (module.equalsIgnoreCase("Dashboard")) {
            element = dashBoard;
        } else if (module.equalsIgnoreCase("Team")) {
            element = team;
        } else if (module.equalsIgnoreCase("Schedule")) {
            element = schedule;
        } else if (module.equalsIgnoreCase("Timesheet")) {
            element = timeSheet;
        } else if (module.equalsIgnoreCase("Compliance")) {
            element = compliance;
        } else if (module.equalsIgnoreCase("Report")) {
            element = report;
        } else if (module.equalsIgnoreCase("Inbox")) {
            element = inbox;
        } else if (module.equalsIgnoreCase("Report")) {
            element = report;
        } else if (module.equalsIgnoreCase("Admin")) {
            element = admin;
        } else if (module.equalsIgnoreCase("Integration")) {
            element = intergation;
        } else if (module.equalsIgnoreCase("News")) {
            element = news;
        } else if (module.equalsIgnoreCase("Controls")) {
            element = controls;
        }else if (module.equalsIgnoreCase("ControlsCustomer")) {
            element = controlsCustomer;
        }else if (module.equalsIgnoreCase("Logout")) {
            element = logout;
        } else if (module.equalsIgnoreCase("LogoutAdmin")) {
            element = logoutAdmin;
        }else {
            System.out.println("No such Module!");
        }
        element.click();
        waitForSeconds(5);
    }
    @FindBy (css = "div.no-left-right-padding.ng-scope")
    private WebElement dashBoardTab;
    @FindBy (css = "div.col-sm-11.pl-35")
    private WebElement reportTab;
    @FindBy (css = "div.col-xs-12.no-left-right-padding.ng-scope")
    private WebElement inboxTab;
    @FindBy (css = "lg-dashboard-card-wrapper.ng-scope")
    private WebElement consoleTab;

    public void verifyOtherTableIsNormal(){
        if(dashBoard.isEnabled()){
            click(dashBoard);
            SimpleUtils.pass("Click dashBoard table successfully");
            waitForSeconds(3);
            if(dashBoardTab.isDisplayed()){
                SimpleUtils.pass("DashBoard page is normal");
            }else
                SimpleUtils.fail("DashBoard page is not normal",false);
        }else
            SimpleUtils.fail("dashBoard table is disable",false);

        if(report.isEnabled()){
            click(report);
            SimpleUtils.pass("Click report table successfully");
            waitForSeconds(3);
            if(reportTab.isDisplayed()){
                SimpleUtils.pass("Report page is normal");
            }else
                SimpleUtils.fail("Report page is not normal",false);
        }else
            SimpleUtils.fail("Report table is disable",false);

        if(inbox.isEnabled()){
            click(inbox);
            SimpleUtils.pass("Click inbox table successfully");
            waitForSeconds(3);
            if(inboxTab.isDisplayed()){
                SimpleUtils.pass("Inbox page is normal");
            }else
                SimpleUtils.fail("Inbox page is not normal",false);
        }else
            SimpleUtils.fail("Inbox table is disable",false);

        if(controlsCustomer.isEnabled()){
            click(controlsCustomer);
            SimpleUtils.pass("Click controls table successfully");
            waitForSeconds(3);
            if(consoleTab.isDisplayed()){
                SimpleUtils.pass("Controls page is normal");
            }else
                SimpleUtils.fail("Controls page is not normal",false);
        }else
            SimpleUtils.fail("Controls table is disable",false);
    }

    public void clickTeamTab(){
        if(team.isEnabled()){
            click(team);
            SimpleUtils.pass("Click team table successfully");
        }else
            SimpleUtils.fail("Team table is disable",false);
    }

    public void clickScheduleTab(){
        if(schedule.isEnabled()){
            click(schedule);
            SimpleUtils.pass("Click schedule table successfully");
        }else
            SimpleUtils.fail("Schedule table is disable",false);
    }
    @FindBy (css = "div.sov.ng-scope")
    private WebElement scheduleTab;

    public void verifySchedulePageIsNormal(){
        waitForSeconds(3);
        if(scheduleTab.isDisplayed()){
            SimpleUtils.pass("Schedule table is normal");
        }else
            SimpleUtils.fail("Schedule table is not normal",false);
    }

    public void clickTimeSheetTab(){
        if(timeSheet.isEnabled()){
            click(timeSheet);
            SimpleUtils.pass("Click timesheet table successfully");
        }else
            SimpleUtils.fail("TimeSheet table is disable",false);
    }

    @FindBy(css ="div.table-view")
    private WebElement timeSheetTab;

    public void verifytimeSheetPageIsNormal(){
        waitForSeconds(3);
        if(timeSheetTab.isDisplayed()){
            SimpleUtils.pass("TimeSheet page is normal");
        }else
            SimpleUtils.fail("TimeSheet page is not normal",false);
    }

    @FindBy(css = "div.col-xs-12.roster-body")
    private WebElement teamTable;

    public void verifyTeamPageIsNormal(){
        waitForSeconds(3);
        if(teamTable.isDisplayed()){
            SimpleUtils.pass("Team page is normal");
        }else
            SimpleUtils.fail("Team page is not normal",false);
    }

    public void clickComplianceTab(){
        if(compliance.isEnabled()){
            click(compliance);
            SimpleUtils.pass("Click compliance table successfully");
        }else
            SimpleUtils.fail("Compliance table is disable",false);
    }

    @FindBy(css = "div.analytics-new-table.ng-scope")
    private WebElement complianceTable;
    public void verifyCompliancePageIsNormal(){
        waitForSeconds(3);
        if(complianceTable.isDisplayed()){
            SimpleUtils.pass("Compliance page is normal");
        }else
            SimpleUtils.fail("Compliance page is not normal",false);
    }

    @FindBy(css = "p.nodata-title.ng-binding")
    private WebElement noData;

    public void verifyPageEmpty(){
        if(noData.getAttribute("innerText").equals("No data to show at this level")){
            SimpleUtils.pass("Page is empty");
        }else
            SimpleUtils.fail("Page is not empty",false);
    }

    @FindBy(css = "div.analytics-new-table.ng-scope")
    private WebElement scheduleTabForDis;

    public void verifySchedulePageForDisIsNormal(){
        waitForSeconds(3);
        if(scheduleTabForDis.isDisplayed()){
            SimpleUtils.pass("Schedule table for distinct is normal");
        }else
            SimpleUtils.fail("Schedule table for distinct is not normal",false);
    }

    @FindBy(css = "div.card-carousel.row-fx")
    private WebElement timeSheetTabForDis;

    public void verifytimeSheetPageForDisIsNormal(){
        waitForSeconds(3);
        if(timeSheetTabForDis.isDisplayed()){
            SimpleUtils.pass("TimeSheet page for distinct is normal");
        }else
            SimpleUtils.fail("Schedule page for distinct is not normal",false);
    }
}
