
package reusableClasses;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;

import pageObjectsPack.LoginPage;


public class BaseTestClass {
	
	public WebDriver driver;

	public WebDriver initilizeDriver() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//src//test//java//reusableClasses//GlobalData.properties");
		prop.load(fis);
		String browserName = prop.getProperty("browser");

		if (browserName.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} 
		else if (browserName.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} 
		else if (browserName.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		}
		//driver.manage().window().maximize();
		return driver;
	}
	
	public WebDriverWait waits() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		return wait;
	}
	
	public String bannerMessage() {
		
		waits().until(ExpectedConditions.visibilityOfElementLocated(By.id("messageContent")));
		WebElement banMsg = driver.findElement(By.id("messageContent"));
		String actualBannerMsg = banMsg.getText();
		return actualBannerMsg;
	}

	@BeforeTest
	public void loginPage() {
		try {
			driver = initilizeDriver();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//driver.get("https://oauth2qa.ebillity.com/web/session/new");
		driver.get("https://secure.ebillity.com/web/session/new");
		driver.manage().window().maximize();
		// static inputs
		//String email = "andttb001@mailinator.com";
		String email = "lock@mailinator.com";
		String pword = "Test123";
		
		PageFactory.initElements(driver, LoginPage.class);
		waits().until(ExpectedConditions.visibilityOf(LoginPage.userID));
		LoginPage.userID.sendKeys(email);
		LoginPage.password.sendKeys(pword);
		LoginPage.submitButton.click();
	}


}
