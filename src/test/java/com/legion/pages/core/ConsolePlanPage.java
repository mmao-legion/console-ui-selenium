package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.InboxPage;
import com.legion.pages.PlanPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.DeleteSession;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.legion.utils.MyThreadLocal.*;

public class ConsolePlanPage extends BasePage implements PlanPage {

    public ConsolePlanPage() {
        PageFactory.initElements(getDriver(), this);
    }

    //Added by Lizzy
    final static String consolePlanItemText = "Plan";


    @FindBy(css = ".console-navigation-item-label.Plan")
    private WebElement goToPlanButton;
    @FindBy(css = "lg-button[label='Create Plan']")
    private WebElement createPlanBtn;
    @FindBy(css = ".modal-dialog.modal-lgn-lg")
    private WebElement createPlanModalDialog;
    @FindBy(css = "input-field[label='Budget plan name']>ng-form>input")
    private WebElement createPlanName;
    @FindBy(css = "input-field[label='Description']>ng-form>input")
    private WebElement createPlanDescription;
    @FindBy(css = "ranged-calendar__day ng-binding ng-scope real-day")
    private List<WebElement> calendarDates;
    @FindBy(css = "ranged-calendar__day ng-binding ng-scope real-day in-range is-today")
    private WebElement currentDay;
    @FindBy(css = "calendar-nav-right")
    private WebElement endDateNextMonthArrow;
    @FindBy(css = "day-selected ng-binding ng-scope")
    private List<WebElement> daysSelected;
    @FindBy(css = "lg-button[label='OK']")
    private WebElement planCreateOKBTN;
    @FindBy(css = "h1.title.ng-binding")
    private WebElement scenarioPlanNameInDeatil;
    @FindBy(css = "input[placeholder=\"Search by labor budget name\"]")
    private WebElement planSearchInputField;
    @FindBy(css = "div[ng-repeat-start=\"plan in filteredPlanLists\"]")
    private List<WebElement> planSearchedResults;
    @FindBy(css = "lg-button[label=\"Create\"]")
    private List<WebElement> createScPlanButton;
    @FindBy(css = "lg-button[label=\"Save\"]>button")
    private WebElement planSaveButton;
    @FindBy(css = "div[ng-repeat=\"s in plan.scenaries\"]")
    private List<WebElement> scenarioPlanRecords;
    @FindBy(css = "span[ng-class=\"getPlanStatusClass(scenario)\"]")
    private WebElement planStatusInDetail;
    @FindBy(css = "lg-button[label=\"Generate demand forecast\"]")
    private WebElement generateForecastButton;
    @FindBy(css = "lg-button[label=\"Run Budget\"]")
    private WebElement runBudgetButton;
    @FindBy(css = "modal[modal-title=\"Generate demand forecast\"]>div.lg-modal")
    private WebElement forecastRunDialog;
    @FindBy(css = "lg-button[label=\"Generate\"]")
    private WebElement forecastGenerateBTNOnDialog;
    @FindBy(css = "a[href*='DemandForecastSample']")
    private WebElement forecastDownloadCSVLink;
    @FindBy(css = "a[href*='BudgetSample']")
    private WebElement budgetDownloadCSVLink;
    @FindBy(css = "lg-button[label=\"Send For Review\"]")
    private WebElement sendForReviewButton;
    @FindBy(css = "modal[modal-title='Send For Review']>div")
    private WebElement sendForReviewDialog;
    @FindBy(css = "lg-button[label=\"Send\"]")
    private WebElement sendButtonOnDialog;


    @Override
    public void clickOnPlanConsoleMenuItem() throws Exception {
        if (isElementLoaded(goToPlanButton, 10)) {
            clickTheElement(goToPlanButton);
            waitForSeconds(3);
            SimpleUtils.pass("'Plan' Console Menu loaded successfully!");
        } else
            SimpleUtils.fail("'Plan' Console Menu failed to load!", false);
    }

