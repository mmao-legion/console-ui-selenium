package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.pages.OpsPortaPageFactories.LocationsPage;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.core.OpsPortal.LocationsTest;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;

public class ReferencePeriodTest extends TestBase {
    private DashboardPage dashboardPage;
    private CreateSchedulePage createSchedulePage;
    private ScheduleMainPage scheduleMainPage;
    private ScheduleShiftTablePage scheduleShiftTablePage;
    private ScheduleCommonPage scheduleCommonPage;
    private EditShiftPage editShiftPage;
    private NewShiftPage newShiftPage;
    private ShiftOperatePage shiftOperatePage;
    private ControlsPage controlsPage;
    private ControlsNewUIPage controlsNewUIPage;
    private MySchedulePage mySchedulePage;
    private BasePage basePage;
    private SmartCardPage smartCardPage;
    private ScheduleOverviewPage scheduleOverviewPage;
    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{
        try {
            this.createDriver((String) params[0], "83", "Window");
            visitPage(testMethod);
            loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
            dashboardPage = pageFactory.createConsoleDashboardPage();
            createSchedulePage = pageFactory.createCreateSchedulePage();
            scheduleMainPage = pageFactory.createScheduleMainPage();
            scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
            scheduleCommonPage = pageFactory.createScheduleCommonPage();
            editShiftPage = pageFactory.createEditShiftPage();
            newShiftPage = pageFactory.createNewShiftPage();
            shiftOperatePage = pageFactory.createShiftOperatePage();
            controlsPage = pageFactory.createConsoleControlsPage();
            controlsNewUIPage = pageFactory.createControlsNewUIPage();
            mySchedulePage = pageFactory.createMySchedulePage();
            basePage = new BasePage();
            smartCardPage = pageFactory.createSmartCardPage();
            scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


    @Automated(automated ="Automated")
    @Owner(owner = "Mary")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
    @TestName(description = "Verify the functionality of peer locations in the Schedule Overview page")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
    public void verifyTheFunctionalityOfPeerLocationsInTheScheduleOverviewPageAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
//        try{
            scheduleCommonPage.clickOnScheduleConsoleMenuItem();
            //Check if the first three weeks been generated
            List<String> weeksStatus = scheduleOverviewPage.getScheduleWeeksStatus();
            if ( (weeksStatus.get(0).equals("Finalized") || weeksStatus.get(0).equals("Published"))
                    && (weeksStatus.get(1).equals("Finalized") || weeksStatus.get(1).equals("Published"))){
                //If generated, check the reference period violation
                goToSchedulePageScheduleTab();
                scheduleCommonPage.navigateToNextWeek();
                scheduleCommonPage.navigateToNextWeek();
                scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyTM.getValue());
                List<String> tmName = new ArrayList<>();
                tmName.add(scheduleShiftTablePage.getFullNameOfOneShiftByIndex(0));

                Map<Integer,String> m = new HashMap<Integer,String>();
                String teamMember1 = scheduleShiftTablePage.getFullNameOfOneShiftByIndex(0).split(" ")[0];
                String teamMember2 = scheduleShiftTablePage.getFullNameOfOneShiftByIndex(7).split(" ")[0];
                String teamMember3 = scheduleShiftTablePage.getFullNameOfOneShiftByIndex(14).split(" ")[0];
                String teamMember4 = scheduleShiftTablePage.getFullNameOfOneShiftByIndex(21).split(" ")[0];
//                int count1 = scheduleShiftTablePage.getShiftsNumberByName(teamMember1);
//                int count1 = scheduleShiftTablePage.getShiftsNumberByName(teamMember2);
//                int count1 = scheduleShiftTablePage.getShiftsNumberByName(teamMember3);
//                int count1 = scheduleShiftTablePage.getShiftsNumberByName(teamMember4);
                m.put(scheduleShiftTablePage.getShiftsNumberByName(teamMember1),teamMember1);
                m.put(scheduleShiftTablePage.getShiftsNumberByName(teamMember2),teamMember2);
                m.put(scheduleShiftTablePage.getShiftsNumberByName(teamMember3),teamMember3);
                m.put(scheduleShiftTablePage.getShiftsNumberByName(teamMember4),teamMember4);

                String firstName1 =m.get(7);
                String firstName2 =m.get(6);
                String firstName3 =m.get(5);
                String firstName4 =m.get(4);
                String violation = "Average hours";
//                //Verify the Average hours violation can display on i icon popup if the shift will trigger week and target reference period violation
//                for(int i=0; i<7;i++){
//                    List<String> complianceMessages = scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup
//                            (scheduleShiftTablePage.getOneDayShiftByName(i, firstName1).get(0));
//                    SimpleUtils.assertOnFail("The Average hours violation should display! ",
//                            complianceMessages.contains(violation), false);
//                }
//                //Verify the Average hours violation will not display on i icon popup if the shift will not trigger reference period violation
//                for(int i=0; i<6;i++){
//                    List<String> complianceMessages = scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup
//                            (scheduleShiftTablePage.getOneDayShiftByName(i, firstName2).get(0));
//                    SimpleUtils.assertOnFail("The Average hours violation should not display! ",
//                            !complianceMessages.contains(violation), false);
//                }
//                //Verify the Average hours violation can display on i icon popup if the shift will trigger target reference period violation
//                for(int i=0; i<5;i++){
//                    List<String> complianceMessages = scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup
//                            (scheduleShiftTablePage.getOneDayShiftByName(i, firstName3).get(0));
//                    SimpleUtils.assertOnFail("The Average hours violation should display! ",
//                            complianceMessages.contains(violation), false);
//                }
//                //Verify the Average hours violation can display on i icon popup if the shift will trigger day, week and target reference period violation
//                for(int i=0; i<4;i++){
//                    List<String> complianceMessages = scheduleShiftTablePage.getComplianceMessageFromInfoIconPopup
//                            (scheduleShiftTablePage.getOneDayShiftByName(i, firstName4).get(0));
//                    SimpleUtils.assertOnFail("The Average hours violation should display! ",
//                            complianceMessages.contains(violation), false);
//                }
//                String workRole = shiftOperatePage.getRandomWorkRole();
//                //Verify the Average hours violation can display on new search TM page if the shift will trigger reference period violation
//                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
//                newShiftPage.clickOnDayViewAddNewShiftButton();
//                newShiftPage.selectWorkRole(workRole);
//                newShiftPage.clearAllSelectedDays();
//                newShiftPage.selectDaysByIndex(6, 6, 6);
//                newShiftPage.moveSliderAtCertainPoint("02:00pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
//                newShiftPage.moveSliderAtCertainPoint("08:00am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
//                newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
//                newShiftPage.clickOnCreateOrNextBtn();
//                newShiftPage.searchWithOutSelectTM(firstName1);
//                SimpleUtils.assertOnFail("There should has Average hours warning message display! ",
//                        shiftOperatePage.getTheMessageOfTMScheduledStatus().contains(violation), false);
//                shiftOperatePage.clickOnRadioButtonOfSearchedTeamMemberByName(firstName1);
//                if(newShiftPage.ifWarningModeDisplay()){
//                    String warningMessage = scheduleShiftTablePage.getWarningMessageInDragShiftWarningMode();
//                    if (warningMessage.contains(violation)){
//                        SimpleUtils.pass("There is no Average hour warning message display on the warning mode! ");
//                    } else
//                        SimpleUtils.fail("There should no Average hour warning message display warning mode! ", false);
//                    shiftOperatePage.clickOnAssignAnywayButton();
//                } else
//                    SimpleUtils.fail("There is no Average hour warning message display on search TM page! ", false);
//
//                newShiftPage.clickOnOfferOrAssignBtn();
//                scheduleMainPage.clickOnCancelButtonOnEditMode();
//
//                //Verify the Average hours violation will not display on new search TM page if the shift will not trigger reference period violation
//                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
//                newShiftPage.clickOnDayViewAddNewShiftButton();
//                newShiftPage.selectWorkRole(workRole);
//                newShiftPage.clearAllSelectedDays();
//                newShiftPage.selectDaysByIndex(6, 6, 6);
//                newShiftPage.moveSliderAtCertainPoint("02:00pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
//                newShiftPage.moveSliderAtCertainPoint("08:00am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
//                newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
//                newShiftPage.clickOnCreateOrNextBtn();
//                newShiftPage.searchWithOutSelectTM(firstName3);
//                SimpleUtils.assertOnFail("There should has Average hours warning message display! ",
//                        !shiftOperatePage.getTheMessageOfTMScheduledStatus().contains(violation), false);
//                shiftOperatePage.clickOnRadioButtonOfSearchedTeamMemberByName(firstName3);
//                if(newShiftPage.ifWarningModeDisplay()){
//                    String warningMessage = scheduleShiftTablePage.getWarningMessageInDragShiftWarningMode();
//                    if (!warningMessage.contains(violation)){
//                        SimpleUtils.pass("There is no Average hour warning message display on the warning mode! ");
//                    } else
//                        SimpleUtils.fail("There should no Average hour warning message display warning mode! ", false);
//                    shiftOperatePage.clickOnAssignAnywayButton();
//                } else
//                    SimpleUtils.pass("There is no Average hour warning message display on search TM page! ");
//
//                newShiftPage.clickOnOfferOrAssignBtn();
//                scheduleMainPage.clickOnCancelButtonOnEditMode();

                //Verify the Average hours violation can display on old search TM page
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                shiftOperatePage.clickOnProfileIconOfOpenShift();
                shiftOperatePage.clickonAssignTM();
                shiftOperatePage.searchTMOnAssignPage(firstName2);
                MyThreadLocal.setMessageOfTMScheduledStatus("");
                SimpleUtils.assertOnFail("There should has Average hours warning message display! ",
                        shiftOperatePage.getTheMessageOfTMScheduledStatus().contains(violation), false);
                shiftOperatePage.clickOnRadioButtonOfSearchedTeamMemberByName(firstName2);
                if(newShiftPage.ifWarningModeDisplay()){
                    String warningMessage = scheduleShiftTablePage.getWarningMessageInDragShiftWarningMode();
                    if (warningMessage.contains(violation)){
                        SimpleUtils.pass("There is no Average hour warning message display on the warning mode! ");
                    } else
                        SimpleUtils.fail("There should no Average hour warning message display warning mode! ", false);
                    shiftOperatePage.clickOnAssignAnywayButton();
                } else
                    SimpleUtils.fail("There is no Average hour warning message display on search TM page! ", false);

                newShiftPage.clickOnOfferOrAssignBtn();
                scheduleMainPage.clickOnCancelButtonOnEditMode();
                //Verify the Average hours violation will not display on old search TM page
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                shiftOperatePage.clickOnProfileIconOfOpenShift();
                shiftOperatePage.clickonAssignTM();
                shiftOperatePage.searchTMOnAssignPage(firstName3);
                MyThreadLocal.setMessageOfTMScheduledStatus("");
                SimpleUtils.assertOnFail("There should has Average hours warning message display! ",
                        !shiftOperatePage.getTheMessageOfTMScheduledStatus().contains(violation), false);
                shiftOperatePage.clickOnRadioButtonOfSearchedTeamMemberByName(firstName3);
                if(newShiftPage.ifWarningModeDisplay()){
                    String warningMessage = scheduleShiftTablePage.getWarningMessageInDragShiftWarningMode();
                    if (!warningMessage.contains(violation)){
                        SimpleUtils.pass("There is no Average hour warning message display on the warning mode! ");
                    } else
                        SimpleUtils.fail("There should no Average hour warning message display warning mode! ", false);
                    shiftOperatePage.clickOnAssignAnywayButton();
                } else
                    SimpleUtils.pass("There is no Average hour warning message display on search TM page! ");

                newShiftPage.clickOnOfferOrAssignBtn();
                scheduleMainPage.clickOnCancelButtonOnEditMode();
                //Verify the Average hours violation can display when drag&drop shift
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                scheduleShiftTablePage.dragOneAvatarToAnotherSpecificAvatar(5, firstName2, 6, firstName1);
                SimpleUtils.assertOnFail("The Average hours violation should display on swap section",
                        scheduleShiftTablePage.verifySwapAndAssignWarningMessageInConfirmPage(violation, "swap"), false);
                SimpleUtils.assertOnFail("The Average hours violation should display on assign section",
                        scheduleShiftTablePage.verifySwapAndAssignWarningMessageInConfirmPage(violation, "assign"), false);

                scheduleShiftTablePage.clickCancelBtnOnDragAndDropConfirmPage();
                scheduleShiftTablePage.dragOneShiftToAnotherDay(5, firstName2, 6);
                SimpleUtils.assertOnFail("The Average hours violation should display on copy section",
                        scheduleShiftTablePage.verifyCopyAndMoveWarningMessageInConfirmPage(violation, "copy"), false);
                SimpleUtils.assertOnFail("The Average hours violation should display on move section",
                        scheduleShiftTablePage.verifySwapAndAssignWarningMessageInConfirmPage(violation, "move"), false);
                scheduleShiftTablePage.clickCancelBtnOnDragAndDropConfirmPage();
                //Verify the Average hours violation can display when drag&drop avatar
                scheduleShiftTablePage.dragOneAvatarToAnotherSpecificAvatar(5, firstName3, 6, firstName1);
                SimpleUtils.assertOnFail("The Average hours violation should not display on swap section",
                        !scheduleShiftTablePage.verifySwapAndAssignWarningMessageInConfirmPage(violation, "swap"), false);
                SimpleUtils.assertOnFail("The Average hours violation should not display on assign section",
                        !scheduleShiftTablePage.verifySwapAndAssignWarningMessageInConfirmPage(violation, "assign"), false);
                scheduleShiftTablePage.clickCancelBtnOnDragAndDropConfirmPage();

                scheduleShiftTablePage.clickCancelBtnOnDragAndDropConfirmPage();
                scheduleShiftTablePage.dragOneShiftToAnotherDay(5, firstName3, 6);
                SimpleUtils.assertOnFail("The Average hours violation should not display on copy section",
                        !scheduleShiftTablePage.verifyCopyAndMoveWarningMessageInConfirmPage(violation, "copy"), false);
                SimpleUtils.assertOnFail("The Average hours violation should not display on move section",
                        !scheduleShiftTablePage.verifySwapAndAssignWarningMessageInConfirmPage(violation, "move"), false);
                scheduleShiftTablePage.clickCancelBtnOnDragAndDropConfirmPage();
            } else{
                //if not generated, try to generate and prepare the data
                goToSchedulePageScheduleTab();
                boolean isScheduleCreated = createSchedulePage.isWeekGenerated();
                if (!isScheduleCreated){
                    createSchedulePage.createScheduleForNonDGFlowNewUI();
                }
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                scheduleShiftTablePage.bulkDeleteTMShiftsInWeekView("");
                scheduleMainPage.saveSchedule();
//                List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getRandomIndexOfShift());
//                String firstNameOfTM1 = shiftInfo.get(0);
//                int shiftCount = 0;
//                while ((firstNameOfTM1.equalsIgnoreCase("open")
//                        || firstNameOfTM1.equalsIgnoreCase("unassigned")) && shiftCount < 100) {
//                    shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getRandomIndexOfShift());
//                    firstNameOfTM1 = shiftInfo.get(0);
//                    shiftCount++;
//                }
//                String workRole1= shiftInfo.get(4);
//                String lastName1 = shiftInfo.get(5);
//
//                shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getRandomIndexOfShift());
//                String firstNameOfTM2 = shiftInfo.get(0);
//                shiftCount = 0;
//                while ((firstNameOfTM2.equalsIgnoreCase("open")
//                        || firstNameOfTM2.equalsIgnoreCase("unassigned")
//                        || firstNameOfTM2.equalsIgnoreCase(firstNameOfTM1)) && shiftCount < 100) {
//                    shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getRandomIndexOfShift());
//                    firstNameOfTM2 = shiftInfo.get(0);
//                    shiftCount++;
//                }
//                String workRole2= shiftInfo.get(4);
//                String lastName2 = shiftInfo.get(5);
//
//                shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getRandomIndexOfShift());
//                String firstNameOfTM3 = shiftInfo.get(0);
//                shiftCount = 0;
//                while ((firstNameOfTM3.equalsIgnoreCase("open")
//                        || firstNameOfTM3.equalsIgnoreCase("unassigned")
//                        || firstNameOfTM3.equalsIgnoreCase(firstNameOfTM1)
//                        || firstNameOfTM3.equalsIgnoreCase(firstNameOfTM2)) && shiftCount < 100) {
//                    shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getRandomIndexOfShift());
//                    firstNameOfTM3 = shiftInfo.get(0);
//                    shiftCount++;
//                }
//                String workRole3= shiftInfo.get(4);
//                String lastName3 = shiftInfo.get(5);
//
//
//                shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getRandomIndexOfShift());
//                String firstNameOfTM4 = shiftInfo.get(0);
//                shiftCount = 0;
//                while ((firstNameOfTM4.equalsIgnoreCase("open")
//                        || firstNameOfTM4.equalsIgnoreCase("unassigned")
//                        || firstNameOfTM4.equalsIgnoreCase(firstNameOfTM1)
//                        || firstNameOfTM4.equalsIgnoreCase(firstNameOfTM2)
//                        || firstNameOfTM4.equalsIgnoreCase(firstNameOfTM3)) && shiftCount < 100) {
//                    shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getRandomIndexOfShift());
//                    firstNameOfTM4 = shiftInfo.get(0);
//                    shiftCount++;
//                }
//                String workRole4= shiftInfo.get(4);
//                String lastName4 = shiftInfo.get(5);
                String workRole = "Team Member Corporate-Theatre";
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                List<String> selectedTMs= createShiftsWithSpecificValues(workRole, "", "", "08:00am", "02:00pm",
                        3, Arrays.asList(0, 1, 2, 3, 4, 5, 6),
                        ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "","");
                String firstNameOfTM1 = selectedTMs.get(0).split(" ")[0];
                String firstNameOfTM2 = selectedTMs.get(1).split(" ")[0];
                String firstNameOfTM3 = selectedTMs.get(2).split(" ")[0];
                scheduleMainPage.saveSchedule();
//                createShiftsWithSpecificValues(workRole2, "", "", "08:00am", "02:00pm",
//                        1, Arrays.asList(0, 1, 2, 3, 4, 5, 6),
//                        ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "",
//                        firstNameOfTM2+ " "+lastName2);
//                createShiftsWithSpecificValues(workRole3, "", "", "08:00am", "02:00pm",
//                        1, Arrays.asList(0, 1, 2, 3, 4, 5, 6),
//                        ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "",
//                        firstNameOfTM3+ " "+lastName3);
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                selectedTMs= createShiftsWithSpecificValues(workRole, "", "", "08:00am", "05:00pm",
                        1, Arrays.asList(0, 1, 2, 3, 4, 5),
                        ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue(), "","");
                String firstNameOfTM4 = selectedTMs.get(0).split(" ")[0];
                scheduleMainPage.saveSchedule();
                createSchedulePage.publishActiveSchedule();
                //Get the info of this week for copy schedule
                String firstWeekInfo = scheduleCommonPage.getActiveWeekText();
                if (firstWeekInfo.length() > 11) {
                    firstWeekInfo = firstWeekInfo.trim().substring(10);
                }
                scheduleCommonPage.navigateToNextWeek();
                isScheduleCreated = createSchedulePage.isWeekGenerated();
                if (isScheduleCreated){
                    createSchedulePage.unGenerateActiveScheduleScheduleWeek();
                }
                createSchedulePage.clickCreateScheduleBtn();
                createSchedulePage.clickNextBtnOnCreateScheduleWindow();
                createSchedulePage.selectWhichWeekToCopyFrom(firstWeekInfo);
                createSchedulePage.clickOnFinishButtonOnCreateSchedulePage();
                Thread.sleep(3000);
                createSchedulePage.publishActiveSchedule();
                String secondWeekInfo = scheduleCommonPage.getActiveWeekText();
                if (secondWeekInfo.length() > 11) {
                    secondWeekInfo = secondWeekInfo.trim().substring(10);
                }
                scheduleCommonPage.navigateToNextWeek();
                isScheduleCreated = createSchedulePage.isWeekGenerated();
                if (isScheduleCreated){
                    createSchedulePage.unGenerateActiveScheduleScheduleWeek();
                }
                createSchedulePage.clickCreateScheduleBtn();
                createSchedulePage.clickNextBtnOnCreateScheduleWindow();
                createSchedulePage.selectWhichWeekToCopyFrom(secondWeekInfo);
                createSchedulePage.clickOnFinishButtonOnCreateSchedulePage();
                //Create one open shift
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                createShiftsWithSpecificValues(workRole, "", "", "08:00am", "02:00pm",
                        1, Arrays.asList(6),
                        ScheduleTestKendraScott2.staffingOption.OpenShift.getValue(), "","");
                scheduleMainPage.saveSchedule();

                scheduleCommonPage.clickOnDayView();
                scheduleCommonPage.navigateDayViewWithIndex(6);
                scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
                shiftOperatePage.deleteTMShiftsInDayView(firstNameOfTM2);
                shiftOperatePage.deleteTMShiftsInDayView(firstNameOfTM3);
                shiftOperatePage.deleteTMShiftsInDayView(firstNameOfTM4);

                scheduleCommonPage.navigateDayViewWithIndex(5);
                shiftOperatePage.deleteTMShiftsInDayView(firstNameOfTM3);
                shiftOperatePage.deleteTMShiftsInDayView(firstNameOfTM4);

                scheduleCommonPage.navigateDayViewWithIndex(4);
                shiftOperatePage.deleteTMShiftsInDayView(firstNameOfTM4);
                scheduleMainPage.saveSchedule();
            }




//        } catch (Exception e){
//            SimpleUtils.fail(e.getMessage(), false);
//        }
    }
}
