package com.legion.pages.core;

import com.legion.pages.*;
import com.legion.pages.SmartTemplatePage;
import com.legion.pages.core.schedule.ConsoleEditShiftPage;
import com.legion.pages.core.schedule.ConsoleNewShiftPage;
import com.legion.pages.core.schedule.ConsoleShiftOperatePage;
import com.legion.tests.core.ScheduleTestKendraScott2;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleSmartTemplatePage extends BasePage implements SmartTemplatePage {
    public ConsoleSmartTemplatePage() {
        PageFactory.initElements(getDriver(), this);
    }
    @FindBy(css = "[label=\"Edit Template\"] button")
    private WebElement editSmartTemplateButton;
    @Override
    public void clickOnEditSmartTemplateBtn() throws Exception {
        if (isElementEnabled(editSmartTemplateButton, 5)){
            clickTheElement(editSmartTemplateButton);
        } else
            SimpleUtils.fail("The edit smart template button fail to load! ", false);

    }

    @FindBy(css = "button[id=\"legion_cons_Schedule_Schedule_Edit_button\"]")
    private WebElement editButton;
    @Override
    public void clickOnEditBtn(){
        if (isElementEnabled(editButton, 5)){
            clickTheElement(editButton);
        } else
            SimpleUtils.fail("The edit smart template button fail to load! ", false);
    }



    @FindBy(css = "[label=\"Reset\"] button")
    private WebElement resetButton;

    @FindBy(css = "button.lgn-action-button-success")
    private WebElement okButtonOnConfirmResetModal;
    @Override
    public void clickOnResetBtn(){
        if (isElementEnabled(resetButton, 5)){
            clickTheElement(resetButton);
            if (isElementEnabled(okButtonOnConfirmResetModal, 5)){
                clickTheElement(okButtonOnConfirmResetModal);
            }
            waitForSeconds(3);
        } else
            SimpleUtils.fail("The reset smart template button fail to load! ", false);
    }

    @Override
    public List<String> createShiftsWithWorkRoleTransition(List<HashMap<String, String>> segments, String shiftName, int shiftPerDay, List<Integer> workDays, String assignment,
                                                           String shiftNotes, String tmName, boolean recurringShift) throws Exception {
        List<String> selectedTMs = new ArrayList<>();
        NewShiftPage newShiftPage = new ConsoleNewShiftPage();
        ShiftOperatePage shiftOperatePage = new ConsoleShiftOperatePage();

        newShiftPage.clickOnDayViewAddNewShiftButton();
        Thread.sleep(5000);
//        SimpleUtils.assertOnFail("New create shift page is not display! ",
//                newShiftPage.checkIfNewCreateShiftPageDisplay(), false);
//        // Select work role
//        newShiftPage.selectWorkRole(workRole);
//        // Select location
//        if (location != null && !location.isEmpty()) {
//            newShiftPage.selectChildLocInCreateShiftWindow(location);
//        }
//        // Set end time
//        if (endTime != null && !endTime.isEmpty()) {
//            newShiftPage.moveSliderAtCertainPoint(endTime, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
//        }
//        // Set start time
//        if (startTime != null && !startTime.isEmpty()) {
//            newShiftPage.moveSliderAtCertainPoint(startTime, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
//        }
        //Set segments
        newShiftPage.addShiftSegments(segments, true);
        // Set shift name
        if (shiftName != null && !shiftName.isEmpty()) {
            newShiftPage.setShiftNameOnNewCreateShiftPage(shiftName);
        }
        // Set recurring shift
        checkOrUnCheckRecurringShift(recurringShift);
        // Set shift per day
        newShiftPage.setShiftPerDayOnNewCreateShiftPage(shiftPerDay);
        // Select work day
        if (workDays.size() == 1) {
            newShiftPage.clearAllSelectedDays();
            newShiftPage.selectMultipleOrSpecificWorkDay(workDays.get(0), true);
        } else if (workDays.size() > 1) {
            newShiftPage.clearAllSelectedDays();
            for (int i : workDays) {
                newShiftPage.selectMultipleOrSpecificWorkDay(workDays.get(i), true);
            }
        }
        // Select the assignment
        newShiftPage.clickRadioBtnStaffingOption(assignment);
        // Set shift notes
        if (shiftNotes != null && !shiftNotes.isEmpty()) {
            newShiftPage.setShiftNotesOnNewCreateShiftPage(shiftNotes);
        }
        newShiftPage.clickOnCreateOrNextBtn();
        if (assignment.equals(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue())
                || assignment.equals(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue()) ) {
            if (tmName != null && !tmName.isEmpty()) {
                newShiftPage.searchTeamMemberByName(tmName);
            } else {
                shiftOperatePage.switchSearchTMAndRecommendedTMsTab();
                for (int i = 0; i < shiftPerDay; i++) {
                    selectedTMs.add(newShiftPage.selectTeamMembers());
                }
            }
            newShiftPage.clickOnCreateOrNextBtn();
        }
        return selectedTMs;
    }

    @Override
    public void checkOrUnCheckRecurringShift(boolean isCheck){
        if(isCheck){
            if (MyThreadLocal.getDriver().findElement(By.xpath("//div[@id=\"create-new-shift-react\"]/div/div/form/div/div/div[4]/div[2]/label/span"))
                    .getAttribute("class").contains("checked")){
                SimpleUtils.pass("Recurring shift already been checked! ");
            } else{
                click(MyThreadLocal.getDriver().findElement(By.xpath("//div[@id=\"create-new-shift-react\"]/div/div/form/div/div/div[4]/div[2]/label/span/input")));
                waitForSeconds(2);
                if (MyThreadLocal.getDriver().findElement(By.xpath("//div[@id=\"create-new-shift-react\"]/div/div/form/div/div/div[4]/div[2]/label/span"))
                        .getAttribute("class").contains("checked")){
                    SimpleUtils.pass("Recurring shift been checked successfully! ");
                }
            }
        } else {
            if (MyThreadLocal.getDriver().findElement(By.xpath("//div[@id=\"create-new-shift-react\"]/div/div/form/div/div/div[4]/div[2]/label/span"))
                    .getAttribute("class").contains("checked")){
                click(MyThreadLocal.getDriver().findElement(By.xpath("//div[@id=\"create-new-shift-react\"]/div/div/form/div/div/div[4]/div[2]/label/span/input")));
                waitForSeconds(2);
                if (!MyThreadLocal.getDriver().findElement(By.xpath("//div[@id=\"create-new-shift-react\"]/div/div/form/div/div/div[4]/div[2]/label/span"))
                        .getAttribute("class").contains("checked")){
                    SimpleUtils.pass("Recurring shift been unchecked successfully! ");
                }
            } else{
                SimpleUtils.pass("Recurring shift already been unchecked! ");
            }
        }
    }

    @FindBy(css = "a[class=\"ng-binding\"]")
    private WebElement backButton;
    @Override
    public void clickOnBackBtn(){
        if (isElementEnabled(backButton, 5)){
            clickTheElement(backButton);
        } else
            SimpleUtils.fail("The back button fail to load! ", false);
    }
}
