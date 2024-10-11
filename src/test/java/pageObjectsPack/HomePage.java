package pageObjectsPack;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {
	
	@FindBy(id="createTitle")
	public static WebElement createButton;
	
	@FindBy(xpath="//*[@id='dashboard-home']/li[1]/a")
	public static WebElement dashboardButton;
	
	@FindBy(xpath="//*[@id='dashboard-home']/li[2]/a")
	public static WebElement onTheClockButton;
	
	@FindBy(xpath="//*[@id='dashboard-home']/li[3]/a")
	public static WebElement reportsButton;
	
	@FindBy(xpath="//*[@id='dashboard-home']/li[4]/a")
	public static WebElement ContactsButton;
	
	@FindBy(xpath="//*[@id='dashboard-home']/li[5]/a")
	public static WebElement documentsButton;
	
	@FindBy(xpath="//*[@id='entries-home']/li[1]/a")
	public static WebElement weeklyTimeSheetButton;
	
	@FindBy(xpath="//*[@id='entries-home']/li[2]/a")
	public static WebElement dailyTimesheetButton;
	
	@FindBy(xpath="//*[@id='entries-home']/li[3]/a")
	public static WebElement manageMyEntriesButton;
	
	@FindBy(xpath="//*[@id='entries-home']/li[4]/a")
	public static WebElement manageTeamEntriesButton;
	
	@FindBy(xpath="//*[@id='entries-home']/li[5]/a")
	public static WebElement recuringExpenseButton;
	
	@FindBy(xpath="//*[@id='entries-home']/li[6]/a")
	public static WebElement timeOffButton;
	
	@FindBy(xpath="//*[@id='entries-home']/li[7]/a")
	public static WebElement approvalsButton;
	
	@FindBy(xpath="//*[@id='entries-home']/li[8]/a")
	public static WebElement lockTimePeriodButton;
	
	@FindBy(xpath="//*[@id='entries-home']/li[9]/a")
	public static WebElement notesButton;
	
	
	
	@FindBy(xpath="//*[@id='billing-home']/li[2]/a")
	public static WebElement invoiceButton;
	

}
