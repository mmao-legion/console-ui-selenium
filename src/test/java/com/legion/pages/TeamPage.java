package com.legion.pages;

import java.util.List;

public interface TeamPage {
	public void goToTeam() throws Exception;
//    public boolean isTeam() throws Exception;
//    public void goToCoverage() throws Exception;
//    public boolean isCoverage() throws Exception;
//    public void verifyTeamPage(boolean isTeamPage) throws Exception;
//    public void verifyCoveragePage(boolean isCoveragePage) throws Exception;
    public void performSearchRoster(List<String> list)throws Exception;
	public void coverage();
	public void coverageViewToPastOrFuture(String nextWeekView, int weekCount);
	public boolean loadTeamTab() throws Exception;
	public void searchAndSelectTeamMemberByName(String username) throws Exception;
	public void approvePendingTimeOffRequest() throws Exception;
	public int getPendingTimeOffRequestCount() throws Exception;
	public void openToDoPopupWindow() throws Exception;
	public void approveOrRejectTimeOffRequestFromToDoList(String userName, String timeOffStartDuration, String timeOffEndDuration, String action) throws Exception;
	public void closeToDoPopupWindow() throws Exception;
	public void verifyTeamPageLoadedProperlyWithNoLoadingIcon() throws Exception;
	public void verifyTheFunctionOfSearchTMBar(List<String> testStrings) throws Exception;
	public void verifyTheFunctionOfAddNewTeamMemberButton() throws Exception;
	public void verifyTheMonthAndCurrentDayOnCalendar(String currentDateForSelectedLocation) throws Exception;
	public void	selectATodoCardToTransfer() throws Exception;
	public void verifyHomeLocationCanBeSelected() throws Exception;
}
