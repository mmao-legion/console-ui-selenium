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
	
	@FindBy(css = "form-section[form-title=\"Schedules\"]")
	private WebElement schedulingPoliciesSchedulesFormSectionDiv;
	
	@FindBy(css = "input-field[value=\"sp.enterprisePreference.shiftIntervalMins\"]")
	private WebElement schedulingPoliciesShiftIntervalDiv;
	
	@FindBy(css="lg-icon-button[type=\"confirm\"]")
	private WebElement confirmSaveButton;
	@FindBy(css = "input-field[placeholder=\"Global\"]")
	private WebElement globalLocationButton;
	@FindBy(css = "input-field[value=\"sp.weeklySchedulePreference.publishDayWindowWeek\"]")
	private WebElement schedulePublishWindowDiv;
	@FindBy(css = "input-field[value=\"sp.weeklySchedulePreference.finalizeDayWindow\"]")
	private WebElement advanceFinalizeDaysDiv;
	@FindBy(css="lg-select[search-hint=\"Search Location\"]")
	private WebElement SchedulingPoliciesActiveLocation;
	@FindBy(css="input-field[origin=\"schedulingWindow\"]")
	private WebElement schedulingWindow;
	@FindBy(css="input-field[value=\"sp.enterprisePreference.firstDayOfWeek\"]")
	private WebElement schedulingPoliciesFirstDayOfWeek;
	@FindBy(css="input-field[value=\"sp.enterprisePreference.minBusinessMinutes\"]")
	private WebElement earliestOpeningTimeDiv;
	@FindBy(css="input-field[value=\"sp.enterprisePreference.maxBusinessMinutes\"]")
	private WebElement latestClosingTimeDiv;
	@FindBy(css="input-field[value=\"sp.weeklySchedulePreference.minShiftLengthHour\"]")
	private WebElement minShiftLengthHourDiv;
	@FindBy(css="input-field[value=\"sp.weeklySchedulePreference.maxShiftLengthHour\"]")
	private WebElement maxShiftLengthHourDiv;
	@FindBy(css="input-field[value=\"sp.weeklySchedulePreference.maxWorkingDaysBestPractice\"]")
	private WebElement maxWorkingDaysBestPracticeInputField;
	@FindBy(css="input-field[value=\"sp.hourlyRatePolicy.displayBreakTimeInShift\"]")
	private WebElement displayBreakTimeInShiftInputField;
	@FindBy(css="yes-no[value=\"sp.budgetPreferences.enabled\"]")
	private WebElement applyLaborBudgetToSchedules;
	@FindBy(css="input-field[value=\"sp.budgetPreferences.budgetType\"]")
	private WebElement scheduleBudgetTypeElement;
	@FindBy(css="input-field[value=\"sp.weeklySchedulePreference.availabilityLockMode\"]")
	private WebElement availabilityLockModeDiv;
	@FindBy(css="input-field[value=\"sp.hourlyRatePolicy.scheduleUnavailableHourWorkers\"]")
	private WebElement scheduleUnavailableHourWorkersDiv;
	@FindBy(css="input-field[value=\"sp.weeklySchedulePreference.availabilityTolerance\"]")
	private WebElement availabilityToleranceField;
	@FindBy(css="yes-no[value=\"sp.teamPreferences.canWorkerRequestTimeOff\"]")
	private WebElement canWorkerRequestTimeOffBtnGroup;
	@FindBy(css="input-field[value=\"sp.teamPreferences.maxWorkersOnTimeOffPerDay\"]")
	private WebElement maxWorkersOnTimeOffPerDayField;
	@FindBy(css = "form-section[form-title=\"Time off\"]")
	private WebElement schedulingPoliciesTimeOffFormSectionDiv;
	@FindBy(css="input-field[value=\"sp.teamPreferences.noticePeriodToRequestTimeOff\"]")
	private WebElement noticePeriodToRequestTimeOffField;
	@FindBy(css="yes-no[value=\"sp.teamPreferences.showTimeOffReasons\"]")
	private WebElement showTimeOffReasonsBtnGroup;
	@FindBy(css="lg-button[label=\"Preserve\"]")
	private WebElement preserveSettingBtn;
	
	@FindBy(css="input-field[value=\"$ctrl.engagementGroup.numHoursPerWeekMin\"]")
	private List<WebElement> numHoursPerWeekMinInputFields;
	@FindBy(css="input-field[value=\"$ctrl.engagementGroup.numHoursPerWeek\"]")
	private List<WebElement> numHoursPerWeekMaxInputFields;
	@FindBy(css="input-field[value=\"$ctrl.engagementGroup.numHoursPerWeekIdeal\"]")
	private List<WebElement> numHoursPerWeekIdealInputFields;
	@FindBy(css="input-field[value=\"$ctrl.engagementGroup.numShiftsPerWeekMin\"]")
	private List<WebElement> numShiftsPerWeekMinInputFields;
	@FindBy(css="input-field[value=\"$ctrl.engagementGroup.numShiftsPerWeek\"]")
	private List<WebElement> numShiftsPerWeekMaxInputFields;
	@FindBy(css="input-field[value=\"$ctrl.engagementGroup.numShiftsIdeal\"]")
	private List<WebElement> numShiftsPerWeekIdealInputFields;
	@FindBy(css="input-field[value=\"$ctrl.engagementGroup.shiftMinLength\"]")
	private List<WebElement> minHoursPerShiftInputFields;
	@FindBy(css="input-field[value=\"$ctrl.engagementGroup.shiftMaxLength\"]")
	private List<WebElement> maxHoursPerShiftInputFields;
	@FindBy(css="input-field[value=\"$ctrl.engagementGroup.shiftIdealLength\"]")
	private List<WebElement> ideaHoursPerShiftInputFields;
	@FindBy(css="form-section[form-title=\"Scheduling Policy Groups\"]")
	private WebElement schedulePolicyGroupSection;
	@FindBy(css="input-field[value=\"$ctrl.engagementGroup.committedHoursPeriod\"]")
	private List<WebElement> committedHoursPeriodFileds;
	@FindBy(css="div[ng-click=\"$ctrl.select(tab)\"]")
	private List<WebElement> schedulingPolicyGroupsTabs;
	
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
	
	@Override
	public void clickOnSchedulingPoliciesSchedulesAdvanceBtn() throws Exception {
		if(isElementLoaded(schedulingPoliciesSchedulesFormSectionDiv)) {
			WebElement schedulingPoliciesShiftAdvanceBtn = schedulingPoliciesSchedulesFormSectionDiv.findElement(
					By.cssSelector("div.lg-advanced-box__toggle"));
			if(isElementLoaded(schedulingPoliciesShiftAdvanceBtn) && !schedulingPoliciesShiftAdvanceBtn.getAttribute("class")
					.contains("--advanced")) {
				click(schedulingPoliciesShiftAdvanceBtn);
				SimpleUtils.pass("Controls Page: - Scheduling Policies 'Schedules' section: 'Advance' button clicked.");
			}
			else
				SimpleUtils.fail("Controls Page: - Scheduling Policies 'Schedules' section: 'Advance' button not loaded.", false);
		}
		else
			SimpleUtils.fail("Controls Page: - Scheduling Policies section: 'Schedules' form section not loaded.", false);
	}


	
	@Override
	public String getSchedulePublishWindowWeeks() throws Exception {
		String selectedOptionLabel = "";
		if(isElementLoaded(schedulePublishWindowDiv)) {
			WebElement schedulePublishWindowDropDown = schedulePublishWindowDiv.findElement(By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(schedulePublishWindowDropDown)) {
				Select dropdown= new Select(schedulePublishWindowDropDown);
				selectedOptionLabel = dropdown.getFirstSelectedOption().getText();
			}
			else
				SimpleUtils.fail("Scheduling Policies: Advance Schedule weeks to be Finalize dropdown not loaded.", false);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Advance Schedule weeks to be Finalize section not loaded.", false);
		return selectedOptionLabel;
	}
	
	@Override
	public int getAdvanceScheduleDaysCountToBeFinalize() throws Exception {
		int finalizeScheduleDays = 0;
		if(isElementLoaded(advanceFinalizeDaysDiv)) {
			WebElement finalizeScheduleDaysField = advanceFinalizeDaysDiv.findElement(By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(finalizeScheduleDaysField)) {
				finalizeScheduleDays = Integer.valueOf(finalizeScheduleDaysField.getAttribute("value"));
			}
			else
				SimpleUtils.fail("Scheduling Policies: Advance Schedule days to be Finalize dropdown not loaded.", false);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Advance Schedule days to be Finalize section not loaded.", false);
		return finalizeScheduleDays;
	}
	
	
	@Override
	public String getSchedulingPoliciesActiveLocation() throws Exception {
		String activeLocation = "";
		if(isElementLoaded(SchedulingPoliciesActiveLocation))
			activeLocation = SchedulingPoliciesActiveLocation.getText();
		else
			SimpleUtils.fail("Controls Page: Scheduling Policies active location not loaded.", true);
		return activeLocation;
	}


	@Override
	public void updateSchedulePublishWindow(String publishWindowAdvanceWeeks) throws Exception {
		if(isElementLoaded(schedulePublishWindowDiv)) {
			WebElement schedulePublishWindowDropDown = schedulePublishWindowDiv.findElement(By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(schedulePublishWindowDropDown)) {
				Select dropdown = new Select(schedulePublishWindowDropDown);
				List<WebElement> dropdownOptions = dropdown.getOptions();
				for(WebElement dropdownOption : dropdownOptions) {
					if(dropdownOption.getText().toLowerCase().contains(publishWindowAdvanceWeeks.toLowerCase())) {
						click(dropdownOption);
						preserveTheSetting();
					}
				}
				if(getSchedulePublishWindowWeeks().toLowerCase().contains(publishWindowAdvanceWeeks.toLowerCase()))
					SimpleUtils.pass("Scheduling Policies: Schedule Publish Window selected '"+publishWindowAdvanceWeeks+"' successfully.");
				else
					SimpleUtils.fail("Scheduling Policies: Schedule Publish Window '"+publishWindowAdvanceWeeks+"' not selected.", true);

			}
			else
				SimpleUtils.fail("Scheduling Policies: Advance Schedule weeks to be Finalize dropdown not loaded.", false);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Advance Schedule weeks to be Finalize section not loaded.", false);
	}


	@Override
	public void updateSchedulePlanningWindow(String planningWindowAdvanceWeeks) throws Exception {
		if(isElementLoaded(schedulingWindow)) {
			WebElement schedulePlanningWindowDropDown = schedulingWindow.findElement(
					By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(schedulePlanningWindowDropDown)) {
				Select dropdown = new Select(schedulePlanningWindowDropDown);
				List<WebElement> dropdownOptions = dropdown.getOptions();
				for(WebElement dropdownOption : dropdownOptions) {
					if(dropdownOption.getText().toLowerCase().contains(planningWindowAdvanceWeeks.toLowerCase())) {
						click(dropdownOption);
						preserveTheSetting();
					}
				}
				if(getSchedulePlanningWindowWeeks().toLowerCase().contains(planningWindowAdvanceWeeks.toLowerCase()))
					SimpleUtils.pass("Scheduling Policies: Schedule Planning Window selected '"+planningWindowAdvanceWeeks+"' successfully.");
				else
					SimpleUtils.fail("Scheduling Policies: Schedule Planning Window '"+planningWindowAdvanceWeeks+"' not selected.", true);

			}
			else
				SimpleUtils.fail("Scheduling Policies: Advance Schedule weeks to be Published dropdown not loaded.", false);
		}

	}
	
	@Override
	public String getSchedulePlanningWindowWeeks() throws Exception {
		String selectedOptionLabel = "";
		if(isElementLoaded(schedulingWindow)) {
			WebElement schedulePlanningWindowDropDown = schedulingWindow.findElement(By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(schedulePlanningWindowDropDown)) {
				Select dropdown= new Select(schedulePlanningWindowDropDown);
				selectedOptionLabel = dropdown.getFirstSelectedOption().getText();
			}
			else
				SimpleUtils.fail("Scheduling Policies: Advance weeks to be Schedule dropdown not loaded.", false);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Advance weeks to be Schedule section not loaded.", false);
		return selectedOptionLabel;
	}
	
	
	@Override
	public void updateSchedulingPoliciesFirstDayOfWeek(String day) throws Exception{
		if(isElementLoaded(schedulingPoliciesFirstDayOfWeek)) {
			WebElement firstDayOfWeekInputBox = schedulingPoliciesFirstDayOfWeek.findElement(
					By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			if(firstDayOfWeekInputBox.isEnabled()) {
				Select dropdown = new Select(firstDayOfWeekInputBox);
				List<WebElement> dropdownOptions = dropdown.getOptions();
				for(WebElement dropdownOption : dropdownOptions) {
					if(dropdownOption.getText().toLowerCase().contains(day.toLowerCase())) {
						click(dropdownOption);
						preserveTheSetting();
					}
				}
				if(getSchedulingPoliciesFirstDayOfWeek().toLowerCase().contains(day.toLowerCase()))
					SimpleUtils.pass("Scheduling Policies: First Day of Week '"+day+"' selected successfully.");
				else
					SimpleUtils.fail("Scheduling Policies: First Day of Week '"+day+"' not selected successfully.", true);
			}
			else
				SimpleUtils.report("Scheduling Policies: First Day of Week input box 'Disabled'.");
		}
		else
			SimpleUtils.fail("Scheduling Policies: First Day of Week Field not loaded.", false);
	}
	
	@Override
	public String getSchedulingPoliciesFirstDayOfWeek() throws Exception{
		String firstDayOfWeekValue = "";
		if(isElementLoaded(schedulingPoliciesFirstDayOfWeek)) {
			WebElement firstDayOfWeekInputBox = schedulingPoliciesFirstDayOfWeek.findElement(
					By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			Select dropdown= new Select(firstDayOfWeekInputBox);
			firstDayOfWeekValue = dropdown.getFirstSelectedOption().getText();
		}
		else
			SimpleUtils.fail("Scheduling Policies: First Day of Week Field not loaded.", false);
		return firstDayOfWeekValue;
	}
	

	@Override
	public void updateEarliestOpeningTime(String openingTime) throws Exception{
		if(isElementLoaded(earliestOpeningTimeDiv)) {
			WebElement openingTimeInputBox = earliestOpeningTimeDiv.findElement(
					By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			if(openingTimeInputBox.isEnabled()) {
				Select dropdown = new Select(openingTimeInputBox);
				List<WebElement> dropdownOptions = dropdown.getOptions();
				for(WebElement dropdownOption : dropdownOptions) {
					if(dropdownOption.getText().toLowerCase().contains(openingTime.toLowerCase())) {
						click(dropdownOption);
						preserveTheSetting();
					}
				}
				if(getEarliestOpeningTime().toLowerCase().contains(openingTime.toLowerCase()))
					SimpleUtils.pass("Scheduling Policies: Earliest Opening Time '"+openingTime+"' selected successfully.");
				else
					SimpleUtils.fail("Scheduling Policies: Earliest Opening Time '"+openingTime+"' not selected successfully.", true);
			}
			else
				SimpleUtils.report("Scheduling Policies: Earliest Opening Time input box 'Disabled'.");
		}
		else
			SimpleUtils.fail("Scheduling Policies: Earliest Opening Time Field not loaded.", false);
	}
	
	@Override
	public String getEarliestOpeningTime() throws Exception{
		String openingTimeValue = "";
		if(isElementLoaded(earliestOpeningTimeDiv)) {
			WebElement openingTimeValueInputBox = earliestOpeningTimeDiv.findElement(
					By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			Select dropdown= new Select(openingTimeValueInputBox);
			openingTimeValue = dropdown.getFirstSelectedOption().getText();
		}
		else
			SimpleUtils.fail("Scheduling Policies: Earliest Opening Time Field not loaded.", false);
		return openingTimeValue;
	}
	
	
	@Override
	public void updateLatestClosingTime(String closingTime) throws Exception{
		if(isElementLoaded(latestClosingTimeDiv)) {
			WebElement openingTimeInputBox = latestClosingTimeDiv.findElement(
					By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			if(openingTimeInputBox.isEnabled()) {
				Select dropdown = new Select(openingTimeInputBox);
				List<WebElement> dropdownOptions = dropdown.getOptions();
				for(WebElement dropdownOption : dropdownOptions) {
					if(dropdownOption.getText().toLowerCase().contains(closingTime.toLowerCase())) {
						click(dropdownOption);
						preserveTheSetting();
					}
				}
				if(getLatestClosingTime().toLowerCase().contains(closingTime.toLowerCase()))
					SimpleUtils.pass("Scheduling Policies: Latest Closing Time '"+closingTime+"' selected successfully.");
				else
					SimpleUtils.fail("Scheduling Policies: Latest Closing Time '"+closingTime+"' not selected successfully.", true);
			}
			else
				SimpleUtils.report("Scheduling Policies: Latest Closing Time input box 'Disabled'.");
		}
		else
			SimpleUtils.fail("Scheduling Policies: Latest Closing Time Field not loaded.", false);
	}
	
	@Override
	public String getLatestClosingTime() throws Exception{
		String openingTimeValue = "";
		if(isElementLoaded(latestClosingTimeDiv)) {
			WebElement openingTimeValueInputBox = latestClosingTimeDiv.findElement(
					By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			Select dropdown= new Select(openingTimeValueInputBox);
			openingTimeValue = dropdown.getFirstSelectedOption().getText();
		}
		else
			SimpleUtils.fail("Scheduling Policies: Latest Closing Time Field not loaded.", false);
		return openingTimeValue;
	}
	
	@Override
	public void updateAdvanceScheduleDaysToBeFinalize(String advanceDays) throws Exception {
		if(isElementLoaded(advanceFinalizeDaysDiv)) {
			WebElement finalizeScheduleDaysField = advanceFinalizeDaysDiv.findElement(By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(finalizeScheduleDaysField)) {
				finalizeScheduleDaysField.clear();
				finalizeScheduleDaysField.sendKeys(advanceDays);
				if(isElementLoaded(confirmSaveButton)) {
					click(confirmSaveButton);
					preserveTheSetting();
				}
				if(getAdvanceScheduleDaysCountToBeFinalize() == Integer.valueOf(advanceDays))
					SimpleUtils.pass("Sceduling Policies: Advance days to be finalize updated successfully with value: '"+advanceDays+"'");
				else
					SimpleUtils.fail("Sceduling Policies: Advance days to be finalize not updated with value: '"+advanceDays+"'", true);
			}
			else
				SimpleUtils.fail("Scheduling Policies: Advance Schedule days to be Finalize input field not loaded.", false);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Advance Schedule days to be Finalize section not loaded.", false);
	}
	
	@Override
	public void updateScheduleBufferHoursBefore(String beforeBufferCount) throws Exception{
		if(isElementLoaded(openingBufferHours)) {
			WebElement beforeBufferHrsElement = openingBufferHours.findElement(By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(beforeBufferHrsElement)) {
				beforeBufferHrsElement.clear();
				beforeBufferHrsElement.sendKeys(beforeBufferCount);
				if(isElementLoaded(confirmSaveButton)) {
					click(confirmSaveButton);
					preserveTheSetting();
				}
				if(getScheduleBufferHours().get("openingBufferHours") == Integer.valueOf(beforeBufferCount))
					SimpleUtils.pass("Scheduling Policies: Schedule Buffer hours BEFORE updated successfully with value :'"+beforeBufferCount+"'");
				else
					SimpleUtils.fail("Scheduling Policies: Schedule Buffer hours BEFORE not updated with value :'"+beforeBufferCount+"'", true);
			}
			else
				SimpleUtils.fail("Scheduling Policies: Schedule Buffer hours BEFORE element not loaded.", true);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Opening Buffer hours section not loaded.", true);
	}
	
	@Override
	public void updateScheduleBufferHoursAfter(String afterBufferCount) throws Exception{
		if(isElementLoaded(closingBufferHours)) {
			WebElement afterBufferHrsElement = closingBufferHours.findElement(By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(afterBufferHrsElement)) {
				afterBufferHrsElement.clear();
				afterBufferHrsElement.sendKeys(afterBufferCount);
				if(isElementLoaded(confirmSaveButton)) {
					click(confirmSaveButton);
					preserveTheSetting();
				}
				if(getScheduleBufferHours().get("closingBufferHours") == Integer.valueOf(afterBufferCount))
					SimpleUtils.pass("Scheduling Policies: Schedule Buffer hours AFTER updated successfully with value :'"+afterBufferCount+"'");
				else
					SimpleUtils.fail("Scheduling Policies: Schedule Buffer hours AFTER not updated with value :'"+afterBufferCount+"'", true);
			}
			else
				SimpleUtils.fail("Scheduling Policies: Schedule Buffer hours AFTER element not loaded.", true);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Closing Buffer hours section not loaded.", true);
	}
	
	
	@Override
	public void updateMinimumShiftLengthHour(String shiftLengthHour) throws Exception{
		if(isElementLoaded(minShiftLengthHourDiv)) {
			WebElement minShiftLengthHourInputBox = minShiftLengthHourDiv.findElement(By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(minShiftLengthHourInputBox)) {
				minShiftLengthHourInputBox.clear();
				minShiftLengthHourInputBox.sendKeys(shiftLengthHour);
				if(isElementLoaded(confirmSaveButton)) {
					click(confirmSaveButton);
					preserveTheSetting();
				}
				if(getMinimumShiftLengthHour() == Float.valueOf(shiftLengthHour))
					SimpleUtils.pass("Scheduling Policies: Schedule Minimum Shift Length Hour updated successfully with value :'"+shiftLengthHour+"'");
				else
					SimpleUtils.fail("Scheduling Policies: Schedule Minimum Shift Length Hour not updated with value :'"+shiftLengthHour+"'", true);
			}
			else
				SimpleUtils.fail("Scheduling Policies: Schedule Minimum Shift Length Hour element not loaded.", true);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Schedule Minimum Shift Length Hour section not loaded.", true);
	}
	
	@Override
	public float getMinimumShiftLengthHour() throws Exception{
		float minimumShiftLengthHour = 0;
		if(isElementLoaded(minShiftLengthHourDiv)) {
			WebElement minShiftLengthHourInputBox = minShiftLengthHourDiv.findElement(By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(minShiftLengthHourInputBox)) {
				minimumShiftLengthHour = Float.valueOf(minShiftLengthHourInputBox.getAttribute("value"));
			}
			else
				SimpleUtils.fail("Scheduling Policies:  Schedule Minimum Shift Length Hour element not loaded.", true);
		}
		else
			SimpleUtils.fail("Scheduling Policies:  Schedule Minimum Shift Length Hour section not loaded.", true);
		return minimumShiftLengthHour;
	}
	
	
	@Override
	public void updateMaximumShiftLengthHour(String shiftLengthHour) throws Exception{
		if(isElementLoaded(maxShiftLengthHourDiv)) {
			WebElement maxShiftLengthHourInputBox = maxShiftLengthHourDiv.findElement(By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(maxShiftLengthHourInputBox)) {
				maxShiftLengthHourInputBox.clear();
				maxShiftLengthHourInputBox.sendKeys(shiftLengthHour);
				if(isElementLoaded(confirmSaveButton)) {
					click(confirmSaveButton);
					preserveTheSetting();
				}
				if(getMaximumShiftLengthHour() == Float.valueOf(shiftLengthHour))
					SimpleUtils.pass("Scheduling Policies: Schedule Maximum Shift Length Hour updated successfully with value :'"+shiftLengthHour+"'");
				else
					SimpleUtils.fail("Scheduling Policies: Schedule Maximum Shift Length Hour not updated with value :'"+shiftLengthHour+"'", true);
			}
			else
				SimpleUtils.fail("Scheduling Policies: Schedule Maximum Shift Length Hour element not loaded.", true);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Schedule Maximum Shift Length Hour section not loaded.", true);
	}
	
	@Override
	public float getMaximumShiftLengthHour() throws Exception{
		float minimumShiftLengthHour = 0;
		if(isElementLoaded(maxShiftLengthHourDiv)) {
			WebElement maxShiftLengthHourInputBox = maxShiftLengthHourDiv.findElement(By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(maxShiftLengthHourInputBox)) {
				minimumShiftLengthHour = Float.valueOf(maxShiftLengthHourInputBox.getAttribute("value"));
			}
			else
				SimpleUtils.fail("Scheduling Policies: Schedule Maximum Shift Length Hour element not loaded.", true);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Schedule Maximum Shift Length Hour section not loaded.", true);
		return minimumShiftLengthHour;
	}
	
	
	@Override
	public void updateMaximumNumWeekDaysToAutoSchedule(String maxDaysLabel) throws Exception{
		if(isElementLoaded(maxWorkingDaysBestPracticeInputField)) {
			WebElement maxWeekDaysToAutoSchedule = maxWorkingDaysBestPracticeInputField.findElement(By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(maxWeekDaysToAutoSchedule)) {
				Select dropdown = new Select(maxWeekDaysToAutoSchedule);
				List<WebElement> dropdownOptions = dropdown.getOptions();
				for(WebElement dropdownOption : dropdownOptions) {
					if(dropdownOption.getText().toLowerCase().contains(maxDaysLabel.toLowerCase())) {
						click(dropdownOption);
						preserveTheSetting();
					}
				}
				if(getMaximumNumWeekDaysToAutoSchedule().toLowerCase().contains(maxDaysLabel.toLowerCase()))
					SimpleUtils.pass("Scheduling Policies: Maximum Number of Week Days To Auto Schedule updated successfully with value :'"
							+ maxDaysLabel +"'");
				else
					SimpleUtils.fail("Scheduling Policies: Maximum Number of Week Days To Auto Schedule not updated with value :'"
							+ maxDaysLabel +"'", true);
			}
			else
				SimpleUtils.fail("Scheduling Policies: Maximum Number of Week Days To Auto Schedule element not loaded.", true);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Maximum Number of Week Days To Auto Schedule section not loaded.", true);
	}
	
	@Override
	public String getMaximumNumWeekDaysToAutoSchedule() throws Exception{
		String minimumShiftLengthHourValue = "";
		if(isElementLoaded(maxWorkingDaysBestPracticeInputField)) {
			WebElement maximumNumWeekDaysToAutoSchedule = maxWorkingDaysBestPracticeInputField.findElement(By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(maximumNumWeekDaysToAutoSchedule)) {
				Select dropdown= new Select(maximumNumWeekDaysToAutoSchedule);
				minimumShiftLengthHourValue = dropdown.getFirstSelectedOption().getText();
			}
			else
				SimpleUtils.fail("Scheduling Policies: Maximum Number of Week Days To Auto Schedule element not loaded.", true);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Maximum Number of Week Days To Auto Schedule section not loaded.", true);
		
		return minimumShiftLengthHourValue;
	}
	
	
	@Override
	public void updateDisplayShiftBreakIcons(String shiftBreakIcons) throws Exception{
		if(isElementLoaded(displayBreakTimeInShiftInputField)) {
			WebElement shiftBreakIconsElement = displayBreakTimeInShiftInputField.findElement(By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(shiftBreakIconsElement)) {
				Select dropdown = new Select(shiftBreakIconsElement);
				List<WebElement> dropdownOptions = dropdown.getOptions();
				for(WebElement dropdownOption : dropdownOptions) {
					if(dropdownOption.getText().toLowerCase().contains(shiftBreakIcons.toLowerCase())) {
						click(dropdownOption);
						preserveTheSetting();
					}
				}
				if(getDisplayShiftBreakIcons().toLowerCase().contains(shiftBreakIcons.toLowerCase()))
					SimpleUtils.pass("Scheduling Policies: Display Shift Break Icons updated successfully with value :'"
							+ shiftBreakIcons +"'");
				else
					SimpleUtils.fail("Scheduling Policies: Display Shift Break Icons not updated with value :'"
							+ shiftBreakIcons +"'", true);
			}
			else
				SimpleUtils.fail("Scheduling Policies: Display Shift Break Icons element not loaded.", true);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Display Shift Break Icons section not loaded.", true);
	}
	
	@Override
	public String getDisplayShiftBreakIcons() throws Exception{
		String displayShiftBreakIconsValue = "";
		if(isElementLoaded(displayBreakTimeInShiftInputField)) {
			WebElement maxWeekDaysToAutoSchedule = displayBreakTimeInShiftInputField.findElement(By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(maxWeekDaysToAutoSchedule)) {
				Select dropdown = new Select(maxWeekDaysToAutoSchedule);
				displayShiftBreakIconsValue = dropdown.getFirstSelectedOption().getText();
			}
			else
				SimpleUtils.fail("Scheduling Policies: Display Shift Break Icons element not loaded.", true);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Display Shift Break Icons section not loaded.", true);
		
		return displayShiftBreakIconsValue;
	}
	
	
	@Override
	public void updateApplyLaborBudgetToSchedules(String isLaborBudgetToApply) throws Exception{
		if(isElementLoaded(applyLaborBudgetToSchedules)) {
			WebElement applyLaborBudgetBtnGroup = applyLaborBudgetToSchedules.findElement(
					By.cssSelector("div.lg-button-group"));
			if(applyLaborBudgetBtnGroup.getAttribute("class").contains("disabled")) {
				SimpleUtils.report("Scheduling Policies: Apply Labor Budget to Schedules buttons are disabled.");
			}
			else {
				List<WebElement> applyLaborBudgetToSchedulesBtns = applyLaborBudgetToSchedules.findElements(
						By.cssSelector("div[ng-click=\"$ctrl.change(button.value)\"]"));
				if(applyLaborBudgetToSchedulesBtns.size() > 0) {
					for(WebElement applyLaborBudgetToSchedulesBtn : applyLaborBudgetToSchedulesBtns) {
						if(applyLaborBudgetToSchedulesBtn.getText().toLowerCase().contains(isLaborBudgetToApply.toLowerCase())) {
							click(applyLaborBudgetToSchedulesBtn);
							preserveTheSetting();
						}
					}
					if(getApplyLaborBudgetToSchedulesActiveBtnLabel().toLowerCase().contains(isLaborBudgetToApply.toLowerCase()))
						SimpleUtils.pass("Scheduling Policies: Apply Labor Budget to Schedules updated with value: '"
								+isLaborBudgetToApply+"'.");
					else
						SimpleUtils.fail("Scheduling Policies: Apply Labor Budget to Schedules not updated with value: '"
								+isLaborBudgetToApply+"'.", true);
				}
				else
					SimpleUtils.fail("Scheduling Policies: Apply Labor Budget to Schedules buttons not loaded.", true);
			}
			
		}
		else
			SimpleUtils.fail("Scheduling Policies: Apply Labor Budget to Schedules section not loaded.", true);
	}
	
	public String getApplyLaborBudgetToSchedulesActiveBtnLabel() throws Exception
	{
		String laborBudgetToApplyActiveBtnLabel = "";
		if(isElementLoaded(applyLaborBudgetToSchedules)) {
			WebElement applyLaborBudgetActiveBtn = applyLaborBudgetToSchedules.findElement(
					By.cssSelector("div.lg-button-group-selected"));
			if(isElementLoaded(applyLaborBudgetActiveBtn))
				laborBudgetToApplyActiveBtnLabel = applyLaborBudgetActiveBtn.getText();
			else 
				SimpleUtils.fail("Scheduling Policies: Labor Budget to Apply active Button not loaded.", true);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Apply Labor Budget to Schedules section not loaded.", true);
		
		return laborBudgetToApplyActiveBtnLabel;
	}
	
	
	@Override
	public void updateScheduleBudgetType(String budgetType) throws Exception{
		if(isElementLoaded(scheduleBudgetTypeElement)) {
			WebElement budgetTypeElement = scheduleBudgetTypeElement.findElement(
					By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			if(!budgetTypeElement.isEnabled()) {
				SimpleUtils.report("Scheduling Policies: Schedule budget Type Dropdown desabled.");
			}
			else {
				if(isElementLoaded(budgetTypeElement)) {
					Select dropdown = new Select(budgetTypeElement);
					
					List<WebElement> dropdownOptions = dropdown.getOptions();
					for(WebElement dropdownOption : dropdownOptions) {
						if(dropdownOption.getText().toLowerCase().contains(budgetType.toLowerCase())) {
							click(dropdownOption);
							preserveTheSetting();
						}
					}
					if(getScheduleBudgetType().toLowerCase().contains(budgetType.toLowerCase()))
						SimpleUtils.pass("Scheduling Policies: Schedule budget Type updated with the value :'"+budgetType+"'.");
					else
						SimpleUtils.fail("Scheduling Policies: Schedule budget Type not updated with the value :'"+budgetType+"'.", true);
				}
				else
					SimpleUtils.fail("Scheduling Policies: Schedule budget Type dropdown not loaded.", true);
			}
		}
		else
			SimpleUtils.fail("Scheduling Policies: Schedule budget Type section not loaded.", true);
	}
	
	public String getScheduleBudgetType() throws Exception
	{
		String BudgetTypeLabel = "";
		if(isElementLoaded(scheduleBudgetTypeElement)) {
			WebElement budgetTypeElement = scheduleBudgetTypeElement.findElement(
					By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
				if(isElementLoaded(budgetTypeElement)) {
					Select dropdown = new Select(budgetTypeElement);
					BudgetTypeLabel = dropdown.getFirstSelectedOption().getText();
				}
				else
					SimpleUtils.fail("Scheduling Policies: Schedule budget Type dropdown not loaded.", true);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Schedule budget Type section not loaded.", true);
		
		return BudgetTypeLabel;
	}
	
	
	@Override
	public void updateTeamAvailabilityLockMode(String availabilityLockModeLabel) throws Exception{
		if(isElementLoaded(availabilityLockModeDiv)) {
			WebElement budgetTypeElement = availabilityLockModeDiv.findElement(
					By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			if(!budgetTypeElement.isEnabled()) {
				SimpleUtils.report("Scheduling Policies: Team Availability Lock Mode desabled.");
			}
			else {
				if(isElementLoaded(budgetTypeElement)) {
					Select dropdown = new Select(budgetTypeElement);
					
					List<WebElement> dropdownOptions = dropdown.getOptions();
					for(WebElement dropdownOption : dropdownOptions) {
						if(dropdownOption.getText().toLowerCase().contains(availabilityLockModeLabel.toLowerCase())) {
							click(dropdownOption);
							preserveTheSetting();
						}
					}
					if(getTeamAvailabilityLockMode().toLowerCase().contains(availabilityLockModeLabel.toLowerCase()))
						SimpleUtils.pass("Scheduling Policies: Team Availability Lock Mode updated with the value :'"
								+availabilityLockModeLabel+"'.");
					else
						SimpleUtils.fail("Scheduling Policies: Team Availability Lock Mode not updated with the value :'"
								+availabilityLockModeLabel+"'.", true);
				}
				else
					SimpleUtils.fail("Scheduling Policies: Team Availability Lock Mode dropdown not loaded.", true);
			}
		}
		else
			SimpleUtils.fail("Scheduling Policies: Team Availability Lock Mode section not loaded.", true);
	}


	public String getTeamAvailabilityLockMode() throws Exception {
		String availabilityLockModeLabel = "";
		if(isElementLoaded(availabilityLockModeDiv)) {
			WebElement budgetTypeElement = availabilityLockModeDiv.findElement(
					By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
				if(isElementLoaded(budgetTypeElement)) {
					Select dropdown = new Select(budgetTypeElement);
					availabilityLockModeLabel = dropdown.getFirstSelectedOption().getText();
				}
				else
					SimpleUtils.fail("Scheduling Policies: Team Availability Lock Mode dropdown not loaded.", true);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Team Availability Lock Mode section not loaded.", true);
		
		return availabilityLockModeLabel;
	}
	
	
	@Override
	public void updateScheduleUnavailableHourOfWorkers(String unavailableWorkersHour) throws Exception{
		if(isElementLoaded(scheduleUnavailableHourWorkersDiv)) {
			WebElement scheduleUnavailableHourWorkers = scheduleUnavailableHourWorkersDiv.findElement(
					By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
			if(!scheduleUnavailableHourWorkers.isEnabled()) {
				SimpleUtils.report("Scheduling Policies: Schedule Unavailable Hour of Workers field desabled.");
			}
			else {
				if(isElementLoaded(scheduleUnavailableHourWorkers)) {
					Select dropdown = new Select(scheduleUnavailableHourWorkers);
					
					List<WebElement> dropdownOptions = dropdown.getOptions();
					for(WebElement dropdownOption : dropdownOptions) {
						if(dropdownOption.getText().toLowerCase().contains(unavailableWorkersHour.toLowerCase())) {
							click(dropdownOption);
							preserveTheSetting();
						}
					}
					if(getScheduleUnavailableHourOfWorkers().toLowerCase().contains(unavailableWorkersHour.toLowerCase()))
						SimpleUtils.pass("Scheduling Policies: Schedule Unavailable Hour of Workers updated with the value :'"
								+unavailableWorkersHour+"'.");
					else
						SimpleUtils.fail("Scheduling Policies: Schedule Unavailable Hour of Workers not updated with the value :'"
								+unavailableWorkersHour+"'.", true);
				}
				else
					SimpleUtils.fail("Scheduling Policies: Schedule Unavailable Hour of Workers dropdown not loaded.", true);
			}
		}
		else
			SimpleUtils.fail("Scheduling Policies: Schedule Unavailable Hour of Workers section not loaded.", true);
	}


	public String getScheduleUnavailableHourOfWorkers() throws Exception {
		String unavailableHourOfWorkers = "";
		if(isElementLoaded(scheduleUnavailableHourWorkersDiv)) {
			WebElement scheduleUnavailableHourWorkers = scheduleUnavailableHourWorkersDiv.findElement(
					By.cssSelector("select[ng-change=\"$ctrl.handleChange()\"]"));
				if(isElementLoaded(scheduleUnavailableHourWorkers)) {
					Select dropdown = new Select(scheduleUnavailableHourWorkers);
					unavailableHourOfWorkers = dropdown.getFirstSelectedOption().getText();
				}
				else
					SimpleUtils.fail("Scheduling Policies: Schedule Unavailable Hour of Workers dropdown not loaded.", true);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Schedule Unavailable Hour of Workers section not loaded.", true);
		
		return unavailableHourOfWorkers;
	}
	
	
	@Override
	public void updateAvailabilityToleranceField(String availabilityToleranceMinutes) throws Exception{
		if(isElementLoaded(availabilityToleranceField)) {
			WebElement availabilityToleranceInputBox = availabilityToleranceField.findElement(
					By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
			if(!availabilityToleranceInputBox.isEnabled()) {
				SimpleUtils.report("Scheduling Policies: Availability Tolerance Field desabled.");
			}
			else {
				if(isElementLoaded(availabilityToleranceInputBox)) {
					availabilityToleranceInputBox.clear();
					availabilityToleranceInputBox.sendKeys(availabilityToleranceMinutes);
					if(isElementLoaded(confirmSaveButton))
						click(confirmSaveButton);
					preserveTheSetting();
						if(getAvailabilityToleranceMinutes()== Float.valueOf(availabilityToleranceMinutes))
							SimpleUtils.pass("Scheduling Policies: Availability Tolerance Field updated with the value :'"+availabilityToleranceMinutes+"'.");
						else
							SimpleUtils.fail("Scheduling Policies: Availability Tolerance Field not updated with the value :'"+availabilityToleranceMinutes+"'.", true);
				}
				else
					SimpleUtils.fail("Scheduling Policies: Availability Tolerance Field not loaded.", true);
				}
		}
		else
			SimpleUtils.fail("Scheduling Policies: SAvailability Tolerance Field section not loaded.", true);
	}


	public float getAvailabilityToleranceMinutes() throws Exception {
		float availabilityToleranceMinutes = 0;
		if(isElementLoaded(availabilityToleranceField)) {
			WebElement availabilityToleranceInputBox = availabilityToleranceField.findElement(
					By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
				if(isElementLoaded(availabilityToleranceInputBox)) {
					availabilityToleranceMinutes = Float.valueOf(availabilityToleranceInputBox.getAttribute("value"));
				}
				else
					SimpleUtils.fail("Scheduling Policies: Availability Tolerance Field not loaded.", true);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Availability Tolerance Field section not loaded.", true);
		
		return availabilityToleranceMinutes;
	}
	
	
	@Override
	public void updateCanWorkerRequestTimeOff(String canWorkerRequestTimeOffValue) throws Exception{
		if(isElementLoaded(canWorkerRequestTimeOffBtnGroup)) {
			WebElement canWorkerRequestTimeOffBtnGroupDiv = canWorkerRequestTimeOffBtnGroup.findElement(
					By.cssSelector("div.lg-button-group"));
			if(canWorkerRequestTimeOffBtnGroupDiv.getAttribute("class").contains("disabled")) {
				SimpleUtils.report("Scheduling Policies: Can Worker Request Time Off buttons are disabled.");
			}
			else {
				List<WebElement> canWorkerRequestTimeOffBtns = canWorkerRequestTimeOffBtnGroup.findElements(
						By.cssSelector("div[ng-click=\"$ctrl.change(button.value)\"]"));
				if(canWorkerRequestTimeOffBtns.size() > 0) {
					for(WebElement canWorkerRequestTimeOffBtn : canWorkerRequestTimeOffBtns) {
						if(canWorkerRequestTimeOffBtn.getText().toLowerCase().contains(canWorkerRequestTimeOffValue.toLowerCase())) {
							click(canWorkerRequestTimeOffBtn);
							preserveTheSetting();
						}
					}
					if(getCanWorkerRequestTimeOffActiveBtnLabel().toLowerCase().contains(canWorkerRequestTimeOffValue.toLowerCase()))
						SimpleUtils.pass("Scheduling Policies: Can Worker Request Time Off  updated with value: '"
								+canWorkerRequestTimeOffValue+"'.");
					else
						SimpleUtils.fail("Scheduling Policies: Can Worker Request Time Off  not updated with value: '"
								+canWorkerRequestTimeOffValue+"'.", true);
				}
				else
					SimpleUtils.fail("Scheduling Policies: Can Worker Request Time Off  buttons not loaded.", true);
			}
			
		}
		else
			SimpleUtils.fail("Scheduling Policies: Can Worker Request Time Off section not loaded.", true);
	}
	
	public String getCanWorkerRequestTimeOffActiveBtnLabel() throws Exception
	{
		String canWorkerRequestTimeOffActiveBtnLabel = "";
		if(isElementLoaded(canWorkerRequestTimeOffBtnGroup)) {
			WebElement canWorkerRequestTimeOffBtn = canWorkerRequestTimeOffBtnGroup.findElement(
					By.cssSelector("div.lg-button-group-selected"));
			if(isElementLoaded(canWorkerRequestTimeOffBtn))
				canWorkerRequestTimeOffActiveBtnLabel = canWorkerRequestTimeOffBtn.getText();
			else 
				SimpleUtils.fail("Scheduling Policies: Can Worker Request Time Off Button not loaded.", true);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Can Worker Request Time Off section not loaded.", true);
		
		return canWorkerRequestTimeOffActiveBtnLabel;
	}
	
	
	@Override
	public void updateMaxEmployeeCanRequestForTimeOffOnSameDay(String maxWorkersTimeOfPerDayCount) throws Exception
	{
		if(isElementLoaded(maxWorkersOnTimeOffPerDayField)) {
			WebElement maxWorkersTimeOfPerDayInputBox = maxWorkersOnTimeOffPerDayField.findElement(
					By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(maxWorkersTimeOfPerDayInputBox)) {
				maxWorkersTimeOfPerDayInputBox.clear();
				maxWorkersTimeOfPerDayInputBox.sendKeys(maxWorkersTimeOfPerDayCount);
				if(isElementLoaded(confirmSaveButton)) {
					click(confirmSaveButton);
					preserveTheSetting();
				}
				if(getMaxEmployeeCanRequestForTimeOffOnSameDay() == Float.valueOf(maxWorkersTimeOfPerDayCount))
					SimpleUtils.pass("Scheduling Policies: Max Workers TimeOf Per Day updated successfully with value :'"
							+ maxWorkersTimeOfPerDayCount +"'");
				else
					SimpleUtils.fail("Scheduling Policies: Max Workers TimeOf Per Day not updated with value :'"
							+ maxWorkersTimeOfPerDayCount +"'", true);
			}
			else
				SimpleUtils.fail("Scheduling Policies: Max Workers TimeOf Per Day element not loaded.", true);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Max Workers TimeOf Per Day section not loaded.", true);
	}


	private float getMaxEmployeeCanRequestForTimeOffOnSameDay() throws Exception {
		float minimumShiftLengthHour = 0;
		if(isElementLoaded(maxWorkersOnTimeOffPerDayField)) {
			WebElement maxWorkersOnTimeOffPerDayInputBox = maxWorkersOnTimeOffPerDayField.findElement(
					By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(maxWorkersOnTimeOffPerDayInputBox)) {
				minimumShiftLengthHour = Float.valueOf(maxWorkersOnTimeOffPerDayInputBox.getAttribute("value"));
			}
			else
				SimpleUtils.fail("Scheduling Policies: Max Workers TimeOf Per Day element not loaded.", true);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Max Workers TimeOf Per Day section not loaded.", true);
		return minimumShiftLengthHour;
	}
	
	
	@Override
	public void clickOnSchedulingPoliciesTimeOffAdvanceBtn() throws Exception {
		if(isElementLoaded(schedulingPoliciesTimeOffFormSectionDiv)) {
			WebElement schedulingPoliciesTimeOffAdvanceBtn = schedulingPoliciesTimeOffFormSectionDiv.findElement(
					By.cssSelector("div.lg-advanced-box__toggle"));
			if(isElementLoaded(schedulingPoliciesTimeOffAdvanceBtn) && !schedulingPoliciesTimeOffAdvanceBtn.getAttribute("class")
					.contains("--advanced")) {
				click(schedulingPoliciesTimeOffAdvanceBtn);
				SimpleUtils.pass("Controls Page: - Scheduling Policies 'Time Off' section: 'Advance' button clicked.");
			}
			else
				SimpleUtils.fail("Controls Page: - Scheduling Policies 'Time Off' section: 'Advance' button not loaded.", false);
		}
		else
			SimpleUtils.fail("Controls Page: - Scheduling Policies section: 'Time Off' form section not loaded.", false);
	}
	
	@Override
	public void updateNoticePeriodToRequestTimeOff(String noticePeriodToRequestTimeOff) throws Exception
	{
		if(isElementLoaded(noticePeriodToRequestTimeOffField)) {
			WebElement noticePeriodToRequestTimeOffInputBox = noticePeriodToRequestTimeOffField.findElement(
					By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(noticePeriodToRequestTimeOffInputBox)) {
				noticePeriodToRequestTimeOffInputBox.clear();
				noticePeriodToRequestTimeOffInputBox.sendKeys(noticePeriodToRequestTimeOff);
				if(isElementLoaded(confirmSaveButton)) {
					click(confirmSaveButton);
					preserveTheSetting();
				}
				if(getNoticePeriodToRequestTimeOff() == Float.valueOf(noticePeriodToRequestTimeOff))
					SimpleUtils.pass("Scheduling Policies: Notice Period To Request Time Off updated successfully with value :'"
							+ noticePeriodToRequestTimeOff +"'");
				else
					SimpleUtils.fail("Scheduling Policies: Notice Period To Request Time Off not updated with value :'"
							+ noticePeriodToRequestTimeOff +"'", true);
			}
			else
				SimpleUtils.fail("Scheduling Policies: Notice Period To Request Time Off element not loaded.", true);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Notice Period To Request Time Off section not loaded.", true);
	}


	private float getNoticePeriodToRequestTimeOff() throws Exception {
		float noticePeriodToRequestTimeOffValue = 0;
		if(isElementLoaded(noticePeriodToRequestTimeOffField)) {
			WebElement noticePeriodToRequestTimeOffInputBox = noticePeriodToRequestTimeOffField.findElement(
					By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
			if(isElementLoaded(noticePeriodToRequestTimeOffInputBox)) {
				noticePeriodToRequestTimeOffValue = Float.valueOf(noticePeriodToRequestTimeOffInputBox.getAttribute("value"));
			}
			else
				SimpleUtils.fail("Scheduling Policies: Notice Period To Request Time Off element not loaded.", true);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Notice Period To Request Time Off section not loaded.", true);
		return noticePeriodToRequestTimeOffValue;
	}
	
	@Override
	public void updateShowTimeOffReasons(String isShowTimeOffReasons) throws Exception{
		if(isElementLoaded(showTimeOffReasonsBtnGroup)) {
			WebElement showTimeOffReasonsBtnGroupBtnGroupDiv = showTimeOffReasonsBtnGroup.findElement(
					By.cssSelector("div.lg-button-group"));
			if(showTimeOffReasonsBtnGroupBtnGroupDiv.getAttribute("class").contains("disabled")) {
				SimpleUtils.report("Scheduling Policies: Require time off reason in the request buttons are disabled.");
			}
			else {
				List<WebElement> showTimeOffReasonsBtns = showTimeOffReasonsBtnGroup.findElements(
						By.cssSelector("div[ng-click=\"$ctrl.change(button.value)\"]"));
				if(showTimeOffReasonsBtns.size() > 0) {
					for(WebElement showTimeOffReasonsBtn : showTimeOffReasonsBtns) {
						if(showTimeOffReasonsBtn.getText().toLowerCase().contains(isShowTimeOffReasons.toLowerCase())) {
							click(showTimeOffReasonsBtn);
							preserveTheSetting();
						}
					}
					if(getShowTimeOffReasons().toLowerCase().contains(isShowTimeOffReasons.toLowerCase()))
						SimpleUtils.pass("Scheduling Policies: Require time off reason in the request updated with value: '"
								+isShowTimeOffReasons+"'.");
					else
						SimpleUtils.fail("Scheduling Policies: Require time off reason in the request not updated with value: '"
								+isShowTimeOffReasons+"'.", true);
				}
				else
					SimpleUtils.fail("Scheduling Policies: Require time off reason in the request buttons not loaded.", true);
			}
			
		}
		else
			SimpleUtils.fail("Scheduling Policies: Require time off reason in the request section not loaded.", true);
	}
	
	public String getShowTimeOffReasons() throws Exception
	{
		String showTimeOffReasonsValue = "";
		if(isElementLoaded(showTimeOffReasonsBtnGroup)) {
			WebElement showTimeOffReasonsActiveBtn = showTimeOffReasonsBtnGroup.findElement(
					By.cssSelector("div.lg-button-group-selected"));
			if(isElementLoaded(showTimeOffReasonsActiveBtn))
				showTimeOffReasonsValue = showTimeOffReasonsActiveBtn.getText();
			else 
				SimpleUtils.fail("Scheduling Policies: Require time off reason in the request Button not loaded.", true);
		}
		else
			SimpleUtils.fail("Scheduling Policies: Require time off reason in the request section not loaded.", true);
		
		return showTimeOffReasonsValue;
	}
	
	public void preserveTheSetting() throws Exception
	{
		if(isElementLoaded(preserveSettingBtn, 10))
		{
			click(preserveSettingBtn);
		}
	}
	
	
	@Override
	public void updateSchedulingPolicyGroupsHoursPerWeek(int minHours, int maxHours, int idealHours) throws Exception
	{
		for(int index = 0; index < numHoursPerWeekMinInputFields.size(); index++) {
			
			if(numHoursPerWeekMinInputFields.get(index).isDisplayed()) {
				WebElement minHoursPerWeekInputBox = numHoursPerWeekMinInputFields.get(index).findElement(
						By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
				if(isElementLoaded(minHoursPerWeekInputBox)) {
					int value = Integer.valueOf(minHoursPerWeekInputBox.getAttribute("value"));
					if(value == minHours) {
						SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Hours Per Week 'Minimum Hours' already updated with value:'"+ minHours +"'.");
					}
					else {
						minHoursPerWeekInputBox.clear();
						minHoursPerWeekInputBox.sendKeys(String.valueOf(minHours));
						if(isElementLoaded(confirmSaveButton, 10))
							click(confirmSaveButton);
						if(Integer.valueOf(minHoursPerWeekInputBox.getAttribute("value")) == minHours)
							SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Hours Per Week 'Minimum Hours' updated with value:'"+ minHours +"'.");
						else
							SimpleUtils.fail("Scheduling Policies: Scheduling Policy Groups Hours Per Week 'Minimum Hours' not updated with value:'"+ minHours +"'.", true);
					}
				}
				else {
					SimpleUtils.fail("Scheduling Policies: Scheduling Policy Groups Hours Per Week 'Minimum Hours' Input box not loaded.", true);
				}
			}	
			
			if(numHoursPerWeekMaxInputFields.get(index).isDisplayed()) {
				WebElement maxHoursPerWeekInputBox = numHoursPerWeekMaxInputFields.get(index).findElement(
						By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
				if(isElementLoaded(maxHoursPerWeekInputBox)) {
					int value = Integer.valueOf(maxHoursPerWeekInputBox.getAttribute("value"));
					if(value == maxHours) {
						SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Hours Per Week 'Maximum Hours' already updated with value:'"+ maxHours +"'.");
					}
					else {
						maxHoursPerWeekInputBox.clear();
						maxHoursPerWeekInputBox.sendKeys(String.valueOf(maxHours));
						if(isElementLoaded(confirmSaveButton, 10))
							click(confirmSaveButton);
						if(Integer.valueOf(maxHoursPerWeekInputBox.getAttribute("value")) == maxHours)
							SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Hours Per Week 'Maximum Hours' updated with value:'"+ maxHours +"'.");
						else
							SimpleUtils.fail("Scheduling Policies: Scheduling Policy Groups Hours Per Week 'Maximum Hours' not updated with value:'"+ maxHours +"'.", true);
					}
				}
				else {
					SimpleUtils.fail("Scheduling Policies: Scheduling Policy Groups Hours Per Week 'Maximum Hours' Input box not loaded.", true);
				}
			}	
			
			if(numHoursPerWeekIdealInputFields.get(index).isDisplayed()) {
				WebElement idealHoursPerWeekInputBox = numHoursPerWeekIdealInputFields.get(index).findElement(
						By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
				if(isElementLoaded(idealHoursPerWeekInputBox)) {
					int value = Integer.valueOf(idealHoursPerWeekInputBox.getAttribute("value"));
					if(value == idealHours) {
						SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Hours Per Week 'Ideal Hours' already updated with value:'"+ idealHours +"'.");
					}
					else {
						idealHoursPerWeekInputBox.clear();
						idealHoursPerWeekInputBox.sendKeys(String.valueOf(idealHours));
						if(isElementLoaded(confirmSaveButton, 10))
							click(confirmSaveButton);
						if(Integer.valueOf(idealHoursPerWeekInputBox.getAttribute("value")) == idealHours)
							SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Hours Per Week 'Ideal Hours' updated with value:'"+ idealHours +"'.");
						else
							SimpleUtils.fail("Scheduling Policies: Scheduling Policy Groups Hours Per Week 'Ideal Hours' not updated with value:'"+ idealHours +"'.", true);
					}
				}
				else {
					SimpleUtils.fail("Scheduling Policies: Scheduling Policy Groups Hours Per Week 'Ideal Hours' Input box not loaded.", true);
				}
			}	
		}
	}
	
	
	@Override
	public void updateSchedulingPolicyGroupsShiftsPerWeek(int minShifts, int maxShifts, int idealShifts) throws Exception
	{
		for(int index = 0; index < numShiftsPerWeekMinInputFields.size(); index++) {
			
			if(numShiftsPerWeekMinInputFields.get(index).isDisplayed()) {
				WebElement minShiftsPerWeekInputBox = numShiftsPerWeekMinInputFields.get(index).findElement(
						By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
				if(isElementLoaded(minShiftsPerWeekInputBox)) {
					int value = Integer.valueOf(minShiftsPerWeekInputBox.getAttribute("value"));
					if(value == minShifts) {
						SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Minimum Shifts' already updated with value:'"+ minShifts +"'.");
					}
					else {
						minShiftsPerWeekInputBox.clear();
						minShiftsPerWeekInputBox.sendKeys(String.valueOf(minShifts));
						if(isElementLoaded(confirmSaveButton, 10))
							click(confirmSaveButton);
						if(Integer.valueOf(minShiftsPerWeekInputBox.getAttribute("value")) == minShifts)
							SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Minimum Shifts' updated with value:'"+ minShifts +"'.");
						else
							SimpleUtils.fail("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Minimum Shifts' not updated with value:'"+ minShifts +"'.", true);
					}
				}
				else {
					SimpleUtils.fail("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Minimum Shifts' Input box not loaded.", true);
				}
			}	
			
			if(numShiftsPerWeekMaxInputFields.get(index).isDisplayed()) {
				WebElement maxShiftsPerWeekInputBox = numShiftsPerWeekMaxInputFields.get(index).findElement(
						By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
				if(isElementLoaded(maxShiftsPerWeekInputBox)) {
					int value = Integer.valueOf(maxShiftsPerWeekInputBox.getAttribute("value"));
					if(value == maxShifts) {
						SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Maximum Shifts' already updated with value:'"+ maxShifts +"'.");
					}
					else {
						maxShiftsPerWeekInputBox.clear();
						maxShiftsPerWeekInputBox.sendKeys(String.valueOf(maxShifts));
						if(isElementLoaded(confirmSaveButton, 10))
							click(confirmSaveButton);
						if(Integer.valueOf(maxShiftsPerWeekInputBox.getAttribute("value")) == maxShifts)
							SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Maximum Shifts' updated with value:'"+ maxShifts +"'.");
						else
							SimpleUtils.fail("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Maximum Shifts' not updated with value:'"+ maxShifts +"'.", true);
					}
				}
				else {
					SimpleUtils.fail("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Maximum Shifts' Input box not loaded.", true);
				}
			}	
			
			if(numShiftsPerWeekIdealInputFields.get(index).isDisplayed()) {
				WebElement idealShiftsPerWeekInputBox = numShiftsPerWeekIdealInputFields.get(index).findElement(
						By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
				if(isElementLoaded(idealShiftsPerWeekInputBox)) {
					int value = Integer.valueOf(idealShiftsPerWeekInputBox.getAttribute("value"));
					if(value == idealShifts) {
						SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Ideal Shifts' already updated with value:'"+ idealShifts +"'.");
					}
					else {
						idealShiftsPerWeekInputBox.clear();
						idealShiftsPerWeekInputBox.sendKeys(String.valueOf(idealShifts));
						if(isElementLoaded(confirmSaveButton, 10))
							click(confirmSaveButton);
						if(Integer.valueOf(idealShiftsPerWeekInputBox.getAttribute("value")) == idealShifts)
							SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Ideal Shifts' updated with value:'"+ idealShifts +"'.");
						else
							SimpleUtils.fail("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Ideal Shifts' not updated with value:'"+ idealShifts +"'.", true);
					}
				}
				else {
					SimpleUtils.fail("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Ideal Shifts' Input box not loaded.", true);
				}
			}	
		}
	}
	

	@Override
	public void updateSchedulingPolicyGroupsHoursPerShift(int minHoursPerShift, int maxHoursPerShift, int ideaHoursPerShift) throws Exception
	{
		for(int index = 0; index < minHoursPerShiftInputFields.size(); index++) {
			
			if(minHoursPerShiftInputFields.get(index).isDisplayed()) {
				WebElement minHoursPerShiftInputBox = minHoursPerShiftInputFields.get(index).findElement(
						By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
				if(isElementLoaded(minHoursPerShiftInputBox)) {
					int value = Integer.valueOf(minHoursPerShiftInputBox.getAttribute("value"));
					if(value == minHoursPerShift) {
						SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Minimum Hours Per Shifts' already updated with value:'"+ minHoursPerShift +"'.");
					}
					else {
						minHoursPerShiftInputBox.clear();
						minHoursPerShiftInputBox.sendKeys(String.valueOf(minHoursPerShift));
						if(isElementLoaded(confirmSaveButton, 10))
							click(confirmSaveButton);
						if(Integer.valueOf(minHoursPerShiftInputBox.getAttribute("value")) == minHoursPerShift)
							SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Minimum Hours Per Shifts' updated with value:'"+ minHoursPerShift +"'.");
						else
							SimpleUtils.fail("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Minimum Hours Per Shifts' not updated with value:'"+ minHoursPerShift +"'.", true);
					}
				}
				else {
					SimpleUtils.fail("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Minimum Hours Per Shifts' Input box not loaded.", true);
				}
			}	
			
			if(maxHoursPerShiftInputFields.get(index).isDisplayed()) {
				WebElement maxHoursPerShiftInputBox = maxHoursPerShiftInputFields.get(index).findElement(
						By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
				if(isElementLoaded(maxHoursPerShiftInputBox)) {
					int value = Integer.valueOf(maxHoursPerShiftInputBox.getAttribute("value"));
					if(value == maxHoursPerShift) {
						SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Maximum Hours Per Shifts' already updated with value:'"+ maxHoursPerShift +"'.");
					}
					else {
						maxHoursPerShiftInputBox.clear();
						maxHoursPerShiftInputBox.sendKeys(String.valueOf(maxHoursPerShift));
						if(isElementLoaded(confirmSaveButton, 10))
							click(confirmSaveButton);
						if(Integer.valueOf(maxHoursPerShiftInputBox.getAttribute("value")) == maxHoursPerShift)
							SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Maximum Hours Per Shifts' updated with value:'"+ maxHoursPerShift +"'.");
						else
							SimpleUtils.fail("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Maximum Hours Per Shifts' not updated with value:'"+ maxHoursPerShift +"'.", true);
					}
				}
				else {
					SimpleUtils.fail("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Maximum Hours Per Shifts' Input box not loaded.", true);
				}
			}	
			
			if(ideaHoursPerShiftInputFields.get(index).isDisplayed()) {
				WebElement ideaHoursPerShiftInputBox = ideaHoursPerShiftInputFields.get(index).findElement(
						By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
				if(isElementLoaded(ideaHoursPerShiftInputBox)) {
					int value = Integer.valueOf(ideaHoursPerShiftInputBox.getAttribute("value"));
					if(value == ideaHoursPerShift) {
						SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Ideal Hours Per Shifts' already updated with value:'"+ ideaHoursPerShift +"'.");
					}
					else {
						ideaHoursPerShiftInputBox.clear();
						ideaHoursPerShiftInputBox.sendKeys(String.valueOf(ideaHoursPerShift));
						if(isElementLoaded(confirmSaveButton, 10))
							click(confirmSaveButton);
						if(Integer.valueOf(ideaHoursPerShiftInputBox.getAttribute("value")) == ideaHoursPerShift)
							SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Ideal Hours Per Shifts' updated with value:'"+ ideaHoursPerShift +"'.");
						else
							SimpleUtils.fail("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Ideal Hours Per Shifts' not updated with value:'"+ ideaHoursPerShift +"'.", true);
					}
				}
				else {
					SimpleUtils.fail("Scheduling Policies: Scheduling Policy Groups Shifts Per Week 'Ideal Hours Per Shifts' Input box not loaded.", true);
				}
			}	
		}
	}
	

	@Override
	public void updateEnforceNewEmployeeCommittedAvailabilityWeeks(boolean isEmployeeCommittedAvailabilityActive, int committedHoursWeeks) throws Exception
	{
		if(isElementLoaded(schedulePolicyGroupSection)) {
			List<WebElement> employeeCommittedAvailabilitySwitchToggles = schedulePolicyGroupSection.findElements(
					By.cssSelector("lg-switch.lg-question-input__toggle"));
			for(WebElement employeeCommittedAvailabilitySwitchToggle : employeeCommittedAvailabilitySwitchToggles) {
				if(employeeCommittedAvailabilitySwitchToggle.isDisplayed()) {
					
					WebElement toggleCheckBox = employeeCommittedAvailabilitySwitchToggle.findElement(By.cssSelector("input[type=\"checkbox\"]"));
					boolean isSliderChecked = toggleCheckBox.getAttribute("class").contains("ng-not-empty");
					if(isEmployeeCommittedAvailabilityActive && isSliderChecked) {
						SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Enforce New Employee Committed Availability Toggle already 'Enabled'.");
					}
					else if(isEmployeeCommittedAvailabilityActive && ! isSliderChecked) {
						click(employeeCommittedAvailabilitySwitchToggle);
						if(toggleCheckBox.getAttribute("class").contains("ng-not-empty"))
							SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Enforce New Employee Committed Availability Toggle 'Enabled' successfully.");
					}
					else if(!isEmployeeCommittedAvailabilityActive && isSliderChecked) {
						click(employeeCommittedAvailabilitySwitchToggle);
						if(! toggleCheckBox.getAttribute("class").contains("ng-not-empty"))
							SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Enforce New Employee Committed Availability Toggle 'Disabled' successfully.");
					}
					else if(!isEmployeeCommittedAvailabilityActive && !isSliderChecked) {
						SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Enforce New Employee Committed Availability Toggle already 'Disabled'.");
					}
				}
			}
			
			if(isEmployeeCommittedAvailabilityActive) {
				for(WebElement committedHoursPeriodFiled : committedHoursPeriodFileds) {
					if(committedHoursPeriodFiled.isDisplayed()) {
						WebElement committedHoursPeriodInputBox = committedHoursPeriodFiled.findElement(
								By.cssSelector("input[ng-change=\"$ctrl.handleChange()\"]"));
						if(isElementLoaded(committedHoursPeriodInputBox)) {
							int value = Integer.valueOf(committedHoursPeriodInputBox.getAttribute("value"));
							if(value == committedHoursWeeks)
								SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Enforce New Employee Committed "
										+ "Availability Hours Week input filed already updated with value :'"+committedHoursWeeks+"'.");
							else {
								committedHoursPeriodInputBox.clear();
								committedHoursPeriodInputBox.sendKeys(String.valueOf(committedHoursWeeks));
								if(Integer.valueOf(committedHoursPeriodInputBox.getAttribute("value")) == committedHoursWeeks)
										SimpleUtils.pass("Scheduling Policies: Scheduling Policy Groups Enforce New Employee Committed "
												+ "Availability Hours Week input filed updated with value :'"+committedHoursWeeks+"'.");
							}
						}
					}
				}
			}
		}
	}


	@Override
	public void selectSchdulingPolicyGroupsTabByLabel(String tabLabel) throws Exception
	{
		boolean isTabSelected = false;
		if(schedulingPolicyGroupsTabs.size() > 0)
		{
			for(WebElement schedulingPolicyGroupsTab : schedulingPolicyGroupsTabs) {
				if(schedulingPolicyGroupsTab.getText().toLowerCase().contains(tabLabel.toLowerCase())) {
					click(schedulingPolicyGroupsTab);
					isTabSelected = true;
				}
			}
			if(isTabSelected)
				SimpleUtils.pass("Scheduling Policy Groups Tab '"+tabLabel+"' selected successfully.");
			else
				SimpleUtils.fail("Scheduling Policy Groups: Unable to select Tab '"+tabLabel+"'.", false);

		}
		else
			SimpleUtils.fail("Scheduling Policy Groups Tabs not loaded.", false);
	}
}
