package client_Scenarios;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjectsPack.ProjectPage;

public class C07_projectPage_Find extends Client_BaseClass {
	
	@Test
	public void proj_Find() throws InterruptedException {
		PageFactory.initElements(driver, ProjectPage.class);
		waits().until(ExpectedConditions.visibilityOf(ProjectPage.projectModule));
		ProjectPage.projectModule.click();
		Thread.sleep(2000);
		ProjectPage.findButton.click();
		Thread.sleep(2000);
	}
	
	@Test(dependsOnMethods = "proj_Find", dataProvider="driveTest")
	public void find(String per_Org, String fName, String lName, String orgName) throws InterruptedException {
		String expectedText = null;
		if(!fName.isEmpty() && !lName.isEmpty()) {
			expectedText=fName+" "+lName;
		}
		else {
			expectedText=orgName;
		}
		String actualText = null;
		WebElement findClientField = driver.findElement(By.xpath("(//*[@class='selection'])[1]/span/span[1]"));
		findClientField.clear();
		findClientField.sendKeys(expectedText);
		Thread.sleep(3000);
		List<WebElement> clientResult = driver.findElements(By.xpath("//*[@role='treeitem']"));
		for(WebElement a : clientResult) {
			String b = a.getText();
			if(b.equals(expectedText)) {
				actualText = b;
				break;
			}
		}
		Assert.assertEquals(actualText, expectedText);
		System.out.println("Created Client name is present in 'Project page Find Client field' - "+actualText);	
	}
}
