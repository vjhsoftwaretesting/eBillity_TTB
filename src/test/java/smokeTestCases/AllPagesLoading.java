package smokeTestCases;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import reusableClasses.BaseTestClass;

public class AllPagesLoading extends BaseTestClass{

	
	@Test
	public void allPages() {
	List<WebElement> links=   driver.findElements(By.xpath("//li[@class='sub-menu ']/a"));
    links.add(driver.findElement(By.id("mnu_project-home")));
    links.add(driver.findElement(By.id("mnu_client-home")));
    links.add(driver.findElement(By.id("mnu_schedule-home")));
	System.out.println(links.size()); 
	 
     for(WebElement link : links)
     {     
      String clickonlinkTab=Keys.chord(Keys.CONTROL,Keys.ENTER);
      
      link.sendKeys(clickonlinkTab);
      try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     }
	}
	
}
