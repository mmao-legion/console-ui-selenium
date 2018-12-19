package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.legion.pages.BasePage;
import com.legion.pages.TimeSheetPage;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;

public class ConsoleTimeSheetPage extends BasePage implements TimeSheetPage{

	@FindBy(css = "div.console-navigation-item-label.Timesheet")
	private WebElement timeSheetConsoleMenuDiv;
	
	@FindBy(css = "div.header-navigation-label")
	private WebElement timeSheetPageHeaderLabel;
	
	@FindBy(css = "div.lg-timesheet-table__grid-row.lg-timesheet-table__worker-row")
	private List<WebElement> timeSheetWorkersRows;
	
	@FindBy(css = "div.lg-timesheet-table")
	private WebElement timesheetTable;
	
	@FindBy(css = "div.lg-timesheet-table__grid-row.lg-timesheet-table__worker-day")
	private List<WebElement> timeSheetTableWorkersDayRows;
	
	@FindBy(css = "div.timesheet-details-modal")
	private WebElement timeSheetDetailModel;
	
	@FindBy(css = "[ng-repeat=\"clockIn in $ctrl.clockIns\"]")
	private List<WebElement> timeSheetDetailPopUpClocks;
	
	@FindBy(css = "lg-button[label=\"Delete\"]")
	private WebElement timeSheetDeleteClockBtn;
	
	@FindBy(css = "lg-button[label=\"Yes\"]")
	private WebElement confirmDeleteShiftYesBtn;
	
	@FindBy(className = "timesheet-details-modal__close")
	private WebElement timeSheetDetailsPopupCloseBtn;
	
	@FindBy(css = "div.lg-timeclocks__alert")
	private List<WebElement> timeClockAlertIcons;
	
	@FindBy(css="div.popover.fade.in")
	private WebElement clickPopover;
	
	
	String timeSheetHeaderLabel = "Timesheet";
	public ConsoleTimeSheetPage(){
		PageFactory.initElements(getDriver(), this);
	}
	
	

	@Override
	public void clickOnTimeSheetConsoleMenu() throws Exception {
		if(isElementLoaded(timeSheetConsoleMenuDiv))
			click(timeSheetConsoleMenuDiv);
		else
			SimpleUtils.fail("Timesheet Console Menu not loaded Successfully!", false);
	}
	
	@Override
	public boolean isTimeSheetPageLoaded() throws Exception
	{
		if(isElementLoaded(timeSheetPageHeaderLabel))
			if(timeSheetPageHeaderLabel.getText().toLowerCase().contains(timeSheetHeaderLabel.toLowerCase())) {
				SimpleUtils.pass("Time Sheet Page loaded Successfully!");
				return true;
			}
				
		return false;
	}
	
	
	@Override
	public void OpenATimeSheetWithClockInAndOut() throws Exception
	{
		if(isElementLoaded(timesheetTable))
		{
			if(timeSheetWorkersRows.size() != 0) {
				for(WebElement workerRow: timeSheetWorkersRows)
				{
					String[] workerRowColumnsText =  workerRow.getText().split("\n");
					int regularHours = Integer.valueOf(workerRowColumnsText[4]);
					if(regularHours > 0)
					{
						click(workerRow);
						List<WebElement> displayedWorkersDayRows = getTimeSheetDisplayedWorkersDayRows();
						for(WebElement activeRow: displayedWorkersDayRows)
						{
							String[] activeRowColumnText = activeRow.getText().split("\n");
							int activeRowRegularHours = Integer.valueOf(activeRowColumnText[2]);
							if(activeRowRegularHours > 0)
							{
								WebElement activeRowPopUpLink = activeRow.findElement(By.cssSelector("lg-button[action-link]"));
								if(isElementLoaded(activeRowPopUpLink))
								{
									click(activeRowPopUpLink);
									SimpleUtils.pass("Timesheet Details edit view popup loaded!");
									break;
								}
							}
						}
						break;
					}
				}
			}
			else {
				SimpleUtils.fail("Time Sheet Page: No Workers row found!", false);
			}
		}
		else {
			SimpleUtils.fail("Time Sheet Page: Workers Table not Loaded successfully!", false);
		}
		
	}
	
