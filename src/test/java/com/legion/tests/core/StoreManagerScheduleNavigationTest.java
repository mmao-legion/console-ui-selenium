package com.legion.tests.core;

import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.utils.JsonUtil;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

public class StoreManagerScheduleNavigationTest extends TestBase {
    private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");

    @BeforeClass
    public void setUp () {
        /*
            Login as SM
            Open Schedule app
            Navigate to schedule overview
            Pick next week which is one week from current date
            Modify business hour and mark one day to be closed
            Generate schedule
         */
    }

    @Automated(automated = "/" + "Manual]")
    @Owner(owner = "[Tulsi")
    @TestName(description = "LEG-4249: should open the same day schedule when cicking Schedule tab from Overview")
    @Test(dataProvider = "browsers")
    public void dayViewShouldBeSticky (String browser, String version, String os, String pageobject) {
        /*
        Open a Schedule of any Day (Not necessarily the same Day) in Day View say (Sep 1)
        Then go to Schedule overview
        Then go back to Schedule tab directly
        Should open the the schedule of the same Day (Sep 1) in Day View
         */
    }

    @Automated(automated = "/" + "Manual]")
    @Owner(owner = "[Tulsi")
    @TestName(description = "LEG-4249: should open the week schedule in TM view when cicking Schedule tab from Overview")
    @Test(dataProvider = "browsers")
    public void teamViewShouldBeSticky (String browser, String version, String os, String pageobject) {
        /*
        Open a Schedule of any Week (Not necessarily the current week) in TM view
        Then go to Schedule overview
        Then go back to Schedule tab directly
        Should open the the schedule of the same week in TM view
         */
    }

    @Automated(automated = "/" + "Manual]")
    @Owner(owner = "[Tulsi")
    @TestName(description = "LEG-4249: should open the week schedule in TM view when cicking Schedule tab from Overview")
    @Test(dataProvider = "browsers")
    public void shouldNaviToNextWeekAndGenerateSchedule (String browser, String version, String os, String pageobject) {
        /*
            navigate to next week
            assert schedule shows there are no shifts on the day when store is closed
            switch to TM view
            click next >
            assert that you are in the next week schedule and generate schedule button is clickable
            click generate and assert seeing successfully generated modal
            assert schedule shows there are shifts on the day when store is closed in the previous week
         */
    }

    @AfterClass
    public void cleanUp () {
        /*
            Ungenerate next week schedule
            Ungenerate the week after next week's schedule
            restore business hour of the next week
         */
    }
}