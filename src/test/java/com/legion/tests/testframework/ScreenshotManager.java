package com.legion.tests.testframework;

import static com.legion.utils.MyThreadLocal.getCurrentTestMethodName;
import static com.legion.utils.MyThreadLocal.getDriver;
import static com.legion.utils.MyThreadLocal.getEnterprise;
import static com.legion.utils.MyThreadLocal.getSessionTimestamp;
import static com.legion.utils.MyThreadLocal.getURL;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;

import static com.legion.utils.MyThreadLocal.*;

public class ScreenshotManager {
	
	private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
	
	public static String takeScreenShot() {
		File targetFile;
		File file = getScreenshotDir();
        String threadIdStr = String.valueOf(Thread.currentThread().getId());
        Date date = new Date();
        String screenShotName = date.toString().replace(":", "_").replace(" ", "_") + ".png";
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        String strDate = formatter.format(date);
        String strDateFinal = strDate.replaceAll(" ", "_");
        String screenshotFinalLocation = file + File.separator + strDateFinal +
                File.separator + getSessionTimestamp() +
                File.separator + getCurrentTestMethodName() +
                File.separator + threadIdStr + File.separator + getScreenshotConsoleName();
        targetFile = new File(screenshotFinalLocation, screenShotName);
        File screenshotFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshotFile, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return targetFile.toString();
        }
    }

    public synchronized static void createScreenshotDirIfNotExist() {
        File file = getScreenshotDir();
        if (!file.exists()) {
            file.mkdir();
        }
    }

    private static File getScreenshotDir() {
        return new File("Screenshots" + File.separator + "Results");
    }
	

}
