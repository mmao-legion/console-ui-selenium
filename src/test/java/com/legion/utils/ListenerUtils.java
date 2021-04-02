package com.legion.utils;

import com.alibaba.fastjson.JSON;
import com.legion.GlobalVar;
import com.legion.entity.TestCaseResult;
import com.legion.entity.TestResult;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ListenerUtils extends RunListener {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private List<String> throwableLog;
    private TestResult testResult;
    public TestCaseResult testCaseResult;
    private List<TestCaseResult> testCaseResultList;

    private static String testBeginTime;
    private static long testCaseBeginTime;


    private static Long testClassCount = 0L;
    private static Long testBeginTimeMills;

    public ListenerUtils() {
        testResult = new TestResult();
        testCaseResultList = new ArrayList<>();
    }

    public void testRunStarted(Description description) throws Exception {
        logger.info("--> {}", description.getClassName());


        if(++testClassCount == 1){
            testBeginTime = new Date().toString();
            testBeginTimeMills = System.currentTimeMillis();
        }
    }

    public void testRunFinished(Result result) throws Exception {

        Long totalTimes = System.currentTimeMillis() - testBeginTimeMills;

        logger.info("Test Result : {}", result.wasSuccessful());
        logger.info("Execute Time : {}", totalTimes);
        logger.info("Total Cases : {}", (result.getRunCount() + result.getIgnoreCount()));
        logger.info("Failed Cases : {}", result.getFailureCount());
        logger.info("Ignore Cases : {}", result.getIgnoreCount());

        testResult.setTestName("API Automatic Test");

        testResult.setTestAll(result.getRunCount() + result.getIgnoreCount());
        testResult.setTestPass(result.getRunCount() - result.getFailureCount());
        testResult.setTestFail(result.getFailureCount());
        testResult.setTestSkip(result.getIgnoreCount());

        testResult.setTestResult(testCaseResultList);

        testResult.setBeginTime(testBeginTime);
        testResult.setTotalTime(totalTimes + "ms");

        createReport();
    }

    public void testStarted(Description description) throws Exception {
        logger.info("----> {}.{}", description.getClassName(), description.getMethodName());

        testCaseBeginTime = System.currentTimeMillis();
        testCaseResult = new TestCaseResult();
    }

    public void testFinished(Description description) throws Exception {
        logger.info("<---- {}.{}", description.getClassName(), description.getMethodName());

        if (testCaseResult.getThrowableLog().isEmpty()) {
            testCaseResult.setStatus("Passed");
        }

        testCaseResult.setClassName(description.getClassName());
        testCaseResult.setMethodName(description.getMethodName());
        testCaseResult.setSpendTime(System.currentTimeMillis() - testCaseBeginTime + "ms");

        testCaseResultList.add(testCaseResult);
    }

    public void testFailure(Failure failure) throws Exception {
        logger.info("Execution of test case failed : " + failure.getMessage());

        testCaseResult.setStatus("Failed");

        throwableLog = new ArrayList<>();
        Throwable throwable = failure.getException();
        if (throwable != null) {
            throwableLog.add(throwable.toString());

            StackTraceElement[] st = throwable.getStackTrace();
            for (StackTraceElement stackTraceElement : st) {
                throwableLog.add("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + stackTraceElement);
            }
        }

        testCaseResult.setThrowableLog(throwableLog);
    }

    public void testIgnored(Description description) throws Exception {

    }

    private void createReport() {
        String filePath = null;
        URL fileURL = this.getClass().getClassLoader().getResource("template.html");
        if (fileURL != null) {
            filePath = fileURL.getPath();
        }

        String template = FileUtils.read(filePath);
        String jsonString = JSON.toJSONString(testResult);
        template = template.replace("${resultData}", jsonString);
        FileUtils.write(template, GlobalVar.REPORT_PATH + "result.html");
    }
}
