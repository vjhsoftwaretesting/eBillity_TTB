package client_Scenarios;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import reusableClasses.BaseTestClass;

public class Client_BaseClass extends BaseTestClass {
	
	DataFormatter formatter = new DataFormatter();
	@DataProvider(name="driveTest")
	Object[][] getData() throws IOException{
		FileInputStream fis = new FileInputStream("D:\\eBillitySeleniumData\\sel_data.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		int rowCount = sheet.getPhysicalNumberOfRows();
		XSSFRow row = 	sheet.getRow(0);
		int colCount = row.getLastCellNum();
		Object data[][] = new Object [rowCount-1][colCount];
		for(int i=0;i<rowCount-1;i++) {
			row=sheet.getRow(i+1);
			for(int j=0;j<colCount;j++) {
				XSSFCell cell = row.getCell(j);
				data[i][j] = formatter.formatCellValue(cell);
			}
		}
		workbook.close();
		fis.close();
		return data;
	}
}
