package com.legion.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;

public interface SchedulePage {
	void clickOnScheduleConsoleMenuItem();
	void goToSchedulePage() throws Exception;
	boolean isSchedulePage() throws Exception;
	Boolean varifyActivatedSubTab(String SubTabText) throws Exception;
	void goToSchedule() throws Exception;
	void goToProjectedSales() throws Exception;
	void goToStaffingGuidance() throws Exception;
	boolean isSchedule() throws Exception;
	void clickOnWeekView() throws Exception;
	void clickOnDayView() throws Exception;
	HashMap<String, Float> getScheduleLabelHoursAndWages() throws Exception;
	List<HashMap<String, Float>> getScheduleLabelHoursAndWagesDataForEveryDayInCurrentWeek() throws Exception;
	void clickOnScheduleSubTab(String subTabString) throws Exception;
	void navigateWeekViewOrDayViewToPastOrFuture(String nextWeekViewOrPreviousWeekView, int weekCount);
	Boolean isWeekGenerated() throws Exception;
	Boolean isWeekPublished() throws Exception;
	void generateSchedule() throws Exception;
	String getScheduleWeekStartDayMonthDate();
	void clickOnEditButton() throws Exception;
	void clickImmediateNextToCurrentActiveWeekInDayPicker() throws Exception;
	Boolean isAddNewDayViewShiftButtonLoaded() throws Exception;
	void clickOnCancelButtonOnEditMode() throws Exception;
	Boolean isGenerateButtonLoaded() throws Exception;
	String getActiveWeekDayMonthAndDateForEachDay() throws Exception;
	Boolean validateScheduleActiveWeekWithOverviewCalendarWeek(String overviewCalendarWeekDate, String overviewCalendarWeekDays, String scheduleActiveWeekDuration);
	boolean isCurrentScheduleWeekPublished();
	void validatingRefreshButtononPublishedSchedule() throws Exception;
	void isGenerateScheduleButton() throws Exception;
	void validatingScheduleRefreshButton() throws Exception;
	void clickOnSchedulePublishButton() throws Exception;
	void navigateDayViewToPast(String nextWeekViewOrPreviousWeekView, int weekCount) throws Exception;
	String clickNewDayViewShiftButtonLoaded() throws Exception;
	void customizeNewShiftPage() throws Exception;
	void compareCustomizeStartDay(String textStartDay) throws Exception;
	void moveSliderAtSomePoint(String shiftTime, int shiftStartingCount, String startingPoint) throws Exception;
	HashMap<String, String> calculateHourDifference() throws Exception;
	void selectWorkRole(String workRoles) throws Exception;
	void clickRadioBtnStaffingOption(String staffingOption) throws Exception;
	void clickOnCreateOrNextBtn() throws Exception;
	HashMap<List<String>,List<String>> calculateTeamCount()throws Exception;
	List<String> calculatePreviousTeamCount(
			HashMap<String, String> previousTeamCount, HashMap<List<String>, List<String>>
			gridDayHourPrevTeamCount)throws Exception;
	List<String> calculateCurrentTeamCount(HashMap<String, String> shiftTiming)throws Exception;
	void clickSaveBtn() throws Exception;
	void clickOnVersionSaveBtn() throws Exception;
	void clickOnPostSaveBtn() throws Exception;
    void filterScheduleByWorkRoleAndShiftType(boolean isWeekView);
    void selectGroupByFilter(String optionVisibleText);
    String getActiveWeekText() throws Exception;
    ArrayList<WebElement> getAllAvailableShiftsInWeekView();
    ArrayList<HashMap<String, String>> getHoursAndShiftsCountForEachWorkRolesInWeekView() throws Exception;
    ArrayList<Float> getAllVesionLabels() throws Exception;
	void publishActiveSchedule()throws Exception;
	boolean isPublishButtonLoaded();
	HashMap<String, Float> getScheduleLabelHours() throws Exception;
	int getgutterSize();
	void verifySelectTeamMembersOption() throws Exception;
	void searchText(String searchInput) throws Exception;
	void getAvailableStatus() throws Exception;
	void clickOnOfferOrAssignBtn() throws Exception;
	void clickOnShiftContainer(int index) throws Exception;
	void deleteShift();
	void deleteAllShiftsInDayView();
	void deleteShiftGutterText();
	boolean getScheduleStatus() throws Exception;
	boolean inActiveWeekDayClosed(int dayIndex) throws Exception;
	void navigateDayViewWithIndex(int dayIndex);
	String getActiveGroupByFilter() throws Exception;
	boolean isActiveWeekHasOneDayClose() throws Exception;
	boolean isActiveWeekAssignedToCurrentUser(String userName) throws Exception;
	boolean isScheduleGroupByWorkRole(String workRoleOption) throws Exception;
	void selectWorkRoleFilterByIndex(int index, boolean isClearWorkRoleFilters) throws Exception;
	ArrayList<String> getSelectedWorkRoleOnSchedule() throws Exception;
	boolean isRequiredActionUnAssignedShiftForActiveWeek() throws Exception;
	void clickOnRefreshButton() throws Exception;
	void selectShiftTypeFilterByText(String filterText) throws Exception;
	List<WebElement> getAvailableShiftsInDayView();
	void dragShiftToRightSide(WebElement shift, int xOffSet);
	boolean isSmartCardAvailableByLabel(String cardLabel) throws Exception;
	void validateBudgetPopUpHeader(String nextWeekView, int weekCount);
	void noBudgetDisplayWhenBudgetNotEntered(String nextWeekView, int weekCount);
//	public void budgetHourInScheduleNBudgetedSmartCard(String nextWeekView, int weekCount);
//	public void budgetHourByWagesInScheduleNBudgetedSmartCard(String nextWeekView,int weekCount);
void budgetInScheduleNBudgetSmartCard(String nextWeekView, int weekCount, int tolerance);
	void disableNextWeekArrow() throws Exception;
	void clickScheduleDraftAndGuidanceStatus(List<String> overviewScheduleWeeksStatus);
	void editBudgetHours();
	void navigateScheduleDayWeekView(String nextWeekView, int weekCount);

