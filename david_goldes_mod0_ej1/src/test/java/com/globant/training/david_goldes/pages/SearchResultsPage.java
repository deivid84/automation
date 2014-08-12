package com.globant.training.david_goldes.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchResultsPage extends MasterPage {
	
	private static final String NOTHINGFOUND = "post-0";	
	
	@FindBy(xpath="//*[@id='content']/article[1]")
	private WebElement article;
	
	public SearchResultsPage(WebDriver driver){
		super(driver);	
	}
	
	public boolean foundResults(){
		return !article.getAttribute("id").equals(NOTHINGFOUND);
	}
	
	public void waitForLoad(){
		super.waitForLoad(article);
	}
}
