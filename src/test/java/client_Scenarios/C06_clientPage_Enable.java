package client_Scenarios;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjectsPack.ClientPage;

public class C06_clientPage_Enable extends Client_BaseClass{
	
	@Test(dataProvider="driveTest")
	public void enableClient(String per_Org, String fName, String lName, String orgName) throws InterruptedException {
		
		//then enable the disabled clients
		PageFactory.initElements(driver, ClientPage.class);
		waits().until(ExpectedConditions.visibilityOf(ClientPage.clientModule));
		ClientPage.clientModule.click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@class='link-container']/div[contains(text(),'Disabled')]")).click();
		Thread.sleep(2000);
		String expectedName = null;
		if(!fName.isEmpty() && !lName.isEmpty()) {
			expectedName=fName+" "+lName;
		}
		else {
			expectedName=orgName;
		}
		int maxPage;
		String maxNo = driver.findElement(By.xpath("//*[@class='paginate_of']")).getText();
		WebElement table = driver.findElement(By.id("grdMyEntries"));
        WebElement rows = table.findElement(By.tagName("tr"));
		if(!maxNo.isBlank()) {
			String[] splitted = maxNo.split(" ");
			String ext = splitted[1];
			maxPage = Integer.valueOf(ext);
			int nextPage = 1;
			WebElement field = driver.findElement(By.xpath("//*[@class='paginate_input']"));

			while(nextPage<=maxPage) {
				String num = Integer.toString(nextPage);
				field.clear();
				field.sendKeys(num);
				nextPage++;
				Thread.sleep(4000);
				List<WebElement> columns = rows.findElements(By.xpath("//td[4]/span"));                
                for (WebElement column : columns) {
                    String text = column.getText();
                    if(text.equals(expectedName)) {
                    	WebElement checkBox = column.findElement(By.xpath(".//parent::td/preceding-sibling::td[3]/input"));
                    	checkBox.click();
                    	Thread.sleep(3000);
                    	ClientPage.activeButton.click();
                    	Thread.sleep(1000);
                    	break;
                    }
                }
			}
		}
		else {
		
            List<WebElement> columns = rows.findElements(By.xpath("//td[4]/span"));                
                for (WebElement column : columns) {
                    String text = column.getText();
                    if(text.equals(expectedName)) {
                    	column.findElement(By.xpath(".//parent::td/preceding-sibling::td[3]/input")).click();
                    	Thread.sleep(3000);
                    	ClientPage.activeButton.click();
                    	Thread.sleep(1000);
                    }
                }
		}
		String actBannerMsg = bannerMessage();
		String expBannerMsg = "Selected client is activated successfully.";
		Assert.assertEquals(actBannerMsg, expBannerMsg);
		//-----------------------------------------------------------------------------
		
		waits().until(ExpectedConditions.visibilityOf(ClientPage.clientModule));
		ClientPage.clientModule.click();
		Thread.sleep(2000);
		
		String actualName = null;
		int maxiPage;
		String maxiNo = driver.findElement(By.xpath("//*[@class='paginate_of']")).getText();
		if(!maxiNo.isBlank()) {
		String[] splitted = maxiNo.split(" ");
		String ext = splitted[1];
		maxiPage = Integer.valueOf(ext);
		int nextPage = 1;
		WebElement field = driver.findElement(By.xpath("//*[@class='paginate_input']"));
		while(nextPage<=maxiPage) {
			String num = Integer.toString(nextPage);
			field.clear();
			field.sendKeys(num);
			nextPage++;
			Thread.sleep(4000);
		List<WebElement> tableClientElements = driver.findElements(By.xpath("//*[@id='tr_0']/following-sibling::tr/td[4]/span"));
		Thread.sleep(2000);
		for(WebElement name : tableClientElements) {
			String clientName = name.getText();
			if(clientName.equals(expectedName)) {
				actualName = clientName;
				System.out.println("Disabled client is present in 'Disabled' filter in table at page "+ nextPage);
				break;
				}
		}
		if(expectedName.equals(actualName)) {
			break;
		}
		}
		}
		else {
			List<WebElement> tableClientElementss = driver.findElements(By.xpath("//*[@id='tr_0']/following-sibling::tr/td[4]/span"));
			Thread.sleep(2000);
			for(WebElement names : tableClientElementss) {
				String clientNames = names.getText();
				if(clientNames.equals(expectedName)) {
					actualName = clientNames;
					System.out.println("Disabled client is present in 'Disabled' filter in table");
					break;
					}
			}
		}
		Assert.assertEquals(expectedName, actualName);
	}

}
