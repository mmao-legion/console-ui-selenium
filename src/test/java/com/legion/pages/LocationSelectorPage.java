package com.legion.pages;

public interface LocationSelectorPage {

	public Boolean isChangeLocationButtonLoaded() throws Exception;
	public void changeLocation(String locationName) throws Exception;
	public Boolean isLocationSelected(String locationName) throws Exception;
}
