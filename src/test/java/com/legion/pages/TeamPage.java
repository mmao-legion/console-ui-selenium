package com.legion.pages;

import java.util.List;

public interface TeamPage {
	public void goToTeam() throws Exception;
//    public boolean isTeam() throws Exception;
//    public void goToCoverage() throws Exception;
//    public boolean isCoverage() throws Exception;
//    public void verifyTeamPage(boolean isTeamPage) throws Exception;
//    public void verifyCoveragePage(boolean isCoveragePage) throws Exception;
    public void performSearchRoster(List list)throws Exception;
	public void coverage();
	public void coverageViewToPastOrFuture(String nextWeekView, int weekCount);
}
