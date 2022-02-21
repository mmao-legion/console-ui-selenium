package com.legion.pages.OpsPortaPageFactories;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface LocationsPage {

    public void clickModelSwitchIconInDashboardPage(String value);
    public void setLaborBudgetLevel(boolean isCentral,String level);

    public boolean isOpsPortalPageLoaded() throws Exception;
    public void locationSourceTypeCheck() throws Exception;

    public void clickOnLocationsTab() throws Exception;

    public void validateItemsInLocations() throws Exception;

    public void goToSubLocationsInLocationsPage() throws Exception;

    public void addNewRegularLocationWithMandatoryFields(String locationName)throws Exception;

    public boolean searchNewLocation(String locationName);

    public void addNewRegularLocationWithAllFields(String locationName, String searchCharactor, int index) throws Exception;

    public void addNewMockLocationWithAllFields(String searchCharactor, int index) throws Exception;
    public void addNewRegularLocationWithDate(String locaname,String searchCharactor, int index,int fromToday) throws Exception;
    public void locationPageCommonFeatureCheck() throws Exception;
    public void checkEveryLocationTemplateConfig(String locName) throws Exception;

    public void verifyImportLocationDistrict();

//    public void disableSwitch(String switchName,String enterpriseName);

    public void verifyThereIsNoLocationGroupField();

    public void addNewNSOLocation(String locationName, String searchCharactor, int index) throws Exception;

    public String disableLocation(String autoCreate) throws Exception;
    public String searchLocationAndGetStatus(String locaname) throws Exception;

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

    public void changeLGToMSOrP2P(String locationName, String value) throws Exception;

    public boolean verifyUpperFieldListShowWellOrNot() throws Exception;

    public void verifyBackBtnFunction() throws Exception;

    public void verifyPaginationFunctionInLocation() throws Exception;

    public void verifySearchUpperFieldsFunction(String[] searchInfo) throws Exception;

    public void addNewDistrict(String districtName, String districtId,String searchChara,int index) throws Exception;

    public String updateUpperfield(String districtName, String districtId, String searchChara, int index, String level) throws Exception;

    public ArrayList<HashMap<String, String>> getUpperfieldsInfo(String districtName);

    public void addNewDistrictWithoutLocation(String districtName, String districtId) throws Exception;

    public void disableEnableUpperfield(String districtName, String action) throws Exception;

    public HashMap<String, String> getEnterpriseLogoAndDefaultLocationInfo();

    public void verifyTheFiledOfLocationSetting() throws Exception;

    public void iCanSeeDynamicGroupItemInLocationsTab();

    public void goToDynamicGroup();

    public void clickOnAddBtnForSharingDynamicLocationGroup() throws Exception;

    public String addWorkforceSharingDGWithOneCriteria(String groupName, String description, String criteria) throws Exception;

    public void iCanDeleteExistingWFSDG();

    public String updateWFSDynamicGroup(String groupName, String criteriaUpdate) throws Exception;

    public void verifyPageNavigationFunctionInDistrict() throws Exception;

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

    public void verifyBackBtnInCreateNewUpperfieldPage();

    public void verifyCancelBtnInCreateNewUpperfieldPage();

    public void addNewUpperfieldsWithRandomLevel(String upperfieldsName, String upperfieldsId, String searchChara, int index) throws Exception;


//
//    public ArrayList<HashMap<String, String>> getWFSGroupForm();

    public void verifyDefaultOrganizationHierarchy() throws Exception;

    public void addOrganizationHierarchy(List<String> hierarchyNames) throws Exception;

    public void deleteOrganizationHierarchy(List<String> hierarchyNames) throws Exception;

    public void updateOrganizationHierarchyDisplayName() throws Exception;

    public void updateEnableUpperfieldViewOfHierarchy() throws Exception;

    public void abnormalCaseOfEmptyDisplayNameForHierarchy() throws Exception;

    public void abnormalCaseOfLongDisplayNameForHierarchy() throws Exception;
    public  HashMap<String, Integer> getUpperfieldsSmartCardInfo();

    public int getSearchResultNum() throws Exception;

    public void cancelCreatingUpperfield(String level, String upperfieldsName, String upperfieldsId) throws Exception;

    public void clickOnLocationInLocationResult(String location) throws Exception;

    public void clickOnConfigurationTabOfLocation() throws Exception;

    public HashMap<String,String> getTemplateTypeAndNameFromLocation() throws Exception;

    public void enableDaypart(String dayPart) throws Exception;
    public void goToLocationDetailsPage(String locationName) throws Exception;

    public void goToConfigurationTabInLocationLevel();

    public void canGoToAssignmentRoleViaTemNameInLocationLevel();

    public List<HashMap<String, String>> getAssignmentRolesInLocationLevel();

    public void canGoToOperationHoursViaTemNameInLocationLevel();

    public String getOHTemplateValueInLocationLevel();

    public void canGoToSchedulingRulesViaTemNameInLocationLevel();

    public List<HashMap<String,String>> getScheRulesTemplateValueInLocationLevel();

    public void canGoToScheduleCollaborationViaTemNameInLocationLevel();

    public String getScheCollTemplateValueInLocationLevel();

    public void canGoToTAViaTemNameInLocationLevel();

    public String getTATemplateValueInLocationLevel();

    public void canGoToSchedulingPoliciesViaTemNameInLocationLevel();

    public String getSchedulingPoliciesTemplateValueInLocationLevel();

    public void canGoToComplianceViaTemNameInLocationLevel();

    public String getComplianceTemplateValueInLocationLevel();

    public void canGoToLaborModelViaTemNameInLocationLevel();

    public List<HashMap<String, String>> getLaborModelInLocationLevel();

    public void backToConfigurationTabInLocationLevel();

    public List<HashMap<String, String>> getLocationTemplateInfoInLocationLevel();

    public void editLocationBtnIsClickableInLocationDetails();

    public void actionsForEachTypeOfTemplate(String template_type, String action);

    public void okBtnIsClickable() throws Exception;

    public void goToScheduleRulesListAtLocationLevel(String workRole) throws Exception;

    public void editBtnIsClickableInBusinessHours();

    public void selectDayInWorkingHoursPopUpWin(int i);

    public void clickSaveBtnInWorkingHoursPopUpWin();

//    public void moveSliderAtSomePoint(int moveCount, String value) throws Exception;

    public HashMap<String, List<String>> getValueAndDescriptionForEachAttributeAtLocationLevel() throws Exception;

    public void clickOnSaveButton() throws Exception;

    public void updateLocationLevelExternalAttributes(String attributeName,String attributeValue,String attributeDescription) throws Exception;

    public void clickOnImportBtn();

    public void cancelBtnOnImportExportPopUpWinsIsClickable();

    public void clickOnExportBtn();

    public void verifyTitleForWorkforceSharingLocationGroup() throws Exception;

    public void clickOnCancelBtnOnSharingDynamicLocationGroupWindow() throws Exception;
    public void checkLocationGroupSetting(String locationName) throws Exception;
    public void checkLocationNavigation(String locationName) throws Exception;
}