	ArrayList<String> getActiveWeekCalendarDates() throws Exception;
	void refreshBrowserPage() throws Exception;
	void addOpenShiftWithDefaultTime(String workRole) throws Exception;
	boolean isNextWeekAvaibale() throws Exception;
	boolean isSmartCardPanelDisplay() throws Exception;
	void convertAllUnAssignedShiftToOpenShift() throws Exception;
	void selectWorkRoleFilterByText(String workRoleLabel, boolean isClearWorkRoleFilters) throws Exception;
	void reduceOvertimeHoursOfActiveWeekShifts() throws Exception;
	boolean isActionButtonLoaded(String actionBtnText) throws Exception;
	void navigateToNextDayIfStoreClosedForActiveDay() throws Exception;
	/*public void validatingRequiredActionforUnAssignedShift() throws Exception;*/
	String getsmartCardTextByLabel(String cardLabel);
	String getWeatherTemperature() throws Exception;
	void validatingGenrateSchedule() throws Exception;
	boolean loadSchedule() throws Exception;
	void generateOrUpdateAndGenerateSchedule() throws Exception;
	void createScheduleForNonDGFlowNewUI() throws Exception;
	HashMap<String, Integer> getScheduleBufferHours() throws Exception;
	boolean isComlianceReviewRequiredForActiveWeek() throws Exception;
	void unGenerateActiveScheduleScheduleWeek() throws Exception;
	boolean isStoreClosedForActiveWeek() throws Exception;
	int getScheduleShiftIntervalCountInAnHour() throws Exception;
	void toggleSummaryView() throws Exception;
	boolean isSummaryViewLoaded() throws Exception;
	void updateScheduleOperatingHours(String day, String startTime, String endTime) throws Exception;
	void dragRollerElementTillTextMatched(WebElement rollerElement, String textToMatch, boolean startHrsSlider) throws Exception;

