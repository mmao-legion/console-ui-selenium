package com.legion.pages;

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

	
}
