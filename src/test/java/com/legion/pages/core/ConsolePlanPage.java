package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.PlanPage;
import com.legion.utils.SimpleUtils;
import cucumber.api.java.ro.Si;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.DeleteSession;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
    @FindBy(css = "input-field[label=\"Budget plan name\"] lg-input-error span")
    private WebElement createPlanNameError;
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
    @FindBy(css = "lg-button[label='OK'] button")
    private WebElement planCreateOKBTN;
    @FindBy(css = "lg-button[label='Cancel'] button")
    private WebElement PlanCreateCancelBTN;
    @FindBy(css = "div.scenario-details h1.title")
    private WebElement scenarioPlanNameInDeatil;
    @FindBy(css = "input[placeholder=\"Search by labor budget name\"]")
    private WebElement planSearchInputField;
    @FindBy(css = "div[ng-repeat-start=\"plan in filteredPlanLists\"]")
    private List<WebElement> planSearchedResults;
    @FindBy(css = "div[ng-repeat=\"s in plan.scenaries\"]")
    private List<WebElement> scenarioPlans;
    @FindBy(css = "lg-button[label=\"Create\"]")
    private List<WebElement> createScPlanButton;
    @FindBy(css = "lg-button[label=\"Save\"]>button")
    private WebElement planSaveButton;
    @FindBy(css = "span[ng-class=\"getPlanStatusClass(scenario)\"]")
    private WebElement planStatusInDetail;
    @FindBy(css = "lg-button[label=\"Generate demand forecast\"] button")
    private WebElement generateForecastButton;
    @FindBy(css = "lg-button[label=\"Run Budget\"] button")
    private WebElement runBudgetButton;
    @FindBy(css = "modal[modal-title=\"Generate demand forecast\"]>div.lg-modal")
    private WebElement forecastRunDialog;
    @FindBy(css = "lg-button[label=\"Generate\"]")
    private WebElement forecastGenerateBTNOnDialog;
    @FindBy(css = "a[href*='DemandForecastSample']")
    private WebElement forecastDownloadCSVLink;
    @FindBy(css = "a[href*='BudgetSample']")
    private WebElement budgetDownloadCSVLink;
    @FindBy(css = "modal[modal-title=\"Run budget\"]>div.lg-modal")
    private WebElement budgetRunDialog;
    @FindBy(css = "lg-button[label=\"Run\"]")
    private WebElement budgetRunBTNOnDialog;
    @FindBy(css = "lg-button[label=\"Send For Review\"]")
    private WebElement sendForReviewButton;
    @FindBy(css = "modal[modal-title='Send For Review']>div")
    private WebElement sendForReviewDialog;
    @FindBy(css = "lg-button[label=\"Send\"]")
    private WebElement sendButtonOnDialog;
    @FindBy(css = "input-field[value=\"scenario.name\"] input")
    private WebElement scenarioPlanNameInput;
    @FindBy(css = "input-field[value=\"scenario.name\"] .input-faked")
    private WebElement scenarioPlanDefaultPlanName;
    @FindBy(css = "input-field[label=\"Description\"] textarea")
    private WebElement scenarioPlanDescriptionInput;
    @FindBy(css = "lg-button[ng-if=\"$ctrl.secondLabel\"] button")
    private WebElement scenarioPlanSave;
    @FindBy(css = "input-field[label=\"Recipients\"] input")
    private WebElement scenarioEmailInput;
    @FindBy(css = "div.lg-toast")
    private WebElement errorToast;
    @FindBy(css = ".lg-pagination__pages.ng-binding")
    private WebElement pageNumberText;
    @FindBy(css = "modal[modal-title=\"Edit Plan\"]")
    private WebElement editParentPlanDialog;
    @FindBy(css = "input-field[value=\"plan.name\"] input")
    private WebElement editParentPlanName;
    @FindBy(css = "p.location-display span")
    private WebElement locationInCreatePlanDialog;
    @FindBy(css = "div.ranged-calendar__day.real-day.can-not-select")
    private List<WebElement> planDurationDisabledDays;
    @FindBy(css = "div.ranged-calendar__day.real-day.is-today")
    private WebElement planDurationToday;
    @FindBy(css = "div[ng-if=\"invalidRangeMessage\"]")
    private WebElement planDurationErrorMsg;
    @FindBy(css = "b[ng-if=\"daySelected\"]")
    private List<WebElement> planDurationStartAndEnd;
    @FindBy(css = "lg-close.dismiss")
    private WebElement planDialogCloseIcon;
    @FindBy(css = "lg-button[label=\"Archive\"]")
    private WebElement planDialogArchiveBTN;
    @FindBy(css = "lg-button[label=\"Copy\"]")
    private WebElement planDialogCopyBTN;
    @FindBy(css = "modal[modal-title=\"Archive labor budget scenario\"]")
    private WebElement scenarioPlanArchivedialog;
    @FindBy(css = "modal[modal-title=\"Create Labor Budget Scenario\"]")
    private WebElement scenarioPlanCopydialog;
    @FindBy(css = "sub-content-box[box-title=\"Email notification on status update\"]")
    private WebElement scenarioPlanDetailEmail;
    @FindBy(css = "a[ng-click=\"$ctrl.back()\"]")
    private WebElement scenarioPlanBackLink;
    @FindBy(css = "span.status-highlight")
    private WebElement scenarioPlanStatusInDetail;
    @FindBy(css = "lg-button[label=\"Re-run\"]")
    private List<WebElement> scenarioPlanReRunBTNs;



    @Override
    public boolean verifyPlanConsoleTabShowing() throws Exception{
        boolean flag = false;
        if(isElementLoaded(goToPlanButton, 10)){
            SimpleUtils.pass("User can see Plan tab");
            flag = true;
        }else {
            SimpleUtils.report("Plan tab is not shown");
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean verifyCreatePlanButtonShowing() throws Exception {
        boolean flag = false;
        if (isElementLoaded(createPlanBtn, 10)) {
            flag = true;
            SimpleUtils.pass("User can see create plan button");
        }else {
            flag = false;
            SimpleUtils.report("User can't see create plan button");
        }
        return flag;
    }


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
                planDurationSetting(7, 14);
                //click the ok
                clickTheElement(planCreateOKBTN);
                if (isElementLoaded(errorToast, 5)) {
                    if (errorToast.getText().contains("Error! plan name already exists:")) {
                        SimpleUtils.report("Create a plan with a duplicated name is not allowed!");
                    }
                    //click the cancel
                    clickTheElement(PlanCreateCancelBTN);
                    waitForSeconds(3);
                } else if (isElementLoaded(createPlanBtn) && searchAPlan(planName))
                    SimpleUtils.pass("Create a parent plan successfully!");
            } else
                SimpleUtils.fail("Create plan dialog not loaded on page!", false);

        } else
            SimpleUtils.fail("Create Plan button not loaded on page!", false);

    }

    @Override
    public void verifyScenarioPlanAutoCreated(String planName, String scenarioName) throws Exception {
        //search the plan
        if (searchAPlan(planName)) {
            clickTheElement(planSearchedResults.get(0).findElement(By.cssSelector("div:nth-child(1)")));
            waitForSeconds(2);
            if (areListElementVisible(createScPlanButton)) {
                //click the create button
                clickTheElement(createScPlanButton.get(0));
                waitForSeconds(2);
                //input scenario plan name and description
                scenarioPlanNameInput.sendKeys(scenarioName);
                scenarioPlanDescriptionInput.sendKeys("This is the description for " + scenarioName);
                //click save
                clickTheElement(scenarioPlanCopydialog.findElement(By.cssSelector(" lg-button[label=\"Create\"]")));
                if (isElementLoaded(errorToast, 5)) {
                    if (errorToast.getText().contains("Error!")) {
                        SimpleUtils.report("Create a scenario plan failed with errors!");
                    }
                    //click the cancel
                    clickTheElement(PlanCreateCancelBTN);
                    waitForSeconds(3);
                }
                //check if auto navigated to the scenario plan detail page
                else if (isElementLoaded(scenarioPlanNameInDeatil, 5)) {
                    SimpleUtils.pass("Scenario plan detail page loaded successfully!");
                    //assert the scenario plane name
                    if (scenarioPlanNameInDeatil.getText().equals(scenarioName))
                        SimpleUtils.pass("Scenario plan created successfully with right name!");
                    //click back lin to list
                    clickTheElement(scenarioPlanBackLink);
                    //check page auto back to plan list
                    if (isElementLoaded(planSearchInputField, 3))

                        SimpleUtils.pass("Page back to plan list after saved the scenario plan!");
                }
            } else
                SimpleUtils.fail("No create button show to allow scenario plan!", false);

        }
    }

    @Override
    public boolean verifyCreatePlanButtonAvail(String upperfieldName) throws Exception {
        boolean createBtn = false;
        if (isElementLoaded(createPlanBtn, 10)) {
            createBtn = true;
        }
        return createBtn;
    }

    @Override
    public boolean searchAPlan(String keywords) throws Exception {
        Boolean isExist = false;
        if (isElementLoaded(planSearchInputField, 3)) {
            SimpleUtils.pass("Plan search input field loaded successfully!");
            waitForSeconds(5);
            planSearchInputField.clear();
            planSearchInputField.sendKeys(keywords);
            planSearchInputField.sendKeys(Keys.ENTER);
            if (areListElementVisible(planSearchedResults)) {
                isExist = true;
                SimpleUtils.pass("Searched plan with result successfully!");

            }
        }
        return isExist;
    }


    @Override
    public void verifyCreatePlanLandingPage(String planName) throws Exception {
        //check the data in list
        if (areListElementVisible(planSearchedResults)) {
            SimpleUtils.report("There are data show in plan landing page!");
            //get the count
            int dataInAPage = planSearchedResults.size();
            //get the total page
            int maxPageNum = Integer.valueOf(pageNumberText.getText().trim().split("of")[1].trim());
            if (maxPageNum > 1 && dataInAPage != 10)
                SimpleUtils.pass("There are more than 1 pages of data and 1o records in a page!");
            else
                SimpleUtils.report("The total data count in the current page is:" + dataInAPage);
            //check the latest updated data will show as the first one
            if (searchAPlan(planName)) {
                //make changes to plan name and check the position
                planSearchedResults.get(0).findElement(By.cssSelector("lg-button[label=\"Edit\"]")).click();
                waitForSeconds(1);
                //check if edit dialog pops up
                if (isElementLoaded(editParentPlanDialog, 5)) {
                    SimpleUtils.pass("Edit parent plan dialog pops up successfully");
                    editParentPlanName.clear();
                    editParentPlanName.sendKeys(planName + "-Updated");
                    //click ok
                    planCreateOKBTN.click();
                    waitForSeconds(3);
                    //check edit successfully
                    if (searchAPlan(planName + "-Updated")) {
                        SimpleUtils.pass("Parent plan edit successfully");
                        //check the first record of data is the one edited
                        planSearchInputField.clear();
                        planSearchInputField.sendKeys(Keys.ENTER);
                        //get the title of the first data
                        String planTitle = planSearchedResults.get(0).findElement(By.cssSelector(".lg-scenario-table-improved__plan-name")).getText().trim();
                        if (planTitle.equals(planName + "Updated"))
                            SimpleUtils.pass("The latest updated data show as the first of record successfully!");
                        //recover the data
                        if (searchAPlan(planName + "-Updated")) {
//                            planSearchedResults.get(0).click();
//                            planSearchedResults.get(0).findElement(By.cssSelector("div:nth-child(1)")).click();
                            planSearchedResults.get(0).findElement(By.cssSelector("lg-button[label=\"Edit\"]")).click();
                            waitForSeconds(2);
                            editParentPlanName.clear();
                            editParentPlanName.sendKeys(planName);
                            planCreateOKBTN.click();
                        }
                    }
                }
            }
            //check the latest updated data will show as the first one
            if (searchAPlan(planName)) {
                planSearchedResults.get(0).click();
                //check expanded plan and view link
                if (areListElementVisible(scenarioPlans)) {
                    SimpleUtils.pass("Plan with scenario plans loaded successfully after it was expanded!");
                    //check the view link
                    if (isElementLoaded(scenarioPlans.get(0).findElement(By.cssSelector(" lg-button[label=\"View\"]")), 3)) {
                        SimpleUtils.pass("View link is existing under a scenario plan!");
                        //get the scenario plan title in list
                        String scplanNameInList = scenarioPlans.get(0).findElement(By.cssSelector("div:nth-child(1)")).getText().trim();
                        //click the link
                        clickTheElement(scenarioPlans.get(0).findElement(By.cssSelector(" lg-button[label=\"View\"]")));
                        waitForSeconds(2);
                        //check page navigate to the detail
                        //get the title of the scenario plan
                        String planTitleInDetails = scenarioPlanNameInDeatil.getText().trim();
                        if (planTitleInDetails.equals(scplanNameInList))
                            SimpleUtils.pass("View link navigate to the plan detail successfully");
                        else
                            SimpleUtils.fail("View link navigate to the plan detail failed", false);
                    } else
                        SimpleUtils.fail("The view link is not show under a scneario plan", false);


                } else
                    SimpleUtils.report("The parent plan has no scenario plan till now!");
            }
        }
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
            if (scenarioPlans.size() > 0) {
                SimpleUtils.pass("Scenario plan loaded successfully");
                for (int index = 0; index < scenarioPlans.size(); index++) {
                    if (scenarioPlans.get(index).findElement(By.cssSelector("div.lg-scenario-table-improved__grid-column--left.ng-binding")).getText().equals(scenarioPlanName)) {
                        WebElement viewEle = scenarioPlans.get(index).findElement(By.cssSelector("lg-button[label=\"View\"] button"));
                        if (isElementLoaded(viewEle, 5)) {
                            SimpleUtils.pass("Plan view button loaded successfully");
                            clickTheElement(viewEle);
                            waitForSeconds(2);
                        } else
                            SimpleUtils.fail("Plan view button not loaded", false);
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

    @Override
    public String getCurrentLocationsForCreatePlan() throws Exception {
        String currentLoc = null;
        if (isElementLoaded(createPlanBtn)) {
            clickTheElement(createPlanBtn);
            //check the create plan dialog
            if (isElementLoaded(createPlanModalDialog)) {
                SimpleUtils.pass("Create plan dialog pops up successfully");
                if (isElementLoaded(locationInCreatePlanDialog)) {
                    currentLoc = locationInCreatePlanDialog.getText().trim();
                    //click cancel to dismiss the dialog
                    clickTheElement(PlanCreateCancelBTN);
                }
            }
        }
        return currentLoc;
    }

    public void verifyCreatePlanDialog(String planName) throws Exception {
        String startDay1 = null;
        String startDay2 = null;
        String endDay1 = null;
        String endDay2 = null;
        if (isElementLoaded(createPlanBtn)) {
            SimpleUtils.pass("Create plan button show in the plan landing page successfully");
            clickTheElement(createPlanBtn);
            //check the create plan dialog
            if (isElementLoaded(createPlanModalDialog)) {
                SimpleUtils.pass("Create plan dialog pops up successfully");
                //check the duration-the day before today can not be selected
                String today = planDurationToday.getText().trim();
                for (WebElement disableDdays : planDurationDisabledDays) {
                    if (Integer.parseInt(disableDdays.getText().trim()) < Integer.parseInt(today))
                        SimpleUtils.pass("The days that before today are disabled to select");
                    else
                        SimpleUtils.fail("The days before today are enabled!", false);
                }
                //check the max length of the duration is not more than 12 months
                planDurationSetting(1, 400);
                if (areListElementVisible(planDurationStartAndEnd)) {
                    //get the start day value
                    startDay1 = planDurationStartAndEnd.get(0).getText();
                    //get the end day value
                    endDay1 = planDurationStartAndEnd.get(1).getText();
                }
                //check the error message
                if (isElementLoaded(planDurationErrorMsg) && planDurationErrorMsg.getText().contains("Duration can not exceed 12 months"))
                    SimpleUtils.pass("The duration for the plan is not more than 12 months");
                else
                    SimpleUtils.fail("The duration for the plan can set to more than 12 months", false);
                if (isElementLoaded(planDialogCloseIcon)) {
                    SimpleUtils.pass("The close icon is show in plan create dialog");
                    //click it to dismiss the dialog
                    clickTheElement(planDialogCloseIcon);
                    waitForSeconds(2);
                }
                //click the create plan button again
                if (isElementLoaded(createPlanBtn)) {
                    SimpleUtils.pass("Page navigate to plan list");
                    clickTheElement(createPlanBtn);
                    //select the duration
                    planDurationSetting(7, 14);
                    if (areListElementVisible(planDurationStartAndEnd)) {
                        //get the start day value
                        startDay1 = planDurationStartAndEnd.get(0).getText();
                        //get the end day value
                        endDay1 = planDurationStartAndEnd.get(1).getText();
                    }
                    //assert the start and end day is not a fixed day, it can be change according to selection
                    if (!startDay1.equals(startDay2))
                        SimpleUtils.pass("The from day value can be changed according to the start day selection");
                    else
                        SimpleUtils.fail("The from day value can not be changed according to the start day selection", false);
                    if (!endDay1.equals(endDay2))
                        SimpleUtils.pass("The end day value can be changed according to the end day selection");
                    else
                        SimpleUtils.fail("The end day value can not be changed according to the end day selection", false);
                    //check the plan name is required
                    createPlanName.clear();
                    //get the ok button disabled status
                    String okStatus = planCreateOKBTN.getAttribute("disabled");
                    if (okStatus.equals("true"))
                        SimpleUtils.pass("The ok button is disabled if plan name is not filled");
                    else
                        SimpleUtils.fail("The ok button enabled when plan name is empty!", false);
                    //check the error info for empty plan name
                    createPlanName.sendKeys("name");
                    createPlanName.clear();
                    if (isElementLoaded(createPlanNameError)) {
                        //get the error message
                        String planNameRequired = createPlanNameError.getText().trim();
                        if (planNameRequired.equals("Budget plan name is required")) ;
                        SimpleUtils.pass("The plan name is not allowed as empty");
                    }
                    //fill the plan name with some special chars
                    createPlanName.clear();
                    createPlanName.sendKeys(planName + "@#$%&*");
                    planCreateOKBTN.click();
                    if (isElementLoaded(errorToast, 5)) {
                        if (errorToast.getText().contains("plan name is empty or have special character")) {
                            SimpleUtils.pass("The plan name is not allowed as empty or with special chars");
                        }
                    }
                    //create the plan as name with "-" and "_"
                    createPlanName.clear();
                    createPlanName.sendKeys(planName + "-n1_m1");
                    planCreateOKBTN.click();
                    waitForSeconds(2);
                    //check plan create successfully.
                    if (isElementLoaded(scenarioPlanNameInDeatil)) {
                        String planTitleInDetails = scenarioPlanNameInDeatil.getText().trim();
                        if (planTitleInDetails.equals(planName + "-n1_m1 scenario 1"))
                            SimpleUtils.pass("Plan created successfully and page navigated to the scenario plan detail successfully!");
                    }
                }

            }
        }
        else
            SimpleUtils.fail("Create plan button not show in the plan landing page",false);
    }

    private void archiveAPlan(String planName,String scplanName) throws Exception{
        //go to the scenario plan detail
        goToScenarioPlanDetail(planName,scplanName);
        if(isElementLoaded(planDialogArchiveBTN)){
            SimpleUtils.pass("Archive button is showed in scenario plan detail page successfully");
            clickTheElement(planDialogArchiveBTN);
            waitForSeconds(2);
            if(isElementLoaded(scenarioPlanArchivedialog)){
                SimpleUtils.pass("Archive dialog pops up successfully!");
                //click the archive
                scenarioPlanArchivedialog.findElement(By.cssSelector("lg-button[label=\"Archive\"] ")).click();
                waitForSeconds(2);
                //check the scenario plan removed from the list
                Boolean archiveRes=goToScenarioPlanDetail(planName,scplanName);
                if(!archiveRes)
                    SimpleUtils.pass("Archive a scenario plan successfully");
            }
            else
                SimpleUtils.fail("Archive dialog not pops up!",false);
        }
        else
            SimpleUtils.fail("No arhive button show in the plan detail page",false);

    }

    private boolean goToScenarioPlanDetail(String planName,String scplanName) throws Exception{
        Boolean scplanExist=false;
        //check the latest updated data will show as the first one
        if (searchAPlan(planName)) {
            if (!areListElementVisible(scenarioPlans, 5))
                clickTheElement(planSearchedResults.get(0).findElement(By.cssSelector("div:nth-child(1)")));
            waitForSeconds(2);
            if (areListElementVisible(scenarioPlans)) {
                SimpleUtils.pass("Plan with scenario plans loaded successfully after it was expanded!");
                for (WebElement scplan : scenarioPlans) {
                    if (scplan.findElement(By.cssSelector(" div:nth-child(1)")).getText().trim().equals(scplanName)) {
                        SimpleUtils.pass("Find the scenario plan successfully!");
                        scplanExist = true;
                        SimpleUtils.pass("Find the tested scenario plan");
                        //get the scenario plan title in list
                        String scplanNameInList = scplan.findElement(By.cssSelector("div:nth-child(1)")).getText().trim();
                        //click the view to enter its detail
                        if (isElementLoaded(scplan.findElement(By.cssSelector(" lg-button[label=\"View\"]")))) {
                            SimpleUtils.pass("View link is supported to scenario plan");
                            //click the view to enter detail
                            scplan.findElement(By.cssSelector(" lg-button[label=\"View\"]")).click();
                            waitForSeconds(2);
                            if (isElementLoaded(scenarioPlanNameInDeatil) && isElementLoaded(scenarioPlanDetailEmail)) {
                                //get the title of the scenario plan
                                String planTitleInDetails = scenarioPlanNameInDeatil.getText().trim();
                                if (planTitleInDetails.equals(scplanNameInList))
                                    SimpleUtils.pass("View link navigate to the plan detail successfully");
                                else
                                    SimpleUtils.fail("View link navigate to the plan detail failed", false);
                            }
                        }
                        break;
                    }
                }
            }
                else
                SimpleUtils.fail("No scenario plan for the parent plan", false);
        }

        return scplanExist;
    }

    @Override
    public void verifyCreatePlanDetailUICheck(String planName, String scplan, String copiedScName) throws Exception{
        //check if the scenario plan existed
        if(goToScenarioPlanDetail(planName,scplan))
            archiveAPlan(planName,scplan);
        //try to create a scenario plan
        verifyScenarioPlanAutoCreated(planName,scplan);
        //go to the scenario plan detail
        goToScenarioPlanDetail(planName,scplan);
        //check the copy button
        if(isElementLoaded(planDialogCopyBTN)){
            SimpleUtils.pass("Copy button is showed in scenario plan detail page successfully");
            clickTheElement(planDialogCopyBTN);
            if(isElementLoaded(scenarioPlanCopydialog)){
                SimpleUtils.pass("Copy dialog pops up successfully!");
                //get the default plan name
                String defaultCopyPlanName=scenarioPlanDefaultPlanName.getText().trim();
                if(defaultCopyPlanName.equals("Copy Of "+scplan))
                    SimpleUtils.pass("The default scenario plan name is correct");
                else
                    SimpleUtils.report("The default scenario plan name is incorrect!");
                //input scenario name
                scenarioPlanNameInput.clear();
                scenarioPlanNameInput.sendKeys(copiedScName);
                //click the copy button
                scenarioPlanCopydialog.findElement(By.cssSelector(" lg-button[label=\"Create\"] ")).click();
                waitForSeconds(2);
                //Check the current navigated plan name is the new copied plan
                if(isElementLoaded(scenarioPlanNameInDeatil)&&isElementLoaded(scenarioPlanDetailEmail)) {
                    //get the title of the scenario plan
                    String planTitleInDetails = scenarioPlanNameInDeatil.getText().trim();
                    if (planTitleInDetails.equals(copiedScName)) {
                        SimpleUtils.pass("Copy to generated a new scenario plan successfully");
                        //input email
                        scenarioEmailInput.clear();
                        scenarioEmailInput.sendKeys("CCAutoTest1@legion.co,CCAutoTest2@legion.co");
                        //click save to back to plan list page
                        clickTheElement(planSaveButton);
                    }
                    else
                        SimpleUtils.fail("Copy to generated a new scenario plan failed", false);
                            }
            }
            else
                SimpleUtils.fail("Copy dialog not pops up!",false);
        }
        else
            SimpleUtils.fail("No Copy button show in the plan detail page",false);
        //check the archive button to archive the copied plan
        archiveAPlan(planName,copiedScName);
        //archive the based scenario plan
        //check the status change--create a new scenario plan
        goToScenarioPlanDetail(planName,scplan);
        //check the back link in detail page
        clickTheElement(scenarioPlanBackLink);
        //check page will back to plan list
        if (isElementLoaded(planSearchInputField, 3))
            SimpleUtils.pass("Page back to plan list after click back lin at the scenario plan!");
        //change the plan from not started to in progress
        goToScenarioPlanDetail(planName,scplan);
        if(isElementLoaded(scenarioPlanStatusInDetail)&&scenarioPlanStatusInDetail.getText().contains("Not Started")){
            SimpleUtils.pass("The scenario plan status is not started!");
            //check the run budget status
            System.out.println(isElementLoaded(runBudgetButton));
            if(isElementLoaded(runBudgetButton)&&runBudgetButton.getAttribute("disabled").equals("true"))
                SimpleUtils.pass("The run budget button is disabled when the scenario plan is not started!");
            else
                SimpleUtils.report("The run budget button is enabled when the scenario plan is not started!");
            //click generate forecast to check the status change to in progress
            if(isElementLoaded(generateForecastButton))
                clickTheElement(generateForecastButton);
            if(isElementLoaded(forecastRunDialog)&&isElementLoaded(forecastGenerateBTNOnDialog)){
                //click the generate button to run the forecast
                clickTheElement(forecastGenerateBTNOnDialog);
                waitForSeconds(2);
                //get the status again
                if(scenarioPlanStatusInDetail.getText().equals("In Progress"))
                    SimpleUtils.pass("Begin to generate forecast will push the plan status from not started to in progress!");
                //wait some seconds till the forecast job finished
                if(areListElementVisible(scenarioPlanReRunBTNs,300)){
                    SimpleUtils.pass("The forecast job run to finished!");
                    System.out.println("enabeld budget is:"+runBudgetButton.getAttribute("disabled"));
                    //check the budget button is enabled
                    if(runBudgetButton.getAttribute("disabled")==null){
                        SimpleUtils.pass("The run budget button is enabled after the forecast job finished");
                        //click the run budget to run budget
                        clickTheElement(runBudgetButton);
                        if(isElementLoaded(budgetRunDialog)&&isElementLoaded(budgetRunBTNOnDialog)) {
                            clickTheElement(budgetRunBTNOnDialog);
                            waitUntilElementIsVisible(scenarioPlanReRunBTNs.get(1));
                        }

                    }
                    else
                        SimpleUtils.fail("The run budget button is still disabeld after the forecast job finished",false);

                }



            }
        }





    }



}