	boolean isScheduleOperatingHoursUpdated(String startTime, String endTime) throws Exception;
	void verifyScheduledHourNTMCountIsCorrect() throws Exception;
	void complianceShiftSmartCard() throws Exception;
	void viewProfile();
	void changeWorkerRole() throws Exception;
	void assignTeamMember() throws Exception;
	void convertToOpenShift();
	void beforeEdit();
	void selectTeamMembersOptionForOverlappingSchedule() throws Exception;
	boolean getScheduleOverlappingStatus()throws Exception;
	void searchWorkerName(String searchInput) throws Exception;
	void verifyScheduleStatusAsOpen() throws Exception;
	void verifyScheduleStatusAsTeamMember() throws Exception;
	String getActiveAndNextDay() throws Exception;
	HashMap<String, String> getOperatingHrsValue(String day) throws Exception;
	void moveSliderAtCertainPoint(String shiftTime, String startingPoint) throws Exception;
	void clickOnNextDaySchedule(String activeDay) throws Exception;
	void selectTeamMembersOptionForSchedule() throws Exception;
	void selectTeamMembersOptionForScheduleForClopening() throws Exception;
	void verifyClopeningHrs() throws Exception;
	void clickOnPreviousDaySchedule(String activeDay) throws Exception;
	void verifyActiveScheduleType() throws Exception;
	List<Float> validateScheduleAndBudgetedHours() throws Exception;
	void compareHoursFromScheduleAndDashboardPage(List<Float> totalHoursFromSchTbl) throws Exception;
	List<Float> getHoursOnLocationSummarySmartCard() throws Exception;
	void compareHoursFromScheduleSmartCardAndDashboardSmartCard(List<Float> totalHoursFromSchTbl) throws Exception;
	void compareProjectedWithinBudget(int totalCountProjectedOverBudget) throws Exception;
	int getProjectedOverBudget();
	String getDateFromDashboard() throws Exception;
	void compareDashboardAndScheduleWeekDate(String DateOnSchdeule, String DateOnDashboard) throws Exception;
	List<String> getLocationSummaryDataFromDashBoard() throws Exception;
	List<String> getLocationSummaryDataFromSchedulePage() throws Exception;
	void compareLocationSummaryFromDashboardAndSchedule(List<String> ListLocationSummaryOnDashboard, List<String> ListLocationSummaryOnSchedule);
	void openBudgetPopUpGenerateSchedule() throws Exception;
	void updatebudgetInScheduleNBudgetSmartCard(String nextWeekView, int weekCount);
	void toNFroNavigationFromDMToSMSchedule(String CurrentWeek, String locationToSelectFromDMViewSchedule, String districtName, String nextWeekViewOrPreviousWeekView) throws Exception;
	void toNFroNavigationFromDMDashboardToDMSchedule(String CurrentWeek) throws Exception;
	void clickOnViewScheduleLocationSummaryDMViewDashboard();
	void clickOnViewSchedulePayrollProjectionDMViewDashboard();
	void loadingOfDMViewSchedulePage(String SelectedWeek) throws Exception;
	void districtSelectionSMView(String districtName) throws Exception;
	void isScheduleForCurrentDayInDayView(String dateFromDashboard) throws Exception;
	HashMap<String, String> getHoursFromSchedulePage() throws Exception;
	void printButtonIsClickable() throws Exception;
	void todoButtonIsClickable() throws Exception;
	void legionButtonIsClickableAndHasNoEditButton() throws Exception;

	void clickOnSuggestedButton() throws Exception;
	void legionIsDisplayingTheSchedul() throws Exception;

	void currentWeekIsGettingOpenByDefault() throws Exception;

	void goToScheduleNewUI() throws Exception;

	void dayWeekPickerSectionNavigatingCorrectly() throws Exception;

	void landscapePortraitModeShowWellInWeekView() throws Exception;

	void landscapeModeWorkWellInWeekView() throws Exception;

	void portraitModeWorkWellInWeekView() throws Exception;

	HashMap<String, String> getFourUpComingShifts(boolean isStartTomorrow, String currentTime) throws Exception;
	void landscapeModeOnlyInDayView() throws Exception;

	void weatherWeekSmartCardIsDisplayedForAWeek() throws Exception;

	void scheduleUpdateAccordingToSelectWeek() throws Exception;

	boolean verifyRedFlagIsVisible() throws Exception;

	boolean verifyComplianceShiftsSmartCardShowing() throws Exception;

	boolean clickViewShift() throws Exception;

	void verifyComplianceFilterIsSelectedAftClickingViewShift() throws Exception;

	void verifyComplianceShiftsShowingInGrid() throws Exception;

	void verifyClearFilterFunction() throws Exception;

	void clickOnFilterBtn() throws Exception;



	void  verifyShiftSwapCoverRequestedIsDisplayInTo();

	void verifyAnalyzeBtnFunctionAndScheduleHistoryScroll() throws Exception;

