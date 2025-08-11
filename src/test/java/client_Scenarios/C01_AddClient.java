package client_Scenarios;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjectsPack.ClientPage;

// Test comit.111

public class C01_AddClient extends Client_BaseClass {
	
	@Test(dataProvider="driveTest")
	public void addingClients(String per_Org, String fName, String lName, String orgName) {
		
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
