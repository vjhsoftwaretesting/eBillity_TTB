package client_Scenarios;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjectsPack.ClientPage;

public class C02_clientPage_Find extends Client_BaseClass{
	//validating the created client in client page 'Find>client field'
	
	@Test
	public void openFind() throws InterruptedException {
		PageFactory.initElements(driver, ClientPage.class);
		waits().until(ExpectedConditions.visibilityOf(ClientPage.clientModule));
		ClientPage.clientModule.click();
		Thread.sleep(2000);
		ClientPage.findButton.click();
		Thread.sleep(2000);
		
	}
	
	@Test(dependsOnMethods = "openFind", dataProvider="driveTest")
	public void find(String per_Org, String fName, String lName, String orgName) throws InterruptedException {
		String expectedText = null;
		if(!fName.isEmpty() && !lName.isEmpty()) {
			expectedText=fName+" "+lName;
		}
		else {
			expectedText=orgName;
		}
		String actualText = null;
		WebElement findClientField = driver.findElement(By.id("clientName"));
		findClientField.clear();
		findClientField.sendKeys(expectedText);
		Thread.sleep(2000);
		List<WebElement> clientResult = driver.findElements(By.xpath("//*[@class='ui-menu-item']/div"));
		for(WebElement a : clientResult) {
			String b = a.getText();
			if(b.equals(expectedText)) {
				actualText = b;
				break;
			}
			
		}
		Assert.assertEquals(actualText, expectedText);
		System.out.println("Created Client name is present in 'Client page Find Client field' - "+actualText);
		
		//validate the visibility of created client in table by 'Find' filter search
		
		driver.findElement(By.id("grdMyEntries_Search")).click();
		Thread.sleep(2000);
		List<WebElement> c = driver.findElements(By.xpath("//*[@id='tr_0']/following-sibling::tr/td[4]/span"));
		for(WebElement d : c) {
			String e = d.getText();
			if(e.equals(expectedText)) {
				actualText = e;
				break;
			}
		}
		Assert.assertEquals(actualText, expectedText);
		System.out.println("Created client name is present in table by search filter");
	}

}
