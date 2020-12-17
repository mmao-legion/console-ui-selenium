package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;
import cucumber.api.java.ro.Si;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
    @TestName(description = "Validate job landing page show")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyJobLandingPage(String browser, String username, String password, String location) throws Exception {

        try{
            JobsPage jobsPage = pageFactory.createOpsPortalJobsPage();
            jobsPage.iCanEnterJobsTab();
            jobsPage.verifyJobLandingPageShowWell();
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
    @TestName(description = "Validate create create schedule job")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateScheduleJobFunction(String browser, String username, String password, String location) throws Exception {


       try {
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss ");
            String currentTime =  dfs.format(new Date());
            String jobType = "Create Schedule";
            String jobTitle = currentTime;
            setJobName(jobTitle);
            String commentText = "created by automation scripts";
            String searchText = "omlocation2";
            int index = 0;

            JobsPage jobsPage = pageFactory.createOpsPortalJobsPage();
            jobsPage.iCanEnterJobsTab();
            jobsPage.iCanEnterCreateNewJobPage();
            jobsPage.selectJobType(jobType);
            jobsPage.selectWeekForJobToTakePlace();
            jobsPage.clickOkBtnInCreateNewJobPage();
            jobsPage.inputJobTitle(jobTitle);
            jobsPage.inputJobComments(commentText);
            jobsPage.addLocationBtnIsClickable();
            jobsPage.iCanSelectLocationsByAddLocation(searchText,index);
            jobsPage.createBtnIsClickable();
            jobsPage.iCanSearchTheJobWhichICreated(jobTitle);
//            ArrayList<HashMap<String, String>> jobInfoDetails =jobsPage.iCanGetJobInfo(jobTitle);
            //go to schedule page to see the schedule info
            LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
            locationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.Console.getValue());

            LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
            locationSelectorPage.changeDistrict("OMDistrict1");
            locationSelectorPage.changeLocation(searchText);
            DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
            SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
            schedulePage = pageFactory.createConsoleScheduleNewUIPage();
            schedulePage.clickOnScheduleConsoleMenuItem();
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
            SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
            schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
            if(!schedulePage.isWeekGenerated()){
               SimpleUtils.pass("Created schedule job doesn't generated the manager schedule");
                if (schedulePage.isSuggestedScheduleGegerated()) {
                    SimpleUtils.pass("Created schedule job generated suggested schedule");
                }else
                    SimpleUtils.fail("Created schedule job generated suggested schedule failed",false);
            }else
                SimpleUtils.fail("It should not generated schedule in manager tab",false);
       } catch (Exception e){
           SimpleUtils.fail(e.getMessage(), false);
       }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate check create schedule  job details page ")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCheckJobDetailsFunction(String browser, String username, String password, String location) throws Exception {

        try{
            String searchText = "*";
            int index = 0;

            JobsPage jobsPage = pageFactory.createOpsPortalJobsPage();
            jobsPage.iCanEnterJobsTab();
            jobsPage.iCanSearchTheJobWhichICreated(searchText);
            jobsPage.iCanGoToJobDetailsPage(index);
            jobsPage.iCanDownloadExportResultFile();
            jobsPage.iCanDownloadExportTaskSummary();
            jobsPage.iCanBackToJobListPage();
            jobsPage.iCanSearchTheJobWhichICreated(searchText);
            jobsPage.iCanGoToJobDetailsPage(index);
            jobsPage.iCanClickCloseBtnInJobDetailsPage();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Estelle")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Validate E2E flow of adjust forecast job")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAdjustForecastJobE2EFunction(String browser, String username, String password, String location) throws Exception {

        try{
            String searchText = "test1";

            JobsPage jobsPage = pageFactory.createOpsPortalJobsPage();
            jobsPage.iCanEnterJobsTab();


        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

}
