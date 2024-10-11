package client_Scenarios;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjectsPack.ProjectPage;

public class C08_projectPage_AddModal extends Client_BaseClass{
	
	@Test (dataProvider="driveTest")
	public void addProModal (String per_Org, String fName, String lName, String orgName) throws InterruptedException {
		String expectedText = null;
		if(!fName.isEmpty() && !lName.isEmpty()) {
			expectedText=fName+" "+lName;
		}
		else {
			expectedText=orgName;
		}
		String actualText = null;
		PageFactory.initElements(driver, ProjectPage.class);
		waits().until(ExpectedConditions.visibilityOf(ProjectPage.projectModule));
		ProjectPage.projectModule.click();
		Thread.sleep(2000);
		ProjectPage.addProject.click();
		Thread.sleep(2000);
		WebElement clientField = driver.findElement(By.xpath("(//*[@class='selection'])[10]/span/span[1]"));
		clientField.sendKeys(expectedText);
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
		System.out.println("Created Client name is present in 'Add Project Client field' - "+actualText);
	}
}
