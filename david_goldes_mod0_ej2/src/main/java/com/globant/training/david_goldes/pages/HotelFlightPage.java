package com.globant.training.david_goldes.pages;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HotelFlightPage extends MasterPage{
	
	@FindBy(xpath = "//*[@class='searchFormForm']/fieldset/div[1]/div[2]/label/input")
	private WebElement fromInput;
	
	@FindBy(xpath = "//*[@class='searchFormForm']/fieldset/div[1]/div[3]/label/input")
	private WebElement toInput;
	
	@FindBy(xpath = "//*[@class='searchFormForm']/fieldset/div[2]/div[2]/label[1]/input")
	private WebElement leaveDateInput;
	
	@FindBy(xpath = "//*[@class='searchFormForm']/fieldset/div[2]/div[3]/label[1]/input")
	private WebElement returnDateInput;
	
	@FindBy(xpath = "//*[@class='adultSelect']/label/select")
	private WebElement adultInput;
	
	@FindBy(xpath = "//*[@class='searchFormForm']/fieldset/div[8]/div/div/input")
	private WebElement searchButton;	
	
	/**
	 * Fills in the fields to complete a "Flight + Hotel" search and clicks the search button
	 * 
	 * @param from
	 * @param to
	 * @param leaveDate
	 * @param returnDate
	 * @param adult Number of adults traveling
	 * @return a new SelectHotelPage
	 */
	public SelectHotelPage setFlightHotel(String from, String to, Date leaveDate, Date returnDate, String adult){
		fromInput.sendKeys(from);
		toInput.sendKeys(to);
		SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yy");
		String lDate = ft.format(leaveDate);
		String rDate = ft.format(returnDate);
		leaveDateInput.sendKeys(lDate);
		returnDateInput.sendKeys(rDate);
		adultInput.sendKeys(adult);
		searchButton.click();
		return PageFactory.initElements(driver, SelectHotelPage.class);
	}
	
	public HotelFlightPage(WebDriver driver){
		super(driver);
	}
	
	public void waitForLoad(){
		super.waitForLoad(adultInput);
	}
}
