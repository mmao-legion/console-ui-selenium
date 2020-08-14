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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
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

    @FindBy(css = "p.estimate-faith-text-vsl")
    private WebElement VSLInfo;

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
            waitForSeconds(5);
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

    @Override
    public LinkedHashMap<String, List<String>> getGFEWorkingHours() throws Exception {
        // SUN,[09:00AM, 05:00PM]
        LinkedHashMap<String, List<String>> GFEHours = new LinkedHashMap<>();
        if (areListElementVisible(weekDaysInGFE, 10)) {
            for (WebElement weekDay : weekDaysInGFE) {
                WebElement day = weekDay.findElement(By.className("weekdays-column-header"));
                List<WebElement> workTimes = weekDay.findElements(By.tagName("input"));
                if (day != null && workTimes != null && workTimes.size() == 2) {
                    List<String> startNEndTime = new ArrayList<>();
                    String startTime = workTimes.get(0).getAttribute("value");
                    String endTime = workTimes.get(1).getAttribute("value");
                    startNEndTime.add(startTime);
                    startNEndTime.add(endTime);
                    GFEHours.put(day.getText(), startNEndTime);
                    SimpleUtils.report("Inbox: Get GFE time for: " + day.getText() + ", GFE time is: " + startTime + " - " + endTime);
                }
            }
        }
        if (GFEHours.size() != 7)
            SimpleUtils.fail("Inbox: Failed to find the week day and working time of GFE!", false);
        return GFEHours;
    }

    @Override
    public String getGFEFirstDayOfWeek() throws Exception {
        // SUN
        String GFETheFirstDayOfWeek = "";
        if (areListElementVisible(weekDaysInGFE, 10)) {
            WebElement day = weekDaysInGFE.get(0).findElement(By.className("weekdays-column-header"));
            GFETheFirstDayOfWeek = day.getText();
        }
        return GFETheFirstDayOfWeek;
    }

    @Override
    public boolean compareGFEWorkingHrsWithRegularWorkingHrs(LinkedHashMap<String, List<String>> GFEWorkingHours,
                                                             LinkedHashMap<String, List<String>> regularHoursFromControl) throws Exception {
        boolean isConsistent = false;
        Iterator itRegular = regularHoursFromControl.keySet().iterator();
        while (itRegular.hasNext()) {
            isConsistent = false;
            String regularKey = itRegular.next().toString();
            List<String> regularValue = regularHoursFromControl.get(regularKey);
            Iterator itGFE = GFEWorkingHours.keySet().iterator();
            while (itGFE.hasNext()) {
                if (isConsistent)
                    break;
                String GFEKey = itGFE.next().toString();
                if (regularKey.contains(GFEKey) ) {
                    List<String> GFEValue = GFEWorkingHours.get(GFEKey);
                    if (GFEValue.size() == 2 && regularValue.size() == 2) {
                        if (GFEValue.get(0).contains(regularValue.get(0).replace(" ", "").toUpperCase())
                                && GFEValue.get(1).contains(regularValue.get(1).replace(" ", "").toUpperCase())) {
                            isConsistent = true;
                            SimpleUtils.report("Regular Working Hours: " + regularKey + "---" + regularValue);
                            SimpleUtils.report("GFE Working Hours: " + GFEKey + "---" + GFEValue);
                            SimpleUtils.report("Are they consistent? " + isConsistent);
                        }
                    }
                }
            }
        }
       return isConsistent;
    }

    @Override
    public void verifyVSLInfo(boolean isVSLTurnOn) throws Exception {
        if (isVSLTurnOn) {
            if (isElementLoaded(VSLInfo, 5) &&
                    VSLInfo.getText().contains("Team members will be informed regarding opting in to the Volntary Standby List"))
                SimpleUtils.pass("Inbox: VSL info \"Team members will be informed regarding opting in to the Voluntary Standby List.\" is loaded successfully when VSL is turned on");
             else
                SimpleUtils.fail("Inbox: VSL info failed to load", false);
        } else {
            if (!isElementLoaded(VSLInfo, 5))
                SimpleUtils.pass("Inbox: No message \"Team members will be informed regarding opting in to the Volntary Standby List.\" loaded when VSL is turned off");
            else
                SimpleUtils.fail("Inbox: VSL info still displays although VSL is turned off",false);
        }
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
    @FindBy(css = ".gfe-send-to-select")
    private WebElement sendToDiv;
    @FindBy(css = ".gfe-send-to-select .selector-dropdown span")
    private List<WebElement> tmOptions;//.findElement(By.cssSelector("selector-input"))
    @Override
    public void sendToTM(String nickName) throws Exception {
        if (isElementLoaded(sendToDiv,5)){
            click(sendToDiv);
            for (WebElement element: tmOptions){
                scrollToElement(element);
                String s = element.getText();
                if (element.getText().contains(nickName)){
                    click(element);
                    break;
                }
            }
            //sendToDiv.findElement(By.cssSelector("input")).sendKeys(nickName);
        }
    }
}
