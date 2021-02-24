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

    public void iCanBackToJobListPage();

    public void iCanClickCloseBtnInJobDetailsPage();

    public void iCanDownloadExportResultFile();

    public void iCanDownloadExportTaskSummary();

    public ArrayList<HashMap<String, String>> iCanGetJobInfo(String jobTitle);

    public void iCanGoToCreateScheduleJobDetailsPage(int index);

    public void iCanGoToReleaseScheduleJobDetailsPage(int index);

    public void iCanGoToAdjustBudgetJobDetailsPage(int index);

    public void iCanGoToAdjustForecastJobDetailsPage(int index);

    public void iCanCopyJob(String jobTitle) throws Exception;

    public void iCanStopJob(String jobTitle) throws Exception;

    public void iCanResumeJob(String jobTitle) throws Exception;

    public void iCanArchiveJob(String jobTitle) throws Exception;

    public void iCanSelectDistrictByAddLocation(String searchText, int index);

    public void filterJobsByJobTypeAndStatus() throws Exception;

    public void filterJobsByJobType() throws Exception;

    public void filterJobsByJobStatus() throws Exception;

    public void filterClearFilterFunction();

    public boolean verifyCreatNewJobPopUpWin();

    public void iCanCloseJobCreatePopUpWindowByCloseBtn();

    public void iCanCancelJobCreatePopUpWindowByCancelBtn();

    public void iCanCancelJobInJobCreatPageByCancelBtn();

    public void iCanSetUpDaysBeforeRelease(String releaseDay);

    public void iCanSetUpTimeOfRelease(String timeForRelease) throws Exception;

    public void iCanClickOnCreatAndReleaseCheckBox();

    public void verifyPaginationFunctionInJob() throws Exception;
}
