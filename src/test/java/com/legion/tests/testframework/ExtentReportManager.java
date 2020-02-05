package com.legion.tests.testframework;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
	
	public static ExtentReports extent;
    
    public static ExtentReports getInstance() {
    	
		if (extent == null)
    		createInstance("test-output");
        return extent;
    }
    
    public static synchronized ExtentReports createInstance(String fileName) {
        String reportPath = createReportPath(fileName);
    	ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportPath);
      //  htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
       // htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("Custom Report");
     // add custom css
        htmlReporter.config().setCSS("css-string");

        // add custom javascript
        htmlReporter.config().setJS("js-string");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        return extent;
    }
    
    public static String createReportPath(String fileName){
    	
    	Date date=new Date();
    	SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");  
		String strDate = formatter.format(date);  
		String strDateFinal = strDate.replaceAll(" ", "_");
		String reportName=date.toString().replace(":", "_").replace(" ", "_")+".html";
		File file = new File(fileName + File.separator + "AutomationReport" + File.separator + strDateFinal);
		if (!file.exists()) {
			boolean flag=file.mkdirs();
		}	
		String reportPath =fileName + File.separator + "AutomationReport" + File.separator + strDateFinal + File.separator + reportName;
		return reportPath;
    }

}
