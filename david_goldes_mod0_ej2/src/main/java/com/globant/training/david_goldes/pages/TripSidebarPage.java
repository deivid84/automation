package com.globant.training.david_goldes.pages;

import java.util.Date;
import java.util.Locale;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TripSidebarPage {

	protected WebDriver driver;
	protected WebDriverWait wait;
	
	@FindBy(xpath = "//*[@class='productInfo']/div/div[1]/dl/dd[1]")
	private WebElement leaveDate;
	
	@FindBy(xpath = "//*[@class='productInfo']/div/div[1]/dl/dd[2]")
	private WebElement returnDate;
	
	@FindBy(xpath = "//*[@class='productInfo']/div/div[2]")
	private WebElement leaveLocation;
	
	@FindBy(xpath = "//*[@class='productInfo']/div/div[3]")
	private WebElement returnLocation;
	
	/**
	 * Verifies that the leave date on the sidebar matches the date
	 * passed to the method. The method takes care of formatting the
	 * the date to the right format.
	 * 
	 * @param date Date object with the date to compare
	 * @return True if both dates match
	 */
	public boolean verifyLeaveDate(Date date){		
		Locale en = new Locale("en");		
		String lDate = String.format(en, "%1$ta, %1$tb %1$te, %1$tY", date);
		return leaveDate.getText().equals(lDate);
	}
	
	/**
	 * Verifies that the return date on the sidebar matches the date
	 * passed to the method. The method takes care of formatting the
	 * the date to the right format.
	 * 
	 * @param date Date object with the date to compare
	 * @return True if both dates match
	 */
	public boolean verifyReturnDate(Date date){
		Locale en = new Locale("en");
		String rDate = String.format(en, "%1$ta, %1$tb %1$te, %1$tY", date);
		return returnDate.getText().equals(rDate);
	}
	
	/**
	 * Verifies that the departure airport matches the string
	 * passed to the method. 
	 * 
	 * @param location String with the code for the airport to compare
	 * @return True if both strings match
	 */
	public boolean verifyLeaveLocation(String location){
		int separator = leaveLocation.getText().indexOf(">");		
		return leaveLocation.getText().substring(0, separator).contains("(" + location + ")");
	}
	
	/**
	 * Verifies that the arrival airport matches the string
	 * passed to the method. 
	 * 
	 * @param location String with the code for the airport to compare
	 * @return True if both strings match
	 */
	public boolean verifyReturnLocation(String location){
		int separator = returnLocation.getText().indexOf(">");
		return returnLocation.getText().substring(0, separator).contains("(" + location + ")");
	}
	
	public TripSidebarPage(WebDriver driver){
		this.driver = driver;
		wait = new WebDriverWait(driver, 30);
	}
}
