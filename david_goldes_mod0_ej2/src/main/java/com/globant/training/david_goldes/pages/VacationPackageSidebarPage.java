package com.globant.training.david_goldes.pages;

import java.util.Date;
import java.util.Locale;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VacationPackageSidebarPage extends MasterPage{

	@FindBy(xpath = "//*[@class='pkgResultsChangeSearchMod ']/div[1]/span/abbr")
	private WebElement fromLabel;
	
	@FindBy(xpath = "//*[@class='pkgResultsChangeSearchMod ']/div[2]/span/abbr")
	private WebElement toLabel;
	
	@FindBy(xpath = "//*[@class='pkgResultsChangeSearchMod ']/div[1]/p")
	private WebElement leaveDateLabel;
	
	@FindBy(xpath = "//*[@class='pkgResultsChangeSearchMod ']/div[2]/p")
	private WebElement returnDateLabel;	
	
	/**
	 * Verifies that the departure airport matches the string
	 * passed to the method. 
	 * 
	 * @param location String with the code for the airport to compare
	 * @return True if both strings match
	 */
	public boolean verifyDepartLocation(String location){
		return fromLabel.getText().equals("(" + location + ")");
	}
	
	/**
	 * Verifies that the arrival airport matches the string
	 * passed to the method. 
	 * 
	 * @param location String with the code for the airport to compare
	 * @return True if both strings match
	 */
	public boolean verifyArriveLocation(String location){
		return toLabel.getText().equals("(" + location + ")");
	}
	
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
		return leaveDateLabel.getText().equals(lDate);				
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
		return returnDateLabel.getText().equals(rDate);
	}
	
	public VacationPackageSidebarPage(WebDriver driver){
		super(driver);
	}
	
	public void waitForLoad(){
		wait.until(ExpectedConditions.visibilityOf(returnDateLabel));
	}
}
