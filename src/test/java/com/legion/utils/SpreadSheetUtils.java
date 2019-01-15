package com.legion.utils;

import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SpreadSheetUtils {

	public static ArrayList<HashMap<String, String>> readExcel(String fileNameAndPath,String sheetName) throws IOException {
		
		File file =    new File(fileNameAndPath);
	    FileInputStream inputStream = new FileInputStream(file);
	    Workbook workbook = null;

	    ArrayList<HashMap <String, String>> spreadSheetValue = new ArrayList<HashMap <String, String>>();
	    //Find the file extension by splitting file name in substring  and getting only extension name

	    String fileExtensionName = fileNameAndPath.substring(fileNameAndPath.indexOf("."));

	    //Check condition if the file is xlsx file

	    if(fileExtensionName.equals(".xlsx")){

	    //If it is xlsx file then create object of XSSFWorkbook class

	    	workbook = new XSSFWorkbook(inputStream);

	    }

	    //Check condition if the file is xls file

	    else if(fileExtensionName.equals(".xls")){

	        //If it is xls file then create object of XSSFWorkbook class

	    	workbook = new HSSFWorkbook(inputStream);

	    }

	    //Read sheet inside the workbook by its name

	    Sheet spreadSheet = workbook.getSheet(sheetName);

	    //Find number of rows in excel file

	    int rowCount = spreadSheet.getLastRowNum()-spreadSheet.getFirstRowNum();
	    for (int i = 1; i < rowCount+1; i++) {
	        Row row = spreadSheet.getRow(i);
	        HashMap <String, String> spreadSheetRow = new HashMap <String, String>();
	        if(! isEmptyRow(row))
	        {
	        	for (int j = 0; j < row.getLastCellNum(); j++) {
		            spreadSheetRow.put(spreadSheet.getRow(0).getCell(j).getStringCellValue().trim(), row.getCell(j).getStringCellValue());
		        }
	        	spreadSheetValue.add(spreadSheetRow);
	        }
	    } 

	    return spreadSheetValue;
	}
	
	  public static boolean isEmptyRow(Row row){
		     boolean isEmptyRow = true;
		         for(int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++){
		            Cell cell = row.getCell(cellNum);
		            if(cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK && StringUtils.isNotBlank(cell.toString())){
		            isEmptyRow = false;
		            }    
		         }
		     return isEmptyRow;
		   }
}
