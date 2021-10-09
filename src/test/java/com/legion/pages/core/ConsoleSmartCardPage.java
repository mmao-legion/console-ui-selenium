package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.ScheduleCommonPage;
import com.legion.pages.SmartCardPage;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.legion.utils.MyThreadLocal.getDriver;

public class ConsoleSmartCardPage extends BasePage implements SmartCardPage {
    public ConsoleSmartCardPage() {
        PageFactory.initElements(getDriver(), this);
    }
    private static HashMap<String, String> parametersMap2 = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ControlsPageLocationDetail.json");

    @FindBy(css = "div.card-carousel-card")
    private List<WebElement> carouselCards;

    @FindBy(css = "div.card-carousel-arrow.card-carousel-arrow-left")
    private WebElement smartcardArrowLeft;

    @FindBy(css = "div.card-carousel-arrow.card-carousel-arrow-right")
    private WebElement smartcardArrowRight;
    @Override
    public HashMap<String, Float> getScheduleLabelHoursAndWages() throws Exception {
        HashMap<String, Float> scheduleHoursAndWages = new HashMap<String, Float>();
        WebElement budgetedScheduledLabelsDivElement = MyThreadLocal.getDriver().findElement(By.cssSelector("[ng-if*=\"isTitleBasedBudget()\"] .card-carousel-card"));
        if(isElementEnabled(budgetedScheduledLabelsDivElement,5))
        {
//			Thread.sleep(2000);
            String scheduleWagesAndHoursCardText = budgetedScheduledLabelsDivElement.getText();
            String [] tmp =  scheduleWagesAndHoursCardText.split("\n");
            String[] scheduleWagesAndHours = new String[5];
            if (tmp.length>6) {
                scheduleWagesAndHours[0] = tmp[0];
                scheduleWagesAndHours[1] = tmp[1];
                scheduleWagesAndHours[2] = tmp[2];
                scheduleWagesAndHours[3] = tmp[3]+" "+tmp[4]+" "+tmp[5];
                scheduleWagesAndHours[4] = tmp[6];
            }else
                scheduleWagesAndHours =tmp;
            for(int i = 0; i < scheduleWagesAndHours.length; i++)
            {
                if(scheduleWagesAndHours[i].toLowerCase().contains(ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.hours.getValue().toLowerCase()))
                {
                    if (scheduleWagesAndHours[i].split(" ").length == 4) {
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , scheduleWagesAndHours[i].split(" ")[1],
                                ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.budgetedHours.getValue());
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , scheduleWagesAndHours[i].split(" ")[2],
                                ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.scheduledHours.getValue());
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , scheduleWagesAndHours[i].split(" ")[3],
                                ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.otherHours.getValue());
                    } else {
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages, scheduleWagesAndHours[i].split(" ")[1],
                                ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.budgetedHours.getValue());
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages, scheduleWagesAndHours[i + 1],
                                ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.scheduledHours.getValue());
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages, scheduleWagesAndHours[i + 2],
                                ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.otherHours.getValue());
                    }
                    break;
                }
                else if(scheduleWagesAndHours[i].toLowerCase().contains(ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.wages.getValue().toLowerCase()))
                {
                    if (scheduleWagesAndHours[i].split(" ").length == 4) {
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , scheduleWagesAndHours[i].split(" ")[1]
                                .replace("$", ""), ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.budgetedWages.getValue());
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , scheduleWagesAndHours[i].split(" ")[2]
                                .replace("$", ""), ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.scheduledWages.getValue());
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , scheduleWagesAndHours[i].split(" ")[3]
                                .replace("$", ""), ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.otherWages.getValue());
                    } else {
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages, scheduleWagesAndHours[i].split(" ")[1]
                                .replace("$", ""), ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.budgetedWages.getValue());
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages, scheduleWagesAndHours[i + 1]
                                .replace("$", ""), ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.scheduledWages.getValue());
                        scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages, scheduleWagesAndHours[i + 1]
                                .replace("$", ""), ConsoleScheduleNewUIPage.scheduleHoursAndWagesData.otherWages.getValue());
                    }
                    break;
                }
            }
        }
        return scheduleHoursAndWages;
    }

    public HashMap<String, Float> updateScheduleHoursAndWages(HashMap<String, Float> scheduleHoursAndWages,
                                                                     String hours, String hoursAndWagesKey) {
        if (!SimpleUtils.isNumeric(hours)){
            hours = "0";
        }
        scheduleHoursAndWages.put(hoursAndWagesKey, Float.valueOf(hours.replaceAll(",","")));
        return scheduleHoursAndWages;
    }

    @Override
    public synchronized List<HashMap<String, Float>> getScheduleLabelHoursAndWagesDataForEveryDayInCurrentWeek() throws Exception {
        List<HashMap<String, Float>> ScheduleLabelHoursAndWagesDataForDays = new ArrayList<HashMap<String, Float>>();
        List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
        ScheduleCommonPage scheduleCommonPage = new ConsoleScheduleCommonPage();
        if(scheduleCommonPage.isScheduleDayViewActive()) {
            if(ScheduleCalendarDayLabels.size() != 0) {
                for(WebElement ScheduleCalendarDayLabel: ScheduleCalendarDayLabels)
                {
                    click(ScheduleCalendarDayLabel);
                    ScheduleLabelHoursAndWagesDataForDays.add(getScheduleLabelHoursAndWages());
                }
            }
            else {
                SimpleUtils.fail("Schedule Page Day View Calender not Loaded Successfully!", true);
            }
        }
        else {
            SimpleUtils.fail("Schedule Page Day View Button not Active!", true);
        }
        return ScheduleLabelHoursAndWagesDataForDays;
    }

    public boolean isSmartCardAvailableByLabel(String cardLabel) throws Exception {
        waitForSeconds(4);
        if (carouselCards.size() != 0) {
            for (WebElement carouselCard : carouselCards) {
                smartCardScrolleToLeft();
                if (carouselCard.isDisplayed() && carouselCard.getText().toLowerCase().contains(cardLabel.toLowerCase())
                        && isSmartcardContainText(carouselCard))
                    return true;
                else if (!carouselCard.isDisplayed()) {
                    while (isSmartCardScrolledToRightActive() == true) {
                        if (carouselCard.isDisplayed() && carouselCard.getText().toLowerCase().contains(cardLabel.toLowerCase())
                                && isSmartcardContainText(carouselCard))
                            return true;
                    }
                }
            }
        }
        return false;
    }


    void smartCardScrolleToLeft() throws Exception {
        if (isElementLoaded(smartcardArrowLeft, 2)) {
            while (smartcardArrowLeft.getAttribute("class").contains("available")) {
                click(smartcardArrowLeft);
            }
        }

    }


    public boolean isSmartcardContainText(WebElement smartcardElement) throws Exception {
        if (smartcardElement.getText().trim().length() > 0) {
            return true;
        } else {
            SimpleUtils.fail("Schedule Page: Smartcard contains No Text or Spinning Icon on duration '" + getActiveWeekText() + "'.", true);
            return false;
        }
    }



    public boolean isSmartCardScrolledToRightActive() throws Exception {
        if (isElementLoaded(smartcardArrowRight, 10) && smartcardArrowRight.getAttribute("class").contains("available")) {
            click(smartcardArrowRight);
            return true;
        }
        return false;
    }

    @Override
    public String getsmartCardTextByLabel(String cardLabel) {
        if (carouselCards.size() != 0) {
            for (WebElement carouselCard : carouselCards) {
                if (carouselCard.isDisplayed() && carouselCard.getText().toLowerCase().contains(cardLabel.toLowerCase()))
                    return carouselCard.getText();
            }
        }
        return null;
    }


    @FindBy(css = "span.weather-forecast-temperature")
    private List<WebElement> weatherTemperatures;

    @Override
    public String getWeatherTemperature() throws Exception {
        String temperatureText = "";
        if (weatherTemperatures.size() != 0)
            for (WebElement weatherTemperature : weatherTemperatures) {
                if (weatherTemperature.isDisplayed()) {
                    if (temperatureText == "")
                        temperatureText = weatherTemperature.getText();
                    else
                        temperatureText = temperatureText + " | " + weatherTemperature.getText();
                } else if (!weatherTemperature.isDisplayed()) {
                    while (isSmartCardScrolledToRightActive() == true) {
                        if (temperatureText == "")
                            temperatureText = weatherTemperature.getText();
                        else
                            temperatureText = temperatureText + " | " + weatherTemperature.getText();
                    }
                }
            }

        return temperatureText;
    }

    @FindBy(css = "table tr:nth-child(1)")
    private WebElement scheduleTableTitle;
    @FindBy(css = "table tr:nth-child(2)")
    private WebElement scheduleTableHours;
    @Override
    public HashMap<String, String> getHoursFromSchedulePage() throws Exception {
        // Wait for the hours to load
        waitForSeconds(5);
        HashMap<String, String> scheduleHours = new HashMap<>();
        if (isElementLoaded(scheduleTableTitle, 5) && isElementLoaded(scheduleTableHours, 5)) {
            List<WebElement> titles = scheduleTableTitle.findElements(By.tagName("th"));
            List<WebElement> hours = scheduleTableHours.findElements(By.tagName("td"));
            if (titles != null && hours != null) {
                if (titles.size() == 4 && hours.size() == 4) {
                    for (int i = 1; i < titles.size(); i++) {
                        scheduleHours.put(titles.get(i).getText(), hours.get(i).getText());
                        SimpleUtils.pass("Get Title: " + titles.get(i).getText() + " and related Hours: " +
                                hours.get(i).getText());
                    }
                }
            } else {
                SimpleUtils.fail("Failed to find the Schedule table elementes!", true);
            }
        }else {
            SimpleUtils.fail("Elements are not Loaded!", true);
        }
        return scheduleHours;
    }


    public void weatherWeekSmartCardIsDisplayedForAWeek() throws Exception {
        if (isElementLoaded(smartcardArrowRight,5)) {
            click(smartcardArrowRight);
            String jsonTimeZoon = parametersMap2.get("Time_Zone");
            TimeZone timeZone = TimeZone.getTimeZone(jsonTimeZoon);
            SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
            dfs.setTimeZone(timeZone);
            String currentTime =  dfs.format(new Date());
            int currentDay = Integer.valueOf(currentTime.substring(currentTime.length()-2));
            try{
                String firstDayInWeatherSmtCad2 = getDriver().findElement(By.xpath("//*[contains(text(),'Weather - Week of')]")).getText();
                int firstDayInWeatherSmtCad = Integer.valueOf(firstDayInWeatherSmtCad2.substring(firstDayInWeatherSmtCad2.length() - 2));
                System.out.println("firstDayInWeatherSmtCad:" + firstDayInWeatherSmtCad);
                if ((firstDayInWeatherSmtCad + 7) > currentDay) {
                    SimpleUtils.pass("The week smartcard is current week");
                    if (areListElementVisible(weatherTemperatures, 8)) {
                        String weatherWeekTest = getWeatherDayOfWeek();
                        SimpleUtils.report("Weather smart card is displayed for a week from mon to sun" + weatherWeekTest);
                        for (ConsoleScheduleNewUIPage.DayOfWeek e : ConsoleScheduleNewUIPage.DayOfWeek.values()) {
                            if (weatherWeekTest.contains(e.toString())) {
                                SimpleUtils.pass("Weather smartcard include one week weather");
                            } else {
                                SimpleUtils.fail("Weather Smart card is not one whole week", false);
                            }
                        }
                    } else {
                        SimpleUtils.fail("there is no week weather smartcard", false);
                    }
                } else {
                    SimpleUtils.fail("This is not current week weather smartcard ", false);
                }
            } catch (Exception e){
                SimpleUtils.report("there is no week weather smartcard!");
            }
        }else {
            String jsonTimeZoon = parametersMap2.get("Time_Zone");
            TimeZone timeZone = TimeZone.getTimeZone(jsonTimeZoon);
            SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
            dfs.setTimeZone(timeZone);
            String currentTime =  dfs.format(new Date());
            int currentDay = Integer.valueOf(currentTime.substring(currentTime.length()-2));
            try{
                String firstDayInWeatherSmtCad2 = getDriver().findElement(By.xpath("//*[contains(text(),'Weather - Week of')]")).getText();
                int firstDayInWeatherSmtCad = Integer.valueOf(firstDayInWeatherSmtCad2.substring(firstDayInWeatherSmtCad2.length() - 2));
                System.out.println("firstDayInWeatherSmtCad:" + firstDayInWeatherSmtCad);
                if ((firstDayInWeatherSmtCad + 7) > currentDay) {
                    SimpleUtils.pass("The week smartcard is current week");
                    if (areListElementVisible(weatherTemperatures, 8)) {
                        String weatherWeekTest = getWeatherDayOfWeek();
                        SimpleUtils.report("Weather smart card is displayed for a week from mon to sun" + weatherWeekTest);
                        for (ConsoleScheduleNewUIPage.DayOfWeek e : ConsoleScheduleNewUIPage.DayOfWeek.values()) {
                            if (weatherWeekTest.contains(e.toString())) {
                                SimpleUtils.pass("Weather smartcard include one week weather");
                            } else {
                                SimpleUtils.fail("Weather Smart card is not one whole week", false);
                            }
                        }

                    } else {
                        SimpleUtils.fail("there is no week weather smartcard", false);
                    }

                } else {
                    SimpleUtils.fail("This is not current week weather smartcard ", false);
                }
            } catch (Exception e){
                SimpleUtils.report("there is no week weather smartcard!");
            }
        }

    }

    @FindBy(css = ".weather-forecast-day-name")
    private List<WebElement> weatherDaysOfWeek;
    public String getWeatherDayOfWeek() throws Exception {
        String daysText = "";
        if (weatherDaysOfWeek.size() != 0)
            for (WebElement weatherDay : weatherDaysOfWeek) {
                if (weatherDay.isDisplayed()) {
                    if (daysText == "")
                        daysText = weatherDay.getText();
                    else
                        daysText = daysText + " | " + weatherDay.getText();
                } else if (!weatherDay.isDisplayed()) {
                    while (isSmartCardScrolledToRightActive() == true) {
                        if (daysText == "")
                            daysText = weatherDay.getText();
                        else
                            daysText = daysText + " | " + weatherDay.getText();
                    }
                }
            }

        return daysText;
    }


    @FindBy(className = "card-carousel-link")
    private WebElement viewShiftsBtn;
    @Override
    public void validateTheAvailabilityOfOpenShiftSmartcard() throws Exception {
        if (areListElementVisible(carouselCards, 10)) {
            if (isSmartCardAvailableByLabel("WANT MORE HOURS"))
                SimpleUtils.pass("My Schedule Page: Open Shift Smartcard is present on Schedule Page successfully");
            else SimpleUtils.fail("My Schedule Page: Open Shift Smartcard isn't present on Schedule Page", true);
        } else SimpleUtils.fail("My Schedule Page: Smartcards failed to load", true);
    }

    public boolean isViewShiftsBtnPresent() throws Exception {
        boolean isConsistent = false;
        if (areListElementVisible(carouselCards, 10)) {
            if (isSmartCardAvailableByLabel("WANT MORE HOURS")) {
                if (isElementLoaded(viewShiftsBtn, 5)) {
                    isConsistent = true;
                    SimpleUtils.pass("My Schedule Page: 'View Shifts' button is present on Open Shift Smartcard successfully");
                } else
                    SimpleUtils.report("My Schedule Page: 'View Shifts' button isn't present on Open Shift Smartcard since there is 0 shift offer");
            } else SimpleUtils.fail("My Schedule Page: Open Shift Smartcard isn't present on Schedule Page", true);
        } else SimpleUtils.fail("My Schedule Page: Smartcards failed to load", true);
        return isConsistent;
    }


    @FindBy (css = "[ng-if=\"(scheduleSmartCard.unassignedShifts || scheduleSmartCard.outsideOperatingHoursShifts) && hasSchedule()\"] .card-carousel-card")
    private WebElement requiredActionSmartCard;
    @FindBy (css = "[ng-click=\"smartCardShiftFilter('Requires Action')\"]")
    private WebElement viewShiftsBtnOnRequiredActionSmartCard;

    public boolean isRequiredActionSmartCardLoaded() throws Exception {
        if (isElementLoaded(requiredActionSmartCard, 5)) {
            return true;
        } else
            return false;
    }

    public void clickOnViewShiftsBtnOnRequiredActionSmartCard() throws Exception {
        if (isElementLoaded(requiredActionSmartCard, 5) && isElementLoaded(viewShiftsBtnOnRequiredActionSmartCard, 5)) {
            if (viewShiftsBtnOnRequiredActionSmartCard.getText().equalsIgnoreCase("View Shifts")){
                click(viewShiftsBtnOnRequiredActionSmartCard);
                SimpleUtils.pass("Click View Shifts button successfully! ");
            } else if(viewShiftsBtnOnRequiredActionSmartCard.getText().equalsIgnoreCase("Clear Filter")){
                SimpleUtils.report("View Shifts button alreay been clicked! ");
            } else
                SimpleUtils.fail("The button name on required action smart card display incorrectly! ", false);
        } else
            SimpleUtils.fail("The required action smard card or the view shifts button on it loaded fail! ", false);
    }

    public void clickOnClearShiftsBtnOnRequiredActionSmartCard() throws Exception {
        if (isElementLoaded(requiredActionSmartCard, 5) && isElementLoaded(viewShiftsBtnOnRequiredActionSmartCard, 5)) {
            if (viewShiftsBtnOnRequiredActionSmartCard.getText().equalsIgnoreCase("Clear Filter")){
                click(viewShiftsBtnOnRequiredActionSmartCard);
                SimpleUtils.pass("Click Clear Filter button successfully! ");
            } else if(viewShiftsBtnOnRequiredActionSmartCard.getText().equalsIgnoreCase("View Shifts")){
                SimpleUtils.report("Clear Filter button alreay been clicked! ");
            } else
                SimpleUtils.fail("The button name on required action smart card display incorrectly! ", false);
        } else
            SimpleUtils.fail("The required action smard card or the view shifts button on it loaded fail! ", false);
    }

}
