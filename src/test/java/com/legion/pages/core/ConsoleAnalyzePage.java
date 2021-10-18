package com.legion.pages.core;

import com.legion.pages.AnalyzePage;
import com.legion.pages.BasePage;
import com.legion.pages.ScheduleMainPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleAnalyzePage extends BasePage implements AnalyzePage {
    public ConsoleAnalyzePage() {
        PageFactory.initElements(getDriver(), this);
    }

    @FindBy(xpath = "//*[@class=\"version-label-container\"]/div")
    private List<WebElement> scheduleHistoryListInAnalyzePopUp;
    @FindBy(css = "lg-close.dismiss")
    private WebElement scheduleAnalyzePopupCloseButtonInKS2;
    @FindBy(className = "sch-schedule-analyze-dismiss")
    private WebElement scheduleAnalyzePopupCloseButton;
    @FindBy(xpath = "//div[contains(text(),'Details')]")
    private WebElement versionDetailsInAnalyzePopUp;
    @FindBy(className = "sch-schedule-analyze-content")
    private WebElement scheduleAnalyzePopup;

    @Override
    public void verifyAnalyzeBtnFunctionAndScheduleHistoryScroll() throws Exception {
        ScheduleMainPage scheduleMainPage = new ConsoleScheduleMainPage();
        scheduleMainPage.clickOnScheduleAnalyzeButton();
        for (WebElement e:scheduleHistoryListInAnalyzePopUp
        ) {
            if(verifyScrollBarWorkingInAnalyzePopUP(e)){
                SimpleUtils.report("Staffing Guidance Schedule History-Scrollbar is working correctly version x details");

            }else {
                SimpleUtils.fail("Staffing Guidance Schedule History-Scrollbar is not working correctly version x details",true);
            }
        }
        if (isElementLoaded(scheduleAnalyzePopupCloseButtonInKS2,10)){
            click(scheduleAnalyzePopupCloseButtonInKS2);
            SimpleUtils.pass("close button is working");
        } else if (isElementLoaded(scheduleAnalyzePopupCloseButton)){
            click(scheduleAnalyzePopupCloseButton);
            SimpleUtils.pass("close button is working");
        } else{
            SimpleUtils.fail("No close button on schedule analyse popup",true);
        }

    }

    public boolean verifyScrollBarWorkingInAnalyzePopUP(WebElement element) throws Exception {
        if (areListElementVisible(scheduleHistoryListInAnalyzePopUp,10)&scheduleHistoryListInAnalyzePopUp.size()>4) {
            SimpleUtils.report("versions are more enough and there is a scroll bar to check details");
            scrollToElement(element);
            click(element);
            String versionNubScrollToText = versionDetailsInAnalyzePopUp.getText().trim().split(" ")[1];
            if (versionNubScrollToText.equals(element.getText().trim().split(" ")[1])) {
                SimpleUtils.pass("scroll bar can work normally");
                return  true;
            }else {
                SimpleUtils.fail("scroll bar can not  work normally",true);
            }
        }else if(scheduleHistoryListInAnalyzePopUp.size()<=4){

            SimpleUtils.report("there are some versions,but not scroll bar");
            click(element);
            String versionNubScrollToText = versionDetailsInAnalyzePopUp.getText().trim().split(" ")[1];
            if (versionNubScrollToText.equals(element.getText().trim().split(" ")[1])) {
                SimpleUtils.pass("schedule version work well");
                return  true;
            }else {
                SimpleUtils.fail("schedule version doesn't work well",true);
            }

        }
        return  false;
    }

    public void closeStaffingGuidanceAnalyzePopup() throws Exception {
        if (isStaffingGuidanceAnalyzePopupAppear()) {
            click(scheduleAnalyzePopupCloseButton);
        }
    }

    public Boolean isStaffingGuidanceAnalyzePopupAppear() throws Exception {
        if (isElementLoaded(scheduleAnalyzePopup)) {
            return true;
        }
        return false;
    }

    @FindBy(css = "lg-button[label=\"Analyze\"]")
    private WebElement analyzeBtn;
    @FindBy(css="div[ng-click=\"selectedTab = 'history'\"]")
    private WebElement schedulelHistoryTab;
    @Override
    public void clickOnAnalyzeBtn() throws Exception {
        if (isElementLoaded(analyzeBtn,15)){
            click(analyzeBtn);
            SimpleUtils.pass("Clicked analyze button!");
            if (isElementLoaded(schedulelHistoryTab,15)){
                click(schedulelHistoryTab);
                SimpleUtils.pass("Clicked schedulelHistoryTab!");
            } else {
                SimpleUtils.fail("There is no schedulelHistoryTab!", false);
            }
        } else {
            SimpleUtils.fail("There is no Analyze button!", false);
        }
    }



    @FindBy(css = ".sch-schedule-analyze__grey tr")
    private List<WebElement> scheduleVersionInfo;

    @Override
    public void verifyScheduleVersion(String version) throws Exception {
        if (areListElementVisible(scheduleVersionInfo,15) && areListElementVisible(scheduleVersionInfo.get(scheduleVersionInfo.size()-1).findElements(By.tagName("td")),15)){
            String versionText = scheduleVersionInfo.get(scheduleVersionInfo.size()-1).findElements(By.tagName("td")).get(0).getText().split("\n")[0];
            if ("".equals(versionText)){
                versionText = scheduleVersionInfo.get(scheduleVersionInfo.size()-1).findElements(By.tagName("td")).get(1).getText().split("\n")[0];
            }
            if(version.equalsIgnoreCase(versionText)){
                SimpleUtils.pass("version info is correct!");
            }else {
                SimpleUtils.fail("There is schedule HistoryTab!", false);
            }
        } else {
            SimpleUtils.fail("There is no schedule version info!", false);
        }
    }

    @FindBy(css = "lg-close.dismiss")
    private WebElement closeAnalyzeBtn;
    @Override
    public void closeAnalyzeWindow() throws Exception {
        if (isElementLoaded(closeAnalyzeBtn,15)){
            click(closeAnalyzeBtn);
            SimpleUtils.pass("Clicked close button!");
        } else {
            SimpleUtils.fail("There is no close button!", false);
        }
    }

}
