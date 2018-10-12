package com.legion.tests.testframework;

import static com.legion.utils.MyThreadLocal.getCurrentTestMethodName;
import static com.legion.utils.MyThreadLocal.getDriver;
import static com.legion.utils.MyThreadLocal.getEnterprise;
import static com.legion.utils.MyThreadLocal.getSessionTimestamp;
import static com.legion.utils.MyThreadLocal.getURL;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.ExtentTest;
import com.legion.utils.JsonUtil;

public class ScreenshotManager {
	
	private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
	protected String strEnterprise = propertyMap.get("ENTERPRISE");
    protected static String screenshotFinalLocation = null;
    public static ThreadLocal<String> consoleName = new ThreadLocal<>();
	
	public static String takeScreenShot() {
		try {
			File file = new File("Screenshots" + File.separator + "Results");
			if (!file.exists()) {
				file.mkdir();
			}
			Date date=new Date();
			String screenShotName=date.toString().replace(":", "_").replace(" ", "_")+".png";
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");  
			String strDate = formatter.format(date);  
			String strDateFinal = strDate.replaceAll(" ", "_");
			screenshotFinalLocation = file + File.separator + strDateFinal + File.separator + getSessionTimestamp() + File.separator + getCurrentTestMethodName() + File.separator + getScreenshotConsoleName();
			File screenshotFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
			File targetFile = new File(screenshotFinalLocation, screenShotName);
			FileUtils.copyFile(screenshotFile, targetFile);
			String targetFinalFile = targetFile.toString();
			return 	targetFinalFile;
			
		} catch (Exception e) {
			return null;
		}
	}
	
	//set screenshot Console name
	public static void setScreenshotConsoleName(String screenshotFolder){
		consoleName.set(screenshotFolder);
    }
	
	//get screenshot Console name
	public static String getScreenshotConsoleName(){
		return consoleName.get();
    }
	

}
