package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.legion.utils.MyThreadLocal.*;


public class JobTest extends TestBase {

    public enum modelSwitchOperation{

        Console("Console"),
        OperationPortal("Operation Portal");

        private final String value;
        modelSwitchOperation(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{


        this.createDriver((String)params[0],"83","Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

    }
    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate to enable centralized schedule release function")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyOpenCentralizedScheduleReleaseToYes(String browser, String username, String password, String location) throws Exception {

        try{
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.Console.getValue());
            ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();

            // Validate Controls Scheduling Policies Section
            controlsNewUIPage.clickOnControlsConsoleMenu();
            controlsNewUIPage.clickOnGlobalLocationButton();
            controlsNewUIPage.clickOnControlsSchedulingPolicies();
            boolean isSchedulingPolicies = controlsNewUIPage.isControlsSchedulingPoliciesLoaded();
            SimpleUtils.assertOnFail("Controls Page: Scheduling Policies Section not Loaded.", isSchedulingPolicies, true);
            controlsNewUIPage.clickOnSchedulingPoliciesSchedulesAdvanceBtn();
            //check the centralized schedule release button is yes or no
            List<WebElement> CentralizedScheduleReleaseSelector = controlsNewUIPage.getAvailableSelector();
            WebElement yesItem = CentralizedScheduleReleaseSelector.get(0);
            WebElement noItem = CentralizedScheduleReleaseSelector.get(1);

            if (controlsNewUIPage.isCentralizedScheduleReleaseValueYes()) {
                SimpleUtils.pass("Scheduling Policies: Centralized Schedule Release button is Yes");
            }else
                controlsNewUIPage.updateCentralizedScheduleRelease(yesItem);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }



    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate job landing page show")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyJobLandingPage(String browser, String username, String password, String location) throws Exception {

        try{
            JobsPage jobsPage = pageFactory.createOpsPortalJobsPage();
            jobsPage.iCanEnterJobsTab();
            jobsPage.verifyJobLandingPageShowWell();
            jobsPage.verifyPaginationFunctionInJob();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate job search function")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyJobSearchFunction(String browser, String username, String password, String location) throws Exception {

        try{
            String searchText = "*,Adjust Forecast,Create Schedule,Adjust Budget,Release Schedule";
            String[] searchJobCha = searchText.split(",");
            JobsPage jobsPage = pageFactory.createOpsPortalJobsPage();
            jobsPage.iCanEnterJobsTab();
            for (String search: searchJobCha
                 ) {
                jobsPage.iCanSearchTheJobWhichICreated(search);
            }

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate check create schedule  job details page")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCheckJobDetailsFunction(String browser, String username, String password, String location) throws Exception {

        try{

            try{
                String searchCreateSchedule = "Create Schedule";
                String searchReleaseSchedule = "Release Schedule";
                String searchAdjustBudget = "Adjust Budget";
                String searchAdjustForecast = "Adjust Forecast";
                int index = 0;

                JobsPage jobsPage = pageFactory.createOpsPortalJobsPage();
                jobsPage.iCanEnterJobsTab();
                jobsPage.iCanSearchTheJobWhichICreated(searchCreateSchedule);
                jobsPage.iCanGoToCreateScheduleJobDetailsPage(index);
                jobsPage.iCanBackToJobListPage();
                jobsPage.iCanSearchTheJobWhichICreated(searchReleaseSchedule);
                jobsPage.iCanGoToReleaseScheduleJobDetailsPage(index);
                jobsPage.iCanClickCloseBtnInJobDetailsPage();
                jobsPage.iCanSearchTheJobWhichICreated(searchAdjustBudget);
                jobsPage.iCanGoToAdjustBudgetJobDetailsPage(index);
                jobsPage.iCanClickCloseBtnInJobDetailsPage();
                jobsPage.iCanSearchTheJobWhichICreated(searchAdjustForecast);
                jobsPage.iCanGoToAdjustForecastJobDetailsPage(index);
                jobsPage.iCanClickCloseBtnInJobDetailsPage();
            } catch (Exception e){
                SimpleUtils.fail(e.getMessage(), false);
            }

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Verify copy stop resume and archive job function")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCopyStopResumeAndArchiveJobFunctionFunction(String browser, String username, String password, String location) throws Exception {

        try{
            int index = 0;
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime =  dfs.format(new Date()).trim();
            String jobType = "Create Schedule";
            String jobTitle = "AutoCreateJob"+currentTime;
            setJobName(jobTitle);
            String commentText = "created by automation scripts";
            String searchText = "QA";

            JobsPage jobsPage = pageFactory.createOpsPortalJobsPage();
            jobsPage.iCanEnterJobsTab();
            //create new job firstly
            jobsPage.iCanEnterCreateNewJobPage();
            jobsPage.selectJobType(jobType);
            jobsPage.selectWeekForJobToTakePlace();
            jobsPage.clickOkBtnInCreateNewJobPage();
            jobsPage.inputJobTitle(jobTitle);
            jobsPage.inputJobComments(commentText);
            jobsPage.addLocationBtnIsClickable();
//            jobsPage.iCanSelectLocationsByAddLocation(searchText,index);
            jobsPage.iCanSelectDistrictByAddLocation(searchText,index);
            jobsPage.createBtnIsClickable();
            jobsPage.iCanSearchTheJobWhichICreated(jobTitle);
            jobsPage.iCanStopJob(jobTitle);

            jobsPage.iCanSearchTheJobWhichICreated(jobTitle);
            jobsPage.iCanResumeJob(jobTitle);
            jobsPage.iCanSearchTheJobWhichICreated(jobTitle);
            jobsPage.iCanCopyJob(jobTitle);
            jobsPage.iCanSearchTheJobWhichICreated("Copy Of "+jobTitle);
            jobsPage.iCanStopJob("Copy Of "+jobTitle);
            jobsPage.iCanSearchTheJobWhichICreated("Copy Of "+jobTitle);
            jobsPage.iCanArchiveJob("Copy Of "+jobTitle);



        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate filter function by job type and job status")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyFilterFunctionByJobTypeAndJobStatus(String browser, String username, String password, String location) throws Exception {

        try{
            JobsPage jobsPage = pageFactory.createOpsPortalJobsPage();
            jobsPage.iCanEnterJobsTab();
            jobsPage.filterJobsByJobTypeAndStatus();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate filter function by job type")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyFilterFunctionByJobType(String browser, String username, String password, String location) throws Exception {

        try{
            JobsPage jobsPage = pageFactory.createOpsPortalJobsPage();
            jobsPage.iCanEnterJobsTab();
            jobsPage.filterJobsByJobType();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate filter function by job status")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyFilterFunctionByJobStatus(String browser, String username, String password, String location) throws Exception {

        try{
            JobsPage jobsPage = pageFactory.createOpsPortalJobsPage();
            jobsPage.iCanEnterJobsTab();
            jobsPage.filterJobsByJobStatus();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate create create schedule job")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateScheduleJobFunction(String browser, String username, String password, String location) throws Exception {


        try {
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
            String currentTime =  dfs.format(new Date());
            String jobType = "Create Schedule";
            String jobTitle = "AutoCreateJob"+currentTime;
            setJobName(jobTitle);
            String commentText = "created by automation scripts";
            String searchText = "OMLocation3";
            int index = 0;

//            ArrayList<HashMap<String, String>> jobInfoDetails =jobsPage.iCanGetJobInfo(jobTitle);

//            //go to schedule page to see current week schedule generated or not
//            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
//            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.Console.getValue());
//
//            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
//
//            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
//            locationSelectorPage.changeDistrict("OMDistrict1");
//            locationSelectorPage.changeLocation(searchText);
//
//            SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
//            schedulePage.clickOnScheduleConsoleMenuItem();
//            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
//            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
//            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
//
//            if (schedulePage.isWeekGenerated()){
//                schedulePage.unGenerateActiveScheduleScheduleWeek();
//            }else {
//                SimpleUtils.pass("Current week schedule is not  Generated!");
//                locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
                JobsPage jobsPage = pageFactory.createOpsPortalJobsPage();
                jobsPage.iCanEnterJobsTab();
                jobsPage.iCanEnterCreateNewJobPage();
                if (jobsPage.verifyCreatNewJobPopUpWin()) {
                    jobsPage.selectJobType(jobType);
                    jobsPage.selectWeekForJobToTakePlace();
                    jobsPage.clickOkBtnInCreateNewJobPage();
                    jobsPage.inputJobTitle(jobTitle);
                    jobsPage.inputJobComments(commentText);
                    jobsPage.addLocationBtnIsClickable();
                    jobsPage.iCanSelectLocationsByAddLocation(searchText,index);
                    jobsPage.createBtnIsClickable();
                    jobsPage.iCanSearchTheJobWhichICreated(jobTitle);
                }else
                    SimpleUtils.fail("Create job pop up page load failed",false);
//            }

//            Thread.sleep(60000);//to wait for job completed
//            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.Console.getValue());
//            SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
//
//
//            locationSelectorPage.changeDistrict("OMDistrict1");
//            locationSelectorPage.changeLocation(searchText);
//
//
//            schedulePage.clickOnScheduleConsoleMenuItem();
//            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
//            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
//            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
//
//            if(!schedulePage.isWeekGenerated()&& schedulePage.suggestedButtonIsHighlighted()){
//                SimpleUtils.pass("Created schedule job doesn't generated the manager schedule");
//
//            }else
//                SimpleUtils.fail("It should not generated schedule in manager tab",false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate abnormal create job flow")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAbnormalCheatJobFunction(String browser, String username, String password, String location) throws Exception {

        try{
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
            String currentTime =  dfs.format(new Date());
            String jobType = "Create Schedule";
            String jobTitle = "AutoCreateJob"+currentTime;
            setJobName(jobTitle);
            String commentText = "created by automation scripts";
            String searchText = "OMLocation3";
            int index = 0;

            JobsPage jobsPage = pageFactory.createOpsPortalJobsPage();
            jobsPage.iCanEnterJobsTab();
            jobsPage.iCanEnterCreateNewJobPage();
            jobsPage.iCanCloseJobCreatePopUpWindowByCloseBtn();
            jobsPage.iCanEnterCreateNewJobPage();
            jobsPage.iCanCancelJobCreatePopUpWindowByCancelBtn();
            jobsPage.iCanEnterCreateNewJobPage();

            if (jobsPage.verifyCreatNewJobPopUpWin()) {
                jobsPage.selectJobType(jobType);
                jobsPage.selectWeekForJobToTakePlace();
                jobsPage.clickOkBtnInCreateNewJobPage();
                jobsPage.inputJobTitle(jobTitle);
                jobsPage.inputJobComments(commentText);
                jobsPage.addLocationBtnIsClickable();
                jobsPage.iCanSelectLocationsByAddLocation(searchText, index);
                jobsPage.iCanCancelJobInJobCreatPageByCancelBtn();
                ArrayList<HashMap<String, String>> jobInfoDetails =jobsPage.iCanGetJobInfo(jobTitle);
                if (jobInfoDetails.size()==0) {
                    SimpleUtils.pass("The creating job was canceled successfully after clicking cancel button");
                }else
                    SimpleUtils.fail("",false);
            }else
                SimpleUtils.fail("Create job pop up page load failed",false);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate release schedule job function")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateReleaseScheduleJobFunction(String browser, String username, String password, String location) throws Exception {


        try {
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
            String currentTime =  dfs.format(new Date());
            String jobType = "Release Schedule";
            String jobTitle = "AutoReleaseJob"+currentTime;
            setJobName(jobTitle);
            String commentText = "created by automation scripts";
            String searchText = "OMLocation3";
            int index = 0;
            String releaseDay = "10";
            String timeForRelease = "0";
            
                JobsPage jobsPage = pageFactory.createOpsPortalJobsPage();
                jobsPage.iCanEnterJobsTab();
                jobsPage.iCanEnterCreateNewJobPage();
                if (jobsPage.verifyCreatNewJobPopUpWin()) {
                    jobsPage.selectJobType(jobType);
                    jobsPage.selectWeekForJobToTakePlace();
                    jobsPage.clickOkBtnInCreateNewJobPage();
                    jobsPage.inputJobTitle(jobTitle);
                    jobsPage.inputJobComments(commentText);
                    jobsPage.addLocationBtnIsClickable();
                    jobsPage.iCanSelectLocationsByAddLocation(searchText,index);
                    jobsPage.iCanClickOnCreatAndReleaseCheckBox();
                    jobsPage.iCanSetUpDaysBeforeRelease(releaseDay);
                    jobsPage.iCanSetUpTimeOfRelease(timeForRelease);
                    jobsPage.createBtnIsClickable();
                    jobsPage.iCanSearchTheJobWhichICreated(jobTitle);
                }else
                    SimpleUtils.fail("Create job pop up page load failed",false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate adjust budget job function")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAdjustBudgetJobFunction(String browser, String username, String password, String location) throws Exception {
        try {
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime =  dfs.format(new Date());
            String jobType = "Adjust Budget";
            String jobTitle = "AutoAdjustBudgetJob"+currentTime;
            setJobName(jobTitle);
            String commentText = "created by automation scripts";
            String searchText = "OMLocation3";
            String searchTaskText = "OMLocation3";
            int index = 0;
            String budgetAssignmentNum = "10";
            String workRole = "Lead Sales Associate";
            String taskName = "Cleaning";

            JobsPage jobsPage = pageFactory.createOpsPortalJobsPage();
            jobsPage.iCanEnterJobsTab();
            jobsPage.iCanEnterCreateNewJobPage();
            if (jobsPage.verifyCreatNewJobPopUpWin()) {
                jobsPage.selectJobType(jobType);
                jobsPage.selectWeekForJobToTakePlace();
                jobsPage.clickOkBtnInCreateNewJobPage();
                if (jobsPage.verifyLayoutOfAdjustBudget()) {
                    jobsPage.inputJobTitle(jobTitle);
                    jobsPage.inputJobComments(commentText);
                    jobsPage.addLocationBtnIsClickable();
                    jobsPage.iCanSelectLocationsByAddLocation(searchText,index);
                    jobsPage.iCanSetUpBudgetAssignmentNum(budgetAssignmentNum);
                    //add tasks
                    jobsPage.addTaskButtonIsClickable();
                    jobsPage.iCanAddTasks(searchText,index,taskName);
                    //add work roles
                    jobsPage.addWorkRoleButtonIsClickable();
                    jobsPage.iCanAddWorkRoles(searchText,index,workRole);
                    jobsPage.createBtnIsClickableInAdjustBudgetJob();
                    jobsPage.verifyAdjustBudgetConfirmationPage(jobTitle,budgetAssignmentNum,taskName,workRole);
                    jobsPage.cancelBthInAdjustBudgetConfirmationPageIsClickable();
                    jobsPage.executeBtnIsClickable();
                    jobsPage.iCanSearchTheJobWhichICreated(jobTitle);
                }

            }else
                SimpleUtils.fail("Create job pop up page load failed",false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate adjust forecast job function")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAdjustForecastJobFunction(String browser, String username, String password, String location) throws Exception {
        try {
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime =  dfs.format(new Date());
            String jobType = "Adjust Forecast";
            String jobTitle = "AutoAdjustForecastJob"+currentTime;
            setJobName(jobTitle);
            String commentText = "created by automation scripts";
            String searchText = "OMLocation3";
            String searchTaskText = "OMLocation3";
            int index = 0;
            String budgetAssignmentNum = "10";
            String workRole = "Lead Sales Associate";
            String taskName = "Cleaning";

            JobsPage jobsPage = pageFactory.createOpsPortalJobsPage();
            jobsPage.iCanEnterJobsTab();
            jobsPage.iCanEnterCreateNewJobPage();
            if (jobsPage.verifyCreatNewJobPopUpWin()) {
                jobsPage.selectJobType(jobType);
                jobsPage.selectWeekForJobToTakePlace();
                jobsPage.clickOkBtnInCreateNewJobPage();
                if (jobsPage.verifyLayoutOfAdjustBudget()) {
                    jobsPage.inputJobTitle(jobTitle);
                    jobsPage.inputJobComments(commentText);
                    jobsPage.addLocationBtnIsClickable();
                    jobsPage.iCanSelectLocationsByAddLocation(searchText,index);
                    jobsPage.iCanSetUpBudgetAssignmentNum(budgetAssignmentNum);
                    //add tasks
                    jobsPage.addTaskButtonIsClickable();
                    jobsPage.iCanAddTasks(searchText,index,taskName);
                    //add work roles
                    jobsPage.addWorkRoleButtonIsClickable();
                    jobsPage.iCanAddWorkRoles(searchText,index,workRole);
                    jobsPage.createBtnIsClickableInAdjustBudgetJob();
                    jobsPage.verifyAdjustBudgetConfirmationPage(jobTitle,budgetAssignmentNum,taskName,workRole);
                    jobsPage.cancelBthInAdjustBudgetConfirmationPageIsClickable();
                    jobsPage.executeBtnIsClickable();
                    jobsPage.iCanSearchTheJobWhichICreated(jobTitle);
                }

            }else
                SimpleUtils.fail("Create job pop up page load failed",false);

        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }
}
