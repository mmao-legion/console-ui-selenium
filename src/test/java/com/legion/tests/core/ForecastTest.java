package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class ForecastTest extends TestBase{

	public enum ForecastCalenderWeekCount{
		ZERO(0),
		One(1),
		Two(2),	
		Three(3);
		private final int value;
		ForecastCalenderWeekCount(final int newValue) {
            value = newValue;
        }
        public int getValue() { return value; }
	}

	@Override
	  @BeforeMethod()
	  public void firstTest(Method testMethod, Object[] params) throws Exception{
		  this.createDriver((String)params[0],"69","Window");
	      visitPage(testMethod);
	      loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
	  }


	@Automated(automated ="Automated")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate navigation and data loading in Day/Week view for Shoppers Subtab Of Forecast Tab ")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void forecastShoppersSubTabNavigationStoreManager(String username, String password, String browser, String location) throws Exception {
		ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
		scheduleOverviewPage.loadScheduleOverview();
		ForecastPage ForecastPage = pageFactory.createForecastPage();
		ForecastPage.loadShoppersForecastforPastWeek(ScheduleTest.weekViewType.Next.getValue(), ScheduleTest.weekCount.One.getValue());
		scheduleOverviewPage.loadScheduleOverview();
		ForecastPage.loadShoppersForecastforCurrentNFutureWeek(ScheduleTest.weekViewType.Next.getValue(), ScheduleTest.weekCount.Two.getValue());
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate navigation and data loading in Day/Week view for Labor Subtab Of Forecast Tab")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void forecastLaborSubTabNavigationStoreManager(String username, String password, String browser, String location) throws Exception {
		ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
		scheduleOverviewPage.loadScheduleOverview();
		ForecastPage ForecastPage = pageFactory.createForecastPage();
		ForecastPage.loadLaborForecastforPastWeek(ScheduleTest.weekViewType.Next.getValue(), ScheduleTest.weekCount.One.getValue());
		scheduleOverviewPage.loadScheduleOverview();
		ForecastPage.loadLaborForecastforCurrentNFutureWeek(ScheduleTest.weekViewType.Next.getValue(), ScheduleTest.weekCount.Two.getValue());
	}

		@Automated(automated = "Automated")
		@Owner(owner = "Estelle")
		@Enterprise(name = "Kendrascott2_Enterprise")
		@TestName(description = "T1828164 T1828165 In Holiday smartcard View All button is clickable and Current weeks Holidays are showing Close button is clickable")
		@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
		public void holidaySmartCardIsDisplayedForCurrentAWeek(String username, String password, String browser, String location)
				throws Exception {
			ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
			scheduleOverviewPage.loadScheduleOverview();
			ForecastPage ForecastPage  = pageFactory.createForecastPage();
			ForecastPage.holidaySmartCardIsDisplayedForCurrentAWeek();

		}

}
