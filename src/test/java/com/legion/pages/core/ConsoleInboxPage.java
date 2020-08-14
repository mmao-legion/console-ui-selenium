package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.InboxPage;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.apache.bcel.generic.Visitor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.ClickElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import javax.xml.crypto.dsig.SignatureMethod;
import java.util.List;
import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleInboxPage  extends BasePage implements InboxPage {

    public ConsoleInboxPage(){
        PageFactory.initElements(getDriver(), this);
    }

    //Added by Nora

    //Added by Julie
    final static String consoleInboxMenuItemText = "Inbox";

    @FindBy(css = "a[ng-click=\"createAnnouncement()\"]")
    private WebElement createAnnouncementIcon;

    @FindBy(css = ".weekdays-column")
    private List<WebElement> weekDaysInGFE;

    @FindBy(className = "console-navigation-item")
    private List<WebElement> consoleNavigationMenuItems;

    @FindBy(css = "select[ng-attr-id=\"{{$ctrl.inputName}}\"]")
    private WebElement announcementType;

    @Override
    public void clickOnInboxConsoleMenuItem() throws Exception {
        if (consoleNavigationMenuItems.size() != 0) {
            WebElement consoleInboxMenuElement = SimpleUtils.getSubTabElement(consoleNavigationMenuItems, consoleInboxMenuItemText);
            clickTheElement(consoleInboxMenuElement);
            SimpleUtils.pass("'Inbox' Console Menu loaded successfully!");
        } else
            SimpleUtils.fail("'Inbox' Console Menu failed to load!", false);
    }

    @Override
    public void createGFEAnnouncement() throws Exception {
        if (isElementLoaded(createAnnouncementIcon,10)) {
            clickTheElement(createAnnouncementIcon);
            if (isElementLoaded(announcementType,5)) {
                selectByVisibleText(announcementType, "Good Faith Estimate");
                if (areListElementVisible(weekDaysInGFE,5))
                    SimpleUtils.pass("Inbox: A new announcement with type \"Good Faith Estimate\" is selected successfully");
                else
                    SimpleUtils.fail("Inbox: A new announcement with type \"Good Faith Estimate\" failed to select",false);
            } else
                SimpleUtils.fail("Inbox: Create New Announcement window failed to load after clicking + icon",false);
        } else
            SimpleUtils.fail("Inbox: Create Announcement + icon failed to load",false);
    }

    //Added by Marym


    @FindBy(css = ".new-announcement-modal")
    private WebElement newAnnouncementModal;

    @FindBy(css = "[ng-if=\"options.canSendGFE\"]")
    private WebElement announcementTypeSection;

    @FindBy(css = "[ng-class=\"{'mt-15': options.canSendGFE}\"]")
    private WebElement sendToSection;

    @FindBy(css = "div.selector-container.ng-isolate-scope.multiple remove-button")
    private WebElement sendToTextBox;

    @FindBy(css = "[placeholder=\"Title goes here\"]")
    private WebElement titleArea;

    @FindBy(css = "[placeholder=\"Your message goes here...\"]")
    private WebElement messageArea;

    @FindBy(css = "[ng-click=\"callCancelCallback()\"]")
    private WebElement cancelButton;

    @FindBy(css = "[ng-click=\"callOkCallback()\"]")
    private WebElement SaveButton;

    @Override
    public void checkCreateAnnouncementPageWithGFETurnOnOrTurnOff(boolean isTurnOn) throws Exception {
        if (isElementLoaded(createAnnouncementIcon,10)) {
            clickTheElement(createAnnouncementIcon);
            if(newAnnouncementModal !=null){
                // check Create new announcement text
                WebElement createNewAnnouncementText = newAnnouncementModal.findElement(By.tagName("h2"));
                if (createNewAnnouncementText != null && createNewAnnouncementText.getText().equals("Create new announcement")){
                    SimpleUtils.pass("Inbox: Create new announcement text display successfully");
                }
                else{
                    SimpleUtils.fail("Inbox: Create new announcement text failed to load",true);
                }

                //check Announcement Type
                if (isTurnOn){
                    if (announcementTypeSection != null){
                        WebElement announcementTypeText = announcementTypeSection.findElement(By.tagName("label"));
                       if (announcementTypeText != null && announcementTypeText.getText().equals("Announcement type:") && announcementType !=null){
                            SimpleUtils.pass("Inbox: Announcement type display successfully");

                            Select dropdown = new Select(announcementType);
                            List<WebElement> allAnnouncementTypes= dropdown.getOptions();

                            if (allAnnouncementTypes != null && allAnnouncementTypes.size() >1){
                                if(allAnnouncementTypes.get(0).getText().equals("Message") && allAnnouncementTypes.get(1).getText().equals("Good Faith Estimate")){
                                    SimpleUtils.pass("Inbox: Announcement type: Message & Good Faith Estimate shows correctly, Message is the first option, Good Faith Estimate is the second option.");
                                }
                                else{
                                    SimpleUtils.fail("Inbox: Announcement type: Message & Good Faith Estimate failed to load",true);
                                }
                            }
                            else{
                                SimpleUtils.fail("Inbox: Announcement types selector failed to load",true);
                            }
                        }
                        else{
                            SimpleUtils.fail("Inbox: Announcement type failed to load",true);
                        }
                    }
                }
                else {
                    if(announcementTypeSection == null){
                        SimpleUtils.pass("Inbox: Announcement type is not displayed, because GFE is turned on");
                    }
                    else{
                        SimpleUtils.fail("Inbox: Announcement type should not display, because GFE is turned on",true);
                    }
                }

                //check Send to:
                if (sendToSection !=null){
                    WebElement sendToText = sendToSection.findElement(By.tagName("label"));
                    if (sendToText !=null && sendToText.getText().equals("Send to:") && sendToTextBox != null){
                        SimpleUtils.pass("Inbox: Send to display successfully");
                    }
                    else{
                        SimpleUtils.fail("Inbox: Send to text or textbox failed to load",true);
                    }

                }
                else{
                    SimpleUtils.fail("Inbox: Send to failed to load",true);
                }

                //check Message section
                List<WebElement> messageSection = newAnnouncementModal.findElements(By.className("mt-15"));
                if (messageSection!=null && messageSection.size()>0){
                    WebElement messageText = messageSection.get(1).findElement(By.tagName("label"));
                    if (messageText !=null && messageText.getText().equals("Message:") && titleArea !=null && messageArea != null){
                            SimpleUtils.pass("Inbox: Message display successfully");
                        }
                    else{
                        SimpleUtils.fail("Inbox: Message text or Message textarea failed to load",true);
                    }
                }
                else{
                    SimpleUtils.fail("Inbox: Message failed to load",true);
                }

                //check Cancel and Send button
                if(cancelButton != null){
                    SimpleUtils.pass("Inbox: Cancel button display successfully");
                }
                else{
                    SimpleUtils.fail("Inbox: Cancel button failed to load",true);
                }
                if (SaveButton !=null){
                    SimpleUtils.pass("Inbox: Save button display successfully");
                }
                else{
                    SimpleUtils.fail("Inbox: Save button failed to load",true);
                }
            }
            else{
                SimpleUtils.fail("Inbox: Create Announcement page failed to load",false);

            }
        } else
            SimpleUtils.fail("Inbox: Create Announcement + icon failed to load",false);
    }


    //Added by Haya

}
