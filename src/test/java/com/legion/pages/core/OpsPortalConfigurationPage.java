package com.legion.pages.core;

import com.legion.pages.BasePage;
import com.legion.pages.ConfigurationPage;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.legion.tests.TestBase.propertyMap;
import static com.legion.utils.MyThreadLocal.getDriver;


public class OpsPortalConfigurationPage extends BasePage implements ConfigurationPage {

	public OpsPortalConfigurationPage() {
		PageFactory.initElements(getDriver(), this);
	}
	/*private static Map<String, String> newLocationParas = JsonUtil.getPropertiesFromJsonFile("src/test/resources/AddANewLocation.json");
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
	}*/


	}