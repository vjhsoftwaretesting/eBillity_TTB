package pageObjectsPack;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WeeklyExpensePage {
	
	@FindBy (xpath="//*[@id='createList']/div/a[2]")
	public static WebElement expenseEntryButton;
	
	@FindBy (xpath="(//*[@class='projectcolumn'])[last()]/descendant::a")
	public static WebElement clientDD;
	
	@FindBy (xpath="//*[@id='select2-drop']/div/input")
	public static WebElement clientSearchBox;
	

}
