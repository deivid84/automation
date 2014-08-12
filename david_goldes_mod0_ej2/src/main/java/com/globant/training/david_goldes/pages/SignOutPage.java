package com.globant.training.david_goldes.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignOutPage extends MasterPage{

	@FindBy(xpath="//*[@id='preMain']/h1")
	private WebElement signedOut;
	
	/**
	 * Verifies that the user has successfully signed out
	 * This is done by checking that a header with the string
	 * "You are now signed out" is present on the page
	 * 
	 * @return True if the user signed out successfully
	 */
	public boolean verifySignedOut(){		
		return signedOut.getText().equals("You are now signed out");					
	}
	
	public SignOutPage(WebDriver driver) {
		super(driver);
	}
	
	public void waitForLoad() {
		super.waitForLoad(signedOut);
	}	
}
