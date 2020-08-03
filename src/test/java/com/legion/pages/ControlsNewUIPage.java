package com.legion.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.WebElement;

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

	public String getSchedulingPoliciesActiveLocation() throws Exception;

	public void updateSchedulePublishWindow(String publishWindowAdvanceWeeks, boolean preserveSetting, boolean overwriteSetting) throws Exception;

	public void updateSchedulePlanningWindow(String planningWindowAdvanceWeeks , boolean preserveSetting, boolean overwriteSetting) throws Exception;

	public String getSchedulePlanningWindowWeeks() throws Exception;

	public void updateSchedulingPoliciesFirstDayOfWeek(String day) throws Exception;

	public String getSchedulingPoliciesFirstDayOfWeek() throws Exception;

	public void updateEarliestOpeningTime(String openingTime) throws Exception;

	public String getEarliestOpeningTime() throws Exception;

	public void updateLatestClosingTime(String closingTime) throws Exception;

	public String getLatestClosingTime() throws Exception;

	public void updateAdvanceScheduleDaysToBeFinalize(String advanceDays) throws Exception;

	public void updateScheduleBufferHoursBefore(String beforeBufferCount) throws Exception;

	public void updateScheduleBufferHoursAfter(String afterBufferCount) throws Exception;

	public void updateMinimumShiftLengthHour(String shiftLengthHour) throws Exception;

	public float getMinimumShiftLengthHour() throws Exception;

	public void updateMaximumShiftLengthHour(String shiftLengthHour) throws Exception;

	public float getMaximumShiftLengthHour() throws Exception;

	public void updateMaximumNumWeekDaysToAutoSchedule(String maxDaysLabel) throws Exception;

	public String getMaximumNumWeekDaysToAutoSchedule() throws Exception;

	public void updateDisplayShiftBreakIcons(String shiftBreakIcons) throws Exception;

	public String getDisplayShiftBreakIcons() throws Exception;

	public void updateApplyLaborBudgetToSchedules(String isLaborBudgetToApply) throws Exception;

	public void updateScheduleBudgetType(String budgetType) throws Exception;

	public void updateTeamAvailabilityLockMode(String availabilityLockModeLabel) throws Exception;

	public void updateScheduleUnavailableHourOfWorkers(String unavailableWorkersHour) throws Exception;

	public void updateAvailabilityToleranceField(String availabilityToleranceMinutes) throws Exception;

	public void updateCanWorkerRequestTimeOff(String canWorkerRequestTimeOffValue) throws Exception;

	public void clickOnSchedulingPoliciesTimeOffAdvanceBtn() throws Exception;

	public void updateMaxEmployeeCanRequestForTimeOffOnSameDay(String maxWorkersTimeOfPerDayCount) throws Exception;

	public void updateNoticePeriodToRequestTimeOff(String noticePeriodToRequestTimeOff) throws Exception;

	public void updateShowTimeOffReasons(String isShowTimeOffReasons) throws Exception;

	public void updateSchedulingPolicyGroupsHoursPerWeek(int minHours, int maxHours, int idealHour) throws Exception;

	public void updateSchedulingPolicyGroupsShiftsPerWeek(int minShifts, int maxShifts, int idealShifts) throws Exception;

	public void updateSchedulingPolicyGroupsHoursPerShift(int minHoursPerShift, int maxHoursPerShift, int ideaHoursPerShift)
			throws Exception;

	public void updateEnforceNewEmployeeCommittedAvailabilityWeeks(boolean isEmployeeCommittedAvailabilityActive
			, int committedHoursWeeks) throws Exception;

	public void selectSchdulingPolicyGroupsTabByLabel(String tabLabel) throws Exception;

	public HashMap<String, ArrayList<String>> getLocationInformationEditableOrNonEditableFields() throws Exception;

	public boolean isControlsConsoleMenuAvailable() throws Exception;

	public HashMap<String, ArrayList<String>> getSchedulingPoliciesSchedulesSectionEditableOrNonEditableFields() throws Exception;

	public HashMap<String, ArrayList<String>> getSchedulingPoliciesShiftsSectionEditableOrNonEditableFields() throws Exception;

	public HashMap<String, ArrayList<String>> getSchedulingPoliciesBudgetSectionEditableOrNonEditableFields() throws Exception;

	public HashMap<String, ArrayList<String>> getSchedulingPoliciesTeamAvailabilityManagementSectionEditableOrNonEditableFields()
			throws Exception;

	public HashMap<String, ArrayList<String>> getSchedulingPoliciesTimeOffSectionEditableOrNonEditableFields() throws Exception;

	public HashMap<String, ArrayList<String>> getSchedulingPoliciesSchedulingPolicyGroupsSectionEditableOrNonEditableFields()
			throws Exception;

	public HashMap<String, ArrayList<String>> getScheduleCollaborationEditableOrNonEditableFields() throws Exception;

	public void clickOnScheduleCollaborationOpenShiftAdvanceBtn() throws Exception;

	public HashMap<String, ArrayList<String>> getComplianceEditableOrNonEditableFields() throws Exception;

	public void selectUsersAndRolesSubTabByLabel(String label) throws Exception;

	public HashMap<String, ArrayList<String>> getUsersAndRolesAddNewUserPageEditableOrNonEditableFields() throws Exception;

	public HashMap<String, ArrayList<String>> getUsersAndRolesEditUserPageEditableOrNonEditableFields(String userFirstName) throws Exception;

	public HashMap<String, ArrayList<String>> getUsersAndRolesUpdateEmployeeJobTitleEditableOrNonEditableFields(
			String employeeJobTitle) throws Exception;

	public HashMap<String, ArrayList<String>> getUsersAndRolesCreateNewEmployeeJobTitleEditableOrNonEditableFields(
			String employeeJobTitle, String newEmployeeJobTitleRole) throws Exception;

	public HashMap<String, ArrayList<String>> getUsersAndRolesUpdateBadgesEditableOrNonEditableFields(String BadgesLabel)
			throws Exception;

	public HashMap<String, ArrayList<String>> getUsersAndRolesNewBadgeEditableOrNonEditableFields() throws Exception;

	public void selectTasksAndWorkRolesSubTabByLabel(String label) throws Exception;

	public List<WebElement> getTasksAndWorkRolesSectionAllWorkRolesList() throws Exception;

	public HashMap<String, ArrayList<String>> getTasksAndWorkRolesEditWorkRolePropertiesEditableOrNonEditableFields() throws Exception;

	public boolean isWorkRoleDetailPageSubSectionsExpandFunctionalityWorking() throws Exception;

	public boolean isLaborCalculationTabLoaded() throws Exception;

	public HashMap<String, ArrayList<String>> getTasksAndWorkRolesLaborCalculatorTabEditableOrNonEditableFields() throws Exception;

	public HashMap<String, ArrayList<String>> getTasksAndWorkRolesAddWorkRolePageEditableOrNonEditableFields()
			throws Exception;

	public HashMap<String, ArrayList<String>> verifyUpdateControlsRegularHoursPopupEditableOrNonEditableFields() throws Exception;

	public HashMap<String, ArrayList<String>> verifyUpdateControlsHolidayHoursPopupEditableOrNonEditableFields() throws Exception;

	public void verifyAllLocations(String txt) throws Exception;
    public void verifySearchLocations(String searchText) throws Exception;
	public boolean isControlsCompanyProfileCard() throws Exception;
	public boolean isControlsSchedulingPoliciesCard() throws Exception;
	public boolean isControlsSchedulingCollaborationCard() throws Exception;
	public boolean isControlsComplianceCard() throws Exception;
	public boolean isControlsUsersAndRolesCard() throws Exception;
	public boolean isControlsTaskAndWorkRolesCard() throws Exception;
	public boolean isControlsWorkingHoursCard() throws Exception;
	public boolean isControlsLocationsCard() throws Exception;
	public void verifySchedulePublishWindow(String publishWindowAdvanceWeeks, String publishWindowQuestion, String userCredential) throws Exception ;
	public void getSchedulePublishWindowWeeksDropDownValues() throws Exception;
	public List<String> getSchedulePublishWindowValueAtDifferentLocations(boolean schedulePublishWindowWeeks) throws Exception;
	public void getSchedulePlanningWindowWeeksDropDownValues() throws Exception;
	public void verifySchedulePublishWindowUpdationValues(String publishWindowAdvanceWeeks, List<String>
			selectionOptionLabelAfterUpdation) throws Exception;
	public void verifySchedulePlanningWindow(String planningWindowAdvanceWeeks,
											 String planningWindowQuestion, String userCredential) throws Exception;
	public void verifySchedulePlanningWindowUpdationValues(String planningWindowAdvanceWeeks, List<String> selectionOptionLabelAfterUpdation)
			throws Exception;
	public String getTimeSheetApprovalSelectedOption(boolean byManager) throws Exception;

	public String getTimeZoneFromLocationDetailsPage() throws Exception;

	public void updateScheduleScore(String budget_score, String coverage_scores_regular_hours, String coverage_scores_peak_hours, String employee_match_score, String compliance_score, String how_to_measure_coverage_relative_to_guidance_budget) throws Exception;

	public boolean isScheduleScoreUpdated(String budget_score, String coverage_scores_regular_hours, String coverage_scores_peak_hours, String employee_match_score, String compliance_score, String how_to_measure_coverage_relative_to_guidance_budget);

	public String getOnBoardOptionFromScheduleCollaboration() throws Exception;

	public void setOnBoardOptionAsEmailWhileInviting() throws Exception;

	public void clickOnWorkHoursTypeByText(String title) throws Exception;

	public LinkedHashMap<String, List<String>> getRegularWorkingHours() throws Exception;

	public void enableOverRideAssignmentRuleAsYes() throws Exception;


	public HashMap<String, Integer> getOvertimePayDataFromControls();

	public HashMap<String, Integer> getMealBreakDataFromControls();

	public boolean isCompliancePageLoaded() throws Exception;

	public String getIsApprovalByManagerRequiredWhenEmployeeClaimsOpenShiftSelectedOption() throws Exception;

	public void updateOpenShiftApprovedByManagerOption(String option)throws Exception;
	public void updateAvailabilityManagementIsApprovalRequired(String option) throws Exception;
	public void updateSwapAndCoverRequestIsApprovalRequired(String option) throws Exception;
	public void searchUserByFirstName(String userFirstName);
}
