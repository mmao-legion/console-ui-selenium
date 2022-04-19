package com.legion.pages.core.schedule;

import com.legion.pages.BasePage;
import com.legion.pages.NewShiftPage;
import com.legion.tests.core.ScheduleTestKendraScott2;
import com.legion.utils.Constants;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.legion.tests.core.ScheduleTestKendraScott2.staffingOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.legion.utils.MyThreadLocal.*;
import static com.legion.utils.MyThreadLocal.setWorkerLocation;

public class ConsoleNewShiftPage extends BasePage implements NewShiftPage{
    public ConsoleNewShiftPage() {
        PageFactory.initElements(getDriver(), this);
    }

    private static HashMap<String, String> parameterMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
    private static HashMap<String, String> propertySearchTeamMember = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SearchTeamMember.json");
    private static HashMap<String, String> propertyCustomizeMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ScheduleCustomizeNewShift.json");

    @FindBy(css = ".modal-content")
    private WebElement customizeNewShift;

    @FindBy(css = "div.lgn-time-slider-notch-selector-end")
    private WebElement sliderNotchEnd;

    @FindBy(css = "div.lgn-time-slider-notch.droppable")
    private List<WebElement> sliderDroppableCount;

    @FindBy(xpath = "//div[contains(@class,'lgn-time-slider-notch-selector-end')]/following-sibling::div[1]")
    private WebElement customizeShiftEnddayLabel;

    @FindBy(css = "div.lgn-time-slider-notch-selector-start")
    private WebElement sliderNotchStart;

    @FindBy(xpath = "//div[contains(@class,'lgn-time-slider-notch-selector-start')]/following-sibling::div[1]")
    private WebElement customizeShiftStartdayLabel;

    @FindBy(css = ".tma-staffing-option-radio-button")
    private List<WebElement> radioBtnStaffingOptions;

    @FindBy(css = ".tma-staffing-option-text-title")
    private List<WebElement> radioBtnShiftTexts;

    @FindBy(css = "button.tma-action")
    private WebElement btnSave;

    @FindBy(css = "[ng-click=\"handleNext()\"]")
    private WebElement btnSaveOnNewCreateShiftPage;

    public void customizeNewShiftPage() throws Exception
    {
        if(isElementLoaded(customizeNewShift,15))
        {
            SimpleUtils.pass("Customize New Shift Page loaded Successfully!");
        }
        else
        {
            SimpleUtils.fail("Customize New Shift Page not loaded Successfully!",false);
        }
    }

    public void moveSliderAtSomePoint(String shiftTime, int shiftStartingCount, String startingPoint) throws Exception
    {
        if(startingPoint.equalsIgnoreCase("End")){
            if(isElementLoaded(sliderNotchEnd,10) && sliderDroppableCount.size()>0){
                SimpleUtils.pass("Shift timings with Sliders loaded on page Successfully for End Point");
                for(int i= shiftStartingCount; i<= sliderDroppableCount.size();i++){
                    if(i == (shiftStartingCount + Integer.parseInt(shiftTime))){
                        WebElement element = getDriver().findElement(By.cssSelector("div.lgn-time-slider-notch.droppable:nth-child("+(i+2)+")"));
                        mouseHoverDragandDrop(sliderNotchEnd,element);
                        WebElement ele = getDriver().findElement(By.xpath("//div[contains(@class,'lgn-time-slider-notch-selector-end')]/following-sibling::div[1]"));
                        String txt = ele.getAttribute("innerHTML");
                        if(customizeShiftEnddayLabel.getAttribute("class").contains("PM")){
                            MyThreadLocal.setScheduleHoursEndTime(customizeShiftEnddayLabel.getText() + ":00PM");
                            break;
                        }else{
                            MyThreadLocal.setScheduleHoursEndTime(customizeShiftEnddayLabel.getText() + ":00AM");
                            break;
                        }
                    }
                }

            }else{
                SimpleUtils.fail("Shift timings with Sliders not loading on page Successfully", false);
            }
        }else if(startingPoint.equalsIgnoreCase("Start")){
            if(isElementLoaded(sliderNotchStart,10) && sliderDroppableCount.size()>0){
                SimpleUtils.pass("Shift timings with Sliders loaded on page Successfully for Starting point");
                for(int i= shiftStartingCount; i<= sliderDroppableCount.size();i++){
                    if(i == (shiftStartingCount + Integer.parseInt(shiftTime))){
                        WebElement element = getDriver().findElement(By.cssSelector("div.lgn-time-slider-notch.droppable:nth-child("+(i+2)+")"));
                        mouseHoverDragandDrop(sliderNotchStart,element);
                        if(customizeShiftStartdayLabel.getAttribute("class").contains("AM")){
                            MyThreadLocal.setScheduleHoursStartTime(customizeShiftStartdayLabel.getText() + ":00AM");
                            break;
                        }else{
                            MyThreadLocal.setScheduleHoursStartTime(customizeShiftStartdayLabel.getText() + ":00PM");
                            break;
                        }
                    }
                }

            }else{
                SimpleUtils.fail("Shift timings with Sliders not loading on page Successfully", false);
            }
        }
    }


