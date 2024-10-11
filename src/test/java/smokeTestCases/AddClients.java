package smokeTestCases;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjectsPack.ClientPage;
import reusableClasses.BaseTestClass;

public class AddClients extends BaseTestClass {
	
	DataFormatter formatter = new DataFormatter();
	@DataProvider(name="driveTest")
	private Object[][] getData() throws IOException{
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
	
	@Test
	public void clientModule() {
		PageFactory.initElements(driver, ClientPage.class);
		waits().until(ExpectedConditions.visibilityOf(ClientPage.clientModule));
		ClientPage.clientModule.click();
	}
	
	@Test(dependsOnMethods="clientModule" ,dataProvider="driveTest")
	public void addingClients(String per_Org, String fName, String lName, String orgName) {
		
		waits().until(ExpectedConditions.visibilityOf(ClientPage.clientModule));
		ClientPage.clientModule.click(); 
		waits().until(ExpectedConditions.visibilityOf(ClientPage.AddClient));
		ClientPage.AddClient.click();
		
		if(per_Org.equalsIgnoreCase("O")) {
			ClientPage.clientType.click();
			waits().until(ExpectedConditions.visibilityOf(ClientPage.orgSelect));
			ClientPage.orgSelect.click();
			ClientPage.orgName.sendKeys(orgName);
			ClientPage.saveCloseClient.click();
			String actBannerMsg = bannerMessage();
			String expBannerMsg = orgName+" saved successfully";
			System.out.println("Client: "+actBannerMsg);
			Assert.assertEquals(actBannerMsg, expBannerMsg);
			
		}
		
		else {
			waits().until(ExpectedConditions.visibilityOf(ClientPage.firstName));
			ClientPage.firstName.sendKeys(fName);
			ClientPage.lastName.sendKeys(lName);
			String cliName = String.join(" ", fName,lName);
			ClientPage.saveCloseClient.click();
			String actBannerMsg = bannerMessage();
			String expBannerMsg = cliName+" saved successfully";
			System.out.println("Client: "+actBannerMsg);
			Assert.assertEquals(actBannerMsg, expBannerMsg);
		}
	}
}
