package com.legion.tests.bdd.steps;

import com.google.inject.Inject;
import com.legion.pages.DashboardPage;
import com.legion.pages.LoginPage;
import com.legion.pages.TimeSheetPage;
import com.legion.pages.core.ConsoleTimeSheetPage;
import com.legion.tests.TestBase;
import com.legion.utils.SimpleUtils;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.lang.reflect.Method;

public class TimeSheetSteps {


    @Inject
    ConsoleTimeSheetPage timeSheetPage;

    @When("I click in Timesheet tab")
    public void whenIclickInTimesheet() throws Exception {
        timeSheetPage.clickOnTimeSheetConsoleMenu();
    }

    @Then("The client verify that results are shown properly")
    public void thenIVerifyit(){
        System.out.println("Step 3c");
    }

}
