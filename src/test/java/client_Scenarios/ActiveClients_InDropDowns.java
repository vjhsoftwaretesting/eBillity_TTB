package client_Scenarios;

import org.testng.annotations.Test;

import pageObjectsPack.ClientPage;
import pageObjectsPack.ProjectPage;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ActiveClients_InDropDowns extends Client_BaseClass {
	
	List<String> expectedClients = new ArrayList<String>();
	
	@Test
	public void getActiveClients() throws InterruptedException {
		
		PageFactory.initElements(driver, ClientPage.class);
		waits().until(ExpectedConditions.visibilityOf(ClientPage.clientModule));
		ClientPage.clientModule.click();
	
		 //active clients in clients module	
		int maxPage;
		waits().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='paginate_of']")));
		String maxNo = driver.findElement(By.xpath("//*[@class='paginate_of']")).getText();
		System.out.println(maxNo);
		if(!maxNo.isBlank()) {
		String[] splitted = maxNo.split(" ");
		String ext = splitted[1];
		maxPage = Integer.valueOf(ext);
		int nextPage = 1;
		waits().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='paginate_input']")));
		WebElement field = driver.findElement(By.xpath("//*[@class='paginate_input']"));

		while(nextPage<=maxPage) {
			String num = Integer.toString(nextPage);
			field.clear();
			field.sendKeys(num);
			nextPage++;
			Thread.sleep(4000);
		waits().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='tr_0']/following-sibling::tr/td[4]/span")));
		List<WebElement> tableClientElements = driver.findElements(By.xpath("//*[@id='tr_0']/following-sibling::tr/td[4]/span"));
		for(WebElement name : tableClientElements) {
			String clientName = name.getText();
			expectedClients.add(clientName);
		}
		tableClientElements.clear();
		}
		}
		System.out.println(expectedClients);
		Thread.sleep(5000);
	}
	
	@Test(dependsOnMethods="getActiveClients")
	public void weeklyTS() throws InterruptedException{
		
		//weekly time sheet client field
		List<String> actualClients = new ArrayList<String>();  //clients in dropdowns
		driver.findElement(By.tagName("a")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@title='Weekly Timesheets']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//*[@class='client COL01']/div/a/span[contains(text(),' / Time Off')])[last()]")).click();
		Thread.sleep(3000);
		WebElement dropdownOptionsContainer = driver.findElement(By.xpath("//ul[@class='select2-results']/li/parent::ul"));
	    // Scroll down the dropdown options container
		String scroll ="over";
		try {
			scroll = driver.findElement(By.xpath("//*[@class='select2-search']/following::ul/li[contains(text(),'Loading more results')]")).getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		while(scroll.equals("Loading more results…")) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", dropdownOptionsContainer);
			Thread.sleep(3000);
			try {
				scroll=driver.findElement(By.xpath("//*[@class='select2-search']/following::ul/li[contains(text(),'Loading more results')]")).getText();
			} catch (Exception e) {
				e.printStackTrace();
				scroll="over";
			}
		}
		waits().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class='select2-search']/following::ul/li/div")));
		List<WebElement> dropdownClientElements = driver.findElements(By.xpath("//*[@class='select2-search']/following::ul/li/div"));
		for(WebElement ddname : dropdownClientElements) {
			String dd = ddname.getText();
			if(dd.contains(" : ")) {
				String [] d = dd.split(" :");
				String ddclientName = d[0];
				actualClients.add(ddclientName);
			}
			else {
			actualClients.add(dd);
			}
		}
		boolean con = actualClients.contains("------------------------- Breaks -------------------------");
		if(con) {
		int break_index = actualClients.indexOf("------------------------- Breaks -------------------------");
		int all_index = actualClients.size();
		int k = break_index;
		int l = all_index;
		while(l>k) {
			actualClients.remove(l-1);
			l--;
		}
		}
		else {
			System.out.println("No Break entries");
		}
		System.out.println(actualClients);
		System.out.println(actualClients.size());
		System.out.println(expectedClients.size());
		
		boolean allClientsPresent = true; 
		 for (String expectedClient : expectedClients) {
			 if (!actualClients.contains(expectedClient)) {
				 allClientsPresent = false; break; } }

		 // Output the result 
		 if (allClientsPresent) {
		 System.out.println("All expected clients are present in the weekly timesheet dropdown."); }
		 else {
		System.out.println("Not all expected clients are present in the weekly timesheet dropdown.");
		 }
		 
		 boolean allClientsPresent2 = true; 
		 for (String actualClient : actualClients) {
			 if (!expectedClients.contains(actualClient)) {
				 allClientsPresent2 = false; break; } }

		 // Output the result 
		 if (allClientsPresent2) {
		 System.out.println("2-All expected clients are present in the weekly timesheet dropdown."); }
		 else {
		System.out.println("2-Not all expected clients are present in the weekly timesheet dropdown.");
		 }	
	}
	
	@Test(dependsOnMethods="getActiveClients")
	public void dailyTimeSheets() throws InterruptedException {
		List<String> actualClients = new ArrayList<String>();  //clients in dropdowns
		driver.findElement(By.tagName("a")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@title='Daily Timesheets']")).click();
		Thread.sleep(3000);
		int colNo = 5;
		try {
			driver.findElement(By.xpath("//*[@class='totalTE_time']/parent::div/preceding-sibling::div["+colNo+"]//span[@class='select2-selection__rendered']")).click();
			Thread.sleep(3000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			colNo=4;
		}
		driver.findElement(By.xpath("//*[@class='totalTE_time']/parent::div/preceding-sibling::div["+colNo+"]//span[@class='select2-selection__rendered']")).click();
		Thread.sleep(3000);
		WebElement dropdownOptionsContainer = driver.findElement(By.xpath("//span[@class='select2-results']/ul/li"));
	    // Scroll down the dropdown options container
		String scroll ="over";
		try {
			scroll = driver.findElement(By.xpath("//span[@class='select2-results']/ul/li[contains(text(),'Loading more results')]")).getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		while(scroll.equals("Loading more results…")) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", dropdownOptionsContainer);
			Thread.sleep(3000);
			try {
				scroll=driver.findElement(By.xpath("//span[@class='select2-results']/ul/li[contains(text(),'Loading more results')]")).getText();
			} catch (Exception e) {
				e.printStackTrace();
				scroll="over";
			}
		}
		
		waits().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[@class='select2-results']/ul/li")));
		List<WebElement> dropdownClientElements = driver.findElements(By.xpath("//span[@class='select2-results']/ul/li"));
		for(WebElement ddname : dropdownClientElements) {
			String dd = ddname.getText();
			if(dd.contains(" : ")) {
				String [] d = dd.split(" :");
				String ddclientName = d[0];
				actualClients.add(ddclientName);
			}
			else {
			actualClients.add(dd);
			}
		}
		boolean con = actualClients.contains("------------------------- Breaks -------------------------");
		if(con) {
		int break_index = actualClients.indexOf("------------------------- Breaks -------------------------");
		int all_index = actualClients.size();
		int k = break_index;
		int l = all_index;
		while(l>k) {
			actualClients.remove(l-1);
			l--;
		}
		}
		else {
			System.out.println("No Break entries");
		}
		System.out.println(actualClients);
		System.out.println(actualClients.size());
		System.out.println(expectedClients.size());
		
		boolean allClientsPresent = true; 
		 for (String expectedClient : expectedClients) {
			 if (!actualClients.contains(expectedClient)) {
				 allClientsPresent = false; break; } }

		 // Output the result 
		 if (allClientsPresent) {
		 System.out.println("All expected clients are present in the daily timesheet dropdown."); }
		 else {
		System.out.println("Not all expected clients are present in the daily timesheet dropdown.");
		 }
		 
		 boolean allClientsPresent2 = true; 
		 for (String actualClient : actualClients) {
			 if (!expectedClients.contains(actualClient)) {
				 allClientsPresent2 = false; break; } }

		 // Output the result 
		 if (allClientsPresent2) {
		 System.out.println("2-All expected clients are present in the daily timesheet dropdown."); }
		 else {
		System.out.println("2-Not all expected clients are present in the daily timesheet dropdown.");
		 }	
		
	}
	
	@Test(dependsOnMethods="getActiveClients")
	public void project() throws InterruptedException {
		List<String> actualClients = new ArrayList<String>();  //clients in dropdowns
		driver.findElement(By.tagName("a")).click();
		Thread.sleep(2000);
		PageFactory.initElements(driver, ProjectPage.class);
		waits().until(ExpectedConditions.visibilityOf(ProjectPage.projectModule));
		ProjectPage.projectModule.click();
		Thread.sleep(2000);
		ProjectPage.addProject.click();
		Thread.sleep(2000);
		WebElement clientField = driver.findElement(By.xpath("(//*[@id='clientsFilterDiv'])[2]/descendant::span[4]"));
		clientField.click();
		Thread.sleep(3000);
		WebElement dropdownOptionsContainer = driver.findElement(By.xpath("//span[@class='select2-results']/ul/li"));
	    // Scroll down the dropdown options container
		String scroll ="over";
		try {
			scroll = driver.findElement(By.xpath("//span[@class='select2-results']/ul/li[contains(text(),'Loading more results')]")).getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		while(scroll.equals("Loading more results…")) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", dropdownOptionsContainer);
			Thread.sleep(3000);
			try {
				scroll=driver.findElement(By.xpath("//span[@class='select2-results']/ul/li[contains(text(),'Loading more results')]")).getText();
			} catch (Exception e) {
				e.printStackTrace();
				scroll="over";
			}
		}
		
		waits().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[@class='select2-results']/ul/li")));
		List<WebElement> dropdownClientElements = driver.findElements(By.xpath("//span[@class='select2-results']/ul/li"));
		for(WebElement ddname : dropdownClientElements) {
			String dd = ddname.getText();
			if(dd.contains(" : ")) {
				String [] d = dd.split(" :");
				String ddclientName = d[0];
				actualClients.add(ddclientName);
			}
			else {
			actualClients.add(dd);
			}
		}
		boolean con = actualClients.contains("------------------------- Breaks -------------------------");
		if(con) {
		int break_index = actualClients.indexOf("------------------------- Breaks -------------------------");
		int all_index = actualClients.size();
		int k = break_index;
		int l = all_index;
		while(l>k) {
			actualClients.remove(l-1);
			l--;
		}
		}
		else {
			System.out.println("No Break entries");
		}
		System.out.println(actualClients);
		System.out.println(actualClients.size());
		System.out.println(expectedClients.size());
		
		boolean allClientsPresent = true; 
		 for (String expectedClient : expectedClients) {
			 if (!actualClients.contains(expectedClient)) {
				 allClientsPresent = false; break; } }

		 // Output the result 
		 if (allClientsPresent) {
		 System.out.println("All expected clients are present in the Project>client dropdown."); }
		 else {
		System.out.println("Not all expected clients are present in the Project>client dropdown.");
		 }
		 
		 boolean allClientsPresent2 = true; 
		 for (String actualClient : actualClients) {
			 if (!expectedClients.contains(actualClient)) {
				 allClientsPresent2 = false; break; } }

		 // Output the result 
		 if (allClientsPresent2) {
		 System.out.println("2-All expected clients are present in the Project>client dropdown."); }
		 else {
		System.out.println("2-Not all expected clients are present in the Project>client dropdown.");
		 }	
		//kskdfnskldnf
	}
	
	
	
}
//Page end here
