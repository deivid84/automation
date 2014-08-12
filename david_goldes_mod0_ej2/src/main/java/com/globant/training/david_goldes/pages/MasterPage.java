package com.globant.training.david_goldes.pages;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MasterPage {
	
	//constants
	private final static String WELCOMETEXT = "Welcome to CheapTickets";
		
	protected WebDriver driver;
	protected WebDriverWait wait;
	
	@FindBy(css = "a[data-context='signInLink']")
	private WebElement signInLink;
	
	@FindBy(css = "a[data-context='signOutLink']")
	private WebElement signOutLink;
	
	@FindBy(className = "welcomeText")
	private WebElement welcomeText;
	
	@FindBy(id = "headerStandard")
	private WebElement header;
	
	@FindBy(xpath = "//*[@id='headerStandard']/div[2]/ul[1]/li[6]/a/span/span")
	private WebElement flightLink;
	
	@FindBy(xpath = ".//*[@id='products']/div/fieldset/div[2]/label[1]/div")
	private WebElement flightRadioButton;
	
	@FindBy(xpath = "//*[@class='products adMedium']/label[1]/input")
	private WebElement flightHotelButton;
	
	/**
	 * Clicks the "Flight Only" radio button
	 * 
	 * @return a new FlightPage
	 */
	public FlightPage clickFlight() {
		flightRadioButton.click();
		return PageFactory.initElements(driver, FlightPage.class);
	}
	
	/**
	 * Clicks on the "Flight + Hotel" radio button
	 * 
	 * @return a new HotelFlightPage
	 */
	public HotelFlightPage selectFlightHotel(){
		flightHotelButton.click();
		return PageFactory.initElements(driver, HotelFlightPage.class);		
	}
	
	protected MasterPage(WebDriver driver){
		this.driver = driver;
		wait = new WebDriverWait(driver, 50);
	}
	
	/**
	 * Clicks on the sign in link near the top of the screen
	 * User must NOT be logged in
	 * 
	 * @return a new SignInPage
	 */
	public SignInPage clickSignIn(){
		signInLink.click();	
		return PageFactory.initElements(driver, SignInPage.class);
	}

	/**
	 * Clicks on the sign out link near the top of the page
	 * User must be logged in
	 * 
	 * @return a new SignOutPage
	 */
	public SignOutPage clickSignOut(){
		signOutLink.click();
		return PageFactory.initElements(driver, SignOutPage.class);
	}
	
	/**
	 * Verifies that there is a user logged in
	 * 
	 * @return True if the user is logged in
	 */
	public boolean verifySignIn() {
		List<WebElement> welcomeTexts = driver.findElements(By.className("welcomeText"));
		if(welcomeTexts.isEmpty())
			return false;
		else if(welcomeTexts.get(0).getText().equals(WELCOMETEXT))
			return false;
		else
			return true;
	}
	
	protected void waitForLoad(WebElement element){
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	protected void waitForLoad(){
		wait.until(ExpectedConditions.visibilityOf(header));
	}
}