	HashMap<String, Float> getScheduleBudgetedHoursInScheduleSmartCard() throws Exception;


//	public HashMap<String, String> getFourUpComingShifts(boolean isStartTomorrow) throws Exception;
void verifyUpComingShiftsConsistentWithSchedule(HashMap<String, String> dashboardShifts, HashMap<String, String> scheduleShifts) throws Exception;
	void clickOnCreateNewShiftButton() throws Exception;
	void verifyTeamCount(List<String> previousTeamCount, List<String> currentTeamCount) throws Exception;
	void selectDaysFromCurrentDay(String currentDay) throws Exception;
	void searchTeamMemberByName(String name) throws Exception;
	void verifyNewShiftsAreShownOnSchedule(String name) throws Exception;
	void verifyShiftsChangeToOpenAfterTerminating(List<Integer> indexes, String name, String currentTime) throws Exception;
	List<Integer> getAddedShiftIndexes(String name) throws Exception;
	boolean areShiftsPresent() throws Exception;
	int verifyClickOnAnyShift() throws Exception;
	void clickTheShiftRequestByName(String requestName) throws Exception;
	boolean isPopupWindowLoaded(String title) throws Exception;
	void verifyComponentsOnSubmitCoverRequest() throws Exception;
	void verifyClickOnSubmitButton() throws Exception;
	void clickOnShiftByIndex(int index) throws Exception;
	boolean verifyShiftRequestButtonOnPopup(List<String> requests) throws Exception;
	void	verifyComparableShiftsAreLoaded() throws Exception;
	String selectOneTeamMemberToSwap() throws Exception;
	void verifyClickCancelSwapOrCoverRequest() throws Exception;
	void goToSchedulePageAsTeamMember() throws Exception;
	void gotoScheduleSubTabByText(String subTitle) throws Exception;
	void	verifyTeamScheduleInViewMode() throws Exception;
	List<String> getWholeWeekSchedule() throws Exception;
	String getSelectedWeek() throws Exception;
	void verifySelectOtherWeeks() throws Exception;
	boolean isSpecificSmartCardLoaded(String cardName) throws Exception;
	int getCountFromSmartCardByName(String cardName) throws Exception;
	void clickLinkOnSmartCardByName(String linkName) throws Exception;
	int getShiftsCount() throws Exception;
	void filterScheduleByShiftTypeAsTeamMember(boolean isWeekView) throws Exception;
	boolean isPrintIconLoaded() throws Exception;
	void verifyThePrintFunction() throws Exception;
	void clickCancelButtonOnPopupWindow() throws Exception;
	void verifyTheDataOfComparableShifts() throws Exception;
	void verifyTheSumOfSwapShifts() throws Exception;
	void verifyNextButtonIsLoadedAndDisabledByDefault() throws Exception;
	void verifySelectOneShiftNVerifyNextButtonEnabled() throws Exception;
	void verifySelectMultipleSwapShifts() throws Exception;
	void verifyClickOnNextButtonOnSwap() throws Exception;
	void verifyBackNSubmitBtnLoaded() throws Exception;
	void verifyTheRedirectionOfBackButton() throws Exception;
	void verifySwapRequestShiftsLoaded() throws Exception;
	void verifyClickAcceptSwapButton() throws Exception;
	void verifyTheContentOfMessageOnSubmitCover() throws Exception;
	void verifyShiftRequestStatus(String expectedStatus) throws Exception;
	Boolean isGenerateButtonLoadedForManagerView() throws Exception;

	void validateGroupBySelectorSchedulePage() throws Exception;
	boolean checkEditButton() 	throws Exception;
	void verifyEditButtonFuntionality() 		throws Exception;
	boolean checkCancelButton() 	throws Exception;
	void selectCancelButton()	throws Exception;
	boolean checkSaveButton()	throws Exception;
	void selectSaveButton()		throws Exception;
	boolean isScheduleFinalized() throws Exception;
	boolean isProfileIconsEnable() throws Exception;
	void clickOnProfileIcon() throws Exception;
	boolean isViewProfileEnable() throws Exception;
	boolean isViewOpenShiftEnable() throws Exception;
	boolean isChangeRoleEnable() throws Exception;
	boolean isAssignTMEnable() throws Exception;
	boolean isConvertToOpenEnable() throws Exception;
	void selectNextWeekSchedule() throws Exception;
	void clickOnViewProfile() throws Exception;
	void clickOnChangeRole() throws Exception;
	boolean validateVariousWorkRolePrompt() throws Exception;
	void verifyRecommendedAndSearchTMEnabled() throws Exception;
	void clickonAssignTM() throws Exception;
	void clickOnConvertToOpenShift() throws Exception;
	void verifyPersonalDetailsDisplayed() throws Exception;
	void verifyWorkPreferenceDisplayed() throws Exception;
	void verifyAvailabilityDisplayed() throws Exception;
	void closeViewProfileContainer() throws Exception;


	boolean  verifyContextOfTMDisplay() throws Exception;

	void verifyChangeRoleFunctionality() throws Exception;

	void verifyMealBreakTimeDisplayAndFunctionality() throws Exception;

	void verifyDeleteShift() throws Exception;

	void clickOnEditMeaLBreakTime() throws Exception;

	void clickOnEditButtonNoMaterScheduleFinalizedOrNot() throws Exception;

	void clickOnOpenShitIcon();

	String getTimeDurationWhenCreateNewShift() throws Exception;

	void verifyConvertToOpenShiftBySelectedSpecificTM() throws Exception;

