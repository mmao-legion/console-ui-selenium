package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.JobsPage;
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

import java.util.*;

import static com.legion.tests.TestBase.propertyMap;
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
	@FindBy(css="input[placeholder=\"You can search by Job title or type\"]")
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
			SimpleUtils.pass("Jobs landing page show well and the placeholder of search field on the jobs landing page show well");
		}else
			SimpleUtils.fail("Jobs landing page load failed",false);
	}

	@FindBy(css = "input[aria-label=\"Job Title\"]")
	private WebElement jobTitleInputBox;
	@FindBy(css = "input[aria-label=\"Comments\"]")
	private WebElement jobCommentsInputBox;
	@FindBy(css = "input-field[options=\"jobTypeChoices\"]>ng-form >div >select")
	private WebElement jobTypeSelectBox;
	@FindBy(css = "div[ng-repeat=\"week in model.data\"]")
	private List<WebElement>  weekSelecters;
	@FindBy(css="lg-button[label=\"OK\"]")
	private WebElement okBtnInCreateNewJobPage;
	@FindBy(css="lg-button[label=\"Add Location\"]")
	private WebElement addLocationBtn;
	@FindBy(css="lg-button[label=\"Add\"]")
	private WebElement addBtn;

	@Override
	public void iCanEnterCreateNewJobPage() {
		if (isElementEnabled(createNewJobBtn,3) ) {
			click(createNewJobBtn);
			if (isElementEnabled(jobTypeSelectBox,3)) {
				SimpleUtils.pass("I can enter create new job page successfully");
			}else
				SimpleUtils.fail("I can not enter create new job page ",false);
		}else 
			SimpleUtils.fail("Create new job button load failed",false);
	}

	@Override
	public void selectJobType(String jobType) throws Exception {
		selectByVisibleText(jobTypeSelectBox,jobType);
		if (!jobTypeSelectBox.getAttribute("class").equalsIgnoreCase("ng-empty")) {
			SimpleUtils.pass("Select job type" + jobType + "successfully");
		}else
			SimpleUtils.fail("Select job type" + jobType + "failed",false);
	}

	@Override
	public void selectWeekForJobToTakePlace() {
		for (WebElement weekSelecter:weekSelecters
			 ) {
			if (!weekSelecter.getAttribute("class").contains("unselectable-week")) {
				click(weekSelecter);
				if (weekSelecter.getAttribute("class").contains("current-week")) {
					SimpleUtils.pass("Select Week for job to take place successfully");
					break;
				}else
					SimpleUtils.fail("Failed to select week for job",false);
			}

		}
	}

	@Override
	public void clickOkBtnInCreateNewJobPage() {
		if (isElementEnabled(okBtnInCreateNewJobPage,3)) {
			click(okBtnInCreateNewJobPage);
			if (isElementEnabled(jobTitleInputBox,3)) {
				SimpleUtils.pass("OK button is clickable and can enter job creation details page");
			}
		}else
			SimpleUtils.fail("Ok button load failed",false);

	}

	@Override
	public void inputJobTitle(String jobTitle) {
		if (isElementEnabled(jobTitleInputBox,3)) {
			jobTitleInputBox.sendKeys(jobTitle);
		}else
			SimpleUtils.fail("job Title InputBox load failed",false);

	}

	@Override
	public void inputJobComments(String commentText) {
		if (isElementEnabled(jobCommentsInputBox,3)) {
			jobCommentsInputBox.sendKeys(commentText);
		}else
			SimpleUtils.fail("job comment InputBox load failed",false);
	}

	@Override
	public void addLocationBtnIsClickable() {
		click(addLocationBtn);
		if (isElementEnabled(selectALocationTitle,5)) {
			SimpleUtils.pass("Add location button is clickable and can enter select location page");
		}else
			SimpleUtils.fail("Add location button load failed",false);
	}
	@FindBy(css=".lg-modal__title-icon")
	private WebElement selectALocationTitle;
	@FindBy(css="div.lg-tab-toolbar__search >lg-search >input-field>ng-form>input")
	private WebElement searchInputInSelectALocation;
	@FindBy(css="tr[ng-repeat=\"item in $ctrl.currentPageItems track by $index\"]")
	private List<WebElement> locationRowsInSelectLocation;
	@FindBy(css="lg-button[label=\"Create\"]")
	private WebElement createBtn;


	@Override
	public void iCanSelectLocationsByAddLocation(String searchText, int index) {
		if (isElementEnabled(selectALocationTitle,5)) {
			searchInputInSelectALocation.sendKeys(searchText);
			searchInputInSelectALocation.sendKeys(Keys.ENTER);
			waitForSeconds(5);
			if (locationRowsInSelectLocation.size()>0) {
				WebElement firstRow = locationRowsInSelectLocation.get(index).findElement(By.cssSelector("input[type=\"checkbox\"]"));
				click(firstRow);
				click(addBtn);
				click(okBtnInCreateNewJobPage);
			}else
				SimpleUtils.report("Search location result is 0");

		}else
			SimpleUtils.fail("Select a location window load failed",true);
	}
	@FindBy(css=".lg-tabs__nav >.lg-tabs__nav-item:nth-child(2)")
	private WebElement districtTabAftClickAddLocationBtn;

	@Override
	public void iCanSelectDistrictByAddLocation(String searchText, int index) {
		click(districtTabAftClickAddLocationBtn);
		if (isElementEnabled(selectALocationTitle,5)) {
			searchInputInSelectALocation.sendKeys(searchText);
			searchInputInSelectALocation.sendKeys(Keys.ENTER);
			waitForSeconds(5);
			if (locationRowsInSelectLocation.size()>0) {
				WebElement firstRow = locationRowsInSelectLocation.get(index).findElement(By.cssSelector("input[type=\"checkbox\"]"));
				click(firstRow);
				click(addBtn);
				click(okBtnInCreateNewJobPage);
			}else
				SimpleUtils.report("Search location result is 0");

		}else
			SimpleUtils.fail("Select a location window load failed",true);
	}

	@Override
	public void createBtnIsClickable() {
		scrollToBottom();
		click(createBtn);
		SimpleUtils.pass("Job creation done");
//		waitForSeconds(3);
		if (isElementEnabled(createNewJobBtn,5)) {
			SimpleUtils.pass("Create button is clickable and can enter select location page");
		}else
			SimpleUtils.fail("Create location button load failed",false);
	}

	@FindBy(css="tr[ng-repeat=\"job in searchFilteredJobs\"]")
	private List<WebElement> jobRows;

	@Override
	public void iCanSearchTheJobWhichICreated(String jobTitle) throws Exception {
		searchInputBox.clear();
		String[] searchJobCha = jobTitle.split(",");
		if (isElementLoaded(searchInputBox, 10) ) {
			for (int i = 0; i < searchJobCha.length; i++) {
				searchInputBox.sendKeys(searchJobCha[0]);
				searchInputBox.sendKeys(Keys.ENTER);
				waitForSeconds(3);
				if (jobRows.size()-1>0) {
					SimpleUtils.pass("Jobs: " + jobRows.size() + " job(s) found  "+ " by "+jobTitle);
					if (getDriver().findElement(By.cssSelector("table.lg-table >tbody> tr:nth-child(1)")).getText().trim().contains("Job Type")&&
						getDriver().findElement(By.cssSelector("table.lg-table >tbody> tr:nth-child(1)")).getText().trim().contains("Job Title") &&
							getDriver().findElement(By.cssSelector("table.lg-table >tbody> tr:nth-child(1)")).getText().trim().contains("Created By") &&
							getDriver().findElement(By.cssSelector("table.lg-table >tbody> tr:nth-child(1)")).getText().trim().contains("Date Created") &&
							getDriver().findElement(By.cssSelector("table.lg-table >tbody> tr:nth-child(1)")).getText().trim().contains("# of Locations") &&
							getDriver().findElement(By.cssSelector("table.lg-table >tbody> tr:nth-child(1)")).getText().trim().contains("Status") &&
							getDriver().findElement(By.cssSelector("table.lg-table >tbody> tr:nth-child(1)")).getText().trim().contains("Action")) {
						SimpleUtils.pass("On each row show Job Type, Name, Created By, Date Created, Status, Actions");
					}else
						SimpleUtils.fail("Search result table header load failed",false);
					break;
				} else {
					searchInputBox.clear();
				}
			}

		} else {
			SimpleUtils.fail("Search input is not clickable", true);
		}
	}

	@FindBy(css=".lg-sub-content-box-title")
	private List<WebElement> jobDetailsSubHeaders;
	@FindBy(css=".om-job-details")
	private WebElement jobDetails;
	@FindBy(css="sub-content-box[box-title=\"Job Details\"]")
	private WebElement jobDetailsBoxIn;
	@FindBy(css="sub-content-box[box-title=\"Week for job to take place\"]")
	private WebElement weekInfoBoxIn;
	@FindBy(css="sub-content-box[box-title=\"Create Schedule  Status\"]")
	private WebElement scheduleStatusBoxIn;
	@FindBy(css="sub-content-box[box-title=\"Notification\"]")
	private WebElement notificationBoxIn;
	@FindBy(css="lg-button[label=\"Export Result File\"]")
	private WebElement exportResultFileBtn;
	@FindBy(css="lg-button[label=\"Export Task Summary\"]")
	private WebElement exportResultSummaryBtn;


	@Override
	public void iCanGoToCreateScheduleJobDetailsPage(int index) {
		if (jobRows.size()>0) {
			List<WebElement> locationDetailsLinks = jobRows.get(index).findElements(By.cssSelector("button[type='button']"));
			click(locationDetailsLinks.get(index));
			waitForSeconds(5);
			for (int i = 0; i <jobDetailsSubHeaders.size() ; i++) {
				if (jobDetails.getText().contains("Job Details") && jobDetails.getText().contains("Week for job to take place")
						&& jobDetails.getText().contains("Create Schedule Status")&& jobDetails.getText().contains("Locations Selected")
						&& jobDetails.getText().contains("#with no schedules created for this week")  &&
						jobDetails.getText().contains("#with schedules created but not released for this week")  &&
						jobDetails.getText().contains("#with schedules released for this week")  &&
						jobDetails.getText().contains("#with schedules published for this week")  &&
						jobDetails.getText().contains("Export Result File")&& jobDetails.getText().contains("Export Task Summary")&&
						jobDetails.getText().contains("Notification") &&
						jobDetails.getText().contains("Enable Email Notification") &&
						jobDetails.getText().contains("Close")
				) {
					SimpleUtils.pass("I can go to job details page successfully and the create schedule job details page show well");
					break;
				}else
					SimpleUtils.fail("Create schedule job details page load failed",false);
			}
			
		}else if (jobRows.size()==0)
			SimpleUtils.report("There are no jobs that match your criteria. ");
	}

	@Override
	public void iCanGoToReleaseScheduleJobDetailsPage(int index) {
		if (jobRows.size()>0) {
			List<WebElement> locationDetailsLinks = jobRows.get(index).findElements(By.cssSelector("button[type='button']"));
			click(locationDetailsLinks.get(index));
			waitForSeconds(5);
			String adbcdf  = jobDetails.getText();
			for (int i = 0; i <jobDetailsSubHeaders.size() ; i++) {
				if (jobDetails.getText().contains("Job Details") && jobDetails.getText().contains("Week for job to take place")
						&& jobDetails.getText().contains("Release Schedule Status")&& jobDetails.getText().contains("Locations Selected")
						&& jobDetails.getText().contains("#with no schedules created for this week")  &&
						jobDetails.getText().contains("#with schedules created but not released for this week")  &&
						jobDetails.getText().contains("#with schedules released for this week")  &&
						jobDetails.getText().contains("#with schedules published for this week")  &&
						jobDetails.getText().contains("Export Result File")&&
						jobDetails.getText().contains("Notification") &&
						jobDetails.getText().contains("Enable Email Notification") &&
						jobDetails.getText().contains("Close")
				) {
					SimpleUtils.pass("I can go to job details page successfully and the release schedule job details page show well");
					break;
				}else
					SimpleUtils.fail("Release schedule job details page load failed",false);
			}

		}else if (jobRows.size()==0)
			SimpleUtils.report("There are no jobs that match your criteria. ");
	}

	@Override
	public void iCanGoToAdjustBudgetJobDetailsPage(int index) {
		if (jobRows.size()>0) {
			List<WebElement> locationDetailsLinks = jobRows.get(index).findElements(By.cssSelector("button[type='button']"));
			click(locationDetailsLinks.get(index));
			waitForSeconds(5);
			String adbcdf  = jobDetails.getText();
			for (int i = 0; i <jobDetailsSubHeaders.size() ; i++) {
				if (jobDetails.getText().contains("Job Details") && jobDetails.getText().contains("Week for job to take place")
						&& jobDetails.getText().contains("Adjust Budget Status")&&
						jobDetails.getText().contains("Budget Adjustment")&&
						jobDetails.getText().contains("Locations Selected")&&
						jobDetails.getText().contains("Export Task Summary")  &&
						jobDetails.getText().contains("Export Result File")&&
						jobDetails.getText().contains("Notification") &&
						jobDetails.getText().contains("Enable Email Notification") &&
						jobDetails.getText().contains("Close")
				) {
					SimpleUtils.pass("I can go to job details page successfully and the adjust budget job details page show well");
					break;
				}else
					SimpleUtils.fail("Adjust budget job details page load failed",false);
			}

		}else if (jobRows.size()==0)
			SimpleUtils.report("There are no jobs that match your criteria. ");
	}

	@Override
	public void iCanGoToAdjustForecastJobDetailsPage(int index) {
		if (jobRows.size()>0) {
			List<WebElement> locationDetailsLinks = jobRows.get(index).findElements(By.cssSelector("button[type='button']"));
			click(locationDetailsLinks.get(index));
			waitForSeconds(5);
			String adbcdf  = jobDetails.getText();
			for (int i = 0; i <jobDetailsSubHeaders.size() ; i++) {
				if (jobDetails.getText().contains("Job Details") && jobDetails.getText().contains("Week for job to take place")
						&& jobDetails.getText().contains("Adjust Forecast Status")&& jobDetails.getText().contains("Forecast Modifications")
						&& jobDetails.getText().contains("Advanced Options")  &&
						jobDetails.getText().contains("Export Result File")&&
						jobDetails.getText().contains("Notification") &&
						jobDetails.getText().contains("Enable Email Notification") &&
						jobDetails.getText().contains("Close")
				) {
					SimpleUtils.pass("I can go to job details page successfully and the adjust forecast job details page show well");
					break;
				}else
					SimpleUtils.fail("Adjust forecast job details page load failed",false);
			}

		}else if (jobRows.size()==0)
			SimpleUtils.report("There are no jobs that match your criteria. ");
	}

	@FindBy(css = "div[ng-click=\"gotoJobs()\"]")
	private WebElement backBtnInJobDetailsPage;
	@FindBy(css = "lg-button[label=\"Close\"]")
	private WebElement closeBtnInJobDetailsPage;
	@Override
	public void iCanBackToJobListPage() {
		if (isElementEnabled(backBtnInJobDetailsPage,3)) {
			click(backBtnInJobDetailsPage);
			waitForSeconds(3);
			if (isElementEnabled(createNewJobBtn,3)) {
				SimpleUtils.pass("I can back to job list page successfully via clicking back button in details page");
			}
		}else
			SimpleUtils.fail("Back button load failed",false);
	}

	@Override
	public void iCanClickCloseBtnInJobDetailsPage() {
		if (isElementEnabled(closeBtnInJobDetailsPage,5)) {
			scrollToBottom();
			click(closeBtnInJobDetailsPage);
			waitForSeconds(3);
			if (isElementEnabled(createNewJobBtn,3)) {
				SimpleUtils.pass("I can back to job list page successfully");
			}
		}else
			SimpleUtils.fail("Close button load failed",false);
	}

	@Override
	public void iCanDownloadExportResultFile() {
		if (isElementEnabled(exportResultFileBtn,5)) {
			click(exportResultFileBtn);
			waitForSeconds(10);
			String downloadPath = propertyMap.get("Download_File_Default_Dir");//when someone run ,need to change this path
			Assert.assertTrue(FileDownloadVerify.isFileDownloaded_Ext(downloadPath, "Task Summary"), "Download successfully");
		}else
			SimpleUtils.fail("Export result file button load failed ",false);
	}

	@Override
	public void iCanDownloadExportTaskSummary() {
		if (isElementEnabled(exportResultSummaryBtn,5)) {
			click(exportResultSummaryBtn);
			waitForSeconds(10);
			String downloadPath = propertyMap.get("Download_File_Default_Dir");//when someone run ,need to change this path
			Assert.assertTrue(FileDownloadVerify.isFileDownloaded_Ext(downloadPath, "Task Summary"), "Download successfully");
		}else
			SimpleUtils.fail("Export result summary button load failed ",false);
	}


	@Override
	public ArrayList<HashMap<String, String>> iCanGetJobInfo(String jobTitle) {
		ArrayList<HashMap<String,String>> jobInfo = new ArrayList<>();
		if (isElementEnabled(searchInputBox, 10)) {
			searchInputBox.clear();
			searchInputBox.sendKeys(jobTitle);
			searchInputBox.sendKeys(Keys.ENTER);
			waitForSeconds(5);
			if (jobRows.size()> 0) {
//				for (WebElement row : jobRows) {
					HashMap<String, String> jobInfoInEachRow = new HashMap<>();
					jobInfoInEachRow.put("jobType", jobRows.get(0).findElement(By.cssSelector("td:nth-child(2)")).getText());
					jobInfoInEachRow.put("jobTitle", jobRows.get(0).findElement(By.cssSelector("td:nth-child(3)>lg-button>button[type=\"button\"]")).getText());
					jobInfoInEachRow.put("createdBy", jobRows.get(0).findElement(By.cssSelector("td:nth-child(4)")).getText());
					jobInfoInEachRow.put("dateCreated", jobRows.get(0).findElement(By.cssSelector("td:nth-child(5) ")).getText());
					jobInfoInEachRow.put("#ofLocations", jobRows.get(0).findElement(By.cssSelector("td:nth-child(6) ")).getText());
					jobInfoInEachRow.put("status", jobRows.get(0).findElement(By.cssSelector("td:nth-child(7) ")).getText());
					jobInfoInEachRow.put("action", jobRows.get(0).findElement(By.cssSelector("td:nth-child(8) ")).getText());
					jobInfo.add(jobInfoInEachRow);
//				}
				return jobInfo;
			}else
				SimpleUtils.fail(jobTitle + "can't been searched", true);
		}

		return null;
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
	@Override
	public void filterJobsByJobTypeAndStatus() throws Exception {
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
					List<WebElement> filters = filterElementInJob.findElements(By.cssSelector("input-filed[type=\"checkbox\"]"/*"[ng-repeat=\"opt in opts\"]"*/));
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
				click(jobTypeFilter);
				SimpleUtils.report("Data for Job Type: '" + jobType + "'" +" selected");
				click(filterBtn);
				if (jobRows.size()<0) {
					SimpleUtils.report("There is no data with this filter: " + jobTypeFilter );
				}else
					SimpleUtils.pass("Jobs: " + jobRows.size() + " job(s) found  ");

			} catch (Exception e) {
				SimpleUtils.fail("Unable to get data", true);
			}
			}

		}
		@FindBy(css="span[ng-click=\"applyAction(5,job)\"]")
		private WebElement copyBtnInJobListPage;
		@FindBy(css="span[ng-click=\"applyAction(6,job)\"]")
		private WebElement archiveBtnInJobListPage;
		@FindBy(css="span[ng-click=\"applyAction(7,job)\"]")
		private WebElement stopBtnInJobListPage;
		@FindBy(css=".lgn-alert-modal")
		private WebElement stopJobPopUpWins;
		@FindBy(css=".lgn-alert-title")
		private WebElement stopJobPopUpWinsWarningTitle;
		@FindBy(css=".lgn-alert-message")
		private WebElement stopJobPopUpWinsWarningDesc;
		@FindBy(css="div.cancel-button > div > button")
		private WebElement cancelBtnInStopJobPopUpWins;
		@FindBy(css="div.ok-button > div > button")
		private WebElement confirmBtnInStopJobPopUpWins;
		@FindBy(css="span[ng-click=\"applyAction(2,job)\"]")
		private WebElement resumeBtnInJobListPage;
		@Override
		public void iCanStopJob(String jobTitle) {
			if (isElementEnabled(stopBtnInJobListPage,5) ) {
				clickTheElement(stopBtnInJobListPage);
				if (verifyJobActionsWarningPageShowWell()) {
					clickTheElement(confirmBtnInStopJobPopUpWins);
					ArrayList<HashMap<String,String>> jobInfo = iCanGetJobInfo(jobTitle);
					if (jobInfo.size()>0 && jobInfo.get(0).get("status").equalsIgnoreCase("Stopped")) {
						SimpleUtils.pass("The job:" +jobTitle + " was stopped successfully");
					}else
						SimpleUtils.fail("The job:" +jobTitle + " failed to stop",false);
					}
			}else
				SimpleUtils.report("There is no stop button and the job is not in progress");
		}
		@FindBy(css=".modal-content")
		private WebElement copyJobPopUpWins;
		@FindBy(css=".lg-modal__content > div:nth-child(1)")
		private WebElement descForTypeOfJobCreating;
		@FindBy(css=".lg-modal__content > div:nth-child(3)")
		private WebElement descForWeekOfJobCreating;
		@FindBy(css="lg-button[label=\"Cancel\"]")
		private WebElement cancelBtnInCopyJobPopUpWins;
		@FindBy(css="lg-button[label=\"Create\"]")
		private WebElement createBtnInCopyJob;
		@Override
		public void iCanCopyJob(String jobTitle) throws Exception {
			ArrayList<HashMap<String,String>> jobInfoBeforeCopy = iCanGetJobInfo(jobTitle);
			if (isElementEnabled(copyBtnInJobListPage,5) ) {
				clickTheElement(copyBtnInJobListPage);
				if (verifyJobCopyPopUpWinsShowWell()) {
					selectWeekForJobToTakePlace();
					click(okBtnInCreateNewJobPage);
					scrollToBottom();
					click(createBtnInCopyJob);
					ArrayList<HashMap<String,String>> jobInfoAfterCopy = iCanGetJobInfo(jobTitle);
					if (!jobInfoAfterCopy.get(0).get("jobTitle").equalsIgnoreCase(jobInfoBeforeCopy.get(0).get("jobTitle"))) {
						SimpleUtils.pass("Job was copied successfully");
					}
				}
			}else {
				SimpleUtils.fail("Copy button load failed",false);
			}

		}

	private boolean verifyJobActionsWarningPageShowWell() {
		if (isElementEnabled(stopJobPopUpWins,5)) {
			if (stopJobPopUpWinsWarningTitle.getText().contains("Are you sure you want to") &&
					stopJobPopUpWinsWarningDesc.getText().contains("Please confirm that you want to perform the above action.")
			&& isElementEnabled(cancelBtnInStopJobPopUpWins,5) && isElementEnabled(confirmBtnInStopJobPopUpWins,5)) {
				SimpleUtils.pass("Job pop up window for each action show well");
				return true;
			}
		}else
			SimpleUtils.fail("Job pop up window load failed",false);
			return false;
	}

	private boolean verifyJobCopyPopUpWinsShowWell() {
		if (isElementEnabled(copyJobPopUpWins,5)) {
			if (descForTypeOfJobCreating.getText().contains("What type of job are you creating") &&
					descForWeekOfJobCreating.getText().contains("Select week for job to take place")
					&& isElementEnabled(cancelBtnInCopyJobPopUpWins,5) && isElementEnabled(okBtnInCreateNewJobPage,5)) {
				SimpleUtils.pass("Copy job pop up window for each action show well");
				return true;
			}
		}else
			SimpleUtils.fail("Copy job  pop up window load failed",false);
		return false;
	}

	@Override
		public void iCanResumeJob(String jobTitle) throws Exception {
			waitForSeconds(5);
		if (isElementEnabled(resumeBtnInJobListPage,5) ) {
			clickTheElement(resumeBtnInJobListPage);
			if (verifyJobActionsWarningPageShowWell()) {
				clickTheElement(confirmBtnInStopJobPopUpWins);
				waitForSeconds(50);
				iCanSearchTheJobWhichICreated(jobTitle);
				if (isElementEnabled(stopBtnInJobListPage,5)) {
					SimpleUtils.pass("Job was resumed successfully");
				}
			}
		}else {
			SimpleUtils.fail("Resume button load failed, may the status of job is completed",false);
		}
	}

		@Override
		public void iCanArchiveJob(String jobTitle) throws Exception {
			if (isElementEnabled(archiveBtnInJobListPage,5) ) {
				clickTheElement(archiveBtnInJobListPage);
				if (verifyJobActionsWarningPageShowWell()) {
					clickTheElement(confirmBtnInStopJobPopUpWins);
					if (isElementEnabled(searchInputBox, 10)) {
						searchInputBox.clear();
						searchInputBox.sendKeys(jobTitle);
						searchInputBox.sendKeys(Keys.ENTER);
						waitForSeconds(5);
						if (jobRows.size() <= 0) {
							SimpleUtils.pass("The job:" +jobTitle + " was archived successfully");
						}else
							SimpleUtils.fail("The job:" +jobTitle + " failed to archive",false);
					}
				}
			}else {
				SimpleUtils.fail("Archive button load failed",false);
			}
		}

	}