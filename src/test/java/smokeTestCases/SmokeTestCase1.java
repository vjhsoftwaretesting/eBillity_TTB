package smokeTestCases;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjectsPack.AddInvoicePage;
import pageObjectsPack.ClientPage;
import pageObjectsPack.MTEWindow;
import pageObjectsPack.ManageMyEntries;
import pageObjectsPack.ProjectPage;
import pageObjectsPack.SingleExpenseEntryWindow;
import reusableClasses.BaseTestClass;

public class SmokeTestCase1 extends BaseTestClass{
	static String cpyDate ;

	DataFormatter formatter = new DataFormatter();
	@DataProvider(name="driveTest")
	Object[][] getData() throws IOException{
		FileInputStream fis = new FileInputStream("D:\\eBillitySeleniumData\\sel_data.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(3);
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
	
	@Test(dataProvider="driveTest",enabled=true,priority=0)
	public void addingClients(String per_Org,String fName,String lName,String orgName,String project,String activity,String fromTime,String toTime,String expenseType,String expenseAmount,String expenseQty) {
		SoftAssert softAssert = new SoftAssert();
		PageFactory.initElements(driver, ClientPage.class);
		waits().until(ExpectedConditions.visibilityOf(ClientPage.clientModule));
		ClientPage.clientModule.click();
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
			String expBannerMsg = orgName+" saved successfully";
			String actBannerMsg = bannerMessage();
			System.out.println("Client: "+actBannerMsg);
			softAssert.assertEquals(actBannerMsg, expBannerMsg);
		}
		
		else {
			waits().until(ExpectedConditions.visibilityOf(ClientPage.firstName));
			ClientPage.firstName.sendKeys(fName);
			ClientPage.lastName.sendKeys(lName);
			String cliName = String.join(" ", fName,lName);
			ClientPage.saveCloseClient.click();
			String expBannerMsg = cliName+" saved successfully";
			String actBannerMsg = bannerMessage();
			System.out.println("Client: "+actBannerMsg);
			softAssert.assertEquals(actBannerMsg, expBannerMsg);
			
		}
	}
	
	@Test(dataProvider="driveTest",enabled=true,priority=1)
	public void project(String per_Org,String fName,String lName,String orgName,String project,String activity,String fromTime,String toTime,String expenseType,String expenseAmount,String expenseQty) {
		SoftAssert softAssert = new SoftAssert();
		String cliName = null;
		if(!fName.isEmpty() && !lName.isEmpty()) {
			cliName=fName+" "+lName;
		}
		else {
			cliName=orgName;
		}
		
		PageFactory.initElements(driver, ProjectPage.class);
		waits().until(ExpectedConditions.visibilityOf(ProjectPage.projectModule));
		ProjectPage.projectModule.click();
		ProjectPage.addProject.click();
		ProjectPage.clientDD.click();
		ProjectPage.clientSearchField.sendKeys(cliName);
		waits().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='select2-results']/ul/li[contains(text(),'"+cliName+"')]")));
		driver.findElement(By.xpath("//*[@class='select2-results']/ul/li[contains(text(),'"+cliName+"')]")).click();
		ProjectPage.projectNameFiled.sendKeys(project);
		ProjectPage.saveCloseProject.click();
		String expBannerMsg = "Project saved successfully";
		String actBannerMsg = bannerMessage();
		System.out.println("Project: "+actBannerMsg);
		softAssert.assertEquals(actBannerMsg, expBannerMsg);
	}
	
