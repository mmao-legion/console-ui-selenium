package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.legion.pages.BasePage;
import com.legion.pages.ControlsNewUIPage;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;



public class ConsoleControlsNewUIPage extends BasePage implements ControlsNewUIPage{

	public ConsoleControlsNewUIPage(){
		PageFactory.initElements(getDriver(), this);
	}
	
	
	@FindBy(css = "div.console-navigation-item-label.Controls")
	private WebElement controlsConsoleMenuDiv;
	
	@FindBy(css = "div.header-navigation-label")
	private WebElement controlsPageHeaderLabel;
	
	@FindBy(css = "lg-dashboard-card[title=\"Company Profile\"]")
	private WebElement companyProfileCard;
	
	@FindBy(css = "input[aria-label=\"Company Name\"]")
	private WebElement locationCompanyNameField;
	
	@FindBy(css = "lg-dashboard-card[title=\"Scheduling Policies\"]")
	private WebElement schedulingPoliciesCard;
	
	
	@FindBy(css = "input[aria-label=\"Business Address\"]")
	private WebElement locationBusinessAddressField;
	
	@FindBy(css = "input[aria-label=\"City\"]")
	private WebElement locationCityField;
	
	@FindBy(css = "select[aria-label=\"State\"]")
	private WebElement locationStateField;

	@FindBy(css = "select[aria-label=\"Province\"]")
	private WebElement locationProvinceField;
	
	@FindBy(css = "select[aria-label=\"Country\"]")
	private WebElement locationCountryField;
	
	@FindBy(css = "input[aria-label=\"Zip Code\"]")
	private WebElement locationZipCodeField;
	
	@FindBy(css = "input[aria-label=\"Postal Code\"]")
	private WebElement locationPostalCodeField;
	
	@FindBy(css = "select[aria-label=\"Time Zone\"]")
	private WebElement locationTimeZoneField;
	
	@FindBy(css = "input[aria-label=\"Website\"]")
	private WebElement locationWebsiteField;
	
	
	@FindBy(css = "input[aria-label=\"First Name\"]")
	private WebElement locationFirstNameField;
	
	@FindBy(css = "input[aria-label=\"Last Name\"]")
	private WebElement locationLastNameField;
	
	@FindBy(css = "input[aria-label=\"E-mail\"]")
	private WebElement locationEmailField;
	
	@FindBy(css = "input[aria-label=\"Phone\"]")
	private WebElement locationPhoneField;
	
	@FindBy(css = "lg-button[label=\"Save\"]")
	private List<WebElement> locationProfileSaveBtn;
	
	@FindBy(css = "lg-dashboard-card[title=\"Working Hours\"]")
	private WebElement workingHoursCard;
	
	@FindBy(css = "collapsible[block-title=\"'Regular'\"]")
	private WebElement regularHoursBlock;
	
	@FindBy(css = "day-working-hours[id=\"day.dayOfTheWeek\"]")
	private List<WebElement> regularHoursRows;
	
	@FindBy(css = "input-field[type=\"checkbox\"]")
	private List<WebElement> regularHoursDaysCheckboxs;
	
	@FindBy(css = "div.lgn-time-slider-post")
	private List<WebElement> editRegularHoursSliders;
	
	@FindBy(css = "lg-button[ng-click=\"saveWorkingHours()\"]")
	private WebElement saveWorkersHoursBtn;
	
	@FindBy(css = "lg-button[ng-click=\"saveWorkdayHours()\"]")
	private WebElement saveAllRegularHoursBtn;
	
	@FindBy(css = "form-section[form-title=\"Budget\"]")
	private WebElement budgetFormSection;

	@FindBy(css = "input-field[origin=\"schedulingWindow\"]")
	private WebElement schedulingWindowAdvanceWeekCount;
	
	@FindBy(css = "div.lg-advanced-box")
	private List<WebElement> controlsAdvanceButtons;
	
	@FindBy(css = "input-field[value=\"sp.weeklySchedulePreference.openingBufferHours\"]")
	private WebElement openingBufferHours;
	
