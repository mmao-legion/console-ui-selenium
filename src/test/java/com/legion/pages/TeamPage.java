package com.legion.pages;

public interface TeamPage {
	public void goToTeam() throws Exception;
    public boolean isTeam() throws Exception;
    
    public void goToCoverage() throws Exception;
    public boolean isCoverage() throws Exception;
    public void verifyTeamPage(boolean isTeamPage) throws Exception;
    public void verifyCoveragePage(boolean isCoveragePage) throws Exception;
}
