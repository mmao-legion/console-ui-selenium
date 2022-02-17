package com.legion.tests.core.opEmployeeManagement;

import com.legion.pages.OpsPortaPageFactories.LocationsPage;
import com.legion.pages.core.OpCommons.ConsoleNavigationPage;
import com.legion.pages.core.OpCommons.OpsCommonComponents;
import com.legion.pages.core.OpCommons.OpsPortalNavigationPage;
import com.legion.pages.core.OpCommons.RightHeaderBarPage;
import com.legion.pages.core.opemployeemanagement.AbsentManagePage;
import com.legion.pages.core.opemployeemanagement.EmployeeManagementPanelPage;
import com.legion.pages.core.opemployeemanagement.TimeOffPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.HttpUtil;
import com.legion.utils.Constants;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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
    @TestName(description = "Accrual engine")
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
        String verification6 = validateTheAccrualResults(accrualBalance1231, expectedTOBalance);
        Assert.assertTrue(verification6.contains("Succeeded in validating"), verification6);


        //Run engine to Date7 2022-01-01
        String[] accrualResponse7 = runAccrualJobToSimulateDate(workerId, date7, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse7), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Annual Leave2", "8");//5 +3hours(newly accrued)
        //expectedTOBalance.put("Grandparents Day Off3", "10");//2(max carryover)+8(newly accrued)//HireDate~Specified/lump-sum
        //not granted 8 hours on Jan-1st
        expectedTOBalance.put("Pandemic1", "3");//2(max carryover)+1(newly accrued) //Specified~Specified/Monthly /calendar month/begin /allowance in days 126(out of)
        expectedTOBalance.put("Pandemic4", "10");//2(max carryover)+8(newly accrued) //Specified~Specified/lump-sum /allowance in days 127(in)
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance0101 = timeOffPage.getTimeOffBalance();
        //HashMap<String, String> accrualHistory0101 = timeOffPage.getAccrualHistory();
        String verification7 = validateTheAccrualResults(accrualBalance0101, expectedTOBalance);
        Assert.assertTrue(verification7.contains("Succeeded in validating"), verification7);

        //Run engine to Date8 2022-01-31
        String[] accrualResponse8 = runAccrualJobToSimulateDate(workerId, date8, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse8), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Annual Leave3", "3");//2 +1hours(newly accrued)
        expectedTOBalance.put("Bereavement3", "7");//3 +4hours(newly accrued)
        expectedTOBalance.put("Grandparents Day Off3", "10");//need to delete
        expectedTOBalance.put("Pandemic2", "6");//2 +4hours
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance0131 = timeOffPage.getTimeOffBalance();
        //HashMap<String, String> accrualHistory0131 = timeOffPage.getAccrualHistory();
        String verification8 = validateTheAccrualResults(accrualBalance0131, expectedTOBalance);
        Assert.assertTrue(verification8.contains("Succeeded in validating"), verification8);

        //Run engine to Date9 2022-05-07
        String[] accrualResponse9 = runAccrualJobToSimulateDate(workerId, date9, sessionId);
        Assert.assertEquals(getHttpStatusCode(accrualResponse9), 200, "Failed to run accrual job!");
        //expected accrual
        expectedTOBalance.put("Annual Leave2", "12");//8 +4hours(newly accrued)
        expectedTOBalance.put("Annual Leave3", "7");//3 +4hours(newly accrued)
        expectedTOBalance.put("Bereavement3", "21");//7+14hours(newly accrued)
        expectedTOBalance.put("Pandemic1", "7");//3 +4hours
        expectedTOBalance.put("Pandemic2", "20");//6 +14hours
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance050722 = timeOffPage.getTimeOffBalance();
        //HashMap<String, String> accrualHistory050722 = timeOffPage.getAccrualHistory();
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
        expectedTOBalance.put("Grandparents Day Off2", "10");//2 max carryover+ 8 new accrual
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance050822 = timeOffPage.getTimeOffBalance();
        //HashMap<String, String> accrualHistory050822 = timeOffPage.getAccrualHistory();
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
        expectedTOBalance.put("Pandemic2", "45");//20 +25hours
        //and verify the result in UI
        refreshPage();
        timeOffPage.switchToTimeOffTab();
        HashMap<String, String> accrualBalance103122 = timeOffPage.getTimeOffBalance();
        //HashMap<String, String> accrualHistory103122 = timeOffPage.getAccrualHistory();
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

}
