package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.JobsPage;
import com.legion.pages.LocationsPage;
import com.legion.utils.FileDownloadVerify;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.legion.tests.TestBase.*;
import static com.legion.utils.MyThreadLocal.*;


public class OpsPortalJobsPage extends BasePage implements JobsPage {

	public OpsPortalJobsPage() {
		PageFactory.initElements(getDriver(), this);
	}
	private static Map<String, String> newLocationParas = JsonUtil.getPropertiesFromJsonFile("src/test/resources/AddANewLocation.json");
	private static HashMap<String, String> parameterMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");

	public enum jobSummarySmartCardData {
		jobsCompleted("jobsCompleted"),
		jobsInProgress("jobsInProgress"),
		notStarted("notStarted"),
		jobs("number");
		private final String value;

		jobSummarySmartCardData(final String newValue) {
			value = newValue;
		}

		public String getValue() {
			return value;
		}
	}
	// Added by Estelle
	@FindBy(css="[class='console-navigation-item-label Jobs']")
	private WebElement goToJobsButton;
	@FindBy(css="lg-button[label=\"Create New Job\"]")
	private WebElement createNewJobBtn;
	@FindBy(css="lg-filter[label=\"Filter\"]")
	private WebElement filterBtn;
	@FindBy(css="input-field[placeholder=\"You can search by Job title or type\"]")
	private WebElement searchInputBox;
	@FindBy (css = ".lg-pagination__arrow--left")
	private WebElement pageLeftBtn;
	@FindBy (css = ".lg-pagination__arrow--right")
	private WebElement pageRightBtn;
	@FindBy(css = "div.card-carousel")
	private WebElement smartCardPanel;
	@FindBy(css = "div.lg-filter__wrapper")
	private WebElement filterPopup;
	@FindBy(css = "[ng-repeat=\"(key, opts) in $ctrl.displayFilters\"]")
	private List<WebElement> filterElementsInJob;

	@Override
	public void iCanEnterJobsTab() {
		if (isElementEnabled(goToJobsButton,3)) {
			click(goToJobsButton);
			if (isElementEnabled(createNewJobBtn,5)) {
				SimpleUtils.pass("I can enter jobs page");
			}else
				SimpleUtils.fail("I can not enter jobs page",false);
		}else
			SimpleUtils.fail("Jobs button load failed",false);
	}

	@Override
	public void verifyJobLandingPageShowWell() {
		if (isElementEnabled(filterBtn,3)&& isElementEnabled(createNewJobBtn,3)&&
		isElementEnabled(searchInputBox,3)&& isElementEnabled(pageLeftBtn,3)&&isElementEnabled(pageRightBtn,3)
		&& isElementEnabled(smartCardPanel,3)) {
			SimpleUtils.pass("Jobs landing page show well");
		}else
			SimpleUtils.fail("Jobs landing page load failed",false);
	}

	public void unCheckFilters(ArrayList<WebElement> filterElements) {
		if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
			click(filterBtn);
		waitForSeconds(2);
		for (WebElement filterElement : filterElements) {
			WebElement filterCheckBox = filterElement.findElement(By.cssSelector("input[type=\"checkbox\"]"));
			String elementClasses = filterCheckBox.getAttribute("class").toLowerCase();
			if (elementClasses.contains("ng-not-empty"))
				click(filterElement);

		}
	}
	public void filterJobsByJobTypeAndStatus(boolean isWeekView) throws Exception {
		waitForSeconds(10);
		String jobTypeFilterKey = "jobtype";
		String statusFilterKey = "status";
		HashMap<String, ArrayList<WebElement>> availableFilters = getAvailableFilters();
		if (availableFilters.size() > 1) {
			ArrayList<WebElement> jobTypeFilters = availableFilters.get(jobTypeFilterKey);
			ArrayList<WebElement> statusFilters = availableFilters.get(statusFilterKey);
			for (WebElement statusFilter : statusFilters) {
				if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
					click(statusFilter);
				unCheckFilters(statusFilters);
				click(statusFilter);
				SimpleUtils.report("Data for job status: '" + statusFilter.getText() + "'");
				filterJobsByJobType(jobTypeFilters);
			}
		} else {
			SimpleUtils.fail("Filters are not appears on job page!", false);
		}
	}

