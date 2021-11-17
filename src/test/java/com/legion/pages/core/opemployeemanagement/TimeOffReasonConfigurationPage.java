package com.legion.pages.core.opemployeemanagement;

import com.legion.pages.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class TimeOffReasonConfigurationPage extends BasePage {
    public TimeOffReasonConfigurationPage() {
        PageFactory.initElements(getDriver(), this);
    }

    // Added by Sophia
    @FindBy(css = "div.lg-page-heading>div>a")
    private WebElement backButton;

    //bread crumbs
    @FindBy(css = "div.title-breadcrumbs>h1.title.ng-binding")
    private WebElement templateName;
    @FindBy(css = "div.title-breadcrumbs>h1.sub-title")
    private WebElement reasonName;

    //section label
    @FindBy(css = "form-section[form-title='Request Rules']  h2")
    private WebElement requestRulesSectionLabel;
    @FindBy(css = "form-section[form-title='Eligibility Rules']  h2")
    private WebElement eligibilityRulesSectionLabel;
    @FindBy(css = "form-section[form-title='Accrual Distribution']  h2")
    private WebElement accrualDistributionSectionLabel;

    //Time off rules list
    @FindBy(css = "form-section[form-title='Request Rules'] h3.lg-question-input__text.ng-binding")
    private List<WebElement> requestRuleLabels;
    @FindBy(css = "yes-no lg-button-group div.lg-button-group-first")
    private List<WebElement> yesToRules;
    @FindBy(css = "yes-no lg-button-group div.lg-button-group-last")
    private List<WebElement> noToRules;
    @FindBy(css = "input-field input")
    private List<WebElement> inputFields;

    //request rules
    //Employee can request ?
    @FindBy(css = "[question-title='Employee can request ?'] h3")
    private WebElement canRequestLabel;
    @FindBy(css = "[question-title='Employee can request ?'] yes-no div.lg-button-group-first")
    private WebElement yesToCanRequest;
    @FindBy(css = "[question-title='Employee can request ?'] yes-no div.lg-button-group-last")
    private WebElement noToCanRequest;
    //Employee can request partial day ?
    @FindBy(css = "[question-title='Employee can request partial day ?'] h3")
    private WebElement partialDayLabel;
    @FindBy(css = "[question-title='Employee can request partial day ?'] yes-no div.lg-button-group-first")
    private WebElement yesToPartialDay;
    @FindBy(css = "[question-title='Employee can request partial day ?'] yes-no div.lg-button-group-last")
    private WebElement noToPartialDay;
    //Manager can submit in timesheet ?
    @FindBy(css = "[question-title='Manager can submit in timesheet ?'] h3")
    private WebElement canSubmitInTimesheetLabel;
    @FindBy(css = "[question-title='Manager can submit in timesheet ?'] yes-no div.lg-button-group-first")
    private WebElement yesToSubmitInTimesheet;
    @FindBy(css = "[question-title='Manager can submit in timesheet ?'] yes-no div.lg-button-group-last")
    private WebElement noToSubmitInTimesheet;
    //Weekly limits(hours)
    @FindBy(css = "[question-title='Weekly limits(hours)'] h3")
    private WebElement weeklyLimitsLabel;
    @FindBy(css = "[question-title='Weekly limits(hours)'] input")
    private WebElement weeklyLimitsInput;
    @FindBy(css = "[question-title='Weekly limits(hours)'] div.input-faked.ng-binding")
    private WebElement weeklyLimitsInputNum;
    //Days request must be made in advance
    @FindBy(css = "[question-title='Days request must be made in advance'] h3")
    private WebElement requestMadeInAdvanceLabel;
    @FindBy(css = "[question-title='Days request must be made in advance'] input")
    private WebElement requestMadeInAdvanceInput;
    @FindBy(css = "[question-title='Days request must be made in advance'] div.input-faked.ng-binding")
    private WebElement requestMadeInAdvanceInputNum;
    //Configure all day time off default
    @FindBy(css = "[question-title='Configure all day time off default'] h3")
    private WebElement allDayDefaultLabel;
    @FindBy(css = "[question-title='Configure all day time off default'] input")
    private WebElement allDayDefaultInput;
    @FindBy(css = "[question-title='Configure all day time off default'] div.input-faked.ng-binding")
    private WebElement allDayDefaultInputNum;
    //Days an employee can request at one time
    @FindBy(css = "[question-title='Days an employee can request at one time'] h3")
    private WebElement daysRequestAtOneTimeLabel;
    @FindBy(css = "[question-title='Days an employee can request at one time'] input")
    private WebElement daysRequestAtOneTimeInput;
    @FindBy(css = "[question-title='Days an employee can request at one time'] div.input-faked.ng-binding")
    private WebElement daysRequestAtOneTimeInputNum;

    //Auto reject time off which exceed accrued hours ?
    @FindBy(css = "[question-title='Auto reject time off which exceed accrued hours ?'] h3")
    private WebElement autoRejectLabel;
    @FindBy(css = "[question-title='Auto reject time off which exceed accrued hours ?'] yes-no div.lg-button-group-first")
    private WebElement yesToAutoReject;
    @FindBy(css = "[question-title='Auto reject time off which exceed accrued hours ?'] yes-no div.lg-button-group-last")
    private WebElement noToAutoReject;
    //Allow Paid Time Off to compute to overtime ?
    @FindBy(css = "[question-title='Allow Paid Time Off to compute to overtime ?'] h3")
    private WebElement toComputeOverTimeLabel;
    @FindBy(css = "[question-title='Allow Paid Time Off to compute to overtime ?'] yes-no div.lg-button-group-first")
    private WebElement yesToComputeOverTime;
    @FindBy(css = "[question-title='Allow Paid Time Off to compute to overtime ?'] yes-no div.lg-button-group-last")
    private WebElement noToComputeOverTime;
    //Does this time off reason track Accruals ?
    @FindBy(css = "[question-title='Does this time off reason track Accruals ?'] h3")
    private WebElement trackAccrualLabel;
    @FindBy(css = "[question-title='Does this time off reason track Accruals ?'] yes-no div.lg-button-group-first")
    private WebElement yesToTrackAccrual;
    @FindBy(css = "[question-title='Does this time off reason track Accruals ?'] yes-no div.lg-button-group-last")
    private WebElement noToTrackAccrual;
    //Max hours in advance of what you earn
    @FindBy(css = "[question-title='Max hours in advance of what you earn'] h3")
    private WebElement maxHrsInAdvanceLabel;
    @FindBy(css = "[question-title='Max hours in advance of what you earn'] input")
    private WebElement maxHrsInAdvanceInput;
    @FindBy(css = "[question-title='Max hours in advance of what you earn'] div.input-faked.ng-binding")
    private WebElement maxHrsInAdvanceInputNum;

    //Enforce Yearly Limits
    @FindBy(css = "[question-title='Enforce Yearly Limits'] h3")
    private WebElement enforceYearlyLimitsLabel;
    @FindBy(css = "[question-title='Enforce Yearly Limits'] yes-no div.lg-button-group-first")
    private WebElement yesToEnforceYearlyLimits;
    @FindBy(css = "[question-title='Enforce Yearly Limits'] yes-no div.lg-button-group-last")
    private WebElement noToEnforceYearlyLimits;
    //Probation Period
    @FindBy(css = "[question-title='Probation Period'] h3")
    private WebElement probationLabel;
    @FindBy(css = "[question-title='Probation Period'] input")
    private WebElement probationInput;
    @FindBy(css = "[question-title='Probation Period'] div.input-faked.ng-binding")
    private WebElement probationInputNum;
    @FindBy(css = "[question-title='Probation Period'] select")//Days Months
    private WebElement probationSelect;
    //Annual Use Limit
    @FindBy(css = "[question-title='Annual Use Limit'] h3")
    private WebElement annualUseLimitLabel;
    @FindBy(css = "[question-title='Annual Use Limit'] input")
    private WebElement annualUseLimitInput;
    @FindBy(css = "[question-title='Annual Use Limit'] div.input-faked.ng-binding")
    private WebElement annualUseLimitInputNum;


    //Accrual Distribution
    @FindBy(css = "question-input[question-title='Accrual Start Date'] select")
    private List<WebElement> accrualStartDate;
    @FindBy(css = "question-input[question-title='Accrual End Date'] select")
    private List<WebElement> accrualEndDate;
    @FindBy(css = "question-input[question-title='Reinstatement Months'] input")
    private WebElement reinstatementMonth;
    @FindBy(css = "question-input[question-title='Distribution Method'] select")
    private WebElement distributionMethods;
    //Distribution type
    @FindBy(css = "question-input[question-title='Distribution type'] select")
    private WebElement distributionType;
    //payable hour
    @FindBy(css = "question-input[question-title='Payable hour types included in calculation'] lg-button[label='Configure']>button")
    private WebElement payableConfig;
    @FindBy(css = "modal[modal-title='Include Hour Types'] select")
    private WebElement hoursTypeSelect;

    //service lever
    @FindBy(css = "table.lg-table.service-level tr:nth-child(2) input-field input")
    private List<WebElement> firstServiceLeverInput;
    @FindBy(css = "table.lg-table.service-level tr:nth-child(3) input-field input")
    private List<WebElement> secondServiceLeverInput;
    @FindBy(css = "table.lg-table.service-level tr")
    private List<WebElement> serviceLeverNum; //size-1


    //service lever
    @FindBy(css = "table.lg-table.service-level td.add-button>lg-button[label='+ Add']>button")
    private WebElement addButtonForServiceLever;
    @FindBy(css = "table.lg-table.service-level td>div>span.remove")
    private WebElement removeButtonForServiceLever;

    //submit
    @FindBy(css = "lg-button[label='Cancel']>button")
    private WebElement cancelButton;
    @FindBy(css = "lg-button[label='Save']>button")
    private WebElement saveButton;


    //back
    public void back() {
        backButton.click();
    }

    //bread crumbs
    public String getTemplateName() {
        return templateName.getText();
    }

    public String getTimeOffReasonName() {
        return reasonName.getText();
    }

    // section label
    public String getRequestRulesSectionLabel() {
        return requestRulesSectionLabel.getText();
    }

    public String getAccrualDistributionSectionLabel() {
        return accrualDistributionSectionLabel.getText();
    }

    public Boolean isAccrualDistributionLabelDisplayed() {
        try {
            scrollToElement(accrualDistributionSectionLabel);
            return accrualDistributionSectionLabel.isDisplayed();
        } catch (Exception NoSuchElementException) {
            return false;
        }
    }

    //Time off request rules method
    public ArrayList<String> getRequestRuleLabels() {
        ArrayList<String> labels = new ArrayList<String>();
        for (WebElement e : requestRuleLabels) {
            labels.add(e.getText());
        }
        return labels;
    }

    public void setTimeOffRequestRuleAs(String ruleLabel, Boolean yesOrNo) {
        WebElement yesButton = null;
        WebElement noButton = null;
        if (canRequestLabel.getText().equals(ruleLabel)) {
            yesButton = yesToCanRequest;
            noButton = noToCanRequest;
        } else if (partialDayLabel.getText().equals(ruleLabel)) {
            yesButton = yesToPartialDay;
            noButton = noToPartialDay;
        } else if (canSubmitInTimesheetLabel.getText().equals(ruleLabel)) {
            yesButton = yesToSubmitInTimesheet;
            noButton = noToSubmitInTimesheet;
        } else if (autoRejectLabel.getText().equals(ruleLabel)) {
            yesButton = yesToAutoReject;
            noButton = noToAutoReject;
        } else if (toComputeOverTimeLabel.getText().equals(ruleLabel)) {
            yesButton = yesToComputeOverTime;
            noButton = noToComputeOverTime;
        } else if (trackAccrualLabel.getText().equals(ruleLabel)) {
            yesButton = yesToTrackAccrual;
            noButton = noToTrackAccrual;
        } else if (enforceYearlyLimitsLabel.getText().equals(ruleLabel)) {
            yesButton = yesToEnforceYearlyLimits;
            noButton = noToEnforceYearlyLimits;
        } else {
            throw new IllegalStateException("Unexpected value: " + ruleLabel);
        }
        if (yesOrNo) {
            scrollToElement(yesButton);
            yesButton.click();
        } else {
            scrollToElement(noButton);
            noButton.click();
        }
    }

    public Boolean isTimeOffRuleToggleTurnOn(String ruleLabel) {
        WebElement yesButton = null;
        String toggleOn = "rgba(244, 246, 248, 1)";
        if (canRequestLabel.getText().equals(ruleLabel)) {
            yesButton = yesToCanRequest;
        } else if (partialDayLabel.getText().equals(ruleLabel)) {
            yesButton = yesToPartialDay;
        } else if (canSubmitInTimesheetLabel.getText().equals(ruleLabel)) {
            yesButton = yesToSubmitInTimesheet;
        } else if (autoRejectLabel.getText().equals(ruleLabel)) {
            yesButton = yesToAutoReject;
        } else if (toComputeOverTimeLabel.getText().equals(ruleLabel)) {
            yesButton = yesToComputeOverTime;
        } else if (trackAccrualLabel.getText().equals(ruleLabel)) {
            yesButton = yesToTrackAccrual;
        } else if (enforceYearlyLimitsLabel.getText().equals(ruleLabel)) {
            yesButton = yesToEnforceYearlyLimits;
        } else {
            throw new IllegalStateException("Unexpected value: " + ruleLabel);
        }
        scrollToElement(yesButton);
        String bgColorForYes = yesButton.getCssValue("background-color");
        return bgColorForYes.equals(toggleOn);
    }

    public void setValueForTimeOffRequestRules(String ruleLabel, String num) {
        WebElement element = null;
        if (weeklyLimitsLabel.getText().equals(ruleLabel)) {
            element = weeklyLimitsInput;
        } else if (requestMadeInAdvanceLabel.getText().equals(ruleLabel)) {
            element = requestMadeInAdvanceInput;
        } else if (allDayDefaultLabel.getText().equals(ruleLabel)) {
            element = allDayDefaultInput;
        } else if (daysRequestAtOneTimeLabel.getText().equals(ruleLabel)) {
            element = daysRequestAtOneTimeInput;
        } else if (maxHrsInAdvanceLabel.getText().equals(ruleLabel)) {
            element = maxHrsInAdvanceInput;
        } else if (probationLabel.getText().equals(ruleLabel)) {
            element = probationInput;
        } else if (annualUseLimitLabel.getText().equals(ruleLabel)) {
            element = annualUseLimitInput;
        } else {
            throw new IllegalStateException("Unexpected value: " + ruleLabel);
        }
        scrollToElement(element);
        element.clear();
        element.sendKeys(num);
    }

    public String getNumSetForTimeOffRequestRules(String ruleLabel) {
        WebElement element = null;
        if (weeklyLimitsLabel.getText().equals(ruleLabel)) {
            element = weeklyLimitsInputNum;
        } else if (requestMadeInAdvanceLabel.getText().equals(ruleLabel)) {
            element = requestMadeInAdvanceInputNum;
        } else if (allDayDefaultLabel.getText().equals(ruleLabel)) {
            element = allDayDefaultInputNum;
        } else if (daysRequestAtOneTimeLabel.getText().equals(ruleLabel)) {
            element = daysRequestAtOneTimeInputNum;
        } else if (maxHrsInAdvanceLabel.getText().equals(ruleLabel)) {
            element = maxHrsInAdvanceInputNum;
        } else if (probationLabel.getText().equals(ruleLabel)) {
            element = probationInputNum;
        } else if (annualUseLimitLabel.getText().equals(ruleLabel)) {
            element = annualUseLimitInputNum;
        } else {
            throw new IllegalStateException("Unexpected value: " + ruleLabel);
        }
        scrollToElement(element);

        if (!isElementDisplayed(element)) {
            removeHidden(element);
        }
        return element.getText();
    }


    public ArrayList<String> getProbationPeriodUnitOptions() {
        ArrayList<String> opt = new ArrayList<String>();
        Select sel = new Select(probationSelect);
        sel.getOptions().forEach(element -> {
            opt.add(element.getText());
        });
        return opt;
    }

    public void setProbationUnitAsMonths() {
        Select sel = new Select(probationSelect);
        sel.selectByIndex(1);
    }

    public void setProbationUnitAs(String method) {//Days, Months, Hours Worked
        Select sel = new Select(probationSelect);
        if (method.equalsIgnoreCase("Days")) {
            sel.selectByVisibleText("Days");
        } else if (method.equalsIgnoreCase("Months")) {
            sel.selectByVisibleText("Months");
        } else if (method.equalsIgnoreCase("Hours Worked")) {
            sel.selectByVisibleText("Hours Worked");
        }
    }

    public boolean isTimeOffConfigurationReadOnly() {
        if (weeklyLimitsInput.isEnabled()) {
            return false;
        } else {
            return true;
        }
    }

    //Accrual part
    public ArrayList<String> getAccrualStartOptions() {
        return getSelectOptions(accrualStartDate.get(0));
    }

    public ArrayList<String> getAccrualEndOptions() {
        return getSelectOptions(accrualEndDate.get(0));
    }

    public void setReinstatementMonth(String reinMonth) {
        reinstatementMonth.clear();
        reinstatementMonth.sendKeys(reinMonth);
    }

    public ArrayList<String> getDistributionOptions() {
        return getSelectOptions(distributionMethods);
    }

    public void setDistributionMethod(String distributionMethod) {
        scrollToBottom();
        Select select = new Select(distributionMethods);
        select.selectByVisibleText(distributionMethod);
    }

    public void addServiceLever() {
        scrollToElement(addButtonForServiceLever);
        addButtonForServiceLever.click();
        firstServiceLeverInput.get(1).clear();
        firstServiceLeverInput.get(1).sendKeys("120");
    }

    public void setMaxAvailableHours(String maxAvailableHours){
        firstServiceLeverInput.get(3).clear();
        firstServiceLeverInput.get(3).sendKeys(maxAvailableHours);
    }

    public void addSecondServiceLever() {
        scrollToElement(addButtonForServiceLever);
        addButtonForServiceLever.click();
        secondServiceLeverInput.get(0).clear();
        secondServiceLeverInput.get(0).sendKeys("5");
        secondServiceLeverInput.get(1).clear();
        secondServiceLeverInput.get(1).sendKeys("240");
    }

    public int getServiceLeverNum() {
        return serviceLeverNum.size() - 1;
    }

    public void removeServiceLever() {
        scrollToElement(removeButtonForServiceLever);
        removeButtonForServiceLever.click();
    }

    public ArrayList<String> getSelectOptions(WebElement element) {
        ArrayList<String> options = new ArrayList<String>();
        scrollToBottom();
        Select select = new Select(element);
        select.getOptions().forEach((e) -> {
            options.add(e.getText());
        });
        return options;
    }

    public void saveTimeOffConfiguration(boolean tureOrFalse) {
        scrollToBottom();
        if (tureOrFalse) {
            saveButton.click();
        } else {
            cancelButton.click();
        }
        waitForSeconds(5);
    }

}
