package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.LiquidDashboardPage;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import cucumber.api.java.hu.Ha;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    @FindBy (css = "div[ng-if=\"ShowEdit\"]")
    private WebElement editLinkBtn;

    @FindBy (xpath = "//div[text()=\"Add Link\"]")
    private WebElement addLinkBtn;

    @FindBy (xpath = "//span[text()=\"CANCEL\"]")
    private WebElement cancelAddLinkBtn;

    @FindBy (xpath = "//span[text()=\"SAVE\"]")
    private WebElement saveAddLinkBtn;

    @FindBy (css = ".link-title.link-title-text")
    private List<WebElement> linkTitles;

    @FindBy (css = ".link-row.link-row-text")
    private List<WebElement> linkTexts;

    @FindBy (css = "ng-container[ng-repeat=\"link in dataLinks\"] div")
    private List<WebElement> linksOnWidget;

    @FindBy (css = ".forecast.forecast-row.row-fx.ng-scope")
    private WebElement dataOnTodayForecast;

    @FindBy (css = ".row-fx.schedule-table-row.ng-scope")
    private List<WebElement> dataOnSchedules;

    @FindBy (className = "widgetCommonText")
    private WebElement welcomeText;

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
            if (isElementLoaded(welcomeText, 10)) {
                SimpleUtils.pass("Edit Dashboard Page: Click on Save button Successfully!");
            }else {
                SimpleUtils.fail("Edit Dashboard Page: Click on Save button failed, Dashboard welcome text not loaded Successfully!", false);
            }
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


    // Added by Nora
    @FindBy(css = ".gridster-item")
    private List<WebElement> widgets;
    @FindBy(css = "div.background-current-week-legend-table")
    private WebElement currentWeekOnSchedules;
    @FindBy(css = "lg-alert .slideNumberText")
    private WebElement alertsWeek;
    @FindBy(css = ".lg-timesheet-carousel__table-cell")
    private List<WebElement> alertsCells;
    @FindBy(css = "[label=\"'Timesheet Approval Status'\"] .dms-box-item-title-row span")
    private WebElement timesheetApprovalStatusWeek;
    @FindBy(className = "analytics-new-smart-card-timesheet-approval-legend-item")
    private List<WebElement> timesheetApprovalLegendItems;

    @Override
    public void verifyTheContentOnTimesheetApprovalStatusWidgetLoaded(String currentWeek) throws Exception {
        /*Timesheet Approval Rate  widget should show:
        a. current week, e.g. Week of Jun 20;
        b. the card is < 24 Hrs/24-48 Hrs/48+ Hrs/Unapproved with different color
        c. [View Timesheets] button*/
        List<String> legendItems = new ArrayList<>(Arrays.asList("< 24 Hrs", "24-48 Hrs", "48+ Hrs", "Unapproved"));
        if (isElementLoaded(timesheetApprovalStatusWeek, 5) && timesheetApprovalStatusWeek.getText().toLowerCase().contains(currentWeek.toLowerCase())) {
            SimpleUtils.pass("The week of \"Timesheet Approval Status\" is loaded and correct!");
        } else {
            SimpleUtils.warn("The week of \"Timesheet Approval Status\" is not loaded or incorrect!");
        }
        if (areListElementVisible(timesheetApprovalLegendItems, 5) && timesheetApprovalLegendItems.size() == 4) {
            for (WebElement item : timesheetApprovalLegendItems) {
                if (legendItems.contains(item.getText().trim())) {
                    SimpleUtils.pass("Verified Legend Item: \"" + item.getText().trim() + "\" loaded Correctly!");
                }else {
                    SimpleUtils.fail("Unexpected legend item: \"" + item.getText().trim() + "\" loaded!", false);
                }
            }
        }else {
            SimpleUtils.fail("The Legend Items of \"Timesheet Approval Status\" not loaded Successfully!", false);
        }
        if (isElementLoaded(MyThreadLocal.getDriver().findElement(By.cssSelector("lg-analytics-timesheet-approval-primary .dms-action-link")), 5)) {
            SimpleUtils.pass("\"View Timesheets\" link loaded Successfully on \"Timesheet Approval Status\"!");
        } else {
            SimpleUtils.fail("\"View Timesheets\" link not loaded Successfully on \"Timesheet Approval Status\"!", false);
        }
    }

    @Override
    public void clickOnLinkByWidgetNameAndLinkName(String widgetName, String linkName) throws Exception {
        //String startingTomorrow = "starting tomorrow";
        if (areListElementVisible(widgets, 10)) {
            for (WebElement widget : widgets) {
                WebElement widgetTitle = widget.findElement(By.className("dms-box-title"));
                //if (widgetTitle != null && (widgetTitle.getText().toLowerCase().trim().contains(widgetName.toLowerCase()) ||
                if (widgetTitle != null && (widgetTitle.getText().toLowerCase().trim().contains(widgetsNameWrapper(widgetName)) ||
                        //widgetTitle.getText().toLowerCase().trim().contains(startingTomorrow.toLowerCase().trim()))) {
                        widgetTitle.getText().toLowerCase().trim().contains(widgetsNameWrapper(widgetName)))) {
                    try {
                        WebElement link = widget.findElement(By.className("dms-action-link"));
                        if (link != null && linkName.toLowerCase().equals(link.getText().toLowerCase().trim())) {
                            clickTheElement(link);
                            SimpleUtils.pass("Click on: \"" + linkName + "\" on Widget: \"" + widgetName + "\" Successfully!");
                            break;
                        }
                    }catch (Exception e) {
                        continue;
                    }
                }
            }
        }else {
            SimpleUtils.report("There are no widgets on dashboard, please turn on them!");
        }
    }

    @Override
    public void verifyEditLinkOfHelpgulLinks() throws Exception {
        if (isElementLoaded(editLinkBtn,10)){
            scrollToElement(editLinkBtn);
            click(editLinkBtn);
            SimpleUtils.pass("Edit link button has been clicked!");
        } else {
            SimpleUtils.fail("verifyEditLinkOfHelpgulLinks: Edit link button fail to load!", true);
        }
    }

    @Override
    public void addLinkOfHelpfulLinks() throws Exception {
        if (isElementLoaded(addLinkBtn,10)){
            click(addLinkBtn);
            SimpleUtils.pass("add link button has been clicked successfully!");
            editNewLink();
        } else if(areListElementVisible(linkTitles) && linkTitles.size()==5) {
            SimpleUtils.pass("there already 5 links");
        } else {
            SimpleUtils.fail("Add Link button fail to load!",true);
        }
    }

    private void editNewLink() throws Exception{
        if (areListElementVisible(linkTitles,10) && areListElementVisible(linkTexts,10)){
            linkTitles.get(linkTitles.size()-1).findElement(By.cssSelector("input")).sendKeys("link"+linkTitles.size());
            linkTexts.get(linkTexts.size()-1).findElement(By.cssSelector("input")).sendKeys("https://www.google.com/");
        } else {
            SimpleUtils.fail("editNewLink: there is no link to edit!",true);
        }
    }

    @Override
    public void deleteAllLinks() throws Exception {
        int s = linkTexts.size();
        if (areListElementVisible(linkTexts,10)){
            for(int i=0; i<s ;i++){
                moveToElementAndClick(linkTexts.get(0).findElement(By.cssSelector(".removeLink")));
                SimpleUtils.pass("delete link successfully!");
            }
        } else {
            SimpleUtils.report("No links to delete!");
        }
    }

    @Override
    public void saveLinks() throws Exception {
        if (isElementLoaded(saveAddLinkBtn,10)){
            click(saveAddLinkBtn);
            SimpleUtils.pass("save button has been clicked successfully!");
        } else {
            SimpleUtils.fail("save button fail to load!",true);
        }
    }

    @Override
    public void cancelLinks() throws Exception {
        if (isElementLoaded(cancelAddLinkBtn,10)){
            click(cancelAddLinkBtn);
            SimpleUtils.pass("cancel button has been clicked successfully!");
        } else {
            SimpleUtils.fail("cancel button fail to load!",true);
        }
    }

    @Override
    public void verifyLinks() throws Exception {
        String handle = getDriver().getWindowHandle();
        if (areListElementVisible(linksOnWidget,10)){
            for (WebElement e: linksOnWidget){
                moveToElementAndClick(e);
                SimpleUtils.pass("new tab open: "+getDriver().getWindowHandle());
                getDriver().switchTo().window(handle);
            }
        } else {
            SimpleUtils.fail("verifyLinks: there is no links to click!",true);
        }
    }

    @Override
    public void verifyNoLinksOnHelpfulLinks() throws Exception {
        if (areListElementVisible(widgetsInDashboardPage,10)){
            for (WebElement widgetTemp : widgetsInDashboardPage){
                if(widgetTemp.findElement(By.cssSelector(".dms-box-title")).getText().toLowerCase().contains("helpful links")){
                    if (widgetTemp.findElement(By.cssSelector("div[ng-if=\"linkText\"]")).getAttribute("class").contains("nodata")){
                        SimpleUtils.pass("No links, message: "+widgetTemp.findElement(By.cssSelector("div[ng-if=\"linkText\"] h1")).getText());
                    }
                }
            }
        } else {
            SimpleUtils.fail("Widgets in Dashboard page fail to load!",true);
        }
    }

    @Override
    public boolean isSpecificWidgetLoaded(String widgetName) throws Exception {
        boolean isLoaded = false;
        String startingTomorrow = "starting tomorrow";
        if (areListElementVisible(widgets, 10)) {
            for (WebElement widget : widgets) {
                try {
                    WebElement widgetTitle = widget.findElement(By.cssSelector(".dms-box-title"));
                    if (widgetTitle != null && (widgetTitle.getText().toLowerCase().trim().contains(widgetName.toLowerCase()) ||
                            widgetTitle.getText().toLowerCase().trim().contains(startingTomorrow.toLowerCase().trim()))) {
                        isLoaded = true;
                    }
                }catch (Exception e) {
                    continue;
                }
            }
        }
        return isLoaded;
    }

    @Override
    public String getTheStartOfCurrentWeekFromSchedulesWidget() throws Exception {
        String currentWeek = "";
        if (isElementLoaded(currentWeekOnSchedules, 5)) {
            if (currentWeekOnSchedules.getText().contains("—")) {
                currentWeek = currentWeekOnSchedules.getText().split("—")[0];
                if (currentWeek.endsWith("\n")) {
                    currentWeek = currentWeek.substring(0, currentWeek.length() - 1);
                }
            }
        }
        if (!currentWeek.isEmpty()) {
            SimpleUtils.pass("Get the start of the current week: \"" + currentWeek + "\" Successfully!");
        }else {
            SimpleUtils.fail("Failed to get the start of the current week!", false);
        }
        return currentWeek;
    }

    @Override
    public List<String> verifyTheContentOnAlertsWidgetLoaded(String currentWeek) throws Exception {
        List<String> alerts = new ArrayList<>();
        /*Alerts widget should show:
        a. current week, e.g. Week of Jun 20;
        b. the number of Early Clocks/Incomplete Clocks/No Show/Late Clocks
                /Missed Meal/Unscheduled
        c. [View Timesheets] button*/
        if (isElementLoaded(alertsWeek, 5) && alertsWeek.getText().toLowerCase().contains(currentWeek.toLowerCase())) {
            SimpleUtils.pass("The week of Alerts is loaded and correct!");
        } else {
            SimpleUtils.fail("The week of \"Alerts\" is not loaded or incorrect!", false);
        }
        if (areListElementVisible(alertsCells, 5) && alertsCells.size() == 6) {
            for (WebElement alertsCell : alertsCells) {
                if (!alertsCell.getText().isEmpty()) {
                    alerts.add(alertsCell.getText());
                    SimpleUtils.report("Get the alerts Data: \"" + alertsCell.getText() + "\" Successfully!");
                } else {
                    SimpleUtils.fail("Failed to get the alert data!", false);
                }
            }
        } else {
            SimpleUtils.fail("The Alerts data not loaded Successfully!", false);
        }
        if (isElementLoaded(MyThreadLocal.getDriver().findElement(By.cssSelector("lg-alert .dms-action-link")), 5)) {
            SimpleUtils.pass("\"View Timesheets\" link loaded Successfully!");
        } else {
            SimpleUtils.fail("\"View Timesheets\" link not loaded Successfully!", false);
        }

        return alerts;
    }

    @Override
    public void verifyIsGraphExistedOnWidget() throws Exception {
        if (areListElementVisible(widgetsInDashboardPage,10)){
            for (WebElement widgetTemp : widgetsInDashboardPage){
                waitForSeconds(3);
                if(widgetTemp.findElement(By.cssSelector(".dms-box-title")).getText().toLowerCase().contains("forecast")){
                    if (isElementLoaded(widgetTemp.findElement(By.cssSelector("#curvedGraphDiv")),10)){
                        SimpleUtils.pass("there is a graph on today's forecast widget.");
                    } else {
                        SimpleUtils.fail("there is no graph on today's widget.",true);
                    }
                }
            }
        } else {
            SimpleUtils.fail("Widgets in Dashboard page fail to load!",true);
        }
    }

    @Override
    public HashMap <String, Float> getDataOnTodayForecast() throws Exception {
        HashMap <String, Float> resultData = new HashMap<String, Float>();
        if (isElementLoaded(dataOnTodayForecast,10)){
            String tempData = dataOnTodayForecast.getText();
            String[] dataString = dataOnTodayForecast.getText().split("\n");
            resultData.put("demand forecast",Float.valueOf(dataString[0].replaceAll("Shoppers","")));
            resultData.put("budget",Float.valueOf(dataString[2].replaceAll("Hrs","")));
            resultData.put("scheduled",Float.valueOf(dataString[4].replaceAll("Hrs","")));
        } else {
            SimpleUtils.fail("getDataOnTodayForecast: No data on widget!",false);
        }
        return resultData;
    }

    @Override
    public List<String> getDataOnSchedulesWidget() throws Exception {
        List<String> resultList= new ArrayList<String>();
        if (areListElementVisible(dataOnSchedules,10)){
            for (int i=0;i<dataOnSchedules.size();i++){
                String[] temp1 = dataOnSchedules.get(i).getText().split("\n");
                String[] temp2 = Arrays.copyOf(temp1,8);
                resultList.add(Arrays.toString(temp2));
            }
        } else {
            SimpleUtils.fail("data on schedules widget fail to load!",true);
        }
        if (resultList.size()<=4){
            SimpleUtils.pass("there are 4 week info on Schedules widget!");
        } else {
            SimpleUtils.fail("there are more than 4 week on Schedule widget which is not expected!",true);
        }
        return resultList;
    }

    @Override
    public void clickFirstWeekOnSchedulesGoToSchedule() throws Exception {
        if (areListElementVisible(dataOnSchedules,10)){
            waitForSeconds(3);
            moveToElementAndClick(dataOnSchedules.get(0));
        } else {
            SimpleUtils.fail("clickFirstWeekOnSchedulesGoToSchedule: data on schedules widget fail to load!",true);
        }
    }

    @Override
    public void verifyWeekInfoOnWidget(String widgetTitle, String startdayOfWeek) throws Exception {
        String weekinfo = null;
        if (areListElementVisible(widgetsInDashboardPage,10)){
            for (WebElement widgetTemp : widgetsInDashboardPage){
                waitForSeconds(2);
                if(widgetTemp.findElement(By.cssSelector(".dms-box-title")).getText().toLowerCase().contains(widgetsNameWrapper(widgetTitle))){
                    if (widgetsNameWrapper(widgetTitle).equalsIgnoreCase("timesheet approval")){
                        if (!widgetTemp.findElement(By.cssSelector(".dms-box-title")).getText().toLowerCase().contains("timesheet approval status")){
                            if (widgetTemp.findElement(By.cssSelector(".dms-box-title")).getText().toLowerCase().contains("week of")){
                                weekinfo = widgetTemp.findElement(By.cssSelector(".dms-box-title")).getText().toLowerCase().split("week of ")[1];
                                if (startdayOfWeek.toLowerCase().contains(weekinfo)){
                                    SimpleUtils.pass("week info is right!");
                                } else {
                                    SimpleUtils.fail("week info is not correct!",true);
                                }
                                break;
                            }
                        }
                    } else {
                        if (widgetTemp.findElement(By.cssSelector(".dms-box-title")).getText().toLowerCase().contains("week of")){
                            weekinfo = widgetTemp.findElement(By.cssSelector(".dms-box-title")).getText().toLowerCase().split("week of ")[1];
                            if (startdayOfWeek.toLowerCase().contains(weekinfo)){
                                SimpleUtils.pass("week info is right!");
                            } else {
                                SimpleUtils.fail("week info is not correct!",true);
                            }
                            break;
                        }
                    }
                }
            }
        } else {
            SimpleUtils.fail("Widgets in Dashboard page fail to load!",true);
        }
    }

    @Override
    public void clickOnCarouselOnWidget(String widgetTitle, String rightOrLeft) throws Exception {
        if (areListElementVisible(widgetsInDashboardPage,10)){
            for (WebElement widgetTemp : widgetsInDashboardPage){
                if(widgetTemp.findElement(By.cssSelector(".dms-box-title")).getText().toLowerCase().contains(widgetsNameWrapper(widgetTitle))){
                    if (widgetsNameWrapper(widgetTitle).equalsIgnoreCase("timesheet approval")){
                        if (!widgetTemp.findElement(By.cssSelector(".dms-box-title")).getText().toLowerCase().contains("timesheet approval status")){
                            List<WebElement> buttonOnCarousel = widgetTemp.findElements(By.cssSelector(".carosel-div .cus-carousel-control"));
                            if (areListElementVisible(buttonOnCarousel,10)){
                                if (rightOrLeft.toLowerCase().contains("left")){
                                    scrollToElement(buttonOnCarousel.get(0));
                                    click(buttonOnCarousel.get(0));
                                    SimpleUtils.pass("click left on carousel");
                                } else {
                                    scrollToElement(buttonOnCarousel.get(1));
                                    click(buttonOnCarousel.get(1));
                                    SimpleUtils.pass("click right on carousel");
                                }
                                break;
                            }
                        }
                    } else {
                        List<WebElement> buttonOnCarousel = widgetTemp.findElements(By.cssSelector(".carosel-div .cus-carousel-control"));
                        if (areListElementVisible(buttonOnCarousel,10)){
                            if (rightOrLeft.toLowerCase().contains("left")){
                                scrollToElement(buttonOnCarousel.get(0));
                                click(buttonOnCarousel.get(0));
                                SimpleUtils.pass("click left on carousel");
                            } else {
                                scrollToElement(buttonOnCarousel.get(1));
                                click(buttonOnCarousel.get(1));
                                SimpleUtils.pass("click right on carousel");
                            }
                            break;
                        }
                    }
                }
            }
        } else {
            SimpleUtils.fail("Widgets in Dashboard page fail to load!",true);
        }
    }

    @FindBy (css = ".dms-number-x-large")
    private List<WebElement> dataOnComplianceWidget;
    @Override
    public List<String> getDataOnComplianceViolationWidget() throws Exception {
        List<String> resultList= new ArrayList<String>();
        if (areListElementVisible(dataOnComplianceWidget,10)){
            for (int i=0;i<dataOnComplianceWidget.size();i++){
                resultList.add(dataOnComplianceWidget.get(i).getText());
            }
        } else {
            SimpleUtils.fail("data on Compliance violation widget fail to load!",true);
        }
        return resultList;
    }

    @FindBy(css = "div.console-navigation-item-label.Compliance")
    private WebElement goToCompliance;
    @Override
    public void goToCompliancePage() throws Exception {
        if (isElementLoaded(goToCompliance,5)){
            click(goToCompliance);
            SimpleUtils.pass("Comliance button clicked!");
        } else {
            SimpleUtils.fail("compliance button is not loaded, please double check!",true);
        }
    }

    @FindBy (css = "lg-search.analytics-new-table-filter input")
    private WebElement searchLocationInCompliancePage;
    @FindBy (css = ".analytics-new-table-group-row-ScheduleCompliance")
    private List <WebElement> searchResultsIncompliacePage;
    @Override
    public List<String> getDataInCompliancePage(String location) throws Exception {
        List<String> resultList= new ArrayList<String>();
        int tempViolation = 0;
        String tempTotalHrs = "";
        if (isElementLoaded(searchLocationInCompliancePage,5)){
            searchLocationInCompliancePage.sendKeys(location);
            if (areListElementVisible(searchResultsIncompliacePage,5)){
                //get values from first row
                waitForSeconds(2);
                for (WebElement element : searchResultsIncompliacePage.get(0).findElements(By.cssSelector("div[class=\"ng-scope col-fx-1\"]"))){
                    scrollToBottom();
                    if (element.getText().toLowerCase().equals("0.0") ){
                        //0.0 no violation
                    } else if (element.getText().toLowerCase().equals("yes")){
                        tempViolation++;
                    } else if (element.getText().toLowerCase().equals("no")){
                        //0.0 no violation
                    } else {
                        tempViolation++;
                    }
                }
                resultList.add(String.valueOf(tempViolation));
                tempTotalHrs = searchResultsIncompliacePage.get(0).findElement(By.cssSelector("div.analytics-new-cell-as-input")).getText();
                //e.g.: 16.0--->16, 16.5--->16.5
                if (tempTotalHrs!="" && tempTotalHrs!=null){
                    if (tempTotalHrs.contains(".0")){
                        //tempTotalHrs.replaceAll(".0","");
                        String a = tempTotalHrs.substring(0,tempTotalHrs.indexOf("."));
                        resultList.add(a);
                    }
                }
                //just has 1 location for SM view
                resultList.add("1");
            } else {
                SimpleUtils.fail("No search result!",true);
            }

        } else {
            SimpleUtils.fail("getDataInCompliancePage: search input fail to load!",true);
        }
        return resultList;
    }
}
