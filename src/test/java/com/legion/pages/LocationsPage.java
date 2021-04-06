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

    public void goToUpperFieldsPage() throws Exception;

    public void searchUpperFields(String searchInputText) throws Exception;

    public int getTotalEnabledDistrictsCount() throws Exception;

    public List<Integer> getSearchDistrictsResultsCount(String searchInputText) throws Exception;

    public void validateTheAddDistrictBtn() throws Exception;

    public void clickModelSwitchIconInOpsPage();

    public List<String> getLocationsInDistrict(String searchInputText) throws Exception;

    public boolean isItMSLG();

    public void changeLGToMSOrP2P(String value) throws Exception;

    public boolean verifyUpperFieldListShowWellOrNot() throws Exception;

    public void verifyBackBtnFunction() throws Exception;

    public void verifyPaginationFunctionInLocation() throws Exception;

    public void verifySearchUpperFieldsFunction(String[] searchInfo) throws Exception;

    public void addNewDistrict(String districtName, String districtId,String searchChara,int index) throws Exception;

    public void updateDistrict(String districtName, String districtId, String searchChara, int index);

    public ArrayList<HashMap<String, String>> getDistrictInfo(String districtName);

    public void addNewDistrictWithoutLocation(String districtName, String districtId) throws Exception;

    public void disableEnableDistrict(String districtName, String action) throws Exception;

    public HashMap<String, String> getEnterpriseLogoAndDefaultLocationInfo();

    public void verifyTheFiledOfLocationSetting() throws Exception;

    public void iCanSeeDynamicGroupItemInLocationsTab();

    public void goToDynamicGroup();

    public String addWorkforceSharingDGWithOneCriteria(String groupName, String description, String criteria) throws Exception;

    public void iCanDeleteExistingWFSDG();

    public String updateWFSDynamicGroup(String groupName, String criteriaUpdate) throws Exception;

    public void verifyPaginationFunctionInDistrict() throws Exception;

    public List<String> getAllDayPartsFromGlobalConfiguration() throws Exception;

    public void goToGlobalConfigurationInLocations() throws Exception;

    public void searchWFSDynamicGroup(String searchText);

    public void searchClockInDynamicGroup(String searchText) throws Exception;

    public String addClockInDGWithOneCriteria(String groupName, String description, String criteria) throws Exception;

    public String updateClockInDynamicGroup(String groupNameForCloIn, String criteriaUpdate) throws Exception;

    public void iCanDeleteExistingClockInDG();

    public List<String> getClockInGroupFromGlobalConfig();

    public void verifyCreateExistingDGAndGroupNameIsNull(String s) throws Exception;

    public List<String> getWFSGroupFromGlobalConfig();

    public void addNewUpperfieldsWithoutParentAndChild(String upperfieldsName, String upperfieldsId, String searchChara, int index, ArrayList<HashMap<String, String>> organizationHierarchyInfo) throws Exception;

    public ArrayList<HashMap<String, String>> getOrganizationHierarchyInfo();

    public void goBackToLocationsTab();
//
//    public ArrayList<HashMap<String, String>> getWFSGroupForm();

    public void verifyDefaultOrganizationHierarchy() throws Exception;

    public void addOrganizatioHierarchy(List<String> hierarchyNames) throws Exception;

    public void deleteOrganizatioHierarchy() throws Exception;

    public void updateOrganizatioHierarchyDisplayName() throws Exception;

    public void updateEnableUpperfieldViewOfHierarchy() throws Exception;
}
