package smokeTestCases;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjectsPack.ProjectPage;
import reusableClasses.BaseTestClass;

public class AddProject extends BaseTestClass{
	
	DataFormatter formatter = new DataFormatter();
	@DataProvider(name="driveTest")
	private Object[][] getData() throws IOException{
		FileInputStream fis = new FileInputStream("D:\\eBillitySeleniumData\\sel_data.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(1);
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
	
	@Test
	public void projectModule() {
		PageFactory.initElements(driver, ProjectPage.class);
		waits().until(ExpectedConditions.visibilityOf(ProjectPage.projectModule));
		ProjectPage.projectModule.click();
		
	}
	
	@Test(dependsOnMethods="projectModule" ,dataProvider="driveTest")
	public void project(String cliName,String proName){
		
		ProjectPage.addProject.click();
		ProjectPage.clientDD.click();
		ProjectPage.clientSearchField.sendKeys(cliName);
		waits().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='select2-results']/ul/li[contains(text(),'"+cliName+"')]")));
		driver.findElement(By.xpath("//*[@class='select2-results']/ul/li[contains(text(),'"+cliName+"')]")).click();
		ProjectPage.projectNameFiled.sendKeys(proName);
		ProjectPage.saveCloseProject.click();
		
		String actBannerMsg = bannerMessage();
		String expBannerMsg = "Project saved successfully";
		System.out.println("Project: "+actBannerMsg);
		Assert.assertEquals(actBannerMsg, expBannerMsg);
	}
	
	

}
