package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.CinemarkMinorPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleCinemarkMinorPage extends BasePage implements CinemarkMinorPage {
    public ConsoleCinemarkMinorPage() {
        PageFactory.initElements(getDriver(), this);
    }

    @FindBy(css = "tbody[ng-repeat=\"item in $ctrl.sortedRows\"] .default")
    private WebElement defaultTemplate;
    @FindBy(css = "tbody[ng-repeat=\"item in $ctrl.sortedRows\"] .child-row.ng-scope lg-button.name")
    private WebElement childTemplate;
    @FindBy(css = "div[class=\"console-navigation-item-label Configuration\"]")
    private WebElement configurationTabInOP;
    @Override
    public void clickConfigurationTabInOP() throws Exception {
        if(isElementLoaded(configurationTabInOP, 15))
        {
            activeConsoleName = configurationTabInOP.getText();
            click(configurationTabInOP);
        }else{
            SimpleUtils.fail("Configuration button not present on the page",false);
        }
    }

    //Add by Haya
    @FindBy(xpath = "//span[text()= \"New Template\"]")
    private WebElement newTemplateBtn;
    @FindBy(css = ".modal-dialog")
    private WebElement newTemplateDialog;
    @FindBy(css = ".walkme-action-destroy-1.wm-close-link")
    private WebElement closeWakeme;
    @Override
    public void newTemplate(String templateName) throws Exception {
        if(isElementLoaded(newTemplateBtn, 15))
        {
            click(newTemplateBtn);
            if (isElementLoaded(newTemplateDialog,5) && newTemplateDialog.findElements(By.cssSelector("[required=\"required\"]")).size()==2){
                List<WebElement> inputs = newTemplateDialog.findElements(By.cssSelector("[required=\"required\"]"));
                inputs.get(0).clear();
                inputs.get(0).sendKeys(templateName);
                inputs.get(1).clear();
                inputs.get(1).sendKeys("description for "+ templateName);
                clickContinue();
                if (isElementLoaded(closeWakeme,10)){
                    click(closeWakeme);
                }
                waitForSeconds(5);
            }
        }else{
            SimpleUtils.fail("New Template button not present on the page",false);
        }
    }

    private void clickContinue() throws Exception{
        if (isElementLoaded(newTemplateDialog.findElement(By.cssSelector("lg-button[label=\"Continue\"]")),5)){
            click(newTemplateDialog.findElement(By.cssSelector("lg-button[label=\"Continue\"]")));
        }else{
            SimpleUtils.fail("Continue button does not present on the page",false);
        }
    }

    @FindBy(css = "form-section[form-title=\"Scheduling Minors (Age 14 & 15)\"]")
    private WebElement schedulingMinorRuleFor14N15;
    @FindBy(css = "form-section[form-title=\"Scheduling Minors (Age 16 & 17)\"]")
    private WebElement schedulingMinorRuleFor16N17;

    @Override
    public void minorRuleToggle(String action, String witchOne) throws Exception {
        if (witchOne.equalsIgnoreCase("14N15")){
            if (isElementLoaded(schedulingMinorRuleFor14N15,10)
                    && isElementLoaded(schedulingMinorRuleFor14N15.findElement(By.cssSelector(" .lg-button-group-last")),10)
                    && isElementLoaded(schedulingMinorRuleFor14N15.findElement(By.cssSelector(" .lg-button-group-first")),10)){
                //WebElement yesBtn = schedulingMinorRuleFor14N15.findElement(By.cssSelector(" .lg-button-group-first"));
                //WebElement noBtn = schedulingMinorRuleFor14N15.findElement(By.cssSelector(" .lg-button-group-last"));
                if (action.equalsIgnoreCase("no")){
                    scrollToElement(schedulingMinorRuleFor14N15.findElement(By.cssSelector(" .lg-button-group-last")));
                    click(schedulingMinorRuleFor14N15.findElement(By.cssSelector(" .lg-button-group-last")));
                    SimpleUtils.pass("Minor Rule For 14N15 is OFF");
                } else {
                    scrollToElement(schedulingMinorRuleFor14N15.findElement(By.cssSelector(" .lg-button-group-first")));
                    click(schedulingMinorRuleFor14N15.findElement(By.cssSelector(" .lg-button-group-first")));
                    SimpleUtils.pass("Minor Rule For 14N15 is ON");
                }
            }else{
                SimpleUtils.fail("Minor Rule section does not present on the page",false);
            }
        } else {
            if (isElementLoaded(schedulingMinorRuleFor16N17,10)
                    && isElementLoaded(schedulingMinorRuleFor16N17.findElement(By.cssSelector(" .lg-button-group-last")),10)
                    && isElementLoaded(schedulingMinorRuleFor16N17.findElement(By.cssSelector(" .lg-button-group-first")),10)){
                WebElement yesBtn = schedulingMinorRuleFor16N17.findElement(By.cssSelector(" .lg-button-group-first"));
                WebElement noBtn = schedulingMinorRuleFor16N17.findElement(By.cssSelector(" .lg-button-group-last"));
                if (action.equalsIgnoreCase("no")){
                    scrollToElement(noBtn);
                    click(noBtn);
                    SimpleUtils.pass("Minor Rule For 14N15 is OFF");
                } else {
                    scrollToElement(yesBtn);
                    click(yesBtn);
                    SimpleUtils.pass("Minor Rule For 14N15 is ON");
                }
            }else{
                SimpleUtils.fail("Minor Rule section does not present on the page",false);
            }
        }
    }

    @Override
    public void verifyDefaultMinorRuleIsOff(String witchOne) throws Exception {
        if (witchOne.equalsIgnoreCase("14N15")){
            if (isElementLoaded(schedulingMinorRuleFor14N15,10)
                    && isElementLoaded(schedulingMinorRuleFor14N15.findElement(By.cssSelector(" .lg-button-group-last")),10)){
                WebElement noBtn = schedulingMinorRuleFor14N15.findElement(By.cssSelector(" .lg-button-group-last"));
                if (noBtn.getAttribute("class").contains("selected")){//
                    SimpleUtils.pass("Minor Rule For 14N15 is OFF by default.");
                } else {
                    SimpleUtils.fail("Minor Rule For 14N15 should be OFF by default.",false);
                }
            }else{
                SimpleUtils.fail("Minor Rule section does not present on the page",false);
            }
        } else {
            if (isElementLoaded(schedulingMinorRuleFor16N17,10)
                    && isElementLoaded(schedulingMinorRuleFor16N17.findElement(By.cssSelector(" .lg-button-group-last")),10)){
                WebElement noBtn = schedulingMinorRuleFor16N17.findElement(By.cssSelector(" .lg-button-group-last"));
                if (noBtn.getAttribute("class").contains("selected")){//
                    SimpleUtils.pass("Minor Rule For 14N15 is OFF by default.");
                } else {
                    SimpleUtils.fail("Minor Rule For 14N15 should be OFF by default.",false);
                }
            }else{
                SimpleUtils.fail("Minor Rule section does not present on the page",false);
            }
        }
    }

    @FindBy(css = ".saveas-drop")
    private WebElement saveDrop;
    @FindBy(css = ".ng-scope.pre-saveas")
    private WebElement doActionBtn;
    @FindBy(css = "lg-button[label=\"Cancel\"]")
    private WebElement cancelBtnForTemplate;
    @Override
    public void saveOrPublishTemplate(String action) throws Exception {
        if (isElementLoaded(saveDrop,5)){
            scrollToElement(saveDrop);
            click(saveDrop);
            if (isElementLoaded(getDriver().findElement(By.cssSelector("h3[ng-click*=\""+action+"\"]")),5)){
                WebElement actionOption = getDriver().findElement(By.cssSelector("h3[ng-click*=\""+action+"\"]"));
                click(actionOption);
                click(doActionBtn);
            } else {
                SimpleUtils.fail("Option is not loaded!", false);
            }
        }
    }

    @Override
    public void clickOnBtn(String button) throws Exception {
        if (button.equalsIgnoreCase("edit template")){
            if(isElementLoaded(getDriver().findElement(By.cssSelector("lg-button[ng-click=\"editTemplate()\"]")), 15))
            {
                click(getDriver().findElement(By.cssSelector("lg-button[ng-click=\"editTemplate()\"]")));
            }else{
                SimpleUtils.fail("Cancel button does not present on the page",false);
            }
        } else {
            if(isElementLoaded(getDriver().findElement(By.cssSelector("lg-button[label=\""+button+"\"]")), 15))
            {
                click(getDriver().findElement(By.cssSelector("lg-button[label=\""+button+"\"]")));
            }else{
                SimpleUtils.fail("Cancel button does not present on the page",false);
            }
        }
    }

    @FindBy(css = "input[placeholder*=\"search\"]")
    private WebElement searchInput;
    @FindBy(css = "tbody[ng-repeat*=\"item\"]")
    private List<WebElement> templateList;
    @Override
    public void findDefaulTemplate(String templateName) throws Exception {
        if (isElementLoaded(searchInput,15)){
            searchInput.clear();
            searchInput.sendKeys(templateName);
            //click(defaultTemplate);
            if (areListElementVisible(templateList,10) && templateList.size()>0){
                WebElement template = templateList.get(0);
                if (!template.findElement(By.tagName("tr")).getAttribute("class").contains("expanded")){
                    waitForSeconds(3);
                    moveToElementAndClick(getDriver().findElements(By.cssSelector("tbody[ng-repeat*=\"item\"] td.toggle")).get(0));
                    //click(template.findElement(By.cssSelector("td.toggle")));
                }
                if (isElementLoaded(childTemplate,10)){
                    click(childTemplate);
                } else {
                    moveToElementAndClick(getDriver().findElements(By.cssSelector("tbody[ng-repeat*=\"item\"] lg-button")).get(0));
                }
                waitForSeconds(5);
            } else {
                SimpleUtils.fail("There is no child template", false);
            }
        } else {
            SimpleUtils.fail("There is no default template", false);
        }
    }

    @Override
    public void setMinorRuleByWeek(String minorType, String weekType, String maxOfDay, String maxOfHrs) throws Exception {
        List<WebElement> dataFields = null; //new ArrayList<WebElement>();
        if (minorType.equalsIgnoreCase("14N15")){
            dataFields = schedulingMinorRuleFor14N15.findElements(By.cssSelector("[ng-class=\"{'ng-invalid': $ctrl.invalid}\"]"));
        } else {
            dataFields = schedulingMinorRuleFor16N17.findElements(By.cssSelector("[ng-class=\"{'ng-invalid': $ctrl.invalid}\"]"));
        }
        if (dataFields.size()>5){
            if (weekType.equalsIgnoreCase("school week")){
                dataFields.get(0).clear();
                dataFields.get(0).sendKeys(maxOfDay);
                dataFields.get(1).clear();
                dataFields.get(1).sendKeys(maxOfHrs);
                SimpleUtils.pass(weekType+" set");
            }
            if (weekType.equalsIgnoreCase("non-school week")){
                dataFields.get(2).clear();
                dataFields.get(2).sendKeys(maxOfDay);
                dataFields.get(3).clear();
                dataFields.get(3).sendKeys(maxOfHrs);
                SimpleUtils.pass(weekType+" set");
            }
            if (weekType.equalsIgnoreCase("summer week")){
                dataFields.get(4).clear();
                dataFields.get(4).sendKeys(maxOfDay);
                dataFields.get(5).clear();
                dataFields.get(5).sendKeys(maxOfHrs);
                SimpleUtils.pass(weekType+" set");
            }
        } else {
            SimpleUtils.fail("No setting fields for minor rule",false);
        }
    }

    @Override
    public void setMinorRuleByDay(String minorType, String dayType, String from, String to, String maxOfHrs) throws Exception {
        List<WebElement> dataFields = null; //new ArrayList<WebElement>();
        if (minorType.equalsIgnoreCase("14N15")){
            dataFields = schedulingMinorRuleFor14N15.findElements(By.cssSelector("[ng-class=\"{'ng-invalid': $ctrl.invalid}\"]"));
        } else {
            dataFields = schedulingMinorRuleFor16N17.findElements(By.cssSelector("[ng-class=\"{'ng-invalid': $ctrl.invalid}\"]"));
        }
        if (dataFields.size()>20){
            if (dayType.equalsIgnoreCase("School today, school tomorrow")){
                selectByVisibleText(dataFields.get(6),from);
                selectByVisibleText(dataFields.get(7),to);
                dataFields.get(8).clear();
                dataFields.get(8).sendKeys(maxOfHrs);
                SimpleUtils.pass(dayType+" set");
            }
            if (dayType.equalsIgnoreCase("School today, no school tomorrow")){
                selectByVisibleText(dataFields.get(9),from);
                selectByVisibleText(dataFields.get(10),to);
                dataFields.get(11).clear();
                dataFields.get(11).sendKeys(maxOfHrs);
                SimpleUtils.pass(dayType+" set");
            }
            if (dayType.equalsIgnoreCase("No school today, no school tomorrow")){
                selectByVisibleText(dataFields.get(12),from);
                selectByVisibleText(dataFields.get(13),to);
                dataFields.get(14).clear();
                dataFields.get(14).sendKeys(maxOfHrs);
                SimpleUtils.pass(dayType+" set");
            }
            if (dayType.equalsIgnoreCase("No school today, school tomorrow")){
                selectByVisibleText(dataFields.get(15),from);
                selectByVisibleText(dataFields.get(16),to);
                dataFields.get(17).clear();
                dataFields.get(17).sendKeys(maxOfHrs);
                SimpleUtils.pass(dayType+" set");
            }
            if (dayType.equalsIgnoreCase("Summer day")){
                selectByVisibleText(dataFields.get(18),from);
                selectByVisibleText(dataFields.get(19),to);
                dataFields.get(20).clear();
                dataFields.get(20).sendKeys(maxOfHrs);
                SimpleUtils.pass(dayType+" set");
            }
        } else {
            SimpleUtils.fail("No setting fields for minor rule",false);
        }
    }
}
