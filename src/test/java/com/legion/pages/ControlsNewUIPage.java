package com.legion.pages;

import java.util.HashMap;

public interface ControlsNewUIPage {

	void clickOnControlsConsoleMenu() throws Exception;

	public boolean isControlsPageLoaded()  throws Exception;

	public void clickOnGlobalLocationButton() throws Exception;

	public void clickOnControlsCompanyProfileCard() throws Exception;

	public void updateUserLocationProfile(String companyName, String businessAddress, String city, String state,
			String country, String zipCode, String timeZone, String website, String firstName, String lastName,
			String email, String phone) throws Exception;

	public boolean isUserLocationProfileUpdated(String companyName, String businessAddress, String city, String state,
			String country, String zipCode, String timeZone, String website, String firstName, String lastName,
			String email, String phone) throws Exception;

	public void clickOnControlsWorkingHoursCard() throws Exception;

	public void updateControlsRegularHours(String isStoreClosed, String openingHours, String closingHours, String day) throws Exception;

	public void clickOnSaveRegularHoursBtn() throws Exception;

	public void clickOnControlsSchedulingPolicies() throws Exception;

	public boolean isBudgetSmartcardEnabled() throws Exception;

	public void enableDisableBudgetSmartcard(boolean enable) throws Exception;

	public String getAdvanceScheduleWeekCountToCreate() throws Exception;

	public void updateAdvanceScheduleWeekCountToCreate(String scheduleWeekCoundToCreate) throws Exception;

	public HashMap<String, Integer> getScheduleBufferHours() throws Exception;

	public void clickOnControlsLocationProfileSection() throws Exception;
	public void clickOnControlsScheduleCollaborationSection() throws Exception;
	public void clickOnControlsComplianceSection() throws Exception;
	public void clickOnControlsUsersAndRolesSection() throws Exception;
	public void clickOnControlsTasksAndWorkRolesSection() throws Exception;
	
	public boolean isControlsLocationProfileLoaded() throws Exception;
	public boolean isControlsScheduleCollaborationLoaded() throws Exception;
	public boolean isControlsComplianceLoaded() throws Exception;
	public boolean isControlsUsersAndRolesLoaded() throws Exception;
	public boolean isControlsTasksAndWorkRolesLoaded() throws Exception;
	public boolean isControlsSchedulingPoliciesLoaded() throws Exception;
	public boolean isControlsWorkingHoursLoaded() throws Exception;

	public void clickOnControlsTimeAndAttendanceCard() throws Exception;

	public void clickOnControlsTimeAndAttendanceAdvanceBtn() throws Exception;

	public void selectTimeSheetExportFormatByLabel(String optionLabel) throws Exception;

	public void clickOnSchedulingPoliciesShiftAdvanceBtn() throws Exception;

	public void selectSchedulingPoliciesShiftIntervalByLabel(String intervalTimeLabel) throws Exception;

	public void clickOnSchedulingPoliciesSchedulesAdvanceBtn() throws Exception;

	public String getSchedulePublishWindowWeeks() throws Exception;

	public int getAdvanceScheduleDaysCountToBeFinalize() throws Exception;


	
}
