package com.globant.training.david_goldes.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TripDetailsPage extends TripSidebarPage{	
	
	@FindBy(name = "_eventId_checkout")
	private WebElement continueButton;
	
	public TripDetailsPage(WebDriver driver){
		super(driver);		
	}	
	
	public CustomizeTripPage clickContinue(){
		continueButton.click();
		return PageFactory.initElements(driver, CustomizeTripPage.class);
	}
	
	public void waitForLoad(){
		wait.until(ExpectedConditions.visibilityOf(continueButton));
	}
}
