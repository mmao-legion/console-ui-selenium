package com.legion.pages;

public interface ProfileNewUIPage {

	public void clickOnProfileConsoleMenu() throws Exception;

	public boolean isProfilePageLoaded() throws Exception;

	public void selectProfilePageSubSectionByLabel(String profilePageSubSectionLabel) throws Exception;

	public void clickOnCreateTimeOffBtn() throws Exception;

	public void selectTimeOffDuration(String startDate, String endDate) throws Exception;

	public void updateTimeOffExplanation(String explanationText) throws Exception;

	public void selectTimeOffReason(String reasonLabel) throws Exception;

	public void createNewTimeOffRequest(String timeOffReasonLabel, String timeOffExplanationText,
			String timeOffStartDate, String timeOffEndDate) throws Exception;

	public void clickOnSaveTimeOffRequestBtn() throws Exception;

	public String getTimeOffRequestStatus(String timeOffReasonLabel, String timeOffExplanationText,
			String timeOffStartDate, String timeOffEndDate) throws Exception;

	public boolean isEditingProfileSectionLoaded() throws Exception;

	public boolean isPersonalDetailsSectionLoaded() throws Exception;

	public boolean isChangePasswordButtonLoaded() throws Exception;

	public boolean isEngagementDetrailsSectionLoaded() throws Exception;

	public boolean isProfileBadgesSectionLoaded() throws Exception;

	public String getProfilePageActiveTabLabel() throws Exception;

	public boolean isShiftPreferenceSectionLoaded() throws Exception;

	public boolean isMyAvailabilitySectionLoaded() throws Exception;

	public boolean isCreateTimeOffButtonLoaded() throws Exception;

	public boolean isTimeOffPendingBlockLoaded() throws Exception;

	public boolean isTimeOffApprovedBlockLoaded() throws Exception;

	public boolean isTimeOffRejectedBlockLoaded() throws Exception;

	public boolean isTimeOffRequestsSectionLoaded() throws Exception;

	public String getProfilePageActiveLocation() throws Exception;

	public int getAllTimeOffRequestCount();

}
