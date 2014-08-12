package com.globant.training.david_goldes.pages;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchResultsPage extends MasterPage {

	@FindBy(xpath = "//*[@data-context='leaveFromToText']")
	private WebElement departLocation;
	
	@FindBy(xpath = ".//*[@data-context='returnFromToText']")
	private WebElement arriveLocation;
	
	@FindBy(xpath = "//*[@data-context='leaveDepartDate']")
	private WebElement departDate;
	
	@FindBy(xpath = "//*[@data-context='returnArriveDate']")
	private WebElement arriveDate;
	
	@FindBy(xpath = "//*[@id='main']/div[7]/div//*[@data-context='selectButton']")
	private List<WebElement> selectButtons;
	
	@FindBy(xpath = "//*[@class='matchingResults']")
	private WebElement searchResults;
	
	@FindBy(xpath = "//*[@class='message']")
	private WebElement loadingMessage;
	
	@FindBy(xpath = "//*[@class='links dropDown']/div[1]")
	private WebElement sortBox;
	
	@FindBy(xpath = "//*[@class='links dropDown opened']/div[2]/ul/li")
	private List<WebElement> sortOptions;
	
	@FindBy(xpath = "//*[@class='filter airlinesFilter checkboxList']/table/tbody/tr[7]/td[1]/label/input")
	private WebElement unitedAirlinesCheckBox;
	
	/**
	 * Verifies that the departure airport matches the string
	 * passed to the method. 
	 * 
	 * @param location String with the code for the airport to compare
	 * @return True if both strings match
	 */
	public boolean verifyDepartLocation(String location){
		return departLocation.getText().contains(location);			
	}
	
	/**
	 * Verifies that the arrival airport matches the string
	 * passed to the method. 
	 * 
	 * @param location String with the code for the airport to compare
	 * @return True if both strings match
	 */
	public boolean verifyArriveLocation(String location){		
		return arriveLocation.getText().contains(location);			
	}
	
	/**
	 * Verifies that the leave date on the sidebar matches the date
	 * passed to the method. The method takes care of formatting the
	 * the date to the right format.
	 * 
	 * @param date Date object with the date to compare
	 * @return True if both dates match
	 */
	public boolean verifyDepartDate(Date date){		
		Locale en = new Locale("en");
		String lDate = String.format(en,"%1$ta, %1$tb %1$te, %1$tY", date);				
		return departDate.getText().equals(lDate);			
	}
	
	/**
	 * Verifies that the return date on the sidebar matches the date
	 * passed to the method. The method takes care of formatting the
	 * the date to the right format.
	 * 
	 * @param date Date object with the date to compare
	 * @return True if both dates match
	 */
	public boolean verifyArriveDate(Date date){
		Locale en = new Locale("en");
		String rDate = String.format(en, "%1$ta, %1$tb %1$te, %1$tY", date);			
		return arriveDate.getText().equals(rDate);		
	}
	
	/**
	 * Verifies that there was at least one result available that has the
	 * select button (matches that ask you to contact the airline are ignored because
	 * they don't allow the tests to continue forward)
	 * 
	 * @return True if there is at least one good result
	 */
	public boolean verifyResultsAvailable(){
		return !selectButtons.isEmpty();			
	}
	
	/**
	 * Clicks on the Sort box and selects the option based on the index received
	 * 
	 * @param option Index for the selected sorting option
	 * @return a new SearchResultsPage sorted by the selection
	 */
	public SearchResultsPage sortByOption(int option){
		wait.until(ExpectedConditions.elementToBeClickable(sortBox));
		sortBox.sendKeys("\n");		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='links dropDown opened']/div[2]/ul/li[1]")));
		sortOptions = driver.findElements(By.xpath("//*[@class='links dropDown opened']/div[2]/ul/li/a"));	
		String selectedOption = sortOptions.get(option).getText();	
		sortOptions.get(option).click();
		wait.until(ExpectedConditions.textToBePresentInElement(sortBox, selectedOption));
		return PageFactory.initElements(driver, SearchResultsPage.class);
	}
	
	/**
	 * Click the select button in the first match that has one
	 * 
	 * @return a new TripDetailsPage
	 */
	public TripDetailsPage selectFirstOption(){
		selectButtons = driver.findElements(By.xpath("//*[@id='main']/div[7]/div//*[@data-context='selectButton']"));
		selectButtons.get(0).click();
		return PageFactory.initElements(driver, TripDetailsPage.class);
	}
	
	/**
	 * Filters result by United Airlines and wait for the load to complete
	 */
	public void clickUnitedAirlinesBox(){
		unitedAirlinesCheckBox.click();
		waitForSearchComplete();
	}
	
	public boolean verifySearchResultsPage(){
		List<WebElement> location = driver.findElements(By.xpath("//*[@data-context='leaveFromToText']"));
		return !location.isEmpty();
	}
	
	public SearchResultsPage(WebDriver driver) {
		super(driver);
	}
	
	public void waitForLoad() {
		super.waitForLoad(arriveDate);
	}	
	
	/**
	 * Waits for the page to finish searching, that is when the "Searching" overlay disappears
	 */
	public void waitForSearchComplete(){
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='message']")));
	}
}