	@Test(dataProvider="driveTest",enabled=true, priority=2)
	public void timeEntries(String per_Org,String fName,String lName,String orgName,String project,String activity,String fromTime,String toTime,String expenseType,String expenseAmount,String expenseQty) throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String cliName = null;
		if(!fName.isEmpty() && !lName.isEmpty()) {
			cliName=fName+" "+lName;
		}
		else {
			cliName=orgName;
		}
		PageFactory.initElements(driver, MTEWindow.class);
		PageFactory.initElements(driver, SingleExpenseEntryWindow.class);
		PageFactory.initElements(driver, ManageMyEntries.class);
		driver.findElement(By.linkText("Manage My Entries")).click();
		String oldWindow =driver.getWindowHandle();
		driver.findElement(By.linkText("Track Time")).click();
		Set<String> newWindow = driver.getWindowHandles();  
		for (String newwindow : newWindow) { driver.switchTo().window(newwindow); } 
		waits().until(ExpectedConditions.visibilityOf(MTEWindow.clientDD));
		MTEWindow.clientDD.click();
		MTEWindow.clientSearchBox.sendKeys(project);
		waits().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='select2-results']/li/div[contains(text(),'"+cliName+"')]")));
		driver.findElement(By.xpath("//*[@class='select2-results']/li/div[contains(text(),'"+cliName+"')]")).click();
		Thread.sleep(1000);
		MTEWindow.activityDD.click();
		MTEWindow.activitySearchBox.sendKeys(activity+" ");
		Thread.sleep(1000);
		MTEWindow.activitySearchBox.sendKeys(Keys.BACK_SPACE);
		MTEWindow.activitySearchBox.sendKeys(Keys.ENTER);
		String descpTime="SMT1"+cliName+"-"+project+"-"+activity+"-"+fromTime+" "+toTime;
		MTEWindow.descriptionBox.sendKeys(descpTime);     
		cpyDate = driver.findElement(By.xpath("//*[@class='pull-right tw_date']")).getText();
		MTEWindow.timeIN.clear();
		MTEWindow.timeIN.sendKeys(fromTime);
		MTEWindow.timeOUT.sendKeys(toTime);
		MTEWindow.saveButton.click();
		Thread.sleep(2000);
		driver.switchTo().window(oldWindow);	
		Thread.sleep(3000);
		System.out.println("time entry created");
		//find button
		ManageMyEntries.findButton.click();
		Thread.sleep(1000);
		ManageMyEntries.clientFilter.click();
		ManageMyEntries.searchField.sendKeys(cliName);
		Thread.sleep(800);
		ManageMyEntries.cliSearchResult.click();
		ManageMyEntries.projectFilter.click();
		ManageMyEntries.searchField.sendKeys(project);
		Thread.sleep(800);
		ManageMyEntries.proSearchResult.click();
		ManageMyEntries.activityFilter.click();
		ManageMyEntries.searchField.sendKeys(activity);
		Thread.sleep(800);
		ManageMyEntries.actSearchResult.click();
		driver.findElement(By.xpath("//label[contains(text(),'Date Range')]/following::span[5]")).click();
		driver.findElement(By.xpath("//li[contains(text(),'Custom')]")).click();
		Thread.sleep(800);
		ManageMyEntries.fromDateFilter.clear();
		ManageMyEntries.fromDateFilter.sendKeys(cpyDate);
		Thread.sleep(800);
		ManageMyEntries.toDateFilter.clear();
		ManageMyEntries.toDateFilter.sendKeys(cpyDate);
		Thread.sleep(800);
		ManageMyEntries.searchFilterButton.click();
		//entry checkbox
		waits().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='tr_0']/following::td/span[contains(text(),'"+cpyDate+"')]/following::td/span[contains(text(),'"+descpTime+"')]/parent::td/preceding-sibling::td/input")));
		driver.findElement(By.xpath("//*[@id='tr_0']/following::td/span[contains(text(),'"+cpyDate+"')]/following::td/span[contains(text(),'"+descpTime+"')]/parent::td/preceding-sibling::td/input")).click();
		ManageMyEntries.submitButton.click();
		Thread.sleep(1000);
		waits().until(ExpectedConditions.visibilityOf(ManageMyEntries.submittedFilter));
		ManageMyEntries.submittedFilter.click();	
		//entry inline arrow
		Thread.sleep(1500);
		waits().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='tr_0']/following::td/span[contains(text(),'"+cpyDate+"')]/following::td/span[contains(text(),'"+descpTime+"')]/parent::td/following-sibling::td[@class=' details-control pointer']")));
		driver.findElement(By.xpath("//*[@id='tr_0']/following::td/span[contains(text(),'"+cpyDate+"')]/following::td/span[contains(text(),'"+descpTime+"')]/parent::td/following-sibling::td[@class=' details-control pointer']")).click();
		waits().until(ExpectedConditions.elementToBeClickable(ManageMyEntries.inlineApproveButton));
		ManageMyEntries.inlineApproveButton.click();
		String expBannerMsg = "Approved Successfully";
		waits().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='messageContent'][contains(text(),'Approved Successfully')]")));
		String actBannerMsg = driver.findElement(By.xpath("//*[@id='messageContent']")).getText();
		System.out.println("Time Entry: "+actBannerMsg);
		softAssert.assertEquals(actBannerMsg, expBannerMsg);	
	}

	@Test(dataProvider="driveTest",enabled=true, priority=3)
	public void expenseEntries(String per_Org,String fName,String lName,String orgName,String project,String activity,String fromTime,String toTime,String expenseType,String expenseAmount,String expenseQty) throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String cliName = null;
		if(!fName.isEmpty() && !lName.isEmpty()) {
			cliName=fName+" "+lName;
		}
		else {
			cliName=orgName;
		}
		PageFactory.initElements(driver, ManageMyEntries.class);
		PageFactory.initElements(driver, SingleExpenseEntryWindow.class);
		driver.findElement(By.linkText("Manage My Entries")).click();
		//Expense entry
		waits().until(ExpectedConditions.visibilityOf(ManageMyEntries.addExpenseButton));
		String oldWindow =driver.getWindowHandle();
		ManageMyEntries.addExpenseButton.click();
		Set<String> newWindow2 = driver.getWindowHandles();  
		for (String newwindow2 : newWindow2) { driver.switchTo().window(newwindow2); } 
		waits().until(ExpectedConditions.visibilityOf(SingleExpenseEntryWindow.expCliDD));
		SingleExpenseEntryWindow.expCliDD.click();
		driver.switchTo().activeElement().sendKeys(Keys.DELETE+cliName+Keys.ENTER);
		Thread.sleep(1500);
		SingleExpenseEntryWindow.expProDD.click();
		driver.switchTo().activeElement().sendKeys(Keys.DELETE+project+Keys.ENTER);
		Thread.sleep(1000);
		SingleExpenseEntryWindow.expTypeDD.click();
		driver.switchTo().activeElement().sendKeys(Keys.DELETE+expenseType+Keys.ENTER);
		Thread.sleep(1000);
		String descpExp="SMT1"+cliName+"-"+project+" "+expenseType+"-"+expenseAmount+" "+expenseQty;
		SingleExpenseEntryWindow.expDesField.sendKeys(descpExp);
		SingleExpenseEntryWindow.expCostField.clear();
		SingleExpenseEntryWindow.expCostField.sendKeys(expenseAmount);
		SingleExpenseEntryWindow.expQtyField.clear();;
		SingleExpenseEntryWindow.expQtyField.sendKeys(expenseQty);
		SingleExpenseEntryWindow.saveButton.click();
		SingleExpenseEntryWindow.saveButton.click();
		Thread.sleep(2000);
		driver.switchTo().window(oldWindow);	
		Thread.sleep(3000);
		System.out.println("expense entry created");
		//find button
		ManageMyEntries.expenseTab.click();
		ManageMyEntries.findButton.click();
		Thread.sleep(1000);
		ManageMyEntries.expClientFilter.click();
		ManageMyEntries.searchField.sendKeys(cliName);
		Thread.sleep(800);
		ManageMyEntries.expCliSearchResult.click();
		ManageMyEntries.expProjectFilter.click();
		ManageMyEntries.searchField.sendKeys(project);
		Thread.sleep(800);
		ManageMyEntries.expProSearchResult.click();
		ManageMyEntries.expActivityFilter.click();
		ManageMyEntries.searchField.sendKeys(expenseType);
		Thread.sleep(800);
		ManageMyEntries.expActSearchResult.click();
		driver.findElement(By.xpath("(//label[text()='Invoice Status'])[2]")).click();
		driver.findElement(By.xpath("//div[@id='expensesearch-box']/descendant::span[@class='select2-selection__arrow'][4]")).click();
		driver.findElement(By.xpath("//li[contains(text(),'Custom')]")).click();
		Thread.sleep(800);
		ManageMyEntries.expFromDateFilter.clear();
		ManageMyEntries.expFromDateFilter.sendKeys(cpyDate);
		Thread.sleep(800);
		ManageMyEntries.expToDateFilter.clear();
		ManageMyEntries.expToDateFilter.sendKeys(cpyDate+Keys.ESCAPE);
		Thread.sleep(1000);
		ManageMyEntries.expSearchFilterButton.click();
		waits().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='tr_0']/following::td/span[contains(text(),'"+cpyDate+"')]/following::td/span[contains(text(),'SMT1')]/parent::td/preceding-sibling::td/input")));
		driver.findElement(By.xpath("//*[@id='tr_0']/following::td/span[contains(text(),'"+cpyDate+"')]/following::td/span[contains(text(),'SMT1')]/parent::td/preceding-sibling::td/input")).click();
		ManageMyEntries.approveButton.click();
		String expBannerMsg = "Approved Successfully";
		waits().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='messageContent'][contains(text(),'Approved Successfully')]")));
		String actBannerMsg = driver.findElement(By.xpath("//*[@id='messageContent']")).getText();
		System.out.println("Time Entry: "+actBannerMsg);
		softAssert.assertEquals(actBannerMsg, expBannerMsg);
	}
	
	@Test(dataProvider="driveTest",enabled=true, priority=4)
	public void invoice(String per_Org,String fName,String lName,String orgName,String project,String activity,String fromTime,String toTime,String expenseType,String expenseAmount,String expenseQty) throws InterruptedException {
		String cliName = null;
		if(!fName.isEmpty() && !lName.isEmpty()) {
			cliName=fName+" "+lName;
		}
		else {
			cliName=orgName;
		}	
	driver.findElement(By.linkText("Invoices")).click();
	
	PageFactory.initElements(driver, AddInvoicePage.class);
	AddInvoicePage.addInvoiceButton.click();
	Thread.sleep(1000);
	if(AddInvoicePage.invoiceNo.isEnabled()) {
		AddInvoicePage.invoiceNo.sendKeys("SMTC1");
	}
	AddInvoicePage.invoiceClientDD.click();
	Thread.sleep(1000);
	//select client
	//waits().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@role='treeitem']/span[contains(text(),'"+cliName+"')]")));
	String cli_pro=cliName+" : "+project;
	try {
		AddInvoicePage.cliSearchField.sendKeys(cli_pro);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@role='treeitem']/span[contains(text(),'"+cli_pro+"')]")).click();
	} catch (NoSuchElementException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		AddInvoicePage.cliSearchField.clear();
		Thread.sleep(1000);
		AddInvoicePage.cliSearchField.sendKeys(cliName);
		Thread.sleep(1000);
		waits().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@role='treeitem']/span[contains(text(),'"+cliName+"')]")));
		driver.findElement(By.xpath("//*[@role='treeitem']/span[contains(text(),'"+cliName+"')]")).click();
	}
	Thread.sleep(1000);
	AddInvoicePage.selectInvoiceEntries.click();
	Thread.sleep(2000);
	waits().until(ExpectedConditions.visibilityOf(AddInvoicePage.proFilterSearch));
	AddInvoicePage.proFilterSearch.sendKeys(project);
	Thread.sleep(1000);
	driver.findElement(By.xpath("//*[@id='projectListBox']/option[contains(text(),'"+project+"')]")).click();
	Thread.sleep(5000);
	WebDriverWait waitt = new WebDriverWait(driver,Duration.ofSeconds(50));
	waitt.until(ExpectedConditions.elementToBeClickable(AddInvoicePage.customDateRadio));
	AddInvoicePage.customDateRadio.click();
	waits().until(ExpectedConditions.visibilityOf(AddInvoicePage.invoiceToDate));
	AddInvoicePage.invoiceToDate.clear();
	AddInvoicePage.invoiceFromDate.clear();
	AddInvoicePage.invoiceFromDate.sendKeys(cpyDate);
	AddInvoicePage.invoiceToDate.sendKeys(cpyDate);
	AddInvoicePage.updateButton.click();
	//
	Thread.sleep(5000);
	waits().until(ExpectedConditions.visibilityOf(AddInvoicePage.selectAllCheckBox));
	AddInvoicePage.selectAllCheckBox.click();
	Thread.sleep(1000);
	AddInvoicePage.selectAllCheckBox.click();
	
	String timeFourtyChars="SMT1";
	//if(descpTime.length()>39) {
		//timeFourtyChars=descpTime.substring(0,39);
	//}
	//else {
		//timeFourtyChars=descpTime;
	//}
	String expFourtyChars="SMT1";
	//if(descpExp.length()>39) {
		//expFourtyChars=descpExp.substring(0,39);
	//}
	//else {
		//expFourtyChars=descpExp;
	//}
	
	//selecting time entry checkbox
	driver.findElement(By.xpath("(//*[@id='chkTime']/following::td[contains(text(),'Time')]/following-sibling::td/descendant::span[contains(text(),'"+activity+"')]/ancestor::td/following-sibling::td/descendant::div/span[contains(text(),'"+timeFourtyChars+"')]/preceding::input[@id='chkTime'])[last()]")).click();
	//selecting expense entry checkbox
	driver.findElement(By.xpath("(//*[@id='chkTime']/following::td[contains(text(),'Expense')]/following-sibling::td/descendant::span[contains(text(),'"+expenseType+"')]/ancestor::td/following-sibling::td/descendant::div/span[contains(text(),'"+expFourtyChars+"')]/preceding::input[@id='chkTime'])[last()]")).click();		
	AddInvoicePage.savePreBill.click();
	Thread.sleep(5000);
	}
	
}
