package com.legion.pages;

public interface LocationSelectorPage {

	public Boolean isChangeLocationButtonLoaded() throws Exception;
	public void changeLocation(String locationName);
	public Boolean isLocationSelected(String locationName) ;
	public String getCurrentUserLocation() throws Exception;
	public Boolean isDistrictSelected(String districtName);
	public void changeDistrict(String districtName);
	public Boolean isChangeDistrictButtonLoaded() throws Exception;
}
