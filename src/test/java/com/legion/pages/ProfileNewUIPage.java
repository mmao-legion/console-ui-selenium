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

}
