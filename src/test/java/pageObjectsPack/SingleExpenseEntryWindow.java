package pageObjectsPack;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SingleExpenseEntryWindow {
	
	@FindBy (id="ctl00_ContentPlaceHolder1_ClientDropDownList_Input")
	public static WebElement expCliDD;
	
	@FindBy (id="ctl00_ContentPlaceHolder1_ProjectDropDownList_Input")
	public static WebElement expProDD;
	
	@FindBy (id="ctl00_ContentPlaceHolder1_ExpenseTypeDropDownList_Input")
	public static WebElement expTypeDD;
	
	@FindBy (id="ctl00_ContentPlaceHolder1_descTextBox_textBoxValue")
	public static WebElement expDesField;
	
	@FindBy (id="ctl00_ContentPlaceHolder1_costTextBox")
	public static WebElement expCostField;
	
	@FindBy (id="ctl00_ContentPlaceHolder1_qtyTextBox")
	public static WebElement expQtyField;
	
	@FindBy (id="ctl00_ContentPlaceHolder1_markupRatioTextBox")
	public static WebElement expMarkUpField;
	
	@FindBy (id="ctl00_ContentPlaceHolder1_reimburseCheckBox")
	public static WebElement expReimbureCheckbox;
	
	@FindBy (id="ctl00_ContentPlaceHolder1_taxableCheckbox")
	public static WebElement expTaxCheckbox;
	
	@FindBy (xpath="//button[@title='Choose File']")
	public static WebElement expChooseFileButton;
	
	@FindBy (xpath="//input[@title='Save Expense']")
	public static WebElement saveButton;


}
