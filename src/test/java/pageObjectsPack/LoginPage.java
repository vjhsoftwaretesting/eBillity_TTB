package pageObjectsPack;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
	
	@FindBy(id="txtEmail")
	public static WebElement userID;
	
	@FindBy(id="txtPassword")
	public static WebElement password;
	
	@FindBy(xpath="//*[@type='submit']")
	public static WebElement submitButton;

}