	@FindBy(css = "input-field[value=\"sp.weeklySchedulePreference.closingBufferHours\"]")
	private WebElement closingBufferHours;
	
	
	@FindBy(css = "lg-dashboard-card[title=\"Location Profile\"]")
	private WebElement locationProfileSection;
	
	@FindBy(css = "lg-dashboard-card[title=\"Schedule Collaboration\"]")
	private WebElement scheduleCollaborationSection;
	
	@FindBy(css = "lg-dashboard-card[title=\"Compliance\"]")
	private WebElement complianceSection;
	
	@FindBy(css = "lg-dashboard-card[title=\"Users and Roles\"]")
	private WebElement usersAndRolesSection;
	
	@FindBy(css = "lg-dashboard-card[title=\"Tasks and Work Roles\"]")
	private WebElement tasksAndWorkRolesSection;

	@FindBy(css = "page-heading[page-title=\"Location Details\"]")
	private WebElement breadcrumbsLocationDetails;

	@FindBy(css = "page-heading[page-title=\"Scheduling Policies\"]")
	private WebElement breadcrumbsSchedulingPolicies;
	
	@FindBy(css = "page-heading[page-title=\"Schedule Collaboration\"]")
	private WebElement breadcrumbsScheduleCollaboration;

	@FindBy(css = "page-heading[page-title=\"Compliance\"]")
	private WebElement breadcrumbsCompliance;
	
	@FindBy(css = "page-heading[page-title=\"Users and Roles\"]")
	private WebElement breadcrumbsUsersAndRoles;
	
	@FindBy(css = "page-heading[page-title=\"Tasks and Work Roles\"]")
	private WebElement breadcrumbsTasksAndWorkRoles;
	
	@FindBy(css = "page-heading[page-title=\"Working Hours\"]")
	private WebElement breadcrumbsWorkingHours;
	
	@FindBy(css = "lg-dashboard-card[title=\"Time and Attendance\"]")
	private WebElement timeAndAttendanceCard;
	
	@FindBy(css = "div.lg-advanced-box__toggle")
	private WebElement timeSheetAdvanceBtn;
	
	@FindBy(css = "question-input[question-title=\"Timesheet export format\"]")
	private WebElement timeSheetExportFormatDiv;
	
	@FindBy(css = "form-section[form-title=\"Shifts\"]")
	private WebElement schedulingPoliciesShiftFormSectionDiv;
	
	@FindBy(css = "question-input[question-title=\"Shift Interval minutes for the enterprise.\"]")
	private WebElement schedulingPoliciesShiftIntervalDiv;
	
	String timeSheetHeaderLabel = "Controls";
	
	@Override
	public void clickOnControlsConsoleMenu() throws Exception {
		if(isElementLoaded(controlsConsoleMenuDiv))
			click(controlsConsoleMenuDiv);
		else
			SimpleUtils.fail("Controls Console Menu not loaded Successfully!", false);
	}


	@Override
	public boolean isControlsPageLoaded() throws Exception {
		if(isElementLoaded(controlsPageHeaderLabel))
			if(controlsPageHeaderLabel.getText().toLowerCase().contains(timeSheetHeaderLabel.toLowerCase())) {
				SimpleUtils.pass("Controls Page loaded Successfully!");
				return true;
			}
		return false;
	}


	@FindBy(css = "input-field[placeholder=\"Global\"]")
	private WebElement globalLocationButton;
	
	@Override
	public void clickOnGlobalLocationButton() throws Exception {
		
		if(isElementLoaded(globalLocationButton)) {
			click(globalLocationButton);
			SimpleUtils.pass("Controls Page: 'Global Location' loaded successfully.");
		}
		else
			SimpleUtils.fail("Controls Page: Global Location Button not Loaded!", false);
	}

	
	@Override
	public void clickOnControlsCompanyProfileCard() throws Exception {
		if(isElementLoaded(companyProfileCard))
			click(companyProfileCard);
		else
			SimpleUtils.fail("Controls Page: Company Profile Card not Loaded!", false);
	}
	

