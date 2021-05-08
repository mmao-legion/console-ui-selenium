package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.ReportPage;
import com.legion.pages.TimeSheetPage;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleReportPage extends BasePage implements ReportPage{

	@FindBy(css = "div.console-navigation-item-label.Report")
	private WebElement reportConsoleMenuDiv;


	@Override
	public void clickOnConsoleReportMenu() throws Exception {
		if(isElementLoaded(reportConsoleMenuDiv,5)) {
			click(reportConsoleMenuDiv);
			if (reportConsoleMenuDiv.findElement(By.xpath("./..")).getAttribute("class").contains("active"))
				SimpleUtils.pass("Report Page: Click on Compliance console menu successfully");
			else
				SimpleUtils.fail("Report Page: It doesn't navigate to Compliance console menu after clicking", false);
		} else
			SimpleUtils.fail("Report Console Menu not loaded Successfully!", false);


	}
}