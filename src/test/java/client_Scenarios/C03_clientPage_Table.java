package client_Scenarios;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjectsPack.ClientPage;

public class C03_clientPage_Table extends Client_BaseClass {
	
	//validate the visibility of created Client in the table
	
	@Test(dataProvider="driveTest")
	public void tableValidate (String per_Org, String fName, String lName, String orgName) throws InterruptedException {
		PageFactory.initElements(driver, ClientPage.class);
		waits().until(ExpectedConditions.visibilityOf(ClientPage.clientModule));
		ClientPage.clientModule.click();
		Thread.sleep(2000);
		
		String expectedName = null;
		if(!fName.isEmpty() && !lName.isEmpty()) {
			expectedName=fName+" "+lName;
		}
		else {
			expectedName=orgName;
		}
		String actualName = null;
		System.out.println(expectedName);
		int maxPage;
		String maxNo = driver.findElement(By.xpath("//*[@class='paginate_of']")).getText();
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
		List<WebElement> tableClientElements = driver.findElements(By.xpath("//*[@id='tr_0']/following-sibling::tr/td[4]/span"));
		for(WebElement name : tableClientElements) {
			String clientName = name.getText();
			if(clientName.equals(expectedName)) {
				actualName = clientName;
				System.out.println("Created client is present in table at page "+ nextPage);
				break;
				}
		}
		if(expectedName.equals(actualName)) {
			break;
		}
		}
		Assert.assertEquals(expectedName, actualName);
		}
		else {
			List<WebElement> tableClientElementss = driver.findElements(By.xpath("//*[@id='tr_0']/following-sibling::tr/td[4]/span"));
			for(WebElement names : tableClientElementss) {
				String clientNames = names.getText();
				if(clientNames.equals(expectedName)) {
					actualName = clientNames;
					System.out.println("Created client is present in table");
					break;
					}
			}
			Assert.assertEquals(expectedName, actualName);
		}
	}
}
