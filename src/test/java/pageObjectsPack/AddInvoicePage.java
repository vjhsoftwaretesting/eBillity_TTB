package pageObjectsPack;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddInvoicePage {
	
	@FindBy (xpath="//*[@title='Add Invoice']")
	static public WebElement addInvoiceButton;
	
	@FindBy (id="invoiceNoTextBox")
	static public WebElement invoiceNo;
	
	@FindBy (xpath="(//b[@role='presentation'])[last()]/parent::*")
	static public WebElement invoiceClientDD;
	
	@FindBy (xpath="//input[@type='search']")
	static public WebElement cliSearchField;
	
	@FindBy (id="selectInvoiceEntries")
	static public WebElement selectInvoiceEntries;
	
	@FindBy (id="entryDateRange")
	static public WebElement customDateRadio;
	
	@FindBy (id="startDate")
	static public WebElement invoiceFromDate;
	
	@FindBy (id="endDate")
	static public WebElement invoiceToDate;
	
	@FindBy (id="FiltersearchButton")
	static public WebElement updateButton;
	
	@FindBy (id="txtProject")
	static public WebElement proFilterSearch;
	
	@FindBy (id="chkAll")
	static public WebElement selectAllCheckBox;
	
	@FindBy (xpath="//*[@class='headerstyle']/descendant::input[@type='checkbox']")
	public static WebElement dateCheckbox;
	
	@FindBy (id="btnSavePrebill")
	public static WebElement savePreBill;
	
	@FindBy (xpath="//*[@title='Add Invoice Batch']")
	static public WebElement addInvoiceBatchButton; 


}
