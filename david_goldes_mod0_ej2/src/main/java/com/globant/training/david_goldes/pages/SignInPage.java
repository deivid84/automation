package com.globant.training.david_goldes.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignInPage extends MasterPage{

	@FindBy(xpath = "//*[@class='signInForm']/div/label[1]/input")
	private WebElement userNameInput;
	
	@FindBy(xpath = "//*[@class='signInForm']/div/label[2]/input")
	private WebElement passwordInput;
	
	@FindBy(name = "_eventId_submit")
	private WebElement signInButton;
	
	/**
	 * Performs the Sign In operation for the user
	 * 
	 * @param user User email
	 * @param password User password
	 * @return a new HomePage with the user signed in
	 */
	public HomePage signIn(String user, String password) {
		userNameInput.sendKeys(user);
		passwordInput.sendKeys(password);
		signInButton.click();
		return PageFactory.initElements(driver, HomePage.class);
	}
	
	public SignInPage(WebDriver driver) {
		super(driver);
	}
	
	public void waitForLoad() {
		super.waitForLoad(signInButton);
	}
}
