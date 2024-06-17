package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.ScheduleAttestationPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleScheduleAttestationPage  extends BasePage implements ScheduleAttestationPage {
    public ConsoleScheduleAttestationPage() {
        PageFactory.initElements(getDriver(), this);
    }

    @FindBy(css = "[class=\"swap-modal modal-instance sch react-modal react-modal_create-shift ng-scope\"]")
    private WebElement scheduleChangeReasonModal;
    @Override
    public boolean isScheduleChangeReasonModalLoaded(){
        boolean isScheduleChangeReasonModalLoaded = false;
        if (isElementEnabled(scheduleChangeReasonModal, 15)){
            isScheduleChangeReasonModalLoaded = true;
            SimpleUtils.report("The Schedule Change Reason Modal is loaded! ");
        } else
            SimpleUtils.report("The Schedule Change Reason Modal is not loaded! ");
        return isScheduleChangeReasonModalLoaded;
    }

    @FindBy(xpath = "//div[contains(@class,'legion-ui-react')]//form/div[2]/div")
    private List<WebElement> scheduleChangeList;
    @Override
    public Map<String, String> getScheduleChangesByIndex(int index){
        Map<String, String> scheduleChangeInfo = new HashMap<>();;
        if (areListElementVisible(scheduleChangeList, 15) && scheduleChangeList.size()>0){
            WebElement scheduleChange = scheduleChangeList.get(index);
            String shiftDate = scheduleChange.findElement(By.xpath("./div[1]/div[1]")).getText();
            String teamMemberName = scheduleChange.findElement(By.xpath("./div[1]/div[2]/div/p")).getText();
            String changeInfo = scheduleChange.findElement(By.xpath("./div[1]/div[3]/p")).getText();
            String changeReason = scheduleChange.findElement(By.xpath("./div[1]/div[4]//div[contains(@class,'react-select__single-value')]")).getText();

            scheduleChangeInfo.put("shiftDate", shiftDate);
            scheduleChangeInfo.put("teamMemberName", teamMemberName);
            scheduleChangeInfo.put("changeInfo", changeInfo);
            scheduleChangeInfo.put("changeReason", changeReason);
        } else
            SimpleUtils.fail("The Schedule Change list is not loaded or empty! ", false);
        return scheduleChangeInfo;
    }

    @FindBy(css = "[button-id=\"legion_cons_Schedule_Schedule_Select_Reason_Save_btn\"]")
    private WebElement saveButtonOnScheduleChangeReasonModal;
    @Override
    public void clickSaveButtonOnScheduleChangeReasonModal(){
        if (isElementEnabled(saveButtonOnScheduleChangeReasonModal, 5)){
            clickTheElement(saveButtonOnScheduleChangeReasonModal);
            SimpleUtils.pass("Click Save button successfully! ");
        } else
            SimpleUtils.fail("Save button fail to load! ", false);
    }
}
