package com.globant.training.david_goldes.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MasterPage {
	protected WebDriver driver;
	protected WebDriverWait wait;
	
	@FindBy(xpath = "//*[@id='access']/div[3]/ul/li[2]/a")
	private WebElement contactUs;
	
	protected MasterPage(WebDriver driver){
		this.driver = driver;
		wait = new WebDriverWait(driver, 10);
	}
	
	public ContactUsPage clickContactUs() {
		contactUs.click();
		return PageFactory.initElements(driver, ContactUsPage.class);
	}
	
	protected void waitForLoad(WebElement element){
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
}
