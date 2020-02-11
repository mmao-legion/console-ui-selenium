package com.legion.pages;

import java.util.List;

public interface LocationSelectorPage {

	public Boolean isChangeLocationButtonLoaded() throws Exception;
	public void changeLocation(String locationName);
	public Boolean isLocationSelected(String locationName) ;
	public String getCurrentUserLocation() throws Exception;
	public Boolean isDistrictSelected(String districtName);
	public void changeDistrict(String districtName);
	public Boolean isChangeDistrictButtonLoaded() throws Exception;
	public void verifyTheDisplayLocationWithSelectedLocationConsistent() throws Exception;
	public void verifyClickChangeLocationButton() throws Exception;
	public void verifyTheContentOfDetailLocations() throws Exception;
	public void verifyTheFunctionOfSearchTextBox(List<String> testStrings) throws Exception;
}