    private int getSpecificDayIndex(WebElement specificDay) {
        int index = 0;
        if (areListElementVisible(calendarDates, 10)) {
            for (int i = 0; i < calendarDates.size(); i++) {
                String day = calendarDates.get(i).getText();
                if (day.equals(specificDay.getText())) {
                    index = i;
                    SimpleUtils.pass("Get current day's index successfully");
                }
            }
        } else {
            SimpleUtils.fail("Days on calendar failed to load!", true);
        }
        return index;
    }


    private void planDurationSetting(int fromDay, int toDay) throws Exception {
        selectDate(fromDay);
        selectDate(toDay);
//        HashMap<String, String> timeOffDate = getTimeOffDuration(fromDay, toDay);
//        return timeOffDate;
    }

    @Override
    public void clickOnSavePlanOKBtn() throws Exception {
        waitForSeconds(3);
        if (planCreateOKBTN.isEnabled()) {
            click(planCreateOKBTN);
        } else
            SimpleUtils.fail("Create plan dialog: Unable to save plan.", false);

    }


    @Override
    public void createANewPlan(String planName) throws Exception {
        if (isElementLoaded(createPlanBtn, 10)) {
            clickTheElement(createPlanBtn);
            if (isElementLoaded(createPlanModalDialog, 10)) {
                SimpleUtils.pass("Create plan dialog loaded successfully!");
                //input plan name
                createPlanName.sendKeys(planName);
                //input plan description
                createPlanDescription.sendKeys("The description was created by automation ");
                //Select duration for the budget to take place
                planDurationSetting(0, 90);
                //click the ok
                clickOnSavePlanOKBtn();
                waitForSeconds(3);
            } else
                SimpleUtils.fail("Create plan dialog not loaded on page!", false);

        } else
            SimpleUtils.fail("Create Plan button not loaded on page!", false);

    }

    @Override
    public void verifyScenarioPlanAutoCreated(String scenarioName) throws Exception {
        waitForSeconds(2);
        if (isElementLoaded(scenarioPlanNameInDeatil, 5)) {
            SimpleUtils.pass("Scenario plan automatically created successfully!");
            //assert the scenario plane name
            if (scenarioPlanNameInDeatil.getText().equals(scenarioName))
                SimpleUtils.pass("Scenario plan created successfully with right name!");
            //scroll to bottom to save the scenario plan
            scrollToBottom();
            clickTheElement(planSaveButton);
            waitForSeconds(2);
        } else
            SimpleUtils.fail("Scenario plan not created automatically!", false);

    }

    public void searchAPlan(String keywords) throws Exception {
        if (isElementLoaded(planSearchInputField, 3)) {
            SimpleUtils.pass("Plan search input field loaded successfully!");
            waitForSeconds(20);
            planSearchInputField.sendKeys(keywords);
            planSearchInputField.sendKeys(Keys.ENTER);
            //check result
            if (areListElementVisible(planSearchedResults)) {
                SimpleUtils.pass("Searched plan with results successfully!");
                //get the numbers
                int recordNumbers = planSearchedResults.size();
                //get the create  button on the page
                if (areListElementVisible(createScPlanButton)) {
                    SimpleUtils.pass("Searched plan with create scenario button successfully!");
                    int createBtN = createScPlanButton.size();
                    if (recordNumbers == createBtN)
                        SimpleUtils.pass("Every HQ parent plan has create button to create a scenario plan");
                    else
                        SimpleUtils.fail("Some HQ parent plan has not create button to create a scenario plan", false);
                }
            } else
                SimpleUtils.fail("Search plan retruns no results!", false);
        } else
            SimpleUtils.fail("Plan search input field not loaded!", false);


    }