	@Override
	public void updateUserLocationProfile(String companyName, String businessAddress, String city, String state,
			String country, String zipCode, String timeZone, String website, String firstName, String lastName,
			String email, String phone) throws Exception {
		
		locationCompanyNameField.clear();
		locationCompanyNameField.sendKeys(companyName);
		
		locationBusinessAddressField.clear();
		locationBusinessAddressField.sendKeys(businessAddress);
		
		locationCityField.clear();
		locationCityField.sendKeys(city);
		
		Select drpCountry = new Select(locationCountryField);
		drpCountry.selectByVisibleText(country);
		
		try {
			Select drpStates = new Select(locationStateField);
			drpStates.selectByVisibleText(state);			
		}catch (Exception e) {
			Select drpStates = new Select(locationProvinceField);
			drpStates.selectByVisibleText(state);
		}
	
		try {
			locationZipCodeField.clear();
			locationZipCodeField.sendKeys(zipCode);	
		}catch (Exception e) {
			locationPostalCodeField.clear();
			locationPostalCodeField.sendKeys(zipCode);	
		}
		
		
		
		
		Select drpTimeZone = new Select(locationTimeZoneField);
		drpTimeZone.selectByVisibleText(timeZone);

		locationWebsiteField.clear();
		locationWebsiteField.sendKeys(website);
		
		locationFirstNameField.clear();
		locationFirstNameField.sendKeys(firstName);
		
		locationLastNameField.clear();
		locationLastNameField.sendKeys(lastName);
		
		locationEmailField.clear();
		locationEmailField.sendKeys(email);
		
		locationPhoneField.clear();
		locationPhoneField.sendKeys(phone);
		
		if(locationProfileSaveBtn.size() > 0)
			click(locationProfileSaveBtn.get(0));
		else
			SimpleUtils.fail("Controls Page: Location Profile Save button not loaded.", false);
		
	}
	
	@Override
	public boolean isUserLocationProfileUpdated(String companyName, String businessAddress, String city, String state,
			String country, String zipCode, String timeZone, String website, String firstName, String lastName,
			String email, String phone) throws Exception {
		boolean isProfileValueUpdated = true;
		
		if(! locationCompanyNameField.getAttribute("value").contains(companyName))
		{
			isProfileValueUpdated = false;
			SimpleUtils.fail("Controls Page: Updating Location Profile - 'Company Name' not updated.", true);
		}
			
		if(! locationBusinessAddressField.getAttribute("value").contains(businessAddress))
		{
			isProfileValueUpdated = false;
			SimpleUtils.fail("Controls Page: Updating Location Profile - 'Business Address' not updated.", true);
		}
		
		
		if(! locationCityField.getAttribute("value").contains(city))
		{
			isProfileValueUpdated = false;
			SimpleUtils.fail("Controls Page: Updating Location Profile - 'City' not updated.", true);
		}
		
		try {
			Select drpStates = new Select(locationStateField);		
			if(! drpStates.getFirstSelectedOption().getText().contains(state))
			{
				isProfileValueUpdated = false;
				SimpleUtils.fail("Controls Page: Updating Location Profile - 'State' not updated.", true);
			}			
		}catch (Exception e) {
			Select drpStates = new Select(locationProvinceField);		
			if(! drpStates.getFirstSelectedOption().getText().contains(state))
			{
				isProfileValueUpdated = false;
				SimpleUtils.fail("Controls Page: Updating Location Profile - 'Province' not updated.", true);
			}
		}
	
		try {
			if(! locationZipCodeField.getAttribute("value").contains(zipCode))
			{
				isProfileValueUpdated = false;
				SimpleUtils.fail("Controls Page: Updating Location Profile - 'Zip Code' not updated.", true);
			}
		}catch (Exception e) {
			if(! locationPostalCodeField.getAttribute("value").contains(zipCode))
			{
				isProfileValueUpdated = false;
				SimpleUtils.fail("Controls Page: Updating Location Profile - 'Postal Code' not updated.", true);
			}
		}
		
		Select drpTimeZone = new Select(locationTimeZoneField);
		if(! drpTimeZone.getFirstSelectedOption().getText().contains(timeZone))
		{
			isProfileValueUpdated = false;
			SimpleUtils.fail("Controls Page: Updating Location Profile - 'Time Zone' not updated.", true);
		}
		
		if(! locationWebsiteField.getAttribute("value").contains(website))
		{
			isProfileValueUpdated = false;
			SimpleUtils.fail("Controls Page: Updating Location Profile - 'Website' not updated.", true);
		}
		
		if(! locationFirstNameField.getAttribute("value").contains(firstName))
		{
			isProfileValueUpdated = false;
			SimpleUtils.fail("Controls Page: Updating Location Profile - 'First Name' not updated.", true);
		}

		if(! locationLastNameField.getAttribute("value").contains(lastName))
		{
			isProfileValueUpdated = false;
			SimpleUtils.fail("Controls Page: Updating Location Profile - 'Last Name' not updated.", true);
		}

		if(! locationEmailField.getAttribute("value").contains(email))
		{
			isProfileValueUpdated = false;
			SimpleUtils.fail("Controls Page: Updating Location Profile - 'E-Mail' not updated.", true);
		}

		if(! locationPhoneField.getAttribute("value").contains(phone))
		{
			isProfileValueUpdated = false;
			SimpleUtils.fail("Controls Page: Updating Location Profile - 'Phone' not updated.", true);
		}
		
		return isProfileValueUpdated;
	}
	
