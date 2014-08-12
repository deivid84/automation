package com.globant.training.david_goldes.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TravelerInfoPage extends TripSidebarPage{
	
	@FindBy(xpath = "//*[@class='whosTravelingName']//*[@class='inlineInputGroup nameInclude']/label[2]/input")
	private WebElement firstNameInput;
	
	@FindBy(xpath = "//*[@class='whosTravelingName']//*[@class='inlineInputGroup nameInclude']/label[4]/input")
	private WebElement lastNameInput;
	
	@FindBy(xpath = "//*[@class='phoneNumberInclude inlineInputGroup']/div/label/select")
	private WebElement countryCodeInput;
	
	@FindBy(xpath = "//*[@class='phoneNumberInclude inlineInputGroup']/label/input")
	private WebElement phoneInput;
	
	@FindBy(xpath = "//*[@class='genderDateOfBirthInput']/label/select")
	private WebElement genderInput;
	
	@FindBy(xpath = "//*[@class='genderDateOfBirthInput']/fieldset/label[1]/select")
	private WebElement monthOfBirthInput;
	
	@FindBy(xpath = "//*[@class='genderDateOfBirthInput']/fieldset/label[2]/select")
	private WebElement dayOfBirthInput;
	
	@FindBy(xpath = "//*[@class='genderDateOfBirthInput']/fieldset/label[3]/select")
	private WebElement yearOfBirthInput;
	
	@FindBy(xpath = "//*[@class='email travelerBlock']/label[1]/input")
	private WebElement emailInput;
	
	@FindBy(name = "_eventId_submit")
	private WebElement submitButton;
	
	@FindBy(xpath = "//*[@class='insuranceGenericModuleMod ']/div/div[2]/div/div[2]/label/input")
	private WebElement insuranceNoButton;
	
	@FindBy(xpath = "//*[@id='signInModule']/span[2]")
	private WebElement signInLink;
	
	@FindBy(xpath = "//*[@class='signInForm']/div/label[1]/input")
	private WebElement loginEmailInput;
	
	@FindBy(xpath = "//*[@class='signInForm']/div/label[2]/input")
	private WebElement loginPasswordInput;
	
	@FindBy(name = "_eventId_authenticate")
	private WebElement signInButton;
	
	public void setFirstName(String name){
		firstNameInput.sendKeys(name);
	}
	
	public void setLastName(String name){
		lastNameInput.sendKeys(name);
	}
	
	public void setCountry(String name){
		countryCodeInput.sendKeys(name);
	}		
	
	public void setPhone(String number){
		phoneInput.sendKeys(number);
	}
	
	public void setGender(String g){
		genderInput.sendKeys(g);
	}
	
	public void setMonthOfBirth(String month){
		monthOfBirthInput.sendKeys(month);
	}
	
	public void setDayOfBirth(String day){
		dayOfBirthInput.sendKeys(day);
	}
	
	public void setYearOfBirth(String year){
		yearOfBirthInput.sendKeys(year);
	}
	
	public void setEmail(String email){
		emailInput.sendKeys(email);
	}
	
	public void setInsurance(){
		insuranceNoButton.click();
	}
	
	/**
	 * Sets all the information for the travel at once
	 * 
	 * @param firstName
	 * @param lastName
	 * @param country
	 * @param phone
	 * @param gender
	 * @param month
	 * @param day
	 * @param year
	 * @param email
	 */
	public void setTravelerInformation(String firstName, String lastName, String country, String phone, String gender, String month, String day, String year, String email){
		setFirstName(firstName);
		setLastName(lastName);
		setCountry(country);
		setPhone(phone);
		setGender(gender);
		setMonthOfBirth(month);
		setDayOfBirth(day);
		setYearOfBirth(year);		
		setEmail(email);
		setInsurance();
	}
	
	/**
	 * Sets the missing information when the user has already logged in
	 */
	public void setTravelerInformationLoggedIn(){		
		setInsurance();
	}
	
	public void clickSubmit(){
		submitButton.click();
	}
	
	public void setLoginEmail(String email){
		loginEmailInput.sendKeys(email);
	}
	
	public void setLoginPassword(String pass){
		loginPasswordInput.sendKeys(pass);
	}
	
	/**
	 * Makes the user sign in.
	 * First it clicks on the Sign In link to open the menu to sign in.
	 * Then enters email and password and finally clicks the sign in button
	 * 
	 * @param email User email 
	 * @param pass User password
	 * @return a new TravelerInfoPage with the user signed in
	 */
	public TravelerInfoPage signIn(String email, String pass){
		signInLink.click();
		wait.until(ExpectedConditions.visibilityOf(loginEmailInput));
		setLoginEmail(email);
		setLoginPassword(pass);
		signInButton.click();
		return PageFactory.initElements(driver, TravelerInfoPage.class);
	}
	
	public TravelerInfoPage(WebDriver driver){
		super(driver);
	}
}