	public List<WebElement> getTimeSheetDisplayedWorkersDayRows()
	{
		List<WebElement> displayedDayRows = new ArrayList<WebElement>();
		if(timeSheetTableWorkersDayRows.size() != 0) {
			for(WebElement workersDayRow : timeSheetTableWorkersDayRows) {
				if(! workersDayRow.getAttribute("class").contains("ng-hide"))
					displayedDayRows.add(workersDayRow);
			}
		}
		return displayedDayRows;
	}

	
	@Override
	public void clickOnEditTimesheetClock(int index) throws Exception
	{
		if(isElementLoaded(timeSheetDetailModel)) {
			if(timeSheetDetailPopUpClocks.size() != index) {
				WebElement shiftStartClockEditBtn = timeSheetDetailPopUpClocks.get(index).findElement(By.cssSelector("lg-button[label=\"Edit\"]"));
				if(isElementLoaded(shiftStartClockEditBtn))
				{
					click(shiftStartClockEditBtn);
					SimpleUtils.pass("Timesheet clock edit button clicked.");
				}
				else {
					SimpleUtils.fail("TimeSheet Detail PopUp Clocks Edit button not Found!", false);
				}
			}
			else {
				SimpleUtils.fail("Timesheet Popup No Clock entry found!",false);
			}
		}
		else {
			SimpleUtils.fail("Time Sheet Detail Model/Popup not displayed!", false);
		}
	}
	
	
	@Override
	public void clickOnDeleteClockButton() throws Exception
	{
		if(isElementLoaded(timeSheetDeleteClockBtn)) {
			click(timeSheetDeleteClockBtn);
			SimpleUtils.pass("Clicked on delete Clock button.");
			if(isElementLoaded(confirmDeleteShiftYesBtn)) {
				click(confirmDeleteShiftYesBtn);
				SimpleUtils.pass("Clicked on 'Yes' buttom to Confirm Clock delete action.");
			}
		}
		else {
			SimpleUtils.fail("Delete Shift Clock button not loaded successfully!", false);
		}
	}
	

	@Override
	public void closeTimeSheetDetailPopUp() throws Exception
	{
		if(isElementLoaded(timeSheetDetailsPopupCloseBtn))
		{
			click(timeSheetDetailsPopupCloseBtn);
			SimpleUtils.pass("Timesheet Details Edit popup Closed successfully.");
		}
		else
			SimpleUtils.fail("Timesheet Detail Popup Close Button not found!", true);
	}
	
	public ArrayList<String> hoverOnClockIconAndGetInfo() throws Exception
	{
		ArrayList<String> clocksInfo = new ArrayList<String>();
		if(timeClockAlertIcons.size() != 0)
		{
			for(WebElement timeClockAlertIcon: timeClockAlertIcons)
			{
				Actions builder = new Actions(MyThreadLocal.getDriver());
				builder.moveToElement(timeClockAlertIcon).build().perform();
				if(isElementLoaded(clickPopover))
					clocksInfo.add(clickPopover.getText());
			}
		}
		else {
			SimpleUtils.report("No Clock Info Icon found of Timesheet Details popup.");
		}
		return clocksInfo;
	}



	@FindBy(css="lg-button[label=\"Add Timeclock\"]")
	private WebElement addTimeClockBtn;
	
	@FindBy(css="lg-select[label=\"Location\"]")
	private WebElement addTCLocationField;
	
	@FindBy(css="input-field[label=\"Date\"]")
	private WebElement addTCDateField;
	
	@FindBy(css="lg-picker-input[label=\"Employee\"]")
	private WebElement addTCEmployeeField;
	
	@FindBy(css="select[aria-label=\"Work Role\"]")
	private WebElement addTCWorkRoleDropDown;
	
	@FindBy(css="input[aria-label=\"Shift Start\"]")
	private WebElement addTCShiftStartTimeTextField;
	
	@FindBy(css="input[aria-label=\"Shift End\"]")
	private WebElement addTCShiftEndTimeTextField;
	
	@FindBy(css="input[aria-label=\"Add Note\"]")
	private WebElement addTCAddNotesTextField;
	
	@FindBy(css="lg-button[label=\"Add\"]")
	private WebElement addTimeClockSaveBtn;
	
