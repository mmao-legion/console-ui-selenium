package com.legion.pages.core;

import com.legion.pages.*;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.*;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleScheduleShiftTablePage extends BasePage implements ScheduleShiftTablePage {
    public ConsoleScheduleShiftTablePage() {
        PageFactory.initElements(getDriver(), this);
    }



    @FindBy(css = ".sch-day-view-shift")
    private List<WebElement> dayViewAvailableShifts;

    @Override
    public void reduceOvertimeHoursOfActiveWeekShifts() throws Exception {
        List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
        ScheduleMainPage scheduleMainPage = new ConsoleScheduleMainPage();
        for (WebElement activeWeekDay : ScheduleCalendarDayLabels) {
            click(activeWeekDay);
            List<WebElement> availableDayShifts = getAvailableShiftsInDayView();
            if (availableDayShifts.size() != 0) {
                scheduleMainPage.clickOnEditButton();
                for (WebElement shiftWithOT : getAvailableShiftsInDayView()) {
                    WebElement shiftRightSlider = shiftWithOT.findElement(By.cssSelector("div.sch-day-view-shift-pinch.right"));
                    String OTString = "hrs ot";
                    int xOffSet = -50;
                    while (shiftWithOT.getText().toLowerCase().contains(OTString)) {
                        moveDayViewCards(shiftRightSlider, xOffSet);
                    }
                }
                scheduleMainPage.clickSaveBtn();
                break;
            }
        }
    }


    public List<WebElement> getAvailableShiftsInDayView() {
        return dayViewAvailableShifts;
    }

    @FindBy(css = ".drag-target-place")
    List<WebElement> shiftPlaces;
    @FindBy(css = ".week-schedule-shift-title")
    List<WebElement> dayPartTitlesOnSchedulePage;

    @Override
    public void verifyNewAddedShiftFallsInDayPart(String nameOfTheShift, String dayPart) throws Exception {
        int index = 0;
        int indexOfDayPart = 0;
        int indexOfNextDayPart = 0;
        String nextDayPart = getNextDayPart(dayPart);

        if (areListElementVisible(shiftPlaces,15) && areListElementVisible(dayPartTitlesOnSchedulePage, 15)){
            verifySpecificDayPartExists(dayPart);
            index = getIndexInAllShiftPlacesOfTheOnlyAddedOne(nameOfTheShift);
            for (int i = 0; i < shiftPlaces.size(); i++){
                if (shiftPlaces.get(i).getText().toLowerCase().contains(dayPart.toLowerCase())){
                    indexOfDayPart = i;
                    continue;
                }
                if (nextDayPart != null && shiftPlaces.get(i).getText().toLowerCase().contains(nextDayPart.toLowerCase())){
                    indexOfNextDayPart = i;
                    break;
                }
            }
            System.out.println(indexOfDayPart);
            System.out.println(index);
            System.out.println(indexOfNextDayPart);
            if (indexOfNextDayPart == 0 && index > indexOfDayPart){
                SimpleUtils.pass("successful!");
            } else if (indexOfNextDayPart != 0 && index > indexOfDayPart && index < indexOfNextDayPart){
                SimpleUtils.pass("successful!");
            } else {
                SimpleUtils.fail("fail!", false);
            }
        } else {

        }
    }

    public String getNextDayPart(String dayPart) throws Exception{
        String nextDayPart = null;
        for (int i = 0 ; i < dayPartTitlesOnSchedulePage.size(); i++){
            if (dayPartTitlesOnSchedulePage.get(i).getText().equalsIgnoreCase(dayPart)){
                if (i < dayPartTitlesOnSchedulePage.size()-1){
                    nextDayPart = dayPartTitlesOnSchedulePage.get(i+1).getText();
                }
            }
        }
        return nextDayPart;
    }

    public void verifySpecificDayPartExists(String dayPart) throws Exception{
        boolean flag = false;
        for (WebElement dayPartTemp: dayPartTitlesOnSchedulePage){
            System.out.println(dayPartTemp.getText());
            if (dayPartTemp.getText().equalsIgnoreCase(dayPart)){
                flag = true;
                break;
            }
        }
        if (flag){
            SimpleUtils.pass(dayPart + " exists!");
        } else {
            SimpleUtils.fail(dayPart + " doesn't exists!", false);
        }
    }

    public int getIndexInAllShiftPlacesOfTheOnlyAddedOne(String name) throws Exception{
        int index = 0;
        for (int i = 0; i < shiftPlaces.size(); i++){
            System.out.println(shiftPlaces.get(i).getText());
            if (shiftPlaces.get(i).getText().toLowerCase().contains(name.toLowerCase())){
                index = i;
                break;
            }
        }
        return index;
    }


    @FindBy(css = ".shift-container.week-schedule-shift-wrapper")
    private List<WebElement> shiftsOnScheduleView;
    public ArrayList<WebElement> getAllAvailableShiftsInWeekView() {
        ArrayList<WebElement> avalableShifts = new ArrayList<WebElement>();
        if (shiftsOnScheduleView.size() != 0) {
            for (WebElement shiftOnScheduleView : shiftsOnScheduleView) {
                if (shiftOnScheduleView.getText().trim().length() > 0 && shiftOnScheduleView.isDisplayed()) {
                    avalableShifts.add(shiftOnScheduleView);
                }
            }
        }
        return avalableShifts;
    }

    public List<WebElement> getUnAssignedShifts() {
        String unAssignedShiftsLabel = "unassigned";
        List<WebElement> unAssignedShiftsObj = new ArrayList<WebElement>();
        waitForSeconds(5);
        if (areListElementVisible(shiftsOnScheduleView, 10) && shiftsOnScheduleView.size() != 0) {
            for (WebElement shift : shiftsOnScheduleView) {
                if (shift.getText().toLowerCase().contains(unAssignedShiftsLabel) && shift.isDisplayed())
                    unAssignedShiftsObj.add(shift);
            }
        }
        return unAssignedShiftsObj;
    }


    @Override
    public HashMap<String, String> getFourUpComingShifts(boolean isStartTomorrow, String currentTime) throws Exception {
        HashMap<String, String> upComingShifts = new HashMap<>();
        String activeDay = null;
        ScheduleCommonPage scheduleCommonPage = new ConsoleScheduleCommonPage();
        if (isStartTomorrow) {
            activeDay = scheduleCommonPage.getActiveAndNextDay();
            scheduleCommonPage.clickOnNextDaySchedule(activeDay);
            upComingShifts = getAvailableShiftsForDayView(upComingShifts);
        }else {
            upComingShifts = getShiftsForCurrentDayIfStartingSoon(upComingShifts, currentTime);
        }
        while (upComingShifts.size() < 4) {
            activeDay = scheduleCommonPage.getActiveAndNextDay();
            scheduleCommonPage.clickOnNextDaySchedule(activeDay);
            upComingShifts = getAvailableShiftsForDayView(upComingShifts);
        }
        if (upComingShifts.size() >= 4) {
            SimpleUtils.pass("Get four shifts successfully!");
        }else {
            SimpleUtils.fail("Failed to get at least four shifts!", false);
        }
        return upComingShifts;
    }


    public HashMap<String, String> getAvailableShiftsForDayView(HashMap<String, String> upComingShifts) throws Exception {
        String name = null;
        String role = null;
        if (areListElementVisible(dayViewAvailableShifts, 15)) {
            for (WebElement dayViewAvailableShift : dayViewAvailableShifts) {
                name = dayViewAvailableShift.findElement(By.className("sch-day-view-shift-worker-name")).getText().toLowerCase();
                if (name.contains("(")) {
                    name = name.substring(0, name.indexOf("(") - 1);
                }
                if (!name.contains("open") && !name.contains("unassigned")) {
                    role = dayViewAvailableShift.findElement(By.className("sch-day-view-shift-worker-title-role")).getText().toLowerCase();
                    upComingShifts.put(name, role);
                }
            }
        } else {
            SimpleUtils.fail("Day View Available shifts failed to load!", true);
        }
        return upComingShifts;
    }

    @FindBy(css = "div.popover div:nth-child(3)>div.ng-binding")
    private WebElement timeDuration;
    public HashMap<String, String> getShiftsForCurrentDayIfStartingSoon(HashMap<String, String> upComingShifts, String currentTime) throws Exception {
        String name = null;
        String role = null;
        ScheduleCommonPage scheduleCommonPage = new ConsoleScheduleCommonPage();
        if (areListElementVisible(dayViewAvailableShifts, 15)) {
            for (WebElement dayViewAvailableShift : dayViewAvailableShifts) {
                WebElement hoverInfo = dayViewAvailableShift.findElement(By.className("day-view-shift-hover-info-icon"));
                if (hoverInfo != null) {
                    clickTheElement(hoverInfo);
                    if (isElementLoaded(timeDuration, 5)) {
                        String startTime = timeDuration.getText().split("-")[0];
                        clickTheElement(hoverInfo);
                        int shiftStartMinutes = scheduleCommonPage.getMinutesFromTime(startTime);
                        int currentMinutes = scheduleCommonPage.getMinutesFromTime(currentTime);
                        if (shiftStartMinutes > currentMinutes) {
                            name = dayViewAvailableShift.findElement(By.className("sch-day-view-shift-worker-name")).getText().toLowerCase();
                            if (name.contains("(")) {
                                name = name.substring(0, name.indexOf("(") - 1);
                            }
                            if (!name.contains("open") && !name.contains("unassigned")) {
                                role = dayViewAvailableShift.findElement(By.className("sch-day-view-shift-worker-title-role")).getText().toLowerCase();
                                upComingShifts.put(name, role);
                            }
                        }
                    }else {
                        SimpleUtils.fail("Failed to get the time duration!", true);
                    }
                }else {
                    SimpleUtils.fail("Failed to get the hover info element!", true);
                }
            }
        }else {
            SimpleUtils.fail("Day View Available shifts failed to load!", true);
        }
        return upComingShifts;
    }


    @FindBy(xpath= "//day-week-picker/div/div/div[3]")
    private WebElement calendarNavigationPreviousWeek;
    public void scheduleUpdateAccordingToSelectWeek() throws Exception {
        if (isElementLoaded(calendarNavigationPreviousWeek,5) ) {
            String preWeekText = calendarNavigationPreviousWeek.getText().replace("\n","").replace("-","");
            String preWeekText2 = preWeekText.trim().substring(preWeekText.length()-2);
            click(calendarNavigationPreviousWeek);
            String scheCalDay = getScheduleDayRange().trim();
            if (areListElementVisible(schCalendarDateLabel,10) && scheCalDay.trim().contains(preWeekText2.trim())) {
                SimpleUtils.pass("data is getting updating on Schedule page according to corresponding week");
            }else {
                SimpleUtils.fail("schedule canlendar is not updating according to corresponding week",true);
            }
        }else {
            SimpleUtils.fail("no next week calendar",true);
        }
    }

    @FindBy(css = "div.sch-calendar-date-label>span")
    private List<WebElement> schCalendarDateLabel;
    public String getScheduleDayRange() throws Exception {
        SmartCardPage smartCardPage = new ConsoleSmartCardPage();
        String dayRangeText = "";
        if (schCalendarDateLabel.size() != 0)
            for (WebElement scheCalDay : schCalendarDateLabel) {
                if (scheCalDay.isDisplayed()) {
                    if (dayRangeText == "")
                        dayRangeText = scheCalDay.getText();
                    else
                        dayRangeText = dayRangeText + " | " + scheCalDay.getText();
                } else if (!scheCalDay.isDisplayed()) {
                    while (smartCardPage.isSmartCardScrolledToRightActive() == true) {
                        if (dayRangeText == "")
                            dayRangeText = scheCalDay.getText();
                        else
                            dayRangeText = dayRangeText + " | " + scheCalDay.getText();
                    }
                }
            }

        return dayRangeText;
    }


    @FindBy(css = "div.sch-calendar-day-dimension")
    private List<WebElement> weekViewDaysAndDates;

    @FindBy(css = "div.sch-week-view-day-summary")
    private List<WebElement> weekDaySummeryHoursAndTeamMembers;
    public void getHoursAndTeamMembersForEachDaysOfWeek() {
        String weekDaysAndDatesText = "";
        String weekDaysHoursAndTMsCount = "";
        try {
            if (weekViewDaysAndDates.size() != 0) {
                for (WebElement weekViewDayAndDate : weekViewDaysAndDates) {
                    if (weekDaysAndDatesText != "")
                        weekDaysAndDatesText = weekDaysAndDatesText + " | " + weekViewDayAndDate.getText();
                    else
                        weekDaysAndDatesText = weekViewDayAndDate.getText();
                }
                SimpleUtils.report("Active Week Days And Dates: " + weekDaysAndDatesText);
            }
            if (weekDaySummeryHoursAndTeamMembers.size() != 0) {
                for (WebElement weekDayHoursAndTMs : weekDaySummeryHoursAndTeamMembers) {
                    if (weekDaysHoursAndTMsCount != "")
                        weekDaysHoursAndTMsCount = weekDaysHoursAndTMsCount + " | " + weekDayHoursAndTMs.getText();
                    else
                        weekDaysHoursAndTMsCount = weekDayHoursAndTMs.getText();
                }
                SimpleUtils.report("Active Week Hours And TeamMembers: " + weekDaysHoursAndTMsCount);
            }
        } catch (Exception e) {
            SimpleUtils.fail("Unable to get Hours & Team Members for active Week!", true);
        }
    }


    public boolean verifyActiveWeekDailyScheduleHoursInWeekView() {
        Float weekDaysScheduleHours = (float) 0;
        Float activeWeekScheduleHoursOnCard = (float) 0;
        try {
            SmartCardPage smartCardPage = new ConsoleSmartCardPage();
            activeWeekScheduleHoursOnCard = smartCardPage.getScheduleLabelHoursAndWages().get(ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.scheduledHours.getValue());
            if (weekDaySummeryHoursAndTeamMembers.size() != 0) {
                for (WebElement weekDayHoursAndTMs : weekDaySummeryHoursAndTeamMembers) {
                    float dayScheduleHours = Float.parseFloat(weekDayHoursAndTMs.getText().split("HRs")[0]);
                    weekDaysScheduleHours = (float) (weekDaysScheduleHours + Math.round(dayScheduleHours * 10.0) / 10.0);
                }
            }
            float totalShiftSizeForWeek = calcTotalScheduledHourForDayInWeekView();
//            System.out.println("sum" + totalShiftSizeForWeek);
            if (totalShiftSizeForWeek == activeWeekScheduleHoursOnCard) {
                SimpleUtils.pass("Sum of all the shifts in a week equal to Week Schedule Hours!('" + totalShiftSizeForWeek + "/" + activeWeekScheduleHoursOnCard + "')");
                return true;
            } else {
                SimpleUtils.fail("Sum of all the shifts in an week is not equal to Week scheduled Hour!('" + totalShiftSizeForWeek + "/" + activeWeekScheduleHoursOnCard + "')", false);
            }
//            if(weekDaysScheduleHours.equals(activeWeekScheduleHoursOnCard))
//            {
//                SimpleUtils.pass("Sum of Daily Schedule Hours equal to Week Schedule Hours! ('"+weekDaysScheduleHours+ "/"+activeWeekScheduleHoursOnCard+"')");
//                return true;
//            }
        } catch (Exception e) {
            SimpleUtils.fail("Unable to Verify Daily Schedule Hours with Week Schedule Hours!", true);
        }
        return false;
    }


    @FindBy(xpath = "//*[@class='shift-hover-seperator']/following-sibling::div[1]/div[1]")
    private WebElement shiftSize;

    @FindBy(css = "img[ng-if*='hasViolation']")
    private List<WebElement> infoIcon;


    public float calcTotalScheduledHourForDayInWeekView() throws Exception {
        float sumOfAllShiftsLength = 0;
        for (int i = 0; i < infoIcon.size(); i++) {
            if (isElementEnabled(infoIcon.get(i))) {
                click(infoIcon.get(i));
                String[] TMShiftSize = shiftSize.getText().split(" ");
                float shiftSizeInHour = Float.valueOf(TMShiftSize[0]);
                sumOfAllShiftsLength = sumOfAllShiftsLength + shiftSizeInHour;

            } else {
                SimpleUtils.fail("Shift not loaded successfully in week view", false);
            }
        }
        return (sumOfAllShiftsLength);

    }


    @Override
    public void verifyScheduledHourNTMCountIsCorrect() throws Exception {
        getHoursAndTeamMembersForEachDaysOfWeek();
        verifyActiveWeekDailyScheduleHoursInWeekView();
        verifyActiveWeekTeamMembersCountAvailableShiftCount();
    }

    public boolean verifyActiveWeekTeamMembersCountAvailableShiftCount() {
        int weekDaysTMsCount = 0;
        int weekDaysShiftsCount = 0;
        try {
            if (weekDaySummeryHoursAndTeamMembers.size() != 0) {
                for (WebElement weekDayHoursAndTMs : weekDaySummeryHoursAndTeamMembers) {
                    String TeamMembersCount = weekDayHoursAndTMs.getText().split("HRs")[1].replace("TMs", "").trim();
                    weekDaysTMsCount = weekDaysTMsCount + Integer.parseInt(TeamMembersCount);
                }
            }


            if (shiftsOnScheduleView.size() != 0) {
                for (WebElement shiftOnScheduleView : shiftsOnScheduleView) {
                    if (shiftOnScheduleView.getText().trim().length() > 0 && shiftOnScheduleView.isDisplayed()) {
                        weekDaysShiftsCount = weekDaysShiftsCount + 1;
                    }
                }
            }

            if (weekDaysTMsCount == weekDaysShiftsCount) {
                SimpleUtils.pass("Sum of Daily Team Members Count equal to Sum of Daily Shifts Count! ('" + weekDaysTMsCount + "/" + weekDaysShiftsCount + "')");
                return true;
            } else {
                SimpleUtils.fail("Sum of Daily Team Members Count not equal to Sum of Daily Shifts Count! ('" + weekDaysTMsCount + "/" + weekDaysShiftsCount + "')", true);
            }
        } catch (Exception e) {
            SimpleUtils.fail("Unable to Verify Daily Team Members Count with Daily Shifts Count!", true);
        }
        return false;
    }


    @FindBy(css = "div.sch-day-view-grid-header.fill")
    private List<WebElement> dayViewShiftsTimeDuration;
    public ArrayList<String> getScheduleDayViewGridTimeDuration() {
        ArrayList<String> gridTimeDurations = new ArrayList<String>();
        if (dayViewShiftsTimeDuration.size() != 0) {
            for (WebElement timeDuration : dayViewShiftsTimeDuration) {
                gridTimeDurations.add(timeDuration.getText());
            }
        }

        return gridTimeDurations;
    }



    @FindBy(css = "div.sch-day-view-grid-header.tm-count.guidance")
    private List<WebElement> dayViewbudgetedTMCount;
    public ArrayList<String> getScheduleDayViewBudgetedTeamMembersCount() {
        ArrayList<String> BudgetedTMsCount = new ArrayList<String>();
        if (dayViewbudgetedTMCount.size() != 0) {
            for (WebElement BudgetedTMs : dayViewbudgetedTMCount) {
                BudgetedTMsCount.add(BudgetedTMs.getText());
            }
        }

        return BudgetedTMsCount;
    }


    @FindBy(xpath = "//div[contains(@class,'sch-day-view-grid-header tm-count') and not(contains(@class,'guidance'))]")
    private List<WebElement> dayViewScheduleTMsCount;
    public ArrayList<String> getScheduleDayViewScheduleTeamMembersCount() {
        ArrayList<String> ScheduledTMsCount = new ArrayList<String>();
        if (dayViewScheduleTMsCount.size() != 0) {
            for (WebElement scheduleTMs : dayViewScheduleTMsCount) {
                ScheduledTMsCount.add(scheduleTMs.getText());
            }
        }

        return ScheduledTMsCount;
    }


    @FindBy(className = "week-schedule-shift-wrapper")
    private List<WebElement> shiftsWeekView;

    @Override
    public void verifyShiftsChangeToOpenAfterTerminating(List<Integer> indexes, String name, String currentTime) throws Exception {
        String open = "Open";
        String unAssigned = "Unassigned";
        String shiftTime = null;
        if (indexes.size() > 0 && areListElementVisible(shiftsWeekView, 5)) {
            for (int index : indexes) {
                WebElement workerName = shiftsWeekView.get(index).findElement(By.className("week-schedule-worker-name"));
                if (workerName != null) {
                    if (workerName.getText().contains(name)) {
                        shiftTime = shiftsWeekView.get(index).findElement(By.className("week-schedule-shift-time")).getText();
                        boolean isConvertToOpen = compareShiftTimeWithCurrentTime(shiftTime, currentTime);
                        SimpleUtils.report("IsConvertToOpen: " + isConvertToOpen + " index is: " + index);
                        if (!isConvertToOpen) {
                            SimpleUtils.pass("Shift isn't change to open or unassigned since the current is earlier than the shift end time!");
                        }else {
                            SimpleUtils.fail("Shift doesn't change to open or unassigned, worker name is: " + workerName.getText(), true);
                        }
                    }else if (workerName.getText().equalsIgnoreCase(open) || workerName.getText().equalsIgnoreCase(unAssigned)) {
                        SimpleUtils.report("Index is: " + index);
                        SimpleUtils.pass("Shift is changed to open or unassigned!");
                    }else {
                        SimpleUtils.fail("Shift doesn't change to open or unassigned, worker name is: " + workerName.getText(), true);
                    }
                }else {
                    SimpleUtils.fail("Failed to find the worker name element!", true);
                }
            }
        }else {
            SimpleUtils.fail("Shifts on week view failed to load!", false);
        }
    }

    public boolean compareShiftTimeWithCurrentTime(String shiftTime, String currentTime) {
        ScheduleCommonPage scheduleCommonPage = new ConsoleScheduleCommonPage();
        boolean isConvertToOpen = false;
        int shiftStartMinutes = 0;
        int currentMinutes = 0;
        String[] startAndEndTime = shiftTime.split("-");
        if (startAndEndTime.length == 2) {
            String startTime = startAndEndTime[0].trim();
            shiftStartMinutes = scheduleCommonPage.getMinutesFromTime(startTime);
            currentMinutes = scheduleCommonPage.getMinutesFromTime(currentTime);
            SimpleUtils.report(startTime);
            SimpleUtils.report("Convert start time to Minute: " + shiftStartMinutes);
            SimpleUtils.report(currentTime);
            SimpleUtils.report("Convert current time to Minute: " + currentMinutes);
        }
        if (currentMinutes < shiftStartMinutes) {
            isConvertToOpen = true;
        }
        return isConvertToOpen;
    }

    @FindBy (css = "div.sch-day-view-shift-worker-detail")
    private List<WebElement> scheduleTableWeekViewWorkerDetail;

    @FindBy(css = ".sch-calendar-day-summary")
    private List<WebElement> daySummaries;

    @Override
    public float newCalcTotalScheduledHourForDayInWeekView() throws Exception {
        float sumOfAllShiftsLength = 0;
        if (areListElementVisible(daySummaries,10)){
            for (int i=0; i<daySummaries.size();i++){
                String[] TMShiftSize = daySummaries.get(i).findElement(By.cssSelector("span:nth-child(1)")).getText().split(" ");
                float shiftSizeInHour = Float.valueOf(TMShiftSize[0]);
                sumOfAllShiftsLength = sumOfAllShiftsLength + shiftSizeInHour;
            }
        } else {
            SimpleUtils.fail("weekDaySummeryHoursAndTeamMembers are not loaded!", false);
        }
        return (sumOfAllShiftsLength);

    }

    public boolean newVerifyActiveWeekDailyScheduleHoursInWeekView() throws Exception {
        Float weekDaysScheduleHours = 0.0f;
        Float activeWeekScheduleHoursOnCard = 0.0f;
        SmartCardPage smartCardPage = new ConsoleSmartCardPage();
        activeWeekScheduleHoursOnCard = smartCardPage.getScheduleLabelHoursAndWages().get(ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.scheduledHours.getValue())
                + smartCardPage.getScheduleLabelHoursAndWages().get(ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.otherHours.getValue());
        if (weekDaySummeryHoursAndTeamMembers.size() != 0) {
            for (WebElement weekDayHoursAndTMs : weekDaySummeryHoursAndTeamMembers) {
                float dayScheduleHours = Float.parseFloat(weekDayHoursAndTMs.getText().split("HRs")[0]);
                weekDaysScheduleHours = (float) (weekDaysScheduleHours + Math.round(dayScheduleHours * 10.0) / 10.0);
            }
        }
        float totalShiftSizeForWeek = newCalcTotalScheduledHourForDayInWeekView();
        if (activeWeekScheduleHoursOnCard - totalShiftSizeForWeek <= 0.06) {
            SimpleUtils.pass("Sum of all the shifts in a week equal to Week Schedule Hours!('" + totalShiftSizeForWeek + "/" + activeWeekScheduleHoursOnCard + "')");
            return true;
        } else {
            SimpleUtils.fail("Sum of all the shifts in an week is not equal to Week scheduled Hour!('" + totalShiftSizeForWeek + "/" + activeWeekScheduleHoursOnCard + "')", false);
        }
        return false;
    }

    @FindBy(css = "div.day-view-shift-hover-info-icon")
    private List<WebElement> scheduleInfoIconInDayView;

    @FindBy(xpath = "//div/shift-hover/div/div[5]/div[1]")
    private WebElement  workHoursInDayViewFromPopUp;
    public float getActiveShiftHoursInDayView() {
        Float totalDayWorkTime = 0.0f;
        if (areListElementVisible(scheduleTableWeekViewWorkerDetail,5) ) {
            for (int i = 0; i <scheduleTableWeekViewWorkerDetail.size() ; i++) {
                clickTheElement(scheduleInfoIconInDayView.get(i));
                String[] timeDurationForTMContext = workHoursInDayViewFromPopUp.getText().split(" ");
                float shiftSizeInHour = Float.valueOf(timeDurationForTMContext[0]);
                totalDayWorkTime = totalDayWorkTime+shiftSizeInHour;
            }
        }else
            totalDayWorkTime = 0.0f;
        return totalDayWorkTime;
    }


    @FindBy(className = "week-schedule-shift-title")
    private List<WebElement> availableJobTitleListInWeekView;

    @FindBy(className = "sch-group-label")
    private List<WebElement> availableJobTitleListInDayView;

    public ArrayList<String> getAvailableJobTitleListInWeekView(){
        ArrayList<String> availableJobTitleList = new ArrayList<>();
        for (WebElement jobTitle:availableJobTitleListInWeekView
        ) {
            availableJobTitleList.add(jobTitle.getText().toLowerCase().trim());
        }

        return availableJobTitleList;
    }

    public ArrayList<String> getAvailableJobTitleListInDayView(){
        ArrayList<String> availableJobTitleList = new ArrayList<>();
        for (WebElement jobTitle:availableJobTitleListInDayView
        ) {
            availableJobTitleList.add(jobTitle.getText().toLowerCase().trim());
        }

        return availableJobTitleList;
    }

    @FindBy(css = ".sch-day-view-shift .sch-shift-worker-img-cursor")
    private List<WebElement> profileIconsInDayView;

    @FindBy(css = ".shift-container.week-schedule-shift-wrapper")
    private List<WebElement> shifts;

    @FindBy(css = ".week-schedule-shift .shift-container .rows .worker-image-optimized img")
    private List<WebElement> profileIcons;
    public WebElement clickOnProfileIconOfShiftInDayView(String openOrNot) throws Exception {
        ShiftOperatePage shiftOperatePage = new ConsoleShiftOperatePage();
        WebElement selectedShift = null;
        if(shiftOperatePage.isProfileIconsEnable()&& areListElementVisible(dayViewAvailableShifts, 10)) {
            if (openOrNot.toLowerCase().contains("open")){
                int randomIndex = (new Random()).nextInt(profileIconsInDayView.size());
                while (!dayViewAvailableShifts.get(randomIndex).findElement(By.cssSelector(".sch-day-view-shift-worker-name")).getText().toLowerCase().contains("open")){
                    randomIndex = (new Random()).nextInt(dayViewAvailableShifts.size());
                }
                clickTheElement(profileIconsInDayView.get(randomIndex));
                selectedShift = dayViewAvailableShifts.get(randomIndex);
            } else {
                int randomIndex = (new Random()).nextInt(profileIconsInDayView.size());
                while (dayViewAvailableShifts.get(randomIndex).findElement(By.cssSelector(".sch-day-view-shift-worker-name")).getText().toLowerCase().contains("open")){
                    randomIndex = (new Random()).nextInt(profileIconsInDayView.size());
                }
                clickTheElement(profileIconsInDayView.get(randomIndex));
                selectedShift = dayViewAvailableShifts.get(randomIndex);
            }
        } else {
            SimpleUtils.fail("Can't Click on Profile Icon due to unavailability ",false);
        }
        return selectedShift;
    }


    @Override
    public WebElement clickOnProfileOfUnassignedShift() throws Exception {
        ShiftOperatePage shiftOperatePage = new ConsoleShiftOperatePage();
        WebElement selectedShift = null;
        if(shiftOperatePage.isProfileIconsEnable()&& areListElementVisible(shifts, 10)) {
            int randomIndex = (new Random()).nextInt(profileIcons.size());
            while (!profileIcons.get(randomIndex).getAttribute("src").contains("unassignedShiftImage")){
                randomIndex = (new Random()).nextInt(profileIcons.size());
            }
            clickTheElement(profileIcons.get(randomIndex));
            selectedShift = shifts.get(randomIndex);
        } else if (areListElementVisible(scheduleTableWeekViewWorkerDetail, 10) && areListElementVisible(dayViewAvailableShifts, 10)) {
            int randomIndex = (new Random()).nextInt(scheduleTableWeekViewWorkerDetail.size());
            while (!dayViewAvailableShifts.get(randomIndex).findElement(By.className("sch-day-view-shift-worker-name")).getText().contains("Open")){
                randomIndex = (new Random()).nextInt(scheduleTableWeekViewWorkerDetail.size());
            }
            clickTheElement(scheduleTableWeekViewWorkerDetail.get(randomIndex));
            selectedShift = dayViewAvailableShifts.get(randomIndex);
        } else {
            SimpleUtils.fail("Can't Click on Profile Icon due to unavailability ",false);
        }
        return selectedShift;
    }

    @FindBy(className = "week-schedule-shift")
    private List<WebElement> weekShifts;
    @FindBy(css = ".sch-shift-hover div:nth-child(3)>div.ng-binding")
    private WebElement shiftDuration;
    @FindBy(css = ".shift-hover-subheading.ng-binding:not([ng-if])")
    private WebElement shiftJobTitleAsWorkRole;
    @Override
    public List<String> getTheShiftInfoByIndex(int index) throws Exception {
        ShiftOperatePage shiftOperatePage = new ConsoleShiftOperatePage();
        waitForSeconds(3);
        List<String> shiftInfo = new ArrayList<>();
        if (areListElementVisible(weekShifts, 20) && index < weekShifts.size()) {
            String firstName = weekShifts.get(index).findElement(By.className("week-schedule-worker-name")).getText();
            if (!firstName.equalsIgnoreCase("Open") && !firstName.equalsIgnoreCase("Unassigned")) {
                String dayIndex = weekShifts.get(index).getAttribute("data-day-index");
                String lastName = shiftOperatePage.getTMDetailNameFromProfilePage(weekShifts.get(index)).split(" ")[1].trim();
                waitForSeconds(2);
                String jobTitle = weekShifts.get(index).findElement(By.cssSelector(".rows .week-schedule-role-name")).getText();
                String shiftTimeWeekView = weekShifts.get(index).findElement(By.className("week-schedule-shift-time")).getText();
                WebElement infoIcon = weekShifts.get(index).findElement(By.className("week-schedule-shit-open-popover"));
                clickTheElement(infoIcon);
                String workRole = shiftJobTitleAsWorkRole.getText().split("as")[1].trim();
                if (isElementLoaded(shiftDuration, 10)) {
                    String shiftTime = shiftDuration.getText();
                    shiftInfo.add(firstName);
                    shiftInfo.add(dayIndex);
                    shiftInfo.add(shiftTime);
                    shiftInfo.add(jobTitle);
                    shiftInfo.add(workRole);
                    shiftInfo.add(lastName);
                    shiftInfo.add(shiftTimeWeekView);
                }
                //To close the info popup
                clickTheElement(weekShifts.get(index));
            } else {
                //SimpleUtils.report("This is an Open Shift");
                //return shiftInfo;
                //For open shift
                String dayIndex = weekShifts.get(index).getAttribute("data-day-index");
                String lastName = "";
                if (firstName.equalsIgnoreCase("Unassigned")){
                    lastName = "unassigned";
                } else
                    lastName = "open";
                String jobTitle = "";
                String shiftTimeWeekView = weekShifts.get(index).findElement(By.className("week-schedule-shift-time")).getText();
                WebElement infoIcon = weekShifts.get(index).findElement(By.className("week-schedule-shit-open-popover"));
                clickTheElement(infoIcon);
                String workRole = shiftJobTitleAsWorkRole.getText().trim();
                if (isElementLoaded(shiftDuration, 10)) {
                    String shiftTime = shiftDuration.getText();
                    shiftInfo.add(firstName);
                    shiftInfo.add(dayIndex);
                    shiftInfo.add(shiftTime);
                    shiftInfo.add(jobTitle);
                    shiftInfo.add(workRole);
                    shiftInfo.add(lastName);
                    shiftInfo.add(shiftTimeWeekView);
                }
                //To close the info popup
                clickTheElement(weekShifts.get(index));
            }
        } else {
            SimpleUtils.fail("Schedule Page: week shifts not loaded successfully!", false);
        }
        if (shiftInfo.size() != 7) {
            SimpleUtils.fail("Failed to get the shift info!", false);
        }
        return shiftInfo;
    }


    @Override
    public List<String> getTheShiftInfoInDayViewByIndex(int index) throws Exception {
        ShiftOperatePage shiftOperatePage = new ConsoleShiftOperatePage();
        ScheduleCommonPage scheduleCommonPage = new ConsoleScheduleCommonPage();
        List<String> shiftInfo = new ArrayList<>();
        if (areListElementVisible(dayViewAvailableShifts, 20) && index < dayViewAvailableShifts.size()) {
            String firstName = dayViewAvailableShifts.get(index).
                    findElement(By.className("sch-day-view-shift-worker-name")).getText().split(" ")[0];
            if (!firstName.equalsIgnoreCase("Open")) {
                String lastName = shiftOperatePage.getTMDetailNameFromProfilePage(dayViewAvailableShifts.get(index)).split(" ")[1].trim();
                String shiftTimeWeekView = dayViewAvailableShifts.get(index).findElement(By.className("sch-day-view-shift-time")).getText();
                WebElement infoIcon = dayViewAvailableShifts.get(index).findElement(By.className("day-view-shift-hover-info-icon"));
                clickTheElement(infoIcon);
                String workRole = shiftJobTitleAsWorkRole.getText().split("as")[1].trim();
                String jobTitle = shiftJobTitleAsWorkRole.getText().split("as")[0].trim();
                if (isElementLoaded(shiftDuration, 10)) {
                    String shiftTime = shiftDuration.getText();
                    shiftInfo.add(firstName);
                    shiftInfo.add(String.valueOf(scheduleCommonPage.getTheIndexOfCurrentDayInDayView()));
                    shiftInfo.add(shiftTime);
                    shiftInfo.add(jobTitle);
                    shiftInfo.add(workRole);
                    shiftInfo.add(lastName);
                    shiftInfo.add(shiftTimeWeekView);
                }
                //To close the info popup
                clickTheElement(dayViewAvailableShifts.get(index));
            } else {
                //SimpleUtils.report("This is an Open Shift");
                //return shiftInfo;
                //For open shift
                //String dayIndex = weekShifts.get(index).getAttribute("data-day-index");
                String lastName = "";
                if (firstName.equalsIgnoreCase("Unassigned")){
                    lastName = "unassigned";
                } else
                    lastName = "open";
                String jobTitle = "";
                String shiftTimeWeekView = dayViewAvailableShifts.get(index).findElement(By.className("sch-day-view-shift-time")).getText();
                WebElement infoIcon = dayViewAvailableShifts.get(index).findElement(By.className("day-view-shift-hover-info-icon"));
                clickTheElement(infoIcon);
                String workRole = shiftJobTitleAsWorkRole.getText().trim();
                if (isElementLoaded(shiftDuration, 10)) {
                    String shiftTime = shiftDuration.getText();
                    shiftInfo.add(firstName);
                    //shiftInfo.add(dayIndex);
                    shiftInfo.add(String.valueOf(scheduleCommonPage.getTheIndexOfCurrentDayInDayView()));
                    shiftInfo.add(shiftTime);
                    shiftInfo.add(jobTitle);
                    shiftInfo.add(workRole);
                    shiftInfo.add(lastName);
                    shiftInfo.add(shiftTimeWeekView);
                }
                //To close the info popup
                clickTheElement(dayViewAvailableShifts.get(index));
            }
        } else {
            SimpleUtils.fail("Schedule Page: week shifts not loaded successfully!", false);
        }
        if (shiftInfo.size() != 7) {
            SimpleUtils.fail("Failed to get the shift info!", false);
        }
        return shiftInfo;
    }

    public void verifySearchResult (String firstNameOfTM, String lastNameOfTM, String workRole, String jobTitle, List<WebElement> searchResults) throws Exception {
        ShiftOperatePage shiftOperatePage = new ConsoleShiftOperatePage();
        if (searchResults !=null && searchResults.size()>0) {
            if (firstNameOfTM != null) {
                for (int i=0; i< searchResults.size(); i++) {
                    String[] tmDetailName = shiftOperatePage.getTMDetailNameFromProfilePage(searchResults.get(i)).split(" ");
                    if (firstNameOfTM.equals(tmDetailName[0])|| firstNameOfTM.equals(tmDetailName[1]) || tmDetailName[0].contains(firstNameOfTM)
                            || tmDetailName[1].contains(firstNameOfTM)) {
                        SimpleUtils.pass("The search result display correctly when search by TM first name");
                    } else {
                        SimpleUtils.fail("The search result incorrect when search by TM first name, the expected name is: " + firstNameOfTM+ ". The actual name is: " + tmDetailName[0] +" " +tmDetailName[1],false);
                        break;
                    }
                }
            } else if (lastNameOfTM != null) {
                for (int i=0; i< searchResults.size(); i++) {
                    String[] tmDetailName = shiftOperatePage.getTMDetailNameFromProfilePage(searchResults.get(i)).split(" ");
                    if (tmDetailName[0].contains(lastNameOfTM) || tmDetailName[1].contains(lastNameOfTM)) {
                        SimpleUtils.pass("The search result display correctly when search by TM last name");
                    } else {
                        SimpleUtils.fail("The search result incorrect when search by TM last name",false);
                        break;
                    }
                }
            }
            else if (workRole != null) {
                String[] workRoleWords = workRole.split(" ");
                for (int i=0; i <searchResults.size(); i++) {
                    scrollToElement(searchResults.get(i));
                    Map<String, String> shiftInfo= getShiftInfoFromInfoPopUp(searchResults.get(i));
                    String shiftWorkRole = shiftInfo.get("WorkRole");
                    String shiftJobTitle = shiftInfo.get("JobTitle");
                    if (workRole.equals(shiftWorkRole)|| workRole.equals(shiftJobTitle)) {
                        SimpleUtils.pass("The search result display correctly when search by Work Role");
                    } else if(workRoleWords.length>1) {
                        for (int j=0; j< workRoleWords.length; j++){
                            if (shiftWorkRole.contains(workRoleWords[j])){
                                SimpleUtils.pass("The search result display correctly when search by Work Role");
                                break;
                            }
                        }
                    } else {
                        SimpleUtils.fail("The search result incorrect when search by Work Role, expected: " + workRole
                                + ", actual is: " + getShiftInfoFromInfoPopUp(searchResults.get(i)).get("WorkRole"),false);
                        break;
                    }
                }
            } else if (jobTitle != null) {
                String[] jobTitleWords = jobTitle.split(" ");
                for (int i=0; i <searchResults.size(); i++) {
                    scrollToElement(searchResults.get(i));
                    Map<String, String> shiftInfo= getShiftInfoFromInfoPopUp(searchResults.get(i));
                    String shiftWorkRole = shiftInfo.get("WorkRole");
                    String shiftJobTitle = shiftInfo.get("JobTitle");
                    if (jobTitle.equals(shiftJobTitle)|| jobTitle.equals(shiftWorkRole)) {
                        SimpleUtils.pass("The search result display correctly when search by Job Title");
                    } else if(jobTitleWords.length>1) {
                        for (int j=0; j< jobTitleWords.length; j++){
                            if (shiftWorkRole.contains(jobTitleWords[j])){
                                SimpleUtils.pass("The search result display correctly when search by Job Title");
                                break;
                            }
                        }
                    } else {
                        SimpleUtils.fail("The search result incorrect when search by Job Title",false);
                        break;
                    }
                }
            } else {
                SimpleUtils.fail("Verify texts all are null!",false);
            }
        } else {
            SimpleUtils.fail("There is no search result!",false);
        }
    }


    @FindBy(className = "popover-content")
    private WebElement popOverContent;
    public Map<String, String> getShiftInfoFromInfoPopUp(WebElement shift) {
        ScheduleCommonPage scheduleCommonPage = new ConsoleScheduleCommonPage();
        Map<String, String> shiftInfo = new HashMap<String, String>();
        if (shift != null) {
            if(scheduleCommonPage.isScheduleDayViewActive()){
                click(shift.findElement(By.className("day-view-shift-hover-info-icon")));
                waitForSeconds(2);
            } else
                click(shift.findElement(By.className("week-schedule-shit-open-popover")));

        } else {
            SimpleUtils.fail("Selected shift is null!",true);
        }
        if (isElementEnabled(popOverContent, 5)) {
            String[] jobTitleAndWorkRole = popOverContent.findElement(By.cssSelector(".shift-hover-subheading.ng-binding")).getText().split("as");
            if (jobTitleAndWorkRole.length==1){
                //add job title
                shiftInfo.put("JobTitle", "");
                //add work role
                shiftInfo.put("WorkRole", jobTitleAndWorkRole[0].trim());
            } else {
                //add job title
                shiftInfo.put("JobTitle", jobTitleAndWorkRole[0].trim());
                //add work role
                shiftInfo.put("WorkRole", jobTitleAndWorkRole[1].trim());
            }

        }
        return shiftInfo;
    }


    @FindBy(css = ".sch-shift-hover.visible")
    private WebElement infoTextFromInfoIcon;
    @Override
    public String getIIconTextInfo(WebElement shift) throws Exception{
        ScheduleCommonPage scheduleCommonPage = new ConsoleScheduleCommonPage();
        if (isElementLoaded(shift, 5)){
            waitForSeconds(3);
            scrollToElement(shift);
            if(scheduleCommonPage.isScheduleDayViewActive()){
                click(shift.findElement(By.cssSelector(".day-view-shift-hover-info-icon img")));
                waitForSeconds(2);
            } else
                click(shift.findElement(By.cssSelector("img.week-schedule-shit-open-popover")));
            if (isElementLoaded(infoTextFromInfoIcon, 5)){
                return infoTextFromInfoIcon.getText();
            } else
                SimpleUtils.fail("Info icon popup fail to load", false);
        } else
            SimpleUtils.fail("Shift fail to load", false);
        return null;
    }


    @Override
    public int getShiftIndexById(String id) throws Exception {
        waitForSeconds(5);
        WebElement shift = null;
        int index = 0;
        if (id != null && !id.equals("")) {
            String css = "[data-shift-id=\""+ id+"\"]";
            shift = MyThreadLocal.getDriver().findElement(By.cssSelector(css));
            if (isElementLoaded(shift, 5) && areListElementVisible(weekShifts,10)) {
                for (WebElement element: weekShifts){
                    if (element.getText().equalsIgnoreCase(shift.getText())){
                        return index;
                    }
                    index++;
                }
            } else if (isElementLoaded(shift, 5) && areListElementVisible(dayViewAvailableShifts,10)) {
                for (WebElement element: dayViewAvailableShifts){
                    if (element.getText().equalsIgnoreCase(shift.getText())){
                        return index;
                    }
                    index++;
                }
            }else{
                SimpleUtils.fail("Cannot find shift by the id !",false);
            }
        } else {
            SimpleUtils.fail("The shift id is null or empty!",false);
        }
        return index;
    }


    @Override
    public List<WebElement> getAllShiftsOfOneTM(String name) throws Exception{
        ScheduleCommonPage scheduleCommonPage = new ConsoleScheduleCommonPage();
        List<WebElement> allShifts = new ArrayList<>();
        if (areListElementVisible(shiftsWeekView, 15)) {
            for (WebElement shiftWeekView : shiftsWeekView) {
                WebElement workerName = null;
                if(scheduleCommonPage.isScheduleDayViewActive()){
                    workerName = shiftWeekView.findElement(By.className("sch-day-view-shift-worker-name"));
                } else
                    workerName = shiftWeekView.findElement(By.className("week-schedule-worker-name"));
                if (workerName != null && workerName.getText().toLowerCase().contains(name)) {
                    allShifts.add(shiftWeekView);
                }
            }
        }else
            SimpleUtils.fail("Schedule Week View: shifts load failed or there is no shift in this week", false);
        return allShifts;
    }

    @FindBy(css = "span.ot-hours-text")
    private List<WebElement> complianceMessageInInfoIconPopup;
    @Override
    public List<WebElement> getAllOOOHShifts() throws Exception {
        ScheduleCommonPage scheduleCommonPage = new ConsoleScheduleCommonPage();
        List<WebElement> allOOOHShifts = new ArrayList<>();
        WebElement iIcon = null;
        if (areListElementVisible(shiftsWeekView, 15)) {
            for (WebElement shiftWeekView : shiftsWeekView) {
                scrollToElement(shiftWeekView);
                if(scheduleCommonPage.isScheduleDayViewActive()){
                    iIcon = shiftWeekView.findElement(By.cssSelector("div.day-view-shift-hover-info-icon img"));
                    waitForSeconds(2);
                } else
                    iIcon = shiftWeekView.findElement(By.cssSelector("img.week-schedule-shit-open-popover"));
                if(iIcon.getAttribute("src").contains("danger")) {
                    click(iIcon);
                    if (isElementLoaded(popOverContent, 5)){
                        if (areListElementVisible(complianceMessageInInfoIconPopup, 5) && complianceMessageInInfoIconPopup.size()>0){
                            List<String> complianceMessages = new ArrayList<>();
                            for (WebElement message: complianceMessageInInfoIconPopup){
                                complianceMessages.add(message.getText());
                            }
                            if(complianceMessages.contains("Outside Operating hours")) {
                                allOOOHShifts.add(shiftWeekView);
                            }
                        } else
                            SimpleUtils.report("There is no compliance message in info icon popup");
                    } else
                        SimpleUtils.fail("Info icon popup fail to load", false);
                }
            }
        }else
            SimpleUtils.fail("Schedule Week View: shifts load failed or there is no shift in this week", false);
        return allOOOHShifts;
    }


    @Override
    public List<String> getComplianceMessageFromInfoIconPopup(WebElement shift) throws Exception {
        ScheduleCommonPage scheduleCommonPage = new ConsoleScheduleCommonPage();
        List<String> complianceMessages = new ArrayList<>();
        if (isElementLoaded(shift, 5)){
            waitForSeconds(3);
            scrollToElement(shift);
            if(scheduleCommonPage.isScheduleDayViewActive()){
                click(shift.findElement(By.cssSelector(".day-view-shift-hover-info-icon img")));
                waitForSeconds(2);
            } else
                click(shift.findElement(By.cssSelector("img.week-schedule-shit-open-popover")));
            if (isElementLoaded(popOverContent, 5)){
                if (areListElementVisible(complianceMessageInInfoIconPopup, 5) && complianceMessageInInfoIconPopup.size()>0){
                    for (int i=0; i< complianceMessageInInfoIconPopup.size(); i++){
                        complianceMessages.add(complianceMessageInInfoIconPopup.get(i).getText());
                    }
                } else
                    SimpleUtils.report("There is no compliance message in info icon popup");
            } else
                SimpleUtils.fail("Info icon popup fail to load", false);
        } else
            SimpleUtils.fail("Shift fail to load", false);
        return complianceMessages;
    }


    public WebElement getShiftById(String id) throws Exception {
        waitForSeconds(5);
        WebElement shift = null;
        if (id != null && !id.equals("")) {
            String css = "div[data-shift-id=\""+ id+"\"]";
            shift = MyThreadLocal.getDriver().findElement(By.cssSelector(css));
            if (isElementLoaded(shift, 5)) {
                SimpleUtils.pass("Get one shift by the id successfully");
            } else
                SimpleUtils.fail("Cannot find shift by the id !",false);
        } else {
            SimpleUtils.fail("The shift id is null or empty!",false);
        }
        return shift;
    }



    @Override
    public String getTheShiftInfoByIndexInDayview(int index) throws Exception {
        String shiftInfo = "";
        if (areListElementVisible(dayViewAvailableShifts, 20) && index < dayViewAvailableShifts.size()) {
            shiftInfo = dayViewAvailableShifts.get(index).getText();
        } else {
            SimpleUtils.fail("Schedule Page: week shifts not loaded successfully!", false);
        }
        return shiftInfo;
    }
}
