package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.InboxPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleInboxPage  extends BasePage implements InboxPage {
    public ConsoleInboxPage(){
        PageFactory.initElements(getDriver(), this);
    }

    //Added by Nora

    //Added by Julie

    //Added by Marym

    //Added by Haya
    @FindBy(css = "form-section[form-title=\"Predictable Schedule\"] .lg-question-input")
    private List<WebElement> predictableScheduleSectionToggles;

    @Override
    public void turnGFEToggleOnOrOff(boolean isTurnOn) throws Exception {
        if (areListElementVisible(predictableScheduleSectionToggles,5) && predictableScheduleSectionToggles.size()>3){
            if (isTurnOn){
                if (predictableScheduleSectionToggles.get(3).findElement(By.cssSelector("input")).getAttribute("class").contains("ng-empty")){
                    click(predictableScheduleSectionToggles.get(3).findElement(By.cssSelector("span")));
                    SimpleUtils.pass("GFE toggle is turned on!");
                } else {
                    SimpleUtils.report("GFE toggle is already on!");
                }
            } else {
                if (predictableScheduleSectionToggles.get(3).findElement(By.cssSelector("input")).getAttribute("class").contains("ng-not-empty")){
                    click(predictableScheduleSectionToggles.get(3).findElement(By.cssSelector("span")));
                    SimpleUtils.pass("GFE toggle is turned off !");
                } else {
                    SimpleUtils.report("GFE toggle is already off!");
                }
            }
        } else {
            SimpleUtils.fail("There is no predictable schedule settings!", false);
        }
    }

    @Override
    public void turnVSLToggleOnOrOff(boolean isTurnOn) throws Exception {
        if (areListElementVisible(predictableScheduleSectionToggles,5) && predictableScheduleSectionToggles.size()>3){
            if (isTurnOn){
                if (predictableScheduleSectionToggles.get(1).findElement(By.cssSelector("input")).getAttribute("class").contains("ng-empty")){
                    click(predictableScheduleSectionToggles.get(1).findElement(By.cssSelector("span")));
                    SimpleUtils.pass("GFE toggle is turned on!");
                } else {
                    SimpleUtils.report("GFE toggle is already on!");
                }
            } else {
                if (predictableScheduleSectionToggles.get(1).findElement(By.cssSelector("input")).getAttribute("class").contains("ng-not-empty")){
                    click(predictableScheduleSectionToggles.get(1).findElement(By.cssSelector("span")));
                    SimpleUtils.pass("GFE toggle is turned off !");
                } else {
                    SimpleUtils.report("GFE toggle is already off!");
                }
            }
        } else {
            SimpleUtils.fail("There is no predictable schedule settings!", false);
        }
    }
}
