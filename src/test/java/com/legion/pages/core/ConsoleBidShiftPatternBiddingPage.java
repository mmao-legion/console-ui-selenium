package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.BidShiftPatternBiddingPage;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleBidShiftPatternBiddingPage extends BasePage implements BidShiftPatternBiddingPage {
    public ConsoleBidShiftPatternBiddingPage() {
        PageFactory.initElements(getDriver(), this);
    }

    @FindBy(xpath = "//div[contains(@class,'MuiBox-root css-0')][2]")
    private WebElement shiftBiddingWidgetOnDashboard;
    @FindBy(xpath = "//div[contains(@class,'MuiBox-root css-0')][2]/div[3]/button")
    private WebElement submitBidsButton;

    @Override
    public boolean checkIfTheShiftBiddingWidgetLoaded() throws Exception {
        boolean isLoaded= false;
        if (isElementLoaded(shiftBiddingWidgetOnDashboard, 5)){
            isLoaded = true;
            SimpleUtils.pass("The shift bidding widget is loaded! ");
        } else
            SimpleUtils.report("The shift bidding widget is not loaded! ");
        return isLoaded;
    }

    @Override
    public void clickSubmitBidButton() throws Exception {
        if (isElementLoaded(submitBidsButton, 5)){
            SimpleUtils.pass("The submit bids button is loaded! ");
            clickTheElement(submitBidsButton);
            SimpleUtils.pass("Click submit bid button successfully! ");
        } else
            SimpleUtils.fail("The submit bids button is not loaded! ", false);
    }

    @FindBy(xpath = "//button[contains(text(),'Next')]")
    private WebElement nextButton;

    @Override
    public void clickNextButton() throws Exception {
        if (isElementLoaded(nextButton, 5)){
            SimpleUtils.pass("The next button is loaded! ");
            clickTheElement(nextButton);
            SimpleUtils.pass("Click next button successfully! ");
        } else
            SimpleUtils.fail("The next button is not loaded! ", false);
    }


    @FindBy(xpath = "//span[contains(text(),'Add')]")
    private List<WebElement> addButtons;
    @Override
    public void addAllShiftPatterns() throws Exception {
        if (areListElementVisible(addButtons, 5)){
            while (addButtons.size()>0){
                clickTheElement(addButtons.get(0));
                SimpleUtils.pass("Add one shift pattern successfully! ");
            }
        } else
            SimpleUtils.fail("There is no shift patterns loaded! ", false);
    }

    @FindBy(xpath = "//button[contains(text(),'Submit')]")
    private WebElement submitButton;

    @Override
    public void clickSubmitButton() throws Exception {
        if (isElementLoaded(submitButton, 5)){
            SimpleUtils.pass("The submit button is loaded! ");
            clickTheElement(submitButton);
            SimpleUtils.pass("Click submit button successfully! ");
        } else
            SimpleUtils.fail("The submit button is not loaded! ", false);
    }
}