	@Override
	public void clickOnControlsWorkingHoursCard() throws Exception {
		if(isElementLoaded(workingHoursCard))
			click(workingHoursCard);
		else
			SimpleUtils.fail("Controls Page: Working Hours Card not Loaded!", false);
	}
	

	@Override
	public void updateControlsRegularHours(String isStoreClosed, String openingHours, String closingHours, String day)
			throws Exception {
		openingHours = openingHours.replace(" ", "");
		closingHours = closingHours.replace(" ", "");
		WebElement collapsibleHeader = regularHoursBlock.findElement(By.cssSelector("div.collapsible.row"));
		boolean isRegularHoursSectionOpened = collapsibleHeader.getAttribute("class").contains("open");
		if(! isRegularHoursSectionOpened)
			click(regularHoursBlock);
		
		if(regularHoursRows.size() > 0)
		{
			for(WebElement regularHoursRow : regularHoursRows)
			{
				if(regularHoursRow.getText().toLowerCase().contains(day.toLowerCase()))
				{
					WebElement regularHoursEditBtn = regularHoursRow.findElement(By.cssSelector("lg-button[label=\"Edit\"]"));
					if(isElementLoaded(regularHoursEditBtn))
					{
						click(regularHoursEditBtn);

						// Select Opening Hours
						int openingHourOnSlider = Integer.valueOf(editRegularHoursSliders.get(0).getText().split(":")[0]);
						if(editRegularHoursSliders.get(0).getText().toLowerCase().contains("pm"))
							openingHourOnSlider = openingHourOnSlider + 12;
						int openingHourOnJson = Integer.valueOf(openingHours.split(":")[0]);
						if(openingHours.toLowerCase().contains("pm"))
							openingHourOnJson = openingHourOnJson + 12;
						int sliderOffSet = 5;
						
						if(openingHourOnSlider > openingHourOnJson)
							sliderOffSet = -5;
						
						while(! editRegularHoursSliders.get(0).getText().toLowerCase().contains(openingHours.toLowerCase()))
						{
							moveDayViewCards(editRegularHoursSliders.get(0), sliderOffSet);
						}
						
						// Select Closing Hours
						int closingHourOnSlider = Integer.valueOf(editRegularHoursSliders.get(1).getText().split(":")[0]);
						if(editRegularHoursSliders.get(1).getText().toLowerCase().contains("pm"))
							closingHourOnSlider = closingHourOnSlider + 12;
						int closingHourOnJson = Integer.valueOf(closingHours.split(":")[0]);
						if(closingHours.toLowerCase().contains("pm"))
							closingHourOnJson = closingHourOnJson + 12;
						if(closingHourOnSlider > closingHourOnJson)
							sliderOffSet = -5;
						else
							sliderOffSet = 5;

						while(! editRegularHoursSliders.get(1).getText().toLowerCase().contains(closingHours.toLowerCase()))
						{
							moveDayViewCards(editRegularHoursSliders.get(1), sliderOffSet);
						}
						
						if(isElementLoaded(saveWorkersHoursBtn))
						{
							click(saveWorkersHoursBtn);
							SimpleUtils.pass("Controls Working Hours Section: Regular Hours Updated for the day ('"+ day  +"'). ");
						}
						else
							SimpleUtils.fail("Controls Working Hours Section: Editing Regular Hours 'Save' Button not loaded.", true);
					}
					else {
						SimpleUtils.fail("Controls Working Hours Section: Regular Hours 'Edit' Button not loaded.", true);
					}
				}
				
			}
		}
		else {
			SimpleUtils.fail("Controls Working Hours Section: Regular Hours not loaded.", true);
		}
	}
	
	
	public void moveDayViewCards(WebElement webElement, int xOffSet)
	{
		Actions builder = new Actions(MyThreadLocal.getDriver());
		builder.moveToElement(webElement)
	         .clickAndHold()
	         .moveByOffset(xOffSet, 0)
	         .release()
	         .build()
	         .perform();
	}
	
	
	@Override
	public void clickOnSaveRegularHoursBtn() throws Exception
	{
		if(isElementLoaded(saveAllRegularHoursBtn))
		{
			click(saveAllRegularHoursBtn);
			SimpleUtils.pass("Controls Working Hours Section: Regular Hours Saved successfully. ");
		}else {
			SimpleUtils.fail("Controls Working Hours Section: Regular Hours 'Save' Button not loaded.", true);
		}
	}