	public HashMap<String, ArrayList<WebElement>> getAvailableFilters() {
		HashMap<String, ArrayList<WebElement>> jobFilters = new HashMap<String, ArrayList<WebElement>>();
		try {
			if (isElementLoaded(filterBtn,10)) {
				if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
					click(filterBtn);
				for (WebElement filterElementInJob : filterElementsInJob) {
					WebElement filterLabel = filterElementInJob.findElement(By.className("lg-filter__category-label"));
					String filterType = filterLabel.getText().toLowerCase().replace(" ", "");
					List<WebElement> filters = filterElementInJob.findElements(By.cssSelector("input-field[type=\"checkbox\"]"/*"[ng-repeat=\"opt in opts\"]"*/));
					ArrayList<WebElement> filterList = new ArrayList<WebElement>();
					for (WebElement filter : filters) {
						filterList.add(filter);
					}
					jobFilters.put(filterType, filterList);
				}
			} else {
				SimpleUtils.fail("Filters button not found on Jobs page!", false);
			}
		} catch (Exception e) {
			SimpleUtils.fail("Filters button not loaded successfully on jobs page!", true);
		}
		return jobFilters;
	}



	public HashMap<String, Float> getSummaryComplateInprogressAndNotStartNum() throws Exception {
		HashMap<String, Float> summaryComplateInprogressAndNotStartNums = new HashMap<String, Float>();
		WebElement smartCardElement = MyThreadLocal.getDriver().findElement(By.xpath("//div[@class='card-carousel-card card-carousel-card-primary card-carousel-card-table ']"));
		if (isElementLoaded(smartCardElement,5)) {
			String sumarySmartCardText = smartCardElement.getText();
			String[] complateInprogressAndNotStartNums = sumarySmartCardText.split("\n");
			for (String complateInprogressAndNotStartNum: complateInprogressAndNotStartNums) {

				if(complateInprogressAndNotStartNum.toLowerCase().contains(jobSummarySmartCardData.jobsCompleted.getValue().toLowerCase()))
				{
					summaryComplateInprogressAndNotStartNums = ConsoleScheduleNewUIPage.updateScheduleHoursAndWages(summaryComplateInprogressAndNotStartNums , complateInprogressAndNotStartNum.split(" ")[1],
							"ComplatedNum");
				}
				else if(complateInprogressAndNotStartNum.toLowerCase().contains(jobSummarySmartCardData.jobsInProgress.getValue().toLowerCase()))
				{
					summaryComplateInprogressAndNotStartNums = ConsoleScheduleNewUIPage.updateScheduleHoursAndWages(summaryComplateInprogressAndNotStartNums , complateInprogressAndNotStartNum.split(" ")[1]
							.replace("$", ""), "InProgressNum");
				}
				else if(complateInprogressAndNotStartNum.toLowerCase().contains(jobSummarySmartCardData.notStarted.getValue().toLowerCase()))
				{
					summaryComplateInprogressAndNotStartNums = ConsoleScheduleNewUIPage.updateScheduleHoursAndWages(summaryComplateInprogressAndNotStartNums , complateInprogressAndNotStartNum.split(" ")[1]
							.replace("$", ""), "NotStartNum");
				}
			}
		}else {
			SimpleUtils.fail("there is no summary smart card",false);
		}
		return summaryComplateInprogressAndNotStartNums;
	}



	public void filterJobsByJobType(ArrayList<WebElement> jobTypeFilters) throws Exception {

		for (WebElement jobTypeFilter : jobTypeFilters) {
			try {
				Thread.sleep(1000);
				if (filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
					click(filterBtn);
				unCheckFilters(jobTypeFilters);
				String jobType = jobTypeFilter.getText();
				SimpleUtils.report("Data for Job Type: '" + jobType + "'");
				click(jobTypeFilter);
				click(filterBtn);
				String cardHoursAndWagesText = "";
				HashMap<String, Float> hoursAndWagesCardData = getSummaryComplateInprogressAndNotStartNum();
				for (Map.Entry<String, Float> hoursAndWages : hoursAndWagesCardData.entrySet()) {
					if (cardHoursAndWagesText != "")
						cardHoursAndWagesText = cardHoursAndWagesText + ", " + hoursAndWages.getKey() + ": '" + hoursAndWages.getValue() + "'";
					else
						cardHoursAndWagesText = hoursAndWages.getKey() + ": '" + hoursAndWages.getValue() + "'";
				}
				SimpleUtils.report("Active Week Card's Data: " + cardHoursAndWagesText);
//				getHoursAndTeamMembersForEachDaysOfWeek();
//				SimpleUtils.assertOnFail("Sum of Daily Schedule Hours not equal to Active Week Schedule Hours!", verifyActiveWeekDailyScheduleHoursInWeekView(), true);
//
//				if (!getActiveGroupByFilter().toLowerCase().contains(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyTM.getValue().toLowerCase())
//						&& !shiftType.toLowerCase().contains("open"))
//					verifyActiveWeekTeamMembersCountAvailableShiftCount();
			} catch (Exception e) {
				SimpleUtils.fail("Unable to get Card data for active week!", true);
			}
			}

		}

	}