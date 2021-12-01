package com.legion.pages.core.opemployeemanagement;

import com.legion.pages.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.legion.utils.MyThreadLocal.getDriver;

public class EmployeeManagementPanelPage extends BasePage {
    public EmployeeManagementPanelPage() {
        PageFactory.initElements(getDriver(), this);
    }

    // Added by Sophia
    @FindBy(css = "[title='Absence Management']")
    private WebElement absentManagement;


    public void goToAbsentManagementPage() {
        absentManagement.click();
        waitForSeconds(5);
    }

    public String getDashboardCardContent(){
        return absentManagement.getText();
    }

}
