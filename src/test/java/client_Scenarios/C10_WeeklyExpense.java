package client_Scenarios;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjectsPack.HomePage;
import pageObjectsPack.WeeklyExpensePage;

public class C10_WeeklyExpense extends Client_BaseClass {
	
	
	@Test(dataProvider="driveTest")
	public void MTE(String per_Org, String fName, String lName, String orgName) throws InterruptedException {
		
		String expectedText = null;
		if(!fName.isEmpty() && !lName.isEmpty()) {
			expectedText=fName+" "+lName;
		}
		else {
			expectedText=orgName;
		}
		String actualText = null;
		
		PageFactory.initElements(driver, WeeklyExpensePage.class);
		PageFactory.initElements(driver, HomePage.class);
		HomePage.createButton.click();
		WeeklyExpensePage.expenseEntryButton.click();
		waits().until(ExpectedConditions.visibilityOf(WeeklyExpensePage.clientDD));
		WeeklyExpensePage.clientDD.click();
		WeeklyExpensePage.clientSearchBox.sendKeys(expectedText);
		waits().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[@class='select2-results'])[last()]/li/div/span")));
		List<WebElement> cli_Opt = driver.findElements(By.xpath("(//*[@class='select2-results'])[last()]/li/div/span"));
		for(WebElement cli : cli_Opt) {
			String cliText = cli.getText();
			if(cliText.contains(" : ")) {
				String [] d = cliText.split(" :");
				String clientName = d[0];
				if(clientName.equals(expectedText)) {
				actualText=clientName;
				}
			}
			else {
				if(cliText.equals(expectedText)) {
				actualText = cliText;
				}
			}
		}
		driver.findElement(By.xpath("(//*[@class='select2-results'])[last()]/li/div/span")).click();
		Assert.assertEquals(actualText, expectedText);
		System.out.println("Created Client name is present in 'weekly expense window Client field' - "+actualText);
	}
}
