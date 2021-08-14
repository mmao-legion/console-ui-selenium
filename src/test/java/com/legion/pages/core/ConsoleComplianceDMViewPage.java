package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.ComplianceDMViewPage;
import com.legion.pages.CompliancePage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.stream.Collectors;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleComplianceDMViewPage extends BasePage implements ComplianceDMViewPage {
    public ConsoleComplianceDMViewPage() {
        PageFactory.initElements(getDriver(), this);
    }
    @FindBy(css = ".analytics-new-table-group-row-open")
    private List<WebElement>  upperFieldsInDMView;

    @FindBy (css = "lg-search.analytics-new-table-filter input")
    private WebElement searchLocationInCompliancePage;

    public Map<String, String> getAllUpperFieldInfoFromComplianceDMViewByUpperField(String upperFieldName) throws Exception {
        Map<String, String> allUpperFieldInfo = new HashMap<>();
        boolean isUpperFieldMatched = false;
        if (isElementLoaded(searchLocationInCompliancePage,5)) {
            searchLocationInCompliancePage.sendKeys(upperFieldName);
            waitForSeconds(3);
            if (areListElementVisible(upperFieldsInDMView, 10) && upperFieldsInDMView.size() != 0) {
                for (int i = 0; i < upperFieldsInDMView.size(); i++) {
                    WebElement upperFieldInDMView = upperFieldsInDMView.get(i).findElement(By.cssSelector("[jj-switch-when=\"cells.CELL_UNTOUCHED\"] span"));
                    if (upperFieldInDMView != null) {
                        String uppeFieldNameInDMView = upperFieldInDMView.getText();
                        if (uppeFieldNameInDMView != null && uppeFieldNameInDMView.equals(upperFieldName)) {
                            isUpperFieldMatched = true;
                            //add schedule upperfield Name
                            allUpperFieldInfo.put("upperFieldName", uppeFieldNameInDMView);
                            //add Total Extra Hours
                            allUpperFieldInfo.put("totalExtraHours", upperFieldsInDMView.get(i).findElement(By.className("analytics-new-cell-as-input")).getText());
                            List<WebElement> upperFieldHeaders = upperFieldsInDMView.get(i).findElements(By.cssSelector("[class=\"ng-scope col-fx-1\"]"));
                            //add Total Overtime
                            allUpperFieldInfo.put("overtime", upperFieldHeaders.get(0).getText());
                            //add Clopening
                            allUpperFieldInfo.put("clopening", upperFieldHeaders.get(1).getText());
                            //add Missed Meal
                            allUpperFieldInfo.put("missedMeal", upperFieldHeaders.get(2).getText());
                            //add Schedule Changed
                            allUpperFieldInfo.put("scheduleChanged", upperFieldHeaders.get(3).getText());
                            //add Doubletime
                            allUpperFieldInfo.put("doubletime", upperFieldHeaders.get(4).getText());
                            //add Late Schedule
                            allUpperFieldInfo.put("lateSchedule", upperFieldHeaders.get(5).getText());
                        } else {
                            SimpleUtils.report("Get upperField info in DM View failed, there is no upperFields display in this upperFields");
                        }
                    }
                }
                if (!isUpperFieldMatched) {
                    SimpleUtils.fail("Get upperField info in DM View failed, there is no matched upperField display in DM view", false);
                } else {
                    SimpleUtils.pass("Get upperField info in DM View successful! ");
                }
            } else
                SimpleUtils.fail("Get upperField info in DM View failed, there is no upperField display in DM view", false);
            searchLocationInCompliancePage.clear();
        } else {
            SimpleUtils.fail("getDataInCompliancePage: search input fail to load!", true);
        }
        return allUpperFieldInfo;
    }
}
