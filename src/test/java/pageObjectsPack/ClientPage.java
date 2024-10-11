package pageObjectsPack;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ClientPage {
	
	@FindBy(xpath="//a[@href='/web/Clients/Index']")
	public static WebElement clientModule;
	
	@FindBy (id="find")
	public static WebElement findButton;
	
	@FindBy(id="btnDisable")
	public static WebElement disableButton;
	
	@FindBy(id="btnActive")
	public static WebElement activeButton;
	
	@FindBy(id="addClient")
	public static WebElement AddClient;
	
	@FindBy(id="txtFirstName")
	public static WebElement firstName;
	
	@FindBy(id="txtLastName")
	public static WebElement lastName;
	
	@FindBy(id="save_CloseClient")
	public static WebElement saveCloseClient;
	
	@FindBy (xpath="//span[contains(text(),'Person')]")
	public static WebElement clientType;
	
	@FindBy(xpath="//*[@role='treeitem'][2]")
	public static WebElement orgSelect;
	
	@FindBy (xpath="//*[@id='bsnName']/div[3]/input")
	public static WebElement orgName;
	
}