	@Override
	public void clickOnControlsSchedulingPolicies() throws Exception {
		if(isElementLoaded(schedulingPoliciesCard))
			click(schedulingPoliciesCard);
		else
			SimpleUtils.fail("Controls Page: Schedule Policies Card not Loaded!", false);
	}

	
	@Override
	public void enableDisableBudgetSmartcard(boolean enable) throws Exception
	{
		WebElement enableBudgetYesBtn = budgetFormSection.findElement(By.cssSelector("div.lg-button-group-first"));
		WebElement enableBudgetNoBtn = budgetFormSection.findElement(By.cssSelector("div.lg-button-group-last"));
		if(enable && isBudgetSmartcardEnabled())
			SimpleUtils.pass("Schedule Policies Budget card already enabled.");
		else if(enable && ! isBudgetSmartcardEnabled())
		{
			click(enableBudgetYesBtn);
			SimpleUtils.pass("Schedule Policies Budget card enabled successfully.");
		}
		else if(!enable && isBudgetSmartcardEnabled())
		{
			click(enableBudgetNoBtn);
			SimpleUtils.pass("Schedule Policies Budget card disabled successfully.");
		}
		else
			SimpleUtils.pass("Schedule Policies Budget card already disabled.");
	}
	
	@Override
	public boolean isBudgetSmartcardEnabled() throws Exception
	{
		if(isElementLoaded(budgetFormSection)) {
			WebElement enableBudgetYesBtn = budgetFormSection.findElement(By.cssSelector("div.lg-button-group-first"));
			if(isElementLoaded(enableBudgetYesBtn)) {
				if(enableBudgetYesBtn.getAttribute("class").contains("selected"))
					return true;
			}
			else
				SimpleUtils.fail("Controls Page: Schedule Policies Budget form section 'Yes' button not loaded!", false);
		}
		else
			SimpleUtils.fail("Controls Page: Schedule Policies Budget form section not loaded!", false);
		return false;
	}

	
	@Override
	public String getAdvanceScheduleWeekCountToCreate() throws Exception {
		String selectedWeek = "";
		if(isElementLoaded(schedulingWindowAdvanceWeekCount))
		{
			WebElement advanceScheduleWeekSelectBox = schedulingWindowAdvanceWeekCount.findElement(By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			Select drpScheduleWeek = new Select(advanceScheduleWeekSelectBox);
			selectedWeek = drpScheduleWeek.getFirstSelectedOption().getText();
		}
		else
			SimpleUtils.fail("Controls Page: Schedule Policies Advance Schedule weeks not loaded.", false);
		
		return selectedWeek;
	}


	@Override
	public void updateAdvanceScheduleWeekCountToCreate(String scheduleWeekCoundToCreate) throws Exception {
		if(getAdvanceScheduleWeekCountToCreate().toLowerCase().contains(scheduleWeekCoundToCreate.toLowerCase()))
			SimpleUtils.pass("Controls Page: Schedule Policies Advance Schedule weeks value '"+scheduleWeekCoundToCreate+" weeks' already Updated.");
		else {
			WebElement advanceScheduleWeekSelectBox = schedulingWindowAdvanceWeekCount.findElement(By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			Select drpScheduleWeek = new Select(advanceScheduleWeekSelectBox);
			drpScheduleWeek.selectByVisibleText(scheduleWeekCoundToCreate);
			if(getAdvanceScheduleWeekCountToCreate().toLowerCase().contains(scheduleWeekCoundToCreate.toLowerCase()))
				SimpleUtils.pass("Controls Page: Schedule Policies Advance Schedule weeks value '"+scheduleWeekCoundToCreate+"' Updated successfully.");
			else
				SimpleUtils.fail("Controls Page: Unable to update Schedule Policies Advance Schedule weeks value.", false);
		}
	}
	
	@Override
	public HashMap<String, Integer> getScheduleBufferHours() throws Exception
	{
		HashMap<String, Integer> bufferHours = new HashMap<String, Integer>();
		Thread.sleep(2000);
		if(controlsAdvanceButtons.size() > 0)
		{
			click(controlsAdvanceButtons.get(0));
			bufferHours.put("openingBufferHours", Integer.valueOf(
					openingBufferHours.findElement(By.cssSelector("input[type=\"number\"]")).getAttribute("value")));
			bufferHours.put("closingBufferHours", Integer.valueOf(
					closingBufferHours.findElement(By.cssSelector("input[type=\"number\"]")).getAttribute("value")));
		}
		return bufferHours;
	}

	
	@Override
	public void clickOnControlsLocationProfileSection() throws Exception {
		if(isElementLoaded(locationProfileSection))
			click(locationProfileSection);
		else
			SimpleUtils.fail("Controls Page: Location Profile Card not Loaded!", false);
	}
	
	
	@Override
	public void clickOnControlsScheduleCollaborationSection() throws Exception {
		if(isElementLoaded(scheduleCollaborationSection))
			click(scheduleCollaborationSection);
		else
			SimpleUtils.fail("Controls Page: Schedule Collaboration Card not Loaded!", false);
	}
	
	@Override
	public void clickOnControlsComplianceSection() throws Exception {
		if(isElementLoaded(complianceSection))
			click(complianceSection);
		else
			SimpleUtils.fail("Controls Page: Compliance Card not Loaded!", false);
	}
	
	@Override
	public void clickOnControlsUsersAndRolesSection() throws Exception {
		if(isElementLoaded(usersAndRolesSection))
			click(usersAndRolesSection);
		else
			SimpleUtils.fail("Controls Page: Users and Roles Card not Loaded!", false);
	}
	
	@Override
	public void clickOnControlsTasksAndWorkRolesSection() throws Exception {
		if(isElementLoaded(tasksAndWorkRolesSection))
			click(tasksAndWorkRolesSection);
		else
			SimpleUtils.fail("Controls Page: tasksAndWorkRolesSection Card not Loaded!", false);
	}


	@Override
	public boolean isControlsLocationProfileLoaded() throws Exception {
		if(isElementLoaded(breadcrumbsLocationDetails)) {
			SimpleUtils.pass("Controls Page: Location Profile Section Loaded Successfully.");
			return true;
		}
		return false;
	}


	@Override
	public boolean isControlsSchedulingPoliciesLoaded() throws Exception {
		if(isElementLoaded(breadcrumbsSchedulingPolicies)) {
			SimpleUtils.pass("Controls Page: Scheduling Policies Section Loaded Successfully.");
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isControlsScheduleCollaborationLoaded() throws Exception {
		if(isElementLoaded(breadcrumbsScheduleCollaboration)) {
			SimpleUtils.pass("Controls Page: Schedule Collaboration Section Loaded Successfully.");
			return true;
		}
		return false;
	}


	@Override
	public boolean isControlsComplianceLoaded() throws Exception {
		if(isElementLoaded(breadcrumbsCompliance)) {
			SimpleUtils.pass("Controls Page: Compliance Section Loaded Successfully.");
			return true;
		}
		return false;
	}


	@Override
	public boolean isControlsUsersAndRolesLoaded() throws Exception {
		if(isElementLoaded(breadcrumbsUsersAndRoles)) {
			SimpleUtils.pass("Controls Page: Users and Roles Section Loaded Successfully.");
			return true;
		}
		return false;
	}

	@Override
	public boolean isControlsTasksAndWorkRolesLoaded() throws Exception {
		if(isElementLoaded(breadcrumbsTasksAndWorkRoles)) {
			SimpleUtils.pass("Controls Page: Tasks and Work Roles Section Loaded Successfully.");
			return true;
		}
		return false;
	}	
	
	@Override
	public boolean isControlsWorkingHoursLoaded() throws Exception {
		if(isElementLoaded(breadcrumbsWorkingHours)) {
			SimpleUtils.pass("Controls Page: Working Hours Section Loaded Successfully.");
			return true;
		}
		return false;
	}


	@Override
	public void clickOnControlsTimeAndAttendanceCard() throws Exception {
		if(isElementLoaded(timeAndAttendanceCard)) {
			click(timeAndAttendanceCard);
			SimpleUtils.pass("Controls Page: 'Time and Attendance' tab selected successfully.");
		}
		else
			SimpleUtils.fail("Controls Page: 'Time and Attendance' tab not loaded.", false);
	}

	@Override
	public void clickOnControlsTimeAndAttendanceAdvanceBtn() throws Exception {
		if(isElementLoaded(timeSheetAdvanceBtn) && !timeSheetAdvanceBtn.getAttribute("class").contains("--advanced"))
			click(timeSheetAdvanceBtn);
		else
			SimpleUtils.fail("Controls - Time and Attendance section: 'Advance' button not loaded.", false);
	}

	@Override
	public void selectTimeSheetExportFormatByLabel(String optionLabel) throws Exception {
		if(isElementLoaded(timeSheetExportFormatDiv)) {
			WebElement timeSheetFormatDropDown = timeSheetExportFormatDiv.findElement(By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(timeSheetFormatDropDown)) {
				Select dropdown= new Select(timeSheetFormatDropDown);
				if(getTimeSheetExportFormatSelectedOption().contains(optionLabel)) {
					SimpleUtils.pass("Time and Attendance: Timesheet export format '"+optionLabel+"' option already selected.");
				}
				else {
					dropdown.selectByVisibleText(optionLabel);
					Thread.sleep(1000);
					if(getTimeSheetExportFormatSelectedOption().contains(optionLabel))
						SimpleUtils.pass("Time and Attendance: Timesheet export format '"+optionLabel+"' option selected successfully.");
					else
						SimpleUtils.fail("Time and Attendance: Unable to select Timesheet export format '"+optionLabel+"' option.", false);
				}
			}
			else
				SimpleUtils.fail("Controls - Time and Attendance: Timesheet export format dropdown not loaded.", false);
		}
		else
			SimpleUtils.fail("Controls Page: TimeSheet Export Format section not loaded.", false);
	}
	
	public String getTimeSheetExportFormatSelectedOption() throws Exception {
		String selectedOptionLabel = "";
		if(isElementLoaded(timeSheetExportFormatDiv)) {
			WebElement timeSheetFormatDropDown = timeSheetExportFormatDiv.findElement(By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(timeSheetFormatDropDown)) {
				Select dropdown= new Select(timeSheetFormatDropDown);
				selectedOptionLabel = dropdown.getFirstSelectedOption().getText();
			}
			else
				SimpleUtils.fail("Controls - Time and Attendance: timesheet export format dropdown not loaded.", false);
		}
		else
			SimpleUtils.fail("Controls Page: TimeSheet Export Format section not loaded.", false);
		return selectedOptionLabel;
	}


	@Override
	public void clickOnSchedulingPoliciesShiftAdvanceBtn() throws Exception {
		if(isElementLoaded(schedulingPoliciesShiftFormSectionDiv)) {
			WebElement schedulingPoliciesShiftAdvanceBtn = schedulingPoliciesShiftFormSectionDiv.findElement(
					By.cssSelector("div.lg-advanced-box__toggle"));
			if(isElementLoaded(schedulingPoliciesShiftAdvanceBtn) && !schedulingPoliciesShiftAdvanceBtn.getAttribute("class")
					.contains("--advanced")) {
				click(schedulingPoliciesShiftAdvanceBtn);
				SimpleUtils.pass("Controls Page: - Scheduling Policies 'Shift' section: 'Advance' button clicked.");
			}
			else
				SimpleUtils.fail("Controls Page: - Scheduling Policies 'Shift' section: 'Advance' button not loaded.", false);
		}
		else
			SimpleUtils.fail("Controls Page: - Scheduling Policies section: 'Shift' form section not loaded.", false);
	}


	@Override
	public void selectSchedulingPoliciesShiftIntervalByLabel(String intervalTimeLabel) throws Exception {
		if(isElementLoaded(schedulingPoliciesShiftIntervalDiv))
		{
			WebElement shiftIntervalDropDown = schedulingPoliciesShiftIntervalDiv.findElement(By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(shiftIntervalDropDown)) {
				Select dropdown= new Select(shiftIntervalDropDown);
				if(getshiftIntervalDropDownSelectedOption().contains(intervalTimeLabel)) {
					SimpleUtils.pass("Scheduling Policies: Shift Interval time '"+intervalTimeLabel+"' option already selected.");
				}
				else {
					dropdown.selectByVisibleText(intervalTimeLabel);
					Thread.sleep(1000);
					if(getshiftIntervalDropDownSelectedOption().contains(intervalTimeLabel))
						SimpleUtils.pass("Scheduling Policies: Shift Interval time '"+intervalTimeLabel+"' option selected successfully.");
					else
						SimpleUtils.fail("Scheduling Policies: Unable to select Shift Interval time '"+intervalTimeLabel+"' option.", false);
				}
			}
		}
	}	
	
	public String getshiftIntervalDropDownSelectedOption() throws Exception {
		String selectedOptionLabel = "";
		if(isElementLoaded(schedulingPoliciesShiftIntervalDiv)) {
			WebElement shiftIntervalDropDown = schedulingPoliciesShiftIntervalDiv.findElement(By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(shiftIntervalDropDown)) {
				Select dropdown= new Select(shiftIntervalDropDown);
				selectedOptionLabel = dropdown.getFirstSelectedOption().getText();
			}
			else
				SimpleUtils.fail("Scheduling Policies: Shift Interval time dropdown not loaded.", false);
		}
		else
			SimpleUtils.fail("Scheduling Policies: 'Shift' section not loaded.", false);
		return selectedOptionLabel;
	}
}
