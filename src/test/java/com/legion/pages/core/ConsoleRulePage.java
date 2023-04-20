package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.RulePage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleRulePage extends BasePage implements RulePage {
    public ConsoleRulePage() {
        PageFactory.initElements(getDriver(), this);
    }


    @FindBy(css = "[id=\"legion_cons_Schedule_Schedule_Rules_button\"]")
    private WebElement rulesButton;
    @Override
    public void clickRuleButton() throws Exception {
        if (isElementLoaded(rulesButton, 10)) {
            scrollToElement(rulesButton);
            clickTheElement(rulesButton);
        }else
            SimpleUtils.fail("Rules button is not found!", false);
    }


    @FindBy(css = "[ng-if=\"controlPanel.isViewRulesMode\"] div img")
    private List<WebElement> shiftPatternsAssignments;
    @FindBy(css = ".MuiCardContent-root+div")
    private WebElement editAssignmentButton;
    @FindBy(css = "div.react-modal_assign-rules")
    private WebElement createOngoingAssignmentModal;
    @FindBy(css = "button svg[fill=\"none\"]")
    private List<WebElement> removeAssignmentButtons;
    @FindBy(css = "[id=\"legion_cons_Schedule_Schedule_AssignRules_Create_button\"]")
    private WebElement saveButtonOnCreateOngoingAssignmentModal;
    @Override
    public void removeAllShiftPatternsAssignmentsOnScheduleRulePage() throws Exception {
        if (areListElementVisible(shiftPatternsAssignments, 10)) {
            while (shiftPatternsAssignments.size()>0){
                click(shiftPatternsAssignments.get(0));
                click(editAssignmentButton);
                if (isElementLoaded(createOngoingAssignmentModal, 5)){
                    removeAllAssignmentsOnCreateOngoingAssignmentModal();
                    clickSaveButtonOnCreateOngoingAssignmentModal();
                }else
                    SimpleUtils.fail("The create ongoing assignment modal fail to load! ", false);
            }
        }else
            SimpleUtils.report("There is no shift pattern assignments on schedule rule page!");
    }


    @Override
    public boolean checkIfThereAreAssignmentOnRulePage() throws Exception {
        boolean isAassignmentDisplay = false;
        if (areListElementVisible(shiftPatternsAssignments, 10)
                && shiftPatternsAssignments.size()>0) {
            isAassignmentDisplay = true;
            SimpleUtils.pass("There is "+shiftPatternsAssignments.size()+" assignments display on rule page");
        }else
            SimpleUtils.report("There is no shift pattern assignments on schedule rule page!");
        return isAassignmentDisplay;
    }

    @Override
    public void removeAllAssignmentsOnCreateOngoingAssignmentModal() throws Exception {
        if (areListElementVisible(removeAssignmentButtons, 10)) {
            while(removeAssignmentButtons.size()>0){
                click(removeAssignmentButtons.get(0));
                SimpleUtils.pass("Remove one assignment successfully! ");
            }
            SimpleUtils.pass("Remove all assignments successfully! ");

        }else
            SimpleUtils.report("There is no assignment on create ongoing assignment modal!");
    }

    @Override
    public void clickSaveButtonOnCreateOngoingAssignmentModal() throws Exception {
        if (isElementLoaded(saveButtonOnCreateOngoingAssignmentModal, 10)) {
            clickTheElement(saveButtonOnCreateOngoingAssignmentModal);
            SimpleUtils.pass("Click save button on create ongoing assignment modal successfully! ");
        }else
            SimpleUtils.fail("There is no save button on create ongoing assignment modal!", false);
    }

    @Override
    public List<String> getAllShiftPatternsAssignmentsOnScheduleRulePage() throws Exception {
        List<String> employeeNames = new ArrayList<>();
        if (areListElementVisible(shiftPatternsAssignments, 10)) {
            for (WebElement shiftPatternsAssignment : shiftPatternsAssignments) {
                String employeeName = shiftPatternsAssignment.getAttribute("alt").trim();
                employeeNames.add(employeeName);
                SimpleUtils.pass("Get employee:" + employeeName + " successfully! ");
            }
        }else
            SimpleUtils.report("There is no shift pattern assignments on schedule rule page!");
        return employeeNames;
    }

    @FindBy(css = "span.tm-info__breadcrumbs")
    private WebElement backButtonOnRulePage;
    @Override
    public void clickBackButton() throws Exception {
        if (isElementLoaded(backButtonOnRulePage, 10)) {
            clickTheElement(backButtonOnRulePage);
            SimpleUtils.pass("Click back button on rule page successfully! ");
        }else
            SimpleUtils.fail("There is no back button on rule page!", false);
    }
}
