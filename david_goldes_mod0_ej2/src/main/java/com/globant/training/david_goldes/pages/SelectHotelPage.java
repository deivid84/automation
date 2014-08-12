package com.globant.training.david_goldes.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SelectHotelPage extends VacationPackageSidebarPage{

	@FindBy(xpath = "//*[@class='links dropDown']/div[1]")
	private WebElement sortBox;
	
	@FindBy(xpath = "//*[@class='links dropDown opened']/div[2]/ul/li")
	private List<WebElement> sortOptions;
	
	@FindBy(xpath = "//*[@data-context='selectButton']")
	private List<WebElement> selectButtons;
	
	@FindBy(xpath = "//*[@class='stars']")
	private List<WebElement> starRatings;
	
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
	 * Click the select button in the first match that has one
	 * 
	 * @return a new SelectFlightPage
	 */
	public SelectFlightPage selectFirstOption(){		
		selectButtons.get(0).click();
		return PageFactory.initElements(driver, SelectFlightPage.class);
	}
	
	/**
	 * Clicks on the Sort box and selects the option based on the index received
	 * 
	 * @param option Index for the selected sorting option
	 * @return a new SelectHotelPage sorted by the selection
	 */
	public SelectHotelPage sortByOption(int option){
		wait.until(ExpectedConditions.elementToBeClickable(sortBox));
		sortBox.sendKeys("\n");		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='links dropDown opened']/div[2]/ul/li[1]")));
		sortOptions = driver.findElements(By.xpath("//*[@class='links dropDown opened']/div[2]/ul/li/a"));	
		String selectedOption = sortOptions.get(option).getText();	
		sortOptions.get(option).click();
		wait.until(ExpectedConditions.textToBePresentInElement(sortBox, selectedOption));
		return PageFactory.initElements(driver, SelectHotelPage.class);
	}
	
	/**
	 * Verifies that the page results were correctly sorted by the stars rating
	 * 
	 * @return True if the sort is correct
	 */
	public boolean verifySortedByStars(){
		String prev = null;
		for(WebElement elem : starRatings){
			String stars = elem.getAttribute("alt").substring(0, 1);			
			if(prev != null && stars.compareTo(prev) > 0)
				return false;
			prev = stars;
		}
		return true;
	}
	
	public SelectHotelPage(WebDriver driver){
		super(driver);
	}
	
	public void waitForLoad(){
		wait.until(ExpectedConditions.visibilityOf(sortBox));
	}
}
