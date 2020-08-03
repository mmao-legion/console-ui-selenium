package com.legion.pages;

public interface LocationsPage {

    public void clickModelSwitchIconInDashboardPage();

    public boolean isOpsPortalPageLoaded() throws Exception;

    public void clickOnLocationsTab() throws Exception;

    public void validateItemsInLocations() throws Exception;

    public void goToSubLocationsInLocationsPage() throws Exception;

    public void addNewRegularLocationWithMandatoryFields(String locationName)throws Exception;

    public void searchNewLocation(String locationName);


}
