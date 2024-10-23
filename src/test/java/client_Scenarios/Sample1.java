package client_Scenarios;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
//bvcxvb
public class Sample1 extends Client_BaseClass{
	
	@Test
	public void ddd() throws InterruptedException {
		Thread.sleep(4000);
		driver.findElement(By.xpath("//*[@title='Weekly Timesheets']")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//*[@class='client COL01']/div/a/span")).click();
		Thread.sleep(4000);
		
		List<String> expectedClients = new ArrayList<String>();
		expectedClients.add("Client 001");
		expectedClients.add("Client 004");
		
		List<String> actualClients = new ArrayList<String>();  //clients in dropdowns
		
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
		 System.out.println("All expected clients are present in the dropdown."); }
		 else {
		System.out.println("Not all expected clients are present in the dropdown.");
		 }
		 
		
		
	}

}