	@FindBy(css="div.lg-search-options__option-wrapper")
	private List<WebElement> dropdownOptions;
	
	@FindBy(className = "lg-single-calendar-month")
	private WebElement timeClockCalendarLabel;
	
	@FindBy(css="[ng-click=\"$ctrl.changeMonth(-1)\"]")
	private WebElement timeClockPreviousMonthArrow;
	
	@FindBy(css="[ng-click=\"$ctrl.changeMonth(1)\"]")
	private WebElement timeClockNextMonthArrow;
	
	@FindBy(css="div.lg-single-calendar-date")
	private List<WebElement> calenderDates;
	
	@FindBy(css = "div.lg-add-clock")
	private WebElement addClockPopup;
	
	@Override
	public void addNewTimeClock(String location, String timeClockDate, String employee, String workRole, String startTime, String endTime, String notes) throws Exception {
		if(isElementLoaded(addTimeClockBtn)) {
			click(addTimeClockBtn);
			
			// Select Location
			click(addTCLocationField);
			List<WebElement> locationTextButtons = addTCLocationField.findElements(By.cssSelector("input[type=\"text\"]"));
			WebElement searchButton = locationTextButtons.get(1);
			searchButton.sendKeys(location);
			Thread.sleep(2000);
			if(dropdownOptions.size() != 0) {
				click(dropdownOptions.get(0));
			}
			
			// Select Date Month & Year
			click(addTCDateField);
			String date = timeClockDate.split(",")[0].split(" ")[1];
			String month = timeClockDate.split(",")[0].split(" ")[0];
			int year = Integer.valueOf(timeClockDate.split(",")[1].trim());
			WebElement changeMonthArrow = timeClockNextMonthArrow;
			while(year != Integer.valueOf(timeClockCalendarLabel.getText().split(" ")[1])
					|| ! timeClockCalendarLabel.getText().toLowerCase().contains(month.toLowerCase()))
			{
				if(timeClockCalendarLabel.getText().toLowerCase().contains(month.toLowerCase())) {
					if(year > Integer.valueOf(timeClockCalendarLabel.getText().split(" ")[1]))
						changeMonthArrow = timeClockNextMonthArrow;
					else
						changeMonthArrow = timeClockPreviousMonthArrow;
				}
				click(changeMonthArrow);
			}
			
			// Select Date
			for(WebElement catenderdate : calenderDates) {
				if(catenderdate.getText().contains(date)) {
					click(catenderdate);
				}
			}
			
			
			// Select Employee
			boolean isEmployeeFound = false;
			List<WebElement> timeCLockEmployeeTextBox = addTCEmployeeField.findElements(By.cssSelector("input[type=\"text\"]"));
			click(timeCLockEmployeeTextBox.get(0));
			timeCLockEmployeeTextBox.get(0).sendKeys(employee.split(" ")[0]);
			timeCLockEmployeeTextBox.get(0).sendKeys(Keys.TAB);
			Thread.sleep(2000);
			for(WebElement employeeOption : dropdownOptions)
			{
				if(employeeOption.getText().toLowerCase().contains(employee.toLowerCase())) {
					click(employeeOption);
					isEmployeeFound = true;
				}
			}
			SimpleUtils.assertOnFail("The employee '"+employee+"' not found while adding a Time Clock.", isEmployeeFound, false);
			
			// Select Work Role
			Select workRoleDropDown= new Select (addTCWorkRoleDropDown);
			workRoleDropDown.selectByVisibleText(workRole);
			
			

			// Shift Start Field
			addTCShiftStartTimeTextField.clear();
			addTCShiftStartTimeTextField.sendKeys(startTime);
			
			// Shift End Field
			addTCShiftEndTimeTextField.clear();
			addTCShiftEndTimeTextField.sendKeys(endTime);
			
			// Add Notes Field
			addTCAddNotesTextField.clear();
			addTCAddNotesTextField.sendKeys(notes);
			
			// Save
			click(addTimeClockSaveBtn);

			if(isElementLoaded(addClockPopup, 1))
				SimpleUtils.fail("Unable to Save Time Clock!", false);
			else
				SimpleUtils.pass("Time Clock Saved Successfully!");
		}
	}
	

