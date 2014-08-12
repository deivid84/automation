package com.globant.training.david_goldes.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CustomizeTripPage extends TripSidebarPage{

	@FindBy(name = "_eventId_continue")
	private WebElement continueButton;
	
	/**
	 * Clicks the continue button
	 * 
	 * @return a new TravelerInfoPage
	 */
	public TravelerInfoPage clickContinue(){
		continueButton.click();
		return PageFactory.initElements(driver, TravelerInfoPage.class);
	}
	
	public CustomizeTripPage(WebDriver driver){
		super(driver);
	}
}
