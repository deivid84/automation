package com.globant.training.david_goldes.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchResultsPage extends MasterPage {
	
    //que article es ? estas hardcodeando el articulo
	@FindBy(xpath="//*[@id='content']/article[1]")
	private WebElement article;
	
	public SearchResultsPage(WebDriver driver){
		super(driver);	
	}
	
	public boolean foundResults(){
	    //en estos casos donde tenes que buscar cant de resultados se suele usar algo asi como
	    return driver.findElements(By.cssSelector("selector")).size() > 0;
		//return !article.getAttribute("id").equals(NOTHINGFOUND);
	}
}