	int getOTShiftCount();

	void saveSchedule();

	void validateXButtonForEachShift() throws Exception;

	void selectSpecificWorkDay(int dayCountInOneWeek);

	float getShiftHoursByTMInWeekView(String teamMember);

	void selectSpecificTMWhileCreateNewShift(String teamMemberName) throws Exception;

	void verifyWeeklyOverTimeAndFlag(String teamMemberName) throws Exception;

	void deleteTMShiftInWeekView(String teamMemberName) throws Exception;

	void filterScheduleByJobTitle(boolean isWeekView) throws Exception;

	void filterScheduleByWorkRoleAndJobTitle(boolean isWeekView) throws Exception;

	void filterScheduleByShiftTypeAndJobTitle(boolean isWeekView) throws Exception;

	boolean verifyConvertToOpenPopUpDisplay() throws Exception;

	void convertToOpenShiftDirectly();

	// Added by Nora
	void verifyScheduledNOpenFilterLoaded() throws Exception;
	void checkAndUnCheckTheFilters() throws Exception;
	void filterScheduleByBothAndNone() throws Exception;
	String selectOneFilter() throws Exception;
	void verifySelectedFilterPersistsWhenSelectingOtherWeeks(String selectedFilter) throws Exception;
	int selectOneShiftIsClaimShift(List<String> claimShift) throws Exception;
	void verifyClaimShiftOfferNBtnsLoaded() throws Exception;
	List<String> getShiftHoursFromInfoLayout() throws Exception;
	void verifyTheShiftHourOnPopupWithScheduleTable(String scheduleShiftTime, String weekDay) throws Exception;
	String getSpecificShiftWeekDay(int index) throws Exception;
	void verifyClickAgreeBtnOnClaimShiftOffer() throws Exception;
	void verifyClickCancelBtnOnClaimShiftOffer() throws Exception;
	void verifyTheColorOfCancelClaimRequest(String cancelClaim) throws Exception;
	void verifyReConfirmDialogPopup() throws Exception;
	void verifyClickNoButton() throws Exception;
	void verifyClickOnYesButton() throws Exception;
	void verifyTheFunctionalityOfClearFilter() throws Exception;

	void validateTheAvailabilityOfScheduleTable(String userName) throws Exception;

	void validateTheAvailabilityOfScheduleMenu() throws Exception;

	void validateTheFocusOfSchedule() throws Exception;

	void validateTheDefaultFilterIsSelectedAsScheduled() throws Exception;

	void validateTheFocusOfWeek(String currentDate) throws Exception;

	void validateForwardAndBackwardButtonClickable() throws Exception;

	void validateTheDataAccordingToTheSelectedWeek() throws Exception;

	void validateTheSevenDaysIsAvailableInScheduleTable() throws Exception;

	String getTheEarliestAndLatestTimeInSummaryView() throws Exception;

	String getTheEarliestAndLatestTimeInScheduleTable() throws Exception;

	void compareOperationHoursBetweenAdminAndTM(String theEarliestAndLatestTimeInScheduleSummary, String theEarliestAndLatestTimeInScheduleTable) throws Exception;

	void validateThatHoursAndDateIsVisibleOfShifts() throws Exception;

	void validateTheDisabilityOfLocationSelectorOnSchedulePage() throws Exception;

	void goToConsoleScheduleAndScheduleSubMenu() throws Exception;

	void validateProfilePictureInAShiftClickable() throws Exception;

	void validateTheDataOfProfilePopupInAShift() throws Exception;

	void validateTheAvailabilityOfInfoIcon() throws Exception;

	void validateInfoIconClickable() throws Exception;

	void validateTheAvailabilityOfOpenShiftSmartcard() throws Exception;

	void validateViewShiftsClickable() throws Exception;

	void validateTheNumberOfOpenShifts() throws Exception;

	void verifyTheAvailabilityOfClaimOpenShiftPopup() throws Exception;

	List<String> getWeekScheduleShiftTimeListOfMySchedule() throws Exception;

	List<String> getWeekScheduleShiftTimeListOfWeekView(String teamMemberName) throws Exception;

	void clickTheShiftRequestToClaimShift(String requestName, String requestUserName) throws Exception;
	
	void navigateToNextWeek() throws Exception;

	void verifyShiftsAreSwapped(List<String> swapData) throws Exception;

	void clickOnDayViewAddNewShiftButton() throws Exception;

	void addNewShiftsByNames(List<String> names) throws Exception;
	boolean displayAlertPopUp() throws Exception;

	void displayAlertPopUpForRoleViolation() throws Exception;

}