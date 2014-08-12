package com.globant.training.david_goldes.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PostPage extends MasterPage {	
	
	@FindBy(xpath="//*[@id='content']/article[1]//time")
	private WebElement dateTime;
	
	public PostPage(WebDriver driver){
		super(driver);	
	}
	
	public String getDateTime(){
		return dateTime.getAttribute("datetime");
	}
	
	public void waitForLoad(){
		super.waitForLoad(dateTime);
	}
}
