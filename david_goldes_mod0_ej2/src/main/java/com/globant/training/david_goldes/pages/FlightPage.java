package com.globant.training.david_goldes.pages;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FlightPage extends MasterPage{

	@FindBy(xpath = "//*[@id='search']/div[2]/div/form/fieldset/div[1]/div[1]/label[1]/input")
	private WebElement leaveInput;
	
	@FindBy(xpath = "//*[@id='search']/div[2]/div/form/fieldset/div[1]/div[2]/label[1]/input")
	private WebElement returnInput;
	
	@FindBy(name = "ar.rt.leaveSlice.date")
	private WebElement leaveDateInput;
	
	@FindBy(name = "ar.rt.returnSlice.date")
	private WebElement returnDateInput;
	
	@FindBy(xpath = "//*[@id='search']/div[2]/div/form/fieldset/div[6]/div/div[1]/input")
	private WebElement searchButton;
	
	/**
	 * Fills in the field to complete a "Flight Only" search and clicks the search button
	 * 
	 * @param leaveLoc
	 * @param returnLoc
	 * @param leaveDate
	 * @param returnDate
	 * @return a new SearchResultsPage
	 */
	public SearchResultsPage setFlight(String leaveLoc, String returnLoc, Date leaveDate, Date returnDate) {
		leaveInput.sendKeys(leaveLoc);
		returnInput.sendKeys(returnLoc);
		SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yy");
		String lDate = ft.format(leaveDate);
		String rDate = ft.format(returnDate);
		leaveDateInput.sendKeys(lDate);
		returnDateInput.sendKeys(rDate);
		searchButton.click();
		return PageFactory.initElements(driver, SearchResultsPage.class);
	}
	public FlightPage(WebDriver driver) {
		super(driver);
	}
	
	public void waitForLoad() {
		super.waitForLoad(searchButton);
	}	
}
