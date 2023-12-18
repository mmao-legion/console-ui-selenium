package com.legion.pages.core;

import com.legion.pages.*;
import com.legion.pages.SmartTemplatePage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
}