    @Override
    public void takeOperationToPlan(String parentPlanName, String scenarioPlanName, String status) throws Exception {
        searchAPlan(parentPlanName);
        //click the planName to expand
        WebElement planName = planSearchedResults.get(0).findElement(By.cssSelector("div[ng-click=\"expandScenario(plan)\"]"));
        if (isElementLoaded(planName, 5)) {
            SimpleUtils.pass("plan name loaded successfully!");
            clickTheElement(planName);
            //find the scenario plan to view
            if (scenarioPlanRecords.size() > 0) {
                SimpleUtils.pass("Scenario plan loaded successfully");
                for (int index = 0; index < scenarioPlanRecords.size(); index++) {
                    if (scenarioPlanRecords.get(index).findElement(By.cssSelector("div.lg-scenario-table-improved__grid-column--left.ng-binding")).getText().equals(scenarioPlanName)){
                        WebElement viewEle=scenarioPlanRecords.get(index).findElement(By.cssSelector("lg-button[label=\"View\"] button"));
                        if(isElementLoaded(viewEle,5)) {
                            SimpleUtils.pass("Plan view button loaded successfully");
                            clickTheElement(viewEle);
                            waitForSeconds(2);
                        }
                        else
                            SimpleUtils.fail("Plan view button not loaded",false);
                    }
                    //check if enter the scenario plan detail
                    if (isElementLoaded(planStatusInDetail, 5) && scenarioPlanNameInDeatil.getText().equals(scenarioPlanName)) {
                        SimpleUtils.pass("Scenario plan loaded successfully with status name!");
                        //check the status and operation
                        String currentStatus = planStatusInDetail.getText().trim();
                        if (status.equalsIgnoreCase("Not Started") && isElementLoaded(generateForecastButton)) {
                            //need to generate forecast and budget
                            clickTheElement(generateForecastButton);
                            if (isElementLoaded(forecastRunDialog, 5)) {
                                SimpleUtils.pass("Generate forecast dialog pops up successfully!");
                                //click the generate button on dialog
                                clickTheElement(forecastGenerateBTNOnDialog);
                                {
                                    //wait until the download csv for generate forecast button is displayed
                                    waitUntilElementIsInVisible(forecastDownloadCSVLink);
                                    if (scenarioPlanNameInDeatil.getText().equals("In Progress"))
                                        SimpleUtils.pass("Scenario plan changed to in progress from not started!");
                                    else
                                        SimpleUtils.fail("Scenario plan not changed to in progress status", false);
                                }
                            }
                        } else if (status.equalsIgnoreCase("In Progress") && isElementLoaded(runBudgetButton)) {
                            //click the run budget button
                            clickTheElement(runBudgetButton);
                            waitUntilElementIsInVisible(budgetDownloadCSVLink);
                            //check the status changed to completed
                            if (scenarioPlanNameInDeatil.getText().equals("Completed"))
                                SimpleUtils.pass("Scenario plan changed to completed from in progress!");
                            else
                                SimpleUtils.fail("Scenario plan not changed to Scenario plan changed to completed from in progress", false);
                            //check the send for review button is enabled
                            if (isElementEnabled(sendForReviewButton)) {
                                SimpleUtils.pass("The send for review button is enabled!");
                                clickTheElement(sendForReviewButton);
                                if (isElementLoaded(sendButtonOnDialog)) {
                                    SimpleUtils.pass("Send for review dialog pops up successfully!");
                                    clickTheElement(sendButtonOnDialog);
                                } else
                                    SimpleUtils.fail("Send for review dialog not pop up", false);


                            } else
                                SimpleUtils.fail("The send for review button is not enabled", false);
                        } else
                            SimpleUtils.fail("run budget button not loaded!", false);
                    } else
                        SimpleUtils.fail("Scenario plan detail page not loaded!", false);
                }
            } else
                SimpleUtils.fail("No scenario plan loaded!", false);

        } else
            SimpleUtils.fail("Arrow icon to expand a parent plan not loaded!", false);
    }

    public void ddd(){

    }

}
