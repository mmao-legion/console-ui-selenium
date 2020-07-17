package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.LiquidDashboardPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleLiquidDashboardPage extends BasePage implements LiquidDashboardPage {
    public ConsoleLiquidDashboardPage(){
        PageFactory.initElements(getDriver(), this);
    }

    @FindBy(css = ".widgetButton")
    private WebElement editBtn;

    @FindBy (css = ".edit-dashboard-text")
    private WebElement editDasboardText;

    @FindBy (xpath = "//ng-container[contains(@ng-repeat, \"groupedWidgets\")]")
    private List<WebElement> widgetsInManagePage;

    @FindBy (xpath = "//span[text()=\"Manage\"]")
    private WebElement manageBtn;

    @FindBy (css = ".manageWidgetsEditLabel")
    private WebElement editDashboardBtn;

    @FindBy (xpath = "//div[@gridster-item]")
    private  List<WebElement> widgetsInDashboardPage;

    @FindBy (xpath = "//span[text()=\"Save\"]")
    private WebElement saveBtn;

    @FindBy (xpath = "//span[text()=\"Cancel\"]")
    private WebElement cancelBtn;

    @FindBy (xpath = "//span[text()=\"Back\"]")
    private WebElement backBtn;

    @FindBy (css = "input[placeholder=\"Search for widgets\"]")
    private WebElement searchInput;

    @Override
    public void enterEditMode() throws Exception {
        scrollToTop();
        if (isElementLoaded(editBtn,5)){
            click(editBtn.findElement(By.cssSelector("button")));
            if (isElementLoaded(editDasboardText,5)){
                SimpleUtils.pass("Edit mode load successfully!");
            } else {
                SimpleUtils.fail("Edit mode fail to load!", true);
            }
        } else{
            SimpleUtils.fail("Edit button fail to load!", true);
        }
    }

    @Override
    public void switchOnWidget(String widget) throws Exception {
        String widgetName ="";
        List<WebElement> widgets = widgetsInManagePage;
        if (isElementLoaded(manageBtn,10)){
            click(manageBtn);
            if (areListElementVisible(widgets,10)){
                for (int i=0; i<widgets.size(); i++) {
                    widgetName = widgets.get(i).findElement(By.cssSelector("div[class=\"detail-div\"] :nth-child(1)")).getText().toLowerCase();
                    if (widget.toLowerCase().contains(widgetName)){
                        if (widgets.get(i).findElement(By.cssSelector("ng-form input")).getAttribute("class").contains("ng-not-empty")){
                            SimpleUtils.pass(widget+"widget's already switched on!");
                        } else {
                            scrollToElement(widgets.get(i));
                            click(widgets.get(i).findElement(By.cssSelector(".slider")));
                            SimpleUtils.pass(widget+" widget's already switched on!");
                        }
                        break;
                    }
                }
                //return to edit dashboard
                if (isElementLoaded(editDashboardBtn,5)){
                    click(editDashboardBtn);
                    SimpleUtils.assertOnFail(widget+" widget is not loaded!",verifyIfSpecificWidgetDisplayed(widget), true);
                } else {
                    SimpleUtils.fail("Edit Dashboard button fail to load!",true);
                }
            } else {
                SimpleUtils.fail("Widgets in Manage page fail to load!",true);
            }
        } else {
            SimpleUtils.fail("Manage button fail to load!",true);
        }
    }

    @Override
    public void switchOffWidget(String widget) throws Exception {
        String widgetName ="";
        boolean flag = false;
        List<WebElement> widgets = widgetsInManagePage;
        if (isElementLoaded(manageBtn,10)){
            click(manageBtn);
            if (areListElementVisible(widgets,10)){
                for (int i=0; i<widgets.size(); i++) {
                    widgetName = widgets.get(i).findElement(By.cssSelector("div[class=\"detail-div\"] :nth-child(1)")).getText().toLowerCase();
                    if (widget.contains(widgetName)){
                        if (widgets.get(i).findElement(By.cssSelector("ng-form input")).getAttribute("class").contains("ng-empty")){
                            SimpleUtils.pass(widget+" widget's already switched off!");
                        } else {
                            scrollToElement(widgets.get(i));
                            click(widgets.get(i).findElement(By.cssSelector(".slider")));
                            SimpleUtils.pass(widget+" widget's already switched off!");
                        }
                        break;
                    }
                }
                //return to edit dashboard
                if (isElementLoaded(editDashboardBtn,5)){
                    click(editDashboardBtn);
                    if (!verifyIfSpecificWidgetDisplayed(widget)){
                        flag = true;
                    }
                    SimpleUtils.assertOnFail(widget+"widget is loaded which is not expected!",flag, true);
                } else {
                    SimpleUtils.fail("Edit Dashboard button fail to load!",true);
                }
            } else {
                SimpleUtils.fail("Widgets in Manage page fail to load!",true);
            }
        } else {
            SimpleUtils.fail("Manage button fail to load!",true);
        }
    }

    //parameter option: helpful links and so on
    private boolean verifyIfSpecificWidgetDisplayed(String widgetTitle) throws Exception{
        waitForSeconds(10);
        if (areListElementVisible(widgetsInDashboardPage,10)){
            for (WebElement widgetTemp : widgetsInDashboardPage){
                if(widgetTemp.findElement(By.cssSelector(".dms-box-title")).getText().toLowerCase().contains(widgetsNameWrapper(widgetTitle))){
                    if (widgetsNameWrapper(widgetTitle).equalsIgnoreCase("timesheet approval")){
                        if (widgetTemp.findElement(By.cssSelector(".dms-box-title")).getText().toLowerCase().contains("timesheet approval status")){
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
            }
        } else {
            SimpleUtils.fail("Widgets in Dashboard page fail to load!",true);
        }
        return false;
    }

    @Override
    public void closeWidget(String widgetTitle) throws Exception {
        boolean flag =false;
        waitForSeconds(10);
        if (areListElementVisible(widgetsInDashboardPage,10)){
            for (WebElement widgetTemp : widgetsInDashboardPage){
                if(widgetTemp.findElement(By.cssSelector(".dms-box-title")).getText().toLowerCase().contains(widgetsNameWrapper(widgetTitle))){
                    if (widgetsNameWrapper(widgetTitle).equalsIgnoreCase("timesheet approval")){
                        if (!widgetTemp.findElement(By.cssSelector(".dms-box-title")).getText().toLowerCase().contains("timesheet approval status")){
                            scrollToElement(widgetTemp);
                            click(widgetTemp.findElement(By.cssSelector(".boxclose")));
                            if (!verifyIfSpecificWidgetDisplayed(widgetTitle)){
                                flag = true;
                            }
                            SimpleUtils.assertOnFail("widget is loaded which is not expected!",flag, true);
                            break;
                        }
                    } else {
                        scrollToElement(widgetTemp);
                        click(widgetTemp.findElement(By.cssSelector(".boxclose")));
                        if (!verifyIfSpecificWidgetDisplayed(widgetTitle)){
                            flag = true;
                        }
                        SimpleUtils.assertOnFail("widget is loaded which is not expected!",flag, true);
                        break;
                    }
                }
            }
        } else {
            SimpleUtils.fail("Widgets in Dashboard page fail to load!",true);
        }
    }

    @Override
    public void verifyUpdateTimeInfoIcon(String widgetTitle) throws Exception {
        waitForSeconds(3);
        if (areListElementVisible(widgetsInDashboardPage,10)){
            for (WebElement widgetTemp : widgetsInDashboardPage){
                if(widgetTemp.findElement(By.cssSelector(".dms-box-title")).getText().toLowerCase().contains(widgetsNameWrapper(widgetTitle))){
                    if (widgetsNameWrapper(widgetTitle).equalsIgnoreCase("timesheet approval")){
                        if (!widgetTemp.findElement(By.cssSelector(".dms-box-title")).getText().toLowerCase().contains("timesheet approval status")){
                            scrollToElement(widgetTemp);
                            mouseToElement(widgetTemp);
                            if (widgetTemp.findElement(By.cssSelector(".widget-timerclock"))!=null){
                                SimpleUtils.pass("update time icon load successfully!");
                            }
                            break;
                        }
                    } else {
                        scrollToElement(widgetTemp);
                        mouseToElement(widgetTemp);
                        if (widgetTemp.findElement(By.cssSelector(".widget-timerclock"))!=null){
                            SimpleUtils.pass("update time icon load successfully!");
                        }
                        break;
                    }
                }
            }
        } else {
            SimpleUtils.fail("Widgets in Dashboard page fail to load!",true);
        }
    }

    @Override
    public void saveAndExitEditMode() throws Exception{
        if (isElementLoaded(saveBtn,10)){
            click(saveBtn);
        } else {
            SimpleUtils.fail("save button is not loaded!",true);
        }
    }

    @Override
    public void cancelAndExitEditMode() throws Exception{
        if (isElementLoaded(cancelBtn,10)){
            click(cancelBtn);
        } else {
            SimpleUtils.fail("cancel button is not loaded!",true);
        }
    }

    @Override
    public void verifyBackBtn() throws Exception {
        if (isElementLoaded(manageBtn,5)){
            click(manageBtn);
            if (isElementLoaded(backBtn,10)){
                click(backBtn);
                SimpleUtils.pass("Back button is working fine!");
            } else {
                SimpleUtils.fail("Back button is not loaded!",true);
            }
        } else {
            SimpleUtils.fail("verifyBackBtn: Manage button fail to load!",true);
        }
    }

    @Override
    public void verifySearchInput(String widgetTitle) throws Exception {
        if (isElementLoaded(manageBtn,10)){
            click(manageBtn);
        } else {
            SimpleUtils.fail("Manage button fail to load!",true);
        }
        if (isElementLoaded(searchInput,5)){
            searchInput.sendKeys(widgetTitle);
            waitForSeconds(5);
            if (areListElementVisible(widgetsInManagePage,5)){
                String actualResult = widgetsInManagePage.get(0).findElement(By.cssSelector("div[class=\"detail-div\"] :nth-child(1)")).getText().toLowerCase();
                if (widgetsInManagePage.size()==1 && widgetTitle.toLowerCase().contains(actualResult)){
                    SimpleUtils.pass("Search result is what you want!");
                } else {
                    SimpleUtils.fail("Search result is not correct!",true);
                }
            } else {
                SimpleUtils.fail("verifySearchInput: no search widgets result!",true);
            }
        } else {
            SimpleUtils.fail("Search input is not loaded!",true);
        }
    }


    //get widget name in edit page
    private String widgetsNameWrapper(String widgetTitleInManagePage) throws Exception {
        if (widgetTitleInManagePage.contains("starting soon")){
            return "starting";
        } else if (widgetTitleInManagePage.contains("timesheet approval rate")){
            return "timesheet approval";
        } else if (widgetTitleInManagePage.contains("compilance violation")){
            return "compliance violation";
        } else if (widgetTitleInManagePage.contains("today")){
            return "today";
        }
        return widgetTitleInManagePage;
    }
}
