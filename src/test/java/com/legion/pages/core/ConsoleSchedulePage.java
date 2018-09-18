package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;
import com.legion.pages.BasePage;
import com.legion.pages.SchedulePage;
import com.legion.tests.TestBase;
import com.legion.utils.SimpleUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConsoleSchedulePage extends BasePage implements SchedulePage {

	public ConsoleSchedulePage()
	{
//    	super(driver);
		PageFactory.initElements(getDriver(), this);
	}
	
//	@FindBy(css="div.console-navigation div:nth-child(4)")
//	private WebElement goToScheduleButton;
//	
	@FindBy(xpath="//*[@id='legion-app']/div/div[2]/div/div/div/div[1]/navigation/div/div[4]")
	private WebElement goToScheduleButton;
	
	@FindBy(xpath="//span[contains(text(),'Overview')]")
	private WebElement goToScheduleOverviewTab;
	
	@FindBy(xpath="//span[contains(text(),'Projected Sales')]")
	private WebElement goToProjectedSalesTab;
	
	@FindBy(xpath="//span[contains(text(),'Staffing Guidance')]")
	private WebElement goToStaffingGuidanceTab;
	
	@FindBy(css="div[helper-text*='Work in progress Schedule'] span.legend-label")
	private WebElement draft;
	
	@FindBy(css="div[helper-text-position='top'] span.legend-label")
	private WebElement published;
	
	@FindBy(css="div[helper-text*='final per schedule changes'] span.legend-label")
	private WebElement finalized;
	
	@FindBy(css="ui-view[name='forecastControlPanel'] span.highlight-when-help-mode-is-on")
	private WebElement salesGuidance;
	
	@FindBy(css="div[ng-click*='refresh'] span.sch-control-button-label")
	private WebElement refresh;
	
	@FindBy(xpath="//div[contains(text(),'Guidance')]")
	private WebElement guidance;
	
	@FindBy(css="div[ng-click*='analyze'] span.sch-control-button-label")
	private WebElement analyze;
	
	@FindBy(css="div[ng-click*='edit'] span.sch-control-button-label")
	private WebElement edit;
	
	@FindBy(xpath="//div[contains(@class,'sub-navigation-view')]//span[contains(text(),'Schedule')]")
	private WebElement goToScheduleTab;
	
	@FindBy(css="[ng-if='!loading']")
	private WebElement weeklyScheduleTableBodyElement;
	
	@FindBy(className="schedule-table-row")
	private List<WebElement> weeklyScheduleTableRowElement;
	
	@FindBy(css="[ng-class='nameClass()']")
	private WebElement weeklyScheduleTableLeftColumnElement;
	
	@FindBy(className="left-banner")
	private WebElement weeklyScheduleDateElement;
	
	@FindBy(css="[ng-if='!isLocationGroup()']")
	private WebElement weeklyScheduleStatusElement;
	
	@FindBy(css="[ng-if=\"controlPanel.fns.getVisibility('EDIT')\"]")
	private WebElement weeklyScheduleEditButton;
	
	@FindBy(css="[ng-if=\"controlPanel.fns.getVisibility('CANCEL')\"]")
	private WebElement weeklyScheduleCancelEditingButton;
	
	@FindBy(css="[label='okLabel()']")
	private List<WebElement> editAnywayPopupButton;
	
	@FindBy(className="ng-click='gotoNextWeek($event)']")
	private WebElement gotoNextWeekArrow;
	
	@FindBy(className="sch-shift-transpose-grid")
	private WebElement weeklyScheduleTransposeGridElement;
	
	@FindBy(css="[label='cancelLabel()']")
	private WebElement weeklyScheduleAnywayPopupCancelButton;
	
	@FindBy(css="[ng-click=\"selectDayWeekView($event, 'day')\"]")
	private WebElement dayViewButton;

	@FindBy(css="[ng-if=\"controlPanel.fns.getVisibility('EDIT')\"]")
	private WebElement dayViewEditButton;
	
	@FindBy(css="[sly-repeat=\"shiftGroup in getShiftGroups()\"]")
	private WebElement dayViewGridItems;
	
	@FindBy(className="sch-control-button-cancel")
	private WebElement dayViewEditCancelButton;
	
	@FindBy(className="sch-grid-container")
	private WebElement dayViewGridContainerElement;
	
	@FindBy(className="right-shift-box")
	private WebElement dayViewRightRowElement;
	
	@FindBy(className="right-shift-box")
	private List<WebElement> dayViewGridRightRows;
	
	@FindBy(className="left-shift-box")
	private WebElement dayViewLeftRowElement;
	
	@FindBy(className="left-shift-box")
	private List<WebElement> dayViewGridLeftRows;
	
	@FindBy(className="schedule-view")
	private WebElement ScheduleViewClassElement;
	
	@FindBy(className="sch-day-view-shift-pinch")
	private WebElement dayViewRowCardElement;
	
	@FindBy(className="tm-time-info-container")
	private WebElement dayViewTimeInfoContainerElement;
	
	@FindBy(className="sch-day-view-shift-time")
	private WebElement dayViewShiftTimeElement;
	
	@FindBy(className="overview-view")
	private WebElement overviewSectionElement;
	
	WebElement scheduleTable = null;
    List<WebElement> scheduleTableRowElement = null;
    Map<String, String> weeklyTableRowsDatesAndStatus = new LinkedHashMap<String, String>();
    WebElement element = null;
    WebElement scheduleTableLeftColumn = null;
    String scheduleWeekDate = null;
    String scheduleWeekStatus = null;
    String memberName = "NA";
	String memberJobTitle = "NA";
	String monthlyHours = "NA";
	String dailyHours = "NA";
	String overTimeHours = "NA";
	String shiftTime = "NA";

	/* Create New Shift Element/Variables */
	
	@FindBy(className="sub-navigation-view")
	private WebElement scheduleSubNavigationViewTab;
	
	@FindBy(className="sub-navigation-view")
	private List<WebElement> scheduleViewSubTabElements;
	
	@FindBy(className="[ng-if=\"canShowNewShiftButton()\"]")
	private WebElement addNewShiftOnDayViewButton;
	
	@FindBy(className="modal-content")
	private WebElement newShiftCreationModalContentElement;
	
	@FindBy(css="[attr-id=\"workerRoles\"]")
	private WebElement newShiftWorkRoleDropdownElement;

	@FindBy(className="dropdown-menu")
	private WebElement newShiftWorkRoleDropdownMenuElement;
	
	@FindBy(css="[ng-click=\"selectChoice($event, choice)\"]")
	private WebElement newShiftWorkRoleDropdownMenuItems;
	
	@FindBy(css="[ng-click=\"selectChoice($event, choice)\"]")
	private List<WebElement> newShiftRoleDropdownMenu;
	
	@FindBy(css="[ng-if=\"!isSimpleLayout\"]")
	private WebElement newShiftHoursSliderElement;
	
	@FindBy(css="[ng-if=\"!isSimpleLayout\"]")
	private List<WebElement> newShiftHoursSliderPoints;
	
	@FindBy(css="[ng-model=\"ec.originalShift.name\"]")
	private WebElement newShiftNameTextField;
	
	@FindBy(css="[ng-model=\"ec.originalShift.notes\"]")
	private WebElement newShiftNoteTextField;
	
	@FindBy(className="tma-scroll-table")
	private List<WebElement> newShiftSearchedTeamList;
	
	@FindBy(className="table-row")
	private WebElement newShiftSearchTeamMemberDetailRow;
	
	@FindBy(css="[ng-click='selectAction($event, worker)']")
	private List<WebElement> NewShiftSelectTeamMemberButton;
	
	@FindBy(css="[ng-if=\"showAssign()\"]")
	private WebElement newShiftAssignTeamMemberbutton;
	
	@FindBy(className="tma-staffing-option-choice")
	private WebElement newShiftStaffingOption;
	
	@FindBy(className="tma-staffing-option-choice")
	private List<WebElement> newShiftStaffingOptionChoice;
	
	@FindBy(css="[ng-click=\"nextAction()\"]")
	private WebElement newShiftNextButton;
	
	@FindBy(className="tma-search-container")
	private WebElement newShiftSearchBoxContainer;
	
	@FindBy(css="[ng-model=\"searchText\"]")
	private WebElement newShiftSearchTeamMemberBox;
	
	@FindBy(className="lgn-time-slider-notch-label")
	private WebElement newShiftTimeSliderNotchLabel;
	
	@FindBy(className="sch-control-button-save")
	private WebElement dayViewShiftSaveButton;
	
	@FindBy(className="lgn-time-slider")
	private WebElement newShiftTimeSlider;
	
	@FindBy(css="[ng-click=\"callOkCallback()\"]")
	private WebElement newShiftAssignTeamMemberAnywayButton;
	
	@FindBy(css="[ng-click=\"confirmSaveAction($event)\"]")
	private WebElement newShiftSaveConfirmButton;
	
	@FindBy(css="[ng-click=\"okAction($event)\"]")
	private WebElement newShiftSucessfullySavedOkButton;
	
	@FindBy(css="[ng-click=\"createAction()\"]")
	private WebElement newShiftCreateActionButton;

	String newShiftname = "This is test Shift";
	String newShiftNote = "This is Sample Note for New Shift.";
	
	@FindBy(className="sub-navigation-view")
	private List<WebElement> schedulePageSubNavigationsTabElements;
	
	@FindBy(css="div.sch-calendar-day-dimension.active-day")
	private WebElement dayViewScheduleWeekStartDay;
	
	@FindBy(css="div.sch-calendar-day-dimension.sch-calendar-day-today")
	private WebElement selectedDayOnCalender;
	
	@FindBy(className="dc-summary-box")
	private WebElement today;
	
	@FindBy(className="dc-summary-slot")
	private List<WebElement> dcSummaryBoxDivElements;
	
	@FindBy(className="mt-18")
	private List<WebElement> budgetedScheduledLabelsDivElement;
	
	@FindBy(className="sf-summary-container")
	private WebElement dayViewScheduleProjectedSalesSummaryContainer;
	
	@FindBy(className="kpi")
	private List<WebElement> dayViewProjectedSalesCards;
	
	@FindBy(className="summary-label")
	private List<WebElement> dayViewprojectedSalesCardsLabel;
	
	@FindBy(className="data-item")
	private List<WebElement> dayViewProjectedSalesCardsData;
	
	
	

    
	public void gotoToSchedulePage() throws Exception {

		checkElementVisibility(goToScheduleButton);
        click(goToScheduleButton);
        pass("Schedule Page Loading..!");
        
        if(isElementLoaded(draft)){
        	pass("Draft is Displayed on the page");
        }else{
        	SimpleUtils.fail("Draft not displayed on the page",true);
        }
        
        if(isElementLoaded(published)){
        	pass("Published is Displayed on the page");
        }else{
        	SimpleUtils.fail("Published not displayed on the page",true);
        }
        
        if(isElementLoaded(finalized)){
        	pass("Finalized is Displayed on the page");
        }else{
        	SimpleUtils.fail("Finalized not displayed on the page",true);
        }
        
  
    }

    public boolean isSchedulePage() throws Exception {

        //	Reporter.log("isSchedulePage method Called..!");
        if(isElementLoaded(overviewSectionElement)){
        	return true;
        }else{
        	return false;
        }
           
    }
    
    public void verifySchedulePageLoadedProperly(boolean isSchedulePageLoaded){
    	if(isSchedulePageLoaded){
    		pass("Schedule Page Loaded Successfully!");
    	}else{
    		SimpleUtils.fail("Schedule Page not Loaded Successfully!",false);
    	}
    }


    public void goToProjectedSales() throws Exception {

    	checkElementVisibility(goToProjectedSalesTab);
        click(goToProjectedSalesTab);
        pass("ProjectedSales Page Loading..!");

        if(isElementLoaded(salesGuidance)){
        	pass("SalesGuidance is Displayed on the page");
        }else{
        	SimpleUtils.fail("SalesGuidance not Displayed on the page",true);
        }
        
        if(isElementLoaded(refresh)){
        	pass("Refresh is Displayed on the page");
        }else{
        	SimpleUtils.fail("Refresh not Displayed on the page",true);
        }
       
    }

  


    public void goToStaffingGuidance() throws Exception {

    	checkElementVisibility(goToStaffingGuidanceTab);
        click(goToStaffingGuidanceTab);
        pass("StaffingGuidance Page Loading..!");

        if(isElementLoaded(guidance)){
        	pass("Guidance is Displayed on Staffing Guidance page");
        }else{
        	SimpleUtils.fail("Guidance not Displayed on Staffing Guidance page",true);
        }
        
        if(isElementLoaded(analyze)){
        	pass("Analyze is Displayed on Staffing Guidance page");
        }else{
        	SimpleUtils.fail("Analyze not Displayed on Staffing Guidance page",true);
        }

    }

 

    public void goToSchedule() throws Exception {

    	checkElementVisibility(goToScheduleTab);
        click(goToScheduleTab);
        pass("Schedule Page Loading..!");
        
        if(isElementLoaded(analyze)){
        	pass("Analyze is Displayed on Schdule page");
        }else{
        	SimpleUtils.fail("Analyze not Displayed on Schedule page",true);
        }
        
//        if(isElementLoaded(refresh)){
//        	pass("Refresh is Displayed on Schedule page");
//        }else{
//        	fail("Refresh not Displayed on Schedule page",false);
//        }
        
        if(isElementLoaded(edit)){
        	pass("Edit is Displayed on Schedule page");
        }else{
        	SimpleUtils.fail("EDit not Displayed on Schedule page",true);
        }


    }

    public boolean isSchedule() throws Exception {
        if(isElementLoaded(goToScheduleTab)){
        	return true;
        }else{
        	return false;
        }
        
    }
    
    public void verifyScheduleTabLoaded(boolean isScheduleTab){
    	if(isScheduleTab){
    		pass("Schedule Tab loaded Successfully!");
    	}else{
    		SimpleUtils.fail("Schedule Tab not loaded Successfully!",false);
    	}
    }

    @Override
    //public boolean editAnywayPopupTest() throws Exception {
    //todo change method to edit published week schedule
    public boolean editWeeklySchedule(HashMap<String,String> propertyMap) throws Exception {
    	if(isElementLoaded(goToScheduleButton))
    	{
    		click(goToScheduleButton);
            // todo please declare all elements outside the page object method
            // todo add a property named setting.schedulingWindow and instead of keep going all the schedule table row, go only by setting.schedulingWindow
    		if(isElementLoaded(weeklyScheduleTableBodyElement) && isElementLoaded(weeklyScheduleTableRowElement.get(0)))
    		{
//    			scheduleTable = getDriver().findElement(weeklyScheduleTableBodyElement);
//                scheduleTableRowElement = weeklyScheduleTableBodyElement.findElements(weeklyScheduleTableRowElement);
                for (WebElement ScheduleTableRow : weeklyScheduleTableRowElement) {
                    scheduleWeekDate = weeklyScheduleDateElement.getText();
                    scheduleWeekStatus = weeklyScheduleStatusElement.getText();
                    //todo filter by ScheduleWeekStatus. Only Finalized or Published should be added to map
                    //todo return if there are no finalized or published week found
                    weeklyTableRowsDatesAndStatus.put(scheduleWeekDate.replace("\n", ""), scheduleWeekStatus.replace("\n", ""));
                }
                weeklyScheduleTableRowElement.get(0).click();
                for (Map.Entry<String, String> tableRowIndex : weeklyTableRowsDatesAndStatus.entrySet()) {
                	if(isElementLoaded(weeklyScheduleEditButton) && (tableRowIndex.getValue().contains("Finalized") 
                			|| tableRowIndex.getValue().contains("Published")))
                	{
                		//System.out.println("Under If Condition: "+tableRowIndex.getValue());
                        click(weeklyScheduleEditButton);
                        if (editAnywayPopupButton.size() != 0) {
                        	pass("Weekly Schedule Popup Loaded!");
                            click(weeklyScheduleAnywayPopupCancelButton);
                            TestBase.extentTest.log(Status.INFO, "Edit Anyway Model found for Week - '" + tableRowIndex.getKey() + "' & Status - '" + tableRowIndex.getValue() + "'");
                       
                            if(isElementLoaded(weeklyScheduleCancelEditingButton))
                            {
                                click(weeklyScheduleCancelEditingButton);
                            }
                        }
                	}
                	else
                	{
                		TestBase.extentTest.log(Status.INFO, "Edit Anyway Model not found for Week - '" + tableRowIndex.getKey() + "' & Status - '" + tableRowIndex.getValue() + "'");
                	}
                	if(isElementLoaded(gotoNextWeekArrow))
                    {
                        Actions action = new Actions(getDriver());
                        action.moveToElement(gotoNextWeekArrow).click().perform();
                      
                    }
                    
                }
    		}
            
    	}
        
        return true;
    }
    
   /* public boolean isElementLoaded(By element) throws Exception
    {
    	WebDriverWait tempWait = new WebDriverWait(driver, 30); 
    	try {
    	    tempWait.until(ExpectedConditions.presenceOfElementLocated(element)); 
    	    return true;
    	}
    	catch (TimeoutException te) {
    		return false;
    	}
    }*/
    
    /*****************Added on Aug, 17 ******************/
//	public void editDayViewSchedule() throws Exception
//	{
//		//waitForElement(goToScheduleButton);
//		if(isElementLoaded(goToScheduleButton))
//		{
//			click(goToScheduleButton);
//			//waitForElement(weeklyScheduleTableRowElement);
//			if(isElementLoaded(weeklyScheduleTableRowElement) && isElementLoaded(weeklyScheduleTableBodyElement))
//			{
//				WebElement ScheduleTable = driver.findElement(weeklyScheduleTableBodyElement);
//				List<WebElement> scheduleTableRowElement = ScheduleTable.findElements(weeklyScheduleTableRowElement);
//				scheduleTableRowElement.get(0).click();
//				//waitForElement(weeklyScheduleTransposeGridElement);
//				if(isElementLoaded(weeklyScheduleTransposeGridElement))
//				{
//					click(dayViewButton);
//					//waitForElement(dayViewGridItems);
//					if(isElementLoaded(dayViewGridItems))
//					{
//						click(dayViewEditButton);
//						if(driver.findElements(editAnywayPopupButton).size() != 0)
//						{
//							click(editAnywayPopupButton);
//							driver.switchTo().activeElement();
//							driver.switchTo().defaultContent();
//						}
//						//waitForElement(dayViewGridContainerElement);
//						if(isElementLoaded(dayViewGridContainerElement))
//						{
//							WebElement rootDayViewGrid = driver.findElement(dayViewGridContainerElement)
//					        		.findElement(By.cssSelector("[sly-repeat=\"shiftGroup in getShiftGroups()\"]"));
//					        Thread.sleep(2000);
//					        List<WebElement> dayViewGridRightRows = driver.findElements(dayViewRightRowElement);
//					        List<WebElement> dayViewGridLeftRows = driver.findElements(dayViewLeftRowElement);
//					        WebElement gridElement = driver.findElement(ScheduleViewClassElement);
//					        int dailyCardXOffSetRightSide = ((gridElement.getSize().getWidth()+gridElement.getLocation().getX()) * 90) / 100;
//					        System.out.println("gridElement.getSize().getWidth()");
//					        System.out.println(gridElement.getSize().getWidth());
//					        System.out.println("gridElement.getLocation().getX()");
//					        System.out.println(gridElement.getLocation().getX());
//					        System.out.println("dailyCardXOffSetRightSide");
//					        System.out.println(dailyCardXOffSetRightSide);
//					        for (WebElement dayViewGridRow: dayViewGridRightRows)
//					        {       
//					        	WebElement dayViewGridRowCard = dayViewGridRow.findElement(dayViewRowCardElement);
//					    		int leftXOffSetCard = dayViewGridLeftRows.get(dayViewGridRightRows.indexOf(dayViewGridRow)).findElement(dayViewRowCardElement).getLocation().getX();
//					    		int rightXOffSetCard = dayViewGridRowCard.getLocation().getX();
//					    		int expectedCardLength = (gridElement.getSize().getWidth() * 55) / 100;//525;
//					    		int moveCardLength = ((leftXOffSetCard + expectedCardLength) -rightXOffSetCard);
//					        	if(dayViewGridRowCard.getLocation().getX()+moveCardLength < dailyCardXOffSetRightSide)
//					        	{
//					        		moveDayViewCards(dayViewGridRowCard, moveCardLength);
//					        	}
//					        	else
//					        	{
//					        		dayViewGridRowCard = dayViewGridLeftRows.get(dayViewGridRightRows.indexOf(dayViewGridRow)).findElement(dayViewRowCardElement);
//					        		moveCardLength = - moveCardLength;
//					        		moveDayViewCards(dayViewGridRowCard, moveCardLength);
//					        	}
//					        	
//					        	/*if (dayViewGridRow.getSize().getWidth() > 0 && dayViewGridRow.findElements(dayViewTimeInfoContainerElement).size() != 0)
//					        	{
//					        		String[] memberDetails = dayViewGridLeftRows.get(dayViewGridRightRows.indexOf(dayViewGridRow)).getText().split("\n");
//					        		System.out.println("memberDetails.length: "+memberDetails.length);
//					        		System.out.println("Under If Condition..............................................");
//					        		WebElement dayViewMemberShiftTime = dayViewGridRow.findElement(dayViewShiftTimeElement);
//					            	WebElement dayViewMemberTimeinfoContainer = dayViewGridRow.findElement(dayViewTimeInfoContainerElement);
//					            	String[] memberTimeInfo = dayViewMemberTimeinfoContainer.getText().split(Pattern.quote("|"));
//					            	shiftTime = dayViewMemberShiftTime.getText();
//					            	if(memberTimeInfo.length == 2)
//					            		monthlyHours = memberTimeInfo[1];
//					            	String[] OTAndDaily = memberTimeInfo[0].split("Hrs");
//					        		dailyHours = OTAndDaily[0] + "Hrs";
//					            	if(OTAndDaily.length == 2 && OTAndDaily[1].contains("OT"))
//					            	    overTimeHours = OTAndDaily[1];
//					            	if(memberDetails.length == 4)
//					            	{
//					            		memberName = memberDetails[2];
//					            		memberJobTitle = memberDetails[3];
//					            	}
//					            	else if(memberDetails.length == 5)
//					            	{
//					            		memberName = memberDetails[3];
//					            		memberJobTitle = memberDetails[4];
//					            	}
//					            	dayViewMoveCardLogs(memberName, memberJobTitle, shiftTime, dailyHours, monthlyHours, overTimeHours);
//					        	}
//					        	else
//					        	{
//					        		String[] memberDetails = dayViewGridLeftRows.get(dayViewGridRightRows.indexOf(dayViewGridRow)).getText().split("\n");
//					        		System.out.println("memberDetails.length: "+memberDetails.length);
//					        		//String[] dayViewCardDetails = dayViewGridLeftRows.get(dayViewGridRightRows.indexOf(dayViewGridRow)).getText().split("\n");
//					        		if(memberDetails.length == 4)
//					        		{
//					        			memberName = memberDetails[2];
//					        			memberJobTitle = memberDetails[3];
//					        			shiftTime = memberDetails[0] + " " + memberDetails[1] + " - " + dayViewGridRow.getText().replace("\n", " ");
//					        		}
//					        		else if(memberDetails.length == 5)
//					        		{
//					        			memberName = memberDetails[3];
//					        			memberJobTitle = memberDetails[4];
//					        			shiftTime = memberDetails[0] + " " + memberDetails[1] + " - " + dayViewGridRow.getText().replace("\n", " ");
//					        		}
//					    			dayViewMoveCardLogs(memberName, memberJobTitle,shiftTime,"NA","NA","NA");
//					        	}*/
//					        }
//					        logDayViewShiftsDetails();
//					        //click(dayViewEditCancelButton);
//					        if(isElementLoaded(dayViewEditCancelButton))
//							{
//								 click(dayViewEditCancelButton);
//							}
//						}
//				        
//					}
//				}
//				
//			}
//			
//		}
//		
//	}
	
	
//	public void logDayViewShiftsDetails() throws Exception
//	{
//		System.out.println("**************** logDayViewShiftsDetails() ********************");
//		if(isElementLoaded(dayViewRightRowElement))
//		{
//	        for (WebElement dayViewGridRow: dayViewGridRightRows)
//	        {
//	        	if (dayViewGridRow.getSize().getWidth() > 0 && dayViewGridRow.findElements(dayViewTimeInfoContainerElement).size() != 0)
//	        	{
//	        		String[] memberDetails = dayViewGridLeftRows.get(dayViewGridRightRows.indexOf(dayViewGridRow)).getText().split("\n");
//	        		System.out.println("memberDetails.length: "+memberDetails.length);
//	        		System.out.println("Under If Condition..............................................");
//	        		WebElement dayViewMemberShiftTime = dayViewGridRow.findElement(dayViewShiftTimeElement);
//	            	WebElement dayViewMemberTimeinfoContainer = dayViewGridRow.findElement(dayViewTimeInfoContainerElement);
//	            	String[] memberTimeInfo = dayViewMemberTimeinfoContainer.getText().split(Pattern.quote("|"));
//	            	shiftTime = dayViewMemberShiftTime.getText();
//	            	if(memberTimeInfo.length == 2)
//	            		monthlyHours = memberTimeInfo[1];
//	            	String[] OTAndDaily = memberTimeInfo[0].split("Hrs");
//	        		dailyHours = OTAndDaily[0] + "Hrs";
//	            	if(OTAndDaily.length == 2 && OTAndDaily[1].contains("OT"))
//	            	    overTimeHours = OTAndDaily[1];
//	            	if(memberDetails.length == 4)
//	            	{
//	            		memberName = memberDetails[2];
//	            		memberJobTitle = memberDetails[3];
//	            	}
//	            	else if(memberDetails.length == 5)
//	            	{
//	            		memberName = memberDetails[3];
//	            		memberJobTitle = memberDetails[4];
//	            	}
//	            	dayViewMoveCardLogs(memberName, memberJobTitle, shiftTime, dailyHours, monthlyHours, overTimeHours);
//	        	}
//	        	else
//	        	{
//	        		String[] memberDetails = dayViewGridLeftRows.get(dayViewGridRightRows.indexOf(dayViewGridRow)).getText().split("\n");
//	        		System.out.println("memberDetails.length: "+memberDetails.length);
//	        		//String[] dayViewCardDetails = dayViewGridLeftRows.get(dayViewGridRightRows.indexOf(dayViewGridRow)).getText().split("\n");
//	        		if(memberDetails.length == 4)
//	        		{
//	        			memberName = memberDetails[2];
//	        			memberJobTitle = memberDetails[3];
//	        			shiftTime = memberDetails[0] + " " + memberDetails[1] + " - " + dayViewGridRow.getText().replace("\n", " ");
//	        		}
//	        		else if(memberDetails.length == 5)
//	        		{
//	        			memberName = memberDetails[3];
//	        			memberJobTitle = memberDetails[4];
//	        			shiftTime = memberDetails[0] + " " + memberDetails[1] + " - " + dayViewGridRow.getText().replace("\n", " ");
//	        		}
//	    			dayViewMoveCardLogs(memberName, memberJobTitle,shiftTime,"NA","NA","NA");
//	        	}
//	        }
//		}
//		getTeamCountsOnScheduleDayView();
//		
//	}
//	
	
	
	
	public void moveDayViewCards(WebElement webElement, int xOffSet)
	{
		Actions builder = new Actions(driver);
		builder//.clickAndHold(dayViewGridRow)
		.moveToElement(webElement)
	         .clickAndHold()
	         .moveByOffset(xOffSet, 0)
	         .release()
	         .build()
	         .perform();
	}
	
	public void dayViewMoveCardLogs(String memberName, String memberJobTitle, String shiftTime, String dailyHours, String monthlyHours, String overTimeHours)
	{
		/* Printing the logs Summery */
    	Reporter.log("**************************************************************************************************************");
		Reporter.log("Name: "+ memberName);
		Reporter.log("Job Title: " + memberJobTitle);
		Reporter.log("Shift Time: " + shiftTime);
		Reporter.log("Daily Hours: " + dailyHours);
		Reporter.log("Monthly Hours: " + monthlyHours);
		Reporter.log("Over Time Hours: " + overTimeHours);
		Reporter.log("**************************************************************************************************************\n");
	}
	
	public void getTeamCountsOnScheduleDayView()
	{
		String dayViewShiftTimes = "";
		String guidanceRequiredTeamCounts = "";
		String availableTeamCounts = "";
		String saparator = "|";
		System.out.println("******************* Team Counts On Schedule Day View Start *********************");
		List<WebElement> teamGuidanceTimeCounts = getDriver().findElements(By.cssSelector("div.sch-day-view-grid-header.fill.ng-scope"));
        System.out.println("TeamGuidanceTimeCounts Counts: "+ teamGuidanceTimeCounts.size());
        dayViewShiftTimes = getListElementsTextAsString(teamGuidanceTimeCounts,saparator);
        /*for(WebElement teamGuidanceTimeCount: teamGuidanceTimeCounts)
        {
        	dayViewShiftTimes = dayViewShiftTimes +" | " + teamGuidanceTimeCount.getText();
        }*/
        System.out.println("Day View Shift Times : "+dayViewShiftTimes);
        
		List<WebElement> guidanceTeamCounts = getDriver().findElements(By.cssSelector("div.tm-count.guidance"));
        System.out.println("guidanceTeamCounts Counts: "+ guidanceTeamCounts.size());
        guidanceRequiredTeamCounts = getListElementsTextAsString(guidanceTeamCounts,saparator);
        /*for(WebElement guidanceTeamCount: guidanceTeamCounts)
        {
        	guidanceRequiredTeamCounts = guidanceRequiredTeamCounts +" | " + guidanceTeamCount.getText();
        }*/
        System.out.println("Guidance Required Team Counts : "+guidanceRequiredTeamCounts);

        List<WebElement> actualTeamCounts = driver.findElements(By.cssSelector("div[class*='tm-count ng-scope']")/*By.cssSelector("div.tm-count.ng-scope")*/);
        System.out.println("actualTeamCounts Counts: "+ actualTeamCounts.size());
        availableTeamCounts = getListElementsTextAsString(actualTeamCounts,saparator);

        /*for(WebElement actualTeamCount: actualTeamCounts)
        {
        	availableTeamCounts = availableTeamCounts +" | " + actualTeamCount.getText();
        }*/
        System.out.println("Available Team Counts : "+availableTeamCounts);
		System.out.println("******************* Team Counts On Schedule Day View End *********************");

	}
	
	public void createShiftOnDayViewSchedulePage(HashMap<String,String> propertyMap, String staffingOption) throws Exception
	{
		String currentUserName = propertyMap.get("DEFAULT_USERNAME");
		String[] searchTeamMemberKeyword = currentUserName.split("\\.");
		click(goToScheduleButton);
		if(isElementLoaded(scheduleSubNavigationViewTab))
		{
			
			if(scheduleViewSubTabElements.size() == 4)
			{
				scheduleViewSubTabElements.get(3).click();
				if(isElementLoaded(dayViewButton))
				{
					click(dayViewButton);
					if(isElementLoaded(dayViewEditButton))
					{
						Reporter.log("****************************Day View Data Before Save***********************");
						logDayViewData();
						click(dayViewEditButton);
						if(isElementLoaded(addNewShiftOnDayViewButton))
						{
							click(addNewShiftOnDayViewButton);
							if(isElementLoaded(newShiftCreationModalContentElement))
							{
								/* Assigning Work Role Here */
								click(newShiftWorkRoleDropdownElement);
								if(isElementLoaded(newShiftWorkRoleDropdownMenuElement))
								{
		
									if(newShiftRoleDropdownMenu.size() > 6)
									{
										newShiftRoleDropdownMenu.get(5).click();
									}
									else if(newShiftRoleDropdownMenu.size() > 0)
									{
										newShiftRoleDropdownMenu.get(0).click();
									}
								}
								/* Giving Shift Name & Note value */
								newShiftNameTextField.sendKeys(newShiftname);
								newShiftNoteTextField.sendKeys(newShiftNote);
								
								/* Adding Work Hours Here */
								TestBase.extentTest.log(Status.INFO,"newShiftHoursSliderPoints Count: "+newShiftHoursSliderPoints.size());
								if(newShiftHoursSliderPoints.size() == 2)
								{
									int SliderXOffSet = 0;
									TestBase.extentTest.log(Status.INFO,"newShiftHoursSliderPoints.get(1).getText(): "+newShiftHoursSliderPoints.get(1).getText());
									//while(! newShiftHoursSliderPoints.get(1).getText().contains("7:00PM"))
									{
										TestBase.extentTest.log(Status.INFO,"Under While: "+SliderXOffSet);
										int SliderXOffSetStartPoint = 50;
										int SliderXOffSetEndPoint = 550;
										//if(isElementLoaded(newShiftHoursSliderElement))
										waitForSeconds(2);
										moveDayViewCards(newShiftHoursSliderPoints.get(1), SliderXOffSetStartPoint);
										waitForSeconds(2);
										moveDayViewCards(newShiftHoursSliderPoints.get(0), SliderXOffSetStartPoint);
									}
								}
								/*WebElement newShiftTimeSliderElement = driver.findElement(newShiftTimeSlider);
								List<WebElement> NewShiftTimeSliderElementTimeValues = newShiftTimeSliderElement.findElements(newShiftTimeSliderNotchLabel);
					            System.out.println("NewShiftTimeSliderElementTimeValues.size(): "+NewShiftTimeSliderElementTimeValues.size());
								if(NewShiftTimeSliderElementTimeValues.size() > 20)
								{
									int StartingPoint = 5;
									NewShiftTimeSliderElementTimeValues.get(StartingPoint + 8).click();
									NewShiftTimeSliderElementTimeValues.get(StartingPoint).click();

								}*/
								
								/* Selecting Staffing Option */
								if(staffingOption.contains("Auto"))
								{
									newShiftStaffingOptionChoice.get(0).click();
									if(isElementLoaded(newShiftCreateActionButton))
								    {
								    	click(newShiftCreateActionButton);
								    	waitForSeconds(1);
								    }
								}
								else if(staffingOption.contains("Manual"))
								{
									newShiftStaffingOptionChoice.get(1).click();
									assignTemMemberOnCreatingNewShiftDayView(searchTeamMemberKeyword[0]);
								}
								else
								{
									newShiftStaffingOptionChoice.get(newShiftStaffingOptionChoice.size() - 1).click();
									assignTemMemberOnCreatingNewShiftDayView(searchTeamMemberKeyword[0]);
								}
							}
						}
					}
					
					/* Click On Save Button */
					TestBase.extentTest.log(Status.INFO,"****************************Day View Data After Save***********************");
//					 logDayViewShiftsDetails();
					 if(isElementLoaded(dayViewShiftSaveButton))
					 {
						waitForSeconds(2);
						 click(dayViewShiftSaveButton);
						 waitForSeconds(2);
						 if(isElementLoaded(newShiftSaveConfirmButton))
	    				 {
	    					click(newShiftSaveConfirmButton);
	    					waitForSeconds(2);
	    					if(isElementLoaded(newShiftSucessfullySavedOkButton))
	    					{
	    						click(newShiftSucessfullySavedOkButton);
	    						waitForSeconds(2);
	    						logDayViewData();
	    					}
	    				 }
						 else
						 {
							 waitForSeconds(2);
							 logDayViewData();
						 }
						
						 
					 }
					 /*if(isElementLoaded(dayViewEditCancelButton))
					 {
						 Thread.sleep(2000);
						 click(dayViewEditCancelButton);
					 }*/

				}
			}
		}
	}
	
	public void logDayViewData() throws Exception {
		
		String BudgetedHours = "NA";
		String ScheduledHours = "NA";
		String OtherHours = "NA";
		String wagesBudgetedCount = "NA";
		String wagesScheduledCount = "NA";
		if(isElementLoaded(budgetedScheduledLabelsDivElement.get(0)))
		{
			for(WebElement budgetedScheduledLabelDiv : budgetedScheduledLabelsDivElement)
			{
				waitForSeconds(3);
				pass("Actual Value of DIV: " + budgetedScheduledLabelDiv.getText());
					if(budgetedScheduledLabelDiv.getText().contains("Wages") && budgetedScheduledLabelDiv.getText().contains("Budgeted") )
					{
						wagesBudgetedCount = budgetedScheduledLabelDiv.findElement(By.className("sch-control-kpi")).getText();
					}
					else if(budgetedScheduledLabelDiv.getText().contains("Wages") && budgetedScheduledLabelDiv.getText().contains("Scheduled") )
					{
						wagesScheduledCount = budgetedScheduledLabelDiv.findElement(By.className("sch-control-kpi")).getText();
					}
					else if(budgetedScheduledLabelDiv.getText().contains("Budgeted") || budgetedScheduledLabelDiv.getText().contains("Guidance"))
					{
						BudgetedHours = budgetedScheduledLabelDiv.findElement(By.className("sch-control-kpi")).getText();
					}
					else if(budgetedScheduledLabelDiv.getText().contains("Scheduled"))
					{
						ScheduledHours = budgetedScheduledLabelDiv.findElement(By.className("sch-control-kpi")).getText();
					}
					else if(budgetedScheduledLabelDiv.getText().contains("Other"))
					{
						OtherHours = budgetedScheduledLabelDiv.findElement(By.className("sch-control-kpi")).getText();
					}
				/*}*/
			}
		}
		 pass("* BudgetedHours:  *"+ BudgetedHours);
		 pass("* ScheduledHours:  *"+ ScheduledHours);
		 pass("* OtherHours:  *"+ OtherHours);
		 pass("* wagesBudgetedCount:  *"+ wagesBudgetedCount);
		 pass("* wagesScheduledCount:  *"+ wagesScheduledCount);		
 	}
	
	public void assignTemMemberOnCreatingNewShiftDayView(String keywordToSearchTeamMember) throws Exception
	{
		keywordToSearchTeamMember = "admin";
		if(isElementLoaded(newShiftNextButton))
	    {
	    	click(newShiftNextButton);
	    	if(isElementLoaded(newShiftSearchBoxContainer))
	    	{
	    		waitForSeconds(3);
//	    		WebElement newShiftSearchTeamElement = driver.findElement(newShiftSearchTeamMemberBox);
	    		newShiftSearchTeamMemberBox.sendKeys(keywordToSearchTeamMember);
	    		newShiftSearchTeamMemberBox.submit();
	    		if(isElementLoaded(newShiftSearchedTeamList.get(0)))
	    		{
	    			if(newShiftSearchedTeamList.size() > 0 )
	    			{
	    				NewShiftSelectTeamMemberButton.get(0).click();
	    				waitForSeconds(2000);
	    				if(isElementLoaded(newShiftAssignTeamMemberAnywayButton))
						 {
							click(newShiftAssignTeamMemberAnywayButton);
						 }
	    				
	    				if(isElementLoaded(newShiftAssignTeamMemberbutton))
	    				{
	    					waitForSeconds(2000);
	    					click(newShiftAssignTeamMemberbutton);
	    				}
	    			}
	    		}
	    	}
	    }
	}
	
	

	
	public void dayViewScheduleProjectedSales() throws Exception
	{
		click(goToScheduleButton);	
		
		if(isElementLoaded(schedulePageSubNavigationsTabElements.get(0)))
		{
			for(WebElement schedulePageSubNavigationsTabElement:schedulePageSubNavigationsTabElements)
			{
				if (schedulePageSubNavigationsTabElement.getText().contains("PROJECTED SALES"))
				{
					schedulePageSubNavigationsTabElement.click();
					if(isElementLoaded(dayViewButton))
					{
						click(dayViewButton);
						if(isElementLoaded(ScheduleViewClassElement))
						{
							for(int i=0; i<dayViewProjectedSalesCards.size();i++)
							{
								if(isElementLoaded(dayViewprojectedSalesCardsLabel.get(0)))
								{
									System.out.println("Card Label: "+dayViewprojectedSalesCardsLabel.get(i).getText().replace("\n", " "));
									System.out.println("cardDataElement "+dayViewProjectedSalesCardsData.get(i).getText());
									
								}
								
							}
						}
					}
				}
			}
		}
	}
	
	public String getListElementsTextAsString(List<WebElement> webElements, String saparator) {
		String webElementsTextString = "";
		for(WebElement webElement: webElements)
        {
			webElementsTextString = webElementsTextString + " " + saparator + " " + webElement.getText();
        }
		return webElementsTextString;
	}

}
