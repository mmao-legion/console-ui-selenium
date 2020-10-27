package com.legion.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public ArrayList<HashMap<String, String>> getLocationInfo(String locationName);

    public void addChildLocation(String locationType, String childlocationName, String locationName, String searchCharactor, int index, String childRelationship) throws Exception;

    public void addParentLocation(String locationType, String locationName, String searchCharactor, int index, String parentRelationship, String value) throws Exception;

    public boolean verifyLGIconShowWellOrNot(String locationName, int childLocationNum);;

    public void changeOneLocationToNone(String locationToNone) throws Exception;

    public void updateChangePTPLocationToNone(String childLocationWhichRemoved) throws Exception;

    public void searchLocation(String lgmsLocationName) throws Exception;

    public void addParentLocationForNsoType(String locationType, String locationName, String searchCharactor, int index, String parentRelationship, String value) throws Exception;

    public void addChildLocationForNSO(String locationType, String childLocationName, String locationName, String searchCharactor, int index, String childRelationship) throws Exception;

    public void checkThereIsNoLocationGroupSettingFieldWhenLocationTypeIsMock() throws Exception;

    public void changeOneLocationToParent(String locationName, String locationRelationship, String locationGroupType) throws Exception;

    public void changeOneLocationToChild(String locationName, String locationRelationship, String parentLocation) throws Exception;

    public void updateParentLocationDistrict(String searchCharacter, int index);

    public void disableEnableLocation(String locationName, String action) throws Exception;

    public void goToSubDistrictsInLocationsPage() throws Exception;

    public void searchDistrict(String searchInputText) throws Exception;

    public int getTotalEnabledDistrictsCount() throws Exception;

    public List<Integer> getSearchDistrictsResultsCount(String searchInputText) throws Exception;

    public void validateTheAddDistrictBtn() throws Exception;

    public void clickModelSwitchIconInOpsPage();

    public List<String> getLocationsInDistrict(String searchInputText) throws Exception;
}
