package com.legion.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public void selectCurrentUpperFieldAgain(String upperFieldType) throws Exception;
	public void isDMView() throws Exception;
	public void isSMView() throws Exception;
	public List<Integer> searchDistrict(String searchInputText) throws Exception;
	public void verifyClickChangeDistrictButton() throws Exception;
	public List<String> searchLocation(String searchInputText) throws Exception;
	public void selectLocationByIndex(int i) throws Exception;
	public String getLocationNameFromDashboard() throws Exception;
	public void verifyTheDisplayDistrictWithSelectedDistrictConsistent(String districtName) throws Exception;
	public void reSelectDistrict(String districtName) throws Exception;
	public void changeAnotherDistrict() throws Exception;
	public void reSelectDistrictInDMView(String districtName) throws Exception;
	public void changeAnotherDistrictInDMView() throws Exception;
	public void changeUpperFields(String upperFields) throws Exception;
	public void changeUpperFieldsByName(String upperFieldType, String upperFieldName) throws Exception;
	public void verifyDefaultLevelForBUOrAdmin();
	public void searchSpecificBUAndNavigateTo(String buText);
	public void searchSpecificRegionAndNavigateTo(String regionText) throws Exception;
	public void searchSpecificUpperFieldAndNavigateTo(String districtText) throws Exception;
//	public void searchSpecificLocationAndNavigateTo(String locationName) throws Exception;
	public void changeLocationDirect(String locationName);
	public Map<String, String> getSelectedUpperFields () throws Exception;
	public void changeUpperFieldDirect(String upperFieldType, String upperFieldName) throws Exception;
	public boolean isNoDataToShowPageLoaded() throws Exception;
	public boolean isUpperFieldNavigationLoaded() throws Exception;
	public Boolean verifyMagnifyGlassIconShowOrNot();
	public List<String> getRecentlyViewedInfo();
	public void changeUpperFieldsFromResentViewList(int index);
	public void changeUpperFieldsByMagnifyGlassIcon(String upperfiledNavigaTo);
	public boolean verifyHQViewShowOrNot();
	public List<String> getConsoleTabs();
	public boolean isCurrentPageEmptyInHQView() throws Exception;
	public void verifyGreyOutPageInHQView();
	public List<String> getNavigatorValue();
	public void refreshTheBrowser();
}
