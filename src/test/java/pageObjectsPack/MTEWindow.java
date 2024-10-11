package pageObjectsPack;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MTEWindow {
	@FindBy (xpath="//*[@id='createList']/div/a[1]")
	public static WebElement timeEntryButton;

	@FindBy (id="s2id_select2_clients")
	public static WebElement clientDD;
	
	@FindBy (xpath="//*[@id='select2-drop']/div/input")
	public static WebElement clientSearchBox;
	
	@FindBy (id="s2id_select2_activities")
	public static WebElement activityDD;
	
	@FindBy (xpath="//*[@id='select2-drop']/div/input")
	public static WebElement activitySearchBox;
	
	@FindBy (id="invoice_description")
	public static WebElement descriptionBox;
	
	@FindBy (id="date")
	public static WebElement datePicker;
	
	@FindBy (xpath="//*[@class='switch']")
	public static WebElement dateHeader;
	
	@FindBy (xpath="//*[@id='time_from']/input")
	public static WebElement timeIN;
	
	@FindBy (xpath="//*[@id='time_to']/input")
	public static WebElement timeOUT;
	
	@FindBy (id="save")
	public static WebElement saveButton;


}
