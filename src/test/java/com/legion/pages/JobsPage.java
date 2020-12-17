package com.legion.pages;

import java.util.ArrayList;
import java.util.HashMap;

public interface JobsPage {

    public void iCanEnterJobsTab();

    public void verifyJobLandingPageShowWell();

    public void iCanEnterCreateNewJobPage();

    public void selectJobType(String jobType) throws Exception;

    public void selectWeekForJobToTakePlace();

    public void inputJobTitle(String jobTitle);

    public void inputJobComments(String commentText);

    public void addLocationBtnIsClickable();

    public void iCanSelectLocationsByAddLocation(String searchText, int index);

    public void createBtnIsClickable();

    public void clickOkBtnInCreateNewJobPage();

    public void iCanSearchTheJobWhichICreated(String jobTitle) throws Exception;

    public void iCanGoToJobDetailsPage(int index);

    public void iCanBackToJobListPage();

    public void iCanClickCloseBtnInJobDetailsPage();

    public void iCanDownloadExportResultFile();

    public void iCanDownloadExportTaskSummary();

    public ArrayList<HashMap<String, String>> iCanGetJobInfo(String jobTitle);
}
