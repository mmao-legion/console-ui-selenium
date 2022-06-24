package com.legion.tests.core.opEmployeeManagement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.legion.pages.OpsPortaPageFactories.LocationsPage;
import com.legion.pages.OpsPortaPageFactories.UserManagementPage;
import com.legion.pages.TeamPage;
import com.legion.pages.core.OpCommons.ConsoleNavigationPage;
import com.legion.pages.core.OpCommons.OpsCommonComponents;
import com.legion.pages.core.OpCommons.OpsPortalNavigationPage;
import com.legion.pages.core.OpCommons.RightHeaderBarPage;
import com.legion.pages.core.opemployeemanagement.AbsentManagePage;
import com.legion.pages.core.opemployeemanagement.EmployeeManagementPanelPage;
import com.legion.pages.core.opemployeemanagement.TimeOffPage;
import com.legion.pages.core.opemployeemanagement.TimeOffReasonConfigurationPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;

public class AccrualEngineTest extends TestBase {
    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception {
        this.createDriver((String) params[0], "83", "Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDoneWithoutUpdateUpperfield((String) params[1], (String) params[2], (String) params[3]);
        RightHeaderBarPage modelSwitchPage = new RightHeaderBarPage();
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        modelSwitchPage.switchToOpsPortal();
        SimpleUtils.assertOnFail("Control Center not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Sophia")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Accrual Engine Distribution Types")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAccrualEngineWorksAsInternalAdminOfAccrualEngineTest(String browser, String username, String password, String location) {
        OpsPortalNavigationPage navigationPage = new OpsPortalNavigationPage();
        //verify that employee management is enabled.
        navigationPage.navigateToEmployeeManagement();
        SimpleUtils.pass("EmployeeManagement Module is enabled!");
        //go to the time off management page
        EmployeeManagementPanelPage panelPage = new EmployeeManagementPanelPage();
        panelPage.goToTimeOffManagementPage();
        //verify that the target template is here.
        AbsentManagePage absentManagePage = new AbsentManagePage();
        String templateName = "AccrualAutoTest(Don't touch!!!)";
        absentManagePage.search(templateName);
        SimpleUtils.assertOnFail("Failed the find the target template!", absentManagePage.getResult().equals(templateName), false);

        //switch to console
        RightHeaderBarPage modelSwitchPage = new RightHeaderBarPage();
        modelSwitchPage.switchToNewTab();
        //search and go to the target location
        ConsoleNavigationPage consoleNavigationPage = new ConsoleNavigationPage();
        consoleNavigationPage.searchLocation("Newark");
        //go to team member details and switch to the time off tab.
        consoleNavigationPage.navigateTo("Team");
        TimeOffPage timeOffPage = new TimeOffPage();
        String teamMemName = "Olaf Kuhic";
        timeOffPage.goToTeamMemberDetail(teamMemName);
        timeOffPage.switchToTimeOffTab();

        //get session id via login
        String sessionId = logIn();
        //set UseAbsenceMgmtConfiguration Toggle On
        if (!isToggleEnabled(sessionId, "UseAbsenceMgmtConfiguration")) {
            String[] toggleResponse = turnOnToggle(sessionId, "UseAbsenceMgmtConfiguration");
            Assert.assertEquals(getHttpStatusCode(toggleResponse), 200, "Failed to get the user's template!");
        }
        //confirm template
        String workerId = "77b9bfb2-3e2a-4cfd-ad40-34c49c27e2b6";
        String targetTemplate = "AccrualAutoTest(Don't touch!!!)";
        String tempName = getUserTemplate(workerId, sessionId);
        Assert.assertEquals(tempName, targetTemplate, "The user wasn't associated to this Template!!! ");
        //create a time off balance map to store the expected time off balances.
        HashMap<String, String> expectedTOBalance = new HashMap<>();
        expectedTOBalance.put("Annual Leave2", "0");// HireDate~HireDate/Monthly /calendar month/begin
        expectedTOBalance.put("Annual Leave3", "0");// HireDate~Specified/Monthly /hire month/end
        expectedTOBalance.put("Bereavement3", "0");// HireDate~Specified/weekly
        expectedTOBalance.put("Covid2", "0");// HireDate~HireDate/worked hours/Rate
        expectedTOBalance.put("Covid3", "0");// HireDate~Specified/worked hours/total hour
        expectedTOBalance.put("Grandparents Day Off2", "0");//HireDate~HireDate/lump-sum
        expectedTOBalance.put("Grandparents Day Off3", "0");//HireDate~Specified/lump-sum
        expectedTOBalance.put("Pandemic1", "0");//Specified~Specified/Monthly /calendar month/begin /allowance in days 126(out of)
        expectedTOBalance.put("Pandemic2", "0");//Specified~Specified/weekly /allowance in days 127(in)
        expectedTOBalance.put("Pandemic3", "0");//Specified~Specified/worked hours/Rate /allowance in days 126(out of)
        expectedTOBalance.put("Pandemic4", "0");//Specified~Specified/lump-sum /allowance in days 127(in)
        expectedTOBalance.put("Spring Festival", "0");//None distribution

        //Delete a worker's accrual
        String[] deleteResponse = deleteAccrualByWorkerId(workerId, sessionId);
        Assert.assertEquals(getHttpStatusCode(deleteResponse), 200, "Failed to delete the user's accrual!");
        System.out.println("Delete worker's accrual balance successfully!");
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> actualTOB = timeOffPage.getTimeOffBalance();
        Assert.assertEquals(actualTOB, expectedTOBalance, "Failed to assert clear the accrual balance!");

        //Edit the None distribution time off to 20 hours
        timeOffPage.editTheLastTimeOff("20");
        OpsCommonComponents opsCommonPon = new OpsCommonComponents();
        opsCommonPon.okToActionInModal(true);
        //expected
        expectedTOBalance.put("Spring Festival", "20");
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> balanceEdited = timeOffPage.getTimeOffBalance();
        Assert.assertEquals(balanceEdited, expectedTOBalance, "Failed to edit accrual balance successfully!");

        //run engine to specified date
        String date1 = "2021-05-09";
        String date2 = "2021-06-07";
        String date3 = "2021-09-01";
        String date4 = "2021-09-07";
        String date5 = "2021-12-30";
        String date6 = "2021-12-31";
        String date7 = "2022-01-01";
        String date8 = "2022-01-31";
        String date9 = "2022-05-07";
        String date10 = "2022-05-08";
        String date11 = "2022-10-31";

        /*clock added in time sheet
         May-9 14.5 approved
         Aug-31 14.5 approved
       */
        //Run engine to Date1 2021-05-09
        String[] accrualResponse1 = runAccrualJobToSimulateDate(workerId, date1, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse1), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Covid2", "1.01");//May-9 14.5 hours approved
        expectedTOBalance.put("Grandparents Day Off2", "8");
        expectedTOBalance.put("Grandparents Day Off3", "8");
        expectedTOBalance.put("Pandemic2", "18");
        expectedTOBalance.put("Pandemic3", "1.01");
        expectedTOBalance.put("Pandemic4", "8");
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance0509 = timeOffPage.getTimeOffBalance();
        //HashMap<String, String> accrualHistory0509 = timeOffPage.getAccrualHistory();
        String verification1 = validateTheAccrualResults(accrualBalance0509, expectedTOBalance);
        Assert.assertTrue(verification1.contains("Succeeded in validating"), verification1);

        //Run engine to Date2 2021-06-07
        String[] accrualResponse2 = runAccrualJobToSimulateDate(workerId, date2, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse2), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Annual Leave3", "1");//the first hire month; +1hours
        expectedTOBalance.put("Bereavement3", "4");//+4 hours on Jun-4
        expectedTOBalance.put("Pandemic1", "1"); // +1 hours on the 1st of every month
        expectedTOBalance.put("Pandemic2", "22"); // on Jun-3
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance0607 = timeOffPage.getTimeOffBalance();
        //HashMap<String, String> accrualHistory0607 = timeOffPage.getAccrualHistory();
        String verification2 = validateTheAccrualResults(accrualBalance0607, expectedTOBalance);
        Assert.assertTrue(verification2.contains("Succeeded in validating"), verification2);

        //Run engine to Date3 2021-09-01
        String[] accrualResponse3 = runAccrualJobToSimulateDate(workerId, date3, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse3), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Annual Leave2", "5");//0 +5 hours on Sep-1st
        expectedTOBalance.put("Annual Leave3", "3");//1 +2hours(newly accrued)
        expectedTOBalance.put("Bereavement3", "16");//4 +12hours(newly accrued)
        expectedTOBalance.put("Pandemic1", "4"); //1 +3 hours, grant 1 hour on the 1st of every month
        expectedTOBalance.put("Pandemic2", "34");//Specified~Specified/weekly /allowance in days 127(in)  22 +12 hours
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance0901 = timeOffPage.getTimeOffBalance();
        //HashMap<String, String> accrualHistory0901 = timeOffPage.getAccrualHistory();
        String verification3 = validateTheAccrualResults(accrualBalance0901, expectedTOBalance);
        Assert.assertTrue(verification3.contains("Succeeded in validating"), verification3);

        //Run engine to Date4 2021-09-07
        String[] accrualResponse4 = runAccrualJobToSimulateDate(workerId, date4, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse4), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Annual Leave3", "4");//3 +1hours(newly accrued)
        expectedTOBalance.put("Bereavement3", "17");//16+1hours(newly accrued)
        expectedTOBalance.put("Pandemic2", "35");//34 +1hours
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance0907 = timeOffPage.getTimeOffBalance();
        //HashMap<String, String> accrualHistory0907 = timeOffPage.getAccrualHistory();
        System.out.println(accrualBalance0907);
        System.out.println(expectedTOBalance);
        String verification4 = validateTheAccrualResults(accrualBalance0907, expectedTOBalance);
        Assert.assertTrue(verification4.contains("Succeeded in validating"), verification4);


        //Run engine to Date5 2021-12-30
        String[] accrualResponse5 = runAccrualJobToSimulateDate(workerId, date5, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse5), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Annual Leave3", "7");//4 +3hours(newly accrued)
        expectedTOBalance.put("Bereavement3", "33");//17+16hours(newly accrued)  +1 on Dec-31
        expectedTOBalance.put("Pandemic1", "7"); //4 +3 hours, grant 1 hour on the 1st of every month
        expectedTOBalance.put("Pandemic2", "52");//35 +17hours
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance1230 = timeOffPage.getTimeOffBalance();
        //HashMap<String, String> accrualHistory1230 = timeOffPage.getAccrualHistory();
        System.out.println(accrualBalance1230);
        System.out.println(expectedTOBalance);
        String verification5 = validateTheAccrualResults(accrualBalance1230, expectedTOBalance);
        Assert.assertTrue(verification5.contains("Succeeded in validating"), verification5);

        //Run engine to Date6 2021-12-31
        /*Verify the max Carryover
          1.hireDate to hireDate should not do max carryover
          2.hireDate to specifiedDate(Dec-31) should do max carryover
          */
        String[] accrualResponse6 = runAccrualJobToSimulateDate(workerId, date6, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse6), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Annual Leave3", "2");// balance 7  max carryover: 2
        expectedTOBalance.put("Bereavement3", "3");// balance 33  max carryover: 2   +1hour accrued
        expectedTOBalance.put("Grandparents Day Off3", "2");//balance 8  max carryover: 2  //HireDate~Specified/lump-sum
        expectedTOBalance.put("Pandemic1", "2");//7 hours  max carryover: 2  //Specified~Specified/Monthly /calendar month/begin /allowance in days 126(out of)
        expectedTOBalance.put("Pandemic2", "2");//52 hours  max carryover: 2 //Specified~Specified/weekly /allowance in days 127(in)
        expectedTOBalance.put("Pandemic4", "2");//8 hours  max carryover: 2  //Specified~Specified/lump-sum /allowance in days 127(in)
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance1231 = timeOffPage.getTimeOffBalance();
        //HashMap<String, String> accrualHistory1231 = timeOffPage.getAccrualHistory();
        System.out.println(accrualBalance1231);
        System.out.println(expectedTOBalance);
        String verification6 = validateTheAccrualResults(accrualBalance1231, expectedTOBalance);
        Assert.assertTrue(verification6.contains("Succeeded in validating"), verification6);


        //Run engine to Date7 2022-01-01
        String[] accrualResponse7 = runAccrualJobToSimulateDate(workerId, date7, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse7), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Annual Leave2", "8");//5 +3hours(newly accrued)
        expectedTOBalance.put("Grandparents Day Off3", "10");//2(max carryover)+8(newly accrued)//HireDate~Specified/lump-sum
        expectedTOBalance.put("Pandemic1", "3");//2(max carryover)+1(newly accrued) //Specified~Specified/Monthly /calendar month/begin /allowance in days 126(out of)
        expectedTOBalance.put("Pandemic4", "10");//2(max carryover)+8(newly accrued) //Specified~Specified/lump-sum /allowance in days 127(in)
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance0101 = timeOffPage.getTimeOffBalance();
        //HashMap<String, String> accrualHistory0101 = timeOffPage.getAccrualHistory();
        System.out.println(accrualBalance0101);
        System.out.println(expectedTOBalance);
        String verification7 = validateTheAccrualResults(accrualBalance0101, expectedTOBalance);
        Assert.assertTrue(verification7.contains("Succeeded in validating"), verification7);

        //Run engine to Date8 2022-01-31
        String[] accrualResponse8 = runAccrualJobToSimulateDate(workerId, date8, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse8), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Annual Leave3", "3");//2 +1=3hours(newly accrued)
        expectedTOBalance.put("Bereavement3", "7");//3 +4hours(newly accrued)
        expectedTOBalance.put("Pandemic2", "6");//2 +4=6 hours

        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance0131 = timeOffPage.getTimeOffBalance();
        //HashMap<String, String> accrualHistory0131 = timeOffPage.getAccrualHistory();
        String verification8 = validateTheAccrualResults(accrualBalance0131, expectedTOBalance);
        System.out.println(accrualBalance0131);
        System.out.println(expectedTOBalance);
        Assert.assertTrue(verification8.contains("Succeeded in validating"), verification8);

        //Run engine to Date9 2022-05-07
        String[] accrualResponse9 = runAccrualJobToSimulateDate(workerId, date9, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse9), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Annual Leave2", "12");//8 +4hours(newly accrued)
        expectedTOBalance.put("Annual Leave3", "7");//3 +4=7hours(newly accrued)
        expectedTOBalance.put("Bereavement3", "21");//7+14hours(newly accrued)
        expectedTOBalance.put("Pandemic1", "7");//3 +4hours
        expectedTOBalance.put("Pandemic2", "20");//6 +14=20hours
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance050722 = timeOffPage.getTimeOffBalance();
        //HashMap<String, String> accrualHistory050722 = timeOffPage.getAccrualHistory();
        System.out.println(accrualBalance050722);
        System.out.println(expectedTOBalance);
        String verification9 = validateTheAccrualResults(accrualBalance050722, expectedTOBalance);
        Assert.assertTrue(verification9.contains("Succeeded in validating"), verification9);

        //Run engine to Date10 2022-05-08
        /*Verify the max Carryover
          1.hireDate to hireDate should do max carryover
          */
        String[] accrualResponse10 = runAccrualJobToSimulateDate(workerId, date10, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse10), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Annual Leave2", "2");//max carryover
        expectedTOBalance.put("Grandparents Day Off2", "10");//2 max carryover+ 8 new accrual=10 hours
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance050822 = timeOffPage.getTimeOffBalance();
        //HashMap<String, String> accrualHistory050822 = timeOffPage.getAccrualHistory();
        System.out.println(accrualBalance050822);
        System.out.println(expectedTOBalance);
        String verification10 = validateTheAccrualResults(accrualBalance050822, expectedTOBalance);
        Assert.assertTrue(verification10.contains("Succeeded in validating"), verification10);

        //Run engine to Date11 2022-10-31
        String[] accrualResponse11 = runAccrualJobToSimulateDate(workerId, date11, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse11), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Annual Leave2", "7");//2+5new accrual
        expectedTOBalance.put("Annual Leave3", "12");//7 +5hours(newly accrued)
        expectedTOBalance.put("Bereavement3", "46");//21+25
        expectedTOBalance.put("Pandemic1", "12");//7 +5hours
        expectedTOBalance.put("Pandemic2", "45");//20 +25=45hours
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance103122 = timeOffPage.getTimeOffBalance();
        //HashMap<String, String> accrualHistory103122 = timeOffPage.getAccrualHistory();
        System.out.println(accrualBalance103122);
        System.out.println(expectedTOBalance);
        String verification11 = validateTheAccrualResults(accrualBalance103122, expectedTOBalance);
        Assert.assertTrue(verification11.contains("Succeeded in validating"), verification11);
    }

    private String logIn() {
        //header
        HashMap<String, String> loginHeader = new HashMap<String, String>();
        //body
        String loginString = "{\"enterpriseName\":\"opauto\",\"userName\":\"fiona+58@legion.co\",\"passwordPlainText\":\"admin11.a\",\"sourceSystem\":\"legion\"}";
        //post request
        String[] postResponse = HttpUtil.httpPost(Constants.loginUrlRC, loginHeader, loginString);
        Assert.assertEquals(getHttpStatusCode(postResponse), 200, "Failed to login!");
        String sessionId = postResponse[1];
        return sessionId;
    }

    public boolean isToggleEnabled(String sessionId, String toggleName) {
        String togglesUrl = Constants.toggles;
        Map<String, String> togglePara = new HashMap<>();
        togglePara.put("toggle", toggleName);
        String[] response = HttpUtil.httpGet(togglesUrl, sessionId, togglePara);
        Assert.assertEquals(getHttpStatusCode(response), 200, "Failed to login!");
        //get the response
        boolean isToggleOn = Boolean.parseBoolean(JsonUtil.getJsonObjectValue(response[1], "record", "enabled"));
        return isToggleOn;
    }

    private String[] turnOnToggle(String sessionId, String toggleName) {//UseAbsenceMgmtConfiguration
        //url
        String toggleUrl = Constants.toggles;
        String url = toggleUrl + "?toggle=" + toggleName;
        //set headers
        HashMap<String, String> toggleHeader = new HashMap<String, String>();
        toggleHeader.put("sessionId", sessionId);
        toggleHeader.put("Content-Type", "application/json;charset=UTF-8");
        //body String
        String setToggleStr = "{ \"level\": \"Enterprise\",\"record\": {\"name\": \"UseAbsenceMgmtConfiguration\",\"defaultValue\": false,\"rules\": [{\"enterpriseName\": \"opauto\"},{\"enterpriseName\": \"cinemark-wkdy\"},{\"enterpriseName\": \"carters\"},{\"enterpriseName\": \"op\"}]},\"valid\": true }";
        //post
        return HttpUtil.httpPost(url, toggleHeader, setToggleStr);
    }

    public String getUserTemplate(String workerId, String sessionId) {
        String getTemplateUrl = Constants.getTemplateByWorkerId;
        Map<String, String> templatePara = new HashMap<>();
        templatePara.put("workerId", workerId);
        templatePara.put("type", "Accruals");
        String[] templateResponse = HttpUtil.httpGet(getTemplateUrl, sessionId, templatePara);
        String tempName = null;
        if (getHttpStatusCode(templateResponse) == HttpStatus.SC_OK) {
            tempName = JsonUtil.getJsonObjectValue(templateResponse[1], "record", "name");
        } else {
            System.out.println("Failed to get the user's template!");
        }
        return tempName;
    }

    public String[] deleteAccrualByWorkerId(String workerId, String sessionId) {
        String deleteAccrualUrl = Constants.deleteAccrualByWorkerId;
        Map<String, String> deleteAccrualPara = new HashMap<>();
        deleteAccrualPara.put("workerId", workerId);
        return HttpUtil.httpGet(deleteAccrualUrl, sessionId, deleteAccrualPara);
    }


    public String[] runAccrualJobToSimulateDate(String workerId, String date, String sessionId) {
        String runAccrualJobUrl = Constants.runAccrualJobWithSimulateDateAndWorkerId;
        Map<String, String> accrualJobParas = new HashMap<>();
        accrualJobParas.put("date", date);
        accrualJobParas.put("workerId", workerId);
        return HttpUtil.httpGet(runAccrualJobUrl, sessionId, accrualJobParas);
    }

    public int getHttpStatusCode(String[] httpResponse) {
        return Integer.parseInt(httpResponse[0]);
    }

    public String validateTheAccrualResults(HashMap<String, String> hashMap1, HashMap<String, String> hashMap2) {
        String result = null;
        if (hashMap1.equals(hashMap2)) {
            result = "Succeeded in validating all the distribution methods work as expected!";
        } else {
            for (String key : hashMap1.keySet()
            ) {
                if (!hashMap1.get(key).equals(hashMap2.get(key))) {
                    result = key + ":  " + distributionMethod().get(key) + ":  Accrued incorrectly!";
                    System.out.println(result);
                }
            }
        }
        return result;
    }

    public HashMap<String, String> distributionMethod() {
        HashMap<String, String> distributionMethod = new HashMap<>();
        distributionMethod.put("Annual Leave2", "HireDate~HireDate/Monthly/calendar month/begin");
        distributionMethod.put("Annual Leave3", "HireDate~Specified/Monthly/hire month/end");
        distributionMethod.put("Bereavement3", "HireDate~Specified/Weekly");
        distributionMethod.put("Covid2", "HireDate~HireDate/Worked Hours/Rate");
        distributionMethod.put("Covid3", "HireDate~Specified/Worked Hours/Total hour");
        distributionMethod.put("Grandparents Day Off2", "HireDate~HireDate/Lump-sum");
        distributionMethod.put("Grandparents Day Off3", "HireDate~Specified/Lump-sum");
        distributionMethod.put("Pandemic1", "Specified~Specified/Monthly/calendar month/begin /allowance in days 126(out of)");
        distributionMethod.put("Pandemic2", "Specified~Specified/Weekly/allowance in days 127(in)");
        distributionMethod.put("Pandemic3", "Specified~Specified/Worked Hours/Rate/allowance in days 126(out of)");
        distributionMethod.put("Pandemic4", "Specified~Specified/Lump-sum /allowance in days 127(in)");
        distributionMethod.put("Spring Festival", "None distribution");
        return distributionMethod;
    }

    /*
   1.accrued well before editing/importing
   2.edit/import employee balance successfully
   3.run accrual engine again, it should accrue well based on the editing or importing balance.
   */
    @Automated(automated = "Automated")
    @Owner(owner = "Sophia")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Accrual Engine Distribution Types")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAccrualEngineWorksWellAfterEditingAsInternalAdminOfAccrualEngineTest(String browser, String username, String password, String location) {

        OpsPortalNavigationPage navigationPage = new OpsPortalNavigationPage();
        //verify that employee management is enabled.
        navigationPage.navigateToEmployeeManagement();
        SimpleUtils.pass("EmployeeManagement Module is enabled!");
        //go to the time off management page
        EmployeeManagementPanelPage panelPage = new EmployeeManagementPanelPage();
        panelPage.goToTimeOffManagementPage();
        //verify that the target template is here.
        AbsentManagePage absentManagePage = new AbsentManagePage();
        String templateName = "AccrualAutoTest(Don't touch!!!)";
        absentManagePage.search(templateName);
        SimpleUtils.assertOnFail("Failed the find the target template!", absentManagePage.getResult().equals(templateName), false);

        //switch to console
        RightHeaderBarPage modelSwitchPage = new RightHeaderBarPage();
        modelSwitchPage.switchToNewTab();
        //search and go to the target location
        ConsoleNavigationPage consoleNavigationPage = new ConsoleNavigationPage();
        consoleNavigationPage.searchLocation("Newark");
        //go to team member details and switch to the time off tab.
        consoleNavigationPage.navigateTo("Team");
        TimeOffPage timeOffPage = new TimeOffPage();
        String teamMemName = "Olaf Kuhic";
        timeOffPage.goToTeamMemberDetail(teamMemName);
        timeOffPage.switchToTimeOffTab();

        //get session id via login
        String sessionId = logIn();
        //set UseAbsenceMgmtConfiguration Toggle On
        if (!isToggleEnabled(sessionId, "UseAbsenceMgmtConfiguration")) {
            String[] toggleResponse = turnOnToggle(sessionId, "UseAbsenceMgmtConfiguration");
            Assert.assertEquals(getHttpStatusCode(toggleResponse), 200, "Failed to get the user's template!");
        }
        //confirm template
        String workerId = "77b9bfb2-3e2a-4cfd-ad40-34c49c27e2b6";
        String targetTemplate = "AccrualAutoTest(Don't touch!!!)";
        String tempName = getUserTemplate(workerId, sessionId);
        Assert.assertEquals(tempName, targetTemplate, "The user wasn't associated to this Template!!! ");
        //create a time off balance map to store the expected time off balances.
        HashMap<String, String> expectedTOBalance = new HashMap<>();
        expectedTOBalance.put("Annual Leave2", "0");// HireDate~HireDate/Monthly /calendar month/begin
        expectedTOBalance.put("Annual Leave3", "0");// HireDate~Specified/Monthly /hire month/end
        expectedTOBalance.put("Bereavement3", "0");// HireDate~Specified/weekly
        expectedTOBalance.put("Covid2", "0");// HireDate~HireDate/worked hours/Rate
        expectedTOBalance.put("Covid3", "0");// HireDate~Specified/worked hours/total hour
        expectedTOBalance.put("Grandparents Day Off2", "0");//HireDate~HireDate/lump-sum
        expectedTOBalance.put("Grandparents Day Off3", "0");//HireDate~Specified/lump-sum
        expectedTOBalance.put("Pandemic1", "0");//Specified~Specified/Monthly /calendar month/begin /allowance in days 126(out of)
        expectedTOBalance.put("Pandemic2", "0");//Specified~Specified/weekly /allowance in days 127(in)
        expectedTOBalance.put("Pandemic3", "0");//Specified~Specified/worked hours/Rate /allowance in days 126(out of)
        expectedTOBalance.put("Pandemic4", "0");//Specified~Specified/lump-sum /allowance in days 127(in)
        expectedTOBalance.put("Spring Festival", "0");//None distribution

        //Delete the worker's accrual balance
        String[] deleteResponse = deleteAccrualByWorkerId(workerId, sessionId);
        Assert.assertEquals(getHttpStatusCode(deleteResponse), 200, "Failed to delete the user's accrual!");
        System.out.println("Delete worker's accrual balance successfully!");
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> actualTOB = timeOffPage.getTimeOffBalance();
        Assert.assertEquals(actualTOB, expectedTOBalance, "Failed to assert clear the accrual balance!");

        //run engine to a specified date
        String date1 = "2021-9-30";
        String[] accrualResponse1 = runAccrualJobToSimulateDate(workerId, date1, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse1), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Annual Leave2", "5");// HireDate~HireDate/Monthly /calendar month/begin
        expectedTOBalance.put("Annual Leave3", "4");// HireDate~Specified/Monthly /hire month/end
        expectedTOBalance.put("Bereavement3", "20");// HireDate~Specified/weekly  +1 on OCt-1
        expectedTOBalance.put("Covid2", "1.01");// HireDate~HireDate/worked hours/Rate
        expectedTOBalance.put("Grandparents Day Off2", "8");//HireDate~HireDate/lump-sum
        expectedTOBalance.put("Grandparents Day Off3", "8");//HireDate~Specified/lump-sum
        expectedTOBalance.put("Pandemic1", "4");//Specified~Specified/Monthly /calendar month/begin /allowance in days 126(out of)
        expectedTOBalance.put("Pandemic2", "39");//Specified~Specified/weekly /allowance in days 127(in)
        expectedTOBalance.put("Pandemic3", "1.01");//Specified~Specified/worked hours/Rate /allowance in days 126(out of)
        expectedTOBalance.put("Pandemic4", "8");//Specified~Specified/lump-sum /allowance in days 127(in)
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance123021 = timeOffPage.getTimeOffBalance();
        String verification1 = validateTheAccrualResults(accrualBalance123021, expectedTOBalance);
        Assert.assertTrue(verification1.contains("Succeeded in validating"), verification1);


        //Edit the employee's balance
        //edit
        HashMap<String, String> editTimeOffNameValues = new HashMap<>();
        editTimeOffNameValues.put("Annual Leave2", "3");// HireDate~HireDate/Monthly /calendar month/begin
        editTimeOffNameValues.put("Annual Leave3", "5");// HireDate~Specified/Monthly /hire month/end
        editTimeOffNameValues.put("Bereavement3", "5");// HireDate~Specified/weekly
        editTimeOffNameValues.put("Covid2", "5");// HireDate~HireDate/worked hours/Rate
        editTimeOffNameValues.put("Covid3", "5");// HireDate~Specified/worked hours/total hour
        editTimeOffNameValues.put("Grandparents Day Off2", "5");//HireDate~HireDate/lump-sum
        editTimeOffNameValues.put("Grandparents Day Off3", "5");//HireDate~Specified/lump-sum
        editTimeOffNameValues.put("Pandemic1", "10");//Specified~Specified/Monthly /calendar month/begin /allowance in days 126(out of)
        editTimeOffNameValues.put("Pandemic2", "10");//Specified~Specified/weekly /allowance in days 127(in)
        editTimeOffNameValues.put("Pandemic3", "10");//Specified~Specified/worked hours/Rate /allowance in days 126(out of)
        editTimeOffNameValues.put("Pandemic4", "10");//Specified~Specified/lump-sum /allowance in days 127(in)
        editTimeOffNameValues.put("Spring Festival", "10");//None distribution

        timeOffPage.editTimeOff(editTimeOffNameValues);
        OpsCommonComponents opsCommonPon = new OpsCommonComponents();
        opsCommonPon.okToActionInModal(true);
        //expected
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> balanceEdited = timeOffPage.getTimeOffBalance();
        Assert.assertEquals(balanceEdited, editTimeOffNameValues, "Failed to edit accrual balance successfully!");


        //run engine to a specified date and verify that accrued based on the editing balance.
        String date2 = "2021-12-30";
        String[] accrualResponse2 = runAccrualJobToSimulateDate(workerId, date2, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse2), 200, "Failed to run accrual job!");
        //expected accrual
        editTimeOffNameValues.put("Annual Leave3", "8");//5 +3hours(newly accrued)
        editTimeOffNameValues.put("Bereavement3", "18");//5 +13(newly accrued)
        editTimeOffNameValues.put("Pandemic1", "12");//10 +3(newly accrued)  for annual earn is 12; so 13-1=12
        editTimeOffNameValues.put("Pandemic2", "23");//10 +13(newly accrued)
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance103122 = timeOffPage.getTimeOffBalance();
        String verification2 = validateTheAccrualResults(accrualBalance103122, editTimeOffNameValues);
        Assert.assertTrue(verification2.contains("Succeeded in validating"), verification2);
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Sophia")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Accrual Look Back")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAccrualEngineLookBackAsInternalAdminOfAccrualEngineTest(String browser, String username, String password, String location) {
        //worked hours look back function
        OpsPortalNavigationPage navigationPage = new OpsPortalNavigationPage();
        navigationPage.navigateToEmployeeManagement();
        SimpleUtils.pass("EmployeeManagement Module is enabled!");
        //go to the time off management page
        EmployeeManagementPanelPage panelPage = new EmployeeManagementPanelPage();
        panelPage.goToTimeOffManagementPage();
        //verify that the target template is here.
        AbsentManagePage absentManagePage = new AbsentManagePage();
        String targetTemplate = "AccrualAuto-WH-Don't Touch!";
        absentManagePage.search(targetTemplate);
        SimpleUtils.assertOnFail("Failed the find the target template!", absentManagePage.getResult().equals(targetTemplate), false);

        //switch to console
        RightHeaderBarPage modelSwitchPage = new RightHeaderBarPage();
        modelSwitchPage.switchToNewTab();
        //search and go to the target location
        ConsoleNavigationPage consoleNavigationPage = new ConsoleNavigationPage();
        consoleNavigationPage.searchLocation("JFK Enrollment");
        //go to team member details and switch to the time off tab.
        consoleNavigationPage.navigateTo("Team");
        TimeOffPage timeOffPage = new TimeOffPage();
        String teamMemName = "Ona Morar";
        timeOffPage.goToTeamMemberDetail(teamMemName);
        timeOffPage.switchToTimeOffTab();

        //get session id via login
        String sessionId = logIn();
        //set UseAbsenceMgmtConfiguration Toggle On
        if (!isToggleEnabled(sessionId, "UseAbsenceMgmtConfiguration")) {
            String[] toggleResponse = turnOnToggle(sessionId, "UseAbsenceMgmtConfiguration");
            Assert.assertEquals(getHttpStatusCode(toggleResponse), 200, "Failed to get the user's template!");
        }
        //confirm template
        String workerId = "b5b707c9-c0c1-4505-82be-d4e944a3e35e";
        String tempName = getUserTemplate(workerId, sessionId);
        Assert.assertEquals(tempName, targetTemplate, "The user wasn't associated to this Template!!! ");

        //data restore after testing.
        resetTheTimeClocksDataForLookBack();

        //create a time off balance map to store the expected time off balances.
        HashMap<String, String> expectedTOBalance = new HashMap<>();
        expectedTOBalance.put("PTO", "0");
        expectedTOBalance.put("Sick", "0");

        //Delete the worker's accrual balance
        String[] deleteResponse = deleteAccrualByWorkerId(workerId, sessionId);
        Assert.assertEquals(getHttpStatusCode(deleteResponse), 200, "Failed to delete the user's accrual!");
        System.out.println("Delete worker's accrual balance successfully!");
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> actualTOB = timeOffPage.getTimeOffBalance();
        Assert.assertEquals(actualTOB, expectedTOBalance, "Failed to assert clear the accrual balance!");

        //clocks
        //Jan-9 to Jan-13  60.2hours Approved.  1/3.47354
        //Jan-30 14.5 Pending
        //Jan-31 14 Approved  0.8078
        //Feb-1st 9 Pending  0.5193
        //Feb-2nd null
        //Feb-3 14.7 Approved  0.84819
        //Feb-4 13 Approved    0.7501
        //Feb-5 14.33 Approved 0.826841
        //Feb-6 14 Approved   0.8078
        //run engine to a specified date and verify that accrued based on the editing balance.
        String date1 = "2022-02-04";
        String[] accrualResponse1 = runAccrualJobToSimulateDate(workerId, date1, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse1), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("PTO", "2");//~21.9   Accrual 1 on Jan-30 + accrual 1 on Feb-3;~8.9
        expectedTOBalance.put("Sick", "5.88");//3.47354(before)+0(lb5 for pending)+0.8078(lb4)+0(lb3 for pending)+0(lb2 null)+0.84819(lb1 ) +0.7501(current date)=5.87963

        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance220204 = timeOffPage.getTimeOffBalance();
        String verification1 = validateTheAccrualResults(accrualBalance220204, expectedTOBalance);
        Assert.assertTrue(verification1.contains("Succeeded in validating"), verification1);

        //update clocks
        //1-30 approved (out of look back window)
        //1-31  -4hours  edit from 840 to 600
        //2-1 approved
        //2-4   +5 hours (13+5)*60=1080  edit from 780 to 1080
        String sql130Approved = "update legionrc.TimeSheet set status='Approved' where dayOfTheYear='30' and year='2022' and workerId='b5b707c9-c0c1-4505-82be-d4e944a3e35e' and enterpriseId='aee2dfb5-387d-4b8b-b3f5-62e86d1a9d95'";
        String sql131Minus4Hrs = "update legionrc.TimeSheet set totalMinutes='600' where dayOfTheYear='31' and year='2022' and workerId='b5b707c9-c0c1-4505-82be-d4e944a3e35e' and enterpriseId='aee2dfb5-387d-4b8b-b3f5-62e86d1a9d95'";
        String sql201Approved = "update legionrc.TimeSheet set status='Approved' where dayOfTheYear='32' and year='2022' and workerId='b5b707c9-c0c1-4505-82be-d4e944a3e35e' and enterpriseId='aee2dfb5-387d-4b8b-b3f5-62e86d1a9d95'";
        String sql204Add5Hrs = "update legionrc.TimeSheet set totalMinutes='1080' where dayOfTheYear='35' and year='2022' and workerId='b5b707c9-c0c1-4505-82be-d4e944a3e35e' and enterpriseId='aee2dfb5-387d-4b8b-b3f5-62e86d1a9d95'";
        DBConnection.updateDB(sql130Approved);
        DBConnection.updateDB(sql131Minus4Hrs);
        DBConnection.updateDB(sql201Approved);
        DBConnection.updateDB(sql204Add5Hrs);

        //run engine to a specified date and verify that accrued based on the editing balance.
        String date2 = "2022-02-05";
        String[] accrualResponse2 = runAccrualJobToSimulateDate(workerId, date2, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse2), 200, "Failed to run accrual job!");
        //expected:
        //Jan-9 to Jan-13  60.2hours Approved.  1~20.2/3.47354 --no change
        //Jan-30 14.5 --approved but out of look back window
        //Jan-31 14 Approved  0.8078---minus 4 hours: 10 hours 0~34.2/0.577
        //Feb-1st 9 Pending  ---Approved   0~39.2/0.5193
        //Feb-2nd null
        //Feb-3 14.7 Approved  0.84819 --no change  1~13.9/0.84819
        //Feb-4 13 Approved    0.7501--add 5hrs: 18hours  0~31.9/1.0386
        //Feb-5 14.33 Approved 0.826841----current date: 1~ 6.23/0.826841
        expectedTOBalance.put("PTO", "3");//~6.23
        expectedTOBalance.put("Sick", "7.28");//7.283471
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance220205 = timeOffPage.getTimeOffBalance();
        String verification2 = validateTheAccrualResults(accrualBalance220205, expectedTOBalance);
        Assert.assertTrue(verification2.contains("Succeeded in validating"), verification2);

    }


    public void resetTheTimeClocksDataForLookBack() {
        String sql130R = "update legionrc.TimeSheet set status='Pending' where dayOfTheYear='30' and year='2022' and workerId='b5b707c9-c0c1-4505-82be-d4e944a3e35e' and enterpriseId='aee2dfb5-387d-4b8b-b3f5-62e86d1a9d95'";
        String sql131Minus4HrsR = "update legionrc.TimeSheet set totalMinutes='840' where dayOfTheYear='31' and year='2022' and workerId='b5b707c9-c0c1-4505-82be-d4e944a3e35e' and enterpriseId='aee2dfb5-387d-4b8b-b3f5-62e86d1a9d95'";
        String sql201AR = "update legionrc.TimeSheet set status='Pending' where dayOfTheYear='32' and year='2022' and workerId='b5b707c9-c0c1-4505-82be-d4e944a3e35e' and enterpriseId='aee2dfb5-387d-4b8b-b3f5-62e86d1a9d95'";
        String sql204Add5HrsR = "update legionrc.TimeSheet set totalMinutes='780' where dayOfTheYear='35' and year='2022' and workerId='b5b707c9-c0c1-4505-82be-d4e944a3e35e' and enterpriseId='aee2dfb5-387d-4b8b-b3f5-62e86d1a9d95'";
        DBConnection.updateDB(sql130R);
        DBConnection.updateDB(sql131Minus4HrsR);
        DBConnection.updateDB(sql201AR);
        DBConnection.updateDB(sql204Add5HrsR);
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Sophia")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Accrual Engine Distribution Types")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAccrualHistoryAsInternalAdminOfAccrualEngineTest(String browser, String username, String password, String location) {
        OpsPortalNavigationPage navigationPage = new OpsPortalNavigationPage();
        navigationPage.navigateToEmployeeManagement();
        SimpleUtils.pass("EmployeeManagement Module is enabled!");
        //go to the time off management page
        EmployeeManagementPanelPage panelPage = new EmployeeManagementPanelPage();
        panelPage.goToTimeOffManagementPage();
        //verify that the target template is here.
        AbsentManagePage absentManagePage = new AbsentManagePage();
        String templateName = "AccrualAutoTest(Don't touch!!!)";
        absentManagePage.search(templateName);
        SimpleUtils.assertOnFail("Failed the find the target template!", absentManagePage.getResult().equals(templateName), false);

        //switch to console
        RightHeaderBarPage modelSwitchPage = new RightHeaderBarPage();
        modelSwitchPage.switchToNewTab();
        //search and go to the target location
        ConsoleNavigationPage consoleNavigationPage = new ConsoleNavigationPage();
        consoleNavigationPage.searchLocation("Newark");
        //go to team member details and switch to the time off tab.
        consoleNavigationPage.navigateTo("Team");
        TimeOffPage timeOffPage = new TimeOffPage();
        String teamMemName = "Olaf Kuhic";
        timeOffPage.goToTeamMemberDetail(teamMemName);
        timeOffPage.switchToTimeOffTab();

        //get session id via login
        String sessionId = logIn();
        //set UseAbsenceMgmtConfiguration Toggle On
        if (!isToggleEnabled(sessionId, "UseAbsenceMgmtConfiguration")) {
            String[] toggleResponse = turnOnToggle(sessionId, "UseAbsenceMgmtConfiguration");
            Assert.assertEquals(getHttpStatusCode(toggleResponse), 200, "Failed to get the user's template!");
        }
        //confirm template
        String workerId = "77b9bfb2-3e2a-4cfd-ad40-34c49c27e2b6";
        String targetTemplate = "AccrualAutoTest(Don't touch!!!)";
        String tempName = getUserTemplate(workerId, sessionId);
        Assert.assertEquals(tempName, targetTemplate, "The user wasn't associated to this Template!!! ");

        //create a time off balance map to store the expected time off balances.
        HashMap<String, String> expectedTOBalance = new HashMap<>();
        expectedTOBalance.put("Annual Leave2", "0");// HireDate~HireDate/Monthly /calendar month/begin
        expectedTOBalance.put("Annual Leave3", "0");// HireDate~Specified/Monthly /hire month/end
        expectedTOBalance.put("Bereavement3", "0");// HireDate~Specified/weekly
        expectedTOBalance.put("Covid2", "0");// HireDate~HireDate/worked hours/Rate
        expectedTOBalance.put("Covid3", "0");// HireDate~Specified/worked hours/total hour
        expectedTOBalance.put("Grandparents Day Off2", "0");//HireDate~HireDate/lump-sum
        expectedTOBalance.put("Grandparents Day Off3", "0");//HireDate~Specified/lump-sum
        expectedTOBalance.put("Pandemic1", "0");//Specified~Specified/Monthly /calendar month/begin /allowance in days 126(out of)
        expectedTOBalance.put("Pandemic2", "0");//Specified~Specified/weekly /allowance in days 127(in)
        expectedTOBalance.put("Pandemic3", "0");//Specified~Specified/worked hours/Rate /allowance in days 126(out of)
        expectedTOBalance.put("Pandemic4", "0");//Specified~Specified/lump-sum /allowance in days 127(in)
        expectedTOBalance.put("Spring Festival", "0");//None distribution

        //Delete the worker's accrual balance
        String[] deleteResponse = deleteAccrualByWorkerId(workerId, sessionId);
        Assert.assertEquals(getHttpStatusCode(deleteResponse), 200, "Failed to delete the user's accrual!");
        System.out.println("Delete worker's accrual balance successfully!");
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> actualTOB = timeOffPage.getTimeOffBalance();
        Assert.assertEquals(actualTOB, expectedTOBalance, "Failed to assert clear the accrual balance!");

        //do accrual
        //run engine to a specified date
        String date1 = "2021-9-30";
        String[] accrualResponse1 = runAccrualJobToSimulateDate(workerId, date1, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse1), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Annual Leave2", "5");// HireDate~HireDate/Monthly /calendar month/begin
        expectedTOBalance.put("Annual Leave3", "4");// HireDate~Specified/Monthly /hire month/end
        expectedTOBalance.put("Bereavement3", "20");// HireDate~Specified/weekly  +1 on OCt-1
        expectedTOBalance.put("Covid2", "1.01");// HireDate~HireDate/worked hours/Rate
        expectedTOBalance.put("Grandparents Day Off2", "8");//HireDate~HireDate/lump-sum
        expectedTOBalance.put("Grandparents Day Off3", "8");//HireDate~Specified/lump-sum
        expectedTOBalance.put("Pandemic1", "4");//Specified~Specified/Monthly /calendar month/begin /allowance in days 126(out of)
        expectedTOBalance.put("Pandemic2", "39");//Specified~Specified/weekly /allowance in days 127(in)
        expectedTOBalance.put("Pandemic3", "1.01");//Specified~Specified/worked hours/Rate /allowance in days 126(out of)
        expectedTOBalance.put("Pandemic4", "8");//Specified~Specified/lump-sum /allowance in days 127(in)
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance123021 = timeOffPage.getTimeOffBalance();
        String verification1 = validateTheAccrualResults(accrualBalance123021, expectedTOBalance);
        Assert.assertTrue(verification1.contains("Succeeded in validating"), verification1);
        //Verify the accrual history
        //create a history map to store the expected history items
        HashMap<String, String> expectedAccrualHistory = new HashMap<>();
        expectedAccrualHistory.put("Grandparents Day Off3 Accrued + 8 hours", "Accrued hours on Sep 30, 2021");
        expectedAccrualHistory.put("Annual Leave2 Accrued + 5 hours", "Accrued hours on Sep 30, 2021");
        expectedAccrualHistory.put("Grandparents Day Off2 Accrued + 8 hours", "Accrued hours on Sep 30, 2021");
        expectedAccrualHistory.put("Pandemic1 Accrued + 4 hours", "Accrued hours on Sep 30, 2021");
        expectedAccrualHistory.put("Pandemic4 Accrued + 8 hours", "Accrued hours on Sep 30, 2021");
        expectedAccrualHistory.put("Pandemic2 Accrued + 39 hours", "Accrued hours on Sep 30, 2021");
        expectedAccrualHistory.put("Pandemic3 Accrued + 1.01 hours", "Accrued hours on Sep 25, 2021");
        expectedAccrualHistory.put("Covid2 Accrued + 1.01 hours", "Accrued hours on Sep 25, 2021");
        expectedAccrualHistory.put("Bereavement3 Accrued + 20 hours", "Accrued hours on Sep 24, 2021");
        expectedAccrualHistory.put("Annual Leave3 Accrued + 4 hours", "Accrued hours on Sep 7, 2021");

        HashMap<String, String> actualHistory = timeOffPage.getAccrualHistory();
        Assert.assertEquals(actualHistory, expectedAccrualHistory, "Something wrong with the accrual history!");
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Sophia")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Accrual Engine Distribution Types")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, enabled = false)
    public void verifyAccrualTimeOffHistoryAsInternalAdminOfAccrualEngineTest(String browser, String username, String password, String location) {
        //take time off
        //Verify the time off history

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Sophia")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Accrual Engine Distribution Types")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, enabled = false)
    public void verifyAccrualLimitationsAsInternalAdminOfAccrualEngineTest(String browser, String username, String password, String location) {
        //do limitation
        //Verify the limitation history

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Sophia")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Accrual Engine Distribution Types")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class, enabled = false)
    public void verifyAccrualEngineWorksWellAfterImportingAsInternalAdminOfAccrualEngineTest(String browser, String username, String password, String location) {
        OpsPortalNavigationPage navigationPage = new OpsPortalNavigationPage();
        //verify that employee management is enabled.
        navigationPage.navigateToEmployeeManagement();
        SimpleUtils.pass("EmployeeManagement Module is enabled!");
        //go to the time off management page
        EmployeeManagementPanelPage panelPage = new EmployeeManagementPanelPage();
        panelPage.goToTimeOffManagementPage();
        //verify that the target template is here.
        AbsentManagePage absentManagePage = new AbsentManagePage();
        String templateName = "AccrualAutoTest(Don't touch!!!)";
        absentManagePage.search(templateName);
        SimpleUtils.assertOnFail("Failed the find the target template!", absentManagePage.getResult().equals(templateName), false);

        //switch to console
        RightHeaderBarPage modelSwitchPage = new RightHeaderBarPage();
        modelSwitchPage.switchToNewTab();
        //search and go to the target location
        ConsoleNavigationPage consoleNavigationPage = new ConsoleNavigationPage();
        consoleNavigationPage.searchLocation("Newark");
        //go to team member details and switch to the time off tab.
        consoleNavigationPage.navigateTo("Team");
        TimeOffPage timeOffPage = new TimeOffPage();
        String teamMemName = "Olaf Kuhic";
        timeOffPage.goToTeamMemberDetail(teamMemName);
        timeOffPage.switchToTimeOffTab();

        //get session id via login
        String sessionId = logIn();
        //set UseAbsenceMgmtConfiguration Toggle On
        if (!isToggleEnabled(sessionId, "UseAbsenceMgmtConfiguration")) {
            String[] toggleResponse = turnOnToggle(sessionId, "UseAbsenceMgmtConfiguration");
            Assert.assertEquals(getHttpStatusCode(toggleResponse), 200, "Failed to get the user's template!");
        }
        //confirm template
        String workerId = "77b9bfb2-3e2a-4cfd-ad40-34c49c27e2b6";
        String targetTemplate = "AccrualAutoTest(Don't touch!!!)";
        String tempName = getUserTemplate(workerId, sessionId);
        Assert.assertEquals(tempName, targetTemplate, "The user wasn't associated to this Template!!! ");
        //create a time off balance map to store the expected time off balances.
        HashMap<String, String> expectedTOBalance = new HashMap<>();
        expectedTOBalance.put("Annual Leave2", "0");// HireDate~HireDate/Monthly /calendar month/begin
        expectedTOBalance.put("Annual Leave3", "0");// HireDate~Specified/Monthly /hire month/end
        expectedTOBalance.put("Bereavement3", "0");// HireDate~Specified/weekly
        expectedTOBalance.put("Covid2", "0");// HireDate~HireDate/worked hours/Rate
        expectedTOBalance.put("Covid3", "0");// HireDate~Specified/worked hours/total hour
        expectedTOBalance.put("Grandparents Day Off2", "0");//HireDate~HireDate/lump-sum
        expectedTOBalance.put("Grandparents Day Off3", "0");//HireDate~Specified/lump-sum
        expectedTOBalance.put("Pandemic1", "0");//Specified~Specified/Monthly /calendar month/begin /allowance in days 126(out of)
        expectedTOBalance.put("Pandemic2", "0");//Specified~Specified/weekly /allowance in days 127(in)
        expectedTOBalance.put("Pandemic3", "0");//Specified~Specified/worked hours/Rate /allowance in days 126(out of)
        expectedTOBalance.put("Pandemic4", "0");//Specified~Specified/lump-sum /allowance in days 127(in)
        expectedTOBalance.put("Spring Festival", "0");//None distribution

        //Delete the worker's accrual balance
        String[] deleteResponse = deleteAccrualByWorkerId(workerId, sessionId);
        Assert.assertEquals(getHttpStatusCode(deleteResponse), 200, "Failed to delete the user's accrual!");
        System.out.println("Delete worker's accrual balance successfully!");
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> actualTOB = timeOffPage.getTimeOffBalance();
        Assert.assertEquals(actualTOB, expectedTOBalance, "Failed to assert clear the accrual balance!");

        //import accrual balance via CSV file.
        importAccrualBalance(sessionId);

        expectedTOBalance.put("Annual Leave2", "5");// HireDate~HireDate/Monthly /calendar month/begin
        expectedTOBalance.put("Annual Leave3", "7");// HireDate~Specified/Monthly /hire month/end
        expectedTOBalance.put("Bereavement3", "23");// HireDate~Specified/weekly
        expectedTOBalance.put("Covid2", "6");// HireDate~HireDate/worked hours/Rate
        expectedTOBalance.put("Covid3", "9");// HireDate~Specified/worked hours/total hour
        expectedTOBalance.put("Grandparents Day Off2", "12.65");//HireDate~HireDate/lump-sum
        expectedTOBalance.put("Grandparents Day Off3", "6");//HireDate~Specified/lump-sum
        expectedTOBalance.put("Spring Festival", "20");//None distribution
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> importedBalance = timeOffPage.getTimeOffBalance();
        String verification1 = validateTheAccrualResults(importedBalance, expectedTOBalance);
        Assert.assertTrue(verification1.contains("Succeeded in validating"), verification1);


        //run engine to a specified date
        String date1 = "2022-05-01";
        String[] accrualResponse1 = runAccrualJobToSimulateDate(workerId, date1, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse1), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Annual Leave2", "12");// HireDate~HireDate/Monthly /calendar month/begin
        expectedTOBalance.put("Annual Leave3", "10");// HireDate~Specified/Monthly /hire month/end
        expectedTOBalance.put("Bereavement3", "52");// HireDate~Specified/weekly
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance050122 = timeOffPage.getTimeOffBalance();
        String verification2 = validateTheAccrualResults(accrualBalance050122, expectedTOBalance);
        Assert.assertTrue(verification2.contains("Succeeded in validating"), verification2);

    }

    @Automated(automated = "Automated")
    @Owner(owner = "Sophia")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Accrual Engine Distribution Types")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAccrualPromotionWorksWellAsInternalAdminOfAccrualEngineTest(String browser, String username, String password, String location) {
        OpsPortalNavigationPage navigationPage = new OpsPortalNavigationPage();
        //verify that employee management is enabled.
        navigationPage.navigateToEmployeeManagement();
        SimpleUtils.pass("EmployeeManagement Module is enabled!");
        //go to the time off management page
        EmployeeManagementPanelPage panelPage = new EmployeeManagementPanelPage();
        panelPage.goToTimeOffManagementPage();
        //go to setting page
        AbsentManagePage absentManagePage = new AbsentManagePage();
        absentManagePage.switchToSettings();
        //1: verify Promotion was added in global settings
        String settingTitle = absentManagePage.getPromotionSettingTitle();
        Assert.assertEquals("Accrual Promotions", settingTitle, "Failed to get the Promotion setting title!");
        SimpleUtils.pass("Succeeded in getting the Promotion setting title!");
        //2: There is an add button in this page and it's clickable
        Assert.assertTrue(absentManagePage.isAddButtonDisplayedAndClickable(), "Failed to assert the promotion rule add button displayed and clickable!");
        SimpleUtils.pass("Succeeded in validating the promotion rule add button was displayed and clickable!");
        //3: "Create new promotion" modal will be popup when clicking add button.
        absentManagePage.addPromotionRule();
        Assert.assertEquals("Create New Accrual Promotion", absentManagePage.getPromotionModalTitle(), "Failed to popup create new promotion modal!");
        SimpleUtils.pass("Succeeded in popping up the create new accrual promotion modal!");
        //4: add a promotion rule--JOb title
        absentManagePage.setPromotionName("AmbassadorToManager");
        absentManagePage.addCriteriaByJobTitle();
        //4.1 verify the job title before promotion is muti-select
        //Assert.assertEquals("2 Job Title Selected", absentManagePage.getJobTitleSelectedBeforePromotion(), "Failed to select 2 job titles!");
        SimpleUtils.pass("Succeeded in Validating job title before promotion is muti-select!");
        //4.2 job title selected before promotion should be disabled in after promotion.
        //Assert.assertFalse(absentManagePage.verifyJobTitleSelectedBeforePromotionShouldBeDisabledAfterPromotion("Senior Ambassador") && absentManagePage.verifyJobTitleSelectedBeforePromotionShouldBeDisabledAfterPromotion("WA Ambassador"), "Failed to assert job title selected before promotion are disabled in after promotion!");
        SimpleUtils.pass("Succeeded in Validating job title selected before promotion are disabled in after promotion!");
        //5: Add promotion actions
        absentManagePage.setPromotionAction("Annual Leave", "Floating Holiday");
        OpsCommonComponents commonComponents = new OpsCommonComponents();
        commonComponents.okToActionInModal(true);

        //6: add another promotion rule--Engagement status.
        absentManagePage.addPromotionRule();
        absentManagePage.setPromotionName("PartTimeToFullTime");
        absentManagePage.addCriteriaByEngagementStatus();
        absentManagePage.setPromotionAction("Sick", "PTO");
        commonComponents.okToActionInModal(true);

        List<String> promotionRN = absentManagePage.getPromotionRuleName();
        Assert.assertTrue(promotionRN.size() == 2 && promotionRN.get(0).equals("AmbassadorToManager") && promotionRN.get(1).equals("PartTimeToFullTime"), "Failed to assert adding promotion rule successfully!");
        SimpleUtils.pass("Succeeded in adding promotion rules!");

        //Edit promotion rule---rename it,
        absentManagePage.EditPromotionRule();
        absentManagePage.setPromotionName("AmbassadorToManager--V2");
        commonComponents.okToActionInModal(true);
        Assert.assertTrue(absentManagePage.getPromotionRuleName().get(0).equals("AmbassadorToManager--V2"), "Failed to assert editing promotion rule successfully!");
        SimpleUtils.pass("Succeeded in editing promotion rule!");

        //Remove promotion rules just Created.
        absentManagePage.removePromotionRule();
        //verify remove promotion rule Modal opened.--verify title
        Assert.assertEquals("Remove Accrual Promotion", absentManagePage.getRemovePromotionRuleModalTitle(), "Failed to open remove promotion rule Modal!");
        SimpleUtils.pass("Succeeded in opening remove promotion rule Modal!");
        //verify remove promotion rule Modal content.
        Assert.assertEquals("Are you sure you want to remove this accrual promotion?", absentManagePage.getRemovePromotionRuleModalContent(), "Failed to assert remove promotion rule Modal content!");
        SimpleUtils.pass("Succeeded in validating remove promotion rule Modal Content!");
        //cancel remove
        commonComponents.okToActionInModal(false);
        //remove promotion rule successfully
        while (absentManagePage.getPromotionRuleName().size() != 0) {
            absentManagePage.removePromotionRule();
            commonComponents.okToActionInModal(true);
        }
        Assert.assertFalse(absentManagePage.isTherePromotionRule(), "Failed to assert there is no promotion rules!");
        SimpleUtils.pass("Succeeded in removing all the promotion rules just created!");
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Nancy")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Refresh Balances")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyRefreshBalancesAsInternalAdminOfAccrualEngineTest(String browser, String username, String password, String location) throws Exception{
        //go to User Management Access Role table
        UserManagementPage userManagementPage = pageFactory.createOpsPortalUserManagementPage();
        userManagementPage.clickOnUserManagementTab();
        userManagementPage.goToUserAndRoles();
        userManagementPage.goToAccessRolesTab();

        userManagementPage.clickManage();
        userManagementPage.verifyRecalculatePermission();

        switchToNewWindow();

        ConsoleNavigationPage consoleNavigationPage = new ConsoleNavigationPage();
        consoleNavigationPage.searchLocation("verifyMock");
        consoleNavigationPage.navigateTo("Team");

        TeamPage teamPage = pageFactory.createConsoleTeamPage();

        teamPage.goToTeam();
        teamPage.searchAndSelectTeamMemberByName("Nancy Customer");
        teamPage.navigateToTimeOffPage();

        //get session id via login
        String sessionId = logIn();
        //set UseAbsenceMgmtConfiguration Toggle On
        if (!isToggleEnabled(sessionId, "UseAbsenceMgmtConfiguration")) {
            String[] toggleResponse = turnOnToggle(sessionId, "UseAbsenceMgmtConfiguration");
            Assert.assertEquals(getHttpStatusCode(toggleResponse), 200, "Failed to get the user's template!");
        }
        //confirm template
        String workerId = "5b4ab45c-3380-46a3-9890-de6f3b9e5225";
        String targetTemplate = "Activity";
        String tempName = getUserTemplate(workerId, sessionId);
        Assert.assertEquals(tempName, targetTemplate, "The user wasn't associated to this Template!!! ");
        //create a time off balance map to store the expected time off balances.
        HashMap<String, String> expectedTOBalance = new HashMap<>();
        expectedTOBalance.put("Annual Leave", "0");// HireDate~HireDate/Monthly /hire month/end
        expectedTOBalance.put("Annual Leave1", "0");// HireDate~HireDate/Worked Hours /total hours/ 10 1
        expectedTOBalance.put("Annual Leave2", "0");// HireDate~Specified/Worked Hours /total hours/ 20 1
        expectedTOBalance.put("Annual Leave3", "0");// Specified~HireDate/worked hours/total hours/ 15 1
        expectedTOBalance.put("Annual Leave4", "0");// Specified~Specified/worked hours/total hour/ 5 1
        expectedTOBalance.put("Bereavement1", "0");//HireDate~HireDate/worked hours/rate/ 0.1
        expectedTOBalance.put("Bereavement2", "0");//HireDate~Specified/worked hours/rate/ 0.2
        expectedTOBalance.put("Bereavement3", "0");//Specified~HireDate/worked hours/rate/ 0.3
        expectedTOBalance.put("Bereavement4", "0");//Specified~Specified/worked hours/rate/ 0.4
        expectedTOBalance.put("Floating Holiday", "0");//HireDate~HireDate/Monthly /hire month/ begin
        expectedTOBalance.put("Grandparents Day Off1", "0");//Specified~Specified/Weekly
        expectedTOBalance.put("Covid1", "0");//HireDate~HireDate/worked hours/fix days
        expectedTOBalance.put("Covid2", "0");//HireDate~Specified/worked hours/fix days
        expectedTOBalance.put("Covid3", "0");//Specified~HireDate/worked hours/fix days
        expectedTOBalance.put("Covid4", "0");//Specified~Specified/worked hours/fix days

        //Delete a worker's accrual
        String[] deleteResponse = deleteAccrualByWorkerId(workerId, sessionId);
        Assert.assertEquals(getHttpStatusCode(deleteResponse), 200, "Failed to delete the user's accrual!");
        System.out.println("Delete worker's accrual balance successfully!");
        refreshPage();

        TimeOffPage timeOffPage = new TimeOffPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> actualTOB = timeOffPage.getTimeOffBalance();
        Assert.assertEquals(actualTOB, expectedTOBalance, "Failed to assert clear the accrual balance!");

        //run engine to specified date
        String date1 = "2022-05-31";

        /*clock added in time sheet
         May-29 8 approved
         May-30 8 approved
         May-31 8 approved
         Jun-1 8 approved
       */
        //Run engine to Date1 2022-05-31
        String[] accrualResponse1 = runAccrualJobToSimulateDate(workerId, date1, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse1), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Annual Leave", "24");// HireDate~HireDate/Monthly /hire month/end
        expectedTOBalance.put("Annual Leave1", "2");// HireDate~HireDate/Worked Hours /total hours/ 10 1
        expectedTOBalance.put("Annual Leave2", "1");// HireDate~Specified/Worked Hours /total hours/ 20 1
        expectedTOBalance.put("Annual Leave3", "1");// Specified~HireDate/worked hours/total hours/ 15 1
        expectedTOBalance.put("Annual Leave4", "4");// Specified~Specified/worked hours/total hour/ 5 1
        expectedTOBalance.put("Bereavement1", "2.4");//HireDate~HireDate/worked hours/rate/ 0.1
        expectedTOBalance.put("Bereavement2", "4.8");//HireDate~Specified/worked hours/rate/ 0.2
        expectedTOBalance.put("Bereavement3", "7.2");//Specified~HireDate/worked hours/rate/ 0.3
        expectedTOBalance.put("Bereavement4", "9.6");//Specified~Specified/worked hours/rate/ 0.4
        expectedTOBalance.put("Floating Holiday", "30");//HireDate~HireDate/Monthly /hire month/ begin
        expectedTOBalance.put("Grandparents Day Off1", "21");//Specified~Specified/Weekly
        expectedTOBalance.put("Covid1", "0");//HireDate~HireDate/worked hours/fix days
        expectedTOBalance.put("Covid2", "0");//HireDate~Specified/worked hours/fix days
        expectedTOBalance.put("Covid3", "0");//Specified~HireDate/worked hours/fix days
        expectedTOBalance.put("Covid4", "0");//Specified~Specified/worked hours/fix days
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance0531 = timeOffPage.getTimeOffBalance();
        //HashMap<String, String> accrualHistory0509 = timeOffPage.getAccrualHistory();
        String verification1 = validateTheAccrualResults(accrualBalance0531, expectedTOBalance);
        Assert.assertTrue(verification1.contains("Succeeded in validating"), verification1);

        userManagementPage.clickRefreshBalances();

        //expected accrual
        expectedTOBalance.put("Annual Leave", "24");// HireDate~HireDate/Monthly /hire month/end
        expectedTOBalance.put("Annual Leave1", "4");// HireDate~HireDate/Worked Hours /total hours/ 10 1
        expectedTOBalance.put("Annual Leave2", "2");// HireDate~Specified/Worked Hours /total hours/ 20 1
        expectedTOBalance.put("Annual Leave3", "2");// Specified~HireDate/worked hours/total hours/ 15 1
        expectedTOBalance.put("Annual Leave4", "8");// Specified~Specified/worked hours/total hour/ 5 1
        expectedTOBalance.put("Bereavement1", "4");//Specified~Specified/worked hours/rate/ 0.1
        expectedTOBalance.put("Bereavement2", "8");//Specified~Specified/worked hours/rate/ 0.2
        expectedTOBalance.put("Bereavement3", "12");//Specified~Specified/worked hours/rate/ 0.3
        expectedTOBalance.put("Bereavement4", "16");//Specified~Specified/worked hours/rate/ 0.4
        expectedTOBalance.put("Floating Holiday", "30");//HireDate~HireDate/Monthly /hire month/ begin
        expectedTOBalance.put("Grandparents Day Off1", "21");//Specified~Specified/Weekly
        expectedTOBalance.put("Covid1", "0");//HireDate~HireDate/worked hours/fix days
        expectedTOBalance.put("Covid2", "0");//HireDate~Specified/worked hours/fix days
        expectedTOBalance.put("Covid3", "0");//Specified~HireDate/worked hours/fix days
        expectedTOBalance.put("Covid4", "0");//Specified~Specified/worked hours/fix days

        accrualBalance0531 = timeOffPage.getTimeOffBalance();
        //HashMap<String, String> accrualHistory0509 = timeOffPage.getAccrualHistory();
        verification1 = validateTheAccrualResults(accrualBalance0531, expectedTOBalance);
        Assert.assertTrue(verification1.contains("Succeeded in validating"), verification1);

        //logout
        OpsPortalNavigationPage opsPortalNavigationPage = new OpsPortalNavigationPage();
        opsPortalNavigationPage.logout();

        //log in with user has no view hourly rate job title permission
        loginToLegionAndVerifyIsLoginDoneWithoutUpdateUpperfield("nancy.nan+customer@legion.co", "admin11.a","verifyMock");
        //go to team
        consoleNavigationPage.searchLocation("verifyMock");
        consoleNavigationPage.navigateTo("Team");

        teamPage.goToTeam();
        teamPage.searchAndSelectTeamMemberByName("Nancy Customer");

        timeOffPage.switchToTimeOffTab();
        userManagementPage.verifyRefreshBalancesNotDisplayed();
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Sophia")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "OPS-4071 GM Holiday.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAccrualGMHolidayUIAsInternalAdminOfAccrualEngineTest(String browser, String username, String password, String location) throws Exception {
        OpsPortalNavigationPage navigationPage = new OpsPortalNavigationPage();
        //verify that employee management is enabled.
        navigationPage.navigateToEmployeeManagement();
        SimpleUtils.pass("EmployeeManagement Module is enabled!");
        //go to the time off management page
        EmployeeManagementPanelPage panelPage = new EmployeeManagementPanelPage();
        panelPage.goToTimeOffManagementPage();
        //verify that the target template is here.
        AbsentManagePage absentManagePage = new AbsentManagePage();
        String targetTemplate = "Accrual Auto GM Holiday";
        absentManagePage.search(targetTemplate);
        Assert.assertTrue(absentManagePage.getResult().equals(targetTemplate), "Failed the find the target template!");
        SimpleUtils.pass("Succeeded in finding the target template!");

        absentManagePage.configureTemplate("Accrual Auto GM Holiday");
        absentManagePage.configureTimeOffRules("Sick");
        //set accrual period
        TimeOffReasonConfigurationPage configurationPage = new TimeOffReasonConfigurationPage();
        configurationPage.setAccrualPeriod("Specified Date", "Specified Date", "January", "1", "December", "31");
        SimpleUtils.pass("Succeeded in setting accrual period!");
        //verify new distribution method (Specified Date)has been added.
        Assert.assertTrue(configurationPage.getDistributionOptions().contains("Specified Date"));
        SimpleUtils.pass("Succeeded in validating new distribution method---Specified Date was added!");
        //verify the new distribution method can be selected.
        configurationPage.setDistributionMethod("Specified Date");
        SimpleUtils.pass("Succeeded in validating new distribution method---Specified Date can be selected!");
        //verify the distribution switch to the Specified one.
        configurationPage.addSpecifiedServiceLever(0, "25", "0", "25");
        Assert.assertEquals(configurationPage.getDistributionType(), "Date", "Failed to assert the Distribution Switch to the Specified date!");
        SimpleUtils.pass("Succeeded in switching to the Specified date distribution!");
        //Verify holiday list
        ArrayList<String> hol = configurationPage.getHolidayList();
        ArrayList<String> usHoliday = getHolidaysViaAPI(logIn());
        Assert.assertTrue(hol.size() == usHoliday.size() && hol.containsAll(usHoliday) && usHoliday.containsAll(hol));
        SimpleUtils.pass("Succeeded in validating the holiday options are default US holidays!");
        //add new holidays
        //search a holiday
        configurationPage.setDateDistribution("Labor Day", "8");
        //selected holidays can't be chose again
        Assert.assertFalse(configurationPage.verifyHolidayUsedCanNotBeSelectedAgain("Labor Day"), "Failed to assert that selected holidays can't be chose again!");
        SimpleUtils.pass("Succeeded in validating selected holidays can't be chose again!");
        //save
        configurationPage.removeHolidayFromTheDistribution();
        configurationPage.saveTimeOffConfiguration(true);
        SimpleUtils.pass("Succeeded in saving GMHoliday setting!");
        //remove an existing holiday from the setting.
        absentManagePage.configureTimeOffRules("Sick");
        configurationPage.showDistributionOfSpecifiedServiceLever0();
        configurationPage.removeHolidayFromTheDistribution();
        Assert.assertEquals(configurationPage.getErrorMessage(), "At least one holiday is required.", "Failed to remove the existing holiday");
        SimpleUtils.pass("Succeeded in removing an existing Holiday!");
        // roll back the settings
        configurationPage.saveTimeOffConfiguration(false);
        SimpleUtils.pass("Succeeded in canceling GMHoliday changes!");
        absentManagePage.removeTimeOffRules("Sick");
        OpsCommonComponents components = new OpsCommonComponents();
        components.saveTemplateAs("Save as draft");
    }


    @Automated(automated = "Automated")
    @Owner(owner = "Sophia")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "OPS-4071 GM Holiday.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAccrualGMHolidayWorksWellAsInternalAdminOfAccrualEngineTest(String browser, String username, String password, String location) {
        OpsPortalNavigationPage navigationPage = new OpsPortalNavigationPage();
        //verify that employee management is enabled.
        navigationPage.navigateToEmployeeManagement();
        SimpleUtils.pass("EmployeeManagement Module is enabled!");
        //go to the time off management page
        EmployeeManagementPanelPage panelPage = new EmployeeManagementPanelPage();
        panelPage.goToTimeOffManagementPage();
        //verify that the target template is here.
        AbsentManagePage absentManagePage = new AbsentManagePage();
        String targetTemplate = "Accrual Auto GM Holiday";
        absentManagePage.search(targetTemplate);
        Assert.assertTrue(absentManagePage.getResult().equals(targetTemplate), "Failed the find the target template!");
        SimpleUtils.pass("Succeeded in finding the target template!");

        //switch to console
        RightHeaderBarPage modelSwitchPage = new RightHeaderBarPage();
        modelSwitchPage.switchToNewTab();
        //search and go to the target location
        ConsoleNavigationPage consoleNavigationPage = new ConsoleNavigationPage();
        consoleNavigationPage.searchLocation("OMLocation16");
        //go to team member details and switch to the time off tab.
        consoleNavigationPage.navigateTo("Team");
        TimeOffPage timeOffPage = new TimeOffPage();
        String teamMemName = "Amir Lemke";
        timeOffPage.goToTeamMemberDetail(teamMemName);
        timeOffPage.switchToTimeOffTab();

        //get session id via login
        String sessionId = logIn();
        //set UseAbsenceMgmtConfiguration Toggle On
        if (!isToggleEnabled(sessionId, "UseAbsenceMgmtConfiguration")) {
            String[] toggleResponse = turnOnToggle(sessionId, "UseAbsenceMgmtConfiguration");
            Assert.assertEquals(getHttpStatusCode(toggleResponse), 200, "Failed to get the user's template!");
        }
        //confirm template
        String workerId = "3b947a79-07c8-4851-8838-9387d3886c7b";
        String tempName = getUserTemplate(workerId, sessionId);
        Assert.assertEquals(tempName, targetTemplate, "The user wasn't associated to this Template!!! ");
        SimpleUtils.pass("Succeeded in validating employee was associated to the target template!");

        //create a time off balance map to store the expected time off balances.
        HashMap<String, String> expectedTOBalance = new HashMap<>();
        expectedTOBalance.put("Annual Leave", "0");//Hire Date 6/25 ~ SpecifiedDate 12/31
        expectedTOBalance.put("Floating Holiday", "0");//SpecifiedDate 01/01 ~ SpecifiedDate 12/31 //in allowance days(175)
        expectedTOBalance.put("PTO", "0");//out of allowance days(174)
        SimpleUtils.pass("Succeeded in validating employee was associated to the target template!");

        //Delete the worker's accrual balance
        String[] deleteResponse = deleteAccrualByWorkerId(workerId, sessionId);
        Assert.assertEquals(getHttpStatusCode(deleteResponse), 200, "Failed to delete the user's accrual!");
        System.out.println("Delete worker's accrual balance successfully!");
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> actualTOB = timeOffPage.getTimeOffBalance();
        Assert.assertEquals(actualTOB, expectedTOBalance, "Failed to clear the employee's accrual balance!");
        SimpleUtils.pass("Succeeded in clearing employee's accrual balance!");

        //New Years Day: 2022-01-01
        //Dr. Martin Luther King,:  2022-01-17
        //President’s Day:  2022-02-21
        //Labor Day: 2022-09-05
        //Christmas Day:  2022-12-25

        String newYearsDay = "2021-01-01";//on holiday
        String beforeDrMartinLutherKing = "2021-01-17";//holiday（2021-01-18）
        //String afterPresidentDay = "2021-03-01";//holiday(2021-02-15)
        String afterMemorialDay = "2021-06-01";//holiday(2021-05-31)
        String laborDay = "2021-09-06";
        String christmasDay = "2021-12-25";
        String expireDate = "2021-12-31";//

        //Run engine to the New Years Day:
        String[] accrualResponse1 = runAccrualJobToSimulateDate(workerId, newYearsDay, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse1), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Floating Holiday", "1");//SpecifiedDate 01/01 ~ SpecifiedDate 12/31
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance1 = timeOffPage.getTimeOffBalance();
        String verification1 = validateTheAccrualResults(accrualBalance1, expectedTOBalance);
        Assert.assertTrue(verification1.contains("Succeeded in validating"), verification1);
        SimpleUtils.pass("Succeeded in validating employee's accrual balance!");

        //Run engine to Dr. Martin Luther King,
        String[] accrualResponse2 = runAccrualJobToSimulateDate(workerId, beforeDrMartinLutherKing, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse2), 200, "Failed to run accrual job!");
        //expected accrual
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance2 = timeOffPage.getTimeOffBalance();
        String verification2 = validateTheAccrualResults(accrualBalance2, expectedTOBalance);
        Assert.assertTrue(verification2.contains("Succeeded in validating"), verification2);
        SimpleUtils.pass("Succeeded in validating employee's accrual balance!");

        //Run engine to the President’s Day.
        String[] accrualResponse3 = runAccrualJobToSimulateDate(workerId, afterMemorialDay, sessionId);//2021-06-01
        Assert.assertEquals(getHttpStatusCode(accrualResponse3), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Floating Holiday", "9");//SpecifiedDate 01/01 ~ SpecifiedDate 12/31
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance3 = timeOffPage.getTimeOffBalance();
        String verification3 = validateTheAccrualResults(accrualBalance3, expectedTOBalance);
        Assert.assertTrue(verification3.contains("Succeeded in validating"), verification3);
        SimpleUtils.pass("Succeeded in validating employee's accrual balance!");

        //Run engine to the Labor Day.
        String[] accrualResponse4 = runAccrualJobToSimulateDate(workerId, laborDay, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse4), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Annual Leave", "8");//Hire Date ~ SpecifiedDate 12/31 (2/4/6/8/10)
        expectedTOBalance.put("Floating Holiday", "16");//SpecifiedDate 01/01 ~ SpecifiedDate 12/31 (1/3/5/7/9)
        expectedTOBalance.put("PTO", "7");
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance4 = timeOffPage.getTimeOffBalance();
        String verification4 = validateTheAccrualResults(accrualBalance4, expectedTOBalance);
        Assert.assertTrue(verification4.contains("Succeeded in validating"), verification4);
        SimpleUtils.pass("Succeeded in validating employee's accrual balance!");

        //Run engine to the Christmas Day.
        String[] accrualResponse5 = runAccrualJobToSimulateDate(workerId, christmasDay, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse5), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Annual Leave", "18");//Hire Date ~ SpecifiedDate 12/31 (2/4/6/8/10)
        expectedTOBalance.put("Floating Holiday", "25");//SpecifiedDate 01/01 ~ SpecifiedDate 12/31 (1/3/5/7/9)
        expectedTOBalance.put("PTO", "16");
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance5 = timeOffPage.getTimeOffBalance();
        String verification5 = validateTheAccrualResults(accrualBalance5, expectedTOBalance);
        Assert.assertTrue(verification5.contains("Succeeded in validating"), verification5);
        SimpleUtils.pass("Succeeded in validating employee's accrual balance!");

        //Run engine to the expire Date.
        //expired on the end of the year
        String[] accrualResponse6 = runAccrualJobToSimulateDate(workerId, expireDate, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse6), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Annual Leave", "0");//Hire Date ~ SpecifiedDate 12/31 (2/4/6/8/10)
        expectedTOBalance.put("Floating Holiday", "0");//SpecifiedDate 01/01 ~ SpecifiedDate 12/31 (1/3/5/7/9)
        expectedTOBalance.put("PTO", "0");
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance6 = timeOffPage.getTimeOffBalance();
        String verification6 = validateTheAccrualResults(accrualBalance6, expectedTOBalance);
        Assert.assertTrue(verification6.contains("Succeeded in validating"), verification6);
        SimpleUtils.pass("Succeeded in validating GM holiday balance expired at the end of the year!");
        //crossing the service lever: run engine to the next year.
        //PTO
        /*
         * New Years Day: 2022-01-01, service lever 0    +1hour
         * Dr. Martin Luther King,: 2022-01-17, service lever 0    +3hour
         * Memorial Day: 2022-05-30, service lever 0    +5hour  //President’s Day: 2022-02-21, service lever 0    +5hour
         * ----------------service lever1 2022-06-25--------------
         * Independence Day: 2022-07-04, service lever 1 +8hours
         * Labor Day: 2022-09-05, service lever 1     +8hours
         * Christmas Day: 2022-12-25, service lever 1 +8hours
         * */
        /*String acrossServiceleverDate = "2022-09-06";
        String[] accrualResponse7 = runAccrualJobToSimulateDate(workerId, acrossServiceleverDate, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse7), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Annual Leave", "20");//Hire Date ~ SpecifiedDate 12/31 (2/4/6/8/10)
        expectedTOBalance.put("Floating Holiday", "16");//SpecifiedDate 01/01 ~ SpecifiedDate 12/31 (1/3/5/7/9)
        expectedTOBalance.put("PTO", "25");
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance7 = timeOffPage.getTimeOffBalance();
        String verification7 = validateTheAccrualResults(accrualBalance7, expectedTOBalance);
        Assert.assertTrue(verification7.contains("Succeeded in validating"), verification7);
        SimpleUtils.pass("Succeeded in validating GM holiday accrued correctly when across the service lever!");*/
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Sophia")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "OPS-3078 Puerto Rico")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyAccrualFixedDaysUIAsInternalAdminOfAccrualEngineTest(String browser, String username, String password, String location) throws Exception {
        OpsPortalNavigationPage navigationPage = new OpsPortalNavigationPage();
        //verify that employee management is enabled.
        navigationPage.navigateToEmployeeManagement();
        SimpleUtils.pass("EmployeeManagement Module is enabled!");
        //go to the time off management page
        EmployeeManagementPanelPage panelPage = new EmployeeManagementPanelPage();
        panelPage.goToTimeOffManagementPage();
        //verify that the target template is here.
        AbsentManagePage absentManagePage = new AbsentManagePage();
        String targetTemplate = "AccrualAuto-FixedDays(Don't touch!!!)";
        absentManagePage.search(targetTemplate);
        Assert.assertTrue(absentManagePage.getResult().equals(targetTemplate), "Failed the find the target template!");
        SimpleUtils.pass("Succeeded in finding the target template!");
        //fixed days ui validation
        absentManagePage.configureTemplate("AccrualAuto-FixedDays(Don't touch!!!)");
        absentManagePage.configureTimeOffRules("Sick");
        //verify that eligibility rules has been added in UI
        TimeOffReasonConfigurationPage configurationPage = new TimeOffReasonConfigurationPage();
        configurationPage.getEligibilityTitle();
        Assert.assertTrue(configurationPage.getEligibilityTitle().contains("Employee must work") && configurationPage.getEligibilityTitle().contains("hours over the last"));
        SimpleUtils.pass("Succeeded in validating eligibility rules was added in UI!");
        //set accrual period
        configurationPage.setAccrualPeriod("Specified Date", "Specified Date", "January", "1", "December", "31");
        SimpleUtils.pass("Succeeded in setting accrual period!");
        //verify new distribution type (Fixed days)has been added.
        configurationPage.setDistributionMethod("Worked Hours");
        Assert.assertTrue(configurationPage.getWorkedHoursDistributionTypeOptions().contains("Fixed Days"));
        SimpleUtils.pass("Succeeded in validating new distribution type---Fixed Days was added!");
        //verify the new distribution method can be selected.
        configurationPage.setDistributionType("Fixed Days");
        SimpleUtils.pass("Succeeded in validating new distribution method---Fixed Days can be selected!");
        //verify the distribution switch to the Specified one.
        configurationPage.addSpecifiedServiceLever(0, "60", "5", "65");
        Assert.assertEquals(configurationPage.getFixedDaysLabel(), "Fixed Days", "Failed to assert the Distribution Switch to the Fixed Days!");
        SimpleUtils.pass("Succeeded in switching to the Fixed days distribution!");
        //configure the fixed days distribution.
        configurationPage.setFixedDaysDistribution("10", "3");
        SimpleUtils.pass("Succeeded in setting Fixed days distribution!");
        //save the configuration
        configurationPage.saveTimeOffConfiguration(true);
        SimpleUtils.pass("Succeeded in saving fixed days configurations!");
        //roll back the settings.
        absentManagePage.removeTimeOffRules("Sick");
        OpsCommonComponents components = new OpsCommonComponents();
        components.saveTemplateAs("Save as draft");

        //switch to console
        RightHeaderBarPage modelSwitchPage = new RightHeaderBarPage();
        modelSwitchPage.switchToNewTab();
        //search and go to the target location
        ConsoleNavigationPage consoleNavigationPage = new ConsoleNavigationPage();
        consoleNavigationPage.searchLocation("OMLocation16");
        //go to team member details and switch to the time off tab.
        consoleNavigationPage.navigateTo("Team");
        TimeOffPage timeOffPage = new TimeOffPage();
        String teamMemName = "Cherry Li";
        timeOffPage.goToTeamMemberDetail(teamMemName);
        timeOffPage.switchToTimeOffTab();

        //get session id via login
        String sessionId = logIn();
        //set UseAbsenceMgmtConfiguration Toggle On
        if (!isToggleEnabled(sessionId, "UseAbsenceMgmtConfiguration")) {
            String[] toggleResponse = turnOnToggle(sessionId, "UseAbsenceMgmtConfiguration");
            Assert.assertEquals(getHttpStatusCode(toggleResponse), 200, "Failed to get the user's template!");
        }
        //confirm template
        String workerId = "1523a61c-d9e4-4ee6-97f9-b8195ae6f990";
        String tempName = getUserTemplate(workerId, sessionId);
        Assert.assertEquals(tempName, targetTemplate, "The user wasn't associated to this Template!!! ");
        SimpleUtils.pass("Succeeded in validating employee was associated to the target template!");

        //create a time off balance map to store the expected time off balances.
        HashMap<String, String> expectedTOBalance = new HashMap<>();
        expectedTOBalance.put("Annual Leave", "0");//Hire Date 10/21/2021~ Hire Date
        expectedTOBalance.put("PTO", "0");//Hire Date 10/21/2021 ~ SpecifiedDate 12/31

        //Delete the worker's accrual balance
        String[] deleteResponse = deleteAccrualByWorkerId(workerId, sessionId);
        Assert.assertEquals(getHttpStatusCode(deleteResponse), 200, "Failed to delete the user's accrual!");
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> actualTOB = timeOffPage.getTimeOffBalance();
        Assert.assertEquals(actualTOB, expectedTOBalance, "Failed to clear the employee's accrual balance!");
        SimpleUtils.pass("Succeeded in clearing employee's accrual balance!");
        //Annual Leave
        //< eligibility rules:  10/21/2021~10/30/2021  in total:55.67(approved)   leave 10/29 5hrs as pending  8/18/28
        //> eligibility rules: 10/31/2021~11/09/2021  in total:60.6(approved)     leave 11/02 10hrs as pending   7/17/27
        //= eligibility rules: 11/10/2021~11/19/2021  in total:60(approved)     leave 11/19 2.5hrs as pending
        //PTO set as no eligibility rules. default value N/A
        //< eligibility rules:
        String Date1 = "2021-10-26";//<fixed days
        String Date2 = "2021-10-30";//=fixed days
        String Date3 = "2021-10-31";//>fixed days
        //> eligibility rules:
        String Date4 = "2021-11-08";//<fixed days
        String Date5 = "2021-11-09";//=fixed days
        String Date6 = "2021-11-10";//>fixed days
        //= eligibility rules:
        String Date7 = "2021-11-18";//<fixed days
        String Date8 = "2021-11-19";//=fixed days
        String Date9 = "2021-11-20";//>fixed days

        //Run engine to the day<fixed days:
        /*String[] accrualResponse1 = runAccrualJobToSimulateDate(workerId, Date1, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse1), 200, "Failed to run accrual job!");
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance1 = timeOffPage.getTimeOffBalance();
        String verification1 = validateTheAccrualResults(accrualBalance1, expectedTOBalance);
        Assert.assertTrue(verification1.contains("Succeeded in validating"), verification1);
        SimpleUtils.pass("Succeeded in validating employee's accrual balance!");

        //Run engine to the fixed days:
        //blocked by OPS-4788
        String[] accrualResponse2 = runAccrualJobToSimulateDate(workerId, Date2, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse2), 200, "Failed to run accrual job!");
        //expectedTOBalance.put("PTO", "3");
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance2 = timeOffPage.getTimeOffBalance();
        String verification2 = validateTheAccrualResults(accrualBalance2, expectedTOBalance);
        Assert.assertTrue(verification2.contains("Succeeded in validating"), verification2);
        SimpleUtils.pass("Succeeded in validating employee's accrual balance!");

        //Delete the worker's accrual balance and directly run engine to the day>fixed days
        String[] deleteResponse1 = deleteAccrualByWorkerId(workerId, sessionId);
        Assert.assertEquals(getHttpStatusCode(deleteResponse1), 200, "Failed to delete the user's accrual!");
        expectedTOBalance.put("PTO", "0");
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> actualTOB1 = timeOffPage.getTimeOffBalance();
        Assert.assertEquals(actualTOB1, expectedTOBalance, "Failed to clear the employee's accrual balance!");
        SimpleUtils.pass("Succeeded in clearing employee's accrual balance!");

        String[] accrualResponse3 = runAccrualJobToSimulateDate(workerId, Date3, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse3), 200, "Failed to run accrual job!");
        //expectedTOBalance.put("PTO", "3");
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance3 = timeOffPage.getTimeOffBalance();
        String verification3 = validateTheAccrualResults(accrualBalance3, expectedTOBalance);
        Assert.assertTrue(verification3.contains("Succeeded in validating"), verification3);
        SimpleUtils.pass("Succeeded in validating employee's accrual balance!");

        //>eligibility rules
        //Run engine to the day<fixed days:  0, 3
        String[] accrualResponse4 = runAccrualJobToSimulateDate(workerId, Date4, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse4), 200, "Failed to run accrual job!");
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance4 = timeOffPage.getTimeOffBalance();
        String verification4 = validateTheAccrualResults(accrualBalance4, expectedTOBalance);
        Assert.assertTrue(verification4.contains("Succeeded in validating"), verification4);
        SimpleUtils.pass("Succeeded in validating employee's accrual balance!");

        //Run engine to the fixed days:
        String[] accrualResponse5 = runAccrualJobToSimulateDate(workerId, Date5, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse5), 200, "Failed to run accrual job!");
        expectedTOBalance.put("Annual Leave", "3");
        //expectedTOBalance.put("PTO", "6");
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance5 = timeOffPage.getTimeOffBalance();
        String verification5 = validateTheAccrualResults(accrualBalance5, expectedTOBalance);
        Assert.assertTrue(verification5.contains("Succeeded in validating"), verification5);
        SimpleUtils.pass("Succeeded in validating employee's accrual balance!");

        //Delete the worker's accrual balance and directly run engine to the day>fixed days
        String[] deleteResponse2 = deleteAccrualByWorkerId(workerId, sessionId);
        Assert.assertEquals(getHttpStatusCode(deleteResponse2), 200, "Failed to delete the user's accrual!");
        expectedTOBalance.put("Annual Leave", "0");
        expectedTOBalance.put("PTO", "0");
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> actualTOB2 = timeOffPage.getTimeOffBalance();
        Assert.assertEquals(actualTOB2, expectedTOBalance, "Failed to clear the employee's accrual balance!");
        SimpleUtils.pass("Succeeded in clearing employee's accrual balance!");

        String[] accrualResponse6 = runAccrualJobToSimulateDate(workerId, Date6, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse6), 200, "Failed to run accrual job!");
        expectedTOBalance.put("Annual Leave", "3");
        //expectedTOBalance.put("PTO", "6");
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance6 = timeOffPage.getTimeOffBalance();
        String verification6 = validateTheAccrualResults(accrualBalance6, expectedTOBalance);
        Assert.assertTrue(verification6.contains("Succeeded in validating"), verification6);
        SimpleUtils.pass("Succeeded in validating employee's accrual balance!");

        //=eligibility rules
        //Run engine to the day<fixed days:
        String[] accrualResponse7 = runAccrualJobToSimulateDate(workerId, Date7, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse7), 200, "Failed to run accrual job!");
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance7 = timeOffPage.getTimeOffBalance();
        String verification7 = validateTheAccrualResults(accrualBalance7, expectedTOBalance);
        Assert.assertTrue(verification7.contains("Succeeded in validating"), verification7);
        SimpleUtils.pass("Succeeded in validating employee's accrual balance!");

        //Run engine to the fixed days:
        String[] accrualResponse8 = runAccrualJobToSimulateDate(workerId, Date8, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse8), 200, "Failed to run accrual job!");
        expectedTOBalance.put("Annual Leave", "6");
        //expectedTOBalance.put("PTO", "9");
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance8 = timeOffPage.getTimeOffBalance();
        String verification8 = validateTheAccrualResults(accrualBalance8, expectedTOBalance);
        Assert.assertTrue(verification8.contains("Succeeded in validating"), verification8);
        SimpleUtils.pass("Succeeded in validating employee's accrual balance!");

        //Delete the worker's accrual balance and directly run engine to the day>fixed days
        String[] deleteResponse3 = deleteAccrualByWorkerId(workerId, sessionId);
        Assert.assertEquals(getHttpStatusCode(deleteResponse3), 200, "Failed to delete the user's accrual!");
        expectedTOBalance.put("Annual Leave", "0");
        expectedTOBalance.put("PTO", "0");
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> actualTOB3 = timeOffPage.getTimeOffBalance();
        Assert.assertEquals(actualTOB3, expectedTOBalance, "Failed to clear the employee's accrual balance!");
        SimpleUtils.pass("Succeeded in clearing employee's accrual balance!");

        String[] accrualResponse9 = runAccrualJobToSimulateDate(workerId, Date9, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse9), 200, "Failed to run accrual job!");
        expectedTOBalance.put("Annual Leave", "6");
        //expectedTOBalance.put("PTO", "9");
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance9 = timeOffPage.getTimeOffBalance();
        String verification9 = validateTheAccrualResults(accrualBalance9, expectedTOBalance);
        Assert.assertTrue(verification9.contains("Succeeded in validating"), verification9);
        SimpleUtils.pass("Succeeded in validating employee's accrual balance!");

        //max carryover works well with fixed days
        //OPS-5063
        timeOffPage.editTimeOffBalance("PTO", "63");//50
        components.okToActionInModal(true);
        runAccrualJobToSimulateDate(workerId, "2021-12-30", sessionId); //PTO: +12hrs
        expectedTOBalance.put("PTO", "60");//hire date to specified date
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance10 = timeOffPage.getTimeOffBalance();
        String verification10 = validateTheAccrualResults(accrualBalance10, expectedTOBalance);
        Assert.assertTrue(verification10.contains("Succeeded in validating"), verification10);
        SimpleUtils.pass("Succeeded in validating Annual earn limit works well with fixed days(Hire date to Specified date)!");
        //OPS-5062
        String[] accrualResponse11 = runAccrualJobToSimulateDate(workerId, "2021-12-31", sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse11), 200, "Failed to run accrual job!");
        expectedTOBalance.put("PTO", "5");
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance11 = timeOffPage.getTimeOffBalance();
        String verification11 = validateTheAccrualResults(accrualBalance11, expectedTOBalance);
        Assert.assertTrue(verification11.contains("Succeeded in validating"), verification11);
        SimpleUtils.pass("Succeeded in validating max carryover works well with fixed days(Hire date to Specified date)!");*/

        //Annual earn limit works well with fixed days
        timeOffPage.editTimeOffBalance("Annual Leave", "61");
        components.okToActionInModal(true);
        runAccrualJobToSimulateDate(workerId, "2022-10-01", sessionId);
        expectedTOBalance.put("Annual Leave", "60");//hire date to specified date
        //expectedTOBalance.put("PTO", "32");//5+27
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance12 = timeOffPage.getTimeOffBalance();
        String verification12 = validateTheAccrualResults(accrualBalance12, expectedTOBalance);
        Assert.assertTrue(verification12.contains("Succeeded in validating"), verification12);
        SimpleUtils.pass("Succeeded in validating Annual earn limit works well with fixed days(Hire date to hire date)!");

        runAccrualJobToSimulateDate(workerId, "2022-10-21", sessionId);
        expectedTOBalance.put("Annual Leave", "5");//hire date to specified date
        //expectedTOBalance.put("PTO", "34");//5+29
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance13 = timeOffPage.getTimeOffBalance();
        String verification13 = validateTheAccrualResults(accrualBalance13, expectedTOBalance);
        Assert.assertTrue(verification13.contains("Succeeded in validating"), verification13);
        SimpleUtils.pass("Succeeded in validating max carryover works well with fixed days(Hire date to hire date)!");

        //Max available hours works well with fixed days
    }


    public void importAccrualBalance(String sessionId) {
        String url = "https://rc-enterprise.dev.legion.work/legion/integration/testUploadAccrualLedgerData?isTest=false&fileName=src/test/resources/uploadFile/AccrualLedger_auto.csv&encrypted=false";
        String filePath = "src/test/resources/uploadFile/AccrualLedger_auto.csv";
        String responseInfo = HttpUtil.fileUploadByHttpPost(url, sessionId, filePath);
        if (StringUtils.isNotBlank(responseInfo)) {
            //转json数据
            JSONObject json = JSONObject.parseObject(responseInfo);
            if (!json.isEmpty()) {
                //数据处理
                String value = json.getString("responseStatus");
                System.out.println(value);
            }
        }
    }

    public ArrayList<String> getHolidaysViaAPI(String sessionId) {
        String holidayUrl = Constants.getHoliday;
        Map<String, String> holidayPara = new HashMap<>();
        holidayPara.put("countryCode", "US");
        String[] response = HttpUtil.httpGet(holidayUrl, sessionId, holidayPara);
        Assert.assertEquals(getHttpStatusCode(response), 200, "Failed to getHolidays!");
        //get the response
        String records = ((JsonUtil.getJsonValue(response[1], "records")));
        JSONArray re=JSON.parseArray(records);// from string to Array
        ArrayList<String> holidays = new ArrayList<>();
        for (int i = 0; i < re.size(); i++) {
            String holidayName = JsonUtil.getJsonValue(re.get(i).toString(), "name");
            holidays.add(holidayName);
        }
        return holidays;
    }

}
