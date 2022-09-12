package helper.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import helper.logger.LoggerHelper;

public class ExcelHelper {
	/*
	private Logger log = new LoggerHelper().getLogger(ExcelHelper.class);
	
	public Object[][] getExcelData(String excelLocation, String sheetName) {
		
		try {
			Object dataSets[][] = null;
			// create object of file input stream, FileInputStream requires File type object, you can pass in the location of the excel sheet
			FileInputStream file = new FileInputStream(new File(excelLocation));
			// create a workbook instance, takes FileInput stream as arguments 
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			// Get sheet name from workbook
			XSSFSheet sheet = workbook.getSheet(sheetName);
			// Count number of active rows in excel sheet
			int totalRow = sheet.getLastRowNum();
			// Count active columns in a row
			int totalColumn = sheet.getRow(0).getLastCellNum();
			
			dataSets = new Object [totalRow+1][totalColumn+1];
			System.out.println("rows :"+totalRow);
			System.out.println("columns :"+totalColumn);
			
			// Iterate through each row one by one
			Iterator<Row> rowIterator = sheet.iterator();
			int i = 0; // for row
			while(rowIterator.hasNext()) {
				i++;
				// For every row we need to iterate over columns
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				int j = 0; // for column
					while(cellIterator.hasNext()) {
						j++; // for column
						Cell cell = cellIterator.next();
						switch(cell.getCellTypeEnum()) {
							case STRING:
								dataSets[i-1][j-1] = cell.getStringCellValue();
								break;
							case NUMERIC:
								dataSets[i-1][j-1] = cell.getNumericCellValue();
								break;
							case BOOLEAN:
								dataSets[i-1][j-1] = cell.getBooleanCellValue();
								break;
							case FORMULA:
								dataSets[i-1][j-1] = cell.getCellFormula();
								break;
							default:
								System.out.println("empty cell : no matching enum data type found");
								break;
						}
					}
			}
			return dataSets;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public void updateResults(String excelLocation, String sheetName, String testCaseName, String testStatus) {
		
		try {
			// create object of file input stream, FileInputStream requires File type object, you can pass in the location of the excel sheet
			FileInputStream file = new FileInputStream(new File(excelLocation));
			// create a workbook instance, takes FileInput stream as arguments 
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			// Get sheet name from workbook
			XSSFSheet sheet = workbook.getSheet(sheetName);
			
			// Count number of active rows in excel sheet
			int totalRow = sheet.getLastRowNum()+1;
			
			// we will start from one because we don't need to read the headers on the excel sheet
			for(int i=1; i<totalRow; i++) {
				XSSFRow r = sheet.getRow(i);
				String ce = r.getCell(0).getStringCellValue();
				if(ce.contains(testCaseName)) {
					// a cell should be created before updating it
					r.createCell(1).setCellValue(testStatus);
					file.close();
					log.info("result updated");
					FileOutputStream out = new FileOutputStream(new File(excelLocation));
					workbook.write(out);
					out.close();
					break;
				}
			}
			
		} catch(Exception e) {
			System.out.println(e);
			
		}
	}
	
	
	
	
	public static void main(String[] args) {
		ExcelHelper excelhelper = new ExcelHelper();
		//Object[][] data = excelhelper.getExcelData("resources/configFile/TEST.xlsx", "four");
		//System.out.println();
		excelhelper.updateResults("resources/configFile/TEST.xlsx", "four", "Registration", "Pass");
		System.out.println("pass");
	}
	*/
	
}