    @FindBy(css = "div.react-select__placeholder")
    private WebElement assignmentDropDownOnNewCreateShiftPage;
    @FindBy(className = "react-select__option")
    private List<WebElement> assignmentOptionsInDropDownList;
    public void clickRadioBtnStaffingOption(String staffingOption) throws Exception {
        if (areListElementVisible(radioBtnStaffingOptions, 5)
                && areListElementVisible(radioBtnShiftTexts, 5)) {
            boolean flag = false;
            int index = -1;
            if (radioBtnStaffingOptions.size() != 0 && radioBtnShiftTexts.size() != 0 &&
                    radioBtnStaffingOptions.size() == radioBtnShiftTexts.size()) {

                for (WebElement radioBtnShiftText : radioBtnShiftTexts) {
                    index = index + 1;
                    if (radioBtnShiftText.getText().contains(staffingOption)) {
                        click(radioBtnStaffingOptions.get(index));
                        SimpleUtils.pass(radioBtnShiftText.getText() + "Radio Button clicked Successfully!");
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    SimpleUtils.fail("No Radio Button Selected!", false);
                }

            } else {
                SimpleUtils.fail("Staffing option Radio Button is not clickable!", false);
            }
        } else if (isElementLoaded(assignmentDropDownOnNewCreateShiftPage, 5)) {
            click(assignmentDropDownOnNewCreateShiftPage);
            SimpleUtils.pass("Assignment button clicked Successfully");
            if (assignmentOptionsInDropDownList.size() > 0) {
                for (WebElement assignmentOptions : assignmentOptionsInDropDownList) {
                    String option = assignmentOptions.getText().toLowerCase();
                    if (staffingOption.toLowerCase().contains("assign")) {
                        MyThreadLocal.setAssignTMStatus(true);
                    } else
                        MyThreadLocal.setAssignTMStatus(false);
                    if (option.contains(staffingOption.toLowerCase())) {
                        click(assignmentOptions);
                        SimpleUtils.pass(option + " been selected Successfully!");
                        break;
                    } else if(!option.contains("auto") && !staffingOption.toLowerCase().contains("auto")) {
                        click(assignmentOptions);
                        SimpleUtils.pass(option + " been selected Successfully!");
                        break;
                    }else {
                        SimpleUtils.report(option + " is not selected Successfully!");
                    }
                }
            } else {
                SimpleUtils.fail("Work Roles size are empty", false);
            }
        } else
            SimpleUtils.fail("Assignment options fail to load on create shift page! ", false);

    }

    public void clickOnCreateOrNextBtn() throws Exception {
        if (isElementLoaded(btnSave, 10)) {
            click(btnSave);
            SimpleUtils.pass("Create or Next Button clicked Successfully on Customize new Shift page!");
        } if (isElementLoaded(btnSaveOnNewCreateShiftPage, 5)) {
            click(btnSaveOnNewCreateShiftPage);
            SimpleUtils.pass("Create or Next Button clicked Successfully on Customize new Shift page!");
        }else {
            SimpleUtils.fail("Create or Next Button not clicked Successfully on Customize new Shift page!", false);
        }
    }

    @FindBy(css = ".tab-set .select .tab-label-text")
    private WebElement selectRecommendedOption;

    @FindBy(css = "[ng-if=\"hasBestWorkers()\"] div.tma-scroll-table tr")
    private List<WebElement> recommendedScrollTable;

    @FindBy(css = ".tma-search-field-input-text")
    private WebElement textSearch;

    @FindBy(css = "div.tab-label")
    private List<WebElement> btnSearchteamMember;

    @FindBy(css = ".sch-search")
    private WebElement searchIcon;

    @FindBy(xpath = "//div[@class='worker-edit-availability-status']")
    private List<WebElement> scheduleStatus;

    @FindBy(xpath="//span[contains(text(),'Best')]")
    private List<WebElement> scheduleBestMatchStatus;

    @FindBy(css = "td.table-field.action-field.tr>div")
    private List<WebElement> radionBtnSelectTeamMembers;

    @FindBy(css = "div.worker-edit-search-worker-name")
    private List<WebElement> searchWorkerName;

    @FindBy(xpath="//div[@class='tma-search-action']/following-sibling::div[1]//div[@class='worker-edit-availability-status']")
    private List<WebElement> scheduleSearchTeamMemberStatus;

    @FindBy(css="div.tma-empty-search-results")
    private WebElement scheduleNoAvailableMatchStatus;

    @FindBy(xpath="//div[@class='tma-search-action']/following-sibling::div[1]//div[@ng-click='selectAction($event, worker)']")
    private List<WebElement> radionBtnSearchTeamMembers;

    @FindBy(xpath="//div[@class='tma-search-action']/following-sibling::div[1]//div[@class='worker-edit-search-worker-name']/following-sibling::div")
    private List<WebElement> searchWorkerRole;

    @FindBy(xpath="//div[@class='tma-search-action']/following-sibling::div[1]//div[@class='worker-edit-search-worker-name']/following-sibling::div[2]")
    private List<WebElement> searchWorkerLocation;

    @FindBy(css = "button.lgn-action-button-success")
    private WebElement btnAssignAnyway;

    @FindBy(css = "div.lgn-alert-modal")
    private WebElement popUpScheduleOverlap;

    @FindBy(css = "div.lgn-alert-message")
    private WebElement alertMessage;

    @FindBy(css="button.tma-action.sch-save")
    private WebElement btnOffer;

    @FindBy(css = "button.lgn-dropdown-button:nth-child(1)")
    private WebElement btnWorkRole;

    @FindBy(css = " [ng-click=\"selectChoice($event, choice)\"]")
    private List<WebElement> listWorkRoles;

    @FindBy(css = "[label=\"Create New Shift\"]")
    private WebElement createNewShiftWeekView;

    @FindBy(className = "week-day-multi-picker-day")
    private List<WebElement> weekDays;

    public void verifySelectTeamMembersOption() throws Exception {
        waitForSeconds(3);
        if (isElementLoaded(selectRecommendedOption, 20)) {
            clickTheElement(selectRecommendedOption);
            waitForSeconds(3);
            if (areListElementVisible(recommendedScrollTable, 5)) {
                String[] txtRecommendedOption = selectRecommendedOption.getText().replaceAll("\\p{P}", "").split(" ");
                if (Integer.parseInt(txtRecommendedOption[2]) == 0) {
                    if (getDriver().getCurrentUrl().contains(parameterMap.get("KendraScott2_Enterprise"))) {
                        searchText(propertySearchTeamMember.get("AssignTeamMember"));
                    } else if (getDriver().getCurrentUrl().contains(parameterMap.get("Coffee_Enterprise"))) {
                        searchText(propertySearchTeamMember.get("TeamLCMember"));
                    }
                    SimpleUtils.pass(txtRecommendedOption[0] + " Option selected By default for Select Team member option");
                } else {
                    boolean  scheduleBestMatchStatus = getScheduleBestMatchStatus();
                    if(scheduleBestMatchStatus){
                        SimpleUtils.pass(txtRecommendedOption[0] + " Option selected By default for Select Team member option");
                    }else{
                        if(areListElementVisible(btnSearchteamMember,5)){
                            click(btnSearchteamMember.get(0));
                            if (getDriver().getCurrentUrl().contains(parameterMap.get("KendraScott2_Enterprise"))) {
                                searchText(propertySearchTeamMember.get("AssignTeamMember"));
                            } else if (getDriver().getCurrentUrl().contains(parameterMap.get("Coffee_Enterprise"))) {
                                searchText(propertySearchTeamMember.get("TeamLCMember"));
                            }
                        }

                    }

                }
            } else if (areListElementVisible(btnSearchteamMember,5)) {
                click(btnSearchteamMember.get(0));
                if (getDriver().getCurrentUrl().contains(parameterMap.get("KendraScott2_Enterprise"))) {
                    searchText(propertySearchTeamMember.get("AssignTeamMember"));
                } else if (getDriver().getCurrentUrl().contains(parameterMap.get("Coffee_Enterprise"))) {
                    searchText(propertySearchTeamMember.get("TeamLCMember"));
                }
            }
        } else {
            SimpleUtils.fail("Select Team member option and Recommended options are not available on page", false);
        }
    }

    public void searchText(String searchInput) throws Exception {
        String[] searchAssignTeamMember = searchInput.split(",");
        if (isElementLoaded(textSearch, 10) && isElementLoaded(searchIcon, 10)) {
            for (int i = 0; i < searchAssignTeamMember.length; i++) {
                String[] searchTM = searchAssignTeamMember[i].split("\\.");
                textSearch.sendKeys(searchTM[0]);
                click(searchIcon);
                if (getScheduleStatus()) {
                    setTeamMemberName(searchAssignTeamMember[i]);
                    break;
                } else {
                    textSearch.clear();
                }
            }

        } else if (isElementLoaded(textSearchOnNewCreateShiftPage, 5)) {
            for (int i = 0; i < searchAssignTeamMember.length; i++) {
                String[] searchTM = searchAssignTeamMember[i].split("\\.");
                textSearchOnNewCreateShiftPage.sendKeys(searchTM[0]);
                waitForSeconds(3);
                if (getScheduleStatus()) {
                    setTeamMemberName(searchAssignTeamMember[i]);
                    break;
                } else {
                    textSearchOnNewCreateShiftPage.clear();
                }
            }
        }else {
            SimpleUtils.fail("Search text not editable and icon are not clickable", false);
        }

    }

    public boolean getScheduleBestMatchStatus()throws Exception {
        boolean ScheduleBestMatchStatus = false;
        if(areListElementVisible(scheduleStatus,5) || scheduleBestMatchStatus.size()!=0 && radionBtnSelectTeamMembers.size() == scheduleStatus.size() && searchWorkerName.size()!=0){
            for(int i=0; i<scheduleStatus.size();i++){
                if(scheduleBestMatchStatus.get(i).getText().contains("Best")
                        || scheduleStatus.get(i).getText().contains("Unknown") || scheduleStatus.get(i).getText().contains("Available")){
                    //if(searchWorkerName.get(i).getText().contains("Gordon.M") || searchWorkerName.get(i).getText().contains("Jayne.H")){
                    click(radionBtnSelectTeamMembers.get(i));
                    setTeamMemberName(searchWorkerName.get(i).getText());
                    ScheduleBestMatchStatus = true;
                    break;
                    //}
                }
            }
        }else{
            SimpleUtils.fail("Not able to found Available status in SearchResult", false);
        }

        return ScheduleBestMatchStatus;
    }


    public boolean getScheduleStatus() throws Exception {
        boolean ScheduleStatus = false;
//		waitForSeconds(5);
        if(areListElementVisible(scheduleSearchTeamMemberStatus,10) || isElementLoaded(scheduleNoAvailableMatchStatus,10)){
            for(int i=0; i<scheduleSearchTeamMemberStatus.size();i++){
                if(scheduleSearchTeamMemberStatus.get(i).getText().contains("Available")
                        || scheduleSearchTeamMemberStatus.get(i).getText().contains("Unknown")){
                    if(scheduleSearchTeamMemberStatus.get(i).getText().contains("Minor")){
                        click(radionBtnSearchTeamMembers.get(i));
                        ScheduleStatus = true;
                        break;
                    } else if(scheduleSearchTeamMemberStatus.get(i).getText().contains("Role Violation")){
                        click(radionBtnSearchTeamMembers.get(i));
                        displayAlertPopUpForRoleViolation();
                        setWorkerRole(searchWorkerRole.get(i).getText());
                        setWorkerLocation(searchWorkerLocation.get(i).getText());
//                        setWorkerShiftTime(searchWorkerSchShiftTime.getText());
//                        setWorkerShiftDuration(searchWorkerSchShiftDuration.getText());
                        ScheduleStatus = true;
                        break;
                    } else if(scheduleSearchTeamMemberStatus.get(i).getText().contains("Will trigger")) {
                        clickTheElement(radionBtnSearchTeamMembers.get(i));
                        if (isElementLoaded(btnAssignAnyway, 10) && btnAssignAnyway.getText().toUpperCase().contains("ASSIGN ANYWAY")) {
                            clickTheElement(btnAssignAnyway);
                            waitUntilElementIsInVisible(btnAssignAnyway);
                        }
                        ScheduleStatus = true;
                        break;
                    } else {
                        click(radionBtnSearchTeamMembers.get(i));
                        setWorkerRole(searchWorkerRole.get(i).getText());
                        setWorkerLocation(searchWorkerLocation.get(i).getText());
//					setWorkerShiftTime(searchWorkerSchShiftTime.getText());
//					setWorkerShiftDuration(searchWorkerSchShiftDuration.getText());
                        ScheduleStatus = true;
                        break;
                    }
                }
            }
        } else if(areListElementVisible(searchResultsOnNewCreateShiftPage,10)){
            for(int i=0; i<searchResultsOnNewCreateShiftPage.size();i++){
                List<WebElement> allStatus= searchResultsOnNewCreateShiftPage.get(i).findElements(By.cssSelector(".MuiGrid-grid-xs-3 .MuiTypography-body2"));
                List<WebElement> tmInfo = searchResultsOnNewCreateShiftPage.get(i).findElements(By.cssSelector("p.MuiTypography-body1"));
                String tmAllStatus = "";
                for (WebElement status: allStatus) {
                    tmAllStatus = tmAllStatus + " "+status.getText();
                }
                if(tmAllStatus.contains("Available")
                        || tmAllStatus.contains("Unknown")){
                    List<WebElement> assignAndOfferButtons = searchResultsOnNewCreateShiftPage.get(i).findElements(By.tagName("button"));
//                    if (MyThreadLocal.getAssignTMStatus()) {
//                        clickTheElement(assignAndOfferButtons.get(0));
//                    } else
//                        clickTheElement(assignAndOfferButtons.get(1));
//                    if (isElementEnabled(btnAssignAnyway, 5)) {
//                        click(btnAssignAnyway);
//                    }
                    if(tmAllStatus.contains("Minor")){
                        if (MyThreadLocal.getAssignTMStatus()) {
                            clickTheElement(assignAndOfferButtons.get(0));
                        } else
                            clickTheElement(assignAndOfferButtons.get(1));
                        ScheduleStatus = true;
                        break;
                    } else if(tmAllStatus.contains("Role Violation")){
                        if (MyThreadLocal.getAssignTMStatus()) {
                            clickTheElement(assignAndOfferButtons.get(0));
                        } else
                            clickTheElement(assignAndOfferButtons.get(1));
                        displayAlertPopUpForRoleViolation();
                        setWorkerRole(tmInfo.get(1).getText());
                        setWorkerLocation(tmInfo.get(2).getText());
//                        setWorkerShiftTime(searchWorkerSchShiftTime.getText());
//                        setWorkerShiftDuration(searchWorkerSchShiftDuration.getText());
                        ScheduleStatus = true;
                        break;
                    } else if(tmAllStatus.contains("Will trigger")) {
                        if (MyThreadLocal.getAssignTMStatus()) {
                            clickTheElement(assignAndOfferButtons.get(0));
                        } else
                            clickTheElement(assignAndOfferButtons.get(1));
                        if (isElementEnabled(btnAssignAnyway, 5)) {
                            click(btnAssignAnyway);
                        }
                        ScheduleStatus = true;
                        break;
                    } else {
                        if (MyThreadLocal.getAssignTMStatus()) {
                            clickTheElement(assignAndOfferButtons.get(0));
                        } else
                            clickTheElement(assignAndOfferButtons.get(1));
                        setWorkerRole(tmInfo.get(1).getText());
                        setWorkerLocation(tmInfo.get(2).getText());
//					setWorkerShiftTime(searchWorkerSchShiftTime.getText());
//					setWorkerShiftDuration(searchWorkerSchShiftDuration.getText());
                        ScheduleStatus = true;
                        break;
                    }
                }
            }
        }else{
            SimpleUtils.fail("Not able to found Available status in SearchResult", false);
        }

        return ScheduleStatus;
    }

    @Override
    public void displayAlertPopUpForRoleViolation() throws Exception{
        String msgAlert = null;
        if(isElementLoaded(popUpScheduleOverlap,5)){
            if(isElementLoaded(alertMessage,5)){
                msgAlert = alertMessage.getText();
                if(isElementLoaded(btnAssignAnyway,5)){
                    SimpleUtils.pass("Role violation messsage as such as " + msgAlert);
                    click(btnAssignAnyway);
                }else{
                    SimpleUtils.fail("Assign Anyway button not displayed on the page",false);
                }
            }else{
                SimpleUtils.fail("Alert message for not displayed",false);
            }
        }else{
            SimpleUtils.fail("Role Violation pop up not displayed",false);
        }
    }

    public void clickOnOfferOrAssignBtn() throws Exception{
        if(isElementLoaded(btnOffer,5)){
            scrollToElement(btnOffer);
            waitForSeconds(3);
            clickTheElement(btnOffer);
            if (isElementLoaded(btnAssignAnyway, 5) && btnAssignAnyway.getText().toUpperCase().equals("ASSIGN ANYWAY")) {
                clickTheElement(btnAssignAnyway);
            }
        }else if (isElementLoaded(btnSaveOnNewCreateShiftPage, 5)) {
            scrollToElement(btnSaveOnNewCreateShiftPage);
            waitForSeconds(3);
            clickTheElement(btnSaveOnNewCreateShiftPage);
            SimpleUtils.pass("Create or Next Button clicked Successfully on Customize new Shift page!");
            if (areListElementVisible(buttonsOnWarningMode, 10)) {
                click(buttonsOnWarningMode.get(1));
            }
        }else{
            SimpleUtils.fail("Offer Or Assign Button is not clickable", false);
        }
    }


    @FindBy(css = "[id=\"workRole\"] div.react-select__placeholder")
    private WebElement workRoleOnNewShiftPage;

    @FindBy(className = "react-select__option")
    private List<WebElement> dropDownListOnNewCreateShiftPage;
    public void selectWorkRole(String workRoles) throws Exception {
        if (isElementLoaded(btnWorkRole, 10)) {
            clickTheElement(btnWorkRole);
            SimpleUtils.pass("Work Role button clicked Successfully");
            if (listWorkRoles.size() > 0) {
                for (WebElement listWorkRole : listWorkRoles) {
                    if (listWorkRole.getText().toLowerCase().contains(workRoles.toLowerCase())) {
                        click(listWorkRole);
                        SimpleUtils.pass("Work Role " + workRoles + "selected Successfully");
                        break;
                    } else {
                        SimpleUtils.report("Work Role " + workRoles + " not selected");
                    }
                }
            } else {
                SimpleUtils.fail("Work Roles size are empty", false);
            }
        } else if (isElementLoaded(workRoleOnNewShiftPage, 5)) {
            click(workRoleOnNewShiftPage);
            SimpleUtils.pass("Work Role button clicked Successfully");
            if (dropDownListOnNewCreateShiftPage.size() > 0) {
                for (WebElement listWorkRole : dropDownListOnNewCreateShiftPage) {
                    if (listWorkRole.getText().toLowerCase().contains(workRoles.toLowerCase())) {
                        click(listWorkRole);
                        SimpleUtils.pass("Work Role " + workRoles + "selected Successfully");
                        break;
                    } else {
                        SimpleUtils.report("Work Role " + workRoles + " not selected");
                    }
                }
            } else {
                SimpleUtils.fail("Work Roles size are empty", false);
            }
        } else {
            SimpleUtils.fail("Work Role button is not clickable", false);
        }
    }

    @Override
    public void addOpenShiftWithDefaultTime(String workRole) throws Exception {
        if (isElementLoaded(createNewShiftWeekView)) {
            click(createNewShiftWeekView);
            selectWorkRole(workRole);
            clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
            clickOnCreateOrNextBtn();
            Thread.sleep(2000);
        } else
            SimpleUtils.fail("Day View Schedule edit mode, add new shift button not found for Week Day: '" +
                    getActiveWeekText() + "'", false);
    }

    @Override
    public void addOpenShiftWithDefaultTime(String workRole, String location) throws Exception {
        if (isElementLoaded(createNewShiftWeekView)) {
            click(createNewShiftWeekView);
            selectWorkRole(workRole);
            clickRadioBtnStaffingOption(staffingOption.OpenShift.getValue());
            if (isLocationLoaded())
                selectLocation(location);
            moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME_3"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
            moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME_3"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
            clickOnCreateOrNextBtn();
            if (ifWarningModeDisplay() && isElementLoaded(okBtnInWarningMode,5))
                click(okBtnInWarningMode);
            if (areListElementVisible(buttonsOnWarningMode, 5)) {
                clickTheElement(buttonsOnWarningMode.get(0));
            }
            Thread.sleep(2000);
        } else
            SimpleUtils.fail("Day View Schedule edit mode, add new shift button not found for Week Day: '" +
                    getActiveWeekText() + "'", false);
    }


    @FindBy(css = "[attr-id=\"location\"] button.lgn-dropdown-button")
    private WebElement btnLocation;

    @FindBy(css = "[attr-id=\"location\"] [ng-click=\"selectChoice($event, choice)\"]")
    private List<WebElement> listLocations;


    public boolean isLocationLoaded() throws Exception {
        if (isElementLoaded(btnLocation, 20))
            return true;
        else if (isElementLoaded(btnChildLocationOnNewCreateShiftPage))
            return true;
        else
            return false;
    }

    public void selectLocation(String location) throws Exception {
        if (isElementLoaded(btnLocation, 10)) {
            click(btnLocation);
            SimpleUtils.pass("Location button clicked Successfully");
            if (listLocations.size() > 1) {
                for (WebElement listWorkRole : listLocations) {
                    if (listWorkRole.getText().toLowerCase().contains(location.toLowerCase())) {
                        click(listWorkRole);
                        SimpleUtils.pass("Location " + location + "selected Successfully");
                        break;
                    } else {
                        SimpleUtils.report("Location " + location + " not selected");
                    }
                }
            } else {
                SimpleUtils.fail("Location size are empty", false);
            }
        } else if (isElementLoaded(btnChildLocationOnNewCreateShiftPage, 5)) {
            click(btnChildLocationOnNewCreateShiftPage);
            SimpleUtils.pass("Location button clicked Successfully");
            if (dropDownListOnNewCreateShiftPage.size() > 1) {
                for (WebElement childLocation : dropDownListOnNewCreateShiftPage) {
                    if (childLocation.getText().toLowerCase().contains(location.toLowerCase())) {
                        click(childLocation);
                        SimpleUtils.pass("Location " + location + "selected Successfully");
                        break;
                    } else {
                        SimpleUtils.report("Location " + location + " not selected");
                    }
                }
            } else {
                SimpleUtils.fail("Location size are empty", false);
            }
        }else {
            SimpleUtils.fail("Work Role button is not clickable", false);
        }

    }

    @FindBy(css = "div.lgn-alert-modal")
    private WebElement warningMode;

    @FindBy(css = "span.lgn-alert-message")
    private WebElement warningMessagesInWarningMode;

    @FindBy(className = "lgn-action-button-success")
    private WebElement okBtnInWarningMode;

    @Override
    public boolean ifWarningModeDisplay() throws Exception {
        if(isElementLoaded(warningMode, 5)) {
            SimpleUtils.pass("Warning mode is loaded successfully");
            return true;
        } else if (isElementLoaded(newCreateShiftModal, 5)) {
            SimpleUtils.pass("Warning mode is loaded successfully");
            return true;
        }else {
            SimpleUtils.report("Warning mode fail to load");
            return false;
        }
    }

    @Override
    public void addOpenShiftWithFirstDay(String workRole) throws Exception {
        if (isElementLoaded(createNewShiftWeekView, 10)) {
            click(createNewShiftWeekView);
            selectWorkRole(workRole);
            clearAllSelectedDays();
            if (areListElementVisible(weekDays, 5) && weekDays.size() > 0) {
                clickTheElement(weekDays.get(0));
            } else if (areListElementVisible(weekDaysInNewCreateShiftPage, 5)
                    && weekDaysInNewCreateShiftPage.size() ==7) {
                clickTheElement(weekDaysInNewCreateShiftPage.get(0)
                        .findElement(By.cssSelector(".MuiButtonBase-root")).findElement(By.tagName("input")));
            }
            clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
            clickOnCreateOrNextBtn();
        } else
            SimpleUtils.fail("Day View Schedule edit mode, add new shift button not found for Week Day: '" +
                    getActiveWeekText() + "'", false);
    }

    @FindBy(css = "[class*=\"MuiGrid-root MuiGrid-item MuiGrid-grid-xs-10\"] [class*=\"MuiGrid-root MuiGrid-item MuiGrid-grid-xs-2\"]")
    private List<WebElement> weekDaysInNewCreateShiftPage;
    @FindBy(css = ".MuiGrid-container .MuiFormHelperText-root")
    private WebElement warningMessageForSelectDays;
    public void clearAllSelectedDays() throws Exception {
        if (areListElementVisible(weekDays, 5) && weekDays.size() == 7) {
            for (WebElement weekDay : weekDays) {
                if (weekDay.getAttribute("class").contains("week-day-multi-picker-day-selected")) {
                    click(weekDay);
                }
            }
        } else if (areListElementVisible(weekDaysInNewCreateShiftPage, 5)
                && weekDaysInNewCreateShiftPage.size() == 7) {
            for (WebElement weekDay : weekDaysInNewCreateShiftPage) {
                if (weekDay.findElement(By.cssSelector(".MuiButtonBase-root")).getAttribute("class").contains("Mui-checked")) {
                    clickTheElement(weekDay.findElement(By.cssSelector(".MuiButtonBase-root")).findElement(By.tagName("input")));
                }
            }
            if (isElementLoaded(warningMessageForSelectDays, 3)) {
                String expectedMessage = "At least one day should be selected";
                if (warningMessageForSelectDays.getText().equalsIgnoreCase(expectedMessage)) {
                    SimpleUtils.pass("The 'at least one day selected' warning message display correctly!");
                } else
                    SimpleUtils.fail("The warning message display incorrectly, the expected is: "+ expectedMessage
                            + " the actual is: "+ warningMessageForSelectDays.getText(), false);
            } else
                SimpleUtils.fail("The 'at least one day selected' warning message fail to load! ", false);
        }else{
            SimpleUtils.fail("Weeks Days failed to load!", true);
        }
    }

    @FindBy(css = "div.lgn-time-slider-notch-label")
    private List<WebElement> scheduleOperatingHrsOnEditPage;
    @FindBy(className = "tma-time-slider")
    private WebElement scheduleOperatingHrsSlider;
    @FindBy(css = "[id=\"shiftStart\"]")
    private WebElement shiftStartInputOnNewCreateShiftPage;
    @FindBy(css = "[id=\"shiftEnd\"]")
    private WebElement shiftEndInputOnNewCreateShiftPage;

    public void moveSliderAtCertainPoint(String shiftTime, String startingPoint) throws Exception {
        if (isElementLoaded(scheduleOperatingHrsSlider, 10)) {
            WebElement element = null;
            String am = "am";
            String pm = "pm";
            if (shiftTime.length() > 2 && (shiftTime.contains(am) || shiftTime.contains(pm))) {
                if(areListElementVisible(scheduleOperatingHrsOnEditPage, 15)
                        && scheduleOperatingHrsOnEditPage.size() >0){
                    for (WebElement scheduleOperatingHour: scheduleOperatingHrsOnEditPage){
                        if (scheduleOperatingHour.getAttribute("class").contains(shiftTime.substring(shiftTime.length() - 2))) {
                            if(scheduleOperatingHour.getText().equals(shiftTime.substring(0, shiftTime.length() - 2))){
                                element = scheduleOperatingHour;
                                break;
                            }
                        }
                    }
                }
            } else {
                if(areListElementVisible(scheduleOperatingHrsOnEditPage, 15)
                        && scheduleOperatingHrsOnEditPage.size() >0){
                    for (WebElement scheduleOperatingHour: scheduleOperatingHrsOnEditPage){
                        if(scheduleOperatingHour.getText().equals(shiftTime)){
                            element = scheduleOperatingHour;
                            break;
                        }
                    }
                }
            }
            if (element == null){
                SimpleUtils.fail("Cannot found the operating hour on edit operating hour page! ", false);
            }
            if(startingPoint.equalsIgnoreCase("End")){
                if(isElementLoaded(sliderNotchEnd,10) && sliderDroppableCount.size()>0){
                    SimpleUtils.pass("Shift timings with Sliders loaded on page Successfully for End Point");
                    mouseHoverDragandDrop(sliderNotchEnd,element);
                } else{
                    SimpleUtils.fail("Shift timings with Sliders not loaded on page Successfully", false);
                }
            }else if(startingPoint.equalsIgnoreCase("Start")){
                if(isElementLoaded(sliderNotchStart,10) && sliderDroppableCount.size()>0){
                    SimpleUtils.pass("Shift timings with Sliders loaded on page Successfully for End Point");
                    mouseHoverDragandDrop(sliderNotchStart,element);
                } else{
                    SimpleUtils.fail("Shift timings with Sliders not loaded on page Successfully", false);
                }
            }
        } else if (isElementLoaded(shiftStartInputOnNewCreateShiftPage, 5)
                && isElementLoaded(shiftEndInputOnNewCreateShiftPage, 5)) {
            if (shiftTime.contains("am")) {
                shiftTime = shiftTime.replace("am","")+ ":00"+"am";
            } else if (shiftTime.contains("pm")) {
                shiftTime = shiftTime.replace("pm","")+ ":00"+"pm";
            } else
                shiftTime = shiftTime+":00";
            if(startingPoint.equalsIgnoreCase("Start")){
                click(shiftEndInputOnNewCreateShiftPage);
                click(shiftStartInputOnNewCreateShiftPage);
                shiftStartInputOnNewCreateShiftPage.sendKeys(shiftTime);
                SimpleUtils.pass("Set shift start time successfully! ");
            } else {
                click(shiftStartInputOnNewCreateShiftPage);
                click(shiftEndInputOnNewCreateShiftPage);
                shiftEndInputOnNewCreateShiftPage.sendKeys(shiftTime);
                SimpleUtils.pass("Set shift end time successfully! ");
            }
        } else
            SimpleUtils.fail("Shift time slider or inputs fail to load on create shift page! ", false);

    }


    @FindBy(css = " [ng-if=\"isLocationGroup()\"] [ng-click=\"selectChoice($event, choice)\"]")
    private List<WebElement> listLocationGroup;
    @Override
    public void addNewShiftsByNames(List<String> names, String workRole) throws Exception {
        for(int i = 0; i < names.size(); i++) {
            clickOnDayViewAddNewShiftButton();
            customizeNewShiftPage();
            if(areListElementVisible(listLocationGroup, 5)
                    || isElementLoaded(btnChildLocationOnNewCreateShiftPage, 5)){
                List<String> locations = getAllLocationGroupLocationsFromCreateShiftWindow();
                selectChildLocInCreateShiftWindow(locations.get((new Random()).nextInt(locations.size()-1)+1));
                moveSliderAtSomePoint("40", 0, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
                moveSliderAtSomePoint("20", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
                selectWorkRole(workRole);
            } else
                selectWorkRole(workRole);
            clearAllSelectedDays();
            if (i == 0) {
                if (getEnterprise().equalsIgnoreCase(propertyMap.get(Constants.OpEnterprice))) {
                    selectDaysByIndex(2, 2, 2);
                } else {
                    selectDaysByIndex(2, 4, 6);
                }
            }else {
                if (getEnterprise().equalsIgnoreCase(propertyMap.get(Constants.OpEnterprice))) {
                    selectDaysByIndex(1, 1, 1);
                } else {
                    selectDaysByIndex(1, 3, 5);
                }
            }
            clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
            clickOnCreateOrNextBtn();
            if(isElementLoaded(btnAssignAnyway,5))
                click(btnAssignAnyway);
            if (areListElementVisible(buttonsOnWarningMode, 5)) {
                click(buttonsOnWarningMode.get(1));
            }
            searchTeamMemberByName(names.get(i));
            if(isElementLoaded(btnAssignAnyway,5))
                click(btnAssignAnyway);
            clickOnOfferOrAssignBtn();
        }
    }


    @FindBy(css = "[ng-show=\"hasSearchResults()\"] [ng-repeat=\"worker in searchResults\"]")
    private List<WebElement> searchResults;

    @FindBy(css = "button.MuiButtonBase-root")
    private List<WebElement> searchAndRecommendedTMTabs;

    @FindBy(css = "[placeholder=\"Search by Team Member, Role, Location or any combination.\"]")
    private WebElement textSearchOnNewCreateShiftPage;

    @FindBy(css = "div.MuiBox-root div.MuiBox-root div.MuiBox-root div div div div div div.MuiGrid-root.MuiGrid-container")
    private List<WebElement> searchResultsOnNewCreateShiftPage;

    @FindBy(css = ".MuiDialogContent-root button")
    private List<WebElement> buttonsOnWarningMode;
    @Override
    public void searchTeamMemberByName(String name) throws Exception {
        if(areListElementVisible(btnSearchteamMember,15)) {
            if (btnSearchteamMember.size() == 2) {
                //click(btnSearchteamMember.get(1));
                if (isElementLoaded(textSearch, 5) && isElementLoaded(searchIcon, 5)) {
                    textSearch.clear();
                    textSearch.sendKeys(name);
                    click(searchIcon);
                    if (areListElementVisible(searchResults, 30)) {
                        for (WebElement searchResult : searchResults) {
                            WebElement workerName = searchResult.findElement(By.className("worker-edit-search-worker-name"));
                            WebElement optionCircle = searchResult.findElement(By.className("tma-staffing-option-outer-circle"));
                            if (workerName != null && optionCircle != null) {
                                if (workerName.getText().toLowerCase().trim().replaceAll("\n"," ").contains(name.split(" ")[0].trim().toLowerCase())) {
                                    clickTheElement(optionCircle);
                                    SimpleUtils.report("Select Team Member: " + name + " Successfully!");
                                    waitForSeconds(2);
                                    if (isElementLoaded(btnAssignAnyway, 5) && btnAssignAnyway.getText().toLowerCase().equalsIgnoreCase("assign anyway")) {
                                        clickTheElement(btnAssignAnyway);
                                        SimpleUtils.report("Assign Team Member: Click on 'ASSIGN ANYWAY' button Successfully!");
                                    }
                                    break;
                                }
                            }else {
                                SimpleUtils.fail("Worker name or option circle not loaded Successfully!", false);
                            }
                        }
                    }else {
                        SimpleUtils.fail("Failed to find the team member!", false);
                    }
                }else {
                    SimpleUtils.fail("Search text not editable and icon are not clickable", false);
                }
            }else {
                SimpleUtils.fail("Search team member should have two tabs, failed to load!", false);
            }
        } else if (areListElementVisible(searchAndRecommendedTMTabs, 5)) {
            if (searchAndRecommendedTMTabs.size() == 2) {
                //click(btnSearchteamMember.get(1));
                if (isElementLoaded(textSearchOnNewCreateShiftPage, 5)) {
                    textSearchOnNewCreateShiftPage.clear();
                    textSearchOnNewCreateShiftPage.sendKeys(name);
                    waitForSeconds(3);
                    if (areListElementVisible(searchResultsOnNewCreateShiftPage, 30)) {
                        for (WebElement searchResult : searchResultsOnNewCreateShiftPage) {
                            List<WebElement> tmInfo = searchResult.findElements(By.cssSelector("p.MuiTypography-body1"));
                            String tmName = tmInfo.get(0).getText();
                            List<WebElement> assignAndOfferButtons = searchResult.findElements(By.tagName("button"));
                            WebElement assignButton = assignAndOfferButtons.get(0);
                            WebElement offerButton = assignAndOfferButtons.get(1);
                            if (tmName != null && assignButton != null && offerButton != null) {
                                if (tmName.toLowerCase().trim().replaceAll("\n"," ").contains(name.split(" ")[0].trim().toLowerCase())) {
                                    if (MyThreadLocal.getAssignTMStatus()) {
                                        clickTheElement(assignButton);
                                    } else
                                        clickTheElement(offerButton);
                                    SimpleUtils.report("Select Team Member: " + name + " Successfully!");
                                    waitForSeconds(2);
                                    if (areListElementVisible(buttonsOnWarningMode, 5) && buttonsOnWarningMode.get(1).getText().toLowerCase().equalsIgnoreCase("assign anyway")) {
                                        clickTheElement(buttonsOnWarningMode.get(1));
                                        SimpleUtils.report("Assign Team Member: Click on 'ASSIGN ANYWAY' button Successfully!");
                                    }
                                    break;
                                }
                            }else {
                                SimpleUtils.fail("Worker name or buttons not loaded Successfully!", false);
                            }
                        }
                    }else {
                        SimpleUtils.fail("Failed to find the team member!", false);
                    }
                }else {
                    SimpleUtils.fail("Search text not editable and icon are not clickable", false);
                }
            }else {
                SimpleUtils.fail("Search team member should have two tabs, failed to load!", false);
            }
        } else
            SimpleUtils.fail("Search team member tab fail to load! ", false);
    }


    @FindBy(css = "[ng-if=\"canShowNewShiftButton()\"]")
    private WebElement addNewShiftOnDayViewButton;
    @Override
    public void clickOnDayViewAddNewShiftButton() throws Exception {
        if (isElementLoaded(createNewShiftWeekView, 10)) {
            clickTheElement(createNewShiftWeekView);
            SimpleUtils.pass("Click on Create New Shift button successfully!");
        }else if (isElementLoaded(addNewShiftOnDayViewButton, 10)) {
            click(addNewShiftOnDayViewButton);
            SimpleUtils.pass("Click on Add New Shift '+' button successfully!");
        }else {
            SimpleUtils.report("Add New Shift '+' button and Create New Shift button not loaded!");
        }
    }

    public List<String> getAllLocationGroupLocationsFromCreateShiftWindow() throws Exception{
        List<String> locationGroupLocations = new ArrayList<>();
        if (isElementLoaded(btnChildLocation, 10)) {
            click(btnChildLocation);
            SimpleUtils.pass("Child location button clicked Successfully");
            if(areListElementVisible(listLocationGroup, 10) && listLocationGroup.size()>0){
                for (WebElement location: listLocationGroup){
                    locationGroupLocations.add(location.getText());
                }
                SimpleUtils.pass("Get location group locations from create shift window successfully! ");
            }else
                SimpleUtils.fail("Location group dropdown loaded fail! ", false);
            //close the dropdown list
            click(btnChildLocation);
        } else if (isElementLoaded(btnSaveOnNewCreateShiftPage, 5)) {
            click(btnSaveOnNewCreateShiftPage);
            SimpleUtils.pass("Child location button clicked Successfully");

            if(areListElementVisible(dropDownListOnNewCreateShiftPage, 10) && dropDownListOnNewCreateShiftPage.size()>0){
                for (WebElement location: dropDownListOnNewCreateShiftPage){
                    locationGroupLocations.add(location.getText());
                }
                SimpleUtils.pass("Get location group locations from create shift window successfully! ");
            }else
                SimpleUtils.fail("Location group dropdown loaded fail! ", false);
            //close the dropdown list
            click(btnSaveOnNewCreateShiftPage);
        }else {
            SimpleUtils.fail("Child location button is not clickable", false);
        }
        return locationGroupLocations;
    }

    @FindBy(css = "lgn-drop-down.tma-locations-dropdown button.lgn-dropdown-button")
    private WebElement btnChildLocation;

    @FindBy(xpath = "//div[@id=\"businessId\"]/div/div[1]/div[1]")
    private WebElement btnChildLocationOnNewCreateShiftPage;

    public void selectChildLocInCreateShiftWindow(String location) throws Exception {
        if (isElementLoaded(btnChildLocation, 10)) {
            click(btnChildLocation);
            SimpleUtils.pass("Child location button clicked Successfully");
            if (listWorkRoles.size() > 0) {
                for (WebElement listWorkRole : listWorkRoles) {
                    if (listWorkRole.getText().toLowerCase().contains(location.toLowerCase())) {
                        click(listWorkRole);
                        SimpleUtils.pass("Child location " + location + "selected Successfully");
                        break;
                    } else {
                        SimpleUtils.report("Child location" + location + " not selected");
                    }
                }

            } else {
                SimpleUtils.fail("Child location size are empty", false);
            }
        } else if (isElementLoaded(btnSaveOnNewCreateShiftPage, 5)) {
            click(btnSaveOnNewCreateShiftPage);
            SimpleUtils.pass("Child location button clicked Successfully");
            if (dropDownListOnNewCreateShiftPage.size() > 0) {
                for (WebElement childLocation : dropDownListOnNewCreateShiftPage) {
                    if (childLocation.getText().toLowerCase().contains(location.toLowerCase())) {
                        click(childLocation);
                        SimpleUtils.pass("Child location " + location + "selected Successfully");
                        break;
                    } else {
                        SimpleUtils.report("Child location" + location + " not selected");
                    }
                }

            } else {
                SimpleUtils.fail("Child location size are empty", false);
            }
        }else {
            SimpleUtils.fail("Child location button is not clickable", false);
        }
    }


    public void selectDaysByIndex(int index1, int index2, int index3) throws Exception {
        if (areListElementVisible(weekDays, 5) && weekDays.size() == 7) {
            if (index1 < weekDays.size() && index2 < weekDays.size() && index3 < weekDays.size()) {
                if (!weekDays.get(index1).getAttribute("class").contains("week-day-multi-picker-day-selected")) {
                    click(weekDays.get(index1));
                    SimpleUtils.report("Select day: " + weekDays.get(index1).getText() + " Successfully!");
                }
                if (!weekDays.get(index2).getAttribute("class").contains("week-day-multi-picker-day-selected")) {
                    click(weekDays.get(index2));
                    SimpleUtils.report("Select day: " + weekDays.get(index2).getText() + " Successfully!");
                }
                if (!weekDays.get(index3).getAttribute("class").contains("week-day-multi-picker-day-selected")) {
                    click(weekDays.get(index3));
                    SimpleUtils.report("Select day: " + weekDays.get(index3).getText() + " Successfully!");
                }
            } else {
                SimpleUtils.fail("There is index that out of range: " + index1 + ", " + index2 + ", " + index3 + ", the max value is 6!", false);
            }
        }else if (areListElementVisible(weekDaysInNewCreateShiftPage, 5)
                && weekDaysInNewCreateShiftPage.size() == 7) {
            if (index1 < weekDaysInNewCreateShiftPage.size()
                    && index2 < weekDaysInNewCreateShiftPage.size()
                    && index3 < weekDaysInNewCreateShiftPage.size()) {
                if (!weekDaysInNewCreateShiftPage.get(index1).findElement(By.cssSelector(".MuiButtonBase-root")).getAttribute("class").contains("Mui-checked")) {
                    clickTheElement(weekDaysInNewCreateShiftPage.get(index1).findElement(By.cssSelector(".MuiButtonBase-root")).findElement(By.tagName("input")));
                    SimpleUtils.report("Select day: " + index1 + " Successfully!");
                }
                if (!weekDaysInNewCreateShiftPage.get(index2).findElement(By.cssSelector(".MuiButtonBase-root")).getAttribute("class").contains("Mui-checked")) {
                    clickTheElement(weekDaysInNewCreateShiftPage.get(index2).findElement(By.cssSelector(".MuiButtonBase-root")).findElement(By.tagName("input")));
                    SimpleUtils.report("Select day: " + index2 + " Successfully!");
                }
                if (!weekDaysInNewCreateShiftPage.get(index3).findElement(By.cssSelector(".MuiButtonBase-root")).getAttribute("class").contains("Mui-checked")) {
                    clickTheElement(weekDaysInNewCreateShiftPage.get(index3).findElement(By.cssSelector(".MuiButtonBase-root")).findElement(By.tagName("input")));
                    SimpleUtils.report("Select day: " + index3 + " Successfully!");
                }
            } else {
                SimpleUtils.fail("There is index that out of range: " + index1 + ", " + index2 + ", " + index3 + ", the max value is 6!", false);
            }
        }else{
            SimpleUtils.fail("Weeks Days failed to load!", false);
        }
    }

    @Override
    public void selectWorkingDaysOnNewShiftPageByIndex(int index) throws Exception {
        clearAllSelectedDays();
        if (areListElementVisible(weekDays, 5) && weekDays.size() == 7) {
            if (index < weekDays.size()) {
                if (!weekDays.get(index).getAttribute("class").contains("week-day-multi-picker-day-selected")) {
                    click(weekDays.get(index));
                    SimpleUtils.report("Select day: " + weekDays.get(index).getText() + " Successfully!");
                }
            }else {
                SimpleUtils.fail("There is index that out of range: " + index + ", the max value is 6!", false);
            }
        }else if (areListElementVisible(weekDaysInNewCreateShiftPage, 5) && weekDaysInNewCreateShiftPage.size() == 7) {
            if (index < weekDaysInNewCreateShiftPage.size()) {
                WebElement checkBoxOfWeekDay = weekDaysInNewCreateShiftPage.get(index).findElement(By.cssSelector(".MuiButtonBase-root"));
                if (!checkBoxOfWeekDay.getAttribute("class").contains("Mui-checked")) {
                    click(checkBoxOfWeekDay);
                    SimpleUtils.report("Select day: " + weekDaysInNewCreateShiftPage.get(index).getText() + " Successfully!");
                }
            }else {
                SimpleUtils.fail("There is index that out of range: " + index + ", the max value is 6!", false);
            }
        }else{
            SimpleUtils.fail("Weeks Days failed to load!", true);
        }
    }

    @FindBy(className = "lgn-alert-modal")
    private WebElement confirmWindow;
    @FindBy(className = "lgn-action-button-success")
    private WebElement okBtnOnConfirm;
    @FindBy(css="[ng-show=\"hasSearchResults()\"] tr.table-row.ng-scope")
    private List<WebElement> searchTMRows;
    public WebElement selectAndGetTheSelectedTM() throws Exception {
        WebElement selectedTM = null;
//		waitForSeconds(5);
        if(areListElementVisible(scheduleSearchTeamMemberStatus,5)
                || isElementLoaded(scheduleNoAvailableMatchStatus,5)){
            for(int i=0; i<scheduleSearchTeamMemberStatus.size();i++){
                String statusText = scheduleSearchTeamMemberStatus.get(i).getText();
                if((statusText.contains("Available") || statusText.contains("Unknown")) && !statusText.contains("Assigned to this shift")){
                    click(radionBtnSearchTeamMembers.get(i));
                    if (isElementEnabled(confirmWindow, 5)) {
                        click(okBtnOnConfirm);
                    }
                    selectedTM = searchTMRows.get(i);
                    break;
                }
            }
            if (selectedTM == null) {
                SimpleUtils.report("Not able to found Available TMs");
            }
        } else if (areListElementVisible(searchResultsOnNewCreateShiftPage, 5)) {
            for (WebElement searchResult: searchResultsOnNewCreateShiftPage) {
                List<WebElement> allStatus= searchResult.findElements(By.cssSelector(".MuiGrid-grid-xs-3 .MuiTypography-body2"));
                StringBuilder tmAllStatus = new StringBuilder();
                for (WebElement status: allStatus) {
                    tmAllStatus.append(" ").append(status.getText());
                }
                if((tmAllStatus.toString().contains("Available") || tmAllStatus.toString().contains("Unknown")) && !tmAllStatus.toString().contains("Assigned to this shift")){
                    List<WebElement> assignAndOfferButtons = searchResult.findElements(By.tagName("button"));
                    if (MyThreadLocal.getAssignTMStatus()) {
                        clickTheElement(assignAndOfferButtons.get(0));
                    } else
                        clickTheElement(assignAndOfferButtons.get(1));
                    if (isElementEnabled(btnAssignAnyway, 5)) {
                        click(btnAssignAnyway);
                    }
                    selectedTM = searchResult.findElements(By.cssSelector("p.MuiTypography-body1")).get(0);
                    break;
                }
            }
        }else{
            SimpleUtils.fail("Not able to found Available status in SearchResult", true);
        }

        return selectedTM;
    }

    @FindBy(css = "tr.table-row.ng-scope:nth-child(1)")
    private WebElement firstTableRow;

    @FindBy(css = "tr.table-row.ng-scope:nth-child(1) > td > div:nth-child(2) > div:nth-child(1) > span:nth-child(1)")
    private WebElement firstnameOfTM;
    public String selectTeamMembers() throws Exception {
        String newSelectedTM = null;
        waitForSeconds(5);
        if (areListElementVisible(recommendedScrollTable, 20)) {
            if (isElementLoaded(selectRecommendedOption, 5)) {
                String[] txtRecommendedOption = selectRecommendedOption.getText().replaceAll("\\p{P}", "").split(" ");
                if (Integer.parseInt(txtRecommendedOption[2]) == 0) {
                    SimpleUtils.report(txtRecommendedOption[0] + " Option no recommended TMs");
                    click(btnSearchteamMember.get(0));
                    newSelectedTM = searchAndGetTMName(propertySearchTeamMember.get("AssignTeamMember"));
                } else {
                    click(firstTableRow.findElement(By.cssSelector("td.table-field.action-field")));
                    newSelectedTM = firstnameOfTM.getText();
                }
            } else {
                click(btnSearchteamMember.get(0));
                newSelectedTM = searchAndGetTMName(propertySearchTeamMember.get("AssignTeamMember"));
                SimpleUtils.report("Recommended option not available on page");
            }
        } else if (isElementLoaded(textSearch, 10)) {
            newSelectedTM = searchAndGetTMName(propertySearchTeamMember.get("AssignTeamMember"));
        } else if (areListElementVisible(searchAndRecommendedTMTabs, 5)) {
            if (areListElementVisible(searchResultsOnNewCreateShiftPage, 5)) {
                List<WebElement> assignAndOfferButtons = searchResultsOnNewCreateShiftPage.get(0).findElements(By.tagName("button"));
                if (MyThreadLocal.getAssignTMStatus()) {
                    clickTheElement(assignAndOfferButtons.get(0));
                } else
                    clickTheElement(assignAndOfferButtons.get(1));
                newSelectedTM = searchResultsOnNewCreateShiftPage.get(0).findElements(By.cssSelector("p.MuiTypography-body1")).get(0).getText();
            } else {
                clickTheElement(searchAndRecommendedTMTabs.get(0));
                newSelectedTM = searchAndGetTMName(propertySearchTeamMember.get("AssignTeamMember"));
            }
        }else {
            SimpleUtils.fail("Select Team member option and Recommended options are not available on page", false);
        }
        return newSelectedTM;
    }


    @FindBy(css = "tr.table-row.ng-scope")
    List<WebElement> recommendedTMs;
    public String selectTeamMembers(int numOfTM) throws Exception {
        String newSelectedTMs = "";
        waitForSeconds(5);
        if (areListElementVisible(recommendedScrollTable, 20)) {
            if (isElementLoaded(selectRecommendedOption, 5)) {
                String[] txtRecommendedOption = selectRecommendedOption.getText().replaceAll("\\p{P}", "").split(" ");
                int recommendedNum= Integer.parseInt(txtRecommendedOption[2]);
                if (recommendedNum == 0) {
                    SimpleUtils.report(txtRecommendedOption[0] + " Option no recommended TMs");
                    click(btnSearchteamMember.get(0));
                    newSelectedTMs = searchAndGetTMName(propertySearchTeamMember.get("AssignTeamMember"));
                } else if (recommendedNum >= numOfTM){
                    for (int i = 0; i < numOfTM; i++){
                        click(recommendedTMs.get(i).findElement(By.cssSelector("td.table-field.action-field")));
                        newSelectedTMs = newSelectedTMs + recommendedTMs.get(i).findElement(By.cssSelector(".worker-edit-search-worker-display-name")).getText() + " ";
                    }
                } else {
                    for (int i = 0; i < recommendedNum; i++){
                        click(recommendedTMs.get(i).findElement(By.cssSelector("td.table-field.action-field")));
                        newSelectedTMs = newSelectedTMs + recommendedTMs.get(i).findElement(By.cssSelector(".worker-edit-search-worker-display-name")).getText() + " ";
                    }
                }
            } else {
                click(btnSearchteamMember.get(0));
                newSelectedTMs = searchAndGetTMName(propertySearchTeamMember.get("AssignTeamMember"));
                SimpleUtils.report("Recommended option not available on page");
            }
        } else if (isElementLoaded(textSearch, 5)) {
            newSelectedTMs = searchAndGetTMName(propertySearchTeamMember.get("AssignTeamMember"));
        } else {
            SimpleUtils.fail("Select Team member option and Recommended options are not available on page", false);
        }
        return newSelectedTMs;
    }


    public String searchAndGetTMName(String searchInput) throws Exception {
        NewShiftPage newShiftPage = new ConsoleNewShiftPage();
        String[] searchAssignTeamMember = searchInput.split(",");
        String selectedTMName = null;
        if (isElementLoaded(textSearch, 10) && isElementLoaded(searchIcon, 10)) {
            for (int i = 0; i < searchAssignTeamMember.length; i++) {
                String[] searchTM = searchAssignTeamMember[i].split("\\.");
                textSearch.sendKeys(searchTM[0]);
                click(searchIcon);
                waitForSeconds(5);
                WebElement selectedTM = newShiftPage.selectAndGetTheSelectedTM();
                if (selectedTM != null) {
                    selectedTMName = selectedTM.findElement(By.className("worker-edit-search-worker-display-name")).getText();
                    break;
                } else {
                    textSearch.clear();
                }
            }

            if (selectedTMName == null || selectedTMName.isEmpty()) {
                SimpleUtils.fail("Not able to found Available TMs in SearchResult", false);
            }

        } else if (isElementLoaded(textSearchOnNewCreateShiftPage, 10)) {
            for (int i = 0; i < searchAssignTeamMember.length; i++) {
                String[] searchTM = searchAssignTeamMember[i].split("\\.");
                textSearchOnNewCreateShiftPage.sendKeys(searchTM[0]);
                waitForSeconds(3);
                WebElement selectedTM = newShiftPage.selectAndGetTheSelectedTM();
                if (selectedTM != null) {
                    selectedTMName = selectedTM.getText();
                    break;
                } else {
                    textSearchOnNewCreateShiftPage.clear();
                }
            }

            if (selectedTMName == null || selectedTMName.isEmpty()) {
                SimpleUtils.fail("Not able to found Available TMs in SearchResult", false);
            }

        }else {
            SimpleUtils.fail("Search text not editable and icon are not clickable", false);
        }
        return selectedTMName;
    }


    @Override
    public void selectSpecificTMWhileCreateNewShift(String teamMemberName) throws Exception {
        if(areListElementVisible(btnSearchteamMember,5)){
            click(btnSearchteamMember.get(1));
            searchText(teamMemberName);
        }

    }


    @Override
    public void addOpenShiftWithLastDay(String workRole) throws Exception {
        if (isElementLoaded(createNewShiftWeekView,5)) {
            click(createNewShiftWeekView);
            selectWorkRole(workRole);
            clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
            clearAllSelectedDays();
            if (areListElementVisible(weekDays, 5) && weekDays.size() == 7) {
                if (!weekDays.get(6).getAttribute("class").contains("week-day-multi-picker-day-selected")) {
                    click(weekDays.get(6));
                }
            }
            clickOnCreateOrNextBtn();
        } else
            SimpleUtils.fail("Day View Schedule edit mode, add new shift button not found for Week Day: '" +
                    getActiveWeekText() + "'", false);
    }



    @Override
    public void addManualShiftWithLastDay(String workRole, String tmName) throws Exception {
        if (isElementLoaded(createNewShiftWeekView,5)) {
            click(createNewShiftWeekView);
            selectWorkRole(workRole);
            if (weekDays.get(0).getAttribute("class").contains("week-day-multi-picker-day-selected"))
                click(weekDays.get(0));
            clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
            if (weekDays.size() == 7) {
                for (int i = weekDays.size() - 1; i >= 0; i--) {
                    if (weekDays.get(i).getAttribute("class").contains("week-day-multi-picker-day-disabled"))
                        continue;
                    else {
                        click(weekDays.get(i));
                        break;
                    }
                }
            }
            clickOnCreateOrNextBtn();
            searchTeamMemberByName(tmName);
            if(isElementLoaded(btnAssignAnyway,5))
                click(btnAssignAnyway);
            clickOnOfferOrAssignBtn();
        } else
            SimpleUtils.fail("Failed to load 'Create New Shift' label, maybe it is not in edit mode", false);
    }


    @FindBy(css = "[ng-repeat=\"day in summary.workingHours\"]")
    private List<WebElement> operatingHours;

    @FindBy(css = "[ng-class=\"{ 'switcher-closed': !value }\"]")
    private WebElement openOrCloseWeekDayButton;

    @FindBy(css = "[ng-click=\"$dismiss()\"] button[ng-click=\"$ctrl.onSubmit({type:'saveas',label:$ctrl.label})\"]")
    private WebElement editOperatingHourCancelButton;

    @FindBy(css = "[ng-click=\"save()\"]")
    private WebElement editOperatingHourSaveButton;

    @FindBy(css = "lg-button[label=\"Cancel\"]")
    private WebElement operatingHoursCancelBtn;

    @FindBy(css = "lg-button.edit-operating-hours-link")
    private WebElement editOperatingHoursBtn;
    @FindBy (css = ".operating-hours-day-list-item.ng-scope")
    private List<WebElement> operatingHoursDayLists;
    @Override
    public void editOperatingHoursOnScheduleOldUIPage(String startTime, String endTime, List<String> weekDaysToClose) throws Exception {
        if (areListElementVisible(operatingHours, 20) && operatingHours.size()==7 && isElementLoaded(editOperatingHoursBtn, 10)){
            clickTheElement(editOperatingHoursBtn);
            if (isElementLoaded(customizeNewShift, 10)) {
                if (areListElementVisible(operatingHoursDayLists, 15)) {
                    for (WebElement dayList : operatingHoursDayLists) {
                        WebElement weekDay = dayList.findElement(By.cssSelector(".operating-hours-day-list-item-day"));
                        if (weekDay != null) {
                            WebElement checkbox = dayList.findElement(By.cssSelector("input[type=\"checkbox\"]"));
                            if (!weekDaysToClose.contains(weekDay.getText())) {
                                if (!startTime.equals("") || !endTime.equals("")) {
                                    if (checkbox.getAttribute("class").contains("ng-empty")) {
                                        clickTheElement(checkbox);
                                    }
                                    List<WebElement> startNEndTimes = dayList.findElements(By.cssSelector("[ng-if*=\"day.isOpened\"] input"));
                                    String openTime = startNEndTimes.get(0).getAttribute("value");
                                    String closeTime = startNEndTimes.get(1).getAttribute("value");
                                    if (!openTime.equals(startTime) || !closeTime.equals(endTime)) {
                                        startNEndTimes.get(0).clear();
                                        startNEndTimes.get(1).clear();
                                        startNEndTimes.get(0).sendKeys(startTime);
                                        startNEndTimes.get(1).sendKeys(endTime);
                                    }
                                }

                            } else {
                                if (!checkbox.getAttribute("class").contains("ng-empty")) {
                                    clickTheElement(checkbox);
                                }
                            }
                        } else {
                            SimpleUtils.fail("Failed to find weekday element!", false);
                        }
                    }
                    clickTheElement(editOperatingHourSaveButton);
                    if (isElementEnabled(editOperatingHoursBtn, 15)) {
                        SimpleUtils.pass("Create Schedule: Save the operating hours Successfully!");
                    } else {
                        SimpleUtils.fail("Create Schedule: Click on Save the operating hours button failed, Next button is not enabled!", false);
                    }
                }
            } else
                SimpleUtils.fail("Edit operating hours modal fail to load! ", false);
        }else{
            SimpleUtils.fail("Operating Hours not loaded Successfully!", false);
        }
    }


    @Override
    public void searchTeamMemberByNameNLocation(String name, String location) throws Exception {
        if(areListElementVisible(btnSearchteamMember,15)) {
            if (btnSearchteamMember.size() == 2) {
                //click(btnSearchteamMember.get(1));
                if (isElementLoaded(textSearch, 5) && isElementLoaded(searchIcon, 15)) {
                    textSearch.clear();
                    textSearch.sendKeys(name);
                    click(searchIcon);
                    if (areListElementVisible(searchResults, 15)) {
                        for (WebElement searchResult : searchResults) {
                            WebElement workerName = searchResult.findElement(By.className("worker-edit-search-worker-name"));
                            WebElement optionCircle = searchResult.findElement(By.className("tma-staffing-option-outer-circle"));
                            WebElement locationInfo = searchResult.findElement(By.className("tma-description-fields"));
                            if (workerName != null && optionCircle != null) {
                                if (workerName.getText().toLowerCase().trim().replaceAll("\n"," ").contains(name.trim().toLowerCase()) && locationInfo.getText().toLowerCase().trim().replaceAll("\n"," ").contains(location.trim().toLowerCase())) {
                                    click(optionCircle);
                                    SimpleUtils.report("Select Team Member: " + name + " Successfully!");
                                    waitForSeconds(2);
                                    if (isElementLoaded(btnAssignAnyway, 5) && btnAssignAnyway.getText().equalsIgnoreCase("ASSIGN ANYWAY")) {
                                        click(btnAssignAnyway);
                                        SimpleUtils.report("Assign Team Member: Click on 'ASSIGN ANYWAY' button Successfully!");
                                    }
                                    break;
                                }
                            }else {
                                SimpleUtils.fail("Worker name or option circle not loaded Successfully!", false);
                            }
                        }
                    }else {
                        SimpleUtils.fail("Failed to find the team member!", false);
                    }
                }else {
                    SimpleUtils.fail("Search text not editable and icon are not clickable", false);
                }
            }else {
                SimpleUtils.fail("Search team member should have two tabs, failed to load!", false);
            }
        } else if (areListElementVisible(searchAndRecommendedTMTabs, 5)) {
            if (searchAndRecommendedTMTabs.size() == 2) {
                //click(btnSearchteamMember.get(1));
                if (isElementLoaded(textSearchOnNewCreateShiftPage, 5)) {
                    textSearchOnNewCreateShiftPage.clear();
                    textSearchOnNewCreateShiftPage.sendKeys(name);
                    waitForSeconds(3);
                    if (areListElementVisible(searchResultsOnNewCreateShiftPage, 30)) {
                        for (WebElement searchResult : searchResultsOnNewCreateShiftPage) {
                            List<WebElement> tmInfo = searchResult.findElements(By.cssSelector("p.MuiTypography-body1"));
                            String tmName = tmInfo.get(0).getText();
                            String locationName = tmInfo.get(2).getText();
                            List<WebElement> assignAndOfferButtons = searchResult.findElements(By.tagName("button"));
                            WebElement assignButton = assignAndOfferButtons.get(0);
                            WebElement offerButton = assignAndOfferButtons.get(1);
                            if (tmName != null && locationName!=null && assignButton != null && offerButton != null) {
                                if (tmName.toLowerCase().trim().replaceAll("\n"," ").contains(name.split(" ")[0].trim().toLowerCase())
                                        && locationName.toLowerCase().trim().replaceAll("\n"," ").contains(location.trim().toLowerCase())) {
                                    if (MyThreadLocal.getAssignTMStatus()) {
                                        clickTheElement(assignButton);
                                    } else
                                        clickTheElement(offerButton);
                                    SimpleUtils.report("Select Team Member: " + name + " Successfully!");
                                    waitForSeconds(2);
                                    if (areListElementVisible(buttonsOnWarningMode, 5) && buttonsOnWarningMode.get(1).getText().toLowerCase().equalsIgnoreCase("assign anyway")) {
                                        clickTheElement(buttonsOnWarningMode.get(1));
                                        SimpleUtils.report("Assign Team Member: Click on 'ASSIGN ANYWAY' button Successfully!");
                                    }
                                    break;
                                }
                            }else {
                                SimpleUtils.fail("Worker name or buttons not loaded Successfully!", false);
                            }
                        }
                    }else {
                        SimpleUtils.fail("Failed to find the team member!", false);
                    }
                }else {
                    SimpleUtils.fail("Search text not editable and icon are not clickable", false);
                }
            }else {
                SimpleUtils.fail("Search team member should have two tabs, failed to load!", false);
            }
        } else
            SimpleUtils.fail("Search team member tab fail to load! ", false);
    }



    @FindBy(xpath="//div[@class='tab-label']/span[text()='Search Team Members']")
    private WebElement btnSearchTeamMember;
    public void selectTeamMembersOptionForSchedule() throws Exception {
        NewShiftPage newShiftPage = new ConsoleNewShiftPage();
        if(isElementEnabled(btnSearchTeamMember,5)){
            click(btnSearchTeamMember);
            if(isElementLoaded(textSearch,5)) {
                newShiftPage.searchText(propertySearchTeamMember.get("AssignTeamMember"));
            }
        }else{
            SimpleUtils.fail("Select Team member option not available on page",false);
        }

    }


    public void selectTeamMembersOptionForOverlappingSchedule() throws Exception{
        if(isElementEnabled(btnSearchTeamMember,5)){
            click(btnSearchTeamMember);
            if(isElementLoaded(textSearch,5)) {
                searchWorkerName(propertySearchTeamMember.get("AssignTeamMember"));
            }
        }else{
            SimpleUtils.fail("Select Team member option not available on page",false);
        }

    }

    public void searchWorkerName(String searchInput) throws Exception {
        String[] searchAssignTeamMember = searchInput.split(",");
        if(isElementLoaded(textSearch,10) && isElementLoaded(searchIcon,10)){
            for(int i=0; i<searchAssignTeamMember.length;i++){
                textSearch.sendKeys(searchAssignTeamMember[i]);
                click(searchIcon);
                if(getScheduleOverlappingStatus()){
                    break;
                }else{
                    textSearch.clear();
                    if(i== searchAssignTeamMember.length-1){
                        SimpleUtils.fail("There is no data found for given team member. Please provide some other input", false);
                    }
                }
            }
        }else{
            SimpleUtils.fail("Search text not editable and icon are not clickable", false);
        }

    }


    @FindBy(xpath="//div[@class='tma-search-action']/following-sibling::div[1]//div[@class='worker-edit-search-worker-name']")
    private List<WebElement> searchWorkerDisplayName;
    public boolean getScheduleOverlappingStatus()throws Exception {
        boolean ScheduleStatus = false;
        if(areListElementVisible(scheduleSearchTeamMemberStatus,5)){
            for(int i=0; i<scheduleSearchTeamMemberStatus.size();i++){
                if(scheduleSearchTeamMemberStatus.get(i).getText().contains("Scheduled")){
                    click(radionBtnSearchTeamMembers.get(i));
                    String workerDisplayFirstNameText =(searchWorkerDisplayName.get(i).getText().replace(" ","").split("\n"))[0];
                    String workerDisplayLastNameText =(searchWorkerDisplayName.get(i).getText().replace(" ","").split("\n"))[1];
                    setTeamMemberName(workerDisplayFirstNameText + workerDisplayLastNameText);
                    boolean flag = displayAlertPopUp();
                    if (flag) {
                        ScheduleStatus = true;
                        break;
                    }
                }
            }
        }

        return ScheduleStatus;
    }


    @Override
    public boolean displayAlertPopUp() throws Exception{
        boolean flag = true;
        String msgAlert = null;
        if(isElementLoaded(popUpScheduleOverlap,5)){
            if(isElementLoaded(alertMessage,5)){
                msgAlert = alertMessage.getText();
                if(isElementLoaded(btnAssignAnyway,5)){
                    if(btnAssignAnyway.getText().toLowerCase().equals("OK".toLowerCase())){
                        click(btnAssignAnyway);
                        flag = false;
                    } else if (btnAssignAnyway.getText().toUpperCase().equals("ASSIGN ANYWAY")) {
                        flag = true;
                    } else {
                        String startTime = ((msgAlert.split(": "))[1].split(" - "))[0];
                        String startTimeFinal = SimpleUtils.convertTimeIntoHHColonMM(startTime);
                        String endTime = (((msgAlert.split(": "))[1].split(" - "))[1].split(" "))[0];
                        String endTimeFinal = SimpleUtils.convertTimeIntoHHColonMM(endTime);
                        String timeDuration = startTimeFinal + "-" + endTimeFinal;
                        setScheduleHoursTimeDuration(timeDuration);
                        click(btnAssignAnyway);
                        flag= true;
                    }

                }else{
                    SimpleUtils.fail("Schedule Overlap Assign Anyway button not displayed on the page",false);
                }
            }else{
                SimpleUtils.fail("Schedule Overlap alert message not displayed",false);
            }
        }else {
            flag = false;
        }
        return flag;
    }


    @Override
    public void selectDaysFromCurrentDay(String currentDay) throws Exception {
        int index = 7;
        String[] items = currentDay.split(" ");
        if (items.length == 3) {
            currentDay = items[0].substring(0, 3) + items[1].substring(0, 3) + (items[2].length() == 1 ? "0"+items[2] : items[2]);
        }
        if (areListElementVisible(weekDays, 5) && weekDays.size() == 7) {
            for (int i = 0; i < weekDays.size(); i++) {
                String weekDay = weekDays.get(i).getText().replaceAll("\\s*", "");
                if (weekDay.equalsIgnoreCase(currentDay)) {
                    index = i;
                }
                if (i >= index) {
                    if (!weekDays.get(i).getAttribute("class").contains("week-day-multi-picker-day-selected")) {
                        click(weekDays.get(i));
                        SimpleUtils.pass("Select the day: " + weekDays.get(i).getText() + " successfully!");
                    }
                }
            }
        }else{
            SimpleUtils.fail("Weeks Days failed to load!", true);
        }
    }


    @FindBy(css = "div.tma-time-interval.fl-right.ng-binding")
    private WebElement timeDurationWhenCreateNewShift;

    @Override
    public String getTimeDurationWhenCreateNewShift() throws Exception {
        if (isElementLoaded(timeDurationWhenCreateNewShift,5)) {
            String timeDuration = timeDurationWhenCreateNewShift.getText();
            return timeDuration;
        }else
            SimpleUtils.fail("time duration load failed",true);
        return null;
    }


    @Override
    public void selectSpecificWorkDay(int dayCountInOneWeek) {
        if (areListElementVisible(weekDays, 5) && weekDays.size() == 7) {
            for (int i = 0; i < dayCountInOneWeek; i++) {
                if (!weekDays.get(i).getAttribute("class").contains("selected")) {
                    click(weekDays.get(i));
                }
            }
        }else if (areListElementVisible(weekDaysInNewCreateShiftPage, 5) && weekDaysInNewCreateShiftPage.size() == 7) {
            for (int i = 0; i < dayCountInOneWeek; i++) {
                if (!weekDaysInNewCreateShiftPage.get(i).findElement(By.cssSelector(".MuiButtonBase-root")).getAttribute("class").contains("checked")) {
                    click(weekDaysInNewCreateShiftPage.get(i).findElement(By.cssSelector(".MuiButtonBase-root")));
                }
            }
        }else
            SimpleUtils.fail("week days load failed",true);
    }

    @Override
    public List<Integer> selectDaysByCountAndCannotSelectedDate(int count, String cannotSelectedDate) throws Exception {
        List<Integer> indexes = new ArrayList<>();
        int selectedCount = 0;
        if (count > 7) {
            SimpleUtils.fail("Create New Shift: There are total 7 days, the count: " + count + " is larger than 7", false);
        }
        if (areListElementVisible(weekDays, 15) && weekDays.size() == 7) {
            for (int i = 0; i < 7; i++) {
                if (weekDays.get(i).getAttribute("class").contains("week-day-multi-picker-day-disabled")) {
                    SimpleUtils.report("Day: " + weekDays.get(i).getText() + " is disabled!");
                } else {
                    if (cannotSelectedDate == null || cannotSelectedDate == "") {
                        if (!weekDays.get(i).getAttribute("class").contains("week-day-multi-picker-day-selected")) {
                            click(weekDays.get(i));
                            SimpleUtils.report("Select day: " + weekDays.get(i).getText() + " Successfully!");
                        }
                        selectedCount++;
                        indexes.add(i);
                    } else {
                        int date = Integer.parseInt(weekDays.get(i).getText().substring(weekDays.get(i).getText().length() - 2).trim());
                        int cannotDate = Integer.parseInt(cannotSelectedDate.substring(cannotSelectedDate.length() - 2).trim());
                        if (date != cannotDate) {
                            if (!weekDays.get(i).getAttribute("class").contains("week-day-multi-picker-day-selected")) {
                                click(weekDays.get(i));
                                SimpleUtils.report("Select day: " + weekDays.get(i).getText() + " Successfully!");
                            }
                            selectedCount++;
                            indexes.add(i);
                        }
                    }
                    if (selectedCount == count) {
                        SimpleUtils.pass("Create New Shift: Select " + count + " days Successfully!");
                        break;
                    }
                }
            }
            if (selectedCount != count) {
                SimpleUtils.fail("Create New Shift: Failed to select " + count + " days! Actual is: " + selectedCount + " days!", false);
            }
        }else if (areListElementVisible(weekDaysInNewCreateShiftPage, 15) && weekDaysInNewCreateShiftPage.size() == 7) {
            for (int i = 0; i < 7; i++) {
                if (weekDaysInNewCreateShiftPage.get(i).findElement(By.cssSelector("span.MuiButtonBase-root"))
                        .getAttribute("class").contains("Mui-disabled")) {
                    SimpleUtils.report("Day: " + weekDaysInNewCreateShiftPage.get(i).getText() + " is disabled!");
                } else {
                    if (cannotSelectedDate == null || cannotSelectedDate.equals("")) {
                        if (!weekDaysInNewCreateShiftPage.get(i).findElement(By.cssSelector("span.MuiButtonBase-root"))
                                .getAttribute("class").contains("Mui-checked")) {
                            click(weekDaysInNewCreateShiftPage.get(i).findElement(By.cssSelector("span.MuiButtonBase-root")));
                            SimpleUtils.report("Select day: " + weekDaysInNewCreateShiftPage.get(i).getText() + " Successfully!");
                        }
                        selectedCount++;
                        indexes.add(i);
                    } else {
                        int date = Integer.parseInt(weekDaysInNewCreateShiftPage.get(i).getText().substring(weekDaysInNewCreateShiftPage.get(i).getText().length() - 2).trim());
                        int cannotDate = Integer.parseInt(cannotSelectedDate.substring(cannotSelectedDate.length() - 2).trim());
                        if (date != cannotDate) {
                            if (!weekDaysInNewCreateShiftPage.get(i).getAttribute("class").contains("week-day-multi-picker-day-selected")) {
                                click(weekDaysInNewCreateShiftPage.get(i));
                                SimpleUtils.report("Select day: " + weekDaysInNewCreateShiftPage.get(i).getText() + " Successfully!");
                            }
                            selectedCount++;
                            indexes.add(i);
                        }
                    }
                    if (selectedCount == count) {
                        SimpleUtils.pass("Create New Shift: Select " + count + " days Successfully!");
                        break;
                    }
                }
            }
            if (selectedCount != count) {
                SimpleUtils.fail("Create New Shift: Failed to select " + count + " days! Actual is: " + selectedCount + " days!", false);
            }
        }else{
            SimpleUtils.fail("Weeks Days failed to load!", false);
        }
        return indexes;
    }


    @Override
    public void selectWeekDaysByDayName(String dayName) throws Exception {
        boolean isDayNameExist = false;
        if (areListElementVisible(weekDays, 5) && weekDays.size() == 7) {
            for(int i=0; i< weekDays.size(); i++){
                String weekDayName = weekDays.get(i).getText().split("\n")[0];
                if (weekDayName.equalsIgnoreCase(dayName)){
                    click(weekDays.get(i));
                    SimpleUtils.report("Select day: " + weekDays.get(i).getText() + " Successfully!");
                    isDayNameExist = true;
                    break;
                }
            }
            if (!isDayNameExist) {
                SimpleUtils.fail("This is a wrong day name: "+ dayName+ "The correct day names should be: Mon, TUE, WED, THU, FRI, SAT, SUN", true);
            }
        }else if (areListElementVisible(weekDaysInNewCreateShiftPage, 5)
                && weekDaysInNewCreateShiftPage.size() == 7) {
            for(int i=0; i< weekDaysInNewCreateShiftPage.size(); i++){
                String weekDayName = weekDaysInNewCreateShiftPage.get(i).getText().split("\n")[0];
                if (weekDayName.toLowerCase().contains(dayName.toLowerCase())){
                    click(weekDaysInNewCreateShiftPage.get(i).findElement(By.cssSelector(".MuiButtonBase-root")));
                    SimpleUtils.report("Select day: " + weekDaysInNewCreateShiftPage.get(i).getText() + " Successfully!");
                    isDayNameExist = true;
                    break;
                }
            }
            if (!isDayNameExist) {
                SimpleUtils.fail("This is a wrong day name: "+ dayName+ "The correct day names should be: Mon, TUE, WED, THU, FRI, SAT, SUN", true);
            }
        }else{
            SimpleUtils.fail("Weeks Days failed to load!", true);
        }
    }

    @Override
    public List<String> getAllOperatingHrsOnCreateShiftPage() throws Exception {
        List<String> allOperatingHrs = new ArrayList<>();
        if (areListElementVisible(scheduleOperatingHrsOnEditPage, 15)) {
            for (WebElement operatingHour : scheduleOperatingHrsOnEditPage) {
                if (operatingHour.getAttribute("class").contains("am")) {
                    allOperatingHrs.add(operatingHour.getText() + "am");
                } else {
                    allOperatingHrs.add(operatingHour.getText() + "pm");
                }
            }
        } else
            SimpleUtils.fail("The operating hours on create shift page fail to load! ", false);
        return allOperatingHrs;
    }

    @FindBy(css = "div.week-day-multi-picker-day-selected")
    private List<WebElement> selectedDaysOnCreateShiftPage;

    @Override
    public List<String> getSelectedDayInfoFromCreateShiftPage() throws Exception {
        List<String> selectedDates = new ArrayList<>();
        if (areListElementVisible(selectedDaysOnCreateShiftPage, 5) && selectedDaysOnCreateShiftPage.size()>0) {
            for (WebElement selectedDate: selectedDaysOnCreateShiftPage){
                selectedDates.add(selectedDate.getText());
            }
            SimpleUtils.pass("Get selected days info successfully");
        }else
            SimpleUtils.fail("Select days load failed",true);
        return selectedDates;
    }

    @FindBy(css = ".tma-dismiss-button")
    private WebElement closeSelectTMWindowBtn;
    @Override
    public void closeCustomizeNewShiftWindow() throws Exception {
        if (isElementLoaded(closeSelectTMWindowBtn, 10)){
            clickTheElement(closeSelectTMWindowBtn);
            waitUntilElementIsInVisible(closeSelectTMWindowBtn);
        } else {
            SimpleUtils.fail("Customize New Shift window: Close button not loaded Successfully!", false);
        }
    }

    @FindBy(css = "[ng-click=\"prevAction()\"]")
    private WebElement backButton;
    @FindBy(css = "ng-click=\"back()\"")
    private WebElement backButtonOnNewCreateShiftPage;

    @Override
    public void clickOnBackButton () throws Exception {
        if (isElementLoaded(backButton, 10)) {
            clickTheElement(backButton);
            SimpleUtils.pass("Click Back button successfully! ");
        } else if (isElementLoaded(backButtonOnNewCreateShiftPage, 5)) {
            clickTheElement(backButtonOnNewCreateShiftPage);
            SimpleUtils.pass("Click Back button successfully! ");
        }else
            SimpleUtils.fail("The Back button fail to loaded! ", false);
    }

    public boolean checkIfWarningModalDisplay () throws Exception {
        boolean checkIfWarningModalDisplay = false;
        if (isElementLoaded(popUpScheduleOverlap, 10)) {
            checkIfWarningModalDisplay = true;
        }
        return checkIfWarningModalDisplay;
    }

    @FindBy(css = ".MuiDialogContent-root p")
    private List<WebElement> warningMessagesInWarningModeOnNewCreaeShiftPage;
    public String getWarningMessageFromWarningModal () throws Exception {
        String warningMesssage ="";
        if (isElementLoaded(warningMessagesInWarningMode, 5)) {
            warningMesssage= warningMessagesInWarningMode.getText();
        } if (areListElementVisible(warningMessagesInWarningModeOnNewCreaeShiftPage, 5)) {
            for (WebElement message: warningMessagesInWarningModeOnNewCreaeShiftPage) {
                warningMesssage = warningMesssage + " " + message.getText();
            }
        }else
            SimpleUtils.fail("The warning message fail to load! ", false);
        return warningMesssage;
    }

    public void clickOnOkButtonOnWarningModal () throws Exception {
        if (isElementLoaded(okBtnInWarningMode, 5)) {
            clickTheElement(okBtnInWarningMode);
        } if (areListElementVisible(buttonsOnWarningMode, 5)) {
            clickTheElement(buttonsOnWarningMode.get(0));
        }else
            SimpleUtils.fail("The OK button fail to load! ", false);
    }

    @FindBy(className = "div.react-create-shift-modal")
    private WebElement newCreateShiftModal;

    public boolean checkIfNewCreateShiftPageDisplay() throws Exception {
        if (isElementLoaded(newCreateShiftModal, 10)) {
            SimpleUtils.pass("The new create shift modal display! ");
            return true;
        } else {
            SimpleUtils.report("The new create shift modal is not display! ");
            return false;
        }
    }
}