	@FindBy(css="div.lg-button-group-last")
	private WebElement payPeriodBtn;
	
	@FindBy(css="input[placeholder=\"You can search by name, location, and job title.\"]")
	private WebElement timeSheetWorkerSearchBox;
	
	@Override
	public void valiadteTimeClock(String location, String timeClockDate, String employee, String workRole, 
			String startTime, String endTime, String notes) throws Exception 
	{
		if(startTime.startsWith("0"))
			startTime = startTime.substring(1);
		if(endTime.startsWith("0"))
			endTime = endTime.substring(1);
		boolean isShiftStartMatched = false;
		boolean isShiftEndMatched = false;
		clickOnTimeSheetConsoleMenu();
		clickOnPayPeriodDuration();
		
		if(isElementLoaded(timeSheetWorkerSearchBox, 10))
		{
			timeSheetWorkerSearchBox.click();
			timeSheetWorkerSearchBox.sendKeys(employee.split(" ")[0]);
			timeSheetWorkerSearchBox.sendKeys(Keys.TAB);
			Thread.sleep(2000);
			if(timeSheetWorkersRows.size() != 0) {
				for(WebElement workerRow: timeSheetWorkersRows)
				{
					if(workerRow.getText().toLowerCase().contains(employee.toLowerCase()))
					{
						click(workerRow);
						for(WebElement WorkersDayRow : getTimeSheetDisplayedWorkersDayRows())
						{
							if(WorkersDayRow.getText().toLowerCase().contains(timeClockDate.toLowerCase().split(",")[0]))
							{
								WebElement activeRowPopUpLink = WorkersDayRow.findElement(By.cssSelector("lg-button[action-link]"));
								if(isElementLoaded(activeRowPopUpLink))
								{
									click(activeRowPopUpLink);
									for(WebElement clock: getAllAvailableClocksOnClockDetailsPopup())
									{
										List<WebElement> clockInAndOutDetails = clock.findElements(By.cssSelector("[ng-repeat=\"key in ['in', 'out']\"]"));
										for(WebElement clockInOut: clockInAndOutDetails)
										{
											String elementText = clockInOut.getText().replace("\n", " ").toLowerCase();
											if(elementText.contains("shift start"))
											{
												if(elementText.contains(location.toLowerCase()) && elementText.contains(workRole.toLowerCase())
														&& elementText.contains(startTime.toLowerCase()))
												{
													isShiftStartMatched = true;
												}
											}
											else if(elementText.contains("shift end"))
											{
												if(elementText.contains(location.toLowerCase()) && elementText.contains(location.toLowerCase())
														&& elementText.contains(endTime.toLowerCase()))
												{
													isShiftEndMatched = true;
												}
											}
										}
									}
										
								}
							}
						}
						break;
					}
					else
						SimpleUtils.fail("Employee '"+ employee +"' not found while validaing time clock.", false);
				}
			}
			else
				SimpleUtils.fail("Employee '"+ employee +"' not found while validaing time clock.", false);
		}
		
		if(isShiftStartMatched == true &&  isShiftEndMatched == true)
		{
			SimpleUtils.pass("Time Clock verified!");
		}
		else
		{
			SimpleUtils.fail("Time Clock not found!", false);
		}
	}
	
	@Override
	public void clickOnPayPeriodDuration() throws Exception
	{
		String activeButtonClassKeyword = "selected";
		if(isElementLoaded(payPeriodBtn))
		{
			if(! payPeriodBtn.getAttribute("class").toLowerCase().contains(activeButtonClassKeyword))
				click(payPeriodBtn);
		}
		else
			SimpleUtils.fail("Timesheet: Pay Period Button not loaded!", false);
	}
	
	@FindBy(css="[ng-repeat=\"clockIn in $ctrl.clockIns\"]")
	private List<WebElement> savedClockes;
	
	public ArrayList<WebElement> getAllAvailableClocksOnClockDetailsPopup()
	{
		ArrayList<WebElement> availableClocks = new ArrayList<WebElement>();
		for(WebElement clockDetails : savedClockes)
		{
			if(clockDetails.getText().length() != 0 && clockDetails.getText().trim() != "")
				availableClocks.add(clockDetails);
		}
		return availableClocks;
	}
	
}
