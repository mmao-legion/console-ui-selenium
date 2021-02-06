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
	public void changeDistrictDirect() throws Exception;
	public void isDMView() throws Exception;
	public void isSMView() throws Exception;
	public List<Integer> searchDistrict(String searchInputText) throws Exception;
	public void verifyClickChangeDistrictButton() throws Exception;
	public List<String> searchLocation(String searchInputText) throws Exception;
	public void selectLocationByIndex(int i) throws Exception;
}
