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

	@Override
	public void createBtnIsClickable() {
		scrollToBottom();
		click(createBtn);
		SimpleUtils.report("Job creation done");
		waitForSeconds(60); //
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
				if (jobRows.size()>0) {
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
	public void iCanGoToJobDetailsPage(int index) {
		if (jobRows.size()>0) {
			List<WebElement> locationDetailsLinks = jobRows.get(index).findElements(By.cssSelector("button[type='button']"));
			click(locationDetailsLinks.get(index));
			waitForSeconds(5);
			for (int i = 0; i <jobDetailsSubHeaders.size() ; i++) {
				if (jobDetails.getText().contains("Job Details") && jobDetails.getText().contains("Week for job to take place")
						&& jobDetails.getText().contains("Create Schedule  Status")&& jobDetails.getText().contains("Locations Selected")
						&& jobDetails.getText().contains("Notification")) {
					SimpleUtils.pass("I can go to job details page successfully and the create schedule job details page show well");
					break;
				}else
					SimpleUtils.fail("Create schedule job details page load failed",false);
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
				SimpleUtils.pass("I can back to job list page successfully");
			}
		}else
			SimpleUtils.fail("Back button load failed",false);
	}

	@Override
	public void iCanClickCloseBtnInJobDetailsPage() {
		if (isElementEnabled(closeBtnInJobDetailsPage,5)) {
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
			if (jobRows.size() > 0) {

				for (WebElement row : jobRows) {
					HashMap<String, String> jobInfoInEachRow = new HashMap<>();
					jobInfoInEachRow.put("Job Type", row.findElement(By.cssSelector("td:nth-child(2)")).getText());
					jobInfoInEachRow.put("Job Title", row.findElement(By.cssSelector("td:nth-child(3)>lg-button>button[type=\"button\"]")).getText());
					jobInfoInEachRow.put("Created By", row.findElement(By.cssSelector("td:nth-child(4)")).getText());
					jobInfoInEachRow.put("Date Created", row.findElement(By.cssSelector("td:nth-child(5) ")).getText());
					jobInfoInEachRow.put("# of Locations", row.findElement(By.cssSelector("td:nth-child(6) ")).getText());
					jobInfoInEachRow.put("Status", row.findElement(By.cssSelector("td:nth-child(7) ")).getText());
					jobInfoInEachRow.put("Action", row.findElement(By.cssSelector("td:nth-child(8) ")).getText());
					jobInfo.add(jobInfoInEachRow);
				}


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