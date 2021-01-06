package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.CompliancePage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleCompliancePage extends BasePage implements CompliancePage {
    public ConsoleCompliancePage() {
        PageFactory.initElements(getDriver(), this);
    }
    @FindBy(css = "div.analytics-new-violations-count.ng-binding")
    private WebElement totalViolationHrs;

    public String getTheTotalViolationHrsFromSmartCard() throws Exception {
        String hrsOfTotalViolation = "";
        if (isElementLoaded(totalViolationHrs, 5)){
            hrsOfTotalViolation = totalViolationHrs.getText();
            SimpleUtils.pass("Get the total violation hrs successfully");
        } else {
            SimpleUtils.fail("Total violation hours not loaded successfully", false);
        }
        return hrsOfTotalViolation;
    }


}
