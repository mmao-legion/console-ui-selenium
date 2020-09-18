package com.legion.pages;

import java.util.HashMap;

public interface LocationsPage {

    public void clickModelSwitchIconInDashboardPage(String value);

    public boolean isOpsPortalPageLoaded() throws Exception;

    public void clickOnLocationsTab() throws Exception;

    public void validateItemsInLocations() throws Exception;

    public void goToSubLocationsInLocationsPage() throws Exception;

    public void addNewRegularLocationWithMandatoryFields(String locationName)throws Exception;

    public boolean searchNewLocation(String locationName);

    public void addNewRegularLocationWithAllFields(String locationName, String searchCharactor, int index) throws Exception;

    public void addNewMockLocationWithAllFields(String locationName, String searchCharactor, int index) throws Exception;

    public void verifyImportLocationDistrict();

//    public void disableSwitch(String switchName,String enterpriseName);

    public void verifyThereIsNoLocationGroupField();

    public void addNewNSOLocation(String locationName, String searchCharactor, int index) throws Exception;

    public String disableLocation(String autoCreate) throws Exception;

    public void verifyExportAllLocationDistrict();

    public void verifyExportSpecificLocationDistrict(String searchCharactor, int index);

    public void enableLocation(String disableLocationName) throws Exception;

    public void updateLocation(String locationName) throws Exception;

    public boolean verifyUpdateLocationResult(String locationName) throws Exception;

    public HashMap<String,String> getLocationInfo(String locationName);

    public void addChildLocation(String childlocationName, String locationName, String searchCharactor, int index, String childRelationship) throws Exception;

    public void addParentLocation(String locationName, String searchCharactor, int index, String parentRelationship, String value) throws Exception;

    public boolean verifyLGIconShowWellOrNot(String locationName, int childLocationNum);;

}
